[CompilationUnitImpl][CtPackageDeclarationImpl]package org.pmiops.workbench.auth;
[CtUnresolvedImport]import com.google.api.client.http.javanet.NetHttpTransport;
[CtImportImpl]import java.time.Instant;
[CtUnresolvedImport]import com.google.api.client.json.jackson2.JacksonFactory;
[CtUnresolvedImport]import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
[CtUnresolvedImport]import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.google.api.client.http.HttpTransport;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.time.Duration;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import com.google.api.client.auth.oauth2.TokenRequest;
[CtUnresolvedImport]import com.google.api.client.json.webtoken.JsonWebToken;
[CtUnresolvedImport]import com.google.auth.oauth2.OAuth2Credentials;
[CtUnresolvedImport]import com.google.cloud.iam.credentials.v1.SignJwtRequest;
[CtUnresolvedImport]import com.google.api.client.json.JsonFactory;
[CtUnresolvedImport]import com.google.api.client.auth.oauth2.TokenResponse;
[CtUnresolvedImport]import com.google.api.client.http.GenericUrl;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.google.auth.oauth2.AccessToken;
[CtClassImpl][CtJavaDocImpl]/**
 * OAuth2 Credentials representing a Service Account using domain-wide delegation of authority to
 * generate access tokens on behalf of a G Suite user.
 *
 * <p>This class calls the IAM Credentials API to request a JWT to be signed using a service
 * account's system-managed private key. This is different from the approach adopted by the
 * ServiceAccountCredentials class, where an application-provided private key is used to self-sign
 * the JWT and then exchange it for an access token.
 *
 * <p>This use of the IAM Credentials API allows a system to use domain-wide delegation of authority
 * to authorize calls as end users without loading private keys directly into the application.
 *
 * <p>This class shares some patterns in common with the ImpersonatedCredentials class; namely, it
 * uses the IAM Credentials API to allow one service account to perform some actions on behalf of
 * another service account. However, this class differs in two notable ways: (1) it supports
 * impersonation of end users, while ImpersonatedCredentials supports only impersonation of service
 * accounts, and (2) it relies on application default credentials for simplicity in the All of Us
 * Researcher Workbench use case.
 *
 * <p>Example usage, for authorizing user requests to the Google Directory API:<br>
 *
 * <pre>
 *   DelegatedUserCredentials delegatedCredentials = new DelegatedUserCredentials(
 *     "service-account-with-dwd-enabled@project-name.iam.gserviceaccount.com",
 *     "admin-gsuite-user@my-gsuite-domain.com",
 *     DirectoryScopes.ADMIN_DIRECTORY_USERS);
 *   Directory service = new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), null)
 *     .setHttpRequestInitializer(new HttpCredentialsAdapter(delegatedCredentials))
 *     .build();
 * </pre>
 */
public class DelegatedUserCredentials extends [CtTypeReferenceImpl]com.google.auth.oauth2.OAuth2Credentials {
    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String JWT_BEARER_GRANT_TYPE = [CtLiteralImpl]"urn:ietf:params:oauth:grant-type:jwt-bearer";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String SERVICE_ACCOUNT_NAME_FORMAT = [CtLiteralImpl]"projects/-/serviceAccounts/%s";

    [CtFieldImpl]static final [CtTypeReferenceImpl]com.google.api.client.json.JsonFactory JSON_FACTORY = [CtInvocationImpl][CtTypeAccessImpl]com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance();

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.time.Duration ACCESS_TOKEN_DURATION = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMinutes([CtLiteralImpl]60);

    [CtFieldImpl][CtCommentImpl]// The email of the service account whose system-managed key should be used to sign the JWT
    [CtCommentImpl]// assertion which is exchanged for an access token. This service account:
    [CtCommentImpl]// - Must have domain-wide delegation enabled for the target user's G Suite domain and scopes.
    [CtCommentImpl]// - Does not need to be the same service account (SA) as the application default credentials
    [CtCommentImpl]// (ADC)
    [CtCommentImpl]// service account. If they are different, the ADC account must have the Service Account Token
    [CtCommentImpl]// Creator role granted on this service account. See
    [CtCommentImpl]// https://cloud.google.com/iam/docs/creating-short-lived-service-account-credentials for more
    [CtCommentImpl]// details.
    private [CtTypeReferenceImpl]java.lang.String serviceAccountEmail;

    [CtFieldImpl][CtCommentImpl]// The full G Suite email address of the user for whom an access token will be generated.
    private [CtTypeReferenceImpl]java.lang.String userEmail;

    [CtFieldImpl][CtCommentImpl]// The set of Google OAuth scopes to be requested.
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> scopes;

    [CtFieldImpl][CtCommentImpl]// The HttpTransport to be used for making requests to Google's OAuth2 token server. If null,
    [CtCommentImpl]// a default NetHttpTransport instance is used.
    private [CtTypeReferenceImpl]com.google.api.client.http.HttpTransport httpTransport;

    [CtFieldImpl][CtCommentImpl]// The IAM Credentials API client to be used for fetching a signed JWT from Google. If null,
    [CtCommentImpl]// a default API client will be used.
    private [CtTypeReferenceImpl]com.google.cloud.iam.credentials.v1.IamCredentialsClient client;

    [CtConstructorImpl]public DelegatedUserCredentials([CtParameterImpl][CtTypeReferenceImpl]java.lang.String serviceAccountEmail, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userEmail, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> scopes) [CtBlockImpl]{
        [CtInvocationImpl]super();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceAccountEmail = [CtVariableReadImpl]serviceAccountEmail;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.userEmail = [CtVariableReadImpl]userEmail;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scopes = [CtVariableReadImpl]scopes;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.scopes == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scopes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    public [CtTypeReferenceImpl]void setIamCredentialsClient([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.iam.credentials.v1.IamCredentialsClient client) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.client = [CtVariableReadImpl]client;
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    public [CtTypeReferenceImpl]void setHttpTransport([CtParameterImpl][CtTypeReferenceImpl]com.google.api.client.http.HttpTransport httpTransport) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.httpTransport = [CtVariableReadImpl]httpTransport;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates the set of JWT claims representing a service account `serviceAccountEmail` using
     * domain-wide delegation of authority to generate an access token on behalf of a G Suite user,
     * `userEmail`.
     *
     * <p>For reference, see the ServiceAccountCredentials.createAssertion method which builds a
     * similar JWT payload in the context of a JWT being self-signed using a service account's private
     * key.
     *
     * @return  */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    public [CtTypeReferenceImpl]JsonWebToken.Payload createClaims() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.api.client.json.webtoken.JsonWebToken.Payload payload = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.api.client.json.webtoken.JsonWebToken.Payload();
        [CtInvocationImpl][CtVariableReadImpl]payload.setIssuedAtTimeSeconds([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now().getEpochSecond());
        [CtInvocationImpl][CtVariableReadImpl]payload.setExpirationTimeSeconds([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now().getEpochSecond() + [CtInvocationImpl][CtFieldReadImpl]org.pmiops.workbench.auth.DelegatedUserCredentials.ACCESS_TOKEN_DURATION.getSeconds());
        [CtInvocationImpl][CtVariableReadImpl]payload.setAudience([CtTypeAccessImpl]GoogleOAuthConstants.TOKEN_SERVER_URL);
        [CtInvocationImpl][CtVariableReadImpl]payload.setIssuer([CtFieldReadImpl][CtThisAccessImpl]this.serviceAccountEmail);
        [CtInvocationImpl][CtVariableReadImpl]payload.setSubject([CtFieldReadImpl][CtThisAccessImpl]this.userEmail);
        [CtInvocationImpl][CtVariableReadImpl]payload.set([CtLiteralImpl]"scope", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]" ", [CtFieldReadImpl][CtThisAccessImpl]this.scopes));
        [CtReturnImpl]return [CtVariableReadImpl]payload;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.auth.oauth2.AccessToken refreshAccessToken() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The first step is to call the IamCredentials API to generate a signed JWT with the
        [CtCommentImpl]// appropriate claims. This call is authorized with application default credentials (ADCs). The
        [CtCommentImpl]// ADC service account may be different from `serviceAccountEmail` if the ADC account has the
        [CtCommentImpl]// roles/iam.serviceAccountTokenCreator role on the `serviceAccountEmail` account.
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.api.client.json.webtoken.JsonWebToken.Payload payload = [CtInvocationImpl]createClaims();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]client == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]client = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.iam.credentials.v1.IamCredentialsClient.create();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.iam.credentials.v1.SignJwtRequest jwtRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.iam.credentials.v1.SignJwtRequest.newBuilder().setName([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]org.pmiops.workbench.auth.DelegatedUserCredentials.SERVICE_ACCOUNT_NAME_FORMAT, [CtFieldReadImpl]serviceAccountEmail)).setPayload([CtInvocationImpl][CtFieldReadImpl]org.pmiops.workbench.auth.DelegatedUserCredentials.JSON_FACTORY.toString([CtVariableReadImpl]payload)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jwt = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.signJwt([CtVariableReadImpl]jwtRequest).getSignedJwt();
        [CtIfImpl][CtCommentImpl]// With the signed JWT in hand, we call Google's OAuth2 token server to exchange the JWT for
        [CtCommentImpl]// an access token.
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]httpTransport == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]httpTransport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.api.client.http.javanet.NetHttpTransport();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.client.auth.oauth2.TokenRequest tokenRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.api.client.auth.oauth2.TokenRequest([CtFieldReadImpl]httpTransport, [CtFieldReadImpl]org.pmiops.workbench.auth.DelegatedUserCredentials.JSON_FACTORY, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.api.client.http.GenericUrl([CtFieldReadImpl]com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants.TOKEN_SERVER_URL), [CtFieldReadImpl]org.pmiops.workbench.auth.DelegatedUserCredentials.JWT_BEARER_GRANT_TYPE);
        [CtInvocationImpl][CtVariableReadImpl]tokenRequest.put([CtLiteralImpl]"assertion", [CtVariableReadImpl]jwt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.client.auth.oauth2.TokenResponse tokenResponse = [CtInvocationImpl][CtVariableReadImpl]tokenRequest.execute();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.auth.oauth2.AccessToken([CtInvocationImpl][CtVariableReadImpl]tokenResponse.getAccessToken(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Date.from([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now().plusSeconds([CtInvocationImpl][CtVariableReadImpl]tokenResponse.getExpiresInSeconds())));
    }
}
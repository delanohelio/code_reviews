[CompilationUnitImpl][CtPackageDeclarationImpl]package com.microsoft.identity.client.e2e.tests.mocked;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.oauth2.TokenRequest;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.AzureActiveDirectoryOAuth2Configuration;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.robolectric.annotation.Config;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse;
[CtUnresolvedImport]import com.microsoft.identity.client.PublicClientApplicationConfiguration;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenRequest;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandAuthError;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.oauth2.TokenResult;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import com.microsoft.identity.client.exception.MsalException;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.AzureActiveDirectoryOAuth2Strategy;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationResponse;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.authorities.AzureActiveDirectoryAuthority;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.shadows.ShadowMsalUtils;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import com.microsoft.identity.client.IPublicClientApplication;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.utils.RoboTestUtils;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandSuccessful;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.microsoft.identity.client.AuthenticationResult;
[CtUnresolvedImport]import com.microsoft.identity.common.exception.ClientException;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.shadows.ShadowHttpRequestForMockedTest;
[CtUnresolvedImport]import org.robolectric.RobolectricTestRunner;
[CtUnresolvedImport]import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest;
[CtUnresolvedImport]import com.microsoft.identity.common.exception.ErrorStrings;
[CtUnresolvedImport]import static com.microsoft.identity.internal.testutils.TestConstants.Configurations.SINGLE_ACCOUNT_DCF_TEST_CONFIG_FILE_PATH;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandTokenError;
[CtUnresolvedImport]import org.junit.Assert;
[CtUnresolvedImport]import com.microsoft.identity.client.e2e.tests.PublicClientApplicationAbstractTest;
[CtClassImpl][CtJavaDocImpl]/**
 * Testing class for the device code flow protocol. Currently only supporting testing for the API-side
 * of the protocol. Will be extended to test individual aspects of the flow.
 */
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.robolectric.RobolectricTestRunner.class)
[CtAnnotationImpl]@org.robolectric.annotation.Config(shadows = [CtNewArrayImpl]{ [CtFieldReadImpl]com.microsoft.identity.client.e2e.shadows.ShadowMsalUtils.class })
public class DeviceCodeFlowApiTest extends [CtTypeReferenceImpl]com.microsoft.identity.client.e2e.tests.PublicClientApplicationAbstractTest {
    [CtFieldImpl]private [CtTypeReferenceImpl]MicrosoftStsAuthorizationRequest.Builder mBuilder;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mUrlBody;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy mStrategy;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenRequest mTokenRequest;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mUserCodeReceived;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.setup();
        [CtLocalVariableImpl][CtCommentImpl]// getDeviceCode() testing variables
        final [CtTypeReferenceImpl]com.microsoft.identity.client.PublicClientApplicationConfiguration config = [CtInvocationImpl][CtFieldReadImpl]mApplication.getConfiguration();
        [CtAssignmentImpl][CtFieldWriteImpl]mUrlBody = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]com.microsoft.identity.common.internal.authorities.AzureActiveDirectoryAuthority) ([CtInvocationImpl][CtVariableReadImpl]config.getAuthorities().get([CtLiteralImpl]0))).getAudience().getCloudUrl();
        [CtAssignmentImpl][CtFieldWriteImpl]mBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest.Builder();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mBuilder.setClientId([CtInvocationImpl][CtVariableReadImpl]config.getClientId()).setScope([CtLiteralImpl]"user.read").setState([CtLiteralImpl]"State!");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters();
        [CtAssignmentImpl][CtFieldWriteImpl]mStrategy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.AzureActiveDirectoryOAuth2Strategy([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.AzureActiveDirectoryOAuth2Configuration(), [CtVariableReadImpl]options);
        [CtAssignmentImpl][CtCommentImpl]// token request testing variable
        [CtFieldWriteImpl]mTokenRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenRequest();
        [CtInvocationImpl][CtFieldReadImpl]mTokenRequest.setCodeVerifier([CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]mTokenRequest.setCorrelationId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.fromString([CtLiteralImpl]"a-b-c-d-e"));
        [CtInvocationImpl][CtFieldReadImpl]mTokenRequest.setClientId([CtInvocationImpl][CtVariableReadImpl]config.getClientId());
        [CtInvocationImpl][CtFieldReadImpl]mTokenRequest.setGrantType([CtTypeAccessImpl]TokenRequest.GrantTypes.DEVICE_CODE);
        [CtInvocationImpl][CtFieldReadImpl]mTokenRequest.setRedirectUri([CtInvocationImpl][CtVariableReadImpl]config.getRedirectUri());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getConfigFilePath() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]SINGLE_ACCOUNT_DCF_TEST_CONFIG_FILE_PATH;
    }

    [CtMethodImpl][CtCommentImpl]// ===========================================================================================================
    [CtCommentImpl]// getDeviceCode() Testing
    [CtCommentImpl]// ===========================================================================================================
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetDeviceCodeSuccessResult() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest authorizationRequest = [CtInvocationImpl][CtFieldReadImpl]mBuilder.build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult authorizationResult = [CtInvocationImpl][CtFieldReadImpl]mStrategy.getDeviceCode([CtVariableReadImpl]authorizationRequest, [CtFieldReadImpl]mUrlBody);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationResponse authorizationResponse = [CtInvocationImpl](([CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationResponse) ([CtVariableReadImpl]authorizationResult.getAuthorizationResponse()));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]authorizationResponse);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getDeviceCode());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getUserCode());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getMessage());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getInterval());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getExpiresIn());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]authorizationResponse.getVerificationUri());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getAuthorizationErrorResponse());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetDeviceCodeFailureNoClientId() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest authorizationRequest = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mBuilder.setClientId([CtLiteralImpl]null).build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult authorizationResult = [CtInvocationImpl][CtFieldReadImpl]mStrategy.getDeviceCode([CtVariableReadImpl]authorizationRequest, [CtFieldReadImpl]mUrlBody);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse authorizationErrorResponse = [CtInvocationImpl](([CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse) ([CtVariableReadImpl]authorizationResult.getAuthorizationErrorResponse()));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getAuthorizationResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]authorizationErrorResponse);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.INVALID_REQUEST, [CtInvocationImpl][CtVariableReadImpl]authorizationErrorResponse.getError());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetDeviceCodeFailureNoScope() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest authorizationRequest = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mBuilder.setScope([CtLiteralImpl]null).build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult authorizationResult = [CtInvocationImpl][CtFieldReadImpl]mStrategy.getDeviceCode([CtVariableReadImpl]authorizationRequest, [CtFieldReadImpl]mUrlBody);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse authorizationErrorResponse = [CtInvocationImpl](([CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse) ([CtVariableReadImpl]authorizationResult.getAuthorizationErrorResponse()));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getAuthorizationResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]authorizationErrorResponse);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.INVALID_REQUEST, [CtInvocationImpl][CtVariableReadImpl]authorizationErrorResponse.getError());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetDeviceCodeFailureBadScope() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest authorizationRequest = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mBuilder.setScope([CtLiteralImpl]"/").build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult authorizationResult = [CtInvocationImpl][CtFieldReadImpl]mStrategy.getDeviceCode([CtVariableReadImpl]authorizationRequest, [CtFieldReadImpl]mUrlBody);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse authorizationErrorResponse = [CtInvocationImpl](([CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationErrorResponse) ([CtVariableReadImpl]authorizationResult.getAuthorizationErrorResponse()));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]authorizationResult.getAuthorizationResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]authorizationErrorResponse);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.INVALID_SCOPE, [CtInvocationImpl][CtVariableReadImpl]authorizationErrorResponse.getError());
    }

    [CtMethodImpl][CtCommentImpl]// ===========================================================================================================
    [CtCommentImpl]// Token Request Testing
    [CtCommentImpl]// ===========================================================================================================x
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testDeviceCodeFlowTokenInvalidRequest() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]com.microsoft.identity.common.exception.ClientException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration();
        [CtInvocationImpl][CtVariableReadImpl]config.setAuthorityUrl([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtFieldReadImpl]mUrlBody));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy strategy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy([CtVariableReadImpl]config, [CtVariableReadImpl]options);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.TokenResult tokenResult = [CtInvocationImpl][CtVariableReadImpl]strategy.requestToken([CtFieldReadImpl]mTokenRequest);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]tokenResult.getTokenResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]tokenResult.getErrorResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.INVALID_REQUEST, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenResult.getErrorResponse().getError());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testDeviceCodeFlowTokenExpiredToken() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]com.microsoft.identity.common.exception.ClientException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration();
        [CtInvocationImpl][CtVariableReadImpl]config.setAuthorityUrl([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtFieldReadImpl]mUrlBody));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2StrategyParameters();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy strategy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy([CtVariableReadImpl]config, [CtVariableReadImpl]options);
        [CtInvocationImpl][CtCommentImpl]// Previously authenticated code
        [CtFieldReadImpl]mTokenRequest.setDeviceCode([CtBinaryOperatorImpl][CtLiteralImpl]"AAQABAAEAAAAm-06blBE1TpVMil8KPQ41e5vDLI7te0y-3XHYO_uurPryAiyBiPiKnjEVzAQZQzCyGZERne4a" + [CtLiteralImpl]"IwYAiBlQ8an93ENYuVOO-vEAt48FEJSEMQqq-zHZVD59bkc6eYIAViZKVvTv5_qilKj4uEjVE9BGkIxY5B6Uq1K8oWHEqzH-w6CiWjC8vQc6mSV_FPCbnAggAA");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.microsoft.identity.common.internal.providers.oauth2.TokenResult tokenResult = [CtInvocationImpl][CtVariableReadImpl]strategy.requestToken([CtFieldReadImpl]mTokenRequest);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]tokenResult.getTokenResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]tokenResult.getErrorResponse());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.DEVICE_CODE_FLOW_EXPIRED_TOKEN_CODE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenResult.getErrorResponse().getError());
    }

    [CtMethodImpl][CtCommentImpl]// A device code that has not yet been registered leads to invalid_grant, not bad_verification_code
    [CtCommentImpl]// @Test
    [CtCommentImpl]// public void testDeviceCodeFlowTokenBadVerificationCode() throws IOException, ClientException {
    [CtCommentImpl]// final MicrosoftStsOAuth2Configuration config = new MicrosoftStsOAuth2Configuration();
    [CtCommentImpl]// config.setAuthorityUrl(new URL(mUrlBody));
    [CtCommentImpl]// final OAuth2StrategyParameters options = new OAuth2StrategyParameters();
    [CtCommentImpl]// final OAuth2Strategy strategy = new MicrosoftStsOAuth2Strategy(config, options);
    [CtCommentImpl]// mTokenRequest.setDeviceCode(
    [CtCommentImpl]// "AAQABAAEAAAAm-06blBE1TpVMil8KPQ41e5vDLI7te0y-3XHYO_uurPryAiyBiPiKnjEVzAQZQzCyGZERne4a" +
    [CtCommentImpl]// "IwYAiBlQ8an93ENYuVOO-vEAt48FEJSEMQqq-zHZVD59bkc6eYIAViZKVvTv5_qilKj4uEjVE9BGkIxY5B6Uq1K8oWHEqzH-w6CiWjC8vQc6mSV_FPCbnAggBA");
    [CtCommentImpl]// 
    [CtCommentImpl]// final TokenResult tokenResult = strategy.requestToken(mTokenRequest);
    [CtCommentImpl]// Assert.assertNull(tokenResult.getTokenResponse());
    [CtCommentImpl]// Assert.assertNotNull(tokenResult.getErrorResponse());
    [CtCommentImpl]// Assert.assertEquals(ErrorStrings.DEVICE_CODE_FLOW_BAD_VERIFICATION_CODE, tokenResult.getErrorResponse().getError());
    [CtCommentImpl]// }
    [CtCommentImpl]// authorization_declined is triggered in the actual auth side
    [CtCommentImpl]// @Test
    [CtCommentImpl]// public void testDeviceCodeFlowTokenAuthorizationDeclined() throws IOException, ClientException {
    [CtCommentImpl]// final MicrosoftStsOAuth2Configuration config = new MicrosoftStsOAuth2Configuration();
    [CtCommentImpl]// config.setAuthorityUrl(new URL(mUrlBody));
    [CtCommentImpl]// final OAuth2StrategyParameters options = new OAuth2StrategyParameters();
    [CtCommentImpl]// final OAuth2Strategy strategy = new MicrosoftStsOAuth2Strategy(config, options);
    [CtCommentImpl]// 
    [CtCommentImpl]// final TokenResult tokenResult = strategy.requestToken(mTokenRequest);
    [CtCommentImpl]// Assert.assertNull(tokenResult.getTokenResponse());
    [CtCommentImpl]// Assert.assertNotNull(tokenResult.getErrorResponse());
    [CtCommentImpl]// Assert.assertEquals(ErrorStrings.DEVICE_CODE_FLOW_AUTHORIZATION_DECLINED_CODE, tokenResult.getErrorResponse().getError());
    [CtCommentImpl]// }
    [CtCommentImpl]// ===========================================================================================================
    [CtCommentImpl]// API-Side Testing
    [CtCommentImpl]// ===========================================================================================================
    [CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@org.robolectric.annotation.Config(shadows = [CtNewArrayImpl]{ [CtFieldReadImpl]com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandAuthError.class })
    public [CtTypeReferenceImpl]void testDeviceCodeFlowAuthFailure() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] scope = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"user.read" };
        [CtInvocationImpl][CtFieldReadImpl]mApplication.acquireTokenWithDeviceCode([CtVariableReadImpl]scope, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.microsoft.identity.client.IPublicClientApplication.DeviceCodeFlowCallback()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onUserCodeReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String vUri, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String userCode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// This shouldn't run if authorization step fails
                [CtTypeAccessImpl]org.junit.Assert.fail();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onTokenReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.AuthenticationResult authResult) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// This shouldn't run if authorization step fails
                [CtTypeAccessImpl]org.junit.Assert.fail();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.exception.MsalException error) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Handle exception when authorization fails
                [CtTypeAccessImpl]org.junit.Assert.assertFalse([CtFieldReadImpl]mUserCodeReceived);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.INVALID_SCOPE, [CtInvocationImpl][CtVariableReadImpl]error.getErrorCode());
            }
        });
        [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.identity.client.e2e.utils.RoboTestUtils.flushScheduler();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@org.robolectric.annotation.Config(shadows = [CtNewArrayImpl]{ [CtFieldReadImpl]com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandTokenError.class })
    public [CtTypeReferenceImpl]void testDeviceCodeFlowTokenFailure() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] scope = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"user.read" };
        [CtInvocationImpl][CtFieldReadImpl]mApplication.acquireTokenWithDeviceCode([CtVariableReadImpl]scope, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.microsoft.identity.client.IPublicClientApplication.DeviceCodeFlowCallback()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onUserCodeReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String vUri, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String userCode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Assert that the protocol returns the userCode and others after successful authorization
                [CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]vUri);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]userCode);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]message);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtFieldReadImpl]mUserCodeReceived);
                [CtAssignmentImpl][CtFieldWriteImpl]mUserCodeReceived = [CtLiteralImpl]true;
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onTokenReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.AuthenticationResult authResult) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// This shouldn't run
                [CtTypeAccessImpl]org.junit.Assert.fail();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.exception.MsalException error) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Handle Exception
                [CtTypeAccessImpl]org.junit.Assert.assertTrue([CtFieldReadImpl]mUserCodeReceived);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtTypeAccessImpl]ErrorStrings.DEVICE_CODE_FLOW_EXPIRED_TOKEN_CODE, [CtInvocationImpl][CtVariableReadImpl]error.getErrorCode());
            }
        });
        [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.identity.client.e2e.utils.RoboTestUtils.flushScheduler();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@org.robolectric.annotation.Config(shadows = [CtNewArrayImpl]{ [CtFieldReadImpl]com.microsoft.identity.client.e2e.shadows.ShadowDeviceCodeFlowCommandSuccessful.class })
    public [CtTypeReferenceImpl]void testDeviceCodeFlowSuccess() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] scope = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"user.read" };
        [CtInvocationImpl][CtFieldReadImpl]mApplication.acquireTokenWithDeviceCode([CtVariableReadImpl]scope, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.microsoft.identity.client.IPublicClientApplication.DeviceCodeFlowCallback()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onUserCodeReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String vUri, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String userCode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Assert that the protocol returns the userCode and others after successful authorization
                [CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]vUri);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]userCode);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]message);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtFieldReadImpl]mUserCodeReceived);
                [CtAssignmentImpl][CtFieldWriteImpl]mUserCodeReceived = [CtLiteralImpl]true;
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onTokenReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.AuthenticationResult authResult) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtFieldReadImpl]mUserCodeReceived);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]authResult);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
            [CtTypeReferenceImpl]com.microsoft.identity.client.exception.MsalException error) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// This shouldn't run
                [CtTypeAccessImpl]org.junit.Assert.fail();
            }
        });
        [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.identity.client.e2e.utils.RoboTestUtils.flushScheduler();
    }
}
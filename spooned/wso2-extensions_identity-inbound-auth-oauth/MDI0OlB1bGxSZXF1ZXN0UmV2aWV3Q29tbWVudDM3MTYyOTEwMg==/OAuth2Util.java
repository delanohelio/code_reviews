[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

WSO2 Inc. licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file except
in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.wso2.carbon.identity.oauth2.util;
[CtUnresolvedImport]import org.apache.commons.lang.StringUtils;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
[CtImportImpl]import javax.xml.stream.XMLStreamReader;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.nimbusds.jose.JOSEException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.user.UserInfoEndpointException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.IdentityOAuth2ScopeException;
[CtImportImpl]import java.io.FileNotFoundException;
[CtImportImpl]import java.security.NoSuchAlgorithmException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder;
[CtUnresolvedImport]import org.apache.commons.codec.binary.Base64;
[CtUnresolvedImport]import org.wso2.carbon.user.core.service.RealmService;
[CtUnresolvedImport]import com.nimbusds.jose.crypto.RSASSAVerifier;
[CtUnresolvedImport]import com.nimbusds.jwt.JWT;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.ScopeDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
[CtImportImpl]import java.security.MessageDigest;
[CtUnresolvedImport]import org.wso2.carbon.registry.core.exceptions.RegistryException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.tokenprocessor.TokenPersistenceProcessor;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.JWTTokenIssuer;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityIOStreamUtils;
[CtUnresolvedImport]import com.nimbusds.jose.EncryptionMethod;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.config.SpOAuth2ExpiryTimeConfiguration;
[CtUnresolvedImport]import com.nimbusds.jose.JWSAlgorithm;
[CtUnresolvedImport]import org.wso2.carbon.user.core.UserCoreConstants;
[CtUnresolvedImport]import com.nimbusds.jwt.SignedJWT;
[CtImportImpl]import java.security.cert.X509Certificate;
[CtImportImpl]import java.io.FileInputStream;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
[CtUnresolvedImport]import com.nimbusds.jose.JWSSigner;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.AppInfoCache;
[CtUnresolvedImport]import org.wso2.carbon.utils.multitenancy.MultitenantUtils;
[CtUnresolvedImport]import com.nimbusds.jose.jwk.KeyUse;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dto.OAuthRevocationRequestDTO;
[CtUnresolvedImport]import org.apache.commons.logging.LogFactory;
[CtImportImpl]import org.json.JSONObject;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.wso2.carbon.identity.base.IdentityException;
[CtUnresolvedImport]import com.nimbusds.jose.JWEEncrypter;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth2.Oauth2ScopeConstants.PERMISSIONS_BINDING_TYPE;
[CtUnresolvedImport]import org.wso2.carbon.identity.openidconnect.model.RequestedClaim;
[CtImportImpl]import java.security.Key;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.mgt.ApplicationManagementService;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.bindings.TokenBinding;
[CtUnresolvedImport]import com.nimbusds.jwt.JWTClaimsSet;
[CtImportImpl]import java.security.cert.CertificateFactory;
[CtImportImpl]import java.net.URI;
[CtImportImpl]import java.sql.Timestamp;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityConfigParser;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
[CtUnresolvedImport]import org.apache.axiom.util.base64.Base64Utils;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.bean.ScopeBinding;
[CtUnresolvedImport]import org.wso2.carbon.user.core.util.UserCoreUtil;
[CtUnresolvedImport]import org.wso2.carbon.registry.core.Resource;
[CtImportImpl]import java.nio.file.Paths;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.bean.OAuthClientAuthnContext;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.tokenprocessor.PlainTextPersistenceProcessor;
[CtImportImpl]import org.json.JSONException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dao.OAuthAppDAO;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.security.cert.CertificateEncodingException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.CacheEntry;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.util.IdentityApplicationManagementUtil;
[CtImportImpl]import java.util.TreeMap;
[CtUnresolvedImport]import com.nimbusds.jose.jwk.RSAKey;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder;
[CtUnresolvedImport]import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dto.OAuth2IntrospectionResponseDTO;
[CtImportImpl]import java.text.ParseException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.bean.Scope;
[CtUnresolvedImport]import org.wso2.carbon.utils.multitenancy.MultitenantConstants;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationRequestDTO;
[CtUnresolvedImport]import com.nimbusds.jose.jwk.JWKSet;
[CtImportImpl]import java.security.cert.CertificateException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
[CtUnresolvedImport]import com.nimbusds.jose.crypto.RSAEncrypter;
[CtImportImpl]import java.security.interfaces.RSAPublicKey;
[CtUnresolvedImport]import com.nimbusds.jose.JWEAlgorithm;
[CtUnresolvedImport]import com.nimbusds.jose.util.Base64URL;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationResponseDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.IdentityOAuth2ScopeServerException;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.IdentityApplicationManagementException;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.ServiceProvider;
[CtUnresolvedImport]import com.nimbusds.jose.JWSHeader;
[CtUnresolvedImport]import com.nimbusds.jose.jwk.JWK;
[CtImportImpl]import java.security.cert.Certificate;
[CtImportImpl]import org.apache.commons.io.Charsets;
[CtUnresolvedImport]import com.nimbusds.jose.crypto.RSASSASigner;
[CtUnresolvedImport]import org.wso2.carbon.identity.base.IdentityConstants;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.security.interfaces.RSAPrivateKey;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.ServiceProviderProperty;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dao.OAuthAppDO;
[CtUnresolvedImport]import org.wso2.carbon.user.api.UserStoreException;
[CtImportImpl]import java.net.URISyntaxException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer;
[CtUnresolvedImport]import org.wso2.carbon.utils.CarbonUtils;
[CtUnresolvedImport]import org.apache.commons.collections.CollectionUtils;
[CtUnresolvedImport]import org.wso2.carbon.core.util.KeyStoreManager;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.wso2.carbon.identity.openidconnect.model.Constants;
[CtUnresolvedImport]import org.apache.axiom.om.OMElement;
[CtUnresolvedImport]import com.nimbusds.jose.JWSVerifier;
[CtUnresolvedImport]import com.nimbusds.jose.JWEHeader;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.common.OAuthConstants;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import javax.xml.namespace.QName;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.handlers.grant.AuthorizationGrantHandler;
[CtUnresolvedImport]import org.wso2.carbon.idp.mgt.IdentityProviderManager;
[CtImportImpl]import java.security.KeyStore;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.model.ClientCredentialDO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.OAuthCache;
[CtImportImpl]import javax.xml.stream.XMLInputFactory;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.token.bindings.TokenBinder;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.IdentityProvider;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityCoreConstants;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
[CtUnresolvedImport]import org.wso2.carbon.registry.core.Registry;
[CtImportImpl]import javax.xml.stream.XMLStreamException;
[CtUnresolvedImport]import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
[CtUnresolvedImport]import org.apache.axiom.om.impl.builder.StAXOMBuilder;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityUtil;
[CtUnresolvedImport]import org.apache.commons.logging.Log;
[CtUnresolvedImport]import com.nimbusds.jwt.EncryptedJWT;
[CtUnresolvedImport]import org.apache.commons.codec.digest.DigestUtils;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dao.OAuthConsumerDAO;
[CtUnresolvedImport]import com.nimbusds.jose.Algorithm;
[CtClassImpl][CtJavaDocImpl]/**
 * Utility methods for OAuth 2.0 implementation
 */
public class OAuth2Util {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String REMOTE_ACCESS_TOKEN = [CtLiteralImpl]"REMOTE_ACCESS_TOKEN";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String JWT_ACCESS_TOKEN = [CtLiteralImpl]"JWT_ACCESS_TOKEN";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ACCESS_TOKEN_DO = [CtLiteralImpl]"AccessTokenDo";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String OAUTH2_VALIDATION_MESSAGE_CONTEXT = [CtLiteralImpl]"OAuth2TokenValidationMessageContext";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String CONFIG_ELEM_OAUTH = [CtLiteralImpl]"OAuth";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String OPENID_CONNECT = [CtLiteralImpl]"OpenIDConnect";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ENABLE_OPENID_CONNECT_AUDIENCES = [CtLiteralImpl]"EnableAudiences";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String OPENID_CONNECT_AUDIENCE = [CtLiteralImpl]"audience";

    [CtFieldImpl][CtCommentImpl]/* Maintain a separate parameter "OPENID_CONNECT_AUDIENCE_IDENTITY_CONFIG" to get the audience from the identity.xml
    when user didn't add any audience in the UI while creating service provider.
     */
    public static final [CtTypeReferenceImpl]java.lang.String OPENID_CONNECT_AUDIENCE_IDENTITY_CONFIG = [CtLiteralImpl]"Audience";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OPENID_CONNECT_AUDIENCES = [CtLiteralImpl]"Audiences";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DOT_SEPARATER = [CtLiteralImpl]".";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String IDP_ENTITY_ID = [CtLiteralImpl]"IdPEntityId";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String DEFAULT_TOKEN_TYPE = [CtLiteralImpl]"Default";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. A JSON string containing a space-separated list of scopes associated with this token, in the format
    described in Section 3.3 of OAuth 2.0
     */
    public static final [CtTypeReferenceImpl]java.lang.String SCOPE = [CtLiteralImpl]"scope";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Client identifier for the OAuth 2.0 client that requested this token. */
    public static final [CtTypeReferenceImpl]java.lang.String CLIENT_ID = [CtLiteralImpl]"client_id";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Human-readable identifier for the resource owner who authorized this token. */
    public static final [CtTypeReferenceImpl]java.lang.String USERNAME = [CtLiteralImpl]"username";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Type of the token as defined in Section 5.1 of OAuth 2.0 */
    public static final [CtTypeReferenceImpl]java.lang.String TOKEN_TYPE = [CtLiteralImpl]"token_type";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Integer time-stamp, measured in the number of seconds since January 1 1970 UTC, indicating when this
    token is not to be used before, as defined in JWT
     */
    public static final [CtTypeReferenceImpl]java.lang.String NBF = [CtLiteralImpl]"nbf";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Service-specific string identifier or list of string identifiers representing the intended audience for
    this token, as defined in JWT
     */
    public static final [CtTypeReferenceImpl]java.lang.String AUD = [CtLiteralImpl]"aud";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. String representing the issuer of this token, as defined in JWT */
    public static final [CtTypeReferenceImpl]java.lang.String ISS = [CtLiteralImpl]"iss";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. String identifier for the token, as defined in JWT */
    public static final [CtTypeReferenceImpl]java.lang.String JTI = [CtLiteralImpl]"jti";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Subject of the token, as defined in JWT [RFC7519]. Usually a machine-readable identifier of the
    resource owner who authorized this token.
     */
    public static final [CtTypeReferenceImpl]java.lang.String SUB = [CtLiteralImpl]"sub";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Integer time-stamp, measured in the number of seconds since January 1 1970 UTC, indicating when this
    token will expire, as defined in JWT
     */
    public static final [CtTypeReferenceImpl]java.lang.String EXP = [CtLiteralImpl]"exp";

    [CtFieldImpl][CtCommentImpl]/* OPTIONAL. Integer time-stamp, measured in the number of seconds since January 1 1970 UTC, indicating when this
    token was originally issued, as defined in JWT
     */
    public static final [CtTypeReferenceImpl]java.lang.String IAT = [CtLiteralImpl]"iat";

    [CtFieldImpl][CtJavaDocImpl]/**
     * *
     * Constant for user access token expiry time.
     */
    public static final [CtTypeReferenceImpl]java.lang.String USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS = [CtLiteralImpl]"userAccessTokenExpireTime";

    [CtFieldImpl][CtJavaDocImpl]/**
     * *
     * Constant for refresh token expiry time.
     */
    public static final [CtTypeReferenceImpl]java.lang.String REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS = [CtLiteralImpl]"refreshTokenExpireTime";

    [CtFieldImpl][CtJavaDocImpl]/**
     * *
     * Constant for application access token expiry time.
     */
    public static final [CtTypeReferenceImpl]java.lang.String APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS = [CtLiteralImpl]"applicationAccessTokenExpireTime";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.commons.logging.Log log = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.logging.LogFactory.getLog([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERNAL_LOGIN_SCOPE = [CtLiteralImpl]"internal_login";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String IDENTITY_PATH = [CtLiteralImpl]"identity";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"name";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DISPLAY_NAME = [CtLiteralImpl]"displayName";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DESCRIPTION = [CtLiteralImpl]"description";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PERMISSION = [CtLiteralImpl]"Permission";

    [CtFieldImpl]private static [CtTypeReferenceImpl]long timestampSkew = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds() * [CtLiteralImpl]1000;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]java.lang.Integer> clientTenantId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal<>();

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext> tokenRequestContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal<>();

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext> authzRequestContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal<>();

    [CtFieldImpl][CtCommentImpl]// Precompile PKCE Regex pattern for performance improvement
    private static [CtTypeReferenceImpl]java.util.regex.Pattern pkceCodeVerifierPattern = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"[\\w\\-\\._~]+");

    [CtFieldImpl][CtCommentImpl]// System flag to allow the weak keys (key length less than 2048) to be used for the signing.
    private static final [CtTypeReferenceImpl]java.lang.String ALLOW_WEAK_RSA_SIGNER_KEY = [CtLiteralImpl]"allow_weak_rsa_signer_key";

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.security.cert.Certificate> publicCerts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.security.cert.Certificate>();

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.security.Key> privateKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.security.Key>();

    [CtFieldImpl][CtCommentImpl]// Supported Signature Algorithms
    private static final [CtTypeReferenceImpl]java.lang.String NONE = [CtLiteralImpl]"NONE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA256_WITH_RSA = [CtLiteralImpl]"SHA256withRSA";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA384_WITH_RSA = [CtLiteralImpl]"SHA384withRSA";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA512_WITH_RSA = [CtLiteralImpl]"SHA512withRSA";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA256_WITH_HMAC = [CtLiteralImpl]"SHA256withHMAC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA384_WITH_HMAC = [CtLiteralImpl]"SHA384withHMAC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA512_WITH_HMAC = [CtLiteralImpl]"SHA512withHMAC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA256_WITH_EC = [CtLiteralImpl]"SHA256withEC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA384_WITH_EC = [CtLiteralImpl]"SHA384withEC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA512_WITH_EC = [CtLiteralImpl]"SHA512withEC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA256_WITH_PS = [CtLiteralImpl]"SHA256withPS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA256 = [CtLiteralImpl]"SHA-256";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA384 = [CtLiteralImpl]"SHA-384";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHA512 = [CtLiteralImpl]"SHA-512";

    [CtFieldImpl][CtCommentImpl]// Supported Client Authentication Methods
    private static final [CtTypeReferenceImpl]java.lang.String CLIENT_SECRET_BASIC = [CtLiteralImpl]"client_secret_basic";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CLIENT_SECRET_POST = [CtLiteralImpl]"client_secret_post";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PRIVATE_KEY_JWT = [CtLiteralImpl]"private_key_jwt";

    [CtConstructorImpl]private OAuth2Util() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return  */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext getAuthzRequestContext() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Retreived OAuthAuthzReqMessageContext from threadlocal");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.authzRequestContext.get();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param context
     */
    public static [CtTypeReferenceImpl]void setAuthzRequestContext([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext context) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.authzRequestContext.set([CtVariableReadImpl]context);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Added OAuthAuthzReqMessageContext to threadlocal");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    public static [CtTypeReferenceImpl]void clearAuthzRequestContext() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.authzRequestContext.remove();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Cleared OAuthAuthzReqMessageContext");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return  */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext getTokenRequestContext() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Retreived OAuthTokenReqMessageContext from threadlocal");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.tokenRequestContext.get();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param context
     */
    public static [CtTypeReferenceImpl]void setTokenRequestContext([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext context) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.tokenRequestContext.set([CtVariableReadImpl]context);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Added OAuthTokenReqMessageContext to threadlocal");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    public static [CtTypeReferenceImpl]void clearTokenRequestContext() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.tokenRequestContext.remove();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Cleared OAuthTokenReqMessageContext");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return  */
    public static [CtTypeReferenceImpl]int getClientTenatId() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.clientTenantId.get() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.clientTenantId.get();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param tenantId
     */
    public static [CtTypeReferenceImpl]void setClientTenatId([CtParameterImpl][CtTypeReferenceImpl]int tenantId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer id = [CtVariableReadImpl]tenantId;
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.clientTenantId.set([CtVariableReadImpl]id);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    public static [CtTypeReferenceImpl]void clearClientTenantId() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.clientTenantId.remove();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build a comma separated list of scopes passed as a String set by OLTU.
     *
     * @param scopes
     * 		set of scopes
     * @return Comma separated list of scopes
     */
    public static [CtTypeReferenceImpl]java.lang.String buildScopeString([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] scopes) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]scopes != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.sort([CtVariableReadImpl]scopes);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.join([CtVariableReadImpl]scopes, [CtLiteralImpl]" ");
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param scopeStr
     * @return  */
    public static [CtArrayTypeReferenceImpl]java.lang.String[] buildScopeArray([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scopeStr) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]scopeStr)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]scopeStr = [CtInvocationImpl][CtVariableReadImpl]scopeStr.trim();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]scopeStr.split([CtLiteralImpl]"\\s");
        }
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0];
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Authenticate the OAuth Consumer
     *
     * @param clientId
     * 		Consumer Key/Id
     * @param clientSecretProvided
     * 		Consumer Secret issued during the time of registration
     * @return true, if the authentication is successful, false otherwise.
     * @throws IdentityOAuthAdminException
     * 		Error when looking up the credentials from the database
     */
    public static [CtTypeReferenceImpl]boolean authenticateClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientSecretProvided) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO appDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAppInformationByClientId([CtVariableReadImpl]clientId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]appDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find a valid application with the provided client_id: " + [CtVariableReadImpl]clientId);
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Cache miss
        [CtTypeReferenceImpl]boolean isHashDisabled = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isHashDisabled();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String appClientSecret = [CtInvocationImpl][CtVariableReadImpl]appDO.getOauthConsumerSecret();
        [CtIfImpl]if ([CtVariableReadImpl]isHashDisabled) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.equals([CtVariableReadImpl]appClientSecret, [CtVariableReadImpl]clientSecretProvided)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided the Client ID : " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" and Client Secret do not match with the issued credentials.");
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.tokenprocessor.TokenPersistenceProcessor persistenceProcessor = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPersistenceProcessor();
            [CtLocalVariableImpl][CtCommentImpl]// We convert the provided client_secret to the processed form stored in the DB.
            [CtTypeReferenceImpl]java.lang.String processedProvidedClientSecret = [CtInvocationImpl][CtVariableReadImpl]persistenceProcessor.getProcessedClientSecret([CtVariableReadImpl]clientSecretProvided);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.equals([CtVariableReadImpl]appClientSecret, [CtVariableReadImpl]processedProvidedClientSecret)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided the Client ID : " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" and Client Secret do not match with the issued credentials.");
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Successfully authenticated the client with client id : " + [CtVariableReadImpl]clientId);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.tokenprocessor.TokenPersistenceProcessor getPersistenceProcessor() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.tokenprocessor.TokenPersistenceProcessor persistenceProcessor;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]persistenceProcessor = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getPersistenceProcessor();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"Error retrieving TokenPersistenceProcessor configured in OAuth.TokenPersistenceProcessor " + [CtLiteralImpl]"in identity.xml. Defaulting to PlainTextPersistenceProcessor.";
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtVariableReadImpl]msg);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtVariableReadImpl]msg, [CtVariableReadImpl]e);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]persistenceProcessor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.tokenprocessor.PlainTextPersistenceProcessor();
        }
        [CtReturnImpl]return [CtVariableReadImpl]persistenceProcessor;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether hashing oauth keys (consumer secret, access token, refresh token and authorization code)
     * configuration is disabled or not in identity.xml file.
     *
     * @return Whether hash feature is disabled or not.
     */
    public static [CtTypeReferenceImpl]boolean isHashDisabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isHashEnabled = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isClientSecretHashEnabled();
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtVariableReadImpl]isHashEnabled;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether hashing oauth keys (consumer secret, access token, refresh token and authorization code)
     * configuration is enabled or not in identity.xml file.
     *
     * @return Whether hash feature is enable or not.
     */
    public static [CtTypeReferenceImpl]boolean isHashEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isHashEnabled = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isClientSecretHashEnabled();
        [CtReturnImpl]return [CtVariableReadImpl]isHashEnabled;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param clientId
     * 		Consumer Key/Id
     * @param clientSecretProvided
     * 		Consumer Secret issued during the time of registration
     * @return Username of the user which own client id and client secret if authentication is
    successful. Empty string otherwise.
     * @throws IdentityOAuthAdminException
     * 		Error when looking up the credentials from the database
     * @deprecated Authenticate the OAuth consumer and return the username of user which own the provided client id
    and client secret.
     */
    public static [CtTypeReferenceImpl]java.lang.String getAuthenticatedUsername([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientSecretProvided) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean cacheHit = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String username = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isUsernameCaseSensitive = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isUserStoreInUsernameCaseSensitive([CtVariableReadImpl]username);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.authenticateClient([CtVariableReadImpl]clientId, [CtVariableReadImpl]clientSecretProvided)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.CacheEntry cacheResult = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().getValueFromCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]username));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cacheResult != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]cacheResult instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.ClientCredentialDO)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Ugh. This is fugly. Have to have a generic way of caching a key:value pair
                [CtVariableWriteImpl]username = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.ClientCredentialDO) (cacheResult)).getClientSecret();
                [CtAssignmentImpl][CtVariableWriteImpl]cacheHit = [CtLiteralImpl]true;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Username was available in the cache : " + [CtVariableReadImpl]username);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]username == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Cache miss
                [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthConsumerDAO oAuthConsumerDAO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthConsumerDAO();
                [CtAssignmentImpl][CtVariableWriteImpl]username = [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerDAO.getAuthenticatedUsername([CtVariableReadImpl]clientId, [CtVariableReadImpl]clientSecretProvided);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Username fetch from the database");
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]username != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtVariableReadImpl]cacheHit)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* Using the same ClientCredentialDO to host username. Semantically wrong since ClientCredentialDo
                accept a client secret and we're storing a username in the secret variable. Do we have to make our
                own cache key and cache entry class every time we need to put something to it? Ideal solution is
                to have a generalized way of caching a key:value pair
                 */
                if ([CtVariableReadImpl]isUsernameCaseSensitive) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().addToCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]username), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.ClientCredentialDO([CtVariableReadImpl]username));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().addToCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]username.toLowerCase()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.ClientCredentialDO([CtVariableReadImpl]username));
                }
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Caching username : " + [CtVariableReadImpl]username);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]username;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build the cache key string when storing Authz Code info in cache
     *
     * @param clientId
     * 		Client Id representing the client
     * @param authzCode
     * 		Authorization Code issued to the client
     * @return concatenated <code>String</code> of clientId:authzCode
     */
    public static [CtTypeReferenceImpl]java.lang.String buildCacheKeyStringForAuthzCode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authzCode) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]authzCode;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build the cache key string when storing token info in cache
     *
     * @param clientId
     * @param scope
     * @param authorizedUser
     * @return  * @deprecated To make the cache key completely unique the authenticated IDP should also be introduced.
    Use {@link #buildCacheKeyStringForToken(String, String, String, String)} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]java.lang.String buildCacheKeyStringForToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authorizedUser) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isUsernameCaseSensitive = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isUserStoreInUsernameCaseSensitive([CtVariableReadImpl]authorizedUser);
        [CtIfImpl]if ([CtVariableReadImpl]isUsernameCaseSensitive) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]authorizedUser) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]authorizedUser.toLowerCase()) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build the cache key string when storing token info in cache.
     *
     * @param clientId
     * 		ClientId of the App.
     * @param scope
     * 		Scopes used.
     * @param authorizedUser
     * 		Authorised user.
     * @param authenticatedIDP
     * 		Authenticated IdP.
     * @return Cache key string combining the input parameters.
     * @deprecated use {@link #buildCacheKeyStringForToken(String, String, String, String, String)} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]java.lang.String buildCacheKeyStringForToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authorizedUser, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authenticatedIDP) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isUsernameCaseSensitive = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isUserStoreInUsernameCaseSensitive([CtVariableReadImpl]authorizedUser);
        [CtIfImpl]if ([CtVariableReadImpl]isUsernameCaseSensitive) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]authorizedUser) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]authorizedUser.toLowerCase()) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build the cache key string when storing token info in cache.
     *
     * @param clientId
     * 		ClientId of the App.
     * @param scope
     * 		Scopes used.
     * @param authorizedUser
     * 		Authorised user.
     * @param authenticatedIDP
     * 		Authenticated IdP.
     * @param tokenBindingReference
     * 		Token binding reference.
     * @return Cache key string combining the input parameters.
     */
    public static [CtTypeReferenceImpl]java.lang.String buildCacheKeyStringForToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authorizedUser, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authenticatedIDP, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenBindingReference) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isUsernameCaseSensitive = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isUserStoreInUsernameCaseSensitive([CtVariableReadImpl]authorizedUser);
        [CtIfImpl]if ([CtVariableReadImpl]isUsernameCaseSensitive) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]authorizedUser) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP) + [CtLiteralImpl]":") + [CtVariableReadImpl]tokenBindingReference;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]authorizedUser.toLowerCase()) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP) + [CtLiteralImpl]":") + [CtVariableReadImpl]tokenBindingReference;
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getTokenBindingReference([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenBindingValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]tokenBindingValue)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.digest.DigestUtils.md5Hex([CtVariableReadImpl]tokenBindingValue);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO validateAccessTokenDO([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long validityPeriodMillis = [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getValidityPeriodInMillis();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long issuedTime = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getIssuedTime().getTime();
        [CtLocalVariableImpl][CtCommentImpl]// check the validity of cached OAuth2AccessToken Response
        [CtTypeReferenceImpl]long accessTokenValidityMillis = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTimeToExpire([CtVariableReadImpl]issuedTime, [CtVariableReadImpl]validityPeriodMillis);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenValidityMillis > [CtLiteralImpl]1000) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshValidityPeriodMillis = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getRefreshTokenValidityPeriodInSeconds() * [CtLiteralImpl]1000;
            [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshTokenValidityMillis = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTimeToExpire([CtVariableReadImpl]issuedTime, [CtVariableReadImpl]refreshValidityPeriodMillis);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]refreshTokenValidityMillis > [CtLiteralImpl]1000) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Set new validity period to response object
                [CtVariableReadImpl]accessTokenDO.setValidityPeriodInMillis([CtVariableReadImpl]accessTokenValidityMillis);
                [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.setRefreshTokenValidityPeriodInMillis([CtVariableReadImpl]refreshTokenValidityMillis);
                [CtInvocationImpl][CtCommentImpl]// Set issued time period to response object
                [CtVariableReadImpl]accessTokenDO.setIssuedTime([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.Timestamp([CtVariableReadImpl]issuedTime));
                [CtReturnImpl]return [CtVariableReadImpl]accessTokenDO;
            }
        }
        [CtReturnImpl][CtCommentImpl]// returns null if cached OAuth2AccessToken response object is expired
        return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean checkAccessTokenPartitioningEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isAccessTokenPartitioningEnabled();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean checkUserNameAssertionEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isUserNameAssertionEnabled();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getAccessTokenPartitioningDomains() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getAccessTokenPartitioningDomains();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getAvailableUserStoreDomainMappings() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// TreeMap is used to ignore the case sensitivity of key. Because when user logged in, the case of the
        [CtCommentImpl]// username is ignored.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> userStoreDomainMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>([CtFieldReadImpl][CtTypeAccessImpl]java.lang.String.[CtFieldReferenceImpl]CASE_INSENSITIVE_ORDER);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String domainsStr = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenPartitioningDomains();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]domainsStr != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] userStoreDomainsArr = [CtInvocationImpl][CtVariableReadImpl]domainsStr.split([CtLiteralImpl]",");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomains : [CtVariableReadImpl]userStoreDomainsArr) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] mapping = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]userStoreDomains.trim().split([CtLiteralImpl]":");[CtCommentImpl]// A:foo.com , B:bar.com

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]mapping.length < [CtLiteralImpl]2) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Domain mapping has not defined correctly");
                }
                [CtInvocationImpl][CtVariableReadImpl]userStoreDomainMap.put([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]mapping[[CtLiteralImpl]1].trim(), [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]mapping[[CtLiteralImpl]0].trim());[CtCommentImpl]// key=domain & value=mapping

            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]userStoreDomainMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the mapped user store if a mapping is defined for this user store in AccessTokenPartitioningDomains
     * element in identity.xml, or the original userstore domain if the mapping is not available.
     *
     * @param userStoreDomain
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getMappedUserStoreDomain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mappedUserStoreDomain = [CtVariableReadImpl]userStoreDomain;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> availableDomainMappings = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAvailableUserStoreDomainMappings();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]userStoreDomain != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]availableDomainMappings.containsKey([CtVariableReadImpl]userStoreDomain)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mappedUserStoreDomain = [CtInvocationImpl][CtVariableReadImpl]availableDomainMappings.get([CtVariableReadImpl]userStoreDomain);
        }
        [CtReturnImpl]return [CtVariableReadImpl]mappedUserStoreDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the updated table name using user store domain if a mapping is defined for this users store in
     * AccessTokenPartitioningDomains element in identity.xml,
     * or the original table name if the mapping is not available.
     * <p>
     * Updated table name derived by appending a underscore and mapped user store domain name to the origin table name.
     *
     * @param userStoreDomain
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getPartitionedTableByUserStore([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]tableName) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]userStoreDomain)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getPrimaryDomainName().equalsIgnoreCase([CtVariableReadImpl]userStoreDomain))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mappedUserStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getMappedUserStoreDomain([CtVariableReadImpl]userStoreDomain);
            [CtAssignmentImpl][CtVariableWriteImpl]tableName = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tableName + [CtLiteralImpl]"_") + [CtVariableReadImpl]mappedUserStoreDomain;
        }
        [CtReturnImpl]return [CtVariableReadImpl]tableName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the updated sql using user store domain if access token partitioning enabled & username assertion enabled
     * or the original sql otherwise.
     * <p>
     * Updated sql derived by replacing original table names IDN_OAUTH2_ACCESS_TOKEN & IDN_OAUTH2_ACCESS_TOKEN_SCOPE
     * with the updated table names which derived using {@code getPartitionedTableByUserStore()} method.
     *
     * @param sql
     * @param userStoreDomain
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getTokenPartitionedSqlByUserStore([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partitionedSql = [CtVariableReadImpl]sql;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkAccessTokenPartitioningEnabled() && [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkUserNameAssertionEnabled()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partitionedAccessTokenTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPartitionedTableByUserStore([CtTypeAccessImpl]OAuthConstants.ACCESS_TOKEN_STORE_TABLE, [CtVariableReadImpl]userStoreDomain);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accessTokenScopeTable = [CtLiteralImpl]"IDN_OAUTH2_ACCESS_TOKEN_SCOPE";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partitionedAccessTokenScopeTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPartitionedTableByUserStore([CtVariableReadImpl]accessTokenScopeTable, [CtVariableReadImpl]userStoreDomain);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"PartitionedAccessTokenTable: " + [CtVariableReadImpl]partitionedAccessTokenTable) + [CtLiteralImpl]" & PartitionedAccessTokenScopeTable: ") + [CtVariableReadImpl]partitionedAccessTokenScopeTable) + [CtLiteralImpl]" for user store domain: ") + [CtVariableReadImpl]userStoreDomain);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wordBoundaryRegex = [CtLiteralImpl]"\\b";
            [CtAssignmentImpl][CtVariableWriteImpl]partitionedSql = [CtInvocationImpl][CtVariableReadImpl]sql.replaceAll([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]wordBoundaryRegex + [CtFieldReadImpl]org.wso2.carbon.identity.oauth.common.OAuthConstants.ACCESS_TOKEN_STORE_TABLE) + [CtVariableReadImpl]wordBoundaryRegex, [CtVariableReadImpl]partitionedAccessTokenTable);
            [CtAssignmentImpl][CtVariableWriteImpl]partitionedSql = [CtInvocationImpl][CtVariableReadImpl]partitionedSql.replaceAll([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]wordBoundaryRegex + [CtVariableReadImpl]accessTokenScopeTable) + [CtVariableReadImpl]wordBoundaryRegex, [CtVariableReadImpl]partitionedAccessTokenScopeTable);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Original SQL: " + [CtVariableReadImpl]sql);
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Partitioned SQL: " + [CtVariableReadImpl]partitionedSql);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]partitionedSql;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the updated sql using username.
     * <p>
     * If the username contains the domain separator, updated sql derived using
     * {@code getTokenPartitionedSqlByUserStore()} method. Returns the original sql otherwise.
     *
     * @param sql
     * @param username
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getTokenPartitionedSqlByUserId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String username) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partitionedSql = [CtVariableReadImpl]sql;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkAccessTokenPartitioningEnabled() && [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkUserNameAssertionEnabled()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Calculating partitioned sql for username: " + [CtVariableReadImpl]username);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStore = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]username != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] strArr = [CtInvocationImpl][CtVariableReadImpl]username.split([CtTypeAccessImpl]UserCoreConstants.DOMAIN_SEPARATOR);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]strArr != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]strArr.length > [CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]userStore = [CtArrayReadImpl][CtVariableReadImpl]strArr[[CtLiteralImpl]0];
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]partitionedSql = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTokenPartitionedSqlByUserStore([CtVariableReadImpl]sql, [CtVariableReadImpl]userStore);
        }
        [CtReturnImpl]return [CtVariableReadImpl]partitionedSql;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the updated sql using token.
     * <p>
     * If the token contains the username appended, updated sql derived using
     * {@code getTokenPartitionedSqlByUserId()} method. Returns the original sql otherwise.
     *
     * @param sql
     * @param token
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getTokenPartitionedSqlByToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String token) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partitionedSql = [CtVariableReadImpl]sql;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkAccessTokenPartitioningEnabled() && [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkUserNameAssertionEnabled()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Calculating partitioned sql for token: " + [CtVariableReadImpl]token);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Avoid logging token since its a sensitive information.
                    [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Calculating partitioned sql for token");
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserIdFromAccessToken([CtVariableReadImpl]token);[CtCommentImpl]// i.e: 'foo.com/admin' or 'admin'

            [CtAssignmentImpl][CtVariableWriteImpl]partitionedSql = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTokenPartitionedSqlByUserId([CtVariableReadImpl]sql, [CtVariableReadImpl]userId);
        }
        [CtReturnImpl]return [CtVariableReadImpl]partitionedSql;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getUserStoreDomainFromUserId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] strArr = [CtInvocationImpl][CtVariableReadImpl]userId.split([CtTypeAccessImpl]UserCoreConstants.DOMAIN_SEPARATOR);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]strArr != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]strArr.length > [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getMappedUserStoreDomain([CtArrayReadImpl][CtVariableReadImpl]strArr[[CtLiteralImpl]0]);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]userStoreDomain;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getUserStoreDomainFromAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String apiKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String decodedKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Base64.decodeBase64([CtInvocationImpl][CtVariableReadImpl]apiKey.getBytes([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8)), [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] tmpArr = [CtInvocationImpl][CtVariableReadImpl]decodedKey.split([CtLiteralImpl]":");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tmpArr != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userId = [CtArrayReadImpl][CtVariableReadImpl]tmpArr[[CtLiteralImpl]1];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userId != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserStoreDomainFromUserId([CtVariableReadImpl]userId);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]userStoreDomain;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]java.lang.String getAccessTokenStoreTableFromUserId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accessTokenStoreTable = [CtFieldReadImpl]org.wso2.carbon.identity.oauth.common.OAuthConstants.ACCESS_TOKEN_STORE_TABLE;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStore;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] strArr = [CtInvocationImpl][CtVariableReadImpl]userId.split([CtTypeAccessImpl]UserCoreConstants.DOMAIN_SEPARATOR);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]strArr.length > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]userStore = [CtArrayReadImpl][CtVariableReadImpl]strArr[[CtLiteralImpl]0];
                [CtAssignmentImpl][CtVariableWriteImpl]accessTokenStoreTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPartitionedTableByUserStore([CtTypeAccessImpl]OAuthConstants.ACCESS_TOKEN_STORE_TABLE, [CtVariableReadImpl]userStore);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]accessTokenStoreTable;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]java.lang.String getAccessTokenStoreTableFromAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String apiKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserIdFromAccessToken([CtVariableReadImpl]apiKey);[CtCommentImpl]// i.e: 'foo.com/admin' or 'admin'

        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenStoreTableFromUserId([CtVariableReadImpl]userId);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getUserIdFromAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String apiKey) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String decodedKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Base64.decodeBase64([CtInvocationImpl][CtVariableReadImpl]apiKey.getBytes([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8)), [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] tmpArr = [CtInvocationImpl][CtVariableReadImpl]decodedKey.split([CtLiteralImpl]":");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tmpArr != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]tmpArr.length > [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userId = [CtArrayReadImpl][CtVariableReadImpl]tmpArr[[CtLiteralImpl]1];
        }
        [CtReturnImpl]return [CtVariableReadImpl]userId;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]long getTokenExpireTimeMillis([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"accessTokenDO is " + [CtLiteralImpl]"\'NULL\'");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long accessTokenValidity = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenExpireMillis([CtVariableReadImpl]accessTokenDO);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshTokenValidity = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getRefreshTokenExpireTimeMillis([CtVariableReadImpl]accessTokenDO);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]accessTokenValidity > [CtLiteralImpl]1000) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]refreshTokenValidity > [CtLiteralImpl]1000) || [CtBinaryOperatorImpl]([CtVariableReadImpl]refreshTokenValidity < [CtLiteralImpl]0))) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]accessTokenValidity;
        }
        [CtReturnImpl]return [CtLiteralImpl]0;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]long getRefreshTokenExpireTimeMillis([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"accessTokenDO is " + [CtLiteralImpl]"\'NULL\'");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshTokenValidityPeriodMillis = [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getRefreshTokenValidityPeriodInMillis();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]refreshTokenValidityPeriodMillis < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Refresh Token has infinite lifetime");
            }
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshTokenIssuedTime = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getRefreshTokenIssuedTime().getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long refreshTokenValidity = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTimeToExpire([CtVariableReadImpl]refreshTokenIssuedTime, [CtVariableReadImpl]refreshTokenValidityPeriodMillis);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]refreshTokenValidity > [CtLiteralImpl]1000) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]refreshTokenValidity;
        }
        [CtReturnImpl]return [CtLiteralImpl]0;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]long getAccessTokenExpireMillis([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"accessTokenDO is " + [CtLiteralImpl]"\'NULL\'");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long validityPeriodMillis = [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getValidityPeriodInMillis();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]validityPeriodMillis < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Access Token(hashed) : " + [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.digest.DigestUtils.sha256Hex([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getAccessToken())) + [CtLiteralImpl]" has infinite lifetime");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Access Token has infinite lifetime");
                }
            }
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long issuedTime = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getIssuedTime().getTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long validityMillis = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTimeToExpire([CtVariableReadImpl]issuedTime, [CtVariableReadImpl]validityPeriodMillis);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]validityMillis > [CtLiteralImpl]1000) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]validityMillis;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]long calculateValidityInMillis([CtParameterImpl][CtTypeReferenceImpl]long issuedTimeInMillis, [CtParameterImpl][CtTypeReferenceImpl]long validityPeriodMillis) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTimeToExpire([CtVariableReadImpl]issuedTimeInMillis, [CtVariableReadImpl]validityPeriodMillis);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Util method to calculate the validity period after applying skew corrections.
     *
     * @param issuedTimeInMillis
     * @param validityPeriodMillis
     * @return skew corrected validity period in milliseconds
     */
    public static [CtTypeReferenceImpl]long getTimeToExpire([CtParameterImpl][CtTypeReferenceImpl]long issuedTimeInMillis, [CtParameterImpl][CtTypeReferenceImpl]long validityPeriodMillis) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]issuedTimeInMillis + [CtVariableReadImpl]validityPeriodMillis) - [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.timestampSkew);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int getTenantId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.user.core.service.RealmService realmService = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getRealmService();
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]realmService.getTenantManager().getTenantId([CtVariableReadImpl]tenantDomain);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.user.api.UserStoreException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Error in obtaining tenant ID from tenant domain : " + [CtVariableReadImpl]tenantDomain;
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]error, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getTenantDomain([CtParameterImpl][CtTypeReferenceImpl]int tenantId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.user.core.service.RealmService realmService = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getRealmService();
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]realmService.getTenantManager().getDomain([CtVariableReadImpl]tenantId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.user.api.UserStoreException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Error in obtaining tenant domain from tenant ID : " + [CtVariableReadImpl]tenantId;
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]error, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int getTenantIdFromUserName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String username) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String domainName = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantDomain([CtVariableReadImpl]username);
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTenantId([CtVariableReadImpl]domainName);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String hashScopes([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] scope) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.digest.DigestUtils.md5Hex([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.buildScopeString([CtVariableReadImpl]scope));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String hashScopes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]scope != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// first converted to an array to sort the scopes
            return [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.digest.DigestUtils.md5Hex([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.buildScopeString([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.buildScopeArray([CtVariableReadImpl]scope)));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser getUserFromUserName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String username) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]username)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantDomain([CtVariableReadImpl]username);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareUsername = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantAwareUsername([CtVariableReadImpl]username);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareUsernameWithNoUserDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.user.core.util.UserCoreUtil.removeDomainFromName([CtVariableReadImpl]tenantAwareUsername);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.extractDomainFromName([CtVariableReadImpl]username).toUpperCase();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser user = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser();
            [CtInvocationImpl][CtVariableReadImpl]user.setUserName([CtVariableReadImpl]tenantAwareUsernameWithNoUserDomain);
            [CtInvocationImpl][CtVariableReadImpl]user.setTenantDomain([CtVariableReadImpl]tenantDomain);
            [CtInvocationImpl][CtVariableReadImpl]user.setUserStoreDomain([CtVariableReadImpl]userStoreDomain);
            [CtReturnImpl]return [CtVariableReadImpl]user;
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Cannot create user from empty user name");
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getIDTokenIssuer() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String issuer = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOpenIDConnectIDTokenIssuerIdentifier();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]issuer)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]issuer = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OAuthURL.getOAuth2TokenEPUrl();
        }
        [CtReturnImpl]return [CtVariableReadImpl]issuer;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * OAuth URL related utility functions.
     */
    public static class OAuthURL {
        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth1RequestTokenUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth1RequestTokenUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth1RequestTokenUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth1RequestTokenUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth1RequestTokenUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth/request-token", [CtLiteralImpl]true, [CtLiteralImpl]true);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth1RequestTokenUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth1AuthorizeUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth1AuthorizeUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth1AuthorizeUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth1AuthorizeUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth1AuthorizeUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth/authorize-url", [CtLiteralImpl]true, [CtLiteralImpl]true);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth1AuthorizeUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth1AccessTokenUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth1AccessTokenUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth1AccessTokenUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth1AccessTokenUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth1AccessTokenUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth/access-token", [CtLiteralImpl]true, [CtLiteralImpl]true);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth1AccessTokenUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2AuthzEPUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth2AuthzEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth2AuthzEPUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth2AuthzEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2AuthzEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth2/authorize", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth2AuthzEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2TokenEPUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth2TokenEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth2TokenEPUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth2TokenEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2TokenEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth2/token", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth2TokenEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2DCREPUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth2TokenEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth2DCREPUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth2TokenEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2TokenEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/api/identity/oauth2/dcr/v1.0/register", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]tenantDomain) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME.equals([CtVariableReadImpl]tenantDomain))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2TokenEPUrl = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OAuthURL.getTenantUrl([CtVariableReadImpl]oauth2TokenEPUrl, [CtVariableReadImpl]tenantDomain);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth2TokenEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2JWKSPageUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String auth2JWKSPageUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth2JWKSPageUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]auth2JWKSPageUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]auth2JWKSPageUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/oauth2/jwks", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]tenantDomain) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME.equals([CtVariableReadImpl]tenantDomain))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]auth2JWKSPageUrl = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OAuthURL.getTenantUrl([CtVariableReadImpl]auth2JWKSPageUrl, [CtVariableReadImpl]tenantDomain);
            }
            [CtReturnImpl]return [CtVariableReadImpl]auth2JWKSPageUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOidcWebFingerEPUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth2TokenEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOidcWebFingerEPUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth2TokenEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2TokenEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]".well-know/webfinger", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth2TokenEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOidcDiscoveryEPUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oidcDiscoveryEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOidcDiscoveryUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oidcDiscoveryEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oidcDiscoveryEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/oauth2/oidcdiscovery", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]tenantDomain) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME.equals([CtVariableReadImpl]tenantDomain))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oidcDiscoveryEPUrl = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OAuthURL.getTenantUrl([CtVariableReadImpl]oidcDiscoveryEPUrl, [CtVariableReadImpl]tenantDomain);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oidcDiscoveryEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2UserInfoEPUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauth2UserInfoEPUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOauth2UserInfoEPUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oauth2UserInfoEPUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oauth2UserInfoEPUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"oauth2/userinfo", [CtLiteralImpl]true, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauth2UserInfoEPUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOIDCConsentPageUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oidcConsentPageUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOIDCConsentPageUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oidcConsentPageUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oidcConsentPageUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/authenticationendpoint/oauth2_consent.do", [CtLiteralImpl]false, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oidcConsentPageUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2ConsentPageUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oAuth2ConsentPageUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOauth2ConsentPageUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oAuth2ConsentPageUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oAuth2ConsentPageUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/authenticationendpoint/oauth2_authz.do", [CtLiteralImpl]false, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oAuth2ConsentPageUrl;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getOAuth2ErrorPageUrl() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oAuth2ErrorPageUrl = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOauth2ErrorPageUrl();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]oAuth2ErrorPageUrl)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]oAuth2ErrorPageUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getServerURL([CtLiteralImpl]"/authenticationendpoint/oauth2_error.do", [CtLiteralImpl]false, [CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oAuth2ErrorPageUrl;
        }

        [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getTenantUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI uri = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtVariableReadImpl]url);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI uriModified = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtInvocationImpl][CtVariableReadImpl]uri.getScheme(), [CtInvocationImpl][CtVariableReadImpl]uri.getUserInfo(), [CtInvocationImpl][CtVariableReadImpl]uri.getHost(), [CtInvocationImpl][CtVariableReadImpl]uri.getPort(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/t/" + [CtVariableReadImpl]tenantDomain) + [CtInvocationImpl][CtVariableReadImpl]uri.getPath(), [CtInvocationImpl][CtVariableReadImpl]uri.getQuery(), [CtInvocationImpl][CtVariableReadImpl]uri.getFragment());
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]uriModified.toString();
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isOIDCAuthzRequest([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> scope) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]scope.contains([CtTypeAccessImpl]OAuthConstants.Scope.OPENID);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isOIDCAuthzRequest([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] scope) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String openidscope : [CtVariableReadImpl]scope) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]openidscope.equals([CtTypeAccessImpl]OAuthConstants.Scope.OPENID)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the PKCE code verifier is upto specification as per RFC 7636
     *
     * @param codeVerifier
     * 		PKCE Code Verifier sent with the token request
     * @return  */
    public static [CtTypeReferenceImpl]boolean validatePKCECodeVerifier([CtParameterImpl][CtTypeReferenceImpl]java.lang.String codeVerifier) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher pkceCodeVerifierMatcher = [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.pkceCodeVerifierPattern.matcher([CtVariableReadImpl]codeVerifier);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]pkceCodeVerifierMatcher.matches()) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]codeVerifier.length() < [CtLiteralImpl]43) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]codeVerifier.length() > [CtLiteralImpl]128))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the codeChallenge is upto specification as per RFC 7636
     *
     * @param codeChallenge
     * @param codeChallengeMethod
     * @return  */
    public static [CtTypeReferenceImpl]boolean validatePKCECodeChallenge([CtParameterImpl][CtTypeReferenceImpl]java.lang.String codeChallenge, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codeChallengeMethod) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]codeChallengeMethod == [CtLiteralImpl]null) || [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAUTH_PKCE_PLAIN_CHALLENGE.equals([CtVariableReadImpl]codeChallengeMethod)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.validatePKCECodeVerifier([CtVariableReadImpl]codeChallenge);
        } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAUTH_PKCE_S256_CHALLENGE.equals([CtVariableReadImpl]codeChallengeMethod)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// SHA256 code challenge is 256 bits that is 256 / 6 ~= 43
            [CtCommentImpl]// See https://tools.ietf.org/html/rfc7636#section-3
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]codeChallenge != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]codeChallenge.trim().length() == [CtLiteralImpl]43)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl][CtCommentImpl]// provided code challenge method is wrong
        return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]boolean doPKCEValidation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String referenceCodeChallenge, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String codeVerifier, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String challengeMethod, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.validatePKCE([CtVariableReadImpl]referenceCodeChallenge, [CtVariableReadImpl]codeVerifier, [CtVariableReadImpl]challengeMethod, [CtVariableReadImpl]oAuthAppDO);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean validatePKCE([CtParameterImpl][CtTypeReferenceImpl]java.lang.String referenceCodeChallenge, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String verificationCode, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String challengeMethod, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthApp) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]oAuthApp != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]oAuthApp.isPkceMandatory()) || [CtBinaryOperatorImpl]([CtVariableReadImpl]referenceCodeChallenge != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// As per RFC 7636 Fallback to 'plain' if no code_challenge_method parameter is sent
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]challengeMethod == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]challengeMethod.trim().length() == [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]challengeMethod = [CtLiteralImpl]"plain";
            }
            [CtIfImpl][CtCommentImpl]// if app with no PKCE code verifier arrives
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]verificationCode == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]verificationCode.trim().length() == [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// if pkce is mandatory, throw error
                if ([CtInvocationImpl][CtVariableReadImpl]oAuthApp.isPkceMandatory()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"No PKCE code verifier found.PKCE is mandatory for this " + [CtLiteralImpl]"oAuth 2.0 application.");
                } else [CtIfImpl][CtCommentImpl]// PKCE is optional, see if the authz code was requested with a PKCE challenge
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]referenceCodeChallenge == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]referenceCodeChallenge.trim().length() == [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtReturnImpl][CtCommentImpl]// since no PKCE challenge was provided
                    return [CtLiteralImpl]true;
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Empty PKCE code_verifier sent. This authorization code " + [CtLiteralImpl]"requires a PKCE verification to obtain an access token.");
                }
            }
            [CtIfImpl][CtCommentImpl]// verify that the code verifier is upto spec as per RFC 7636
            if ([CtUnaryOperatorImpl]![CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.validatePKCECodeVerifier([CtVariableReadImpl]verificationCode)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Code verifier used is not up to RFC 7636 specifications.");
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAUTH_PKCE_PLAIN_CHALLENGE.equals([CtVariableReadImpl]challengeMethod)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// if the current application explicitly doesn't support plain, throw exception
                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]oAuthApp.isPkceSupportPlain()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"This application does not allow 'plain' transformation algorithm.");
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]referenceCodeChallenge.equals([CtVariableReadImpl]verificationCode)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAUTH_PKCE_S256_CHALLENGE.equals([CtVariableReadImpl]challengeMethod)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.MessageDigest messageDigest = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtLiteralImpl]"SHA-256");
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] hash = [CtInvocationImpl][CtVariableReadImpl]messageDigest.digest([CtInvocationImpl][CtVariableReadImpl]verificationCode.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]US_ASCII));
                    [CtLocalVariableImpl][CtCommentImpl]// Trim the base64 string to remove trailing CR LF characters.
                    [CtTypeReferenceImpl]java.lang.String referencePKCECodeChallenge = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Base64.encodeBase64URLSafe([CtVariableReadImpl]hash), [CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8).trim();
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]referencePKCECodeChallenge.equals([CtVariableReadImpl]referenceCodeChallenge)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]false;
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Failed to create SHA256 Message Digest.");
                    }
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            } else [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// Invalid OAuth2 token response
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid OAuth2 Token Response. Invalid PKCE Code Challenge Method '" + [CtVariableReadImpl]challengeMethod) + [CtLiteralImpl]"'");
            }
        }
        [CtReturnImpl][CtCommentImpl]// pkce validation successful
        return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]boolean isPKCESupportEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isPkceEnabled();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To check whether the given response type is for Implicit flow.
     *
     * @param responseType
     * 		response type
     * @return true if the response type is for Implicit flow
     */
    public static [CtTypeReferenceImpl]boolean isImplicitResponseType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String responseType) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]responseType) && [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.ID_TOKEN.equals([CtVariableReadImpl]responseType)) || [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.TOKEN.equals([CtVariableReadImpl]responseType)) || [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.IDTOKEN_TOKEN.equals([CtVariableReadImpl]responseType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To check whether the given response type is for Hybrid flow.
     *
     * @param responseType
     * 		response type
     * @return true if the response type is for Hybrid flow.
     */
    public static [CtTypeReferenceImpl]boolean isHybridResponseType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String responseType) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]responseType) && [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.CODE_TOKEN.equals([CtVariableReadImpl]responseType)) || [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.CODE_IDTOKEN.equals([CtVariableReadImpl]responseType)) || [CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.CODE_IDTOKEN_TOKEN.equals([CtVariableReadImpl]responseType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To populate the database in the very first server startup.
     *
     * @param tenantId
     * 		tenant id
     */
    public static [CtTypeReferenceImpl]void initiateOIDCScopes([CtParameterImpl][CtTypeReferenceImpl]int tenantId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO> scopeClaimsList = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.loadScopeConfigFile();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().addScopes([CtVariableReadImpl]tenantId, [CtVariableReadImpl]scopeClaimsList);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getOIDCScopes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> scopes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getRealmService().getTenantManager().getTenantId([CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtCommentImpl]// Get the scopes from the cache or the db
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO> scopesDTOList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().getScopes([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.isNotEmpty([CtVariableReadImpl]scopesDTOList)) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO scope : [CtVariableReadImpl]scopesDTOList) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]scopes.add([CtInvocationImpl][CtVariableReadImpl]scope.getName());
                }
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.wso2.carbon.user.api.UserStoreException | [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while retrieving OIDC scopes.", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]scopes;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO getAccessTokenDOfromTokenIdentifier([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessTokenIdentifier) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOFromTokenIdentifier([CtVariableReadImpl]accessTokenIdentifier, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO getAccessTokenDOFromTokenIdentifier([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessTokenIdentifier, [CtParameterImpl][CtTypeReferenceImpl]boolean includeExpired) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean cacheHit = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// As the server implementation knows about the PersistenceProcessor Processed Access Token,
        [CtCommentImpl]// we are converting before adding to the cache.
        [CtTypeReferenceImpl]java.lang.String processedToken = [CtInvocationImpl][CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPersistenceProcessor().getProcessedAccessTokenIdentifier([CtVariableReadImpl]accessTokenIdentifier);
        [CtLocalVariableImpl][CtCommentImpl]// check the cache, if caching is enabled.
        [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey cacheKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]accessTokenIdentifier);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.CacheEntry result = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().getValueFromCache([CtVariableReadImpl]cacheKey);
        [CtIfImpl][CtCommentImpl]// cache hit, do the type check.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]result != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]result instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDO = [CtVariableReadImpl](([CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO) (result));
            [CtAssignmentImpl][CtVariableWriteImpl]cacheHit = [CtLiteralImpl]true;
        }
        [CtIfImpl][CtCommentImpl]// cache miss, load the access token info from the database.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getAccessToken([CtVariableReadImpl]accessTokenIdentifier, [CtVariableReadImpl]includeExpired);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// this means the token is not active so we can't proceed further
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Invalid Access Token. Access token is not ACTIVE.");
        }
        [CtIfImpl][CtCommentImpl]// Add the token back to the cache in the case of a cache miss but don't add to cache when OAuth2 token
        [CtCommentImpl]// hashing feature enabled inorder to reduce the complexity.
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]cacheHit) & [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isHashDisabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().addToCache([CtVariableReadImpl]cacheKey, [CtVariableReadImpl]accessTokenDO);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Access Token Info object was added back to the cache.");
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]accessTokenDO;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getClientIdForAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessTokenIdentifier) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOfromTokenIdentifier([CtVariableReadImpl]accessTokenIdentifier);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getConsumerKey();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Read the configuration file at server start up.
     *
     * @param tenantId
     * @deprecated due to UI implementation.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]void initTokenExpiryTimesOfSps([CtParameterImpl][CtTypeReferenceImpl]int tenantId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.Registry registry = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.getRegistryService().getConfigSystemRegistry([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]registry.resourceExists([CtTypeAccessImpl]OAuthConstants.TOKEN_EXPIRE_TIME_RESOURCE_PATH)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.Resource resource = [CtInvocationImpl][CtVariableReadImpl]registry.newResource();
                [CtInvocationImpl][CtVariableReadImpl]registry.put([CtTypeAccessImpl]OAuthConstants.TOKEN_EXPIRE_TIME_RESOURCE_PATH, [CtVariableReadImpl]resource);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.exceptions.RegistryException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtBinaryOperatorImpl][CtLiteralImpl]"Error while creating registry collection for :" + [CtFieldReadImpl]org.wso2.carbon.identity.oauth.common.OAuthConstants.TOKEN_EXPIRE_TIME_RESOURCE_PATH, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Return the SP-token Expiry time configuration object when consumer key is given.
     *
     * @param consumerKey
     * @param tenantId
     * @return A SpOAuth2ExpiryTimeConfiguration Object
     * @deprecated due to UI implementation
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.config.SpOAuth2ExpiryTimeConfiguration getSpTokenExpiryTimeConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey, [CtParameterImpl][CtTypeReferenceImpl]int tenantId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.config.SpOAuth2ExpiryTimeConfiguration spTokenTimeObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.config.SpOAuth2ExpiryTimeConfiguration();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SP wise token expiry time feature is applied for tenant id : " + [CtVariableReadImpl]tenantId) + [CtLiteralImpl]"and consumer key : ") + [CtVariableReadImpl]consumerKey);
            }
            [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.initializeRegistry([CtVariableReadImpl]tenantId, [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTenantDomain([CtVariableReadImpl]tenantId));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.Registry registry = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.getConfigRegistry([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]registry.resourceExists([CtTypeAccessImpl]OAuthConstants.TOKEN_EXPIRE_TIME_RESOURCE_PATH)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.Resource resource = [CtInvocationImpl][CtVariableReadImpl]registry.get([CtTypeAccessImpl]OAuthConstants.TOKEN_EXPIRE_TIME_RESOURCE_PATH);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonString = [CtLiteralImpl]"{}";
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object consumerKeyObject = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getProperties().get([CtVariableReadImpl]consumerKey);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]consumerKeyObject instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.List) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List) (consumerKeyObject)).isEmpty()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]jsonString = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List) (consumerKeyObject)).get([CtLiteralImpl]0).toString();
                    }
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject spTimeObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject([CtVariableReadImpl]jsonString);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.has([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]spTimeObject.isNull([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS))) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setUserAccessTokenExpiryTime([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()));
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The user access token expiry time :" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()) + [CtLiteralImpl]"  for application id : ") + [CtVariableReadImpl]consumerKey);
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid value provided as user access token expiry time for consumer " + [CtLiteralImpl]"key %s, tenant id : %d. Given value: %s, Expected a long value", [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]tenantId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.USER_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString());
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setUserAccessTokenExpiryTime([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getUserAccessTokenValidityPeriodInSeconds() * [CtLiteralImpl]1000);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.has([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]spTimeObject.isNull([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS))) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setApplicationAccessTokenExpiryTime([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()));
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The application access token expiry time :" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()) + [CtLiteralImpl]"  for application id : ") + [CtVariableReadImpl]consumerKey);
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid value provided as application access token expiry time for consumer " + [CtLiteralImpl]"key %s, tenant id : %d. Given value: %s, Expected a long value ", [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]tenantId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.APPLICATION_ACCESS_TOKEN_EXP_TIME_IN_MILLISECONDS).toString());
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setApplicationAccessTokenExpiryTime([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getApplicationAccessTokenValidityPeriodInSeconds() * [CtLiteralImpl]1000);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.has([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]spTimeObject.isNull([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS))) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setRefreshTokenExpiryTime([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()));
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The refresh token expiry time :" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS).toString()) + [CtLiteralImpl]" for application id : ") + [CtVariableReadImpl]consumerKey);
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid value provided as refresh token expiry time for consumer key %s, tenant " + [CtLiteralImpl]"id : %d. Given value: %s, Expected a long value", [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]tenantId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spTimeObject.get([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.REFRESH_TOKEN_EXP_TIME_IN_MILLISECONDS).toString());
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]spTokenTimeObject.setRefreshTokenExpiryTime([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getRefreshTokenValidityPeriodInSeconds() * [CtLiteralImpl]1000);
                    }
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.registry.core.exceptions.RegistryException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while getting data from the registry.", [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.base.IdentityException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtBinaryOperatorImpl][CtLiteralImpl]"Error while getting the tenant domain from tenant id : " + [CtVariableReadImpl]tenantId, [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]spTokenTimeObject;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieve audience configured for the particular service provider.
     *
     * @param clientId
     * @param oAuthAppDO
     * @return  */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getOIDCAudience([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> oidcAudiences = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getDefinedCustomOIDCAudiences([CtVariableReadImpl]oAuthAppDO);
        [CtIfImpl][CtCommentImpl]// Need to add client_id as an audience value according to the spec.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]oidcAudiences.contains([CtVariableReadImpl]clientId)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]oidcAudiences.add([CtLiteralImpl]0, [CtVariableReadImpl]clientId);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.swap([CtVariableReadImpl]oidcAudiences, [CtInvocationImpl][CtVariableReadImpl]oidcAudiences.indexOf([CtVariableReadImpl]clientId), [CtLiteralImpl]0);
        }
        [CtReturnImpl]return [CtVariableReadImpl]oidcAudiences;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getDefinedCustomOIDCAudiences([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> audiences = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl][CtCommentImpl]// Priority should be given to service provider specific audiences over globally configured ones.
        if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isAudienceEnabled()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]audiences = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAudienceListFromOAuthAppDO([CtVariableReadImpl]oAuthAppDO);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.isNotEmpty([CtVariableReadImpl]audiences)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"OIDC Audiences " + [CtVariableReadImpl]audiences) + [CtLiteralImpl]" had been retrieved for the client_id: ") + [CtInvocationImpl][CtVariableReadImpl]oAuthAppDO.getOauthConsumerKey());
                }
                [CtReturnImpl]return [CtVariableReadImpl]audiences;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.core.util.IdentityConfigParser configParser = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityConfigParser.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement oauthElem = [CtInvocationImpl][CtVariableReadImpl]configParser.getConfigElement([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.CONFIG_ELEM_OAUTH);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthElem == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error in OAuth Configuration: <OAuth> configuration element is not available in identity.xml.");
            [CtReturnImpl]return [CtVariableReadImpl]audiences;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement oidcConfig = [CtInvocationImpl][CtVariableReadImpl]oauthElem.getFirstChildWithName([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.core.util.IdentityCoreConstants.IDENTITY_DEFAULT_NAMESPACE, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OPENID_CONNECT));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oidcConfig == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error in OAuth Configuration: <OpenIDConnect> element is not available in identity.xml.");
            [CtReturnImpl]return [CtVariableReadImpl]audiences;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement audienceConfig = [CtInvocationImpl][CtVariableReadImpl]oidcConfig.getFirstChildWithName([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.core.util.IdentityCoreConstants.IDENTITY_DEFAULT_NAMESPACE, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OPENID_CONNECT_AUDIENCES));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]audienceConfig == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]audiences;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator iterator = [CtInvocationImpl][CtVariableReadImpl]audienceConfig.getChildrenWithName([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.core.util.IdentityCoreConstants.IDENTITY_DEFAULT_NAMESPACE, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OPENID_CONNECT_AUDIENCE_IDENTITY_CONFIG));
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement supportedAudience = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]iterator.next()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String supportedAudienceName;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]supportedAudience != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]supportedAudienceName = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.fillURLPlaceholders([CtInvocationImpl][CtVariableReadImpl]supportedAudience.getText());
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]supportedAudienceName)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]audiences.add([CtVariableReadImpl]supportedAudienceName);
                }
            }
        } 
        [CtReturnImpl]return [CtVariableReadImpl]audiences;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAudienceListFromOAuthAppDO([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oAuthAppDO.getAudiences() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]oAuthAppDO.getAudiences()));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns oauth token issuer registered in the service provider app
     *
     * @param clientId
     * 		client id of the oauth app
     * @return oauth token issuer
     * @throws IdentityOAuth2Exception
     * @throws InvalidOAuthClientException
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer getOAuthTokenIssuerForOAuthApp([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO appDO;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]appDO = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAppInformationByClientId([CtVariableReadImpl]clientId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error while retrieving app information for clientId: " + [CtVariableReadImpl]clientId, [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getOAuthTokenIssuerForOAuthApp([CtVariableReadImpl]appDO);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns oauth token issuer registered in the service provider app.
     *
     * @param appDO
     * 		oauth app data object
     * @return oauth token issuer
     * @throws IdentityOAuth2Exception
     * @throws InvalidOAuthClientException
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer getOAuthTokenIssuerForOAuthApp([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO appDO) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer oauthIdentityTokenGenerator;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]appDO.getTokenType() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]oauthIdentityTokenGenerator = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().addAndReturnTokenIssuerInstance([CtInvocationImpl][CtVariableReadImpl]appDO.getTokenType());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthIdentityTokenGenerator == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// get server level configured token issuer
                [CtVariableWriteImpl]oauthIdentityTokenGenerator = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getIdentityOauthTokenIssuer();
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]oauthIdentityTokenGenerator = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getIdentityOauthTokenIssuer();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Token type is not set for service provider app with client Id: " + [CtInvocationImpl][CtVariableReadImpl]appDO.getOauthConsumerKey()) + [CtLiteralImpl]". Hence the default Identity OAuth token issuer will be used. ") + [CtLiteralImpl]"No custom token generator is set.");
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]oauthIdentityTokenGenerator;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO> loadScopeConfigFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO> listOIDCScopesClaims = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configDirPath = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.CarbonUtils.getCarbonConfigDirPath();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String confXml = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtVariableReadImpl]configDirPath, [CtLiteralImpl]"identity", [CtTypeAccessImpl]OAuthConstants.OIDC_SCOPE_CONFIG_PATH).toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File configfile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]confXml);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]configfile.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtBinaryOperatorImpl][CtLiteralImpl]"OIDC scope-claim Configuration File is not present at: " + [CtVariableReadImpl]confXml);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamReader parser = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream stream = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]stream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtVariableReadImpl]configfile);
            [CtAssignmentImpl][CtVariableWriteImpl]parser = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.xml.stream.XMLInputFactory.newInstance().createXMLStreamReader([CtVariableReadImpl]stream);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.impl.builder.StAXOMBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.axiom.om.impl.builder.StAXOMBuilder([CtVariableReadImpl]parser);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement documentElement = [CtInvocationImpl][CtVariableReadImpl]builder.getDocumentElement();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator iterator = [CtInvocationImpl][CtVariableReadImpl]documentElement.getChildElements();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO scope = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement omElement = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]iterator.next()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configType = [CtInvocationImpl][CtVariableReadImpl]omElement.getAttributeValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtLiteralImpl]"id"));
                [CtInvocationImpl][CtVariableReadImpl]scope.setName([CtVariableReadImpl]configType);
                [CtInvocationImpl][CtVariableReadImpl]scope.setClaim([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.loadClaimConfig([CtVariableReadImpl]omElement));
                [CtInvocationImpl][CtVariableReadImpl]listOIDCScopesClaims.add([CtVariableReadImpl]scope);
            } 
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error while loading scope config.", [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.FileNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error while loading email config.", [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parser != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]parser.close();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stream != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityIOStreamUtils.closeInputStream([CtVariableReadImpl]stream);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while closing XML stream", [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]listOIDCScopesClaims;
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]java.lang.String[] loadClaimConfig([CtParameterImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement configElement) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder claimConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]configElement.getChildElements();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement element = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]it.next()));
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"Claim".equals([CtInvocationImpl][CtVariableReadImpl]element.getLocalName())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commaSeparatedClaimNames = [CtInvocationImpl][CtVariableReadImpl]element.getText();
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]commaSeparatedClaimNames)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]claimConfig.append([CtInvocationImpl][CtVariableReadImpl]commaSeparatedClaimNames.trim());
                }
            }
        } 
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]claimConfig.toString().split([CtLiteralImpl]",");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get Oauth application information
     *
     * @param clientId
     * @return Oauth app information
     * @throws IdentityOAuth2Exception
     * @throws InvalidOAuthClientException
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO getAppInformationByClientId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().getValueFromCache([CtVariableReadImpl]clientId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oAuthAppDO != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]oAuthAppDO;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]oAuthAppDO = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO().getAppInformation([CtVariableReadImpl]clientId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oAuthAppDO != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().addToCache([CtVariableReadImpl]clientId, [CtVariableReadImpl]oAuthAppDO);
            }
            [CtReturnImpl]return [CtVariableReadImpl]oAuthAppDO;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the tenant domain of an oauth application
     *
     * @param oAuthAppDO
     * @return  */
    public static [CtTypeReferenceImpl]java.lang.String getTenantDomainOfOauthApp([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtFieldReadImpl]org.wso2.carbon.utils.multitenancy.MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]oAuthAppDO != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]oAuthAppDO.getUser() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]oAuthAppDO.getUser().getTenantDomain();
        }
        [CtReturnImpl]return [CtVariableReadImpl]tenantDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is used to get the tenant domain of an application by clientId.
     *
     * @param clientId
     * 		Consumer key of Application
     * @return Tenant Domain
     * @throws IdentityOAuth2Exception
     * @throws InvalidOAuthClientException
     */
    public static [CtTypeReferenceImpl]java.lang.String getTenantDomainOfOauthApp([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAppInformationByClientId([CtVariableReadImpl]clientId);
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTenantDomainOfOauthApp([CtVariableReadImpl]oAuthAppDO);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method map signature algorithm define in identity.xml to nimbus
     * signature algorithm
     *
     * @param signatureAlgorithm
     * 		name of the signature algorithm
     * @return mapped JWSAlgorithm name
     * @throws IdentityOAuth2Exception
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]java.lang.String mapSignatureAlgorithm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String signatureAlgorithm) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.mapSignatureAlgorithmForJWSAlgorithm([CtVariableReadImpl]signatureAlgorithm).getName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method maps the encryption algorithm name defined in identity.xml to a respective
     * nimbus encryption algorithm.
     *
     * @param encryptionAlgorithm
     * 		name of the encryption algorithm
     * @return mapped JWEAlgorithm
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm mapEncryptionAlgorithmForJWEAlgorithm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String encryptionAlgorithm) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Parse method in JWEAlgorithm is used to get a JWEAlgorithm object from the algorithm name.
        [CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm jweAlgorithm = [CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jose.JWEAlgorithm.parse([CtVariableReadImpl]encryptionAlgorithm);
        [CtIfImpl][CtCommentImpl]// Parse method returns a new JWEAlgorithm with requirement set to null if unknown algorithm name is passed.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]jweAlgorithm.getRequirement() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]jweAlgorithm;
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported Encryption Algorithm: " + [CtVariableReadImpl]encryptionAlgorithm);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method maps the encryption method name defined in identity.xml to a respective nimbus
     * encryption method.
     *
     * @param encryptionMethod
     * 		name of the encryption method
     * @return mapped EncryptionMethod
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jose.EncryptionMethod mapEncryptionMethodForJWEAlgorithm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String encryptionMethod) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Parse method in EncryptionMethod is used to get a EncryptionMethod object from the method name.
        [CtTypeReferenceImpl]com.nimbusds.jose.EncryptionMethod method = [CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jose.EncryptionMethod.parse([CtVariableReadImpl]encryptionMethod);
        [CtIfImpl][CtCommentImpl]// Parse method returns a new EncryptionMethod with requirement set to null if unknown method name is passed.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]method.getRequirement() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]method;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Unsupported Encryption Method in identity.xml");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported Encryption Method: " + [CtVariableReadImpl]encryptionMethod);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method map signature algorithm define in identity.xml to nimbus
     * signature algorithm
     *
     * @param signatureAlgorithm
     * 		name of the signature algorithm
     * @return mapped JWSAlgorithm
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm mapSignatureAlgorithmForJWSAlgorithm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String signatureAlgorithm) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.NONE.equalsIgnoreCase([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.NONE.getName());
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA256_WITH_RSA.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.RS256;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA384_WITH_RSA.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.RS384;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA512_WITH_RSA.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.RS512;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA256_WITH_HMAC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.HS256;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA384_WITH_HMAC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.HS384;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA512_WITH_HMAC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.HS512;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA256_WITH_EC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.ES256;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA384_WITH_EC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.ES384;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA512_WITH_EC.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.ES512;
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA256_WITH_PS.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.nimbusds.jose.JWSAlgorithm.PS256;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Unsupported Signature Algorithm in identity.xml");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Unsupported Signature Algorithm in identity.xml");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if audiences are enabled by reading configuration file at server startup.
     *
     * @return  */
    public static [CtTypeReferenceImpl]boolean checkAudienceEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isAudienceEnabled = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.core.util.IdentityConfigParser configParser = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityConfigParser.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement oauthElem = [CtInvocationImpl][CtVariableReadImpl]configParser.getConfigElement([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.CONFIG_ELEM_OAUTH);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthElem == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error in OAuth Configuration. OAuth element is not available.");
            [CtReturnImpl]return [CtVariableReadImpl]isAudienceEnabled;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement configOpenIDConnect = [CtInvocationImpl][CtVariableReadImpl]oauthElem.getFirstChildWithName([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.core.util.IdentityCoreConstants.IDENTITY_DEFAULT_NAMESPACE, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.OPENID_CONNECT));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]configOpenIDConnect == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error in OAuth Configuration. OpenID element is not available.");
            [CtReturnImpl]return [CtVariableReadImpl]isAudienceEnabled;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement configAudience = [CtInvocationImpl][CtVariableReadImpl]configOpenIDConnect.getFirstChildWithName([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.core.util.IdentityCoreConstants.IDENTITY_DEFAULT_NAMESPACE, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.ENABLE_OPENID_CONNECT_AUDIENCES));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]configAudience != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configAudienceValue = [CtInvocationImpl][CtVariableReadImpl]configAudience.getText();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]configAudienceValue)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]isAudienceEnabled = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]configAudienceValue);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]isAudienceEnabled;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate the unique user domain value in the format of "FEDERATED:idp_name".
     *
     * @param authenticatedIDP
     * 		: Name of the IDP, which authenticated the user.
     * @return  */
    public static [CtTypeReferenceImpl]java.lang.String getFederatedUserDomain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String authenticatedIDP) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isNotBlank([CtVariableReadImpl]authenticatedIDP)) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX + [CtFieldReadImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_SEPARATOR) + [CtVariableReadImpl]authenticatedIDP;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validate Id token signature
     *
     * @param idToken
     * 		Id token
     * @return validation state
     */
    public static [CtTypeReferenceImpl]boolean validateIdToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String idToken) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isJWTSignedWithSPKey = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isJWTSignedWithSPKey();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clientId = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jwt.SignedJWT.parse([CtVariableReadImpl]idToken).getJWTClaimsSet().getAudience().get([CtLiteralImpl]0);
            [CtIfImpl]if ([CtVariableReadImpl]isJWTSignedWithSPKey) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAppInformationByClientId([CtVariableReadImpl]clientId);
                [CtAssignmentImpl][CtVariableWriteImpl]tenantDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTenantDomainOfOauthApp([CtVariableReadImpl]oAuthAppDO);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// It is not sending tenant domain with the subject in id_token by default, So to work this as
                [CtCommentImpl]// expected, need to enable the option "Use tenant domain in local subject identifier" in SP config
                [CtVariableWriteImpl]tenantDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantDomain([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jwt.SignedJWT.parse([CtVariableReadImpl]idToken).getJWTClaimsSet().getSubject());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtVariableReadImpl]tenantDomain)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.getTenantId([CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.interfaces.RSAPublicKey publicKey;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.core.util.KeyStoreManager keyStoreManager = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.core.util.KeyStoreManager.getInstance([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tenantDomain.equals([CtTypeAccessImpl]org.wso2.carbon.base.MultitenantConstants.SUPER_TENANT_DOMAIN_NAME)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ksName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tenantDomain.trim().replace([CtLiteralImpl]".", [CtLiteralImpl]"-");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jksName = [CtBinaryOperatorImpl][CtVariableReadImpl]ksName + [CtLiteralImpl]".jks";
                [CtAssignmentImpl][CtVariableWriteImpl]publicKey = [CtInvocationImpl](([CtTypeReferenceImpl]java.security.interfaces.RSAPublicKey) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyStoreManager.getKeyStore([CtVariableReadImpl]jksName).getCertificate([CtVariableReadImpl]tenantDomain).getPublicKey()));
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]publicKey = [CtInvocationImpl](([CtTypeReferenceImpl]java.security.interfaces.RSAPublicKey) ([CtVariableReadImpl]keyStoreManager.getDefaultPublicKey()));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jwt.SignedJWT signedJWT = [CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jwt.SignedJWT.parse([CtVariableReadImpl]idToken);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSVerifier verifier = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jose.crypto.RSASSAVerifier([CtVariableReadImpl]publicKey);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]signedJWT.verify([CtVariableReadImpl]verifier);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]com.nimbusds.jose.JOSEException | [CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error occurred while validating id token signature.");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error occurred while validating id token signature.");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method maps signature algorithm define in identity.xml to digest algorithms to generate the at_hash
     *
     * @param signatureAlgorithm
     * @return the mapped digest algorithm
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String mapDigestAlgorithm([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.Algorithm signatureAlgorithm) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS256.equals([CtVariableReadImpl]signatureAlgorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS256.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.ES256.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.PS256.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA256;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS384.equals([CtVariableReadImpl]signatureAlgorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS384.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.ES384.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA384;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS512.equals([CtVariableReadImpl]signatureAlgorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS512.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.ES512.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.SHA512;
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided signature algorithm: " + [CtVariableReadImpl]signatureAlgorithm) + [CtLiteralImpl]" is not supported");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is the generic Encryption function which calls algorithm specific encryption function
     * depending on the algorithm name.
     *
     * @param jwtClaimsSet
     * 		contains JWT body
     * @param encryptionAlgorithm
     * 		JWT encryption algorithm
     * @param spTenantDomain
     * 		Service provider tenant domain
     * @param clientId
     * 		ID of the client
     * @return encrypted JWT token
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jwt.JWT encryptJWT([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jwt.JWTClaimsSet jwtClaimsSet, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm encryptionAlgorithm, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.EncryptionMethod encryptionMethod, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String spTenantDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isRSAAlgorithm([CtVariableReadImpl]encryptionAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.encryptWithRSA([CtVariableReadImpl]jwtClaimsSet, [CtVariableReadImpl]encryptionAlgorithm, [CtVariableReadImpl]encryptionMethod, [CtVariableReadImpl]spTenantDomain, [CtVariableReadImpl]clientId);
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided encryption algorithm: " + [CtVariableReadImpl]encryptionAlgorithm) + [CtLiteralImpl]" is not supported");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encrypt JWT id token using RSA algorithm.
     *
     * @param jwtClaimsSet
     * 		contains JWT body
     * @param encryptionAlgorithm
     * 		JWT signing algorithm
     * @param spTenantDomain
     * 		Service provider tenant domain
     * @param clientId
     * 		ID of the client
     * @return encrypted JWT token
     * @throws IdentityOAuth2Exception
     */
    private static [CtTypeReferenceImpl]com.nimbusds.jwt.JWT encryptWithRSA([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jwt.JWTClaimsSet jwtClaimsSet, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm encryptionAlgorithm, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.EncryptionMethod encryptionMethod, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String spTenantDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]spTenantDomain)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]spTenantDomain = [CtFieldReadImpl]org.wso2.carbon.utils.multitenancy.MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Assigned super tenant domain as signing domain when encrypting id token for " + [CtLiteralImpl]"client_id: ") + [CtVariableReadImpl]clientId);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jwksUri = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getSPJwksUrl([CtVariableReadImpl]clientId, [CtVariableReadImpl]spTenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.Certificate publicCert;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String thumbPrint;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]jwksUri)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Jwks uri is not configured for the service provider associated with " + [CtLiteralImpl]"client_id: %s. Checking for x509 certificate", [CtVariableReadImpl]clientId));
            }
            [CtAssignmentImpl][CtVariableWriteImpl]publicCert = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getX509CertOfOAuthApp([CtVariableReadImpl]clientId, [CtVariableReadImpl]spTenantDomain);
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]thumbPrint = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getThumbPrint([CtVariableReadImpl]publicCert);
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.security.cert.CertificateEncodingException | [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while getting the certificate thumbprint for the " + [CtLiteralImpl]"client_id: ") + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" with the tenant domain: ") + [CtVariableReadImpl]spTenantDomain, [CtVariableReadImpl]e);
            }
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Fetching public keys for the client %s from jwks uri %s", [CtVariableReadImpl]clientId, [CtVariableReadImpl]jwksUri));
            }
            [CtAssignmentImpl][CtVariableWriteImpl]publicCert = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPublicCertFromJWKS([CtVariableReadImpl]jwksUri);
            [CtAssignmentImpl][CtVariableWriteImpl]thumbPrint = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getJwkThumbPrint([CtVariableReadImpl]publicCert);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.Key publicKey = [CtInvocationImpl][CtVariableReadImpl]publicCert.getPublicKey();
        [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.encryptWithPublicKey([CtVariableReadImpl]publicKey, [CtVariableReadImpl]jwtClaimsSet, [CtVariableReadImpl]encryptionAlgorithm, [CtVariableReadImpl]encryptionMethod, [CtVariableReadImpl]spTenantDomain, [CtVariableReadImpl]clientId, [CtVariableReadImpl]thumbPrint);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encrypt the JWT token with with given public key.
     *
     * @param publicKey
     * 		public key used to encrypt
     * @param jwtClaimsSet
     * 		contains JWT body
     * @param encryptionAlgorithm
     * 		JWT signing algorithm
     * @param spTenantDomain
     * 		Service provider tenant domain
     * @param clientId
     * 		ID of the client
     * @param thumbPrint
     * 		value used as 'kid'
     * @return encrypted JWT token
     * @throws IdentityOAuth2Exception
     */
    private static [CtTypeReferenceImpl]com.nimbusds.jwt.JWT encryptWithPublicKey([CtParameterImpl][CtTypeReferenceImpl]java.security.Key publicKey, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jwt.JWTClaimsSet jwtClaimsSet, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm encryptionAlgorithm, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.EncryptionMethod encryptionMethod, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String spTenantDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String thumbPrint) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEHeader.Builder headerBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEHeader.Builder([CtVariableReadImpl]encryptionAlgorithm, [CtVariableReadImpl]encryptionMethod);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]headerBuilder.keyID([CtVariableReadImpl]thumbPrint);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEHeader header = [CtInvocationImpl][CtVariableReadImpl]headerBuilder.build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jwt.EncryptedJWT encryptedJWT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jwt.EncryptedJWT([CtVariableReadImpl]header, [CtVariableReadImpl]jwtClaimsSet);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Encrypting JWT using the algorithm: " + [CtVariableReadImpl]encryptionAlgorithm) + [CtLiteralImpl]", method: ") + [CtVariableReadImpl]encryptionMethod) + [CtLiteralImpl]", tenant: ") + [CtVariableReadImpl]spTenantDomain) + [CtLiteralImpl]" & header: ") + [CtInvocationImpl][CtVariableReadImpl]header.toString());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEEncrypter encrypter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jose.crypto.RSAEncrypter([CtVariableReadImpl](([CtTypeReferenceImpl]java.security.interfaces.RSAPublicKey) (publicKey)));
            [CtInvocationImpl][CtVariableReadImpl]encryptedJWT.encrypt([CtVariableReadImpl]encrypter);
            [CtReturnImpl]return [CtVariableReadImpl]encryptedJWT;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JOSEException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while encrypting JWT for the client_id: " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" with the tenant domain: ") + [CtVariableReadImpl]spTenantDomain, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create JWSSigner using the server level configurations and return.
     *
     * @param privateKey
     * 		RSA Private key.
     * @return JWSSigner
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jose.JWSSigner createJWSSigner([CtParameterImpl][CtTypeReferenceImpl]java.security.interfaces.RSAPrivateKey privateKey) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allowWeakKey = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.ALLOW_WEAK_RSA_SIGNER_KEY));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allowWeakKey && [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"System flag 'allow_weak_rsa_signer_key' is  enabled. So weak keys (key length less than 2048) " + [CtLiteralImpl]" will be used for the signing.");
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jose.crypto.RSASSASigner([CtVariableReadImpl]privateKey, [CtVariableReadImpl]allowWeakKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generic Signing function
     *
     * @param jwtClaimsSet
     * 		contains JWT body
     * @param signatureAlgorithm
     * 		JWT signing algorithm
     * @param tenantDomain
     * 		tenant domain
     * @return signed JWT token
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]com.nimbusds.jwt.JWT signJWT([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jwt.JWTClaimsSet jwtClaimsSet, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm signatureAlgorithm, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS256.equals([CtVariableReadImpl]signatureAlgorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS384.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS512.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.PS256.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.signJWTWithRSA([CtVariableReadImpl]jwtClaimsSet, [CtVariableReadImpl]signatureAlgorithm, [CtVariableReadImpl]tenantDomain);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS256.equals([CtVariableReadImpl]signatureAlgorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS384.equals([CtVariableReadImpl]signatureAlgorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.HS512.equals([CtVariableReadImpl]signatureAlgorithm)) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// return signWithHMAC(jwtClaimsSet,jwsAlgorithm,request); implementation need to be done
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided signature algorithm: " + [CtVariableReadImpl]signatureAlgorithm) + [CtLiteralImpl]" is not supported");
        } else [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// return signWithEC(jwtClaimsSet,jwsAlgorithm,request); implementation need to be done
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Provided signature algorithm: " + [CtVariableReadImpl]signatureAlgorithm) + [CtLiteralImpl]" is not supported");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * sign JWT token from RSA algorithm
     *
     * @param jwtClaimsSet
     * 		contains JWT body
     * @param signatureAlgorithm
     * 		JWT signing algorithm
     * @param tenantDomain
     * 		tenant domain
     * @return signed JWT token
     * @throws IdentityOAuth2Exception
     */
    [CtCommentImpl]// TODO: Can make this private after removing deprecated "signJWTWithRSA" methods in DefaultIDTokenBuilder
    public static [CtTypeReferenceImpl]com.nimbusds.jwt.JWT signJWTWithRSA([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jwt.JWTClaimsSet jwtClaimsSet, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm signatureAlgorithm, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]tenantDomain)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]tenantDomain = [CtFieldReadImpl]org.wso2.carbon.utils.multitenancy.MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Assign super tenant domain as signing domain.");
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Signing JWT using the algorithm: " + [CtVariableReadImpl]signatureAlgorithm) + [CtLiteralImpl]" & key of the tenant: ") + [CtVariableReadImpl]tenantDomain);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.getTenantId([CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.Key privateKey = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getPrivateKey([CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]tenantId);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSSigner signer = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.createJWSSigner([CtVariableReadImpl](([CtTypeReferenceImpl]java.security.interfaces.RSAPrivateKey) (privateKey)));
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSHeader.Builder headerBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSHeader.Builder([CtVariableReadImpl](([CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm) (signatureAlgorithm)));
            [CtInvocationImpl][CtVariableReadImpl]headerBuilder.keyID([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getKID([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getThumbPrint([CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]tenantId), [CtVariableReadImpl]signatureAlgorithm));
            [CtInvocationImpl][CtVariableReadImpl]headerBuilder.x509CertThumbprint([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jose.util.Base64URL([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getThumbPrint([CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]tenantId)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jwt.SignedJWT signedJWT = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.nimbusds.jwt.SignedJWT([CtInvocationImpl][CtVariableReadImpl]headerBuilder.build(), [CtVariableReadImpl]jwtClaimsSet);
            [CtInvocationImpl][CtVariableReadImpl]signedJWT.sign([CtVariableReadImpl]signer);
            [CtReturnImpl]return [CtVariableReadImpl]signedJWT;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.JOSEException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Error occurred while signing JWT", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.security.Key getPrivateKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain, [CtParameterImpl][CtTypeReferenceImpl]int tenantId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.Key privateKey;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.privateKeys.containsKey([CtVariableReadImpl]tenantId)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.initializeRegistry([CtVariableReadImpl]tenantId, [CtVariableReadImpl]tenantDomain);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.base.IdentityException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while loading registry for tenant " + [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl][CtCommentImpl]// get tenant's key store manager
            [CtTypeReferenceImpl]org.wso2.carbon.core.util.KeyStoreManager tenantKSM = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.core.util.KeyStoreManager.getInstance([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tenantDomain.equals([CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// derive key store name
                [CtTypeReferenceImpl]java.lang.String ksName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tenantDomain.trim().replace([CtLiteralImpl]".", [CtLiteralImpl]"-");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jksName = [CtBinaryOperatorImpl][CtVariableReadImpl]ksName + [CtLiteralImpl]".jks";
                [CtAssignmentImpl][CtCommentImpl]// obtain private key
                [CtVariableWriteImpl]privateKey = [CtInvocationImpl][CtVariableReadImpl]tenantKSM.getPrivateKey([CtVariableReadImpl]jksName, [CtVariableReadImpl]tenantDomain);
            } else [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]privateKey = [CtInvocationImpl][CtVariableReadImpl]tenantKSM.getDefaultPrivateKey();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Error while obtaining private key for super tenant", [CtVariableReadImpl]e);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// privateKey will not be null always
            [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.privateKeys.put([CtVariableReadImpl]tenantId, [CtVariableReadImpl]privateKey);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// privateKey will not be null because containsKey() true says given key is exist and ConcurrentHashMap
            [CtCommentImpl]// does not allow to store null values
            [CtVariableWriteImpl]privateKey = [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.privateKeys.get([CtVariableReadImpl]tenantId);
        }
        [CtReturnImpl]return [CtVariableReadImpl]privateKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method to add algo into to JWT_HEADER to signature verification.
     *
     * @param certThumbprint
     * @param signatureAlgorithm
     * @return  */
    public static [CtTypeReferenceImpl]java.lang.String getKID([CtParameterImpl][CtTypeReferenceImpl]java.lang.String certThumbprint, [CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWSAlgorithm signatureAlgorithm) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]certThumbprint + [CtLiteralImpl]"_") + [CtInvocationImpl][CtVariableReadImpl]signatureAlgorithm.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method to add public certificate to JWT_HEADER to signature verification.
     *
     * @param tenantDomain
     * @param tenantId
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getThumbPrint([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain, [CtParameterImpl][CtTypeReferenceImpl]int tenantId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.Certificate certificate = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getCertificate([CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]tenantId);
            [CtReturnImpl][CtCommentImpl]// TODO: maintain a hashmap with tenants' pubkey thumbprints after first initialization
            return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getThumbPrint([CtVariableReadImpl]certificate);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Error in obtaining certificate for tenant " + [CtVariableReadImpl]tenantDomain;
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]error, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method to add public certificate to JWT_HEADER to signature verification.
     * This creates thumbPrints directly from given certificates
     *
     * @param certificate
     * @param alias
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getThumbPrint([CtParameterImpl][CtTypeReferenceImpl]java.security.cert.Certificate certificate, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getThumbPrint([CtVariableReadImpl]certificate);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.cert.CertificateEncodingException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while encoding thumbPrint for alias: " + [CtVariableReadImpl]alias;
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]error, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Error in obtaining SHA-1 thumbprint for alias: " + [CtVariableReadImpl]alias;
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]error, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getThumbPrint([CtParameterImpl][CtTypeReferenceImpl]java.security.cert.Certificate certificate) throws [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException, [CtTypeReferenceImpl]java.security.cert.CertificateEncodingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Generate the SHA-256 thumbprint of the certificate.
        [CtTypeReferenceImpl]java.security.MessageDigest digestValue = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtLiteralImpl]"SHA-256");
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] der = [CtInvocationImpl][CtVariableReadImpl]certificate.getEncoded();
        [CtInvocationImpl][CtVariableReadImpl]digestValue.update([CtVariableReadImpl]der);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] digestInBytes = [CtInvocationImpl][CtVariableReadImpl]digestValue.digest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String publicCertThumbprint = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.hexify([CtVariableReadImpl]digestInBytes);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.codec.binary.Base64([CtLiteralImpl]0, [CtLiteralImpl]null, [CtLiteralImpl]true).encode([CtInvocationImpl][CtVariableReadImpl]publicCertThumbprint.getBytes([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8)), [CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isRSAAlgorithm([CtParameterImpl][CtTypeReferenceImpl]com.nimbusds.jose.JWEAlgorithm algorithm) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]JWEAlgorithm.RSA_OAEP.equals([CtVariableReadImpl]algorithm) || [CtInvocationImpl][CtTypeAccessImpl]JWEAlgorithm.RSA1_5.equals([CtVariableReadImpl]algorithm)) || [CtInvocationImpl][CtTypeAccessImpl]JWEAlgorithm.RSA_OAEP_256.equals([CtVariableReadImpl]algorithm);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.security.cert.Certificate getCertificate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain, [CtParameterImpl][CtTypeReferenceImpl]int tenantId) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.Certificate publicCert = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.publicCerts.containsKey([CtVariableReadImpl]tenantId)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityTenantUtil.initializeRegistry([CtVariableReadImpl]tenantId, [CtVariableReadImpl]tenantDomain);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.base.IdentityException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while loading registry for tenant " + [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl][CtCommentImpl]// get tenant's key store manager
            [CtTypeReferenceImpl]org.wso2.carbon.core.util.KeyStoreManager tenantKSM = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.core.util.KeyStoreManager.getInstance([CtVariableReadImpl]tenantId);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.KeyStore keyStore = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tenantDomain.equals([CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// derive key store name
                [CtTypeReferenceImpl]java.lang.String ksName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tenantDomain.trim().replace([CtLiteralImpl]".", [CtLiteralImpl]"-");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jksName = [CtBinaryOperatorImpl][CtVariableReadImpl]ksName + [CtLiteralImpl]".jks";
                [CtAssignmentImpl][CtVariableWriteImpl]keyStore = [CtInvocationImpl][CtVariableReadImpl]tenantKSM.getKeyStore([CtVariableReadImpl]jksName);
                [CtAssignmentImpl][CtVariableWriteImpl]publicCert = [CtInvocationImpl][CtVariableReadImpl]keyStore.getCertificate([CtVariableReadImpl]tenantDomain);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]publicCert = [CtInvocationImpl][CtVariableReadImpl]tenantKSM.getDefaultPrimaryCertificate();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]publicCert != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.publicCerts.put([CtVariableReadImpl]tenantId, [CtVariableReadImpl]publicCert);
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]publicCert = [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.publicCerts.get([CtVariableReadImpl]tenantId);
        }
        [CtReturnImpl]return [CtVariableReadImpl]publicCert;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method to hexify a byte array.
     * TODO:need to verify the logic
     *
     * @param bytes
     * @return hexadecimal representation
     */
    private static [CtTypeReferenceImpl]java.lang.String hexify([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] bytes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]char[] hexDigits = [CtNewArrayImpl]new char[]{ [CtLiteralImpl]'0', [CtLiteralImpl]'1', [CtLiteralImpl]'2', [CtLiteralImpl]'3', [CtLiteralImpl]'4', [CtLiteralImpl]'5', [CtLiteralImpl]'6', [CtLiteralImpl]'7', [CtUnaryOperatorImpl]+[CtLiteralImpl]'8', [CtLiteralImpl]'9', [CtLiteralImpl]'a', [CtLiteralImpl]'b', [CtLiteralImpl]'c', [CtLiteralImpl]'d', [CtLiteralImpl]'e', [CtLiteralImpl]'f' };
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder buf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]bytes.length * [CtLiteralImpl]2);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]bytes.length; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]buf.append([CtArrayReadImpl][CtVariableReadImpl]hexDigits[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]bytes[[CtVariableReadImpl]i] & [CtLiteralImpl]0xf0) >> [CtLiteralImpl]4]);
            [CtInvocationImpl][CtVariableReadImpl]buf.append([CtArrayReadImpl][CtVariableReadImpl]hexDigits[[CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]bytes[[CtVariableReadImpl]i] & [CtLiteralImpl]0xf]);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]buf.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns essential claims according to claim type: id_token/userinfo .
     *
     * @param essentialClaims
     * @param claimType
     * @return essential claims list
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getEssentialClaims([CtParameterImpl][CtTypeReferenceImpl]java.lang.String essentialClaims, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject jsonObjectClaims = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject([CtVariableReadImpl]essentialClaims);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> essentialClaimsList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jsonObjectClaims.toString().contains([CtVariableReadImpl]claimType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject newJSON = [CtInvocationImpl][CtVariableReadImpl]jsonObjectClaims.getJSONObject([CtVariableReadImpl]claimType);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newJSON != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtWildcardReferenceImpl]?> keys = [CtInvocationImpl][CtVariableReadImpl]newJSON.keys();
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]keys.hasNext()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]keys.next()));
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]newJSON.isNull([CtVariableReadImpl]key)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String value = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]newJSON.get([CtVariableReadImpl]key).toString();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject jsonObjectValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject([CtVariableReadImpl]value);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtWildcardReferenceImpl]?> claimKeyValues = [CtInvocationImpl][CtVariableReadImpl]jsonObjectValues.keys();
                        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]claimKeyValues.hasNext()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String claimKey = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]claimKeyValues.next()));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String claimValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jsonObjectValues.get([CtVariableReadImpl]claimKey).toString();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtVariableReadImpl]claimValue) && [CtInvocationImpl][CtVariableReadImpl]claimKey.equals([CtTypeAccessImpl]OAuthConstants.OAuth20Params.ESSENTIAL)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]essentialClaimsList.add([CtVariableReadImpl]key);
                            }
                        } 
                    }
                } 
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]essentialClaimsList;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the domain name convert to upper case if the domain is not not empty, else return primary domain name.
     *
     * @param userStoreDomain
     * @return  */
    public static [CtTypeReferenceImpl]java.lang.String getSanitizedUserStoreDomain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]userStoreDomain)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl][CtVariableReadImpl]userStoreDomain.toUpperCase();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.getPrimaryDomainName();
        }
        [CtReturnImpl]return [CtVariableReadImpl]userStoreDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the mapped user store domain representation federated users according to the MapFederatedUsersToLocal
     * configuration in the identity.xml file.
     *
     * @param authenticatedUser
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.lang.String getUserStoreForFederatedUser([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]authenticatedUser == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Authenticated user cannot be null");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserStoreDomainFromUserId([CtInvocationImpl][CtVariableReadImpl]authenticatedUser.toString());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal()) && [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.isFederatedUser()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isIDPIdColumnEnabled()) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// When the IDP_ID column is available it was decided to set the
                [CtCommentImpl]// domain name for federated users to 'FEDERATED'.
                [CtCommentImpl]// This is a system reserved word and users stores cannot be created with this name.
                [CtVariableWriteImpl]userStoreDomain = [CtFieldReadImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.FEDERATED_IDP_NAME;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getFederatedUserDomain([CtInvocationImpl][CtVariableReadImpl]authenticatedUser.getFederatedIdPName());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]userStoreDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns Base64 encoded token which have username appended.
     *
     * @param authenticatedUser
     * @param token
     * @return  */
    public static [CtTypeReferenceImpl]java.lang.String addUsernameToToken([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]authenticatedUser == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Authenticated user cannot be null");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]token)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Token cannot be blank");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String usernameForToken = [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.toString();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal()) && [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.isFederatedUser()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]usernameForToken = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getFederatedUserDomain([CtInvocationImpl][CtVariableReadImpl]authenticatedUser.getFederatedIdPName());
            [CtAssignmentImpl][CtVariableWriteImpl]usernameForToken = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]usernameForToken + [CtFieldReadImpl]org.wso2.carbon.user.core.UserCoreConstants.DOMAIN_SEPARATOR) + [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.getAuthenticatedSubjectIdentifier();
        }
        [CtLocalVariableImpl][CtCommentImpl]// use ':' for token & userStoreDomain separation
        [CtTypeReferenceImpl]java.lang.String tokenStrToEncode = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]token + [CtLiteralImpl]":") + [CtVariableReadImpl]usernameForToken;
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.axiom.util.base64.Base64Utils.encode([CtInvocationImpl][CtVariableReadImpl]tokenStrToEncode.getBytes([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.Charsets.[CtFieldReferenceImpl]UTF_8));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validates the json provided.
     *
     * @param redirectURL
     * 		redirect url
     * @return true if a valid json
     */
    public static [CtTypeReferenceImpl]boolean isValidJson([CtParameterImpl][CtTypeReferenceImpl]java.lang.String redirectURL) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject([CtVariableReadImpl]redirectURL);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method returns essential:true claims list from the request parameter of OIDC authorization request
     *
     * @param claimRequestor
     * 		claimrequestor is either id_token or  userinfo
     * @param requestedClaimsFromRequestParam
     * 		claims defined in the value of the request parameter
     * @return the claim list which have attribute vale essentail :true
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> essentialClaimsFromRequestParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String claimRequestor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.openidconnect.model.RequestedClaim>> requestedClaimsFromRequestParam) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> essentialClaimsfromRequestParam = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.openidconnect.model.RequestedClaim> claimsforClaimRequestor = [CtInvocationImpl][CtVariableReadImpl]requestedClaimsFromRequestParam.get([CtVariableReadImpl]claimRequestor);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.isNotEmpty([CtVariableReadImpl]claimsforClaimRequestor)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.openidconnect.model.RequestedClaim claimforClaimRequestor : [CtVariableReadImpl]claimsforClaimRequestor) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String claim = [CtInvocationImpl][CtVariableReadImpl]claimforClaimRequestor.getName();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]claimforClaimRequestor.isEssential()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]essentialClaimsfromRequestParam.add([CtVariableReadImpl]claim);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]essentialClaimsfromRequestParam;
    }

    [CtMethodImpl][CtCommentImpl]/* Get authorized user from the {@link AccessTokenDO}. When getting authorized user we also make sure flag to
    determine whether the user is federated or not is set.

    @param accessTokenDO accessTokenDO
    @return user
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser getAuthenticatedUser([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]authenticatedUser = [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getAuthzUser();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]authenticatedUser != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedUser([CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isFederatedUser([CtVariableReadImpl]authenticatedUser));
        }
        [CtReturnImpl]return [CtVariableReadImpl]authenticatedUser;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the user represented by {@link AuthenticatedUser} object is a federated user.
     *
     * @param authenticatedUser
     * @return true if user is federated, false otherwise.
     */
    public static [CtTypeReferenceImpl]boolean isFederatedUser([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.getUserStoreDomain();
        [CtLocalVariableImpl][CtCommentImpl]// We consider a user federated if the flag for federated user is set or the user store domain contain the
        [CtCommentImpl]// federated user store domain prefix.
        [CtTypeReferenceImpl]boolean isExplicitlyFederatedUser = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.startsWith([CtVariableReadImpl]userStoreDomain, [CtTypeAccessImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX) || [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.isFederatedUser();
        [CtLocalVariableImpl][CtCommentImpl]// Flag to make sure federated user is not mapped to local users.
        [CtTypeReferenceImpl]boolean isFederatedUserNotMappedToLocalUser = [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]isExplicitlyFederatedUser && [CtVariableReadImpl]isFederatedUserNotMappedToLocalUser;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the service provider associated with the OAuth clientId.
     *
     * @param clientId
     * 		OAuth2/OIDC Client Identifier
     * @param tenantDomain
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProvider getServiceProvider([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.mgt.ApplicationManagementService applicationMgtService = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.getApplicationMgtService();
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Get the Service Provider.
            return [CtInvocationImpl][CtVariableReadImpl]applicationMgtService.getServiceProviderByClientId([CtVariableReadImpl]clientId, [CtTypeAccessImpl]IdentityApplicationConstants.OAuth2.NAME, [CtVariableReadImpl]tenantDomain);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.IdentityApplicationManagementException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while obtaining the service provider for client_id: " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" of tenantDomain: ") + [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the service provider associated with the OAuth clientId.
     *
     * @param clientId
     * 		OAuth2/OIDC Client Identifier
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProvider getServiceProvider([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.mgt.ApplicationManagementService applicationMgtService = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.getApplicationMgtService();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tenantDomain = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getTenantDomainOfOauthApp([CtVariableReadImpl]clientId);
            [CtReturnImpl][CtCommentImpl]// Get the Service Provider.
            return [CtInvocationImpl][CtVariableReadImpl]applicationMgtService.getServiceProviderByClientId([CtVariableReadImpl]clientId, [CtTypeAccessImpl]IdentityApplicationConstants.OAuth2.NAME, [CtVariableReadImpl]tenantDomain);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.IdentityApplicationManagementException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while obtaining the service provider for client_id: " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" of tenantDomain: ") + [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Could not find an existing app for clientId: " + [CtVariableReadImpl]clientId, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the public certificate of the service provider associated with the OAuth consumer app as
     * an X509 @{@link Certificate} object.
     *
     * @param clientId
     * 		OAuth2/OIDC Client Identifier
     * @param tenantDomain
     * @return  * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]java.security.cert.Certificate getX509CertOfOAuthApp([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProvider serviceProvider = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getServiceProvider([CtVariableReadImpl]clientId, [CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtCommentImpl]// Get the certificate content.
            [CtTypeReferenceImpl]java.lang.String certificateContent = [CtInvocationImpl][CtVariableReadImpl]serviceProvider.getCertificateContent();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]certificateContent)) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// Build the Certificate object from cert content.
                return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.convertPEMEncodedContentToCertificate([CtVariableReadImpl]certificateContent);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Public certificate not configured for Service Provider with " + [CtLiteralImpl]"client_id: ") + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" of tenantDomain: ") + [CtVariableReadImpl]tenantDomain);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.cert.CertificateException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while building X509 cert of oauth app with client_id: " + [CtVariableReadImpl]clientId) + [CtLiteralImpl]" of tenantDomain: ") + [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the token identifier is JWT.
     *
     * @param tokenIdentifier
     * 		String JWT token identifier.
     * @return true for a JWT token.
     */
    public static [CtTypeReferenceImpl]boolean isJWT([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenIdentifier) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// JWT token contains 3 base64 encoded components separated by periods.
        return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.countMatches([CtVariableReadImpl]tokenIdentifier, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.DOT_SEPARATER) == [CtLiteralImpl]2;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the JWT id token is encrypted.
     *
     * @param idToken
     * 		String JWT ID token.
     * @return Boolean state of encryption.
     */
    public static [CtTypeReferenceImpl]boolean isIDTokenEncrypted([CtParameterImpl][CtTypeReferenceImpl]java.lang.String idToken) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Encrypted ID token contains 5 base64 encoded components separated by periods.
        return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.countMatches([CtVariableReadImpl]idToken, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.DOT_SEPARATER) == [CtLiteralImpl]4;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @deprecated We cannot determine the token issuer this way. Have a look at the
    {@link #findAccessToken(String, boolean)} method.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer getTokenIssuer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer oauthTokenIssuer = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String consumerKey = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isJWT([CtVariableReadImpl]accessToken) || [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isIDTokenEncrypted([CtVariableReadImpl]accessToken)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]oauthTokenIssuer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.JWTTokenIssuer();
        } else [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]consumerKey = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getClientIdForAccessToken([CtVariableReadImpl]accessToken);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]consumerKey != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]oauthTokenIssuer = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getOAuthTokenIssuerForOAuthApp([CtVariableReadImpl]consumerKey);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Consumer key is not found for token identifier: " + [CtVariableReadImpl]accessToken, [CtVariableReadImpl]e);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error while retrieving oauth issuer for the app with clientId: " + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]oauthTokenIssuer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Publish event on token generation error.
     *
     * @param exception
     * 		Exception occurred.
     * @param params
     * 		Additional parameters.
     */
    public static [CtTypeReferenceImpl]void triggerOnTokenExceptionListeners([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable exception, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> params) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor oAuthEventInterceptorProxy = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getOAuthEventInterceptorProxy();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oAuthEventInterceptorProxy != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.onTokenIssueException([CtVariableReadImpl]exception, [CtVariableReadImpl]params);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while invoking OAuthEventInterceptor for onTokenIssueException", [CtVariableReadImpl]e);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Catching a throwable as we do no need to interrupt the code flow since these are logging purposes.
            if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Error occurred while executing oAuthEventInterceptorProxy for onTokenIssueException.", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract information related to the token introspection and publish the event on introspection error.
     *
     * @param 
     */
    public static [CtTypeReferenceImpl]void triggerOnIntrospectionExceptionListeners([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationRequestDTO introspectionRequest, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuth2IntrospectionResponseDTO introspectionResponse) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"error", [CtInvocationImpl][CtVariableReadImpl]introspectionResponse.getError());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor oAuthEventInterceptorProxy = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getOAuthEventInterceptorProxy();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oAuthEventInterceptorProxy != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.onTokenValidationException([CtVariableReadImpl]introspectionRequest, [CtVariableReadImpl]params);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while invoking OAuthEventInterceptor for onTokenValidationException", [CtVariableReadImpl]e);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Catching a throwable as we do no need to interrupt the code flow since these are logging purposes.
            if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Error occurred while executing oAuthEventInterceptorProxy for onTokenValidationException.", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the supported oauth grant types
     *
     * @return list of grant types
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSupportedGrantTypes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.handlers.grant.AuthorizationGrantHandler> supportedGrantTypesMap = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedGrantTypes();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> supportedGrantTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]supportedGrantTypesMap != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]supportedGrantTypesMap.isEmpty())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]supportedGrantTypes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]supportedGrantTypesMap.keySet().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        }
        [CtReturnImpl]return [CtVariableReadImpl]supportedGrantTypes;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the supported client authentication methods
     *
     * @return list of client authentication methods
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSupportedClientAuthenticationMethods() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> clientAuthenticationMethods = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]clientAuthenticationMethods.add([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.CLIENT_SECRET_BASIC);
        [CtInvocationImpl][CtVariableReadImpl]clientAuthenticationMethods.add([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.CLIENT_SECRET_POST);
        [CtReturnImpl]return [CtVariableReadImpl]clientAuthenticationMethods;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the supported request object signing algorithms
     *
     * @return list of algorithms
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getRequestObjectSigningAlgValuesSupported() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> requestObjectSigningAlgValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]requestObjectSigningAlgValues.add([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS256.getName());
        [CtInvocationImpl][CtVariableReadImpl]requestObjectSigningAlgValues.add([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS384.getName());
        [CtInvocationImpl][CtVariableReadImpl]requestObjectSigningAlgValues.add([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.RS512.getName());
        [CtInvocationImpl][CtVariableReadImpl]requestObjectSigningAlgValues.add([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.PS256.getName());
        [CtInvocationImpl][CtVariableReadImpl]requestObjectSigningAlgValues.add([CtInvocationImpl][CtTypeAccessImpl]JWSAlgorithm.NONE.getName());
        [CtReturnImpl]return [CtVariableReadImpl]requestObjectSigningAlgValues;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether the request object parameter is supported
     *
     * @return true if supported
     */
    public static [CtTypeReferenceImpl]boolean isRequestParameterSupported() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether the claims parameter is supported
     *
     * @return true if supported
     */
    public static [CtTypeReferenceImpl]boolean isClaimsParameterSupported() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the federated IdP resolved from the given domain.
     * For a federated user the user store domain is in the format of FEDERATED:{federated-idp-name}
     *
     * @param userStoreDomain
     * 		user store domain to be resolved from
     * @return federated IdP name if user store domain is of format FEDERATED:{federated-idp-name}. Else returns null.
     */
    public static [CtTypeReferenceImpl]java.lang.String getFederatedIdPFromDomain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.startsWith([CtVariableReadImpl]userStoreDomain, [CtTypeAccessImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] tokens = [CtInvocationImpl][CtVariableReadImpl]userStoreDomain.split([CtTypeAccessImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_SEPARATOR);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]tokens.length == [CtLiteralImpl]2) [CtBlockImpl]{
                [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]tokens[[CtLiteralImpl]1];
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an instance on AuthenticatedUser{@link AuthenticatedUser} for the given parameters.
     * If given user store domain is of format FEDERATED:{federated-idp-name}, the authenticated user instance will
     * be flagged as a federated user.
     *
     * @param username
     * 		username of the user
     * @param userStoreDomain
     * 		user store domain
     * @param tenantDomain
     * 		tenent domain
     * @return an instance of AuthenticatedUser{@link AuthenticatedUser}
     * @deprecated use {@link #createAuthenticatedUser(String, String, String, String)} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser createAuthenticatedUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String username, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser();
        [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setUserName([CtVariableReadImpl]username);
        [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setTenantDomain([CtVariableReadImpl]tenantDomain);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.startsWith([CtVariableReadImpl]userStoreDomain, [CtTypeAccessImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal())) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Federated prefix found in domain: " + [CtVariableReadImpl]userStoreDomain) + [CtLiteralImpl]" for user: ") + [CtVariableReadImpl]username) + [CtLiteralImpl]" in tenant domain: ") + [CtVariableReadImpl]tenantDomain) + [CtLiteralImpl]". Flag user as a federated user.");
            }
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedUser([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedIdPName([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getFederatedIdPFromDomain([CtVariableReadImpl]userStoreDomain));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setUserStoreDomain([CtVariableReadImpl]userStoreDomain);
        }
        [CtReturnImpl]return [CtVariableReadImpl]authenticatedUser;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an instance on AuthenticatedUser{@link AuthenticatedUser} for the given parameters.
     *
     * @param username
     * 		username of the user
     * @param userStoreDomain
     * 		user store domain
     * @param tenantDomain
     * 		tenent domain
     * @param idpName
     * 		idp name
     * @return an instance of AuthenticatedUser{@link AuthenticatedUser}
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser createAuthenticatedUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String username, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String idpName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authenticatedUser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser();
        [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setUserName([CtVariableReadImpl]username);
        [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setTenantDomain([CtVariableReadImpl]tenantDomain);
        [CtIfImpl][CtCommentImpl]/* When the IDP_ID column is available it was decided to set the
        domain name for federated users to 'FEDERATED'.
        This is a system reserved word and user stores cannot be created with this name.

        For jwt bearer grant and saml bearer grant types, assertion issuing idp is set as
        the authenticated idp, but this may not always be the idp user is in;
        i.e, for an assertion issued by IS, idp name will be 'LOCAL', yet the user could have been
        authenticated with some external idp.
        Therefore, we cannot stop setting 'FEDERATED' as the user store domain for federated users.
         */
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.startsWith([CtVariableReadImpl]userStoreDomain, [CtTypeAccessImpl]OAuthConstants.UserType.FEDERATED_USER_DOMAIN_PREFIX) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedUser([CtLiteralImpl]true);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isIDPIdColumnEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedIdPName([CtVariableReadImpl]idpName);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setFederatedIdPName([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getFederatedIdPFromDomain([CtVariableReadImpl]userStoreDomain));
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Federated prefix found in domain: " + [CtVariableReadImpl]userStoreDomain) + [CtLiteralImpl]" for user: ") + [CtVariableReadImpl]username) + [CtLiteralImpl]" in tenant domain: ") + [CtVariableReadImpl]tenantDomain) + [CtLiteralImpl]". Flag user as a federated user. ") + [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.getFederatedIdPName()) + [CtLiteralImpl]" is set as the authenticated idp.");
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]authenticatedUser.setUserStoreDomain([CtVariableReadImpl]userStoreDomain);
        }
        [CtReturnImpl]return [CtVariableReadImpl]authenticatedUser;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getIdTokenIssuer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.IdentityProvider identityProvider = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getResidentIdp([CtVariableReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig[] fedAuthnConfigs = [CtInvocationImpl][CtVariableReadImpl]identityProvider.getFederatedAuthenticatorConfigs();
        [CtLocalVariableImpl][CtCommentImpl]// Get OIDC authenticator
        [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig oidcAuthenticatorConfig = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.application.common.util.IdentityApplicationManagementUtil.getFederatedAuthenticator([CtVariableReadImpl]fedAuthnConfigs, [CtTypeAccessImpl]IdentityApplicationConstants.Authenticator.OIDC.NAME);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.application.common.util.IdentityApplicationManagementUtil.getProperty([CtInvocationImpl][CtVariableReadImpl]oidcAuthenticatorConfig.getProperties(), [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.IDP_ENTITY_ID).getValue();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.IdentityProvider getResidentIdp([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.idp.mgt.IdentityProviderManager.getInstance().getResidentIdP([CtVariableReadImpl]tenantDomain);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.idp.mgt.IdentityProviderManagementException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Error while getting Resident Identity Provider of '%s' tenant.", [CtVariableReadImpl]tenantDomain);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to build an OAuth revocation request DTO.
     *
     * @param oAuthClientAuthnContext
     * 		OAuth client authentication context.
     * @param accessToken
     * 		Access token to be revoked.
     * @return Returns a OAuth revocation request DTO.
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuthRevocationRequestDTO buildOAuthRevocationRequest([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.OAuthClientAuthnContext oAuthClientAuthnContext, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuthRevocationRequestDTO revocationRequestDTO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuthRevocationRequestDTO();
        [CtInvocationImpl][CtVariableReadImpl]revocationRequestDTO.setToken([CtVariableReadImpl]accessToken);
        [CtInvocationImpl][CtVariableReadImpl]revocationRequestDTO.setOauthClientAuthnContext([CtVariableReadImpl]oAuthClientAuthnContext);
        [CtInvocationImpl][CtVariableReadImpl]revocationRequestDTO.setConsumerKey([CtInvocationImpl][CtVariableReadImpl]oAuthClientAuthnContext.getClientId());
        [CtReturnImpl]return [CtVariableReadImpl]revocationRequestDTO;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Find access tokenDO from token identifier by chaining through all available token issuers.
     *
     * @param tokenIdentifier
     * 		access token data object from the validation request.
     * @return AccessTokenDO
     * @throws IdentityOAuth2Exception
     */
    public static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO findAccessToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenIdentifier, [CtParameterImpl][CtTypeReferenceImpl]boolean includeExpired) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO;
        [CtLocalVariableImpl][CtCommentImpl]// Get a copy of the list of token issuers .
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> allOAuthTokenIssuerMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOauthTokenIssuerMap());
        [CtLocalVariableImpl][CtCommentImpl]// Differentiate default token issuers and other issuers for better performance.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> defaultOAuthTokenIssuerMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.extractDefaultOauthTokenIssuers([CtVariableReadImpl]allOAuthTokenIssuerMap, [CtVariableReadImpl]defaultOAuthTokenIssuerMap);
        [CtAssignmentImpl][CtCommentImpl]// First try default token issuers.
        [CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOFromMatchingTokenIssuer([CtVariableReadImpl]tokenIdentifier, [CtVariableReadImpl]defaultOAuthTokenIssuerMap, [CtVariableReadImpl]includeExpired);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]accessTokenDO;
        }
        [CtAssignmentImpl][CtCommentImpl]// Loop through other issuer and try to get the hash.
        [CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOFromMatchingTokenIssuer([CtVariableReadImpl]tokenIdentifier, [CtVariableReadImpl]allOAuthTokenIssuerMap, [CtVariableReadImpl]includeExpired);
        [CtIfImpl][CtCommentImpl]// If the lookup is only for tokens in 'ACTIVE' state, APIs calling this method expect an
        [CtCommentImpl]// IllegalArgumentException to be thrown to identify inactive/invalid tokens.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]accessTokenDO == [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtVariableReadImpl]includeExpired)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Invalid Access Token. ACTIVE access token is not found.");
        }
        [CtReturnImpl]return [CtVariableReadImpl]accessTokenDO;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Loop through provided token issuer list and tries to get the access token DO.
     *
     * @param tokenIdentifier
     * 		Provided token identifier.
     * @param tokenIssuerMap
     * 		List of token issuers.
     * @return Obtained matching access token DO if possible.
     * @throws IdentityOAuth2Exception
     */
    private static [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO getAccessTokenDOFromMatchingTokenIssuer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenIdentifier, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> tokenIssuerMap, [CtParameterImpl][CtTypeReferenceImpl]boolean includeExpired) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tokenIssuerMap != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> oauthTokenIssuerEntry : [CtInvocationImpl][CtVariableReadImpl]tokenIssuerMap.entrySet()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer oauthTokenIssuer = [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuerEntry.getValue();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tokenAlias = [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuer.getAccessTokenHash([CtVariableReadImpl]tokenIdentifier);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuer.usePersistedAccessTokenAlias()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOFromTokenIdentifier([CtVariableReadImpl]tokenAlias, [CtVariableReadImpl]includeExpired);
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getAccessTokenDOFromTokenIdentifier([CtVariableReadImpl]tokenIdentifier, [CtVariableReadImpl]includeExpired);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtReturnImpl]return [CtVariableReadImpl]accessTokenDO;
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.oltu.oauth2.common.exception.OAuthSystemException e) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Token issuer: " + [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuerEntry.getKey()) + [CtLiteralImpl]" was tried and") + [CtLiteralImpl]" failed to parse the received token: ") + [CtVariableReadImpl]tokenIdentifier);
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Token issuer: " + [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuerEntry.getKey()) + [CtLiteralImpl]" was tried and") + [CtLiteralImpl]" failed to parse the received token.");
                        }
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Token issuer: " + [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuerEntry.getKey()) + [CtLiteralImpl]" was tried and") + [CtLiteralImpl]" failed to get the token from database: ") + [CtVariableReadImpl]tokenIdentifier);
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Token issuer: " + [CtInvocationImpl][CtVariableReadImpl]oauthTokenIssuerEntry.getKey()) + [CtLiteralImpl]" was tried and") + [CtLiteralImpl]" failed  to get the token from database.");
                        }
                    }
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Differentiate default token issuers from all available token issuers map.
     *
     * @param allOAuthTokenIssuerMap
     * 		Map of all available token issuers.
     * @param defaultOAuthTokenIssuerMap
     * 		default token issuers
     */
    private static [CtTypeReferenceImpl]void extractDefaultOauthTokenIssuers([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> allOAuthTokenIssuerMap, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer> defaultOAuthTokenIssuerMap) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// TODO: 4/9/19 Implement logic to read default issuer from config.
        [CtCommentImpl]// TODO: 4/9/19 add sorting mechanism to use JWT issuer first.
        [CtVariableReadImpl]defaultOAuthTokenIssuerMap.put([CtTypeAccessImpl]OAuthServerConfiguration.JWT_TOKEN_TYPE, [CtInvocationImpl][CtVariableReadImpl]allOAuthTokenIssuerMap.get([CtTypeAccessImpl]OAuthServerConfiguration.JWT_TOKEN_TYPE));
        [CtInvocationImpl][CtVariableReadImpl]allOAuthTokenIssuerMap.remove([CtTypeAccessImpl]OAuthServerConfiguration.JWT_TOKEN_TYPE);
        [CtInvocationImpl][CtVariableReadImpl]defaultOAuthTokenIssuerMap.put([CtTypeAccessImpl]OAuthServerConfiguration.DEFAULT_TOKEN_TYPE, [CtInvocationImpl][CtVariableReadImpl]allOAuthTokenIssuerMap.get([CtTypeAccessImpl]OAuthServerConfiguration.DEFAULT_TOKEN_TYPE));
        [CtInvocationImpl][CtVariableReadImpl]allOAuthTokenIssuerMap.remove([CtTypeAccessImpl]OAuthServerConfiguration.DEFAULT_TOKEN_TYPE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return access token identifier from OAuth2TokenValidationResponseDTO. This method validated the token against
     * the cache and the DB.
     *
     * @param tokenResponse
     * 		OAuth2TokenValidationResponseDTO object.
     * @return extracted access token identifier.
     * @throws UserInfoEndpointException
     */
    public static [CtTypeReferenceImpl]java.lang.String getAccessTokenIdentifier([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationResponseDTO tokenResponse) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.user.UserInfoEndpointException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenResponse.getAuthorizationContextToken().getTokenString() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.findAccessToken([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenResponse.getAuthorizationContextToken().getTokenString(), [CtLiteralImpl]false);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.user.UserInfoEndpointException([CtLiteralImpl]"Error occurred while obtaining access token.", [CtVariableReadImpl]e);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]accessTokenDO != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getAccessToken();
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * There are cases where we store an 'alias' of the token returned to the client as the token inside IS.
     * For example, in the case of JWT access tokens we store the 'jti' claim in the database instead of the
     * actual JWT. Therefore we need to cache an AccessTokenDO with the stored token identifier.
     *
     * @param newTokenBean
     * 		token DO to be added to the cache.
     */
    public static [CtTypeReferenceImpl]void addTokenDOtoCache([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO newTokenBean) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.OauthTokenIssuer tokenIssuer = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tokenIssuer = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getOAuthTokenIssuerForOAuthApp([CtInvocationImpl][CtVariableReadImpl]newTokenBean.getConsumerKey());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tokenAlias = [CtInvocationImpl][CtVariableReadImpl]tokenIssuer.getAccessTokenHash([CtInvocationImpl][CtVariableReadImpl]newTokenBean.getAccessToken());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey accessTokenCacheKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]tokenAlias);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO tokenDO = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO.clone([CtVariableReadImpl]newTokenBean);
            [CtInvocationImpl][CtVariableReadImpl]tokenDO.setAccessToken([CtVariableReadImpl]tokenAlias);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().addToCache([CtVariableReadImpl]accessTokenCacheKey, [CtVariableReadImpl]tokenDO);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Access token DO was added to OAuthCache with cache key: " + [CtInvocationImpl][CtVariableReadImpl]accessTokenCacheKey.getCacheKeyString());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"Access token DO was added to OAuthCache");
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.oltu.oauth2.common.exception.OAuthSystemException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while getting the token alias from token issuer: " + [CtInvocationImpl][CtVariableReadImpl]tokenIssuer.toString()) + [CtLiteralImpl]" for the token: ") + [CtInvocationImpl][CtVariableReadImpl]newTokenBean.getAccessToken(), [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error while getting the token alias from token issuer: " + [CtInvocationImpl][CtVariableReadImpl]tokenIssuer.toString(), [CtVariableReadImpl]e);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isTokenLoggable([CtTypeAccessImpl]IdentityConstants.IdentityTokens.ACCESS_TOKEN)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Error while getting the token issuer for the token: " + [CtInvocationImpl][CtVariableReadImpl]newTokenBean.getAccessToken(), [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Error while getting the token issuer", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to get the authenticated IDP name from a user.
     *
     * @param user
     * 		Authenticated User.
     * @return Returns the authenticated IDP name.
     */
    public static [CtTypeReferenceImpl]java.lang.String getAuthenticatedIDP([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser user) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String authenticatedIDP;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isIDPIdColumnEnabled()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal()) && [CtInvocationImpl][CtVariableReadImpl]user.isFederatedUser()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]authenticatedIDP = [CtInvocationImpl][CtVariableReadImpl]user.getFederatedIdPName();
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IDP_ID column is available. User is federated and not mapped to local users. " + [CtLiteralImpl]"Authenticated IDP is set to:") + [CtVariableReadImpl]authenticatedIDP) + [CtLiteralImpl]" for user:") + [CtInvocationImpl][CtVariableReadImpl]user.toString());
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]authenticatedIDP = [CtFieldReadImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.LOCAL_IDP_NAME;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IDP_ID column is available. Authenticated IDP is set to:" + [CtVariableReadImpl]authenticatedIDP) + [CtLiteralImpl]" for user:") + [CtInvocationImpl][CtVariableReadImpl]user.toString());
                }
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]authenticatedIDP = [CtInvocationImpl][CtVariableReadImpl]user.getFederatedIdPName();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IDP_ID column is not available. Authenticated IDP is set to:" + [CtVariableReadImpl]authenticatedIDP) + [CtLiteralImpl]" for user:") + [CtInvocationImpl][CtVariableReadImpl]user.toString());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]authenticatedIDP;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to get the user store domain name from a user.
     *
     * @param user
     * 		Authenticated User.
     * @return Returns the sanitized user store domain.
     */
    public static [CtTypeReferenceImpl]java.lang.String getUserStoreDomain([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser user) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userDomain;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isIDPIdColumnEnabled() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal())) && [CtInvocationImpl][CtVariableReadImpl]user.isFederatedUser()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"IDP_ID column is available. User is federated and not mapped to local users.");
            }
            [CtAssignmentImpl][CtCommentImpl]// When the IDP_ID column is available it was decided to set the
            [CtCommentImpl]// domain name for federated users to 'FEDERATED'.
            [CtCommentImpl]// This is a system reserved word and users stores cannot be created with this name.
            [CtVariableWriteImpl]userDomain = [CtFieldReadImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants.FEDERATED_IDP_NAME;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isMapFederatedUsersToLocal()) && [CtInvocationImpl][CtVariableReadImpl]user.isFederatedUser()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"IDP_ID column is not available. User is federated and not mapped to local users.");
            }
            [CtAssignmentImpl][CtVariableWriteImpl]userDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getFederatedUserDomain([CtInvocationImpl][CtVariableReadImpl]user.getFederatedIdPName());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userDomain = [CtInvocationImpl][CtVariableReadImpl]user.getUserStoreDomain();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.isIDPIdColumnEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"IDP_ID column is available. User is not federated or mapped to local users.");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"IDP_ID column is not available. User is not federated or mapped to local users.");
                }
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sanitizedUserDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getSanitizedUserStoreDomain([CtVariableReadImpl]userDomain);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User domain is set to:" + [CtVariableReadImpl]sanitizedUserDomain) + [CtLiteralImpl]" for user:") + [CtInvocationImpl][CtVariableReadImpl]user.toString());
        }
        [CtReturnImpl]return [CtVariableReadImpl]sanitizedUserDomain;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if the IDP_ID column is available in the relevant tables.
     *
     * @return True if IDP_ID column is available in all the relevant table.
     */
    public static [CtTypeReferenceImpl]boolean checkIDPIdColumnAvailable() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isIdpIdAvailableInAuthzCodeTable;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isIdpIdAvailableInTokenTable;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isIdpIdAvailableInTokenAuditTable;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String columnIdpId = [CtLiteralImpl]"IDP_ID";
        [CtAssignmentImpl][CtVariableWriteImpl]isIdpIdAvailableInAuthzCodeTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils.isTableColumnExists([CtLiteralImpl]"IDN_OAUTH2_AUTHORIZATION_CODE", [CtVariableReadImpl]columnIdpId);
        [CtAssignmentImpl][CtVariableWriteImpl]isIdpIdAvailableInTokenTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils.isTableColumnExists([CtLiteralImpl]"IDN_OAUTH2_ACCESS_TOKEN", [CtVariableReadImpl]columnIdpId);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().useRetainOldAccessTokens()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]isIdpIdAvailableInTokenAuditTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils.isTableColumnExists([CtLiteralImpl]"IDN_OAUTH2_ACCESS_TOKEN_AUDIT", [CtVariableReadImpl]columnIdpId);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]isIdpIdAvailableInTokenAuditTable = [CtLiteralImpl]true;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Retaining old access tokens in IDN_OAUTH2_ACCESS_TOKEN_AUDIT is disabled, therefore " + [CtLiteralImpl]"ignoring the availability of IDP_ID column in IDN_OAUTH2_ACCESS_TOKEN_AUDIT table.");
            }
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]isIdpIdAvailableInAuthzCodeTable && [CtVariableReadImpl]isIdpIdAvailableInTokenTable) && [CtVariableReadImpl]isIdpIdAvailableInTokenAuditTable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This can be used to load the oauth scope permissions bindings in oauth-scope-bindings.xml file.
     */
    public static [CtTypeReferenceImpl]void initiateOAuthScopePermissionsBindings([CtParameterImpl][CtTypeReferenceImpl]int tenantId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Check login scope is available. If exists, assumes all scopes are loaded using the file.
            if ([CtUnaryOperatorImpl]![CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.hasScopesAlreadyAdded([CtVariableReadImpl]tenantId)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope> scopes = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.loadOauthScopeBinding();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope scope : [CtVariableReadImpl]scopes) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getOAuthScopeDAO().addScope([CtVariableReadImpl]scope, [CtVariableReadImpl]tenantId);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"OAuth scopes are loaded for the tenant : " + [CtVariableReadImpl]tenantId);
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtLiteralImpl]"OAuth scopes are already loaded");
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2ScopeException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while registering OAuth scopes with permissions bindings", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasScopesAlreadyAdded([CtParameterImpl][CtTypeReferenceImpl]int tenantId) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2ScopeServerException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope loginScope = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getOAuthScopeDAO().getScopeByName([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.INTERNAL_LOGIN_SCOPE, [CtVariableReadImpl]tenantId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]loginScope == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.ScopeBinding> scopeBindings = [CtInvocationImpl][CtVariableReadImpl]loginScope.getScopeBindings();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.ScopeBinding scopeBinding : [CtVariableReadImpl]scopeBindings) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.Oauth2ScopeConstants.PERMISSIONS_BINDING_TYPE.equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]scopeBinding.getBindingType())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope> loadOauthScopeBinding() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope> scopes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configDirPath = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.CarbonUtils.getCarbonConfigDirPath();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String confXml = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtVariableReadImpl]configDirPath, [CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.IDENTITY_PATH, [CtTypeAccessImpl]OAuthConstants.OAUTH_SCOPE_BINDING_PATH).toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File configFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]confXml);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]configFile.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtBinaryOperatorImpl][CtLiteralImpl]"OAuth scope binding File is not present at: " + [CtVariableReadImpl]confXml);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamReader parser = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream stream = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]stream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtVariableReadImpl]configFile);
            [CtAssignmentImpl][CtVariableWriteImpl]parser = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.xml.stream.XMLInputFactory.newInstance().createXMLStreamReader([CtVariableReadImpl]stream);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.impl.builder.StAXOMBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.axiom.om.impl.builder.StAXOMBuilder([CtVariableReadImpl]parser);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement documentElement = [CtInvocationImpl][CtVariableReadImpl]builder.getDocumentElement();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator iterator = [CtInvocationImpl][CtVariableReadImpl]documentElement.getChildElements();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement omElement = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]iterator.next()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scopeName = [CtInvocationImpl][CtVariableReadImpl]omElement.getAttributeValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.NAME));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String displayName = [CtInvocationImpl][CtVariableReadImpl]omElement.getAttributeValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.DISPLAY_NAME));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String description = [CtInvocationImpl][CtVariableReadImpl]omElement.getAttributeValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.xml.namespace.QName([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.DESCRIPTION));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> bindingPermissions = [CtInvocationImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.loadScopePermissions([CtVariableReadImpl]omElement);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.ScopeBinding scopeBinding = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.ScopeBinding([CtFieldReadImpl]org.wso2.carbon.identity.oauth2.Oauth2ScopeConstants.PERMISSIONS_BINDING_TYPE, [CtVariableReadImpl]bindingPermissions);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.ScopeBinding> scopeBindings = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtInvocationImpl][CtVariableReadImpl]scopeBindings.add([CtVariableReadImpl]scopeBinding);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope scope = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.bean.Scope([CtVariableReadImpl]scopeName, [CtVariableReadImpl]displayName, [CtVariableReadImpl]scopeBindings, [CtVariableReadImpl]description);
                [CtInvocationImpl][CtVariableReadImpl]scopes.add([CtVariableReadImpl]scope);
            } 
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error while loading scope config.", [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.FileNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtLiteralImpl]"Error while loading email config.", [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parser != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]parser.close();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stream != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityIOStreamUtils.closeInputStream([CtVariableReadImpl]stream);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.xml.stream.XMLStreamException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.error([CtLiteralImpl]"Error while closing XML stream", [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]scopes;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> loadScopePermissions([CtParameterImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement configElement) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> permissions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator it = [CtInvocationImpl][CtVariableReadImpl]configElement.getChildElements();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement element = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]it.next()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator permissonsIterator = [CtInvocationImpl][CtVariableReadImpl]element.getChildElements();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]permissonsIterator.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.axiom.om.OMElement permissionElement = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.axiom.om.OMElement) ([CtVariableReadImpl]permissonsIterator.next()));
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.PERMISSION.equals([CtInvocationImpl][CtVariableReadImpl]permissionElement.getLocalName())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String permisson = [CtInvocationImpl][CtVariableReadImpl]permissionElement.getText();
                    [CtInvocationImpl][CtVariableReadImpl]permissions.add([CtVariableReadImpl]permisson);
                }
            } 
        } 
        [CtReturnImpl]return [CtVariableReadImpl]permissions;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether required token binding available in the request.
     *
     * @param tokenBinding
     * 		token binding.
     * @param request
     * 		http request.
     * @return true if binding is valid.
     */
    public static [CtTypeReferenceImpl]boolean isValidTokenBinding([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.bindings.TokenBinding tokenBinding, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]request == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]tokenBinding == [CtLiteralImpl]null)) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtInvocationImpl][CtVariableReadImpl]tokenBinding.getBindingReference())) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtInvocationImpl][CtVariableReadImpl]tokenBinding.getBindingType())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.token.bindings.TokenBinder> tokenBinderOptional = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.internal.OAuth2ServiceComponentHolder.getInstance().getTokenBinder([CtInvocationImpl][CtVariableReadImpl]tokenBinding.getBindingType());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tokenBinderOptional.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Token binder with type: " + [CtInvocationImpl][CtVariableReadImpl]tokenBinding.getBindingType()) + [CtLiteralImpl]" is not available.");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenBinderOptional.get().isValidTokenBinding([CtVariableReadImpl]request, [CtInvocationImpl][CtVariableReadImpl]tokenBinding.getBindingReference());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get public certificate from JWKS when kid and JWKS Uri is given.
     *
     * @param jwksUri
     * 		- JWKS Uri
     * @return - X509Certificate
     * @throws IdentityOAuth2Exception
     * 		- IdentityOAuth2Exception
     */
    private static [CtTypeReferenceImpl]java.security.cert.X509Certificate getPublicCertFromJWKS([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jwksUri) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Attempting to retrieve public certificate from the Jwks uri: %s.", [CtVariableReadImpl]jwksUri));
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.jwk.JWKSet publicKeys = [CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jose.jwk.JWKSet.load([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtVariableReadImpl]jwksUri));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.jwk.JWK jwk = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.X509Certificate certificate;
            [CtLocalVariableImpl][CtCommentImpl]// Get the first signing JWK from the list
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.nimbusds.jose.jwk.JWK> jwkList = [CtInvocationImpl][CtVariableReadImpl]publicKeys.getKeys();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.jwk.JWK currentJwk : [CtVariableReadImpl]jwkList) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.nimbusds.jose.jwk.KeyUse.SIGNATURE == [CtInvocationImpl][CtVariableReadImpl]currentJwk.getKeyUse()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]jwk = [CtVariableReadImpl]currentJwk;
                    [CtBreakImpl]break;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]jwk != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]certificate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jwk.getParsedX509CertChain().get([CtLiteralImpl]0);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Retrieved the public signing certificate successfully from the " + [CtLiteralImpl]"jwks uri: %s", [CtVariableReadImpl]jwksUri));
                }
                [CtReturnImpl]return [CtVariableReadImpl]certificate;
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to retrieve public certificate from " + [CtLiteralImpl]"jwks uri: %s", [CtVariableReadImpl]jwksUri));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.text.ParseException | [CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to retrieve public certificate from " + [CtLiteralImpl]"jwks uri: %s", [CtVariableReadImpl]jwksUri), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get Jwks uri of SP when clientId and spTenantDomain is provided.
     *
     * @param clientId
     * 		- ClientId
     * @param spTenantDomain
     * 		- Tenant domain
     * @return Jwks Url
     * @throws IdentityOAuth2Exception
     */
    private static [CtTypeReferenceImpl]java.lang.String getSPJwksUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String spTenantDomain) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jwksUri = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProvider serviceProvider = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getServiceProvider([CtVariableReadImpl]clientId, [CtVariableReadImpl]spTenantDomain);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProviderProperty[] spPropertyArray = [CtInvocationImpl][CtVariableReadImpl]serviceProvider.getSpProperties();
        [CtForEachImpl][CtCommentImpl]// get jwks uri from sp-properties
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.ServiceProviderProperty spProperty : [CtVariableReadImpl]spPropertyArray) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Constants.JWKS_URI.equals([CtInvocationImpl][CtVariableReadImpl]spProperty.getName())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]jwksUri = [CtInvocationImpl][CtVariableReadImpl]spProperty.getValue();
                [CtBreakImpl]break;
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Retrieved jwks uri: %s for the service provider associated with client_id: %s", [CtVariableReadImpl]jwksUri, [CtVariableReadImpl]clientId));
        }
        [CtReturnImpl]return [CtVariableReadImpl]jwksUri;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method to extract the SHA-1 JWK thumbprint from certificates.
     *
     * @param certificate
     * 		x509 certificate
     * @return String thumbprint
     * @throws IdentityOAuth2Exception
     * 		When failed to extract thumbprint
     */
    public static [CtTypeReferenceImpl]java.lang.String getJwkThumbPrint([CtParameterImpl][CtTypeReferenceImpl]java.security.cert.Certificate certificate) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Calculating SHA-1 JWK thumb-print for certificate: %s", [CtInvocationImpl][CtVariableReadImpl]certificate.toString()));
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.CertificateFactory cf = [CtInvocationImpl][CtTypeAccessImpl]java.security.cert.CertificateFactory.getInstance([CtTypeAccessImpl]Constants.X509);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayInputStream bais = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]certificate.getEncoded());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.cert.X509Certificate x509 = [CtInvocationImpl](([CtTypeReferenceImpl]java.security.cert.X509Certificate) ([CtVariableReadImpl]cf.generateCertificate([CtVariableReadImpl]bais)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.nimbusds.jose.util.Base64URL jwkThumbprint = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.nimbusds.jose.jwk.RSAKey.parse([CtVariableReadImpl]x509).computeThumbprint([CtTypeAccessImpl]Constants.SHA1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String thumbprintString = [CtInvocationImpl][CtVariableReadImpl]jwkThumbprint.toString();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Calculated SHA-1 JWK thumbprint %s from the certificate", [CtVariableReadImpl]thumbprintString));
            }
            [CtReturnImpl]return [CtVariableReadImpl]thumbprintString;
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.security.cert.CertificateException | [CtTypeReferenceImpl]com.nimbusds.jose.JOSEException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception([CtLiteralImpl]"Error occurred while generating SHA-1 JWK thumbprint", [CtVariableReadImpl]e);
        }
    }
}
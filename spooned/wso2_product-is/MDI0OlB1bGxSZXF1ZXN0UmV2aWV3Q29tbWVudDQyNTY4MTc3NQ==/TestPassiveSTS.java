[CompilationUnitImpl][CtPackageDeclarationImpl]package org.wso2.identity.integration.test.sts;
[CtUnresolvedImport]import org.apache.http.message.BasicNameValuePair;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpPost;
[CtUnresolvedImport]import org.apache.commons.lang.StringUtils;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping;
[CtUnresolvedImport]import org.testng.annotations.DataProvider;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.http.client.entity.UrlEncodedFormEntity;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.ClaimConfig;
[CtUnresolvedImport]import static org.testng.Assert.assertEquals;
[CtUnresolvedImport]import org.testng.annotations.Test;
[CtUnresolvedImport]import org.apache.http.Header;
[CtUnresolvedImport]import org.apache.http.util.EntityUtils;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig;
[CtUnresolvedImport]import org.wso2.identity.integration.test.util.Utils;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import org.testng.Assert;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.Claim;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
[CtUnresolvedImport]import org.testng.annotations.AfterClass;
[CtUnresolvedImport]import org.apache.http.impl.client.CloseableHttpClient;
[CtUnresolvedImport]import org.testng.annotations.Factory;
[CtUnresolvedImport]import org.wso2.identity.integration.common.utils.ISIntegrationTest;
[CtUnresolvedImport]import org.testng.annotations.BeforeClass;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.OutboundProvisioningConfig;
[CtUnresolvedImport]import org.apache.http.HttpEntity;
[CtUnresolvedImport]import org.wso2.carbon.automation.engine.context.TestUserMode;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import static org.testng.Assert.assertNotNull;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.model.xsd.Property;
[CtUnresolvedImport]import org.wso2.identity.integration.test.utils.DataExtractUtil;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpGet;
[CtUnresolvedImport]import org.wso2.identity.integration.common.clients.application.mgt.ApplicationManagementServiceClient;
[CtUnresolvedImport]import org.wso2.carbon.automation.engine.context.AutomationContext;
[CtUnresolvedImport]import org.apache.http.HttpResponse;
[CtUnresolvedImport]import org.apache.http.impl.client.HttpClientBuilder;
[CtUnresolvedImport]import org.apache.http.NameValuePair;
[CtUnresolvedImport]import org.wso2.carbon.integration.common.admin.client.AuthenticatorClient;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import static org.testng.Assert.assertTrue;
[CtClassImpl]public class TestPassiveSTS extends [CtTypeReferenceImpl]org.wso2.identity.integration.common.utils.ISIntegrationTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SERVICE_PROVIDER_NAME = [CtLiteralImpl]"PassiveSTSSampleApp";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SERVICE_PROVIDER_Desc = [CtLiteralImpl]"PassiveSTS Service Provider";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EMAIL_CLAIM_URI = [CtLiteralImpl]"http://wso2.org/claims/emailaddress";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String GIVEN_NAME_CLAIM_URI = [CtLiteralImpl]"http://wso2.org/claims/givenname";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PASSIVE_STS_SAMPLE_APP_URL = [CtLiteralImpl]"http://localhost:8490/PassiveSTSSampleApp";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COMMON_AUTH_URL = [CtLiteralImpl]"https://localhost:9853/commonauth";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String HTTP_RESPONSE_HEADER_LOCATION = [CtLiteralImpl]"location";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String USER_AGENT = [CtLiteralImpl]"Apache-HttpClient/4.2.5 (java 1.6)";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String username;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String userPassword;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String tenantDomain;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String sessionDataKey;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.http.Header locationHeader;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String passiveStsURL;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.wso2.identity.integration.common.clients.application.mgt.ApplicationManagementServiceClient appMgtClient;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider serviceProvider;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.http.impl.client.CloseableHttpClient client;

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.DataProvider(name = [CtLiteralImpl]"configProvider")
    public static [CtArrayTypeReferenceImpl]java.lang.Object[][] configProvider() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[][]{ [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.wso2.carbon.automation.engine.context.TestUserMode.SUPER_TENANT_ADMIN }, [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.wso2.carbon.automation.engine.context.TestUserMode.TENANT_ADMIN } };
    }

    [CtConstructorImpl][CtAnnotationImpl]@org.testng.annotations.Factory(dataProvider = [CtLiteralImpl]"configProvider")
    public TestPassiveSTS([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.TestUserMode userMode) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.init([CtVariableReadImpl]userMode);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.AutomationContext context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.AutomationContext([CtLiteralImpl]"IDENTITY", [CtVariableReadImpl]userMode);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.username = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getTenantAdmin().getUserName();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.userPassword = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getTenantAdmin().getPassword();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getDomain();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.BeforeClass(alwaysRun = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void testInit() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.integration.common.admin.client.AuthenticatorClient logManger = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.integration.common.admin.client.AuthenticatorClient([CtFieldReadImpl]backendURL);
        [CtInvocationImpl][CtVariableReadImpl]logManger.login([CtFieldReadImpl]username, [CtFieldReadImpl]userPassword, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]isServer.getInstance().getHosts().get([CtLiteralImpl]"default"));
        [CtAssignmentImpl][CtFieldWriteImpl]appMgtClient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.identity.integration.common.clients.application.mgt.ApplicationManagementServiceClient([CtFieldReadImpl]sessionCookie, [CtFieldReadImpl]backendURL, [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.http.impl.client.HttpClientBuilder.create().build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String isURL = [CtInvocationImpl][CtFieldReadImpl]backendURL.substring([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]backendURL.indexOf([CtLiteralImpl]"services/"));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.passiveStsURL = [CtBinaryOperatorImpl][CtVariableReadImpl]isURL + [CtLiteralImpl]"passivests";
        [CtInvocationImpl]setSystemProperties();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.AfterClass(alwaysRun = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void atEnd() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]appMgtClient.deleteApplication([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Add service provider")
    public [CtTypeReferenceImpl]void testAddSP() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]serviceProvider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider();
        [CtInvocationImpl][CtFieldReadImpl]serviceProvider.setApplicationName([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME);
        [CtInvocationImpl][CtFieldReadImpl]serviceProvider.setDescription([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_Desc);
        [CtInvocationImpl][CtFieldReadImpl]appMgtClient.createApplication([CtFieldReadImpl]serviceProvider);
        [CtAssignmentImpl][CtFieldWriteImpl]serviceProvider = [CtInvocationImpl][CtFieldReadImpl]appMgtClient.getApplication([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtFieldReadImpl]serviceProvider, [CtBinaryOperatorImpl][CtLiteralImpl]"Service provider registration failed for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Update service provider with passiveSTS configs", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testAddSP" })
    public [CtTypeReferenceImpl]void testUpdateSP() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]serviceProvider.setOutboundProvisioningConfig([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.OutboundProvisioningConfig());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig> authRequestList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig opicAuthenticationRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig();
        [CtInvocationImpl][CtVariableReadImpl]opicAuthenticationRequest.setInboundAuthKey([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME);
        [CtInvocationImpl][CtVariableReadImpl]opicAuthenticationRequest.setInboundAuthType([CtLiteralImpl]"passivests");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Property property = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Property();
        [CtInvocationImpl][CtVariableReadImpl]property.setName([CtLiteralImpl]"passiveSTSWReply");
        [CtInvocationImpl][CtVariableReadImpl]property.setValue([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.PASSIVE_STS_SAMPLE_APP_URL);
        [CtInvocationImpl][CtVariableReadImpl]opicAuthenticationRequest.setProperties([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Property[]{ [CtVariableReadImpl]property });
        [CtInvocationImpl][CtVariableReadImpl]authRequestList.add([CtVariableReadImpl]opicAuthenticationRequest);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]authRequestList.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]serviceProvider.getInboundAuthenticationConfig().setInboundAuthenticationRequestConfigs([CtInvocationImpl][CtVariableReadImpl]authRequestList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig[[CtLiteralImpl]0]));
        }
        [CtInvocationImpl][CtFieldReadImpl]appMgtClient.updateApplicationData([CtFieldReadImpl]serviceProvider);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotEquals([CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]appMgtClient.getApplication([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME).getInboundAuthenticationConfig().getInboundAuthenticationRequestConfigs().length, [CtLiteralImpl]0, [CtBinaryOperatorImpl][CtLiteralImpl]"Fail to update service provider with passiveSTS configs for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Update service provider with claim configurations", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testUpdateSP" })
    public [CtTypeReferenceImpl]void testAddClaimConfiguration() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]serviceProvider.getClaimConfig().setClaimMappings([CtInvocationImpl]getClaimMappings());
        [CtInvocationImpl][CtFieldReadImpl]appMgtClient.updateApplicationData([CtFieldReadImpl]serviceProvider);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider updatedServiceProvider = [CtInvocationImpl][CtFieldReadImpl]appMgtClient.getApplication([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.SERVICE_PROVIDER_NAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimConfig updatedClaimConfig = [CtInvocationImpl][CtVariableReadImpl]updatedServiceProvider.getClaimConfig();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int arraySize = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]updatedClaimConfig.getClaimMappings().length;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] claimsUris = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtVariableReadImpl]arraySize];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]index < [CtVariableReadImpl]arraySize; [CtUnaryOperatorImpl][CtVariableWriteImpl]index++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]claimsUris[[CtVariableReadImpl]index] = [CtInvocationImpl][CtInvocationImpl][CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]updatedClaimConfig.getClaimMappings()[[CtVariableReadImpl]index].getLocalClaim().getClaimUri();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> claimsList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]claimsUris);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]claimsList.contains([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.GIVEN_NAME_CLAIM_URI), [CtBinaryOperatorImpl][CtLiteralImpl]"Failed update given name claim uri for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]claimsList.contains([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.EMAIL_CLAIM_URI), [CtBinaryOperatorImpl][CtLiteralImpl]"Failed update email claim uri for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Invoke PassiveSTS endpoint", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testAddClaimConfiguration" })
    public [CtTypeReferenceImpl]void testInvokePassiveSTSEndPoint() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String passiveParams = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"?wreply=" + [CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.PASSIVE_STS_SAMPLE_APP_URL) + [CtLiteralImpl]"&wtrealm=PassiveSTSSampleApp";
        [CtAssignmentImpl][CtVariableWriteImpl]passiveParams = [CtInvocationImpl]appendTenantDomainQueryParam([CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.passiveStsURL + [CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"PassiveSTSSampleApp invoke response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int responseCode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertEquals([CtVariableReadImpl]responseCode, [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid Response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> keyPositionMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]keyPositionMap.put([CtLiteralImpl]"name=\"sessionDataKey\"", [CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.identity.integration.test.utils.DataExtractUtil.KeyValue> keyValues = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.utils.DataExtractUtil.extractDataFromResponse([CtVariableReadImpl]response, [CtVariableReadImpl]keyPositionMap);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]keyValues, [CtBinaryOperatorImpl][CtLiteralImpl]"sessionDataKey key value is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtAssignmentImpl][CtFieldWriteImpl]sessionDataKey = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyValues.get([CtLiteralImpl]0).getValue();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtFieldReadImpl]sessionDataKey, [CtBinaryOperatorImpl][CtLiteralImpl]"Session data key is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Send login post request", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testInvokePassiveSTSEndPoint" })
    public [CtTypeReferenceImpl]void testSendLoginRequestPost() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.COMMON_AUTH_URL);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.http.NameValuePair> urlParameters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.apache.http.NameValuePair>();
        [CtInvocationImpl][CtVariableReadImpl]urlParameters.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.message.BasicNameValuePair([CtLiteralImpl]"username", [CtFieldReadImpl]username));
        [CtInvocationImpl][CtVariableReadImpl]urlParameters.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.message.BasicNameValuePair([CtLiteralImpl]"password", [CtFieldReadImpl]userPassword));
        [CtInvocationImpl][CtVariableReadImpl]urlParameters.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.message.BasicNameValuePair([CtLiteralImpl]"sessionDataKey", [CtFieldReadImpl]sessionDataKey));
        [CtInvocationImpl][CtVariableReadImpl]request.setEntity([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.entity.UrlEncodedFormEntity([CtVariableReadImpl]urlParameters));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"Login response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]302, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid Response " + [CtLiteralImpl]"for tenant domain: ") + [CtFieldReadImpl]tenantDomain);
        [CtAssignmentImpl][CtFieldWriteImpl]locationHeader = [CtInvocationImpl][CtVariableReadImpl]response.getFirstHeader([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.HTTP_RESPONSE_HEADER_LOCATION);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtFieldReadImpl]locationHeader, [CtBinaryOperatorImpl][CtLiteralImpl]"Login response header is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtIfImpl]if ([CtInvocationImpl]requestMissingClaims([CtVariableReadImpl]response)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pastrCookie = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.util.Utils.getPastreCookie([CtVariableReadImpl]response);
            [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]pastrCookie, [CtBinaryOperatorImpl][CtLiteralImpl]"pastr cookie not found in response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.util.Utils.sendPOSTConsentMessage([CtVariableReadImpl]response, [CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.COMMON_AUTH_URL, [CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.USER_AGENT, [CtInvocationImpl][CtFieldReadImpl]locationHeader.getValue(), [CtFieldReadImpl]client, [CtVariableReadImpl]pastrCookie);
            [CtAssignmentImpl][CtFieldWriteImpl]locationHeader = [CtInvocationImpl][CtVariableReadImpl]response.getFirstHeader([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.HTTP_RESPONSE_HEADER_LOCATION);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet getRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtInvocationImpl][CtFieldReadImpl]locationHeader.getValue());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]getRequest);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Test PassiveSTS SAML2 Assertion", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testSendLoginRequestPost" })
    public [CtTypeReferenceImpl]void testPassiveSAML2Assertion() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String passiveParams = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"?wa=wsignin1.0&wreply=" + [CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.PASSIVE_STS_SAMPLE_APP_URL) + [CtLiteralImpl]"&wtrealm=PassiveSTSSampleApp";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wreqParam = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"&wreq=%3Cwst%3ARequestSecurityToken+xmlns%3Awst%3D%22http%3A%2F%2Fdocs.oasis-open.org" + [CtLiteralImpl]"%2Fws-sx%2Fws-trust%2F200512%22%3E%3Cwst%3ATokenType%3Ehttp%3A%2F%2Fdocs.oasis-open.org") + [CtLiteralImpl]"%2Fwss%2Foasis-wss-saml-token-profile-1.1%23SAMLV2.0%3C%2Fwst%3ATokenType%3E%3C%2Fwst") + [CtLiteralImpl]"%3ARequestSecurityToken%3E";
        [CtAssignmentImpl][CtVariableWriteImpl]passiveParams = [CtInvocationImpl]appendTenantDomainQueryParam([CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.passiveStsURL + [CtVariableReadImpl]passiveParams) + [CtVariableReadImpl]wreqParam);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"PassiveSTSSampleApp invoke response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int responseCode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertEquals([CtVariableReadImpl]responseCode, [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid Response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpEntity entity = [CtInvocationImpl][CtVariableReadImpl]response.getEntity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseString = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtVariableReadImpl]entity, [CtLiteralImpl]"UTF-8");
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]responseString.contains([CtLiteralImpl]"urn:oasis:names:tc:SAML:2.0:assertion"), [CtBinaryOperatorImpl][CtLiteralImpl]"No SAML2 Assertion found for the SAML2 request for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Test PassiveSTS SAML2 Assertion with WReply URL in passive-sts request", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testPassiveSAML2Assertion" })
    public [CtTypeReferenceImpl]void testPassiveSAML2AssertionWithoutWReply() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String passiveParams = [CtLiteralImpl]"?wa=wsignin1.0&wtrealm=PassiveSTSSampleApp";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wreqParam = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"&wreq=%3Cwst%3ARequestSecurityToken+xmlns%3Awst%3D%22http%3A%2F%2Fdocs.oasis-open.org" + [CtLiteralImpl]"%2Fws-sx%2Fws-trust%2F200512%22%3E%3Cwst%3ATokenType%3Ehttp%3A%2F%2Fdocs.oasis-open.org") + [CtLiteralImpl]"%2Fwss%2Foasis-wss-saml-token-profile-1.1%23SAMLV2.0%3C%2Fwst%3ATokenType%3E%3C%2Fwst") + [CtLiteralImpl]"%3ARequestSecurityToken%3E";
        [CtAssignmentImpl][CtVariableWriteImpl]passiveParams = [CtInvocationImpl]appendTenantDomainQueryParam([CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.passiveStsURL + [CtVariableReadImpl]passiveParams) + [CtVariableReadImpl]wreqParam);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"PassiveSTSSampleApp invoke response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int responseCode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertEquals([CtVariableReadImpl]responseCode, [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid Response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpEntity entity = [CtInvocationImpl][CtVariableReadImpl]response.getEntity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseString = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtVariableReadImpl]entity, [CtLiteralImpl]"UTF-8");
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]responseString.contains([CtLiteralImpl]"urn:oasis:names:tc:SAML:2.0:assertion"), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"No SAML2 Assertion found for the SAML2 request without WReply in passive-sts request for " + [CtLiteralImpl]"tenant domain: ") + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Test Soap fault in case invalid WReply URL", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testSendLoginRequestPost" })
    public [CtTypeReferenceImpl]void testPassiveSAML2AssertionForInvalidWReply() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String INVALID_PASSIVE_STS_SAMPLE_APP_URL = [CtBinaryOperatorImpl][CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.PASSIVE_STS_SAMPLE_APP_URL + [CtLiteralImpl]"INVALID";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String passiveParams = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"?wa=wsignin1.0&wreply=" + [CtVariableReadImpl]INVALID_PASSIVE_STS_SAMPLE_APP_URL) + [CtLiteralImpl]"&wtrealm=PassiveSTSSampleApp";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wreqParam = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"&wreq=%3Cwst%3ARequestSecurityToken+xmlns%3Awst%3D%22http%3A%2F%2Fdocs.oasis-open.org" + [CtLiteralImpl]"%2Fws-sx%2Fws-trust%2F200512%22%3E%3Cwst%3ATokenType%3Ehttp%3A%2F%2Fdocs.oasis-open.org") + [CtLiteralImpl]"%2Fwss%2Foasis-wss-saml-token-profile-1.1%23SAMLV2.0%3C%2Fwst%3ATokenType%3E%3C%2Fwst") + [CtLiteralImpl]"%3ARequestSecurityToken%3E";
        [CtAssignmentImpl][CtVariableWriteImpl]passiveParams = [CtInvocationImpl]appendTenantDomainQueryParam([CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.passiveStsURL + [CtVariableReadImpl]passiveParams) + [CtVariableReadImpl]wreqParam);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"PassiveSTSSampleApp invoke response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int responseCode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]responseCode, [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid Response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpEntity entity = [CtInvocationImpl][CtVariableReadImpl]response.getEntity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseString = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtVariableReadImpl]entity, [CtLiteralImpl]"UTF-8");
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]responseString.contains([CtLiteralImpl]"soapenv:Fault"), [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find soap fault for invalid WReply URL for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Test Session Hijacking", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testPassiveSAML2Assertion" })
    public [CtTypeReferenceImpl]void testSessionHijacking() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet getRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtInvocationImpl][CtFieldReadImpl]locationHeader.getValue());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]getRequest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resultPage2 = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.utils.DataExtractUtil.getContentData([CtVariableReadImpl]response);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]resultPage2.contains([CtLiteralImpl]"Authentication Error!"), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Session hijacking is possible for " + [CtLiteralImpl]"tenant domain: ") + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(alwaysRun = [CtLiteralImpl]true, description = [CtLiteralImpl]"Test logout request", dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testPassiveSAML2Assertion", [CtLiteralImpl]"testSessionHijacking" })
    public [CtTypeReferenceImpl]void testSendLogoutRequest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String passiveParams = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"?wa=wsignout1.0&wreply=" + [CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.PASSIVE_STS_SAMPLE_APP_URL) + [CtLiteralImpl]"&wtrealm=PassiveSTSSampleApp";
        [CtAssignmentImpl][CtVariableWriteImpl]passiveParams = [CtInvocationImpl]appendTenantDomainQueryParam([CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.passiveStsURL + [CtVariableReadImpl]passiveParams);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]response, [CtBinaryOperatorImpl][CtLiteralImpl]"PassiveSTSSampleApp logout response is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int responseCode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertEquals([CtVariableReadImpl]responseCode, [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid Response for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> keyPositionMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]keyPositionMap.put([CtLiteralImpl]"name=\"sessionDataKey\"", [CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.identity.integration.test.utils.DataExtractUtil.KeyValue> keyValues = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.utils.DataExtractUtil.extractDataFromResponse([CtVariableReadImpl]response, [CtVariableReadImpl]keyPositionMap);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtVariableReadImpl]keyValues, [CtBinaryOperatorImpl][CtLiteralImpl]"sessionDataKey key value is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
        [CtAssignmentImpl][CtFieldWriteImpl]sessionDataKey = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyValues.get([CtLiteralImpl]0).getValue();
        [CtInvocationImpl][CtTypeAccessImpl]org.testng.Assert.assertNotNull([CtFieldReadImpl]sessionDataKey, [CtBinaryOperatorImpl][CtLiteralImpl]"Session data key is null for tenant domain: " + [CtFieldReadImpl]tenantDomain);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setSystemProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL resourceUrl = [CtInvocationImpl][CtInvocationImpl]getClass().getResource([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]org.wso2.identity.integration.common.utils.ISIntegrationTest.URL_SEPARATOR + [CtLiteralImpl]"keystores") + [CtFieldReadImpl]org.wso2.identity.integration.common.utils.ISIntegrationTest.URL_SEPARATOR) + [CtLiteralImpl]"products") + [CtFieldReadImpl]org.wso2.identity.integration.common.utils.ISIntegrationTest.URL_SEPARATOR) + [CtLiteralImpl]"wso2carbon.jks");
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"javax.net.ssl.trustStore", [CtInvocationImpl][CtVariableReadImpl]resourceUrl.getPath());
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"javax.net.ssl.trustStorePassword", [CtLiteralImpl]"wso2carbon");
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"javax.net.ssl.trustStoreType", [CtLiteralImpl]"JKS");
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping[] getClaimMappings() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping> claimMappingList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Claim givenNameClaim = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Claim();
        [CtInvocationImpl][CtVariableReadImpl]givenNameClaim.setClaimUri([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.GIVEN_NAME_CLAIM_URI);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping givenNameClaimMapping = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping();
        [CtInvocationImpl][CtVariableReadImpl]givenNameClaimMapping.setRequested([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]givenNameClaimMapping.setLocalClaim([CtVariableReadImpl]givenNameClaim);
        [CtInvocationImpl][CtVariableReadImpl]givenNameClaimMapping.setRemoteClaim([CtVariableReadImpl]givenNameClaim);
        [CtInvocationImpl][CtVariableReadImpl]claimMappingList.add([CtVariableReadImpl]givenNameClaimMapping);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Claim emailClaim = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.Claim();
        [CtInvocationImpl][CtVariableReadImpl]emailClaim.setClaimUri([CtFieldReadImpl]org.wso2.identity.integration.test.sts.TestPassiveSTS.EMAIL_CLAIM_URI);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping emailClaimMapping = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping();
        [CtInvocationImpl][CtVariableReadImpl]emailClaimMapping.setRequested([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]emailClaimMapping.setLocalClaim([CtVariableReadImpl]emailClaim);
        [CtInvocationImpl][CtVariableReadImpl]emailClaimMapping.setRemoteClaim([CtVariableReadImpl]emailClaim);
        [CtInvocationImpl][CtVariableReadImpl]claimMappingList.add([CtVariableReadImpl]emailClaimMapping);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]claimMappingList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.model.xsd.ClaimMapping[[CtLiteralImpl]0]);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean requestMissingClaims([CtParameterImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String redirectUrl = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.identity.integration.test.util.Utils.getRedirectUrl([CtVariableReadImpl]response);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]redirectUrl.contains([CtLiteralImpl]"consent.do");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String appendTenantDomainQueryParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String params) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.equals([CtFieldReadImpl]tenantDomain, [CtLiteralImpl]"carbon.super")) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]params + [CtLiteralImpl]"&tenantDomain=") + [CtFieldReadImpl]tenantDomain;
        }
        [CtReturnImpl]return [CtVariableReadImpl]params;
    }
}
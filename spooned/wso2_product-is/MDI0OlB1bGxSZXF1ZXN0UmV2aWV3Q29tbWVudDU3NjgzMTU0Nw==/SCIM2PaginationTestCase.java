[CompilationUnitImpl][CtPackageDeclarationImpl]package org.wso2.identity.integration.test.scim2;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpPost;
[CtUnresolvedImport]import org.wso2.carbon.utils.multitenancy.MultitenantConstants;
[CtUnresolvedImport]import org.testng.annotations.DataProvider;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static org.testng.Assert.assertEquals;
[CtUnresolvedImport]import org.testng.annotations.Test;
[CtUnresolvedImport]import org.json.simple.JSONArray;
[CtUnresolvedImport]import org.apache.commons.codec.binary.Base64;
[CtUnresolvedImport]import org.apache.http.util.EntityUtils;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpDelete;
[CtUnresolvedImport]import org.json.simple.JSONValue;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.http.HttpHeaders;
[CtUnresolvedImport]import org.apache.http.impl.client.CloseableHttpClient;
[CtUnresolvedImport]import org.testng.annotations.Factory;
[CtUnresolvedImport]import org.wso2.identity.integration.common.utils.ISIntegrationTest;
[CtUnresolvedImport]import static org.wso2.identity.integration.test.scim2.SCIM2BaseTestCase.*;
[CtUnresolvedImport]import org.testng.annotations.BeforeClass;
[CtUnresolvedImport]import org.wso2.carbon.automation.engine.context.TestUserMode;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import static org.testng.Assert.assertNotNull;
[CtUnresolvedImport]import org.apache.http.entity.StringEntity;
[CtUnresolvedImport]import org.json.simple.JSONObject;
[CtImportImpl]import java.lang.reflect.Array;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpGet;
[CtUnresolvedImport]import org.wso2.carbon.automation.engine.context.AutomationContext;
[CtUnresolvedImport]import org.apache.http.HttpResponse;
[CtUnresolvedImport]import org.apache.http.impl.client.HttpClients;
[CtClassImpl]public class SCIM2PaginationTestCase extends [CtTypeReferenceImpl]org.wso2.identity.integration.common.utils.ISIntegrationTest {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.http.impl.client.CloseableHttpClient client;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String TOTAL_RESULTS_ATTRIBUTE = [CtLiteralImpl]"totalResults";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String RESOURCES_ATTRIBUTE = [CtLiteralImpl]"Resources";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String START_INDEX_ATTRIBUTE = [CtLiteralImpl]"startIndex";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ITEMS_PER_PAGE_ATTRIBUTE = [CtLiteralImpl]"itemsPerPage";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String START_INDEX = [CtLiteralImpl]"2";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String COUNT = [CtLiteralImpl]"4";

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String adminUsername;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String adminPassword;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String tenant;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int tenantUserCount;

    [CtFieldImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> userIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.BeforeClass(alwaysRun = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void testInit() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.init();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.impl.client.HttpClients.createDefault();
    }

    [CtConstructorImpl][CtAnnotationImpl]@org.testng.annotations.Factory(dataProvider = [CtLiteralImpl]"scim2UserConfigProvider")
    public SCIM2PaginationTestCase([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.TestUserMode userMode) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.AutomationContext context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.automation.engine.context.AutomationContext([CtLiteralImpl]"IDENTITY", [CtVariableReadImpl]userMode);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.adminUsername = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getTenantAdmin().getUserName();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.adminPassword = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getTenantAdmin().getPassword();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tenant = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getDomain();
        [CtAssignmentImpl][CtFieldWriteImpl]tenantUserCount = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContextTenant().getTenantUserList().size();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.DataProvider(name = [CtLiteralImpl]"scim2UserConfigProvider")
    public static [CtArrayTypeReferenceImpl]java.lang.Object[][] scim2UserConfigProvider() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[][]{ [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.wso2.carbon.automation.engine.context.TestUserMode.SUPER_TENANT_ADMIN }, [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.wso2.carbon.automation.engine.context.TestUserMode.TENANT_ADMIN } };
    }

    [CtMethodImpl][CtCommentImpl]// validate user listing with pagination
    [CtAnnotationImpl]@org.testng.annotations.Test(dependsOnMethods = [CtLiteralImpl]"testCreateUser")
    public [CtTypeReferenceImpl]void testUserListingPagination() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getUserPath() + [CtLiteralImpl]"?startIndex=") + [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.START_INDEX) + [CtLiteralImpl]"&count=") + [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.COUNT);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.AUTHORIZATION, [CtInvocationImpl]getAuthzHeader());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.CONTENT_TYPE, [CtLiteralImpl]"application/json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Users " + [CtLiteralImpl]"has not been retrieved successfully");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object responseObj = [CtInvocationImpl][CtTypeAccessImpl]org.json.simple.JSONValue.parse([CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtInvocationImpl][CtVariableReadImpl]response.getEntity()));
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String totalResults = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.TOTAL_RESULTS_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]totalResults, [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.COUNT, [CtLiteralImpl]"total results in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String startIndex = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.START_INDEX_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]startIndex, [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.START_INDEX, [CtLiteralImpl]"startIndex in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String itemsPerPage = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.ITEMS_PER_PAGE_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]itemsPerPage, [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.COUNT, [CtLiteralImpl]"itemsPerPage in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int resourcesSize = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.json.simple.JSONArray) ([CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.RESOURCES_ATTRIBUTE))).size();
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]resourcesSize), [CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.COUNT, [CtLiteralImpl]"resources size in pagination listing is incorrect");
    }

    [CtMethodImpl][CtCommentImpl]// validating legacy behavior support (returning all the results for not specified list param)
    [CtAnnotationImpl]@org.testng.annotations.Test(dependsOnMethods = [CtLiteralImpl]"testCreateUser")
    public [CtTypeReferenceImpl]void testUserListingPaginationWithoutParams() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// current no of users is equal to number of users created  + tenant context users + tenant admin user
        [CtTypeReferenceImpl]int currentUsers = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]userIds.size() + [CtFieldReadImpl]tenantUserCount) + [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtInvocationImpl]getUserPath());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.AUTHORIZATION, [CtInvocationImpl]getAuthzHeader());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.CONTENT_TYPE, [CtLiteralImpl]"application/json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]200, [CtBinaryOperatorImpl][CtLiteralImpl]"Users " + [CtLiteralImpl]"has not been retrieved successfully");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object responseObj = [CtInvocationImpl][CtTypeAccessImpl]org.json.simple.JSONValue.parse([CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtInvocationImpl][CtVariableReadImpl]response.getEntity()));
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String totalResults = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.TOTAL_RESULTS_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]totalResults, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]currentUsers), [CtLiteralImpl]"total results in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String startIndex = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.START_INDEX_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]startIndex, [CtLiteralImpl]"1", [CtLiteralImpl]"startIndex in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String itemsPerPage = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.ITEMS_PER_PAGE_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]itemsPerPage, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]currentUsers), [CtLiteralImpl]"itemsPerPage in pagination listing is incorrect");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int resourcesSize = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.json.simple.JSONArray) ([CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtFieldReadImpl]org.wso2.identity.integration.test.scim2.SCIM2PaginationTestCase.RESOURCES_ATTRIBUTE))).size();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]resourcesSize, [CtVariableReadImpl]currentUsers, [CtLiteralImpl]"resources size in pagination listing is incorrect");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testCreateUser() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]10; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl]createUser([CtBinaryOperatorImpl][CtLiteralImpl]"Family" + [CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtLiteralImpl]"user" + [CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtLiteralImpl]"user" + [CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"user" + [CtVariableReadImpl]i) + [CtLiteralImpl]"@gmail.com", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"user" + [CtVariableReadImpl]i) + [CtLiteralImpl]"@gmail.com", [CtBinaryOperatorImpl][CtLiteralImpl]"dummyPW" + [CtVariableReadImpl]i);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String familyName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String givenName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String workEmail, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String homeEmail, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String password) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost([CtInvocationImpl]getUserPath());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.AUTHORIZATION, [CtInvocationImpl]getAuthzHeader());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.CONTENT_TYPE, [CtLiteralImpl]"application/json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONObject rootObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONArray schemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONArray();
        [CtInvocationImpl][CtVariableReadImpl]rootObject.put([CtTypeAccessImpl]SCHEMAS_ATTRIBUTE, [CtVariableReadImpl]schemas);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONObject names = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONObject();
        [CtInvocationImpl][CtVariableReadImpl]names.put([CtTypeAccessImpl]FAMILY_NAME_ATTRIBUTE, [CtVariableReadImpl]familyName);
        [CtInvocationImpl][CtVariableReadImpl]names.put([CtTypeAccessImpl]GIVEN_NAME_ATTRIBUTE, [CtVariableReadImpl]givenName);
        [CtInvocationImpl][CtVariableReadImpl]rootObject.put([CtTypeAccessImpl]NAME_ATTRIBUTE, [CtVariableReadImpl]names);
        [CtInvocationImpl][CtVariableReadImpl]rootObject.put([CtTypeAccessImpl]USER_NAME_ATTRIBUTE, [CtVariableReadImpl]userName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONObject emailWork = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONObject();
        [CtInvocationImpl][CtVariableReadImpl]emailWork.put([CtTypeAccessImpl]TYPE_PARAM, [CtTypeAccessImpl]EMAIL_TYPE_WORK_ATTRIBUTE);
        [CtInvocationImpl][CtVariableReadImpl]emailWork.put([CtTypeAccessImpl]VALUE_PARAM, [CtVariableReadImpl]workEmail);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONObject emailHome = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONObject();
        [CtInvocationImpl][CtVariableReadImpl]emailHome.put([CtTypeAccessImpl]TYPE_PARAM, [CtTypeAccessImpl]EMAIL_TYPE_HOME_ATTRIBUTE);
        [CtInvocationImpl][CtVariableReadImpl]emailHome.put([CtTypeAccessImpl]VALUE_PARAM, [CtVariableReadImpl]homeEmail);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.simple.JSONArray emails = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.simple.JSONArray();
        [CtInvocationImpl][CtVariableReadImpl]emails.add([CtVariableReadImpl]emailWork);
        [CtInvocationImpl][CtVariableReadImpl]emails.add([CtVariableReadImpl]emailHome);
        [CtInvocationImpl][CtVariableReadImpl]rootObject.put([CtTypeAccessImpl]EMAILS_ATTRIBUTE, [CtVariableReadImpl]emails);
        [CtInvocationImpl][CtVariableReadImpl]rootObject.put([CtTypeAccessImpl]PASSWORD_ATTRIBUTE, [CtVariableReadImpl]password);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.entity.StringEntity entity = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.entity.StringEntity([CtInvocationImpl][CtVariableReadImpl]rootObject.toString());
        [CtInvocationImpl][CtVariableReadImpl]request.setEntity([CtVariableReadImpl]entity);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]201, [CtLiteralImpl]"User has not been created successfully");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object responseObj = [CtInvocationImpl][CtTypeAccessImpl]org.json.simple.JSONValue.parse([CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtInvocationImpl][CtVariableReadImpl]response.getEntity()));
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String usernameFromResponse = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtTypeAccessImpl]USER_NAME_ATTRIBUTE).toString();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]usernameFromResponse, [CtVariableReadImpl]userName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId;
        [CtAssignmentImpl][CtVariableWriteImpl]userId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.json.simple.JSONObject) (responseObj)).get([CtTypeAccessImpl]ID_ATTRIBUTE).toString();
        [CtInvocationImpl][CtFieldReadImpl]userIds.add([CtVariableReadImpl]userId);
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]userId);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test(dependsOnMethods = [CtNewArrayImpl]{ [CtLiteralImpl]"testUserListingPagination", [CtLiteralImpl]"testUserListingPaginationWithoutParams" })
    public [CtTypeReferenceImpl]void testDeleteUser() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId : [CtFieldReadImpl]userIds) [CtBlockImpl]{
            [CtInvocationImpl]deleteUser([CtVariableReadImpl]userId);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void deleteUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userResourcePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getUserPath() + [CtLiteralImpl]"/") + [CtVariableReadImpl]userId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpDelete request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpDelete([CtVariableReadImpl]userResourcePath);
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.AUTHORIZATION, [CtInvocationImpl]getAuthzHeader());
        [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtTypeAccessImpl]HttpHeaders.CONTENT_TYPE, [CtLiteralImpl]"application/json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.HttpResponse response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]204, [CtLiteralImpl]"User has not been retrieved successfully");
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
        [CtAssignmentImpl][CtVariableWriteImpl]userResourcePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getUserPath() + [CtLiteralImpl]"/") + [CtVariableReadImpl]userId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet getRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtVariableReadImpl]userResourcePath);
        [CtInvocationImpl][CtVariableReadImpl]getRequest.addHeader([CtTypeAccessImpl]HttpHeaders.AUTHORIZATION, [CtInvocationImpl]getAuthzHeader());
        [CtInvocationImpl][CtVariableReadImpl]getRequest.addHeader([CtTypeAccessImpl]HttpHeaders.CONTENT_TYPE, [CtLiteralImpl]"application/json");
        [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl][CtFieldReadImpl]client.execute([CtVariableReadImpl]request);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode(), [CtLiteralImpl]404, [CtLiteralImpl]"User has not been deleted successfully");
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.consume([CtInvocationImpl][CtVariableReadImpl]response.getEntity());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getUserPath() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]tenant.equals([CtTypeAccessImpl]MultitenantConstants.SUPER_TENANT_DOMAIN_NAME)) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]SERVER_URL + [CtFieldReadImpl]SCIM2_USERS_ENDPOINT;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]SERVER_URL + [CtLiteralImpl]"/t/") + [CtFieldReadImpl]tenant) + [CtFieldReadImpl]SCIM2_USERS_ENDPOINT;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getAuthzHeader() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"Basic " + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Base64.encodeBase64String([CtInvocationImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]adminUsername + [CtLiteralImpl]":") + [CtFieldReadImpl]adminPassword).getBytes()).trim();
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.cloud.healthcare.fdamystudies.controller;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertNotNull;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtUnresolvedImport]import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.TestConstants;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.model.UserRegAdminEntity;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.notNullValue;
[CtUnresolvedImport]import com.github.tomakehurst.wiremock.client.WireMock;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.MessageCode;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtUnresolvedImport]import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.ApiEndpoint;
[CtUnresolvedImport]import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.UserAccountStatus;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.ErrorCode;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.repository.UserRegAdminRepository;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
[CtUnresolvedImport]import org.springframework.http.HttpHeaders;
[CtUnresolvedImport]import static com.google.cloud.healthcare.fdamystudies.common.JsonUtils.asJsonString;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.verify;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.junit.jupiter.api.AfterEach;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.service.UserProfileService;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.common.BaseMockIT;
[CtUnresolvedImport]import com.google.cloud.healthcare.fdamystudies.helper.TestDataHelper;
[CtClassImpl]public class UserProfileControllerTest extends [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.common.BaseMockIT {
    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.controller.UserProfileController controller;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.service.UserProfileService userProfileService;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.helper.TestDataHelper testDataHelper;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.model.UserRegAdminEntity userRegAdminEntity;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.repository.UserRegAdminRepository userRegAdminRepository;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String EMAIL_VALUE = [CtLiteralImpl]"mockit_email@grr.la";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String VALID_BEARER_TOKEN = [CtLiteralImpl]"Bearer 7fd50c2c-d618-493c-89d6-f1887e3e4bb8";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void contextLoads() [CtBlockImpl]{
        [CtInvocationImpl]assertNotNull([CtFieldReadImpl]controller);
        [CtInvocationImpl]assertNotNull([CtFieldReadImpl]mockMvc);
        [CtInvocationImpl]assertNotNull([CtFieldReadImpl]userProfileService);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]userRegAdminEntity = [CtInvocationImpl][CtFieldReadImpl]testDataHelper.createUserRegAdmin();
        [CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldSetUpNewAccount() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Step 1: Setting up the request for set up account
        [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest request = [CtInvocationImpl]setUpAccountRequest();
        [CtInvocationImpl][CtFieldReadImpl]userRegAdminEntity.setEmail([CtTypeAccessImpl]TestConstants.USER_EMAIL_VALUE);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]testDataHelper.getUserRegAdminRepository().saveAndFlush([CtFieldReadImpl]userRegAdminEntity);
        [CtLocalVariableImpl][CtCommentImpl]// Step 2: Call the API and expect SET_UP_ACCOUNT_SUCCESS message
        [CtTypeReferenceImpl]org.springframework.http.HttpHeaders headers = [CtInvocationImpl][CtFieldReadImpl]testDataHelper.newCommonHeaders();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mockMvc.perform([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]post([CtInvocationImpl][CtTypeAccessImpl]ApiEndpoint.SET_UP_ACCOUNT.getPath()).content([CtInvocationImpl]JsonUtils.asJsonString([CtVariableReadImpl]request)).headers([CtVariableReadImpl]headers).contextPath([CtInvocationImpl]getContextPath())).andDo([CtInvocationImpl]print()).andExpect([CtInvocationImpl][CtInvocationImpl]status().isCreated()).andExpect([CtInvocationImpl][CtInvocationImpl]jsonPath([CtLiteralImpl]"$.message").value([CtInvocationImpl][CtTypeAccessImpl]MessageCode.SET_UP_ACCOUNT_SUCCESS.getMessage())).andExpect([CtInvocationImpl]jsonPath([CtLiteralImpl]"$.userId", [CtInvocationImpl]CoreMatchers.notNullValue())).andReturn();
        [CtLocalVariableImpl][CtCommentImpl]// Step 3: verify saved values
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.model.UserRegAdminEntity> optUser = [CtInvocationImpl][CtFieldReadImpl]userRegAdminRepository.findByEmail([CtInvocationImpl][CtVariableReadImpl]request.getEmail());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.model.UserRegAdminEntity user = [CtInvocationImpl][CtVariableReadImpl]optUser.get();
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]request.getFirstName(), [CtInvocationImpl][CtVariableReadImpl]user.getFirstName());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]request.getLastName(), [CtInvocationImpl][CtVariableReadImpl]user.getLastName());
        [CtInvocationImpl]verify([CtLiteralImpl]1, [CtInvocationImpl]postRequestedFor([CtInvocationImpl]urlEqualTo([CtLiteralImpl]"/oauth-scim-service/users")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldReturnUserNotInvitedError() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Step 1: Setting up the request
        [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest request = [CtInvocationImpl]setUpAccountRequest();
        [CtLocalVariableImpl][CtCommentImpl]// Step 2: Call the API and expect USER_NOT_INVITED error
        [CtTypeReferenceImpl]org.springframework.http.HttpHeaders headers = [CtInvocationImpl][CtFieldReadImpl]testDataHelper.newCommonHeaders();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mockMvc.perform([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]post([CtInvocationImpl][CtTypeAccessImpl]ApiEndpoint.SET_UP_ACCOUNT.getPath()).content([CtInvocationImpl]JsonUtils.asJsonString([CtVariableReadImpl]request)).headers([CtVariableReadImpl]headers).contextPath([CtInvocationImpl]getContextPath())).andDo([CtInvocationImpl]print()).andExpect([CtInvocationImpl][CtInvocationImpl]status().isBadRequest()).andExpect([CtInvocationImpl]jsonPath([CtLiteralImpl]"$.error_description", [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl][CtTypeAccessImpl]ErrorCode.USER_NOT_INVITED.getDescription())));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldReturnAuthServerApplicationError() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Step 1: Setting up the request for AuthServerApplicationError
        [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest request = [CtInvocationImpl]setUpAccountRequest();
        [CtInvocationImpl][CtFieldReadImpl]userRegAdminEntity.setEmail([CtTypeAccessImpl]TestConstants.USER_EMAIL_VALUE);
        [CtInvocationImpl][CtVariableReadImpl]request.setPassword([CtLiteralImpl]"AuthServerError@b0ston");
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]testDataHelper.getUserRegAdminRepository().saveAndFlush([CtFieldReadImpl]userRegAdminEntity);
        [CtLocalVariableImpl][CtCommentImpl]// Step 2: Call the API and expect APPLICATION_ERROR error
        [CtTypeReferenceImpl]org.springframework.http.HttpHeaders headers = [CtInvocationImpl][CtFieldReadImpl]testDataHelper.newCommonHeaders();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mockMvc.perform([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]post([CtInvocationImpl][CtTypeAccessImpl]ApiEndpoint.SET_UP_ACCOUNT.getPath()).content([CtInvocationImpl]JsonUtils.asJsonString([CtVariableReadImpl]request)).headers([CtVariableReadImpl]headers).contextPath([CtInvocationImpl]getContextPath())).andDo([CtInvocationImpl]print()).andExpect([CtInvocationImpl][CtInvocationImpl]status().isInternalServerError()).andExpect([CtInvocationImpl]jsonPath([CtLiteralImpl]"$.error_description", [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl][CtTypeAccessImpl]ErrorCode.APPLICATION_ERROR.getDescription())));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void shouldReturnAuthServerBadRequestError() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Step 1: Setting up the request for bad request
        [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest request = [CtInvocationImpl]setUpAccountRequest();
        [CtInvocationImpl][CtFieldReadImpl]userRegAdminEntity.setEmail([CtTypeAccessImpl]TestConstants.USER_EMAIL_VALUE);
        [CtInvocationImpl][CtVariableReadImpl]request.setPassword([CtLiteralImpl]"AuthServerBadRequest@b0ston");
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]testDataHelper.getUserRegAdminRepository().saveAndFlush([CtFieldReadImpl]userRegAdminEntity);
        [CtLocalVariableImpl][CtCommentImpl]// Step 2: Call the API and expect BAD_REQUEST error
        [CtTypeReferenceImpl]org.springframework.http.HttpHeaders headers = [CtInvocationImpl][CtFieldReadImpl]testDataHelper.newCommonHeaders();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mockMvc.perform([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]post([CtInvocationImpl][CtTypeAccessImpl]ApiEndpoint.SET_UP_ACCOUNT.getPath()).content([CtInvocationImpl]JsonUtils.asJsonString([CtVariableReadImpl]request)).headers([CtVariableReadImpl]headers).contextPath([CtInvocationImpl]getContextPath())).andDo([CtInvocationImpl]print()).andExpect([CtInvocationImpl][CtInvocationImpl]status().isBadRequest());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.AfterEach
    public [CtTypeReferenceImpl]void cleanUp() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]testDataHelper.getUserRegAdminRepository().deleteAll();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest setUpAccountRequest() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.healthcare.fdamystudies.beans.SetUpAccountRequest();
        [CtInvocationImpl][CtVariableReadImpl]request.setEmail([CtTypeAccessImpl]TestConstants.USER_EMAIL_VALUE);
        [CtInvocationImpl][CtVariableReadImpl]request.setFirstName([CtTypeAccessImpl]TestDataHelper.ADMIN_FIRST_NAME);
        [CtInvocationImpl][CtVariableReadImpl]request.setLastName([CtTypeAccessImpl]TestDataHelper.ADMIN_LAST_NAME);
        [CtInvocationImpl][CtVariableReadImpl]request.setPassword([CtLiteralImpl]"Kantharaj#1123");
        [CtInvocationImpl][CtVariableReadImpl]request.setAppId([CtLiteralImpl]"PARTICIPANT MANAGER");
        [CtInvocationImpl][CtVariableReadImpl]request.setStatus([CtInvocationImpl][CtTypeAccessImpl]UserAccountStatus.ACTIVE.getStatus());
        [CtReturnImpl]return [CtVariableReadImpl]request;
    }
}
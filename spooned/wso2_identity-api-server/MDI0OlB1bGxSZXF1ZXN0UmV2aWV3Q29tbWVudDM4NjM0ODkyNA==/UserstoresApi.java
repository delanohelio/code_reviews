[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.wso2.carbon.identity.api.server.userstore.v1;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.MetaUserStoreType;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreListResponse;
[CtUnresolvedImport]import org.apache.cxf.jaxrs.ext.multipart.Attachment;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreReq;
[CtUnresolvedImport]import javax.ws.rs.*;
[CtUnresolvedImport]import javax.validation.Valid;
[CtUnresolvedImport]import javax.validation.constraints.*;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.ConnectionEstablishedResponse;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.UserstoresApiService;
[CtUnresolvedImport]import org.apache.cxf.jaxrs.ext.multipart.Multipart;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.Error;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.PatchDocument;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.RDBMSConnectionReq;
[CtUnresolvedImport]import io.swagger.annotations.*;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreConfigurationsRes;
[CtUnresolvedImport]import org.wso2.carbon.identity.api.server.userstore.v1.model.AvailableUserStoreClassesRes;
[CtClassImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/userstores")
[CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Api(description = [CtLiteralImpl]"The userstores API")
public class UserstoresApi {
    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.wso2.carbon.identity.api.server.userstore.v1.UserstoresApiService delegate;

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.POST
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Consumes([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Add a secondary user store.", notes = [CtLiteralImpl]"This API provides the capability to add a secondary user store.   <b>Permission required:</b>   *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]201, message = [CtLiteralImpl]"Successful response", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]400, message = [CtLiteralImpl]"Invalid input request.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]403, message = [CtLiteralImpl]"Resource Forbidden.", response = [CtFieldReadImpl]java.lang.Void.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]409, message = [CtLiteralImpl]"Element Already Exists.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response addUserStore([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Secondary user store to add.")
    [CtAnnotationImpl]@javax.validation.Valid
    [CtTypeReferenceImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreReq userStoreReq) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.addUserStore([CtVariableReadImpl]userStoreReq);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.DELETE
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/{userstore-domain-id}")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Delete a secondary user store.", notes = [CtLiteralImpl]"This API provides the capability to delete a secondary user store matching to the given user store domain id.   <b>Permission required:</b>  *_/permission/admin ", response = [CtFieldReadImpl]java.lang.Void.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]204, message = [CtLiteralImpl]"No Content.", response = [CtFieldReadImpl]java.lang.Void.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]403, message = [CtLiteralImpl]"Resource Forbidden.", response = [CtFieldReadImpl]java.lang.Void.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteUserStore([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"The unique name of the user store domain", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PathParam([CtLiteralImpl]"userstore-domain-id")
    [CtTypeReferenceImpl]java.lang.String userstoreDomainId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.deleteUserStore([CtVariableReadImpl]userstoreDomainId);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.GET
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/meta/types")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Retrieve the available user store classes/types.", notes = [CtLiteralImpl]"This API provides the capability to retrieve the available user store types.   <b>Permission required:</b>  *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.AvailableUserStoreClassesRes.class, responseContainer = [CtLiteralImpl]"List", authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"Meta" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful Response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.AvailableUserStoreClassesRes.class, responseContainer = [CtLiteralImpl]"List"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getAvailableUserStoreTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.getAvailableUserStoreTypes();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.GET
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Retrieve or List the configured secondary user stores.", notes = [CtLiteralImpl]"This API provides the capability to list the configured secondary userstores. <b>Permission required:</b> *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreListResponse.class, responseContainer = [CtLiteralImpl]"List", authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreListResponse.class, responseContainer = [CtLiteralImpl]"List"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]404, message = [CtLiteralImpl]"The specified resource is not found.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]501, message = [CtLiteralImpl]"Not Implemented.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getSecondaryUserStores([CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"maximum number of records to return")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"limit")
    [CtTypeReferenceImpl]java.lang.Integer limit, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"number of records to skip for pagination")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"offset")
    [CtTypeReferenceImpl]java.lang.Integer offset, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Condition to filter the retrival of records.")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"filter")
    [CtTypeReferenceImpl]java.lang.String filter, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Define the order how the retrieved records should be sorted.")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"sort")
    [CtTypeReferenceImpl]java.lang.String sort, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Define set of user store attributes (as comma separated) to be returned.")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"attributes")
    [CtTypeReferenceImpl]java.lang.String attributes) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.getSecondaryUserStores([CtVariableReadImpl]limit, [CtVariableReadImpl]offset, [CtVariableReadImpl]filter, [CtVariableReadImpl]sort, [CtVariableReadImpl]attributes);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.GET
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/{userstore-domain-id}")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Retrieve the configurations of secondary user store based on its domain id.", notes = [CtLiteralImpl]"This API provides the capability to retrieve the configurations of secondary user store based on its domain id.    <b>Permission required:</b> *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreConfigurationsRes.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreConfigurationsRes.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]404, message = [CtLiteralImpl]"The specified resource is not found.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getUserStoreByDomainId([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"The unique name of the user store domain", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PathParam([CtLiteralImpl]"userstore-domain-id")
    [CtTypeReferenceImpl]java.lang.String userstoreDomainId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.getUserStoreByDomainId([CtVariableReadImpl]userstoreDomainId);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.GET
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/meta/types/{type-id}")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Retrieve the properties of secondary user store of a given user store type.", notes = [CtLiteralImpl]"This API provides the capability to retrieve the properties of secondary user store of a given class name.   <b>Permission required:</b>  *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.MetaUserStoreType.class, responseContainer = [CtLiteralImpl]"List", authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"Meta" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.MetaUserStoreType.class, responseContainer = [CtLiteralImpl]"List"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]400, message = [CtLiteralImpl]"Invalid input request.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]404, message = [CtLiteralImpl]"The specified resource is not found.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]501, message = [CtLiteralImpl]"Not Implemented.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getUserStoreManagerProperties([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"Id of the user store type", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PathParam([CtLiteralImpl]"type-id")
    [CtTypeReferenceImpl]java.lang.String typeId, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"maximum number of records to return")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"limit")
    [CtTypeReferenceImpl]java.lang.Integer limit, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"number of records to skip for pagination")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"offset")
    [CtTypeReferenceImpl]java.lang.Integer offset, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Condition to filter the retrival of records.")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"filter")
    [CtTypeReferenceImpl]java.lang.String filter, [CtParameterImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"Define the order how the retrieved records should be sorted.")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.QueryParam([CtLiteralImpl]"sort")
    [CtTypeReferenceImpl]java.lang.String sort) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.getUserStoreManagerProperties([CtVariableReadImpl]typeId, [CtVariableReadImpl]limit, [CtVariableReadImpl]offset, [CtVariableReadImpl]filter, [CtVariableReadImpl]sort);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PATCH
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/{userstore-domain-id}")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Consumes([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Patch the secondary user store by it's domain id.", notes = [CtLiteralImpl]"This API provides the capability to update the secondary user store's property using patch request by using it's domain id.   <b>Permission required:</b>  *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]400, message = [CtLiteralImpl]"Invalid input request.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]403, message = [CtLiteralImpl]"Resource Forbidden.", response = [CtFieldReadImpl]java.lang.Void.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]404, message = [CtLiteralImpl]"The specified resource is not found.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response patchUserStore([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"The unique name of the user store domain", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PathParam([CtLiteralImpl]"userstore-domain-id")
    [CtTypeReferenceImpl]java.lang.String userstoreDomainId, [CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@javax.validation.Valid
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.PatchDocument> patchDocument) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.patchUserStore([CtVariableReadImpl]userstoreDomainId, [CtVariableReadImpl]patchDocument);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.POST
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/test-connection")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Consumes([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Test the connection to the datasource used by a JDBC user store manager.", notes = [CtLiteralImpl]"This API provides the capability to test the connection to the datasource used by a JDBC user store manager.    <b>Permission required:</b>   *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.ConnectionEstablishedResponse.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.ConnectionEstablishedResponse.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]400, message = [CtLiteralImpl]"Invalid input request.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response testRDBMSConnection([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"RDBMS connection properties used to connect to the datasource used by a JDBC user store manager.")
    [CtAnnotationImpl]@javax.validation.Valid
    [CtTypeReferenceImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.RDBMSConnectionReq rdBMSConnectionReq) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.testRDBMSConnection([CtVariableReadImpl]rdBMSConnectionReq);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.Valid
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PUT
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Path([CtLiteralImpl]"/{userstore-domain-id}")
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Consumes([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/json" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiOperation(value = [CtLiteralImpl]"Update a user store by its domain id.", notes = [CtLiteralImpl]"This API provides the capability to edit a user store based on its domain id.   <b>Permission required:</b>   *_/permission/admin ", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class, authorizations = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization([CtLiteralImpl]"BasicAuth"), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.Authorization(value = [CtLiteralImpl]"OAuth2", scopes = [CtNewArrayImpl]{  }) }, tags = [CtNewArrayImpl]{ [CtLiteralImpl]"User Store" })
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponses([CtNewArrayImpl]{ [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]200, message = [CtLiteralImpl]"Successful response.", response = [CtFieldReadImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreResponse.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]400, message = [CtLiteralImpl]"Invalid input request.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]401, message = [CtLiteralImpl]"Unauthorized.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]403, message = [CtLiteralImpl]"Resource Forbidden.", response = [CtFieldReadImpl]java.lang.Void.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]404, message = [CtLiteralImpl]"The specified resource is not found.", response = [CtFieldReadImpl]java.lang.Error.class), [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiResponse(code = [CtLiteralImpl]500, message = [CtLiteralImpl]"Internal Server Error.", response = [CtFieldReadImpl]java.lang.Error.class) })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateUserStore([CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam(value = [CtLiteralImpl]"Current domain id of the user store", required = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.PathParam([CtLiteralImpl]"userstore-domain-id")
    [CtTypeReferenceImpl]java.lang.String userstoreDomainId, [CtParameterImpl][CtAnnotationImpl]@org.wso2.carbon.identity.api.server.userstore.v1.ApiParam([CtLiteralImpl]"The secondary user store values which are need to be edited of the given domain id.")
    [CtAnnotationImpl]@javax.validation.Valid
    [CtTypeReferenceImpl]org.wso2.carbon.identity.api.server.userstore.v1.model.UserStoreReq userStoreReq) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]delegate.updateUserStore([CtVariableReadImpl]userstoreDomainId, [CtVariableReadImpl]userStoreReq);
    }
}
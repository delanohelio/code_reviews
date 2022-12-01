[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

 WSO2 Inc. licenses this file to you under the Apache License,
 Version 2.0 (the "License"); you may not use this file except
 in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.wso2.carbon.apimgt.rest.api.gateway.v1.impl;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.gateway.InMemoryAPIDeployer;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.api.gateway.GatewayAPIDTO;
[CtImportImpl]import org.wso2.carbon.apimgt.rest.api.gateway.v1.*;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;
[CtImportImpl]import org.json.JSONArray;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.impl.APIConstants;
[CtUnresolvedImport]import org.apache.cxf.jaxrs.ext.MessageContext;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import org.apache.commons.logging.Log;
[CtUnresolvedImport]import org.apache.commons.logging.LogFactory;
[CtImportImpl]import org.json.JSONObject;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.api.gateway.GatewayContentDTO;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.wso2.carbon.apimgt.gateway.utils.EndpointAdminServiceProxy;
[CtUnresolvedImport]import org.wso2.carbon.endpoint.EndpointAdminException;
[CtClassImpl]public class EndPointsApiServiceImpl implements [CtTypeReferenceImpl]EndPointsApiService {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.commons.logging.Log log = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.logging.LogFactory.getLog([CtFieldReadImpl]org.wso2.carbon.apimgt.rest.api.gateway.v1.impl.EndPointsApiServiceImpl.class);

    [CtMethodImpl]public [CtTypeReferenceImpl]javax.ws.rs.core.Response endPointsGet([CtParameterImpl][CtTypeReferenceImpl]java.lang.String apiName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain, [CtParameterImpl][CtTypeReferenceImpl]org.apache.cxf.jaxrs.ext.MessageContext messageContext) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.apimgt.gateway.InMemoryAPIDeployer inMemoryApiDeployer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.apimgt.gateway.InMemoryAPIDeployer();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tenantDomain == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tenantDomain = [CtFieldReadImpl]org.wso2.carbon.apimgt.impl.APIConstants.SUPER_TENANT_DOMAIN;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> apiAttributes = [CtInvocationImpl][CtVariableReadImpl]inMemoryApiDeployer.getGatewayAPIAttributes([CtVariableReadImpl]apiName, [CtVariableReadImpl]version, [CtVariableReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String apiId = [CtInvocationImpl][CtVariableReadImpl]apiAttributes.get([CtTypeAccessImpl]APIConstants.GatewayArtifactSynchronizer.API_ID);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String label = [CtInvocationImpl][CtVariableReadImpl]apiAttributes.get([CtTypeAccessImpl]APIConstants.GatewayArtifactSynchronizer.LABEL);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.apimgt.api.gateway.GatewayAPIDTO gatewayAPIDTO = [CtInvocationImpl][CtVariableReadImpl]inMemoryApiDeployer.getAPIArtifact([CtVariableReadImpl]apiId, [CtVariableReadImpl]label);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject responseObj = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]gatewayAPIDTO != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray endPointArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray unDeployedEndPointArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]gatewayAPIDTO.getEndpointEntriesToBeAdd() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.apimgt.gateway.utils.EndpointAdminServiceProxy endpointAdminServiceProxy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.apimgt.gateway.utils.EndpointAdminServiceProxy([CtInvocationImpl][CtVariableReadImpl]gatewayAPIDTO.getTenantDomain());
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.apimgt.api.gateway.GatewayContentDTO gatewayEndpoint : [CtInvocationImpl][CtVariableReadImpl]gatewayAPIDTO.getEndpointEntriesToBeAdd()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]endpointAdminServiceProxy.isEndpointExist([CtInvocationImpl][CtVariableReadImpl]gatewayEndpoint.getName())) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]endPointArray.put([CtInvocationImpl][CtVariableReadImpl]endpointAdminServiceProxy.getEndpoints([CtInvocationImpl][CtVariableReadImpl]gatewayEndpoint.getName()));
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.apimgt.rest.api.gateway.v1.impl.EndPointsApiServiceImpl.log.error([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]gatewayEndpoint.getName() + [CtLiteralImpl]" was not deployed in the gateway");
                            [CtInvocationImpl][CtVariableReadImpl]unDeployedEndPointArray.put([CtInvocationImpl][CtVariableReadImpl]gatewayEndpoint.getContent());
                        }
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]responseObj.put([CtLiteralImpl]"Deployed Endpoints", [CtVariableReadImpl]endPointArray);
                [CtInvocationImpl][CtVariableReadImpl]responseObj.put([CtLiteralImpl]"UnDeployed Endpoints", [CtVariableReadImpl]unDeployedEndPointArray);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.endpoint.EndpointAdminException e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtLiteralImpl]"Error in fetching deployed Endpoints from Synapse Configuration";
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.apimgt.rest.api.gateway.v1.impl.EndPointsApiServiceImpl.log.error([CtVariableReadImpl]errorMessage, [CtVariableReadImpl]e);
                [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil.handleInternalServerError([CtVariableReadImpl]errorMessage, [CtVariableReadImpl]e, [CtFieldReadImpl]org.wso2.carbon.apimgt.rest.api.gateway.v1.impl.EndPointsApiServiceImpl.log);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseStringObj = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]responseObj);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok().entity([CtVariableReadImpl]responseStringObj).build();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]responseObj.put([CtLiteralImpl]"Message", [CtLiteralImpl]"Error");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseStringObj = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]responseObj);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.serverError().entity([CtVariableReadImpl]responseStringObj).build();
        }
    }
}
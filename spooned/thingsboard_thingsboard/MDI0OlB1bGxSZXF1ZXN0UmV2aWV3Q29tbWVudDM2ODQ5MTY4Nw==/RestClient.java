[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright © 2016-2020 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package org.thingsboard.client.tools;
[CtUnresolvedImport]import org.thingsboard.server.common.data.Event;
[CtUnresolvedImport]import org.thingsboard.server.common.data.audit.AuditLog;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.thingsboard.server.common.data.page.TimePageLink;
[CtImportImpl]import java.io.Closeable;
[CtUnresolvedImport]import org.springframework.http.HttpRequest;
[CtUnresolvedImport]import org.thingsboard.server.common.data.Customer;
[CtUnresolvedImport]import org.thingsboard.server.common.data.rule.RuleChainMetaData;
[CtUnresolvedImport]import org.thingsboard.server.common.data.security.model.SecuritySettings;
[CtUnresolvedImport]import org.thingsboard.server.common.data.page.TextPageData;
[CtImportImpl]import java.util.concurrent.Executors;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonNode;
[CtUnresolvedImport]import org.thingsboard.server.common.data.widget.WidgetsBundle;
[CtUnresolvedImport]import org.thingsboard.server.common.data.relation.EntityRelationInfo;
[CtUnresolvedImport]import org.thingsboard.server.common.data.relation.EntityRelation;
[CtUnresolvedImport]import org.springframework.http.client.support.HttpRequestWrapper;
[CtUnresolvedImport]import org.thingsboard.server.common.data.kv.AttributeKvEntry;
[CtUnresolvedImport]import org.thingsboard.server.common.data.AdminSettings;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.springframework.web.client.RestTemplate;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.thingsboard.server.common.data.Dashboard;
[CtUnresolvedImport]import org.thingsboard.server.common.data.ClaimRequest;
[CtUnresolvedImport]import org.thingsboard.server.common.data.id.DeviceId;
[CtUnresolvedImport]import org.thingsboard.server.common.data.DashboardInfo;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.thingsboard.client.tools.utils.JsonConverter;
[CtUnresolvedImport]import org.thingsboard.server.common.data.alarm.Alarm;
[CtUnresolvedImport]import org.thingsboard.server.common.data.EntityView;
[CtUnresolvedImport]import org.thingsboard.server.common.data.kv.TsKvEntry;
[CtUnresolvedImport]import org.springframework.http.HttpEntity;
[CtUnresolvedImport]import org.springframework.http.client.ClientHttpRequestInterceptor;
[CtUnresolvedImport]import org.thingsboard.server.common.data.page.TimePageData;
[CtUnresolvedImport]import org.thingsboard.server.common.data.UpdateMessage;
[CtUnresolvedImport]import org.thingsboard.server.common.data.entityview.EntityViewSearchQuery;
[CtUnresolvedImport]import org.springframework.util.CollectionUtils;
[CtUnresolvedImport]import static org.springframework.util.StringUtils.isEmpty;
[CtUnresolvedImport]import org.thingsboard.server.common.data.EntitySubtype;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.thingsboard.server.common.data.device.DeviceSearchQuery;
[CtUnresolvedImport]import org.thingsboard.server.common.data.id.CustomerId;
[CtUnresolvedImport]import org.thingsboard.server.common.data.alarm.AlarmSeverity;
[CtUnresolvedImport]import org.thingsboard.server.common.data.widget.WidgetType;
[CtUnresolvedImport]import org.springframework.core.ParameterizedTypeReference;
[CtUnresolvedImport]import org.thingsboard.server.common.data.Device;
[CtUnresolvedImport]import org.thingsboard.server.common.data.id.AssetId;
[CtUnresolvedImport]import org.thingsboard.server.common.data.Tenant;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import org.thingsboard.server.common.data.security.model.UserPasswordPolicy;
[CtUnresolvedImport]import org.thingsboard.server.common.data.User;
[CtUnresolvedImport]import org.thingsboard.server.common.data.id.EntityId;
[CtUnresolvedImport]import org.springframework.http.client.ClientHttpResponse;
[CtUnresolvedImport]import org.springframework.http.client.ClientHttpRequestExecution;
[CtUnresolvedImport]import org.thingsboard.server.common.data.id.DashboardId;
[CtUnresolvedImport]import org.thingsboard.server.common.data.alarm.AlarmInfo;
[CtUnresolvedImport]import org.thingsboard.server.common.data.security.DeviceCredentialsType;
[CtImportImpl]import com.fasterxml.jackson.databind.node.ObjectNode;
[CtUnresolvedImport]import org.springframework.http.HttpMethod;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.thingsboard.server.common.data.security.DeviceCredentials;
[CtUnresolvedImport]import org.thingsboard.server.common.data.asset.AssetSearchQuery;
[CtUnresolvedImport]import org.springframework.http.HttpStatus;
[CtUnresolvedImport]import org.springframework.web.client.HttpClientErrorException;
[CtUnresolvedImport]import org.thingsboard.server.common.data.rule.RuleChain;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtUnresolvedImport]import org.springframework.http.ResponseEntity;
[CtUnresolvedImport]import org.thingsboard.server.common.data.plugin.ComponentDescriptor;
[CtUnresolvedImport]import org.thingsboard.server.common.data.page.TextPageLink;
[CtUnresolvedImport]import org.thingsboard.server.common.data.relation.EntityRelationsQuery;
[CtImportImpl]import java.util.concurrent.Future;
[CtUnresolvedImport]import org.springframework.util.StringUtils;
[CtUnresolvedImport]import org.thingsboard.server.common.data.asset.Asset;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Andrew Shvayka
 */
public class RestClient implements [CtTypeReferenceImpl]org.springframework.http.client.ClientHttpRequestInterceptor , [CtTypeReferenceImpl]java.io.Closeable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JWT_TOKEN_HEADER_PARAM = [CtLiteralImpl]"X-Authorization";

    [CtFieldImpl]protected final [CtTypeReferenceImpl]org.springframework.web.client.RestTemplate restTemplate;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]java.lang.String baseURL;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String token;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String refreshToken;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper objectMapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.ExecutorService service = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newFixedThreadPool([CtLiteralImpl]10);

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String ACTIVATE_TOKEN_REGEX = [CtLiteralImpl]"/api/noauth/activate?activateToken=";

    [CtConstructorImpl]public RestClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseURL) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.restTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.web.client.RestTemplate();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseURL = [CtVariableReadImpl]baseURL;
    }

    [CtConstructorImpl]public RestClient([CtParameterImpl][CtTypeReferenceImpl]org.springframework.web.client.RestTemplate restTemplate, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseURL) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.restTemplate = [CtVariableReadImpl]restTemplate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseURL = [CtVariableReadImpl]baseURL;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.springframework.http.client.ClientHttpResponse intercept([CtParameterImpl][CtTypeReferenceImpl]org.springframework.http.HttpRequest request, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] bytes, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.http.client.ClientHttpRequestExecution execution) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.HttpRequest wrapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.client.support.HttpRequestWrapper([CtVariableReadImpl]request);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]wrapper.getHeaders().set([CtFieldReadImpl]org.thingsboard.client.tools.RestClient.JWT_TOKEN_HEADER_PARAM, [CtBinaryOperatorImpl][CtLiteralImpl]"Bearer " + [CtFieldReadImpl]token);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.client.ClientHttpResponse response = [CtInvocationImpl][CtVariableReadImpl]execution.execute([CtVariableReadImpl]wrapper, [CtVariableReadImpl]bytes);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.UNAUTHORIZED) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.getInterceptors().remove([CtThisAccessImpl]this);
                [CtInvocationImpl]refreshToken();
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]wrapper.getHeaders().set([CtFieldReadImpl]org.thingsboard.client.tools.RestClient.JWT_TOKEN_HEADER_PARAM, [CtBinaryOperatorImpl][CtLiteralImpl]"Bearer " + [CtFieldReadImpl]token);
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]execution.execute([CtVariableReadImpl]wrapper, [CtVariableReadImpl]bytes);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getToken() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]token;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getRefreshToken() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]refreshToken;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void refreshToken() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> refreshTokenRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]refreshTokenRequest.put([CtLiteralImpl]"refreshToken", [CtFieldReadImpl]refreshToken);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> tokenInfo = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/auth/token", [CtVariableReadImpl]refreshTokenRequest, [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class);
        [CtInvocationImpl]setTokenInfo([CtInvocationImpl][CtVariableReadImpl]tokenInfo.getBody());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void login([CtParameterImpl][CtTypeReferenceImpl]java.lang.String username, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String password) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> loginRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]loginRequest.put([CtLiteralImpl]"username", [CtVariableReadImpl]username);
        [CtInvocationImpl][CtVariableReadImpl]loginRequest.put([CtLiteralImpl]"password", [CtVariableReadImpl]password);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> tokenInfo = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/auth/login", [CtVariableReadImpl]loginRequest, [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class);
        [CtInvocationImpl]setTokenInfo([CtInvocationImpl][CtVariableReadImpl]tokenInfo.getBody());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setTokenInfo([CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode tokenInfo) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.token = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenInfo.get([CtLiteralImpl]"token").asText();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.refreshToken = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tokenInfo.get([CtLiteralImpl]"refreshToken").asText();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.getInterceptors().add([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> findDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"deviceName", [CtVariableReadImpl]name);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> deviceEntity = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/devices?deviceName={deviceName}", [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]deviceEntity.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> findCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerTitle", [CtVariableReadImpl]title);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> customerEntity = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/customers?customerTitle={customerTitle}", [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]customerEntity.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> findAsset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"assetName", [CtVariableReadImpl]name);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> assetEntity = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/assets?assetName={assetName}", [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]assetEntity.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> getAttributes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientKeys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String sharedKeys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"accessToken", [CtVariableReadImpl]accessToken);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"clientKeys", [CtVariableReadImpl]clientKeys);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"sharedKeys", [CtVariableReadImpl]sharedKeys);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> telemetryEntity = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/v1/{accessToken}/attributes?clientKeys={clientKeys}&sharedKeys={sharedKeys}", [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]telemetryEntity.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer createCustomer([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer customer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer", [CtVariableReadImpl]customer, [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer createCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer customer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer();
        [CtInvocationImpl][CtVariableReadImpl]customer.setTitle([CtVariableReadImpl]title);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer", [CtVariableReadImpl]customer, [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials updateDeviceCredentials([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.DeviceId deviceId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials deviceCredentials = [CtInvocationImpl]getCredentials([CtVariableReadImpl]deviceId);
        [CtInvocationImpl][CtVariableReadImpl]deviceCredentials.setCredentialsType([CtTypeAccessImpl]DeviceCredentialsType.ACCESS_TOKEN);
        [CtInvocationImpl][CtVariableReadImpl]deviceCredentials.setCredentialsId([CtVariableReadImpl]token);
        [CtReturnImpl]return [CtInvocationImpl]saveDeviceCredentials([CtVariableReadImpl]deviceCredentials);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device createDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Device device = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device();
        [CtInvocationImpl][CtVariableReadImpl]device.setName([CtVariableReadImpl]name);
        [CtInvocationImpl][CtVariableReadImpl]device.setType([CtVariableReadImpl]type);
        [CtReturnImpl]return [CtInvocationImpl]doCreateDevice([CtVariableReadImpl]device, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device createDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Device device) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]doCreateDevice([CtVariableReadImpl]device, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device createDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Device device, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]doCreateDevice([CtVariableReadImpl]device, [CtVariableReadImpl]accessToken);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device doCreateDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Device device, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessToken) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deviceCreationUrl = [CtLiteralImpl]"/api/device";
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]accessToken)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]deviceCreationUrl = [CtBinaryOperatorImpl][CtVariableReadImpl]deviceCreationUrl + [CtLiteralImpl]"?accessToken={accessToken}";
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"accessToken", [CtVariableReadImpl]accessToken);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtVariableReadImpl]deviceCreationUrl, [CtVariableReadImpl]device, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset createAsset([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset asset) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset", [CtVariableReadImpl]asset, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset createAsset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset asset = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset();
        [CtInvocationImpl][CtVariableReadImpl]asset.setName([CtVariableReadImpl]name);
        [CtInvocationImpl][CtVariableReadImpl]asset.setType([CtVariableReadImpl]type);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset", [CtVariableReadImpl]asset, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm createAlarm([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm alarm) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm", [CtVariableReadImpl]alarm, [CtFieldReadImpl]org.thingsboard.server.common.data.alarm.Alarm.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteCustomer([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.CustomerId customerId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}", [CtVariableReadImpl]customerId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.DeviceId deviceId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/{deviceId}", [CtVariableReadImpl]deviceId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteAsset([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.AssetId assetId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset/{assetId}", [CtVariableReadImpl]assetId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device assignDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.CustomerId customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.DeviceId deviceId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/device/{deviceId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtInvocationImpl][CtVariableReadImpl]customerId.toString(), [CtInvocationImpl][CtVariableReadImpl]deviceId.toString()).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset assignAsset([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.CustomerId customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.AssetId assetId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/asset/{assetId}", [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtInvocationImpl][CtVariableReadImpl]customerId.toString(), [CtInvocationImpl][CtVariableReadImpl]assetId.toString()).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation makeRelation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationType, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId idFrom, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId idTo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation relation = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation();
        [CtInvocationImpl][CtVariableReadImpl]relation.setFrom([CtVariableReadImpl]idFrom);
        [CtInvocationImpl][CtVariableReadImpl]relation.setTo([CtVariableReadImpl]idTo);
        [CtInvocationImpl][CtVariableReadImpl]relation.setType([CtVariableReadImpl]relationType);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relation", [CtVariableReadImpl]relation, [CtFieldReadImpl]org.thingsboard.server.common.data.relation.EntityRelation.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard createDashboard([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard dashboard) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard", [CtVariableReadImpl]dashboard, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteDashboard([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.DashboardId dashboardId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}", [CtVariableReadImpl]dashboardId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> findTenantDashboards() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo>> dashboards = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/dashboards?limit=100000", [CtTypeAccessImpl]HttpMethod.GET, [CtLiteralImpl]null, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo>>()[CtClassImpl] {});
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dashboards.getBody().getData();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials getCredentials([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.DeviceId id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]id.getId().toString()) + [CtLiteralImpl]"/credentials", [CtFieldReadImpl]org.thingsboard.server.common.data.security.DeviceCredentials.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.springframework.web.client.RestTemplate getRestTemplate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]restTemplate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.AdminSettings> getAdminSettings([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.AdminSettings> adminSettings = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/settings/{key}", [CtFieldReadImpl]org.thingsboard.server.common.data.AdminSettings.class, [CtVariableReadImpl]key);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]adminSettings.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.AdminSettings saveAdminSettings([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.AdminSettings adminSettings) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/settings", [CtVariableReadImpl]adminSettings, [CtFieldReadImpl]org.thingsboard.server.common.data.AdminSettings.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sendTestMail([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.AdminSettings adminSettings) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/settings/testMail", [CtVariableReadImpl]adminSettings, [CtFieldReadImpl]org.thingsboard.server.common.data.AdminSettings.class);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.SecuritySettings> getSecuritySettings() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.SecuritySettings> securitySettings = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/securitySettings", [CtFieldReadImpl]org.thingsboard.server.common.data.security.model.SecuritySettings.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]securitySettings.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.SecuritySettings saveSecuritySettings([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.SecuritySettings securitySettings) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/securitySettings", [CtVariableReadImpl]securitySettings, [CtFieldReadImpl]org.thingsboard.server.common.data.security.model.SecuritySettings.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.UpdateMessage> checkUpdates() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.UpdateMessage> updateMsg = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/admin/updates", [CtFieldReadImpl]org.thingsboard.server.common.data.UpdateMessage.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]updateMsg.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm> getAlarmById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alarmId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm> alarm = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/{alarmId}", [CtFieldReadImpl]org.thingsboard.server.common.data.alarm.Alarm.class, [CtVariableReadImpl]alarmId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]alarm.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmInfo> getAlarmInfoById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alarmId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmInfo> alarmInfo = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/info/{alarmId}", [CtFieldReadImpl]org.thingsboard.server.common.data.alarm.AlarmInfo.class, [CtVariableReadImpl]alarmId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]alarmInfo.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm saveAlarm([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.Alarm alarm) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm", [CtVariableReadImpl]alarm, [CtFieldReadImpl]org.thingsboard.server.common.data.alarm.Alarm.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteAlarm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alarmId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/{alarmId}", [CtVariableReadImpl]alarmId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void ackAlarm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alarmId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/{alarmId}/ack", [CtLiteralImpl]null, [CtVariableReadImpl]alarmId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clearAlarm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alarmId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/{alarmId}/clear", [CtLiteralImpl]null, [CtVariableReadImpl]alarmId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmInfo> getAlarms([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String searchStatus, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String status, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean fetchOriginator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"searchStatus", [CtVariableReadImpl]searchStatus);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"status", [CtVariableReadImpl]status);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fetchOriginator", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]fetchOriginator));
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String urlParams = [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/{entityType}/{entityId}?searchStatus={searchStatus}&status={status}&fetchOriginator={fetchOriginator}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getUrlParams([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String urlParams = [CtLiteralImpl]"limit={limit}&ascOrder={ascOrder}";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getStartTime() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&startTime={startTime}";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getEndTime() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&endTime={endTime}";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&offset={offset}";
        }
        [CtReturnImpl]return [CtVariableReadImpl]urlParams;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getUrlParams([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String urlParams = [CtLiteralImpl]"limit={limit}";
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isEmpty([CtInvocationImpl][CtVariableReadImpl]pageLink.getTextSearch())) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&textSearch={textSearch}";
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isEmpty([CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset())) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&idOffset={idOffset}";
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isEmpty([CtInvocationImpl][CtVariableReadImpl]pageLink.getTextOffset())) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]urlParams += [CtLiteralImpl]"&textOffset={textOffset}";
        }
        [CtReturnImpl]return [CtVariableReadImpl]urlParams;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmSeverity> getHighestAlarmSeverity([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String searchStatus, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String status) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"searchStatus", [CtVariableReadImpl]searchStatus);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"status", [CtVariableReadImpl]status);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.alarm.AlarmSeverity> alarmSeverity = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/alarm/highestSeverity/{entityType}/{entityId}?searchStatus={searchStatus}&status={status}", [CtFieldReadImpl]org.thingsboard.server.common.data.alarm.AlarmSeverity.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]alarmSeverity.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> getAssetById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> asset = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset/{assetId}", [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]assetId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]asset.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset saveAsset([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset asset) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset", [CtVariableReadImpl]asset, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteAsset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset/{assetId}", [CtVariableReadImpl]assetId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> assignAssetToCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"assetId", [CtVariableReadImpl]assetId);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> asset = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/asset/{assetId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]asset.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> unassignAssetFromCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> asset = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/asset/{assetId}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]assetId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]asset.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> assignAssetToPublicCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> asset = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/public/asset/{assetId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]assetId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]asset.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> getTenantAssets([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>> assets = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/assets?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]assets.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> getTenantAsset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String assetName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> asset = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/assets?assetName={assetName}", [CtFieldReadImpl]org.thingsboard.server.common.data.asset.Asset.class, [CtVariableReadImpl]assetName);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]asset.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> getCustomerAssets([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>> assets = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/assets?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]assets.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> getAssetsByIds([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> assetIds) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/assets?assetIds={assetIds}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>>()[CtClassImpl] {}, [CtInvocationImpl]listToString([CtVariableReadImpl]assetIds)).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset> findByQuery([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.AssetSearchQuery query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtInvocationImpl][CtTypeAccessImpl]java.net.URI.create([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/assets"), [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]query), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.asset.Asset>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype> getAssetTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtInvocationImpl][CtTypeAccessImpl]java.net.URI.create([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/asset/types"), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog> getAuditLogsByCustomerId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"actionTypes", [CtVariableReadImpl]actionTypes);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>> auditLog = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/audit/logs/customer/{customerId}?actionTypes={actionTypes}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]auditLog.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog> getAuditLogsByUserId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"userId", [CtVariableReadImpl]userId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"actionTypes", [CtVariableReadImpl]actionTypes);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>> auditLog = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/audit/logs/user/{userId}?actionTypes={actionTypes}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]auditLog.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog> getAuditLogsByEntityId([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionTypes, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"actionTypes", [CtVariableReadImpl]actionTypes);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>> auditLog = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/audit/logs/entity/{entityType}/{entityId}?actionTypes={actionTypes}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]auditLog.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog> getAuditLogs([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"actionTypes", [CtVariableReadImpl]actionTypes);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>> auditLog = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/audit/logs?actionTypes={actionTypes}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.audit.AuditLog>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]auditLog.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getActivateToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String activationLink = [CtInvocationImpl]getActivationLink([CtVariableReadImpl]userId);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.delete([CtVariableReadImpl]activationLink, [CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtFieldReadImpl]org.thingsboard.client.tools.RestClient.ACTIVATE_TOKEN_REGEX);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> getUser() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> user = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/auth/user", [CtFieldReadImpl]org.thingsboard.server.common.data.User.class);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]user.getBody());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void logout() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/auth/logout", [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void changePassword([CtParameterImpl][CtTypeReferenceImpl]java.lang.String currentPassword, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String newPassword) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ObjectNode changePasswordRequest = [CtInvocationImpl][CtFieldReadImpl]objectMapper.createObjectNode();
        [CtInvocationImpl][CtVariableReadImpl]changePasswordRequest.put([CtLiteralImpl]"currentPassword", [CtVariableReadImpl]currentPassword);
        [CtInvocationImpl][CtVariableReadImpl]changePasswordRequest.put([CtLiteralImpl]"newPassword", [CtVariableReadImpl]newPassword);
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/auth/changePassword", [CtVariableReadImpl]changePasswordRequest);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.UserPasswordPolicy> getUserPasswordPolicy() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.model.UserPasswordPolicy> userPasswordPolicy = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/noauth/userPasswordPolicy", [CtFieldReadImpl]org.thingsboard.server.common.data.security.model.UserPasswordPolicy.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]userPasswordPolicy.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.lang.String> checkActivateToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String activateToken = [CtInvocationImpl]getActivateToken([CtVariableReadImpl]userId);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/noauth/activate?activateToken={activateToken}", [CtFieldReadImpl]java.lang.String.class, [CtVariableReadImpl]activateToken);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void requestResetPasswordByEmail([CtParameterImpl][CtTypeReferenceImpl]java.lang.String email) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ObjectNode resetPasswordByEmailRequest = [CtInvocationImpl][CtFieldReadImpl]objectMapper.createObjectNode();
        [CtInvocationImpl][CtVariableReadImpl]resetPasswordByEmailRequest.put([CtLiteralImpl]"email", [CtVariableReadImpl]email);
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/noauth/resetPasswordByEmail", [CtVariableReadImpl]resetPasswordByEmailRequest);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> activateUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String password) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ObjectNode activateRequest = [CtInvocationImpl][CtFieldReadImpl]objectMapper.createObjectNode();
        [CtInvocationImpl][CtVariableReadImpl]activateRequest.put([CtLiteralImpl]"activateToken", [CtInvocationImpl]getActivateToken([CtVariableReadImpl]userId));
        [CtInvocationImpl][CtVariableReadImpl]activateRequest.put([CtLiteralImpl]"password", [CtVariableReadImpl]password);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> jsonNode = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/noauth/activate", [CtVariableReadImpl]activateRequest, [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]jsonNode.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor> getComponentDescriptorByClazz([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentDescriptorClazz) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor> componentDescriptor = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/component/{componentDescriptorClazz}", [CtFieldReadImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor.class, [CtVariableReadImpl]componentDescriptorClazz);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]componentDescriptor.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor> getComponentDescriptorsByType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/components?componentType={componentType}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor>>()[CtClassImpl] {}, [CtVariableReadImpl]componentType).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor> getComponentDescriptorsByTypes([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> componentTypes) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/components?componentTypes={componentTypes}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.plugin.ComponentDescriptor>>()[CtClassImpl] {}, [CtInvocationImpl]listToString([CtVariableReadImpl]componentTypes)).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> getCustomerById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> customer = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}", [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class, [CtVariableReadImpl]customerId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]customer.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> getShortCustomerInfoById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> customerInfo = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/shortInfo", [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class, [CtVariableReadImpl]customerId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]customerInfo.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCustomerTitleById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForObject([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/title", [CtFieldReadImpl]java.lang.String.class, [CtVariableReadImpl]customerId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer saveCustomer([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer customer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer", [CtVariableReadImpl]customer, [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}", [CtVariableReadImpl]customerId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> getCustomers([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer>> customer = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customers?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer>>()[CtClassImpl] {}, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]customer.getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> getTenantCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerTitle) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Customer> customer = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/customers?customerTitle={customerTitle}", [CtFieldReadImpl]org.thingsboard.server.common.data.Customer.class, [CtVariableReadImpl]customerTitle);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]customer.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Long getServerTime() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForObject([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/serverTime", [CtFieldReadImpl]java.lang.Long.class);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Long getMaxDatapointsLimit() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForObject([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/maxDatapointsLimit", [CtFieldReadImpl]java.lang.Long.class);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> getDashboardInfoById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> dashboardInfo = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/info/{dashboardId}", [CtFieldReadImpl]org.thingsboard.server.common.data.DashboardInfo.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboardInfo.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> getDashboardById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}", [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard saveDashboard([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard dashboard) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard", [CtVariableReadImpl]dashboard, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteDashboard([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}", [CtVariableReadImpl]dashboardId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> assignDashboardToCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/dashboard/{dashboardId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]customerId, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> unassignDashboardFromCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/dashboard/{dashboardId}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]customerId, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> updateDashboardCustomers([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> customerIds) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}/customers", [CtVariableReadImpl]customerIds, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> addDashboardCustomers([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> customerIds) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}/customers/add", [CtVariableReadImpl]customerIds, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> removeDashboardCustomers([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> customerIds) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/dashboard/{dashboardId}/customers/remove", [CtVariableReadImpl]customerIds, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> assignDashboardToPublicCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/public/dashboard/{dashboardId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> unassignDashboardFromPublicCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dashboardId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Dashboard> dashboard = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/public/dashboard/{dashboardId}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.Dashboard.class, [CtVariableReadImpl]dashboardId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]dashboard.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> getTenantDashboards([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"tenantId", [CtVariableReadImpl]tenantId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/{tenantId}/dashboards?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> getTenantDashboards([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/dashboards?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo> getCustomerDashboards([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/dashboards?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.DashboardInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> getDeviceById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> device = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/{deviceId}", [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]deviceId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]device.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Device saveDevice([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Device device) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device", [CtVariableReadImpl]device, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/{deviceId}", [CtVariableReadImpl]deviceId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> assignDeviceToCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> device = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/device/{deviceId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]customerId, [CtVariableReadImpl]deviceId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]device.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> unassignDeviceFromCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> device = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/device/{deviceId}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]deviceId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]device.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> assignDeviceToPublicCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> device = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/public/device/{deviceId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]deviceId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]device.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials> getDeviceCredentialsByDeviceId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials> deviceCredentials = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/{deviceId}/credentials", [CtFieldReadImpl]org.thingsboard.server.common.data.security.DeviceCredentials.class, [CtVariableReadImpl]deviceId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]deviceCredentials.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials saveDeviceCredentials([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.security.DeviceCredentials deviceCredentials) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/device/credentials", [CtVariableReadImpl]deviceCredentials, [CtFieldReadImpl]org.thingsboard.server.common.data.security.DeviceCredentials.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> getTenantDevices([CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/devices?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> getTenantDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> device = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/devices?deviceName={deviceName}", [CtFieldReadImpl]org.thingsboard.server.common.data.Device.class, [CtVariableReadImpl]deviceName);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]device.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> getCustomerDevices([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/devices?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> getDevicesByIds([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> deviceIds) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/devices?deviceIds={deviceIds}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device>>()[CtClassImpl] {}, [CtInvocationImpl]listToString([CtVariableReadImpl]deviceIds)).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device> findByQuery([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.device.DeviceSearchQuery query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/devices", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]query), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Device>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype> getDeviceTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/devices", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode claimDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceName, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.ClaimRequest claimRequest) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/device/{deviceName}/claim", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]claimRequest), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>()[CtClassImpl] {}, [CtVariableReadImpl]deviceName).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void reClaimDevice([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceName) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/device/{deviceName}/claim", [CtVariableReadImpl]deviceName);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void saveRelation([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation relation) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relation", [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteRelation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromId", [CtVariableReadImpl]fromId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromType", [CtVariableReadImpl]fromType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationType", [CtVariableReadImpl]relationType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toId", [CtVariableReadImpl]toId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toType", [CtVariableReadImpl]toType);
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relation?fromId={fromId}&fromType={fromType}&relationType={relationType}&relationTypeGroup={relationTypeGroup}&toId={toId}&toType={toType}", [CtVariableReadImpl]params);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteRelations([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?entityId={entityId}&entityType={entityType}", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> getRelation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromId", [CtVariableReadImpl]fromId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromType", [CtVariableReadImpl]fromType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationType", [CtVariableReadImpl]relationType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toId", [CtVariableReadImpl]toId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toType", [CtVariableReadImpl]toType);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> entityRelation = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relation?fromId={fromId}&fromType={fromType}&relationType={relationType}&relationTypeGroup={relationTypeGroup}&toId={toId}&toType={toType}", [CtFieldReadImpl]org.thingsboard.server.common.data.relation.EntityRelation.class, [CtVariableReadImpl]params);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityRelation.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> findByFrom([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromId", [CtVariableReadImpl]fromId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromType", [CtVariableReadImpl]fromType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?fromId={fromId}&fromType={fromType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo> findInfoByFrom([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromId", [CtVariableReadImpl]fromId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromType", [CtVariableReadImpl]fromType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations/info?fromId={fromId}&fromType={fromType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> findByFrom([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fromType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromId", [CtVariableReadImpl]fromId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"fromType", [CtVariableReadImpl]fromType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationType", [CtVariableReadImpl]relationType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?fromId={fromId}&fromType={fromType}&relationType={relationType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> findByTo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String toId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toId", [CtVariableReadImpl]toId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toType", [CtVariableReadImpl]toType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?toId={toId}&toType={toType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo> findInfoByTo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String toId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toId", [CtVariableReadImpl]toId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toType", [CtVariableReadImpl]toType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?toId={toId}&toType={toType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> findByTo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String toId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String relationTypeGroup) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toId", [CtVariableReadImpl]toId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"toType", [CtVariableReadImpl]toType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationType", [CtVariableReadImpl]relationType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"relationTypeGroup", [CtVariableReadImpl]relationTypeGroup);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations?toId={toId}&toType={toType}&relationType={relationType}&relationTypeGroup={relationTypeGroup}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation> findByQuery([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationsQuery query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]query), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelation>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo> findInfoByQuery([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationsQuery query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/relations", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]query), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.relation.EntityRelationInfo>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> getEntityViewById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> entityView = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/entityView/{entityViewId}", [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class, [CtVariableReadImpl]entityViewId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityView.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView saveEntityView([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView entityView) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/entityView", [CtVariableReadImpl]entityView, [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteEntityView([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/entityView/{entityViewId}", [CtVariableReadImpl]entityViewId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> getTenantEntityView([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> entityView = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/entityViews?entityViewName={entityViewName}", [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class, [CtVariableReadImpl]entityViewName);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityView.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> assignEntityViewToCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> entityView = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/entityView/{entityViewId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class, [CtVariableReadImpl]customerId, [CtVariableReadImpl]entityViewId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityView.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> unassignEntityViewFromCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> entityView = [CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/entityView/{entityViewId}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class, [CtVariableReadImpl]entityViewId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityView.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> getCustomerEntityViews([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/entityViews?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> getTenantEntityViews([CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"type", [CtVariableReadImpl]type);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/entityViews?type={type}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> findByQuery([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.entityview.EntityViewSearchQuery query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/entityViews", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]query), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype> getEntityViewTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/entityView/types", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntitySubtype>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> assignEntityViewToPublicCustomer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityViewId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.EntityView> entityView = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/public/entityView/{entityViewId}", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.EntityView.class, [CtVariableReadImpl]entityViewId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]entityView.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Event> getEvents([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"eventType", [CtVariableReadImpl]eventType);
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"tenantId", [CtVariableReadImpl]tenantId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/events/{entityType}/{entityId}/{eventType}?tenantId={tenantId}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Event>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Event> getEvents([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"tenantId", [CtVariableReadImpl]tenantId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/events/{entityType}/{entityId}?tenantId={tenantId}&") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Event>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void handleOneWayDeviceRPCRequest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode requestBody) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/rpc/oneway/{deviceId}", [CtVariableReadImpl]requestBody, [CtVariableReadImpl]deviceId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode handleTwoWayDeviceRPCRequest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode requestBody) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/rpc/twoway/{deviceId}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]requestBody), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>()[CtClassImpl] {}, [CtVariableReadImpl]deviceId).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain> getRuleChainById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ruleChainId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain> ruleChain = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/{ruleChainId}", [CtFieldReadImpl]org.thingsboard.server.common.data.rule.RuleChain.class, [CtVariableReadImpl]ruleChainId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]ruleChain.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData> getRuleChainMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ruleChainId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData> ruleChainMetaData = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/{ruleChainId}/metadata", [CtFieldReadImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData.class, [CtVariableReadImpl]ruleChainId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]ruleChainMetaData.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain saveRuleChain([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain ruleChain) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain", [CtVariableReadImpl]ruleChain, [CtFieldReadImpl]org.thingsboard.server.common.data.rule.RuleChain.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain> setRootRuleChain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ruleChainId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain> ruleChain = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/{ruleChainId}/root", [CtLiteralImpl]null, [CtFieldReadImpl]org.thingsboard.server.common.data.rule.RuleChain.class, [CtVariableReadImpl]ruleChainId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]ruleChain.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData saveRuleChainMetaData([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData ruleChainMetaData) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/metadata", [CtVariableReadImpl]ruleChainMetaData, [CtFieldReadImpl]org.thingsboard.server.common.data.rule.RuleChainMetaData.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain> getRuleChains([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChains") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.rule.RuleChain>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteRuleChain([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ruleChainId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/{ruleChainId}", [CtVariableReadImpl]ruleChainId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> getLatestRuleNodeDebugInput([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ruleNodeId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> jsonNode = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleNode/{ruleNodeId}/debugIn", [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class, [CtVariableReadImpl]ruleNodeId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]jsonNode.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> testScript([CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode inputParams) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> jsonNode = [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/ruleChain/testScript", [CtVariableReadImpl]inputParams, [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]jsonNode.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAttributeKeys([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/keys/attributes", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString()).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAttributeKeysByScope([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/keys/attributes/{scope}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> getAttributeKvEntries([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/values/attributes?keys={keys}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtVariableReadImpl]entityId.getId(), [CtInvocationImpl]listToString([CtVariableReadImpl]keys)).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.Future<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry>> getAttributeKvEntriesAsync([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]service.submit([CtLambdaImpl]() -> [CtInvocationImpl]getAttributeKvEntries([CtVariableReadImpl]entityId, [CtVariableReadImpl]keys));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> getAttributesByScope([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/values/attributes/{scope}?keys={keys}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope, [CtInvocationImpl]listToString([CtVariableReadImpl]keys)).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getTimeseriesKeys([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/keys/timeseries", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString()).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.TsKvEntry> getLatestTimeseries([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>> timeseries = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/values/timeseries?keys={keys}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtInvocationImpl]listToString([CtVariableReadImpl]keys)).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]timeseries)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toTimeseries([CtVariableReadImpl]timeseries);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.TsKvEntry> getTimeseries([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long startTs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long endTs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long interval, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer limit, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String agg) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"keys", [CtInvocationImpl]listToString([CtVariableReadImpl]keys));
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"startTs", [CtInvocationImpl][CtVariableReadImpl]startTs.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"endTs", [CtInvocationImpl][CtVariableReadImpl]endTs.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"interval", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]interval == [CtLiteralImpl]null ? [CtLiteralImpl]"0" : [CtInvocationImpl][CtVariableReadImpl]interval.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"limit", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]limit == [CtLiteralImpl]null ? [CtLiteralImpl]"100" : [CtInvocationImpl][CtVariableReadImpl]limit.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"agg", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]agg == [CtLiteralImpl]null ? [CtLiteralImpl]"NONE" : [CtVariableReadImpl]agg);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>> timeseries = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/values/timeseries?keys={keys}&startTs={startTs}&endTs={endTs}&interval={interval}&limit={limit}&agg={agg}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]timeseries)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toTimeseries([CtVariableReadImpl]timeseries);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> saveDeviceAttributes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{deviceId}/{scope}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]request), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtVariableReadImpl]deviceId, [CtVariableReadImpl]scope).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> saveEntityAttributesV1([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/{scope}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]request), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> saveEntityAttributesV2([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/attributes/{scope}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]request), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.TsKvEntry> saveEntityTelemetry([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestBody) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>> timeseries = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/timeseries/{scope}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]requestBody), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]timeseries)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toTimeseries([CtVariableReadImpl]timeseries);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.TsKvEntry> saveEntityTelemetryWithTTL([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long ttl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestBody) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>> timeseries = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/timeseries/{scope}/{ttl}", [CtTypeAccessImpl]HttpMethod.POST, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtVariableReadImpl]requestBody), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope, [CtVariableReadImpl]ttl).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]timeseries)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toTimeseries([CtVariableReadImpl]timeseries);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.TsKvEntry> deleteEntityTimeseries([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys, [CtParameterImpl][CtTypeReferenceImpl]boolean deleteAllDataForKeys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long startTs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long endTs, [CtParameterImpl][CtTypeReferenceImpl]boolean rewriteLatestIfDeleted) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityType", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"entityId", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"keys", [CtInvocationImpl]listToString([CtVariableReadImpl]keys));
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"deleteAllDataForKeys", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]deleteAllDataForKeys));
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"startTs", [CtInvocationImpl][CtVariableReadImpl]startTs.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"endTs", [CtInvocationImpl][CtVariableReadImpl]endTs.toString());
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"rewriteLatestIfDeleted", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]rewriteLatestIfDeleted));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>> timeseries = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/timeseries/delete?keys={keys}&deleteAllDataForKeys={deleteAllDataForKeys}&startTs={startTs}&endTs={endTs}&rewriteLatestIfDeleted={rewriteLatestIfDeleted}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]timeseries)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toTimeseries([CtVariableReadImpl]timeseries);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> deleteEntityAttributes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String deviceId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{deviceId}/{scope}?keys={keys}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtVariableReadImpl]deviceId, [CtVariableReadImpl]scope, [CtInvocationImpl]listToString([CtVariableReadImpl]keys)).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.kv.AttributeKvEntry> deleteEntityAttributes([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.id.EntityId entityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> attributes = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/plugins/telemetry/{entityType}/{entityId}/{scope}?keys={keys}", [CtTypeAccessImpl]HttpMethod.DELETE, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>>()[CtClassImpl] {}, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getEntityType().name(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entityId.getId().toString(), [CtVariableReadImpl]scope, [CtInvocationImpl]listToString([CtVariableReadImpl]keys)).getBody();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtVariableReadImpl]attributes)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.thingsboard.client.tools.utils.JsonConverter.toAttributes([CtVariableReadImpl]attributes);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant> getTenantById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant> tenant = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/{tenantId}", [CtFieldReadImpl]org.thingsboard.server.common.data.Tenant.class, [CtVariableReadImpl]tenantId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]tenant.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant saveTenant([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant tenant) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant", [CtVariableReadImpl]tenant, [CtFieldReadImpl]org.thingsboard.server.common.data.Tenant.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteTenant([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/{tenantId}", [CtVariableReadImpl]tenantId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant> getTenants([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenants?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.Tenant>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> getUserById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> user = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/{userId}", [CtFieldReadImpl]org.thingsboard.server.common.data.User.class, [CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]user.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean isUserTokenAccessEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/tokenAccessEnabled", [CtFieldReadImpl]java.lang.Boolean.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> getUserToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> userToken = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/{userId}/token", [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonNode.class, [CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]userToken.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.User saveUser([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.User user, [CtParameterImpl][CtTypeReferenceImpl]boolean sendActivationMail) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user?sendActivationMail={sendActivationMail}", [CtVariableReadImpl]user, [CtFieldReadImpl]org.thingsboard.server.common.data.User.class, [CtVariableReadImpl]sendActivationMail).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sendActivationEmail([CtParameterImpl][CtTypeReferenceImpl]java.lang.String email) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/sendActivationMail?email={email}", [CtLiteralImpl]null, [CtVariableReadImpl]email);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getActivationLink([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/{userId}/activationLink", [CtFieldReadImpl]java.lang.String.class, [CtVariableReadImpl]userId).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/{userId}", [CtVariableReadImpl]userId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> getTenantAdmins([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"tenantId", [CtVariableReadImpl]tenantId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/tenant/{tenantId}/users?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User> getCustomerUsers([CtParameterImpl][CtTypeReferenceImpl]java.lang.String customerId, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"customerId", [CtVariableReadImpl]customerId);
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/customer/{customerId}/users?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.User>>()[CtClassImpl] {}, [CtVariableReadImpl]params).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setUserCredentialsEnabled([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtTypeReferenceImpl]boolean userCredentialsEnabled) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.postForLocation([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/user/{userId}/userCredentialsEnabled?serCredentialsEnabled={serCredentialsEnabled}", [CtLiteralImpl]null, [CtVariableReadImpl]userId, [CtVariableReadImpl]userCredentialsEnabled);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle> getWidgetsBundleById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String widgetsBundleId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle> widgetsBundle = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetsBundle/{widgetsBundleId}", [CtFieldReadImpl]org.thingsboard.server.common.data.widget.WidgetsBundle.class, [CtVariableReadImpl]widgetsBundleId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]widgetsBundle.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle saveWidgetsBundle([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle widgetsBundle) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetsBundle", [CtVariableReadImpl]widgetsBundle, [CtFieldReadImpl]org.thingsboard.server.common.data.widget.WidgetsBundle.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteWidgetsBundle([CtParameterImpl][CtTypeReferenceImpl]java.lang.String widgetsBundleId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetsBundle/{widgetsBundleId}", [CtVariableReadImpl]widgetsBundleId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle> getWidgetsBundles([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl]addPageLinkToParam([CtVariableReadImpl]params, [CtVariableReadImpl]pageLink);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetsBundles?") + [CtInvocationImpl]getUrlParams([CtVariableReadImpl]pageLink), [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageData<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle> getWidgetsBundles() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetsBundles", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetsBundle>>()[CtClassImpl] {}).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType> getWidgetTypeById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String widgetTypeId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType> widgetType = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetType/{widgetTypeId}", [CtFieldReadImpl]org.thingsboard.server.common.data.widget.WidgetType.class, [CtVariableReadImpl]widgetTypeId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]widgetType.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType saveWidgetType([CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType widgetType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.postForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetType", [CtVariableReadImpl]widgetType, [CtFieldReadImpl]org.thingsboard.server.common.data.widget.WidgetType.class).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void deleteWidgetType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String widgetTypeId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]restTemplate.delete([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetType/{widgetTypeId}", [CtVariableReadImpl]widgetTypeId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType> getBundleWidgetTypes([CtParameterImpl][CtTypeReferenceImpl]boolean isSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String bundleAlias) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]restTemplate.exchange([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetTypes?isSystem={isSystem}&bundleAlias={bundleAlias}", [CtTypeAccessImpl]HttpMethod.GET, [CtTypeAccessImpl]HttpEntity.EMPTY, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType>>()[CtClassImpl] {}, [CtVariableReadImpl]isSystem, [CtVariableReadImpl]bundleAlias).getBody();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType> getWidgetType([CtParameterImpl][CtTypeReferenceImpl]boolean isSystem, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String bundleAlias, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]org.thingsboard.server.common.data.widget.WidgetType> widgetType = [CtInvocationImpl][CtFieldReadImpl]restTemplate.getForEntity([CtBinaryOperatorImpl][CtFieldReadImpl]baseURL + [CtLiteralImpl]"/api/widgetType?isSystem={isSystem}&bundleAlias={bundleAlias}&alias={alias}", [CtFieldReadImpl]org.thingsboard.server.common.data.widget.WidgetType.class, [CtVariableReadImpl]isSystem, [CtVariableReadImpl]bundleAlias, [CtVariableReadImpl]alias);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]widgetType.getBody());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException exception) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exception.getStatusCode() == [CtFieldReadImpl]org.springframework.http.HttpStatus.NOT_FOUND) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]exception;
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addPageLinkToParam([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TimePageLink pageLink) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"limit", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]pageLink.getLimit()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getStartTime() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"startTime", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]pageLink.getStartTime()));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getEndTime() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"endTime", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]pageLink.getEndTime()));
        }
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"ascOrder", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]pageLink.isAscOrder()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"offset", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset().toString());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addPageLinkToParam([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params, [CtParameterImpl][CtTypeReferenceImpl]org.thingsboard.server.common.data.page.TextPageLink pageLink) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"limit", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]pageLink.getLimit()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getTextSearch() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"textSearch", [CtInvocationImpl][CtVariableReadImpl]pageLink.getTextSearch());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"idOffset", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getIdOffset().toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pageLink.getTextOffset() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"textOffset", [CtInvocationImpl][CtVariableReadImpl]pageLink.getTextOffset());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String listToString([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> list) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]",", [CtVariableReadImpl]list);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]service != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]service.shutdown();
        }
    }
}
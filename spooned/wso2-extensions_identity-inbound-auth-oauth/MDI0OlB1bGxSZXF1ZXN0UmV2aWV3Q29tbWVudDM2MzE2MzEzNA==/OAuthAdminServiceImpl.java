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
[CtPackageDeclarationImpl]package org.wso2.carbon.identity.oauth;
[CtUnresolvedImport]import org.apache.commons.lang.StringUtils;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.common.OAuth2ErrorCodes;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.ScopeDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.common.IdentityApplicationManagementException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.util.OAuth2Util;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth2.util.OAuth2Util.buildScopeString;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.validators.OAuth2ScopeValidator;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.AppInfoCache;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.OAuth2Service;
[CtUnresolvedImport]import org.wso2.carbon.utils.multitenancy.MultitenantUtils;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.OAuthUtil.handleError;
[CtUnresolvedImport]import org.apache.commons.logging.LogFactory;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.OAuthTokenExpiryTimeDTO;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dao.OAuthAppDO;
[CtUnresolvedImport]import org.wso2.carbon.user.api.UserStoreException;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.OAuthIDTokenAlgorithmDTO;
[CtUnresolvedImport]import org.wso2.carbon.context.PrivilegedCarbonContext;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.common.OAuthConstants.OauthAppStates.APP_STATE_ACTIVE;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.authz.handlers.ResponseTypeHandler;
[CtUnresolvedImport]import org.apache.commons.collections.CollectionUtils;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.Error.AUTHENTICATED_USER_NOT_FOUND;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
[CtUnresolvedImport]import org.wso2.carbon.user.core.util.UserCoreUtil;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.common.OAuthConstants;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dao.OAuthAppDAO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.cache.OAuthCache;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.TokenBindingMetaDataDTO;
[CtUnresolvedImport]import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory;
[CtUnresolvedImport]import org.wso2.carbon.context.CarbonContext;
[CtUnresolvedImport]import org.wso2.carbon.identity.core.util.IdentityUtil;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.common.OAuthConstants.TokenBindings.NONE;
[CtUnresolvedImport]import org.apache.commons.logging.Log;
[CtUnresolvedImport]import org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO;
[CtUnresolvedImport]import static org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST;
[CtUnresolvedImport]import org.apache.commons.lang.ArrayUtils;
[CtClassImpl][CtJavaDocImpl]/**
 * OAuth OSGi service implementation.
 */
public class OAuthAdminServiceImpl {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String IMPLICIT = [CtLiteralImpl]"implicit";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String AUTHORIZATION_CODE = [CtLiteralImpl]"authorization_code";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String RESPONSE_TYPE_TOKEN = [CtLiteralImpl]"token";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String RESPONSE_TYPE_ID_TOKEN = [CtLiteralImpl]"id_token";

    [CtFieldImpl]static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> allowedGrants = [CtLiteralImpl]null;

    [CtFieldImpl]static [CtArrayTypeReferenceImpl]java.lang.String[] allowedScopeValidators = [CtLiteralImpl]null;

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.commons.logging.Log LOG = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.logging.LogFactory.getLog([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.class);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers an consumer secret against the logged in user. A given user can only have a single
     * consumer secret at a time. Calling this method again and again will update the existing
     * consumer secret key.
     *
     * @return An array containing the consumer key and the consumer secret correspondingly.
     * @throws IdentityOAuthAdminException
     * 		Error when persisting the data in the persistence store.
     */
    public [CtArrayTypeReferenceImpl]java.lang.String[] registerOAuthConsumer() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loggedInUser = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Adding a consumer secret for the logged in user " + [CtVariableReadImpl]loggedInUser);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantUser = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantAwareUsername([CtVariableReadImpl]loggedInUser);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.extractDomainFromName([CtVariableReadImpl]loggedInUser);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]dao.addOAuthConsumer([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.user.core.util.UserCoreUtil.removeDomainFromName([CtVariableReadImpl]tenantUser), [CtVariableReadImpl]tenantId, [CtVariableReadImpl]userDomain);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get all registered OAuth applications for the logged in user.
     *
     * @return An array of <code>OAuthConsumerAppDTO</code> objecting containing the application
    information of the user
     * @throws IdentityOAuthAdminException
     * 		Error when reading the data from the persistence store.
     */
    public [CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[] getAllOAuthApplicationData() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[] dtos = [CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[[CtLiteralImpl]0];
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtLiteralImpl]"User not logged in to get all registered OAuth Applications";
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtVariableReadImpl]msg);
            }
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.AUTHENTICATED_USER_NOT_FOUND, [CtVariableReadImpl]msg);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO[] apps = [CtInvocationImpl][CtVariableReadImpl]dao.getOAuthConsumerAppsOfUser([CtVariableReadImpl]userName, [CtVariableReadImpl]tenantId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]apps != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]apps.length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dtos = [CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[[CtFieldReadImpl][CtVariableReadImpl]apps.length];
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO app;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]apps.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]app = [CtArrayReadImpl][CtVariableReadImpl]apps[[CtVariableReadImpl]i];
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]dtos[[CtVariableReadImpl]i] = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.buildConsumerAppDTO([CtVariableReadImpl]app);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]dtos;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get OAuth application data by the consumer key.
     *
     * @param consumerKey
     * 		Consumer Key
     * @return <code>OAuthConsumerAppDTO</code> with application information
     * @throws IdentityOAuthAdminException
     * 		Error when reading application information from persistence store.
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO getOAuthApplicationData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO dto;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO app = [CtInvocationImpl]getOAuthApp([CtVariableReadImpl]consumerKey);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]app != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dto = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.buildConsumerAppDTO([CtVariableReadImpl]app);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Found App :" + [CtInvocationImpl][CtVariableReadImpl]dto.getApplicationName()) + [CtLiteralImpl]" for consumerKey: ") + [CtVariableReadImpl]consumerKey);
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO();
            }
            [CtReturnImpl]return [CtVariableReadImpl]dto;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find a valid OAuth client for consumerKey: " + [CtVariableReadImpl]consumerKey;
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtVariableReadImpl]msg, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while retrieving the app information using consumerKey: " + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get OAuth application data by the application name.
     *
     * @param appName
     * 		OAuth application name
     * @return <code>OAuthConsumerAppDTO</code> with application information
     * @throws IdentityOAuthAdminException
     * 		Error when reading application information from persistence store.
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO getOAuthApplicationDataByAppName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appName) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO dto;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO app = [CtInvocationImpl][CtVariableReadImpl]dao.getAppInformationByAppName([CtVariableReadImpl]appName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]app != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dto = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.buildConsumerAppDTO([CtVariableReadImpl]app);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO();
            }
            [CtReturnImpl]return [CtVariableReadImpl]dto;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find a valid OAuth client with application name: " + [CtVariableReadImpl]appName;
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtVariableReadImpl]msg);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while retrieving the app information by app name: " + [CtVariableReadImpl]appName, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers an OAuth consumer application.
     *
     * @param application
     * 		<code>OAuthConsumerAppDTO</code> with application information
     * @throws IdentityOAuthAdminException
     * 		Error when persisting the application information to the persistence store.
     */
    public [CtTypeReferenceImpl]void registerOAuthApplicationData([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtInvocationImpl]registerAndRetrieveOAuthApplicationData([CtVariableReadImpl]application);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers an OAuth consumer application and retrieve application details.
     *
     * @param application
     * 		<code>OAuthConsumerAppDTO</code> with application information.
     * @return OAuthConsumerAppDTO Created OAuth application details.
     * @throws IdentityOAuthAdminException
     * 		Error when persisting the application information to the persistence store.
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO registerAndRetrieveOAuthApplicationData([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareLoggedInUser = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO app = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tenantAwareLoggedInUser != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getTenantDomain();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]application != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]app.setApplicationName([CtInvocationImpl][CtVariableReadImpl]application.getApplicationName());
                [CtInvocationImpl]validateCallbackURI([CtVariableReadImpl]application);
                [CtInvocationImpl][CtVariableReadImpl]app.setCallbackUrl([CtInvocationImpl][CtVariableReadImpl]application.getCallbackUrl());
                [CtInvocationImpl][CtVariableReadImpl]app.setState([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.APP_STATE_ACTIVE);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]application.getOauthConsumerKey())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]app.setOauthConsumerKey([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.getRandomNumber());
                    [CtInvocationImpl][CtVariableReadImpl]app.setOauthConsumerSecret([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.getRandomNumber());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]app.setOauthConsumerKey([CtInvocationImpl][CtVariableReadImpl]application.getOauthConsumerKey());
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]application.getOauthConsumerSecret())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]app.setOauthConsumerSecret([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.getRandomNumber());
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]app.setOauthConsumerSecret([CtInvocationImpl][CtVariableReadImpl]application.getOauthConsumerSecret());
                    }
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser defaultAppOwner = [CtInvocationImpl]buildAuthenticatedUser([CtVariableReadImpl]tenantAwareLoggedInUser, [CtVariableReadImpl]tenantDomain);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser appOwner = [CtInvocationImpl]getAppOwner([CtVariableReadImpl]application, [CtVariableReadImpl]defaultAppOwner);
                [CtInvocationImpl][CtVariableReadImpl]app.setAppOwner([CtVariableReadImpl]appOwner);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]application.getOAuthVersion() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]app.setOauthVersion([CtInvocationImpl][CtVariableReadImpl]application.getOAuthVersion());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// by default, assume OAuth 2.0, if it is not set.
                    [CtVariableReadImpl]app.setOauthVersion([CtTypeAccessImpl]OAuthConstants.OAuthVersions.VERSION_2);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAuthVersions.VERSION_2.equals([CtInvocationImpl][CtVariableReadImpl]app.getOauthVersion())) [CtBlockImpl]{
                    [CtInvocationImpl]validateGrantTypes([CtVariableReadImpl]application);
                    [CtInvocationImpl][CtVariableReadImpl]app.setGrantTypes([CtInvocationImpl][CtVariableReadImpl]application.getGrantTypes());
                    [CtInvocationImpl][CtVariableReadImpl]app.setScopeValidators([CtInvocationImpl]filterScopeValidators([CtVariableReadImpl]application));
                    [CtInvocationImpl][CtVariableReadImpl]app.setAudiences([CtInvocationImpl][CtVariableReadImpl]application.getAudiences());
                    [CtInvocationImpl][CtVariableReadImpl]app.setPkceMandatory([CtInvocationImpl][CtVariableReadImpl]application.getPkceMandatory());
                    [CtInvocationImpl][CtVariableReadImpl]app.setPkceSupportPlain([CtInvocationImpl][CtVariableReadImpl]application.getPkceSupportPlain());
                    [CtInvocationImpl][CtCommentImpl]// Validate access token expiry configurations.
                    validateTokenExpiryConfigurations([CtVariableReadImpl]application);
                    [CtInvocationImpl][CtVariableReadImpl]app.setUserAccessTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]application.getUserAccessTokenExpiryTime());
                    [CtInvocationImpl][CtVariableReadImpl]app.setApplicationAccessTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]application.getApplicationAccessTokenExpiryTime());
                    [CtInvocationImpl][CtVariableReadImpl]app.setRefreshTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]application.getRefreshTokenExpiryTime());
                    [CtInvocationImpl][CtVariableReadImpl]app.setIdTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]application.getIdTokenExpiryTime());
                    [CtInvocationImpl][CtCommentImpl]// Set OIDC Config Properties.
                    [CtVariableReadImpl]app.setRequestObjectSignatureValidationEnabled([CtInvocationImpl][CtVariableReadImpl]application.isRequestObjectSignatureValidationEnabled());
                    [CtInvocationImpl][CtVariableReadImpl]app.setIdTokenEncryptionEnabled([CtInvocationImpl][CtVariableReadImpl]application.isIdTokenEncryptionEnabled());
                    [CtInvocationImpl][CtVariableReadImpl]app.setIdTokenEncryptionAlgorithm([CtInvocationImpl][CtVariableReadImpl]application.getIdTokenEncryptionAlgorithm());
                    [CtInvocationImpl][CtVariableReadImpl]app.setIdTokenEncryptionMethod([CtInvocationImpl][CtVariableReadImpl]application.getIdTokenEncryptionMethod());
                    [CtInvocationImpl][CtVariableReadImpl]app.setBackChannelLogoutUrl([CtInvocationImpl][CtVariableReadImpl]application.getBackChannelLogoutUrl());
                    [CtInvocationImpl][CtVariableReadImpl]app.setFrontchannelLogoutUrl([CtInvocationImpl][CtVariableReadImpl]application.getFrontchannelLogoutUrl());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]application.getTokenType() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]app.setTokenType([CtInvocationImpl][CtVariableReadImpl]application.getTokenType());
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]app.setTokenType([CtInvocationImpl]getDefaultTokenType());
                    }
                    [CtInvocationImpl][CtVariableReadImpl]app.setBypassClientCredentials([CtInvocationImpl][CtVariableReadImpl]application.isBypassClientCredentials());
                    [CtInvocationImpl][CtVariableReadImpl]app.setRenewRefreshTokenEnabled([CtInvocationImpl][CtVariableReadImpl]application.getRenewRefreshTokenEnabled());
                    [CtInvocationImpl][CtVariableReadImpl]app.setTokenBindingType([CtInvocationImpl][CtVariableReadImpl]application.getTokenBindingType());
                }
                [CtInvocationImpl][CtVariableReadImpl]dao.addOAuthApplication([CtVariableReadImpl]app);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().addToCache([CtInvocationImpl][CtVariableReadImpl]app.getOauthConsumerKey(), [CtVariableReadImpl]app);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Oauth Application registration success : " + [CtInvocationImpl][CtVariableReadImpl]application.getApplicationName()) + [CtLiteralImpl]" in ") + [CtLiteralImpl]"tenant domain: ") + [CtVariableReadImpl]tenantDomain);
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtLiteralImpl]"No application details in the request. Failed to register OAuth App.";
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtVariableReadImpl]message);
                }
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtVariableReadImpl]message);
            }
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]application != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"No authenticated user found. Failed to register OAuth App: " + [CtInvocationImpl][CtVariableReadImpl]application.getApplicationName());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtLiteralImpl]"No authenticated user found. Failed to register OAuth App");
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtLiteralImpl]"No authenticated user found. Failed to register OAuth App.";
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.AUTHENTICATED_USER_NOT_FOUND, [CtVariableReadImpl]message);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.buildConsumerAppDTO([CtVariableReadImpl]app);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void validateGrantTypes([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] requestGrants = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getGrantTypes().split([CtLiteralImpl]"\\s");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> allowedGrantTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl]getAllowedGrantTypes()));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requestedGrant : [CtVariableReadImpl]requestGrants) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]requestedGrant)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]allowedGrantTypes.contains([CtVariableReadImpl]requestedGrant)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"'%s' grant type is not allowed.", [CtVariableReadImpl]requestedGrant);
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtVariableReadImpl]msg);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException handleClientError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Error errorMessage, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException([CtInvocationImpl][CtVariableReadImpl]errorMessage.getErrorCode(), [CtVariableReadImpl]msg);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException handleClientError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Error errorMessage, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException([CtInvocationImpl][CtVariableReadImpl]errorMessage.getErrorCode(), [CtVariableReadImpl]msg, [CtVariableReadImpl]ex);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void validateCallbackURI([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthClientException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isCallbackUriRequired = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getGrantTypes().contains([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.AUTHORIZATION_CODE) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getGrantTypes().contains([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.IMPLICIT);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]isCallbackUriRequired && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]application.getCallbackUrl())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtLiteralImpl]"Callback URI is mandatory for Code or Implicit grant types");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update existing consumer application.
     *
     * @param consumerAppDTO
     * 		<code>OAuthConsumerAppDTO</code> with updated application information
     * @throws IdentityOAuthAdminException
     * 		Error when updating the underlying identity persistence store.
     */
    public [CtTypeReferenceImpl]void updateConsumerApplication([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO consumerAppDTO) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtLiteralImpl]"Error while updating the app information.";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oauthConsumerKey = [CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getOauthConsumerKey();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtVariableReadImpl]oauthConsumerKey) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getOauthConsumerSecret())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtLiteralImpl]"ConsumerKey or ConsumerSecret is not provided for updating the OAuth application.";
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtVariableReadImpl]errorMessage);
            }
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtVariableReadImpl]errorMessage);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loggedInUserName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareLoggedInUserName = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantAwareUsername([CtVariableReadImpl]loggedInUserName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oauthappdo;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]oauthappdo = [CtInvocationImpl]getOAuthApp([CtVariableReadImpl]oauthConsumerKey);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthappdo == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"OAuth application cannot be found for consumerKey: " + [CtVariableReadImpl]oauthConsumerKey;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtVariableReadImpl]msg);
                }
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtVariableReadImpl]msg);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.equals([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getOauthConsumerSecret(), [CtInvocationImpl][CtVariableReadImpl]oauthappdo.getOauthConsumerSecret())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid ConsumerSecret is provided for updating the OAuth application with " + [CtLiteralImpl]"consumerKey: ") + [CtVariableReadImpl]oauthConsumerKey;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtVariableReadImpl]errorMessage);
                }
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtVariableReadImpl]errorMessage);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find a valid OAuth client for consumerKey: " + [CtVariableReadImpl]oauthConsumerKey;
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtVariableReadImpl]msg, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtLiteralImpl]"Error while updating the app information.", [CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser defaultAppOwner = [CtInvocationImpl][CtVariableReadImpl]oauthappdo.getAppOwner();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser appOwner = [CtInvocationImpl]getAppOwner([CtVariableReadImpl]consumerAppDTO, [CtVariableReadImpl]defaultAppOwner);
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setAppOwner([CtVariableReadImpl]appOwner);
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setOauthConsumerKey([CtVariableReadImpl]oauthConsumerKey);
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setOauthConsumerSecret([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getOauthConsumerSecret());
        [CtInvocationImpl]validateCallbackURI([CtVariableReadImpl]consumerAppDTO);
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setCallbackUrl([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getCallbackUrl());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setApplicationName([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getApplicationName());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setPkceMandatory([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getPkceMandatory());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setPkceSupportPlain([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getPkceSupportPlain());
        [CtInvocationImpl][CtCommentImpl]// Validate access token expiry configurations.
        validateTokenExpiryConfigurations([CtVariableReadImpl]consumerAppDTO);
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setUserAccessTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getUserAccessTokenExpiryTime());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setApplicationAccessTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getApplicationAccessTokenExpiryTime());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setRefreshTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getRefreshTokenExpiryTime());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setIdTokenExpiryTime([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getIdTokenExpiryTime());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setTokenType([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getTokenType());
        [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setBypassClientCredentials([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.isBypassClientCredentials());
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]OAuthConstants.OAuthVersions.VERSION_2.equals([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getOAuthVersion())) [CtBlockImpl]{
            [CtInvocationImpl]validateGrantTypes([CtVariableReadImpl]consumerAppDTO);
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setGrantTypes([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getGrantTypes());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setAudiences([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getAudiences());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setScopeValidators([CtInvocationImpl]filterScopeValidators([CtVariableReadImpl]consumerAppDTO));
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setRequestObjectSignatureValidationEnabled([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.isRequestObjectSignatureValidationEnabled());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setIdTokenEncryptionEnabled([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.isIdTokenEncryptionEnabled());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setIdTokenEncryptionAlgorithm([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getIdTokenEncryptionAlgorithm());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setIdTokenEncryptionMethod([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getIdTokenEncryptionMethod());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setBackChannelLogoutUrl([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getBackChannelLogoutUrl());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setFrontchannelLogoutUrl([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getFrontchannelLogoutUrl());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setRenewRefreshTokenEnabled([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getRenewRefreshTokenEnabled());
            [CtInvocationImpl][CtVariableReadImpl]oauthappdo.setTokenBindingType([CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getTokenBindingType());
        }
        [CtInvocationImpl][CtVariableReadImpl]dao.updateConsumerApplication([CtVariableReadImpl]oauthappdo);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().addToCache([CtInvocationImpl][CtVariableReadImpl]oauthappdo.getOauthConsumerKey(), [CtVariableReadImpl]oauthappdo);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Oauth Application update success : " + [CtInvocationImpl][CtVariableReadImpl]consumerAppDTO.getApplicationName()) + [CtLiteralImpl]" in ") + [CtLiteralImpl]"tenant domain: ") + [CtVariableReadImpl]tenantDomain);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return  * @throws IdentityOAuthAdminException
     */
    public [CtTypeReferenceImpl]java.lang.String getOauthApplicationState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getOAuth2Service().getOauthApplicationState([CtVariableReadImpl]consumerKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To insert oidc scopes and claims in the related db tables.
     *
     * @param scope
     * 		an oidc scope
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when inserting scopes or claims.
     */
    public [CtTypeReferenceImpl]void addScope([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] claims) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotEmpty([CtVariableReadImpl]scope)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().addScope([CtVariableReadImpl]tenantId, [CtVariableReadImpl]scope, [CtVariableReadImpl]claims);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtLiteralImpl]"The scope can not be empty.");
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtLiteralImpl]"Error while inserting OIDC scopes and claims.", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To retrieve all persisted oidc scopes with mapped claims.
     *
     * @return all persisted scopes and claims
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when loading scopes and claims.
     */
    public [CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO[] getScopes() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO> scopeDTOList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().getScopes([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.isNotEmpty([CtVariableReadImpl]scopeDTOList)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]scopeDTOList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO[[CtInvocationImpl][CtVariableReadImpl]scopeDTOList.size()]);
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtLiteralImpl]"Could not find scope claim mapping. Hence returning an empty array.");
                }
                [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO[[CtLiteralImpl]0];
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while loading OIDC scopes and claims for tenant: " + [CtVariableReadImpl]tenantId, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To remove persisted scopes and claims.
     *
     * @param scope
     * 		oidc scope
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when deleting scopes and claims.
     */
    public [CtTypeReferenceImpl]void deleteScope([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().deleteScope([CtVariableReadImpl]scope, [CtVariableReadImpl]tenantId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while deleting OIDC scope: " + [CtVariableReadImpl]scope, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To retrieve all persisted oidc scopes.
     *
     * @return list of scopes persisted.
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when loading oidc scopes.
     */
    public [CtArrayTypeReferenceImpl]java.lang.String[] getScopeNames() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> scopeDTOList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().getScopeNames([CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.isNotEmpty([CtVariableReadImpl]scopeDTOList)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]scopeDTOList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]scopeDTOList.size()]);
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtLiteralImpl]"Could not load oidc scopes. Hence returning an empty array.");
                }
                [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0];
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while loading OIDC scopes and claims for tenant: " + [CtVariableReadImpl]tenantId, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To retrieve oidc claims mapped to an oidc scope.
     *
     * @param scope
     * 		scope
     * @return list of claims which are mapped to the oidc scope.
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when lading oidc claims.
     */
    public [CtArrayTypeReferenceImpl]java.lang.String[] getClaims([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.ScopeDTO scopeDTO = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().getClaims([CtVariableReadImpl]scope, [CtVariableReadImpl]tenantId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]scopeDTO != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.ArrayUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]scopeDTO.getClaim())) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]scopeDTO.getClaim();
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtLiteralImpl]"Could not load oidc claims. Hence returning an empty array.");
                }
                [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0];
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while loading OIDC claims for the scope: " + [CtVariableReadImpl]scope) + [CtLiteralImpl]" in tenant: ") + [CtVariableReadImpl]tenantId, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To add new claims for an existing scope.
     *
     * @param scope
     * 		scope name
     * @param addClaims
     * 		list of oidc claims to be added
     * @param deleteClaims
     * 		list of oidc claims to be deleted
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when adding a new claim for a scope.
     */
    public [CtTypeReferenceImpl]void updateScope([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] addClaims, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] deleteClaims) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().updateScope([CtVariableReadImpl]scope, [CtVariableReadImpl]tenantId, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]addClaims), [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]deleteClaims));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while updating OIDC claims for the scope: " + [CtVariableReadImpl]scope) + [CtLiteralImpl]" in tenant: ") + [CtVariableReadImpl]tenantId, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * To load id of the scope table.
     *
     * @param scope
     * 		scope name
     * @return id of the given scope
     * @throws IdentityOAuthAdminException
     * 		if an error occurs when loading scope id.
     */
    public [CtTypeReferenceImpl]boolean isScopeExist([CtParameterImpl][CtTypeReferenceImpl]java.lang.String scope) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tenantId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getScopeClaimMappingDAO().isScopeExist([CtVariableReadImpl]scope, [CtVariableReadImpl]tenantId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtLiteralImpl]"Error while inserting the scopes.", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param consumerKey
     * @param newState
     * @throws IdentityOAuthAdminException
     */
    public [CtTypeReferenceImpl]void updateConsumerAppState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String newState) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oAuthAppDO = [CtInvocationImpl]getOAuthApp([CtVariableReadImpl]consumerKey);
            [CtInvocationImpl][CtCommentImpl]// change the state
            [CtVariableReadImpl]oAuthAppDO.setState([CtVariableReadImpl]newState);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
            [CtInvocationImpl][CtVariableReadImpl]properties.setProperty([CtTypeAccessImpl]OAuthConstants.OAUTH_APP_NEW_STATE, [CtVariableReadImpl]newState);
            [CtInvocationImpl][CtVariableReadImpl]properties.setProperty([CtTypeAccessImpl]OAuthConstants.ACTION_PROPERTY_KEY, [CtTypeAccessImpl]OAuthConstants.ACTION_REVOKE);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().clearCacheEntry([CtVariableReadImpl]consumerKey);
            [CtInvocationImpl]updateAppAndRevokeTokensAndAuthzCodes([CtVariableReadImpl]consumerKey, [CtVariableReadImpl]properties);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"App state is updated to:" + [CtVariableReadImpl]newState) + [CtLiteralImpl]" in the AppInfoCache for OAuth App with ") + [CtLiteralImpl]"consumerKey: ") + [CtVariableReadImpl]consumerKey);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtLiteralImpl]"Error while updating state of OAuth app with consumerKey: " + [CtVariableReadImpl]consumerKey;
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtVariableReadImpl]msg, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while updating state of OAuth app with consumerKey: " + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Regenerate consumer secret for the application.
     *
     * @param consumerKey
     * 		Consumer key for the application.
     * @throws IdentityOAuthAdminException
     * 		Error while regenerating the consumer secret.
     */
    public [CtTypeReferenceImpl]void updateOauthSecretKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtInvocationImpl]updateAndRetrieveOauthSecretKey([CtVariableReadImpl]consumerKey);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Regenerate consumer secret for the application and retrieve application details.
     *
     * @param consumerKey
     * 		Consumer key for the application.
     * @return OAuthConsumerAppDTO OAuth application details.
     * @throws IdentityOAuthAdminException
     * 		Error while regenerating the consumer secret.
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO updateAndRetrieveOauthSecretKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newSecret = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.getRandomNumber();
        [CtInvocationImpl][CtVariableReadImpl]properties.setProperty([CtTypeAccessImpl]OAuthConstants.OAUTH_APP_NEW_SECRET_KEY, [CtVariableReadImpl]newSecret);
        [CtInvocationImpl][CtVariableReadImpl]properties.setProperty([CtTypeAccessImpl]OAuthConstants.ACTION_PROPERTY_KEY, [CtTypeAccessImpl]OAuthConstants.ACTION_REGENERATE);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().clearCacheEntry([CtVariableReadImpl]consumerKey);
        [CtInvocationImpl]updateAppAndRevokeTokensAndAuthzCodes([CtVariableReadImpl]consumerKey, [CtVariableReadImpl]properties);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Client Secret for OAuth app with consumerKey: " + [CtVariableReadImpl]consumerKey) + [CtLiteralImpl]" updated in OAuthCache.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO updatedApplication = [CtInvocationImpl]getOAuthApplicationData([CtVariableReadImpl]consumerKey);
        [CtInvocationImpl][CtVariableReadImpl]updatedApplication.setOauthConsumerSecret([CtVariableReadImpl]newSecret);
        [CtReturnImpl]return [CtVariableReadImpl]updatedApplication;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void updateAppAndRevokeTokensAndAuthzCodes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties properties) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countToken = [CtLiteralImpl]0;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO> activeDetailedTokens = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getActiveAcessTokenDataByConsumerKey([CtVariableReadImpl]consumerKey);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] accessTokens = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]activeDetailedTokens.size()];
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO detailToken : [CtVariableReadImpl]activeDetailedTokens) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String token = [CtInvocationImpl][CtVariableReadImpl]detailToken.getAccessToken();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]accessTokens[[CtVariableReadImpl]countToken] = [CtVariableReadImpl]token;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countToken++;
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey cacheKeyToken = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]token);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().clearCacheEntry([CtVariableReadImpl]cacheKeyToken);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scope = [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]detailToken.getScope());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String authorizedUser = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detailToken.getAuthzUser().toString();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String authenticatedIDP = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detailToken.getAuthzUser().getFederatedIdPName();
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isUsernameCaseSensitive = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.isUserStoreInUsernameCaseSensitive([CtVariableReadImpl]authorizedUser);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cacheKeyString;
                [CtIfImpl]if ([CtVariableReadImpl]isUsernameCaseSensitive) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]cacheKeyString = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]consumerKey + [CtLiteralImpl]":") + [CtVariableReadImpl]authorizedUser) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]cacheKeyString = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]consumerKey + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]authorizedUser.toLowerCase()) + [CtLiteralImpl]":") + [CtVariableReadImpl]scope) + [CtLiteralImpl]":") + [CtVariableReadImpl]authenticatedIDP;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey cacheKeyUser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]cacheKeyString);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().clearCacheEntry([CtVariableReadImpl]cacheKeyUser);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Access tokens and token of users are removed from the cache for OAuth App with " + [CtLiteralImpl]"consumerKey: ") + [CtVariableReadImpl]consumerKey);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> authorizationCodes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAuthorizationCodeDAO().getActiveAuthorizationCodesByConsumerKey([CtVariableReadImpl]consumerKey);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String authorizationCode : [CtVariableReadImpl]authorizationCodes) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey cacheKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]authorizationCode);
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().clearCacheEntry([CtVariableReadImpl]cacheKey);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Access tokens are removed from the cache for OAuth App with consumerKey: " + [CtVariableReadImpl]consumerKey);
            }
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getTokenManagementDAO().updateAppAndRevokeTokensAndAuthzCodes([CtVariableReadImpl]consumerKey, [CtVariableReadImpl]properties, [CtInvocationImpl][CtVariableReadImpl]authorizationCodes.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]authorizationCodes.size()]), [CtVariableReadImpl]accessTokens);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception | [CtTypeReferenceImpl]org.wso2.carbon.identity.application.common.IdentityApplicationManagementException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error in updating oauth app & revoking access tokens and authz " + [CtLiteralImpl]"codes for OAuth App with consumerKey: ") + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Removes an OAuth consumer application.
     *
     * @param consumerKey
     * 		Consumer Key
     * @throws IdentityOAuthAdminException
     * 		Error when removing the consumer information from the database.
     */
    public [CtTypeReferenceImpl]void removeOAuthApplicationData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtInvocationImpl][CtVariableReadImpl]dao.removeConsumerApplication([CtVariableReadImpl]consumerKey);
        [CtInvocationImpl][CtCommentImpl]// remove client credentials from cache
        [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.OAuthCache.getInstance().clearCacheEntry([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.cache.OAuthCacheKey([CtVariableReadImpl]consumerKey));
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().clearCacheEntry([CtVariableReadImpl]consumerKey);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Client credentials are removed from the cache for OAuth App with consumerKey: " + [CtVariableReadImpl]consumerKey);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get apps that are authorized by the given user
     *
     * @return OAuth applications authorized by the user that have tokens in ACTIVE or EXPIRED state
     */
    public [CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[] getAppsAuthorizedByUser() throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareLoggedInUserName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser loggedInUser = [CtInvocationImpl]buildAuthenticatedUser([CtVariableReadImpl]tenantAwareLoggedInUserName, [CtVariableReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String username = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.user.core.util.UserCoreUtil.addTenantDomainToEntry([CtVariableReadImpl]tenantAwareLoggedInUserName, [CtVariableReadImpl]tenantDomain);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkAccessTokenPartitioningEnabled() && [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkUserNameAssertionEnabled()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserStoreForFederatedUser([CtVariableReadImpl]loggedInUser);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while getting user store domain for User ID : " + [CtVariableReadImpl]loggedInUser;
                [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> clientIds;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]clientIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getTokenManagementDAO().getAllTimeAuthorizedClientIds([CtVariableReadImpl]loggedInUser);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while retrieving apps authorized by User ID : " + [CtVariableReadImpl]username;
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO> appDTOs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clientId : [CtVariableReadImpl]clientIds) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO> accessTokenDOs;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]accessTokenDOs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getAccessTokens([CtVariableReadImpl]clientId, [CtVariableReadImpl]loggedInUser, [CtVariableReadImpl]userStoreDomain, [CtLiteralImpl]true);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while retrieving access tokens issued for " + [CtLiteralImpl]"Client ID : ") + [CtVariableReadImpl]clientId) + [CtLiteralImpl]", User ID : ") + [CtVariableReadImpl]username;
                [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]accessTokenDOs.isEmpty()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> distinctClientUserScopeCombo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO : [CtVariableReadImpl]accessTokenDOs) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO scopedToken;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scopeString = [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getScope());
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]scopedToken = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getLatestAccessToken([CtVariableReadImpl]clientId, [CtVariableReadImpl]loggedInUser, [CtVariableReadImpl]userStoreDomain, [CtVariableReadImpl]scopeString, [CtLiteralImpl]true);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]scopedToken != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]distinctClientUserScopeCombo.contains([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]username))) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO appDO = [CtInvocationImpl]getOAuthAppDO([CtInvocationImpl][CtVariableReadImpl]scopedToken.getConsumerKey());
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Found App: " + [CtInvocationImpl][CtVariableReadImpl]appDO.getApplicationName()) + [CtLiteralImpl]" for user: ") + [CtVariableReadImpl]username);
                            }
                            [CtInvocationImpl][CtVariableReadImpl]appDTOs.add([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.buildConsumerAppDTO([CtVariableReadImpl]appDO));
                            [CtInvocationImpl][CtVariableReadImpl]distinctClientUserScopeCombo.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clientId + [CtLiteralImpl]":") + [CtVariableReadImpl]username);
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while retrieving latest access token issued for Client ID :" + [CtLiteralImpl]" ") + [CtVariableReadImpl]clientId) + [CtLiteralImpl]", User ID : ") + [CtVariableReadImpl]username) + [CtLiteralImpl]" and Scope : ") + [CtVariableReadImpl]scopeString;
                        [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                    }
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]appDTOs.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[[CtLiteralImpl]0]);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO getOAuthAppDO([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO appDO;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]appDO = [CtInvocationImpl]getOAuthApp([CtVariableReadImpl]consumerKey);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_OAUTH_CLIENT, [CtBinaryOperatorImpl][CtLiteralImpl]"Invalid ConsumerKey: " + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while retrieving app information for Client ID : " + [CtVariableReadImpl]consumerKey, [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]appDO;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Revoke authorization for OAuth apps by resource owners
     *
     * @param revokeRequestDTO
     * 		DTO representing authorized user and apps[]
     * @return revokeRespDTO DTO representing success or failure message
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO revokeAuthzForAppsByResourceOwner([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO revokeRequestDTO) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtInvocationImpl]triggerPreRevokeListeners([CtVariableReadImpl]revokeRequestDTO);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]revokeRequestDTO.getApps() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]revokeRequestDTO.getApps().length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareLoggedInUserName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getUsername();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser user = [CtInvocationImpl]buildAuthenticatedUser([CtVariableReadImpl]tenantAwareLoggedInUserName, [CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userName = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.user.core.util.UserCoreUtil.addTenantDomainToEntry([CtVariableReadImpl]tenantAwareLoggedInUserName, [CtVariableReadImpl]tenantDomain);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userStoreDomain = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkAccessTokenPartitioningEnabled() && [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.checkUserNameAssertionEnabled()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]userStoreDomain = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.getUserStoreForFederatedUser([CtVariableReadImpl]user);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error occurred while getting user store domain from User ID : " + [CtVariableReadImpl]user, [CtVariableReadImpl]e);
                }
            }
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO[] appDTOs = [CtInvocationImpl]getAppsAuthorizedByUser();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String appName : [CtInvocationImpl][CtVariableReadImpl]revokeRequestDTO.getApps()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO appDTO : [CtVariableReadImpl]appDTOs) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]appDTO.getApplicationName().equals([CtVariableReadImpl]appName)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO> accessTokenDOs;
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtAssignmentImpl][CtCommentImpl]// Retrieve all ACTIVE or EXPIRED access tokens for particular client authorized by this
                            [CtCommentImpl]// user
                            [CtVariableWriteImpl]accessTokenDOs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getAccessTokens([CtInvocationImpl][CtVariableReadImpl]appDTO.getOauthConsumerKey(), [CtVariableReadImpl]user, [CtVariableReadImpl]userStoreDomain, [CtLiteralImpl]true);
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while retrieving access tokens issued for " + [CtLiteralImpl]"Client ID : ") + [CtInvocationImpl][CtVariableReadImpl]appDTO.getOauthConsumerKey()) + [CtLiteralImpl]", User ID : ") + [CtVariableReadImpl]userName;
                            [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser authzUser;
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO : [CtVariableReadImpl]accessTokenDOs) [CtBlockImpl]{
                            [CtAssignmentImpl][CtCommentImpl]// Clear cache with AccessTokenDO
                            [CtVariableWriteImpl]authzUser = [CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getAuthzUser();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tokenBindingReference = [CtFieldReadImpl]NONE;
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getTokenBinding() != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getTokenBinding().getBindingReference())) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]tokenBindingReference = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getTokenBinding().getBindingReference();
                            }
                            [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.clearOAuthCache([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getConsumerKey(), [CtVariableReadImpl]authzUser, [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getScope()), [CtVariableReadImpl]tokenBindingReference);
                            [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.clearOAuthCache([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getConsumerKey(), [CtVariableReadImpl]authzUser, [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getScope()));
                            [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.clearOAuthCache([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getConsumerKey(), [CtVariableReadImpl]authzUser);
                            [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.OAuthUtil.clearOAuthCache([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getAccessToken());
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO scopedToken;
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtAssignmentImpl][CtCommentImpl]// Retrieve latest access token for particular client, user and scope combination if
                                [CtCommentImpl]// its ACTIVE or EXPIRED.
                                [CtVariableWriteImpl]scopedToken = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().getLatestAccessToken([CtInvocationImpl][CtVariableReadImpl]appDTO.getOauthConsumerKey(), [CtVariableReadImpl]user, [CtVariableReadImpl]userStoreDomain, [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getScope()), [CtLiteralImpl]true);
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while retrieving latest " + [CtLiteralImpl]"access token issued for Client ID : ") + [CtInvocationImpl][CtVariableReadImpl]appDTO.getOauthConsumerKey()) + [CtLiteralImpl]", User ID : ") + [CtVariableReadImpl]userName) + [CtLiteralImpl]" and Scope : ") + [CtInvocationImpl]buildScopeString([CtInvocationImpl][CtVariableReadImpl]accessTokenDO.getScope());
                                [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]scopedToken != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtTryImpl][CtCommentImpl]// Revoking token from database
                                try [CtBlockImpl]{
                                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO().revokeAccessTokens([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtVariableReadImpl]scopedToken.getAccessToken() });
                                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while revoking " + [CtLiteralImpl]"Access Token : ") + [CtInvocationImpl][CtVariableReadImpl]scopedToken.getAccessToken();
                                    [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                                }
                                [CtTryImpl][CtCommentImpl]// Revoking the oauth consent from database.
                                try [CtBlockImpl]{
                                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getTokenManagementDAO().revokeOAuthConsentByApplicationAndUser([CtInvocationImpl][CtVariableReadImpl]authzUser.getAuthenticatedSubjectIdentifier(), [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]appName);
                                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while removing OAuth Consent of Application " + [CtVariableReadImpl]appName) + [CtLiteralImpl]" of user ") + [CtVariableReadImpl]userName;
                                    [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
                                }
                            }
                            [CtInvocationImpl]triggerPostRevokeListeners([CtVariableReadImpl]revokeRequestDTO, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO(), [CtInvocationImpl][CtVariableReadImpl]accessTokenDOs.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO[[CtLiteralImpl]0]));
                        }
                    }
                }
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO revokeRespDTO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO();
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setError([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setErrorCode([CtTypeAccessImpl]OAuth2ErrorCodes.INVALID_REQUEST);
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setErrorMsg([CtLiteralImpl]"Invalid revocation request");
            [CtInvocationImpl][CtCommentImpl]// passing a single element array with null element to make sure listeners are triggered at least once
            triggerPostRevokeListeners([CtVariableReadImpl]revokeRequestDTO, [CtVariableReadImpl]revokeRespDTO, [CtNewArrayImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO[]{ [CtLiteralImpl]null });
            [CtReturnImpl]return [CtVariableReadImpl]revokeRespDTO;
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Revoke approve always of the consent for OAuth apps by resource owners
     *
     * @param appName
     * 		name of the app
     * @param state
     * 		state of the approve always
     * @return revokeRespDTO DTO representing success or failure message
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO updateApproveAlwaysForAppConsentByResourceOwner([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String state) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO revokeRespDTO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareUserName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.PrivilegedCarbonContext.getThreadLocalCarbonContext().getUsername();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory.getInstance().getTokenManagementDAO().updateApproveAlwaysForAppConsentByResourceOwner([CtVariableReadImpl]tenantAwareUserName, [CtVariableReadImpl]tenantDomain, [CtVariableReadImpl]appName, [CtVariableReadImpl]state);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error occurred while revoking OAuth Consent approve always of Application " + [CtVariableReadImpl]appName) + [CtLiteralImpl]" of user ") + [CtVariableReadImpl]tenantAwareUserName;
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.error([CtVariableReadImpl]errorMsg, [CtVariableReadImpl]e);
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setError([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setErrorCode([CtTypeAccessImpl]OAuth2ErrorCodes.INVALID_REQUEST);
            [CtInvocationImpl][CtVariableReadImpl]revokeRespDTO.setErrorMsg([CtLiteralImpl]"Invalid revocation request");
        }
        [CtReturnImpl]return [CtVariableReadImpl]revokeRespDTO;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void triggerPreRevokeListeners([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO revokeRequestDTO) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor oAuthEventInterceptorProxy = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getOAuthEventInterceptorProxy();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]oAuthEventInterceptorProxy != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.isEnabled()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> paramMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
                [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.onPreTokenRevocationByResourceOwner([CtVariableReadImpl]revokeRequestDTO, [CtVariableReadImpl]paramMap);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtLiteralImpl]"Error occurred with Oauth pre-revoke listener ", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void triggerPostRevokeListeners([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO revokeRequestDTO, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO revokeRespDTO, [CtParameterImpl][CtArrayTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO[] accessTokenDOs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor oAuthEventInterceptorProxy = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getOAuthEventInterceptorProxy();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.model.AccessTokenDO accessTokenDO : [CtVariableReadImpl]accessTokenDOs) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]oAuthEventInterceptorProxy != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.isEnabled()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> paramMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
                    [CtInvocationImpl][CtVariableReadImpl]oAuthEventInterceptorProxy.onPostTokenRevocationByResourceOwner([CtVariableReadImpl]revokeRequestDTO, [CtVariableReadImpl]revokeRespDTO, [CtVariableReadImpl]accessTokenDO, [CtVariableReadImpl]paramMap);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.error([CtLiteralImpl]"Error occurred with post revocation listener ", [CtVariableReadImpl]e);
                }
            }
        }
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]java.lang.String[] getAllowedGrantTypes() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedGrants == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminService.class) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedGrants == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> allowedGrantSet = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedGrantTypes().keySet();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> modifiableGrantSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet([CtVariableReadImpl]allowedGrantSet);
                    [CtIfImpl]if ([CtInvocationImpl]isImplicitGrantEnabled()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]modifiableGrantSet.add([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.IMPLICIT);
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedGrants = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>([CtVariableReadImpl]modifiableGrantSet);
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedGrants.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedGrants.size()]);
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean isImplicitGrantEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.authz.handlers.ResponseTypeHandler> responseTypeHandlers = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedResponseTypes();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseType : [CtInvocationImpl][CtVariableReadImpl]responseTypeHandlers.keySet()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]responseType.contains([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.RESPONSE_TYPE_TOKEN) || [CtInvocationImpl][CtVariableReadImpl]responseType.contains([CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.RESPONSE_TYPE_ID_TOKEN)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the registered scope validators from OAuth server configuration file.
     *
     * @return List of string containing simple names of the registered validator class.
     */
    public [CtArrayTypeReferenceImpl]java.lang.String[] getAllowedScopeValidators() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedScopeValidators == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.validators.OAuth2ScopeValidator> oAuth2ScopeValidators = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOAuth2ScopeValidators();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> validators = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.validators.OAuth2ScopeValidator validator : [CtVariableReadImpl]oAuth2ScopeValidators) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]validators.add([CtInvocationImpl][CtVariableReadImpl]validator.getValidatorName());
            }
            [CtAssignmentImpl][CtFieldWriteImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedScopeValidators = [CtInvocationImpl][CtVariableReadImpl]validators.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]validators.size()]);
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.allowedScopeValidators;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the registered oauth token types from OAuth server configuration file.
     *
     * @return List of supported oauth token types
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSupportedTokenTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedTokenTypes();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the default token type.
     */
    public [CtTypeReferenceImpl]java.lang.String getDefaultTokenType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.DEFAULT_TOKEN_TYPE;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the renew refresh token property value from identity.xml file.
     *
     * @return renew refresh token property value
     */
    public [CtTypeReferenceImpl]boolean isRefreshTokenRenewalEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().isRefreshTokenRenewalEnabled();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return true if PKCE is supported by the database, false if not
     */
    public [CtTypeReferenceImpl]boolean isPKCESupportEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isPKCESupportEnabled();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get supported token bindings meta data.
     *
     * @return list of TokenBindingMetaDataDTOs.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.TokenBindingMetaDataDTO> getSupportedTokenBindingsMetaData() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getTokenBindingMetaDataDTOs();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthTokenExpiryTimeDTO getTokenExpiryTimes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthTokenExpiryTimeDTO tokenExpiryTime = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthTokenExpiryTimeDTO();
        [CtInvocationImpl][CtVariableReadImpl]tokenExpiryTime.setUserAccessTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getUserAccessTokenValidityPeriodInSeconds());
        [CtInvocationImpl][CtVariableReadImpl]tokenExpiryTime.setApplicationAccessTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getApplicationAccessTokenValidityPeriodInSeconds());
        [CtInvocationImpl][CtVariableReadImpl]tokenExpiryTime.setRefreshTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getRefreshTokenValidityPeriodInSeconds());
        [CtInvocationImpl][CtVariableReadImpl]tokenExpiryTime.setIdTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOpenIDConnectIDTokenExpiryTimeInSeconds());
        [CtReturnImpl]return [CtVariableReadImpl]tokenExpiryTime;
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser buildAuthenticatedUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantAwareUser, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantDomain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser user = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser();
        [CtInvocationImpl][CtVariableReadImpl]user.setUserName([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.user.core.util.UserCoreUtil.removeDomainFromName([CtVariableReadImpl]tenantAwareUser));
        [CtInvocationImpl][CtVariableReadImpl]user.setTenantDomain([CtVariableReadImpl]tenantDomain);
        [CtInvocationImpl][CtVariableReadImpl]user.setUserStoreDomain([CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.core.util.IdentityUtil.extractDomainFromName([CtVariableReadImpl]tenantAwareUser));
        [CtReturnImpl]return [CtVariableReadImpl]user;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void validateTokenExpiryConfigurations([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO oAuthConsumerAppDTO) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getUserAccessTokenExpiryTime() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.setUserAccessTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getUserAccessTokenValidityPeriodInSeconds());
            [CtInvocationImpl]logOnInvalidConfig([CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationName(), [CtLiteralImpl]"user access token", [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getUserAccessTokenExpiryTime());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationAccessTokenExpiryTime() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.setApplicationAccessTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getApplicationAccessTokenValidityPeriodInSeconds());
            [CtInvocationImpl]logOnInvalidConfig([CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationName(), [CtLiteralImpl]"application access token", [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationAccessTokenExpiryTime());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getRefreshTokenExpiryTime() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.setRefreshTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getRefreshTokenValidityPeriodInSeconds());
            [CtInvocationImpl]logOnInvalidConfig([CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationName(), [CtLiteralImpl]"refresh token", [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getRefreshTokenExpiryTime());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getIdTokenExpiryTime() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.setIdTokenExpiryTime([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getOpenIDConnectIDTokenExpiryTimeInSeconds());
            [CtInvocationImpl]logOnInvalidConfig([CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getApplicationName(), [CtLiteralImpl]"id token", [CtInvocationImpl][CtVariableReadImpl]oAuthConsumerAppDTO.getIdTokenExpiryTime());
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void logOnInvalidConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenType, [CtParameterImpl][CtTypeReferenceImpl]long defaultValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid expiry time value '0' set for " + [CtVariableReadImpl]tokenType) + [CtLiteralImpl]" in ServiceProvider: ") + [CtVariableReadImpl]appName) + [CtLiteralImpl]". ") + [CtLiteralImpl]"Defaulting to expiry value: ") + [CtVariableReadImpl]defaultValue) + [CtLiteralImpl]" seconds.");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the scope validators registered by the user and filter the allowed ones.
     *
     * @param application
     * 		Application user have registered.
     * @return List of scope validators.
     * @throws IdentityOAuthAdminException
     * 		Identity OAuthAdmin exception.
     */
    [CtArrayTypeReferenceImpl]java.lang.String[] filterScopeValidators([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> scopeValidators = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl]getAllowedScopeValidators()));
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] requestedScopeValidators = [CtInvocationImpl][CtVariableReadImpl]application.getScopeValidators();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]requestedScopeValidators == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]requestedScopeValidators = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0];
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requestedScopeValidator : [CtVariableReadImpl]requestedScopeValidators) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]scopeValidators.contains([CtVariableReadImpl]requestedScopeValidator)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"'%s' scope validator is not allowed.", [CtVariableReadImpl]requestedScopeValidator);
                [CtThrowImpl]throw [CtInvocationImpl]handleClientError([CtTypeAccessImpl]org.wso2.carbon.identity.oauth.Error.INVALID_REQUEST, [CtVariableReadImpl]msg);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]requestedScopeValidators;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get supported algorithms from OAuthServerConfiguration and construct an OAuthIDTokenAlgorithmDTO object.
     *
     * @return Constructed OAuthIDTokenAlgorithmDTO object with supported algorithms.
     */
    public [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthIDTokenAlgorithmDTO getSupportedIDTokenAlgorithms() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthIDTokenAlgorithmDTO oAuthIDTokenAlgorithmDTO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthIDTokenAlgorithmDTO();
        [CtInvocationImpl][CtVariableReadImpl]oAuthIDTokenAlgorithmDTO.setDefaultIdTokenEncryptionAlgorithm([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getDefaultIdTokenEncryptionAlgorithm());
        [CtInvocationImpl][CtVariableReadImpl]oAuthIDTokenAlgorithmDTO.setDefaultIdTokenEncryptionMethod([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getDefaultIdTokenEncryptionMethod());
        [CtInvocationImpl][CtVariableReadImpl]oAuthIDTokenAlgorithmDTO.setSupportedIdTokenEncryptionAlgorithms([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedIdTokenEncryptionAlgorithm());
        [CtInvocationImpl][CtVariableReadImpl]oAuthIDTokenAlgorithmDTO.setSupportedIdTokenEncryptionMethods([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration.getInstance().getSupportedIdTokenEncryptionMethods());
        [CtReturnImpl]return [CtVariableReadImpl]oAuthIDTokenAlgorithmDTO;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether hashing oauth keys (consumer secret, access token, refresh token and authorization code)
     * configuration is disabled or not in identity.xml file.
     *
     * @return Whether hash feature is disabled or not.
     */
    public [CtTypeReferenceImpl]boolean isHashDisabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth2.util.OAuth2Util.isHashDisabled();
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser getAppOwner([CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dto.OAuthConsumerAppDTO application, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser defaultAppOwner) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.IdentityOAuthAdminException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// We first set the logged in user as the owner.
        [CtTypeReferenceImpl]org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser appOwner = [CtVariableReadImpl]defaultAppOwner;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String applicationOwnerInRequest = [CtInvocationImpl][CtVariableReadImpl]application.getUsername();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]applicationOwnerInRequest)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenantAwareAppOwnerInRequest = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.utils.multitenancy.MultitenantUtils.getTenantAwareUsername([CtVariableReadImpl]applicationOwnerInRequest);
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getUserRealm().getUserStoreManager().isExistingUser([CtVariableReadImpl]tenantAwareAppOwnerInRequest)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// Since the app owner sent in OAuthConsumerAppDTO is a valid one we set the appOwner to be
                    [CtCommentImpl]// the one sent in the OAuthConsumerAppDTO.
                    [CtTypeReferenceImpl]java.lang.String tenantDomain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.context.CarbonContext.getThreadLocalCarbonContext().getTenantDomain();
                    [CtAssignmentImpl][CtVariableWriteImpl]appOwner = [CtInvocationImpl]buildAuthenticatedUser([CtVariableReadImpl]tenantAwareAppOwnerInRequest, [CtVariableReadImpl]tenantDomain);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"OAuth application owner user name " + [CtVariableReadImpl]applicationOwnerInRequest) + [CtLiteralImpl]" does not exist in the user store. Using user: ") + [CtInvocationImpl][CtVariableReadImpl]defaultAppOwner.toFullQualifiedUsername()) + [CtLiteralImpl]" as app owner.");
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.user.api.UserStoreException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl]org.wso2.carbon.identity.oauth.OAuthUtil.handleError([CtBinaryOperatorImpl][CtLiteralImpl]"Error while retrieving the user store manager for user: " + [CtVariableReadImpl]applicationOwnerInRequest, [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]appOwner;
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.OAuth2Service getOAuth2Service() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder.getInstance().getOauth2Service();
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO getOAuthApp([CtParameterImpl][CtTypeReferenceImpl]java.lang.String consumerKey) throws [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException, [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDO oauthApp = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().getValueFromCache([CtVariableReadImpl]consumerKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthApp != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"OAuth app with consumerKey: " + [CtVariableReadImpl]consumerKey) + [CtLiteralImpl]" retrieved from AppInfoCache.");
            }
            [CtReturnImpl]return [CtVariableReadImpl]oauthApp;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO dao = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.carbon.identity.oauth.dao.OAuthAppDAO();
        [CtAssignmentImpl][CtVariableWriteImpl]oauthApp = [CtInvocationImpl][CtVariableReadImpl]dao.getAppInformation([CtVariableReadImpl]consumerKey);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oauthApp != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.wso2.carbon.identity.oauth.OAuthAdminServiceImpl.LOG.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"OAuth app with consumerKey: " + [CtVariableReadImpl]consumerKey) + [CtLiteralImpl]" retrieved from database.");
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.wso2.carbon.identity.oauth.cache.AppInfoCache.getInstance().addToCache([CtVariableReadImpl]consumerKey, [CtVariableReadImpl]oauthApp);
        }
        [CtReturnImpl]return [CtVariableReadImpl]oauthApp;
    }
}
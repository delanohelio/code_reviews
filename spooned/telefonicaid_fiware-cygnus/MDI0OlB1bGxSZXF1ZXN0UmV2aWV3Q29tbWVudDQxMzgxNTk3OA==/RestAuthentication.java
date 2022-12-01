[CompilationUnitImpl][CtPackageDeclarationImpl]package com.telefonica.iot.cygnus.backends.arcgis.restutils;
[CtUnresolvedImport]import com.telefonica.iot.cygnus.backends.arcgis.model.UserCredential;
[CtImportImpl]import java.time.LocalDateTime;
[CtUnresolvedImport]import com.google.gson.JsonObject;
[CtUnresolvedImport]import com.telefonica.iot.cygnus.backends.arcgis.model.Credential;
[CtUnresolvedImport]import com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException;
[CtUnresolvedImport]import com.telefonica.iot.cygnus.backends.arcgis.http.HttpResponse;
[CtImportImpl]import java.time.Instant;
[CtImportImpl]import java.time.ZoneId;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import com.google.gson.JsonParser;
[CtUnresolvedImport]import com.telefonica.iot.cygnus.backends.arcgis.restutils.RestApi;
[CtUnresolvedImport]import com.google.gson.JsonElement;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author dmartinez
 */
public class RestAuthentication extends [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestApi {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String REQUEST_FORMAT_PARAMETER = [CtLiteralImpl]"pjson";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String APP_RESPONSE_EXPIRES_TAG = [CtLiteralImpl]"expires_in";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ONLINE_RESPONSE_EXPIRES_TAG = [CtLiteralImpl]"expires";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ONLINE_RESPONSE_TOKEN_TAG = [CtLiteralImpl]"token";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String APP_RESPONSE_TOKEN_TAG = [CtLiteralImpl]"access_token";

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts token from Json response.
     *
     * @param tokenJson
     * @return  * @throws ArcGisException
     */
    public static [CtTypeReferenceImpl]java.lang.String tokenFromJson([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenJson, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenTag) throws [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String result = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]tokenTag)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tokenTag = [CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_TOKEN_TAG;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonParser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parser.parse([CtVariableReadImpl]tokenJson).getAsJsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonElement node = [CtInvocationImpl][CtVariableReadImpl]json.get([CtVariableReadImpl]tokenTag);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]node.getAsString();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonElement expirationElement = [CtInvocationImpl][CtVariableReadImpl]json.get([CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_EXPIRES_TAG);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]expirationElement == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]expirationElement = [CtInvocationImpl][CtVariableReadImpl]json.get([CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.APP_RESPONSE_EXPIRES_TAG);
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Expiration: " + [CtInvocationImpl][CtVariableReadImpl]expirationElement.getAsLong());
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long expiration = [CtInvocationImpl][CtVariableReadImpl]expirationElement.getAsLong();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.Instant expirationInstant = [CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.ofEpochMilli([CtVariableReadImpl]expiration);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]expirationInstant.isBefore([CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now())) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// token is expired.
                    [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Token expired at " + [CtVariableReadImpl]expirationInstant);
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorDesc = [CtLiteralImpl]"Invalid token response format.";
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]json.get([CtLiteralImpl]"error") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorDesc = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]json.get([CtLiteralImpl]"error").toString();
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException([CtVariableReadImpl]errorDesc);
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param tokenJson
     * @param expirationTag
     * @return  * @throws ArcgisException
     */
    public static [CtTypeReferenceImpl]java.time.LocalDateTime expirationFromJson([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tokenJson, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String expirationTag) throws [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDateTime result = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]expirationTag)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]expirationTag = [CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_EXPIRES_TAG;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonParser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parser.parse([CtVariableReadImpl]tokenJson).getAsJsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonElement node = [CtInvocationImpl][CtVariableReadImpl]json.get([CtVariableReadImpl]expirationTag);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonElement expirationElement = [CtInvocationImpl][CtVariableReadImpl]json.get([CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_EXPIRES_TAG);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]expirationElement == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]expirationElement = [CtInvocationImpl][CtVariableReadImpl]json.get([CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.APP_RESPONSE_EXPIRES_TAG);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]long expiration = [CtInvocationImpl][CtVariableReadImpl]expirationElement.getAsLong();
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDateTime.ofInstant([CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.ofEpochMilli([CtVariableReadImpl]expiration), [CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault());
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorDesc = [CtLiteralImpl]"Invalid token response format.";
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]json.get([CtLiteralImpl]"error") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorDesc = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]json.get([CtLiteralImpl]"error").toString();
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException([CtVariableReadImpl]errorDesc);
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Request a token for a non OAuth token authentication.
     *
     * @param cred
     * @param tokenGenUrl
     * @param referer
     * @param expirationMins
     * @return token
     * @throws ArcGisException
     */
    public static [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.Credential createUserToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String password, [CtParameterImpl][CtTypeReferenceImpl]java.net.URL tokenGenUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String referer, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer expirationMins) throws [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tokenJSON = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> bodyParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
            [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"username", [CtVariableReadImpl]user);
            [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"password", [CtVariableReadImpl]password);
            [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"referer", [CtVariableReadImpl]referer);
            [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"username", [CtVariableReadImpl]user);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]expirationMins != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"expiration", [CtInvocationImpl][CtVariableReadImpl]expirationMins.toString());
            }
            [CtInvocationImpl][CtVariableReadImpl]bodyParams.put([CtLiteralImpl]"f", [CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.REQUEST_FORMAT_PARAMETER);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.http.HttpResponse response = [CtInvocationImpl]httpPost([CtInvocationImpl][CtVariableReadImpl]tokenGenUrl.toString(), [CtVariableReadImpl]bodyParams);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]response.getResponseCode() == [CtLiteralImpl]200) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]tokenJSON = [CtInvocationImpl][CtVariableReadImpl]response.getBody();
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"    tokenJSON: " + [CtVariableReadImpl]tokenJSON);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String token = [CtInvocationImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.tokenFromJson([CtVariableReadImpl]tokenJSON, [CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_TOKEN_TAG);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDateTime expiration = [CtInvocationImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.expirationFromJson([CtVariableReadImpl]tokenJSON, [CtFieldReadImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.ONLINE_RESPONSE_EXPIRES_TAG);
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Token recived from service: " + [CtVariableReadImpl]token);
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.UserCredential([CtVariableReadImpl]user, [CtVariableReadImpl]password, [CtVariableReadImpl]token, [CtVariableReadImpl]expiration);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException([CtInvocationImpl][CtVariableReadImpl]response.toString());
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException([CtBinaryOperatorImpl][CtLiteralImpl]"createUserToken, Unexpected Exception " + [CtInvocationImpl][CtVariableReadImpl]e.toString());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Request a token for a non OAuth token authentication.
     *
     * @param cred
     * @param tokenGenUrl
     * @param referer
     * @return token
     * @throws ArcGisException
     */
    public static [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.Credential createUserToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String password, [CtParameterImpl][CtTypeReferenceImpl]java.net.URL tokenGenUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String referer) throws [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.createUserToken([CtVariableReadImpl]user, [CtVariableReadImpl]password, [CtVariableReadImpl]tokenGenUrl, [CtVariableReadImpl]referer, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Crea el token dentro de un objeto credential.
     *
     * @param credential
     * @param tokenGenUrl
     * @param referer
     * @param expirationMins
     * @return  * @throws ArcgisException
     */
    public static [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.Credential createToken([CtParameterImpl][CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.Credential credential, [CtParameterImpl][CtTypeReferenceImpl]java.net.URL tokenGenUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String referer) throws [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]credential instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.UserCredential) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.UserCredential userCredential = [CtVariableReadImpl](([CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.model.UserCredential) (credential));
            [CtAssignmentImpl][CtVariableWriteImpl]credential = [CtInvocationImpl]com.telefonica.iot.cygnus.backends.arcgis.restutils.RestAuthentication.createUserToken([CtInvocationImpl][CtVariableReadImpl]userCredential.getUser(), [CtInvocationImpl][CtVariableReadImpl]userCredential.getPassword(), [CtVariableReadImpl]tokenGenUrl, [CtVariableReadImpl]referer);
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.telefonica.iot.cygnus.backends.arcgis.exceptions.ArcgisException([CtLiteralImpl]"Invalid Credential type.");
        }
        [CtReturnImpl]return [CtVariableReadImpl]credential;
    }
}
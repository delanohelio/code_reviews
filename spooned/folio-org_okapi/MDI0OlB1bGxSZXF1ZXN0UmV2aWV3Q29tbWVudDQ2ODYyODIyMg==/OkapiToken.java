[CompilationUnitImpl][CtPackageDeclarationImpl]package org.folio.okapi.common;
[CtUnresolvedImport]import io.vertx.core.json.DecodeException;
[CtUnresolvedImport]import io.vertx.core.json.JsonObject;
[CtImportImpl]import java.util.Base64;
[CtClassImpl][CtJavaDocImpl]/**
 * The Okapi security token.
 * This class implements some methods to extract some pieces of information out
 * of the security token. In theory the token is private to the auth subsystem,
 * but occasionally a module or even Okapi itself may need to extract the current
 * tenant-id, or some other piece of information.
 */
public class OkapiToken {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String token;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.vertx.core.json.JsonObject payloadWithoutValidation;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Construct from token string.
     * Note that there is no JWT validation taking place.
     */
    public OkapiToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.token = [CtVariableReadImpl]token;
        [CtAssignmentImpl][CtFieldWriteImpl]payloadWithoutValidation = [CtInvocationImpl][CtThisAccessImpl]this.getPayloadWithoutValidation();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.vertx.core.json.JsonObject getPayloadWithoutValidation() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]token == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int idx1 = [CtInvocationImpl][CtFieldReadImpl]token.indexOf([CtLiteralImpl]'.');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx1 == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Missing . separator for token");
        }
        [CtUnaryOperatorImpl][CtVariableWriteImpl]idx1++;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int idx2 = [CtInvocationImpl][CtFieldReadImpl]token.indexOf([CtLiteralImpl]'.', [CtVariableReadImpl]idx1);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx2 == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Missing . separator for token");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String encodedJson = [CtInvocationImpl][CtFieldReadImpl]token.substring([CtVariableReadImpl]idx1, [CtVariableReadImpl]idx2);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String decodedJson = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getDecoder().decode([CtVariableReadImpl]encodedJson));
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.vertx.core.json.JsonObject([CtVariableReadImpl]decodedJson);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.vertx.core.json.DecodeException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getFieldFromToken([CtParameterImpl][CtTypeReferenceImpl]java.lang.String field) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]payloadWithoutValidation == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]payloadWithoutValidation.getString([CtVariableReadImpl]field);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the tenant out from the token.
     * Note there is no JWT validation taking place.
     *
     * @return null if no token, or no tenant there
     */
    public [CtTypeReferenceImpl]java.lang.String getTenant() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getFieldFromToken([CtLiteralImpl]"tenant");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the user name out from the token.
     * Note there is no JWT validation taking place.
     *
     * @return null if no token, or no tenant there
     */
    public [CtTypeReferenceImpl]java.lang.String getUsername() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getFieldFromToken([CtLiteralImpl]"sub");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the user id out from the token.
     * Note there is no JWT validation taking place.
     *
     * @return null if no token, or no tenant there
     */
    public [CtTypeReferenceImpl]java.lang.String getUserId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getFieldFromToken([CtLiteralImpl]"user_id");
    }
}
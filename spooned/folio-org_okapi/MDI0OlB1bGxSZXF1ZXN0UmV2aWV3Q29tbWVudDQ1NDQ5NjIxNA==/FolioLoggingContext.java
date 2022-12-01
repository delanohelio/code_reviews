[CompilationUnitImpl][CtPackageDeclarationImpl]package org.folio.okapi.common.logging;
[CtUnresolvedImport]import io.vertx.core.Vertx;
[CtUnresolvedImport]import org.apache.logging.log4j.core.lookup.StrLookup;
[CtUnresolvedImport]import io.vertx.core.Context;
[CtUnresolvedImport]import org.apache.logging.log4j.core.LogEvent;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.plugins.Plugin;
[CtClassImpl][CtJavaDocImpl]/**
 * This class should be used for storing context variables
 * and use them in logging events.
 */
[CtAnnotationImpl]@org.apache.logging.log4j.core.config.plugins.Plugin(name = [CtLiteralImpl]"FolioLoggingContext", category = [CtFieldReadImpl]org.apache.logging.log4j.core.lookup.StrLookup.CATEGORY)
public class FolioLoggingContext implements [CtTypeReferenceImpl]org.apache.logging.log4j.core.lookup.StrLookup {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EMPTY_VALUE = [CtLiteralImpl]"";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String LOGGING_VAR_PREFIX = [CtLiteralImpl]"folio_";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String TENANT_ID_LOGGING_VAR_NAME = [CtLiteralImpl]"tenantid";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String REQUEST_ID_LOGGING_VAR_NAME = [CtLiteralImpl]"requestid";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String MODULE_ID_LOGGING_VAR_NAME = [CtLiteralImpl]"moduleid";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String USER_ID_LOGGING_VAR_NAME = [CtLiteralImpl]"userid";

    [CtMethodImpl][CtJavaDocImpl]/**
     * Lookup value by key.
     *
     * @param key
     * 		the name of logging variable (e.g. requestId)
     * @return value for key or *empty string* if there is no such key
     */
    public [CtTypeReferenceImpl]java.lang.String lookup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]lookup([CtLiteralImpl]null, [CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Lookup value by key. LogEvent isn't used.
     *
     * @param key
     * 		the name of logging variable (e.g. requestId)
     * @return value for key or *empty string* if there is no such key
     */
    public [CtTypeReferenceImpl]java.lang.String lookup([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.LogEvent event, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.core.Context ctx = [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Vertx.currentContext();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ctx != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String val = [CtInvocationImpl][CtVariableReadImpl]ctx.getLocal([CtBinaryOperatorImpl][CtFieldReadImpl]org.folio.okapi.common.logging.FolioLoggingContext.LOGGING_VAR_PREFIX + [CtVariableReadImpl]key);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]val;
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.folio.okapi.common.logging.FolioLoggingContext.EMPTY_VALUE;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Put value by key to the logging context.   *
     *
     * @param key
     * 		the name of logging variable (e.g. requestId)
     */
    public static [CtTypeReferenceImpl]void put([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.core.Context ctx = [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Vertx.currentContext();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ctx != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ctx.putLocal([CtBinaryOperatorImpl][CtFieldReadImpl]org.folio.okapi.common.logging.FolioLoggingContext.LOGGING_VAR_PREFIX + [CtVariableReadImpl]key, [CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ctx.removeLocal([CtBinaryOperatorImpl][CtFieldReadImpl]org.folio.okapi.common.logging.FolioLoggingContext.LOGGING_VAR_PREFIX + [CtVariableReadImpl]key);
            }
        }
    }
}
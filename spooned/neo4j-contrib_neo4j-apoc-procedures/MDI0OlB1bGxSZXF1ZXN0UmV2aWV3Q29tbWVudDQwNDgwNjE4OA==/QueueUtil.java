[CompilationUnitImpl][CtPackageDeclarationImpl]package apoc.util;
[CtImportImpl]import java.util.concurrent.BlockingQueue;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import org.neo4j.function.ThrowingSupplier;
[CtClassImpl]public class QueueUtil {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]int WAIT = [CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.concurrent.TimeUnit WAIT_UNIT = [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS;

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]void put([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.BlockingQueue<[CtTypeParameterReferenceImpl]T> queue, [CtParameterImpl][CtTypeParameterReferenceImpl]T item, [CtParameterImpl][CtTypeReferenceImpl]long timeoutSeconds) [CtBlockImpl]{
        [CtInvocationImpl]apoc.util.QueueUtil.put([CtVariableReadImpl]queue, [CtVariableReadImpl]item, [CtVariableReadImpl]timeoutSeconds, [CtLiteralImpl]true, [CtLambdaImpl]() -> [CtBlockImpl]{
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * to be used instead of {@link BlockingQueue#put}
     *
     * @param queue
     * @param item
     * @param timeoutSeconds
     * @param failWithExecption
     * 		true if a {@link RuntimeException} should be thrown in case we couldn't add item into the queue within timeframe
     * @param checkDuringOffering
     * 		a callback supposed to throw an exception to terminate
     * @param <T>
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]void put([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.BlockingQueue<[CtTypeParameterReferenceImpl]T> queue, [CtParameterImpl][CtTypeParameterReferenceImpl]T item, [CtParameterImpl][CtTypeReferenceImpl]long timeoutSeconds, [CtParameterImpl][CtTypeReferenceImpl]boolean failWithExecption, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable checkDuringOffering) [CtBlockImpl]{
        [CtInvocationImpl]apoc.util.QueueUtil.withHandlingInterrupted([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Queue offer interrupted before " + [CtVariableReadImpl]timeoutSeconds) + [CtLiteralImpl]" seconds", [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long started = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]started + [CtBinaryOperatorImpl]([CtVariableReadImpl]timeoutSeconds * [CtLiteralImpl]1000)) > [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean success = [CtInvocationImpl][CtVariableReadImpl]queue.offer([CtVariableReadImpl]item, [CtFieldReadImpl][CtFieldReferenceImpl]WAIT, [CtFieldReadImpl][CtFieldReferenceImpl]WAIT_UNIT);
                [CtIfImpl]if ([CtVariableReadImpl]success) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
                [CtInvocationImpl][CtVariableReadImpl]checkDuringOffering.run();
            } 
            [CtIfImpl]if ([CtVariableReadImpl]failWithExecption) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error queuing item before timeout of " + [CtVariableReadImpl]timeoutSeconds) + [CtLiteralImpl]" seconds");
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * to be used instead of {@link BlockingQueue#take}
     *
     * @param queue
     * @param timeoutSeconds
     * @param checkDuringPolling
     * 		a callback supposed to throw an exception to terminate
     * @param <T>
     * @return  */
    public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T take([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.BlockingQueue<[CtTypeParameterReferenceImpl]T> queue, [CtParameterImpl][CtTypeReferenceImpl]long timeoutSeconds, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable checkDuringPolling) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]apoc.util.QueueUtil.withHandlingInterrupted([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Queue poll interrupted before " + [CtVariableReadImpl]timeoutSeconds) + [CtLiteralImpl]" seconds", [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long started = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]started + [CtBinaryOperatorImpl]([CtVariableReadImpl]timeoutSeconds * [CtLiteralImpl]1000)) > [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]apoc.util.T polled = [CtInvocationImpl][CtVariableReadImpl]queue.poll([CtFieldReadImpl][CtFieldReferenceImpl]WAIT, [CtFieldReadImpl][CtFieldReferenceImpl]WAIT_UNIT);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]polled != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]polled;
                }
                [CtInvocationImpl][CtVariableReadImpl]checkDuringPolling.run();
            } 
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error polling, timeout of " + [CtVariableReadImpl]timeoutSeconds) + [CtLiteralImpl]" seconds reached.");
        });
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T withHandlingInterrupted([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtTypeReferenceImpl]org.neo4j.function.ThrowingSupplier<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.InterruptedException> consumer) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]consumer.get();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]msg, [CtVariableReadImpl]e);
        }
    }
}
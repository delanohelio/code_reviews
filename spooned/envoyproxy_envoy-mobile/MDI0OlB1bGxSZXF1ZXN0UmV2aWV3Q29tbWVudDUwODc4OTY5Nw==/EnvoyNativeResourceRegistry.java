[CompilationUnitImpl][CtPackageDeclarationImpl]package io.envoyproxy.envoymobile.engine;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtImportImpl]import java.lang.ref.ReferenceQueue;
[CtImportImpl]import java.lang.ref.PhantomReference;
[CtUnresolvedImport]import io.envoyproxy.envoymobile.engine.EnvoyNativeResourceReleaser;
[CtUnresolvedImport]import io.envoyproxy.envoymobile.engine.EnvoyNativeResourceWrapper;
[CtEnumImpl][CtJavaDocImpl]/**
 * Central class to manage releasing native resources when wrapper objects are flagged as
 * unreachable by the garbage collector.
 */
public enum EnvoyNativeResourceRegistry {

    [CtEnumValueImpl]SINGLETON;
    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.ref.ReferenceQueue refQueue;[CtCommentImpl]// References are automatically enqueued when the gc flags them as unreachable.


    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Set refMaintainer;[CtCommentImpl]// Maintains references in the object graph while we wait for them to


    [CtFieldImpl][CtCommentImpl]// be enqueued.
    private [CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.RefQueueThread refQueueThread;[CtCommentImpl]// Blocks on the reference queue and calls the releaser of queued references.


    [CtClassImpl]private class RefQueueThread extends [CtTypeReferenceImpl]java.lang.Thread {
        [CtMethodImpl]public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.EnvoyPhantomRef ref;
            [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]ref = [CtInvocationImpl](([CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.EnvoyPhantomRef) ([CtFieldReadImpl]refQueue.remove()));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtInvocationImpl][CtVariableReadImpl]ref.releaseResource();
                [CtInvocationImpl][CtFieldReadImpl]refMaintainer.remove([CtVariableReadImpl]ref);
            } 
        }
    }

    [CtClassImpl]private class EnvoyPhantomRef extends [CtTypeReferenceImpl]java.lang.ref.PhantomReference<[CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceWrapper> {
        [CtFieldImpl]private final [CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceReleaser releaser;

        [CtFieldImpl]private final [CtTypeReferenceImpl]long nativeHandle;

        [CtConstructorImpl]EnvoyPhantomRef([CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceWrapper owner, [CtParameterImpl][CtTypeReferenceImpl]long nativeHandle, [CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceReleaser releaser) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]owner, [CtFieldReadImpl]refQueue);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.nativeHandle = [CtVariableReadImpl]nativeHandle;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.releaser = [CtVariableReadImpl]releaser;
            [CtAssignmentImpl][CtFieldWriteImpl]refQueueThread = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.RefQueueThread();
            [CtAssignmentImpl][CtFieldWriteImpl]refMaintainer = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap().newKeySet();
            [CtInvocationImpl][CtFieldReadImpl]refQueueThread.start();
        }

        [CtMethodImpl][CtTypeReferenceImpl]void releaseResource() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]releaser.release([CtFieldReadImpl]nativeHandle);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Register an EnvoyNativeResourceWrapper to schedule cleanup of its native resources when the
     * Java object is flagged for collection by the garbage collector.
     */
    public [CtTypeReferenceImpl]void register([CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceWrapper owner, [CtParameterImpl][CtTypeReferenceImpl]long nativeHandle, [CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceReleaser releaser) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.EnvoyPhantomRef ref = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.EnvoyPhantomRef([CtVariableReadImpl]owner, [CtVariableReadImpl]nativeHandle, [CtVariableReadImpl]releaser);
        [CtInvocationImpl][CtFieldReadImpl]refMaintainer.add([CtVariableReadImpl]ref);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void globalRegister([CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceWrapper owner, [CtParameterImpl][CtTypeReferenceImpl]long nativeHandle, [CtParameterImpl][CtTypeReferenceImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceReleaser releaser) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]io.envoyproxy.envoymobile.engine.EnvoyNativeResourceRegistry.SINGLETON.register([CtVariableReadImpl]owner, [CtVariableReadImpl]nativeHandle, [CtVariableReadImpl]releaser);
    }
}
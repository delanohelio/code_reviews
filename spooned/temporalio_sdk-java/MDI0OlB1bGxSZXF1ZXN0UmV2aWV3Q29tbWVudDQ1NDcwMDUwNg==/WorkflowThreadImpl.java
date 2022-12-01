[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2020 Temporal Technologies, Inc. All Rights Reserved.

 Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 Modifications copyright (C) 2017 Uber Technologies, Inc.

 Licensed under the Apache License, Version 2.0 (the "License"). You may not
 use this file except in compliance with the License. A copy of the License is
 located at

 http://aws.amazon.com/apache2.0

 or in the "license" file accompanying this file. This file is distributed on
 an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 express or implied. See the License for the specific language governing
 permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package io.temporal.internal.sync;
[CtUnresolvedImport]import io.temporal.internal.context.ContextThreadLocal;
[CtUnresolvedImport]import io.temporal.internal.replay.ReplayWorkflowContext;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import org.slf4j.MDC;
[CtUnresolvedImport]import com.google.common.util.concurrent.RateLimiter;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtImportImpl]import java.io.StringWriter;
[CtImportImpl]import java.util.function.Consumer;
[CtUnresolvedImport]import io.temporal.failure.CanceledFailure;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.function.Supplier;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import io.temporal.common.context.ContextPropagator;
[CtUnresolvedImport]import io.temporal.internal.metrics.MetricsType;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import io.temporal.workflow.Promise;
[CtImportImpl]import java.util.concurrent.RejectedExecutionException;
[CtUnresolvedImport]import io.temporal.internal.logging.LoggerTag;
[CtImportImpl]import java.util.concurrent.ThreadPoolExecutor;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtUnresolvedImport]import io.temporal.internal.replay.WorkflowExecutorCache;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.concurrent.Future;
[CtClassImpl]class WorkflowThreadImpl implements [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThread {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.common.util.concurrent.RateLimiter metricsRateLimiter = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.util.concurrent.RateLimiter.create([CtLiteralImpl]1);

    [CtClassImpl][CtJavaDocImpl]/**
     * Runnable passed to the thread that wraps a runnable passed to the WorkflowThreadImpl
     * constructor.
     */
    class RunnableWrapper implements [CtTypeReferenceImpl]java.lang.Runnable {
        [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadContext threadContext;

        [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.replay.ReplayWorkflowContext replayWorkflowContext;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String originalName;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl]private [CtTypeReferenceImpl]io.temporal.internal.sync.CancellationScopeImpl cancellationScope;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.temporal.common.context.ContextPropagator> contextPropagators;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> propagatedContexts;

        [CtConstructorImpl]RunnableWrapper([CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadContext threadContext, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.replay.ReplayWorkflowContext replayWorkflowContext, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]boolean detached, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.CancellationScopeImpl parent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.temporal.common.context.ContextPropagator> contextPropagators, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> propagatedContexts) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.threadContext = [CtVariableReadImpl]threadContext;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.replayWorkflowContext = [CtVariableReadImpl]replayWorkflowContext;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtAssignmentImpl][CtFieldWriteImpl]cancellationScope = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.CancellationScopeImpl([CtVariableReadImpl]detached, [CtVariableReadImpl]runnable, [CtVariableReadImpl]parent);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getStatus() != [CtFieldReadImpl]Status.CREATED) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"threadContext not in CREATED state");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.contextPropagators = [CtVariableReadImpl]contextPropagators;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.propagatedContexts = [CtVariableReadImpl]propagatedContexts;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]thread = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread();
            [CtAssignmentImpl][CtFieldWriteImpl]originalName = [CtInvocationImpl][CtFieldReadImpl]thread.getName();
            [CtInvocationImpl][CtFieldReadImpl]thread.setName([CtFieldReadImpl]name);
            [CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.sync.DeterministicRunnerImpl.setCurrentThreadInternal([CtThisAccessImpl]io.temporal.internal.sync.WorkflowThreadImpl.this);
            [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtTypeAccessImpl]LoggerTag.WORKFLOW_ID, [CtInvocationImpl][CtFieldReadImpl]replayWorkflowContext.getWorkflowId());
            [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtTypeAccessImpl]LoggerTag.WORKFLOW_TYPE, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]replayWorkflowContext.getWorkflowType().getName());
            [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtTypeAccessImpl]LoggerTag.RUN_ID, [CtInvocationImpl][CtFieldReadImpl]replayWorkflowContext.getRunId());
            [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtTypeAccessImpl]LoggerTag.TASK_QUEUE, [CtInvocationImpl][CtFieldReadImpl]replayWorkflowContext.getTaskQueue());
            [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.put([CtTypeAccessImpl]LoggerTag.NAMESPACE, [CtInvocationImpl][CtFieldReadImpl]replayWorkflowContext.getNamespace());
            [CtInvocationImpl][CtCommentImpl]// Repopulate the context(s)
            [CtTypeAccessImpl]io.temporal.internal.context.ContextThreadLocal.setContextPropagators([CtFieldReadImpl][CtThisAccessImpl]this.contextPropagators);
            [CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.context.ContextThreadLocal.propagateContextToCurrentThread([CtFieldReadImpl][CtThisAccessImpl]this.propagatedContexts);
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// initialYield blocks thread until the first runUntilBlocked is called.
                [CtCommentImpl]// Otherwise r starts executing without control of the sync.
                [CtFieldReadImpl]threadContext.initialYield();
                [CtInvocationImpl][CtFieldReadImpl]cancellationScope.run();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.temporal.internal.sync.DestroyWorkflowThreadError e) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]threadContext.isDestroyRequested()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]threadContext.setUnhandledException([CtVariableReadImpl]e);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Error e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]threadContext.setUnhandledException([CtVariableReadImpl]e);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.temporal.failure.CanceledFailure e) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isCancelRequested()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]threadContext.setUnhandledException([CtVariableReadImpl]e);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.sync.WorkflowThreadImpl.log.isDebugEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.sync.WorkflowThreadImpl.log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Workflow thread \"%s\" run cancelled", [CtFieldReadImpl]name));
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]threadContext.setUnhandledException([CtVariableReadImpl]e);
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.sync.DeterministicRunnerImpl.setCurrentThreadInternal([CtLiteralImpl]null);
                [CtInvocationImpl][CtFieldReadImpl]threadContext.setStatus([CtTypeAccessImpl]Status.DONE);
                [CtInvocationImpl][CtFieldReadImpl]thread.setName([CtFieldReadImpl]originalName);
                [CtAssignmentImpl][CtFieldWriteImpl]thread = [CtLiteralImpl]null;
                [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.MDC.clear();
            }
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl][CtArrayTypeReferenceImpl]java.lang.StackTraceElement[] getStackTrace() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]thread != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]thread.getStackTrace();
            }
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.StackTraceElement[[CtLiteralImpl]0];
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]thread != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]thread.setName([CtVariableReadImpl]name);
            }
        }
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.temporal.internal.sync.WorkflowThreadImpl.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ExecutorService threadPool;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadContext context;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.replay.WorkflowExecutorCache cache;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.sync.DeterministicRunnerImpl runner;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadImpl.RunnableWrapper task;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int priority;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Thread thread;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.Future<[CtWildcardReferenceImpl]?> taskFuture;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadLocalInternal<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.lang.Object> threadLocalMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * If not 0 then thread is blocked on a sleep (or on an operation with a timeout). The value is
     * the time in milliseconds (as in currentTimeMillis()) when thread will continue. Note that
     * thread still has to be called for evaluation as other threads might interrupt the blocking
     * call.
     */
    private [CtTypeReferenceImpl]long blockedUntil;

    [CtConstructorImpl]WorkflowThreadImpl([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ExecutorService threadPool, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.DeterministicRunnerImpl runner, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]int priority, [CtParameterImpl][CtTypeReferenceImpl]boolean detached, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.CancellationScopeImpl parentCancellationScope, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.replay.WorkflowExecutorCache cache, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.temporal.common.context.ContextPropagator> contextPropagators, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> propagatedContexts) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.threadPool = [CtVariableReadImpl]threadPool;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.runner = [CtVariableReadImpl]runner;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadContext([CtInvocationImpl][CtVariableReadImpl]runner.getLock());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cache = [CtVariableReadImpl]cache;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.priority = [CtVariableReadImpl]priority;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]name = [CtBinaryOperatorImpl][CtLiteralImpl]"workflow-" + [CtInvocationImpl][CtSuperAccessImpl]super.hashCode();
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.task = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadImpl.RunnableWrapper([CtFieldReadImpl]context, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]runner.getWorkflowContext().getContext(), [CtVariableReadImpl]name, [CtVariableReadImpl]detached, [CtVariableReadImpl]parentCancellationScope, [CtVariableReadImpl]runnable, [CtVariableReadImpl]contextPropagators, [CtVariableReadImpl]propagatedContexts);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"not used");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isDetached() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.isDetached();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void cancel() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.cancel();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void cancel([CtParameterImpl][CtTypeReferenceImpl]java.lang.String reason) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.cancel([CtVariableReadImpl]reason);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getCancellationReason() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.getCancellationReason();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isCancelRequested() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.isCancelRequested();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.temporal.workflow.Promise<[CtTypeReferenceImpl]java.lang.String> getCancellationRequest() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]task.cancellationScope.getCancellationRequest();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void start() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getStatus() != [CtFieldReadImpl]Status.CREATED) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalThreadStateException([CtLiteralImpl]"already started");
        }
        [CtInvocationImpl][CtFieldReadImpl]context.setStatus([CtTypeAccessImpl]Status.RUNNING);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.sync.WorkflowThreadImpl.metricsRateLimiter.tryAcquire([CtLiteralImpl]1)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getWorkflowContext().getMetricsScope().gauge([CtTypeAccessImpl]MetricsType.WORKFLOW_ACTIVE_THREAD_COUNT).update([CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]java.util.concurrent.ThreadPoolExecutor) (threadPool)).getActiveCount());
        }
        [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]taskFuture = [CtInvocationImpl][CtFieldReadImpl]threadPool.submit([CtFieldReadImpl]task);
                [CtReturnImpl]return;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.concurrent.RejectedExecutionException e) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getWorkflowContext().getMetricsScope().counter([CtTypeAccessImpl]MetricsType.STICKY_CACHE_THREAD_FORCED_EVICTION).inc([CtLiteralImpl]1);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.sync.SyncWorkflowContext workflowContext = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.runner.getWorkflowContext();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]boolean evicted = [CtInvocationImpl][CtFieldReadImpl]cache.evictAnyNotInProcessing([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowContext.getContext().getRunId(), [CtInvocationImpl][CtVariableReadImpl]workflowContext.getMetricsScope());
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]evicted) [CtBlockImpl]{
                        [CtThrowImpl][CtCommentImpl]// Note here we need to throw error, not exception. Otherwise it will be
                        [CtCommentImpl]// translated to workflow execution exception and instead of failing the
                        [CtCommentImpl]// workflow task we will be failing the workflow.
                        throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowRejectedExecutionError([CtVariableReadImpl]e);
                    }
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowRejectedExecutionError([CtVariableReadImpl]e);
                }
            }
        } 
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isStarted() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getStatus() != [CtFieldReadImpl]Status.CREATED;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadContext getContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]context;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.temporal.internal.sync.DeterministicRunnerImpl getRunner() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]runner;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.temporal.internal.sync.SyncWorkflowContext getWorkflowContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]runner.getWorkflowContext();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]task.setName([CtVariableReadImpl]name);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]task.getName();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]hashCode();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getPriority() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]priority;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getBlockedUntil() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]blockedUntil;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setBlockedUntil([CtParameterImpl][CtTypeReferenceImpl]long blockedUntil) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.blockedUntil = [CtVariableReadImpl]blockedUntil;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return true if coroutine made some progress.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean runUntilBlocked() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]taskFuture == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]start();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]context.runUntilBlocked();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isDone() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]context.isDone();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl][CtTypeReferenceImpl]java.lang.Thread.State getState() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getStatus() == [CtFieldReadImpl]Status.YIELDED) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Thread.State.[CtFieldReferenceImpl]BLOCKED;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]context.getStatus() == [CtFieldReadImpl]Status.DONE) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Thread.State.[CtFieldReferenceImpl]TERMINATED;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Thread.State.[CtFieldReferenceImpl]RUNNABLE;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Throwable getUnhandledException() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]context.getUnhandledException();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Evaluates function in the threadContext of the coroutine without unblocking it. Used to get
     * current coroutine status, like stack trace.
     *
     * @param function
     * 		Parameter is reason for current goroutine blockage.
     */
    public [CtTypeReferenceImpl]void evaluateInCoroutineContext([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]java.lang.String> function) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]context.evaluateInCoroutineContext([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Interrupt coroutine by throwing DestroyWorkflowThreadError from a await method it is blocked on
     * and return underlying Future to be waited on.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.concurrent.Future<[CtWildcardReferenceImpl]?> stopNow() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Cannot call destroy() on itself
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]thread == [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot call destroy on itself: " + [CtInvocationImpl][CtFieldReadImpl]thread.getName());
        }
        [CtInvocationImpl][CtFieldReadImpl]context.destroy();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]context.isDone()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Couldn't destroy the thread. " + [CtLiteralImpl]"The blocked thread stack trace: ") + [CtInvocationImpl]getStackTrace());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]taskFuture == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getCompletedFuture();
        }
        [CtReturnImpl]return [CtFieldReadImpl]taskFuture;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.concurrent.Future<[CtWildcardReferenceImpl]?> getCompletedFuture() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> f = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
        [CtInvocationImpl][CtVariableReadImpl]f.complete([CtLiteralImpl]"done");
        [CtReturnImpl]return [CtVariableReadImpl]f;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addStackTrace([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder result) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]result.append([CtInvocationImpl]getName());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]thread == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"(NEW)");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]": (BLOCKED on ").append([CtInvocationImpl][CtInvocationImpl]getContext().getYieldReason()).append([CtLiteralImpl]")\n");
        [CtLocalVariableImpl][CtCommentImpl]// These numbers might change if implementation changes.
        [CtTypeReferenceImpl]int omitTop = [CtLiteralImpl]5;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int omitBottom = [CtLiteralImpl]7;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]DeterministicRunnerImpl.WORKFLOW_ROOT_THREAD_NAME.equals([CtInvocationImpl]getName())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]omitBottom = [CtLiteralImpl]11;
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.StackTraceElement[] stackTrace = [CtInvocationImpl][CtFieldReadImpl]thread.getStackTrace();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtVariableReadImpl]omitTop; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]stackTrace.length - [CtVariableReadImpl]omitBottom); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StackTraceElement e = [CtArrayReadImpl][CtVariableReadImpl]stackTrace[[CtVariableReadImpl]i];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i == [CtVariableReadImpl]omitTop) && [CtInvocationImpl][CtLiteralImpl]"await".equals([CtInvocationImpl][CtVariableReadImpl]e.getMethodName()))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtInvocationImpl][CtVariableReadImpl]result.append([CtVariableReadImpl]e);
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"\n");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void yield([CtParameterImpl][CtTypeReferenceImpl]java.lang.String reason, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> unblockCondition) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]context.yield([CtVariableReadImpl]reason, [CtVariableReadImpl]unblockCondition);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean yield([CtParameterImpl][CtTypeReferenceImpl]long timeoutMillis, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String reason, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> unblockCondition) throws [CtTypeReferenceImpl]io.temporal.internal.sync.DestroyWorkflowThreadError [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]timeoutMillis == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]unblockCondition.get();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long blockedUntil = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.sync.WorkflowInternal.currentTimeMillis() + [CtVariableReadImpl]timeoutMillis;
        [CtInvocationImpl]setBlockedUntil([CtVariableReadImpl]blockedUntil);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadImpl.YieldWithTimeoutCondition condition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadImpl.YieldWithTimeoutCondition([CtVariableReadImpl]unblockCondition, [CtVariableReadImpl]blockedUntil);
        [CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.sync.WorkflowThread.await([CtVariableReadImpl]reason, [CtVariableReadImpl]condition);
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]condition.isTimedOut();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R> [CtTypeReferenceImpl]void exitThread([CtParameterImpl][CtTypeParameterReferenceImpl]R value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]runner.exit([CtVariableReadImpl]value);
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.sync.DestroyWorkflowThreadError([CtLiteralImpl]"exit");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]void setThreadLocal([CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadLocalInternal<[CtTypeParameterReferenceImpl]T> key, [CtParameterImpl][CtTypeParameterReferenceImpl]T value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]threadLocalMap.put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> getThreadLocal([CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.sync.WorkflowThreadLocalInternal<[CtTypeParameterReferenceImpl]T> key) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]threadLocalMap.containsKey([CtVariableReadImpl]key)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtFieldReadImpl]threadLocalMap.get([CtVariableReadImpl]key))));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return stack trace of the coroutine thread
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getStackTrace() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.StackTraceElement[] st = [CtInvocationImpl][CtFieldReadImpl]task.getStackTrace();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StackTraceElement se : [CtVariableReadImpl]st) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]pw.println([CtBinaryOperatorImpl][CtLiteralImpl]"\tat " + [CtVariableReadImpl]se);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sw.toString();
    }

    [CtClassImpl]static class YieldWithTimeoutCondition implements [CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> unblockCondition;

        [CtFieldImpl]private final [CtTypeReferenceImpl]long blockedUntil;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean timedOut;

        [CtConstructorImpl]YieldWithTimeoutCondition([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> unblockCondition, [CtParameterImpl][CtTypeReferenceImpl]long blockedUntil) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.unblockCondition = [CtVariableReadImpl]unblockCondition;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.blockedUntil = [CtVariableReadImpl]blockedUntil;
        }

        [CtMethodImpl][CtTypeReferenceImpl]boolean isTimedOut() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]timedOut;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return true if condition matched or timed out
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.Boolean get() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl]unblockCondition.get();
            [CtIfImpl]if ([CtVariableReadImpl]result) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]long currentTimeMillis = [CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.sync.WorkflowInternal.currentTimeMillis();
            [CtAssignmentImpl][CtFieldWriteImpl]timedOut = [CtBinaryOperatorImpl][CtVariableReadImpl]currentTimeMillis >= [CtFieldReadImpl]blockedUntil;
            [CtReturnImpl]return [CtFieldReadImpl]timedOut;
        }
    }
}
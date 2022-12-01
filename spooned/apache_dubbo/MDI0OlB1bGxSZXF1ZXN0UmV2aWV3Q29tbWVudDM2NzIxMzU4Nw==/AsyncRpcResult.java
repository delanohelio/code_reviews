[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.dubbo.rpc;
[CtUnresolvedImport]import org.apache.dubbo.common.logger.LoggerFactory;
[CtUnresolvedImport]import org.apache.dubbo.rpc.model.ApplicationModel;
[CtUnresolvedImport]import org.apache.dubbo.common.logger.Logger;
[CtUnresolvedImport]import org.apache.dubbo.rpc.model.ConsumerModel;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import org.apache.dubbo.rpc.model.MethodDescriptor;
[CtImportImpl]import java.util.concurrent.ExecutionException;
[CtImportImpl]import java.util.concurrent.Executor;
[CtUnresolvedImport]import org.apache.dubbo.common.utils.ReflectUtils;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.function.BiConsumer;
[CtUnresolvedImport]import org.apache.dubbo.common.threadpool.ThreadlessExecutor;
[CtImportImpl]import java.util.concurrent.TimeoutException;
[CtClassImpl][CtJavaDocImpl]/**
 * This class represents an unfinished RPC call, it will hold some context information for this call, for example RpcContext and Invocation,
 * so that when the call finishes and the result returns, it can guarantee all the contexts being recovered as the same as when the call was made
 * before any callback is invoked.
 * <p>
 * TODO if it's reasonable or even right to keep a reference to Invocation?
 * <p>
 * As {@link Result} implements CompletionStage, {@link AsyncRpcResult} allows you to easily build a async filter chain whose status will be
 * driven entirely by the state of the underlying RPC call.
 * <p>
 * AsyncRpcResult does not contain any concrete value (except the underlying value bring by CompletableFuture), consider it as a status transfer node.
 * {@link #getValue()} and {@link #getException()} are all inherited from {@link Result} interface, implementing them are mainly
 * for compatibility consideration. Because many legacy {@link Filter} implementation are most possibly to call getValue directly.
 */
public class AsyncRpcResult implements [CtTypeReferenceImpl]org.apache.dubbo.rpc.Result {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.dubbo.common.logger.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.common.logger.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.dubbo.rpc.AsyncRpcResult.class);

    [CtFieldImpl][CtJavaDocImpl]/**
     * RpcContext may already have been changed when callback happens, it happens when the same thread is used to execute another RPC call.
     * So we should keep the reference of current RpcContext instance and restore it before callback being executed.
     */
    private [CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcContext storedContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcContext storedServerContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.Executor executor;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse> responseFuture;

    [CtConstructorImpl]public AsyncRpcResult([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse> future, [CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.responseFuture = [CtVariableReadImpl]future;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.invocation = [CtVariableReadImpl]invocation;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storedContext = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.getContext();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storedServerContext = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.getServerContext();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notice the return type of {@link #getValue} is the actual type of the RPC method, not {@link AppResponse}
     *
     * @return  */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object getValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().getValue();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * CompletableFuture can only be completed once, so try to update the result of one completed CompletableFuture will
     * has no effect. To avoid this problem, we check the complete status of this future before update it's value.
     *
     * But notice that trying to give an uncompleted CompletableFuture a new specified value may face a race condition,
     * because the background thread watching the real result will also change the status of this CompletableFuture.
     * The result is you may lose the value you expected to set.
     *
     * @param value
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]responseFuture.isDone()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]responseFuture.get().setValue([CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse appResponse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse();
                [CtInvocationImpl][CtVariableReadImpl]appResponse.setValue([CtVariableReadImpl]value);
                [CtInvocationImpl][CtFieldReadImpl]responseFuture.complete([CtVariableReadImpl]appResponse);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This should never happen;
            [CtFieldReadImpl]org.apache.dubbo.rpc.AsyncRpcResult.logger.error([CtLiteralImpl]"Got exception when trying to change the value of the underlying result from AsyncRpcResult.", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Throwable getException() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().getException();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setException([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]responseFuture.isDone()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]responseFuture.get().setException([CtVariableReadImpl]t);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse appResponse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse();
                [CtInvocationImpl][CtVariableReadImpl]appResponse.setException([CtVariableReadImpl]t);
                [CtInvocationImpl][CtFieldReadImpl]responseFuture.complete([CtVariableReadImpl]appResponse);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This should never happen;
            [CtFieldReadImpl]org.apache.dubbo.rpc.AsyncRpcResult.logger.error([CtLiteralImpl]"Got exception when trying to change the value of the underlying result from AsyncRpcResult.", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean hasException() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().hasException();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse> getResponseFuture() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]responseFuture;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setResponseFuture([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse> responseFuture) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.responseFuture = [CtVariableReadImpl]responseFuture;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.dubbo.rpc.Result getAppResponse() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]responseFuture.isDone()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]responseFuture.get();
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This should never happen;
            [CtFieldReadImpl]org.apache.dubbo.rpc.AsyncRpcResult.logger.error([CtLiteralImpl]"Got exception when trying to fetch the underlying result from AsyncRpcResult.", [CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.model.ConsumerModel consumerModel = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.model.ApplicationModel.getConsumerModel([CtInvocationImpl][CtFieldReadImpl]invocation.getTargetServiceUniqueName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String methodName = [CtInvocationImpl][CtFieldReadImpl]invocation.getMethodName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String params = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.common.utils.ReflectUtils.getDesc([CtInvocationImpl][CtFieldReadImpl]invocation.getParameterTypes());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.model.MethodDescriptor method = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]consumerModel.getServiceModel().getMethod([CtVariableReadImpl]methodName, [CtVariableReadImpl]params);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse([CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.common.utils.ReflectUtils.defaultReturn([CtInvocationImpl][CtVariableReadImpl]method.getReturnClass()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will always return after a maximum 'timeout' waiting:
     * 1. if value returns before timeout, return normally.
     * 2. if no value returns after timeout, throw TimeoutException.
     *
     * @return  * @throws InterruptedException
     * @throws ExecutionException
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.dubbo.rpc.Result get() throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.util.concurrent.ExecutionException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]executor != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.common.threadpool.ThreadlessExecutor threadlessExecutor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.apache.dubbo.common.threadpool.ThreadlessExecutor) (executor));
            [CtInvocationImpl][CtVariableReadImpl]threadlessExecutor.waitAndDrain();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]responseFuture.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.dubbo.rpc.Result get([CtParameterImpl][CtTypeReferenceImpl]long timeout, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.TimeUnit unit) throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.util.concurrent.ExecutionException, [CtTypeReferenceImpl]java.util.concurrent.TimeoutException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]executor != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.common.threadpool.ThreadlessExecutor threadlessExecutor = [CtFieldReadImpl](([CtTypeReferenceImpl]org.apache.dubbo.common.threadpool.ThreadlessExecutor) (executor));
            [CtInvocationImpl][CtVariableReadImpl]threadlessExecutor.waitAndDrain();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]responseFuture.get([CtVariableReadImpl]timeout, [CtVariableReadImpl]unit);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object recreate() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcInvocation rpcInvocation = [CtFieldReadImpl](([CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcInvocation) (invocation));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]InvokeMode.FUTURE == [CtInvocationImpl][CtVariableReadImpl]rpcInvocation.getInvokeMode()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.getContext().getFuture();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().recreate();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.dubbo.rpc.Result whenCompleteWithContext([CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]org.apache.dubbo.rpc.Result, [CtTypeReferenceImpl]java.lang.Throwable> fn) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.responseFuture = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.responseFuture.whenComplete([CtLambdaImpl]([CtParameterImpl] v,[CtParameterImpl] t) -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]beforeContext.accept([CtVariableReadImpl]v, [CtVariableReadImpl]t);
            [CtInvocationImpl][CtVariableReadImpl]fn.accept([CtVariableReadImpl]v, [CtVariableReadImpl]t);
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]afterContext.accept([CtVariableReadImpl]v, [CtVariableReadImpl]t);
        });
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]U> [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeParameterReferenceImpl]U> thenApply([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]org.apache.dubbo.rpc.Result, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]U> fn) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.responseFuture.thenApply([CtVariableReadImpl]fn);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> getAttachments() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().getAttachments();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setAttachments([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> map) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getAppResponse().setAttachments([CtVariableReadImpl]map);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addAttachments([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> map) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getAppResponse().addAttachments([CtVariableReadImpl]map);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object getAttachment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().getAttachment([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object getAttachment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object defaultValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getAppResponse().getAttachment([CtVariableReadImpl]key, [CtVariableReadImpl]defaultValue);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setAttachment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getAppResponse().setAttachment([CtVariableReadImpl]key, [CtVariableReadImpl]value);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.Executor getExecutor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]executor;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setExecutor([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.Executor executor) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executor = [CtVariableReadImpl]executor;
    }

    [CtFieldImpl][CtJavaDocImpl]/**
     * tmp context to use when the thread switch to Dubbo thread.
     */
    private [CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcContext tmpContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.dubbo.rpc.RpcContext tmpServerContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]org.apache.dubbo.rpc.Result, [CtTypeReferenceImpl]java.lang.Throwable> beforeContext = [CtLambdaImpl]([CtParameterImpl]org.apache.dubbo.rpc.Result appResponse,[CtParameterImpl]java.lang.Throwable t) -> [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]tmpContext = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.getContext();
        [CtAssignmentImpl][CtFieldWriteImpl]tmpServerContext = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.getServerContext();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.restoreContext([CtFieldReadImpl]storedContext);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.restoreServerContext([CtFieldReadImpl]storedServerContext);
    };

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]org.apache.dubbo.rpc.Result, [CtTypeReferenceImpl]java.lang.Throwable> afterContext = [CtLambdaImpl]([CtParameterImpl]org.apache.dubbo.rpc.Result appResponse,[CtParameterImpl]java.lang.Throwable t) -> [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.restoreContext([CtFieldReadImpl]tmpContext);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.dubbo.rpc.RpcContext.restoreServerContext([CtFieldReadImpl]tmpServerContext);
    };

    [CtMethodImpl][CtJavaDocImpl]/**
     * Some utility methods used to quickly generate default AsyncRpcResult instance.
     */
    public static [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult newDefaultAsyncResult([CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse appResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult([CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.completedFuture([CtVariableReadImpl]appResponse), [CtVariableReadImpl]invocation);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult newDefaultAsyncResult([CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.apache.dubbo.rpc.AsyncRpcResult.newDefaultAsyncResult([CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]invocation);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult newDefaultAsyncResult([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.apache.dubbo.rpc.AsyncRpcResult.newDefaultAsyncResult([CtVariableReadImpl]value, [CtLiteralImpl]null, [CtVariableReadImpl]invocation);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult newDefaultAsyncResult([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t, [CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.apache.dubbo.rpc.AsyncRpcResult.newDefaultAsyncResult([CtLiteralImpl]null, [CtVariableReadImpl]t, [CtVariableReadImpl]invocation);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult newDefaultAsyncResult([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t, [CtParameterImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.Invocation invocation) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse> future = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AppResponse();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.setException([CtVariableReadImpl]t);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.setValue([CtVariableReadImpl]value);
        }
        [CtInvocationImpl][CtVariableReadImpl]future.complete([CtVariableReadImpl]result);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dubbo.rpc.AsyncRpcResult([CtVariableReadImpl]future, [CtVariableReadImpl]invocation);
    }
}
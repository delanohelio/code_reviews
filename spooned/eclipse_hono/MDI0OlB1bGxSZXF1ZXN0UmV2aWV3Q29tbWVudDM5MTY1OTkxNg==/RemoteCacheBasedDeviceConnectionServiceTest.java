[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * Copyright (c) 2019, 2020 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package org.eclipse.hono.deviceconnection.infinispan;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import io.vertx.junit5.VertxTestContext;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.any;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.eq;
[CtUnresolvedImport]import org.eclipse.hono.client.ClientErrorException;
[CtUnresolvedImport]import org.eclipse.hono.util.Constants;
[CtUnresolvedImport]import io.vertx.core.Context;
[CtUnresolvedImport]import static org.mockito.Mockito.doAnswer;
[CtImportImpl]import java.net.HttpURLConnection;
[CtUnresolvedImport]import io.vertx.core.eventbus.EventBus;
[CtUnresolvedImport]import org.junit.jupiter.api.extension.ExtendWith;
[CtUnresolvedImport]import io.vertx.core.AsyncResult;
[CtUnresolvedImport]import io.vertx.core.Promise;
[CtUnresolvedImport]import static org.mockito.Mockito.verify;
[CtUnresolvedImport]import io.vertx.core.Vertx;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import io.vertx.junit5.VertxExtension;
[CtUnresolvedImport]import io.vertx.core.Future;
[CtUnresolvedImport]import org.eclipse.hono.deviceconnection.infinispan.client.DeviceConnectionInfo;
[CtUnresolvedImport]import io.opentracing.SpanContext;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.anyString;
[CtUnresolvedImport]import org.infinispan.client.hotrod.RemoteCacheContainer;
[CtUnresolvedImport]import io.opentracing.Span;
[CtUnresolvedImport]import io.vertx.core.Handler;
[CtUnresolvedImport]import io.vertx.core.eventbus.MessageConsumer;
[CtUnresolvedImport]import static org.mockito.Mockito.mock;
[CtClassImpl][CtJavaDocImpl]/**
 * Tests verifying behavior of {@link RemoteCacheBasedDeviceConnectionService}.
 */
[CtAnnotationImpl]@org.junit.jupiter.api.extension.ExtendWith([CtFieldReadImpl]io.vertx.junit5.VertxExtension.class)
public class RemoteCacheBasedDeviceConnectionServiceTest {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.hono.deviceconnection.infinispan.RemoteCacheBasedDeviceConnectionService svc;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.opentracing.Span span;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.hono.deviceconnection.infinispan.client.DeviceConnectionInfo cache;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets up fixture.
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.opentracing.SpanContext spanContext = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.opentracing.SpanContext.class);
        [CtAssignmentImpl][CtFieldWriteImpl]span = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.opentracing.Span.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]span.context()).thenReturn([CtVariableReadImpl]spanContext);
        [CtAssignmentImpl][CtFieldWriteImpl]cache = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.eclipse.hono.deviceconnection.infinispan.client.DeviceConnectionInfo.class);
        [CtAssignmentImpl][CtFieldWriteImpl]svc = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.hono.deviceconnection.infinispan.RemoteCacheBasedDeviceConnectionService([CtFieldReadImpl]cache);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtTypeReferenceImpl]io.vertx.core.Future<[CtTypeReferenceImpl]java.lang.Void> givenAStartedService() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.vertx.core.Vertx vertx = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.vertx.core.Vertx.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doAnswer([CtLambdaImpl]([CtParameterImpl] invocation) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Promise<[CtTypeReferenceImpl]org.infinispan.client.hotrod.RemoteCacheContainer> result = [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Promise.promise();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Handler<[CtTypeReferenceImpl]Future<[CtTypeReferenceImpl]org.infinispan.client.hotrod.RemoteCacheContainer>> blockingCode = [CtInvocationImpl][CtVariableReadImpl]invocation.getArgument([CtLiteralImpl]0);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Handler<[CtTypeReferenceImpl]AsyncResult<[CtTypeReferenceImpl]org.infinispan.client.hotrod.RemoteCacheContainer>> resultHandler = [CtInvocationImpl][CtVariableReadImpl]invocation.getArgument([CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]blockingCode.handle([CtInvocationImpl][CtVariableReadImpl]result.future());
            [CtInvocationImpl][CtVariableReadImpl]resultHandler.handle([CtInvocationImpl][CtVariableReadImpl]result.future());
            [CtReturnImpl]return [CtLiteralImpl]null;
        }).when([CtVariableReadImpl]vertx).executeBlocking([CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.vertx.core.Handler.class), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.vertx.core.Handler.class));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.vertx.core.eventbus.EventBus eventBus = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.vertx.core.eventbus.EventBus.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtVariableReadImpl]eventBus.consumer([CtInvocationImpl]ArgumentMatchers.anyString())).thenReturn([CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.vertx.core.eventbus.MessageConsumer.class));
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtVariableReadImpl]vertx.eventBus()).thenReturn([CtVariableReadImpl]eventBus);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.vertx.core.Context ctx = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]io.vertx.core.Context.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.vertx.core.Promise<[CtTypeReferenceImpl]java.lang.Void> startPromise = [CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Promise.promise();
        [CtInvocationImpl][CtFieldReadImpl]svc.init([CtVariableReadImpl]vertx, [CtVariableReadImpl]ctx);
        [CtInvocationImpl][CtFieldReadImpl]svc.start([CtVariableReadImpl]startPromise);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]startPromise.future();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies that the last known gateway id can be set via the <em>setLastKnownGatewayForDevice</em> operation.
     *
     * @param ctx
     * 		The vert.x context.
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testSetLastKnownGatewayForDevice([CtParameterImpl]final [CtTypeReferenceImpl]io.vertx.junit5.VertxTestContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String deviceId = [CtLiteralImpl]"testDevice";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String gatewayId = [CtLiteralImpl]"testGateway";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]cache.setLastKnownGatewayForDevice([CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.opentracing.SpanContext.class))).thenReturn([CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Future.succeededFuture());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]givenAStartedService().compose([CtLambdaImpl]([CtParameterImpl] ok) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]svc.setLastKnownGatewayForDevice([CtVariableReadImpl]Constants.DEFAULT_TENANT, [CtVariableReadImpl]deviceId, [CtVariableReadImpl]gatewayId, [CtFieldReadImpl][CtFieldReferenceImpl]span)).setHandler([CtInvocationImpl][CtVariableReadImpl]ctx.succeeding([CtLambdaImpl]([CtParameterImpl] result) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ctx.verify([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getStatus()).isEqualTo([CtVariableReadImpl]HttpURLConnection.HTTP_NO_CONTENT);
                [CtInvocationImpl][CtInvocationImpl]verify([CtFieldReadImpl][CtFieldReferenceImpl]cache).setLastKnownGatewayForDevice([CtInvocationImpl]eq([CtVariableReadImpl]Constants.DEFAULT_TENANT), [CtInvocationImpl]eq([CtVariableReadImpl]deviceId), [CtInvocationImpl]eq([CtVariableReadImpl]gatewayId), [CtInvocationImpl]any([CtFieldReadImpl]io.opentracing.SpanContext.class));
            });
            [CtInvocationImpl][CtVariableReadImpl]ctx.completeNow();
        }));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies that the <em>getLastKnownGatewayForDevice</em> operation fails if no such entry is associated
     * with the given device.
     *
     * @param ctx
     * 		The vert.x context.
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testGetLastKnownGatewayForDeviceNotFound([CtParameterImpl]final [CtTypeReferenceImpl]io.vertx.junit5.VertxTestContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String deviceId = [CtLiteralImpl]"testDevice";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]cache.getLastKnownGatewayForDevice([CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.opentracing.SpanContext.class))).thenReturn([CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Future.failedFuture([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.hono.client.ClientErrorException([CtFieldReadImpl][CtTypeAccessImpl]java.net.HttpURLConnection.[CtFieldReferenceImpl]HTTP_NOT_FOUND)));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]givenAStartedService().compose([CtLambdaImpl]([CtParameterImpl] ok) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]svc.getLastKnownGatewayForDevice([CtVariableReadImpl]Constants.DEFAULT_TENANT, [CtVariableReadImpl]deviceId, [CtFieldReadImpl][CtFieldReferenceImpl]span)).setHandler([CtInvocationImpl][CtVariableReadImpl]ctx.succeeding([CtLambdaImpl]([CtParameterImpl] deviceConnectionResult) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ctx.verify([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]deviceConnectionResult.getStatus()).isEqualTo([CtVariableReadImpl]HttpURLConnection.HTTP_NOT_FOUND);
                [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]deviceConnectionResult.getPayload()).isNull();
            });
            [CtInvocationImpl][CtVariableReadImpl]ctx.completeNow();
        }));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies that the last known gateway id can be set via the <em>setCommandHandlingAdapterInstance</em> operation.
     *
     * @param ctx
     * 		The vert.x context.
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testSetCommandHandlingAdapterInstance([CtParameterImpl]final [CtTypeReferenceImpl]io.vertx.junit5.VertxTestContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String deviceId = [CtLiteralImpl]"testDevice";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String adapterInstanceId = [CtLiteralImpl]"adapterInstanceId";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]cache.setCommandHandlingAdapterInstance([CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.anyString(), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.opentracing.SpanContext.class))).thenReturn([CtInvocationImpl][CtTypeAccessImpl]io.vertx.core.Future.succeededFuture());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]givenAStartedService().compose([CtLambdaImpl]([CtParameterImpl] ok) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]svc.setCommandHandlingAdapterInstance([CtVariableReadImpl]Constants.DEFAULT_TENANT, [CtVariableReadImpl]deviceId, [CtVariableReadImpl]adapterInstanceId, [CtFieldReadImpl][CtFieldReferenceImpl]span)).setHandler([CtInvocationImpl][CtVariableReadImpl]ctx.succeeding([CtLambdaImpl]([CtParameterImpl] result) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ctx.verify([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getStatus()).isEqualTo([CtVariableReadImpl]HttpURLConnection.HTTP_NO_CONTENT);
                [CtInvocationImpl][CtInvocationImpl]verify([CtFieldReadImpl][CtFieldReferenceImpl]cache).setCommandHandlingAdapterInstance([CtInvocationImpl]eq([CtVariableReadImpl]Constants.DEFAULT_TENANT), [CtInvocationImpl]eq([CtVariableReadImpl]deviceId), [CtInvocationImpl]eq([CtVariableReadImpl]adapterInstanceId), [CtInvocationImpl]any([CtFieldReadImpl]io.opentracing.SpanContext.class));
            });
            [CtInvocationImpl][CtVariableReadImpl]ctx.completeNow();
        }));
    }
}
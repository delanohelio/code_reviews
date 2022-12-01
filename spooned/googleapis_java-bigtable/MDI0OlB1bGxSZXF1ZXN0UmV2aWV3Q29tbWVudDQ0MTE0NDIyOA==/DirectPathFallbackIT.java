[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.google.cloud.bigtable.data.v2.it;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
[CtUnresolvedImport]import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
[CtImportImpl]import java.net.InetSocketAddress;
[CtUnresolvedImport]import com.google.common.base.Stopwatch;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoopGroup;
[CtUnresolvedImport]import com.google.api.gax.core.FixedCredentialsProvider;
[CtUnresolvedImport]import com.google.auth.oauth2.ComputeEngineCredentials;
[CtImportImpl]import java.lang.reflect.Field;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel;
[CtUnresolvedImport]import org.junit.After;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
[CtUnresolvedImport]import com.google.cloud.bigtable.data.v2.BigtableDataClient;
[CtUnresolvedImport]import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
[CtUnresolvedImport]import org.junit.ClassRule;
[CtImportImpl]import java.util.concurrent.TimeoutException;
[CtUnresolvedImport]import io.grpc.alts.ComputeEngineChannelBuilder;
[CtImportImpl]import java.net.SocketAddress;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import static com.google.common.truth.TruthJUnit.assume;
[CtImportImpl]import java.net.Inet4Address;
[CtUnresolvedImport]import static com.google.common.truth.Truth.assertWithMessage;
[CtUnresolvedImport]import com.google.cloud.bigtable.test_helpers.env.TestEnvRule;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtImportImpl]import java.net.Inet6Address;
[CtUnresolvedImport]import org.junit.runners.JUnit4;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.ChannelDuplexHandler;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
[CtUnresolvedImport]import io.grpc.ManagedChannelBuilder;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.channel.ChannelFactory;
[CtUnresolvedImport]import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;
[CtImportImpl]import java.net.InetAddress;
[CtUnresolvedImport]import com.google.api.core.ApiFunction;
[CtClassImpl][CtJavaDocImpl]/**
 * Test DirectPath fallback behavior by injecting a ChannelHandler into the netty stack that will
 * disrupt IPv6 communications.
 *
 * <p>WARNING: this test can only be run on a GCE VM and will explicitly ignore
 * GOOGLE_APPLICATION_CREDENTIALS and use the service account associated with the VM.
 */
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.JUnit4.class)
public class DirectPathFallbackIT {
    [CtFieldImpl][CtCommentImpl]// A threshold of completed read calls to observe to ascertain IPv6 is working.
    [CtCommentImpl]// This was determined experimentally to account for both gRPC-LB RPCs and Bigtable api RPCs.
    private static final [CtTypeReferenceImpl]int MIN_COMPLETE_READ_CALLS = [CtLiteralImpl]40;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int NUM_RPCS_TO_SEND = [CtLiteralImpl]20;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DP_IPV6_PREFIX = [CtLiteralImpl]"2001:4860:8040";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DP_IPV4_PREFIX = [CtLiteralImpl]"34.126";

    [CtFieldImpl][CtAnnotationImpl]@org.junit.ClassRule
    public static [CtTypeReferenceImpl]com.google.cloud.bigtable.test_helpers.env.TestEnvRule testEnvRule = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigtable.test_helpers.env.TestEnvRule();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean blackholeDpAddr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger numBlocked = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger numDpAddrRead = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger();

    [CtFieldImpl]private [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelFactory<[CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel> channelFactory;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.EventLoopGroup eventLoopGroup;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.bigtable.data.v2.BigtableDataClient instrumentedClient;

    [CtConstructorImpl]public DirectPathFallbackIT() [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// Create a transport channel provider that can intercept ipv6 packets.
        [CtFieldWriteImpl]channelFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.MyChannelFactory();
        [CtAssignmentImpl][CtFieldWriteImpl]eventLoopGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoopGroup();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assume().withMessage([CtLiteralImpl]"DirectPath integration tests can only run against DirectPathEnv").that([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.testEnvRule.env().isDirectPathEnabled()).isTrue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigtable.data.v2.BigtableDataSettings defaultSettings = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.testEnvRule.env().getDataClientSettings();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.gax.grpc.InstantiatingGrpcChannelProvider defaultTransportProvider = [CtInvocationImpl](([CtTypeReferenceImpl]com.google.api.gax.grpc.InstantiatingGrpcChannelProvider) ([CtInvocationImpl][CtVariableReadImpl]defaultSettings.getStubSettings().getTransportChannelProvider()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.gax.grpc.InstantiatingGrpcChannelProvider instrumentedTransportChannelProvider = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]defaultTransportProvider.toBuilder().setAttemptDirectPath([CtLiteralImpl]true).setPoolSize([CtLiteralImpl]1).setChannelConfigurator([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.api.core.ApiFunction<[CtTypeReferenceImpl]io.grpc.ManagedChannelBuilder, [CtTypeReferenceImpl]io.grpc.ManagedChannelBuilder>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]io.grpc.ManagedChannelBuilder apply([CtParameterImpl][CtTypeReferenceImpl]io.grpc.ManagedChannelBuilder builder) [CtBlockImpl]{
                [CtInvocationImpl]injectNettyChannelHandler([CtVariableReadImpl]builder);
                [CtInvocationImpl][CtCommentImpl]// Fail fast when blackhole is active
                [CtVariableReadImpl]builder.keepAliveTime([CtLiteralImpl]1, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS);
                [CtInvocationImpl][CtVariableReadImpl]builder.keepAliveTimeout([CtLiteralImpl]1, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS);
                [CtReturnImpl]return [CtVariableReadImpl]builder;
            }
        }).build();
        [CtLocalVariableImpl][CtCommentImpl]// Inject the instrumented transport provider into a new client
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigtable.data.v2.BigtableDataSettings.Builder settingsBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.testEnvRule.env().getDataClientSettings().toBuilder();
        [CtInvocationImpl][CtCommentImpl]// Forcefully ignore GOOGLE_APPLICATION_CREDENTIALS
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]settingsBuilder.stubSettings().setTransportChannelProvider([CtVariableReadImpl]instrumentedTransportChannelProvider).setCredentialsProvider([CtInvocationImpl][CtTypeAccessImpl]com.google.api.gax.core.FixedCredentialsProvider.create([CtInvocationImpl][CtTypeAccessImpl]com.google.auth.oauth2.ComputeEngineCredentials.create()));
        [CtAssignmentImpl][CtFieldWriteImpl]instrumentedClient = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigtable.data.v2.BigtableDataClient.create([CtInvocationImpl][CtVariableReadImpl]settingsBuilder.build());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void teardown() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]instrumentedClient != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]instrumentedClient.close();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]eventLoopGroup != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]eventLoopGroup.shutdownGracefully();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFallback() throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.util.concurrent.TimeoutException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Precondition: wait for DirectPath to connect
        [CtInvocationImpl][CtInvocationImpl]assertWithMessage([CtLiteralImpl]"Failed to observe RPCs over DirectPath").that([CtInvocationImpl]exerciseDirectPath()).isTrue();
        [CtInvocationImpl][CtCommentImpl]// Enable the blackhole, which will prevent communication with grpclb and thus DirectPath.
        [CtFieldReadImpl]blackholeDpAddr.set([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// Send a request, which should be routed over IPv4 and CFE.
        [CtFieldReadImpl]instrumentedClient.readRow([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.testEnvRule.env().getTableId(), [CtLiteralImpl]"nonexistent-row");
        [CtInvocationImpl][CtCommentImpl]// Verify that the above check was meaningful, by verifying that the blackhole actually dropped
        [CtCommentImpl]// packets.
        [CtInvocationImpl][CtInvocationImpl]assertWithMessage([CtLiteralImpl]"Failed to detect any IPv6 traffic in blackhole").that([CtInvocationImpl][CtFieldReadImpl]numBlocked.get()).isGreaterThan([CtLiteralImpl]0);
        [CtInvocationImpl][CtCommentImpl]// Make sure that the client will start reading from IPv6 again by sending new requests and
        [CtCommentImpl]// checking the injected IPv6 counter has been updated.
        [CtFieldReadImpl]blackholeDpAddr.set([CtLiteralImpl]false);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertWithMessage([CtLiteralImpl]"Failed to upgrade back to DirectPath").that([CtInvocationImpl]exerciseDirectPath()).isTrue();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean exerciseDirectPath() throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.util.concurrent.TimeoutException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Stopwatch stopwatch = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Stopwatch.createStarted();
        [CtInvocationImpl][CtFieldReadImpl]numDpAddrRead.set([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean seenEnough = [CtLiteralImpl]false;
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]seenEnough) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]stopwatch.elapsed([CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MINUTES) < [CtLiteralImpl]2)) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.NUM_RPCS_TO_SEND; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]instrumentedClient.readRow([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.testEnvRule.env().getTableId(), [CtLiteralImpl]"nonexistent-row");
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]100);
            [CtAssignmentImpl][CtVariableWriteImpl]seenEnough = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]numDpAddrRead.get() >= [CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.MIN_COMPLETE_READ_CALLS;
        } 
        [CtReturnImpl]return [CtVariableReadImpl]seenEnough;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is a giant hack to enable testing DirectPath CFE fallback.
     *
     * <p>It unwraps the {@link ComputeEngineChannelBuilder} to inject a NettyChannelHandler to signal
     * IPv6 packet loss.
     */
    private [CtTypeReferenceImpl]void injectNettyChannelHandler([CtParameterImpl][CtTypeReferenceImpl]io.grpc.ManagedChannelBuilder<[CtWildcardReferenceImpl]?> channelBuilder) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Extract the delegate NettyChannelBuilder using reflection
            [CtTypeReferenceImpl]java.lang.reflect.Field delegateField = [CtInvocationImpl][CtFieldReadImpl]io.grpc.alts.ComputeEngineChannelBuilder.class.getDeclaredField([CtLiteralImpl]"delegate");
            [CtInvocationImpl][CtVariableReadImpl]delegateField.setAccessible([CtLiteralImpl]true);
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.grpc.alts.ComputeEngineChannelBuilder gceChannelBuilder = [CtVariableReadImpl](([CtTypeReferenceImpl]io.grpc.alts.ComputeEngineChannelBuilder) (channelBuilder));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object delegateChannelBuilder = [CtInvocationImpl][CtVariableReadImpl]delegateField.get([CtVariableReadImpl]gceChannelBuilder);
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder nettyChannelBuilder = [CtVariableReadImpl](([CtTypeReferenceImpl]io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder) (delegateChannelBuilder));
            [CtInvocationImpl][CtVariableReadImpl]nettyChannelBuilder.channelFactory([CtFieldReadImpl]channelFactory);
            [CtInvocationImpl][CtVariableReadImpl]nettyChannelBuilder.eventLoopGroup([CtFieldReadImpl]eventLoopGroup);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NoSuchFieldException | [CtTypeReferenceImpl]java.lang.IllegalAccessException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Failed to inject the netty ChannelHandler", [CtVariableReadImpl]e);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     *
     * @see com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.MyChannelHandler
     */
    private class MyChannelFactory implements [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelFactory<[CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel> {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel newChannel() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel channel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]channel.pipeline().addLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.MyChannelHandler());
            [CtReturnImpl]return [CtVariableReadImpl]channel;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * A netty {@link io.grpc.netty.shaded.io.netty.channel.ChannelHandler} that can be instructed to
     * make IPv6 packets disappear
     */
    private class MyChannelHandler extends [CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelDuplexHandler {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean isDpAddr;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void connect([CtParameterImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.net.SocketAddress remoteAddress, [CtParameterImpl][CtTypeReferenceImpl]java.net.SocketAddress localAddress, [CtParameterImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelPromise promise) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]remoteAddress instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.net.InetSocketAddress) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.InetAddress inetAddress = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.net.InetSocketAddress) (remoteAddress)).getAddress();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String addr = [CtInvocationImpl][CtVariableReadImpl]inetAddress.getHostAddress();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]inetAddress instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.net.Inet6Address) && [CtInvocationImpl][CtVariableReadImpl]addr.startsWith([CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.DP_IPV6_PREFIX)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]inetAddress instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.net.Inet4Address) && [CtInvocationImpl][CtVariableReadImpl]addr.startsWith([CtFieldReadImpl]com.google.cloud.bigtable.data.v2.it.DirectPathFallbackIT.DP_IPV4_PREFIX))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]isDpAddr = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtFieldReadImpl]isDpAddr && [CtInvocationImpl][CtFieldReadImpl]blackholeDpAddr.get())) [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.connect([CtVariableReadImpl]ctx, [CtVariableReadImpl]remoteAddress, [CtVariableReadImpl]localAddress, [CtVariableReadImpl]promise);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Fail the connection fast
                [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]">>>>>>>>>>>>>>>>>>>>>>>>>>> Failing connection");
                [CtInvocationImpl][CtVariableReadImpl]promise.setFailure([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtLiteralImpl]"fake error"));
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void channelRead([CtParameterImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object msg) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean dropCall = [CtBinaryOperatorImpl][CtFieldReadImpl]isDpAddr && [CtInvocationImpl][CtFieldReadImpl]blackholeDpAddr.get();
            [CtIfImpl]if ([CtVariableReadImpl]dropCall) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]">>>>>>>>>>>>>>>>>>>>>>> Dropping call.");
                [CtInvocationImpl][CtCommentImpl]// Don't notify the next handler and increment counter
                [CtFieldReadImpl]numBlocked.incrementAndGet();
                [CtInvocationImpl][CtTypeAccessImpl]io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil.release([CtVariableReadImpl]msg);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.channelRead([CtVariableReadImpl]ctx, [CtVariableReadImpl]msg);
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void channelReadComplete([CtParameterImpl][CtTypeReferenceImpl]io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext ctx) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean dropCall = [CtBinaryOperatorImpl][CtFieldReadImpl]isDpAddr && [CtInvocationImpl][CtFieldReadImpl]blackholeDpAddr.get();
            [CtIfImpl]if ([CtVariableReadImpl]dropCall) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Don't notify the next handler and increment counter
                [CtFieldReadImpl]numBlocked.incrementAndGet();
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtFieldReadImpl]isDpAddr) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]numDpAddrRead.incrementAndGet();
                }
                [CtInvocationImpl][CtSuperAccessImpl]super.channelReadComplete([CtVariableReadImpl]ctx);
            }
        }
    }
}
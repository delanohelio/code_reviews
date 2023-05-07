[CompilationUnitImpl][CtJavaDocImpl]/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.storm.messaging.netty;
[CtImportImpl]import java.util.concurrent.ThreadFactory;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.buffer.PooledByteBufAllocator;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.net.InetSocketAddress;
[CtUnresolvedImport]import org.apache.storm.serialization.KryoValuesDeserializer;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.bootstrap.ServerBootstrap;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.util.concurrent.GlobalEventExecutor;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.group.ChannelGroup;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.Channel;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.nio.NioEventLoopGroup;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.apache.storm.serialization.KryoValuesSerializer;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.function.Supplier;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.EventLoopGroup;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.group.DefaultChannelGroup;
[CtUnresolvedImport]import org.apache.storm.messaging.TaskMessage;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.socket.nio.NioServerSocketChannel;
[CtUnresolvedImport]import org.apache.storm.grouping.Load;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.ChannelFuture;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtUnresolvedImport]import org.apache.storm.shade.io.netty.channel.ChannelOption;
[CtUnresolvedImport]import org.apache.storm.utils.ObjectReader;
[CtUnresolvedImport]import org.apache.storm.messaging.IConnectionCallback;
[CtUnresolvedImport]import org.apache.storm.messaging.ConnectionWithStatus;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.storm.metric.api.IMetric;
[CtUnresolvedImport]import org.apache.storm.Config;
[CtUnresolvedImport]import org.apache.storm.metric.api.IStatefulObject;
[CtImportImpl]import java.util.Map;
[CtClassImpl]class Server extends [CtTypeReferenceImpl]org.apache.storm.messaging.ConnectionWithStatus implements [CtTypeReferenceImpl]org.apache.storm.metric.api.IStatefulObject , [CtTypeReferenceImpl]org.apache.storm.messaging.netty.ISaslServer {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]int LOAD_METRICS_TASK_ID = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.storm.messaging.netty.Server.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.EventLoopGroup bossEventLoopGroup;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.EventLoopGroup workerEventLoopGroup;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.bootstrap.ServerBootstrap bootstrap;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger> messagesEnqueued = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger messagesDequeued = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger([CtLiteralImpl]0);

    [CtFieldImpl]private final [CtTypeReferenceImpl]int boundPort;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> topoConf;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int port;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.group.ChannelGroup allChannels = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.group.DefaultChannelGroup([CtLiteralImpl]"storm-server", [CtFieldReadImpl]org.apache.storm.shade.io.netty.util.concurrent.GlobalEventExecutor.INSTANCE);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.serialization.KryoValuesSerializer ser;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.storm.messaging.IConnectionCallback cb;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Object> newConnectionResponse;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]boolean closing = [CtLiteralImpl]false;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Starts Netty at the given port.
     *
     * @param topoConf
     * 		The topology config
     * @param port
     * 		The port to start Netty at
     * @param cb
     * 		The callback to deliver incoming messages to
     * @param newConnectionResponse
     * 		The response to send to clients when they connect. Can be null.
     */
    Server([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> topoConf, [CtParameterImpl][CtTypeReferenceImpl]int port, [CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.messaging.IConnectionCallback cb, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Object> newConnectionResponse) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.topoConf = [CtVariableReadImpl]topoConf;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.port = [CtVariableReadImpl]port;
        [CtAssignmentImpl][CtFieldWriteImpl]ser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.serialization.KryoValuesSerializer([CtVariableReadImpl]topoConf);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cb = [CtVariableReadImpl]cb;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.newConnectionResponse = [CtVariableReadImpl]newConnectionResponse;
        [CtLocalVariableImpl][CtCommentImpl]// Configure the server.
        [CtTypeReferenceImpl]int bufferSize = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ObjectReader.getInt([CtInvocationImpl][CtVariableReadImpl]topoConf.get([CtTypeAccessImpl]Config.STORM_MESSAGING_NETTY_BUFFER_SIZE));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxWorkers = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ObjectReader.getInt([CtInvocationImpl][CtVariableReadImpl]topoConf.get([CtTypeAccessImpl]Config.STORM_MESSAGING_NETTY_SERVER_WORKER_THREADS));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.ThreadFactory bossFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.messaging.netty.NettyRenameThreadFactory([CtBinaryOperatorImpl][CtInvocationImpl]netty_name() + [CtLiteralImpl]"-boss");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.ThreadFactory workerFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.messaging.netty.NettyRenameThreadFactory([CtBinaryOperatorImpl][CtInvocationImpl]netty_name() + [CtLiteralImpl]"-worker");
        [CtAssignmentImpl][CtFieldWriteImpl]bossEventLoopGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.nio.NioEventLoopGroup([CtLiteralImpl]1, [CtVariableReadImpl]bossFactory);
        [CtAssignmentImpl][CtCommentImpl]// 0 means DEFAULT_EVENT_LOOP_THREADS
        [CtCommentImpl]// https://github.com/netty/netty/blob/netty-4.1.24.Final/transport/src/main/java/io/netty/channel/MultithreadEventLoopGroup.java#L40
        [CtFieldWriteImpl][CtThisAccessImpl]this.workerEventLoopGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.nio.NioEventLoopGroup([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]maxWorkers > [CtLiteralImpl]0 ? [CtVariableReadImpl]maxWorkers : [CtLiteralImpl]0, [CtVariableReadImpl]workerFactory);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.messaging.netty.Server.LOG.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Create Netty Server " + [CtInvocationImpl]netty_name()) + [CtLiteralImpl]", buffer_size: ") + [CtVariableReadImpl]bufferSize) + [CtLiteralImpl]", maxWorkers: ") + [CtVariableReadImpl]maxWorkers);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int backlog = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.ObjectReader.getInt([CtInvocationImpl][CtVariableReadImpl]topoConf.get([CtTypeAccessImpl]Config.STORM_MESSAGING_NETTY_SOCKET_BACKLOG), [CtLiteralImpl]500);
        [CtAssignmentImpl][CtFieldWriteImpl]bootstrap = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.shade.io.netty.bootstrap.ServerBootstrap().group([CtFieldReadImpl]bossEventLoopGroup, [CtFieldReadImpl]workerEventLoopGroup).channel([CtFieldReadImpl]org.apache.storm.shade.io.netty.channel.socket.nio.NioServerSocketChannel.class).option([CtTypeAccessImpl]ChannelOption.SO_REUSEADDR, [CtLiteralImpl]true).option([CtTypeAccessImpl]ChannelOption.SO_BACKLOG, [CtVariableReadImpl]backlog).childOption([CtTypeAccessImpl]ChannelOption.TCP_NODELAY, [CtLiteralImpl]true).childOption([CtTypeAccessImpl]ChannelOption.SO_RCVBUF, [CtVariableReadImpl]bufferSize).childOption([CtTypeAccessImpl]ChannelOption.SO_KEEPALIVE, [CtLiteralImpl]true).childOption([CtTypeAccessImpl]ChannelOption.ALLOCATOR, [CtTypeAccessImpl]PooledByteBufAllocator.DEFAULT).childHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.messaging.netty.StormServerPipelineFactory([CtVariableReadImpl]topoConf, [CtThisAccessImpl]this));
        [CtTryImpl][CtCommentImpl]// Bind and start to accept incoming connections.
        try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.ChannelFuture bindFuture = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]bootstrap.bind([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.InetSocketAddress([CtVariableReadImpl]port)).sync();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel channel = [CtInvocationImpl][CtVariableReadImpl]bindFuture.channel();
            [CtAssignmentImpl][CtFieldWriteImpl]boundPort = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.net.InetSocketAddress) ([CtVariableReadImpl]channel.localAddress())).getPort();
            [CtInvocationImpl][CtFieldReadImpl]allChannels.add([CtVariableReadImpl]channel);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addReceiveCount([CtParameterImpl][CtTypeReferenceImpl]java.lang.String from, [CtParameterImpl][CtTypeReferenceImpl]int amount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// This is possibly lossy in the case where a value is deleted
        [CtCommentImpl]// because it has received no messages over the metrics collection
        [CtCommentImpl]// period and new messages are starting to come in.  This is
        [CtCommentImpl]// because I don't want the overhead of a synchronize just to have
        [CtCommentImpl]// the metric be absolutely perfect.
        [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger i = [CtInvocationImpl][CtFieldReadImpl]messagesEnqueued.get([CtVariableReadImpl]from);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]i = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger([CtVariableReadImpl]amount);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger prev = [CtInvocationImpl][CtFieldReadImpl]messagesEnqueued.putIfAbsent([CtVariableReadImpl]from, [CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]prev != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]prev.addAndGet([CtVariableReadImpl]amount);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]i.addAndGet([CtVariableReadImpl]amount);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * enqueue a received message.
     */
    protected [CtTypeReferenceImpl]void enqueue([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.storm.messaging.TaskMessage> msgs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String from) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]msgs) || [CtInvocationImpl][CtVariableReadImpl]msgs.isEmpty()) || [CtFieldReadImpl]closing) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]addReceiveCount([CtVariableReadImpl]from, [CtInvocationImpl][CtVariableReadImpl]msgs.size());
        [CtInvocationImpl][CtFieldReadImpl]cb.recv([CtVariableReadImpl]msgs);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getPort() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]boundPort;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * close all channels, and release resources.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]allChannels.close().awaitUninterruptibly();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]workerEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]bossEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void sendLoadMetrics([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Double> taskToLoad) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.messaging.netty.MessageBatch mb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.messaging.netty.MessageBatch([CtLiteralImpl]1);
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]ser) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]mb.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.messaging.TaskMessage([CtFieldReadImpl]org.apache.storm.messaging.netty.Server.LOAD_METRICS_TASK_ID, [CtInvocationImpl][CtFieldReadImpl]ser.serialize([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Object) (taskToLoad))))));
        }
        [CtInvocationImpl][CtFieldReadImpl]allChannels.writeAndFlush([CtVariableReadImpl]mb);
    }

    [CtMethodImpl][CtCommentImpl]// this method expected to be thread safe
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void sendBackPressureStatus([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.messaging.netty.BackPressureStatus bpStatus) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.messaging.netty.Server.LOG.info([CtLiteralImpl]"Sending BackPressure status update to connected workers. BPStatus = {}", [CtVariableReadImpl]bpStatus);
        [CtInvocationImpl][CtFieldReadImpl]allChannels.writeAndFlush([CtVariableReadImpl]bpStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]org.apache.storm.grouping.Load> getLoad([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Integer> tasks) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Server connection cannot get load");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void send([CtParameterImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.apache.storm.messaging.TaskMessage> msgs) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"Server connection should not send any messages");
    }

    [CtMethodImpl]public final [CtTypeReferenceImpl]java.lang.String netty_name() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"Netty-server-localhost-" + [CtFieldReadImpl]port;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.storm.messaging.netty.Status status() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]closing) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]Status.Closed;
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]connectionEstablished([CtFieldReadImpl]allChannels)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]Status.Connecting;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]Status.Ready;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean connectionEstablished([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel channel) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]channel != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]channel.isActive();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean connectionEstablished([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.group.ChannelGroup allChannels) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allEstablished = [CtLiteralImpl]true;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel channel : [CtVariableReadImpl]allChannels) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]connectionEstablished([CtVariableReadImpl]channel)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]allEstablished = [CtLiteralImpl]false;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]allEstablished;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object getState() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.messaging.netty.Server.LOG.debug([CtLiteralImpl]"Getting metrics for server on port {}", [CtFieldReadImpl]port);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> ret = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]ret.put([CtLiteralImpl]"dequeuedMessages", [CtInvocationImpl][CtFieldReadImpl]messagesDequeued.getAndSet([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> enqueued = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger>> it = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]messagesEnqueued.entrySet().iterator();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger> ent = [CtInvocationImpl][CtVariableReadImpl]it.next();
            [CtLocalVariableImpl][CtCommentImpl]// Yes we can delete something that is not 0 because of races, but that is OK for metrics
            [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger i = [CtInvocationImpl][CtVariableReadImpl]ent.getValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]i.get() == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]it.remove();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]enqueued.put([CtInvocationImpl][CtVariableReadImpl]ent.getKey(), [CtInvocationImpl][CtVariableReadImpl]i.getAndSet([CtLiteralImpl]0));
            }
        } 
        [CtInvocationImpl][CtVariableReadImpl]ret.put([CtLiteralImpl]"enqueued", [CtVariableReadImpl]enqueued);
        [CtIfImpl][CtCommentImpl]// Report messageSizes metric, if enabled (non-null).
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]cb instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.storm.metric.api.IMetric) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object metrics = [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]org.apache.storm.metric.api.IMetric) (cb)).getValueAndReset();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]metrics instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Map) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ret.put([CtLiteralImpl]"messageBytes", [CtVariableReadImpl]metrics);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]ret;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Implementing IServer.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void channelActive([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel c) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]newConnectionResponse != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]c.writeAndFlush([CtInvocationImpl][CtFieldReadImpl]newConnectionResponse.get(), [CtInvocationImpl][CtVariableReadImpl]c.voidPromise());
        }
        [CtInvocationImpl][CtFieldReadImpl]allChannels.add([CtVariableReadImpl]c);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void received([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object message, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String remote, [CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel channel) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.storm.messaging.TaskMessage> msgs;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]msgs = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.storm.messaging.TaskMessage>) (message));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassCastException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.messaging.netty.Server.LOG.error([CtLiteralImpl]"Worker netty server receive message other than the expected class List<TaskMessage> from remote: {}", [CtVariableReadImpl]remote, [CtVariableReadImpl]e);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]enqueue([CtVariableReadImpl]msgs, [CtVariableReadImpl]remote);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String name() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]topoConf.get([CtTypeAccessImpl]Config.TOPOLOGY_NAME)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String secretKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.messaging.netty.SaslUtils.getSecretKey([CtFieldReadImpl]topoConf);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void authenticated([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.shade.io.netty.channel.Channel c) [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Netty server listening on port %s", [CtFieldReadImpl]port);
    }
}
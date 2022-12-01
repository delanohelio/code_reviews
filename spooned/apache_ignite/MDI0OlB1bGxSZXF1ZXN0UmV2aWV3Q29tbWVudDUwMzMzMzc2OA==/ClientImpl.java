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
[CtPackageDeclarationImpl]package org.apache.ignite.spi.discovery.tcp;
[CtUnresolvedImport]import org.apache.ignite.internal.util.future.GridFutureAdapter;
[CtImportImpl]import java.net.InetSocketAddress;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_NODE_LEFT;
[CtUnresolvedImport]import org.apache.ignite.cluster.ClusterMetrics;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCheckFailedMessage;
[CtImportImpl]import java.util.TreeSet;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiOperationTimeoutHelper;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientMetricsUpdateMessage;
[CtImportImpl]import java.io.StreamCorruptedException;
[CtUnresolvedImport]import static org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi.DFLT_DISCO_FAILED_CLIENT_RECONNECT_DELAY;
[CtImportImpl]import static java.util.concurrent.TimeUnit.MILLISECONDS;
[CtUnresolvedImport]import org.apache.ignite.IgniteClientDisconnectedException;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteInClosure;
[CtUnresolvedImport]import org.apache.ignite.internal.util.tostring.GridToStringExclude;
[CtUnresolvedImport]import org.apache.ignite.IgniteCheckedException;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAuthFailedMessage;
[CtImportImpl]import java.net.SocketTimeoutException;
[CtUnresolvedImport]import org.apache.ignite.configuration.IgniteConfiguration;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.DiscoverySpiListener;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddedMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryDuplicateIdMessage;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_NODE_METRICS_UPDATED;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.DiscoveryNotification;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.discovery.CustomMessageWrapper;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiException;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiThread;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.internal.U;
[CtUnresolvedImport]import org.apache.ignite.IgniteLogger;
[CtUnresolvedImport]import static org.apache.ignite.internal.events.DiscoveryCustomEvent.EVT_DISCOVERY_CUSTOM_EVT;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.apache.ignite.cluster.ClusterNode;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingResponse;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_CLIENT_NODE_RECONNECTED;
[CtUnresolvedImport]import org.apache.ignite.IgniteInterruptedException;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNodesRing;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteInterruptedCheckedException;
[CtImportImpl]import java.util.Queue;
[CtImportImpl]import static org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import org.apache.ignite.Ignite;
[CtImportImpl]import java.util.TreeMap;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.T3;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.T2;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiAdapter;
[CtImportImpl]import static org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED;
[CtImportImpl]import java.net.Socket;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteUuid;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.tracing.messages.TraceableMessage;
[CtUnresolvedImport]import static org.apache.ignite.failure.FailureType.CRITICAL_ERROR;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.discovery.DiscoveryServerOnlyCustomMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryHandshakeRequest;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteNodeAttributes;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddFinishedMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingRequest;
[CtImportImpl]import java.util.concurrent.Executors;
[CtImportImpl]import javax.net.ssl.SSLException;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteEx;
[CtUnresolvedImport]import static org.apache.ignite.IgniteSystemProperties.IGNITE_DISCO_FAILED_CLIENT_RECONNECT_DELAY;
[CtUnresolvedImport]import org.apache.ignite.internal.util.worker.GridWorker;
[CtImportImpl]import java.io.InterruptedIOException;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import org.apache.ignite.IgniteSystemProperties;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_NODE_SEGMENTED;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.tracing.messages.SpanContainer;
[CtImportImpl]import java.util.concurrent.BlockingDeque;
[CtImportImpl]import java.io.BufferedInputStream;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.X;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import org.apache.ignite.internal.worker.WorkersRegistry;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.concurrent.ScheduledExecutorService;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteClientDisconnectedCheckedException;
[CtUnresolvedImport]import org.apache.ignite.thread.IgniteThreadFactory;
[CtUnresolvedImport]import org.apache.ignite.IgniteException;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.tracing.SpanTags;
[CtImportImpl]import java.util.concurrent.ConcurrentMap;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientAckResponse;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.DiscoverySpiCustomMessage;
[CtUnresolvedImport]import org.jetbrains.annotations.Nullable;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_NODE_FAILED;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_CLIENT_NODE_DISCONNECTED;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import static org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi.DFLT_THROTTLE_RECONNECT_RESET_TIMEOUT_INTERVAL;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode;
[CtUnresolvedImport]import org.jetbrains.annotations.NotNull;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryMetricsUpdateMessage;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.tracing.messages.TraceableMessagesTable;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingRequest;
[CtImportImpl]import java.util.concurrent.LinkedBlockingDeque;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryJoinRequestMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.IgniteSpiContext;
[CtImportImpl]import java.util.NavigableMap;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.tracing.Span;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteFeatures;
[CtImportImpl]import java.util.NavigableSet;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.internal.LT;
[CtUnresolvedImport]import org.apache.ignite.failure.FailureContext;
[CtImportImpl]import static org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STOPPED;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryServerOnlyCustomEventMessage;
[CtImportImpl]import java.util.concurrent.ThreadLocalRandom;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryHandshakeResponse;
[CtImportImpl]import java.util.ArrayDeque;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.F;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage;
[CtUnresolvedImport]import static org.apache.ignite.events.EventType.EVT_NODE_JOINED;
[CtImportImpl]import static org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.util.concurrent.CountDownLatch;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryRingLatencyCheckMessage;
[CtUnresolvedImport]import org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingResponse;
[CtImportImpl]import static org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED;
[CtUnresolvedImport]import org.apache.ignite.cache.CacheMetrics;
[CtClassImpl][CtJavaDocImpl]/**
 */
class ClientImpl extends [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.TcpDiscoveryImpl {
    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]java.lang.Object SPI_STOP = [CtLiteralImpl]"SPI_STOP";

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]java.lang.Object SPI_RECONNECT_FAILED = [CtLiteralImpl]"SPI_RECONNECT_FAILED";

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]java.lang.Object SPI_RECONNECT = [CtLiteralImpl]"SPI_RECONNECT";

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]long CLIENT_THROTTLE_RECONNECT_RESET_TIMEOUT = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.IgniteSystemProperties.getLong([CtTypeAccessImpl]IgniteSystemProperties.CLIENT_THROTTLE_RECONNECT_RESET_TIMEOUT_INTERVAL, [CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi.DFLT_THROTTLE_RECONNECT_RESET_TIMEOUT_INTERVAL);

    [CtFieldImpl][CtJavaDocImpl]/**
     * Remote nodes.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentMap<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode> rmtNodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket> delayDiscoData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Topology history.
     */
    private final [CtTypeReferenceImpl]java.util.NavigableMap<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode>> topHist = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Remote nodes.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentMap<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean>> pingFuts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Socket writer.
     */
    private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketWriter sockWriter;

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketReader sockReader;

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private volatile [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State state;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Last message ID.
     */
    private volatile [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid lastMsgId;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Current topology version.
     */
    private volatile [CtTypeReferenceImpl]long topVer;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Join error. Contains error what occurs on join process.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException> joinErr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl][CtJavaDocImpl]/**
     * Joined latch.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch joinLatch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch([CtLiteralImpl]1);

    [CtFieldImpl][CtJavaDocImpl]/**
     * Left latch.
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch leaveLatch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch([CtLiteralImpl]1);

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private final [CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService executorService;

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.MessageWorker msgWorker;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Force fail message for local node.
     */
    private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage forceFailMsg;

    [CtFieldImpl][CtJavaDocImpl]/**
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.util.tostring.GridToStringExclude
    private [CtTypeReferenceImpl]int joinCnt;

    [CtConstructorImpl][CtJavaDocImpl]/**
     *
     * @param adapter
     * 		Adapter.
     */
    ClientImpl([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi adapter) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]adapter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String instanceName = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]adapter.ignite() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adapter.ignite().name() == [CtLiteralImpl]null)) ? [CtLiteralImpl]"client-node" : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adapter.ignite().name();
        [CtAssignmentImpl][CtFieldWriteImpl]executorService = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newSingleThreadScheduledExecutor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.thread.IgniteThreadFactory([CtVariableReadImpl]instanceName, [CtLiteralImpl]"tcp-discovery-exec"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dumpDebugInfo([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteLogger log) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder b = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]">>>").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]">>>").append([CtLiteralImpl]"Dumping discovery SPI debug info.").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]">>>").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"Local node ID: ").append([CtInvocationImpl]getLocalNodeId()).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl()).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"Local node: ").append([CtFieldReadImpl]locNode).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl()).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"Internal threads: ").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"    Message worker: ").append([CtInvocationImpl]threadStatus([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner())).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"    Socket reader: ").append([CtInvocationImpl]threadStatus([CtFieldReadImpl]sockReader)).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"    Socket writer: ").append([CtInvocationImpl]threadStatus([CtFieldReadImpl]sockWriter)).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtVariableReadImpl]b.append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"Nodes: ").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode node : [CtInvocationImpl]allVisibleNodes())[CtBlockImpl]
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"    ").append([CtInvocationImpl][CtVariableReadImpl]node.id()).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());

        [CtInvocationImpl][CtVariableReadImpl]b.append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.append([CtLiteralImpl]"Stats: ").append([CtTypeAccessImpl]spi.stats).append([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nl());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndInfo([CtVariableReadImpl]log, [CtInvocationImpl][CtVariableReadImpl]b.toString());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dumpRingStructure([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteLogger log) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode[] serverNodes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRemoteNodes().stream().filter([CtLambdaImpl]([CtParameterImpl] node) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]node.isClient()).sorted([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparingLong([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ClusterNode::order)).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode[]::new);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndInfo([CtVariableReadImpl]log, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.toString([CtVariableReadImpl]serverNodes));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getCurrentTopologyVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]topVer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getSpiState() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]sockWriter.isOnline())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"connected";

        [CtReturnImpl]return [CtLiteralImpl]"disconnected";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getMessageWorkerQueueSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]msgWorker.queueSize();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.UUID getCoordinator() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void spiStart([CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]java.lang.String igniteInstanceName) throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]spi.initLocalNode([CtLiteralImpl]0, [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]locNode = [CtFieldReadImpl]spi.locNode;
        [CtInvocationImpl][CtCommentImpl]// Marshal credentials for backward compatibility and security.
        marshalCredentials([CtFieldReadImpl]locNode);
        [CtAssignmentImpl][CtFieldWriteImpl]sockWriter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketWriter();
        [CtInvocationImpl][CtFieldReadImpl]sockWriter.start();
        [CtAssignmentImpl][CtFieldWriteImpl]sockReader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketReader();
        [CtInvocationImpl][CtFieldReadImpl]sockReader.start();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]spi.ipFinder.isShared() && [CtInvocationImpl][CtFieldReadImpl]spi.isForceServerMode())[CtBlockImpl]
            [CtInvocationImpl]registerLocalNodeAddress();

        [CtAssignmentImpl][CtFieldWriteImpl]msgWorker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.MessageWorker([CtFieldReadImpl]log);
        [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread([CtInvocationImpl][CtFieldReadImpl]msgWorker.igniteInstanceName(), [CtInvocationImpl][CtFieldReadImpl]msgWorker.name(), [CtFieldReadImpl]log)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void body() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]msgWorker.run();
            }
        }.start();
        [CtInvocationImpl][CtFieldReadImpl]executorService.scheduleAtFixedRate([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.MetricsSender(), [CtTypeAccessImpl]spi.metricsUpdateFreq, [CtTypeAccessImpl]spi.metricsUpdateFreq, [CtFieldReadImpl]java.util.concurrent.TimeUnit.MILLISECONDS);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]joinLatch.await();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException err = [CtInvocationImpl][CtFieldReadImpl]joinErr.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]err != [CtLiteralImpl]null)[CtBlockImpl]
                [CtThrowImpl]throw [CtVariableReadImpl]err;

        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtLiteralImpl]"Thread has been interrupted.", [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtFieldReadImpl]spi.printStartInfo();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void spiStop() throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]msgWorker != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]msgWorker.isDone())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Should always be alive
            [CtFieldReadImpl]msgWorker.addMessage([CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_STOP);
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]leaveLatch.await([CtTypeAccessImpl]spi.netTimeout, [CtFieldReadImpl]java.util.concurrent.TimeUnit.MILLISECONDS))[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to left node: timeout [nodeId=" + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignored) [CtBlockImpl]{
                [CtCommentImpl]// No-op.
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> fut : [CtInvocationImpl][CtFieldReadImpl]pingFuts.values())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]fut.onDone([CtLiteralImpl]false);

        [CtInvocationImpl][CtFieldReadImpl]rmtNodes.clear();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner());

        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockWriter);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockReader);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner(), [CtFieldReadImpl]log);

        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtFieldReadImpl]sockWriter, [CtFieldReadImpl]log);
        [CtWhileImpl][CtCommentImpl]// SocketReader may loose interruption, this hack is made to overcome that case.
        while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtFieldReadImpl]sockReader, [CtFieldReadImpl]log, [CtLiteralImpl]200))[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockReader);

        [CtInvocationImpl][CtFieldReadImpl]executorService.shutdownNow();
        [CtInvocationImpl][CtFieldReadImpl]spi.printStopInfo();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onContextInitialized0([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiContext spiCtx) throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtCommentImpl]// No-op.
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> getRemoteNodes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.arrayList([CtInvocationImpl][CtFieldReadImpl]rmtNodes.values(), [CtTypeAccessImpl]TcpDiscoveryNodesRing.VISIBLE_NODES);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean allNodesSupport([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteFeatures feature) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.IgniteFeatures.allNodesSupports([CtInvocationImpl]upcast([CtInvocationImpl][CtFieldReadImpl]rmtNodes.values()), [CtVariableReadImpl]feature);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode getNode([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtVariableReadImpl]nodeId))[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]locNode;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]node.visible() ? [CtVariableReadImpl]node : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean pingNode([CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.NotNull
    final [CtTypeReferenceImpl]java.util.UUID nodeId) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtInvocationImpl]getLocalNodeId()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]node.visible()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> fut = [CtInvocationImpl][CtFieldReadImpl]pingFuts.get([CtVariableReadImpl]nodeId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fut == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]fut = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> oldFut = [CtInvocationImpl][CtFieldReadImpl]pingFuts.putIfAbsent([CtVariableReadImpl]nodeId, [CtVariableReadImpl]fut);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldFut != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]fut = [CtVariableReadImpl]oldFut;
            else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State state = [CtFieldReadImpl][CtThisAccessImpl]this.state;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping() || [CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STOPPED)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]pingFuts.remove([CtVariableReadImpl]nodeId, [CtVariableReadImpl]fut))[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]fut.onDone([CtLiteralImpl]false);

                    [CtReturnImpl]return [CtLiteralImpl]false;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]pingFuts.remove([CtVariableReadImpl]nodeId, [CtVariableReadImpl]fut))[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]fut.onDone([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.IgniteClientDisconnectedCheckedException([CtLiteralImpl]null, [CtLiteralImpl]"Failed to ping node, client node disconnected."));

                } else [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> finalFut = [CtVariableReadImpl]fut;
                    [CtInvocationImpl][CtFieldReadImpl]executorService.schedule([CtLambdaImpl]() -> [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]pingFuts.remove([CtVariableReadImpl]nodeId, [CtVariableReadImpl]finalFut)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.state == [CtFieldReadImpl][CtFieldReferenceImpl]DISCONNECTED)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]finalFut.onDone([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.IgniteClientDisconnectedCheckedException([CtLiteralImpl]null, [CtLiteralImpl]"Failed to ping node, client node disconnected."));
                            else[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]finalFut.onDone([CtLiteralImpl]false);

                        }
                    }, [CtTypeAccessImpl]spi.netTimeout, [CtFieldReadImpl]java.util.concurrent.TimeUnit.MILLISECONDS);
                    [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingRequest([CtInvocationImpl]getLocalNodeId(), [CtVariableReadImpl]nodeId));
                }
            }
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fut.get();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteInterruptedCheckedException ignored) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void disconnect() throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner());

        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockWriter);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockReader);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner(), [CtFieldReadImpl]log);

        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtFieldReadImpl]sockWriter, [CtFieldReadImpl]log);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtFieldReadImpl]sockReader, [CtFieldReadImpl]log);
        [CtInvocationImpl][CtFieldReadImpl]leaveLatch.countDown();
        [CtInvocationImpl][CtFieldReadImpl]joinLatch.countDown();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().deregisterPorts();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> rmts = [CtInvocationImpl]getRemoteNodes();
        [CtLocalVariableImpl][CtCommentImpl]// This is restart/disconnection and remote nodes are not empty.
        [CtCommentImpl]// We need to fire FAIL event for each.
        [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiListener lsnr = [CtFieldReadImpl]spi.lsnr;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lsnr != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode n : [CtVariableReadImpl]rmts) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]rmtNodes.remove([CtInvocationImpl][CtVariableReadImpl]n.id());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top = [CtInvocationImpl]updateTopologyHistory([CtBinaryOperatorImpl][CtFieldReadImpl]topVer + [CtLiteralImpl]1, [CtLiteralImpl]null);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lsnr.onDiscovery([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoveryNotification([CtFieldReadImpl]EventType.EVT_NODE_FAILED, [CtFieldReadImpl]topVer, [CtVariableReadImpl]n, [CtVariableReadImpl]top, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>([CtFieldReadImpl]topHist), [CtLiteralImpl]null, [CtLiteralImpl]null)).get();
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]rmtNodes.clear();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void sendCustomEvent([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiCustomMessage evt) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State state = [CtFieldReadImpl][CtThisAccessImpl]this.state;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.IgniteException([CtLiteralImpl]"Failed to send custom message: client is segmented.");

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.IgniteClientDisconnectedException([CtLiteralImpl]null, [CtLiteralImpl]"Failed to send custom message: client is disconnected.");

        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage msg;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.managers.discovery.CustomMessageWrapper) (evt)).delegate() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.managers.discovery.DiscoveryServerOnlyCustomMessage)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryServerOnlyCustomEventMessage([CtInvocationImpl]getLocalNodeId(), [CtVariableReadImpl]evt, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.marshal([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtVariableReadImpl]evt));
            else[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage([CtInvocationImpl]getLocalNodeId(), [CtVariableReadImpl]evt, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.marshal([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtVariableReadImpl]evt));

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.Span rootSpan = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.create([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessagesTable.traceName([CtInvocationImpl][CtVariableReadImpl]msg.getClass())).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl]getLocalNodeId().toString()).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.CONSISTENT_ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.locNode.consistentId().toString()).addTag([CtTypeAccessImpl]SpanTags.MESSAGE_CLASS, [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.managers.discovery.CustomMessageWrapper) (evt)).delegate().getClass().getSimpleName()).addLog([CtLambdaImpl]() -> [CtLiteralImpl]"Created");
            [CtInvocationImpl][CtCommentImpl]// This root span will be parent both from local and remote nodes.
            [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer().serializedSpanBytes([CtInvocationImpl][CtFieldReadImpl]tracing.serialize([CtVariableReadImpl]rootSpan));
            [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtVariableReadImpl]msg);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rootSpan.addLog([CtLambdaImpl]() -> [CtLiteralImpl]"Sent").end();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to marshal custom event: " + [CtVariableReadImpl]evt, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void failNode([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]java.lang.String warning) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage([CtInvocationImpl]getLocalNodeId(), [CtInvocationImpl][CtVariableReadImpl]node.id(), [CtInvocationImpl][CtVariableReadImpl]node.internalOrder());
            [CtInvocationImpl][CtVariableReadImpl]msg.warning([CtVariableReadImpl]warning);
            [CtInvocationImpl][CtVariableReadImpl]msg.force([CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtVariableReadImpl]msg);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param prevAddr
     * 		If reconnect is in progress, then previous address of the router the client was connected to
     * 		and {@code null} otherwise.
     * @param timeout
     * 		Timeout.
     * @param beforeEachSleep
     * 		code to be run before each sleep span.
     * @param afterEachSleep
     * 		code to be run before each sleep span.
     * @return Opened socket or {@code null} if timeout.
     * @throws InterruptedException
     * 		If interrupted.
     * @throws IgniteSpiException
     * 		If failed.
     * @see TcpDiscoverySpi#joinTimeout
     */
    [CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    private [CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T2<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream, [CtTypeReferenceImpl]java.lang.Boolean> joinTopology([CtParameterImpl][CtTypeReferenceImpl]java.net.InetSocketAddress prevAddr, [CtParameterImpl][CtTypeReferenceImpl]long timeout, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]java.lang.Runnable beforeEachSleep, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]java.lang.Runnable afterEachSleep) throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.net.InetSocketAddress> addrs = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
        [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted())[CtBlockImpl]
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InterruptedException();

            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]addrs == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]addrs.isEmpty()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]addrs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]spi.resolvedAddresses());
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.F.isEmpty([CtVariableReadImpl]addrs)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Resolved addresses from IP finder: " + [CtVariableReadImpl]addrs);

                } else [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]timeout > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisSinceNanos([CtVariableReadImpl]startNanos) > [CtVariableReadImpl]timeout))[CtBlockImpl]
                        [CtReturnImpl]return [CtLiteralImpl]null;

                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.LT.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IP finder returned empty addresses list. " + [CtLiteralImpl]"Please check IP finder configuration") + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]spi.ipFinder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder ? [CtLiteralImpl]" and make sure multicast works on your network. " : [CtLiteralImpl]". ")) + [CtLiteralImpl]"Will retry every ") + [CtInvocationImpl][CtFieldReadImpl]spi.getReconnectDelay()) + [CtLiteralImpl]" ms. ") + [CtLiteralImpl]"Change 'reconnectDelay' to configure the frequency of retries.", [CtLiteralImpl]true);
                    [CtInvocationImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.sleepEx([CtInvocationImpl][CtFieldReadImpl]spi.getReconnectDelay(), [CtVariableReadImpl]beforeEachSleep, [CtVariableReadImpl]afterEachSleep);
                }
            } 
            [CtIfImpl][CtCommentImpl]// Process failed node last.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]prevAddr != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtInvocationImpl][CtVariableReadImpl]addrs.indexOf([CtVariableReadImpl]prevAddr);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx != [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.swap([CtVariableReadImpl]addrs, [CtVariableReadImpl]idx, [CtLiteralImpl]0);

            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.net.InetSocketAddress> addrs0 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]addrs);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean wait = [CtLiteralImpl]false;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]addrs.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted())[CtBlockImpl]
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InterruptedException();

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.InetSocketAddress addr = [CtInvocationImpl][CtVariableReadImpl]addrs.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean recon = [CtBinaryOperatorImpl][CtVariableReadImpl]prevAddr != [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T3<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream, [CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Boolean> sockAndRes = [CtInvocationImpl]sendJoinRequest([CtVariableReadImpl]recon, [CtVariableReadImpl]addr);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sockAndRes == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]addrs.remove([CtVariableReadImpl]i);
                    [CtContinueImpl]continue;
                }
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sockAndRes.get1() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sockAndRes.get2() != [CtLiteralImpl]null) : [CtVariableReadImpl]sockAndRes;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sockAndRes.get1().socket();
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Received response to join request [addr=" + [CtVariableReadImpl]addr) + [CtLiteralImpl]", res=") + [CtInvocationImpl][CtVariableReadImpl]sockAndRes.get2()) + [CtLiteralImpl]']');

                [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]sockAndRes.get2()) {
                    [CtCaseImpl]case [CtFieldReadImpl]RES_OK :
                        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T2<>([CtInvocationImpl][CtVariableReadImpl]sockAndRes.get1(), [CtInvocationImpl][CtVariableReadImpl]sockAndRes.get3());
                    [CtCaseImpl]case [CtFieldReadImpl]RES_CONTINUE_JOIN :
                    [CtCaseImpl]case [CtFieldReadImpl]RES_WAIT :
                        [CtAssignmentImpl][CtVariableWriteImpl]wait = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received unexpected response to join request: " + [CtInvocationImpl][CtVariableReadImpl]sockAndRes.get2());

                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]timeout > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisSinceNanos([CtVariableReadImpl]startNanos) > [CtVariableReadImpl]timeout))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtIfImpl]if ([CtVariableReadImpl]wait) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Will wait before retry join.");

                [CtInvocationImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.sleepEx([CtInvocationImpl][CtFieldReadImpl]spi.getReconnectDelay(), [CtVariableReadImpl]beforeEachSleep, [CtVariableReadImpl]afterEachSleep);
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]addrs.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.LT.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to connect to any address from IP finder (will retry to join topology " + [CtLiteralImpl]"every ") + [CtInvocationImpl][CtFieldReadImpl]spi.getReconnectDelay()) + [CtLiteralImpl]" ms; change 'reconnectDelay' to configure the frequency ") + [CtLiteralImpl]"of retries): ") + [CtInvocationImpl]toOrderedList([CtVariableReadImpl]addrs0), [CtLiteralImpl]true);
                [CtInvocationImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.sleepEx([CtInvocationImpl][CtFieldReadImpl]spi.getReconnectDelay(), [CtVariableReadImpl]beforeEachSleep, [CtVariableReadImpl]afterEachSleep);
            }
        } 
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    private static [CtTypeReferenceImpl]void sleepEx([CtParameterImpl][CtTypeReferenceImpl]long millis, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable before, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable after) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]before != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]before.run();

        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtVariableReadImpl]millis);
        } finally [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]after != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]after.run();

        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param recon
     * 		{@code True} if reconnects.
     * @param addr
     * 		Address.
     * @return Socket, connect response and client acknowledge support flag.
     */
    [CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    private [CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T3<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream, [CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Boolean> sendJoinRequest([CtParameterImpl][CtTypeReferenceImpl]boolean recon, [CtParameterImpl][CtTypeReferenceImpl]java.net.InetSocketAddress addr) [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]addr != [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Send join request [addr=" + [CtVariableReadImpl]addr) + [CtLiteralImpl]", reconnect=") + [CtVariableReadImpl]recon) + [CtLiteralImpl]", locNodeId=") + [CtInvocationImpl]getLocalNodeId()) + [CtLiteralImpl]']');

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Throwable> errs = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long ackTimeout0 = [CtInvocationImpl][CtFieldReadImpl]spi.getAckTimeout();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int reconCnt = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int connectAttempts = [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int sslConnectAttempts = [CtLiteralImpl]3;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNodeId = [CtInvocationImpl]getLocalNodeId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiOperationTimeoutHelper timeoutHelper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiOperationTimeoutHelper([CtFieldReadImpl]spi, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket discoveryData = [CtLiteralImpl]null;
        [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean openSock = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long tsNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
                [CtAssignmentImpl][CtVariableWriteImpl]sock = [CtInvocationImpl][CtFieldReadImpl]spi.openSocket([CtVariableReadImpl]addr, [CtVariableReadImpl]timeoutHelper);
                [CtAssignmentImpl][CtVariableWriteImpl]openSock = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryHandshakeRequest req = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryHandshakeRequest([CtVariableReadImpl]locNodeId);
                [CtInvocationImpl][CtVariableReadImpl]req.client([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]spi.writeToSocket([CtVariableReadImpl]sock, [CtVariableReadImpl]req, [CtInvocationImpl][CtVariableReadImpl]timeoutHelper.nextTimeoutChunk([CtInvocationImpl][CtFieldReadImpl]spi.getSocketTimeout()));
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryHandshakeResponse res = [CtInvocationImpl][CtFieldReadImpl]spi.readMessage([CtVariableReadImpl]sock, [CtLiteralImpl]null, [CtVariableReadImpl]ackTimeout0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID rmtNodeId = [CtInvocationImpl][CtVariableReadImpl]res.creatorNodeId();
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]rmtNodeId != [CtLiteralImpl]null;
                [CtAssertImpl]assert [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtVariableReadImpl]rmtNodeId);
                [CtInvocationImpl][CtFieldReadImpl]locNode.clientRouterNodeId([CtVariableReadImpl]rmtNodeId);
                [CtAssignmentImpl][CtVariableWriteImpl]tsNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg;
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]recon) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtFieldReadImpl]locNode;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]locNode.order() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]node = [CtInvocationImpl][CtFieldReadImpl]locNode.clientReconnectNode([CtTypeAccessImpl]spi.locNodeAttrs);
                        [CtInvocationImpl]marshalCredentials([CtVariableReadImpl]node);
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]discoveryData == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket discoveryDataPacket = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket([CtInvocationImpl]getLocalNodeId());
                        [CtInvocationImpl][CtVariableReadImpl]discoveryDataPacket.joiningNodeClient([CtLiteralImpl]true);
                        [CtAssignmentImpl][CtVariableWriteImpl]discoveryData = [CtInvocationImpl][CtFieldReadImpl]spi.collectExchangeData([CtVariableReadImpl]discoveryDataPacket);
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryJoinRequestMessage joinReqMsg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryJoinRequestMessage([CtVariableReadImpl]node, [CtVariableReadImpl]discoveryData);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode nodef = [CtVariableReadImpl]node;
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]joinReqMsg.spanContainer().span([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.create([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessagesTable.traceName([CtInvocationImpl][CtVariableReadImpl]joinReqMsg.getClass())).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nodef.id().toString()).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.CONSISTENT_ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nodef.consistentId().toString()).addLog([CtLambdaImpl]() -> [CtLiteralImpl]"Created").end());
                    [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtVariableReadImpl]joinReqMsg;
                    [CtIfImpl][CtCommentImpl]// During marshalling, SPI didn't know whether all nodes support compression as we didn't join yet.
                    [CtCommentImpl]// The only way to know is passing flag directly with handshake response.
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]res.isDiscoveryDataPacketCompression())[CtBlockImpl]
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryJoinRequestMessage) (msg)).gridDiscoveryData().unzipData([CtFieldReadImpl]log);

                } else[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage([CtInvocationImpl]getLocalNodeId(), [CtVariableReadImpl]rmtNodeId, [CtFieldReadImpl]lastMsgId);

                [CtInvocationImpl][CtVariableReadImpl]msg.client([CtLiteralImpl]true);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage)[CtBlockImpl]
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.messages().beforeSend([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage) (msg)));

                [CtInvocationImpl][CtFieldReadImpl]spi.writeToSocket([CtVariableReadImpl]sock, [CtVariableReadImpl]msg, [CtInvocationImpl][CtVariableReadImpl]timeoutHelper.nextTimeoutChunk([CtInvocationImpl][CtFieldReadImpl]spi.getSocketTimeout()));
                [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onMessageSent([CtVariableReadImpl]msg, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisSinceNanos([CtVariableReadImpl]tsNanos));
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Message has been sent to address [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", addr=") + [CtVariableReadImpl]addr) + [CtLiteralImpl]", rmtNodeId=") + [CtVariableReadImpl]rmtNodeId) + [CtLiteralImpl]']');

                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T3<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream([CtVariableReadImpl]sock), [CtInvocationImpl][CtFieldReadImpl]spi.readReceipt([CtVariableReadImpl]sock, [CtInvocationImpl][CtVariableReadImpl]timeoutHelper.nextTimeoutChunk([CtVariableReadImpl]ackTimeout0)), [CtInvocationImpl][CtVariableReadImpl]res.clientAck());
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtLiteralImpl]"Exception on joining: " + [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);

                [CtInvocationImpl]onException([CtBinaryOperatorImpl][CtLiteralImpl]"Exception on joining: " + [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]errs == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]errs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

                [CtInvocationImpl][CtVariableReadImpl]errs.add([CtVariableReadImpl]e);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.hasCause([CtVariableReadImpl]e, [CtFieldReadImpl]javax.net.ssl.SSLException.class)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](--[CtVariableWriteImpl]sslConnectAttempts) == [CtLiteralImpl]0)[CtBlockImpl]
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unable to establish secure connection. " + [CtLiteralImpl]"Was remote cluster configured with SSL? [rmtAddr=") + [CtVariableReadImpl]addr) + [CtLiteralImpl]", errMsg=\"") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage()) + [CtLiteralImpl]"\"]", [CtVariableReadImpl]e);

                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.hasCause([CtVariableReadImpl]e, [CtFieldReadImpl]java.io.StreamCorruptedException.class)) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// StreamCorruptedException could be caused by remote node failover
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]connectAttempts < [CtLiteralImpl]2) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]connectAttempts++;
                        [CtContinueImpl]continue;
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Connect failed with StreamCorruptedException, skip address: " + [CtVariableReadImpl]addr);

                    [CtBreakImpl]break;
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]timeoutHelper.checkFailureTimeoutReached([CtVariableReadImpl]e))[CtBlockImpl]
                    [CtBreakImpl]break;

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled()) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](++[CtVariableWriteImpl]reconCnt) == [CtInvocationImpl][CtFieldReadImpl]spi.getReconnectCount()))[CtBlockImpl]
                    [CtBreakImpl]break;

                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]openSock) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// Reconnect for the second time, if connection is not established.
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]connectAttempts < [CtLiteralImpl]2) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]connectAttempts++;
                        [CtContinueImpl]continue;
                    }
                    [CtBreakImpl]break;[CtCommentImpl]// Don't retry if we can not establish connection.

                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.net.SocketTimeoutException) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.hasCause([CtVariableReadImpl]e, [CtFieldReadImpl]java.net.SocketTimeoutException.class))) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]ackTimeout0 *= [CtLiteralImpl]2;
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkAckTimeout([CtVariableReadImpl]ackTimeout0))[CtBlockImpl]
                        [CtBreakImpl]break;

                }
            }
        } 
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to join to address [addr=" + [CtVariableReadImpl]addr) + [CtLiteralImpl]", recon=") + [CtVariableReadImpl]recon) + [CtLiteralImpl]", errs=") + [CtVariableReadImpl]errs) + [CtLiteralImpl]']');

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Marshalls credentials with discovery SPI marshaller (will replace attribute value).
     *
     * @param node
     * 		Node to marshall credentials for.
     * @throws IgniteSpiException
     * 		If marshalling failed.
     */
    private [CtTypeReferenceImpl]void marshalCredentials([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node) throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Use security-unsafe getter.
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> attrs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtInvocationImpl][CtVariableReadImpl]node.getAttributes());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object creds = [CtInvocationImpl][CtVariableReadImpl]attrs.get([CtTypeAccessImpl]IgniteNodeAttributes.ATTR_SECURITY_CREDENTIALS);
            [CtAssertImpl]assert [CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]creds instanceof [CtTypeAccessImpl][CtArrayTypeReferenceImpl]byte[]);
            [CtInvocationImpl][CtVariableReadImpl]attrs.put([CtTypeAccessImpl]IgniteNodeAttributes.ATTR_SECURITY_CREDENTIALS, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.marshal([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtVariableReadImpl]creds));
            [CtInvocationImpl][CtVariableReadImpl]node.setAttributes([CtVariableReadImpl]attrs);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to marshal node security credentials: " + [CtInvocationImpl][CtVariableReadImpl]node.id(), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param topVer
     * 		New topology version.
     * @param msg
     * 		Discovery message.
     * @return Latest topology snapshot.
     */
    private [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> updateTopologyHistory([CtParameterImpl][CtTypeReferenceImpl]long topVer, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.topVer = [CtVariableReadImpl]topVer;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]topHist.isEmpty()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]topVer <= [CtInvocationImpl][CtFieldReadImpl]topHist.lastKey())) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Skip topology update since topology already updated [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", lastHistKey=") + [CtInvocationImpl][CtFieldReadImpl]topHist.lastKey()) + [CtLiteralImpl]", topVer=") + [CtVariableReadImpl]topVer) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top = [CtInvocationImpl][CtFieldReadImpl]topHist.get([CtVariableReadImpl]topVer);
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]top != [CtLiteralImpl]null : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to find topology history [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", hist=") + [CtFieldReadImpl]topHist) + [CtLiteralImpl]']';
            [CtReturnImpl]return [CtVariableReadImpl]top;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.NavigableSet<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> allNodes = [CtInvocationImpl]allVisibleNodes();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]topHist.containsKey([CtVariableReadImpl]topVer)) [CtBlockImpl]{
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]topHist.isEmpty() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]topHist.lastKey() == [CtBinaryOperatorImpl]([CtVariableReadImpl]topVer - [CtLiteralImpl]1)) : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"lastVer=" + [CtConditionalImpl]([CtInvocationImpl][CtFieldReadImpl]topHist.isEmpty() ? [CtLiteralImpl]null : [CtInvocationImpl][CtFieldReadImpl]topHist.lastKey())) + [CtLiteralImpl]", newVer=") + [CtVariableReadImpl]topVer) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]", msg=") + [CtVariableReadImpl]msg;
            [CtInvocationImpl][CtFieldReadImpl]topHist.put([CtVariableReadImpl]topVer, [CtVariableReadImpl]allNodes);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]topHist.size() > [CtFieldReadImpl]spi.topHistSize)[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]topHist.pollFirstEntry();

            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]topHist.lastKey() == [CtVariableReadImpl]topVer;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]topHist.size() <= [CtFieldReadImpl]spi.topHistSize;
        }
        [CtReturnImpl]return [CtVariableReadImpl]allNodes;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return All nodes.
     */
    private [CtTypeReferenceImpl]java.util.NavigableSet<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> allVisibleNodes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.NavigableSet<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> allNodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node : [CtInvocationImpl][CtFieldReadImpl]rmtNodes.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]node.visible())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]allNodes.add([CtVariableReadImpl]node);

        }
        [CtInvocationImpl][CtVariableReadImpl]allNodes.add([CtFieldReadImpl]locNode);
        [CtReturnImpl]return [CtVariableReadImpl]allNodes;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtTypeReferenceImpl]void simulateNodeFailure() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtLiteralImpl]"Simulating client node failure: " + [CtInvocationImpl]getLocalNodeId());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtFieldReadImpl]sockWriter);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.interrupt([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner());

        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtFieldReadImpl]sockWriter, [CtFieldReadImpl]log);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]msgWorker != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.join([CtInvocationImpl][CtFieldReadImpl]msgWorker.runner(), [CtFieldReadImpl]log);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void reconnect() throws [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_RECONNECT);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void brakeConnection() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream = [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]msgWorker.currSock;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sockStream != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtInvocationImpl][CtVariableReadImpl]sockStream.socket());

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void checkRingLatency([CtParameterImpl][CtTypeReferenceImpl]int maxHops) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryRingLatencyCheckMessage msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryRingLatencyCheckMessage([CtInvocationImpl]getLocalNodeId(), [CtVariableReadImpl]maxHops);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Latency check initiated: " + [CtInvocationImpl][CtVariableReadImpl]msg.id());

        [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtVariableReadImpl]msg);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread> threads() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread> res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]res.add([CtFieldReadImpl]sockWriter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.MessageWorker msgWorker0 = [CtFieldReadImpl]msgWorker;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msgWorker0 != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Thread runner = [CtInvocationImpl][CtVariableReadImpl]msgWorker0.runner();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]runner instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]res.add([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread) (runner)));

        }
        [CtReturnImpl]return [CtVariableReadImpl]res;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateMetrics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterMetrics metrics, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]org.apache.ignite.cache.CacheMetrics> cacheMetrics, [CtParameterImpl][CtTypeReferenceImpl]long tsNanos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isLocDaemon = [CtInvocationImpl][CtTypeAccessImpl]spi.locNode.isDaemon();
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]nodeId != [CtLiteralImpl]null;
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]metrics != [CtLiteralImpl]null;
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]isLocDaemon || [CtBinaryOperatorImpl]([CtVariableReadImpl]cacheMetrics != [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtInvocationImpl]getLocalNodeId())) ? [CtFieldReadImpl]locNode : [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]node.visible()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]node.setMetrics([CtVariableReadImpl]metrics);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isLocDaemon)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]node.setCacheMetrics([CtVariableReadImpl]cacheMetrics);

            [CtInvocationImpl][CtVariableReadImpl]node.lastUpdateTimeNanos([CtVariableReadImpl]tsNanos);
            [CtInvocationImpl][CtFieldReadImpl]msgWorker.notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_METRICS_UPDATED, [CtFieldReadImpl]topVer, [CtVariableReadImpl]node, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received metrics from unknown node: " + [CtVariableReadImpl]nodeId);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FOR TEST PURPOSE ONLY!
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"BusyWait")
    public [CtTypeReferenceImpl]void waitForClientMessagePrecessed() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object last = [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]msgWorker.queue.peekLast();
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]last != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]msgWorker.isDone())) && [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]msgWorker.queue.contains([CtVariableReadImpl]last)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]10);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignored) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().interrupt();
            }
        } 
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param err
     * 		Error.
     */
    private [CtTypeReferenceImpl]void joinError([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException err) [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]err != [CtLiteralImpl]null;
        [CtInvocationImpl][CtFieldReadImpl]joinErr.compareAndSet([CtLiteralImpl]null, [CtVariableReadImpl]err);
        [CtInvocationImpl][CtFieldReadImpl]joinLatch.countDown();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    private [CtTypeReferenceImpl]org.apache.ignite.internal.worker.WorkersRegistry getWorkersRegistry() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.Ignite ignite = [CtInvocationImpl][CtFieldReadImpl]spi.ignite();
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]ignite instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx) (ignite)).context().workersRegistry() : [CtLiteralImpl]null;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Metrics sender.
     */
    private class MetricsSender implements [CtTypeReferenceImpl]java.lang.Runnable {
        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping()) && [CtInvocationImpl][CtFieldReadImpl]sockWriter.isOnline()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientMetricsUpdateMessage msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientMetricsUpdateMessage([CtInvocationImpl]getLocalNodeId(), [CtInvocationImpl][CtTypeAccessImpl]spi.metricsProvider.metrics());
                [CtInvocationImpl][CtVariableReadImpl]msg.client([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtVariableReadImpl]msg);
            }
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Socket reader.
     */
    private class SocketReader extends [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.lang.Object mux = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]java.util.UUID rmtNodeId;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch stopReadLatch;

        [CtConstructorImpl][CtJavaDocImpl]/**
         */
        SocketReader() [CtBlockImpl]{
            [CtInvocationImpl]super([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().name(), [CtLiteralImpl]"tcp-client-disco-sock-reader-[]", [CtFieldReadImpl]log);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param sockStream
         * 		Socket.
         * @param rmtNodeId
         * 		Rmt node id.
         */
        [CtTypeReferenceImpl]void setSocket([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID rmtNodeId) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sockStream = [CtVariableReadImpl]sockStream;
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rmtNodeId = [CtVariableReadImpl]rmtNodeId;
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @throws InterruptedException
         * 		If interrupted.
         */
        private [CtTypeReferenceImpl]void forceStopRead() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CountDownLatch stopReadLatch;
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream stream = [CtFieldReadImpl]sockStream;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stream == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stopReadLatch = [CtAssignmentImpl][CtVariableWriteImpl]stopReadLatch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch([CtLiteralImpl]1);
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtInvocationImpl][CtVariableReadImpl]stream.socket());
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sockStream = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rmtNodeId = [CtLiteralImpl]null;
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
            [CtInvocationImpl][CtVariableReadImpl]stopReadLatch.await();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]void body() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl]isInterrupted()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID rmtNodeId;
                [CtInvocationImpl][CtCommentImpl]// Disconnected from router node.
                [CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.enhanceThreadName([CtLiteralImpl]"");
                [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]stopReadLatch != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]stopReadLatch.countDown();
                        [CtAssignmentImpl][CtFieldWriteImpl]stopReadLatch = [CtLiteralImpl]null;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.sockStream == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]mux.wait();
                        [CtContinueImpl]continue;
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]sockStream = [CtFieldReadImpl][CtThisAccessImpl]this.sockStream;
                    [CtAssignmentImpl][CtVariableWriteImpl]rmtNodeId = [CtFieldReadImpl][CtThisAccessImpl]this.rmtNodeId;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock = [CtInvocationImpl][CtVariableReadImpl]sockStream.socket();
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.enhanceThreadName([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.id8([CtVariableReadImpl]rmtNodeId) + [CtLiteralImpl]' ') + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sockStream.sock.getInetAddress().getHostAddress()) + [CtLiteralImpl]":") + [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sockStream.sock.getPort());
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl][CtVariableReadImpl]sockStream.stream();
                    [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sock.getKeepAlive() && [CtInvocationImpl][CtVariableReadImpl]sock.getTcpNoDelay() : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Socket wasn't configured properly:" + [CtLiteralImpl]" KeepAlive ") + [CtInvocationImpl][CtVariableReadImpl]sock.getKeepAlive()) + [CtLiteralImpl]" TcpNoDelay ") + [CtInvocationImpl][CtVariableReadImpl]sock.getTcpNoDelay();
                    [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl]isInterrupted()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg;
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.unmarshal([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtVariableReadImpl]in, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to read message [sock=" + [CtVariableReadImpl]sock) + [CtLiteralImpl]", ") + [CtLiteralImpl]"locNodeId=") + [CtInvocationImpl]getLocalNodeId()) + [CtLiteralImpl]", rmtNodeId=") + [CtVariableReadImpl]rmtNodeId) + [CtLiteralImpl]']', [CtVariableReadImpl]e);

                            [CtIfImpl][CtCommentImpl]// Exists possibility that exception raised on interruption.
                            if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.hasCause([CtVariableReadImpl]e, [CtFieldReadImpl]java.lang.InterruptedException.class, [CtFieldReadImpl]java.io.InterruptedIOException.class, [CtFieldReadImpl]org.apache.ignite.internal.IgniteInterruptedCheckedException.class, [CtFieldReadImpl]org.apache.ignite.IgniteInterruptedException.class))[CtBlockImpl]
                                [CtInvocationImpl]interrupt();

                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.IOException ioEx = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.cause([CtVariableReadImpl]e, [CtFieldReadImpl]java.io.IOException.class);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ioEx != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtThrowImpl]throw [CtVariableReadImpl]ioEx;

                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException clsNotFoundEx = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.X.cause([CtVariableReadImpl]e, [CtFieldReadImpl]java.lang.ClassNotFoundException.class);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clsNotFoundEx != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.LT.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to read message due to ClassNotFoundException " + [CtLiteralImpl]"(make sure same versions of all classes are available on all nodes) ") + [CtLiteralImpl]"[rmtNodeId=") + [CtVariableReadImpl]rmtNodeId) + [CtLiteralImpl]", err=") + [CtInvocationImpl][CtVariableReadImpl]clsNotFoundEx.getMessage()) + [CtLiteralImpl]']');
                            else[CtBlockImpl]
                                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.LT.error([CtFieldReadImpl]log, [CtVariableReadImpl]e, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to read message [sock=" + [CtVariableReadImpl]sock) + [CtLiteralImpl]", locNodeId=") + [CtInvocationImpl]getLocalNodeId()) + [CtLiteralImpl]", rmtNodeId=") + [CtVariableReadImpl]rmtNodeId) + [CtLiteralImpl]']');

                            [CtContinueImpl]continue;
                        }
                        [CtInvocationImpl][CtVariableReadImpl]msg.senderNodeId([CtVariableReadImpl]rmtNodeId);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.DebugLogger debugLog = [CtInvocationImpl]messageLogger([CtVariableReadImpl]msg);
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]debugLog.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]debugLog.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Message has been received: " + [CtVariableReadImpl]msg);

                        [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onMessageReceived([CtVariableReadImpl]msg);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean ack = [CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientAckResponse;
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]ack)[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtVariableReadImpl]msg);
                        else[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]sockWriter.ackReceived([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientAckResponse) (msg)));

                    } 
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketClosedMessage([CtVariableReadImpl]sockStream));
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Connection failed [sock=" + [CtVariableReadImpl]sock) + [CtLiteralImpl]", locNodeId=") + [CtInvocationImpl]getLocalNodeId()) + [CtLiteralImpl]']', [CtVariableReadImpl]e);

                } finally [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                    [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.sockStream == [CtVariableReadImpl]sockStream) [CtBlockImpl]{
                            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sockStream = [CtLiteralImpl]null;
                            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rmtNodeId = [CtLiteralImpl]null;
                        }
                    }
                }
            } 
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     */
    private class SocketWriter extends [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.lang.Object mux = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]java.net.Socket sock;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]boolean clientAck;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage> queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayDeque<>();

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]long sockTimeout;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage unackedMsg;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch forceLeaveLatch;

        [CtConstructorImpl][CtJavaDocImpl]/**
         */
        SocketWriter() [CtBlockImpl]{
            [CtInvocationImpl]super([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().name(), [CtLiteralImpl]"tcp-client-disco-sock-writer", [CtFieldReadImpl]log);
            [CtAssignmentImpl][CtFieldWriteImpl]sockTimeout = [CtConditionalImpl]([CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled()) ? [CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeout() : [CtInvocationImpl][CtFieldReadImpl]spi.getSocketTimeout();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void sendMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]queue.add([CtVariableReadImpl]msg);
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sends {@link TcpDiscoveryNodeLeftMessage} and closes socket.
         *
         * @throws InterruptedException
         * 		If interrupted.
         */
        private [CtTypeReferenceImpl]void forceLeave() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CountDownLatch forceLeaveLatch;
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// If writer was stopped.
                if ([CtBinaryOperatorImpl][CtFieldReadImpl]sock == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.forceLeaveLatch = [CtAssignmentImpl][CtVariableWriteImpl]forceLeaveLatch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CountDownLatch([CtLiteralImpl]1);
                [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtLiteralImpl]null;
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
            [CtInvocationImpl][CtVariableReadImpl]forceLeaveLatch.await();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param sock
         * 		Socket.
         * @param clientAck
         * 		{@code True} is server supports client message acknowlede.
         */
        private [CtTypeReferenceImpl]void setSocket([CtParameterImpl][CtTypeReferenceImpl]java.net.Socket sock, [CtParameterImpl][CtTypeReferenceImpl]boolean clientAck) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sock = [CtVariableReadImpl]sock;
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.clientAck = [CtVariableReadImpl]clientAck;
                [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtLiteralImpl]null;
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return {@code True} if connection is alive.
         */
        public [CtTypeReferenceImpl]boolean isOnline() [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]sock != [CtLiteralImpl]null;
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param res
         * 		Acknowledge response.
         */
        [CtTypeReferenceImpl]void ackReceived([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientAckResponse res) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]unackedMsg != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssertImpl]assert [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]unackedMsg.id().equals([CtInvocationImpl][CtVariableReadImpl]res.messageId()) : [CtFieldReadImpl]unackedMsg;
                    [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtLiteralImpl]null;
                }
                [CtInvocationImpl][CtFieldReadImpl]mux.notifyAll();
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]void body() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg = [CtLiteralImpl]null;
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock;
                [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]sock = [CtFieldReadImpl][CtThisAccessImpl]this.sock;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sock == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]mux.wait();
                        [CtContinueImpl]continue;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]forceLeaveLatch != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage([CtInvocationImpl]getLocalNodeId());
                        [CtInvocationImpl][CtVariableReadImpl]msg.client([CtLiteralImpl]true);
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]spi.writeToSocket([CtVariableReadImpl]sock, [CtVariableReadImpl]msg, [CtFieldReadImpl]sockTimeout);
                        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to send TcpDiscoveryNodeLeftMessage on force leave [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", err=") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage()) + [CtLiteralImpl]']');
                            }
                        }
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sock = [CtLiteralImpl]null;
                        [CtInvocationImpl]clear();
                        [CtContinueImpl]continue;
                    } else [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg == [CtLiteralImpl]null)[CtBlockImpl]
                            [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtInvocationImpl][CtFieldReadImpl]queue.poll();

                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]mux.wait();
                            [CtContinueImpl]continue;
                        }
                    }
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteInClosure<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage> msgLsnr : [CtFieldReadImpl]spi.sndMsgLsnrs)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]msgLsnr.apply([CtVariableReadImpl]msg);

                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean ack = [CtBinaryOperatorImpl][CtFieldReadImpl]clientAck && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingResponse));
                [CtTryImpl]try [CtBlockImpl]{
                    [CtIfImpl]if ([CtVariableReadImpl]ack) [CtBlockImpl]{
                        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtFieldReadImpl]unackedMsg == [CtLiteralImpl]null : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unacked=" + [CtFieldReadImpl]unackedMsg) + [CtLiteralImpl]", received=") + [CtVariableReadImpl]msg;
                            [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtVariableReadImpl]msg;
                        }
                    }
                    [CtInvocationImpl][CtFieldReadImpl]spi.writeToSocket([CtVariableReadImpl]sock, [CtVariableReadImpl]msg, [CtFieldReadImpl]sockTimeout);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid latencyCheckId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryRingLatencyCheckMessage) ? [CtInvocationImpl][CtVariableReadImpl]msg.id() : [CtLiteralImpl]null;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]latencyCheckId != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Latency check message has been written to socket: " + [CtVariableReadImpl]latencyCheckId);

                    [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtLiteralImpl]null;
                    [CtIfImpl]if ([CtVariableReadImpl]ack) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long waitEndNanos = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime() + [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisToNanos([CtConditionalImpl][CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled() ? [CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeout() : [CtInvocationImpl][CtFieldReadImpl]spi.getAckTimeout());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage unacked;
                        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]long nowNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
                            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]unackedMsg != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]waitEndNanos - [CtVariableReadImpl]nowNanos) > [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]mux.wait([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.nanosToMillis([CtBinaryOperatorImpl][CtVariableReadImpl]waitEndNanos - [CtVariableReadImpl]nowNanos));
                                [CtAssignmentImpl][CtVariableWriteImpl]nowNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
                            } 
                            [CtAssignmentImpl][CtVariableWriteImpl]unacked = [CtFieldReadImpl]unackedMsg;
                            [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtLiteralImpl]null;
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]unacked != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to get acknowledge for message, will try to reconnect " + [CtLiteralImpl]"[msg=") + [CtVariableReadImpl]unacked) + [CtConditionalImpl]([CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled() ? [CtBinaryOperatorImpl][CtLiteralImpl]", failureDetectionTimeout=" + [CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeout() : [CtBinaryOperatorImpl][CtLiteralImpl]", timeout=" + [CtInvocationImpl][CtFieldReadImpl]spi.getAckTimeout())) + [CtLiteralImpl]']');

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to get acknowledge for message: " + [CtVariableReadImpl]unacked);
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]latencyCheckId != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Latency check message has been acked: " + [CtVariableReadImpl]latencyCheckId);

                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignored) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Client socket writer interrupted.");

                    [CtReturnImpl]return;
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to send message, node is stopping [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", err=") + [CtVariableReadImpl]e) + [CtLiteralImpl]']');

                    } else[CtBlockImpl]
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtLiteralImpl]"Failed to send message: " + [CtVariableReadImpl]msg, [CtVariableReadImpl]e);

                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                    [CtSynchronizedImpl]synchronized([CtFieldReadImpl]mux) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sock == [CtFieldReadImpl][CtThisAccessImpl]this.sock)[CtBlockImpl]
                            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sock = [CtLiteralImpl]null;
                        [CtCommentImpl]// Connection has dead.

                        [CtInvocationImpl]clear();
                    }
                }
            } 
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
            [CtAssertImpl]assert [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.holdsLock([CtFieldReadImpl]mux);
            [CtInvocationImpl][CtFieldReadImpl]queue.clear();
            [CtAssignmentImpl][CtFieldWriteImpl]unackedMsg = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CountDownLatch forceLeaveLatch = [CtFieldReadImpl][CtThisAccessImpl]this.forceLeaveLatch;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]forceLeaveLatch != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.forceLeaveLatch = [CtLiteralImpl]null;
                [CtInvocationImpl][CtVariableReadImpl]forceLeaveLatch.countDown();
            }
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     */
    private class Reconnector extends [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiThread {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private volatile [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]boolean clientAck;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]boolean join;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.net.InetSocketAddress prevAddr;

        [CtConstructorImpl][CtJavaDocImpl]/**
         *
         * @param join
         * 		{@code True} if reconnects during join.
         * @param prevAddr
         * 		Address of the node, that this client was previously connected to.
         */
        protected Reconnector([CtParameterImpl][CtTypeReferenceImpl]boolean join, [CtParameterImpl][CtTypeReferenceImpl]java.net.InetSocketAddress prevAddr) [CtBlockImpl]{
            [CtInvocationImpl]super([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().name(), [CtLiteralImpl]"tcp-client-disco-reconnector", [CtFieldReadImpl]log);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.join = [CtVariableReadImpl]join;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.prevAddr = [CtVariableReadImpl]prevAddr;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         */
        public [CtTypeReferenceImpl]void cancel() [CtBlockImpl]{
            [CtInvocationImpl]interrupt();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream = [CtFieldReadImpl][CtThisAccessImpl]this.sockStream;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sockStream != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtInvocationImpl][CtVariableReadImpl]sockStream.socket());

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]void body() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean success = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Exception err = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]long timeout = [CtConditionalImpl]([CtFieldReadImpl]join) ? [CtFieldReadImpl]spi.joinTimeout : [CtFieldReadImpl]spi.netTimeout;
            [CtLocalVariableImpl][CtTypeReferenceImpl]long startNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Started reconnect process [join=" + [CtFieldReadImpl]join) + [CtLiteralImpl]", timeout=") + [CtVariableReadImpl]timeout) + [CtLiteralImpl]']');

            [CtTryImpl]try [CtBlockImpl]{
                [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T2<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream, [CtTypeReferenceImpl]java.lang.Boolean> joinRes = [CtInvocationImpl]joinTopology([CtFieldReadImpl]prevAddr, [CtVariableReadImpl]timeout, [CtLiteralImpl]null, [CtLiteralImpl]null);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]joinRes == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtIfImpl]if ([CtFieldReadImpl]join) [CtBlockImpl]{
                            [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Join process timed out, connection failed and " + [CtLiteralImpl]"failed to reconnect (consider increasing 'joinTimeout' configuration property) ") + [CtLiteralImpl]"[joinTimeout=") + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]']'));
                        } else[CtBlockImpl]
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to reconnect to cluster (consider increasing 'networkTimeout'" + [CtLiteralImpl]" configuration property) [networkTimeout=") + [CtFieldReadImpl]spi.netTimeout) + [CtLiteralImpl]']');

                        [CtReturnImpl]return;
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]sockStream = [CtInvocationImpl][CtVariableReadImpl]joinRes.get1();
                    [CtAssignmentImpl][CtFieldWriteImpl]clientAck = [CtInvocationImpl][CtVariableReadImpl]joinRes.get2();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock = [CtInvocationImpl][CtFieldReadImpl]sockStream.socket();
                    [CtIfImpl]if ([CtInvocationImpl]isInterrupted())[CtBlockImpl]
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InterruptedException();

                    [CtLocalVariableImpl][CtTypeReferenceImpl]int oldTimeout = [CtLiteralImpl]0;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]oldTimeout = [CtInvocationImpl][CtVariableReadImpl]sock.getSoTimeout();
                        [CtInvocationImpl][CtVariableReadImpl]sock.setSoTimeout([CtTypeAccessImpl](([CtTypeReferenceImpl]int) (spi.netTimeout)));
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl][CtFieldReadImpl]sockStream.stream();
                        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sock.getKeepAlive() && [CtInvocationImpl][CtVariableReadImpl]sock.getTcpNoDelay() : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Socket wasn't configured properly:" + [CtLiteralImpl]" KeepAlive ") + [CtInvocationImpl][CtVariableReadImpl]sock.getKeepAlive()) + [CtLiteralImpl]" TcpNoDelay ") + [CtInvocationImpl][CtVariableReadImpl]sock.getTcpNoDelay();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage> msgs = [CtLiteralImpl]null;
                        [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl]isInterrupted()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.unmarshal([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtVariableReadImpl]in, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage res = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage) (msg));
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]res.creatorNodeId().equals([CtInvocationImpl]getLocalNodeId())) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Received reconnect response [success=" + [CtInvocationImpl][CtVariableReadImpl]res.success()) + [CtLiteralImpl]", msg=") + [CtVariableReadImpl]msg) + [CtLiteralImpl]']');

                                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.success()) [CtBlockImpl]{
                                        [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtVariableReadImpl]res);
                                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msgs != [CtLiteralImpl]null) [CtBlockImpl]{
                                            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg0 : [CtVariableReadImpl]msgs)[CtBlockImpl]
                                                [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtVariableReadImpl]msg0);

                                        }
                                        [CtAssignmentImpl][CtVariableWriteImpl]success = [CtLiteralImpl]true;
                                        [CtReturnImpl]return;
                                    } else[CtBlockImpl]
                                        [CtReturnImpl]return;

                                }
                            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]spi.ensured([CtVariableReadImpl]msg)) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msgs == [CtLiteralImpl]null)[CtBlockImpl]
                                    [CtAssignmentImpl][CtVariableWriteImpl]msgs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

                                [CtInvocationImpl][CtVariableReadImpl]msgs.add([CtVariableReadImpl]msg);
                            }
                        } 
                    }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]sock);
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Reconnect error [join=" + [CtFieldReadImpl]join) + [CtLiteralImpl]", timeout=") + [CtVariableReadImpl]timeout) + [CtLiteralImpl]']', [CtVariableReadImpl]e);

                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]timeout > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisSinceNanos([CtVariableReadImpl]startNanos) > [CtVariableReadImpl]timeout)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtConditionalImpl]([CtFieldReadImpl]join) ? [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to connect to cluster (consider increasing 'joinTimeout' " + [CtLiteralImpl]"configuration  property) [joinTimeout=") + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]", err=") + [CtVariableReadImpl]e) + [CtLiteralImpl]']' : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to reconnect to cluster (consider increasing 'networkTimeout' " + [CtLiteralImpl]"configuration  property) [networkTimeout=") + [CtFieldReadImpl]spi.netTimeout) + [CtLiteralImpl]", err=") + [CtVariableReadImpl]e) + [CtLiteralImpl]']';
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.warn([CtFieldReadImpl]log, [CtVariableReadImpl]msg);
                            [CtThrowImpl]throw [CtVariableReadImpl]e;
                        } else[CtBlockImpl]
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtLiteralImpl]"Failed to reconnect to cluster (will retry): " + [CtVariableReadImpl]e);

                    } finally [CtBlockImpl]{
                        [CtIfImpl]if ([CtVariableReadImpl]success)[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]sock.setSoTimeout([CtVariableReadImpl]oldTimeout);

                    }
                } 
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]err = [CtVariableReadImpl]e;
                [CtAssignmentImpl][CtVariableWriteImpl]success = [CtLiteralImpl]false;
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtLiteralImpl]"Failed to reconnect", [CtVariableReadImpl]e);
            } finally [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]success) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sockStream = [CtFieldReadImpl][CtThisAccessImpl]this.sockStream;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sockStream != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtInvocationImpl][CtVariableReadImpl]sockStream.socket());

                    [CtIfImpl]if ([CtFieldReadImpl]join)[CtBlockImpl]
                        [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to connect to cluster, connection failed and failed " + [CtLiteralImpl]"to reconnect.", [CtVariableReadImpl]err));
                    else[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]msgWorker.addMessage([CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_RECONNECT_FAILED);

                }
            }
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Message worker.
     */
    protected class MessageWorker extends [CtTypeReferenceImpl]org.apache.ignite.internal.util.worker.GridWorker {
        [CtFieldImpl][CtJavaDocImpl]/**
         * Message queue.
         */
        private final [CtTypeReferenceImpl]java.util.concurrent.BlockingDeque<[CtTypeReferenceImpl]java.lang.Object> queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.LinkedBlockingDeque<>();

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream currSock;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.Reconnector reconnector;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]boolean nodeAdded;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]long lastReconnectTsNanos = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]long currentReconnectDelay = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtConstructorImpl][CtJavaDocImpl]/**
         *
         * @param log
         * 		Logger.
         */
        private MessageWorker([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteLogger log) [CtBlockImpl]{
            [CtInvocationImpl]super([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().name(), [CtLiteralImpl]"tcp-client-disco-msg-worker", [CtVariableReadImpl]log, [CtInvocationImpl]getWorkersRegistry());
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]void body() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING;
            [CtInvocationImpl]updateHeartbeat();
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]tryJoin();
                [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                    [CtInvocationImpl]onIdle();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object msg;
                    [CtInvocationImpl]blockingSectionBegin();
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]msg = [CtInvocationImpl][CtFieldReadImpl]queue.take();
                    } finally [CtBlockImpl]{
                        [CtInvocationImpl]blockingSectionEnd();
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.JoinTimeout) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int joinCnt0 = [CtFieldReadImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.JoinTimeout) (msg)).joinCnt;
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]joinCnt == [CtVariableReadImpl]joinCnt0) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING) [CtBlockImpl]{
                                [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Join process timed out, did not receive response for " + [CtLiteralImpl]"join request (consider increasing 'joinTimeout' configuration property) ") + [CtLiteralImpl]"[joinTimeout=") + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]", sock=") + [CtFieldReadImpl]currSock) + [CtLiteralImpl]']'));
                                [CtBreakImpl]break;
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to reconnect, local node segmented " + [CtLiteralImpl]"[joinTimeout=") + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]']');

                                [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED;
                                [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_SEGMENTED, [CtFieldReadImpl]topVer, [CtFieldReadImpl]locNode, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
                            }
                        }
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_STOP) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean connected = [CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED;
                        [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STOPPED;
                        [CtAssertImpl]assert [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]connected && [CtBinaryOperatorImpl]([CtFieldReadImpl]currSock != [CtLiteralImpl]null)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage leftMsg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage([CtInvocationImpl]getLocalNodeId());
                            [CtInvocationImpl][CtVariableReadImpl]leftMsg.client([CtLiteralImpl]true);
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.Span rootSpan = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.create([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessagesTable.traceName([CtInvocationImpl][CtVariableReadImpl]leftMsg.getClass())).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.locNode.id().toString()).addTag([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.SpanTags.tag([CtTypeAccessImpl]SpanTags.EVENT_NODE, [CtTypeAccessImpl]SpanTags.CONSISTENT_ID), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.locNode.consistentId().toString()).addLog([CtLambdaImpl]() -> [CtLiteralImpl]"Created");
                            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]leftMsg.spanContainer().serializedSpanBytes([CtInvocationImpl][CtFieldReadImpl]tracing.serialize([CtVariableReadImpl]rootSpan));
                            [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtVariableReadImpl]leftMsg);
                            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rootSpan.addLog([CtLambdaImpl]() -> [CtLiteralImpl]"Sent").end();
                        } else[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]leaveLatch.countDown();

                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_RECONNECT) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]reconnector != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]reconnector.cancel();
                                [CtInvocationImpl][CtFieldReadImpl]reconnector.join();
                                [CtAssignmentImpl][CtFieldWriteImpl]reconnector = [CtLiteralImpl]null;
                            }
                            [CtInvocationImpl][CtFieldReadImpl]sockWriter.forceLeave();
                            [CtInvocationImpl][CtFieldReadImpl]sockReader.forceStopRead();
                            [CtAssignmentImpl][CtFieldWriteImpl]currSock = [CtLiteralImpl]null;
                            [CtInvocationImpl][CtFieldReadImpl]queue.clear();
                            [CtInvocationImpl]onDisconnected();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID newId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndWarn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Local node will try to reconnect to cluster with new id due " + [CtLiteralImpl]"to network problems [newId=") + [CtVariableReadImpl]newId) + [CtLiteralImpl]", prevId=") + [CtInvocationImpl][CtFieldReadImpl]locNode.id()) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');
                            [CtInvocationImpl][CtFieldReadImpl]locNode.onClientDisconnected([CtVariableReadImpl]newId);
                            [CtInvocationImpl]throttleClientReconnect();
                            [CtInvocationImpl]tryJoin();
                        }
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage) (msg)).failedNodeId().equals([CtInvocationImpl][CtFieldReadImpl]locNode.id())) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage msg0 = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage) (msg));
                        [CtAssertImpl]assert [CtInvocationImpl][CtVariableReadImpl]msg0.force() : [CtVariableReadImpl]msg0;
                        [CtAssignmentImpl][CtFieldWriteImpl]forceFailMsg = [CtVariableReadImpl]msg0;
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketClosedMessage) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketClosedMessage) (msg)).sock == [CtFieldReadImpl]currSock) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.Socket sock = [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]currSock.sock;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.InetSocketAddress prevAddr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.InetSocketAddress([CtInvocationImpl][CtVariableReadImpl]sock.getInetAddress(), [CtInvocationImpl][CtVariableReadImpl]sock.getPort());
                            [CtAssignmentImpl][CtFieldWriteImpl]currSock = [CtLiteralImpl]null;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean join = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]joinLatch.getCount() > [CtLiteralImpl]0;
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping() || [CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED)) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]leaveLatch.countDown();
                                [CtIfImpl]if ([CtVariableReadImpl]join) [CtBlockImpl]{
                                    [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtLiteralImpl]"Failed to connect to cluster: socket closed."));
                                    [CtBreakImpl]break;
                                }
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]forceFailMsg != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Connection closed, local node received force fail message, " + [CtLiteralImpl]"will not try to restore connection");
                                }
                                [CtInvocationImpl][CtFieldReadImpl]queue.addFirst([CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_RECONNECT_FAILED);
                            } else [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Connection closed, will try to restore connection.");

                                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtFieldReadImpl]reconnector == [CtLiteralImpl]null;
                                [CtAssignmentImpl][CtFieldWriteImpl]reconnector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.Reconnector([CtVariableReadImpl]join, [CtVariableReadImpl]prevAddr);
                                [CtInvocationImpl][CtFieldReadImpl]reconnector.start();
                            }
                        }
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SPI_RECONNECT_FAILED) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]reconnector != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]reconnector.cancel();
                            [CtInvocationImpl][CtFieldReadImpl]reconnector.join();
                            [CtAssignmentImpl][CtFieldWriteImpl]reconnector = [CtLiteralImpl]null;
                        } else[CtBlockImpl]
                            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtFieldReadImpl]forceFailMsg != [CtLiteralImpl]null;

                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]spi.isClientReconnectDisabled()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]state != [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED) && [CtBinaryOperatorImpl]([CtFieldReadImpl]state != [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STOPPED)) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]forceFailMsg != [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndWarn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Local node was dropped from cluster due to network problems " + [CtLiteralImpl]"[nodeInitiatedFail=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.creatorNodeId()) + [CtLiteralImpl]", msg=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.warning()) + [CtLiteralImpl]']');
                                }
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to restore closed connection, reconnect disabled, " + [CtLiteralImpl]"local node segmented [networkTimeout=") + [CtFieldReadImpl]spi.netTimeout) + [CtLiteralImpl]']');
                                }
                                [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED;
                                [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_SEGMENTED, [CtFieldReadImpl]topVer, [CtFieldReadImpl]locNode, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
                            }
                        } else [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING) || [CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED)) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to restore closed connection, will try to reconnect " + [CtLiteralImpl]"[networkTimeout=") + [CtFieldReadImpl]spi.netTimeout) + [CtLiteralImpl]", joinTimeout=") + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]", failMsg=") + [CtFieldReadImpl]forceFailMsg) + [CtLiteralImpl]']');
                                }
                                [CtInvocationImpl]onDisconnected();
                            }
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID newId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]forceFailMsg != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]long delay = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.IgniteSystemProperties.getLong([CtTypeAccessImpl]org.apache.ignite.IgniteSystemProperties.IGNITE_DISCO_FAILED_CLIENT_RECONNECT_DELAY, [CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi.DFLT_DISCO_FAILED_CLIENT_RECONNECT_DELAY);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]delay > [CtLiteralImpl]0) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndWarn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Local node was dropped from cluster due to network problems, " + [CtLiteralImpl]"will try to reconnect with new id after ") + [CtVariableReadImpl]delay) + [CtLiteralImpl]"ms (reconnect delay ") + [CtLiteralImpl]"can be changed using IGNITE_DISCO_FAILED_CLIENT_RECONNECT_DELAY system ") + [CtLiteralImpl]"property) [") + [CtLiteralImpl]"newId=") + [CtVariableReadImpl]newId) + [CtLiteralImpl]", prevId=") + [CtInvocationImpl][CtFieldReadImpl]locNode.id()) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]", nodeInitiatedFail=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.creatorNodeId()) + [CtLiteralImpl]", msg=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.warning()) + [CtLiteralImpl]']');
                                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtVariableReadImpl]delay);
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndWarn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Local node was dropped from cluster due to network problems, " + [CtLiteralImpl]"will try to reconnect with new id [") + [CtLiteralImpl]"newId=") + [CtVariableReadImpl]newId) + [CtLiteralImpl]", prevId=") + [CtInvocationImpl][CtFieldReadImpl]locNode.id()) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]", nodeInitiatedFail=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.creatorNodeId()) + [CtLiteralImpl]", msg=") + [CtInvocationImpl][CtFieldReadImpl]forceFailMsg.warning()) + [CtLiteralImpl]']');
                                }
                                [CtAssignmentImpl][CtFieldWriteImpl]forceFailMsg = [CtLiteralImpl]null;
                            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Client node disconnected from cluster, will try to reconnect with new id " + [CtLiteralImpl]"[newId=") + [CtVariableReadImpl]newId) + [CtLiteralImpl]", prevId=") + [CtInvocationImpl][CtFieldReadImpl]locNode.id()) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');
                            }
                            [CtInvocationImpl][CtFieldReadImpl]locNode.onClientDisconnected([CtVariableReadImpl]newId);
                            [CtInvocationImpl]tryJoin();
                        }
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage discoMsg = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage) (msg));
                        [CtIfImpl]if ([CtInvocationImpl]joining()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException err = [CtLiteralImpl]null;
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]discoMsg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryDuplicateIdMessage)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]err = [CtInvocationImpl][CtFieldReadImpl]spi.duplicateIdError([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryDuplicateIdMessage) (msg)));
                            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]discoMsg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAuthFailedMessage)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]err = [CtInvocationImpl][CtFieldReadImpl]spi.authenticationFailedError([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAuthFailedMessage) (msg)));
                            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]discoMsg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCheckFailedMessage)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]err = [CtInvocationImpl][CtFieldReadImpl]spi.checkFailedError([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCheckFailedMessage) (msg)));

                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]err != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtLiteralImpl]"Failed to reconnect, segment local node.", [CtVariableReadImpl]err);
                                    [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED;
                                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_SEGMENTED, [CtFieldReadImpl]topVer, [CtFieldReadImpl]locNode, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
                                } else[CtBlockImpl]
                                    [CtInvocationImpl]joinError([CtVariableReadImpl]err);

                                [CtInvocationImpl]cancel();
                                [CtBreakImpl]break;
                            }
                        }
                        [CtInvocationImpl]processDiscoveryMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage) (msg)));
                    }
                } 
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignored) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().interrupt();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx)[CtBlockImpl]
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx) ([CtFieldReadImpl]spi.ignite())).context().failure().process([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.failure.FailureContext([CtFieldReadImpl]FailureType.CRITICAL_ERROR, [CtVariableReadImpl]t));

            } finally [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream currSock = [CtFieldReadImpl][CtThisAccessImpl]this.currSock;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currSock != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtInvocationImpl][CtVariableReadImpl]currSock.socket());

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]joinLatch.getCount() > [CtLiteralImpl]0)[CtBlockImpl]
                    [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtLiteralImpl]"Some error in join process."));
                [CtCommentImpl]// This should not occur.

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]reconnector != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]reconnector.cancel();
                    [CtInvocationImpl][CtFieldReadImpl]reconnector.join();
                }
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Wait random delay before trying to reconnect. Delay will grow exponentially every time client is forced to
         * reconnect, but only if all these reconnections happened in small period of time (2 minutes). Maximum delay
         * could be configured with {@link IgniteSpiAdapter#clientFailureDetectionTimeout()}, default value is
         * {@link IgniteConfiguration#DFLT_CLIENT_FAILURE_DETECTION_TIMEOUT}.
         *
         * @throws InterruptedException
         * 		If thread is interrupted.
         */
        private [CtTypeReferenceImpl]void throttleClientReconnect() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]currentReconnectDelay == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.millisSinceNanos([CtFieldReadImpl]lastReconnectTsNanos) > [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.CLIENT_THROTTLE_RECONNECT_RESET_TIMEOUT))[CtBlockImpl]
                [CtAssignmentImpl][CtFieldWriteImpl]currentReconnectDelay = [CtLiteralImpl]0;
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentReconnectDelay == [CtLiteralImpl]0)[CtBlockImpl]
                [CtAssignmentImpl][CtFieldWriteImpl]currentReconnectDelay = [CtLiteralImpl]200;
            else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long maxDelay = [CtConditionalImpl]([CtInvocationImpl][CtFieldReadImpl]spi.failureDetectionTimeoutEnabled()) ? [CtInvocationImpl][CtFieldReadImpl]spi.clientFailureDetectionTimeout() : [CtFieldReadImpl]org.apache.ignite.configuration.IgniteConfiguration.DFLT_CLIENT_FAILURE_DETECTION_TIMEOUT;
                [CtAssignmentImpl][CtFieldWriteImpl]currentReconnectDelay = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]maxDelay, [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]currentReconnectDelay * [CtLiteralImpl]1.5)));
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentReconnectDelay != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.ThreadLocalRandom random = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.ThreadLocalRandom.current();
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtInvocationImpl][CtVariableReadImpl]random.nextLong([CtBinaryOperatorImpl][CtFieldReadImpl]currentReconnectDelay / [CtLiteralImpl]2, [CtFieldReadImpl]currentReconnectDelay));
            }
            [CtAssignmentImpl][CtFieldWriteImpl]lastReconnectTsNanos = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]void onDisconnected() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED;
            [CtAssignmentImpl][CtFieldWriteImpl]nodeAdded = [CtLiteralImpl]false;
            [CtInvocationImpl][CtFieldReadImpl]delayDiscoData.clear();
            [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_CLIENT_NODE_DISCONNECTED, [CtFieldReadImpl]topVer, [CtFieldReadImpl]locNode, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteClientDisconnectedCheckedException err = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.IgniteClientDisconnectedCheckedException([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtLiteralImpl]"Failed to ping node, " + [CtLiteralImpl]"client node disconnected.");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean>> e : [CtInvocationImpl][CtFieldReadImpl]pingFuts.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> fut = [CtInvocationImpl][CtVariableReadImpl]e.getValue();
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]pingFuts.remove([CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtVariableReadImpl]fut))[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]fut.onDone([CtVariableReadImpl]err);

            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @throws InterruptedException
         * 		If interrupted.
         */
        private [CtTypeReferenceImpl]void tryJoin() throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED) || [CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING) : [CtFieldReadImpl]state;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean join = [CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING;
            [CtUnaryOperatorImpl][CtFieldWriteImpl]joinCnt++;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.typedef.T2<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream, [CtTypeReferenceImpl]java.lang.Boolean> joinRes;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]joinRes = [CtInvocationImpl]joinTopology([CtLiteralImpl]null, [CtTypeAccessImpl]spi.joinTimeout, [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                        [CtInvocationImpl]blockingSectionBegin();
                    }
                }, [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                        [CtInvocationImpl]blockingSectionEnd();
                    }
                });
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException e) [CtBlockImpl]{
                [CtInvocationImpl]joinError([CtVariableReadImpl]e);
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]joinRes == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]join)[CtBlockImpl]
                    [CtInvocationImpl]joinError([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.IgniteSpiException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Join process timed out (timeout = " + [CtFieldReadImpl]spi.joinTimeout) + [CtLiteralImpl]")."));
                else [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.SEGMENTED;
                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_SEGMENTED, [CtFieldReadImpl]topVer, [CtFieldReadImpl]locNode, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
                }
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]currSock = [CtInvocationImpl][CtVariableReadImpl]joinRes.get1();
            [CtInvocationImpl][CtFieldReadImpl]sockWriter.setSocket([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]joinRes.get1().socket(), [CtInvocationImpl][CtVariableReadImpl]joinRes.get2());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]spi.joinTimeout > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int joinCnt0 = [CtFieldReadImpl]joinCnt;
                [CtInvocationImpl][CtFieldReadImpl]executorService.schedule([CtLambdaImpl]() -> [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]queue.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.JoinTimeout([CtVariableReadImpl]joinCnt0));
                }, [CtTypeAccessImpl]spi.joinTimeout, [CtFieldReadImpl]java.util.concurrent.TimeUnit.MILLISECONDS);
            }
            [CtInvocationImpl][CtFieldReadImpl]sockReader.setSocket([CtInvocationImpl][CtVariableReadImpl]joinRes.get1(), [CtInvocationImpl][CtFieldReadImpl]locNode.clientRouterNodeId());
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        protected [CtTypeReferenceImpl]void processDiscoveryMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage msg) [CtBlockImpl]{
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]msg != [CtLiteralImpl]null;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]msg.verified() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]msg.senderNodeId() == [CtLiteralImpl]null);
            [CtInvocationImpl][CtFieldReadImpl]spi.startMessageProcess([CtVariableReadImpl]msg);
            [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onMessageProcessingStarted([CtVariableReadImpl]msg);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage)[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.messages().beforeSend([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage) (msg)));

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddedMessage)[CtBlockImpl]
                [CtInvocationImpl]processNodeAddedMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddedMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddFinishedMessage)[CtBlockImpl]
                [CtInvocationImpl]processNodeAddFinishedMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddFinishedMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage)[CtBlockImpl]
                [CtInvocationImpl]processNodeLeftMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage)[CtBlockImpl]
                [CtInvocationImpl]processNodeFailedMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryMetricsUpdateMessage)[CtBlockImpl]
                [CtInvocationImpl]processMetricsUpdateMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryMetricsUpdateMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage)[CtBlockImpl]
                [CtInvocationImpl]processClientReconnectMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage)[CtBlockImpl]
                [CtInvocationImpl]processCustomMessage([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingResponse)[CtBlockImpl]
                [CtInvocationImpl]processClientPingResponse([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingResponse) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingRequest)[CtBlockImpl]
                [CtInvocationImpl]processPingRequest();

            [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onMessageProcessingFinished([CtVariableReadImpl]msg);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage)[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.messages().finishProcessing([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage) (msg)));

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]spi.ensured([CtVariableReadImpl]msg) && [CtBinaryOperatorImpl]([CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage)))[CtBlockImpl]
                [CtAssignmentImpl][CtFieldWriteImpl]lastMsgId = [CtInvocationImpl][CtVariableReadImpl]msg.id();

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return {@code True} if client in process of join.
         */
        private [CtTypeReferenceImpl]boolean joining() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State state = [CtFieldReadImpl][CtThisAccessImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.this.state;
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.STARTING) || [CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return {@code True} if client disconnected.
         */
        private [CtTypeReferenceImpl]boolean disconnected() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.DISCONNECTED;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processNodeAddedMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddedMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping())[CtBlockImpl]
                [CtReturnImpl]return;

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtVariableReadImpl]msg.node();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID newNodeId = [CtInvocationImpl][CtVariableReadImpl]node.id();
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtVariableReadImpl]newNodeId)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]joining()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode> top = [CtInvocationImpl][CtVariableReadImpl]msg.topology();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]top != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]spi.gridStartTime = [CtInvocationImpl][CtVariableReadImpl]msg.gridStartTime();
                        [CtIfImpl]if ([CtInvocationImpl]disconnected())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]rmtNodes.clear();

                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode n : [CtVariableReadImpl]top) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]n.order() > [CtLiteralImpl]0)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]n.visible([CtLiteralImpl]true);

                            [CtInvocationImpl][CtFieldReadImpl]rmtNodes.put([CtInvocationImpl][CtVariableReadImpl]n.id(), [CtVariableReadImpl]n);
                        }
                        [CtInvocationImpl][CtFieldReadImpl]topHist.clear();
                        [CtAssignmentImpl][CtFieldWriteImpl]nodeAdded = [CtLiteralImpl]true;
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]msg.topologyHistory() != [CtLiteralImpl]null)[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]topHist.putAll([CtInvocationImpl][CtVariableReadImpl]msg.topologyHistory());

                    } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding node added message with empty topology: " + [CtVariableReadImpl]msg);

                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Discarding node added message (this message has already been processed) " + [CtLiteralImpl]"[msg=") + [CtVariableReadImpl]msg) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');

            } else [CtIfImpl]if ([CtInvocationImpl]nodeAdded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean topChanged = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]rmtNodes.putIfAbsent([CtVariableReadImpl]newNodeId, [CtVariableReadImpl]node) == [CtLiteralImpl]null;
                [CtIfImpl]if ([CtVariableReadImpl]topChanged) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Added new node to topology: " + [CtVariableReadImpl]node);

                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket dataPacket = [CtInvocationImpl][CtVariableReadImpl]msg.gridDiscoveryData();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dataPacket != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]dataPacket.hasJoiningNodeData()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl]joining())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]delayDiscoData.add([CtVariableReadImpl]dataPacket);
                        else[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]spi.onExchange([CtVariableReadImpl]dataPacket, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));

                    }
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Ignore topology message, local node not added to topology: " + [CtVariableReadImpl]msg);

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processNodeAddFinishedMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeAddFinishedMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping())[CtBlockImpl]
                [CtReturnImpl]return;

            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode node : [CtInvocationImpl]getRemoteNodes()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.id().equals([CtInvocationImpl][CtFieldReadImpl]locNode.clientRouterNodeId())) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Router node: " + [CtVariableReadImpl]node);

                        [CtBreakImpl]break;
                    }
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.nodeId())) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]joining()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket dataContainer = [CtInvocationImpl][CtVariableReadImpl]msg.clientDiscoData();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataContainer != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]spi.onExchange([CtVariableReadImpl]dataContainer, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));

                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]delayDiscoData.isEmpty()) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.DiscoveryDataPacket data : [CtFieldReadImpl]delayDiscoData)[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]spi.onExchange([CtVariableReadImpl]data, [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));

                        [CtInvocationImpl][CtFieldReadImpl]delayDiscoData.clear();
                    }
                    [CtInvocationImpl][CtFieldReadImpl]locNode.setAttributes([CtInvocationImpl][CtVariableReadImpl]msg.clientNodeAttributes());
                    [CtInvocationImpl][CtFieldReadImpl]locNode.visible([CtLiteralImpl]true);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long topVer = [CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion();
                    [CtInvocationImpl][CtFieldReadImpl]locNode.order([CtVariableReadImpl]topVer);
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Long> it = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]topHist.keySet().iterator(); [CtInvocationImpl][CtVariableReadImpl]it.hasNext();) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]it.next() >= [CtVariableReadImpl]topVer)[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]it.remove();

                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> nodes = [CtInvocationImpl]updateTopologyHistory([CtVariableReadImpl]topVer, [CtVariableReadImpl]msg);
                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_JOINED, [CtVariableReadImpl]topVer, [CtFieldReadImpl]locNode, [CtVariableReadImpl]nodes, [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]boolean disconnected = [CtInvocationImpl]disconnected();
                    [CtAssignmentImpl][CtFieldWriteImpl]state = [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED;
                    [CtIfImpl]if ([CtVariableReadImpl]disconnected) [CtBlockImpl]{
                        [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_CLIENT_NODE_RECONNECTED, [CtVariableReadImpl]topVer, [CtFieldReadImpl]locNode, [CtVariableReadImpl]nodes, [CtLiteralImpl]null);
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.quietAndWarn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Client node was reconnected after it was already considered " + [CtLiteralImpl]"failed by the server topology (this could happen after all servers restarted or due ") + [CtLiteralImpl]"to a long network outage between the client and servers). All continuous queries and ") + [CtLiteralImpl]"remote event listeners created by this client will be unsubscribed, consider ") + [CtLiteralImpl]"listening to EVT_CLIENT_NODE_RECONNECTED event to restore them.");
                    }
                    [CtInvocationImpl][CtFieldReadImpl]joinErr.set([CtLiteralImpl]null);
                    [CtInvocationImpl][CtFieldReadImpl]joinLatch.countDown();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Discarding node add finished message (this message has already been processed) " + [CtLiteralImpl]"[msg=") + [CtVariableReadImpl]msg) + [CtLiteralImpl]", locNode=") + [CtFieldReadImpl]locNode) + [CtLiteralImpl]']');

            } else [CtIfImpl]if ([CtInvocationImpl]nodeAdded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtInvocationImpl][CtVariableReadImpl]msg.nodeId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Discarding node add finished message since node is not found [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]']');

                    [CtReturnImpl]return;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean evt = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]long topVer = [CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion();
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]topVer > [CtLiteralImpl]0 : [CtVariableReadImpl]msg;
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]node.visible()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]node.order([CtVariableReadImpl]topVer);
                    [CtInvocationImpl][CtVariableReadImpl]node.visible([CtLiteralImpl]true);
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]spi.locNodeVer.equals([CtInvocationImpl][CtVariableReadImpl]node.version()))[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]node.version([CtTypeAccessImpl]spi.locNodeVer);

                    [CtAssignmentImpl][CtVariableWriteImpl]evt = [CtLiteralImpl]true;
                } else [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Skip node join event, node already joined [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", node=") + [CtVariableReadImpl]node) + [CtLiteralImpl]']');

                    [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]node.order() == [CtVariableReadImpl]topVer : [CtVariableReadImpl]node;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top = [CtInvocationImpl]updateTopologyHistory([CtVariableReadImpl]topVer, [CtVariableReadImpl]msg);
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]top != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]top.contains([CtVariableReadImpl]node) : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Topology does not contain node [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]", node=") + [CtVariableReadImpl]node) + [CtLiteralImpl]", top=") + [CtVariableReadImpl]top) + [CtLiteralImpl]']';
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state != [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding node add finished message (join process is not finished): " + [CtVariableReadImpl]msg);

                    [CtReturnImpl]return;
                }
                [CtIfImpl]if ([CtVariableReadImpl]evt) [CtBlockImpl]{
                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_JOINED, [CtVariableReadImpl]topVer, [CtVariableReadImpl]node, [CtVariableReadImpl]top, [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer());
                    [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onNodeJoined();
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Ignore topology message, local node not added to topology: " + [CtVariableReadImpl]msg);

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processNodeLeftMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeLeftMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received node left message for local node: " + [CtVariableReadImpl]msg);

                [CtInvocationImpl][CtFieldReadImpl]leaveLatch.countDown();
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping())[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtIfImpl]if ([CtInvocationImpl]nodeAdded()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.remove([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Discarding node left message since node is not found [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]']');

                        [CtReturnImpl]return;
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top = [CtInvocationImpl]updateTopologyHistory([CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion(), [CtVariableReadImpl]msg);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state != [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding node left message (join process is not finished): " + [CtVariableReadImpl]msg);

                        [CtReturnImpl]return;
                    }
                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_LEFT, [CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion(), [CtVariableReadImpl]node, [CtVariableReadImpl]top, [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer());
                    [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onNodeLeft();
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Ignore topology message, local node not added to topology: " + [CtVariableReadImpl]msg);

            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return {@code True} if received node added message for local node.
         */
        private [CtTypeReferenceImpl]boolean nodeAdded() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]nodeAdded;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processNodeFailedMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryNodeFailedMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) && [CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.failedNodeId())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]leaveLatch.getCount() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Remote node fail this node while node is stopping [locNode=" + [CtInvocationImpl]getLocalNodeId()) + [CtLiteralImpl]", rmtNode=") + [CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId()) + [CtLiteralImpl]']');

                        [CtInvocationImpl][CtFieldReadImpl]leaveLatch.countDown();
                    }
                }
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl]nodeAdded()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.remove([CtInvocationImpl][CtVariableReadImpl]msg.failedNodeId());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Discarding node failed message since node is not found [msg=" + [CtVariableReadImpl]msg) + [CtLiteralImpl]']');

                        [CtReturnImpl]return;
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top = [CtInvocationImpl]updateTopologyHistory([CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion(), [CtVariableReadImpl]msg);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state != [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding node failed message (join process is not finished): " + [CtVariableReadImpl]msg);

                        [CtReturnImpl]return;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]msg.warning() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode creatorNode = [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId());
                        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.warn([CtFieldReadImpl]log, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Received EVT_NODE_FAILED event with warning [" + [CtLiteralImpl]"nodeInitiatedEvt=") + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]creatorNode != [CtLiteralImpl]null ? [CtVariableReadImpl]creatorNode : [CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) + [CtLiteralImpl]", msg=") + [CtInvocationImpl][CtVariableReadImpl]msg.warning()) + [CtLiteralImpl]']');
                    }
                    [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_FAILED, [CtInvocationImpl][CtVariableReadImpl]msg.topologyVersion(), [CtVariableReadImpl]node, [CtVariableReadImpl]top, [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer());
                    [CtInvocationImpl][CtTypeAccessImpl]spi.stats.onNodeFailed();
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Ignore topology message, local node not added to topology: " + [CtVariableReadImpl]msg);

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processMetricsUpdateMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryMetricsUpdateMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping())[CtBlockImpl]
                [CtReturnImpl]return;

            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) [CtBlockImpl]{
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]msg.senderNodeId() != [CtLiteralImpl]null;
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received metrics response: " + [CtVariableReadImpl]msg);

            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]msg.hasMetrics())[CtBlockImpl]
                [CtInvocationImpl]processMsgCacheMetrics([CtVariableReadImpl]msg, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime());

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processClientReconnectMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientReconnectMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.getSpiContext().isStopping())[CtBlockImpl]
                [CtReturnImpl]return;

            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getLocalNodeId().equals([CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId())) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]reconnector != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssertImpl]assert [CtInvocationImpl][CtVariableReadImpl]msg.success() : [CtVariableReadImpl]msg;
                    [CtAssignmentImpl][CtFieldWriteImpl]currSock = [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]reconnector.sockStream;
                    [CtInvocationImpl][CtFieldReadImpl]sockWriter.setSocket([CtInvocationImpl][CtFieldReadImpl]currSock.socket(), [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]reconnector.clientAck);
                    [CtInvocationImpl][CtFieldReadImpl]sockReader.setSocket([CtFieldReadImpl]currSock, [CtInvocationImpl][CtFieldReadImpl]locNode.clientRouterNodeId());
                    [CtAssignmentImpl][CtFieldWriteImpl]reconnector = [CtLiteralImpl]null;
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage pendingMsg : [CtInvocationImpl][CtVariableReadImpl]msg.pendingMessages()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Process pending message on reconnect [msg=" + [CtVariableReadImpl]pendingMsg) + [CtLiteralImpl]']');

                        [CtInvocationImpl]processDiscoveryMessage([CtVariableReadImpl]pendingMsg);
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]joinLatch.getCount() > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]msg.success()) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage pendingMsg : [CtInvocationImpl][CtVariableReadImpl]msg.pendingMessages()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Process pending message on connect [msg=" + [CtVariableReadImpl]pendingMsg) + [CtLiteralImpl]']');

                            [CtInvocationImpl]processDiscoveryMessage([CtVariableReadImpl]pendingMsg);
                        }
                        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]joinLatch.getCount() == [CtLiteralImpl]0 : [CtVariableReadImpl]msg;
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding reconnect message, reconnect is completed: " + [CtVariableReadImpl]msg);

            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Discarding reconnect message for another client: " + [CtVariableReadImpl]msg);

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processCustomMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]state == [CtFieldReadImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.State.CONNECTED) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiListener lsnr = [CtFieldReadImpl]spi.lsnr;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lsnr != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID nodeId = [CtInvocationImpl][CtVariableReadImpl]msg.creatorNodeId();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtInvocationImpl]getLocalNodeId())) ? [CtFieldReadImpl]locNode : [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]node.visible()) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiCustomMessage msgObj = [CtInvocationImpl][CtVariableReadImpl]msg.message([CtInvocationImpl][CtFieldReadImpl]spi.marshaller(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.resolveClassLoader([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]spi.ignite().configuration()));
                            [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]org.apache.ignite.spi.discovery.tcp.EVT_DISCOVERY_CUSTOM_EVT, [CtFieldReadImpl]topVer, [CtVariableReadImpl]node, [CtInvocationImpl]allVisibleNodes(), [CtVariableReadImpl]msgObj, [CtInvocationImpl][CtVariableReadImpl]msg.spanContainer());
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.error([CtFieldReadImpl]log, [CtLiteralImpl]"Failed to unmarshal discovery custom message.", [CtVariableReadImpl]e);
                        }
                    } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received metrics from unknown node: " + [CtVariableReadImpl]nodeId);

                }
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        private [CtTypeReferenceImpl]void processClientPingResponse([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryClientPingResponse msg) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.future.GridFutureAdapter<[CtTypeReferenceImpl]java.lang.Boolean> fut = [CtInvocationImpl][CtFieldReadImpl]pingFuts.remove([CtInvocationImpl][CtVariableReadImpl]msg.nodeToPing());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fut != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]fut.onDone([CtInvocationImpl][CtVariableReadImpl]msg.result());

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Router want to ping this client.
         */
        private [CtTypeReferenceImpl]void processPingRequest() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingResponse res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryPingResponse([CtInvocationImpl]getLocalNodeId());
            [CtInvocationImpl][CtVariableReadImpl]res.client([CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]sockWriter.sendMessage([CtVariableReadImpl]res);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param nodeId
         * 		Node ID.
         * @param metrics
         * 		Metrics.
         * @param cacheMetrics
         * 		Cache metrics.
         * @param tsNanos
         * 		Timestamp as returned by {@link System#nanoTime()}.
         */
        private [CtTypeReferenceImpl]void updateMetrics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterMetrics metrics, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]org.apache.ignite.cache.CacheMetrics> cacheMetrics, [CtParameterImpl][CtTypeReferenceImpl]long tsNanos) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isLocDaemon = [CtInvocationImpl][CtTypeAccessImpl]spi.locNode.isDaemon();
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]nodeId != [CtLiteralImpl]null;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]metrics != [CtLiteralImpl]null;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]isLocDaemon || [CtBinaryOperatorImpl]([CtVariableReadImpl]cacheMetrics != [CtLiteralImpl]null);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode node = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtInvocationImpl]getLocalNodeId())) ? [CtFieldReadImpl]locNode : [CtInvocationImpl][CtFieldReadImpl]rmtNodes.get([CtVariableReadImpl]nodeId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]node.visible()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]node.setMetrics([CtVariableReadImpl]metrics);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isLocDaemon)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]node.setCacheMetrics([CtVariableReadImpl]cacheMetrics);

                [CtInvocationImpl][CtVariableReadImpl]node.lastUpdateTimeNanos([CtVariableReadImpl]tsNanos);
                [CtInvocationImpl]notifyDiscovery([CtTypeAccessImpl]EventType.EVT_NODE_METRICS_UPDATED, [CtFieldReadImpl]topVer, [CtVariableReadImpl]node, [CtInvocationImpl]allVisibleNodes(), [CtLiteralImpl]null);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtBinaryOperatorImpl][CtLiteralImpl]"Received metrics from unknown node: " + [CtVariableReadImpl]nodeId);

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param type
         * 		Event type.
         * @param topVer
         * 		Topology version.
         * @param node
         * 		Node.
         * @param top
         * 		Topology snapshot.
         * @param spanContainer
         * 		Span container.
         */
        private [CtTypeReferenceImpl]void notifyDiscovery([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]long topVer, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode node, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.SpanContainer spanContainer) [CtBlockImpl]{
            [CtInvocationImpl]notifyDiscovery([CtVariableReadImpl]type, [CtVariableReadImpl]topVer, [CtVariableReadImpl]node, [CtVariableReadImpl]top, [CtLiteralImpl]null, [CtVariableReadImpl]spanContainer);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param type
         * 		Event type.
         * @param topVer
         * 		Topology version.
         * @param node
         * 		Node.
         * @param top
         * 		Topology snapshot.
         * @param data
         * 		Optional custom message data.
         */
        private [CtTypeReferenceImpl]void notifyDiscovery([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]long topVer, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode node, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode> top, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
        [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiCustomMessage data, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.SpanContainer spanContainer) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoverySpiListener lsnr = [CtFieldReadImpl]spi.lsnr;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.DebugLogger debugLog = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]EventType.EVT_NODE_METRICS_UPDATED) ? [CtFieldReadImpl]traceLog : [CtFieldReadImpl][CtThisAccessImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.this.debugLog;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lsnr != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]debugLog.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]debugLog.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Discovery notification [node=" + [CtVariableReadImpl]node) + [CtLiteralImpl]", type=") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.gridEventName([CtVariableReadImpl]type)) + [CtLiteralImpl]", topVer=") + [CtVariableReadImpl]topVer) + [CtLiteralImpl]']');

                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lsnr.onDiscovery([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.DiscoveryNotification([CtVariableReadImpl]type, [CtVariableReadImpl]topVer, [CtVariableReadImpl]node, [CtVariableReadImpl]top, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>([CtFieldReadImpl]topHist), [CtVariableReadImpl]data, [CtVariableReadImpl]spanContainer)).get();
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]debugLog.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]debugLog.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Skipped discovery notification [node=" + [CtVariableReadImpl]node) + [CtLiteralImpl]", type=") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.gridEventName([CtVariableReadImpl]type)) + [CtLiteralImpl]", topVer=") + [CtVariableReadImpl]topVer) + [CtLiteralImpl]']');

        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param msg
         * 		Message.
         */
        [CtTypeReferenceImpl]void addMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object msg) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// TODO: https://ggsystems.atlassian.net/browse/GG-22502
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage) && [CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage tMsg = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessage) (msg));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryAbstractMessage) (msg)).verified()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tMsg.spanContainer().serializedSpanBytes() == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.tracing.Span rootSpan = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tracing.create([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.tracing.messages.TraceableMessagesTable.traceName([CtInvocationImpl][CtVariableReadImpl]tMsg.getClass())).end();
                    [CtInvocationImpl][CtCommentImpl]// This root span will be parent both from local and remote nodes.
                    [CtInvocationImpl][CtVariableReadImpl]tMsg.spanContainer().serializedSpanBytes([CtInvocationImpl][CtFieldReadImpl]tracing.serialize([CtVariableReadImpl]rootSpan));
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]queue.add([CtVariableReadImpl]msg);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return Queue size.
         */
        [CtTypeReferenceImpl]int queueSize() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]queue.size();
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     */
    private static class JoinTimeout {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private [CtTypeReferenceImpl]int joinCnt;

        [CtConstructorImpl][CtJavaDocImpl]/**
         *
         * @param joinCnt
         * 		Join count to compare.
         */
        private JoinTimeout([CtParameterImpl][CtTypeReferenceImpl]int joinCnt) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.joinCnt = [CtVariableReadImpl]joinCnt;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     */
    private static class SocketClosedMessage {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sock;

        [CtConstructorImpl][CtJavaDocImpl]/**
         *
         * @param sock
         * 		Socket.
         */
        private SocketClosedMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.spi.discovery.tcp.ClientImpl.SocketStream sock) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sock = [CtVariableReadImpl]sock;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     */
    private static class SocketStream {
        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.net.Socket sock;

        [CtFieldImpl][CtJavaDocImpl]/**
         */
        private final [CtTypeReferenceImpl]java.io.InputStream in;

        [CtConstructorImpl][CtJavaDocImpl]/**
         *
         * @param sock
         * 		Socket.
         * @throws IOException
         * 		If failed to create stream.
         */
        public SocketStream([CtParameterImpl][CtTypeReferenceImpl]java.net.Socket sock) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]sock != [CtLiteralImpl]null;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sock = [CtVariableReadImpl]sock;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.in = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedInputStream([CtInvocationImpl][CtVariableReadImpl]sock.getInputStream());
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return Socket.
         */
        [CtTypeReferenceImpl]java.net.Socket socket() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]sock;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return Socket input stream.
         */
        [CtTypeReferenceImpl]java.io.InputStream stream() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]in;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]sock.toString();
        }
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     */
    enum State {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         */
        STARTING,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         */
        CONNECTED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         */
        DISCONNECTED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         */
        SEGMENTED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         */
        STOPPED;}
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 GridGain Systems, Inc. and Contributors.

Licensed under the GridGain Community Edition License (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.ignite.internal.processors.query.stat;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.gridgain.internal.h2.table.Column;
[CtImportImpl]import java.util.concurrent.LinkedBlockingQueue;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.opt.GridH2Table;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.communication.GridIoPolicy;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionState;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.GridQueryTypeDescriptor;
[CtUnresolvedImport]import org.apache.ignite.events.EventType;
[CtUnresolvedImport]import org.apache.ignite.IgniteCheckedException;
[CtUnresolvedImport]import org.apache.ignite.events.Event;
[CtUnresolvedImport]import static org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionState.OWNING;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.persistence.CacheDataRow;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteBiTuple;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage;
[CtUnresolvedImport]import org.jetbrains.annotations.Nullable;
[CtUnresolvedImport]import org.apache.ignite.configuration.IgniteConfiguration;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.opt.H2Row;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionTopology;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsGetRequest;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.ignite.plugin.extensions.communication.Message;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.apache.ignite.events.DiscoveryEvent;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest;
[CtUnresolvedImport]import org.apache.ignite.IgniteLogger;
[CtUnresolvedImport]import org.apache.ignite.internal.GridKernalContext;
[CtUnresolvedImport]import static org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionState.MOVING;
[CtUnresolvedImport]import org.apache.ignite.cluster.ClusterNode;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest;
[CtUnresolvedImport]import org.apache.ignite.thread.IgniteThreadPoolExecutor;
[CtImportImpl]import java.util.function.Supplier;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.opt.GridH2RowDescriptor;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.communication.GridMessageListener;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.SchemaManager;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.F;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsClearRequest;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.affinity.AffinityTopologyVersion;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtLocalPartition;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.persistence.IgniteCacheDatabaseSharedManager;
[CtUnresolvedImport]import org.apache.ignite.internal.GridTopic;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse;
[CtUnresolvedImport]import org.apache.ignite.internal.util.lang.GridTuple3;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.GridCacheUtils;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.eventstorage.GridLocalEventListener;
[CtClassImpl][CtJavaDocImpl]/**
 * Statistics manager implementation.
 */
public class IgniteStatisticsManagerImpl implements [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManager , [CtTypeReferenceImpl]org.apache.ignite.internal.managers.communication.GridMessageListener {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Statistics related messages topic name.
     */
    private static final [CtTypeReferenceImpl]java.lang.Object TOPIC = [CtInvocationImpl][CtTypeAccessImpl]GridTopic.TOPIC_CACHE.topic([CtLiteralImpl]"statistics");

    [CtFieldImpl][CtJavaDocImpl]/**
     * Size of statistics collection pool.
     */
    private static final [CtTypeReferenceImpl]int STATS_POOL_SIZE = [CtLiteralImpl]1;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Node left listener to complete statistics collection tasks without left nodes.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.NodeLeftListener nodeLeftLsnr;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Logger.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.IgniteLogger log;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Kernal context.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.GridKernalContext ctx;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Schema manager.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.SchemaManager schemaMgr;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Statistics repository.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRepository statsRepos;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Ignite Thread pool executor to do statistics collection tasks.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.thread.IgniteThreadPoolExecutor statMgmtPool;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Current statistics collections tasks.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRequestCollection currCollections;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructor.
     *
     * @param ctx
     * 		Kernal context.
     * @param schemaMgr
     * 		Schema manager.
     */
    public IgniteStatisticsManagerImpl([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.GridKernalContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.SchemaManager schemaMgr) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ctx = [CtVariableReadImpl]ctx;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.schemaMgr = [CtVariableReadImpl]schemaMgr;
        [CtAssignmentImpl][CtFieldWriteImpl]currCollections = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRequestCollection([CtVariableReadImpl]schemaMgr);
        [CtAssignmentImpl][CtFieldWriteImpl]log = [CtInvocationImpl][CtVariableReadImpl]ctx.log([CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.class);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.io().addMessageListener([CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.TOPIC, [CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean storeData = [CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.config().isClientMode() || [CtInvocationImpl][CtVariableReadImpl]ctx.isDaemon());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.persistence.IgniteCacheDatabaseSharedManager db = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.cache.GridCacheUtils.isPersistenceEnabled([CtInvocationImpl][CtVariableReadImpl]ctx.config())) ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.cache().context().database() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]statsRepos = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRepositoryImpl([CtVariableReadImpl]storeData, [CtVariableReadImpl]db, [CtInvocationImpl][CtVariableReadImpl]ctx.internalSubscriptionProcessor(), [CtThisAccessImpl]this, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]ctx::log);
        [CtAssignmentImpl][CtFieldWriteImpl]nodeLeftLsnr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.NodeLeftListener();
        [CtAssignmentImpl][CtFieldWriteImpl]statMgmtPool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.thread.IgniteThreadPoolExecutor([CtLiteralImpl]"dr-mgmt-pool", [CtInvocationImpl][CtVariableReadImpl]ctx.igniteInstanceName(), [CtLiteralImpl]0, [CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.STATS_POOL_SIZE, [CtFieldReadImpl]org.apache.ignite.configuration.IgniteConfiguration.DFLT_THREAD_KEEP_ALIVE_TIME, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.LinkedBlockingQueue<>(), [CtFieldReadImpl]org.apache.ignite.internal.managers.communication.GridIoPolicy.UNDEFINED, [CtInvocationImpl][CtVariableReadImpl]ctx.uncaughtExceptionHandler());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.event().addLocalEventListener([CtFieldReadImpl]nodeLeftLsnr, [CtTypeAccessImpl]EventType.EVT_NODE_FAILED, [CtTypeAccessImpl]EventType.EVT_NODE_LEFT);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Statistics repository.
     */
    public [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRepository statisticsRepository() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]statsRepos;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatistics getLocalStatistics([CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String objName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]statsRepos.getLocalStatistics([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtVariableReadImpl]schemaName, [CtVariableReadImpl]objName));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void clearObjectStatistics([CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String objName, [CtParameterImpl]java.lang.String... colNames) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsCollectionFutureAdapter doneFut = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsCollectionFutureAdapter([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID reqId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toMessage([CtVariableReadImpl]schemaName, [CtVariableReadImpl]objName, [CtVariableReadImpl]colNames);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsClearRequest>> reqs = [CtInvocationImpl][CtFieldReadImpl]currCollections.generateClearRequests([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]keyMsg));
        [CtInvocationImpl]sendRequests([CtVariableReadImpl]reqs);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer>> requestNodes = [CtLiteralImpl]null;
        [CtInvocationImpl][CtCommentImpl]/* try {
        requestNodes = nodePartitions(extractGroups(Collections.singletonList(keyMsg)), null);
        } catch (IgniteCheckedException e) {
        // TODO: handle & remove task
        }

        StatsClearRequest req = new StatsClearRequest(reqId, false, Collections.singletonList(keyMsg));

        sendLocalRequests(reqId, req, requestNodes);
         */
        clearObjectStatisticsLocal([CtVariableReadImpl]keyMsg);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Actually clear local object statistics by the given key.
     *
     * @param keyMsg
     * 		Key to clear statistics by.
     */
    private [CtTypeReferenceImpl]void clearObjectStatisticsLocal([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] colNames = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyMsg.colNames().toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0]);
        [CtInvocationImpl][CtFieldReadImpl]statsRepos.clearLocalPartitionsStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]colNames);
        [CtInvocationImpl][CtFieldReadImpl]statsRepos.clearLocalStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]colNames);
        [CtInvocationImpl][CtFieldReadImpl]statsRepos.clearGlobalStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]colNames);
    }

    [CtMethodImpl][CtCommentImpl]// TODO
    [CtJavaDocImpl]/**
     * Collect local object statistics by primary partitions of specified object.
     *
     * @param keyMsg
     * 		Statistic key message to collect statistics by.
     * @param partIds
     * 		Set of partition ids to collect statistics by.
     * @param cancelled
     * 		Supplier to check if operation was cancelled.
     * @return Tuple of Collected local statistics with array of successfully collected partitions.
     * @throws IgniteCheckedException
     */
    private [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl, [CtArrayTypeReferenceImpl]int[]> collectLocalObjectStatistics([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg, [CtParameterImpl][CtArrayTypeReferenceImpl]int[] partIds, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> cancelled) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl = [CtInvocationImpl][CtFieldReadImpl]schemaMgr.dataTable([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tbl == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Can't find table %s.%s", [CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj()));

        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[] selectedCols = [CtInvocationImpl]filterColumns([CtInvocationImpl][CtVariableReadImpl]tbl.getColumns(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.colNames());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl> partsStats = [CtInvocationImpl]collectPartitionStatistics([CtVariableReadImpl]tbl, [CtVariableReadImpl]partIds, [CtVariableReadImpl]selectedCols, [CtVariableReadImpl]cancelled);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partsStats == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssertImpl]assert [CtInvocationImpl][CtVariableReadImpl]cancelled.get() : [CtLiteralImpl]"Error collecting partition level statistics.";
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtInvocationImpl]sendPartitionStatisticsToBackupNodes([CtVariableReadImpl]tbl, [CtVariableReadImpl]partsStats);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
        [CtInvocationImpl][CtFieldReadImpl]statsRepos.saveLocalPartitionsStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]partsStats);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl tblStats = [CtInvocationImpl]aggregateLocalStatistics([CtVariableReadImpl]tbl, [CtVariableReadImpl]selectedCols, [CtVariableReadImpl]partsStats);
        [CtInvocationImpl][CtFieldReadImpl]statsRepos.mergeLocalStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]tblStats);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple([CtVariableReadImpl]tblStats, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partsStats.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ObjectPartitionStatisticsImpl::partId).toArray());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send statistics propagation messages with partition statistics to all backups node.
     *
     * @param tbl
     * 		Table to which statistics should be send.
     * @param objStats
     * 		Collection of partition statistics to send.
     * @throws IgniteCheckedException
     * 		In case of errors.
     */
    private [CtTypeReferenceImpl]void sendPartitionStatisticsToBackupNodes([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl> objStats) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNode = [CtInvocationImpl][CtFieldReadImpl]ctx.localNodeId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().schema(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().table(), [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionTopology topology = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().topology();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData>> statsByNodes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl stat : [CtVariableReadImpl]objStats) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData statData = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toObjectData([CtVariableReadImpl]keyMsg, [CtTypeAccessImpl]StatsType.PARTITION, [CtVariableReadImpl]stat);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cluster.ClusterNode partNode : [CtInvocationImpl][CtVariableReadImpl]topology.nodes([CtInvocationImpl][CtVariableReadImpl]stat.partId(), [CtInvocationImpl][CtVariableReadImpl]topology.readyTopologyVersion())) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]locNode.equals([CtInvocationImpl][CtVariableReadImpl]partNode.id()))[CtBlockImpl]
                    [CtContinueImpl]continue;

                [CtInvocationImpl][CtVariableReadImpl]statsByNodes.compute([CtInvocationImpl][CtVariableReadImpl]partNode.id(), [CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]v = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ArrayList<>();

                    [CtInvocationImpl][CtVariableReadImpl]v.add([CtVariableReadImpl]statData);
                    [CtReturnImpl]return [CtVariableReadImpl]v;
                });
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData>> statToNode : [CtInvocationImpl][CtVariableReadImpl]statsByNodes.entrySet()) [CtBlockImpl]{
            [CtCommentImpl]// StatsPropagationMessage nodeMsg = StatisticsUtils.toMessage(null, statToNode.getValue());
            [CtCommentImpl]// ctx.io().sendToCustomTopic(statToNode.getKey(), TOPIC, nodeMsg, GridIoPolicy.QUERY_POOL);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Filter columns by specified names.
     *
     * @param cols
     * 		Columns to filter.
     * @param colNames
     * 		Column names.
     * @return Column with specified names.
     */
    private [CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[] filterColumns([CtParameterImpl][CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[] cols, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> colNames) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.F.isEmpty([CtVariableReadImpl]colNames))[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]cols;

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> colNamesSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]colNames));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]cols).filter([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl][CtVariableReadImpl]colNamesSet.contains([CtInvocationImpl][CtVariableReadImpl]c.getName())).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[]::new);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Collect object statistics prepared status.
     *
     * @param status
     * 		Collection status to collect statistics by.
     * @throws IgniteCheckedException
     * 		In case of errors.
     */
    private [CtTypeReferenceImpl]void collectObjectStatistics([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus status) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtVariableReadImpl]status) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]currCollections.updateCollection([CtInvocationImpl][CtVariableReadImpl]status.colId(), [CtLambdaImpl]([CtParameterImpl] s) -> [CtVariableReadImpl]status);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtArrayTypeReferenceImpl]int[]> failedPartitions = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int cnt = [CtLiteralImpl]0;
            [CtDoImpl]do [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest>> reqs = [CtInvocationImpl][CtFieldReadImpl]currCollections.generateCollectionRequests([CtInvocationImpl][CtVariableReadImpl]status.colId(), [CtInvocationImpl][CtVariableReadImpl]status.keys(), [CtVariableReadImpl]failedPartitions);
                [CtLocalVariableImpl][CtCommentImpl]// Map<UUID, StatsCollectionRequest> msgs = reqs.stream().collect(Collectors.toMap(
                [CtCommentImpl]// StatsAddrRequest::nodeId, StatsAddrRequest::req));
                [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest>> failedMsgs = [CtInvocationImpl]sendRequests([CtVariableReadImpl]reqs);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest>> sendedMsgs;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]failedMsgs == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]sendedMsgs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]reqs.stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.req().reqId(), [CtLambdaImpl]([CtParameterImpl] r) -> [CtVariableReadImpl]r));
                    [CtAssignmentImpl][CtVariableWriteImpl]failedPartitions = [CtLiteralImpl]null;
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.UUID> failedIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]failedMsgs.stream().map([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.req().reqId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
                    [CtAssignmentImpl][CtVariableWriteImpl]sendedMsgs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]reqs.stream().filter([CtLambdaImpl]([CtParameterImpl] r) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]failedIds.contains([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.req().reqId())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.req().reqId(), [CtLambdaImpl]([CtParameterImpl] r) -> [CtVariableReadImpl]r));
                    [CtAssignmentImpl][CtVariableWriteImpl]failedPartitions = [CtInvocationImpl][CtFieldReadImpl]currCollections.extractFailed([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]failedMsgs.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]StatsAddrRequest::req).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest[]::new));
                }
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.remainingCollectionReqs().putAll([CtVariableReadImpl]sendedMsgs);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl]([CtVariableWriteImpl]cnt++) > [CtLiteralImpl]10) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage key = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.keys().iterator().next();
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to send all messages to collect statistics by key %s.%s and the others %d", [CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj(), [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.keys().size() - [CtLiteralImpl]1));
                }
            } while ([CtBinaryOperatorImpl][CtVariableReadImpl]failedPartitions != [CtLiteralImpl]null );
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNode = [CtInvocationImpl][CtFieldReadImpl]ctx.localNodeId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest> locReq = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.remainingCollectionReqs().values().stream().filter([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtVariableReadImpl]locNode.equals([CtInvocationImpl][CtVariableReadImpl]r.nodeId())).findAny().orElse([CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]locReq != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]statMgmtPool.submit([CtLambdaImpl]() -> [CtInvocationImpl]processLocal([CtVariableReadImpl]locNode, [CtInvocationImpl][CtVariableReadImpl]locReq.req()));

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collectObjectStatistics([CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String objName, [CtParameterImpl]java.lang.String... colNames) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID colId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage([CtVariableReadImpl]schemaName, [CtVariableReadImpl]objName, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]colNames));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus status = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus([CtVariableReadImpl]colId, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]keyMsg), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
        [CtInvocationImpl]collectObjectStatistics([CtVariableReadImpl]status);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.doneFut().get();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsCollectionFuture<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.util.lang.GridTuple3<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]>, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatistics>> collectObjectStatisticsAsync([CtParameterImpl]org.apache.ignite.internal.util.lang.GridTuple3<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]>... keys) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID colId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage> keysMsg = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]keys).map([CtLambdaImpl]([CtParameterImpl] k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage([CtInvocationImpl][CtVariableReadImpl]k.get1(), [CtInvocationImpl][CtVariableReadImpl]k.get2(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]k.get3()))).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus status = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus([CtVariableReadImpl]colId, [CtVariableReadImpl]keysMsg, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
        [CtInvocationImpl]collectObjectStatistics([CtVariableReadImpl]status);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]status.doneFut();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean cancelObjectStatisticsCollection([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID colId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]boolean[] res = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[]{ [CtLiteralImpl]true };
        [CtInvocationImpl][CtFieldReadImpl]currCollections.updateCollection([CtVariableReadImpl]colId, [CtLambdaImpl]([CtParameterImpl] s) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]res[[CtLiteralImpl]0] = [CtLiteralImpl]false;
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.doneFut().cancel();
            [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.util.UUID>> nodeRequests = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.HashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest> req : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.remainingCollectionReqs().values()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]nodeRequests.compute([CtInvocationImpl][CtVariableReadImpl]req.nodeId(), [CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]v = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList();

                    [CtInvocationImpl][CtVariableReadImpl]v.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.req().reqId());
                    [CtReturnImpl]return [CtVariableReadImpl]v;
                });
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest>> cancelReqs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nodeRequests.entrySet().stream().map([CtLambdaImpl]([CtParameterImpl] targetNode) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest>([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest([CtVariableReadImpl]colId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetNode.getValue().toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.util.UUID[[CtLiteralImpl]0])), [CtInvocationImpl][CtVariableReadImpl]targetNode.getKey())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            [CtLocalVariableImpl][CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest>> failed = [CtInvocationImpl]sendRequests([CtVariableReadImpl]cancelReqs);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]failed != [CtLiteralImpl]null)[CtBlockImpl]
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to cancel all statistics collections requests (%d failed) by colId %s", [CtInvocationImpl][CtVariableReadImpl]failed.size(), [CtVariableReadImpl]colId));


            [CtReturnImpl]return [CtLiteralImpl]null;
        });
        [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]res[[CtLiteralImpl]0];
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate and try to send all request for particular status. Should be called inside status lock after putting it
     * into currCollections map. REMOVE!!!!
     *
     * @param status
     * 		Status to process.
     * @param keys
     * 		Collection of object keys to collect statistics by.
     * @return {@code true} if all request was successfully sended, {@code false} - otherwise (one should remove
    status from cullCollections.
     */
    protected [CtTypeReferenceImpl]boolean doRequests([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus status, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage> keys) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]/* Map<CacheGroupContext, Collection<StatsKeyMessage>> grpContexts = extractGroups(keys);
        List<Map<UUID, StatsCollectionAddrRequest>> reqsByGrps = new ArrayList<>(grpContexts.size());
        for (Map.Entry<CacheGroupContext, Collection<StatsKeyMessage>> grpEntry : grpContexts.entrySet()) {
        Map<UUID, List<Integer>> reqNodes = nodePartitions(grpEntry.getKey(), null);
        for(StatsKeyMessage keyMsg : grpEntry.getValue())
        reqsByGrps.add(prepareRequests(status.colId(), keyMsg, reqNodes));

        }
        Map<UUID, StatsCollectionAddrRequest> reqs = compressRequests(reqsByGrps);

        status.remainingCollectionReqs().putAll(reqs);

        Map<UUID, StatsCollectionRequest> failedReqs = sendLocalRequests(reqs.values().stream().collect(
        Collectors.toMap(StatsCollectionAddrRequest::nodeId, StatsCollectionAddrRequest::req)));
        // TODO: cycle replanning and sending
        return failedReqs.isEmpty();
         */
        return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Stop statistics manager.
     */
    public [CtTypeReferenceImpl]void stop() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]statMgmtPool != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Runnable> unfinishedTasks = [CtInvocationImpl][CtFieldReadImpl]statMgmtPool.shutdownNow();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]unfinishedTasks.isEmpty())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.warning([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%d statistics collection request cancelled.", [CtInvocationImpl][CtVariableReadImpl]unfinishedTasks.size()));

        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Collect partition level statistics.
     *
     * @param tbl
     * 		Table to collect statistics by.
     * @param partIds
     * 		Array of partition ids to collect statistics by.
     * @param selectedCols
     * 		Columns to collect statistics by.
     * @param cancelled
     * 		Supplier to check if collection was cancelled.
     * @return Collection of partition level statistics by local primary partitions.
     * @throws IgniteCheckedException
     * 		in case of error.
     */
    private [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl> collectPartitionStatistics([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl, [CtParameterImpl][CtArrayTypeReferenceImpl]int[] partIds, [CtParameterImpl][CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[] selectedCols, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Boolean> cancelled) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl> tblPartStats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2RowDescriptor desc = [CtInvocationImpl][CtVariableReadImpl]tbl.rowDescriptor();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tblName = [CtInvocationImpl][CtVariableReadImpl]tbl.getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionTopology topology = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().topology();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.affinity.AffinityTopologyVersion topologyVersion = [CtInvocationImpl][CtVariableReadImpl]topology.readyTopologyVersion();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int partId : [CtVariableReadImpl]partIds) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtLocalPartition locPart = [CtInvocationImpl][CtVariableReadImpl]topology.localPartition([CtVariableReadImpl]partId, [CtVariableReadImpl]topologyVersion, [CtLiteralImpl]false);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]locPart == [CtLiteralImpl]null)[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cancelled.get()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Canceled collection of object %s.%s statistics.", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().schema(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().table()));

                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean reserved = [CtInvocationImpl][CtVariableReadImpl]locPart.reserve();
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]reserved) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]locPart.state() != [CtFieldReadImpl]OWNING) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]locPart.state() != [CtFieldReadImpl]MOVING))) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]locPart.primary([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.discovery().topologyVersionEx())))[CtBlockImpl]
                    [CtContinueImpl]continue;

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]locPart.state() == [CtFieldReadImpl]MOVING)[CtBlockImpl]
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().preloader().syncFuture().get();

                [CtLocalVariableImpl][CtTypeReferenceImpl]long rowsCnt = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatisticsCollector> colStatsCollectors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl][CtVariableReadImpl]selectedCols.length);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.gridgain.internal.h2.table.Column col : [CtVariableReadImpl]selectedCols)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]colStatsCollectors.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatisticsCollector([CtVariableReadImpl]col, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]tbl::compareValues));

                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.persistence.CacheDataRow row : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().offheap().cachePartitionIterator([CtInvocationImpl][CtVariableReadImpl]tbl.cacheId(), [CtInvocationImpl][CtVariableReadImpl]locPart.id(), [CtLiteralImpl]null, [CtLiteralImpl]true)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.GridQueryTypeDescriptor typeDesc = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.query().typeByValue([CtInvocationImpl][CtVariableReadImpl]tbl.cacheName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().cacheObjectContext(), [CtInvocationImpl][CtVariableReadImpl]row.key(), [CtInvocationImpl][CtVariableReadImpl]row.value(), [CtLiteralImpl]false);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tblName.equals([CtInvocationImpl][CtVariableReadImpl]typeDesc.tableName()))[CtBlockImpl]
                        [CtContinueImpl]continue;

                    [CtUnaryOperatorImpl][CtVariableWriteImpl]rowsCnt++;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.H2Row row0 = [CtInvocationImpl][CtVariableReadImpl]desc.createRow([CtVariableReadImpl]row);
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatisticsCollector colStat : [CtVariableReadImpl]colStatsCollectors)[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]colStat.add([CtInvocationImpl][CtVariableReadImpl]row0.getValue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]colStat.col().getColumnId()));

                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatistics> colStats = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]colStatsCollectors.stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] csc) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]csc.col().getName(), [CtLambdaImpl]([CtParameterImpl] csc) -> [CtInvocationImpl][CtVariableReadImpl]csc.finish()));
                [CtInvocationImpl][CtVariableReadImpl]tblPartStats.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl([CtInvocationImpl][CtVariableReadImpl]locPart.id(), [CtLiteralImpl]true, [CtVariableReadImpl]rowsCnt, [CtInvocationImpl][CtVariableReadImpl]locPart.updateCounter(), [CtVariableReadImpl]colStats));
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isTraceEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Finished statistics collection on %s.%s:%d", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().schema(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.identifier().table(), [CtInvocationImpl][CtVariableReadImpl]locPart.id()));

            } finally [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]reserved)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]locPart.release();

            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]tblPartStats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Aggregate specified partition level statistics to local level statistics.
     *
     * @param keyMsg
     * 		Aggregation key.
     * @param stats
     * 		Collection of all local partition level or local level statistics by specified key to aggregate.
     * @return Local level aggregated statistics.
     */
    public [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl aggregateLocalStatistics([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl> stats) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// For now there can be only tables
        [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl = [CtInvocationImpl][CtFieldReadImpl]schemaMgr.dataTable([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tbl == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// remove all loaded statistics.
            if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Removing statistics for object %s.%s cause table doesn't exists.", [CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj()));

            [CtInvocationImpl][CtCommentImpl]// Just to double check
            [CtFieldReadImpl]statsRepos.clearLocalPartitionsStatistics([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj()));
        }
        [CtReturnImpl]return [CtInvocationImpl]aggregateLocalStatistics([CtVariableReadImpl]tbl, [CtInvocationImpl]filterColumns([CtInvocationImpl][CtVariableReadImpl]tbl.getColumns(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.colNames()), [CtVariableReadImpl]stats);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Aggregate partition level statistics to local level one.
     *
     * @param tbl
     * 		Table to aggregate statistics by.
     * @param selectedCols
     * 		Columns to aggregate statistics by.
     * @param stats
     * 		Collection of partition level or local level statistics to aggregate.
     * @return Local level statistics.
     */
    private [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl aggregateLocalStatistics([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl, [CtParameterImpl][CtArrayTypeReferenceImpl]org.gridgain.internal.h2.table.Column[] selectedCols, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl> stats) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.gridgain.internal.h2.table.Column, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatistics>> colPartStats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtFieldReadImpl][CtVariableReadImpl]selectedCols.length);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long rowCnt = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.gridgain.internal.h2.table.Column col : [CtVariableReadImpl]selectedCols)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]colPartStats.put([CtVariableReadImpl]col, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl partStat : [CtVariableReadImpl]stats) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.gridgain.internal.h2.table.Column col : [CtVariableReadImpl]selectedCols) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatistics colPartStat = [CtInvocationImpl][CtVariableReadImpl]partStat.columnStatistics([CtInvocationImpl][CtVariableReadImpl]col.getName());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]colPartStat != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]colPartStats.computeIfPresent([CtVariableReadImpl]col, [CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]v.add([CtVariableReadImpl]colPartStat);
                        [CtReturnImpl]return [CtVariableReadImpl]v;
                    });
                }
            }
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]rowCnt += [CtInvocationImpl][CtVariableReadImpl]partStat.rowCount();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatistics> colStats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtFieldReadImpl][CtVariableReadImpl]selectedCols.length);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.gridgain.internal.h2.table.Column col : [CtVariableReadImpl]selectedCols) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatistics stat = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.ColumnStatisticsCollector.aggregate([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]tbl::compareValues, [CtInvocationImpl][CtVariableReadImpl]colPartStats.get([CtVariableReadImpl]col));
            [CtInvocationImpl][CtVariableReadImpl]colStats.put([CtInvocationImpl][CtVariableReadImpl]col.getName(), [CtVariableReadImpl]stat);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl tblStats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl([CtVariableReadImpl]rowCnt, [CtVariableReadImpl]colStats);
        [CtReturnImpl]return [CtVariableReadImpl]tblStats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Receive and handle statistics propagation message with partitions statistics.
     *
     * @param nodeId
     * 		Sender node id.
     * @param msg
     * 		Statistics propagation message with partitions statistics to handle.
     * @throws IgniteCheckedException
     * 		In case of errors.
     */
    private [CtTypeReferenceImpl]void receivePartitionsStatistics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage msg) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNode = [CtInvocationImpl][CtFieldReadImpl]ctx.localNodeId();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData partData : [CtInvocationImpl][CtVariableReadImpl]msg.data()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partData.key().schema(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partData.key().obj());
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partData.type() == [CtFieldReadImpl]StatsType.PARTITION : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Got non partition level statistics by " + [CtVariableReadImpl]key) + [CtLiteralImpl]" without request";
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isTraceEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Received partition statistics %s.%s:%d from node %s", [CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj(), [CtInvocationImpl][CtVariableReadImpl]partData.partId(), [CtVariableReadImpl]nodeId));

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl = [CtInvocationImpl][CtFieldReadImpl]schemaMgr.dataTable([CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tbl == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Ignoring outdated partition statistics %s.%s:%d from node %s", [CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj(), [CtInvocationImpl][CtVariableReadImpl]partData.partId(), [CtVariableReadImpl]nodeId));

                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.distributed.dht.topology.GridDhtPartitionState partState = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tbl.cacheContext().topology().partitionState([CtVariableReadImpl]locNode, [CtInvocationImpl][CtVariableReadImpl]partData.partId());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partState != [CtFieldReadImpl]OWNING) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Ignoring non local partition statistics %s.%s:%d from node %s", [CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj(), [CtInvocationImpl][CtVariableReadImpl]partData.partId(), [CtVariableReadImpl]nodeId));

                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectPartitionStatisticsImpl opStat = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toObjectPartitionStatistics([CtFieldReadImpl]ctx, [CtVariableReadImpl]partData);
            [CtInvocationImpl][CtFieldReadImpl]statsRepos.saveLocalPartitionStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]opStat);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Receive and handle statistics propagation message as response for collection request.
     *
     * @param nodeId
     * 		Sender node id.
     * @param msg
     * 		Statistics propagation message with partitions statistics to handle.
     * @throws IgniteCheckedException
     * 		In case of errors.
     */
    private [CtTypeReferenceImpl]void receiveLocalStatistics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse msg) [CtBlockImpl]{
        [CtAssertImpl]assert [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.data().keySet().stream().noneMatch([CtLambdaImpl]([CtParameterImpl] pd) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]pd.type() == [CtVariableReadImpl]StatsType.PARTITION) : [CtBinaryOperatorImpl][CtLiteralImpl]"Got partition statistics by request " + [CtInvocationImpl][CtVariableReadImpl]msg.reqId();
        [CtInvocationImpl][CtFieldReadImpl]currCollections.updateCollection([CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtLambdaImpl]([CtParameterImpl] stat) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Ignoring outdated local statistics collection response from node %s to col %s req %s", [CtVariableReadImpl]nodeId, [CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtInvocationImpl][CtVariableReadImpl]msg.reqId()));

                [CtReturnImpl]return [CtVariableReadImpl]stat;
            }
            [CtAssertImpl]assert [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.colId().equals([CtInvocationImpl][CtVariableReadImpl]msg.colId());
            [CtSynchronizedImpl][CtCommentImpl]// Need syncronization here to avoid races between removing remaining reqs and adding new ones.
            synchronized([CtVariableReadImpl]stat) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest> req = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.remainingCollectionReqs().remove([CtInvocationImpl][CtVariableReadImpl]msg.reqId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]req == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Ignoring unknown local statistics collection response from node %s to col %s req %s", [CtVariableReadImpl]nodeId, [CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtInvocationImpl][CtVariableReadImpl]msg.reqId()));

                    [CtReturnImpl]return [CtVariableReadImpl]stat;
                }
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.localStatistics().add([CtVariableReadImpl]msg);
                [CtIfImpl][CtCommentImpl]// TODO: reschedule if not all partition collected.
                if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.remainingCollectionReqs().isEmpty()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl> mergedGlobal = [CtInvocationImpl]finishStatCollection([CtVariableReadImpl]stat);
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.doneFut().onDone([CtLiteralImpl]null);
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]stat;
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Aggregate local statistics to global one.
     *
     * @param stat
     * 		Statistics collection status to aggregate.
     * @return Map stats key to merged global statistics.
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl> finishStatCollection([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus stat) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl>> keysStats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse resp : [CtInvocationImpl][CtVariableReadImpl]stat.localStatistics()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData objData : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resp.data().keySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]keysStats.compute([CtInvocationImpl][CtVariableReadImpl]objData.key(), [CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]v = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ArrayList<>();

                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl objStat = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toObjectStatistics([CtFieldReadImpl][CtFieldReferenceImpl]ctx, [CtVariableReadImpl]objData);
                        [CtInvocationImpl][CtVariableReadImpl]v.add([CtVariableReadImpl]objStat);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isInfoEnabled())[CtBlockImpl]
                            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to parse statistics for object %s from response %s", [CtInvocationImpl][CtVariableReadImpl]objData.key(), [CtInvocationImpl][CtVariableReadImpl]resp.reqId()));

                    }
                    [CtReturnImpl]return [CtVariableReadImpl]v;
                });
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey, [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl> res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl>> keyStats : [CtInvocationImpl][CtVariableReadImpl]keysStats.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg = [CtInvocationImpl][CtVariableReadImpl]keyStats.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.opt.GridH2Table tbl = [CtInvocationImpl][CtFieldReadImpl]schemaMgr.dataTable([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tbl == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isInfoEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to find object %s.%s to save collected statistics by.", [CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj()));

                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl globalStat = [CtInvocationImpl]aggregateLocalStatistics([CtVariableReadImpl]keyMsg, [CtInvocationImpl][CtVariableReadImpl]keyStats.getValue());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
            [CtInvocationImpl][CtVariableReadImpl]res.put([CtVariableReadImpl]key, [CtInvocationImpl][CtFieldReadImpl]statsRepos.mergeGlobalStatistics([CtVariableReadImpl]key, [CtVariableReadImpl]globalStat));
        }
        [CtReturnImpl]return [CtVariableReadImpl]res;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send statistics by request.
     *
     * @param nodeId
     * 		Node to send statistics to.
     * @param msg
     * 		Statistics request to process.
     * @throws IgniteCheckedException
     * 		In case of errors.
     */
    private [CtTypeReferenceImpl]void supplyStatistics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsGetRequest msg) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData> data = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.keys().size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage keyMsg : [CtInvocationImpl][CtVariableReadImpl]msg.keys()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey key = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]keyMsg.schema(), [CtInvocationImpl][CtVariableReadImpl]keyMsg.obj());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl objStats = [CtInvocationImpl][CtFieldReadImpl]statsRepos.getGlobalStatistics([CtVariableReadImpl]key);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]objStats != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]data.add([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toObjectData([CtVariableReadImpl]keyMsg, [CtTypeAccessImpl]StatsType.GLOBAL, [CtVariableReadImpl]objStats));

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage([CtVariableReadImpl]data);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.io().sendToCustomTopic([CtVariableReadImpl]nodeId, [CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.TOPIC, [CtVariableReadImpl]res, [CtTypeAccessImpl]GridIoPolicy.QUERY_POOL);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cancel local statistics collection task.
     *
     * @param nodeId
     * 		Sender node id.
     * @param msg
     * 		Cancel request.
     */
    private [CtTypeReferenceImpl]void cancelStatisticsCollection([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest msg) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID reqId : [CtInvocationImpl][CtVariableReadImpl]msg.reqIds()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]currCollections.updateCollection([CtVariableReadImpl]reqId, [CtLambdaImpl]([CtParameterImpl] stat) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isDebugEnabled())[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to cancel statistics collection %s by req %s from node %s", [CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtVariableReadImpl]reqId, [CtVariableReadImpl]nodeId));

                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.doneFut().cancel();
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Cancelling statistics collection by colId = %s, reqId = %s from node %s", [CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtVariableReadImpl]reqId, [CtVariableReadImpl]nodeId));

                [CtReturnImpl]return [CtLiteralImpl]null;
            });
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onMessage([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object msg, [CtParameterImpl][CtTypeReferenceImpl]byte plc) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage)[CtBlockImpl]
                [CtInvocationImpl]receivePartitionsStatistics([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsPropagationMessage) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse)[CtBlockImpl]
                [CtInvocationImpl]receiveLocalStatistics([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsGetRequest)[CtBlockImpl]
                [CtInvocationImpl]supplyStatistics([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsGetRequest) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest)[CtBlockImpl]
                [CtInvocationImpl]handleCollectionRequest([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest)[CtBlockImpl]
                [CtInvocationImpl]cancelStatisticsCollection([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.CancelStatsCollectionRequest) (msg)));
            else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsClearRequest)[CtBlockImpl]
                [CtInvocationImpl]clearObjectStatistics([CtVariableReadImpl]nodeId, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsClearRequest) (msg)));
            else[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.warning([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unknown msg " + [CtVariableReadImpl]msg) + [CtLiteralImpl]" in statistics topic ") + [CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.TOPIC) + [CtLiteralImpl]" from node ") + [CtVariableReadImpl]nodeId);

        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.warning([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Statistic msg from node " + [CtVariableReadImpl]nodeId) + [CtLiteralImpl]" processing failed", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle statistics clear request.
     *
     * @param nodeId
     * 		UUID of request sender node.
     * @param msg
     * 		Clear request message.
     */
    private [CtTypeReferenceImpl]void clearObjectStatistics([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsClearRequest msg) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage key : [CtInvocationImpl][CtVariableReadImpl]msg.keys()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isTraceEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]log.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Clearing statistics by request %s from node %s by key %s.%s", [CtInvocationImpl][CtVariableReadImpl]msg.reqId(), [CtVariableReadImpl]nodeId, [CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj()));

            [CtInvocationImpl][CtFieldReadImpl]statMgmtPool.submit([CtLambdaImpl]() -> [CtInvocationImpl]clearObjectStatisticsLocal([CtVariableReadImpl]key));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Collect local object statistics by specified request (possibly for a few objects) and send result back to origin
     * node specified. If local node id specified - process result without sending it throw the communication.
     *
     * @param req
     * 		request to collect statistics by.
     */
    private [CtTypeReferenceImpl]void processLocal([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest req) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNode = [CtInvocationImpl][CtFieldReadImpl]ctx.localNodeId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus stat = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtVariableReadImpl]locNode)) ? [CtInvocationImpl][CtFieldReadImpl]currCollections.getCollection([CtInvocationImpl][CtVariableReadImpl]req.colId()) : [CtInvocationImpl][CtFieldReadImpl]currCollections.getCollection([CtInvocationImpl][CtVariableReadImpl]req.reqId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData, [CtArrayTypeReferenceImpl]int[]> collected = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.keys().size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtArrayTypeReferenceImpl]int[]> keyEntry : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.keys().entrySet()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage key = [CtInvocationImpl][CtVariableReadImpl]keyEntry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.ObjectStatisticsImpl, [CtArrayTypeReferenceImpl]int[]> loStat = [CtInvocationImpl]collectLocalObjectStatistics([CtVariableReadImpl]key, [CtInvocationImpl][CtVariableReadImpl]keyEntry.getValue(), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.doneFut().isCancelled());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey statsKey = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsKey([CtInvocationImpl][CtVariableReadImpl]key.schema(), [CtInvocationImpl][CtVariableReadImpl]key.obj());
                [CtInvocationImpl][CtCommentImpl]// TODO?
                [CtFieldReadImpl]statsRepos.mergeLocalStatistics([CtVariableReadImpl]statsKey, [CtInvocationImpl][CtVariableReadImpl]loStat.getKey());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsObjectData objData = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatisticsUtils.toObjectData([CtVariableReadImpl]key, [CtTypeAccessImpl]StatsType.LOCAL, [CtInvocationImpl][CtVariableReadImpl]loStat.getKey());
                [CtInvocationImpl][CtVariableReadImpl]collected.put([CtVariableReadImpl]objData, [CtInvocationImpl][CtVariableReadImpl]loStat.getValue());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.warning([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to complete request %s due to error %s", [CtInvocationImpl][CtVariableReadImpl]req.reqId(), [CtInvocationImpl][CtVariableReadImpl]e.getMessage()));
                [CtCommentImpl]// TODO: send cancel to originator node
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionResponse([CtInvocationImpl][CtVariableReadImpl]req.colId(), [CtInvocationImpl][CtVariableReadImpl]req.reqId(), [CtVariableReadImpl]collected);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]locNode.equals([CtVariableReadImpl]nodeId))[CtBlockImpl]
            [CtInvocationImpl]receiveLocalStatistics([CtVariableReadImpl]nodeId, [CtVariableReadImpl]res);
        else [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.io().sendToCustomTopic([CtVariableReadImpl]nodeId, [CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.TOPIC, [CtVariableReadImpl]res, [CtTypeAccessImpl]GridIoPolicy.QUERY_POOL);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]log.isDebugEnabled())[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to send statistics collection result to node %s in response to colId %s, reqId %s", [CtVariableReadImpl]nodeId, [CtInvocationImpl][CtVariableReadImpl]req.colId(), [CtInvocationImpl][CtVariableReadImpl]req.reqId()));

            }
            [CtInvocationImpl][CtCommentImpl]// Not local collection - remove by its reqId.
            [CtFieldReadImpl]currCollections.updateCollection([CtInvocationImpl][CtVariableReadImpl]req.reqId(), [CtLambdaImpl]([CtParameterImpl] s) -> [CtLiteralImpl]null);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Schedule statistics collection by specified request.
     *
     * @param nodeId
     * 		request origin node.
     * @param msg
     * 		request message.
     */
    private [CtTypeReferenceImpl]void handleCollectionRequest([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest msg) [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]msg.reqId() != [CtLiteralImpl]null : [CtLiteralImpl]"Got statistics collection request without request id";
        [CtInvocationImpl][CtFieldReadImpl]currCollections.addActiveCollectionStatus([CtInvocationImpl][CtVariableReadImpl]msg.reqId(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatCollectionStatus([CtInvocationImpl][CtVariableReadImpl]msg.colId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.keys().keySet(), [CtLiteralImpl]null));
        [CtInvocationImpl][CtFieldReadImpl]statMgmtPool.submit([CtLambdaImpl]() -> [CtInvocationImpl]processLocal([CtVariableReadImpl]nodeId, [CtVariableReadImpl]msg));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send requests to target nodes except of local one.
     *
     * @param reqs
     * 		Collection of addressed requests to send.
     * @return Collection of addressed requests that has errors while sending or {@code null} if all requests was send
    successfully.
     */
    private <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.apache.ignite.plugin.extensions.communication.Message> [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeParameterReferenceImpl]T>> sendRequests([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeParameterReferenceImpl]T>> reqs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID locNode = [CtInvocationImpl][CtFieldReadImpl]ctx.localNodeId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeParameterReferenceImpl]T>> res = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest<[CtTypeParameterReferenceImpl]T> req : [CtVariableReadImpl]reqs) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]locNode.equals([CtInvocationImpl][CtVariableReadImpl]req.nodeId()))[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.io().sendToCustomTopic([CtInvocationImpl][CtVariableReadImpl]req.nodeId(), [CtFieldReadImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsManagerImpl.TOPIC, [CtInvocationImpl][CtVariableReadImpl]req.req(), [CtTypeAccessImpl]GridIoPolicy.QUERY_POOL);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]res == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]res = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

                [CtInvocationImpl][CtVariableReadImpl]res.add([CtVariableReadImpl]req);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]res;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle node left event:
     * 1) Cancel all collection tasks which expect specified node statistics result.
     * 2) Cancel collection task requested by node left.
     *
     * @param nodeId
     * 		leaved node id.
     */
    private [CtTypeReferenceImpl]void onNodeLeft([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID nodeId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtArrayTypeReferenceImpl]int[]>> failedCollections = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtFieldReadImpl]currCollections.updateAllCollections([CtLambdaImpl]([CtParameterImpl] colStat) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest[] nodeRequests = [CtInvocationImpl](([CtArrayTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest[]) ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]colStat.remainingCollectionReqs().values().stream().filter([CtLambdaImpl]([CtParameterImpl] addReq) -> [CtInvocationImpl][CtVariableReadImpl]nodeId.equals([CtInvocationImpl][CtVariableReadImpl]addReq.nodeId())).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.StatsAddrRequest::req).toArray()));
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.F.isEmpty([CtVariableReadImpl]nodeRequests)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsKeyMessage, [CtArrayTypeReferenceImpl]int[]> failedKeys = [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.IgniteStatisticsRequestCollection.extractFailed([CtVariableReadImpl]nodeRequests);
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]StatsAddrRequest<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.stat.messages.StatsCollectionRequest>> reqs = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]currCollections.generateCollectionRequests([CtInvocationImpl][CtVariableReadImpl]colStat.colId(), [CtInvocationImpl][CtVariableReadImpl]colStat.keys(), [CtVariableReadImpl]failedKeys);
                    [CtInvocationImpl][CtCommentImpl]// Map<UUID, StatsCollectionRequest> msgs = reqs.stream().collect(Collectors.toMap(
                    [CtCommentImpl]// StatsAddrRequest::nodeId, StatsAddrRequest::req));
                    [CtCommentImpl]// TODO: resend it
                    sendRequests([CtVariableReadImpl]reqs);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// TODO
                    [CtTypeAccessImpl]org.apache.ignite.internal.processors.query.stat.e.printStackTrace();
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        });
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Listener to handle nodeLeft/nodeFailed and call onNodeLeft method.
     */
    private class NodeLeftListener implements [CtTypeReferenceImpl]org.apache.ignite.internal.managers.eventstorage.GridLocalEventListener {
        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onEvent([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.events.Event evt) [CtBlockImpl]{
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]evt.type() == [CtFieldReadImpl]org.apache.ignite.events.EventType.EVT_NODE_FAILED) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]evt.type() == [CtFieldReadImpl]org.apache.ignite.events.EventType.EVT_NODE_LEFT);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.UUID nodeId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.ignite.events.DiscoveryEvent) (evt)).eventNode().id();
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]ctx.closure().runLocalSafe([CtLambdaImpl]() -> [CtInvocationImpl]onNodeLeft([CtVariableReadImpl]nodeId), [CtTypeAccessImpl]GridIoPolicy.QUERY_POOL);
        }
    }
}
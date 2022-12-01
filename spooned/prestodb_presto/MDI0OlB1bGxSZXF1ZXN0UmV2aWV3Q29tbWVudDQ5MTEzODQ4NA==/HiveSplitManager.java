[CompilationUnitImpl][CtCommentImpl]/* Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.facebook.presto.hive;
[CtUnresolvedImport]import com.facebook.presto.spi.WarningCollector;
[CtUnresolvedImport]import static com.facebook.presto.hive.BackgroundHiveSplitLoader.BucketSplitInfo.createBucketSplitInfo;
[CtUnresolvedImport]import static com.facebook.presto.hive.metastore.MetastoreUtil.makePartName;
[CtUnresolvedImport]import com.facebook.presto.spi.ConnectorSplitSource;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import static com.google.common.collect.Iterables.transform;
[CtUnresolvedImport]import com.facebook.airlift.stats.CounterStat;
[CtUnresolvedImport]import static com.facebook.presto.spi.StandardErrorCode.GENERIC_INTERNAL_ERROR;
[CtUnresolvedImport]import static com.facebook.presto.spi.StandardErrorCode.SERVER_SHUTTING_DOWN;
[CtUnresolvedImport]import com.facebook.presto.spi.connector.ConnectorTransactionHandle;
[CtUnresolvedImport]import static com.facebook.presto.hive.HiveSessionProperties.isOfflineDataDebugModeEnabled;
[CtUnresolvedImport]import com.facebook.presto.spi.SchemaTableName;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkArgument;
[CtUnresolvedImport]import static com.google.common.collect.Iterables.concat;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import com.facebook.presto.spi.connector.ConnectorSplitManager;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import static com.facebook.presto.spi.connector.ConnectorSplitManager.SplitSchedulingStrategy.GROUPED_SCHEDULING;
[CtUnresolvedImport]import static com.google.common.collect.ImmutableList.toImmutableList;
[CtImportImpl]import static java.lang.String.format;
[CtUnresolvedImport]import com.facebook.presto.hive.metastore.SemiTransactionalHiveMetastore;
[CtUnresolvedImport]import com.facebook.presto.spi.ConnectorSession;
[CtUnresolvedImport]import io.airlift.units.DataSize;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.google.common.collect.Iterables;
[CtUnresolvedImport]import org.weakref.jmx.Nested;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import static com.facebook.presto.hive.HiveColumnHandle.isPathColumnHandle;
[CtUnresolvedImport]import static com.facebook.presto.hive.metastore.MetastoreUtil.getProtectMode;
[CtUnresolvedImport]import com.facebook.presto.hive.metastore.Table;
[CtUnresolvedImport]import com.facebook.presto.hive.metastore.Column;
[CtUnresolvedImport]import static com.facebook.presto.hive.HiveColumnHandle.ColumnType.REGULAR;
[CtImportImpl]import static java.util.Objects.requireNonNull;
[CtUnresolvedImport]import com.facebook.presto.spi.ConnectorTableLayoutHandle;
[CtUnresolvedImport]import static com.facebook.presto.hive.HiveSessionProperties.shouldIgnoreUnreadablePartition;
[CtUnresolvedImport]import com.facebook.presto.spi.PrestoException;
[CtUnresolvedImport]import static com.google.common.base.Strings.isNullOrEmpty;
[CtUnresolvedImport]import com.facebook.presto.hive.metastore.Partition;
[CtUnresolvedImport]import static com.facebook.presto.hive.HivePartition.UNPARTITIONED_ID;
[CtImportImpl]import static java.lang.Math.min;
[CtUnresolvedImport]import com.facebook.airlift.concurrent.BoundedExecutor;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import com.facebook.presto.common.Subfield;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import org.weakref.jmx.Managed;
[CtImportImpl]import java.util.concurrent.Executor;
[CtUnresolvedImport]import static com.google.common.collect.ImmutableSet.toImmutableSet;
[CtUnresolvedImport]import com.facebook.presto.spi.PrestoWarning;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import static com.facebook.presto.hive.metastore.MetastoreUtil.verifyOnline;
[CtUnresolvedImport]import com.facebook.presto.hive.HiveBucketing.HiveBucketFilter;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import com.google.common.collect.AbstractIterator;
[CtUnresolvedImport]import static com.google.common.collect.Iterables.getOnlyElement;
[CtUnresolvedImport]import com.facebook.presto.spi.FixedSplitSource;
[CtImportImpl]import java.util.concurrent.RejectedExecutionException;
[CtUnresolvedImport]import com.facebook.presto.common.predicate.Domain;
[CtUnresolvedImport]import com.facebook.presto.common.predicate.TupleDomain;
[CtUnresolvedImport]import com.facebook.presto.spi.TableNotFoundException;
[CtUnresolvedImport]import com.google.common.collect.Ordering;
[CtUnresolvedImport]import static org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category.STRUCT;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtClassImpl]public class HiveSplitManager implements [CtTypeReferenceImpl]com.facebook.presto.spi.connector.ConnectorSplitManager {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String OBJECT_NOT_READABLE = [CtLiteralImpl]"object_not_readable";

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.HiveTransactionManager hiveTransactionManager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.NamenodeStats namenodeStats;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.HdfsEnvironment hdfsEnvironment;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.DirectoryLister directoryLister;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.Executor executor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.CoercionPolicy coercionPolicy;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int maxOutstandingSplits;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.airlift.units.DataSize maxOutstandingSplitsSize;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int minPartitionBatchSize;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int maxPartitionBatchSize;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int maxInitialSplits;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int splitLoaderConcurrency;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean recursiveDfsWalkerEnabled;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat highMemorySplitSourceCounter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.CacheQuotaRequirementProvider cacheQuotaRequirementProvider;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.hive.HiveEncryptionInformationProvider encryptionInformationProvider;

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    public HiveSplitManager([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveClientConfig hiveClientConfig, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.CacheQuotaRequirementProvider cacheQuotaRequirementProvider, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveTransactionManager hiveTransactionManager, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.NamenodeStats namenodeStats, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HdfsEnvironment hdfsEnvironment, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.DirectoryLister directoryLister, [CtParameterImpl][CtAnnotationImpl]@com.facebook.presto.hive.ForHiveClient
    [CtTypeReferenceImpl]java.util.concurrent.ExecutorService executorService, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.CoercionPolicy coercionPolicy, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveEncryptionInformationProvider encryptionInformationProvider) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]hiveTransactionManager, [CtVariableReadImpl]namenodeStats, [CtVariableReadImpl]hdfsEnvironment, [CtVariableReadImpl]directoryLister, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.airlift.concurrent.BoundedExecutor([CtVariableReadImpl]executorService, [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMaxSplitIteratorThreads()), [CtVariableReadImpl]coercionPolicy, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMaxOutstandingSplits(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMaxOutstandingSplitsSize(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMinPartitionBatchSize(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMaxPartitionBatchSize(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getMaxInitialSplits(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getSplitLoaderConcurrency(), [CtInvocationImpl][CtVariableReadImpl]hiveClientConfig.getRecursiveDirWalkerEnabled(), [CtVariableReadImpl]cacheQuotaRequirementProvider, [CtVariableReadImpl]encryptionInformationProvider);
    }

    [CtConstructorImpl]public HiveSplitManager([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveTransactionManager hiveTransactionManager, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.NamenodeStats namenodeStats, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HdfsEnvironment hdfsEnvironment, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.DirectoryLister directoryLister, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.Executor executor, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.CoercionPolicy coercionPolicy, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat highMemorySplitSourceCounter, [CtParameterImpl][CtTypeReferenceImpl]int maxOutstandingSplits, [CtParameterImpl][CtTypeReferenceImpl]io.airlift.units.DataSize maxOutstandingSplitsSize, [CtParameterImpl][CtTypeReferenceImpl]int minPartitionBatchSize, [CtParameterImpl][CtTypeReferenceImpl]int maxPartitionBatchSize, [CtParameterImpl][CtTypeReferenceImpl]int maxInitialSplits, [CtParameterImpl][CtTypeReferenceImpl]int splitLoaderConcurrency, [CtParameterImpl][CtTypeReferenceImpl]boolean recursiveDfsWalkerEnabled, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.CacheQuotaRequirementProvider cacheQuotaRequirementProvider, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveEncryptionInformationProvider encryptionInformationProvider) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hiveTransactionManager = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]hiveTransactionManager, [CtLiteralImpl]"hiveTransactionManager is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.namenodeStats = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]namenodeStats, [CtLiteralImpl]"namenodeStats is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hdfsEnvironment = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]hdfsEnvironment, [CtLiteralImpl]"hdfsEnvironment is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.directoryLister = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]directoryLister, [CtLiteralImpl]"directoryLister is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HiveSplitManager.ErrorCodedExecutor([CtVariableReadImpl]executor);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.coercionPolicy = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]coercionPolicy, [CtLiteralImpl]"coercionPolicy is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.highMemorySplitSourceCounter = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]highMemorySplitSourceCounter, [CtLiteralImpl]"highMemorySplitSourceCounter is null");
        [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]maxOutstandingSplits >= [CtLiteralImpl]1, [CtLiteralImpl]"maxOutstandingSplits must be at least 1");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxOutstandingSplits = [CtVariableReadImpl]maxOutstandingSplits;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxOutstandingSplitsSize = [CtVariableReadImpl]maxOutstandingSplitsSize;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minPartitionBatchSize = [CtVariableReadImpl]minPartitionBatchSize;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxPartitionBatchSize = [CtVariableReadImpl]maxPartitionBatchSize;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxInitialSplits = [CtVariableReadImpl]maxInitialSplits;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.splitLoaderConcurrency = [CtVariableReadImpl]splitLoaderConcurrency;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recursiveDfsWalkerEnabled = [CtVariableReadImpl]recursiveDfsWalkerEnabled;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cacheQuotaRequirementProvider = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]cacheQuotaRequirementProvider, [CtLiteralImpl]"cacheQuotaRequirementProvider is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encryptionInformationProvider = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]encryptionInformationProvider, [CtLiteralImpl]"encryptionInformationProvider is null");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.facebook.presto.spi.ConnectorSplitSource getSplits([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.connector.ConnectorTransactionHandle transaction, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.ConnectorSession session, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.ConnectorTableLayoutHandle layoutHandle, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.SplitSchedulingContext splitSchedulingContext) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveTableLayoutHandle layout = [CtVariableReadImpl](([CtTypeReferenceImpl]com.facebook.presto.hive.HiveTableLayoutHandle) (layoutHandle));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.spi.SchemaTableName tableName = [CtInvocationImpl][CtVariableReadImpl]layout.getSchemaTableName();
        [CtLocalVariableImpl][CtCommentImpl]// get table metadata
        [CtTypeReferenceImpl]com.facebook.presto.hive.TransactionalMetadata metadata = [CtInvocationImpl][CtFieldReadImpl]hiveTransactionManager.get([CtVariableReadImpl]transaction);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]metadata == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl]com.facebook.presto.hive.HiveErrorCode.HIVE_TRANSACTION_NOT_FOUND, [CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Transaction not found: %s", [CtVariableReadImpl]transaction));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.SemiTransactionalHiveMetastore metastore = [CtInvocationImpl][CtVariableReadImpl]metadata.getMetastore();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Table table = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]metastore.getTable([CtInvocationImpl][CtVariableReadImpl]tableName.getSchemaName(), [CtInvocationImpl][CtVariableReadImpl]tableName.getTableName()).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.TableNotFoundException([CtVariableReadImpl]tableName));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]com.facebook.presto.hive.HiveSessionProperties.isOfflineDataDebugModeEnabled([CtVariableReadImpl]session)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// verify table is not marked as non-readable
            [CtTypeReferenceImpl]java.lang.String tableNotReadable = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getParameters().get([CtFieldReadImpl]com.facebook.presto.hive.HiveSplitManager.OBJECT_NOT_READABLE);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]tableNotReadable)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HiveNotReadableException([CtVariableReadImpl]tableName, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty(), [CtVariableReadImpl]tableNotReadable);
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// get partitions
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition> partitions = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]layout.getPartitions().orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]GENERIC_INTERNAL_ERROR, [CtLiteralImpl]"Layout does not contain partitions"));
        [CtLocalVariableImpl][CtCommentImpl]// short circuit if we don't have any partitions
        [CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition partition = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.getFirst([CtVariableReadImpl]partitions, [CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partition == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.FixedSplitSource([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveBucketing.HiveBucketFilter> bucketFilter = [CtInvocationImpl][CtVariableReadImpl]layout.getBucketFilter();
        [CtLocalVariableImpl][CtCommentImpl]// validate bucket bucketed execution
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveBucketHandle> bucketHandle = [CtInvocationImpl][CtVariableReadImpl]layout.getBucketHandle();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]splitSchedulingContext.getSplitSchedulingStrategy() == [CtFieldReadImpl]GROUPED_SCHEDULING) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]bucketHandle.isPresent())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl]StandardErrorCode.GENERIC_INTERNAL_ERROR, [CtLiteralImpl]"SchedulingPolicy is bucketed, but BucketHandle is not present");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bucketHandle.isPresent()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bucketHandle.get().getReadBucketCount() > [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bucketHandle.get().getTableBucketCount()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl]StandardErrorCode.GENERIC_INTERNAL_ERROR, [CtLiteralImpl]"readBucketCount (%s) is greater than the tableBucketCount (%s) which generally points to an issue in plan generation");
            }
        }
        [CtAssignmentImpl][CtCommentImpl]// sort partitions
        [CtVariableWriteImpl]partitions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Ordering.natural().onResultOf([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]HivePartition::getPartitionId).reverse().sortedCopy([CtVariableReadImpl]partitions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata> hivePartitions = [CtInvocationImpl]getPartitionMetadata([CtVariableReadImpl]metastore, [CtVariableReadImpl]table, [CtVariableReadImpl]tableName, [CtVariableReadImpl]partitions, [CtVariableReadImpl]bucketHandle, [CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]splitSchedulingContext.getWarningCollector(), [CtInvocationImpl][CtVariableReadImpl]layout.getRequestedColumns(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]layout.getPredicateColumns().values()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveSplitLoader hiveSplitLoader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.BackgroundHiveSplitLoader([CtVariableReadImpl]table, [CtVariableReadImpl]hivePartitions, [CtInvocationImpl]com.facebook.presto.hive.HiveSplitManager.getPathDomain([CtInvocationImpl][CtVariableReadImpl]layout.getDomainPredicate(), [CtInvocationImpl][CtVariableReadImpl]layout.getPredicateColumns()), [CtInvocationImpl]BucketSplitInfo.createBucketSplitInfo([CtVariableReadImpl]bucketHandle, [CtVariableReadImpl]bucketFilter), [CtVariableReadImpl]session, [CtFieldReadImpl]hdfsEnvironment, [CtFieldReadImpl]namenodeStats, [CtFieldReadImpl]directoryLister, [CtFieldReadImpl]executor, [CtFieldReadImpl]splitLoaderConcurrency, [CtFieldReadImpl]recursiveDfsWalkerEnabled, [CtInvocationImpl][CtVariableReadImpl]splitSchedulingContext.schedulerUsesHostAddresses(), [CtInvocationImpl][CtVariableReadImpl]layout.isPartialAggregationsPushedDown());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveSplitSource splitSource;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.CacheQuotaRequirement cacheQuotaRequirement = [CtInvocationImpl][CtFieldReadImpl]cacheQuotaRequirementProvider.getCacheQuotaRequirement([CtInvocationImpl][CtVariableReadImpl]table.getDatabaseName(), [CtInvocationImpl][CtVariableReadImpl]table.getTableName());
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]splitSchedulingContext.getSplitSchedulingStrategy()) {
            [CtCaseImpl]case [CtFieldReadImpl]UNGROUPED_SCHEDULING :
                [CtAssignmentImpl][CtVariableWriteImpl]splitSource = [CtInvocationImpl][CtTypeAccessImpl]com.facebook.presto.hive.HiveSplitSource.allAtOnce([CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]table.getDatabaseName(), [CtInvocationImpl][CtVariableReadImpl]table.getTableName(), [CtVariableReadImpl]cacheQuotaRequirement, [CtFieldReadImpl]maxInitialSplits, [CtFieldReadImpl]maxOutstandingSplits, [CtFieldReadImpl]maxOutstandingSplitsSize, [CtVariableReadImpl]hiveSplitLoader, [CtFieldReadImpl]executor, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]GROUPED_SCHEDULING :
                [CtAssignmentImpl][CtVariableWriteImpl]splitSource = [CtInvocationImpl][CtTypeAccessImpl]com.facebook.presto.hive.HiveSplitSource.bucketed([CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]table.getDatabaseName(), [CtInvocationImpl][CtVariableReadImpl]table.getTableName(), [CtVariableReadImpl]cacheQuotaRequirement, [CtFieldReadImpl]maxInitialSplits, [CtFieldReadImpl]maxOutstandingSplits, [CtFieldReadImpl]maxOutstandingSplitsSize, [CtVariableReadImpl]hiveSplitLoader, [CtFieldReadImpl]executor, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]REWINDABLE_GROUPED_SCHEDULING :
                [CtAssignmentImpl][CtVariableWriteImpl]splitSource = [CtInvocationImpl][CtTypeAccessImpl]com.facebook.presto.hive.HiveSplitSource.bucketedRewindable([CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]table.getDatabaseName(), [CtInvocationImpl][CtVariableReadImpl]table.getTableName(), [CtVariableReadImpl]cacheQuotaRequirement, [CtFieldReadImpl]maxInitialSplits, [CtFieldReadImpl]maxOutstandingSplitsSize, [CtVariableReadImpl]hiveSplitLoader, [CtFieldReadImpl]executor, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat());
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown splitSchedulingStrategy: " + [CtInvocationImpl][CtVariableReadImpl]splitSchedulingContext.getSplitSchedulingStrategy());
        }
        [CtInvocationImpl][CtVariableReadImpl]hiveSplitLoader.start([CtVariableReadImpl]splitSource);
        [CtReturnImpl]return [CtVariableReadImpl]splitSource;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.facebook.presto.common.predicate.Domain> getPathDomain([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.common.predicate.TupleDomain<[CtTypeReferenceImpl]com.facebook.presto.common.Subfield> domainPredicate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle> predicateColumns) [CtBlockImpl]{
        [CtInvocationImpl]checkArgument([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]domainPredicate.isNone(), [CtLiteralImpl]"Unexpected domain predicate: none");
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]domainPredicate.getDomains().get().entrySet().stream().filter([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl]isPathColumnHandle([CtInvocationImpl][CtVariableReadImpl]predicateColumns.get([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().getRootName()))).findFirst().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Map.Entry::getValue);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.weakref.jmx.Managed
    [CtAnnotationImpl]@org.weakref.jmx.Nested
    public [CtTypeReferenceImpl]com.facebook.airlift.stats.CounterStat getHighMemorySplitSource() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]highMemorySplitSourceCounter;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata> getPartitionMetadata([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.SemiTransactionalHiveMetastore metastore, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Table table, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.SchemaTableName tableName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition> hivePartitions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveBucketHandle> hiveBucketHandle, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.ConnectorSession session, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.WarningCollector warningCollector, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle>> requestedColumns, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle> predicateColumns) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]hivePartitions.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle>> allRequestedColumns = [CtInvocationImpl]com.facebook.presto.hive.HiveSplitManager.mergeRequestedAndPredicateColumns([CtVariableReadImpl]requestedColumns, [CtVariableReadImpl]predicateColumns);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]hivePartitions.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition firstPartition = [CtInvocationImpl]getOnlyElement([CtVariableReadImpl]hivePartitions);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]firstPartition.getPartitionId().equals([CtTypeAccessImpl]com.facebook.presto.hive.HivePartition.UNPARTITIONED_ID)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata([CtVariableReadImpl]firstPartition, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of(), [CtInvocationImpl][CtFieldReadImpl]encryptionInformationProvider.getReadEncryptionInformation([CtVariableReadImpl]session, [CtVariableReadImpl]table, [CtVariableReadImpl]allRequestedColumns)));
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition>> partitionNameBatches = [CtInvocationImpl]com.facebook.presto.hive.HiveSplitManager.partitionExponentially([CtVariableReadImpl]hivePartitions, [CtFieldReadImpl]minPartitionBatchSize, [CtFieldReadImpl]maxPartitionBatchSize);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata>> partitionBatches = [CtInvocationImpl]transform([CtVariableReadImpl]partitionNameBatches, [CtLambdaImpl]([CtParameterImpl] partitionBatch) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Partition>> batch = [CtInvocationImpl][CtVariableReadImpl]metastore.getPartitionsByNames([CtInvocationImpl][CtVariableReadImpl]tableName.getSchemaName(), [CtInvocationImpl][CtVariableReadImpl]tableName.getTableName(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.transform([CtVariableReadImpl]partitionBatch, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.facebook.presto.hive.HivePartition::getPartitionId));
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableMap.Builder<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Partition> partitionBuilder = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.builder();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Partition>> entry : [CtInvocationImpl][CtVariableReadImpl]batch.entrySet()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().isPresent()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]HIVE_PARTITION_DROPPED_DURING_QUERY, [CtBinaryOperatorImpl][CtLiteralImpl]"Partition no longer exists: " + [CtInvocationImpl][CtVariableReadImpl]entry.getKey());
                }
                [CtInvocationImpl][CtVariableReadImpl]partitionBuilder.put([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().get());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Partition> partitions = [CtInvocationImpl][CtVariableReadImpl]partitionBuilder.build();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partitionBatch.size() != [CtInvocationImpl][CtVariableReadImpl]partitions.size()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]GENERIC_INTERNAL_ERROR, [CtInvocationImpl]format([CtLiteralImpl]"Expected %s partitions but found %s", [CtInvocationImpl][CtVariableReadImpl]partitionBatch.size(), [CtInvocationImpl][CtVariableReadImpl]partitions.size()));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Optional<[CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.hive.EncryptionInformation>> encryptionInformationForPartitions = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]encryptionInformationProvider.getReadEncryptionInformation([CtVariableReadImpl]session, [CtVariableReadImpl]table, [CtVariableReadImpl]allRequestedColumns, [CtVariableReadImpl]partitions);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableList.Builder<[CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata> results = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.builder();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HivePartition hivePartition : [CtVariableReadImpl]partitionBatch) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Partition partition = [CtInvocationImpl][CtVariableReadImpl]partitions.get([CtInvocationImpl][CtVariableReadImpl]hivePartition.getPartitionId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partition == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]GENERIC_INTERNAL_ERROR, [CtBinaryOperatorImpl][CtLiteralImpl]"Partition not loaded: " + [CtVariableReadImpl]hivePartition);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partName = [CtInvocationImpl]makePartName([CtInvocationImpl][CtVariableReadImpl]table.getPartitionColumns(), [CtInvocationImpl][CtVariableReadImpl]partition.getValues());
                [CtLocalVariableImpl][CtTypeReferenceImpl]Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.EncryptionInformation> encryptionInformation = [CtInvocationImpl][CtVariableReadImpl]encryptionInformationForPartitions.map([CtLambdaImpl]([CtParameterImpl] metadata) -> [CtInvocationImpl][CtVariableReadImpl]metadata.get([CtInvocationImpl][CtVariableReadImpl]hivePartition.getPartitionId()));
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isOfflineDataDebugModeEnabled([CtVariableReadImpl]session)) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// verify partition is online
                    verifyOnline([CtVariableReadImpl]tableName, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]partName), [CtInvocationImpl]getProtectMode([CtVariableReadImpl]partition), [CtInvocationImpl][CtVariableReadImpl]partition.getParameters());
                    [CtLocalVariableImpl][CtCommentImpl]// verify partition is not marked as non-readable
                    [CtTypeReferenceImpl]java.lang.String partitionNotReadable = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partition.getParameters().get([CtFieldReadImpl][CtFieldReferenceImpl]OBJECT_NOT_READABLE);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]partitionNotReadable)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]shouldIgnoreUnreadablePartition([CtVariableReadImpl]session)) [CtBlockImpl]{
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HiveNotReadableException([CtVariableReadImpl]tableName, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]partName), [CtVariableReadImpl]partitionNotReadable);
                        }
                        [CtInvocationImpl][CtVariableReadImpl]warningCollector.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoWarning([CtFieldReadImpl][CtFieldReferenceImpl]PARTITION_NOT_READABLE, [CtInvocationImpl]format([CtLiteralImpl]"Table '%s' partition '%s' is not readable: %s", [CtVariableReadImpl]tableName, [CtVariableReadImpl]partName, [CtVariableReadImpl]partitionNotReadable)));
                        [CtContinueImpl]continue;
                    }
                }
                [CtLocalVariableImpl][CtCommentImpl]// Verify that the partition schema matches the table schema.
                [CtCommentImpl]// Either adding or dropping columns from the end of the table
                [CtCommentImpl]// without modifying existing partitions is allowed, but every
                [CtCommentImpl]// column that exists in both the table and partition must have
                [CtCommentImpl]// the same type.
                [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Column> tableColumns = [CtInvocationImpl][CtVariableReadImpl]table.getDataColumns();
                [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Column> partitionColumns = [CtInvocationImpl][CtVariableReadImpl]partition.getColumns();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tableColumns == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]partitionColumns == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]HIVE_INVALID_METADATA, [CtInvocationImpl]format([CtLiteralImpl]"Table '%s' or partition '%s' has null columns", [CtVariableReadImpl]tableName, [CtVariableReadImpl]partName));
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableMap.Builder<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Column> partitionSchemaDifference = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.builder();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]partitionColumns.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.metastore.Column partitionColumn = [CtInvocationImpl][CtVariableReadImpl]partitionColumns.get([CtVariableReadImpl]i);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtInvocationImpl][CtVariableReadImpl]tableColumns.size()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]partitionSchemaDifference.put([CtVariableReadImpl]i, [CtVariableReadImpl]partitionColumn);
                        [CtContinueImpl]continue;
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveType tableType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableColumns.get([CtVariableReadImpl]i).getType();
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tableType.equals([CtInvocationImpl][CtVariableReadImpl]partitionColumn.getType())) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]coercionPolicy.canCoerce([CtInvocationImpl][CtVariableReadImpl]partitionColumn.getType(), [CtVariableReadImpl]tableType)) [CtBlockImpl]{
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]HIVE_PARTITION_SCHEMA_MISMATCH, [CtInvocationImpl]format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"" + [CtLiteralImpl]"There is a mismatch between the table and partition schemas. ") + [CtLiteralImpl]"The types are incompatible and cannot be coerced. ") + [CtLiteralImpl]"The column '%s' in table '%s' is declared as type '%s', ") + [CtLiteralImpl]"but partition '%s' declared column '%s' as type '%s'.", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableColumns.get([CtVariableReadImpl]i).getName(), [CtVariableReadImpl]tableName, [CtVariableReadImpl]tableType, [CtVariableReadImpl]partName, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partitionColumns.get([CtVariableReadImpl]i).getName(), [CtInvocationImpl][CtVariableReadImpl]partitionColumn.getType()));
                        }
                        [CtInvocationImpl][CtVariableReadImpl]partitionSchemaDifference.put([CtVariableReadImpl]i, [CtVariableReadImpl]partitionColumn);
                        [CtContinueImpl]continue;
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableColumns.get([CtVariableReadImpl]i).getName().equals([CtInvocationImpl][CtVariableReadImpl]partitionColumn.getName())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]partitionSchemaDifference.put([CtVariableReadImpl]i, [CtVariableReadImpl]partitionColumn);
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]hiveBucketHandle.isPresent() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hiveBucketHandle.get().isVirtuallyBucketed())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Optional<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveBucketProperty> partitionBucketProperty = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partition.getStorage().getBucketProperty();
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]partitionBucketProperty.isPresent()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]HIVE_PARTITION_SCHEMA_MISMATCH, [CtInvocationImpl]format([CtLiteralImpl]"Hive table (%s) is bucketed but partition (%s) is not bucketed", [CtInvocationImpl][CtVariableReadImpl]hivePartition.getTableName(), [CtInvocationImpl][CtVariableReadImpl]hivePartition.getPartitionId()));
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int tableBucketCount = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hiveBucketHandle.get().getTableBucketCount();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int partitionBucketCount = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partitionBucketProperty.get().getBucketCount();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> tableBucketColumns = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]hiveBucketHandle.get().getColumns().stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.facebook.presto.hive.HiveColumnHandle::getName).collect([CtInvocationImpl]toImmutableList());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> partitionBucketColumns = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partitionBucketProperty.get().getBucketedBy();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]tableBucketColumns.equals([CtVariableReadImpl]partitionBucketColumns)) || [CtUnaryOperatorImpl](![CtInvocationImpl]isBucketCountCompatible([CtVariableReadImpl]tableBucketCount, [CtVariableReadImpl]partitionBucketCount))) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl][CtFieldReferenceImpl]HIVE_PARTITION_SCHEMA_MISMATCH, [CtInvocationImpl]format([CtLiteralImpl]"Hive table (%s) bucketing (columns=%s, buckets=%s) is not compatible with partition (%s) bucketing (columns=%s, buckets=%s)", [CtInvocationImpl][CtVariableReadImpl]hivePartition.getTableName(), [CtVariableReadImpl]tableBucketColumns, [CtVariableReadImpl]tableBucketCount, [CtInvocationImpl][CtVariableReadImpl]hivePartition.getPartitionId(), [CtVariableReadImpl]partitionBucketColumns, [CtVariableReadImpl]partitionBucketCount));
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]results.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HivePartitionMetadata([CtVariableReadImpl]hivePartition, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]partition), [CtInvocationImpl][CtVariableReadImpl]partitionSchemaDifference.build(), [CtVariableReadImpl]encryptionInformation));
            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]results.build();
        });
        [CtReturnImpl]return [CtInvocationImpl]concat([CtVariableReadImpl]partitionBatches);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle>> mergeRequestedAndPredicateColumns([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle>> requestedColumns, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle> predicateColumns) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]requestedColumns.isPresent()) || [CtInvocationImpl][CtVariableReadImpl]predicateColumns.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]requestedColumns;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle>> nameToColumnHandles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.concat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]requestedColumns.get().stream(), [CtInvocationImpl][CtVariableReadImpl]predicateColumns.stream()).filter([CtLambdaImpl]([CtParameterImpl] column) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]column.getColumnType() == [CtFieldReadImpl][CtFieldReferenceImpl]REGULAR).forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nameToColumnHandles.computeIfAbsent([CtInvocationImpl][CtVariableReadImpl]column.getName(), [CtLambdaImpl]([CtParameterImpl] key) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.hive.HashSet<>()).add([CtVariableReadImpl]column));
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nameToColumnHandles.values().stream().map([CtLambdaImpl]([CtParameterImpl] columnHandles) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle finalHandle = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle columnHandle : [CtVariableReadImpl]columnHandles) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]finalHandle == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]finalHandle = [CtVariableReadImpl]columnHandle;
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]finalHandle.getHiveType().getCategory() != [CtTypeAccessImpl]com.facebook.presto.hive.STRUCT) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]finalHandle.getRequiredSubfields().isEmpty()) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]columnHandle.getRequiredSubfields().isEmpty()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]finalHandle = [CtVariableReadImpl]columnHandle;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]finalHandle = [CtInvocationImpl](([CtTypeReferenceImpl]com.facebook.presto.hive.HiveColumnHandle) ([CtVariableReadImpl]finalHandle.withRequiredSubfields([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.copyOf([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.<[CtTypeReferenceImpl]com.facebook.presto.common.Subfield>builder().addAll([CtInvocationImpl][CtVariableReadImpl]finalHandle.getRequiredSubfields()).addAll([CtInvocationImpl][CtVariableReadImpl]columnHandle.getRequiredSubfields()).build())))));
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]finalHandle;
        }).collect([CtInvocationImpl]toImmutableSet()));
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isBucketCountCompatible([CtParameterImpl][CtTypeReferenceImpl]int tableBucketCount, [CtParameterImpl][CtTypeReferenceImpl]int partitionBucketCount) [CtBlockImpl]{
        [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tableBucketCount > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]partitionBucketCount > [CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int larger = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]tableBucketCount, [CtVariableReadImpl]partitionBucketCount);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int smaller = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]tableBucketCount, [CtVariableReadImpl]partitionBucketCount);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]larger % [CtVariableReadImpl]smaller) != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// must be evenly divisible
            return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.bitCount([CtBinaryOperatorImpl][CtVariableReadImpl]larger / [CtVariableReadImpl]smaller) != [CtLiteralImpl]1) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// ratio must be power of two
            return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Partition the given list in exponentially (power of 2) increasing batch sizes starting at 1 up to maxBatchSize
     */
    private static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]T>> partitionExponentially([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]T> values, [CtParameterImpl][CtTypeReferenceImpl]int minBatchSize, [CtParameterImpl][CtTypeReferenceImpl]int maxBatchSize) [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]() -> [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.collect.AbstractIterator<[CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]T>>()[CtClassImpl] {
            [CtFieldImpl]private [CtTypeReferenceImpl]int currentSize = [CtVariableReadImpl]minBatchSize;

            [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Iterator<[CtTypeParameterReferenceImpl]T> iterator = [CtInvocationImpl][CtVariableReadImpl]values.iterator();

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]T> computeNext() [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]iterator.hasNext()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]endOfData();
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableList.Builder<[CtTypeParameterReferenceImpl]T> builder = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.builder();
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]iterator.hasNext() && [CtBinaryOperatorImpl]([CtVariableReadImpl]count < [CtFieldReadImpl]currentSize)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]builder.add([CtInvocationImpl][CtFieldReadImpl]iterator.next());
                    [CtUnaryOperatorImpl]++[CtVariableWriteImpl]count;
                } 
                [CtAssignmentImpl][CtFieldWriteImpl]currentSize = [CtInvocationImpl]java.lang.Math.min([CtVariableReadImpl]maxBatchSize, [CtBinaryOperatorImpl][CtFieldReadImpl]currentSize * [CtLiteralImpl]2);
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
            }
        };
    }

    [CtClassImpl]private static class ErrorCodedExecutor implements [CtTypeReferenceImpl]java.util.concurrent.Executor {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.Executor delegate;

        [CtConstructorImpl]private ErrorCodedExecutor([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.Executor delegate) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.delegate = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]delegate, [CtLiteralImpl]"delegate is null");
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable command) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]delegate.execute([CtVariableReadImpl]command);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.concurrent.RejectedExecutionException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.spi.PrestoException([CtFieldReadImpl]StandardErrorCode.SERVER_SHUTTING_DOWN, [CtLiteralImpl]"Server is shutting down", [CtVariableReadImpl]e);
            }
        }
    }
}
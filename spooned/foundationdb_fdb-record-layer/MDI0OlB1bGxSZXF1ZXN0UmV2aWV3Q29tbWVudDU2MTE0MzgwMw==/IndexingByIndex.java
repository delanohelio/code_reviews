[CompilationUnitImpl][CtCommentImpl]/* OnlineIndexerByIndex.java

This source file is part of the FoundationDB open source project

Copyright 2015-2020 Apple Inc. and the FoundationDB project authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.apple.foundationdb.record.provider.foundationdb;
[CtUnresolvedImport]import com.apple.foundationdb.record.RecordMetaData;
[CtUnresolvedImport]import com.apple.foundationdb.record.logging.LogMessageKeys;
[CtUnresolvedImport]import com.apple.foundationdb.async.AsyncUtil;
[CtUnresolvedImport]import com.apple.foundationdb.record.metadata.MetaDataException;
[CtUnresolvedImport]import com.apple.foundationdb.async.RangeSet;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import com.apple.foundationdb.subspace.Subspace;
[CtUnresolvedImport]import com.apple.foundationdb.MutationType;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import com.apple.foundationdb.record.ExecuteProperties;
[CtUnresolvedImport]import com.apple.foundationdb.record.IndexScanType;
[CtUnresolvedImport]import com.apple.foundationdb.tuple.Tuple;
[CtUnresolvedImport]import com.apple.foundationdb.record.RecordCursorResult;
[CtUnresolvedImport]import com.apple.foundationdb.record.logging.KeyValueLogMessage;
[CtUnresolvedImport]import com.apple.foundationdb.record.RecordCoreException;
[CtUnresolvedImport]import com.apple.foundationdb.record.ScanProperties;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import com.apple.foundationdb.tuple.ByteArrayUtil2;
[CtUnresolvedImport]import com.apple.foundationdb.record.IsolationLevel;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import com.apple.foundationdb.TransactionContext;
[CtUnresolvedImport]import com.apple.foundationdb.record.TupleRange;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtUnresolvedImport]import com.apple.foundationdb.record.metadata.RecordType;
[CtUnresolvedImport]import com.apple.foundationdb.record.RecordMetaDataProvider;
[CtUnresolvedImport]import com.apple.foundationdb.record.metadata.Index;
[CtUnresolvedImport]import com.google.protobuf.Message;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import com.apple.foundationdb.record.RecordCursor;
[CtUnresolvedImport]import com.apple.foundationdb.record.metadata.IndexTypes;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.apple.foundationdb.annotation.API;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import java.util.concurrent.atomic.AtomicLong;
[CtClassImpl][CtJavaDocImpl]/**
 * This indexer scans records by a source index.
 */
[CtAnnotationImpl]@com.apple.foundationdb.annotation.API([CtFieldReadImpl]API.Status.INTERNAL)
public class IndexingByIndex extends [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingBase {
    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingByIndex.class);

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private final [CtTypeReferenceImpl]OnlineIndexer.IndexFromIndexPolicy policy;

    [CtConstructorImpl]IndexingByIndex([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingCommon common, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.OnlineIndexer.IndexFromIndexPolicy policy) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]common);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.policy = [CtVariableReadImpl]policy;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> buildIndexByEntityAsync() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getRunner().runAsync([CtLambdaImpl]([CtParameterImpl] context) -> [CtInvocationImpl][CtInvocationImpl]openRecordStore([CtVariableReadImpl]context).thenCompose([CtLambdaImpl]([CtParameterImpl] store) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// first validate that both src and tgt are of a single, similar, type
            final [CtTypeReferenceImpl]com.apple.foundationdb.record.RecordMetaData metaData = [CtInvocationImpl][CtVariableReadImpl]store.getRecordMetaData();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.Index srcIndex = [CtInvocationImpl][CtVariableReadImpl]metaData.getIndex([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]policy.getSourceIndex()));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.RecordType> srcRecordTypes = [CtInvocationImpl][CtVariableReadImpl]metaData.recordTypesForIndex([CtVariableReadImpl]srcIndex);
            [CtInvocationImpl]validateOrThrowEx([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.common.isSyntheticIndex(), [CtLiteralImpl]"target index is synthetic");
            [CtInvocationImpl]validateOrThrowEx([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]common.recordTypes.size() == [CtLiteralImpl]1, [CtLiteralImpl]"target index has multiple types");
            [CtInvocationImpl]validateOrThrowEx([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]srcRecordTypes.size() == [CtLiteralImpl]1, [CtLiteralImpl]"source index has multiple types");
            [CtInvocationImpl]validateOrThrowEx([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]srcIndex.getRootExpression().createsDuplicates(), [CtLiteralImpl]"source index creates duplicates");
            [CtInvocationImpl]validateOrThrowEx([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]srcIndex.getType().equals([CtVariableReadImpl]IndexTypes.VALUE), [CtLiteralImpl]"source index is not a VALUE index");
            [CtInvocationImpl]validateOrThrowEx([CtInvocationImpl][CtVariableReadImpl]common.recordTypes.equals([CtVariableReadImpl]srcRecordTypes), [CtLiteralImpl]"source index's type is not equal to target index's");
            [CtReturnImpl][CtCommentImpl]// all valid; back to the future. Note that for practical reasons, readability and idempotency will be validated later
            return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getReadVersionAsync().thenCompose([CtLambdaImpl]([CtParameterImpl] vignore) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.SubspaceProvider subspaceProvider = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.common.getRecordStoreBuilder().getSubspaceProvider();
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subspaceProvider.getSubspaceAsync([CtVariableReadImpl]context).thenCompose([CtLambdaImpl]([CtParameterImpl] subspace) -> [CtInvocationImpl][CtInvocationImpl]buildIndexFromIndex([CtVariableReadImpl]subspaceProvider, [CtVariableReadImpl]subspace).thenCompose([CtLambdaImpl]([CtParameterImpl] vignore2) -> [CtInvocationImpl]markBuilt([CtVariableReadImpl]subspace)));
            });
        }));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> markBuilt([CtParameterImpl][CtTypeReferenceImpl]com.apple.foundationdb.subspace.Subspace subspace) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Grand Finale - after fully building the index, remove all missing ranges in one gulp
        return [CtInvocationImpl][CtInvocationImpl]getRunner().runAsync([CtLambdaImpl]([CtParameterImpl] context) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.Index index = [CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.common.getIndex();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.async.RangeSet rangeSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.apple.foundationdb.async.RangeSet([CtInvocationImpl][CtVariableReadImpl]subspace.subspace([CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.tuple.Tuple.from([CtVariableReadImpl]FDBRecordStore.INDEX_RANGE_SPACE_KEY, [CtInvocationImpl][CtVariableReadImpl]index.getSubspaceKey())));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.TransactionContext tc = [CtInvocationImpl][CtVariableReadImpl]context.ensureActive();
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rangeSet.insertRange([CtVariableReadImpl]tc, [CtLiteralImpl]null, [CtLiteralImpl]null).thenApply([CtLambdaImpl]([CtParameterImpl] bignore) -> [CtLiteralImpl]null);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void maybeLogBuildProgress([CtParameterImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.SubspaceProvider subspaceProvider, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] retCont) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingByIndex.LOGGER.isInfoEnabled() && [CtInvocationImpl]shouldLogBuildProgress()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.Index index = [CtInvocationImpl][CtFieldReadImpl]common.getIndex();
            [CtInvocationImpl][CtFieldReadImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingByIndex.LOGGER.info([CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.logging.KeyValueLogMessage.of([CtLiteralImpl]"Built Range", [CtTypeAccessImpl]LogMessageKeys.INDEX_NAME, [CtInvocationImpl][CtVariableReadImpl]index.getName(), [CtTypeAccessImpl]LogMessageKeys.INDEX_VERSION, [CtInvocationImpl][CtVariableReadImpl]index.getLastModifiedVersion(), [CtInvocationImpl][CtVariableReadImpl]subspaceProvider.logKey(), [CtVariableReadImpl]subspaceProvider, [CtTypeAccessImpl]LogMessageKeys.NEXT_CONTINUATION, [CtVariableReadImpl]retCont, [CtTypeAccessImpl]LogMessageKeys.RECORDS_SCANNED, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]common.getTotalRecordsScanned().get()), [CtTypeAccessImpl]LogMessageKeys.INDEXER_ID, [CtInvocationImpl][CtFieldReadImpl]common.getUuid());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> buildIndexFromIndex([CtParameterImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.SubspaceProvider subspaceProvider, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]com.apple.foundationdb.subspace.Subspace subspace) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtArrayTypeReferenceImpl]byte[]> nextCont = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.async.AsyncUtil.whileTrue([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] cont = [CtInvocationImpl][CtVariableReadImpl]nextCont.get();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.Object> additionalLogMessageKeyValues = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]LogMessageKeys.CALLING_METHOD, [CtLiteralImpl]"buildIndexFromIndex", [CtVariableReadImpl]LogMessageKeys.NEXT_CONTINUATION, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]cont == [CtLiteralImpl]null ? [CtLiteralImpl]"" : [CtVariableReadImpl]cont);
            [CtReturnImpl][CtCommentImpl]// apparently, buildAsync=buildAndCommitWithRetry
            return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]buildCommitRetryAsync([CtLambdaImpl]([CtParameterImpl] store,[CtParameterImpl] recordsScanned) -> [CtInvocationImpl]buildRangeOnly([CtVariableReadImpl]store, [CtVariableReadImpl]cont, [CtVariableReadImpl]recordsScanned), [CtLiteralImpl]true, [CtVariableReadImpl]additionalLogMessageKeyValues).handle([CtLambdaImpl]([CtParameterImpl] retCont,[CtParameterImpl] ex) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ex == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]maybeLogBuildProgress([CtVariableReadImpl]subspaceProvider, [CtVariableReadImpl]retCont);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retCont == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtReturnImpl]return [CtVariableReadImpl]AsyncUtil.READY_FALSE;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]nextCont.set([CtVariableReadImpl]retCont);[CtCommentImpl]// continuation

                    [CtReturnImpl]return [CtInvocationImpl]throttleDelay();
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.RuntimeException unwrappedEx = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRunner().getDatabase().mapAsyncToSyncException([CtVariableReadImpl]ex);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]LOGGER.isInfoEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]LOGGER.info([CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.logging.KeyValueLogMessage.of([CtLiteralImpl]"possibly non-fatal error encountered building range", [CtVariableReadImpl]LogMessageKeys.NEXT_CONTINUATION, [CtVariableReadImpl]nextCont, [CtVariableReadImpl]LogMessageKeys.SUBSPACE, [CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.tuple.ByteArrayUtil2.loggable([CtInvocationImpl][CtVariableReadImpl]subspace.pack())), [CtVariableReadImpl]ex);
                }
                [CtThrowImpl]throw [CtVariableReadImpl]unwrappedEx;
            }).thenCompose([CtInvocationImpl][CtTypeAccessImpl]java.util.function.Function.identity());
        }, [CtInvocationImpl][CtInvocationImpl]getRunner().getExecutor());
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBStoredRecord<[CtTypeReferenceImpl]com.google.protobuf.Message> castCursorResultToStoreRecord([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]com.apple.foundationdb.record.RecordCursorResult<[CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBIndexedRecord<[CtTypeReferenceImpl]com.google.protobuf.Message>> cursorResult) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBIndexedRecord<[CtTypeReferenceImpl]com.google.protobuf.Message> indexResult = [CtInvocationImpl][CtVariableReadImpl]cursorResult.get();
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]indexResult == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]indexResult.getStoredRecord();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtArrayTypeReferenceImpl]byte[]> buildRangeOnly([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBRecordStore store, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] cont, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong recordsScanned) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.Index index = [CtInvocationImpl][CtFieldReadImpl]common.getIndex();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.subspace.Subspace scannedRecordsSubspace = [CtInvocationImpl]indexBuildScannedRecordsSubspace([CtVariableReadImpl]store, [CtVariableReadImpl]index);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.RecordMetaDataProvider recordMetaDataProvider = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]common.getRecordStoreBuilder().getMetaDataProvider();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]recordMetaDataProvider == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]store.getRecordMetaData().equals([CtInvocationImpl][CtVariableReadImpl]recordMetaDataProvider.getRecordMetaData()))) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.apple.foundationdb.record.metadata.MetaDataException([CtLiteralImpl]"Store does not have the same metadata");
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String srcIndex = [CtInvocationImpl][CtFieldReadImpl]policy.getSourceIndex();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.IndexMaintainer maintainer = [CtInvocationImpl][CtVariableReadImpl]store.getIndexMaintainer([CtVariableReadImpl]index);
        [CtInvocationImpl][CtCommentImpl]// idempotence - We could have verified it at the first iteration only, but the repeating checks seem harmless
        validateOrThrowEx([CtInvocationImpl][CtVariableReadImpl]maintainer.isIdempotent(), [CtLiteralImpl]"target index is not idempotent");
        [CtInvocationImpl][CtCommentImpl]// this should never happen. But it makes the compiler happy
        validateOrThrowEx([CtBinaryOperatorImpl][CtVariableReadImpl]srcIndex != [CtLiteralImpl]null, [CtLiteralImpl]"source index is null");
        [CtInvocationImpl][CtCommentImpl]// readability - This method shouldn't block if one has already opened the record store (as we did)
        validateOrThrowEx([CtInvocationImpl][CtVariableReadImpl]store.isIndexReadable([CtVariableReadImpl]srcIndex), [CtLiteralImpl]"source index is not readable");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.ExecuteProperties.Builder executeProperties = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.ExecuteProperties.newBuilder().setIsolationLevel([CtTypeAccessImpl]IsolationLevel.SNAPSHOT).setReturnedRowLimit([CtInvocationImpl]getLimit());[CtCommentImpl]// always respectLimit in this path

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.apple.foundationdb.record.ScanProperties scanProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.apple.foundationdb.record.ScanProperties([CtInvocationImpl][CtVariableReadImpl]executeProperties.build());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.RecordCursor<[CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBIndexedRecord<[CtTypeReferenceImpl]com.google.protobuf.Message>> cursor = [CtInvocationImpl][CtVariableReadImpl]store.scanIndexRecords([CtVariableReadImpl]srcIndex, [CtTypeAccessImpl]IndexScanType.BY_VALUE, [CtTypeAccessImpl]TupleRange.ALL, [CtVariableReadImpl]cont, [CtVariableReadImpl]scanProperties);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong recordsScannedCounter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]com.apple.foundationdb.record.RecordCursorResult<[CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBIndexedRecord<[CtTypeReferenceImpl]com.google.protobuf.Message>>> lastResult = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.RecordCursorResult.exhausted());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean isEmpty = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]true);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]iterateRangeOnly([CtVariableReadImpl]store, [CtVariableReadImpl]cursor, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingByIndex::castCursorResultToStoreRecord, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]lastResult::set, [CtVariableReadImpl]isEmpty, [CtVariableReadImpl]recordsScannedCounter).thenCompose([CtLambdaImpl]([CtParameterImpl] vignore) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long recordsScannedInTransaction = [CtInvocationImpl][CtVariableReadImpl]recordsScannedCounter.get();
            [CtInvocationImpl][CtVariableReadImpl]recordsScanned.addAndGet([CtVariableReadImpl]recordsScannedInTransaction);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.common.isTrackProgress()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]store.context.ensureActive().mutate([CtVariableReadImpl]MutationType.ADD, [CtInvocationImpl][CtVariableReadImpl]scannedRecordsSubspace.getKey(), [CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.record.provider.foundationdb.FDBRecordStore.encodeRecordCount([CtVariableReadImpl]recordsScannedInTransaction));
            }
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]byte[] retCont = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]isEmpty.get()) ? [CtLiteralImpl]null : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lastResult.get().getContinuation().toBytes();
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.completedFuture([CtVariableReadImpl]retCont);
        });
    }

    [CtMethodImpl][CtCommentImpl]// support rebuildIndexAsync
    [CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> rebuildIndexByEntityAsync([CtParameterImpl][CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.FDBRecordStore store) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtArrayTypeReferenceImpl]byte[]> nextCont = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong recordScanned = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.apple.foundationdb.async.AsyncUtil.whileTrue([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl]buildRangeOnly([CtVariableReadImpl]store, [CtInvocationImpl][CtVariableReadImpl]nextCont.get(), [CtVariableReadImpl]recordScanned).thenApply([CtLambdaImpl]([CtParameterImpl] cont) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cont == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtInvocationImpl][CtVariableReadImpl]nextCont.set([CtVariableReadImpl]cont);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }), [CtInvocationImpl][CtVariableReadImpl]store.getExecutor());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void validateOrThrowEx([CtParameterImpl][CtTypeReferenceImpl]boolean isValid, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isValid) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.apple.foundationdb.record.provider.foundationdb.IndexingByIndex.ValidationException([CtVariableReadImpl]msg, [CtFieldReadImpl]com.apple.foundationdb.record.logging.LogMessageKeys.INDEX_NAME, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]common.getIndex().getName(), [CtFieldReadImpl]com.apple.foundationdb.record.logging.LogMessageKeys.SOURCE_INDEX, [CtInvocationImpl][CtFieldReadImpl]policy.getSourceIndex(), [CtFieldReadImpl]com.apple.foundationdb.record.logging.LogMessageKeys.INDEXER_ID, [CtInvocationImpl][CtFieldReadImpl]common.getUuid());
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * thrown when IndexFromIndex validation fails.
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"serial")
    public static class ValidationException extends [CtTypeReferenceImpl]com.apple.foundationdb.record.RecordCoreException {
        [CtConstructorImpl]public ValidationException([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
        [CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        java.lang.Object... keyValues) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]msg, [CtVariableReadImpl]keyValues);
        }
    }
}
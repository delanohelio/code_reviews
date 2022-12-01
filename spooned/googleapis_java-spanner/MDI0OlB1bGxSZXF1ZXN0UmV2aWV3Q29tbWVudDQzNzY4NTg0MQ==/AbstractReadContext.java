[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Google LLC

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
[CtPackageDeclarationImpl]package com.google.cloud.spanner;
[CtUnresolvedImport]import com.google.common.util.concurrent.MoreExecutors;
[CtUnresolvedImport]import static com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException;
[CtUnresolvedImport]import com.google.cloud.spanner.spi.v1.SpannerRpc;
[CtUnresolvedImport]import com.google.spanner.v1.PartialResultSet;
[CtUnresolvedImport]import com.google.cloud.Timestamp;
[CtUnresolvedImport]import com.google.cloud.spanner.AbstractResultSet.GrpcResultSet;
[CtUnresolvedImport]import com.google.cloud.spanner.SessionImpl.SessionTransaction;
[CtUnresolvedImport]import com.google.spanner.v1.TransactionOptions;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkArgument;
[CtUnresolvedImport]import com.google.spanner.v1.ReadRequest;
[CtUnresolvedImport]import com.google.spanner.v1.Transaction;
[CtUnresolvedImport]import com.google.cloud.spanner.AbstractResultSet.GrpcStreamIterator;
[CtUnresolvedImport]import com.google.spanner.v1.TransactionSelector;
[CtUnresolvedImport]import com.google.spanner.v1.BeginTransactionRequest;
[CtUnresolvedImport]import io.opencensus.trace.Tracing;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import com.google.spanner.v1.ExecuteSqlRequest.QueryOptions;
[CtUnresolvedImport]import com.google.cloud.spanner.AbstractResultSet.ResumableStreamIterator;
[CtUnresolvedImport]import com.google.cloud.spanner.AsyncResultSet.CallbackResponse;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkNotNull;
[CtUnresolvedImport]import com.google.cloud.spanner.AsyncResultSet.ReadyCallback;
[CtUnresolvedImport]import javax.annotation.concurrent.GuardedBy;
[CtUnresolvedImport]import io.opencensus.trace.Span;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkState;
[CtUnresolvedImport]import com.google.api.core.ApiFuture;
[CtUnresolvedImport]import com.google.api.core.SettableApiFuture;
[CtUnresolvedImport]import com.google.cloud.spanner.Options.QueryOption;
[CtUnresolvedImport]import com.google.protobuf.ByteString;
[CtUnresolvedImport]import com.google.cloud.spanner.Options.ReadOption;
[CtUnresolvedImport]import com.google.cloud.spanner.AbstractResultSet.CloseableIterator;
[CtUnresolvedImport]import com.google.spanner.v1.ExecuteSqlRequest;
[CtUnresolvedImport]import com.google.spanner.v1.ExecuteSqlRequest.QueryMode;
[CtUnresolvedImport]import com.google.api.gax.core.ExecutorProvider;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import com.google.spanner.v1.ExecuteBatchDmlRequest;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.concurrent.atomic.AtomicLong;
[CtClassImpl][CtJavaDocImpl]/**
 * Abstract base class for all {@link ReadContext}s + concrete implementations of read-only {@link ReadContext}s.
 */
abstract class AbstractReadContext implements [CtTypeReferenceImpl]com.google.cloud.spanner.ReadContext , [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.Listener , [CtTypeReferenceImpl]com.google.cloud.spanner.SessionImpl.SessionTransaction {
    [CtClassImpl]static abstract class Builder<[CtTypeParameterImpl]B extends [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.Builder<[CtWildcardReferenceImpl]?, [CtTypeParameterReferenceImpl]T>, [CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext> {
        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.SessionImpl session;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.spi.v1.SpannerRpc rpc;

        [CtFieldImpl]private [CtTypeReferenceImpl]io.opencensus.trace.Span span = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.opencensus.trace.Tracing.getTracer().getCurrentSpan();

        [CtFieldImpl]private [CtTypeReferenceImpl]int defaultPrefetchChunks = [CtFieldReadImpl]SpannerOptions.Builder.DEFAULT_PREFETCH_CHUNKS;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions defaultQueryOptions = [CtFieldReadImpl]SpannerOptions.Builder.DEFAULT_QUERY_OPTIONS;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.api.gax.core.ExecutorProvider executorProvider;

        [CtConstructorImpl]Builder() [CtBlockImpl]{
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtTypeParameterReferenceImpl]B self() [CtBlockImpl]{
            [CtReturnImpl]return [CtThisAccessImpl](([CtTypeParameterReferenceImpl]B) (this));
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setSession([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.SessionImpl session) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.session = [CtVariableReadImpl]session;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setRpc([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.spi.v1.SpannerRpc rpc) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rpc = [CtVariableReadImpl]rpc;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setSpan([CtParameterImpl][CtTypeReferenceImpl]io.opencensus.trace.Span span) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.span = [CtVariableReadImpl]span;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setDefaultPrefetchChunks([CtParameterImpl][CtTypeReferenceImpl]int defaultPrefetchChunks) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultPrefetchChunks = [CtVariableReadImpl]defaultPrefetchChunks;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setDefaultQueryOptions([CtParameterImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions defaultQueryOptions) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultQueryOptions = [CtVariableReadImpl]defaultQueryOptions;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl][CtTypeParameterReferenceImpl]B setExecutorProvider([CtParameterImpl][CtTypeReferenceImpl]com.google.api.gax.core.ExecutorProvider executorProvider) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executorProvider = [CtVariableReadImpl]executorProvider;
            [CtReturnImpl]return [CtInvocationImpl]self();
        }

        [CtMethodImpl]abstract [CtTypeParameterReferenceImpl]T build();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * {@link AsyncResultSet} that supports adding listeners that are called when all rows from the
     * underlying result stream have been fetched.
     */
    interface ListenableAsyncResultSet extends [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Adds a listener to this {@link AsyncResultSet}.
         */
        [CtTypeReferenceImpl]void addListener([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable listener);

        [CtMethodImpl][CtTypeReferenceImpl]void removeListener([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable listener);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * A {@code ReadContext} for standalone reads. This can only be used for a single operation, since
     * each standalone read may see a different timestamp of Cloud Spanner data.
     */
    static class SingleReadContext extends [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext {
        [CtClassImpl]static class Builder extends [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.Builder<[CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder, [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext> {
            [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound;

            [CtConstructorImpl]private Builder() [CtBlockImpl]{
            }

            [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder setTimestampBound([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bound = [CtVariableReadImpl]bound;
                [CtReturnImpl]return [CtInvocationImpl]self();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext build() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext([CtThisAccessImpl]this);
            }

            [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleUseReadOnlyTransaction buildSingleUseReadOnlyTransaction() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleUseReadOnlyTransaction([CtThisAccessImpl]this);
            }
        }

        [CtMethodImpl]static [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder newBuilder() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder();
        }

        [CtFieldImpl]final [CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound;

        [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
        private [CtTypeReferenceImpl]boolean used;

        [CtConstructorImpl]private SingleReadContext([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder builder) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]builder);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bound = [CtFieldReadImpl][CtVariableReadImpl]builder.bound;
        }

        [CtMethodImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
        [CtAnnotationImpl]@java.lang.Override
        [CtTypeReferenceImpl]void beforeReadOrQueryLocked() [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.beforeReadOrQueryLocked();
            [CtInvocationImpl]checkState([CtUnaryOperatorImpl]![CtFieldReadImpl]used, [CtLiteralImpl]"Cannot use a single-read ReadContext for multiple reads");
            [CtAssignmentImpl][CtFieldWriteImpl]used = [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector getTransactionSelector() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]bound.getMode() == [CtFieldReadImpl]TimestampBound.Mode.STRONG) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// Default mode: no need to specify a transaction.
                return [CtLiteralImpl]null;
            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionSelector.newBuilder().setSingleUse([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionOptions.newBuilder().setReadOnly([CtInvocationImpl][CtFieldReadImpl]bound.toProto())).build();
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void assertTimestampAvailable([CtParameterImpl][CtTypeReferenceImpl]boolean available) [CtBlockImpl]{
        [CtInvocationImpl]checkState([CtVariableReadImpl]available, [CtLiteralImpl]"Method can only be called after read has returned data or finished");
    }

    [CtClassImpl]static class SingleUseReadOnlyTransaction extends [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext implements [CtTypeReferenceImpl]com.google.cloud.spanner.ReadOnlyTransaction {
        [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
        private [CtTypeReferenceImpl]com.google.cloud.Timestamp timestamp;

        [CtConstructorImpl]private SingleUseReadOnlyTransaction([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.SingleReadContext.Builder builder) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]builder);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.cloud.Timestamp getReadTimestamp() [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]lock) [CtBlockImpl]{
                [CtInvocationImpl]com.google.cloud.spanner.AbstractReadContext.assertTimestampAvailable([CtBinaryOperatorImpl][CtFieldReadImpl]timestamp != [CtLiteralImpl]null);
                [CtReturnImpl]return [CtFieldReadImpl]timestamp;
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector getTransactionSelector() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.TransactionOptions.Builder options = [CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionOptions.newBuilder();
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]bound.applyToBuilder([CtInvocationImpl][CtVariableReadImpl]options.getReadOnlyBuilder()).setReturnReadTimestamp([CtLiteralImpl]true);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionSelector.newBuilder().setSingleUse([CtVariableReadImpl]options).build();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onTransactionMetadata([CtParameterImpl][CtTypeReferenceImpl]com.google.spanner.v1.Transaction transaction) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]lock) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]transaction.hasReadTimestamp()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Missing expected transaction.read_timestamp metadata field");
                }
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]timestamp = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.Timestamp.fromProto([CtInvocationImpl][CtVariableReadImpl]transaction.getReadTimestamp());
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Bad value in transaction.read_timestamp metadata field", [CtVariableReadImpl]e);
                }
            }
        }
    }

    [CtClassImpl]static class MultiUseReadOnlyTransaction extends [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext implements [CtTypeReferenceImpl]com.google.cloud.spanner.ReadOnlyTransaction {
        [CtClassImpl]static class Builder extends [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.Builder<[CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder, [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction> {
            [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound;

            [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.Timestamp timestamp;

            [CtFieldImpl]private [CtTypeReferenceImpl]com.google.protobuf.ByteString transactionId;

            [CtConstructorImpl]private Builder() [CtBlockImpl]{
            }

            [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder setTimestampBound([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bound = [CtVariableReadImpl]bound;
                [CtReturnImpl]return [CtThisAccessImpl]this;
            }

            [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder setTimestamp([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.Timestamp timestamp) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timestamp = [CtVariableReadImpl]timestamp;
                [CtReturnImpl]return [CtThisAccessImpl]this;
            }

            [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder setTransactionId([CtParameterImpl][CtTypeReferenceImpl]com.google.protobuf.ByteString transactionId) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transactionId = [CtVariableReadImpl]transactionId;
                [CtReturnImpl]return [CtThisAccessImpl]this;
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction build() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction([CtThisAccessImpl]this);
            }
        }

        [CtMethodImpl]static [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder newBuilder() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder();
        }

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.TimestampBound bound;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Object txnLock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

        [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"txnLock")
        private [CtTypeReferenceImpl]com.google.cloud.Timestamp timestamp;

        [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"txnLock")
        private [CtTypeReferenceImpl]com.google.protobuf.ByteString transactionId;

        [CtConstructorImpl]MultiUseReadOnlyTransaction([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.MultiUseReadOnlyTransaction.Builder builder) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]builder);
            [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]builder.bound != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]builder.transactionId != [CtLiteralImpl]null))) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]builder.bound == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]builder.transactionId == [CtLiteralImpl]null))), [CtLiteralImpl]"Either TimestampBound or TransactionId must be specified");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]builder.bound != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]builder.bound.getMode() != [CtFieldReadImpl]TimestampBound.Mode.MAX_STALENESS) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]builder.bound.getMode() != [CtFieldReadImpl]TimestampBound.Mode.MIN_READ_TIMESTAMP), [CtBinaryOperatorImpl][CtLiteralImpl]"Bounded staleness mode %s is not supported for multi-use read-only transactions." + [CtLiteralImpl]" Create a single-use read or read-only transaction instead.", [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]builder.bound.getMode());
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bound = [CtFieldReadImpl][CtVariableReadImpl]builder.bound;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timestamp = [CtFieldReadImpl][CtVariableReadImpl]builder.timestamp;
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transactionId = [CtFieldReadImpl][CtVariableReadImpl]builder.transactionId;
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtTypeReferenceImpl]void beforeReadOrQuery() [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.beforeReadOrQuery();
            [CtInvocationImpl]initTransaction();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector getTransactionSelector() [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// No need for synchronization: super.readInternal() is always preceded by a check of
            [CtCommentImpl]// "transactionId" that provides a happens-before from initialization, and the value is never
            [CtCommentImpl]// changed afterwards.
            [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"GuardedByChecker")
            [CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector selector = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionSelector.newBuilder().setId([CtFieldReadImpl]transactionId).build();
            [CtReturnImpl]return [CtVariableReadImpl]selector;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.cloud.Timestamp getReadTimestamp() [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]txnLock) [CtBlockImpl]{
                [CtInvocationImpl]com.google.cloud.spanner.AbstractReadContext.assertTimestampAvailable([CtBinaryOperatorImpl][CtFieldReadImpl]timestamp != [CtLiteralImpl]null);
                [CtReturnImpl]return [CtFieldReadImpl]timestamp;
            }
        }

        [CtMethodImpl][CtTypeReferenceImpl]com.google.protobuf.ByteString getTransactionId() [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtFieldReadImpl]txnLock) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]transactionId;
            }
        }

        [CtMethodImpl][CtTypeReferenceImpl]void initTransaction() [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.SessionImpl.throwIfTransactionsPending();
            [CtSynchronizedImpl][CtCommentImpl]// Since we only support synchronous calls, just block on "txnLock" while the RPC is in
            [CtCommentImpl]// flight. Note that we use the strategy of sending an explicit BeginTransaction() RPC,
            [CtCommentImpl]// rather than using the first read in the transaction to begin it implicitly. The chosen
            [CtCommentImpl]// strategy is sub-optimal in the case of the first read being fast, as it incurs an extra
            [CtCommentImpl]// RTT, but optimal if the first read is slow. As the client library is now using streaming
            [CtCommentImpl]// reads, a possible optimization could be to use the first read in the transaction to begin
            [CtCommentImpl]// it implicitly.
            synchronized([CtFieldReadImpl]txnLock) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]transactionId != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return;
                }
                [CtInvocationImpl][CtFieldReadImpl]span.addAnnotation([CtLiteralImpl]"Creating Transaction");
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.TransactionOptions.Builder options = [CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.TransactionOptions.newBuilder();
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]bound.applyToBuilder([CtInvocationImpl][CtVariableReadImpl]options.getReadOnlyBuilder()).setReturnReadTimestamp([CtLiteralImpl]true);
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.spanner.v1.BeginTransactionRequest request = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.BeginTransactionRequest.newBuilder().setSession([CtInvocationImpl][CtFieldReadImpl]session.getName()).setOptions([CtVariableReadImpl]options).build();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.spanner.v1.Transaction transaction = [CtInvocationImpl][CtFieldReadImpl]rpc.beginTransaction([CtVariableReadImpl]request, [CtInvocationImpl][CtFieldReadImpl]session.getOptions());
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]transaction.hasReadTimestamp()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Missing expected transaction.read_timestamp metadata field");
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]transaction.getId().isEmpty()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Missing expected transaction.id metadata field");
                    }
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]timestamp = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.Timestamp.fromProto([CtInvocationImpl][CtVariableReadImpl]transaction.getReadTimestamp());
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Bad value in transaction.read_timestamp metadata field", [CtVariableReadImpl]e);
                    }
                    [CtAssignmentImpl][CtFieldWriteImpl]transactionId = [CtInvocationImpl][CtVariableReadImpl]transaction.getId();
                    [CtInvocationImpl][CtFieldReadImpl]span.addAnnotation([CtLiteralImpl]"Transaction Creation Done", [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.TraceUtil.getTransactionAnnotations([CtVariableReadImpl]transaction));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.SpannerException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]span.addAnnotation([CtLiteralImpl]"Transaction Creation Failed", [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.TraceUtil.getExceptionAnnotations([CtVariableReadImpl]e));
                    [CtThrowImpl]throw [CtVariableReadImpl]e;
                }
            }
        }
    }

    [CtFieldImpl]final [CtTypeReferenceImpl]java.lang.Object lock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

    [CtFieldImpl]final [CtTypeReferenceImpl]com.google.cloud.spanner.SessionImpl session;

    [CtFieldImpl]final [CtTypeReferenceImpl]com.google.cloud.spanner.spi.v1.SpannerRpc rpc;

    [CtFieldImpl]final [CtTypeReferenceImpl]com.google.api.gax.core.ExecutorProvider executorProvider;

    [CtFieldImpl][CtTypeReferenceImpl]io.opencensus.trace.Span span;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int defaultPrefetchChunks;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions defaultQueryOptions;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
    private [CtTypeReferenceImpl]boolean isValid = [CtLiteralImpl]true;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
    private [CtTypeReferenceImpl]boolean isClosed = [CtLiteralImpl]false;

    [CtFieldImpl][CtCommentImpl]// A per-transaction sequence number used to identify this ExecuteSqlRequests. Required for DML,
    [CtCommentImpl]// ignored for query by the server.
    private [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong seqNo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong();

    [CtFieldImpl][CtCommentImpl]// Allow up to 512MB to be buffered (assuming 1MB chunks). In practice, restart tokens are sent
    [CtCommentImpl]// much more frequently.
    private static final [CtTypeReferenceImpl]int MAX_BUFFERED_CHUNKS = [CtLiteralImpl]512;

    [CtConstructorImpl]AbstractReadContext([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.Builder<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> builder) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.session = [CtFieldReadImpl][CtVariableReadImpl]builder.session;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rpc = [CtFieldReadImpl][CtVariableReadImpl]builder.rpc;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultPrefetchChunks = [CtFieldReadImpl][CtVariableReadImpl]builder.defaultPrefetchChunks;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultQueryOptions = [CtFieldReadImpl][CtVariableReadImpl]builder.defaultQueryOptions;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.span = [CtFieldReadImpl][CtVariableReadImpl]builder.span;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executorProvider = [CtFieldReadImpl][CtVariableReadImpl]builder.executorProvider;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setSpan([CtParameterImpl][CtTypeReferenceImpl]io.opencensus.trace.Span span) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.span = [CtVariableReadImpl]span;
    }

    [CtMethodImpl][CtTypeReferenceImpl]long getSeqNo() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]seqNo.incrementAndGet();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet read([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl]com.google.cloud.spanner.Options.ReadOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]readInternal([CtVariableReadImpl]table, [CtLiteralImpl]null, [CtVariableReadImpl]keys, [CtVariableReadImpl]columns, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.ListenableAsyncResultSet readAsync([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl]com.google.cloud.spanner.Options.ReadOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options readOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.Options.fromReadOptions([CtVariableReadImpl]options);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int bufferRows = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]readOptions.hasBufferRows()) ? [CtInvocationImpl][CtVariableReadImpl]readOptions.bufferRows() : [CtFieldReadImpl]AsyncResultSetImpl.DEFAULT_BUFFER_SIZE;
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSetImpl([CtFieldReadImpl]executorProvider, [CtInvocationImpl]readInternal([CtVariableReadImpl]table, [CtLiteralImpl]null, [CtVariableReadImpl]keys, [CtVariableReadImpl]columns, [CtVariableReadImpl]options), [CtVariableReadImpl]bufferRows);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet readUsingIndex([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl]com.google.cloud.spanner.Options.ReadOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]readInternal([CtVariableReadImpl]table, [CtInvocationImpl]checkNotNull([CtVariableReadImpl]index), [CtVariableReadImpl]keys, [CtVariableReadImpl]columns, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.ListenableAsyncResultSet readUsingIndexAsync([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl]com.google.cloud.spanner.Options.ReadOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options readOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.Options.fromReadOptions([CtVariableReadImpl]options);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int bufferRows = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]readOptions.hasBufferRows()) ? [CtInvocationImpl][CtVariableReadImpl]readOptions.bufferRows() : [CtFieldReadImpl]AsyncResultSetImpl.DEFAULT_BUFFER_SIZE;
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSetImpl([CtFieldReadImpl]executorProvider, [CtInvocationImpl]readInternal([CtVariableReadImpl]table, [CtInvocationImpl]checkNotNull([CtVariableReadImpl]index), [CtVariableReadImpl]keys, [CtVariableReadImpl]columns, [CtVariableReadImpl]options), [CtVariableReadImpl]bufferRows);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.Struct readRow([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Key key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet resultSet = [CtInvocationImpl]read([CtVariableReadImpl]table, [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.KeySet.singleKey([CtVariableReadImpl]key), [CtVariableReadImpl]columns)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]consumeSingleRow([CtVariableReadImpl]resultSet);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.api.core.ApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> readRowAsync([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Key key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet resultSet = [CtInvocationImpl]readAsync([CtVariableReadImpl]table, [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.KeySet.singleKey([CtVariableReadImpl]key), [CtVariableReadImpl]columns)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]consumeSingleRowAsync([CtVariableReadImpl]resultSet);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.Struct readRowUsingIndex([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Key key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet resultSet = [CtInvocationImpl]readUsingIndex([CtVariableReadImpl]table, [CtVariableReadImpl]index, [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.KeySet.singleKey([CtVariableReadImpl]key), [CtVariableReadImpl]columns)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]consumeSingleRow([CtVariableReadImpl]resultSet);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.api.core.ApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> readRowUsingIndexAsync([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Key key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet resultSet = [CtInvocationImpl]readUsingIndexAsync([CtVariableReadImpl]table, [CtVariableReadImpl]index, [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.KeySet.singleKey([CtVariableReadImpl]key), [CtVariableReadImpl]columns)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]consumeSingleRowAsync([CtVariableReadImpl]resultSet);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet executeQuery([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl]com.google.cloud.spanner.Options.QueryOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]executeQueryInternal([CtVariableReadImpl]statement, [CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.ExecuteSqlRequest.QueryMode, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.ListenableAsyncResultSet executeQueryAsync([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl]com.google.cloud.spanner.Options.QueryOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options readOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.Options.fromQueryOptions([CtVariableReadImpl]options);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int bufferRows = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]readOptions.hasBufferRows()) ? [CtInvocationImpl][CtVariableReadImpl]readOptions.bufferRows() : [CtFieldReadImpl]AsyncResultSetImpl.DEFAULT_BUFFER_SIZE;
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSetImpl([CtFieldReadImpl]executorProvider, [CtInvocationImpl]executeQueryInternal([CtVariableReadImpl]statement, [CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode.NORMAL, [CtVariableReadImpl]options), [CtVariableReadImpl]bufferRows);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet analyzeQuery([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.QueryAnalyzeMode readContextQueryMode) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]readContextQueryMode) {
            [CtCaseImpl]case [CtFieldReadImpl]PROFILE :
                [CtReturnImpl]return [CtInvocationImpl]executeQueryInternal([CtVariableReadImpl]statement, [CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode.PROFILE);
            [CtCaseImpl]case [CtFieldReadImpl]PLAN :
                [CtReturnImpl]return [CtInvocationImpl]executeQueryInternal([CtVariableReadImpl]statement, [CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode.PLAN);
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown value for QueryAnalyzeMode : " + [CtVariableReadImpl]readContextQueryMode);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet executeQueryInternal([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode queryMode, [CtParameterImpl]com.google.cloud.spanner.Options.QueryOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options queryOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.Options.fromQueryOptions([CtVariableReadImpl]options);
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* partitionToken */
        executeQueryInternalWithOptions([CtVariableReadImpl]statement, [CtVariableReadImpl]queryMode, [CtVariableReadImpl]queryOptions, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determines the {@link QueryOptions} to use for a query. This is determined using the following
     * precedence:
     *
     * <ol>
     *   <li>Specific {@link QueryOptions} passed in for this query.
     *   <li>Any value specified in a valid environment variable when the {@link SpannerOptions}
     *       instance was created.
     *   <li>The default {@link SpannerOptions#getDefaultQueryOptions()} specified for the database
     *       where the query is executed.
     * </ol>
     */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions buildQueryOptions([CtParameterImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions requestOptions) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Shortcut for the most common return value.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]defaultQueryOptions.equals([CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions.getDefaultInstance()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]requestOptions == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions.getDefaultInstance();
        }
        [CtLocalVariableImpl][CtCommentImpl]// Create a builder based on the default query options.
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryOptions.Builder builder = [CtInvocationImpl][CtFieldReadImpl]defaultQueryOptions.toBuilder();
        [CtIfImpl][CtCommentImpl]// Then overwrite with specific options for this query.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]requestOptions != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.mergeFrom([CtVariableReadImpl]requestOptions);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
    }

    [CtMethodImpl][CtTypeReferenceImpl]ExecuteSqlRequest.Builder getExecuteSqlRequestBuilder([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode queryMode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.Builder builder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.ExecuteSqlRequest.newBuilder().setSql([CtInvocationImpl][CtVariableReadImpl]statement.getSql()).setQueryMode([CtVariableReadImpl]queryMode).setSession([CtInvocationImpl][CtFieldReadImpl]session.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.cloud.spanner.Value> stmtParameters = [CtInvocationImpl][CtVariableReadImpl]statement.getParameters();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]stmtParameters.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.protobuf.Struct.Builder paramsBuilder = [CtInvocationImpl][CtVariableReadImpl]builder.getParamsBuilder();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.cloud.spanner.Value> param : [CtInvocationImpl][CtVariableReadImpl]stmtParameters.entrySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]paramsBuilder.putFields([CtInvocationImpl][CtVariableReadImpl]param.getKey(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]param.getValue().toProto());
                [CtInvocationImpl][CtVariableReadImpl]builder.putParamTypes([CtInvocationImpl][CtVariableReadImpl]param.getKey(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]param.getValue().getType().toProto());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector selector = [CtInvocationImpl]getTransactionSelector();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selector != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setTransaction([CtVariableReadImpl]selector);
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.setSeqno([CtInvocationImpl]getSeqNo());
        [CtInvocationImpl][CtVariableReadImpl]builder.setQueryOptions([CtInvocationImpl]buildQueryOptions([CtInvocationImpl][CtVariableReadImpl]statement.getQueryOptions()));
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl][CtTypeReferenceImpl]ExecuteBatchDmlRequest.Builder getExecuteBatchDmlRequestBuilder([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.spanner.Statement> statements) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteBatchDmlRequest.Builder builder = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.ExecuteBatchDmlRequest.newBuilder().setSession([CtInvocationImpl][CtFieldReadImpl]session.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement stmt : [CtVariableReadImpl]statements) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.addStatementsBuilder();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getStatementsBuilder([CtVariableReadImpl]idx).setSql([CtInvocationImpl][CtVariableReadImpl]stmt.getSql());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.cloud.spanner.Value> stmtParameters = [CtInvocationImpl][CtVariableReadImpl]stmt.getParameters();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]stmtParameters.isEmpty()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.protobuf.Struct.Builder paramsBuilder = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getStatementsBuilder([CtVariableReadImpl]idx).getParamsBuilder();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.cloud.spanner.Value> param : [CtInvocationImpl][CtVariableReadImpl]stmtParameters.entrySet()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]paramsBuilder.putFields([CtInvocationImpl][CtVariableReadImpl]param.getKey(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]param.getValue().toProto());
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getStatementsBuilder([CtVariableReadImpl]idx).putParamTypes([CtInvocationImpl][CtVariableReadImpl]param.getKey(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]param.getValue().getType().toProto());
                }
            }
            [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector selector = [CtInvocationImpl]getTransactionSelector();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selector != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setTransaction([CtVariableReadImpl]selector);
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.setSeqno([CtInvocationImpl]getSeqNo());
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet executeQueryInternalWithOptions([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Statement statement, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.QueryMode queryMode, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options options, [CtParameterImpl][CtTypeReferenceImpl]com.google.protobuf.ByteString partitionToken) [CtBlockImpl]{
        [CtInvocationImpl]beforeReadOrQuery();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ExecuteSqlRequest.Builder request = [CtInvocationImpl]getExecuteSqlRequestBuilder([CtVariableReadImpl]statement, [CtVariableReadImpl]queryMode);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partitionToken != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]request.setPartitionToken([CtVariableReadImpl]partitionToken);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int prefetchChunks = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]options.hasPrefetchChunks()) ? [CtInvocationImpl][CtVariableReadImpl]options.prefetchChunks() : [CtFieldReadImpl]defaultPrefetchChunks;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.ResumableStreamIterator stream = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.ResumableStreamIterator([CtFieldReadImpl]com.google.cloud.spanner.AbstractReadContext.MAX_BUFFERED_CHUNKS, [CtFieldReadImpl]SpannerImpl.QUERY, [CtFieldReadImpl]span)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.CloseableIterator<[CtTypeReferenceImpl]com.google.spanner.v1.PartialResultSet> startStream([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
            [CtTypeReferenceImpl]com.google.protobuf.ByteString resumeToken) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcStreamIterator stream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcStreamIterator([CtVariableReadImpl]prefetchChunks);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resumeToken != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]request.setResumeToken([CtVariableReadImpl]resumeToken);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.spanner.spi.v1.SpannerRpc.StreamingCall call = [CtInvocationImpl][CtFieldReadImpl]rpc.executeQuery([CtInvocationImpl][CtVariableReadImpl]request.build(), [CtInvocationImpl][CtVariableReadImpl]stream.consumer(), [CtInvocationImpl][CtFieldReadImpl]session.getOptions());
                [CtInvocationImpl][CtVariableReadImpl]call.request([CtVariableReadImpl]prefetchChunks);
                [CtInvocationImpl][CtVariableReadImpl]stream.setCall([CtVariableReadImpl]call);
                [CtReturnImpl]return [CtVariableReadImpl]stream;
            }
        };
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcResultSet([CtVariableReadImpl]stream, [CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called before any read or query is started to perform state checks and initializations.
     * Subclasses should call {@code super.beforeReadOrQuery()} if overriding.
     */
    [CtTypeReferenceImpl]void beforeReadOrQuery() [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]lock) [CtBlockImpl]{
            [CtInvocationImpl]beforeReadOrQueryLocked();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called as part of {@link #beforeReadOrQuery()} under {@link #lock}.
     */
    [CtAnnotationImpl]@javax.annotation.concurrent.GuardedBy([CtLiteralImpl]"lock")
    [CtTypeReferenceImpl]void beforeReadOrQueryLocked() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Note that transactions are invalidated under some circumstances on the backend, but we
        [CtCommentImpl]// implement the check more strictly here to encourage coding to contract rather than the
        [CtCommentImpl]// implementation.
        checkState([CtFieldReadImpl]isValid, [CtLiteralImpl]"Context has been invalidated by a new operation on the session");
        [CtInvocationImpl]checkState([CtUnaryOperatorImpl]![CtFieldReadImpl]isClosed, [CtLiteralImpl]"Context has been closed");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Invalidates the context since another context has been created more recently.
     */
    [CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]void invalidate() [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]lock) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]isValid = [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]span.end([CtTypeAccessImpl]TraceUtil.END_SPAN_OPTIONS);
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]lock) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]isClosed = [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    abstract [CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector getTransactionSelector();

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onTransactionMetadata([CtParameterImpl][CtTypeReferenceImpl]com.google.spanner.v1.Transaction transaction) [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.SpannerException e) [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onDone() [CtBlockImpl]{
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet readInternal([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl]com.google.cloud.spanner.Options.ReadOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options readOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.Options.fromReadOptions([CtVariableReadImpl]options);
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* partitionToken */
        readInternalWithOptions([CtVariableReadImpl]table, [CtVariableReadImpl]index, [CtVariableReadImpl]keys, [CtVariableReadImpl]columns, [CtVariableReadImpl]readOptions, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet readInternalWithOptions([CtParameterImpl][CtTypeReferenceImpl]java.lang.String table, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String index, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.KeySet keys, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Options readOptions, [CtParameterImpl][CtTypeReferenceImpl]com.google.protobuf.ByteString partitionToken) [CtBlockImpl]{
        [CtInvocationImpl]beforeReadOrQuery();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.spanner.v1.ReadRequest.Builder builder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.spanner.v1.ReadRequest.newBuilder().setSession([CtInvocationImpl][CtFieldReadImpl]session.getName()).setTable([CtInvocationImpl]checkNotNull([CtVariableReadImpl]table)).addAllColumns([CtVariableReadImpl]columns);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]readOptions.hasLimit()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setLimit([CtInvocationImpl][CtVariableReadImpl]readOptions.limit());
        }
        [CtInvocationImpl][CtVariableReadImpl]keys.appendToProto([CtInvocationImpl][CtVariableReadImpl]builder.getKeySetBuilder());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setIndex([CtVariableReadImpl]index);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.spanner.v1.TransactionSelector selector = [CtInvocationImpl]getTransactionSelector();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selector != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setTransaction([CtVariableReadImpl]selector);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partitionToken != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setPartitionToken([CtVariableReadImpl]partitionToken);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int prefetchChunks = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]readOptions.hasPrefetchChunks()) ? [CtInvocationImpl][CtVariableReadImpl]readOptions.prefetchChunks() : [CtFieldReadImpl]defaultPrefetchChunks;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.ResumableStreamIterator stream = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.ResumableStreamIterator([CtFieldReadImpl]com.google.cloud.spanner.AbstractReadContext.MAX_BUFFERED_CHUNKS, [CtFieldReadImpl]SpannerImpl.READ, [CtFieldReadImpl]span)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.CloseableIterator<[CtTypeReferenceImpl]com.google.spanner.v1.PartialResultSet> startStream([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
            [CtTypeReferenceImpl]com.google.protobuf.ByteString resumeToken) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcStreamIterator stream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcStreamIterator([CtVariableReadImpl]prefetchChunks);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resumeToken != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]builder.setResumeToken([CtVariableReadImpl]resumeToken);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.spanner.spi.v1.SpannerRpc.StreamingCall call = [CtInvocationImpl][CtFieldReadImpl]rpc.read([CtInvocationImpl][CtVariableReadImpl]builder.build(), [CtInvocationImpl][CtVariableReadImpl]stream.consumer(), [CtInvocationImpl][CtFieldReadImpl]session.getOptions());
                [CtInvocationImpl][CtVariableReadImpl]call.request([CtVariableReadImpl]prefetchChunks);
                [CtInvocationImpl][CtVariableReadImpl]stream.setCall([CtVariableReadImpl]call);
                [CtReturnImpl]return [CtVariableReadImpl]stream;
            }
        };
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcResultSet resultSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractResultSet.GrpcResultSet([CtVariableReadImpl]stream, [CtThisAccessImpl]this);
        [CtReturnImpl]return [CtVariableReadImpl]resultSet;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.Struct consumeSingleRow([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.ResultSet resultSet) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]resultSet.next()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.spanner.Struct row = [CtInvocationImpl][CtVariableReadImpl]resultSet.getCurrentRowAsStruct();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resultSet.next()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Multiple rows returned for single key");
        }
        [CtReturnImpl]return [CtVariableReadImpl]row;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.api.core.ApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> consumeSingleRowAsync([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet resultSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.core.SettableApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> result = [CtInvocationImpl][CtTypeAccessImpl]com.google.api.core.SettableApiFuture.create();
        [CtInvocationImpl][CtCommentImpl]// We can safely use a directExecutor here, as we will only be consuming one row, and we will
        [CtCommentImpl]// not be doing any blocking stuff in the handler.
        [CtVariableReadImpl]resultSet.setCallback([CtInvocationImpl][CtTypeAccessImpl]com.google.common.util.concurrent.MoreExecutors.directExecutor(), [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.spanner.AbstractReadContext.ConsumeSingleRowCallback.create([CtVariableReadImpl]result));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * {@link ReadyCallback} for returning the first row in a result set as a future {@link Struct}.
     */
    static class ConsumeSingleRowCallback implements [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet.ReadyCallback {
        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.api.core.SettableApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> result;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.spanner.Struct row;

        [CtMethodImpl]static [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.ConsumeSingleRowCallback create([CtParameterImpl][CtTypeReferenceImpl]com.google.api.core.SettableApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> result) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.spanner.AbstractReadContext.ConsumeSingleRowCallback([CtVariableReadImpl]result);
        }

        [CtConstructorImpl]private ConsumeSingleRowCallback([CtParameterImpl][CtTypeReferenceImpl]com.google.api.core.SettableApiFuture<[CtTypeReferenceImpl]com.google.cloud.spanner.Struct> result) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.result = [CtVariableReadImpl]result;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet.CallbackResponse cursorReady([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.spanner.AsyncResultSet resultSet) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                    [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]resultSet.tryNext()) {
                        [CtCaseImpl]case [CtFieldReadImpl]DONE :
                            [CtInvocationImpl][CtFieldReadImpl]result.set([CtFieldReadImpl]row);
                            [CtReturnImpl]return [CtFieldReadImpl]com.google.cloud.spanner.AsyncResultSet.CallbackResponse.DONE;
                        [CtCaseImpl]case [CtFieldReadImpl]NOT_READY :
                            [CtReturnImpl]return [CtFieldReadImpl]com.google.cloud.spanner.AsyncResultSet.CallbackResponse.CONTINUE;
                        [CtCaseImpl]case [CtFieldReadImpl]OK :
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]row != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtThrowImpl]throw [CtInvocationImpl]com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException([CtTypeAccessImpl]ErrorCode.INTERNAL, [CtLiteralImpl]"Multiple rows returned for single key");
                            }
                            [CtAssignmentImpl][CtFieldWriteImpl]row = [CtInvocationImpl][CtVariableReadImpl]resultSet.getCurrentRowAsStruct();
                    }
                } 
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]result.setException([CtVariableReadImpl]t);
                [CtReturnImpl]return [CtFieldReadImpl]com.google.cloud.spanner.AsyncResultSet.CallbackResponse.DONE;
            }
        }
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package org.infinispan.query.backend;
[CtUnresolvedImport]import org.infinispan.commands.write.WriteCommand;
[CtUnresolvedImport]import org.infinispan.search.mapper.mapping.SearchMapping;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.infinispan.util.logging.LogFactory;
[CtUnresolvedImport]import org.infinispan.commands.write.ReplaceCommand;
[CtUnresolvedImport]import org.infinispan.context.InvocationContext;
[CtUnresolvedImport]import org.infinispan.AdvancedCache;
[CtUnresolvedImport]import org.infinispan.util.concurrent.CompletableFutures;
[CtUnresolvedImport]import org.infinispan.interceptors.InvocationSuccessAction;
[CtUnresolvedImport]import org.infinispan.context.impl.TxInvocationContext;
[CtUnresolvedImport]import org.infinispan.interceptors.DDAsyncInterceptor;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.infinispan.commands.write.RemoveCommand;
[CtUnresolvedImport]import org.infinispan.commands.functional.ReadWriteManyEntriesCommand;
[CtUnresolvedImport]import org.infinispan.commands.functional.ReadWriteKeyValueCommand;
[CtImportImpl]import java.util.concurrent.ConcurrentMap;
[CtUnresolvedImport]import org.infinispan.commons.util.IntSet;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtUnresolvedImport]import org.infinispan.commands.write.PutKeyValueCommand;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.infinispan.encoding.DataConversion;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.infinispan.container.entries.ReadCommittedEntry;
[CtUnresolvedImport]import org.infinispan.query.impl.ComponentRegistryUtils;
[CtUnresolvedImport]import org.infinispan.commands.FlagAffectedCommand;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.infinispan.commands.write.ComputeCommand;
[CtUnresolvedImport]import org.infinispan.commands.functional.WriteOnlyManyEntriesCommand;
[CtUnresolvedImport]import org.infinispan.factories.annotations.Start;
[CtImportImpl]import static java.util.concurrent.CompletableFuture.allOf;
[CtUnresolvedImport]import org.infinispan.context.impl.FlagBitSets;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import org.infinispan.distribution.ch.KeyPartitioner;
[CtUnresolvedImport]import org.infinispan.search.mapper.work.SearchIndexer;
[CtUnresolvedImport]import org.infinispan.commands.write.IracPutKeyValueCommand;
[CtUnresolvedImport]import org.infinispan.commands.SegmentSpecificCommand;
[CtUnresolvedImport]import org.infinispan.commands.write.DataWriteCommand;
[CtUnresolvedImport]import org.infinispan.commands.write.ClearCommand;
[CtUnresolvedImport]import org.infinispan.container.entries.CacheEntry;
[CtUnresolvedImport]import org.infinispan.commands.functional.WriteOnlyKeyValueCommand;
[CtUnresolvedImport]import org.infinispan.commands.functional.WriteOnlyManyCommand;
[CtUnresolvedImport]import org.infinispan.commands.write.PutMapCommand;
[CtUnresolvedImport]import org.infinispan.factories.annotations.Inject;
[CtUnresolvedImport]import org.infinispan.container.entries.MVCCEntry;
[CtUnresolvedImport]import org.infinispan.commands.functional.ReadWriteKeyCommand;
[CtUnresolvedImport]import org.infinispan.commands.write.ComputeIfAbsentCommand;
[CtUnresolvedImport]import org.infinispan.transaction.xa.GlobalTransaction;
[CtUnresolvedImport]import org.infinispan.query.logging.Log;
[CtUnresolvedImport]import org.infinispan.distribution.DistributionInfo;
[CtUnresolvedImport]import org.infinispan.commands.functional.WriteOnlyKeyCommand;
[CtUnresolvedImport]import org.infinispan.commands.VisitableCommand;
[CtUnresolvedImport]import org.infinispan.commands.functional.ReadWriteManyCommand;
[CtUnresolvedImport]import org.infinispan.util.concurrent.BlockingManager;
[CtUnresolvedImport]import org.infinispan.distribution.DistributionManager;
[CtClassImpl][CtJavaDocImpl]/**
 * This interceptor will be created when the System Property "infinispan.query.indexLocalOnly" is "false"
 * <p>
 * This type of interceptor will allow the indexing of data even when it comes from other caches within a cluster.
 * <p>
 * However, if the a cache would not be putting the data locally, the interceptor will not index it.
 *
 * @author Navin Surtani
 * @author Sanne Grinovero &lt;sanne@hibernate.org&gt; (C) 2011 Red Hat Inc.
 * @author Marko Luksa
 * @author anistor@redhat.com
 * @since 4.0
 */
public final class QueryInterceptor extends [CtTypeReferenceImpl]org.infinispan.interceptors.DDAsyncInterceptor {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.infinispan.query.logging.Log log = [CtInvocationImpl][CtTypeAccessImpl]org.infinispan.util.logging.LogFactory.getLog([CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.class, [CtFieldReadImpl]org.infinispan.query.logging.Log.class);

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.Object UNKNOWN = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Object()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"<UNKNOWN>";
        }
    };

    [CtFieldImpl][CtAnnotationImpl]@org.infinispan.factories.annotations.Inject
    [CtTypeReferenceImpl]org.infinispan.distribution.DistributionManager distributionManager;

    [CtFieldImpl][CtAnnotationImpl]@org.infinispan.factories.annotations.Inject
    [CtTypeReferenceImpl]org.infinispan.util.concurrent.BlockingManager blockingManager;

    [CtFieldImpl][CtAnnotationImpl]@org.infinispan.factories.annotations.Inject
    protected [CtTypeReferenceImpl]org.infinispan.distribution.ch.KeyPartitioner keyPartitioner;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean stopping = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ConcurrentMap<[CtTypeReferenceImpl]org.infinispan.transaction.xa.GlobalTransaction, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object>> txOldValues;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.infinispan.encoding.DataConversion valueDataConversion;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.infinispan.encoding.DataConversion keyDataConversion;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isPersistenceEnabled;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.infinispan.interceptors.InvocationSuccessAction<[CtTypeReferenceImpl]org.infinispan.commands.write.ClearCommand> processClearCommand = [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::processClearCommand;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isManualIndexing;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.infinispan.AdvancedCache<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> cache;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> indexedClasses;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.infinispan.search.mapper.mapping.SearchMapping searchMapping;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.infinispan.query.backend.SegmentListener segmentListener;

    [CtConstructorImpl]public QueryInterceptor([CtParameterImpl][CtTypeReferenceImpl]boolean isManualIndexing, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ConcurrentMap<[CtTypeReferenceImpl]org.infinispan.transaction.xa.GlobalTransaction, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object>> txOldValues, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.AdvancedCache<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> cache, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> indexedClasses) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isManualIndexing = [CtVariableReadImpl]isManualIndexing;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.txOldValues = [CtVariableReadImpl]txOldValues;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.valueDataConversion = [CtInvocationImpl][CtVariableReadImpl]cache.getValueDataConversion();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.keyDataConversion = [CtInvocationImpl][CtVariableReadImpl]cache.getKeyDataConversion();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isPersistenceEnabled = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cache.getCacheConfiguration().persistence().usingStores();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cache = [CtVariableReadImpl]cache;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexedClasses = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtVariableReadImpl]indexedClasses);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.infinispan.factories.annotations.Start
    protected [CtTypeReferenceImpl]void start() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]stopping.set([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isClustered = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]cache.getCacheConfiguration().clustering().cacheMode().isClustered();
        [CtIfImpl]if ([CtVariableReadImpl]isClustered) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]segmentListener = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.infinispan.query.backend.SegmentListener([CtFieldReadImpl]cache, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::purgeIndex, [CtFieldReadImpl]blockingManager);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.cache.addListener([CtFieldReadImpl]segmentListener);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]searchMapping = [CtInvocationImpl][CtTypeAccessImpl]org.infinispan.query.impl.ComponentRegistryUtils.getSearchMapping([CtFieldReadImpl]cache);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void prepareForStopping() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]segmentListener != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]cache.removeListener([CtFieldReadImpl]segmentListener);

        [CtInvocationImpl][CtFieldReadImpl]stopping.set([CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean shouldModifyIndexes([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.FlagAffectedCommand command, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]isManualIndexing)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]distributionManager == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]key == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.infinispan.distribution.DistributionInfo info = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]distributionManager.getCacheTopology().getDistribution([CtVariableReadImpl]key);
        [CtReturnImpl][CtCommentImpl]// If this is a backup node we should modify the entry in the remote context
        return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]info.isPrimary() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]info.isWriteOwner() && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ctx.isInTxScope() || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]ctx.isOriginLocal())) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]command != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]command.hasAnyFlag([CtTypeAccessImpl]FlagBitSets.PUT_FOR_STATE_TRANSFER))));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.infinispan.util.concurrent.BlockingManager getBlockingManager() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]blockingManager;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object handleDataWriteCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.DataWriteCommand command) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]command.hasAnyFlag([CtTypeAccessImpl]FlagBitSets.SKIP_INDEXING)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]invokeNext([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
        }
        [CtReturnImpl]return [CtInvocationImpl]invokeNextThenApply([CtVariableReadImpl]ctx, [CtVariableReadImpl]command, [CtLambdaImpl]([CtParameterImpl] rCtx,[CtParameterImpl] cmd,[CtParameterImpl] rv) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cmd.isSuccessful()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]rv;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean unreliablePrevious = [CtInvocationImpl]unreliablePreviousValue([CtVariableReadImpl]command);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ctx.isInTxScope()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object> oldValues = [CtInvocationImpl]getOldValuesMap([CtVariableReadImpl](([CtTypeReferenceImpl]TxInvocationContext<[CtWildcardReferenceImpl]?>) (ctx)));
                [CtInvocationImpl]registerOldValue([CtVariableReadImpl]ctx, [CtInvocationImpl][CtVariableReadImpl]command.getKey(), [CtVariableReadImpl]unreliablePrevious, [CtVariableReadImpl]oldValues);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]asyncValue([CtInvocationImpl][CtInvocationImpl]indexIfNeeded([CtVariableReadImpl]rCtx, [CtVariableReadImpl]cmd, [CtVariableReadImpl]unreliablePrevious, [CtInvocationImpl][CtVariableReadImpl]cmd.getKey()).thenApply([CtLambdaImpl]([CtParameterImpl] ignored) -> [CtVariableReadImpl]rv));
            }
            [CtReturnImpl]return [CtVariableReadImpl]rv;
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object handleManyWriteCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.WriteCommand command) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]command.hasAnyFlag([CtTypeAccessImpl]FlagBitSets.SKIP_INDEXING)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]invokeNext([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
        }
        [CtReturnImpl]return [CtInvocationImpl]invokeNextThenApply([CtVariableReadImpl]ctx, [CtVariableReadImpl]command, [CtLambdaImpl]([CtParameterImpl] rCtx,[CtParameterImpl] cmd,[CtParameterImpl] rv) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cmd.isSuccessful()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]rv;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean unreliablePrevious = [CtInvocationImpl]unreliablePreviousValue([CtVariableReadImpl]command);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ctx.isInTxScope()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object> oldValues = [CtInvocationImpl]getOldValuesMap([CtVariableReadImpl](([CtTypeReferenceImpl]TxInvocationContext<[CtWildcardReferenceImpl]?>) (ctx)));
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object key : [CtInvocationImpl][CtVariableReadImpl]command.getAffectedKeys()) [CtBlockImpl]{
                    [CtInvocationImpl]registerOldValue([CtVariableReadImpl]ctx, [CtVariableReadImpl]key, [CtVariableReadImpl]unreliablePrevious, [CtVariableReadImpl]oldValues);
                }
                [CtReturnImpl]return [CtVariableReadImpl]rv;
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]asyncValue([CtInvocationImpl]allOf([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getAffectedKeys().stream().map([CtLambdaImpl]([CtParameterImpl] key) -> [CtInvocationImpl]indexIfNeeded([CtVariableReadImpl]rCtx, [CtVariableReadImpl]cmd, [CtVariableReadImpl]unreliablePrevious, [CtVariableReadImpl]key)).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]java.util.concurrent.CompletableFuture[]::new)));
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void registerOldValue([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeReferenceImpl]boolean unreliablePrevious, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object> oldValues) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.infinispan.container.entries.CacheEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> entryTx = [CtInvocationImpl][CtVariableReadImpl]ctx.lookupEntry([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]entryTx != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entryTx.getValue() != [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtVariableReadImpl]unreliablePrevious))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.infinispan.container.entries.ReadCommittedEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> mvccEntry = [CtVariableReadImpl](([CtTypeReferenceImpl]org.infinispan.container.entries.ReadCommittedEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>) (entryTx));
            [CtInvocationImpl][CtVariableReadImpl]oldValues.putIfAbsent([CtVariableReadImpl]key, [CtInvocationImpl][CtVariableReadImpl]mvccEntry.getOldValue());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Object, [CtTypeReferenceImpl]java.lang.Object> getOldValuesMap([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.impl.TxInvocationContext<[CtWildcardReferenceImpl]?> ctx) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]txOldValues.computeIfAbsent([CtInvocationImpl][CtVariableReadImpl]ctx.getGlobalTransaction(), [CtLambdaImpl]([CtParameterImpl] gid) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getCacheTransaction().addListener([CtLambdaImpl]() -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]txOldValues.remove([CtVariableReadImpl]gid));
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.infinispan.query.backend.HashMap<>();
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> indexIfNeeded([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext rCtx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.WriteCommand cmd, [CtParameterImpl][CtTypeReferenceImpl]boolean unreliablePrevious, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.infinispan.container.entries.CacheEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> entry = [CtInvocationImpl][CtVariableReadImpl]rCtx.lookupEntry([CtVariableReadImpl]key);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isStale = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object old = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entry instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.infinispan.container.entries.MVCCEntry) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.infinispan.container.entries.ReadCommittedEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> mvccEntry = [CtVariableReadImpl](([CtTypeReferenceImpl]org.infinispan.container.entries.ReadCommittedEntry<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>) (entry));
            [CtAssignmentImpl][CtVariableWriteImpl]isStale = [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]mvccEntry.isCommitted();
            [CtAssignmentImpl][CtVariableWriteImpl]old = [CtConditionalImpl]([CtVariableReadImpl]unreliablePrevious) ? [CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.UNKNOWN : [CtInvocationImpl][CtVariableReadImpl]mvccEntry.getOldValue();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]entry != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]entry.isChanged()) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isStale)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.debugf([CtLiteralImpl]"Try indexing command '%s',key='%s', oldValue='%s', stale='%s'", [CtVariableReadImpl]cmd, [CtVariableReadImpl]key, [CtVariableReadImpl]old, [CtVariableReadImpl]isStale);
            }
            [CtReturnImpl]return [CtInvocationImpl]processChange([CtVariableReadImpl]rCtx, [CtVariableReadImpl]cmd, [CtVariableReadImpl]key, [CtVariableReadImpl]old, [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.debugf([CtLiteralImpl]"Skipping indexing for command '%s',key='%s', oldValue='%s', stale='%s'", [CtVariableReadImpl]cmd, [CtVariableReadImpl]key, [CtVariableReadImpl]old, [CtVariableReadImpl]isStale);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.infinispan.util.concurrent.CompletableFutures.completedNull();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean unreliablePreviousValue([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.WriteCommand command) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// alternative approach would be changing the flag and forcing load type in an interceptor before EWI
        return [CtBinaryOperatorImpl][CtFieldReadImpl]isPersistenceEnabled && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]command.loadType() == [CtFieldReadImpl]VisitableCommand.LoadType.DONT_LOAD) || [CtInvocationImpl][CtVariableReadImpl]command.hasAnyFlag([CtTypeAccessImpl]FlagBitSets.SKIP_CACHE_LOAD));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitPutKeyValueCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.PutKeyValueCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitIracPutKeyValueCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.IracPutKeyValueCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitRemoveCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.RemoveCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitReplaceCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.ReplaceCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitComputeCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.ComputeCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitComputeIfAbsentCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.ComputeIfAbsentCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitPutMapCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.PutMapCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleManyWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitClearCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.ClearCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]invokeNextThenAccept([CtVariableReadImpl]ctx, [CtVariableReadImpl]command, [CtFieldReadImpl]processClearCommand);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitReadWriteKeyCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.ReadWriteKeyCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitWriteOnlyKeyCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.WriteOnlyKeyCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitReadWriteKeyValueCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.ReadWriteKeyValueCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitWriteOnlyManyEntriesCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.WriteOnlyManyEntriesCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleManyWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitWriteOnlyKeyValueCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.WriteOnlyKeyValueCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleDataWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitWriteOnlyManyCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.WriteOnlyManyCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleManyWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitReadWriteManyCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.ReadWriteManyCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleManyWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object visitReadWriteManyEntriesCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.functional.ReadWriteManyEntriesCommand command) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]handleManyWriteCommand([CtVariableReadImpl]ctx, [CtVariableReadImpl]command);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove all entries from all known indexes
     */
    public [CtTypeReferenceImpl]void purgeAllIndexes() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]searchMapping == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]searchMapping.scopeAll().workspace().purge();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void purgeIndex([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> entityType) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]searchMapping == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]searchMapping.scope([CtVariableReadImpl]entityType).workspace().purge();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Removes from the index the entries corresponding to the supplied segments, if the index is local.
     */
    [CtTypeReferenceImpl]void purgeIndex([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commons.util.IntSet segments) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]segments == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]segments.isEmpty()) || [CtBinaryOperatorImpl]([CtFieldReadImpl]searchMapping == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> routingKeys = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]segments.intStream().boxed().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::toString).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]searchMapping.scopeAll().workspace().purge([CtVariableReadImpl]routingKeys);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove entries from all indexes by key.
     */
    [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> removeFromIndexes([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeReferenceImpl]int segment) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getSearchIndexer().purge([CtVariableReadImpl]key, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]segment));
    }

    [CtMethodImpl][CtCommentImpl]// Method that will be called when data needs to be removed from Lucene.
    private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> removeFromIndexes([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeReferenceImpl]int segment) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getSearchIndexer().delete([CtVariableReadImpl]key, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]segment), [CtVariableReadImpl]value);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> updateIndexes([CtParameterImpl][CtTypeReferenceImpl]boolean usingSkipIndexCleanupFlag, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeReferenceImpl]int segment) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Note: it's generally unsafe to assume there is no previous entry to cleanup: always use UPDATE
        [CtCommentImpl]// unless the specific flag is allowing this.
        if ([CtVariableReadImpl]usingSkipIndexCleanupFlag) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getSearchIndexer().add([CtVariableReadImpl]key, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]segment), [CtVariableReadImpl]value);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getSearchIndexer().addOrUpdate([CtVariableReadImpl]key, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]segment), [CtVariableReadImpl]value);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The indexed classes.
     *
     * @deprecated since 11
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> indexedEntities() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]indexedClasses;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.infinispan.search.mapper.work.SearchIndexer getSearchIndexer() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]searchMapping.getSearchIndexer();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object extractValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object storedValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]valueDataConversion.extractIndexable([CtVariableReadImpl]storedValue);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object extractKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object storedKey) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]keyDataConversion.extractIndexable([CtVariableReadImpl]storedKey);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> processChange([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.FlagAffectedCommand command, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object storedKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object storedOldValue, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object storedNewValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int segment = [CtInvocationImpl][CtTypeAccessImpl]org.infinispan.commands.SegmentSpecificCommand.extractSegment([CtVariableReadImpl]command, [CtVariableReadImpl]storedKey, [CtFieldReadImpl]keyPartitioner);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object key = [CtInvocationImpl]extractKey([CtVariableReadImpl]storedKey);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object oldValue = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]storedOldValue == [CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.UNKNOWN) ? [CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.UNKNOWN : [CtInvocationImpl]extractValue([CtVariableReadImpl]storedOldValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object newValue = [CtInvocationImpl]extractValue([CtVariableReadImpl]storedNewValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean skipIndexCleanup = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]command != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]command.hasAnyFlag([CtTypeAccessImpl]FlagBitSets.SKIP_INDEX_CLEANUP);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtWildcardReferenceImpl]?> operation = [CtInvocationImpl][CtTypeAccessImpl]org.infinispan.util.concurrent.CompletableFutures.completedNull();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]skipIndexCleanup) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldValue == [CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.UNKNOWN) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]shouldModifyIndexes([CtVariableReadImpl]command, [CtVariableReadImpl]ctx, [CtVariableReadImpl]storedKey)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]operation = [CtInvocationImpl]removeFromIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]segment);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]isPotentiallyIndexedType([CtVariableReadImpl]oldValue) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]newValue == [CtLiteralImpl]null) || [CtInvocationImpl]shouldRemove([CtVariableReadImpl]newValue, [CtVariableReadImpl]oldValue))) && [CtInvocationImpl]shouldModifyIndexes([CtVariableReadImpl]command, [CtVariableReadImpl]ctx, [CtVariableReadImpl]storedKey)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]operation = [CtInvocationImpl]removeFromIndexes([CtVariableReadImpl]oldValue, [CtVariableReadImpl]key, [CtVariableReadImpl]segment);
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isTraceEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.tracef([CtLiteralImpl]"Index cleanup not needed for %s -> %s", [CtVariableReadImpl]oldValue, [CtVariableReadImpl]newValue);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isTraceEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.tracef([CtLiteralImpl]"Skipped index cleanup for command %s", [CtVariableReadImpl]command);
        }
        [CtIfImpl]if ([CtInvocationImpl]isPotentiallyIndexedType([CtVariableReadImpl]newValue)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]shouldModifyIndexes([CtVariableReadImpl]command, [CtVariableReadImpl]ctx, [CtVariableReadImpl]storedKey)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// This means that the entry is just modified so we need to update the indexes and not add to them.
                [CtVariableWriteImpl]operation = [CtInvocationImpl][CtVariableReadImpl]operation.thenCompose([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl]updateIndexes([CtVariableReadImpl]skipIndexCleanup, [CtVariableReadImpl]newValue, [CtVariableReadImpl]key, [CtVariableReadImpl]segment));
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isTraceEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.tracef([CtLiteralImpl]"Not modifying index for %s (%s)", [CtVariableReadImpl]storedKey, [CtVariableReadImpl]command);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.isTraceEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.infinispan.query.backend.QueryInterceptor.log.tracef([CtLiteralImpl]"Update not needed for %s", [CtVariableReadImpl]newValue);
        }
        [CtReturnImpl]return [CtVariableReadImpl]operation;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean shouldRemove([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object previousValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]value != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]previousValue != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]value.getClass() != [CtInvocationImpl][CtVariableReadImpl]previousValue.getClass());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processClearCommand([CtParameterImpl][CtTypeReferenceImpl]org.infinispan.context.InvocationContext ctx, [CtParameterImpl][CtTypeReferenceImpl]org.infinispan.commands.write.ClearCommand command, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object rv) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]shouldModifyIndexes([CtVariableReadImpl]command, [CtVariableReadImpl]ctx, [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl]purgeAllIndexes();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isStopping() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]stopping.get();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param value
     * 		An entity.
     * @return {@code true} if there is a chance that this entity is of an indexed types.
    For protobuf entities which are not yet deserialized,
    this returns {@code true} even though we don't know the exact type until the entity is deserialized.
    The {@link org.infinispan.search.mapper.mapping.EntityConverter entity converter}
    that takes care of deserialization will take care of cancelling indexing
    if it turns out the actual type of the entity is not one that should be indexed.
     */
    private [CtTypeReferenceImpl]boolean isPotentiallyIndexedType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]searchMapping == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> convertedType = [CtInvocationImpl][CtFieldReadImpl]searchMapping.toConvertedEntityJavaClass([CtVariableReadImpl]value);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]searchMapping.allIndexedEntityJavaClasses().contains([CtVariableReadImpl]convertedType);
    }
}
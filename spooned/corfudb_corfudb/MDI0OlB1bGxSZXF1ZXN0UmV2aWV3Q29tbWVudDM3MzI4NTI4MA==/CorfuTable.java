[CompilationUnitImpl][CtPackageDeclarationImpl]package org.corfudb.runtime.collections;
[CtImportImpl]import java.util.function.Predicate;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.corfudb.runtime.object.ICorfuExecutionContext;
[CtImportImpl]import java.util.ConcurrentModificationException;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.corfudb.annotations.Mutator;
[CtUnresolvedImport]import org.corfudb.annotations.PassThrough;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.function.BiFunction;
[CtUnresolvedImport]import com.google.common.reflect.TypeToken;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import org.corfudb.annotations.CorfuObject;
[CtUnresolvedImport]import org.corfudb.annotations.TransactionalMethod;
[CtUnresolvedImport]import org.corfudb.runtime.object.ICorfuVersionPolicy;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.function.Supplier;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtUnresolvedImport]import org.corfudb.annotations.ConflictParameter;
[CtUnresolvedImport]import org.corfudb.annotations.DontInstrument;
[CtUnresolvedImport]import org.corfudb.util.ImmutableListSetWrapper;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import org.corfudb.annotations.Accessor;
[CtUnresolvedImport]import org.corfudb.annotations.MutatorAccessor;
[CtUnresolvedImport]import org.corfudb.runtime.object.ICorfuSMR;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.function.BiConsumer;
[CtClassImpl][CtJavaDocImpl]/**
 * The CorfuTable implements a simple key-value store.
 *
 * <p>The primary interface to the CorfuTable is a Map, where the keys must be unique and
 * each key is mapped to exactly one value. Null values are not permitted.
 *
 * <p>The CorfuTable also supports an unlimited number of secondary indexes, which
 * the user provides at construction time as an enum which implements the IndexSpecification
 * interface. An index specification consists of a IndexFunction, which returns a set of secondary
 * keys (indexes) the value should be mapped to. Secondary indexes are many-to-many: values
 * can be mapped to multiple indexes, and indexes can be mapped to multiples values. Each
 * IndexSpecification also specifies a projection function, which specifies a transformation
 * that can be done on a retrieval on the index. A common projection is to emit only the
 * values.
 *
 * @param <K>
 * 		The type of the primary key.
 * @param <V>
 * 		The type of the values to be mapped.
 */
[CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
[CtAnnotationImpl]@org.corfudb.annotations.CorfuObject
public class CorfuTable<[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> implements [CtTypeReferenceImpl]org.corfudb.runtime.collections.ICorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> , [CtTypeReferenceImpl]org.corfudb.runtime.object.ICorfuSMR<[CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> {
    [CtFieldImpl][CtCommentImpl]// The "main" map which contains the primary key-value mappings.
    private final [CtTypeReferenceImpl]org.corfudb.runtime.collections.ContextAwareMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> mainMap;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Index.Spec<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Comparable>> indexSpec;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>>> secondaryIndexes;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> optimisticTable;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.corfudb.runtime.collections.VersionPolicy versionPolicy;

    [CtConstructorImpl]public CorfuTable([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.ContextAwareMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> mainMap, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Index.Spec<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Comparable>> indexSpec, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>>> secondaryIndexe, [CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> optimisticTable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mainMap = [CtVariableReadImpl]mainMap;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexSpec = [CtVariableReadImpl]indexSpec;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.secondaryIndexes = [CtVariableReadImpl]secondaryIndexe;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.optimisticTable = [CtVariableReadImpl]optimisticTable;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.versionPolicy = [CtFieldReadImpl]org.corfudb.runtime.object.ICorfuVersionPolicy.DEFAULT;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * The main constructor that generates a table with a given implementation of the
     * {@link StreamingMap} along with {@link Index.Registry} and {@link VersionPolicy}
     * specification.
     */
    public CorfuTable([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.Index.Registry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> indices, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]org.corfudb.runtime.collections.ContextAwareMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> streamingMapSupplier, [CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.VersionPolicy versionPolicy) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexSpec = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.secondaryIndexes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mainMap = [CtInvocationImpl][CtVariableReadImpl]streamingMapSupplier.get();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.versionPolicy = [CtVariableReadImpl]versionPolicy;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.optimisticTable = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<>([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.mainMap.getOptimisticMap(), [CtFieldReadImpl][CtThisAccessImpl]this.indexSpec, [CtFieldReadImpl][CtThisAccessImpl]this.secondaryIndexes, [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]indices.forEach([CtLambdaImpl]([CtParameterImpl] index) -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]secondaryIndexes.put([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.getName().get(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.corfudb.runtime.collections.HashMap<>());
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]indexSpec.add([CtVariableReadImpl]index);
        });
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.keySet().size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"CorfuTable: creating CorfuTable with the following indexes: {}", [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.keySet());
        }
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Generate a table with a given implementation for the {@link StreamingMap} and
     * {@link Index.Registry}.
     */
    public CorfuTable([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.Index.Registry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> indices, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]org.corfudb.runtime.collections.ContextAwareMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> streamingMapSupplier) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]indices, [CtVariableReadImpl]streamingMapSupplier, [CtTypeAccessImpl]ICorfuVersionPolicy.DEFAULT);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Generate a table with a given implementation for the {@link StreamingMap},
     * and {@link VersionPolicy}.
     */
    public CorfuTable([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]org.corfudb.runtime.collections.ContextAwareMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> streamingMapSupplier, [CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.VersionPolicy versionPolicy) [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]Index.Registry.empty(), [CtVariableReadImpl]streamingMapSupplier, [CtVariableReadImpl]versionPolicy);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Generate a table with the given {@link Index.Registry}.
     */
    public CorfuTable([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.Index.Registry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> indexRegistry) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]indexRegistry, [CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.corfudb.runtime.collections.StreamingMapDecorator<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.corfudb.runtime.collections.HashMap<>()), [CtTypeAccessImpl]ICorfuVersionPolicy.DEFAULT);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Default constructor. Generates a table without any secondary indexes.
     */
    public CorfuTable() [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]Index.Registry.empty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper function to get a map (non-secondary index) Corfu table.
     *
     * @param <K>
     * 		Key type
     * @param <V>
     * 		Value type
     * @return A type token to pass to the builder.
     */
    static <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> getMapType() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>>()[CtClassImpl] {};
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper function to get a Corfu Table.
     *
     * @param <K>
     * 		Key type
     * @param <V>
     * 		Value type
     * @return A type token to pass to the builder.
     */
    static <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> getTableType() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>>()[CtClassImpl] {};
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]int size() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.size();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]boolean isEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.isEmpty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return whether this table has secondary indexes or not.
     *
     * @return True, if secondary indexes are present. False otherwise.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]boolean hasSecondaryIndices() [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.isEmpty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]boolean containsKey([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeReferenceImpl]java.lang.Object key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.containsKey([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]boolean containsValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.containsValue([CtVariableReadImpl]value);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeParameterReferenceImpl]V get([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeReferenceImpl]java.lang.Object key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.get([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a mapping using the specified index function.
     *
     * @param indexName
     * 		Name of the the secondary index to query.
     * @param indexKey
     * 		The index key used to query the secondary index
     * @return A collection of Map.Entry<K, V>
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public <[CtTypeParameterImpl]I extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]I>> [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.corfudb.runtime.collections.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> getByIndex([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.Index.Name indexName, [CtParameterImpl][CtTypeParameterReferenceImpl]I indexKey) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String secondaryIndex = [CtInvocationImpl][CtVariableReadImpl]indexName.get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> secondaryMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.containsKey([CtVariableReadImpl]secondaryIndex) && [CtBinaryOperatorImpl]([CtAssignmentImpl]([CtVariableWriteImpl]secondaryMap = [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.get([CtVariableReadImpl]secondaryIndex)) != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// If secondary index exists and function for this index is not null
            [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> res = [CtInvocationImpl][CtVariableReadImpl]secondaryMap.get([CtVariableReadImpl]indexKey);
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]res == [CtLiteralImpl]null ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptySet() : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtInvocationImpl][CtVariableReadImpl]res.entrySet());
        }
        [CtInvocationImpl][CtCommentImpl]// If index is not specified, the lookup by index API must fail.
        [CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"CorfuTable: secondary index " + [CtVariableReadImpl]secondaryIndex) + [CtLiteralImpl]" does not exist for this table, cannot complete the get by index.");
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Secondary Index " + [CtVariableReadImpl]secondaryIndex) + [CtLiteralImpl]" is not defined.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Scan and filter using the specified index function and projection.
     *
     * @param indexName
     * 		Name of the the secondary index to query.
     * @param entryPredicate
     * 		The predicate to scan and filter with.
     * @param indexKey
     * 		A collection of Map.Entry<K, V>
     * @return  */
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public <[CtTypeParameterImpl]I extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]I>> [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> getByIndexAndFilter([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.Index.Name indexName, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.util.function.Predicate<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]org.corfudb.runtime.collections.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> entryPredicate, [CtParameterImpl][CtTypeParameterReferenceImpl]I indexKey) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.corfudb.runtime.collections.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> entryStream;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String secondaryIndex = [CtInvocationImpl][CtVariableReadImpl]indexName.get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> secondaryMap;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.containsKey([CtVariableReadImpl]secondaryIndex) && [CtBinaryOperatorImpl]([CtAssignmentImpl]([CtVariableWriteImpl]secondaryMap = [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.get([CtVariableReadImpl]secondaryIndex)) != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]secondaryMap.get([CtVariableReadImpl]indexKey) == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]entryStream = [CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.empty();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]entryStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]secondaryMap.get([CtVariableReadImpl]indexKey).entrySet().stream();
            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entryStream.filter([CtVariableReadImpl]entryPredicate).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
        }
        [CtInvocationImpl][CtCommentImpl]// If there are is no index function available, fail
        [CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"CorfuTable: secondary index " + [CtVariableReadImpl]secondaryIndex) + [CtLiteralImpl]" does not exist for this table, cannot complete the get by index and filter.");
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Secondary Index " + [CtVariableReadImpl]secondaryIndex) + [CtLiteralImpl]" is not defined.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.MutatorAccessor(name = [CtLiteralImpl]"put", undoFunction = [CtLiteralImpl]"undoPut", undoRecordFunction = [CtLiteralImpl]"undoPutRecord")
    public [CtTypeParameterReferenceImpl]V put([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V previous = [CtInvocationImpl][CtFieldReadImpl]mainMap.put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
        [CtIfImpl][CtCommentImpl]// If we have index functions, update the secondary indexes.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]unmapSecondaryIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]previous);
            [CtInvocationImpl]mapSecondaryIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]value);
        }
        [CtReturnImpl]return [CtVariableReadImpl]previous;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    protected [CtTypeParameterReferenceImpl]V undoPutRecord([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> table, [CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]table.mainMap.get([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    protected [CtTypeReferenceImpl]void undoPut([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> table, [CtParameterImpl][CtTypeParameterReferenceImpl]V undoRecord, [CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Same as undoRemove (restore previous value)
        undoRemove([CtVariableReadImpl]table, [CtVariableReadImpl]undoRecord, [CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtArrayTypeReferenceImpl]java.lang.Object[] putAllConflictFunction([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> m) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.keySet().stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Object::hashCode).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]java.lang.Object[]::new);
    }

    [CtEnumImpl]enum UndoNullable {

        [CtEnumValueImpl]NULL;}

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate an undo record for putAll, given the previous state of the map
     * and the parameters to the putAll call.
     *
     * @param previousState
     * 		The previous state of the map
     * @param m
     * 		The map from the putAll call
     * @return An undo record, which for a putAll is all the
    previous entries in the map.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> undoPutAllRecord([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> previousState, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> m) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableMap.Builder<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> builder = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.builder();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.keySet().forEach([CtLambdaImpl]([CtParameterImpl] k) -> [CtInvocationImpl][CtVariableReadImpl]builder.put([CtVariableReadImpl]k, [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]previousState.get([CtVariableReadImpl]k) == [CtLiteralImpl]null ? [CtFieldReadImpl](([CtTypeParameterReferenceImpl]V) ([CtTypeAccessImpl]org.corfudb.runtime.collections.CorfuTable.UndoNullable.[CtFieldReferenceImpl]NULL)) : [CtInvocationImpl][CtVariableReadImpl]previousState.get([CtVariableReadImpl]k)));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Undo a remove, given the current state of the map, an undo record
     * and the arguments to the remove command to undo.
     *
     * @param table
     * 		The state of the map after the put to undo
     * @param undoRecord
     * 		The undo record generated by undoRemoveRecord
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtTypeReferenceImpl]void undoPutAll([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> table, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> undoRecord, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> m) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]undoRecord.entrySet().forEach([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<K, V> e) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getValue() == [CtFieldReadImpl][CtTypeAccessImpl]org.corfudb.runtime.collections.CorfuTable.UndoNullable.[CtFieldReferenceImpl]NULL) [CtBlockImpl]{
                [CtInvocationImpl]undoRemove([CtVariableReadImpl]table, [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]e.getKey());
            } else [CtBlockImpl]{
                [CtInvocationImpl]undoRemove([CtVariableReadImpl]table, [CtInvocationImpl][CtVariableReadImpl]e.getValue(), [CtInvocationImpl][CtVariableReadImpl]e.getKey());
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Mutator(name = [CtLiteralImpl]"put", noUpcall = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void insert([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtCommentImpl]// This is just a stub, the annotation processor will generate an update with
        [CtCommentImpl]// put(key, value), since this method doesn't require an upcall therefore no
        [CtCommentImpl]// operations are needed to be executed on the internal data structure
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a filtered {@link List} view of the values contained in this map.
     * This method has a memory/CPU advantage over the map iterators as no deep copy
     * is actually performed.
     *
     * @param valuePredicate
     * 		java predicate (function to evaluate)
     * @return a view of the values contained in this map meeting the predicate condition.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]java.util.List<[CtTypeParameterReferenceImpl]V> scanAndFilter([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V> valuePredicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mainMap.entryStream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Entry::getValue).filter([CtVariableReadImpl]valuePredicate).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> scanAndFilterByEntry([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> entryPredicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mainMap.entryStream().filter([CtVariableReadImpl]entryPredicate).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.MutatorAccessor(name = [CtLiteralImpl]"remove", undoFunction = [CtLiteralImpl]"undoRemove", undoRecordFunction = [CtLiteralImpl]"undoRemoveRecord")
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeParameterReferenceImpl]V remove([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeReferenceImpl]java.lang.Object key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V previous = [CtInvocationImpl][CtFieldReadImpl]mainMap.remove([CtVariableReadImpl]key);
        [CtInvocationImpl]unmapSecondaryIndexes([CtVariableReadImpl](([CtTypeParameterReferenceImpl]K) (key)), [CtVariableReadImpl]previous);
        [CtReturnImpl]return [CtVariableReadImpl]previous;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    protected [CtTypeParameterReferenceImpl]V undoRemoveRecord([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> table, [CtParameterImpl][CtTypeParameterReferenceImpl]K key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]table.mainMap.get([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    protected [CtTypeReferenceImpl]void undoRemove([CtParameterImpl][CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> table, [CtParameterImpl][CtTypeParameterReferenceImpl]V undoRecord, [CtParameterImpl][CtTypeParameterReferenceImpl]K key) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]undoRecord == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V previous = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]table.mainMap.remove([CtVariableReadImpl]key);
            [CtInvocationImpl][CtVariableReadImpl]table.unmapSecondaryIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]previous);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V previous = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]table.mainMap.put([CtVariableReadImpl]key, [CtVariableReadImpl]undoRecord);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]table.secondaryIndexes.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]table.unmapSecondaryIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]previous);
                [CtInvocationImpl][CtVariableReadImpl]table.mapSecondaryIndexes([CtVariableReadImpl]key, [CtVariableReadImpl]undoRecord);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Mutator(name = [CtLiteralImpl]"remove", noUpcall = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void delete([CtParameterImpl][CtAnnotationImpl]@org.corfudb.annotations.ConflictParameter
    [CtTypeParameterReferenceImpl]K key) [CtBlockImpl]{
        [CtCommentImpl]// This is just a stub, the annotation processor will generate an update with
        [CtCommentImpl]// remove(key), since this method doesn't require an upcall therefore no
        [CtCommentImpl]// operations are needed to be executed on the internal data structure
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Mutator(name = [CtLiteralImpl]"putAll", undoFunction = [CtLiteralImpl]"undoPutAll", undoRecordFunction = [CtLiteralImpl]"undoPutAllRecord", conflictParameterFunction = [CtLiteralImpl]"putAllConflictFunction")
    public [CtTypeReferenceImpl]void putAll([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> m) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// If we have no index functions, then just directly put all
        if ([CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mainMap.putAll([CtVariableReadImpl]m);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Otherwise we must update all secondary indexes
            [CtCommentImpl]// TODO: Do this in parallel (need to acquire update locks, potentially)
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.entrySet().stream().forEach([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<? extends K, ? extends V> e) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V previous = [CtInvocationImpl][CtFieldReadImpl]mainMap.put([CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtInvocationImpl][CtVariableReadImpl]e.getValue());
                [CtInvocationImpl]unmapSecondaryIndexes([CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtVariableReadImpl]previous);
                [CtInvocationImpl]mapSecondaryIndexes([CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtInvocationImpl][CtVariableReadImpl]e.getValue());
            });
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Mutator(name = [CtLiteralImpl]"clear", reset = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mainMap.clear();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.values().forEach([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Map::clear);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]K> keySet() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mainMap.entryStream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Entry::getKey).collect([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.toImmutableSet());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V> values() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mainMap.entryStream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Entry::getValue).collect([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.toImmutableSet());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Makes a shallow copy of the map (i.e. all map entries), but not the
     * key/values (i.e only the mappings are copied).
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.corfudb.runtime.collections.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> entrySet() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.corfudb.util.ImmutableListSetWrapper.fromMap([CtFieldReadImpl]mainMap);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Present the content of a {@link CorfuTable} via the {@link Stream} interface.
     *
     * @return stream of entries
     */
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.corfudb.runtime.collections.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> entryStream() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.entryStream();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeParameterReferenceImpl]V getOrDefault([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeParameterReferenceImpl]V defaultValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mainMap.getOrDefault([CtVariableReadImpl]key, [CtVariableReadImpl]defaultValue);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.Accessor
    public [CtTypeReferenceImpl]void forEach([CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiConsumer<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V> action) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mainMap.forEach([CtVariableReadImpl]action);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeReferenceImpl]void replaceAll([CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]function);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> entry : [CtInvocationImpl]entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]K k;
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V v;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]k = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtAssignmentImpl][CtVariableWriteImpl]v = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalStateException ise) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// this usually means the entry is no longer in the map.
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ConcurrentModificationException([CtVariableReadImpl]ise);
            }
            [CtAssignmentImpl][CtCommentImpl]// ise thrown from function is not a cme.
            [CtVariableWriteImpl]v = [CtInvocationImpl][CtVariableReadImpl]function.apply([CtVariableReadImpl]k, [CtVariableReadImpl]v);
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]insert([CtVariableReadImpl]k, [CtVariableReadImpl]v);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalStateException ise) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// this usually means the entry is no longer in the map.
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ConcurrentModificationException([CtVariableReadImpl]ise);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeParameterReferenceImpl]V putIfAbsent([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V v = [CtInvocationImpl]get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]v = [CtInvocationImpl]put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
        }
        [CtReturnImpl]return [CtVariableReadImpl]v;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeReferenceImpl]boolean remove([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object curValue = [CtInvocationImpl]get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]curValue, [CtVariableReadImpl]value)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]curValue == [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl]containsKey([CtVariableReadImpl]key)))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl]remove([CtVariableReadImpl]key);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeParameterReferenceImpl]V replace([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V curValue;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtAssignmentImpl]([CtVariableWriteImpl]curValue = [CtInvocationImpl]get([CtVariableReadImpl]key)) != [CtLiteralImpl]null) || [CtInvocationImpl]containsKey([CtVariableReadImpl]key)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]curValue = [CtInvocationImpl]put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
        }
        [CtReturnImpl]return [CtVariableReadImpl]curValue;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeParameterReferenceImpl]V computeIfAbsent([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> mappingFunction) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]mappingFunction);
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V v;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]v = [CtInvocationImpl]get([CtVariableReadImpl]key)) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V newValue;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]newValue = [CtInvocationImpl][CtVariableReadImpl]mappingFunction.apply([CtVariableReadImpl]key)) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]put([CtVariableReadImpl]key, [CtVariableReadImpl]newValue);
                [CtReturnImpl]return [CtVariableReadImpl]newValue;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]v;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeParameterReferenceImpl]V computeIfPresent([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]K, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> remappingFunction) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]remappingFunction);
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V oldValue;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]oldValue = [CtInvocationImpl]get([CtVariableReadImpl]key)) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V newValue = [CtInvocationImpl][CtVariableReadImpl]remappingFunction.apply([CtVariableReadImpl]key, [CtVariableReadImpl]oldValue);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newValue != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]put([CtVariableReadImpl]key, [CtVariableReadImpl]newValue);
                [CtReturnImpl]return [CtVariableReadImpl]newValue;
            } else [CtBlockImpl]{
                [CtInvocationImpl]remove([CtVariableReadImpl]key);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.corfudb.annotations.TransactionalMethod
    public [CtTypeParameterReferenceImpl]V merge([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> remappingFunction) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]remappingFunction);
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]value);
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V oldValue = [CtInvocationImpl]get([CtVariableReadImpl]key);
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V newValue = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]oldValue == [CtLiteralImpl]null) ? [CtVariableReadImpl]value : [CtInvocationImpl][CtVariableReadImpl]remappingFunction.apply([CtVariableReadImpl]oldValue, [CtVariableReadImpl]value);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newValue == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]remove([CtVariableReadImpl]key);
        } else [CtBlockImpl]{
            [CtInvocationImpl]put([CtVariableReadImpl]key, [CtVariableReadImpl]newValue);
        }
        [CtReturnImpl]return [CtVariableReadImpl]newValue;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Unmaps the secondary indexes for a given key value pair.
     *
     * @param key
     * 		The primary key (index) for the mapping.
     * @param value
     * 		The value to unmap.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    protected [CtTypeReferenceImpl]void unmapSecondaryIndexes([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]// Map entry into secondary indexes
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]Index.Spec<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Comparable> index : [CtFieldReadImpl]indexSpec) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.getName().get();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> secondaryIndex = [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.get([CtVariableReadImpl]indexName);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Comparable indexKey : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.getMultiValueIndexFunction().apply([CtVariableReadImpl]key, [CtVariableReadImpl]value)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> slot = [CtInvocationImpl][CtVariableReadImpl]secondaryIndex.get([CtVariableReadImpl]indexKey);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slot != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]slot.remove([CtVariableReadImpl]key, [CtVariableReadImpl]value);
                    }
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtLiteralImpl]"Received an exception while computing the index. " + [CtLiteralImpl]"This is most likely an issue with the client's indexing function. {}", [CtVariableReadImpl]e);
            [CtInvocationImpl][CtCommentImpl]// The index might be corrupt, and the only way to ensure safety is to disable
            [CtCommentImpl]// indexing for this table.
            clearIndex();
            [CtThrowImpl][CtCommentImpl]// In case of both a transactional and non-transactional operation, the client
            [CtCommentImpl]// is going to receive UnrecoverableCorfuError along with the appropriate cause.
            throw [CtVariableReadImpl]e;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Maps the secondary indexes for a given key value pair.
     *
     * @param key
     * 		the primary key associated with the indexing.
     * @param value
     * 		the value to map.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    protected [CtTypeReferenceImpl]void mapSecondaryIndexes([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.warn([CtLiteralImpl]"Attempting to build an index with a null value, skipping.");
            [CtReturnImpl]return;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]// Map entry into secondary indexes
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]Index.Spec<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Comparable> index : [CtFieldReadImpl]indexSpec) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.getName().get();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Comparable, [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> secondaryIndex = [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.get([CtVariableReadImpl]indexName);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Comparable indexKey : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]index.getMultiValueIndexFunction().apply([CtVariableReadImpl]key, [CtVariableReadImpl]value)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> slot = [CtInvocationImpl][CtVariableReadImpl]secondaryIndex.computeIfAbsent([CtVariableReadImpl]indexKey, [CtLambdaImpl]([CtParameterImpl]java.lang.Comparable k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>());
                    [CtInvocationImpl][CtVariableReadImpl]slot.put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtBinaryOperatorImpl][CtLiteralImpl]"Received an exception while computing the index. " + [CtLiteralImpl]"This is most likely an issue with the client's indexing function.", [CtVariableReadImpl]e);
            [CtInvocationImpl][CtCommentImpl]// The index might be corrupt, and the only way to ensure safety is to disable
            [CtCommentImpl]// indexing for this table.
            clearIndex();
            [CtThrowImpl][CtCommentImpl]// In case of both a transactional and non-transactional operation, the client
            [CtCommentImpl]// is going to receive UnrecoverableCorfuError along with the appropriate cause.
            throw [CtVariableReadImpl]e;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Disable all secondary indices for this table. Only used during error-recovery.
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    protected [CtTypeReferenceImpl]void clearIndex() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]indexSpec.clear();
        [CtInvocationImpl][CtFieldReadImpl]secondaryIndexes.clear();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@org.corfudb.annotations.PassThrough
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.corfudb.runtime.collections.CorfuTable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> getContext([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.corfudb.runtime.object.ICorfuExecutionContext.Context context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]context == [CtFieldReadImpl]OPTIMISTIC) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]optimisticTable;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.corfudb.runtime.collections.VersionPolicy getVersionPolicy() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]versionPolicy;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@org.corfudb.annotations.PassThrough
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.mainMap.close();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@org.corfudb.annotations.DontInstrument
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void closeWrapper() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.mainMap.close();
    }
}
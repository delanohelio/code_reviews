[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2020 Goldman Sachs and others.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
and Eclipse Distribution License v. 1.0 which accompany this distribution.
The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
and the Eclipse Distribution License is available at
http://www.eclipse.org/org/documents/edl-v10.php.
 */
[CtPackageDeclarationImpl]package org.eclipse.collections.impl.stack.immutable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.CharFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.block.predicate.Predicate;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableFloatStack;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.ByteArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.RichIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.multimap.MutableMultimap;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableDoubleStack;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableShortStack;
[CtUnresolvedImport]import org.eclipse.collections.api.partition.stack.PartitionImmutableStack;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.LongFunction;
[CtUnresolvedImport]import org.eclipse.collections.impl.map.mutable.UnifiedMap;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.utility.Iterate;
[CtUnresolvedImport]import org.eclipse.collections.api.block.procedure.Procedure2;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ObjectLongMap;
[CtUnresolvedImport]import org.eclipse.collections.api.list.ListIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
[CtImportImpl]import java.io.Externalizable;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.io.Serializable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.FloatFunction;
[CtUnresolvedImport]import org.eclipse.collections.impl.partition.stack.PartitionArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.ordered.OrderedIterable;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.ShortArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.block.procedure.Procedure;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.BooleanArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.impl.list.mutable.FastList;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.LongArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.map.MutableMap;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.factory.Predicates;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.IntFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.ByteFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.map.MutableMapIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableByteStack;
[CtUnresolvedImport]import org.eclipse.collections.api.block.predicate.Predicate2;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.MutableBag;
[CtUnresolvedImport]import org.eclipse.collections.api.list.MutableList;
[CtUnresolvedImport]import org.eclipse.collections.impl.multimap.list.FastListMultimap;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableCharStack;
[CtUnresolvedImport]import org.eclipse.collections.api.map.sorted.MutableSortedMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.FloatArrayStack;
[CtImportImpl]import java.util.EmptyStackException;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.set.MutableSet;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.procedure.MutatingAggregationProcedure;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.LongIterable;
[CtUnresolvedImport]import org.eclipse.collections.impl.list.Interval;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.ShortFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.DoubleArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.CharIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ImmutableObjectLongMap;
[CtUnresolvedImport]import org.eclipse.collections.api.set.sorted.MutableSortedSet;
[CtUnresolvedImport]import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
[CtUnresolvedImport]import org.eclipse.collections.api.DoubleIterable;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.io.ObjectInput;
[CtUnresolvedImport]import org.eclipse.collections.api.FloatIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.IntIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableIntStack;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.bimap.MutableBiMap;
[CtUnresolvedImport]import org.eclipse.collections.api.LazyIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function0;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function2;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.eclipse.collections.api.ByteIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.tuple.Pair;
[CtUnresolvedImport]import org.eclipse.collections.api.BooleanIterable;
[CtImportImpl]import java.io.ObjectOutput;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.ImmutableStack;
[CtUnresolvedImport]import org.eclipse.collections.api.ShortIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.procedure.checked.CheckedProcedure;
[CtUnresolvedImport]import org.eclipse.collections.impl.stack.mutable.primitive.CharArrayStack;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.StackIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.map.ImmutableMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.utility.LazyIterate;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableBooleanStack;
[CtUnresolvedImport]import org.eclipse.collections.api.stack.primitive.ImmutableLongStack;
[CtClassImpl][CtJavaDocImpl]/**
 * The immutable equivalent of ArrayStack. Wraps a FastList.
 *
 * Deprecated this class because it was replaced by singly-linked implementation of immutable stack.
 * https://github.com/eclipse/eclipse-collections/pull/767
 */
[CtAnnotationImpl]@java.lang.Deprecated
final class ImmutableArrayStack<[CtTypeParameterImpl]T> implements [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> , [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> delegate;

    [CtConstructorImpl]private ImmutableArrayStack([CtParameterImpl][CtArrayTypeReferenceImpl]T[] newElements) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.delegate = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newListWith([CtVariableReadImpl]newElements);
    }

    [CtConstructorImpl]private ImmutableArrayStack([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> newElements) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.delegate = [CtVariableReadImpl]newElements;
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> newStack() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList());
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]E> [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<[CtTypeParameterReferenceImpl]E> newStack([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]E> iterable) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtInvocationImpl](([CtArrayTypeReferenceImpl]E[]) ([CtTypeAccessImpl]org.eclipse.collections.impl.utility.Iterate.toArray([CtVariableReadImpl]iterable))));
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]E> [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<[CtTypeParameterReferenceImpl]E> newStackWith([CtParameterImpl]E... elements) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtInvocationImpl][CtVariableReadImpl]elements.clone());
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<[CtTypeParameterReferenceImpl]T> newStackFromTopToBottom([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> items) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.<[CtTypeParameterReferenceImpl]T>newList([CtVariableReadImpl]items).reverseThis());
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<[CtTypeParameterReferenceImpl]T> newStackFromTopToBottom([CtParameterImpl]T... items) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newListWith([CtVariableReadImpl]items).reverseThis());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> push([CtParameterImpl][CtTypeParameterReferenceImpl]T item) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> newDelegate = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList([CtFieldReadImpl][CtThisAccessImpl]this.delegate);
        [CtInvocationImpl][CtVariableReadImpl]newDelegate.add([CtVariableReadImpl]item);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtVariableReadImpl]newDelegate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> pop() [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.checkEmptyStack();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> newDelegate = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList([CtFieldReadImpl][CtThisAccessImpl]this.delegate);
        [CtInvocationImpl][CtVariableReadImpl]newDelegate.remove([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() - [CtLiteralImpl]1);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtVariableReadImpl]newDelegate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> pop([CtParameterImpl][CtTypeReferenceImpl]int count) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.checkNegativeCount([CtVariableReadImpl]count);
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.checkZeroCount([CtVariableReadImpl]count)) [CtBlockImpl]{
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }
        [CtInvocationImpl][CtThisAccessImpl]this.checkEmptyStack();
        [CtInvocationImpl][CtThisAccessImpl]this.checkSizeLessThanCount([CtVariableReadImpl]count);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> newDelegate = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.clone();
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]count > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newDelegate.remove([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() - [CtLiteralImpl]1);
            [CtUnaryOperatorImpl][CtVariableWriteImpl]count--;
        } 
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<>([CtVariableReadImpl]newDelegate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T peek() [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.checkEmptyStack();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.getLast();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkEmptyStack() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.isEmpty()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.EmptyStackException();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.list.ListIterable<[CtTypeParameterReferenceImpl]T> peek([CtParameterImpl][CtTypeReferenceImpl]int count) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.checkNegativeCount([CtVariableReadImpl]count);
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.checkZeroCount([CtVariableReadImpl]count)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList();
        }
        [CtInvocationImpl][CtThisAccessImpl]this.checkEmptyStack();
        [CtInvocationImpl][CtThisAccessImpl]this.checkSizeLessThanCount([CtVariableReadImpl]count);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.asLazy().take([CtVariableReadImpl]count));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean checkZeroCount([CtParameterImpl][CtTypeReferenceImpl]int count) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]count == [CtLiteralImpl]0;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkSizeLessThanCount([CtParameterImpl][CtTypeReferenceImpl]int count) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() < [CtVariableReadImpl]count) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Count must be less than size: Count = " + [CtVariableReadImpl]count) + [CtLiteralImpl]" Size = ") + [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkSizeLessThanOrEqualToIndex([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() <= [CtVariableReadImpl]index) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Count must be less than size: Count = " + [CtVariableReadImpl]index) + [CtLiteralImpl]" Size = ") + [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkNegativeCount([CtParameterImpl][CtTypeReferenceImpl]int count) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Count must be positive but was " + [CtVariableReadImpl]count);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T peekAt([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.checkNegativeCount([CtVariableReadImpl]index);
        [CtInvocationImpl][CtThisAccessImpl]this.checkEmptyStack();
        [CtInvocationImpl][CtThisAccessImpl]this.checkSizeLessThanOrEqualToIndex([CtVariableReadImpl]index);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.get([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() - [CtLiteralImpl]1) - [CtVariableReadImpl]index);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T getFirst() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.peek();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T getLast() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot call getLast() on " + [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T getOnly() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.getOnly();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean contains([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().contains([CtVariableReadImpl]object);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsAllIterable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]?> source) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().containsAllIterable([CtVariableReadImpl]source);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsAll([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]?> source) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().containsAll([CtVariableReadImpl]source);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsAllArguments([CtParameterImpl]java.lang.Object... elements) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().containsAllArguments([CtVariableReadImpl]elements);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> select([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().select([CtVariableReadImpl]predicate).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> selectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.select([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates.bind([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R select([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().select([CtVariableReadImpl]predicate, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R selectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().selectWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter, [CtVariableReadImpl]targetCollection);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> reject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().reject([CtVariableReadImpl]predicate).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R reject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().reject([CtVariableReadImpl]predicate, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> rejectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.reject([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates.bind([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R rejectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().rejectWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter, [CtVariableReadImpl]targetCollection);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.partition.stack.PartitionImmutableStack<[CtTypeParameterReferenceImpl]T> partition([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack<[CtTypeParameterReferenceImpl]T> partitionMutableStack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack<>();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().forEach([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack.PartitionProcedure<>([CtVariableReadImpl]predicate, [CtVariableReadImpl]partitionMutableStack));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]partitionMutableStack.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.partition.stack.PartitionImmutableStack<[CtTypeParameterReferenceImpl]T> partitionWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack<[CtTypeParameterReferenceImpl]T> partitionMutableStack = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack<>();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().forEach([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.partition.stack.PartitionArrayStack.PartitionPredicate2Procedure<>([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter, [CtVariableReadImpl]partitionMutableStack));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]partitionMutableStack.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]S> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]S> selectInstancesOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S> clazz) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().selectInstancesOf([CtVariableReadImpl]clazz).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]V> collect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collect([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableBooleanStack collectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.BooleanFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> booleanFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.BooleanArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectBoolean([CtVariableReadImpl]booleanFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableBooleanCollection> [CtTypeParameterReferenceImpl]R collectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.BooleanFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> booleanFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectBoolean([CtVariableReadImpl]booleanFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableBooleanCollection> [CtTypeParameterReferenceImpl]R flatCollectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.BooleanIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectBoolean([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableByteStack collectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ByteFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> byteFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.ByteArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectByte([CtVariableReadImpl]byteFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableByteCollection> [CtTypeParameterReferenceImpl]R collectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ByteFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> byteFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectByte([CtVariableReadImpl]byteFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableByteCollection> [CtTypeParameterReferenceImpl]R flatCollectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.ByteIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectByte([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableCharStack collectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.CharFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> charFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.CharArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectChar([CtVariableReadImpl]charFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableCharCollection> [CtTypeParameterReferenceImpl]R collectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.CharFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> charFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectChar([CtVariableReadImpl]charFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableCharCollection> [CtTypeParameterReferenceImpl]R flatCollectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.CharIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectChar([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableDoubleStack collectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.DoubleArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectDouble([CtVariableReadImpl]doubleFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableDoubleCollection> [CtTypeParameterReferenceImpl]R collectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectDouble([CtVariableReadImpl]doubleFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableDoubleCollection> [CtTypeParameterReferenceImpl]R flatCollectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.DoubleIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectDouble([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableFloatStack collectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.FloatArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectFloat([CtVariableReadImpl]floatFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableFloatCollection> [CtTypeParameterReferenceImpl]R collectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectFloat([CtVariableReadImpl]floatFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableFloatCollection> [CtTypeParameterReferenceImpl]R flatCollectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.FloatIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectFloat([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableIntStack collectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectInt([CtVariableReadImpl]intFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableIntCollection> [CtTypeParameterReferenceImpl]R collectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectInt([CtVariableReadImpl]intFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableIntCollection> [CtTypeParameterReferenceImpl]R flatCollectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.IntIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectInt([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableLongStack collectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.LongArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectLong([CtVariableReadImpl]longFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableLongCollection> [CtTypeParameterReferenceImpl]R collectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectLong([CtVariableReadImpl]longFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableLongCollection> [CtTypeParameterReferenceImpl]R flatCollectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.LongIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectLong([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.primitive.ImmutableShortStack collectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ShortFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> shortFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.mutable.primitive.ShortArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectShort([CtVariableReadImpl]shortFunction)).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableShortCollection> [CtTypeParameterReferenceImpl]R collectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ShortFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> shortFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectShort([CtVariableReadImpl]shortFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableShortCollection> [CtTypeParameterReferenceImpl]R flatCollectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.ShortIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollectShort([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collect([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]V> collectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectWith([CtVariableReadImpl]function, [CtVariableReadImpl]parameter).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectWith([CtVariableReadImpl]function, [CtVariableReadImpl]parameter, [CtVariableReadImpl]targetCollection);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]V> collectIf([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectIf([CtVariableReadImpl]predicate, [CtVariableReadImpl]function).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collectIf([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().collectIf([CtVariableReadImpl]predicate, [CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]V> flatCollect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollect([CtVariableReadImpl]function).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R flatCollect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().flatCollect([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T detect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detect([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeParameterReferenceImpl]T detectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detectWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> detectOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detectOptional([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> detectWithOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detectWithOptional([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T detectIfNone([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detectIfNone([CtVariableReadImpl]predicate, [CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeParameterReferenceImpl]T detectWithIfNone([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().detectWithIfNone([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter, [CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int count([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().count([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]int countWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().countWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean anySatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().anySatisfy([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean anySatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().anySatisfyWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean allSatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().allSatisfy([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean allSatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().allSatisfyWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean noneSatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().noneSatisfy([CtVariableReadImpl]predicate);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean noneSatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().noneSatisfyWith([CtVariableReadImpl]predicate, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]IV> [CtTypeParameterReferenceImpl]IV injectInto([CtParameterImpl][CtTypeParameterReferenceImpl]IV injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]IV, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]IV> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().injectInto([CtVariableReadImpl]injectedValue, [CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int injectInto([CtParameterImpl][CtTypeReferenceImpl]int injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intObjectToIntFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().injectInto([CtVariableReadImpl]injectedValue, [CtVariableReadImpl]intObjectToIntFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long injectInto([CtParameterImpl][CtTypeReferenceImpl]long injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longObjectToLongFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().injectInto([CtVariableReadImpl]injectedValue, [CtVariableReadImpl]longObjectToLongFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double injectInto([CtParameterImpl][CtTypeReferenceImpl]double injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleObjectToDoubleFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().injectInto([CtVariableReadImpl]injectedValue, [CtVariableReadImpl]doubleObjectToDoubleFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]float injectInto([CtParameterImpl][CtTypeReferenceImpl]float injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatObjectToFloatFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().injectInto([CtVariableReadImpl]injectedValue, [CtVariableReadImpl]floatObjectToFloatFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R into([CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().into([CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toList() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toList();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedList() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedList();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedList([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedList([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedListBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedListBy([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.set.MutableSet<[CtTypeParameterReferenceImpl]T> toSet() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSet();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSet() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedSet();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSet([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedSet([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSetBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedSetBy([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBag<[CtTypeParameterReferenceImpl]T> toBag() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toBag();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBag() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedBag();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBag([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedBag([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBagBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedBagBy([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toMap([CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV>> [CtTypeParameterReferenceImpl]R toMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toMap([CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedMap([CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMap([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]NK> comparator, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedMap([CtVariableReadImpl]comparator, [CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]KK extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]KK>, [CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMapBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]KK> sortBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toSortedMapBy([CtVariableReadImpl]sortBy, [CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.bimap.MutableBiMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toBiMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toBiMap([CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.LazyIterable<[CtTypeParameterReferenceImpl]T> asLazy() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.utility.LazyIterate.adapt([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]java.lang.Object[] toArray() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toArray();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtArrayTypeReferenceImpl]T[] toArray([CtParameterImpl][CtArrayTypeReferenceImpl]T[] a) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toArray([CtVariableReadImpl]a);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T min([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().min([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T max([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().max([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T min() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().min();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]T max() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().max();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]T minBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().toList().minBy([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]T maxBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().maxBy([CtVariableReadImpl]function);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long sumOfInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumOfInt([CtVariableReadImpl]intFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double sumOfFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumOfFloat([CtVariableReadImpl]floatFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long sumOfLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumOfLong([CtVariableReadImpl]longFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]double sumOfDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumOfDouble([CtVariableReadImpl]doubleFunction);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ImmutableObjectLongMap<[CtTypeParameterReferenceImpl]V> sumByInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectLongMap<[CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumByInt([CtVariableReadImpl]groupBy, [CtVariableReadImpl]function);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]map.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap<[CtTypeParameterReferenceImpl]V> sumByFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectDoubleMap<[CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumByFloat([CtVariableReadImpl]groupBy, [CtVariableReadImpl]function);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]map.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ImmutableObjectLongMap<[CtTypeParameterReferenceImpl]V> sumByLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectLongMap<[CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumByLong([CtVariableReadImpl]groupBy, [CtVariableReadImpl]function);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]map.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap<[CtTypeParameterReferenceImpl]V> sumByDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectDoubleMap<[CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().sumByDouble([CtVariableReadImpl]groupBy, [CtVariableReadImpl]function);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]map.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String makeString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().makeString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String makeString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().makeString([CtVariableReadImpl]separator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String makeString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String start, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String end) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().makeString([CtVariableReadImpl]start, [CtVariableReadImpl]separator, [CtVariableReadImpl]end);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().appendString([CtVariableReadImpl]appendable);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().appendString([CtVariableReadImpl]appendable, [CtVariableReadImpl]separator);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String start, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String end) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().appendString([CtVariableReadImpl]appendable, [CtVariableReadImpl]start, [CtVariableReadImpl]separator, [CtVariableReadImpl]end);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.list.ImmutableListMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.groupBy([CtVariableReadImpl]function, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.multimap.list.FastListMultimap.<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>newMultimap()).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.MutableMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().groupBy([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.list.ImmutableListMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.groupByEach([CtVariableReadImpl]function, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.multimap.list.FastListMultimap.newMultimap()).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.MutableMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().groupByEach([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.ImmutableMap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupByUniqueKey([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.groupByUniqueKey([CtVariableReadImpl]function, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>newMap([CtInvocationImpl][CtThisAccessImpl]this.size())).toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMapIterable<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupByUniqueKey([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().groupByUniqueKey([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]S> [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]S>> zip([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]S> that) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().zip([CtVariableReadImpl]that).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]S, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]S>>> [CtTypeParameterReferenceImpl]R zip([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]S> that, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().zip([CtVariableReadImpl]that, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.Integer>> zipWithIndex() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size() - [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.Interval indices = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.Interval.fromTo([CtLiteralImpl]0, [CtVariableReadImpl]maxIndex);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().zip([CtVariableReadImpl]indices).toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.Integer>>> [CtTypeParameterReferenceImpl]R zipWithIndex([CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().zipWithIndex([CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> toImmutable() [CtBlockImpl]{
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T>> chunk([CtParameterImpl][CtTypeReferenceImpl]int size) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().chunk([CtVariableReadImpl]size);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.ImmutableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> aggregateInPlaceBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> zeroValueFactory, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> mutatingAggregator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap();
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.block.procedure.MutatingAggregationProcedure<>([CtVariableReadImpl]map, [CtVariableReadImpl]groupBy, [CtVariableReadImpl]zeroValueFactory, [CtVariableReadImpl]mutatingAggregator));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]map.toImmutable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int size() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.size();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.isEmpty();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean notEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.notEmpty();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> tap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtVariableReadImpl]procedure);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void each([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.reverseForEach([CtVariableReadImpl]procedure);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void forEachWithIndex([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> objectIntProcedure) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().forEachWithIndex([CtVariableReadImpl]objectIntProcedure);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]void forEachWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> procedure, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().forEachWith([CtVariableReadImpl]procedure, [CtVariableReadImpl]parameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> takeWhile([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".takeWhile() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> dropWhile([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".dropWhile() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.partition.stack.PartitionImmutableStack<[CtTypeParameterReferenceImpl]T> partitionWhile([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".partitionWhile() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.collections.api.stack.ImmutableStack<[CtTypeParameterReferenceImpl]T> distinct() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".distinct() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int indexOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".indexOf() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]S> [CtTypeReferenceImpl]boolean corresponds([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.ordered.OrderedIterable<[CtTypeParameterReferenceImpl]S> other, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]S> predicate) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".corresponds() not implemented yet");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasSameElements([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.ordered.OrderedIterable<[CtTypeParameterReferenceImpl]T> other) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".hasSameElements() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void forEach([CtParameterImpl][CtTypeReferenceImpl]int startIndex, [CtParameterImpl][CtTypeReferenceImpl]int endIndex, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".forEach() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void forEachWithIndex([CtParameterImpl][CtTypeReferenceImpl]int fromIndex, [CtParameterImpl][CtTypeReferenceImpl]int toIndex, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> objectIntProcedure) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".forEachWithIndex() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int detectIndex([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName() + [CtLiteralImpl]".detectIndex() not implemented yet");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Iterator<[CtTypeParameterReferenceImpl]T> iterator() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().iterator();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.collections.api.stack.StackIterable<?>)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.stack.StackIterable<[CtWildcardReferenceImpl]?> that = [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.stack.StackIterable<[CtWildcardReferenceImpl]?>) (o));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]that instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<?>) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.equals([CtFieldReadImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack<[CtWildcardReferenceImpl]?>) (that)).delegate);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeParameterReferenceImpl]T> thisIterator = [CtInvocationImpl][CtThisAccessImpl]this.iterator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtWildcardReferenceImpl]?> thatIterator = [CtInvocationImpl][CtVariableReadImpl]that.iterator();
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]thisIterator.hasNext() && [CtInvocationImpl][CtVariableReadImpl]thatIterator.hasNext()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]thisIterator.next(), [CtInvocationImpl][CtVariableReadImpl]thatIterator.next())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        } 
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]thisIterator.hasNext()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]thatIterator.hasNext());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.delegate.asReversed().makeString([CtLiteralImpl]"[", [CtLiteralImpl]", ", [CtLiteralImpl]"]");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int hashCode = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeParameterReferenceImpl]T each : [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]hashCode = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]hashCode) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]each == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]each.hashCode());
        }
        [CtReturnImpl]return [CtVariableReadImpl]hashCode;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object writeReplace() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.ImmutableStackSerializationProxy<>([CtThisAccessImpl]this);
    }

    [CtClassImpl]private static class ImmutableStackSerializationProxy<[CtTypeParameterImpl]T> implements [CtTypeReferenceImpl]java.io.Externalizable {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.collections.api.stack.StackIterable<[CtTypeParameterReferenceImpl]T> stack;

        [CtConstructorImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"UnusedDeclaration")
        public ImmutableStackSerializationProxy() [CtBlockImpl]{
            [CtCommentImpl]// Empty constructor for Externalizable class
        }

        [CtConstructorImpl]protected ImmutableStackSerializationProxy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.stack.StackIterable<[CtTypeParameterReferenceImpl]T> stack) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stack = [CtVariableReadImpl]stack;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void writeExternal([CtParameterImpl][CtTypeReferenceImpl]java.io.ObjectOutput out) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.writeInt([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.stack.size());
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.stack.forEach([CtNewClassImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.block.procedure.checked.CheckedProcedure<[CtTypeParameterReferenceImpl]T>()[CtClassImpl] {
                    [CtMethodImpl]public [CtTypeReferenceImpl]void safeValue([CtParameterImpl][CtTypeParameterReferenceImpl]T object) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]out.writeObject([CtVariableReadImpl]object);
                    }
                });
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getCause() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.io.IOException) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
                }
                [CtThrowImpl]throw [CtVariableReadImpl]e;
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void readExternal([CtParameterImpl][CtTypeReferenceImpl]java.io.ObjectInput in) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.ClassNotFoundException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<[CtTypeParameterReferenceImpl]T> deserializedDelegate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.list.mutable.FastList<>([CtVariableReadImpl]size);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]deserializedDelegate.add([CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtVariableReadImpl]in.readObject())));
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stack = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.stack.immutable.ImmutableArrayStack.newStackFromTopToBottom([CtVariableReadImpl]deserializedDelegate);
        }

        [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.Object readResolve() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.stack;
        }
    }
}
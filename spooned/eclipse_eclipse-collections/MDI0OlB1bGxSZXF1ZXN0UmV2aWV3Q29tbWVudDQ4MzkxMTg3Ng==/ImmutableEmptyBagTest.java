[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018 Goldman Sachs and others.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
and Eclipse Distribution License v. 1.0 which accompany this distribution.
The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
and the Eclipse Distribution License is available at
http://www.eclipse.org/org/documents/edl-v10.php.
 */
[CtPackageDeclarationImpl]package org.eclipse.collections.impl.bag.immutable;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function;
[CtUnresolvedImport]import org.eclipse.collections.api.block.predicate.Predicate;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.Bag;
[CtUnresolvedImport]import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
[CtUnresolvedImport]import org.eclipse.collections.impl.test.Verify;
[CtUnresolvedImport]import static org.eclipse.collections.impl.factory.Iterables.iBag;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.MutableBag;
[CtUnresolvedImport]import org.eclipse.collections.api.list.MutableList;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import org.eclipse.collections.impl.list.mutable.FastList;
[CtUnresolvedImport]import org.eclipse.collections.api.map.sorted.MutableSortedMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.bag.mutable.HashBag;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.factory.Functions;
[CtUnresolvedImport]import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
[CtUnresolvedImport]import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.ImmutableBag;
[CtUnresolvedImport]import org.eclipse.collections.api.tuple.Pair;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.factory.Comparators;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.factory.Predicates;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.factory.Predicates2;
[CtUnresolvedImport]import org.eclipse.collections.impl.map.mutable.UnifiedMap;
[CtUnresolvedImport]import org.eclipse.collections.api.partition.bag.PartitionImmutableBag;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
[CtUnresolvedImport]import org.eclipse.collections.impl.factory.Bags;
[CtUnresolvedImport]import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
[CtUnresolvedImport]import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.eclipse.collections.impl.block.function.PassThruFunction0;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
[CtUnresolvedImport]import org.eclipse.collections.impl.list.primitive.IntInterval;
[CtUnresolvedImport]import org.eclipse.collections.impl.factory.Sets;
[CtImportImpl]import java.util.NoSuchElementException;
[CtUnresolvedImport]import org.eclipse.collections.api.set.ImmutableSet;
[CtUnresolvedImport]import org.junit.Assert;
[CtUnresolvedImport]import org.eclipse.collections.impl.set.mutable.UnifiedSet;
[CtClassImpl]public class ImmutableEmptyBagTest extends [CtTypeReferenceImpl]org.eclipse.collections.impl.bag.immutable.ImmutableBagTestCase {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtTypeReferenceImpl]java.lang.String> ERROR_THROWING_PREDICATE = [CtLambdaImpl]([CtParameterImpl] each) -> [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError();
    };

    [CtFieldImpl]public static final [CtTypeReferenceImpl]org.eclipse.collections.impl.block.factory.Predicates2<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtTypeReferenceImpl]java.lang.Integer>> ERROR_THROWING_PREDICATE_2 = [CtNewClassImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.block.factory.Predicates2<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtTypeReferenceImpl]java.lang.Integer>>()[CtClassImpl] {
        [CtMethodImpl]public [CtTypeReferenceImpl]boolean accept([CtParameterImpl][CtTypeReferenceImpl]java.lang.String argument1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeReferenceImpl]java.lang.Integer> argument2) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError();
        }
    };

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> newBag() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String>) (ImmutableEmptyBag.INSTANCE));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]int numKeys() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFactory() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertInstanceOf([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBag.class, [CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.of());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testAnySatisfyWithOccurrences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]bag.anySatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]bag.anySatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testAllSatisfyWithOccurrences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]bag.allSatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]bag.allSatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNoneSatisfyWithOccurrences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]bag.noneSatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]bag.noneSatisfyWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testDetectWithOccurrences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]bag.detectWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]bag.detectWithOccurrences([CtLambdaImpl]([CtParameterImpl] object,[CtParameterImpl] value) -> [CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void newWith() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> newBag = [CtInvocationImpl][CtVariableReadImpl]bag.newWith([CtLiteralImpl]"1");
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotEquals([CtVariableReadImpl]bag, [CtVariableReadImpl]newBag);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]newBag.size(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bag.size() + [CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> newBag2 = [CtInvocationImpl][CtVariableReadImpl]bag.newWith([CtLiteralImpl]"5");
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotEquals([CtVariableReadImpl]bag, [CtVariableReadImpl]newBag2);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]newBag2.size(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bag.size() + [CtLiteralImpl]1);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]newBag2.sizeDistinct());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void selectDuplicates() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.empty(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().selectDuplicates());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void select() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]strings.select([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates.lessThan([CtLiteralImpl]"0")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void reject() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]strings.reject([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates.greaterThan([CtLiteralImpl]"0")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 9.1.
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void collectWithOccurrences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.primitive.ObjectIntPair<[CtTypeReferenceImpl]java.lang.String>> actual = [CtInvocationImpl][CtVariableReadImpl]bag.collectWithOccurrences([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]PrimitiveTuples::pair, [CtInvocationImpl][CtTypeAccessImpl]Bags.mutable.empty());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.primitive.ObjectIntPair<[CtTypeReferenceImpl]java.lang.String>> expected = [CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.empty();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]expected, [CtVariableReadImpl]actual);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.primitive.ObjectIntPair<[CtTypeReferenceImpl]java.lang.String>> actual2 = [CtInvocationImpl][CtVariableReadImpl]bag.collectWithOccurrences([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]PrimitiveTuples::pair, [CtInvocationImpl][CtTypeAccessImpl]Sets.mutable.empty());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.set.ImmutableSet<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.primitive.ObjectIntPair<[CtTypeReferenceImpl]java.lang.String>> expected2 = [CtInvocationImpl][CtTypeAccessImpl]Sets.immutable.empty();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]expected2, [CtVariableReadImpl]actual2);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void partition() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.partition.bag.PartitionImmutableBag<[CtTypeReferenceImpl]java.lang.String> partition = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().partition([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates.lessThan([CtLiteralImpl]"0"));
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]partition.getSelected());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]partition.getRejected());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void partitionWith() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.partition.bag.PartitionImmutableBag<[CtTypeReferenceImpl]java.lang.String> partition = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().partitionWith([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates2.lessThan(), [CtLiteralImpl]"0");
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]partition.getSelected());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableEmpty([CtInvocationImpl][CtVariableReadImpl]partition.getRejected());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void selectInstancesOf() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.Number> numbers = [CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.of();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl]Iterables.iBag(), [CtInvocationImpl][CtVariableReadImpl]numbers.selectInstancesOf([CtFieldReadImpl]java.lang.Integer.class));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl]Iterables.iBag(), [CtInvocationImpl][CtVariableReadImpl]numbers.selectInstancesOf([CtFieldReadImpl]java.lang.Double.class));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl]Iterables.iBag(), [CtInvocationImpl][CtVariableReadImpl]numbers.selectInstancesOf([CtFieldReadImpl]java.lang.Number.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testToString() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.testToString();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"[]", [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSize() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertIterableSize([CtLiteralImpl]0, [CtInvocationImpl][CtThisAccessImpl]this.newBag());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void newWithout() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertSame([CtInvocationImpl][CtThisAccessImpl]this.newBag(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().newWithout([CtLiteralImpl]"1"));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void toStringOfItemToCount() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"{}", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.of().toStringOfItemToCount());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void detect() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().detect([CtExecutableReferenceExpressionImpl][CtLiteralImpl]"1"::equals));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void detectWith() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().detectWith([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Predicates2.greaterThan(), [CtLiteralImpl]"3"));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void detectWithIfNone() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"Not Found", [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().detectWithIfNone([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Object::equals, [CtLiteralImpl]"1", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.block.function.PassThruFunction0<>([CtLiteralImpl]"Not Found")));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void detectIfNone() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.detectIfNone();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"Not Found", [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().detectIfNone([CtExecutableReferenceExpressionImpl][CtLiteralImpl]"2"::equals, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.block.function.PassThruFunction0<>([CtLiteralImpl]"Not Found")));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void allSatisfy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]strings.allSatisfy([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void anySatisfy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]strings.anySatisfy([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void noneSatisfy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]strings.noneSatisfy([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void allSatisfyWith() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]strings.allSatisfyWith([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE_2, [CtFieldReadImpl]java.lang.Integer.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void anySatisfyWith() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]strings.anySatisfyWith([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE_2, [CtFieldReadImpl]java.lang.Integer.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void noneSatisfyWith() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> strings = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]strings.noneSatisfyWith([CtFieldReadImpl]org.eclipse.collections.impl.bag.immutable.ImmutableEmptyBagTest.ERROR_THROWING_PREDICATE_2, [CtFieldReadImpl]java.lang.Integer.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getFirst() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().getFirst());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getLast() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().getLast());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void getOnly() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertThrows([CtFieldReadImpl]java.lang.IllegalStateException.class, [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().getOnly());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void isEmpty() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]bag.isEmpty());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]bag.notEmpty());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void min() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().min([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::compareTo);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void max() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().max([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::compareTo);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void min_null_throws() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.min_null_throws();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void max_null_throws() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.max_null_throws();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void min_without_comparator() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().min();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void max_without_comparator() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().max();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void min_null_throws_without_comparator() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Not applicable for empty collections
        [CtSuperAccessImpl]super.min_null_throws_without_comparator();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void max_null_throws_without_comparator() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Not applicable for empty collections
        [CtSuperAccessImpl]super.max_null_throws_without_comparator();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void minBy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().minBy([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.util.NoSuchElementException.class)
    public [CtTypeReferenceImpl]void maxBy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().maxBy([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void zip() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> nulls = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.nCopies([CtInvocationImpl][CtVariableReadImpl]immutableBag.size(), [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> nullsPlusOne = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.nCopies([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]immutableBag.size() + [CtLiteralImpl]1, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> pairs = [CtInvocationImpl][CtVariableReadImpl]immutableBag.zip([CtVariableReadImpl]nulls);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]immutableBag, [CtInvocationImpl][CtVariableReadImpl]pairs.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.lang.String>) ([CtFieldReadImpl]Pair::getOne))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.mutable.HashBag.newBag([CtVariableReadImpl]nulls), [CtInvocationImpl][CtVariableReadImpl]pairs.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]java.lang.Object>, [CtTypeReferenceImpl]java.lang.Object>) ([CtFieldReadImpl]Pair::getTwo))));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> pairsPlusOne = [CtInvocationImpl][CtVariableReadImpl]immutableBag.zip([CtVariableReadImpl]nullsPlusOne);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]immutableBag, [CtInvocationImpl][CtVariableReadImpl]pairsPlusOne.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.lang.String>) ([CtFieldReadImpl]Pair::getOne))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.mutable.HashBag.newBag([CtVariableReadImpl]nulls), [CtInvocationImpl][CtVariableReadImpl]pairsPlusOne.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]java.lang.Object>, [CtTypeReferenceImpl]java.lang.Object>) ([CtFieldReadImpl]Pair::getTwo))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]immutableBag.zip([CtVariableReadImpl]nulls), [CtInvocationImpl][CtVariableReadImpl]immutableBag.zip([CtVariableReadImpl]nulls, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.mutable.HashBag.newBag()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void zipWithIndex() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.set.ImmutableSet<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>> pairs = [CtInvocationImpl][CtVariableReadImpl]immutableBag.zipWithIndex();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.set.mutable.UnifiedSet.<[CtTypeReferenceImpl]java.lang.String>newSet(), [CtInvocationImpl][CtVariableReadImpl]pairs.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeReferenceImpl]java.lang.String, [CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.lang.String>) ([CtFieldReadImpl]Pair::getOne))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.set.mutable.UnifiedSet.<[CtTypeReferenceImpl]java.lang.Integer>newSet(), [CtInvocationImpl][CtVariableReadImpl]pairs.collect([CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]java.lang.Integer>, [CtTypeReferenceImpl]java.lang.Integer>) ([CtFieldReadImpl]Pair::getTwo))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]immutableBag.zipWithIndex(), [CtInvocationImpl][CtVariableReadImpl]immutableBag.zipWithIndex([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.set.mutable.UnifiedSet.newSet()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void chunk() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtThisAccessImpl]this.newBag(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().chunk([CtLiteralImpl]2));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.lang.IllegalArgumentException.class)
    public [CtTypeReferenceImpl]void chunk_zero_throws() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().chunk([CtLiteralImpl]0);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void chunk_large_size() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtThisAccessImpl]this.newBag(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().chunk([CtLiteralImpl]10));
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertInstanceOf([CtFieldReadImpl]org.eclipse.collections.api.bag.ImmutableBag.class, [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().chunk([CtLiteralImpl]10));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedMap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> map = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().toSortedMap([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru(), [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertEmpty([CtVariableReadImpl]map);
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertInstanceOf([CtFieldReadImpl]org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap.class, [CtVariableReadImpl]map);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedMap_with_comparator() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> map = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().toSortedMap([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.reverseNaturalOrder(), [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru(), [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertEmpty([CtVariableReadImpl]map);
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertInstanceOf([CtFieldReadImpl]org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap.class, [CtVariableReadImpl]map);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.<[CtTypeReferenceImpl]java.lang.String>reverseNaturalOrder(), [CtInvocationImpl][CtVariableReadImpl]map.comparator());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedMapBy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> map = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().toSortedMapBy([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Integer::valueOf, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru(), [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Functions.getStringPassThru());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertEmpty([CtVariableReadImpl]map);
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertInstanceOf([CtFieldReadImpl]org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap.class, [CtVariableReadImpl]map);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void serialization() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertPostSerializedIdentity([CtVariableReadImpl]bag);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void collectBoolean() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag result = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().collectBoolean([CtExecutableReferenceExpressionImpl][CtLiteralImpl]"4"::equals);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.sizeDistinct());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.occurrencesOf([CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.occurrencesOf([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void collectBooleanWithTarget() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag result = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().collectBoolean([CtExecutableReferenceExpressionImpl][CtLiteralImpl]"4"::equals, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertSame([CtLiteralImpl]"Target sent as parameter not returned", [CtVariableReadImpl]target, [CtVariableReadImpl]result);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.sizeDistinct());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.occurrencesOf([CtLiteralImpl]true));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]result.occurrencesOf([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void collect_target() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeReferenceImpl]java.lang.Integer> targetCollection = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeReferenceImpl]java.lang.Integer> actual = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().collect([CtLambdaImpl]([CtParameterImpl] object) -> [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError();
        }, [CtVariableReadImpl]targetCollection);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]targetCollection, [CtVariableReadImpl]actual);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertSame([CtVariableReadImpl]targetCollection, [CtVariableReadImpl]actual);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void collectWith_target() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeReferenceImpl]java.lang.Integer> targetCollection = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.mutable.FastList.newList();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeReferenceImpl]java.lang.Integer> actual = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().collectWith([CtLambdaImpl]([CtParameterImpl] argument1,[CtParameterImpl] argument2) -> [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError();
        }, [CtLiteralImpl]1, [CtVariableReadImpl]targetCollection);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]targetCollection, [CtVariableReadImpl]actual);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertSame([CtVariableReadImpl]targetCollection, [CtVariableReadImpl]actual);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupByUniqueKey() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap().toImmutable(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().groupByUniqueKey([CtLambdaImpl]([CtParameterImpl] id) -> [CtVariableReadImpl]id));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupByUniqueKey_throws() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.groupByUniqueKey_throws();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap().toImmutable(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().groupByUniqueKey([CtLambdaImpl]([CtParameterImpl] id) -> [CtVariableReadImpl]id));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupByUniqueKey_target() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().groupByUniqueKey([CtLambdaImpl]([CtParameterImpl] id) -> [CtVariableReadImpl]id, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupByUniqueKey_target_throws() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.groupByUniqueKey_target_throws();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().groupByUniqueKey([CtLambdaImpl]([CtParameterImpl] id) -> [CtVariableReadImpl]id, [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.map.mutable.UnifiedMap.newMap()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void countByEach() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.empty(), [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().countByEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.primitive.IntInterval.oneTo([CtLiteralImpl]5).collect([CtLambdaImpl]([CtParameterImpl] i) -> [CtBinaryOperatorImpl][CtVariableReadImpl]each + [CtVariableReadImpl]i)));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void countByEach_target() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBag<[CtTypeReferenceImpl]java.lang.String> target = [CtInvocationImpl][CtTypeAccessImpl]Bags.mutable.empty();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]target, [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.newBag().countByEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.list.primitive.IntInterval.oneTo([CtLiteralImpl]5).collect([CtLambdaImpl]([CtParameterImpl] i) -> [CtBinaryOperatorImpl][CtVariableReadImpl]each + [CtVariableReadImpl]i), [CtVariableReadImpl]target));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedBag() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> sortedBag = [CtInvocationImpl][CtVariableReadImpl]immutableBag.toSortedBag();
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBag(), [CtVariableReadImpl]sortedBag);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> reverse = [CtInvocationImpl][CtVariableReadImpl]immutableBag.toSortedBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.reverseOrder());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.<[CtTypeReferenceImpl]java.lang.String>reverseOrder()), [CtVariableReadImpl]reverse);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag1 = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> sortedBag1 = [CtInvocationImpl][CtVariableReadImpl]immutableBag1.toSortedBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.reverseOrder());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBag(), [CtInvocationImpl][CtVariableReadImpl]sortedBag1.toSortedBag());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag2 = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> sortedBag2 = [CtInvocationImpl][CtVariableReadImpl]immutableBag2.toSortedBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.reverseOrder());
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.<[CtTypeReferenceImpl]java.lang.String>reverseOrder()), [CtVariableReadImpl]sortedBag2);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedBag_empty() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag = [CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.of();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> sortedBag = [CtInvocationImpl][CtVariableReadImpl]immutableBag.toSortedBag([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.reverseNaturalOrder());
        [CtInvocationImpl][CtVariableReadImpl]sortedBag.addOccurrences([CtLiteralImpl]"apple", [CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]sortedBag.addOccurrences([CtLiteralImpl]"orange", [CtLiteralImpl]2);
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBagWith([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.reverseNaturalOrder(), [CtLiteralImpl]"orange", [CtLiteralImpl]"orange", [CtLiteralImpl]"apple", [CtLiteralImpl]"apple", [CtLiteralImpl]"apple"), [CtVariableReadImpl]sortedBag);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedBagBy_empty() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.Integer> immutableBag = [CtInvocationImpl][CtTypeAccessImpl]Bags.immutable.of();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Integer> function = [CtLambdaImpl]([CtParameterImpl] object) -> [CtBinaryOperatorImpl][CtVariableReadImpl]object * [CtUnaryOperatorImpl](-[CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.Integer> sortedBag = [CtInvocationImpl][CtVariableReadImpl]immutableBag.toSortedBagBy([CtVariableReadImpl]function);
        [CtInvocationImpl][CtVariableReadImpl]sortedBag.addOccurrences([CtLiteralImpl]1, [CtLiteralImpl]3);
        [CtInvocationImpl][CtVariableReadImpl]sortedBag.addOccurrences([CtLiteralImpl]10, [CtLiteralImpl]2);
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBagWith([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.byFunction([CtVariableReadImpl]function), [CtLiteralImpl]10, [CtLiteralImpl]10, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1), [CtVariableReadImpl]sortedBag);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void toSortedBagBy() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> immutableBag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeReferenceImpl]java.lang.String> sortedBag = [CtInvocationImpl][CtVariableReadImpl]immutableBag.toSortedBagBy([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag<[CtTypeReferenceImpl]java.lang.Object> expectedBag = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.bag.sorted.mutable.TreeBag.newBag([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.block.factory.Comparators.byFunction([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf));
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.collections.impl.test.Verify.assertSortedBagsEqual([CtVariableReadImpl]expectedBag, [CtVariableReadImpl]sortedBag);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void selectUnique() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.selectUnique();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.bag.ImmutableBag<[CtTypeReferenceImpl]java.lang.String> bag = [CtInvocationImpl][CtThisAccessImpl]this.newBag();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.set.ImmutableSet<[CtTypeReferenceImpl]java.lang.String> expected = [CtInvocationImpl][CtTypeAccessImpl]Sets.immutable.empty();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.set.ImmutableSet<[CtTypeReferenceImpl]java.lang.String> actual = [CtInvocationImpl][CtVariableReadImpl]bag.selectUnique();
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]expected, [CtVariableReadImpl]actual);
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018 Goldman Sachs and others.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
and Eclipse Distribution License v. 1.0 which accompany this distribution.
The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
and the Eclipse Distribution License is available at
http://www.eclipse.org/org/documents/edl-v10.php.
 */
[CtPackageDeclarationImpl]package org.eclipse.collections.api;
[CtUnresolvedImport]import org.eclipse.collections.api.map.MutableMapIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.CharFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.block.predicate.Predicate;
[CtUnresolvedImport]import org.eclipse.collections.api.multimap.Multimap;
[CtUnresolvedImport]import org.eclipse.collections.api.block.predicate.Predicate2;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.MutableBag;
[CtUnresolvedImport]import org.eclipse.collections.api.list.MutableList;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.factory.Maps;
[CtUnresolvedImport]import org.eclipse.collections.api.map.sorted.MutableSortedMap;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.set.MutableSet;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.multimap.MutableMultimap;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.ShortFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
[CtImportImpl]import java.util.function.BinaryOperator;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.LongFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
[CtUnresolvedImport]import org.eclipse.collections.api.factory.Bags;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
[CtUnresolvedImport]import org.eclipse.collections.api.set.sorted.MutableSortedSet;
[CtImportImpl]import java.util.LongSummaryStatistics;
[CtUnresolvedImport]import org.eclipse.collections.api.block.procedure.Procedure2;
[CtUnresolvedImport]import org.eclipse.collections.api.map.primitive.ObjectLongMap;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.MutableBagIterable;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.FloatFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.ordered.OrderedIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function;
[CtUnresolvedImport]import org.eclipse.collections.api.bag.Bag;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.bimap.MutableBiMap;
[CtUnresolvedImport]import org.eclipse.collections.api.block.procedure.Procedure;
[CtImportImpl]import java.util.IntSummaryStatistics;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function0;
[CtUnresolvedImport]import org.eclipse.collections.api.map.MutableMap;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.Function2;
[CtImportImpl]import java.util.function.Supplier;
[CtImportImpl]import java.util.stream.Collector;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction;
[CtUnresolvedImport]import org.eclipse.collections.api.tuple.Pair;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.IntFunction;
[CtImportImpl]import java.util.DoubleSummaryStatistics;
[CtUnresolvedImport]import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
[CtImportImpl]import java.util.NoSuchElementException;
[CtUnresolvedImport]import org.eclipse.collections.api.map.MapIterable;
[CtUnresolvedImport]import org.eclipse.collections.api.partition.PartitionIterable;
[CtImportImpl]import java.util.function.BiConsumer;
[CtUnresolvedImport]import org.eclipse.collections.api.block.function.primitive.ByteFunction;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * RichIterable is an interface which extends the InternalIterable interface with several internal iterator methods, from
 * the Smalltalk Collection protocol. These include select, reject, detect, collect, injectInto, anySatisfy,
 * allSatisfy. The API also includes converter methods to convert a RichIterable to a List (toList), to a sorted
 * List (toSortedList), to a Set (toSet), and to a Map (toMap).
 *
 * @since 1.0
 */
public interface RichIterable<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]org.eclipse.collections.api.InternalIterable<[CtTypeParameterReferenceImpl]T> {
    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    default [CtTypeReferenceImpl]void forEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtVariableReadImpl]procedure);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the number of items in this iterable.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]int size();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if this iterable has zero items.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean isEmpty();

    [CtMethodImpl][CtJavaDocImpl]/**
     * The English equivalent of !this.isEmpty()
     *
     * @since 1.0
     */
    default [CtTypeReferenceImpl]boolean notEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtThisAccessImpl]this.isEmpty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns any element of an iterable.
     *
     * @return an element of an iterable.
     * @since 10.0
     */
    default [CtTypeParameterReferenceImpl]T getAny() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.getFirst();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element of an iterable. In the case of a List it is the element at the first index. In the
     * case of any other Collection, it is the first element that would be returned during an iteration. If the
     * iterable is empty, null is returned. If null is a valid element of the container, then a developer would need to
     * check to see if the iterable is empty to validate that a null result was not due to the container being empty.
     * <p>
     * The order of Sets are not guaranteed (except for TreeSets and other Ordered Set implementations), so if you use
     * this method, the first element could be any element from the Set.
     *
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#getFirst()} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeParameterReferenceImpl]T getFirst();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the last element of an iterable. In the case of a List it is the element at the last index. In the case
     * of any other Collection, it is the last element that would be returned during an iteration. If the iterable is
     * empty, null is returned. If null is a valid element of the container, then a developer would need to check to
     * see if the iterable is empty to validate that a null result was not due to the container being empty.
     * <p>
     * The order of Sets are not guaranteed (except for TreeSets and other Ordered Set implementations), so if you use
     * this method, the last element could be any element from the Set.
     *
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#getLast()} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeParameterReferenceImpl]T getLast();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the element if the iterable has exactly one element. Otherwise, throw {@link IllegalStateException}.
     *
     * @return an element of an iterable.
     * @throws IllegalStateException
     * 		if iterable is empty or has multiple elements.
     * @since 8.0
     */
    default [CtTypeParameterReferenceImpl]T getOnly() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtThisAccessImpl]this.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.getFirst();
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Size must be 1 but was " + [CtInvocationImpl][CtThisAccessImpl]this.size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the iterable has an element which responds true to element.equals(object).
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean contains([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if all elements in source are contained in this collection.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean containsAllIterable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]?> source);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if all elements in source are contained in this collection.
     *
     * @see Collection#containsAll(Collection)
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean containsAll([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtWildcardReferenceImpl]?> source);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if all elements in the specified var arg array are contained in this collection.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean containsAllArguments([CtParameterImpl]java.lang.Object... elements);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Executes the Procedure for each element in the iterable and returns {@code this}.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Person&gt; tapped =
     *     people.<b>tap</b>(person -&gt; LOGGER.info(person.getName()));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * RichIterable&lt;Person&gt; tapped =
     *     people.<b>tap</b>(new Procedure&lt;Person&gt;()
     *     {
     *         public void value(Person person)
     *         {
     *             LOGGER.info(person.getName());
     *         }
     *     });
     * </pre>
     *
     * @see #each(Procedure)
     * @see #forEach(Procedure)
     * @since 6.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T> tap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure);

    [CtMethodImpl][CtJavaDocImpl]/**
     * The procedure is executed for each element in the iterable.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * people.each(person -&gt; LOGGER.info(person.getName()));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * people.each(new Procedure&lt;Person&gt;()
     * {
     *     public void value(Person person)
     *     {
     *         LOGGER.info(person.getName());
     *     }
     * });
     * </pre>
     * This method is a variant of {@link InternalIterable#forEach(Procedure)}
     * that has a signature conflict with {@link Iterable#forEach(java.util.function.Consumer)}.
     *
     * @see InternalIterable#forEach(Procedure)
     * @see Iterable#forEach(java.util.function.Consumer)
     * @since 6.0
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"UnnecessaryFullyQualifiedName")
    [CtTypeReferenceImpl]void each([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> procedure);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns all elements of the source collection that return true when evaluating the predicate. This method is also
     * commonly called filter.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Person&gt; selected =
     *     people.<b>select</b>(person -&gt; person.getAddress().getCity().equals("London"));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * RichIterable&lt;Person&gt; selected =
     *     people.<b>select</b>(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.getAddress().getCity().equals("London");
     *         }
     *     });
     * </pre>
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T> select([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as the select method with one parameter but uses the specified target collection for the results.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableList&lt;Person&gt; selected =
     *     people.select(person -&gt; person.person.getLastName().equals("Smith"), Lists.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;Person&gt; selected =
     *     people.select(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.person.getLastName().equals("Smith");
     *         }
     *     }, Lists.mutable.empty());
     * </pre>
     * <p>
     *
     * @param predicate
     * 		a {@link Predicate} to use as the select criteria
     * @param target
     * 		the Collection to append to for all elements in this {@code RichIterable} that meet select criteria {@code predicate}
     * @return {@code target}, which contains appended elements as a result of the select criteria
     * @see #select(Predicate)
     * @since 1.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R select([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar to {@link #select(Predicate)}, except with an evaluation parameter for the second generic argument in {@link Predicate2}.
     * <p>
     * E.g. return a {@link Collection} of Person elements where the person has an age <b>greater than or equal to</b> 18 years
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Person&gt; selected =
     *     people.selectWith((Person person, Integer age) -&gt; person.getAge()&gt;= age, Integer.valueOf(18));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * RichIterable&lt;Person&gt; selected =
     *     people.selectWith(new Predicate2&lt;Person, Integer&gt;()
     *     {
     *         public boolean accept(Person person, Integer age)
     *         {
     *             return person.getAge()&gt;= age;
     *         }
     *     }, Integer.valueOf(18));
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate2} to use as the select criteria
     * @param parameter
     * 		a parameter to pass in for evaluation of the second argument {@code P} in {@code predicate}
     * @see #select(Predicate)
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T> selectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar to {@link #select(Predicate, Collection)}, except with an evaluation parameter for the second generic argument in {@link Predicate2}.
     * <p>
     * E.g. return a {@link Collection} of Person elements where the person has an age <b>greater than or equal to</b> 18 years
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableList&lt;Person&gt; selected =
     *     people.selectWith((Person person, Integer age) -&gt; person.getAge()&gt;= age, Integer.valueOf(18), Lists.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;Person&gt; selected =
     *     people.selectWith(new Predicate2&lt;Person, Integer&gt;()
     *     {
     *         public boolean accept(Person person, Integer age)
     *         {
     *             return person.getAge()&gt;= age;
     *         }
     *     }, Integer.valueOf(18), Lists.mutable.empty());
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate2} to use as the select criteria
     * @param parameter
     * 		a parameter to pass in for evaluation of the second argument {@code P} in {@code predicate}
     * @param targetCollection
     * 		the Collection to append to for all elements in this {@code RichIterable} that meet select criteria {@code predicate}
     * @return {@code targetCollection}, which contains appended elements as a result of the select criteria
     * @see #select(Predicate)
     * @see #select(Predicate, Collection)
     * @since 1.0
     */
    <[CtTypeParameterImpl]P, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R selectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns all elements of the source collection that return false when evaluating of the predicate. This method is also
     * sometimes called filterNot and is the equivalent of calling iterable.select(Predicates.not(predicate)).
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Person&gt; rejected =
     *     people.reject(person -&gt; person.person.getLastName().equals("Smith"));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * RichIterable&lt;Person&gt; rejected =
     *     people.reject(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.person.getLastName().equals("Smith");
     *         }
     *     });
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate} to use as the reject criteria
     * @return a RichIterable that contains elements that cause {@link Predicate#accept(Object)} method to evaluate to false
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T> reject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar to {@link #reject(Predicate)}, except with an evaluation parameter for the second generic argument in {@link Predicate2}.
     * <p>
     * E.g. return a {@link Collection} of Person elements where the person has an age <b>greater than or equal to</b> 18 years
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Person&gt; rejected =
     *     people.rejectWith((Person person, Integer age) -&gt; person.getAge() &lt; age, Integer.valueOf(18));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;Person&gt; rejected =
     *     people.rejectWith(new Predicate2&lt;Person, Integer&gt;()
     *     {
     *         public boolean accept(Person person, Integer age)
     *         {
     *             return person.getAge() &lt; age;
     *         }
     *     }, Integer.valueOf(18));
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate2} to use as the select criteria
     * @param parameter
     * 		a parameter to pass in for evaluation of the second argument {@code P} in {@code predicate}
     * @see #select(Predicate)
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T> rejectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as the reject method with one parameter but uses the specified target collection for the results.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableList&lt;Person&gt; rejected =
     *     people.reject(person -&gt; person.person.getLastName().equals("Smith"), Lists.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;Person&gt; rejected =
     *     people.reject(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.person.getLastName().equals("Smith");
     *         }
     *     }, Lists.mutable.empty());
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate} to use as the reject criteria
     * @param target
     * 		the Collection to append to for all elements in this {@code RichIterable} that cause {@code Predicate#accept(Object)} method to evaluate to false
     * @return {@code target}, which contains appended elements as a result of the reject criteria
     * @since 1.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R reject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar to {@link #reject(Predicate, Collection)}, except with an evaluation parameter for the second generic argument in {@link Predicate2}.
     * <p>
     * E.g. return a {@link Collection} of Person elements where the person has an age <b>greater than or equal to</b> 18 years
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableList&lt;Person&gt; rejected =
     *     people.rejectWith((Person person, Integer age) -&gt; person.getAge() &lt; age, Integer.valueOf(18), Lists.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;Person&gt; rejected =
     *     people.rejectWith(new Predicate2&lt;Person, Integer&gt;()
     *     {
     *         public boolean accept(Person person, Integer age)
     *         {
     *             return person.getAge() &lt; age;
     *         }
     *     }, Integer.valueOf(18), Lists.mutable.empty());
     * </pre>
     *
     * @param predicate
     * 		a {@link Predicate2} to use as the reject criteria
     * @param parameter
     * 		a parameter to pass in for evaluation of the second argument {@code P} in {@code predicate}
     * @param targetCollection
     * 		the Collection to append to for all elements in this {@code RichIterable} that cause {@code Predicate#accept(Object)} method to evaluate to false
     * @return {@code targetCollection}, which contains appended elements as a result of the reject criteria
     * @see #reject(Predicate)
     * @see #reject(Predicate, Collection)
     * @since 1.0
     */
    <[CtTypeParameterImpl]P, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R rejectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Filters a collection into a PartitionedIterable based on the evaluation of the predicate.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * PartitionIterable&lt;Person&gt; newYorkersAndNonNewYorkers =
     *     people.<b>partition</b>(person -&gt; person.getAddress().getState().getName().equals("New York"));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * PartitionIterable&lt;Person&gt; newYorkersAndNonNewYorkers =
     *     people.<b>partition</b>(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.getAddress().getState().getName().equals("New York");
     *         }
     *     });
     * </pre>
     *
     * @since 1.0.
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.partition.PartitionIterable<[CtTypeParameterReferenceImpl]T> partition([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Filters a collection into a PartitionIterable based on the evaluation of the predicate.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * PartitionIterable&lt;Person&gt; newYorkersAndNonNewYorkers =
     *     people.<b>partitionWith</b>((Person person, String state) -&gt; person.getAddress().getState().getName().equals(state), "New York");
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * PartitionIterable&lt;Person&gt; newYorkersAndNonNewYorkers =
     *     people.<b>partitionWith</b>(new Predicate2&lt;Person, String&gt;()
     *     {
     *         public boolean accept(Person person, String state)
     *         {
     *             return person.getAddress().getState().getName().equals(state);
     *         }
     *     }, "New York");
     * </pre>
     *
     * @since 5.0.
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.partition.PartitionIterable<[CtTypeParameterReferenceImpl]T> partitionWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns all elements of the source collection that are instances of the Class {@code clazz}.
     *
     * <pre>
     * RichIterable&lt;Integer&gt; integers =
     *     List.mutable.with(new Integer(0), new Long(0L), new Double(0.0)).selectInstancesOf(Integer.class);
     * </pre>
     *
     * @since 2.0
     */
    <[CtTypeParameterImpl]S> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]S> selectInstancesOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]S> clazz);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new collection with the results of applying the specified function on each element of the source
     * collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;String&gt; names =
     *     people.collect(person -&gt; person.getFirstName() + " " + person.getLastName());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * RichIterable&lt;String&gt; names =
     *     people.collect(new Function&lt;Person, String&gt;()
     *     {
     *         public String valueOf(Person person)
     *         {
     *             return person.getFirstName() + " " + person.getLastName();
     *         }
     *     });
     * </pre>
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]V> collect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collect(Function)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableList&lt;String&gt; names =
     *     people.collect(person -&gt; person.getFirstName() + " " + person.getLastName(), Lists.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * MutableList&lt;String&gt; names =
     *     people.collect(new Function&lt;Person, String&gt;()
     *     {
     *         public String valueOf(Person person)
     *         {
     *             return person.getFirstName() + " " + person.getLastName();
     *         }
     *     }, Lists.mutable.empty());
     * </pre>
     *
     * @param function
     * 		a {@link Function} to use as the collect transformation function
     * @param target
     * 		the Collection to append to for all elements in this {@code RichIterable} that meet select criteria {@code function}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @see #collect(Function)
     * @since 1.0
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code boolean} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * BooleanIterable licenses =
     *     people.collectBoolean(person -&gt; person.hasDrivingLicense());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * BooleanIterable licenses =
     *     people.collectBoolean(new BooleanFunction&lt;Person&gt;()
     *     {
     *         public boolean booleanValueOf(Person person)
     *         {
     *             return person.hasDrivingLicense();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.BooleanIterable collectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.BooleanFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> booleanFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectBoolean(BooleanFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * BooleanArrayList licenses =
     *     people.collectBoolean(person -&gt; person.hasDrivingLicense(), new BooleanArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * BooleanArrayList licenses =
     *     people.collectBoolean(new BooleanFunction&lt;Person&gt;()
     *     {
     *         public boolean booleanValueOf(Person person)
     *         {
     *             return person.hasDrivingLicense();
     *         }
     *     }, new BooleanArrayList());
     * </pre>
     *
     * @param booleanFunction
     * 		a {@link BooleanFunction} to use as the collect transformation function
     * @param target
     * 		the MutableBooleanCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableBooleanCollection> [CtTypeParameterReferenceImpl]R collectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.BooleanFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> booleanFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code byte} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * ByteIterable bytes =
     *     people.collectByte(person -&gt; person.getCode());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * ByteIterable bytes =
     *     people.collectByte(new ByteFunction&lt;Person&gt;()
     *     {
     *         public byte byteValueOf(Person person)
     *         {
     *             return person.getCode();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.ByteIterable collectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ByteFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> byteFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectByte(ByteFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * ByteArrayList bytes =
     *     people.collectByte(person -&gt; person.getCode(), new ByteArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * ByteArrayList bytes =
     *     people.collectByte(new ByteFunction&lt;Person&gt;()
     *     {
     *         public byte byteValueOf(Person person)
     *         {
     *             return person.getCode();
     *         }
     *     }, new ByteArrayList());
     * </pre>
     *
     * @param byteFunction
     * 		a {@link ByteFunction} to use as the collect transformation function
     * @param target
     * 		the MutableByteCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableByteCollection> [CtTypeParameterReferenceImpl]R collectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ByteFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> byteFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code char} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * CharIterable chars =
     *     people.collectChar(person -&gt; person.getMiddleInitial());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * CharIterable chars =
     *     people.collectChar(new CharFunction&lt;Person&gt;()
     *     {
     *         public char charValueOf(Person person)
     *         {
     *             return person.getMiddleInitial();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.CharIterable collectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.CharFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> charFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectChar(CharFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * CharArrayList chars =
     *     people.collectChar(person -&gt; person.getMiddleInitial(), new CharArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * CharArrayList chars =
     *     people.collectChar(new CharFunction&lt;Person&gt;()
     *     {
     *         public char charValueOf(Person person)
     *         {
     *             return person.getMiddleInitial();
     *         }
     *     }, new CharArrayList());
     * </pre>
     *
     * @param charFunction
     * 		a {@link CharFunction} to use as the collect transformation function
     * @param target
     * 		the MutableCharCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableCharCollection> [CtTypeParameterReferenceImpl]R collectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.CharFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> charFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code double} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * DoubleIterable doubles =
     *     people.collectDouble(person -&gt; person.getMilesFromNorthPole());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * DoubleIterable doubles =
     *     people.collectDouble(new DoubleFunction&lt;Person&gt;()
     *     {
     *         public double doubleValueOf(Person person)
     *         {
     *             return person.getMilesFromNorthPole();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.DoubleIterable collectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectDouble(DoubleFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * DoubleArrayList doubles =
     *     people.collectDouble(person -&gt; person.getMilesFromNorthPole(), new DoubleArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * DoubleArrayList doubles =
     *     people.collectDouble(new DoubleFunction&lt;Person&gt;()
     *     {
     *         public double doubleValueOf(Person person)
     *         {
     *             return person.getMilesFromNorthPole();
     *         }
     *     }, new DoubleArrayList());
     * </pre>
     *
     * @param doubleFunction
     * 		a {@link DoubleFunction} to use as the collect transformation function
     * @param target
     * 		the MutableDoubleCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableDoubleCollection> [CtTypeParameterReferenceImpl]R collectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> doubleFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code float} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * FloatIterable floats =
     *     people.collectFloat(person -&gt; person.getHeightInInches());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * FloatIterable floats =
     *     people.collectFloat(new FloatFunction&lt;Person&gt;()
     *     {
     *         public float floatValueOf(Person person)
     *         {
     *             return person.getHeightInInches();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.FloatIterable collectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectFloat(FloatFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * FloatArrayList floats =
     *     people.collectFloat(person -&gt; person.getHeightInInches(), new FloatArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * FloatArrayList floats =
     *     people.collectFloat(new FloatFunction&lt;Person&gt;()
     *     {
     *         public float floatValueOf(Person person)
     *         {
     *             return person.getHeightInInches();
     *         }
     *     }, new FloatArrayList());
     * </pre>
     *
     * @param floatFunction
     * 		a {@link FloatFunction} to use as the collect transformation function
     * @param target
     * 		the MutableFloatCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableFloatCollection> [CtTypeParameterReferenceImpl]R collectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> floatFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code int} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * IntIterable ints =
     *     people.collectInt(person -&gt; person.getAge());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * IntIterable ints =
     *     people.collectInt(new IntFunction&lt;Person&gt;()
     *     {
     *         public int intValueOf(Person person)
     *         {
     *             return person.getAge();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.IntIterable collectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectInt(IntFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * IntArrayList ints =
     *     people.collectInt(person -&gt; person.getAge(), new IntArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * IntArrayList ints =
     *     people.collectInt(new IntFunction&lt;Person&gt;()
     *     {
     *         public int intValueOf(Person person)
     *         {
     *             return person.getAge();
     *         }
     *     }, new IntArrayList());
     * </pre>
     *
     * @param intFunction
     * 		a {@link IntFunction} to use as the collect transformation function
     * @param target
     * 		the MutableIntCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableIntCollection> [CtTypeParameterReferenceImpl]R collectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> intFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code long} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * LongIterable longs =
     *     people.collectLong(person -&gt; person.getGuid());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * LongIterable longs =
     *     people.collectLong(new LongFunction&lt;Person&gt;()
     *     {
     *         public long longValueOf(Person person)
     *         {
     *             return person.getGuid();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.LongIterable collectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectLong(LongFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * LongArrayList longs =
     *     people.collectLong(person -&gt; person.getGuid(), new LongArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * LongArrayList longs =
     *     people.collectLong(new LongFunction&lt;Person&gt;()
     *     {
     *         public long longValueOf(Person person)
     *         {
     *             return person.getGuid();
     *         }
     *     }, new LongArrayList());
     * </pre>
     *
     * @param longFunction
     * 		a {@link LongFunction} to use as the collect transformation function
     * @param target
     * 		the MutableLongCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableLongCollection> [CtTypeParameterReferenceImpl]R collectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> longFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new primitive {@code short} iterable with the results of applying the specified function on each element
     * of the source collection. This method is also commonly called transform or map.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * ShortIterable shorts =
     *     people.collectShort(person -&gt; person.getNumberOfJunkMailItemsReceivedPerMonth());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * ShortIterable shorts =
     *     people.collectShort(new ShortFunction&lt;Person&gt;()
     *     {
     *         public short shortValueOf(Person person)
     *         {
     *             return person.getNumberOfJunkMailItemsReceivedPerMonth();
     *         }
     *     });
     * </pre>
     *
     * @since 4.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.ShortIterable collectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ShortFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> shortFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collectShort(ShortFunction)}, except that the results are gathered into the specified {@code target}
     * collection.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * ShortArrayList shorts =
     *     people.collectShort(person -&gt; person.getNumberOfJunkMailItemsReceivedPerMonth, new ShortArrayList());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * ShortArrayList shorts =
     *     people.collectShort(new ShortFunction&lt;Person&gt;()
     *     {
     *         public short shortValueOf(Person person)
     *         {
     *             return person.getNumberOfJunkMailItemsReceivedPerMonth;
     *         }
     *     }, new ShortArrayList());
     * </pre>
     *
     * @param shortFunction
     * 		a {@link ShortFunction} to use as the collect transformation function
     * @param target
     * 		the MutableShortCollection to append to for all elements in this {@code RichIterable}
     * @return {@code target}, which contains appended elements as a result of the collect transformation
     * @since 5.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableShortCollection> [CtTypeParameterReferenceImpl]R collectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.ShortFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> shortFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #collect(Function)} with a {@code Function2} and specified parameter which is passed to the block.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * RichIterable&lt;Integer&gt; integers =
     *     Lists.mutable.with(1, 2, 3).collectWith((each, parameter) -&gt; each + parameter, Integer.valueOf(1));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * Function2&lt;Integer, Integer, Integer&gt; addParameterFunction =
     *     new Function2&lt;Integer, Integer, Integer&gt;()
     *     {
     *         public Integer value(Integer each, Integer parameter)
     *         {
     *             return each + parameter;
     *         }
     *     };
     * RichIterable&lt;Integer&gt; integers =
     *     Lists.mutable.with(1, 2, 3).collectWith(addParameterFunction, Integer.valueOf(1));
     * </pre>
     *
     * @param function
     * 		A {@link Function2} to use as the collect transformation function
     * @param parameter
     * 		A parameter to pass in for evaluation of the second argument {@code P} in {@code function}
     * @return A new {@code RichIterable} that contains the transformed elements returned by {@link Function2#value(Object, Object)}
     * @see #collect(Function)
     * @since 5.0
     */
    <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]V> collectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as collectWith but with a targetCollection parameter to gather the results.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * MutableSet&lt;Integer&gt; integers =
     *     Lists.mutable.with(1, 2, 3).collectWith((each, parameter) -&gt; each + parameter, Integer.valueOf(1), Sets.mutable.empty());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * Function2&lt;Integer, Integer, Integer&gt; addParameterFunction =
     *     new Function2&lt;Integer, Integer, Integer&gt;()
     *     {
     *         public Integer value(final Integer each, final Integer parameter)
     *         {
     *             return each + parameter;
     *         }
     *     };
     * MutableSet&lt;Integer&gt; integers =
     *     Lists.mutable.with(1, 2, 3).collectWith(addParameterFunction, Integer.valueOf(1), Sets.mutable.empty());
     * </pre>
     *
     * @param function
     * 		a {@link Function2} to use as the collect transformation function
     * @param parameter
     * 		a parameter to pass in for evaluation of the second argument {@code P} in {@code function}
     * @param targetCollection
     * 		the Collection to append to for all elements in this {@code RichIterable} that meet select criteria {@code function}
     * @return {@code targetCollection}, which contains appended elements as a result of the collect transformation
     * @since 1.0
     */
    <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R targetCollection);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a new collection with the results of applying the specified function on each element of the source
     * collection, but only for those elements which return true upon evaluation of the predicate. This is the
     * the optimized equivalent of calling iterable.select(predicate).collect(function).
     * <p>
     * Example using a Java 8 lambda and method reference:
     * <pre>
     * RichIterable&lt;String&gt; strings = Lists.mutable.with(1, 2, 3).collectIf(e -&gt; e != null, Object::toString);
     * </pre>
     * <p>
     * Example using Predicates factory:
     * <pre>
     * RichIterable&lt;String&gt; strings = Lists.mutable.with(1, 2, 3).collectIf(Predicates.notNull(), Functions.getToString());
     * </pre>
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]V> collectIf([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as the collectIf method with two parameters but uses the specified target collection for the results.
     *
     * @param predicate
     * 		a {@link Predicate} to use as the select criteria
     * @param function
     * 		a {@link Function} to use as the collect transformation function
     * @param target
     * 		the Collection to append to for all elements in this {@code RichIterable} that meet the collect criteria {@code predicate}
     * @return {@code targetCollection}, which contains appended elements as a result of the collect criteria and transformation
     * @see #collectIf(Predicate, Function)
     * @since 1.0
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R collectIf([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@code flatCollect} is a special case of {@link #collect(Function)}. With {@code collect}, when the {@link Function} returns
     * a collection, the result is a collection of collections. {@code flatCollect} outputs a single "flattened" collection
     * instead. This method is commonly called flatMap.
     * <p>
     * Consider the following example where we have a {@code Person} class, and each {@code Person} has a list of {@code Address} objects. Take the following {@link Function}:
     * <pre>
     * Function&lt;Person, List&lt;Address&gt;&gt; addressFunction = Person::getAddresses;
     * RichIterable&lt;Person&gt; people = ...;
     * </pre>
     * Using {@code collect} returns a collection of collections of addresses.
     * <pre>
     * RichIterable&lt;List&lt;Address&gt;&gt; addresses = people.collect(addressFunction);
     * </pre>
     * Using {@code flatCollect} returns a single flattened list of addresses.
     * <pre>
     * RichIterable&lt;Address&gt; addresses = people.flatCollect(addressFunction);
     * </pre>
     *
     * @param function
     * 		The {@link Function} to apply
     * @return a new flattened collection produced by applying the given {@code function}
     * @since 1.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]V> flatCollect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableByteCollection> [CtTypeParameterReferenceImpl]R flatCollectByte([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.ByteIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableCharCollection> [CtTypeParameterReferenceImpl]R flatCollectChar([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.CharIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableIntCollection> [CtTypeParameterReferenceImpl]R flatCollectInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.IntIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableShortCollection> [CtTypeParameterReferenceImpl]R flatCollectShort([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.ShortIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableDoubleCollection> [CtTypeParameterReferenceImpl]R flatCollectDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.DoubleIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableFloatCollection> [CtTypeParameterReferenceImpl]R flatCollectFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.FloatIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableLongCollection> [CtTypeParameterReferenceImpl]R flatCollectLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.LongIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    default <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.collection.primitive.MutableBooleanCollection> [CtTypeParameterReferenceImpl]R flatCollectBoolean([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.eclipse.collections.api.BooleanIterable> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.addAll([CtInvocationImpl][CtVariableReadImpl]function.valueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 9.2
     */
    default <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]V> flatCollectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.flatCollect([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]function.apply([CtVariableReadImpl]each, [CtVariableReadImpl]parameter));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as flatCollect, only the results are collected into the target collection.
     *
     * @param function
     * 		The {@link Function} to apply
     * @param target
     * 		The collection into which results should be added.
     * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
     * @see #flatCollect(Function)
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R flatCollect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 9.2
     */
    default <[CtTypeParameterImpl]P, [CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R flatCollectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.flatCollect([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]function.apply([CtVariableReadImpl]each, [CtVariableReadImpl]parameter), [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element of the iterable for which the predicate evaluates to true or null in the case where no
     * element returns true. This method is commonly called find.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * Person person =
     *     people.detect(person -&gt; person.getFirstName().equals("John") &amp;&amp; person.getLastName().equals("Smith"));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * Person person =
     *     people.detect(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.getFirstName().equals("John") &amp;&amp; person.getLastName().equals("Smith");
     *         }
     *     });
     * </pre>
     *
     * @since 1.0
     */
    [CtTypeParameterReferenceImpl]T detect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element that evaluates to true for the specified predicate2 and parameter, or null if none
     * evaluate to true.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * Person person =
     *     people.detectWith((person, fullName) -&gt; person.getFullName().equals(fullName), "John Smith");
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * Person person =
     *     people.detectWith(new Predicate2&lt;Person, String&gt;()
     *     {
     *         public boolean accept(Person person, String fullName)
     *         {
     *             return person.getFullName().equals(fullName);
     *         }
     *     }, "John Smith");
     * </pre>
     *
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeParameterReferenceImpl]T detectWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element of the iterable for which the predicate evaluates to true as an Optional. This method is commonly called find.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * Person person =
     *     people.detectOptional(person -&gt; person.getFirstName().equals("John") &amp;&amp; person.getLastName().equals("Smith"));
     * </pre>
     * <p>
     *
     * @throws NullPointerException
     * 		if the element selected is null
     * @since 8.0
     */
    [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> detectOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element that evaluates to true for the specified predicate2 and parameter as an Optional.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * Optional&lt;Person&gt; person =
     *     people.detectWithOptional((person, fullName) -&gt; person.getFullName().equals(fullName), "John Smith");
     * </pre>
     * <p>
     *
     * @throws NullPointerException
     * 		if the element selected is null
     * @since 8.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> detectWithOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element of the iterable for which the predicate evaluates to true. If no element matches
     * the predicate, then returns the value of applying the specified function.
     *
     * @since 1.0
     */
    default [CtTypeParameterReferenceImpl]T detectIfNone([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T result = [CtInvocationImpl][CtThisAccessImpl]this.detect([CtVariableReadImpl]predicate);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]function.value() : [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the first element of the iterable that evaluates to true for the specified predicate2 and parameter, or
     * returns the value of evaluating the specified function.
     *
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeParameterReferenceImpl]T detectWithIfNone([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the total number of elements that answer true to the specified predicate.
     * <p>
     * Example using a Java 8 lambda expression:
     * <pre>
     * int count =
     *     people.<b>count</b>(person -&gt; person.getAddress().getState().getName().equals("New York"));
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * int count =
     *     people.<b>count</b>(new Predicate&lt;Person&gt;()
     *     {
     *         public boolean accept(Person person)
     *         {
     *             return person.getAddress().getState().getName().equals("New York");
     *         }
     *     });
     * </pre>
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]int count([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the total number of elements that evaluate to true for the specified predicate.
     *
     * <pre>e.g.
     * return lastNames.<b>countWith</b>(Predicates2.equal(), "Smith");
     * </pre>
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]int countWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to true for any element of the iterable.
     * Returns false if the iterable is empty, or if no element returned true when evaluating the predicate.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean anySatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to true for any element of the collection, or return false.
     * Returns false if the collection is empty.
     *
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean anySatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to true for every element of the iterable or if the iterable is empty.
     * Otherwise, returns false.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]boolean allSatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to true for every element of the collection, or returns false.
     *
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean allSatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to false for every element of the iterable or if the iterable is empty.
     * Otherwise, returns false.
     *
     * @since 3.0
     */
    [CtTypeReferenceImpl]boolean noneSatisfy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> predicate);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the predicate evaluates to false for every element of the collection, or return false.
     * Returns true if the collection is empty.
     *
     * @since 5.0
     */
    <[CtTypeParameterImpl]P> [CtTypeReferenceImpl]boolean noneSatisfyWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.predicate.Predicate2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P> predicate, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final result of evaluating function using each element of the iterable and the previous evaluation
     * result as the parameters. The injected value is used for the first parameter of the first evaluation, and the current
     * item in the iterable is used as the second parameter. This method is commonly called fold or sometimes reduce.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]IV> [CtTypeParameterReferenceImpl]IV injectInto([CtParameterImpl][CtTypeParameterReferenceImpl]IV injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]IV, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]IV> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final int result of evaluating function using each element of the iterable and the previous evaluation
     * result as the parameters. The injected value is used for the first parameter of the first evaluation, and the current
     * item in the iterable is used as the second parameter.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]int injectInto([CtParameterImpl][CtTypeReferenceImpl]int injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final long result of evaluating function using each element of the iterable and the previous evaluation
     * result as the parameters. The injected value is used for the first parameter of the first evaluation, and the current
     * item in the iterable is used as the second parameter.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]long injectInto([CtParameterImpl][CtTypeReferenceImpl]long injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final float result of evaluating function using each element of the iterable and the previous evaluation
     * result as the parameters. The injected value is used for the first parameter of the first evaluation, and the current
     * item in the iterable is used as the second parameter.
     *
     * @since 2.0
     */
    [CtTypeReferenceImpl]float injectInto([CtParameterImpl][CtTypeReferenceImpl]float injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final double result of evaluating function using each element of the iterable and the previous evaluation
     * result as the parameters. The injected value is used for the first parameter of the first evaluation, and the current
     * item in the iterable is used as the second parameter.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]double injectInto([CtParameterImpl][CtTypeReferenceImpl]double injectedValue, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds all the elements in this iterable to the specific target Collection.
     *
     * @since 8.0
     */
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R into([CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableList implementation.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toList();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableList implementation and sorts it using the natural order of the elements.
     *
     * @since 1.0
     */
    default [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedList() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.toList().sortThis();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableList implementation and sorts it using the specified comparator.
     *
     * @since 1.0
     */
    default [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedList([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.toList().sortThis([CtVariableReadImpl]comparator);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableList implementation and sorts it based on the natural order of the
     * attribute returned by {@code function}.
     *
     * @since 1.0
     */
    default <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.list.MutableList<[CtTypeParameterReferenceImpl]T> toSortedListBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.toSortedList([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSet implementation.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.set.MutableSet<[CtTypeParameterReferenceImpl]T> toSet();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedSet implementation and sorts it using the natural order of the
     * elements.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSet();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedSet implementation and sorts it using the specified comparator.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSet([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedSet implementation and sorts it based on the natural order of the
     * attribute returned by {@code function}.
     *
     * @since 1.0
     */
    default <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.set.sorted.MutableSortedSet<[CtTypeParameterReferenceImpl]T> toSortedSetBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.toSortedSet([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to the default MutableBag implementation.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBag<[CtTypeParameterReferenceImpl]T> toBag();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedBag implementation and sorts it using the natural order of the
     * elements.
     *
     * @since 6.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBag();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to the MutableSortedBag implementation and sorts it using the specified comparator.
     *
     * @since 6.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBag([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedBag implementation and sorts it based on the natural order of the
     * attribute returned by {@code function}.
     *
     * @since 6.0
     */
    default <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]org.eclipse.collections.api.bag.sorted.MutableSortedBag<[CtTypeParameterReferenceImpl]T> toSortedBagBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.toSortedBag([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableMap implementation using the specified key and value functions.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #toMap(Function, Function)}, except that the results are gathered into the specified {@code target}
     * map.
     *
     * @since 10.0
     */
    default <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV>> [CtTypeParameterReferenceImpl]R toMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.put([CtInvocationImpl][CtVariableReadImpl]keyFunction.apply([CtVariableReadImpl]each), [CtInvocationImpl][CtVariableReadImpl]valueFunction.apply([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedMap implementation using the specified key and value functions
     * sorted by the key elements' natural ordering.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedMap implementation using the specified key and value functions
     * sorted by the given comparator.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMap([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]NK> comparator, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a MutableSortedMap implementation using the specified key and value functions
     * and sorts it based on the natural order of the attribute returned by {@code sortBy} function.
     */
    default <[CtTypeParameterImpl]KK extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]KK>, [CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.map.sorted.MutableSortedMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toSortedMapBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]KK> sortBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.toSortedMap([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtVariableReadImpl]sortBy), [CtVariableReadImpl]keyFunction, [CtVariableReadImpl]valueFunction);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the collection to a BiMap implementation using the specified key and value functions.
     *
     * @since 10.0
     */
    <[CtTypeParameterImpl]NK, [CtTypeParameterImpl]NV> [CtTypeReferenceImpl]org.eclipse.collections.api.bimap.MutableBiMap<[CtTypeParameterReferenceImpl]NK, [CtTypeParameterReferenceImpl]NV> toBiMap([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NK> keyFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]NV> valueFunction);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a lazy (deferred) iterable, most likely implemented by calling LazyIterate.adapt(this).
     *
     * @since 1.0.
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.LazyIterable<[CtTypeParameterReferenceImpl]T> asLazy();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts this iterable to an array.
     *
     * @see Collection#toArray()
     * @since 1.0
     */
    [CtArrayTypeReferenceImpl]java.lang.Object[] toArray();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts this iterable to an array using the specified target array, assuming the target array is as long
     * or longer than the iterable.
     *
     * @see Collection#toArray(Object[])
     * @since 1.0
     */
    <[CtTypeParameterImpl]E> [CtArrayTypeReferenceImpl]E[] toArray([CtParameterImpl][CtArrayTypeReferenceImpl]E[] array);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum element out of this container based on the comparator.
     *
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    [CtTypeParameterReferenceImpl]T min([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum element out of this container based on the comparator.
     *
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    [CtTypeParameterReferenceImpl]T max([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum element out of this container based on the comparator as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws NullPointerException
     * 		if the minimum element is null
     * @since 8.2
     */
    default [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> minOptional([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.min([CtVariableReadImpl]comparator));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum element out of this container based on the comparator as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws NullPointerException
     * 		if the maximum element is null
     * @since 8.2
     */
    default [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> maxOptional([CtParameterImpl][CtTypeReferenceImpl]java.util.Comparator<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> comparator) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.max([CtVariableReadImpl]comparator));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum element out of this container based on the natural order.
     *
     * @throws ClassCastException
     * 		if the elements are not {@link Comparable}
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    [CtTypeParameterReferenceImpl]T min();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum element out of this container based on the natural order.
     *
     * @throws ClassCastException
     * 		if the elements are not {@link Comparable}
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    [CtTypeParameterReferenceImpl]T max();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum element out of this container based on the natural order as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws ClassCastException
     * 		if the elements are not {@link Comparable}
     * @throws NullPointerException
     * 		if the minimum element is null
     * @since 8.2
     */
    default [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> minOptional() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.min());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum element out of this container based on the natural order as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws ClassCastException
     * 		if the elements are not {@link Comparable}
     * @throws NullPointerException
     * 		if the maximum element is null
     * @since 8.2
     */
    default [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> maxOptional() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.max());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum elements out of this container based on the natural order of the attribute returned by Function.
     *
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]T minBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum elements out of this container based on the natural order of the attribute returned by Function.
     *
     * @throws NoSuchElementException
     * 		if the RichIterable is empty
     * @since 1.0
     */
    <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]T maxBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum elements out of this container based on the natural order of the attribute returned by Function as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws NullPointerException
     * 		if the minimum element is null
     * @since 8.2
     */
    default <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> minByOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.minBy([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum elements out of this container based on the natural order of the attribute returned by Function as an Optional.
     * If the container is empty {@link Optional#empty()} is returned.
     *
     * @throws NullPointerException
     * 		if the maximum element is null
     * @since 8.2
     */
    default <[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> maxByOptional([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtThisAccessImpl]this.maxBy([CtVariableReadImpl]function));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final long result of evaluating function for each element of the iterable and adding the results
     * together.
     *
     * @since 2.0
     */
    [CtTypeReferenceImpl]long sumOfInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final double result of evaluating function for each element of the iterable and adding the results
     * together. It uses Kahan summation algorithm to reduce numerical error.
     *
     * @since 2.0
     */
    [CtTypeReferenceImpl]double sumOfFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final long result of evaluating function for each element of the iterable and adding the results
     * together.
     *
     * @since 2.0
     */
    [CtTypeReferenceImpl]long sumOfLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the final double result of evaluating function for each element of the iterable and adding the results
     * together. It uses Kahan summation algorithm to reduce numerical error.
     *
     * @since 2.0
     */
    [CtTypeReferenceImpl]double sumOfDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the result of summarizing the value returned from applying the IntFunction to
     * each element of the iterable.
     *
     * <pre>
     * IntSummaryStatistics stats =
     *     Lists.mutable.with(1, 2, 3).summarizeInt(Integer::intValue);
     * </pre>
     *
     * @since 8.0
     */
    default [CtTypeReferenceImpl]java.util.IntSummaryStatistics summarizeInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.IntSummaryStatistics stats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.IntSummaryStatistics();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]stats.accept([CtInvocationImpl][CtVariableReadImpl]function.intValueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]stats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the result of summarizing the value returned from applying the FloatFunction to
     * each element of the iterable.
     *
     * <pre>
     * DoubleSummaryStatistics stats =
     *     Lists.mutable.with(1, 2, 3).summarizeFloat(Integer::floatValue);
     * </pre>
     *
     * @since 8.0
     */
    default [CtTypeReferenceImpl]java.util.DoubleSummaryStatistics summarizeFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.DoubleSummaryStatistics stats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.DoubleSummaryStatistics();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]stats.accept([CtInvocationImpl][CtVariableReadImpl]function.floatValueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]stats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the result of summarizing the value returned from applying the LongFunction to
     * each element of the iterable.
     *
     * <pre>
     * LongSummaryStatistics stats =
     *     Lists.mutable.with(1, 2, 3).summarizeLong(Integer::longValue);
     * </pre>
     *
     * @since 8.0
     */
    default [CtTypeReferenceImpl]java.util.LongSummaryStatistics summarizeLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LongSummaryStatistics stats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LongSummaryStatistics();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]stats.accept([CtInvocationImpl][CtVariableReadImpl]function.longValueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]stats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the result of summarizing the value returned from applying the DoubleFunction to
     * each element of the iterable.
     *
     * <pre>
     * DoubleSummaryStatistics stats =
     *     Lists.mutable.with(1, 2, 3).summarizeDouble(Integer::doubleValue);
     * </pre>
     *
     * @since 8.0
     */
    default [CtTypeReferenceImpl]java.util.DoubleSummaryStatistics summarizeDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.DoubleSummaryStatistics stats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.DoubleSummaryStatistics();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]stats.accept([CtInvocationImpl][CtVariableReadImpl]function.doubleValueOf([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]stats;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method produces the equivalent result as {@link Stream#collect(Collector)}.
     *
     * <pre>
     * MutableObjectLongMap&lt;Integer&gt; map2 =
     *     Lists.mutable.with(1, 2, 3, 4, 5).reduceInPlace(Collectors2.sumByInt(i -&gt; Integer.valueOf(i % 2), Integer::intValue));
     * </pre>
     *
     * @since 8.0
     */
    default <[CtTypeParameterImpl]R, [CtTypeParameterImpl]A> [CtTypeParameterReferenceImpl]R reduceInPlace([CtParameterImpl][CtTypeReferenceImpl]java.util.stream.Collector<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]R> collector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]A mutableResult = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.supplier().get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeParameterReferenceImpl]A, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> accumulator = [CtInvocationImpl][CtVariableReadImpl]collector.accumulator();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]accumulator.accept([CtVariableReadImpl]mutableResult, [CtVariableReadImpl]each));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.finisher().apply([CtVariableReadImpl]mutableResult);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method produces the equivalent result as {@link Stream#collect(Supplier, BiConsumer, BiConsumer)}.
     * The combiner used in collect is unnecessary in the serial case, so is not included in the API.
     *
     * @since 8.0
     */
    default <[CtTypeParameterImpl]R> [CtTypeParameterReferenceImpl]R reduceInPlace([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeParameterReferenceImpl]R> supplier, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeParameterReferenceImpl]R, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> accumulator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]R result = [CtInvocationImpl][CtVariableReadImpl]supplier.get();
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]accumulator.accept([CtVariableReadImpl]result, [CtVariableReadImpl]each));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method produces the equivalent result as {@link Stream#reduce(BinaryOperator)}.
     *
     * @since 8.0
     */
    default [CtTypeReferenceImpl]java.util.Optional<[CtTypeParameterReferenceImpl]T> reduce([CtParameterImpl][CtTypeReferenceImpl]java.util.function.BinaryOperator<[CtTypeParameterReferenceImpl]T> accumulator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]boolean[] seenOne = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtLiteralImpl]1];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]T[] result = [CtNewArrayImpl](([CtArrayTypeReferenceImpl]T[]) (new [CtTypeReferenceImpl]java.lang.Object[[CtLiteralImpl]1]));
        [CtInvocationImpl][CtThisAccessImpl]this.each([CtLambdaImpl]([CtParameterImpl] each) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtArrayReadImpl][CtVariableReadImpl]seenOne[[CtLiteralImpl]0]) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtLiteralImpl]0] = [CtInvocationImpl][CtVariableReadImpl]accumulator.apply([CtArrayReadImpl][CtVariableReadImpl]result[[CtLiteralImpl]0], [CtVariableReadImpl]each);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seenOne[[CtLiteralImpl]0] = [CtLiteralImpl]true;
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtLiteralImpl]0] = [CtVariableReadImpl]each;
            }
        });
        [CtReturnImpl]return [CtConditionalImpl][CtArrayReadImpl][CtVariableReadImpl]seenOne[[CtLiteralImpl]0] ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtArrayReadImpl][CtVariableReadImpl]result[[CtLiteralImpl]0]) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Groups and sums the values using the two specified functions.
     *
     * @since 6.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectLongMap<[CtTypeParameterReferenceImpl]V> sumByInt([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.IntFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Groups and sums the values using the two specified functions.
     *
     * @since 6.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectDoubleMap<[CtTypeParameterReferenceImpl]V> sumByFloat([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.FloatFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Groups and sums the values using the two specified functions.
     *
     * @since 6.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectLongMap<[CtTypeParameterReferenceImpl]V> sumByLong([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.LongFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Groups and sums the values using the two specified functions.
     *
     * @since 6.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.primitive.ObjectDoubleMap<[CtTypeParameterReferenceImpl]V> sumByDouble([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.primitive.DoubleFunction<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a string representation of this collection by delegating to {@link #makeString(String)} and defaulting
     * the separator parameter to the characters {@code ", "} (comma and space).
     *
     * @return a string representation of this collection.
     * @since 1.0
     */
    default [CtTypeReferenceImpl]java.lang.String makeString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.makeString([CtLiteralImpl]", ");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a string representation of this collection by delegating to {@link #makeString(String, String, String)}
     * and defaulting the start and end parameters to {@code ""} (the empty String).
     *
     * @return a string representation of this collection.
     * @since 1.0
     */
    default [CtTypeReferenceImpl]java.lang.String makeString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.makeString([CtLiteralImpl]"", [CtVariableReadImpl]separator, [CtLiteralImpl]"");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a string representation of this collection with the elements separated by the specified
     * separator and enclosed between the start and end strings.
     *
     * @return a string representation of this collection.
     * @since 1.0
     */
    default [CtTypeReferenceImpl]java.lang.String makeString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String start, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String end) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Appendable stringBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtThisAccessImpl]this.appendString([CtVariableReadImpl]stringBuilder, [CtVariableReadImpl]start, [CtVariableReadImpl]separator, [CtVariableReadImpl]end);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]stringBuilder.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Prints a string representation of this collection onto the given {@code Appendable}. Prints the string returned
     * by {@link #makeString()}.
     *
     * @since 1.0
     */
    default [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.appendString([CtVariableReadImpl]appendable, [CtLiteralImpl]", ");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Prints a string representation of this collection onto the given {@code Appendable}. Prints the string returned
     * by {@link #makeString(String)}.
     *
     * @since 1.0
     */
    default [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.appendString([CtVariableReadImpl]appendable, [CtLiteralImpl]"", [CtVariableReadImpl]separator, [CtLiteralImpl]"");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Prints a string representation of this collection onto the given {@code Appendable}. Prints the string returned
     * by {@link #makeString(String, String, String)}.
     *
     * @since 1.0
     */
    [CtTypeReferenceImpl]void appendString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable appendable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String start, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String separator, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String end);

    [CtMethodImpl][CtJavaDocImpl]/**
     * For each element of the iterable, the function is evaluated and the results of these evaluations are collected
     * into a new multimap, where the transformed value is the key and the original values are added to the same (or similar)
     * species of collection as the source iterable.
     * <p>
     * Example using a Java 8 method reference:
     * <pre>
     * Multimap&lt;String, Person&gt; peopleByLastName =
     *     people.groupBy(Person::getLastName);
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * Multimap&lt;String, Person&gt; peopleByLastName =
     *     people.groupBy(new Function&lt;Person, String&gt;()
     *     {
     *         public String valueOf(Person person)
     *         {
     *             return person.getLastName();
     *         }
     *     });
     * </pre>
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.Multimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection.
     *
     * @since 9.0
     */
    default <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeParameterReferenceImpl]V> countBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.countBy([CtVariableReadImpl]function, [CtInvocationImpl][CtTypeAccessImpl]Bags.mutable.empty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection.
     *
     * @since 9.0
     */
    default <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBagIterable<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R countBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.collect([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection with the specified parameter as the second argument.
     *
     * @since 9.0
     */
    default <[CtTypeParameterImpl]V, [CtTypeParameterImpl]P> [CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeParameterReferenceImpl]V> countByWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.countByWith([CtVariableReadImpl]function, [CtVariableReadImpl]parameter, [CtInvocationImpl][CtTypeAccessImpl]Bags.mutable.empty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection with the specified parameter as the second argument.
     *
     * @since 9.0
     */
    default <[CtTypeParameterImpl]V, [CtTypeParameterImpl]P, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBagIterable<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R countByWith([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]P, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]P parameter, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.collectWith([CtVariableReadImpl]function, [CtVariableReadImpl]parameter, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection.
     *
     * @since 10.0.0
     */
    default <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.bag.Bag<[CtTypeParameterReferenceImpl]V> countByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.asLazy().flatCollect([CtVariableReadImpl]function).toBag();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will count the number of occurrences of each value calculated by applying the
     * function to each element of the collection.
     *
     * @since 10.0.0
     */
    default <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.bag.MutableBagIterable<[CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R countByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.flatCollect([CtVariableReadImpl]function, [CtVariableReadImpl]target);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #groupBy(Function)}, except that the results are gathered into the specified {@code target}
     * multimap.
     * <p>
     * Example using a Java 8 method reference:
     * <pre>
     * FastListMultimap&lt;String, Person&gt; peopleByLastName =
     *     people.groupBy(Person::getLastName, new FastListMultimap&lt;String, Person&gt;());
     * </pre>
     * <p>
     * Example using an anonymous inner class:
     * <pre>
     * FastListMultimap&lt;String, Person&gt; peopleByLastName =
     *     people.groupBy(new Function&lt;Person, String&gt;()
     *     {
     *         public String valueOf(Person person)
     *         {
     *             return person.getLastName();
     *         }
     *     }, new FastListMultimap&lt;String, Person&gt;());
     * </pre>
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.MutableMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar to {@link #groupBy(Function)}, except the result of evaluating function will return a collection of keys
     * for each value.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.Multimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #groupByEach(Function)}, except that the results are gathered into the specified {@code target}
     * multimap.
     *
     * @since 1.0
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.MutableMultimap<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupByEach([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]V>> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * For each element of the iterable, the function is evaluated and he results of these evaluations are collected
     * into a new map, where the transformed value is the key. The generated keys must each be unique, or else an
     * exception is thrown.
     *
     * @throws IllegalStateException
     * 		if the keys returned by the function are not unique
     * @see #groupBy(Function)
     * @since 5.0
     */
    <[CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.MapIterable<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T> groupByUniqueKey([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #groupByUniqueKey(Function)}, except that the results are gathered into the specified {@code target}
     * map.
     *
     * @throws IllegalStateException
     * 		if the keys returned by the function are not unique
     * @see #groupByUniqueKey(Function)
     * @since 6.0
     */
    <[CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMapIterable<[CtTypeParameterReferenceImpl]V, [CtTypeParameterReferenceImpl]T>> [CtTypeParameterReferenceImpl]R groupByUniqueKey([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> function, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a string with the elements of this iterable separated by commas with spaces and
     * enclosed in square brackets.
     *
     * <pre>
     * Assert.assertEquals("[]", Lists.mutable.empty().toString());
     * Assert.assertEquals("[1]", Lists.mutable.with(1).toString());
     * Assert.assertEquals("[1, 2, 3]", Lists.mutable.with(1, 2, 3).toString());
     * </pre>
     *
     * @return a string representation of this RichIterable
     * @see java.util.AbstractCollection#toString()
     * @since 1.0
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtTypeReferenceImpl]java.lang.String toString();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a {@code RichIterable} formed from this {@code RichIterable} and another {@code RichIterable} by
     * combining corresponding elements in pairs. If one of the two {@code RichIterable}s is longer than the other, its
     * remaining elements are ignored.
     *
     * @param that
     * 		The {@code RichIterable} providing the second half of each result pair
     * @param <S>
     * 		the type of the second half of the returned pairs
     * @return A new {@code RichIterable} containing pairs consisting of corresponding elements of this {@code RichIterable} and that. The length of the returned {@code RichIterable} is the minimum of the lengths of
    this {@code RichIterable} and that.
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#zip(Iterable)} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    <[CtTypeParameterImpl]S> [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]S>> zip([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]S> that);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #zip(Iterable)} but uses {@code target} for output.
     *
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#zip(Iterable, Collection)} instead;
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    <[CtTypeParameterImpl]S, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]S>>> [CtTypeParameterReferenceImpl]R zip([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeParameterReferenceImpl]S> that, [CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Zips this {@code RichIterable} with its indices.
     *
     * @return A new {@code RichIterable} containing pairs consisting of all elements of this {@code RichIterable}
    paired with their index. Indices start at 0.
     * @see #zip(Iterable)
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#zipWithIndex()} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.Integer>> zipWithIndex();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Same as {@link #zipWithIndex()} but uses {@code target} for output.
     *
     * @since 1.0
     * @deprecated in 6.0. Use {@link OrderedIterable#zipWithIndex(Collection)} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    <[CtTypeParameterImpl]R extends [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.eclipse.collections.api.tuple.Pair<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.Integer>>> [CtTypeParameterReferenceImpl]R zipWithIndex([CtParameterImpl][CtTypeParameterReferenceImpl]R target);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Partitions elements in fixed size chunks.
     *
     * @param size
     * 		the number of elements per chunk
     * @return A {@code RichIterable} containing {@code RichIterable}s of size {@code size}, except the last will be
    truncated if the elements don't divide evenly.
     * @since 1.0
     */
    [CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeReferenceImpl]org.eclipse.collections.api.RichIterable<[CtTypeParameterReferenceImpl]T>> chunk([CtParameterImpl][CtTypeReferenceImpl]int size);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Applies an aggregate procedure over the iterable grouping results into a Map based on the specific groupBy function.
     * Aggregate results are required to be mutable as they will be changed in place by the procedure. A second function
     * specifies the initial "zero" aggregate value to work with (i.e. new AtomicInteger(0)).
     *
     * @since 3.0
     */
    default <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.MapIterable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> aggregateInPlaceBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> zeroValueFactory, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.procedure.Procedure2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> mutatingAggregator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> map = [CtInvocationImpl][CtTypeAccessImpl]Maps.mutable.empty();
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.K key = [CtInvocationImpl][CtVariableReadImpl]groupBy.valueOf([CtVariableReadImpl]each);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.V value = [CtInvocationImpl][CtVariableReadImpl]map.getIfAbsentPut([CtVariableReadImpl]key, [CtVariableReadImpl]zeroValueFactory);
            [CtInvocationImpl][CtVariableReadImpl]mutatingAggregator.value([CtVariableReadImpl]value, [CtVariableReadImpl]each);
        });
        [CtReturnImpl]return [CtVariableReadImpl]map;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Applies an aggregate function over the iterable grouping results into a map based on the specific groupBy function.
     * Aggregate results are allowed to be immutable as they will be replaced in place in the map. A second function
     * specifies the initial "zero" aggregate value to work with (i.e. Integer.valueOf(0)).
     *
     * @since 3.0
     */
    default <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> [CtTypeReferenceImpl]org.eclipse.collections.api.map.MapIterable<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> aggregateBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> zeroValueFactory, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> nonMutatingAggregator) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.aggregateBy([CtVariableReadImpl]groupBy, [CtVariableReadImpl]zeroValueFactory, [CtVariableReadImpl]nonMutatingAggregator, [CtInvocationImpl][CtTypeAccessImpl]Maps.mutable.empty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 10.3
     */
    default <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.map.MutableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R aggregateBy([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K> groupBy, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function0<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> zeroValueFactory, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function2<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]V, [CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> nonMutatingAggregator, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.collections.api.K key = [CtInvocationImpl][CtVariableReadImpl]groupBy.valueOf([CtVariableReadImpl]each);
            [CtInvocationImpl][CtVariableReadImpl]target.updateValueWith([CtVariableReadImpl]key, [CtVariableReadImpl]zeroValueFactory, [CtVariableReadImpl]nonMutatingAggregator, [CtVariableReadImpl]each);
        });
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Applies a groupBy function over the iterable, followed by a collect function.
     *
     * @param groupByFunction
     * 		a {@link Function} to use as the groupBy transformation function
     * @param collectFunction
     * 		a {@link Function} to use as the collect transformation function
     * @return The {@code target} collection where the key is the transformed result from applying the groupBy function
    and the value is the transformed result from applying the collect function.
     * @see #groupBy(Function)
     * @see Multimap#collectValues(Function)
     * @since 10.1.0
     */
    default <[CtTypeParameterImpl]K, [CtTypeParameterImpl]V, [CtTypeParameterImpl]R extends [CtTypeReferenceImpl]org.eclipse.collections.api.multimap.MutableMultimap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V>> [CtTypeParameterReferenceImpl]R groupByAndCollect([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]K> groupByFunction, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.collections.api.block.function.Function<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T, [CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]V> collectFunction, [CtParameterImpl][CtTypeParameterReferenceImpl]R target) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.forEach([CtLambdaImpl]([CtParameterImpl] each) -> [CtInvocationImpl][CtVariableReadImpl]target.put([CtInvocationImpl][CtVariableReadImpl]groupByFunction.apply([CtVariableReadImpl]each), [CtInvocationImpl][CtVariableReadImpl]collectFunction.apply([CtVariableReadImpl]each)));
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }
}
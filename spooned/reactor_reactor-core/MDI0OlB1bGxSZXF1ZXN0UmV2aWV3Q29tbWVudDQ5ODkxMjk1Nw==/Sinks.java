[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2011-Present VMware Inc. or its affiliates, All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package reactor.core.publisher;
[CtUnresolvedImport]import reactor.core.Disposable;
[CtUnresolvedImport]import reactor.core.Scannable;
[CtUnresolvedImport]import reactor.util.context.Context;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import reactor.core.Exceptions;
[CtUnresolvedImport]import reactor.core.scheduler.Scheduler;
[CtUnresolvedImport]import org.reactivestreams.Subscriber;
[CtUnresolvedImport]import reactor.util.annotation.Nullable;
[CtImportImpl]import java.util.Queue;
[CtUnresolvedImport]import reactor.util.concurrent.Queues;
[CtClassImpl][CtJavaDocImpl]/**
 * Sinks are constructs through which Reactive Streams signals can be programmatically pushed, with {@link Flux} or {@link Mono}
 * semantics. These standalone sinks expose {@code emit} methods that return an {@link Emission} enum, allowing to
 * softly fail in case the attempted signal is inconsistent with the spec and/or the state of the sink.
 * <p>
 * This class exposes a collection of ({@link Sinks.Many} builders and {@link Sinks.One} factories.
 *
 * @author Simon Baslé
 * @author Stephane Maldini
 */
public final class Sinks {
    [CtConstructorImpl]private Sinks() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A {@link Sinks.Empty} which exclusively produces one terminal signal: error or complete.
     * It has the following characteristics:
     * <ul>
     *     <li>Multicast</li>
     *     <li>Backpressure : this sink does not need any demand since it can only signal error or completion</li>
     *     <li>Replaying: Replay the terminal signal (error or complete).</li>
     * </ul>
     * Use {@link Sinks.Empty#asMono()} to expose the {@link Mono} view of the sink to downstream consumers.
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Empty<[CtTypeParameterReferenceImpl]T> empty() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]reactor.core.publisher.SinkEmptyMulticast<[CtTypeParameterReferenceImpl]T>();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A {@link Sinks.One} that works like a conceptual promise: it can be completed
     * with or without a value at any time, but only once. This completion is replayed to late subscribers.
     * Calling {@link One#emitValue(Object)} (or {@link One#tryEmitValue(Object)}) is enough and will
     * implicitly produce a {@link Subscriber#onComplete()} signal as well.
     * <p>
     * Use {@link One#asMono()} to expose the {@link Mono} view of the sink to downstream consumers.
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.One<[CtTypeParameterReferenceImpl]T> one() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]reactor.core.publisher.NextProcessor<>([CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Help building {@link Sinks.Many} sinks that will broadcast multiple signals to one or more {@link Subscriber}.
     * <p>
     * Use {@link Many#asFlux()} to expose the {@link Flux} view of the sink to the downstream consumers.
     *
     * @return {@link ManySpec}
     */
    public static [CtTypeReferenceImpl]reactor.core.publisher.Sinks.ManySpec many() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]SinksSpecs.MANY_SPEC;
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * Represents the immediate status of a signal emission. This does not guarantee that a signal is consumed,
     * it simply refers to the sink state when an emit method is invoked. This is a particularly important
     * distinction with regards to {@link #FAIL_CANCELLED} which means the sink is -now- interrupted and emission can't
     * proceed. Consequently, it is possible to emit a signal and obtain an "OK" status even if an in-flight cancellation
     * is happening. This is due to the async nature of these actions: producer emits while consumer can interrupt independently.
     */
    public enum Emission {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has successfully emitted the signal
         */
        OK,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the sink was previously terminated successfully or with an error
         */
        FAIL_TERMINATED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the sink does not have buffering capacity left
         */
        FAIL_OVERFLOW,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the sink was previously interrupted by its consumer
         */
        FAIL_CANCELLED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the access was not serialized
         */
        FAIL_NON_SERIALIZED,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the sink has never been subscribed to has no capacity
         * to buffer the signal.
         */
        FAIL_ZERO_SUBSCRIBER;
        [CtMethodImpl][CtJavaDocImpl]/**
         * Has successfully emitted the signal
         */
        public [CtTypeReferenceImpl]boolean hasSucceeded() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]reactor.core.publisher.Sinks.Emission.OK;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Has failed to emit the signal because the sink was previously terminated successfully or with an error, or
         * has been cancelled or has overflowed its buffering capacity in terms of backpressure.
         */
        public [CtTypeReferenceImpl]boolean hasFailed() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtThisAccessImpl]this != [CtFieldReadImpl]reactor.core.publisher.Sinks.Emission.OK;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Easily convert from an {@link Emission} to throwing an exception on {@link #hasFailed() failure cases}.
         * This is useful if throwing is the most relevant way of dealing with a failed emission attempt.
         * See also {@link #orThrowWithCause(Throwable)} in case of an {@link One#emitError(Throwable) emitError}
         * failure for which you want to propagate the originally pushed {@link Exception}.
         *
         * @see #orThrowWithCause(Throwable)
         */
        public [CtTypeReferenceImpl]void orThrow() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]reactor.core.publisher.Sinks.Emission.OK)[CtBlockImpl]
                [CtReturnImpl]return;

            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmissionException([CtThisAccessImpl]this);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Easily convert from an {@link Emission} to throwing an exception on {@link #hasFailed() failure cases}.
         * This is useful if throwing is the most relevant way of dealing with failed {@link One#emitError(Throwable)}
         * attempt, in which case you probably wants to propagate the originally pushed {@link Exception}.
         *
         * @see #orThrow()
         */
        public [CtTypeReferenceImpl]void orThrowWithCause([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable cause) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtFieldReadImpl]reactor.core.publisher.Sinks.Emission.OK)[CtBlockImpl]
                [CtReturnImpl]return;

            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmissionException([CtVariableReadImpl]cause, [CtThisAccessImpl]this);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * An exception representing a {@link Emission#hasFailed() failed} {@link Emission}.
     * The exact type of failure can be found via {@link #getReason()}.
     */
    public static final class EmissionException extends [CtTypeReferenceImpl]java.lang.IllegalStateException {
        [CtFieldImpl]final [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission reason;

        [CtConstructorImpl]public EmissionException([CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission reason) [CtBlockImpl]{
            [CtInvocationImpl]this([CtVariableReadImpl]reason, [CtBinaryOperatorImpl][CtLiteralImpl]"Sink emission failed with " + [CtVariableReadImpl]reason);
        }

        [CtConstructorImpl]public EmissionException([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable cause, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission reason) [CtBlockImpl]{
            [CtInvocationImpl]super([CtBinaryOperatorImpl][CtLiteralImpl]"Sink emission failed with " + [CtVariableReadImpl]reason, [CtVariableReadImpl]cause);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reason = [CtVariableReadImpl]reason;
        }

        [CtConstructorImpl]public EmissionException([CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission reason, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]message);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reason = [CtVariableReadImpl]reason;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Get the failure {@link Emission} code that is represented by this exception.
         *
         * @return the {@link Emission}
         */
        public [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission getReason() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.reason;
        }
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * A handler for any non-successful emission result from operations
     * such as {@link Many#emitNext(Object, EmitFailureHandler)}
     * that allows retrying or failing the operation.
     */
    public interface EmitFailureHandler {
        [CtFieldImpl][CtJavaDocImpl]/**
         * A pre-made handler that will not attempt at retry any failure
         * trigger the failure handling immediately.
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler FAIL_FAST = [CtLambdaImpl]([CtParameterImpl]reactor.core.publisher.SignalType signalType,[CtParameterImpl]reactor.core.publisher.Sinks.Emission emission) -> [CtLiteralImpl]false;

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param signalType
         * 		the signal that triggered the emission. Can be either {@link SignalType#ON_NEXT}, {@link SignalType#ON_ERROR} or {@link SignalType#ON_COMPLETE}.
         * @param emission
         * 		the emission of the failure.
         * @return {@code true} if the operation should be retried, {@code false} otherwise.
         */
        [CtTypeReferenceImpl]boolean onEmitFailure([CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.SignalType signalType, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission emission);
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Provides {@link Sinks.Many} specs for sinks which can emit multiple elements
     */
    public interface ManySpec {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Help building {@link Sinks.Many} that will broadcast signals to a single {@link Subscriber}
         *
         * @return {@link UnicastSpec}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.UnicastSpec unicast();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Help building {@link Sinks.Many} that will broadcast signals to multiple {@link Subscriber}
         *
         * @return {@link MulticastSpec}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.MulticastSpec multicast();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Help building {@link Sinks.Many} that will broadcast signals to multiple {@link Subscriber} with the ability to retain
         * and replay all or an arbitrary number of elements.
         *
         * @return {@link MulticastReplaySpec}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.MulticastReplaySpec replay();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Return a builder for more advanced use cases such as building operators.
         * Unsafe {@link Sinks.Many} are not serialized and expect usage to be externally synchronized to respect
         * the Reactive Streams specification.
         *
         * @return {@link ManySpec}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.ManySpec unsafe();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Provides unicast: 1 sink, 1 {@link Subscriber}
     */
    public interface UnicastSpec {
        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li><strong>Unicast</strong>: contrary to most other {@link Sinks.Many}, the
         *     {@link Flux} view rejects {@link Subscriber subscribers} past the first one.</li>
         *     <li>Backpressure : this sink honors downstream demand of its single {@link Subscriber}.</li>
         *     <li>Replaying: non-applicable, since only one {@link Subscriber} can register.</li>
         *     <li>Without {@link Subscriber}: all elements pushed to this sink are remembered and will
         *     be replayed once the {@link Subscriber} subscribes.</li>
         * </ul>
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer();

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li><strong>Unicast</strong>: contrary to most other {@link Sinks.Many}, the
         *     {@link Flux} view rejects {@link Subscriber subscribers} past the first one.</li>
         *     <li>Backpressure : this sink honors downstream demand of its single {@link Subscriber}.</li>
         *     <li>Replaying: non-applicable, since only one {@link Subscriber} can register.</li>
         *    <li>Without {@link Subscriber}: depending on the queue, all elements pushed to this sink are remembered and will
         * 		  be replayed once the {@link Subscriber} subscribes.</li>
         * </ul>
         *
         * @param queue
         * 		an arbitrary queue to use that must at least support Single Producer / Single Consumer semantics
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer([CtParameterImpl][CtTypeReferenceImpl]java.util.Queue<[CtTypeParameterReferenceImpl]T> queue);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li><strong>Unicast</strong>: contrary to most other {@link Sinks.Many}, the
         *     {@link Flux} view rejects {@link Subscriber subscribers} past the first one.</li>
         *     <li>Backpressure : this sink honors downstream demand of its single {@link Subscriber}.</li>
         *     <li>Replaying: non-applicable, since only one {@link Subscriber} can register.</li>
         *     <li>Without {@link Subscriber}: depending on the queue, all elements pushed to this sink are remembered and will
         *     be replayed once the {@link Subscriber} subscribes.</li>
         * </ul>
         *
         * @param queue
         * 		an arbitrary queue to use that must at least support Single Producer / Single Consumer semantics
         * @param endCallback
         * 		when a terminal signal is observed: error, complete or cancel
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer([CtParameterImpl][CtTypeReferenceImpl]java.util.Queue<[CtTypeParameterReferenceImpl]T> queue, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.Disposable endCallback);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li><strong>Unicast</strong>: contrary to most other {@link Sinks.Many}, the
         *     {@link Flux} view rejects {@link Subscriber subscribers} past the first one.</li>
         *     <li>Backpressure : this sink honors downstream demand of the Subscriber, and will emit {@link Subscriber#onError(Throwable)} if there is a mismatch.</li>
         *     <li>Replaying: No replay. Only forwards to a {@link Subscriber} the elements that have been
         *     pushed to the sink AFTER this subscriber was subscribed.</li>
         * </ul>
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureError();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Provides multicast : 1 sink, N {@link Subscriber}
     */
    public interface MulticastSpec {
        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: warm up. Remembers up to {@link Queues#SMALL_BUFFER_SIZE}
         *     elements pushed via {@link Many#tryEmitNext(Object)} before the first {@link Subscriber} is registered.</li>
         *     <li>Backpressure : this sink honors downstream demand by conforming to the lowest demand in case
         *     of multiple subscribers.<br>If the difference between multiple subscribers is greater than {@link Queues#SMALL_BUFFER_SIZE}:
         *          <ul><li>{@link Many#tryEmitNext(Object) tryEmitNext} will return {@link Emission#FAIL_OVERFLOW}</li>
         * 	        <li>{@link Many#emitNext(Object) emitNext} will terminate the sink by {@link Many#emitError(Throwable) emitting}
         *          an {@link Exceptions#failWithOverflow() overflow error}.</li></ul>
         * 	   </li>
         *     <li>Replaying: No replay of values seen by earlier subscribers. Only forwards to a {@link Subscriber}
         *     the elements that have been pushed to the sink AFTER this subscriber was subscribed, or elements
         *     that have been buffered due to backpressure/warm up.</li>
         * </ul>
         * <p>
         * <img class="marble" src="doc-files/marbles/sinkWarmup.svg" alt="">
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer();

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: warm up. Remembers up to {@code bufferSize}
         *     elements pushed via {@link Many#tryEmitNext(Object)} before the first {@link Subscriber} is registered.</li>
         *     <li>Backpressure : this sink honors downstream demand by conforming to the lowest demand in case
         *     of multiple subscribers.<br>If the difference between multiple subscribers is too high compared to {@code bufferSize}:
         *          <ul><li>{@link Many#tryEmitNext(Object) tryEmitNext} will return {@link Emission#FAIL_OVERFLOW}</li>
         *          <li>{@link Many#emitNext(Object) emitNext} will terminate the sink by {@link Many#emitError(Throwable) emitting}
         *          an {@link Exceptions#failWithOverflow() overflow error}.</li></ul>
         *     </li>
         *     <li>Replaying: No replay of values seen by earlier subscribers. Only forwards to a {@link Subscriber}
         *     the elements that have been pushed to the sink AFTER this subscriber was subscribed, or elements
         *     that have been buffered due to backpressure/warm up.</li>
         * </ul>
         * <p>
         * <img class="marble" src="doc-files/marbles/sinkWarmup.svg" alt="">
         *
         * @param bufferSize
         * 		the maximum queue size
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer([CtParameterImpl][CtTypeReferenceImpl]int bufferSize);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: warm up. Remembers up to {@code bufferSize}
         *     elements pushed via {@link Many#tryEmitNext(Object)} before the first {@link Subscriber} is registered.</li>
         *     <li>Backpressure : this sink honors downstream demand by conforming to the lowest demand in case
         *     of multiple subscribers.<br>If the difference between multiple subscribers is too high compared to {@code bufferSize}:
         *          <ul><li>{@link Many#tryEmitNext(Object) tryEmitNext} will return {@link Emission#FAIL_OVERFLOW}</li>
         *          <li>{@link Many#emitNext(Object) emitNext} will terminate the sink by {@link Many#emitError(Throwable) emitting}
         *          an {@link Exceptions#failWithOverflow() overflow error}.</li></ul>
         *     </li>
         *     <li>Replaying: No replay of values seen by earlier subscribers. Only forwards to a {@link Subscriber}
         *     the elements that have been pushed to the sink AFTER this subscriber was subscribed, or elements
         *     that have been buffered due to backpressure/warm up.</li>
         * </ul>
         * <p>
         * <img class="marble" src="doc-files/marbles/sinkWarmup.svg" alt="">
         *
         * @param bufferSize
         * 		the maximum queue size
         * @param autoCancel
         * 		should the sink fully shutdowns (not publishing anymore) when the last subscriber cancels
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> onBackpressureBuffer([CtParameterImpl][CtTypeReferenceImpl]int bufferSize, [CtParameterImpl][CtTypeReferenceImpl]boolean autoCancel);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: fail fast on {@link Many#tryEmitNext(Object) tryEmitNext}.</li>
         *     <li>Backpressure : notify the caller with {@link Emission#FAIL_OVERFLOW} if any of the subscribers
         *     cannot process an element, failing fast and backing off from emitting the element at all (all or nothing).
         * 	   From the perspective of subscribers, data is dropped and never seen but they are not terminated.
         *     </li>
         *     <li>Replaying: No replay of elements. Only forwards to a {@link Subscriber} the elements that
         *     have been pushed to the sink AFTER this subscriber was subscribed, provided all of the subscribers
         *     have demand.</li>
         * </ul>
         * <p>
         * <img class="marble" src="doc-files/marbles/sinkDirectAllOrNothing.svg" alt="">
         *
         * @param <T>
         * 		the type of elements to emit
         * @return a multicast {@link Sinks.Many} that "drops" in case any subscriber is too slow
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> directAllOrNothing();

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: fail fast on {@link Many#tryEmitNext(Object) tryEmitNext}.</li>
         *     <li>Backpressure : notify the caller with {@link Emission#FAIL_OVERFLOW} if <strong>none</strong>
         *     of the subscribers can process an element. Otherwise, it ignores slow subscribers and emits the
         *     element to fast ones as a best effort. From the perspective of slow subscribers, data is dropped
         *     and never seen, but they are not terminated.
         *     </li>
         *     <li>Replaying: No replay of elements. Only forwards to a {@link Subscriber} the elements that
         *     have been pushed to the sink AFTER this subscriber was subscribed.</li>
         * </ul>
         * <p>
         * <img class="marble" src="doc-files/marbles/sinkDirectBestEffort.svg" alt="">
         *
         * @param <T>
         * 		the type of elements to emit
         * @return a multicast {@link Sinks.Many} that "drops" in case of no demand from any subscriber
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> directBestEffort();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Provides multicast with history/replay capacity : 1 sink, N {@link Subscriber}
     */
    public interface MulticastReplaySpec {
        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: all elements pushed to this sink are remembered,
         *     even when there is no subscriber.</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying: all elements pushed to this sink are replayed to new subscribers.</li>
         * </ul>
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> all();

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: all elements pushed to this sink are remembered,
         *     even when there is no subscriber.</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying: all elements pushed to this sink are replayed to new subscribers.</li>
         * </ul>
         *
         * @param batchSize
         * 		the underlying buffer will optimize storage by linked arrays of given size
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> all([CtParameterImpl][CtTypeReferenceImpl]int batchSize);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: the latest element pushed to this sink are remembered,
         *     even when there is no subscriber. Older elements are discarded</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying: the latest element pushed to this sink is replayed to new subscribers.</li>
         * </ul>
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> latest();

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: the latest element pushed to this sink are remembered,
         *     even when there is no subscriber.</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying: the latest element pushed to this sink is replayed to new subscribers. If none the default value is replayed</li>
         * </ul>
         *
         * @param value
         * 		default value if there is no latest element to replay
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> latestOrDefault([CtParameterImpl][CtTypeParameterReferenceImpl]T value);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: up to {@param historySize} elements pushed to this sink are remembered,
         *     even when there is no subscriber. Older elements are discarded</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying:  up to {@param historySize} elements pushed to this sink are replayed to new subscribers.
         *     Older elements are discarded.</li>
         * </ul>
         *
         * @param historySize
         * 		maximum number of elements able to replayed
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> limit([CtParameterImpl][CtTypeReferenceImpl]int historySize);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: up to {@param historySize} elements pushed to this sink are remembered,
         *     even when there is no subscriber. Older elements are discarded</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying:  up to {@param historySize} elements pushed to this sink are replayed to new subscribers.
         *     Older elements are discarded.</li>
         * </ul>
         *
         * @param maxAge
         * 		maximum retention time for elements to be retained
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> limit([CtParameterImpl][CtTypeReferenceImpl]java.time.Duration maxAge);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: all elements pushed to this sink are remembered until their {@param maxAge} is reached,
         *     even when there is no subscriber. Older elements are discarded</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying:  up to {@param historySize} elements pushed to this sink are replayed to new subscribers.
         *     Older elements are discarded.</li>
         * </ul>
         * Note: Age is checked when a signal occurs, not using a background task.
         *
         * @param maxAge
         * 		maximum retention time for elements to be retained
         * @param scheduler
         * 		a {@link Scheduler} to derive the time from
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> limit([CtParameterImpl][CtTypeReferenceImpl]java.time.Duration maxAge, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.scheduler.Scheduler scheduler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: up to {@param historySize} elements pushed to this sink are remembered,
         *     until their {@param maxAge} is reached, even when there is no subscriber. Older elements are discarded</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying:  up to {@param historySize} elements pushed to this sink are replayed to new subscribers.
         *     Older elements are discarded.</li>
         * </ul>
         * Note: Age is checked when a signal occurs, not using a background task.
         *
         * @param historySize
         * 		maximum number of elements able to replayed
         * @param maxAge
         * 		maximum retention time for elements to be retained
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> limit([CtParameterImpl][CtTypeReferenceImpl]int historySize, [CtParameterImpl][CtTypeReferenceImpl]java.time.Duration maxAge);

        [CtMethodImpl][CtJavaDocImpl]/**
         * A {@link Sinks.Many} with the following characteristics:
         * <ul>
         *     <li>Multicast</li>
         *     <li>Without {@link Subscriber}: up to {@param historySize} elements pushed to this sink are remembered,
         *     until their {@param maxAge} is reached, even when there is no subscriber. Older elements are discarded.</li>
         *     <li>Backpressure : this sink honors downstream demand of individual subscribers.</li>
         *     <li>Replaying:  up to {@param historySize} elements pushed to this sink are replayed to new subscribers.
         *     Older elements are discarded.</li>
         * </ul>
         * Note: Age is checked when a signal occurs, not using a background task.
         *
         * @param historySize
         * 		maximum number of elements able to replayed
         * @param maxAge
         * 		maximum retention time for elements to be retained
         * @param scheduler
         * 		a {@link Scheduler} to derive the time from
         */
        <[CtTypeParameterImpl]T> [CtTypeReferenceImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.Many<[CtTypeParameterReferenceImpl]T> limit([CtParameterImpl][CtTypeReferenceImpl]int historySize, [CtParameterImpl][CtTypeReferenceImpl]java.time.Duration maxAge, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.scheduler.Scheduler scheduler);
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * A base interface for standalone {@link Sinks} with {@link Flux} semantics.
     * <p>
     * The sink can be exposed to consuming code as a {@link Flux} via its {@link #asFlux()} view.
     *
     * @author Simon Baslé
     * @author Stephane Maldini
     */
    public interface Many<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]reactor.core.Scannable {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Try emitting a non-null element, generating an {@link Subscriber#onNext(Object) onNext} signal.
         * The result of the attempt is represented as an {@link Emission}, which possibly indicates error cases.
         * <p>
         * Might throw an unchecked exception in case of a fatal error downstream which cannot
         * be propagated to any asynchronous handler (aka a bubbling exception).
         *
         * @param t
         * 		the value to emit, not null
         * @return {@link Emission}
         * @see Subscriber#onNext(Object)
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitNext([CtParameterImpl][CtTypeParameterReferenceImpl]T t);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to terminate the sequence successfully, generating an {@link Subscriber#onComplete() onComplete}
         * signal. The result of the attempt is represented as an {@link Emission}, which possibly indicates error cases.
         *
         * @return {@link Emission}
         * @see Subscriber#onComplete()
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitComplete();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to fail the sequence, generating an {@link Subscriber#onError(Throwable) onError}
         * signal. The result of the attempt is represented as an {@link Emission}, which possibly indicates error cases.
         *
         * @param error
         * 		the exception to signal, not null
         * @return {@link Emission}
         * @see Subscriber#onError(Throwable)
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Emit a non-null element, generating an {@link Subscriber#onNext(Object) onNext} signal,
         * or notifies the downstream subscriber(s) of a failure to do so via {@link #emitError(Throwable)}
         * (with an {@link Exceptions#isOverflow(Throwable) overflow exception}).
         * <p>
         * Generally, {@link #tryEmitNext(Object)} is preferable since it allows a custom handling
         * of error cases, although this implies checking the returned {@link Emission} and correctly
         * acting on it (see implementation notes).
         * <p>
         * Might throw an unchecked exception in case of a fatal error downstream which cannot
         * be propagated to any asynchronous handler (aka a bubbling exception).
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitNext(Object)} and act on
        failures: {@link Emission#FAIL_OVERFLOW} should lead to {@link Operators#onDiscard(Object, Context)} followed
        by {@link #emitError(Throwable)}. {@link Emission#FAIL_CANCELLED} should lead to {@link Operators#onDiscard(Object, Context)}.
        {@link Emission#FAIL_TERMINATED} should lead to {@link Operators#onNextDropped(Object, Context)}.
         * @param t
         * 		the value to emit, not null
         * @see #tryEmitNext(Object)
         * @see Subscriber#onNext(Object)
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitNext(Object)} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitNext([CtParameterImpl][CtTypeParameterReferenceImpl]T t) [CtBlockImpl]{
            [CtInvocationImpl]emitNext([CtVariableReadImpl]t, [CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitNext([CtParameterImpl][CtTypeParameterReferenceImpl]T t, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Terminate the sequence successfully, generating an {@link Subscriber#onComplete() onComplete}
         * signal.
         * <p>
         * Generally, {@link #tryEmitComplete()} is preferable, since it allows a custom handling
         * of error cases.
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitComplete()}. Failure {@link Emission}
        don't need any particular handling where emitComplete is concerned.
         * @see #tryEmitComplete()
         * @see Subscriber#onComplete()
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitComplete()} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitComplete() [CtBlockImpl]{
            [CtInvocationImpl]emitComplete([CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitComplete([CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Fail the sequence, generating an {@link Subscriber#onError(Throwable) onError}
         * signal.
         * <p>
         * Generally, {@link #tryEmitError(Throwable)} is preferable since it allows a custom handling
         * of error cases, although this implies checking the returned {@link Emission} and correctly
         * acting on it (see implementation notes).
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitError(Throwable)} and act on
        {@link Emission#FAIL_TERMINATED} by calling {@link Operators#onErrorDropped(Throwable, Context)}.
         * @param error
         * 		the exception to signal, not null
         * @see #tryEmitError(Throwable)
         * @see Subscriber#onError(Throwable)
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitError(Throwable)} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error) [CtBlockImpl]{
            [CtInvocationImpl]emitError([CtVariableReadImpl]error, [CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Get how many {@link Subscriber Subscribers} are currently subscribed to the sink.
         * <p>
         * This is a best effort peek at the sink state, and a subsequent attempt at emitting
         * to the sink might still return {@link Emission#FAIL_ZERO_SUBSCRIBER} where relevant.
         * (generally in {@link #tryEmitNext(Object)}). Request (and lack thereof) isn't taken
         * into account, all registered subscribers are counted.
         *
         * @return the number of subscribers at the time of invocation
         */
        [CtTypeReferenceImpl]int currentSubscriberCount();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Return a {@link Flux} view of this sink. Every call returns the same instance.
         *
         * @return the {@link Flux} view associated to this {@link Sinks.Many}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeParameterReferenceImpl]T> asFlux();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * A base interface for standalone {@link Sinks} with complete-or-fail semantics.
     * <p>
     * The sink can be exposed to consuming code as a {@link Mono} via its {@link #asMono()} view.
     *
     * @param <T>
     * 		a generic type for the {@link Mono} view, allowing composition
     * @author Simon Baslé
     * @author Stephane Maldini
     */
    public interface Empty<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]reactor.core.Scannable {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to complete the {@link Mono} without a value, generating only an {@link Subscriber#onComplete() onComplete} signal.
         * The result of the attempt is represented as an {@link Emission}, which possibly indicates error cases.
         *
         * @return {@link Emission}
         * @see #emitEmpty()
         * @see Subscriber#onComplete()
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitEmpty();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to fail the {@link Mono}, generating only an {@link Subscriber#onError(Throwable) onError} signal.
         * The result of the attempt is represented as an {@link Emission}, which possibly indicates error cases.
         *
         * @param error
         * 		the exception to signal, not null
         * @return {@link Emission}
         * @see #emitError(Throwable)
         * @see Subscriber#onError(Throwable)
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Terminate the sequence successfully, generating an {@link Subscriber#onComplete() onComplete}
         * signal.
         * <p>
         * Generally, {@link #tryEmitEmpty()} is preferable, since it allows a custom handling
         * of error cases.
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitEmpty()}. Failure {@link Emission}
        don't need any particular handling where emitEmpty is concerned.
         * @see #tryEmitEmpty()
         * @see Subscriber#onComplete()
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitEmpty()} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitEmpty() [CtBlockImpl]{
            [CtInvocationImpl]emitEmpty([CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitEmpty([CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Fail the sequence, generating an {@link Subscriber#onError(Throwable) onError}
         * signal.
         * <p>
         * Generally, {@link #tryEmitError(Throwable)} is preferable since it allows a custom handling
         * of error cases, although this implies checking the returned {@link Emission} and correctly
         * acting on it (see implementation notes).
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitError(Throwable)} and act on
        {@link Emission#FAIL_TERMINATED} by calling {@link Operators#onErrorDropped(Throwable, Context)}.
         * @param error
         * 		the exception to signal, not null
         * @see #tryEmitError(Throwable)
         * @see Subscriber#onError(Throwable)
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitError(Throwable)} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error) [CtBlockImpl]{
            [CtInvocationImpl]emitError([CtVariableReadImpl]error, [CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable error, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Get how many {@link Subscriber Subscribers} are currently subscribed to the sink.
         * <p>
         * This is a best effort peek at the sink state, and a subsequent attempt at emitting
         * to the sink might still return {@link Emission#FAIL_ZERO_SUBSCRIBER} where relevant.
         * Request (and lack thereof) isn't taken into account, all registered subscribers are counted.
         *
         * @return the number of active subscribers at the time of invocation
         */
        [CtTypeReferenceImpl]int currentSubscriberCount();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Return a {@link Mono} view of this sink. Every call returns the same instance.
         *
         * @return the {@link Mono} view associated to this {@link Sinks.One}
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Mono<[CtTypeParameterReferenceImpl]T> asMono();
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * A base interface for standalone {@link Sinks} with {@link Mono} semantics.
     * <p>
     * The sink can be exposed to consuming code as a {@link Mono} via its {@link #asMono()} view.
     *
     * @author Simon Baslé
     * @author Stephane Maldini
     */
    public interface One<[CtTypeParameterImpl]T> extends [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Empty<[CtTypeParameterReferenceImpl]T> {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to complete the {@link Mono} with an element, generating an {@link Subscriber#onNext(Object) onNext} signal
         * immediately followed by an {@link Subscriber#onComplete() onComplete} signal. A {@code null} value
         * will only trigger the onComplete. The result of the attempt is represented as an {@link Emission},
         * which possibly indicates error cases.
         * <p>
         * Might throw an unchecked exception in case of a fatal error downstream which cannot
         * be propagated to any asynchronous handler (aka a bubbling exception).
         *
         * @param value
         * 		the value to emit and complete with, or {@code null} to only trigger an onComplete
         * @return {@link Emission}
         * @see #emitValue(Object)
         * @see Subscriber#onNext(Object)
         * @see Subscriber#onComplete()
         */
        [CtTypeReferenceImpl]reactor.core.publisher.Sinks.Emission tryEmitValue([CtParameterImpl][CtAnnotationImpl]@reactor.util.annotation.Nullable
        [CtTypeParameterReferenceImpl]T value);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Emit a non-null element, generating an {@link Subscriber#onNext(Object) onNext} signal
         * immediately followed by an {@link Subscriber#onComplete() onComplete} signal,
         * or notifies the downstream subscriber(s) of a failure to do so via {@link #emitError(Throwable)}
         * (with an {@link Exceptions#isOverflow(Throwable) overflow exception}).
         * <p>
         * Generally, {@link #tryEmitValue(Object)} is preferable since it allows a custom handling
         * of error cases, although this implies checking the returned {@link Emission} and correctly
         * acting on it (see implementation notes).
         * <p>
         * Might throw an unchecked exception in case of a fatal error downstream which cannot
         * be propagated to any asynchronous handler (aka a bubbling exception).
         *
         * @implNote Implementors should typically delegate to {@link #tryEmitValue (Object)} and act on
        failures: {@link Emission#FAIL_OVERFLOW} should lead to {@link Operators#onDiscard(Object, Context)} followed
        by {@link #emitError(Throwable)}. {@link Emission#FAIL_CANCELLED} should lead to {@link Operators#onDiscard(Object, Context)}.
        {@link Emission#FAIL_TERMINATED} should lead to {@link Operators#onNextDropped(Object, Context)}.
         * @param value
         * 		the value to emit and complete with, or {@code null} to only trigger an onComplete
         * @see #tryEmitValue(Object)
         * @see Subscriber#onNext(Object)
         * @see Subscriber#onComplete()
         * @deprecated to be removed shortly after 3.4.0-RC1. Use {@link #tryEmitValue(Object)} and handle the result.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        default [CtTypeReferenceImpl]void emitValue([CtParameterImpl][CtAnnotationImpl]@reactor.util.annotation.Nullable
        [CtTypeParameterReferenceImpl]T value) [CtBlockImpl]{
            [CtInvocationImpl]emitValue([CtVariableReadImpl]value, [CtFieldReadImpl][CtTypeAccessImpl]reactor.core.publisher.Sinks.EmitFailureHandler.[CtFieldReferenceImpl]FAIL_FAST);
        }

        [CtMethodImpl][CtTypeReferenceImpl]void emitValue([CtParameterImpl][CtAnnotationImpl]@reactor.util.annotation.Nullable
        [CtTypeParameterReferenceImpl]T value, [CtParameterImpl][CtTypeReferenceImpl]reactor.core.publisher.Sinks.EmitFailureHandler failureHandler);
    }
}
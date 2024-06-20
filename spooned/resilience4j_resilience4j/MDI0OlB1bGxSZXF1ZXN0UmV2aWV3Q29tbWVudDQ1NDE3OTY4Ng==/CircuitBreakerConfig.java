[CompilationUnitImpl][CtCommentImpl]/* Copyright 2016 Robert Winkler

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
[CtPackageDeclarationImpl]package io.github.resilience4j.circuitbreaker;
[CtUnresolvedImport]import io.github.resilience4j.core.lang.Nullable;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import io.github.resilience4j.core.IntervalFunction;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import io.github.resilience4j.core.predicate.PredicateCreator;
[CtImportImpl]import java.time.Clock;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtJavaDocImpl]/**
 * A {@link CircuitBreakerConfig} configures a {@link CircuitBreaker}
 */
public class CircuitBreakerConfig {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_FAILURE_RATE_THRESHOLD = [CtLiteralImpl]50;[CtCommentImpl]// Percentage


    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_SLOW_CALL_RATE_THRESHOLD = [CtLiteralImpl]100;[CtCommentImpl]// Percentage


    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_WAIT_DURATION_IN_OPEN_STATE = [CtLiteralImpl]60;[CtCommentImpl]// Seconds


    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE = [CtLiteralImpl]10;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_MINIMUM_NUMBER_OF_CALLS = [CtLiteralImpl]100;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_SLIDING_WINDOW_SIZE = [CtLiteralImpl]100;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_SLOW_CALL_DURATION_THRESHOLD = [CtLiteralImpl]60;[CtCommentImpl]// Seconds


    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_WAIT_DURATION_IN_HALF_OPEN_STATE = [CtLiteralImpl]0;[CtCommentImpl]// Seconds. It is an optional parameter


    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType DEFAULT_SLIDING_WINDOW_TYPE = [CtFieldReadImpl][CtTypeAccessImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.[CtFieldReferenceImpl]COUNT_BASED;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]boolean DEFAULT_WRITABLE_STACK_TRACE_ENABLED = [CtLiteralImpl]true;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> DEFAULT_RECORD_EXCEPTION_PREDICATE = [CtLambdaImpl]([CtParameterImpl]java.lang.Throwable throwable) -> [CtLiteralImpl]true;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> DEFAULT_IGNORE_EXCEPTION_PREDICATE = [CtLambdaImpl]([CtParameterImpl]java.lang.Throwable throwable) -> [CtLiteralImpl]false;

    [CtFieldImpl][CtCommentImpl]// The default Function to return current time
    private static final [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.time.Clock, [CtTypeReferenceImpl]java.lang.Long> DEFAULT_CURRENT_TIME_FUNCTION = [CtLambdaImpl]([CtParameterImpl]java.time.Clock clock) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.concurrent.TimeUnit DEFAULT_CURRENT_TIME_UNIT = [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]NANOSECONDS;

    [CtFieldImpl][CtCommentImpl]// The default exception predicate counts all exceptions as failures.
    private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> recordExceptionPredicate = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_RECORD_EXCEPTION_PREDICATE;

    [CtFieldImpl][CtCommentImpl]// The default exception predicate ignores no exceptions.
    private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> ignoreExceptionPredicate = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_IGNORE_EXCEPTION_PREDICATE;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.time.Clock, [CtTypeReferenceImpl]java.lang.Long> currentTimeFunction = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_CURRENT_TIME_FUNCTION;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.TimeUnit currentTimeUnit = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_CURRENT_TIME_UNIT;

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>[] recordExceptions = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    private [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>[] ignoreExceptions = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];

    [CtFieldImpl]private [CtTypeReferenceImpl]float failureRateThreshold = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_FAILURE_RATE_THRESHOLD;

    [CtFieldImpl]private [CtTypeReferenceImpl]int permittedNumberOfCallsInHalfOpenState = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE;

    [CtFieldImpl]private [CtTypeReferenceImpl]int slidingWindowSize = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_SIZE;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType slidingWindowType = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_TYPE;

    [CtFieldImpl]private [CtTypeReferenceImpl]int minimumNumberOfCalls = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_MINIMUM_NUMBER_OF_CALLS;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean writableStackTraceEnabled = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_WRITABLE_STACK_TRACE_ENABLED;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean automaticTransitionFromOpenToHalfOpenEnabled = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.github.resilience4j.core.IntervalFunction waitIntervalFunctionInOpenState = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.core.IntervalFunction.of([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_WAIT_DURATION_IN_OPEN_STATE));

    [CtFieldImpl]private [CtTypeReferenceImpl]float slowCallRateThreshold = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLOW_CALL_RATE_THRESHOLD;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.Duration slowCallDurationThreshold = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD);

    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.Duration maxWaitDurationInHalfOpenState = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_WAIT_DURATION_IN_HALF_OPEN_STATE);

    [CtConstructorImpl]private CircuitBreakerConfig() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a builder to create a custom CircuitBreakerConfig.
     *
     * @return a {@link Builder}
     */
    public static [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder custom() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a builder to create a custom CircuitBreakerConfig based on another
     * CircuitBreakerConfig.
     *
     * @return a {@link Builder}
     */
    public static [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder from([CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig baseConfig) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder([CtVariableReadImpl]baseConfig);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a default CircuitBreaker configuration.
     *
     * @return a default CircuitBreaker configuration.
     */
    public static [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig ofDefaults() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder().build();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]float getFailureRateThreshold() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]failureRateThreshold;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @deprecated since 1.2.0 You should use {@link #getWaitIntervalFunctionInOpenState()} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.time.Duration getWaitDurationInOpenState() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtInvocationImpl][CtFieldReadImpl]waitIntervalFunctionInOpenState.apply([CtLiteralImpl]1));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an interval function which controls how long the CircuitBreaker should stay open,
     * before it switches to half open.
     *
     * @return the CircuitBreakerConfig.Builder
     */
    public [CtTypeReferenceImpl]io.github.resilience4j.core.IntervalFunction getWaitIntervalFunctionInOpenState() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]waitIntervalFunctionInOpenState;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getSlidingWindowSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]slidingWindowSize;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> getRecordExceptionPredicate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]recordExceptionPredicate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> getIgnoreExceptionPredicate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]ignoreExceptionPredicate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.time.Clock, [CtTypeReferenceImpl]java.lang.Long> getCurrentTimeFunction() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]currentTimeFunction;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.TimeUnit getCurrentTimeUnit() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]currentTimeUnit;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isAutomaticTransitionFromOpenToHalfOpenEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]automaticTransitionFromOpenToHalfOpenEnabled;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMinimumNumberOfCalls() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]minimumNumberOfCalls;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isWritableStackTraceEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]writableStackTraceEnabled;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getPermittedNumberOfCallsInHalfOpenState() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]permittedNumberOfCallsInHalfOpenState;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType getSlidingWindowType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]slidingWindowType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]float getSlowCallRateThreshold() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]slowCallRateThreshold;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.Duration getSlowCallDurationThreshold() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]slowCallDurationThreshold;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.Duration getMaxWaitDurationInHalfOpenState() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxWaitDurationInHalfOpenState;
    }

    [CtEnumImpl]public enum SlidingWindowType {

        [CtEnumValueImpl]TIME_BASED,
        [CtEnumValueImpl]COUNT_BASED;}

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder circuitBreakerConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"CircuitBreakerConfig {");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]"recordExceptionPredicate=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]recordExceptionPredicate);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", ignoreExceptionPredicate=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]ignoreExceptionPredicate);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", recordExceptions=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.toString([CtFieldReadImpl]recordExceptions));
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", ignoreExceptions=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.toString([CtFieldReadImpl]ignoreExceptions));
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", failureRateThreshold=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]failureRateThreshold);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", permittedNumberOfCallsInHalfOpenState=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]permittedNumberOfCallsInHalfOpenState);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", slidingWindowSize=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]slidingWindowSize);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", slidingWindowType=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]slidingWindowType);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", minimumNumberOfCalls=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]minimumNumberOfCalls);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", writableStackTraceEnabled=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]writableStackTraceEnabled);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", automaticTransitionFromOpenToHalfOpenEnabled=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]automaticTransitionFromOpenToHalfOpenEnabled);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", waitIntervalFunctionInOpenState=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]waitIntervalFunctionInOpenState);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", slowCallRateThreshold=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]slowCallRateThreshold);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]", slowCallDurationThreshold=");
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtFieldReadImpl]slowCallDurationThreshold);
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.append([CtLiteralImpl]"}");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]circuitBreakerConfig.toString();
    }

    [CtClassImpl]public static class Builder {
        [CtFieldImpl][CtAnnotationImpl]@io.github.resilience4j.core.lang.Nullable
        private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> recordExceptionPredicate;

        [CtFieldImpl][CtAnnotationImpl]@io.github.resilience4j.core.lang.Nullable
        private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> ignoreExceptionPredicate;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.time.Clock, [CtTypeReferenceImpl]java.lang.Long> currentTimeFunction = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_CURRENT_TIME_FUNCTION;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.TimeUnit currentTimeUnit = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_CURRENT_TIME_UNIT;

        [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        private [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>[] recordExceptions = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];

        [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        private [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>[] ignoreExceptions = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];

        [CtFieldImpl]private [CtTypeReferenceImpl]float failureRateThreshold = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_FAILURE_RATE_THRESHOLD;

        [CtFieldImpl]private [CtTypeReferenceImpl]int minimumNumberOfCalls = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_MINIMUM_NUMBER_OF_CALLS;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean writableStackTraceEnabled = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_WRITABLE_STACK_TRACE_ENABLED;

        [CtFieldImpl]private [CtTypeReferenceImpl]int permittedNumberOfCallsInHalfOpenState = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE;

        [CtFieldImpl]private [CtTypeReferenceImpl]int slidingWindowSize = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_SIZE;

        [CtFieldImpl]private [CtTypeReferenceImpl]io.github.resilience4j.core.IntervalFunction waitIntervalFunctionInOpenState = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.core.IntervalFunction.of([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD));

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean automaticTransitionFromOpenToHalfOpenEnabled = [CtLiteralImpl]false;

        [CtFieldImpl]private [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType slidingWindowType = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_TYPE;

        [CtFieldImpl]private [CtTypeReferenceImpl]float slowCallRateThreshold = [CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLOW_CALL_RATE_THRESHOLD;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.time.Duration slowCallDurationThreshold = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD);

        [CtFieldImpl]private [CtTypeReferenceImpl]java.time.Duration maxWaitDurationInHalfOpenState = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofSeconds([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_WAIT_DURATION_IN_HALF_OPEN_STATE);

        [CtConstructorImpl]public Builder([CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig baseConfig) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.waitIntervalFunctionInOpenState = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.waitIntervalFunctionInOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.permittedNumberOfCallsInHalfOpenState = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.permittedNumberOfCallsInHalfOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowSize = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.slidingWindowSize;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowType = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.slidingWindowType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minimumNumberOfCalls = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.minimumNumberOfCalls;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.failureRateThreshold = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.failureRateThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoreExceptions = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.ignoreExceptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recordExceptions = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.recordExceptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recordExceptionPredicate = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.recordExceptionPredicate;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoreExceptionPredicate = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.ignoreExceptionPredicate;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentTimeFunction = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.currentTimeFunction;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentTimeUnit = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.currentTimeUnit;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.automaticTransitionFromOpenToHalfOpenEnabled = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.automaticTransitionFromOpenToHalfOpenEnabled;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slowCallRateThreshold = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.slowCallRateThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slowCallDurationThreshold = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.slowCallDurationThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxWaitDurationInHalfOpenState = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.maxWaitDurationInHalfOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.writableStackTraceEnabled = [CtFieldReadImpl][CtVariableReadImpl]baseConfig.writableStackTraceEnabled;
        }

        [CtConstructorImpl]public Builder() [CtBlockImpl]{
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the failure rate threshold in percentage. If the failure rate is equal to or
         * greater than the threshold, the CircuitBreaker transitions to open and starts
         * short-circuiting calls.
         * <p>
         * The threshold must be greater than 0 and not greater than 100. Default value is 50
         * percentage.
         *
         * @param failureRateThreshold
         * 		the failure rate threshold in percentage
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code failureRateThreshold <= 0 ||
         * 		failureRateThreshold > 100}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder failureRateThreshold([CtParameterImpl][CtTypeReferenceImpl]float failureRateThreshold) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]failureRateThreshold <= [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]failureRateThreshold > [CtLiteralImpl]100)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"failureRateThreshold must be between 1 and 100");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.failureRateThreshold = [CtVariableReadImpl]failureRateThreshold;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a threshold in percentage. The CircuitBreaker considers a call as slow when
         * the call duration is greater than {@link #slowCallDurationThreshold(Duration)}. When the
         * percentage of slow calls is equal to or greater than the threshold, the CircuitBreaker
         * transitions to open and starts short-circuiting calls.
         *
         * <p>
         * The threshold must be greater than 0 and not greater than 100. Default value is 100
         * percentage which means that all recorded calls must be slower than {@link #slowCallDurationThreshold(Duration)}.
         *
         * @param slowCallRateThreshold
         * 		the slow calls threshold in percentage
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code slowCallRateThreshold <= 0 ||
         * 		slowCallRateThreshold > 100}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder slowCallRateThreshold([CtParameterImpl][CtTypeReferenceImpl]float slowCallRateThreshold) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]slowCallRateThreshold <= [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]slowCallRateThreshold > [CtLiteralImpl]100)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"slowCallRateThreshold must be between 1 and 100");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slowCallRateThreshold = [CtVariableReadImpl]slowCallRateThreshold;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Enables writable stack traces. When set to false, {@link Exception#getStackTrace()}
         * returns a zero length array. This may be used to reduce log spam when the circuit breaker
         * is open as the cause of the exceptions is already known (the circuit breaker is
         * short-circuiting calls).
         *
         * @param writableStackTraceEnabled
         * 		the flag to enable writable stack traces.
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder writableStackTraceEnabled([CtParameterImpl][CtTypeReferenceImpl]boolean writableStackTraceEnabled) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.writableStackTraceEnabled = [CtVariableReadImpl]writableStackTraceEnabled;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures an interval function with a fixed wait duration which controls how long the
         * CircuitBreaker should stay open, before it switches to half open. Default value is 60
         * seconds.
         *
         * @param waitDurationInOpenState
         * 		the wait duration which specifies how long the
         * 		CircuitBreaker should stay open
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code waitDurationInOpenState.toMillis() < 1}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder waitDurationInOpenState([CtParameterImpl][CtTypeReferenceImpl]java.time.Duration waitDurationInOpenState) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long waitDurationInMillis = [CtInvocationImpl][CtVariableReadImpl]waitDurationInOpenState.toMillis();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]waitDurationInMillis < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"waitDurationInOpenState must be at least 1[ms]");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.waitIntervalFunctionInOpenState = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.core.IntervalFunction.of([CtVariableReadImpl]waitDurationInMillis);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures an interval function which controls how long the CircuitBreaker should stay
         * open, before it switches to half open. The default interval function returns a fixed wait
         * duration of 60 seconds.
         * <p>
         * A custom interval function is useful if you need an exponential backoff algorithm.
         *
         * @param waitIntervalFunctionInOpenState
         * 		Interval function that returns wait time as a
         * 		function of attempts
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder waitIntervalFunctionInOpenState([CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.core.IntervalFunction waitIntervalFunctionInOpenState) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.waitIntervalFunctionInOpenState = [CtVariableReadImpl]waitIntervalFunctionInOpenState;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the duration threshold above which calls are considered as slow and increase
         * the slow calls percentage. Default value is 60 seconds.
         *
         * @param slowCallDurationThreshold
         * 		the duration above which calls are considered as slow
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code slowCallDurationThreshold.toNanos() < 1}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder slowCallDurationThreshold([CtParameterImpl][CtTypeReferenceImpl]java.time.Duration slowCallDurationThreshold) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]slowCallDurationThreshold.toNanos() < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"slowCallDurationThreshold must be at least 1[ns]");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slowCallDurationThreshold = [CtVariableReadImpl]slowCallDurationThreshold;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures CircuitBreaker with a fixed wait duration which controls how long the
         * CircuitBreaker should stay in Half Open state, before it switches to open. This is an
         * optional parameter.
         *
         * By default CircuitBreaker will stay in Half Open state until
         * {@code minimumNumberOfCalls} is completed with either success or failure.
         *
         * @param maxWaitDurationInHalfOpenState
         * 		the wait duration which specifies how long the
         * 		CircuitBreaker should stay in Half Open
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code waitDurationInOpenState.toMillis() < 1000}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder maxWaitDurationInHalfOpenState([CtParameterImpl][CtTypeReferenceImpl]java.time.Duration maxWaitDurationInHalfOpenState) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]maxWaitDurationInHalfOpenState.toMillis() < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"maxWaitDurationInHalfOpenState must be at least 1[ms]");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxWaitDurationInHalfOpenState = [CtVariableReadImpl]maxWaitDurationInHalfOpenState;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the number of permitted calls when the CircuitBreaker is half open.
         * <p>
         * The size must be greater than 0. Default size is 10.
         *
         * @param permittedNumberOfCallsInHalfOpenState
         * 		the permitted number of calls when the
         * 		CircuitBreaker is half open
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code permittedNumberOfCallsInHalfOpenState < 1}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder permittedNumberOfCallsInHalfOpenState([CtParameterImpl][CtTypeReferenceImpl]int permittedNumberOfCallsInHalfOpenState) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]permittedNumberOfCallsInHalfOpenState < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"permittedNumberOfCallsInHalfOpenState must be greater than 0");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.permittedNumberOfCallsInHalfOpenState = [CtVariableReadImpl]permittedNumberOfCallsInHalfOpenState;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @deprecated Use {@link #permittedNumberOfCallsInHalfOpenState(int)} instead.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder ringBufferSizeInHalfOpenState([CtParameterImpl][CtTypeReferenceImpl]int ringBufferSizeInHalfOpenState) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ringBufferSizeInHalfOpenState < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"ringBufferSizeInHalfOpenState must be greater than 0");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.permittedNumberOfCallsInHalfOpenState = [CtVariableReadImpl]ringBufferSizeInHalfOpenState;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @deprecated Use {@link #slidingWindow(int, int, SlidingWindowType)} instead.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder ringBufferSizeInClosedState([CtParameterImpl][CtTypeReferenceImpl]int ringBufferSizeInClosedState) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ringBufferSizeInClosedState < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"ringBufferSizeInClosedState must be greater than 0");
            }
            [CtReturnImpl]return [CtInvocationImpl]slidingWindow([CtVariableReadImpl]ringBufferSizeInClosedState, [CtVariableReadImpl]ringBufferSizeInClosedState, [CtFieldReadImpl][CtTypeAccessImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.[CtFieldReferenceImpl]COUNT_BASED);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the sliding window which is used to record the outcome of calls when the
         * CircuitBreaker is closed. {@code slidingWindowSize} configures the size of the sliding
         * window. Sliding window can either be count-based or time-based, specified by {@code slidingWindowType}. {@code minimumNumberOfCalls} configures the minimum number of calls
         * which are required (per sliding window period) before the CircuitBreaker can calculate
         * the error rate. For example, if {@code minimumNumberOfCalls} is 10, then at least 10
         * calls must be recorded, before the failure rate can be calculated. If only 9 calls have
         * been recorded, the CircuitBreaker will not transition to open, even if all 9 calls have
         * failed.
         * <p>
         * If {@code slidingWindowSize} is 100 and {@code slidingWindowType} is COUNT_BASED, the
         * last 100 calls are recorded and aggregated. If {@code slidingWindowSize} is 10 and {@code slidingWindowType} is TIME_BASED, the calls of the last 10 seconds are recorded and
         * aggregated.
         * <p>
         * The {@code slidingWindowSize} must be greater than 0. The {@code minimumNumberOfCalls}
         * must be greater than 0. If the {@code slidingWindowType} is COUNT_BASED, the {@code minimumNumberOfCalls} may not be greater than {@code slidingWindowSize}. If a greater
         * value is provided, {@code minimumNumberOfCalls} will be equal to {@code slidingWindowSize}. If the {@code slidingWindowType} is TIME_BASED, the {@code minimumNumberOfCalls} may be any amount.
         * <p>
         * Default slidingWindowSize is 100, minimumNumberOfCalls is 100 and slidingWindowType is
         * COUNT_BASED.
         *
         * @param slidingWindowSize
         * 		the size of the sliding window when the CircuitBreaker is
         * 		closed.
         * @param minimumNumberOfCalls
         * 		the minimum number of calls that must be recorded before the
         * 		failure rate can be calculated.
         * @param slidingWindowType
         * 		the type of the sliding window. Either COUNT_BASED or
         * 		TIME_BASED.
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code slidingWindowSize < 1 || minimumNumberOfCalls
         * 		< 1}
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder slidingWindow([CtParameterImpl][CtTypeReferenceImpl]int slidingWindowSize, [CtParameterImpl][CtTypeReferenceImpl]int minimumNumberOfCalls, [CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType slidingWindowType) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slidingWindowSize < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"slidingWindowSize must be greater than 0");
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minimumNumberOfCalls < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"minimumNumberOfCalls must be greater than 0");
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slidingWindowType == [CtFieldReadImpl][CtTypeAccessImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.[CtFieldReferenceImpl]COUNT_BASED) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minimumNumberOfCalls = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]minimumNumberOfCalls, [CtVariableReadImpl]slidingWindowSize);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minimumNumberOfCalls = [CtVariableReadImpl]minimumNumberOfCalls;
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowSize = [CtVariableReadImpl]slidingWindowSize;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowType = [CtVariableReadImpl]slidingWindowType;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the size of the sliding window which is used to record the outcome of calls
         * when the CircuitBreaker is closed. {@code slidingWindowSize} configures the size of the
         * sliding window.
         * <p>
         * The {@code slidingWindowSize} must be greater than 0.
         * <p>
         * Default slidingWindowSize is 100.
         *
         * @param slidingWindowSize
         * 		the size of the sliding window when the CircuitBreaker is
         * 		closed.
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code slidingWindowSize < 1}
         * @see #slidingWindow(int, int, SlidingWindowType)
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder slidingWindowSize([CtParameterImpl][CtTypeReferenceImpl]int slidingWindowSize) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slidingWindowSize < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"slidingWindowSize must be greater than 0");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowSize = [CtVariableReadImpl]slidingWindowSize;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the minimum number of calls which are required (per sliding window period)
         * before the CircuitBreaker can calculate the error rate. For example, if {@code minimumNumberOfCalls} is 10, then at least 10 calls must be recorded, before the failure
         * rate can be calculated. If only 9 calls have been recorded, the CircuitBreaker will not
         * transition to open, even if all 9 calls have failed.
         * <p>
         * Default minimumNumberOfCalls is 100
         *
         * @param minimumNumberOfCalls
         * 		the minimum number of calls that must be recorded before the
         * 		failure rate can be calculated.
         * @return the CircuitBreakerConfig.Builder
         * @throws IllegalArgumentException
         * 		if {@code minimumNumberOfCalls < 1}
         * @see #slidingWindow(int, int, SlidingWindowType)
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder minimumNumberOfCalls([CtParameterImpl][CtTypeReferenceImpl]int minimumNumberOfCalls) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minimumNumberOfCalls < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"minimumNumberOfCalls must be greater than 0");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minimumNumberOfCalls = [CtVariableReadImpl]minimumNumberOfCalls;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the type of the sliding window which is used to record the outcome of calls
         * when the CircuitBreaker is closed. Sliding window can either be count-based or
         * time-based.
         * <p>
         * Default slidingWindowType is COUNT_BASED.
         *
         * @param slidingWindowType
         * 		the type of the sliding window. Either COUNT_BASED or
         * 		TIME_BASED.
         * @return the CircuitBreakerConfig.Builder
         * @see #slidingWindow(int, int, SlidingWindowType)
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder slidingWindowType([CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType slidingWindowType) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.slidingWindowType = [CtVariableReadImpl]slidingWindowType;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @deprecated use {@link #recordException(Predicate)} instead.
         */
        [CtAnnotationImpl]@java.lang.Deprecated
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder recordFailure([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> predicate) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recordExceptionPredicate = [CtVariableReadImpl]predicate;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a Predicate which evaluates if an exception should be recorded as a failure
         * and thus increase the failure rate. The Predicate must return true if the exception
         * should count as a failure. The Predicate must return false, if the exception should count
         * as a success, unless the exception is explicitly ignored by {@link #ignoreExceptions(Class[])} or {@link #ignoreException(Predicate)}.
         *
         * @param predicate
         * 		the Predicate which evaluates if an exception should count as a failure
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder recordException([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> predicate) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recordExceptionPredicate = [CtVariableReadImpl]predicate;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a function that returns current timestamp for CircuitBreaker.
         * Default implementation uses System.nanoTime() to compute current timestamp.
         * Configure currentTimeFunction to provide different implementation to compute current timestamp.
         * <p>
         *
         * @param currentTimeFunction
         * 		function that computes current timestamp.
         * @param timeUnit
         * 		TimeUnit of timestamp returned by the function.
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder currentTimeFunction([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.time.Clock, [CtTypeReferenceImpl]java.lang.Long> currentTimeFunction, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.TimeUnit timeUnit) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentTimeUnit = [CtVariableReadImpl]timeUnit;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentTimeFunction = [CtVariableReadImpl]currentTimeFunction;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a Predicate which evaluates if an exception should be ignored and neither
         * count as a failure nor success. The Predicate must return true if the exception should be
         * ignored. The Predicate must return false, if the exception should count as a failure.
         *
         * @param predicate
         * 		the Predicate which evaluates if an exception should count as a failure
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder ignoreException([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> predicate) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoreExceptionPredicate = [CtVariableReadImpl]predicate;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a list of error classes that are recorded as a failure and thus increase the
         * failure rate. Any exception matching or inheriting from one of the list should count as a
         * failure, unless ignored via {@link #ignoreExceptions(Class[])} or {@link #ignoreException(Predicate)}.
         *
         * @param errorClasses
         * 		the error classes that are recorded
         * @return the CircuitBreakerConfig.Builder
         * @see #ignoreExceptions(Class[]) ). Ignoring an exception has priority over recording an
        exception.
        <p>
        Example: recordExceptions(Throwable.class) and ignoreExceptions(RuntimeException.class)
        would capture all Errors and checked Exceptions, and ignore RuntimeExceptions.
        <p>
        For a more sophisticated exception management use the
         * @see #recordException(Predicate) method
         */
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtAnnotationImpl]@java.lang.SafeVarargs
        public final [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder recordExceptions([CtParameterImpl][CtAnnotationImpl]@io.github.resilience4j.core.lang.Nullable
        java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>... errorClasses) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.recordExceptions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]errorClasses != [CtLiteralImpl]null) ? [CtVariableReadImpl]errorClasses : [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures a list of error classes that are ignored and thus neither count as a failure
         * nor success. Any exception matching or inheriting from one of the list will not count as
         * a failure nor success, even if marked via {@link #recordExceptions(Class[])} or {@link #recordException(Predicate)}.
         *
         * @param errorClasses
         * 		the error classes that are ignored
         * @return the CircuitBreakerConfig.Builder
         * @see #recordExceptions(Class[]) . Ignoring an exception has priority over recording an
        exception.
        <p>
        Example: ignoreExceptions(Throwable.class) and recordExceptions(Exception.class) would
        capture nothing.
        <p>
        Example: ignoreExceptions(Exception.class) and recordExceptions(Throwable.class) would
        capture Errors.
        <p>
        For a more sophisticated exception management use the
         * @see #ignoreException(Predicate) method
         */
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        [CtAnnotationImpl]@java.lang.SafeVarargs
        public final [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder ignoreExceptions([CtParameterImpl][CtAnnotationImpl]@io.github.resilience4j.core.lang.Nullable
        java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Throwable>... errorClasses) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ignoreExceptions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]errorClasses != [CtLiteralImpl]null) ? [CtVariableReadImpl]errorClasses : [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]0];
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Enables automatic transition from OPEN to HALF_OPEN state once the
         * waitDurationInOpenState has passed.
         *
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder enableAutomaticTransitionFromOpenToHalfOpen() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.automaticTransitionFromOpenToHalfOpenEnabled = [CtLiteralImpl]true;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Enables automatic transition from OPEN to HALF_OPEN state once the
         * waitDurationInOpenState has passed.
         *
         * @param enableAutomaticTransitionFromOpenToHalfOpen
         * 		the flag to enable the automatic
         * 		transitioning.
         * @return the CircuitBreakerConfig.Builder
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.Builder automaticTransitionFromOpenToHalfOpenEnabled([CtParameterImpl][CtTypeReferenceImpl]boolean enableAutomaticTransitionFromOpenToHalfOpen) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.automaticTransitionFromOpenToHalfOpenEnabled = [CtVariableReadImpl]enableAutomaticTransitionFromOpenToHalfOpen;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Builds a CircuitBreakerConfig
         *
         * @return the CircuitBreakerConfig
         */
        public [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig build() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.circuitbreaker.CircuitBreakerConfig();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.waitIntervalFunctionInOpenState = [CtFieldReadImpl]waitIntervalFunctionInOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.slidingWindowType = [CtFieldReadImpl]slidingWindowType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.slowCallDurationThreshold = [CtFieldReadImpl]slowCallDurationThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.maxWaitDurationInHalfOpenState = [CtFieldReadImpl]maxWaitDurationInHalfOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.slowCallRateThreshold = [CtFieldReadImpl]slowCallRateThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.failureRateThreshold = [CtFieldReadImpl]failureRateThreshold;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.slidingWindowSize = [CtFieldReadImpl]slidingWindowSize;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.minimumNumberOfCalls = [CtFieldReadImpl]minimumNumberOfCalls;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.permittedNumberOfCallsInHalfOpenState = [CtFieldReadImpl]permittedNumberOfCallsInHalfOpenState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.recordExceptions = [CtFieldReadImpl]recordExceptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.ignoreExceptions = [CtFieldReadImpl]ignoreExceptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.automaticTransitionFromOpenToHalfOpenEnabled = [CtFieldReadImpl]automaticTransitionFromOpenToHalfOpenEnabled;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.writableStackTraceEnabled = [CtFieldReadImpl]writableStackTraceEnabled;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.recordExceptionPredicate = [CtInvocationImpl]createRecordExceptionPredicate();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.ignoreExceptionPredicate = [CtInvocationImpl]createIgnoreFailurePredicate();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.currentTimeFunction = [CtFieldReadImpl]currentTimeFunction;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]config.currentTimeUnit = [CtFieldReadImpl]currentTimeUnit;
            [CtReturnImpl]return [CtVariableReadImpl]config;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> createIgnoreFailurePredicate() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.core.predicate.PredicateCreator.createExceptionsPredicate([CtFieldReadImpl]ignoreExceptions).map([CtLambdaImpl]([CtParameterImpl] predicate) -> [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]ignoreExceptionPredicate != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]predicate.or([CtFieldReadImpl][CtFieldReferenceImpl]ignoreExceptionPredicate) : [CtVariableReadImpl]predicate).orElseGet([CtLambdaImpl]() -> [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]ignoreExceptionPredicate != [CtLiteralImpl]null ? [CtFieldReadImpl][CtFieldReferenceImpl]ignoreExceptionPredicate : [CtTypeAccessImpl]io.github.resilience4j.circuitbreaker.DEFAULT_IGNORE_EXCEPTION_PREDICATE);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.Throwable> createRecordExceptionPredicate() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.core.predicate.PredicateCreator.createExceptionsPredicate([CtFieldReadImpl]recordExceptions).map([CtLambdaImpl]([CtParameterImpl] predicate) -> [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]recordExceptionPredicate != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]predicate.or([CtFieldReadImpl][CtFieldReferenceImpl]recordExceptionPredicate) : [CtVariableReadImpl]predicate).orElseGet([CtLambdaImpl]() -> [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]recordExceptionPredicate != [CtLiteralImpl]null ? [CtFieldReadImpl][CtFieldReferenceImpl]recordExceptionPredicate : [CtTypeAccessImpl]io.github.resilience4j.circuitbreaker.DEFAULT_RECORD_EXCEPTION_PREDICATE);
        }
    }
}
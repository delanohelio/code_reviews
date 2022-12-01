[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.

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
[CtPackageDeclarationImpl]package com.hazelcast.jet.retry;
[CtUnresolvedImport]import com.hazelcast.jet.retry.impl.RetryStrategyImpl;
[CtClassImpl][CtJavaDocImpl]/**
 * Collection of factory methods for creating the most frequently used
 * {@link RetryStrategy RetryStrategies}.
 *
 * @since 4.3
 */
public final class RetryStrategies {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int DEFAULT_MAX_ATTEMPTS = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long DEFAULT_WAIT_DURATION_MS = [CtLiteralImpl]500;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.hazelcast.jet.retry.IntervalFunction DEFAULT_INTERVAL_FUNCTION = [CtInvocationImpl][CtTypeAccessImpl]com.hazelcast.jet.retry.IntervalFunction.constant([CtFieldReadImpl]com.hazelcast.jet.retry.RetryStrategies.DEFAULT_WAIT_DURATION_MS);

    [CtConstructorImpl]private RetryStrategies() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a strategy which will not retry a failed action.
     */
    public static [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategy never() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder().maxAttempts([CtLiteralImpl]0).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a strategy which will retry failed actions indefinitely and will
     * wait for a fixed amount of time between any two subsequent attempts.
     */
    public static [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategy indefinitely([CtParameterImpl][CtTypeReferenceImpl]long intervalMillis) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder().intervalFunction([CtInvocationImpl][CtTypeAccessImpl]com.hazelcast.jet.retry.IntervalFunction.constant([CtVariableReadImpl]intervalMillis)).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a builder which can be used for setting up an arbitrarily complex
     * strategy.
     */
    public static [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder custom() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder();
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Builder for custom retry strategies.
     */
    public static final class Builder {
        [CtFieldImpl]private [CtTypeReferenceImpl]int maxAttempts = [CtFieldReadImpl]com.hazelcast.jet.retry.RetryStrategies.DEFAULT_MAX_ATTEMPTS;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.jet.retry.IntervalFunction intervalFunction = [CtFieldReadImpl]com.hazelcast.jet.retry.RetryStrategies.DEFAULT_INTERVAL_FUNCTION;

        [CtConstructorImpl]private Builder() [CtBlockImpl]{
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the maximum number of attempts
         */
        public [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder maxAttempts([CtParameterImpl][CtTypeReferenceImpl]int maxAttempts) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxAttempts = [CtVariableReadImpl]maxAttempts;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Set a function to modify the waiting interval after a failure.
         *
         * @param f
         * 		Function to modify the interval after a failure
         * @return the RetryConfig.Builder
         */
        public [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategies.Builder intervalFunction([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.jet.retry.IntervalFunction f) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.intervalFunction = [CtVariableReadImpl]f;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Constructs the actual strategy based on the properties set previously.
         */
        public [CtTypeReferenceImpl]com.hazelcast.jet.retry.RetryStrategy build() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.jet.retry.impl.RetryStrategyImpl([CtFieldReadImpl]maxAttempts, [CtFieldReadImpl]intervalFunction);
        }
    }
}
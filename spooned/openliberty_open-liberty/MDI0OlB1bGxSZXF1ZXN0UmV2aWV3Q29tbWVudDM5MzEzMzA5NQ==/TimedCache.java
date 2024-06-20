[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package com.ibm.ws.microprofile.config14.impl;
[CtUnresolvedImport]import com.ibm.ws.ffdc.annotation.FFDCIgnore;
[CtImportImpl]import java.util.concurrent.ScheduledExecutorService;
[CtUnresolvedImport]import com.ibm.websphere.ras.TraceComponent;
[CtImportImpl]import java.util.concurrent.Executors;
[CtImportImpl]import java.util.concurrent.RejectedExecutionException;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import com.ibm.websphere.ras.Tr;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import com.ibm.websphere.ras.annotation.Trivial;
[CtUnresolvedImport]import com.ibm.ws.ffdc.FFDCFilter;
[CtImportImpl]import java.util.concurrent.Future;
[CtClassImpl][CtJavaDocImpl]/**
 * Caches looked up values for a period of time.
 * <p>
 * Allows {@code null} to be used as a value, but not as a key
 * <p>
 * Uses a scheduled task to invalidate the cache after the configured period of time.
 *
 * @param K
 * 		the lookup key type
 * @param V
 * 		the value type
 */
public class TimedCache<[CtTypeParameterImpl]K, [CtTypeParameterImpl]V> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.ibm.websphere.ras.TraceComponent tc = [CtInvocationImpl][CtTypeAccessImpl]com.ibm.websphere.ras.Tr.register([CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.class);

    [CtClassImpl]private static class CachedNullValue {}

    [CtFieldImpl][CtJavaDocImpl]/**
     * Constant to represent {@code null} when stored inside the cache map
     * <p>
     * Needed because ConcurrentHashMap can't hold null values and we need to distinguish between a cached {@code null} and a missing value.
     */
    private static final [CtTypeReferenceImpl]java.lang.Object NULL_VALUE = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.CachedNullValue();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService executor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean localExecutor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]long delay;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.TimeUnit unit;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeParameterReferenceImpl]K, [CtTypeReferenceImpl]java.lang.Object> cache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean invalidationPending = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]java.util.concurrent.Future<[CtWildcardReferenceImpl]?> invalidationFuture = [CtLiteralImpl]null;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean closed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);

    [CtConstructorImpl][CtJavaDocImpl]/**
     *
     * @param executor
     * @param delay
     * @param unit
     */
    public TimedCache([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService executor, [CtParameterImpl][CtTypeReferenceImpl]long delay, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.TimeUnit unit) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]executor == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// For unit testing only
            [CtFieldWriteImpl][CtThisAccessImpl]this.executor = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.localExecutor = [CtLiteralImpl]true;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executor = [CtVariableReadImpl]executor;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.localExecutor = [CtLiteralImpl]false;
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.delay = [CtVariableReadImpl]delay;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.unit = [CtVariableReadImpl]unit;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the value associated with {@code key}. If the value is in the cache, it will be returned from the cache. Otherwise the value will be retrieved using
     * {@code lookupFunction} and stored in the cache.
     *
     * @param key
     * 		the key to use for the lookup
     * @param lookupFunction
     * 		a function which returns the value associated with the given {@code key}. Only used if the value is not in the cache.
     * @return the value
     */
    public [CtTypeParameterReferenceImpl]V get([CtParameterImpl][CtTypeParameterReferenceImpl]K key, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> lookupFunction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delay == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]lookupFunction.apply([CtVariableReadImpl]key);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object cacheValue = [CtInvocationImpl][CtFieldReadImpl]cache.get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cacheValue == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// If the object is not in the cache, find it with the lookup function and store it in the cache
            [CtVariableWriteImpl]cacheValue = [CtInvocationImpl]toCacheValue([CtInvocationImpl][CtVariableReadImpl]lookupFunction.apply([CtVariableReadImpl]key));
            [CtInvocationImpl][CtFieldReadImpl]cache.put([CtVariableReadImpl]key, [CtVariableReadImpl]cacheValue);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.ibm.websphere.ras.TraceComponent.isAnyTracingEnabled() && [CtInvocationImpl][CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.tc.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.ibm.websphere.ras.Tr.debug([CtThisAccessImpl]this, [CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.tc, [CtLiteralImpl]"Item added to timed cache", [CtVariableReadImpl]key, [CtVariableReadImpl]cacheValue);
            }
            [CtInvocationImpl]requestInvalidation();
        }
        [CtReturnImpl]return [CtInvocationImpl]fromCacheValue([CtVariableReadImpl]cacheValue);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Prepares a value to be put into the cache by converting {@code null} to {@link #NULL_VALUE}.
     *
     * @param value
     * 		the value to be cached
     * @return the object to store in the cache map
     */
    [CtAnnotationImpl]@com.ibm.websphere.ras.annotation.Trivial
    private [CtTypeReferenceImpl]java.lang.Object toCacheValue([CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null ? [CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.NULL_VALUE : [CtVariableReadImpl]value;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts the value from the object stored in the cache by converting {@link #NULL_VALUE} to {@code null}
     *
     * @param cacheValue
     * 		the object from the cache map
     * @return the value stored in the cache
     */
    [CtCommentImpl]// Safe because we ensure that all values in the map are of type V except NULL_VALUE
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@com.ibm.websphere.ras.annotation.Trivial
    private [CtTypeParameterReferenceImpl]V fromCacheValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object cacheValue) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]cacheValue == [CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.NULL_VALUE ? [CtLiteralImpl]null : [CtVariableReadImpl](([CtTypeParameterReferenceImpl]V) (cacheValue));
    }

    [CtMethodImpl][CtAnnotationImpl]@com.ibm.ws.ffdc.annotation.FFDCIgnore([CtFieldReadImpl]java.util.concurrent.RejectedExecutionException.class)
    private [CtTypeReferenceImpl]void requestInvalidation() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]closed.get()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]invalidationPending.compareAndSet([CtLiteralImpl]false, [CtLiteralImpl]true)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]invalidationFuture = [CtInvocationImpl][CtFieldReadImpl]executor.schedule([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::invalidate, [CtFieldReadImpl]delay, [CtFieldReadImpl]unit);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]closed.get()) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// If the cache was closed while we were scheduling an invalidation, we should now cancel it
                    [CtFieldReadImpl]invalidationFuture.cancel([CtLiteralImpl]false);
                    [CtInvocationImpl][CtFieldReadImpl]invalidationPending.compareAndSet([CtLiteralImpl]true, [CtLiteralImpl]false);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.concurrent.RejectedExecutionException e) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Didn't actually start task
                [CtFieldReadImpl]invalidationPending.compareAndSet([CtLiteralImpl]true, [CtLiteralImpl]false);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.ibm.wsspi.kernel.service.utils.FrameworkState.isStopping()) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// This shouldn't happen unless the server is stopping
                    [CtTypeAccessImpl]com.ibm.websphere.ras.Tr.error([CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.tc, [CtLiteralImpl]"failed.to.schedule.cache.invalidation.CWMCG0301E", [CtVariableReadImpl]e);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sourceId = [CtInvocationImpl][CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.class.getName();
                    [CtInvocationImpl][CtTypeAccessImpl]com.ibm.ws.ffdc.FFDCFilter.processException([CtVariableReadImpl]e, [CtVariableReadImpl]sourceId, [CtLiteralImpl]"requestInvalidation.rejectedExecution", [CtThisAccessImpl]this);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Probably didn't actually start task
                [CtFieldReadImpl]invalidationPending.compareAndSet([CtLiteralImpl]true, [CtLiteralImpl]false);
                [CtInvocationImpl][CtTypeAccessImpl]com.ibm.websphere.ras.Tr.error([CtFieldReadImpl]com.ibm.ws.microprofile.config14.impl.TimedCache.tc, [CtLiteralImpl]"failed.to.schedule.cache.invalidation.CWMCG0301E", [CtVariableReadImpl]t);
                [CtThrowImpl]throw [CtVariableReadImpl]t;
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void invalidate() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]invalidationPending.compareAndSet([CtLiteralImpl]true, [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]cache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]closed.set([CtLiteralImpl]true);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]invalidationFuture != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]invalidationFuture.cancel([CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtFieldReadImpl]localExecutor) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]executor.shutdown();
        }
    }
}
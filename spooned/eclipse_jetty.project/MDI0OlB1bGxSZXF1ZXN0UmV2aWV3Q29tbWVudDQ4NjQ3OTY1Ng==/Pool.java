[CompilationUnitImpl][CtPackageDeclarationImpl]package org.eclipse.jetty.util;
[CtImportImpl]import java.io.Closeable;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.eclipse.jetty.util.component.Dumpable;
[CtImportImpl]import java.util.ListIterator;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtUnresolvedImport]import org.eclipse.jetty.util.log.Log;
[CtUnresolvedImport]import org.eclipse.jetty.util.thread.Locker;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.concurrent.ThreadLocalRandom;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.eclipse.jetty.util.component.DumpableCollection;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.eclipse.jetty.util.log.Logger;
[CtImportImpl]import java.util.concurrent.CopyOnWriteArrayList;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 * A fast pool of objects, with optional support for
 * multiplexing, max usage count and several optimized strategies plus
 * an optional {@link ThreadLocal} cache of the last release entry.
 * <p>
 * When the method {@link #close()} is called, all {@link Closeable}s in the pool
 * are also closed.
 * </p>
 *
 * @param <T>
 */
public class Pool<[CtTypeParameterImpl]T> implements [CtTypeReferenceImpl]java.lang.AutoCloseable , [CtTypeReferenceImpl]org.eclipse.jetty.util.component.Dumpable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.eclipse.jetty.util.log.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.log.Log.getLogger([CtFieldReadImpl]org.eclipse.jetty.util.Pool.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry> entries = [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry>()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.ListIterator<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry> listIterator([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.listIterator([CtVariableReadImpl]index);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IndexOutOfBoundsException e) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]listIterator();
            }
        }
    };

    [CtFieldImpl][CtCommentImpl]/* The cache is used to avoid hammering on the first index of the entry list.
    Caches can become poisoned (i.e.: containing entries that are in use) when
    the release isn't done by the acquiring thread or when the entry pool is
    undersized compared to the load applied on it.
    When an entry can't be found in the cache, the global list is iterated
    normally so the cache has no visible effect besides performance.
     */
    private final [CtTypeReferenceImpl]org.eclipse.jetty.util.thread.Locker locker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jetty.util.thread.Locker();

    [CtFieldImpl]private final [CtTypeReferenceImpl]int maxEntries;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger pending = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger();

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.eclipse.jetty.util.Pool.Strategy strategy;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry> cache;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger next;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]boolean closed;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]int maxMultiplex = [CtLiteralImpl]1;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]int maxUsageCount = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtEnumImpl]public enum Strategy {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The Linear strategy looks for an entry always starting from the first entry.
         * It will favour the early entries in the pool, but may contend on them more.
         */
        LINEAR,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The Random strategy looks for an entry by iterating from a random starting
         * index.  No entries are favoured and contention is reduced.
         */
        RANDOM,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The Thread ID strategy uses the {@link Thread#getId()} of the current thread
         * to select a starting point for an entry search.  Whilst not as performant as
         * using the {@link ThreadLocal} cache, it may be suitable when the pool is substantially smaller
         * than the number of available threads.
         * No entries are favoured and contention is reduced.
         */
        THREAD_ID,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The Round Robin strategy looks for an entry by iterating from a starting point
         * that is incremented on every search. This gives similar results to the
         * random strategy but with more predictable behaviour.
         * No entries are favoured and contention is reduced.
         */
        ROUND_ROBIN;}

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Construct a Pool with a specified lookup strategy and no
     * {@link ThreadLocal} cache.
     *
     * @param strategy
     * 		The strategy to used for looking up entries.
     * @param maxEntries
     * 		the maximum amount of entries that the pool will accept.
     */
    public Pool([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool.Strategy strategy, [CtParameterImpl][CtTypeReferenceImpl]int maxEntries) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]strategy, [CtVariableReadImpl]maxEntries, [CtLiteralImpl]false);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Construct a Pool with the specified thread-local cache size and
     * an optional {@link ThreadLocal} cache.
     *
     * @param strategy
     * 		The strategy to used for looking up entries.
     * @param maxEntries
     * 		the maximum amount of entries that the pool will accept.
     * @param cache
     * 		True if a {@link ThreadLocal} cache should be used to try the most recently released entry.
     */
    public Pool([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool.Strategy strategy, [CtParameterImpl][CtTypeReferenceImpl]int maxEntries, [CtParameterImpl][CtTypeReferenceImpl]boolean cache) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxEntries = [CtVariableReadImpl]maxEntries;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.strategy = [CtVariableReadImpl]strategy;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cache = [CtConditionalImpl]([CtVariableReadImpl]cache) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]next = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]strategy == [CtFieldReadImpl][CtTypeAccessImpl]org.eclipse.jetty.util.Pool.Strategy.[CtFieldReferenceImpl]ROUND_ROBIN) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger() : [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getReservedCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]pending.get();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getIdleCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entries.stream().filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.eclipse.jetty.util.Pool.Entry::isIdle).count()));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getInUseCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entries.stream().filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.eclipse.jetty.util.Pool.Entry::isInUse).count()));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMaxEntries() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxEntries;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMaxMultiplex() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxMultiplex;
    }

    [CtMethodImpl]public final [CtTypeReferenceImpl]void setMaxMultiplex([CtParameterImpl][CtTypeReferenceImpl]int maxMultiplex) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxMultiplex < [CtLiteralImpl]1)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Max multiplex must be >= 1");

        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxMultiplex = [CtVariableReadImpl]maxMultiplex;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMaxUsageCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxUsageCount;
    }

    [CtMethodImpl]public final [CtTypeReferenceImpl]void setMaxUsageCount([CtParameterImpl][CtTypeReferenceImpl]int maxUsageCount) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxUsageCount == [CtLiteralImpl]0)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Max usage count must be != 0");

        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxUsageCount = [CtVariableReadImpl]maxUsageCount;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a new disabled slot into the pool.
     * The returned entry must ultimately have the {@link Entry#enable(Object, boolean)}
     * method called or be removed via {@link Pool.Entry#remove()} or
     * {@link Pool#remove(Pool.Entry)}.
     *
     * @param allotment
     * 		the desired allotment, where each entry handles an allotment of maxMultiplex,
     * 		or a negative number to always trigger the reservation of a new entry.
     * @return a disabled entry that is contained in the pool,
    or null if the pool is closed or if the pool already contains
    {@link #getMaxEntries()} entries, or the allotment has already been reserved
     */
    public [CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry reserve([CtParameterImpl][CtTypeReferenceImpl]int allotment) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.thread.Locker.Lock l = [CtInvocationImpl][CtFieldReadImpl]locker.lock()) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]closed)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtLocalVariableImpl][CtTypeReferenceImpl]int space = [CtBinaryOperatorImpl][CtFieldReadImpl]maxEntries - [CtInvocationImpl][CtFieldReadImpl]entries.size();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]space <= [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtIfImpl][CtCommentImpl]// The pending count is an AtomicInteger that is only ever incremented here with
            [CtCommentImpl]// the lock held.  Thus the pending count can be reduced immediately after the
            [CtCommentImpl]// test below, but never incremented.  Thus the allotment limit can be enforced.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]allotment >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]pending.get() * [CtInvocationImpl]getMaxMultiplex()) >= [CtVariableReadImpl]allotment))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]null;

            [CtInvocationImpl][CtFieldReadImpl]pending.incrementAndGet();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Entry([CtInvocationImpl][CtFieldReadImpl]entries.size());
            [CtInvocationImpl][CtFieldReadImpl]entries.add([CtVariableReadImpl]entry);
            [CtReturnImpl]return [CtVariableReadImpl]entry;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Acquire the entry from the pool at the specified index. This method bypasses the thread-local mechanism.
     *
     * @param idx
     * 		the index of the entry to acquire.
     * @return the specified entry or null if there is none at the specified index or if it is not available.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry acquireAt([CtParameterImpl][CtTypeReferenceImpl]int idx) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]closed)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry = [CtInvocationImpl][CtFieldReadImpl]entries.get([CtVariableReadImpl]idx);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]entry.tryAcquire())[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]entry;

        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IndexOutOfBoundsException e) [CtBlockImpl]{
            [CtCommentImpl]// no entry at that index
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Acquire an entry from the pool.
     * Only enabled entries will be returned from this method and their enable method must not be called.
     *
     * @return an entry from the pool or null if none is available.
     */
    public [CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry acquire() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]closed)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtFieldReadImpl]entries.size();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]size == [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cache != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry = [CtInvocationImpl][CtFieldReadImpl]cache.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]entry != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]entry.tryAcquire())[CtBlockImpl]
                [CtReturnImpl]return [CtVariableReadImpl]entry;

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl]startIndex([CtVariableReadImpl]size);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int tries = [CtVariableReadImpl]size; [CtBinaryOperatorImpl][CtUnaryOperatorImpl]([CtVariableWriteImpl]tries--) > [CtLiteralImpl]0;) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry = [CtInvocationImpl][CtFieldReadImpl]entries.get([CtVariableReadImpl]index);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]entry != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]entry.tryAcquire())[CtBlockImpl]
                    [CtReturnImpl]return [CtVariableReadImpl]entry;

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IndexOutOfBoundsException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.eclipse.jetty.util.Pool.LOGGER.ignore([CtVariableReadImpl]e);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]index = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]index + [CtLiteralImpl]1) % [CtVariableReadImpl]size;
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int startIndex([CtParameterImpl][CtTypeReferenceImpl]int size) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtFieldReadImpl]strategy) {
            [CtCaseImpl]case LINEAR :
                [CtReturnImpl]return [CtLiteralImpl]0;
            [CtCaseImpl]case RANDOM :
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.ThreadLocalRandom.current().nextInt([CtVariableReadImpl]size);
            [CtCaseImpl]case ROUND_ROBIN :
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]next.getAndUpdate([CtLambdaImpl]([CtParameterImpl]int c) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]c + [CtLiteralImpl]1)) % [CtVariableReadImpl]size;
            [CtCaseImpl]case THREAD_ID :
                [CtReturnImpl]return [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getId() % [CtVariableReadImpl]size));
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Utility method to acquire an entry from the pool,
     * reserving and creating a new entry if necessary.
     *
     * @param creator
     * 		a function to create the pooled value for a reserved entry.
     * @return an entry from the pool or null if none is available.
     */
    public [CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry acquire([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry, [CtTypeParameterReferenceImpl]T> creator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry = [CtInvocationImpl]acquire();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entry != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]entry;

        [CtAssignmentImpl][CtVariableWriteImpl]entry = [CtInvocationImpl]reserve([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entry == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T value;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]creator.apply([CtVariableReadImpl]entry);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable th) [CtBlockImpl]{
            [CtInvocationImpl]remove([CtVariableReadImpl]entry);
            [CtThrowImpl]throw [CtVariableReadImpl]th;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]remove([CtVariableReadImpl]entry);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]entry.enable([CtVariableReadImpl]value, [CtLiteralImpl]true) ? [CtVariableReadImpl]entry : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method will return an acquired object to the pool. Objects
     * that are acquired from the pool but never released will result
     * in a memory leak.
     *
     * @param entry
     * 		the value to return to the pool
     * @return true if the entry was released and could be acquired again,
    false if the entry should be removed by calling {@link #remove(Pool.Entry)}
    and the object contained by the entry should be disposed.
     * @throws NullPointerException
     * 		if value is null
     */
    public [CtTypeReferenceImpl]boolean release([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]closed)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean released = [CtInvocationImpl][CtVariableReadImpl]entry.tryRelease();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]released && [CtBinaryOperatorImpl]([CtFieldReadImpl]cache != [CtLiteralImpl]null))[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]cache.set([CtVariableReadImpl]entry);

        [CtReturnImpl]return [CtVariableReadImpl]released;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a value from the pool.
     *
     * @param entry
     * 		the value to remove
     * @return true if the entry was removed, false otherwise
     */
    public [CtTypeReferenceImpl]boolean remove([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]closed)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]entry.tryRemove()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.eclipse.jetty.util.Pool.LOGGER.isDebugEnabled())[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl]org.eclipse.jetty.util.Pool.LOGGER.debug([CtLiteralImpl]"Attempt to remove an object from the pool that is still in use: {}", [CtVariableReadImpl]entry);

            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean removed = [CtInvocationImpl][CtFieldReadImpl]entries.remove([CtVariableReadImpl]entry);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]removed) && [CtInvocationImpl][CtFieldReadImpl]org.eclipse.jetty.util.Pool.LOGGER.isDebugEnabled())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]org.eclipse.jetty.util.Pool.LOGGER.debug([CtLiteralImpl]"Attempt to remove an object from the pool that does not exist: {}", [CtVariableReadImpl]entry);

        [CtReturnImpl]return [CtVariableReadImpl]removed;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isClosed() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]closed;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry> copy;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.thread.Locker.Lock l = [CtInvocationImpl][CtFieldReadImpl]locker.lock()) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]closed = [CtLiteralImpl]true;
            [CtAssignmentImpl][CtVariableWriteImpl]copy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]entries);
            [CtInvocationImpl][CtFieldReadImpl]entries.clear();
        }
        [CtForEachImpl][CtCommentImpl]// iterate the copy and close its entries
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry entry : [CtVariableReadImpl]copy) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entry.tryRemove() && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]entry.pooled instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.io.Closeable))[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.IO.close([CtFieldReadImpl](([CtTypeReferenceImpl]java.io.Closeable) ([CtVariableReadImpl]entry.pooled)));

        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int size() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]entries.size();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.eclipse.jetty.util.Pool<T>.Entry> values() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableCollection([CtFieldReadImpl]entries);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dump([CtParameterImpl][CtTypeReferenceImpl]java.lang.Appendable out, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String indent) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.component.Dumpable.dumpObjects([CtVariableReadImpl]out, [CtVariableReadImpl]indent, [CtThisAccessImpl]this, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jetty.util.component.DumpableCollection([CtLiteralImpl]"entries", [CtFieldReadImpl]entries));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s@%x[size=%d closed=%s pending=%d]", [CtInvocationImpl][CtInvocationImpl]getClass().getSimpleName(), [CtInvocationImpl]hashCode(), [CtInvocationImpl][CtFieldReadImpl]entries.size(), [CtFieldReadImpl]closed, [CtInvocationImpl][CtFieldReadImpl]pending.get());
    }

    [CtClassImpl]public class Entry {
        [CtFieldImpl][CtCommentImpl]// hi: positive=open/maxUsage counter; negative=closed; MIN_VALUE pending
        [CtCommentImpl]// lo: multiplexing counter
        private final [CtTypeReferenceImpl]org.eclipse.jetty.util.AtomicBiInteger state;

        [CtFieldImpl]private final [CtTypeReferenceImpl]int index;

        [CtFieldImpl][CtCommentImpl]// The pooled item.  This is not volatile as it is set once and then never changed.
        [CtCommentImpl]// Other threads accessing must check the state field above first, so a good before/after
        [CtCommentImpl]// relationship exists to make a memory barrier.
        private [CtTypeParameterReferenceImpl]T pooled;

        [CtConstructorImpl]Entry([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.state = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jetty.util.AtomicBiInteger([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE, [CtLiteralImpl]0);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.index = [CtVariableReadImpl]index;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Enable a reserved entry {@link Entry}.
         * An entry returned from the {@link #reserve(int)} method must be enabled with this method,
         * once and only once, before it is usable by the pool.
         * The entry may be enabled and not acquired, in which case it is immediately available to be
         * acquired, potentially by another thread; or it can be enabled and acquired atomically so that
         * no other thread can acquire it, although the acquire may still fail if the pool has been closed.
         *
         * @param pooled
         * 		The pooled item for the entry
         * @param acquire
         * 		If true the entry is atomically enabled and acquired.
         * @return true If the entry was enabled.
         * @throws IllegalStateException
         * 		if the entry was already enabled
         */
        public [CtTypeReferenceImpl]boolean enable([CtParameterImpl][CtTypeParameterReferenceImpl]T pooled, [CtParameterImpl][CtTypeReferenceImpl]boolean acquire) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]pooled);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]state.getHi() != [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]state.getHi() == [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
                    [CtReturnImpl]return [CtLiteralImpl]false;
                [CtCommentImpl]// Pool has been closed

                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Entry already enabled: " + [CtThisAccessImpl]this);
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pooled = [CtVariableReadImpl]pooled;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int usage = [CtConditionalImpl]([CtVariableReadImpl]acquire) ? [CtLiteralImpl]1 : [CtLiteralImpl]0;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]state.compareAndSet([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE, [CtVariableReadImpl]usage, [CtLiteralImpl]0, [CtVariableReadImpl]usage)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pooled = [CtLiteralImpl]null;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]state.getHi() == [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
                    [CtReturnImpl]return [CtLiteralImpl]false;
                [CtCommentImpl]// Pool has been closed

                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Entry already enabled: " + [CtThisAccessImpl]this);
            }
            [CtInvocationImpl][CtFieldReadImpl]pending.decrementAndGet();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl]public [CtTypeParameterReferenceImpl]T getPooled() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]pooled;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Release the entry.
         * This is equivalent to calling {@link Pool#release(Pool.Entry)} passing this entry.
         *
         * @return true if released.
         */
        public [CtTypeReferenceImpl]boolean release() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]org.eclipse.jetty.util.Pool.this.release([CtThisAccessImpl]this);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Remove the entry.
         * This is equivalent to calling {@link Pool#remove(Pool.Entry)} passing this entry.
         *
         * @return true if remove.
         */
        public [CtTypeReferenceImpl]boolean remove() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]org.eclipse.jetty.util.Pool.this.remove([CtThisAccessImpl]this);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to acquire the entry if possible by incrementing both the usage
         * count and the multiplex count.
         *
         * @return true if the usage count is &lt;= maxUsageCount and
        the multiplex count is maxMultiplex and the entry is not closed,
        false otherwise.
         */
        public [CtTypeReferenceImpl]boolean tryAcquire() [CtBlockImpl]{
            [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int usageCount = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean closed = [CtBinaryOperatorImpl][CtVariableReadImpl]usageCount < [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int multiplexingCount = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int currentMaxUsageCount = [CtFieldReadImpl]maxUsageCount;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]closed || [CtBinaryOperatorImpl]([CtVariableReadImpl]multiplexingCount >= [CtFieldReadImpl]maxMultiplex)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]currentMaxUsageCount > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]usageCount >= [CtVariableReadImpl]currentMaxUsageCount)))[CtBlockImpl]
                    [CtReturnImpl]return [CtLiteralImpl]false;

                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]state.compareAndSet([CtVariableReadImpl]encoded, [CtBinaryOperatorImpl][CtVariableReadImpl]usageCount + [CtLiteralImpl]1, [CtBinaryOperatorImpl][CtVariableReadImpl]multiplexingCount + [CtLiteralImpl]1))[CtBlockImpl]
                    [CtReturnImpl]return [CtLiteralImpl]true;

            } 
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to release the entry if possible by decrementing the multiplexing
         * count unless the entity is closed.
         *
         * @return true if the entry was released,
        false if {@link #tryRemove()} should be called.
         */
        [CtTypeReferenceImpl]boolean tryRelease() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int newMultiplexingCount;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int usageCount;
            [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
                [CtAssignmentImpl][CtVariableWriteImpl]usageCount = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean closed = [CtBinaryOperatorImpl][CtVariableReadImpl]usageCount < [CtLiteralImpl]0;
                [CtIfImpl]if ([CtVariableReadImpl]closed)[CtBlockImpl]
                    [CtReturnImpl]return [CtLiteralImpl]false;

                [CtAssignmentImpl][CtVariableWriteImpl]newMultiplexingCount = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded) - [CtLiteralImpl]1;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newMultiplexingCount < [CtLiteralImpl]0)[CtBlockImpl]
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Cannot release an already released entry");

                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]state.compareAndSet([CtVariableReadImpl]encoded, [CtVariableReadImpl]usageCount, [CtVariableReadImpl]newMultiplexingCount))[CtBlockImpl]
                    [CtBreakImpl]break;

            } 
            [CtLocalVariableImpl][CtTypeReferenceImpl]int currentMaxUsageCount = [CtFieldReadImpl]maxUsageCount;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean overUsed = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]currentMaxUsageCount > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]usageCount >= [CtVariableReadImpl]currentMaxUsageCount);
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]overUsed && [CtBinaryOperatorImpl]([CtVariableReadImpl]newMultiplexingCount == [CtLiteralImpl]0));
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isOverUsed() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int currentMaxUsageCount = [CtFieldReadImpl]maxUsageCount;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int usageCount = [CtInvocationImpl][CtFieldReadImpl]state.getHi();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]currentMaxUsageCount > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]usageCount >= [CtVariableReadImpl]currentMaxUsageCount);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Try to mark the entry as removed.
         *
         * @return true if the entry has to be removed from the containing pool, false otherwise.
         */
        [CtTypeReferenceImpl]boolean tryRemove() [CtBlockImpl]{
            [CtWhileImpl]while ([CtLiteralImpl]true) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int usageCount = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int multiplexCount = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int newMultiplexCount = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]multiplexCount - [CtLiteralImpl]1, [CtLiteralImpl]0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean removed = [CtInvocationImpl][CtFieldReadImpl]state.compareAndSet([CtVariableReadImpl]usageCount, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtVariableReadImpl]multiplexCount, [CtVariableReadImpl]newMultiplexCount);
                [CtIfImpl]if ([CtVariableReadImpl]removed) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]usageCount == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE)[CtBlockImpl]
                        [CtInvocationImpl][CtFieldReadImpl]pending.decrementAndGet();

                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]newMultiplexCount == [CtLiteralImpl]0;
                }
            } 
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isClosed() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]state.getHi() < [CtLiteralImpl]0;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isIdle() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded) == [CtLiteralImpl]0);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isInUse() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded) > [CtLiteralImpl]0);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]int getUsageCount() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtFieldReadImpl]state.getHi(), [CtLiteralImpl]0);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long encoded = [CtInvocationImpl][CtFieldReadImpl]state.get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int hi = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getHi([CtVariableReadImpl]encoded);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int lo = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jetty.util.AtomicBiInteger.getLo([CtVariableReadImpl]encoded);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String state = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]hi < [CtLiteralImpl]0) ? [CtLiteralImpl]"CLOSED" : [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]lo == [CtLiteralImpl]0 ? [CtLiteralImpl]"IDLE" : [CtLiteralImpl]"INUSE";
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s@%x{%s, usage=%d, multiplex=%d/%d, pooled=%s}", [CtInvocationImpl][CtInvocationImpl]getClass().getSimpleName(), [CtInvocationImpl]hashCode(), [CtVariableReadImpl]state, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]hi, [CtLiteralImpl]0), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]lo, [CtLiteralImpl]0), [CtInvocationImpl]getMaxMultiplex(), [CtFieldReadImpl]pooled);
        }
    }
}
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
[CtPackageDeclarationImpl]package com.hazelcast.config;
[CtUnresolvedImport]import com.hazelcast.memory.MemorySize;
[CtUnresolvedImport]import com.hazelcast.core.HazelcastException;
[CtUnresolvedImport]import com.hazelcast.memory.NativeOutOfMemoryError;
[CtUnresolvedImport]import com.hazelcast.memory.MemoryUnit;
[CtUnresolvedImport]import static com.hazelcast.internal.util.Preconditions.checkPositive;
[CtUnresolvedImport]import static com.hazelcast.internal.util.Preconditions.isNotNull;
[CtImportImpl]import static java.util.Objects.requireNonNull;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.hazelcast.map.IMap;
[CtClassImpl][CtJavaDocImpl]/**
 * Configures native memory region.
 * <p>
 * Native memory is allocated outside JVM heap space and is not subject to JVM garbage collection.
 * Therefore, hundreds of gigabytes of native memory can be allocated &amp; used without introducing
 * pressure on GC mechanism.
 * <p>
 * Data structures, such as {@link IMap} and {@link com.hazelcast.cache.ICache},
 * store their data (entries, indexes etc.) in native memory region when they are configured with
 * {@link InMemoryFormat#NATIVE}.
 */
public class NativeMemoryConfig {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Default minimum block size in bytes
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_MIN_BLOCK_SIZE = [CtLiteralImpl]16;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default page size in bytes
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"checkstyle:magicnumber")
    public static final [CtTypeReferenceImpl]int DEFAULT_PAGE_SIZE = [CtBinaryOperatorImpl][CtLiteralImpl]1 << [CtLiteralImpl]22;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default metadata space percentage
     */
    public static final [CtTypeReferenceImpl]float DEFAULT_METADATA_SPACE_PERCENTAGE = [CtLiteralImpl]12.5F;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Minimum initial memory size in megabytes
     */
    public static final [CtTypeReferenceImpl]int MIN_INITIAL_MEMORY_SIZE = [CtLiteralImpl]512;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Initial memory size in megabytes
     */
    public static final [CtTypeReferenceImpl]int INITIAL_MEMORY_SIZE = [CtFieldReadImpl]com.hazelcast.config.NativeMemoryConfig.MIN_INITIAL_MEMORY_SIZE;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean enabled;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.memory.MemorySize size = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.memory.MemorySize([CtFieldReadImpl]com.hazelcast.config.NativeMemoryConfig.INITIAL_MEMORY_SIZE, [CtFieldReadImpl]com.hazelcast.memory.MemoryUnit.MEGABYTES);

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig.MemoryAllocatorType allocatorType = [CtFieldReadImpl][CtTypeAccessImpl]com.hazelcast.config.NativeMemoryConfig.MemoryAllocatorType.[CtFieldReferenceImpl]POOLED;

    [CtFieldImpl]private [CtTypeReferenceImpl]int minBlockSize = [CtFieldReadImpl]com.hazelcast.config.NativeMemoryConfig.DEFAULT_MIN_BLOCK_SIZE;

    [CtFieldImpl]private [CtTypeReferenceImpl]int pageSize = [CtFieldReadImpl]com.hazelcast.config.NativeMemoryConfig.DEFAULT_PAGE_SIZE;

    [CtFieldImpl]private [CtTypeReferenceImpl]float metadataSpacePercentage = [CtFieldReadImpl]com.hazelcast.config.NativeMemoryConfig.DEFAULT_METADATA_SPACE_PERCENTAGE;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryConfig persistentMemoryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryConfig();

    [CtConstructorImpl]public NativeMemoryConfig() [CtBlockImpl]{
    }

    [CtConstructorImpl]public NativeMemoryConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig nativeMemoryConfig) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]enabled = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.enabled;
        [CtAssignmentImpl][CtFieldWriteImpl]size = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.size;
        [CtAssignmentImpl][CtFieldWriteImpl]allocatorType = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.allocatorType;
        [CtAssignmentImpl][CtFieldWriteImpl]minBlockSize = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.minBlockSize;
        [CtAssignmentImpl][CtFieldWriteImpl]pageSize = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.pageSize;
        [CtAssignmentImpl][CtFieldWriteImpl]metadataSpacePercentage = [CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.metadataSpacePercentage;
        [CtAssignmentImpl][CtFieldWriteImpl]persistentMemoryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryConfig([CtFieldReadImpl][CtVariableReadImpl]nativeMemoryConfig.persistentMemoryConfig);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns size of the native memory region.
     */
    public [CtTypeReferenceImpl]com.hazelcast.memory.MemorySize getSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]size;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets size of the native memory region.
     * <p>
     * Total size of the memory blocks allocated in native memory region cannot exceed this memory size.
     * When native memory region is completely allocated and in-use, further allocation requests will fail
     * with {@link NativeOutOfMemoryError}.
     *
     * @param size
     * 		memory size
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setSize([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.memory.MemorySize size) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.size = [CtInvocationImpl]isNotNull([CtVariableReadImpl]size, [CtLiteralImpl]"size");
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns {@code true} if native memory allocation is enabled, {@code false} otherwise.
     */
    public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]enabled;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Enables or disables native memory allocation.
     *
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setEnabled([CtParameterImpl]final [CtTypeReferenceImpl]boolean enabled) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.enabled = [CtVariableReadImpl]enabled;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the {@link MemoryAllocatorType} to be used while allocating native memory.
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig.MemoryAllocatorType getAllocatorType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]allocatorType;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the {@link MemoryAllocatorType} to be used while allocating native memory.
     *
     * @param allocatorType
     * 		{@code MemoryAllocatorType}
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setAllocatorType([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig.MemoryAllocatorType allocatorType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allocatorType = [CtVariableReadImpl]allocatorType;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the minimum memory block size, in bytes, to be served by native memory manager.
     * Allocation requests smaller than minimum block size are served with the minimum block size.
     * Default value is {@link #DEFAULT_MIN_BLOCK_SIZE} bytes.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     */
    public [CtTypeReferenceImpl]int getMinBlockSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]minBlockSize;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the minimum memory block size, in bytes, to be served by native memory manager.
     * Allocation requests smaller than minimum block size are served with the minimum block size.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     *
     * @param minBlockSize
     * 		minimum memory block size
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setMinBlockSize([CtParameterImpl][CtTypeReferenceImpl]int minBlockSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minBlockSize = [CtInvocationImpl]checkPositive([CtVariableReadImpl]minBlockSize, [CtLiteralImpl]"Minimum block size should be positive");
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the page size, in bytes, to be allocated by native memory manager as a single block. These page blocks are
     * split into smaller blocks to serve allocation requests.
     * Allocation requests greater than the page size are allocated from system directly,
     * instead of managed memory pool.
     * Default value is {@link #DEFAULT_PAGE_SIZE} bytes.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     */
    public [CtTypeReferenceImpl]int getPageSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]pageSize;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the page size, in bytes, to be allocated by native memory manager as a single block. These page blocks are
     * split into smaller blocks to serve allocation requests.
     * Allocation requests greater than the page size are allocated from system directly,
     * instead of managed memory pool.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     *
     * @param pageSize
     * 		size of the page
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setPageSize([CtParameterImpl][CtTypeReferenceImpl]int pageSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pageSize = [CtInvocationImpl]checkPositive([CtVariableReadImpl]pageSize, [CtLiteralImpl]"Page size should be positive");
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the percentage of native memory space to be used to store metadata and internal memory structures
     * by the native memory manager.
     * Default value is {@link #DEFAULT_METADATA_SPACE_PERCENTAGE}.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     */
    public [CtTypeReferenceImpl]float getMetadataSpacePercentage() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]metadataSpacePercentage;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the percentage of native memory space to be used to store metadata and internal memory structures
     * by the native memory manager.
     * <p>
     * <strong>This configuration is used only by {@link MemoryAllocatorType#POOLED}, otherwise ignored.</strong>
     *
     * @param metadataSpacePercentage
     * 		percentage of metadata space
     * @return this {@link NativeMemoryConfig} instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setMetadataSpacePercentage([CtParameterImpl][CtTypeReferenceImpl]float metadataSpacePercentage) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.metadataSpacePercentage = [CtVariableReadImpl]metadataSpacePercentage;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the persistent memory configuration this native memory
     * configuration uses.
     *
     * @return the persistent memory configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryConfig getPersistentMemoryConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]persistentMemoryConfig;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the persistent memory configuration this native memory
     * configuration uses.
     *
     * @param persistentMemoryConfig
     * 		The persistent memory configuration to use
     */
    public [CtTypeReferenceImpl]void setPersistentMemoryConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryConfig persistentMemoryConfig) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.persistentMemoryConfig = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]persistentMemoryConfig);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the persistent memory directory (e.g. Intel Optane) to be
     * used to store memory structures allocated by native memory manager.
     * If there are multiple persistent memory directories are defined in
     * {@link #persistentMemoryConfig}, an {@link IllegalStateException}
     * is thrown.
     *
     * @see PersistentMemoryConfig#getDirectoryConfigs()
     * @deprecated Since 4.1 multiple persistent memory directories are
    supported. Please use {@link PersistentMemoryConfig#getDirectoryConfigs()}
    instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.lang.String getPersistentMemoryDirectory() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryDirectoryConfig> directoryConfigs = [CtInvocationImpl][CtFieldReadImpl]persistentMemoryConfig.getDirectoryConfigs();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int directoriesDefined = [CtInvocationImpl][CtVariableReadImpl]directoryConfigs.size();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]directoriesDefined > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.core.HazelcastException([CtBinaryOperatorImpl][CtLiteralImpl]"There are multiple persistent memory directories configured. Please use " + [CtLiteralImpl]"PersistentMemoryConfig.getDirectoryConfigs()!");
        }
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]directoriesDefined == [CtLiteralImpl]1 ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directoryConfigs.get([CtLiteralImpl]0).getDirectory() : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the persistent memory directory (e.g. Intel Optane) to be used
     * to store memory structures allocated by native memory manager. If
     * the {@link #persistentMemoryConfig} already contains directory
     * definition, it is overridden with the provided {@code directory}.
     *
     * @param directory
     * 		the persistent memory directory
     * @return this {@link NativeMemoryConfig} instance
     * @see #getPersistentMemoryConfig()
     * @see PersistentMemoryConfig#addDirectoryConfig(PersistentMemoryDirectoryConfig)
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig setPersistentMemoryDirectory([CtParameterImpl][CtTypeReferenceImpl]java.lang.String directory) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.persistentMemoryConfig.setDirectoryConfig([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.config.PersistentMemoryDirectoryConfig([CtVariableReadImpl]directory));
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * Type of memory allocator:
     * <ul>
     * <li>STANDARD: allocate/free memory using default OS memory manager</li>
     * <li>POOLED: manage memory blocks in pool</li>
     * </ul>
     */
    public enum MemoryAllocatorType {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * STANDARD memory allocator: allocate/free memory using default OS memory manager
         */
        STANDARD,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * POOLED memory allocator: manage memory blocks in pool
         */
        POOLED;}

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"checkstyle:cyclomaticcomplexity", [CtLiteralImpl]"checkstyle:npathcomplexity" })
    public final [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig that = [CtVariableReadImpl](([CtTypeReferenceImpl]com.hazelcast.config.NativeMemoryConfig) (o));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]enabled != [CtFieldReadImpl][CtVariableReadImpl]that.enabled) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]minBlockSize != [CtFieldReadImpl][CtVariableReadImpl]that.minBlockSize) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]pageSize != [CtFieldReadImpl][CtVariableReadImpl]that.pageSize) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Float.compare([CtFieldReadImpl][CtVariableReadImpl]that.metadataSpacePercentage, [CtFieldReadImpl]metadataSpacePercentage) != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]size, [CtFieldReadImpl][CtVariableReadImpl]that.size)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]persistentMemoryConfig.equals([CtFieldReadImpl][CtVariableReadImpl]that.persistentMemoryConfig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]allocatorType == [CtFieldReadImpl][CtVariableReadImpl]that.allocatorType;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int result = [CtConditionalImpl]([CtFieldReadImpl]enabled) ? [CtLiteralImpl]1 : [CtLiteralImpl]0;
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]size != [CtLiteralImpl]null ? [CtInvocationImpl][CtFieldReadImpl]size.hashCode() : [CtLiteralImpl]0);
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]allocatorType != [CtLiteralImpl]null ? [CtInvocationImpl][CtFieldReadImpl]allocatorType.hashCode() : [CtLiteralImpl]0);
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtFieldReadImpl]minBlockSize;
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtFieldReadImpl]pageSize;
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]metadataSpacePercentage != [CtUnaryOperatorImpl](+[CtLiteralImpl]0.0F) ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.Float.floatToIntBits([CtFieldReadImpl]metadataSpacePercentage) : [CtLiteralImpl]0);
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtInvocationImpl][CtFieldReadImpl]persistentMemoryConfig.hashCode();
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"NativeMemoryConfig{" + [CtLiteralImpl]"enabled=") + [CtFieldReadImpl]enabled) + [CtLiteralImpl]", size=") + [CtFieldReadImpl]size) + [CtLiteralImpl]", allocatorType=") + [CtFieldReadImpl]allocatorType) + [CtLiteralImpl]", minBlockSize=") + [CtFieldReadImpl]minBlockSize) + [CtLiteralImpl]", pageSize=") + [CtFieldReadImpl]pageSize) + [CtLiteralImpl]", metadataSpacePercentage=") + [CtFieldReadImpl]metadataSpacePercentage) + [CtLiteralImpl]", persistentMemoryConfig=") + [CtFieldReadImpl]persistentMemoryConfig) + [CtLiteralImpl]'}';
    }
}
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
[CtUnresolvedImport]import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
[CtUnresolvedImport]import com.hazelcast.internal.config.ConfigDataSerializerHook;
[CtUnresolvedImport]import static com.hazelcast.internal.util.Preconditions.checkAsyncBackupCount;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import static com.hazelcast.internal.util.Preconditions.checkNotNull;
[CtUnresolvedImport]import static com.hazelcast.internal.util.Preconditions.checkBackupCount;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import static com.hazelcast.internal.serialization.impl.SerializationUtil.writeNullableList;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.hazelcast.nio.ObjectDataOutput;
[CtUnresolvedImport]import com.hazelcast.nio.ObjectDataInput;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.hazelcast.collection.IQueue;
[CtUnresolvedImport]import static com.hazelcast.internal.serialization.impl.SerializationUtil.readNullableList;
[CtClassImpl][CtJavaDocImpl]/**
 * Contains the configuration for an {@link IQueue}.
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"checkstyle:methodcount")
public class QueueConfig implements [CtTypeReferenceImpl]com.hazelcast.nio.serialization.IdentifiedDataSerializable , [CtTypeReferenceImpl]com.hazelcast.config.NamedConfig {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Default value for the maximum size of the Queue.
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_MAX_SIZE = [CtLiteralImpl]0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default value for the synchronous backup count.
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_SYNC_BACKUP_COUNT = [CtLiteralImpl]1;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default value of the asynchronous backup count.
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_ASYNC_BACKUP_COUNT = [CtLiteralImpl]0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default value for the TTL (time to live) for empty Queue.
     */
    public static final [CtTypeReferenceImpl]int DEFAULT_EMPTY_QUEUE_TTL = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hazelcast.config.ItemListenerConfig> listenerConfigs;

    [CtFieldImpl]private [CtTypeReferenceImpl]int backupCount = [CtFieldReadImpl]com.hazelcast.config.QueueConfig.DEFAULT_SYNC_BACKUP_COUNT;

    [CtFieldImpl]private [CtTypeReferenceImpl]int asyncBackupCount = [CtFieldReadImpl]com.hazelcast.config.QueueConfig.DEFAULT_ASYNC_BACKUP_COUNT;

    [CtFieldImpl]private [CtTypeReferenceImpl]int maxSize = [CtFieldReadImpl]com.hazelcast.config.QueueConfig.DEFAULT_MAX_SIZE;

    [CtFieldImpl]private [CtTypeReferenceImpl]int emptyQueueTtl = [CtFieldReadImpl]com.hazelcast.config.QueueConfig.DEFAULT_EMPTY_QUEUE_TTL;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.config.QueueStoreConfig queueStoreConfig;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean statisticsEnabled = [CtLiteralImpl]true;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String splitBrainProtectionName;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hazelcast.config.MergePolicyConfig mergePolicyConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.config.MergePolicyConfig();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String comparatorClassName;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean duplicateAllowed = [CtLiteralImpl]true;

    [CtConstructorImpl]public QueueConfig() [CtBlockImpl]{
    }

    [CtConstructorImpl]public QueueConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl]setName([CtVariableReadImpl]name);
    }

    [CtConstructorImpl]public QueueConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.QueueConfig config) [CtBlockImpl]{
        [CtInvocationImpl]this();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtFieldReadImpl][CtVariableReadImpl]config.name;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.backupCount = [CtFieldReadImpl][CtVariableReadImpl]config.backupCount;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.asyncBackupCount = [CtFieldReadImpl][CtVariableReadImpl]config.asyncBackupCount;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxSize = [CtFieldReadImpl][CtVariableReadImpl]config.maxSize;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.emptyQueueTtl = [CtFieldReadImpl][CtVariableReadImpl]config.emptyQueueTtl;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.statisticsEnabled = [CtFieldReadImpl][CtVariableReadImpl]config.statisticsEnabled;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.splitBrainProtectionName = [CtFieldReadImpl][CtVariableReadImpl]config.splitBrainProtectionName;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mergePolicyConfig = [CtFieldReadImpl][CtVariableReadImpl]config.mergePolicyConfig;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queueStoreConfig = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]config.queueStoreConfig != [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hazelcast.config.QueueStoreConfig([CtFieldReadImpl][CtVariableReadImpl]config.queueStoreConfig) : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.listenerConfigs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]config.getItemListenerConfigs());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.comparatorClassName = [CtFieldReadImpl][CtVariableReadImpl]config.comparatorClassName;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.duplicateAllowed = [CtFieldReadImpl][CtVariableReadImpl]config.duplicateAllowed;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the TTL (time to live) for emptying the Queue.
     *
     * @return the TTL (time to live) for emptying the Queue
     */
    public [CtTypeReferenceImpl]int getEmptyQueueTtl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]emptyQueueTtl;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the TTL (time to live) for emptying the Queue.
     *
     * @param emptyQueueTtl
     * 		set the TTL (time to live) for emptying the Queue to this value
     * @return the Queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setEmptyQueueTtl([CtParameterImpl][CtTypeReferenceImpl]int emptyQueueTtl) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.emptyQueueTtl = [CtVariableReadImpl]emptyQueueTtl;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the maximum size of the Queue.
     *
     * @return the maximum size of the Queue
     */
    public [CtTypeReferenceImpl]int getMaxSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]maxSize == [CtLiteralImpl]0 ? [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE : [CtFieldReadImpl]maxSize;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the maximum size of the Queue.
     *
     * @param maxSize
     * 		set the maximum size of the Queue to this value
     * @return the Queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setMaxSize([CtParameterImpl][CtTypeReferenceImpl]int maxSize) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxSize < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Size of the queue can not be a negative value!");
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxSize = [CtVariableReadImpl]maxSize;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the total number of backups: the backup count plus the asynchronous backup count.
     *
     * @return the total number of backups
     */
    public [CtTypeReferenceImpl]int getTotalBackupCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]backupCount + [CtFieldReadImpl]asyncBackupCount;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the number of synchronous backups for this queue.
     *
     * @return the synchronous backup count
     */
    public [CtTypeReferenceImpl]int getBackupCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]backupCount;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the number of synchronous backups for this queue.
     *
     * @param backupCount
     * 		the number of synchronous backups to set
     * @return the current QueueConfig
     * @throws IllegalArgumentException
     * 		if backupCount is smaller than 0,
     * 		or larger than the maximum number of backups,
     * 		or the sum of the backups and async backups is larger than the maximum number of backups
     * @see #setAsyncBackupCount(int)
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setBackupCount([CtParameterImpl][CtTypeReferenceImpl]int backupCount) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.backupCount = [CtInvocationImpl]checkBackupCount([CtVariableReadImpl]backupCount, [CtFieldReadImpl]asyncBackupCount);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the number of asynchronous backups for this queue.
     *
     * @return the number of asynchronous backups
     */
    public [CtTypeReferenceImpl]int getAsyncBackupCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]asyncBackupCount;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the number of asynchronous backups. 0 means no backups.
     *
     * @param asyncBackupCount
     * 		the number of asynchronous synchronous backups to set
     * @return the updated QueueConfig
     * @throws IllegalArgumentException
     * 		if asyncBackupCount smaller than 0,
     * 		or larger than the maximum number of backup
     * 		or the sum of the backups and async backups is larger than the maximum number of backups
     * @see #setBackupCount(int)
     * @see #getAsyncBackupCount()
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setAsyncBackupCount([CtParameterImpl][CtTypeReferenceImpl]int asyncBackupCount) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.asyncBackupCount = [CtInvocationImpl]checkAsyncBackupCount([CtFieldReadImpl]backupCount, [CtVariableReadImpl]asyncBackupCount);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the QueueStore (load and store queue items from/to a database) configuration.
     *
     * @return the QueueStore configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueStoreConfig getQueueStoreConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]queueStoreConfig;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the QueueStore (load and store queue items from/to a database) configuration.
     *
     * @param queueStoreConfig
     * 		set the QueueStore configuration to this configuration
     * @return the QueueStore configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setQueueStoreConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.QueueStoreConfig queueStoreConfig) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queueStoreConfig = [CtVariableReadImpl]queueStoreConfig;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if statistics are enabled for this queue.
     *
     * @return {@code true} if statistics are enabled, {@code false} otherwise
     */
    public [CtTypeReferenceImpl]boolean isStatisticsEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]statisticsEnabled;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Enables or disables statistics for this queue.
     *
     * @param statisticsEnabled
     * 		{@code true} to enable statistics for this queue, {@code false} to disable
     * @return the updated QueueConfig
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setStatisticsEnabled([CtParameterImpl][CtTypeReferenceImpl]boolean statisticsEnabled) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.statisticsEnabled = [CtVariableReadImpl]statisticsEnabled;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the name of this queue
     */
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the name for this queue.
     *
     * @param name
     * 		the name to set for this queue
     * @return this queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an item listener configuration to this queue.
     *
     * @param listenerConfig
     * 		the item listener configuration to add to this queue
     * @return the updated queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig addItemListenerConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.ItemListenerConfig listenerConfig) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getItemListenerConfigs().add([CtVariableReadImpl]listenerConfig);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the list of item listener configurations for this queue.
     *
     * @return the list of item listener configurations for this queue
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hazelcast.config.ItemListenerConfig> getItemListenerConfigs() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]listenerConfigs == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]listenerConfigs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        }
        [CtReturnImpl]return [CtFieldReadImpl]listenerConfigs;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the list of item listener configurations for this queue.
     *
     * @param listenerConfigs
     * 		the list of item listener configurations to set for this queue
     * @return the updated queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setItemListenerConfigs([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hazelcast.config.ItemListenerConfig> listenerConfigs) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.listenerConfigs = [CtVariableReadImpl]listenerConfigs;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the split brain protection name for queue operations.
     *
     * @return the split brain protection name
     */
    public [CtTypeReferenceImpl]java.lang.String getSplitBrainProtectionName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]splitBrainProtectionName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the split brain protection name for queue operations.
     *
     * @param splitBrainProtectionName
     * 		the split brain protection name
     * @return the updated queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setSplitBrainProtectionName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String splitBrainProtectionName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.splitBrainProtectionName = [CtVariableReadImpl]splitBrainProtectionName;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link MergePolicyConfig} for this queue.
     *
     * @return the {@link MergePolicyConfig} for this queue
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.MergePolicyConfig getMergePolicyConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mergePolicyConfig;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the {@link MergePolicyConfig} for this queue.
     *
     * @return the updated queue configuration
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setMergePolicyConfig([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.config.MergePolicyConfig mergePolicyConfig) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mergePolicyConfig = [CtInvocationImpl]checkNotNull([CtVariableReadImpl]mergePolicyConfig, [CtLiteralImpl]"mergePolicyConfig cannot be null");
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if underlying implementation is a {@code PriorityQueue}. Otherwise it is a FIFO queue
     *
     * @return {@code true} if priority queue has been configured, {@code false} otherwise
     */
    public [CtTypeReferenceImpl]boolean isPriorityQueue() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]comparatorClassName != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the class name of the configured {@link Comparator} implementation.
     *
     * @return the class name of the configured {@link Comparator} implementation
     */
    public [CtTypeReferenceImpl]java.lang.String getComparatorClassName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]comparatorClassName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the class name of the configured {@link Comparator} implementation.
     *
     * @param comparatorClassName
     * 		the class name of the
     * 		configured {@link Comparator} implementation
     * @return this QueueConfig instance
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setComparatorClassName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String comparatorClassName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.comparatorClassName = [CtVariableReadImpl]comparatorClassName;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if duplicates are allowed for this queue.
     *
     * @return {@code true} if duplicates are allowed, {@code false} otherwise
     */
    public [CtTypeReferenceImpl]boolean isDuplicateAllowed() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]duplicateAllowed;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Allows or forbids duplicates for this queue.
     *
     * @param duplicateAllowed
     * 		{@code true} to allow duplicates for this queue, {@code false} to forbid
     * @return the updated QueueConfig
     */
    public [CtTypeReferenceImpl]com.hazelcast.config.QueueConfig setDuplicateAllowed([CtParameterImpl][CtTypeReferenceImpl]boolean duplicateAllowed) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.duplicateAllowed = [CtVariableReadImpl]duplicateAllowed;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"QueueConfig{" + [CtLiteralImpl]"name='") + [CtFieldReadImpl]name) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", listenerConfigs=") + [CtFieldReadImpl]listenerConfigs) + [CtLiteralImpl]", backupCount=") + [CtFieldReadImpl]backupCount) + [CtLiteralImpl]", asyncBackupCount=") + [CtFieldReadImpl]asyncBackupCount) + [CtLiteralImpl]", maxSize=") + [CtFieldReadImpl]maxSize) + [CtLiteralImpl]", emptyQueueTtl=") + [CtFieldReadImpl]emptyQueueTtl) + [CtLiteralImpl]", queueStoreConfig=") + [CtFieldReadImpl]queueStoreConfig) + [CtLiteralImpl]", statisticsEnabled=") + [CtFieldReadImpl]statisticsEnabled) + [CtLiteralImpl]", mergePolicyConfig=") + [CtFieldReadImpl]mergePolicyConfig) + [CtLiteralImpl]", comparatorClassName=") + [CtFieldReadImpl]comparatorClassName) + [CtLiteralImpl]", duplicateAllowed=") + [CtFieldReadImpl]duplicateAllowed) + [CtLiteralImpl]'}';
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getFactoryId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.hazelcast.internal.config.ConfigDataSerializerHook.F_ID;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getClassId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.hazelcast.internal.config.ConfigDataSerializerHook.QUEUE_CONFIG;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void writeData([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.nio.ObjectDataOutput out) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]out.writeUTF([CtFieldReadImpl]name);
        [CtInvocationImpl]writeNullableList([CtFieldReadImpl]listenerConfigs, [CtVariableReadImpl]out);
        [CtInvocationImpl][CtVariableReadImpl]out.writeInt([CtFieldReadImpl]backupCount);
        [CtInvocationImpl][CtVariableReadImpl]out.writeInt([CtFieldReadImpl]asyncBackupCount);
        [CtInvocationImpl][CtVariableReadImpl]out.writeInt([CtFieldReadImpl]maxSize);
        [CtInvocationImpl][CtVariableReadImpl]out.writeInt([CtFieldReadImpl]emptyQueueTtl);
        [CtInvocationImpl][CtVariableReadImpl]out.writeObject([CtFieldReadImpl]queueStoreConfig);
        [CtInvocationImpl][CtVariableReadImpl]out.writeBoolean([CtFieldReadImpl]statisticsEnabled);
        [CtInvocationImpl][CtVariableReadImpl]out.writeUTF([CtFieldReadImpl]splitBrainProtectionName);
        [CtInvocationImpl][CtVariableReadImpl]out.writeObject([CtFieldReadImpl]mergePolicyConfig);
        [CtInvocationImpl][CtVariableReadImpl]out.writeObject([CtFieldReadImpl]comparatorClassName);
        [CtInvocationImpl][CtVariableReadImpl]out.writeBoolean([CtFieldReadImpl]duplicateAllowed);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void readData([CtParameterImpl][CtTypeReferenceImpl]com.hazelcast.nio.ObjectDataInput in) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]name = [CtInvocationImpl][CtVariableReadImpl]in.readUTF();
        [CtAssignmentImpl][CtFieldWriteImpl]listenerConfigs = [CtInvocationImpl]readNullableList([CtVariableReadImpl]in);
        [CtAssignmentImpl][CtFieldWriteImpl]backupCount = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
        [CtAssignmentImpl][CtFieldWriteImpl]asyncBackupCount = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
        [CtAssignmentImpl][CtFieldWriteImpl]maxSize = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
        [CtAssignmentImpl][CtFieldWriteImpl]emptyQueueTtl = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
        [CtAssignmentImpl][CtFieldWriteImpl]queueStoreConfig = [CtInvocationImpl][CtVariableReadImpl]in.readObject();
        [CtAssignmentImpl][CtFieldWriteImpl]statisticsEnabled = [CtInvocationImpl][CtVariableReadImpl]in.readBoolean();
        [CtAssignmentImpl][CtFieldWriteImpl]splitBrainProtectionName = [CtInvocationImpl][CtVariableReadImpl]in.readUTF();
        [CtAssignmentImpl][CtFieldWriteImpl]mergePolicyConfig = [CtInvocationImpl][CtVariableReadImpl]in.readObject();
        [CtAssignmentImpl][CtFieldWriteImpl]comparatorClassName = [CtInvocationImpl][CtVariableReadImpl]in.readObject();
        [CtAssignmentImpl][CtFieldWriteImpl]duplicateAllowed = [CtInvocationImpl][CtVariableReadImpl]in.readBoolean();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"checkstyle:cyclomaticcomplexity", [CtLiteralImpl]"checkstyle:npathcomplexity" })
    public final [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.hazelcast.config.QueueConfig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hazelcast.config.QueueConfig that = [CtVariableReadImpl](([CtTypeReferenceImpl]com.hazelcast.config.QueueConfig) (o));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]backupCount != [CtFieldReadImpl][CtVariableReadImpl]that.backupCount) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]asyncBackupCount != [CtFieldReadImpl][CtVariableReadImpl]that.asyncBackupCount) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getMaxSize() != [CtInvocationImpl][CtVariableReadImpl]that.getMaxSize()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]emptyQueueTtl != [CtFieldReadImpl][CtVariableReadImpl]that.emptyQueueTtl) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]statisticsEnabled != [CtFieldReadImpl][CtVariableReadImpl]that.statisticsEnabled) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]name, [CtFieldReadImpl][CtVariableReadImpl]that.name)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getItemListenerConfigs().equals([CtInvocationImpl][CtVariableReadImpl]that.getItemListenerConfigs())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]queueStoreConfig, [CtFieldReadImpl][CtVariableReadImpl]that.queueStoreConfig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]splitBrainProtectionName, [CtFieldReadImpl][CtVariableReadImpl]that.splitBrainProtectionName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mergePolicyConfig, [CtFieldReadImpl][CtVariableReadImpl]that.mergePolicyConfig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]comparatorClassName, [CtFieldReadImpl][CtVariableReadImpl]that.comparatorClassName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]duplicateAllowed, [CtFieldReadImpl][CtVariableReadImpl]that.duplicateAllowed);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtFieldReadImpl]name, [CtInvocationImpl]getItemListenerConfigs(), [CtFieldReadImpl]backupCount, [CtFieldReadImpl]asyncBackupCount, [CtInvocationImpl]getMaxSize(), [CtFieldReadImpl]emptyQueueTtl, [CtFieldReadImpl]queueStoreConfig, [CtFieldReadImpl]statisticsEnabled, [CtFieldReadImpl]splitBrainProtectionName, [CtFieldReadImpl]mergePolicyConfig, [CtFieldReadImpl]comparatorClassName, [CtFieldReadImpl]duplicateAllowed);
    }
}
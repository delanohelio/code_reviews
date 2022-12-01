[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 LinkedIn Corp. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
[CtPackageDeclarationImpl]package com.github.ambry.quota;
[CtImportImpl]import java.util.concurrent.ScheduledExecutorService;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.apache.helix.store.HelixPropertyListener;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import org.apache.zookeeper.data.Stat;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.helix.zookeeper.datamodel.ZNRecord;
[CtUnresolvedImport]import com.github.ambry.config.StorageQuotaConfig;
[CtUnresolvedImport]import org.apache.helix.store.HelixPropertyStore;
[CtImportImpl]import java.util.Random;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import com.github.ambry.server.StatsSnapshot;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import org.apache.helix.AccessOption;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.codehaus.jackson.map.ObjectMapper;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 * A Helix implementation of {@link StorageUsageRefresher}. A Helix aggregation task, created in ambry-server, would persist
 * current storage usage in ZooKeeper periodically. This implementation would fetch the storage usage from zookeeper using
 * {@link HelixPropertyStore} and subscribe to the change. This implementation also periodically refetch the storage usage
 * from ZooKeeper to keep it up-to-date.
 */
public class HelixStorageUsageRefresher implements [CtTypeReferenceImpl]com.github.ambry.quota.StorageUsageRefresher {
    [CtFieldImpl][CtCommentImpl]// These two constant fields are shared by other classes. TODO: reuse these two constant fields.
    public static final [CtTypeReferenceImpl]java.lang.String AGGREGATED_CONTAINER_STORAGE_USAGE_PATH = [CtLiteralImpl]"Aggregated_AccountReport";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String VALID_SIZE_FILED_NAME = [CtLiteralImpl]"valid_data_size";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.helix.store.HelixPropertyStore<[CtTypeReferenceImpl]org.apache.helix.zookeeper.datamodel.ZNRecord> helixStore;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>>> containerStorageUsageRef = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]com.github.ambry.quota.Callback> callback = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService scheduler;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.github.ambry.config.StorageQuotaConfig config;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructor to create a {@link HelixStorageUsageRefresher}.
     *
     * @param helixStore
     * 		The {@link HelixPropertyStore} used to fetch storage usage and subscribe to change.
     * @param scheduler
     * 		The {@link ScheduledExecutorService} to schedule a task to periodically refresh the usage.
     * @param config
     * 		The {@link StorageQuotaConfig}.
     */
    public HelixStorageUsageRefresher([CtParameterImpl][CtTypeReferenceImpl]org.apache.helix.store.HelixPropertyStore<[CtTypeReferenceImpl]org.apache.helix.zookeeper.datamodel.ZNRecord> helixStore, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService scheduler, [CtParameterImpl][CtTypeReferenceImpl]com.github.ambry.config.StorageQuotaConfig config) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.helixStore = [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]helixStore, [CtLiteralImpl]"HelixPropertyStore can't be null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scheduler = [CtVariableReadImpl]scheduler;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
        [CtInvocationImpl]subscribeToChange();
        [CtInvocationImpl]initialFetchAndSchedule();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch the storage usage and schedule a task to periodically refresh the storage usage.
     */
    private [CtTypeReferenceImpl]void initialFetchAndSchedule() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Runnable updater = [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> initialValue = [CtInvocationImpl]fetchContainerStorageUsageFromPath([CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.AGGREGATED_CONTAINER_STORAGE_USAGE_PATH);
                [CtInvocationImpl][CtFieldReadImpl]containerStorageUsageRef.set([CtVariableReadImpl]initialValue);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.logger.error([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to parse the container usage from znode " + [CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.AGGREGATED_CONTAINER_STORAGE_USAGE_PATH, [CtVariableReadImpl]e);
                [CtInvocationImpl][CtCommentImpl]// If we already have a container usage map in memory, then don't replace it with empty map.
                [CtFieldReadImpl]containerStorageUsageRef.compareAndSet([CtLiteralImpl]null, [CtFieldReadImpl][CtTypeAccessImpl]java.util.Collections.[CtFieldReferenceImpl]EMPTY_MAP);
            }
        };
        [CtInvocationImpl][CtVariableReadImpl]updater.run();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]scheduler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int initialDelay = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Random().nextInt([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.refresherPollingIntervalMs + [CtLiteralImpl]1);
            [CtInvocationImpl][CtFieldReadImpl]scheduler.scheduleAtFixedRate([CtVariableReadImpl]updater, [CtVariableReadImpl]initialDelay, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.refresherPollingIntervalMs, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MILLISECONDS);
            [CtInvocationImpl][CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.logger.info([CtLiteralImpl]"Background storage usage updater will fetch storage usage from remote starting {} ms from now and repeat with interval={} ms", [CtVariableReadImpl]initialDelay, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.refresherPollingIntervalMs);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Subscribe to storage usage change via helix property store. It will update the in memory cache of the storage usage
     * and invoke the callback if present.
     */
    private [CtTypeReferenceImpl]void subscribeToChange() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]helixStore.subscribe([CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.AGGREGATED_CONTAINER_STORAGE_USAGE_PATH, [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.helix.store.HelixPropertyListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onDataChange([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
                [CtInvocationImpl]refreshOnUpdate([CtVariableReadImpl]path);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onDataCreate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
                [CtInvocationImpl]refreshOnUpdate([CtVariableReadImpl]path);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onDataDelete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// This is a no-op when a ZNRecord for aggregated storage usage is deleted.
                [CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.logger.warn([CtLiteralImpl]"StorageUsage is unexpectedly deleted for at path {}", [CtVariableReadImpl]path);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> getContainerStorageUsage() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl][CtFieldReadImpl]containerStorageUsageRef.get());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void registerCallback([CtParameterImpl][CtTypeReferenceImpl]com.github.ambry.quota.Callback cb) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]cb, [CtLiteralImpl]"Callback has to be non-null");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]callback.compareAndSet([CtLiteralImpl]null, [CtVariableReadImpl]cb)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Callback already registered");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch the container storage usage from the given ZooKeeper path and update the in memory cache. Invoke callback
     * if present.
     *
     * @param path
     * 		The ZooKeeper path to fetch the storage usage.
     */
    private [CtTypeReferenceImpl]void refreshOnUpdate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> storageUsage = [CtInvocationImpl]fetchContainerStorageUsageFromPath([CtVariableReadImpl]path);
            [CtInvocationImpl][CtFieldReadImpl]containerStorageUsageRef.set([CtVariableReadImpl]storageUsage);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]callback.get() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]callback.get().onNewContainerStorageUsage([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtVariableReadImpl]storageUsage));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.logger.error([CtLiteralImpl]"Failed to refresh storage usage on update of path = {}", [CtVariableReadImpl]path, [CtVariableReadImpl]e);
            [CtInvocationImpl][CtFieldReadImpl]containerStorageUsageRef.compareAndSet([CtLiteralImpl]null, [CtFieldReadImpl][CtTypeAccessImpl]java.util.Collections.[CtFieldReferenceImpl]EMPTY_MAP);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch container storage usage from the given ZooKeeper path.
     *
     * @param path
     * 		The ZooKeeper path to fetch storage usage.
     * @return  * @throws IOException
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> fetchContainerStorageUsageFromPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.zookeeper.data.Stat stat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.zookeeper.data.Stat();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.helix.zookeeper.datamodel.ZNRecord znRecord = [CtInvocationImpl][CtFieldReadImpl]helixStore.get([CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.AGGREGATED_CONTAINER_STORAGE_USAGE_PATH, [CtVariableReadImpl]stat, [CtTypeAccessImpl]AccessOption.PERSISTENT);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]znRecord == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.logger.info([CtLiteralImpl]"The ZNRecord to read does not exist on path={}", [CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.AGGREGATED_CONTAINER_STORAGE_USAGE_PATH);
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.util.Collections.[CtFieldReferenceImpl]EMPTY_MAP;
        }
        [CtReturnImpl]return [CtInvocationImpl]parseContainerStorageUsageFromZNRecord([CtVariableReadImpl]znRecord);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Parse the container storage usage from given {@link ZNRecord}.
     *
     * @param znRecord
     * 		The {@link ZNRecord} that contains storage usage in json format.
     * @return The map representing container storage usage.
     * @throws IOException
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> parseContainerStorageUsageFromZNRecord([CtParameterImpl][CtTypeReferenceImpl]org.apache.helix.zookeeper.datamodel.ZNRecord znRecord) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.codehaus.jackson.map.ObjectMapper mapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.codehaus.jackson.map.ObjectMapper();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String serializedStr = [CtInvocationImpl][CtVariableReadImpl]znRecord.getSimpleField([CtFieldReadImpl]com.github.ambry.quota.HelixStorageUsageRefresher.VALID_SIZE_FILED_NAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.github.ambry.server.StatsSnapshot statsSnapshot = [CtInvocationImpl][CtVariableReadImpl]mapper.readValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]serializedStr.getBytes()), [CtFieldReadImpl]com.github.ambry.server.StatsSnapshot.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long>> accountStorageUsages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.github.ambry.server.StatsSnapshot> accountSubMap = [CtInvocationImpl][CtVariableReadImpl]statsSnapshot.getSubMap();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.github.ambry.server.StatsSnapshot> accountSubMapEntry : [CtInvocationImpl][CtVariableReadImpl]accountSubMap.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String accountKey = [CtInvocationImpl][CtVariableReadImpl]accountSubMapEntry.getKey();
            [CtLocalVariableImpl][CtCommentImpl]// AccountKey's format is A[accountId], use substring to get the real accountId
            [CtTypeReferenceImpl]java.lang.String accountId = [CtInvocationImpl][CtVariableReadImpl]accountKey.substring([CtLiteralImpl]2, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]accountKey.length() - [CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long> containerUsages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]accountStorageUsages.put([CtVariableReadImpl]accountId, [CtVariableReadImpl]containerUsages);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.github.ambry.server.StatsSnapshot> containerSubMap = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]accountSubMapEntry.getValue().getSubMap();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.github.ambry.server.StatsSnapshot> containerSubMapEntry : [CtInvocationImpl][CtVariableReadImpl]containerSubMap.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String containerKey = [CtInvocationImpl][CtVariableReadImpl]containerSubMapEntry.getKey();
                [CtLocalVariableImpl][CtCommentImpl]// ContainerKey's format is C[containerId], use substring to get the real containerId
                [CtTypeReferenceImpl]java.lang.String containerId = [CtInvocationImpl][CtVariableReadImpl]containerKey.substring([CtLiteralImpl]2, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]containerKey.length() - [CtLiteralImpl]1);
                [CtInvocationImpl][CtVariableReadImpl]containerUsages.put([CtVariableReadImpl]containerId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]containerSubMapEntry.getValue().getValue());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]accountStorageUsages;
    }
}
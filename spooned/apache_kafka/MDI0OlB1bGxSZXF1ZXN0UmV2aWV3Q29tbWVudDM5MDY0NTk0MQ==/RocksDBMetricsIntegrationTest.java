[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.kafka.streams.integration;
[CtUnresolvedImport]import org.apache.kafka.streams.StreamsConfig;
[CtUnresolvedImport]import org.apache.kafka.common.utils.MockTime;
[CtUnresolvedImport]import org.apache.kafka.streams.kstream.Produced;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.kafka.streams.state.Stores;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.runners.Parameterized.Parameters;
[CtUnresolvedImport]import org.apache.kafka.test.IntegrationTest;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import org.apache.kafka.streams.state.WindowStore;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.StringDeserializer;
[CtUnresolvedImport]import org.apache.kafka.streams.integration.utils.EmbeddedKafkaCluster;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import org.apache.kafka.common.Metric;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.IntegerSerializer;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.Serdes;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.StringSerializer;
[CtUnresolvedImport]import static org.hamcrest.MatcherAssert.assertThat;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.junit.ClassRule;
[CtUnresolvedImport]import org.junit.runners.Parameterized;
[CtUnresolvedImport]import org.junit.Before;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.apache.kafka.common.metrics.Sensor;
[CtUnresolvedImport]import org.apache.kafka.streams.StreamsBuilder;
[CtUnresolvedImport]import static org.hamcrest.Matchers.notNullValue;
[CtUnresolvedImport]import org.apache.kafka.test.TestUtils;
[CtUnresolvedImport]import org.apache.kafka.streams.kstream.Consumed;
[CtUnresolvedImport]import org.junit.runners.Parameterized.Parameter;
[CtUnresolvedImport]import org.apache.kafka.streams.KeyValue;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.LongDeserializer;
[CtUnresolvedImport]import org.junit.Test;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import org.junit.experimental.categories.Category;
[CtUnresolvedImport]import org.apache.kafka.common.utils.Bytes;
[CtUnresolvedImport]import org.apache.kafka.streams.integration.utils.IntegrationTestUtils;
[CtUnresolvedImport]import org.apache.kafka.streams.kstream.TimeWindows;
[CtUnresolvedImport]import org.apache.kafka.streams.kstream.Materialized;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.IntegerDeserializer;
[CtUnresolvedImport]import org.apache.kafka.streams.KafkaStreams;
[CtUnresolvedImport]import org.apache.kafka.test.StreamsTestUtils;
[CtUnresolvedImport]import static org.hamcrest.Matchers.is;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtAnnotationImpl]@org.junit.experimental.categories.Category([CtNewArrayImpl]{ [CtFieldReadImpl]org.apache.kafka.test.IntegrationTest.class })
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.Parameterized.class)
public class RocksDBMetricsIntegrationTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int NUM_BROKERS = [CtLiteralImpl]3;

    [CtFieldImpl][CtAnnotationImpl]@org.junit.ClassRule
    public static final [CtTypeReferenceImpl]org.apache.kafka.streams.integration.utils.EmbeddedKafkaCluster CLUSTER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.integration.utils.EmbeddedKafkaCluster([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.NUM_BROKERS);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String STREAM_INPUT = [CtLiteralImpl]"STREAM_INPUT";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String STREAM_OUTPUT = [CtLiteralImpl]"STREAM_OUTPUT";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MY_STORE_PERSISTENT_KEY_VALUE = [CtLiteralImpl]"myStorePersistentKeyValue";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.time.Duration WINDOW_SIZE = [CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtLiteralImpl]50);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long TIMEOUT = [CtLiteralImpl]60000;

    [CtFieldImpl][CtCommentImpl]// RocksDB metrics
    private static final [CtTypeReferenceImpl]java.lang.String METRICS_GROUP = [CtLiteralImpl]"stream-state-metrics";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_WRITTEN_RATE = [CtLiteralImpl]"bytes-written-rate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_WRITTEN_TOTAL = [CtLiteralImpl]"bytes-written-total";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_READ_RATE = [CtLiteralImpl]"bytes-read-rate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_READ_TOTAL = [CtLiteralImpl]"bytes-read-total";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MEMTABLE_BYTES_FLUSHED_RATE = [CtLiteralImpl]"memtable-bytes-flushed-rate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MEMTABLE_BYTES_FLUSHED_TOTAL = [CtLiteralImpl]"memtable-bytes-flushed-total";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MEMTABLE_HIT_RATIO = [CtLiteralImpl]"memtable-hit-ratio";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String WRITE_STALL_DURATION_AVG = [CtLiteralImpl]"write-stall-duration-avg";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String WRITE_STALL_DURATION_TOTAL = [CtLiteralImpl]"write-stall-duration-total";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BLOCK_CACHE_DATA_HIT_RATIO = [CtLiteralImpl]"block-cache-data-hit-ratio";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BLOCK_CACHE_INDEX_HIT_RATIO = [CtLiteralImpl]"block-cache-index-hit-ratio";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BLOCK_CACHE_FILTER_HIT_RATIO = [CtLiteralImpl]"block-cache-filter-hit-ratio";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_READ_DURING_COMPACTION_RATE = [CtLiteralImpl]"bytes-read-compaction-rate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BYTES_WRITTEN_DURING_COMPACTION_RATE = [CtLiteralImpl]"bytes-written-compaction-rate";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NUMBER_OF_OPEN_FILES = [CtLiteralImpl]"number-open-files";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NUMBER_OF_FILE_ERRORS = [CtLiteralImpl]"number-file-errors-total";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.runners.Parameterized.Parameters(name = [CtLiteralImpl]"{0}")
    public static [CtTypeReferenceImpl]java.util.Collection<[CtArrayTypeReferenceImpl]java.lang.Object[]> data() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[][]{ [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.apache.kafka.streams.StreamsConfig.EXACTLY_ONCE }, [CtNewArrayImpl]new java.lang.Object[]{ [CtFieldReadImpl]org.apache.kafka.streams.StreamsConfig.AT_LEAST_ONCE } });
    }

    [CtFieldImpl][CtAnnotationImpl]@org.junit.runners.Parameterized.Parameter
    public [CtTypeReferenceImpl]java.lang.String processingGuarantee;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void before() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.createTopic([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtLiteralImpl]1, [CtLiteralImpl]3);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void after() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.deleteTopicsAndWait([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_OUTPUT);
    }

    [CtInterfaceImpl][CtAnnotationImpl]@java.lang.FunctionalInterface
    private interface MetricsVerifier {
        [CtMethodImpl][CtTypeReferenceImpl]void verify([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams kafkaStreams, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricScope) throws [CtTypeReferenceImpl]java.lang.Exception;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldExposeRocksDBMetricsForNonSegmentedStateStoreBeforeAndAfterFailureWithEmptyStateDir() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration = [CtInvocationImpl]streamsConfig();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.purgeLocalStreamsState([CtVariableReadImpl]streamsConfiguration);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtInvocationImpl]builderForNonSegmentedStateStore();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope = [CtLiteralImpl]"rocksdb-state-id";
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatRocksDBMetricsAreExposed, [CtVariableReadImpl]metricsScope);
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatRocksDBMetricsAreExposed, [CtVariableReadImpl]metricsScope);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldExposeRocksDBMetricsForSegmentedStateStoreBeforeAndAfterFailureWithEmptyStateDir() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration = [CtInvocationImpl]streamsConfig();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.purgeLocalStreamsState([CtVariableReadImpl]streamsConfiguration);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtInvocationImpl]builderForSegmentedStateStore();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope = [CtLiteralImpl]"rocksdb-window-state-id";
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatRocksDBMetricsAreExposed, [CtVariableReadImpl]metricsScope);
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatRocksDBMetricsAreExposed, [CtVariableReadImpl]metricsScope);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldVerifyThatMetricsGetMeasurementsFromRocksDBForNonSegmentedStateStore() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration = [CtInvocationImpl]streamsConfig();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.purgeLocalStreamsState([CtVariableReadImpl]streamsConfiguration);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtInvocationImpl]builderForNonSegmentedStateStore();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope = [CtLiteralImpl]"rocksdb-state-id";
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatBytesWrittenTotalIncreases, [CtVariableReadImpl]metricsScope);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldVerifyThatMetricsGetMeasurementsFromRocksDBForSegmentedStateStore() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration = [CtInvocationImpl]streamsConfig();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.purgeLocalStreamsState([CtVariableReadImpl]streamsConfiguration);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtInvocationImpl]builderForSegmentedStateStore();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope = [CtLiteralImpl]"rocksdb-window-state-id";
        [CtInvocationImpl]cleanUpStateRunAndVerify([CtVariableReadImpl]builder, [CtVariableReadImpl]streamsConfiguration, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.LongDeserializer.class, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::verifyThatBytesWrittenTotalIncreases, [CtVariableReadImpl]metricsScope);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Properties streamsConfig() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.APPLICATION_ID_CONFIG, [CtLiteralImpl]"test-application");
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, [CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.bootstrapServers());
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.Integer().getClass());
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.String().getClass());
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.METRICS_RECORDING_LEVEL_CONFIG, [CtTypeAccessImpl]Sensor.RecordingLevel.DEBUG.name);
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.PROCESSING_GUARANTEE_CONFIG, [CtFieldReadImpl]processingGuarantee);
        [CtInvocationImpl][CtVariableReadImpl]streamsConfiguration.put([CtTypeAccessImpl]StreamsConfig.STATE_DIR_CONFIG, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.tempDirectory().getPath());
        [CtReturnImpl]return [CtVariableReadImpl]streamsConfiguration;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builderForNonSegmentedStateStore() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.table([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.kstream.Materialized.as([CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.state.Stores.persistentKeyValueStore([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.MY_STORE_PERSISTENT_KEY_VALUE)).withCachingEnabled()).toStream().to([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_OUTPUT);
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builderForSegmentedStateStore() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.stream([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.kstream.Consumed.with([CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.Integer(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.String())).groupByKey().windowedBy([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.kstream.TimeWindows.of([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.WINDOW_SIZE).grace([CtFieldReadImpl][CtTypeAccessImpl]java.time.Duration.[CtFieldReferenceImpl]ZERO)).aggregate([CtLambdaImpl]() -> [CtLiteralImpl]0L, [CtLambdaImpl]([CtParameterImpl] aggKey,[CtParameterImpl] newValue,[CtParameterImpl] aggValue) -> [CtVariableReadImpl]aggValue, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.kstream.Materialized.<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]org.apache.kafka.streams.state.WindowStore<[CtTypeReferenceImpl]org.apache.kafka.common.utils.Bytes, [CtArrayTypeReferenceImpl]byte[]>>as([CtLiteralImpl]"time-windowed-aggregated-stream-store").withValueSerde([CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.Long()).withRetention([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.WINDOW_SIZE)).toStream().map([CtLambdaImpl]([CtParameterImpl] key,[CtParameterImpl] value) -> [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.KeyValue.pair([CtVariableReadImpl]value, [CtVariableReadImpl]value)).to([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_OUTPUT, [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.kstream.Produced.with([CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.Long(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.common.serialization.Serdes.Long()));
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void cleanUpStateRunAndVerify([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.StreamsBuilder builder, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Properties streamsConfiguration, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class outputKeyDeserializer, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class outputValueDeserializer, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.MetricsVerifier metricsVerifier, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams kafkaStreams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams([CtInvocationImpl][CtVariableReadImpl]builder.build(), [CtVariableReadImpl]streamsConfiguration);
        [CtInvocationImpl][CtVariableReadImpl]kafkaStreams.cleanUp();
        [CtInvocationImpl]produceRecords();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.StreamsTestUtils.startKafkaStreamsAndWaitForRunningState([CtVariableReadImpl]kafkaStreams, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.TIMEOUT);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.waitUntilMinKeyValueRecordsReceived([CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.consumerConfig([CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.bootstrapServers(), [CtLiteralImpl]"consumerApp", [CtVariableReadImpl]outputKeyDeserializer, [CtVariableReadImpl]outputValueDeserializer, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties()), [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_OUTPUT, [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]metricsVerifier.verify([CtVariableReadImpl]kafkaStreams, [CtVariableReadImpl]metricsScope);
        [CtInvocationImpl][CtVariableReadImpl]kafkaStreams.close();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void produceRecords() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.common.utils.MockTime mockTime = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.common.utils.MockTime([CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.WINDOW_SIZE.toMillis());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.produceKeyValuesSynchronouslyWithTimestamp([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.KeyValue<>([CtLiteralImpl]1, [CtLiteralImpl]"A")), [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.producerConfig([CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.bootstrapServers(), [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerSerializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringSerializer.class, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties()), [CtInvocationImpl][CtVariableReadImpl]mockTime.milliseconds());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.produceKeyValuesSynchronouslyWithTimestamp([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.KeyValue<>([CtLiteralImpl]1, [CtLiteralImpl]"B")), [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.producerConfig([CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.bootstrapServers(), [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerSerializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringSerializer.class, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties()), [CtInvocationImpl][CtVariableReadImpl]mockTime.milliseconds());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.streams.integration.utils.IntegrationTestUtils.produceKeyValuesSynchronouslyWithTimestamp([CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.STREAM_INPUT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.kafka.streams.KeyValue<>([CtLiteralImpl]1, [CtLiteralImpl]"C")), [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.producerConfig([CtInvocationImpl][CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.CLUSTER.bootstrapServers(), [CtFieldReadImpl]org.apache.kafka.common.serialization.IntegerSerializer.class, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringSerializer.class, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties()), [CtInvocationImpl][CtVariableReadImpl]mockTime.milliseconds());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void verifyThatRocksDBMetricsAreExposed([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams kafkaStreams, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.kafka.common.Metric> listMetricStore = [CtInvocationImpl]getRocksDBMetrics([CtVariableReadImpl]kafkaStreams, [CtVariableReadImpl]metricsScope);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_WRITTEN_RATE, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_WRITTEN_TOTAL, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_READ_RATE, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_READ_TOTAL, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.MEMTABLE_BYTES_FLUSHED_RATE, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.MEMTABLE_BYTES_FLUSHED_TOTAL, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.MEMTABLE_HIT_RATIO, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.WRITE_STALL_DURATION_AVG, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.WRITE_STALL_DURATION_TOTAL, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BLOCK_CACHE_DATA_HIT_RATIO, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BLOCK_CACHE_INDEX_HIT_RATIO, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BLOCK_CACHE_FILTER_HIT_RATIO, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_READ_DURING_COMPACTION_RATE, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.BYTES_WRITTEN_DURING_COMPACTION_RATE, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.NUMBER_OF_OPEN_FILES, [CtLiteralImpl]1);
        [CtInvocationImpl]checkMetricByName([CtVariableReadImpl]listMetricStore, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.NUMBER_OF_FILE_ERRORS, [CtLiteralImpl]1);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkMetricByName([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.kafka.common.Metric> listMetric, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricName, [CtParameterImpl]final [CtTypeReferenceImpl]int numMetric) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.kafka.common.Metric> metrics = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]listMetric.stream().filter([CtLambdaImpl]([CtParameterImpl] m) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.metricName().name().equals([CtVariableReadImpl]metricName)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl]MatcherAssert.assertThat([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Size of metrics of type:'" + [CtVariableReadImpl]metricName) + [CtLiteralImpl]"' must be equal to ") + [CtVariableReadImpl]numMetric) + [CtLiteralImpl]" but it's equal to ") + [CtInvocationImpl][CtVariableReadImpl]metrics.size(), [CtInvocationImpl][CtVariableReadImpl]metrics.size(), [CtInvocationImpl]Matchers.is([CtVariableReadImpl]numMetric));
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.kafka.common.Metric metric : [CtVariableReadImpl]metrics) [CtBlockImpl]{
            [CtInvocationImpl]MatcherAssert.assertThat([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Metric:'" + [CtInvocationImpl][CtVariableReadImpl]metric.metricName()) + [CtLiteralImpl]"' must be not null", [CtInvocationImpl][CtVariableReadImpl]metric.metricValue(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.notNullValue()));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void verifyThatBytesWrittenTotalIncreases([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams kafkaStreams, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.kafka.common.Metric> metric = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRocksDBMetrics([CtVariableReadImpl]kafkaStreams, [CtVariableReadImpl]metricsScope).stream().filter([CtLambdaImpl]([CtParameterImpl] m) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]BYTES_WRITTEN_TOTAL.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.metricName().name())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.kafka.test.TestUtils.waitForCondition([CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]double) ([CtInvocationImpl][CtVariableReadImpl]metric.get([CtLiteralImpl]0).metricValue())) > [CtLiteralImpl]0, [CtFieldReadImpl]org.apache.kafka.streams.integration.RocksDBMetricsIntegrationTest.TIMEOUT, [CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"RocksDB metric bytes.written.total did not increase in " + [CtFieldReadImpl][CtFieldReferenceImpl]TIMEOUT) + [CtLiteralImpl]" ms");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.kafka.common.Metric> getRocksDBMetrics([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.kafka.streams.KafkaStreams kafkaStreams, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String metricsScope) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.apache.kafka.common.Metric>([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]kafkaStreams.metrics().values()).stream().filter([CtLambdaImpl]([CtParameterImpl] m) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.metricName().group().equals([CtFieldReadImpl][CtFieldReferenceImpl]METRICS_GROUP) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.metricName().tags().containsKey([CtVariableReadImpl]metricsScope)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }
}
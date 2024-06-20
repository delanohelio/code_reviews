[CompilationUnitImpl][CtJavaDocImpl]/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
[CtPackageDeclarationImpl]package org.apache.pulsar.sql.presto;
[CtUnresolvedImport]import org.apache.pulsar.common.api.proto.PulsarApi;
[CtUnresolvedImport]import org.apache.pulsar.common.naming.TopicName;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import org.testng.annotations.DataProvider;
[CtUnresolvedImport]import io.airlift.log.Logger;
[CtUnresolvedImport]import org.apache.bookkeeper.mledger.impl.EntryImpl;
[CtUnresolvedImport]import io.prestosql.spi.connector.ConnectorContext;
[CtUnresolvedImport]import org.apache.pulsar.common.partition.PartitionedTopicMetadata;
[CtUnresolvedImport]import org.testng.annotations.AfterMethod;
[CtUnresolvedImport]import javax.ws.rs.ClientErrorException;
[CtUnresolvedImport]import static org.apache.pulsar.common.protocol.Commands.serializeMetadataAndPayload;
[CtUnresolvedImport]import org.apache.pulsar.common.schema.SchemaType;
[CtImportImpl]import java.time.LocalDate;
[CtUnresolvedImport]import org.apache.pulsar.common.protocol.Commands;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import org.mockito.stubbing.Answer;
[CtUnresolvedImport]import org.mockito.invocation.InvocationOnMock;
[CtUnresolvedImport]import io.netty.buffer.ByteBuf;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import org.apache.pulsar.client.api.schema.SchemaDefinition;
[CtUnresolvedImport]import org.apache.pulsar.common.naming.NamespaceName;
[CtUnresolvedImport]import org.apache.pulsar.client.impl.schema.AvroSchema;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtUnresolvedImport]import org.apache.bookkeeper.mledger.impl.PositionImpl;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.time.LocalTime;
[CtUnresolvedImport]import org.apache.bookkeeper.stats.NullStatsProvider;
[CtUnresolvedImport]import io.prestosql.spi.connector.ColumnMetadata;
[CtUnresolvedImport]import io.prestosql.spi.predicate.TupleDomain;
[CtUnresolvedImport]import org.testng.annotations.BeforeMethod;
[CtUnresolvedImport]import static org.testng.Assert.assertNotNull;
[CtUnresolvedImport]import org.apache.bookkeeper.mledger.proto.MLDataFormats;
[CtImportImpl]import java.time.ZoneId;
[CtUnresolvedImport]import static org.mockito.Mockito.*;
[CtUnresolvedImport]import org.apache.pulsar.client.api.Schema;
[CtImportImpl]import java.time.temporal.ChronoUnit;
[CtUnresolvedImport]import org.mockito.Mockito;
[CtUnresolvedImport]import org.apache.bookkeeper.mledger.impl.ReadOnlyCursorImpl;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import io.prestosql.testing.TestingConnectorContext;
[CtUnresolvedImport]import org.apache.pulsar.client.impl.schema.JSONSchema;
[CtUnresolvedImport]import org.apache.bookkeeper.mledger.*;
[CtUnresolvedImport]import org.apache.pulsar.common.schema.SchemaInfo;
[CtUnresolvedImport]import org.apache.pulsar.client.admin.*;
[CtClassImpl]public abstract class TestPulsarConnector {
    [CtFieldImpl]protected static final [CtTypeReferenceImpl]long currentTimeMs = [CtLiteralImpl]1534806330000L;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarConnectorConfig pulsarConnectorConfig;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarMetadata pulsarMetadata;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarAdmin pulsarAdmin;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.Schemas schemas;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarSplitManager pulsarSplitManager;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName, [CtTypeReferenceImpl]PulsarRecordCursor> pulsarRecordCursors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]protected static [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarDispatchingRowDecoderFactory dispatchingRowDecoderFactory;

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarConnectorId pulsarConnectorId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarConnectorId([CtLiteralImpl]"test-connector");

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName> topicNames;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName> partitionedTopicNames;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> partitionedTopicsToPartitions;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.pulsar.common.schema.SchemaInfo> topicsToSchemas;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Long> topicsToNumEntries;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper objectMapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> fooFieldNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.NamespaceName NAMESPACE_NAME_1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.NamespaceName.get([CtLiteralImpl]"tenant-1", [CtLiteralImpl]"ns-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.NamespaceName NAMESPACE_NAME_2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.NamespaceName.get([CtLiteralImpl]"tenant-1", [CtLiteralImpl]"ns-2");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.NamespaceName NAMESPACE_NAME_3 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.NamespaceName.get([CtLiteralImpl]"tenant-2", [CtLiteralImpl]"ns-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.NamespaceName NAMESPACE_NAME_4 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.NamespaceName.get([CtLiteralImpl]"tenant-2", [CtLiteralImpl]"ns-2");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_1, [CtLiteralImpl]"topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_1, [CtLiteralImpl]"topic-2");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_3 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_2, [CtLiteralImpl]"topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_4 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_3, [CtLiteralImpl]"topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_5 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_4, [CtLiteralImpl]"topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName TOPIC_6 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_4, [CtLiteralImpl]"topic-2");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName NON_SCHEMA_TOPIC = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_2, [CtLiteralImpl]"non-schema-topic");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_1, [CtLiteralImpl]"partitioned-topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_1, [CtLiteralImpl]"partitioned-topic-2");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_3 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_2, [CtLiteralImpl]"partitioned-topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_4 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_3, [CtLiteralImpl]"partitioned-topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_5 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_4, [CtLiteralImpl]"partitioned-topic-1");

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName PARTITIONED_TOPIC_6 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtLiteralImpl]"persistent", [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NAMESPACE_NAME_4, [CtLiteralImpl]"partitioned-topic-2");

    [CtClassImpl]public static class Foo {
        [CtEnumImpl]public enum TestEnum {

            [CtEnumValueImpl]TEST_ENUM_1,
            [CtEnumValueImpl]TEST_ENUM_2,
            [CtEnumValueImpl]TEST_ENUM_3;}

        [CtFieldImpl]public [CtTypeReferenceImpl]int field1;

        [CtFieldImpl]public [CtTypeReferenceImpl]java.lang.String field2;

        [CtFieldImpl]public [CtTypeReferenceImpl]float field3;

        [CtFieldImpl]public [CtTypeReferenceImpl]double field4;

        [CtFieldImpl]public [CtTypeReferenceImpl]boolean field5;

        [CtFieldImpl]public [CtTypeReferenceImpl]long field6;

        [CtFieldImpl][CtAnnotationImpl]@org.apache.avro.reflect.AvroSchema([CtLiteralImpl]"{ \"type\": \"long\", \"logicalType\": \"timestamp-millis\" }")
        public [CtTypeReferenceImpl]long timestamp;

        [CtFieldImpl][CtAnnotationImpl]@org.apache.avro.reflect.AvroSchema([CtLiteralImpl]"{ \"type\": \"int\", \"logicalType\": \"time-millis\" }")
        public [CtTypeReferenceImpl]int time;

        [CtFieldImpl][CtAnnotationImpl]@org.apache.avro.reflect.AvroSchema([CtLiteralImpl]"{ \"type\": \"int\", \"logicalType\": \"date\" }")
        public [CtTypeReferenceImpl]int date;

        [CtFieldImpl]public [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Bar bar;

        [CtFieldImpl]public [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.TestEnum field7;
    }

    [CtClassImpl]public static class Bar {
        [CtFieldImpl]public [CtTypeReferenceImpl]java.lang.Integer field1;

        [CtFieldImpl]public [CtTypeReferenceImpl]java.lang.String field2;

        [CtFieldImpl]public [CtTypeReferenceImpl]float field3;
    }

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PulsarColumnHandle>> topicsToColumnHandles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName, [CtTypeReferenceImpl]PulsarSplit> splits;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.Object>> fooFunctions;

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NON_SCHEMA_TOPIC);
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.add([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6);
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1.toString(), [CtLiteralImpl]2);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2.toString(), [CtLiteralImpl]3);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3.toString(), [CtLiteralImpl]4);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4.toString(), [CtLiteralImpl]5);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5.toString(), [CtLiteralImpl]6);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6.toString(), [CtLiteralImpl]7);
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.AVRO([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6.getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.Schema.JSON([CtFieldReadImpl]TestPulsarMetadata.Foo.class).getSchemaInfo());
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1.getSchemaName(), [CtLiteralImpl]1233L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2.getSchemaName(), [CtLiteralImpl]0L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3.getSchemaName(), [CtLiteralImpl]100L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4.getSchemaName(), [CtLiteralImpl]12345L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5.getSchemaName(), [CtLiteralImpl]8000L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6.getSchemaName(), [CtLiteralImpl]1L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.NON_SCHEMA_TOPIC.getSchemaName(), [CtLiteralImpl]8000L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1.getSchemaName(), [CtLiteralImpl]1233L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2.getSchemaName(), [CtLiteralImpl]8000L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3.getSchemaName(), [CtLiteralImpl]100L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4.getSchemaName(), [CtLiteralImpl]0L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5.getSchemaName(), [CtLiteralImpl]800L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.put([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6.getSchemaName(), [CtLiteralImpl]1L);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field1");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field2");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field3");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field4");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field5");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field6");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"timestamp");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"time");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"date");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"bar");
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFieldNames.add([CtLiteralImpl]"field7");
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.prestosql.spi.connector.ConnectorContext prestoConnectorContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.testing.TestingConnectorContext();
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarDispatchingRowDecoderFactory([CtInvocationImpl][CtVariableReadImpl]prestoConnectorContext.getTypeManager());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_1.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_2.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_3.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_4.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_5.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.PARTITIONED_TOPIC_6.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_1.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_2.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_3.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_4.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_5.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.put([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6, [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getColumnColumnHandles([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.TOPIC_6.getSchemaName()), [CtTypeAccessImpl]PulsarColumnHandle.HandleKeyValueType.NONE, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.splits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName> allTopics = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
            [CtInvocationImpl][CtVariableReadImpl]allTopics.addAll([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames);
            [CtInvocationImpl][CtVariableReadImpl]allTopics.addAll([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName topicName : [CtVariableReadImpl]allTopics) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.containsKey([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName())) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.splits.put([CtVariableReadImpl]topicName, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarSplit([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.pulsarConnectorId.toString(), [CtInvocationImpl][CtVariableReadImpl]topicName.getNamespace(), [CtInvocationImpl][CtVariableReadImpl]topicName.getLocalName(), [CtInvocationImpl][CtVariableReadImpl]topicName.getLocalName(), [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.get([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName()).getSchema()), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName()).getType(), [CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.get([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName()), [CtLiteralImpl]0, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]io.prestosql.spi.predicate.TupleDomain.all(), [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.objectMapper.writeValueAsString([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtInvocationImpl][CtVariableReadImpl]topicName.getSchemaName()).getProperties()), [CtLiteralImpl]null));
                }
            }
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field1", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtVariableReadImpl]integer);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field2", [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field3", [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Integer::floatValue);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field4", [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Integer::doubleValue);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field5", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]integer % [CtLiteralImpl]2) == [CtLiteralImpl]0);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field6", [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Integer::longValue);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"timestamp", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"time", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalTime now = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalTime.now([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault());
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]now.toSecondOfDay() * [CtLiteralImpl]1000;
            });
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"date", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate localDate = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDate.now();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate epoch = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDate.ofEpochDay([CtLiteralImpl]0);
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toIntExact([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtVariableReadImpl]epoch, [CtVariableReadImpl]localDate));
            });
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"bar.field1", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]integer % [CtLiteralImpl]3) == [CtLiteralImpl]0 ? [CtLiteralImpl]null : [CtBinaryOperatorImpl][CtVariableReadImpl]integer + [CtLiteralImpl]1);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"bar.field2", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]integer % [CtLiteralImpl]2) == [CtLiteralImpl]0 ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]integer + [CtLiteralImpl]2));
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"bar.field3", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtBinaryOperatorImpl][CtVariableReadImpl]integer + [CtLiteralImpl]3.0F);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.put([CtLiteralImpl]"field7", [CtLambdaImpl]([CtParameterImpl]java.lang.Integer integer) -> [CtArrayReadImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.TestEnum.values()[[CtBinaryOperatorImpl][CtVariableReadImpl]integer % [CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.TestEnum.values().length]);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Error: " + [CtVariableReadImpl]e);
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Stacktrace: " + [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]e.getStackTrace()));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Parse PulsarColumnMetadata to PulsarColumnHandle Util
     *
     * @param schemaInfo
     * @param handleKeyValueType
     * @param includeInternalColumn
     * @param dispatchingRowDecoderFactory
     * @return  */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PulsarColumnHandle> getColumnColumnHandles([CtParameterImpl][CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName topicName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.pulsar.common.schema.SchemaInfo schemaInfo, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]PulsarColumnHandle.HandleKeyValueType handleKeyValueType, [CtParameterImpl][CtTypeReferenceImpl]boolean includeInternalColumn, [CtParameterImpl][CtTypeReferenceImpl]PulsarDispatchingRowDecoderFactory dispatchingRowDecoderFactory) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]PulsarColumnHandle> columnHandles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.prestosql.spi.connector.ColumnMetadata> columnMetadata = [CtInvocationImpl][CtVariableReadImpl]dispatchingRowDecoderFactory.extractColumnMetadata([CtVariableReadImpl]topicName, [CtVariableReadImpl]schemaInfo, [CtVariableReadImpl]handleKeyValueType);
        [CtInvocationImpl][CtVariableReadImpl]columnMetadata.forEach([CtLambdaImpl]([CtParameterImpl] column) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PulsarColumnMetadata pulsarColumnMetadata = [CtVariableReadImpl](([CtTypeReferenceImpl]PulsarColumnMetadata) (column));
            [CtInvocationImpl][CtVariableReadImpl]columnHandles.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarColumnHandle([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]pulsarConnectorId.toString(), [CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getNameWithCase(), [CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getType(), [CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.isHidden(), [CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.isInternal(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getDecoderExtraInfo().getMapping(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getDecoderExtraInfo().getDataFormat(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getDecoderExtraInfo().getFormatHint(), [CtInvocationImpl][CtVariableReadImpl]pulsarColumnMetadata.getHandleKeyValueType()));
        });
        [CtIfImpl]if ([CtVariableReadImpl]includeInternalColumn) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]columnHandles.addAll([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]PulsarInternalColumn.getInternalFields().stream().map([CtLambdaImpl]([CtParameterImpl] pulsarInternalColumn) -> [CtInvocationImpl][CtVariableReadImpl]pulsarInternalColumn.getColumnHandle([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]pulsarConnectorId.toString(), [CtLiteralImpl]false)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]columnHandles;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.PulsarConnectorId getPulsarConnectorId() [CtBlockImpl]{
        [CtInvocationImpl]Assert.assertNotNull([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.pulsarConnectorId);
        [CtReturnImpl]return [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.pulsarConnectorId;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Entry> getTopicEntries([CtParameterImpl][CtTypeReferenceImpl]java.lang.String topicSchemaName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Entry> entries = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.get([CtVariableReadImpl]topicSchemaName);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]count; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo foo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field1 = [CtVariableReadImpl](([CtTypeReferenceImpl]int) (count));
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field2 = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]count);
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field3 = [CtVariableReadImpl]count;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field4 = [CtVariableReadImpl]count;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field5 = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]count % [CtLiteralImpl]2) == [CtLiteralImpl]0;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field6 = [CtVariableReadImpl]count;
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.timestamp = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalTime now = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalTime.now([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault());
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.time = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]now.toSecondOfDay() * [CtLiteralImpl]1000;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate localDate = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDate.now();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate epoch = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDate.ofEpochDay([CtLiteralImpl]0);
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.date = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toIntExact([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtVariableReadImpl]epoch, [CtVariableReadImpl]localDate));
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.common.api.proto.PulsarApi.MessageMetadata messageMetadata = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]PulsarApi.MessageMetadata.newBuilder().setProducerName([CtLiteralImpl]"test-producer").setSequenceId([CtVariableReadImpl]i).setPublishTime([CtBinaryOperatorImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.currentTimeMs + [CtVariableReadImpl]i).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pulsar.client.api.Schema schema = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtVariableReadImpl]topicSchemaName).getType() == [CtFieldReadImpl]org.apache.pulsar.common.schema.SchemaType.AVRO) ? [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.impl.schema.AvroSchema.of([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.schema.SchemaDefinition.<[CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo>builder().withPojo([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.class).build()) : [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.impl.schema.JSONSchema.of([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.api.schema.SchemaDefinition.<[CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo>builder().withPojo([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.class).build());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf payload = [CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtInvocationImpl][CtVariableReadImpl]schema.encode([CtVariableReadImpl]foo));
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf byteBuf = [CtInvocationImpl]serializeMetadataAndPayload([CtTypeAccessImpl]Commands.ChecksumType.Crc32c, [CtVariableReadImpl]messageMetadata, [CtVariableReadImpl]payload);
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entry entry = [CtInvocationImpl][CtTypeAccessImpl]org.apache.bookkeeper.mledger.impl.EntryImpl.create([CtLiteralImpl]0, [CtVariableReadImpl]i, [CtVariableReadImpl]byteBuf);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.log.info([CtLiteralImpl]"create entry: %s", [CtInvocationImpl][CtVariableReadImpl]entry.getEntryId());
            [CtInvocationImpl][CtVariableReadImpl]entries.add([CtVariableReadImpl]entry);
        }
        [CtReturnImpl]return [CtVariableReadImpl]entries;
    }

    [CtFieldImpl]public [CtTypeReferenceImpl]long completedBytes = [CtLiteralImpl]0L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.airlift.log.Logger log = [CtInvocationImpl][CtTypeAccessImpl]io.airlift.log.Logger.get([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.class);

    [CtMethodImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getNamespace([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenant) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.stream().filter([CtLambdaImpl]([CtParameterImpl] topicName) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]topicName.getTenant().equals([CtVariableReadImpl]tenant)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]TopicName::getNamespace).distinct().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.LinkedList::new));
    }

    [CtMethodImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getTopics([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ns) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> topics = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.stream().filter([CtLambdaImpl]([CtParameterImpl] topicName) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]topicName.getNamespace().equals([CtVariableReadImpl]ns)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]TopicName::toString).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.stream().filter([CtLambdaImpl]([CtParameterImpl] topicName) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]topicName.getNamespace().equals([CtVariableReadImpl]ns)).forEach([CtLambdaImpl]([CtParameterImpl] topicName) -> [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.get([CtInvocationImpl][CtVariableReadImpl]topicName.toString()); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]topics.add([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]topicName + [CtLiteralImpl]"-partition-") + [CtVariableReadImpl]i).toString());
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]topics;
    }

    [CtMethodImpl]protected static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getPartitionedTopics([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ns) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicNames.stream().filter([CtLambdaImpl]([CtParameterImpl] topicName) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]topicName.getNamespace().equals([CtVariableReadImpl]ns)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]TopicName::toString).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.BeforeMethod
    public [CtTypeReferenceImpl]void setup() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pulsarConnectorConfig = [CtInvocationImpl]spy([CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarConnectorConfig());
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig.setMaxEntryReadBatchSize([CtLiteralImpl]1);
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig.setMaxSplitEntryQueueSize([CtLiteralImpl]10);
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig.setMaxSplitMessageQueueSize([CtLiteralImpl]100);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Tenants tenants = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.Tenants.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicNames.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]TopicName::getTenant).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet()))).when([CtVariableReadImpl]tenants).getTenants();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Namespaces namespaces = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.Namespaces.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]namespaces.getNamespaces([CtInvocationImpl]anyString())).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocation) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocation.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tenant = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> ns = [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getNamespace([CtVariableReadImpl]tenant);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ns.isEmpty()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarAdminException([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.ClientErrorException([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.status([CtLiteralImpl]404).build()));
                }
                [CtReturnImpl]return [CtVariableReadImpl]ns;
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]Topics topics = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.Topics.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]topics.getList([CtInvocationImpl]anyString())).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ns = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> topics = [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getTopics([CtVariableReadImpl]ns);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]topics.isEmpty()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarAdminException([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.ClientErrorException([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.status([CtLiteralImpl]404).build()));
                }
                [CtReturnImpl]return [CtVariableReadImpl]topics;
            }
        });
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]topics.getPartitionedTopicList([CtInvocationImpl]anyString())).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ns = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> topics = [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getPartitionedTopics([CtVariableReadImpl]ns);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]topics.isEmpty()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarAdminException([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.ClientErrorException([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.status([CtLiteralImpl]404).build()));
                }
                [CtReturnImpl]return [CtVariableReadImpl]topics;
            }
        });
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]topics.getPartitionedTopicMetadata([CtInvocationImpl]anyString())).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]org.apache.pulsar.common.partition.PartitionedTopicMetadata>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.apache.pulsar.common.partition.PartitionedTopicMetadata answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String topic = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]int partitions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.get([CtVariableReadImpl]topic) == [CtLiteralImpl]null) ? [CtLiteralImpl]0 : [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.partitionedTopicsToPartitions.get([CtVariableReadImpl]topic);
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pulsar.common.partition.PartitionedTopicMetadata([CtVariableReadImpl]partitions);
            }
        });
        [CtAssignmentImpl][CtFieldWriteImpl]schemas = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.Schemas.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtFieldReadImpl]schemas.getSchemaInfo([CtInvocationImpl]anyString())).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]org.apache.pulsar.common.schema.SchemaInfo>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.apache.pulsar.common.schema.SchemaInfo answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String topic = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtVariableReadImpl]topic) != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtVariableReadImpl]topic);
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarAdminException([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.ClientErrorException([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.status([CtLiteralImpl]404).build()));
                }
            }
        });
        [CtAssignmentImpl][CtFieldWriteImpl]pulsarAdmin = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.PulsarAdmin.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtVariableReadImpl]tenants).when([CtFieldReadImpl]pulsarAdmin).tenants();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtVariableReadImpl]namespaces).when([CtFieldReadImpl]pulsarAdmin).namespaces();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtVariableReadImpl]topics).when([CtFieldReadImpl]pulsarAdmin).topics();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtFieldReadImpl]schemas).when([CtFieldReadImpl]pulsarAdmin).schemas();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtFieldReadImpl]pulsarAdmin).when([CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig).getPulsarAdmin();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pulsarMetadata = [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarMetadata([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.pulsarConnectorId, [CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig, [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pulsarSplitManager = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.spy([CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarSplitManager([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.pulsarConnectorId, [CtFieldReadImpl][CtThisAccessImpl]this.pulsarConnectorConfig));
        [CtLocalVariableImpl][CtTypeReferenceImpl]ManagedLedgerFactory managedLedgerFactory = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.ManagedLedgerFactory.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]managedLedgerFactory.openReadOnlyCursor([CtInvocationImpl]any(), [CtInvocationImpl]any(), [CtInvocationImpl]any())).then([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]ReadOnlyCursor>()[CtClassImpl] {
            [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> positions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

            [CtFieldImpl]private [CtTypeReferenceImpl]int count = [CtLiteralImpl]0;

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.ReadOnlyCursor answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String topic = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl positionImpl = [CtArrayReadImpl](([CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl) ([CtVariableReadImpl]args[[CtLiteralImpl]1]));
                [CtLocalVariableImpl][CtTypeReferenceImpl]int position = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]positionImpl.getEntryId() == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) ? [CtLiteralImpl]0 : [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]positionImpl.getEntryId()));
                [CtInvocationImpl][CtFieldReadImpl]positions.put([CtVariableReadImpl]topic, [CtVariableReadImpl]position);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String schemaName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtInvocationImpl][CtVariableReadImpl]topic.replaceAll([CtLiteralImpl]"/persistent", [CtLiteralImpl]"")).getPartitionedTopicName()).getSchemaName();
                [CtLocalVariableImpl][CtTypeReferenceImpl]long entries = [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToNumEntries.get([CtVariableReadImpl]schemaName);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.ReadOnlyCursorImpl readOnlyCursor = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.bookkeeper.mledger.impl.ReadOnlyCursorImpl.class);
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtVariableReadImpl]entries).when([CtVariableReadImpl]readOnlyCursor).getNumberOfEntries();
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.lang.Void>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.Void answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocation) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocation.getArguments();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer skipEntries = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Integer) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                        [CtInvocationImpl][CtFieldReadImpl]positions.put([CtVariableReadImpl]topic, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic) + [CtVariableReadImpl]skipEntries);
                        [CtReturnImpl]return [CtLiteralImpl]null;
                    }
                }).when([CtVariableReadImpl]readOnlyCursor).skipEntries([CtInvocationImpl]anyInt());
                [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]readOnlyCursor.getReadPosition()).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.bookkeeper.mledger.impl.PositionImpl.get([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic));
                    }
                });
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.Object answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer readEntries = [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Integer) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]AsyncCallbacks.ReadEntriesCallback callback = [CtArrayReadImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]AsyncCallbacks.ReadEntriesCallback) ([CtVariableReadImpl]args[[CtLiteralImpl]1]));
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object ctx = [CtArrayReadImpl][CtVariableReadImpl]args[[CtLiteralImpl]2];
                        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Entry> entries = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
                                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]readEntries; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Bar bar = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Bar();
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]bar.field1 = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"bar.field1").apply([CtFieldReadImpl]count) == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"bar.field1").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]bar.field2 = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"bar.field2").apply([CtFieldReadImpl]count) == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"bar.field2").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]bar.field3 = [CtInvocationImpl](([CtTypeReferenceImpl]float) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"bar.field3").apply([CtFieldReadImpl]count)));
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo foo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo();
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field1 = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field1").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field2 = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field2").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field3 = [CtInvocationImpl](([CtTypeReferenceImpl]float) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field3").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field4 = [CtInvocationImpl](([CtTypeReferenceImpl]double) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field4").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field5 = [CtInvocationImpl](([CtTypeReferenceImpl]boolean) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field5").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field6 = [CtInvocationImpl](([CtTypeReferenceImpl]long) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field6").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.timestamp = [CtInvocationImpl](([CtTypeReferenceImpl]long) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"timestamp").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.time = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"time").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.date = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"date").apply([CtFieldReadImpl]count)));
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.bar = [CtVariableReadImpl]bar;
                                    [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]foo.field7 = [CtInvocationImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.TestEnum) ([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.fooFunctions.get([CtLiteralImpl]"field7").apply([CtFieldReadImpl]count)));
                                    [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pulsar.common.api.proto.PulsarApi.MessageMetadata messageMetadata = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]PulsarApi.MessageMetadata.newBuilder().setProducerName([CtLiteralImpl]"test-producer").setSequenceId([CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic)).setPublishTime([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis()).build();
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pulsar.client.api.Schema schema = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToSchemas.get([CtVariableReadImpl]schemaName).getType() == [CtFieldReadImpl]org.apache.pulsar.common.schema.SchemaType.AVRO) ? [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.impl.schema.AvroSchema.of([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.class) : [CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.client.impl.schema.JSONSchema.of([CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.Foo.class);
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf payload = [CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.copiedBuffer([CtInvocationImpl][CtVariableReadImpl]schema.encode([CtVariableReadImpl]foo));
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf byteBuf = [CtInvocationImpl]serializeMetadataAndPayload([CtTypeAccessImpl]Commands.ChecksumType.Crc32c, [CtVariableReadImpl]messageMetadata, [CtVariableReadImpl]payload);
                                    [CtOperatorAssignmentImpl][CtFieldWriteImpl]completedBytes += [CtInvocationImpl][CtVariableReadImpl]byteBuf.readableBytes();
                                    [CtInvocationImpl][CtVariableReadImpl]entries.add([CtInvocationImpl][CtTypeAccessImpl]org.apache.bookkeeper.mledger.impl.EntryImpl.create([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic), [CtVariableReadImpl]byteBuf));
                                    [CtInvocationImpl][CtFieldReadImpl]positions.put([CtVariableReadImpl]topic, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic) + [CtLiteralImpl]1);
                                    [CtUnaryOperatorImpl][CtFieldWriteImpl]count++;
                                }
                                [CtInvocationImpl][CtVariableReadImpl]callback.readEntriesComplete([CtVariableReadImpl]entries, [CtVariableReadImpl]ctx);
                            }
                        }).start();
                        [CtReturnImpl]return [CtLiteralImpl]null;
                    }
                }).when([CtVariableReadImpl]readOnlyCursor).asyncReadEntries([CtInvocationImpl]anyInt(), [CtInvocationImpl]any(), [CtInvocationImpl]any());
                [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]readOnlyCursor.hasMoreEntries()).thenAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.Boolean answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]positions.get([CtVariableReadImpl]topic) < [CtVariableReadImpl]entries;
                    }
                });
                [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]readOnlyCursor.findNewestMatching([CtInvocationImpl]any(), [CtInvocationImpl]any())).then([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]Position>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]org.apache.pulsar.sql.presto.Position answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Predicate<[CtTypeReferenceImpl]Entry> predicate = [CtArrayReadImpl](([CtTypeReferenceImpl]com.google.common.base.Predicate<[CtTypeReferenceImpl]Entry>) ([CtVariableReadImpl]args[[CtLiteralImpl]1]));
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String schemaName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pulsar.common.naming.TopicName.get([CtInvocationImpl][CtVariableReadImpl]topic.replaceAll([CtLiteralImpl]"/persistent", [CtLiteralImpl]"")).getPartitionedTopicName()).getSchemaName();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Entry> entries = [CtInvocationImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.getTopicEntries([CtVariableReadImpl]schemaName);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer target = [CtLiteralImpl]null;
                        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entries.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]Entry entry = [CtInvocationImpl][CtVariableReadImpl]entries.get([CtVariableReadImpl]i);
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]predicate.apply([CtVariableReadImpl]entry)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]target = [CtVariableReadImpl]i;
                                [CtBreakImpl]break;
                            }
                        }
                        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]target == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl([CtLiteralImpl]0, [CtVariableReadImpl]target);
                    }
                });
                [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]readOnlyCursor.getNumberOfEntries([CtInvocationImpl]any())).then([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.lang.Long>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.Long answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocationOnMock) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] args = [CtInvocationImpl][CtVariableReadImpl]invocationOnMock.getArguments();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.collect.Range<[CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl> range = [CtArrayReadImpl](([CtTypeReferenceImpl]com.google.common.collect.Range<[CtTypeReferenceImpl]org.apache.bookkeeper.mledger.impl.PositionImpl>) ([CtVariableReadImpl]args[[CtLiteralImpl]0]));
                        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]range.upperEndpoint().getEntryId() + [CtLiteralImpl]1) - [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]range.lowerEndpoint().getEntryId();
                    }
                });
                [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]readOnlyCursor.getCurrentLedgerInfo()).thenReturn([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MLDataFormats.ManagedLedgerInfo.LedgerInfo.newBuilder().setLedgerId([CtLiteralImpl]0).build());
                [CtReturnImpl]return [CtVariableReadImpl]readOnlyCursor;
            }
        });
        [CtAssignmentImpl][CtFieldWriteImpl]PulsarConnectorCache.instance = [CtInvocationImpl]mock([CtFieldReadImpl]org.apache.pulsar.sql.presto.PulsarConnectorCache.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtTypeAccessImpl]PulsarConnectorCache.instance.getManagedLedgerFactory()).thenReturn([CtVariableReadImpl]managedLedgerFactory);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.pulsar.common.naming.TopicName, [CtTypeReferenceImpl]PulsarSplit> split : [CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.splits.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]PulsarRecordCursor pulsarRecordCursor = [CtInvocationImpl]spy([CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarRecordCursor([CtInvocationImpl][CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.topicsToColumnHandles.get([CtInvocationImpl][CtVariableReadImpl]split.getKey()), [CtInvocationImpl][CtVariableReadImpl]split.getValue(), [CtFieldReadImpl]pulsarConnectorConfig, [CtVariableReadImpl]managedLedgerFactory, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ManagedLedgerConfig(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]PulsarConnectorMetricsTracker([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.bookkeeper.stats.NullStatsProvider()), [CtFieldReadImpl]org.apache.pulsar.sql.presto.TestPulsarConnector.dispatchingRowDecoderFactory));
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.pulsarRecordCursors.put([CtInvocationImpl][CtVariableReadImpl]split.getKey(), [CtVariableReadImpl]pulsarRecordCursor);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.AfterMethod
    public [CtTypeReferenceImpl]void cleanup() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]completedBytes = [CtLiteralImpl]0L;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.DataProvider(name = [CtLiteralImpl]"rewriteNamespaceDelimiter")
    public static [CtArrayTypeReferenceImpl]java.lang.Object[][] serviceUrls() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[][]{ [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"|" }, [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]null } };
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void updateRewriteNamespaceDelimiterIfNeeded([CtParameterImpl][CtTypeReferenceImpl]java.lang.String delimiter) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]delimiter)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]pulsarConnectorConfig.setNamespaceDelimiterRewriteEnable([CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]pulsarConnectorConfig.setRewriteNamespaceDelimiter([CtVariableReadImpl]delimiter);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]pulsarConnectorConfig.setNamespaceDelimiterRewriteEnable([CtLiteralImpl]false);
        }
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package io.prestosql.plugin.kafka.schema.confluent;
[CtUnresolvedImport]import org.apache.avro.generic.GenericRecordBuilder;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import io.prestosql.decoder.avro.AvroRowDecoderFactory;
[CtUnresolvedImport]import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
[CtUnresolvedImport]import static org.testng.Assert.assertNull;
[CtUnresolvedImport]import io.prestosql.plugin.kafka.KafkaColumnHandle;
[CtUnresolvedImport]import static org.testng.Assert.assertEquals;
[CtUnresolvedImport]import static io.prestosql.spi.type.IntegerType.INTEGER;
[CtUnresolvedImport]import org.testng.annotations.Test;
[CtUnresolvedImport]import static io.prestosql.decoder.avro.AvroRowDecoderFactory.DATA_SCHEMA;
[CtImportImpl]import java.io.UncheckedIOException;
[CtUnresolvedImport]import io.prestosql.decoder.avro.AvroBytesDeserializer;
[CtUnresolvedImport]import io.prestosql.decoder.RowDecoder;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static io.prestosql.spi.type.VarcharType.VARCHAR;
[CtUnresolvedImport]import static io.prestosql.spi.type.BigintType.BIGINT;
[CtUnresolvedImport]import org.apache.avro.io.EncoderFactory;
[CtUnresolvedImport]import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
[CtUnresolvedImport]import io.prestosql.decoder.DecoderColumnHandle;
[CtUnresolvedImport]import org.apache.avro.generic.GenericDatumWriter;
[CtUnresolvedImport]import org.apache.avro.generic.GenericRecord;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import org.apache.avro.Schema;
[CtUnresolvedImport]import io.prestosql.decoder.FieldValueProvider;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.io.ByteArrayOutputStream;
[CtUnresolvedImport]import static com.google.common.collect.Iterables.getOnlyElement;
[CtImportImpl]import java.nio.ByteBuffer;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkState;
[CtUnresolvedImport]import org.apache.avro.SchemaBuilder;
[CtUnresolvedImport]import org.apache.avro.io.BinaryEncoder;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl]public class TestAvroConfluentRowDecoder {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TOPIC = [CtLiteralImpl]"test";

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testDecodingRows() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient mockSchemaRegistryClient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.Schema initialSchema = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.avro.SchemaBuilder.record([CtFieldReadImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.TOPIC).fields().name([CtLiteralImpl]"col1").type().intType().noDefault().name([CtLiteralImpl]"col2").type().stringType().noDefault().endRecord();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.Schema evolvedSchema = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.avro.SchemaBuilder.record([CtFieldReadImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.TOPIC).fields().name([CtLiteralImpl]"col1").type().intType().noDefault().name([CtLiteralImpl]"col2").type().stringType().noDefault().name([CtLiteralImpl]"col3").type().optional().longType().endRecord();
        [CtInvocationImpl][CtVariableReadImpl]mockSchemaRegistryClient.register([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"%s-value", [CtFieldReadImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.TOPIC), [CtVariableReadImpl]initialSchema);
        [CtInvocationImpl][CtVariableReadImpl]mockSchemaRegistryClient.register([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"%s-value", [CtFieldReadImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.TOPIC), [CtVariableReadImpl]evolvedSchema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle> columnHandles = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle>builder().add([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.plugin.kafka.KafkaColumnHandle([CtLiteralImpl]"col1", [CtFieldReadImpl]INTEGER, [CtLiteralImpl]"col1", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]false)).add([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.plugin.kafka.KafkaColumnHandle([CtLiteralImpl]"col2", [CtFieldReadImpl]VARCHAR, [CtLiteralImpl]"col2", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]false)).add([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.plugin.kafka.KafkaColumnHandle([CtLiteralImpl]"col1", [CtFieldReadImpl]BIGINT, [CtLiteralImpl]"col1", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]false)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.prestosql.decoder.RowDecoder rowDecoder = [CtInvocationImpl]getRowDecoder([CtVariableReadImpl]mockSchemaRegistryClient, [CtVariableReadImpl]columnHandles, [CtVariableReadImpl]evolvedSchema);
        [CtInvocationImpl]testRow([CtVariableReadImpl]rowDecoder, [CtInvocationImpl]generateRecord([CtVariableReadImpl]initialSchema, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]3, [CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"string-%s", [CtLiteralImpl]3))), [CtLiteralImpl]1);
        [CtInvocationImpl]testRow([CtVariableReadImpl]rowDecoder, [CtInvocationImpl]generateRecord([CtVariableReadImpl]evolvedSchema, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]4, [CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"string-%s", [CtLiteralImpl]4), [CtLiteralImpl]4L)), [CtLiteralImpl]2);
        [CtInvocationImpl]testRow([CtVariableReadImpl]rowDecoder, [CtInvocationImpl]generateRecord([CtVariableReadImpl]evolvedSchema, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]5, [CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"string-%s", [CtLiteralImpl]5), [CtLiteralImpl]null)), [CtLiteralImpl]2);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testSingleValueRow() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient mockSchemaRegistryClient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.Schema schema = [CtInvocationImpl][CtTypeAccessImpl]org.apache.avro.Schema.create([CtTypeAccessImpl]Schema.Type.LONG);
        [CtInvocationImpl][CtVariableReadImpl]mockSchemaRegistryClient.register([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"%s-key", [CtFieldReadImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.TOPIC), [CtVariableReadImpl]schema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle> columnHandles = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle>builder().add([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.plugin.kafka.KafkaColumnHandle([CtLiteralImpl]"col1", [CtFieldReadImpl]BIGINT, [CtLiteralImpl]"col1", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtLiteralImpl]false)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.prestosql.decoder.RowDecoder rowDecoder = [CtInvocationImpl]getRowDecoder([CtVariableReadImpl]mockSchemaRegistryClient, [CtVariableReadImpl]columnHandles, [CtVariableReadImpl]schema);
        [CtInvocationImpl]testSingleValueRow([CtVariableReadImpl]rowDecoder, [CtLiteralImpl]3L, [CtVariableReadImpl]schema, [CtLiteralImpl]1);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testRow([CtParameterImpl][CtTypeReferenceImpl]io.prestosql.decoder.RowDecoder rowDecoder, [CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.generic.GenericRecord record, [CtParameterImpl][CtTypeReferenceImpl]int schemaId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] serializedRecord = [CtInvocationImpl]serializeRecord([CtVariableReadImpl]record, [CtInvocationImpl][CtVariableReadImpl]record.getSchema(), [CtVariableReadImpl]schemaId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle, [CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider>> decodedRow = [CtInvocationImpl][CtVariableReadImpl]rowDecoder.decodeRow([CtVariableReadImpl]serializedRecord, [CtLiteralImpl]null);
        [CtInvocationImpl]assertRowsAreEqual([CtVariableReadImpl]decodedRow, [CtVariableReadImpl]record);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testSingleValueRow([CtParameterImpl][CtTypeReferenceImpl]io.prestosql.decoder.RowDecoder rowDecoder, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]int schemaId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] serializedRecord = [CtInvocationImpl]serializeRecord([CtVariableReadImpl]value, [CtVariableReadImpl]schema, [CtVariableReadImpl]schemaId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle, [CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider>> decodedRow = [CtInvocationImpl][CtVariableReadImpl]rowDecoder.decodeRow([CtVariableReadImpl]serializedRecord, [CtLiteralImpl]null);
        [CtInvocationImpl]checkState([CtInvocationImpl][CtVariableReadImpl]decodedRow.isPresent(), [CtLiteralImpl]"decodedRow is not present");
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle, [CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider> entry = [CtInvocationImpl]getOnlyElement([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]decodedRow.get().entrySet());
        [CtInvocationImpl]assertValuesAreEqual([CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtVariableReadImpl]value, [CtInvocationImpl][CtVariableReadImpl]schema.getType());
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]byte[] serializeRecord([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object record, [CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]int schemaId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayOutputStream outputStream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayOutputStream();
            [CtInvocationImpl][CtVariableReadImpl]outputStream.write([CtLiteralImpl]0);
            [CtInvocationImpl][CtVariableReadImpl]outputStream.write([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.allocate([CtLiteralImpl]4).putInt([CtVariableReadImpl]schemaId).array());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.io.BinaryEncoder encoder = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.avro.io.EncoderFactory.get().directBinaryEncoder([CtVariableReadImpl]outputStream, [CtLiteralImpl]null);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.generic.GenericDatumWriter<[CtTypeReferenceImpl]java.lang.Object> avroRecordWriter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.avro.generic.GenericDatumWriter<>([CtVariableReadImpl]schema);
            [CtInvocationImpl][CtVariableReadImpl]avroRecordWriter.write([CtVariableReadImpl]record, [CtVariableReadImpl]encoder);
            [CtInvocationImpl][CtVariableReadImpl]encoder.flush();
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] serializedRecord = [CtInvocationImpl][CtVariableReadImpl]outputStream.toByteArray();
            [CtInvocationImpl][CtVariableReadImpl]outputStream.close();
            [CtReturnImpl]return [CtVariableReadImpl]serializedRecord;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.prestosql.decoder.RowDecoder getRowDecoder([CtParameterImpl][CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.SchemaRegistryClient schemaRegistryClient, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle> columnHandles, [CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.Schema schema) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> decoderParams = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>builder().put([CtTypeAccessImpl]io.prestosql.plugin.kafka.schema.confluent.DATA_SCHEMA, [CtInvocationImpl][CtVariableReadImpl]schema.toString()).build();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]io.prestosql.plugin.kafka.schema.confluent.TestAvroConfluentRowDecoder.getAvroRowDecoderyFactory([CtVariableReadImpl]schemaRegistryClient).create([CtVariableReadImpl]decoderParams, [CtVariableReadImpl]columnHandles);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]io.prestosql.decoder.avro.AvroRowDecoderFactory getAvroRowDecoderyFactory([CtParameterImpl][CtTypeReferenceImpl]io.confluent.kafka.schemaregistry.client.SchemaRegistryClient schemaRegistryClient) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.prestosql.decoder.avro.AvroRowDecoderFactory([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.prestosql.plugin.kafka.schema.confluent.ConfluentAvroReaderSupplier.Factory([CtVariableReadImpl]schemaRegistryClient), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.prestosql.decoder.avro.AvroBytesDeserializer.Factory());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertRowsAreEqual([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle, [CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider>> decodedRow, [CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.generic.GenericRecord expected) [CtBlockImpl]{
        [CtInvocationImpl]checkState([CtInvocationImpl][CtVariableReadImpl]decodedRow.isPresent(), [CtLiteralImpl]"decoded row is not present");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]io.prestosql.decoder.DecoderColumnHandle, [CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider> entry : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]decodedRow.get().entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String columnName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().getName();
            [CtInvocationImpl]assertValuesAreEqual([CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtInvocationImpl][CtVariableReadImpl]expected.get([CtVariableReadImpl]columnName), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]expected.getSchema().getField([CtVariableReadImpl]columnName).schema().getType());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertValuesAreEqual([CtParameterImpl][CtTypeReferenceImpl]io.prestosql.decoder.FieldValueProvider actual, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object expected, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.avro.Schema.Type avroType) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]actual.isNull()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertNull([CtVariableReadImpl]expected);
        } else [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]avroType) {
                [CtCaseImpl]case [CtFieldReadImpl]INT :
                [CtCaseImpl]case [CtFieldReadImpl]LONG :
                    [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]actual.getLong(), [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Number) (expected)).longValue());
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]STRING :
                    [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]actual.getSlice().toStringUtf8(), [CtVariableReadImpl]expected);
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Unexpected type");
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.avro.generic.GenericRecord generateRecord([CtParameterImpl][CtTypeReferenceImpl]org.apache.avro.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> values) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.avro.generic.GenericRecordBuilder recordBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.avro.generic.GenericRecordBuilder([CtVariableReadImpl]schema);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]values.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]recordBuilder.set([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getFields().get([CtVariableReadImpl]i), [CtInvocationImpl][CtVariableReadImpl]values.get([CtVariableReadImpl]i));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]recordBuilder.build();
    }
}
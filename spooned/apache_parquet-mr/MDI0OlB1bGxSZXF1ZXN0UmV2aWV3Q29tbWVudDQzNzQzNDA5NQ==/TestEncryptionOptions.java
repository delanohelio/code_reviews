[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.parquet.hadoop;
[CtUnresolvedImport]import org.apache.parquet.schema.PrimitiveType;
[CtUnresolvedImport]import org.apache.parquet.hadoop.metadata.ColumnPath;
[CtUnresolvedImport]import org.apache.parquet.crypto.ColumnDecryptionProperties;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FIXED_LEN_BYTE_ARRAY;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.parquet.example.data.Group;
[CtUnresolvedImport]import static org.apache.parquet.schema.Type.Repetition.OPTIONAL;
[CtUnresolvedImport]import org.apache.parquet.crypto.ColumnEncryptionProperties;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.INT32;
[CtImportImpl]import java.util.Random;
[CtUnresolvedImport]import static org.apache.parquet.schema.Type.Repetition.REQUIRED;
[CtUnresolvedImport]import org.apache.hadoop.conf.Configuration;
[CtUnresolvedImport]import org.apache.hadoop.fs.Path;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.parquet.example.data.simple.SimpleGroupFactory;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.apache.parquet.hadoop.example.GroupReadSupport;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.parquet.schema.Types;
[CtUnresolvedImport]import org.apache.parquet.crypto.DecryptionKeyRetrieverMock;
[CtUnresolvedImport]import org.apache.parquet.crypto.FileEncryptionProperties;
[CtUnresolvedImport]import static org.apache.parquet.hadoop.ParquetFileWriter.Mode.OVERWRITE;
[CtUnresolvedImport]import org.apache.parquet.crypto.ParquetCipher;
[CtUnresolvedImport]import org.apache.parquet.statistics.RandomValues;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import org.apache.parquet.crypto.FileDecryptionProperties;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.junit.rules.ErrorCollector;
[CtUnresolvedImport]import org.apache.parquet.hadoop.example.ExampleParquetWriter;
[CtUnresolvedImport]import org.apache.parquet.io.api.Binary;
[CtUnresolvedImport]import org.apache.parquet.schema.MessageType;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BOOLEAN;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.DOUBLE;
[CtUnresolvedImport]import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FLOAT;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import org.apache.parquet.crypto.ParquetCryptoRuntimeException;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.junit.rules.TemporaryFolder;
[CtClassImpl][CtCommentImpl]/* This file contains samples for writing and reading encrypted Parquet files in different
encryption and decryption configurations. The samples have the following goals:
1) Demonstrate usage of different options for data encryption and decryption.
2) Produce encrypted files for interoperability tests with other (eg parquet-cpp)
   readers that support encryption.
3) Produce encrypted files with plaintext footer, for testing the ability of legacy
   readers to parse the footer and read unencrypted columns.
4) Perform interoperability tests with other (eg parquet-cpp) writers, by reading
   encrypted files produced by these writers.

The write sample produces number of parquet files, each encrypted with a different
encryption configuration as described below.
The name of each file is in the form of:
tester<encryption config number>.parquet.encrypted.

The read sample creates a set of decryption configurations and then uses each of them
to read all encrypted files in the input directory.

The different encryption and decryption configurations are listed below.


A detailed description of the Parquet Modular Encryption specification can be found
here:
https://github.com/apache/parquet-format/blob/encryption/Encryption.md

The write sample creates files with seven columns in the following
encryption configurations:

 UNIFORM_ENCRYPTION:             Encrypt all columns and the footer with the same key.
                                 (uniform encryption)
 ENCRYPT_COLUMNS_AND_FOOTER:     Encrypt six columns and the footer, with different
                                 keys.
 ENCRYPT_COLUMNS_PLAINTEXT_FOOTER: Encrypt six columns, with different keys.
                                 Do not encrypt footer (to enable legacy readers)
                                 - plaintext footer mode.
 ENCRYPT_COLUMNS_AND_FOOTER_AAD: Encrypt six columns and the footer, with different
                                 keys. Supply aad_prefix for file identity
                                 verification.
 ENCRYPT_COLUMNS_AND_FOOTER_DISABLE_AAD_STORAGE:   Encrypt six columns and the footer,
                                 with different keys. Supply aad_prefix, and call
                                 disable_aad_prefix_storage to prevent file
                                 identity storage in file metadata.
 ENCRYPT_COLUMNS_AND_FOOTER_CTR: Encrypt six columns and the footer, with different
                                 keys. Use the alternative (AES_GCM_CTR_V1) algorithm.
 NO_ENCRYPTION:                  Do not encrypt anything


The read sample uses each of the following decryption configurations to read every
encrypted files in the input directory:

 DECRYPT_WITH_KEY_RETRIEVER:     Decrypt using key retriever that holds the keys of
                                 the encrypted columns and the footer key.
 DECRYPT_WITH_KEY_RETRIEVER_AAD: Decrypt using key retriever that holds the keys of
                                 the encrypted columns and the footer key. Supplies
                                 aad_prefix to verify file identity.
 DECRYPT_WITH_EXPLICIT_KEYS:     Decrypt using explicit column and footer keys
                                 (instead of key retrieval callback).
 NO_DECRYPTION:                  Do not decrypt anything.
 */
public class TestEncryptionOptions {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.class);

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder temporaryFolder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.junit.rules.ErrorCollector errorCollector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.rules.ErrorCollector();

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.String PARQUET_TESTING_PATH = [CtLiteralImpl]"../submodules/parquet-testing/data";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int RANDOM_SEED = [CtLiteralImpl]42;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int FIXED_LENGTH = [CtLiteralImpl]10;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Random RANDOM = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Random([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]RandomValues.IntGenerator intGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.parquet.statistics.RandomValues.IntGenerator([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]RandomValues.FloatGenerator floatGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.parquet.statistics.RandomValues.FloatGenerator([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]RandomValues.DoubleGenerator doubleGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.parquet.statistics.RandomValues.DoubleGenerator([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]RandomValues.BinaryGenerator binaryGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.parquet.statistics.RandomValues.BinaryGenerator([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]RandomValues.FixedGenerator fixedBinaryGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.parquet.statistics.RandomValues.FixedGenerator([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM_SEED, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH);

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] FOOTER_ENCRYPTION_KEY = [CtInvocationImpl][CtLiteralImpl]"0123456789012345".getBytes();

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[][] COLUMN_ENCRYPTION_KEYS = [CtNewArrayImpl]new byte[][]{ [CtInvocationImpl][CtLiteralImpl]"1234567890123450".getBytes(), [CtInvocationImpl][CtLiteralImpl]"1234567890123451".getBytes(), [CtInvocationImpl][CtLiteralImpl]"1234567890123452".getBytes(), [CtInvocationImpl][CtLiteralImpl]"1234567890123453".getBytes(), [CtInvocationImpl][CtLiteralImpl]"1234567890123454".getBytes(), [CtInvocationImpl][CtLiteralImpl]"1234567890123455".getBytes() };

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.String[] COLUMN_ENCRYPTION_KEY_IDS = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"kc1", [CtLiteralImpl]"kc2", [CtLiteralImpl]"kc3", [CtLiteralImpl]"kc4", [CtLiteralImpl]"kc5", [CtLiteralImpl]"kc6" };

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FOOTER_ENCRYPTION_KEY_ID = [CtLiteralImpl]"kf";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AAD_PREFIX_STRING = [CtLiteralImpl]"tester";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BOOLEAN_FIELD_NAME = [CtLiteralImpl]"boolean_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INT32_FIELD_NAME = [CtLiteralImpl]"int32_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FLOAT_FIELD_NAME = [CtLiteralImpl]"float_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DOUBLE_FIELD_NAME = [CtLiteralImpl]"double_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BINARY_FIELD_NAME = [CtLiteralImpl]"ba_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FIXED_LENGTH_BINARY_FIELD_NAME = [CtLiteralImpl]"flba_field";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PLAINTEXT_INT32_FIELD_NAME = [CtLiteralImpl]"plain_int32_field";

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] footerKeyMetadata = [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY_ID.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] AADPrefix = [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AAD_PREFIX_STRING.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int ROW_COUNT = [CtLiteralImpl]10000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> DATA = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.generateRandomData([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.ROW_COUNT));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> LINEAR_DATA = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.generateLinearData([CtLiteralImpl]250));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.parquet.schema.MessageType SCHEMA = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.MessageType([CtLiteralImpl]"schema", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]REQUIRED, [CtFieldReadImpl]BOOLEAN, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]REQUIRED, [CtFieldReadImpl]INT32, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]REQUIRED, [CtFieldReadImpl]FLOAT, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]REQUIRED, [CtFieldReadImpl]DOUBLE, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]OPTIONAL, [CtFieldReadImpl]BINARY, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BINARY_FIELD_NAME), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.schema.Types.required([CtTypeAccessImpl]org.apache.parquet.hadoop.FIXED_LEN_BYTE_ARRAY).length([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH).named([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH_BINARY_FIELD_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.schema.PrimitiveType([CtFieldReadImpl]OPTIONAL, [CtFieldReadImpl]INT32, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.PLAINTEXT_INT32_FIELD_NAME));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.parquet.crypto.DecryptionKeyRetrieverMock decryptionKeyRetrieverMock = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.crypto.DecryptionKeyRetrieverMock().putKey([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY_ID, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]0], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]0]).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]1], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]1]).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]2], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]2]).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]3], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]3]).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]4], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]4]).putKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]5], [CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]5]);

    [CtEnumImpl]public enum EncryptionConfiguration {

        [CtEnumValueImpl]UNIFORM_ENCRYPTION()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getUniformEncryptionEncryptionProperties();
            }
        },
        [CtEnumValueImpl]ENCRYPT_COLUMNS_AND_FOOTER()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getEncryptColumnsAndFooterEncryptionProperties();
            }
        },
        [CtEnumValueImpl]ENCRYPT_COLUMNS_PLAINTEXT_FOOTER()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getPlaintextFooterEncryptionProperties();
            }
        },
        [CtEnumValueImpl]ENCRYPT_COLUMNS_AND_FOOTER_AAD()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getEncryptWithAADEncryptionProperties();
            }
        },
        [CtEnumValueImpl]ENCRYPT_COLUMNS_AND_FOOTER_DISABLE_AAD_STORAGE()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getDisableAADStorageEncryptionProperties();
            }
        },
        [CtEnumValueImpl]ENCRYPT_COLUMNS_AND_FOOTER_CTR()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getCTREncryptionProperties();
            }
        },
        [CtEnumValueImpl]NO_ENCRYPTION()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        };
        [CtMethodImpl]public abstract [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptionProperties();
    }

    [CtEnumImpl]public enum DecryptionConfiguration {

        [CtEnumValueImpl]DECRYPT_WITH_KEY_RETRIEVER()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getDecryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getKeyRetrieverDecryptionProperties();
            }
        },
        [CtEnumValueImpl]DECRYPT_WITH_KEY_RETRIEVER_AAD()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getDecryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getKeyRetrieverAADDecryptionProperties();
            }
        },
        [CtEnumValueImpl]DECRYPT_WITH_EXPLICIT_KEYS()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getDecryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getExplicitKeysDecryptionProperties();
            }
        },
        [CtEnumValueImpl]NO_DECRYPTION()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getDecryptionProperties() [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        };
        [CtMethodImpl]public abstract [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getDecryptionProperties();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testWriteReadEncryptedParquetFiles() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path rootPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]temporaryFolder.getRoot().getPath());
        [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"======== testWriteReadEncryptedParquetFiles {} ========", [CtInvocationImpl][CtVariableReadImpl]rootPath.toString());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] AADPrefix = [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AAD_PREFIX_STRING.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        [CtInvocationImpl][CtCommentImpl]// Write using various encryption configuraions
        testWriteEncryptedParquetFiles([CtVariableReadImpl]rootPath, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DATA);
        [CtInvocationImpl][CtCommentImpl]// Read using various decryption configurations.
        testReadEncryptedParquetFiles([CtVariableReadImpl]rootPath, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DATA);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testInteropReadEncryptedParquetFiles() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path rootPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.PARQUET_TESTING_PATH);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"======== testInteropReadEncryptedParquetFiles {} ========", [CtInvocationImpl][CtVariableReadImpl]rootPath.toString());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] AADPrefix = [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AAD_PREFIX_STRING.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        [CtInvocationImpl][CtCommentImpl]// Read using various decryption configurations.
        [CtCommentImpl]/* readOnlyEncrypted */
        testInteropReadEncryptedParquetFiles([CtVariableReadImpl]rootPath, [CtLiteralImpl]true, [CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LINEAR_DATA);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> generateRandomData([CtParameterImpl][CtTypeReferenceImpl]int rowCount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> dataList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]rowCount);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int row = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]row < [CtVariableReadImpl]rowCount; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]row) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow newRow = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow([CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.RANDOM.nextBoolean(), [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.intGenerator.nextValue(), [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.floatGenerator.nextValue(), [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.doubleGenerator.nextValue(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.binaryGenerator.nextValue().getBytes(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.fixedBinaryGenerator.nextValue().getBytes(), [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.intGenerator.nextValue());
            [CtInvocationImpl][CtVariableReadImpl]dataList.add([CtVariableReadImpl]newRow);
        }
        [CtReturnImpl]return [CtVariableReadImpl]dataList;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> generateLinearData([CtParameterImpl][CtTypeReferenceImpl]int rowCount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> dataList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]rowCount);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String baseStr = [CtLiteralImpl]"parquet";
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int row = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]row < [CtVariableReadImpl]rowCount; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]row) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean boolean_val = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]row % [CtLiteralImpl]2) == [CtLiteralImpl]0) ? [CtLiteralImpl]true : [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]float float_val = [CtBinaryOperatorImpl][CtVariableReadImpl](([CtTypeReferenceImpl]float) (row)) * [CtLiteralImpl]1.1F;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double double_val = [CtBinaryOperatorImpl][CtVariableReadImpl]row * [CtLiteralImpl]1.1111111;
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] binary_val = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]row % [CtLiteralImpl]2) == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]char firstChar = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]char) ([CtLiteralImpl](([CtTypeReferenceImpl]int) ('0')) + [CtBinaryOperatorImpl]([CtVariableReadImpl]row / [CtLiteralImpl]100)));
                [CtLocalVariableImpl][CtTypeReferenceImpl]char secondChar = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]char) ([CtLiteralImpl](([CtTypeReferenceImpl]int) ('0')) + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]row / [CtLiteralImpl]10) % [CtLiteralImpl]10)));
                [CtLocalVariableImpl][CtTypeReferenceImpl]char thirdChar = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]char) ([CtLiteralImpl](([CtTypeReferenceImpl]int) ('0')) + [CtBinaryOperatorImpl]([CtVariableReadImpl]row % [CtLiteralImpl]10)));
                [CtAssignmentImpl][CtVariableWriteImpl]binary_val = [CtInvocationImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]baseStr + [CtVariableReadImpl]firstChar) + [CtVariableReadImpl]secondChar) + [CtVariableReadImpl]thirdChar).getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
            }
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]char[] fixed = [CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH];
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]char[] aChar = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.toChars([CtVariableReadImpl]row);
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtVariableReadImpl]fixed, [CtArrayReadImpl][CtVariableReadImpl]aChar[[CtLiteralImpl]0]);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow newRow = [CtConstructorCallImpl][CtCommentImpl]/* plaintext_int32_field */
            new [CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow([CtVariableReadImpl]boolean_val, [CtVariableReadImpl]row, [CtVariableReadImpl]float_val, [CtVariableReadImpl]double_val, [CtVariableReadImpl]binary_val, [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]fixed).getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8), [CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]dataList.add([CtVariableReadImpl]newRow);
        }
        [CtReturnImpl]return [CtVariableReadImpl]dataList;
    }

    [CtClassImpl]public static class SingleRow {
        [CtFieldImpl]public final [CtTypeReferenceImpl]boolean boolean_field;

        [CtFieldImpl]public final [CtTypeReferenceImpl]int int32_field;

        [CtFieldImpl]public final [CtTypeReferenceImpl]float float_field;

        [CtFieldImpl]public final [CtTypeReferenceImpl]double double_field;

        [CtFieldImpl]public final [CtArrayTypeReferenceImpl]byte[] ba_field;

        [CtFieldImpl]public final [CtArrayTypeReferenceImpl]byte[] flba_field;

        [CtFieldImpl]public final [CtTypeReferenceImpl]java.lang.Integer plaintext_int32_field;[CtCommentImpl]// Can be null, since it doesn't exist in C++-created files yet.


        [CtConstructorImpl]public SingleRow([CtParameterImpl][CtTypeReferenceImpl]boolean boolean_field, [CtParameterImpl][CtTypeReferenceImpl]int int32_field, [CtParameterImpl][CtTypeReferenceImpl]float float_field, [CtParameterImpl][CtTypeReferenceImpl]double double_field, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] ba_field, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] flba_field, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer plaintext_int32_field) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.boolean_field = [CtVariableReadImpl]boolean_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.int32_field = [CtVariableReadImpl]int32_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.float_field = [CtVariableReadImpl]float_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.double_field = [CtVariableReadImpl]double_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ba_field = [CtVariableReadImpl]ba_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.flba_field = [CtVariableReadImpl]flba_field;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.plaintext_int32_field = [CtVariableReadImpl]plaintext_int32_field;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testWriteEncryptedParquetFiles([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path root, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> data) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration conf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int pageSize = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]data.size() / [CtLiteralImpl]10;[CtCommentImpl]// Ensure that several pages will be created

        [CtLocalVariableImpl][CtTypeReferenceImpl]int rowGroupSize = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]pageSize * [CtLiteralImpl]6) * [CtLiteralImpl]5;[CtCommentImpl]// Ensure that there are more row-groups created

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.example.data.simple.SimpleGroupFactory f = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.example.data.simple.SimpleGroupFactory([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SCHEMA);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration[] encryptionConfigurations = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration : [CtVariableReadImpl]encryptionConfigurations) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path file = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]root, [CtInvocationImpl]getFileName([CtVariableReadImpl]encryptionConfiguration));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties encryptionProperties = [CtInvocationImpl][CtVariableReadImpl]encryptionConfiguration.getEncryptionProperties();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"\nWrite " + [CtInvocationImpl][CtVariableReadImpl]file.toString());
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.ParquetWriter<[CtTypeReferenceImpl]org.apache.parquet.example.data.Group> writer = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.example.ExampleParquetWriter.builder([CtVariableReadImpl]file).withWriteMode([CtTypeAccessImpl]Mode.OVERWRITE).withRowGroupSize([CtVariableReadImpl]rowGroupSize).withPageSize([CtVariableReadImpl]pageSize).withType([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SCHEMA).withConf([CtVariableReadImpl]conf).withEncryption([CtVariableReadImpl]encryptionProperties).build()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow singleRow : [CtVariableReadImpl]data) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]writer.write([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.newGroup().append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME, [CtFieldReadImpl][CtVariableReadImpl]singleRow.boolean_field).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME, [CtFieldReadImpl][CtVariableReadImpl]singleRow.int32_field).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME, [CtFieldReadImpl][CtVariableReadImpl]singleRow.float_field).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME, [CtFieldReadImpl][CtVariableReadImpl]singleRow.double_field).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BINARY_FIELD_NAME, [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.io.api.Binary.fromConstantByteArray([CtFieldReadImpl][CtVariableReadImpl]singleRow.ba_field)).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH_BINARY_FIELD_NAME, [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.io.api.Binary.fromConstantByteArray([CtFieldReadImpl][CtVariableReadImpl]singleRow.flba_field)).append([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.PLAINTEXT_INT32_FIELD_NAME, [CtFieldReadImpl][CtVariableReadImpl]singleRow.plaintext_int32_field));
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getFileName([CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]encryptionConfiguration.toString().toLowerCase() + [CtLiteralImpl]".parquet.encrypted";
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testReadEncryptedParquetFiles([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path root, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> data) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration conf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration[] decryptionConfigurations = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration decryptionConfiguration : [CtVariableReadImpl]decryptionConfigurations) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration[] encryptionConfigurations = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.values();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration : [CtVariableReadImpl]encryptionConfigurations) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path file = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]root, [CtInvocationImpl]getFileName([CtVariableReadImpl]encryptionConfiguration));
                [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"==> Decryption configuration {}", [CtVariableReadImpl]decryptionConfiguration);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties fileDecryptionProperties = [CtInvocationImpl][CtVariableReadImpl]decryptionConfiguration.getDecryptionProperties();
                [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"--> Read file {} {}", [CtInvocationImpl][CtVariableReadImpl]file.toString(), [CtVariableReadImpl]encryptionConfiguration);
                [CtIfImpl][CtCommentImpl]// Read only the non-encrypted columns
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]NO_DECRYPTION) && [CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_PLAINTEXT_FOOTER)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]conf.set([CtLiteralImpl]"parquet.read.schema", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.schema.Types.buildMessage().optional([CtTypeAccessImpl]org.apache.parquet.hadoop.INT32).named([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.PLAINTEXT_INT32_FIELD_NAME).named([CtLiteralImpl]"FormatTestObject").toString());
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]int rowNum = [CtLiteralImpl]0;
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.ParquetReader<[CtTypeReferenceImpl]org.apache.parquet.example.data.Group> reader = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.ParquetReader.builder([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.hadoop.example.GroupReadSupport(), [CtVariableReadImpl]file).withConf([CtVariableReadImpl]conf).withDecryption([CtVariableReadImpl]fileDecryptionProperties).build()) [CtBlockImpl]{
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.example.data.Group group = [CtInvocationImpl][CtVariableReadImpl]reader.read(); [CtBinaryOperatorImpl][CtVariableReadImpl]group != [CtLiteralImpl]null; [CtAssignmentImpl][CtVariableWriteImpl]group = [CtInvocationImpl][CtVariableReadImpl]reader.read()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow rowExpected = [CtInvocationImpl][CtVariableReadImpl]data.get([CtUnaryOperatorImpl][CtVariableWriteImpl]rowNum++);
                        [CtIfImpl][CtCommentImpl]// plaintext columns
                        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]rowExpected.plaintext_int32_field) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]rowExpected.plaintext_int32_field != [CtInvocationImpl][CtVariableReadImpl]group.getInteger([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.PLAINTEXT_INT32_FIELD_NAME, [CtLiteralImpl]0))) [CtBlockImpl]{
                            [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong int", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                        }
                        [CtIfImpl][CtCommentImpl]// encrypted columns
                        if ([CtBinaryOperatorImpl][CtVariableReadImpl]decryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]NO_DECRYPTION) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.boolean_field != [CtInvocationImpl][CtVariableReadImpl]group.getBoolean([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong bool", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.int32_field != [CtInvocationImpl][CtVariableReadImpl]group.getInteger([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong int", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.float_field != [CtInvocationImpl][CtVariableReadImpl]group.getFloat([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong float", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.double_field != [CtInvocationImpl][CtVariableReadImpl]group.getDouble([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong double", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]rowExpected.ba_field) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.equals([CtFieldReadImpl][CtVariableReadImpl]rowExpected.ba_field, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]group.getBinary([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BINARY_FIELD_NAME, [CtLiteralImpl]0).getBytes()))) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong byte array", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.equals([CtFieldReadImpl][CtVariableReadImpl]rowExpected.flba_field, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]group.getBinary([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH_BINARY_FIELD_NAME, [CtLiteralImpl]0).getBytes())) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong fixed-length byte array", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                        }
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ParquetCryptoRuntimeException e) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtInvocationImpl][CtVariableReadImpl]e.getMessage();
                    [CtInvocationImpl]checkResult([CtInvocationImpl][CtVariableReadImpl]file.getName(), [CtVariableReadImpl]decryptionConfiguration, [CtConditionalImpl][CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]errorMessage ? [CtInvocationImpl][CtVariableReadImpl]e.toString() : [CtVariableReadImpl]errorMessage);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unexpected exception: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getClass().getName()) + [CtLiteralImpl]" with message: ") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                }
                [CtInvocationImpl][CtVariableReadImpl]conf.unset([CtLiteralImpl]"parquet.read.schema");
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testInteropReadEncryptedParquetFiles([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path root, [CtParameterImpl][CtTypeReferenceImpl]boolean readOnlyEncrypted, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow> data) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration conf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration[] decryptionConfigurations = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration decryptionConfiguration : [CtVariableReadImpl]decryptionConfigurations) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration[] encryptionConfigurations = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.values();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration : [CtVariableReadImpl]encryptionConfigurations) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]readOnlyEncrypted && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]NO_ENCRYPTION == [CtVariableReadImpl]encryptionConfiguration)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.fs.Path file = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.Path([CtVariableReadImpl]root, [CtInvocationImpl]getFileName([CtVariableReadImpl]encryptionConfiguration));
                [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"==> Decryption configuration {}", [CtVariableReadImpl]decryptionConfiguration);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties fileDecryptionProperties = [CtInvocationImpl][CtVariableReadImpl]decryptionConfiguration.getDecryptionProperties();
                [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtLiteralImpl]"--> Read file {} {}", [CtInvocationImpl][CtVariableReadImpl]file.toString(), [CtVariableReadImpl]encryptionConfiguration);
                [CtIfImpl][CtCommentImpl]// Read only the non-encrypted columns
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]NO_DECRYPTION) && [CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_PLAINTEXT_FOOTER)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]conf.set([CtLiteralImpl]"parquet.read.schema", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.schema.Types.buildMessage().required([CtTypeAccessImpl]org.apache.parquet.hadoop.BOOLEAN).named([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME).required([CtTypeAccessImpl]org.apache.parquet.hadoop.INT32).named([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME).named([CtLiteralImpl]"FormatTestObject").toString());
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]int rowNum = [CtLiteralImpl]0;
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.ParquetReader<[CtTypeReferenceImpl]org.apache.parquet.example.data.Group> reader = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.ParquetReader.builder([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.parquet.hadoop.example.GroupReadSupport(), [CtVariableReadImpl]file).withConf([CtVariableReadImpl]conf).withDecryption([CtVariableReadImpl]fileDecryptionProperties).build()) [CtBlockImpl]{
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.example.data.Group group = [CtInvocationImpl][CtVariableReadImpl]reader.read(); [CtBinaryOperatorImpl][CtVariableReadImpl]group != [CtLiteralImpl]null; [CtAssignmentImpl][CtVariableWriteImpl]group = [CtInvocationImpl][CtVariableReadImpl]reader.read()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.SingleRow rowExpected = [CtInvocationImpl][CtVariableReadImpl]data.get([CtUnaryOperatorImpl][CtVariableWriteImpl]rowNum++);
                        [CtIfImpl][CtCommentImpl]// plaintext columns
                        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.boolean_field != [CtInvocationImpl][CtVariableReadImpl]group.getBoolean([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                            [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong bool", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.int32_field != [CtInvocationImpl][CtVariableReadImpl]group.getInteger([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                            [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong int", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                        }
                        [CtIfImpl][CtCommentImpl]// encrypted columns
                        if ([CtBinaryOperatorImpl][CtVariableReadImpl]decryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]NO_DECRYPTION) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.float_field != [CtInvocationImpl][CtVariableReadImpl]group.getFloat([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong float", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]rowExpected.double_field != [CtInvocationImpl][CtVariableReadImpl]group.getDouble([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME, [CtLiteralImpl]0)) [CtBlockImpl]{
                                [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Wrong double", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                            }
                        }
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ParquetCryptoRuntimeException e) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtInvocationImpl][CtVariableReadImpl]e.getMessage();
                    [CtInvocationImpl]checkResult([CtInvocationImpl][CtVariableReadImpl]file.getName(), [CtVariableReadImpl]decryptionConfiguration, [CtConditionalImpl][CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]errorMessage ? [CtInvocationImpl][CtVariableReadImpl]e.toString() : [CtVariableReadImpl]errorMessage);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unexpected exception: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getClass().getName()) + [CtLiteralImpl]" with message: ") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                }
                [CtInvocationImpl][CtVariableReadImpl]conf.unset([CtLiteralImpl]"parquet.read.schema");
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check that the decryption result is as expected.
     */
    private [CtTypeReferenceImpl]void checkResult([CtParameterImpl][CtTypeReferenceImpl]java.lang.String file, [CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration decryptionConfiguration, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String exceptionMsg) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Extract encryptionConfigurationNumber from the parquet file name.
        [CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration = [CtInvocationImpl]getEncryptionConfigurationFromFilename([CtVariableReadImpl]file);
        [CtIfImpl][CtCommentImpl]// Encryption_configuration 5 contains aad_prefix and
        [CtCommentImpl]// disable_aad_prefix_storage.
        [CtCommentImpl]// An exception is expected to be thrown if the file is not decrypted with aad_prefix.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]encryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_AND_FOOTER_DISABLE_AAD_STORAGE) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_KEY_RETRIEVER) || [CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_EXPLICIT_KEYS)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.contains([CtLiteralImpl]"AAD")) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Expecting AAD related exception", [CtVariableReadImpl]exceptionMsg, [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Exception as expected: " + [CtVariableReadImpl]exceptionMsg);
                }
                [CtReturnImpl]return;
            }
        }
        [CtIfImpl][CtCommentImpl]// Decryption configuration 2 contains aad_prefix. An exception is expected to
        [CtCommentImpl]// be thrown if the file was not encrypted with the same aad_prefix.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_KEY_RETRIEVER_AAD) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_AND_FOOTER_DISABLE_AAD_STORAGE) && [CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_AND_FOOTER_AAD)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]NO_ENCRYPTION)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.contains([CtLiteralImpl]"AAD")) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Expecting AAD related exception", [CtVariableReadImpl]exceptionMsg, [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Exception as expected: " + [CtVariableReadImpl]exceptionMsg);
                }
                [CtReturnImpl]return;
            }
        }
        [CtIfImpl][CtCommentImpl]// Encryption_configuration 7 has null encryptor, so parquet is plaintext.
        [CtCommentImpl]// An exception is expected to be thrown if the file is being decrypted.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]encryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]NO_ENCRYPTION) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_KEY_RETRIEVER) || [CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_KEY_RETRIEVER_AAD)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]DECRYPT_WITH_EXPLICIT_KEYS)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.endsWith([CtLiteralImpl]"Applying decryptor on plaintext file")) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Expecting exception Applying decryptor on plaintext file", [CtVariableReadImpl]exceptionMsg, [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Exception as expected: " + [CtVariableReadImpl]exceptionMsg);
                }
                [CtReturnImpl]return;
            }
        }
        [CtIfImpl][CtCommentImpl]// Decryption configuration 4 is null, so only plaintext file can be read. An exception is expected to
        [CtCommentImpl]// be thrown if the file is encrypted.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]decryptionConfiguration == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration.[CtFieldReferenceImpl]NO_DECRYPTION) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]NO_ENCRYPTION) && [CtBinaryOperatorImpl]([CtVariableReadImpl]encryptionConfiguration != [CtFieldReadImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.[CtFieldReferenceImpl]ENCRYPT_COLUMNS_PLAINTEXT_FOOTER)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.endsWith([CtLiteralImpl]"No keys available")) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.endsWith([CtLiteralImpl]"Null File Decryptor"))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.endsWith([CtLiteralImpl]"Footer key unavailable"))) [CtBlockImpl]{
                    [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Expecting No keys available exception", [CtVariableReadImpl]exceptionMsg, [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Exception as expected: " + [CtVariableReadImpl]exceptionMsg);
                }
                [CtReturnImpl]return;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]exceptionMsg) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]exceptionMsg.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl]addErrorToErrorCollectorAndLog([CtLiteralImpl]"Didn't expect an exception", [CtVariableReadImpl]exceptionMsg, [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration getEncryptionConfigurationFromFilename([CtParameterImpl][CtTypeReferenceImpl]java.lang.String file) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]file.endsWith([CtLiteralImpl]".parquet.encrypted")) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fileNamePrefix = [CtInvocationImpl][CtVariableReadImpl]file.replaceFirst([CtLiteralImpl]".parquet.encrypted", [CtLiteralImpl]"");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration = [CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration.valueOf([CtInvocationImpl][CtVariableReadImpl]fileNamePrefix.toUpperCase());
            [CtReturnImpl]return [CtVariableReadImpl]encryptionConfiguration;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.error([CtBinaryOperatorImpl][CtLiteralImpl]"File name doesn't match any known encryption configuration: " + [CtVariableReadImpl]file);
            [CtInvocationImpl][CtFieldReadImpl]errorCollector.addError([CtVariableReadImpl]e);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addErrorToErrorCollectorAndLog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorMessage, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String exceptionMessage, [CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration, [CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration decryptionConfiguration) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullErrorMessage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s - %s Error: %s, but got [%s]", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration, [CtVariableReadImpl]errorMessage, [CtVariableReadImpl]exceptionMessage);
        [CtInvocationImpl][CtFieldReadImpl]errorCollector.addError([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Throwable([CtVariableReadImpl]fullErrorMessage));
        [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.error([CtVariableReadImpl]fullErrorMessage);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addErrorToErrorCollectorAndLog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorMessage, [CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.EncryptionConfiguration encryptionConfiguration, [CtParameterImpl][CtTypeReferenceImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DecryptionConfiguration decryptionConfiguration) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullErrorMessage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s - %s Error: %s", [CtVariableReadImpl]encryptionConfiguration, [CtVariableReadImpl]decryptionConfiguration, [CtVariableReadImpl]errorMessage);
        [CtInvocationImpl][CtFieldReadImpl]errorCollector.addError([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Throwable([CtVariableReadImpl]fullErrorMessage));
        [CtInvocationImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.LOG.error([CtVariableReadImpl]fullErrorMessage);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> getColumnEncryptionPropertiesMap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesDouble = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]0]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]0]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesDouble.getPath(), [CtVariableReadImpl]columnPropertiesDouble);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesFloat = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]1]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]1]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesFloat.getPath(), [CtVariableReadImpl]columnPropertiesFloat);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesBool = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]2]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]2]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesBool.getPath(), [CtVariableReadImpl]columnPropertiesBool);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesInt32 = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]3]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]3]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesInt32.getPath(), [CtVariableReadImpl]columnPropertiesInt32);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesBinary = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BINARY_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]4]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]4]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesBinary.getPath(), [CtVariableReadImpl]columnPropertiesBinary);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties columnPropertiesFixed = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH_BINARY_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]5]).withKeyID([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEY_IDS[[CtLiteralImpl]5]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnPropertiesMap.put([CtInvocationImpl][CtVariableReadImpl]columnPropertiesFixed.getPath(), [CtVariableReadImpl]columnPropertiesFixed);
        [CtReturnImpl]return [CtVariableReadImpl]columnPropertiesMap;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties> getColumnDecryptionPropertiesMap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties> columnMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsDouble = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.DOUBLE_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]0]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsDouble.getPath(), [CtVariableReadImpl]columnDecryptionPropsDouble);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsFloat = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FLOAT_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]1]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsFloat.getPath(), [CtVariableReadImpl]columnDecryptionPropsFloat);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsBool = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BOOLEAN_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]2]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsBool.getPath(), [CtVariableReadImpl]columnDecryptionPropsBool);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsInt32 = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.INT32_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]3]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsInt32.getPath(), [CtVariableReadImpl]columnDecryptionPropsInt32);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsBinary = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.BINARY_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]4]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsBinary.getPath(), [CtVariableReadImpl]columnDecryptionPropsBinary);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties columnDecryptionPropsFixed = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.ColumnDecryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FIXED_LENGTH_BINARY_FIELD_NAME).withKey([CtArrayReadImpl][CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.COLUMN_ENCRYPTION_KEYS[[CtLiteralImpl]5]).build();
        [CtInvocationImpl][CtVariableReadImpl]columnMap.put([CtInvocationImpl][CtVariableReadImpl]columnDecryptionPropsFixed.getPath(), [CtVariableReadImpl]columnDecryptionPropsFixed);
        [CtReturnImpl]return [CtVariableReadImpl]columnMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 1: Encrypt all columns and the footer with the same key.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getUniformEncryptionEncryptionProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 2: Encrypt six columns and the footer, with different keys.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptColumnsAndFooterEncryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnEncryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).withEncryptedColumns([CtVariableReadImpl]columnPropertiesMap).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 3: Encrypt six columns, with different keys.
     * Don't encrypt footer.
     * (plaintext footer mode, readable by legacy readers)
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getPlaintextFooterEncryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnEncryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).withEncryptedColumns([CtVariableReadImpl]columnPropertiesMap).withPlaintextFooter().build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 4: Encrypt six columns and the footer, with different keys.
     * Use aad_prefix.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getEncryptWithAADEncryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnEncryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).withEncryptedColumns([CtVariableReadImpl]columnPropertiesMap).withAADPrefix([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AADPrefix).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 5: Encrypt six columns and the footer, with different keys.
     * Use aad_prefix and disable_aad_prefix_storage.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getDisableAADStorageEncryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnEncryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).withEncryptedColumns([CtVariableReadImpl]columnPropertiesMap).withAADPrefix([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AADPrefix).withoutAADPrefixStorage().build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encryption configuration 6: Encrypt six columns and the footer, with different keys.
     *  Use AES_GCM_CTR_V1 algorithm.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileEncryptionProperties getCTREncryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnEncryptionProperties> columnPropertiesMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnEncryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileEncryptionProperties.builder([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).withFooterKeyMetadata([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.footerKeyMetadata).withEncryptedColumns([CtVariableReadImpl]columnPropertiesMap).withAlgorithm([CtTypeAccessImpl]ParquetCipher.AES_GCM_CTR_V1).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Decryption configuration 1: Decrypt using key retriever callback that holds the keys
     * of the encrypted columns and the footer key.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getKeyRetrieverDecryptionProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileDecryptionProperties.builder().withKeyRetriever([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.decryptionKeyRetrieverMock).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Decryption configuration 2: Decrypt using key retriever callback that holds the keys
     * of the encrypted columns and the footer key. Supply aad_prefix.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getKeyRetrieverAADDecryptionProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileDecryptionProperties.builder().withKeyRetriever([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.decryptionKeyRetrieverMock).withAADPrefix([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.AADPrefix).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Decryption configuration 3: Decrypt using explicit column and footer keys.
     */
    private static [CtTypeReferenceImpl]org.apache.parquet.crypto.FileDecryptionProperties getExplicitKeysDecryptionProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.parquet.hadoop.metadata.ColumnPath, [CtTypeReferenceImpl]org.apache.parquet.crypto.ColumnDecryptionProperties> columnMap = [CtInvocationImpl]org.apache.parquet.hadoop.TestEncryptionOptions.getColumnDecryptionPropertiesMap();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.parquet.crypto.FileDecryptionProperties.builder().withColumnKeys([CtVariableReadImpl]columnMap).withFooterKey([CtFieldReadImpl]org.apache.parquet.hadoop.TestEncryptionOptions.FOOTER_ENCRYPTION_KEY).build();
    }
}
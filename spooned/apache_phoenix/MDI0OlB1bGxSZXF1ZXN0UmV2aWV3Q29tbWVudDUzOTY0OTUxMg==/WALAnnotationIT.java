[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.phoenix.end2end;
[CtUnresolvedImport]import org.apache.phoenix.query.QueryServices;
[CtUnresolvedImport]import org.apache.phoenix.util.MetaDataUtil;
[CtUnresolvedImport]import org.apache.phoenix.util.PhoenixRuntime;
[CtUnresolvedImport]import org.junit.BeforeClass;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.phoenix.util.SchemaUtil;
[CtImportImpl]import java.sql.SQLException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import org.apache.hadoop.hbase.coprocessor.WALCoprocessorEnvironment;
[CtUnresolvedImport]import org.apache.hadoop.hbase.HConstants;
[CtUnresolvedImport]import org.apache.phoenix.compat.hbase.coprocessor.CompatIndexRegionObserver;
[CtUnresolvedImport]import org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder;
[CtUnresolvedImport]import org.apache.hadoop.hbase.regionserver.wal.WALCoprocessorHost;
[CtUnresolvedImport]import org.apache.hadoop.hbase.wal.WAL;
[CtUnresolvedImport]import org.junit.Assume;
[CtUnresolvedImport]import org.apache.phoenix.query.PhoenixTestBuilder;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.phoenix.util.ReadOnlyProps;
[CtUnresolvedImport]import org.junit.runners.Parameterized;
[CtImportImpl]import java.sql.DriverManager;
[CtUnresolvedImport]import org.apache.hadoop.hbase.util.Bytes;
[CtUnresolvedImport]import org.apache.phoenix.schema.PTable;
[CtUnresolvedImport]import org.apache.phoenix.util.TestUtil;
[CtUnresolvedImport]import org.apache.hadoop.hbase.TableName;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.sql.Connection;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
[CtUnresolvedImport]import org.junit.experimental.categories.Category;
[CtUnresolvedImport]import static org.apache.phoenix.jdbc.PhoenixDatabaseMetaData.CHANGE_DETECTION_ENABLED;
[CtUnresolvedImport]import org.apache.hadoop.hbase.HRegionInfo;
[CtUnresolvedImport]import org.apache.phoenix.hbase.index.IndexRegionObserver;
[CtUnresolvedImport]import org.apache.hadoop.hbase.coprocessor.BaseWALObserver;
[CtUnresolvedImport]import org.apache.phoenix.compat.hbase.HbaseCompatCapabilities;
[CtUnresolvedImport]import org.apache.phoenix.jdbc.PhoenixConnection;
[CtUnresolvedImport]import org.apache.phoenix.schema.PTableType;
[CtUnresolvedImport]import org.apache.phoenix.execute.MutationState;
[CtUnresolvedImport]import org.apache.hadoop.hbase.coprocessor.ObserverContext;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.apache.hadoop.hbase.wal.WALKey;
[CtUnresolvedImport]import org.apache.phoenix.exception.SQLExceptionCode;
[CtUnresolvedImport]import org.junit.Assert;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.Parameterized.class)
[CtAnnotationImpl]@org.junit.experimental.categories.Category([CtFieldReadImpl]org.apache.phoenix.end2end.NeedsOwnMiniClusterTest.class)
public class WALAnnotationIT extends [CtTypeReferenceImpl]org.apache.phoenix.end2end.BaseUniqueNamesOwnClusterIT {
    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isImmutable;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isMultiTenant;

    [CtMethodImpl][CtCommentImpl]// name is used by failsafe as file name in reports
    [CtAnnotationImpl]@Parameterized.Parameters(name = [CtLiteralImpl]"WALAnnotationIT_isImmutable={0}_isMultiTenant={1}")
    public static synchronized [CtTypeReferenceImpl]java.util.Collection<[CtArrayTypeReferenceImpl]java.lang.Object[]> data() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]true, [CtLiteralImpl]true }, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]true, [CtLiteralImpl]false }, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]false, [CtLiteralImpl]true }, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]false, [CtLiteralImpl]false });
    }

    [CtConstructorImpl]public WALAnnotationIT([CtParameterImpl][CtTypeReferenceImpl]boolean isImmutable, [CtParameterImpl][CtTypeReferenceImpl]boolean isMultiTenant) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isImmutable = [CtVariableReadImpl]isImmutable;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isMultiTenant = [CtVariableReadImpl]isMultiTenant;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.BeforeClass
    public static synchronized [CtTypeReferenceImpl]void doSetup() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]2);
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtLiteralImpl]"hbase.coprocessor.wal.classes", [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver.class.getName());
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]IndexRegionObserver.PHOENIX_APPEND_METADATA_TO_WAL, [CtLiteralImpl]"true");
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]QueryServices.ENABLE_SERVER_UPSERT_SELECT, [CtLiteralImpl]"true");
        [CtInvocationImpl]setUpTestDriver([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.util.ReadOnlyProps([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]props.entrySet().iterator()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSimpleUpsertAndDelete() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean createGlobalIndex = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long ddlTimestamp = [CtInvocationImpl]upsertAndDeleteHelper([CtVariableReadImpl]builder, [CtVariableReadImpl]createGlobalIndex);
        [CtInvocationImpl]assertAnnotation([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]builder.getPhysicalTableName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getDataOptions().getTableName(), [CtTypeAccessImpl]PTableType.TABLE, [CtVariableReadImpl]ddlTimestamp);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNoAnnotationsIfChangeDetectionDisabled() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtInvocationImpl]getUrl())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]conn.setAutoCommit([CtLiteralImpl]true);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.TableOptions tableOptions = [CtInvocationImpl]getTableOptions();
            [CtInvocationImpl][CtVariableReadImpl]tableOptions.setChangeDetectionEnabled([CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableOptions([CtVariableReadImpl]tableOptions).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtLiteralImpl]"Change detection is enabled when it shouldn't be!", [CtInvocationImpl][CtVariableReadImpl]table.isChangeDetectionEnabled());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String upsertSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" VALUES") + [CtLiteralImpl]" ('a', 'b', '2', 'bc', '3')";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]upsertSql);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>> entries = [CtInvocationImpl]getEntriesForTable([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.TableName.valueOf([CtInvocationImpl][CtVariableReadImpl]builder.getPhysicalTableName([CtLiteralImpl]false)));
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]entries.size());
            [CtLocalVariableImpl][CtCommentImpl]// now flip to TRUE so we can test disabling it
            [CtTypeReferenceImpl]java.lang.String enableSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ALTER TABLE " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" SET ") + [CtFieldReadImpl]PhoenixDatabaseMetaData.CHANGE_DETECTION_ENABLED) + [CtLiteralImpl]"=TRUE";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]enableSql);
            [CtAssignmentImpl][CtVariableWriteImpl]table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"Change detection is disabled when it should be enabled!", [CtInvocationImpl][CtVariableReadImpl]table.isChangeDetectionEnabled());
            [CtLocalVariableImpl][CtCommentImpl]// set to FALSE
            [CtTypeReferenceImpl]java.lang.String disableSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ALTER TABLE " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" SET ") + [CtFieldReadImpl]PhoenixDatabaseMetaData.CHANGE_DETECTION_ENABLED) + [CtLiteralImpl]"=FALSE";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]disableSql);
            [CtAssignmentImpl][CtVariableWriteImpl]table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtLiteralImpl]"Change detection is enabled when it should be disabled!", [CtInvocationImpl][CtVariableReadImpl]table.isChangeDetectionEnabled());
            [CtInvocationImpl][CtCommentImpl]// now upsert again
            [CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]upsertSql);
            [CtAssignmentImpl][CtCommentImpl]// check that we still didn't annotate anything
            [CtVariableWriteImpl]entries = [CtInvocationImpl]getEntriesForTable([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.TableName.valueOf([CtInvocationImpl][CtVariableReadImpl]builder.getPhysicalTableName([CtLiteralImpl]false)));
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]entries.size());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testCantSetChangeDetectionOnIndex() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtInvocationImpl]getUrl())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableDefaults().build();
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String badIndexSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"CREATE INDEX IDX_SHOULD_FAIL" + [CtLiteralImpl]" ON ") + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]"(COL1) ") + [CtFieldReadImpl]PhoenixDatabaseMetaData.CHANGE_DETECTION_ENABLED) + [CtLiteralImpl]"=TRUE";
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]badIndexSql);
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.fail([CtBinaryOperatorImpl][CtLiteralImpl]"Didn't throw a SQLException for setting change detection on an " + [CtLiteralImpl]"index at create time!");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException se) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.TestUtil.assertSqlExceptionCode([CtTypeAccessImpl]SQLExceptionCode.CHANGE_DETECTION_SUPPORTED_FOR_TABLES_AND_VIEWS_ONLY, [CtVariableReadImpl]se);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpsertAndDeleteWithGlobalIndex() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean createGlobalIndex = [CtLiteralImpl]true;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long ddlTimestamp = [CtInvocationImpl]upsertAndDeleteHelper([CtVariableReadImpl]builder, [CtVariableReadImpl]createGlobalIndex);
        [CtInvocationImpl]assertAnnotation([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]builder.getPhysicalTableName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getDataOptions().getTableName(), [CtTypeAccessImpl]PTableType.TABLE, [CtVariableReadImpl]ddlTimestamp);
        [CtInvocationImpl]assertAnnotation([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]builder.getPhysicalTableIndexName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.SchemaUtil.getTableNameFromFullName([CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableIndexName()), [CtTypeAccessImpl]PTableType.INDEX, [CtVariableReadImpl]ddlTimestamp);
    }

    [CtMethodImpl][CtCommentImpl]// Note that local secondary indexes aren't supported because they go in the same WALEdit as the
    [CtCommentImpl]// "base" table data they index.
    private [CtTypeReferenceImpl]long upsertAndDeleteHelper([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder, [CtParameterImpl][CtTypeReferenceImpl]boolean createGlobalIndex) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.TableOptions tableOptions = [CtInvocationImpl]getTableOptions();
            [CtIfImpl]if ([CtVariableReadImpl]createGlobalIndex) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableOptions([CtVariableReadImpl]tableOptions).withTableIndexDefaults().build();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableOptions([CtVariableReadImpl]tableOptions).build();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String upsertSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" VALUES") + [CtLiteralImpl]" ('a', 'b', 'c')";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]upsertSql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"Change Detection Enabled is false!", [CtInvocationImpl][CtVariableReadImpl]table.isChangeDetectionEnabled());
            [CtLocalVariableImpl][CtCommentImpl]// Deleting by entire PK gets executed as more like an UPSERT VALUES than an UPSERT SELECT
            [CtCommentImpl]// (i.e, it generates the Mutations and then pushes them to server, rather than
            [CtCommentImpl]// running a select query and deleting the mutations returned)
            [CtTypeReferenceImpl]java.lang.String deleteSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"DELETE FROM " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"WHERE OID = 'a' AND KP = 'b'";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]deleteSql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]table.getLastDDLTimestamp();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]SchemaBuilder.TableOptions getTableOptions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.TableOptions tableOptions = [CtInvocationImpl][CtTypeAccessImpl]SchemaBuilder.TableOptions.withDefaults();
        [CtInvocationImpl][CtVariableReadImpl]tableOptions.setImmutable([CtFieldReadImpl]isImmutable);
        [CtInvocationImpl][CtVariableReadImpl]tableOptions.setMultiTenant([CtFieldReadImpl]isMultiTenant);
        [CtInvocationImpl][CtVariableReadImpl]tableOptions.setChangeDetectionEnabled([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]tableOptions;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpsertSelectClientSide() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder baseBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder targetBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
            [CtInvocationImpl][CtCommentImpl]// upsert selecting from a different table will force processing to be client-side
            [CtInvocationImpl][CtVariableReadImpl]baseBuilder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getEntityTableName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"VALUES") + [CtLiteralImpl]" ('a', 'b', '2', 'bc', '3')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName()) + [CtLiteralImpl]" (OID, KP, COL1, COL2, COL3) SELECT * FROM ") + [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getEntityTableName();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]sql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int expectedAnnotations = [CtLiteralImpl]1;
            [CtInvocationImpl]verifyBaseAndTargetAnnotations([CtVariableReadImpl]conn, [CtVariableReadImpl]baseBuilder, [CtVariableReadImpl]targetBuilder, [CtVariableReadImpl]expectedAnnotations);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void verifyBaseAndTargetAnnotations([CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection conn, [CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder baseBuilder, [CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder targetBuilder, [CtParameterImpl][CtTypeReferenceImpl]int expectedAnnotations) throws [CtTypeReferenceImpl]java.sql.SQLException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable baseTable = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getEntityTableName());
        [CtInvocationImpl]assertAnnotation([CtVariableReadImpl]expectedAnnotations, [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getPhysicalTableName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]baseBuilder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]baseBuilder.getDataOptions().getTableName(), [CtTypeAccessImpl]PTableType.TABLE, [CtInvocationImpl][CtVariableReadImpl]baseTable.getLastDDLTimestamp());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable targetTable = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName());
        [CtInvocationImpl]assertAnnotation([CtVariableReadImpl]expectedAnnotations, [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getPhysicalTableName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.getDataOptions().getTableName(), [CtTypeAccessImpl]PTableType.TABLE, [CtInvocationImpl][CtVariableReadImpl]targetTable.getLastDDLTimestamp());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpsertSelectServerSide() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeFalse([CtFieldReadImpl]isImmutable);[CtCommentImpl]// only mutable tables can be processed server-side

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder targetBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"VALUES") + [CtLiteralImpl]" ('a', 'b', '2', 'bc', '3')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtInvocationImpl][CtVariableReadImpl]conn.setAutoCommit([CtLiteralImpl]true);[CtCommentImpl]// required for server side execution

            [CtInvocationImpl]clearAnnotations([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.TableName.valueOf([CtInvocationImpl][CtVariableReadImpl]targetBuilder.getPhysicalTableName([CtLiteralImpl]false)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName()) + [CtLiteralImpl]" (OID, KP, COL1, COL2, COL3) SELECT * FROM ") + [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]sql);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName());
            [CtInvocationImpl]assertAnnotation([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getPhysicalTableName([CtLiteralImpl]false), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.getTableOptions().getSchemaName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.getDataOptions().getTableName(), [CtTypeAccessImpl]PTableType.TABLE, [CtInvocationImpl][CtVariableReadImpl]table.getLastDDLTimestamp());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGroupedUpsertSelect() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtLocalVariableImpl][CtCommentImpl]// because we're inserting to a different table than we're selecting from, this should be
        [CtCommentImpl]// processed client-side
        [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder baseBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder targetBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]baseBuilder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetBuilder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getEntityTableName()) + [CtLiteralImpl]" VALUES") + [CtLiteralImpl]" ('a', 'b', '2', 'bc', '3')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String aggSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]targetBuilder.getEntityTableName()) + [CtLiteralImpl]" SELECT OID, KP, MAX(COL1), MIN(COL2), MAX(COL3) FROM ") + [CtInvocationImpl][CtVariableReadImpl]baseBuilder.getEntityTableName()) + [CtLiteralImpl]" GROUP BY OID, KP";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]aggSql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int expectedAnnotations = [CtLiteralImpl]1;
            [CtInvocationImpl]verifyBaseAndTargetAnnotations([CtVariableReadImpl]conn, [CtVariableReadImpl]baseBuilder, [CtVariableReadImpl]targetBuilder, [CtVariableReadImpl]expectedAnnotations);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRangeDeleteServerSide() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isClientSide = [CtLiteralImpl]false;
        [CtInvocationImpl]testRangeDeleteHelper([CtVariableReadImpl]isClientSide);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testRangeDeleteHelper([CtParameterImpl][CtTypeReferenceImpl]boolean isClientSide) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableOptions([CtInvocationImpl]getTableOptions()).build();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" VALUES ('a', 'b', '2', 'bc', '3')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtCommentImpl]// Deleting by a partial PK to so that it executes a SELECT and then deletes the
            [CtCommentImpl]// returned mutations
            [CtTypeReferenceImpl]java.lang.String sql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"DELETE FROM " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"WHERE OID = 'a' AND KP = 'b'";
            [CtIfImpl]if ([CtVariableReadImpl]isClientSide) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]sql += [CtLiteralImpl]" LIMIT 1";
            }
            [CtInvocationImpl][CtVariableReadImpl]conn.setAutoCommit([CtUnaryOperatorImpl]![CtVariableReadImpl]isClientSide);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]sql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable table = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName());
            [CtInvocationImpl]assertAnnotation([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getPhysicalName().getString(), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getSchemaName().getString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getTableName().getString(), [CtTypeAccessImpl]PTableType.TABLE, [CtInvocationImpl][CtVariableReadImpl]table.getLastDDLTimestamp());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRangeDeleteClientSide() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isClientSide = [CtLiteralImpl]true;
        [CtInvocationImpl]testRangeDeleteHelper([CtVariableReadImpl]isClientSide);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGlobalViewUpsert() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtInvocationImpl]createGlobalViewHelper([CtVariableReadImpl]builder, [CtVariableReadImpl]conn);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityGlobalViewName()) + [CtLiteralImpl]" VALUES") + [CtLiteralImpl]" ('a', '") + [CtFieldReadImpl]PhoenixTestBuilder.DDLDefaults.DEFAULT_KP) + [CtLiteralImpl]"', '2', 'bc', '3', 'c')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deleteSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"DELETE FROM " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityGlobalViewName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"WHERE OID = 'a' AND KP = '") + [CtFieldReadImpl]PhoenixTestBuilder.DDLDefaults.DEFAULT_KP) + [CtLiteralImpl]"' ") + [CtLiteralImpl]"and ID = 'c'";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]deleteSql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable view = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityGlobalViewName());
            [CtInvocationImpl]assertAnnotation([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getPhysicalName().getString(), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getSchemaName().getString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getTableName().getString(), [CtTypeAccessImpl]PTableType.VIEW, [CtInvocationImpl][CtVariableReadImpl]view.getLastDDLTimestamp());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void createGlobalViewHelper([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder, [CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection conn) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTableOptions([CtInvocationImpl]getTableOptions()).withGlobalViewOptions([CtInvocationImpl]getGlobalViewOptions([CtVariableReadImpl]builder)).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable view = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityGlobalViewName());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"View does not have change detection enabled!", [CtInvocationImpl][CtVariableReadImpl]view.isChangeDetectionEnabled());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]SchemaBuilder.GlobalViewOptions getGlobalViewOptions([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.GlobalViewOptions options = [CtInvocationImpl][CtTypeAccessImpl]SchemaBuilder.GlobalViewOptions.withDefaults();
        [CtInvocationImpl][CtVariableReadImpl]options.setChangeDetectionEnabled([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]options;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTenantViewUpsert() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtFieldReadImpl]isMultiTenant);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean createIndex = [CtLiteralImpl]false;
        [CtInvocationImpl]tenantViewHelper([CtVariableReadImpl]createIndex);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void tenantViewHelper([CtParameterImpl][CtTypeReferenceImpl]boolean createIndex) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// create a base table, global view, and child tenant view, then insert / delete into the
        [CtCommentImpl]// child tenant view. Make sure that the annotations use the tenant view name
        [CtTypeReferenceImpl]java.lang.String tenant = [CtInvocationImpl]generateUniqueName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder([CtInvocationImpl]getUrl());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getConnection()) [CtBlockImpl]{
            [CtInvocationImpl]createGlobalViewHelper([CtVariableReadImpl]builder, [CtVariableReadImpl]conn);
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl]getTenantConnection([CtVariableReadImpl]tenant)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.DataOptions dataOptions = [CtInvocationImpl][CtVariableReadImpl]builder.getDataOptions();
            [CtInvocationImpl][CtVariableReadImpl]dataOptions.setTenantId([CtVariableReadImpl]tenant);
            [CtIfImpl]if ([CtVariableReadImpl]createIndex) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTenantViewOptions([CtInvocationImpl]getTenantViewOptions([CtVariableReadImpl]builder)).withDataOptions([CtVariableReadImpl]dataOptions).withTenantViewIndexDefaults().build();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTenantViewOptions([CtInvocationImpl]getTenantViewOptions([CtVariableReadImpl]builder)).withDataOptions([CtVariableReadImpl]dataOptions).build();
            }
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.withTenantViewOptions([CtInvocationImpl]getTenantViewOptions([CtVariableReadImpl]builder)).withDataOptions([CtVariableReadImpl]dataOptions).withTenantViewIndexDefaults().build();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"UPSERT INTO " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTenantViewName()) + [CtLiteralImpl]" VALUES") + [CtLiteralImpl]" ('") + [CtFieldReadImpl]PhoenixTestBuilder.DDLDefaults.DEFAULT_KP) + [CtLiteralImpl]"', '2', 'bc', ") + [CtLiteralImpl]"'3', 'c', ") + [CtLiteralImpl]"'col4', 'col5', 'col6', 'd')");
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deleteSql = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"DELETE FROM " + [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTenantViewName()) + [CtLiteralImpl]" ") + [CtLiteralImpl]"WHERE KP = '") + [CtFieldReadImpl]PhoenixTestBuilder.DDLDefaults.DEFAULT_KP) + [CtLiteralImpl]"' and COL1 = '2' AND ID = 'c' AND ZID = 'd'";
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]conn.createStatement().execute([CtVariableReadImpl]deleteSql);
            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTable view = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.PhoenixRuntime.getTableNoCache([CtVariableReadImpl]conn, [CtInvocationImpl][CtVariableReadImpl]builder.getEntityTenantViewName());
            [CtInvocationImpl]assertAnnotation([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getPhysicalName().getString(), [CtVariableReadImpl]tenant, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getSchemaName().getString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getTableName().getString(), [CtTypeAccessImpl]PTableType.VIEW, [CtInvocationImpl][CtVariableReadImpl]view.getLastDDLTimestamp());
            [CtIfImpl]if ([CtVariableReadImpl]createIndex) [CtBlockImpl]{
                [CtInvocationImpl]assertAnnotation([CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.MetaDataUtil.getViewIndexPhysicalName([CtInvocationImpl][CtVariableReadImpl]builder.getEntityTableName()), [CtVariableReadImpl]tenant, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]view.getSchemaName().getString(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.SchemaUtil.getTableNameFromFullName([CtInvocationImpl][CtVariableReadImpl]builder.getEntityTenantViewIndexName()), [CtTypeAccessImpl]PTableType.INDEX, [CtInvocationImpl][CtVariableReadImpl]view.getLastDDLTimestamp());
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]SchemaBuilder.TenantViewOptions getTenantViewOptions([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder builder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.phoenix.query.PhoenixTestBuilder.SchemaBuilder.TenantViewOptions options = [CtInvocationImpl][CtTypeAccessImpl]SchemaBuilder.TenantViewOptions.withDefaults();
        [CtInvocationImpl][CtVariableReadImpl]options.setChangeDetectionEnabled([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]options;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTenantViewUpsertWithIndex() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.HbaseCompatCapabilities.hasPreWALAppend());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assume.assumeTrue([CtFieldReadImpl]isMultiTenant);
        [CtInvocationImpl]tenantViewHelper([CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>> getEntriesForTable([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver c = [CtInvocationImpl]getTestCoprocessor([CtVariableReadImpl]tableName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>> entries = [CtInvocationImpl][CtVariableReadImpl]c.getWalAnnotationsByTable([CtVariableReadImpl]tableName);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]entries != [CtLiteralImpl]null ? [CtVariableReadImpl]entries : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>>();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver getTestCoprocessor([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.HRegionInfo info = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getUtility().getHBaseCluster().getRegions([CtVariableReadImpl]tableName).get([CtLiteralImpl]0).getRegionInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.wal.WAL wal = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getUtility().getHBaseCluster().getRegionServer([CtLiteralImpl]0).getWAL([CtVariableReadImpl]info);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.regionserver.wal.WALCoprocessorHost host = [CtInvocationImpl][CtVariableReadImpl]wal.getCoprocessorHost();
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver) ([CtVariableReadImpl]host.findCoprocessor([CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver.class.getName())));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void clearAnnotations([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.end2end.WALAnnotationIT.AnnotatedWALObserver observer = [CtInvocationImpl]getTestCoprocessor([CtVariableReadImpl]tableName);
        [CtInvocationImpl][CtVariableReadImpl]observer.clearAnnotations();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertAnnotation([CtParameterImpl][CtTypeReferenceImpl]int numOccurrences, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String physicalTableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenant, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String logicalTableName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.schema.PTableType tableType, [CtParameterImpl][CtTypeReferenceImpl]long ddlTimestamp) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int foundCount = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int notFoundCount = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>> entries = [CtInvocationImpl]getEntriesForTable([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.TableName.valueOf([CtVariableReadImpl]physicalTableName));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]> m : [CtVariableReadImpl]entries) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] tenantBytes = [CtInvocationImpl][CtVariableReadImpl]m.get([CtInvocationImpl][CtTypeAccessImpl]MutationState.MutationMetadataType.TENANT_ID.toString());
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] schemaBytes = [CtInvocationImpl][CtVariableReadImpl]m.get([CtInvocationImpl][CtTypeAccessImpl]MutationState.MutationMetadataType.SCHEMA_NAME.toString());
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] logicalTableBytes = [CtInvocationImpl][CtVariableReadImpl]m.get([CtInvocationImpl][CtTypeAccessImpl]MutationState.MutationMetadataType.LOGICAL_TABLE_NAME.toString());
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] tableTypeBytes = [CtInvocationImpl][CtVariableReadImpl]m.get([CtInvocationImpl][CtTypeAccessImpl]MutationState.MutationMetadataType.TABLE_TYPE.toString());
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] timestampBytes = [CtInvocationImpl][CtVariableReadImpl]m.get([CtInvocationImpl][CtTypeAccessImpl]MutationState.MutationMetadataType.TIMESTAMP.toString());
            [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]timestampBytes);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long timestamp = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toLong([CtVariableReadImpl]timestampBytes);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]tenant, [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toString([CtVariableReadImpl]tenantBytes)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]schemaName, [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toString([CtVariableReadImpl]schemaBytes))) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]logicalTableName, [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toString([CtVariableReadImpl]logicalTableBytes))) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]tableType.toString(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toString([CtVariableReadImpl]tableTypeBytes))) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]ddlTimestamp, [CtVariableReadImpl]timestamp)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]timestamp < [CtFieldReadImpl]org.apache.hadoop.hbase.HConstants.LATEST_TIMESTAMP)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]foundCount++;
            } else [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]notFoundCount++;
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtVariableReadImpl]numOccurrences, [CtVariableReadImpl]foundCount);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]0, [CtVariableReadImpl]notFoundCount);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.phoenix.jdbc.PhoenixConnection getConnection() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtTypeAccessImpl]QueryServices.IS_NAMESPACE_MAPPING_ENABLED, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.toString([CtLiteralImpl]false));
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.jdbc.PhoenixConnection) ([CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtInvocationImpl]getUrl(), [CtVariableReadImpl]props)));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.sql.Connection getTenantConnection([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenant) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtTypeAccessImpl]PhoenixRuntime.TENANT_ID_ATTRIB, [CtVariableReadImpl]tenant);
        [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtTypeAccessImpl]QueryServices.IS_NAMESPACE_MAPPING_ENABLED, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.toString([CtLiteralImpl]false));
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtInvocationImpl]getUrl(), [CtVariableReadImpl]props);
    }

    [CtClassImpl]public static class AnnotatedWALObserver extends [CtTypeReferenceImpl]org.apache.hadoop.hbase.coprocessor.BaseWALObserver {
        [CtFieldImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>>> walAnnotations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>>> getWalAnnotations() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]walAnnotations;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>> getWalAnnotationsByTable([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]walAnnotations.get([CtVariableReadImpl]tableName);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void clearAnnotations() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]walAnnotations.clear();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void postWALWrite([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.coprocessor.ObserverContext<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.hadoop.hbase.coprocessor.WALCoprocessorEnvironment> ctx, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.HRegionInfo info, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.wal.WALKey logKey, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.regionserver.wal.WALEdit logEdit) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName = [CtInvocationImpl][CtVariableReadImpl]logKey.getTablename();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]> annotationMap = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.compat.hbase.coprocessor.CompatIndexRegionObserver.getAttributeValuesFromWALKey([CtVariableReadImpl]logKey);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]annotationMap.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]walAnnotations.containsKey([CtVariableReadImpl]tableName)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]walAnnotations.put([CtVariableReadImpl]tableName, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]byte[]>>());
                }
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]walAnnotations.get([CtInvocationImpl][CtVariableReadImpl]logKey.getTablename()).add([CtVariableReadImpl]annotationMap);
            }
        }
    }
}
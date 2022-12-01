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
[CtPackageDeclarationImpl]package org.apache.iceberg.mr.hive;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
[CtUnresolvedImport]import org.apache.iceberg.mr.hive.serde.objectinspector.IcebergRecordObjectInspector;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
[CtUnresolvedImport]import org.apache.iceberg.Schema;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
[CtUnresolvedImport]import org.apache.hadoop.hive.metastore.api.FieldSchema;
[CtUnresolvedImport]import org.apache.iceberg.mr.hive.serde.objectinspector.IcebergObjectInspector;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.hadoop.hive.serde2.objectinspector.StructField;
[CtClassImpl]public class HiveSchemaUtil {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.class);

    [CtConstructorImpl]private HiveSchemaUtil() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the list of Hive FieldSchemas to an Iceberg schema.
     * <p>
     * The list should contain the columns and the partition columns as well.
     *
     * @param fieldSchemas
     * 		The list of the columns
     * @return An equivalent Iceberg Schema
     */
    public static [CtTypeReferenceImpl]org.apache.iceberg.Schema schema([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.hive.metastore.api.FieldSchema> fieldSchemas) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> names = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]fieldSchemas.size());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.typeinfo.TypeInfo> typeInfos = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]fieldSchemas.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.metastore.api.FieldSchema col : [CtVariableReadImpl]fieldSchemas) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]names.add([CtInvocationImpl][CtVariableReadImpl]col.getName());
            [CtInvocationImpl][CtVariableReadImpl]typeInfos.add([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils.getTypeInfoFromTypeString([CtInvocationImpl][CtVariableReadImpl]col.getType()));
        }
        [CtReturnImpl]return [CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.convert([CtVariableReadImpl]names, [CtVariableReadImpl]typeInfos);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Converts the Hive properties defining the columns to an Iceberg schema.
     *
     * @param columnNames
     * 		The property containing the column names
     * @param columnTypes
     * 		The property containing the column types
     * @param columnNameDelimiter
     * 		The name delimiter
     * @return The Iceberg schema
     */
    public static [CtTypeReferenceImpl]org.apache.iceberg.Schema schema([CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnNames, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnTypes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnNameDelimiter) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Parse the configuration parameters
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> names = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.addAll([CtVariableReadImpl]names, [CtInvocationImpl][CtVariableReadImpl]columnNames.split([CtVariableReadImpl]columnNameDelimiter));
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.convert([CtVariableReadImpl]names, [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils.getTypeInfosFromTypeString([CtVariableReadImpl]columnTypes));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the ObjectInspectors generated by the two schema definitions are compatible.
     * <p>
     * Currently only allows the same column names and column types. Later we might want allow compatible column types as
     * well.
     * TODO: We might want to allow compatible conversions
     *
     * @param schema
     * 		First schema
     * @param other
     * 		Second schema
     * @return True if the two schema is compatible
     */
    public static [CtTypeReferenceImpl]boolean compatible([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema other) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector inspector = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.mr.hive.serde.objectinspector.IcebergObjectInspector.create([CtVariableReadImpl]schema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector otherInspector = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.mr.hive.serde.objectinspector.IcebergObjectInspector.create([CtVariableReadImpl]other);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]inspector instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.iceberg.mr.hive.serde.objectinspector.IcebergRecordObjectInspector)) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]otherInspector instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.iceberg.mr.hive.serde.objectinspector.IcebergRecordObjectInspector))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtVariableReadImpl]inspector, [CtVariableReadImpl]otherInspector);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean compatible([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector inspector, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector other) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]inspector == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]other == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// We do not expect this type of calls, but for completeness shake
            return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]inspector == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]other == [CtLiteralImpl]null)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inspector.getCategory().equals([CtInvocationImpl][CtVariableReadImpl]other.getCategory()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]inspector.getCategory()) {
            [CtCaseImpl]case [CtFieldReadImpl]PRIMITIVE :
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]inspector instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector) && [CtBinaryOperatorImpl]([CtVariableReadImpl]other instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]inspector.equals([CtVariableReadImpl]other);
            [CtCaseImpl]case [CtFieldReadImpl]MAP :
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector mapInspector = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector) (inspector));
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector otherMapInspector = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector) (other));
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtInvocationImpl][CtVariableReadImpl]mapInspector.getMapKeyObjectInspector(), [CtInvocationImpl][CtVariableReadImpl]otherMapInspector.getMapKeyObjectInspector()) && [CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtInvocationImpl][CtVariableReadImpl]mapInspector.getMapValueObjectInspector(), [CtInvocationImpl][CtVariableReadImpl]otherMapInspector.getMapValueObjectInspector());
            [CtCaseImpl]case [CtFieldReadImpl]LIST :
                [CtReturnImpl]return [CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector) (inspector)).getListElementObjectInspector(), [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector) (other)).getListElementObjectInspector());
            [CtCaseImpl]case [CtFieldReadImpl]STRUCT :
                [CtReturnImpl]return [CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector) (inspector)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector) (other)));
            [CtCaseImpl]case [CtFieldReadImpl]UNION :
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unsupported inspector type " + [CtVariableReadImpl]inspector) + [CtLiteralImpl]", ") + [CtVariableReadImpl]other);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean compatible([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector inspector, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector other) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructField> structFields = [CtInvocationImpl][CtVariableReadImpl]inspector.getAllStructFieldRefs();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]structFields.size() != [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]other.getAllStructFieldRefs().size()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructField field : [CtVariableReadImpl]structFields) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.objectinspector.StructField otherField;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]otherField = [CtInvocationImpl][CtVariableReadImpl]other.getStructFieldRef([CtInvocationImpl][CtVariableReadImpl]field.getFieldName());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.LOG.debug([CtLiteralImpl]"Error getting struct field", [CtVariableReadImpl]e);
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]org.apache.iceberg.mr.hive.HiveSchemaUtil.compatible([CtInvocationImpl][CtVariableReadImpl]field.getFieldObjectInspector(), [CtInvocationImpl][CtVariableReadImpl]otherField.getFieldObjectInspector())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.iceberg.Schema convert([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> names, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.hadoop.hive.serde2.typeinfo.TypeInfo> typeInfos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.mr.hive.HiveSchemaConverter converter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.mr.hive.HiveSchemaConverter();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.Schema([CtInvocationImpl][CtVariableReadImpl]converter.convert([CtVariableReadImpl]names, [CtVariableReadImpl]typeInfos));
    }
}
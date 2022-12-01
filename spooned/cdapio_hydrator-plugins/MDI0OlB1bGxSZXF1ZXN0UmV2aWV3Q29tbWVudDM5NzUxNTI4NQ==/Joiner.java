[CompilationUnitImpl][CtCommentImpl]/* Copyright © 2016-2019 Cask Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
 */
[CtPackageDeclarationImpl]package io.cdap.plugin.batch.joiner;
[CtUnresolvedImport]import com.google.common.collect.ArrayListMultimap;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.lineage.field.FieldOperation;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.batch.BatchJoinerRuntimeContext;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Description;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.batch.BatchJoinerContext;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import com.google.common.collect.Multimap;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.FailureCollector;
[CtUnresolvedImport]import io.cdap.cdap.api.data.format.StructuredRecord;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Name;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.MultiInputStageConfigurer;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.JoinConfig;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.batch.BatchJoiner;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.lineage.field.FieldTransformOperation;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Plugin;
[CtUnresolvedImport]import io.cdap.cdap.api.data.schema.Schema;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.JoinElement;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.MultiInputPipelineConfigurer;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.google.common.collect.Table;
[CtClassImpl][CtJavaDocImpl]/**
 * Batch joiner to join records from multiple inputs
 */
[CtAnnotationImpl]@io.cdap.cdap.api.annotation.Plugin(type = [CtFieldReadImpl]io.cdap.cdap.etl.api.batch.BatchJoiner.PLUGIN_TYPE)
[CtAnnotationImpl]@io.cdap.cdap.api.annotation.Name([CtLiteralImpl]"Joiner")
[CtAnnotationImpl]@io.cdap.cdap.api.annotation.Description([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Performs join operation on records from each input based on required inputs. If all the inputs are " + [CtLiteralImpl]"required inputs, inner join will be performed. Otherwise inner join will be performed on required inputs and ") + [CtLiteralImpl]"records from non-required inputs will only be present if they match join criteria. If there are no required ") + [CtLiteralImpl]"inputs, outer join will be performed")
public class Joiner extends [CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchJoiner<[CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord, [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord, [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord> {
    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String JOIN_OPERATION_DESCRIPTION = [CtLiteralImpl]"Used as a key in a join";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String IDENTITY_OPERATION_DESCRIPTION = [CtLiteralImpl]"Unchanged as part of a join";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String RENAME_OPERATION_DESCRIPTION = [CtLiteralImpl]"Renamed as a part of a join";

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.JoinerConfig conf;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema outputSchema;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> perStageJoinKeys;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.common.collect.Table<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> perStageSelectedFields;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.common.collect.Multimap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> duplicateFields = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ArrayListMultimap.create();

    [CtFieldImpl]private [CtTypeReferenceImpl]io.cdap.cdap.etl.api.JoinConfig joinConfig;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> keySchemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl]public Joiner([CtParameterImpl][CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.JoinerConfig conf) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.conf = [CtVariableReadImpl]conf;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void configurePipeline([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.MultiInputPipelineConfigurer pipelineConfigurer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.MultiInputStageConfigurer stageConfigurer = [CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.getMultiInputStageConfigurer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas = [CtInvocationImpl][CtVariableReadImpl]stageConfigurer.getInputSchemas();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.getMultiInputStageConfigurer().getFailureCollector();
        [CtInvocationImpl]init([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]conf.inputSchemasAvailable([CtVariableReadImpl]inputSchemas)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]conf.containsMacro([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]conf.OUTPUT_SCHEMA))) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]conf.getOutputSchema([CtVariableReadImpl]collector) == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// If input schemas are unknown, an output schema must be provided.
            [CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtLiteralImpl]"Output schema must be specified", [CtLiteralImpl]null).withConfigProperty([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]conf.OUTPUT_SCHEMA);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema outputSchema = [CtInvocationImpl]getOutputSchema([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]outputSchema != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Set output schema if it's not a macro.
            [CtVariableReadImpl]stageConfigurer.setOutputSchema([CtVariableReadImpl]outputSchema);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void prepareRun([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchJoinerContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]conf.getNumPartitions() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]context.setNumPartitions([CtInvocationImpl][CtFieldReadImpl]conf.getNumPartitions());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtVariableReadImpl]context.getFailureCollector();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas = [CtInvocationImpl][CtVariableReadImpl]context.getInputSchemas();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]conf.inputSchemasAvailable([CtVariableReadImpl]inputSchemas)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// inputSchemas will be empty if the output schema of a previous node is a macro
            return;
        }
        [CtInvocationImpl]init([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo> outputFieldInfos = [CtInvocationImpl]createOutputFieldInfos([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtInvocationImpl][CtVariableReadImpl]context.record([CtInvocationImpl]io.cdap.plugin.batch.joiner.Joiner.createFieldOperations([CtVariableReadImpl]outputFieldInfos, [CtFieldReadImpl]perStageJoinKeys));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create the field operations from the provided OutputFieldInfo instances and join keys.
     * For join we record several types of transformation; Join, Identity, and Rename.
     * For each of these transformations, if the input field is directly coming from the schema
     * of one of the stage, the field is added as {@code stage_name.field_name}. We keep track of fields
     * outputted by operation (in {@code outputsSoFar set}, so that any operation uses that field as
     * input later, we add it without the stage name.
     * <p>
     * Join transform operation is added with join keys as input tagged with the stage name, and join keys
     * without stage name as output.
     * <p>
     * For other fields which are not renamed in join, Identity transform is added, while for fields which
     * are renamed Rename transform is added.
     *
     * @param outputFieldInfos
     * 		collection of output fields along with information such as stage name, alias
     * @param perStageJoinKeys
     * 		join keys
     * @return List of field operations
     */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldOperation> createFieldOperations([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo> outputFieldInfos, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> perStageJoinKeys) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldOperation> operations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtCommentImpl]// Add JOIN operation
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> joinInputs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> joinOutputs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> joinKey : [CtInvocationImpl][CtVariableReadImpl]perStageJoinKeys.entrySet()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String field : [CtInvocationImpl][CtVariableReadImpl]joinKey.getValue()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]joinInputs.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]joinKey.getKey() + [CtLiteralImpl]".") + [CtVariableReadImpl]field);
                [CtInvocationImpl][CtVariableReadImpl]joinOutputs.add([CtVariableReadImpl]field);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldOperation joinOperation = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldTransformOperation([CtLiteralImpl]"Join", [CtFieldReadImpl]io.cdap.plugin.batch.joiner.Joiner.JOIN_OPERATION_DESCRIPTION, [CtVariableReadImpl]joinInputs, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]joinOutputs));
        [CtInvocationImpl][CtVariableReadImpl]operations.add([CtVariableReadImpl]joinOperation);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> outputsSoFar = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtVariableReadImpl]joinOutputs);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo outputFieldInfo : [CtVariableReadImpl]outputFieldInfos) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// input field name for the operation will come in from schema if its not outputted so far
            [CtTypeReferenceImpl]java.lang.String stagedInputField = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]outputsSoFar.contains([CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.inputFieldName)) ? [CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.inputFieldName : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.stageName + [CtLiteralImpl]".") + [CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.inputFieldName;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.name.equals([CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.inputFieldName)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Record identity transform
                if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]perStageJoinKeys.get([CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.stageName).contains([CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.inputFieldName)) [CtBlockImpl]{
                    [CtContinueImpl][CtCommentImpl]// if the field is part of join key no need to emit the identity transform as it is already taken care
                    [CtCommentImpl]// by join
                    continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String operationName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Identity %s", [CtVariableReadImpl]stagedInputField);
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldOperation identity = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldTransformOperation([CtVariableReadImpl]operationName, [CtFieldReadImpl]io.cdap.plugin.batch.joiner.Joiner.IDENTITY_OPERATION_DESCRIPTION, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]stagedInputField), [CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.name);
                [CtInvocationImpl][CtVariableReadImpl]operations.add([CtVariableReadImpl]identity);
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String operationName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Rename %s", [CtVariableReadImpl]stagedInputField);
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldOperation transform = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.lineage.field.FieldTransformOperation([CtVariableReadImpl]operationName, [CtFieldReadImpl]io.cdap.plugin.batch.joiner.Joiner.RENAME_OPERATION_DESCRIPTION, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]stagedInputField), [CtFieldReadImpl][CtVariableReadImpl]outputFieldInfo.name);
            [CtInvocationImpl][CtVariableReadImpl]operations.add([CtVariableReadImpl]transform);
        }
        [CtReturnImpl]return [CtVariableReadImpl]operations;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void initialize([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchJoinerRuntimeContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtVariableReadImpl]context.getFailureCollector();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas = [CtInvocationImpl][CtVariableReadImpl]context.getInputSchemas();
        [CtInvocationImpl]init([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtAssignmentImpl][CtFieldWriteImpl]outputSchema = [CtInvocationImpl]getOutputSchema([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord joinOn([CtParameterImpl][CtTypeReferenceImpl]java.lang.String stageName, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord record) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema keySchema;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> joinKeys = [CtInvocationImpl][CtFieldReadImpl]perStageJoinKeys.get([CtVariableReadImpl]stageName);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]keySchemas.containsKey([CtVariableReadImpl]stageName)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keySchema = [CtInvocationImpl][CtFieldReadImpl]keySchemas.get([CtVariableReadImpl]stageName);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field> fields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema schema = [CtInvocationImpl][CtVariableReadImpl]record.getSchema();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String joinKey : [CtVariableReadImpl]joinKeys) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field field = [CtInvocationImpl][CtVariableReadImpl]schema.getField([CtVariableReadImpl]joinKey);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]field == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Join key field '%s' does not exist in schema from '%s'.", [CtVariableReadImpl]joinKey, [CtVariableReadImpl]stageName));
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field joinField = [CtInvocationImpl][CtTypeAccessImpl]Schema.Field.of([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtUnaryOperatorImpl][CtVariableWriteImpl]i++), [CtInvocationImpl][CtVariableReadImpl]field.getSchema());
                [CtInvocationImpl][CtVariableReadImpl]fields.add([CtVariableReadImpl]joinField);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]keySchema = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.schema.Schema.recordOf([CtLiteralImpl]"join.key", [CtVariableReadImpl]fields);
            [CtInvocationImpl][CtFieldReadImpl]keySchemas.put([CtVariableReadImpl]stageName, [CtVariableReadImpl]keySchema);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord.Builder keyRecordBuilder = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.format.StructuredRecord.builder([CtVariableReadImpl]keySchema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String joinKey : [CtVariableReadImpl]joinKeys) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]keyRecordBuilder.set([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtUnaryOperatorImpl][CtVariableWriteImpl]i++), [CtInvocationImpl][CtVariableReadImpl]record.get([CtVariableReadImpl]joinKey));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]keyRecordBuilder.build();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.cdap.cdap.etl.api.JoinConfig getJoinConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]joinConfig;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord merge([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord joinKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]io.cdap.cdap.etl.api.JoinElement<[CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord>> joinRow) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord.Builder outRecordBuilder = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.format.StructuredRecord.builder([CtFieldReadImpl]outputSchema);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.JoinElement<[CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord> joinElement : [CtVariableReadImpl]joinRow) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stageName = [CtInvocationImpl][CtVariableReadImpl]joinElement.getStageName();
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord record = [CtInvocationImpl][CtVariableReadImpl]joinElement.getInputRecord();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> selectedFields = [CtInvocationImpl][CtFieldReadImpl]perStageSelectedFields.row([CtVariableReadImpl]stageName);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field field : [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]record.getSchema().getFields())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inputFieldName = [CtInvocationImpl][CtVariableReadImpl]field.getName();
                [CtIfImpl][CtCommentImpl]// drop the field if not part of fieldsToRename
                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]selectedFields.containsKey([CtVariableReadImpl]inputFieldName)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String outputFieldName = [CtInvocationImpl][CtVariableReadImpl]selectedFields.get([CtVariableReadImpl]inputFieldName);
                [CtInvocationImpl][CtVariableReadImpl]outRecordBuilder.set([CtVariableReadImpl]outputFieldName, [CtInvocationImpl][CtVariableReadImpl]record.get([CtVariableReadImpl]inputFieldName));
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]outRecordBuilder.build();
    }

    [CtMethodImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector init([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector failureCollector) [CtBlockImpl]{
        [CtInvocationImpl]validateJoinKeySchemas([CtVariableReadImpl]inputSchemas, [CtInvocationImpl][CtFieldReadImpl]conf.getPerStageJoinKeys(), [CtVariableReadImpl]failureCollector);
        [CtAssignmentImpl][CtFieldWriteImpl]joinConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.JoinConfig([CtInvocationImpl][CtFieldReadImpl]conf.getInputs());
        [CtAssignmentImpl][CtFieldWriteImpl]perStageSelectedFields = [CtInvocationImpl][CtFieldReadImpl]conf.getPerStageSelectedFields();
        [CtReturnImpl]return [CtVariableReadImpl]failureCollector;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void validateJoinKeySchemas([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> joinKeys, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]perStageJoinKeys = [CtVariableReadImpl]joinKeys;
        [CtInvocationImpl][CtFieldReadImpl]conf.validateJoinKeySchemas([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]joinKeys, [CtVariableReadImpl]collector);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> prevSchemaList = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> entry : [CtInvocationImpl][CtFieldReadImpl]perStageJoinKeys.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> schemaList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stageName = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema schema = [CtInvocationImpl][CtVariableReadImpl]inputSchemas.get([CtVariableReadImpl]stageName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// Input schema will be null if the output schema of the previous node is a macro
                return;
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String joinKey : [CtInvocationImpl][CtVariableReadImpl]entry.getValue()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field field = [CtInvocationImpl][CtVariableReadImpl]schema.getField([CtVariableReadImpl]joinKey);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]field == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Join key field '%s' is not present in input stage of '%s'.", [CtVariableReadImpl]joinKey, [CtVariableReadImpl]stageName), [CtLiteralImpl]null).withConfigProperty([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]conf.JOIN_KEYS);
                }
                [CtInvocationImpl][CtVariableReadImpl]schemaList.add([CtInvocationImpl][CtVariableReadImpl]field.getSchema());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]prevSchemaList != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]prevSchemaList.equals([CtVariableReadImpl]schemaList))) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"For stage '%s', Schemas of join keys '%s' are expected to be: '%s', but found: '%s'.", [CtVariableReadImpl]stageName, [CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtInvocationImpl][CtVariableReadImpl]prevSchemaList.toString(), [CtInvocationImpl][CtVariableReadImpl]schemaList.toString()), [CtLiteralImpl]null).withConfigProperty([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]conf.JOIN_KEYS);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]prevSchemaList = [CtVariableReadImpl]schemaList;
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema getOutputSchema([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]perStageSelectedFields = [CtInvocationImpl][CtFieldReadImpl]conf.getPerStageSelectedFields();
        [CtAssignmentImpl][CtFieldWriteImpl]duplicateFields = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ArrayListMultimap.create();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field> outputFields = [CtInvocationImpl]getOutputFields([CtInvocationImpl]createOutputFieldInfos([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]outputFields.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Could not derive output schema from input schema. Try to get output schema from config.
            return [CtInvocationImpl][CtFieldReadImpl]conf.getOutputSchema([CtVariableReadImpl]collector);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.schema.Schema.recordOf([CtLiteralImpl]"join.output", [CtVariableReadImpl]outputFields);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo> createOutputFieldInfos([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector) [CtBlockImpl]{
        [CtInvocationImpl]validateRequiredInputs([CtVariableReadImpl]inputSchemas, [CtVariableReadImpl]collector);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtLocalVariableImpl][CtCommentImpl]// stage name to input schema
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtVariableReadImpl]inputSchemas);
        [CtLocalVariableImpl][CtCommentImpl]// Selected Field name to output field info
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo> outputFieldInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> duplicateAliases = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]conf.inputSchemasAvailable([CtVariableReadImpl]inputSchemas)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// inputSchemas will be empty if the output schema of a previous node is a macro
            return [CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.values();
        }
        [CtLocalVariableImpl][CtCommentImpl]// order of fields in output schema will be same as order of selectedFields
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.Table.Cell<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>> rows = [CtInvocationImpl][CtFieldReadImpl]perStageSelectedFields.cellSet();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.collect.Multimap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> duplicateFields = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ArrayListMultimap.create();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.Table.Cell<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> row : [CtVariableReadImpl]rows) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stageName = [CtInvocationImpl][CtVariableReadImpl]row.getRowKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inputFieldName = [CtInvocationImpl][CtVariableReadImpl]row.getColumnKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String alias = [CtInvocationImpl][CtVariableReadImpl]row.getValue();
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema inputSchema = [CtInvocationImpl][CtVariableReadImpl]inputs.get([CtVariableReadImpl]stageName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inputSchema == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Input schema for input stage '%s' cannot be null.", [CtVariableReadImpl]stageName), [CtLiteralImpl]null);
                [CtThrowImpl]throw [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.containsKey([CtVariableReadImpl]alias)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo outInfo = [CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.get([CtVariableReadImpl]alias);
                [CtInvocationImpl][CtVariableReadImpl]duplicateAliases.add([CtVariableReadImpl]alias);
                [CtInvocationImpl][CtVariableReadImpl]duplicateFields.put([CtInvocationImpl][CtVariableReadImpl]outInfo.getStageName(), [CtInvocationImpl][CtVariableReadImpl]outInfo.getInputFieldName());
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field inputField = [CtInvocationImpl][CtVariableReadImpl]inputSchema.getField([CtVariableReadImpl]inputFieldName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inputField == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Field '%s' of stage '%s' must be present in input schema '%s'.", [CtVariableReadImpl]inputFieldName, [CtVariableReadImpl]stageName, [CtVariableReadImpl]inputSchema), [CtLiteralImpl]null).withConfigElement([CtLiteralImpl]"selectedFields", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s.%s as %s", [CtVariableReadImpl]stageName, [CtVariableReadImpl]inputFieldName, [CtVariableReadImpl]alias));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]conf.getInputs().contains([CtVariableReadImpl]stageName) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputField.getSchema().isNullable()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.put([CtVariableReadImpl]alias, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo([CtVariableReadImpl]alias, [CtVariableReadImpl]stageName, [CtVariableReadImpl]inputFieldName, [CtInvocationImpl][CtTypeAccessImpl]Schema.Field.of([CtVariableReadImpl]alias, [CtInvocationImpl][CtVariableReadImpl]inputField.getSchema())));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.put([CtVariableReadImpl]alias, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo([CtVariableReadImpl]alias, [CtVariableReadImpl]stageName, [CtVariableReadImpl]inputFieldName, [CtInvocationImpl][CtTypeAccessImpl]Schema.Field.of([CtVariableReadImpl]alias, [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.schema.Schema.nullableOf([CtInvocationImpl][CtVariableReadImpl]inputField.getSchema()))));
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]duplicateFields.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Output schema must not contain duplicate fields: '%s' for aliases: '%s'.", [CtVariableReadImpl]duplicateFields, [CtVariableReadImpl]duplicateAliases), [CtLiteralImpl]null).withConfigProperty([CtTypeAccessImpl]JoinerConfig.SELECTED_FIELDS);
            [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]outputFieldInfo.values();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field> getOutputFields([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo> fieldsInfo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field> outputFields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo fieldInfo : [CtVariableReadImpl]fieldsInfo) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]outputFields.add([CtInvocationImpl][CtVariableReadImpl]fieldInfo.getField());
        }
        [CtReturnImpl]return [CtVariableReadImpl]outputFields;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Class to hold information about output fields
     */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    static class OutputFieldInfo {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String stageName;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String inputFieldName;

        [CtFieldImpl]private [CtTypeReferenceImpl]Schema.Field field;

        [CtConstructorImpl]OutputFieldInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String stageName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String inputFieldName, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field field) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stageName = [CtVariableReadImpl]stageName;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputFieldName = [CtVariableReadImpl]inputFieldName;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.field = [CtVariableReadImpl]field;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getStageName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]stageName;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setStageName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String stageName) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stageName = [CtVariableReadImpl]stageName;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getInputFieldName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]inputFieldName;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setInputFieldName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String inputFieldName) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputFieldName = [CtVariableReadImpl]inputFieldName;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]Schema.Field getField() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]field;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setField([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema.Field field) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.field = [CtVariableReadImpl]field;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo that = [CtVariableReadImpl](([CtTypeReferenceImpl]io.cdap.plugin.batch.joiner.Joiner.OutputFieldInfo) (o));
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]name.equals([CtFieldReadImpl][CtVariableReadImpl]that.name)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]stageName.equals([CtFieldReadImpl][CtVariableReadImpl]that.stageName)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]inputFieldName.equals([CtFieldReadImpl][CtVariableReadImpl]that.inputFieldName)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]field.equals([CtFieldReadImpl][CtVariableReadImpl]that.field);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int result = [CtInvocationImpl][CtFieldReadImpl]name.hashCode();
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtInvocationImpl][CtFieldReadImpl]stageName.hashCode();
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtInvocationImpl][CtFieldReadImpl]inputFieldName.hashCode();
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]31 * [CtVariableReadImpl]result) + [CtInvocationImpl][CtFieldReadImpl]field.hashCode();
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"OutputFieldInfo{" + [CtLiteralImpl]"name='") + [CtFieldReadImpl]name) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", stageName='") + [CtFieldReadImpl]stageName) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", inputFieldName='") + [CtFieldReadImpl]inputFieldName) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", field=") + [CtFieldReadImpl]field) + [CtLiteralImpl]'}';
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void validateRequiredInputs([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema> inputSchemas, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requiredInput : [CtInvocationImpl][CtFieldReadImpl]conf.getInputs()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]inputSchemas.isEmpty()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]inputSchemas.containsKey([CtVariableReadImpl]requiredInput))) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Provided input '%s' must be an input stage name.", [CtVariableReadImpl]requiredInput), [CtLiteralImpl]null).withConfigElement([CtTypeAccessImpl]JoinerConfig.REQUIRED_INPUTS, [CtVariableReadImpl]requiredInput);
            }
        }
    }
}
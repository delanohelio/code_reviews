[CompilationUnitImpl][CtCommentImpl]/* Copyright © 2018-2019 Cask Data, Inc.

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
[CtPackageDeclarationImpl]package io.cdap.plugin.format.plugin;
[CtUnresolvedImport]import io.cdap.plugin.format.FileFormat;
[CtUnresolvedImport]import org.apache.hadoop.io.NullWritable;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.validation.ValidatingOutputFormat;
[CtUnresolvedImport]import io.cdap.cdap.api.dataset.lib.KeyValue;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.InvalidPluginConfigException;
[CtUnresolvedImport]import io.cdap.plugin.common.LineageRecorder;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.FailureCollector;
[CtUnresolvedImport]import io.cdap.cdap.api.data.format.StructuredRecord;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.PluginConfig;
[CtUnresolvedImport]import io.cdap.cdap.api.data.batch.Output;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.validation.FormatContext;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.batch.BatchSinkContext;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.PipelineConfigurer;
[CtUnresolvedImport]import io.cdap.cdap.api.data.schema.Schema;
[CtUnresolvedImport]import io.cdap.plugin.common.batch.sink.SinkOutputFormatProvider;
[CtUnresolvedImport]import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.Emitter;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.batch.BatchSink;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtJavaDocImpl]/**
 * Writes data to files on Google Cloud Storage.
 *
 * @param <T>
 * 		the type of plugin config
 */
public abstract class AbstractFileSink<[CtTypeParameterImpl]T extends [CtIntersectionTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginConfig & [CtTypeReferenceImpl]io.cdap.plugin.format.plugin.FileSinkProperties> extends [CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchSink<[CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord, [CtTypeReferenceImpl]org.apache.hadoop.io.NullWritable, [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.cdap.plugin.format.plugin.AbstractFileSink.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NAME_FORMAT = [CtLiteralImpl]"format";

    [CtFieldImpl]private final [CtTypeParameterReferenceImpl]T config;

    [CtConstructorImpl]protected AbstractFileSink([CtParameterImpl][CtTypeParameterReferenceImpl]T config) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void configurePipeline([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.PipelineConfigurer pipelineConfigurer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.getStageConfigurer().getFailureCollector();
        [CtInvocationImpl][CtFieldReadImpl]config.validate([CtVariableReadImpl]collector);
        [CtInvocationImpl][CtCommentImpl]// throw exception if there were any errors while validating the config. This could happen if format or schema is
        [CtCommentImpl]// invalid
        [CtVariableReadImpl]collector.getOrThrowException();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]config.containsMacro([CtFieldReadImpl]io.cdap.plugin.format.plugin.AbstractFileSink.NAME_FORMAT)) [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]// Deploy all format plugins. This ensures that the required plugin is available when
            [CtCommentImpl]// the format macro is evaluated in prepareRun,
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.format.FileFormat f : [CtInvocationImpl][CtTypeAccessImpl]io.cdap.plugin.format.FileFormat.values()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.usePlugin([CtTypeAccessImpl]ValidatingOutputFormat.PLUGIN_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.name().toLowerCase(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.name().toLowerCase(), [CtInvocationImpl][CtFieldReadImpl]config.getRawProperties());
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.InvalidPluginConfigException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]io.cdap.plugin.format.plugin.AbstractFileSink.LOG.warn([CtLiteralImpl]"Failed to configure {}, missing properties: {}, invalid properties: {}", [CtInvocationImpl][CtVariableReadImpl]f.name(), [CtInvocationImpl][CtVariableReadImpl]e.getMissingProperties(), [CtInvocationImpl][CtVariableReadImpl]e.getInvalidProperties());
                }
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.format.FileFormat format = [CtInvocationImpl][CtFieldReadImpl]config.getFormat();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.ValidatingOutputFormat validatingOutputFormat = [CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.usePlugin([CtTypeAccessImpl]ValidatingOutputFormat.PLUGIN_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]format.name().toLowerCase(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]format.name().toLowerCase(), [CtInvocationImpl][CtFieldReadImpl]config.getRawProperties());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.FormatContext context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.FormatContext([CtVariableReadImpl]collector, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pipelineConfigurer.getStageConfigurer().getInputSchema());
        [CtInvocationImpl]validateOutputFormatProvider([CtVariableReadImpl]context, [CtVariableReadImpl]format, [CtVariableReadImpl]validatingOutputFormat);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void prepareRun([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchSinkContext context) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtVariableReadImpl]context.getFailureCollector();
        [CtInvocationImpl][CtFieldReadImpl]config.validate([CtVariableReadImpl]collector);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.format.FileFormat fileFormat = [CtInvocationImpl][CtFieldReadImpl]config.getFormat();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.ValidatingOutputFormat validatingOutputFormat;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]validatingOutputFormat = [CtInvocationImpl][CtVariableReadImpl]context.newPluginInstance([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fileFormat.name().toLowerCase());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.InvalidPluginConfigException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]io.cdap.plugin.format.plugin.AbstractFileSink.LOG.error([CtLiteralImpl]"Failed to create config for {} plugin missing properties: {} invalid properties: {}", [CtInvocationImpl][CtVariableReadImpl]fileFormat.name(), [CtInvocationImpl][CtVariableReadImpl]e.getMissingProperties(), [CtInvocationImpl][CtVariableReadImpl]e.getInvalidProperties());
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.FormatContext formatContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.FormatContext([CtVariableReadImpl]collector, [CtInvocationImpl][CtVariableReadImpl]context.getInputSchema());
        [CtInvocationImpl]validateOutputFormatProvider([CtVariableReadImpl]formatContext, [CtVariableReadImpl]fileFormat, [CtVariableReadImpl]validatingOutputFormat);
        [CtInvocationImpl][CtVariableReadImpl]collector.getOrThrowException();
        [CtLocalVariableImpl][CtCommentImpl]// record field level lineage information
        [CtCommentImpl]// needs to happen before context.addOutput(), otherwise an external dataset without schema will be created.
        [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema schema = [CtInvocationImpl][CtFieldReadImpl]config.getSchema();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]schema = [CtInvocationImpl][CtVariableReadImpl]context.getInputSchema();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.plugin.common.LineageRecorder lineageRecorder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.plugin.common.LineageRecorder([CtVariableReadImpl]context, [CtInvocationImpl][CtFieldReadImpl]config.getReferenceName());
        [CtInvocationImpl][CtVariableReadImpl]lineageRecorder.createExternalDataset([CtVariableReadImpl]schema);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]schema != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getFields() != [CtLiteralImpl]null)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getFields().isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl]recordLineage([CtVariableReadImpl]lineageRecorder, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getFields().stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]io.cdap.cdap.api.data.schema.Schema.Field::getName).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> outputProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtInvocationImpl][CtVariableReadImpl]validatingOutputFormat.getOutputFormatConfiguration());
        [CtInvocationImpl][CtVariableReadImpl]outputProperties.putAll([CtInvocationImpl]getFileSystemProperties([CtVariableReadImpl]context));
        [CtInvocationImpl][CtVariableReadImpl]outputProperties.put([CtTypeAccessImpl]FileOutputFormat.OUTDIR, [CtInvocationImpl]getOutputDir([CtInvocationImpl][CtVariableReadImpl]context.getLogicalStartTime()));
        [CtInvocationImpl][CtVariableReadImpl]context.addOutput([CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.data.batch.Output.of([CtInvocationImpl][CtFieldReadImpl]config.getReferenceName(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.plugin.common.batch.sink.SinkOutputFormatProvider([CtInvocationImpl][CtVariableReadImpl]validatingOutputFormat.getOutputFormatClassName(), [CtVariableReadImpl]outputProperties)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void transform([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord input, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.Emitter<[CtTypeReferenceImpl]io.cdap.cdap.api.dataset.lib.KeyValue<[CtTypeReferenceImpl]org.apache.hadoop.io.NullWritable, [CtTypeReferenceImpl]io.cdap.cdap.api.data.format.StructuredRecord>> emitter) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]emitter.emit([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.dataset.lib.KeyValue<>([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.io.NullWritable.get(), [CtVariableReadImpl]input));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Override this to provide any additional Configuration properties that are required by the FileSystem.
     * For example, if the FileSystem requires setting properties for credentials, those should be returned by
     * this method.
     */
    protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getFileSystemProperties([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.batch.BatchSinkContext context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Override this to specify a custom field level operation name and description.
     */
    protected [CtTypeReferenceImpl]void recordLineage([CtParameterImpl][CtTypeReferenceImpl]io.cdap.plugin.common.LineageRecorder lineageRecorder, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> outputFields) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]lineageRecorder.recordWrite([CtLiteralImpl]"Write", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Wrote to %s files.", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]config.getFormat().name().toLowerCase()), [CtVariableReadImpl]outputFields);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getOutputDir([CtParameterImpl][CtTypeReferenceImpl]long logicalStartTime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String suffix = [CtInvocationImpl][CtFieldReadImpl]config.getSuffix();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String timeSuffix = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]suffix == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]suffix.isEmpty()) ? [CtLiteralImpl]"" : [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtVariableReadImpl]suffix).format([CtVariableReadImpl]logicalStartTime);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s/%s", [CtInvocationImpl][CtFieldReadImpl]config.getPath(), [CtVariableReadImpl]timeSuffix);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void validateOutputFormatProvider([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.FormatContext context, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.plugin.format.FileFormat format, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]io.cdap.cdap.etl.api.validation.ValidatingOutputFormat validatingOutputFormat) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.FailureCollector collector = [CtInvocationImpl][CtVariableReadImpl]context.getFailureCollector();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]validatingOutputFormat == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collector.addFailure([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not find the '%s' output format plugin.", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]format.name().toLowerCase()), [CtLiteralImpl]null).withPluginNotFound([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]format.name().toLowerCase(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]format.name().toLowerCase(), [CtTypeAccessImpl]ValidatingOutputFormat.PLUGIN_TYPE);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]validatingOutputFormat.validate([CtVariableReadImpl]context);
        }
    }
}
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
[CtPackageDeclarationImpl]package io.cdap.cdap.datastreams;
[CtUnresolvedImport]import io.cdap.cdap.api.spark.SparkClientContext;
[CtUnresolvedImport]import io.cdap.cdap.etl.api.streaming.StreamingSource;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import io.cdap.cdap.internal.io.SchemaTypeAdapter;
[CtUnresolvedImport]import io.cdap.cdap.etl.common.plugin.PipelinePluginContext;
[CtUnresolvedImport]import org.apache.spark.SparkConf;
[CtUnresolvedImport]import io.cdap.cdap.api.data.schema.Schema;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import com.google.gson.GsonBuilder;
[CtUnresolvedImport]import io.cdap.cdap.etl.spark.plugin.SparkPipelinePluginContext;
[CtUnresolvedImport]import io.cdap.cdap.etl.common.Constants;
[CtUnresolvedImport]import io.cdap.cdap.etl.common.LocationAwareMDCWrapperLogger;
[CtUnresolvedImport]import com.google.gson.Gson;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.TransactionControl;
[CtUnresolvedImport]import org.apache.hadoop.security.UserGroupInformation;
[CtUnresolvedImport]import io.cdap.cdap.api.ProgramStatus;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.TransactionPolicy;
[CtUnresolvedImport]import io.cdap.cdap.etl.proto.v2.spec.StageSpec;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import io.cdap.cdap.api.spark.AbstractSpark;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import com.google.common.base.Joiner;
[CtClassImpl][CtJavaDocImpl]/**
 * CDAP Spark client that configures and launches the actual Spark program.
 */
public class DataStreamsSparkLauncher extends [CtTypeReferenceImpl]io.cdap.cdap.api.spark.AbstractSpark {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger WRAPPERLOGGER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.common.LocationAwareMDCWrapperLogger([CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.LOG, [CtFieldReadImpl]io.cdap.cdap.etl.common.Constants.EVENT_TYPE_TAG, [CtFieldReadImpl]io.cdap.cdap.etl.common.Constants.PIPELINE_LIFECYCLE_TAG_VALUE);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.gson.Gson GSON = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.GsonBuilder().registerTypeAdapter([CtFieldReadImpl]io.cdap.cdap.api.data.schema.Schema.class, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.internal.io.SchemaTypeAdapter()).create();

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"DataStreamsSparkStreaming";

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.cdap.cdap.datastreams.DataStreamsPipelineSpec pipelineSpec;

    [CtConstructorImpl]DataStreamsSparkLauncher([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.datastreams.DataStreamsPipelineSpec pipelineSpec) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pipelineSpec = [CtVariableReadImpl]pipelineSpec;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void configure() [CtBlockImpl]{
        [CtInvocationImpl]setName([CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.NAME);
        [CtInvocationImpl]setMainClass([CtFieldReadImpl]io.cdap.cdap.datastreams.SparkStreamingPipelineDriver.class);
        [CtInvocationImpl]setExecutorResources([CtInvocationImpl][CtFieldReadImpl]pipelineSpec.getResources());
        [CtInvocationImpl]setDriverResources([CtInvocationImpl][CtFieldReadImpl]pipelineSpec.getDriverResources());
        [CtInvocationImpl]setClientResources([CtInvocationImpl][CtFieldReadImpl]pipelineSpec.getClientResources());
        [CtLocalVariableImpl][CtCommentImpl]// add source, sink, transform ids to the properties. These are needed at runtime to instantiate the plugins
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]properties.put([CtTypeAccessImpl]Constants.PIPELINEID, [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.GSON.toJson([CtFieldReadImpl]pipelineSpec));
        [CtInvocationImpl]setProperties([CtVariableReadImpl]properties);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.cdap.cdap.api.annotation.TransactionPolicy([CtFieldReadImpl]io.cdap.cdap.api.annotation.TransactionControl.EXPLICIT)
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void initialize() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.spark.SparkClientContext context = [CtInvocationImpl]getContext();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String arguments = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Joiner.on([CtLiteralImpl]", ").withKeyValueSeparator([CtLiteralImpl]"=").join([CtInvocationImpl][CtVariableReadImpl]context.getRuntimeArguments());
        [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.WRAPPERLOGGER.info([CtLiteralImpl]"Pipeline '{}' is started by user '{}' with arguments {}", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getApplicationSpecification().getName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.security.UserGroupInformation.getCurrentUser().getShortUserName(), [CtVariableReadImpl]arguments);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.datastreams.DataStreamsPipelineSpec spec = [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.GSON.fromJson([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getSpecification().getProperty([CtTypeAccessImpl]Constants.PIPELINEID), [CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsPipelineSpec.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.common.plugin.PipelinePluginContext pluginContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.etl.spark.plugin.SparkPipelinePluginContext([CtVariableReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]context.getMetrics(), [CtLiteralImpl]true, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int numSources = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.proto.v2.spec.StageSpec stageSpec : [CtInvocationImpl][CtVariableReadImpl]spec.getStages()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]StreamingSource.PLUGIN_TYPE.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stageSpec.getPlugin().getType())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.etl.api.streaming.StreamingSource<[CtTypeReferenceImpl]java.lang.Object> streamingSource = [CtInvocationImpl][CtVariableReadImpl]pluginContext.newPluginInstance([CtInvocationImpl][CtVariableReadImpl]stageSpec.getName());
                [CtAssignmentImpl][CtVariableWriteImpl]numSources = [CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtInvocationImpl][CtVariableReadImpl]streamingSource.getRequiredExecutors();
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.spark.SparkConf sparkConf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.spark.SparkConf();
        [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.streaming.backpressure.enabled", [CtLiteralImpl]"true");
        [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.spark.streaming.blockInterval", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]spec.getBatchIntervalMillis() / [CtLiteralImpl]5));
        [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.maxRemoteBlockSizeFetchToMem", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE - [CtLiteralImpl]512));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> property : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spec.getProperties().entrySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtInvocationImpl][CtVariableReadImpl]property.getKey(), [CtInvocationImpl][CtVariableReadImpl]property.getValue());
        }
        [CtLocalVariableImpl][CtCommentImpl]// spark... makes you set this to at least the number of receivers (streaming sources)
        [CtCommentImpl]// because it holds one thread per receiver, or one core in distributed mode.
        [CtCommentImpl]// so... we have to set this hacky master variable based on the isUnitTest setting in the config
        [CtTypeReferenceImpl]java.lang.String extraOpts = [CtInvocationImpl][CtVariableReadImpl]spec.getExtraJavaOpts();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]extraOpts != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]extraOpts.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.driver.extraJavaOptions", [CtVariableReadImpl]extraOpts);
            [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.executor.extraJavaOptions", [CtVariableReadImpl]extraOpts);
        }
        [CtInvocationImpl][CtCommentImpl]// without this, stopping will hang on machines with few cores.
        [CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.rpc.netty.dispatcher.numThreads", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]sparkConf.setMaster([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"local[%d]", [CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtLiteralImpl]"spark.executor.instances", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtLiteralImpl]2));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]spec.isUnitTest()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sparkConf.setMaster([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"local[%d]", [CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtLiteralImpl]1));
        }
        [CtLocalVariableImpl][CtCommentImpl]// override defaults with any user provided engine configs
        [CtTypeReferenceImpl]int minExecutors = [CtBinaryOperatorImpl][CtVariableReadImpl]numSources + [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> property : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spec.getProperties().entrySet()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"spark.executor.instances".equals([CtInvocationImpl][CtVariableReadImpl]property.getKey())) [CtBlockImpl]{
                [CtTryImpl][CtCommentImpl]// don't let the user set this to something that doesn't make sense
                try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int numExecutors = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]property.getValue());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]numExecutors < [CtVariableReadImpl]minExecutors) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.LOG.warn([CtBinaryOperatorImpl][CtLiteralImpl]"Number of executors {} is less than the minimum number required to run the pipeline. " + [CtLiteralImpl]"Automatically increasing it to {}", [CtVariableReadImpl]numExecutors, [CtVariableReadImpl]minExecutors);
                        [CtAssignmentImpl][CtVariableWriteImpl]numExecutors = [CtVariableReadImpl]minExecutors;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtInvocationImpl][CtVariableReadImpl]property.getKey(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]numExecutors));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Number of spark executors was set to invalid value " + [CtInvocationImpl][CtVariableReadImpl]property.getValue(), [CtVariableReadImpl]e);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sparkConf.set([CtInvocationImpl][CtVariableReadImpl]property.getKey(), [CtInvocationImpl][CtVariableReadImpl]property.getValue());
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]context.setSparkConf([CtVariableReadImpl]sparkConf);
        [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.WRAPPERLOGGER.info([CtLiteralImpl]"Pipeline '{}' running", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getApplicationSpecification().getName());
    }

    [CtMethodImpl][CtAnnotationImpl]@io.cdap.cdap.api.annotation.TransactionPolicy([CtFieldReadImpl]io.cdap.cdap.api.annotation.TransactionControl.EXPLICIT)
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void destroy() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.destroy();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.ProgramStatus status = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getContext().getState().getStatus();
        [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.datastreams.DataStreamsSparkLauncher.WRAPPERLOGGER.info([CtLiteralImpl]"Pipeline '{}' {}", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getContext().getApplicationSpecification().getName(), [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]status == [CtFieldReadImpl]io.cdap.cdap.api.ProgramStatus.COMPLETED ? [CtLiteralImpl]"succeeded" : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.name().toLowerCase());
    }
}
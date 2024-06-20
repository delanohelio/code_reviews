[CompilationUnitImpl][CtCommentImpl]/* -
#%L
Elastic APM Java agent
%%
Copyright (C) 2018 - 2020 Elastic and contributors
%%
Licensed to Elasticsearch B.V. under one or more contributor
license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright
ownership. Elasticsearch B.V. licenses this file to you under
the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
#L%
 */
[CtPackageDeclarationImpl]package co.elastic.apm.agent.logging;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.LOG_LEVEL_KEY;
[CtUnresolvedImport]import org.apache.logging.log4j.core.LoggerContext;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.logging.log4j.Level;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.SHIP_AGENT_LOGS;
[CtUnresolvedImport]import co.elastic.apm.agent.configuration.ServiceNameUtil;
[CtUnresolvedImport]import co.elastic.apm.agent.bci.ElasticApmAgent;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.Configuration;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.DEPRECATED_LOG_LEVEL_KEY;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.DEFAULT_LOG_FILE;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FILE_KEY;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.AGENT_HOME_PLACEHOLDER;
[CtUnresolvedImport]import co.elastic.apm.agent.configuration.converter.ByteValue;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import co.elastic.apm.agent.configuration.CoreConfiguration;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.ConfigurationFactory;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FORMAT_SOUT_KEY;
[CtUnresolvedImport]import org.stagemonitor.configuration.ConfigurationOption;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
[CtUnresolvedImport]import co.elastic.logging.log4j2.EcsLayout;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
[CtUnresolvedImport]import org.stagemonitor.configuration.converter.EnumValueConverter;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FORMAT_FILE_KEY;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
[CtUnresolvedImport]import org.apache.logging.log4j.core.appender.ConsoleAppender;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
[CtUnresolvedImport]import static co.elastic.apm.agent.logging.LoggingConfiguration.DEPRECATED_LOG_FILE_KEY;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.ConfigurationSource;
[CtClassImpl]public class Log4j2ConfigurationFactory extends [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.ConfigurationFactory {
    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.core.config.plugins.util.PluginManager.addPackage([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]co.elastic.logging.log4j2.EcsLayout.class.getPackage().getName());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.core.config.plugins.util.PluginManager.addPackage([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.apache.logging.log4j.core.LoggerContext.class.getPackage().getName());
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.stagemonitor.configuration.source.ConfigurationSource> sources;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String ephemeralId;

    [CtConstructorImpl]public Log4j2ConfigurationFactory([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.stagemonitor.configuration.source.ConfigurationSource> sources, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ephemeralId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sources = [CtVariableReadImpl]sources;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ephemeralId = [CtVariableReadImpl]ephemeralId;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The ConfigurationRegistry uses and thereby initializes a logger,
     * so we can't use it here initialize the {@link ConfigurationOption}s in this class.
     */
    private static [CtTypeReferenceImpl]java.lang.String getValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.stagemonitor.configuration.source.ConfigurationSource> sources, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultValue) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.stagemonitor.configuration.source.ConfigurationSource source : [CtVariableReadImpl]sources) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String value = [CtInvocationImpl][CtVariableReadImpl]source.getValue([CtVariableReadImpl]key);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]value;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]defaultValue;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    static [CtTypeReferenceImpl]java.lang.String getActualLogFile([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String agentHome, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String logFile) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]logFile.equalsIgnoreCase([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]logFile.contains([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.AGENT_HOME_PLACEHOLDER)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]agentHome == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Could not resolve " + [CtFieldReadImpl]co.elastic.apm.agent.logging.LoggingConfiguration.AGENT_HOME_PLACEHOLDER) + [CtLiteralImpl]". Falling back to System.out.");
                [CtReturnImpl]return [CtFieldReadImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]logFile = [CtInvocationImpl][CtVariableReadImpl]logFile.replace([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.AGENT_HOME_PLACEHOLDER, [CtVariableReadImpl]agentHome);
            }
        }
        [CtAssignmentImpl][CtVariableWriteImpl]logFile = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]logFile).getAbsolutePath();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.File logDir = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]logFile).getParentFile();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]logDir.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]logDir.mkdir();
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]logDir.canWrite()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]err.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Log file " + [CtVariableReadImpl]logFile) + [CtLiteralImpl]" is not writable. Falling back to System.out.");
            [CtReturnImpl]return [CtFieldReadImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT;
        }
        [CtReturnImpl]return [CtVariableReadImpl]logFile;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtArrayTypeReferenceImpl]java.lang.String[] getSupportedTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.Configuration getConfiguration([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.LoggerContext loggerContext, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI configLocation) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getConfiguration();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.Configuration getConfiguration([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.LoggerContext loggerContext, [CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.ConfigurationSource source) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getConfiguration();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.Configuration getConfiguration() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration> builder = [CtInvocationImpl]newConfigurationBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.setStatusLevel([CtTypeAccessImpl]Level.ERROR).setConfigurationName([CtLiteralImpl]"ElasticAPM");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.Level level = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.Level.valueOf([CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.LOG_LEVEL_KEY, [CtFieldReadImpl]sources, [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.DEPRECATED_LOG_LEVEL_KEY, [CtFieldReadImpl]sources, [CtInvocationImpl][CtTypeAccessImpl]Level.INFO.toString())));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder rootLogger = [CtInvocationImpl][CtVariableReadImpl]builder.newRootLogger([CtVariableReadImpl]level);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder> appenders = [CtInvocationImpl]createAppenders([CtVariableReadImpl]builder);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder appender : [CtVariableReadImpl]appenders) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rootLogger.add([CtInvocationImpl][CtVariableReadImpl]builder.newAppenderRef([CtInvocationImpl][CtVariableReadImpl]appender.getName()));
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.add([CtVariableReadImpl]rootLogger);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder> createAppenders([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration> builder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder> appenders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String logFile = [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getActualLogFile([CtInvocationImpl][CtTypeAccessImpl]co.elastic.apm.agent.bci.ElasticApmAgent.getAgentHome(), [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FILE_KEY, [CtFieldReadImpl]sources, [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.DEPRECATED_LOG_FILE_KEY, [CtFieldReadImpl]sources, [CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.DEFAULT_LOG_FILE)));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]logFile.equals([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SYSTEM_OUT)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]appenders.add([CtInvocationImpl]createConsoleAppender([CtVariableReadImpl]builder));
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.SHIP_AGENT_LOGS, [CtFieldReadImpl]sources, [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.toString()))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File tempLog = [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getTempLogFile([CtFieldReadImpl]ephemeralId);
                [CtInvocationImpl][CtVariableReadImpl]tempLog.deleteOnExit();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File rotatedTempLog = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtBinaryOperatorImpl][CtVariableReadImpl]tempLog + [CtLiteralImpl]".1");
                [CtInvocationImpl][CtVariableReadImpl]rotatedTempLog.deleteOnExit();
                [CtInvocationImpl][CtVariableReadImpl]appenders.add([CtInvocationImpl]createFileAppender([CtVariableReadImpl]builder, [CtInvocationImpl][CtVariableReadImpl]tempLog.getAbsolutePath(), [CtInvocationImpl]createLayout([CtVariableReadImpl]builder, [CtTypeAccessImpl]LogFormat.JSON)));
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]appenders.add([CtInvocationImpl]createFileAppender([CtVariableReadImpl]builder, [CtVariableReadImpl]logFile, [CtInvocationImpl]createLayout([CtVariableReadImpl]builder, [CtInvocationImpl]getFileLogFormat())));
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder appender : [CtVariableReadImpl]appenders) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.add([CtVariableReadImpl]appender);
        }
        [CtReturnImpl]return [CtVariableReadImpl]appenders;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.io.File getTempLogFile([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ephemeralId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"java.io.tmpdir") + [CtLiteralImpl]"/elasticapm-java-") + [CtVariableReadImpl]ephemeralId) + [CtLiteralImpl]".log.json");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder createConsoleAppender([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration> builder) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newAppender([CtLiteralImpl]"Stdout", [CtLiteralImpl]"CONSOLE").addAttribute([CtLiteralImpl]"target", [CtTypeAccessImpl]ConsoleAppender.Target.SYSTEM_OUT).add([CtInvocationImpl]createLayout([CtVariableReadImpl]builder, [CtInvocationImpl]getSoutLogFormat()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder createLayout([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration> builder, [CtParameterImpl][CtTypeReferenceImpl]co.elastic.apm.agent.logging.LogFormat logFormat) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]logFormat == [CtFieldReadImpl]LogFormat.PLAIN_TEXT) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newLayout([CtLiteralImpl]"PatternLayout").addAttribute([CtLiteralImpl]"pattern", [CtLiteralImpl]"%d [%thread] %-5level %logger{36} - %msg%n");
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String serviceName = [CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]CoreConfiguration.SERVICE_NAME, [CtFieldReadImpl]sources, [CtInvocationImpl][CtTypeAccessImpl]co.elastic.apm.agent.configuration.ServiceNameUtil.getDefaultServiceName());
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newLayout([CtLiteralImpl]"EcsLayout").addAttribute([CtLiteralImpl]"eventDataset", [CtBinaryOperatorImpl][CtVariableReadImpl]serviceName + [CtLiteralImpl]".apm");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]co.elastic.apm.agent.logging.LogFormat getSoutLogFormat() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.stagemonitor.configuration.converter.EnumValueConverter<>([CtFieldReadImpl]co.elastic.apm.agent.logging.LogFormat.class).convert([CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FORMAT_SOUT_KEY, [CtFieldReadImpl]sources, [CtInvocationImpl][CtTypeAccessImpl]LogFormat.PLAIN_TEXT.toString()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]co.elastic.apm.agent.logging.LogFormat getFileLogFormat() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.stagemonitor.configuration.converter.EnumValueConverter<>([CtFieldReadImpl]co.elastic.apm.agent.logging.LogFormat.class).convert([CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtTypeAccessImpl]co.elastic.apm.agent.logging.LoggingConfiguration.LOG_FORMAT_FILE_KEY, [CtFieldReadImpl]sources, [CtInvocationImpl][CtTypeAccessImpl]LogFormat.JSON.toString()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder createFileAppender([CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder<[CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration> builder, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String logFile, [CtParameterImpl][CtTypeReferenceImpl]org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder layout) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]co.elastic.apm.agent.configuration.converter.ByteValue size = [CtInvocationImpl][CtTypeAccessImpl]co.elastic.apm.agent.configuration.converter.ByteValue.of([CtInvocationImpl]co.elastic.apm.agent.logging.Log4j2ConfigurationFactory.getValue([CtLiteralImpl]"log_file_max_size", [CtFieldReadImpl]sources, [CtTypeAccessImpl]LoggingConfiguration.DEFAULT_MAX_SIZE));
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]// Always keep exactly one history file.
        [CtCommentImpl]// This is needed to ensure that the rest of the file can be sent when its rotated.
        [CtCommentImpl]// Storing multiple history files would give the false impression that, for example,
        [CtCommentImpl]// when currently reading from apm.log2, the reading would continue from apm.log1.
        [CtCommentImpl]// This is not the case, when apm.log2 is fully read, the reading will continue from apm.log.
        [CtCommentImpl]// That is because we don't want to require the reader having to know the file name pattern of the rotated file.
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newAppender([CtLiteralImpl]"rolling", [CtLiteralImpl]"RollingFile").addAttribute([CtLiteralImpl]"fileName", [CtVariableReadImpl]logFile).addAttribute([CtLiteralImpl]"filePattern", [CtBinaryOperatorImpl][CtVariableReadImpl]logFile + [CtLiteralImpl]".%i").add([CtVariableReadImpl]layout).addComponent([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newComponent([CtLiteralImpl]"Policies").addComponent([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newComponent([CtLiteralImpl]"SizeBasedTriggeringPolicy").addAttribute([CtLiteralImpl]"size", [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]size.getBytes() + [CtLiteralImpl]"B"))).addComponent([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.newComponent([CtLiteralImpl]"DefaultRolloverStrategy").addAttribute([CtLiteralImpl]"max", [CtLiteralImpl]1));
    }
}
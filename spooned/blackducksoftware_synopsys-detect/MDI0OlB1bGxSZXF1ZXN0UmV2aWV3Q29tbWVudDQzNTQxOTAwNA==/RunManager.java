[CompilationUnitImpl][CtJavaDocImpl]/**
 * synopsys-detect
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
[CtPackageDeclarationImpl]package com.synopsys.integration.detect.lifecycle.run;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.binaryscanner.BinaryScanOptions;
[CtUnresolvedImport]import com.synopsys.integration.detectable.detectable.file.impl.SimpleFileFinder;
[CtUnresolvedImport]import com.synopsys.integration.exception.IntegrationException;
[CtUnresolvedImport]import com.synopsys.integration.detectable.detectable.result.DetectableResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.lifecycle.run.data.BlackDuckRunData;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.CodeLocationWaitData;
[CtUnresolvedImport]import com.synopsys.integration.detector.finder.DetectorFinder;
[CtUnresolvedImport]import com.synopsys.integration.detect.DetectTool;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.impl.DetectDetectableFactory;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.DetectorToolResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.phonehome.PhoneHomeManager;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.bdio.BdioResult;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.codelocation.bdioupload.UploadBatchOutput;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.bdio.BdioOptions;
[CtUnresolvedImport]import com.synopsys.integration.polaris.common.configuration.PolarisServerConfig;
[CtUnresolvedImport]import com.synopsys.integration.bdio.SimpleBdioFactory;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.service.ProjectMappingService;
[CtUnresolvedImport]import com.synopsys.integration.util.NameVersion;
[CtUnresolvedImport]import com.google.gson.Gson;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.DetectCodeLocationUnmapService;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.DetectorRuleFactory;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.project.ProjectNameVersionDecider;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.result.BlackDuckBomDetectResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.DetectorTool;
[CtUnresolvedImport]import com.synopsys.integration.detector.base.DetectorType;
[CtUnresolvedImport]import com.synopsys.integration.detectable.detectable.result.WrongOperatingSystemResult;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.synopsys.integration.configuration.config.PropertyConfiguration;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.BlackDuckPostActions;
[CtUnresolvedImport]import com.synopsys.integration.util.IntegrationEscapeUtil;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.project.ProjectNameVersionOptions;
[CtUnresolvedImport]import com.synopsys.integration.log.Slf4jIntLogger;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.result.DetectResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.lifecycle.DetectContext;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.signaturescanner.BlackDuckSignatureScannerOptions;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.api.generated.view.ProjectVersionView;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.event.Event;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.service.model.ProjectVersionWrapper;
[CtUnresolvedImport]import com.synopsys.integration.detect.exception.DetectUserFriendlyException;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.DetectProjectService;
[CtUnresolvedImport]import com.synopsys.integration.detector.evaluation.DetectorEvaluationOptions;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.codelocation.Result;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.BlackDuckPostOptions;
[CtUnresolvedImport]import com.synopsys.integration.detect.exitcode.ExitCodeType;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.signaturescanner.SignatureScannerToolResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.file.DirectoryManager;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.DetectableTool;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.synopsys.integration.detect.util.filter.DetectToolFilter;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.report.util.ReportConstants;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.DetectExecutableRunner;
[CtUnresolvedImport]import com.synopsys.integration.detector.finder.DetectorFinderOptions;
[CtUnresolvedImport]import com.synopsys.integration.detect.configuration.DetectProperties;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.bdio.BdioManager;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.event.EventSystem;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.impl.ExtractionEnvironmentProvider;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.service.BlackDuckServicesFactory;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.UniversalToolsResult;
[CtUnresolvedImport]import com.synopsys.integration.detector.rule.DetectorRuleSet;
[CtUnresolvedImport]import org.antlr.v4.runtime.misc.Nullable;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.bdio2.Bdio2Factory;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.status.Status;
[CtUnresolvedImport]import com.synopsys.integration.detectable.detectable.executable.ExecutableRunner;
[CtImportImpl]import java.nio.file.Path;
[CtUnresolvedImport]import com.synopsys.integration.detect.DetectInfo;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.binaryscanner.BinaryScanToolResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.binaryscanner.BlackDuckBinaryScannerTool;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.polaris.PolarisTool;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.DetectBdioUploadService;
[CtUnresolvedImport]import com.synopsys.integration.bdio.model.externalid.ExternalIdFactory;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.DetectableToolResult;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.DetectProjectServiceOptions;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.detector.CodeLocationConverter;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.configuration.BlackDuckServerConfig;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.status.StatusType;
[CtUnresolvedImport]import com.synopsys.integration.detect.lifecycle.run.data.ProductRunData;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.blackduck.DetectCustomFieldService;
[CtUnresolvedImport]import com.synopsys.integration.detect.configuration.DetectConfigurationFactory;
[CtUnresolvedImport]import com.synopsys.integration.blackduck.codelocation.CodeLocationCreationData;
[CtUnresolvedImport]import com.synopsys.integration.detect.tool.signaturescanner.BlackDuckSignatureScannerTool;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.codelocation.BdioCodeLocationCreator;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.bdio.AggregateMode;
[CtUnresolvedImport]import com.synopsys.integration.detect.workflow.bdio.AggregateOptions;
[CtUnresolvedImport]import com.synopsys.integration.detect.lifecycle.shutdown.ExitCodeRequest;
[CtClassImpl]public class RunManager {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtThisAccessImpl]this.getClass());

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.DetectContext detectContext;

    [CtConstructorImpl]public RunManager([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.DetectContext detectContext) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.detectContext = [CtVariableReadImpl]detectContext;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunResult run([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.data.ProductRunData productRunData) throws [CtTypeReferenceImpl]com.synopsys.integration.detect.exception.DetectUserFriendlyException, [CtTypeReferenceImpl]com.synopsys.integration.exception.IntegrationException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// TODO: Better way for run manager to get dependencies so he can be tested. (And better ways of creating his objects)
        final [CtTypeReferenceImpl]com.google.gson.Gson gson = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.google.gson.Gson.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.configuration.config.PropertyConfiguration detectConfiguration = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.configuration.config.PropertyConfiguration.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.configuration.DetectConfigurationFactory detectConfigurationFactory = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.configuration.DetectConfigurationFactory.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager directoryManager = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.event.EventSystem eventSystem = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.workflow.event.EventSystem.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager codeLocationNameManager = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.BdioCodeLocationCreator bdioCodeLocationCreator = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.workflow.codelocation.BdioCodeLocationCreator.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.DetectInfo detectInfo = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.DetectInfo.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.impl.DetectDetectableFactory detectDetectableFactory = [CtInvocationImpl][CtFieldReadImpl]detectContext.getBean([CtFieldReadImpl]com.synopsys.integration.detect.tool.detector.impl.DetectDetectableFactory.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunResult runResult = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunResult();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunOptions runOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createRunOptions();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.util.filter.DetectToolFilter detectToolFilter = [CtInvocationImpl][CtVariableReadImpl]runOptions.getDetectToolFilter();
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]productRunData.shouldUsePolarisProduct()) [CtBlockImpl]{
            [CtInvocationImpl]runPolarisProduct([CtVariableReadImpl]productRunData, [CtVariableReadImpl]detectConfiguration, [CtVariableReadImpl]directoryManager, [CtVariableReadImpl]eventSystem, [CtVariableReadImpl]detectToolFilter);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Polaris tools will not be run.");
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.UniversalToolsResult universalToolsResult = [CtInvocationImpl]runUniversalProjectTools([CtVariableReadImpl]detectConfiguration, [CtVariableReadImpl]detectConfigurationFactory, [CtVariableReadImpl]directoryManager, [CtVariableReadImpl]eventSystem, [CtVariableReadImpl]detectDetectableFactory, [CtVariableReadImpl]runResult, [CtVariableReadImpl]runOptions, [CtVariableReadImpl]detectToolFilter, [CtVariableReadImpl]codeLocationNameManager);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]productRunData.shouldUseBlackDuckProduct()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions aggregateOptions = [CtInvocationImpl]determineAggregationStrategy([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]runOptions.getAggregateName().orElse([CtLiteralImpl]null), [CtInvocationImpl][CtVariableReadImpl]runOptions.getAggregateMode(), [CtVariableReadImpl]universalToolsResult);
            [CtInvocationImpl]runBlackDuckProduct([CtVariableReadImpl]productRunData, [CtVariableReadImpl]detectConfigurationFactory, [CtVariableReadImpl]directoryManager, [CtVariableReadImpl]eventSystem, [CtVariableReadImpl]codeLocationNameManager, [CtVariableReadImpl]bdioCodeLocationCreator, [CtVariableReadImpl]detectInfo, [CtVariableReadImpl]runResult, [CtVariableReadImpl]runOptions, [CtVariableReadImpl]detectToolFilter, [CtInvocationImpl][CtVariableReadImpl]universalToolsResult.getNameVersion(), [CtVariableReadImpl]aggregateOptions);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Black Duck tools will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"All tools have finished.");
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtReturnImpl]return [CtVariableReadImpl]runResult;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions determineAggregationStrategy([CtParameterImpl][CtAnnotationImpl]@org.antlr.v4.runtime.misc.Nullable
    final [CtTypeReferenceImpl]java.lang.String aggregateName, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.AggregateMode aggregateMode, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.UniversalToolsResult universalToolsResult) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]aggregateName)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]universalToolsResult.anyFailed()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions.aggregateButSkipEmpty([CtVariableReadImpl]aggregateName, [CtVariableReadImpl]aggregateMode);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions.aggregateAndAlwaysUpload([CtVariableReadImpl]aggregateName, [CtVariableReadImpl]aggregateMode);
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions.doNotAggregate();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.UniversalToolsResult runUniversalProjectTools([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.configuration.config.PropertyConfiguration detectConfiguration, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.configuration.DetectConfigurationFactory detectConfigurationFactory, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager directoryManager, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.event.EventSystem eventSystem, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.impl.DetectDetectableFactory detectDetectableFactory, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunResult runResult, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunOptions runOptions, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.util.filter.DetectToolFilter detectToolFilter, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager codeLocationNameManager) throws [CtTypeReferenceImpl]com.synopsys.integration.detect.exception.DetectUserFriendlyException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.impl.ExtractionEnvironmentProvider extractionEnvironmentProvider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.impl.ExtractionEnvironmentProvider([CtVariableReadImpl]directoryManager);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.CodeLocationConverter codeLocationConverter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.CodeLocationConverter([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.bdio.model.externalid.ExternalIdFactory());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean anythingFailed = [CtLiteralImpl]false;
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.DOCKER)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the Docker tool.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableTool detectableTool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableTool([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]detectDetectableFactory::createDockerDetectable, [CtVariableReadImpl]extractionEnvironmentProvider, [CtVariableReadImpl]codeLocationConverter, [CtLiteralImpl]"DOCKER", [CtFieldReadImpl]com.synopsys.integration.detect.DetectTool.DOCKER, [CtVariableReadImpl]eventSystem);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableToolResult detectableToolResult = [CtInvocationImpl][CtVariableReadImpl]detectableTool.execute([CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory());
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectableToolResult.getFailedExtractableResult().isPresent()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// TODO: Remove hack when windows docker support added. This workaround allows docker to throw a user friendly exception when not-extractable due to operating system.
                final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectable.result.DetectableResult extractable = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectableToolResult.getFailedExtractableResult().get();
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.synopsys.integration.detectable.detectable.result.WrongOperatingSystemResult.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]extractable.getClass())) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.exception.DetectUserFriendlyException([CtLiteralImpl]"Docker currently requires a non-Windows OS to run. Attempting to run Docker on Windows is not currently supported.", [CtFieldReadImpl]com.synopsys.integration.detect.exitcode.ExitCodeType.FAILURE_CONFIGURATION);
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]runResult.addDetectableToolResult([CtVariableReadImpl]detectableToolResult);
            [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.CodeLocationNamesCalculated, [CtInvocationImpl]collectCodeLocationNames([CtVariableReadImpl]detectableToolResult, [CtVariableReadImpl]codeLocationNameManager, [CtVariableReadImpl]directoryManager));
            [CtAssignmentImpl][CtVariableWriteImpl]anythingFailed = [CtBinaryOperatorImpl][CtVariableReadImpl]anythingFailed || [CtInvocationImpl][CtVariableReadImpl]detectableToolResult.isFailure();
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Docker actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Docker tool will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.BAZEL)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the Bazel tool.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableTool detectableTool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableTool([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]detectDetectableFactory::createBazelDetectable, [CtVariableReadImpl]extractionEnvironmentProvider, [CtVariableReadImpl]codeLocationConverter, [CtLiteralImpl]"BAZEL", [CtFieldReadImpl]com.synopsys.integration.detect.DetectTool.BAZEL, [CtVariableReadImpl]eventSystem);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableToolResult detectableToolResult = [CtInvocationImpl][CtVariableReadImpl]detectableTool.execute([CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory());
            [CtInvocationImpl][CtVariableReadImpl]runResult.addDetectableToolResult([CtVariableReadImpl]detectableToolResult);
            [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.CodeLocationNamesCalculated, [CtInvocationImpl]collectCodeLocationNames([CtVariableReadImpl]detectableToolResult, [CtVariableReadImpl]codeLocationNameManager, [CtVariableReadImpl]directoryManager));
            [CtAssignmentImpl][CtVariableWriteImpl]anythingFailed = [CtBinaryOperatorImpl][CtVariableReadImpl]anythingFailed || [CtInvocationImpl][CtVariableReadImpl]detectableToolResult.isFailure();
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Bazel actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Bazel tool will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.DETECTOR)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the detector tool.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String projectBomTool = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectConfiguration.getValueOrEmpty([CtInvocationImpl][CtTypeAccessImpl]DetectProperties.Companion.getDETECT_PROJECT_DETECTOR()).orElse([CtLiteralImpl]null);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.synopsys.integration.detector.base.DetectorType> requiredDetectors = [CtInvocationImpl][CtVariableReadImpl]detectConfiguration.getValueOrDefault([CtInvocationImpl][CtTypeAccessImpl]DetectProperties.Companion.getDETECT_REQUIRED_DETECTOR_TYPES());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean buildless = [CtInvocationImpl][CtVariableReadImpl]detectConfiguration.getValueOrDefault([CtInvocationImpl][CtTypeAccessImpl]DetectProperties.Companion.getDETECT_BUILDLESS());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.DetectorRuleFactory detectorRuleFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.DetectorRuleFactory();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detector.rule.DetectorRuleSet detectRuleSet = [CtInvocationImpl][CtVariableReadImpl]detectorRuleFactory.createRules([CtVariableReadImpl]detectDetectableFactory, [CtVariableReadImpl]buildless);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path sourcePath = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory().toPath();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detector.finder.DetectorFinderOptions finderOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createSearchOptions([CtVariableReadImpl]sourcePath);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detector.evaluation.DetectorEvaluationOptions detectorEvaluationOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createDetectorEvaluationOptions();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.DetectorTool detectorTool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.DetectorTool([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detector.finder.DetectorFinder(), [CtVariableReadImpl]extractionEnvironmentProvider, [CtVariableReadImpl]eventSystem, [CtVariableReadImpl]codeLocationConverter);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.detector.DetectorToolResult detectorToolResult = [CtInvocationImpl][CtVariableReadImpl]detectorTool.performDetectors([CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory(), [CtVariableReadImpl]detectRuleSet, [CtVariableReadImpl]finderOptions, [CtVariableReadImpl]detectorEvaluationOptions, [CtVariableReadImpl]projectBomTool, [CtVariableReadImpl]requiredDetectors);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectorToolResult.getBomToolProjectNameVersion().ifPresent([CtLambdaImpl]([CtParameterImpl] it) -> [CtInvocationImpl][CtVariableReadImpl]runResult.addToolNameVersion([CtVariableReadImpl]DetectTool.DETECTOR, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.util.NameVersion([CtInvocationImpl][CtVariableReadImpl]it.getName(), [CtInvocationImpl][CtVariableReadImpl]it.getVersion())));
            [CtInvocationImpl][CtVariableReadImpl]runResult.addDetectCodeLocations([CtInvocationImpl][CtVariableReadImpl]detectorToolResult.getBomToolCodeLocations());
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectorToolResult.getFailedDetectorTypes().isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.ExitCode, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.shutdown.ExitCodeRequest([CtFieldReadImpl]com.synopsys.integration.detect.exitcode.ExitCodeType.FAILURE_DETECTOR, [CtLiteralImpl]"A detector failed."));
                [CtAssignmentImpl][CtVariableWriteImpl]anythingFailed = [CtLiteralImpl]true;
            }
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Detector actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Detector tool will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Completed code location tools.");
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Determining project info.");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.project.ProjectNameVersionOptions projectNameVersionOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createProjectNameVersionOptions([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory().getName());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.project.ProjectNameVersionDecider projectNameVersionDecider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.project.ProjectNameVersionDecider([CtVariableReadImpl]projectNameVersionOptions);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.util.NameVersion projectNameVersion = [CtInvocationImpl][CtVariableReadImpl]projectNameVersionDecider.decideProjectNameVersion([CtInvocationImpl][CtVariableReadImpl]runOptions.getPreferredTools(), [CtInvocationImpl][CtVariableReadImpl]runResult.getDetectToolProjectInfo());
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtBinaryOperatorImpl][CtLiteralImpl]"Project name: " + [CtInvocationImpl][CtVariableReadImpl]projectNameVersion.getName());
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtBinaryOperatorImpl][CtLiteralImpl]"Project version: " + [CtInvocationImpl][CtVariableReadImpl]projectNameVersion.getVersion());
        [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.ProjectNameVersionChosen, [CtVariableReadImpl]projectNameVersion);
        [CtIfImpl]if ([CtVariableReadImpl]anythingFailed) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.tool.UniversalToolsResult.failure([CtVariableReadImpl]projectNameVersion);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.tool.UniversalToolsResult.success([CtVariableReadImpl]projectNameVersion);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void runPolarisProduct([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.data.ProductRunData productRunData, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.configuration.config.PropertyConfiguration detectConfiguration, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager directoryManager, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.event.EventSystem eventSystem, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.util.filter.DetectToolFilter detectToolFilter) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.POLARIS)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the Polaris tool.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.polaris.common.configuration.PolarisServerConfig polarisServerConfig = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]productRunData.getPolarisRunData().getPolarisServerConfig();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectable.executable.ExecutableRunner polarisExecutableRunner = [CtInvocationImpl][CtTypeAccessImpl]com.synopsys.integration.detect.tool.detector.DetectExecutableRunner.newInfo([CtVariableReadImpl]eventSystem);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.polaris.PolarisTool polarisTool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.polaris.PolarisTool([CtVariableReadImpl]eventSystem, [CtVariableReadImpl]directoryManager, [CtVariableReadImpl]polarisExecutableRunner, [CtVariableReadImpl]detectConfiguration, [CtVariableReadImpl]polarisServerConfig);
            [CtInvocationImpl][CtVariableReadImpl]polarisTool.runPolaris([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.log.Slf4jIntLogger([CtFieldReadImpl]logger), [CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory());
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Polaris actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Polaris CLI tool will not be run.");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void runBlackDuckProduct([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.data.ProductRunData productRunData, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.configuration.DetectConfigurationFactory detectConfigurationFactory, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager directoryManager, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.event.EventSystem eventSystem, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager codeLocationNameManager, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.BdioCodeLocationCreator bdioCodeLocationCreator, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.DetectInfo detectInfo, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunResult runResult, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.RunOptions runOptions, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.util.filter.DetectToolFilter detectToolFilter, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.util.NameVersion projectNameVersion, [CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.AggregateOptions aggregateOptions) throws [CtTypeReferenceImpl]com.synopsys.integration.exception.IntegrationException, [CtTypeReferenceImpl]com.synopsys.integration.detect.exception.DetectUserFriendlyException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Black Duck tools will run.");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.lifecycle.run.data.BlackDuckRunData blackDuckRunData = [CtInvocationImpl][CtVariableReadImpl]productRunData.getBlackDuckRunData();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckRunData.getPhoneHomeManager().ifPresent([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]PhoneHomeManager::startPhoneHome);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.synopsys.integration.blackduck.service.model.ProjectVersionWrapper projectVersionWrapper = [CtLiteralImpl]null;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.blackduck.service.BlackDuckServicesFactory blackDuckServicesFactory = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckRunData.getBlackDuckServicesFactory().orElse([CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckRunData.isOnline() && [CtBinaryOperatorImpl]([CtVariableReadImpl]blackDuckServicesFactory != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Getting or creating project.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectProjectServiceOptions options = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createDetectProjectServiceOptions();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.blackduck.service.ProjectMappingService detectProjectMappingService = [CtInvocationImpl][CtVariableReadImpl]blackDuckServicesFactory.createProjectMappingService();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectCustomFieldService detectCustomFieldService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectCustomFieldService();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectProjectService detectProjectService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectProjectService([CtVariableReadImpl]blackDuckServicesFactory, [CtVariableReadImpl]options, [CtVariableReadImpl]detectProjectMappingService, [CtVariableReadImpl]detectCustomFieldService);
            [CtAssignmentImpl][CtVariableWriteImpl]projectVersionWrapper = [CtInvocationImpl][CtVariableReadImpl]detectProjectService.createOrUpdateBlackDuckProject([CtVariableReadImpl]projectNameVersion);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]projectVersionWrapper) && [CtInvocationImpl][CtVariableReadImpl]runOptions.shouldUnmapCodeLocations()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Unmapping code locations.");
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectCodeLocationUnmapService detectCodeLocationUnmapService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectCodeLocationUnmapService([CtInvocationImpl][CtVariableReadImpl]blackDuckServicesFactory.createBlackDuckService(), [CtInvocationImpl][CtVariableReadImpl]blackDuckServicesFactory.createCodeLocationService());
                [CtInvocationImpl][CtVariableReadImpl]detectCodeLocationUnmapService.unmapCodeLocations([CtInvocationImpl][CtVariableReadImpl]projectVersionWrapper.getProjectVersionView());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Will not unmap code locations: Project view was not present, or should not unmap code locations.");
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Detect is not online, and will not create the project.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Completed project and version actions.");
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Processing Detect Code Locations.");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.BdioOptions bdioOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createBdioOptions();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.BdioManager bdioManager = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.BdioManager([CtVariableReadImpl]detectInfo, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.bdio.SimpleBdioFactory(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.blackduck.bdio2.Bdio2Factory(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.util.IntegrationEscapeUtil(), [CtVariableReadImpl]codeLocationNameManager, [CtVariableReadImpl]bdioCodeLocationCreator, [CtVariableReadImpl]directoryManager, [CtVariableReadImpl]eventSystem);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.bdio.BdioResult bdioResult = [CtInvocationImpl][CtVariableReadImpl]bdioManager.createBdioFiles([CtVariableReadImpl]bdioOptions, [CtVariableReadImpl]aggregateOptions, [CtVariableReadImpl]projectNameVersion, [CtInvocationImpl][CtVariableReadImpl]runResult.getDetectCodeLocations(), [CtInvocationImpl][CtVariableReadImpl]runOptions.shouldUseBdio2());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.CodeLocationWaitData codeLocationWaitData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.CodeLocationWaitData();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bdioResult.getUploadTargets().size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Created " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bdioResult.getUploadTargets().size()) + [CtLiteralImpl]" BDIO files.");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]blackDuckServicesFactory) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectBdioUploadService detectBdioUploadService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectBdioUploadService();
                [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Uploading BDIO files.");
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.blackduck.codelocation.CodeLocationCreationData<[CtTypeReferenceImpl]com.synopsys.integration.blackduck.codelocation.bdioupload.UploadBatchOutput> uploadBatchOutputCodeLocationCreationData;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.DetectBdioUploadService.BdioUploader bdioUploader;
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]bdioResult.isBdio2()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]bdioUploader = [CtExecutableReferenceExpressionImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckServicesFactory.createBdio2UploadService()::uploadBdio;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]bdioUploader = [CtExecutableReferenceExpressionImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckServicesFactory.createBdioUploadService()::uploadBdio;
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String blackDuckUrl = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blackDuckRunData.getBlackDuckServerConfig().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]BlackDuckServerConfig::getBlackDuckUrl).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.net.URL::toExternalForm).orElse([CtLiteralImpl]"Unknown Host");
                [CtAssignmentImpl][CtVariableWriteImpl]uploadBatchOutputCodeLocationCreationData = [CtInvocationImpl][CtVariableReadImpl]detectBdioUploadService.uploadBdioFiles([CtVariableReadImpl]blackDuckUrl, [CtInvocationImpl][CtVariableReadImpl]bdioResult.getUploadTargets(), [CtVariableReadImpl]bdioUploader);
                [CtInvocationImpl][CtVariableReadImpl]codeLocationWaitData.addWaitForCreationData([CtVariableReadImpl]uploadBatchOutputCodeLocationCreationData, [CtVariableReadImpl]eventSystem);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Did not create any BDIO files.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Completed Detect Code Location processing.");
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.SIGNATURE_SCAN)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the signature scanner tool.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.signaturescanner.BlackDuckSignatureScannerOptions blackDuckSignatureScannerOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createBlackDuckSignatureScannerOptions();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.signaturescanner.BlackDuckSignatureScannerTool blackDuckSignatureScannerTool = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.signaturescanner.BlackDuckSignatureScannerTool([CtVariableReadImpl]blackDuckSignatureScannerOptions, [CtFieldReadImpl]detectContext);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.signaturescanner.SignatureScannerToolResult signatureScannerToolResult = [CtInvocationImpl][CtVariableReadImpl]blackDuckSignatureScannerTool.runScanTool([CtVariableReadImpl]blackDuckRunData, [CtVariableReadImpl]projectNameVersion, [CtInvocationImpl][CtVariableReadImpl]runResult.getDockerTar());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]signatureScannerToolResult.getResult() == [CtFieldReadImpl]com.synopsys.integration.blackduck.codelocation.Result.SUCCESS) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]signatureScannerToolResult.getCreationData().isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]codeLocationWaitData.addWaitForCreationData([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]signatureScannerToolResult.getCreationData().get(), [CtVariableReadImpl]eventSystem);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]signatureScannerToolResult.getResult() != [CtFieldReadImpl]com.synopsys.integration.blackduck.codelocation.Result.SUCCESS) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.StatusSummary, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.status.Status([CtLiteralImpl]"SIGNATURE_SCAN", [CtFieldReadImpl]com.synopsys.integration.detect.workflow.status.StatusType.FAILURE));
            }
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Signature scanner actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Signature scan tool will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.BINARY_SCAN)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will include the binary scanner tool.");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]blackDuckServicesFactory) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.binaryscanner.BinaryScanOptions binaryScanOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createBinaryScanOptions();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.binaryscanner.BlackDuckBinaryScannerTool blackDuckBinaryScanner = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.binaryscanner.BlackDuckBinaryScannerTool([CtVariableReadImpl]eventSystem, [CtVariableReadImpl]codeLocationNameManager, [CtVariableReadImpl]directoryManager, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectable.file.impl.SimpleFileFinder(), [CtVariableReadImpl]binaryScanOptions, [CtVariableReadImpl]blackDuckServicesFactory);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]blackDuckBinaryScanner.shouldRun()) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.tool.binaryscanner.BinaryScanToolResult result = [CtInvocationImpl][CtVariableReadImpl]blackDuckBinaryScanner.performBinaryScanActions([CtVariableReadImpl]projectNameVersion);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isSuccessful()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]codeLocationWaitData.addWaitForCreationData([CtInvocationImpl][CtVariableReadImpl]result.getCodeLocationCreationData(), [CtVariableReadImpl]eventSystem);
                    }
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Binary scanner actions finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Binary scan tool will not be run.");
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.info([CtTypeAccessImpl]ReportConstants.RUN_SEPARATOR);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]blackDuckServicesFactory) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Will perform Black Duck post actions.");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.BlackDuckPostOptions blackDuckPostOptions = [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.createBlackDuckPostOptions();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.BlackDuckPostActions blackDuckPostActions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.blackduck.BlackDuckPostActions([CtVariableReadImpl]blackDuckServicesFactory, [CtVariableReadImpl]eventSystem);
            [CtInvocationImpl][CtVariableReadImpl]blackDuckPostActions.perform([CtVariableReadImpl]blackDuckPostOptions, [CtVariableReadImpl]codeLocationWaitData, [CtVariableReadImpl]projectVersionWrapper, [CtInvocationImpl][CtVariableReadImpl]detectConfigurationFactory.findTimeoutInSeconds());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bdioResult.getUploadTargets().size() > [CtLiteralImpl]0) || [CtInvocationImpl][CtVariableReadImpl]detectToolFilter.shouldInclude([CtTypeAccessImpl]DetectTool.SIGNATURE_SCAN)) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> componentsLink = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]projectVersionWrapper).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ProjectVersionWrapper::getProjectVersionView).flatMap([CtLambdaImpl]([CtParameterImpl] projectVersionView) -> [CtInvocationImpl][CtVariableReadImpl]projectVersionView.getFirstLink([CtVariableReadImpl]ProjectVersionView.COMPONENTS_LINK));
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]componentsLink.isPresent()) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.result.DetectResult detectResult = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.result.BlackDuckBomDetectResult([CtInvocationImpl][CtVariableReadImpl]componentsLink.get());
                    [CtInvocationImpl][CtVariableReadImpl]eventSystem.publishEvent([CtTypeAccessImpl]Event.ResultProduced, [CtVariableReadImpl]detectResult);
                }
            }
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"Black Duck actions have finished.");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"Will not perform Black Duck post actions: Detect is not online.");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> collectCodeLocationNames([CtParameterImpl][CtTypeReferenceImpl]com.synopsys.integration.detect.tool.DetectableToolResult detectableToolResult, [CtParameterImpl][CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.codelocation.CodeLocationNameManager codeLocationNameManager, [CtParameterImpl][CtTypeReferenceImpl]com.synopsys.integration.detect.workflow.file.DirectoryManager directoryManager) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectableToolResult.getDetectToolProjectInfo().isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.synopsys.integration.util.NameVersion projectNameVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectableToolResult.getDetectToolProjectInfo().get().getSuggestedNameVersion();
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detectableToolResult.getDetectCodeLocations().stream().map([CtLambdaImpl]([CtParameterImpl] detectCodeLocation) -> [CtInvocationImpl][CtVariableReadImpl]codeLocationNameManager.createCodeLocationName([CtVariableReadImpl]detectCodeLocation, [CtInvocationImpl][CtVariableReadImpl]directoryManager.getSourceDirectory(), [CtInvocationImpl][CtVariableReadImpl]projectNameVersion.getName(), [CtInvocationImpl][CtVariableReadImpl]projectNameVersion.getVersion(), [CtLiteralImpl]null, [CtLiteralImpl]null)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
    }
}
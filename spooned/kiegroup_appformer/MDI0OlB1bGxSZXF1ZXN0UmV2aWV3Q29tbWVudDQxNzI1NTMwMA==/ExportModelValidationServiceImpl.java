[CompilationUnitImpl][CtCommentImpl]/* Copyright 202 Red Hat, Inc. and/or its affiliates.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.dashbuilder.transfer;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import org.dashbuilder.dataset.def.DataSetDef;
[CtUnresolvedImport]import org.dashbuilder.navigation.service.PerspectivePluginServices;
[CtUnresolvedImport]import org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller;
[CtUnresolvedImport]import org.jboss.errai.bus.server.annotations.Service;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import javax.enterprise.context.ApplicationScoped;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtAnnotationImpl]@org.jboss.errai.bus.server.annotations.Service
[CtAnnotationImpl]@javax.enterprise.context.ApplicationScoped
public class ExportModelValidationServiceImpl implements [CtTypeReferenceImpl]org.dashbuilder.transfer.ExportModelValidationService {
    [CtFieldImpl][CtTypeReferenceImpl]org.dashbuilder.navigation.service.PerspectivePluginServices perspectivePluginServices;

    [CtFieldImpl][CtTypeReferenceImpl]org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller marshaller;

    [CtConstructorImpl]public ExportModelValidationServiceImpl() [CtBlockImpl]{
    }

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    public ExportModelValidationServiceImpl([CtParameterImpl][CtTypeReferenceImpl]org.dashbuilder.navigation.service.PerspectivePluginServices perspectivePluginServices) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]perspectivePluginServices, [CtInvocationImpl][CtTypeAccessImpl]org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller.get());
    }

    [CtConstructorImpl]ExportModelValidationServiceImpl([CtParameterImpl][CtTypeReferenceImpl]org.dashbuilder.navigation.service.PerspectivePluginServices perspectivePluginServices, [CtParameterImpl][CtTypeReferenceImpl]org.dashbuilder.displayer.json.DisplayerSettingsJSONMarshaller marshaller) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.perspectivePluginServices = [CtVariableReadImpl]perspectivePluginServices;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.marshaller = [CtVariableReadImpl]marshaller;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> checkMissingDatasets([CtParameterImpl][CtTypeReferenceImpl]org.dashbuilder.transfer.DataTransferExportModel exportModel) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> deps = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exportModel.getPages().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] p) -> [CtVariableReadImpl]p, [CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl]datasetsUsedInPage([CtVariableReadImpl]p, [CtVariableReadImpl]exportModel)));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deps.entrySet().removeIf([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, java.util.List<java.lang.String>> e) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getValue().isEmpty());
        [CtReturnImpl]return [CtVariableReadImpl]deps;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> datasetsUsedInPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String p, [CtParameterImpl][CtTypeReferenceImpl]org.dashbuilder.transfer.DataTransferExportModel exportModel) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.dashbuilder.dataset.def.DataSetDef> exportedDefs = [CtInvocationImpl][CtVariableReadImpl]exportModel.getDatasetDefinitions();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]allDataSetsFromPage([CtVariableReadImpl]p).filter([CtLambdaImpl]([CtParameterImpl]java.lang.String uuid) -> [CtInvocationImpl]isDataSetMissing([CtVariableReadImpl]uuid, [CtVariableReadImpl]exportedDefs)).distinct().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isDataSetMissing([CtParameterImpl][CtTypeReferenceImpl]java.lang.String uuid, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.dashbuilder.dataset.def.DataSetDef> exportedDefs) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exportedDefs.isEmpty() || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exportedDefs.stream().noneMatch([CtLambdaImpl]([CtParameterImpl] ds) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getUUID().equals([CtVariableReadImpl]uuid));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.lang.String> allDataSetsFromPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String p) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]perspectivePluginServices.getLayoutTemplate([CtVariableReadImpl]p).getRows().stream().flatMap([CtLambdaImpl]([CtParameterImpl] r) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getLayoutColumns().stream()).flatMap([CtLambdaImpl]([CtParameterImpl] cl) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cl.getLayoutComponents().stream()).map([CtLambdaImpl]([CtParameterImpl] lc) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lc.getProperties().get([CtLiteralImpl]"json")).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]marshaller::fromJsonString).map([CtLambdaImpl]([CtParameterImpl] settings) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]settings.getDataSetLookup().getDataSetUUID());
    }
}
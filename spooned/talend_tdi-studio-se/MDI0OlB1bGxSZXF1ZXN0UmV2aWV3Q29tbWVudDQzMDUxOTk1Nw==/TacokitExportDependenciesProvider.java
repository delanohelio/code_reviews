[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2006-2020 Talend Inc. - www.talend.com

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package org.talend.sdk.component.studio.provider;
[CtUnresolvedImport]import org.talend.sdk.component.studio.ComponentModel;
[CtUnresolvedImport]import org.talend.core.runtime.repository.build.BuildExportManager.EXPORT_TYPE;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import static org.talend.sdk.component.studio.util.TaCoKitUtil.getTaCoKitComponents;
[CtUnresolvedImport]import static org.talend.sdk.component.studio.util.TaCoKitUtil.getJobComponents;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.talend.core.runtime.repository.build.IBuildExportDependenciesProvider;
[CtImportImpl]import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
[CtImportImpl]import java.io.BufferedInputStream;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import org.talend.commons.exception.ExceptionHandler;
[CtImportImpl]import java.nio.file.Paths;
[CtUnresolvedImport]import static org.talend.sdk.component.studio.util.TaCoKitUtil.findM2Path;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.talend.commons.utils.system.EnvironmentUtils;
[CtUnresolvedImport]import org.talend.core.runtime.repository.build.BuildExportManager;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.util.jar.JarEntry;
[CtImportImpl]import java.util.jar.JarFile;
[CtUnresolvedImport]import org.talend.core.model.properties.Item;
[CtImportImpl]import java.io.InputStreamReader;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.talend.repository.documentation.ExportFileResource;
[CtUnresolvedImport]import static org.talend.sdk.component.studio.util.TaCoKitUtil.gavToMvnPath;
[CtUnresolvedImport]import org.talend.core.CorePlugin;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import org.talend.sdk.component.studio.util.TaCoKitConst;
[CtImportImpl]import java.io.BufferedReader;
[CtUnresolvedImport]import org.talend.core.runtime.process.ITalendProcessJavaProject;
[CtUnresolvedImport]import static org.talend.sdk.component.studio.util.TaCoKitUtil.hasTaCoKitComponents;
[CtImportImpl]import java.util.Map;
[CtClassImpl]public class TacokitExportDependenciesProvider implements [CtTypeReferenceImpl]org.talend.core.runtime.repository.build.IBuildExportDependenciesProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.class.getName());

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called when exporting a job, this is up to the implementor to check that the item should export the dependencies.
     *
     * We use this interface as a hook to feed MAVEN-INF repository.
     *
     * Side-note: this method is called every time we have an ESB job export as Microservice export also calls it.
     *
     * @param exportFileResource
     * 		resources to export, mainly needed in OSGi.
     * @param item
     * 		<code>ProcessItem</code>
     * @see org.talend.core.runtime.repository.build.IBuildExportDependenciesProvider
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void exportDependencies([CtParameterImpl]final [CtTypeReferenceImpl]org.talend.repository.documentation.ExportFileResource exportFileResource, [CtParameterImpl]final [CtTypeReferenceImpl]org.talend.core.model.properties.Item item) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.talend.core.runtime.repository.build.BuildExportManager.getInstance().getCurrentExportType().equals([CtTypeAccessImpl]EXPORT_TYPE.OSGI)) || [CtUnaryOperatorImpl](![CtInvocationImpl]TaCoKitUtil.hasTaCoKitComponents([CtInvocationImpl]TaCoKitUtil.getJobComponents([CtVariableReadImpl]item)))) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.debug([CtLiteralImpl]"[exportDependencies] Searching for TaCoKit components...");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> plugins = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]TaCoKitUtil.getTaCoKitComponents([CtInvocationImpl]TaCoKitUtil.getJobComponents([CtVariableReadImpl]item)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ComponentModel::getId).forEach([CtLambdaImpl]([CtParameterImpl] id) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]plugins.put([CtInvocationImpl][CtVariableReadImpl]id.getPlugin(), [CtInvocationImpl]gavToMvnPath([CtInvocationImpl][CtVariableReadImpl]id.getPluginLocation()));
        });
        [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.info([CtLiteralImpl]"[exportDependencies] Found {} TaCoKit components.", [CtInvocationImpl][CtVariableReadImpl]plugins.size());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.talend.core.runtime.process.ITalendProcessJavaProject project = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.talend.core.CorePlugin.getDefault().getRunProcessService().getTalendJobJavaProject([CtInvocationImpl][CtVariableReadImpl]item.getProperty());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String output = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]org.talend.commons.utils.system.EnvironmentUtils.isWindowsSystem()) ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getResourcesFolder().getLocationURI().getPath().substring([CtLiteralImpl]1) : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]project.getResourcesFolder().getLocationURI().getPath();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path m2 = [CtInvocationImpl]TaCoKitUtil.findM2Path();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path resM2 = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtVariableReadImpl]output, [CtTypeAccessImpl]TaCoKitConst.MAVEN_INF, [CtLiteralImpl]"repository");
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path coordinates = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtVariableReadImpl]output, [CtTypeAccessImpl]TaCoKitConst.TALEND_INF, [CtLiteralImpl]"plugins.properties");
            [CtInvocationImpl][CtVariableReadImpl]exportFileResource.addResource([CtLiteralImpl]"TALEND-INF/", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]coordinates.toUri().toURL());
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]resM2);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]coordinates)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// we assume gav already translated
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.readAllLines([CtVariableReadImpl]coordinates).stream().filter([CtLambdaImpl]([CtParameterImpl]java.lang.String line) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]line.matches([CtLiteralImpl]"^\\s?#")).filter([CtLambdaImpl]([CtParameterImpl]java.lang.String line) -> [CtInvocationImpl][CtVariableReadImpl]line.matches([CtLiteralImpl]".*=.*")).map([CtLambdaImpl]([CtParameterImpl]java.lang.String line) -> [CtInvocationImpl][CtVariableReadImpl]line.split([CtLiteralImpl]"=")).forEach([CtLambdaImpl]([CtParameterImpl] line) -> [CtInvocationImpl][CtVariableReadImpl]plugins.putIfAbsent([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]line[[CtLiteralImpl]0].trim(), [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]line[[CtLiteralImpl]1].trim()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtInvocationImpl][CtVariableReadImpl]coordinates.getParent());
            }
            [CtInvocationImpl][CtCommentImpl]// Feed MAVEN-INF repository
            [CtVariableReadImpl]plugins.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String plugin,[CtParameterImpl]java.lang.String location) -> [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.info([CtLiteralImpl]"[exportDependencies] Adding {} to MAVEN-INF.", [CtVariableReadImpl]plugin);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path src = [CtInvocationImpl][CtVariableReadImpl]m2.resolve([CtVariableReadImpl]location);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.nio.file.Path dst = [CtInvocationImpl][CtVariableReadImpl]resM2.resolve([CtVariableReadImpl]location);
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// First, cp component jar
                    copyJar([CtVariableReadImpl]src, [CtVariableReadImpl]dst, [CtVariableReadImpl]exportFileResource);
                    [CtLocalVariableImpl][CtCommentImpl]// then, find deps for current plugin : This is definitely needed for the microservice case and may
                    [CtCommentImpl]// help to avoid classes collisions as it may happen with azure-dls-gen2 for instance!
                    [CtTypeReferenceImpl]java.util.jar.JarFile jar = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.jar.JarFile([CtInvocationImpl][CtVariableReadImpl]src.toFile());
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.jar.JarEntry entry = [CtInvocationImpl][CtVariableReadImpl]jar.getJarEntry([CtLiteralImpl]"TALEND-INF/dependencies.txt");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.BufferedReader reader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtInvocationImpl][CtVariableReadImpl]jar.getInputStream([CtVariableReadImpl]entry)));
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]reader.lines().filter([CtLambdaImpl]([CtParameterImpl]java.lang.String l) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]l.endsWith([CtLiteralImpl]":test")).map([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::translateGavToJar).peek([CtLambdaImpl]([CtParameterImpl]java.lang.String dep) -> [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.debug([CtLiteralImpl]"[exportDependencies] Copying dependency {} to MAVEN-INF.", [CtVariableReadImpl]dep)).forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String dep) -> [CtInvocationImpl]copyJar([CtInvocationImpl][CtVariableReadImpl]m2.resolve([CtVariableReadImpl]dep), [CtInvocationImpl][CtVariableReadImpl]resM2.resolve([CtVariableReadImpl]dep), [CtVariableReadImpl]exportFileResource));
                    [CtInvocationImpl][CtVariableReadImpl]reader.close();
                    [CtInvocationImpl][CtVariableReadImpl]jar.close();
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]java.lang.IllegalStateException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.error([CtLiteralImpl]"[exportDependencies] Error occurred during artifact copy:", [CtVariableReadImpl]e);
                    [CtInvocationImpl][CtTypeAccessImpl]org.talend.commons.exception.ExceptionHandler.process([CtVariableReadImpl]e);
                }
            });
            [CtLocalVariableImpl][CtCommentImpl]// Finalize by writing our plugins.properties
            final [CtTypeReferenceImpl]java.lang.StringBuffer coord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer([CtLiteralImpl]"# component-runtime components coordinates:\n");
            [CtInvocationImpl][CtVariableReadImpl]plugins.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String k,[CtParameterImpl]java.lang.String v) -> [CtInvocationImpl][CtVariableReadImpl]coord.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s = %s\n", [CtVariableReadImpl]k, [CtVariableReadImpl]v)));
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.copy([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedInputStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]coord.toString().getBytes())), [CtVariableReadImpl]coordinates, [CtFieldReadImpl]java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            [CtInvocationImpl][CtCommentImpl]// For microservice m2 extraction, not necessary for real OSGi bundle
            [CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"talend.component.manager.components.present", [CtLiteralImpl]"true");
            [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.info([CtLiteralImpl]"[exportDependencies] Finished MAVEN-INF feeding.");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.error([CtLiteralImpl]"[exportDependencies] Error occurred:", [CtVariableReadImpl]e);
            [CtInvocationImpl][CtTypeAccessImpl]org.talend.commons.exception.ExceptionHandler.process([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Class wrapper for {@link @TaCoKitDependencyUtil#gavToJar(String)}
     *
     * @param gav
     * 		see {@link @TaCoKitDependencyUtil#gavToJar(String)}
     * @return a translated maven path
     */
    public [CtTypeReferenceImpl]java.lang.String translateGavToJar([CtParameterImpl][CtTypeReferenceImpl]java.lang.String gav) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]TaCoKitUtil.gavToMvnPath([CtVariableReadImpl]gav);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Copy a jar and update export resources.
     *
     * @param source
     * 		m2 jar
     * @param destination
     * 		MAVEN-INF destination jar
     * @param exportFileResource
     * 		file resources for job export build
     */
    private [CtTypeReferenceImpl]void copyJar([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path source, [CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path destination, [CtParameterImpl][CtTypeReferenceImpl]org.talend.repository.documentation.ExportFileResource exportFileResource) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtInvocationImpl][CtVariableReadImpl]destination.getParent())) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtInvocationImpl][CtVariableReadImpl]destination.getParent());
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]destination)) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.copy([CtVariableReadImpl]source, [CtVariableReadImpl]destination);
            }
            [CtInvocationImpl][CtVariableReadImpl]exportFileResource.addResource([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]destination.getParent().toString().substring([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]destination.getParent().toString().indexOf([CtLiteralImpl]"MAVEN-INF")), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]destination.toUri().toURL());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.talend.sdk.component.studio.provider.TacokitExportDependenciesProvider.LOG.error([CtLiteralImpl]"[copyJar] Something went wrong during jar copying...", [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtVariableReadImpl]e);
        }
    }
}
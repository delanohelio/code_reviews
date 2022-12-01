[CompilationUnitImpl][CtCommentImpl]/* Copyright © 2015-2019 Cask Data, Inc.

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
[CtPackageDeclarationImpl]package io.cdap.cdap.internal.app.runtime.artifact;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.jar.Attributes;
[CtUnresolvedImport]import io.cdap.cdap.api.artifact.ArtifactClasses;
[CtUnresolvedImport]import org.objectweb.asm.Type;
[CtUnresolvedImport]import io.cdap.cdap.api.artifact.CloseableClassLoader;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.objectweb.asm.ClassVisitor;
[CtImportImpl]import java.io.EOFException;
[CtUnresolvedImport]import io.cdap.cdap.api.app.Application;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.google.common.base.Function;
[CtUnresolvedImport]import io.cdap.cdap.api.data.schema.UnsupportedTypeException;
[CtUnresolvedImport]import io.cdap.cdap.api.artifact.ApplicationClass;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.PluginClass;
[CtUnresolvedImport]import io.cdap.cdap.common.id.Id;
[CtUnresolvedImport]import org.objectweb.asm.ClassReader;
[CtUnresolvedImport]import com.google.common.base.Predicate;
[CtUnresolvedImport]import io.cdap.cdap.common.conf.Constants;
[CtUnresolvedImport]import io.cdap.cdap.common.InvalidArtifactException;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import io.cdap.cdap.common.utils.DirUtils;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.objectweb.asm.AnnotationVisitor;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.apache.twill.filesystem.Location;
[CtUnresolvedImport]import io.cdap.cdap.app.program.ManifestFields;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Description;
[CtUnresolvedImport]import com.google.common.reflect.TypeToken;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import com.google.common.collect.Iterators;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.PluginConfig;
[CtImportImpl]import java.lang.reflect.Field;
[CtUnresolvedImport]import com.google.common.base.Strings;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.lang.reflect.Modifier;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Macro;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Name;
[CtUnresolvedImport]import io.cdap.cdap.common.io.Locations;
[CtUnresolvedImport]import io.cdap.cdap.internal.io.ReflectionSchemaGenerator;
[CtImportImpl]import java.util.Iterator;
[CtImportImpl]import java.nio.file.Paths;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import org.objectweb.asm.Opcodes;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.util.jar.JarFile;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import com.google.common.collect.AbstractIterator;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.google.common.base.Throwables;
[CtUnresolvedImport]import io.cdap.cdap.api.Config;
[CtImportImpl]import java.util.zip.ZipException;
[CtUnresolvedImport]import io.cdap.cdap.api.annotation.Plugin;
[CtImportImpl]import java.util.Enumeration;
[CtUnresolvedImport]import com.google.common.collect.Maps;
[CtUnresolvedImport]import io.cdap.cdap.api.data.schema.Schema;
[CtUnresolvedImport]import com.google.common.primitives.Primitives;
[CtImportImpl]import java.lang.annotation.Annotation;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.Requirements;
[CtUnresolvedImport]import io.cdap.cdap.common.conf.CConfiguration;
[CtUnresolvedImport]import io.cdap.cdap.internal.app.runtime.plugin.PluginInstantiator;
[CtImportImpl]import java.util.jar.Manifest;
[CtUnresolvedImport]import io.cdap.cdap.common.lang.jar.BundleJarUtil;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import io.cdap.cdap.api.plugin.PluginPropertyField;
[CtUnresolvedImport]import io.cdap.cdap.api.artifact.ArtifactId;
[CtClassImpl][CtJavaDocImpl]/**
 * Inspects a jar file to determine metadata about the artifact.
 */
final class ArtifactInspector {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactInspector.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.cdap.cdap.common.conf.CConfiguration cConf;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactClassLoaderFactory artifactClassLoaderFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.cdap.cdap.internal.io.ReflectionSchemaGenerator schemaGenerator;

    [CtConstructorImpl]ArtifactInspector([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.common.conf.CConfiguration cConf, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactClassLoaderFactory artifactClassLoaderFactory) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cConf = [CtVariableReadImpl]cConf;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.artifactClassLoaderFactory = [CtVariableReadImpl]artifactClassLoaderFactory;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.schemaGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.internal.io.ReflectionSchemaGenerator([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Inspect the given artifact to determine the classes contained in the artifact.
     *
     * @param artifactId
     * 		the id of the artifact to inspect
     * @param artifactFile
     * 		the artifact file
     * @param parentClassLoader
     * 		the parent classloader to use when inspecting plugins contained in the artifact.
     * 		For example, a ProgramClassLoader created from the artifact the input artifact extends
     * @param additionalPlugins
     * 		Additional plugin classes
     * @return metadata about the classes contained in the artifact
     * @throws IOException
     * 		if there was an exception opening the jar file
     * @throws InvalidArtifactException
     * 		if the artifact is invalid. For example, if the application main class is not
     * 		actually an Application.
     */
    [CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactClasses inspectArtifact([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.common.id.Id.Artifact artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.io.File artifactFile, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.ClassLoader parentClassLoader, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass> additionalPlugins) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path tmpDir = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtInvocationImpl][CtFieldReadImpl]cConf.get([CtTypeAccessImpl]Constants.CFG_LOCAL_DATA_DIR), [CtInvocationImpl][CtFieldReadImpl]cConf.get([CtTypeAccessImpl]Constants.AppFabric.TEMP_DIR)).toAbsolutePath();
        [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]tmpDir);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.twill.filesystem.Location artifactLocation = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.common.io.Locations.toLocation([CtVariableReadImpl]artifactFile);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path stageDir = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createTempDirectory([CtVariableReadImpl]tmpDir, [CtInvocationImpl][CtVariableReadImpl]artifactFile.getName());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File unpackedDir = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.common.lang.jar.BundleJarUtil.unJar([CtVariableReadImpl]artifactLocation, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createTempDirectory([CtVariableReadImpl]stageDir, [CtLiteralImpl]"unpacked-").toFile());
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.CloseableClassLoader artifactClassLoader = [CtInvocationImpl][CtFieldReadImpl]artifactClassLoaderFactory.createClassLoader([CtVariableReadImpl]unpackedDir);[CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.internal.app.runtime.plugin.PluginInstantiator pluginInstantiator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.internal.app.runtime.plugin.PluginInstantiator([CtFieldReadImpl]cConf, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]parentClassLoader == [CtLiteralImpl]null ? [CtVariableReadImpl]artifactClassLoader : [CtVariableReadImpl]parentClassLoader, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createTempDirectory([CtVariableReadImpl]stageDir, [CtLiteralImpl]"plugins-").toFile(), [CtLiteralImpl]false)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]pluginInstantiator.addArtifact([CtVariableReadImpl]artifactLocation, [CtInvocationImpl][CtVariableReadImpl]artifactId.toArtifactId());
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactClasses.Builder builder = [CtInvocationImpl]inspectApplications([CtVariableReadImpl]artifactId, [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.api.artifact.ArtifactClasses.builder(), [CtVariableReadImpl]artifactLocation, [CtVariableReadImpl]artifactClassLoader);
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]inspectPlugins([CtVariableReadImpl]builder, [CtVariableReadImpl]artifactFile, [CtInvocationImpl][CtVariableReadImpl]artifactId.toArtifactId(), [CtVariableReadImpl]pluginInstantiator, [CtVariableReadImpl]additionalPlugins).build();
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.EOFException | [CtTypeReferenceImpl]java.util.zip.ZipException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Artifact " + [CtVariableReadImpl]artifactId) + [CtLiteralImpl]" is not a valid zip file.", [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.common.utils.DirUtils.deleteDirectoryContents([CtInvocationImpl][CtVariableReadImpl]stageDir.toFile());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactInspector.LOG.warn([CtLiteralImpl]"Exception raised while deleting directory {}", [CtVariableReadImpl]stageDir, [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]ArtifactClasses.Builder inspectApplications([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.common.id.Id.Artifact artifactId, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactClasses.Builder builder, [CtParameterImpl][CtTypeReferenceImpl]org.apache.twill.filesystem.Location artifactLocation, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader artifactClassLoader) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// right now we force users to include the application main class as an attribute in their manifest,
        [CtCommentImpl]// which forces them to have a single application class.
        [CtCommentImpl]// in the future, we may want to let users do this or maybe specify a list of classes or
        [CtCommentImpl]// a package that will be searched for applications, to allow multiple applications in a single artifact.
        [CtTypeReferenceImpl]java.lang.String mainClassName;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.jar.Manifest manifest = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.common.lang.jar.BundleJarUtil.getManifest([CtVariableReadImpl]artifactLocation);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]manifest == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]builder;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.jar.Attributes manifestAttributes = [CtInvocationImpl][CtVariableReadImpl]manifest.getMainAttributes();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]manifestAttributes == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]builder;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]mainClassName = [CtInvocationImpl][CtVariableReadImpl]manifestAttributes.getValue([CtTypeAccessImpl]ManifestFields.MAIN_CLASS);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.zip.ZipException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Couldn't unzip artifact %s, please check it is a valid jar file.", [CtVariableReadImpl]artifactId), [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mainClassName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]builder;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object appMain = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]artifactClassLoader.loadClass([CtVariableReadImpl]mainClassName).newInstance();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]appMain instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.cdap.cdap.api.app.Application)) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// we don't want to error here, just don't record an application class.
                [CtCommentImpl]// possible for 3rd party plugin artifacts to have the main class set
                return [CtVariableReadImpl]builder;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.app.Application app = [CtVariableReadImpl](([CtTypeReferenceImpl]io.cdap.cdap.api.app.Application) (appMain));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Type configType;
            [CtTryImpl][CtCommentImpl]// if the user parameterized their application, like 'xyz extends Application<T>',
            [CtCommentImpl]// we can deserialize the config into that object. Otherwise it'll just be a Config
            try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]configType = [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.internal.app.runtime.artifact.Artifacts.getConfigType([CtInvocationImpl][CtVariableReadImpl]app.getClass());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Could not resolve config type for Application class %s in artifact %s. " + [CtLiteralImpl]"The type must extend Config and cannot be parameterized.", [CtVariableReadImpl]mainClassName, [CtVariableReadImpl]artifactId));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.Schema configSchema = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]configType == [CtFieldReadImpl]io.cdap.cdap.api.Config.class) ? [CtLiteralImpl]null : [CtInvocationImpl][CtFieldReadImpl]schemaGenerator.generate([CtVariableReadImpl]configType);
            [CtInvocationImpl][CtVariableReadImpl]builder.addApp([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ApplicationClass([CtVariableReadImpl]mainClassName, [CtLiteralImpl]"", [CtVariableReadImpl]configSchema, [CtInvocationImpl]getRequirementsAnnotation([CtInvocationImpl][CtVariableReadImpl]app.getClass())));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not find Application main class %s in artifact %s.", [CtVariableReadImpl]mainClassName, [CtVariableReadImpl]artifactId));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtLiteralImpl]"Config for Application %s in artifact %s has an unsupported schema. " + [CtLiteralImpl]"The type must extend Config and cannot be parameterized.", [CtVariableReadImpl]mainClassName, [CtVariableReadImpl]artifactId));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.InstantiationException | [CtTypeReferenceImpl]java.lang.IllegalAccessException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not instantiate Application class %s in artifact %s.", [CtVariableReadImpl]mainClassName, [CtVariableReadImpl]artifactId), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Inspects the plugin file and extracts plugin classes information.
     */
    private [CtTypeReferenceImpl]ArtifactClasses.Builder inspectPlugins([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactClasses.Builder builder, [CtParameterImpl][CtTypeReferenceImpl]java.io.File artifactFile, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactId artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.internal.app.runtime.plugin.PluginInstantiator pluginInstantiator, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass> additionalPlugins) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.ClassLoader pluginClassLoader = [CtInvocationImpl][CtVariableReadImpl]pluginInstantiator.getArtifactClassLoader([CtVariableReadImpl]artifactId);
        [CtInvocationImpl]inspectAdditionalPlugins([CtVariableReadImpl]artifactId, [CtVariableReadImpl]additionalPlugins, [CtVariableReadImpl]pluginClassLoader);
        [CtLocalVariableImpl][CtCommentImpl]// See if there are export packages. Plugins should be in those packages
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> exportPackages = [CtInvocationImpl]getExportPackages([CtVariableReadImpl]artifactFile);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]exportPackages.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]builder;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls : [CtInvocationImpl]getPluginClasses([CtVariableReadImpl]exportPackages, [CtVariableReadImpl]pluginClassLoader)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Plugin pluginAnnotation = [CtInvocationImpl][CtVariableReadImpl]cls.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Plugin.class);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pluginAnnotation == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField> pluginProperties = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configField = [CtInvocationImpl]getProperties([CtInvocationImpl][CtTypeAccessImpl]com.google.common.reflect.TypeToken.of([CtVariableReadImpl]cls), [CtVariableReadImpl]pluginProperties);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass pluginClass = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass([CtInvocationImpl][CtVariableReadImpl]pluginAnnotation.type(), [CtInvocationImpl]getPluginName([CtVariableReadImpl]cls), [CtInvocationImpl]getPluginDescription([CtVariableReadImpl]cls), [CtInvocationImpl][CtVariableReadImpl]cls.getName(), [CtVariableReadImpl]configField, [CtVariableReadImpl]pluginProperties, [CtInvocationImpl]getRequirementsAnnotation([CtVariableReadImpl]cls));
                    [CtInvocationImpl][CtVariableReadImpl]builder.addPlugin([CtVariableReadImpl]pluginClass);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactInspector.LOG.warn([CtLiteralImpl]"Plugin configuration type not supported. Plugin ignored. {}", [CtVariableReadImpl]cls, [CtVariableReadImpl]e);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Class could not be found while inspecting artifact for plugins. " + [CtLiteralImpl]"Please check dependencies are available, and that the correct parent artifact was specified. ") + [CtLiteralImpl]"Error class: %s, message: %s.", [CtInvocationImpl][CtVariableReadImpl]t.getClass(), [CtInvocationImpl][CtVariableReadImpl]t.getMessage()), [CtVariableReadImpl]t);
        }
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void inspectAdditionalPlugins([CtParameterImpl][CtTypeReferenceImpl]io.cdap.cdap.api.artifact.ArtifactId artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass> additionalPlugins, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader pluginClassLoader) throws [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]additionalPlugins != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginClass pluginClass : [CtVariableReadImpl]additionalPlugins) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Make sure additional plugin classes can be loaded. This is to ensure that plugin artifacts without
                    [CtCommentImpl]// plugin classes are not deployed.
                    [CtVariableReadImpl]pluginClassLoader.loadClass([CtInvocationImpl][CtVariableReadImpl]pluginClass.getClassName());
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.common.InvalidArtifactException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Artifact %s with version %s and scope %s does not have class %s.", [CtInvocationImpl][CtVariableReadImpl]artifactId.getName(), [CtInvocationImpl][CtVariableReadImpl]artifactId.getVersion(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]artifactId.getScope().name(), [CtInvocationImpl][CtVariableReadImpl]pluginClass.getClassName()), [CtVariableReadImpl]e);
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the set of package names that are declared in "Export-Package" in the jar file Manifest.
     */
    private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getExportPackages([CtParameterImpl][CtTypeReferenceImpl]java.io.File file) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.jar.JarFile jarFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.jar.JarFile([CtVariableReadImpl]file)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.app.program.ManifestFields.getExportPackages([CtInvocationImpl][CtVariableReadImpl]jarFile.getManifest());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an {@link Iterable} of class name that are under the given list of package names that are loadable
     * through the plugin ClassLoader.
     */
    private [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> getPluginClasses([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> packages, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.ClassLoader pluginClassLoader) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> iterator() [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> packageIterator = [CtInvocationImpl][CtVariableReadImpl]packages.iterator();
                [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.collect.AbstractIterator<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>>()[CtClassImpl] {
                    [CtFieldImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> classIterator = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.<[CtTypeReferenceImpl]java.lang.String>of().iterator();

                    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String currentPackage;

                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    protected [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> computeNext() [CtBlockImpl]{
                        [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]classIterator.hasNext()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]packageIterator.hasNext()) [CtBlockImpl]{
                                [CtReturnImpl]return [CtInvocationImpl]endOfData();
                            }
                            [CtAssignmentImpl][CtFieldWriteImpl]currentPackage = [CtInvocationImpl][CtVariableReadImpl]packageIterator.next();
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtLocalVariableImpl][CtCommentImpl]// Gets all package resource URL for the given package
                                [CtTypeReferenceImpl]java.lang.String resourceName = [CtInvocationImpl][CtFieldReadImpl]currentPackage.replace([CtLiteralImpl]'.', [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separatorChar);
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration<[CtTypeReferenceImpl]java.net.URL> resources = [CtInvocationImpl][CtVariableReadImpl]pluginClassLoader.getResources([CtVariableReadImpl]resourceName);
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String>> iterators = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                                [CtWhileImpl][CtCommentImpl]// Go though all available resources and collect all class names that are plugin classes.
                                while ([CtInvocationImpl][CtVariableReadImpl]resources.hasMoreElements()) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL packageResource = [CtInvocationImpl][CtVariableReadImpl]resources.nextElement();
                                    [CtIfImpl][CtCommentImpl]// Only inspect classes in the top level jar file for Plugins.
                                    [CtCommentImpl]// The jar manifest may have packages in Export-Package that are loadable from the bundled jar files,
                                    [CtCommentImpl]// which is for classloading purpose. Those classes won't be inspected for plugin classes.
                                    [CtCommentImpl]// There should be exactly one of resource that match, because it maps to a directory on the FS.
                                    if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]packageResource.getProtocol().equals([CtLiteralImpl]"file")) [CtBlockImpl]{
                                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> classFiles = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.cdap.cdap.common.utils.DirUtils.list([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtVariableReadImpl]packageResource.toURI()), [CtLiteralImpl]"class").iterator();
                                        [CtInvocationImpl][CtCommentImpl]// Transform class file into class name and filter by @Plugin class only
                                        [CtVariableReadImpl]iterators.add([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterators.filter([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterators.transform([CtVariableReadImpl]classFiles, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]java.lang.String apply([CtParameterImpl][CtTypeReferenceImpl]java.lang.String input) [CtBlockImpl]{
                                                [CtReturnImpl]return [CtInvocationImpl]getClassName([CtFieldReadImpl]currentPackage, [CtVariableReadImpl]input);
                                            }
                                        }), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Predicate<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]boolean apply([CtParameterImpl][CtTypeReferenceImpl]java.lang.String className) [CtBlockImpl]{
                                                [CtReturnImpl]return [CtInvocationImpl]isPlugin([CtVariableReadImpl]className, [CtVariableReadImpl]pluginClassLoader);
                                            }
                                        }));
                                    }
                                } 
                                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]iterators.isEmpty()) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtFieldWriteImpl]classIterator = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterators.concat([CtInvocationImpl][CtVariableReadImpl]iterators.iterator());
                                }
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                                [CtThrowImpl][CtCommentImpl]// Cannot happen
                                throw [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Throwables.propagate([CtVariableReadImpl]e);
                            }
                        } 
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]pluginClassLoader.loadClass([CtInvocationImpl][CtFieldReadImpl]classIterator.next());
                        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.ClassNotFoundException | [CtTypeReferenceImpl]java.lang.NoClassDefFoundError e) [CtBlockImpl]{
                            [CtThrowImpl][CtCommentImpl]// Cannot happen, since the class name is from the list of the class files under the classloader.
                            throw [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Throwables.propagate([CtVariableReadImpl]e);
                        }
                    }
                };
            }
        };
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts and returns name of the plugin.
     */
    private [CtTypeReferenceImpl]java.lang.String getPluginName([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Name annotation = [CtInvocationImpl][CtVariableReadImpl]cls.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Name.class);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]annotation == [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotation.value().isEmpty() ? [CtInvocationImpl][CtVariableReadImpl]cls.getName() : [CtInvocationImpl][CtVariableReadImpl]annotation.value();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get all the {@link io.cdap.cdap.api.annotation.Requirements} specified by a plugin as {@link Requirements}.
     * The requirements are case insensitive and always represented in lowercase.
     *
     * @param cls
     * 		the plugin class whose requirement needs to be found
     * @return {@link Requirements} containing the requirements specified by the plugin (in lowercase). If the plugin does
    not specify any {@link io.cdap.cdap.api.annotation.Requirements} then the {@link Requirements} will be empty.
     */
    [CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.Requirements getRequirementsAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Requirements annotation = [CtInvocationImpl][CtVariableReadImpl]cls.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.plugin.Requirements.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]io.cdap.cdap.api.plugin.Requirements.EMPTY;
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.Requirements([CtInvocationImpl]collect([CtInvocationImpl][CtVariableReadImpl]annotation.datasetTypes()), [CtInvocationImpl]collect([CtInvocationImpl][CtVariableReadImpl]annotation.accelerators()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> collect([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] field) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]field).map([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.trim().toLowerCase()).filter([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]s)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns description for the plugin.
     */
    private [CtTypeReferenceImpl]java.lang.String getPluginDescription([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> cls) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Description annotation = [CtInvocationImpl][CtVariableReadImpl]cls.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Description.class);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null ? [CtLiteralImpl]"" : [CtInvocationImpl][CtVariableReadImpl]annotation.value();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Constructs the fully qualified class name based on the package name and the class file name.
     */
    private [CtTypeReferenceImpl]java.lang.String getClassName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String classFileName) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]packageName + [CtLiteralImpl]".") + [CtInvocationImpl][CtVariableReadImpl]classFileName.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]classFileName.length() - [CtInvocationImpl][CtLiteralImpl]".class".length());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets all config properties for the given plugin.
     *
     * @return the name of the config field in the plugin class or {@code null} if the plugin doesn't have a config field
     */
    [CtAnnotationImpl]@javax.annotation.Nullable
    private [CtTypeReferenceImpl]java.lang.String getProperties([CtParameterImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> pluginType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField> result) throws [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// Get the config field
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> type : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pluginType.getTypes().classes()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getRawType().getDeclaredFields()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> fieldType = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.reflect.TypeToken.of([CtInvocationImpl][CtVariableReadImpl]field.getGenericType());
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.api.plugin.PluginConfig.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]fieldType.getRawType())) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Pick up all config properties
                    inspectConfigField([CtVariableReadImpl]fieldType, [CtVariableReadImpl]result);
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]field.getName();
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Inspects the plugin config class and build up a map for {@link PluginPropertyField}.
     *
     * @param configType
     * 		type of the config class
     * @param result
     * 		map for storing the result
     * @throws UnsupportedTypeException
     * 		if a field type in the config class is not supported
     */
    private [CtTypeReferenceImpl]void inspectConfigField([CtParameterImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> configType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField> result) throws [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> type : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configType.getTypes().classes()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.api.plugin.PluginConfig.class.equals([CtInvocationImpl][CtVariableReadImpl]type.getRawType())) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getRawType().getDeclaredFields()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int modifiers = [CtInvocationImpl][CtVariableReadImpl]field.getModifiers();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isTransient([CtVariableReadImpl]modifiers) || [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isStatic([CtVariableReadImpl]modifiers)) || [CtInvocationImpl][CtVariableReadImpl]field.isSynthetic()) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField property = [CtInvocationImpl]createPluginProperty([CtVariableReadImpl]field, [CtVariableReadImpl]type);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.containsKey([CtInvocationImpl][CtVariableReadImpl]property.getName())) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Plugin config with name " + [CtInvocationImpl][CtVariableReadImpl]property.getName()) + [CtLiteralImpl]" already defined in ") + [CtInvocationImpl][CtVariableReadImpl]configType.getRawType());
                }
                [CtInvocationImpl][CtVariableReadImpl]result.put([CtInvocationImpl][CtVariableReadImpl]property.getName(), [CtVariableReadImpl]property);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a {@link PluginPropertyField} based on the given field.
     */
    private [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField createPluginProperty([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Field field, [CtParameterImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> resolvingType) throws [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.reflect.TypeToken<[CtWildcardReferenceImpl]?> fieldType = [CtInvocationImpl][CtVariableReadImpl]resolvingType.resolveType([CtInvocationImpl][CtVariableReadImpl]field.getGenericType());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> rawType = [CtInvocationImpl][CtVariableReadImpl]fieldType.getRawType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Name nameAnnotation = [CtInvocationImpl][CtVariableReadImpl]field.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Name.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Description descAnnotation = [CtInvocationImpl][CtVariableReadImpl]field.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Description.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]nameAnnotation == [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl]field.getName() : [CtInvocationImpl][CtVariableReadImpl]nameAnnotation.value();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String description = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]descAnnotation == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtInvocationImpl][CtVariableReadImpl]descAnnotation.value();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.cdap.cdap.api.annotation.Macro macroAnnotation = [CtInvocationImpl][CtVariableReadImpl]field.getAnnotation([CtFieldReadImpl]io.cdap.cdap.api.annotation.Macro.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean macroSupported = [CtBinaryOperatorImpl][CtVariableReadImpl]macroAnnotation != [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rawType.isPrimitive()) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField([CtVariableReadImpl]name, [CtVariableReadImpl]description, [CtInvocationImpl][CtVariableReadImpl]rawType.getName(), [CtLiteralImpl]true, [CtVariableReadImpl]macroSupported);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]rawType = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.primitives.Primitives.unwrap([CtVariableReadImpl]rawType);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]rawType.isPrimitive()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]java.lang.String.class.equals([CtVariableReadImpl]rawType))) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.data.schema.UnsupportedTypeException([CtLiteralImpl]"Only primitive and String types are supported");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean required = [CtLiteralImpl]true;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.annotation.Annotation annotation : [CtInvocationImpl][CtVariableReadImpl]field.getAnnotations()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]annotation.annotationType().getName().endsWith([CtLiteralImpl]".Nullable")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]required = [CtLiteralImpl]false;
                [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.cdap.cdap.api.plugin.PluginPropertyField([CtVariableReadImpl]name, [CtVariableReadImpl]description, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rawType.getSimpleName().toLowerCase(), [CtVariableReadImpl]required, [CtVariableReadImpl]macroSupported);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Detects if a class is annotated with {@link Plugin} without loading the class.
     *
     * @param className
     * 		name of the class
     * @param classLoader
     * 		ClassLoader for loading the class file of the given class
     * @return true if the given class is annotated with {@link Plugin}
     */
    private [CtTypeReferenceImpl]boolean isPlugin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String className, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtVariableReadImpl]classLoader.getResourceAsStream([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]className.replace([CtLiteralImpl]'.', [CtLiteralImpl]'/') + [CtLiteralImpl]".class")) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]is == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtCommentImpl]// Use ASM to inspect the class bytecode to see if it is annotated with @Plugin
            final [CtArrayTypeReferenceImpl]boolean[] isPlugin = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtLiteralImpl]1];
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.objectweb.asm.ClassReader cr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.objectweb.asm.ClassReader([CtVariableReadImpl]is);
            [CtInvocationImpl][CtVariableReadImpl]cr.accept([CtNewClassImpl]new [CtTypeReferenceImpl]org.objectweb.asm.ClassVisitor([CtFieldReadImpl]org.objectweb.asm.Opcodes.ASM5)[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.objectweb.asm.AnnotationVisitor visitAnnotation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String desc, [CtParameterImpl][CtTypeReferenceImpl]boolean visible) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]io.cdap.cdap.api.annotation.Plugin.class.getName().equals([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.objectweb.asm.Type.getType([CtVariableReadImpl]desc).getClassName()) && [CtVariableReadImpl]visible) [CtBlockImpl]{
                        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]isPlugin[[CtLiteralImpl]0] = [CtLiteralImpl]true;
                    }
                    [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.visitAnnotation([CtVariableReadImpl]desc, [CtVariableReadImpl]visible);
                }
            }, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]org.objectweb.asm.ClassReader.SKIP_CODE | [CtFieldReadImpl]org.objectweb.asm.ClassReader.SKIP_DEBUG) | [CtFieldReadImpl]org.objectweb.asm.ClassReader.SKIP_FRAMES);
            [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]isPlugin[[CtLiteralImpl]0];
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// If failed to open the class file, then it cannot be a plugin
            [CtFieldReadImpl]io.cdap.cdap.internal.app.runtime.artifact.ArtifactInspector.LOG.warn([CtLiteralImpl]"Failed to open class file for {}", [CtVariableReadImpl]className, [CtVariableReadImpl]e);
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }
}
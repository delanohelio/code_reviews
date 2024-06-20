[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at:
 *
 *     https://www.eclipse.org/legal/epl-2.0/
 * AssemblyConfiguration
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
[CtPackageDeclarationImpl]package org.eclipse.jkube.kit.config.image.build;
[CtImportImpl]import java.util.regex.Pattern;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.KitLogger;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.util.EnvUtil;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.io.Serializable;
[CtImportImpl]import org.apache.commons.lang3.SerializationUtils;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author roland
 * @since 02.09.14
 */
public class BuildConfiguration<[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.AssemblyConfiguration> implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String DEFAULT_FILTER = [CtLiteralImpl]"${*}";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String DEFAULT_CLEANUP = [CtLiteralImpl]"try";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Directory used as the contexst directory, e.g. for a docker build.
     */
    private [CtTypeReferenceImpl]java.lang.String contextDir;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Path to a dockerfile to use. Its parent directory is used as build context (i.e. as <code>dockerFileDir</code>).
     * Multiple different Dockerfiles can be specified that way. If set overwrites a possibly given
     * <code>contextDir</code>
     */
    private [CtTypeReferenceImpl]java.lang.String dockerFile;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Path to a docker archive to load an image instead of building from scratch.
     * Note only either dockerFile/dockerFileDir or
     * dockerArchive can be used.
     */
    private [CtTypeReferenceImpl]java.lang.String dockerArchive;

    [CtFieldImpl][CtJavaDocImpl]/**
     * How interpolation of a dockerfile should be performed
     */
    private [CtTypeReferenceImpl]java.lang.String filter;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Base Image
     */
    private [CtTypeReferenceImpl]java.lang.String from;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Extended version for ;&lt;from;&gt;
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> fromExt;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String registry;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String maintainer;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> ports;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments shell;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Policy for pulling the base images
     */
    private [CtTypeReferenceImpl]java.lang.String imagePullPolicy;

    [CtFieldImpl][CtJavaDocImpl]/**
     * RUN Commands within Build/Image
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> runCmds;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String cleanup;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean nocache;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean optimise;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> volumes;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> tags;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> env;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> labels;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> args;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments entryPoint;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String workdir;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments cmd;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String user;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.HealthCheckConfiguration healthCheck;

    [CtFieldImpl]private [CtTypeParameterReferenceImpl]A assembly;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean skip;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.ArchiveCompression compression = [CtFieldReadImpl]ArchiveCompression.none;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> buildOptions;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Directory holding an external Dockerfile which is used to build the
     * image. This Dockerfile will be enriched by the addition build configuration
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    private [CtTypeReferenceImpl]java.lang.String dockerFileDir;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.io.File dockerFileFile;

    [CtFieldImpl][CtCommentImpl]// Path to Dockerfile to use, initialized lazily ....
    private [CtTypeReferenceImpl]java.io.File dockerArchiveFile;

    [CtConstructorImpl]protected BuildConfiguration() [CtBlockImpl]{
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isDockerFileMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFile != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl]contextDir != [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getDockerFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dockerFileFile;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getDockerArchive() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dockerArchiveFile;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getContextDir() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]contextDir != [CtLiteralImpl]null ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]contextDir) : [CtInvocationImpl][CtInvocationImpl]getDockerFile().getParentFile();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFilter() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]filter;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDockerFileRaw() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dockerFile;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getContextDirRaw() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]contextDir;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments getShell() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]shell;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDockerArchiveRaw() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dockerArchive;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDockerFileDirRaw() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dockerFileDir;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFilterRaw() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]filter;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFrom() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]from == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl]getFromExt() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getFromExt().get([CtLiteralImpl]"name");
        }
        [CtReturnImpl]return [CtFieldReadImpl]from;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getFromExt() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fromExt;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getRegistry() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]registry;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getMaintainer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maintainer;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getWorkdir() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]workdir;
    }

    [CtMethodImpl]public [CtTypeParameterReferenceImpl]A getAssemblyConfiguration() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]assembly;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getPorts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]removeEmptyEntries([CtFieldReadImpl]ports);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getImagePullPolicy() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]imagePullPolicy;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getVolumes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]removeEmptyEntries([CtFieldReadImpl]volumes);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getTags() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]removeEmptyEntries([CtFieldReadImpl]tags);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getEnv() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]env;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getLabels() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]labels;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments getCmd() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]cmd;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCleanupMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]cleanup;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean getNoCache() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]nocache;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean getOptimise() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]optimise;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean getSkip() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]skip != [CtLiteralImpl]null ? [CtFieldReadImpl]skip : [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.ArchiveCompression getCompression() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]compression;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getBuildOptions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]buildOptions;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments getEntryPoint() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]entryPoint;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getRunCmds() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]removeEmptyEntries([CtFieldReadImpl]runCmds);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getUser() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]user;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.HealthCheckConfiguration getHealthCheck() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]healthCheck;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getArgs() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]args;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean optimise() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]optimise != [CtLiteralImpl]null ? [CtFieldReadImpl]optimise : [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.lang.String getCommand() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCleanup() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]cleanup;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean nocache() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]nocache != [CtLiteralImpl]null ? [CtFieldReadImpl]nocache : [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.CleanupMode cleanupMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jkube.kit.config.image.build.CleanupMode.parse([CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]cleanup != [CtLiteralImpl]null ? [CtFieldReadImpl]cleanup : [CtFieldReadImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.DEFAULT_CLEANUP);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getAbsoluteContextDirPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sourceDirectory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectBaseDir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jkube.kit.common.util.EnvUtil.prepareAbsoluteSourceDirPath([CtVariableReadImpl]sourceDirectory, [CtVariableReadImpl]projectBaseDir, [CtInvocationImpl][CtInvocationImpl]getContextDir().getPath());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getAbsoluteDockerFilePath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sourceDirectory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectBaseDir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jkube.kit.common.util.EnvUtil.prepareAbsoluteSourceDirPath([CtVariableReadImpl]sourceDirectory, [CtVariableReadImpl]projectBaseDir, [CtInvocationImpl][CtInvocationImpl]getDockerFile().getPath());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File getAbsoluteDockerTarPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sourceDirectory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectBaseDir) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jkube.kit.common.util.EnvUtil.prepareAbsoluteSourceDirPath([CtVariableReadImpl]sourceDirectory, [CtVariableReadImpl]projectBaseDir, [CtInvocationImpl][CtInvocationImpl]getDockerArchive().getPath());
    }

    [CtClassImpl][CtCommentImpl]// ===========================================================================================
    public static class TypedBuilder<[CtTypeParameterImpl]A extends [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.AssemblyConfiguration, [CtTypeParameterImpl]B extends [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration<[CtTypeParameterReferenceImpl]A>> {
        [CtFieldImpl]protected final [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration<[CtTypeParameterReferenceImpl]A> config;

        [CtConstructorImpl]protected TypedBuilder([CtParameterImpl][CtTypeParameterReferenceImpl]B config) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> contextDir([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dir) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.contextDir = [CtVariableReadImpl]dir;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> dockerFile([CtParameterImpl][CtTypeReferenceImpl]java.lang.String file) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.dockerFile = [CtVariableReadImpl]file;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> dockerArchive([CtParameterImpl][CtTypeReferenceImpl]java.lang.String archive) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.dockerArchive = [CtVariableReadImpl]archive;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> dockerFileDir([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dir) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.dockerFileDir = [CtVariableReadImpl]dir;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> filter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String filter) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.filter = [CtVariableReadImpl]filter;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> from([CtParameterImpl][CtTypeReferenceImpl]java.lang.String from) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.from = [CtVariableReadImpl]from;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> fromExt([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> fromExt) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.fromExt = [CtVariableReadImpl]fromExt;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> registry([CtParameterImpl][CtTypeReferenceImpl]java.lang.String registry) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.registry = [CtVariableReadImpl]registry;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> maintainer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String maintainer) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.maintainer = [CtVariableReadImpl]maintainer;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> workdir([CtParameterImpl][CtTypeReferenceImpl]java.lang.String workdir) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.workdir = [CtVariableReadImpl]workdir;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> assembly([CtParameterImpl][CtTypeParameterReferenceImpl]A assembly) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.assembly = [CtVariableReadImpl]assembly;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> ports([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> ports) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.ports = [CtVariableReadImpl]ports;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> imagePullPolicy([CtParameterImpl][CtTypeReferenceImpl]java.lang.String imagePullPolicy) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.imagePullPolicy = [CtVariableReadImpl]imagePullPolicy;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> runCmds([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> theCmds) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.runCmds = [CtVariableReadImpl]theCmds;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> volumes([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> volumes) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.volumes = [CtVariableReadImpl]volumes;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> tags([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> tags) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.tags = [CtVariableReadImpl]tags;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> env([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> env) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.env = [CtVariableReadImpl]env;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> args([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> args) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.args = [CtVariableReadImpl]args;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> labels([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> labels) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.labels = [CtVariableReadImpl]labels;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> cmd([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments cmd) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cmd != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.cmd = [CtVariableReadImpl]cmd;
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> cleanup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String cleanup) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.cleanup = [CtVariableReadImpl]cleanup;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> compression([CtParameterImpl][CtTypeReferenceImpl]java.lang.String compression) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]compression == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.compression = [CtLiteralImpl]null;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.compression = [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jkube.kit.config.image.build.ArchiveCompression.valueOf([CtVariableReadImpl]compression);
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> nocache([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean nocache) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.nocache = [CtVariableReadImpl]nocache;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> optimise([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean optimise) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.optimise = [CtVariableReadImpl]optimise;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> entryPoint([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments entryPoint) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entryPoint != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.entryPoint = [CtVariableReadImpl]entryPoint;
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> user([CtParameterImpl][CtTypeReferenceImpl]java.lang.String user) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.user = [CtVariableReadImpl]user;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> healthCheck([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.HealthCheckConfiguration healthCheck) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.healthCheck = [CtVariableReadImpl]healthCheck;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> skip([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean skip) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.skip = [CtVariableReadImpl]skip;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> buildOptions([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> buildOptions) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.buildOptions = [CtVariableReadImpl]buildOptions;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeParameterReferenceImpl]A, [CtTypeParameterReferenceImpl]B> shell([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.Arguments shell) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]shell != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.shell = [CtVariableReadImpl]shell;
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeParameterReferenceImpl]B build() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl](([CtTypeParameterReferenceImpl]B) (config));
        }
    }

    [CtClassImpl]public static class Builder extends [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration.TypedBuilder<[CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.AssemblyConfiguration, [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration<[CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.AssemblyConfiguration>> {
        [CtConstructorImpl]public Builder() [CtBlockImpl]{
            [CtInvocationImpl]this([CtLiteralImpl]null);
        }

        [CtConstructorImpl]public Builder([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration<[CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.AssemblyConfiguration> that) [CtBlockImpl]{
            [CtInvocationImpl]super([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]that == [CtLiteralImpl]null ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.image.build.BuildConfiguration<>() : [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.SerializationUtils.clone([CtVariableReadImpl]that));
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String initAndValidate([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.common.KitLogger log) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]entryPoint != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]entryPoint.validate();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cmd != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]cmd.validate();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]healthCheck != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]healthCheck.validate();
        }
        [CtInvocationImpl]initDockerFileFile([CtVariableReadImpl]log);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]healthCheck != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// HEALTHCHECK support added later
            return [CtLiteralImpl]"1.24";
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]args != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// ARG support came in later
            return [CtLiteralImpl]"1.21";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtCommentImpl]// Initialize the dockerfile location and the build mode
    private [CtTypeReferenceImpl]void initDockerFileFile([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.common.KitLogger log) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// can't have dockerFile/dockerFileDir and dockerArchive
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFile != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFileDir != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]dockerArchive != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Both <dockerFile> (<dockerFileDir>) and <dockerArchive> are set. " + [CtLiteralImpl]"Only one of them can be specified.");
        }
        [CtAssignmentImpl][CtFieldWriteImpl]dockerFileFile = [CtInvocationImpl]findDockerFileFile([CtVariableReadImpl]log);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]dockerArchive != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]dockerArchiveFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]dockerArchive);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.io.File findDockerFileFile([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.common.KitLogger log) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFileDir != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]contextDir != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]log.warn([CtLiteralImpl]"Both contextDir (%s) and deprecated dockerFileDir (%s) are configured. Using contextDir.", [CtFieldReadImpl]contextDir, [CtFieldReadImpl]dockerFileDir);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]dockerFile != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]dockerFile);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFileDir == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]contextDir == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]dFile;
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]contextDir != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dFile.isAbsolute()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtVariableReadImpl]dFile;
                    }
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]contextDir, [CtFieldReadImpl]dockerFile);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dFile.isAbsolute()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"<dockerFile> can not be absolute path if <dockerFileDir> also set.");
                }
                [CtInvocationImpl][CtVariableReadImpl]log.warn([CtLiteralImpl]"dockerFileDir parameter is deprecated, please migrate to contextDir");
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]dockerFileDir, [CtFieldReadImpl]dockerFile);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]contextDir != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]contextDir, [CtLiteralImpl]"Dockerfile");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]dockerFileDir != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]dockerFileDir, [CtLiteralImpl]"Dockerfile");
        }
        [CtIfImpl][CtCommentImpl]// TODO: Remove the following deprecated handling section
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]dockerArchive == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deprecatedDockerFileDir = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl]getAssemblyConfiguration() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl]getAssemblyConfiguration().getDockerFileDir() : [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deprecatedDockerFileDir != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]log.warn([CtLiteralImpl]"<dockerFileDir> in the <assembly> section of a <build> configuration is deprecated");
                [CtInvocationImpl][CtVariableReadImpl]log.warn([CtLiteralImpl]"Please use <dockerFileDir> or <dockerFile> directly within the <build> configuration instead");
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]deprecatedDockerFileDir, [CtLiteralImpl]"Dockerfile");
            }
        }
        [CtReturnImpl][CtCommentImpl]// No dockerfile mode
        return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String validate() throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]entryPoint != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]entryPoint.validate();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]cmd != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]cmd.validate();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]healthCheck != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]healthCheck.validate();
        }
        [CtIfImpl][CtCommentImpl]// can't have dockerFile/dockerFileDir and dockerArchive
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]dockerFile != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl]contextDir != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]dockerArchive != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Both <dockerFile> (<dockerFileDir>) and <dockerArchive> are set. " + [CtLiteralImpl]"Only one of them can be specified.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]healthCheck != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// HEALTHCHECK support added later
            return [CtLiteralImpl]"1.24";
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]args != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// ARG support came in later
            return [CtLiteralImpl]"1.21";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.io.File calculateDockerFilePath() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]dockerFile != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File dFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]dockerFile);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]contextDir == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]dFile;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dFile.isAbsolute()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]dFile;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"os.name").toLowerCase().contains([CtLiteralImpl]"windows") && [CtUnaryOperatorImpl](![CtInvocationImpl]isValidWindowsFileName([CtFieldReadImpl]dockerFile))) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Invalid Windows file name %s for <dockerFile>", [CtFieldReadImpl]dockerFile));
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]contextDir, [CtInvocationImpl][CtVariableReadImpl]dFile.getPath());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]contextDir != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]contextDir, [CtLiteralImpl]"Dockerfile");
        }
        [CtThrowImpl][CtCommentImpl]// No dockerfile mode
        throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Can't calculate a docker file path if neither dockerFile nor contextDir is specified");
    }

    [CtMethodImpl][CtCommentImpl]// ===============================================================================================================
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> removeEmptyEntries([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> list) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]list == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]list.stream().filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::trim).filter([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]s.isEmpty()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validate that the provided filename is a valid Windows filename.
     *
     * The validation of the Windows filename is copied from stackoverflow: https://stackoverflow.com/a/6804755
     *
     * @param filename
     * 		the filename
     * @return filename is a valid Windows filename
     */
    [CtTypeReferenceImpl]boolean isValidWindowsFileName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String filename) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Pattern pattern = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"# Match a valid Windows filename (unspecified file system).          \n" + [CtLiteralImpl]"^                                # Anchor to start of string.        \n") + [CtLiteralImpl]"(?!                              # Assert filename is not: CON, PRN, \n") + [CtLiteralImpl]"  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n") + [CtLiteralImpl]"    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n") + [CtLiteralImpl]"    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n") + [CtLiteralImpl]"  )                              # LPT6, LPT7, LPT8, and LPT9...     \n") + [CtLiteralImpl]"  (?:\\.[^.]*)?                  # followed by optional extension    \n") + [CtLiteralImpl]"  $                              # and end of string                 \n") + [CtLiteralImpl]")                                # End negative lookahead assertion. \n") + [CtLiteralImpl]"[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n") + [CtLiteralImpl]"[^<>:\"/\\\\|?*\\x00-\\x1F .]    # Last char is not a space or dot.  \n") + [CtLiteralImpl]"$                                # Anchor to end of string.            ", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]java.util.regex.Pattern.[CtFieldReferenceImpl]CASE_INSENSITIVE | [CtFieldReadImpl][CtTypeAccessImpl]java.util.regex.Pattern.[CtFieldReferenceImpl]UNICODE_CASE) | [CtFieldReadImpl][CtTypeAccessImpl]java.util.regex.Pattern.[CtFieldReferenceImpl]COMMENTS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher matcher = [CtInvocationImpl][CtVariableReadImpl]pattern.matcher([CtVariableReadImpl]filename);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]matcher.matches();
    }
}
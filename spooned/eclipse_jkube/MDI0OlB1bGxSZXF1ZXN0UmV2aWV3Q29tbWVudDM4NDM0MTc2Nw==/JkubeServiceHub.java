[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at:
 *
 *     https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
[CtPackageDeclarationImpl]package org.eclipse.jkube.kit.config.service;
[CtUnresolvedImport]import org.eclipse.jkube.kit.config.service.openshift.OpenshiftBuildService;
[CtUnresolvedImport]import org.eclipse.jkube.kit.config.access.ClusterAccess;
[CtUnresolvedImport]import org.eclipse.jkube.kit.build.service.docker.ServiceHub;
[CtUnresolvedImport]import org.eclipse.jkube.kit.config.resource.RuntimeMode;
[CtUnresolvedImport]import org.eclipse.jkube.kit.config.service.kubernetes.DockerBuildService;
[CtUnresolvedImport]import io.fabric8.openshift.client.OpenShiftClient;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.KitLogger;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.JkubeProject;
[CtUnresolvedImport]import io.fabric8.kubernetes.client.KubernetesClient;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.service.ArtifactResolverService;
[CtUnresolvedImport]import org.eclipse.jkube.kit.common.util.LazyBuilder;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author nicola
 * @since 17/02/2017
 */
public class JkubeServiceHub {
    [CtFieldImpl][CtCommentImpl]/* Configured resources */
    private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.access.ClusterAccess clusterAccess;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode platformMode;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.KitLogger log;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.build.service.docker.ServiceHub dockerServiceHub;

    [CtFieldImpl]private [CtTypeReferenceImpl]BuildService.BuildServiceConfig buildServiceConfig;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.JkubeProject mavenProject;

    [CtFieldImpl][CtJavaDocImpl]/**
     * /*
     * Computed resources
     */
    private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode resolvedMode;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.fabric8.kubernetes.client.KubernetesClient client;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.util.LazyBuilder<[CtWildcardReferenceImpl]?>> services = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtConstructorImpl]private JkubeServiceHub() [CtBlockImpl]{
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void init() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtFieldReadImpl]clusterAccess, [CtLiteralImpl]"clusterAccess");
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtFieldReadImpl]log, [CtLiteralImpl]"log");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.resolvedMode = [CtInvocationImpl][CtFieldReadImpl]clusterAccess.resolveRuntimeMode([CtFieldReadImpl]platformMode, [CtFieldReadImpl]log);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]resolvedMode != [CtFieldReadImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode.kubernetes) && [CtBinaryOperatorImpl]([CtFieldReadImpl]resolvedMode != [CtFieldReadImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode.openshift)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Unknown platform mode " + [CtFieldReadImpl]platformMode) + [CtLiteralImpl]" resolved as ") + [CtFieldReadImpl]resolvedMode);
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.client = [CtInvocationImpl][CtFieldReadImpl]clusterAccess.createDefaultClient([CtFieldReadImpl]log);
        [CtInvocationImpl][CtCommentImpl]// Lazily building services
        [CtFieldReadImpl][CtThisAccessImpl]this.services.putIfAbsent([CtFieldReadImpl]org.eclipse.jkube.kit.config.service.ApplyService.class, [CtNewClassImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.util.LazyBuilder<[CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.ApplyService>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.ApplyService build() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.ApplyService([CtFieldReadImpl]client, [CtFieldReadImpl]log);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.services.putIfAbsent([CtFieldReadImpl]org.eclipse.jkube.kit.config.service.BuildService.class, [CtNewClassImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.util.LazyBuilder<[CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService build() [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService buildService;
                [CtIfImpl][CtCommentImpl]// Creating platform-dependent services
                if ([CtBinaryOperatorImpl][CtFieldReadImpl]resolvedMode == [CtFieldReadImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode.openshift) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtFieldReadImpl]client instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.fabric8.openshift.client.OpenShiftClient)) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Openshift platform has been specified but Openshift has not been detected!");
                    }
                    [CtAssignmentImpl][CtCommentImpl]// Openshift services
                    [CtVariableWriteImpl]buildService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.openshift.OpenshiftBuildService([CtFieldReadImpl](([CtTypeReferenceImpl]io.fabric8.openshift.client.OpenShiftClient) (client)), [CtFieldReadImpl]log, [CtFieldReadImpl]dockerServiceHub, [CtFieldReadImpl]buildServiceConfig);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Kubernetes services
                    [CtVariableWriteImpl]buildService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.kubernetes.DockerBuildService([CtFieldReadImpl]dockerServiceHub, [CtFieldReadImpl]buildServiceConfig);
                }
                [CtReturnImpl]return [CtVariableReadImpl]buildService;
            }
        });
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.services.putIfAbsent([CtFieldReadImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService.class, [CtNewClassImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.util.LazyBuilder<[CtTypeReferenceImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService build() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.ArtifactResolverServiceMavenImpl([CtFieldReadImpl]mavenProject);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService getBuildService() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService) ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.services.get([CtFieldReadImpl]org.eclipse.jkube.kit.config.service.BuildService.class).get()));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService getArtifactResolverService() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService) ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.services.get([CtFieldReadImpl]org.eclipse.jkube.kit.common.service.ArtifactResolverService.class).get()));
    }

    [CtClassImpl][CtCommentImpl]// =================================================
    public static class Builder {
        [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub hub;

        [CtConstructorImpl]public Builder() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hub = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder clusterAccess([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.access.ClusterAccess clusterAccess) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.clusterAccess = [CtVariableReadImpl]clusterAccess;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder platformMode([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.resource.RuntimeMode platformMode) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.platformMode = [CtVariableReadImpl]platformMode;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder log([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.common.KitLogger log) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.log = [CtVariableReadImpl]log;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder dockerServiceHub([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.build.service.docker.ServiceHub dockerServiceHub) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.dockerServiceHub = [CtVariableReadImpl]dockerServiceHub;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder buildServiceConfig([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.BuildService.BuildServiceConfig buildServiceConfig) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.buildServiceConfig = [CtVariableReadImpl]buildServiceConfig;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub.Builder jkubeProject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.jkube.kit.common.JkubeProject mavenProject) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]hub.mavenProject = [CtVariableReadImpl]mavenProject;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.jkube.kit.config.service.JkubeServiceHub build() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]hub.init();
            [CtReturnImpl]return [CtFieldReadImpl]hub;
        }
    }
}
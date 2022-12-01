[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Netflix, Inc.

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
[CtPackageDeclarationImpl]package com.netflix.titus.master.mesos.kubeapiserver;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import io.kubernetes.client.models.V1ContainerState;
[CtUnresolvedImport]import io.kubernetes.client.models.V1Pod;
[CtUnresolvedImport]import io.kubernetes.client.models.V1ContainerStateWaiting;
[CtUnresolvedImport]import com.netflix.titus.common.util.CollectionsExt;
[CtImportImpl]import java.util.concurrent.Executors;
[CtUnresolvedImport]import com.netflix.titus.common.util.StringExt;
[CtUnresolvedImport]import com.netflix.titus.master.mesos.TitusExecutorDetails;
[CtUnresolvedImport]import io.kubernetes.client.ApiClient;
[CtUnresolvedImport]import io.kubernetes.client.informer.SharedInformerFactory;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import com.google.common.base.Strings;
[CtUnresolvedImport]import io.kubernetes.client.models.V1ContainerStateTerminated;
[CtUnresolvedImport]import io.kubernetes.client.models.V1NodeAddress;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import com.squareup.okhttp.Request;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import io.kubernetes.client.models.V1ContainerStateRunning;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import com.netflix.titus.api.jobmanager.model.job.Job;
[CtUnresolvedImport]import io.kubernetes.client.models.V1ContainerStatus;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import com.netflix.titus.master.mesos.kubeapiserver.direct.KubeConstants;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import io.kubernetes.client.util.Config;
[CtUnresolvedImport]import com.netflix.titus.master.mesos.kubeapiserver.direct.DirectKubeConfiguration;
[CtUnresolvedImport]import com.netflix.titus.api.jobmanager.JobConstraints;
[CtUnresolvedImport]import io.kubernetes.client.models.V1Toleration;
[CtUnresolvedImport]import com.netflix.titus.common.runtime.TitusRuntime;
[CtUnresolvedImport]import com.netflix.titus.common.util.NetworkExt;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.concurrent.atomic.AtomicLong;
[CtUnresolvedImport]import io.kubernetes.client.models.V1Node;
[CtClassImpl]public class KubeUtil {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.regex.Pattern UUID_PATTERN = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]com.squareup.okhttp.Request, [CtTypeReferenceImpl]java.lang.String> DEFAULT_URI_MAPPER = [CtLambdaImpl]([CtParameterImpl]com.squareup.okhttp.Request r) -> [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.url().getPath();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher matcher = [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.UUID_PATTERN.matcher([CtVariableReadImpl]path);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]matcher.replaceAll([CtLiteralImpl]"");
    };

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERNAL_IP = [CtLiteralImpl]"InternalIP";

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.kubernetes.client.ApiClient createApiClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String kubeApiServerUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String kubeConfigPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String metricsNamePrefix, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.common.runtime.TitusRuntime titusRuntime, [CtParameterImpl][CtTypeReferenceImpl]long readTimeoutMs) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.createApiClient([CtVariableReadImpl]kubeApiServerUrl, [CtVariableReadImpl]kubeConfigPath, [CtVariableReadImpl]metricsNamePrefix, [CtVariableReadImpl]titusRuntime, [CtFieldReadImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.DEFAULT_URI_MAPPER, [CtVariableReadImpl]readTimeoutMs);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.kubernetes.client.ApiClient createApiClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String kubeApiServerUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String kubeConfigPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String metricsNamePrefix, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.common.runtime.TitusRuntime titusRuntime, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]com.squareup.okhttp.Request, [CtTypeReferenceImpl]java.lang.String> uriMapper, [CtParameterImpl][CtTypeReferenceImpl]long readTimeoutMs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.titus.master.mesos.kubeapiserver.OkHttpMetricsInterceptor metricsInterceptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.netflix.titus.master.mesos.kubeapiserver.OkHttpMetricsInterceptor([CtVariableReadImpl]metricsNamePrefix, [CtInvocationImpl][CtVariableReadImpl]titusRuntime.getRegistry(), [CtInvocationImpl][CtVariableReadImpl]titusRuntime.getClock(), [CtVariableReadImpl]uriMapper);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.ApiClient client;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]kubeApiServerUrl)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]client = [CtInvocationImpl][CtTypeAccessImpl]io.kubernetes.client.util.Config.fromConfig([CtVariableReadImpl]kubeConfigPath);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]client = [CtInvocationImpl][CtTypeAccessImpl]io.kubernetes.client.util.Config.fromUrl([CtVariableReadImpl]kubeApiServerUrl);
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getHttpClient().setReadTimeout([CtVariableReadImpl]readTimeoutMs, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MILLISECONDS);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getHttpClient().interceptors().add([CtVariableReadImpl]metricsInterceptor);
        [CtReturnImpl]return [CtVariableReadImpl]client;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.kubernetes.client.informer.SharedInformerFactory createSharedInformerFactory([CtParameterImpl][CtTypeReferenceImpl]java.lang.String threadNamePrefix, [CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.ApiClient apiClient) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong nextThreadNum = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong([CtLiteralImpl]0);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.informer.SharedInformerFactory([CtVariableReadImpl]apiClient, [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newCachedThreadPool([CtLambdaImpl]([CtParameterImpl]java.lang.Runnable runnable) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Thread thread = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtVariableReadImpl]runnable, [CtBinaryOperatorImpl][CtVariableReadImpl]threadNamePrefix + [CtInvocationImpl][CtVariableReadImpl]nextThreadNum.getAndIncrement());
            [CtInvocationImpl][CtVariableReadImpl]thread.setDaemon([CtLiteralImpl]true);
            [CtReturnImpl]return [CtVariableReadImpl]thread;
        }));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.netflix.titus.master.mesos.TitusExecutorDetails> getTitusExecutorDetails([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Pod pod) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> annotations = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pod.getMetadata().getAnnotations();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]annotations.get([CtLiteralImpl]"IpAddress"))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.titus.master.mesos.TitusExecutorDetails titusExecutorDetails = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.netflix.titus.master.mesos.TitusExecutorDetails([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap(), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.netflix.titus.master.mesos.TitusExecutorDetails.NetworkConfiguration([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtVariableReadImpl]annotations.getOrDefault([CtLiteralImpl]"IsRoutableIp", [CtLiteralImpl]"true")), [CtInvocationImpl][CtVariableReadImpl]annotations.getOrDefault([CtLiteralImpl]"IpAddress", [CtLiteralImpl]"UnknownIpAddress"), [CtInvocationImpl][CtVariableReadImpl]annotations.getOrDefault([CtLiteralImpl]"EniIpAddress", [CtLiteralImpl]"UnknownEniIpAddress"), [CtInvocationImpl][CtVariableReadImpl]annotations.getOrDefault([CtLiteralImpl]"EniId", [CtLiteralImpl]"UnknownEniId"), [CtInvocationImpl][CtVariableReadImpl]annotations.getOrDefault([CtLiteralImpl]"ResourceId", [CtLiteralImpl]"UnknownResourceId")));
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]titusExecutorDetails);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerState> findContainerState([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Pod pod) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStatus> containerStatuses = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pod.getStatus().getContainerStatuses();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]containerStatuses != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStatus status : [CtVariableReadImpl]containerStatuses) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerState state = [CtInvocationImpl][CtVariableReadImpl]status.getState();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]state);
                }
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStateTerminated> findTerminatedContainerStatus([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Pod pod) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.findContainerState([CtVariableReadImpl]pod).flatMap([CtLambdaImpl]([CtParameterImpl] state) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]state.getTerminated()));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String formatV1ContainerState([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerState containerState) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]containerState.getWaiting() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStateWaiting waiting = [CtInvocationImpl][CtVariableReadImpl]containerState.getWaiting();
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"{state=waiting, reason=%s, message=%s}", [CtInvocationImpl][CtVariableReadImpl]waiting.getReason(), [CtInvocationImpl][CtVariableReadImpl]waiting.getMessage());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]containerState.getRunning() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStateRunning running = [CtInvocationImpl][CtVariableReadImpl]containerState.getRunning();
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"{state=running, startedAt=%s}", [CtInvocationImpl][CtVariableReadImpl]running.getStartedAt());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]containerState.getTerminated() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1ContainerStateTerminated terminated = [CtInvocationImpl][CtVariableReadImpl]containerState.getTerminated();
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"{state=terminated, startedAt=%s, finishedAt=%s, reason=%s, message=%s}", [CtInvocationImpl][CtVariableReadImpl]terminated.getStartedAt(), [CtInvocationImpl][CtVariableReadImpl]terminated.getFinishedAt(), [CtInvocationImpl][CtVariableReadImpl]terminated.getReason(), [CtInvocationImpl][CtVariableReadImpl]terminated.getMessage());
        }
        [CtReturnImpl]return [CtLiteralImpl]"{state=<not set>}";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a job has an availability zone hard constraint with a farzone id, return this farzone id.
     */
    public static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> findFarzoneId([CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.master.mesos.kubeapiserver.direct.DirectKubeConfiguration configuration, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.api.jobmanager.model.job.Job job) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> farzones = [CtInvocationImpl][CtVariableReadImpl]configuration.getFarzones();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.CollectionsExt.isNullOrEmpty([CtVariableReadImpl]farzones)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> hardConstraints = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]job.getJobDescriptor().getContainer().getHardConstraints();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String zone = [CtInvocationImpl][CtVariableReadImpl]hardConstraints.get([CtTypeAccessImpl]JobConstraints.AVAILABILITY_ZONE);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.StringExt.isEmpty([CtVariableReadImpl]zone)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String farzone : [CtVariableReadImpl]farzones) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]zone.equalsIgnoreCase([CtVariableReadImpl]farzone)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]farzone);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isOwnedByKubeScheduler([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Pod v1Pod) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.kubernetes.client.models.V1Toleration> tolerations = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v1Pod.getSpec().getTolerations();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.CollectionsExt.isNullOrEmpty([CtVariableReadImpl]tolerations)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Toleration toleration : [CtVariableReadImpl]tolerations) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]KubeConstants.TAINT_SCHEDULER.equals([CtInvocationImpl][CtVariableReadImpl]toleration.getKey()) && [CtInvocationImpl][CtTypeAccessImpl]KubeConstants.TAINT_SCHEDULER_VALUE_KUBE.equals([CtInvocationImpl][CtVariableReadImpl]toleration.getValue())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getNodeIpV4Address([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.models.V1Node node) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.getStatus().getAddresses().stream().filter([CtLambdaImpl]([CtParameterImpl] a) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.getType().equalsIgnoreCase([CtFieldReadImpl][CtFieldReferenceImpl]INTERNAL_IP) && [CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.NetworkExt.isIpV4([CtInvocationImpl][CtVariableReadImpl]a.getAddress())).findFirst().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1NodeAddress::getAddress).orElse([CtLiteralImpl]"UnknownIpAddress");
    }
}
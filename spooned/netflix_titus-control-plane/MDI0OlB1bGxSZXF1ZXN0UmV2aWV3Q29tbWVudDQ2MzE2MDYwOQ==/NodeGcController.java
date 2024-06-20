[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Netflix, Inc.

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
[CtPackageDeclarationImpl]package com.netflix.titus.master.kubernetes.controller;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.netflix.spectator.api.Gauge;
[CtUnresolvedImport]import static com.netflix.titus.master.mesos.kubeapiserver.KubeConstants.READY;
[CtUnresolvedImport]import com.netflix.titus.api.agent.service.AgentManagementService;
[CtUnresolvedImport]import com.netflix.titus.master.MetricConstants;
[CtUnresolvedImport]import com.netflix.titus.common.util.StringExt;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1Node;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import com.netflix.titus.api.agent.model.InstanceLifecycleState;
[CtUnresolvedImport]import com.netflix.titus.master.mesos.kubeapiserver.KubeUtil;
[CtUnresolvedImport]import io.kubernetes.client.openapi.ApiException;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.netflix.titus.common.util.ExecutorsExt;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import com.netflix.titus.api.agent.model.AgentInstance;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import com.google.gson.JsonSyntaxException;
[CtUnresolvedImport]import org.joda.time.DateTime;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import com.netflix.titus.common.util.guice.annotation.Activator;
[CtImportImpl]import javax.inject.Singleton;
[CtUnresolvedImport]import static com.netflix.titus.master.mesos.kubeapiserver.KubeConstants.BACKGROUND;
[CtUnresolvedImport]import static com.netflix.titus.master.mesos.kubeapiserver.KubeConstants.NOT_FOUND;
[CtUnresolvedImport]import com.netflix.titus.common.framework.scheduler.model.ScheduleDescriptor;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1NodeCondition;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import com.netflix.titus.common.runtime.TitusRuntime;
[CtUnresolvedImport]import com.netflix.titus.master.mesos.kubeapiserver.client.KubeApiFacade;
[CtUnresolvedImport]import com.netflix.titus.common.util.time.Clock;
[CtUnresolvedImport]import com.netflix.titus.common.framework.scheduler.LocalScheduler;
[CtClassImpl][CtJavaDocImpl]/**
 * Responsible for deleting Kubernetes node objects when they no longer exist in agent management. This will eventually be moved
 * to a new component.
 */
[CtAnnotationImpl]@javax.inject.Singleton
public class NodeGcController {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String METRIC_ROOT = [CtBinaryOperatorImpl][CtFieldReadImpl]com.netflix.titus.master.MetricConstants.METRIC_KUBERNETES_CONTROLLER + [CtLiteralImpl]"nodeGcController.";

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.titus.master.kubernetes.controller.KubeControllerConfiguration configuration;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.titus.api.agent.service.AgentManagementService agentManagementService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.titus.master.mesos.kubeapiserver.client.KubeApiFacade kubeApiFacade;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.titus.common.framework.scheduler.LocalScheduler scheduler;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.titus.common.util.time.Clock clock;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.netflix.spectator.api.Gauge nodesToGc;

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    public NodeGcController([CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.master.kubernetes.controller.KubeControllerConfiguration configuration, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.common.runtime.TitusRuntime titusRuntime, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.api.agent.service.AgentManagementService agentManagementService, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.master.mesos.kubeapiserver.client.KubeApiFacade kubeApiFacade, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.titus.common.framework.scheduler.LocalScheduler scheduler) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configuration = [CtVariableReadImpl]configuration;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.agentManagementService = [CtVariableReadImpl]agentManagementService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kubeApiFacade = [CtVariableReadImpl]kubeApiFacade;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scheduler = [CtVariableReadImpl]scheduler;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.clock = [CtInvocationImpl][CtVariableReadImpl]titusRuntime.getClock();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.nodesToGc = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]titusRuntime.getRegistry().gauge([CtBinaryOperatorImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.METRIC_ROOT + [CtLiteralImpl]"nodesToGc");
    }

    [CtMethodImpl][CtAnnotationImpl]@com.netflix.titus.common.util.guice.annotation.Activator
    public [CtTypeReferenceImpl]void enterActiveMode() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.titus.common.framework.scheduler.model.ScheduleDescriptor reconcileSchedulerDescriptor = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.framework.scheduler.model.ScheduleDescriptor.newBuilder().withName([CtLiteralImpl]"nodeGcController").withDescription([CtLiteralImpl]"GC nodes that no longer exist").withInitialDelay([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtInvocationImpl][CtFieldReadImpl]configuration.getNodeGcControllerInitialDelayMs())).withInterval([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtInvocationImpl][CtFieldReadImpl]configuration.getNodeGcControllerIntervalMs())).withTimeout([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtInvocationImpl][CtFieldReadImpl]configuration.getNodeGcControllerTimeoutMs())).build();
        [CtInvocationImpl][CtFieldReadImpl]scheduler.schedule([CtVariableReadImpl]reconcileSchedulerDescriptor, [CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl]gcNodes(), [CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.ExecutorsExt.namedSingleThreadExecutor([CtLiteralImpl]"node-gc-controller"));
    }

    [CtMethodImpl][CtTypeReferenceImpl]void gcNodes() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]kubeApiFacade.getNodeInformer().hasSynced()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.info([CtLiteralImpl]"Node informer has not synced");
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]configuration.isNodeGcControllerEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.info([CtLiteralImpl]"Node GC is disabled");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Node> nodes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]kubeApiFacade.getNodeInformer().getIndexer().list();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> nodeNamesToGc = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nodes.stream().filter([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::isNodeEligibleForGc).map([CtLambdaImpl]([CtParameterImpl] n) -> [CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.getMetadataName([CtInvocationImpl][CtVariableReadImpl]n.getMetadata())).filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]StringExt::isNotEmpty).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.info([CtLiteralImpl]"Attempting to GC {} nodes: {}", [CtInvocationImpl][CtVariableReadImpl]nodeNamesToGc.size(), [CtVariableReadImpl]nodeNamesToGc);
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.nodesToGc.set([CtInvocationImpl][CtVariableReadImpl]nodeNamesToGc.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nodeName : [CtVariableReadImpl]nodeNamesToGc) [CtBlockImpl]{
            [CtInvocationImpl]gcNode([CtVariableReadImpl]nodeName);
        }
        [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.info([CtLiteralImpl]"Finished node GC");
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtTypeReferenceImpl]boolean isNodeEligibleForGc([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Node node) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nodeName = [CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.getMetadataName([CtInvocationImpl][CtVariableReadImpl]node.getMetadata());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1NodeCondition> readyNodeConditionOpt = [CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.master.mesos.kubeapiserver.KubeUtil.findNodeCondition([CtVariableReadImpl]node, [CtTypeAccessImpl]com.netflix.titus.master.kubernetes.controller.READY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.StringExt.isNotEmpty([CtVariableReadImpl]nodeName) && [CtInvocationImpl][CtVariableReadImpl]readyNodeConditionOpt.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1NodeCondition readyNodeCondition = [CtInvocationImpl][CtVariableReadImpl]readyNodeConditionOpt.get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isReadyConditionTimestampPastGracePeriod = [CtInvocationImpl]hasConditionGracePeriodElapsed([CtVariableReadImpl]readyNodeCondition, [CtInvocationImpl][CtFieldReadImpl]configuration.getNodeGcGracePeriodMs());
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isAgentInstanceNotAvailable = [CtInvocationImpl]isAgentInstanceNotAvailable([CtVariableReadImpl]nodeName);
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]isReadyConditionTimestampPastGracePeriod && [CtVariableReadImpl]isAgentInstanceNotAvailable;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasConditionGracePeriodElapsed([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1NodeCondition condition, [CtParameterImpl][CtTypeReferenceImpl]long gracePeriodMs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime lastHeartbeatTime = [CtInvocationImpl][CtVariableReadImpl]condition.getLastHeartbeatTime();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]lastHeartbeatTime != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]clock.isPast([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lastHeartbeatTime.getMillis() + [CtVariableReadImpl]gracePeriodMs);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isAgentInstanceNotAvailable([CtParameterImpl][CtTypeReferenceImpl]java.lang.String nodeName) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.StringExt.isEmpty([CtVariableReadImpl]nodeName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.netflix.titus.api.agent.model.AgentInstance> agentInstanceOpt = [CtInvocationImpl][CtFieldReadImpl]agentManagementService.findAgentInstance([CtVariableReadImpl]nodeName);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]agentInstanceOpt.isPresent()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.titus.api.agent.model.AgentInstance agentInstance = [CtInvocationImpl][CtVariableReadImpl]agentInstanceOpt.get();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]agentInstance.getLifecycleStatus().getState() == [CtFieldReadImpl]com.netflix.titus.api.agent.model.InstanceLifecycleState.Stopped;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void gcNode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String nodeName) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.titus.common.util.StringExt.isNotEmpty([CtVariableReadImpl]nodeName)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]kubeApiFacade.getCoreV1Api().deleteNode([CtVariableReadImpl]nodeName, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]null, [CtTypeAccessImpl]com.netflix.titus.master.kubernetes.controller.BACKGROUND, [CtLiteralImpl]null);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonSyntaxException e) [CtBlockImpl]{
                [CtCommentImpl]// this is probably successful. the generated client has the wrong response type
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.ApiException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().equalsIgnoreCase([CtTypeAccessImpl]com.netflix.titus.master.kubernetes.controller.NOT_FOUND)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.error([CtLiteralImpl]"Failed to delete node: {} with error: ", [CtVariableReadImpl]nodeName, [CtVariableReadImpl]e);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.netflix.titus.master.kubernetes.controller.NodeGcController.logger.error([CtLiteralImpl]"Failed to delete node: {} with error: ", [CtVariableReadImpl]nodeName, [CtVariableReadImpl]e);
            }
        }
    }
}
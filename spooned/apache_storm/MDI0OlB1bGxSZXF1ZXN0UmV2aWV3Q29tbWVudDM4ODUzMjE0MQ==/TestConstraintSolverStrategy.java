[CompilationUnitImpl][CtJavaDocImpl]/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.storm.scheduler.resource.strategies.scheduling;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.NavigableMap;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.runners.Parameterized.Parameters;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler.*;
[CtUnresolvedImport]import org.apache.storm.scheduler.Topologies;
[CtUnresolvedImport]import org.apache.storm.DaemonConfig;
[CtUnresolvedImport]import org.json.simple.JSONValue;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.normalization.ResourceMetrics;
[CtUnresolvedImport]import org.apache.storm.scheduler.WorkerSlot;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.apache.storm.scheduler.SchedulerAssignmentImpl;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.junit.runners.Parameterized;
[CtUnresolvedImport]import org.apache.storm.metric.StormMetricsRegistry;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import org.apache.storm.scheduler.SchedulerAssignment;
[CtUnresolvedImport]import org.apache.storm.scheduler.TopologyDetails;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.apache.storm.scheduler.SupervisorDetails;
[CtUnresolvedImport]import org.apache.storm.utils.Utils;
[CtUnresolvedImport]import org.apache.storm.scheduler.Cluster;
[CtUnresolvedImport]import org.apache.storm.utils.Time;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.ResourceAwareScheduler;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.SchedulingResult;
[CtUnresolvedImport]import org.json.simple.JSONObject;
[CtUnresolvedImport]import org.apache.storm.Config;
[CtUnresolvedImport]import org.junit.Assert;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.apache.storm.scheduler.ExecutorDetails;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.Parameterized.class)
public class TestConstraintSolverStrategy {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.runners.Parameterized.Parameters
    public static [CtArrayTypeReferenceImpl]java.lang.Object[] data() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]false, [CtLiteralImpl]true };
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_TRAVERSAL_DEPTH = [CtLiteralImpl]2000;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int NORMAL_BOLT_PARALLEL = [CtLiteralImpl]11;

    [CtFieldImpl][CtCommentImpl]// Dropping the parallelism of the bolts to 3 instead of 11 so we can find a solution in a reasonable amount of work when backtracking.
    private static final [CtTypeReferenceImpl]int BACKTRACK_BOLT_PARALLEL = [CtLiteralImpl]3;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int CO_LOCATION_CNT = [CtLiteralImpl]2;

    [CtFieldImpl][CtCommentImpl]// class members
    public [CtTypeReferenceImpl]java.lang.Boolean consolidatedConfigFlag = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE;

    [CtConstructorImpl]public TestConstraintSolverStrategy([CtParameterImpl][CtTypeReferenceImpl]boolean consolidatedConfigFlag) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.consolidatedConfigFlag = [CtVariableReadImpl]consolidatedConfigFlag;
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Running tests with consolidatedConfigFlag={}", [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtVariableReadImpl]consolidatedConfigFlag);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper function to add a constraint specifying two components that cannot co-exist.
     * Note that it is redundant to specify the converse.
     *
     * @param comp1
     * 		first component name
     * @param comp2
     * 		second component name
     * @param constraints
     * 		the resulting constraint list of lists which is updated
     */
    public static [CtTypeReferenceImpl]void addConstraints([CtParameterImpl][CtTypeReferenceImpl]java.lang.String comp1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String comp2, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]java.lang.String> constraintPair = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl][CtVariableReadImpl]constraintPair.add([CtVariableReadImpl]comp1);
        [CtInvocationImpl][CtVariableReadImpl]constraintPair.add([CtVariableReadImpl]comp2);
        [CtInvocationImpl][CtVariableReadImpl]constraints.add([CtVariableReadImpl]constraintPair);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Make test Topology configuration, but with the newer spread constraints that allow associating a number
     * with the spread. This number represents the maximum co-located component count. Default under the old
     * configuration is assumed to be 1.
     *
     * @param maxCoLocationCnt
     * 		Maximum co-located component (spout-0), minimum value is 1.
     * @return  */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> makeTestTopoConf([CtParameterImpl][CtTypeReferenceImpl]int maxCoLocationCnt) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxCoLocationCnt < [CtLiteralImpl]1) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]maxCoLocationCnt = [CtLiteralImpl]1;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"spout-0", [CtLiteralImpl]"bolt-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-2", [CtLiteralImpl]"spout-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-2", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"spout-0", [CtVariableReadImpl]constraints);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> spreads = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]spreads.put([CtLiteralImpl]"spout-0", [CtVariableReadImpl]maxCoLocationCnt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.readDefaultConfig();
        [CtInvocationImpl]setConstraintConfig([CtVariableReadImpl]constraints, [CtVariableReadImpl]spreads, [CtVariableReadImpl]config);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]DaemonConfig.RESOURCE_AWARE_SCHEDULER_MAX_STATE_SEARCH, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.MAX_TRAVERSAL_DEPTH);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.MAX_TRAVERSAL_DEPTH);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_WORKER_MAX_HEAP_SIZE_MB, [CtLiteralImpl]100000);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_PRIORITY, [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_CPU_PCORE_PERCENT, [CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_RESOURCES_ONHEAP_MEMORY_MB, [CtLiteralImpl]100);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_RESOURCES_OFFHEAP_MEMORY_MB, [CtLiteralImpl]0.0);
        [CtReturnImpl]return [CtVariableReadImpl]config;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set Config.TOPOLOGY_RAS_CONSTRAINTS (when consolidatedConfigFlag is true) or both
     * Config.TOPOLOGY_RAS_CONSTRAINTS/Config.TOPOLOGY_SPREAD_COMPONENTS (when consolidatedConfigFlag is false).
     *
     * When consolidatedConfigFlag when true, use the new more consolidated format to set Config.TOPOLOGY_RAS_CONSTRAINTS.
     * When false, use the old configuration format for Config.TOPOLOGY_RAS_CONSTRAINTS/TOPOLOGY_SPREAD_COMPONENTS.
     *
     * @param constraints
     * 		List of components, where the first one cannot co-exist with the others in the list
     * @param spreads
     * 		Map of component and its maxCoLocationCnt
     * @param config
     * 		Configuration to be updated
     */
    private [CtTypeReferenceImpl]void setConstraintConfig([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> spreads, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]consolidatedConfigFlag) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// single configuration for each component
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> modifiedConstraints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> constraint : [CtVariableReadImpl]constraints) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]constraint.size() < [CtLiteralImpl]2) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String comp = [CtInvocationImpl][CtVariableReadImpl]constraint.get([CtLiteralImpl]0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> others = [CtInvocationImpl][CtVariableReadImpl]constraint.subList([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]constraint.size());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> incompatibleComponents = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object>) ([CtInvocationImpl][CtVariableReadImpl]modifiedConstraints.computeIfAbsent([CtVariableReadImpl]comp, [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>()).computeIfAbsent([CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_INCOMPATIBLE_COMPONENTS, [CtLambdaImpl]([CtParameterImpl] k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]ArrayList<[CtTypeReferenceImpl]java.lang.Object>())));
                [CtInvocationImpl][CtVariableReadImpl]incompatibleComponents.addAll([CtVariableReadImpl]others);
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String comp : [CtInvocationImpl][CtVariableReadImpl]spreads.keySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modifiedConstraints.computeIfAbsent([CtVariableReadImpl]comp, [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>()).put([CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_MAX_NODE_CO_LOCATION_CNT, [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]spreads.get([CtVariableReadImpl]comp));
            }
            [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINTS, [CtVariableReadImpl]modifiedConstraints);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// constraint and MaxCoLocationCnts are separate - no maxCoLocationCnt implied as 1
            [CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINTS, [CtVariableReadImpl]constraints);
            [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_SPREAD_COMPONENTS, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]spreads.entrySet().stream().map([CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList()));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set Config.TOPOLOGY_RAS_CONSTRAINTS (when consolidated is true) or both
     * Config.TOPOLOGY_RAS_CONSTRAINTS/Config.TOPOLOGY_SPREAD_COMPONENTS (when consolidated is false).
     *
     * When consolidatedConfigFlag when true, use the new more consolidated format to set Config.TOPOLOGY_RAS_CONSTRAINTS.
     * When false, use the old configuration format for Config.TOPOLOGY_RAS_CONSTRAINTS/TOPOLOGY_SPREAD_COMPONENTS.
     *
     * @param constraints
     * 		List of components, where the first one cannot co-exist with the others in the list
     * @param spreads
     * 		List of components that can have only one Executor on a node (i.e. their maxCoLocationCnt = 1)
     * @param config
     * 		Configuration to be updated
     */
    private [CtTypeReferenceImpl]void setConstraintConfig([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> spreads, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]consolidatedConfigFlag) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// single configuration for each component
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> modifiedConstraints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> constraint : [CtVariableReadImpl]constraints) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]constraint.size() < [CtLiteralImpl]2) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String comp = [CtInvocationImpl][CtVariableReadImpl]constraint.get([CtLiteralImpl]0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> others = [CtInvocationImpl][CtVariableReadImpl]constraint.subList([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]constraint.size());
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modifiedConstraints.computeIfAbsent([CtVariableReadImpl]comp, [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>()).addAll([CtVariableReadImpl]others);
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String comp : [CtVariableReadImpl]spreads) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modifiedConstraints.computeIfAbsent([CtVariableReadImpl]comp, [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>()).add([CtLiteralImpl]"1");
            }
            [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINTS, [CtVariableReadImpl]modifiedConstraints);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// constraint and MaxCoLocationCnts are separate - maxCoLocationCnt implied as 1
            [CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINTS, [CtVariableReadImpl]constraints);
            [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_SPREAD_COMPONENTS, [CtVariableReadImpl]spreads);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> makeTestTopoConf() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]makeTestTopoConf([CtLiteralImpl]1);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails makeTopology([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config, [CtParameterImpl][CtTypeReferenceImpl]int boltParallel) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]genTopology([CtLiteralImpl]"testTopo", [CtVariableReadImpl]config, [CtLiteralImpl]1, [CtLiteralImpl]4, [CtLiteralImpl]4, [CtVariableReadImpl]boltParallel, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]"user");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster makeCluster([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]makeCluster([CtVariableReadImpl]topologies, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster makeCluster([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> supMap) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]supMap == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]supMap = [CtInvocationImpl]genSupervisors([CtLiteralImpl]4, [CtLiteralImpl]2, [CtLiteralImpl]120, [CtLiteralImpl]1200);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.readDefaultConfig();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster([CtConstructorCallImpl]new [CtTypeReferenceImpl]INimbusTest(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.normalization.ResourceMetrics([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.metric.StormMetricsRegistry()), [CtVariableReadImpl]supMap, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>(), [CtVariableReadImpl]topologies, [CtVariableReadImpl]config);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void basicUnitTestWithKillAndRecover([CtParameterImpl][CtTypeReferenceImpl]ConstraintSolverStrategy cs, [CtParameterImpl][CtTypeReferenceImpl]int boltParallel, [CtParameterImpl][CtTypeReferenceImpl]int coLocationCnt) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl]makeTestTopoConf([CtVariableReadImpl]coLocationCnt);
        [CtInvocationImpl][CtVariableReadImpl]cs.prepare([CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topo = [CtInvocationImpl]makeTopology([CtVariableReadImpl]config, [CtVariableReadImpl]boltParallel);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies([CtVariableReadImpl]topo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster cluster = [CtInvocationImpl]makeCluster([CtVariableReadImpl]topologies);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Scheduling...");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.resource.SchedulingResult result = [CtInvocationImpl][CtVariableReadImpl]cs.schedule([CtVariableReadImpl]cluster, [CtVariableReadImpl]topo);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Done scheduling {}...", [CtVariableReadImpl]result);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"Assert scheduling topology success " + [CtVariableReadImpl]result, [CtInvocationImpl][CtVariableReadImpl]result.isSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"topo all executors scheduled? " + [CtInvocationImpl][CtVariableReadImpl]cluster.getUnassignedExecutors([CtVariableReadImpl]topo), [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getUnassignedExecutors([CtVariableReadImpl]topo).size());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"Valid Scheduling?", [CtInvocationImpl][CtTypeAccessImpl]ConstraintSolverStrategy.validateSolution([CtVariableReadImpl]cluster, [CtVariableReadImpl]topo, [CtLiteralImpl]null));
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Slots Used {}", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getAssignmentById([CtInvocationImpl][CtVariableReadImpl]topo.getId()).getSlots());
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Assignment {}", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getAssignmentById([CtInvocationImpl][CtVariableReadImpl]topo.getId()).getSlotToExecutors());
        [CtLocalVariableImpl][CtCommentImpl]// simulate worker loss
        [CtTypeReferenceImpl]org.apache.storm.scheduler.SchedulerAssignment assignment = [CtInvocationImpl][CtVariableReadImpl]cluster.getAssignmentById([CtInvocationImpl][CtVariableReadImpl]topo.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> slotsToDelete = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> slots = [CtInvocationImpl][CtVariableReadImpl]assignment.getSlots();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot slot : [CtVariableReadImpl]slots) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i % [CtLiteralImpl]2) == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]slotsToDelete.add([CtVariableReadImpl]slot);
            }
            [CtUnaryOperatorImpl][CtVariableWriteImpl]i++;
        }
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"KILL WORKER(s) {}", [CtVariableReadImpl]slotsToDelete);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot slot : [CtVariableReadImpl]slotsToDelete) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cluster.freeSlot([CtVariableReadImpl]slot);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]cs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy();
        [CtInvocationImpl][CtVariableReadImpl]cs.prepare([CtVariableReadImpl]config);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Scheduling again...");
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]cs.schedule([CtVariableReadImpl]cluster, [CtVariableReadImpl]topo);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Done scheduling {}...", [CtVariableReadImpl]result);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"Assert scheduling topology success " + [CtVariableReadImpl]result, [CtInvocationImpl][CtVariableReadImpl]result.isSuccess());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"topo all executors scheduled?", [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getUnassignedExecutors([CtVariableReadImpl]topo).size());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"Valid Scheduling?", [CtInvocationImpl][CtTypeAccessImpl]ConstraintSolverStrategy.validateSolution([CtVariableReadImpl]cluster, [CtVariableReadImpl]topo, [CtLiteralImpl]null));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNewConstraintFormat() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"{ \"comp-1\": " + [CtLiteralImpl]"                  { \"%s\": 2, ") + [CtLiteralImpl]"                    \"%s\": [\"comp-2\", \"comp-3\" ] }, ") + [CtLiteralImpl]"     \"comp-2\": ") + [CtLiteralImpl]"                  { \"%s\": [ \"comp-4\" ] }, ") + [CtLiteralImpl]"     \"comp-3\": ") + [CtLiteralImpl]"                  { \"%s\": \"comp-5\" } ") + [CtLiteralImpl]"}", [CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_MAX_NODE_CO_LOCATION_CNT, [CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_INCOMPATIBLE_COMPONENTS, [CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_INCOMPATIBLE_COMPONENTS, [CtTypeAccessImpl]ConstraintSolverStrategy.CONSTRAINT_TYPE_INCOMPATIBLE_COMPONENTS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object jsonValue = [CtInvocationImpl][CtTypeAccessImpl]org.json.simple.JSONValue.parse([CtVariableReadImpl]s);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.readDefaultConfig();
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINTS, [CtVariableReadImpl]jsonValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> allComps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]allComps.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"comp-1", [CtLiteralImpl]"comp-2", [CtLiteralImpl]"comp-3", [CtLiteralImpl]"comp-4", [CtLiteralImpl]"comp-5"));
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]ConstraintSolverStrategy.ConstraintConfig constraintConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]ConstraintSolverStrategy.ConstraintConfig([CtVariableReadImpl]config, [CtVariableReadImpl]allComps);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> expectedSetComp1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]expectedSetComp1.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"comp-2", [CtLiteralImpl]"comp-3"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> expectedSetComp2 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]expectedSetComp2.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"comp-1", [CtLiteralImpl]"comp-4"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> expectedSetComp3 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]expectedSetComp3.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"comp-1", [CtLiteralImpl]"comp-5"));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"comp-1 incompatible components", [CtVariableReadImpl]expectedSetComp1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraintConfig.getIncompatibleComponents().get([CtLiteralImpl]"comp-1"));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"comp-2 incompatible components", [CtVariableReadImpl]expectedSetComp2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraintConfig.getIncompatibleComponents().get([CtLiteralImpl]"comp-2"));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"comp-3 incompatible components", [CtVariableReadImpl]expectedSetComp3, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraintConfig.getIncompatibleComponents().get([CtLiteralImpl]"comp-3"));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"comp-1 maxNodeCoLocationCnt", [CtLiteralImpl](([CtTypeReferenceImpl]int) (2)), [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]constraintConfig.getMaxCoLocationCnts().getOrDefault([CtLiteralImpl]"comp-1", [CtUnaryOperatorImpl]-[CtLiteralImpl]1))));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtLiteralImpl]"comp-2 maxNodeCoLocationCnt", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constraintConfig.getMaxCoLocationCnts().get([CtLiteralImpl]"comp-2"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testConstraintSolverForceBacktrack() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The best way to force backtracking is to change the heuristic so the components are reversed, so it is hard
        [CtCommentImpl]// to find an answer.
        [CtTypeReferenceImpl]ConstraintSolverStrategy cs = [CtNewClassImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public <[CtTypeParameterImpl]K extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]K>, [CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]java.util.NavigableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> sortByValues([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> map) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtSuperAccessImpl]super.sortByValues([CtVariableReadImpl]map).descendingMap();
            }
        };
        [CtInvocationImpl]basicUnitTestWithKillAndRecover([CtVariableReadImpl]cs, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.BACKTRACK_BOLT_PARALLEL, [CtLiteralImpl]2);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testConstraintSolverForceBacktrackWithSpreadCoLocation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The best way to force backtracking is to change the heuristic so the components are reversed, so it is hard
        [CtCommentImpl]// to find an answer.
        [CtTypeReferenceImpl]ConstraintSolverStrategy cs = [CtNewClassImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public <[CtTypeParameterImpl]K extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]K>, [CtTypeParameterImpl]V extends [CtTypeReferenceImpl]java.lang.Comparable<[CtTypeParameterReferenceImpl]V>> [CtTypeReferenceImpl]java.util.NavigableMap<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> sortByValues([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]K, [CtTypeParameterReferenceImpl]V> map) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtSuperAccessImpl]super.sortByValues([CtVariableReadImpl]map).descendingMap();
            }
        };
        [CtInvocationImpl]basicUnitTestWithKillAndRecover([CtVariableReadImpl]cs, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.BACKTRACK_BOLT_PARALLEL, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.CO_LOCATION_CNT);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testConstraintSolver() [CtBlockImpl]{
        [CtInvocationImpl]basicUnitTestWithKillAndRecover([CtConstructorCallImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy(), [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.NORMAL_BOLT_PARALLEL, [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testConstraintSolverWithSpreadCoLocation() [CtBlockImpl]{
        [CtInvocationImpl]basicUnitTestWithKillAndRecover([CtConstructorCallImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy(), [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.NORMAL_BOLT_PARALLEL, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.CO_LOCATION_CNT);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void basicFailureTest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String confKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object confValue, [CtParameterImpl][CtTypeReferenceImpl]ConstraintSolverStrategy cs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl]makeTestTopoConf();
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtVariableReadImpl]confKey, [CtVariableReadImpl]confValue);
        [CtInvocationImpl][CtVariableReadImpl]cs.prepare([CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topo = [CtInvocationImpl]makeTopology([CtVariableReadImpl]config, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.NORMAL_BOLT_PARALLEL);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies([CtVariableReadImpl]topo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster cluster = [CtInvocationImpl]makeCluster([CtVariableReadImpl]topologies);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Scheduling...");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.resource.SchedulingResult result = [CtInvocationImpl][CtVariableReadImpl]cs.schedule([CtVariableReadImpl]cluster, [CtVariableReadImpl]topo);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Done scheduling {}...", [CtVariableReadImpl]result);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"Assert scheduling topology success " + [CtVariableReadImpl]result, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]result.isSuccess());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTooManyStateTransitions() [CtBlockImpl]{
        [CtInvocationImpl]basicFailureTest([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH, [CtLiteralImpl]10, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTimeout() [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.storm.utils.Time.SimulatedTime simulating = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.storm.utils.Time.SimulatedTime()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]ConstraintSolverStrategy cs = [CtNewClassImpl]new [CtTypeReferenceImpl]ConstraintSolverStrategy()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                protected [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.strategies.scheduling.SolverResult backtrackSearch([CtParameterImpl][CtTypeReferenceImpl]SearcherState state) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// Each time we try to schedule a new component simulate taking 1 second longer
                    [CtTypeAccessImpl]org.apache.storm.utils.Time.advanceTime([CtLiteralImpl]1001);
                    [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.backtrackSearch([CtVariableReadImpl]state);
                }
            };
            [CtInvocationImpl]basicFailureTest([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_TIME_SECS, [CtLiteralImpl]1, [CtVariableReadImpl]cs);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testScheduleLargeExecutorConstraintCountSmall() [CtBlockImpl]{
        [CtInvocationImpl]testScheduleLargeExecutorConstraintCount([CtLiteralImpl]1);
    }

    [CtMethodImpl][CtCommentImpl]/* Test scheduling large number of executors and constraints.
    This test can succeed only with new style config that allows maxCoLocationCnt = parallelismMultiplier.
    In prior code, this test would succeed because effectively the old code did not properly enforce the
    SPREAD constraint.

    Cluster has sufficient resources for scheduling to succeed but can fail due to StackOverflowError.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testScheduleLargeExecutorConstraintCountLarge() [CtBlockImpl]{
        [CtInvocationImpl]testScheduleLargeExecutorConstraintCount([CtLiteralImpl]20);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testScheduleLargeExecutorConstraintCount([CtParameterImpl][CtTypeReferenceImpl]int parallelismMultiplier) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]parallelismMultiplier > [CtLiteralImpl]1) && [CtUnaryOperatorImpl](![CtFieldReadImpl]consolidatedConfigFlag)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertFalse([CtBinaryOperatorImpl][CtLiteralImpl]"Large parallelism test requires new consolidated constraint format with maxCoLocationCnt=" + [CtVariableReadImpl]parallelismMultiplier, [CtFieldReadImpl]consolidatedConfigFlag);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Add 1 topology with large number of executors and constraints. Too many can cause a java.lang.StackOverflowError
        [CtTypeReferenceImpl]org.apache.storm.Config config = [CtInvocationImpl]createCSSClusterConfig([CtLiteralImpl]10, [CtLiteralImpl]10, [CtLiteralImpl]0, [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH, [CtLiteralImpl]50000);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_TIME_SECS, [CtLiteralImpl]120);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]DaemonConfig.SCHEDULING_TIMEOUT_SECONDS_PER_TOPOLOGY, [CtLiteralImpl]120);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"spout-0", [CtLiteralImpl]"spout-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-1", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"spout-0", [CtLiteralImpl]"bolt-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-2", [CtLiteralImpl]"spout-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-2", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"spout-0", [CtVariableReadImpl]constraints);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> spreads = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]spreads.put([CtLiteralImpl]"spout-0", [CtVariableReadImpl]parallelismMultiplier);
        [CtInvocationImpl][CtVariableReadImpl]spreads.put([CtLiteralImpl]"bolt-1", [CtVariableReadImpl]parallelismMultiplier);
        [CtInvocationImpl]setConstraintConfig([CtVariableReadImpl]constraints, [CtVariableReadImpl]spreads, [CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topo = [CtInvocationImpl]genTopology([CtBinaryOperatorImpl][CtLiteralImpl]"testTopo-" + [CtVariableReadImpl]parallelismMultiplier, [CtVariableReadImpl]config, [CtLiteralImpl]10, [CtLiteralImpl]10, [CtBinaryOperatorImpl][CtLiteralImpl]30 * [CtVariableReadImpl]parallelismMultiplier, [CtBinaryOperatorImpl][CtLiteralImpl]30 * [CtVariableReadImpl]parallelismMultiplier, [CtLiteralImpl]31414, [CtLiteralImpl]0, [CtLiteralImpl]"user");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies([CtVariableReadImpl]topo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> supMap = [CtInvocationImpl]genSupervisors([CtBinaryOperatorImpl][CtLiteralImpl]30 * [CtVariableReadImpl]parallelismMultiplier, [CtLiteralImpl]30, [CtLiteralImpl]3500, [CtLiteralImpl]35000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster cluster = [CtInvocationImpl]makeCluster([CtVariableReadImpl]topologies, [CtVariableReadImpl]supMap);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler scheduler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler();
        [CtInvocationImpl][CtVariableReadImpl]scheduler.prepare([CtVariableReadImpl]config);
        [CtInvocationImpl][CtVariableReadImpl]scheduler.schedule([CtVariableReadImpl]topologies, [CtVariableReadImpl]cluster);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean scheduleSuccess = [CtInvocationImpl]isStatusSuccess([CtInvocationImpl][CtVariableReadImpl]cluster.getStatus([CtInvocationImpl][CtVariableReadImpl]topo.getId()));
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"testScheduleLargeExecutorCount scheduling {} with {}x executor multiplier, consolidatedConfigFlag={}", [CtConditionalImpl][CtVariableReadImpl]scheduleSuccess ? [CtLiteralImpl]"succeeds" : [CtLiteralImpl]"fails", [CtVariableReadImpl]parallelismMultiplier, [CtFieldReadImpl]consolidatedConfigFlag);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtVariableReadImpl]scheduleSuccess);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIntegrationWithRAS() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]consolidatedConfigFlag) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.LOG.info([CtLiteralImpl]"Skipping test since bolt-1 maxCoLocationCnt=10 requires consolidatedConfigFlag=true, current={}", [CtFieldReadImpl]consolidatedConfigFlag);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.readDefaultConfig();
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_SCHEDULER_STRATEGY, [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.ConstraintSolverStrategy.class.getName());
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.MAX_TRAVERSAL_DEPTH);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_WORKER_MAX_HEAP_SIZE_MB, [CtLiteralImpl]100000);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_PRIORITY, [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_CPU_PCORE_PERCENT, [CtLiteralImpl]10);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_RESOURCES_ONHEAP_MEMORY_MB, [CtLiteralImpl]100);
        [CtInvocationImpl][CtVariableReadImpl]config.put([CtTypeAccessImpl]Config.TOPOLOGY_COMPONENT_RESOURCES_OFFHEAP_MEMORY_MB, [CtLiteralImpl]0.0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> constraints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"spout-0", [CtLiteralImpl]"bolt-0", [CtVariableReadImpl]constraints);
        [CtInvocationImpl][CtCommentImpl]// commented out unsatisfiable constraint since there are 300 executors and cannot fit on 30 nodes, added as spread
        [CtCommentImpl]// addConstraints("bolt-1", "bolt-1", constraints);
        org.apache.storm.scheduler.resource.strategies.scheduling.TestConstraintSolverStrategy.addConstraints([CtLiteralImpl]"bolt-1", [CtLiteralImpl]"bolt-2", [CtVariableReadImpl]constraints);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> spreads = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>();
        [CtInvocationImpl][CtVariableReadImpl]spreads.put([CtLiteralImpl]"spout-0", [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]spreads.put([CtLiteralImpl]"bolt-1", [CtLiteralImpl]10);
        [CtInvocationImpl]setConstraintConfig([CtVariableReadImpl]constraints, [CtVariableReadImpl]spreads, [CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topo = [CtInvocationImpl]genTopology([CtLiteralImpl]"testTopo", [CtVariableReadImpl]config, [CtLiteralImpl]2, [CtLiteralImpl]3, [CtLiteralImpl]30, [CtLiteralImpl]300, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]"user");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails> topoMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]topoMap.put([CtInvocationImpl][CtVariableReadImpl]topo.getId(), [CtVariableReadImpl]topo);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies([CtVariableReadImpl]topoMap);
        [CtLocalVariableImpl][CtCommentImpl]// Fails with 36 supervisors, works with 37
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> supMap = [CtInvocationImpl]genSupervisors([CtLiteralImpl]37, [CtLiteralImpl]16, [CtLiteralImpl]400, [CtBinaryOperatorImpl][CtLiteralImpl]1024 * [CtLiteralImpl]4);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster cluster = [CtInvocationImpl]makeCluster([CtVariableReadImpl]topologies, [CtVariableReadImpl]supMap);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler rs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler();
        [CtInvocationImpl][CtVariableReadImpl]rs.prepare([CtVariableReadImpl]config);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rs.schedule([CtVariableReadImpl]topologies, [CtVariableReadImpl]cluster);
            [CtInvocationImpl]assertStatusSuccess([CtVariableReadImpl]cluster, [CtInvocationImpl][CtVariableReadImpl]topo.getId());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"topo all executors scheduled?", [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getUnassignedExecutors([CtVariableReadImpl]topo).size());
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rs.cleanup();
        }
        [CtLocalVariableImpl][CtCommentImpl]// simulate worker loss
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.storm.scheduler.ExecutorDetails, [CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> newExecToSlot = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.storm.scheduler.ExecutorDetails, [CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> execToSlot = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getAssignmentById([CtInvocationImpl][CtVariableReadImpl]topo.getId()).getExecutorToSlot();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.apache.storm.scheduler.ExecutorDetails, [CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot>> it = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]execToSlot.entrySet().iterator();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]execToSlot.size() / [CtLiteralImpl]2); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.ExecutorDetails exec = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot ws = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().getValue();
            [CtInvocationImpl][CtVariableReadImpl]newExecToSlot.put([CtVariableReadImpl]exec, [CtVariableReadImpl]ws);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SchedulerAssignment> newAssignments = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]newAssignments.put([CtInvocationImpl][CtVariableReadImpl]topo.getId(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.SchedulerAssignmentImpl([CtInvocationImpl][CtVariableReadImpl]topo.getId(), [CtVariableReadImpl]newExecToSlot, [CtLiteralImpl]null, [CtLiteralImpl]null));
        [CtInvocationImpl][CtVariableReadImpl]cluster.setAssignments([CtVariableReadImpl]newAssignments, [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]rs.prepare([CtVariableReadImpl]config);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rs.schedule([CtVariableReadImpl]topologies, [CtVariableReadImpl]cluster);
            [CtInvocationImpl]assertStatusSuccess([CtVariableReadImpl]cluster, [CtInvocationImpl][CtVariableReadImpl]topo.getId());
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"topo all executors scheduled?", [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cluster.getUnassignedExecutors([CtVariableReadImpl]topo).size());
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rs.cleanup();
        }
    }
}
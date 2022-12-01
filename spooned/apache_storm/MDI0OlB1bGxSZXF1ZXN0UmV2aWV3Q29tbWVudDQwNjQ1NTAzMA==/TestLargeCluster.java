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
[CtUnresolvedImport]import org.apache.storm.scheduler.IScheduler;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.logging.log4j.Level;
[CtUnresolvedImport]import org.apache.storm.scheduler.INimbus;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.storm.scheduler.Topologies;
[CtUnresolvedImport]import org.apache.storm.DaemonConfig;
[CtUnresolvedImport]import org.junit.jupiter.api.extension.ExtendWith;
[CtUnresolvedImport]import org.apache.logging.log4j.core.config.Configurator;
[CtUnresolvedImport]import org.apache.storm.generated.StormTopology;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.normalization.ResourceMetrics;
[CtUnresolvedImport]import org.apache.storm.scheduler.WorkerSlot;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.normalization.NormalizedResourcesExtension;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.apache.storm.metric.StormMetricsRegistry;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.LinkedList;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.InputStreamReader;
[CtUnresolvedImport]import org.apache.storm.scheduler.TopologyDetails;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.io.ByteArrayOutputStream;
[CtUnresolvedImport]import org.apache.storm.scheduler.SupervisorDetails;
[CtUnresolvedImport]import org.apache.storm.utils.Utils;
[CtImportImpl]import java.util.TreeMap;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.normalization.NormalizedResources;
[CtUnresolvedImport]import org.apache.storm.scheduler.Cluster;
[CtUnresolvedImport]import org.apache.storm.utils.Time;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.ResourceAwareScheduler;
[CtUnresolvedImport]import org.apache.storm.utils.ObjectReader;
[CtUnresolvedImport]import org.junit.jupiter.api.AfterEach;
[CtUnresolvedImport]import org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.io.BufferedReader;
[CtUnresolvedImport]import org.apache.storm.Config;
[CtUnresolvedImport]import org.junit.Assert;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.apache.storm.scheduler.ExecutorDetails;
[CtClassImpl][CtAnnotationImpl]@org.junit.jupiter.api.extension.ExtendWith([CtNewArrayImpl]{ [CtFieldReadImpl]org.apache.storm.scheduler.resource.normalization.NormalizedResourcesExtension.class })
public class TestLargeCluster {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String TEST_CLUSTER_NAME = [CtLiteralImpl]"largeCluster01";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String TEST_RESOURCE_PATH = [CtBinaryOperatorImpl][CtLiteralImpl]"clusterconf/" + [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.TEST_CLUSTER_NAME;

    [CtFieldImpl]private static [CtTypeReferenceImpl]org.apache.storm.scheduler.IScheduler scheduler = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.AfterEach
    public [CtTypeReferenceImpl]void cleanup() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler.cleanup();
            [CtAssignmentImpl][CtFieldWriteImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the list of serialized topology (*code.ser) and configuration (*conf.ser)
     * resource files in the path. The resources are sorted so that paired topology and conf
     * files are sequential. Unpaired files may be ignored by the caller.
     *
     * @param path
     * 		directory in which resources exist.
     * @return  * @throws IOException
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getResourceFiles([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> fileNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceAsStream([CtVariableReadImpl]path);[CtLocalVariableImpl][CtTypeReferenceImpl]java.io.BufferedReader br = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]in))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resource;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]resource = [CtInvocationImpl][CtVariableReadImpl]br.readLine()) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]resource.endsWith([CtLiteralImpl]"code.ser") || [CtInvocationImpl][CtVariableReadImpl]resource.endsWith([CtLiteralImpl]"conf.ser")) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]fileNames.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]path + [CtLiteralImpl]"/") + [CtVariableReadImpl]resource);
                }
            } 
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtVariableReadImpl]fileNames);
        }
        [CtReturnImpl]return [CtVariableReadImpl]fileNames;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * InputStream to read the fully qualified resource path.
     *
     * @param resource
     * @return  */
    public static [CtTypeReferenceImpl]java.io.InputStream getResourceAsStream([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resource) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl][CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getContextClassLoader().getResourceAsStream([CtVariableReadImpl]resource);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]in == [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.ClassLoader.getSystemClassLoader().getResourceAsStream([CtVariableReadImpl]resource) : [CtVariableReadImpl]in;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Read the contents of the fully qualified resource path.
     *
     * @param resource
     * @return  * @throws Exception
     */
    public static [CtArrayTypeReferenceImpl]byte[] getResourceAsBytes([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resource) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream in = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceAsStream([CtVariableReadImpl]resource);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]in == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayOutputStream out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayOutputStream()) [CtBlockImpl]{
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]in.available() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]out.write([CtInvocationImpl][CtVariableReadImpl]in.read());
            } 
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]out.toByteArray();
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.ClassLoader getContextClassLoader() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create an array of TopologyDetails by reading serialized files for topology and configuration in the
     * resource path.
     *
     * @param failOnParseError
     * 		throw exception if there are unmatched files, otherwise ignore unmatched and read errors.
     * @return An array of TopologyDetails representing resource files.
     * @throws Exception
     */
    public static [CtArrayTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails[] createTopoDetailsArray([CtParameterImpl][CtTypeReferenceImpl]boolean failOnParseError) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails> topoDetailsList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> errors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> resources = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceFiles([CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.TEST_RESOURCE_PATH);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> codeResourceMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> confResourceMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]resources.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resource = [CtInvocationImpl][CtVariableReadImpl]resources.get([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int idxOfSlash = [CtInvocationImpl][CtVariableReadImpl]resource.lastIndexOf([CtLiteralImpl]"/");
            [CtLocalVariableImpl][CtTypeReferenceImpl]int idxOfDash = [CtInvocationImpl][CtVariableReadImpl]resource.lastIndexOf([CtLiteralImpl]"-");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nm = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]idxOfDash > [CtVariableReadImpl]idxOfSlash) ? [CtInvocationImpl][CtVariableReadImpl]resource.substring([CtBinaryOperatorImpl][CtVariableReadImpl]idxOfSlash + [CtLiteralImpl]1, [CtVariableReadImpl]idxOfDash) : [CtInvocationImpl][CtVariableReadImpl]resource.substring([CtBinaryOperatorImpl][CtVariableReadImpl]idxOfSlash + [CtLiteralImpl]1, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]resource.length() - [CtLiteralImpl]7);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resource.endsWith([CtLiteralImpl]"code.ser")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]codeResourceMap.put([CtVariableReadImpl]nm, [CtVariableReadImpl]resource);
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resource.endsWith([CtLiteralImpl]"conf.ser")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]confResourceMap.put([CtVariableReadImpl]nm, [CtVariableReadImpl]resource);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Ignoring unsupported resource file " + [CtVariableReadImpl]resource);
            }
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] examinedConfParams = [CtNewArrayImpl]new java.lang.String[]{ [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_NAME, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_SCHEDULER_STRATEGY, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_PRIORITY, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_WORKERS, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_WORKER_MAX_HEAP_SIZE_MB, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_SUBMITTER_USER, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_ACKER_CPU_PCORE_PERCENT, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_ACKER_RESOURCES_OFFHEAP_MEMORY_MB, [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_ACKER_RESOURCES_ONHEAP_MEMORY_MB };
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nm : [CtInvocationImpl][CtVariableReadImpl]codeResourceMap.keySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String codeResource = [CtInvocationImpl][CtVariableReadImpl]codeResourceMap.get([CtVariableReadImpl]nm);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]confResourceMap.containsKey([CtVariableReadImpl]nm)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String err = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Ignoring topology file %s because of missing config file for %s", [CtVariableReadImpl]codeResource, [CtVariableReadImpl]nm);
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]err);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.error([CtVariableReadImpl]err);
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String confResource = [CtInvocationImpl][CtVariableReadImpl]confResourceMap.get([CtVariableReadImpl]nm);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtLiteralImpl]"Found matching topology and config files: {}, {}", [CtVariableReadImpl]codeResource, [CtVariableReadImpl]confResource);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.generated.StormTopology stormTopology;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]stormTopology = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.deserialize([CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceAsBytes([CtVariableReadImpl]codeResource), [CtFieldReadImpl]org.apache.storm.generated.StormTopology.class);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String err = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Cannot read topology from resource %s", [CtVariableReadImpl]codeResource);
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]err);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.error([CtVariableReadImpl]err, [CtVariableReadImpl]ex);
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> conf;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]conf = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Utils.fromCompressedJsonConf([CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceAsBytes([CtVariableReadImpl]confResource));
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.RuntimeException | [CtTypeReferenceImpl]java.io.IOException ex) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String err = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Cannot read configuration from resource %s", [CtVariableReadImpl]confResource);
                [CtInvocationImpl][CtVariableReadImpl]errors.add([CtVariableReadImpl]err);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.error([CtVariableReadImpl]err, [CtVariableReadImpl]ex);
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtCommentImpl]// fix 0.10 conf class names
            [CtArrayTypeReferenceImpl]java.lang.String[] configParamsToFix = [CtNewArrayImpl]new java.lang.String[]{ [CtFieldReadImpl]org.apache.storm.Config.TOPOLOGY_SCHEDULER_STRATEGY, [CtFieldReadImpl]org.apache.storm.Config.STORM_NETWORK_TOPOGRAPHY_PLUGIN, [CtFieldReadImpl]org.apache.storm.DaemonConfig.RESOURCE_AWARE_SCHEDULER_PRIORITY_STRATEGY };
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configParam : [CtVariableReadImpl]configParamsToFix) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]conf.containsKey([CtVariableReadImpl]configParam)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String className = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]conf.get([CtVariableReadImpl]configParam)));
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]className.startsWith([CtLiteralImpl]"backtype")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]className = [CtInvocationImpl][CtVariableReadImpl]className.replace([CtLiteralImpl]"backtype", [CtLiteralImpl]"org.apache");
                    [CtInvocationImpl][CtVariableReadImpl]conf.put([CtVariableReadImpl]configParam, [CtVariableReadImpl]className);
                }
            }
            [CtIfImpl][CtCommentImpl]// fix conf params used by ConstraintSolverStrategy
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]conf.containsKey([CtTypeAccessImpl]DaemonConfig.RESOURCE_AWARE_SCHEDULER_MAX_STATE_SEARCH)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]conf.put([CtTypeAccessImpl]DaemonConfig.RESOURCE_AWARE_SCHEDULER_MAX_STATE_SEARCH, [CtLiteralImpl]10000);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]conf.containsKey([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]conf.put([CtTypeAccessImpl]Config.TOPOLOGY_RAS_CONSTRAINT_MAX_STATE_SEARCH, [CtLiteralImpl]10000);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String topoId = [CtVariableReadImpl]nm;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String topoName = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]conf.getOrDefault([CtTypeAccessImpl]Config.TOPOLOGY_NAME, [CtVariableReadImpl]nm)));
            [CtLocalVariableImpl][CtCommentImpl]// conf
            [CtTypeReferenceImpl]java.lang.StringBuffer sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Config for " + [CtVariableReadImpl]nm) + [CtLiteralImpl]": ");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String param : [CtVariableReadImpl]examinedConfParams) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object val = [CtInvocationImpl][CtVariableReadImpl]conf.getOrDefault([CtVariableReadImpl]param, [CtLiteralImpl]"<null>");
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtVariableReadImpl]param).append([CtLiteralImpl]"=").append([CtVariableReadImpl]val).append([CtLiteralImpl]", ");
            }
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtInvocationImpl][CtVariableReadImpl]sb.toString());
            [CtLocalVariableImpl][CtCommentImpl]// topo
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.storm.scheduler.ExecutorDetails, [CtTypeReferenceImpl]java.lang.String> execToComp = [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler.genExecsAndComps([CtVariableReadImpl]stormTopology);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtLiteralImpl]"Topology \"{}\" spouts={}, bolts={}, execToComp size is {}", [CtVariableReadImpl]topoName, [CtInvocationImpl][CtVariableReadImpl]stormTopology.get_spouts_size(), [CtInvocationImpl][CtVariableReadImpl]stormTopology.get_bolts_size(), [CtInvocationImpl][CtVariableReadImpl]execToComp.size());
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numWorkers = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]conf.getOrDefault([CtTypeAccessImpl]Config.TOPOLOGY_WORKERS, [CtLiteralImpl]"0"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails([CtVariableReadImpl]topoId, [CtVariableReadImpl]conf, [CtVariableReadImpl]stormTopology, [CtVariableReadImpl]numWorkers, [CtVariableReadImpl]execToComp, [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.utils.Time.currentTimeSecs(), [CtLiteralImpl]"user");
            [CtInvocationImpl][CtVariableReadImpl]topo.getComponents();[CtCommentImpl]// sanity check - normally this should not fail

            [CtInvocationImpl][CtVariableReadImpl]topoDetailsList.add([CtVariableReadImpl]topo);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]errors.isEmpty()) && [CtVariableReadImpl]failOnParseError) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Exception([CtBinaryOperatorImpl][CtLiteralImpl]"Unable to parse all serialized objects\n\t" + [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]"\n\t", [CtVariableReadImpl]errors));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]topoDetailsList.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails[[CtLiteralImpl]0]);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if the files in the resource directory are matched, can be read properly, and code/config files occur
     * in matched pairs.
     *
     * @throws Exception
     * 		showing bad and unmatched resource files.
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testReadSerializedTopologiesAndConfigs() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> resources = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.getResourceFiles([CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.TEST_RESOURCE_PATH);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"No resource files found in " + [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.TEST_RESOURCE_PATH, [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]resources.isEmpty());
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails[] topoDetailsArray = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createTopoDetailsArray([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create one supervisor and add to the supervisors list.
     *
     * @param rack
     * 		rack-number
     * @param superInRack
     * 		supervisor number in the rack
     * @param cpu
     * 		percentage
     * @param mem
     * 		in megabytes
     * @param numPorts
     * 		number of ports on this supervisor
     * @param sups
     * 		returned map os supervisors
     */
    private static [CtTypeReferenceImpl]void createAndAddOneSupervisor([CtParameterImpl][CtTypeReferenceImpl]int rack, [CtParameterImpl][CtTypeReferenceImpl]int superInRack, [CtParameterImpl][CtTypeReferenceImpl]double cpu, [CtParameterImpl][CtTypeReferenceImpl]double mem, [CtParameterImpl][CtTypeReferenceImpl]int numPorts, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> sups) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Number> ports = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int p = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]p < [CtVariableReadImpl]numPorts; [CtUnaryOperatorImpl][CtVariableWriteImpl]p++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ports.add([CtVariableReadImpl]p);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String superId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"r%03ds%03d", [CtVariableReadImpl]rack, [CtVariableReadImpl]superInRack);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String hostId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"host-%03d-rack-%03d", [CtVariableReadImpl]superInRack, [CtVariableReadImpl]rack);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Double> resourceMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]resourceMap.put([CtTypeAccessImpl]Config.SUPERVISOR_CPU_CAPACITY, [CtVariableReadImpl]cpu);
        [CtInvocationImpl][CtVariableReadImpl]resourceMap.put([CtTypeAccessImpl]Config.SUPERVISOR_MEMORY_CAPACITY_MB, [CtVariableReadImpl]mem);
        [CtInvocationImpl][CtVariableReadImpl]resourceMap.put([CtLiteralImpl]"network.resource.units", [CtLiteralImpl]50.0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails sup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails([CtVariableReadImpl]superId, [CtVariableReadImpl]hostId, [CtLiteralImpl]null, [CtVariableReadImpl]ports, [CtInvocationImpl][CtTypeAccessImpl]NormalizedResources.RESOURCE_NAME_NORMALIZER.normalizedResourceMap([CtVariableReadImpl]resourceMap));
        [CtInvocationImpl][CtVariableReadImpl]sups.put([CtInvocationImpl][CtVariableReadImpl]sup.getId(), [CtVariableReadImpl]sup);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create supervisors.
     *
     * @param uniformSupervisors
     * 		true if all supervisors are of the same size, false otherwise.
     * @return supervisor details indexed by id
     */
    private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> createSupervisors([CtParameterImpl][CtTypeReferenceImpl]boolean uniformSupervisors) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> retVal;
        [CtIfImpl]if ([CtVariableReadImpl]uniformSupervisors) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numRacks = [CtLiteralImpl]16;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numSupersPerRack = [CtLiteralImpl]82;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numPorts = [CtLiteralImpl]50;[CtCommentImpl]// scheduling will fill up 2-6, leaving of 90% workerslots unused - does this cause slow scheduling?

            [CtLocalVariableImpl][CtTypeReferenceImpl]int rackStart = [CtLiteralImpl]0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int superInRackStart = [CtLiteralImpl]1;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double cpu = [CtLiteralImpl]7200;[CtCommentImpl]// %percent

            [CtLocalVariableImpl][CtTypeReferenceImpl]double mem = [CtLiteralImpl]356000;[CtCommentImpl]// MB

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Double> miscResources = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]miscResources.put([CtLiteralImpl]"network.resource.units", [CtLiteralImpl]100.0);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler.genSupervisorsWithRacks([CtVariableReadImpl]numRacks, [CtVariableReadImpl]numSupersPerRack, [CtVariableReadImpl]numPorts, [CtVariableReadImpl]rackStart, [CtVariableReadImpl]superInRackStart, [CtVariableReadImpl]cpu, [CtVariableReadImpl]mem, [CtVariableReadImpl]miscResources);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// this non-uniform supervisor distribution closelt (but not exactly) mimics IridiumBlue cluster
            [CtTypeReferenceImpl]int numSupersPerRack = [CtLiteralImpl]82;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numPorts = [CtLiteralImpl]50;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> retList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rack = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]rack < [CtLiteralImpl]12; [CtUnaryOperatorImpl][CtVariableWriteImpl]rack++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]double cpu = [CtLiteralImpl]3600;[CtCommentImpl]// %percent

                [CtLocalVariableImpl][CtTypeReferenceImpl]double mem = [CtLiteralImpl]178000;[CtCommentImpl]// MB

                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int superInRack = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]superInRack < [CtVariableReadImpl]numSupersPerRack; [CtUnaryOperatorImpl][CtVariableWriteImpl]superInRack++) [CtBlockImpl]{
                    [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createAndAddOneSupervisor([CtVariableReadImpl]rack, [CtVariableReadImpl]superInRack, [CtBinaryOperatorImpl][CtVariableReadImpl]cpu - [CtBinaryOperatorImpl]([CtLiteralImpl]100 * [CtBinaryOperatorImpl]([CtVariableReadImpl]superInRack % [CtLiteralImpl]2)), [CtVariableReadImpl]mem, [CtVariableReadImpl]numPorts, [CtVariableReadImpl]retList);
                }
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rack = [CtLiteralImpl]12; [CtBinaryOperatorImpl][CtVariableReadImpl]rack < [CtLiteralImpl]14; [CtUnaryOperatorImpl][CtVariableWriteImpl]rack++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]double cpu = [CtLiteralImpl]2400;[CtCommentImpl]// %percent

                [CtLocalVariableImpl][CtTypeReferenceImpl]double mem = [CtLiteralImpl]118100;[CtCommentImpl]// MB

                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int superInRack = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]superInRack < [CtVariableReadImpl]numSupersPerRack; [CtUnaryOperatorImpl][CtVariableWriteImpl]superInRack++) [CtBlockImpl]{
                    [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createAndAddOneSupervisor([CtVariableReadImpl]rack, [CtVariableReadImpl]superInRack, [CtBinaryOperatorImpl][CtVariableReadImpl]cpu - [CtBinaryOperatorImpl]([CtLiteralImpl]100 * [CtBinaryOperatorImpl]([CtVariableReadImpl]superInRack % [CtLiteralImpl]2)), [CtVariableReadImpl]mem, [CtVariableReadImpl]numPorts, [CtVariableReadImpl]retList);
                }
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rack = [CtLiteralImpl]14; [CtBinaryOperatorImpl][CtVariableReadImpl]rack < [CtLiteralImpl]16; [CtUnaryOperatorImpl][CtVariableWriteImpl]rack++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]double cpu = [CtLiteralImpl]1200;[CtCommentImpl]// %percent

                [CtLocalVariableImpl][CtTypeReferenceImpl]double mem = [CtLiteralImpl]42480;[CtCommentImpl]// MB

                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int superInRack = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]superInRack < [CtVariableReadImpl]numSupersPerRack; [CtUnaryOperatorImpl][CtVariableWriteImpl]superInRack++) [CtBlockImpl]{
                    [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createAndAddOneSupervisor([CtVariableReadImpl]rack, [CtVariableReadImpl]superInRack, [CtBinaryOperatorImpl][CtVariableReadImpl]cpu - [CtBinaryOperatorImpl]([CtLiteralImpl]100 * [CtBinaryOperatorImpl]([CtVariableReadImpl]superInRack % [CtLiteralImpl]2)), [CtVariableReadImpl]mem, [CtVariableReadImpl]numPorts, [CtVariableReadImpl]retList);
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]retList;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a large cluster, read topologies and configuration from resource directory and schedule.
     *
     * @throws Exception
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testLargeCluster() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean uniformSupervisors = [CtLiteralImpl]false;[CtCommentImpl]// false means non-uniform supervisor distribution

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> supervisors = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createSupervisors([CtVariableReadImpl]uniformSupervisors);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails[] topoDetailsArray = [CtInvocationImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.createTopoDetailsArray([CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertTrue([CtLiteralImpl]"No topologies found", [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]topoDetailsArray.length > [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies([CtVariableReadImpl]topoDetailsArray);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.Config confWithDefaultStrategy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.Config();
        [CtInvocationImpl][CtVariableReadImpl]confWithDefaultStrategy.putAll([CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]topoDetailsArray[[CtLiteralImpl]0].getConf());
        [CtInvocationImpl][CtVariableReadImpl]confWithDefaultStrategy.put([CtTypeAccessImpl]Config.TOPOLOGY_SCHEDULER_STRATEGY, [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.DefaultResourceAwareStrategy.class.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.INimbus iNimbus = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.INimbusTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster cluster = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.Cluster([CtVariableReadImpl]iNimbus, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.normalization.ResourceMetrics([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.metric.StormMetricsRegistry()), [CtVariableReadImpl]supervisors, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>(), [CtVariableReadImpl]topologies, [CtVariableReadImpl]confWithDefaultStrategy);
        [CtAssignmentImpl][CtFieldWriteImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Class> classesToDebug = [CtInvocationImpl][CtCommentImpl]// count calls to calculateSharedOffHeapNodeMemory()
        [CtTypeAccessImpl]java.util.Arrays.asList([CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.DefaultResourceAwareStrategy.class, [CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.GenericResourceAwareStrategy.class, [CtFieldReadImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler.class, [CtFieldReadImpl]org.apache.storm.scheduler.Cluster.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.logging.log4j.Level logLevel = [CtFieldReadImpl]org.apache.logging.log4j.Level.INFO;[CtCommentImpl]// switch to Level.DEBUG for verbose otherwise Level.INFO

        [CtInvocationImpl][CtVariableReadImpl]classesToDebug.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.Class x) -> [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.core.config.Configurator.setLevel([CtInvocationImpl][CtVariableReadImpl]x.getName(), [CtVariableReadImpl]logLevel));
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler.prepare([CtVariableReadImpl]confWithDefaultStrategy, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.metric.StormMetricsRegistry());
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.scheduler.schedule([CtVariableReadImpl]topologies, [CtVariableReadImpl]cluster);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long endTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtLiteralImpl]"Scheduling Time: {} topologies in {} seconds", [CtFieldReadImpl][CtVariableReadImpl]topoDetailsArray.length, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]endTime - [CtVariableReadImpl]startTime) / [CtLiteralImpl]1000.0);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails td : [CtVariableReadImpl]topoDetailsArray) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler.assertTopologiesFullyScheduled([CtVariableReadImpl]cluster, [CtInvocationImpl][CtVariableReadImpl]td.getName());
        }
        [CtLocalVariableImpl][CtCommentImpl]// Remove topology and reschedule it
        [CtTypeReferenceImpl]java.util.Random random = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Random();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]topoDetailsArray.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]startTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int iTopo = [CtVariableReadImpl]i;[CtCommentImpl]// random.nextInt(topoDetailsArray.length);

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.TopologyDetails topoDetails = [CtArrayReadImpl][CtVariableReadImpl]topoDetailsArray[[CtVariableReadImpl]iTopo];
            [CtInvocationImpl][CtVariableReadImpl]cluster.unassign([CtInvocationImpl][CtVariableReadImpl]topoDetails.getId());
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtLiteralImpl]"({}) Removed topology {}", [CtVariableReadImpl]i, [CtInvocationImpl][CtVariableReadImpl]topoDetails.getName());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.IScheduler rescheduler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.resource.ResourceAwareScheduler();
            [CtInvocationImpl][CtVariableReadImpl]rescheduler.prepare([CtVariableReadImpl]confWithDefaultStrategy, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.metric.StormMetricsRegistry());
            [CtInvocationImpl][CtVariableReadImpl]rescheduler.schedule([CtVariableReadImpl]topologies, [CtVariableReadImpl]cluster);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.storm.scheduler.resource.TestUtilsForResourceAwareScheduler.assertTopologiesFullyScheduled([CtVariableReadImpl]cluster, [CtInvocationImpl][CtVariableReadImpl]topoDetails.getName());
            [CtAssignmentImpl][CtVariableWriteImpl]endTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtInvocationImpl][CtFieldReadImpl]org.apache.storm.scheduler.resource.strategies.scheduling.TestLargeCluster.LOG.info([CtLiteralImpl]"({}) Scheduling Time: Removed topology {} and rescheduled in {} seconds", [CtVariableReadImpl]i, [CtInvocationImpl][CtVariableReadImpl]topoDetails.getName(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]endTime - [CtVariableReadImpl]startTime) / [CtLiteralImpl]1000.0);
        }
        [CtInvocationImpl][CtVariableReadImpl]classesToDebug.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.Class x) -> [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.core.config.Configurator.setLevel([CtInvocationImpl][CtVariableReadImpl]x.getName(), [CtTypeAccessImpl]Level.INFO));
    }

    [CtClassImpl]public static class INimbusTest implements [CtTypeReferenceImpl]org.apache.storm.scheduler.INimbus {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void prepare([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> topoConf, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schedulerLocalDir) [CtBlockImpl]{
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> allSlotsAvailableForScheduling([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> existingSupervisors, [CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> topologiesMissingAssignments) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// return null;
            [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot> ret = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails sd : [CtVariableReadImpl]existingSupervisors) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String id = [CtInvocationImpl][CtVariableReadImpl]sd.getId();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Number port : [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Number>) ([CtVariableReadImpl]sd.getMeta()))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ret.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot([CtVariableReadImpl]id, [CtVariableReadImpl]port));
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]ret;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void assignSlots([CtParameterImpl][CtTypeReferenceImpl]org.apache.storm.scheduler.Topologies topologies, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.apache.storm.scheduler.WorkerSlot>> newSlotsByTopologyId) [CtBlockImpl]{
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getHostName([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.storm.scheduler.SupervisorDetails> existingSupervisors, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String nodeId) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]existingSupervisors.containsKey([CtVariableReadImpl]nodeId)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingSupervisors.get([CtVariableReadImpl]nodeId).getHost();
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.apache.storm.scheduler.IScheduler getForcedScheduler() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }
}
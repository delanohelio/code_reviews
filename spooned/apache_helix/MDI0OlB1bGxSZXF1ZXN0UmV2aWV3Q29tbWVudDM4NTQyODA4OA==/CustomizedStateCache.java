[CompilationUnitImpl][CtPackageDeclarationImpl]package org.apache.helix.common.caches;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.apache.helix.PropertyKey;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.helix.model.LiveInstance;
[CtUnresolvedImport]import com.google.common.collect.Maps;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.helix.HelixDataAccessor;
[CtUnresolvedImport]import org.apache.helix.common.controllers.ControlContextProvider;
[CtUnresolvedImport]import org.apache.helix.controller.LogUtil;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.helix.model.CustomizedState;
[CtClassImpl]public class CustomizedStateCache extends [CtTypeReferenceImpl]org.apache.helix.common.caches.AbstractDataCache<[CtTypeReferenceImpl]org.apache.helix.model.CustomizedState> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtFieldReadImpl]org.apache.helix.common.caches.CurrentStateCache.class.getName());

    [CtFieldImpl][CtCommentImpl]// instance -> customizedStateType -> resource -> CustomizedState
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState>>> _customizedStateMap;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.helix.PropertyKey, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState> _customizedStateCache = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();

    [CtConstructorImpl]public CustomizedStateCache([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clusterName) [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl]createDefaultControlContextProvider([CtVariableReadImpl]clusterName));
    }

    [CtConstructorImpl]public CustomizedStateCache([CtParameterImpl][CtTypeReferenceImpl]org.apache.helix.common.controllers.ControlContextProvider contextProvider) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]contextProvider);
        [CtAssignmentImpl][CtFieldWriteImpl]_customizedStateMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This refreshes the CustomizedStates data by re-fetching the data from zookeeper in an efficient
     * way
     *
     * @param accessor
     * @param liveInstanceMap
     * 		map of all liveInstances in cluster
     * @return  */
    public [CtTypeReferenceImpl]boolean refresh([CtParameterImpl][CtTypeReferenceImpl]org.apache.helix.HelixDataAccessor accessor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.LiveInstance> liveInstanceMap, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> aggregationEnabledTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtInvocationImpl]refreshCustomizedStatesCache([CtVariableReadImpl]accessor, [CtVariableReadImpl]liveInstanceMap, [CtVariableReadImpl]aggregationEnabledTypes);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState>>> allCustomizedStateMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.helix.PropertyKey key : [CtInvocationImpl][CtFieldReadImpl]_customizedStateCache.keySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.helix.model.CustomizedState customizedState = [CtInvocationImpl][CtFieldReadImpl]_customizedStateCache.get([CtVariableReadImpl]key);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] params = [CtInvocationImpl][CtVariableReadImpl]key.getParams();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]customizedState != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.length >= [CtLiteralImpl]4)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String instanceName = [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]1];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String customizedStateType = [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]2];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourceName = [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]3];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState>> instanceCustomizedStateMap = [CtInvocationImpl][CtVariableReadImpl]allCustomizedStateMap.get([CtVariableReadImpl]instanceName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]instanceCustomizedStateMap == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]instanceCustomizedStateMap = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();
                    [CtInvocationImpl][CtVariableReadImpl]allCustomizedStateMap.put([CtVariableReadImpl]instanceName, [CtVariableReadImpl]instanceCustomizedStateMap);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState> customizedStateNameCustomizedStateMap = [CtInvocationImpl][CtVariableReadImpl]instanceCustomizedStateMap.get([CtVariableReadImpl]customizedStateType);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]customizedStateNameCustomizedStateMap == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]customizedStateNameCustomizedStateMap = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();
                    [CtInvocationImpl][CtVariableReadImpl]instanceCustomizedStateMap.put([CtVariableReadImpl]customizedStateType, [CtVariableReadImpl]customizedStateNameCustomizedStateMap);
                }
                [CtInvocationImpl][CtVariableReadImpl]customizedStateNameCustomizedStateMap.put([CtVariableReadImpl]resourceName, [CtVariableReadImpl]customizedState);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String instance : [CtInvocationImpl][CtVariableReadImpl]allCustomizedStateMap.keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]allCustomizedStateMap.put([CtVariableReadImpl]instance, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl][CtVariableReadImpl]allCustomizedStateMap.get([CtVariableReadImpl]instance)));
        }
        [CtAssignmentImpl][CtFieldWriteImpl]_customizedStateMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtVariableReadImpl]allCustomizedStateMap);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long endTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.helix.controller.LogUtil.logInfo([CtFieldReadImpl]org.apache.helix.common.caches.CustomizedStateCache.LOG, [CtInvocationImpl]genEventInfo(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"END: CustomizedStateCache.refresh() for cluster " + [CtInvocationImpl][CtFieldReadImpl]_controlContextProvider.getClusterName()) + [CtLiteralImpl]", started at : ") + [CtVariableReadImpl]startTime) + [CtLiteralImpl]", took ") + [CtBinaryOperatorImpl]([CtVariableReadImpl]endTime - [CtVariableReadImpl]startTime)) + [CtLiteralImpl]" ms");
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.helix.common.caches.CustomizedStateCache.LOG.isDebugEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.helix.controller.LogUtil.logDebug([CtFieldReadImpl]org.apache.helix.common.caches.CustomizedStateCache.LOG, [CtInvocationImpl]genEventInfo(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Customized State refreshed : %s", [CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.toString()));
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtCommentImpl]// reload customized states that has been changed from zk to local cache.
    private [CtTypeReferenceImpl]void refreshCustomizedStatesCache([CtParameterImpl][CtTypeReferenceImpl]org.apache.helix.HelixDataAccessor accessor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.LiveInstance> liveInstanceMap, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> aggregationEnabledTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long start = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.helix.PropertyKey.Builder keyBuilder = [CtInvocationImpl][CtVariableReadImpl]accessor.keyBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.helix.PropertyKey> customizedStateKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> resourceNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String instanceName : [CtInvocationImpl][CtVariableReadImpl]liveInstanceMap.keySet()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String customizedStateName : [CtVariableReadImpl]aggregationEnabledTypes) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]resourceNames = [CtInvocationImpl][CtVariableReadImpl]accessor.getChildNames([CtInvocationImpl][CtVariableReadImpl]keyBuilder.customizedStates([CtVariableReadImpl]instanceName, [CtVariableReadImpl]customizedStateName));
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourceName : [CtVariableReadImpl]resourceNames) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]customizedStateKeys.add([CtInvocationImpl][CtVariableReadImpl]keyBuilder.customizedState([CtVariableReadImpl]instanceName, [CtVariableReadImpl]customizedStateName, [CtVariableReadImpl]resourceName));
                }
            }
            [CtLocalVariableImpl][CtCommentImpl]// All new entries from zk not cached locally yet should be read from ZK.
            [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.helix.PropertyKey> reloadKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtVariableReadImpl]customizedStateKeys);
            [CtInvocationImpl][CtVariableReadImpl]reloadKeys.removeAll([CtInvocationImpl][CtFieldReadImpl]_customizedStateCache.keySet());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.helix.PropertyKey> cachedKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtInvocationImpl][CtFieldReadImpl]_customizedStateCache.keySet());
            [CtInvocationImpl][CtVariableReadImpl]cachedKeys.retainAll([CtVariableReadImpl]customizedStateKeys);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.apache.helix.PropertyKey> reloadedKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.helix.PropertyKey, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState> newStateCache = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl]refreshProperties([CtVariableReadImpl]accessor, [CtVariableReadImpl]reloadKeys, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]cachedKeys), [CtFieldReadImpl]_customizedStateCache, [CtVariableReadImpl]reloadedKeys));
            [CtAssignmentImpl][CtFieldWriteImpl]_customizedStateCache = [CtVariableReadImpl]newStateCache;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.helix.common.caches.CustomizedStateCache.LOG.isDebugEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.helix.controller.LogUtil.logDebug([CtFieldReadImpl]org.apache.helix.common.caches.CustomizedStateCache.LOG, [CtInvocationImpl]genEventInfo(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"# of CustomizedStates reload: " + [CtInvocationImpl][CtVariableReadImpl]reloadKeys.size()) + [CtLiteralImpl]", skipped:") + [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]customizedStateKeys.size() - [CtInvocationImpl][CtVariableReadImpl]reloadKeys.size())) + [CtLiteralImpl]". took ") + [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtVariableReadImpl]start)) + [CtLiteralImpl]" ms to reload new customized states for cluster: ") + [CtInvocationImpl][CtFieldReadImpl]_controlContextProvider.getClusterName());
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return CustomizedStates map for all instances.
     *
     * @return  */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState>>> getCustomizedStatesMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtFieldReadImpl]_customizedStateMap);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return all CustomizedStates on the given instance.
     *
     * @param instance
     * @return  */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState>> getCustomizedStates([CtParameterImpl][CtTypeReferenceImpl]java.lang.String instance) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.containsKey([CtVariableReadImpl]instance)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.get([CtVariableReadImpl]instance));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Provides the customized state of the node for a given customized state type
     *
     * @param instance
     * @param customizeStateType
     * @return  */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.helix.model.CustomizedState> getCustomizedState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String instance, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String customizeStateType) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.containsKey([CtVariableReadImpl]instance)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.get([CtVariableReadImpl]instance).containsKey([CtVariableReadImpl]customizeStateType))) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_customizedStateMap.get([CtVariableReadImpl]instance).get([CtVariableReadImpl]customizeStateType));
    }
}
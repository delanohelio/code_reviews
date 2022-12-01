[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.shardingsphere.cluster.configuration.swapper;
[CtUnresolvedImport]import org.apache.shardingsphere.infra.yaml.swapper.YamlSwapper;
[CtUnresolvedImport]import org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration;
[CtUnresolvedImport]import org.apache.shardingsphere.cluster.configuration.yaml.YamlHeartBeatConfiguration;
[CtUnresolvedImport]import org.apache.shardingsphere.cluster.configuration.config.HeartBeatConfiguration;
[CtUnresolvedImport]import org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration;
[CtClassImpl][CtJavaDocImpl]/**
 * Cluster configuration YAML swapper.
 */
public final class ClusterConfigurationYamlSwapper implements [CtTypeReferenceImpl]org.apache.shardingsphere.infra.yaml.swapper.YamlSwapper<[CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration, [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration> {
    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration swap([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration clusterConfiguration) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration yamlClusterConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlHeartBeatConfiguration yamlHeartBeatConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlHeartBeatConfiguration();
        [CtInvocationImpl][CtVariableReadImpl]yamlHeartBeatConfiguration.setSql([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clusterConfiguration.getHeartBeat().getSql());
        [CtInvocationImpl][CtVariableReadImpl]yamlHeartBeatConfiguration.setInterval([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clusterConfiguration.getHeartBeat().getInterval());
        [CtInvocationImpl][CtVariableReadImpl]yamlHeartBeatConfiguration.setRetryEnable([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clusterConfiguration.getHeartBeat().getRetryEnable());
        [CtInvocationImpl][CtVariableReadImpl]yamlHeartBeatConfiguration.setRetryMaximum([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clusterConfiguration.getHeartBeat().getRetryMaximum());
        [CtInvocationImpl][CtVariableReadImpl]yamlClusterConfiguration.setHeartBeat([CtVariableReadImpl]yamlHeartBeatConfiguration);
        [CtReturnImpl]return [CtVariableReadImpl]yamlClusterConfiguration;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration swap([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.yaml.YamlClusterConfiguration yamlConfiguration) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration clusterConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.ClusterConfiguration();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.HeartBeatConfiguration heartBeatConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.shardingsphere.cluster.configuration.config.HeartBeatConfiguration();
        [CtInvocationImpl][CtVariableReadImpl]heartBeatConfiguration.setSql([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yamlConfiguration.getHeartBeat().getSql());
        [CtInvocationImpl][CtVariableReadImpl]heartBeatConfiguration.setInterval([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yamlConfiguration.getHeartBeat().getInterval());
        [CtInvocationImpl][CtVariableReadImpl]heartBeatConfiguration.setRetryEnable([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yamlConfiguration.getHeartBeat().getRetryEnable());
        [CtInvocationImpl][CtVariableReadImpl]heartBeatConfiguration.setRetryMaximum([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yamlConfiguration.getHeartBeat().getRetryMaximum());
        [CtInvocationImpl][CtVariableReadImpl]clusterConfiguration.setHeartBeat([CtVariableReadImpl]heartBeatConfiguration);
        [CtReturnImpl]return [CtVariableReadImpl]clusterConfiguration;
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.optaplanner.core.impl.constructionheuristic.placer;
[CtUnresolvedImport]import org.optaplanner.core.config.constructionheuristic.placer.EntityPlacerConfig;
[CtUnresolvedImport]import org.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
[CtUnresolvedImport]import org.optaplanner.core.config.constructionheuristic.placer.QueuedEntityPlacerConfig;
[CtUnresolvedImport]import org.optaplanner.core.config.constructionheuristic.placer.QueuedValuePlacerConfig;
[CtUnresolvedImport]import org.optaplanner.core.config.constructionheuristic.placer.PooledEntityPlacerConfig;
[CtInterfaceImpl]public interface EntityPlacerFactory {
    [CtMethodImpl]static [CtTypeReferenceImpl]org.optaplanner.core.impl.constructionheuristic.placer.EntityPlacerFactory create([CtParameterImpl][CtTypeReferenceImpl]org.optaplanner.core.config.constructionheuristic.placer.EntityPlacerConfig entityPlacerConfig) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.optaplanner.core.config.constructionheuristic.placer.PooledEntityPlacerConfig.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]entityPlacerConfig.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.optaplanner.core.impl.constructionheuristic.placer.PooledEntityPlacerFactory([CtVariableReadImpl](([CtTypeReferenceImpl]org.optaplanner.core.config.constructionheuristic.placer.PooledEntityPlacerConfig) (entityPlacerConfig)));
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.optaplanner.core.config.constructionheuristic.placer.QueuedEntityPlacerConfig.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]entityPlacerConfig.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.optaplanner.core.impl.constructionheuristic.placer.QueuedEntityPlacerFactory([CtVariableReadImpl](([CtTypeReferenceImpl]org.optaplanner.core.config.constructionheuristic.placer.QueuedEntityPlacerConfig) (entityPlacerConfig)));
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.optaplanner.core.config.constructionheuristic.placer.QueuedValuePlacerConfig.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]entityPlacerConfig.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.optaplanner.core.impl.constructionheuristic.placer.QueuedValuePlacerFactory([CtVariableReadImpl](([CtTypeReferenceImpl]org.optaplanner.core.config.constructionheuristic.placer.QueuedValuePlacerConfig) (entityPlacerConfig)));
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.optaplanner.core.impl.constructionheuristic.placer.EntityPlacer buildEntityPlacer([CtParameterImpl][CtTypeReferenceImpl]org.optaplanner.core.impl.heuristic.HeuristicConfigPolicy configPolicy);
}
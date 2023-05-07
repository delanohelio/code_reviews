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
[CtPackageDeclarationImpl]package org.apache.ignite.spi.systemview.view;
[CtUnresolvedImport]import org.jetbrains.annotations.Nullable;
[CtUnresolvedImport]import org.apache.ignite.internal.managers.systemview.walker.Order;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.task.GridTaskWorker;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteUuid;
[CtClassImpl][CtJavaDocImpl]/**
 * Compute task representation for a {@link SystemView}.
 */
public class ComputeTaskView {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Worker for task.
     */
    public final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.task.GridTaskWorker worker;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Task id.
     */
    public final [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid id;

    [CtConstructorImpl][CtJavaDocImpl]/**
     *
     * @param id
     * 		Task id.
     * @param worker
     * 		Worker for task.
     */
    public ComputeTaskView([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid id, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.task.GridTaskWorker worker) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.worker = [CtVariableReadImpl]worker;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Task id.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order
    public [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid id() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]id;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@link ComputeTaskView#sessionId()} value equal to the value of {@link ComputeJobView#sessionId()}
     * if both records represents parts of the same computation.
     *
     * @see ComputeJobView#sessionId()
     * @return Session id.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]1)
    public [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteUuid sessionId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getId();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Task node id.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]2)
    public [CtTypeReferenceImpl]java.util.UUID taskNodeId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getTaskNodeId();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return {@code True} if task is internal.
     */
    public [CtTypeReferenceImpl]boolean internal() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]worker.isInternal();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Task name.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]3)
    public [CtTypeReferenceImpl]java.lang.String taskName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getTaskName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Task class name.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]4)
    public [CtTypeReferenceImpl]java.lang.String taskClassName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getTaskClassName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Start time in milliseconds.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]7)
    public [CtTypeReferenceImpl]long startTime() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getStartTime();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return End time in milliseconds.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]8)
    public [CtTypeReferenceImpl]long endTime() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getEndTime();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Executor name.
     */
    public [CtTypeReferenceImpl]java.lang.String execName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().executorName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Affinity cache name or {@code null} for non affinity call.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]6)
    [CtAnnotationImpl]@org.jetbrains.annotations.Nullable
    public [CtTypeReferenceImpl]java.lang.String affinityCacheName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]worker.affCacheName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Affinity partition id or {@code -1} for non affinity call.
     */
    [CtAnnotationImpl]@org.apache.ignite.internal.managers.systemview.walker.Order([CtLiteralImpl]5)
    public [CtTypeReferenceImpl]int affinityPartitionId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]worker.affPartId();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return User provided version.
     */
    public [CtTypeReferenceImpl]java.lang.String userVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]worker.getSession().getUserVersion();
    }
}
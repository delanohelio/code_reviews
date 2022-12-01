[CompilationUnitImpl][CtCommentImpl]/* Copyright 2016 Netflix, Inc.

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
[CtPackageDeclarationImpl]package com.netflix.conductor.common.metadata.workflow;
[CtUnresolvedImport]import javax.validation.constraints.PositiveOrZero;
[CtUnresolvedImport]import com.netflix.conductor.common.metadata.tasks.TaskDef;
[CtImportImpl]import java.util.LinkedList;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.github.vmg.protogen.annotations.ProtoField;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.github.vmg.protogen.annotations.ProtoMessage;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import javax.validation.constraints.NotEmpty;
[CtUnresolvedImport]import javax.validation.Valid;
[CtUnresolvedImport]import com.netflix.conductor.common.run.Workflow;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Iterator;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Viren

This is the task definition definied as part of the {@link WorkflowDef}. The tasks definied in the Workflow definition are saved
as part of {@link WorkflowDef#getTasks}
 */
[CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoMessage
public class WorkflowTask {
    [CtEnumImpl][CtJavaDocImpl]/**
     * This field is deprecated and will be removed in the next version.
     * Please use {@link TaskType} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public enum Type {

        [CtEnumValueImpl]SIMPLE,
        [CtEnumValueImpl]DYNAMIC,
        [CtEnumValueImpl]FORK_JOIN,
        [CtEnumValueImpl]FORK_JOIN_DYNAMIC,
        [CtEnumValueImpl]DECISION,
        [CtEnumValueImpl]JOIN,
        [CtEnumValueImpl]SUB_WORKFLOW,
        [CtEnumValueImpl]EVENT,
        [CtEnumValueImpl]WAIT,
        [CtEnumValueImpl]USER_DEFINED;
        [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> systemTasks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

        [CtAnonymousExecutableImpl]static [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]SIMPLE.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]DYNAMIC.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]FORK_JOIN.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]FORK_JOIN_DYNAMIC.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]DECISION.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]JOIN.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]SUB_WORKFLOW.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]EVENT.name());
            [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.add([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.[CtFieldReferenceImpl]WAIT.name());
            [CtCommentImpl]// Do NOT add USER_DEFINED here...
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isSystemTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask.Type.systemTasks.contains([CtVariableReadImpl]name);
        }
    }

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]1)
    [CtAnnotationImpl]@javax.validation.constraints.NotEmpty(message = [CtLiteralImpl]"WorkflowTask name cannot be empty or null")
    private [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]2)
    [CtAnnotationImpl]@javax.validation.constraints.NotEmpty(message = [CtLiteralImpl]"WorkflowTask taskReferenceName name cannot be empty or null")
    private [CtTypeReferenceImpl]java.lang.String taskReferenceName;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]3)
    private [CtTypeReferenceImpl]java.lang.String description;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]4)
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> inputParameters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]5)
    private [CtTypeReferenceImpl]java.lang.String type = [CtInvocationImpl][CtTypeAccessImpl]TaskType.SIMPLE.name();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]6)
    private [CtTypeReferenceImpl]java.lang.String dynamicTaskNameParam;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]7)
    private [CtTypeReferenceImpl]java.lang.String caseValueParam;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]8)
    private [CtTypeReferenceImpl]java.lang.String caseExpression;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]22)
    private [CtTypeReferenceImpl]java.lang.String scriptExpression;

    [CtClassImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoMessage(wrapper = [CtLiteralImpl]true)
    public static class WorkflowTaskList {
        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> getTasks() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]tasks;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> tasks) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tasks = [CtVariableReadImpl]tasks;
        }

        [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]1)
        private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> tasks;
    }

    [CtFieldImpl][CtCommentImpl]// Populates for the tasks of the decision type
    [CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]9)
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.[CtAnnotationImpl]@javax.validation.Valid
    List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.[CtAnnotationImpl]@javax.validation.Valid
    WorkflowTask>> decisionCases = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();

    [CtFieldImpl][CtAnnotationImpl]@java.lang.Deprecated
    private [CtTypeReferenceImpl]java.lang.String dynamicForkJoinTasksParam;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]10)
    private [CtTypeReferenceImpl]java.lang.String dynamicForkTasksParam;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]11)
    private [CtTypeReferenceImpl]java.lang.String dynamicForkTasksInputParamName;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]12)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.[CtAnnotationImpl]@javax.validation.Valid
    WorkflowTask> defaultCase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]13)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.[CtAnnotationImpl]@javax.validation.Valid
    List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.[CtAnnotationImpl]@javax.validation.Valid
    WorkflowTask>> forkTasks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]14)
    [CtAnnotationImpl]@javax.validation.constraints.PositiveOrZero
    private [CtTypeReferenceImpl]int startDelay;[CtCommentImpl]// No. of seconds (at-least) to wait before starting a task.


    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]15)
    [CtAnnotationImpl]@javax.validation.Valid
    private [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.SubWorkflowParams subWorkflowParam;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]16)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> joinOn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]17)
    private [CtTypeReferenceImpl]java.lang.String sink;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]18)
    private [CtTypeReferenceImpl]boolean optional = [CtLiteralImpl]false;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]19)
    private [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.tasks.TaskDef taskDefinition;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]20)
    private [CtTypeReferenceImpl]java.lang.Boolean rateLimited;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]21)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> defaultExclusiveJoinTask = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]23)
    private [CtTypeReferenceImpl]java.lang.Boolean asyncComplete = [CtLiteralImpl]false;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]24)
    private [CtTypeReferenceImpl]java.lang.String loopCondition;

    [CtFieldImpl][CtAnnotationImpl]@com.github.vmg.protogen.annotations.ProtoField(id = [CtLiteralImpl]25)
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> loopOver = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the name
     */
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param name
     * 		the name to set
     */
    public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the taskReferenceName
     */
    public [CtTypeReferenceImpl]java.lang.String getTaskReferenceName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]taskReferenceName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param taskReferenceName
     * 		the taskReferenceName to set
     */
    public [CtTypeReferenceImpl]void setTaskReferenceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskReferenceName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.taskReferenceName = [CtVariableReadImpl]taskReferenceName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the description
     */
    public [CtTypeReferenceImpl]java.lang.String getDescription() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]description;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param description
     * 		the description to set
     */
    public [CtTypeReferenceImpl]void setDescription([CtParameterImpl][CtTypeReferenceImpl]java.lang.String description) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.description = [CtVariableReadImpl]description;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the inputParameters
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> getInputParameters() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]inputParameters;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param inputParameters
     * 		the inputParameters to set
     */
    public [CtTypeReferenceImpl]void setInputParameters([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> inputParameters) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputParameters = [CtVariableReadImpl]inputParameters;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the type
     */
    public [CtTypeReferenceImpl]java.lang.String getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]type;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setWorkflowTaskType([CtParameterImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.TaskType type) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtInvocationImpl][CtVariableReadImpl]type.name();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param type
     * 		the type to set
     */
    public [CtTypeReferenceImpl]void setType([CtParameterImpl][CtAnnotationImpl]@javax.validation.constraints.NotEmpty(message = [CtLiteralImpl]"WorkTask type cannot be null or empty")
    [CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the decisionCases
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> getDecisionCases() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]decisionCases;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param decisionCases
     * 		the decisionCases to set
     */
    public [CtTypeReferenceImpl]void setDecisionCases([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> decisionCases) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.decisionCases = [CtVariableReadImpl]decisionCases;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the defaultCase
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> getDefaultCase() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultCase;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param defaultCase
     * 		the defaultCase to set
     */
    public [CtTypeReferenceImpl]void setDefaultCase([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> defaultCase) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultCase = [CtVariableReadImpl]defaultCase;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the forkTasks
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> getForkTasks() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]forkTasks;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param forkTasks
     * 		the forkTasks to set
     */
    public [CtTypeReferenceImpl]void setForkTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> forkTasks) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.forkTasks = [CtVariableReadImpl]forkTasks;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the startDelay in seconds
     */
    public [CtTypeReferenceImpl]int getStartDelay() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]startDelay;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param startDelay
     * 		the startDelay to set
     */
    public [CtTypeReferenceImpl]void setStartDelay([CtParameterImpl][CtTypeReferenceImpl]int startDelay) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startDelay = [CtVariableReadImpl]startDelay;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the dynamicTaskNameParam
     */
    public [CtTypeReferenceImpl]java.lang.String getDynamicTaskNameParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dynamicTaskNameParam;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param dynamicTaskNameParam
     * 		the dynamicTaskNameParam to set to be used by DYNAMIC tasks
     */
    public [CtTypeReferenceImpl]void setDynamicTaskNameParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicTaskNameParam) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dynamicTaskNameParam = [CtVariableReadImpl]dynamicTaskNameParam;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the caseValueParam
     */
    public [CtTypeReferenceImpl]java.lang.String getCaseValueParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]caseValueParam;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.lang.String getDynamicForkJoinTasksParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dynamicForkJoinTasksParam;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void setDynamicForkJoinTasksParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicForkJoinTasksParam) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dynamicForkJoinTasksParam = [CtVariableReadImpl]dynamicForkJoinTasksParam;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDynamicForkTasksParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dynamicForkTasksParam;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDynamicForkTasksParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicForkTasksParam) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dynamicForkTasksParam = [CtVariableReadImpl]dynamicForkTasksParam;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDynamicForkTasksInputParamName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dynamicForkTasksInputParamName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDynamicForkTasksInputParamName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicForkTasksInputParamName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dynamicForkTasksInputParamName = [CtVariableReadImpl]dynamicForkTasksInputParamName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param caseValueParam
     * 		the caseValueParam to set
     */
    public [CtTypeReferenceImpl]void setCaseValueParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String caseValueParam) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.caseValueParam = [CtVariableReadImpl]caseValueParam;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return A javascript expression for decision cases.  The result should be a scalar value that is used to decide the case branches.
     * @see #getDecisionCases()
     */
    public [CtTypeReferenceImpl]java.lang.String getCaseExpression() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]caseExpression;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param caseExpression
     * 		A javascript expression for decision cases.  The result should be a scalar value that is used to decide the case branches.
     */
    public [CtTypeReferenceImpl]void setCaseExpression([CtParameterImpl][CtTypeReferenceImpl]java.lang.String caseExpression) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.caseExpression = [CtVariableReadImpl]caseExpression;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getScriptExpression() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]scriptExpression;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setScriptExpression([CtParameterImpl][CtTypeReferenceImpl]java.lang.String expression) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scriptExpression = [CtVariableReadImpl]expression;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the subWorkflow
     */
    public [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.SubWorkflowParams getSubWorkflowParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]subWorkflowParam;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param subWorkflow
     * 		the subWorkflowParam to set
     */
    public [CtTypeReferenceImpl]void setSubWorkflowParam([CtParameterImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.SubWorkflowParams subWorkflow) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.subWorkflowParam = [CtVariableReadImpl]subWorkflow;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the joinOn
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getJoinOn() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]joinOn;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param joinOn
     * 		the joinOn to set
     */
    public [CtTypeReferenceImpl]void setJoinOn([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> joinOn) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.joinOn = [CtVariableReadImpl]joinOn;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the loopCondition
     */
    public [CtTypeReferenceImpl]java.lang.String getLoopCondition() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]loopCondition;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param loopCondition
     * 		the expression to set
     */
    public [CtTypeReferenceImpl]void setLoopCondition([CtParameterImpl][CtTypeReferenceImpl]java.lang.String loopCondition) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.loopCondition = [CtVariableReadImpl]loopCondition;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the loopOver
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> getLoopOver() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]loopOver;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param loopOver
     * 		the loopOver to set
     */
    public [CtTypeReferenceImpl]void setLoopOver([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> loopOver) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.loopOver = [CtVariableReadImpl]loopOver;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Sink value for the EVENT type of task
     */
    public [CtTypeReferenceImpl]java.lang.String getSink() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sink;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sink
     * 		Name of the sink
     */
    public [CtTypeReferenceImpl]void setSink([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sink) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sink = [CtVariableReadImpl]sink;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return whether wait for an external event to complete the task, for EVENT and HTTP tasks
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isAsyncComplete() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]asyncComplete;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAsyncComplete([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean asyncComplete) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.asyncComplete = [CtVariableReadImpl]asyncComplete;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return If the task is optional.  When set to true, the workflow execution continues even when the task is in failed status.
     */
    public [CtTypeReferenceImpl]boolean isOptional() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]optional;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Task definition associated to the Workflow Task
     */
    public [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.tasks.TaskDef getTaskDefinition() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]taskDefinition;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param taskDefinition
     * 		Task definition
     */
    public [CtTypeReferenceImpl]void setTaskDefinition([CtParameterImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.tasks.TaskDef taskDefinition) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.taskDefinition = [CtVariableReadImpl]taskDefinition;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param optional
     * 		when set to true, the task is marked as optional
     */
    public [CtTypeReferenceImpl]void setOptional([CtParameterImpl][CtTypeReferenceImpl]boolean optional) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.optional = [CtVariableReadImpl]optional;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean getRateLimited() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]rateLimited;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRateLimited([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean rateLimited) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rateLimited = [CtVariableReadImpl]rateLimited;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Boolean isRateLimited() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]rateLimited != [CtLiteralImpl]null) && [CtFieldReadImpl]rateLimited;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getDefaultExclusiveJoinTask() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultExclusiveJoinTask;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDefaultExclusiveJoinTask([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> defaultExclusiveJoinTask) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultExclusiveJoinTask = [CtVariableReadImpl]defaultExclusiveJoinTask;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> children() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask>> workflowTaskLists = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.TaskType taskType = [CtFieldReadImpl]TaskType.USER_DEFINED;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.isSystemTask([CtFieldReadImpl]type)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]taskType = [CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.valueOf([CtFieldReadImpl]type);
        }
        [CtSwitchImpl]switch ([CtVariableReadImpl]taskType) {
            [CtCaseImpl]case [CtFieldReadImpl]DECISION :
                [CtInvocationImpl][CtVariableReadImpl]workflowTaskLists.addAll([CtInvocationImpl][CtFieldReadImpl]decisionCases.values());
                [CtInvocationImpl][CtVariableReadImpl]workflowTaskLists.add([CtFieldReadImpl]defaultCase);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]FORK_JOIN :
                [CtInvocationImpl][CtVariableReadImpl]workflowTaskLists.addAll([CtFieldReadImpl]forkTasks);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]DO_WHILE :
                [CtInvocationImpl][CtVariableReadImpl]workflowTaskLists.add([CtFieldReadImpl]loopOver);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
        [CtReturnImpl]return [CtVariableReadImpl]workflowTaskLists;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> collectTasks() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> tasks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl][CtVariableReadImpl]tasks.add([CtThisAccessImpl]this);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> workflowTaskList : [CtInvocationImpl]children()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask workflowTask : [CtVariableReadImpl]workflowTaskList) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]tasks.addAll([CtInvocationImpl][CtVariableReadImpl]workflowTask.collectTasks());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]tasks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask next([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskReferenceName, [CtParameterImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask parent) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.TaskType taskType = [CtFieldReadImpl]TaskType.USER_DEFINED;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.isSystemTask([CtFieldReadImpl]type)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]taskType = [CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.valueOf([CtFieldReadImpl]type);
        }
        [CtSwitchImpl]switch ([CtVariableReadImpl]taskType) {
            [CtCaseImpl]case [CtFieldReadImpl]DO_WHILE :
            [CtCaseImpl]case [CtFieldReadImpl]DECISION :
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> workflowTasks : [CtInvocationImpl]children()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> iterator = [CtInvocationImpl][CtVariableReadImpl]workflowTasks.iterator();
                    [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask task = [CtInvocationImpl][CtVariableReadImpl]iterator.next();
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getTaskReferenceName().equals([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
                            [CtBreakImpl]break;
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask nextTask = [CtInvocationImpl][CtVariableReadImpl]task.next([CtVariableReadImpl]taskReferenceName, [CtThisAccessImpl]this);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nextTask != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtReturnImpl]return [CtVariableReadImpl]nextTask;
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]task.has([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TaskType.DO_WHILE.name().equals([CtInvocationImpl][CtVariableReadImpl]task.getType())) [CtBlockImpl]{
                                [CtReturnImpl][CtCommentImpl]// come here means task is DO_WHILE task and `taskReferenceName` is the last task in
                                [CtCommentImpl]// this DO_WHILE task, because DO_WHILE task need to be executed to decide whether to
                                [CtCommentImpl]// schedule next iteration, so we just return the DO_WHILE task, and then ignore
                                [CtCommentImpl]// generating this task again in deciderService.getNextTask()
                                return [CtVariableReadImpl]task;
                            }
                            [CtBreakImpl]break;
                        }
                    } 
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]iterator.next();
                    }
                }
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]FORK_JOIN :
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> workflowTasks : [CtInvocationImpl]children()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> iterator = [CtInvocationImpl][CtVariableReadImpl]workflowTasks.iterator();
                    [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask task = [CtInvocationImpl][CtVariableReadImpl]iterator.next();
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]task.getTaskReferenceName().equals([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                            [CtBreakImpl]break;
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask nextTask = [CtInvocationImpl][CtVariableReadImpl]task.next([CtVariableReadImpl]taskReferenceName, [CtThisAccessImpl]this);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nextTask != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtReturnImpl]return [CtVariableReadImpl]nextTask;
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]task.has([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TaskType.DO_WHILE.name().equals([CtInvocationImpl][CtVariableReadImpl]task.getType())) [CtBlockImpl]{
                                [CtReturnImpl][CtCommentImpl]// same reason as above
                                return [CtVariableReadImpl]task;
                            }
                            [CtBreakImpl]break;
                        }
                    } 
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]iterator.next();
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]found && [CtBinaryOperatorImpl]([CtVariableReadImpl]parent != [CtLiteralImpl]null)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]parent.next([CtFieldReadImpl][CtThisAccessImpl]this.taskReferenceName, [CtVariableReadImpl]parent);[CtCommentImpl]// we need to return join task... -- get my sibling from my parent..

                    }
                }
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]DYNAMIC :
            [CtCaseImpl]case [CtFieldReadImpl]SIMPLE :
                [CtReturnImpl]return [CtLiteralImpl]null;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean has([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskReferenceName) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getTaskReferenceName().equals([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.TaskType taskType = [CtFieldReadImpl]TaskType.USER_DEFINED;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.isSystemTask([CtFieldReadImpl]type)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]taskType = [CtInvocationImpl][CtTypeAccessImpl]com.netflix.conductor.common.metadata.workflow.TaskType.valueOf([CtFieldReadImpl]type);
        }
        [CtSwitchImpl]switch ([CtVariableReadImpl]taskType) {
            [CtCaseImpl]case [CtFieldReadImpl]DECISION :
            [CtCaseImpl]case [CtFieldReadImpl]DO_WHILE :
            [CtCaseImpl]case [CtFieldReadImpl]FORK_JOIN :
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> childx : [CtInvocationImpl]children()) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask child : [CtVariableReadImpl]childx) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]child.has([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]true;
                        }
                    }
                }
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask get([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskReferenceName) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getTaskReferenceName().equals([CtVariableReadImpl]taskReferenceName)) [CtBlockImpl]{
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask> childx : [CtInvocationImpl]children()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask child : [CtVariableReadImpl]childx) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask found = [CtInvocationImpl][CtVariableReadImpl]child.get([CtVariableReadImpl]taskReferenceName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]found != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]found;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]name + [CtLiteralImpl]"/") + [CtFieldReadImpl]taskReferenceName;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask that = [CtVariableReadImpl](([CtTypeReferenceImpl]com.netflix.conductor.common.metadata.workflow.WorkflowTask) (o));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getStartDelay() == [CtInvocationImpl][CtVariableReadImpl]that.getStartDelay()) && [CtBinaryOperatorImpl]([CtInvocationImpl]isOptional() == [CtInvocationImpl][CtVariableReadImpl]that.isOptional())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getName(), [CtInvocationImpl][CtVariableReadImpl]that.getName())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getTaskReferenceName(), [CtInvocationImpl][CtVariableReadImpl]that.getTaskReferenceName())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDescription(), [CtInvocationImpl][CtVariableReadImpl]that.getDescription())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getInputParameters(), [CtInvocationImpl][CtVariableReadImpl]that.getInputParameters())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getType(), [CtInvocationImpl][CtVariableReadImpl]that.getType())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDynamicTaskNameParam(), [CtInvocationImpl][CtVariableReadImpl]that.getDynamicTaskNameParam())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getCaseValueParam(), [CtInvocationImpl][CtVariableReadImpl]that.getCaseValueParam())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getCaseExpression(), [CtInvocationImpl][CtVariableReadImpl]that.getCaseExpression())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDecisionCases(), [CtInvocationImpl][CtVariableReadImpl]that.getDecisionCases())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDynamicForkJoinTasksParam(), [CtInvocationImpl][CtVariableReadImpl]that.getDynamicForkJoinTasksParam())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDynamicForkTasksParam(), [CtInvocationImpl][CtVariableReadImpl]that.getDynamicForkTasksParam())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDynamicForkTasksInputParamName(), [CtInvocationImpl][CtVariableReadImpl]that.getDynamicForkTasksInputParamName())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDefaultCase(), [CtInvocationImpl][CtVariableReadImpl]that.getDefaultCase())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getForkTasks(), [CtInvocationImpl][CtVariableReadImpl]that.getForkTasks())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getSubWorkflowParam(), [CtInvocationImpl][CtVariableReadImpl]that.getSubWorkflowParam())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getJoinOn(), [CtInvocationImpl][CtVariableReadImpl]that.getJoinOn())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getSink(), [CtInvocationImpl][CtVariableReadImpl]that.getSink())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]isAsyncComplete(), [CtInvocationImpl][CtVariableReadImpl]that.isAsyncComplete())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getDefaultExclusiveJoinTask(), [CtInvocationImpl][CtVariableReadImpl]that.getDefaultExclusiveJoinTask());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtInvocationImpl]getName(), [CtInvocationImpl]getTaskReferenceName(), [CtInvocationImpl]getDescription(), [CtInvocationImpl]getInputParameters(), [CtInvocationImpl]getType(), [CtInvocationImpl]getDynamicTaskNameParam(), [CtInvocationImpl]getCaseValueParam(), [CtInvocationImpl]getCaseExpression(), [CtInvocationImpl]getDecisionCases(), [CtInvocationImpl]getDynamicForkJoinTasksParam(), [CtInvocationImpl]getDynamicForkTasksParam(), [CtInvocationImpl]getDynamicForkTasksInputParamName(), [CtInvocationImpl]getDefaultCase(), [CtInvocationImpl]getForkTasks(), [CtInvocationImpl]getStartDelay(), [CtInvocationImpl]getSubWorkflowParam(), [CtInvocationImpl]getJoinOn(), [CtInvocationImpl]getSink(), [CtInvocationImpl]isAsyncComplete(), [CtInvocationImpl]isOptional(), [CtInvocationImpl]getDefaultExclusiveJoinTask());
    }
}
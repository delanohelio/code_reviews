[CompilationUnitImpl][CtCommentImpl]/* Copyright 2017 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.jbpm.runtime.manager.impl.jpa;
[CtUnresolvedImport]import javax.persistence.Entity;
[CtUnresolvedImport]import javax.persistence.Lob;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import javax.persistence.Column;
[CtUnresolvedImport]import javax.persistence.GenerationType;
[CtUnresolvedImport]import javax.persistence.Table;
[CtUnresolvedImport]import javax.persistence.GeneratedValue;
[CtUnresolvedImport]import javax.persistence.Index;
[CtUnresolvedImport]import org.kie.internal.runtime.error.ExecutionError;
[CtUnresolvedImport]import javax.persistence.SequenceGenerator;
[CtUnresolvedImport]import javax.persistence.Temporal;
[CtImportImpl]import java.io.Serializable;
[CtUnresolvedImport]import javax.persistence.Id;
[CtClassImpl][CtAnnotationImpl]@javax.persistence.Entity
[CtAnnotationImpl]@javax.persistence.Table(name = [CtLiteralImpl]"ExecutionErrorInfo", indexes = [CtNewArrayImpl]{ [CtAnnotationImpl]@javax.persistence.Index(name = [CtLiteralImpl]"IDX_ErrorInfo_pInstId", columnList = [CtLiteralImpl]"PROCESS_INST_ID"), [CtAnnotationImpl]@javax.persistence.Index(name = [CtLiteralImpl]"IDX_ErrorInfo_errorAck", columnList = [CtLiteralImpl]"ERROR_ACK") })
[CtAnnotationImpl]@javax.persistence.SequenceGenerator(name = [CtLiteralImpl]"execErrorInfoIdSeq", sequenceName = [CtLiteralImpl]"EXEC_ERROR_INFO_ID_SEQ", allocationSize = [CtLiteralImpl]1)
public class ExecutionErrorInfo extends [CtTypeReferenceImpl]org.kie.internal.runtime.error.ExecutionError implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]6669858787722894023L;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int ERROR_LOG_LENGTH = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"org.kie.jbpm.error.log.length", [CtLiteralImpl]"255"));

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Long id;

    [CtConstructorImpl]public ExecutionErrorInfo() [CtBlockImpl]{
    }

    [CtConstructorImpl]public ExecutionErrorInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String deploymentId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long processInstanceId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String processId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long activityId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String activityName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long jobId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorMessage, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String error, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date errorDate, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long initActivityId) [CtBlockImpl]{
        [CtInvocationImpl]super();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.errorId = [CtVariableReadImpl]errorId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.deploymentId = [CtVariableReadImpl]deploymentId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.processInstanceId = [CtVariableReadImpl]processInstanceId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.processId = [CtVariableReadImpl]processId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activityId = [CtVariableReadImpl]activityId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activityName = [CtVariableReadImpl]activityName;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jobId = [CtVariableReadImpl]jobId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.error = [CtVariableReadImpl]error;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.errorDate = [CtVariableReadImpl]errorDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.acknowledged = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Short([CtLiteralImpl]"0");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.initActivityId = [CtVariableReadImpl]initActivityId;
        [CtInvocationImpl][CtThisAccessImpl]this.setErrorMessage([CtVariableReadImpl]errorMessage);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Id
    [CtAnnotationImpl]@javax.persistence.GeneratedValue(strategy = [CtFieldReadImpl]javax.persistence.GenerationType.AUTO, generator = [CtLiteralImpl]"execErrorInfoIdSeq")
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"id")
    public [CtTypeReferenceImpl]java.lang.Long getId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setId([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long id) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_ID")
    public [CtTypeReferenceImpl]java.lang.String getErrorId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.errorId;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_TYPE")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getType();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"DEPLOYMENT_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getDeploymentId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getDeploymentId();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"PROCESS_INST_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getProcessInstanceId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getProcessInstanceId();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ACTIVITY_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getActivityId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getActivityId();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setErrorMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorMessage) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String trimmedErrorMessage = [CtVariableReadImpl]errorMessage;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]trimmedErrorMessage != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]trimmedErrorMessage.length() > [CtFieldReadImpl]ERROR_LOG_LENGTH)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]trimmedErrorMessage = [CtInvocationImpl][CtVariableReadImpl]trimmedErrorMessage.substring([CtLiteralImpl]0, [CtFieldReadImpl]ERROR_LOG_LENGTH);
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.setErrorMessage([CtVariableReadImpl]trimmedErrorMessage);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_MSG")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getErrorMessage() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getErrorMessage();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Lob
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_INFO", length = [CtLiteralImpl]65535)
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getError() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getError();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_ACK")
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.Short getAcknowledged() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getAcknowledged();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_ACK_BY")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getAcknowledgedBy() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getAcknowledgedBy();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_ACK_AT")
    [CtAnnotationImpl]@javax.persistence.Temporal([CtFieldReadImpl]javax.persistence.TemporalType.TIMESTAMP)
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Date getAcknowledgedAt() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getAcknowledgedAt();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"PROCESS_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getProcessId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getProcessId();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ACTIVITY_NAME")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getActivityName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getActivityName();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ERROR_DATE")
    [CtAnnotationImpl]@javax.persistence.Temporal([CtFieldReadImpl]javax.persistence.TemporalType.TIMESTAMP)
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Date getErrorDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getErrorDate();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setErrorId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.errorId = [CtVariableReadImpl]errorId;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"JOB_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getJobId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getJobId();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"INIT_ACTIVITY_ID")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getInitActivityId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getInitActivityId();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ExecutionErrorInfo [errorId=" + [CtFieldReadImpl]errorId) + [CtLiteralImpl]", type=") + [CtFieldReadImpl]type) + [CtLiteralImpl]", deploymentId=") + [CtFieldReadImpl]deploymentId) + [CtLiteralImpl]", processInstanceId=") + [CtFieldReadImpl]processInstanceId) + [CtLiteralImpl]", initActivityId=") + [CtFieldReadImpl]initActivityId) + [CtLiteralImpl]", processId=") + [CtFieldReadImpl]processId) + [CtLiteralImpl]", activityId=") + [CtFieldReadImpl]activityId) + [CtLiteralImpl]", activityName=") + [CtFieldReadImpl]activityName) + [CtLiteralImpl]", errorMessage=") + [CtFieldReadImpl]errorMessage) + [CtLiteralImpl]", acknowledged=") + [CtFieldReadImpl]acknowledged) + [CtLiteralImpl]"]";
    }
}
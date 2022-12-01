[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2020 Temporal Technologies, Inc. All Rights Reserved.

 Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 Modifications copyright (C) 2017 Uber Technologies, Inc.

 Licensed under the Apache License, Version 2.0 (the "License"). You may not
 use this file except in compliance with the License. A copy of the License is
 located at

 http://aws.amazon.com/apache2.0

 or in the "license" file accompanying this file. This file is distributed on
 an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 express or implied. See the License for the specific language governing
 permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package io.temporal.internal.testservice;
[CtUnresolvedImport]import io.temporal.api.common.v1.Payloads;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionTimedOutEventAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondWorkflowTaskFailedRequest;
[CtUnresolvedImport]import io.temporal.api.history.v1.ChildWorkflowExecutionTimedOutEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskCancelRequestedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.QueryWorkflowRequest;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedByIdRequest;
[CtUnresolvedImport]import io.temporal.api.common.v1.RetryPolicy;
[CtUnresolvedImport]import io.temporal.api.history.v1.ExternalWorkflowExecutionSignaledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowTaskStartedEventAttributes;
[CtUnresolvedImport]import io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId;
[CtUnresolvedImport]import io.temporal.api.enums.v1.CancelExternalWorkflowExecutionFailedCause;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.PollActivityTaskQueueResponse;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledByIdRequest;
[CtUnresolvedImport]import io.temporal.api.enums.v1.StartChildWorkflowExecutionFailedCause;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.command.v1.RequestCancelActivityTaskCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskFailedRequest;
[CtImportImpl]import static io.temporal.internal.testservice.StateMachines.Action.*;
[CtUnresolvedImport]import io.temporal.api.command.v1.CancelTimerCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.command.v1.FailWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionCanceledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.TimerStartedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.ChildWorkflowExecutionFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RequestCancelWorkflowExecutionRequest;
[CtUnresolvedImport]import io.temporal.api.history.v1.TimerCanceledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import io.grpc.Status;
[CtUnresolvedImport]import io.temporal.api.command.v1.SignalExternalWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.common.v1.WorkflowExecution;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskTimedOutEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.enums.v1.SignalExternalWorkflowExecutionFailedCause;
[CtUnresolvedImport]import io.temporal.api.failure.v1.ApplicationFailureInfo;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowTaskCompletedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.QueryWorkflowResponse;
[CtUnresolvedImport]import io.temporal.api.command.v1.ContinueAsNewWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.TerminateWorkflowExecutionRequest;
[CtUnresolvedImport]import io.temporal.api.history.v1.ChildWorkflowExecutionStartedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskCanceledEventAttributes;
[CtUnresolvedImport]import io.temporal.internal.testservice.TestWorkflowStore.ActivityTask;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedRequest;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskStartedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.enums.v1.TimeoutType;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskScheduledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.TimerFiredEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.StartChildWorkflowExecutionInitiatedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionStartedEventAttributes;
[CtImportImpl]import java.util.concurrent.ForkJoinPool;
[CtUnresolvedImport]import io.temporal.api.history.v1.ActivityTaskCompletedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionCancelRequestedEventAttributes;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskFailedByIdRequest;
[CtUnresolvedImport]import io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionCompletedEventAttributes;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import io.temporal.api.enums.v1.RetryState;
[CtUnresolvedImport]import io.temporal.internal.testservice.TestWorkflowStore.WorkflowTask;
[CtUnresolvedImport]import io.temporal.api.history.v1.StartChildWorkflowExecutionFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.taskqueue.v1.StickyExecutionAttributes;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest;
[CtUnresolvedImport]import io.temporal.api.command.v1.RequestCancelExternalWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowTaskTimedOutEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionContinuedAsNewEventAttributes;
[CtUnresolvedImport]import io.temporal.api.command.v1.ScheduleActivityTaskCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.errordetails.v1.QueryFailedFailure;
[CtUnresolvedImport]import io.temporal.api.failure.v1.Failure;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondWorkflowTaskCompletedRequest;
[CtUnresolvedImport]import io.temporal.api.command.v1.StartTimerCommandAttributes;
[CtImportImpl]import static io.temporal.internal.testservice.StateMachines.State.*;
[CtUnresolvedImport]import io.temporal.api.history.v1.ChildWorkflowExecutionCompletedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowExecutionTerminatedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.failure.v1.TimeoutFailureInfo;
[CtUnresolvedImport]import io.temporal.api.history.v1.HistoryEvent;
[CtUnresolvedImport]import io.grpc.StatusRuntimeException;
[CtUnresolvedImport]import io.temporal.api.history.v1.SignalExternalWorkflowExecutionFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.internal.common.StatusUtils;
[CtUnresolvedImport]import io.temporal.api.history.v1.ChildWorkflowExecutionCanceledEventAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.GetWorkflowExecutionHistoryRequest;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.PollActivityTaskQueueRequest;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueRequest;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse;
[CtUnresolvedImport]import io.temporal.api.command.v1.StartChildWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.enums.v1.EventType;
[CtUnresolvedImport]import io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.ExternalWorkflowExecutionCancelRequestedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.History;
[CtUnresolvedImport]import io.temporal.api.command.v1.CompleteWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.history.v1.WorkflowTaskFailedEventAttributes;
[CtUnresolvedImport]import io.temporal.api.query.v1.WorkflowQueryResult;
[CtUnresolvedImport]import io.temporal.api.command.v1.CancelWorkflowExecutionCommandAttributes;
[CtUnresolvedImport]import io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledRequest;
[CtClassImpl]class StateMachines {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.class);

    [CtFieldImpl]static final [CtTypeReferenceImpl]int NO_EVENT_ID = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_WORKFLOW_EXECUTION_TIMEOUT_SECONDS = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]10 * [CtLiteralImpl]365) * [CtLiteralImpl]24) * [CtLiteralImpl]3600;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_WORKFLOW_TASK_TIMEOUT_SECONDS = [CtLiteralImpl]10;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int MAX_WORKFLOW_TASK_TIMEOUT_SECONDS = [CtLiteralImpl]60;

    [CtEnumImpl]enum State {

        [CtEnumValueImpl]NONE,
        [CtEnumValueImpl]INITIATED,
        [CtEnumValueImpl]INITIATED_QUERY_ONLY,
        [CtEnumValueImpl]STARTED,
        [CtEnumValueImpl]STARTED_QUERY_ONLY,
        [CtEnumValueImpl]FAILED,
        [CtEnumValueImpl]TIMED_OUT,
        [CtEnumValueImpl]CANCELLATION_REQUESTED,
        [CtEnumValueImpl]CANCELED,
        [CtEnumValueImpl]COMPLETED,
        [CtEnumValueImpl]CONTINUED_AS_NEW,
        [CtEnumValueImpl]TERMINATED;}

    [CtEnumImpl]enum Action {

        [CtEnumValueImpl]INITIATE,
        [CtEnumValueImpl]START,
        [CtEnumValueImpl]FAIL,
        [CtEnumValueImpl]TIME_OUT,
        [CtEnumValueImpl]REQUEST_CANCELLATION,
        [CtEnumValueImpl]CANCEL,
        [CtEnumValueImpl]TERMINATE,
        [CtEnumValueImpl]UPDATE,
        [CtEnumValueImpl]COMPLETE,
        [CtEnumValueImpl]CONTINUE_AS_NEW,
        [CtEnumValueImpl]QUERY;}

    [CtClassImpl]static final class WorkflowData {
        [CtFieldImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]TestServiceRetryState> retryState;

        [CtFieldImpl][CtTypeReferenceImpl]int backoffStartIntervalInSeconds;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String cronSchedule;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads lastCompletionResult;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String originalExecutionRunId;

        [CtFieldImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> continuedExecutionRunId;

        [CtConstructorImpl]WorkflowData([CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]TestServiceRetryState> retryState, [CtParameterImpl][CtTypeReferenceImpl]int backoffStartIntervalInSeconds, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cronSchedule, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads lastCompletionResult, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String originalExecutionRunId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> continuedExecutionRunId) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.retryState = [CtVariableReadImpl]retryState;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.backoffStartIntervalInSeconds = [CtVariableReadImpl]backoffStartIntervalInSeconds;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cronSchedule = [CtVariableReadImpl]cronSchedule;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lastCompletionResult = [CtVariableReadImpl]lastCompletionResult;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.originalExecutionRunId = [CtVariableReadImpl]originalExecutionRunId;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.continuedExecutionRunId = [CtVariableReadImpl]continuedExecutionRunId;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"WorkflowData{" + [CtLiteralImpl]"retryState=") + [CtFieldReadImpl]retryState) + [CtLiteralImpl]", backoffStartIntervalInSeconds=") + [CtFieldReadImpl]backoffStartIntervalInSeconds) + [CtLiteralImpl]", cronSchedule='") + [CtFieldReadImpl]cronSchedule) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", lastCompletionResult=") + [CtFieldReadImpl]lastCompletionResult) + [CtLiteralImpl]", originalExecutionRunId='") + [CtFieldReadImpl]originalExecutionRunId) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", continuedExecutionRunId=") + [CtFieldReadImpl]continuedExecutionRunId) + [CtLiteralImpl]'}';
        }
    }

    [CtClassImpl]static final class WorkflowTaskData {
        [CtFieldImpl]final [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore store;

        [CtFieldImpl][CtTypeReferenceImpl]boolean workflowCompleted;

        [CtFieldImpl][CtJavaDocImpl]/**
         * id of the last started event which completed successfully
         */
        [CtTypeReferenceImpl]long lastSuccessfulStartedEventId;

        [CtFieldImpl]final [CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startRequest;

        [CtFieldImpl][CtTypeReferenceImpl]long startedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl][CtTypeReferenceImpl]PollWorkflowTaskQueueResponse.Builder workflowTask;

        [CtFieldImpl][CtJavaDocImpl]/**
         * Events that are added during execution of a workflow task. They have to be buffered to be
         * added after the events generated by a workflow task. Without this the determinism will be
         * broken on replay.
         */
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]RequestContext> bufferedEvents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

        [CtFieldImpl][CtTypeReferenceImpl]long scheduledEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl][CtTypeReferenceImpl]int attempt;

        [CtFieldImpl][CtJavaDocImpl]/**
         * Query requests received during workflow task processing (after start)
         */
        final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery> queryBuffer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

        [CtFieldImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery> consistentQueryRequests = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

        [CtConstructorImpl]WorkflowTaskData([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowStore store, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startRequest) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.store = [CtVariableReadImpl]store;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startRequest = [CtVariableReadImpl]startRequest;
        }

        [CtMethodImpl][CtTypeReferenceImpl]void clear() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]startedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;
            [CtAssignmentImpl][CtFieldWriteImpl]workflowTask = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtFieldWriteImpl]scheduledEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;
            [CtAssignmentImpl][CtFieldWriteImpl]attempt = [CtLiteralImpl]0;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"WorkflowTaskData{" + [CtLiteralImpl]"store=") + [CtFieldReadImpl]store) + [CtLiteralImpl]", workflowCompleted=") + [CtFieldReadImpl]workflowCompleted) + [CtLiteralImpl]", lastSuccessfulStartedEventId=") + [CtFieldReadImpl]lastSuccessfulStartedEventId) + [CtLiteralImpl]", startRequest=") + [CtFieldReadImpl]startRequest) + [CtLiteralImpl]", startedEventId=") + [CtFieldReadImpl]startedEventId) + [CtLiteralImpl]", workflowTask=") + [CtFieldReadImpl]workflowTask) + [CtLiteralImpl]", bufferedEvents=") + [CtFieldReadImpl]bufferedEvents) + [CtLiteralImpl]", scheduledEventId=") + [CtFieldReadImpl]scheduledEventId) + [CtLiteralImpl]", attempt=") + [CtFieldReadImpl]attempt) + [CtLiteralImpl]", queryBuffer=") + [CtFieldReadImpl]queryBuffer) + [CtLiteralImpl]", consistentQueryRequests=") + [CtFieldReadImpl]consistentQueryRequests) + [CtLiteralImpl]'}';
        }
    }

    [CtClassImpl]static final class ActivityTaskData {
        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startWorkflowExecutionRequest;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskScheduledEventAttributes scheduledEvent;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.ActivityTask activityTask;

        [CtFieldImpl]final [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore store;

        [CtFieldImpl][CtTypeReferenceImpl]long scheduledEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl][CtTypeReferenceImpl]long startedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl]public [CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent startedEvent;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads heartbeatDetails;

        [CtFieldImpl][CtTypeReferenceImpl]long lastHeartbeatTime;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestServiceRetryState retryState;

        [CtFieldImpl][CtTypeReferenceImpl]long nextBackoffIntervalSeconds;

        [CtConstructorImpl]ActivityTaskData([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowStore store, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startWorkflowExecutionRequest) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.store = [CtVariableReadImpl]store;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startWorkflowExecutionRequest = [CtVariableReadImpl]startWorkflowExecutionRequest;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ActivityTaskData{" + [CtLiteralImpl]"startWorkflowExecutionRequest=") + [CtFieldReadImpl]startWorkflowExecutionRequest) + [CtLiteralImpl]", scheduledEvent=") + [CtFieldReadImpl]scheduledEvent) + [CtLiteralImpl]", activityTask=") + [CtFieldReadImpl]activityTask) + [CtLiteralImpl]", store=") + [CtFieldReadImpl]store) + [CtLiteralImpl]", scheduledEventId=") + [CtFieldReadImpl]scheduledEventId) + [CtLiteralImpl]", startedEventId=") + [CtFieldReadImpl]startedEventId) + [CtLiteralImpl]", startedEvent=") + [CtFieldReadImpl]startedEvent) + [CtLiteralImpl]", heartbeatDetails=") + [CtFieldReadImpl]heartbeatDetails) + [CtLiteralImpl]", lastHeartbeatTime=") + [CtFieldReadImpl]lastHeartbeatTime) + [CtLiteralImpl]", retryState=") + [CtFieldReadImpl]retryState) + [CtLiteralImpl]", nextBackoffIntervalSeconds=") + [CtFieldReadImpl]nextBackoffIntervalSeconds) + [CtLiteralImpl]'}';
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]int getAttempt() [CtBlockImpl]{
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]retryState != [CtLiteralImpl]null ? [CtInvocationImpl][CtFieldReadImpl]retryState.getAttempt() : [CtLiteralImpl]0;
        }
    }

    [CtClassImpl]static final class SignalExternalData {
        [CtFieldImpl][CtTypeReferenceImpl]long initiatedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl]public [CtTypeReferenceImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SignalExternalData{" + [CtLiteralImpl]"initiatedEventId=") + [CtFieldReadImpl]initiatedEventId) + [CtLiteralImpl]", initiatedEvent=") + [CtFieldReadImpl]initiatedEvent) + [CtLiteralImpl]'}';
        }
    }

    [CtClassImpl]static final class CancelExternalData {
        [CtFieldImpl][CtTypeReferenceImpl]long initiatedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;

        [CtFieldImpl]public [CtTypeReferenceImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"CancelExternalData{" + [CtLiteralImpl]"initiatedEventId=") + [CtFieldReadImpl]initiatedEventId) + [CtLiteralImpl]", initiatedEvent=") + [CtFieldReadImpl]initiatedEvent) + [CtLiteralImpl]'}';
        }
    }

    [CtClassImpl]static final class ChildWorkflowData {
        [CtFieldImpl]final [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowService service;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionInitiatedEventAttributes initiatedEvent;

        [CtFieldImpl][CtTypeReferenceImpl]long initiatedEventId;

        [CtFieldImpl][CtTypeReferenceImpl]long startedEventId;

        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.WorkflowExecution execution;

        [CtConstructorImpl]public ChildWorkflowData([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowService service) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.service = [CtVariableReadImpl]service;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ChildWorkflowData{" + [CtLiteralImpl]"service=") + [CtFieldReadImpl]service) + [CtLiteralImpl]", initiatedEvent=") + [CtFieldReadImpl]initiatedEvent) + [CtLiteralImpl]", initiatedEventId=") + [CtFieldReadImpl]initiatedEventId) + [CtLiteralImpl]", startedEventId=") + [CtFieldReadImpl]startedEventId) + [CtLiteralImpl]", execution=") + [CtFieldReadImpl]execution) + [CtLiteralImpl]'}';
        }
    }

    [CtClassImpl]static final class TimerData {
        [CtFieldImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.TimerStartedEventAttributes startedEvent;

        [CtFieldImpl]public [CtTypeReferenceImpl]long startedEventId;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"TimerData{" + [CtLiteralImpl]"startedEvent=") + [CtFieldReadImpl]startedEvent) + [CtLiteralImpl]", startedEventId=") + [CtFieldReadImpl]startedEventId) + [CtLiteralImpl]'}';
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData> newWorkflowStateMachine([CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtVariableReadImpl]data).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::startWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.CONTINUE_AS_NEW, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CONTINUED_AS_NEW, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::continueAsNewWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.REQUEST_CANCELLATION, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::requestWorkflowCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TERMINATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TERMINATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::terminateWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.CANCEL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::cancelWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TERMINATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TERMINATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::terminateWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutWorkflow);
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData> newCommandStateMachine([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowStore store, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startRequest) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// TODO(maxim): Uncomment once the server supports consistent query only workflow tasks
        [CtCommentImpl]// .add(NONE, QUERY, INITIATED_QUERY_ONLY, StateMachines::scheduleQueryWorkflowTask)
        [CtCommentImpl]// .add(INITIATED_QUERY_ONLY, QUERY, INITIATED_QUERY_ONLY,
        [CtCommentImpl]// StateMachines::queryWhileScheduled)
        [CtCommentImpl]// .add(
        [CtCommentImpl]// INITIATED_QUERY_ONLY,
        [CtCommentImpl]// INITIATE,
        [CtCommentImpl]// INITIATED,
        [CtCommentImpl]// StateMachines::convertQueryWorkflowTaskToReal)
        [CtCommentImpl]// .add(
        [CtCommentImpl]// INITIATED_QUERY_ONLY,
        [CtCommentImpl]// START,
        [CtCommentImpl]// STARTED_QUERY_ONLY,
        [CtCommentImpl]// StateMachines::startQueryOnlyWorkflowTask)
        [CtCommentImpl]// .add(STARTED_QUERY_ONLY, INITIATE, STARTED_QUERY_ONLY,
        [CtCommentImpl]// StateMachines::needsWorkflowTask)
        [CtCommentImpl]// .add(STARTED_QUERY_ONLY, QUERY, STARTED_QUERY_ONLY,
        [CtCommentImpl]// StateMachines::needsWorkflowTaskDueToQuery)
        [CtCommentImpl]// .add(STARTED_QUERY_ONLY, FAIL, NONE, StateMachines::failQueryWorkflowTask)
        [CtCommentImpl]// .add(STARTED_QUERY_ONLY, TIME_OUT, NONE, StateMachines::failQueryWorkflowTask)
        [CtCommentImpl]// .add(STARTED_QUERY_ONLY, COMPLETE, NONE, StateMachines::completeQuery)
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData([CtVariableReadImpl]store, [CtVariableReadImpl]startRequest)).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::scheduleWorkflowTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.QUERY, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::bufferQuery).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::noop).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.QUERY, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::queryWhileScheduled).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::startWorkflowTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeWorkflowTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failWorkflowTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutWorkflowTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::needsWorkflowTask);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData> newActivityStateMachine([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowStore store, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest workflowStartedEvent) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// Transitions to initiated in case of a retry
        [CtInvocationImpl][CtCommentImpl]// Transitions to initiated in case of the a retry
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData([CtVariableReadImpl]store, [CtVariableReadImpl]workflowStartedEvent)).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::scheduleActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::startActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.REQUEST_CANCELLATION, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::requestActivityCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtNewArrayImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State[]{ [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED }, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtNewArrayImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State[]{ [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED }, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.UPDATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::heartbeatActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.REQUEST_CANCELLATION, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::requestActivityCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.CANCEL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::reportActivityTaskCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.UPDATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::heartbeatActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutActivityTask).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELLATION_REQUESTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failActivityTask);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData> newChildWorkflowStateMachine([CtParameterImpl][CtTypeReferenceImpl]TestWorkflowService service) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData([CtVariableReadImpl]service)).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::initiateChildWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::childWorkflowStarted).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::startChildWorkflowFailed).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutChildWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::childWorkflowCompleted).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::childWorkflowFailed).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.TIME_OUT, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::timeoutChildWorkflow).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.CANCEL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::childWorkflowCanceled);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.TimerData> newTimerStateMachine() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.TimerData()).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::startTimer).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::fireTimer).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.CANCEL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.CANCELED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::cancelTimer);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.SignalExternalData> newSignalExternalStateMachine() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.SignalExternalData()).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::initiateExternalSignal).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failExternalSignal).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.COMPLETE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.COMPLETED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::completeExternalSignal);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachine<[CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.CancelExternalData> newCancelExternalStateMachine() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]StateMachine<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.CancelExternalData()).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.NONE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.INITIATE, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::initiateExternalCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.FAIL, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::failExternalCancellation).add([CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.Action.START, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.STARTED, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]io.temporal.internal.testservice.StateMachines::reportExternalCancellationRequested);
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T, [CtTypeParameterImpl]A> [CtTypeReferenceImpl]void noop([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeParameterReferenceImpl]T data, [CtParameterImpl][CtTypeParameterReferenceImpl]A a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void timeoutChildWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState retryState, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionInitiatedEventAttributes ie = [CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent;
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionTimedOutEventAttributes a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ChildWorkflowExecutionTimedOutEventAttributes.newBuilder().setNamespace([CtInvocationImpl][CtVariableReadImpl]ie.getNamespace()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).setWorkflowExecution([CtFieldReadImpl][CtVariableReadImpl]data.execution).setWorkflowType([CtInvocationImpl][CtVariableReadImpl]ie.getWorkflowType()).setRetryState([CtVariableReadImpl]retryState).setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_CHILD_WORKFLOW_EXECUTION_TIMED_OUT).setChildWorkflowExecutionTimedOutEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startChildWorkflowFailed([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionFailedEventAttributes a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionFailedEventAttributes.Builder updatedAttr = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.toBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setWorkflowType([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getWorkflowType()).setWorkflowId([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getWorkflowId());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getNamespace().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]updatedAttr.setNamespace([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getNamespace());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_START_CHILD_WORKFLOW_EXECUTION_FAILED).setStartChildWorkflowExecutionFailedEventAttributes([CtInvocationImpl][CtVariableReadImpl]updatedAttr.build()).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void childWorkflowStarted([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionStartedEventAttributes a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionStartedEventAttributes updatedAttr = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.toBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_CHILD_WORKFLOW_EXECUTION_STARTED).setChildWorkflowExecutionStartedEventAttributes([CtVariableReadImpl]updatedAttr).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.startedEventId = [CtVariableReadImpl]startedEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.execution = [CtInvocationImpl][CtVariableReadImpl]updatedAttr.getWorkflowExecution();
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void childWorkflowCompleted([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionCompletedEventAttributes a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionCompletedEventAttributes updatedAttr = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.toBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_CHILD_WORKFLOW_EXECUTION_COMPLETED).setChildWorkflowExecutionCompletedEventAttributes([CtVariableReadImpl]updatedAttr).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void childWorkflowFailed([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionFailedEventAttributes a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionFailedEventAttributes.Builder updatedAttr = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.toBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).setWorkflowExecution([CtFieldReadImpl][CtVariableReadImpl]data.execution).setWorkflowType([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getWorkflowType());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getNamespace().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]updatedAttr.setNamespace([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getNamespace());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_CHILD_WORKFLOW_EXECUTION_FAILED).setChildWorkflowExecutionFailedEventAttributes([CtInvocationImpl][CtVariableReadImpl]updatedAttr.build()).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void childWorkflowCanceled([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionCanceledEventAttributes a, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ChildWorkflowExecutionCanceledEventAttributes updatedAttr = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.toBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_CHILD_WORKFLOW_EXECUTION_CANCELED).setChildWorkflowExecutionCanceledEventAttributes([CtVariableReadImpl]updatedAttr).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void initiateChildWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.StartChildWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionInitiatedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionInitiatedEventAttributes.newBuilder().setControl([CtInvocationImpl][CtVariableReadImpl]d.getControl()).setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput()).setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId).setNamespace([CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]d.getNamespace().isEmpty() ? [CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace() : [CtInvocationImpl][CtVariableReadImpl]d.getNamespace()).setWorkflowExecutionTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowExecutionTimeoutSeconds()).setWorkflowRunTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowRunTimeoutSeconds()).setWorkflowTaskTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowTaskTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]d.getTaskQueue()).setWorkflowId([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowId()).setWorkflowIdReusePolicy([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowIdReusePolicy()).setWorkflowType([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowType()).setCronSchedule([CtInvocationImpl][CtVariableReadImpl]d.getCronSchedule()).setParentClosePolicy([CtInvocationImpl][CtVariableReadImpl]d.getParentClosePolicy());
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasHeader()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setHeader([CtInvocationImpl][CtVariableReadImpl]d.getHeader());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasMemo()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setMemo([CtInvocationImpl][CtVariableReadImpl]d.getMemo());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasRetryPolicy()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setRetryPolicy([CtInvocationImpl][CtVariableReadImpl]d.getRetryPolicy());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_START_CHILD_WORKFLOW_EXECUTION_INITIATED).setStartChildWorkflowExecutionInitiatedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long initiatedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEventId = [CtVariableReadImpl]initiatedEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEvent = [CtInvocationImpl][CtVariableReadImpl]a.build();
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest.Builder startChild = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest.newBuilder().setRequestId([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]UUID.randomUUID().toString()).setNamespace([CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]d.getNamespace().isEmpty() ? [CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace() : [CtInvocationImpl][CtVariableReadImpl]d.getNamespace()).setWorkflowExecutionTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowExecutionTimeoutSeconds()).setWorkflowRunTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowRunTimeoutSeconds()).setWorkflowTaskTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowTaskTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]d.getTaskQueue()).setWorkflowId([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowId()).setWorkflowIdReusePolicy([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowIdReusePolicy()).setWorkflowType([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowType()).setCronSchedule([CtInvocationImpl][CtVariableReadImpl]d.getCronSchedule());
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasHeader()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]startChild.setHeader([CtInvocationImpl][CtVariableReadImpl]d.getHeader());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasMemo()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]startChild.setMemo([CtInvocationImpl][CtVariableReadImpl]d.getMemo());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasRetryPolicy()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]startChild.setRetryPolicy([CtInvocationImpl][CtVariableReadImpl]d.getRetryPolicy());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasInput()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]startChild.setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput());
            }
            [CtInvocationImpl]addStartChildTask([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl]initiatedEventId, [CtInvocationImpl][CtVariableReadImpl]startChild.build());
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void addStartChildTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ChildWorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]long initiatedEventId, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest startChild) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.ForkJoinPool.commonPool().execute([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.service.startWorkflowExecutionImpl([CtVariableReadImpl]startChild, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState()), [CtInvocationImpl][CtTypeAccessImpl]java.util.OptionalLong.of([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.grpc.StatusRuntimeException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getStatus().getCode() == [CtFieldReadImpl]Status.Code.ALREADY_EXISTS) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionFailedEventAttributes failRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.StartChildWorkflowExecutionFailedEventAttributes.newBuilder().setInitiatedEventId([CtVariableReadImpl]initiatedEventId).setCause([CtTypeAccessImpl]StartChildWorkflowExecutionFailedCause.START_CHILD_WORKFLOW_EXECUTION_FAILED_CAUSE_WORKFLOW_ALREADY_EXISTS).build();
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState().failStartChildWorkflow([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent.getWorkflowId(), [CtVariableReadImpl]failRequest);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable ee) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.log.error([CtLiteralImpl]"Unexpected failure inserting failStart for a child workflow", [CtVariableReadImpl]ee);
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.log.error([CtLiteralImpl]"Unexpected failure starting a child workflow", [CtVariableReadImpl]e);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.log.error([CtLiteralImpl]"Unexpected failure starting a child workflow", [CtVariableReadImpl]e);
            }
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]request.getWorkflowExecutionTimeoutSeconds() < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INVALID_ARGUMENT.withDescription([CtLiteralImpl]"negative workflowExecution timeout").asRuntimeException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]request.getWorkflowRunTimeoutSeconds() < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INVALID_ARGUMENT.withDescription([CtLiteralImpl]"negative workflowRun timeout").asRuntimeException();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]request.getWorkflowTaskTimeoutSeconds() < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INVALID_ARGUMENT.withDescription([CtLiteralImpl]"negative workflowTaskTimeoutSeconds").asRuntimeException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionStartedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionStartedEventAttributes.newBuilder().setWorkflowType([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowType()).setWorkflowRunTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowRunTimeoutSeconds()).setWorkflowTaskTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowTaskTimeoutSeconds()).setWorkflowExecutionTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowExecutionTimeoutSeconds()).setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setInput([CtInvocationImpl][CtVariableReadImpl]request.getInput()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue());
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setAttempt([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState.get().getAttempt());
        }
        [CtInvocationImpl][CtVariableReadImpl]a.setOriginalExecutionRunId([CtFieldReadImpl][CtVariableReadImpl]data.originalExecutionRunId);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.continuedExecutionRunId.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setContinuedExecutionRunId([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.continuedExecutionRunId.get());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]data.lastCompletionResult != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setLastCompletionResult([CtFieldReadImpl][CtVariableReadImpl]data.lastCompletionResult);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]request.hasMemo()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setMemo([CtInvocationImpl][CtVariableReadImpl]request.getMemo());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]request.hasSearchAttributes()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setSearchAttributes([CtInvocationImpl][CtVariableReadImpl]request.getSearchAttributes());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]request.hasHeader()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setHeader([CtInvocationImpl][CtVariableReadImpl]request.getHeader());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cronSchedule = [CtInvocationImpl][CtVariableReadImpl]request.getCronSchedule();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cronSchedule.trim().isEmpty()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]TestWorkflowMutableStateImpl.parseCron([CtVariableReadImpl]cronSchedule);
                [CtInvocationImpl][CtVariableReadImpl]a.setCronSchedule([CtVariableReadImpl]cronSchedule);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INVALID_ARGUMENT.withDescription([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid cron expression \"" + [CtVariableReadImpl]cronSchedule) + [CtLiteralImpl]"\": ") + [CtInvocationImpl][CtVariableReadImpl]e.getMessage()).withCause([CtVariableReadImpl]e).asRuntimeException();
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]TestWorkflowMutableState> parent = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState().getParent();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]parent.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]ExecutionId parentExecutionId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parent.get().getExecutionId();
            [CtInvocationImpl][CtVariableReadImpl]a.setParentWorkflowNamespace([CtInvocationImpl][CtVariableReadImpl]parentExecutionId.getNamespace());
            [CtInvocationImpl][CtVariableReadImpl]a.setParentWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]parentExecutionId.getExecution());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_STARTED).setWorkflowExecutionStartedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.CompleteWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionCompletedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionCompletedEventAttributes.newBuilder().setResult([CtInvocationImpl][CtVariableReadImpl]d.getResult()).setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_COMPLETED).setWorkflowExecutionCompletedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void continueAsNewWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.ContinueAsNewWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest sr = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState().getStartRequest();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionContinuedAsNewEventAttributes.Builder a = [CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionContinuedAsNewEventAttributes.newBuilder();
        [CtInvocationImpl][CtVariableReadImpl]a.setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]d.getWorkflowRunTimeoutSeconds() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowRunTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowRunTimeoutSeconds());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowRunTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]sr.getWorkflowRunTimeoutSeconds());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasTaskQueue()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setTaskQueue([CtInvocationImpl][CtVariableReadImpl]d.getTaskQueue());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setTaskQueue([CtInvocationImpl][CtVariableReadImpl]sr.getTaskQueue());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasWorkflowType()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowType([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowType());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowType([CtInvocationImpl][CtVariableReadImpl]sr.getWorkflowType());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]d.getWorkflowTaskTimeoutSeconds() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowTaskTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowTaskTimeoutSeconds());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowTaskTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]sr.getWorkflowTaskTimeoutSeconds());
        }
        [CtInvocationImpl][CtVariableReadImpl]a.setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtInvocationImpl][CtVariableReadImpl]a.setBackoffStartIntervalInSeconds([CtInvocationImpl][CtVariableReadImpl]d.getBackoffStartIntervalInSeconds());
        [CtInvocationImpl][CtVariableReadImpl]a.setLastCompletionResult([CtInvocationImpl][CtVariableReadImpl]d.getLastCompletionResult());
        [CtInvocationImpl][CtVariableReadImpl]a.setNewExecutionRunId([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_CONTINUED_AS_NEW).setWorkflowExecutionContinuedAsNewEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void failWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.FailWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionFailedEventAttributes.newBuilder().setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]d.hasFailure()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setFailure([CtInvocationImpl][CtVariableReadImpl]d.getFailure());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_FAILED).setWorkflowExecutionFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void timeoutWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState retryState, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionTimedOutEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionTimedOutEventAttributes.newBuilder().setRetryState([CtVariableReadImpl]retryState);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_TIMED_OUT).setWorkflowExecutionTimedOutEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void cancelWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.CancelWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionCanceledEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionCanceledEventAttributes.newBuilder().setDetails([CtInvocationImpl][CtVariableReadImpl]d.getDetails()).setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_CANCELED).setWorkflowExecutionCanceledEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void terminateWorkflow([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.TerminateWorkflowExecutionRequest d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionTerminatedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionTerminatedEventAttributes.newBuilder().setDetails([CtInvocationImpl][CtVariableReadImpl]d.getDetails()).setIdentity([CtInvocationImpl][CtVariableReadImpl]d.getIdentity()).setReason([CtInvocationImpl][CtVariableReadImpl]d.getReason());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_TERMINATED).setWorkflowExecutionTerminatedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void requestWorkflowCancellation([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RequestCancelWorkflowExecutionRequest cancelRequest, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowExecutionCancelRequestedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowExecutionCancelRequestedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]cancelRequest.getIdentity());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent cancelRequested = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_EXECUTION_CANCEL_REQUESTED).setWorkflowExecutionCancelRequestedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]cancelRequested);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void scheduleActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.ScheduleActivityTaskCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.RetryPolicy retryPolicy = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.ensureDefaultFieldsForActivityRetryPolicy([CtInvocationImpl][CtVariableReadImpl]d.getRetryPolicy());
        [CtLocalVariableImpl][CtTypeReferenceImpl]long expirationInterval = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS.toMillis([CtInvocationImpl][CtVariableReadImpl]d.getScheduleToCloseTimeoutSeconds());
        [CtLocalVariableImpl][CtTypeReferenceImpl]long expirationTime = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.store.currentTimeMillis() + [CtVariableReadImpl]expirationInterval;
        [CtLocalVariableImpl][CtTypeReferenceImpl]TestServiceRetryState retryState = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TestServiceRetryState([CtVariableReadImpl]retryPolicy, [CtVariableReadImpl]expirationTime);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskScheduledEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskScheduledEventAttributes.newBuilder().setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput()).setActivityId([CtInvocationImpl][CtVariableReadImpl]d.getActivityId()).setActivityType([CtInvocationImpl][CtVariableReadImpl]d.getActivityType()).setNamespace([CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]d.getNamespace().isEmpty() ? [CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace() : [CtInvocationImpl][CtVariableReadImpl]d.getNamespace()).setHeartbeatTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getHeartbeatTimeoutSeconds()).setRetryPolicy([CtVariableReadImpl]retryPolicy).setScheduleToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getScheduleToCloseTimeoutSeconds()).setScheduleToStartTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getScheduleToStartTimeoutSeconds()).setStartToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getStartToCloseTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]d.getTaskQueue()).setHeader([CtInvocationImpl][CtVariableReadImpl]d.getHeader()).setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtAssignmentImpl][CtCommentImpl]// Cannot set it in onCommit as it is used in the processScheduleActivityTask
        [CtFieldWriteImpl][CtVariableReadImpl]data.scheduledEvent = [CtInvocationImpl][CtVariableReadImpl]a.build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_SCHEDULED).setActivityTaskScheduledEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long scheduledEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollActivityTaskQueueResponse.Builder taskResponse = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.PollActivityTaskQueueResponse.newBuilder().setWorkflowNamespace([CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace()).setWorkflowType([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.startWorkflowExecutionRequest.getWorkflowType()).setActivityType([CtInvocationImpl][CtVariableReadImpl]d.getActivityType()).setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]ctx.getExecution()).setActivityId([CtInvocationImpl][CtVariableReadImpl]d.getActivityId()).setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput()).setHeartbeatTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getHeartbeatTimeoutSeconds()).setScheduleToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getScheduleToCloseTimeoutSeconds()).setStartToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getStartToCloseTimeoutSeconds()).setScheduledTimestamp([CtInvocationImpl][CtVariableReadImpl]ctx.currentTimeInNanoseconds()).setScheduledTimestampThisAttempt([CtInvocationImpl][CtVariableReadImpl]ctx.currentTimeInNanoseconds()).setHeader([CtInvocationImpl][CtVariableReadImpl]d.getHeader()).setAttempt([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId taskQueueId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId([CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]d.getTaskQueue().getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.ActivityTask activityTask = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.ActivityTask([CtVariableReadImpl]taskQueueId, [CtVariableReadImpl]taskResponse);
        [CtInvocationImpl][CtVariableReadImpl]ctx.addActivityTask([CtVariableReadImpl]activityTask);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.scheduledEventId = [CtVariableReadImpl]scheduledEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.activityTask = [CtVariableReadImpl]activityTask;
            [CtAssignmentImpl][CtVariableWriteImpl]data.retryState = [CtVariableReadImpl]retryState;
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void requestActivityCancellation([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.RequestCancelActivityTaskCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskCancelRequestedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskCancelRequestedEventAttributes.newBuilder().setScheduledEventId([CtInvocationImpl][CtVariableReadImpl]d.getScheduledEventId()).setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_CANCEL_REQUESTED).setActivityTaskCancelRequestedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void scheduleWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object notUsedRequest, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest request = [CtFieldReadImpl][CtVariableReadImpl]data.startRequest;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long scheduledEventId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes.newBuilder().setStartToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowTaskTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue()).setAttempt([CtFieldReadImpl][CtVariableReadImpl]data.attempt).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_SCHEDULED).setWorkflowTaskScheduledEventAttributes([CtVariableReadImpl]a).build();
        [CtAssignmentImpl][CtVariableWriteImpl]scheduledEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse.Builder workflowTaskResponse = [CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse.newBuilder();
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]ctx.getExecution());
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setWorkflowType([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowType());
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setAttempt([CtFieldReadImpl][CtVariableReadImpl]data.attempt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId taskQueueId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId([CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue().getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.WorkflowTask workflowTask = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.WorkflowTask([CtVariableReadImpl]taskQueueId, [CtVariableReadImpl]workflowTaskResponse);
        [CtInvocationImpl][CtVariableReadImpl]ctx.setWorkflowTask([CtVariableReadImpl]workflowTask);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.scheduledEventId = [CtVariableReadImpl]scheduledEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.workflowTask = [CtVariableReadImpl]workflowTaskResponse;
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void convertQueryWorkflowTaskToReal([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object notUsedRequest, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest request = [CtFieldReadImpl][CtVariableReadImpl]data.startRequest;
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes.newBuilder().setStartToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowTaskTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue()).setAttempt([CtFieldReadImpl][CtVariableReadImpl]data.attempt).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_SCHEDULED).setWorkflowTaskScheduledEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long scheduledEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtAssignmentImpl][CtVariableWriteImpl]data.scheduledEventId = [CtVariableReadImpl]scheduledEventId);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void scheduleQueryWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery query, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]ctx.lockTimer([CtLiteralImpl]"scheduleQueryWorkflowTask");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.StartWorkflowExecutionRequest request = [CtFieldReadImpl][CtVariableReadImpl]data.startRequest;
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse.Builder workflowTaskResponse = [CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse.newBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.taskqueue.v1.StickyExecutionAttributes stickyAttributes = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState().getStickyExecutionAttributes();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String taskQueue = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]stickyAttributes == [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue().getName() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stickyAttributes.getWorkerTaskQueue().getName();
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]ctx.getExecution());
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setWorkflowType([CtInvocationImpl][CtVariableReadImpl]request.getWorkflowType());
        [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setAttempt([CtFieldReadImpl][CtVariableReadImpl]data.attempt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId taskQueueId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.TaskQueueId([CtInvocationImpl][CtVariableReadImpl]ctx.getNamespace(), [CtVariableReadImpl]taskQueue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.WorkflowTask workflowTask = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.temporal.internal.testservice.TestWorkflowStore.WorkflowTask([CtVariableReadImpl]taskQueueId, [CtVariableReadImpl]workflowTaskResponse);
        [CtInvocationImpl][CtVariableReadImpl]ctx.setWorkflowTask([CtVariableReadImpl]workflowTask);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data.lastSuccessfulStartedEventId > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]workflowTaskResponse.setPreviousStartedEventId([CtVariableReadImpl]data.lastSuccessfulStartedEventId);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]data.scheduledEventId = [CtFieldReadImpl][CtFieldReferenceImpl]NO_EVENT_ID;
            [CtAssignmentImpl][CtVariableWriteImpl]data.workflowTask = [CtVariableReadImpl]workflowTaskResponse;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]query != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]data.consistentQueryRequests.put([CtInvocationImpl][CtVariableReadImpl]query.getKey(), [CtVariableReadImpl]query);
            }
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void queryWhileScheduled([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery query, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.consistentQueryRequests.put([CtInvocationImpl][CtVariableReadImpl]query.getKey(), [CtVariableReadImpl]query);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void bufferQuery([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery query, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.queryBuffer.put([CtInvocationImpl][CtVariableReadImpl]query.getKey(), [CtVariableReadImpl]query);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskStartedEventAttributes a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskStartedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_STARTED).setWorkflowTaskStartedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.startWorkflowTaskImpl([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl]request, [CtVariableReadImpl]startedEventId, [CtLiteralImpl]false);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startQueryOnlyWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.startWorkflowTaskImpl([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl]request, [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID, [CtLiteralImpl]true);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startWorkflowTaskImpl([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueRequest request, [CtParameterImpl][CtTypeReferenceImpl]long startedEventId, [CtParameterImpl][CtTypeReferenceImpl]boolean queryOnly) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollWorkflowTaskQueueResponse.Builder task = [CtVariableReadImpl]data.workflowTask;
            [CtInvocationImpl][CtVariableReadImpl]task.setStartedEventId([CtBinaryOperatorImpl][CtVariableReadImpl]data.scheduledEventId + [CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]WorkflowTaskToken taskToken = [CtConstructorCallImpl]new [CtTypeReferenceImpl]WorkflowTaskToken([CtInvocationImpl][CtVariableReadImpl]ctx.getExecutionId(), [CtVariableReadImpl]historySize);
            [CtInvocationImpl][CtVariableReadImpl]task.setTaskToken([CtInvocationImpl][CtVariableReadImpl]taskToken.toBytes());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.GetWorkflowExecutionHistoryRequest getRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.GetWorkflowExecutionHistoryRequest.newBuilder().setNamespace([CtInvocationImpl][CtVariableReadImpl]request.getNamespace()).setExecution([CtInvocationImpl][CtVariableReadImpl]ctx.getExecution()).build();
            [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent> events;
            [CtAssignmentImpl][CtVariableWriteImpl]events = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]data.store.getWorkflowExecutionHistory([CtInvocationImpl][CtVariableReadImpl]ctx.getExecutionId(), [CtVariableReadImpl]getRequest, [CtLiteralImpl]null).getHistory().getEventsList();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long lastEventId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]events.get([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]events.size() - [CtLiteralImpl]1).getEventId();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getWorkflowMutableState().getStickyExecutionAttributes() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]events = [CtInvocationImpl][CtVariableReadImpl]events.subList([CtVariableReadImpl](([CtTypeReferenceImpl]int) (data.lastSuccessfulStartedEventId)), [CtInvocationImpl][CtVariableReadImpl]events.size());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]queryOnly && [CtUnaryOperatorImpl](![CtVariableReadImpl]data.workflowCompleted)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]events = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ArrayList<>([CtVariableReadImpl]events);[CtCommentImpl]// convert list to mutable

                [CtLocalVariableImpl][CtCommentImpl]// Add "fake" workflow task scheduled and started if workflow is not closed
                [CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes scheduledAttributes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskScheduledEventAttributes.newBuilder().setStartToCloseTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]data.startRequest.getWorkflowTaskTimeoutSeconds()).setTaskQueue([CtInvocationImpl][CtVariableReadImpl]request.getTaskQueue()).setAttempt([CtVariableReadImpl]data.attempt).build();
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent scheduledEvent = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtVariableReadImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_SCHEDULED).setEventId([CtBinaryOperatorImpl][CtVariableReadImpl]lastEventId + [CtLiteralImpl]1).setWorkflowTaskScheduledEventAttributes([CtVariableReadImpl]scheduledAttributes).build();
                [CtInvocationImpl][CtVariableReadImpl]events.add([CtVariableReadImpl]scheduledEvent);
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskStartedEventAttributes startedAttributes = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskStartedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtBinaryOperatorImpl][CtVariableReadImpl]lastEventId + [CtLiteralImpl]1).build();
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent startedEvent = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventId([CtBinaryOperatorImpl][CtVariableReadImpl]lastEventId + [CtLiteralImpl]1).setEventType([CtVariableReadImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_STARTED).setWorkflowTaskStartedEventAttributes([CtVariableReadImpl]startedAttributes).build();
                [CtInvocationImpl][CtVariableReadImpl]events.add([CtVariableReadImpl]startedEvent);
                [CtInvocationImpl][CtVariableReadImpl]task.setStartedEventId([CtBinaryOperatorImpl][CtVariableReadImpl]lastEventId + [CtLiteralImpl]2);
            }
            [CtInvocationImpl][CtCommentImpl]// get it from pervious started event id.
            [CtVariableReadImpl]task.setHistory([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.History.newBuilder().addAllEvents([CtVariableReadImpl]events));
            [CtLocalVariableImpl][CtCommentImpl]// Transfer the queries
            [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery> queries = [CtVariableReadImpl]data.consistentQueryRequests;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery> queryEntry : [CtInvocationImpl][CtVariableReadImpl]queries.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.QueryWorkflowRequest queryWorkflowRequest = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]queryEntry.getValue().getRequest();
                [CtInvocationImpl][CtVariableReadImpl]task.putQueries([CtInvocationImpl][CtVariableReadImpl]queryEntry.getKey(), [CtInvocationImpl][CtVariableReadImpl]queryWorkflowRequest.getQuery());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data.lastSuccessfulStartedEventId > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setPreviousStartedEventId([CtVariableReadImpl]data.lastSuccessfulStartedEventId);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]queryOnly) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]data.startedEventId = [CtVariableReadImpl]startedEventId;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]data.attempt++;
            }
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollActivityTaskQueueRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskStartedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskStartedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId);
        [CtInvocationImpl][CtVariableReadImpl]a.setAttempt([CtInvocationImpl][CtVariableReadImpl]data.getAttempt());
        [CtLocalVariableImpl][CtCommentImpl]// Setting timestamp here as the default logic will set it to the time when it is added to the
        [CtCommentImpl]// history. But in the case of retry it happens only after an activity completion.
        [CtTypeReferenceImpl]long timestamp = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MILLISECONDS.toNanos([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.store.currentTimeMillis());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_STARTED).setTimestamp([CtVariableReadImpl]timestamp).setActivityTaskStartedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startedEventId;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]startedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]startedEventId = [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.NO_EVENT_ID;
        }
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.startedEventId = [CtVariableReadImpl]startedEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.startedEvent = [CtVariableReadImpl]event;
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollActivityTaskQueueResponse.Builder task = [CtInvocationImpl][CtVariableReadImpl]data.activityTask.getTask();
            [CtInvocationImpl][CtVariableReadImpl]task.setTaskToken([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]ActivityId([CtInvocationImpl][CtVariableReadImpl]ctx.getExecutionId(), [CtVariableReadImpl]data.scheduledEventId).toBytes());
            [CtInvocationImpl][CtVariableReadImpl]task.setStartedTimestamp([CtVariableReadImpl]timestamp);
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondWorkflowTaskCompletedRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskCompletedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskCompletedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_COMPLETED).setWorkflowTaskCompletedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.isTraceEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.trace([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"completeWorkflowTask commit workflowId=" + [CtInvocationImpl][CtVariableReadImpl]data.startRequest.getWorkflowId()) + [CtLiteralImpl]", lastSuccessfulStartedEventId=") + [CtVariableReadImpl]data.startedEventId);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]data.lastSuccessfulStartedEventId = [CtVariableReadImpl]data.startedEventId;
            [CtInvocationImpl][CtVariableReadImpl]data.clear();
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeQuery([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondWorkflowTaskCompletedRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.temporal.api.query.v1.WorkflowQueryResult> responses = [CtInvocationImpl][CtVariableReadImpl]request.getQueryResultsMap();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.temporal.api.query.v1.WorkflowQueryResult> resultEntry : [CtInvocationImpl][CtVariableReadImpl]responses.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery query = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.consistentQueryRequests.remove([CtInvocationImpl][CtVariableReadImpl]resultEntry.getKey());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]query != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.query.v1.WorkflowQueryResult value = [CtInvocationImpl][CtVariableReadImpl]resultEntry.getValue();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.QueryWorkflowResponse> result = [CtInvocationImpl][CtVariableReadImpl]query.getResult();
                [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]value.getResultType()) {
                    [CtCaseImpl]case [CtFieldReadImpl]QUERY_RESULT_TYPE_ANSWERED :
                        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.QueryWorkflowResponse response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.workflowservice.v1.QueryWorkflowResponse.newBuilder().setQueryResult([CtInvocationImpl][CtVariableReadImpl]value.getAnswer()).build();
                        [CtInvocationImpl][CtVariableReadImpl]result.complete([CtVariableReadImpl]response);
                        [CtBreakImpl]break;
                    [CtCaseImpl]case [CtFieldReadImpl]QUERY_RESULT_TYPE_FAILED :
                        [CtInvocationImpl][CtVariableReadImpl]result.completeExceptionally([CtInvocationImpl][CtTypeAccessImpl]io.temporal.internal.common.StatusUtils.newException([CtInvocationImpl][CtTypeAccessImpl]Status.INTERNAL.withDescription([CtInvocationImpl][CtVariableReadImpl]value.getErrorMessage()), [CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.errordetails.v1.QueryFailedFailure.getDefaultInstance()));
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INVALID_ARGUMENT.withDescription([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid query result type: " + [CtInvocationImpl][CtVariableReadImpl]value.getResultType()).asRuntimeException();
                }
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]data.clear();
            [CtInvocationImpl][CtVariableReadImpl]ctx.unlockTimer([CtLiteralImpl]"completeQuery");
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void failQueryWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object unused, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery>> iterator = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.consistentQueryRequests.entrySet().iterator();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]iterator.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl][CtTypeReferenceImpl]TestWorkflowMutableStateImpl.ConsistentQuery> entry = [CtInvocationImpl][CtVariableReadImpl]iterator.next();
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().getResult().isCancelled()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]iterator.remove();
                [CtContinueImpl]continue;
            }
        } 
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.consistentQueryRequests.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ctx.setNeedWorkflowTask([CtLiteralImpl]true);
        }
        [CtInvocationImpl][CtVariableReadImpl]ctx.unlockTimer([CtLiteralImpl]"failQueryWorkflowTask");
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void failWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondWorkflowTaskFailedRequest request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskFailedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]request.hasFailure()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setFailure([CtInvocationImpl][CtVariableReadImpl]request.getFailure());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_FAILED).setWorkflowTaskFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.setNeedWorkflowTask([CtLiteralImpl]true);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void timeoutWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object ignored, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.WorkflowTaskTimedOutEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.WorkflowTaskTimedOutEventAttributes.newBuilder().setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).setTimeoutType([CtTypeAccessImpl]TimeoutType.TIMEOUT_TYPE_START_TO_CLOSE).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_WORKFLOW_TASK_TIMED_OUT).setWorkflowTaskTimedOutEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.setNeedWorkflowTask([CtLiteralImpl]true);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void needsWorkflowTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext requestContext, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.WorkflowTaskData workflowTaskData, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object notUsedRequest, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]requestContext.setNeedWorkflowTask([CtLiteralImpl]true);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtFieldReadImpl][CtVariableReadImpl]data.startedEvent);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedRequest) [CtBlockImpl]{
            [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.completeActivityTaskByTaskToken([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedRequest) (request)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedByIdRequest) [CtBlockImpl]{
            [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.completeActivityTaskById([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedByIdRequest) (request)));
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown request: " + [CtVariableReadImpl]request);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeActivityTaskByTaskToken([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskCompletedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskCompletedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setResult([CtInvocationImpl][CtVariableReadImpl]request.getResult()).setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_COMPLETED).setActivityTaskCompletedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeActivityTaskById([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCompletedByIdRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskCompletedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskCompletedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setResult([CtInvocationImpl][CtVariableReadImpl]request.getResult()).setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_COMPLETED).setActivityTaskCompletedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State failActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedRequest) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.failActivityTaskByTaskToken([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedRequest) (request)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedByIdRequest) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.failActivityTaskById([CtVariableReadImpl]ctx, [CtVariableReadImpl]data, [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedByIdRequest) (request)));
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown request: " + [CtVariableReadImpl]request);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State failActivityTaskByTaskToken([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedRequest request) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getFailure().hasApplicationFailureInfo()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"application failure expected: " + [CtInvocationImpl][CtVariableReadImpl]request.getFailure());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.failure.v1.ApplicationFailureInfo info = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getFailure().getApplicationFailureInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState retryState = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.attemptActivityRetry([CtVariableReadImpl]ctx, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]info), [CtVariableReadImpl]data);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retryState == [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_IN_PROGRESS) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskFailedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setFailure([CtInvocationImpl][CtVariableReadImpl]request.getFailure()).setRetryState([CtVariableReadImpl]retryState).setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_FAILED).setActivityTaskFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State failActivityTaskById([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskFailedByIdRequest request) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getFailure().hasApplicationFailureInfo()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"application failure expected: " + [CtInvocationImpl][CtVariableReadImpl]request.getFailure());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.failure.v1.ApplicationFailureInfo info = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getFailure().getApplicationFailureInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState retryState = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.attemptActivityRetry([CtVariableReadImpl]ctx, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]info), [CtVariableReadImpl]data);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retryState == [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_IN_PROGRESS) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskFailedEventAttributes.newBuilder().setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setFailure([CtInvocationImpl][CtVariableReadImpl]request.getFailure()).setRetryState([CtVariableReadImpl]retryState).setIdentity([CtInvocationImpl][CtVariableReadImpl]request.getIdentity()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_FAILED).setActivityTaskFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.FAILED;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.State timeoutActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.TimeoutType timeoutType, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// ScheduleToStart (queue timeout) is not retryable. Instead of the retry, a customer should set
        [CtCommentImpl]// a larger ScheduleToStart timeout.
        [CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState retryState;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]timeoutType != [CtFieldReadImpl]io.temporal.api.enums.v1.TimeoutType.TIMEOUT_TYPE_SCHEDULE_TO_START) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]retryState = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.attemptActivityRetry([CtVariableReadImpl]ctx, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty(), [CtVariableReadImpl]data);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retryState == [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_IN_PROGRESS) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.INITIATED;
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]retryState = [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_NON_RETRYABLE_FAILURE;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.failure.v1.Failure failure;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]timeoutType == [CtFieldReadImpl]io.temporal.api.enums.v1.TimeoutType.TIMEOUT_TYPE_HEARTBEAT) || [CtBinaryOperatorImpl]([CtVariableReadImpl]timeoutType == [CtFieldReadImpl]io.temporal.api.enums.v1.TimeoutType.TIMEOUT_TYPE_START_TO_CLOSE)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]failure = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.newTimeoutFailure([CtTypeAccessImpl]TimeoutType.TIMEOUT_TYPE_SCHEDULE_TO_CLOSE, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl][CtVariableReadImpl]data.heartbeatDetails), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl]io.temporal.internal.testservice.StateMachines.newTimeoutFailure([CtVariableReadImpl]timeoutType, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty())));
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]failure = [CtInvocationImpl]io.temporal.internal.testservice.StateMachines.newTimeoutFailure([CtVariableReadImpl]timeoutType, [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl][CtVariableReadImpl]data.heartbeatDetails), [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskTimedOutEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskTimedOutEventAttributes.newBuilder().setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setRetryState([CtVariableReadImpl]retryState).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId).setFailure([CtVariableReadImpl]failure);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_TIMED_OUT).setActivityTaskTimedOutEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtFieldReadImpl]io.temporal.internal.testservice.StateMachines.State.TIMED_OUT;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.api.failure.v1.Failure newTimeoutFailure([CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.TimeoutType timeoutType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads> lastHeartbeatDetails, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.temporal.api.failure.v1.Failure> cause) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.failure.v1.TimeoutFailureInfo.Builder info = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.failure.v1.TimeoutFailureInfo.newBuilder().setTimeoutType([CtVariableReadImpl]timeoutType);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lastHeartbeatDetails.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]info.setLastHeartbeatDetails([CtInvocationImpl][CtVariableReadImpl]lastHeartbeatDetails.get());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.failure.v1.Failure.Builder result = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.failure.v1.Failure.newBuilder().setTimeoutFailureInfo([CtVariableReadImpl]info);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cause.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.setCause([CtInvocationImpl][CtVariableReadImpl]cause.get());
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.temporal.api.enums.v1.RetryState attemptActivityRetry([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.temporal.api.failure.v1.ApplicationFailureInfo> info, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_RETRY_POLICY_NOT_SET;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]info.isPresent() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.get().getNonRetryable()) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_NON_RETRYABLE_FAILURE;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TestServiceRetryState nextAttempt = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState.getNextAttempt();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]TestServiceRetryState.BackoffInterval backoffInterval = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.retryState.getBackoffIntervalInSeconds([CtInvocationImpl][CtVariableReadImpl]info.map([CtLambdaImpl]([CtParameterImpl] i) -> [CtInvocationImpl][CtVariableReadImpl]i.getType()), [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.store.currentTimeMillis());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]backoffInterval.getRetryState() == [CtFieldReadImpl]io.temporal.api.enums.v1.RetryState.RETRY_STATE_IN_PROGRESS) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]data.nextBackoffIntervalSeconds = [CtInvocationImpl][CtVariableReadImpl]backoffInterval.getIntervalSeconds();
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.PollActivityTaskQueueResponse.Builder task = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.activityTask.getTask();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]data.heartbeatDetails != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]task.setHeartbeatDetails([CtFieldReadImpl][CtVariableReadImpl]data.heartbeatDetails);
            }
            [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]data.retryState = [CtVariableReadImpl]nextAttempt;
                [CtInvocationImpl][CtVariableReadImpl]task.setAttempt([CtInvocationImpl][CtVariableReadImpl]nextAttempt.getAttempt());
                [CtInvocationImpl][CtVariableReadImpl]task.setScheduledTimestampThisAttempt([CtInvocationImpl][CtVariableReadImpl]ctx.currentTimeInNanoseconds());
            });
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]data.startedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtFieldReadImpl][CtVariableReadImpl]data.startedEvent);
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]data.nextBackoffIntervalSeconds = [CtLiteralImpl]0;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]backoffInterval.getRetryState();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void reportActivityTaskCancellation([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object request, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads> details;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledRequest) [CtBlockImpl]{
            [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledRequest cr = [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledRequest) (request));
                [CtAssignmentImpl][CtVariableWriteImpl]details = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]cr.hasDetails()) ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]cr.getDetails()) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]request instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledByIdRequest) [CtBlockImpl]{
            [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledByIdRequest cr = [CtVariableReadImpl](([CtTypeReferenceImpl]io.temporal.api.workflowservice.v1.RespondActivityTaskCanceledByIdRequest) (request));
                [CtAssignmentImpl][CtVariableWriteImpl]details = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]cr.hasDetails()) ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]cr.getDetails()) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            }
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Status.INTERNAL.withDescription([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected request type: " + [CtVariableReadImpl]request).asRuntimeException();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ActivityTaskCanceledEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ActivityTaskCanceledEventAttributes.newBuilder().setScheduledEventId([CtFieldReadImpl][CtVariableReadImpl]data.scheduledEventId).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]details.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]a.setDetails([CtInvocationImpl][CtVariableReadImpl]details.get());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_ACTIVITY_TASK_CANCELED).setActivityTaskCanceledEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void heartbeatActivityTask([CtParameterImpl][CtTypeReferenceImpl]RequestContext nullCtx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.ActivityTaskData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.Payloads details, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableReadImpl]data.heartbeatDetails = [CtVariableReadImpl]details;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startTimer([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.TimerData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.StartTimerCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.TimerStartedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.TimerStartedEventAttributes.newBuilder().setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId).setStartToFireTimeoutSeconds([CtInvocationImpl][CtVariableReadImpl]d.getStartToFireTimeoutSeconds()).setTimerId([CtInvocationImpl][CtVariableReadImpl]d.getTimerId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_TIMER_STARTED).setTimerStartedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.startedEvent = [CtInvocationImpl][CtVariableReadImpl]a.build();
            [CtAssignmentImpl][CtVariableWriteImpl]data.startedEventId = [CtVariableReadImpl]startedEventId;
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireTimer([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.TimerData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object ignored, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.TimerFiredEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.TimerFiredEventAttributes.newBuilder().setTimerId([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]data.startedEvent.getTimerId()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_TIMER_FIRED).setTimerFiredEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void cancelTimer([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.TimerData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.CancelTimerCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.TimerCanceledEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.TimerCanceledEventAttributes.newBuilder().setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId).setTimerId([CtInvocationImpl][CtVariableReadImpl]d.getTimerId()).setStartedEventId([CtFieldReadImpl][CtVariableReadImpl]data.startedEventId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_TIMER_CANCELED).setTimerCanceledEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void initiateExternalSignal([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.SignalExternalData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.SignalExternalWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes.newBuilder().setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId).setControl([CtInvocationImpl][CtVariableReadImpl]d.getControl()).setInput([CtInvocationImpl][CtVariableReadImpl]d.getInput()).setNamespace([CtInvocationImpl][CtVariableReadImpl]d.getNamespace()).setChildWorkflowOnly([CtInvocationImpl][CtVariableReadImpl]d.getChildWorkflowOnly()).setSignalName([CtInvocationImpl][CtVariableReadImpl]d.getSignalName()).setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]d.getExecution());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_SIGNAL_EXTERNAL_WORKFLOW_EXECUTION_INITIATED).setSignalExternalWorkflowExecutionInitiatedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long initiatedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEventId = [CtVariableReadImpl]initiatedEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEvent = [CtInvocationImpl][CtVariableReadImpl]a.build();
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void failExternalSignal([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.SignalExternalData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.SignalExternalWorkflowExecutionFailedCause cause, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent = [CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent;
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionFailedEventAttributes.newBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getWorkflowExecution()).setControl([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getControl()).setCause([CtVariableReadImpl]cause).setNamespace([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getNamespace());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_SIGNAL_EXTERNAL_WORKFLOW_EXECUTION_FAILED).setSignalExternalWorkflowExecutionFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void completeExternalSignal([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.SignalExternalData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String runId, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.SignalExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent = [CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent;
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.WorkflowExecution signaledExecution = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getWorkflowExecution().toBuilder().setRunId([CtVariableReadImpl]runId).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ExternalWorkflowExecutionSignaledEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ExternalWorkflowExecutionSignaledEventAttributes.newBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setWorkflowExecution([CtVariableReadImpl]signaledExecution).setControl([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getControl()).setNamespace([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getNamespace());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_EXTERNAL_WORKFLOW_EXECUTION_SIGNALED).setExternalWorkflowExecutionSignaledEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void initiateExternalCancellation([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.CancelExternalData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.command.v1.RequestCancelExternalWorkflowExecutionCommandAttributes d, [CtParameterImpl][CtTypeReferenceImpl]long workflowTaskCompletedEventId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes.newBuilder().setWorkflowTaskCompletedEventId([CtVariableReadImpl]workflowTaskCompletedEventId).setControl([CtInvocationImpl][CtVariableReadImpl]d.getControl()).setNamespace([CtInvocationImpl][CtVariableReadImpl]d.getNamespace()).setChildWorkflowOnly([CtInvocationImpl][CtVariableReadImpl]d.getChildWorkflowOnly()).setWorkflowExecution([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.common.v1.WorkflowExecution.newBuilder().setWorkflowId([CtInvocationImpl][CtVariableReadImpl]d.getWorkflowId()).setRunId([CtInvocationImpl][CtVariableReadImpl]d.getRunId()).build());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_REQUEST_CANCEL_EXTERNAL_WORKFLOW_EXECUTION_INITIATED).setRequestCancelExternalWorkflowExecutionInitiatedEventAttributes([CtVariableReadImpl]a).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long initiatedEventId = [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
        [CtInvocationImpl][CtVariableReadImpl]ctx.onCommit([CtLambdaImpl]([CtParameterImpl] historySize) -> [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEventId = [CtVariableReadImpl]initiatedEventId;
            [CtAssignmentImpl][CtVariableWriteImpl]data.initiatedEvent = [CtInvocationImpl][CtVariableReadImpl]a.build();
        });
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void reportExternalCancellationRequested([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.CancelExternalData data, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String runId, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent = [CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent;
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.ExternalWorkflowExecutionCancelRequestedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.ExternalWorkflowExecutionCancelRequestedEventAttributes.newBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setWorkflowExecution([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.common.v1.WorkflowExecution.newBuilder().setRunId([CtVariableReadImpl]runId).setWorkflowId([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getWorkflowExecution().getWorkflowId()).build()).setNamespace([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getNamespace());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_EXTERNAL_WORKFLOW_EXECUTION_CANCEL_REQUESTED).setExternalWorkflowExecutionCancelRequestedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void failExternalCancellation([CtParameterImpl][CtTypeReferenceImpl]RequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.internal.testservice.StateMachines.CancelExternalData data, [CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.enums.v1.CancelExternalWorkflowExecutionFailedCause cause, [CtParameterImpl][CtTypeReferenceImpl]long notUsed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionInitiatedEventAttributes initiatedEvent = [CtFieldReadImpl][CtVariableReadImpl]data.initiatedEvent;
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionFailedEventAttributes.Builder a = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.RequestCancelExternalWorkflowExecutionFailedEventAttributes.newBuilder().setInitiatedEventId([CtFieldReadImpl][CtVariableReadImpl]data.initiatedEventId).setWorkflowExecution([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getWorkflowExecution()).setControl([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getControl()).setCause([CtVariableReadImpl]cause).setNamespace([CtInvocationImpl][CtVariableReadImpl]initiatedEvent.getNamespace());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.temporal.api.history.v1.HistoryEvent event = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.history.v1.HistoryEvent.newBuilder().setEventType([CtTypeAccessImpl]EventType.EVENT_TYPE_REQUEST_CANCEL_EXTERNAL_WORKFLOW_EXECUTION_FAILED).setRequestCancelExternalWorkflowExecutionFailedEventAttributes([CtVariableReadImpl]a).build();
        [CtInvocationImpl][CtVariableReadImpl]ctx.addEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtCommentImpl]// Mimics the default activity retry policy of a standard Temporal server.
    static [CtTypeReferenceImpl]io.temporal.api.common.v1.RetryPolicy ensureDefaultFieldsForActivityRetryPolicy([CtParameterImpl][CtTypeReferenceImpl]io.temporal.api.common.v1.RetryPolicy originalPolicy) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.temporal.api.common.v1.RetryPolicy.newBuilder().setInitialIntervalInSeconds([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]originalPolicy.getInitialIntervalInSeconds() == [CtLiteralImpl]0 ? [CtLiteralImpl]1 : [CtInvocationImpl][CtVariableReadImpl]originalPolicy.getInitialIntervalInSeconds()).setMaximumIntervalInSeconds([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]originalPolicy.getMaximumIntervalInSeconds() == [CtLiteralImpl]0 ? [CtLiteralImpl]100 : [CtInvocationImpl][CtVariableReadImpl]originalPolicy.getMaximumIntervalInSeconds()).setBackoffCoefficient([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]originalPolicy.getBackoffCoefficient() == [CtLiteralImpl]0 ? [CtLiteralImpl]2.0 : [CtInvocationImpl][CtVariableReadImpl]originalPolicy.getBackoffCoefficient()).setMaximumAttempts([CtInvocationImpl][CtVariableReadImpl]originalPolicy.getMaximumAttempts()).build();
    }
}
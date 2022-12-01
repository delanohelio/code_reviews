[CompilationUnitImpl][CtCommentImpl]/* JBoss, Home of Professional Open Source.
Copyright 2011, Red Hat, Inc., and individual contributors
as indicated by the @author tags. See the copyright.txt file in the
distribution for a full listing of individual contributors.

This is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as
published by the Free Software Foundation; either version 2.1 of
the License, or (at your option) any later version.

This software is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this software; if not, write to the Free
Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
[CtPackageDeclarationImpl]package org.jboss.as.controller;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.jboss.as.controller.capability.CapabilityServiceSupport;
[CtUnresolvedImport]import org.jboss.as.controller.registry.Resource;
[CtUnresolvedImport]import org.jboss.as.controller.capability.RuntimeCapability;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import org.jboss.msc.service.ServiceRegistry;
[CtUnresolvedImport]import org.jboss.as.controller.registry.ImmutableManagementResourceRegistration;
[CtUnresolvedImport]import org.jboss.as.controller.notification.Notification;
[CtUnresolvedImport]import org.jboss.as.controller.registry.ManagementResourceRegistration;
[CtUnresolvedImport]import org.jboss.msc.service.ServiceController;
[CtUnresolvedImport]import org.jboss.as.controller.access.Action;
[CtUnresolvedImport]import org.jboss.as.controller.access.AuthorizationResult;
[CtUnresolvedImport]import org.jboss.as.controller.access.ResourceAuthorization;
[CtUnresolvedImport]import org.jboss.msc.service.ServiceName;
[CtUnresolvedImport]import org.wildfly.security.auth.server.SecurityIdentity;
[CtUnresolvedImport]import org.jboss.as.controller.access.Caller;
[CtUnresolvedImport]import org.jboss.dmr.ModelNode;
[CtImportImpl]import java.util.logging.Level;
[CtUnresolvedImport]import org.jboss.msc.service.ServiceTarget;
[CtUnresolvedImport]import org.jboss.as.controller.access.Environment;
[CtUnresolvedImport]import org.jboss.as.controller.client.MessageSeverity;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * The context for an operation step execution.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public interface OperationContext extends [CtTypeReferenceImpl]org.jboss.as.controller.ExpressionResolver {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process.  Runtime operation steps are automatically added after
     * configuration operation steps.  Since only one operation may perform runtime work at a time, this method
     * may block until other runtime operations have completed.
     *
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process.  Runtime operation steps are automatically added after
     * configuration operation steps.  Since only one operation may perform runtime work at a time, this method
     * may block until other runtime operations have completed.
     *
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @param addFirst
     * 		add the handler before the others
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage, [CtParameterImpl][CtTypeReferenceImpl]boolean addFirst) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process, writing any output to the response object
     * associated with the current step.
     * Runtime operation steps are automatically added after configuration operation steps.  Since only one operation
     * may perform runtime work at a time, this method may block until other runtime operations have completed.
     *
     * @param operation
     * 		the operation body to pass into the added step
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process, writing any output to the response object
     * associated with the current step.
     * Runtime operation steps are automatically added after configuration operation steps.  Since only one operation
     * may perform runtime work at a time, this method may block until other runtime operations have completed.
     *
     * @param operation
     * 		the operation body to pass into the added step
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @param addFirst
     * 		add the handler before the others
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage, [CtParameterImpl][CtTypeReferenceImpl]boolean addFirst) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process.  Runtime operation steps are automatically added after
     * configuration operation steps.  Since only one operation may perform runtime work at a time, this method
     * may block until other runtime operations have completed.
     *
     * @param response
     * 		the response which the nested step should populate
     * @param operation
     * 		the operation body to pass into the added step
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode response, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add an execution step to this operation process.  Runtime operation steps are automatically added after
     * configuration operation steps.  Since only one operation may perform runtime work at a time, this method
     * may block until other runtime operations have completed.
     *
     * @param response
     * 		the response which the nested step should populate
     * @param operation
     * 		the operation body to pass into the added step
     * @param step
     * 		the step to add
     * @param stage
     * 		the stage at which the operation applies
     * @param addFirst
     * 		add the handler before the others
     * @throws IllegalArgumentException
     * 		if the step handler is not valid for this controller type
     */
    [CtTypeReferenceImpl]void addStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode response, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler step, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage stage, [CtParameterImpl][CtTypeReferenceImpl]boolean addFirst) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a {@link org.jboss.as.controller.OperationContext.Stage#MODEL} execution step to this operation process,
     * including descriptive information for the operation.
     * <p>
     * This method is primarily intended for internal use.
     * </p>
     *
     * @param stepDefinition
     * 		the definition of the step to add
     * @param stepHandler
     * 		the handler for the step
     * @param addFirst
     * 		add the handler before the others  @throws IllegalArgumentException if the step handler is not valid for this controller type
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void addModelStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationDefinition stepDefinition, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler stepHandler, [CtParameterImpl][CtTypeReferenceImpl]boolean addFirst) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a {@link org.jboss.as.controller.OperationContext.Stage#MODEL} execution step to this operation process,
     * including descriptive information for the operation.
     * <p>
     * This method is primarily intended for use by a handler for the {@code composite} operation.
     * </p>
     *
     * @param response
     * 		the response which the nested step should populate
     * @param operation
     * 		the operation body to pass into the added step
     * @param stepDefinition
     * 		the definition of the step to add
     * @param stepHandler
     * 		the handler for the step
     * @param addFirst
     * 		add the handler before the others  @throws IllegalArgumentException if the step handler is not valid for this controller type
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void addModelStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode response, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationDefinition stepDefinition, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationStepHandler stepHandler, [CtParameterImpl][CtTypeReferenceImpl]boolean addFirst) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a warning to response. This method appends warning message in response headers. Warning should be issued to inform
     * managing end of non catastrophic occurrence, which require administrative action
     *
     * @param level
     * 		- level of warning. Used to filter warning based on level value, just like
     * @param warning
     * 		- i18n formatter message, it should contain ID, just like jboss.Logger output does.
     */
    [CtTypeReferenceImpl]void addResponseWarning([CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Level level, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String warning);

    [CtMethodImpl][CtJavaDocImpl]/**
     * See {@link #addResponseWarning(Level, String)}
     *
     * @param warning
     * 		- pre-formatted warning messsage.
     */
    [CtTypeReferenceImpl]void addResponseWarning([CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Level level, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode warning);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a stream which is attached to the request.
     *
     * @param index
     * 		the index
     * @return the input stream
     */
    [CtTypeReferenceImpl]java.io.InputStream getAttachmentStream([CtParameterImpl][CtTypeReferenceImpl]int index);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the number of streams attached to the request.
     *
     * @return the number of streams
     */
    [CtTypeReferenceImpl]int getAttachmentStreamCount();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the node into which the operation result should be written.
     *
     * @return the result node
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getResult();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether {@link #getResult()} has been invoked.
     *
     * @return {@code true} if {@link #getResult()} has been invoked
     */
    [CtTypeReferenceImpl]boolean hasResult();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attach a stream to be included as part of the response. The return value of this method should be
     * used as the value of the {@link #getResult() result} for the step that invokes this method. Callers
     * can then use that value to find the stream in the
     * {@link org.jboss.as.controller.client.OperationResponse response} to this operation.
     *
     * @param mimeType
     * 		the mime type of the stream. Cannot be {@code null}
     * @param stream
     * 		the stream. Cannot be {@code null}
     * @return a uuid for the stream. Will not be {@code null}
     * @throws IllegalStateException
     * 		if {@link #isBooting()} returns {@code true}.
     */
    [CtTypeReferenceImpl]java.lang.String attachResultStream([CtParameterImpl][CtTypeReferenceImpl]java.lang.String mimeType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream stream);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attach a stream to be included as part of the response, with a predetermined UUID.
     * <p>
     * This method is intended for use by core handlers related to managed domain operation
     * as they propagate a stream throughout a domain. Ordinary handlers should use
     * {@link #attachResultStream(String, java.io.InputStream)}.
     *
     * @param mimeType
     * 		the mime type of the stream. Cannot be {@code null}
     * @param stream
     * 		the stream. Cannot be {@code null}
     * @throws IllegalStateException
     * 		if {@link #isBooting()} returns {@code true}.
     */
    [CtTypeReferenceImpl]void attachResultStream([CtParameterImpl][CtTypeReferenceImpl]java.lang.String uuid, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String mimeType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream stream);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the failure description response node, creating it if necessary.
     *
     * @return the failure description
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getFailureDescription();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether {@link #getFailureDescription()} has been invoked.
     *
     * @return {@code true} if {@link #getFailureDescription()} has been invoked
     */
    [CtTypeReferenceImpl]boolean hasFailureDescription();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the node into which the details of server results in a multi-server managed domain operation should be written.
     *
     * @return the server results node
     * @throws IllegalStateException
     * 		if this process is not a {@link ProcessType#HOST_CONTROLLER}
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getServerResults();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the response-headers response node, creating it if necessary. Ordinary operation step handlers should not
     * use this API for manipulating the {@code operation-requires-restart} or {@code process-state} headers. Use
     * {@link #reloadRequired()} and {@link #restartRequired()} for that. (Some core operation step handlers used
     * for coordinating execution of operations across different processes in a managed domain may use this
     * method to manipulate the {@code operation-requires-restart} or {@code process-state} headers, but that is
     * an exception.)
     *
     * @return the response-headers node
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getResponseHeaders();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Complete a step, while registering for
     * {@link RollbackHandler#handleRollback(OperationContext, ModelNode) a notification} if the work done by the
     * caller needs to be rolled back}.
     *
     * @param rollbackHandler
     * 		the handler for any rollback notification. Cannot be {@code null}.
     */
    [CtTypeReferenceImpl]void completeStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.RollbackHandler rollbackHandler);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Complete a step, while registering for
     * {@link ResultHandler#handleResult(ResultAction, OperationContext, ModelNode) a notification} when the overall
     * result of the operation is known. Handlers that register for notifications will receive the notifications in
     * the reverse of the order in which their steps execute.
     *
     * @param resultHandler
     * 		the handler for the result notification. Cannot be {@code null}.
     */
    [CtTypeReferenceImpl]void completeStep([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.ResultHandler resultHandler);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called by an {@link OperationStepHandler} to indicate it has completed its handling of a step and is
     * uninterested in the result of subsequent steps. This is a convenience method that is equivalent to a call to
     * {@link #completeStep(ResultHandler) completeStep(OperationContext.ResultHandler.NO_OP_RESULT_HANDLER)}.
     * <p>
     * A common user of this method would be a {@link Stage#MODEL} handler. Typically such a handler would not need
     * to take any further action in the case of a {@link ResultAction#KEEP successful result} for the overall operation.
     * If the operation result is a {@link ResultAction#ROLLBACK rollback}, the {@code OperationContext} itself
     * will ensure any changes made to the model are discarded, again requiring no action on the part of the handler.
     * So a {@link Stage#MODEL} handler typically can be uninterested in the result of the overall operation and can
     * use this method.
     * </p>
     *
     * @deprecated invoking this method is unnecessary since if an {@code OperationStepHandler} does not call one of
    the {@link #completeStep(org.jboss.as.controller.OperationContext.ResultHandler) variants} from its
    {@code execute} method, a no-op {@code ResultHandler} will automatically be registered
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeReferenceImpl]void stepCompleted();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the type of process in which this operation is executing.
     *
     * @return the process type. Will not be {@code null}
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.ProcessType getProcessType();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the running mode of the process.
     *
     * @return the running mode. Will not be {@code null}
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.RunningMode getRunningMode();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the controller is currently performing boot tasks.
     *
     * @return whether the controller is currently booting
     */
    [CtTypeReferenceImpl]boolean isBooting();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convenience method to check if the {@link #getProcessType() process type} is {@link ProcessType#isServer() a server type}
     * and the {@link #getRunningMode() running mode} is {@link RunningMode#NORMAL}. The typical usage would
     * be for handlers that are only meant to execute on a normally running server, not on a host controller
     * or on a {@link RunningMode#ADMIN_ONLY} server.
     *
     * @return {@code true} if the {@link #getProcessType() process type} is {@link ProcessType#isServer() a server type}
    and the {@link #getRunningMode() running mode} is {@link RunningMode#NORMAL}.
     */
    [CtTypeReferenceImpl]boolean isNormalServer();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the current operation is bound to be rolled back.
     *
     * @return {@code true} if the operation will be rolled back
     */
    [CtTypeReferenceImpl]boolean isRollbackOnly();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Indicate that the operation should be rolled back, regardless of outcome.
     */
    [CtTypeReferenceImpl]void setRollbackOnly();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets whether any changes made by the operation should be rolled back if an error is introduced
     * by a {@link Stage#RUNTIME} or {@link Stage#VERIFY} handler.
     *
     * @return {@code true} if the operation should rollback if there is a runtime stage failure
     */
    [CtTypeReferenceImpl]boolean isRollbackOnRuntimeFailure();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets whether {@link Stage#RUNTIME} handlers can restart (or remove) runtime services in order to
     * make the operation take effect. If {@code false} and the operation cannot be effected without restarting
     * or removing services, the handler should invoke {@link #reloadRequired()} or {@link #restartRequired()}.
     *
     * @return {@code true} if a service restart or removal is allowed
     */
    [CtTypeReferenceImpl]boolean isResourceServiceRestartAllowed();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify the context that the process requires a stop and re-start of its root service (but not a full process
     * restart) in order to ensure stable operation and/or to bring its running state in line with its persistent configuration.
     *
     * @see ControlledProcessState.State#RELOAD_REQUIRED
     */
    [CtTypeReferenceImpl]void reloadRequired();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify the context that the process must be terminated and replaced with a new process in order to ensure stable
     * operation and/or to bring the running state in line with the persistent configuration.
     *
     * @see ControlledProcessState.State#RESTART_REQUIRED
     */
    [CtTypeReferenceImpl]void restartRequired();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify the context that a previous call to {@link #reloadRequired()} can be ignored (typically because the change
     * that led to the need for reload has been rolled back.)
     */
    [CtTypeReferenceImpl]void revertReloadRequired();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify the context that a previous call to {@link #restartRequired()} can be ignored (typically because the change
     * that led to the need for restart has been rolled back.)
     */
    [CtTypeReferenceImpl]void revertRestartRequired();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify the context that an update to the runtime that would normally have been made could not be made due to
     * the current state of the process. As an example, a step handler that can only update the runtime when
     * {@link #isBooting()} is {@code true} must invoke this method if it is executed when {@link #isBooting()}
     * is {@code false}.
     */
    [CtTypeReferenceImpl]void runtimeUpdateSkipped();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the address associated with the currently executing step.
     *
     * @return the address. Will not be {@code null}
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.PathAddress getCurrentAddress();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link PathElement#getValue() value} of the {@link #getCurrentAddress() current address'}
     * {@link PathAddress#getLastElement() last element}.
     *
     * @return the last element value
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentAddress()} is the empty address
     */
    [CtTypeReferenceImpl]java.lang.String getCurrentAddressValue();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the node with current operation name
     *
     * @return operation name node
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getCurrentOperationName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get parameter node by its name
     *
     * @param name
     * 		of desired parameter
     * @return node for parameter of given name
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getCurrentOperationParameter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get parameter node by its name
     *
     * @param name
     * 		of desired parameter
     * @param nullable
     * 		whether return value can be null
     * @return node for parameter of given name
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode getCurrentOperationParameter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]boolean nullable);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a read only view of the managed resource registration.  The registration is relative to the operation address.
     *
     * @return the model node registration
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.ImmutableManagementResourceRegistration getResourceRegistration();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a mutable view of the managed resource registration.  The registration is relative to the operation address.
     *
     * @return the model node registration
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.ManagementResourceRegistration getResourceRegistrationForUpdate();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a read only view of the root managed resource registration.
     *
     * @return the root resource registration
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.ImmutableManagementResourceRegistration getRootResourceRegistration();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the service registry.  If the step is not a runtime operation handler step, an exception will be thrown.  The
     * returned registry must not be used to remove services, if an attempt is made to call {@code ServiceController.setMode(REMOVE)}
     * on a {@code ServiceController} returned from this registry an {@code IllegalStateException} will be thrown. To
     * remove a service call {@link #removeService(org.jboss.msc.service.ServiceName)}.
     * <p>
     * <strong>Note:</strong> It is very important that the {@code modify} parameter accurately reflect whether the
     * caller intends to make any modifications to any object reachable, directly or indirectly, from the returned
     * {@link ServiceRegistry}. This includes modifying any {@link ServiceController},
     * {@link org.jboss.msc.service.Service}, {@link org.jboss.msc.value.Value} or any object reachable from a value.
     *
     * @param modify
     * 		{@code true} if the operation may be modifying any object reachable directly or indirectly from
     * 		the returned {@link ServiceRegistry}, {@code false} otherwise
     * @return the service registry
     * @throws UnsupportedOperationException
     * 		if the calling step is not a runtime operation step
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceRegistry getServiceRegistry([CtParameterImpl][CtTypeReferenceImpl]boolean modify) throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initiate a service removal.  If the step is not a runtime operation handler step, an exception will be thrown.  Any
     * subsequent step which attempts to add a service with the same name will block until the service removal completes.
     * The returned controller may be used to attempt to cancel a removal in progress.
     *
     * @param name
     * 		the service to remove
     * @return the controller of the service to be removed if service of given name exists; null otherwise
     * @throws UnsupportedOperationException
     * 		if the calling step is not a runtime operation step
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceController<[CtWildcardReferenceImpl]?> removeService([CtParameterImpl][CtTypeReferenceImpl]org.jboss.msc.service.ServiceName name) throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initiate a service removal.  If the step is not a runtime operation handler step, an exception will be thrown.  Any
     * subsequent step which attempts to add a service with the same name will block until the service removal completes.
     *
     * @param controller
     * 		the service controller to remove
     * @throws UnsupportedOperationException
     * 		if the calling step is not a runtime operation step
     */
    [CtTypeReferenceImpl]void removeService([CtParameterImpl][CtTypeReferenceImpl]org.jboss.msc.service.ServiceController<[CtWildcardReferenceImpl]?> controller) throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the service target.  If the step is not a runtime operation handler step, an exception will be thrown.  The
     * returned service target is limited such that only the service add methods are supported.  If a service added
     * to this target was removed by a prior operation step, the install will wait until the removal completes.
     *
     * @return the service target
     * @throws UnsupportedOperationException
     * 		if the calling step is not a runtime operation step
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceTarget getServiceTarget() throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the service target.  If the step is not a runtime operation handler step, an exception will be thrown.  The
     * returned service target is limited such that only the service add methods are supported.  If a service added
     * to this target was removed by a prior operation step, the install will wait until the removal completes.
     *
     * @return the service target
     * @throws UnsupportedOperationException
     * 		if the calling step is not a runtime operation step
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.CapabilityServiceTarget getCapabilityServiceTarget() throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Acquire the controlling {@link ModelController}'s exclusive lock. Holding this lock prevent other operations
     * from mutating the model, the {@link ManagementResourceRegistration management resource registry} or the runtime
     * service registry until the lock is released. The lock is automatically released when the
     * {@link OperationStepHandler#execute(OperationContext, org.jboss.dmr.ModelNode) execute method} of the handler
     * that invoked this method returns.
     * <p>
     * This method should be little used. The model controller's exclusive lock is acquired automatically when any
     * of the operations in this interface that imply mutating the model, management resource registry or service
     * registry are invoked. The only use for this method are special situations where an exclusive lock is needed
     * but none of those methods will be invoked.
     * </p>
     */
    [CtTypeReferenceImpl]void acquireControllerLock();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a new resource, relative to the executed operation address.  Since only one operation
     * may write at a time, this operation may block until other writing operations have completed.
     *
     * @param address
     * 		the (possibly empty) address where the resource should be created. Address is relative to the
     * 		address of the operation being executed
     * @return the created resource
     * @throws IllegalStateException
     * 		if a resource already exists at the given address
     * @throws UnsupportedOperationException
     * 		if the calling operation is not a model operation
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource createResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress address) throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a new resource, at the executed operation address.  Since only one operation
     * may write at a time, this operation may block until other writing operations have completed.
     *
     * @param address
     * 		the (possibly empty) address where the resource should be added. Address is relative to the
     * 		address of the operation being executed
     * @param toAdd
     * 		the new resource
     * @throws IllegalStateException
     * 		if a resource already exists at the given address, or if the resource does not support ordered childred
     * @throws UnsupportedOperationException
     * 		if the calling operation is not a model operation
     */
    [CtTypeReferenceImpl]void addResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress address, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource toAdd);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a new resource, at to the executed operation address.  Since only one operation
     * may write at a time, this operation may block until other writing operations have completed.
     *
     * @param address
     * 		the (possibly empty) address where the resource should be added. Address is relative to the
     * 		address of the operation being executed
     * @param index
     * 		the index of the resource to be created in the parent resources children of this type
     * @param toAdd
     * 		the new resource
     * @throws IllegalStateException
     * 		if a resource already exists at the given address
     * @throws UnsupportedOperationException
     * 		if the calling operation is not a model operation
     */
    [CtTypeReferenceImpl]void addResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress address, [CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource toAdd);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the resource for read only operations, relative to the executed operation address. Reads never block.
     * If a write action was previously performed, the value read will be from an uncommitted copy of the the management model.<br/>
     *
     * Note: By default the returned resource is read-only copy of the entire sub-model. In case this is not required use
     * {@link OperationContext#readResource(PathAddress, boolean)} instead.
     *
     * @param relativeAddress
     * 		the (possibly empty) address where the resource should be added. The address is relative to the
     * 		address of the operation being executed
     * @return the resource
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource readResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress relativeAddress);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the resource for read only operations, relative to the executed operation address. Reads never block.
     * If a write action was previously performed, the value read will be from an uncommitted copy of the the management model.
     *
     * @param relativeAddress
     * 		the (possibly empty) address where the resource should be added. The address is relative to the
     * 		address of the operation being executed
     * @param recursive
     * 		whether the model should be read recursively or not
     * @return the resource
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource readResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress relativeAddress, [CtParameterImpl][CtTypeReferenceImpl]boolean recursive);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Read an addressable resource from the root of the model. Reads never block. If a write action was previously performed,
     * the value read will be from an uncommitted copy of the the management model.
     * <p>
     * Note: By default the returned resource is read-only copy of the entire sub-model. In case the entire sub-model
     * is not required use {@link OperationContext#readResourceFromRoot(PathAddress, boolean)} instead.
     *
     * @param address
     * 		the (possibly empty) address
     * @return a read-only reference from the model
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource readResourceFromRoot([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress address);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Read an addressable resource from the root of the model. Reads never block. If a write action was previously performed,
     * the value read will be from an uncommitted copy of the the management model.
     * <p>
     * Use the {@code recursive} parameter to avoid the expense of making read-only copies of large portions of the
     * resource tree. If {@code recursive} is {@code false}, the returned resource will only have placeholder resources
     * for immediate children. Those placeholder resources will return an empty
     * {@link org.jboss.as.controller.registry.Resource#getModel() model} and will not themselves have any children.
     * Their presence, however, allows the caller to see what immediate children exist under the target resource.
     *
     * @param address
     * 		the (possibly empty) address
     * @param recursive
     * 		whether the model should be read recursively or not
     * @return a read-only reference from the model
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource readResourceFromRoot([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress address, [CtParameterImpl][CtTypeReferenceImpl]boolean recursive);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get an addressable resource for update operations. Since only one operation may write at a time, this operation
     * may block until other writing operations have completed.
     *
     * @param relativeAddress
     * 		the (possibly empty) address where the resource should be added. The address is relative to the
     * 		address of the operation being executed
     * @return the resource
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource readResourceForUpdate([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress relativeAddress);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a resource relative to the executed operation address. Since only one operation
     * may write at a time, this operation may block until other writing operations have completed.
     *
     * @param relativeAddress
     * 		the (possibly empty) address where the resource should be removed. The address is relative to the
     * 		address of the operation being executed
     * @return the old value of the node
     * @throws UnsupportedOperationException
     * 		if the calling operation is not a model operation
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource removeResource([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress relativeAddress) throws [CtTypeReferenceImpl]java.lang.UnsupportedOperationException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get a read-only reference of the entire management model BEFORE any changes were made by this context.
     * The structure of the returned model may depend on the context type (domain vs. server).
     *
     * @return the read-only original resource
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.registry.Resource getOriginalRootResource();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the model has thus far been affected by this operation.
     *
     * @return {@code true} if the model was affected, {@code false} otherwise
     */
    [CtTypeReferenceImpl]boolean isModelAffected();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the {@link ManagementResourceRegistration management resource registry} has thus far been affected by this operation.
     *
     * @return {@code true} if the management resource registry was affected, {@code false} otherwise
     */
    [CtTypeReferenceImpl]boolean isResourceRegistryAffected();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine whether the runtime container has thus far been affected by this operation.
     *
     * @return {@code true} if the container was affected, {@code false} otherwise
     */
    [CtTypeReferenceImpl]boolean isRuntimeAffected();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the current stage of execution.
     *
     * @return the current stage
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage getCurrentStage();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send a message to the client.  Valid only during this operation.
     *
     * @param severity
     * 		the message severity
     * @param message
     * 		the message
     */
    [CtTypeReferenceImpl]void report([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.client.MessageSeverity severity, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String message);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Marks a resource to indicate that it's backing service(s) will be restarted.
     * This is to ensure that a restart only occurs once, even if there are multiple updates.
     * When true is returned the caller has "acquired" the mark and should proceed with the
     * restart, when false, the caller should take no additional action.
     *
     * The passed owner is compared by instance when a call to {@link #revertReloadRequired()}.
     * This is to ensure that only the "owner" will be successful in reverting the mark.
     *
     * @param resource
     * 		the resource that will be restarted
     * @param owner
     * 		the instance representing ownership of the mark
     * @return true if the mark was required and the service should be restarted,
    false if no action should be taken.
     */
    [CtTypeReferenceImpl]boolean markResourceRestarted([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress resource, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object owner);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Removes the restarted marking on the specified resource, provided the passed owner is the one
     * originally used to obtain the mark. The purpose of this method is to facilitate rollback processing.
     * Only the "owner" of the mark should be the one to revert the service to a previous state (once again
     * restarting it).When true is returned, the caller must take the required corrective
     * action by restarting the resource, when false is returned the caller should take no additional action.
     *
     * The passed owner is compared by instance to the one provided in {@link #markResourceRestarted(PathAddress, Object)}
     *
     * @param resource
     * 		the resource being reverted
     * @param owner
     * 		the owner of the mark for the resource
     * @return true if the caller owns the mark and the service should be restored by restarting
    false if no action should be taken.
     */
    [CtTypeReferenceImpl]boolean revertResourceRestarted([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.PathAddress resource, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object owner);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Resolves any expressions in the passed in ModelNode.
     * Expressions may either represent system properties or vaulted date. For vaulted data the format is
     * ${VAULT::vault_block::attribute_name::sharedKey}
     *
     * @param node
     * 		the ModelNode containing expressions.
     * @return a copy of the node with expressions resolved
     * @throws OperationFailedException
     * 		if there is a value of type {@link org.jboss.dmr.ModelType#EXPRESSION} in the node tree and
     * 		there is no system property or environment variable that matches the expression, or if a security
     * 		manager exists and its {@link SecurityManager#checkPermission checkPermission} method doesn't allow
     * 		access to the relevant system property or environment variable
     */
    [CtTypeReferenceImpl]org.jboss.dmr.ModelNode resolveExpressions([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode node) throws [CtTypeReferenceImpl]org.jboss.as.controller.OperationFailedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves an object that has been attached to this context.
     *
     * @param key
     * 		the key to the attachment.
     * @param <T>
     * 		the value type of the attachment.
     * @return the attachment if found otherwise {@code null}.
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getAttachment([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey<[CtTypeParameterReferenceImpl]T> key);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attaches an arbitrary object to this context.
     *
     * @param key
     * 		they attachment key used to ensure uniqueness and used for retrieval of the value.
     * @param value
     * 		the value to store.
     * @param <T>
     * 		the value type of the attachment.
     * @return the previous value associated with the key or {@code null} if there was no previous value.
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T attach([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey<[CtTypeParameterReferenceImpl]T> key, [CtParameterImpl][CtTypeParameterReferenceImpl]T value);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attaches an arbitrary object to this context only if the object was not already attached. If a value has already
     * been attached with the key provided, the current value associated with the key is returned.
     *
     * @param key
     * 		they attachment key used to ensure uniqueness and used for retrieval of the value.
     * @param value
     * 		the value to store.
     * @param <T>
     * 		the value type of the attachment.
     * @return the previous value associated with the key or {@code null} if there was no previous value.
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T attachIfAbsent([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey<[CtTypeParameterReferenceImpl]T> key, [CtParameterImpl][CtTypeParameterReferenceImpl]T value);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Detaches or removes the value from this context.
     *
     * @param key
     * 		the key to the attachment.
     * @param <T>
     * 		the value type of the attachment.
     * @return the attachment if found otherwise {@code null}.
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T detach([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey<[CtTypeParameterReferenceImpl]T> key);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization of the given operation.
     *
     * @param operation
     * 		the operation. Cannot be {@code null}
     * @return the authorization result
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.AuthorizationResult authorize([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization of the given effects for the given operation.
     *
     * @param operation
     * 		the operation. Cannot be {@code null}
     * @param effects
     * 		the relevant effects. If empty, all effects associated with the operation are tested.
     * @return the authorization result
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.AuthorizationResult authorize([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.jboss.as.controller.access.Action.ActionEffect> effects);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization for the resource associated with the currently executing operation step and,
     * optionally, its individual attributes
     *
     * @param attributes
     * 		{@code true} if the result should include attribute authorizations
     * @param isDefaultResource
     * 		{@code true} if
     * @return the resource authorization
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.ResourceAuthorization authorizeResource([CtParameterImpl][CtTypeReferenceImpl]boolean attributes, [CtParameterImpl][CtTypeReferenceImpl]boolean isDefaultResource);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization to read or modify an attribute, checking all effects of the given operation
     *
     * @param operation
     * 		the operation that will read or modify
     * @param attribute
     * 		the attribute name
     * @param currentValue
     * 		the current value of the attribute
     * @return the authorization result
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.AuthorizationResult authorize([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode currentValue);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization to read or modify an attribute, limiting the check to the given effects of the operation
     *
     * @param operation
     * 		the operation that will read or modify
     * @param attribute
     * 		the attribute name
     * @param currentValue
     * 		the current value of the attribute
     * @param effects
     * 		the effects to check, or, if empty, all effects
     * @return the authorization result
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.AuthorizationResult authorize([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode currentValue, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.jboss.as.controller.access.Action.ActionEffect> effects);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for authorization to execute an operation.
     *
     * @param operation
     * 		the operation. Cannot be {@code null}
     * @return the authorization result
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.AuthorizationResult authorizeOperation([CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Obtain the {@link Caller} for the current request.
     *
     * @return The current caller.
     * @deprecated Use {@link #getSecurityIdentity()} instead.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeReferenceImpl]org.jboss.as.controller.access.Caller getCaller();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Obtain the {@link SecurityIdentity} for the current request.
     *
     * @return The current {@code SecurityIdentity}.
     */
    [CtTypeReferenceImpl]org.wildfly.security.auth.server.SecurityIdentity getSecurityIdentity();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Emit a {@link org.jboss.as.controller.notification.Notification}.
     *
     * @param notification
     * 		the notification to emit
     */
    [CtTypeReferenceImpl]void emit([CtParameterImpl]final [CtTypeReferenceImpl]org.jboss.as.controller.notification.Notification notification);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the caller environment for the current request.
     *
     * @return the call environment
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.access.Environment getCallEnvironment();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers a capability with the system. Any {@link org.jboss.as.controller.capability.RuntimeCapability#getRequirements() requirements}
     * associated with the capability will be recorded as requirements.
     *
     * @param capability
     * 		the capability. Cannot be {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void registerCapability([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.capability.RuntimeCapability capability);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers an additional hard requirement a capability has beyond what it was aware of when {@code capability}
     * was passed to {@link #registerCapability(org.jboss.as.controller.capability.RuntimeCapability)}. Used for cases
     * where a capability optionally depends on another capability, and whether or not that requirement is needed is
     * not known when the capability is first registered.
     * <p>
     * This method should be used in preference to {@link #requireOptionalCapability(String, String, String)}
     * when, based on its own configuration, the caller knows in {@link org.jboss.as.controller.OperationContext.Stage#MODEL}
     * that the optional capability is actually required in the current process.
     * </p>
     *
     * @param required
     * 		the name of the required capability. Cannot be {@code null}
     * @param dependent
     * 		the name of the capability that requires the other capability. Cannot be {@code null}
     * @param attribute
     * 		the name of the attribute that triggered this requirement, or {@code null} if no single
     * 		attribute was responsible
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     * 		or if {@code capability} is not registered
     */
    [CtTypeReferenceImpl]void registerAdditionalCapabilityRequirement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String required, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dependent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks whether one of a capability's optional and runtime-only requirements is present. Only for use in cases
     * where the {@code dependent} capability's persistent configuration does not <strong>mandate</strong> the presence
     * of the {@code requested} capability, but the capability will use it at runtime if it is present.
     * <p>
     * This method should be used in preference to {@link #registerAdditionalCapabilityRequirement(String, String, String)}
     * when the caller's own configuration doesn't impose a hard requirement for the {@code requested} capability, but,
     * if it is present it will be used. Once the caller declares an intent to use the capability by invoking this
     * method and getting a {@code true} response, thereafter the system is aware that {@code dependent} is actually
     * using {@code requested}, but <strong>will not</strong> prevent configuration changes that make {@code requested}
     * unavailable.
     * </p>
     *
     * @param requested
     * 		the name of the requested capability. Cannot be {@code null}
     * @param dependent
     * 		the name of the capability that requires the other capability. Cannot be {@code null}
     * @param attribute
     * 		the name of the attribute that triggered this requirement, or {@code null} if no single
     * 		attribute was responsible
     * @return {@code true} if the requested capability is present; {@code false} if not. If {@code true}, hereafter
    {@code dependent}'s requirement for {@code requested} will not be treated as optional.
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     */
    [CtTypeReferenceImpl]boolean hasOptionalCapability([CtParameterImpl][CtTypeReferenceImpl]java.lang.String requested, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dependent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Requests that one of a capability's optional requirements hereafter be treated as required, until the process is
     * stopped or reloaded. This request will only be granted if the required capability is already present; otherwise
     * an {@link org.jboss.as.controller.OperationFailedException} will be thrown.
     * <p>
     * This method should be used in preference to {@link #registerAdditionalCapabilityRequirement(String, String, String)}
     * only if the caller is not sure whether the capability is required until {@link org.jboss.as.controller.OperationContext.Stage#RUNTIME}.
     * <strong>Not knowing whether a capability is required until stage RUNTIME is an anti-pattern, so use of this
     * method is strongly discouraged.</strong> It only exists to avoid the need to break backward compatibility by removing
     * support for expressions from certain attributes.
     * </p>
     *
     * @param required
     * 		the name of the required capability. Cannot be {@code null}
     * @param dependent
     * 		the name of the capability that requires the other capability. Cannot be {@code null}
     * @param attribute
     * 		the name of the attribute that triggered this requirement, or {@code null} if no single
     * 		attribute was responsible
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws org.jboss.as.controller.OperationFailedException
     * 		if the requested capability is not available
     */
    [CtTypeReferenceImpl]void requireOptionalCapability([CtParameterImpl][CtTypeReferenceImpl]java.lang.String required, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dependent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute) throws [CtTypeReferenceImpl]org.jboss.as.controller.OperationFailedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Record that a previously registered requirement for a capability will no longer exist.
     * <p>
     * <strong>Semantics with "reload-required" and "restart-required":</strong>
     * Deregistering a capability requirement does not obligate the caller to cease using a
     * {@link #getCapabilityRuntimeAPI(String, Class) previously obtained} reference to that
     * capability's {@link org.jboss.as.controller.capability.RuntimeCapability#getRuntimeAPI() runtime API}. But, if
     * the caller will not cease using the capability, it must put the process in {@link #reloadRequired() reload-required}
     * or {@link #restartRequired() restart-required} state. This will reflect the fact that the model says the
     * capability is not required, but in the runtime it still is.
     * </p>
     *
     * @param required
     * 		the name of the no longer required capability
     * @param dependent
     * 		the name of the capability that no longer has the requirement
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void deregisterCapabilityRequirement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String required, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dependent);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Record that a previously registered requirement for a capability will no longer exist.
     * <p>
     * <strong>Semantics with "reload-required" and "restart-required":</strong>
     * Deregistering a capability requirement does not obligate the caller to cease using a
     * {@link #getCapabilityRuntimeAPI(String, Class) previously obtained} reference to that
     * capability's {@link org.jboss.as.controller.capability.RuntimeCapability#getRuntimeAPI() runtime API}. But, if
     * the caller will not cease using the capability, it must put the process in {@link #reloadRequired() reload-required}
     * or {@link #restartRequired() restart-required} state. This will reflect the fact that the model says the
     * capability is not required, but in the runtime it still is.
     * </p>
     *
     * @param required
     * 		the name of the no longer required capability
     * @param dependent
     * 		the name of the capability that no longer has the requirement
     * @param attribute
     * 		the name of the attribute that references to the no longer required capability, or {@code null}
     * 		if there is if no single attribute referring to the required capability.
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void deregisterCapabilityRequirement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String required, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dependent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String attribute);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Record that a previously registered capability will no longer be available. Invoking this operation will also
     * automatically {@link #deregisterCapabilityRequirement(String, String) deregister any requirements} that are
     * associated with this capability, including optional ones.
     * <p><strong>Semantics with "reload-required" and "restart-required":</strong>
     * Deregistering a capability does not eliminate the obligation to other capabilities that have
     * previously depended upon it to support them by providing expected runtime services. It does require that those other
     * capabilities also {@link #deregisterCapabilityRequirement(String, String) deregister their requirements} as
     * part of the same operation. Requiring that they do so ensures that the management model is consistent.
     * However, those other capabilities may simply put the process in {@code reload-required}
     * or {@code restart-required} state and then continue to use the existing services. So, an operation that invokes
     * this method should also always put the process into {@code reload-required} or {@code restart-required} state.
     * This will reflect the fact that the model says the capability is not present, but in the runtime it still is.
     * </p>
     *
     * @param capabilityName
     * 		the name of the capability
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is not {@link Stage#MODEL}
     */
    [CtTypeReferenceImpl]void deregisterCapability([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the runtime API associated with a given capability, if there is one.
     *
     * @param capabilityName
     * 		the name of the capability. Cannot be {@code null}
     * @param apiType
     * 		class of the java type that exposes the API. Cannot be {@code null}
     * @param <T>
     * 		the java type that exposes the API
     * @return the runtime API. Will not return {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws java.lang.IllegalArgumentException
     * 		if the capability does not provide a runtime API
     * @throws java.lang.ClassCastException
     * 		if the runtime API exposed by the capability cannot be cast to type {code T}
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getCapabilityRuntimeAPI([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> apiType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the runtime API associated with a given {@link RuntimeCapability#isDynamicallyNamed() dynamically named}
     * capability, if there is one.
     *
     * @param capabilityBaseName
     * 		the base name of the capability. Cannot be {@code null}
     * @param dynamicPart
     * 		the dynamic part of the capability name. Cannot be {@code null}
     * @param apiType
     * 		class of the java type that exposes the API. Cannot be {@code null}
     * @param <T>
     * 		the java type that exposes the API
     * @return the runtime API. Will not return {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws java.lang.IllegalArgumentException
     * 		if the capability does not provide a runtime API
     * @throws java.lang.ClassCastException
     * 		if the runtime API exposed by the capability cannot be cast to type {code T}
     */
    <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getCapabilityRuntimeAPI([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityBaseName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicPart, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> apiType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the name of a service associated with a given capability, if there is one.
     *
     * @param capabilityName
     * 		the name of the capability. Cannot be {@code null}
     * @param serviceType
     * 		class of the java type that exposes by the service. Cannot be {@code null}
     * @return the name of the service. Will not return {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws IllegalArgumentException
     * 		if {@code serviceType} is {@code null} or
     * 		the capability does not provide a service of type {@code serviceType}
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceName getCapabilityServiceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> serviceType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the name of a service associated with a given {@link RuntimeCapability#isDynamicallyNamed() dynamically named}
     * capability, if there is one.
     *
     * @param capabilityBaseName
     * 		the base name of the capability. Cannot be {@code null}
     * @param dynamicPart
     * 		the dynamic part of the capability name. Cannot be {@code null}
     * @param serviceType
     * 		class of the java type that exposes by the service. Cannot be {@code null}
     * @return the name of the service. Will not return {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws IllegalArgumentException
     * 		if {@code serviceType} is {@code null} or
     * 		the capability does not provide a service of type {@code serviceType}
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceName getCapabilityServiceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityBaseName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dynamicPart, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> serviceType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the name of a service associated with a given {@link RuntimeCapability#isDynamicallyNamed() dynamically named}
     * capability, if there is one.
     *
     * @param capabilityBaseName
     * 		the base name of the capability. Cannot be {@code null}
     * @param serviceType
     * 		class of the java type that exposes by the service. Cannot be {@code null}
     * @param dynamicParts
     * 		the dynamic parts of the capability name. Cannot be {@code null}
     * @return the name of the service. Will not return {@code null}
     * @throws java.lang.IllegalStateException
     * 		if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}. The
     * 		complete set of capabilities is not known until the end of the model stage.
     * @throws IllegalArgumentException
     * 		if {@code serviceType} is {@code null} or
     * 		the capability does not provide a service of type {@code serviceType}
     */
    [CtTypeReferenceImpl]org.jboss.msc.service.ServiceName getCapabilityServiceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String capabilityBaseName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> serviceType, [CtParameterImpl]java.lang.String... dynamicParts);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a support object that allows service implementations installed from this context to
     * integrate with capabilities.
     *
     * @return the support object. Will not return {@code null}

    * @throws java.lang.IllegalStateException if {@link #getCurrentStage() the current stage} is {@link Stage#MODEL}.
    Service integration is not supported in the model stage.
     */
    [CtTypeReferenceImpl]org.jboss.as.controller.capability.CapabilityServiceSupport getCapabilityServiceSupport();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Whether normally this operation would require a runtime step. It returns {@code true in the following cases}
     * <ul>
     *  <li>The process is a server, and it is running in NORMAL (i.e. not admin-only) mode.</li>
     *  <li>The process is a HC, and the address of the operation is a subsystem in the host model or a child thereof</li>
     */
    [CtTypeReferenceImpl]boolean isDefaultRequiresRuntime();

    [CtEnumImpl][CtJavaDocImpl]/**
     * The stage at which a step should apply.
     */
    enum Stage {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The step applies to the model (read or write).
         */
        MODEL,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The step applies to the runtime container (read or write).
         */
        RUNTIME,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The step checks the result of a runtime container operation (read only).  Inspect the container,
         * and if problems are detected, record the problem(s) in the operation result.
         */
        VERIFY,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The step performs any actions needed to cause the operation to take effect on the relevant servers
         * in the domain. Adding a step in this stage is only allowed when {@link ProcessType#isHostController()} is true.
         */
        DOMAIN,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The operation has completed execution.
         */
        DONE;
        [CtConstructorImpl]Stage() [CtBlockImpl]{
        }

        [CtMethodImpl][CtTypeReferenceImpl]boolean hasNext() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtThisAccessImpl]this != [CtFieldReadImpl]org.jboss.as.controller.OperationContext.Stage.DONE;
        }

        [CtMethodImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.Stage next() [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtThisAccessImpl]this) {
                [CtCaseImpl]case MODEL :
                    [CtReturnImpl]return [CtFieldReadImpl]org.jboss.as.controller.OperationContext.Stage.RUNTIME;
                [CtCaseImpl]case RUNTIME :
                    [CtReturnImpl]return [CtFieldReadImpl]org.jboss.as.controller.OperationContext.Stage.VERIFY;
                [CtCaseImpl]case VERIFY :
                    [CtReturnImpl]return [CtFieldReadImpl]org.jboss.as.controller.OperationContext.Stage.DOMAIN;
                [CtCaseImpl]case DOMAIN :
                    [CtReturnImpl]return [CtFieldReadImpl]org.jboss.as.controller.OperationContext.Stage.DONE;
                [CtCaseImpl]case DONE :
                [CtCaseImpl]default :
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException();
            }
        }
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * The result action.
     */
    enum ResultAction {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The operation will be committed to the model and/or runtime.
         */
        KEEP,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The operation will be reverted.
         */
        ROLLBACK;}

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Handler for a callback to an {@link OperationStepHandler} indicating that the overall operation is being
     * rolled back and the handler should revert any change it has made.
     */
    [CtAnnotationImpl]@java.lang.FunctionalInterface
    interface RollbackHandler {
        [CtFieldImpl][CtJavaDocImpl]/**
         * A {@link RollbackHandler} that does nothing in the callback. Intended for use by operation step
         * handlers that do not need to do any clean up work -- e.g. those that only perform reads or those
         * that only perform persistent configuration changes. (Persistent configuration changes need not be
         * explicitly rolled back as the {@link OperationContext} will handle that automatically.)
         */
        [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.RollbackHandler NOOP_ROLLBACK_HANDLER = [CtNewClassImpl]new [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.RollbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtJavaDocImpl]/**
             * Does nothing.
             *
             * @param context
             * 		ignored
             * @param operation
             * 		ignored
             */
            [CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void handleRollback([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation) [CtBlockImpl]{
                [CtCommentImpl]// no-op
            }
        };

        [CtFieldImpl][CtJavaDocImpl]/**
         * A {@link RollbackHandler} that calls {@link OperationContext#revertReloadRequired()}. Intended for use by
         * operation step handlers call {@link OperationContext#reloadRequired()} and perform no other actions
         * that need to be rolled back.
         */
        [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.RollbackHandler REVERT_RELOAD_REQUIRED_ROLLBACK_HANDLER = [CtNewClassImpl]new [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.RollbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtJavaDocImpl]/**
             * Does nothing.
             *
             * @param context
             * 		ignored
             * @param operation
             * 		ignored
             */
            [CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void handleRollback([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]context.revertReloadRequired();
            }
        };

        [CtMethodImpl][CtJavaDocImpl]/**
         * Callback to an {@link OperationStepHandler} indicating that the overall operation is being rolled back and the
         * handler should revert any change it has made. A handler executing in {@link Stage#MODEL} need not revert any changes
         * it has made to the configuration model; this will be done automatically. A handler need not to remove services
         * created by the operation; this will be done automatically.
         *
         * @param context
         * 		the operation execution context; will be the same as what was passed to the
         * 		{@link OperationStepHandler#execute(OperationContext, ModelNode)} method invocation
         * 		that registered this rollback handler.
         * @param operation
         * 		the operation being rolled back; will be the same as what was passed to the
         * 		{@link OperationStepHandler#execute(OperationContext, ModelNode)} method invocation
         * 		that registered this rollback handler.
         */
        [CtTypeReferenceImpl]void handleRollback([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation);
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Handler for a callback to an {@link OperationStepHandler} indicating that the result of the overall operation is
     * known and the handler can take any necessary actions to deal with that result.
     */
    [CtAnnotationImpl]@java.lang.FunctionalInterface
    interface ResultHandler {
        [CtFieldImpl][CtJavaDocImpl]/**
         * A {@link ResultHandler} that does nothing in the callback. Intended for use by operation step
         * handlers that do not need to do any clean up work -- e.g. those that only perform reads or those
         * that only perform persistent configuration changes. (Persistent configuration changes need not be
         * explicitly rolled back as the {@link OperationContext} will handle that automatically.)
         */
        [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.ResultHandler NOOP_RESULT_HANDLER = [CtNewClassImpl]new [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.ResultHandler()[CtClassImpl] {
            [CtMethodImpl][CtJavaDocImpl]/**
             * Does nothing.
             *
             * @param resultAction
             * 		ignored
             * @param context
             * 		ignored
             * @param operation
             * 		ignored
             */
            [CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void handleResult([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.ResultAction resultAction, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation) [CtBlockImpl]{
                [CtCommentImpl]// no-op
            }
        };

        [CtMethodImpl][CtJavaDocImpl]/**
         * Callback to an {@link OperationStepHandler} indicating that the result of the overall operation is
         * known and the handler can take any necessary actions to deal with that result.
         *
         * @param resultAction
         * 		the overall result of the operation
         * @param context
         * 		the operation execution context; will be the same as what was passed to the
         * 		{@link OperationStepHandler#execute(OperationContext, ModelNode)} method invocation
         * 		that registered this rollback handler.
         * @param operation
         * 		the operation being rolled back; will be the same as what was passed to the
         * 		{@link OperationStepHandler#execute(OperationContext, ModelNode)} method invocation
         * 		that registered this rollback handler.
         */
        [CtTypeReferenceImpl]void handleResult([CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.ResultAction resultAction, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.as.controller.OperationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.dmr.ModelNode operation);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * An attachment key instance.
     *
     * @param <T>
     * 		the attachment value type
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"UnusedDeclaration")
    public static final class AttachmentKey<[CtTypeParameterImpl]T> {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> valueClass;

        [CtConstructorImpl][CtJavaDocImpl]/**
         * Construct a new instance.
         *
         * @param valueClass
         * 		the value type.
         */
        private AttachmentKey([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> valueClass) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.valueClass = [CtVariableReadImpl]valueClass;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Cast the value to the type of this attachment key.
         *
         * @param value
         * 		the value
         * @return the cast value
         */
        public [CtTypeParameterReferenceImpl]T cast([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Object value) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]valueClass.cast([CtVariableReadImpl]value);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Construct a new simple attachment key.
         *
         * @param valueClass
         * 		the value class
         * @param <T>
         * 		the attachment type
         * @return the new instance
         */
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey<[CtTypeParameterReferenceImpl]T> create([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> valueClass) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jboss.as.controller.OperationContext.AttachmentKey([CtVariableReadImpl]valueClass);
        }
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * The current activity of an operation.
     */
    public static enum ExecutionStatus {

        [CtEnumValueImpl]EXECUTING([CtLiteralImpl]"executing"),
        [CtEnumValueImpl]AWAITING_OTHER_OPERATION([CtLiteralImpl]"awaiting-other-operation"),
        [CtEnumValueImpl]AWAITING_STABILITY([CtLiteralImpl]"awaiting-stability"),
        [CtEnumValueImpl]COMPLETING([CtLiteralImpl]"completing"),
        [CtEnumValueImpl]ROLLING_BACK([CtLiteralImpl]"rolling-back");
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String name;

        [CtConstructorImpl]private ExecutionStatus([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]name;
        }
    }
}
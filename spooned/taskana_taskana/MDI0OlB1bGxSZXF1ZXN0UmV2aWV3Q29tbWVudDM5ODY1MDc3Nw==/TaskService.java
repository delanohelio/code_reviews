[CompilationUnitImpl][CtPackageDeclarationImpl]package pro.taskana.task.api;
[CtUnresolvedImport]import pro.taskana.task.api.models.TaskComment;
[CtUnresolvedImport]import pro.taskana.common.api.exceptions.TaskanaException;
[CtImportImpl]import java.time.Instant;
[CtUnresolvedImport]import pro.taskana.common.api.exceptions.NotAuthorizedException;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.AttachmentPersistenceException;
[CtUnresolvedImport]import pro.taskana.common.api.exceptions.InvalidArgumentException;
[CtUnresolvedImport]import pro.taskana.task.api.models.ObjectReference;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.InvalidStateException;
[CtUnresolvedImport]import pro.taskana.classification.api.models.Classification;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.TaskNotFoundException;
[CtUnresolvedImport]import pro.taskana.task.api.models.Task;
[CtUnresolvedImport]import pro.taskana.classification.api.exceptions.ClassificationNotFoundException;
[CtUnresolvedImport]import pro.taskana.common.api.exceptions.ConcurrencyException;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.TaskCommentNotFoundException;
[CtUnresolvedImport]import pro.taskana.common.api.BulkOperationResults;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.TaskAlreadyExistException;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import pro.taskana.task.api.exceptions.InvalidOwnerException;
[CtUnresolvedImport]import pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException;
[CtUnresolvedImport]import pro.taskana.task.api.models.Attachment;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * The Task Service manages all operations on tasks.
 */
public interface TaskService {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Claim an existing task for the current user.
     *
     * @param taskId
     * 		the id of the task to be claimed
     * @return claimed Task
     * @throws TaskNotFoundException
     * 		if the task with taskId was not found
     * @throws InvalidStateException
     * 		if the state of the task with taskId is not READY
     * @throws InvalidOwnerException
     * 		if the task with taskId is claimed by some else
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task claim([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Claim an existing task for the current user even if it is already claimed by someone else.
     *
     * @param taskId
     * 		the id of the task to be claimed
     * @return claimed Task
     * @throws TaskNotFoundException
     * 		if the task with taskId was not found
     * @throws InvalidStateException
     * 		if the state of the task with taskId is not READY
     * @throws InvalidOwnerException
     * 		if the task with taskId is claimed by someone else
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task forceClaim([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cancel the claim of an existing task if it was claimed by the current user before.
     *
     * @param taskId
     * 		id of the task which should be unclaimed.
     * @return updated unclaimed task
     * @throws TaskNotFoundException
     * 		if the task can´t be found or does not exist
     * @throws InvalidStateException
     * 		when the task is already completed.
     * @throws InvalidOwnerException
     * 		when the task is claimed by another user.
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task cancelClaim([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cancel the claim of an existing task even if it was claimed by another user.
     *
     * @param taskId
     * 		id of the task which should be unclaimed.
     * @return updated unclaimed task
     * @throws TaskNotFoundException
     * 		if the task can´t be found or does not exist
     * @throws InvalidStateException
     * 		when the task is already completed.
     * @throws InvalidOwnerException
     * 		when forceCancel is false and the task is claimed by another
     * 		user.
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task forceCancelClaim([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Complete a claimed Task as owner/admin and update State and Timestamps. If task is already
     * completed, the task is returned as itself.
     *
     * @param taskId
     * 		- Id of the Task which should be completed.
     * @return Task - updated task after completion.
     * @throws InvalidStateException
     * 		when Task wasn´t claimed before.
     * @throws TaskNotFoundException
     * 		if the given Task can´t be found in DB.
     * @throws InvalidOwnerException
     * 		if current user is not the task-owner or administrator.
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task completeTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Complete a Task and update State and Timestamps in every case if the Task exists. If task is
     * already completed, the task is returned as itself.
     *
     * @param taskId
     * 		- Id of the Task which should be completed.
     * @return Task - updated task after completion.
     * @throws InvalidStateException
     * 		when Task wasn´t claimed before.
     * @throws TaskNotFoundException
     * 		if the given Task can´t be found in DB.
     * @throws InvalidOwnerException
     * 		if current user is not the task-owner or administrator.
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task forceCompleteTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidOwnerException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Persists a not persisted Task which does not exist already.
     *
     * @param taskToCreate
     * 		the transient task object to be persisted
     * @return the created and persisted task
     * @throws TaskAlreadyExistException
     * 		when the Task does already exist.
     * @throws NotAuthorizedException
     * 		thrown if the current user is not authorized to create that task
     * @throws WorkbasketNotFoundException
     * 		thrown if the work basket referenced by the task is not
     * 		found
     * @throws ClassificationNotFoundException
     * 		thrown if the {@link Classification} referenced by the
     * 		task is not found
     * @throws InvalidArgumentException
     * 		thrown if the primary ObjectReference is invalid
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task createTask([CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.Task taskToCreate) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException, [CtTypeReferenceImpl]pro.taskana.classification.api.exceptions.ClassificationNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskAlreadyExistException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the details of a task by Id without checking permissions.
     *
     * @param taskId
     * 		the id of the task
     * @return the Task
     * @throws TaskNotFoundException
     * 		thrown of the {@link Task} with taskId is not found
     * @throws NotAuthorizedException
     * 		if the current user has no READ permission for the workbasket
     * 		the task is in.
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task getTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transfer a task to another work basket. The transfer sets the transferred flag and resets the
     * read flag.
     *
     * @param taskId
     * 		The id of the {@link Task} to be transferred
     * @param destinationWorkbasketId
     * 		The Id of the target work basket
     * @return the transferred task
     * @throws TaskNotFoundException
     * 		Thrown if the {@link Task} with taskId was not found.
     * @throws WorkbasketNotFoundException
     * 		Thrown if the target work basket was not found.
     * @throws NotAuthorizedException
     * 		Thrown if the current user is not authorized to transfer this
     * 		{@link Task} to the target work basket
     * @throws InvalidStateException
     * 		Thrown if the task is in a state which does not allow
     * 		transferring
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task transfer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String destinationWorkbasketId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transfer a task to another work basket. The transfer sets the transferred flag and resets the
     * read flag.
     *
     * @param taskId
     * 		The id of the {@link Task} to be transferred
     * @param workbasketKey
     * 		The key of the target work basket
     * @param domain
     * 		The domain of the target work basket
     * @return the transferred task
     * @throws TaskNotFoundException
     * 		Thrown if the {@link Task} with taskId was not found.
     * @throws WorkbasketNotFoundException
     * 		Thrown if the target work basket was not found.
     * @throws NotAuthorizedException
     * 		Thrown if the current user is not authorized to transfer this
     * 		{@link Task} to the target work basket
     * @throws InvalidStateException
     * 		Thrown if the task is in a state which does not allow
     * 		transferring
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task transfer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String workbasketKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String domain) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Marks a task as read.
     *
     * @param taskId
     * 		the id of the task to be updated
     * @param isRead
     * 		the new status of the read flag.
     * @return the updated Task
     * @throws TaskNotFoundException
     * 		Thrown if the {@link Task} with taskId was not found
     * @throws NotAuthorizedException
     * 		if the current user has no read permission for the workbasket
     * 		the task is in
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task setTaskRead([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId, [CtParameterImpl][CtTypeReferenceImpl]boolean isRead) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method provides a query builder for quering the database.
     *
     * @return a {@link TaskQuery}
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.TaskQuery createTaskQuery();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a not persisted instance of {@link Task}. The returned task has no workbasket Id set.
     * When createTask() is invoked for this task, TaskService will call the TaskRouting SPI to
     * determine a workbasket for the task. If the TaskRouting API is not active, e.g. because no
     * TaskRouter is registered, or the TaskRouter(s) don't find a workbasket, the task will not be
     * persisted.
     *
     * @return an empty new Task
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task newTask();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a not persisted instance of {@link Task}.
     *
     * @param workbasketId
     * 		the id of the workbasket to which the task belongs
     * @return an empty new Task
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task newTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String workbasketId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a not persisted instance of {@link Task}.
     *
     * @param workbasketKey
     * 		the key of the workbasket to which the task belongs
     * @param domain
     * 		the domain of the workbasket to which the task belongs
     * @return an empty new Task
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task newTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String workbasketKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String domain);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a not persisted instance of {@link TaskComment}.
     *
     * @param taskId
     * 		The id of the task to which the task comment belongs
     * @return an empty new TaskComment
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment newTaskComment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a not persisted instance of {@link Attachment}.
     *
     * @return an empty new Attachment
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Attachment newAttachment();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update a task.
     *
     * @param task
     * 		the task to be updated in the database
     * @return the updated task
     * @throws InvalidArgumentException
     * 		if the task to be updated contains invalid properties like
     * 		e.g. invalid object references
     * @throws TaskNotFoundException
     * 		if the id of the task is not found in the database
     * @throws ConcurrencyException
     * 		if the task has already been updated by another user
     * @throws ClassificationNotFoundException
     * 		if the updated task refers to a classification that
     * 		cannot be found
     * @throws NotAuthorizedException
     * 		if the current user is not authorized to update the task
     * @throws AttachmentPersistenceException
     * 		if an Attachment with ID will be added multiple times
     * 		without using the task-methods
     * @throws InvalidStateException
     * 		if an attempt is made to change the owner of the task and the
     * 		task is not in state READY .
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task updateTask([CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.Task task) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.ConcurrencyException, [CtTypeReferenceImpl]pro.taskana.classification.api.exceptions.ClassificationNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.AttachmentPersistenceException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transfers a list of tasks to an other workbasket. Exceptions will be thrown if the caller got
     * no permissions on the target or it doesn´t exist. Other Exceptions will be stored and returned
     * in the end.
     *
     * @param destinationWorkbasketId
     * 		target workbasket id
     * @param taskIds
     * 		source task which will be moved
     * @return Bulkresult with ID and Error in it for failed transactions.
     * @throws NotAuthorizedException
     * 		if the caller hasn´t permissions on tarket WB.
     * @throws InvalidArgumentException
     * 		if the method paramesters are EMPTY or NULL.
     * @throws WorkbasketNotFoundException
     * 		if the target WB can´t be found.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> transferTasks([CtParameterImpl][CtTypeReferenceImpl]java.lang.String destinationWorkbasketId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException, [CtTypeReferenceImpl]pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Transfers a list of tasks to an other workbasket. Exceptions will be thrown if the caller got
     * no permissions on the target or it doesn´t exist. Other Exceptions will be stored and returned
     * in the end.
     *
     * @param destinationWorkbasketKey
     * 		target workbasket key
     * @param destinationWorkbasketDomain
     * 		target workbasket domain
     * @param taskIds
     * 		source task which will be moved
     * @return Bulkresult with ID and Error in it for failed transactions.
     * @throws NotAuthorizedException
     * 		if the caller hasn´t permissions on tarket WB.
     * @throws InvalidArgumentException
     * 		if the method paramesters are EMPTY or NULL.
     * @throws WorkbasketNotFoundException
     * 		if the target WB can´t be found.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> transferTasks([CtParameterImpl][CtTypeReferenceImpl]java.lang.String destinationWorkbasketKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String destinationWorkbasketDomain, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException, [CtTypeReferenceImpl]pro.taskana.workbasket.api.exceptions.WorkbasketNotFoundException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes the task with the given Id.
     *
     * @param taskId
     * 		The Id of the task to delete.
     * @throws TaskNotFoundException
     * 		If the given Id does not refer to an existing task.
     * @throws InvalidStateException
     * 		If the state of the referenced task is not Completed.
     * @throws NotAuthorizedException
     * 		if the current user is not member of role ADMIN
     */
    [CtTypeReferenceImpl]void deleteTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes the task with the given Id even if it is not completed.
     *
     * @param taskId
     * 		The Id of the task to delete.
     * @throws TaskNotFoundException
     * 		If the given Id does not refer to an existing task.
     * @throws InvalidStateException
     * 		If the state of the referenced task is not Completed and
     * 		forceDelet is false.
     * @throws NotAuthorizedException
     * 		if the current user is not member of role ADMIN
     */
    [CtTypeReferenceImpl]void forceDeleteTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes a list of tasks.
     *
     * @param tasks
     * 		the ids of the tasks to delete.
     * @return the result of the operations with Id and Exception for each failed task deletion.
     * @throws InvalidArgumentException
     * 		if the TaskIds parameter is NULL
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> deleteTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> tasks) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Completes a list of tasks.
     *
     * @param taskIds
     * 		of the tasks which should be completed.
     * @return the result of the operations with Id and Exception for each failed task completion.
     * @throws InvalidArgumentException
     * 		If the taskId parameter is NULL.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> completeTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates tasks with a matching {@link ObjectReference}.
     *
     * @param selectionCriteria
     * 		the {@link ObjectReference} that is used to select the tasks.
     * @param customFieldsToUpdate
     * 		a {@link Map} that contains as key the identification of the custom
     * 		field and as value the corresponding new value of that custom field. The key for
     * 		identification of the custom field must be a String with value "1", "2" ... "16" as in the
     * 		setCustomAttribute or getCustomAttribute method of {@link Task}
     * @return a list of the Ids of all modified tasks
     * @throws InvalidArgumentException
     * 		If the customFieldsToUpdate map contains an invalid key or if
     * 		the selectionCriteria is invalid
     */
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> updateTasks([CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.ObjectReference selectionCriteria, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> customFieldsToUpdate) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates tasks with matching taskIds.
     *
     * @param taskIds
     * 		the taskIds that are used to select the tasks.
     * @param customFieldsToUpdate
     * 		a {@link Map} that contains as key the identification of the custom
     * 		field and as value the corresponding new value of that custom field. The key for
     * 		identification of the custom field must be a String with value "1", "2" ... "16" as in the
     * 		setCustomAttribute or getCustomAttribute method of {@link Task}
     * @return a list of the Ids of all modified tasks
     * @throws InvalidArgumentException
     * 		If the customFieldsToUpdate map contains an invalid key or if
     * 		the selectionCriteria is invalid
     */
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> updateTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> customFieldsToUpdate) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create a task comment.
     *
     * @param taskComment
     * 		the task comment to be created.
     * @return the created task comment.
     * @throws NotAuthorizedException
     * 		If the current user has no authorization to create a task
     * 		comment for the given taskId in the TaskComment or is not authorized to access the task.
     * @throws TaskNotFoundException
     * 		If the given taskId in the TaskComment does not refer to an
     * 		existing task.
     * @throws InvalidArgumentException
     * 		If the given taskCommentId from the provided task comment is
     * 		not null or empty
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment createTaskComment([CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment taskComment) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update a task comment.
     *
     * @param taskComment
     * 		the task comment to be updated in the database.
     * @return the updated task comment.
     * @throws NotAuthorizedException
     * 		If the current user has no authorization to update a task
     * 		comment or is not authorized to access the task.
     * @throws ConcurrencyException
     * 		if an attempt is made to update the task comment and another user.
     * 		updated it already.
     * @throws TaskCommentNotFoundException
     * 		If the given taskCommentId in the TaskComment does not
     * 		refer to an existing taskComment.
     * @throws TaskNotFoundException
     * 		If the given taskId in the TaskComment does not refer to an
     * 		existing task.
     * @throws InvalidArgumentException
     * 		If the given taskCommentId from the provided task comment is
     * 		null or empty
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment updateTaskComment([CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment taskComment) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.ConcurrencyException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskCommentNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes the task comment with the given Id.
     *
     * @param taskCommentId
     * 		The id of the task comment to delete.
     * @throws NotAuthorizedException
     * 		If the current user has no authorization to delete a task
     * 		comment or is not authorized to access the task.
     * @throws InvalidArgumentException
     * 		If the taskCommentId is null/empty
     * @throws TaskCommentNotFoundException
     * 		If the given taskCommentId in the TaskComment does not
     * 		refer to an existing taskComment.
     * @throws TaskNotFoundException
     * 		If the given taskId in the TaskComment does not refer to an
     * 		existing task.
     * @throws InvalidArgumentException
     * 		If the given taskCommentId is null or empty
     */
    [CtTypeReferenceImpl]void deleteTaskComment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskCommentId) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskCommentNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves a task comment for a given taskCommentId.
     *
     * @param taskCommentId
     * 		The id of the task comment which should be retrieved
     * @return the task comment identified by taskCommentId
     * @throws TaskCommentNotFoundException
     * 		If the given taskCommentId in the TaskComment does not
     * 		refer to an existing taskComment.
     * @throws NotAuthorizedException
     * 		If the current user has no authorization to retrieve a
     * 		taskComment from a certain task or is not authorized to access the task.
     * @throws TaskNotFoundException
     * 		If the given taskId in the TaskComment does not refer to an
     * 		existing task.
     * @throws InvalidArgumentException
     * 		If the given taskCommentId is null or empty
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment getTaskComment([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskCommentId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskCommentNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.InvalidArgumentException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves a list of task comments for a given taskId.
     *
     * @param taskId
     * 		The id of the task for which all task comments should be retrieved
     * @return the list of task comments attached to task with id taskId
     * @throws NotAuthorizedException
     * 		If the current user has no authorization to retrieve a
     * 		taskComment from a certain task or is not authorized to access the task.
     * @throws TaskNotFoundException
     * 		If the given taskId in the TaskComment does not refer to an
     * 		existing task.
     */
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.task.api.models.TaskComment> getTaskComments([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the callback state on a list of tasks. Note: this method is primarily intended to be used
     * by the TaskanaAdapter
     *
     * @param externalIds
     * 		the EXTERNAL_IDs of the tasks on which the callback state is set.
     * @param state
     * 		the callback state that is to be set on the tasks
     * @return the result of the operations with Id and Exception for each failed task deletion.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> setCallbackStateForTasks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> externalIds, [CtParameterImpl][CtTypeReferenceImpl]pro.taskana.task.api.CallbackState state);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the owner on a list of tasks. The owner will only be set on tasks that are in state READY.
     *
     * @param owner
     * 		the new owner of the tasks
     * @param taskIds
     * 		the IDs of the tasks on which the owner is to be set.
     * @return the result of the operations with Id and Exception for each failed task update.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> setOwnerOfTasks([CtParameterImpl][CtTypeReferenceImpl]java.lang.String owner, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the planned property on a list of tasks. Only tasks in state READY and CLAIMED will be
     * affected by this method. On each task, the corresponding due date is set according to the
     * shortest service level in the classifications of the task and the task's attachments.
     *
     * @param planned
     * 		the new 'PLANNED" property of the tasks
     * @param taskIds
     * 		the IDs of the tasks on which the new planned property is to be set.
     * @return the result of the operations with Id and Exception for each failed task update.
     */
    [CtTypeReferenceImpl]pro.taskana.common.api.BulkOperationResults<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.TaskanaException> setPlannedPropertyOfTasks([CtParameterImpl][CtTypeReferenceImpl]java.time.Instant planned, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> taskIds);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cancels a task.
     *
     * @param taskId
     * 		the id of the task to cancel.
     * @return the updated task.
     * @throws TaskNotFoundException
     * 		if the Task with Id TaskId is not found
     * @throws InvalidStateException
     * 		if the task is not in state READY or CLAIMED
     * @throws NotAuthorizedException
     * 		if the current user is not authorized to see the task
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task cancelTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Terminates a task.
     *
     * @param taskId
     * 		the id of the task to cancel.
     * @return the updated task.
     * @throws TaskNotFoundException
     * 		if the Task with Id TaskId is not found
     * @throws InvalidStateException
     * 		if the task is not in state READY or CLAIMED
     * @throws NotAuthorizedException
     * 		if the current user is not authorized to see the task
     */
    [CtTypeReferenceImpl]pro.taskana.task.api.models.Task terminateTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String taskId) throws [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.TaskNotFoundException, [CtTypeReferenceImpl]pro.taskana.task.api.exceptions.InvalidStateException, [CtTypeReferenceImpl]pro.taskana.common.api.exceptions.NotAuthorizedException;
}
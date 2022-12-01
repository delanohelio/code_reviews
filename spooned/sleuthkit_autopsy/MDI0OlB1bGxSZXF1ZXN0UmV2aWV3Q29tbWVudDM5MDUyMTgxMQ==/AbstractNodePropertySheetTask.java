[CompilationUnitImpl][CtCommentImpl]/* Autopsy Forensic Browser

Copyright 2020 Basis Technology Corp.
Contact: carrier <at> sleuthkit <dot> org

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
[CtPackageDeclarationImpl]package org.sleuthkit.autopsy.datamodel.utils;
[CtUnresolvedImport]import com.google.common.util.concurrent.ThreadFactoryBuilder;
[CtImportImpl]import java.lang.ref.WeakReference;
[CtImportImpl]import java.beans.PropertyChangeEvent;
[CtUnresolvedImport]import org.sleuthkit.autopsy.datamodel.AbstractContentNode;
[CtImportImpl]import java.beans.PropertyChangeListener;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtImportImpl]import java.util.concurrent.Executors;
[CtImportImpl]import java.util.logging.Level;
[CtUnresolvedImport]import org.sleuthkit.autopsy.coreutils.Logger;
[CtImportImpl]import java.util.concurrent.Future;
[CtUnresolvedImport]import org.openide.nodes.AbstractNode;
[CtClassImpl][CtJavaDocImpl]/**
 * An abstract base class for background tasks needed to compute values for the
 * property sheet of an AbstractNode.
 *
 * The results of the computation are returned by firing a PropertyChangeEvent
 * and the run method has an exception firewall with logging. These features
 * relieve the AbstractNode from having to create a thread to block on the get()
 * method of the task Future.
 *
 * Only weak references to the AbstractNode and its PropertyChangeListener are
 * held prior to task execution so that a queued task does not interfere with
 * garbage collection if the node has been destroyed by the NetBeans framework.
 *
 * A thread pool with descriptively named threads (node-background-task-N) is
 * provided for executing instances of the tasks.
 */
public abstract class AbstractNodePropertySheetTask implements [CtTypeReferenceImpl]java.lang.Runnable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.sleuthkit.autopsy.coreutils.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.sleuthkit.autopsy.coreutils.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]org.sleuthkit.autopsy.datamodel.AbstractContentNode.class.getName());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.Integer THREAD_POOL_SIZE = [CtLiteralImpl]10;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.concurrent.ExecutorService executor = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newFixedThreadPool([CtFieldReadImpl]org.sleuthkit.autopsy.datamodel.utils.AbstractNodePropertySheetTask.THREAD_POOL_SIZE, [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.common.util.concurrent.ThreadFactoryBuilder().setNameFormat([CtLiteralImpl]"node-background-task-%d").build());

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.ref.WeakReference<[CtTypeReferenceImpl]org.openide.nodes.AbstractNode> weakNodeRef;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.ref.WeakReference<[CtTypeReferenceImpl]java.beans.PropertyChangeListener> weakListenerRef;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Submits a task to compute values for the property sheet of an
     * AbstractNode to a thread pool dedicated to such tasks with descriptively
     * named threads (node-background-task-N).
     *
     * @param task
     * 		The task.
     * @return The Future of the task, may be used for task cancellation by
    calling Future.cancel(true).
     */
    public static [CtTypeReferenceImpl]java.util.concurrent.Future<[CtWildcardReferenceImpl]?> submitTask([CtParameterImpl][CtTypeReferenceImpl]org.sleuthkit.autopsy.datamodel.utils.AbstractNodePropertySheetTask task) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]org.sleuthkit.autopsy.datamodel.utils.AbstractNodePropertySheetTask.executor.submit([CtVariableReadImpl]task);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs an abstract base class for background tasks needed to compute
     * values for the property sheet of an AbstractNode.
     *
     * The results of the computation are returned by firing a
     * PropertyChangeEvent and the run method has an exception firewall with
     * logging. These features relieve the AbstractNode from having to create a
     * thread to block on the get() method of the task Future.
     *
     * Only weak references to the AbstractNode and its PropertyChangeListener
     * are held prior to task execution so that a queued task does not interfere
     * with garbage collection if the node has been destroyed by the NetBeans
     * framework.
     *
     * A thread pool with descriptively named threads (node-background-task-N)
     * is provided for executing instances of the tasks.
     *
     * @param node
     * 		The node.
     * @param listener
     * 		A property change listener for the node.
     */
    protected AbstractNodePropertySheetTask([CtParameterImpl][CtTypeReferenceImpl]org.openide.nodes.AbstractNode node, [CtParameterImpl][CtTypeReferenceImpl]java.beans.PropertyChangeListener listener) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.weakNodeRef = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.WeakReference<>([CtVariableReadImpl]node);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.weakListenerRef = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.WeakReference<>([CtVariableReadImpl]listener);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Computes the values for the property sheet of an AbstractNode. The
     * results of the computation are returned as a PropertyChangeEvent which is
     * fired to the PropertyChangeEventListener of the node.
     *
     * IMPORTANT: Implementations of this method should check for cancellation
     * by calling Thread.currentThread().isInterrupted() at approoraite
     * intervals.
     *
     * @param node
     * 		The AbstractNode.
     * @return The result of the computation as a PropertyChangeEvent.
     */
    protected abstract [CtTypeReferenceImpl]java.beans.PropertyChangeEvent computePropertyValue([CtParameterImpl][CtTypeReferenceImpl]org.openide.nodes.AbstractNode node) throws [CtTypeReferenceImpl]java.lang.Exception;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public final [CtTypeReferenceImpl]void run() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.openide.nodes.AbstractNode node = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.weakNodeRef.get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.beans.PropertyChangeListener listener = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.weakListenerRef.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]node == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]listener == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.beans.PropertyChangeEvent changeEvent = [CtInvocationImpl]computePropertyValue([CtVariableReadImpl]node);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]changeEvent != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.propertyChange([CtVariableReadImpl]changeEvent);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.sleuthkit.autopsy.datamodel.utils.AbstractNodePropertySheetTask.LOGGER.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"Error executing property sheet values computation background task", [CtVariableReadImpl]ex);
        }
    }
}
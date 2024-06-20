[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2015, 2017 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.eclipse.xtext.resource.impl;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.eclipse.emf.common.notify.Adapter;
[CtUnresolvedImport]import org.eclipse.emf.common.notify.impl.AdapterImpl;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.eclipse.emf.common.notify.Notifier;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;
[CtUnresolvedImport]import com.google.common.annotations.Beta;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.9
 */
[CtAnnotationImpl]@com.google.common.annotations.Beta
public class ProjectDescription {
    [CtFieldImpl][CtJavaDocImpl]/**
     * A unique name for this project
     */
    private [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl][CtJavaDocImpl]/**
     * list of logical names of upstream dependencies
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> dependencies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription findInEmfObject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.notify.Notifier emfObject) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.common.notify.Adapter adapter : [CtInvocationImpl][CtVariableReadImpl]emfObject.eAdapters()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]adapter instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription.ProjectDescriptionAdapter)[CtBlockImpl]
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription.ProjectDescriptionAdapter) (adapter)).get();

        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription removeFromEmfObject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.notify.Notifier emfObject) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.emf.common.notify.Adapter> adapters = [CtInvocationImpl][CtVariableReadImpl]emfObject.eAdapters();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0, [CtLocalVariableImpl]max = [CtInvocationImpl][CtVariableReadImpl]adapters.size(); [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]max; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.common.notify.Adapter adapter = [CtInvocationImpl][CtVariableReadImpl]adapters.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]adapter instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription.ProjectDescriptionAdapter) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]emfObject.eAdapters().remove([CtVariableReadImpl]i);
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription.ProjectDescriptionAdapter) (adapter)).get();
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void attachToEmfObject([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.common.notify.Notifier emfObject) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.eclipse.xtext.resource.impl.ProjectDescription.findInEmfObject([CtVariableReadImpl]emfObject) != [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"The given EMF object already contains an adapter for ProjectDescription");

        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]emfObject.eAdapters().add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription.ProjectDescriptionAdapter([CtThisAccessImpl]this));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getDependencies() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dependencies;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDependencies([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> dependencies) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dependencies = [CtVariableReadImpl]dependencies;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.xbase.lib.util.ToStringBuilder([CtThisAccessImpl]this).add([CtLiteralImpl]"name", [CtFieldReadImpl]name).add([CtLiteralImpl]"dependencies", [CtFieldReadImpl]dependencies).toString();
    }

    [CtClassImpl]public static class ProjectDescriptionAdapter extends [CtTypeReferenceImpl]org.eclipse.emf.common.notify.impl.AdapterImpl {
        [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription element;

        [CtConstructorImpl]public ProjectDescriptionAdapter([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription element) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.element = [CtVariableReadImpl]element;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.eclipse.xtext.resource.impl.ProjectDescription get() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]element;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isAdapterForType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]object == [CtFieldReadImpl]org.eclipse.xtext.resource.impl.ProjectDescription.class;
        }
    }
}
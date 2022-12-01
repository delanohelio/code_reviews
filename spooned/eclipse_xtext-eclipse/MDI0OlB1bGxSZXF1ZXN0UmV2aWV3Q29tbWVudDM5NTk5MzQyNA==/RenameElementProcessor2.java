[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2017, 2020 TypeFox GmbH (http://www.typefox.io) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.eclipse.xtext.ui.refactoring2.rename;
[CtUnresolvedImport]import org.eclipse.xtext.ui.XtextProjectHelper;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
[CtUnresolvedImport]import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor;
[CtUnresolvedImport]import org.eclipse.xtext.ide.refactoring.RenameChange;
[CtUnresolvedImport]import org.eclipse.emf.common.util.URI;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;
[CtUnresolvedImport]import org.eclipse.xtext.ide.refactoring.RenameContext;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtUnresolvedImport]import org.eclipse.core.runtime.CoreException;
[CtUnresolvedImport]import org.eclipse.emf.ecore.resource.ResourceSet;
[CtUnresolvedImport]import org.apache.log4j.Logger;
[CtUnresolvedImport]import org.eclipse.core.runtime.OperationCanceledException;
[CtUnresolvedImport]import org.eclipse.xtext.ide.serializer.IChangeSerializer;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import org.eclipse.core.resources.IProject;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.RefactoringStatus;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.Change;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
[CtUnresolvedImport]import org.eclipse.xtext.ide.refactoring.IRenameNameValidator;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
[CtUnresolvedImport]import org.eclipse.emf.ecore.EObject;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
[CtUnresolvedImport]import org.eclipse.core.runtime.IProgressMonitor;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
[CtUnresolvedImport]import com.google.inject.Provider;
[CtUnresolvedImport]import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
[CtUnresolvedImport]import org.eclipse.xtext.Constants;
[CtUnresolvedImport]import com.google.inject.name.Named;
[CtUnresolvedImport]import org.eclipse.xtext.ide.refactoring.IRenameStrategy2;
[CtUnresolvedImport]import org.eclipse.xtext.ui.refactoring.impl.ProjectUtil;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author koehnlein - Initial contribution and API
 * @since 2.13
 */
public class RenameElementProcessor2 extends [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor {
    [CtFieldImpl]protected static final [CtTypeReferenceImpl]org.apache.log4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.apache.log4j.Logger.getLogger([CtFieldReadImpl]org.eclipse.xtext.ui.refactoring2.rename.RenameElementProcessor2.class);

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    [CtAnnotationImpl]@com.google.inject.name.Named([CtFieldReadImpl]org.eclipse.xtext.Constants.LANGUAGE_NAME)
    private [CtTypeReferenceImpl]java.lang.String languageName;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.IRenameNameValidator nameValidator;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider resourceSetProvider;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring2.rename.ISimpleNameProvider simpleNameProvider;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring.impl.ProjectUtil projectUtil;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]com.google.inject.Provider<[CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor> statusProvider;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ide.serializer.IChangeSerializer changeSerializer;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.IRenameStrategy2 renameStrategy;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]ChangeConverter.Factory changeConverterFactory;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String newName;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext renameElementContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.core.resources.IProject project;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.emf.ecore.resource.ResourceSet resourceSet;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject target;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String originalName;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor status;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.Change change;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean initialize([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext renameElementContext) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.renameElementContext = [CtVariableReadImpl]renameElementContext;
        [CtAssignmentImpl][CtFieldWriteImpl]status = [CtInvocationImpl][CtFieldReadImpl]statusProvider.get();
        [CtAssignmentImpl][CtFieldWriteImpl]project = [CtInvocationImpl][CtFieldReadImpl]projectUtil.getProject([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]renameElementContext.getTargetElementURI().trimFragment());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]project == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.common.util.URI targetElementURI = [CtInvocationImpl][CtVariableReadImpl]renameElementContext.getTargetElementURI();
            [CtInvocationImpl][CtCommentImpl]// 
            [CtCommentImpl]// 
            [CtFieldReadImpl]status.add([CtTypeAccessImpl]RefactoringIssueAcceptor.Severity.ERROR, [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot determine project from targetURI " + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]targetElementURI != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]targetElementURI.toString() : [CtLiteralImpl]null), [CtInvocationImpl][CtVariableReadImpl]renameElementContext.getTargetElementURI());
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]resourceSet = [CtInvocationImpl][CtFieldReadImpl]resourceSetProvider.get([CtFieldReadImpl]project);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject target = [CtInvocationImpl][CtFieldReadImpl]resourceSet.getEObject([CtInvocationImpl][CtVariableReadImpl]renameElementContext.getTargetElementURI(), [CtLiteralImpl]true);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]status.add([CtTypeAccessImpl]RefactoringIssueAcceptor.Severity.ERROR, [CtLiteralImpl]"Rename target does not exist", [CtInvocationImpl][CtVariableReadImpl]renameElementContext.getTargetElementURI());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]originalName = [CtInvocationImpl][CtFieldReadImpl]simpleNameProvider.getSimpleName([CtVariableReadImpl]target);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.RefactoringStatus checkInitialConditions([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.core.runtime.IProgressMonitor pm) throws [CtTypeReferenceImpl]org.eclipse.core.runtime.CoreException, [CtTypeReferenceImpl]org.eclipse.core.runtime.OperationCanceledException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]status.getRefactoringStatus();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.RefactoringStatus checkFinalConditions([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.core.runtime.IProgressMonitor pm, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext context) throws [CtTypeReferenceImpl]org.eclipse.core.runtime.CoreException, [CtTypeReferenceImpl]org.eclipse.core.runtime.OperationCanceledException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.RenameChange renameChange = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.RenameChange([CtFieldReadImpl]newName, [CtInvocationImpl][CtFieldReadImpl]renameElementContext.getTargetElementURI());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.RenameContext renameContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.xtext.ide.refactoring.RenameContext([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]renameChange), [CtFieldReadImpl]resourceSet, [CtFieldReadImpl]changeSerializer, [CtFieldReadImpl]status);
        [CtInvocationImpl][CtFieldReadImpl]renameStrategy.applyRename([CtVariableReadImpl]renameContext);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Rename " + [CtFieldReadImpl]originalName) + [CtLiteralImpl]" to ") + [CtFieldReadImpl]newName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring2.ChangeConverter changeConverter = [CtInvocationImpl][CtFieldReadImpl]changeConverterFactory.create([CtVariableReadImpl]name, [CtLiteralImpl]null, [CtFieldReadImpl]status);
        [CtInvocationImpl][CtFieldReadImpl]changeSerializer.applyModifications([CtVariableReadImpl]changeConverter);
        [CtAssignmentImpl][CtFieldWriteImpl]change = [CtInvocationImpl][CtVariableReadImpl]changeConverter.getChange();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]status.getRefactoringStatus();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.Change createChange([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.core.runtime.IProgressMonitor pm) throws [CtTypeReferenceImpl]org.eclipse.core.runtime.CoreException, [CtTypeReferenceImpl]org.eclipse.core.runtime.OperationCanceledException [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]change;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]java.lang.Object[] getElements() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtFieldReadImpl]renameElementContext.getTargetElementURI() };
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getIdentifier() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]languageName + [CtLiteralImpl]".renameProcessor2";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getProcessorName() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"Rename element";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isApplicable() throws [CtTypeReferenceImpl]org.eclipse.core.runtime.CoreException [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant[] loadParticipants([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.RefactoringStatus status, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.participants.SharableParticipants sharedParticipants) throws [CtTypeReferenceImpl]org.eclipse.core.runtime.CoreException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.eclipse.ltk.core.refactoring.participants.ParticipantManager.loadRenameParticipants([CtVariableReadImpl]status, [CtThisAccessImpl]this, [CtFieldReadImpl]renameElementContext, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.participants.RenameArguments([CtFieldReadImpl]newName, [CtLiteralImpl]true), [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtFieldReadImpl]org.eclipse.xtext.ui.XtextProjectHelper.NATURE_ID }, [CtVariableReadImpl]sharedParticipants);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getOriginalName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]originalName;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.eclipse.ltk.core.refactoring.RefactoringStatus validateNewName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String newName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor nameStatus = [CtInvocationImpl][CtFieldReadImpl]statusProvider.get();
        [CtInvocationImpl][CtFieldReadImpl]nameValidator.validate([CtFieldReadImpl]target, [CtVariableReadImpl]newName, [CtVariableReadImpl]nameStatus);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]nameStatus.getRefactoringStatus();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getNewName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]newName;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setNewName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String newName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.newName = [CtVariableReadImpl]newName;
    }
}
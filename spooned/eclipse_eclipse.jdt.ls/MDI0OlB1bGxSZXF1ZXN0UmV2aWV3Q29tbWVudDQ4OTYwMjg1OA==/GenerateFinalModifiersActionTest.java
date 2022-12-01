[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * Copyright (c) 2020 Microsoft Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Microsoft Corporation - initial API and implementation
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package org.eclipse.jdt.ls.core.internal.handlers;
[CtUnresolvedImport]import org.eclipse.jdt.core.ICompilationUnit;
[CtUnresolvedImport]import org.eclipse.jdt.core.IJavaProject;
[CtUnresolvedImport]import org.eclipse.jdt.ls.core.internal.correction.TestOptions;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.eclipse.jdt.ls.core.internal.correction.AbstractQuickFixTest;
[CtUnresolvedImport]import org.eclipse.jdt.core.IPackageFragment;
[CtUnresolvedImport]import org.eclipse.jdt.core.IPackageFragmentRoot;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl]public class GenerateFinalModifiersActionTest extends [CtTypeReferenceImpl]org.eclipse.jdt.ls.core.internal.correction.AbstractQuickFixTest {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jdt.core.IJavaProject fJProject;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.eclipse.jdt.core.IPackageFragmentRoot fSourceFolder;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]fJProject = [CtInvocationImpl]newEmptyProject();
        [CtInvocationImpl][CtFieldReadImpl]fJProject.setOptions([CtInvocationImpl][CtTypeAccessImpl]org.eclipse.jdt.ls.core.internal.correction.TestOptions.getDefaultOptions());
        [CtAssignmentImpl][CtFieldWriteImpl]fSourceFolder = [CtInvocationImpl][CtFieldReadImpl]fJProject.getPackageFragmentRoot([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]fJProject.getProject().getFolder([CtLiteralImpl]"src"));
        [CtInvocationImpl][CtThisAccessImpl]this.setIgnoredKind([CtLiteralImpl]"");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testInsertFinalModifierWherePossible() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jdt.core.IPackageFragment pack = [CtInvocationImpl][CtFieldReadImpl]fSourceFolder.createPackageFragment([CtLiteralImpl]"test", [CtLiteralImpl]false, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder buf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"package test;\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"public class A {\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"  public static void abc(int x){\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"    int b = 3;\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"  }\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"}");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jdt.core.ICompilationUnit cu = [CtInvocationImpl][CtVariableReadImpl]pack.createCompilationUnit([CtLiteralImpl]"A.java", [CtInvocationImpl][CtVariableReadImpl]buf.toString(), [CtLiteralImpl]false, [CtLiteralImpl]null);
        [CtAssignmentImpl][CtVariableWriteImpl]buf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"package test;\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"public class A {\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"  public static void abc(final int x){\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"    final int b = 3;\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"  }\n");
        [CtInvocationImpl][CtVariableReadImpl]buf.append([CtLiteralImpl]"}");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.jdt.ls.core.internal.handlers.Expected e1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.jdt.ls.core.internal.handlers.Expected([CtLiteralImpl]"Change modifiers to final where possible", [CtInvocationImpl][CtVariableReadImpl]buf.toString());
        [CtInvocationImpl]assertCodeActions([CtVariableReadImpl]cu, [CtVariableReadImpl]e1);
    }
}
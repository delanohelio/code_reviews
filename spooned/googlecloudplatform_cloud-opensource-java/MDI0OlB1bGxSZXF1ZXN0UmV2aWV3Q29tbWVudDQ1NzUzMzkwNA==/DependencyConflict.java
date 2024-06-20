[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Google LLC.

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
[CtPackageDeclarationImpl]package com.google.cloud.tools.opensource.classpath;
[CtUnresolvedImport]import com.google.cloud.tools.opensource.dependencies.Artifacts;
[CtUnresolvedImport]import com.google.cloud.tools.opensource.dependencies.DependencyPath;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkNotNull;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import org.eclipse.aether.artifact.Artifact;
[CtClassImpl][CtJavaDocImpl]/**
 * Diamond dependency conflict caused a {@link LinkageProblem} where the {@link LinkageProblem}'s
 * invalid reference points to the symbol in {@code pathToSelectedArtifact.getLeaf()} but a valid
 * symbol is in {@code pathToUnselectedArtifact.getLeaf()}.
 */
class DependencyConflict extends [CtTypeReferenceImpl]com.google.cloud.tools.opensource.classpath.LinkageProblemCause {
    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.tools.opensource.classpath.Symbol symbol;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath pathToUnselectedArtifact;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath pathToSelectedArtifact;

    [CtConstructorImpl]DependencyConflict([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.tools.opensource.classpath.Symbol symbol, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath pathToSelectedArtifact, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath pathToUnselectedArtifact) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.symbol = [CtInvocationImpl]checkNotNull([CtVariableReadImpl]symbol);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pathToUnselectedArtifact = [CtInvocationImpl]checkNotNull([CtVariableReadImpl]pathToUnselectedArtifact);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pathToSelectedArtifact = [CtInvocationImpl]checkNotNull([CtVariableReadImpl]pathToSelectedArtifact);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath getPathToUnselectedArtifact() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]pathToUnselectedArtifact;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.google.cloud.tools.opensource.dependencies.DependencyPath getPathToSelectedArtifact() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]pathToSelectedArtifact;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.aether.artifact.Artifact selected = [CtInvocationImpl][CtFieldReadImpl]pathToSelectedArtifact.getLeaf();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.aether.artifact.Artifact unselected = [CtInvocationImpl][CtFieldReadImpl]pathToUnselectedArtifact.getLeaf();
        [CtReturnImpl][CtCommentImpl]// com.google:foo:1 (selected in the class path) does not have symbol 'java.lang.Object's
        [CtCommentImpl]// method equals(Object arg1)' but version '2' has it.
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Dependency conflict: " + [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.tools.opensource.dependencies.Artifacts.toCoordinates([CtVariableReadImpl]selected)) + [CtLiteralImpl]" (selected for the class path) does not have the symbol \"") + [CtFieldReadImpl]symbol) + [CtLiteralImpl]"\" but ") + [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.tools.opensource.dependencies.Artifacts.toCoordinates([CtVariableReadImpl]unselected)) + [CtLiteralImpl]" (unselected) defines it.\n") + [CtLiteralImpl]"  selected: ") + [CtFieldReadImpl]pathToSelectedArtifact) + [CtLiteralImpl]"\n  unselected: ") + [CtFieldReadImpl]pathToUnselectedArtifact;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object other) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]other) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]other == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]other.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.tools.opensource.classpath.DependencyConflict that = [CtVariableReadImpl](([CtTypeReferenceImpl]com.google.cloud.tools.opensource.classpath.DependencyConflict) (other));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]symbol, [CtFieldReadImpl][CtVariableReadImpl]that.symbol) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]pathToUnselectedArtifact, [CtFieldReadImpl][CtVariableReadImpl]that.pathToUnselectedArtifact)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]pathToSelectedArtifact, [CtFieldReadImpl][CtVariableReadImpl]that.pathToSelectedArtifact);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtFieldReadImpl]symbol, [CtFieldReadImpl]pathToUnselectedArtifact, [CtFieldReadImpl]pathToSelectedArtifact);
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.jena.shacl.engine.constraint;
[CtUnresolvedImport]import org.apache.jena.shacl.compact.writer.CompactOut;
[CtUnresolvedImport]import org.apache.jena.graph.Node;
[CtUnresolvedImport]import org.apache.jena.shacl.engine.ValidationContext;
[CtUnresolvedImport]import org.apache.jena.riot.out.NodeFormatter;
[CtUnresolvedImport]import org.apache.jena.shacl.vocabulary.SHJ;
[CtUnresolvedImport]import org.apache.jena.atlas.io.IndentedWriter;
[CtUnresolvedImport]import org.apache.jena.shacl.validation.ReportItem;
[CtClassImpl][CtJavaDocImpl]/**
 * A constraint that causes a violation if it's object is "true"
 */
public class JViolationConstraint extends [CtTypeReferenceImpl]org.apache.jena.shacl.engine.constraint.ConstraintTerm {
    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean generateViolation;

    [CtConstructorImpl]public JViolationConstraint([CtParameterImpl][CtTypeReferenceImpl]boolean generateViolation) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.generateViolation = [CtVariableReadImpl]generateViolation;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isGenerateViolation() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]generateViolation;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.jena.graph.Node getComponent() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.apache.jena.shacl.vocabulary.SHJ.ViolationConstraintComponent;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.jena.shacl.validation.ReportItem validate([CtParameterImpl][CtTypeReferenceImpl]org.apache.jena.shacl.engine.ValidationContext vCxt, [CtParameterImpl][CtTypeReferenceImpl]org.apache.jena.graph.Node n) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]generateViolation)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.jena.shacl.validation.ReportItem([CtLiteralImpl]"Violation");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void printCompact([CtParameterImpl][CtTypeReferenceImpl]org.apache.jena.atlas.io.IndentedWriter out, [CtParameterImpl][CtTypeReferenceImpl]org.apache.jena.riot.out.NodeFormatter nodeFmt) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.jena.shacl.compact.writer.CompactOut.compactUnquotedString([CtVariableReadImpl]out, [CtLiteralImpl]"violation", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.toString([CtFieldReadImpl]generateViolation));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Violation[" + [CtFieldReadImpl]generateViolation) + [CtLiteralImpl]"]";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]158 + [CtConditionalImpl]([CtFieldReadImpl]generateViolation ? [CtLiteralImpl]1 : [CtLiteralImpl]2);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object obj) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]obj)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]obj == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]obj.getClass())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.shacl.engine.constraint.JViolationConstraint other = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.jena.shacl.engine.constraint.JViolationConstraint) (obj));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]generateViolation == [CtFieldReadImpl][CtVariableReadImpl]other.generateViolation;
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

 http://aws.amazon.com/apache2.0

or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package software.amazon.smithy.model.knowledge;
[CtUnresolvedImport]import software.amazon.smithy.model.traits.PaginatedTrait;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ServiceShape;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.OperationShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.MemberShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.StructureShape;
[CtClassImpl][CtJavaDocImpl]/**
 * Resolved and valid pagination information about an operation in a service.
 */
public final class PaginationInfo {
    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ServiceShape service;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.OperationShape operation;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape input;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape output;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.traits.PaginatedTrait paginatedTrait;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape inputToken;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> outputToken;

    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape pageSize;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> items;

    [CtConstructorImpl]PaginationInfo([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ServiceShape service, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.OperationShape operation, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape input, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape output, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.traits.PaginatedTrait paginatedTrait, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape inputToken, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> outputToken, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape pageSize, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> items) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.service = [CtVariableReadImpl]service;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.operation = [CtVariableReadImpl]operation;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.input = [CtVariableReadImpl]input;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.output = [CtVariableReadImpl]output;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.paginatedTrait = [CtVariableReadImpl]paginatedTrait;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputToken = [CtVariableReadImpl]inputToken;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.outputToken = [CtVariableReadImpl]outputToken;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pageSize = [CtVariableReadImpl]pageSize;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.items = [CtVariableReadImpl]items;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ServiceShape getService() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]service;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.OperationShape getOperation() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]operation;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape getInput() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]input;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape getOutput() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]output;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the paginated trait of the operation merged with the service.
     *
     * @return Returns the resolved paginated trait.
     */
    public [CtTypeReferenceImpl]software.amazon.smithy.model.traits.PaginatedTrait getPaginatedTrait() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]paginatedTrait.merge([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]service.getTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.PaginatedTrait.class).orElse([CtLiteralImpl]null));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape getInputTokenMember() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]inputToken;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the last {@link MemberShape} of the output path.
     * @deprecated See {@link PaginationInfo#getOutputTokenPath} to retrieve the full path.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape getOutputTokenMember() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]outputToken.get([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]outputToken.size() - [CtLiteralImpl]1);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the resolved output path identifiers as a list of {@link MemberShape}.
     *
     * @return A list of {@link MemberShape}.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> getOutputTokenPath() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]outputToken;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the last {@link MemberShape} of the items path.
     * @deprecated See {@link PaginationInfo#getItemsMemberPath} to retrieve the full path.
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> getItemsMember() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtFieldReadImpl]items.size();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]size == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]items.get([CtBinaryOperatorImpl][CtVariableReadImpl]size - [CtLiteralImpl]1));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the resolved items path identifiers as a list of {@link MemberShape}.
     *
     * @return A list of {@link MemberShape}.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> getItemsMemberPath() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]items;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> getPageSizeMember() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]pageSize);
    }
}
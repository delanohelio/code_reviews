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
[CtPackageDeclarationImpl]package software.amazon.smithy.linters;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import software.amazon.smithy.model.Model;
[CtUnresolvedImport]import software.amazon.smithy.model.validation.ValidatorService;
[CtImportImpl]import static java.lang.String.format;
[CtUnresolvedImport]import software.amazon.smithy.model.traits.TraitDefinition;
[CtUnresolvedImport]import software.amazon.smithy.model.validation.AbstractValidator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import software.amazon.smithy.model.traits.DeprecatedTrait;
[CtUnresolvedImport]import software.amazon.smithy.model.validation.ValidationEvent;
[CtClassImpl][CtJavaDocImpl]/**
 * Emits a validation event if a model contains a trait that has been marked as deprecated.
 */
public final class DeprecatedTraitsValidator extends [CtTypeReferenceImpl]software.amazon.smithy.model.validation.AbstractValidator {
    [CtClassImpl]public static final class Provider extends [CtTypeReferenceImpl][CtTypeReferenceImpl]software.amazon.smithy.model.validation.ValidatorService.Provider {
        [CtConstructorImpl]public Provider() [CtBlockImpl]{
            [CtInvocationImpl]super([CtFieldReadImpl]software.amazon.smithy.linters.DeprecatedTraitsValidator.class, [CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]software.amazon.smithy.linters.DeprecatedTraitsValidator::new);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]software.amazon.smithy.model.validation.ValidationEvent> validate([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.Model model) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]model.shapes().filter([CtLambdaImpl]([CtParameterImpl] shape) -> [CtInvocationImpl][CtVariableReadImpl]shape.hasTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.TraitDefinition.class)).filter([CtLambdaImpl]([CtParameterImpl] trait) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.DeprecatedTrait.class).isPresent()).map([CtLambdaImpl]([CtParameterImpl] deprecatedTrait) -> [CtInvocationImpl]warning([CtVariableReadImpl]deprecatedTrait, [CtInvocationImpl]format([CtLiteralImpl]"The %s trait is deprecated.", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deprecatedTrait.getId().getName()))).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }
}
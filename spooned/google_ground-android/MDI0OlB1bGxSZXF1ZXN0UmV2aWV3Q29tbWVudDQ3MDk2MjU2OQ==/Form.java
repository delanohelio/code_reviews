[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.google.android.gnd.model.form;
[CtUnresolvedImport]import static com.google.android.gnd.util.ImmutableListCollector.toImmutableList;
[CtUnresolvedImport]import java8.util.Comparators;
[CtUnresolvedImport]import static java8.util.stream.StreamSupport.stream;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import com.google.auto.value.AutoValue;
[CtUnresolvedImport]import java8.util.Optional;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import com.google.android.gnd.model.observation.Response;
[CtClassImpl][CtJavaDocImpl]/**
 * Describes the layout, field types, and validation rules of a user-defined form. Does not contain
 * actual form responses (see {@link Response} instead.
 */
[CtAnnotationImpl]@com.google.auto.value.AutoValue
public abstract class Form {
    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public abstract [CtTypeReferenceImpl]java.lang.String getId();

    [CtMethodImpl]public abstract [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]com.google.android.gnd.model.form.Element> getElements();

    [CtMethodImpl]public [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]com.google.android.gnd.model.form.Element> getElementsSorted() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java8.util.stream.StreamSupport.stream([CtInvocationImpl]getElements()).sorted([CtInvocationImpl][CtTypeAccessImpl]java8.util.Comparators.comparing([CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl][CtVariableReadImpl]e.getIndex())).collect([CtInvocationImpl]ImmutableListCollector.toImmutableList());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java8.util.Optional<[CtTypeReferenceImpl]com.google.android.gnd.model.form.Field> getField([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]java8.util.stream.StreamSupport.stream([CtInvocationImpl]getElements()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Element::getField).filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]f != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getId().equals([CtVariableReadImpl]id)).findFirst();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.google.android.gnd.model.form.Form.Builder newBuilder() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.android.gnd.model.form.AutoValue_Form.Builder().setElements([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of());
    }

    [CtClassImpl][CtAnnotationImpl]@com.google.auto.value.AutoValue.Builder
    public static abstract class Builder {
        [CtMethodImpl]public abstract [CtTypeReferenceImpl]com.google.android.gnd.model.form.Form.Builder setId([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]java.lang.String newId);

        [CtMethodImpl]public abstract [CtTypeReferenceImpl]com.google.android.gnd.model.form.Form.Builder setElements([CtParameterImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]com.google.android.gnd.model.form.Element> newElementsList);

        [CtMethodImpl]public abstract [CtTypeReferenceImpl]com.google.android.gnd.model.form.Form build();
    }
}
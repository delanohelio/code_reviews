[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 B2i Healthcare Pte Ltd, http://b2i.sg

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
[CtPackageDeclarationImpl]package com.b2international.snowowl.core.request;
[CtUnresolvedImport]import com.b2international.commons.options.Options;
[CtUnresolvedImport]import com.b2international.snowowl.core.domain.SetMembers;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.b2international.snowowl.core.uri.CodeSystemURI;
[CtUnresolvedImport]import com.b2international.snowowl.core.domain.BranchContext;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @since 7.7
 */
public final class MemberSearchRequest extends [CtTypeReferenceImpl]com.b2international.snowowl.core.request.SearchResourceRequest<[CtTypeReferenceImpl]com.b2international.snowowl.core.domain.BranchContext, [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMembers> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMembers createEmptyResult([CtParameterImpl][CtTypeReferenceImpl]int limit) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMembers([CtVariableReadImpl]limit, [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMembers doExecute([CtParameterImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.domain.BranchContext context) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.b2international.commons.options.Options options = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.b2international.commons.options.Options.builder().putAll([CtInvocationImpl]options()).put([CtTypeAccessImpl]SetMemberSearchRequestEvaluator.OptionKey.AFTER, [CtInvocationImpl]searchAfter()).put([CtTypeAccessImpl]SetMemberSearchRequestEvaluator.OptionKey.LIMIT, [CtInvocationImpl]limit()).put([CtTypeAccessImpl]SetMemberSearchRequestEvaluator.OptionKey.LOCALES, [CtInvocationImpl]locales()).put([CtTypeAccessImpl]SearchResourceRequest.OptionKey.SORT_BY, [CtInvocationImpl]sortBy()).build();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.service([CtFieldReadImpl]com.b2international.snowowl.core.request.SetMemberSearchRequestEvaluator.class).evaluate([CtInvocationImpl][CtVariableReadImpl]context.service([CtFieldReadImpl]com.b2international.snowowl.core.uri.CodeSystemURI.class), [CtVariableReadImpl]context, [CtVariableReadImpl]options);
    }
}
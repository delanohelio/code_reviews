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
[CtUnresolvedImport]import com.b2international.snowowl.core.internal.locks.DatastoreLockContextDescriptions;
[CtUnresolvedImport]import com.b2international.snowowl.core.domain.RepositoryContext;
[CtUnresolvedImport]import com.b2international.snowowl.core.locks.Locks;
[CtUnresolvedImport]import com.b2international.snowowl.core.api.SnowowlRuntimeException;
[CtUnresolvedImport]import com.b2international.snowowl.core.events.Request;
[CtUnresolvedImport]import com.b2international.commons.exceptions.ApiException;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @since 7.6
 */
public abstract class ImportRequest<[CtTypeParameterImpl]C extends [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.RepositoryContext, [CtTypeParameterImpl]R> implements [CtTypeReferenceImpl]com.b2international.snowowl.core.events.Request<[CtTypeParameterReferenceImpl]C, [CtTypeParameterReferenceImpl]R> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;

    [CtMethodImpl]protected abstract [CtTypeParameterReferenceImpl]R doImport([CtParameterImpl][CtTypeParameterReferenceImpl]C context);

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String parentLockContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.b2international.snowowl.core.internal.locks.DatastoreLockContextDescriptions.IMPORT;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]R execute([CtParameterImpl][CtTypeParameterReferenceImpl]C context) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.locks.Locks locks = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.b2international.snowowl.core.locks.Locks.on([CtVariableReadImpl]context).lock([CtInvocationImpl]parentLockContext())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]doImport([CtVariableReadImpl]context);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.b2international.commons.exceptions.ApiException) [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl](([CtTypeReferenceImpl]com.b2international.commons.exceptions.ApiException) (e));
            }
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.b2international.snowowl.core.api.SnowowlRuntimeException.wrap([CtVariableReadImpl]e);
        }
    }
}
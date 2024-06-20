[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Google LLC.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
 */
[CtPackageDeclarationImpl]package com.google.cloud.tools.jib.api.buildplan;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtClassImpl]public class Platform {
    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private [CtTypeReferenceImpl]java.lang.String os;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    private [CtTypeReferenceImpl]java.lang.String architecture;

    [CtConstructorImpl]public Platform([CtParameterImpl][CtTypeReferenceImpl]java.lang.String os, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String architecture) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.os = [CtVariableReadImpl]os;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.architecture = [CtVariableReadImpl]architecture;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getOs() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]os;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getArchitecture() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]architecture;
    }
}
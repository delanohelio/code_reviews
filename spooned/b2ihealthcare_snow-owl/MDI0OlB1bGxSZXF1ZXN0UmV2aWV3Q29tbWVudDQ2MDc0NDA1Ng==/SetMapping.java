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
[CtPackageDeclarationImpl]package com.b2international.snowowl.core.domain;
[CtUnresolvedImport]import com.b2international.snowowl.core.request.MappingCorrelation;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.b2international.snowowl.core.uri.ComponentURI;
[CtUnresolvedImport]import com.google.common.base.MoreObjects;
[CtImportImpl]import java.io.Serializable;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @since 7.8
 */
public final class SetMapping implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder builder() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder();
    }

    [CtClassImpl]public static class Builder {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String sourceIconId;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String sourceTerm;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI sourceComponentURI;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String targetTerm;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI targetComponentURI = [CtFieldReadImpl]com.b2international.snowowl.core.uri.ComponentURI.UNSPECIFIED;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean isActive;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.b2international.snowowl.core.request.MappingCorrelation mappingCorrelation = [CtFieldReadImpl]com.b2international.snowowl.core.request.MappingCorrelation.NOT_SPECIFIED;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer mapGroup = [CtLiteralImpl]0;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer mapPriority = [CtLiteralImpl]0;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mapRule = [CtLiteralImpl]"";

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mapAdvice = [CtLiteralImpl]"";

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder sourceTerm([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String sourceTerm) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceTerm = [CtVariableReadImpl]sourceTerm;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder sourceIconId([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String sourceIconId) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceIconId = [CtVariableReadImpl]sourceIconId;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder sourceComponentURI([CtParameterImpl]final [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI sourceComponentURI) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceComponentURI = [CtVariableReadImpl]sourceComponentURI;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder targetTerm([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String targetTerm) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.targetTerm = [CtVariableReadImpl]targetTerm;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder targetComponentURI([CtParameterImpl]final [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI targetComponentURI) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.targetComponentURI = [CtVariableReadImpl]targetComponentURI;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder active([CtParameterImpl]final [CtTypeReferenceImpl]boolean isActive) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isActive = [CtVariableReadImpl]isActive;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder mappingCorrelation([CtParameterImpl]final [CtTypeReferenceImpl]com.b2international.snowowl.core.request.MappingCorrelation mappingCorrelation) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mappingCorrelation = [CtVariableReadImpl]mappingCorrelation;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder mapGroup([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer mapGroup) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapGroup = [CtVariableReadImpl]mapGroup;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder mapPriority([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer mapPriority) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapPriority = [CtVariableReadImpl]mapPriority;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder mapRule([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String mapRule) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapRule = [CtVariableReadImpl]mapRule;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping.Builder mapAdvice([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String mapAdvice) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapAdvice = [CtVariableReadImpl]mapAdvice;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping build() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping([CtFieldReadImpl]sourceIconId, [CtFieldReadImpl]sourceTerm, [CtFieldReadImpl]sourceComponentURI, [CtFieldReadImpl]targetTerm, [CtFieldReadImpl]targetComponentURI, [CtFieldReadImpl]isActive, [CtFieldReadImpl]mappingCorrelation, [CtFieldReadImpl]mapGroup, [CtFieldReadImpl]mapPriority, [CtFieldReadImpl]mapRule, [CtFieldReadImpl]mapAdvice);
        }
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String sourceIconId;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String sourceTerm;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI sourceComponentURI;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String targetTerm;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI targetComponentURI;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isActive;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.b2international.snowowl.core.request.MappingCorrelation mappingCorrelation;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer mapGroup;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Integer mapPriority;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mapRule;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mapAdvice;

    [CtConstructorImpl]SetMapping([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sourceIconId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String sourceTerm, [CtParameterImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI sourceComponentURI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String targetTerm, [CtParameterImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI targetComponentURI, [CtParameterImpl][CtTypeReferenceImpl]boolean isActive, [CtParameterImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.request.MappingCorrelation mappingCorrelation, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer mapGroup, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer mapPriority, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String mapRule, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String mapAdvice) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceIconId = [CtVariableReadImpl]sourceIconId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceTerm = [CtVariableReadImpl]sourceTerm;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceComponentURI = [CtVariableReadImpl]sourceComponentURI;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.targetTerm = [CtVariableReadImpl]targetTerm;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.targetComponentURI = [CtVariableReadImpl]targetComponentURI;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isActive = [CtVariableReadImpl]isActive;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mappingCorrelation = [CtVariableReadImpl]mappingCorrelation;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapGroup = [CtVariableReadImpl]mapGroup;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapPriority = [CtVariableReadImpl]mapPriority;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapRule = [CtVariableReadImpl]mapRule;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapAdvice = [CtVariableReadImpl]mapAdvice;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getSourceIconId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sourceIconId;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getSourceTerm() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sourceTerm;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI getSourceComponentURI() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sourceComponentURI;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.uri.ComponentURI getTargetComponentURI() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]targetComponentURI;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTargetTerm() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]targetTerm;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isActive() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]isActive;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.b2international.snowowl.core.request.MappingCorrelation getMappingCorrelation() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mappingCorrelation;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMapGroup() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mapGroup;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMapPriority() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mapPriority;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getMapAdvice() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mapAdvice;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getMapRule() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mapRule;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.MoreObjects.toStringHelper([CtLiteralImpl]"SetMember").add([CtLiteralImpl]"sourceComponentURI", [CtFieldReadImpl]sourceComponentURI).add([CtLiteralImpl]"term", [CtFieldReadImpl]sourceTerm).add([CtLiteralImpl]"iconId", [CtFieldReadImpl]sourceIconId).add([CtLiteralImpl]"targetComponentURI", [CtFieldReadImpl]targetComponentURI).add([CtLiteralImpl]"targetTerm", [CtFieldReadImpl]targetTerm).add([CtLiteralImpl]"isActive", [CtFieldReadImpl]isActive).add([CtLiteralImpl]"mappingCorrelation", [CtFieldReadImpl]mappingCorrelation).add([CtLiteralImpl]"mapGroup", [CtFieldReadImpl]mapGroup).add([CtLiteralImpl]"mapPriority", [CtFieldReadImpl]mapPriority).toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtFieldReadImpl]sourceComponentURI, [CtFieldReadImpl]targetComponentURI, [CtFieldReadImpl]sourceTerm, [CtFieldReadImpl]targetTerm, [CtFieldReadImpl]sourceIconId, [CtFieldReadImpl]isActive, [CtFieldReadImpl]mappingCorrelation, [CtFieldReadImpl]mapGroup, [CtFieldReadImpl]mapPriority, [CtFieldReadImpl]mapRule, [CtFieldReadImpl]mapAdvice);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object obj) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]obj)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]obj == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]obj.getClass())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping other = [CtVariableReadImpl](([CtTypeReferenceImpl]com.b2international.snowowl.core.domain.SetMapping) (obj));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]sourceComponentURI, [CtFieldReadImpl][CtVariableReadImpl]other.sourceComponentURI) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]targetComponentURI, [CtFieldReadImpl][CtVariableReadImpl]other.targetComponentURI)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]sourceTerm, [CtFieldReadImpl][CtVariableReadImpl]other.sourceTerm)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]targetTerm, [CtFieldReadImpl][CtVariableReadImpl]other.targetTerm)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]sourceIconId, [CtFieldReadImpl][CtVariableReadImpl]other.sourceIconId)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]isActive, [CtFieldReadImpl][CtVariableReadImpl]other.isActive)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mappingCorrelation, [CtFieldReadImpl][CtVariableReadImpl]other.mappingCorrelation)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mapGroup, [CtFieldReadImpl][CtVariableReadImpl]other.mapGroup)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mapPriority, [CtFieldReadImpl][CtVariableReadImpl]other.mapPriority)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mapRule, [CtFieldReadImpl][CtVariableReadImpl]other.mapRule)) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]mapAdvice, [CtFieldReadImpl][CtVariableReadImpl]other.mapAdvice);
    }
}
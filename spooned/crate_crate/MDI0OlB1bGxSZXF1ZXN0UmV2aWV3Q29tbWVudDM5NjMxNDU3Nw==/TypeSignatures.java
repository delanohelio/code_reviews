[CompilationUnitImpl][CtCommentImpl]/* Licensed to Crate under one or more contributor license agreements.
See the NOTICE file distributed with this work for additional
information regarding copyright ownership.  Crate licenses this file
to you under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License.  You may
obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.  See the License for the specific language governing
permissions and limitations under the License.

However, if you have executed another commercial license agreement
with Crate these terms will supersede the license and you may use the
software solely pursuant to the terms of the relevant commercial
agreement.
 */
[CtPackageDeclarationImpl]package io.crate.types;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static io.crate.types.TypeSignature.parseTypeSignature;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtImportImpl]import java.util.Collections;
[CtClassImpl]public final class TypeSignatures {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the type with the specified signature.
     */
    public static [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> getType([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.TypeSignature signature) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String base = [CtInvocationImpl][CtVariableReadImpl]signature.getBase();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.crate.types.TypeSignatureParameter> parameters = [CtInvocationImpl][CtVariableReadImpl]signature.getParameters();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]base.equalsIgnoreCase([CtTypeAccessImpl]ArrayType.NAME)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]parameters.size() == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.crate.types.ArrayType<>([CtFieldReadImpl]UndefinedType.INSTANCE);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> innerType = [CtInvocationImpl]io.crate.types.TypeSignatures.getType([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parameters.get([CtLiteralImpl]0).getTypeSignature());
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.crate.types.ArrayType<>([CtVariableReadImpl]innerType);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]base.equalsIgnoreCase([CtTypeAccessImpl]ObjectType.NAME)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.var builder = [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.ObjectType.builder();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]parameters.size() - [CtLiteralImpl]1);) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.var valTypeSignature = [CtInvocationImpl][CtVariableReadImpl]parameters.get([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1);
                [CtInvocationImpl][CtVariableReadImpl]builder.setInnerType([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]i), [CtInvocationImpl]io.crate.types.TypeSignatures.getType([CtInvocationImpl][CtVariableReadImpl]valTypeSignature.getTypeSignature()));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.DataTypes.ofName([CtInvocationImpl][CtVariableReadImpl]signature.getBase());
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> getCommonSuperType([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> firstType, [CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> secondType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility compatibility = [CtInvocationImpl]io.crate.types.TypeSignatures.compatibility([CtVariableReadImpl]firstType, [CtVariableReadImpl]secondType);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]compatibility.isCompatible()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]compatibility.getCommonSuperType();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canCoerce([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> fromType, [CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> toType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fromType.isConvertableTo([CtVariableReadImpl]toType);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility compatibility([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> fromType, [CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> toType) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fromType.equals([CtVariableReadImpl]toType)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtVariableReadImpl]toType, [CtLiteralImpl]true);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fromType.equals([CtTypeAccessImpl]UndefinedType.INSTANCE)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtVariableReadImpl]toType, [CtLiteralImpl]true);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]toType.equals([CtTypeAccessImpl]UndefinedType.INSTANCE)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtVariableReadImpl]fromType, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// If given types share the same base, e.g. arrays, parameter types must be compatible.
        [CtTypeReferenceImpl]java.lang.String fromTypeBaseName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromType.getTypeSignature().getBase();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String toTypeBaseName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]toType.getTypeSignature().getBase();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fromTypeBaseName.equals([CtVariableReadImpl]toTypeBaseName)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]io.crate.types.TypeSignatures.isCovariantParametrizedType([CtVariableReadImpl]fromType)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]io.crate.types.TypeSignatures.typeCompatibilityForCovariantParametrizedType([CtVariableReadImpl]fromType, [CtVariableReadImpl]toType);
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtVariableReadImpl]fromType, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Use possible common super type (safe conversion)
        [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> commonSuperType = [CtInvocationImpl]io.crate.types.TypeSignatures.convertTypeByPrecedence([CtVariableReadImpl]fromType, [CtVariableReadImpl]toType);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]commonSuperType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtVariableReadImpl]commonSuperType, [CtInvocationImpl][CtVariableReadImpl]commonSuperType.equals([CtVariableReadImpl]toType));
        }
        [CtLocalVariableImpl][CtCommentImpl]// Try to force conversion, first to the target type or if fails to the source type (possible unsafe conversion)
        [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> coercedType = [CtInvocationImpl]io.crate.types.TypeSignatures.coerceTypeBase([CtVariableReadImpl]fromType, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]toType.getTypeSignature().getBase());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]coercedType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]io.crate.types.TypeSignatures.compatibility([CtVariableReadImpl]coercedType, [CtVariableReadImpl]toType);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]coercedType = [CtInvocationImpl]io.crate.types.TypeSignatures.coerceTypeBase([CtVariableReadImpl]toType, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromType.getTypeSignature().getBase());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]coercedType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility typeCompatibility = [CtInvocationImpl]io.crate.types.TypeSignatures.compatibility([CtVariableReadImpl]fromType, [CtVariableReadImpl]coercedType);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]typeCompatibility.isCompatible()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.incompatible();
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtInvocationImpl][CtVariableReadImpl]typeCompatibility.getCommonSuperType(), [CtLiteralImpl]false);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.incompatible();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> convertTypeByPrecedence([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> arg1, [CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> arg2) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> higherPrecedenceArg;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> lowerPrecedenceArg;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]arg1.precedes([CtVariableReadImpl]arg2)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]higherPrecedenceArg = [CtVariableReadImpl]arg1;
            [CtAssignmentImpl][CtVariableWriteImpl]lowerPrecedenceArg = [CtVariableReadImpl]arg2;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]higherPrecedenceArg = [CtVariableReadImpl]arg2;
            [CtAssignmentImpl][CtVariableWriteImpl]lowerPrecedenceArg = [CtVariableReadImpl]arg1;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean lowerPrecedenceCastable = [CtInvocationImpl][CtVariableReadImpl]lowerPrecedenceArg.isConvertableTo([CtVariableReadImpl]higherPrecedenceArg);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean higherPrecedenceCastable = [CtInvocationImpl][CtVariableReadImpl]higherPrecedenceArg.isConvertableTo([CtVariableReadImpl]lowerPrecedenceArg);
        [CtIfImpl]if ([CtVariableReadImpl]lowerPrecedenceCastable) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]higherPrecedenceArg;
        } else [CtIfImpl]if ([CtVariableReadImpl]higherPrecedenceCastable) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]lowerPrecedenceArg;
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> coerceTypeBase([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> sourceType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String resultTypeBase) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> resultType = [CtInvocationImpl]io.crate.types.TypeSignatures.getType([CtInvocationImpl]io.crate.types.TypeSignature.parseTypeSignature([CtVariableReadImpl]resultTypeBase));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resultType.equals([CtVariableReadImpl]sourceType)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]sourceType;
        }
        [CtReturnImpl]return [CtInvocationImpl]io.crate.types.TypeSignatures.convertTypeByPrecedence([CtVariableReadImpl]sourceType, [CtVariableReadImpl]resultType);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isCovariantParametrizedType([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> type) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// if we ever introduce contravariant, this function should be changed to return an enumeration: INVARIANT, COVARIANT, CONTRAVARIANT
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.crate.types.ObjectType) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.crate.types.ArrayType);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility typeCompatibilityForCovariantParametrizedType([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> fromType, [CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> toType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]io.crate.types.TypeSignatureParameter> commonParameterTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?>> fromTypeParameters = [CtInvocationImpl][CtVariableReadImpl]fromType.getTypeParameters();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?>> toTypeParameters = [CtInvocationImpl][CtVariableReadImpl]toType.getTypeParameters();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fromTypeParameters.size() != [CtInvocationImpl][CtVariableReadImpl]toTypeParameters.size()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.incompatible();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean coercible = [CtLiteralImpl]true;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]fromTypeParameters.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility compatibility = [CtInvocationImpl]io.crate.types.TypeSignatures.compatibility([CtInvocationImpl][CtVariableReadImpl]fromTypeParameters.get([CtVariableReadImpl]i), [CtInvocationImpl][CtVariableReadImpl]toTypeParameters.get([CtVariableReadImpl]i));
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]compatibility.isCompatible()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.incompatible();
            }
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]coercible &= [CtInvocationImpl][CtVariableReadImpl]compatibility.isCoercible();
            [CtInvocationImpl][CtVariableReadImpl]commonParameterTypes.add([CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatureParameter.of([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]compatibility.getCommonSuperType().getTypeSignature()));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String typeBase = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromType.getTypeSignature().getBase();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.crate.types.TypeSignatures.TypeCompatibility.compatible([CtInvocationImpl]io.crate.types.TypeSignatures.getType([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.crate.types.TypeSignature([CtVariableReadImpl]typeBase, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtVariableReadImpl]commonParameterTypes))), [CtVariableReadImpl]coercible);
    }

    [CtClassImpl]public static class TypeCompatibility {
        [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nullable
        private final [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> commonSuperType;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean coercible;

        [CtConstructorImpl]private TypeCompatibility([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> commonSuperType, [CtParameterImpl][CtTypeReferenceImpl]boolean coercible) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.commonSuperType = [CtVariableReadImpl]commonSuperType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.coercible = [CtVariableReadImpl]coercible;
        }

        [CtMethodImpl]private static [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility compatible([CtParameterImpl][CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> commonSuperType, [CtParameterImpl][CtTypeReferenceImpl]boolean coercible) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility([CtVariableReadImpl]commonSuperType, [CtVariableReadImpl]coercible);
        }

        [CtMethodImpl]private static [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility incompatible() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.crate.types.TypeSignatures.TypeCompatibility([CtLiteralImpl]null, [CtLiteralImpl]false);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isCompatible() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]commonSuperType != [CtLiteralImpl]null;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]io.crate.types.DataType<[CtWildcardReferenceImpl]?> getCommonSuperType() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]commonSuperType == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Types are not compatible");
            }
            [CtReturnImpl]return [CtFieldReadImpl]commonSuperType;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isCoercible() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]coercible;
        }
    }
}
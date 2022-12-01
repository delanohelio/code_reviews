[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2013, 2020 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.eclipse.xtext.xbase.ui.editor.copyqualifiedname;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.XExpression;
[CtUnresolvedImport]import org.eclipse.xtext.common.types.JvmOperation;
[CtUnresolvedImport]import org.eclipse.xtext.ui.editor.copyqualifiedname.DefaultCopyQualifiedNameService;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.XAbstractFeatureCall;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.override.ResolvedFeatures;
[CtUnresolvedImport]import com.google.inject.Inject;
[CtUnresolvedImport]import org.eclipse.emf.ecore.EObject;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable;
[CtUnresolvedImport]import org.eclipse.xtext.common.types.JvmExecutable;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.XConstructorCall;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.override.OverrideHelper;
[CtUnresolvedImport]import com.google.common.collect.FluentIterable;
[CtUnresolvedImport]import com.google.common.base.Optional;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.IResolvedTypes;
[CtUnresolvedImport]import org.eclipse.xtext.common.types.JvmConstructor;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.eclipse.xtext.xbase.typesystem.override.IResolvedOperation;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Anton Kosyakov - Initial contribution and API
 * @since 2.4
 */
public class XbaseCopyQualifiedNameService extends [CtTypeReferenceImpl]org.eclipse.xtext.ui.editor.copyqualifiedname.DefaultCopyQualifiedNameService {
    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.OverrideHelper overrideHelper;

    [CtFieldImpl][CtAnnotationImpl]@com.google.inject.Inject
    private [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver typeResolver;

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String toQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable jvmExecutable) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]toFullyQualifiedName([CtVariableReadImpl]jvmExecutable)[CtCommentImpl]// 
         + [CtLiteralImpl]"(") + [CtInvocationImpl]toQualifiedNames([CtInvocationImpl][CtVariableReadImpl]jvmExecutable.getParameters(), [CtLambdaImpl]([CtParameterImpl] parameter) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parameter.getParameterType().getSimpleName())) + [CtLiteralImpl]")";
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String toQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XExpression expression, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable resolvedExecutable, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable executable, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.XExpression> arguments) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference actualType = [CtInvocationImpl][CtVariableReadImpl]resolvedTypes.getActualType([CtVariableReadImpl]expression);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]actualType != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]actualType.isAny())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]actualType.isUnknown())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]actualType.getHumanReadableName();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl][CtVariableReadImpl]arguments.indexOf([CtVariableReadImpl]expression);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resolvedExecutable == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]executable.getParameters().get([CtVariableReadImpl]index).getParameterType().getSimpleName();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resolvedExecutable.getResolvedParameterTypes().get([CtVariableReadImpl]index).getSimpleName();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject constructor, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject constructorCall) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) (constructor)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall) (constructorCall)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) (constructor)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall) (constructorCall)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) (constructor)), [CtVariableReadImpl]constructorCall);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable) (constructor)), [CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Void) (null)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl]constructor, [CtVariableReadImpl]constructorCall);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtVariableReadImpl]constructor, [CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Void) (null)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]constructor == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]constructorCall != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Void) (null)), [CtVariableReadImpl]constructorCall);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_getQualifiedName([CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Void) (null)), [CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Void) (null)));
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String _getQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable jvmExecutable, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.emf.ecore.EObject context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]toQualifiedName([CtVariableReadImpl]jvmExecutable);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String _getQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable jvmExecutable, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Void context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]toQualifiedName([CtVariableReadImpl]jvmExecutable);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String _getQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable executable, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall featureCall) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes = [CtInvocationImpl][CtFieldReadImpl]typeResolver.resolveTypes([CtVariableReadImpl]featureCall);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable resolvedExecutable = [CtInvocationImpl]resolveExecutable([CtVariableReadImpl]executable, [CtVariableReadImpl]featureCall, [CtVariableReadImpl]resolvedTypes);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]toFullyQualifiedName([CtVariableReadImpl]executable)[CtCommentImpl]// 
         + [CtLiteralImpl]"(") + [CtInvocationImpl][CtCommentImpl]// 
        toQualifiedNames([CtInvocationImpl][CtVariableReadImpl]featureCall.getActualArguments(), [CtLambdaImpl]([CtParameterImpl] argument) -> [CtInvocationImpl]toQualifiedName([CtVariableReadImpl]argument, [CtVariableReadImpl]resolvedExecutable, [CtVariableReadImpl]executable, [CtVariableReadImpl]resolvedTypes, [CtInvocationImpl][CtVariableReadImpl]featureCall.getActualArguments()))) + [CtLiteralImpl]")";
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String _getQualifiedName([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor constructor, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall constructorCall) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes = [CtInvocationImpl][CtFieldReadImpl]typeResolver.resolveTypes([CtVariableReadImpl]constructorCall);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable resolvedExecutable = [CtInvocationImpl]resolveExecutable([CtVariableReadImpl]constructor, [CtVariableReadImpl]constructorCall, [CtVariableReadImpl]resolvedTypes);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]toFullyQualifiedName([CtVariableReadImpl]constructor)[CtCommentImpl]// 
         + [CtLiteralImpl]"(") + [CtInvocationImpl][CtCommentImpl]// 
        toQualifiedNames([CtInvocationImpl][CtVariableReadImpl]constructorCall.getArguments(), [CtLambdaImpl]([CtParameterImpl] argument) -> [CtInvocationImpl]toQualifiedName([CtVariableReadImpl]argument, [CtVariableReadImpl]resolvedExecutable, [CtVariableReadImpl]constructor, [CtVariableReadImpl]resolvedTypes, [CtInvocationImpl][CtVariableReadImpl]constructorCall.getArguments()))) + [CtLiteralImpl]")";
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable resolveExecutable([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmExecutable consturctor, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XExpression expression, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]consturctor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) && [CtBinaryOperatorImpl]([CtVariableReadImpl]expression instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_resolveExecutable([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) (consturctor)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall) (expression)), [CtVariableReadImpl]resolvedTypes);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]consturctor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) && [CtBinaryOperatorImpl]([CtVariableReadImpl]expression instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_resolveExecutable([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor) (consturctor)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall) (expression)), [CtVariableReadImpl]resolvedTypes);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]consturctor instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmOperation) && [CtBinaryOperatorImpl]([CtVariableReadImpl]expression instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]_resolveExecutable([CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmOperation) (consturctor)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall) (expression)), [CtVariableReadImpl]resolvedTypes);
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unhandled parameter types: " + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]consturctor, [CtVariableReadImpl]expression, [CtVariableReadImpl]resolvedTypes).toString());
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable _resolveExecutable([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor consturctor, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall featureCall, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XExpression actualReceiver = [CtInvocationImpl][CtVariableReadImpl]featureCall.getActualReceiver();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference actualType = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actualReceiver != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]actualType = [CtInvocationImpl][CtVariableReadImpl]resolvedTypes.getActualType([CtVariableReadImpl]actualReceiver);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.ResolvedFeatures resolvedFeatures = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actualType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resolvedFeatures = [CtInvocationImpl][CtFieldReadImpl]overrideHelper.getResolvedFeatures([CtVariableReadImpl]actualType);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor> declaredConstructors = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resolvedFeatures != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]declaredConstructors = [CtInvocationImpl][CtVariableReadImpl]resolvedFeatures.getDeclaredConstructors();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Optional<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor> resolvedConstructor = [CtInvocationImpl][CtCommentImpl]// 
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.FluentIterable.from([CtVariableReadImpl]declaredConstructors).filter([CtLambdaImpl]([CtParameterImpl] declaredConstructor) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]declaredConstructor.getDeclaration().equals([CtVariableReadImpl]consturctor)).first();
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]resolvedConstructor.isPresent() ? [CtInvocationImpl][CtVariableReadImpl]resolvedConstructor.get() : [CtLiteralImpl]null;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable _resolveExecutable([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmConstructor constructor, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XConstructorCall constructorCall, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference actualType = [CtInvocationImpl][CtVariableReadImpl]resolvedTypes.getActualType([CtVariableReadImpl]constructorCall);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.ResolvedFeatures resolvedFeatures = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actualType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resolvedFeatures = [CtInvocationImpl][CtFieldReadImpl]overrideHelper.getResolvedFeatures([CtVariableReadImpl]actualType);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor> declaredConstructors = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resolvedFeatures != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]declaredConstructors = [CtInvocationImpl][CtVariableReadImpl]resolvedFeatures.getDeclaredConstructors();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Optional<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor> resolvedConstrutor = [CtInvocationImpl][CtCommentImpl]// 
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.FluentIterable.from([CtVariableReadImpl]declaredConstructors).filter([CtLambdaImpl]([CtParameterImpl] constr) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]constr.getDeclaration().equals([CtVariableReadImpl]constructor)).first();
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]resolvedConstrutor.isPresent() ? [CtInvocationImpl][CtVariableReadImpl]resolvedConstrutor.get() : [CtLiteralImpl]null;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedExecutable _resolveExecutable([CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.common.types.JvmOperation operation, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XAbstractFeatureCall featureCall, [CtParameterImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.IResolvedTypes resolvedTypes) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.XExpression actualReceiver = [CtInvocationImpl][CtVariableReadImpl]featureCall.getActualReceiver();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference actualType = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actualReceiver != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]actualType = [CtInvocationImpl][CtVariableReadImpl]resolvedTypes.getActualType([CtVariableReadImpl]actualReceiver);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.ResolvedFeatures resolvedFeatures = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actualType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resolvedFeatures = [CtInvocationImpl][CtFieldReadImpl]overrideHelper.getResolvedFeatures([CtVariableReadImpl]actualType);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedOperation> allOperations = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resolvedFeatures != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]allOperations = [CtInvocationImpl][CtVariableReadImpl]resolvedFeatures.getAllOperations();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Optional<[CtTypeReferenceImpl]org.eclipse.xtext.xbase.typesystem.override.IResolvedOperation> resolvedOperation = [CtInvocationImpl][CtCommentImpl]// 
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.FluentIterable.from([CtVariableReadImpl]allOperations).filter([CtLambdaImpl]([CtParameterImpl] oper) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]oper.getDeclaration().equals([CtVariableReadImpl]operation)).first();
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]resolvedOperation.isPresent() ? [CtInvocationImpl][CtVariableReadImpl]resolvedOperation.get() : [CtLiteralImpl]null;
    }
}
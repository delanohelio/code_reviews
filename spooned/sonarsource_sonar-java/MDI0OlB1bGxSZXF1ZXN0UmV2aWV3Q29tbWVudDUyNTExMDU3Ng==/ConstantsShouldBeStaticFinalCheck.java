[CompilationUnitImpl][CtCommentImpl]/* SonarQube Java
Copyright (C) 2012-2020 SonarSource SA
mailto:info AT sonarsource DOT com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
[CtPackageDeclarationImpl]package org.sonar.java.checks;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.Modifier;
[CtUnresolvedImport]import org.sonar.java.model.ExpressionUtils;
[CtUnresolvedImport]import org.sonar.java.model.JavaTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.Tree;
[CtUnresolvedImport]import org.sonar.java.model.ModifiersUtils;
[CtUnresolvedImport]import org.sonar.plugins.java.api.JavaFileScannerContext;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.ExpressionTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.VariableTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.ClassTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.sonar.check.Rule;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.IdentifierTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.MethodReferenceTree;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtAnnotationImpl]@org.sonar.check.Rule(key = [CtLiteralImpl]"S1170")
public class ConstantsShouldBeStaticFinalCheck extends [CtTypeReferenceImpl]org.sonar.plugins.java.api.IssuableSubscriptionVisitor {
    [CtFieldImpl]private [CtTypeReferenceImpl]int nestedClassesLevel;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree.Kind> nodesToVisit() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtTypeAccessImpl]Tree.Kind.CLASS);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setContext([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.JavaFileScannerContext context) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]nestedClassesLevel = [CtLiteralImpl]0;
        [CtInvocationImpl][CtSuperAccessImpl]super.setContext([CtVariableReadImpl]context);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void visitNode([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree tree) [CtBlockImpl]{
        [CtUnaryOperatorImpl][CtFieldWriteImpl]nestedClassesLevel++;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree member : [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.ClassTree) (tree)).members()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]member.is([CtTypeAccessImpl]Tree.Kind.VARIABLE)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree = [CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree) (member));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.staticNonFinal([CtVariableReadImpl]variableTree) && [CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.hasConstantInitializer([CtVariableReadImpl]variableTree)) && [CtUnaryOperatorImpl](![CtInvocationImpl]isObjectInInnerClass([CtVariableReadImpl]variableTree))) [CtBlockImpl]{
                    [CtInvocationImpl]reportIssue([CtInvocationImpl][CtVariableReadImpl]variableTree.simpleName(), [CtLiteralImpl]"Make this final field static too.");
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isObjectInInnerClass([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]nestedClassesLevel > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.ExpressionTree initializer = [CtInvocationImpl][CtVariableReadImpl]variableTree.initializer();
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variableTree.type().is([CtTypeAccessImpl]Tree.Kind.PRIMITIVE_TYPE) || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variableTree.symbol().type().is([CtLiteralImpl]"java.lang.String")) && [CtBinaryOperatorImpl]([CtVariableReadImpl]initializer != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]initializer.asConstant().isPresent());
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean staticNonFinal([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.isFinal([CtVariableReadImpl]variableTree) && [CtUnaryOperatorImpl](![CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.isStatic([CtVariableReadImpl]variableTree));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void leaveNode([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree tree) [CtBlockImpl]{
        [CtUnaryOperatorImpl][CtFieldWriteImpl]nestedClassesLevel--;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasConstantInitializer([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.ExpressionTree init = [CtInvocationImpl][CtVariableReadImpl]variableTree.initializer();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]init != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sonar.java.model.ExpressionUtils.skipParentheses([CtVariableReadImpl]init).is([CtTypeAccessImpl]Tree.Kind.METHOD_REFERENCE)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.MethodReferenceTree methodRef = [CtInvocationImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.MethodReferenceTree) ([CtTypeAccessImpl]org.sonar.java.model.ExpressionUtils.skipParentheses([CtVariableReadImpl]init)));
                [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.isInstanceIdentifier([CtInvocationImpl][CtVariableReadImpl]methodRef.expression())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]init.is([CtTypeAccessImpl]Tree.Kind.NEW_ARRAY)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.containsChildMatchingPredicate([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.java.model.JavaTree) (init)), [CtInvocationImpl][CtExecutableReferenceExpressionImpl](([CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree>) ([CtTypeAccessImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck::isIgnoredKind)).or([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck::isThisOrSuper));
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isIgnoredKind([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree tree) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tree.is([CtTypeAccessImpl]Tree.Kind.METHOD_INVOCATION, [CtTypeAccessImpl]Tree.Kind.NEW_CLASS);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isThisOrSuper([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree tree) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tree.is([CtTypeAccessImpl]Tree.Kind.IDENTIFIER)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.IdentifierTree) (tree)).name();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"super".equals([CtVariableReadImpl]name) || [CtInvocationImpl][CtLiteralImpl]"this".equals([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isInstanceIdentifier([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree expression) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]expression.is([CtTypeAccessImpl]Tree.Kind.IDENTIFIER) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.IdentifierTree) (expression)).symbol().isStatic());
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean containsChildMatchingPredicate([CtParameterImpl][CtTypeReferenceImpl]org.sonar.java.model.JavaTree tree, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree> predicate) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]predicate.test([CtVariableReadImpl]tree)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]tree.isLeaf()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Tree javaTree : [CtInvocationImpl][CtVariableReadImpl]tree.getChildren()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]javaTree != [CtLiteralImpl]null) && [CtInvocationImpl]org.sonar.java.checks.ConstantsShouldBeStaticFinalCheck.containsChildMatchingPredicate([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.java.model.JavaTree) (javaTree)), [CtVariableReadImpl]predicate)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isFinal([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sonar.java.model.ModifiersUtils.hasModifier([CtInvocationImpl][CtVariableReadImpl]variableTree.modifiers(), [CtTypeAccessImpl]Modifier.FINAL);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isStatic([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.VariableTree variableTree) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sonar.java.model.ModifiersUtils.hasModifier([CtInvocationImpl][CtVariableReadImpl]variableTree.modifiers(), [CtTypeAccessImpl]Modifier.STATIC);
    }
}
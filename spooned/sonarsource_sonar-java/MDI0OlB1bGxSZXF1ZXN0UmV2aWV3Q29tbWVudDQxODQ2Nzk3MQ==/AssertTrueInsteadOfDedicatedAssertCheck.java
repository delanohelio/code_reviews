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
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.sonar.java.model.ExpressionUtils;
[CtUnresolvedImport]import org.sonar.plugins.java.api.semantic.Type;
[CtUnresolvedImport]import org.sonar.plugins.java.api.JavaFileScannerContext;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.ExpressionTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.MethodInvocationTree;
[CtUnresolvedImport]import static org.sonar.plugins.java.api.tree.Tree.Kind.NULL_LITERAL;
[CtUnresolvedImport]import org.sonar.java.checks.methods.AbstractMethodDetection;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.sonar.check.Rule;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.UnaryExpressionTree;
[CtUnresolvedImport]import org.sonar.plugins.java.api.semantic.MethodMatchers;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.IdentifierTree;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.Arguments;
[CtUnresolvedImport]import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
[CtClassImpl][CtAnnotationImpl]@org.sonar.check.Rule(key = [CtLiteralImpl]"S5785")
public class AssertTrueInsteadOfDedicatedAssertCheck extends [CtTypeReferenceImpl]org.sonar.java.checks.methods.AbstractMethodDetection {
    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.String[] ASSERT_METHOD_NAMES = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"assertTrue", [CtLiteralImpl]"assertFalse" };

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.String[] ASSERTION_CLASSES = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl][CtCommentImpl]// JUnit4
    "org.junit.Assert", [CtLiteralImpl]"junit.framework.TestCase", [CtLiteralImpl][CtCommentImpl]// JUnit4 (deprecated)
    "junit.framework.Assert", [CtLiteralImpl][CtCommentImpl]// JUnit5
    "org.junit.jupiter.api.Assertions" };

    [CtEnumImpl]private enum Assertion {

        [CtEnumValueImpl]NULL([CtLiteralImpl]"Null", [CtLiteralImpl]"A null-check"),
        [CtEnumValueImpl]NOT_NULL([CtLiteralImpl]"NotNull", [CtLiteralImpl]"A null-check"),
        [CtEnumValueImpl]SAME([CtLiteralImpl]"Same", [CtLiteralImpl]"An object reference comparison"),
        [CtEnumValueImpl]NOT_SAME([CtLiteralImpl]"NotSame", [CtLiteralImpl]"An object reference comparison"),
        [CtEnumValueImpl]EQUALS([CtLiteralImpl]"Equals", [CtLiteralImpl]"An equals check"),
        [CtEnumValueImpl]NOT_EQUALS([CtLiteralImpl]"NotEquals", [CtLiteralImpl]"An equals check");
        [CtFieldImpl]public final [CtTypeReferenceImpl]java.lang.String methodName;

        [CtFieldImpl]public final [CtTypeReferenceImpl]java.lang.String actionDescription;

        [CtConstructorImpl]Assertion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String namePostfix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionDescription) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]methodName = [CtBinaryOperatorImpl][CtLiteralImpl]"assert" + [CtVariableReadImpl]namePostfix;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.actionDescription = [CtVariableReadImpl]actionDescription;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]org.sonar.plugins.java.api.semantic.MethodMatchers getMethodInvocationMatchers() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sonar.plugins.java.api.semantic.MethodMatchers.create().ofTypes([CtFieldReadImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.ASSERTION_CLASSES).names([CtFieldReadImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.ASSERT_METHOD_NAMES).withAnyParameters().build();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onMethodInvocationFound([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.MethodInvocationTree mit) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasSemantic()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Arguments arguments = [CtInvocationImpl][CtVariableReadImpl]mit.arguments();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.ExpressionTree argumentExpression;
        [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.hasBooleanArgumentAtPosition([CtVariableReadImpl]arguments, [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]argumentExpression = [CtInvocationImpl][CtVariableReadImpl]arguments.get([CtLiteralImpl]0);
        } else [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.hasBooleanArgumentAtPosition([CtVariableReadImpl]arguments, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arguments.size() - [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]argumentExpression = [CtInvocationImpl][CtVariableReadImpl]arguments.get([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arguments.size() - [CtLiteralImpl]1);
        } else [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// We encountered a JUnit5 assert[True|False] method that accepts a BooleanSupplier - not supported.
            return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion> replacementAssertionOpt = [CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.getReplacementAssertion([CtVariableReadImpl]argumentExpression);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]replacementAssertionOpt.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.IdentifierTree problematicAssertionCallIdentifier = [CtInvocationImpl][CtTypeAccessImpl]org.sonar.java.model.ExpressionUtils.methodName([CtVariableReadImpl]mit);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]problematicAssertionCallIdentifier.name().equals([CtLiteralImpl]"assertFalse")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]replacementAssertionOpt = [CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.complement([CtInvocationImpl][CtVariableReadImpl]replacementAssertionOpt.get());
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]replacementAssertionOpt.isPresent()) [CtBlockImpl]{
                    [CtReturnImpl]return;
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion replacementAssertion = [CtInvocationImpl][CtVariableReadImpl]replacementAssertionOpt.get();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.JavaFileScannerContext.Location> secondaryLocation = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.JavaFileScannerContext.Location([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s is performed here, which is better expressed with %s.", [CtFieldReadImpl][CtVariableReadImpl]replacementAssertion.actionDescription, [CtFieldReadImpl][CtVariableReadImpl]replacementAssertion.methodName), [CtVariableReadImpl]argumentExpression));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Use %s instead.", [CtFieldReadImpl][CtVariableReadImpl]replacementAssertion.methodName);
            [CtInvocationImpl]reportIssue([CtVariableReadImpl]problematicAssertionCallIdentifier, [CtVariableReadImpl]message, [CtVariableReadImpl]secondaryLocation, [CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasBooleanArgumentAtPosition([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.Arguments arguments, [CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]arguments.size() > [CtVariableReadImpl]index) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]arguments.get([CtVariableReadImpl]index).symbolType().isPrimitive([CtTypeAccessImpl]Type.Primitives.BOOLEAN);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the assertX method that should be used instead of assertTrue, if applicable.
     *
     * @param argumentExpression
     * 		the boolean expression passed to assertTrue
     * @return the assertion method to be used instead of assertTrue, or {@code null} if no better assertion method was determined
     */
    private static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion> getReplacementAssertion([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.ExpressionTree argumentExpression) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]argumentExpression == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion assertion = [CtLiteralImpl]null;
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]argumentExpression.kind()) {
            [CtCaseImpl]case [CtFieldReadImpl]EQUAL_TO :
                [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.isCheckForNull([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree) (argumentExpression)))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NULL;
                } else [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.isPrimitiveComparison([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree) (argumentExpression)))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]EQUALS;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]SAME;
                }
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]NOT_EQUAL_TO :
                [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.isCheckForNull([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree) (argumentExpression)))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_NULL;
                } else [CtIfImpl]if ([CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.isPrimitiveComparison([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree) (argumentExpression)))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_EQUALS;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_SAME;
                }
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]METHOD_INVOCATION :
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sonar.java.model.ExpressionUtils.methodName([CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.MethodInvocationTree) (argumentExpression))).name().equals([CtLiteralImpl]"equals")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assertion = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]EQUALS;
                }
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]LOGICAL_COMPLEMENT :
                [CtReturnImpl]return [CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.complement([CtInvocationImpl][CtInvocationImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.getReplacementAssertion([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.UnaryExpressionTree) (argumentExpression)).expression()).orElse([CtLiteralImpl]null));
            [CtCaseImpl]default :
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]assertion);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isCheckForNull([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree bet) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bet.leftOperand().is([CtTypeAccessImpl]org.sonar.java.checks.NULL_LITERAL) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bet.rightOperand().is([CtTypeAccessImpl]org.sonar.java.checks.NULL_LITERAL);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isPrimitiveComparison([CtParameterImpl][CtTypeReferenceImpl]org.sonar.plugins.java.api.tree.BinaryExpressionTree bet) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bet.leftOperand().symbolType().isPrimitive() || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bet.rightOperand().symbolType().isPrimitive();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion> complement([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion assertion) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]assertion == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion complement = [CtLiteralImpl]null;
        [CtSwitchImpl]switch ([CtVariableReadImpl]assertion) {
            [CtCaseImpl]case NULL :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_NULL;
                [CtBreakImpl]break;
            [CtCaseImpl]case NOT_NULL :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NULL;
                [CtBreakImpl]break;
            [CtCaseImpl]case SAME :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_SAME;
                [CtBreakImpl]break;
            [CtCaseImpl]case NOT_SAME :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]SAME;
                [CtBreakImpl]break;
            [CtCaseImpl]case EQUALS :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]NOT_EQUALS;
                [CtBreakImpl]break;
            [CtCaseImpl]case NOT_EQUALS :
                [CtAssignmentImpl][CtVariableWriteImpl]complement = [CtFieldReadImpl][CtTypeAccessImpl]org.sonar.java.checks.AssertTrueInsteadOfDedicatedAssertCheck.Assertion.[CtFieldReferenceImpl]EQUALS;
                [CtBreakImpl]break;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]complement);
    }
}
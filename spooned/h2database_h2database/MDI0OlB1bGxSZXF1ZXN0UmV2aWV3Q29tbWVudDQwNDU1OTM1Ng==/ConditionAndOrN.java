[CompilationUnitImpl][CtCommentImpl]/* Copyright 2004-2020 H2 Group. Multiple-Licensed under the MPL 2.0, and the
EPL 1.0 (https://h2database.com/html/license.html). Initial Developer: H2
Group
 */
[CtPackageDeclarationImpl]package org.h2.expression.condition;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import org.h2.expression.ExpressionVisitor;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.h2.message.DbException;
[CtUnresolvedImport]import org.h2.table.TableFilter;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import org.h2.table.ColumnResolver;
[CtUnresolvedImport]import org.h2.engine.Session;
[CtUnresolvedImport]import org.h2.value.ValueNull;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.h2.value.ValueBoolean;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.h2.expression.Expression;
[CtUnresolvedImport]import org.h2.value.Value;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 * An 'and' or 'or' condition as in WHERE ID=1 AND NAME=? with N operands.
 * Mostly useful for optimisation and preventing stack overflow where generated
 * SQL has tons of conditions.
 */
public class ConditionAndOrN extends [CtTypeReferenceImpl]org.h2.expression.condition.Condition {
    [CtFieldImpl][CtJavaDocImpl]/**
     * The AND condition type as in ID=1 AND NAME='Hello'.
     */
    public static final [CtTypeReferenceImpl]int AND = [CtLiteralImpl]0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The OR condition type as in ID=1 OR NAME='Hello'.
     */
    public static final [CtTypeReferenceImpl]int OR = [CtLiteralImpl]1;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int andOrType;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Use an ArrayDeque because we primarily insert at the front.
     */
    private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.h2.expression.Expression> expressions;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Additional conditions for index only.
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.h2.expression.Expression> added;

    [CtConstructorImpl]public ConditionAndOrN([CtParameterImpl][CtTypeReferenceImpl]int andOrType, [CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression expr1, [CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression expr2, [CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression expr3) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.andOrType = [CtVariableReadImpl]andOrType;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.expressions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl][CtFieldReadImpl]expressions.add([CtVariableReadImpl]expr1);
        [CtInvocationImpl][CtFieldReadImpl]expressions.add([CtVariableReadImpl]expr2);
        [CtInvocationImpl][CtFieldReadImpl]expressions.add([CtVariableReadImpl]expr3);
    }

    [CtConstructorImpl]public ConditionAndOrN([CtParameterImpl][CtTypeReferenceImpl]int andOrType, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.h2.expression.Expression> expressions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.andOrType = [CtVariableReadImpl]andOrType;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.expressions = [CtVariableReadImpl]expressions;
    }

    [CtMethodImpl][CtTypeReferenceImpl]int getAndOrType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]andOrType;
    }

    [CtMethodImpl][CtTypeReferenceImpl]void addFirst([CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression e) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]expressions.add([CtLiteralImpl]0, [CtVariableReadImpl]e);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.StringBuilder getSQL([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder, [CtParameterImpl][CtTypeReferenceImpl]int sqlFlags) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]'(');
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.h2.expression.Expression> it = [CtInvocationImpl][CtFieldReadImpl]expressions.iterator();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().getSQL([CtVariableReadImpl]builder, [CtVariableReadImpl]sqlFlags);
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtFieldReadImpl]andOrType) {
                [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND :
                    [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\n    AND ");
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.OR :
                    [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"\n    OR ");
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.h2.message.DbException.throwInternalError([CtBinaryOperatorImpl][CtLiteralImpl]"andOrType=" + [CtFieldReadImpl]andOrType);
            }
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().getSQL([CtVariableReadImpl]builder, [CtVariableReadImpl]sqlFlags);
        } 
        [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]')');
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createIndexConditions([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.h2.table.TableFilter filter) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]andOrType == [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]e.createIndexConditions([CtVariableReadImpl]session, [CtVariableReadImpl]filter);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]added != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]added) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]e.createIndexConditions([CtVariableReadImpl]session, [CtVariableReadImpl]filter);
                }
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.h2.expression.Expression getNotIfPossible([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// (NOT (A OR B)): (NOT(A) AND NOT(B))
        [CtCommentImpl]// (NOT (A AND B)): (NOT(A) OR NOT(B))
        final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.h2.expression.Expression> newList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]expressions.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression l = [CtInvocationImpl][CtVariableReadImpl]e.getNotIfPossible([CtVariableReadImpl]session);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]l == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.h2.expression.condition.ConditionNot([CtVariableReadImpl]e);
            }
            [CtInvocationImpl][CtVariableReadImpl]newList.add([CtVariableReadImpl]l);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int reversed = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]andOrType == [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND) ? [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.OR : [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND;
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOrN([CtVariableReadImpl]reversed, [CtVariableReadImpl]newList);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.h2.value.Value getValue([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtFieldReadImpl]andOrType) {
            [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND :
                [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.value.Value v = [CtInvocationImpl][CtVariableReadImpl]e.getValue([CtVariableReadImpl]session);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtFieldReadImpl]org.h2.value.ValueNull.INSTANCE) [CtBlockImpl]{
                            [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.FALSE;
                        }
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]v.getBoolean()) [CtBlockImpl]{
                            [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.FALSE;
                        }
                    }
                    [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.TRUE;
                }
            [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.OR :
                [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.value.Value v = [CtInvocationImpl][CtVariableReadImpl]e.getValue([CtVariableReadImpl]session);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtFieldReadImpl]org.h2.value.ValueNull.INSTANCE) [CtBlockImpl]{
                            [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.FALSE;
                        }
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]v.getBoolean()) [CtBlockImpl]{
                            [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.TRUE;
                        }
                    }
                    [CtReturnImpl]return [CtFieldReadImpl]org.h2.value.ValueBoolean.FALSE;
                }
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.h2.message.DbException.throwInternalError([CtBinaryOperatorImpl][CtLiteralImpl]"type=" + [CtFieldReadImpl]andOrType);
        }
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Comparator<[CtTypeReferenceImpl]org.h2.expression.Expression> COMPARE_BY_COST = [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.Comparator<[CtTypeReferenceImpl]org.h2.expression.Expression>()[CtClassImpl] {
        [CtMethodImpl]public [CtTypeReferenceImpl]int compare([CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression lhs, [CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.Expression rhs) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]lhs.getCost() - [CtInvocationImpl][CtVariableReadImpl]rhs.getCost();
        }
    };

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.h2.expression.Expression optimize([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session) [CtBlockImpl]{
        [CtForImpl][CtCommentImpl]// NULL handling: see wikipedia,
        [CtCommentImpl]// http://www-cs-students.stanford.edu/~wlam/compsci/sqlnulls
        [CtCommentImpl]// first pass, optimize individual sub-expressions
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]expressions.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]expressions.set([CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]expressions.get([CtVariableReadImpl]i).optimize([CtVariableReadImpl]session));
        }
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtFieldReadImpl]expressions, [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.COMPARE_BY_COST);
        [CtInvocationImpl][CtCommentImpl]// TODO we're only matching pairs so that are next to each other, so in complex expressions
        [CtCommentImpl]// we will miss opportunities
        [CtCommentImpl]// second pass, optimize combinations
        optimizeMerge([CtLiteralImpl]0);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]expressions.size();) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression left = [CtInvocationImpl][CtFieldReadImpl]expressions.get([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression right = [CtInvocationImpl][CtFieldReadImpl]expressions.get([CtVariableReadImpl]i);
            [CtSwitchImpl]switch ([CtFieldReadImpl]andOrType) {
                [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND :
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getDatabase().getSettings().optimizeTwoEquals) [CtBlockImpl]{
                        [CtBreakImpl]break;
                    }
                    [CtIfImpl][CtCommentImpl]// this optimization does not work in the following case,
                    [CtCommentImpl]// but NOT is optimized before:
                    [CtCommentImpl]// CREATE TABLE TEST(A INT, B INT);
                    [CtCommentImpl]// INSERT INTO TEST VALUES(1, NULL);
                    [CtCommentImpl]// SELECT * FROM TEST WHERE NOT (B=A AND B=0); // no rows
                    [CtCommentImpl]// SELECT * FROM TEST WHERE NOT (B=A AND B=0 AND A=0); // 1,
                    [CtCommentImpl]// NULL
                    [CtCommentImpl]// try to add conditions (A=B AND B=1: add A=1)
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// try to add conditions (A=B AND B=1: add A=1)
                        [CtTypeReferenceImpl]org.h2.expression.Expression added = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (left)).getAdditionalAnd([CtVariableReadImpl]session, [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (right)));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]added != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.added == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.added = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                            }
                            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.added.add([CtInvocationImpl][CtVariableReadImpl]added.optimize([CtVariableReadImpl]session));
                        }
                    }
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.OR :
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getDatabase().getSettings().optimizeOr) [CtBlockImpl]{
                        [CtBreakImpl]break;
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression reduced;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (left)).optimizeOr([CtVariableReadImpl]session, [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (right)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionIn) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionIn) (left)).getAdditional([CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (right)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionIn) && [CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionIn) (right)).getAdditional([CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (left)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionInConstantSet) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionInConstantSet) (left)).getAdditional([CtVariableReadImpl]session, [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (right)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionInConstantSet) && [CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.Comparison)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionInConstantSet) (right)).getAdditional([CtVariableReadImpl]session, [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.Comparison) (left)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]reduced = [CtInvocationImpl][CtTypeAccessImpl]org.h2.expression.condition.ConditionAndOr.optimizeConditionAndOr([CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr) (left)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr) (right)));
                    } else [CtBlockImpl]{
                        [CtBreakImpl][CtCommentImpl]// TODO optimization: convert .. OR .. to UNION if the cost
                        [CtCommentImpl]// is lower
                        break;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]reduced != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]expressions.remove([CtVariableReadImpl]i);
                        [CtInvocationImpl][CtFieldReadImpl]expressions.set([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1, [CtVariableReadImpl]reduced);
                        [CtContinueImpl]continue;[CtCommentImpl]// because we don't want to increment, we want to compare the new pair exposed

                    }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e = [CtInvocationImpl][CtTypeAccessImpl]org.h2.expression.condition.ConditionAndOr.optimizeConstant([CtVariableReadImpl]session, [CtThisAccessImpl]this, [CtFieldReadImpl]andOrType, [CtVariableReadImpl]left, [CtVariableReadImpl]right);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e != [CtThisAccessImpl]this) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]expressions.remove([CtVariableReadImpl]i);
                [CtInvocationImpl][CtFieldReadImpl]expressions.set([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1, [CtVariableReadImpl]e);
                [CtContinueImpl]continue;[CtCommentImpl]// because we don't want to increment, we want to compare the new pair exposed

            }
            [CtIfImpl]if ([CtInvocationImpl]optimizeMerge([CtVariableReadImpl]i)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtUnaryOperatorImpl][CtVariableWriteImpl]i++;
        }
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtFieldReadImpl]expressions, [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.COMPARE_BY_COST);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]expressions.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]expressions.get([CtLiteralImpl]0);
        }
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean optimizeMerge([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e = [CtInvocationImpl][CtFieldReadImpl]expressions.get([CtVariableReadImpl]i);
        [CtIfImpl][CtCommentImpl]// If we have a ConditionAndOrN as a sub-expression, see if we can merge it
        [CtCommentImpl]// into this one.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOrN) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOrN rightCondition = [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOrN) (e));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.andOrType == [CtFieldReadImpl][CtVariableReadImpl]rightCondition.andOrType) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]expressions.remove([CtVariableReadImpl]i);
                [CtInvocationImpl][CtFieldReadImpl]expressions.addAll([CtVariableReadImpl]i, [CtFieldReadImpl][CtVariableReadImpl]rightCondition.expressions);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr rightCondition = [CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.expression.condition.ConditionAndOr) (e));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.andOrType == [CtInvocationImpl][CtVariableReadImpl]rightCondition.getAndOrType()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]expressions.set([CtVariableReadImpl]i, [CtInvocationImpl][CtVariableReadImpl]rightCondition.getSubexpression([CtLiteralImpl]0));
                [CtInvocationImpl][CtFieldReadImpl]expressions.add([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]rightCondition.getSubexpression([CtLiteralImpl]1));
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addFilterConditions([CtParameterImpl][CtTypeReferenceImpl]org.h2.table.TableFilter filter) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]andOrType == [CtFieldReadImpl]org.h2.expression.condition.ConditionAndOrN.AND) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]e.addFilterConditions([CtVariableReadImpl]filter);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.addFilterConditions([CtVariableReadImpl]filter);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void mapColumns([CtParameterImpl][CtTypeReferenceImpl]org.h2.table.ColumnResolver resolver, [CtParameterImpl][CtTypeReferenceImpl]int level, [CtParameterImpl][CtTypeReferenceImpl]int state) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.mapColumns([CtVariableReadImpl]resolver, [CtVariableReadImpl]level, [CtVariableReadImpl]state);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setEvaluatable([CtParameterImpl][CtTypeReferenceImpl]org.h2.table.TableFilter tableFilter, [CtParameterImpl][CtTypeReferenceImpl]boolean b) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.setEvaluatable([CtVariableReadImpl]tableFilter, [CtVariableReadImpl]b);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateAggregate([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session, [CtParameterImpl][CtTypeReferenceImpl]int stage) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.updateAggregate([CtVariableReadImpl]session, [CtVariableReadImpl]stage);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isEverything([CtParameterImpl][CtTypeReferenceImpl]org.h2.expression.ExpressionVisitor visitor) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]e.isEverything([CtVariableReadImpl]visitor)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getCost() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int cost = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.Expression e : [CtFieldReadImpl]expressions) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]cost += [CtInvocationImpl][CtVariableReadImpl]e.getCost();
        }
        [CtReturnImpl]return [CtVariableReadImpl]cost;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getSubexpressionCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]expressions.size();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.h2.expression.Expression getSubexpression([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]expressions.get([CtVariableReadImpl]index);
    }
}
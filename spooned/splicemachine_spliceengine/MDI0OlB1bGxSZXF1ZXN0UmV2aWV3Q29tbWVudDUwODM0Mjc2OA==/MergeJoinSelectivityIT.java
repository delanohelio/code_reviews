[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2012 - 2020 Splice Machine, Inc.

This file is part of Splice Machine.
Splice Machine is free software: you can redistribute it and/or modify it under the terms of the
GNU Affero General Public License as published by the Free Software Foundation, either
version 3, or (at your option) any later version.
Splice Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with Splice Machine.
If not, see <http://www.gnu.org/licenses/>.
 */
[CtPackageDeclarationImpl]package com.splicemachine.db.impl.sql.compile;
[CtUnresolvedImport]import com.splicemachine.derby.test.framework.SpliceWatcher;
[CtUnresolvedImport]import org.junit.rules.RuleChain;
[CtUnresolvedImport]import org.junit.rules.TestRule;
[CtUnresolvedImport]import org.junit.*;
[CtImportImpl]import java.sql.Statement;
[CtUnresolvedImport]import com.splicemachine.derby.test.framework.SpliceSchemaWatcher;
[CtImportImpl]import java.sql.ResultSet;
[CtClassImpl][CtJavaDocImpl]/**
 */
public class MergeJoinSelectivityIT extends [CtTypeReferenceImpl]BaseJoinSelectivityIT {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String CLASS_NAME = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.class.getSimpleName().toUpperCase();

    [CtFieldImpl]protected static [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceWatcher spliceClassWatcher = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceWatcher([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.CLASS_NAME);

    [CtFieldImpl]protected static [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceSchemaWatcher spliceSchemaWatcher = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceSchemaWatcher([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.CLASS_NAME);

    [CtFieldImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.ClassRule
    public static [CtTypeReferenceImpl]org.junit.rules.TestRule chain = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.junit.rules.RuleChain.outerRule([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.spliceClassWatcher).around([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.spliceSchemaWatcher);

    [CtFieldImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Rule
    public [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceWatcher methodWatcher = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.splicemachine.derby.test.framework.SpliceWatcher([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.CLASS_NAME);

    [CtMethodImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.BeforeClass
    public static [CtTypeReferenceImpl]void createDataSet() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]createJoinDataSet([CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.spliceClassWatcher, [CtInvocationImpl][CtFieldReadImpl]com.splicemachine.db.impl.sql.compile.MergeJoinSelectivityIT.spliceSchemaWatcher.toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Test
    public [CtTypeReferenceImpl]void innerJoin() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]3 }, [CtLiteralImpl]"explain select * from --splice-properties joinOrder=fixed\n ts_10_spk, ts_5_spk --splice-properties joinStrategy=MERGE\n where ts_10_spk.c1 = ts_5_spk.c1", [CtLiteralImpl]"rows=10", [CtLiteralImpl]"MergeJoin");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]3 }, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain select upper(ts_10_spk.c2), upper(ts_5_spk.c2) from --splice-properties joinOrder=fixed\n " + [CtLiteralImpl]"ts_10_spk --splice-properties index=ts_10_spk_expr_idx\n, ") + [CtLiteralImpl]"ts_5_spk --splice-properties index=ts_5_spk_expr_idx, joinStrategy=MERGE\n ") + [CtLiteralImpl]"where upper(ts_10_spk.c2) = upper(ts_5_spk.c2)", [CtLiteralImpl]"rows=10", [CtLiteralImpl]"MergeJoin");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Test
    public [CtTypeReferenceImpl]void leftOuterJoin() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]3 }, [CtLiteralImpl]"explain select * from --splice-properties joinOrder=fixed\n ts_10_spk left outer join ts_5_spk --splice-properties joinStrategy=MERGE\n on ts_10_spk.c1 = ts_5_spk.c1", [CtLiteralImpl]"rows=10", [CtLiteralImpl]"MergeLeftOuterJoin");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]3 }, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain select upper(ts_10_spk.c2), upper(ts_5_spk.c2) from --splice-properties joinOrder=fixed\n " + [CtLiteralImpl]"ts_10_spk --splice-properties index=ts_10_spk_expr_idx\n") + [CtLiteralImpl]"left outer join ") + [CtLiteralImpl]"ts_5_spk --splice-properties index=ts_5_spk_expr_idx, joinStrategy=MERGE\n ") + [CtLiteralImpl]"on upper(ts_10_spk.c2) = upper(ts_5_spk.c2)", [CtLiteralImpl]"rows=10", [CtLiteralImpl]"MergeLeftOuterJoin");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Test
    public [CtTypeReferenceImpl]void rightOuterJoin() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]4 }, [CtLiteralImpl]"explain select * from ts_10_spk --splice-properties joinStrategy=MERGE\n right outer join ts_5_spk on ts_10_spk.c1 = ts_5_spk.c1", [CtLiteralImpl]"rows=5", [CtLiteralImpl]"MergeLeftOuterJoin");
        }
        [CtTryWithResourceImpl][CtCommentImpl]// We assert output rows=6 instead of 5 here. Reason is from statistics, max value of upper(ts_10_spk.c2)
        [CtCommentImpl]// returns "9", not "10". Why is that? Becasuse c2 is a varchar column, not integer. "10" sorts before "9"!
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]4 }, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain select upper(ts_10_spk.c2), upper(ts_5_spk.c2) from " + [CtLiteralImpl]"ts_10_spk --splice-properties index=ts_10_spk_expr_idx, joinStrategy=MERGE\n ") + [CtLiteralImpl]"right outer join ") + [CtLiteralImpl]"ts_5_spk --splice-properties index=ts_5_spk_expr_idx\n ") + [CtLiteralImpl]"on upper(ts_10_spk.c2) = upper(ts_5_spk.c2)", [CtLiteralImpl]"rows=6", [CtLiteralImpl]"MergeLeftOuterJoin");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Test
    public [CtTypeReferenceImpl]void testInnerJoinWithStartEndKey() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl]rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]3 }, [CtLiteralImpl]"explain select * from --splice-properties joinOrder=fixed\n ts_3_spk, ts_10_spk --splice-properties joinStrategy=MERGE\n where ts_10_spk.c1 = ts_3_spk.c1", [CtLiteralImpl]"rows=3", [CtLiteralImpl]"MergeJoin");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement s = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// need one extra ProjectRestrict on top of join because we select two index expressions only
            rowContainsQuery([CtVariableReadImpl]s, [CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtLiteralImpl]1, [CtLiteralImpl]4 }, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain select upper(ts_10_spk.c2), upper(ts_3_spk.c2) from --splice-properties joinOrder=fixed\n " + [CtLiteralImpl]"ts_3_spk --splice-properties index=ts_3_spk_expr_idx\n, ") + [CtLiteralImpl]"ts_10_spk --splice-properties index=ts_10_spk_expr_idx, joinStrategy=MERGE\n ") + [CtLiteralImpl]"where upper(ts_10_spk.c2) = upper(ts_3_spk.c2)", [CtLiteralImpl]"rows=3", [CtLiteralImpl]"MergeJoin");
        }
    }

    [CtMethodImpl][CtCommentImpl]// DB-4106: make sure for merge join, plan has a lower cost if outer table is empty
    [CtAnnotationImpl]@com.splicemachine.db.impl.sql.compile.Test
    public [CtTypeReferenceImpl]void testEmptyInputTable() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement statement = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]methodWatcher.getOrCreateConnection().createStatement()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String query = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain \n" + [CtLiteralImpl]"select * from --SPLICE-PROPERTIES joinOrder=FIXED\n") + [CtLiteralImpl]"t1 --SPLICE-PROPERTIES index=t1i\n") + [CtLiteralImpl]", t2--SPLICE-PROPERTIES index=t2j,joinStrategy=MERGE\n") + [CtLiteralImpl]"where i=j";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtLiteralImpl]null;
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]statement.executeQuery([CtVariableReadImpl]query)) [CtBlockImpl]{
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]rs.next()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.contains([CtLiteralImpl]"MergeJoin"))[CtBlockImpl]
                        [CtBreakImpl]break;

                } 
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]double cost1 = [CtInvocationImpl]getTotalCost([CtVariableReadImpl]s);
            [CtAssignmentImpl][CtVariableWriteImpl]query = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"explain \n" + [CtLiteralImpl]"select * from --SPLICE-PROPERTIES joinOrder=FIXED\n") + [CtLiteralImpl]"t2 --SPLICE-PROPERTIES index=t2j\n") + [CtLiteralImpl]", t1--SPLICE-PROPERTIES index=t1i, joinStrategy=MERGE\n") + [CtLiteralImpl]"where i=j";
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]statement.executeQuery([CtVariableReadImpl]query)) [CtBlockImpl]{
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]rs.next()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.contains([CtLiteralImpl]"MergeJoin"))[CtBlockImpl]
                        [CtBreakImpl]break;

                } 
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]double cost2 = [CtInvocationImpl]getTotalCost([CtVariableReadImpl]s);
            [CtInvocationImpl][CtTypeAccessImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtVariableReadImpl]cost1 < [CtVariableReadImpl]cost2);
        }
    }
}
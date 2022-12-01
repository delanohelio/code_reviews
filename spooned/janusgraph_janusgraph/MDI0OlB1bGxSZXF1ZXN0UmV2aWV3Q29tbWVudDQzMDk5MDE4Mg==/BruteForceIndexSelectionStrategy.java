[CompilationUnitImpl][CtPackageDeclarationImpl]package org.janusgraph.graphdb.query.index;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.janusgraph.graphdb.query.condition.MultiCondition;
[CtUnresolvedImport]import org.janusgraph.util.datastructures.PowerSet;
[CtUnresolvedImport]import org.janusgraph.graphdb.query.graph.JointIndexQuery;
[CtUnresolvedImport]import org.janusgraph.graphdb.query.condition.Condition;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.janusgraph.graphdb.database.IndexSerializer;
[CtUnresolvedImport]import org.javatuples.Pair;
[CtUnresolvedImport]import org.janusgraph.graphdb.internal.OrderList;
[CtUnresolvedImport]import org.janusgraph.graphdb.types.MixedIndexType;
[CtUnresolvedImport]import org.janusgraph.graphdb.types.IndexType;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.janusgraph.core.JanusGraphElement;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Florian Grieskamp (Florian.Grieskamp@gdata.de)
 */
public class BruteForceIndexSelectionStrategy extends [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.AbstractIndexSelectionStrategy implements [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexSelectionStrategy {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Determine the best jointIndexQuery by enumerating all possibilities with exponential time
     * complexity. Similar to Weighted Set Cover problem, to find the best choice is NP-Complete, so
     * we should be careful that the problem size MUST be small, otherwise it is more recommended to
     * use an approximation algorithm.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.SelectedIndexQuery selectIndices([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.janusgraph.graphdb.types.IndexType> rawCandidates, [CtParameterImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.query.condition.MultiCondition<[CtTypeReferenceImpl]org.janusgraph.core.JanusGraphElement> conditions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.janusgraph.graphdb.query.condition.Condition> coveredClauses, [CtParameterImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.internal.OrderList orders, [CtParameterImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.database.IndexSerializer serializer) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.query.graph.JointIndexQuery jointQuery = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.query.graph.JointIndexQuery();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidate> indexCandidates = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isSorted = [CtInvocationImpl][CtVariableReadImpl]orders.isEmpty();
        [CtForEachImpl][CtCommentImpl]// validate, enrich index candidates and calculate scores
        for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.types.IndexType index : [CtVariableReadImpl]rawCandidates) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidate ic = [CtInvocationImpl]createIndexCandidate([CtVariableReadImpl]index, [CtVariableReadImpl]conditions, [CtVariableReadImpl]serializer);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ic == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtInvocationImpl][CtVariableReadImpl]ic.calculateScoreBruteForce();
            [CtInvocationImpl][CtVariableReadImpl]indexCandidates.add([CtVariableReadImpl]ic);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidateGroup bestGroup = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidate> subset : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.util.datastructures.PowerSet<>([CtVariableReadImpl]indexCandidates)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]subset.isEmpty())[CtBlockImpl]
                [CtContinueImpl]continue;

            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidateGroup group = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidateGroup([CtVariableReadImpl]subset);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]group.compareTo([CtVariableReadImpl]bestGroup) > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bestGroup = [CtVariableReadImpl]group;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bestGroup != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]coveredClauses.addAll([CtInvocationImpl][CtVariableReadImpl]bestGroup.getCoveredClauses());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidate> bestIndexes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]bestGroup.getIndexCandidates());
            [CtInvocationImpl][CtCommentImpl]// sort indexes by score descending order
            [CtVariableReadImpl]bestIndexes.sort([CtLambdaImpl]([CtParameterImpl] a,[CtParameterImpl] b) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.compare([CtInvocationImpl][CtVariableReadImpl]b.getScore(), [CtInvocationImpl][CtVariableReadImpl]a.getScore()));
            [CtAssignmentImpl][CtCommentImpl]// isSorted depends on the first index subquery
            [CtVariableWriteImpl]isSorted = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]orders.isEmpty() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bestIndexes.get([CtLiteralImpl]0).getIndex().isMixedIndex() && [CtInvocationImpl][CtTypeAccessImpl]org.janusgraph.graphdb.query.index.IndexSelectionStrategy.indexCoversOrder([CtInvocationImpl](([CtTypeReferenceImpl]org.janusgraph.graphdb.types.MixedIndexType) ([CtInvocationImpl][CtVariableReadImpl]bestIndexes.get([CtLiteralImpl]0).getIndex())), [CtVariableReadImpl]orders));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.IndexCandidate c : [CtVariableReadImpl]bestIndexes) [CtBlockImpl]{
                [CtInvocationImpl]addToJointQuery([CtVariableReadImpl]c, [CtVariableReadImpl]jointQuery, [CtVariableReadImpl]serializer, [CtVariableReadImpl]orders);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.janusgraph.graphdb.query.index.SelectedIndexQuery([CtVariableReadImpl]jointQuery, [CtVariableReadImpl]isSorted);
    }
}
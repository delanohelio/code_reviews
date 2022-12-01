[CompilationUnitImpl][CtCommentImpl]/* Copyright 2017 HugeGraph Authors

Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements. See the NOTICE file distributed with this
work for additional information regarding copyright ownership. The ASF
licenses this file to You under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package com.baidu.hugegraph.traversal.algorithm;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.baidu.hugegraph.type.define.Frequency;
[CtUnresolvedImport]import com.baidu.hugegraph.structure.HugeEdge;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtUnresolvedImport]import com.baidu.hugegraph.util.InsertionOrderUtil;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.structure.Vertex;
[CtUnresolvedImport]import com.baidu.hugegraph.structure.HugeVertex;
[CtUnresolvedImport]import javax.ws.rs.core.MultivaluedHashMap;
[CtUnresolvedImport]import javax.ws.rs.core.MultivaluedMap;
[CtImportImpl]import org.apache.commons.lang3.mutable.MutableInt;
[CtUnresolvedImport]import com.baidu.hugegraph.type.define.Directions;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import com.baidu.hugegraph.HugeGraph;
[CtUnresolvedImport]import com.baidu.hugegraph.backend.id.Id;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import com.baidu.hugegraph.schema.EdgeLabel;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;
[CtUnresolvedImport]import com.baidu.hugegraph.util.E;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.structure.Edge;
[CtClassImpl]public class FusiformSimilarityTraverser extends [CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.HugeTraverser {
    [CtFieldImpl]private [CtTypeReferenceImpl]long accessed = [CtLiteralImpl]0L;

    [CtConstructorImpl]public FusiformSimilarityTraverser([CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.HugeGraph graph) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]graph);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.SimilarsMap fusiformSimilarity([CtParameterImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Vertex> vertices, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.type.define.Directions direction, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.schema.EdgeLabel label, [CtParameterImpl][CtTypeReferenceImpl]int minNeighbors, [CtParameterImpl][CtTypeReferenceImpl]double alpha, [CtParameterImpl][CtTypeReferenceImpl]int minSimilars, [CtParameterImpl][CtTypeReferenceImpl]int top, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupProperty, [CtParameterImpl][CtTypeReferenceImpl]int minGroups, [CtParameterImpl][CtTypeReferenceImpl]long degree, [CtParameterImpl][CtTypeReferenceImpl]long capacity, [CtParameterImpl][CtTypeReferenceImpl]long limit, [CtParameterImpl][CtTypeReferenceImpl]boolean withIntermediary) [CtBlockImpl]{
        [CtInvocationImpl]checkCapacity([CtVariableReadImpl]capacity);
        [CtInvocationImpl]checkLimit([CtVariableReadImpl]limit);
        [CtInvocationImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.checkGroupArgs([CtVariableReadImpl]groupProperty, [CtVariableReadImpl]minGroups);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int foundCount = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.SimilarsMap results = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.SimilarsMap();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]vertices.hasNext()) [CtBlockImpl]{
            [CtInvocationImpl]checkCapacity([CtVariableReadImpl]capacity, [CtUnaryOperatorImpl]++[CtFieldWriteImpl][CtThisAccessImpl]this.accessed, [CtLiteralImpl]"fusiform similarity");
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeVertex vertex = [CtInvocationImpl](([CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeVertex) ([CtVariableReadImpl]vertices.next()));
            [CtLocalVariableImpl][CtCommentImpl]// Find fusiform similarity for current vertex
            [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar> result = [CtInvocationImpl][CtThisAccessImpl]this.fusiformSimilarityForVertex([CtVariableReadImpl]vertex, [CtVariableReadImpl]direction, [CtVariableReadImpl]label, [CtVariableReadImpl]minNeighbors, [CtVariableReadImpl]alpha, [CtVariableReadImpl]minSimilars, [CtVariableReadImpl]top, [CtVariableReadImpl]groupProperty, [CtVariableReadImpl]minGroups, [CtVariableReadImpl]degree, [CtVariableReadImpl]capacity, [CtVariableReadImpl]withIntermediary);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]result.isEmpty()) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtInvocationImpl][CtVariableReadImpl]results.put([CtInvocationImpl][CtVariableReadImpl]vertex.id(), [CtVariableReadImpl]result);
            [CtIfImpl][CtCommentImpl]// Reach limit
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]limit != [CtFieldReadImpl]NO_LIMIT) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](++[CtVariableWriteImpl]foundCount) >= [CtVariableReadImpl]limit)) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
        } 
        [CtInvocationImpl][CtThisAccessImpl]this.reset();
        [CtReturnImpl]return [CtVariableReadImpl]results;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar> fusiformSimilarityForVertex([CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeVertex vertex, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.type.define.Directions direction, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.schema.EdgeLabel label, [CtParameterImpl][CtTypeReferenceImpl]int minNeighbors, [CtParameterImpl][CtTypeReferenceImpl]double alpha, [CtParameterImpl][CtTypeReferenceImpl]int minSimilars, [CtParameterImpl][CtTypeReferenceImpl]int top, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupProperty, [CtParameterImpl][CtTypeReferenceImpl]int minGroups, [CtParameterImpl][CtTypeReferenceImpl]long degree, [CtParameterImpl][CtTypeReferenceImpl]long capacity, [CtParameterImpl][CtTypeReferenceImpl]boolean withIntermediary) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean matched = [CtInvocationImpl][CtThisAccessImpl]this.matchMinNeighborCount([CtVariableReadImpl]vertex, [CtVariableReadImpl]direction, [CtVariableReadImpl]label, [CtVariableReadImpl]minNeighbors, [CtVariableReadImpl]degree);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]matched) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Ignore current vertex if its neighbors number is not enough
            return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id labelId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]label == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]label.id();
        [CtLocalVariableImpl][CtCommentImpl]// Get similar nodes and counts
        [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Edge> edges = [CtInvocationImpl][CtThisAccessImpl]this.edgesOfVertex([CtInvocationImpl][CtVariableReadImpl]vertex.id(), [CtVariableReadImpl]direction, [CtVariableReadImpl]labelId, [CtVariableReadImpl]degree);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]org.apache.commons.lang3.mutable.MutableInt> similars = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.ws.rs.core.MultivaluedMap<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> intermediaries = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.core.MultivaluedHashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> neighbors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]edges.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id target = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeEdge) ([CtVariableReadImpl]edges.next())).id().otherVertexId();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]neighbors.contains([CtVariableReadImpl]target)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtInvocationImpl][CtVariableReadImpl]neighbors.add([CtVariableReadImpl]target);
            [CtInvocationImpl]checkCapacity([CtVariableReadImpl]capacity, [CtUnaryOperatorImpl]++[CtFieldWriteImpl][CtThisAccessImpl]this.accessed, [CtLiteralImpl]"fusiform similarity");
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.type.define.Directions backDir = [CtInvocationImpl][CtVariableReadImpl]direction.opposite();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Edge> backEdges = [CtInvocationImpl][CtThisAccessImpl]this.edgesOfVertex([CtVariableReadImpl]target, [CtVariableReadImpl]backDir, [CtVariableReadImpl]labelId, [CtVariableReadImpl]degree);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> currentSimilars = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]backEdges.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id node = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeEdge) ([CtVariableReadImpl]backEdges.next())).id().otherVertexId();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currentSimilars.contains([CtVariableReadImpl]node)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtInvocationImpl][CtVariableReadImpl]currentSimilars.add([CtVariableReadImpl]node);
                [CtIfImpl]if ([CtVariableReadImpl]withIntermediary) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]intermediaries.add([CtVariableReadImpl]node, [CtVariableReadImpl]target);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.lang3.mutable.MutableInt count = [CtInvocationImpl][CtVariableReadImpl]similars.get([CtVariableReadImpl]node);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]count = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.lang3.mutable.MutableInt([CtLiteralImpl]0);
                    [CtInvocationImpl][CtVariableReadImpl]similars.put([CtVariableReadImpl]node, [CtVariableReadImpl]count);
                    [CtInvocationImpl]checkCapacity([CtVariableReadImpl]capacity, [CtUnaryOperatorImpl]++[CtFieldWriteImpl][CtThisAccessImpl]this.accessed, [CtLiteralImpl]"fusiform similarity");
                }
                [CtInvocationImpl][CtVariableReadImpl]count.increment();
            } 
        } 
        [CtAssertImpl][CtCommentImpl]// Delete source vertex
        assert [CtInvocationImpl][CtVariableReadImpl]similars.containsKey([CtInvocationImpl][CtVariableReadImpl]vertex.id());
        [CtInvocationImpl][CtVariableReadImpl]similars.remove([CtInvocationImpl][CtVariableReadImpl]vertex.id());
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]similars.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of();
        }
        [CtLocalVariableImpl][CtCommentImpl]// Match alpha
        [CtTypeReferenceImpl]double neighborNum = [CtInvocationImpl][CtVariableReadImpl]neighbors.size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.lang.Double> matchedAlpha = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]org.apache.commons.lang3.mutable.MutableInt> entry : [CtInvocationImpl][CtVariableReadImpl]similars.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]double score = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().intValue() / [CtVariableReadImpl]neighborNum;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]score >= [CtVariableReadImpl]alpha) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]matchedAlpha.put([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtVariableReadImpl]score);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]matchedAlpha.size() < [CtVariableReadImpl]minSimilars) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of();
        }
        [CtLocalVariableImpl][CtCommentImpl]// Sorted and topN if needed
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.lang.Double> topN;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]top > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]topN = [CtInvocationImpl]topN([CtVariableReadImpl]matchedAlpha, [CtLiteralImpl]true, [CtVariableReadImpl]top);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]topN = [CtVariableReadImpl]matchedAlpha;
        }
        [CtIfImpl][CtCommentImpl]// Filter by groupCount by property
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]groupProperty != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Object> values = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtInvocationImpl][CtCommentImpl]// Add groupProperty value of source vertex
            [CtVariableReadImpl]values.add([CtInvocationImpl][CtVariableReadImpl]vertex.value([CtVariableReadImpl]groupProperty));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id id : [CtInvocationImpl][CtVariableReadImpl]topN.keySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Vertex v = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]graph().vertices([CtVariableReadImpl]id).next();
                [CtInvocationImpl][CtVariableReadImpl]values.add([CtInvocationImpl][CtVariableReadImpl]v.value([CtVariableReadImpl]groupProperty));
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]values.size() < [CtVariableReadImpl]minGroups) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.of();
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Construct result
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar> result = [CtInvocationImpl][CtTypeAccessImpl]com.baidu.hugegraph.util.InsertionOrderUtil.newSet();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.lang.Double> entry : [CtInvocationImpl][CtVariableReadImpl]topN.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id similar = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double score = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> inters = [CtConditionalImpl]([CtVariableReadImpl]withIntermediary) ? [CtInvocationImpl][CtVariableReadImpl]intermediaries.get([CtVariableReadImpl]similar) : [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of();
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar([CtVariableReadImpl]similar, [CtVariableReadImpl]score, [CtVariableReadImpl]inters));
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void checkGroupArgs([CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupProperty, [CtParameterImpl][CtTypeReferenceImpl]int minGroups) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]groupProperty == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.baidu.hugegraph.util.E.checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]minGroups == [CtLiteralImpl]0, [CtBinaryOperatorImpl][CtLiteralImpl]"Can not set min group count when " + [CtLiteralImpl]"group property not set");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.baidu.hugegraph.util.E.checkArgument([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]groupProperty.isEmpty(), [CtLiteralImpl]"The group property can't be empty");
            [CtInvocationImpl][CtTypeAccessImpl]com.baidu.hugegraph.util.E.checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]minGroups > [CtLiteralImpl]0, [CtBinaryOperatorImpl][CtLiteralImpl]"Must set min group count when " + [CtLiteralImpl]"group property set");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean matchMinNeighborCount([CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeVertex vertex, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.type.define.Directions direction, [CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.schema.EdgeLabel edgeLabel, [CtParameterImpl][CtTypeReferenceImpl]int minNeighbors, [CtParameterImpl][CtTypeReferenceImpl]long degree) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Edge> edges;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long neighborCount;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id labelId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]edgeLabel == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]edgeLabel.id();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]edgeLabel != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]edgeLabel.frequency() == [CtFieldReadImpl]com.baidu.hugegraph.type.define.Frequency.SINGLE)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]edges = [CtInvocationImpl][CtThisAccessImpl]this.edgesOfVertex([CtInvocationImpl][CtVariableReadImpl]vertex.id(), [CtVariableReadImpl]direction, [CtVariableReadImpl]labelId, [CtVariableReadImpl]minNeighbors);
            [CtAssignmentImpl][CtVariableWriteImpl]neighborCount = [CtInvocationImpl][CtTypeAccessImpl]org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils.count([CtVariableReadImpl]edges);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]edges = [CtInvocationImpl][CtThisAccessImpl]this.edgesOfVertex([CtInvocationImpl][CtVariableReadImpl]vertex.id(), [CtVariableReadImpl]direction, [CtVariableReadImpl]labelId, [CtVariableReadImpl]degree);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> neighbors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]edges.hasNext()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id target = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]com.baidu.hugegraph.structure.HugeEdge) ([CtVariableReadImpl]edges.next())).id().otherVertexId();
                [CtInvocationImpl][CtVariableReadImpl]neighbors.add([CtVariableReadImpl]target);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]neighbors.size() >= [CtVariableReadImpl]minNeighbors) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
            } 
            [CtAssignmentImpl][CtVariableWriteImpl]neighborCount = [CtInvocationImpl][CtVariableReadImpl]neighbors.size();
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]neighborCount >= [CtVariableReadImpl]minNeighbors;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void reset() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.accessed = [CtLiteralImpl]0L;
    }

    [CtClassImpl]public static class Similar {
        [CtFieldImpl]private final [CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id id;

        [CtFieldImpl]private final [CtTypeReferenceImpl]double score;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> intermediaries;

        [CtConstructorImpl]public Similar([CtParameterImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id id, [CtParameterImpl][CtTypeReferenceImpl]double score, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> intermediaries) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.score = [CtVariableReadImpl]score;
            [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtVariableReadImpl]intermediaries).size() == [CtInvocationImpl][CtVariableReadImpl]intermediaries.size() : [CtLiteralImpl]"Invalid intermediaries";
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.intermediaries = [CtVariableReadImpl]intermediaries;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id id() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.id;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]double score() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.score;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> intermediaries() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.intermediaries;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> toMap() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of([CtLiteralImpl]"id", [CtFieldReadImpl][CtThisAccessImpl]this.id, [CtLiteralImpl]"score", [CtFieldReadImpl][CtThisAccessImpl]this.score, [CtLiteralImpl]"intermediaries", [CtFieldReadImpl][CtThisAccessImpl]this.intermediaries);
        }
    }

    [CtClassImpl]public static class SimilarsMap extends [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar>> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]1906770930513268291L;

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> vertices() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id> vertices = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtInvocationImpl][CtVariableReadImpl]vertices.addAll([CtInvocationImpl][CtThisAccessImpl]this.keySet());
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar> similars : [CtInvocationImpl][CtThisAccessImpl]this.values()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar similar : [CtVariableReadImpl]similars) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]vertices.add([CtInvocationImpl][CtVariableReadImpl]similar.id());
                    [CtInvocationImpl][CtVariableReadImpl]vertices.addAll([CtInvocationImpl][CtVariableReadImpl]similar.intermediaries());
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]vertices;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>>> toMap() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>>> results = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar>> entry : [CtInvocationImpl][CtThisAccessImpl]this.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.backend.id.Id source = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar> similars = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> result = [CtInvocationImpl][CtTypeAccessImpl]com.baidu.hugegraph.util.InsertionOrderUtil.newSet();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.baidu.hugegraph.traversal.algorithm.FusiformSimilarityTraverser.Similar similar : [CtVariableReadImpl]similars) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]result.add([CtInvocationImpl][CtVariableReadImpl]similar.toMap());
                }
                [CtInvocationImpl][CtVariableReadImpl]results.put([CtVariableReadImpl]source, [CtVariableReadImpl]result);
            }
            [CtReturnImpl]return [CtVariableReadImpl]results;
        }
    }
}
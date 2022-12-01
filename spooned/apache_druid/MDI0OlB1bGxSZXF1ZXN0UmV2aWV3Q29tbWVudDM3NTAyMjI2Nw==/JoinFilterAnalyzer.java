[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.druid.segment.join;
[CtUnresolvedImport]import org.apache.druid.segment.join.lookup.LookupColumnSelectorFactory;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.druid.segment.join.lookup.LookupJoinable;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.druid.segment.ColumnValueSelector;
[CtUnresolvedImport]import org.apache.druid.segment.ColumnSelectorFactory;
[CtUnresolvedImport]import org.apache.druid.segment.NilColumnValueSelector;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import org.apache.druid.math.expr.Expr;
[CtUnresolvedImport]import org.apache.druid.segment.DimensionSelector;
[CtUnresolvedImport]import org.apache.druid.segment.filter.OrFilter;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.druid.segment.virtual.ExpressionVirtualColumn;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.druid.query.filter.ValueMatcher;
[CtUnresolvedImport]import org.apache.druid.segment.filter.SelectorFilter;
[CtUnresolvedImport]import org.apache.druid.segment.join.table.IndexedTable;
[CtUnresolvedImport]import org.apache.druid.segment.join.table.IndexedTableJoinable;
[CtUnresolvedImport]import org.apache.druid.segment.VirtualColumn;
[CtUnresolvedImport]import org.apache.druid.segment.filter.AndFilter;
[CtUnresolvedImport]import org.apache.druid.segment.column.ValueType;
[CtUnresolvedImport]import it.unimi.dsi.fastutil.ints.IntList;
[CtUnresolvedImport]import org.apache.druid.query.dimension.DimensionSpec;
[CtUnresolvedImport]import org.apache.druid.segment.filter.InFilter;
[CtUnresolvedImport]import org.apache.druid.segment.column.ColumnCapabilities;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.apache.druid.query.filter.InDimFilter;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.druid.segment.filter.Filters;
[CtUnresolvedImport]import org.apache.druid.query.filter.Filter;
[CtClassImpl]public class JoinFilterAnalyzer {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PUSH_DOWN_VIRTUAL_COLUMN_NAME_BASE = [CtLiteralImpl]"JOIN-FILTER-PUSHDOWN-VIRTUAL-COLUMN-";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.druid.segment.ColumnSelectorFactory ALL_NULL_COLUMN_SELECTOR_FACTORY = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.AllNullColumnSelectorFactory();

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterSplit splitFilter([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.HashJoinSegmentStorageAdapter hashJoinSegmentStorageAdapter, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter originalFilter) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]originalFilter == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterSplit([CtLiteralImpl]null, [CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter normalizedFilter = [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.filter.Filters.convertToCNF([CtVariableReadImpl]originalFilter);
        [CtLocalVariableImpl][CtCommentImpl]// build the prefix and equicondition maps
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> equiconditions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause> prefixes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause clause : [CtInvocationImpl][CtVariableReadImpl]hashJoinSegmentStorageAdapter.getClauses()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]prefixes.put([CtInvocationImpl][CtVariableReadImpl]clause.getPrefix(), [CtVariableReadImpl]clause);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.Equality equality : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clause.getCondition().getEquiConditions()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]equiconditions.put([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]clause.getPrefix() + [CtInvocationImpl][CtVariableReadImpl]equality.getRightColumn(), [CtInvocationImpl][CtVariableReadImpl]equality.getLeftExpr());
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// List of candidates for pushdown
        [CtCommentImpl]// CNF normalization will generate either
        [CtCommentImpl]// - an AND filter with multiple subfilters
        [CtCommentImpl]// - or a single non-AND subfilter which cannot be split further
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.query.filter.Filter> normalizedOrClauses;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]normalizedFilter instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.AndFilter) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]normalizedOrClauses = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.AndFilter) (normalizedFilter)).getFilters();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]normalizedOrClauses = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]normalizedFilter);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Pushdown filters, rewriting if necessary
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.query.filter.Filter> leftFilters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.query.filter.Filter> rightFilters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushDownVirtualColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis>> correlationCache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter orClause : [CtVariableReadImpl]normalizedOrClauses) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis joinFilterAnalysis = [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.analyzeJoinFilterClause([CtVariableReadImpl]hashJoinSegmentStorageAdapter, [CtVariableReadImpl]orClause, [CtVariableReadImpl]prefixes, [CtVariableReadImpl]equiconditions, [CtVariableReadImpl]correlationCache);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.isCanPushDown()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]leftFilters.add([CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.getPushdownFilter());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.getPushdownVirtualColumns() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]pushDownVirtualColumns.addAll([CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.getPushdownVirtualColumns());
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.isRetainAfterJoin()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rightFilters.add([CtInvocationImpl][CtVariableReadImpl]joinFilterAnalysis.getOriginalFilter());
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterSplit([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]leftFilters.isEmpty() ? [CtLiteralImpl]null : [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]leftFilters.size() == [CtLiteralImpl]1 ? [CtInvocationImpl][CtVariableReadImpl]leftFilters.get([CtLiteralImpl]0) : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.filter.AndFilter([CtVariableReadImpl]leftFilters), [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]rightFilters.isEmpty() ? [CtLiteralImpl]null : [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rightFilters.size() == [CtLiteralImpl]1 ? [CtInvocationImpl][CtVariableReadImpl]rightFilters.get([CtLiteralImpl]0) : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.filter.AndFilter([CtVariableReadImpl]rightFilters), [CtVariableReadImpl]pushDownVirtualColumns);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Holds the result of splitting a filter into:
     * - a portion that can be pushed down to the base table
     * - a portion that will be applied post-join
     * - additional virtual columns that need to be created on the base table to support the pushed down filters.
     */
    public static class JoinFilterSplit {
        [CtFieldImpl]final [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter baseTableFilter;

        [CtFieldImpl]final [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter joinTableFilter;

        [CtFieldImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushDownVirtualColumns;

        [CtConstructorImpl]public JoinFilterSplit([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter baseTableFilter, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter joinTableFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushDownVirtualColumns) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseTableFilter = [CtVariableReadImpl]baseTableFilter;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.joinTableFilter = [CtVariableReadImpl]joinTableFilter;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pushDownVirtualColumns = [CtVariableReadImpl]pushDownVirtualColumns;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter getBaseTableFilter() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]baseTableFilter;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter getJoinTableFilter() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]joinTableFilter;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> getPushDownVirtualColumns() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]pushDownVirtualColumns;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"JoinFilterSplit{" + [CtLiteralImpl]"baseTableFilter=") + [CtFieldReadImpl]baseTableFilter) + [CtLiteralImpl]", joinTableFilter=") + [CtFieldReadImpl]joinTableFilter) + [CtLiteralImpl]", pushDownVirtualColumns=") + [CtFieldReadImpl]pushDownVirtualColumns) + [CtLiteralImpl]'}';
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterSplit that = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterSplit) (o));
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getBaseTableFilter(), [CtInvocationImpl][CtVariableReadImpl]that.getBaseTableFilter()) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getJoinTableFilter(), [CtInvocationImpl][CtVariableReadImpl]that.getJoinTableFilter())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl]getPushDownVirtualColumns(), [CtInvocationImpl][CtVariableReadImpl]that.getPushDownVirtualColumns());
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtInvocationImpl]getBaseTableFilter(), [CtInvocationImpl]getJoinTableFilter(), [CtInvocationImpl]getPushDownVirtualColumns());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Analyze a single filter clause from a filter that is in conjunctive normal form (AND of ORs),
     * returning a JoinFilterAnalysis that contains a possible filter rewrite and information on how to handle the filter.
     *
     * @param adapter
     * 		Adapter for the join
     * @param filterClause
     * 		Individual filter clause from a filter that is in CNF
     * @param prefixes
     * 		Map of table prefixes
     * @param equiconditions
     * 		Equicondition map
     * @param correlationCache
     * 		Cache of column correlation analyses
     * @return a JoinFilterAnalysis that contains a possible filter rewrite and information on how to handle the filter.
     */
    private static [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis analyzeJoinFilterClause([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.HashJoinSegmentStorageAdapter adapter, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter filterClause, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause> prefixes, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> equiconditions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis>> correlationCache) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// NULL matching conditions are not currently pushed down
        if ([CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.filterMatchesNull([CtVariableReadImpl]filterClause)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]filterClause);
        }
        [CtIfImpl][CtCommentImpl]// Currently we only support rewrites of selector filters and selector filters within OR filters.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]filterClause instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.SelectorFilter) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.rewriteSelectorFilter([CtVariableReadImpl]adapter, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.SelectorFilter) (filterClause)), [CtVariableReadImpl]prefixes, [CtVariableReadImpl]equiconditions, [CtVariableReadImpl]correlationCache);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]filterClause instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.OrFilter) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.rewriteOrFilter([CtVariableReadImpl]adapter, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.OrFilter) (filterClause)), [CtVariableReadImpl]prefixes, [CtVariableReadImpl]equiconditions, [CtVariableReadImpl]correlationCache);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requiredColumn : [CtInvocationImpl][CtVariableReadImpl]filterClause.getRequiredColumns()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]adapter.isBaseColumn([CtVariableReadImpl]requiredColumn)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]filterClause);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis([CtLiteralImpl]true, [CtLiteralImpl]false, [CtVariableReadImpl]filterClause, [CtVariableReadImpl]filterClause, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Potentially rewrite the subfilters of an OR filter so that the whole OR filter can be pushed down to
     * the base table.
     *
     * @param adapter
     * 		Adapter for the join
     * @param orFilter
     * 		OrFilter to be rewritten
     * @param prefixes
     * 		Map of table prefixes to clauses
     * @param equiconditions
     * 		Map of equiconditions
     * @param correlationCache
     * 		Column correlation analysis cache
     * @return A JoinFilterAnalysis indicating how to handle the potentially rewritten filter
     */
    private static [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis rewriteOrFilter([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.HashJoinSegmentStorageAdapter adapter, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.OrFilter orFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause> prefixes, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> equiconditions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis>> correlationCache) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean retainRhs = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.query.filter.Filter> newFilters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter filter : [CtInvocationImpl][CtVariableReadImpl]orFilter.getFilters()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allBaseColumns = [CtLiteralImpl]true;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requiredColumn : [CtInvocationImpl][CtVariableReadImpl]filter.getRequiredColumns()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]adapter.isBaseColumn([CtVariableReadImpl]requiredColumn)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]allBaseColumns = [CtLiteralImpl]false;
                }
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]allBaseColumns) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]retainRhs = [CtLiteralImpl]true;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]filter instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.SelectorFilter) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis rewritten = [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.rewriteSelectorFilter([CtVariableReadImpl]adapter, [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.SelectorFilter) (filter)), [CtVariableReadImpl]prefixes, [CtVariableReadImpl]equiconditions, [CtVariableReadImpl]correlationCache);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]rewritten.isCanPushDown()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]orFilter);
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]newFilters.add([CtInvocationImpl][CtVariableReadImpl]rewritten.getPushdownFilter());
                    }
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]orFilter);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]newFilters.add([CtVariableReadImpl]filter);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis([CtLiteralImpl]true, [CtVariableReadImpl]retainRhs, [CtVariableReadImpl]orFilter, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.filter.OrFilter([CtVariableReadImpl]newFilters), [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Rewrites a selector filter on a join table into an IN filter on the base table.
     *
     * @param baseAdapter
     * 		The adapter for the join
     * @param selectorFilter
     * 		SelectorFilter to be rewritten
     * @param prefixes
     * 		Map of join table prefixes to clauses
     * @param equiconditions
     * 		Map of equiconditions
     * @param correlationCache
     * 		Cache of column correlation analyses
     * @return A JoinFilterAnalysis that indicates how to handle the potentially rewritten filter
     */
    private static [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis rewriteSelectorFilter([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.HashJoinSegmentStorageAdapter baseAdapter, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.SelectorFilter selectorFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause> prefixes, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> equiconditions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis>> correlationCache) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String filteringColumn = [CtInvocationImpl][CtVariableReadImpl]selectorFilter.getDimension();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause> prefixAndClause : [CtInvocationImpl][CtVariableReadImpl]prefixes.entrySet()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filteringColumn.startsWith([CtInvocationImpl][CtVariableReadImpl]prefixAndClause.getKey())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis> correlations = [CtInvocationImpl][CtVariableReadImpl]correlationCache.computeIfAbsent([CtInvocationImpl][CtVariableReadImpl]prefixAndClause.getKey(), [CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl]findCorrelatedBaseTableColumns([CtVariableReadImpl]baseAdapter, [CtVariableReadImpl]p, [CtInvocationImpl][CtVariableReadImpl]prefixes.get([CtVariableReadImpl]p), [CtVariableReadImpl]equiconditions));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]correlations == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]selectorFilter);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.query.filter.Filter> newFilters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushdownVirtualColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis correlationAnalysis : [CtVariableReadImpl]correlations) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]correlationAnalysis.supportsPushDown()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> correlatedValues = [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.getCorrelatedValuesForPushDown([CtInvocationImpl][CtVariableReadImpl]selectorFilter.getDimension(), [CtInvocationImpl][CtVariableReadImpl]selectorFilter.getValue(), [CtInvocationImpl][CtVariableReadImpl]correlationAnalysis.getJoinColumn(), [CtInvocationImpl][CtVariableReadImpl]prefixAndClause.getValue());
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]correlatedValues == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]selectorFilter);
                        }
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String correlatedBaseColumn : [CtInvocationImpl][CtVariableReadImpl]correlationAnalysis.getBaseColumns()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.InFilter rewrittenFilter = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.InFilter) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.query.filter.InDimFilter([CtVariableReadImpl]correlatedBaseColumn, [CtVariableReadImpl]correlatedValues, [CtLiteralImpl]null, [CtLiteralImpl]null).toFilter()));
                            [CtInvocationImpl][CtVariableReadImpl]newFilters.add([CtVariableReadImpl]rewrittenFilter);
                        }
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.math.expr.Expr correlatedBaseExpr : [CtInvocationImpl][CtVariableReadImpl]correlationAnalysis.getBaseExpressions()) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtCommentImpl]// need to create a virtual column for the expressions when pushing down
                            [CtTypeReferenceImpl]java.lang.String vcName = [CtInvocationImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.getCorrelatedBaseExprVirtualColumnName([CtInvocationImpl][CtVariableReadImpl]pushdownVirtualColumns.size());
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn correlatedBaseExprVirtualColumn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.virtual.ExpressionVirtualColumn([CtVariableReadImpl]vcName, [CtVariableReadImpl]correlatedBaseExpr, [CtFieldReadImpl]org.apache.druid.segment.column.ValueType.STRING);
                            [CtInvocationImpl][CtVariableReadImpl]pushdownVirtualColumns.add([CtVariableReadImpl]correlatedBaseExprVirtualColumn);
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.filter.InFilter rewrittenFilter = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.druid.segment.filter.InFilter) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.query.filter.InDimFilter([CtVariableReadImpl]vcName, [CtVariableReadImpl]correlatedValues, [CtLiteralImpl]null, [CtLiteralImpl]null).toFilter()));
                            [CtInvocationImpl][CtVariableReadImpl]newFilters.add([CtVariableReadImpl]rewrittenFilter);
                        }
                    }
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]newFilters.isEmpty()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis.createNoPushdownFilterAnalysis([CtVariableReadImpl]selectorFilter);
                }
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis([CtLiteralImpl]true, [CtLiteralImpl]true, [CtVariableReadImpl]selectorFilter, [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]newFilters.size() == [CtLiteralImpl]1 ? [CtInvocationImpl][CtVariableReadImpl]newFilters.get([CtLiteralImpl]0) : [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.filter.AndFilter([CtVariableReadImpl]newFilters), [CtVariableReadImpl]pushdownVirtualColumns);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis([CtLiteralImpl]true, [CtLiteralImpl]false, [CtVariableReadImpl]selectorFilter, [CtVariableReadImpl]selectorFilter, [CtLiteralImpl]null);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getCorrelatedBaseExprVirtualColumnName([CtParameterImpl][CtTypeReferenceImpl]int counter) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// May want to have this check other column names to absolutely prevent name conflicts
        return [CtBinaryOperatorImpl][CtFieldReadImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.PUSH_DOWN_VIRTUAL_COLUMN_NAME_BASE + [CtVariableReadImpl]counter;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper method for rewriting filters on join table columns into filters on base table columns.
     *
     * @param filterColumn
     * 		A join table column that we're filtering on
     * @param filterValue
     * 		The value to filter on
     * @param correlatedJoinColumn
     * 		A join table column that appears as the RHS of an equicondition, which we can correlate
     * 		with a column on the base table
     * @param clauseForFilteredTable
     * 		The joinable clause that corresponds to the join table being filtered on
     * @return A list of values of the correlatedJoinColumn that appear in rows where filterColumn = filterValue
    Returns null if we cannot determine the correlated values.
     */
    [CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getCorrelatedValuesForPushDown([CtParameterImpl][CtTypeReferenceImpl]java.lang.String filterColumn, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String filterValue, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlatedJoinColumn, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause clauseForFilteredTable) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String filterColumnNoPrefix = [CtInvocationImpl][CtVariableReadImpl]filterColumn.substring([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clauseForFilteredTable.getPrefix().length());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String correlatedColumnNoPrefix = [CtInvocationImpl][CtVariableReadImpl]correlatedJoinColumn.substring([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clauseForFilteredTable.getPrefix().length());
        [CtIfImpl][CtCommentImpl]// would be good to allow non-key column indices on the Joinables for better perf
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]clauseForFilteredTable.getJoinable() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.lookup.LookupJoinable) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.lookup.LookupJoinable lookupJoinable = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.druid.segment.join.lookup.LookupJoinable) ([CtVariableReadImpl]clauseForFilteredTable.getJoinable()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> correlatedValues;
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LookupColumnSelectorFactory.KEY_COLUMN.equals([CtVariableReadImpl]filterColumnNoPrefix)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LookupColumnSelectorFactory.KEY_COLUMN.equals([CtVariableReadImpl]correlatedColumnNoPrefix)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]correlatedValues = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtVariableReadImpl]filterValue);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]correlatedValues = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lookupJoinable.getExtractor().apply([CtVariableReadImpl]filterColumnNoPrefix));
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]LookupColumnSelectorFactory.VALUE_COLUMN.equals([CtVariableReadImpl]correlatedColumnNoPrefix)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]correlatedValues = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtVariableReadImpl]filterValue);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]correlatedValues = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lookupJoinable.getExtractor().unapply([CtVariableReadImpl]filterValue);
            }
            [CtReturnImpl]return [CtVariableReadImpl]correlatedValues;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]clauseForFilteredTable.getJoinable() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTableJoinable) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTableJoinable indexedTableJoinable = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTableJoinable) ([CtVariableReadImpl]clauseForFilteredTable.getJoinable()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTable indexedTable = [CtInvocationImpl][CtVariableReadImpl]indexedTableJoinable.getTable();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int filterColumnPosition = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]indexedTable.allColumns().indexOf([CtVariableReadImpl]filterColumnNoPrefix);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int correlatedColumnPosition = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]indexedTable.allColumns().indexOf([CtVariableReadImpl]correlatedColumnNoPrefix);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]filterColumnPosition < [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]correlatedColumnPosition < [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]indexedTable.keyColumns().contains([CtVariableReadImpl]filterColumnNoPrefix)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTable.Index index = [CtInvocationImpl][CtVariableReadImpl]indexedTable.columnIndex([CtVariableReadImpl]filterColumnPosition);
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTable.Reader reader = [CtInvocationImpl][CtVariableReadImpl]indexedTable.columnReader([CtVariableReadImpl]correlatedColumnPosition);
                [CtLocalVariableImpl][CtTypeReferenceImpl]it.unimi.dsi.fastutil.ints.IntList rowIndex = [CtInvocationImpl][CtVariableReadImpl]index.find([CtVariableReadImpl]filterValue);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> correlatedValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]rowIndex.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int rowNum = [CtInvocationImpl][CtVariableReadImpl]rowIndex.getInt([CtVariableReadImpl]i);
                    [CtInvocationImpl][CtVariableReadImpl]correlatedValues.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]reader.read([CtVariableReadImpl]rowNum).toString());
                }
                [CtReturnImpl]return [CtVariableReadImpl]correlatedValues;
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTable.Reader dimNameReader = [CtInvocationImpl][CtVariableReadImpl]indexedTable.columnReader([CtVariableReadImpl]filterColumnPosition);
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.table.IndexedTable.Reader correlatedColumnReader = [CtInvocationImpl][CtVariableReadImpl]indexedTable.columnReader([CtVariableReadImpl]correlatedColumnPosition);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> correlatedValueSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]indexedTable.numRows(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filterValue.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dimNameReader.read([CtVariableReadImpl]i).toString())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]correlatedValueSet.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]correlatedColumnReader.read([CtVariableReadImpl]i).toString());
                    }
                }
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]correlatedValueSet);
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * For all RHS columns that appear in the join's equiconditions, correlate them with base table columns if possible.
     *
     * @param adapter
     * 		The adapter for the join. Used to determine if a column is a base table column.
     * @param tablePrefix
     * 		Prefix for a join table
     * @param clauseForTablePrefix
     * 		Joinable clause for the prefix
     * @param equiconditions
     * 		Map of equiconditions, keyed by the right hand columns
     * @return A list of correlatation analyses for the equicondition RHS columns that reside in the table associated with
    the tablePrefix
     */
    [CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis> findCorrelatedBaseTableColumns([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.HashJoinSegmentStorageAdapter adapter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tablePrefix, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinableClause clauseForTablePrefix, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> equiconditions) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.JoinConditionAnalysis jca = [CtInvocationImpl][CtVariableReadImpl]clauseForTablePrefix.getCondition();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> rhsColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.segment.join.Equality eq : [CtInvocationImpl][CtVariableReadImpl]jca.getEquiConditions()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rhsColumns.add([CtBinaryOperatorImpl][CtVariableReadImpl]tablePrefix + [CtInvocationImpl][CtVariableReadImpl]eq.getRightColumn());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis> correlations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rhsColumn : [CtVariableReadImpl]rhsColumns) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> correlatedBaseColumns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> correlatedBaseExpressions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean terminate = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String findMappingFor = [CtVariableReadImpl]rhsColumn;
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtVariableReadImpl]terminate) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.math.expr.Expr lhs = [CtInvocationImpl][CtVariableReadImpl]equiconditions.get([CtVariableReadImpl]findMappingFor);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lhs == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String identifier = [CtInvocationImpl][CtVariableReadImpl]lhs.getBindingIfIdentifier();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]identifier == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// We push down if the function only requires base table columns
                    [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.druid.math.expr.Expr.BindingDetails bindingDetails = [CtInvocationImpl][CtVariableReadImpl]lhs.analyzeInputs();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> requiredBindings = [CtInvocationImpl][CtVariableReadImpl]bindingDetails.getRequiredBindings();
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String requiredBinding : [CtVariableReadImpl]requiredBindings) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]adapter.isBaseColumn([CtVariableReadImpl]requiredBinding)) [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]null;
                        }
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]terminate = [CtLiteralImpl]true;
                    [CtInvocationImpl][CtVariableReadImpl]correlatedBaseExpressions.add([CtVariableReadImpl]lhs);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// simple identifier, see if we can correlate it with a column on the base table
                    [CtVariableWriteImpl]findMappingFor = [CtVariableReadImpl]identifier;
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]adapter.isBaseColumn([CtVariableReadImpl]identifier)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]terminate = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtVariableReadImpl]correlatedBaseColumns.add([CtVariableReadImpl]findMappingFor);
                    }
                }
            } 
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]correlatedBaseColumns.isEmpty() && [CtInvocationImpl][CtVariableReadImpl]correlatedBaseExpressions.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtInvocationImpl][CtVariableReadImpl]correlations.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterColumnCorrelationAnalysis([CtVariableReadImpl]rhsColumn, [CtVariableReadImpl]correlatedBaseColumns, [CtVariableReadImpl]correlatedBaseExpressions));
        }
        [CtReturnImpl]return [CtVariableReadImpl]correlations;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean filterMatchesNull([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter filter) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.ValueMatcher valueMatcher = [CtInvocationImpl][CtVariableReadImpl]filter.makeMatcher([CtFieldReadImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.ALL_NULL_COLUMN_SELECTOR_FACTORY);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]valueMatcher.matches();
    }

    [CtClassImpl]private static class AllNullColumnSelectorFactory implements [CtTypeReferenceImpl]org.apache.druid.segment.ColumnSelectorFactory {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.apache.druid.segment.DimensionSelector makeDimensionSelector([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.dimension.DimensionSpec dimensionSpec) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.DimensionSelector.constant([CtLiteralImpl]null);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.apache.druid.segment.ColumnValueSelector<[CtWildcardReferenceImpl]?> makeColumnValueSelector([CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnName) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.druid.segment.NilColumnValueSelector.instance();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.apache.druid.segment.column.ColumnCapabilities getColumnCapabilities([CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnName) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Holds information about:
     * - whether a filter can be pushed down
     * - if it needs to be retained after the join,
     * - a reference to the original filter
     * - a potentially rewritten filter to be pushed down to the base table
     * - a list of virtual columns that need to be created on the base table to support the pushed down filter
     */
    private static class JoinFilterAnalysis {
        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean canPushDown;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean retainAfterJoin;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter originalFilter;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter pushdownFilter;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushdownVirtualColumns;

        [CtConstructorImpl]public JoinFilterAnalysis([CtParameterImpl][CtTypeReferenceImpl]boolean canPushDown, [CtParameterImpl][CtTypeReferenceImpl]boolean retainAfterJoin, [CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter originalFilter, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter pushdownFilter, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> pushdownVirtualColumns) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.canPushDown = [CtVariableReadImpl]canPushDown;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.retainAfterJoin = [CtVariableReadImpl]retainAfterJoin;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.originalFilter = [CtVariableReadImpl]originalFilter;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pushdownFilter = [CtVariableReadImpl]pushdownFilter;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pushdownVirtualColumns = [CtVariableReadImpl]pushdownVirtualColumns;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isCanPushDown() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]canPushDown;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isRetainAfterJoin() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]retainAfterJoin;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter getOriginalFilter() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]originalFilter;
        }

        [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
        public [CtTypeReferenceImpl]org.apache.druid.query.filter.Filter getPushdownFilter() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]pushdownFilter;
        }

        [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
        public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.segment.VirtualColumn> getPushdownVirtualColumns() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]pushdownVirtualColumns;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Utility method for generating an analysis that represents: "Filter cannot be pushed down"
         *
         * @param originalFilter
         * 		The original filter which cannot be pushed down
         * @return analysis that represents: "Filter cannot be pushed down"
         */
        public static [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis createNoPushdownFilterAnalysis([CtParameterImpl][CtTypeReferenceImpl]org.apache.druid.query.filter.Filter originalFilter) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.druid.segment.join.JoinFilterAnalyzer.JoinFilterAnalysis([CtLiteralImpl]false, [CtLiteralImpl]true, [CtVariableReadImpl]originalFilter, [CtLiteralImpl]null, [CtLiteralImpl]null);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Represents an analysis of what base table columns, if any, can be correlated with a column that will
     * be filtered on.
     * <p>
     * For example, if we're joining on a base table via the equiconditions (id = j.id AND f(id2) = j.id2),
     * then we can correlate j.id with id (base table column) and j.id2 with f(id2) (a base table expression).
     */
    private static class JoinFilterColumnCorrelationAnalysis {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String joinColumn;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> baseColumns;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> baseExpressions;

        [CtConstructorImpl]public JoinFilterColumnCorrelationAnalysis([CtParameterImpl][CtTypeReferenceImpl]java.lang.String joinColumn, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> baseColumns, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> baseExpressions) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.joinColumn = [CtVariableReadImpl]joinColumn;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseColumns = [CtVariableReadImpl]baseColumns;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseExpressions = [CtVariableReadImpl]baseExpressions;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getJoinColumn() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]joinColumn;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getBaseColumns() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]baseColumns;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.druid.math.expr.Expr> getBaseExpressions() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]baseExpressions;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean supportsPushDown() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]baseColumns.isEmpty()) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]baseExpressions.isEmpty());
        }
    }
}
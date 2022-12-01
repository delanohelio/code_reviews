[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

  Licensed under the Apache License, Version 2.0 (the "License").
  You may not use this file except in compliance with the License.
  A copy of the License is located at

    http://www.apache.org/licenses/LICENSE-2.0

  or in the "license" file accompanying this file. This file is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.sql.planner.logical;
[CtUnresolvedImport]import lombok.experimental.UtilityClass;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.Expression;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.aggregation.Aggregator;
[CtClassImpl][CtJavaDocImpl]/**
 * Logical Plan DSL.
 */
[CtAnnotationImpl]@lombok.experimental.UtilityClass
public class LogicalPlanDSL {
    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan aggregation([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan input, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.aggregation.Aggregator> aggregatorList, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.Expression> groupByList) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalAggregation([CtVariableReadImpl]input, [CtVariableReadImpl]aggregatorList, [CtVariableReadImpl]groupByList);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan filter([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan input, [CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.Expression expression) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalFilter([CtVariableReadImpl]input, [CtVariableReadImpl]expression);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan relation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalRelation([CtVariableReadImpl]tableName);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan rename([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan input, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression, [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression> renameMap) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalRename([CtVariableReadImpl]input, [CtVariableReadImpl]renameMap);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan project([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan input, [CtParameterImpl]com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression... fields) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalProject([CtVariableReadImpl]input, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]fields));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan remove([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan input, [CtParameterImpl]com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression... fields) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalRemove([CtVariableReadImpl]input, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtVariableReadImpl]fields));
    }
}
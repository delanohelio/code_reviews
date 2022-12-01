[CompilationUnitImpl][CtCommentImpl]/* Copyright 2015 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.google.cloud.bigquery;
[CtImportImpl]import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
[CtUnresolvedImport]import com.google.common.collect.Iterables;
[CtUnresolvedImport]import com.google.cloud.Policy;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.google.common.base.Supplier;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
[CtUnresolvedImport]import static com.google.cloud.bigquery.PolicyHelper.convertToApiPolicy;
[CtImportImpl]import java.util.concurrent.Callable;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.GetQueryResultsResponse;
[CtUnresolvedImport]import com.google.common.base.Strings;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.QueryRequest;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkArgument;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import com.google.common.collect.FluentIterable;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableRow;
[CtUnresolvedImport]import com.google.cloud.PageImpl.NextPageFetcher;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableSchema;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.google.cloud.PageImpl;
[CtUnresolvedImport]import com.google.api.gax.paging.Page;
[CtUnresolvedImport]import com.google.cloud.RetryHelper.RetryHelperException;
[CtUnresolvedImport]import com.google.cloud.Tuple;
[CtUnresolvedImport]import com.google.cloud.BaseService;
[CtUnresolvedImport]import com.google.common.base.Function;
[CtUnresolvedImport]import static com.google.cloud.RetryHelper.runWithRetries;
[CtUnresolvedImport]import com.google.cloud.RetryHelper;
[CtUnresolvedImport]import com.google.cloud.bigquery.spi.v2.BigQueryRpc;
[CtUnresolvedImport]import com.google.common.collect.Maps;
[CtUnresolvedImport]import com.google.cloud.bigquery.InsertAllRequest.RowToInsert;
[CtUnresolvedImport]import static com.google.cloud.bigquery.PolicyHelper.convertFromApiPolicy;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import com.google.api.core.InternalApi;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.ErrorProto;
[CtUnresolvedImport]import com.google.api.services.bigquery.model.TableDataList;
[CtImportImpl]import java.util.Map;
[CtClassImpl]final class BigQueryImpl extends [CtTypeReferenceImpl]com.google.cloud.BaseService<[CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions> implements [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQuery {
    [CtClassImpl]private static class DatasetPageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]3057564042439021278L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String projectId;

        [CtConstructorImpl]DatasetPageFetcher([CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.projectId = [CtVariableReadImpl]projectId;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listDatasets([CtFieldReadImpl]projectId, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions);
        }
    }

    [CtClassImpl]private static class TablePageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]8611248840504201187L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId;

        [CtConstructorImpl]TablePageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.datasetId = [CtVariableReadImpl]datasetId;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listTables([CtFieldReadImpl]datasetId, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions);
        }
    }

    [CtClassImpl]private static class ModelPageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]8611248811504201187L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId;

        [CtConstructorImpl]ModelPageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.datasetId = [CtVariableReadImpl]datasetId;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listModels([CtFieldReadImpl]datasetId, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions);
        }
    }

    [CtClassImpl]private static class RoutinePageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]8611242311504201187L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId;

        [CtConstructorImpl]RoutinePageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.datasetId = [CtVariableReadImpl]datasetId;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listRoutines([CtFieldReadImpl]datasetId, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions);
        }
    }

    [CtClassImpl]private static class JobPageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.Job> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]8536533282558245472L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtConstructorImpl]JobPageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Job> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listJobs([CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions);
        }
    }

    [CtClassImpl]private static class TableDataPageFetcher implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]8501991114794410114L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId table;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema;

        [CtConstructorImpl]TableDataPageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId table, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.table = [CtVariableReadImpl]table;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.schema = [CtVariableReadImpl]schema;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList> getNextPage() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]listTableData([CtFieldReadImpl]table, [CtFieldReadImpl]schema, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions).x();
        }
    }

    [CtClassImpl]private class QueryPageFetcher extends [CtTypeReferenceImpl]java.lang.Thread implements [CtTypeReferenceImpl]com.google.cloud.PageImpl.NextPageFetcher<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]8501991114794410114L;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> requestOptions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions;

        [CtFieldImpl]private [CtTypeReferenceImpl]com.google.cloud.bigquery.Job job;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId table;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema;

        [CtConstructorImpl]QueryPageFetcher([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String cursor, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestOptions = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.PageImpl.nextRequestOptions([CtTypeAccessImpl]BigQueryRpc.Option.PAGE_TOKEN, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionMap);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceOptions = [CtVariableReadImpl]serviceOptions;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.job = [CtInvocationImpl]getJob([CtVariableReadImpl]jobId);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.table = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]com.google.cloud.bigquery.QueryJobConfiguration) ([CtFieldReadImpl]job.getConfiguration())).getDestinationTable();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.schema = [CtVariableReadImpl]schema;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList> getNextPage() [CtBlockImpl]{
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]JobStatus.State.DONE.equals([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]job.getStatus().getState())) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl]java.lang.Thread.sleep([CtLiteralImpl]5000);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ex) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
                }
                [CtAssignmentImpl][CtFieldWriteImpl]job = [CtInvocationImpl][CtFieldReadImpl]job.reload();
            } 
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]listTableData([CtFieldReadImpl]table, [CtFieldReadImpl]schema, [CtFieldReadImpl]serviceOptions, [CtFieldReadImpl]requestOptions).x();
        }
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc bigQueryRpc;

    [CtConstructorImpl]BigQueryImpl([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions options) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]options);
        [CtAssignmentImpl][CtFieldWriteImpl]bigQueryRpc = [CtInvocationImpl][CtVariableReadImpl]options.getBigQueryRpcV2();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset create([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetInfo datasetInfo, [CtParameterImpl]com.google.cloud.bigquery.DatasetOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset datasetPb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetInfo.getDatasetId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetInfo.getDatasetId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Dataset.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.create([CtVariableReadImpl]datasetPb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Table create([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableInfo tableInfo, [CtParameterImpl]com.google.cloud.bigquery.TableOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table tablePb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.getTableId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.getTableId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Table.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.create([CtVariableReadImpl]tablePb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine create([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineInfo routineInfo, [CtParameterImpl]com.google.cloud.bigquery.RoutineOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine routinePb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.getRoutineId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.getRoutineId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Routine.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.create([CtVariableReadImpl]routinePb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Job create([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobInfo jobInfo, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.base.Supplier<[CtTypeReferenceImpl]com.google.cloud.bigquery.JobId> idProvider = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Supplier<[CtTypeReferenceImpl]com.google.cloud.bigquery.JobId>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]com.google.cloud.bigquery.JobId get() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.of();
            }
        };
        [CtReturnImpl]return [CtInvocationImpl]create([CtVariableReadImpl]jobInfo, [CtVariableReadImpl]idProvider, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.api.core.InternalApi([CtLiteralImpl]"visible for testing")
    [CtTypeReferenceImpl]com.google.cloud.bigquery.Job create([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobInfo jobInfo, [CtParameterImpl][CtTypeReferenceImpl]com.google.common.base.Supplier<[CtTypeReferenceImpl]com.google.cloud.bigquery.JobId> idProvider, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean idRandom = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]jobInfo.getJobId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]jobInfo = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobInfo.toBuilder().setJobId([CtInvocationImpl][CtVariableReadImpl]idProvider.get()).build();
            [CtAssignmentImpl][CtVariableWriteImpl]idRandom = [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job jobPb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobInfo.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException createException;
        [CtTryImpl][CtCommentImpl]// NOTE(pongad): This double-try structure is admittedly odd.
        [CtCommentImpl]// translateAndThrow itself throws, and pretends to return an exception only
        [CtCommentImpl]// so users can pretend to throw.
        [CtCommentImpl]// This makes it difficult to translate without throwing.
        [CtCommentImpl]// Fixing this entails some work on BaseServiceException.translate.
        [CtCommentImpl]// Since that affects a bunch of APIs, we should fix this as a separate change.
        try [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Job.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job call() [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.create([CtVariableReadImpl]jobPb, [CtVariableReadImpl]optionsMap);
                    }
                }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]createException = [CtVariableReadImpl]e;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]idRandom) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]createException;
        }
        [CtLocalVariableImpl][CtCommentImpl]// If create RPC fails, it's still possible that the job has been successfully
        [CtCommentImpl]// created,
        [CtCommentImpl]// and get might work.
        [CtCommentImpl]// We can only do this if we randomly generated the ID. Otherwise we might
        [CtCommentImpl]// mistakenly
        [CtCommentImpl]// fetch a job created by someone else.
        [CtTypeReferenceImpl]com.google.cloud.bigquery.Job job;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]job = [CtInvocationImpl]getJob([CtInvocationImpl][CtVariableReadImpl]jobInfo.getJobId());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]createException;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]job == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]createException;
        }
        [CtReturnImpl]return [CtVariableReadImpl]job;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset getDataset([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]com.google.cloud.bigquery.DatasetOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDataset([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.DatasetId.of([CtVariableReadImpl]datasetId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset getDataset([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]com.google.cloud.bigquery.DatasetOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId completeDatasetId = [CtInvocationImpl][CtVariableReadImpl]datasetId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset answer = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getDataset([CtInvocationImpl][CtVariableReadImpl]completeDatasetId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeDatasetId.getDataset(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getOptions().getThrowNotFound() && [CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtFieldReadImpl]java.net.HttpURLConnection.HTTP_NOT_FOUND, [CtLiteralImpl]"Dataset not found");
            }
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]answer == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Dataset.fromPb([CtThisAccessImpl]this, [CtVariableReadImpl]answer);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset> listDatasets([CtParameterImpl]com.google.cloud.bigquery.DatasetListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]listDatasets([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId(), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset> listDatasets([CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl]com.google.cloud.bigquery.DatasetListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listDatasets([CtVariableReadImpl]projectId, [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset> listDatasets([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>> result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>>>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>> call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listDatasets([CtVariableReadImpl]projectId, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.x();
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.DatasetPageFetcher([CtVariableReadImpl]projectId, [CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionsMap), [CtVariableReadImpl]cursor, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtInvocationImpl][CtVariableReadImpl]result.y(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset, [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset dataset) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Dataset.fromPb([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getService(), [CtVariableReadImpl]dataset);
                }
            }));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]com.google.cloud.bigquery.DatasetDeleteOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]delete([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.DatasetId.of([CtVariableReadImpl]datasetId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]com.google.cloud.bigquery.DatasetDeleteOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId completeDatasetId = [CtInvocationImpl][CtVariableReadImpl]datasetId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.deleteDataset([CtInvocationImpl][CtVariableReadImpl]completeDatasetId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeDatasetId.getDataset(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]delete([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]tableId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.deleteTable([CtInvocationImpl][CtVariableReadImpl]completeTableId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getTable());
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.ModelId modelId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.ModelId completeModelId = [CtInvocationImpl][CtVariableReadImpl]modelId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]modelId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]modelId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.deleteModel([CtInvocationImpl][CtVariableReadImpl]completeModelId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeModelId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeModelId.getModel());
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean delete([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineId routineId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineId completeRoutineId = [CtInvocationImpl][CtVariableReadImpl]routineId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]routineId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]routineId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.deleteRoutine([CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getRoutine());
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Dataset update([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetInfo datasetInfo, [CtParameterImpl]com.google.cloud.bigquery.DatasetOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset datasetPb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetInfo.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Dataset.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Dataset call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.patch([CtVariableReadImpl]datasetPb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Table update([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableInfo tableInfo, [CtParameterImpl]com.google.cloud.bigquery.TableOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table tablePb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.getTableId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tableInfo.getTableId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Table.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.patch([CtVariableReadImpl]tablePb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Model update([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.ModelInfo modelInfo, [CtParameterImpl]com.google.cloud.bigquery.ModelOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model modelPb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelInfo.getModelId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelInfo.getModelId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Model.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.patch([CtVariableReadImpl]modelPb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine update([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineInfo routineInfo, [CtParameterImpl]com.google.cloud.bigquery.RoutineOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine routinePb = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.getRoutineId().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routineInfo.getRoutineId().getProject()).toPb();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Routine.fromPb([CtThisAccessImpl]this, [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.update([CtVariableReadImpl]routinePb, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Table getTable([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String tableId, [CtParameterImpl]com.google.cloud.bigquery.TableOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getTable([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]tableId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Table getTable([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]com.google.cloud.bigquery.TableOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// More context about why this:
        [CtCommentImpl]// https://github.com/googleapis/google-cloud-java/issues/3808
        final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table answer = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getTable([CtInvocationImpl][CtVariableReadImpl]completeTableId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getTable(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getOptions().getThrowNotFound() && [CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtFieldReadImpl]java.net.HttpURLConnection.HTTP_NOT_FOUND, [CtLiteralImpl]"Table not found");
            }
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]answer == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Table.fromPb([CtThisAccessImpl]this, [CtVariableReadImpl]answer);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Model getModel([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String modelId, [CtParameterImpl]com.google.cloud.bigquery.ModelOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getModel([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.ModelId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]modelId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Model getModel([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.ModelId modelId, [CtParameterImpl]com.google.cloud.bigquery.ModelOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.ModelId completeModelId = [CtInvocationImpl][CtVariableReadImpl]modelId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]modelId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]modelId.getProject());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model answer = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getModel([CtInvocationImpl][CtVariableReadImpl]completeModelId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeModelId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeModelId.getModel(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getOptions().getThrowNotFound() && [CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtFieldReadImpl]java.net.HttpURLConnection.HTTP_NOT_FOUND, [CtLiteralImpl]"Model not found");
            }
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]answer == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Model.fromPb([CtThisAccessImpl]this, [CtVariableReadImpl]answer);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine getRoutine([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String routineId, [CtParameterImpl]com.google.cloud.bigquery.RoutineOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getRoutine([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.RoutineId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]routineId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine getRoutine([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineId routineId, [CtParameterImpl]com.google.cloud.bigquery.RoutineOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.RoutineId completeRoutineId = [CtInvocationImpl][CtVariableReadImpl]routineId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]routineId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]routineId.getProject());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine answer = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getRoutine([CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeRoutineId.getRoutine(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getOptions().getThrowNotFound() && [CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtFieldReadImpl]java.net.HttpURLConnection.HTTP_NOT_FOUND, [CtLiteralImpl]"Routine not found");
            }
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]answer == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Routine.fromPb([CtThisAccessImpl]this, [CtVariableReadImpl]answer);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> listTables([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]com.google.cloud.bigquery.TableListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listTables([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.DatasetId.of([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId(), [CtVariableReadImpl]datasetId), [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> listTables([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]com.google.cloud.bigquery.TableListOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId completeDatasetId = [CtInvocationImpl][CtVariableReadImpl]datasetId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId());
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listTables([CtVariableReadImpl]completeDatasetId, [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> listModels([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]com.google.cloud.bigquery.ModelListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listModels([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.DatasetId.of([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId(), [CtVariableReadImpl]datasetId), [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> listModels([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]com.google.cloud.bigquery.ModelListOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId completeDatasetId = [CtInvocationImpl][CtVariableReadImpl]datasetId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId());
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listModels([CtVariableReadImpl]completeDatasetId, [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> listRoutines([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl]com.google.cloud.bigquery.RoutineListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listRoutines([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.DatasetId.of([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId(), [CtVariableReadImpl]datasetId), [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> listRoutines([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]com.google.cloud.bigquery.RoutineListOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId completeDatasetId = [CtInvocationImpl][CtVariableReadImpl]datasetId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId());
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listRoutines([CtVariableReadImpl]completeDatasetId, [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> listPartitions([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> partitions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Table metaTable = [CtInvocationImpl]getTable([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableId.of([CtInvocationImpl][CtVariableReadImpl]tableId.getDataset(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tableId.getTable() + [CtLiteralImpl]"$__PARTITIONS_SUMMARY__"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema metaSchema = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]metaTable.getDefinition().getSchema();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partition_id = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Field field : [CtInvocationImpl][CtVariableReadImpl]metaSchema.getFields()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getName().equals([CtLiteralImpl]"partition_id")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]partition_id = [CtInvocationImpl][CtVariableReadImpl]field.getName();
                [CtBreakImpl]break;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult result = [CtInvocationImpl][CtVariableReadImpl]metaTable.list([CtVariableReadImpl]metaSchema);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList list : [CtInvocationImpl][CtVariableReadImpl]result.iterateAll()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]partitions.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]list.get([CtVariableReadImpl]partition_id).getStringValue());
        }
        [CtReturnImpl]return [CtVariableReadImpl]partitions;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> listTables([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>> result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>>>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table>> call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listTables([CtInvocationImpl][CtVariableReadImpl]datasetId.getProject(), [CtInvocationImpl][CtVariableReadImpl]datasetId.getDataset(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.x();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.bigquery.Table> tables = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtInvocationImpl][CtVariableReadImpl]result.y(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table, [CtTypeReferenceImpl]com.google.cloud.bigquery.Table>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.bigquery.Table apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Table table) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Table.fromPb([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getService(), [CtVariableReadImpl]table);
                }
            });
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.TablePageFetcher([CtVariableReadImpl]datasetId, [CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionsMap), [CtVariableReadImpl]cursor, [CtVariableReadImpl]tables);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> listModels([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model>> result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model>>>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model>> call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listModels([CtInvocationImpl][CtVariableReadImpl]datasetId.getProject(), [CtInvocationImpl][CtVariableReadImpl]datasetId.getDataset(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.x();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.bigquery.Model> models = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtInvocationImpl][CtVariableReadImpl]result.y(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model, [CtTypeReferenceImpl]com.google.cloud.bigquery.Model>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.bigquery.Model apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Model model) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Model.fromPb([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getService(), [CtVariableReadImpl]model);
                }
            });
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.ModelPageFetcher([CtVariableReadImpl]datasetId, [CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionsMap), [CtVariableReadImpl]cursor, [CtVariableReadImpl]models);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> listRoutines([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.DatasetId datasetId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>> result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>>>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine>> call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listRoutines([CtInvocationImpl][CtVariableReadImpl]datasetId.getProject(), [CtInvocationImpl][CtVariableReadImpl]datasetId.getDataset(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.x();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.bigquery.Routine> routines = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtInvocationImpl][CtVariableReadImpl]result.y(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine, [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.bigquery.Routine apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Routine routinePb) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Routine.fromPb([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getService(), [CtVariableReadImpl]routinePb);
                }
            });
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.RoutinePageFetcher([CtVariableReadImpl]datasetId, [CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionsMap), [CtVariableReadImpl]cursor, [CtVariableReadImpl]routines);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.InsertAllResponse insertAll([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.InsertAllRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getTable().setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getTable().getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getTable().getProject());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest requestPb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest();
        [CtInvocationImpl][CtVariableReadImpl]requestPb.setIgnoreUnknownValues([CtInvocationImpl][CtVariableReadImpl]request.ignoreUnknownValues());
        [CtInvocationImpl][CtVariableReadImpl]requestPb.setSkipInvalidRows([CtInvocationImpl][CtVariableReadImpl]request.skipInvalidRows());
        [CtInvocationImpl][CtVariableReadImpl]requestPb.setTemplateSuffix([CtInvocationImpl][CtVariableReadImpl]request.getTemplateSuffix());
        [CtLocalVariableImpl][CtCommentImpl]// Using an array of size 1 here to have a mutable boolean variable, which can
        [CtCommentImpl]// be modified in
        [CtCommentImpl]// an anonymous inner class.
        final [CtArrayTypeReferenceImpl]boolean[] allInsertIdsSet = [CtNewArrayImpl]new boolean[]{ [CtLiteralImpl]true };
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows> rowsPb = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.FluentIterable.from([CtInvocationImpl][CtVariableReadImpl]request.getRows()).transform([CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.cloud.bigquery.InsertAllRequest.RowToInsert, [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows apply([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.InsertAllRequest.RowToInsert rowToInsert) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]allInsertIdsSet[[CtLiteralImpl]0] &= [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rowToInsert.getId() != [CtLiteralImpl]null;
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows().setInsertId([CtInvocationImpl][CtVariableReadImpl]rowToInsert.getId()).setJson([CtInvocationImpl][CtVariableReadImpl]rowToInsert.getContent());
            }
        }).toList();
        [CtInvocationImpl][CtVariableReadImpl]requestPb.setRows([CtVariableReadImpl]rowsPb);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllResponse responsePb;
        [CtIfImpl]if ([CtArrayReadImpl][CtVariableReadImpl]allInsertIdsSet[[CtLiteralImpl]0]) [CtBlockImpl]{
            [CtTryImpl][CtCommentImpl]// allowing retries only if all row insertIds are set (used for deduplication)
            try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]responsePb = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllResponse>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataInsertAllResponse call() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.insertAll([CtInvocationImpl][CtVariableReadImpl]tableId.getProject(), [CtInvocationImpl][CtVariableReadImpl]tableId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]tableId.getTable(), [CtVariableReadImpl]requestPb);
                    }
                }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]responsePb = [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.insertAll([CtInvocationImpl][CtVariableReadImpl]tableId.getProject(), [CtInvocationImpl][CtVariableReadImpl]tableId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]tableId.getTable(), [CtVariableReadImpl]requestPb);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.InsertAllResponse.fromPb([CtVariableReadImpl]responsePb);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult listTableData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableId, [CtParameterImpl]com.google.cloud.bigquery.TableDataListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]listTableData([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]tableId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult listTableData([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]com.google.cloud.bigquery.TableDataListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]listTableData([CtVariableReadImpl]tableId, [CtLiteralImpl]null, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult listTableData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema, [CtParameterImpl]com.google.cloud.bigquery.TableDataListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]listTableData([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableId.of([CtVariableReadImpl]datasetId, [CtVariableReadImpl]tableId), [CtVariableReadImpl]schema, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult listTableData([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema, [CtParameterImpl]com.google.cloud.bigquery.TableDataListOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList>, [CtTypeReferenceImpl]java.lang.Long> data = [CtInvocationImpl]listTableData([CtVariableReadImpl]tableId, [CtVariableReadImpl]schema, [CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult([CtVariableReadImpl]schema, [CtInvocationImpl][CtVariableReadImpl]data.y(), [CtInvocationImpl][CtVariableReadImpl]data.x());
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList>, [CtTypeReferenceImpl]java.lang.Long> listTableData([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataList result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataList>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableDataList call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listTableData([CtInvocationImpl][CtVariableReadImpl]completeTableId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getDataset(), [CtInvocationImpl][CtVariableReadImpl]completeTableId.getTable(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.getPageToken();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> pageOptionMap = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]cursor)) ? [CtVariableReadImpl]optionsMap : [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.TableDataListOption.startIndex([CtLiteralImpl]0));
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.Tuple.of([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.TableDataPageFetcher([CtVariableReadImpl]tableId, [CtVariableReadImpl]schema, [CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]pageOptionMap), [CtVariableReadImpl]cursor, [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.transformTableData([CtInvocationImpl][CtVariableReadImpl]result.getRows(), [CtVariableReadImpl]schema)), [CtInvocationImpl][CtVariableReadImpl]result.getTotalRows());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList> transformTableData([CtParameterImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableRow> tableDataPb, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.copyOf([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]tableDataPb != [CtLiteralImpl]null ? [CtVariableReadImpl]tableDataPb : [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableRow>of(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableRow, [CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList>()[CtClassImpl] {
            [CtFieldImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.FieldList fields = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]schema != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl]schema.getFields() : [CtLiteralImpl]null;

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]com.google.cloud.bigquery.FieldValueList apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableRow rowPb) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.FieldValueList.fromPb([CtInvocationImpl][CtVariableReadImpl]rowPb.getF(), [CtFieldReadImpl]fields);
            }
        }));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Job getJob([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jobId, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getJob([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.of([CtVariableReadImpl]jobId), [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.Job getJob([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.JobId completeJobId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()).setLocation([CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]jobId.getLocation() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getOptions().getLocation() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getLocation() : [CtInvocationImpl][CtVariableReadImpl]jobId.getLocation());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job answer = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getJob([CtInvocationImpl][CtVariableReadImpl]completeJobId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getJob(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getLocation(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getOptions().getThrowNotFound() && [CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtFieldReadImpl]java.net.HttpURLConnection.HTTP_NOT_FOUND, [CtLiteralImpl]"Job not found");
            }
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]answer == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Job.fromPb([CtThisAccessImpl]this, [CtVariableReadImpl]answer);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Job> listJobs([CtParameterImpl]com.google.cloud.bigquery.JobListOption... options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.listJobs([CtInvocationImpl]getOptions(), [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.api.gax.paging.Page<[CtTypeReferenceImpl]com.google.cloud.bigquery.Job> listJobs([CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job>> result = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job>>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]com.google.cloud.Tuple<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job>> call() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().listJobs([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getProjectId(), [CtVariableReadImpl]optionsMap);
            }
        }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]result.x();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]com.google.cloud.bigquery.Job> jobs = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Iterables.transform([CtInvocationImpl][CtVariableReadImpl]result.y(), [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job, [CtTypeReferenceImpl]com.google.cloud.bigquery.Job>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]com.google.cloud.bigquery.Job apply([CtParameterImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.Job job) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Job.fromPb([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getService(), [CtVariableReadImpl]job);
            }
        });
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.JobPageFetcher([CtVariableReadImpl]serviceOptions, [CtVariableReadImpl]cursor, [CtVariableReadImpl]optionsMap), [CtVariableReadImpl]cursor, [CtVariableReadImpl]jobs);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean cancel([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jobId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]cancel([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.of([CtVariableReadImpl]jobId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean cancel([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.JobId completeJobId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()).setLocation([CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]jobId.getLocation() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getOptions().getLocation() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getLocation() : [CtInvocationImpl][CtVariableReadImpl]jobId.getLocation());
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.cancel([CtInvocationImpl][CtVariableReadImpl]completeJobId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getJob(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getLocation());
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult query([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.QueryJobConfiguration configuration, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]com.google.cloud.bigquery.JobException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Job.checkNotDryRun([CtVariableReadImpl]configuration, [CtLiteralImpl]"query");
        [CtLocalVariableImpl][CtCommentImpl]// If all parameters passed in configuration are supported by the query() method on the backend,
        [CtCommentImpl]// put on fast path
        [CtTypeReferenceImpl]com.google.cloud.bigquery.QueryRequestInfo requestInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.QueryRequestInfo([CtVariableReadImpl]configuration);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]requestInfo.isFastQuerySupported()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String projectId = [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.QueryRequest content = [CtInvocationImpl][CtVariableReadImpl]requestInfo.toPb();
            [CtReturnImpl]return [CtInvocationImpl]queryRpc([CtVariableReadImpl]projectId, [CtVariableReadImpl]content, [CtVariableReadImpl]options);
        }
        [CtReturnImpl][CtCommentImpl]// Otherwise, fall back to the existing create query job logic
        return [CtInvocationImpl][CtInvocationImpl]create([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobInfo.of([CtVariableReadImpl]configuration), [CtVariableReadImpl]options).getQueryResults();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult queryRpc([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String projectId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.api.services.bigquery.model.QueryRequest content, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.QueryResponse results;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]results = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.QueryResponse>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.QueryResponse call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.queryRpc([CtVariableReadImpl]projectId, [CtVariableReadImpl]content);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getErrors() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryError> bigQueryErrors = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.transform([CtInvocationImpl][CtVariableReadImpl]results.getErrors(), [CtTypeAccessImpl]BigQueryError.FROM_PB_FUNCTION);
            [CtThrowImpl][CtCommentImpl]// Throwing BigQueryException since there may be no JobId and we want to stay consistent
            [CtCommentImpl]// with the case where there there is a HTTP error
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException([CtVariableReadImpl]bigQueryErrors);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long numRows;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Schema schema;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]results.getSchema() == [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]results.getJobComplete()) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]results.getJobComplete())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.fromPb([CtInvocationImpl][CtVariableReadImpl]results.getJobReference());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Job job = [CtInvocationImpl]getJob([CtVariableReadImpl]jobId, [CtVariableReadImpl]options);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult tableResult = [CtInvocationImpl][CtVariableReadImpl]job.getQueryResults();
            [CtReturnImpl]return [CtVariableReadImpl]tableResult;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]schema = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getSchema() == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Schema.fromPb([CtInvocationImpl][CtVariableReadImpl]results.getSchema());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]results.getNumDmlAffectedRows() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]results.getTotalRows() == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]numRows = [CtLiteralImpl]0L;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getNumDmlAffectedRows() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]numRows = [CtInvocationImpl][CtVariableReadImpl]results.getNumDmlAffectedRows();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]numRows = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]results.getTotalRows().longValue();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getPageToken() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId = [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.fromPb([CtInvocationImpl][CtVariableReadImpl]results.getJobReference());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cursor = [CtInvocationImpl][CtVariableReadImpl]results.getPageToken();
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult([CtVariableReadImpl]schema, [CtVariableReadImpl]numRows, [CtConstructorCallImpl][CtCommentImpl]// fetch next pages of results
            [CtCommentImpl]// cache first page of result
            new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.QueryPageFetcher([CtVariableReadImpl]jobId, [CtVariableReadImpl]schema, [CtInvocationImpl]getOptions(), [CtVariableReadImpl]cursor, [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options)), [CtVariableReadImpl]cursor, [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.transformTableData([CtInvocationImpl][CtVariableReadImpl]results.getRows(), [CtVariableReadImpl]schema)));
        }
        [CtReturnImpl][CtCommentImpl]// only 1 page of result
        return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult([CtVariableReadImpl]schema, [CtVariableReadImpl]numRows, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.PageImpl<>([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryImpl.TableDataPageFetcher([CtLiteralImpl]null, [CtVariableReadImpl]schema, [CtInvocationImpl]getOptions(), [CtLiteralImpl]null, [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options)), [CtLiteralImpl]null, [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.transformTableData([CtInvocationImpl][CtVariableReadImpl]results.getRows(), [CtVariableReadImpl]schema)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableResult query([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.QueryJobConfiguration configuration, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl]com.google.cloud.bigquery.JobOption... options) throws [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]com.google.cloud.bigquery.JobException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Job.checkNotDryRun([CtVariableReadImpl]configuration, [CtLiteralImpl]"query");
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]create([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobInfo.of([CtVariableReadImpl]jobId, [CtVariableReadImpl]configuration), [CtVariableReadImpl]options).getQueryResults();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.QueryResponse getQueryResults([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl]com.google.cloud.bigquery.QueryResultsOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
        [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.getQueryResults([CtVariableReadImpl]jobId, [CtInvocationImpl]getOptions(), [CtVariableReadImpl]optionsMap);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.cloud.bigquery.QueryResponse getQueryResults([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryOptions serviceOptions, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.JobId completeJobId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobId.setProjectId([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getProjectId()).setLocation([CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]jobId.getLocation() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]serviceOptions.getLocation() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getLocation() : [CtInvocationImpl][CtVariableReadImpl]jobId.getLocation());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.GetQueryResultsResponse results = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.GetQueryResultsResponse>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.GetQueryResultsResponse call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serviceOptions.getBigQueryRpcV2().getQueryResults([CtInvocationImpl][CtVariableReadImpl]completeJobId.getProject(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getJob(), [CtInvocationImpl][CtVariableReadImpl]completeJobId.getLocation(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtVariableReadImpl]serviceOptions.getClock());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.TableSchema schemaPb = [CtInvocationImpl][CtVariableReadImpl]results.getSchema();
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.common.collect.ImmutableList.Builder<[CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryError> errors = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.builder();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getErrors() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.ErrorProto error : [CtInvocationImpl][CtVariableReadImpl]results.getErrors()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]errors.add([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryError.fromPb([CtVariableReadImpl]error));
                }
            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.QueryResponse.newBuilder().setCompleted([CtInvocationImpl][CtVariableReadImpl]results.getJobComplete()).setSchema([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]schemaPb == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.Schema.fromPb([CtVariableReadImpl]schemaPb)).setTotalRows([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]results.getTotalRows() == [CtLiteralImpl]null ? [CtLiteralImpl]0 : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]results.getTotalRows().longValue()).setErrors([CtInvocationImpl][CtVariableReadImpl]errors.build()).build();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableDataWriteChannel writer([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.WriteChannelConfiguration writeChannelConfiguration) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]writer([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.JobId.of(), [CtVariableReadImpl]writeChannelConfiguration);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.TableDataWriteChannel writer([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.JobId jobId, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.WriteChannelConfiguration writeChannelConfiguration) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.cloud.bigquery.TableDataWriteChannel([CtInvocationImpl]getOptions(), [CtInvocationImpl][CtVariableReadImpl]jobId.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()), [CtInvocationImpl][CtVariableReadImpl]writeChannelConfiguration.setProjectId([CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.Policy getIamPolicy([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]com.google.cloud.bigquery.IAMOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.PolicyHelper.convertFromApiPolicy([CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Policy>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Policy call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.getIamPolicy([CtInvocationImpl][CtVariableReadImpl]completeTableId.getIAMResourceName(), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.Policy setIamPolicy([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]final [CtTypeReferenceImpl]com.google.cloud.Policy policy, [CtParameterImpl]com.google.cloud.bigquery.IAMOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
            [CtReturnImpl]return [CtInvocationImpl]com.google.cloud.bigquery.PolicyHelper.convertFromApiPolicy([CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.Policy>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.cloud.Policy call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.setIamPolicy([CtInvocationImpl][CtVariableReadImpl]completeTableId.getIAMResourceName(), [CtInvocationImpl]com.google.cloud.bigquery.PolicyHelper.convertToApiPolicy([CtVariableReadImpl]policy), [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> testIamPermissions([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.TableId tableId, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> permissions, [CtParameterImpl]com.google.cloud.bigquery.IAMOption... options) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.cloud.bigquery.TableId completeTableId = [CtInvocationImpl][CtVariableReadImpl]tableId.setProjectId([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtInvocationImpl][CtVariableReadImpl]tableId.getProject()) ? [CtInvocationImpl][CtInvocationImpl]getOptions().getProjectId() : [CtInvocationImpl][CtVariableReadImpl]tableId.getProject());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionsMap = [CtInvocationImpl]com.google.cloud.bigquery.BigQueryImpl.optionMap([CtVariableReadImpl]options);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.services.bigquery.model.TestIamPermissionsResponse response = [CtInvocationImpl]com.google.cloud.RetryHelper.runWithRetries([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]com.google.api.services.bigquery.model.TestIamPermissionsResponse>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.google.api.services.bigquery.model.TestIamPermissionsResponse call() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bigQueryRpc.testIamPermissions([CtInvocationImpl][CtVariableReadImpl]completeTableId.getIAMResourceName(), [CtVariableReadImpl]permissions, [CtVariableReadImpl]optionsMap);
                }
            }, [CtInvocationImpl][CtInvocationImpl]getOptions().getRetrySettings(), [CtTypeAccessImpl]com.google.cloud.bigquery.EXCEPTION_HANDLER, [CtInvocationImpl][CtInvocationImpl]getOptions().getClock());
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.copyOf([CtInvocationImpl][CtVariableReadImpl]response.getPermissions());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.RetryHelper.RetryHelperException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryException.translateAndThrow([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtWildcardReferenceImpl]?> optionMap([CtParameterImpl]com.google.cloud.bigquery.Option... options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.spi.v2.BigQueryRpc.Option, [CtTypeReferenceImpl]java.lang.Object> optionMap = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newEnumMap([CtFieldReadImpl]BigQueryRpc.Option.class);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Option option : [CtVariableReadImpl]options) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object prev = [CtInvocationImpl][CtVariableReadImpl]optionMap.put([CtInvocationImpl][CtVariableReadImpl]option.getRpcOption(), [CtInvocationImpl][CtVariableReadImpl]option.getValue());
            [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]prev == [CtLiteralImpl]null, [CtLiteralImpl]"Duplicate option %s", [CtVariableReadImpl]option);
        }
        [CtReturnImpl]return [CtVariableReadImpl]optionMap;
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Google LLC

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
[CtCommentImpl]// [END bigquery_update_routine]
[CtPackageDeclarationImpl]package com.example.bigquery;
[CtUnresolvedImport]import com.google.cloud.bigquery.BigQueryOptions;
[CtUnresolvedImport]import com.google.cloud.bigquery.RoutineId;
[CtUnresolvedImport]import com.google.cloud.bigquery.BigQueryException;
[CtUnresolvedImport]import com.google.cloud.bigquery.Routine;
[CtUnresolvedImport]import com.google.cloud.bigquery.BigQuery;
[CtClassImpl][CtCommentImpl]// Sample to update routine
public class UpdateRoutine {
    [CtMethodImpl]public static [CtTypeReferenceImpl]void runUpdateRoutine() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// TODO(developer): Replace these variables before running the sample.
        [CtTypeReferenceImpl]java.lang.String datasetName = [CtLiteralImpl]"MY_DATASET_NAME";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String routineName = [CtLiteralImpl]"MY_ROUTINE_NAME";
        [CtInvocationImpl]com.example.bigquery.UpdateRoutine.updateRoutine([CtVariableReadImpl]datasetName, [CtVariableReadImpl]routineName);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void updateRoutine([CtParameterImpl][CtTypeReferenceImpl]java.lang.String datasetName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String routineName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Initialize client that will be used to send requests. This client only needs to be created
            [CtCommentImpl]// once, and can be reused for multiple requests.
            [CtTypeReferenceImpl]com.google.cloud.bigquery.BigQuery bigquery = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.BigQueryOptions.getDefaultInstance().getService();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.Routine routine = [CtInvocationImpl][CtVariableReadImpl]bigquery.getRoutine([CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.RoutineId.of([CtVariableReadImpl]datasetName, [CtVariableReadImpl]routineName));
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]routine.toBuilder().setBody([CtLiteralImpl]"x * 4").build().update();
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Routine updated successfully");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.BigQueryException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Routine was not updated. \n" + [CtInvocationImpl][CtVariableReadImpl]e.toString());
        }
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2020 Stichting Akvo (Akvo Foundation)

 This file is part of Akvo FLOW.

 Akvo FLOW is free software: you can redistribute it and modify it under the terms of
 the GNU Affero General Public License (AGPL) as published by the Free Software Foundation,
 either version 3 of the License or any later version.

 Akvo FLOW is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License included below for more details.

 The full license text can also be seen at <http://www.gnu.org/licenses/agpl.html>.
 */
[CtPackageDeclarationImpl]package org.akvo.gae.remoteapi;
[CtUnresolvedImport]import com.google.appengine.api.datastore.Query.FilterPredicate;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.google.appengine.api.datastore.Query.FilterOperator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.google.appengine.api.datastore.*;
[CtImportImpl]import java.util.Date;
[CtClassImpl][CtCommentImpl]/* - For ech SurveyAssignment.deviceIds should have a DataPointAssigment entity related, when missing create a new one with dataPointsIds=["0"] */
public class UnifyDataPointAssignment implements [CtTypeReferenceImpl]java.lang.Process {
    [CtMethodImpl]private [CtTypeReferenceImpl]org.akvo.gae.remoteapi.Entity getDataPointAssignment([CtParameterImpl][CtTypeReferenceImpl]DatastoreService ds, [CtParameterImpl][CtTypeReferenceImpl]long deviceId, [CtParameterImpl][CtTypeReferenceImpl]long surveyAssignmentId, [CtParameterImpl][CtTypeReferenceImpl]long surveyId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Query.Filter f1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.appengine.api.datastore.Query.FilterPredicate([CtLiteralImpl]"deviceId", [CtFieldReadImpl]com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, [CtVariableReadImpl]deviceId);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Query.Filter f2 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.appengine.api.datastore.Query.FilterPredicate([CtLiteralImpl]"surveyAssignmentId", [CtFieldReadImpl]com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, [CtVariableReadImpl]surveyAssignmentId);
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]Query.Filter f3 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.appengine.api.datastore.Query.FilterPredicate([CtLiteralImpl]"surveyId", [CtFieldReadImpl]com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, [CtVariableReadImpl]surveyId);
            [CtLocalVariableImpl][CtTypeReferenceImpl]Query q = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Query([CtLiteralImpl]"DataPointAssignment");
            [CtInvocationImpl][CtVariableReadImpl]q.setFilter([CtInvocationImpl][CtTypeAccessImpl]Query.CompositeFilterOperator.and([CtVariableReadImpl]f1, [CtVariableReadImpl]f2, [CtVariableReadImpl]f3));
            [CtLocalVariableImpl][CtTypeReferenceImpl]PreparedQuery pq = [CtInvocationImpl][CtVariableReadImpl]ds.prepare([CtVariableReadImpl]q);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]pq.asSingleEntity();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Error([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error DataPointAssignment duplicated! deviceId: " + [CtVariableReadImpl]deviceId) + [CtLiteralImpl]" surveyAssignmentId: ") + [CtVariableReadImpl]surveyAssignmentId) + [CtLiteralImpl]" surveyId: ") + [CtVariableReadImpl]surveyId);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assignEntityProp([CtParameterImpl][CtTypeReferenceImpl]Entity origin, [CtParameterImpl][CtTypeReferenceImpl]Entity target, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prop) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]target.setProperty([CtVariableReadImpl]prop, [CtInvocationImpl][CtVariableReadImpl]origin.getProperty([CtVariableReadImpl]prop));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]DatastoreService ds, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] args) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Query q = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Query([CtLiteralImpl]"SurveyAssignment");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]PreparedQuery pq = [CtInvocationImpl][CtVariableReadImpl]ds.prepare([CtVariableReadImpl]q);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Entity> toBeCreated = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Processing SurveyAssignments");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Entity sl : [CtInvocationImpl][CtVariableReadImpl]pq.asIterable([CtInvocationImpl][CtTypeAccessImpl]FetchOptions.Builder.withChunkSize([CtLiteralImpl]500))) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long surveyAssignmentId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sl.getKey().getId();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> deviceIds = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long>) ([CtVariableReadImpl]sl.getProperty([CtLiteralImpl]"deviceIds")));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long surveyId = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Long) ([CtVariableReadImpl]sl.getProperty([CtLiteralImpl]"surveyId")));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deviceIds == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long deviceId : [CtVariableReadImpl]deviceIds) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]Entity dataPointAssignement = [CtInvocationImpl]getDataPointAssignment([CtVariableReadImpl]ds, [CtVariableReadImpl]deviceId, [CtVariableReadImpl]surveyAssignmentId, [CtVariableReadImpl]surveyId);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataPointAssignement == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Entity newDataPointAssignment = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Entity([CtLiteralImpl]"DataPointAssignment");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> dataPointIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Long>();
                    [CtInvocationImpl][CtVariableReadImpl]dataPointIds.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Long([CtLiteralImpl]0));
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"dataPointIds", [CtVariableReadImpl]dataPointIds);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"deviceId", [CtVariableReadImpl]deviceId);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"surveyId", [CtVariableReadImpl]surveyId);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"surveyAssignmentId", [CtVariableReadImpl]surveyAssignmentId);
                    [CtInvocationImpl]assignEntityProp([CtVariableReadImpl]sl, [CtVariableReadImpl]newDataPointAssignment, [CtLiteralImpl]"createUserId");
                    [CtInvocationImpl]assignEntityProp([CtVariableReadImpl]sl, [CtVariableReadImpl]newDataPointAssignment, [CtLiteralImpl]"lastUpdateUserId");
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"ancestorIds", [CtLiteralImpl]null);
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Date date = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"createdDateTime", [CtVariableReadImpl]date);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"lastUpdateDateTime", [CtVariableReadImpl]date);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"lastUpdateUserId", [CtLiteralImpl]0);
                    [CtInvocationImpl][CtVariableReadImpl]newDataPointAssignment.setProperty([CtLiteralImpl]"createUserId", [CtLiteralImpl]0);
                    [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"DataPointAssignment to be created: " + [CtVariableReadImpl]newDataPointAssignment);
                    [CtInvocationImpl][CtVariableReadImpl]toBeCreated.add([CtVariableReadImpl]newDataPointAssignment);
                }
            }
        }
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"DataPointAssignment that should be create: " + [CtInvocationImpl][CtVariableReadImpl]toBeCreated.size());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]toBeCreated.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ds.put([CtVariableReadImpl]toBeCreated);
        }
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   Licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License.
   A copy of the License is located at

       http://www.apache.org/licenses/LICENSE-2.0

   or in the "license" file accompanying this file. This file is distributed
   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
   express or implied. See the License for the specific language governing
   permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.sql.expression.datetime;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.TIME;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getTimestampValue;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDateValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.STRING;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.define;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimeValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.DATETIME;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.TIMESTAMP;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver;
[CtUnresolvedImport]import lombok.experimental.UtilityClass;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getTimeValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDateValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.INTEGER;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.impl;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.DATE;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.nullMissingHandling;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYOFMONTH;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getStringValue;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDatetimeValue;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDatetimeValue;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimestampValue;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.sql.data.model.ExprIntegerValue;
[CtClassImpl][CtJavaDocImpl]/**
 * The definition of date and time functions.
 * 1) have the clear interface for function define.
 * 2) the implementation should rely on ExprValue.
 */
[CtAnnotationImpl]@lombok.experimental.UtilityClass
public class DateTimeFunction {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Register Date and Time Functions.
     *
     * @param repository
     * 		{@link BuiltinFunctionRepository}.
     */
    public [CtTypeReferenceImpl]void register([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository repository) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]repository.register([CtInvocationImpl]date());
        [CtInvocationImpl][CtVariableReadImpl]repository.register([CtInvocationImpl]dayOfMonth());
        [CtInvocationImpl][CtVariableReadImpl]repository.register([CtInvocationImpl]time());
        [CtInvocationImpl][CtVariableReadImpl]repository.register([CtInvocationImpl]timestamp());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts the date part of a date and time value.
     * Also to construct a date type. The supported signatures:
     * STRING/DATE/DATETIME/TIMESTAMP -> DATE
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver date() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]FunctionDSL.define([CtInvocationImpl][CtTypeAccessImpl]BuiltinFunctionName.DATE.getName(), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprDate), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.STRING), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprDate), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprDate), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATETIME), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprDate), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * DAYOFMONTH(DATE). return the day of the month (1-31).
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver dayOfMonth() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]FunctionDSL.define([CtInvocationImpl][CtTypeAccessImpl]BuiltinFunctionName.DAYOFMONTH.getName(), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprDayOfMonth), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.INTEGER, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts the time part of a date and time value.
     * Also to construct a time type. The supported signatures:
     * STRING/DATE/DATETIME/TIME/TIMESTAMP -> TIME
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver time() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]FunctionDSL.define([CtInvocationImpl][CtTypeAccessImpl]BuiltinFunctionName.TIME.getName(), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTime), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.STRING), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTime), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTime), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATETIME), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTime), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTime), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIME, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extracts the timestamp of a date and time value.
     * Also to construct a date type. The supported signatures:
     * STRING/DATE/DATETIME/TIMESTAMP -> DATE
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver timestamp() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]FunctionDSL.define([CtInvocationImpl][CtTypeAccessImpl]BuiltinFunctionName.TIMESTAMP.getName(), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTimestamp), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.STRING), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTimestamp), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATE), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTimestamp), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DATETIME), [CtInvocationImpl]FunctionDSL.impl([CtInvocationImpl]FunctionDSL.nullMissingHandling([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.DateTimeFunction::exprTimestamp), [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.expression.datetime.TIMESTAMP));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Date implementation for ExprValue.
     *
     * @param exprValue
     * 		ExprValue of Date type or String type.
     * @return ExprValue.
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprDate([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exprValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprDateValue([CtInvocationImpl]getStringValue([CtVariableReadImpl]exprValue));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprDateValue([CtInvocationImpl]getDateValue([CtVariableReadImpl]exprValue));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Day of Month implementation for ExprValue.
     *
     * @param date
     * 		ExprValue of Date type.
     * @return ExprValue.
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprDayOfMonth([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue date) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprIntegerValue([CtInvocationImpl][CtInvocationImpl]getDateValue([CtVariableReadImpl]date).getMonthValue());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Time implementation for ExprValue.
     *
     * @param exprValue
     * 		ExprValue of Time type or String.
     * @return ExprValue.
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprTime([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exprValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimeValue([CtInvocationImpl]getStringValue([CtVariableReadImpl]exprValue));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimeValue([CtInvocationImpl]getTimeValue([CtVariableReadImpl]exprValue));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Timestamp implementation for ExprValue.
     *
     * @param exprValue
     * 		ExprValue of Timestamp type or String type.
     * @return ExprValue.
     */
    private [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprTimestamp([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue exprValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exprValue instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimestampValue([CtInvocationImpl]getStringValue([CtVariableReadImpl]exprValue));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimestampValue([CtInvocationImpl]getTimestampValue([CtVariableReadImpl]exprValue));
        }
    }
}
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
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.sql.executor.format;
[CtImportImpl]import java.time.ZonedDateTime;
[CtImportImpl]import java.time.format.DateTimeFormatter;
[CtImportImpl]import java.time.Instant;
[CtImportImpl]import java.time.ZoneId;
[CtEnumImpl][CtCommentImpl]// import java.util.TimeZone;
public enum DateFormat {

    [CtEnumValueImpl][CtCommentImpl]// Special cases that are parsed separately
    DATE_OPTIONAL_TIME([CtLiteralImpl]""),
    [CtEnumValueImpl]EPOCH_MILLIS([CtLiteralImpl]""),
    [CtEnumValueImpl]EPOCH_SECOND([CtLiteralImpl]""),
    [CtEnumValueImpl]BASIC_DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_DATE),
    [CtEnumValueImpl]BASIC_DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_ORDINAL_DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_ORDINAL_DATE),
    [CtEnumValueImpl]BASIC_ORDINAL_DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_ORDINAL_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_ORDINAL_DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_ORDINAL_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_T_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_T_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_WEEK_DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_WEEK_DATE),
    [CtEnumValueImpl]BASIC_WEEK_DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_WEEK_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]BASIC_WEEK_DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]BASIC_WEEK_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]BASIC_TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZ),
    [CtEnumValueImpl]DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE),
    [CtEnumValueImpl]DATE_HOUR([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]HOUR),
    [CtEnumValueImpl]DATE_HOUR_MINUTE([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]HOUR_MINUTE),
    [CtEnumValueImpl]DATE_HOUR_MINUTE_SECOND([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME),
    [CtEnumValueImpl]DATE_HOUR_MINUTE_SECOND_FRACTION([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS),
    [CtEnumValueImpl]DATE_HOUR_MINUTE_SECOND_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS),
    [CtEnumValueImpl]DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]HOUR([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]HOUR),
    [CtEnumValueImpl]HOUR_MINUTE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]HOUR_MINUTE),
    [CtEnumValueImpl]HOUR_MINUTE_SECOND([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME),
    [CtEnumValueImpl]HOUR_MINUTE_SECOND_FRACTION([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS),
    [CtEnumValueImpl]HOUR_MINUTE_SECOND_MILLIS([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS),
    [CtEnumValueImpl]ORDINAL_DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]ORDINAL_DATE),
    [CtEnumValueImpl]ORDINAL_DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]ORDINAL_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]ORDINAL_DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]ORDINAL_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]TIME_NO_MILLIS([CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]T_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]T_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]WEEK_DATE([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEK_DATE),
    [CtEnumValueImpl]WEEK_DATE_TIME([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEK_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]MILLIS) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl]WEEK_DATE_TIME_NO_MILLIS([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEK_DATE + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]T) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TIME) + [CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Time.[CtFieldReferenceImpl]TZZ),
    [CtEnumValueImpl][CtCommentImpl]// Note: input mapping is "weekyear", but output value is "week_year"
    WEEK_YEAR([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEKYEAR),
    [CtEnumValueImpl]WEEKYEAR_WEEK([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEKYEAR_WEEK),
    [CtEnumValueImpl]WEEKYEAR_WEEK_DAY([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]WEEK_DATE),
    [CtEnumValueImpl]YEAR([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]YEAR),
    [CtEnumValueImpl]YEAR_MONTH([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]YEAR_MONTH),
    [CtEnumValueImpl]YEAR_MONTH_DAY([CtFieldReadImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.sql.executor.format.DateFormat.Date.[CtFieldReferenceImpl]DATE);
    [CtClassImpl]private static class Date {
        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String BASIC_DATE = [CtLiteralImpl]"yyyyMMdd";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String BASIC_ORDINAL_DATE = [CtLiteralImpl]"yyyyDDD";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String BASIC_WEEK_DATE = [CtLiteralImpl]"YYYY'W'wwu";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String DATE = [CtLiteralImpl]"yyyy-MM-dd";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String ORDINAL_DATE = [CtLiteralImpl]"yyyy-DDD";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String YEAR = [CtLiteralImpl]"yyyy";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String YEAR_MONTH = [CtLiteralImpl]"yyyy-MM";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String WEEK_DATE = [CtLiteralImpl]"YYYY-'W'ww-u";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String WEEKYEAR = [CtLiteralImpl]"YYYY";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String WEEKYEAR_WEEK = [CtLiteralImpl]"YYYY-'W'ww";
    }

    [CtClassImpl]private static class Time {
        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String T = [CtLiteralImpl]"'T'";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String BASIC_TIME = [CtLiteralImpl]"HHmmss";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String TIME = [CtLiteralImpl]"HH:mm:ss";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String HOUR = [CtLiteralImpl]"HH";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String HOUR_MINUTE = [CtLiteralImpl]"HH:mm";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String MILLIS = [CtLiteralImpl]".SSS";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String TZ = [CtLiteralImpl]"Z";

        [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String TZZ = [CtLiteralImpl]"XX";
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String formatString;

    [CtConstructorImpl]DateFormat([CtParameterImpl][CtTypeReferenceImpl]java.lang.String formatString) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.formatString = [CtVariableReadImpl]formatString;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFormatString() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]formatString;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String nameLowerCase() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]name().toLowerCase();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getFormattedDate([CtParameterImpl][CtTypeReferenceImpl]java.util.Date date, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dateFormat) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.Instant instant = [CtInvocationImpl][CtVariableReadImpl]date.toInstant();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.ZonedDateTime zdt = [CtInvocationImpl][CtTypeAccessImpl]java.time.ZonedDateTime.ofInstant([CtVariableReadImpl]instant, [CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.of([CtLiteralImpl]"Etc/UTC"));
        [CtReturnImpl][CtCommentImpl]// return zdt.format(DateTimeFormatter.ofPattern(dateFormat));
        return [CtInvocationImpl][CtVariableReadImpl]zdt.format([CtInvocationImpl][CtTypeAccessImpl]java.time.format.DateTimeFormatter.ofPattern([CtVariableReadImpl]dateFormat));
        [CtCommentImpl]// return DateTimeFormatter.ofLocalizedDateTime().format(date.toInstant());
        [CtCommentImpl]// SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        [CtCommentImpl]// formatter.applyLocalizedPattern(dateFormat);
        [CtCommentImpl]// formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        [CtCommentImpl]// return formatter.format(date);
        [CtCommentImpl]// formatter.
    }
}
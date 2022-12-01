[CompilationUnitImpl][CtCommentImpl]/* CDDL HEADER START

The contents of this file are subject to the terms of the
Common Development and Distribution License (the "License").
You may not use this file except in compliance with the License.

See LICENSE.txt included in this distribution for the specific
language governing permissions and limitations under the License.

When distributing Covered Code, include this CDDL HEADER in each
file and include the License file at LICENSE.txt.
If applicable, add the following below this CDDL HEADER, with the
fields enclosed by brackets "[]" replaced with your own identifying
information: Portions Copyright [yyyy] [name of copyright owner]

CDDL HEADER END
 */
[CtPackageDeclarationImpl]package org.opengrok.indexer.util;
[CtUnresolvedImport]import static org.opengrok.indexer.util.StringUtils.getReadableTime;
[CtImportImpl]import java.util.logging.Level;
[CtImportImpl]import java.util.logging.Logger;
[CtClassImpl]public class Statistics {
    [CtFieldImpl]private final [CtTypeReferenceImpl]long startTime;

    [CtConstructorImpl]public Statistics() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]startTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * log a message along with how much time it took since the constructor was called.
     *
     * @param logger
     * 		logger instance
     * @param logLevel
     * 		log level
     * @param msg
     * 		message string
     */
    public [CtTypeReferenceImpl]void report([CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Logger logger, [CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Level logLevel, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long stopTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String time_str = [CtInvocationImpl][CtTypeAccessImpl]org.opengrok.indexer.util.StringUtils.getReadableTime([CtBinaryOperatorImpl][CtVariableReadImpl]stopTime - [CtFieldReadImpl]startTime);
        [CtInvocationImpl][CtVariableReadImpl]logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtBinaryOperatorImpl][CtVariableReadImpl]msg + [CtLiteralImpl]" (took {0})", [CtVariableReadImpl]time_str);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * log a message along with how much time it took since the constructor was called.
     * The log level is Level.INFO.
     *
     * @param logger
     * 		logger instance
     * @param msg
     * 		message string
     */
    public [CtTypeReferenceImpl]void report([CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Logger logger, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtInvocationImpl]report([CtVariableReadImpl]logger, [CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtVariableReadImpl]msg);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * log a message along with how much time and memory it took since the constructor was called.
     *
     * @param logger
     */
    public [CtTypeReferenceImpl]void report([CtParameterImpl][CtTypeReferenceImpl]java.util.logging.Logger logger) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long stopTime = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtFieldReadImpl]startTime;
        [CtInvocationImpl][CtVariableReadImpl]logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtLiteralImpl]"Total time: {0}", [CtInvocationImpl]org.opengrok.indexer.util.StringUtils.getReadableTime([CtVariableReadImpl]stopTime));
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.gc();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Runtime r = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long mb = [CtBinaryOperatorImpl][CtLiteralImpl]1024L * [CtLiteralImpl]1024;
        [CtInvocationImpl][CtVariableReadImpl]logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtLiteralImpl]"Final Memory: {0}M/{1}M", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]r.totalMemory() - [CtInvocationImpl][CtVariableReadImpl]r.freeMemory()) / [CtVariableReadImpl]mb, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]r.totalMemory() / [CtVariableReadImpl]mb });
    }
}
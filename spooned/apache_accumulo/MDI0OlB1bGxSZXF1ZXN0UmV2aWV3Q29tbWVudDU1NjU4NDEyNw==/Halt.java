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
[CtPackageDeclarationImpl]package org.apache.accumulo.core.util;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import static org.apache.accumulo.fate.util.UtilWaitThread.sleepUninterruptibly;
[CtUnresolvedImport]import org.apache.accumulo.core.util.threads.Threads;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl]public class Halt {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.accumulo.core.util.Halt.class);

    [CtMethodImpl]public static [CtTypeReferenceImpl]void halt([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// ACCUMULO-3651 Changed level to error and added FATAL to message for slf4j compatibility
        org.apache.accumulo.core.util.Halt.halt([CtLiteralImpl]0, [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.util.Halt.log.error([CtLiteralImpl]"FATAL {}", [CtVariableReadImpl]msg);
            }
        });
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void halt([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtTypeReferenceImpl]int status) [CtBlockImpl]{
        [CtInvocationImpl]org.apache.accumulo.core.util.Halt.halt([CtVariableReadImpl]status, [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.accumulo.core.util.Halt.log.error([CtLiteralImpl]"FATAL {}", [CtVariableReadImpl]msg);
            }
        });
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void halt([CtParameterImpl]final [CtTypeReferenceImpl]int status, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// give ourselves a little time to try and do something
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.accumulo.core.util.threads.Threads.createThread([CtLiteralImpl]"Halt Thread", [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                    [CtInvocationImpl]sleepUninterruptibly([CtLiteralImpl]100, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MILLISECONDS);
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().halt([CtVariableReadImpl]status);
                }
            }).start();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]runnable != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]runnable.run();

            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().halt([CtVariableReadImpl]status);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// In case something else decides to throw a Runtime exception
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Runtime.getRuntime().halt([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        }
    }
}
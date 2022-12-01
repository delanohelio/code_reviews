[CompilationUnitImpl][CtCommentImpl]/* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright (c) 2020 Payara Foundation and/or its affiliates. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 2 only ("GPL") or the Common Development
and Distribution License("CDDL") (collectively, the "License").  You
may not use this file except in compliance with the License.  You can
obtain a copy of the License at
https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
or packager/legal/LICENSE.txt.  See the License for the specific
language governing permissions and limitations under the License.

When distributing the software, include this License Header Notice in each
file and include the License file at packager/legal/LICENSE.txt.

GPL Classpath Exception:
Oracle designates this particular file as subject to the "Classpath"
exception as provided by Oracle in the GPL Version 2 section of the License
file that accompanied this code.

Modifications:
If applicable, add the following below the License Header, with the fields
enclosed by brackets [] replaced by your own identifying information:
"Portions Copyright [year] [name of copyright owner]"

Contributor(s):
If you wish your version of this file to be governed by only the CDDL or
only the GPL Version 2, indicate your decision by adding "[Contributor]
elects to include this software in this distribution under the [CDDL or GPL
Version 2] license."  If you don't indicate a single choice of license, a
recipient has the option to distribute your version of this file under
either the CDDL, the GPL Version 2 or to extend the choice of license to
its licensees as provided above.  However, if you add GPL Version 2 code
and therefore, elected the GPL Version 2 license, then the option applies
only if the new code is made subject to such option by the copyright
holder.
 */
[CtPackageDeclarationImpl]package fish.payara.jdbc;
[CtUnresolvedImport]import org.glassfish.api.jdbc.SQLTraceListener;
[CtUnresolvedImport]import org.glassfish.api.jdbc.SQLTraceRecord;
[CtUnresolvedImport]import org.glassfish.internal.api.Globals;
[CtUnresolvedImport]import org.glassfish.api.jdbc.SQLTraceStore;
[CtClassImpl][CtJavaDocImpl]/**
 * An adapter between the {@link SQLTraceListener} abstraction that is registered with implementation class as key and a
 * managed instance of the {@link SQLTraceStore}.
 *
 * @author Jan Bernitt
 */
public class SQLTraceStoreAdapter implements [CtTypeReferenceImpl]org.glassfish.api.jdbc.SQLTraceListener {
    [CtFieldImpl]private static [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]fish.payara.jdbc.SQLQuery> currentQuery = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.glassfish.api.jdbc.SQLTraceStore store;

    [CtConstructorImpl]public SQLTraceStoreAdapter() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.store = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.glassfish.internal.api.Globals.getDefaultHabitat().getService([CtFieldReadImpl]org.glassfish.api.jdbc.SQLTraceStore.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void sqlTrace([CtParameterImpl][CtTypeReferenceImpl]org.glassfish.api.jdbc.SQLTraceRecord record) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]record != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]record.getMethodName()) {
                [CtCaseImpl][CtCommentImpl]// these calls capture a query string
                case [CtLiteralImpl]"nativeSQL" :
                [CtCaseImpl]case [CtLiteralImpl]"prepareCall" :
                [CtCaseImpl]case [CtLiteralImpl]"prepareStatement" :
                [CtCaseImpl]case [CtLiteralImpl]"addBatch" :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// acquire the SQL
                        [CtTypeReferenceImpl]fish.payara.jdbc.SQLQuery query = [CtInvocationImpl][CtFieldReadImpl]fish.payara.jdbc.SQLTraceStoreAdapter.currentQuery.get();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]query == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]query = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fish.payara.jdbc.SQLQuery();
                            [CtInvocationImpl][CtFieldReadImpl]fish.payara.jdbc.SQLTraceStoreAdapter.currentQuery.set([CtVariableReadImpl]query);
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]record.getParams() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]record.getParams().length > [CtLiteralImpl]0))[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]query.addSQL([CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]record.getParams()[[CtLiteralImpl]0])));

                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtLiteralImpl]"execute" :
                [CtCaseImpl]case [CtLiteralImpl]"executeQuery" :
                [CtCaseImpl]case [CtLiteralImpl]"executeUpdate" :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// acquire the SQL
                        [CtTypeReferenceImpl]fish.payara.jdbc.SQLQuery query = [CtInvocationImpl][CtFieldReadImpl]fish.payara.jdbc.SQLTraceStoreAdapter.currentQuery.get();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]query == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]query = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fish.payara.jdbc.SQLQuery();
                            [CtInvocationImpl][CtFieldReadImpl]fish.payara.jdbc.SQLTraceStoreAdapter.currentQuery.set([CtVariableReadImpl]query);
                        }[CtCommentImpl]// these can all run the SQL and contain SQL

                        [CtIfImpl][CtCommentImpl]// see if we have more SQL
                        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]record.getParams() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]record.getParams().length > [CtLiteralImpl]0)) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// gather the SQL
                            [CtVariableReadImpl]query.addSQL([CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]record.getParams()[[CtLiteralImpl]0])));
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]store != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]store.trace([CtVariableReadImpl]record, [CtInvocationImpl][CtVariableReadImpl]query.getSQL());
                        }
                        [CtInvocationImpl][CtCommentImpl]// clean the thread local
                        [CtFieldReadImpl]fish.payara.jdbc.SQLTraceStoreAdapter.currentQuery.set([CtLiteralImpl]null);
                        [CtBreakImpl]break;
                    }
            }
        }
    }
}
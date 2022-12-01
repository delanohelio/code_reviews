[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 GridGain Systems, Inc. and Contributors.

Licensed under the GridGain Community Edition License (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.ignite.internal.processors.query.oom;
[CtUnresolvedImport]import org.apache.ignite.cache.query.SqlFieldsQuery;
[CtImportImpl]import sun.reflect.generics.reflectiveObjects.NotImplementedException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.ignite.internal.util.typedef.internal.U;
[CtUnresolvedImport]import org.apache.ignite.internal.IgniteEx;
[CtUnresolvedImport]import static org.apache.ignite.IgniteSystemProperties.IGNITE_SQL_ENABLE_CONNECTION_MEMORY_QUOTA;
[CtUnresolvedImport]import org.apache.ignite.IgniteSystemProperties;
[CtUnresolvedImport]import org.h2.engine.Session;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.ignite.testframework.junits.common.GridCommonAbstractTest;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.query.SqlFieldsQueryEx;
[CtUnresolvedImport]import org.h2.result.LocalResult;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.cache.query.IgniteQueryErrorCode;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.QueryMemoryManager;
[CtUnresolvedImport]import org.apache.ignite.testframework.GridTestUtils;
[CtUnresolvedImport]import org.apache.ignite.testframework.junits.WithSystemProperty;
[CtUnresolvedImport]import org.apache.ignite.configuration.IgniteConfiguration;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.IgniteSQLException;
[CtUnresolvedImport]import static org.apache.ignite.internal.util.IgniteUtils.KB;
[CtUnresolvedImport]import org.h2.result.LocalResultImpl;
[CtUnresolvedImport]import static org.apache.ignite.internal.util.IgniteUtils.MB;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.H2LocalResultFactory;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult;
[CtUnresolvedImport]import org.h2.expression.Expression;
[CtUnresolvedImport]import org.apache.ignite.cache.query.FieldsQueryCursor;
[CtClassImpl][CtJavaDocImpl]/**
 * Basic test class for quotas.
 */
[CtAnnotationImpl]@org.apache.ignite.testframework.junits.WithSystemProperty(key = [CtFieldReadImpl]org.apache.ignite.IgniteSystemProperties.IGNITE_SQL_ENABLE_CONNECTION_MEMORY_QUOTA, value = [CtLiteralImpl]"true")
public abstract class AbstractQueryMemoryTrackerSelfTest extends [CtTypeReferenceImpl]org.apache.ignite.testframework.junits.common.GridCommonAbstractTest {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Row count.
     */
    static final [CtTypeReferenceImpl]int SMALL_TABLE_SIZE = [CtLiteralImpl]1000;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Row count.
     */
    static final [CtTypeReferenceImpl]int BIG_TABLE_SIZE = [CtLiteralImpl]10000;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Query local results.
     */
    static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult> localResults = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.synchronizedList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());

    [CtFieldImpl][CtJavaDocImpl]/**
     * Query memory limit.
     */
    protected [CtTypeReferenceImpl]long maxMem;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Node client mode flag.
     */
    private [CtTypeReferenceImpl]boolean client;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Flag whether to use jdbc2 node config with global quota.
     */
    protected [CtTypeReferenceImpl]boolean useJdbcV2GlobalQuotaCfg;

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void beforeTestsStarted() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.beforeTestsStarted();
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtTypeAccessImpl]IgniteSystemProperties.IGNITE_H2_LOCAL_RESULT_FACTORY, [CtInvocationImpl][CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.TestH2LocalResultFactory.class.getName());
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtTypeAccessImpl]IgniteSystemProperties.IGNITE_SQL_MEMORY_RESERVATION_BLOCK_SIZE, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtTypeAccessImpl]IgniteUtils.KB));
        [CtInvocationImpl]startGrid([CtLiteralImpl]0);
        [CtIfImpl]if ([CtInvocationImpl]startClient()) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]client = [CtLiteralImpl]true;
            [CtInvocationImpl]startGrid([CtLiteralImpl]1);
        }
        [CtInvocationImpl]createSchema();
        [CtInvocationImpl]populateData();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void afterTestsStopped() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]stopAllGrids();
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.clearProperty([CtTypeAccessImpl]IgniteSystemProperties.IGNITE_SQL_MEMORY_RESERVATION_BLOCK_SIZE);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.clearProperty([CtTypeAccessImpl]IgniteSystemProperties.IGNITE_H2_LOCAL_RESULT_FACTORY);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void beforeTest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.beforeTest();
        [CtAssignmentImpl][CtFieldWriteImpl]maxMem = [CtFieldReadImpl]IgniteUtils.MB;
        [CtAssignmentImpl][CtFieldWriteImpl]useJdbcV2GlobalQuotaCfg = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult res : [CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults)[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]res.memoryTracker() != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]res.memoryTracker().close();


        [CtInvocationImpl][CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults.clear();
        [CtInvocationImpl]resetMemoryManagerState([CtInvocationImpl]grid([CtLiteralImpl]0));
        [CtIfImpl]if ([CtInvocationImpl]startClient())[CtBlockImpl]
            [CtInvocationImpl]resetMemoryManagerState([CtInvocationImpl]grid([CtLiteralImpl]1));

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void afterTest() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult res : [CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults)[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]res.memoryTracker() != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]res.memoryTracker().close();


        [CtInvocationImpl]checkMemoryManagerState([CtInvocationImpl]grid([CtLiteralImpl]0));
        [CtIfImpl]if ([CtInvocationImpl]startClient())[CtBlockImpl]
            [CtInvocationImpl]checkMemoryManagerState([CtInvocationImpl]grid([CtLiteralImpl]1));

        [CtInvocationImpl][CtSuperAccessImpl]super.afterTest();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if all reserved memory was correctly released.
     *
     * @param node
     * 		Node.
     */
    private [CtTypeReferenceImpl]void checkMemoryManagerState([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx node) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.QueryMemoryManager memMgr = [CtInvocationImpl]memoryManager([CtVariableReadImpl]node);
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.testframework.GridTestUtils.waitForCondition([CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]memMgr.reserved() == [CtLiteralImpl]0, [CtLiteralImpl]5000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long memReserved = [CtInvocationImpl][CtVariableReadImpl]memMgr.reserved();
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"Potential memory leak in SQL engine: reserved=" + [CtVariableReadImpl]memReserved, [CtLiteralImpl]0, [CtVariableReadImpl]memReserved);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Resets node query memory manager state.
     *
     * @param grid
     * 		Node.
     */
    private [CtTypeReferenceImpl]void resetMemoryManagerState([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx grid) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.QueryMemoryManager memoryManager = [CtInvocationImpl]memoryManager([CtVariableReadImpl]grid);
        [CtIfImpl][CtCommentImpl]// Reset memory manager.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]memoryManager.reserved() > [CtLiteralImpl]0)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]memoryManager.release([CtInvocationImpl][CtVariableReadImpl]memoryManager.reserved());

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return node query memory manager.
     *
     * @param node
     * 		Node.
     * @return Query memory manager.
     */
    private [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.QueryMemoryManager memoryManager([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.IgniteEx node) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing h2 = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.context().query().getIndexing()));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]h2.memoryManager();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]org.apache.ignite.configuration.IgniteConfiguration getConfiguration([CtParameterImpl][CtTypeReferenceImpl]java.lang.String igniteInstanceName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtSuperAccessImpl]super.getConfiguration([CtVariableReadImpl]igniteInstanceName).setClientMode([CtFieldReadImpl]client).setSqlOffloadingEnabled([CtLiteralImpl]false).setSqlGlobalMemoryQuota([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtInvocationImpl]globalQuotaSize()));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    protected [CtTypeReferenceImpl]long globalQuotaSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]10L * [CtFieldReadImpl]IgniteUtils.MB;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    private [CtTypeReferenceImpl]void populateData() [CtBlockImpl]{
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.SMALL_TABLE_SIZE; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i)[CtBlockImpl]
            [CtInvocationImpl]execSql([CtLiteralImpl]"insert into T VALUES (?, ?, ?)", [CtVariableReadImpl]i, [CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString());

        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.BIG_TABLE_SIZE; [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i)[CtBlockImpl]
            [CtInvocationImpl]execSql([CtLiteralImpl]"insert into K VALUES (?, ?, ?, ?, ?)", [CtVariableReadImpl]i, [CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtVariableReadImpl]i % [CtLiteralImpl]100, [CtBinaryOperatorImpl][CtVariableReadImpl]i % [CtLiteralImpl]100, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString());

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    protected [CtTypeReferenceImpl]void createSchema() [CtBlockImpl]{
        [CtInvocationImpl]execSql([CtLiteralImpl]"create table T (id int primary key, ref_key int, name varchar)");
        [CtInvocationImpl]execSql([CtLiteralImpl]"create table K (id int primary key, indexed int, grp int, grp_indexed int, name varchar)");
        [CtInvocationImpl]execSql([CtLiteralImpl]"create index K_IDX on K(indexed)");
        [CtInvocationImpl]execSql([CtLiteralImpl]"create index K_GRP_IDX on K(grp_indexed)");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sql
     * 		SQL query
     * @return Results set.
     */
    protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>> execQuery([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]boolean lazy) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.cache.query.FieldsQueryCursor<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>> cursor = [CtInvocationImpl]query([CtVariableReadImpl]sql, [CtVariableReadImpl]lazy)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]cursor.getAll();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sql
     * 		SQL query
     * @return Results set.
     */
    [CtTypeReferenceImpl]org.apache.ignite.cache.query.FieldsQueryCursor<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>> query([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]boolean lazy) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean localQry = [CtInvocationImpl]isLocal();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]grid([CtConditionalImpl][CtInvocationImpl]startClient() ? [CtLiteralImpl]1 : [CtLiteralImpl]0).context().query().querySqlFields([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.cache.query.SqlFieldsQueryEx([CtVariableReadImpl]sql, [CtLiteralImpl]null).setLocal([CtVariableReadImpl]localQry).setMaxMemory([CtFieldReadImpl]maxMem).setLazy([CtVariableReadImpl]lazy).setEnforceJoinOrder([CtLiteralImpl]true).setPageSize([CtLiteralImpl]100), [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Local query flag.
     */
    protected abstract [CtTypeReferenceImpl]boolean isLocal();

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return {@code True} if client node should be started, {@code False} otherwise.
     */
    protected [CtTypeReferenceImpl]boolean startClient() [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl]isLocal();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sql
     * 		SQL query
     * @param args
     * 		Query parameters.
     */
    protected [CtTypeReferenceImpl]void execSql([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl]java.lang.Object... args) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]grid([CtLiteralImpl]0).context().query().querySqlFields([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.cache.query.SqlFieldsQuery([CtVariableReadImpl]sql).setArgs([CtVariableReadImpl]args), [CtLiteralImpl]false).getAll();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sql
     * 		SQL query.
     * @param lazy
     * 		Lazy flag.
     */
    protected [CtTypeReferenceImpl]void checkQueryExpectOOM([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]boolean lazy) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.IgniteSQLException sqlEx = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.IgniteSQLException) ([CtTypeAccessImpl]org.apache.ignite.testframework.GridTestUtils.assertThrowsAnyCause([CtFieldReadImpl]log, [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl]execQuery([CtVariableReadImpl]sql, [CtVariableReadImpl]lazy);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }, [CtFieldReadImpl]org.apache.ignite.internal.processors.query.IgniteSQLException.class, [CtLiteralImpl]"SQL query run out of memory: Query quota exceeded.")));
        [CtInvocationImpl]assertNotNull([CtLiteralImpl]"SQL exception missed.", [CtVariableReadImpl]sqlEx);
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]IgniteQueryErrorCode.QUERY_OUT_OF_MEMORY, [CtInvocationImpl][CtVariableReadImpl]sqlEx.statusCode());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.processors.cache.query.IgniteQueryErrorCode.codeToSqlState([CtTypeAccessImpl]IgniteQueryErrorCode.QUERY_OUT_OF_MEMORY), [CtInvocationImpl][CtVariableReadImpl]sqlEx.sqlState());
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Local result factory for test.
     */
    public static class TestH2LocalResultFactory extends [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2LocalResultFactory {
        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.h2.result.LocalResult create([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session ses, [CtParameterImpl][CtArrayTypeReferenceImpl]org.h2.expression.Expression[] expressions, [CtParameterImpl][CtTypeReferenceImpl]int visibleColCnt, [CtParameterImpl][CtTypeReferenceImpl]boolean system) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]system)[CtBlockImpl]
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.h2.result.LocalResultImpl([CtVariableReadImpl]ses, [CtVariableReadImpl]expressions, [CtVariableReadImpl]visibleColCnt);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ses.memoryTracker() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult res = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult([CtVariableReadImpl]ses, [CtVariableReadImpl]expressions, [CtVariableReadImpl]visibleColCnt)[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onClose() [CtBlockImpl]{
                        [CtCommentImpl]// Just prevent 'rows' from being nullified for test purposes.
                    }
                };
                [CtInvocationImpl][CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults.add([CtVariableReadImpl]res);
                [CtReturnImpl]return [CtVariableReadImpl]res;
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.h2.H2ManagedLocalResult([CtVariableReadImpl]ses, [CtVariableReadImpl]expressions, [CtVariableReadImpl]visibleColCnt);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * {@inheritDoc }
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.h2.result.LocalResult create() [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]sun.reflect.generics.reflectiveObjects.NotImplementedException();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     */
    protected [CtTypeReferenceImpl]void clearResults() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.result.LocalResult res : [CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.ignite.internal.util.typedef.internal.U.closeQuiet([CtVariableReadImpl]res);

        [CtInvocationImpl][CtFieldReadImpl]org.apache.ignite.internal.processors.query.oom.AbstractQueryMemoryTrackerSelfTest.localResults.clear();
    }
}
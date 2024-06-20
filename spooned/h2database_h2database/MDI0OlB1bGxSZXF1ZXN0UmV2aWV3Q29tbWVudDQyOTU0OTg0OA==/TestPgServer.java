[CompilationUnitImpl][CtCommentImpl]/* Copyright 2004-2020 H2 Group. Multiple-Licensed under the MPL 2.0,
and the EPL 1.0 (https://h2database.com/html/license.html).
Initial Developer: H2 Group
 */
[CtPackageDeclarationImpl]package org.h2.test.unit;
[CtUnresolvedImport]import org.h2.tools.Server;
[CtImportImpl]import java.sql.Types;
[CtImportImpl]import java.sql.SQLException;
[CtImportImpl]import java.sql.Time;
[CtUnresolvedImport]import org.h2.util.DateTimeUtils;
[CtImportImpl]import java.util.concurrent.Executors;
[CtImportImpl]import java.util.Properties;
[CtImportImpl]import java.sql.Timestamp;
[CtUnresolvedImport]import org.postgresql.jdbc.PgArray;
[CtImportImpl]import java.sql.ResultSetMetaData;
[CtImportImpl]import java.util.concurrent.ExecutionException;
[CtUnresolvedImport]import org.h2.test.TestBase;
[CtImportImpl]import java.sql.ResultSet;
[CtImportImpl]import java.sql.DriverManager;
[CtUnresolvedImport]import org.h2.api.ErrorCode;
[CtImportImpl]import java.sql.Connection;
[CtUnresolvedImport]import org.h2.test.TestDb;
[CtImportImpl]import java.sql.PreparedStatement;
[CtImportImpl]import java.math.BigDecimal;
[CtImportImpl]import java.sql.DatabaseMetaData;
[CtImportImpl]import java.sql.Date;
[CtImportImpl]import java.util.TimeZone;
[CtImportImpl]import java.sql.Statement;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtImportImpl]import java.util.concurrent.Future;
[CtImportImpl]import java.sql.ParameterMetaData;
[CtUnresolvedImport]import org.h2.store.Data;
[CtClassImpl][CtJavaDocImpl]/**
 * Tests the PostgreSQL server protocol compliant implementation.
 */
public class TestPgServer extends [CtTypeReferenceImpl]org.h2.test.TestDb {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Run just this test.
     *
     * @param a
     * 		ignored
     */
    public static [CtTypeReferenceImpl]void main([CtParameterImpl]java.lang.String... a) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.test.TestBase test = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.test.TestBase.createCaller().init();
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtVariableWriteImpl]test.config.memory = [CtLiteralImpl]true;
        [CtInvocationImpl][CtVariableReadImpl]test.testFromMain();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]config.memory) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void test() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// testPgAdapter() starts server by itself without a wait so run it first
        testPgAdapter();
        [CtInvocationImpl]testKeyAlias();
        [CtInvocationImpl]testCancelQuery();
        [CtInvocationImpl]testTextualAndBinaryTypes();
        [CtInvocationImpl]testDateTime();
        [CtInvocationImpl]testPrepareWithUnspecifiedType();
        [CtInvocationImpl]testOtherPgClients();
        [CtInvocationImpl]testArray();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean getPgJdbcDriver() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtLiteralImpl]"org.postgresql.Driver");
            [CtReturnImpl]return [CtLiteralImpl]true;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl]println([CtLiteralImpl]"PostgreSQL JDBC driver not found - PgServer not tested");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.h2.tools.Server createPgServer([CtParameterImpl]java.lang.String... args) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl][CtTypeAccessImpl]org.h2.tools.Server.createPgServer([CtVariableReadImpl]args);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int failures = [CtLiteralImpl]0;
        [CtForImpl]for (; ;) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]server.start();
                [CtReturnImpl]return [CtVariableReadImpl]server;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException e) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// the sleeps are too mitigate "port in use" exceptions on Jenkins
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getErrorCode() != [CtFieldReadImpl]org.h2.api.ErrorCode.EXCEPTION_OPENING_PORT_2) || [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](++[CtVariableWriteImpl]failures) > [CtLiteralImpl]10)) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtVariableReadImpl]e;
                }
                [CtInvocationImpl]println([CtLiteralImpl]"Sleeping");
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]100);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e2) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e2);
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testPgAdapter() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]deleteDb([CtLiteralImpl]"pgserver");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl][CtTypeAccessImpl]org.h2.tools.Server.createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-baseDir", [CtInvocationImpl]getBaseDir(), [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon");
        [CtInvocationImpl]assertEquals([CtLiteralImpl]5535, [CtInvocationImpl][CtVariableReadImpl]server.getPort());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Not started", [CtInvocationImpl][CtVariableReadImpl]server.getStatus());
        [CtInvocationImpl][CtVariableReadImpl]server.start();
        [CtInvocationImpl]assertStartsWith([CtInvocationImpl][CtVariableReadImpl]server.getStatus(), [CtLiteralImpl]"PG server running at pg://");
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
                [CtInvocationImpl]testPgClient();
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testCancelQuery() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.ExecutorService executor = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.Executors.newSingleThreadExecutor();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create alias sleep for \"java.lang.Thread.sleep\"");
            [CtInvocationImpl][CtCommentImpl]// create a table with 200 rows (cancel interval is 127)
            [CtVariableReadImpl]stat.execute([CtLiteralImpl]"create table test(id int)");
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]200; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"insert into test (id) values (rand())");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.Future<[CtTypeReferenceImpl]java.lang.Boolean> future = [CtInvocationImpl][CtVariableReadImpl]executor.submit([CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"select id, sleep(5) from test"));
            [CtInvocationImpl][CtCommentImpl]// give it a little time to start and then cancel it
            [CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]100);
            [CtInvocationImpl][CtVariableReadImpl]stat.cancel();
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]future.get();
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.concurrent.ExecutionException e) [CtBlockImpl]{
                [CtInvocationImpl]assertStartsWith([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getCause().getMessage(), [CtLiteralImpl]"ERROR: canceling statement due to user request");
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]conn.close();
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
            [CtInvocationImpl][CtVariableReadImpl]executor.shutdown();
        }
        [CtInvocationImpl]deleteDb([CtLiteralImpl]"pgserver");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testPgClient() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
        [CtInvocationImpl][CtInvocationImpl]assertThrows([CtFieldReadImpl]java.sql.SQLException.class, [CtVariableReadImpl]stat).execute([CtLiteralImpl]"select ***");
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create user test password 'test'");
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create table test(id int primary key, name varchar)");
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create index idx_test_name on test(name, id)");
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"grant all on test to test");
        [CtInvocationImpl][CtVariableReadImpl]stat.close();
        [CtInvocationImpl][CtVariableReadImpl]conn.close();
        [CtAssignmentImpl][CtVariableWriteImpl]conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"test", [CtLiteralImpl]"test");
        [CtAssignmentImpl][CtVariableWriteImpl]stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs;
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"prepare test(int, int) as select ?1*?2");
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"execute test(3, 2)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]6, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"deallocate test");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement prep;
        [CtAssignmentImpl][CtVariableWriteImpl]prep = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"select * from test where name = ?");
        [CtInvocationImpl][CtVariableReadImpl]prep.setNull([CtLiteralImpl]1, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]VARCHAR);
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]prep.executeQuery();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]prep = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"insert into test values(?, ?)");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ParameterMetaData meta = [CtInvocationImpl][CtVariableReadImpl]prep.getParameterMetaData();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]meta.getParameterCount());
        [CtInvocationImpl][CtVariableReadImpl]prep.setInt([CtLiteralImpl]1, [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]prep.setString([CtLiteralImpl]2, [CtLiteralImpl]"Hello");
        [CtInvocationImpl][CtVariableReadImpl]prep.execute();
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select * from test");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSetMetaData rsMeta = [CtInvocationImpl][CtVariableReadImpl]rs.getMetaData();
        [CtInvocationImpl]assertEquals([CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]INTEGER, [CtInvocationImpl][CtVariableReadImpl]rsMeta.getColumnType([CtLiteralImpl]1));
        [CtInvocationImpl]assertEquals([CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]VARCHAR, [CtInvocationImpl][CtVariableReadImpl]rsMeta.getColumnType([CtLiteralImpl]2));
        [CtInvocationImpl][CtVariableReadImpl]prep.close();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Hello", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]prep = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtBinaryOperatorImpl][CtLiteralImpl]"select * from test " + [CtLiteralImpl]"where id = ? and name = ?");
        [CtInvocationImpl][CtVariableReadImpl]prep.setInt([CtLiteralImpl]1, [CtLiteralImpl]1);
        [CtInvocationImpl][CtVariableReadImpl]prep.setString([CtLiteralImpl]2, [CtLiteralImpl]"Hello");
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]prep.executeQuery();
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Hello", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtInvocationImpl][CtVariableReadImpl]rs.close();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.DatabaseMetaData dbMeta = [CtInvocationImpl][CtVariableReadImpl]conn.getMetaData();
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]dbMeta.getTables([CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]"TEST", [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"test", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"TABLE_NAME"));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]dbMeta.getColumns([CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]"TEST", [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"COLUMN_NAME"));
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"name", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"COLUMN_NAME"));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]dbMeta.getIndexInfo([CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]"TEST", [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// index info is currently disabled
        [CtCommentImpl]// rs.next();
        [CtCommentImpl]// assertEquals("TEST", rs.getString("TABLE_NAME"));
        [CtCommentImpl]// rs.next();
        [CtCommentImpl]// assertEquals("TEST", rs.getString("TABLE_NAME"));
        assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select version(), pg_postmaster_start_time(), current_schema()");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1);
        [CtInvocationImpl]assertContains([CtVariableReadImpl]s, [CtLiteralImpl]"H2");
        [CtInvocationImpl]assertContains([CtVariableReadImpl]s, [CtLiteralImpl]"PostgreSQL");
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2);
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]3);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]s, [CtLiteralImpl]"public");
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtInvocationImpl][CtVariableReadImpl]conn.setAutoCommit([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"delete from test");
        [CtInvocationImpl][CtVariableReadImpl]conn.rollback();
        [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"update test set name = 'Hallo'");
        [CtInvocationImpl][CtVariableReadImpl]conn.commit();
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select * from test order by id");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"Hallo", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtLiteralImpl]"select id, name, pg_get_userbyid(id) " + [CtLiteralImpl]"from information_schema.users order by id");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2), [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]3));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
        [CtInvocationImpl][CtVariableReadImpl]rs.close();
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select currTid2('x', 1)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select has_table_privilege('TEST', 'READ')");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.getBoolean([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select has_schema_privilege(1, 'READ')");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.getBoolean([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select has_database_privilege(1, 'READ')");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.getBoolean([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_get_userbyid(1000000000)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"unknown (OID=1000000000)", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_encoding_to_char(0)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"SQL_ASCII", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_encoding_to_char(6)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"UTF8", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_encoding_to_char(8)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"LATIN1", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_encoding_to_char(20)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"UTF8", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_encoding_to_char(40)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select 0::regclass");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select pg_get_indexdef(0, 0, false)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtLiteralImpl]"select id from information_schema.indexes " + [CtLiteralImpl]"where index_name='idx_test_name'");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int indexId = [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1);
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select pg_get_indexdef(" + [CtVariableReadImpl]indexId) + [CtLiteralImpl]", 0, false)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"CREATE INDEX \"public\".\"idx_test_name\" ON \"public\".\"test\"(\"name\", \"id\")", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select pg_get_indexdef(" + [CtVariableReadImpl]indexId) + [CtLiteralImpl]", null, false)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select pg_get_indexdef(" + [CtVariableReadImpl]indexId) + [CtLiteralImpl]", 1, false)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"name", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select pg_get_indexdef(" + [CtVariableReadImpl]indexId) + [CtLiteralImpl]", 2, false)");
        [CtInvocationImpl][CtVariableReadImpl]rs.next();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
        [CtInvocationImpl][CtVariableReadImpl]conn.close();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testKeyAlias() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
            [CtInvocationImpl][CtCommentImpl]// confirm that we've got the in memory implementation
            [CtCommentImpl]// by creating a table and checking flags
            [CtVariableReadImpl]stat.execute([CtLiteralImpl]"create table test(id int primary key, name varchar)");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtLiteralImpl]"select storage_type from information_schema.tables " + [CtLiteralImpl]"where table_name = 'test'");
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
            [CtInvocationImpl]assertEquals([CtLiteralImpl]"MEMORY", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
            [CtInvocationImpl][CtVariableReadImpl]conn.close();
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testTextualAndBinaryTypes() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]testTextualAndBinaryTypes([CtLiteralImpl]false);
        [CtInvocationImpl]testTextualAndBinaryTypes([CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testTextualAndBinaryTypes([CtParameterImpl][CtTypeReferenceImpl]boolean binary) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
            [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"user", [CtLiteralImpl]"sa");
            [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"password", [CtLiteralImpl]"sa");
            [CtIfImpl][CtCommentImpl]// force binary
            if ([CtVariableReadImpl]binary) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"prepareThreshold", [CtLiteralImpl]"-1");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtVariableReadImpl]props);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"create table test(x1 varchar, x2 int, " + [CtLiteralImpl]"x3 smallint, x4 bigint, x5 double precision, x6 float, ") + [CtLiteralImpl]"x7 real, x8 boolean, x9 char(3), x10 bytea, ") + [CtLiteralImpl]"x11 date, x12 time, x13 timestamp, x14 numeric(25, 5),") + [CtLiteralImpl]"x15 time with time zone, x16 timestamp with time zone)");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"insert into test values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            [CtInvocationImpl][CtVariableReadImpl]ps.setString([CtLiteralImpl]1, [CtLiteralImpl]"test");
            [CtInvocationImpl][CtVariableReadImpl]ps.setInt([CtLiteralImpl]2, [CtLiteralImpl]12345678);
            [CtInvocationImpl][CtVariableReadImpl]ps.setShort([CtLiteralImpl]3, [CtLiteralImpl](([CtTypeReferenceImpl]short) (12345)));
            [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtLiteralImpl]4, [CtLiteralImpl]1234567890123L);
            [CtInvocationImpl][CtVariableReadImpl]ps.setDouble([CtLiteralImpl]5, [CtLiteralImpl]123.456);
            [CtInvocationImpl][CtVariableReadImpl]ps.setFloat([CtLiteralImpl]6, [CtLiteralImpl]123.456F);
            [CtInvocationImpl][CtVariableReadImpl]ps.setFloat([CtLiteralImpl]7, [CtLiteralImpl]123.456F);
            [CtInvocationImpl][CtVariableReadImpl]ps.setBoolean([CtLiteralImpl]8, [CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]ps.setByte([CtLiteralImpl]9, [CtLiteralImpl](([CtTypeReferenceImpl]byte) (0xfe)));
            [CtInvocationImpl][CtVariableReadImpl]ps.setBytes([CtLiteralImpl]10, [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtLiteralImpl]'a', [CtLiteralImpl](([CtTypeReferenceImpl]byte) (0xfe)), [CtLiteralImpl]'W', [CtLiteralImpl]0, [CtLiteralImpl]127, [CtLiteralImpl]'\\' });
            [CtInvocationImpl][CtVariableReadImpl]ps.setDate([CtLiteralImpl]11, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"2015-01-31"));
            [CtInvocationImpl][CtVariableReadImpl]ps.setTime([CtLiteralImpl]12, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"20:11:15"));
            [CtInvocationImpl][CtVariableReadImpl]ps.setTimestamp([CtLiteralImpl]13, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"2001-10-30 14:16:10.111"));
            [CtInvocationImpl][CtVariableReadImpl]ps.setBigDecimal([CtLiteralImpl]14, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.math.BigDecimal([CtLiteralImpl]"12345678901234567890.12345"));
            [CtInvocationImpl][CtVariableReadImpl]ps.setTime([CtLiteralImpl]15, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"20:11:15"));
            [CtInvocationImpl][CtVariableReadImpl]ps.setTimestamp([CtLiteralImpl]16, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"2001-10-30 14:16:10.111"));
            [CtInvocationImpl][CtVariableReadImpl]ps.execute();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i <= [CtLiteralImpl]16; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtVariableReadImpl]i, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]NULL);
            }
            [CtInvocationImpl][CtVariableReadImpl]ps.execute();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select * from test");
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
            [CtInvocationImpl]assertEquals([CtLiteralImpl]"test", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]12345678, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]2));
            [CtInvocationImpl]assertEquals([CtLiteralImpl](([CtTypeReferenceImpl]short) (12345)), [CtInvocationImpl][CtVariableReadImpl]rs.getShort([CtLiteralImpl]3));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]1234567890123L, [CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtLiteralImpl]4));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]123.456, [CtInvocationImpl][CtVariableReadImpl]rs.getDouble([CtLiteralImpl]5));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]123.456F, [CtInvocationImpl][CtVariableReadImpl]rs.getFloat([CtLiteralImpl]6));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]123.456F, [CtInvocationImpl][CtVariableReadImpl]rs.getFloat([CtLiteralImpl]7));
            [CtInvocationImpl]assertEquals([CtLiteralImpl]true, [CtInvocationImpl][CtVariableReadImpl]rs.getBoolean([CtLiteralImpl]8));
            [CtInvocationImpl]assertEquals([CtLiteralImpl](([CtTypeReferenceImpl]byte) (0xfe)), [CtInvocationImpl][CtVariableReadImpl]rs.getByte([CtLiteralImpl]9));
            [CtInvocationImpl]assertEquals([CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtLiteralImpl]'a', [CtLiteralImpl](([CtTypeReferenceImpl]byte) (0xfe)), [CtLiteralImpl]'W', [CtLiteralImpl]0, [CtLiteralImpl]127, [CtLiteralImpl]'\\' }, [CtInvocationImpl][CtVariableReadImpl]rs.getBytes([CtLiteralImpl]10));
            [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"2015-01-31"), [CtInvocationImpl][CtVariableReadImpl]rs.getDate([CtLiteralImpl]11));
            [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"20:11:15"), [CtInvocationImpl][CtVariableReadImpl]rs.getTime([CtLiteralImpl]12));
            [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"2001-10-30 14:16:10.111"), [CtInvocationImpl][CtVariableReadImpl]rs.getTimestamp([CtLiteralImpl]13));
            [CtInvocationImpl]assertEquals([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.math.BigDecimal([CtLiteralImpl]"12345678901234567890.12345"), [CtInvocationImpl][CtVariableReadImpl]rs.getBigDecimal([CtLiteralImpl]14));
            [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"20:11:15"), [CtInvocationImpl][CtVariableReadImpl]rs.getTime([CtLiteralImpl]15));
            [CtInvocationImpl]assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"2001-10-30 14:16:10.111"), [CtInvocationImpl][CtVariableReadImpl]rs.getTimestamp([CtLiteralImpl]16));
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i <= [CtLiteralImpl]16; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]rs.getObject([CtVariableReadImpl]i));
            }
            [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            [CtInvocationImpl][CtVariableReadImpl]conn.close();
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testDateTime() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.TimeZone old = [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getDefault();
        [CtInvocationImpl][CtCommentImpl]/* java.util.TimeZone doesn't support LMT, so perform this test with
        fixed time zone offset
         */
        [CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"GMT+01"));
        [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.resetCalendar();
        [CtInvocationImpl][CtTypeAccessImpl]org.h2.store.Data.resetCalendar();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
                [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"user", [CtLiteralImpl]"sa");
                [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"password", [CtLiteralImpl]"sa");
                [CtInvocationImpl][CtCommentImpl]// force binary
                [CtVariableReadImpl]props.setProperty([CtLiteralImpl]"prepareThreshold", [CtLiteralImpl]"-1");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtVariableReadImpl]props);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
                [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create table test(x1 date, x2 time, x3 timestamp)");
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.sql.Date[] dates = [CtNewArrayImpl]new java.sql.Date[]{ [CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"2017-02-20"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1970-01-01"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1969-12-31"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1940-01-10"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1950-11-10"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1500-01-01") };
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.sql.Time[] times = [CtNewArrayImpl]new java.sql.Time[]{ [CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"14:15:16"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"00:00:00"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"23:59:59"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"00:10:59"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"08:30:42"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Time.valueOf([CtLiteralImpl]"10:00:00") };
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.sql.Timestamp[] timestamps = [CtNewArrayImpl]new java.sql.Timestamp[]{ [CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"2017-02-20 14:15:16.763"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"1970-01-01 00:00:00"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"1969-12-31 23:59:59"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"1940-01-10 00:10:59"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"1950-11-10 08:30:42.12"), [CtInvocationImpl][CtTypeAccessImpl]java.sql.Timestamp.valueOf([CtLiteralImpl]"1500-01-01 10:00:10") };
                [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtFieldReadImpl][CtVariableReadImpl]dates.length;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"insert into test values (?,?,?)");
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]count; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setDate([CtLiteralImpl]1, [CtArrayReadImpl][CtVariableReadImpl]dates[[CtVariableReadImpl]i]);
                    [CtInvocationImpl][CtVariableReadImpl]ps.setTime([CtLiteralImpl]2, [CtArrayReadImpl][CtVariableReadImpl]times[[CtVariableReadImpl]i]);
                    [CtInvocationImpl][CtVariableReadImpl]ps.setTimestamp([CtLiteralImpl]3, [CtArrayReadImpl][CtVariableReadImpl]timestamps[[CtVariableReadImpl]i]);
                    [CtInvocationImpl][CtVariableReadImpl]ps.execute();
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"select * from test");
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]count; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                    [CtInvocationImpl]assertEquals([CtArrayReadImpl][CtVariableReadImpl]dates[[CtVariableReadImpl]i], [CtInvocationImpl][CtVariableReadImpl]rs.getDate([CtLiteralImpl]1));
                    [CtInvocationImpl]assertEquals([CtArrayReadImpl][CtVariableReadImpl]times[[CtVariableReadImpl]i], [CtInvocationImpl][CtVariableReadImpl]rs.getTime([CtLiteralImpl]2));
                    [CtInvocationImpl]assertEquals([CtArrayReadImpl][CtVariableReadImpl]timestamps[[CtVariableReadImpl]i], [CtInvocationImpl][CtVariableReadImpl]rs.getTimestamp([CtLiteralImpl]3));
                }
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl][CtVariableReadImpl]conn.close();
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]server.stop();
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtVariableReadImpl]old);
            [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.resetCalendar();
            [CtInvocationImpl][CtTypeAccessImpl]org.h2.store.Data.resetCalendar();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testPrepareWithUnspecifiedType() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
            [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"user", [CtLiteralImpl]"sa");
            [CtInvocationImpl][CtVariableReadImpl]props.setProperty([CtLiteralImpl]"password", [CtLiteralImpl]"sa");
            [CtInvocationImpl][CtCommentImpl]// force server side prepare
            [CtVariableReadImpl]props.setProperty([CtLiteralImpl]"prepareThreshold", [CtLiteralImpl]"1");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtVariableReadImpl]props);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stmt = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement();
            [CtInvocationImpl][CtVariableReadImpl]stmt.executeUpdate([CtLiteralImpl]"create table t1 (id integer, v timestamp)");
            [CtInvocationImpl][CtVariableReadImpl]stmt.close();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement pstmt = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"insert into t1 values(100500, ?)");
            [CtInvocationImpl][CtCommentImpl]// assertTrue(((PGStatement) pstmt).isUseServerPrepare());
            assertEquals([CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]TIMESTAMP, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pstmt.getParameterMetaData().getParameterType([CtLiteralImpl]1));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Timestamp t = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.Timestamp([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
            [CtInvocationImpl][CtVariableReadImpl]pstmt.setObject([CtLiteralImpl]1, [CtVariableReadImpl]t);
            [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]pstmt.executeUpdate());
            [CtInvocationImpl][CtVariableReadImpl]pstmt.close();
            [CtAssignmentImpl][CtVariableWriteImpl]pstmt = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtLiteralImpl]"SELECT * FROM t1 WHERE v = ?");
            [CtInvocationImpl]assertEquals([CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]TIMESTAMP, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pstmt.getParameterMetaData().getParameterType([CtLiteralImpl]1));
            [CtInvocationImpl][CtVariableReadImpl]pstmt.setObject([CtLiteralImpl]1, [CtVariableReadImpl]t);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]pstmt.executeQuery();
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
            [CtInvocationImpl]assertEquals([CtLiteralImpl]100500, [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1));
            [CtInvocationImpl][CtVariableReadImpl]rs.close();
            [CtInvocationImpl][CtVariableReadImpl]pstmt.close();
            [CtInvocationImpl][CtVariableReadImpl]conn.close();
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testOtherPgClients() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn0 = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:h2:mem:pgserver;mode=postgresql;database_to_lower=true", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");[CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"create table test(id serial primary key, x1 integer)");
            [CtInvocationImpl][CtCommentImpl]// pgAdmin
            [CtVariableReadImpl]stat.execute([CtLiteralImpl]"SET client_min_messages=notice");
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtLiteralImpl]"SELECT set_config('bytea_output','escape',false) " + [CtLiteralImpl]"FROM pg_settings WHERE name = 'bytea_output'")) [CtBlockImpl]{
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"SET client_encoding='UNICODE'");
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"SELECT version()")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"version"));
            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT " + [CtLiteralImpl]"db.oid as did, db.datname, db.datallowconn, ") + [CtLiteralImpl]"pg_encoding_to_char(db.encoding) AS serverencoding, ") + [CtLiteralImpl]"has_database_privilege(db.oid, 'CREATE') as cancreate, datlastsysoid ") + [CtLiteralImpl]"FROM pg_database db WHERE db.datname = current_database()")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"pgserver", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"datname"));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT " + [CtLiteralImpl]"oid as id, rolname as name, rolsuper as is_superuser, ") + [CtLiteralImpl]"CASE WHEN rolsuper THEN true ELSE rolcreaterole END as can_create_role, ") + [CtLiteralImpl]"CASE WHEN rolsuper THEN true ELSE rolcreatedb END as can_create_db ") + [CtLiteralImpl]"FROM pg_catalog.pg_roles WHERE rolname = current_user")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"sa", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"name"));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT " + [CtLiteralImpl]"db.oid as did, db.datname as name, ta.spcname as spcname, db.datallowconn, ") + [CtLiteralImpl]"has_database_privilege(db.oid, 'CREATE') as cancreate, datdba as owner ") + [CtLiteralImpl]"FROM pg_database db LEFT OUTER JOIN pg_tablespace ta ON db.dattablespace = ta.oid ") + [CtLiteralImpl]"WHERE db.oid > 100000::OID")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"pgserver", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"name"));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT nsp.oid, nsp.nspname as name, " + [CtLiteralImpl]"has_schema_privilege(nsp.oid, 'CREATE') as can_create, ") + [CtLiteralImpl]"has_schema_privilege(nsp.oid, 'USAGE') as has_usage ") + [CtLiteralImpl]"FROM pg_namespace nsp WHERE nspname NOT LIKE \'pg\\_%\' AND NOT (") + [CtLiteralImpl]"(nsp.nspname = 'pg_catalog' AND EXISTS (SELECT 1 FROM pg_class ") + [CtLiteralImpl]"WHERE relname = 'pg_class' AND relnamespace = nsp.oid LIMIT 1)) OR ") + [CtLiteralImpl]"(nsp.nspname = 'pgagent' AND EXISTS (SELECT 1 FROM pg_class ") + [CtLiteralImpl]"WHERE relname = 'pga_job' AND relnamespace = nsp.oid LIMIT 1)) OR ") + [CtLiteralImpl]"(nsp.nspname = 'information_schema' AND EXISTS (SELECT 1 FROM pg_class ") + [CtLiteralImpl]"WHERE relname = 'tables' AND relnamespace = nsp.oid LIMIT 1))") + [CtLiteralImpl]") ORDER BY nspname")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"public", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"name"));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"SELECT format_type(23, NULL)")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"INTEGER", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
            [CtInvocationImpl][CtCommentImpl]// pgAdmin sends `SET LOCAL join_collapse_limit=8`, but `LOCAL` is not supported yet
            [CtVariableReadImpl]stat.execute([CtLiteralImpl]"SET join_collapse_limit=8");
            [CtTryWithResourceImpl][CtCommentImpl]// HeidiSQL
            try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"SHOW ssl")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"off", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
            }
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"SET search_path TO 'public', '$user'");
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT *, NULL AS data_length, " + [CtLiteralImpl]"pg_relation_size(QUOTE_IDENT(t.TABLE_SCHEMA) || '.' || QUOTE_IDENT(t.TABLE_NAME))::bigint ") + [CtLiteralImpl]"AS index_length, ") + [CtLiteralImpl]"c.reltuples, obj_description(c.oid) AS comment ") + [CtLiteralImpl]"FROM \"information_schema\".\"tables\" AS t ") + [CtLiteralImpl]"LEFT JOIN \"pg_namespace\" n ON t.table_schema = n.nspname ") + [CtLiteralImpl]"LEFT JOIN \"pg_class\" c ON n.oid = c.relnamespace AND c.relname=t.table_name ") + [CtLiteralImpl]"WHERE t.\"table_schema\"=\'public\'")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"test", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"table_name"));
                [CtInvocationImpl]assertTrue([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtLiteralImpl]"index_length") >= [CtLiteralImpl]0L);[CtCommentImpl]// test pg_relation_size()

                [CtInvocationImpl]assertNull([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"comment"));[CtCommentImpl]// test obj_description()

            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT \"p\".\"proname\", \"p\".\"proargtypes\" " + [CtLiteralImpl]"FROM \"pg_catalog\".\"pg_namespace\" AS \"n\" ") + [CtLiteralImpl]"JOIN \"pg_catalog\".\"pg_proc\" AS \"p\" ON \"p\".\"pronamespace\" = \"n\".\"oid\" ") + [CtLiteralImpl]"WHERE \"n\".\"nspname\"=\'public\';")) [CtBlockImpl]{
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());[CtCommentImpl]// "pg_proc" always empty

            }
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT DISTINCT a.attname AS column_name, " + [CtLiteralImpl]"a.attnum, a.atttypid, FORMAT_TYPE(a.atttypid, a.atttypmod) AS data_type, ") + [CtLiteralImpl]"CASE a.attnotnull WHEN false THEN 'YES' ELSE 'NO' END AS IS_NULLABLE, ") + [CtLiteralImpl]"com.description AS column_comment, pg_get_expr(def.adbin, def.adrelid) AS column_default, ") + [CtLiteralImpl]"NULL AS character_maximum_length FROM pg_attribute AS a ") + [CtLiteralImpl]"JOIN pg_class AS pgc ON pgc.oid = a.attrelid ") + [CtLiteralImpl]"LEFT JOIN pg_description AS com ON (pgc.oid = com.objoid AND a.attnum = com.objsubid) ") + [CtLiteralImpl]"LEFT JOIN pg_attrdef AS def ON (a.attrelid = def.adrelid AND a.attnum = def.adnum) ") + [CtLiteralImpl]"WHERE a.attnum > 0 AND pgc.oid = a.attrelid AND pg_table_is_visible(pgc.oid) ") + [CtLiteralImpl]"AND NOT a.attisdropped AND pgc.relname = 'test' ORDER BY a.attnum")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"column_name"));
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"x1", [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"column_name"));
                [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]rs.next());
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
            [CtInvocationImpl][CtVariableReadImpl]conn0.close();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testArray() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]getPgJdbcDriver()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.tools.Server server = [CtInvocationImpl]createPgServer([CtLiteralImpl]"-ifNotExists", [CtLiteralImpl]"-pgPort", [CtLiteralImpl]"5535", [CtLiteralImpl]"-pgDaemon", [CtLiteralImpl]"-key", [CtLiteralImpl]"pgserver", [CtLiteralImpl]"mem:pgserver");
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtTypeAccessImpl]java.sql.DriverManager.getConnection([CtLiteralImpl]"jdbc:postgresql://localhost:5535/pgserver", [CtLiteralImpl]"sa", [CtLiteralImpl]"sa");[CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Statement stat = [CtInvocationImpl][CtVariableReadImpl]conn.createStatement()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"CREATE TABLE test (id int primary key, x1 int array, x2 varchar array)");
            [CtInvocationImpl][CtVariableReadImpl]stat.execute([CtLiteralImpl]"INSERT INTO test (id, x1, x2) VALUES (1, ARRAY[2, 3], ARRAY['4', '5'])");
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stat.executeQuery([CtLiteralImpl]"SELECT x1, x2 FROM test WHERE id = 1")) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]rs.next());
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] arr = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) ([CtInvocationImpl][CtVariableReadImpl]rs.getArray([CtLiteralImpl]1).getArray()));
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"2", [CtArrayReadImpl][CtVariableReadImpl]arr[[CtLiteralImpl]0]);
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"3", [CtArrayReadImpl][CtVariableReadImpl]arr[[CtLiteralImpl]1]);
                [CtAssignmentImpl][CtVariableWriteImpl]arr = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) ([CtInvocationImpl][CtVariableReadImpl]rs.getArray([CtLiteralImpl]2).getArray()));
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"4", [CtArrayReadImpl][CtVariableReadImpl]arr[[CtLiteralImpl]0]);
                [CtInvocationImpl]assertEquals([CtLiteralImpl]"5", [CtArrayReadImpl][CtVariableReadImpl]arr[[CtLiteralImpl]1]);
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.stop();
        }
    }
}
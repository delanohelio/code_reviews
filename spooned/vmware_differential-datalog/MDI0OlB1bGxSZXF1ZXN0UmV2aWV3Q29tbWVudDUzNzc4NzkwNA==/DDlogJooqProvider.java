[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018-2020 VMware, Inc. All Rights Reserved.
SPDX-License-Identifier: BSD-2
 */
[CtPackageDeclarationImpl]package com.vmware.ddlog;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlUpdate;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlInsert;
[CtImportImpl]import java.sql.Types;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.jooq.impl.DSL;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlDelete;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.sql.SQLException;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import org.jooq.Table;
[CtUnresolvedImport]import static org.jooq.impl.DSL.field;
[CtUnresolvedImport]import org.jooq.tools.jdbc.MockDataProvider;
[CtUnresolvedImport]import ddlogapi.DDlogRecord;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlCall;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlLiteral;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlNode;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import ddlogapi.DDlogException;
[CtUnresolvedImport]import org.jooq.Record1;
[CtUnresolvedImport]import org.apache.calcite.sql.util.SqlBasicVisitor;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlIdentifier;
[CtUnresolvedImport]import org.jooq.DSLContext;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlSelect;
[CtUnresolvedImport]import org.jooq.Record;
[CtUnresolvedImport]import org.jooq.DataType;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlKind;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlBasicCall;
[CtUnresolvedImport]import org.jooq.Field;
[CtUnresolvedImport]import org.jooq.Result;
[CtUnresolvedImport]import ddlogapi.DDlogAPI;
[CtUnresolvedImport]import org.jooq.tools.jdbc.MockResult;
[CtUnresolvedImport]import ddlogapi.DDlogRecCommand;
[CtUnresolvedImport]import org.apache.calcite.sql.parser.SqlParser;
[CtUnresolvedImport]import ddlogapi.DDlogCommand;
[CtUnresolvedImport]import org.apache.calcite.sql.SqlDynamicParam;
[CtImportImpl]import java.util.NoSuchElementException;
[CtUnresolvedImport]import com.facebook.presto.sql.parser.ParsingOptions;
[CtUnresolvedImport]import org.apache.calcite.sql.parser.SqlParseException;
[CtUnresolvedImport]import org.jooq.tools.jdbc.MockExecuteContext;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.facebook.presto.sql.tree.Statement;
[CtClassImpl][CtJavaDocImpl]/**
 * This class provides a restricted mechanism to make a DDlog program appear like an SQL database that can be
 * queried over a JDBC connection. To initialize, it requires a set of "create table" and "create view" statements
 * to be supplied during initialization. For example:
 *
 *         final DDlogAPI dDlogAPI = new DDlogAPI(1, null, true);
 *
 *         // Initialise the data provider. ddl represents a list of Strings that are SQL DDL statements
 *         // like "create table" and "create view"
 *         MockDataProvider provider = new DDlogJooqProvider(dDlogAPI, ddl);
 *         MockConnection connection = new MockConnection(provider);
 *
 *         // Pass the mock connection to a jOOQ DSLContext:
 *         DSLContext create = DSL.using(connection);
 *
 * After that, the connection that is created with this MockProvider can execute a restricted subset of SQL queries.
 * We assume these queries are of one of the following forms:
 *   A1. "select * from T" where T is a table name which corresponds to a ddlog output relation. By definition,
 *                        T can therefore only be an SQL view for which there is a corresponding
 *                        "create view T as ..." that is passed to the  DdlogJooqProvider.
 *   A2. "insert into T values (<row>)" where T is a base table. That is, there should be a corresponding
 *                                     "create table T..." DDL statement that is passed to the DDlogJooqProvider.
 *   A3. "delete from T where P1 = A and P2 = B..." where T is a base table and P1, P2... are columns in T's
 *                                         primary key. That is, there should be a corresponding
 *                                        "create table T..." DDL statement that is passed to the DDlogJooqProvider
 *                                         where P1, P2... etc are columns in T's primary key.
 *  A4. "update T set C1 = A, C2 = B where P1 = A..." where T is a base table and P1, P2... are columns in T's
 *                                          primary key. That is, there should be a corresponding
 *                                          "create table T..." DDL statement that is passed to the DDlogJooqProvider
 *                                          where P1, P2... etc are columns in T's primary key. C1, C2 etc can
 *                                          be any column in T.
 */
public final class DDlogJooqProvider implements [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockDataProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DDLOG_SOME = [CtLiteralImpl]"ddlog_std::Some";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DDLOG_NONE = [CtLiteralImpl]"ddlog_std::None";

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.Object[] DEFAULT_BINDING = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[[CtLiteralImpl]0];

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.vmware.ddlog.ParseLiterals PARSE_LITERALS = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.ParseLiterals();

    [CtFieldImpl]private final [CtTypeReferenceImpl]ddlogapi.DDlogAPI dDlogAPI;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.jooq.DSLContext dslContext;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.jooq.Field<[CtTypeReferenceImpl]java.lang.Integer> updateCountField;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>>> tablesToFields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>>> tablesToPrimaryKeys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Record>> materializedViews = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtConstructorImpl]public DDlogJooqProvider([CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogAPI dDlogAPI, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> sqlStatements) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dDlogAPI = [CtVariableReadImpl]dDlogAPI;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dslContext = [CtInvocationImpl][CtTypeAccessImpl]org.jooq.impl.DSL.using([CtLiteralImpl]"jdbc:h2:mem:");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.updateCountField = [CtInvocationImpl]field([CtLiteralImpl]"UPDATE_COUNT", [CtFieldReadImpl]java.lang.Integer.class);
        [CtLocalVariableImpl][CtCommentImpl]// We translate DDL statements from the Presto dialect to H2.
        [CtCommentImpl]// We then execute these statements in a temporary database so that JOOQ can extract useful metadata
        [CtCommentImpl]// that we will use later (for example, the record types for views).
        final [CtTypeReferenceImpl]com.facebook.presto.sql.parser.SqlParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.sql.parser.SqlParser();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.facebook.presto.sql.parser.ParsingOptions options = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.facebook.presto.sql.parser.ParsingOptions.builder().build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.TranslateCreateTableDialect translateCreateTableDialect = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.TranslateCreateTableDialect();
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String sql : [CtVariableReadImpl]sqlStatements) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.facebook.presto.sql.tree.Statement statement = [CtInvocationImpl][CtVariableReadImpl]parser.createStatement([CtVariableReadImpl]sql, [CtVariableReadImpl]options);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String statementInH2Dialect = [CtInvocationImpl][CtVariableReadImpl]translateCreateTableDialect.process([CtVariableReadImpl]statement, [CtVariableReadImpl]sql);
            [CtInvocationImpl][CtFieldReadImpl]dslContext.execute([CtVariableReadImpl]statementInH2Dialect);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Table<[CtWildcardReferenceImpl]?> table : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]dslContext.meta().getTables()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getSchema().getName().equals([CtLiteralImpl]"PUBLIC")) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// H2-specific assumption
                [CtFieldReadImpl]tablesToFields.put([CtInvocationImpl][CtVariableReadImpl]table.getName(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]table.fields()));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]table.getPrimaryKey() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]tablesToPrimaryKeys.put([CtInvocationImpl][CtVariableReadImpl]table.getName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getPrimaryKey().getFields());
                }
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]/* All executed SQL queries against a JOOQ connection are received here */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]org.jooq.tools.jdbc.MockResult[] execute([CtParameterImpl]final [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockExecuteContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] batchSql = [CtInvocationImpl][CtVariableReadImpl]ctx.batchSQL();
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]org.jooq.tools.jdbc.MockResult[] mock = [CtNewArrayImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult[[CtFieldReadImpl][CtVariableReadImpl]batchSql.length];
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.transactionStart();
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.Object[][] bindings = [CtInvocationImpl][CtVariableReadImpl]ctx.batchBindings();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]batchSql.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.Object[] binding = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]bindings != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]bindings.length > [CtVariableReadImpl]i)) ? [CtArrayReadImpl][CtVariableReadImpl]bindings[[CtVariableReadImpl]i] : [CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.DEFAULT_BINDING;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext([CtArrayReadImpl][CtVariableReadImpl]batchSql[[CtVariableReadImpl]i], [CtVariableReadImpl]binding);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParser parser = [CtInvocationImpl][CtTypeAccessImpl]org.apache.calcite.sql.parser.SqlParser.create([CtArrayReadImpl][CtVariableReadImpl]batchSql[[CtVariableReadImpl]i]);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlNode sqlNode = [CtInvocationImpl][CtVariableReadImpl]parser.parseStmt();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]mock[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]sqlNode.accept([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryVisitor([CtVariableReadImpl]context));
            }
            [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.transactionCommitDumpChanges([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::onChange);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]ddlogapi.DDlogException | [CtTypeReferenceImpl]org.apache.calcite.sql.parser.SqlParseException e) [CtBlockImpl]{
            [CtInvocationImpl]rollback();
        }
        [CtReturnImpl]return [CtVariableReadImpl]mock;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]java.lang.Record> fetchTable([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String tableName) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> fields = [CtInvocationImpl][CtFieldReadImpl]tablesToFields.get([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fields == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unknown table %s queried", [CtVariableReadImpl]tableName));
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]java.lang.Record> result = [CtInvocationImpl][CtFieldReadImpl]dslContext.newResult([CtVariableReadImpl]fields);
        [CtInvocationImpl][CtVariableReadImpl]result.addAll([CtInvocationImpl][CtFieldReadImpl]materializedViews.computeIfAbsent([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase(), [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>()));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void rollback() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.transactionRollback();
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onChange([CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogCommand<[CtTypeReferenceImpl]ddlogapi.DDlogRecord> command) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int relationId = [CtInvocationImpl][CtVariableReadImpl]command.relid();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String relationName = [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.getTableName([CtVariableReadImpl]relationId);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.relationNameToTableName([CtVariableReadImpl]relationName);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> fields = [CtInvocationImpl][CtFieldReadImpl]tablesToFields.get([CtVariableReadImpl]tableName);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record = [CtInvocationImpl][CtVariableReadImpl]command.value();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Record jooqRecord = [CtInvocationImpl][CtFieldReadImpl]dslContext.newRecord([CtVariableReadImpl]fields);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]fields.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.structToValue([CtInvocationImpl][CtVariableReadImpl]fields.get([CtVariableReadImpl]i), [CtInvocationImpl][CtVariableReadImpl]record.getStructField([CtVariableReadImpl]i), [CtVariableReadImpl]jooqRecord);
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Record> materializedView = [CtInvocationImpl][CtFieldReadImpl]materializedViews.computeIfAbsent([CtVariableReadImpl]tableName, [CtLambdaImpl]([CtParameterImpl]java.lang.String k) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>());
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]command.kind()) {
                [CtCaseImpl]case [CtFieldReadImpl]Insert :
                    [CtInvocationImpl][CtVariableReadImpl]materializedView.add([CtVariableReadImpl]jooqRecord);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]DeleteKey :
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Did not expect DeleteKey command type");
                [CtCaseImpl]case [CtFieldReadImpl]DeleteVal :
                    [CtInvocationImpl][CtVariableReadImpl]materializedView.remove([CtVariableReadImpl]jooqRecord);
                    [CtBreakImpl]break;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]ddlogapi.DDlogException ex) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]ex);
        }
    }

    [CtClassImpl]private final class QueryVisitor extends [CtTypeReferenceImpl]org.apache.calcite.sql.util.SqlBasicVisitor<[CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult> {
        [CtFieldImpl]private final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context;

        [CtConstructorImpl]QueryVisitor([CtParameterImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult visit([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]call.getKind()) {
                [CtCaseImpl]case [CtFieldReadImpl]SELECT :
                    [CtReturnImpl]return [CtInvocationImpl]visitSelect([CtVariableReadImpl]call);
                [CtCaseImpl]case [CtFieldReadImpl]INSERT :
                    [CtReturnImpl]return [CtInvocationImpl]visitInsert([CtVariableReadImpl]call);
                [CtCaseImpl]case [CtFieldReadImpl]DELETE :
                    [CtReturnImpl]return [CtInvocationImpl]visitDelete([CtVariableReadImpl]call);
                [CtCaseImpl]case [CtFieldReadImpl]UPDATE :
                    [CtReturnImpl]return [CtInvocationImpl]visitUpdate([CtVariableReadImpl]call);
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtInvocationImpl][CtVariableReadImpl]call.toString());
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult visitSelect([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// The checks below encode assumption A1 (see javadoc for the DDlogJooqProvider class)
            final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlSelect select = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlSelect) (call));
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]select.getSelectList().size() == [CtLiteralImpl]1) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]select.getSelectList().get([CtLiteralImpl]0).toString().equals([CtLiteralImpl]"*"))) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtBinaryOperatorImpl][CtLiteralImpl]"Statement not supported: " + [CtInvocationImpl][CtFieldReadImpl]context.sql());
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) ([CtVariableReadImpl]select.getFrom())).getSimple();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]java.lang.Record> result = [CtInvocationImpl]fetchTable([CtVariableReadImpl]tableName);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtLiteralImpl]1, [CtVariableReadImpl]result);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult visitInsert([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// The assertions below, and in the ParseWhereClauseForDeletes visitor encode assumption A2
            [CtCommentImpl]// (see javadoc for the DDlogJooqProvider class)
            final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlInsert insert = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlInsert) (call));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]insert.getSource().getKind() != [CtFieldReadImpl]org.apache.calcite.sql.SqlKind.VALUES) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtInvocationImpl][CtVariableReadImpl]call.toString());
            }
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]org.apache.calcite.sql.SqlNode[] values = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall) ([CtVariableReadImpl]insert.getSource())).getOperands();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) ([CtVariableReadImpl]insert.getTargetTable())).getSimple();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> fields = [CtInvocationImpl][CtFieldReadImpl]tablesToFields.get([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int tableId = [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.getTableId([CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.ddlogRelationName([CtVariableReadImpl]tableName));
            [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlNode value : [CtVariableReadImpl]values) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]value.getKind() != [CtFieldReadImpl]org.apache.calcite.sql.SqlKind.ROW) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtInvocationImpl][CtVariableReadImpl]call.toString());
                }
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]org.apache.calcite.sql.SqlNode[] rowElements = [CtFieldReadImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall) (value)).operands;
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] recordsArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord[[CtFieldReadImpl][CtVariableReadImpl]rowElements.length];
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]context.hasBinding()) [CtBlockImpl]{
                    [CtForImpl][CtCommentImpl]// Is a statement with bound variables
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]rowElements.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNullableField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fields.get([CtVariableReadImpl]i).getDataType().nullable();
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.toValue([CtInvocationImpl][CtVariableReadImpl]fields.get([CtVariableReadImpl]i), [CtInvocationImpl][CtFieldReadImpl]context.nextBinding());
                        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]recordsArray[[CtVariableReadImpl]i] = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.maybeOption([CtVariableReadImpl]isNullableField, [CtVariableReadImpl]record);
                    }
                } else [CtBlockImpl]{
                    [CtForImpl][CtCommentImpl]// need to parse literals into DDLogRecords
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]rowElements.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNullableField = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fields.get([CtVariableReadImpl]i).getDataType().nullable();
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord result = [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]rowElements[[CtVariableReadImpl]i].accept([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.PARSE_LITERALS);
                        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]recordsArray[[CtVariableReadImpl]i] = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.maybeOption([CtVariableReadImpl]isNullableField, [CtVariableReadImpl]result);
                    }
                }
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record = [CtInvocationImpl][CtTypeAccessImpl]ddlogapi.DDlogRecord.makeStruct([CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.ddlogTableTypeName([CtVariableReadImpl]tableName), [CtVariableReadImpl]recordsArray);
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand command = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand([CtFieldReadImpl]DDlogCommand.Kind.Insert, [CtVariableReadImpl]tableId, [CtVariableReadImpl]record);
                    [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.applyUpdates([CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand[]{ [CtVariableReadImpl]command });
                }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtVariableReadImpl]e);
                }
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer>> result = [CtInvocationImpl][CtFieldReadImpl]dslContext.newResult([CtFieldReadImpl]updateCountField);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer> resultRecord = [CtInvocationImpl][CtFieldReadImpl]dslContext.newRecord([CtFieldReadImpl]updateCountField);
            [CtInvocationImpl][CtVariableReadImpl]resultRecord.setValue([CtFieldReadImpl]updateCountField, [CtFieldReadImpl][CtVariableReadImpl]values.length);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]resultRecord);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtFieldReadImpl][CtVariableReadImpl]values.length, [CtVariableReadImpl]result);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult visitDelete([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// The assertions below, and in the ParseWhereClauseForDeletes visitor encode assumption A3
            [CtCommentImpl]// (see javadoc for the DDlogJooqProvider class)
            final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlDelete delete = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlDelete) (call));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) ([CtVariableReadImpl]delete.getTargetTable())).getSimple();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]delete.getCondition() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtBinaryOperatorImpl][CtLiteralImpl]"Delete queries without where clauses are unsupported: " + [CtInvocationImpl][CtFieldReadImpl]context.sql());
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall where = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall) ([CtVariableReadImpl]delete.getCondition()));
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> pkFields = [CtInvocationImpl][CtFieldReadImpl]tablesToPrimaryKeys.get([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase());
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.matchExpressionFromWhere([CtVariableReadImpl]where, [CtVariableReadImpl]pkFields, [CtFieldReadImpl]context);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int tableId = [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.getTableId([CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.ddlogRelationName([CtVariableReadImpl]tableName));
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand command = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand([CtFieldReadImpl]DDlogCommand.Kind.DeleteKey, [CtVariableReadImpl]tableId, [CtVariableReadImpl]record);
                [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.applyUpdates([CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand[]{ [CtVariableReadImpl]command });
            }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer>> result = [CtInvocationImpl][CtFieldReadImpl]dslContext.newResult([CtFieldReadImpl]updateCountField);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer> resultRecord = [CtInvocationImpl][CtFieldReadImpl]dslContext.newRecord([CtFieldReadImpl]updateCountField);
            [CtInvocationImpl][CtVariableReadImpl]resultRecord.setValue([CtFieldReadImpl]updateCountField, [CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]resultRecord);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtLiteralImpl]1, [CtVariableReadImpl]result);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult visitUpdate([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// The assertions below, and in the ParseWhereClauseForDeletes visitor encode assumption A4
            [CtCommentImpl]// (see javadoc for the DDlogJooqProvider class)
            final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlUpdate update = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlUpdate) (call));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) ([CtVariableReadImpl]update.getTargetTable())).getSimple();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]update.getCondition() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtBinaryOperatorImpl][CtLiteralImpl]"Delete queries without where clauses are unsupported: " + [CtInvocationImpl][CtFieldReadImpl]context.sql());
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> allFields = [CtInvocationImpl][CtFieldReadImpl]tablesToFields.get([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase());
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int numColumnsToUpdate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]update.getTargetColumnList().size();
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] updatedValues = [CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord[[CtVariableReadImpl]numColumnsToUpdate];
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] columnsToUpdate = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtVariableReadImpl]numColumnsToUpdate];
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]numColumnsToUpdate; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String columnName = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) ([CtInvocationImpl][CtVariableReadImpl]update.getTargetColumnList().get([CtVariableReadImpl]i))).getSimple().toLowerCase();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?> field = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]allFields.stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.getUnqualifiedName().last().equalsIgnoreCase([CtVariableReadImpl]columnName)).findFirst().get();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNullableField = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getDataType().nullable();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord valueToUpdateTo = [CtConditionalImpl]([CtInvocationImpl][CtFieldReadImpl]context.hasBinding()) ? [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.toValue([CtVariableReadImpl]field, [CtInvocationImpl][CtFieldReadImpl]context.nextBinding()) : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]update.getSourceExpressionList().accept([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.PARSE_LITERALS);
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord maybeWrapped = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.maybeOption([CtVariableReadImpl]isNullableField, [CtVariableReadImpl]valueToUpdateTo);
                    [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]updatedValues[[CtVariableReadImpl]i] = [CtVariableReadImpl]maybeWrapped;
                    [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]columnsToUpdate[[CtVariableReadImpl]i] = [CtVariableReadImpl]columnName;
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall where = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall) ([CtVariableReadImpl]update.getCondition()));
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> pkFields = [CtInvocationImpl][CtFieldReadImpl]tablesToPrimaryKeys.get([CtInvocationImpl][CtVariableReadImpl]tableName.toUpperCase());
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord key = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.matchExpressionFromWhere([CtVariableReadImpl]where, [CtVariableReadImpl]pkFields, [CtFieldReadImpl]context);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord updateRecord = [CtInvocationImpl][CtTypeAccessImpl]ddlogapi.DDlogRecord.makeNamedStruct([CtLiteralImpl]"", [CtVariableReadImpl]columnsToUpdate, [CtVariableReadImpl]updatedValues);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int tableId = [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.getTableId([CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.ddlogRelationName([CtVariableReadImpl]tableName));
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand command = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand([CtFieldReadImpl]DDlogCommand.Kind.Modify, [CtVariableReadImpl]tableId, [CtVariableReadImpl]key, [CtVariableReadImpl]updateRecord);
                [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.applyUpdates([CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecCommand[]{ [CtVariableReadImpl]command });
            }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.exception([CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Result<[CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer>> result = [CtInvocationImpl][CtFieldReadImpl]dslContext.newResult([CtFieldReadImpl]updateCountField);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.Record1<[CtTypeReferenceImpl]java.lang.Integer> resultRecord = [CtInvocationImpl][CtFieldReadImpl]dslContext.newRecord([CtFieldReadImpl]updateCountField);
            [CtInvocationImpl][CtVariableReadImpl]resultRecord.setValue([CtFieldReadImpl]updateCountField, [CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]resultRecord);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtLiteralImpl]1, [CtVariableReadImpl]result);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]ddlogapi.DDlogRecord matchExpressionFromWhere([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall where, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> pkFields, [CtParameterImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context) throws [CtTypeReferenceImpl]ddlogapi.DDlogException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.WhereClauseToMatchExpression visitor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.WhereClauseToMatchExpression([CtVariableReadImpl]pkFields, [CtVariableReadImpl]context);
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] matchExpression = [CtInvocationImpl][CtVariableReadImpl]where.accept([CtVariableReadImpl]visitor);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]matchExpression.length > [CtLiteralImpl]1 ? [CtInvocationImpl][CtTypeAccessImpl]ddlogapi.DDlogRecord.makeTuple([CtVariableReadImpl]matchExpression) : [CtArrayReadImpl][CtVariableReadImpl]matchExpression[[CtLiteralImpl]0];
    }

    [CtClassImpl]private static final class WhereClauseToMatchExpression extends [CtTypeReferenceImpl]org.apache.calcite.sql.util.SqlBasicVisitor<[CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[]> {
        [CtFieldImpl]private final [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] matchExpressions;

        [CtFieldImpl]private final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> pkFields;

        [CtConstructorImpl]public WhereClauseToMatchExpression([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?>> pkFields, [CtParameterImpl]final [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.QueryContext context) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pkFields = [CtVariableReadImpl]pkFields;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.matchExpressions = [CtNewArrayImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord[[CtInvocationImpl][CtVariableReadImpl]pkFields.size()];
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] visit([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlCall call) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall expr = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlBasicCall) (call));
            [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]expr.getOperator().getKind()) {
                [CtCaseImpl]case [CtFieldReadImpl]AND :
                    [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.visit([CtVariableReadImpl]call);
                [CtCaseImpl]case [CtFieldReadImpl]EQUALS :
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlNode left = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]expr.getOperands()[[CtLiteralImpl]0];
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlNode right = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]expr.getOperands()[[CtLiteralImpl]1];
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]context.hasBinding()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlDynamicParam)) [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl]setMatchExpression([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) (left)), [CtInvocationImpl][CtFieldReadImpl]context.nextBinding());
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) && [CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlDynamicParam)) [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl]setMatchExpression([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) (right)), [CtInvocationImpl][CtFieldReadImpl]context.nextBinding());
                        }
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) && [CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlLiteral)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl]setMatchExpression([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) (left)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlLiteral) (right)));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]right instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) && [CtBinaryOperatorImpl]([CtVariableReadImpl]left instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.calcite.sql.SqlLiteral)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl]setMatchExpression([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier) (right)), [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.calcite.sql.SqlLiteral) (left)));
                    }
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected comparison expression: " + [CtVariableReadImpl]call);
                [CtCaseImpl]default :
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported expression in where clause " + [CtVariableReadImpl]call);
            }
        }

        [CtMethodImpl]private [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] setMatchExpression([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier identifier, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlLiteral literal) [CtBlockImpl]{
            [CtForImpl][CtCommentImpl]/* The match-expressions correspond to each column in the primary key, in the same
            order as the primary key declaration in the SQL create table statement.
             */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]pkFields.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pkFields.get([CtVariableReadImpl]i).getUnqualifiedName().last().equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]identifier.getSimple())) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNullable = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pkFields.get([CtVariableReadImpl]i).getDataType().nullable();
                    [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]matchExpressions[[CtVariableReadImpl]i] = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.maybeOption([CtVariableReadImpl]isNullable, [CtInvocationImpl][CtVariableReadImpl]literal.accept([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.PARSE_LITERALS));
                    [CtReturnImpl]return [CtFieldReadImpl]matchExpressions;
                }
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Field %s being queried is not a primary key", [CtVariableReadImpl]identifier));
        }

        [CtMethodImpl]private [CtArrayTypeReferenceImpl]ddlogapi.DDlogRecord[] setMatchExpression([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.calcite.sql.SqlIdentifier identifier, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Object parameter) [CtBlockImpl]{
            [CtForImpl][CtCommentImpl]/* The match-expressions correspond to each column in the primary key, in the same
            order as the primary key declaration in the SQL create table statement.
             */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]pkFields.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pkFields.get([CtVariableReadImpl]i).getUnqualifiedName().last().equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]identifier.getSimple())) [CtBlockImpl]{
                    [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]matchExpressions[[CtVariableReadImpl]i] = [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.toValue([CtInvocationImpl][CtFieldReadImpl]pkFields.get([CtVariableReadImpl]i), [CtVariableReadImpl]parameter);
                    [CtReturnImpl]return [CtFieldReadImpl]matchExpressions;
                }
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Field %s being queried is not a primary key", [CtVariableReadImpl]identifier));
        }
    }

    [CtMethodImpl][CtCommentImpl]/* This corresponds to the naming convention followed by the SQL -> DDlog compiler */
    private static [CtTypeReferenceImpl]java.lang.String ddlogTableTypeName([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String tableName) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"T" + [CtInvocationImpl][CtVariableReadImpl]tableName.toLowerCase();
    }

    [CtMethodImpl][CtCommentImpl]/* This corresponds to the naming convention followed by the SQL -> DDlog compiler */
    private static [CtTypeReferenceImpl]java.lang.String ddlogRelationName([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String tableName) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"R" + [CtInvocationImpl][CtVariableReadImpl]tableName.toLowerCase();
    }

    [CtMethodImpl][CtCommentImpl]/* This corresponds to the naming convention followed by the SQL -> DDlog compiler */
    private static [CtTypeReferenceImpl]java.lang.String relationNameToTableName([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String relationName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]relationName.substring([CtLiteralImpl]1).toUpperCase();
    }

    [CtMethodImpl][CtCommentImpl]/* The SQL -> DDlog compiler represents nullable fields as ddlog Option<> types. We therefore
    wrap DDlogRecords if needed.
     */
    private static [CtTypeReferenceImpl]ddlogapi.DDlogRecord maybeOption([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Boolean isNullable, [CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]isNullable) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]ddlogapi.DDlogRecord.makeStruct([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.DDLOG_SOME, [CtVariableReadImpl]record);
            }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtVariableReadImpl]e);
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]record;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void structToValue([CtParameterImpl]final [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?> field, [CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Record jooqRecord) throws [CtTypeReferenceImpl]ddlogapi.DDlogException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isStruct = [CtInvocationImpl][CtVariableReadImpl]record.isStruct();
        [CtIfImpl]if ([CtVariableReadImpl]isStruct) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String structName = [CtInvocationImpl][CtVariableReadImpl]record.getStructName();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]structName.equals([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.DDLOG_NONE)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]jooqRecord.setValue([CtVariableReadImpl]field, [CtLiteralImpl]null);
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]structName.equals([CtFieldReadImpl]com.vmware.ddlog.DDlogJooqProvider.DDLOG_SOME)) [CtBlockImpl]{
                [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.setValue([CtVariableReadImpl]field, [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.getStructField([CtVariableReadImpl]record, [CtLiteralImpl]0), [CtVariableReadImpl]jooqRecord);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl]com.vmware.ddlog.DDlogJooqProvider.setValue([CtVariableReadImpl]field, [CtVariableReadImpl]record, [CtVariableReadImpl]jooqRecord);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]ddlogapi.DDlogRecord toValue([CtParameterImpl]final [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?> field, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Object in) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.DataType<[CtWildcardReferenceImpl]?> dataType = [CtInvocationImpl][CtVariableReadImpl]field.getDataType();
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]dataType.getSQLType()) {
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BOOLEAN :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord([CtVariableReadImpl](([CtTypeReferenceImpl]boolean) (in)));
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]INTEGER :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord([CtVariableReadImpl](([CtTypeReferenceImpl]int) (in)));
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord([CtVariableReadImpl](([CtTypeReferenceImpl]long) (in)));
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]VARCHAR :
                [CtTryImpl]try [CtBlockImpl]{
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ddlogapi.DDlogRecord([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (in)));
                }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtBinaryOperatorImpl][CtLiteralImpl]"Could not create String DDlogRecord for object: " + [CtVariableReadImpl]in);
                }
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown datatype " + [CtVariableReadImpl]field);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void setValue([CtParameterImpl]final [CtTypeReferenceImpl]org.jooq.Field<[CtWildcardReferenceImpl]?> field, [CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord in, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Record out) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jooq.DataType<[CtWildcardReferenceImpl]?> dataType = [CtInvocationImpl][CtVariableReadImpl]field.getDataType();
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]dataType.getSQLType()) {
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BOOLEAN :
                [CtInvocationImpl][CtVariableReadImpl]out.setValue([CtVariableReadImpl](([CtTypeReferenceImpl]org.jooq.Field<[CtTypeReferenceImpl]java.lang.Boolean>) (field)), [CtInvocationImpl][CtVariableReadImpl]in.getBoolean());
                [CtReturnImpl]return;
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]INTEGER :
                [CtInvocationImpl][CtVariableReadImpl]out.setValue([CtVariableReadImpl](([CtTypeReferenceImpl]org.jooq.Field<[CtTypeReferenceImpl]java.lang.Integer>) (field)), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]in.getInt().intValue());
                [CtReturnImpl]return;
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT :
                [CtInvocationImpl][CtVariableReadImpl]out.setValue([CtVariableReadImpl](([CtTypeReferenceImpl]org.jooq.Field<[CtTypeReferenceImpl]java.lang.Long>) (field)), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]in.getInt().longValue());
                [CtReturnImpl]return;
            [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]VARCHAR :
                [CtInvocationImpl][CtVariableReadImpl]out.setValue([CtVariableReadImpl](([CtTypeReferenceImpl]org.jooq.Field<[CtTypeReferenceImpl]java.lang.String>) (field)), [CtInvocationImpl][CtVariableReadImpl]in.getString());
                [CtReturnImpl]return;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown datatype " + [CtVariableReadImpl]field);
        }
    }

    [CtClassImpl]private static final class QueryContext {
        [CtFieldImpl]final [CtTypeReferenceImpl]java.lang.String sql;

        [CtFieldImpl]final [CtArrayTypeReferenceImpl]java.lang.Object[] binding;

        [CtFieldImpl][CtTypeReferenceImpl]int bindingIndex = [CtLiteralImpl]0;

        [CtConstructorImpl]QueryContext([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl]final [CtArrayTypeReferenceImpl]java.lang.Object[] binding) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sql = [CtVariableReadImpl]sql;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.binding = [CtVariableReadImpl]binding;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String sql() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]sql;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Object nextBinding() [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object ret = [CtArrayReadImpl][CtFieldReadImpl]binding[[CtFieldReadImpl]bindingIndex];
            [CtUnaryOperatorImpl][CtFieldWriteImpl]bindingIndex++;
            [CtReturnImpl]return [CtVariableReadImpl]ret;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasBinding() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]binding.length > [CtLiteralImpl]0;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getTableName([CtParameterImpl]final [CtTypeReferenceImpl]int relationId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]dDlogAPI.getTableName([CtVariableReadImpl]relationId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]ddlogapi.DDlogRecord getStructField([CtParameterImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogRecord record, [CtParameterImpl]final [CtTypeReferenceImpl]int fieldIndex) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]record.getStructField([CtVariableReadImpl]fieldIndex);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]ddlogapi.DDlogException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vmware.ddlog.DDlogJooqProvider.DDlogJooqProviderException([CtVariableReadImpl]e);
        }
    }

    [CtClassImpl]public static final class DDlogJooqProviderException extends [CtTypeReferenceImpl]java.lang.RuntimeException {
        [CtConstructorImpl]private DDlogJooqProviderException([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]e);
        }

        [CtConstructorImpl]private DDlogJooqProviderException([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]msg);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult exception([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtVariableReadImpl]msg));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult exception([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jooq.tools.jdbc.MockResult([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtVariableReadImpl]e));
    }
}
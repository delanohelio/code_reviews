[CompilationUnitImpl][CtCommentImpl]/* Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.facebook.presto.jdbc;
[CtImportImpl]import java.sql.NClob;
[CtImportImpl]import java.sql.SQLWarning;
[CtImportImpl]import java.util.Locale;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import static java.util.concurrent.TimeUnit.MINUTES;
[CtImportImpl]import java.nio.charset.CharsetEncoder;
[CtUnresolvedImport]import io.airlift.units.Duration;
[CtImportImpl]import static java.nio.charset.StandardCharsets.US_ASCII;
[CtUnresolvedImport]import static com.google.common.base.Preconditions.checkArgument;
[CtImportImpl]import java.sql.ResultSet;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.google.common.base.Splitter;
[CtImportImpl]import java.sql.Blob;
[CtImportImpl]import static java.util.concurrent.TimeUnit.MILLISECONDS;
[CtUnresolvedImport]import com.google.common.collect.ImmutableSet;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import com.facebook.presto.client.ClientSession;
[CtImportImpl]import static java.lang.String.format;
[CtImportImpl]import java.sql.DatabaseMetaData;
[CtImportImpl]import java.sql.SQLXML;
[CtImportImpl]import java.util.TimeZone;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.sql.Clob;
[CtImportImpl]import java.sql.SQLFeatureNotSupportedException;
[CtImportImpl]import java.sql.SQLException;
[CtUnresolvedImport]import static com.google.common.base.Strings.nullToEmpty;
[CtImportImpl]import static java.util.Objects.requireNonNull;
[CtImportImpl]import static java.util.concurrent.TimeUnit.DAYS;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import com.facebook.presto.client.StatementClient;
[CtImportImpl]import java.net.URI;
[CtImportImpl]import java.sql.Struct;
[CtImportImpl]import java.sql.Savepoint;
[CtImportImpl]import java.util.function.Function;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtImportImpl]import java.util.concurrent.Executor;
[CtImportImpl]import java.sql.CallableStatement;
[CtUnresolvedImport]import com.facebook.presto.spi.security.SelectedRole;
[CtImportImpl]import java.sql.Connection;
[CtImportImpl]import java.sql.PreparedStatement;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import com.google.common.primitives.Ints;
[CtImportImpl]import java.sql.Array;
[CtImportImpl]import java.sql.SQLClientInfoException;
[CtUnresolvedImport]import static com.google.common.collect.Maps.fromProperties;
[CtImportImpl]import java.sql.Statement;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import com.facebook.presto.client.ServerInfo;
[CtImportImpl]import java.util.concurrent.atomic.AtomicLong;
[CtClassImpl]public class PrestoConnection implements [CtTypeReferenceImpl]java.sql.Connection {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean closed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean autoCommit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]true);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger isolationLevel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger([CtFieldReadImpl]java.sql.Connection.TRANSACTION_READ_UNCOMMITTED);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean readOnly = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.String> catalog = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.String> schema = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.String> timeZoneId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.util.Locale> locale = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.Integer> networkTimeoutMillis = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtInvocationImpl][CtTypeAccessImpl]com.google.common.primitives.Ints.saturatedCast([CtInvocationImpl][CtFieldReadImpl]java.util.concurrent.TimeUnit.MINUTES.toMillis([CtLiteralImpl]2)));

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]com.facebook.presto.client.ServerInfo> serverInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong nextStatementId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong([CtLiteralImpl]1);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.net.URI jdbcUri;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.net.URI httpUri;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String user;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean compressionDisabled;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> extraCredentials;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> sessionProperties;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> applicationNamePrefix;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> clientInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> preparedStatements = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.spi.security.SelectedRole> roles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.String> transactionId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryExecutor queryExecutor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.facebook.presto.jdbc.WarningsManager warningsManager = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.WarningsManager();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor> queryInterceptorInstances;

    [CtConstructorImpl]PrestoConnection([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoDriverUri uri, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryExecutor queryExecutor) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]uri, [CtLiteralImpl]"uri is null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jdbcUri = [CtInvocationImpl][CtVariableReadImpl]uri.getJdbcUri();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.httpUri = [CtInvocationImpl][CtVariableReadImpl]uri.getHttpUri();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.schema.set([CtInvocationImpl][CtVariableReadImpl]uri.getSchema());
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.catalog.set([CtInvocationImpl][CtVariableReadImpl]uri.getCatalog());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.user = [CtInvocationImpl][CtVariableReadImpl]uri.getUser();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.applicationNamePrefix = [CtInvocationImpl][CtVariableReadImpl]uri.getApplicationNamePrefix();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.compressionDisabled = [CtInvocationImpl][CtVariableReadImpl]uri.isCompressionDisabled();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.extraCredentials = [CtInvocationImpl][CtVariableReadImpl]uri.getExtraCredentials();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sessionProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>([CtInvocationImpl][CtVariableReadImpl]uri.getSessionProperties());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queryExecutor = [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]queryExecutor, [CtLiteralImpl]"queryExecutor is null");
        [CtInvocationImpl][CtFieldReadImpl]timeZoneId.set([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getDefault().getID());
        [CtInvocationImpl][CtFieldReadImpl]locale.set([CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queryInterceptorInstances = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.copyOf([CtInvocationImpl][CtVariableReadImpl]uri.getQueryInterceptors());
        [CtInvocationImpl]initializeQueryInterceptors();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Statement createStatement() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtBinaryOperatorImpl][CtLiteralImpl]"statement" + [CtInvocationImpl][CtFieldReadImpl]nextStatementId.getAndIncrement();
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoPreparedStatement([CtThisAccessImpl]this, [CtVariableReadImpl]name, [CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.CallableStatement prepareCall([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.NotImplementedException([CtLiteralImpl]"Connection", [CtLiteralImpl]"prepareCall");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String nativeSQL([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtVariableReadImpl]sql;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setAutoCommit([CtParameterImpl][CtTypeReferenceImpl]boolean autoCommit) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean wasAutoCommit = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.autoCommit.getAndSet([CtVariableReadImpl]autoCommit);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]autoCommit && [CtUnaryOperatorImpl](![CtVariableReadImpl]wasAutoCommit)) [CtBlockImpl]{
            [CtInvocationImpl]commit();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean getAutoCommit() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]autoCommit.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void commit() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtIfImpl]if ([CtInvocationImpl]getAutoCommit()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Connection is in auto-commit mode");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement statement = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement([CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statement.internalExecute([CtLiteralImpl]"COMMIT");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void rollback() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtIfImpl]if ([CtInvocationImpl]getAutoCommit()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Connection is in auto-commit mode");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement statement = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement([CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statement.internalExecute([CtLiteralImpl]"ROLLBACK");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]closed.get()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]transactionId.get() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement statement = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoStatement([CtThisAccessImpl]this)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]statement.internalExecute([CtLiteralImpl]"ROLLBACK");
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable innerException = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor queryInterceptor : [CtFieldReadImpl][CtThisAccessImpl]this.queryInterceptorInstances) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]queryInterceptor.destroy();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]innerException == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]innerException = [CtVariableReadImpl]t;
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]innerException != [CtVariableReadImpl]t) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]innerException.addSuppressed([CtVariableReadImpl]t);
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]innerException != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]innerException);
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]closed.set([CtLiteralImpl]true);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isClosed() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]closed.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.DatabaseMetaData getMetaData() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoDatabaseMetaData([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setReadOnly([CtParameterImpl][CtTypeReferenceImpl]boolean readOnly) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.readOnly.set([CtVariableReadImpl]readOnly);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isReadOnly() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]readOnly.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setCatalog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalog) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.catalog.set([CtVariableReadImpl]catalog);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getCatalog() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]catalog.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setTransactionIsolation([CtParameterImpl][CtTypeReferenceImpl]int level) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.getIsolationLevel([CtVariableReadImpl]level);
        [CtInvocationImpl][CtFieldReadImpl]isolationLevel.set([CtVariableReadImpl]level);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"MagicConstant")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getTransactionIsolation() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]isolationLevel.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.SQLWarning getWarnings() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void clearWarnings() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Statement createStatement([CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkResultSet([CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
        [CtReturnImpl]return [CtInvocationImpl]createStatement();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkResultSet([CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
        [CtReturnImpl]return [CtInvocationImpl]prepareStatement([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.CallableStatement prepareCall([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkResultSet([CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"prepareCall");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> getTypeMap() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"getTypeMap");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setTypeMap([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> map) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"setTypeMap");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setHoldability([CtParameterImpl][CtTypeReferenceImpl]int holdability) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]holdability != [CtFieldReadImpl][CtTypeAccessImpl]java.sql.ResultSet.[CtFieldReferenceImpl]HOLD_CURSORS_OVER_COMMIT) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"Changing holdability not supported");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getHoldability() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.sql.ResultSet.[CtFieldReferenceImpl]HOLD_CURSORS_OVER_COMMIT;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Savepoint setSavepoint() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"setSavepoint");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Savepoint setSavepoint([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"setSavepoint");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void rollback([CtParameterImpl][CtTypeReferenceImpl]java.sql.Savepoint savepoint) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"rollback");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void releaseSavepoint([CtParameterImpl][CtTypeReferenceImpl]java.sql.Savepoint savepoint) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"releaseSavepoint");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Statement createStatement([CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency, [CtParameterImpl][CtTypeReferenceImpl]int resultSetHoldability) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkHoldability([CtVariableReadImpl]resultSetHoldability);
        [CtReturnImpl]return [CtInvocationImpl]createStatement([CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency, [CtParameterImpl][CtTypeReferenceImpl]int resultSetHoldability) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkHoldability([CtVariableReadImpl]resultSetHoldability);
        [CtReturnImpl]return [CtInvocationImpl]prepareStatement([CtVariableReadImpl]sql, [CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.CallableStatement prepareCall([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency, [CtParameterImpl][CtTypeReferenceImpl]int resultSetHoldability) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.checkHoldability([CtVariableReadImpl]resultSetHoldability);
        [CtReturnImpl]return [CtInvocationImpl]prepareCall([CtVariableReadImpl]sql, [CtVariableReadImpl]resultSetType, [CtVariableReadImpl]resultSetConcurrency);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]int autoGeneratedKeys) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]autoGeneratedKeys != [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Statement.[CtFieldReferenceImpl]RETURN_GENERATED_KEYS) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"Auto generated keys must be NO_GENERATED_KEYS");
        }
        [CtReturnImpl]return [CtInvocationImpl]prepareStatement([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtArrayTypeReferenceImpl]int[] columnIndexes) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"prepareStatement");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.PreparedStatement prepareStatement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] columnNames) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"prepareStatement");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Clob createClob() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createClob");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Blob createBlob() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createBlob");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.NClob createNClob() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createNClob");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.SQLXML createSQLXML() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createSQLXML");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isValid([CtParameterImpl][CtTypeReferenceImpl]int timeout) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]timeout < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Timeout is negative");
        }
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl]isClosed();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setClientInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) throws [CtTypeReferenceImpl]java.sql.SQLClientInfoException [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]name, [CtLiteralImpl]"name is null");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientInfo.put([CtVariableReadImpl]name, [CtVariableReadImpl]value);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]clientInfo.remove([CtVariableReadImpl]name);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setClientInfo([CtParameterImpl][CtTypeReferenceImpl]java.util.Properties properties) throws [CtTypeReferenceImpl]java.sql.SQLClientInfoException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]clientInfo.putAll([CtInvocationImpl]fromProperties([CtVariableReadImpl]properties));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getClientInfo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]clientInfo.get([CtVariableReadImpl]name);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Properties getClientInfo() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtFieldReadImpl]clientInfo.entrySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]properties.setProperty([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
        }
        [CtReturnImpl]return [CtVariableReadImpl]properties;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Array createArrayOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.String typeName, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] elements) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createArrayOf");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.sql.Struct createStruct([CtParameterImpl][CtTypeReferenceImpl]java.lang.String typeName, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] attributes) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"createStruct");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setSchema([CtParameterImpl][CtTypeReferenceImpl]java.lang.String schema) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.schema.set([CtVariableReadImpl]schema);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getSchema() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]schema.get();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTimeZoneId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]timeZoneId.get();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTimeZoneId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String timeZoneId) [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]timeZoneId, [CtLiteralImpl]"timeZoneId is null");
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.timeZoneId.set([CtVariableReadImpl]timeZoneId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Locale getLocale() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]locale.get();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLocale([CtParameterImpl][CtTypeReferenceImpl]java.util.Locale locale) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.locale.set([CtVariableReadImpl]locale);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds a session property (experimental).
     */
    public [CtTypeReferenceImpl]void setSessionProperty([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]name, [CtLiteralImpl]"name is null");
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]value, [CtLiteralImpl]"value is null");
        [CtInvocationImpl]checkArgument([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]name.isEmpty(), [CtLiteralImpl]"name is empty");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.charset.CharsetEncoder charsetEncoder = [CtInvocationImpl][CtFieldReadImpl]java.nio.charset.StandardCharsets.US_ASCII.newEncoder();
        [CtInvocationImpl]checkArgument([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]name.indexOf([CtLiteralImpl]'=') < [CtLiteralImpl]0, [CtLiteralImpl]"Session property name must not contain '=': %s", [CtVariableReadImpl]name);
        [CtInvocationImpl]checkArgument([CtInvocationImpl][CtVariableReadImpl]charsetEncoder.canEncode([CtVariableReadImpl]name), [CtLiteralImpl]"Session property name is not US_ASCII: %s", [CtVariableReadImpl]name);
        [CtInvocationImpl]checkArgument([CtInvocationImpl][CtVariableReadImpl]charsetEncoder.canEncode([CtVariableReadImpl]value), [CtLiteralImpl]"Session property value is not US_ASCII: %s", [CtVariableReadImpl]value);
        [CtInvocationImpl][CtFieldReadImpl]sessionProperties.put([CtVariableReadImpl]name, [CtVariableReadImpl]value);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void setRole([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalog, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.spi.security.SelectedRole role) [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]catalog, [CtLiteralImpl]"catalog is null");
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]role, [CtLiteralImpl]"role is null");
        [CtInvocationImpl][CtFieldReadImpl]roles.put([CtVariableReadImpl]catalog, [CtVariableReadImpl]role);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.facebook.presto.spi.security.SelectedRole> getRoles() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtFieldReadImpl]roles);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void abort([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.Executor executor) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]close();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setNetworkTimeout([CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.Executor executor, [CtParameterImpl][CtTypeReferenceImpl]int milliseconds) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]milliseconds < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Timeout is negative");
        }
        [CtInvocationImpl][CtFieldReadImpl]networkTimeoutMillis.set([CtVariableReadImpl]milliseconds);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getNetworkTimeout() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtInvocationImpl]checkOpen();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]networkTimeoutMillis.get();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T unwrap([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> iface) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isWrapperFor([CtVariableReadImpl]iface)) [CtBlockImpl]{
            [CtReturnImpl]return [CtThisAccessImpl](([CtTypeParameterReferenceImpl]T) (this));
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtBinaryOperatorImpl][CtLiteralImpl]"No wrapper for " + [CtVariableReadImpl]iface);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isWrapperFor([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> iface) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]iface.isInstance([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.net.URI getURI() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jdbcUri;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String getUser() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]user;
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getExtraCredentials() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtFieldReadImpl]extraCredentials);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getSessionProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtFieldReadImpl]sessionProperties);
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.client.ServerInfo getServerInfo() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]serverInfo.get() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]serverInfo.set([CtInvocationImpl][CtFieldReadImpl]queryExecutor.getServerInfo([CtFieldReadImpl]httpUri));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Error fetching version from server", [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]serverInfo.get();
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor> getQueryInterceptorInstances() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.queryInterceptorInstances;
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean shouldStartTransaction() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]autoCommit.get()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]transactionId.get() == [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String getStartTransactionSql() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"START TRANSACTION ISOLATION LEVEL %s, READ %s", [CtInvocationImpl]com.facebook.presto.jdbc.PrestoConnection.getIsolationLevel([CtInvocationImpl][CtFieldReadImpl]isolationLevel.get()), [CtConditionalImpl][CtInvocationImpl][CtFieldReadImpl]readOnly.get() ? [CtLiteralImpl]"ONLY" : [CtLiteralImpl]"WRITE");
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.client.StatementClient startQuery([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> sessionPropertiesOverride) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String source = [CtLiteralImpl]"presto-jdbc";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String applicationName = [CtInvocationImpl][CtFieldReadImpl]clientInfo.get([CtLiteralImpl]"ApplicationName");
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]applicationNamePrefix.isPresent()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]source = [CtInvocationImpl][CtFieldReadImpl]applicationNamePrefix.get();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]applicationName != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]source += [CtVariableReadImpl]applicationName;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]applicationName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]source = [CtVariableReadImpl]applicationName;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> traceToken = [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]clientInfo.get([CtLiteralImpl]"TraceToken"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.lang.String> clientTags = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Splitter.on([CtLiteralImpl]',').trimResults().omitEmptyStrings().split([CtInvocationImpl]nullToEmpty([CtInvocationImpl][CtFieldReadImpl]clientInfo.get([CtLiteralImpl]"ClientTags")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> allProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtFieldReadImpl]sessionProperties);
        [CtInvocationImpl][CtVariableReadImpl]allProperties.putAll([CtVariableReadImpl]sessionPropertiesOverride);
        [CtLocalVariableImpl][CtCommentImpl]// zero means no timeout, so use a huge value that is effectively unlimited
        [CtTypeReferenceImpl]int millis = [CtInvocationImpl][CtFieldReadImpl]networkTimeoutMillis.get();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.airlift.units.Duration timeout = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]millis > [CtLiteralImpl]0) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.airlift.units.Duration([CtVariableReadImpl]millis, [CtFieldReadImpl]java.util.concurrent.TimeUnit.MILLISECONDS) : [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.airlift.units.Duration([CtLiteralImpl]999, [CtFieldReadImpl]java.util.concurrent.TimeUnit.DAYS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.client.ClientSession session = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.facebook.presto.client.ClientSession([CtFieldReadImpl]httpUri, [CtFieldReadImpl]user, [CtVariableReadImpl]source, [CtVariableReadImpl]traceToken, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableSet.copyOf([CtVariableReadImpl]clientTags), [CtInvocationImpl][CtFieldReadImpl]clientInfo.get([CtLiteralImpl]"ClientInfo"), [CtInvocationImpl][CtFieldReadImpl]catalog.get(), [CtInvocationImpl][CtFieldReadImpl]schema.get(), [CtInvocationImpl][CtFieldReadImpl]timeZoneId.get(), [CtInvocationImpl][CtFieldReadImpl]locale.get(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtVariableReadImpl]allProperties), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtFieldReadImpl]preparedStatements), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.copyOf([CtFieldReadImpl]roles), [CtFieldReadImpl]extraCredentials, [CtInvocationImpl][CtFieldReadImpl]transactionId.get(), [CtVariableReadImpl]timeout, [CtFieldReadImpl]compressionDisabled);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]queryExecutor.startQuery([CtVariableReadImpl]session, [CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void updateSession([CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.client.StatementClient client) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getSetSessionProperties().forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]sessionProperties::put);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getResetSessionProperties().forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]sessionProperties::remove);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getAddedPreparedStatements().forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]preparedStatements::put);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getDeallocatedPreparedStatements().forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]preparedStatements::remove);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getSetCatalog().ifPresent([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]catalog::set);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.getSetSchema().ifPresent([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]schema::set);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]client.getStartedTransactionId() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]transactionId.set([CtInvocationImpl][CtVariableReadImpl]client.getStartedTransactionId());
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]client.isClearTransactionId()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]transactionId.set([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.WarningsManager getWarningsManager() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]warningsManager;
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet invokeQueryInterceptorsPre([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.sql.Statement interceptedStatement) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]processInterceptors([CtLambdaImpl]([CtParameterImpl]com.facebook.presto.jdbc.QueryInterceptor interceptor) -> [CtInvocationImpl][CtVariableReadImpl]interceptor.preProcess([CtVariableReadImpl]sql, [CtVariableReadImpl]interceptedStatement), [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet invokeQueryInterceptorsPost([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.sql.Statement interceptedStatement, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet originalResultSet) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]processInterceptors([CtLambdaImpl]([CtParameterImpl] interceptor) -> [CtInvocationImpl][CtVariableReadImpl]interceptor.postProcess([CtVariableReadImpl]sql, [CtVariableReadImpl]interceptedStatement, [CtVariableReadImpl]originalResultSet), [CtVariableReadImpl]originalResultSet);
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet processInterceptors([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor, [CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet> process, [CtParameterImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet originalResultSet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet interceptedResultSet = [CtVariableReadImpl]originalResultSet;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor interceptor : [CtFieldReadImpl][CtThisAccessImpl]this.queryInterceptorInstances) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet newResultSet = [CtInvocationImpl][CtVariableReadImpl]process.apply([CtVariableReadImpl]interceptor);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]newResultSet != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]newResultSet != [CtVariableReadImpl]interceptedResultSet)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.PrestoResultSet previousResultSet = [CtVariableReadImpl]interceptedResultSet;
                [CtAssignmentImpl][CtVariableWriteImpl]interceptedResultSet = [CtVariableReadImpl]newResultSet;
                [CtInvocationImpl][CtVariableReadImpl]interceptedResultSet.addInnerResultSet([CtVariableReadImpl]previousResultSet);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]interceptedResultSet;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkOpen() throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isClosed()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtLiteralImpl]"Connection is closed");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void initializeQueryInterceptors() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.facebook.presto.jdbc.QueryInterceptor interceptor : [CtFieldReadImpl][CtThisAccessImpl]this.queryInterceptorInstances) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]interceptor.init([CtFieldReadImpl][CtThisAccessImpl]this.sessionProperties);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void checkResultSet([CtParameterImpl][CtTypeReferenceImpl]int resultSetType, [CtParameterImpl][CtTypeReferenceImpl]int resultSetConcurrency) throws [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultSetType != [CtFieldReadImpl][CtTypeAccessImpl]java.sql.ResultSet.[CtFieldReferenceImpl]TYPE_FORWARD_ONLY) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"Result set type must be TYPE_FORWARD_ONLY");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultSetConcurrency != [CtFieldReadImpl][CtTypeAccessImpl]java.sql.ResultSet.[CtFieldReferenceImpl]CONCUR_READ_ONLY) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"Result set concurrency must be CONCUR_READ_ONLY");
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void checkHoldability([CtParameterImpl][CtTypeReferenceImpl]int resultSetHoldability) throws [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultSetHoldability != [CtFieldReadImpl][CtTypeAccessImpl]java.sql.ResultSet.[CtFieldReferenceImpl]HOLD_CURSORS_OVER_COMMIT) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLFeatureNotSupportedException([CtLiteralImpl]"Result set holdability must be HOLD_CURSORS_OVER_COMMIT");
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getIsolationLevel([CtParameterImpl][CtTypeReferenceImpl]int level) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]level) {
            [CtCaseImpl]case [CtFieldReadImpl]java.sql.Connection.TRANSACTION_READ_UNCOMMITTED :
                [CtReturnImpl]return [CtLiteralImpl]"READ UNCOMMITTED";
            [CtCaseImpl]case [CtFieldReadImpl]java.sql.Connection.TRANSACTION_READ_COMMITTED :
                [CtReturnImpl]return [CtLiteralImpl]"READ COMMITTED";
            [CtCaseImpl]case [CtFieldReadImpl]java.sql.Connection.TRANSACTION_REPEATABLE_READ :
                [CtReturnImpl]return [CtLiteralImpl]"REPEATABLE READ";
            [CtCaseImpl]case [CtFieldReadImpl]java.sql.Connection.TRANSACTION_SERIALIZABLE :
                [CtReturnImpl]return [CtLiteralImpl]"SERIALIZABLE";
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.SQLException([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid transaction isolation level: " + [CtVariableReadImpl]level);
    }
}
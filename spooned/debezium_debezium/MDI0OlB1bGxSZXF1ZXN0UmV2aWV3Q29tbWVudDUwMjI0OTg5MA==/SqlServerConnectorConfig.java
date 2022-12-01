[CompilationUnitImpl][CtCommentImpl]/* Copyright Debezium Authors.

Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
[CtPackageDeclarationImpl]package io.debezium.connector.sqlserver;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import io.debezium.relational.Tables.ColumnNameFilter;
[CtUnresolvedImport]import org.apache.kafka.common.config.ConfigDef.Type;
[CtUnresolvedImport]import io.debezium.connector.SourceInfoStructMaker;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.time.DateTimeException;
[CtUnresolvedImport]import io.debezium.document.Document;
[CtUnresolvedImport]import org.apache.kafka.common.config.ConfigDef.Width;
[CtUnresolvedImport]import org.apache.kafka.common.config.ConfigDef.Importance;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import io.debezium.config.EnumeratedValue;
[CtUnresolvedImport]import org.apache.kafka.common.config.ConfigDef;
[CtUnresolvedImport]import io.debezium.relational.Tables.TableFilter;
[CtUnresolvedImport]import io.debezium.function.Predicates;
[CtUnresolvedImport]import io.debezium.config.Field;
[CtUnresolvedImport]import io.debezium.config.ConfigDefinition;
[CtUnresolvedImport]import io.debezium.config.Configuration;
[CtImportImpl]import java.time.ZoneId;
[CtUnresolvedImport]import io.debezium.relational.TableId;
[CtUnresolvedImport]import io.debezium.config.CommonConnectorConfig;
[CtUnresolvedImport]import io.debezium.relational.RelationalDatabaseConnectorConfig;
[CtUnresolvedImport]import io.debezium.relational.history.HistoryRecordComparator;
[CtUnresolvedImport]import io.debezium.relational.ColumnId;
[CtUnresolvedImport]import io.debezium.relational.HistorizedRelationalDatabaseConnectorConfig;
[CtUnresolvedImport]import io.debezium.connector.AbstractSourceInfo;
[CtUnresolvedImport]import io.debezium.jdbc.JdbcConfiguration;
[CtClassImpl][CtJavaDocImpl]/**
 * The list of configuration options for SQL Server connector
 *
 * @author Jiri Pechanec
 */
public class SqlServerConnectorConfig extends [CtTypeReferenceImpl]io.debezium.relational.HistorizedRelationalDatabaseConnectorConfig {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.class);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SOURCE_TIMESTAMP_MODE_CONFIG_NAME = [CtLiteralImpl]"source.timestamp.mode";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]int DEFAULT_PORT = [CtLiteralImpl]1433;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String READ_ONLY_INTENT = [CtLiteralImpl]"ReadOnly";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String APPLICATION_INTENT_KEY = [CtLiteralImpl]"database.applicationIntent";

    [CtEnumImpl][CtJavaDocImpl]/**
     * The set of predefined SnapshotMode options or aliases.
     */
    public static enum SnapshotMode implements [CtTypeReferenceImpl]io.debezium.config.EnumeratedValue {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Perform a snapshot of data and schema upon initial startup of a connector.
         */
        INITIAL([CtLiteralImpl]"initial", [CtLiteralImpl]true),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Perform a snapshot of data and schema upon initial startup of a connector but does not transition to streaming.
         */
        INITIAL_ONLY([CtLiteralImpl]"initial_only", [CtLiteralImpl]true),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * Perform a snapshot of the schema but no data upon initial startup of a connector.
         */
        SCHEMA_ONLY([CtLiteralImpl]"schema_only", [CtLiteralImpl]false);
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String value;

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean includeData;

        [CtConstructorImpl]private SnapshotMode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value, [CtParameterImpl][CtTypeReferenceImpl]boolean includeData) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.value = [CtVariableReadImpl]value;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.includeData = [CtVariableReadImpl]includeData;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]value;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Whether this snapshotting mode should include the actual data or just the
         * schema of captured tables.
         */
        public [CtTypeReferenceImpl]boolean includeData() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]includeData;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Determine if the supplied value is one of the predefined options.
         *
         * @param value
         * 		the configuration property value; may not be null
         * @return the matching option, or null if no match is found
         */
        public static [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.trim();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode option : [CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]option.getValue().equalsIgnoreCase([CtVariableReadImpl]value)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]option;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Determine if the supplied value is one of the predefined options.
         *
         * @param value
         * 		the configuration property value; may not be null
         * @param defaultValue
         * 		the default value; may be null
         * @return the matching option, or null if no match is found and the non-null default is invalid
         */
        public static [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultValue) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode mode = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.parse([CtVariableReadImpl]value);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mode == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]defaultValue != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]mode = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.parse([CtVariableReadImpl]defaultValue);
            }
            [CtReturnImpl]return [CtVariableReadImpl]mode;
        }
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * The set of predefined snapshot isolation mode options.
     */
    public static enum SnapshotIsolationMode implements [CtTypeReferenceImpl]io.debezium.config.EnumeratedValue {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * This mode will block all reads and writes for the entire duration of the snapshot.
         *
         * The connector will execute {@code SELECT * FROM .. WITH (TABLOCKX)}
         */
        EXCLUSIVE([CtLiteralImpl]"exclusive"),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * This mode uses SNAPSHOT isolation level. This way reads and writes are not blocked for the entire duration
         * of the snapshot.  Snapshot consistency is guaranteed as long as DDL statements are not executed at the time.
         */
        SNAPSHOT([CtLiteralImpl]"snapshot"),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * This mode uses REPEATABLE READ isolation level. This mode will avoid taking any table
         * locks during the snapshot process, except schema snapshot phase where exclusive table
         * locks are acquired for a short period.  Since phantom reads can occur, it does not fully
         * guarantee consistency.
         */
        REPEATABLE_READ([CtLiteralImpl]"repeatable_read"),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * This mode uses READ COMMITTED isolation level. This mode does not take any table locks during
         * the snapshot process. In addition, it does not take any long-lasting row-level locks, like
         * in repeatable read isolation level. Snapshot consistency is not guaranteed.
         */
        READ_COMMITTED([CtLiteralImpl]"read_committed"),
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * This mode uses READ UNCOMMITTED isolation level. This mode takes neither table locks nor row-level locks
         * during the snapshot process.  This way other transactions are not affected by initial snapshot process.
         * However, snapshot consistency is not guaranteed.
         */
        READ_UNCOMMITTED([CtLiteralImpl]"read_uncommitted");
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String value;

        [CtConstructorImpl]private SnapshotIsolationMode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.value = [CtVariableReadImpl]value;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]value;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Determine if the supplied value is one of the predefined options.
         *
         * @param value
         * 		the configuration property value; may not be null
         * @return the matching option, or null if no match is found
         */
        public static [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.trim();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode option : [CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]option.getValue().equalsIgnoreCase([CtVariableReadImpl]value)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]option;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Determine if the supplied value is one of the predefined options.
         *
         * @param value
         * 		the configuration property value; may not be null
         * @param defaultValue
         * 		the default value; may be null
         * @return the matching option, or null if no match is found and the non-null default is invalid
         */
        public static [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultValue) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode mode = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.parse([CtVariableReadImpl]value);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mode == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]defaultValue != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]mode = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.parse([CtVariableReadImpl]defaultValue);
            }
            [CtReturnImpl]return [CtVariableReadImpl]mode;
        }
    }

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field HOSTNAME = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]io.debezium.jdbc.JdbcConfiguration.HOSTNAME).withDisplayName([CtLiteralImpl]"Hostname").withType([CtTypeAccessImpl]Type.STRING).withWidth([CtTypeAccessImpl]Width.MEDIUM).withImportance([CtTypeAccessImpl]Importance.HIGH).withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Field::isRequired).withDescription([CtLiteralImpl]"Resolvable hostname or IP address of the SQL Server database server.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field PORT = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]io.debezium.jdbc.JdbcConfiguration.PORT).withDisplayName([CtLiteralImpl]"Port").withType([CtTypeAccessImpl]Type.INT).withWidth([CtTypeAccessImpl]Width.SHORT).withDefault([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.DEFAULT_PORT).withImportance([CtTypeAccessImpl]Importance.HIGH).withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Field::isInteger).withDescription([CtLiteralImpl]"Port of the SQL Server database server.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field USER = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]io.debezium.jdbc.JdbcConfiguration.USER).withDisplayName([CtLiteralImpl]"User").withType([CtTypeAccessImpl]Type.STRING).withWidth([CtTypeAccessImpl]Width.SHORT).withImportance([CtTypeAccessImpl]Importance.HIGH).withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Field::isRequired).withDescription([CtLiteralImpl]"Name of the SQL Server database user to be used when connecting to the database.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field PASSWORD = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]io.debezium.jdbc.JdbcConfiguration.PASSWORD).withDisplayName([CtLiteralImpl]"Password").withType([CtTypeAccessImpl]Type.PASSWORD).withWidth([CtTypeAccessImpl]Width.SHORT).withImportance([CtTypeAccessImpl]Importance.HIGH).withDescription([CtLiteralImpl]"Password of the SQL Server database user to be used when connecting to the database.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field SERVER_NAME = [CtInvocationImpl][CtTypeAccessImpl]RelationalDatabaseConnectorConfig.SERVER_NAME.withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]CommonConnectorConfig::validateServerNameIsDifferentFromHistoryTopicName);

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field DATABASE_NAME = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]io.debezium.jdbc.JdbcConfiguration.DATABASE).withDisplayName([CtLiteralImpl]"Database name").withType([CtTypeAccessImpl]Type.STRING).withWidth([CtTypeAccessImpl]Width.MEDIUM).withImportance([CtTypeAccessImpl]Importance.HIGH).withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Field::isRequired).withDescription([CtBinaryOperatorImpl][CtLiteralImpl]"The name of the database the connector should be monitoring. When working with a " + [CtLiteralImpl]"multi-tenant set-up, must be set to the CDB name.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field INSTANCE = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]SqlServerConnection.INSTANCE_NAME).withDisplayName([CtLiteralImpl]"Instance name").withType([CtTypeAccessImpl]Type.STRING).withImportance([CtTypeAccessImpl]Importance.LOW).withValidation([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Field::isOptional).withDescription([CtLiteralImpl]"The SQL Server instance name");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field SERVER_TIMEZONE = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtBinaryOperatorImpl][CtFieldReadImpl]DATABASE_CONFIG_PREFIX + [CtFieldReadImpl]SqlServerConnection.SERVER_TIMEZONE_PROP_NAME).withDisplayName([CtLiteralImpl]"Server timezone").withType([CtTypeAccessImpl]Type.STRING).withImportance([CtTypeAccessImpl]Importance.LOW).withValidation([CtLambdaImpl]([CtParameterImpl] config,[CtParameterImpl] field,[CtParameterImpl] problems) -> [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String value = [CtInvocationImpl][CtVariableReadImpl]config.getString([CtVariableReadImpl]field);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.of([CtVariableReadImpl]value, [CtVariableReadImpl]ZoneId.SHORT_IDS);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.time.DateTimeException e) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]problems.accept([CtVariableReadImpl]field, [CtVariableReadImpl]value, [CtLiteralImpl]"The value must be a valid ZoneId");
                [CtReturnImpl]return [CtLiteralImpl]1;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]0;
    }).withDescription([CtBinaryOperatorImpl][CtLiteralImpl]"The timezone of the server used to correctly shift the commit transaction timestamp on the client side" + [CtLiteralImpl]"Options include: Any valid Java ZoneId");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field MAX_LSN_OPTIMIZATION = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.createInternal([CtLiteralImpl]"streaming.lsn.optimization").withDisplayName([CtLiteralImpl]"Max LSN Optimization").withDefault([CtLiteralImpl]true).withType([CtTypeAccessImpl]Type.BOOLEAN).withImportance([CtTypeAccessImpl]Importance.LOW).withDescription([CtLiteralImpl]"This property can be used to enable/disable an optimization that prevents querying the cdc tables on LSNs not correlated to changes.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field SOURCE_TIMESTAMP_MODE = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SOURCE_TIMESTAMP_MODE_CONFIG_NAME).withDisplayName([CtLiteralImpl]"Source timestamp mode").withDefault([CtInvocationImpl][CtTypeAccessImpl]SourceTimestampMode.COMMIT.getValue()).withType([CtTypeAccessImpl]Type.STRING).withWidth([CtTypeAccessImpl]Width.SHORT).withImportance([CtTypeAccessImpl]Importance.LOW).withDescription([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Configures the criteria of the attached timestamp within the source record (ts_ms)." + [CtLiteralImpl]"Options include:") + [CtLiteralImpl]"'") + [CtInvocationImpl][CtTypeAccessImpl]SourceTimestampMode.COMMIT.getValue()) + [CtLiteralImpl]"', (default) the source timestamp is set to the instant where the record was committed in the database") + [CtLiteralImpl]"'") + [CtInvocationImpl][CtTypeAccessImpl]SourceTimestampMode.PROCESSING.getValue()) + [CtLiteralImpl]"', the source timestamp is set to the instant where the record was processed by Debezium.");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field SNAPSHOT_MODE = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtLiteralImpl]"snapshot.mode").withDisplayName([CtLiteralImpl]"Snapshot mode").withEnum([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.class, [CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.[CtFieldReferenceImpl]INITIAL).withWidth([CtTypeAccessImpl]Width.SHORT).withImportance([CtTypeAccessImpl]Importance.LOW).withDescription([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The criteria for running a snapshot upon startup of the connector. " + [CtLiteralImpl]"Options include: ") + [CtLiteralImpl]"'initial' (the default) to specify the connector should run a snapshot only when no offsets are available for the logical server name; ") + [CtLiteralImpl]"'schema_only' to specify the connector should run a snapshot of the schema when no offsets are available for the logical server name. ");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.debezium.config.Field SNAPSHOT_ISOLATION_MODE = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.create([CtLiteralImpl]"snapshot.isolation.mode").withDisplayName([CtLiteralImpl]"Snapshot isolation mode").withEnum([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.class, [CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]REPEATABLE_READ).withWidth([CtTypeAccessImpl]Width.SHORT).withImportance([CtTypeAccessImpl]Importance.LOW).withDescription([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Controls which transaction isolation level is used and how long the connector locks the monitored tables. " + [CtLiteralImpl]"The default is '") + [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]REPEATABLE_READ.getValue()) + [CtLiteralImpl]"', which means that repeatable read isolation level is used. In addition, exclusive locks are taken only during schema snapshot. ") + [CtLiteralImpl]"Using a value of '") + [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]EXCLUSIVE.getValue()) + [CtLiteralImpl]"' ensures that the connector holds the exclusive lock (and thus prevents any reads and updates) for all monitored tables during the entire snapshot duration. ") + [CtLiteralImpl]"When '") + [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]SNAPSHOT.getValue()) + [CtLiteralImpl]"' is specified, connector runs the initial snapshot in SNAPSHOT isolation level, which guarantees snapshot consistency. In addition, neither table nor row-level locks are held. ") + [CtLiteralImpl]"When '") + [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]READ_COMMITTED.getValue()) + [CtLiteralImpl]"' is specified, connector runs the initial snapshot in READ COMMITTED isolation level. No long-running locks are taken, so that initial snapshot does not prevent ") + [CtLiteralImpl]"other transactions from updating table rows. Snapshot consistency is not guaranteed.") + [CtLiteralImpl]"In '") + [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]READ_UNCOMMITTED.getValue()) + [CtLiteralImpl]"' mode neither table nor row-level locks are acquired, but connector does not guarantee snapshot consistency.");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.debezium.config.ConfigDefinition CONFIG_DEFINITION = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]HistorizedRelationalDatabaseConnectorConfig.CONFIG_DEFINITION.edit().name([CtLiteralImpl]"SQL Server").type([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.DATABASE_NAME, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.HOSTNAME, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.PORT, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.USER, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.PASSWORD, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SERVER_TIMEZONE, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.INSTANCE).connector([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_MODE, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_ISOLATION_MODE, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SOURCE_TIMESTAMP_MODE, [CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.MAX_LSN_OPTIMIZATION).excluding([CtTypeAccessImpl]io.debezium.connector.sqlserver.SCHEMA_WHITELIST, [CtTypeAccessImpl]io.debezium.connector.sqlserver.SCHEMA_INCLUDE_LIST, [CtTypeAccessImpl]io.debezium.connector.sqlserver.SCHEMA_BLACKLIST, [CtTypeAccessImpl]io.debezium.connector.sqlserver.SCHEMA_EXCLUDE_LIST).create();

    [CtFieldImpl][CtJavaDocImpl]/**
     * The set of {@link Field}s defined as part of this configuration.
     */
    public static [CtTypeReferenceImpl]Field.Set ALL_FIELDS = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.config.Field.setOf([CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.CONFIG_DEFINITION.all());

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.apache.kafka.common.config.ConfigDef configDef() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.CONFIG_DEFINITION.configDef();
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String databaseName;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String instanceName;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode snapshotMode;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode snapshotIsolationMode;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SourceTimestampMode sourceTimestampMode;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter columnFilter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean readOnlyDatabaseConnection;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean skipLowActivityLSNsEnabled;

    [CtConstructorImpl]public SqlServerConnectorConfig([CtParameterImpl][CtTypeReferenceImpl]io.debezium.config.Configuration config) [CtBlockImpl]{
        [CtInvocationImpl]super([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnector.class, [CtVariableReadImpl]config, [CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SERVER_NAME), [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SystemTablesPredicate(), [CtLambdaImpl]([CtParameterImpl] x) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]x.schema() + [CtLiteralImpl]".") + [CtInvocationImpl][CtVariableReadImpl]x.table(), [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.databaseName = [CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.DATABASE_NAME);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.instanceName = [CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.INSTANCE);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.snapshotMode = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode.parse([CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_MODE), [CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_MODE.defaultValueAsString());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]columnIncludeList() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.columnFilter = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.getColumnIncludeNameFilter([CtInvocationImpl]columnIncludeList());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.columnFilter = [CtInvocationImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.getColumnExcludeNameFilter([CtInvocationImpl]columnExcludeList());
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.readOnlyDatabaseConnection = [CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.READ_ONLY_INTENT.equals([CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.APPLICATION_INTENT_KEY));
        [CtIfImpl]if ([CtFieldReadImpl]readOnlyDatabaseConnection) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.snapshotIsolationMode = [CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]SNAPSHOT;
            [CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.LOGGER.info([CtLiteralImpl]"JDBC connection has set applicationIntent = ReadOnly, switching snapshot isolation mode to {}", [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.[CtFieldReferenceImpl]SNAPSHOT.name());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.snapshotIsolationMode = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode.parse([CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_ISOLATION_MODE), [CtInvocationImpl][CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SNAPSHOT_ISOLATION_MODE.defaultValueAsString());
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceTimestampMode = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.SourceTimestampMode.fromMode([CtInvocationImpl][CtVariableReadImpl]config.getString([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SOURCE_TIMESTAMP_MODE_CONFIG_NAME));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.skipLowActivityLSNsEnabled = [CtInvocationImpl][CtVariableReadImpl]config.getBoolean([CtFieldReadImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.MAX_LSN_OPTIMIZATION);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter getColumnExcludeNameFilter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String excludedColumnPatterns) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter()[CtClassImpl] {
            [CtFieldImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]io.debezium.relational.ColumnId> delegate = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.function.Predicates.excludes([CtVariableReadImpl]excludedColumnPatterns, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ColumnId::toString);

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean matches([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalogName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnName) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// ignore database name as it's not relevant here
                return [CtInvocationImpl][CtFieldReadImpl]delegate.test([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.relational.ColumnId([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.relational.TableId([CtLiteralImpl]null, [CtVariableReadImpl]schemaName, [CtVariableReadImpl]tableName), [CtVariableReadImpl]columnName));
            }
        };
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter getColumnIncludeNameFilter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String excludedColumnPatterns) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter()[CtClassImpl] {
            [CtFieldImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]io.debezium.relational.ColumnId> delegate = [CtInvocationImpl][CtTypeAccessImpl]io.debezium.function.Predicates.includes([CtVariableReadImpl]excludedColumnPatterns, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ColumnId::toString);

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean matches([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalogName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnName) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// ignore database name as it's not relevant here
                return [CtInvocationImpl][CtFieldReadImpl]delegate.test([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.relational.ColumnId([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.relational.TableId([CtLiteralImpl]null, [CtVariableReadImpl]schemaName, [CtVariableReadImpl]tableName), [CtVariableReadImpl]columnName));
            }
        };
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDatabaseName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]databaseName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getInstanceName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]instanceName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotIsolationMode getSnapshotIsolationMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.snapshotIsolationMode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerConnectorConfig.SnapshotMode getSnapshotMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]snapshotMode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SourceTimestampMode getSourceTimestampMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sourceTimestampMode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.debezium.relational.Tables.ColumnNameFilter getColumnFilter() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]columnFilter;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isReadOnlyDatabaseConnection() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]readOnlyDatabaseConnection;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isSkipLowActivityLSNsEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]skipLowActivityLSNsEnabled;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]io.debezium.connector.SourceInfoStructMaker<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.debezium.connector.AbstractSourceInfo> getSourceInfoStructMaker([CtParameterImpl][CtTypeReferenceImpl]io.debezium.connector.sqlserver.Version version) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]version) {
            [CtCaseImpl]case [CtFieldReadImpl]V1 :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.connector.sqlserver.LegacyV1SqlServerSourceInfoStructMaker([CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.name(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.version(), [CtThisAccessImpl]this);
            [CtCaseImpl]default :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.debezium.connector.sqlserver.SqlServerSourceInfoStructMaker([CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.name(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.version(), [CtThisAccessImpl]this);
        }
    }

    [CtClassImpl]private static class SystemTablesPredicate implements [CtTypeReferenceImpl]io.debezium.relational.Tables.TableFilter {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isIncluded([CtParameterImpl][CtTypeReferenceImpl]io.debezium.relational.TableId t) [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]t.schema().toLowerCase().equals([CtLiteralImpl]"cdc") || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]t.schema().toLowerCase().equals([CtLiteralImpl]"sys")) || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]t.table().toLowerCase().equals([CtLiteralImpl]"systranschemas"));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]io.debezium.relational.history.HistoryRecordComparator getHistoryRecordComparator() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]io.debezium.relational.history.HistoryRecordComparator()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]boolean isPositionAtOrBefore([CtParameterImpl][CtTypeReferenceImpl]io.debezium.document.Document recorded, [CtParameterImpl][CtTypeReferenceImpl]io.debezium.document.Document desired) [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.Lsn.valueOf([CtInvocationImpl][CtVariableReadImpl]recorded.getString([CtTypeAccessImpl]SourceInfo.CHANGE_LSN_KEY)).compareTo([CtInvocationImpl][CtTypeAccessImpl]io.debezium.connector.sqlserver.Lsn.valueOf([CtInvocationImpl][CtVariableReadImpl]desired.getString([CtTypeAccessImpl]SourceInfo.CHANGE_LSN_KEY))) < [CtLiteralImpl]1;
            }
        };
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getContextName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.contextName();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getConnectorName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Module.name();
    }
}
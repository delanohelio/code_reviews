[CompilationUnitImpl][CtPackageDeclarationImpl]package org.sagebionetworks.table.cluster;
[CtUnresolvedImport]import org.springframework.jdbc.core.BatchPreparedStatementSetter;
[CtUnresolvedImport]import org.sagebionetworks.util.Callback;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_TYPE;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
[CtUnresolvedImport]import org.sagebionetworks.table.model.Grouping;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_VERSION;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.P_LIMIT;
[CtUnresolvedImport]import org.springframework.dao.DataIntegrityViolationException;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ID_PARAMETER_NAME;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_ETAG;
[CtImportImpl]import java.sql.ResultSet;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.EntityDTO;
[CtUnresolvedImport]import org.springframework.transaction.support.DefaultTransactionDefinition;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_FILE_ID;
[CtUnresolvedImport]import org.sagebionetworks.table.query.util.ColumnTypeListMappings;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.PARENT_ID_PARAMETER_NAME;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.Row;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_MODIFIED_BY;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.TableConstants;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.TRUNCATE_REPLICATION_SYNC_EXPIRATION_TABLE;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_NAME;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_CRATED_ON;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_MODIFIED_ON;
[CtImportImpl]import javax.sql.DataSource;
[CtUnresolvedImport]import com.google.common.collect.Sets;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_BENEFACTOR_ID;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_TABLE;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.SELECT_ENTITY_CHILD_ID_ETAG;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.ColumnModel;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_CRATED_BY;
[CtUnresolvedImport]import org.springframework.jdbc.core.RowCallbackHandler;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_FILE_SIZE_BYTES;
[CtUnresolvedImport]import org.sagebionetworks.util.csv.CSVWriterStream;
[CtUnresolvedImport]import org.springframework.jdbc.core.RowMapper;
[CtUnresolvedImport]import org.springframework.transaction.TransactionStatus;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.SELECT_NON_EXPIRED_IDS;
[CtUnresolvedImport]import org.springframework.transaction.support.TransactionCallback;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.entity.IdAndVersion;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ANNOTATION_REPLICATION_COL_TYPE;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.springframework.dao.DataAccessException;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.AnnotationDTO;
[CtImportImpl]import java.sql.SQLException;
[CtUnresolvedImport]import org.springframework.transaction.TransactionDefinition;
[CtUnresolvedImport]import org.springframework.jdbc.core.namedparam.SqlParameterSource;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.IdAndEtag;
[CtUnresolvedImport]import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
[CtUnresolvedImport]import org.springframework.jdbc.core.JdbcTemplate;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.AnnotationType;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.ColumnType;
[CtUnresolvedImport]import org.sagebionetworks.table.cluster.SQLUtils.TableType;
[CtUnresolvedImport]import org.springframework.jdbc.datasource.DataSourceTransactionManager;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.EXPIRES_PARAM;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.BATCH_INSERT_REPLICATION_SYNC_EXP;
[CtUnresolvedImport]import org.sagebionetworks.util.ValidateArgument;
[CtUnresolvedImport]import org.sagebionetworks.table.cluster.utils.TableModelUtils;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.P_OFFSET;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.table.RowSet;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ANNOTATION_REPLICATION_COL_ENTITY_ID;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ANNOTATION_REPLICATION_COL_KEY;
[CtImportImpl]import java.sql.Connection;
[CtImportImpl]import java.sql.PreparedStatement;
[CtImportImpl]import org.json.JSONArray;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.EntityType;
[CtUnresolvedImport]import org.springframework.jdbc.BadSqlGrammarException;
[CtUnresolvedImport]import com.google.common.collect.Maps;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.report.SynapseStorageProjectStats;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_PARENT_ID;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_IN_SYNAPSE_STORAGE;
[CtUnresolvedImport]import org.sagebionetworks.repo.model.dao.table.RowHandler;
[CtUnresolvedImport]import org.springframework.jdbc.core.SingleColumnRowMapper;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ANNOTATION_REPLICATION_COL_STRING_LIST_VALUE;
[CtUnresolvedImport]import org.springframework.transaction.support.TransactionTemplate;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_PROJECT_ID;
[CtUnresolvedImport]import org.sagebionetworks.common.util.progress.ProgressCallback;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.CRC_ALIAS;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.ENTITY_REPLICATION_COL_ID;
[CtUnresolvedImport]import static org.sagebionetworks.repo.model.table.TableConstants.SELECT_ENTITY_CHILD_CRC;
[CtClassImpl]public class TableIndexDAOImpl implements [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.TableIndexDAO {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SQL_SUM_FILE_SIZES = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT SUM(" + [CtFieldReadImpl]ENTITY_REPLICATION_COL_FILE_SIZE_BYTES) + [CtLiteralImpl]") FROM ") + [CtFieldReadImpl]ENTITY_REPLICATION_TABLE) + [CtLiteralImpl]" WHERE ") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_ID) + [CtLiteralImpl]" IN (:rowIds)";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SQL_SELECT_PROJECTS_BY_SIZE = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT t1." + [CtFieldReadImpl]ENTITY_REPLICATION_COL_PROJECT_ID) + [CtLiteralImpl]", t2.") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_NAME) + [CtLiteralImpl]", t1.PROJECT_SIZE_BYTES ") + [CtLiteralImpl]" FROM (SELECT ") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_PROJECT_ID) + [CtLiteralImpl]", ") + [CtLiteralImpl]" SUM(") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_FILE_SIZE_BYTES) + [CtLiteralImpl]") AS PROJECT_SIZE_BYTES") + [CtLiteralImpl]" FROM ") + [CtFieldReadImpl]ENTITY_REPLICATION_TABLE) + [CtLiteralImpl]" WHERE ") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_IN_SYNAPSE_STORAGE) + [CtLiteralImpl]" = 1") + [CtLiteralImpl]" GROUP BY ") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_PROJECT_ID) + [CtLiteralImpl]") t1,") + [CtFieldReadImpl]ENTITY_REPLICATION_TABLE) + [CtLiteralImpl]" t2") + [CtLiteralImpl]" WHERE t1.") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_PROJECT_ID) + [CtLiteralImpl]" = t2.") + [CtFieldReadImpl]ENTITY_REPLICATION_COL_ID) + [CtLiteralImpl]" ORDER BY t1.PROJECT_SIZE_BYTES DESC";

    [CtFieldImpl][CtJavaDocImpl]/**
     * The MD5 used for tables with no schema.
     */
    public static final [CtTypeReferenceImpl]java.lang.String EMPTY_SCHEMA_MD5 = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.utils.TableModelUtils.createSchemaMD5Hex([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]java.lang.String>());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KEY_NAME = [CtLiteralImpl]"Key_name";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COLUMN_NAME = [CtLiteralImpl]"Column_name";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SHOW_INDEXES_FROM = [CtLiteralImpl]"SHOW INDEXES FROM ";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KEY = [CtLiteralImpl]"Key";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SQL_SHOW_COLUMNS = [CtLiteralImpl]"SHOW FULL COLUMNS FROM ";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FIELD = [CtLiteralImpl]"Field";

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManager;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionTemplate writeTransactionTemplate;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionTemplate readTransactionTemplate;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.jdbc.core.JdbcTemplate template;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setDataSource([CtParameterImpl][CtTypeReferenceImpl]javax.sql.DataSource dataSource) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]template != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"DataSource can only be set once");
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transactionManager = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.datasource.DataSourceTransactionManager([CtVariableReadImpl]dataSource);
        [CtAssignmentImpl][CtCommentImpl]// This will manage transactions for calls that need it.
        [CtFieldWriteImpl][CtThisAccessImpl]this.writeTransactionTemplate = [CtInvocationImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.createTransactionTemplate([CtFieldReadImpl][CtThisAccessImpl]this.transactionManager, [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.readTransactionTemplate = [CtInvocationImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.createTransactionTemplate([CtFieldReadImpl][CtThisAccessImpl]this.transactionManager, [CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]/* By default the MySQL driver will read all query results into memory which can
        cause memory problems for large query results. (see: <a hreft=
        "http://dev.mysql.com/doc/connector-j/en/connector-j-reference-implementation-notes.html"
        />) According to the MySQL driver docs the only way to get the driver to
        change this default behavior is to create a statement with TYPE_FORWARD_ONLY
        & CONCUR_READ_ONLY and then set statement fetch size to Integer.MIN_VALUE.
        With Spring 4.3 {@link JdbcTemplate#setFetchSize()} allows the fetch size to
        be set to Integer.MIN_VALUE. See: PLFM-3429
         */
        [CtFieldWriteImpl][CtThisAccessImpl]this.template = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.JdbcTemplate([CtVariableReadImpl]dataSource);
        [CtInvocationImpl][CtCommentImpl]// See comments above.
        [CtFieldReadImpl][CtThisAccessImpl]this.template.setFetchSize([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionTemplate createTransactionTemplate([CtParameterImpl][CtTypeReferenceImpl]org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManager, [CtParameterImpl][CtTypeReferenceImpl]boolean readOnly) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// This will define how transaction are run for this instance.
        [CtTypeReferenceImpl]org.springframework.transaction.support.DefaultTransactionDefinition transactionDef;
        [CtAssignmentImpl][CtVariableWriteImpl]transactionDef = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.DefaultTransactionDefinition();
        [CtInvocationImpl][CtVariableReadImpl]transactionDef.setIsolationLevel([CtFieldReadImpl][CtTypeAccessImpl]java.sql.Connection.[CtFieldReferenceImpl]TRANSACTION_READ_COMMITTED);
        [CtInvocationImpl][CtVariableReadImpl]transactionDef.setReadOnly([CtVariableReadImpl]readOnly);
        [CtInvocationImpl][CtVariableReadImpl]transactionDef.setPropagationBehavior([CtTypeAccessImpl]TransactionDefinition.PROPAGATION_REQUIRED);
        [CtInvocationImpl][CtVariableReadImpl]transactionDef.setName([CtLiteralImpl]"TableIndexDAOImpl");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionTemplate([CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transactionDef);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean deleteTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dropTableDML = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.dropTableSQL([CtVariableReadImpl]tableId, [CtTypeAccessImpl]SQLUtils.TableType.INDEX);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]dropTableDML);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.BadSqlGrammarException e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// This is thrown when the table does not exist
            return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createOrUpdateOrDeleteRows([CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.table.model.Grouping grouping) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]grouping, [CtLiteralImpl]"grouping");
        [CtInvocationImpl][CtCommentImpl]// Execute this within a transaction
        [CtFieldReadImpl][CtThisAccessImpl]this.writeTransactionTemplate.execute([CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionCallback<[CtTypeReferenceImpl]java.lang.Void>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void doInTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus status) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// We need a named template for this case.
                [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl]template);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> groupingColumns = [CtInvocationImpl][CtVariableReadImpl]grouping.getColumnsWithValues();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]groupingColumns.isEmpty()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// This is a delete
                    [CtTypeReferenceImpl]java.lang.String deleteSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildDeleteSQL([CtVariableReadImpl]tableId);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.SqlParameterSource batchDeleteBinding = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.bindParameterForDelete([CtInvocationImpl][CtVariableReadImpl]grouping.getRows());
                    [CtInvocationImpl][CtVariableReadImpl]namedTemplate.update([CtVariableReadImpl]deleteSql, [CtVariableReadImpl]batchDeleteBinding);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// this is a create or update
                    [CtTypeReferenceImpl]java.lang.String createOrUpdateSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildCreateOrUpdateRowSQL([CtVariableReadImpl]groupingColumns, [CtVariableReadImpl]tableId);
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.springframework.jdbc.core.namedparam.SqlParameterSource[] batchUpdateOrCreateBinding = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.bindParametersForCreateOrUpdate([CtVariableReadImpl]grouping);
                    [CtInvocationImpl][CtVariableReadImpl]namedTemplate.batchUpdate([CtVariableReadImpl]createOrUpdateSql, [CtVariableReadImpl]batchUpdateOrCreateBinding);
                }
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getRowCountForTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getCountSQL([CtVariableReadImpl]tableId);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]template.queryForObject([CtVariableReadImpl]sql, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.SingleColumnRowMapper<[CtTypeReferenceImpl]java.lang.Long>());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.BadSqlGrammarException e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Spring throws this when the table does not
            return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long getMaxCurrentCompleteVersionForTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getStatusMaxVersionSQL([CtVariableReadImpl]tableId);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]template.queryForObject([CtVariableReadImpl]sql, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.SingleColumnRowMapper<[CtTypeReferenceImpl]java.lang.Long>());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Spring throws this when the table is empty
            return [CtUnaryOperatorImpl]-[CtLiteralImpl]1L;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setMaxCurrentCompleteVersionForTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long version) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String createOrUpdateStatusSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildCreateOrUpdateStatusSQL([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]createOrUpdateStatusSql, [CtVariableReadImpl]version, [CtVariableReadImpl]version);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setCurrentSchemaMD5Hex([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaMD5Hex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String createOrUpdateStatusSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildCreateOrUpdateStatusHashSQL([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]createOrUpdateStatusSql, [CtVariableReadImpl]schemaMD5Hex, [CtVariableReadImpl]schemaMD5Hex);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setIndexVersionAndSchemaMD5Hex([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewCRC, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaMD5Hex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String createOrUpdateStatusSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildCreateOrUpdateStatusVersionAndHashSQL([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]createOrUpdateStatusSql, [CtVariableReadImpl]viewCRC, [CtVariableReadImpl]schemaMD5Hex, [CtVariableReadImpl]viewCRC, [CtVariableReadImpl]schemaMD5Hex);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getCurrentSchemaMD5Hex([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getSchemaHashSQL([CtVariableReadImpl]tableId);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]template.queryForObject([CtVariableReadImpl]sql, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.SingleColumnRowMapper<[CtTypeReferenceImpl]java.lang.String>());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Spring throws this when the table is empty
            return [CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.EMPTY_SCHEMA_MD5;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteSecondaryTables([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.SQLUtils.TableType type : [CtFieldReadImpl]SQLUtils.SECONDARY_TYPES) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dropStatusTableDML = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.dropTableSQL([CtVariableReadImpl]tableId, [CtVariableReadImpl]type);
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]dropStatusTableDML);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.BadSqlGrammarException e) [CtBlockImpl]{
                [CtCommentImpl]// This is thrown when the table does not exist
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createSecondaryTables([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.SQLUtils.TableType type : [CtFieldReadImpl]SQLUtils.SECONDARY_TYPES) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createTableSQL([CtVariableReadImpl]tableId, [CtVariableReadImpl]type);
            [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.springframework.jdbc.core.JdbcTemplate getConnection() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]template;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.RowSet query([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.common.util.progress.ProgressCallback callback, [CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.SqlQuery query) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]query == [CtLiteralImpl]null)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"SqlQuery cannot be null");

        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.Row> rows = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.Row>();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.RowSet rowSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.RowSet();
        [CtInvocationImpl][CtVariableReadImpl]rowSet.setRows([CtVariableReadImpl]rows);
        [CtInvocationImpl][CtVariableReadImpl]rowSet.setHeaders([CtInvocationImpl][CtVariableReadImpl]query.getSelectColumns());
        [CtInvocationImpl][CtCommentImpl]// Stream over the results and save the results in a a list
        queryAsStream([CtVariableReadImpl]callback, [CtVariableReadImpl]query, [CtNewClassImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.dao.table.RowHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void nextRow([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.Row row) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rows.add([CtVariableReadImpl]row);
            }
        });
        [CtInvocationImpl][CtVariableReadImpl]rowSet.setTableId([CtInvocationImpl][CtVariableReadImpl]query.getTableId());
        [CtReturnImpl]return [CtVariableReadImpl]rowSet;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Long countQuery([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameters) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]sql, [CtLiteralImpl]"sql");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]parameters, [CtLiteralImpl]"parameters");
        [CtLocalVariableImpl][CtCommentImpl]// We use spring to create create the prepared statement
        [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]namedTemplate.queryForObject([CtVariableReadImpl]sql, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource([CtVariableReadImpl]parameters), [CtFieldReadImpl]java.lang.Long.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean queryAsStream([CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.common.util.progress.ProgressCallback callback, [CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.SqlQuery query, [CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.repo.model.dao.table.RowHandler handler) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]query, [CtLiteralImpl]"Query");
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnTypeInfo[] infoArray = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLTranslatorUtils.getColumnTypeInfoArray([CtInvocationImpl][CtVariableReadImpl]query.getSelectColumns());
        [CtLocalVariableImpl][CtCommentImpl]// We use spring to create create the prepared statement
        [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtInvocationImpl][CtVariableReadImpl]query.getOutputSQL(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource([CtInvocationImpl][CtVariableReadImpl]query.getParameters()), [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowCallbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void processRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.Row row = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLTranslatorUtils.readRow([CtVariableReadImpl]rs, [CtInvocationImpl][CtVariableReadImpl]query.includesRowIdAndVersion(), [CtInvocationImpl][CtVariableReadImpl]query.includeEntityEtag(), [CtVariableReadImpl]infoArray);
                [CtInvocationImpl][CtVariableReadImpl]handler.nextRow([CtVariableReadImpl]row);
            }
        });
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sagebionetworks.table.cluster.TableIndexDAO#executeInReadTransaction(org.springframework.transaction.support.TransactionCallback)
     */
    [CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T executeInReadTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.support.TransactionCallback<[CtTypeParameterReferenceImpl]T> callable) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]readTransactionTemplate.execute([CtVariableReadImpl]callable);
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sagebionetworks.table.cluster.TableIndexDAO#executeInWriteTransaction(org.springframework.transaction.support.TransactionCallback)
     */
    [CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T executeInWriteTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.support.TransactionCallback<[CtTypeParameterReferenceImpl]T> callable) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]writeTransactionTemplate.execute([CtVariableReadImpl]callable);
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sagebionetworks.table.cluster.TableIndexDAO#applyFileHandleIdsToTable(java.lang.String, java.util.Set)
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void applyFileHandleIdsToTable([CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> fileHandleIds) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.writeTransactionTemplate.execute([CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionCallback<[CtTypeReferenceImpl]java.lang.Void>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void doInTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus status) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createSQLInsertIgnoreFileHandleId([CtVariableReadImpl]tableId);
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.Long[] args = [CtInvocationImpl][CtVariableReadImpl]fileHandleIds.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Long[[CtInvocationImpl][CtVariableReadImpl]fileHandleIds.size()]);
                [CtInvocationImpl][CtFieldReadImpl]template.batchUpdate([CtVariableReadImpl]sql, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.BatchPreparedStatementSetter()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void setValues([CtParameterImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps, [CtParameterImpl][CtTypeReferenceImpl]int parameterIndex) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtLiteralImpl]1, [CtArrayReadImpl][CtVariableReadImpl]args[[CtVariableReadImpl]parameterIndex]);
                    }

                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]int getBatchSize() [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fileHandleIds.size();
                    }
                });
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        });
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sagebionetworks.table.cluster.TableIndexDAO#getFileHandleIdsAssociatedWithTable(java.util.Set, java.lang.String)
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> getFileHandleIdsAssociatedWithTable([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> fileHandleIds, [CtParameterImpl]final [CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.readTransactionTemplate.execute([CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.TransactionCallback<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long>>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> doInTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus status) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createSQLGetBoundFileHandleId([CtVariableReadImpl]tableId);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl]template);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long>> params = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Maps.newHashMap();
                    [CtInvocationImpl][CtVariableReadImpl]params.put([CtTypeAccessImpl]SQLUtils.FILE_ID_BIND, [CtVariableReadImpl]fileHandleIds);
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> intersection = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.newHashSet();
                    [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtVariableReadImpl]sql, [CtVariableReadImpl]params, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowCallbackHandler()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void processRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]intersection.add([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]TableConstants.FILE_ID));
                        }
                    });
                    [CtReturnImpl]return [CtVariableReadImpl]intersection;
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.BadSqlGrammarException e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// thrown when the table does not exist
            return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.Long>([CtLiteralImpl]0);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doesIndexStateMatch([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]long versionNumber, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemaMD5Hex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long indexVersion = [CtInvocationImpl]getMaxCurrentCompleteVersionForTable([CtVariableReadImpl]tableId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]indexVersion != [CtVariableReadImpl]versionNumber) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexMD5Hex = [CtInvocationImpl]getCurrentSchemaMD5Hex([CtVariableReadImpl]tableId);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]indexMD5Hex.equals([CtVariableReadImpl]schemaMD5Hex);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> getDistinctLongValues([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String columnName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createSQLGetDistinctValues([CtVariableReadImpl]tableId, [CtVariableReadImpl]columnName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> results = [CtInvocationImpl][CtFieldReadImpl]template.queryForList([CtVariableReadImpl]sql, [CtFieldReadImpl]java.lang.Long.class);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.Long>([CtVariableReadImpl]results);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createTableIfDoesNotExist([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]boolean isView) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createTableIfDoesNotExistSQL([CtVariableReadImpl]tableId, [CtVariableReadImpl]isView);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean alterTableAsNeeded([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnChangeDetails> changes, [CtParameterImpl][CtTypeReferenceImpl]boolean alterTemp) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createAlterTableSql([CtVariableReadImpl]changes, [CtVariableReadImpl]tableId, [CtVariableReadImpl]alterTemp);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sql == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// no change are needed.
            return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtCommentImpl]// apply the update
        [CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
        [CtForEachImpl][CtCommentImpl]// for any columns that have list columns delete their table indexes
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sqlStatement : [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.listColumnIndexTableCreateOrDropStatements([CtVariableReadImpl]changes, [CtVariableReadImpl]tableId)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sqlStatement);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void truncateTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createTruncateSql([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo> getDatabaseInfo([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getTableNameForId([CtVariableReadImpl]tableId, [CtTypeAccessImpl]SQLUtils.TableType.INDEX);
            [CtReturnImpl][CtCommentImpl]// Bind variables do not seem to work here
            return [CtInvocationImpl][CtFieldReadImpl]template.query([CtBinaryOperatorImpl][CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.SQL_SHOW_COLUMNS + [CtVariableReadImpl]tableName, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowMapper<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo mapRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs, [CtParameterImpl][CtTypeReferenceImpl]int rowNum) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo info = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo();
                    [CtInvocationImpl][CtVariableReadImpl]info.setColumnName([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.FIELD));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.KEY);
                    [CtInvocationImpl][CtVariableReadImpl]info.setHasIndex([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]key));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String typeString = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"Type");
                    [CtInvocationImpl][CtVariableReadImpl]info.setType([CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.MySqlColumnType.parserType([CtVariableReadImpl]typeString));
                    [CtInvocationImpl][CtVariableReadImpl]info.setMaxSize([CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.MySqlColumnType.parseSize([CtVariableReadImpl]typeString));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String comment = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]"Comment");
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]comment != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]comment))) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]info.setColumnType([CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.repo.model.table.ColumnType.valueOf([CtVariableReadImpl]comment));
                    }
                    [CtReturnImpl]return [CtVariableReadImpl]info;
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.BadSqlGrammarException e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Spring throws this when the table does not
            return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo>();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void provideCardinality([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo> list, [CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]list, [CtLiteralImpl]"list");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]tableId, [CtLiteralImpl]"tableId");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]list.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createCardinalitySql([CtVariableReadImpl]list, [CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.query([CtVariableReadImpl]sql, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowCallbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void processRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo info : [CtVariableReadImpl]list) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]info.setCardinality([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtInvocationImpl][CtVariableReadImpl]info.getColumnName()));
                }
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void provideIndexName([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo> list, [CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]list, [CtLiteralImpl]"list");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]tableId, [CtLiteralImpl]"tableId");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]list.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo> nameToInfoMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo>([CtInvocationImpl][CtVariableReadImpl]list.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo info : [CtVariableReadImpl]list) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]nameToInfoMap.put([CtInvocationImpl][CtVariableReadImpl]info.getColumnName(), [CtVariableReadImpl]info);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getTableNameForId([CtVariableReadImpl]tableId, [CtTypeAccessImpl]SQLUtils.TableType.INDEX);
        [CtInvocationImpl][CtFieldReadImpl]template.query([CtBinaryOperatorImpl][CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.SHOW_INDEXES_FROM + [CtVariableReadImpl]tableName, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowCallbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void processRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String columnName = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.COLUMN_NAME);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexName = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.KEY_NAME);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo info = [CtInvocationImpl][CtVariableReadImpl]nameToInfoMap.get([CtVariableReadImpl]columnName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]info == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Provided List<DatabaseColumnInfo> has no match for column: " + [CtVariableReadImpl]columnName);
                }
                [CtInvocationImpl][CtVariableReadImpl]info.setIndexName([CtVariableReadImpl]indexName);
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void optimizeTableIndices([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.DatabaseColumnInfo> list, [CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId, [CtParameterImpl][CtTypeReferenceImpl]int maxNumberOfIndex) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String alterSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createOptimizedAlterIndices([CtVariableReadImpl]list, [CtVariableReadImpl]tableId, [CtVariableReadImpl]maxNumberOfIndex);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]alterSql == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// No changes are needed.
            return;
        }
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]alterSql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void populateListColumnIndexTables([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableIdAndVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> columnModels) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel columnModel : [CtVariableReadImpl]columnModels) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// only operate on list column types
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.query.util.ColumnTypeListMappings.isList([CtInvocationImpl][CtVariableReadImpl]columnModel.getColumnType())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtCommentImpl]// index tables for list columns have already been created when column changes were applied
            [CtTypeReferenceImpl]java.lang.String truncateTablesql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.truncateListColumnIndexTable([CtVariableReadImpl]tableIdAndVersion, [CtVariableReadImpl]columnModel);
            [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]truncateTablesql);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String insertIntoSql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.insertIntoListColumnIndexTable([CtVariableReadImpl]tableIdAndVersion, [CtVariableReadImpl]columnModel);
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]insertIntoSql);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.dao.DataIntegrityViolationException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]columnModel.getColumnType() == [CtFieldReadImpl]org.sagebionetworks.repo.model.table.ColumnType.STRING_LIST) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"The size of the column '" + [CtInvocationImpl][CtVariableReadImpl]columnModel.getName()) + [CtLiteralImpl]"' is too small.") + [CtLiteralImpl]" Unable to automatically determine the necessary size to fit all values in a STRING_LIST column", [CtVariableReadImpl]e);
                }
                [CtThrowImpl]throw [CtVariableReadImpl]e;
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createTemporaryTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createTempTableSql([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void copyAllDataToTemporaryTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.copyTableToTempSql([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteTemporaryTable([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.deleteTempTableSql([CtVariableReadImpl]tableId);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtVariableReadImpl]sql);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getTempTableCount([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion tableId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.countTempRowsSql([CtVariableReadImpl]tableId);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]template.queryForObject([CtVariableReadImpl]sql, [CtFieldReadImpl]java.lang.Long.class);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.dao.DataAccessException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0L;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createEntityReplicationTablesIfDoesNotExist() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtTypeAccessImpl]TableConstants.ENTITY_REPLICATION_TABLE_CREATE);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtTypeAccessImpl]TableConstants.ANNOTATION_REPLICATION_TABLE_CREATE);
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtTypeAccessImpl]TableConstants.REPLICATION_SYNCH_EXPIRATION_TABLE_CREATE);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteEntityData([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> entityIds) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> sorted = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Long>([CtVariableReadImpl]entityIds);
        [CtInvocationImpl][CtCommentImpl]// sort to prevent deadlock.
        [CtTypeAccessImpl]java.util.Collections.sort([CtVariableReadImpl]sorted);
        [CtInvocationImpl][CtCommentImpl]// Batch delete.
        [CtFieldReadImpl]template.batchUpdate([CtTypeAccessImpl]TableConstants.ENTITY_REPLICATION_DELETE_ALL, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.BatchPreparedStatementSetter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void setValues([CtParameterImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps, [CtParameterImpl][CtTypeReferenceImpl]int i) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]sorted.get([CtVariableReadImpl]i));
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]int getBatchSize() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sorted.size();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addEntityData([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO> entityDTOs) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO> sorted = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO>([CtVariableReadImpl]entityDTOs);
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtVariableReadImpl]sorted);
        [CtInvocationImpl][CtCommentImpl]// batch update the entity table
        [CtFieldReadImpl]template.batchUpdate([CtTypeAccessImpl]TableConstants.ENTITY_REPLICATION_INSERT, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.BatchPreparedStatementSetter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void setValues([CtParameterImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps, [CtParameterImpl][CtTypeReferenceImpl]int i) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO dto = [CtInvocationImpl][CtVariableReadImpl]sorted.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int parameterIndex = [CtLiteralImpl]1;
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getId());
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getCurrentVersion());
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getCreatedBy());
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dto.getCreatedOn().getTime());
                [CtInvocationImpl][CtVariableReadImpl]ps.setString([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getEtag());
                [CtInvocationImpl][CtVariableReadImpl]ps.setString([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getName());
                [CtInvocationImpl][CtVariableReadImpl]ps.setString([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dto.getType().name());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getParentId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getParentId());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getBenefactorId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getBenefactorId());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getProjectId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getProjectId());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
                }
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getModifiedBy());
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dto.getModifiedOn().getTime());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getFileHandleId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getFileHandleId());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getFileSizeBytes() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getFileSizeBytes());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dto.getIsInSynapseStorage() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setBoolean([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtInvocationImpl][CtVariableReadImpl]dto.getIsInSynapseStorage());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]ps.setNull([CtUnaryOperatorImpl][CtVariableWriteImpl]parameterIndex++, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BOOLEAN);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]int getBatchSize() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sorted.size();
            }
        });
        [CtLocalVariableImpl][CtCommentImpl]// map the entities with annotations
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO> annotations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]sorted.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO dto = [CtInvocationImpl][CtVariableReadImpl]sorted.get([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dto.getAnnotations() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dto.getAnnotations().isEmpty())) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// this index has annotations.
                [CtVariableReadImpl]annotations.addAll([CtInvocationImpl][CtVariableReadImpl]dto.getAnnotations());
            }
        }
        [CtInvocationImpl][CtCommentImpl]// update the annotations
        [CtFieldReadImpl]template.batchUpdate([CtTypeAccessImpl]TableConstants.ANNOTATION_REPLICATION_INSERT, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.BatchPreparedStatementSetter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void setValues([CtParameterImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps, [CtParameterImpl][CtTypeReferenceImpl]int i) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO dto = [CtInvocationImpl][CtVariableReadImpl]annotations.get([CtVariableReadImpl]i);
                [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.writeAnnotationDtoToPreparedStatement([CtVariableReadImpl]ps, [CtVariableReadImpl]dto);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]int getBatchSize() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]annotations.size();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO getEntityData([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long entityId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// query for the template.
        [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO dto;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dto = [CtInvocationImpl][CtFieldReadImpl]template.queryForObject([CtTypeAccessImpl]TableConstants.ENTITY_REPLICATION_GET, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowMapper<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO mapRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs, [CtParameterImpl][CtTypeReferenceImpl]int rowNum) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO dto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.EntityDTO();
                    [CtInvocationImpl][CtVariableReadImpl]dto.setId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_ID));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setCurrentVersion([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_VERSION));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setCreatedBy([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_CRATED_BY));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setCreatedOn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_CRATED_ON)));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setEtag([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_ETAG));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setName([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_NAME));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setType([CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.repo.model.EntityType.valueOf([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_TYPE)));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setParentId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_PARENT_ID));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setParentId([CtLiteralImpl]null);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dto.setBenefactorId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_BENEFACTOR_ID));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setBenefactorId([CtLiteralImpl]null);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dto.setProjectId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_PROJECT_ID));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setProjectId([CtLiteralImpl]null);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dto.setModifiedBy([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_MODIFIED_BY));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setModifiedOn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_MODIFIED_ON)));
                    [CtInvocationImpl][CtVariableReadImpl]dto.setFileHandleId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_FILE_ID));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setFileHandleId([CtLiteralImpl]null);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dto.setFileSizeBytes([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_FILE_SIZE_BYTES));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setFileSizeBytes([CtLiteralImpl]null);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dto.setIsInSynapseStorage([CtInvocationImpl][CtVariableReadImpl]rs.getBoolean([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_IN_SYNAPSE_STORAGE));
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]dto.setIsInSynapseStorage([CtLiteralImpl]null);
                    }
                    [CtReturnImpl]return [CtVariableReadImpl]dto;
                }
            }, [CtVariableReadImpl]entityId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.dao.DataAccessException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// get the annotations.
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO> annotations = [CtInvocationImpl][CtFieldReadImpl]template.query([CtTypeAccessImpl]TableConstants.ANNOTATION_REPLICATION_GET, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowMapper<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO mapRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs, [CtParameterImpl][CtTypeReferenceImpl]int rowNum) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO dto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.AnnotationDTO();
                [CtInvocationImpl][CtVariableReadImpl]dto.setEntityId([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ANNOTATION_REPLICATION_COL_ENTITY_ID));
                [CtInvocationImpl][CtVariableReadImpl]dto.setKey([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ANNOTATION_REPLICATION_COL_KEY));
                [CtInvocationImpl][CtVariableReadImpl]dto.setType([CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.repo.model.table.AnnotationType.valueOf([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ANNOTATION_REPLICATION_COL_TYPE)));
                [CtInvocationImpl][CtVariableReadImpl]dto.setValue([CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>) (([CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ANNOTATION_REPLICATION_COL_STRING_LIST_VALUE)).toList()))));
                [CtReturnImpl]return [CtVariableReadImpl]dto;
            }
        }, [CtVariableReadImpl]entityId);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]annotations.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]dto.setAnnotations([CtVariableReadImpl]annotations);
        }
        [CtReturnImpl]return [CtVariableReadImpl]dto;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long calculateCRC32ofEntityReplicationScope([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewTypeMask, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> allContainersInScope) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]viewTypeMask, [CtLiteralImpl]"viewTypeMask");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]allContainersInScope, [CtLiteralImpl]"allContainersInScope");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]allContainersInScope.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1L;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.PARENT_ID_PARAMETER_NAME, [CtVariableReadImpl]allContainersInScope);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getCalculateCRC32Sql([CtVariableReadImpl]viewTypeMask);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long crc32 = [CtInvocationImpl][CtVariableReadImpl]namedTemplate.queryForObject([CtVariableReadImpl]sql, [CtVariableReadImpl]param, [CtFieldReadImpl]java.lang.Long.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]crc32 == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1L;
        }
        [CtReturnImpl]return [CtVariableReadImpl]crc32;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long calculateCRC32ofTableView([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.buildTableViewCRC32Sql([CtVariableReadImpl]viewId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long result = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.template.queryForObject([CtVariableReadImpl]sql, [CtFieldReadImpl]java.lang.Long.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1L;
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void copyEntityReplicationToTable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewTypeMask, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> allContainersInScope, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> currentSchema) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]viewTypeMask, [CtLiteralImpl]"viewTypeMask");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]allContainersInScope, [CtLiteralImpl]"allContainersInScope");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]allContainersInScope.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// nothing to do if the scope is empty.
            return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.PARENT_ID_PARAMETER_NAME, [CtVariableReadImpl]allContainersInScope);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createSelectInsertFromEntityReplication([CtVariableReadImpl]viewId, [CtVariableReadImpl]viewTypeMask, [CtVariableReadImpl]currentSchema);
        [CtInvocationImpl][CtVariableReadImpl]namedTemplate.update([CtVariableReadImpl]sql, [CtVariableReadImpl]param);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createViewSnapshotFromEntityReplication([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewTypeMask, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> allContainersInScope, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> currentSchema, [CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.util.csv.CSVWriterStream outputStream) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]viewTypeMask, [CtLiteralImpl]"viewTypeMask");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]allContainersInScope, [CtLiteralImpl]"allContainersInScope");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]allContainersInScope.isEmpty()) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// nothing to do if the scope is empty.
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Scope has not been defined for this view.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.PARENT_ID_PARAMETER_NAME, [CtVariableReadImpl]allContainersInScope);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> headers = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createSelectFromEntityReplication([CtVariableReadImpl]builder, [CtVariableReadImpl]viewId, [CtVariableReadImpl]viewTypeMask, [CtVariableReadImpl]currentSchema);
        [CtInvocationImpl][CtCommentImpl]// push the headers to the stream
        [CtVariableReadImpl]outputStream.writeNext([CtInvocationImpl][CtVariableReadImpl]headers.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]headers.size()]));
        [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtInvocationImpl][CtVariableReadImpl]builder.toString(), [CtVariableReadImpl]param, [CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Push each row to the callback
            [CtArrayTypeReferenceImpl]java.lang.String[] row = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]headers.size()];
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]headers.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]row[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1);
            }
            [CtInvocationImpl][CtVariableReadImpl]outputStream.writeNext([CtVariableReadImpl]row);
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> getPossibleColumnModelsForContainers([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Long> containerIds, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long viewTypeMask, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long limit, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long offset) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]containerIds, [CtLiteralImpl]"containerIds");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]limit, [CtLiteralImpl]"limit");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]offset, [CtLiteralImpl]"offset");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]containerIds.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.PARENT_ID_PARAMETER_NAME, [CtVariableReadImpl]containerIds);
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.P_LIMIT, [CtVariableReadImpl]limit);
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.P_OFFSET, [CtVariableReadImpl]offset);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.getDistinctAnnotationColumnsSql([CtVariableReadImpl]viewTypeMask);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation> results = [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtVariableReadImpl]sql, [CtVariableReadImpl]param, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowMapper<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation mapRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs, [CtParameterImpl][CtTypeReferenceImpl]int rowNum) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation aggregation = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation();
                [CtInvocationImpl][CtVariableReadImpl]aggregation.setColumnName([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ANNOTATION_REPLICATION_COL_KEY));
                [CtInvocationImpl][CtVariableReadImpl]aggregation.setColumnTypeConcat([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2));
                [CtInvocationImpl][CtVariableReadImpl]aggregation.setMaxStringElementSize([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtLiteralImpl]3));
                [CtInvocationImpl][CtVariableReadImpl]aggregation.setListSize([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtLiteralImpl]4));
                [CtReturnImpl]return [CtVariableReadImpl]aggregation;
            }
        });
        [CtReturnImpl][CtCommentImpl]// convert from the aggregation to column models.
        return [CtInvocationImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.expandFromAggregation([CtVariableReadImpl]results);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Expand the given column aggregations into column model objects.
     * This was added for PLFM-5034
     *
     * @param aggregations
     * @return  */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> expandFromAggregation([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation> aggregations) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel> results = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.table.cluster.ColumnAggregation aggregation : [CtVariableReadImpl]aggregations) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] typeSplit = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregation.getColumnTypeConcat().split([CtLiteralImpl]",");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String typeString : [CtVariableReadImpl]typeSplit) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel model = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnModel();
                [CtInvocationImpl][CtVariableReadImpl]model.setName([CtInvocationImpl][CtVariableReadImpl]aggregation.getColumnName());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.table.ColumnType type = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.repo.model.table.AnnotationType.valueOf([CtVariableReadImpl]typeString).getColumnType();
                [CtIfImpl][CtCommentImpl]// check if a LIST columnType needs to be used
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]aggregation.getListSize() > [CtLiteralImpl]1) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]type = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.query.util.ColumnTypeListMappings.forNonListType([CtVariableReadImpl]type).getListType();
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                        [CtCommentImpl]// do nothing because a list type mapping does not exist
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]model.setColumnType([CtVariableReadImpl]type);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]org.sagebionetworks.repo.model.table.ColumnType.STRING == [CtVariableReadImpl]type) || [CtBinaryOperatorImpl]([CtFieldReadImpl]org.sagebionetworks.repo.model.table.ColumnType.STRING_LIST == [CtVariableReadImpl]type)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]model.setMaximumSize([CtInvocationImpl][CtVariableReadImpl]aggregation.getMaxStringElementSize());
                }
                [CtInvocationImpl][CtVariableReadImpl]results.add([CtVariableReadImpl]model);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]results;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.Long> getSumOfChildCRCsForEachParent([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> parentIds) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]parentIds, [CtLiteralImpl]"parentIds");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.Long> results = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]parentIds.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]results;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.PARENT_ID_PARAMETER_NAME, [CtVariableReadImpl]parentIds);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtTypeAccessImpl]org.sagebionetworks.table.cluster.SELECT_ENTITY_CHILD_CRC, [CtVariableReadImpl]param, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowCallbackHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void processRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long parentId = [CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_PARENT_ID);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long crc = [CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.CRC_ALIAS);
                [CtInvocationImpl][CtVariableReadImpl]results.put([CtVariableReadImpl]parentId, [CtVariableReadImpl]crc);
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]results;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.IdAndEtag> getEntityChildren([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long parentId) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]parentId, [CtLiteralImpl]"parentId");
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.template.query([CtTypeAccessImpl]org.sagebionetworks.table.cluster.SELECT_ENTITY_CHILD_ID_ETAG, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.RowMapper<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.IdAndEtag>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.sagebionetworks.repo.model.IdAndEtag mapRow([CtParameterImpl][CtTypeReferenceImpl]java.sql.ResultSet rs, [CtParameterImpl][CtTypeReferenceImpl]int rowNum) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long id = [CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]TableConstants.ENTITY_REPLICATION_COL_ID);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String etag = [CtInvocationImpl][CtVariableReadImpl]rs.getString([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_ETAG);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long benefactorId = [CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ENTITY_REPLICATION_COL_BENEFACTOR_ID);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.wasNull()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]benefactorId = [CtLiteralImpl]null;
                }
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.IdAndEtag([CtVariableReadImpl]id, [CtVariableReadImpl]etag, [CtVariableReadImpl]benefactorId);
            }
        }, [CtVariableReadImpl]parentId);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> getExpiredContainerIds([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> entityContainerIds) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]entityContainerIds, [CtLiteralImpl]"entityContainerIds");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]entityContainerIds.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]java.lang.Long>();
        }
        [CtLocalVariableImpl][CtCommentImpl]/* An ID that does not exist, should be treated the same as an expired
        ID. Therefore, start off with all of the IDs expired, so the
        non-expired IDs can be removed.
         */
        [CtTypeReferenceImpl]java.util.LinkedHashSet<[CtTypeReferenceImpl]java.lang.Long> expiredId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<[CtTypeReferenceImpl]java.lang.Long>([CtVariableReadImpl]entityContainerIds);
        [CtLocalVariableImpl][CtCommentImpl]// Query for those that are not expired.
        [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource param = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource();
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.ID_PARAMETER_NAME, [CtVariableReadImpl]entityContainerIds);
        [CtInvocationImpl][CtVariableReadImpl]param.addValue([CtTypeAccessImpl]org.sagebionetworks.table.cluster.EXPIRES_PARAM, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> nonExpiredIds = [CtInvocationImpl][CtVariableReadImpl]namedTemplate.queryForList([CtTypeAccessImpl]org.sagebionetworks.table.cluster.SELECT_NON_EXPIRED_IDS, [CtVariableReadImpl]param, [CtFieldReadImpl]java.lang.Long.class);
        [CtInvocationImpl][CtCommentImpl]// remove all that are not expired.
        [CtVariableReadImpl]expiredId.removeAll([CtVariableReadImpl]nonExpiredIds);
        [CtReturnImpl][CtCommentImpl]// return the remain.
        return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]java.lang.Long>([CtVariableReadImpl]expiredId);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setContainerSynchronizationExpiration([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> toSet, [CtParameterImpl]final [CtTypeReferenceImpl]long newExpirationDateMS) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]toSet, [CtLiteralImpl]"toSet");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]toSet.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]template.batchUpdate([CtTypeAccessImpl]org.sagebionetworks.table.cluster.BATCH_INSERT_REPLICATION_SYNC_EXP, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.BatchPreparedStatementSetter()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void setValues([CtParameterImpl][CtTypeReferenceImpl]java.sql.PreparedStatement ps, [CtParameterImpl][CtTypeReferenceImpl]int i) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long idToSet = [CtInvocationImpl][CtVariableReadImpl]toSet.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]1;
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]index++, [CtVariableReadImpl]idToSet);
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]index++, [CtVariableReadImpl]newExpirationDateMS);
                [CtInvocationImpl][CtVariableReadImpl]ps.setLong([CtUnaryOperatorImpl][CtVariableWriteImpl]index++, [CtVariableReadImpl]newExpirationDateMS);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]int getBatchSize() [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]toSet.size();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void truncateReplicationSyncExpiration() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]template.update([CtTypeAccessImpl]org.sagebionetworks.table.cluster.TRUNCATE_REPLICATION_SYNC_EXPIRATION_TABLE);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> getRowIds([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sql, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameters) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]sql, [CtLiteralImpl]"sql");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]parameters, [CtLiteralImpl]"parameters");
        [CtLocalVariableImpl][CtCommentImpl]// We use spring to create create the prepared statement
        [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]namedTemplate.queryForList([CtVariableReadImpl]sql, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource([CtVariableReadImpl]parameters), [CtFieldReadImpl]java.lang.Long.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getSumOfFileSizes([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> rowIds) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]rowIds, [CtLiteralImpl]"rowIds");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rowIds.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0L;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long sum = [CtInvocationImpl][CtVariableReadImpl]namedTemplate.queryForObject([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.SQL_SUM_FILE_SIZES, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.MapSqlParameterSource([CtLiteralImpl]"rowIds", [CtVariableReadImpl]rowIds), [CtFieldReadImpl]java.lang.Long.class);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sum == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sum = [CtLiteralImpl]0L;
        }
        [CtReturnImpl]return [CtVariableReadImpl]sum;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void streamSynapseStorageStats([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.util.Callback<[CtTypeReferenceImpl]org.sagebionetworks.repo.model.report.SynapseStorageProjectStats> callback) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// We use spring to create create the prepared statement
        [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate namedTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate([CtFieldReadImpl][CtThisAccessImpl]this.template);
        [CtInvocationImpl][CtVariableReadImpl]namedTemplate.query([CtFieldReadImpl]org.sagebionetworks.table.cluster.TableIndexDAOImpl.SQL_SELECT_PROJECTS_BY_SIZE, [CtLambdaImpl]([CtParameterImpl] rs) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.report.SynapseStorageProjectStats stats = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sagebionetworks.repo.model.report.SynapseStorageProjectStats();
            [CtInvocationImpl][CtVariableReadImpl]stats.setId([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]1));
            [CtInvocationImpl][CtVariableReadImpl]stats.setProjectName([CtInvocationImpl][CtVariableReadImpl]rs.getString([CtLiteralImpl]2));
            [CtInvocationImpl][CtVariableReadImpl]stats.setSizeInBytes([CtInvocationImpl][CtVariableReadImpl]rs.getLong([CtLiteralImpl]3));
            [CtInvocationImpl][CtVariableReadImpl]callback.invoke([CtVariableReadImpl]stats);
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void populateViewFromSnapshot([CtParameterImpl][CtTypeReferenceImpl]org.sagebionetworks.repo.model.entity.IdAndVersion idAndVersion, [CtParameterImpl][CtTypeReferenceImpl]java.util.Iterator<[CtArrayTypeReferenceImpl]java.lang.String[]> input, [CtParameterImpl][CtTypeReferenceImpl]long maxBytesPerBatch) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]idAndVersion, [CtLiteralImpl]"idAndVersion");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]idAndVersion.getVersion().isPresent(), [CtLiteralImpl]"idAndVersion.version");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtVariableReadImpl]input, [CtLiteralImpl]"input");
        [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.util.ValidateArgument.required([CtInvocationImpl][CtVariableReadImpl]input.hasNext(), [CtLiteralImpl]"input is empty");
        [CtLocalVariableImpl][CtCommentImpl]// The first row is the header
        [CtArrayTypeReferenceImpl]java.lang.String[] headers = [CtInvocationImpl][CtVariableReadImpl]input.next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.createInsertViewFromSnapshot([CtVariableReadImpl]idAndVersion, [CtVariableReadImpl]headers);
        [CtLocalVariableImpl][CtCommentImpl]// push the data in batches
        [CtTypeReferenceImpl]java.util.List<[CtArrayTypeReferenceImpl]java.lang.Object[]> batch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int batchSize = [CtLiteralImpl]0;
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]input.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] row = [CtInvocationImpl][CtVariableReadImpl]input.next();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long rowSize = [CtInvocationImpl][CtTypeAccessImpl]org.sagebionetworks.table.cluster.SQLUtils.calculateBytes([CtVariableReadImpl]row);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]batchSize + [CtVariableReadImpl]rowSize) > [CtVariableReadImpl]maxBytesPerBatch) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]template.batchUpdate([CtVariableReadImpl]sql, [CtVariableReadImpl]batch);
                [CtInvocationImpl][CtVariableReadImpl]batch.clear();
            }
            [CtInvocationImpl][CtVariableReadImpl]batch.add([CtVariableReadImpl]row);
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]batchSize += [CtVariableReadImpl]rowSize;
        } 
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]batch.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]template.batchUpdate([CtVariableReadImpl]sql, [CtVariableReadImpl]batch);
        }
    }
}
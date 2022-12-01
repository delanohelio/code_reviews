[CompilationUnitImpl][CtCommentImpl]/* (C) Copyright IBM Corp. 2020

SPDX-License-Identifier: Apache-2.0
 */
[CtPackageDeclarationImpl]package com.ibm.fhir.persistence.jdbc.postgresql;
[CtImportImpl]import java.sql.Types;
[CtUnresolvedImport]import com.ibm.fhir.database.utils.postgresql.PostgreSqlTranslator;
[CtImportImpl]import java.sql.SQLException;
[CtImportImpl]import java.sql.Timestamp;
[CtImportImpl]import java.util.logging.Logger;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.api.FhirRefSequenceDAO;
[CtUnresolvedImport]import static com.ibm.fhir.persistence.jdbc.JDBCConstants.UTC;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.api.ParameterNameDAO;
[CtImportImpl]import java.sql.CallableStatement;
[CtImportImpl]import java.sql.ResultSet;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dto.Resource;
[CtUnresolvedImport]import com.ibm.fhir.persistence.exception.FHIRPersistenceException;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.api.ParameterDAO;
[CtImportImpl]import java.sql.Connection;
[CtUnresolvedImport]import com.ibm.fhir.persistence.exception.FHIRPersistenceVersionIdMismatchException;
[CtImportImpl]import java.sql.PreparedStatement;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceFKVException;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.impl.ParameterVisitorBatchDAO;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException;
[CtUnresolvedImport]import javax.transaction.TransactionSynchronizationRegistry;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException;
[CtImportImpl]import java.sql.SQLIntegrityConstraintViolationException;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dto.ExtractedParameterValue;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.api.CodeSystemDAO;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.dao.impl.ResourceDAOImpl;
[CtImportImpl]import java.util.logging.Level;
[CtUnresolvedImport]import com.ibm.fhir.persistence.jdbc.util.ResourceTypesCache;
[CtClassImpl][CtJavaDocImpl]/**
 * Data access object for writing FHIR resources to an postgresql database.
 *
 * @implNote This class follows the logic of the DB2 stored procedure, but does so
using a series of individual JDBC statements.
 */
public class PostgreSqlResourceDAO extends [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.impl.ResourceDAOImpl {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.class.getName());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CLASSNAME = [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.class.getSimpleName();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.ibm.fhir.database.utils.postgresql.PostgreSqlTranslator translator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.database.utils.postgresql.PostgreSqlTranslator();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SQL_READ_RESOURCE_TYPE = [CtLiteralImpl]"{CALL %s.add_resource_type(?, ?)}";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SQL_INSERT_WITH_PARAMETERS = [CtLiteralImpl]"{CALL %s.add_any_resource(?,?,?,?,?,?,?,?)}";

    [CtFieldImpl][CtCommentImpl]// DAO used to obtain sequence values from FHIR_REF_SEQUENCE
    private [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.api.FhirRefSequenceDAO fhirRefSequenceDAO;

    [CtFieldImpl][CtCommentImpl]// DAO used to manage parameter_names
    private [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.api.ParameterNameDAO parameterNameDAO;

    [CtFieldImpl][CtCommentImpl]// DAO used to manage code_systems
    private [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.api.CodeSystemDAO codeSystemDAO;

    [CtConstructorImpl]public PostgreSqlResourceDAO([CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection managedConnection) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]managedConnection);
    }

    [CtConstructorImpl]public PostgreSqlResourceDAO([CtParameterImpl][CtTypeReferenceImpl]javax.transaction.TransactionSynchronizationRegistry trxSynchRegistry) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]trxSynchRegistry);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Inserts the passed FHIR Resource and associated search parameters to a postgresql FHIR database.
     * The search parameters are stored first by calling the passed parameterDao. Then the Resource is stored
     * by sql.
     *
     * @param resource
     * 		The FHIR Resource to be inserted.
     * @param parameters
     * 		The Resource's search parameters to be inserted.
     * @param parameterDao
     * @return The Resource DTO
     * @throws FHIRPersistenceDataAccessException
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceVersionIdMismatchException
     */
    [CtCommentImpl]// @Override
    [CtCommentImpl]// public Resource  insert(Resource resource, List<ExtractedParameterValue> parameters, ParameterDAO parameterDao)
    [CtCommentImpl]// throws FHIRPersistenceException {
    [CtCommentImpl]// final String METHODNAME = "insert";
    [CtCommentImpl]// logger.entering(CLASSNAME, METHODNAME);
    [CtCommentImpl]// 
    [CtCommentImpl]// Connection connection = null;
    [CtCommentImpl]// Integer resourceTypeId;
    [CtCommentImpl]// Timestamp lastUpdated;
    [CtCommentImpl]// boolean acquiredFromCache;
    [CtCommentImpl]// long dbCallStartTime;
    [CtCommentImpl]// double dbCallDuration;
    [CtCommentImpl]// 
    [CtCommentImpl]// try {
    [CtCommentImpl]// connection = this.getConnection();
    [CtCommentImpl]// 
    [CtCommentImpl]// this.fhirRefSequenceDAO = new FhirRefSequenceDAOImpl(connection);
    [CtCommentImpl]// this.parameterNameDAO = new PostgreSqlParameterNamesDAO(connection, fhirRefSequenceDAO);
    [CtCommentImpl]// this.codeSystemDAO = new PostgreSqlCodeSystemDAO(connection, fhirRefSequenceDAO);
    [CtCommentImpl]// 
    [CtCommentImpl]// // Get resourceTypeId from ResourceTypesCache first.
    [CtCommentImpl]// resourceTypeId = ResourceTypesCache.getResourceTypeId(resource.getResourceType());
    [CtCommentImpl]// // If no found, then get resourceTypeId from local newResourceTypeIds in case this id is already in newResourceTypeIds
    [CtCommentImpl]// // but has not been updated to ResourceTypesCache yet. newResourceTypeIds is updated to ResourceTypesCache only when the
    [CtCommentImpl]// // current transaction is committed.
    [CtCommentImpl]// if (resourceTypeId == null) {
    [CtCommentImpl]// resourceTypeId = getResourceTypeIdFromCandidatorsCache(resource.getResourceType());
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// if (resourceTypeId == null) {
    [CtCommentImpl]// acquiredFromCache = false;
    [CtCommentImpl]// resourceTypeId = getOrCreateResourceType(resource.getResourceType(), connection);
    [CtCommentImpl]// this.addResourceTypeCacheCandidate(resource.getResourceType(), resourceTypeId);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// acquiredFromCache = true;
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// if (logger.isLoggable(Level.FINE)) {
    [CtCommentImpl]// logger.fine("resourceType=" + resource.getResourceType() + "  resourceTypeId=" + resourceTypeId +
    [CtCommentImpl]// "  acquiredFromCache=" + acquiredFromCache + "  tenantDatastoreCacheName=" + ResourceTypesCache.getCacheNameForTenantDatastore());
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// lastUpdated = resource.getLastUpdated();
    [CtCommentImpl]// dbCallStartTime = System.nanoTime();
    [CtCommentImpl]// 
    [CtCommentImpl]// final String sourceKey = UUID.randomUUID().toString();
    [CtCommentImpl]// 
    [CtCommentImpl]// long resourceId = this.storeResource(resource.getResourceType(),
    [CtCommentImpl]// parameters,
    [CtCommentImpl]// resource.getLogicalId(),
    [CtCommentImpl]// resource.getData(),
    [CtCommentImpl]// lastUpdated,
    [CtCommentImpl]// resource.isDeleted(),
    [CtCommentImpl]// sourceKey,
    [CtCommentImpl]// resource.getVersionId(),
    [CtCommentImpl]// connection
    [CtCommentImpl]// );
    [CtCommentImpl]// dbCallDuration = (System.nanoTime() - dbCallStartTime)/1e6;
    [CtCommentImpl]// 
    [CtCommentImpl]// resource.setId(resourceId);
    [CtCommentImpl]// if (logger.isLoggable(Level.FINE)) {
    [CtCommentImpl]// logger.fine("Successfully inserted Resource. id=" + resource.getId() + " executionTime=" + dbCallDuration + "ms");
    [CtCommentImpl]// }
    [CtCommentImpl]// } catch(FHIRPersistenceDBConnectException | FHIRPersistenceDataAccessException e) {
    [CtCommentImpl]// throw e;
    [CtCommentImpl]// } catch(SQLIntegrityConstraintViolationException e) {
    [CtCommentImpl]// FHIRPersistenceFKVException fx = new FHIRPersistenceFKVException("Encountered FK violation while inserting Resource.");
    [CtCommentImpl]// throw severe(logger, fx, e);
    [CtCommentImpl]// } catch(SQLException e) {
    [CtCommentImpl]// if ("99001".equals(e.getSQLState())) {
    [CtCommentImpl]// // this is just a concurrency update, so there's no need to log the SQLException here
    [CtCommentImpl]// throw new FHIRPersistenceVersionIdMismatchException("Encountered version id mismatch while inserting Resource");
    [CtCommentImpl]// } else {
    [CtCommentImpl]// FHIRPersistenceException fx = new FHIRPersistenceException("SQLException encountered while inserting Resource.");
    [CtCommentImpl]// throw severe(logger, fx, e);
    [CtCommentImpl]// }
    [CtCommentImpl]// } catch(Throwable e) {
    [CtCommentImpl]// FHIRPersistenceDataAccessException fx = new FHIRPersistenceDataAccessException("Failure inserting Resource.");
    [CtCommentImpl]// throw severe(logger, fx, e);
    [CtCommentImpl]// } finally {
    [CtCommentImpl]// this.cleanup(null, connection);
    [CtCommentImpl]// logger.exiting(CLASSNAME, METHODNAME);
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// return resource;
    [CtCommentImpl]// }
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dto.Resource insert([CtParameterImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dto.Resource resource, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dto.ExtractedParameterValue> parameters, [CtParameterImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.api.ParameterDAO parameterDao) throws [CtTypeReferenceImpl]com.ibm.fhir.persistence.exception.FHIRPersistenceException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String METHODNAME = [CtLiteralImpl]"insert(Resource, List<ExtractedParameterValue>";
        [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.entering([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.CLASSNAME, [CtVariableReadImpl]METHODNAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection connection = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.CallableStatement stmt = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentSchema;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stmtString = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer resourceTypeId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Timestamp lastUpdated;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean acquiredFromCache;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long dbCallStartTime;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double dbCallDuration;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]connection = [CtInvocationImpl][CtThisAccessImpl]this.getConnection();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parameterNameDAO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlParameterNamesDAO([CtVariableReadImpl]connection);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.codeSystemDAO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlCodeSystemDAO([CtVariableReadImpl]connection);
            [CtAssignmentImpl][CtVariableWriteImpl]resourceTypeId = [CtInvocationImpl]getResourceTypeIdFromCaches([CtInvocationImpl][CtVariableReadImpl]resource.getResourceType());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resourceTypeId == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]acquiredFromCache = [CtLiteralImpl]false;
                [CtAssignmentImpl][CtVariableWriteImpl]resourceTypeId = [CtInvocationImpl][CtThisAccessImpl]this.readResourceTypeId([CtInvocationImpl][CtVariableReadImpl]resource.getResourceType());
                [CtInvocationImpl][CtThisAccessImpl]this.addResourceTypeCacheCandidate([CtInvocationImpl][CtVariableReadImpl]resource.getResourceType(), [CtVariableReadImpl]resourceTypeId);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]acquiredFromCache = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.finer([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"resourceType=" + [CtInvocationImpl][CtVariableReadImpl]resource.getResourceType()) + [CtLiteralImpl]"  resourceTypeId=") + [CtVariableReadImpl]resourceTypeId) + [CtLiteralImpl]"  acquiredFromCache=") + [CtVariableReadImpl]acquiredFromCache) + [CtLiteralImpl]"  tenantDatastoreCacheName=") + [CtInvocationImpl][CtTypeAccessImpl]com.ibm.fhir.persistence.jdbc.util.ResourceTypesCache.getCacheNameForTenantDatastore());
            }
            [CtAssignmentImpl][CtCommentImpl]// TODO avoid the round-trip and use the configured data schema name
            [CtVariableWriteImpl]currentSchema = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]connection.getSchema().trim();
            [CtAssignmentImpl][CtVariableWriteImpl]stmtString = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.SQL_INSERT_WITH_PARAMETERS, [CtVariableReadImpl]currentSchema);
            [CtAssignmentImpl][CtVariableWriteImpl]stmt = [CtInvocationImpl][CtVariableReadImpl]connection.prepareCall([CtVariableReadImpl]stmtString);
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]resource.getResourceType());
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]resource.getLogicalId());
            [CtInvocationImpl][CtVariableReadImpl]stmt.setBytes([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]resource.getData());
            [CtAssignmentImpl][CtVariableWriteImpl]lastUpdated = [CtInvocationImpl][CtVariableReadImpl]resource.getLastUpdated();
            [CtInvocationImpl][CtVariableReadImpl]stmt.setTimestamp([CtLiteralImpl]4, [CtVariableReadImpl]lastUpdated, [CtTypeAccessImpl]com.ibm.fhir.persistence.jdbc.JDBCConstants.UTC);
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]5, [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]resource.isDeleted() ? [CtLiteralImpl]"Y" : [CtLiteralImpl]"N");
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]6, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString());
            [CtInvocationImpl][CtVariableReadImpl]stmt.setInt([CtLiteralImpl]7, [CtInvocationImpl][CtVariableReadImpl]resource.getVersionId());
            [CtInvocationImpl][CtVariableReadImpl]stmt.registerOutParameter([CtLiteralImpl]8, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]BIGINT);
            [CtAssignmentImpl][CtVariableWriteImpl]dbCallStartTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
            [CtInvocationImpl][CtVariableReadImpl]stmt.execute();
            [CtAssignmentImpl][CtVariableWriteImpl]dbCallDuration = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime() - [CtVariableReadImpl]dbCallStartTime) / [CtLiteralImpl]1000000.0;
            [CtInvocationImpl][CtVariableReadImpl]resource.setId([CtInvocationImpl][CtVariableReadImpl]stmt.getLong([CtLiteralImpl]8));
            [CtIfImpl][CtCommentImpl]// Parameter time
            [CtCommentImpl]// To keep things simple for the postgresql use-case, we just use a visitor to
            [CtCommentImpl]// handle inserts of parameters directly in the resource parameter tables.
            [CtCommentImpl]// Note we don't get any parameters for the resource soft-delete operation
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameters != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryWithResourceImpl][CtCommentImpl]// postgresql doesn't support partitioned multi-tenancy, so we disable it on the DAO:
                try ([CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.impl.ParameterVisitorBatchDAO pvd = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dao.impl.ParameterVisitorBatchDAO([CtVariableReadImpl]connection, [CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]resource.getResourceType(), [CtLiteralImpl]false, [CtInvocationImpl][CtVariableReadImpl]resource.getId(), [CtLiteralImpl]100, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.postgresql.ParameterNameCacheAdapter([CtFieldReadImpl]parameterNameDAO), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.postgresql.CodeSystemCacheAdapter([CtFieldReadImpl]codeSystemDAO))) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.dto.ExtractedParameterValue p : [CtVariableReadImpl]parameters) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]p.accept([CtVariableReadImpl]pvd);
                    }
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINE)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.fine([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Successfully inserted Resource. id=" + [CtInvocationImpl][CtVariableReadImpl]resource.getId()) + [CtLiteralImpl]" executionTime=") + [CtVariableReadImpl]dbCallDuration) + [CtLiteralImpl]"ms");
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException | [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLIntegrityConstraintViolationException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceFKVException fx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceFKVException([CtLiteralImpl]"Encountered FK violation while inserting Resource.");
            [CtThrowImpl]throw [CtInvocationImpl]severe([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger, [CtVariableReadImpl]fx, [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"99001".equals([CtInvocationImpl][CtVariableReadImpl]e.getSQLState())) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// this is just a concurrency update, so there's no need to log the SQLException here
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.exception.FHIRPersistenceVersionIdMismatchException([CtLiteralImpl]"Encountered version id mismatch while inserting Resource");
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException fx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException([CtLiteralImpl]"SQLException encountered while inserting Resource.");
                [CtThrowImpl]throw [CtInvocationImpl]severe([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger, [CtVariableReadImpl]fx, [CtVariableReadImpl]e);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException fx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException([CtLiteralImpl]"Failure inserting Resource.");
            [CtThrowImpl]throw [CtInvocationImpl]severe([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger, [CtVariableReadImpl]fx, [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtThisAccessImpl]this.cleanup([CtVariableReadImpl]stmt, [CtVariableReadImpl]connection);
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.exiting([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.CLASSNAME, [CtVariableReadImpl]METHODNAME);
        }
        [CtReturnImpl]return [CtVariableReadImpl]resource;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Store the resource in the database, creating a new logical_resource entry if this is
     * the first version of this resource, or creating a new resource entry if this a new
     * version of an existing logical resource. The logic tracks closely the DB2 stored
     * procedure implementation, including locking of the logical_resource and handling
     * concurrency issues using the standard insert-or-update pattern:
     * <pre>
     *   SELECT FOR UPDATE                 -- try and get a write lock
     *   IF NOT FOUND THEN                 -- doesn't exist, so we don't have a lock
     *     INSERT new logical resource     -- create the record - if OK, we own the lock
     *     IF DUPLICATE THEN               -- someone else beat us to the create
     *       SELECT FOR UPDATE             -- so we need to try again for a write lock
     *     ...
     *   ...
     * </pre>
     *
     * This works because we never delete a logical_resource record, and so don't have to deal
     * with concurrency issues caused when deletes are mingled with inserts/updates
     *
     * Note the execution flow aligns very closely with the DB2 stored procedure
     * implementation (fhir-persistence-schema/src/main/resources/add_any_resource.sql)
     *
     * @param tablePrefix
     * @param parameters
     * @param p_logical_id
     * @param p_payload
     * @param p_last_updated
     * @param p_is_deleted
     * @param p_source_key
     * @param p_version
     * @return the resource_id for the entry we created
     * @throws Exception
     */
    [CtCommentImpl]// public long storeResource(String tablePrefix, List<ExtractedParameterValue> parameters, String p_logical_id, byte[] p_payload, Timestamp p_last_updated, boolean p_is_deleted,
    [CtCommentImpl]// String p_source_key, Integer p_version, Connection conn) throws Exception {
    [CtCommentImpl]// 
    [CtCommentImpl]// final String METHODNAME = "storeResource() for " + tablePrefix + " resource";
    [CtCommentImpl]// logger.entering(CLASSNAME, METHODNAME);
    [CtCommentImpl]// 
    [CtCommentImpl]// Long v_logical_resource_id = null;
    [CtCommentImpl]// Long v_current_resource_id = null;
    [CtCommentImpl]// Long v_resource_id = null;
    [CtCommentImpl]// Integer v_resource_type_id = null;
    [CtCommentImpl]// boolean v_new_resource = false;
    [CtCommentImpl]// boolean v_not_found = false;
    [CtCommentImpl]// boolean v_duplicate = false;
    [CtCommentImpl]// int v_version = 0;
    [CtCommentImpl]// int v_insert_version = 0;
    [CtCommentImpl]// 
    [CtCommentImpl]// String v_resource_type = tablePrefix;
    [CtCommentImpl]// 
    [CtCommentImpl]// // Map the resource type name to the normalized id value in the database
    [CtCommentImpl]// v_resource_type_id = getResourceTypeId(v_resource_type, conn);
    [CtCommentImpl]// if (v_resource_type_id == null) {
    [CtCommentImpl]// // programming error, as this should've been created earlier
    [CtCommentImpl]// throw new IllegalStateException("resource type not found: " + v_resource_type);
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // Get a lock at the system-wide logical resource level.
    [CtCommentImpl]// final String SELECT_FOR_UPDATE = "SELECT logical_resource_id FROM logical_resources WHERE resource_type_id = ? AND logical_id = ? FOR UPDATE";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(SELECT_FOR_UPDATE)) {
    [CtCommentImpl]// stmt.setInt(1, v_resource_type_id);
    [CtCommentImpl]// stmt.setString(2, p_logical_id);
    [CtCommentImpl]// ResultSet rs = stmt.executeQuery();
    [CtCommentImpl]// if (rs.next()) {
    [CtCommentImpl]// v_logical_resource_id = rs.getLong(1);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// v_not_found = true;
    [CtCommentImpl]// v_logical_resource_id = -1L; // just to be careful
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // Create the logical resource if we don't have it already
    [CtCommentImpl]// if (v_not_found) {
    [CtCommentImpl]// // grab the id we want to use for the new logical resource instance
    [CtCommentImpl]// final String sql2 = "SELECT nextval('fhir_sequence')";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
    [CtCommentImpl]// ResultSet res = stmt.executeQuery();
    [CtCommentImpl]// if (res.next()) {
    [CtCommentImpl]// v_logical_resource_id = res.getLong(1);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// // not going to happen, unless someone butchers the statement being executed
    [CtCommentImpl]// throw new IllegalStateException("VALUES failed to return a row: " + sql2);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// try {
    [CtCommentImpl]// // insert the system-wide logical resource record.
    [CtCommentImpl]// final String sql3 = "INSERT INTO logical_resources (logical_resource_id, resource_type_id, logical_id) VALUES (?, ?, ?)";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setLong(1, v_logical_resource_id);
    [CtCommentImpl]// stmt.setInt(2, v_resource_type_id);
    [CtCommentImpl]// stmt.setString(3, p_logical_id);
    [CtCommentImpl]// stmt.executeUpdate();
    [CtCommentImpl]// }
    [CtCommentImpl]// } catch (SQLException e) {
    [CtCommentImpl]// if (translator.isDuplicate(e)) {
    [CtCommentImpl]// v_duplicate = true;
    [CtCommentImpl]// }  else {
    [CtCommentImpl]// throw e;
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// /**
    [CtCommentImpl]// * remember that we have a concurrent system...so there is a possibility
    [CtCommentImpl]// * that another thread snuck in before us and created the logical resource. This
    [CtCommentImpl]// * is easy to handle, just turn around and read it
    [CtCommentImpl]// */
    [CtCommentImpl]// if (v_duplicate) {
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(SELECT_FOR_UPDATE)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setInt(1, v_resource_type_id);
    [CtCommentImpl]// stmt.setString(2, p_logical_id);
    [CtCommentImpl]// ResultSet res = stmt.executeQuery();
    [CtCommentImpl]// if (res.next()) {
    [CtCommentImpl]// v_logical_resource_id = res.getLong(1);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// // Extremely unlikely as we should never delete logical resource records
    [CtCommentImpl]// throw new IllegalStateException("Logical resource was deleted: " + tablePrefix + "/" + p_logical_id);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// } else {
    [CtCommentImpl]// v_new_resource = true;
    [CtCommentImpl]// 
    [CtCommentImpl]// // Insert the resource-specific logical resource record. Remember that logical_id is denormalized
    [CtCommentImpl]// // so it gets stored again here for convenience
    [CtCommentImpl]// final String sql3 = "INSERT INTO " + tablePrefix + "_logical_resources (logical_resource_id, logical_id) VALUES (?, ?)";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setLong(1, v_logical_resource_id);
    [CtCommentImpl]// stmt.setString(2, p_logical_id);
    [CtCommentImpl]// stmt.executeUpdate();
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// if (!v_new_resource) {
    [CtCommentImpl]// // existing resource.  We need to know the current version from the
    [CtCommentImpl]// // resource-specific logical resources table.
    [CtCommentImpl]// final String sql3 = "SELECT current_resource_id FROM " + tablePrefix + "_logical_resources WHERE logical_resource_id = ?";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
    [CtCommentImpl]// stmt.setLong(1, v_logical_resource_id);
    [CtCommentImpl]// ResultSet rs = stmt.executeQuery();
    [CtCommentImpl]// if (rs.next()) {
    [CtCommentImpl]// v_current_resource_id = rs.getLong(1);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// // This database is broken, because we shouldn't have logical_resource records without
    [CtCommentImpl]// // corresponding resource-specific logical_resource records.
    [CtCommentImpl]// throw new SQLException("Logical_id record '" + p_logical_id + "' missing for resource " + tablePrefix);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // so if we are storing a specific version, do a quick check to make
    [CtCommentImpl]// // sure that this version doesn't currently exist. This is only done when processing
    [CtCommentImpl]// // replication messages which might be duplicated. We want the operation to be idempotent,
    [CtCommentImpl]// // so if the resource already exists, we don't need to do anything else.
    [CtCommentImpl]// 
    [CtCommentImpl]// if (p_version != null) {
    [CtCommentImpl]// final String sqlStmt = "SELECT resource_id FROM " + tablePrefix + "_resources dr WHERE dr.logical_resource_id = ? AND dr.version_id = ?";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setLong(1, v_logical_resource_id);
    [CtCommentImpl]// stmt.setLong(2, p_version);
    [CtCommentImpl]// ResultSet res = stmt.executeQuery();
    [CtCommentImpl]// if (res.next()) {
    [CtCommentImpl]// // this version of this resource already exists, so we bail out right away
    [CtCommentImpl]// v_resource_id = res.getLong(1);
    [CtCommentImpl]// return v_resource_id;
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // Grab the version value for the current version (identified by v_current_resource_id)
    [CtCommentImpl]// final String sql4 = "SELECT version_id FROM " + tablePrefix + "_resources WHERE resource_id = ?";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql4)) {
    [CtCommentImpl]// stmt.setLong(1, v_current_resource_id);
    [CtCommentImpl]// ResultSet res = stmt.executeQuery();
    [CtCommentImpl]// if (res.next()) {
    [CtCommentImpl]// v_version = res.getInt(1);
    [CtCommentImpl]// } else {
    [CtCommentImpl]// throw new IllegalStateException("current resource not found: "
    [CtCommentImpl]// + tablePrefix + "_resources.resource_id=" + v_current_resource_id);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// //If we have been passed a version number, this means that this is a replicated
    [CtCommentImpl]// //resource, and so we only need to delete parameters if the given version is
    [CtCommentImpl]// // later than the current version
    [CtCommentImpl]// if (p_version == null || p_version > v_version) {
    [CtCommentImpl]// // existing resource, so need to delete all its parameters
    [CtCommentImpl]// // delete composites first, or else the foreign keys there restrict deletes on referenced tables
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_composites", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_str_values", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_number_values", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_date_values", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_latlng_values", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_token_values", v_logical_resource_id);
    [CtCommentImpl]// deleteFromParameterTable(conn, tablePrefix + "_quantity_values", v_logical_resource_id);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // Persist the data using the given version number if required
    [CtCommentImpl]// if (p_version != null) {
    [CtCommentImpl]// v_insert_version = p_version;
    [CtCommentImpl]// } else {
    [CtCommentImpl]// // remember we have a write (update) lock on the logical version, so we can safely calculate
    [CtCommentImpl]// // the next version value here
    [CtCommentImpl]// v_insert_version = v_version + 1;
    [CtCommentImpl]// 
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// /**
    [CtCommentImpl]// * Create the new resource version.
    [CtCommentImpl]// * uses last_updated time from the app-server, so we have consistency between the various DAOs
    [CtCommentImpl]// */
    [CtCommentImpl]// String sql2 = "SELECT nextval('fhir_sequence')";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
    [CtCommentImpl]// ResultSet res = stmt.executeQuery();
    [CtCommentImpl]// if (res.next()) {
    [CtCommentImpl]// v_resource_id = res.getLong(1); //Assign result of the above query
    [CtCommentImpl]// } else {
    [CtCommentImpl]// // unlikely
    [CtCommentImpl]// throw new IllegalStateException("no row returned: " + sql2);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // Finally we get to the big resource data insert
    [CtCommentImpl]// String sql3 = "INSERT INTO " + tablePrefix + "_resources (resource_id, logical_resource_id, version_id, data, last_updated, is_deleted) "
    [CtCommentImpl]// + "VALUES (?,?,?,?,?,?)";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setLong(1, v_resource_id);
    [CtCommentImpl]// stmt.setLong(2, v_logical_resource_id);
    [CtCommentImpl]// stmt.setInt(3, v_insert_version);
    [CtCommentImpl]// stmt.setBytes(4, p_payload);
    [CtCommentImpl]// stmt.setTimestamp(5, p_last_updated, UTC);
    [CtCommentImpl]// stmt.setString(6, p_is_deleted ? "Y" : "N");
    [CtCommentImpl]// stmt.executeUpdate();
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// if (p_version == null || p_version > v_version) {
    [CtCommentImpl]// //only update the logical resource if the resource we are adding supercedes the
    [CtCommentImpl]// //current resource
    [CtCommentImpl]// String sql4 = "UPDATE " + tablePrefix + "_logical_resources SET current_resource_id = ? WHERE logical_resource_id = ?";
    [CtCommentImpl]// try (PreparedStatement stmt = conn.prepareStatement(sql4)) {
    [CtCommentImpl]// // bind parameters
    [CtCommentImpl]// stmt.setLong(1, v_resource_id);
    [CtCommentImpl]// stmt.setLong(2, v_logical_resource_id);
    [CtCommentImpl]// stmt.executeUpdate();
    [CtCommentImpl]// }
    [CtCommentImpl]// 
    [CtCommentImpl]// // To keep things simple for the postgresql use-case, we just use a visitor to
    [CtCommentImpl]// // handle inserts of parameters directly in the resource parameter tables.
    [CtCommentImpl]// // Note we don't get any parameters for the resource soft-delete operation
    [CtCommentImpl]// if (parameters != null) {
    [CtCommentImpl]// // postgresql doesn't support partitioned multi-tenancy, so we disable it on the DAO:
    [CtCommentImpl]// try (ParameterVisitorBatchDAO pvd = new ParameterVisitorBatchDAO(conn, null, tablePrefix, false, v_logical_resource_id, 100,
    [CtCommentImpl]// new ParameterNameCacheAdapter(parameterNameDAO), new CodeSystemCacheAdapter(codeSystemDAO))) {
    [CtCommentImpl]// for (ExtractedParameterValue p: parameters) {
    [CtCommentImpl]// p.accept(pvd);
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// }
    [CtCommentImpl]// logger.exiting(CLASSNAME, METHODNAME);
    [CtCommentImpl]// return v_resource_id;
    [CtCommentImpl]// }
    [CtJavaDocImpl]/**
     * Delete all parameters for the given resourceId from the parameters table
     *
     * @param conn
     * @param tableName
     * @param logicalResourceId
     * @throws SQLException
     */
    protected [CtTypeReferenceImpl]void deleteFromParameterTable([CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection conn, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tableName, [CtParameterImpl][CtTypeReferenceImpl]long logicalResourceId) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String delStrValues = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"DELETE FROM " + [CtVariableReadImpl]tableName) + [CtLiteralImpl]" WHERE logical_resource_id = ?";
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement stmt = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtVariableReadImpl]delStrValues)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// bind parameters
            [CtVariableReadImpl]stmt.setLong([CtLiteralImpl]1, [CtVariableReadImpl]logicalResourceId);
            [CtInvocationImpl][CtVariableReadImpl]stmt.executeUpdate();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Read the id for the named type
     *
     * @param resourceTypeName
     * @return the database id, or null if the named record is not found
     * @throws SQLException
     */
    protected [CtTypeReferenceImpl]java.lang.Integer getResourceTypeId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceTypeName, [CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection conn) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer result = [CtLiteralImpl]null;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String sql = [CtLiteralImpl]"SELECT resource_type_id FROM resource_types WHERE resource_type = ?";
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement stmt = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtVariableReadImpl]sql)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]1, [CtVariableReadImpl]resourceTypeName);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtInvocationImpl][CtVariableReadImpl]stmt.executeQuery();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rs.next()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]rs.getInt([CtLiteralImpl]1);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * stored-procedure-less implementation for managing the resource_types table
     *
     * @param resourceTypeName
     * @throw SQLException
     */
    public [CtTypeReferenceImpl]int getOrCreateResourceType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceTypeName, [CtParameterImpl][CtTypeReferenceImpl]java.sql.Connection conn) throws [CtTypeReferenceImpl]java.sql.SQLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// As the system is concurrent, we have to handle cases where another thread
        [CtCommentImpl]// might create the entry after we selected and found nothing
        [CtTypeReferenceImpl]java.lang.Integer result = [CtInvocationImpl]getResourceTypeId([CtVariableReadImpl]resourceTypeName, [CtVariableReadImpl]conn);
        [CtIfImpl][CtCommentImpl]// Create the resource if we don't have it already (set by the continue handler)
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtFieldReadImpl]fhirRefSequenceDAO.nextValue();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String INS = [CtLiteralImpl]"INSERT INTO resource_types (resource_type_id, resource_type) VALUES (?, ?)";
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement stmt = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtVariableReadImpl]INS)) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// bind parameters
                    [CtVariableReadImpl]stmt.setInt([CtLiteralImpl]1, [CtVariableReadImpl]result);
                    [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]2, [CtVariableReadImpl]resourceTypeName);
                    [CtInvocationImpl][CtVariableReadImpl]stmt.executeUpdate();
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]e;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Integer readResourceTypeId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceType) throws [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException, [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String METHODNAME = [CtLiteralImpl]"readResourceTypeId";
        [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.entering([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.CLASSNAME, [CtVariableReadImpl]METHODNAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection connection = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.CallableStatement stmt = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer resourceTypeId = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentSchema;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stmtString;
        [CtLocalVariableImpl][CtTypeReferenceImpl]long dbCallStartTime;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double dbCallDuration;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]connection = [CtInvocationImpl][CtThisAccessImpl]this.getConnection();
            [CtAssignmentImpl][CtVariableWriteImpl]currentSchema = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]connection.getSchema().trim();
            [CtAssignmentImpl][CtVariableWriteImpl]stmtString = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.SQL_READ_RESOURCE_TYPE, [CtVariableReadImpl]currentSchema);
            [CtAssignmentImpl][CtVariableWriteImpl]stmt = [CtInvocationImpl][CtVariableReadImpl]connection.prepareCall([CtVariableReadImpl]stmtString);
            [CtInvocationImpl][CtVariableReadImpl]stmt.setString([CtLiteralImpl]1, [CtVariableReadImpl]resourceType);
            [CtInvocationImpl][CtVariableReadImpl]stmt.registerOutParameter([CtLiteralImpl]2, [CtFieldReadImpl][CtTypeAccessImpl]java.sql.Types.[CtFieldReferenceImpl]INTEGER);
            [CtAssignmentImpl][CtVariableWriteImpl]dbCallStartTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime();
            [CtInvocationImpl][CtVariableReadImpl]stmt.execute();
            [CtAssignmentImpl][CtVariableWriteImpl]dbCallDuration = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.nanoTime() - [CtVariableReadImpl]dbCallStartTime) / [CtLiteralImpl]1000000.0;
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.finer([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"DB read resource type id complete. executionTime=" + [CtVariableReadImpl]dbCallDuration) + [CtLiteralImpl]"ms");
            }
            [CtAssignmentImpl][CtVariableWriteImpl]resourceTypeId = [CtInvocationImpl][CtVariableReadImpl]stmt.getInt([CtLiteralImpl]2);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String errMsg = [CtBinaryOperatorImpl][CtLiteralImpl]"Failure storing Resource type name id: name=" + [CtVariableReadImpl]resourceType;
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException fx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException([CtVariableReadImpl]errMsg);
            [CtThrowImpl]throw [CtInvocationImpl]severe([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger, [CtVariableReadImpl]fx, [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtThisAccessImpl]this.cleanup([CtVariableReadImpl]stmt, [CtVariableReadImpl]connection);
            [CtInvocationImpl][CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.logger.exiting([CtFieldReadImpl]com.ibm.fhir.persistence.jdbc.postgresql.PostgreSqlResourceDAO.CLASSNAME, [CtVariableReadImpl]METHODNAME);
        }
        [CtReturnImpl]return [CtVariableReadImpl]resourceTypeId;
    }
}
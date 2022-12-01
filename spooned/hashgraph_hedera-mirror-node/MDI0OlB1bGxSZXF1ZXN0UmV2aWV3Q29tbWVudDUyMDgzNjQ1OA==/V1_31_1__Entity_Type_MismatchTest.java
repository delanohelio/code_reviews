[CompilationUnitImpl][CtPackageDeclarationImpl]package com.hedera.mirror.importer.migration;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtUnresolvedImport]import com.hedera.mirror.importer.domain.TransactionTypeEnum;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.*;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.hedera.mirror.importer.repository.TransactionRepository;
[CtUnresolvedImport]import org.flywaydb.core.api.configuration.Configuration;
[CtUnresolvedImport]import com.hedera.mirror.importer.domain.EntityId;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.flywaydb.core.api.migration.Context;
[CtUnresolvedImport]import com.hedera.mirror.importer.domain.EntityTypeEnum;
[CtImportImpl]import java.time.Instant;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.ResponseCodeEnum;
[CtImportImpl]import java.sql.Connection;
[CtImportImpl]import javax.annotation.Resource;
[CtUnresolvedImport]import org.springframework.test.context.TestPropertySource;
[CtImportImpl]import javax.sql.DataSource;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import com.hedera.mirror.importer.IntegrationTest;
[CtUnresolvedImport]import com.hedera.mirror.importer.MirrorProperties;
[CtUnresolvedImport]import com.hedera.mirror.importer.repository.EntityRepository;
[CtUnresolvedImport]import lombok.extern.log4j.Log4j2;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import com.hedera.mirror.importer.domain.Transaction;
[CtUnresolvedImport]import com.hedera.mirror.importer.domain.Entities;
[CtClassImpl][CtAnnotationImpl]@lombok.extern.log4j.Log4j2
[CtAnnotationImpl]@org.springframework.test.context.TestPropertySource(properties = [CtLiteralImpl]"spring.flyway.target=1.31.0")
public class V1_31_1__Entity_Type_MismatchTest extends [CtTypeReferenceImpl]com.hedera.mirror.importer.IntegrationTest {
    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.V1_31_1__Entity_Type_Mismatch migration;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]javax.sql.DataSource dataSource;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]com.hedera.mirror.importer.repository.EntityRepository entityRepository;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]com.hedera.mirror.importer.MirrorProperties mirrorProperties;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]com.hedera.mirror.importer.repository.TransactionRepository transactionRepository;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Resource
    private [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.FlywayMigrationProperties flywayMigrationProperties;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    [CtTypeReferenceImpl]void before() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mirrorProperties.setStartDate([CtFieldReadImpl][CtTypeAccessImpl]java.time.Instant.[CtFieldReferenceImpl]EPOCH);
        [CtInvocationImpl][CtFieldReadImpl]mirrorProperties.setEndDate([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.Instant.[CtFieldReferenceImpl]EPOCH.plusSeconds([CtLiteralImpl]1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void verifyEntityTypeMigrationEmpty() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// migration
        [CtFieldReadImpl]migration.migrate([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.V1_31_1__Entity_Type_MismatchTest.FlywayContext());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]entityRepository.count());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]transactionRepository.count());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void verifyEntityTypeMigrationValidEntities() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchReadPageSize([CtLiteralImpl]3);
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchWriteBatchSize([CtLiteralImpl]3);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.FILE));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.TOKEN));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction> transactionList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]1, [CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CRYPTOCREATEACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]20, [CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONTRACTCREATEINSTANCE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]30, [CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.FILE, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.FILECREATE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]40, [CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSCREATETOPIC));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]50, [CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.TOKEN, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.TOKENCREATION));
        [CtInvocationImpl][CtFieldReadImpl]transactionRepository.saveAll([CtVariableReadImpl]transactionList);
        [CtInvocationImpl][CtCommentImpl]// migration
        [CtFieldReadImpl]migration.migrate([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.V1_31_1__Entity_Type_MismatchTest.FlywayContext());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtFieldReadImpl]entityRepository.count());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]5, [CtInvocationImpl][CtFieldReadImpl]transactionRepository.count());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void verifyEntityTypeMigrationInValidEntities() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchReadPageSize([CtLiteralImpl]3);
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchWriteBatchSize([CtLiteralImpl]3);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedAccountEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.TOPIC);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedContractEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.TOKEN);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedFileEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedTopicEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedTokenEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.FILE);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedAccountEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedContractEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedFileEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedTopicEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedTokenEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]50, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]100, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction> transactionList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]1, [CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CRYPTOCREATEACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]20, [CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONTRACTCREATEINSTANCE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]30, [CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.FILE, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.FILECREATE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]40, [CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSCREATETOPIC));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]50, [CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.TOKEN, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.TOKENCREATION));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]70, [CtLiteralImpl]50, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.INVALID_TOPIC_ID, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSSUBMITMESSAGE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]80, [CtLiteralImpl]100, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.TOPIC_EXPIRED, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSSUBMITMESSAGE));
        [CtInvocationImpl][CtFieldReadImpl]transactionRepository.saveAll([CtVariableReadImpl]transactionList);
        [CtInvocationImpl][CtCommentImpl]// migration
        [CtFieldReadImpl]migration.migrate([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.V1_31_1__Entity_Type_MismatchTest.FlywayContext());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtFieldReadImpl]entityRepository.count());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]7, [CtInvocationImpl][CtFieldReadImpl]transactionRepository.count());
        [CtInvocationImpl]assertAll([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedAccountEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.ACCOUNT.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedContractEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.CONTRACT.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedFileEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.FILE.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedTopicEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.TOPIC.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedTokenEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.TOKEN.getId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void verifyEntityTypeMigrationInValidEntitiesMultiBatch() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchReadPageSize([CtLiteralImpl]4);
        [CtInvocationImpl][CtFieldReadImpl]flywayMigrationProperties.setEntityMismatchWriteBatchSize([CtLiteralImpl]4);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.FILE));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.TOKEN));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedAccountEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]6, [CtTypeAccessImpl]EntityTypeEnum.TOPIC);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedContractEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]7, [CtTypeAccessImpl]EntityTypeEnum.TOKEN);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedFileEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]8, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedTopicEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]9, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId typeMismatchedTokenEntityId = [CtInvocationImpl]entityId([CtLiteralImpl]10, [CtTypeAccessImpl]EntityTypeEnum.FILE);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedAccountEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedContractEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedFileEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedTopicEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtVariableReadImpl]typeMismatchedTokenEntityId);
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]50, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtInvocationImpl][CtFieldReadImpl]entityRepository.insertEntityId([CtInvocationImpl]entityId([CtLiteralImpl]100, [CtTypeAccessImpl]EntityTypeEnum.TOPIC));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction> transactionList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]1, [CtLiteralImpl]1, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CRYPTOCREATEACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]20, [CtLiteralImpl]2, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONTRACTCREATEINSTANCE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]30, [CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.FILE, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.FILECREATE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]40, [CtLiteralImpl]4, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSCREATETOPIC));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]50, [CtLiteralImpl]5, [CtTypeAccessImpl]EntityTypeEnum.TOKEN, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.TOKENCREATION));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]60, [CtLiteralImpl]6, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CRYPTOCREATEACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]70, [CtLiteralImpl]7, [CtTypeAccessImpl]EntityTypeEnum.CONTRACT, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONTRACTCREATEINSTANCE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]80, [CtLiteralImpl]8, [CtTypeAccessImpl]EntityTypeEnum.FILE, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.FILECREATE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]90, [CtLiteralImpl]9, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSCREATETOPIC));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]100, [CtLiteralImpl]10, [CtTypeAccessImpl]EntityTypeEnum.TOKEN, [CtTypeAccessImpl]ResponseCodeEnum.SUCCESS, [CtTypeAccessImpl]TransactionTypeEnum.TOKENCREATION));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]500, [CtLiteralImpl]50, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.INVALID_TOPIC_ID, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSSUBMITMESSAGE));
        [CtInvocationImpl][CtVariableReadImpl]transactionList.add([CtInvocationImpl]transaction([CtLiteralImpl]1000, [CtLiteralImpl]100, [CtTypeAccessImpl]EntityTypeEnum.TOPIC, [CtTypeAccessImpl]ResponseCodeEnum.TOPIC_EXPIRED, [CtTypeAccessImpl]TransactionTypeEnum.CONSENSUSSUBMITMESSAGE));
        [CtInvocationImpl][CtFieldReadImpl]transactionRepository.saveAll([CtVariableReadImpl]transactionList);
        [CtInvocationImpl][CtCommentImpl]// migration
        [CtFieldReadImpl]migration.migrate([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.migration.V1_31_1__Entity_Type_MismatchTest.FlywayContext());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtFieldReadImpl]entityRepository.count());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]12, [CtInvocationImpl][CtFieldReadImpl]transactionRepository.count());
        [CtInvocationImpl]assertAll([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedAccountEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.ACCOUNT.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedContractEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.CONTRACT.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedFileEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.FILE.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedTopicEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.TOPIC.getId()), [CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]entityRepository.findById([CtInvocationImpl][CtVariableReadImpl]typeMismatchedTokenEntityId.getId())).isPresent().get().extracting([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.Entities::getEntityTypeId).isEqualTo([CtInvocationImpl][CtVariableReadImpl]EntityTypeEnum.TOKEN.getId()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction transaction([CtParameterImpl][CtTypeReferenceImpl]long consensusNs, [CtParameterImpl][CtTypeReferenceImpl]long id, [CtParameterImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityTypeEnum entityType, [CtParameterImpl][CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.ResponseCodeEnum result, [CtParameterImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.TransactionTypeEnum transactionTypeEnum) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction transaction = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.domain.Transaction();
        [CtInvocationImpl][CtVariableReadImpl]transaction.setChargedTxFee([CtLiteralImpl]100L);
        [CtInvocationImpl][CtVariableReadImpl]transaction.setConsensusNs([CtVariableReadImpl]consensusNs);
        [CtInvocationImpl][CtVariableReadImpl]transaction.setEntityId([CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.EntityId.of([CtLiteralImpl]0, [CtLiteralImpl]1, [CtVariableReadImpl]id, [CtVariableReadImpl]entityType));
        [CtInvocationImpl][CtVariableReadImpl]transaction.setInitialBalance([CtLiteralImpl]1000L);
        [CtInvocationImpl][CtVariableReadImpl]transaction.setMemo([CtInvocationImpl][CtLiteralImpl]"transaction memo".getBytes());
        [CtInvocationImpl][CtVariableReadImpl]transaction.setNodeAccountId([CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.EntityId.of([CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]3, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transaction.setPayerAccountId([CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.EntityId.of([CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]98, [CtTypeAccessImpl]EntityTypeEnum.ACCOUNT));
        [CtInvocationImpl][CtVariableReadImpl]transaction.setResult([CtInvocationImpl][CtVariableReadImpl]result.getNumber());
        [CtInvocationImpl][CtVariableReadImpl]transaction.setType([CtInvocationImpl][CtVariableReadImpl]transactionTypeEnum.getProtoId());
        [CtInvocationImpl][CtVariableReadImpl]transaction.setValidStartNs([CtLiteralImpl]20L);
        [CtInvocationImpl][CtVariableReadImpl]transaction.setValidDurationSeconds([CtLiteralImpl]11L);
        [CtInvocationImpl][CtVariableReadImpl]transaction.setMaxFee([CtLiteralImpl]33L);
        [CtReturnImpl]return [CtVariableReadImpl]transaction;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityId entityId([CtParameterImpl][CtTypeReferenceImpl]long id, [CtParameterImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.domain.EntityTypeEnum entityType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.domain.EntityId.of([CtLiteralImpl]0, [CtLiteralImpl]1, [CtVariableReadImpl]id, [CtVariableReadImpl]entityType);
    }

    [CtClassImpl]private class FlywayContext implements [CtTypeReferenceImpl]org.flywaydb.core.api.migration.Context {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.flywaydb.core.api.configuration.Configuration getConfiguration() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.sql.Connection getConnection() [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]dataSource.getConnection();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
            }
        }
    }
}
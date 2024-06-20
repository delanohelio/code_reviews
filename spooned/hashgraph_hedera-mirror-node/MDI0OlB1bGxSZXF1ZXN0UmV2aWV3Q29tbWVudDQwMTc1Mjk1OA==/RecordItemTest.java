[CompilationUnitImpl][CtPackageDeclarationImpl]package com.hedera.mirror.importer.parser.domain;
[CtUnresolvedImport]import com.google.protobuf.ByteString;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.Transaction;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.TransactionRecord;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.TransactionBody;
[CtUnresolvedImport]import com.hedera.mirror.importer.exception.ParserException;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.*;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.SignatureMap;
[CtUnresolvedImport]import com.hedera.mirror.importer.parser.record.transactionhandler.AbstractTransactionHandlerTest;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.SignaturePair;
[CtUnresolvedImport]import com.hederahashgraph.api.proto.java.TransactionReceipt;
[CtClassImpl]class RecordItemTest extends [CtTypeReferenceImpl]com.hedera.mirror.importer.parser.record.transactionhandler.AbstractTransactionHandlerTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.Transaction DEFAULT_TRANSACTION = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.Transaction.newBuilder().setBodyBytes([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.TransactionBody.getDefaultInstance().toByteString()).build();

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] DEFAULT_TRANSACTION_BYTES = [CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.DEFAULT_TRANSACTION.toByteArray();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.TransactionRecord DEFAULT_RECORD = [CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.TransactionRecord.getDefaultInstance();

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] DEFAULT_RECORD_BYTES = [CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.DEFAULT_RECORD.toByteArray();

    [CtFieldImpl][CtCommentImpl]// 'body' and 'bodyBytes' feilds left empty
    private static final [CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.Transaction TRANSACTION = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.Transaction.newBuilder().setSigMap([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.SignatureMap.newBuilder().addSigPair([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.SignaturePair.newBuilder().setEd25519([CtInvocationImpl][CtTypeAccessImpl]com.google.protobuf.ByteString.copyFromUtf8([CtLiteralImpl]"ed25519")).setPubKeyPrefix([CtInvocationImpl][CtTypeAccessImpl]com.google.protobuf.ByteString.copyFromUtf8([CtLiteralImpl]"pubKeyPrefix")).build()).build()).build();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.TransactionBody TRANSACTION_BODY = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.TransactionBody.newBuilder().setTransactionFee([CtLiteralImpl]10L).setMemo([CtLiteralImpl]"memo").build();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.TransactionRecord TRANSACTION_RECORD = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.TransactionRecord.newBuilder().setReceipt([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.TransactionReceipt.newBuilder().setStatusValue([CtLiteralImpl]22).build()).setMemo([CtLiteralImpl]"memo").build();

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testBadTransactionBytesThrowException() [CtBlockImpl]{
        [CtInvocationImpl]testException([CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtLiteralImpl]0x0, [CtLiteralImpl]0x1 }, [CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.DEFAULT_RECORD_BYTES, [CtTypeAccessImpl]RecordItem.BAD_TRANSACTION_BYTES_MESSAGE);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testBadRecordBytesThrowException() [CtBlockImpl]{
        [CtInvocationImpl]testException([CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.DEFAULT_TRANSACTION_BYTES, [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtLiteralImpl]0x0, [CtLiteralImpl]0x1 }, [CtTypeAccessImpl]RecordItem.BAD_RECORD_BYTES_MESSAGE);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testTransactionBytesWithoutTransactionBodyThrowException() [CtBlockImpl]{
        [CtInvocationImpl]testException([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hederahashgraph.api.proto.java.Transaction.newBuilder().build().toByteArray(), [CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.DEFAULT_RECORD_BYTES, [CtTypeAccessImpl]RecordItem.BAD_TRANSACTION_BODY_BYTES_MESSAGE);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testWithBody() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.Transaction transaction = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION.toBuilder().setBody([CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_BODY).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]RecordItem recordItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RecordItem([CtInvocationImpl][CtVariableReadImpl]transaction.toByteArray(), [CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_RECORD.toByteArray());
        [CtInvocationImpl]assertRecordItem([CtVariableReadImpl]transaction, [CtVariableReadImpl]recordItem);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testWithBodyBytes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.Transaction transaction = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION.toBuilder().setBodyBytes([CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_BODY.toByteString()).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]RecordItem recordItem = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RecordItem([CtInvocationImpl][CtVariableReadImpl]transaction.toByteArray(), [CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_RECORD.toByteArray());
        [CtInvocationImpl]assertRecordItem([CtVariableReadImpl]transaction, [CtVariableReadImpl]recordItem);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void testException([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionBytes, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] recordBytes, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String expectedMessage) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Exception exception = [CtInvocationImpl]assertThrows([CtFieldReadImpl]com.hedera.mirror.importer.exception.ParserException.class, [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtConstructorCallImpl]new [CtTypeReferenceImpl]RecordItem([CtVariableReadImpl]transactionBytes, [CtVariableReadImpl]recordBytes);
        });
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expectedMessage, [CtInvocationImpl][CtVariableReadImpl]exception.getMessage());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertRecordItem([CtParameterImpl][CtTypeReferenceImpl]com.hederahashgraph.api.proto.java.Transaction transaction, [CtParameterImpl][CtTypeReferenceImpl]RecordItem recordItem) [CtBlockImpl]{
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]transaction, [CtInvocationImpl][CtVariableReadImpl]recordItem.getTransaction());
        [CtInvocationImpl]assertEquals([CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_RECORD, [CtInvocationImpl][CtVariableReadImpl]recordItem.getRecord());
        [CtInvocationImpl]assertEquals([CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_BODY, [CtInvocationImpl][CtVariableReadImpl]recordItem.getTransactionBody());
        [CtInvocationImpl]assertArrayEquals([CtInvocationImpl][CtVariableReadImpl]transaction.toByteArray(), [CtInvocationImpl][CtVariableReadImpl]recordItem.getTransactionBytes());
        [CtInvocationImpl]assertArrayEquals([CtInvocationImpl][CtFieldReadImpl]com.hedera.mirror.importer.parser.domain.RecordItemTest.TRANSACTION_RECORD.toByteArray(), [CtInvocationImpl][CtVariableReadImpl]recordItem.getRecordBytes());
    }
}
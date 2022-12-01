[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018, Oath Inc.
Licensed under the Apache License, Version 2.0
See LICENSE file in project root for terms.
 */
[CtPackageDeclarationImpl]package com.yahoo.elide.datastores.jpa.transaction;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import javax.transaction.Status;
[CtImportImpl]import java.util.function.Consumer;
[CtImportImpl]import javax.naming.NamingException;
[CtUnresolvedImport]import javax.transaction.UserTransaction;
[CtUnresolvedImport]import com.yahoo.elide.core.exceptions.TransactionException;
[CtImportImpl]import javax.naming.InitialContext;
[CtUnresolvedImport]import javax.persistence.EntityManager;
[CtUnresolvedImport]import com.yahoo.elide.core.RequestScope;
[CtClassImpl][CtJavaDocImpl]/**
 * JTA transaction implementation.
 */
[CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
public class JtaTransaction extends [CtTypeReferenceImpl]com.yahoo.elide.datastores.jpa.transaction.AbstractJpaTransaction {
    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.transaction.UserTransaction transaction;

    [CtConstructorImpl]public JtaTransaction([CtParameterImpl][CtTypeReferenceImpl]javax.persistence.EntityManager entityManager, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]javax.persistence.EntityManager> jpaTransactionCancel) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]entityManager, [CtInvocationImpl]com.yahoo.elide.datastores.jpa.transaction.JtaTransaction.lookupUserTransaction(), [CtVariableReadImpl]jpaTransactionCancel);
    }

    [CtConstructorImpl]public JtaTransaction([CtParameterImpl][CtTypeReferenceImpl]javax.persistence.EntityManager entityManager, [CtParameterImpl][CtTypeReferenceImpl]javax.transaction.UserTransaction transaction, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]javax.persistence.EntityManager> txCancel) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]entityManager, [CtVariableReadImpl]txCancel);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transaction = [CtVariableReadImpl]transaction;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]javax.transaction.UserTransaction lookupUserTransaction() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]javax.transaction.UserTransaction) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.naming.InitialContext().lookup([CtLiteralImpl]"java:comp/UserTransaction")));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.naming.NamingException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Fail lookup UserTransaction from InitialContext", [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.yahoo.elide.core.exceptions.TransactionException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void begin() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]transaction.begin();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Fail UserTransaction#begin()", [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.yahoo.elide.core.exceptions.TransactionException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void commit([CtParameterImpl][CtTypeReferenceImpl]com.yahoo.elide.core.RequestScope scope) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.commit([CtVariableReadImpl]scope);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]transaction.commit();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Fail UserTransaction#commit()", [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.yahoo.elide.core.exceptions.TransactionException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void rollback() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.rollback();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]transaction.rollback();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Fail UserTransaction#rollback()", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isOpen() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]transaction.getStatus() == [CtFieldReadImpl]javax.transaction.Status.STATUS_ACTIVE;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }
}
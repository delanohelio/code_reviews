[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright 2020 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
[CtPackageDeclarationImpl]package org.opensmartgridplatform.secretmanagement.application.domain;
[CtUnresolvedImport]import javax.persistence.EnumType;
[CtUnresolvedImport]import javax.persistence.Entity;
[CtUnresolvedImport]import javax.persistence.GenerationType;
[CtUnresolvedImport]import javax.persistence.ManyToOne;
[CtUnresolvedImport]import lombok.Setter;
[CtUnresolvedImport]import javax.persistence.Table;
[CtUnresolvedImport]import javax.persistence.GeneratedValue;
[CtUnresolvedImport]import lombok.Getter;
[CtUnresolvedImport]import javax.persistence.SequenceGenerator;
[CtUnresolvedImport]import javax.persistence.Enumerated;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import javax.persistence.Id;
[CtClassImpl][CtJavaDocImpl]/**
 * Encrypted secret, which should not be mutated, because modifications should result in a new version (record in the
 * DB). Historic entries remain in the DB table and the current secret should be determined via a query on creationTime.
 */
[CtAnnotationImpl]@javax.persistence.Entity
[CtAnnotationImpl]@javax.persistence.Table(name = [CtLiteralImpl]"encrypted_secret")
[CtAnnotationImpl]@lombok.Getter
[CtAnnotationImpl]@lombok.Setter
public class DbEncryptedSecret {
    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Id
    [CtAnnotationImpl]@javax.persistence.SequenceGenerator(name = [CtLiteralImpl]"encrypted_secret_seq_gen", sequenceName = [CtLiteralImpl]"encrypted_secret_id_seq", allocationSize = [CtLiteralImpl]1)
    [CtAnnotationImpl]@javax.persistence.GeneratedValue(strategy = [CtFieldReadImpl]javax.persistence.GenerationType.SEQUENCE, generator = [CtLiteralImpl]"encrypted_secret_seq_gen")
    [CtTypeReferenceImpl]java.lang.Long id;

    [CtFieldImpl][CtTypeReferenceImpl]java.util.Date creationTime;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String deviceIdentification;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Enumerated([CtFieldReadImpl]javax.persistence.EnumType.STRING)
    [CtTypeReferenceImpl]org.opensmartgridplatform.secretmanagement.application.domain.SecretType secretType;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String encodedSecret;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.ManyToOne
    [CtTypeReferenceImpl]org.opensmartgridplatform.secretmanagement.application.domain.DbEncryptionKeyReference encryptionKeyReference;
}
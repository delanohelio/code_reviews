[CompilationUnitImpl][CtCommentImpl]/* (C) Copyright 2020 Nuxeo (http://nuxeo.com/) and others.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    Salem Aouana
 */
[CtPackageDeclarationImpl]package org.nuxeo.ecm.core;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.nuxeo.ecm.core.test.ColdStorageFeature;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.TransactionalFeature;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.Features;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.DocumentModel;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.FeaturesRunner;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.Blobs;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.NuxeoException;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import static org.junit.Assert.fail;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.CoreSession;
[CtUnresolvedImport]import org.nuxeo.ecm.core.blob.ColdStorageHelper;
[CtUnresolvedImport]import org.nuxeo.ecm.core.schema.FacetNames;
[CtImportImpl]import java.io.Serializable;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.Blob;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @since 11.1
 */
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.nuxeo.runtime.test.runner.FeaturesRunner.class)
[CtAnnotationImpl]@org.nuxeo.runtime.test.runner.Features([CtFieldReadImpl]org.nuxeo.ecm.core.test.ColdStorageFeature.class)
public class TestColdStorage {
    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String FILE_CONTENT = [CtLiteralImpl]"foo";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]int NUMBER_OF_DAYS_OF_AVAILABILITY = [CtLiteralImpl]5;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    protected [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.CoreSession session;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    protected [CtTypeReferenceImpl]org.nuxeo.runtime.test.runner.TransactionalFeature transactionalFeature;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldMoveBlobDocumentToColdStorage() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]// move the blob to cold storage
        [CtVariableWriteImpl]documentModel = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
        [CtInvocationImpl][CtFieldReadImpl]transactionalFeature.nextTransaction();
        [CtInvocationImpl][CtVariableReadImpl]documentModel.refresh();
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]documentModel.hasFacet([CtTypeAccessImpl]FacetNames.COLD_STORAGE));
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]documentModel.getPropertyValue([CtTypeAccessImpl]ColdStorageHelper.FILE_CONTENT_PROPERTY));
        [CtInvocationImpl][CtCommentImpl]// check if the `coldstorage:coldContent` contains the original file content
        checkBlobContent([CtVariableReadImpl]documentModel, [CtTypeAccessImpl]ColdStorageHelper.COLD_STORAGE_CONTENT_PROPERTY, [CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.FILE_CONTENT);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWhenMovingDocumentBlobAlreadyInColdStorage() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]// move for the first time
        [CtVariableWriteImpl]documentModel = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
        [CtTryImpl][CtCommentImpl]// try to make another move
        try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should fail because the content is already in cold storage");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.NuxeoException ne) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.core.SC_CONFLICT, [CtInvocationImpl][CtVariableReadImpl]ne.getStatusCode());
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The main content for document: %s is already in cold storage.", [CtVariableReadImpl]documentModel), [CtInvocationImpl][CtVariableReadImpl]ne.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWhenMovingToColdStorageDocumentWithoutContent() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]false);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should fail because there is no main content associated with the document");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.NuxeoException ne) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.core.SC_NOT_FOUND, [CtInvocationImpl][CtVariableReadImpl]ne.getStatusCode());
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"There is no main content for document: %s.", [CtVariableReadImpl]documentModel), [CtInvocationImpl][CtVariableReadImpl]ne.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldRequestRetrievalDocumentBlobFromColdStorage() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// move the blob to cold storage
        [CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
        [CtAssignmentImpl][CtCommentImpl]// request a retrieval from the cold storage content
        [CtVariableWriteImpl]documentModel = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.requestRetrievalFromColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef(), [CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.NUMBER_OF_DAYS_OF_AVAILABILITY);
        [CtInvocationImpl][CtFieldReadImpl]transactionalFeature.nextTransaction();
        [CtInvocationImpl][CtVariableReadImpl]documentModel.refresh();
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]documentModel.getPropertyValue([CtTypeAccessImpl]ColdStorageHelper.COLD_STORAGE_BEING_RETRIEVED_PROPERTY)));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWhenRequestRetrievalDocumentBlobFromColdStorageBeingRetrieved() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// move the blob to cold storage
        [CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.moveContentToColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef());
        [CtAssignmentImpl][CtCommentImpl]// request a retrieval from the cold storage content
        [CtVariableWriteImpl]documentModel = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.requestRetrievalFromColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef(), [CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.NUMBER_OF_DAYS_OF_AVAILABILITY);
        [CtTryImpl][CtCommentImpl]// try to request a retrieval for a second time
        try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.requestRetrievalFromColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef(), [CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.NUMBER_OF_DAYS_OF_AVAILABILITY);
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should fail because the cold storage content is being retrieved.");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.NuxeoException ne) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.core.SC_FORBIDDEN, [CtInvocationImpl][CtVariableReadImpl]ne.getStatusCode());
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The cold storage content associated with the document: %s is being retrieved.", [CtVariableReadImpl]documentModel), [CtInvocationImpl][CtVariableReadImpl]ne.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWheRequestRetrievalDocumentBlobWithoutColdStorageContent() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl]createDocument([CtLiteralImpl]true);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// try a request retrieval from the cold storage content where the blob is not stored in it
            [CtTypeAccessImpl]org.nuxeo.ecm.core.blob.ColdStorageHelper.requestRetrievalFromColdStorage([CtFieldReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]documentModel.getRef(), [CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.NUMBER_OF_DAYS_OF_AVAILABILITY);
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should fail because there no cold storage content associated to this document.");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.NuxeoException ne) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.core.SC_NOT_FOUND, [CtInvocationImpl][CtVariableReadImpl]ne.getStatusCode());
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"No cold storage content defined for document: %s.", [CtVariableReadImpl]documentModel), [CtInvocationImpl][CtVariableReadImpl]ne.getMessage());
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel createDocument([CtParameterImpl][CtTypeReferenceImpl]boolean addBlobContent) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel = [CtInvocationImpl][CtFieldReadImpl]session.createDocumentModel([CtLiteralImpl]"/", [CtLiteralImpl]"anyFile", [CtLiteralImpl]"File");
        [CtIfImpl]if ([CtVariableReadImpl]addBlobContent) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]documentModel.setPropertyValue([CtLiteralImpl]"file:content", [CtInvocationImpl](([CtTypeReferenceImpl]java.io.Serializable) ([CtTypeAccessImpl]org.nuxeo.ecm.core.api.Blobs.createBlob([CtFieldReadImpl]org.nuxeo.ecm.core.TestColdStorage.FILE_CONTENT))));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]session.createDocument([CtVariableReadImpl]documentModel);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void checkBlobContent([CtParameterImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel documentModel, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String xpath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String expectedContent) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.Blob content = [CtInvocationImpl](([CtTypeReferenceImpl]org.nuxeo.ecm.core.api.Blob) ([CtVariableReadImpl]documentModel.getPropertyValue([CtVariableReadImpl]xpath)));
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]content);
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]expectedContent, [CtInvocationImpl][CtVariableReadImpl]content.getString());
    }
}
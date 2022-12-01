[CompilationUnitImpl][CtCommentImpl]/* (C) Copyright 2013-2017 Nuxeo (http://nuxeo.com/) and others.

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
    dmetzler
 */
[CtPackageDeclarationImpl]package org.nuxeo.ecm.restapi.test;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.api.DirectoryService;
[CtUnresolvedImport]import org.nuxeo.common.utils.FileUtils;
[CtUnresolvedImport]import org.nuxeo.ecm.automation.client.model.FileBlob;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.nuxeo.directory.test.DirectoryFeature;
[CtUnresolvedImport]import org.nuxeo.ecm.core.io.registry.context.RenderingContext.CtxBuilder;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.io.DirectoryEntryJsonWriter;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonNode;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.TransactionalFeature;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.api.DirectoryEntry;
[CtUnresolvedImport]import org.nuxeo.ecm.core.io.registry.MarshallerHelper;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.DocumentModel;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.FeaturesRunner;
[CtUnresolvedImport]import org.nuxeo.ecm.core.test.annotations.Granularity;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import org.nuxeo.runtime.api.Framework;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.nuxeo.ecm.platform.usermanager.UserManager;
[CtUnresolvedImport]import static org.junit.Assert.fail;
[CtImportImpl]import com.fasterxml.jackson.databind.node.ArrayNode;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import org.nuxeo.jaxrs.test.CloseableClientResponse;
[CtUnresolvedImport]import com.sun.jersey.core.util.MultivaluedMapImpl;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.nuxeo.ecm.automation.client.OperationRequest;
[CtUnresolvedImport]import javax.ws.rs.core.MultivaluedMap;
[CtUnresolvedImport]import org.nuxeo.ecm.automation.client.model.Blob;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.Deploy;
[CtUnresolvedImport]import org.nuxeo.runtime.test.runner.Features;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.io.DirectoryListJsonWriter;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.Session;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.nuxeo.ecm.automation.client.RemoteException;
[CtUnresolvedImport]import org.nuxeo.ecm.directory.io.DirectoryEntryListJsonWriter;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.nuxeo.ecm.core.api.DocumentModelList;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @since 5.7.3
 */
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.nuxeo.runtime.test.runner.FeaturesRunner.class)
[CtAnnotationImpl]@org.nuxeo.runtime.test.runner.Features([CtNewArrayImpl]{ [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.RestServerFeature.class, [CtFieldReadImpl]org.nuxeo.directory.test.DirectoryFeature.class })
[CtAnnotationImpl]@org.nuxeo.ecm.core.test.annotations.RepositoryConfig(init = [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.RestServerInit.class, cleanup = [CtFieldReadImpl]org.nuxeo.ecm.core.test.annotations.Granularity.METHOD)
[CtAnnotationImpl]@org.nuxeo.runtime.test.runner.Deploy([CtLiteralImpl]"org.nuxeo.ecm.platform.restapi.test:test-directory-contrib.xml")
public class DirectoryTest extends [CtTypeReferenceImpl]org.nuxeo.ecm.restapi.test.BaseTest {
    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]org.nuxeo.ecm.directory.api.DirectoryService ds;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]org.nuxeo.runtime.test.runner.TransactionalFeature txFeature;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]org.nuxeo.ecm.directory.Session clientSession;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TESTDIRNAME = [CtLiteralImpl]"testdir";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INT_ID_TEST_DIR_NAME = [CtLiteralImpl]"intIdTestDir";

    [CtFieldImpl][CtTypeReferenceImpl]org.nuxeo.ecm.directory.Session dirSession = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void doBefore() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.doBefore();
        [CtAssignmentImpl][CtFieldWriteImpl]dirSession = [CtInvocationImpl][CtFieldReadImpl]ds.open([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void doAfter() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]dirSession != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dirSession.close();
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void nextTransaction() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]dirSession.close();
        [CtInvocationImpl][CtFieldReadImpl]txFeature.nextTransaction();
        [CtAssignmentImpl][CtFieldWriteImpl]dirSession = [CtInvocationImpl][CtFieldReadImpl]ds.open([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanQueryDirectoryEntry() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a directoryEntry
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test1");
        [CtLocalVariableImpl][CtCommentImpl]// When I call the Rest endpoint
        [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/test1");
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryEntryJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"directoryName").asText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"properties").get([CtLiteralImpl]"label").asText());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 8.4
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanQueryDirectoryNames() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// When I call the Rest endpoint
        [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory");
        [CtInvocationImpl][CtCommentImpl]// It should not return system directories
        Assert.assertEquals([CtTypeAccessImpl]DirectoryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").size());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"continent", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").get([CtLiteralImpl]0).get([CtLiteralImpl]"name").textValue());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"country", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").get([CtLiteralImpl]1).get([CtLiteralImpl]"name").textValue());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"nature", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").get([CtLiteralImpl]2).get([CtLiteralImpl]"name").textValue());
        [CtLocalVariableImpl][CtCommentImpl]// It should not retrieve directory with unknown type
        [CtTypeReferenceImpl]javax.ws.rs.core.MultivaluedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> queryParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sun.jersey.core.util.MultivaluedMapImpl();
        [CtInvocationImpl][CtVariableReadImpl]queryParams.put([CtLiteralImpl]"types", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"notExistingType"));
        [CtAssignmentImpl][CtVariableWriteImpl]node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory", [CtVariableReadImpl]queryParams);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").size());
        [CtAssignmentImpl][CtCommentImpl]// It should not retrieve system directories
        [CtVariableWriteImpl]queryParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sun.jersey.core.util.MultivaluedMapImpl();
        [CtInvocationImpl][CtVariableReadImpl]queryParams.put([CtLiteralImpl]"types", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtTypeAccessImpl]DirectoryService.SYSTEM_DIRECTORY_TYPE));
        [CtAssignmentImpl][CtVariableWriteImpl]node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory", [CtVariableReadImpl]queryParams);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").size());
        [CtAssignmentImpl][CtCommentImpl]// It should be able to retrieve a single type
        [CtVariableWriteImpl]queryParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sun.jersey.core.util.MultivaluedMapImpl();
        [CtInvocationImpl][CtVariableReadImpl]queryParams.put([CtLiteralImpl]"types", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"toto"));
        [CtAssignmentImpl][CtVariableWriteImpl]node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory", [CtVariableReadImpl]queryParams);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").size());
        [CtAssignmentImpl][CtCommentImpl]// It should be able to retrieve many types
        [CtVariableWriteImpl]queryParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sun.jersey.core.util.MultivaluedMapImpl();
        [CtInvocationImpl][CtVariableReadImpl]queryParams.put([CtLiteralImpl]"types", [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"toto", [CtLiteralImpl]"pouet"));
        [CtAssignmentImpl][CtVariableWriteImpl]node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory", [CtVariableReadImpl]queryParams);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entries").size());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @since 8.4
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCannotDeleteDirectoryEntryWithConstraints() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl][CtCommentImpl]// When I try to delete an entry which has contraints
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.DELETE, [CtLiteralImpl]"/directory/continent/europe")) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// It should fail
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_CONFLICT, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtLocalVariableImpl][CtCommentImpl]// When I remove all the contraints
        [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory/country");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode jsonEntries = [CtInvocationImpl](([CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode) ([CtVariableReadImpl]node.get([CtLiteralImpl]"entries")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> it = [CtInvocationImpl][CtVariableReadImpl]jsonEntries.elements();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]it.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode props = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().get([CtLiteralImpl]"properties");
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"europe".equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]props.get([CtLiteralImpl]"parent").textValue())) [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.DELETE, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/country/" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]props.get([CtLiteralImpl]"id").textValue())) [CtBlockImpl]{
                    [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.NO_CONTENT.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
                }
            }
        } 
        [CtTryWithResourceImpl][CtCommentImpl]// I should be able to delete the entry
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.DELETE, [CtLiteralImpl]"/directory/continent/europe")) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.NO_CONTENT.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanQueryDirectoryEntries() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a directory
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModelList entries = [CtInvocationImpl][CtFieldReadImpl]dirSession.query([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
        [CtLocalVariableImpl][CtCommentImpl]// When i do a request on the directory endpoint
        [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME);
        [CtInvocationImpl][CtCommentImpl]// Then i receive the response as json
        Assert.assertEquals([CtTypeAccessImpl]DirectoryEntryListJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode jsonEntries = [CtInvocationImpl](([CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode) ([CtVariableReadImpl]node.get([CtLiteralImpl]"entries")));
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]entries.size(), [CtInvocationImpl][CtVariableReadImpl]jsonEntries.size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanUpdateADirectoryEntry() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a directory modified entry as Json
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test1");
        [CtInvocationImpl][CtVariableReadImpl]docEntry.setPropertyValue([CtLiteralImpl]"vocabulary:label", [CtLiteralImpl]"newlabel");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]docEntry);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an update request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.PUT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId(), [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then the entry is updated
            Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.OK.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

        [CtAssignmentImpl][CtVariableWriteImpl]docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test1");
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"newlabel", [CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"));
        [CtLocalVariableImpl][CtCommentImpl]// update an entry without the `id` field at the root
        [CtTypeReferenceImpl]java.lang.String compatJSONEntry = [CtLiteralImpl]"{\"entity-type\":\"directoryEntry\",\"directoryName\":\"testdir\",\"properties\":{\"id\":\"test1\",\"label\":\"another label\"}}";
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.PUT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId(), [CtVariableReadImpl]compatJSONEntry)) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.OK.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

        [CtAssignmentImpl][CtVariableWriteImpl]docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtInvocationImpl][CtVariableReadImpl]docEntry.getId());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"another label", [CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"));
        [CtLocalVariableImpl][CtCommentImpl]// The document should not be updated if the id is missing at the root and in the properties
        [CtTypeReferenceImpl]java.lang.String missingIdEntry = [CtLiteralImpl]"{\"entity-type\":\"directoryEntry\",\"directoryName\":\"testdir\",\"properties\":{\"label\":\"different label\"}}";
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.PUT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId(), [CtVariableReadImpl]missingIdEntry)) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.BAD_REQUEST.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

        [CtAssignmentImpl][CtVariableWriteImpl]docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtInvocationImpl][CtVariableReadImpl]docEntry.getId());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"another label", [CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanUpdateADirectoryEntryWithAnIntId() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.directory.Session dirSession = [CtInvocationImpl][CtFieldReadImpl]ds.open([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.INT_ID_TEST_DIR_NAME)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtVariableReadImpl]dirSession.createEntry([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonMap([CtLiteralImpl]"label", [CtLiteralImpl]"test label"));
            [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

            [CtInvocationImpl][CtVariableReadImpl]docEntry.setPropertyValue([CtLiteralImpl]"intIdSchema:label", [CtLiteralImpl]"new label");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.INT_ID_TEST_DIR_NAME, [CtVariableReadImpl]docEntry);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.PUT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.INT_ID_TEST_DIR_NAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId(), [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
                [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.OK.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
            }
            [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

            [CtAssignmentImpl][CtVariableWriteImpl]docEntry = [CtInvocationImpl][CtVariableReadImpl]dirSession.getEntry([CtInvocationImpl][CtVariableReadImpl]docEntry.getId());
            [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"new label", [CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"intIdSchema:label"));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanCreateADirectoryEntry() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a directory modified entry as Json
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test1");
        [CtInvocationImpl][CtVariableReadImpl]docEntry.setPropertyValue([CtLiteralImpl]"vocabulary:id", [CtLiteralImpl]"newtest");
        [CtInvocationImpl][CtVariableReadImpl]docEntry.setPropertyValue([CtLiteralImpl]"vocabulary:label", [CtLiteralImpl]"newlabel");
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"newtest"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]docEntry);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an update request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.POST, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then the entry is updated
            Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.CREATED.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtInvocationImpl]nextTransaction();[CtCommentImpl]// see committed changes

        [CtAssignmentImpl][CtVariableWriteImpl]docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"newtest");
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"newlabel", [CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanDeleteADirectoryEntry() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given an existent entry
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test2");
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]docEntry);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do a DELETE request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.DELETE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then the entry is deleted
            nextTransaction();[CtCommentImpl]// see committed changes

            [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test2"));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itSends404OnnotExistentDirectory() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.GET, [CtLiteralImpl]"/directory/nonexistendirectory")) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.NOT_FOUND.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itSends404OnnotExistentDirectoryEntry() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/nonexistententry")) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]Response.Status.NOT_FOUND.getStatusCode(), [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void genericUserCanNotEditDirectories() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// As a generic user
        [CtFieldWriteImpl]service = [CtInvocationImpl]getServiceFor([CtLiteralImpl]"user1", [CtLiteralImpl]"user1");
        [CtLocalVariableImpl][CtCommentImpl]// Given a directory entry as Json
        [CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"test1");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]docEntry);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an update request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.PUT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId(), [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then it is forbidden
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_FORBIDDEN, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an create request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.POST, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then it is forbidden
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_FORBIDDEN, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an delete request on the entry endpoint
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.DELETE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]docEntry.getId())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then it is forbidden
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_FORBIDDEN, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void userDirectoryAreNotEditable() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a user directory entry
        [CtTypeReferenceImpl]org.nuxeo.ecm.platform.usermanager.UserManager um = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.runtime.api.Framework.getService([CtFieldReadImpl]org.nuxeo.ecm.platform.usermanager.UserManager.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel model = [CtInvocationImpl][CtVariableReadImpl]um.getUserModel([CtLiteralImpl]"user1");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userDirectoryName = [CtInvocationImpl][CtVariableReadImpl]um.getUserDirectoryName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]userDirectoryName, [CtVariableReadImpl]model);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an update request on it
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.POST, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtVariableReadImpl]userDirectoryName, [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then it is unauthorized
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_BAD_REQUEST, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itShouldNotWritePasswordFieldInResponse() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a user directory entry
        [CtTypeReferenceImpl]org.nuxeo.ecm.platform.usermanager.UserManager um = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.runtime.api.Framework.getService([CtFieldReadImpl]org.nuxeo.ecm.platform.usermanager.UserManager.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userDirectoryName = [CtInvocationImpl][CtVariableReadImpl]um.getUserDirectoryName();
        [CtLocalVariableImpl][CtCommentImpl]// When i do an update request on it
        [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtVariableReadImpl]userDirectoryName) + [CtLiteralImpl]"/user1");
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"properties").get([CtLiteralImpl]"password").asText());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void groupDirectoryAreNotEditable() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Given a user directory entry
        [CtTypeReferenceImpl]org.nuxeo.ecm.platform.usermanager.UserManager um = [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.runtime.api.Framework.getService([CtFieldReadImpl]org.nuxeo.ecm.platform.usermanager.UserManager.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel model = [CtInvocationImpl][CtVariableReadImpl]um.getGroupModel([CtLiteralImpl]"group1");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String groupDirectoryName = [CtInvocationImpl][CtVariableReadImpl]um.getGroupDirectoryName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]groupDirectoryName, [CtVariableReadImpl]model);
        [CtTryWithResourceImpl][CtCommentImpl]// When i do an create request on it
        try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.POST, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtVariableReadImpl]groupDirectoryName, [CtVariableReadImpl]jsonEntry)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Then it is unauthorized
            Assert.assertEquals([CtTypeAccessImpl]org.nuxeo.ecm.restapi.test.SC_BAD_REQUEST, [CtInvocationImpl][CtVariableReadImpl]response.getStatus());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itCanQueryDirectoryEntryWithIdContainingSlashes() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"id/with/slash");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node = [CtInvocationImpl]getResponseAsJson([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME) + [CtLiteralImpl]"/id/with/slash");
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]DirectoryEntryJsonWriter.ENTITY_TYPE, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"entity-type").asText());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"directoryName").asText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]docEntry.getPropertyValue([CtLiteralImpl]"vocabulary:label"), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]node.get([CtLiteralImpl]"properties").get([CtLiteralImpl]"label").asText());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void itReturnsProperPagination() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel docEntry = [CtInvocationImpl][CtFieldReadImpl]dirSession.getEntry([CtLiteralImpl]"foo");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String jsonEntry = [CtInvocationImpl]getDirectoryEntryAsJson([CtVariableReadImpl]docEntry);
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]jsonEntry);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.ws.rs.core.MultivaluedMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> queryParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sun.jersey.core.util.MultivaluedMapImpl();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxResults = [CtLiteralImpl]5;
        [CtInvocationImpl][CtVariableReadImpl]queryParams.putSingle([CtLiteralImpl]"pageSize", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]maxResults));
        [CtInvocationImpl][CtVariableReadImpl]queryParams.putSingle([CtLiteralImpl]"maxResults", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]maxResults));
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.jaxrs.test.CloseableClientResponse response = [CtInvocationImpl]getResponse([CtTypeAccessImpl]RequestType.GET, [CtBinaryOperatorImpl][CtLiteralImpl]"/directory/" + [CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtVariableReadImpl]queryParams)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String json = [CtInvocationImpl][CtVariableReadImpl]response.getEntity([CtFieldReadImpl]java.lang.String.class);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode jsonNode = [CtInvocationImpl][CtFieldReadImpl]mapper.readTree([CtVariableReadImpl]json);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode entriesNode = [CtInvocationImpl][CtVariableReadImpl]jsonNode.get([CtLiteralImpl]"entries");
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]entriesNode.isArray());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode entriesArrayNode = [CtVariableReadImpl](([CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ArrayNode) (entriesNode));
            [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]maxResults, [CtInvocationImpl][CtVariableReadImpl]entriesArrayNode.size());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDirectoryEntryAsJson([CtParameterImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel dirEntry) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDirectoryEntryAsJson([CtFieldReadImpl]org.nuxeo.ecm.restapi.test.DirectoryTest.TESTDIRNAME, [CtVariableReadImpl]dirEntry);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDirectoryEntryAsJson([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dirName, [CtParameterImpl][CtTypeReferenceImpl]org.nuxeo.ecm.core.api.DocumentModel dirEntry) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.io.registry.MarshallerHelper.objectToJson([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.nuxeo.ecm.directory.api.DirectoryEntry([CtVariableReadImpl]dirName, [CtVariableReadImpl]dirEntry), [CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.ecm.core.io.registry.context.RenderingContext.CtxBuilder.get());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testLoadDirectoryFromCsv() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.automation.client.model.Blob blob = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.nuxeo.ecm.automation.client.model.FileBlob([CtInvocationImpl][CtTypeAccessImpl]org.nuxeo.common.utils.FileUtils.getResourceFileFromContext([CtLiteralImpl]"directories/country.csv"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.automation.client.OperationRequest loadCsv = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientSession.newRequest([CtLiteralImpl]"Directory.LoadFromCSV").set([CtLiteralImpl]"directoryName", [CtLiteralImpl]"country").set([CtLiteralImpl]"dataLoadingPolicy", [CtLiteralImpl]"error_on_duplicate").setInput([CtVariableReadImpl]blob);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]loadCsv.execute();
            [CtInvocationImpl]Assert.fail();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.nuxeo.ecm.automation.client.RemoteException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"already exists in directory"));
        }
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright 2008 The University of North Carolina at Chapel Hill
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package edu.unc.lib.dl.persist.services.edit;
[CtUnresolvedImport]import edu.unc.lib.dl.test.SelfReturningAnswer;
[CtUnresolvedImport]import edu.unc.lib.dl.acl.util.AgentPrincipals;
[CtUnresolvedImport]import static org.mockito.MockitoAnnotations.initMocks;
[CtUnresolvedImport]import org.mockito.Mock;
[CtUnresolvedImport]import edu.unc.lib.dl.event.PremisLogger;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.RepositoryObjectLoader;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import edu.unc.lib.dl.acl.service.AccessControlService;
[CtUnresolvedImport]import org.mockito.Captor;
[CtUnresolvedImport]import edu.unc.lib.dl.fedora.PID;
[CtUnresolvedImport]import edu.unc.lib.dl.acl.exception.AccessRestrictionException;
[CtUnresolvedImport]import static org.mockito.Matchers.anyString;
[CtUnresolvedImport]import static org.mockito.Mockito.doAnswer;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Model;
[CtUnresolvedImport]import static org.mockito.Matchers.eq;
[CtUnresolvedImport]import org.mockito.stubbing.Answer;
[CtUnresolvedImport]import edu.unc.lib.dl.event.PremisEventBuilder;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.WorkObject;
[CtUnresolvedImport]import static org.mockito.Mockito.doThrow;
[CtUnresolvedImport]import org.mockito.invocation.InvocationOnMock;
[CtUnresolvedImport]import org.mockito.ArgumentCaptor;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Resource;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.FedoraTransaction;
[CtUnresolvedImport]import edu.unc.lib.dl.rdf.Premis;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.PIDs;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import edu.unc.lib.dl.acl.util.Permission;
[CtUnresolvedImport]import edu.unc.lib.dl.rdf.Ebucore;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.FileObject;
[CtUnresolvedImport]import edu.unc.lib.dl.services.OperationsMessageSender;
[CtUnresolvedImport]import static org.mockito.Mockito.verify;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.RepositoryObjectFactory;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.TransactionCancelledException;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static org.mockito.Matchers.any;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.TransactionManager;
[CtUnresolvedImport]import edu.unc.lib.dl.acl.util.AccessGroupSet;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import static org.mockito.Mockito.mock;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author harring
 */
public class EditFilenameServiceTest {
    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.acl.service.AccessControlService aclService;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectFactory repoObjFactory;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectLoader repoObjLoader;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.TransactionManager txManager;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.services.OperationsMessageSender messageSender;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.FedoraTransaction tx;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.FileObject repoObj;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.WorkObject workObj;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.apache.jena.rdf.model.Model model;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.apache.jena.rdf.model.Resource resc;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.acl.util.AgentPrincipals agent;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.acl.util.AccessGroupSet groups;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]edu.unc.lib.dl.event.PremisLogger premisLogger;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Captor
    private [CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]java.lang.String> labelCaptor;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Captor
    private [CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.unc.lib.dl.fedora.PID>> pidCaptor;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.event.PremisEventBuilder eventBuilder;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.fedora.PID pid;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.net.URI uri;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.persist.services.edit.EditFilenameService service;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void init() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]MockitoAnnotations.initMocks([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]pid = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.fcrepo4.PIDs.get([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString());
        [CtAssignmentImpl][CtFieldWriteImpl]uri = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtLiteralImpl]"path/to/obj");
        [CtAssignmentImpl][CtFieldWriteImpl]service = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.unc.lib.dl.persist.services.edit.EditFilenameService();
        [CtInvocationImpl][CtFieldReadImpl]service.setAclService([CtFieldReadImpl]aclService);
        [CtInvocationImpl][CtFieldReadImpl]service.setRepositoryObjectFactory([CtFieldReadImpl]repoObjFactory);
        [CtInvocationImpl][CtFieldReadImpl]service.setRepositoryObjectLoader([CtFieldReadImpl]repoObjLoader);
        [CtInvocationImpl][CtFieldReadImpl]service.setTransactionManager([CtFieldReadImpl]txManager);
        [CtInvocationImpl][CtFieldReadImpl]service.setOperationsMessageSender([CtFieldReadImpl]messageSender);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repoObjLoader.getRepositoryObject([CtInvocationImpl]Matchers.any([CtFieldReadImpl]edu.unc.lib.dl.fedora.PID.class))).thenReturn([CtFieldReadImpl]repoObj);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repoObj.getModel()).thenReturn([CtFieldReadImpl]model);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]model.getResource([CtInvocationImpl]Matchers.anyString())).thenReturn([CtFieldReadImpl]resc);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repoObj.getUri()).thenReturn([CtFieldReadImpl]uri);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]agent.getPrincipals()).thenReturn([CtFieldReadImpl]groups);
        [CtAssignmentImpl][CtFieldWriteImpl]eventBuilder = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]edu.unc.lib.dl.event.PremisEventBuilder.class, [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.unc.lib.dl.test.SelfReturningAnswer());
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repoObj.getPremisLog()).thenReturn([CtFieldReadImpl]premisLogger);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]premisLogger.buildEvent([CtInvocationImpl]Matchers.eq([CtTypeAccessImpl]Premis.FilenameChange))).thenReturn([CtFieldReadImpl]eventBuilder);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]agent.getUsernameUri()).thenReturn([CtLiteralImpl]"agentname");
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]eventBuilder.write()).thenReturn([CtFieldReadImpl]resc);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]txManager.startTransaction()).thenReturn([CtFieldReadImpl]tx);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doAnswer([CtNewClassImpl]new [CtTypeReferenceImpl]org.mockito.stubbing.Answer<[CtTypeReferenceImpl]java.lang.Object>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Object answer([CtParameterImpl][CtTypeReferenceImpl]org.mockito.invocation.InvocationOnMock invocation) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.TransactionCancelledException([CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]invocation.getArgumentAt([CtLiteralImpl]0, [CtFieldReadImpl]java.lang.Throwable.class));
            }
        }).when([CtFieldReadImpl]tx).cancel([CtInvocationImpl]Matchers.any([CtFieldReadImpl]java.lang.Throwable.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void editFilenameTest() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String label = [CtLiteralImpl]"a brand-new title!";
        [CtInvocationImpl][CtFieldReadImpl]service.editLabel([CtFieldReadImpl]agent, [CtFieldReadImpl]pid, [CtVariableReadImpl]label);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repoObjFactory).createExclusiveRelationship([CtInvocationImpl]Matchers.eq([CtFieldReadImpl]repoObj), [CtInvocationImpl]Matchers.eq([CtTypeAccessImpl]Ebucore.filename), [CtInvocationImpl]Matchers.any([CtFieldReadImpl]org.apache.jena.rdf.model.Resource.class));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]premisLogger).buildEvent([CtInvocationImpl]Matchers.eq([CtTypeAccessImpl]Premis.FilenameChange));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]eventBuilder).addEventDetail([CtInvocationImpl][CtFieldReadImpl]labelCaptor.capture());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtFieldReadImpl]labelCaptor.getValue(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Object renamed from " + [CtLiteralImpl]"no ebucore:filename") + [CtLiteralImpl]" to ") + [CtVariableReadImpl]label);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]eventBuilder).writeAndClose();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]messageSender).sendUpdateDescriptionOperation([CtInvocationImpl]Matchers.anyString(), [CtInvocationImpl][CtFieldReadImpl]pidCaptor.capture());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]pid, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]pidCaptor.getValue().get([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void editFilenamelNonFileObjTest() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repoObjLoader.getRepositoryObject([CtInvocationImpl]Matchers.any([CtFieldReadImpl]edu.unc.lib.dl.fedora.PID.class))).thenReturn([CtFieldReadImpl]workObj);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doThrow([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException()).when([CtFieldReadImpl]aclService).assertHasAccess([CtInvocationImpl]Matchers.anyString(), [CtInvocationImpl]Matchers.eq([CtFieldReadImpl]pid), [CtInvocationImpl]Matchers.any([CtFieldReadImpl]edu.unc.lib.dl.acl.util.AccessGroupSet.class), [CtInvocationImpl]Matchers.eq([CtTypeAccessImpl]Permission.editDescription));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]service.editLabel([CtFieldReadImpl]agent, [CtFieldReadImpl]pid, [CtLiteralImpl]"label");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getCause().getClass(), [CtFieldReadImpl]java.lang.IllegalArgumentException.class);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]edu.unc.lib.dl.fcrepo4.TransactionCancelledException.class)
    public [CtTypeReferenceImpl]void insufficientAccessTest() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doThrow([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.unc.lib.dl.acl.exception.AccessRestrictionException()).when([CtFieldReadImpl]aclService).assertHasAccess([CtInvocationImpl]Matchers.anyString(), [CtInvocationImpl]Matchers.eq([CtFieldReadImpl]pid), [CtInvocationImpl]Matchers.any([CtFieldReadImpl]edu.unc.lib.dl.acl.util.AccessGroupSet.class), [CtInvocationImpl]Matchers.eq([CtTypeAccessImpl]Permission.editDescription));
        [CtInvocationImpl][CtFieldReadImpl]service.editLabel([CtFieldReadImpl]agent, [CtFieldReadImpl]pid, [CtLiteralImpl]"label");
    }
}
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
[CtUnresolvedImport]import static edu.unc.lib.dl.util.DateTimeUtil.formatDateToUTC;
[CtUnresolvedImport]import static edu.unc.lib.dl.util.DateTimeUtil.parseUTCToDate;
[CtUnresolvedImport]import edu.unc.lib.dl.event.PremisLogger;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.RepositoryObjectLoader;
[CtUnresolvedImport]import edu.unc.lib.dl.util.SoftwareAgentConstants.SoftwareAgent;
[CtUnresolvedImport]import org.springframework.scheduling.annotation.Scheduled;
[CtUnresolvedImport]import edu.unc.lib.dl.fedora.PID;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Model;
[CtUnresolvedImport]import org.apache.jena.query.QueryExecution;
[CtUnresolvedImport]import org.apache.jena.query.QuerySolution;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Resource;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.FedoraTransaction;
[CtUnresolvedImport]import edu.unc.lib.dl.rdf.Premis;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.PIDs;
[CtUnresolvedImport]import org.springframework.scheduling.annotation.EnableScheduling;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.RepositoryObject;
[CtUnresolvedImport]import edu.unc.lib.dl.util.JMSMessageUtil;
[CtUnresolvedImport]import edu.unc.lib.dl.services.OperationsMessageSender;
[CtUnresolvedImport]import io.dropwizard.metrics5.Timer;
[CtUnresolvedImport]import edu.unc.lib.dl.sparql.SparqlQueryService;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.RepositoryObjectFactory;
[CtUnresolvedImport]import edu.unc.lib.dl.metrics.TimerFactory;
[CtImportImpl]import java.util.Date;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import edu.unc.lib.dl.fcrepo4.TransactionManager;
[CtImportImpl]import java.text.DateFormat;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.jena.rdf.model.ModelFactory;
[CtUnresolvedImport]import static edu.unc.lib.dl.rdf.CdrAcl.embargoUntil;
[CtUnresolvedImport]import org.apache.jena.query.ResultSet;
[CtClassImpl][CtJavaDocImpl]/**
 * Service that manages embargo expiration
 *
 * @author smithjp
 */
[CtAnnotationImpl]@org.springframework.scheduling.annotation.EnableScheduling
public class ExpireEmbargoService {
    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectFactory repoObjFactory;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectLoader repoObjLoader;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.services.OperationsMessageSender operationsMessageSender;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.TransactionManager txManager;

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.sparql.SparqlQueryService sparqlQueryService;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.dropwizard.metrics5.Timer timer = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.metrics.TimerFactory.createTimerForClass([CtFieldReadImpl]edu.unc.lib.dl.persist.services.edit.EditTitleService.class);

    [CtConstructorImpl]public ExpireEmbargoService() [CtBlockImpl]{
    }

    [CtMethodImpl][CtCommentImpl]// run service every day 1 minute after midnight
    [CtAnnotationImpl]@org.springframework.scheduling.annotation.Scheduled(cron = [CtLiteralImpl]"0 1 0 * * *")
    public [CtTypeReferenceImpl]void expireEmbargoes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.FedoraTransaction tx = [CtInvocationImpl][CtFieldReadImpl]txManager.startTransaction();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.dropwizard.metrics5.Timer.Context context = [CtInvocationImpl][CtFieldReadImpl]edu.unc.lib.dl.persist.services.edit.ExpireEmbargoService.timer.time()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// get list of expired embargoes
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> resourceList = [CtInvocationImpl]getEmbargoInfo();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]edu.unc.lib.dl.fedora.PID> pids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForImpl][CtCommentImpl]// remove all expired embargoes
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]resourceList.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rescUri = [CtInvocationImpl][CtVariableReadImpl]resourceList.get([CtVariableReadImpl]i);
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fedora.PID pid = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.fcrepo4.PIDs.get([CtVariableReadImpl]rescUri);
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObject repoObj = [CtInvocationImpl][CtFieldReadImpl]repoObjLoader.getRepositoryObject([CtVariableReadImpl]pid);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Model model = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.jena.rdf.model.ModelFactory.createDefaultModel().add([CtInvocationImpl][CtVariableReadImpl]repoObj.getModel());
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Resource resc = [CtInvocationImpl][CtVariableReadImpl]model.getResource([CtVariableReadImpl]rescUri);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eventText = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean expiredEmbargo = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String embargoDate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resc.getProperty([CtFieldReadImpl]CdrAcl.embargoUntil).getString();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resc.hasProperty([CtFieldReadImpl]CdrAcl.embargoUntil)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]repoObjFactory.deleteProperty([CtVariableReadImpl]repoObj, [CtFieldReadImpl]CdrAcl.embargoUntil);
                    [CtInvocationImpl][CtVariableReadImpl]pids.add([CtVariableReadImpl]pid);
                    [CtAssignmentImpl][CtVariableWriteImpl]expiredEmbargo = [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtVariableReadImpl]expiredEmbargo) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]eventText = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Expired an embargo for " + [CtInvocationImpl][CtVariableReadImpl]pid.toString()) + [CtLiteralImpl]" which ended ") + [CtInvocationImpl]DateTimeUtil.formatDateToUTC([CtInvocationImpl]DateTimeUtil.parseUTCToDate([CtVariableReadImpl]embargoDate));
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]eventText = [CtLiteralImpl]"Failed to expire embargo.";
                }
                [CtInvocationImpl][CtCommentImpl]// Produce the premis event for this embargo
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]repoObj.getPremisLog().buildEvent([CtTypeAccessImpl]Premis.Dissemination).addSoftwareAgent([CtInvocationImpl][CtTypeAccessImpl]SoftwareAgent.embargoExpirationService.getFullname()).addEventDetail([CtVariableReadImpl]eventText).writeAndClose();
            }
            [CtInvocationImpl][CtCommentImpl]// send a message for expired embargoes
            [CtFieldReadImpl]operationsMessageSender.sendOperationMessage([CtInvocationImpl][CtTypeAccessImpl]SoftwareAgent.embargoExpirationService.getFullname(), [CtTypeAccessImpl]JMSMessageUtil.CDRActions.EDIT_ACCESS_CONTROL, [CtVariableReadImpl]pids);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tx.cancelAndIgnore();
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tx.close();
        }
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EMBARGO_QUERY = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + [CtLiteralImpl]"select ?resource ?date\n") + [CtLiteralImpl]"where {\n") + [CtLiteralImpl]"  ?resource <http://cdr.unc.edu/definitions/acl#embargoUntil> ?date .\n") + [CtLiteralImpl]"  FILTER (?date < \"%s\"^^xsd:dateTime)\n") + [CtLiteralImpl]"}";

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getEmbargoInfo() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.DateFormat dateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String today = [CtInvocationImpl][CtVariableReadImpl]dateFormat.format([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String query = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]edu.unc.lib.dl.persist.services.edit.ExpireEmbargoService.EMBARGO_QUERY, [CtVariableReadImpl]today);
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.query.QueryExecution exec = [CtInvocationImpl][CtFieldReadImpl]sparqlQueryService.executeQuery([CtVariableReadImpl]query)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.query.ResultSet resultSet = [CtInvocationImpl][CtVariableReadImpl]exec.execSelect();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> embargoedRescList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForImpl]for (; [CtInvocationImpl][CtVariableReadImpl]resultSet.hasNext();) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.query.QuerySolution soln = [CtInvocationImpl][CtVariableReadImpl]resultSet.nextSolution();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Resource resc = [CtInvocationImpl][CtVariableReadImpl]soln.getResource([CtLiteralImpl]"resource");
                [CtInvocationImpl][CtVariableReadImpl]embargoedRescList.add([CtInvocationImpl][CtVariableReadImpl]resc.getURI());
            }
            [CtReturnImpl]return [CtVariableReadImpl]embargoedRescList;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param sparqlQueryService
     * 		the sparqlQueryService to set
     */
    public [CtTypeReferenceImpl]void setSparqlQueryService([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.sparql.SparqlQueryService sparqlQueryService) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sparqlQueryService = [CtVariableReadImpl]sparqlQueryService;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void writePremisEvents([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObject repoObj, [CtParameterImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Resource embargoEvent) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.unc.lib.dl.event.PremisLogger logger = [CtInvocationImpl][CtVariableReadImpl]repoObj.getPremisLog()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]embargoEvent != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]logger.writeEvents([CtVariableReadImpl]embargoEvent);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param repoObjLoader
     * 		the object loader to set
     */
    public [CtTypeReferenceImpl]void setRepositoryObjectLoader([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectLoader repoObjLoader) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.repoObjLoader = [CtVariableReadImpl]repoObjLoader;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param repoObjFactory
     * 		the factory to set
     */
    public [CtTypeReferenceImpl]void setRepositoryObjectFactory([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.RepositoryObjectFactory repoObjFactory) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.repoObjFactory = [CtVariableReadImpl]repoObjFactory;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param operationsMessageSender
     * 		the operations message sender to set
     */
    public [CtTypeReferenceImpl]void setOperationsMessageSender([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.services.OperationsMessageSender operationsMessageSender) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.operationsMessageSender = [CtVariableReadImpl]operationsMessageSender;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param txManager
     * 		the transaction manager to set
     */
    public [CtTypeReferenceImpl]void setTransactionManager([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.fcrepo4.TransactionManager txManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.txManager = [CtVariableReadImpl]txManager;
    }
}
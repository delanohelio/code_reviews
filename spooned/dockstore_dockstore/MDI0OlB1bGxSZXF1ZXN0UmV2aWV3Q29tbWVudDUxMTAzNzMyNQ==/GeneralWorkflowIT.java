[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018 OICR

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
[CtPackageDeclarationImpl]package io.dockstore.client.cli;
[CtUnresolvedImport]import org.hibernate.context.internal.ManagedSessionContext;
[CtUnresolvedImport]import io.dockstore.common.DescriptorLanguage;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import io.swagger.client.model.Workflow;
[CtUnresolvedImport]import io.dockstore.common.CommonTestUtilities;
[CtUnresolvedImport]import org.hibernate.Session;
[CtUnresolvedImport]import static io.dockstore.webservice.helpers.EntryVersionHelper.CANNOT_MODIFY_FROZEN_VERSIONS_THIS_WAY;
[CtUnresolvedImport]import static org.junit.Assert.assertFalse;
[CtUnresolvedImport]import io.dockstore.common.WorkflowTest;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import io.swagger.client.model.SourceFile;
[CtUnresolvedImport]import static io.dockstore.webservice.core.Version.CANNOT_FREEZE_VERSIONS_WITH_NO_FILES;
[CtUnresolvedImport]import io.dockstore.common.SourceControl;
[CtUnresolvedImport]import static io.dockstore.webservice.resources.WorkflowResource.NO_ZENDO_USER_TOKEN;
[CtUnresolvedImport]import org.junit.contrib.java.lang.system.ExpectedSystemExit;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.eclipse.jetty.http.HttpStatus;
[CtUnresolvedImport]import static io.dockstore.webservice.resources.WorkflowResource.FROZEN_VERSION_REQUIRED;
[CtUnresolvedImport]import static org.junit.Assert.fail;
[CtUnresolvedImport]import io.swagger.client.ApiException;
[CtUnresolvedImport]import org.junit.contrib.java.lang.system.SystemOutRule;
[CtUnresolvedImport]import io.dockstore.webservice.jdbi.FileDAO;
[CtUnresolvedImport]import org.junit.Before;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import io.swagger.client.ApiClient;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import io.swagger.client.api.UsersApi;
[CtUnresolvedImport]import org.hibernate.SessionFactory;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtUnresolvedImport]import io.dockstore.webservice.DockstoreWebserviceApplication;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.junit.experimental.categories.Category;
[CtUnresolvedImport]import io.dockstore.common.SlowTest;
[CtUnresolvedImport]import static org.junit.Assert.assertNotEquals;
[CtUnresolvedImport]import io.swagger.client.api.WorkflowsApi;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import io.dockstore.common.ConfidentialTest;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import io.swagger.client.model.WorkflowVersion;
[CtUnresolvedImport]import org.junit.Assert;
[CtUnresolvedImport]import org.junit.contrib.java.lang.system.SystemErrRule;
[CtClassImpl][CtJavaDocImpl]/**
 * This test suite tests various workflow related processes.
 * Created by aduncan on 05/04/16.
 */
[CtAnnotationImpl]@org.junit.experimental.categories.Category([CtNewArrayImpl]{ [CtFieldReadImpl]io.dockstore.common.ConfidentialTest.class, [CtFieldReadImpl]io.dockstore.common.WorkflowTest.class })
public class GeneralWorkflowIT extends [CtTypeReferenceImpl]io.dockstore.client.cli.BaseIT {
    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]org.junit.contrib.java.lang.system.ExpectedSystemExit systemExit = [CtInvocationImpl][CtTypeAccessImpl]org.junit.contrib.java.lang.system.ExpectedSystemExit.none();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]org.junit.contrib.java.lang.system.SystemOutRule systemOutRule = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.contrib.java.lang.system.SystemOutRule().enableLog().muteForSuccessfulTests();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]org.junit.contrib.java.lang.system.SystemErrRule systemErrRule = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.contrib.java.lang.system.SystemErrRule().enableLog().muteForSuccessfulTests();

    [CtFieldImpl]private [CtTypeReferenceImpl]io.dockstore.webservice.jdbi.FileDAO fileDAO;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.dockstore.webservice.DockstoreWebserviceApplication application = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SUPPORT.getApplication();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hibernate.SessionFactory sessionFactory = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getHibernate().getSessionFactory();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileDAO = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.dockstore.webservice.jdbi.FileDAO([CtVariableReadImpl]sessionFactory);
        [CtLocalVariableImpl][CtCommentImpl]// used to allow us to use fileDAO outside of the web service
        [CtTypeReferenceImpl]org.hibernate.Session session = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getHibernate().getSessionFactory().openSession();
        [CtInvocationImpl][CtTypeAccessImpl]org.hibernate.context.internal.ManagedSessionContext.bind([CtVariableReadImpl]session);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void resetDBBetweenTests() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.common.CommonTestUtilities.cleanStatePrivate2([CtTypeAccessImpl]io.dockstore.client.cli.SUPPORT, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Manually register and publish a workflow with the given path and name
     *
     * @param workflowsApi
     * @param workflowPath
     * @param workflowName
     * @param descriptorType
     * @param sourceControl
     * @param descriptorPath
     * @param toPublish
     * @return Published workflow
     */
    private [CtTypeReferenceImpl]io.swagger.client.model.Workflow manualRegisterAndPublish([CtParameterImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String workflowPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String workflowName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String descriptorType, [CtParameterImpl][CtTypeReferenceImpl]io.dockstore.common.SourceControl sourceControl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String descriptorPath, [CtParameterImpl][CtTypeReferenceImpl]boolean toPublish) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Manually register
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceControl.getFriendlyName().toLowerCase(), [CtVariableReadImpl]workflowPath, [CtVariableReadImpl]descriptorPath, [CtVariableReadImpl]workflowName, [CtVariableReadImpl]descriptorType, [CtLiteralImpl]"/test.json");
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]Workflow.ModeEnum.STUB, [CtInvocationImpl][CtVariableReadImpl]workflow.getMode());
        [CtAssignmentImpl][CtCommentImpl]// Refresh
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]Workflow.ModeEnum.FULL, [CtInvocationImpl][CtVariableReadImpl]workflow.getMode());
        [CtIfImpl][CtCommentImpl]// Publish
        if ([CtVariableReadImpl]toPublish) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]workflow.isIsPublished());
        }
        [CtReturnImpl]return [CtVariableReadImpl]workflow;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that smart refresh correctly refreshes the right versions based on some scenarios for GitHub
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSmartRefreshGitHub() [CtBlockImpl]{
        [CtInvocationImpl]commonSmartRefreshTest([CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testBoth");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that smart refresh correctly refreshes the right versions based on some scenarios for Gitlab
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSmartRefreshGitlab() [CtBlockImpl]{
        [CtInvocationImpl]commonSmartRefreshTest([CtTypeAccessImpl]SourceControl.GITLAB, [CtLiteralImpl]"dockstore.test.user2/dockstore-workflow-example", [CtLiteralImpl]"master");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that smart refresh correctly refreshes the right versions based on some scenarios for BitBucket
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSmartRefreshBitbucket() [CtBlockImpl]{
        [CtInvocationImpl]commonSmartRefreshTest([CtTypeAccessImpl]SourceControl.BITBUCKET, [CtLiteralImpl]"dockstore_testuser2/dockstore-workflow", [CtLiteralImpl]"cwl_import");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void commonSmartRefreshTest([CtParameterImpl][CtTypeReferenceImpl]io.dockstore.common.SourceControl sourceControl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String workflowPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionOfInterest) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String correctDescriptorPath = [CtLiteralImpl]"/Dockstore.cwl";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String incorrectDescriptorPath = [CtLiteralImpl]"/Dockstore2.cwl";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullPath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]sourceControl.toString() + [CtLiteralImpl]"/") + [CtVariableReadImpl]workflowPath;
        [CtInvocationImpl][CtCommentImpl]// Add workflow
        [CtVariableReadImpl]workflowsApi.manualRegister([CtInvocationImpl][CtVariableReadImpl]sourceControl.name(), [CtVariableReadImpl]workflowPath, [CtVariableReadImpl]correctDescriptorPath, [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]DescriptorLanguage.CWL.getShortName(), [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtCommentImpl]// Smart refresh individual that is valid (should add versions that doesn't exist)
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflowByPath([CtVariableReadImpl]fullPath, [CtLiteralImpl]"", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// All versions should be synced
        [CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isSynced()));
        [CtLocalVariableImpl][CtCommentImpl]// When the commit ID is null, a refresh should occur
        [CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion oldVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"update workflowversion set commitid = NULL where name = '" + [CtVariableReadImpl]versionOfInterest) + [CtLiteralImpl]"'");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion updatedVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl]Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]updatedVersion.getCommitID());
        [CtInvocationImpl]Assert.assertNotEquals([CtBinaryOperatorImpl][CtVariableReadImpl]versionOfInterest + [CtLiteralImpl]" version should be updated (different dbupdatetime)", [CtInvocationImpl][CtVariableReadImpl]oldVersion.getDbUpdateDate(), [CtInvocationImpl][CtVariableReadImpl]updatedVersion.getDbUpdateDate());
        [CtAssignmentImpl][CtCommentImpl]// When the commit ID is different, a refresh should occur
        [CtVariableWriteImpl]oldVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"update workflowversion set commitid = 'dj90jd9jd230d3j9' where name = '" + [CtVariableReadImpl]versionOfInterest) + [CtLiteralImpl]"'");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]updatedVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl]Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]updatedVersion.getCommitID());
        [CtInvocationImpl]Assert.assertNotEquals([CtBinaryOperatorImpl][CtVariableReadImpl]versionOfInterest + [CtLiteralImpl]" version should be updated (different dbupdatetime)", [CtInvocationImpl][CtVariableReadImpl]oldVersion.getDbUpdateDate(), [CtInvocationImpl][CtVariableReadImpl]updatedVersion.getDbUpdateDate());
        [CtInvocationImpl][CtCommentImpl]// Updating the workflow should make the version not synced, a refresh should refresh all versions
        [CtVariableReadImpl]workflow.setWorkflowPath([CtVariableReadImpl]incorrectDescriptorPath);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isSynced()));
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// All versions should be synced and updated
        [CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isSynced()));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getWorkflowPath(), [CtVariableReadImpl]incorrectDescriptorPath));
        [CtLocalVariableImpl][CtCommentImpl]// Update the version to have the correct path
        [CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion testBothVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl][CtVariableReadImpl]testBothVersion.setWorkflowPath([CtVariableReadImpl]correctDescriptorPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> versions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]versions.add([CtVariableReadImpl]testBothVersion);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]versions);
        [CtAssignmentImpl][CtCommentImpl]// Refresh should only update the version that is not synced
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"");
        [CtAssignmentImpl][CtVariableWriteImpl]testBothVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Version should not be synced", [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]testBothVersion.isSynced());
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]testBothVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtVariableReadImpl]versionOfInterest)).findFirst().get();
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Version should now be synced", [CtInvocationImpl][CtVariableReadImpl]testBothVersion.isSynced());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Workflow version path should be set", [CtVariableReadImpl]correctDescriptorPath, [CtInvocationImpl][CtVariableReadImpl]testBothVersion.getWorkflowPath());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This test checks that refresh all workflows (with a mix of stub and full) and refresh individual.  It then tries to publish them
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRefreshAndPublish() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtInvocationImpl][CtCommentImpl]// refresh all
        [CtVariableReadImpl]workflowsApi.manualRegister([CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITHUB.name(), [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]DescriptorLanguage.CWL.getShortName(), [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtCommentImpl]// refresh individual that is valid
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflowByPath([CtLiteralImpl]"github.com/DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtLiteralImpl]"Should have a license object even if it's null name", [CtInvocationImpl][CtVariableReadImpl]workflow.getLicenseInformation());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtLiteralImpl]"Should have no license name", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getLicenseInformation().getLicenseName());
        [CtLocalVariableImpl][CtCommentImpl]// check that valid is valid and full
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where ispublished='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 0 published entries, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]0, [CtVariableReadImpl]count);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 2 valid versions, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]2, [CtVariableReadImpl]count2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count3 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where mode='FULL'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 full workflows, there are " + [CtVariableReadImpl]count3, [CtLiteralImpl]1, [CtVariableReadImpl]count3);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count4 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 4 versions, there are " + [CtVariableReadImpl]count4, [CtLiteralImpl]4, [CtVariableReadImpl]count4);
        [CtInvocationImpl][CtCommentImpl]// attempt to publish it
        [CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count5 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where ispublished='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 published entry, there are " + [CtVariableReadImpl]count5, [CtLiteralImpl]1, [CtVariableReadImpl]count5);
        [CtInvocationImpl][CtCommentImpl]// unpublish
        [CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]false));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count6 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where ispublished='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 0 published entries, there are " + [CtVariableReadImpl]count6, [CtLiteralImpl]0, [CtVariableReadImpl]count6);
        [CtAssignmentImpl][CtCommentImpl]// Restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtAssignmentImpl][CtCommentImpl]// Refresh a single version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"master", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should only have one version", [CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have master version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"master")));
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should no longer be a stub workflow", [CtTypeAccessImpl]Workflow.ModeEnum.FULL, [CtInvocationImpl][CtVariableReadImpl]workflow.getMode());
        [CtAssignmentImpl][CtCommentImpl]// Refresh another version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"testCWL", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should now have two versions", [CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have testCWL version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"testCWL")));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"fakeVersion", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should not be able to refresh a version that does not exist");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException ex) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]HttpStatus.BAD_REQUEST_400, [CtInvocationImpl][CtVariableReadImpl]ex.getCode());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This test manually publishing a workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testManualPublish() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests attempting to manually publish a workflow with no valid versions
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testManualPublishInvalid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtTryImpl][CtCommentImpl]// try and publish
        try [CtBlockImpl]{
            [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/dockstore_empty_repo", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"Repository does not meet requirements to publish"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests adding and removing labels from a workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testLabelEditing() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// Set up workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]// add labels
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateLabels([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"test1,test2", [CtLiteralImpl]"");
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getLabels().size());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from entry_label", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 2 labels, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]2, [CtVariableReadImpl]count);
        [CtAssignmentImpl][CtCommentImpl]// remove labels
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateLabels([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"test2,test3", [CtLiteralImpl]"");
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getLabels().size());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from entry_label", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 2 labels, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]2, [CtVariableReadImpl]count2);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests manually publishing a duplicate workflow (should fail)
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testManualPublishDuplicate() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtInvocationImpl][CtCommentImpl]// Manually register workflow
        manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtTryImpl][CtCommentImpl]// Manually register the same workflow
        try [CtBlockImpl]{
            [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"A workflow with the same path and name already exists."));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that a user can update a workflow version
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpdateWorkflowVersion() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// Manually register workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// Update workflow
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] version) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]version.getName(), [CtLiteralImpl]"testCWL")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"testCWL version should exist");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion updateWorkflowVersion = [CtInvocationImpl][CtVariableReadImpl]workflowVersion.get();
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setHidden([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setWorkflowPath([CtLiteralImpl]"/Dockstore2.wdl");
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.add([CtVariableReadImpl]updateWorkflowVersion);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflowVersions);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion wv, version_metadata vm where wv.name = 'testCWL' and vm.hidden = 't' and wv.workflowpath = '/Dockstore2.wdl' and wv.id = vm.id", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 matching workflow version, there is " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that a restub will work on an unpublished, full workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRestub() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// Restub workflow
        [CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 0 workflow versions, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]0, [CtVariableReadImpl]count);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that a restub will not work on an published, full workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRestubError() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// Publish workflow
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        [CtTryImpl][CtCommentImpl]// Restub
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"A workflow must be unpublished to restub"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tests updating workflow descriptor type when a workflow is FULL and when it is a STUB
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testDescriptorTypes() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where descriptortype = 'wdl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 wdl workflow, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]workflow.setDescriptorType([CtTypeAccessImpl]Workflow.DescriptorTypeEnum.CWL);
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"You cannot change the descriptor type of a FULL workflow"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tests updating a workflow tag with invalid workflow descriptor path
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testWorkflowVersionIncorrectPath() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Update workflow version to new path
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] version) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]version.getName(), [CtLiteralImpl]"master")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Master version should exist");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion updateWorkflowVersion = [CtInvocationImpl][CtVariableReadImpl]workflowVersion.get();
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setWorkflowPath([CtLiteralImpl]"/newdescriptor.cwl");
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.add([CtVariableReadImpl]updateWorkflowVersion);
        [CtAssignmentImpl][CtVariableWriteImpl]workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflowVersions);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where name = 'master' and workflowpath = '/newdescriptor.cwl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"the workflow version should now have a new descriptor path", [CtLiteralImpl]1, [CtVariableReadImpl]count);
        [CtAssignmentImpl][CtCommentImpl]// Update workflow version to incorrect path (wrong extension)
        [CtVariableWriteImpl]workflowVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowVersions.stream().filter([CtLambdaImpl]([CtParameterImpl] version) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]version.getName(), [CtLiteralImpl]"master")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Master version should exist");
        }
        [CtAssignmentImpl][CtVariableWriteImpl]updateWorkflowVersion = [CtInvocationImpl][CtVariableReadImpl]workflowVersion.get();
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setWorkflowPath([CtLiteralImpl]"/Dockstore.wdl");
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.clear();
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.add([CtVariableReadImpl]updateWorkflowVersion);
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflowVersions);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"Please ensure that the workflow path uses the file extension cwl"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tests that refreshing with valid imports will work (for WDL)
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRefreshWithImportsWDL() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtInvocationImpl][CtCommentImpl]// refresh all
        [CtVariableReadImpl]workflowsApi.manualRegister([CtInvocationImpl][CtTypeAccessImpl]SourceControl.BITBUCKET.name(), [CtLiteralImpl]"dockstore_testuser2/dockstore-workflow", [CtLiteralImpl]"/dockstore.wdl", [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]DescriptorLanguage.WDL.getShortName(), [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtCommentImpl]// refresh individual that is valid
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflowByPath([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SourceControl.BITBUCKET.toString() + [CtLiteralImpl]"/dockstore_testuser2/dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// Update workflow path
        [CtVariableReadImpl]workflow.setDescriptorType([CtTypeAccessImpl]Workflow.DescriptorTypeEnum.WDL);
        [CtInvocationImpl][CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"/Dockstore.wdl");
        [CtAssignmentImpl][CtCommentImpl]// Update workflow descriptor type
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtAssignmentImpl][CtCommentImpl]// Refresh workflow
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// Publish workflow
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        [CtAssignmentImpl][CtCommentImpl]// Unpublish workflow
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]false));
        [CtAssignmentImpl][CtCommentImpl]// Restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtAssignmentImpl][CtCommentImpl]// Refresh a single version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"master", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should only have one version", [CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have master version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"master")));
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should no longer be a stub workflow", [CtTypeAccessImpl]Workflow.ModeEnum.FULL, [CtInvocationImpl][CtVariableReadImpl]workflow.getMode());
        [CtAssignmentImpl][CtCommentImpl]// Refresh another version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"cwl_import", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should now have two versions", [CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have cwl_import version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"cwl_import")));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"fakeVersion", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should not be able to refresh a version that does not exist");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException ex) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]HttpStatus.BAD_REQUEST_400, [CtInvocationImpl][CtVariableReadImpl]ex.getCode());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpdateWorkflowPath() throws [CtTypeReferenceImpl]io.swagger.client.ApiException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Set up webservice
        [CtTypeReferenceImpl]io.swagger.client.ApiClient webClient = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.WorkflowIT.getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]webClient);
        [CtInvocationImpl][CtVariableReadImpl]usersApi.getUser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow githubWorkflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtLiteralImpl]"github", [CtLiteralImpl]"DockstoreTestUser2/test_lastmodified", [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]"test-update-workflow", [CtLiteralImpl]"cwl", [CtLiteralImpl]"/test.json");
        [CtLocalVariableImpl][CtCommentImpl]// Publish github workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// update the default workflow path to be hello.cwl , the workflow path in workflow versions should also be changes
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"/hello.cwl");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowPath([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// check if the workflow versions have the same workflow path or not in the database
        final [CtTypeReferenceImpl]java.lang.String masterpath = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select workflowpath from workflowversion where name = 'testWorkflowPath'", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String testpath = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select workflowpath from workflowversion where name = 'testWorkflowPath'", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"master workflow path should be the same as default workflow path, it is " + [CtVariableReadImpl]masterpath, [CtLiteralImpl]"/Dockstore.cwl", [CtVariableReadImpl]masterpath);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"test workflow path should be the same as default workflow path, it is " + [CtVariableReadImpl]testpath, [CtLiteralImpl]"/Dockstore.cwl", [CtVariableReadImpl]testpath);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testAddingWorkflowForumUrl() throws [CtTypeReferenceImpl]io.swagger.client.ApiException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Set up webservice
        [CtTypeReferenceImpl]io.swagger.client.ApiClient webClient = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.WorkflowIT.getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]webClient);
        [CtInvocationImpl][CtVariableReadImpl]usersApi.getUser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITHUB.getFriendlyName(), [CtLiteralImpl]"DockstoreTestUser2/test_lastmodified", [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]"test-update-workflow", [CtInvocationImpl][CtTypeAccessImpl]DescriptorLanguage.CWL.toString(), [CtLiteralImpl]"/test.json");
        [CtInvocationImpl][CtCommentImpl]// update the forumUrl to hello.com
        [CtVariableReadImpl]workflow.setForumUrl([CtLiteralImpl]"hello.com");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// check the workflow's forumUrl is hello.com
        final [CtTypeReferenceImpl]java.lang.String updatedForumUrl = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select forumurl from workflow where workflowname = 'test-update-workflow'", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"forumUrl should be updated, it is " + [CtVariableReadImpl]updatedForumUrl, [CtLiteralImpl]"hello.com", [CtVariableReadImpl]updatedForumUrl);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testWorkflowFreezingWithNoFiles() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient webClient = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.WorkflowIT.getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long userId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]usersApi.getUser().getId();
        [CtLocalVariableImpl][CtCommentImpl]// Get workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow githubWorkflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtLiteralImpl]"github", [CtLiteralImpl]"DockstoreTestUser2/test_lastmodified", [CtLiteralImpl]"/wrongpath.wdl", [CtLiteralImpl]"test-update-workflow", [CtLiteralImpl]"wdl", [CtLiteralImpl]"/wrong-test.json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflowBeforeFreezing = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl][CtVariableReadImpl]master.setFrozen([CtLiteralImpl]true);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]master));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// should exception
            Assert.assertTrue([CtLiteralImpl]"missing error message", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtTypeAccessImpl]io.dockstore.webservice.core.Version.CANNOT_FREEZE_VERSIONS_WITH_NO_FILES));
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]Assert.fail([CtLiteralImpl]"should be unreachable");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testWorkflowFreezing() throws [CtTypeReferenceImpl]io.swagger.client.ApiException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Set up webservice
        [CtTypeReferenceImpl]io.swagger.client.ApiClient webClient = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.WorkflowIT.getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long userId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]usersApi.getUser().getId();
        [CtLocalVariableImpl][CtCommentImpl]// Get workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow githubWorkflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtLiteralImpl]"github", [CtLiteralImpl]"DockstoreTestUser2/test_lastmodified", [CtLiteralImpl]"/hello.wdl", [CtLiteralImpl]"test-update-workflow", [CtLiteralImpl]"wdl", [CtLiteralImpl]"/test.json");
        [CtLocalVariableImpl][CtCommentImpl]// Publish github workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflowBeforeFreezing = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl][CtVariableReadImpl]master.setFrozen([CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions1 = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]master));
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowVersions1.stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]master.isFrozen());
        [CtAssignmentImpl][CtCommentImpl]// try various operations that should be disallowed
        [CtCommentImpl]// cannot modify version properties, like unfreezing for now
        [CtVariableWriteImpl]workflowBeforeFreezing = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl][CtVariableReadImpl]master.setFrozen([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]master));
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowVersions.stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]master.isFrozen());
        [CtInvocationImpl][CtCommentImpl]// but should be able to change doi stuff
        [CtVariableReadImpl]master.setFrozen([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]master.setDoiStatus([CtTypeAccessImpl]WorkflowVersion.DoiStatusEnum.REQUESTED);
        [CtInvocationImpl][CtVariableReadImpl]master.setDoiURL([CtLiteralImpl]"foo");
        [CtAssignmentImpl][CtVariableWriteImpl]workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]master));
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowVersions.stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"foo", [CtInvocationImpl][CtVariableReadImpl]master.getDoiURL());
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]WorkflowVersion.DoiStatusEnum.REQUESTED, [CtInvocationImpl][CtVariableReadImpl]master.getDoiStatus());
        [CtLocalVariableImpl][CtCommentImpl]// refresh should skip over the frozen version
        final [CtTypeReferenceImpl]io.swagger.client.model.Workflow refresh = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refresh.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtLocalVariableImpl][CtCommentImpl]// cannot modify sourcefiles for a frozen version
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.dockstore.webservice.core.SourceFile> sourceFiles = [CtInvocationImpl][CtFieldReadImpl]fileDAO.findSourceFilesByVersion([CtInvocationImpl][CtVariableReadImpl]master.getId());
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtVariableReadImpl]sourceFiles.isEmpty());
        [CtInvocationImpl][CtVariableReadImpl]sourceFiles.forEach([CtLambdaImpl]([CtParameterImpl] s) -> [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]s.isFrozen());
            [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtLiteralImpl]"update sourcefile set content = 'foo' where id = " + [CtInvocationImpl][CtVariableReadImpl]s.getId());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String content = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtLiteralImpl]"select content from sourcefile where id = " + [CtInvocationImpl][CtVariableReadImpl]s.getId(), [CtFieldReadImpl]java.lang.String.class);
            [CtInvocationImpl]assertNotEquals([CtLiteralImpl]"foo", [CtVariableReadImpl]content);
        });
        [CtInvocationImpl][CtCommentImpl]// try deleting a row join table
        [CtVariableReadImpl]sourceFiles.forEach([CtLambdaImpl]([CtParameterImpl] s) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int affected = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtLiteralImpl]"delete from version_sourcefile vs where vs.sourcefileid = " + [CtInvocationImpl][CtVariableReadImpl]s.getId());
            [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtVariableReadImpl]affected);
        });
        [CtInvocationImpl][CtCommentImpl]// try updating a row in the join table
        [CtVariableReadImpl]sourceFiles.forEach([CtLambdaImpl]([CtParameterImpl] s) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int affected = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtLiteralImpl]"update version_sourcefile set sourcefileid=123456 where sourcefileid = " + [CtInvocationImpl][CtVariableReadImpl]s.getId());
            [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtVariableReadImpl]affected);
        });
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Long versionId = [CtInvocationImpl][CtVariableReadImpl]master.getId();
        [CtInvocationImpl][CtCommentImpl]// try creating a row in the join table
        [CtVariableReadImpl]sourceFiles.forEach([CtLambdaImpl]([CtParameterImpl] s) -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.testingPostgres.runUpdateStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"insert into version_sourcefile (versionid, sourcefileid) values (" + [CtVariableReadImpl]versionId) + [CtLiteralImpl]", ") + [CtLiteralImpl]1234567890) + [CtLiteralImpl]")");
                [CtInvocationImpl]fail([CtLiteralImpl]"Insert should have failed to do row-level security");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.ex.getMessage().contains([CtLiteralImpl]"new row violates row-level"));
            }
        });
        [CtTryImpl][CtCommentImpl]// cannot add or delete test files for frozen versions
        try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.deleteTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtLiteralImpl]"foo"), [CtLiteralImpl]"master");
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"could delete test parameter file");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtTypeAccessImpl]io.dockstore.webservice.helpers.EntryVersionHelper.CANNOT_MODIFY_FROZEN_VERSIONS_THIS_WAY));
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.addTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtLiteralImpl]"foo"), [CtLiteralImpl]"", [CtLiteralImpl]"master");
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"could add test parameter file");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtTypeAccessImpl]io.dockstore.webservice.helpers.EntryVersionHelper.CANNOT_MODIFY_FROZEN_VERSIONS_THIS_WAY));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that a workflow's default version can be automatically set during refresh
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpdateWorkflowDefaultVersionDuringRefresh() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// Manually register workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]true);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"manualRegisterAndPublish does a refresh, it should automatically set the default version", [CtLiteralImpl]"master", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowDefaultVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"testBoth");
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"Should be able to overwrite previous default version", [CtLiteralImpl]"testBoth", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"Refresh should not have set it back to the automatic one", [CtLiteralImpl]"testBoth", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
        [CtInvocationImpl][CtCommentImpl]// Mimic version on Dockstore no longer present on GitHub
        [CtFieldReadImpl]testingPostgres.runUpdateStatement([CtLiteralImpl]"UPDATE workflowversion SET name = 'deletedGitHubBranch', reference ='deletedGitHubBranch' where name='testBoth'");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtLiteralImpl]"the old default was deleted during refresh, it should automatically set the default version again", [CtLiteralImpl]"master", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that a workflow can be updated to have default version, and that metadata is set related to the default version
     * WorkflowVersion 951 is testBoth
     * WorkflowVersion 952 is testCWL
     * WorkflowVersion 953 is testWWL
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testUpdateWorkflowDefaultVersion() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// Manually register workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtCommentImpl]// Update workflow with version with no metadata
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowDefaultVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"testWDL");
        [CtLocalVariableImpl][CtCommentImpl]// Assert default version is updated and no author or email is found
        [CtTypeReferenceImpl]long defaultVersionNumber = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select actualdefaultversion from workflow where id = '951'", [CtFieldReadImpl]long.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String defaultVersionName = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select name from workflowversion where id = '" + [CtVariableReadImpl]defaultVersionNumber) + [CtLiteralImpl]"'", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"the default version should be for the testWDL branch, but is for the branch " + [CtVariableReadImpl]defaultVersionName, [CtLiteralImpl]"testWDL", [CtVariableReadImpl]defaultVersionName);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where actualdefaultversion = '" + [CtVariableReadImpl]defaultVersionNumber) + [CtLiteralImpl]"' and author is null and email is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The given workflow shouldn't have any contact info", [CtLiteralImpl]1, [CtVariableReadImpl]count2);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]null);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"testWDL", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]workflow.getAuthor());
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]workflow.getEmail());
        [CtAssignmentImpl][CtCommentImpl]// Update workflow with version with metadata
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowDefaultVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"testBoth");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// Assert default version is updated and author and email are set
        [CtVariableWriteImpl]defaultVersionNumber = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select actualdefaultversion from workflow where id = '951'", [CtFieldReadImpl]long.class);
        [CtAssignmentImpl][CtVariableWriteImpl]defaultVersionName = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select name from workflowversion where id = '" + [CtVariableReadImpl]defaultVersionNumber) + [CtLiteralImpl]"'", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"the default version should be for the testBoth branch, but is for the branch " + [CtVariableReadImpl]defaultVersionName, [CtLiteralImpl]"testBoth", [CtVariableReadImpl]defaultVersionName);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count3 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where actualdefaultversion = '" + [CtVariableReadImpl]defaultVersionNumber) + [CtLiteralImpl]"' and author = 'testAuthor' and email = 'testEmail'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The given workflow should have contact info", [CtLiteralImpl]1, [CtVariableReadImpl]count3);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.getWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]null);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"testBoth", [CtInvocationImpl][CtVariableReadImpl]workflow.getDefaultVersion());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"testAuthor", [CtInvocationImpl][CtVariableReadImpl]workflow.getAuthor());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"testEmail", [CtInvocationImpl][CtVariableReadImpl]workflow.getEmail());
        [CtAssignmentImpl][CtCommentImpl]// Unpublish
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]false));
        [CtInvocationImpl][CtCommentImpl]// Alter workflow so that it has no valid tags
        [CtFieldReadImpl]testingPostgres.runUpdateStatement([CtLiteralImpl]"UPDATE workflowversion SET valid='f'");
        [CtTryImpl][CtCommentImpl]// Now you shouldn't be able to publish the workflow
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"Repository does not meet requirements to publish"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This test tests a bunch of different assumptions for how refresh should work for workflows
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRefreshRelatedConcepts() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// check that workflow is valid and full
        final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 2 valid versions, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]2, [CtVariableReadImpl]count2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count3 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where mode='FULL'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 full workflows, there are " + [CtVariableReadImpl]count3, [CtLiteralImpl]1, [CtVariableReadImpl]count3);
        [CtInvocationImpl][CtCommentImpl]// Change path for each version so that it is invalid
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"thisisnotarealpath.cwl");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Workflow has no valid versions so you cannot publish
        [CtCommentImpl]// check that invalid
        final [CtTypeReferenceImpl]long count4 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='f'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 4 invalid versions, there are " + [CtVariableReadImpl]count4, [CtLiteralImpl]4, [CtVariableReadImpl]count4);
        [CtAssignmentImpl][CtCommentImpl]// Restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtInvocationImpl][CtCommentImpl]// Update workflow to WDL
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"/Dockstore.wdl");
        [CtInvocationImpl][CtVariableReadImpl]workflow.setDescriptorType([CtTypeAccessImpl]Workflow.DescriptorTypeEnum.WDL);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// Can now publish workflow
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        [CtAssignmentImpl][CtCommentImpl]// unpublish
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]false));
        [CtInvocationImpl][CtCommentImpl]// Set paths to invalid
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"thisisnotarealpath.wdl");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Check that versions are invalid
        final [CtTypeReferenceImpl]long count5 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='f'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 4 invalid versions, there are " + [CtVariableReadImpl]count5, [CtLiteralImpl]4, [CtVariableReadImpl]count5);
        [CtTryImpl][CtCommentImpl]// should now not be able to publish
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getMessage().contains([CtLiteralImpl]"Repository does not meet requirements to publish"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests the dirty bit attribute for workflow versions with github
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGithubDirtyBit() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/hello-dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Check that no versions have a true dirty bit
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where dirtybit = true", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be no versions with dirty bit, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]0, [CtVariableReadImpl]count);
        [CtLocalVariableImpl][CtCommentImpl]// Update workflow version to new path
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] version) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]version.getName(), [CtLiteralImpl]"master")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Master version should exist");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion updateWorkflowVersion = [CtInvocationImpl][CtVariableReadImpl]workflowVersion.get();
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setWorkflowPath([CtLiteralImpl]"/Dockstoredirty.cwl");
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.add([CtVariableReadImpl]updateWorkflowVersion);
        [CtAssignmentImpl][CtVariableWriteImpl]workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflowVersions);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// There should be on dirty bit
        final [CtTypeReferenceImpl]long count1 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where dirtybit = true", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 versions with dirty bit, there are " + [CtVariableReadImpl]count1, [CtLiteralImpl]1, [CtVariableReadImpl]count1);
        [CtInvocationImpl][CtCommentImpl]// Update default cwl
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"/Dockstoreclean.cwl");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// There should be 3 versions with new cwl
        final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where workflowpath = '/Dockstoreclean.cwl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 3 versions with workflow path /Dockstoreclean.cwl, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]3, [CtVariableReadImpl]count2);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests the dirty bit attribute for workflow versions with bitbucket
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBitbucketDirtyBit() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"dockstore_testuser2/dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.BITBUCKET, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long nullLastModifiedWorkflowVersions = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where lastmodified is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"All Bitbucket workflow versions should have last modified populated after refreshing", [CtLiteralImpl]0, [CtVariableReadImpl]nullLastModifiedWorkflowVersions);
        [CtLocalVariableImpl][CtCommentImpl]// Check that no versions have a true dirty bit
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where dirtybit = true", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be no versions with dirty bit, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]0, [CtVariableReadImpl]count);
        [CtLocalVariableImpl][CtCommentImpl]// Update workflow version to new path
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersion = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] version) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]version.getName(), [CtLiteralImpl]"master")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]workflowVersion.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Master version should exist");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion updateWorkflowVersion = [CtInvocationImpl][CtVariableReadImpl]workflowVersion.get();
        [CtInvocationImpl][CtVariableReadImpl]updateWorkflowVersion.setWorkflowPath([CtLiteralImpl]"/Dockstoredirty.cwl");
        [CtInvocationImpl][CtVariableReadImpl]workflowVersions.add([CtVariableReadImpl]updateWorkflowVersion);
        [CtAssignmentImpl][CtVariableWriteImpl]workflowVersions = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflowVersions);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// There should be on dirty bit
        final [CtTypeReferenceImpl]long count1 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where dirtybit = true", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 versions with dirty bit, there are " + [CtVariableReadImpl]count1, [CtLiteralImpl]1, [CtVariableReadImpl]count1);
        [CtInvocationImpl][CtCommentImpl]// Update default cwl
        [CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"/Dockstoreclean.cwl");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// There should be 3 versions with new cwl
        final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where workflowpath = '/Dockstoreclean.cwl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 4 versions with workflow path /Dockstoreclean.cwl, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]4, [CtVariableReadImpl]count2);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is a high level test to ensure that gitlab basics are working for gitlab as a workflow repo
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGitlab() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"dockstore.test.user2/dockstore-workflow-example", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITLAB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long nullLastModifiedWorkflowVersions = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where lastmodified is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"All GitLab workflow versions should have last modified populated after refreshing", [CtLiteralImpl]0, [CtVariableReadImpl]nullLastModifiedWorkflowVersions);
        [CtLocalVariableImpl][CtCommentImpl]// Check a few things
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where mode='FULL' and sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 workflow, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 2 valid version, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]2, [CtVariableReadImpl]count2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count3 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where mode='FULL' and sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 workflow, there are " + [CtVariableReadImpl]count3, [CtLiteralImpl]1, [CtVariableReadImpl]count3);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtBlockImpl]{
            [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getCommitID());
        });
        [CtAssignmentImpl][CtCommentImpl]// publish
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]true));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count4 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where mode='FULL' and sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example' and ispublished='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 published workflow, there are " + [CtVariableReadImpl]count4, [CtLiteralImpl]1, [CtVariableReadImpl]count4);
        [CtAssignmentImpl][CtCommentImpl]// unpublish
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.publish([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.SwaggerUtility.createPublishRequest([CtLiteralImpl]false));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count5 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where mode='FULL' and sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example' and ispublished='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 0 published workflows, there are " + [CtVariableReadImpl]count5, [CtLiteralImpl]0, [CtVariableReadImpl]count5);
        [CtLocalVariableImpl][CtCommentImpl]// change default branch
        final [CtTypeReferenceImpl]long count6 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example' and author is null and email is null and description is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The given workflow shouldn't have any contact info", [CtLiteralImpl]1, [CtVariableReadImpl]count6);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowDefaultVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"test");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count7 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where actualdefaultversion = 952 and author is null and email is null and description is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The given workflow should now have contact info and description", [CtLiteralImpl]0, [CtVariableReadImpl]count7);
        [CtAssignmentImpl][CtCommentImpl]// restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count8 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select count(*) from workflow where mode='STUB' and sourcecontrol = '" + [CtInvocationImpl][CtTypeAccessImpl]SourceControl.GITLAB.toString()) + [CtLiteralImpl]"' and organization = 'dockstore.test.user2' and repository = 'dockstore-workflow-example'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"The workflow should now be a stub", [CtLiteralImpl]1, [CtVariableReadImpl]count8);
        [CtInvocationImpl][CtCommentImpl]// Convert to WDL workflow
        [CtVariableReadImpl]workflow.setDescriptorType([CtTypeAccessImpl]Workflow.DescriptorTypeEnum.WDL);
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtLocalVariableImpl][CtCommentImpl]// Should now be a WDL workflow
        final [CtTypeReferenceImpl]long count9 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflow where descriptortype='wdl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 wdl workflow" + [CtVariableReadImpl]count9, [CtLiteralImpl]1, [CtVariableReadImpl]count9);
        [CtAssignmentImpl][CtCommentImpl]// Restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtAssignmentImpl][CtCommentImpl]// Refresh a single version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"master", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should only have one version", [CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have master version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"master")));
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should no longer be a stub workflow", [CtTypeAccessImpl]Workflow.ModeEnum.FULL, [CtInvocationImpl][CtVariableReadImpl]workflow.getMode());
        [CtAssignmentImpl][CtCommentImpl]// Refresh another version
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"test", [CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should now have two versions", [CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().size());
        [CtInvocationImpl]Assert.assertTrue([CtLiteralImpl]"Should have test version", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().anyMatch([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"test")));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refreshVersion([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]"fakeVersion", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should not be able to refresh a version that does not exist");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException ex) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]HttpStatus.BAD_REQUEST_400, [CtInvocationImpl][CtVariableReadImpl]ex.getCode());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests manually publishing a Bitbucket workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testManualPublishBitbucket() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// manual publish
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"dockstore_testuser2/dockstore-workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.BITBUCKET, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// Check for two valid versions (wdl_import and surprisingly, cwl_import)
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='t' and (name='wdl_import' OR name='cwl_import')", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"There should be a valid 'wdl_import' version and a valid 'cwl_import' version", [CtLiteralImpl]2, [CtVariableReadImpl]count);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where lastmodified is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"All Bitbucket workflow versions should have last modified populated when manual published", [CtLiteralImpl]0, [CtVariableReadImpl]count2);
        [CtInvocationImpl][CtCommentImpl]// Check that commit ID is set
        [CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().forEach([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtBlockImpl]{
            [CtInvocationImpl]assertNotNull([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getCommitID());
        });
        [CtLocalVariableImpl][CtCommentImpl]// grab wdl file
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> version = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"wdl_import")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]version.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"wdl_import version should exist");
        }
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]fileDAO.findSourceFilesByVersion([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]version.get().getId()).stream().filter([CtLambdaImpl]([CtParameterImpl] sourceFile) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]sourceFile.getAbsolutePath(), [CtLiteralImpl]"/Dockstore.wdl")).findFirst().isPresent());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests manually publishing a gitlab workflow
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testManualPublishGitlab() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// manual publish
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"dockstore.test.user2/dockstore-workflow-example", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITLAB, [CtLiteralImpl]"/Dockstore.wdl", [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// Check for one valid version
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where valid='t'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 valid version, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where lastmodified is null", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"All GitLab workflow versions should have last modified populated when manual published", [CtLiteralImpl]0, [CtVariableReadImpl]count2);
        [CtLocalVariableImpl][CtCommentImpl]// grab wdl file
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> version = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflow.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] workflowVersion) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]workflowVersion.getName(), [CtLiteralImpl]"master")).findFirst();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]version.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"master version should exist");
        }
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]fileDAO.findSourceFilesByVersion([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]version.get().getId()).stream().filter([CtLambdaImpl]([CtParameterImpl] sourceFile) -> [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]sourceFile.getAbsolutePath(), [CtLiteralImpl]"/Dockstore.wdl")).findFirst().isPresent());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests getting branches and tags from gitlab repositories
     */
    [CtAnnotationImpl]@org.junit.Test
    [CtAnnotationImpl]@org.junit.experimental.categories.Category([CtFieldReadImpl]io.dockstore.common.SlowTest.class)
    public [CtTypeReferenceImpl]void testGitLabTagAndBranchTracking() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// manual publish
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"dockstore.test.user2/dockstore-workflow-md5sum-unified", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITLAB, [CtLiteralImpl]"/checker.wdl", [CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"there should be at least 5 versions, there are " + [CtVariableReadImpl]count, [CtBinaryOperatorImpl][CtVariableReadImpl]count >= [CtLiteralImpl]5);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long branchCount = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where referencetype = 'BRANCH'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"there should be at least 2 branches, there are " + [CtVariableReadImpl]count, [CtBinaryOperatorImpl][CtVariableReadImpl]branchCount >= [CtLiteralImpl]2);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long tagCount = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from workflowversion where referencetype = 'TAG'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertTrue([CtBinaryOperatorImpl][CtLiteralImpl]"there should be at least 3 tags, there are " + [CtVariableReadImpl]count, [CtBinaryOperatorImpl][CtVariableReadImpl]tagCount >= [CtLiteralImpl]3);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that WDL files are properly parsed for secondary WDL files
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testWDLWithImports() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/test_workflow_wdl", [CtLiteralImpl]"testname", [CtLiteralImpl]"wdl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/hello.wdl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Check for WDL files
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where path='helper.wdl'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be 1 secondary file named helper.wdl, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests basic concepts with workflow test parameter files
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTestParameterFile() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtCommentImpl]// refresh all and individual
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/parameter_test_workflow", [CtLiteralImpl]"testname", [CtLiteralImpl]"cwl", [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// There should be no sourcefiles
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type like '%_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be no source files that are test parameter files, there are " + [CtVariableReadImpl]count, [CtLiteralImpl]0, [CtVariableReadImpl]count);
        [CtLocalVariableImpl][CtCommentImpl]// Update version master with test parameters
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> toAdd = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"test.cwl.json");
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"test2.cwl.json");
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"fake.cwl.json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.SourceFile> master = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.addTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toAdd, [CtLiteralImpl]"", [CtLiteralImpl]"master");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> toDelete = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]toDelete.add([CtLiteralImpl]"notreal.cwl.json");
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.deleteTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toDelete, [CtLiteralImpl]"master");
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Should've have thrown an error when deleting non-existent file");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException e) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Should have returned a 404 when deleting non-existent file", [CtTypeAccessImpl]HttpStatus.NOT_FOUND_404, [CtInvocationImpl][CtVariableReadImpl]e.getCode());
        }
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count2 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type like '%_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be two sourcefiles that are test parameter files, there are " + [CtVariableReadImpl]count2, [CtLiteralImpl]2, [CtVariableReadImpl]count2);
        [CtInvocationImpl][CtCommentImpl]// Update version with test parameters
        [CtVariableReadImpl]toAdd.clear();
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"test.cwl.json");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.addTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toAdd, [CtLiteralImpl]"", [CtLiteralImpl]"master");
        [CtInvocationImpl][CtVariableReadImpl]toDelete.clear();
        [CtInvocationImpl][CtVariableReadImpl]toDelete.add([CtLiteralImpl]"test2.cwl.json");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.deleteTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toDelete, [CtLiteralImpl]"master");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count3 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type like '%_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be one sourcefile that is a test parameter file, there are " + [CtVariableReadImpl]count3, [CtLiteralImpl]1, [CtVariableReadImpl]count3);
        [CtInvocationImpl][CtCommentImpl]// Update other version with test parameters
        [CtVariableReadImpl]toAdd.clear();
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"test.wdl.json");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.addTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toAdd, [CtLiteralImpl]"", [CtLiteralImpl]"wdltest");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count4 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type='CWL_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be two sourcefiles that are cwl test parameter files, there are " + [CtVariableReadImpl]count4, [CtLiteralImpl]2, [CtVariableReadImpl]count4);
        [CtAssignmentImpl][CtCommentImpl]// Restub
        [CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.restub([CtInvocationImpl][CtVariableReadImpl]workflow.getId());
        [CtInvocationImpl][CtCommentImpl]// Change to WDL
        [CtVariableReadImpl]workflow.setDescriptorType([CtTypeAccessImpl]Workflow.DescriptorTypeEnum.WDL);
        [CtInvocationImpl][CtVariableReadImpl]workflow.setWorkflowPath([CtLiteralImpl]"Dockstore.wdl");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflow([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]workflow);
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Should be no sourcefiles
        final [CtTypeReferenceImpl]long count5 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type like '%_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be no source files that are test parameter files, there are " + [CtVariableReadImpl]count5, [CtLiteralImpl]0, [CtVariableReadImpl]count5);
        [CtInvocationImpl][CtCommentImpl]// Update version wdltest with test parameters
        [CtVariableReadImpl]toAdd.clear();
        [CtInvocationImpl][CtVariableReadImpl]toAdd.add([CtLiteralImpl]"test.wdl.json");
        [CtInvocationImpl][CtVariableReadImpl]workflowsApi.addTestParameterFiles([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtVariableReadImpl]toAdd, [CtLiteralImpl]"", [CtLiteralImpl]"wdltest");
        [CtAssignmentImpl][CtVariableWriteImpl]workflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]workflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]long count6 = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from sourcefile where type='WDL_TEST_JSON'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"there should be one sourcefile that is a wdl test parameter file, there are " + [CtVariableReadImpl]count6, [CtLiteralImpl]1, [CtVariableReadImpl]count6);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This tests that you can refresh user data by refreshing a workflow
     * ONLY WORKS if the current user in the database dump has no metadata, and on Github there is metadata (bio, location)
     * If the user has metadata, test will pass as long as the user's metadata isn't the same as Github already
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRefreshingUserMetadata() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Refresh all workflows
        [CtTypeReferenceImpl]io.swagger.client.ApiClient client = [CtInvocationImpl]getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.UsersApi usersApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.UsersApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]client);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflow = [CtInvocationImpl]manualRegisterAndPublish([CtVariableReadImpl]workflowsApi, [CtLiteralImpl]"DockstoreTestUser2/parameter_test_workflow", [CtLiteralImpl]"testname", [CtInvocationImpl][CtTypeAccessImpl]DescriptorLanguage.CWL.getShortName(), [CtTypeAccessImpl]SourceControl.GITHUB, [CtLiteralImpl]"/Dockstore.cwl", [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Check that user has been updated
        [CtCommentImpl]// TODO: bizarrely, the new GitHub Java API library doesn't seem to handle bio
        [CtCommentImpl]// final long count = testingPostgres.runSelectStatement("select count(*) from enduser where location='Toronto' and bio='I am a test user'", long.class);
        final [CtTypeReferenceImpl]long count = [CtInvocationImpl][CtFieldReadImpl]testingPostgres.runSelectStatement([CtLiteralImpl]"select count(*) from user_profile where location='Toronto'", [CtFieldReadImpl]long.class);
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]"One user should have this info now, there are  " + [CtVariableReadImpl]count, [CtLiteralImpl]1, [CtVariableReadImpl]count);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGenerateDOIFrozenVersion() throws [CtTypeReferenceImpl]io.swagger.client.ApiException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Set up webservice
        [CtTypeReferenceImpl]io.swagger.client.ApiClient webClient = [CtInvocationImpl][CtTypeAccessImpl]io.dockstore.client.cli.WorkflowIT.getWebClient([CtTypeAccessImpl]io.dockstore.client.cli.USER_2_USERNAME, [CtFieldReadImpl]testingPostgres);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi workflowsApi = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.swagger.client.api.WorkflowsApi([CtVariableReadImpl]webClient);
        [CtLocalVariableImpl][CtCommentImpl]// register workflow
        [CtTypeReferenceImpl]io.swagger.client.model.Workflow githubWorkflow = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.manualRegister([CtLiteralImpl]"github", [CtLiteralImpl]"DockstoreTestUser2/test_lastmodified", [CtLiteralImpl]"/hello.wdl", [CtLiteralImpl]"test-update-workflow", [CtLiteralImpl]"wdl", [CtLiteralImpl]"/test.json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.Workflow workflowBeforeFreezing = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getWorkflowVersions().stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtTryImpl][CtCommentImpl]// try issuing DOI for workflow version that is not frozen.
        try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.requestDOIForWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtVariableReadImpl]master.getId(), [CtLiteralImpl]"");
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"This line should never execute if version is mutable. DOI should only be generated for frozen versions of workflows.");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException ex) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ex.getResponseBody().contains([CtTypeAccessImpl]io.dockstore.webservice.resources.WorkflowResource.FROZEN_VERSION_REQUIRED));
        }
        [CtInvocationImpl][CtCommentImpl]// freeze version 'master'
        [CtVariableReadImpl]master.setFrozen([CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.client.model.WorkflowVersion> workflowVersions1 = [CtInvocationImpl][CtVariableReadImpl]workflowsApi.updateWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]master));
        [CtAssignmentImpl][CtVariableWriteImpl]master = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workflowVersions1.stream().filter([CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.getName().equals([CtLiteralImpl]"master")).findFirst().get();
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]master.isFrozen());
        [CtTryImpl][CtCommentImpl]// TODO: For now just checking for next failure (no Zenodo token), but should replace with when DOI registration tests are written
        try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]workflowsApi.requestDOIForWorkflowVersion([CtInvocationImpl][CtVariableReadImpl]workflowBeforeFreezing.getId(), [CtInvocationImpl][CtVariableReadImpl]master.getId(), [CtLiteralImpl]"");
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"This line should never execute without valid Zenodo token");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]io.swagger.client.ApiException ex) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ex.getResponseBody().contains([CtTypeAccessImpl]io.dockstore.webservice.resources.WorkflowResource.NO_ZENDO_USER_TOKEN));
        }
        [CtInvocationImpl][CtCommentImpl]// Should be able to refresh a workflow with a frozen version without throwing an error
        [CtVariableReadImpl]workflowsApi.refresh([CtInvocationImpl][CtVariableReadImpl]githubWorkflow.getId(), [CtLiteralImpl]false);
    }
}
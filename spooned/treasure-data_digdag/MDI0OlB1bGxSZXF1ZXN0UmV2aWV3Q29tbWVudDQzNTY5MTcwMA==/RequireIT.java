[CompilationUnitImpl][CtPackageDeclarationImpl]package acceptance;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import io.digdag.client.api.Id;
[CtUnresolvedImport]import io.digdag.client.api.RestSessionAttemptCollection;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static org.hamcrest.Matchers.not;
[CtImportImpl]import static java.util.Arrays.asList;
[CtImportImpl]import java.nio.file.Files;
[CtImportImpl]import static java.nio.charset.StandardCharsets.UTF_8;
[CtUnresolvedImport]import static org.junit.Assert.assertThat;
[CtUnresolvedImport]import static utils.TestUtils.main;
[CtUnresolvedImport]import io.digdag.client.DigdagClient;
[CtUnresolvedImport]import static utils.TestUtils.getAttemptId;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.junit.Before;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import utils.CommandStatus;
[CtUnresolvedImport]import static org.hamcrest.Matchers.notNullValue;
[CtImportImpl]import java.nio.file.Path;
[CtUnresolvedImport]import com.google.common.io.Resources;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static utils.TestUtils.copyResource;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import com.google.common.io.ByteStreams;
[CtUnresolvedImport]import utils.TemporaryDigdagServer;
[CtUnresolvedImport]import static org.hamcrest.Matchers.is;
[CtUnresolvedImport]import static org.hamcrest.Matchers.containsString;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.junit.rules.TemporaryFolder;
[CtClassImpl]public class RequireIT {
    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder folder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]utils.TemporaryDigdagServer server = [CtInvocationImpl][CtTypeAccessImpl]utils.TemporaryDigdagServer.of();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.nio.file.Path config;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.nio.file.Path projectDir;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.digdag.client.DigdagClient client;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]projectDir = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]folder.getRoot().toPath().resolve([CtLiteralImpl]"foobar");
        [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtFieldReadImpl]projectDir);
        [CtAssignmentImpl][CtFieldWriteImpl]config = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]folder.newFile().toPath();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.digdag.client.DigdagClient.builder().host([CtInvocationImpl][CtFieldReadImpl]server.host()).port([CtInvocationImpl][CtFieldReadImpl]server.port()).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRequire() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create new project
        [CtTypeReferenceImpl]utils.CommandStatus initStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"init", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtInvocationImpl][CtFieldReadImpl]projectDir.toString());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]initStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]initStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path childOutFile = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.out").toAbsolutePath().normalize();
        [CtInvocationImpl]prepareForChildWF([CtVariableReadImpl]childOutFile);
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/parent.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent.dig"));
        [CtLocalVariableImpl][CtCommentImpl]// Push the project
        [CtTypeReferenceImpl]utils.CommandStatus pushStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"push", [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"require", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"-r", [CtLiteralImpl]"4711");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]pushStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]pushStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtCommentImpl]// Start the workflow
        [CtTypeReferenceImpl]io.digdag.client.api.Id attemptId;
        [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus startStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"start", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"require", [CtLiteralImpl]"parent", [CtLiteralImpl]"--session", [CtLiteralImpl]"now");
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]startStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
            [CtAssignmentImpl][CtVariableWriteImpl]attemptId = [CtInvocationImpl]utils.TestUtils.getAttemptId([CtVariableReadImpl]startStatus);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Wait for the attempt to complete
        [CtTypeReferenceImpl]boolean success = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]30; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus attemptsStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"attempts", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]attemptId));
            [CtAssignmentImpl][CtVariableWriteImpl]success = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attemptsStatus.outUtf8().contains([CtLiteralImpl]"status: success");
            [CtIfImpl]if ([CtVariableReadImpl]success) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);
        }
        [CtInvocationImpl]Assert.assertThat([CtVariableReadImpl]success, [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));
        [CtInvocationImpl][CtCommentImpl]// Verify that the file created by the child workflow is there
        Assert.assertThat([CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]childOutFile), [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRequireFailsWhenDependentFails() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/parent.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent.dig"));
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/fail.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.dig"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus status = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"run", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"parent.dig");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]status.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]status.code(), [CtInvocationImpl]Matchers.is([CtInvocationImpl]Matchers.not([CtLiteralImpl]0)));
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]status.errUtf8(), [CtInvocationImpl]Matchers.containsString([CtLiteralImpl]"Dependent workflow failed."));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRequireSucceedsWhenDependentFailsButIgnoreFailureIsSet() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/parent_ignore_failure.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent.dig"));
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/fail.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.dig"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus status = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"run", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"parent.dig");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]status.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]status.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIgnoreProjectIdParam() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// If require> op. does not have 'project_id' param and session start with --param project_id=,
        [CtCommentImpl]// --param project_id should be ignored in require> op.
        [CtCommentImpl]// In this test --param project_id= is set in start command.
        [CtCommentImpl]// If the session will finish successfully, it means that project_id of --param is ignored.
        [CtCommentImpl]// Create new project
        [CtTypeReferenceImpl]utils.CommandStatus initStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"init", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtInvocationImpl][CtFieldReadImpl]projectDir.toString());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]initStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]initStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path childOutFile = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.out").toAbsolutePath().normalize();
        [CtInvocationImpl]prepareForChildWF([CtVariableReadImpl]childOutFile);
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/parent.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent.dig"));
        [CtLocalVariableImpl][CtCommentImpl]// Push the project
        [CtTypeReferenceImpl]utils.CommandStatus pushStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"push", [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"require", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"-r", [CtLiteralImpl]"4711");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]pushStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]pushStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtCommentImpl]// Start the workflow
        [CtTypeReferenceImpl]io.digdag.client.api.Id attemptId;
        [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus startStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"start", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"require", [CtLiteralImpl]"parent", [CtLiteralImpl]"--session", [CtLiteralImpl]"now", [CtLiteralImpl]"--param", [CtLiteralImpl]"project_id=-1");
            [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]startStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
            [CtAssignmentImpl][CtVariableWriteImpl]attemptId = [CtInvocationImpl]utils.TestUtils.getAttemptId([CtVariableReadImpl]startStatus);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Wait for the attempt to complete
        [CtTypeReferenceImpl]boolean success = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]120; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus attemptsStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"attempts", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]attemptId));
            [CtAssignmentImpl][CtVariableWriteImpl]success = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attemptsStatus.outUtf8().contains([CtLiteralImpl]"status: success");
            [CtIfImpl]if ([CtVariableReadImpl]success) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);
        }
        [CtInvocationImpl]Assert.assertThat([CtVariableReadImpl]success, [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));[CtCommentImpl]// --param project_id=-1 is ignored.

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Test for project_id and project_name parameter
     *
     * @throws Exception
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRequireToAnotherProject() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String childProjectName = [CtLiteralImpl]"child_another";
        [CtLocalVariableImpl][CtCommentImpl]// Push child project
        [CtTypeReferenceImpl]java.nio.file.Path childProjectDir = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]folder.getRoot().toPath().resolve([CtLiteralImpl]"another_foobar");
        [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]childProjectDir);
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/child_another_project.dig", [CtVariableReadImpl]childProjectDir);
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus pushChildStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"push", [CtLiteralImpl]"--project", [CtInvocationImpl][CtVariableReadImpl]childProjectDir.toString(), [CtVariableReadImpl]childProjectName, [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]pushChildStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]pushChildStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtCommentImpl]// extract child project id
        [CtTypeReferenceImpl]java.util.regex.Matcher m = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]".*\\s+id:\\s+(\\d+).*").matcher([CtInvocationImpl][CtVariableReadImpl]pushChildStatus.outUtf8());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]m.find(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String childProjectId = [CtInvocationImpl][CtVariableReadImpl]m.group([CtLiteralImpl]1);
        [CtInvocationImpl][CtCommentImpl]// Push parent project
        [CtTypeAccessImpl]java.nio.file.Files.write([CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent_another_project.dig"), [CtInvocationImpl]java.util.Arrays.asList([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.io.Resources.toString([CtInvocationImpl][CtTypeAccessImpl]com.google.common.io.Resources.getResource([CtLiteralImpl]"acceptance/require/parent_another_project.dig"), [CtFieldReadImpl]java.nio.charset.StandardCharsets.UTF_8).replace([CtLiteralImpl]"__CHILD_PROJECT_ID__", [CtVariableReadImpl]childProjectId).replace([CtLiteralImpl]"__CHILD_PROJECT_NAME__", [CtVariableReadImpl]childProjectName)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus pushParentStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"push", [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"parent_another", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]pushParentStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]pushParentStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus startStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"start", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"parent_another", [CtLiteralImpl]"parent_another_project", [CtLiteralImpl]"--session", [CtLiteralImpl]"now");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]startStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]startStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testRerunOnParam() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create new project
        [CtTypeReferenceImpl]utils.CommandStatus initStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"init", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtInvocationImpl][CtFieldReadImpl]projectDir.toString());
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]initStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]initStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path childOutFile = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.out").toAbsolutePath().normalize();
        [CtInvocationImpl]prepareForChildWF([CtVariableReadImpl]childOutFile);
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/parent_rerun_on.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"parent.dig"));
        [CtInvocationImpl]utils.TestUtils.copyResource([CtLiteralImpl]"acceptance/require/child_rerun_on.dig", [CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.dig"));
        [CtLocalVariableImpl][CtCommentImpl]// Push the project
        [CtTypeReferenceImpl]utils.CommandStatus pushStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"push", [CtLiteralImpl]"--project", [CtInvocationImpl][CtFieldReadImpl]projectDir.toString(), [CtLiteralImpl]"require", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtLiteralImpl]"-r", [CtLiteralImpl]"4711");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]pushStatus.errUtf8(), [CtInvocationImpl][CtVariableReadImpl]pushStatus.code(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]0));
        [CtBlockImpl][CtCommentImpl]// test for rerun_on: none
        {
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sessionTime = [CtLiteralImpl]"2020-05-28 12:34:01";
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection childAttempts = [CtInvocationImpl]testRerunOnParam([CtVariableReadImpl]sessionTime, [CtLiteralImpl]"none", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.assertThat([CtLiteralImpl]"Number of child's attempt must be one. (== require> skip the call)", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]childAttempts.getAttempts().size(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]1));
        }
        [CtBlockImpl][CtCommentImpl]// test for rerun_on: all. require> kick child always.
        {
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sessionTime = [CtLiteralImpl]"2020-05-28 12:34:11";
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection childAttempts = [CtInvocationImpl]testRerunOnParam([CtVariableReadImpl]sessionTime, [CtLiteralImpl]"all", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.assertThat([CtLiteralImpl]"Number of child's attempt must be two. (== require> kick child)", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]childAttempts.getAttempts().size(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]2));
        }
        [CtBlockImpl][CtCommentImpl]// test for rerun_on: failed. previous child attempt failed
        {
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sessionTime = [CtLiteralImpl]"2020-05-28 12:34:21";
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection childAttempts = [CtInvocationImpl]testRerunOnParam([CtVariableReadImpl]sessionTime, [CtLiteralImpl]"failed", [CtLiteralImpl]true);
            [CtInvocationImpl]Assert.assertThat([CtLiteralImpl]"Number of child's attempt must be two. (== require> kick child)", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]childAttempts.getAttempts().size(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]2));
        }
        [CtBlockImpl][CtCommentImpl]// test for rerun_on: failed. previous child attempt succeeded
        {
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sessionTime = [CtLiteralImpl]"2020-05-28 12:34:31";
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection childAttempts = [CtInvocationImpl]testRerunOnParam([CtVariableReadImpl]sessionTime, [CtLiteralImpl]"failed", [CtLiteralImpl]false);
            [CtInvocationImpl]Assert.assertThat([CtLiteralImpl]"Number of child's attempt must be one. (== require> kick)", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]childAttempts.getAttempts().size(), [CtInvocationImpl]Matchers.is([CtLiteralImpl]1));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection testRerunOnParam([CtParameterImpl][CtTypeReferenceImpl]java.lang.String sessionTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String rerunOn, [CtParameterImpl][CtTypeReferenceImpl]boolean previousChildRunFail) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Start a child
        [CtTypeReferenceImpl]java.lang.String childFailParam = [CtConditionalImpl]([CtVariableReadImpl]previousChildRunFail) ? [CtLiteralImpl]"yes" : [CtLiteralImpl]"no";
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus childStatus = [CtInvocationImpl]startAndWait([CtLiteralImpl]true, [CtLiteralImpl]"require", [CtLiteralImpl]"child", [CtLiteralImpl]"--session", [CtVariableReadImpl]sessionTime, [CtLiteralImpl]"-p", [CtBinaryOperatorImpl][CtLiteralImpl]"child_fail=" + [CtVariableReadImpl]childFailParam);
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl]acceptance.RequireIT.isAttemptSuccess([CtVariableReadImpl]childStatus), [CtInvocationImpl]Matchers.not([CtVariableReadImpl]previousChildRunFail));
        [CtLocalVariableImpl][CtCommentImpl]// Start a parent with same session time.
        [CtTypeReferenceImpl]utils.CommandStatus rerunOnNoneStatus = [CtInvocationImpl]startAndWait([CtLiteralImpl]false, [CtLiteralImpl]"require", [CtLiteralImpl]"parent", [CtLiteralImpl]"--session", [CtVariableReadImpl]sessionTime, [CtLiteralImpl]"-p", [CtBinaryOperatorImpl][CtLiteralImpl]"param_rerun_on=" + [CtVariableReadImpl]rerunOn, [CtLiteralImpl]"-p", [CtLiteralImpl]"child_fail=no");
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl]acceptance.RequireIT.isAttemptSuccess([CtVariableReadImpl]rerunOnNoneStatus), [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.RestSessionAttemptCollection attempts = [CtInvocationImpl][CtFieldReadImpl]client.getSessionAttemptRetries([CtInvocationImpl]utils.TestUtils.getAttemptId([CtVariableReadImpl]childStatus));
        [CtReturnImpl]return [CtVariableReadImpl]attempts;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isAttemptSuccess([CtParameterImpl][CtTypeReferenceImpl]utils.CommandStatus status) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]status.outUtf8().contains([CtLiteralImpl]"status: success");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]utils.CommandStatus startAndWait([CtParameterImpl][CtTypeReferenceImpl]boolean ignoreAttemptFailure, [CtParameterImpl]java.lang.String... args) throws [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> newArgs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtLiteralImpl]"start", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint()));
        [CtInvocationImpl][CtVariableReadImpl]newArgs.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]args));
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus startStatus = [CtInvocationImpl]utils.TestUtils.main([CtVariableReadImpl]newArgs);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.digdag.client.api.Id startAttemptId = [CtInvocationImpl]utils.TestUtils.getAttemptId([CtVariableReadImpl]startStatus);
        [CtLocalVariableImpl][CtTypeReferenceImpl]utils.CommandStatus attemptsStatus = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// Wait for the attempt to complete
        [CtTypeReferenceImpl]boolean success = [CtLiteralImpl]false;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]30; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]attemptsStatus = [CtInvocationImpl]utils.TestUtils.main([CtLiteralImpl]"attempt", [CtLiteralImpl]"-c", [CtInvocationImpl][CtFieldReadImpl]config.toString(), [CtLiteralImpl]"-e", [CtInvocationImpl][CtFieldReadImpl]server.endpoint(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]startAttemptId));
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attemptsStatus.outUtf8().contains([CtLiteralImpl]"status: success")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]success = [CtLiteralImpl]true;
                [CtBreakImpl]break;
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attemptsStatus.outUtf8().contains([CtLiteralImpl]"status: error")) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]ignoreAttemptFailure) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertThat([CtVariableReadImpl]success, [CtInvocationImpl]Matchers.is([CtLiteralImpl]true));
        }
        [CtInvocationImpl]Assert.assertThat([CtVariableReadImpl]attemptsStatus, [CtInvocationImpl]Matchers.notNullValue());
        [CtReturnImpl]return [CtVariableReadImpl]attemptsStatus;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Replaice __FILE__ to real path then save to project dir
     *
     * @param childOutFile
     * @throws IOException
     */
    private [CtTypeReferenceImpl]void prepareForChildWF([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path childOutFile) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream input = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.io.Resources.getResource([CtLiteralImpl]"acceptance/require/child.dig").openStream()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String child = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtTypeAccessImpl]com.google.common.io.ByteStreams.toByteArray([CtVariableReadImpl]input), [CtLiteralImpl]"UTF-8").replace([CtLiteralImpl]"__FILE__", [CtInvocationImpl][CtVariableReadImpl]childOutFile.toString());
            [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.write([CtInvocationImpl][CtFieldReadImpl]projectDir.resolve([CtLiteralImpl]"child.dig"), [CtInvocationImpl][CtVariableReadImpl]child.getBytes([CtLiteralImpl]"UTF-8"));
        }
    }
}
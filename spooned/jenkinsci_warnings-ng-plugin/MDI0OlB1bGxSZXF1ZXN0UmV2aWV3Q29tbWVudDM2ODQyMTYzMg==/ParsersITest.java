[CompilationUnitImpl][CtPackageDeclarationImpl]package io.jenkins.plugins.analysis.warnings;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.jenkinsci.plugins.workflow.job.WorkflowJob;
[CtUnresolvedImport]import edu.hm.hafner.analysis.Issue;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.core.model.AnalysisResult;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.core.model.StaticAnalysisLabelProvider;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.core.model.Tool;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.warnings.checkstyle.CheckStyle;
[CtUnresolvedImport]import edu.hm.hafner.analysis.Report;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.core.testutil.IntegrationTestWithJenkinsPerSuite;
[CtUnresolvedImport]import io.jenkins.plugins.analysis.core.model.ReportScanningTool;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import static io.jenkins.plugins.analysis.core.assertions.Assertions.*;
[CtClassImpl][CtJavaDocImpl]/**
 * Integration tests of all parsers of the warnings plug-in in pipelines.
 *
 * @author Ullrich Hafner
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"PMD.CouplingBetweenObjects", [CtLiteralImpl]"PMD.ExcessivePublicCount", [CtLiteralImpl]"ClassDataAbstractionCoupling", [CtLiteralImpl]"ClassFanOutComplexity" })
public class ParsersITest extends [CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.testutil.IntegrationTestWithJenkinsPerSuite {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CODE_FRAGMENT = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<pre><code>#\n" + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    ERROR HANDLING: N/A\n") + [CtLiteralImpl]"    #\n") + [CtLiteralImpl]"    REMARKS: N/A\n") + [CtLiteralImpl]"    #\n") + [CtLiteralImpl]"    ****************************** END HEADER *************************************\n") + [CtLiteralImpl]"    #\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    ***************************** BEGIN PDL ***************************************\n") + [CtLiteralImpl]"    #\n") + [CtLiteralImpl]"    ****************************** END PDL ****************************************\n") + [CtLiteralImpl]"    #\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    ***************************** BEGIN CODE **************************************\n") + [CtLiteralImpl]"    **\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"if [ $# -lt 3 ]\n") + [CtLiteralImpl]"then\n") + [CtLiteralImpl]"exit 1\n") + [CtLiteralImpl]"fi\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"    initialize local variables\n") + [CtLiteralImpl]"    shift input parameter (twice) to leave only files to copy\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"files&#61;&#34;&#34;\n") + [CtLiteralImpl]"shift\n") + [CtLiteralImpl]"shift\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"    *******************************************************************************\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"for i in $*\n") + [CtLiteralImpl]"do\n") + [CtLiteralImpl]"files&#61;&#34;$files $directory/$i&#34;\n") + [CtLiteralImpl]"done</code></pre>";

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the native parser on a file that contains 9 issues..
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldReadNativeFormats() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]9 + [CtLiteralImpl]5) + [CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]WarningsPlugin(), [CtLiteralImpl]"warnings-issues.xml", [CtLiteralImpl]"issues.json", [CtLiteralImpl]"json-issues.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the native parser on a file that contains 9 issues..
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldReadNativeXmlFormat() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]9, [CtConstructorCallImpl]new [CtTypeReferenceImpl]WarningsPlugin(), [CtLiteralImpl]"warnings-issues.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the native parser on a file that contains 5 issues..
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldReadNativeJsonFormat() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]WarningsPlugin(), [CtLiteralImpl]"issues.json");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the native parser on a file that contains 8 issues..
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldReadNativeJsonLogFormat() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]WarningsPlugin(), [CtLiteralImpl]"json-issues.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies that a broken file does not fail.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldSilentlyIgnoreWrongFile() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]0, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.jenkins.plugins.analysis.warnings.checkstyle.CheckStyle(), [CtLiteralImpl]"sun_checks.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs with several tools that internally delegate to CheckStyle's  parser on an output file that contains 6
     * issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIssuesForCheckStyleAlias() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.ReportScanningTool tool : [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtConstructorCallImpl]new [CtTypeReferenceImpl]Detekt(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]EsLint(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]KtLint(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]PhpCodeSniffer(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]SwiftLint(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]TsLint())) [CtBlockImpl]{
            [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtVariableReadImpl]tool, [CtLiteralImpl]"checkstyle.xml");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCmakeIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Cmake(), [CtLiteralImpl]"cmake.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCargoIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Cargo(), [CtLiteralImpl]"CargoCheck.json");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 262 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIssuesForPmdAlias() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]262, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Infer(), [CtLiteralImpl]"pmd-6.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 262 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIssuesForMsBuildAlias() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PcLint(), [CtLiteralImpl]"msbuild.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllYamlLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]YamlLint(), [CtLiteralImpl]"yamllint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Iar parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIarIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Iar(), [CtLiteralImpl]"iar.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the IbLinter parser on an output file that contains 1 issue.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIbLinterIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1, [CtConstructorCallImpl]new [CtTypeReferenceImpl]IbLinter(), [CtLiteralImpl]"iblinter.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the IarCStat parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIarCStatIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]IarCstat(), [CtLiteralImpl]"iar-cstat.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the TagList parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllOpenTasks() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jenkinsci.plugins.workflow.job.WorkflowJob job = [CtInvocationImpl]createPipelineWithWorkspaceFiles([CtLiteralImpl]"tasks/file-with-tasks.txt");
        [CtInvocationImpl][CtVariableReadImpl]job.setDefinition([CtInvocationImpl]asStage([CtBinaryOperatorImpl][CtLiteralImpl]"def issues = scanForIssues tool: " + [CtLiteralImpl]"taskScanner(includePattern:'**/*issues.txt', highTags:'FIXME', normalTags:'TODO')", [CtTypeAccessImpl]PUBLISH_ISSUES_STEP));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.AnalysisResult result = [CtInvocationImpl]scheduleSuccessfulBuild([CtVariableReadImpl]job);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getTotalSize()).isEqualTo([CtLiteralImpl]2);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getIssues()).hasSize([CtLiteralImpl]2).hasSeverities([CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the SonarQube parsers on two files that contains 6 and 31 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllSonarQubeIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]32, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SonarQube(), [CtLiteralImpl]"sonarqube-api.json");
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SonarQube(), [CtLiteralImpl]"sonarqube-differential.json");
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]38, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SonarQube(), [CtLiteralImpl]"sonarqube-api.json", [CtLiteralImpl]"sonarqube-differential.json");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the TagList parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllTagListIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]TagList(), [CtLiteralImpl]"taglist.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Ccm parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCcmIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Ccm(), [CtLiteralImpl]"ccm.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the ruboCop parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllRuboCopIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]RuboCop(), [CtLiteralImpl]"rubocop.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Android Lint parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllAndroidLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]AndroidLint(), [CtLiteralImpl]"android-lint.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CodeNarc parser on an output file that contains 11 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCodeNArcIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]11, [CtConstructorCallImpl]new [CtTypeReferenceImpl]CodeNarc(), [CtLiteralImpl]"codeNarc.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Cppcheck parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCppCheckIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]CppCheck(), [CtLiteralImpl]"cppcheck.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the DocFx parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDocFXIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]DocFx(), [CtLiteralImpl]"docfx.json");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the ErrorProne parser on output files that contain 9 + 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllErrorProneIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]9 + [CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ErrorProne(), [CtLiteralImpl]"errorprone-maven.log", [CtLiteralImpl]"gradle-error-prone.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Flake8 parser on an output file that contains 12 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllFlake8Issues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]12, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Flake8(), [CtLiteralImpl]"flake8.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the JSHint parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllJsHintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]JsHint(), [CtLiteralImpl]"jshint.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Klocwork parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllKlocWorkIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]KlocWork(), [CtLiteralImpl]"klocwork.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the MyPy parser on an output file that contains 5 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllMyPyIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]MyPy(), [CtLiteralImpl]"mypy.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PIT parser on an output file that contains 25 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPitIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]22, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Pit(), [CtLiteralImpl]"pit.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PyDocStyle parser on an output file that contains 33 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPyDocStyleIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]33, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PyDocStyle(), [CtLiteralImpl]"pydocstyle.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the XML Lint parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllXmlLintStyleIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]XmlLint(), [CtLiteralImpl]"xmllint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the zptlint parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllZptLintStyleIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ZptLint(), [CtLiteralImpl]"zptlint.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CPD parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCpdIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Cpd(), [CtLiteralImpl]"cpd.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]Cpd(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0), [CtFieldReadImpl]io.jenkins.plugins.analysis.warnings.ParsersITest.CODE_FRAGMENT);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Simian parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllSimianIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Simian(), [CtLiteralImpl]"simian.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the DupFinder parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDupFinderIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]DupFinder(), [CtLiteralImpl]"dupfinder.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]DupFinder(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0), [CtLiteralImpl]"<pre><code>if (items &#61;&#61; null) throw new ArgumentNullException(&#34;items&#34;);</code></pre>");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Armcc parser on output files that contain 3 + 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllArmccIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]3 + [CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ArmCc(), [CtLiteralImpl]"armcc5.txt", [CtLiteralImpl]"armcc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Buckminster parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllBuckminsterIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Buckminster(), [CtLiteralImpl]"buckminster.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Cadence parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCadenceIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Cadence(), [CtLiteralImpl]"CadenceIncisive.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Mentor parser on an output file that contains 12 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllMentorGraphicsIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]12, [CtConstructorCallImpl]new [CtTypeReferenceImpl]MentorGraphics(), [CtLiteralImpl]"MentorGraphics.log");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PMD parser on an output file that contains 262 issues (PMD 6.1.0).
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPmdIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]262, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Pmd(), [CtLiteralImpl]"pmd-6.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]Pmd(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0), [CtLiteralImpl]"A high number of imports can indicate a high degree of coupling within an object.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CheckStyle parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCheckStyleIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.jenkins.plugins.analysis.warnings.checkstyle.CheckStyle(), [CtLiteralImpl]"checkstyle.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.jenkins.plugins.analysis.warnings.checkstyle.CheckStyle(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]2), [CtLiteralImpl]"<p>Since Checkstyle 3.1</p><p>");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.StaticAnalysisLabelProvider labelProvider = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.jenkins.plugins.analysis.warnings.checkstyle.CheckStyle().getLabelProvider();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]labelProvider.getDescription([CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]2))).contains([CtLiteralImpl]"The check finds classes that are designed for extension (subclass creation).");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertThatDescriptionOfIssueIsSet([CtParameterImpl]final [CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.Tool tool, [CtParameterImpl]final [CtTypeReferenceImpl]edu.hm.hafner.analysis.Issue issue, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String expectedDescription) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.StaticAnalysisLabelProvider labelProvider = [CtInvocationImpl][CtVariableReadImpl]tool.getLabelProvider();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]issue).hasDescription([CtLiteralImpl]"");
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]labelProvider.getDescription([CtVariableReadImpl]issue)).contains([CtVariableReadImpl]expectedDescription);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the FindBugs parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllFindBugsIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]FindBugs(), [CtLiteralImpl]"findbugs-native.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]FindBugs(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<p> The fields of this class appear to be accessed inconsistently with respect\n" + [CtLiteralImpl]"  to synchronization.&nbsp; This bug report indicates that the bug pattern detector\n") + [CtLiteralImpl]"  judged that\n") + [CtLiteralImpl]"  </p>\n") + [CtLiteralImpl]"  <ul>\n") + [CtLiteralImpl]"  <li> The class contains a mix of locked and unlocked accesses,</li>\n") + [CtLiteralImpl]"  <li> The class is <b>not</b> annotated as javax.annotation.concurrent.NotThreadSafe,</li>\n") + [CtLiteralImpl]"  <li> At least one locked access was performed by one of the class\'s own methods, and</li>\n") + [CtLiteralImpl]"  <li> The number of unsynchronized field accesses (reads and writes) was no more than\n") + [CtLiteralImpl]"       one third of all accesses, with writes being weighed twice as high as reads</li>\n") + [CtLiteralImpl]"  </ul>\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"  <p> A typical bug matching this bug pattern is forgetting to synchronize\n") + [CtLiteralImpl]"  one of the methods in a class that is intended to be thread-safe.</p>\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"  <p> You can select the nodes labeled \"Unsynchronized access\" to show the\n") + [CtLiteralImpl]"  code locations where the detector believed that a field was accessed\n") + [CtLiteralImpl]"  without synchronization.</p>\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"  <p> Note that there are various sources of inaccuracy in this detector;\n") + [CtLiteralImpl]"  for example, the detector cannot statically detect all situations in which\n") + [CtLiteralImpl]"  a lock is held.&nbsp; Also, even when the detector is accurate in\n") + [CtLiteralImpl]"  distinguishing locked vs. unlocked accesses, the code in question may still\n") + [CtLiteralImpl]"  be correct.</p>");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the SpotBugs parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllSpotBugsIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SpotBugs(), [CtLiteralImpl]"spotbugsXml.xml");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]SpotBugs(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<p>This code calls a method and ignores the return value. However our analysis shows that\n" + [CtLiteralImpl]"the method (including its implementations in subclasses if any) does not produce any effect \n") + [CtLiteralImpl]"other than return value. Thus this call can be removed.\n") + [CtLiteralImpl]"</p>\n") + [CtLiteralImpl]"<p>We are trying to reduce the false positives as much as possible, but in some cases this warning might be wrong.\n") + [CtLiteralImpl]"Common false-positive cases include:</p>\n") + [CtLiteralImpl]"<p>- The method is designed to be overridden and produce a side effect in other projects which are out of the scope of the analysis.</p>\n") + [CtLiteralImpl]"<p>- The method is called to trigger the class loading which may have a side effect.</p>\n") + [CtLiteralImpl]"<p>- The method is called just to get some exception.</p>\n") + [CtLiteralImpl]"<p>If you feel that our assumption is incorrect, you can use a @CheckReturnValue annotation\n") + [CtLiteralImpl]"to instruct FindBugs that ignoring the return value of this method is acceptable.\n") + [CtLiteralImpl]"</p>");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the SpotBugs parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldProvideMessagesAndDescriptionForSecurityIssuesWithSpotBugs() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SpotBugs(), [CtLiteralImpl]"issue55707.xml");
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Issue issue = [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]0);
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]SpotBugs(), [CtVariableReadImpl]issue, [CtBinaryOperatorImpl][CtLiteralImpl]"<p>A file is opened to read its content. The filename comes from an <b>input</b> parameter. \n" + [CtLiteralImpl]"If an unfiltered parameter is passed to this file API, files from an arbitrary filesystem location could be read.</p>\n");
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]issue).hasMessage([CtLiteralImpl]"java/nio/file/Paths.get(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path; reads a file whose location might be specified by user input");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Clang-Analyzer parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllClangAnalyzerIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ClangAnalyzer(), [CtLiteralImpl]"ClangAnalyzer.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Clang-Tidy parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllClangTidyIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ClangTidy(), [CtLiteralImpl]"ClangTidy.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Clang parser on an output file that contains 9 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllClangIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]9, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Clang(), [CtLiteralImpl]"apple-llvm-clang.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Coolflux parser on an output file that contains 1 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCoolfluxIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Coolflux(), [CtLiteralImpl]"coolfluxchesscc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CppLint parser on an output file that contains 1031 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCppLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1031, [CtConstructorCallImpl]new [CtTypeReferenceImpl]CppLint(), [CtLiteralImpl]"cpplint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CodeAnalysis parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCodeAnalysisIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]CodeAnalysis(), [CtLiteralImpl]"codeanalysis.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the DScanner parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDScannerIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]DScanner(), [CtLiteralImpl]"dscanner-report.json");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the GoLint parser on an output file that contains 7 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGoLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]7, [CtConstructorCallImpl]new [CtTypeReferenceImpl]GoLint(), [CtLiteralImpl]"golint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the GoVet parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGoVetIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]GoVet(), [CtLiteralImpl]"govet.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the SunC parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllSunCIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SunC(), [CtLiteralImpl]"sunc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the JcReport parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllJcReportIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]JcReport(), [CtLiteralImpl]"jcreport.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the StyleCop parser on an output file that contains 5 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllStyleCopIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]StyleCop(), [CtLiteralImpl]"stylecop.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Tasking VX parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllTaskingVxIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]TaskingVx(), [CtLiteralImpl]"tasking-vx.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the tnsdl translator parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllTnsdlIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Tnsdl(), [CtLiteralImpl]"tnsdl.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Texas Instruments Code Composer Studio parser on an output file that contains 10 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllTiCssIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]10, [CtConstructorCallImpl]new [CtTypeReferenceImpl]TiCss(), [CtLiteralImpl]"ticcs.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the IBM XLC compiler and linker parser on an output file that contains 1 + 1 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllXlcIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Xlc(), [CtLiteralImpl]"xlc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the YIU compressor parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllYuiCompressorIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]YuiCompressor(), [CtLiteralImpl]"yui.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Erlc parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllErlcIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Erlc(), [CtLiteralImpl]"erlc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the FlexSdk parser on an output file that contains 5 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllFlexSDKIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]FlexSdk(), [CtLiteralImpl]"flexsdk.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the FxCop parser on an output file that contains 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllFxcopSDKIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Fxcop(), [CtLiteralImpl]"fxcop.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Gendarme parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGendarmeIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Gendarme(), [CtLiteralImpl]"Gendarme.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the GhsMulti parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGhsMultiIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]GhsMulti(), [CtLiteralImpl]"ghsmulti.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Gnat parser on an output file that contains 9 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGnatIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]9, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Gnat(), [CtLiteralImpl]"gnat.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the GnuFortran parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGnuFortranIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]GnuFortran(), [CtLiteralImpl]"GnuFortran.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the MsBuild parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllMsBuildIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]MsBuild(), [CtLiteralImpl]"msbuild.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the NagFortran parser on an output file that contains 10 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllNagFortranIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]10, [CtConstructorCallImpl]new [CtTypeReferenceImpl]NagFortran(), [CtLiteralImpl]"NagFortran.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Perforce parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllP4Issues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Perforce(), [CtLiteralImpl]"perforce.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Pep8 parser on an output file: the build should report 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPep8Issues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Pep8(), [CtLiteralImpl]"pep8Test.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Gcc3Compiler parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGcc3CompilerIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Gcc3(), [CtLiteralImpl]"gcc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Gcc4Compiler and Gcc4Linker parsers on separate output file that contains 14 + 7 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllGcc4Issues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]14 + [CtLiteralImpl]7, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Gcc4(), [CtLiteralImpl]"gcc4.txt", [CtLiteralImpl]"gcc4ld.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Maven console parser on output files that contain 4 + 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllMavenConsoleIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]4 + [CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]MavenConsole(), [CtLiteralImpl]"maven-console.txt", [CtLiteralImpl]"issue13969.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the MetrowerksCWCompiler parser on two output files that contains 5 + 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllMetrowerksCWCompilerIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]5 + [CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]MetrowerksCodeWarrior(), [CtLiteralImpl]"MetrowerksCWCompiler.txt", [CtLiteralImpl]"MetrowerksCWLinker.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the AcuCobol parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllAcuCobolIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]AcuCobol(), [CtLiteralImpl]"acu.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Ajc parser on an output file that contains 9 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllAjcIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]9, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Ajc(), [CtLiteralImpl]"ajc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the AnsibleLint parser on an output file that contains 4 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllAnsibleLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]4, [CtConstructorCallImpl]new [CtTypeReferenceImpl]AnsibleLint(), [CtLiteralImpl]"ansibleLint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Perl::Critic parser on an output file that contains 105 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPerlCriticIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]105, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PerlCritic(), [CtLiteralImpl]"perlcritic.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Php parser on an output file that contains 5 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPhpIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Php(), [CtLiteralImpl]"php.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PHPStan scanner on an output file that contains 14 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPhpStanIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]11, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PhpStan(), [CtLiteralImpl]"phpstan.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Microsoft PreFast parser on an output file that contains 11 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPREfastIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]11, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PreFast(), [CtLiteralImpl]"PREfast.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Puppet Lint parser on an output file that contains 5 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPuppetLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]5, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PuppetLint(), [CtLiteralImpl]"puppet-lint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Eclipse parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllEclipseIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Eclipse(), [CtLiteralImpl]"eclipse.txt");
        [CtInvocationImpl][CtCommentImpl]// FIXME: fails if offline
        shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Eclipse(), [CtLiteralImpl]"eclipse-withinfo.xml");
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]8 + [CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Eclipse(), [CtLiteralImpl]"eclipse-withinfo.xml", [CtLiteralImpl]"eclipse.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PyLint parser on output files that contains 6 + 19 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPyLintParserIssues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]6 + [CtLiteralImpl]19, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PyLint(), [CtLiteralImpl]"pyLint.txt", [CtLiteralImpl]"pylint_parseable.txt");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]PyLint(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]1), [CtLiteralImpl]"Used when the name doesn't match the regular expression associated to its type(constant, variable, class...).");
        [CtInvocationImpl]assertThatDescriptionOfIssueIsSet([CtConstructorCallImpl]new [CtTypeReferenceImpl]PyLint(), [CtInvocationImpl][CtVariableReadImpl]report.get([CtLiteralImpl]7), [CtLiteralImpl]"Used when a wrong number of spaces is used around an operator, bracket orblock opener.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the QacSourceCodeAnalyser parser on an output file that contains 9 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllQACSourceCodeAnalyserIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]9, [CtConstructorCallImpl]new [CtTypeReferenceImpl]QacSourceCodeAnalyser(), [CtLiteralImpl]"QACSourceCodeAnalyser.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Resharper parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllResharperInspectCodeIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ResharperInspectCode(), [CtLiteralImpl]"ResharperInspectCode.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the RfLint parser on an output file that contains 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllRfLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]RfLint(), [CtLiteralImpl]"rflint.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Robocopy parser on an output file: the build should report 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllRobocopyIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Robocopy(), [CtLiteralImpl]"robocopy.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Scala and SbtScala parser on separate output files: the build should report 2+3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllScalaIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtLiteralImpl]2 + [CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Scala(), [CtLiteralImpl]"scalac.txt", [CtLiteralImpl]"sbtScalac.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Sphinx build parser on an output file: the build should report 6 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllSphinxIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]6, [CtConstructorCallImpl]new [CtTypeReferenceImpl]SphinxBuild(), [CtLiteralImpl]"sphinxbuild.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Idea Inspection parser on an output file that contains 1 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIdeaInspectionIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1, [CtConstructorCallImpl]new [CtTypeReferenceImpl]IdeaInspection(), [CtLiteralImpl]"IdeaInspectionExample.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Intel parser on an output file that contains 7 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllIntelIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]7, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Intel(), [CtLiteralImpl]"intelc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Oracle Invalids parser on an output file that contains 3 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllInvalidsIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]3, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Invalids(), [CtLiteralImpl]"invalids.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Java parser on several output files that contain 2 + 1 + 1 + 1 + 2 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllJavaIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]2 + [CtLiteralImpl]1) + [CtLiteralImpl]1) + [CtLiteralImpl]1) + [CtLiteralImpl]2, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Java(), [CtLiteralImpl]"javac.txt", [CtLiteralImpl]"gradle.java.log", [CtLiteralImpl]"gradle.another.java.log", [CtLiteralImpl]"ant-javac.txt", [CtLiteralImpl]"hpi.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Kotlin parser on several output files that contain 1 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllKotlinIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]1, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Kotlin(), [CtLiteralImpl]"kotlin.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the CssLint parser on an output file that contains 51 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllCssLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]51, [CtConstructorCallImpl]new [CtTypeReferenceImpl]CssLint(), [CtLiteralImpl]"csslint.xml");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the DiabC parser on an output file that contains 12 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDiabCIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]12, [CtConstructorCallImpl]new [CtTypeReferenceImpl]DiabC(), [CtLiteralImpl]"diabc.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Doxygen parser on an output file that contains 18 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDoxygenIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]18, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Doxygen(), [CtLiteralImpl]"doxygen.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the Dr. Memory parser on an output file that contains 8 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllDrMemoryIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]8, [CtConstructorCallImpl]new [CtTypeReferenceImpl]DrMemory(), [CtLiteralImpl]"drmemory.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the PVS-Studio parser on an output file that contains 33 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllPVSStudioIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]33, [CtConstructorCallImpl]new [CtTypeReferenceImpl]PVSStudio(), [CtLiteralImpl]"TestReport.plog");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the JavaC parser on an output file of the Eclipse compiler: the build should report no issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindNoJavacIssuesInEclipseOutput() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]0, [CtConstructorCallImpl]new [CtTypeReferenceImpl]Java(), [CtLiteralImpl]"eclipse.txt");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Runs the ProtoLint parser on an output file that contains 10 issues.
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFindAllProtoLintIssues() [CtBlockImpl]{
        [CtInvocationImpl]shouldFindIssuesOfTool([CtLiteralImpl]10, [CtConstructorCallImpl]new [CtTypeReferenceImpl]ProtoLint(), [CtLiteralImpl]"protolint.txt");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"illegalcatch", [CtLiteralImpl]"OverlyBroadCatchBlock", [CtLiteralImpl]"PMD.LinguisticNaming" })
    private [CtTypeReferenceImpl]edu.hm.hafner.analysis.Report shouldFindIssuesOfTool([CtParameterImpl]final [CtTypeReferenceImpl]int expectedSizeOfIssues, [CtParameterImpl]final [CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.ReportScanningTool tool, [CtParameterImpl]final java.lang.String... fileNames) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jenkinsci.plugins.workflow.job.WorkflowJob job = [CtInvocationImpl]createPipeline();
            [CtInvocationImpl]copyMultipleFilesToWorkspace([CtVariableReadImpl]job, [CtVariableReadImpl]fileNames);
            [CtInvocationImpl][CtVariableReadImpl]job.setDefinition([CtInvocationImpl]asStage([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"recordIssues tool: %s(pattern:'**/%s', reportEncoding:'UTF-8')", [CtInvocationImpl][CtVariableReadImpl]tool.getSymbolName(), [CtInvocationImpl]createPatternFor([CtVariableReadImpl]fileNames))));
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.jenkins.plugins.analysis.core.model.AnalysisResult result = [CtInvocationImpl]scheduleSuccessfulBuild([CtVariableReadImpl]job);
            [CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]result).hasTotalSize([CtVariableReadImpl]expectedSizeOfIssues);
            [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getIssues()).hasSize([CtVariableReadImpl]expectedSizeOfIssues);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.hm.hafner.analysis.Report report = [CtInvocationImpl][CtVariableReadImpl]result.getIssues();
            [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]report.filter([CtLambdaImpl]([CtParameterImpl] issue) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]issue.getOrigin().equals([CtInvocationImpl][CtVariableReadImpl]tool.getActualId()))).hasSize([CtVariableReadImpl]expectedSizeOfIssues);
            [CtReturnImpl]return [CtVariableReadImpl]report;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception exception) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError([CtVariableReadImpl]exception);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String createPatternFor([CtParameterImpl]final java.lang.String... fileNames) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]fileNames).map([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtBinaryOperatorImpl][CtLiteralImpl]"**/" + [CtVariableReadImpl]s).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
    }
}
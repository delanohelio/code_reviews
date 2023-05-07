[CompilationUnitImpl][CtPackageDeclarationImpl]package org.csanchez.jenkins.plugins.kubernetes;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.kohsuke.stapler.DataBoundConstructor;
[CtUnresolvedImport]import com.cloudbees.plugins.credentials.common.StandardCredentials;
[CtUnresolvedImport]import org.jenkinsci.plugins.kubernetes.auth.KubernetesAuthConfig;
[CtUnresolvedImport]import org.kohsuke.stapler.QueryParameter;
[CtUnresolvedImport]import jenkins.tasks.SimpleBuildWrapper;
[CtUnresolvedImport]import org.jenkinsci.Symbol;
[CtUnresolvedImport]import com.cloudbees.plugins.credentials.CredentialsMatchers;
[CtUnresolvedImport]import hudson.security.ACL;
[CtUnresolvedImport]import org.kohsuke.stapler.AncestorInPath;
[CtUnresolvedImport]import org.jenkinsci.plugins.kubernetes.auth.KubernetesAuth;
[CtUnresolvedImport]import hudson.model.Item;
[CtUnresolvedImport]import static com.google.common.collect.Sets.newHashSet;
[CtUnresolvedImport]import hudson.Extension;
[CtUnresolvedImport]import hudson.EnvVars;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import jenkins.authentication.tokens.api.AuthenticationTokens;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import hudson.Util;
[CtUnresolvedImport]import hudson.AbortException;
[CtUnresolvedImport]import hudson.model.TaskListener;
[CtUnresolvedImport]import org.jenkinsci.plugins.kubernetes.auth.KubernetesAuthException;
[CtUnresolvedImport]import hudson.util.ListBoxModel;
[CtUnresolvedImport]import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
[CtUnresolvedImport]import hudson.tasks.BuildWrapperDescriptor;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import hudson.Launcher;
[CtImportImpl]import java.io.ByteArrayOutputStream;
[CtUnresolvedImport]import com.cloudbees.plugins.credentials.domains.URIRequirementBuilder;
[CtUnresolvedImport]import hudson.model.Run;
[CtUnresolvedImport]import hudson.FilePath;
[CtUnresolvedImport]import com.cloudbees.plugins.credentials.CredentialsProvider;
[CtUnresolvedImport]import hudson.model.AbstractProject;
[CtImportImpl]import java.io.Writer;
[CtImportImpl]import java.io.OutputStreamWriter;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class KubectlBuildWrapper extends [CtTypeReferenceImpl]jenkins.tasks.SimpleBuildWrapper {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String serverUrl;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String credentialsId;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String caCertificate;

    [CtConstructorImpl][CtAnnotationImpl]@org.kohsuke.stapler.DataBoundConstructor
    public KubectlBuildWrapper([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.lang.String serverUrl, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.lang.String credentialsId, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]java.lang.String caCertificate) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serverUrl = [CtVariableReadImpl]serverUrl;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.credentialsId = [CtInvocationImpl][CtTypeAccessImpl]hudson.Util.fixEmpty([CtVariableReadImpl]credentialsId);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.caCertificate = [CtInvocationImpl][CtTypeAccessImpl]hudson.Util.fixEmptyAndTrim([CtVariableReadImpl]caCertificate);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getServerUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]serverUrl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCredentialsId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]credentialsId;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCaCertificate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]caCertificate;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.Object readResolve() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.credentialsId = [CtInvocationImpl][CtTypeAccessImpl]hudson.Util.fixEmpty([CtFieldReadImpl]credentialsId);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.caCertificate = [CtInvocationImpl][CtTypeAccessImpl]hudson.Util.fixEmptyAndTrim([CtFieldReadImpl]caCertificate);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setUp([CtParameterImpl][CtTypeReferenceImpl]org.csanchez.jenkins.plugins.kubernetes.Context context, [CtParameterImpl][CtTypeReferenceImpl]hudson.model.Run<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> build, [CtParameterImpl][CtTypeReferenceImpl]hudson.FilePath workspace, [CtParameterImpl][CtTypeReferenceImpl]hudson.Launcher launcher, [CtParameterImpl][CtTypeReferenceImpl]hudson.model.TaskListener listener, [CtParameterImpl][CtTypeReferenceImpl]hudson.EnvVars initialEnvironment) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]credentialsId == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]hudson.AbortException([CtLiteralImpl]"No credentials defined to setup Kubernetes CLI");
        }
        [CtInvocationImpl][CtVariableReadImpl]workspace.mkdirs();
        [CtLocalVariableImpl][CtTypeReferenceImpl]hudson.FilePath configFile = [CtInvocationImpl][CtVariableReadImpl]workspace.createTempFile([CtLiteralImpl]".kube", [CtLiteralImpl]"config");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> tempFiles = [CtInvocationImpl]newHashSet([CtInvocationImpl][CtVariableReadImpl]configFile.getRemote());
        [CtInvocationImpl][CtVariableReadImpl]context.env([CtLiteralImpl]"KUBECONFIG", [CtInvocationImpl][CtVariableReadImpl]configFile.getRemote());
        [CtInvocationImpl][CtVariableReadImpl]context.setDisposer([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.csanchez.jenkins.plugins.kubernetes.KubectlBuildWrapper.CleanupDisposer([CtVariableReadImpl]tempFiles));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.cloudbees.plugins.credentials.common.StandardCredentials credentials = [CtInvocationImpl][CtTypeAccessImpl]com.cloudbees.plugins.credentials.CredentialsProvider.findCredentialById([CtFieldReadImpl]credentialsId, [CtFieldReadImpl]com.cloudbees.plugins.credentials.common.StandardCredentials.class, [CtVariableReadImpl]build, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]credentials == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]hudson.AbortException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"No credentials found for id \"" + [CtFieldReadImpl]credentialsId) + [CtLiteralImpl]"\"");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jenkinsci.plugins.kubernetes.auth.KubernetesAuth auth = [CtInvocationImpl][CtTypeAccessImpl]jenkins.authentication.tokens.api.AuthenticationTokens.convert([CtFieldReadImpl]org.jenkinsci.plugins.kubernetes.auth.KubernetesAuth.class, [CtVariableReadImpl]credentials);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]auth == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]hudson.AbortException([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported Credentials type " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]credentials.getClass().getName());
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Writer w = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.OutputStreamWriter([CtInvocationImpl][CtVariableReadImpl]configFile.write())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]w.write([CtInvocationImpl][CtVariableReadImpl]auth.buildKubeConfig([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jenkinsci.plugins.kubernetes.auth.KubernetesAuthConfig([CtInvocationImpl]getServerUrl(), [CtInvocationImpl]getCaCertificate(), [CtBinaryOperatorImpl][CtInvocationImpl]getCaCertificate() == [CtLiteralImpl]null)));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.jenkinsci.plugins.kubernetes.auth.KubernetesAuthException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]hudson.AbortException([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayOutputStream out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayOutputStream();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.ByteArrayOutputStream err = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayOutputStream();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cmd = [CtLiteralImpl]"kubectl version";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int status = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]launcher.launch().cmdAsSingleString([CtVariableReadImpl]cmd).stdout([CtVariableReadImpl]out).stderr([CtVariableReadImpl]err).quiet([CtLiteralImpl]true).envs([CtBinaryOperatorImpl][CtLiteralImpl]"KUBECONFIG=" + [CtInvocationImpl][CtVariableReadImpl]configFile.getRemote()).join();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]status != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder msgBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"Failed to run \"").append([CtVariableReadImpl]cmd).append([CtLiteralImpl]"\". Returned status code ").append([CtVariableReadImpl]status).append([CtLiteralImpl]".\n");
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msgBuilder.append([CtLiteralImpl]"stdout:\n").append([CtVariableReadImpl]out).append([CtLiteralImpl]"\n");
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msgBuilder.append([CtLiteralImpl]"stderr:\n").append([CtVariableReadImpl]err);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]hudson.AbortException([CtInvocationImpl][CtVariableReadImpl]msgBuilder.toString());
        }
    }

    [CtClassImpl][CtAnnotationImpl]@hudson.Extension
    [CtAnnotationImpl]@org.jenkinsci.Symbol([CtLiteralImpl]"kubectl")
    public static class DescriptorImpl extends [CtTypeReferenceImpl]hudson.tasks.BuildWrapperDescriptor {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isApplicable([CtParameterImpl][CtTypeReferenceImpl]hudson.model.AbstractProject<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> item) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getDisplayName() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"Setup Kubernetes CLI (kubectl)";
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]hudson.util.ListBoxModel doFillCredentialsIdItems([CtParameterImpl][CtAnnotationImpl]@org.kohsuke.stapler.AncestorInPath
        [CtTypeReferenceImpl]hudson.model.Item item, [CtParameterImpl][CtAnnotationImpl]@org.kohsuke.stapler.QueryParameter
        [CtTypeReferenceImpl]java.lang.String serverUrl) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.cloudbees.plugins.credentials.common.StandardListBoxModel result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.cloudbees.plugins.credentials.common.StandardListBoxModel();
            [CtInvocationImpl][CtVariableReadImpl]result.includeEmptyValue();
            [CtInvocationImpl][CtVariableReadImpl]result.includeMatchingAs([CtTypeAccessImpl]ACL.SYSTEM, [CtVariableReadImpl]item, [CtFieldReadImpl]com.cloudbees.plugins.credentials.common.StandardCredentials.class, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.cloudbees.plugins.credentials.domains.URIRequirementBuilder.fromUri([CtVariableReadImpl]serverUrl).build(), [CtInvocationImpl][CtTypeAccessImpl]com.cloudbees.plugins.credentials.CredentialsMatchers.anyOf([CtInvocationImpl][CtTypeAccessImpl]com.cloudbees.plugins.credentials.CredentialsMatchers.instanceOf([CtFieldReadImpl]org.jenkinsci.plugins.kubernetes.credentials.TokenProducer.class), [CtInvocationImpl][CtTypeAccessImpl]jenkins.authentication.tokens.api.AuthenticationTokens.matcher([CtFieldReadImpl]org.jenkinsci.plugins.kubernetes.auth.KubernetesAuth.class)));
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }
    }

    [CtClassImpl]private static class CleanupDisposer extends [CtTypeReferenceImpl]org.csanchez.jenkins.plugins.kubernetes.Disposer {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]3006113419319201358L;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> configFiles;

        [CtConstructorImpl]public CleanupDisposer([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> tempFiles) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configFiles = [CtVariableReadImpl]tempFiles;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void tearDown([CtParameterImpl][CtTypeReferenceImpl]hudson.model.Run<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> build, [CtParameterImpl][CtTypeReferenceImpl]hudson.FilePath workspace, [CtParameterImpl][CtTypeReferenceImpl]hudson.Launcher launcher, [CtParameterImpl][CtTypeReferenceImpl]hudson.model.TaskListener listener) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String configFile : [CtFieldReadImpl]configFiles) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]workspace.child([CtVariableReadImpl]configFile).delete();
            }
        }
    }
}
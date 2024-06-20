[CompilationUnitImpl][CtPackageDeclarationImpl]package oracle.kubernetes.operator.helpers;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.model.ConfigurationConstants;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.Matchers.hasEnvVarRegEx;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createAffinity;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static org.hamcrest.Matchers.not;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1Toleration;
[CtUnresolvedImport]import oracle.kubernetes.operator.TuningParameters;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1JobSpec;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.DomainConfiguratorFactory;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1SecurityContext;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1EnvVar;
[CtUnresolvedImport]import oracle.kubernetes.operator.LabelConstants;
[CtUnresolvedImport]import static org.hamcrest.Matchers.nullValue;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import oracle.kubernetes.operator.utils.WlsDomainConfigSupport;
[CtUnresolvedImport]import oracle.kubernetes.operator.work.Component;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.Matchers.hasEnvVar;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createContainer;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import static org.hamcrest.junit.MatcherAssert.assertThat;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import static org.hamcrest.Matchers.notNullValue;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import static org.hamcrest.Matchers.allOf;
[CtImportImpl]import java.lang.reflect.Method;
[CtImportImpl]import java.lang.reflect.InvocationTargetException;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.model.Domain;
[CtUnresolvedImport]import static org.hamcrest.Matchers.contains;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import com.meterware.simplestub.Memento;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.model.DomainSpec;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1Affinity;
[CtUnresolvedImport]import static oracle.kubernetes.operator.ProcessingConstants.DOMAIN_TOPOLOGY;
[CtUnresolvedImport]import oracle.kubernetes.utils.TestUtils;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1PodSecurityContext;
[CtUnresolvedImport]import oracle.kubernetes.operator.work.Packet;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1Job;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.equalTo;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1LocalObjectReference;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.DomainConfigurator;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.Matchers.hasContainer;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createSecretKeyRefEnvVar;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1ObjectMeta;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createSecurityContext;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1Container;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1PodReadinessGate;
[CtUnresolvedImport]import static org.hamcrest.Matchers.empty;
[CtUnresolvedImport]import static org.hamcrest.Matchers.hasEntry;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createToleration;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.ServerConfigurator;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.model.ServerEnvVars;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.model.DomainValidationBaseTest;
[CtUnresolvedImport]import static oracle.kubernetes.operator.DomainProcessorTestSetup.NS;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.Matchers.hasVolumeMount;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createConfigMapKeyRefEnvVar;
[CtUnresolvedImport]import static org.hamcrest.Matchers.hasItem;
[CtUnresolvedImport]import io.kubernetes.client.openapi.models.V1PodSpec;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createFieldRefEnvVar;
[CtUnresolvedImport]import org.hamcrest.Matcher;
[CtUnresolvedImport]import oracle.kubernetes.operator.helpers.JobHelper.DomainIntrospectorJobStepContext;
[CtUnresolvedImport]import static oracle.kubernetes.operator.DomainProcessorTestSetup.createTestDomain;
[CtUnresolvedImport]import static org.hamcrest.Matchers.anEmptyMap;
[CtUnresolvedImport]import oracle.kubernetes.operator.ProcessingConstants;
[CtUnresolvedImport]import static oracle.kubernetes.operator.DomainProcessorTestSetup.UID;
[CtUnresolvedImport]import oracle.kubernetes.weblogic.domain.ClusterConfigurator;
[CtUnresolvedImport]import static oracle.kubernetes.operator.helpers.PodHelperTestBase.createPodSecurityContext;
[CtClassImpl][CtCommentImpl]// todo add domain uid and created by operator labels to pod template so that they can be watched
[CtCommentImpl]// todo have pod processor able to recognize job-created pods to update domain status
public class JobHelperTest extends [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.model.DomainValidationBaseTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String RAW_VALUE_1 = [CtLiteralImpl]"find uid1 at $(DOMAIN_HOME)";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String END_VALUE_1 = [CtLiteralImpl]"find uid1 at /u01/oracle/user_projects/domains";

    [CtFieldImpl][CtJavaDocImpl]/**
     * OEVN is the name of an env var that contains a comma-separated list of oper supplied env var names.
     * It's used by the Model in Image introspector job to detect env var differences from the last
     * time the job ran.
     */
    private static final [CtTypeReferenceImpl]java.lang.String OEVN = [CtLiteralImpl]"OPERATOR_ENVVAR_NAMES";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.reflect.Method getDomainSpec;

    [CtFieldImpl]private final [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.model.Domain domain = [CtInvocationImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.createTestDomain();

    [CtFieldImpl]private final [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo domainPresenceInfo = [CtInvocationImpl]createDomainPresenceInfo([CtFieldReadImpl]domain);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1PodSecurityContext podSecurityContext = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createPodSecurityContext([CtLiteralImpl]123L);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1SecurityContext containerSecurityContext = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createSecurityContext([CtLiteralImpl]555L);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Affinity podAffinity = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createAffinity();

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Toleration toleration = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createToleration([CtLiteralImpl]"key", [CtLiteralImpl]"Eqauls", [CtLiteralImpl]"value", [CtLiteralImpl]"NoSchedule");

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar configMapKeyRefEnvVar = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createConfigMapKeyRefEnvVar([CtLiteralImpl]"VARIABLE1", [CtLiteralImpl]"my-env", [CtLiteralImpl]"VAR1");

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar secretKeyRefEnvVar = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createSecretKeyRefEnvVar([CtLiteralImpl]"VARIABLE2", [CtLiteralImpl]"my-secret", [CtLiteralImpl]"VAR2");

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar fieldRefEnvVar = [CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createFieldRefEnvVar([CtLiteralImpl]"MY_NODE_IP", [CtLiteralImpl]"status.hostIP");

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.meterware.simplestub.Memento> mementos = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.KubernetesTestSupport testSupport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.KubernetesTestSupport();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Setup test environment.
     *
     * @throws Exception
     * 		if setup fails
     */
    [CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mementos.add([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.utils.TestUtils.silenceOperatorLogger());
        [CtInvocationImpl][CtFieldReadImpl]mementos.add([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.TuningParametersStub.install());
        [CtInvocationImpl][CtFieldReadImpl]mementos.add([CtInvocationImpl][CtFieldReadImpl]testSupport.install());
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]domain.getSpec().setNodeName([CtLiteralImpl]null);
        [CtInvocationImpl][CtFieldReadImpl]testSupport.defineResources([CtFieldReadImpl]domain);
        [CtInvocationImpl][CtFieldReadImpl]testSupport.addDomainPresenceInfo([CtFieldReadImpl]domainPresenceInfo);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cleanup test environment.
     */
    [CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void tearDown() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mementos.forEach([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Memento::revert);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_whenClusterReplicas_gt_0() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureCluster([CtLiteralImpl]"cluster1").withReplicas([CtLiteralImpl]1);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_false_whenClusterReplicas_is_0() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureCluster([CtLiteralImpl]"cluster1").withReplicas([CtLiteralImpl]0);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_whenDomainReplicas_gt_0_and_cluster_has_no_replicas() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDefaultReplicaCount([CtLiteralImpl]1);
        [CtInvocationImpl]configureCluster([CtLiteralImpl]"cluster1");
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_false_whenDomainReplicas_is_0_and_cluster_has_no_replicas() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDefaultReplicaCount([CtLiteralImpl]0);
        [CtInvocationImpl]configureCluster([CtLiteralImpl]"cluster1");
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_false_when_no_domain_nor_cluster_replicas() [CtBlockImpl]{
        [CtInvocationImpl]configureCluster([CtLiteralImpl]"cluster1");
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_false_when_noCluster_and_Start_Never_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_when_noCluster_and_Start_If_Needed_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDefaultServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_IF_NEEDED);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_when_noCluster_and_Start_Always_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDefaultServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_ALWAYS);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_false_when_server_with_Start_Never_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureServer([CtLiteralImpl]"managed-server1").withServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_NEVER);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_when_server_with_Start_If_Needed_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureServer([CtLiteralImpl]"managed-server1").withServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_IF_NEEDED);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creatingServers_true_when_server_with_Start_Always_startPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureServer([CtLiteralImpl]"managed-server1").withServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_ALWAYS);
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.creatingServers([CtFieldReadImpl]domainPresenceInfo), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentItems_introspectorPodStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtLiteralImpl]"item1", [CtLiteralImpl]"value1").withEnvironmentVariable([CtLiteralImpl]"item2", [CtLiteralImpl]"value2").withEnvironmentVariable([CtLiteralImpl]"WL_HOME", [CtLiteralImpl]"/u01/custom_wl_home/").withEnvironmentVariable([CtLiteralImpl]"MW_HOME", [CtLiteralImpl]"/u01/custom_mw_home/");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item1", [CtLiteralImpl]"value1"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item2", [CtLiteralImpl]"value2"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"WL_HOME", [CtLiteralImpl]"/u01/custom_wl_home/"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"MW_HOME", [CtLiteralImpl]"/u01/custom_mw_home/")));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item1"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item2"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"WL_HOME"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"MW_HOME")));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec createJobSpec() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.work.Packet packet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]oracle.kubernetes.operator.work.Packet();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]packet.getComponents().put([CtTypeAccessImpl]ProcessingConstants.DOMAIN_COMPONENT_NAME, [CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.work.Component.createFor([CtFieldReadImpl]domainPresenceInfo));
        [CtLocalVariableImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.JobHelper.DomainIntrospectorJobStepContext domainIntrospectorJobStepContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.JobHelper.DomainIntrospectorJobStepContext([CtVariableReadImpl]packet);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]domainIntrospectorJobStepContext.createJobSpec([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.TuningParameters.getInstance());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodStartsWithDefaultUser_Mem_Args_environmentVariable() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"USER_MEM_ARGS", [CtLiteralImpl]"-Djava.security.egd=file:/dev/./urandom"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEmptyStringUser_Mem_Args_EnvironmentItem_introspectorPodStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtLiteralImpl]"USER_MEM_ARGS", [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"USER_MEM_ARGS", [CtLiteralImpl]""));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"USER_MEM_ARGS"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentItemsWithVariables_introspectorPodStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtLiteralImpl]"item1", [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.RAW_VALUE_1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item1", [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.END_VALUE_1));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item1"));
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EMPTY_DATA_HOME = [CtLiteralImpl]"";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentVars_introspectorPodStartupVerifyDataHomeEnvNotDefined() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.DATA_HOME, [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.EMPTY_DATA_HOME)));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OEVN), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtTypeAccessImpl]ServerEnvVars.DATA_HOME))));
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OVERRIDE_DATA_DIR = [CtLiteralImpl]"/u01/data";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OVERRIDE_DATA_HOME = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OVERRIDE_DATA_DIR + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtFieldReadImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.UID;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentVars_introspectorPodStartupVerifyDataHomeEnvDefined() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDataHome([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OVERRIDE_DATA_DIR);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.DATA_HOME, [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OVERRIDE_DATA_HOME));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtTypeAccessImpl]ServerEnvVars.DATA_HOME));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentVars_introspectorPodStartupVerifyEmptyDataHome() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDataHome([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.EMPTY_DATA_HOME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.DATA_HOME, [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.EMPTY_DATA_HOME)));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OEVN), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtTypeAccessImpl]ServerEnvVars.DATA_HOME))));
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NULL_DATA_HOME = [CtLiteralImpl]null;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentVars_introspectorPodStartupVerifyNullDataHome() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDataHome([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.NULL_DATA_HOME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.DATA_HOME, [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.NULL_DATA_HOME)));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenAdminServerHasEnvironmentItems_introspectorPodStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtLiteralImpl]"item1", [CtLiteralImpl]"domain-value1").withEnvironmentVariable([CtLiteralImpl]"item2", [CtLiteralImpl]"domain-value2").configureAdminServer().withEnvironmentVariable([CtLiteralImpl]"item2", [CtLiteralImpl]"admin-value2").withEnvironmentVariable([CtLiteralImpl]"item3", [CtLiteralImpl]"admin-value3");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item1", [CtLiteralImpl]"domain-value1"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item2", [CtLiteralImpl]"admin-value2"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item3", [CtLiteralImpl]"admin-value3")));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item1"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item2"), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"item3")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasValueFromEnvironmentItems_introspectorPodStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtFieldReadImpl]configMapKeyRefEnvVar).withEnvironmentVariable([CtFieldReadImpl]secretKeyRefEnvVar).withEnvironmentVariable([CtFieldReadImpl]fieldRefEnvVar);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]configMapKeyRefEnvVar), [CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]secretKeyRefEnvVar), [CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]fieldRefEnvVar)));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]configMapKeyRefEnvVar.getName()), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]secretKeyRefEnvVar.getName()), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]fieldRefEnvVar.getName())));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenAdminServerHasValueFromEnvironmentItems_introspectorPodStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().configureAdminServer().withEnvironmentVariable([CtFieldReadImpl]configMapKeyRefEnvVar).withEnvironmentVariable([CtFieldReadImpl]secretKeyRefEnvVar).withEnvironmentVariable([CtFieldReadImpl]fieldRefEnvVar);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]configMapKeyRefEnvVar), [CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]secretKeyRefEnvVar), [CtInvocationImpl]Matchers.hasItem([CtFieldReadImpl]fieldRefEnvVar)));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]configMapKeyRefEnvVar.getName()), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]secretKeyRefEnvVar.getName()), [CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtInvocationImpl][CtFieldReadImpl]fieldRefEnvVar.getName())));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodStartupWithNullAdminUsernamePasswordEnvVarValues() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"ADMIN_USERNAME", [CtLiteralImpl]null), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"ADMIN_PASSWORD", [CtLiteralImpl]null)));
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]Matchers.allOf([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OEVN), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"ADMIN_USERNAME")), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.envVarOEVNContains([CtLiteralImpl]"ADMIN_PASSWORD"))));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasEnvironmentItemsWithVariable_createIntrospectorPodShouldNotChangeItsValue() throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.IllegalAccessException, [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.DomainConfigurator domainConfigurator = [CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtLiteralImpl]"item1", [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.RAW_VALUE_1);
        [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getConfiguredDomainSpec([CtVariableReadImpl]domainConfigurator).getEnv(), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtLiteralImpl]"item1", [CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.RAW_VALUE_1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasAdditionalVolumesWithReservedVariables_createIntrospectorPodWithSubstitutions() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withAdditionalVolumeMount([CtLiteralImpl]"volume2", [CtLiteralImpl]"/source-$(DOMAIN_UID)");
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]job.getSpec().getTemplate().getSpec().getContainers().get([CtLiteralImpl]0).getVolumeMounts(), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasVolumeMount([CtLiteralImpl]"volume2", [CtBinaryOperatorImpl][CtLiteralImpl]"/source-" + [CtFieldReadImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.UID));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasAdditionalVolumesWithCustomVariables_createIntrospectorPodWithSubstitutions() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.SECRET_NAME, [CtTypeAccessImpl]KubernetesResourceType.Secret, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.OVERRIDES_CM_NAME_MODEL, [CtTypeAccessImpl]KubernetesResourceType.ConfigMap, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.OVERRIDES_CM_NAME_IMAGE, [CtTypeAccessImpl]KubernetesResourceType.ConfigMap, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.ENV_NAME1, [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.GOOD_MY_ENV_VALUE).withWebLogicCredentialsSecret([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.SECRET_NAME, [CtLiteralImpl]null).withAdditionalVolume([CtLiteralImpl]"volume1", [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.VOLUME_PATH_1).withAdditionalVolumeMount([CtLiteralImpl]"volume1", [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.VOLUME_MOUNT_PATH_1);
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]job.getSpec().getTemplate().getSpec().getContainers().get([CtLiteralImpl]0).getVolumeMounts(), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasVolumeMount([CtLiteralImpl]"volume1", [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.END_VOLUME_MOUNT_PATH_1));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasAdditionalVolumesWithCustomVariablesInvalidValue_reportValidationError() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.SECRET_NAME, [CtTypeAccessImpl]KubernetesResourceType.Secret, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.OVERRIDES_CM_NAME_MODEL, [CtTypeAccessImpl]KubernetesResourceType.ConfigMap, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtInvocationImpl][CtFieldReadImpl]resourceLookup.defineResource([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.OVERRIDES_CM_NAME_IMAGE, [CtTypeAccessImpl]KubernetesResourceType.ConfigMap, [CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar envVar = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar().name([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.ENV_NAME1).value([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.BAD_MY_ENV_VALUE);
        [CtInvocationImpl][CtFieldReadImpl]testSupport.addToPacket([CtTypeAccessImpl]ProcessingConstants.ENVVARS, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]envVar));
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]configureDomain().withEnvironmentVariable([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.ENV_NAME1, [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.BAD_MY_ENV_VALUE).withWebLogicCredentialsSecret([CtTypeAccessImpl]oracle.kubernetes.operator.helpers.SECRET_NAME, [CtLiteralImpl]null).withAdditionalVolume([CtLiteralImpl]"volume1", [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.VOLUME_PATH_1).withAdditionalVolumeMount([CtLiteralImpl]"volume1", [CtTypeAccessImpl]oracle.kubernetes.operator.helpers.VOLUME_MOUNT_PATH_1);
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]testSupport.getResources([CtTypeAccessImpl]KubernetesTestSupport.POD).isEmpty(), [CtInvocationImpl][CtTypeAccessImpl]org.hamcrest.Matchers.is([CtLiteralImpl]true));
        [CtInvocationImpl]assertThat([CtFieldReadImpl]job, [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl]Matchers.nullValue()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void verify_introspectorPodSpec_activeDeadlineSeconds_initial_values() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.getActiveDeadlineSeconds([CtVariableReadImpl]jobSpec), [CtInvocationImpl]CoreMatchers.is([CtTypeAccessImpl]TuningParametersStub.INTROSPECTOR_JOB_ACTIVE_DEADLINE_SECONDS));
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]jobSpec.getActiveDeadlineSeconds(), [CtInvocationImpl]CoreMatchers.is([CtTypeAccessImpl]TuningParametersStub.INTROSPECTOR_JOB_ACTIVE_DEADLINE_SECONDS));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.Long getActiveDeadlineSeconds([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.getTemplateSpec([CtVariableReadImpl]jobSpec).getActiveDeadlineSeconds();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1PodSpec getTemplateSpec([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobSpec.getTemplate().getSpec();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void verify_introspectorPodSpec_activeDeadlineSeconds_retry_values() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int failureCount = [CtInvocationImpl][CtFieldReadImpl]domainPresenceInfo.incrementAndGetFailureCount();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long expectedActiveDeadlineSeconds = [CtBinaryOperatorImpl][CtFieldReadImpl]TuningParametersStub.INTROSPECTOR_JOB_ACTIVE_DEADLINE_SECONDS + [CtBinaryOperatorImpl]([CtVariableReadImpl]failureCount * [CtFieldReadImpl]JobStepContext.DEFAULT_ACTIVE_DEADLINE_INCREMENT_SECONDS);
        [CtInvocationImpl]assertThat([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.getActiveDeadlineSeconds([CtVariableReadImpl]jobSpec), [CtInvocationImpl]CoreMatchers.is([CtVariableReadImpl]expectedActiveDeadlineSeconds));
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]jobSpec.getActiveDeadlineSeconds(), [CtInvocationImpl]CoreMatchers.is([CtVariableReadImpl]expectedActiveDeadlineSeconds));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void verify_introspectorPodSpec_activeDeadlineSeconds_domain_overrides_values() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withIntrospectorJobActiveDeadlineSeconds([CtLiteralImpl]600L);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]oracle.kubernetes.operator.helpers.JobHelperTest.getActiveDeadlineSeconds([CtVariableReadImpl]jobSpec), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]600L));
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]jobSpec.getActiveDeadlineSeconds(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]600L));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void podTemplate_hasCreateByOperatorLabel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getTemplateLabel([CtVariableReadImpl]jobSpec, [CtTypeAccessImpl]LabelConstants.CREATEDBYOPERATOR_LABEL), [CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]"true"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void podTemplate_hasDomainUidLabel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getTemplateLabel([CtVariableReadImpl]jobSpec, [CtTypeAccessImpl]LabelConstants.DOMAINUID_LABEL), [CtInvocationImpl]CoreMatchers.equalTo([CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.UID));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void podTemplate_hasJobNameLabel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getTemplateLabel([CtVariableReadImpl]jobSpec, [CtTypeAccessImpl]LabelConstants.JOBNAME_LABEL), [CtInvocationImpl]CoreMatchers.equalTo([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.LegalNames.toJobIntrospectorName([CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.UID)));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getTemplateLabel([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String labelKey) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtVariableReadImpl]jobSpec.getTemplate()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1PodTemplateSpec::getMetadata).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1ObjectMeta::getLabels).map([CtLambdaImpl]([CtParameterImpl] m) -> [CtInvocationImpl][CtVariableReadImpl]m.get([CtVariableReadImpl]labelKey)).orElse([CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodSpec_alwaysCreatedWithNeverRestartPolicy() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withRestartPolicy([CtLiteralImpl]"Always");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getRestartPolicy(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]"Never"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodSpec_createdWithoutConfiguredReadinessGates() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withReadinessGate([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1PodReadinessGate().conditionType([CtLiteralImpl]"www.example.com/feature-1"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getReadinessGates(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodSpec_createdWithoutConfiguredInitContainers() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withInitContainer([CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createContainer([CtLiteralImpl]"container1", [CtLiteralImpl]"busybox", [CtLiteralImpl]"sh", [CtLiteralImpl]"-c", [CtLiteralImpl]"echo managed server && sleep 120"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getInitContainers(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodSpec_createdWithoutConfiguredContainers() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withContainer([CtInvocationImpl]oracle.kubernetes.operator.helpers.PodHelperTestBase.createContainer([CtLiteralImpl]"container1", [CtLiteralImpl]"busybox", [CtLiteralImpl]"sh", [CtLiteralImpl]"-c", [CtLiteralImpl]"echo managed server && sleep 120"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getContainers(), [CtInvocationImpl]Matchers.not([CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasContainer([CtLiteralImpl]"container1", [CtLiteralImpl]"busybox", [CtLiteralImpl]"sh", [CtLiteralImpl]"-c", [CtLiteralImpl]"echo admin server && sleep 120")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void introspectorPodContainerSpec_hasJobNameAsContainerName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getMatchingContainer([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1Container::getName).orElse([CtLiteralImpl]null), [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.createJobName([CtTypeAccessImpl]oracle.kubernetes.operator.DomainProcessorTestSetup.UID)));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasContainerSecurityContext_introspectorPodContainersStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withContainerSecurityContext([CtFieldReadImpl]containerSecurityContext);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl][CtInvocationImpl]getContainerStream([CtVariableReadImpl]jobSpec).forEach([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]c.getSecurityContext(), [CtInvocationImpl]is([CtFieldReadImpl][CtFieldReferenceImpl]containerSecurityContext)));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodContainers_hasEmptySecurityContext() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl][CtInvocationImpl]getContainerStream([CtVariableReadImpl]jobSpec).forEach([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]c.getSecurityContext(), [CtInvocationImpl]is([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1SecurityContext())));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasPodSecurityContext_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withPodSecurityContext([CtFieldReadImpl]podSecurityContext);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getSecurityContext(), [CtInvocationImpl]CoreMatchers.is([CtFieldReadImpl]podSecurityContext));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasEmptySecurityContext() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getSecurityContext(), [CtInvocationImpl]CoreMatchers.is([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1PodSecurityContext()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasAffinityConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withAffinity([CtFieldReadImpl]podAffinity);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getAffinity(), [CtInvocationImpl]CoreMatchers.is([CtFieldReadImpl]podAffinity));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullAffinity() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getAffinity(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasNodeSelectorConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withNodeSelector([CtLiteralImpl]"os", [CtLiteralImpl]"linux");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getNodeSelector(), [CtInvocationImpl]Matchers.hasEntry([CtLiteralImpl]"os", [CtLiteralImpl]"linux"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasEmptyNodeSelector() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getNodeSelector(), [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl]Matchers.anEmptyMap()));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasNodeNameConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withNodeName([CtLiteralImpl]"kube-02");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getNodeName(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]"kube-02"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullNodeName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getNodeName(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasSchedulerNameConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withSchedulerName([CtLiteralImpl]"my-scheduler");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getSchedulerName(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]"my-scheduler"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullSchedulerName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getSchedulerName(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasRuntimeClassNameConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withRuntimeClassName([CtLiteralImpl]"MyRuntimeClass");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getRuntimeClassName(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]"MyRuntimeClass"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullRuntimeClassName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getRuntimeClassName(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasImagePullSecretsConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1LocalObjectReference imagePullSecret = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1LocalObjectReference().name([CtLiteralImpl]"secret");
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withDefaultImagePullSecrets([CtVariableReadImpl]imagePullSecret);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getImagePullSecrets(), [CtInvocationImpl]Matchers.hasItem([CtVariableReadImpl]imagePullSecret));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasEmptyImagePullSecrets() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getImagePullSecrets(), [CtInvocationImpl]Matchers.empty());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasPriorityClassNameConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withPriorityClassName([CtLiteralImpl]"MyPriorityClass");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getPriorityClassName(), [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]"MyPriorityClass"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullPriorityClassName() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getPriorityClassName(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasTolerationsConfigured_introspectorPodSpecStartupWithThem() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withToleration([CtFieldReadImpl]toleration);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getTolerations(), [CtInvocationImpl]Matchers.contains([CtFieldReadImpl]toleration));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasNullTolerations() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl]getPodSpec([CtVariableReadImpl]jobSpec).getTolerations(), [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenDomainHasHttpAccessLogInLogHomeConfigured_introspectorPodSpecStartupWithIt() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]configureDomain().withHttpAccessLogInLogHome([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.ACCESS_LOG_IN_LOG_HOME, [CtLiteralImpl]"false"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNotConfigured_introspectorPodSpec_hasTrueAccessLogInLogHomeEnvVar() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec = [CtInvocationImpl]createJobSpec();
        [CtInvocationImpl]assertThat([CtInvocationImpl]getMatchingContainerEnv([CtFieldReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec), [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVar([CtTypeAccessImpl]ServerEnvVars.ACCESS_LOG_IN_LOG_HOME, [CtLiteralImpl]"true"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenNoExistingTopologyRunIntrospector() [CtBlockImpl]{
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtFieldReadImpl]job, [CtInvocationImpl]Matchers.notNullValue());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void runCreateJob() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]testSupport.doOnCreate([CtTypeAccessImpl]KubernetesTestSupport.JOB, [CtLambdaImpl]([CtParameterImpl] j) -> [CtInvocationImpl]recordJob([CtVariableReadImpl](([CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Job) (j))));
        [CtInvocationImpl][CtFieldReadImpl]testSupport.runSteps([CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.createDomainIntrospectorJobStep([CtLiteralImpl]null));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenTopologyExistsAndNothingChanged_dontRunIntrospector() [CtBlockImpl]{
        [CtInvocationImpl]defineTopology();
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtFieldReadImpl]job, [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenIntrospectNotRequested_dontRunIntrospector() [CtBlockImpl]{
        [CtInvocationImpl]defineTopology();
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtFieldReadImpl]job, [CtInvocationImpl]Matchers.nullValue());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void whenIntrospectRequestSet_runIntrospector() [CtBlockImpl]{
        [CtInvocationImpl]defineTopology();
        [CtInvocationImpl][CtFieldReadImpl]testSupport.addToPacket([CtTypeAccessImpl]ProcessingConstants.DOMAIN_INTROSPECT_REQUESTED, [CtLiteralImpl]"123");
        [CtInvocationImpl]runCreateJob();
        [CtInvocationImpl]assertThat([CtFieldReadImpl]job, [CtInvocationImpl]Matchers.notNullValue());
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Job job;

    [CtMethodImpl]private [CtTypeReferenceImpl]void recordJob([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Job job) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.job = [CtVariableReadImpl]job;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void defineTopology() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.utils.WlsDomainConfigSupport configSupport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]oracle.kubernetes.operator.utils.WlsDomainConfigSupport([CtLiteralImpl]"domain");
        [CtInvocationImpl][CtVariableReadImpl]configSupport.addWlsServer([CtLiteralImpl]"admin", [CtLiteralImpl]8045);
        [CtInvocationImpl][CtVariableReadImpl]configSupport.setAdminServerName([CtLiteralImpl]"admin");
        [CtInvocationImpl][CtFieldReadImpl]testSupport.addToPacket([CtTypeAccessImpl]oracle.kubernetes.operator.ProcessingConstants.DOMAIN_TOPOLOGY, [CtInvocationImpl][CtVariableReadImpl]configSupport.createDomainConfig());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo createDomainPresenceInfo([CtParameterImpl][CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.model.Domain domain) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo domainPresenceInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo([CtVariableReadImpl]domain);
        [CtInvocationImpl][CtInvocationImpl]configureDomain([CtVariableReadImpl]domainPresenceInfo).withDefaultServerStartPolicy([CtTypeAccessImpl]ConfigurationConstants.START_NEVER);
        [CtReturnImpl]return [CtVariableReadImpl]domainPresenceInfo;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.DomainConfigurator configureDomain() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]configureDomain([CtFieldReadImpl]domainPresenceInfo);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.DomainConfigurator configureDomain([CtParameterImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo domainPresenceInfo) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.weblogic.domain.DomainConfiguratorFactory.forDomain([CtInvocationImpl][CtVariableReadImpl]domainPresenceInfo.getDomain());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"SameParameterValue")
    private [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.ClusterConfigurator configureCluster([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clusterName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]configureDomain().configureCluster([CtVariableReadImpl]clusterName);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"SameParameterValue")
    private [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.ServerConfigurator configureServer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String serverName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]configureDomain().configureServer([CtVariableReadImpl]serverName);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1PodSpec getPodSpec([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobSpec.getTemplate().getSpec();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Container> getMatchingContainer([CtParameterImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo domainPresenceInfo, [CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getContainerStream([CtVariableReadImpl]jobSpec).filter([CtLambdaImpl]([CtParameterImpl] c) -> [CtInvocationImpl]hasCreateJobName([CtVariableReadImpl]c, [CtInvocationImpl][CtVariableReadImpl]domainPresenceInfo.getDomainUid())).findFirst();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar> getMatchingContainerEnv([CtParameterImpl][CtTypeReferenceImpl]oracle.kubernetes.operator.helpers.DomainPresenceInfo domainPresenceInfo, [CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getMatchingContainer([CtVariableReadImpl]domainPresenceInfo, [CtVariableReadImpl]jobSpec).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1Container::getEnv).orElse([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasCreateJobName([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Container container, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String domainUid) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]oracle.kubernetes.operator.helpers.JobHelper.createJobName([CtVariableReadImpl]domainUid).equals([CtInvocationImpl][CtVariableReadImpl]container.getName());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1Container> getContainerStream([CtParameterImpl][CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1JobSpec jobSpec) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]jobSpec.getTemplate().getSpec()).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]V1PodSpec::getContainers).stream().flatMap([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Collection::stream);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.model.DomainSpec getConfiguredDomainSpec([CtParameterImpl][CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.DomainConfigurator domainConfigurator) throws [CtTypeReferenceImpl]java.lang.NoSuchMethodException, [CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException, [CtTypeReferenceImpl]java.lang.IllegalAccessException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]getDomainSpec == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]getDomainSpec = [CtInvocationImpl][CtFieldReadImpl]oracle.kubernetes.weblogic.domain.DomainConfigurator.class.getDeclaredMethod([CtLiteralImpl]"getDomainSpec");
            [CtInvocationImpl][CtFieldReadImpl]getDomainSpec.setAccessible([CtLiteralImpl]true);
        }
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]oracle.kubernetes.weblogic.domain.model.DomainSpec) ([CtFieldReadImpl]getDomainSpec.invoke([CtVariableReadImpl]domainConfigurator)));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.hamcrest.Matcher<[CtTypeReferenceImpl]java.lang.Iterable<[CtWildcardReferenceImpl]? super [CtTypeReferenceImpl]io.kubernetes.client.openapi.models.V1EnvVar>> envVarOEVNContains([CtParameterImpl][CtTypeReferenceImpl]java.lang.String val) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// OEVN env var contains a comma separated list of env var names
        return [CtInvocationImpl]oracle.kubernetes.operator.helpers.Matchers.hasEnvVarRegEx([CtFieldReadImpl]oracle.kubernetes.operator.helpers.JobHelperTest.OEVN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(^|.*,)" + [CtVariableReadImpl]val) + [CtLiteralImpl]"($|,.*)");
    }
}
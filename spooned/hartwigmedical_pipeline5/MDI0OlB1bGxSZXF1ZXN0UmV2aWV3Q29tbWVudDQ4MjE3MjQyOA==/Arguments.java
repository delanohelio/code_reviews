[CompilationUnitImpl][CtPackageDeclarationImpl]package com.hartwig.pipeline;
[CtUnresolvedImport]import com.hartwig.pipeline.execution.vm.VirtualMachineJobDefinition;
[CtUnresolvedImport]import com.hartwig.pipeline.resource.RefGenomeVersion;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.immutables.value.Value;
[CtInterfaceImpl][CtAnnotationImpl]@org.immutables.value.Value.Immutable
public interface Arguments extends [CtTypeReferenceImpl]com.hartwig.pipeline.CommonArguments {
    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String EMPTY = [CtLiteralImpl]"";

    [CtEnumImpl]enum DefaultsProfile {

        [CtEnumValueImpl]PUBLIC,
        [CtEnumValueImpl]PRODUCTION,
        [CtEnumValueImpl]DEVELOPMENT,
        [CtEnumValueImpl]DEVELOPMENT_DOCKER;}

    [CtMethodImpl][CtTypeReferenceImpl]boolean cleanup();

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.Integer DEFAULT_POLL_INTERVAL = [CtLiteralImpl]5;

    [CtMethodImpl][CtTypeReferenceImpl]boolean runBamMetrics();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runAligner();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runSnpGenotyper();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runGermlineCaller();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runSomaticCaller();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runStructuralCaller();

    [CtMethodImpl][CtTypeReferenceImpl]boolean runTertiary();

    [CtMethodImpl][CtTypeReferenceImpl]boolean shallow();

    [CtMethodImpl][CtTypeReferenceImpl]com.hartwig.pipeline.Arguments.DefaultsProfile profile();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String setId();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String sbpApiUrl();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String rclonePath();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String rcloneGcpRemote();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String rcloneS3RemoteDownload();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String rcloneS3RemoteUpload();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String outputBucket();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String archiveBucket();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String archiveProject();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String archivePrivateKeyPath();

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String uploadPrivateKeyPath();

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> sbpApiRunId();

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> runId();

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> zone();

    [CtMethodImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> sampleJson();

    [CtMethodImpl][CtTypeReferenceImpl]com.hartwig.pipeline.resource.RefGenomeVersion refGenomeVersion();

    [CtMethodImpl][CtTypeReferenceImpl]int maxConcurrentLanes();

    [CtMethodImpl]static [CtTypeReferenceImpl]ImmutableArguments.Builder builder() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.ImmutableArguments.builder();
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]com.hartwig.pipeline.Arguments defaults([CtParameterImpl][CtTypeReferenceImpl]java.lang.String profileString) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]com.hartwig.pipeline.Arguments.defaultsBuilder([CtVariableReadImpl]profileString).build();
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]com.hartwig.pipeline.Arguments testDefaults() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]com.hartwig.pipeline.Arguments.testDefaultsBuilder().build();
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]ImmutableArguments.Builder testDefaultsBuilder() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]com.hartwig.pipeline.Arguments.defaultsBuilder([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.[CtFieldReferenceImpl]DEVELOPMENT.name()).runId([CtLiteralImpl]"test");
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean outputCram();

    [CtMethodImpl][CtTypeReferenceImpl]boolean publishToTurquoise();

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String workingDir() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.dir");
    }

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_RCLONE_PATH = [CtLiteralImpl]"/usr/bin";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_RCLONE_GCP_REMOTE = [CtLiteralImpl]"gs";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_RCLONE_S3_REMOTE = [CtLiteralImpl]"s3";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_PROJECT = [CtLiteralImpl]"hmf-pipeline-prod-e45b00f2";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_SBP_API_URL = [CtLiteralImpl]"http://hmfapi";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_SERVICE_ACCOUNT_EMAIL = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"bootstrap@%s.iam.gserviceaccount.com", [CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_PROJECT);

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_PATIENT_REPORT_BUCKET = [CtLiteralImpl]"pipeline-output-prod";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_ARCHIVE_BUCKET = [CtLiteralImpl]"pipeline-archive-prod";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_PRODUCTION_ARCHIVE_PROJECT = [CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_PROJECT;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DOCKER_KEY_PATH = [CtLiteralImpl]"/secrets/bootstrap-key.json";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DOCKER_ARCHIVE_KEY_PATH = [CtLiteralImpl]"/secrets/archive-key.json";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DOCKER_UPLOAD_KEY_PATH = [CtLiteralImpl]"/secrets/upload-key.json";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DOCKER_CLOUD_SDK_PATH = [CtLiteralImpl]"/usr/lib/google-cloud-sdk/bin";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NOT_APPLICABLE = [CtLiteralImpl]"N/A";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DEVELOPMENT_KEY_PATH = [CtBinaryOperatorImpl][CtInvocationImpl]com.hartwig.pipeline.Arguments.workingDir() + [CtLiteralImpl]"/bootstrap-key.json";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DEVELOPMENT_PATIENT_REPORT_BUCKET = [CtLiteralImpl]"pipeline-output-dev";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEFAULT_DEVELOPMENT_ARCHIVE_BUCKET = [CtLiteralImpl]"pipeline-archive-dev";

    [CtFieldImpl][CtTypeReferenceImpl]com.hartwig.pipeline.resource.RefGenomeVersion DEFAULT_REF_GENOME_VERSION = [CtFieldReadImpl]com.hartwig.pipeline.resource.RefGenomeVersion.HG19;

    [CtFieldImpl][CtTypeReferenceImpl]int DEFAULT_MAX_CONCURRENT_LANES = [CtLiteralImpl]8;

    [CtMethodImpl]static [CtTypeReferenceImpl]ImmutableArguments.Builder defaultsBuilder([CtParameterImpl][CtTypeReferenceImpl]java.lang.String profileString) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hartwig.pipeline.Arguments.DefaultsProfile profile = [CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.valueOf([CtInvocationImpl][CtVariableReadImpl]profileString.toUpperCase());
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profile.equals([CtFieldReadImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.[CtFieldReferenceImpl]PRODUCTION)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.ImmutableArguments.builder().profile([CtVariableReadImpl]profile).rclonePath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_RCLONE_PATH).rcloneGcpRemote([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_RCLONE_GCP_REMOTE).rcloneS3RemoteDownload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_RCLONE_S3_REMOTE).rcloneS3RemoteUpload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_RCLONE_S3_REMOTE).region([CtTypeAccessImpl]CommonArguments.DEFAULT_REGION).project([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_PROJECT).sbpApiUrl([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_SBP_API_URL).privateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_KEY_PATH).serviceAccountEmail([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_SERVICE_ACCOUNT_EMAIL).cloudSdkPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_CLOUD_SDK_PATH).cleanup([CtLiteralImpl]true).usePreemptibleVms([CtLiteralImpl]true).useLocalSsds([CtLiteralImpl]true).runBamMetrics([CtLiteralImpl]true).runAligner([CtLiteralImpl]true).runSnpGenotyper([CtLiteralImpl]true).runGermlineCaller([CtLiteralImpl]true).runSomaticCaller([CtLiteralImpl]true).runStructuralCaller([CtLiteralImpl]true).runTertiary([CtLiteralImpl]true).shallow([CtLiteralImpl]false).setId([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).cmek([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).outputBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_PATIENT_REPORT_BUCKET).archiveBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_ARCHIVE_BUCKET).archiveProject([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_PRODUCTION_ARCHIVE_PROJECT).archivePrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_ARCHIVE_KEY_PATH).uploadPrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_KEY_PATH).network([CtTypeAccessImpl]com.hartwig.pipeline.DEFAULT_NETWORK).outputCram([CtLiteralImpl]true).publishToTurquoise([CtLiteralImpl]false).pollInterval([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_POLL_INTERVAL).refGenomeVersion([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_REF_GENOME_VERSION).maxConcurrentLanes([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_MAX_CONCURRENT_LANES).imageName([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profile.equals([CtFieldReadImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.[CtFieldReferenceImpl]DEVELOPMENT)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.ImmutableArguments.builder().profile([CtVariableReadImpl]profile).region([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_REGION).project([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_PROJECT).cloudSdkPath([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_CLOUD_SDK_PATH).serviceAccountEmail([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_SERVICE_ACCOUNT_EMAIL).cleanup([CtLiteralImpl]true).cmek([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_CMEK).usePreemptibleVms([CtLiteralImpl]true).runBamMetrics([CtLiteralImpl]true).runAligner([CtLiteralImpl]true).runSnpGenotyper([CtLiteralImpl]true).runGermlineCaller([CtLiteralImpl]true).runSomaticCaller([CtLiteralImpl]true).runTertiary([CtLiteralImpl]true).runStructuralCaller([CtLiteralImpl]true).shallow([CtLiteralImpl]false).rclonePath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteDownload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteUpload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneGcpRemote([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).sbpApiUrl([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).setId([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).outputBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_PATIENT_REPORT_BUCKET).archiveBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_ARCHIVE_BUCKET).archiveProject([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_PROJECT).archivePrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_KEY_PATH).uploadPrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_KEY_PATH).outputCram([CtLiteralImpl]true).publishToTurquoise([CtLiteralImpl]false).pollInterval([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_POLL_INTERVAL).refGenomeVersion([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_REF_GENOME_VERSION).maxConcurrentLanes([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_MAX_CONCURRENT_LANES).network([CtTypeAccessImpl]com.hartwig.pipeline.DEFAULT_NETWORK).useLocalSsds([CtLiteralImpl]true).imageName([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profile.equals([CtFieldReadImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.[CtFieldReferenceImpl]DEVELOPMENT_DOCKER)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.ImmutableArguments.builder().profile([CtVariableReadImpl]profile).region([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_REGION).project([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_PROJECT).cloudSdkPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_CLOUD_SDK_PATH).serviceAccountEmail([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_SERVICE_ACCOUNT_EMAIL).cleanup([CtLiteralImpl]true).cmek([CtTypeAccessImpl]com.hartwig.pipeline.DEFAULT_DEVELOPMENT_CMEK).usePreemptibleVms([CtLiteralImpl]true).useLocalSsds([CtLiteralImpl]true).runBamMetrics([CtLiteralImpl]true).runAligner([CtLiteralImpl]true).runSnpGenotyper([CtLiteralImpl]true).runGermlineCaller([CtLiteralImpl]true).runSomaticCaller([CtLiteralImpl]true).runTertiary([CtLiteralImpl]true).runStructuralCaller([CtLiteralImpl]true).shallow([CtLiteralImpl]false).rclonePath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteDownload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteUpload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneGcpRemote([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).sbpApiUrl([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).setId([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).outputBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_PATIENT_REPORT_BUCKET).archiveBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_ARCHIVE_BUCKET).archiveProject([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_PROJECT).archivePrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_KEY_PATH).uploadPrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_UPLOAD_KEY_PATH).network([CtTypeAccessImpl]com.hartwig.pipeline.DEFAULT_NETWORK).outputCram([CtLiteralImpl]true).publishToTurquoise([CtLiteralImpl]false).pollInterval([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_POLL_INTERVAL).refGenomeVersion([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_REF_GENOME_VERSION).maxConcurrentLanes([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_MAX_CONCURRENT_LANES).imageName([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profile.equals([CtFieldReadImpl][CtTypeAccessImpl]com.hartwig.pipeline.Arguments.DefaultsProfile.[CtFieldReferenceImpl]PUBLIC)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.hartwig.pipeline.ImmutableArguments.builder().profile([CtVariableReadImpl]profile).outputBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).region([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).project([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).serviceAccountEmail([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).cloudSdkPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_CLOUD_SDK_PATH).cleanup([CtLiteralImpl]true).usePreemptibleVms([CtLiteralImpl]true).useLocalSsds([CtLiteralImpl]true).runBamMetrics([CtLiteralImpl]true).runAligner([CtLiteralImpl]true).runSnpGenotyper([CtLiteralImpl]true).runGermlineCaller([CtLiteralImpl]true).runSomaticCaller([CtLiteralImpl]true).runTertiary([CtLiteralImpl]true).runStructuralCaller([CtLiteralImpl]true).shallow([CtLiteralImpl]false).rclonePath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteDownload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneS3RemoteUpload([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).rcloneGcpRemote([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).sbpApiUrl([CtFieldReadImpl]com.hartwig.pipeline.Arguments.NOT_APPLICABLE).setId([CtFieldReadImpl]com.hartwig.pipeline.Arguments.EMPTY).archiveBucket([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DEVELOPMENT_ARCHIVE_BUCKET).archiveProject([CtTypeAccessImpl]CommonArguments.DEFAULT_DEVELOPMENT_PROJECT).archivePrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_KEY_PATH).uploadPrivateKeyPath([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_DOCKER_UPLOAD_KEY_PATH).network([CtTypeAccessImpl]com.hartwig.pipeline.DEFAULT_NETWORK).outputCram([CtLiteralImpl]true).publishToTurquoise([CtLiteralImpl]false).pollInterval([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_POLL_INTERVAL).refGenomeVersion([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_REF_GENOME_VERSION).maxConcurrentLanes([CtFieldReadImpl]com.hartwig.pipeline.Arguments.DEFAULT_MAX_CONCURRENT_LANES).imageName([CtTypeAccessImpl]VirtualMachineJobDefinition.PUBLIC_IMAGE_NAME);
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unknown profile [%s], please create defaults for this profile.", [CtVariableReadImpl]profile));
    }
}
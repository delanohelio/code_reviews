[CompilationUnitImpl][CtCommentImpl]/* Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package io.trino.plugin.hive.s3;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import io.airlift.configuration.ConfigDescription;
[CtUnresolvedImport]import org.codehaus.commons.nullanalysis.NotNull;
[CtUnresolvedImport]import io.airlift.configuration.Config;
[CtUnresolvedImport]import io.airlift.configuration.validation.FileExists;
[CtUnresolvedImport]import javax.validation.constraints.AssertTrue;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import io.airlift.units.Duration;
[CtClassImpl]public class S3SecurityMappingConfig {
    [CtFieldImpl]private [CtTypeReferenceImpl]java.io.File configFile;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String configUri;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String jsonPointer = [CtLiteralImpl]"";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String roleCredentialName;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String kmsKeyIdCredentialName;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.airlift.units.Duration refreshPeriod;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String colonReplacement;

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.io.[CtAnnotationImpl]@io.airlift.configuration.validation.FileExists
    File> getConfigFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]configFile);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.config-file")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"JSON configuration file containing security mappings")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setConfigFile([CtParameterImpl][CtTypeReferenceImpl]java.io.File configFile) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configFile = [CtVariableReadImpl]configFile;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> getConfigUri() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]configUri);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.config-uri")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"URI returning the security mappings as JSON")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setConfigUri([CtParameterImpl][CtTypeReferenceImpl]java.lang.String configUri) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configUri = [CtVariableReadImpl]configUri;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.validation.constraints.AssertTrue(message = [CtLiteralImpl]"Cannot set both hive.s3.security-mapping.config-file and hive.s3.security-mapping.config-uri")
    public [CtTypeReferenceImpl]boolean atMostOneProvider() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfigFile().isEmpty() || [CtInvocationImpl][CtInvocationImpl]getConfigUri().isEmpty();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.codehaus.commons.nullanalysis.NotNull
    public [CtTypeReferenceImpl]java.lang.String getJSONPointer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jsonPointer;
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.json-pointer")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"JSON pointer (RFC 6901) to mappings inside JSON config")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setJSONPointer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonPointer) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jsonPointer = [CtVariableReadImpl]jsonPointer;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> getRoleCredentialName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]roleCredentialName);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.iam-role-credential-name")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"Name of the extra credential used to provide IAM role")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setRoleCredentialName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String roleCredentialName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.roleCredentialName = [CtVariableReadImpl]roleCredentialName;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> getKmsKeyIdCredentialName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]kmsKeyIdCredentialName);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.kms-key-id-credential-name")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"Name of the extra credential used to provide KMS Key ID")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setKmsKeyIdCredentialName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String kmsKeyIdCredentialName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kmsKeyIdCredentialName = [CtVariableReadImpl]kmsKeyIdCredentialName;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.airlift.units.Duration> getRefreshPeriod() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]refreshPeriod);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.refresh-period")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"How often to refresh the security mapping configuration")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setRefreshPeriod([CtParameterImpl][CtTypeReferenceImpl]io.airlift.units.Duration refreshPeriod) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.refreshPeriod = [CtVariableReadImpl]refreshPeriod;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> getColonReplacement() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]colonReplacement);
    }

    [CtMethodImpl][CtAnnotationImpl]@io.airlift.configuration.Config([CtLiteralImpl]"hive.s3.security-mapping.colon-replacement")
    [CtAnnotationImpl]@io.airlift.configuration.ConfigDescription([CtLiteralImpl]"Value used in place of colon for IAM role name in extra credentials")
    public [CtTypeReferenceImpl]io.trino.plugin.hive.s3.S3SecurityMappingConfig setColonReplacement([CtParameterImpl][CtTypeReferenceImpl]java.lang.String colonReplacement) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.colonReplacement = [CtVariableReadImpl]colonReplacement;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (C) 2015 Red Hat, Inc.
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
[CtPackageDeclarationImpl]package io.fabric8.kubernetes.client;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonProperty;
[CtImportImpl]import java.util.Locale;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonIgnore;
[CtImportImpl]import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
[CtUnresolvedImport]import io.sundr.builder.annotations.Buildable;
[CtUnresolvedImport]import io.fabric8.kubernetes.client.utils.ApiVersionUtil;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonDeserializer;
[CtUnresolvedImport]import io.fabric8.kubernetes.api.model.HasMetadata;
[CtUnresolvedImport]import io.fabric8.kubernetes.api.model.Namespaced;
[CtUnresolvedImport]import io.fabric8.kubernetes.model.annotation.Singular;
[CtUnresolvedImport]import io.fabric8.kubernetes.api.model.KubernetesResource;
[CtUnresolvedImport]import io.fabric8.kubernetes.client.utils.Pluralize;
[CtUnresolvedImport]import io.fabric8.kubernetes.api.model.ObjectMeta;
[CtUnresolvedImport]import io.fabric8.kubernetes.model.annotation.Plural;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonPropertyOrder;
[CtUnresolvedImport]import static io.fabric8.kubernetes.client.utils.Utils.isNullOrEmpty;
[CtClassImpl][CtJavaDocImpl]/**
 * A base class for implementing a custom resource kind
 */
[CtAnnotationImpl]@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = [CtFieldReadImpl]com.fasterxml.jackson.databind.JsonDeserializer.None.class)
[CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonPropertyOrder([CtNewArrayImpl]{ [CtLiteralImpl]"apiVersion", [CtLiteralImpl]"kind", [CtLiteralImpl]"metadata", [CtLiteralImpl]"spec", [CtLiteralImpl]"status" })
[CtAnnotationImpl]@io.sundr.builder.annotations.Buildable(builderPackage = [CtLiteralImpl]"io.fabric8.kubernetes.api.builder", editableEnabled = [CtLiteralImpl]false)
public abstract class CustomResource<[CtTypeParameterImpl]Spec extends [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.KubernetesResource, [CtTypeParameterImpl]Status extends [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.KubernetesResource> implements [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.HasMetadata {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NAMESPACE_SCOPE = [CtLiteralImpl]"Namespaced";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String CLUSTER_SCOPE = [CtLiteralImpl]"Cluster";

    [CtFieldImpl]private [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.ObjectMeta metadata = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.ObjectMeta();

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"spec")
    private [CtTypeParameterReferenceImpl]Spec spec;

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"status")
    private [CtTypeParameterReferenceImpl]Status status;

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    private [CtTypeReferenceImpl]java.lang.String plural;

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    private [CtTypeReferenceImpl]java.lang.String singular;

    [CtFieldImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    private [CtTypeReferenceImpl]java.lang.String crdName;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String kind;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String apiVersion;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String scope;

    [CtConstructorImpl]public CustomResource() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String version = [CtInvocationImpl][CtSuperAccessImpl][CtTypeAccessImpl][CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.HasMetadata.super.getApiVersion();
        [CtIfImpl]if ([CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.isNullOrEmpty([CtVariableReadImpl]version)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getClass().getName() + [CtLiteralImpl]" CustomResource must provide an API version using @ApiGroup and @ApiVersion annotations");
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.apiVersion = [CtVariableReadImpl]version;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kind = [CtInvocationImpl][CtSuperAccessImpl][CtTypeAccessImpl][CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.HasMetadata.super.getKind();
        [CtAssignmentImpl][CtFieldWriteImpl]scope = [CtConditionalImpl]([CtBinaryOperatorImpl][CtThisAccessImpl]this instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.Namespaced) ? [CtFieldReadImpl]io.fabric8.kubernetes.client.CustomResource.NAMESPACE_SCOPE : [CtFieldReadImpl]io.fabric8.kubernetes.client.CustomResource.CLUSTER_SCOPE;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"CustomResource{" + [CtLiteralImpl]"kind='") + [CtInvocationImpl]getKind()) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", apiVersion='") + [CtInvocationImpl]getApiVersion()) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", metadata=") + [CtFieldReadImpl]metadata) + [CtLiteralImpl]", spec=") + [CtFieldReadImpl]spec) + [CtLiteralImpl]", status=") + [CtFieldReadImpl]status) + [CtLiteralImpl]'}';
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getApiVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]apiVersion;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setApiVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String version) [CtBlockImpl]{
        [CtCommentImpl]// already set in constructor
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getKind() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.kind;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setKind([CtParameterImpl][CtTypeReferenceImpl]java.lang.String kind) [CtBlockImpl]{
        [CtCommentImpl]// already set in constructor
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.ObjectMeta getMetadata() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]metadata;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setMetadata([CtParameterImpl][CtTypeReferenceImpl]io.fabric8.kubernetes.api.model.ObjectMeta metadata) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.metadata = [CtVariableReadImpl]metadata;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getPlural([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.fabric8.kubernetes.client.CustomResource> clazz) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.fabric8.kubernetes.model.annotation.Plural fromAnnotation = [CtInvocationImpl][CtVariableReadImpl]clazz.getAnnotation([CtFieldReadImpl]io.fabric8.kubernetes.model.annotation.Plural.class);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]fromAnnotation != [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromAnnotation.value().toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT) : [CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.client.utils.Pluralize.toPlural([CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getSingular([CtVariableReadImpl]clazz));
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getPlural() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]plural == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.fabric8.kubernetes.model.annotation.Plural fromAnnotation = [CtInvocationImpl][CtInvocationImpl]getClass().getAnnotation([CtFieldReadImpl]io.fabric8.kubernetes.model.annotation.Plural.class);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.plural = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]fromAnnotation != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromAnnotation.value().toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT) : [CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.client.utils.Pluralize.toPlural([CtInvocationImpl]getSingular());
        }
        [CtReturnImpl]return [CtFieldReadImpl]plural;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getSingular([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.fabric8.kubernetes.client.CustomResource> clazz) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.fabric8.kubernetes.model.annotation.Singular fromAnnotation = [CtInvocationImpl][CtVariableReadImpl]clazz.getAnnotation([CtFieldReadImpl]io.fabric8.kubernetes.model.annotation.Singular.class);
        [CtReturnImpl]return [CtInvocationImpl][CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]fromAnnotation != [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromAnnotation.value().toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT) : [CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.api.model.HasMetadata.getKind([CtVariableReadImpl]clazz)).toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getSingular() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]singular == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.fabric8.kubernetes.model.annotation.Singular fromAnnotation = [CtInvocationImpl][CtInvocationImpl]getClass().getAnnotation([CtFieldReadImpl]io.fabric8.kubernetes.model.annotation.Singular.class);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.singular = [CtInvocationImpl][CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]fromAnnotation != [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fromAnnotation.value().toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT) : [CtInvocationImpl]getKind()).toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ROOT);
        }
        [CtReturnImpl]return [CtFieldReadImpl]singular;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getCRDName([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.fabric8.kubernetes.client.CustomResource> clazz) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getPlural([CtVariableReadImpl]clazz) + [CtLiteralImpl]".") + [CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getGroup([CtVariableReadImpl]clazz);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getCRDName() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]crdName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.crdName = [CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getCRDName([CtInvocationImpl]getClass());
        }
        [CtReturnImpl]return [CtFieldReadImpl]crdName;
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getScope() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]scope;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.fabric8.kubernetes.client.CustomResource> clazz) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.client.utils.ApiVersionUtil.trimGroup([CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.api.model.HasMetadata.getApiVersion([CtVariableReadImpl]clazz));
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getGroup() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getGroup([CtInvocationImpl]getClass());
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]io.fabric8.kubernetes.client.CustomResource> clazz) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.client.utils.ApiVersionUtil.trimVersion([CtInvocationImpl][CtTypeAccessImpl]io.fabric8.kubernetes.api.model.HasMetadata.getApiVersion([CtVariableReadImpl]clazz));
    }

    [CtMethodImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    public [CtTypeReferenceImpl]java.lang.String getVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]io.fabric8.kubernetes.client.CustomResource.getVersion([CtInvocationImpl]getClass());
    }

    [CtMethodImpl]public [CtTypeParameterReferenceImpl]Spec getSpec() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]spec;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSpec([CtParameterImpl][CtTypeParameterReferenceImpl]Spec spec) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.spec = [CtVariableReadImpl]spec;
    }

    [CtMethodImpl]public [CtTypeParameterReferenceImpl]Status getStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]status;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStatus([CtParameterImpl][CtTypeParameterReferenceImpl]Status status) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.status = [CtVariableReadImpl]status;
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package io.quarkus.smallrye.graphql.deployment;
[CtUnresolvedImport]import io.quarkus.runtime.annotations.ConfigItem;
[CtUnresolvedImport]import io.quarkus.runtime.annotations.ConfigDocSection;
[CtUnresolvedImport]import io.quarkus.runtime.annotations.ConfigRoot;
[CtClassImpl][CtAnnotationImpl]@io.quarkus.runtime.annotations.ConfigRoot(name = [CtLiteralImpl]"smallrye-graphql")
public class SmallRyeGraphQLConfig {
    [CtFieldImpl][CtJavaDocImpl]/**
     * The rootPath under which queries will be served. Default to /graphql
     */
    [CtAnnotationImpl]@io.quarkus.runtime.annotations.ConfigItem(defaultValue = [CtLiteralImpl]"/graphql")
    [CtTypeReferenceImpl]java.lang.String rootPath;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Enable metrics
     */
    [CtAnnotationImpl]@io.quarkus.runtime.annotations.ConfigItem(name = [CtLiteralImpl]"metrics.enabled", defaultValue = [CtLiteralImpl]"false")
    [CtTypeReferenceImpl]boolean metricsEnabled;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Config group for all UI related options.
     * Configuration properties for UI
     */
    [CtAnnotationImpl]@io.quarkus.runtime.annotations.ConfigItem
    [CtAnnotationImpl]@io.quarkus.runtime.annotations.ConfigDocSection
    [CtTypeReferenceImpl]io.quarkus.smallrye.graphql.deployment.SmallRyeGraphQLUIConfig ui;
}
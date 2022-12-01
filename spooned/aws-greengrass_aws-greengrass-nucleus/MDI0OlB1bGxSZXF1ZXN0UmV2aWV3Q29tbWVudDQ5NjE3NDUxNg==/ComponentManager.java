[CompilationUnitImpl][CtCommentImpl]/* Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
 */
[CtPackageDeclarationImpl]package com.aws.greengrass.componentmanager;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.exceptions.PackageLoadingException;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.exceptions.NoAvailableComponentVersionException;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.plugins.S3Downloader;
[CtUnresolvedImport]import com.aws.greengrass.deployment.model.Deployment;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import com.aws.greengrass.util.Coerce;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.exceptions.InvalidArtifactUriException;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.plugins.GreengrassRepositoryDownloader;
[CtUnresolvedImport]import com.amazon.aws.iot.greengrass.component.common.Unarchive;
[CtUnresolvedImport]import com.vdurmont.semver4j.Requirement;
[CtUnresolvedImport]import com.aws.greengrass.lifecyclemanager.GreengrassService;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import com.vdurmont.semver4j.Semver;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.exceptions.PackageDownloadException;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.plugins.ArtifactDownloader;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import com.aws.greengrass.logging.impl.LogManager;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.exceptions.PackagingException;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.amazonaws.services.evergreen.model.ComponentContent;
[CtUnresolvedImport]import com.aws.greengrass.dependency.InjectionActions;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import static com.aws.greengrass.deployment.converter.DeploymentDocumentConverter.ANY_VERSION;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.aws.greengrass.lifecyclemanager.exceptions.ServiceLoadException;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.models.ComponentArtifact;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import javax.inject.Inject;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.models.ComponentRecipe;
[CtUnresolvedImport]import com.aws.greengrass.config.Topic;
[CtUnresolvedImport]import com.aws.greengrass.lifecyclemanager.Kernel;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.models.ComponentMetadata;
[CtUnresolvedImport]import com.aws.greengrass.util.Utils;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.aws.greengrass.logging.api.Logger;
[CtImportImpl]import java.util.concurrent.Future;
[CtUnresolvedImport]import com.aws.greengrass.componentmanager.models.ComponentIdentifier;
[CtClassImpl]public class ComponentManager implements [CtTypeReferenceImpl]com.aws.greengrass.dependency.InjectionActions {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.aws.greengrass.logging.api.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]com.aws.greengrass.logging.impl.LogManager.getLogger([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String GREENGRASS_SCHEME = [CtLiteralImpl]"GREENGRASS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String S3_SCHEME = [CtLiteralImpl]"S3";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String VERSION_KEY = [CtLiteralImpl]"version";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PACKAGE_NAME_KEY = [CtLiteralImpl]"packageName";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PACKAGE_IDENTIFIER = [CtLiteralImpl]"packageIdentifier";

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.S3Downloader s3ArtifactsDownloader;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.GreengrassRepositoryDownloader greengrassArtifactDownloader;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.ComponentServiceHelper componentServiceHelper;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.ExecutorService executorService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.ComponentStore componentStore;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.Kernel kernel;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.Unarchiver unarchiver;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * PackageManager constructor.
     *
     * @param s3ArtifactsDownloader
     * 		s3ArtifactsDownloader
     * @param greengrassArtifactDownloader
     * 		greengrassArtifactDownloader
     * @param componentServiceHelper
     * 		greengrassPackageServiceHelper
     * @param executorService
     * 		executorService
     * @param componentStore
     * 		componentStore
     * @param kernel
     * 		kernel
     * @param unarchiver
     * 		unarchiver
     */
    [CtAnnotationImpl]@javax.inject.Inject
    public ComponentManager([CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.S3Downloader s3ArtifactsDownloader, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.GreengrassRepositoryDownloader greengrassArtifactDownloader, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.ComponentServiceHelper componentServiceHelper, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ExecutorService executorService, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.ComponentStore componentStore, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.Kernel kernel, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.Unarchiver unarchiver) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.s3ArtifactsDownloader = [CtVariableReadImpl]s3ArtifactsDownloader;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.greengrassArtifactDownloader = [CtVariableReadImpl]greengrassArtifactDownloader;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.componentServiceHelper = [CtVariableReadImpl]componentServiceHelper;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.executorService = [CtVariableReadImpl]executorService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.componentStore = [CtVariableReadImpl]componentStore;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kernel = [CtVariableReadImpl]kernel;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.unarchiver = [CtVariableReadImpl]unarchiver;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * List the package metadata for available package versions that satisfy the requirement. It is ordered by the
     * active version first if found, followed by available versions locally.
     *
     * @param packageName
     * 		the package name
     * @param versionRequirement
     * 		the version requirement for this package
     * @return an iterator of PackageMetadata, with the active version first if found, followed by available versions
    locally.
     * @throws PackagingException
     * 		if fails when trying to list available package metadata
     */
    [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata> listAvailablePackageMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement versionRequirement) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// TODO Switch to customized Iterator to enable lazy iteration
        [CtCommentImpl]// 1. Find the version if this package is currently active with some version and it is satisfied by requirement
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata> optionalActivePackageMetadata = [CtInvocationImpl]findActiveAndSatisfiedPackageMetadata([CtVariableReadImpl]packageName, [CtVariableReadImpl]versionRequirement);
        [CtLocalVariableImpl][CtCommentImpl]// 2. list available packages locally
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata> componentMetadataList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]componentStore.listAvailablePackageMetadata([CtVariableReadImpl]packageName, [CtVariableReadImpl]versionRequirement));
        [CtIfImpl][CtCommentImpl]// 3. If the active satisfied version presents, set it as the head of list.
        if ([CtInvocationImpl][CtVariableReadImpl]optionalActivePackageMetadata.isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata activeComponentMetadata = [CtInvocationImpl][CtVariableReadImpl]optionalActivePackageMetadata.get();
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_NAME_KEY, [CtVariableReadImpl]packageName).addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.VERSION_KEY, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]activeComponentMetadata.getComponentIdentifier().getVersion()).log([CtBinaryOperatorImpl][CtLiteralImpl]"Found active version for dependency package and it is satisfied by the version requirement." + [CtLiteralImpl]" Setting it as the head of the available package list.");
            [CtInvocationImpl][CtVariableReadImpl]componentMetadataList.remove([CtVariableReadImpl]activeComponentMetadata);
            [CtInvocationImpl][CtVariableReadImpl]componentMetadataList.add([CtLiteralImpl]0, [CtVariableReadImpl]activeComponentMetadata);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]componentMetadataList.addAll([CtInvocationImpl][CtFieldReadImpl]componentServiceHelper.listAvailableComponentMetadata([CtVariableReadImpl]packageName, [CtVariableReadImpl]versionRequirement));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atInfo([CtLiteralImpl]"list-package-versions").addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_NAME_KEY, [CtVariableReadImpl]packageName).log([CtLiteralImpl]"Failed when calling Component Management Service to list available versions", [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_NAME_KEY, [CtVariableReadImpl]packageName).addKeyValue([CtLiteralImpl]"packageMetadataList", [CtVariableReadImpl]componentMetadataList).log([CtLiteralImpl]"Found possible versions for dependency package");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]componentMetadataList.iterator();
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata resolveComponentVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement> versionRequirements) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// acquire ever possible local best candidate
        [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier> localCandidateOptional = [CtInvocationImpl]findLocalBestCandidate([CtVariableReadImpl]componentName, [CtVariableReadImpl]versionRequirements);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"componentName", [CtVariableReadImpl]componentName).kv([CtLiteralImpl]"localCandidate", [CtInvocationImpl][CtVariableReadImpl]localCandidateOptional.orElse([CtLiteralImpl]null)).log([CtLiteralImpl]"Resolve to local version");
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]versionRequirements.containsKey([CtInvocationImpl][CtTypeAccessImpl]Deployment.DeploymentType.LOCAL.toString())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// keep using local version if the component is meant to be local override
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"componentName", [CtVariableReadImpl]componentName).log([CtLiteralImpl]"Keep local version if it's local override");
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier localCandidateId = [CtInvocationImpl][CtVariableReadImpl]localCandidateOptional.orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.NoAvailableComponentVersionException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Component %s is meant to be local override, but no version can satisfy %s", [CtVariableReadImpl]componentName, [CtVariableReadImpl]versionRequirements)));
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]componentStore.getPackageMetadata([CtVariableReadImpl]localCandidateId);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// otherwise use cloud determined version
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"componentName", [CtVariableReadImpl]componentName).kv([CtLiteralImpl]"versionRequirement", [CtVariableReadImpl]versionRequirements).log([CtLiteralImpl]"Negotiate version with cloud");
            [CtReturnImpl]return [CtInvocationImpl]negotiateVersionWithCloud([CtVariableReadImpl]componentName, [CtVariableReadImpl]versionRequirements, [CtInvocationImpl][CtVariableReadImpl]localCandidateOptional.orElse([CtLiteralImpl]null));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata negotiateVersionWithCloud([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement> versionRequirements, [CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier localCandidate) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazonaws.services.evergreen.model.ComponentContent componentContent;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]componentContent = [CtInvocationImpl][CtFieldReadImpl]componentServiceHelper.resolveComponentVersion([CtVariableReadImpl]componentName, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]localCandidate == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]localCandidate.getVersion(), [CtVariableReadImpl]versionRequirements);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.NoAvailableComponentVersionException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]localCandidate != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// if it's builtin service, it's not required to have components registered in registry
                [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata componentMetadata = [CtInvocationImpl]getBuiltinComponentMetadata([CtInvocationImpl][CtVariableReadImpl]localCandidate.getName(), [CtInvocationImpl][CtVariableReadImpl]localCandidate.getVersion());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]componentMetadata != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"componentMetadata", [CtVariableReadImpl]componentMetadata).log([CtBinaryOperatorImpl][CtLiteralImpl]"Builtin service and no available" + [CtLiteralImpl]" version in registry, keep using local version");
                    [CtReturnImpl]return [CtVariableReadImpl]componentMetadata;
                }
            }
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier resolvedComponentId = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier([CtInvocationImpl][CtVariableReadImpl]componentContent.getName(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vdurmont.semver4j.Semver([CtInvocationImpl][CtVariableReadImpl]componentContent.getVersion()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String downloadedRecipeContent = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8.decode([CtInvocationImpl][CtVariableReadImpl]componentContent.getRecipe()).toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean saveContent = [CtLiteralImpl]true;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> recipeContentOnDevice = [CtInvocationImpl][CtFieldReadImpl]componentStore.findComponentRecipeContent([CtVariableReadImpl]resolvedComponentId);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]recipeContentOnDevice.filter([CtLambdaImpl]([CtParameterImpl]java.lang.String recipe) -> [CtInvocationImpl][CtVariableReadImpl]recipe.equals([CtVariableReadImpl]downloadedRecipeContent)).isPresent()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]saveContent = [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtVariableReadImpl]saveContent) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]componentStore.savePackageRecipe([CtVariableReadImpl]resolvedComponentId, [CtVariableReadImpl]downloadedRecipeContent);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]componentStore.getPackageMetadata([CtVariableReadImpl]resolvedComponentId);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier> findLocalBestCandidate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement> versionRequirements) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement req = [CtInvocationImpl]mergeVersionRequirements([CtVariableReadImpl]versionRequirements);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier> optionalActiveComponentId = [CtInvocationImpl]findActiveAndSatisfiedComponent([CtVariableReadImpl]componentName, [CtVariableReadImpl]req);
        [CtIfImpl][CtCommentImpl]// use active one if compatible, otherwise check local available ones
        if ([CtInvocationImpl][CtVariableReadImpl]optionalActiveComponentId.isPresent()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]optionalActiveComponentId;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]componentStore.findBestMatchAvailableComponent([CtVariableReadImpl]componentName, [CtVariableReadImpl]req);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement mergeVersionRequirements([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement> versionRequirements) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.vdurmont.semver4j.Requirement.buildNPM([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]versionRequirements.values().stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Requirement::toString).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]" ")));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Make sure all the specified packages exist in the package cache. Download them from remote repository if they
     * don't exist.
     *
     * @param pkgIds
     * 		a list of packages.
     * @return a future to notify once this is finished.
     */
    public [CtTypeReferenceImpl]java.util.concurrent.Future<[CtTypeReferenceImpl]java.lang.Void> preparePackages([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier> pkgIds) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]executorService.submit([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier componentIdentifier : [CtVariableReadImpl]pkgIds) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().isInterrupted()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
                [CtInvocationImpl]preparePackage([CtVariableReadImpl]componentIdentifier);
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void preparePackage([CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier componentIdentifier) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException, [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException, [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.InvalidArtifactUriException [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atInfo().setEventType([CtLiteralImpl]"prepare-package-start").kv([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_IDENTIFIER, [CtVariableReadImpl]componentIdentifier).log();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentRecipe pkg = [CtInvocationImpl]findRecipeDownloadIfNotExisted([CtVariableReadImpl]componentIdentifier);
            [CtInvocationImpl]prepareArtifacts([CtVariableReadImpl]componentIdentifier, [CtInvocationImpl][CtVariableReadImpl]pkg.getArtifacts());
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atInfo([CtLiteralImpl]"prepare-package-finished").kv([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_IDENTIFIER, [CtVariableReadImpl]componentIdentifier).log();
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException | [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atError().log([CtLiteralImpl]"Failed to prepare package {}", [CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentRecipe findRecipeDownloadIfNotExisted([CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier componentIdentifier) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException, [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentRecipe> packageOptional = [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]packageOptional = [CtInvocationImpl][CtFieldReadImpl]componentStore.findPackageRecipe([CtVariableReadImpl]componentIdentifier);
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"component", [CtVariableReadImpl]componentIdentifier).log([CtLiteralImpl]"Loaded from local component store");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atWarn().log([CtLiteralImpl]"Failed to load package recipe for {}", [CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]packageOptional.isPresent()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]packageOptional.get();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String downloadRecipeContent = [CtInvocationImpl][CtFieldReadImpl]componentServiceHelper.downloadPackageRecipeAsString([CtVariableReadImpl]componentIdentifier);
        [CtInvocationImpl][CtFieldReadImpl]componentStore.savePackageRecipe([CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]downloadRecipeContent);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().kv([CtLiteralImpl]"pkgId", [CtVariableReadImpl]componentIdentifier).log([CtLiteralImpl]"Downloaded from component service");
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]componentStore.getPackageRecipe([CtVariableReadImpl]componentIdentifier);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void prepareArtifacts([CtParameterImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier componentIdentifier, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentArtifact> artifacts) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException, [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException, [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.InvalidArtifactUriException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]artifacts == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atWarn().kv([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_IDENTIFIER, [CtVariableReadImpl]componentIdentifier).log([CtLiteralImpl]"Artifact list was null, expected non-null and non-empty");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path packageArtifactDirectory = [CtInvocationImpl][CtFieldReadImpl]componentStore.resolveArtifactDirectoryPath([CtVariableReadImpl]componentIdentifier);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]packageArtifactDirectory)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.isDirectory([CtVariableReadImpl]packageArtifactDirectory))) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectories([CtVariableReadImpl]packageArtifactDirectory);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to create package artifact cache directory %s", [CtVariableReadImpl]packageArtifactDirectory), [CtVariableReadImpl]e);
            }
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().setEventType([CtLiteralImpl]"downloading-package-artifacts").addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_IDENTIFIER, [CtVariableReadImpl]componentIdentifier).log();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentArtifact artifact : [CtVariableReadImpl]artifacts) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.ArtifactDownloader downloader = [CtInvocationImpl]selectArtifactDownloader([CtInvocationImpl][CtVariableReadImpl]artifact.getArtifactUri());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File downloadedFile;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]downloadedFile = [CtInvocationImpl][CtVariableReadImpl]downloader.downloadToPath([CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]artifact, [CtVariableReadImpl]packageArtifactDirectory);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to download package %s artifact %s", [CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]artifact), [CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.aws.iot.greengrass.component.common.Unarchive unarchive = [CtInvocationImpl][CtVariableReadImpl]artifact.getUnarchive();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]unarchive == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]unarchive = [CtFieldReadImpl]com.amazon.aws.iot.greengrass.component.common.Unarchive.NONE;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]downloadedFile != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]unarchive.equals([CtTypeAccessImpl]Unarchive.NONE))) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path unarchivePath = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]componentStore.resolveAndSetupArtifactsDecompressedDirectory([CtVariableReadImpl]componentIdentifier).resolve([CtInvocationImpl]getFileName([CtVariableReadImpl]downloadedFile));
                    [CtInvocationImpl][CtTypeAccessImpl]com.aws.greengrass.util.Utils.createPaths([CtVariableReadImpl]unarchivePath);
                    [CtInvocationImpl][CtFieldReadImpl]unarchiver.unarchive([CtVariableReadImpl]unarchive, [CtVariableReadImpl]downloadedFile, [CtVariableReadImpl]unarchivePath);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageDownloadException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to unarchive package %s artifact %s", [CtVariableReadImpl]componentIdentifier, [CtVariableReadImpl]artifact), [CtVariableReadImpl]e);
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.plugins.ArtifactDownloader selectArtifactDownloader([CtParameterImpl][CtTypeReferenceImpl]java.net.URI artifactUri) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scheme = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]artifactUri.getScheme() == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]artifactUri.getScheme().toUpperCase();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.GREENGRASS_SCHEME.equals([CtVariableReadImpl]scheme)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]greengrassArtifactDownloader;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.S3_SCHEME.equals([CtVariableReadImpl]scheme)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]s3ArtifactsDownloader;
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackageLoadingException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"artifact URI scheme %s is not supported yet", [CtVariableReadImpl]scheme));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Find the active version for a package.
     *
     * @param packageName
     * 		the package name
     * @return Optional of version; Empty if no active version for this package found.
     */
    private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.vdurmont.semver4j.Semver> findActiveVersion([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String packageName) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]kernel.findServiceTopic([CtVariableReadImpl]packageName) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.GreengrassService service = [CtInvocationImpl][CtFieldReadImpl]kernel.locate([CtVariableReadImpl]packageName);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl]getPackageVersionFromService([CtVariableReadImpl]service));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.exceptions.ServiceLoadException e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.logger.atDebug().addKeyValue([CtFieldReadImpl]com.aws.greengrass.componentmanager.ComponentManager.PACKAGE_NAME_KEY, [CtVariableReadImpl]packageName).log([CtLiteralImpl]"Didn't find a active service for this package running in the kernel.");
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the package version from the active Greengrass service.
     *
     * @param service
     * 		the active Greengrass service
     * @return the package version from the active Greengrass service
     */
    [CtTypeReferenceImpl]com.vdurmont.semver4j.Semver getPackageVersionFromService([CtParameterImpl]final [CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.GreengrassService service) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.config.Topic versionTopic = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]service.getServiceConfig().findLeafChild([CtTypeAccessImpl]KernelConfigResolver.VERSION_CONFIG_KEY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]versionTopic == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vdurmont.semver4j.Semver([CtInvocationImpl][CtTypeAccessImpl]com.aws.greengrass.util.Coerce.toString([CtVariableReadImpl]versionTopic));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Find the package metadata for a package if it's active version satisfies the requirement.
     *
     * @param componentName
     * 		the component name
     * @param requirement
     * 		the version requirement
     * @return Optional of the package metadata for the package; empty if this package doesn't have active version or
    the active version doesn't satisfy the requirement.
     * @throws PackagingException
     * 		if fails to find the target recipe or parse the recipe
     */
    private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata> findActiveAndSatisfiedPackageMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement requirement) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.vdurmont.semver4j.Semver> activeVersionOptional = [CtInvocationImpl]findActiveVersion([CtVariableReadImpl]componentName);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]activeVersionOptional.isPresent()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Semver activeVersion = [CtInvocationImpl][CtVariableReadImpl]activeVersionOptional.get();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]requirement.isSatisfiedBy([CtVariableReadImpl]activeVersion)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtTryImpl][CtCommentImpl]// If the component is builtin, then we won't be able to get the metadata from the filesystem,
        [CtCommentImpl]// so in that case we will try getting it from builtin. If that fails too, then we just rethrow.
        try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtFieldReadImpl]componentStore.getPackageMetadata([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier([CtVariableReadImpl]componentName, [CtVariableReadImpl]activeVersion)));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException e) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata md = [CtInvocationImpl]getBuiltinComponentMetadata([CtVariableReadImpl]componentName, [CtVariableReadImpl]activeVersion);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]md != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtVariableReadImpl]md);
            }
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata getActiveAndSatisfiedComponentMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement> requirementMap) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getActiveAndSatisfiedComponentMetadata([CtVariableReadImpl]componentName, [CtInvocationImpl]mergeVersionRequirements([CtVariableReadImpl]requirementMap));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata getActiveAndSatisfiedComponentMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement requirement) throws [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.PackagingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata> componentMetadataOptional = [CtInvocationImpl]findActiveAndSatisfiedPackageMetadata([CtVariableReadImpl]componentName, [CtVariableReadImpl]requirement);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]componentMetadataOptional.isPresent()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.exceptions.NoAvailableComponentVersionException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"There is no version of component %s satisfying %s", [CtVariableReadImpl]componentName, [CtVariableReadImpl]requirement));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]componentMetadataOptional.get();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier> findActiveAndSatisfiedComponent([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Requirement requirement) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.vdurmont.semver4j.Semver> activeVersionOptional = [CtInvocationImpl]findActiveVersion([CtVariableReadImpl]componentName);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]activeVersionOptional.filter([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]requirement::isSatisfiedBy).map([CtLambdaImpl]([CtParameterImpl] version) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier([CtVariableReadImpl]componentName, [CtVariableReadImpl]version));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata getBuiltinComponentMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl][CtTypeReferenceImpl]com.vdurmont.semver4j.Semver activeVersion) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.GreengrassService service = [CtInvocationImpl][CtFieldReadImpl]kernel.locate([CtVariableReadImpl]packageName);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]service.isBuiltin()) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> deps = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]service.forAllDependencies([CtLambdaImpl]([CtParameterImpl] d) -> [CtInvocationImpl][CtVariableReadImpl]deps.put([CtInvocationImpl][CtVariableReadImpl]d.getServiceName(), [CtTypeAccessImpl]com.aws.greengrass.componentmanager.ANY_VERSION));
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentMetadata([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.greengrass.componentmanager.models.ComponentIdentifier([CtVariableReadImpl]packageName, [CtVariableReadImpl]activeVersion), [CtVariableReadImpl]deps);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.aws.greengrass.lifecyclemanager.exceptions.ServiceLoadException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getFileName([CtParameterImpl][CtTypeReferenceImpl]java.io.File f) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fileName = [CtInvocationImpl][CtVariableReadImpl]f.getName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fileName.indexOf([CtLiteralImpl]'.') > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]fileName.substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]fileName.lastIndexOf([CtLiteralImpl]'.'));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]fileName;
        }
    }
}
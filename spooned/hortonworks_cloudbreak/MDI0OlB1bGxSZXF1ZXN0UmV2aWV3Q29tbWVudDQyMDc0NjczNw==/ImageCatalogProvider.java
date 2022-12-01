[CompilationUnitImpl][CtPackageDeclarationImpl]package com.sequenceiq.freeipa.service.image;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import org.springframework.cache.annotation.Cacheable;
[CtUnresolvedImport]import javax.ws.rs.client.Client;
[CtUnresolvedImport]import org.springframework.cache.annotation.CacheEvict;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Value;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonMappingException;
[CtUnresolvedImport]import org.springframework.stereotype.Service;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import com.sequenceiq.freeipa.api.model.image.Images;
[CtUnresolvedImport]import com.sequenceiq.cloudbreak.util.FileReaderUtils;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import javax.inject.Inject;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.sequenceiq.freeipa.api.model.image.FreeIpaVersions;
[CtUnresolvedImport]import javax.ws.rs.core.Response.Status.Family;
[CtUnresolvedImport]import com.sequenceiq.freeipa.api.model.image.Image;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import org.springframework.util.CollectionUtils;
[CtUnresolvedImport]import com.sequenceiq.cloudbreak.client.RestClientUtil;
[CtUnresolvedImport]import com.sequenceiq.freeipa.api.model.image.ImageCatalog;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import javax.ws.rs.ProcessingException;
[CtUnresolvedImport]import javax.ws.rs.client.WebTarget;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.sequenceiq.freeipa.api.model.image.Versions;
[CtClassImpl][CtAnnotationImpl]@org.springframework.stereotype.Service
public class ImageCatalogProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.sequenceiq.freeipa.service.image.ImageCatalogProvider.class);

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtLiteralImpl]"${cb.etc.config.dir}")
    private [CtTypeReferenceImpl]java.lang.String etcConfigDir;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtLiteralImpl]"#{'${cb.enabled.linux.types}'.split(',')}")
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> enabledLinuxTypes;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    private [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.cache.annotation.Cacheable(cacheNames = [CtLiteralImpl]"imageCatalogCache", key = [CtLiteralImpl]"#catalogUrl")
    public [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog getImageCatalog([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalogUrl) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog catalog;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]catalogUrl == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.sequenceiq.freeipa.service.image.ImageCatalogProvider.LOGGER.info([CtLiteralImpl]"No image catalog was defined!");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long started = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String content;
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]catalogUrl.startsWith([CtLiteralImpl]"http")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.ws.rs.client.Client client = [CtInvocationImpl][CtTypeAccessImpl]com.sequenceiq.cloudbreak.client.RestClientUtil.get();
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.ws.rs.client.WebTarget target = [CtInvocationImpl][CtVariableReadImpl]client.target([CtVariableReadImpl]catalogUrl);
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.ws.rs.core.Response response = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.request().get();
                [CtAssignmentImpl][CtVariableWriteImpl]content = [CtInvocationImpl]readResponse([CtVariableReadImpl]target, [CtVariableReadImpl]response);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]content = [CtInvocationImpl]readCatalogFromFile([CtVariableReadImpl]catalogUrl);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]catalog = [CtInvocationImpl]filterImagesByOsType([CtInvocationImpl][CtFieldReadImpl]objectMapper.readValue([CtVariableReadImpl]content, [CtFieldReadImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog.class));
            [CtLocalVariableImpl][CtTypeReferenceImpl]long timeOfParse = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtVariableReadImpl]started;
            [CtInvocationImpl][CtFieldReadImpl]com.sequenceiq.freeipa.service.image.ImageCatalogProvider.LOGGER.debug([CtLiteralImpl]"ImageCatalog was fetched and parsed from '{}' and took '{}' ms.", [CtVariableReadImpl]catalogUrl, [CtVariableReadImpl]timeOfParse);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.service.image.ImageCatalogException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to get image catalog: %s from %s", [CtInvocationImpl][CtVariableReadImpl]e.getCause(), [CtVariableReadImpl]catalogUrl), [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonMappingException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.service.image.ImageCatalogException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Invalid json format for image catalog with error: %s", [CtInvocationImpl][CtVariableReadImpl]e.getMessage()), [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.service.image.ImageCatalogException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to read image catalog from file: '%s'", [CtVariableReadImpl]catalogUrl), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]catalog;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.cache.annotation.CacheEvict(value = [CtLiteralImpl]"imageCatalogCache", key = [CtLiteralImpl]"#catalogUrl")
    public [CtTypeReferenceImpl]void evictImageCatalogCache([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalogUrl) [CtBlockImpl]{
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog filterImagesByOsType([CtParameterImpl][CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog catalog) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.sequenceiq.freeipa.service.image.ImageCatalogProvider.LOGGER.debug([CtLiteralImpl]"Filtering images by OS type {}", [CtInvocationImpl]getEnabledLinuxTypes());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.CollectionUtils.isEmpty([CtInvocationImpl]getEnabledLinuxTypes()) || [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.isNull([CtVariableReadImpl]catalog)) || [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.isNull([CtInvocationImpl][CtVariableReadImpl]catalog.getImages())) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]catalog;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> catalogImages = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]catalog.getImages().getFreeipaImages();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> filterImages = [CtInvocationImpl]filterImages([CtVariableReadImpl]catalogImages, [CtInvocationImpl]enabledOsPredicate());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.FreeIpaVersions> filteredVersions = [CtInvocationImpl]filterVersions([CtVariableReadImpl]catalog, [CtVariableReadImpl]filterImages);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Images([CtVariableReadImpl]filterImages), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Versions([CtVariableReadImpl]filteredVersions));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.FreeIpaVersions> filterVersions([CtParameterImpl][CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.ImageCatalog catalog, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> filterImages) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> filteredUuids = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]filterImages.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Image::getUuid).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]catalog.getVersions().getFreeIpaVersions().stream().map([CtLambdaImpl]([CtParameterImpl] versions) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> defaults = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]versions.getDefaults().stream().filter([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]filteredUuids::contains).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> imageIds = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]versions.getImageIds().stream().filter([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]filteredUuids::contains).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]LOGGER.debug([CtLiteralImpl]"Filtered versions: [versions: {}, defaults: {}, images: {}]", [CtInvocationImpl][CtVariableReadImpl]versions.getVersions(), [CtVariableReadImpl]defaults, [CtVariableReadImpl]imageIds);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.FreeIpaVersions([CtInvocationImpl][CtVariableReadImpl]versions.getVersions(), [CtVariableReadImpl]defaults, [CtVariableReadImpl]imageIds);
        }).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> filterImages([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> imageList, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> predicate) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Boolean, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image>> partitionedImages = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]imageList).orElse([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList()).stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.partitioningBy([CtVariableReadImpl]predicate));
        [CtIfImpl]if ([CtInvocationImpl]hasFiltered([CtVariableReadImpl]partitionedImages)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.sequenceiq.freeipa.service.image.ImageCatalogProvider.LOGGER.debug([CtLiteralImpl]"Used filter linuxTypes: | {} | Images filtered: {}", [CtInvocationImpl]getEnabledLinuxTypes(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partitionedImages.get([CtLiteralImpl]false).stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Image::toString).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", ")));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]partitionedImages.get([CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean hasFiltered([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Boolean, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image>> partitioned) [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partitioned.get([CtLiteralImpl]false).isEmpty();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]com.sequenceiq.freeipa.api.model.image.Image> enabledOsPredicate() [CtBlockImpl]{
        [CtReturnImpl]return [CtLambdaImpl]([CtParameterImpl]com.sequenceiq.freeipa.api.model.image.Image img) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getEnabledLinuxTypes().stream().anyMatch([CtExecutableReferenceExpressionImpl][CtInvocationImpl][CtVariableReadImpl]img.getOs()::equalsIgnoreCase);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getEnabledLinuxTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]enabledLinuxTypes.stream().filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils::isNoneBlank).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String readResponse([CtParameterImpl][CtTypeReferenceImpl]javax.ws.rs.client.WebTarget target, [CtParameterImpl][CtTypeReferenceImpl]javax.ws.rs.core.Response response) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusInfo().getFamily().equals([CtTypeAccessImpl]Family.SUCCESSFUL)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.service.image.ImageCatalogException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to get image catalog from '%s' due to: '%s'", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getUri().toString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusInfo().getReasonPhrase()));
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]response.readEntity([CtFieldReadImpl]java.lang.String.class);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.ws.rs.ProcessingException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.sequenceiq.freeipa.service.image.ImageCatalogException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to process image catalog from '%s' due to: '%s'", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getUri().toString(), [CtInvocationImpl][CtVariableReadImpl]e.getMessage()));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String readCatalogFromFile([CtParameterImpl][CtTypeReferenceImpl]java.lang.String catalogUrl) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File customCatalogFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]etcConfigDir, [CtVariableReadImpl]catalogUrl);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.sequenceiq.cloudbreak.util.FileReaderUtils.readFileFromPath([CtInvocationImpl][CtVariableReadImpl]customCatalogFile.toPath());
    }
}
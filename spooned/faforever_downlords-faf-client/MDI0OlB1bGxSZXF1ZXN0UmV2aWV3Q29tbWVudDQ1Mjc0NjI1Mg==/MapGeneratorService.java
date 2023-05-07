[CompilationUnitImpl][CtPackageDeclarationImpl]package com.faforever.client.map.generator;
[CtUnresolvedImport]import com.faforever.client.io.FileUtils;
[CtUnresolvedImport]import com.faforever.client.preferences.PreferencesService;
[CtUnresolvedImport]import com.faforever.client.config.CacheNames;
[CtUnresolvedImport]import lombok.Setter;
[CtUnresolvedImport]import lombok.Getter;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import org.springframework.cache.annotation.Cacheable;
[CtUnresolvedImport]import org.springframework.core.ParameterizedTypeReference;
[CtUnresolvedImport]import org.springframework.core.io.ClassPathResource;
[CtUnresolvedImport]import org.springframework.beans.factory.InitializingBean;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtImportImpl]import java.nio.file.Files;
[CtImportImpl]import java.util.Random;
[CtUnresolvedImport]import org.springframework.stereotype.Service;
[CtUnresolvedImport]import org.apache.maven.artifact.versioning.ComparableVersion;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.util.Base64;
[CtUnresolvedImport]import org.springframework.web.client.RestTemplate;
[CtUnresolvedImport]import com.faforever.client.config.ClientProperties;
[CtImportImpl]import java.util.regex.Pattern;
[CtImportImpl]import java.nio.file.Path;
[CtUnresolvedImport]import org.springframework.http.HttpMethod;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.nio.ByteBuffer;
[CtUnresolvedImport]import org.springframework.context.ApplicationContext;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import org.springframework.http.HttpEntity;
[CtUnresolvedImport]import org.springframework.context.annotation.Lazy;
[CtUnresolvedImport]import org.springframework.http.ResponseEntity;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import static com.github.nocatch.NoCatch.noCatch;
[CtUnresolvedImport]import javafx.scene.image.Image;
[CtUnresolvedImport]import org.springframework.util.LinkedMultiValueMap;
[CtUnresolvedImport]import com.faforever.client.task.TaskService;
[CtClassImpl][CtAnnotationImpl]@org.springframework.context.annotation.Lazy
[CtAnnotationImpl]@org.springframework.stereotype.Service
[CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
public class MapGeneratorService implements [CtTypeReferenceImpl]org.springframework.beans.factory.InitializingBean {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Naming template for generated maps. It is all lower case because server expects lower case names for maps.
     */
    public static final [CtTypeReferenceImpl]java.lang.String GENERATED_MAP_NAME = [CtLiteralImpl]"neroxis_map_generator_%s_%s";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String GENERATOR_EXECUTABLE_FILENAME = [CtLiteralImpl]"MapGenerator_%s.jar";

    [CtFieldImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    public static final [CtTypeReferenceImpl]java.lang.String GENERATOR_EXECUTABLE_SUB_DIRECTORY = [CtLiteralImpl]"map_generator";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int GENERATION_TIMEOUT_SECONDS = [CtLiteralImpl]60;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.regex.Pattern VERSION_PATTERN = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.regex.Pattern GENERATED_MAP_PATTERN = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"neroxis_map_generator_(" + [CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.VERSION_PATTERN) + [CtLiteralImpl]")_(.*)");

    [CtFieldImpl][CtAnnotationImpl]@lombok.Getter
    private final [CtTypeReferenceImpl]java.nio.file.Path generatorExecutablePath;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.springframework.context.ApplicationContext applicationContext;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.faforever.client.preferences.PreferencesService preferencesService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.faforever.client.task.TaskService taskService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.faforever.client.config.ClientProperties clientProperties;

    [CtFieldImpl][CtAnnotationImpl]@lombok.Getter
    private final [CtTypeReferenceImpl]java.nio.file.Path customMapsDirectory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Random seedGenerator;

    [CtFieldImpl][CtAnnotationImpl]@lombok.Getter
    private [CtTypeReferenceImpl]javafx.scene.image.Image generatedMapPreviewImage;

    [CtFieldImpl][CtAnnotationImpl]@lombok.Getter
    [CtAnnotationImpl]@lombok.Setter
    private [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion generatorVersion;

    [CtConstructorImpl]public MapGeneratorService([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext applicationContext, [CtParameterImpl][CtTypeReferenceImpl]com.faforever.client.preferences.PreferencesService preferencesService, [CtParameterImpl][CtTypeReferenceImpl]com.faforever.client.task.TaskService taskService, [CtParameterImpl][CtTypeReferenceImpl]com.faforever.client.config.ClientProperties clientProperties) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.applicationContext = [CtVariableReadImpl]applicationContext;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.preferencesService = [CtVariableReadImpl]preferencesService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.taskService = [CtVariableReadImpl]taskService;
        [CtAssignmentImpl][CtFieldWriteImpl]generatorExecutablePath = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]preferencesService.getFafDataDirectory().resolve([CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATOR_EXECUTABLE_SUB_DIRECTORY);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.clientProperties = [CtVariableReadImpl]clientProperties;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtFieldReadImpl]generatorExecutablePath)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.createDirectory([CtFieldReadImpl]generatorExecutablePath);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Could not create map generator executable directory.", [CtVariableReadImpl]e);
            }
        }
        [CtAssignmentImpl][CtFieldWriteImpl]seedGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Random();
        [CtAssignmentImpl][CtFieldWriteImpl]customMapsDirectory = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.preferencesService.getPreferences().getForgedAlliance().getCustomMapsDirectory();
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]generatedMapPreviewImage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javafx.scene.image.Image([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.core.io.ClassPathResource([CtLiteralImpl]"/images/generatedMapIcon.png").getURL().toString(), [CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Could not load generated map preview image.", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void afterPropertiesSet() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]deleteGeneratedMaps();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void deleteGeneratedMaps() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"Deleting leftover generated maps...");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]customMapsDirectory != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]customMapsDirectory.toFile().exists()) [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.nio.file.Path> listOfMapFiles = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.list([CtFieldReadImpl]customMapsDirectory)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]listOfMapFiles.filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.nio.file.Files::isDirectory).filter([CtLambdaImpl]([CtParameterImpl]java.nio.file.Path p) -> [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATED_MAP_PATTERN.matcher([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getFileName().toString()).matches()).forEach([CtLambdaImpl]([CtParameterImpl]java.nio.file.Path p) -> [CtInvocationImpl]noCatch([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]com.faforever.client.io.FileUtils.deleteRecursively([CtVariableReadImpl]p)));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Could not list custom maps directory for deleting leftover generated maps.", [CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.ByteBuffer seedBuffer = [CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.allocate([CtLiteralImpl]8);
        [CtInvocationImpl][CtVariableReadImpl]seedBuffer.putLong([CtInvocationImpl][CtFieldReadImpl]seedGenerator.nextLong());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String seedString = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getEncoder().encodeToString([CtInvocationImpl][CtVariableReadImpl]seedBuffer.array());
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtFieldReadImpl]generatorVersion, [CtVariableReadImpl]seedString);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] optionArray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtFieldReadImpl]generatorVersion, [CtVariableReadImpl]optionArray);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] optionArray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtVariableReadImpl]version), [CtVariableReadImpl]optionArray);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion version, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] optionArray) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.ByteBuffer seedBuffer = [CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.allocate([CtLiteralImpl]8);
        [CtInvocationImpl][CtVariableReadImpl]seedBuffer.putLong([CtInvocationImpl][CtFieldReadImpl]seedGenerator.nextLong());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String seedString = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getEncoder().encodeToString([CtInvocationImpl][CtVariableReadImpl]seedBuffer.array());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String optionString = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getEncoder().encodeToString([CtVariableReadImpl]optionArray);
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtVariableReadImpl]version, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]seedString + [CtLiteralImpl]'_') + [CtVariableReadImpl]optionString);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    [CtAnnotationImpl]@org.springframework.cache.annotation.Cacheable([CtFieldReadImpl]com.faforever.client.config.CacheNames.MAP_GENERATOR)
    public [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion queryMaxSupportedVersion() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion version = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtLiteralImpl]"");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion minVersion = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientProperties.getMapGenerator().getMinSupportedMajorVersion()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion maxVersion = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientProperties.getMapGenerator().getMaxSupportedMajorVersion() + [CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.RestTemplate restTemplate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.web.client.RestTemplate();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.util.LinkedMultiValueMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.util.LinkedMultiValueMap<>();
        [CtInvocationImpl][CtVariableReadImpl]headers.add([CtLiteralImpl]"Accept", [CtLiteralImpl]"application/vnd.github.v3+json");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.HttpEntity<[CtTypeReferenceImpl]java.lang.String> entity = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.http.HttpEntity<>([CtLiteralImpl]null, [CtVariableReadImpl]headers);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.faforever.client.map.generator.GithubGeneratorRelease>> response = [CtInvocationImpl][CtVariableReadImpl]restTemplate.exchange([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientProperties.getMapGenerator().getQueryVersionsUrl(), [CtTypeAccessImpl]HttpMethod.GET, [CtVariableReadImpl]entity, [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<>()[CtClassImpl] {});
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.faforever.client.map.generator.GithubGeneratorRelease> releases = [CtInvocationImpl][CtVariableReadImpl]response.getBody();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.faforever.client.map.generator.GithubGeneratorRelease release : [CtVariableReadImpl]releases) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]version.parseVersion([CtInvocationImpl][CtVariableReadImpl]release.getTag_name());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtVariableReadImpl]maxVersion) < [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]minVersion.compareTo([CtVariableReadImpl]version) < [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]version;
            }
        }
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"No Valid Generator Version Found");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String mapName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher matcher = [CtInvocationImpl][CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATED_MAP_PATTERN.matcher([CtVariableReadImpl]mapName);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]matcher.find()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> errorFuture = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
            [CtInvocationImpl][CtVariableReadImpl]errorFuture.completeExceptionally([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Map name is not a generated map"));
            [CtReturnImpl]return [CtVariableReadImpl]errorFuture;
        }
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtInvocationImpl][CtVariableReadImpl]matcher.group([CtLiteralImpl]1), [CtInvocationImpl][CtVariableReadImpl]matcher.group([CtLiteralImpl]2));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String seedAndOptions) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]generateMap([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtVariableReadImpl]version), [CtVariableReadImpl]seedAndOptions);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> generateMap([CtParameterImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String seedAndOptions) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion minVersion = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientProperties.getMapGenerator().getMinSupportedMajorVersion()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion maxVersion = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]clientProperties.getMapGenerator().getMaxSupportedMajorVersion() + [CtLiteralImpl]1));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtVariableReadImpl]maxVersion) >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> errorFuture = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
            [CtInvocationImpl][CtVariableReadImpl]errorFuture.completeExceptionally([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"New Version not supported"));
            [CtReturnImpl]return [CtVariableReadImpl]errorFuture;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtVariableReadImpl]minVersion) < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> errorFuture = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
            [CtInvocationImpl][CtVariableReadImpl]errorFuture.completeExceptionally([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Old Version not supported"));
            [CtReturnImpl]return [CtVariableReadImpl]errorFuture;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String generatorExecutableFileName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATOR_EXECUTABLE_FILENAME, [CtVariableReadImpl]version);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path generatorExecutablePath = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.generatorExecutablePath.resolve([CtVariableReadImpl]generatorExecutableFileName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> downloadGeneratorFuture;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.exists([CtVariableReadImpl]generatorExecutablePath)) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.VERSION_PATTERN.matcher([CtInvocationImpl][CtVariableReadImpl]version.toString()).matches()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.warn([CtLiteralImpl]"Unsupported generator version: {}", [CtVariableReadImpl]version);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> errorFuture = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<>();
                [CtInvocationImpl][CtVariableReadImpl]errorFuture.completeExceptionally([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported generator version: " + [CtVariableReadImpl]version));
                [CtReturnImpl]return [CtVariableReadImpl]errorFuture;
            }
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"Downloading MapGenerator version: {}", [CtVariableReadImpl]version);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.faforever.client.map.generator.DownloadMapGeneratorTask downloadMapGeneratorTask = [CtInvocationImpl][CtFieldReadImpl]applicationContext.getBean([CtFieldReadImpl]com.faforever.client.map.generator.DownloadMapGeneratorTask.class);
            [CtInvocationImpl][CtVariableReadImpl]downloadMapGeneratorTask.setVersion([CtInvocationImpl][CtVariableReadImpl]version.toString());
            [CtAssignmentImpl][CtVariableWriteImpl]downloadGeneratorFuture = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]taskService.submitTask([CtVariableReadImpl]downloadMapGeneratorTask).getFuture();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"Found MapGenerator version: {}", [CtVariableReadImpl]version);
            [CtAssignmentImpl][CtVariableWriteImpl]downloadGeneratorFuture = [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.completedFuture([CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] seedParts = [CtInvocationImpl][CtVariableReadImpl]seedAndOptions.split([CtLiteralImpl]"_");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String seedString = [CtArrayReadImpl][CtVariableReadImpl]seedParts[[CtLiteralImpl]0];
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mapFilename;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String seed;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]seed = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtVariableReadImpl]seedString));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException nfe) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] seedBytes = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getDecoder().decode([CtVariableReadImpl]seedString);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.ByteBuffer seedWrapper = [CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.wrap([CtVariableReadImpl]seedBytes);
            [CtAssignmentImpl][CtVariableWriteImpl]seed = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtInvocationImpl][CtVariableReadImpl]seedWrapper.getLong());
        }
        [CtIfImpl][CtCommentImpl]// Check if major version 0 which requires numeric seed
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.maven.artifact.versioning.ComparableVersion([CtLiteralImpl]"1")) < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mapFilename = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATED_MAP_NAME, [CtVariableReadImpl]version, [CtVariableReadImpl]seed).replace([CtLiteralImpl]'/', [CtLiteralImpl]'^');
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mapFilename = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATED_MAP_NAME, [CtVariableReadImpl]version, [CtVariableReadImpl]seedAndOptions).replace([CtLiteralImpl]'/', [CtLiteralImpl]'^');
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.faforever.client.map.generator.GenerateMapTask generateMapTask = [CtInvocationImpl][CtFieldReadImpl]applicationContext.getBean([CtFieldReadImpl]com.faforever.client.map.generator.GenerateMapTask.class);
        [CtInvocationImpl][CtVariableReadImpl]generateMapTask.setVersion([CtInvocationImpl][CtVariableReadImpl]version.toString());
        [CtInvocationImpl][CtVariableReadImpl]generateMapTask.setSeed([CtVariableReadImpl]seed);
        [CtInvocationImpl][CtVariableReadImpl]generateMapTask.setGeneratorExecutableFile([CtVariableReadImpl]generatorExecutablePath);
        [CtInvocationImpl][CtVariableReadImpl]generateMapTask.setMapFilename([CtVariableReadImpl]mapFilename);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]downloadGeneratorFuture.thenApplyAsync([CtLambdaImpl]([CtParameterImpl]java.lang.Void aVoid) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.Void> generateMapFuture = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]taskService.submitTask([CtVariableReadImpl]generateMapTask).getFuture();
            [CtInvocationImpl][CtVariableReadImpl]generateMapFuture.join();
            [CtReturnImpl]return [CtVariableReadImpl]mapFilename;
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isGeneratedMap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String mapName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.faforever.client.map.generator.MapGeneratorService.GENERATED_MAP_PATTERN.matcher([CtVariableReadImpl]mapName).matches();
    }
}
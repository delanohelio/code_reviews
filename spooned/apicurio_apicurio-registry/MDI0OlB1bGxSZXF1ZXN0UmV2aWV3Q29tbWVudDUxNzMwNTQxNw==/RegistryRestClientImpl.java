[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Red Hat
Copyright 2020 IBM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package io.apicurio.registry.client;
[CtUnresolvedImport]import io.apicurio.registry.client.service.RulesService;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.ArtifactMetaData;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.ArtifactSearchResults;
[CtUnresolvedImport]import io.apicurio.registry.utils.IoUtil;
[CtImportImpl]import java.security.NoSuchAlgorithmException;
[CtImportImpl]import java.security.cert.CertificateException;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.VersionMetaData;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_LOCATION;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_KEY_PASSWORD;
[CtImportImpl]import java.util.List;
[CtImportImpl]import javax.net.ssl.KeyManager;
[CtUnresolvedImport]import okhttp3.MediaType;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_HEADERS_PREFIX;
[CtUnresolvedImport]import okhttp3.Interceptor;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_KEYSTORE_PASSWORD;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.VersionSearchResults;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_TYPE;
[CtImportImpl]import java.security.SecureRandom;
[CtUnresolvedImport]import okhttp3.Credentials;
[CtImportImpl]import javax.net.ssl.TrustManager;
[CtUnresolvedImport]import retrofit2.Retrofit;
[CtImportImpl]import java.io.FileInputStream;
[CtImportImpl]import javax.net.ssl.X509TrustManager;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.SearchOver;
[CtImportImpl]import javax.net.ssl.TrustManagerFactory;
[CtUnresolvedImport]import retrofit2.converter.jackson.JacksonConverterFactory;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_KEYSTORE_TYPE;
[CtUnresolvedImport]import io.apicurio.registry.types.RuleType;
[CtUnresolvedImport]import okhttp3.HttpUrl;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.SortOrder;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.EditableMetaData;
[CtImportImpl]import java.security.KeyManagementException;
[CtUnresolvedImport]import okhttp3.RequestBody;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_PASSWORD;
[CtUnresolvedImport]import io.apicurio.registry.types.ArtifactType;
[CtUnresolvedImport]import static io.apicurio.registry.client.request.RestClientConfig.REGISTRY_REQUEST_KEYSTORE_LOCATION;
[CtUnresolvedImport]import io.apicurio.registry.client.service.ArtifactsService;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.UpdateState;
[CtImportImpl]import java.security.UnrecoverableKeyException;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.IfExistsType;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.security.KeyStoreException;
[CtImportImpl]import javax.net.ssl.SSLContext;
[CtImportImpl]import java.security.KeyStore;
[CtUnresolvedImport]import io.apicurio.registry.client.service.SearchService;
[CtImportImpl]import javax.net.ssl.KeyManagerFactory;
[CtUnresolvedImport]import io.apicurio.registry.rest.beans.Rule;
[CtUnresolvedImport]import okhttp3.OkHttpClient;
[CtUnresolvedImport]import io.apicurio.registry.client.request.HeadersInterceptor;
[CtUnresolvedImport]import io.apicurio.registry.client.service.IdsService;
[CtUnresolvedImport]import io.apicurio.registry.client.request.RequestExecutor;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Carles Arnal <carnalca@redhat.com>
 */
public class RegistryRestClientImpl implements [CtTypeReferenceImpl]io.apicurio.registry.client.RegistryRestClient {
    [CtFieldImpl]private final [CtTypeReferenceImpl]io.apicurio.registry.client.request.RequestExecutor requestHandler;

    [CtFieldImpl]private final [CtTypeReferenceImpl]okhttp3.OkHttpClient httpClient;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.apicurio.registry.client.service.ArtifactsService artifactsService;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.apicurio.registry.client.service.RulesService rulesService;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.apicurio.registry.client.service.SearchService searchService;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.apicurio.registry.client.service.IdsService idsService;

    [CtConstructorImpl]RegistryRestClientImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]baseUrl, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
    }

    [CtConstructorImpl]RegistryRestClientImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> config) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]baseUrl, [CtInvocationImpl]io.apicurio.registry.client.RegistryRestClientImpl.createHttpClientWithConfig([CtVariableReadImpl]baseUrl, [CtVariableReadImpl]config));
    }

    [CtConstructorImpl]RegistryRestClientImpl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl]okhttp3.OkHttpClient okHttpClient) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]baseUrl.endsWith([CtLiteralImpl]"/")) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]baseUrl += [CtLiteralImpl]"/";
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.httpClient = [CtVariableReadImpl]okHttpClient;
        [CtLocalVariableImpl][CtTypeReferenceImpl]retrofit2.Retrofit retrofit = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]retrofit2.Retrofit.Builder().client([CtVariableReadImpl]okHttpClient).addConverterFactory([CtInvocationImpl][CtTypeAccessImpl]retrofit2.converter.jackson.JacksonConverterFactory.create()).baseUrl([CtVariableReadImpl]baseUrl).build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.apicurio.registry.client.request.RequestExecutor();
        [CtInvocationImpl]initServices([CtVariableReadImpl]retrofit);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]okhttp3.OkHttpClient createHttpClientWithConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]okhttp3.OkHttpClient.Builder okHttpClientBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]okhttp3.OkHttpClient.Builder();
        [CtAssignmentImpl][CtVariableWriteImpl]okHttpClientBuilder = [CtInvocationImpl]io.apicurio.registry.client.RegistryRestClientImpl.addHeaders([CtVariableReadImpl]okHttpClientBuilder, [CtVariableReadImpl]baseUrl, [CtVariableReadImpl]configs);
        [CtAssignmentImpl][CtVariableWriteImpl]okHttpClientBuilder = [CtInvocationImpl]io.apicurio.registry.client.RegistryRestClientImpl.addSSL([CtVariableReadImpl]okHttpClientBuilder, [CtVariableReadImpl]configs);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]okHttpClientBuilder.build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]OkHttpClient.Builder addHeaders([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]okhttp3.OkHttpClient.Builder okHttpClientBuilder, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> requestHeaders = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configs.entrySet().stream().filter([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, java.lang.Object> map) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]map.getKey().startsWith([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_HEADERS_PREFIX)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, java.lang.Object> map) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]map.getKey().replace([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_HEADERS_PREFIX, [CtLiteralImpl]""), [CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, java.lang.Object> map) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]map.getValue().toString()));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]requestHeaders.containsKey([CtLiteralImpl]"Authorization")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Check if url includes user/password
            [CtCommentImpl]// and add auth header if it does
            [CtTypeReferenceImpl]okhttp3.HttpUrl url = [CtInvocationImpl][CtTypeAccessImpl]okhttp3.HttpUrl.parse([CtVariableReadImpl]baseUrl);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtInvocationImpl][CtVariableReadImpl]url.encodedUsername();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pwd = [CtInvocationImpl][CtVariableReadImpl]url.encodedPassword();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]user != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]user.isEmpty())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String credentials = [CtInvocationImpl][CtTypeAccessImpl]okhttp3.Credentials.basic([CtVariableReadImpl]user, [CtVariableReadImpl]pwd);
                [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"Authorization", [CtVariableReadImpl]credentials);
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]requestHeaders.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]okhttp3.Interceptor headersInterceptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.apicurio.registry.client.request.HeadersInterceptor([CtVariableReadImpl]requestHeaders);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]okHttpClientBuilder.addInterceptor([CtVariableReadImpl]headersInterceptor);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]okHttpClientBuilder;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]OkHttpClient.Builder addSSL([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]okhttp3.OkHttpClient.Builder okHttpClientBuilder, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configs) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]javax.net.ssl.KeyManager[] keyManagers = [CtInvocationImpl]io.apicurio.registry.client.RegistryRestClientImpl.getKeyManagers([CtVariableReadImpl]configs);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]javax.net.ssl.TrustManager[] trustManagers = [CtInvocationImpl]io.apicurio.registry.client.RegistryRestClientImpl.getTrustManagers([CtVariableReadImpl]configs);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]trustManagers != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]trustManagers.length != [CtLiteralImpl]1) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]trustManagers[[CtLiteralImpl]0] instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.net.ssl.X509TrustManager)))) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"A single X509TrustManager is expected. Unexpected trust managers: " + [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.toString([CtVariableReadImpl]trustManagers));
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]keyManagers != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]trustManagers != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.net.ssl.SSLContext sslContext = [CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.SSLContext.getInstance([CtLiteralImpl]"SSL");
                [CtInvocationImpl][CtVariableReadImpl]sslContext.init([CtVariableReadImpl]keyManagers, [CtVariableReadImpl]trustManagers, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.security.SecureRandom());
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]okHttpClientBuilder.sslSocketFactory([CtInvocationImpl][CtVariableReadImpl]sslContext.getSocketFactory(), [CtArrayReadImpl](([CtTypeReferenceImpl]javax.net.ssl.X509TrustManager) ([CtVariableReadImpl]trustManagers[[CtLiteralImpl]0])));
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]okHttpClientBuilder;
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]java.security.UnrecoverableKeyException | [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException | [CtTypeReferenceImpl]java.security.KeyStoreException | [CtTypeReferenceImpl]java.security.cert.CertificateException | [CtTypeReferenceImpl]java.security.KeyManagementException ex) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]javax.net.ssl.TrustManager[] getTrustManagers([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configs) throws [CtTypeReferenceImpl]java.security.KeyStoreException, [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException, [CtTypeReferenceImpl]java.security.cert.CertificateException [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]javax.net.ssl.TrustManager[] trustManagers = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]configs.containsKey([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_LOCATION)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String truststoreType = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.getOrDefault([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_TYPE, [CtLiteralImpl]"JKS")));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.KeyStore truststore = [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getInstance([CtVariableReadImpl]truststoreType);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String truststorePwd = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.getOrDefault([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_PASSWORD, [CtLiteralImpl]"")));
            [CtInvocationImpl][CtVariableReadImpl]truststore.load([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.get([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_TRUSTSTORE_LOCATION)))), [CtInvocationImpl][CtVariableReadImpl]truststorePwd.toCharArray());
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.net.ssl.TrustManagerFactory trustManagerFactory = [CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.TrustManagerFactory.getInstance([CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
            [CtInvocationImpl][CtVariableReadImpl]trustManagerFactory.init([CtVariableReadImpl]truststore);
            [CtAssignmentImpl][CtVariableWriteImpl]trustManagers = [CtInvocationImpl][CtVariableReadImpl]trustManagerFactory.getTrustManagers();
        }
        [CtReturnImpl]return [CtVariableReadImpl]trustManagers;
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]javax.net.ssl.KeyManager[] getKeyManagers([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> configs) throws [CtTypeReferenceImpl]java.security.KeyStoreException, [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException, [CtTypeReferenceImpl]java.security.cert.CertificateException, [CtTypeReferenceImpl]java.security.UnrecoverableKeyException [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]javax.net.ssl.KeyManager[] keyManagers = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]configs.containsKey([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_KEYSTORE_LOCATION)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keystoreType = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.getOrDefault([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_KEYSTORE_TYPE, [CtLiteralImpl]"JKS")));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.KeyStore keystore = [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getInstance([CtVariableReadImpl]keystoreType);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyStorePwd = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.getOrDefault([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_KEYSTORE_PASSWORD, [CtLiteralImpl]"")));
            [CtInvocationImpl][CtVariableReadImpl]keystore.load([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.get([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_KEYSTORE_LOCATION)))), [CtInvocationImpl][CtVariableReadImpl]keyStorePwd.toCharArray());
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.net.ssl.KeyManagerFactory keyManagerFactory = [CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.KeyManagerFactory.getInstance([CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
            [CtLocalVariableImpl][CtCommentImpl]// If no key password provided, try using the keystore password
            [CtTypeReferenceImpl]java.lang.String keyPwd = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]configs.getOrDefault([CtTypeAccessImpl]RestClientConfig.REGISTRY_REQUEST_KEY_PASSWORD, [CtVariableReadImpl]keyStorePwd)));
            [CtInvocationImpl][CtVariableReadImpl]keyManagerFactory.init([CtVariableReadImpl]keystore, [CtInvocationImpl][CtVariableReadImpl]keyPwd.toCharArray());
            [CtAssignmentImpl][CtVariableWriteImpl]keyManagers = [CtInvocationImpl][CtVariableReadImpl]keyManagerFactory.getKeyManagers();
        }
        [CtReturnImpl]return [CtVariableReadImpl]keyManagers;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void initServices([CtParameterImpl][CtTypeReferenceImpl]retrofit2.Retrofit retrofit) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]artifactsService = [CtInvocationImpl][CtVariableReadImpl]retrofit.create([CtFieldReadImpl]io.apicurio.registry.client.service.ArtifactsService.class);
        [CtAssignmentImpl][CtFieldWriteImpl]rulesService = [CtInvocationImpl][CtVariableReadImpl]retrofit.create([CtFieldReadImpl]io.apicurio.registry.client.service.RulesService.class);
        [CtAssignmentImpl][CtFieldWriteImpl]idsService = [CtInvocationImpl][CtVariableReadImpl]retrofit.create([CtFieldReadImpl]io.apicurio.registry.client.service.IdsService.class);
        [CtAssignmentImpl][CtFieldWriteImpl]searchService = [CtInvocationImpl][CtVariableReadImpl]retrofit.create([CtFieldReadImpl]io.apicurio.registry.client.service.SearchService.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> listArtifacts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.listArtifacts());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactMetaData createArtifact([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.ArtifactType artifactType, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.IfExistsType ifExistsType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.createArtifact([CtVariableReadImpl]artifactType, [CtVariableReadImpl]artifactId, [CtVariableReadImpl]ifExistsType, [CtInvocationImpl][CtTypeAccessImpl]okhttp3.RequestBody.create([CtInvocationImpl][CtTypeAccessImpl]okhttp3.MediaType.parse([CtLiteralImpl]"*/*"), [CtInvocationImpl][CtTypeAccessImpl]io.apicurio.registry.utils.IoUtil.toBytes([CtVariableReadImpl]data))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.io.InputStream getLatestArtifact([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getLatestArtifact([CtVariableReadImpl]artifactId)).byteStream();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactMetaData updateArtifact([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.ArtifactType artifactType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifact([CtVariableReadImpl]artifactId, [CtVariableReadImpl]artifactType, [CtInvocationImpl][CtTypeAccessImpl]okhttp3.RequestBody.create([CtInvocationImpl][CtTypeAccessImpl]okhttp3.MediaType.parse([CtLiteralImpl]"*/*"), [CtInvocationImpl][CtTypeAccessImpl]io.apicurio.registry.utils.IoUtil.toBytes([CtVariableReadImpl]data))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteArtifact([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.deleteArtifact([CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateArtifactState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.UpdateState data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifactState([CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactMetaData getArtifactMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getArtifactMetaData([CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateArtifactMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.EditableMetaData data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifactMetaData([CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactMetaData getArtifactMetaDataByContent([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getArtifactMetaDataByContent([CtVariableReadImpl]artifactId, [CtInvocationImpl][CtTypeAccessImpl]okhttp3.RequestBody.create([CtInvocationImpl][CtTypeAccessImpl]okhttp3.MediaType.parse([CtLiteralImpl]"*/*"), [CtInvocationImpl][CtTypeAccessImpl]io.apicurio.registry.utils.IoUtil.toBytes([CtVariableReadImpl]data))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Long> listArtifactVersions([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.listArtifactVersions([CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.VersionMetaData createArtifactVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.ArtifactType artifactType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.createArtifactVersion([CtVariableReadImpl]artifactId, [CtVariableReadImpl]artifactType, [CtInvocationImpl][CtTypeAccessImpl]okhttp3.RequestBody.create([CtInvocationImpl][CtTypeAccessImpl]okhttp3.MediaType.parse([CtLiteralImpl]"*/*"), [CtInvocationImpl][CtTypeAccessImpl]io.apicurio.registry.utils.IoUtil.toBytes([CtVariableReadImpl]data))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.io.InputStream getArtifactVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer version) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getArtifactVersion([CtVariableReadImpl]version, [CtVariableReadImpl]artifactId)).byteStream();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateArtifactVersionState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer version, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.UpdateState data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifactVersionState([CtVariableReadImpl]version, [CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.VersionMetaData getArtifactVersionMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer version) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getArtifactVersionMetaData([CtVariableReadImpl]version, [CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void updateArtifactVersionMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer version, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.EditableMetaData data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifactVersionMetaData([CtVariableReadImpl]version, [CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteArtifactVersionMetaData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer version) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.deleteArtifactVersionMetaData([CtVariableReadImpl]version, [CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.apicurio.registry.types.RuleType> listArtifactRules([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.listArtifactRules([CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createArtifactRule([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.createArtifactRule([CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteArtifactRules([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.deleteArtifactRules([CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule getArtifactRuleConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.getArtifactRuleConfig([CtVariableReadImpl]rule, [CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule updateArtifactRuleConfig([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.updateArtifactRuleConfig([CtVariableReadImpl]rule, [CtVariableReadImpl]artifactId, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteArtifactRule([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.deleteArtifactRule([CtVariableReadImpl]rule, [CtVariableReadImpl]artifactId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void testUpdateArtifact([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.ArtifactType artifactType, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]artifactsService.testUpdateArtifact([CtVariableReadImpl]artifactId, [CtVariableReadImpl]artifactType, [CtInvocationImpl][CtTypeAccessImpl]okhttp3.RequestBody.create([CtInvocationImpl][CtTypeAccessImpl]okhttp3.MediaType.parse([CtLiteralImpl]"*/*"), [CtInvocationImpl][CtTypeAccessImpl]io.apicurio.registry.utils.IoUtil.toBytes([CtVariableReadImpl]data))));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.io.InputStream getArtifactByGlobalId([CtParameterImpl][CtTypeReferenceImpl]long globalId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]idsService.getArtifactByGlobalId([CtVariableReadImpl]globalId)).byteStream();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactMetaData getArtifactMetaDataByGlobalId([CtParameterImpl][CtTypeReferenceImpl]long globalId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]idsService.getArtifactMetaDataByGlobalId([CtVariableReadImpl]globalId));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.ArtifactSearchResults searchArtifacts([CtParameterImpl][CtTypeReferenceImpl]java.lang.String search, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.SearchOver over, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.SortOrder order, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer offset, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer limit) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]searchService.searchArtifacts([CtVariableReadImpl]search, [CtVariableReadImpl]offset, [CtVariableReadImpl]limit, [CtVariableReadImpl]over, [CtVariableReadImpl]order));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.VersionSearchResults searchVersions([CtParameterImpl][CtTypeReferenceImpl]java.lang.String artifactId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer offset, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer limit) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]searchService.searchVersions([CtVariableReadImpl]artifactId, [CtVariableReadImpl]offset, [CtVariableReadImpl]limit));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule getGlobalRuleConfig([CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.getGlobalRuleConfig([CtVariableReadImpl]rule));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule updateGlobalRuleConfig([CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule, [CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule data) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.updateGlobalRuleConfig([CtVariableReadImpl]rule, [CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteGlobalRule([CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.types.RuleType rule) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.deleteGlobalRule([CtVariableReadImpl]rule));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.apicurio.registry.types.RuleType> listGlobalRules() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.listGlobalRules());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createGlobalRule([CtParameterImpl][CtTypeReferenceImpl]io.apicurio.registry.rest.beans.Rule data) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.createGlobalRule([CtVariableReadImpl]data));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void deleteAllGlobalRules() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]requestHandler.execute([CtInvocationImpl][CtFieldReadImpl]rulesService.deleteAllGlobalRules());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]httpClient.dispatcher().executorService().shutdown();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]httpClient.connectionPool().evictAll();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]httpClient.cache() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]httpClient.cache().close();
        }
    }
}
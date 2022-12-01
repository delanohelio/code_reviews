[CompilationUnitImpl][CtPackageDeclarationImpl]package io.quarkus.spring.cloud.config.client.runtime;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.http.client.protocol.HttpClientContext;
[CtImportImpl]import java.net.URISyntaxException;
[CtImportImpl]import java.security.NoSuchAlgorithmException;
[CtImportImpl]import java.security.cert.CertificateException;
[CtUnresolvedImport]import org.apache.http.client.config.RequestConfig;
[CtImportImpl]import java.net.URI;
[CtImportImpl]import com.fasterxml.jackson.databind.DeserializationFeature;
[CtImportImpl]import java.security.KeyManagementException;
[CtUnresolvedImport]import org.apache.http.util.EntityUtils;
[CtUnresolvedImport]import org.apache.http.impl.client.BasicCredentialsProvider;
[CtImportImpl]import java.nio.file.Files;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.apache.http.client.methods.CloseableHttpResponse;
[CtImportImpl]import java.security.UnrecoverableKeyException;
[CtUnresolvedImport]import org.apache.http.ssl.SSLContexts;
[CtUnresolvedImport]import org.apache.http.auth.AuthScheme;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.http.conn.ssl.NoopHostnameVerifier;
[CtUnresolvedImport]import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
[CtUnresolvedImport]import org.apache.http.client.AuthCache;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtUnresolvedImport]import org.apache.http.impl.auth.BasicScheme;
[CtUnresolvedImport]import org.apache.http.impl.client.CloseableHttpClient;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.apache.http.client.utils.URIBuilder;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.security.KeyStoreException;
[CtUnresolvedImport]import org.apache.http.HttpEntity;
[CtImportImpl]import java.security.KeyStore;
[CtUnresolvedImport]import org.apache.http.ssl.SSLContextBuilder;
[CtUnresolvedImport]import org.apache.http.auth.UsernamePasswordCredentials;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpGet;
[CtUnresolvedImport]import org.apache.http.auth.AuthScope;
[CtUnresolvedImport]import org.apache.http.impl.client.HttpClientBuilder;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.http.client.CredentialsProvider;
[CtUnresolvedImport]import org.apache.http.HttpHost;
[CtUnresolvedImport]import org.apache.http.conn.ssl.TrustAllStrategy;
[CtClassImpl]class DefaultSpringCloudConfigClientGateway implements [CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.SpringCloudConfigClientGateway {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper().configure([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.databind.DeserializationFeature.[CtFieldReferenceImpl]FAIL_ON_UNKNOWN_PROPERTIES, [CtLiteralImpl]false);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.SpringCloudConfigClientConfig springCloudConfigClientConfig;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.http.conn.ssl.SSLConnectionSocketFactory sslSocketFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.net.URI baseURI;

    [CtConstructorImpl]public DefaultSpringCloudConfigClientGateway([CtParameterImpl][CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.SpringCloudConfigClientConfig springCloudConfigClientConfig) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.springCloudConfigClientConfig = [CtVariableReadImpl]springCloudConfigClientConfig;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseURI = [CtInvocationImpl]determineBaseUri([CtVariableReadImpl]springCloudConfigClientConfig);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.net.URISyntaxException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Value: '" + [CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.url) + [CtLiteralImpl]"' of property 'quarkus.spring-cloud-config.url' is invalid", [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustStore.isPresent() || [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyStore.isPresent()) || [CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustCerts) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sslSocketFactory = [CtInvocationImpl]createFactoryFromAgentConfig([CtVariableReadImpl]springCloudConfigClientConfig);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sslSocketFactory = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.net.URI determineBaseUri([CtParameterImpl][CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.SpringCloudConfigClientConfig springCloudConfigClientConfig) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.url;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]url) || [CtInvocationImpl][CtVariableReadImpl]url.isEmpty()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"The 'quarkus.spring-cloud-config.url' property cannot be empty");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.endsWith([CtLiteralImpl]"/")) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtInvocationImpl][CtVariableReadImpl]url.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]url.length() - [CtLiteralImpl]1));
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtVariableReadImpl]url);
    }

    [CtMethodImpl][CtCommentImpl]// The SSL code is basically a copy of the code in the Consul extension
    [CtCommentImpl]// Normally we would consider moving this code to one place, but as I want
    [CtCommentImpl]// to stop using Apache HTTP Client when we move to JDK 11, lets not do the
    [CtCommentImpl]// extra work
    private [CtTypeReferenceImpl]org.apache.http.conn.ssl.SSLConnectionSocketFactory createFactoryFromAgentConfig([CtParameterImpl][CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.SpringCloudConfigClientConfig springCloudConfigClientConfig) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.ssl.SSLContextBuilder sslContextBuilder = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.ssl.SSLContexts.custom();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustStore.isPresent()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sslContextBuilder = [CtInvocationImpl][CtVariableReadImpl]sslContextBuilder.loadTrustMaterial([CtInvocationImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.readStore([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustStore.get(), [CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustStorePassword), [CtLiteralImpl]null);
            } else [CtIfImpl]if ([CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.trustCerts) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sslContextBuilder = [CtInvocationImpl][CtVariableReadImpl]sslContextBuilder.loadTrustMaterial([CtTypeAccessImpl]TrustAllStrategy.INSTANCE);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyStore.isPresent()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyPassword = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyPassword.orElse([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyStorePassword.orElse([CtLiteralImpl]""));
                [CtAssignmentImpl][CtVariableWriteImpl]sslContextBuilder = [CtInvocationImpl][CtVariableReadImpl]sslContextBuilder.loadKeyMaterial([CtInvocationImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.readStore([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyStore.get(), [CtFieldReadImpl][CtVariableReadImpl]springCloudConfigClientConfig.keyStorePassword), [CtInvocationImpl][CtVariableReadImpl]keyPassword.toCharArray());
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.conn.ssl.SSLConnectionSocketFactory([CtInvocationImpl][CtVariableReadImpl]sslContextBuilder.build(), [CtFieldReadImpl]org.apache.http.conn.ssl.NoopHostnameVerifier.INSTANCE);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.security.NoSuchAlgorithmException | [CtTypeReferenceImpl]java.security.KeyManagementException | [CtTypeReferenceImpl]java.security.KeyStoreException | [CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]java.security.cert.CertificateException | [CtTypeReferenceImpl]java.security.UnrecoverableKeyException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String findKeystoreFileType([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path keyStorePath) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pathName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyStorePath.toString().toLowerCase();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]pathName.endsWith([CtLiteralImpl]".p12") || [CtInvocationImpl][CtVariableReadImpl]pathName.endsWith([CtLiteralImpl]".pkcs12")) || [CtInvocationImpl][CtVariableReadImpl]pathName.endsWith([CtLiteralImpl]".pfx")) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"PKS12";
        }
        [CtReturnImpl]return [CtLiteralImpl]"JKS";
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.security.KeyStore readStore([CtParameterImpl][CtTypeReferenceImpl]java.nio.file.Path keyStorePath, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> keyStorePassword) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.security.KeyStoreException, [CtTypeReferenceImpl]java.security.cert.CertificateException, [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyStoreType = [CtInvocationImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.findKeystoreFileType([CtVariableReadImpl]keyStorePath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream classPathResource = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader().getResourceAsStream([CtInvocationImpl][CtVariableReadImpl]keyStorePath.toString());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classPathResource != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtVariableReadImpl]classPathResource) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.doReadStore([CtVariableReadImpl]is, [CtVariableReadImpl]keyStoreType, [CtVariableReadImpl]keyStorePassword);
            }
        } else [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.newInputStream([CtVariableReadImpl]keyStorePath)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.doReadStore([CtVariableReadImpl]is, [CtVariableReadImpl]keyStoreType, [CtVariableReadImpl]keyStorePassword);
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.security.KeyStore doReadStore([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream keyStoreStream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String keyStoreType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> keyStorePassword) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.security.KeyStoreException, [CtTypeReferenceImpl]java.security.cert.CertificateException, [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.KeyStore keyStore = [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getInstance([CtVariableReadImpl]keyStoreType);
        [CtInvocationImpl][CtVariableReadImpl]keyStore.load([CtVariableReadImpl]keyStoreStream, [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]keyStorePassword.isPresent() ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]keyStorePassword.get().toCharArray() : [CtLiteralImpl]null);
        [CtReturnImpl]return [CtVariableReadImpl]keyStore;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.Response exchange([CtParameterImpl][CtTypeReferenceImpl]java.lang.String applicationName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String profile) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.config.RequestConfig requestConfig = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.http.client.config.RequestConfig.custom().setConnectionRequestTimeout([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.connectionTimeout.toMillis()))).setSocketTimeout([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.readTimeout.toMillis()))).build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.impl.client.HttpClientBuilder httpClientBuilder = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.http.impl.client.HttpClientBuilder.create().setDefaultRequestConfig([CtVariableReadImpl]requestConfig);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]sslSocketFactory != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]httpClientBuilder.setSSLSocketFactory([CtFieldReadImpl]sslSocketFactory);
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.impl.client.CloseableHttpClient client = [CtInvocationImpl][CtVariableReadImpl]httpClientBuilder.build()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.net.URI finalURI = [CtInvocationImpl]finalURI([CtVariableReadImpl]applicationName, [CtVariableReadImpl]profile);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtVariableReadImpl]finalURI);
            [CtInvocationImpl][CtVariableReadImpl]request.addHeader([CtLiteralImpl]"Accept", [CtLiteralImpl]"application/json");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.headers.entrySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]request.setHeader([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.protocol.HttpClientContext context = [CtInvocationImpl]setupContext([CtVariableReadImpl]finalURI);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.CloseableHttpResponse response = [CtInvocationImpl][CtVariableReadImpl]client.execute([CtVariableReadImpl]request, [CtVariableReadImpl]context)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode() != [CtLiteralImpl]200) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Got unexpected HTTP response code " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getStatusLine().getStatusCode()) + [CtLiteralImpl]" from ") + [CtVariableReadImpl]finalURI);
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.HttpEntity entity = [CtInvocationImpl][CtVariableReadImpl]response.getEntity();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Got empty HTTP response body " + [CtVariableReadImpl]finalURI);
                }
                [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.OBJECT_MAPPER.readValue([CtInvocationImpl][CtTypeAccessImpl]org.apache.http.util.EntityUtils.toString([CtVariableReadImpl]entity), [CtFieldReadImpl]io.quarkus.spring.cloud.config.client.runtime.Response.class);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.net.URI finalURI([CtParameterImpl][CtTypeReferenceImpl]java.lang.String applicationName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String profile) throws [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.utils.URIBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.utils.URIBuilder([CtFieldReadImpl]baseURI);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.getPort() == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// we need to set the port otherwise auth case doesn't match the request
            [CtVariableReadImpl]result.setPort([CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getScheme().equalsIgnoreCase([CtLiteralImpl]"http") ? [CtLiteralImpl]80 : [CtLiteralImpl]443);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> finalPathSegments = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]result.getPathSegments());
        [CtInvocationImpl][CtVariableReadImpl]finalPathSegments.add([CtVariableReadImpl]applicationName);
        [CtInvocationImpl][CtVariableReadImpl]finalPathSegments.add([CtVariableReadImpl]profile);
        [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.label.ifPresent([CtLambdaImpl]([CtParameterImpl] label) -> [CtInvocationImpl][CtVariableReadImpl]finalPathSegments.add([CtVariableReadImpl]label));
        [CtInvocationImpl][CtVariableReadImpl]result.setPathSegments([CtVariableReadImpl]finalPathSegments);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.build();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.http.client.protocol.HttpClientContext setupContext([CtParameterImpl][CtTypeReferenceImpl]java.net.URI finalURI) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.protocol.HttpClientContext context = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.client.protocol.HttpClientContext.create();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]baseURI.getUserInfo() != [CtLiteralImpl]null) || [CtInvocationImpl][CtFieldReadImpl]springCloudConfigClientConfig.usernameAndPasswordSet()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.AuthCache authCache = [CtFieldReadImpl][CtTypeAccessImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.InMemoryAuthCache.[CtFieldReferenceImpl]INSTANCE;
            [CtInvocationImpl][CtVariableReadImpl]authCache.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.HttpHost([CtInvocationImpl][CtVariableReadImpl]finalURI.getHost(), [CtInvocationImpl][CtVariableReadImpl]finalURI.getPort(), [CtInvocationImpl][CtVariableReadImpl]finalURI.getScheme()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.impl.auth.BasicScheme());
            [CtInvocationImpl][CtVariableReadImpl]context.setAuthCache([CtVariableReadImpl]authCache);
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]springCloudConfigClientConfig.usernameAndPasswordSet()) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.CredentialsProvider provider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.impl.client.BasicCredentialsProvider();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.auth.UsernamePasswordCredentials credentials = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.auth.UsernamePasswordCredentials([CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.username.get(), [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]springCloudConfigClientConfig.password.get());
                [CtInvocationImpl][CtVariableReadImpl]provider.setCredentials([CtTypeAccessImpl]AuthScope.ANY, [CtVariableReadImpl]credentials);
                [CtInvocationImpl][CtVariableReadImpl]context.setCredentialsProvider([CtVariableReadImpl]provider);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]context;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * We need this class in order to avoid the serialization that Apache HTTP client does by default
     * and that does not work in GraalVM.
     * We don't care about caching the auth result since one call is only ever going to be made in any case
     */
    private static class InMemoryAuthCache implements [CtTypeReferenceImpl]org.apache.http.client.AuthCache {
        [CtFieldImpl]static final [CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.InMemoryAuthCache INSTANCE = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.quarkus.spring.cloud.config.client.runtime.DefaultSpringCloudConfigClientGateway.InMemoryAuthCache();

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.http.HttpHost, [CtTypeReferenceImpl]org.apache.http.auth.AuthScheme> map = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

        [CtConstructorImpl]private InMemoryAuthCache() [CtBlockImpl]{
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void put([CtParameterImpl][CtTypeReferenceImpl]org.apache.http.HttpHost host, [CtParameterImpl][CtTypeReferenceImpl]org.apache.http.auth.AuthScheme authScheme) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]map.put([CtVariableReadImpl]host, [CtVariableReadImpl]authScheme);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.apache.http.auth.AuthScheme get([CtParameterImpl][CtTypeReferenceImpl]org.apache.http.HttpHost host) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]map.get([CtVariableReadImpl]host);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void remove([CtParameterImpl][CtTypeReferenceImpl]org.apache.http.HttpHost host) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]map.remove([CtVariableReadImpl]host);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]map.clear();
        }
    }
}
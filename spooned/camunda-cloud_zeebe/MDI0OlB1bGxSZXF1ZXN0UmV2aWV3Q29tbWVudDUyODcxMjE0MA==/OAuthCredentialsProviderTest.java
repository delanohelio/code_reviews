[CompilationUnitImpl][CtCommentImpl]/* Copyright © 2017 camunda services GmbH (info@camunda.com)

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
[CtPackageDeclarationImpl]package io.zeebe.client;
[CtUnresolvedImport]import io.zeebe.client.impl.oauth.OAuthCredentialsCache;
[CtUnresolvedImport]import io.grpc.ServerInterceptors;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.net.MalformedURLException;
[CtImportImpl]import java.io.UncheckedIOException;
[CtImportImpl]import java.time.ZonedDateTime;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.matching;
[CtUnresolvedImport]import io.grpc.Status;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
[CtUnresolvedImport]import static io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder.OAUTH_ENV_CLIENT_ID;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.time.Instant;
[CtImportImpl]import java.io.UnsupportedEncodingException;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import io.grpc.Metadata.Key;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import io.zeebe.client.util.Environment;
[CtImportImpl]import java.net.SocketTimeoutException;
[CtUnresolvedImport]import static io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder.OAUTH_ENV_AUTHORIZATION_SERVER;
[CtUnresolvedImport]import io.grpc.ServerCall;
[CtUnresolvedImport]import io.grpc.testing.GrpcServerRule;
[CtUnresolvedImport]import io.grpc.Metadata;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.any;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
[CtUnresolvedImport]import io.zeebe.client.api.command.ClientException;
[CtUnresolvedImport]import io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
[CtUnresolvedImport]import io.zeebe.client.impl.ZeebeClientImpl;
[CtUnresolvedImport]import com.github.tomakehurst.wiremock.client.WireMock;
[CtUnresolvedImport]import io.grpc.Status.Code;
[CtUnresolvedImport]import com.github.tomakehurst.wiremock.junit.WireMockRule;
[CtUnresolvedImport]import org.mockito.ArgumentCaptor;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThatThrownBy;
[CtImportImpl]import java.time.temporal.ChronoField;
[CtUnresolvedImport]import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
[CtImportImpl]import java.net.URLEncoder;
[CtUnresolvedImport]import org.junit.Before;
[CtUnresolvedImport]import static io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder.OAUTH_ENV_CLIENT_SECRET;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
[CtUnresolvedImport]import static org.mockito.Mockito.verify;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static org.mockito.Mockito.times;
[CtImportImpl]import java.time.ZoneId;
[CtUnresolvedImport]import static com.github.tomakehurst.wiremock.client.WireMock.verify;
[CtUnresolvedImport]import io.zeebe.client.impl.ZeebeClientCredentials;
[CtUnresolvedImport]import org.mockito.Mockito;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import io.zeebe.client.impl.ZeebeClientBuilderImpl;
[CtUnresolvedImport]import io.zeebe.client.util.EnvironmentRule;
[CtImportImpl]import java.util.function.BiConsumer;
[CtUnresolvedImport]import io.zeebe.client.util.RecordingGatewayService;
[CtUnresolvedImport]import org.junit.rules.TemporaryFolder;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"ResultOfMethodCallIgnored")
public final class OAuthCredentialsProviderTest {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.time.ZonedDateTime EXPIRY = [CtInvocationImpl][CtTypeAccessImpl]java.time.ZonedDateTime.of([CtLiteralImpl]3020, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.of([CtLiteralImpl]"Z"));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.grpc.Metadata.Key<[CtTypeReferenceImpl]java.lang.String> AUTH_KEY = [CtInvocationImpl][CtTypeAccessImpl]io.grpc.Metadata.Key.of([CtLiteralImpl]"Authorization", [CtTypeAccessImpl]Metadata.ASCII_STRING_MARSHALLER);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SECRET = [CtLiteralImpl]"secret";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AUDIENCE = [CtLiteralImpl]"endpoint";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ACCESS_TOKEN = [CtLiteralImpl]"someToken";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TOKEN_TYPE = [CtLiteralImpl]"Bearer";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CLIENT_ID = [CtLiteralImpl]"client";

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]io.grpc.testing.GrpcServerRule serverRule = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.grpc.testing.GrpcServerRule();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder tempFolder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]io.zeebe.client.util.EnvironmentRule environmentRule = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.util.EnvironmentRule();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public final [CtTypeReferenceImpl]com.github.tomakehurst.wiremock.junit.WireMockRule wireMockRule = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.tomakehurst.wiremock.junit.WireMockRule([CtInvocationImpl][CtInvocationImpl]wireMockConfig().dynamicPort());

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.zeebe.client.RecordingInterceptor recordingInterceptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.RecordingInterceptor();

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.zeebe.client.util.RecordingGatewayService gatewayService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.util.RecordingGatewayService();

    [CtFieldImpl]private [CtTypeReferenceImpl]io.zeebe.client.ZeebeClient client;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String cachedUserHome;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]serverRule.getServiceRegistry().addService([CtInvocationImpl][CtTypeAccessImpl]io.grpc.ServerInterceptors.intercept([CtFieldReadImpl]gatewayService, [CtFieldReadImpl]recordingInterceptor));
        [CtAssignmentImpl][CtCommentImpl]// necessary when testing defaults to ensure we don't reuse the cache
        [CtFieldWriteImpl]cachedUserHome = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user.home");
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"user.home", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.getRoot().getAbsolutePath());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void tearDown() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]client != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]client.close();
            [CtAssignmentImpl][CtFieldWriteImpl]client = [CtLiteralImpl]null;
        }
        [CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.reset();
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.setProperty([CtLiteralImpl]"user.home", [CtFieldReadImpl]cachedUserHome);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldRequestTokenAndAddToCall() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.newFile().getPath()).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.getCapturedHeaders().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldRetryRequestWithNewCredentials() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]java.lang.String firstToken = [CtLiteralImpl]"firstToken";
        [CtInvocationImpl]mockCredentials([CtVariableReadImpl]firstToken);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]io.grpc.ServerCall, [CtTypeReferenceImpl]io.grpc.Metadata> interceptAction = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.spy([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]io.grpc.ServerCall, [CtTypeReferenceImpl]io.grpc.Metadata>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void accept([CtParameterImpl]final [CtTypeReferenceImpl]io.grpc.ServerCall call, [CtParameterImpl]final [CtTypeReferenceImpl]io.grpc.Metadata headers) [CtBlockImpl]{
                [CtInvocationImpl]mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
                [CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.reset();
                [CtInvocationImpl][CtVariableReadImpl]call.close([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.grpc.Status.fromCode([CtTypeAccessImpl]Code.UNAUTHENTICATED).augmentDescription([CtLiteralImpl]"Stale token"), [CtVariableReadImpl]headers);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.setInterceptAction([CtVariableReadImpl]interceptAction);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.newFile().getPath()).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtLocalVariableImpl][CtCommentImpl]// then
        final [CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]io.grpc.Metadata> captor = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.ArgumentCaptor.forClass([CtFieldReadImpl]io.grpc.Metadata.class);
        [CtInvocationImpl][CtInvocationImpl]verify([CtVariableReadImpl]interceptAction, [CtInvocationImpl]Mockito.times([CtLiteralImpl]1)).accept([CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.grpc.ServerCall.class), [CtInvocationImpl][CtVariableReadImpl]captor.capture());
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]captor.getValue().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtVariableReadImpl]firstToken);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.getCapturedHeaders().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldNotRetryWithSameCredentials() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]io.grpc.ServerCall, [CtTypeReferenceImpl]io.grpc.Metadata> interceptAction = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.spy([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.function.BiConsumer<[CtTypeReferenceImpl]io.grpc.ServerCall, [CtTypeReferenceImpl]io.grpc.Metadata>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void accept([CtParameterImpl]final [CtTypeReferenceImpl]io.grpc.ServerCall call, [CtParameterImpl]final [CtTypeReferenceImpl]io.grpc.Metadata headers) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]call.close([CtTypeAccessImpl]Status.UNAUTHENTICATED, [CtVariableReadImpl]headers);
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.setInterceptAction([CtVariableReadImpl]interceptAction);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.newFile().getPath()).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]client.newTopologyRequest().send().join()).isInstanceOf([CtFieldReadImpl]io.zeebe.client.api.command.ClientException.class);
        [CtInvocationImpl][CtInvocationImpl]verify([CtVariableReadImpl]interceptAction, [CtInvocationImpl]Mockito.times([CtLiteralImpl]1)).accept([CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.grpc.ServerCall.class), [CtInvocationImpl]ArgumentMatchers.any([CtFieldReadImpl]io.grpc.Metadata.class));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldUseClientContactPointAsDefaultAudience() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]java.lang.String contactPointHost = [CtLiteralImpl]"some.domain";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String authorizationServerUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token";
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.zeebe.client.util.Environment.system().put([CtTypeAccessImpl]io.zeebe.client.OAUTH_ENV_CLIENT_ID, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.zeebe.client.util.Environment.system().put([CtTypeAccessImpl]io.zeebe.client.OAUTH_ENV_CLIENT_SECRET, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.zeebe.client.util.Environment.system().put([CtTypeAccessImpl]io.zeebe.client.OAUTH_ENV_AUTHORIZATION_SERVER, [CtVariableReadImpl]authorizationServerUrl);
        [CtInvocationImpl]mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN, [CtVariableReadImpl]contactPointHost);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().gatewayAddress([CtBinaryOperatorImpl][CtVariableReadImpl]contactPointHost + [CtLiteralImpl]":26500").build().close();
        [CtAssignmentImpl][CtCommentImpl]// when
        [CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldUseCachedCredentials() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String cachePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.getRoot().getPath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]".credsCache";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache cache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]cachePath));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cache.put([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.EXPIRY, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE)).writeCache();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtVariableReadImpl]cachePath).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.getCapturedHeaders().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtInvocationImpl]verify([CtLiteralImpl]0, [CtInvocationImpl]postRequestedFor([CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo([CtLiteralImpl]"/oauth/token")));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldCacheAndReuseCredentials() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String cachePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.getRoot().getPath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]".credsCache";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder credsBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtVariableReadImpl]cachePath);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtVariableReadImpl]credsBuilder.build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtInvocationImpl]verify([CtLiteralImpl]1, [CtInvocationImpl]postRequestedFor([CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo([CtLiteralImpl]"/oauth/token")));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtVariableReadImpl]credsBuilder.build());
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.getCapturedHeaders().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtInvocationImpl]verify([CtLiteralImpl]1, [CtInvocationImpl]postRequestedFor([CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo([CtLiteralImpl]"/oauth/token")));
        [CtInvocationImpl]assertCacheContents([CtVariableReadImpl]cachePath);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldUpdateCacheIfStale() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.setInterceptAction([CtLambdaImpl]([CtParameterImpl] call,[CtParameterImpl] metadata) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String authHeader = [CtInvocationImpl][CtVariableReadImpl]metadata.get([CtFieldReadImpl][CtFieldReferenceImpl]AUTH_KEY);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]authHeader != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]authHeader.endsWith([CtLiteralImpl]"staleToken")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]call.close([CtVariableReadImpl]Status.UNAUTHENTICATED, [CtVariableReadImpl]metadata);
            }
        });
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String cachePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.getRoot().getPath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]".credsCache";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache cache = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]cachePath));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cache.put([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientCredentials([CtLiteralImpl]"staleToken", [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.EXPIRY, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE)).writeCache();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").credentialsCachePath([CtVariableReadImpl]cachePath).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]client.newTopologyRequest().send().join();
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]recordingInterceptor.getCapturedHeaders().get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUTH_KEY)).isEqualTo([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE + [CtLiteralImpl]" ") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN);
        [CtInvocationImpl]verify([CtLiteralImpl]1, [CtInvocationImpl]postRequestedFor([CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo([CtLiteralImpl]"/oauth/token")));
        [CtInvocationImpl]assertCacheContents([CtVariableReadImpl]cachePath);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWithNoAudience() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtLiteralImpl]"a").clientSecret([CtLiteralImpl]"b").authorizationServerUrl([CtLiteralImpl]"http://some.url").build()).isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class).hasMessageEndingWith([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtTypeAccessImpl]OAuthCredentialsProviderBuilder.INVALID_ARGUMENT_MSG, [CtLiteralImpl]"audience"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWithNoClientId() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().audience([CtLiteralImpl]"a").clientSecret([CtLiteralImpl]"b").authorizationServerUrl([CtLiteralImpl]"http://some.url").build()).isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class).hasMessageEndingWith([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtTypeAccessImpl]OAuthCredentialsProviderBuilder.INVALID_ARGUMENT_MSG, [CtLiteralImpl]"client id"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWithNoClientSecret() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().audience([CtLiteralImpl]"a").clientId([CtLiteralImpl]"b").authorizationServerUrl([CtLiteralImpl]"http://some.url").build()).isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class).hasMessageEndingWith([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtTypeAccessImpl]OAuthCredentialsProviderBuilder.INVALID_ARGUMENT_MSG, [CtLiteralImpl]"client secret"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailWithMalformedServerUrl() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().audience([CtLiteralImpl]"a").clientId([CtLiteralImpl]"b").clientSecret([CtLiteralImpl]"c").authorizationServerUrl([CtLiteralImpl]"someServerUrl").build()).isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class).hasCauseInstanceOf([CtFieldReadImpl]java.net.MalformedURLException.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailIfSpecifiedCacheIsDir() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]java.lang.String cachePath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]tempFolder.getRoot().getAbsolutePath() + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"404_folder";
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]cachePath).mkdir();
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().audience([CtFieldReadImpl][CtFieldReferenceImpl]AUDIENCE).clientId([CtFieldReadImpl][CtFieldReferenceImpl]CLIENT_ID).clientSecret([CtFieldReadImpl][CtFieldReferenceImpl]SECRET).authorizationServerUrl([CtLiteralImpl]"http://localhost").credentialsCachePath([CtVariableReadImpl]cachePath).build()).isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class).hasMessage([CtLiteralImpl]"Expected specified credentials cache to be a file but found directory instead.");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldThrowExceptionIfTimeout() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// given
        mockCredentials([CtLiteralImpl]10);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID).clientSecret([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET).audience([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").readTimeout([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofMillis([CtLiteralImpl]5)).build()).build().close();
        [CtAssignmentImpl][CtFieldWriteImpl]client = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientImpl([CtVariableReadImpl]builder, [CtInvocationImpl][CtFieldReadImpl]serverRule.getChannel());
        [CtInvocationImpl][CtCommentImpl]// when
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]client.newTopologyRequest().send().join()).hasRootCauseExactlyInstanceOf([CtFieldReadImpl]java.net.SocketTimeoutException.class).hasRootCauseMessage([CtLiteralImpl]"Read timed out");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldThrowExceptionIfTimeoutIsZero() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl().usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl][CtFieldReferenceImpl]CLIENT_ID).clientSecret([CtFieldReadImpl][CtFieldReferenceImpl]SECRET).audience([CtFieldReadImpl][CtFieldReferenceImpl]AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").readTimeout([CtVariableReadImpl]Duration.ZERO).build()).build().close()).hasMessageContaining([CtLiteralImpl]"Expected readTimeout to be is more that 0.").isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldThrowExceptionIfTimeoutTooLarge() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// when/then
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientBuilderImpl().usePlaintext().credentialsProvider([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder().clientId([CtFieldReadImpl][CtFieldReferenceImpl]CLIENT_ID).clientSecret([CtFieldReadImpl][CtFieldReferenceImpl]SECRET).audience([CtFieldReadImpl][CtFieldReferenceImpl]AUDIENCE).authorizationServerUrl([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"http://localhost:" + [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]wireMockRule.port()) + [CtLiteralImpl]"/oauth/token").readTimeout([CtInvocationImpl][CtTypeAccessImpl]java.time.Duration.ofDays([CtLiteralImpl]1000000)).build()).build().close()).hasMessageContaining([CtLiteralImpl]"Expected readTimeout to be in range of integer milliseconds.").isInstanceOf([CtFieldReadImpl]java.lang.IllegalArgumentException.class);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Mocks an authorization server that returns credentials with the provided access token. Returns
     * the credentials to be return by the server.
     */
    private [CtTypeReferenceImpl]void mockCredentials([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String accessToken) [CtBlockImpl]{
        [CtInvocationImpl]mockCredentials([CtVariableReadImpl]accessToken, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void mockCredentials([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer readDelay) [CtBlockImpl]{
        [CtInvocationImpl]mockCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN, [CtVariableReadImpl]readDelay);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void mockCredentials([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String accessToken, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer readDelay) [CtBlockImpl]{
        [CtInvocationImpl]mockCredentials([CtVariableReadImpl]accessToken, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE, [CtVariableReadImpl]readDelay);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void mockCredentials([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String accessToken, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String audience) [CtBlockImpl]{
        [CtInvocationImpl]mockCredentials([CtVariableReadImpl]accessToken, [CtVariableReadImpl]audience, [CtLiteralImpl]0);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void mockCredentials([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String accessToken, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String audience, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Integer readDelay) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> map = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]map.put([CtLiteralImpl]"client_secret", [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.SECRET);
        [CtInvocationImpl][CtVariableReadImpl]map.put([CtLiteralImpl]"client_id", [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.CLIENT_ID);
        [CtInvocationImpl][CtVariableReadImpl]map.put([CtLiteralImpl]"audience", [CtVariableReadImpl]audience);
        [CtInvocationImpl][CtVariableReadImpl]map.put([CtLiteralImpl]"grant_type", [CtLiteralImpl]"client_credentials");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String encodedBody = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]map.entrySet().stream().map([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, java.lang.String> e) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]io.zeebe.client.OAuthCredentialsProviderTest.encode([CtInvocationImpl][CtVariableReadImpl]e.getKey()) + [CtLiteralImpl]"=") + [CtInvocationImpl]io.zeebe.client.OAuthCredentialsProviderTest.encode([CtInvocationImpl][CtVariableReadImpl]e.getValue())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]"&"));
        [CtInvocationImpl][CtFieldReadImpl]wireMockRule.stubFor([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.post([CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo([CtLiteralImpl]"/oauth/token")).withHeader([CtLiteralImpl]"Content-Type", [CtInvocationImpl]equalTo([CtLiteralImpl]"application/x-www-form-urlencoded")).withHeader([CtLiteralImpl]"Accept", [CtInvocationImpl]equalTo([CtLiteralImpl]"application/json")).withHeader([CtLiteralImpl]"User-Agent", [CtInvocationImpl]matching([CtLiteralImpl]"zeebe-client-java/\\d+\\.\\d+\\.\\d+.*")).withRequestBody([CtInvocationImpl]equalTo([CtVariableReadImpl]encodedBody)).willReturn([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.github.tomakehurst.wiremock.client.WireMock.aResponse().withBody([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"{\"access_token\":\"" + [CtVariableReadImpl]accessToken) + [CtLiteralImpl]"\",\"token_type\":\"") + [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE) + [CtLiteralImpl]"\",\"expires_in\":") + [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.EXPIRY.getLong([CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoField.[CtFieldReferenceImpl]INSTANT_SECONDS) - [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now().getEpochSecond())) + [CtLiteralImpl]",\"scope\": \"") + [CtVariableReadImpl]audience) + [CtLiteralImpl]"\"}").withFixedDelay([CtVariableReadImpl]readDelay).withStatus([CtLiteralImpl]200)));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String encode([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String param) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.net.URLEncoder.encode([CtVariableReadImpl]param, [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8.name());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.UnsupportedEncodingException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtLiteralImpl]"Failed while encoding OAuth request parameters: ", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assertCacheContents([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String cachePath) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache cache = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.oauth.OAuthCredentialsCache([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]cachePath)).readCache();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientCredentials credentials = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.zeebe.client.impl.ZeebeClientCredentials([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.ACCESS_TOKEN, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.EXPIRY, [CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.TOKEN_TYPE);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]cache.get([CtFieldReadImpl]io.zeebe.client.OAuthCredentialsProviderTest.AUDIENCE)).contains([CtVariableReadImpl]credentials);
    }
}
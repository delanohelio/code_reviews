[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

    http://www.apache.org/licenses/LICENSE-2.0

or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.ad;
[CtUnresolvedImport]import org.elasticsearch.client.RestClient;
[CtUnresolvedImport]import org.elasticsearch.client.RestClientBuilder;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.XContentType;
[CtUnresolvedImport]import org.elasticsearch.common.util.concurrent.ThreadContext;
[CtUnresolvedImport]import org.apache.http.Header;
[CtUnresolvedImport]import org.apache.http.impl.client.BasicCredentialsProvider;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.DeprecationHandler;
[CtUnresolvedImport]import org.elasticsearch.common.settings.Settings;
[CtUnresolvedImport]import org.elasticsearch.common.unit.TimeValue;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import org.elasticsearch.test.rest.ESRestTestCase;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.http.conn.ssl.NoopHostnameVerifier;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.NamedXContentRegistry;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.apache.http.ssl.SSLContextBuilder;
[CtUnresolvedImport]import org.elasticsearch.client.Request;
[CtUnresolvedImport]import org.apache.http.auth.UsernamePasswordCredentials;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.XContentParser;
[CtUnresolvedImport]import org.apache.http.auth.AuthScope;
[CtUnresolvedImport]import org.elasticsearch.client.Response;
[CtUnresolvedImport]import org.apache.http.message.BasicHeader;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.http.client.CredentialsProvider;
[CtUnresolvedImport]import org.apache.http.HttpHost;
[CtClassImpl][CtJavaDocImpl]/**
 * ODFE integration test base class to support both security disabled and enabled ODFE cluster.
 */
public abstract class ODFERestTestCase extends [CtTypeReferenceImpl]org.elasticsearch.test.rest.ESRestTestCase {
    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean isHttps() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isHttps = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"https")).map([CtExecutableReferenceExpressionImpl][CtLiteralImpl]"true"::equalsIgnoreCase).orElse([CtLiteralImpl]false);
        [CtIfImpl]if ([CtVariableReadImpl]isHttps) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// currently only external cluster is supported for security enabled testing
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"tests.rest.cluster")).isPresent()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"external cluster url should be provided for security enabled testing");
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]isHttps;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getProtocol() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl]isHttps() ? [CtLiteralImpl]"https" : [CtLiteralImpl]"http";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]org.elasticsearch.client.RestClient buildClient([CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.common.settings.Settings settings, [CtParameterImpl][CtArrayTypeReferenceImpl]org.apache.http.HttpHost[] hosts) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.client.RestClientBuilder builder = [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.client.RestClient.builder([CtVariableReadImpl]hosts);
        [CtIfImpl]if ([CtInvocationImpl]isHttps()) [CtBlockImpl]{
            [CtInvocationImpl]com.amazon.opendistroforelasticsearch.ad.ODFERestTestCase.configureHttpsClient([CtVariableReadImpl]builder, [CtVariableReadImpl]settings);
        } else [CtBlockImpl]{
            [CtInvocationImpl]configureClient([CtVariableReadImpl]builder, [CtVariableReadImpl]settings);
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.setStrictDeprecationMode([CtLiteralImpl]true);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@org.junit.After
    protected [CtTypeReferenceImpl]void wipeAllODFEIndices() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.client.Response response = [CtInvocationImpl][CtInvocationImpl]client().performRequest([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.client.Request([CtLiteralImpl]"GET", [CtLiteralImpl]"/_cat/indices?format=json&expand_wildcards=all"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentType xContentType = [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.common.xcontent.XContentType.fromMediaTypeOrFormat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getEntity().getContentType().getValue());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParser parser = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xContentType.xContent().createParser([CtTypeAccessImpl]NamedXContentRegistry.EMPTY, [CtTypeAccessImpl]DeprecationHandler.THROW_UNSUPPORTED_OPERATION, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getEntity().getContent())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParser.Token token = [CtInvocationImpl][CtVariableReadImpl]parser.nextToken();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> parserList = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]token == [CtFieldReadImpl]XContentParser.Token.START_ARRAY) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]parserList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]parser.listOrderedMap().stream().map([CtLambdaImpl]([CtParameterImpl] obj) -> [CtVariableReadImpl](([CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>) (obj))).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]parserList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]parser.mapOrdered());
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> index : [CtVariableReadImpl]parserList) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexName = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]index.get([CtLiteralImpl]"index")));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]indexName != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtLiteralImpl]".opendistro_security".equals([CtVariableReadImpl]indexName))) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl]client().performRequest([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.client.Request([CtLiteralImpl]"DELETE", [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtVariableReadImpl]indexName));
                }
            }
        }
    }

    [CtMethodImpl]protected static [CtTypeReferenceImpl]void configureHttpsClient([CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.client.RestClientBuilder builder, [CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.common.settings.Settings settings) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> headers = [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.common.util.concurrent.ThreadContext.buildDefaultHeaders([CtVariableReadImpl]settings);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.apache.http.Header[] defaultHeaders = [CtNewArrayImpl]new [CtTypeReferenceImpl]org.apache.http.Header[[CtInvocationImpl][CtVariableReadImpl]headers.size()];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtVariableReadImpl]headers.entrySet()) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]defaultHeaders[[CtUnaryOperatorImpl][CtVariableWriteImpl]i++] = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.message.BasicHeader([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
        }
        [CtInvocationImpl][CtVariableReadImpl]builder.setDefaultHeaders([CtVariableReadImpl]defaultHeaders);
        [CtInvocationImpl][CtVariableReadImpl]builder.setHttpClientConfigCallback([CtLambdaImpl]([CtParameterImpl] httpClientBuilder) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userName = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"user")).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"user name is missing"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"password")).orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"password is missing"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.CredentialsProvider credentialsProvider = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.impl.client.BasicCredentialsProvider();
            [CtInvocationImpl][CtVariableReadImpl]credentialsProvider.setCredentials([CtVariableReadImpl]AuthScope.ANY, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.auth.UsernamePasswordCredentials([CtVariableReadImpl]userName, [CtVariableReadImpl]password));
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// disable the certificate since our testing cluster just uses the default security configuration
                [CtInvocationImpl][CtVariableReadImpl]httpClientBuilder.setDefaultCredentialsProvider([CtVariableReadImpl]credentialsProvider).setSSLHostnameVerifier([CtVariableReadImpl]NoopHostnameVerifier.INSTANCE).setSSLContext([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.http.ssl.SSLContextBuilder.create().loadTrustMaterial([CtLiteralImpl]null, [CtLambdaImpl]([CtParameterImpl] chains,[CtParameterImpl] authType) -> [CtLiteralImpl]true).build());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new <com.amazon.opendistroforelasticsearch.ad.e>[CtTypeReferenceImpl]java.lang.RuntimeException();
            }
        });
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String socketTimeoutString = [CtInvocationImpl][CtVariableReadImpl]settings.get([CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.CLIENT_SOCKET_TIMEOUT);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.elasticsearch.common.unit.TimeValue socketTimeout = [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.common.unit.TimeValue.parseTimeValue([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]socketTimeoutString == [CtLiteralImpl]null ? [CtLiteralImpl]"60s" : [CtVariableReadImpl]socketTimeoutString, [CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.CLIENT_SOCKET_TIMEOUT);
        [CtInvocationImpl][CtVariableReadImpl]builder.setRequestConfigCallback([CtLambdaImpl]([CtParameterImpl] conf) -> [CtInvocationImpl][CtVariableReadImpl]conf.setSocketTimeout([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toIntExact([CtInvocationImpl][CtVariableReadImpl]socketTimeout.getMillis())));
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]settings.hasValue([CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.CLIENT_PATH_PREFIX)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.setPathPrefix([CtInvocationImpl][CtVariableReadImpl]settings.get([CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.CLIENT_PATH_PREFIX));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * wipeAllIndices won't work since it cannot delete security index. Use wipeAllODFEIndices instead.
     */
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean preserveIndicesUponCompletion() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }
}
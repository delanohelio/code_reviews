[CompilationUnitImpl][CtCommentImpl]/* Copyright 2015-2020 The OpenZipkin Authors

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
 */
[CtPackageDeclarationImpl]package zipkin2.server.internal.elasticsearch;
[CtUnresolvedImport]import com.linecorp.armeria.common.HttpRequest;
[CtUnresolvedImport]import com.linecorp.armeria.common.HttpResponse;
[CtUnresolvedImport]import com.linecorp.armeria.client.HttpClient;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import com.linecorp.armeria.client.SimpleDecoratingHttpClient;
[CtUnresolvedImport]import com.linecorp.armeria.client.ClientRequestContext;
[CtUnresolvedImport]import com.linecorp.armeria.common.HttpHeaderNames;
[CtClassImpl][CtJavaDocImpl]/**
 * Adds basic auth username and password to every request per https://www.elastic.co/guide/en/x-pack/current/how-security-works.html
 */
final class BasicAuthInterceptor extends [CtTypeReferenceImpl]com.linecorp.armeria.client.SimpleDecoratingHttpClient {
    [CtFieldImpl]final [CtTypeReferenceImpl]zipkin2.server.internal.elasticsearch.BasicCredentials basicCredentials;

    [CtConstructorImpl]BasicAuthInterceptor([CtParameterImpl][CtTypeReferenceImpl]com.linecorp.armeria.client.HttpClient client, [CtParameterImpl][CtTypeReferenceImpl]zipkin2.server.internal.elasticsearch.BasicCredentials basicCredentials) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]client);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.basicCredentials = [CtVariableReadImpl]basicCredentials;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.linecorp.armeria.common.HttpResponse execute([CtParameterImpl][CtTypeReferenceImpl]com.linecorp.armeria.client.ClientRequestContext ctx, [CtParameterImpl][CtTypeReferenceImpl]com.linecorp.armeria.common.HttpRequest req) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> credentialsOption = [CtInvocationImpl][CtFieldReadImpl]basicCredentials.getCredentials();
        [CtInvocationImpl][CtVariableReadImpl]credentialsOption.ifPresent([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtInvocationImpl][CtVariableReadImpl]ctx.addAdditionalRequestHeader([CtTypeAccessImpl]HttpHeaderNames.AUTHORIZATION, [CtVariableReadImpl]s));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]delegate().execute([CtVariableReadImpl]ctx, [CtVariableReadImpl]req);
    }
}
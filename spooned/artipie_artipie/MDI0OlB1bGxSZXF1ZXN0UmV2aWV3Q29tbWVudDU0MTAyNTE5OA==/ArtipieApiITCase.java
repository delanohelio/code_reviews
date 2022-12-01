[CompilationUnitImpl][CtCommentImpl]/* The MIT License (MIT)

Copyright (c) 2020 artipie.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
[CtPackageDeclarationImpl]package com.artipie.api;
[CtUnresolvedImport]import org.junit.jupiter.params.provider.ValueSource;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.artipie.http.rs.RsStatus;
[CtUnresolvedImport]import com.artipie.asto.Content;
[CtUnresolvedImport]import com.artipie.asto.Key;
[CtUnresolvedImport]import com.amihaiemil.eoyaml.Yaml;
[CtUnresolvedImport]import org.apache.commons.codec.binary.Base64;
[CtImportImpl]import java.net.HttpURLConnection;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import org.junit.jupiter.api.AfterEach;
[CtUnresolvedImport]import org.junit.jupiter.params.ParameterizedTest;
[CtUnresolvedImport]import org.hamcrest.MatcherAssert;
[CtUnresolvedImport]import com.artipie.asto.Storage;
[CtUnresolvedImport]import com.artipie.asto.fs.FileStorage;
[CtUnresolvedImport]import org.junit.jupiter.api.io.TempDir;
[CtUnresolvedImport]import org.hamcrest.core.IsEqual;
[CtUnresolvedImport]import com.artipie.RepoConfigYaml;
[CtUnresolvedImport]import com.artipie.ArtipieServer;
[CtClassImpl][CtJavaDocImpl]/**
 * IT for Artipie API and dashboard.
 *
 * @since 0.14
 */
class ArtipieApiITCase {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Temporary directory for all tests.
     *
     * @checkstyle VisibilityModifierCheck (3 lines)
     */
    [CtAnnotationImpl]@org.junit.jupiter.api.io.TempDir
    [CtTypeReferenceImpl]java.nio.file.Path tmp;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Tested Artipie server.
     */
    private [CtTypeReferenceImpl]com.artipie.ArtipieServer server;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Port.
     */
    private [CtTypeReferenceImpl]int port;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    [CtTypeReferenceImpl]void init() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.artipie.asto.Storage storage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.asto.fs.FileStorage([CtFieldReadImpl][CtThisAccessImpl]this.tmp);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]storage.save([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.artipie.asto.Key.From([CtLiteralImpl]"repos/_permissions.yaml"), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.artipie.asto.Content.From([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.apiPerms().getBytes())).join();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.server = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.ArtipieServer([CtFieldReadImpl][CtThisAccessImpl]this.tmp, [CtLiteralImpl]"my_repo", [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.RepoConfigYaml([CtLiteralImpl]"binary").withFileStorage([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tmp.resolve([CtLiteralImpl]"repos/test")));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.port = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.server.start([CtLiteralImpl]"org");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.params.ParameterizedTest
    [CtAnnotationImpl]@org.junit.jupiter.params.provider.ValueSource(strings = [CtNewArrayImpl]{ [CtLiteralImpl]"api/repos/bob", [CtLiteralImpl]"dashboard/bob", [CtLiteralImpl]"dashboard/bob/my_repo", [CtLiteralImpl]"api/security/users/bob", [CtLiteralImpl]"api/security/permissions/my_repo", [CtLiteralImpl]"api/security/permissions" })
    [CtTypeReferenceImpl]void getRequestsWork([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String url) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.net.HttpURLConnection con = [CtInvocationImpl](([CtTypeReferenceImpl]java.net.HttpURLConnection) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"http://localhost:%s/%s", [CtFieldReadImpl][CtThisAccessImpl]this.port, [CtVariableReadImpl]url)).openConnection()));
        [CtInvocationImpl][CtVariableReadImpl]con.setRequestMethod([CtLiteralImpl]"GET");
        [CtInvocationImpl][CtVariableReadImpl]con.setRequestProperty([CtLiteralImpl]"Authorization", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Basic %s", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Base64.encodeBase64([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s:%s", [CtInvocationImpl][CtTypeAccessImpl]ArtipieServer.BOB.name(), [CtInvocationImpl][CtTypeAccessImpl]ArtipieServer.BOB.password()).getBytes()))));
        [CtInvocationImpl][CtTypeAccessImpl]org.hamcrest.MatcherAssert.assertThat([CtLiteralImpl]"Response status is 200", [CtInvocationImpl][CtVariableReadImpl]con.getResponseCode(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hamcrest.core.IsEqual<>([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtTypeAccessImpl]RsStatus.OK.code())));
        [CtInvocationImpl][CtVariableReadImpl]con.disconnect();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.AfterEach
    [CtTypeReferenceImpl]void stop() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.server.stop();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String apiPerms() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder().add([CtLiteralImpl]"permissions", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder().add([CtInvocationImpl][CtTypeAccessImpl]ArtipieServer.BOB.name(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlSequenceBuilder().add([CtLiteralImpl]"api").build()).build()).build().toString();
    }
}
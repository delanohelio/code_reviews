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
[CtPackageDeclarationImpl]package com.artipie.rpm;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import java.io.OutputStream;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.artipie.http.rs.RsStatus;
[CtUnresolvedImport]import com.artipie.asto.Key;
[CtUnresolvedImport]import com.amihaiemil.eoyaml.Yaml;
[CtImportImpl]import java.net.HttpURLConnection;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import org.junit.jupiter.api.AfterEach;
[CtUnresolvedImport]import org.hamcrest.MatcherAssert;
[CtUnresolvedImport]import com.artipie.asto.fs.FileStorage;
[CtUnresolvedImport]import org.junit.jupiter.api.io.TempDir;
[CtUnresolvedImport]import org.hamcrest.core.IsEqual;
[CtUnresolvedImport]import com.artipie.ArtipieServer;
[CtUnresolvedImport]import com.artipie.asto.test.TestResource;
[CtClassImpl][CtJavaDocImpl]/**
 * IT case for RPM repository.
 *
 * @since 0.12
 * @checkstyle MagicNumberCheck (500 lines)
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"PMD.AvoidDuplicateLiterals")
public final class RpmITCase {
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
     * Artipie server port.
     */
    private [CtTypeReferenceImpl]int port;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    [CtTypeReferenceImpl]void init() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.server = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.ArtipieServer([CtFieldReadImpl][CtThisAccessImpl]this.tmp, [CtLiteralImpl]"my-rpm", [CtInvocationImpl][CtThisAccessImpl]this.configs());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.port = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.server.start();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void addsRpm() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.net.HttpURLConnection con = [CtInvocationImpl](([CtTypeReferenceImpl]java.net.HttpURLConnection) ([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"http://localhost:%s/my-rpm/time-1.7-45.el7.x86_64.rpm", [CtFieldReadImpl][CtThisAccessImpl]this.port)).openConnection()));
        [CtInvocationImpl][CtVariableReadImpl]con.setRequestMethod([CtLiteralImpl]"PUT");
        [CtInvocationImpl][CtVariableReadImpl]con.setDoOutput([CtLiteralImpl]true);
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.OutputStream out = [CtInvocationImpl][CtVariableReadImpl]con.getOutputStream()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]byte[] input = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.asto.test.TestResource([CtLiteralImpl]"rpm/time-1.7-45.el7.x86_64.rpm").asBytes();
            [CtInvocationImpl][CtVariableReadImpl]out.write([CtVariableReadImpl]input, [CtLiteralImpl]0, [CtFieldReadImpl][CtVariableReadImpl]input.length);
        }
        [CtInvocationImpl][CtTypeAccessImpl]org.hamcrest.MatcherAssert.assertThat([CtLiteralImpl]"Response status is 202", [CtInvocationImpl][CtVariableReadImpl]con.getResponseCode(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hamcrest.core.IsEqual<>([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtTypeAccessImpl]RsStatus.ACCEPTED.code())));
        [CtInvocationImpl][CtTypeAccessImpl]org.hamcrest.MatcherAssert.assertThat([CtLiteralImpl]"Repository xml indexes are created", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.artipie.asto.fs.FileStorage([CtFieldReadImpl][CtThisAccessImpl]this.tmp).list([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.artipie.asto.Key.From([CtLiteralImpl]"my-rpm/repodata")).join().size(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hamcrest.core.IsEqual<>([CtLiteralImpl]4));
        [CtInvocationImpl][CtVariableReadImpl]con.disconnect();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.AfterEach
    [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.server.stop();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String configs() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder().add([CtLiteralImpl]"repo", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder().add([CtLiteralImpl]"type", [CtLiteralImpl]"rpm").add([CtLiteralImpl]"storage", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder().add([CtLiteralImpl]"type", [CtLiteralImpl]"fs").add([CtLiteralImpl]"path", [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tmp.toString()).build()).build()).build().toString();
    }
}
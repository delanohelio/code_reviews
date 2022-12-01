[CompilationUnitImpl][CtCommentImpl]/* Copyright 2016 LINE Corporation

LINE Corporation licenses this file to you under the Apache License,
version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at:

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package com.linecorp.armeria.common;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.EnumSet;
[CtImportImpl]import static java.util.Objects.requireNonNull;
[CtUnresolvedImport]import com.google.common.collect.Sets;
[CtEnumImpl][CtJavaDocImpl]/**
 * HTTP request method.
 */
public enum HttpMethod {

    [CtEnumValueImpl][CtCommentImpl]// Forked from Netty 4.1.34 at ff7484864b1785103cbc62845ff3a392c93822b7
    [CtJavaDocImpl]/**
     * The OPTIONS method which represents a request for information about the communication options
     * available on the request/response chain identified by the Request-URI. This method allows
     * the client to determine the options and/or requirements associated with a resource, or the
     * capabilities of a server, without implying a resource action or initiating a resource
     * retrieval.
     */
    OPTIONS,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The GET method which means retrieve whatever information (in the form of an entity) is identified
     * by the Request-URI.  If the Request-URI refers to a data-producing process, it is the
     * produced data which shall be returned as the entity in the response and not the source text
     * of the process, unless that text happens to be the output of the process.
     */
    GET,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The HEAD method which is identical to GET except that the server MUST NOT return a message-body
     * in the response.
     */
    HEAD,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The POST method which is used to request that the origin server accept the entity enclosed in the
     * request as a new subordinate of the resource identified by the Request-URI in the
     * Request-Line.
     */
    POST,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The PUT method which requests that the enclosed entity be stored under the supplied Request-URI.
     */
    PUT,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The PATCH method which requests that a set of changes described in the
     * request entity be applied to the resource identified by the Request-URI.
     */
    PATCH,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The DELETE method which requests that the origin server delete the resource identified by the
     * Request-URI.
     */
    DELETE,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The TRACE method which is used to invoke a remote, application-layer loop-back of the request
     * message.
     */
    TRACE,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * The CONNECT method which is used for a proxy that can dynamically switch to being a tunnel.
     */
    CONNECT,
    [CtEnumValueImpl][CtJavaDocImpl]/**
     * A special method which represents the client sent a method that is none of the constants defined in
     * this enum.
     */
    UNKNOWN;
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linecorp.armeria.common.HttpMethod> knownMethods;[CtCommentImpl]// ImmutableEnumSet


    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linecorp.armeria.common.HttpMethod> idempotentMethods = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.immutableEnumSet([CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.GET, [CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.HEAD, [CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.PUT, [CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.DELETE);

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linecorp.armeria.common.HttpMethod> allMethods = [CtInvocationImpl][CtTypeAccessImpl]java.util.EnumSet.allOf([CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.class);
        [CtInvocationImpl][CtVariableReadImpl]allMethods.remove([CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.UNKNOWN);
        [CtAssignmentImpl][CtFieldWriteImpl]com.linecorp.armeria.common.HttpMethod.knownMethods = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Sets.immutableEnumSet([CtVariableReadImpl]allMethods);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether the specified {@link String} is one of the supported method names.
     *
     * @return {@code true} if supported. {@code false} otherwise.
     */
    public static [CtTypeReferenceImpl]boolean isSupported([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtInvocationImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]value, [CtLiteralImpl]"value");
        [CtSwitchImpl]switch ([CtVariableReadImpl]value) {
            [CtCaseImpl]case [CtLiteralImpl]"OPTIONS" :
            [CtCaseImpl]case [CtLiteralImpl]"GET" :
            [CtCaseImpl]case [CtLiteralImpl]"HEAD" :
            [CtCaseImpl]case [CtLiteralImpl]"POST" :
            [CtCaseImpl]case [CtLiteralImpl]"PUT" :
            [CtCaseImpl]case [CtLiteralImpl]"PATCH" :
            [CtCaseImpl]case [CtLiteralImpl]"DELETE" :
            [CtCaseImpl]case [CtLiteralImpl]"TRACE" :
            [CtCaseImpl]case [CtLiteralImpl]"CONNECT" :
                [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the <a href="https://developer.mozilla.org/en-US/docs/Glossary/Idempotent">idempotent</a>
     * HTTP methods.
     */
    public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linecorp.armeria.common.HttpMethod> idempotentMethods() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.idempotentMethods;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns all {@link HttpMethod}s except {@link #UNKNOWN}.
     */
    public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linecorp.armeria.common.HttpMethod> knownMethods() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.linecorp.armeria.common.HttpMethod.knownMethods;
    }
}
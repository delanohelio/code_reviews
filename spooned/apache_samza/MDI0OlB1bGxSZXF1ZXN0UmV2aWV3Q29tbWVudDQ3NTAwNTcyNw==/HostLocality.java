[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.samza.job.model;
[CtUnresolvedImport]import com.google.common.base.Objects;
[CtClassImpl][CtJavaDocImpl]/**
 * A class to represent the host locality information.
 * Fields such as <i>jmxUrl</i> and <i>jmxTunnelingUrl</i> exist for backward compatibility reasons as they were
 * historically stored under the same name space as locality and surfaced within the framework through the locality
 * manager.
 */
public class HostLocality {
    [CtFieldImpl][CtCommentImpl]/* Container identifier */
    private [CtTypeReferenceImpl]java.lang.String id;

    [CtFieldImpl][CtCommentImpl]/* Host on which the container is currently placed */
    private [CtTypeReferenceImpl]java.lang.String host;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String jmxUrl;

    [CtFieldImpl][CtCommentImpl]/* JMX tunneling URL for debugging */
    private [CtTypeReferenceImpl]java.lang.String jmxTunnelingUrl;

    [CtConstructorImpl]public HostLocality([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String host) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]id, [CtVariableReadImpl]host, [CtLiteralImpl]"", [CtLiteralImpl]"");
    }

    [CtConstructorImpl]public HostLocality([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String host, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String jmxUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String jmxTunnelingUrl) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.host = [CtVariableReadImpl]host;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jmxUrl = [CtVariableReadImpl]jmxUrl;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jmxTunnelingUrl = [CtVariableReadImpl]jmxTunnelingUrl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String id() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String host() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]host;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String jmxUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jmxUrl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String jmxTunnelingUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jmxTunnelingUrl;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.samza.job.model.HostLocality that = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.samza.job.model.HostLocality) (o));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.equal([CtFieldReadImpl]id, [CtFieldReadImpl][CtVariableReadImpl]that.id) && [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.equal([CtFieldReadImpl]host, [CtFieldReadImpl][CtVariableReadImpl]that.host)) && [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.equal([CtFieldReadImpl]jmxUrl, [CtFieldReadImpl][CtVariableReadImpl]that.jmxUrl)) && [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.equal([CtFieldReadImpl]jmxTunnelingUrl, [CtFieldReadImpl][CtVariableReadImpl]that.jmxTunnelingUrl);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.hashCode([CtFieldReadImpl]id, [CtFieldReadImpl]host, [CtFieldReadImpl]jmxUrl, [CtFieldReadImpl]jmxTunnelingUrl);
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.dolphinscheduler.remote.command.log;
[CtUnresolvedImport]import org.apache.dolphinscheduler.remote.command.Command;
[CtUnresolvedImport]import org.apache.dolphinscheduler.remote.command.CommandType;
[CtUnresolvedImport]import org.apache.dolphinscheduler.remote.utils.JacksonSerializer;
[CtImportImpl]import java.io.Serializable;
[CtClassImpl][CtJavaDocImpl]/**
 * view log response command
 */
public class ViewLogResponseCommand implements [CtTypeReferenceImpl]java.io.Serializable {
    [CtFieldImpl][CtJavaDocImpl]/**
     * response data
     */
    private [CtTypeReferenceImpl]java.lang.String msg;

    [CtConstructorImpl]public ViewLogResponseCommand() [CtBlockImpl]{
    }

    [CtConstructorImpl]public ViewLogResponseCommand([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.msg = [CtVariableReadImpl]msg;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getMsg() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]msg;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMsg([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.msg = [CtVariableReadImpl]msg;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * package response command
     *
     * @param opaque
     * 		request unique identification
     * @return command
     */
    public [CtTypeReferenceImpl]org.apache.dolphinscheduler.remote.command.Command convert2Command([CtParameterImpl][CtTypeReferenceImpl]long opaque) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.dolphinscheduler.remote.command.Command command = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.dolphinscheduler.remote.command.Command([CtVariableReadImpl]opaque);
        [CtInvocationImpl][CtVariableReadImpl]command.setType([CtTypeAccessImpl]CommandType.VIEW_WHOLE_LOG_RESPONSE);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] body = [CtInvocationImpl][CtTypeAccessImpl]org.apache.dolphinscheduler.remote.utils.JacksonSerializer.serialize([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]command.setBody([CtVariableReadImpl]body);
        [CtReturnImpl]return [CtVariableReadImpl]command;
    }
}
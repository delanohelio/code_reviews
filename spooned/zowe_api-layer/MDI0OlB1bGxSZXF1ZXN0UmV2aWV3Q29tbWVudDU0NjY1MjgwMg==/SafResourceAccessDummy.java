[CompilationUnitImpl][CtCommentImpl]/* This program and the accompanying materials are made available under the terms of the
Eclipse Public License v2.0 which accompanies this distribution, and is available at
https://www.eclipse.org/legal/epl-v20.html

SPDX-License-Identifier: EPL-2.0

Copyright Contributors to the Zowe Project.
 */
[CtPackageDeclarationImpl]package org.zowe.apiml.security.common.auth.saf;
[CtUnresolvedImport]import lombok.Builder;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.io.*;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.springframework.security.core.Authentication;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import lombok.Value;
[CtUnresolvedImport]import org.yaml.snakeyaml.Yaml;
[CtClassImpl]public class SafResourceAccessDummy implements [CtTypeReferenceImpl]SafResourceAccessVerifying {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SAF_ACCESS = [CtLiteralImpl]"safAccess";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DEFAULT_FILE_LOCATION = [CtLiteralImpl]"saf.yml";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DEFAULT_RESOURCE_LOCATION = [CtLiteralImpl]"mock-saf.yml";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser, [CtTypeReferenceImpl]AccessLevel> resourceUserToAccessLevel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl]public SafResourceAccessDummy() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File file = [CtInvocationImpl]getFile();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]file.exists()) [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.FileInputStream fis = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtVariableReadImpl]file);[CtLocalVariableImpl][CtTypeReferenceImpl]java.io.BufferedInputStream bis = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedInputStream([CtVariableReadImpl]fis)) [CtBlockImpl]{
                [CtInvocationImpl]load([CtVariableReadImpl]bis);
            }
        } else [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getClassLoader().getResourceAsStream([CtFieldReadImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.DEFAULT_RESOURCE_LOCATION)) [CtBlockImpl]{
                [CtInvocationImpl]load([CtVariableReadImpl]inputStream);
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.io.File getFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.DEFAULT_FILE_LOCATION);
    }

    [CtConstructorImpl]public SafResourceAccessDummy([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream inputStream) [CtBlockImpl]{
        [CtInvocationImpl]load([CtVariableReadImpl]inputStream);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean hasSafResourceAccess([CtParameterImpl][CtTypeReferenceImpl]org.springframework.security.core.Authentication authentication, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceClass, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String accessLevel) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser resourceUser = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser.builder().resourceClass([CtVariableReadImpl]resourceClass).resourceName([CtVariableReadImpl]resourceName).userId([CtInvocationImpl][CtVariableReadImpl]authentication.getName()).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]AccessLevel currentLevel = [CtInvocationImpl][CtFieldReadImpl]resourceUserToAccessLevel.get([CtVariableReadImpl]resourceUser);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentLevel == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]currentLevel.compareTo([CtInvocationImpl][CtTypeAccessImpl]AccessLevel.valueOf([CtVariableReadImpl]accessLevel)) >= [CtLiteralImpl]0;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void load([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream inputStream) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.yaml.snakeyaml.Yaml yaml = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.yaml.snakeyaml.Yaml();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> data = [CtInvocationImpl][CtVariableReadImpl]yaml.load([CtVariableReadImpl]inputStream);
        [CtInvocationImpl]load([CtVariableReadImpl]data);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void set([CtParameterImpl][CtTypeReferenceImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser resourceUser, [CtParameterImpl][CtTypeReferenceImpl]AccessLevel accessLevel) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]AccessLevel currentLevel = [CtInvocationImpl][CtFieldReadImpl]resourceUserToAccessLevel.get([CtVariableReadImpl]resourceUser);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]currentLevel == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]currentLevel.compareTo([CtVariableReadImpl]accessLevel) < [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]currentLevel = [CtVariableReadImpl]accessLevel;
        }
        [CtInvocationImpl][CtFieldReadImpl]resourceUserToAccessLevel.put([CtVariableReadImpl]resourceUser, [CtVariableReadImpl]currentLevel);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void set([CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceClass, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String resourceName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> users, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String levelName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]AccessLevel accessLevel = [CtInvocationImpl][CtTypeAccessImpl]AccessLevel.valueOf([CtVariableReadImpl]levelName);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId : [CtVariableReadImpl]users) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser resourceUser = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.ResourceUser.builder().resourceClass([CtVariableReadImpl]resourceClass).resourceName([CtVariableReadImpl]resourceName).userId([CtVariableReadImpl]userId).build();
            [CtInvocationImpl]set([CtVariableReadImpl]resourceUser, [CtVariableReadImpl]accessLevel);
        }
    }

    [CtMethodImpl]private <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getSafAccess([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> data) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]null;

        [CtReturnImpl]return [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtVariableReadImpl]data.get([CtFieldReadImpl]org.zowe.apiml.security.common.auth.saf.SafResourceAccessDummy.SAF_ACCESS)));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void load([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> data) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>>> classes = [CtInvocationImpl]getSafAccess([CtVariableReadImpl]data);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classes == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>>> clazz : [CtInvocationImpl][CtVariableReadImpl]classes.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourceClass = [CtInvocationImpl][CtVariableReadImpl]clazz.getKey();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>> resource : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.getValue().entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourceName = [CtInvocationImpl][CtVariableReadImpl]resource.getKey();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> level : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getValue().entrySet()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String levelName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]level.getKey().toUpperCase();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> users = [CtInvocationImpl][CtVariableReadImpl]level.getValue();
                    [CtInvocationImpl]set([CtVariableReadImpl]resourceClass, [CtVariableReadImpl]resourceName, [CtVariableReadImpl]users, [CtVariableReadImpl]levelName);
                }
            }
        }
    }

    [CtClassImpl][CtAnnotationImpl]@lombok.Value
    [CtAnnotationImpl]@lombok.Builder
    private static class ResourceUser {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String resourceClass;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String resourceName;

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String userId;
    }
}
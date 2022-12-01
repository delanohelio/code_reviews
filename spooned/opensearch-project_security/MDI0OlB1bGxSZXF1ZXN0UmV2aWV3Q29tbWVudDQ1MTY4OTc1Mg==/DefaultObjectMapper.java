[CompilationUnitImpl][CtCommentImpl]/* Copyright 2015-2018 _floragunn_ GmbH
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
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.security;
[CtUnresolvedImport]import org.elasticsearch.SpecialPermission;
[CtImportImpl]import java.security.PrivilegedActionException;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonInclude.Include;
[CtImportImpl]import java.security.PrivilegedExceptionAction;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonNode;
[CtImportImpl]import com.fasterxml.jackson.databind.InjectableValues;
[CtImportImpl]import com.fasterxml.jackson.databind.exc.InvalidFormatException;
[CtImportImpl]import com.fasterxml.jackson.core.JsonParser;
[CtImportImpl]import java.security.AccessController;
[CtImportImpl]import com.fasterxml.jackson.databind.JavaType;
[CtImportImpl]import com.fasterxml.jackson.core.type.TypeReference;
[CtUnresolvedImport]import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
[CtImportImpl]import com.fasterxml.jackson.databind.exc.MismatchedInputException;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import com.fasterxml.jackson.core.JsonProcessingException;
[CtImportImpl]import com.fasterxml.jackson.databind.type.TypeFactory;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtClassImpl]public class DefaultObjectMapper {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper objectMapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtFieldImpl]public static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper YAML_MAPPER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.dataformat.yaml.YAMLFactory());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper defaulOmittingObjectMapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.setSerializationInclusion([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.annotation.JsonInclude.Include.[CtFieldReferenceImpl]NON_NULL);
        [CtInvocationImpl][CtCommentImpl]// objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.enable([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.core.JsonParser.Feature.[CtFieldReferenceImpl]STRICT_DUPLICATE_DETECTION);
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.defaulOmittingObjectMapper.setSerializationInclusion([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.annotation.JsonInclude.Include.[CtFieldReferenceImpl]NON_DEFAULT);
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.defaulOmittingObjectMapper.enable([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.core.JsonParser.Feature.[CtFieldReferenceImpl]STRICT_DUPLICATE_DETECTION);
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.YAML_MAPPER.enable([CtFieldReadImpl][CtTypeAccessImpl]com.fasterxml.jackson.core.JsonParser.Feature.[CtFieldReferenceImpl]STRICT_DUPLICATE_DETECTION);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void inject([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.InjectableValues.Std injectableValues) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.setInjectableValues([CtVariableReadImpl]injectableValues);
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.YAML_MAPPER.setInjectableValues([CtVariableReadImpl]injectableValues);
        [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.defaulOmittingObjectMapper.setInjectableValues([CtVariableReadImpl]injectableValues);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean getOrDefault([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> properties, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]boolean defaultValue) throws [CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object value = [CtInvocationImpl][CtVariableReadImpl]properties.get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]defaultValue;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Boolean) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]boolean) (value));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)).trim();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"true".equals([CtVariableReadImpl]text) || [CtInvocationImpl][CtLiteralImpl]"True".equals([CtVariableReadImpl]text)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"false".equals([CtVariableReadImpl]text) || [CtInvocationImpl][CtLiteralImpl]"False".equals([CtVariableReadImpl]text)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.fasterxml.jackson.databind.exc.InvalidFormatException.from([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Cannot deserialize value of type \'boolean\' from String \"" + [CtVariableReadImpl]text) + [CtLiteralImpl]"\": only \"true\" or \"false\" recognized)", [CtLiteralImpl]null, [CtFieldReadImpl]java.lang.Boolean.class);
        }
        [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.fasterxml.jackson.databind.exc.MismatchedInputException.from([CtLiteralImpl]null, [CtFieldReadImpl]java.lang.Boolean.class, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Cannot deserialize instance of 'boolean' out of '" + [CtVariableReadImpl]value) + [CtLiteralImpl]"' (Property: ") + [CtVariableReadImpl]key) + [CtLiteralImpl]")");
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getOrDefault([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> properties, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeParameterReferenceImpl]T defaultValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T value = [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtVariableReadImpl]properties.get([CtVariableReadImpl]key)));
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null ? [CtVariableReadImpl]value : [CtVariableReadImpl]defaultValue;
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T readTree([CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> clazz) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeParameterReferenceImpl]T>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeParameterReferenceImpl]T run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.treeToValue([CtVariableReadImpl]node, [CtVariableReadImpl]clazz);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T readValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String string, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> clazz) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeParameterReferenceImpl]T>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeParameterReferenceImpl]T run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.readValue([CtVariableReadImpl]string, [CtVariableReadImpl]clazz);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode readTree([CtParameterImpl][CtTypeReferenceImpl]java.lang.String string) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.readTree([CtVariableReadImpl]string);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String writeValueAsString([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object value, [CtParameterImpl][CtTypeReferenceImpl]boolean omitDefaults) throws [CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeReferenceImpl]java.lang.String>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.String run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtConditionalImpl]([CtVariableReadImpl]omitDefaults ? [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.defaulOmittingObjectMapper : [CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper).writeValueAsString([CtVariableReadImpl]value);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T readValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String string, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.core.type.TypeReference<[CtTypeParameterReferenceImpl]T> tr) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeParameterReferenceImpl]T>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeParameterReferenceImpl]T run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.readValue([CtVariableReadImpl]string, [CtVariableReadImpl]tr);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T readValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String string, [CtParameterImpl][CtTypeReferenceImpl]com.fasterxml.jackson.databind.JavaType jt) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.SecurityManager sm = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getSecurityManager();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sm != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sm.checkPermission([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.SpecialPermission());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.security.AccessController.doPrivileged([CtNewClassImpl]new [CtTypeReferenceImpl]java.security.PrivilegedExceptionAction<[CtTypeParameterReferenceImpl]T>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeParameterReferenceImpl]T run() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.readValue([CtVariableReadImpl]string, [CtVariableReadImpl]jt);
                }
            });
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.security.PrivilegedActionException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtInvocationImpl](([CtTypeReferenceImpl]java.io.IOException) ([CtVariableReadImpl]e.getCause()));
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.fasterxml.jackson.databind.type.TypeFactory getTypeFactory() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]com.amazon.opendistroforelasticsearch.security.DefaultObjectMapper.objectMapper.getTypeFactory();
    }
}
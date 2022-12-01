[CompilationUnitImpl][CtCommentImpl]/* Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
Copyright 2018 SmartBear Software

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.openapitools.codegen.utils;
[CtUnresolvedImport]import io.swagger.v3.oas.models.media.*;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import io.swagger.v3.oas.models.responses.ApiResponse;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import org.openapitools.codegen.CodegenModel;
[CtUnresolvedImport]import io.swagger.v3.oas.models.PathItem;
[CtUnresolvedImport]import io.swagger.v3.oas.models.parameters.Parameter;
[CtUnresolvedImport]import io.swagger.v3.oas.models.parameters.RequestBody;
[CtUnresolvedImport]import io.swagger.v3.oas.models.Operation;
[CtUnresolvedImport]import io.swagger.v3.oas.models.headers.Header;
[CtUnresolvedImport]import io.swagger.v3.oas.models.callbacks.Callback;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.math.BigDecimal;
[CtUnresolvedImport]import io.swagger.v3.parser.util.SchemaTypeUtil;
[CtUnresolvedImport]import org.openapitools.codegen.IJsonSchemaValidationProperties;
[CtImportImpl]import java.util.Map.Entry;
[CtUnresolvedImport]import io.swagger.v3.oas.models.OpenAPI;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import org.openapitools.codegen.config.GlobalSettings;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl]public class ModelUtils {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String URI_FORMAT = [CtLiteralImpl]"uri";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String generateAliasAsModelKey = [CtLiteralImpl]"generateAliasAsModel";

    [CtMethodImpl]public static [CtTypeReferenceImpl]void setGenerateAliasAsModel([CtParameterImpl][CtTypeReferenceImpl]boolean value) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.config.GlobalSettings.setProperty([CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.generateAliasAsModelKey, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.toString([CtVariableReadImpl]value));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isGenerateAliasAsModel() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.config.GlobalSettings.getProperty([CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.generateAliasAsModelKey, [CtLiteralImpl]"false"));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Searches for the model by name in the map of models and returns it
     *
     * @param name
     * 		Name of the model
     * @param models
     * 		Map of models
     * @return model
     */
    public static [CtTypeReferenceImpl]org.openapitools.codegen.CodegenModel getModelByName([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> models) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object data = [CtInvocationImpl][CtVariableReadImpl]models.get([CtVariableReadImpl]name);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Map) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> dataMap = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>) (data));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object dataModels = [CtInvocationImpl][CtVariableReadImpl]dataMap.get([CtLiteralImpl]"models");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataModels instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.List) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> dataModelsList = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>) (dataModels));
                [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object entry : [CtVariableReadImpl]dataModelsList) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entry instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Map) [CtBlockImpl]{
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> entryMap = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>) (entry));
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Object model = [CtInvocationImpl][CtVariableReadImpl]entryMap.get([CtLiteralImpl]"model");
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]model instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.openapitools.codegen.CodegenModel) [CtBlockImpl]{
                            [CtReturnImpl]return [CtVariableReadImpl](([CtTypeReferenceImpl]org.openapitools.codegen.CodegenModel) (model));
                        }
                    }
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the list of all schemas in the 'components/schemas' section used in the openAPI specification
     *
     * @param openAPI
     * 		specification
     * @return schemas a list of used schemas
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAllUsedSchemas([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> childrenMap = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getChildrenMap([CtVariableReadImpl]openAPI);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> allUsedSchemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitOpenAPI([CtVariableReadImpl]openAPI, [CtLambdaImpl]([CtParameterImpl] s,[CtParameterImpl] t) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.get$ref() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ref = [CtInvocationImpl]getSimpleRef([CtInvocationImpl][CtVariableReadImpl]s.get$ref());
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]allUsedSchemas.contains([CtVariableReadImpl]ref)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]allUsedSchemas.add([CtVariableReadImpl]ref);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]childrenMap.containsKey([CtVariableReadImpl]ref)) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String child : [CtInvocationImpl][CtVariableReadImpl]childrenMap.get([CtVariableReadImpl]ref)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]allUsedSchemas.contains([CtVariableReadImpl]child)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]allUsedSchemas.add([CtVariableReadImpl]child);
                        }
                    }
                }
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]allUsedSchemas;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the list of unused schemas in the 'components/schemas' section of an openAPI specification
     *
     * @param openAPI
     * 		specification
     * @return schemas a list of unused schemas
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getUnusedSchemas([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> childrenMap;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> tmpChildrenMap;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]tmpChildrenMap = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getChildrenMap([CtVariableReadImpl]openAPI);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException npe) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// in rare cases, such as a spec document with only one top-level oneOf schema and multiple referenced schemas,
            [CtCommentImpl]// the stream used in getChildrenMap will raise an NPE. Rather than modify getChildrenMap which is used by getAllUsedSchemas,
            [CtCommentImpl]// we'll catch here as a workaround for this edge case.
            [CtVariableWriteImpl]tmpChildrenMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        }
        [CtAssignmentImpl][CtVariableWriteImpl]childrenMap = [CtVariableReadImpl]tmpChildrenMap;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> unusedSchemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> schemas = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemas([CtVariableReadImpl]openAPI);
        [CtInvocationImpl][CtVariableReadImpl]unusedSchemas.addAll([CtInvocationImpl][CtVariableReadImpl]schemas.keySet());
        [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitOpenAPI([CtVariableReadImpl]openAPI, [CtLambdaImpl]([CtParameterImpl] s,[CtParameterImpl] t) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.get$ref() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ref = [CtInvocationImpl]getSimpleRef([CtInvocationImpl][CtVariableReadImpl]s.get$ref());
                [CtInvocationImpl][CtVariableReadImpl]unusedSchemas.remove([CtVariableReadImpl]ref);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]childrenMap.containsKey([CtVariableReadImpl]ref)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]unusedSchemas.removeAll([CtInvocationImpl][CtVariableReadImpl]childrenMap.get([CtVariableReadImpl]ref));
                }
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]unusedSchemas;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the list of schemas in the 'components/schemas' used only in a 'application/x-www-form-urlencoded' or 'multipart/form-data' mime time
     *
     * @param openAPI
     * 		specification
     * @return schemas a list of schemas
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSchemasUsedOnlyInFormParam([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> schemasUsedInFormParam = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> schemasUsedInOtherCases = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitOpenAPI([CtVariableReadImpl]openAPI, [CtLambdaImpl]([CtParameterImpl] s,[CtParameterImpl] t) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.get$ref() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ref = [CtInvocationImpl]getSimpleRef([CtInvocationImpl][CtVariableReadImpl]s.get$ref());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"application/x-www-form-urlencoded".equalsIgnoreCase([CtVariableReadImpl]t) || [CtInvocationImpl][CtLiteralImpl]"multipart/form-data".equalsIgnoreCase([CtVariableReadImpl]t)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]schemasUsedInFormParam.add([CtVariableReadImpl]ref);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]schemasUsedInOtherCases.add([CtVariableReadImpl]ref);
                }
            }
        });
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schemasUsedInFormParam.stream().filter([CtLambdaImpl]([CtParameterImpl]java.lang.String n) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]schemasUsedInOtherCases.contains([CtVariableReadImpl]n)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Private method used by several methods ({@link #getAllUsedSchemas(OpenAPI)},
     * {@link #getUnusedSchemas(OpenAPI)},
     * {@link #getSchemasUsedOnlyInFormParam(OpenAPI)}, ...) to traverse all paths of an
     * OpenAPI instance and call the visitor functional interface when a schema is found.
     *
     * @param openAPI
     * 		specification
     * @param visitor
     * 		functional interface (can be defined as a lambda) called each time a schema is found.
     */
    private static [CtTypeReferenceImpl]void visitOpenAPI([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.utils.ModelUtils.OpenAPISchemaVisitor visitor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.swagger.v3.oas.models.PathItem> paths = [CtInvocationImpl][CtVariableReadImpl]openAPI.getPaths();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> visitedSchemas = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]paths != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.PathItem path : [CtInvocationImpl][CtVariableReadImpl]paths.values()) [CtBlockImpl]{
                [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitPathItem([CtVariableReadImpl]path, [CtVariableReadImpl]openAPI, [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void visitPathItem([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.PathItem pathItem, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.utils.ModelUtils.OpenAPISchemaVisitor visitor, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> visitedSchemas) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.v3.oas.models.Operation> allOperations = [CtInvocationImpl][CtVariableReadImpl]pathItem.readOperations();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allOperations != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.Operation operation : [CtVariableReadImpl]allOperations) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Params:
                org.openapitools.codegen.utils.ModelUtils.visitParameters([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]operation.getParameters(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                [CtLocalVariableImpl][CtCommentImpl]// RequestBody:
                [CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody requestBody = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getReferencedRequestBody([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]operation.getRequestBody());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]requestBody != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitContent([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]requestBody.getContent(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                }
                [CtIfImpl][CtCommentImpl]// Responses:
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]operation.getResponses() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse r : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]operation.getResponses().values()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse apiResponse = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getReferencedApiResponse([CtVariableReadImpl]openAPI, [CtVariableReadImpl]r);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]apiResponse != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitContent([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]apiResponse.getContent(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]apiResponse.getHeaders() != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header> e : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]apiResponse.getHeaders().entrySet()) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header header = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getReferencedHeader([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]e.getValue());
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]header.getSchema() != [CtLiteralImpl]null) [CtBlockImpl]{
                                        [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]header.getSchema(), [CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                                    }
                                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitContent([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]header.getContent(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                                }
                            }
                        }
                    }
                }
                [CtIfImpl][CtCommentImpl]// Callbacks:
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]operation.getCallbacks() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback c : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]operation.getCallbacks().values()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback callback = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getReferencedCallback([CtVariableReadImpl]openAPI, [CtVariableReadImpl]c);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]callback != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.PathItem p : [CtInvocationImpl][CtVariableReadImpl]callback.values()) [CtBlockImpl]{
                                [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitPathItem([CtVariableReadImpl]p, [CtVariableReadImpl]openAPI, [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                            }
                        }
                    }
                }
            }
        }
        [CtInvocationImpl][CtCommentImpl]// Params:
        org.openapitools.codegen.utils.ModelUtils.visitParameters([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]pathItem.getParameters(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void visitParameters([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter> parameters, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.utils.ModelUtils.OpenAPISchemaVisitor visitor, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> visitedSchemas) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameters != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter p : [CtVariableReadImpl]parameters) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter parameter = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getReferencedParameter([CtVariableReadImpl]openAPI, [CtVariableReadImpl]p);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]parameter != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]parameter.getSchema() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]parameter.getSchema(), [CtLiteralImpl]null, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                    }
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitContent([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]parameter.getContent(), [CtVariableReadImpl]visitor, [CtVariableReadImpl]visitedSchemas);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.warn([CtLiteralImpl]"Unreferenced parameter found.");
                }
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void visitContent([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]Content content, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.utils.ModelUtils.OpenAPISchemaVisitor visitor, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> visitedSchemas) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]content != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]MediaType> e : [CtInvocationImpl][CtVariableReadImpl]content.entrySet()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getValue().getSchema() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getValue().getSchema(), [CtInvocationImpl][CtVariableReadImpl]e.getKey(), [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                }
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void visitSchema([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]Schema schema, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String mimeType, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> visitedSchemas, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.utils.ModelUtils.OpenAPISchemaVisitor visitor) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]visitor.visit([CtVariableReadImpl]schema, [CtVariableReadImpl]mimeType);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]schema.get$ref() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ref = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]visitedSchemas.contains([CtVariableReadImpl]ref)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]visitedSchemas.add([CtVariableReadImpl]ref);
                [CtLocalVariableImpl][CtTypeReferenceImpl]Schema referencedSchema = [CtInvocationImpl][CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemas([CtVariableReadImpl]openAPI).get([CtVariableReadImpl]ref);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedSchema != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]referencedSchema, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> oneOf = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (schema)).getOneOf();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oneOf != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema s : [CtVariableReadImpl]oneOf) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]s, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> allOf = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (schema)).getAllOf();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]allOf != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema s : [CtVariableReadImpl]allOf) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]s, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> anyOf = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (schema)).getAnyOf();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]anyOf != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema s : [CtVariableReadImpl]anyOf) [CtBlockImpl]{
                    [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]s, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
                }
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ArraySchema) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Schema itemsSchema = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]ArraySchema) (schema)).getItems();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]itemsSchema != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]itemsSchema, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
            }
        } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isMapSchema([CtVariableReadImpl]schema)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object additionalProperties = [CtInvocationImpl][CtVariableReadImpl]schema.getAdditionalProperties();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]additionalProperties instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Schema) [CtBlockImpl]{
                [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl](([CtTypeReferenceImpl]Schema) (additionalProperties)), [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]schema.getNot() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]schema.getNot(), [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> properties = [CtInvocationImpl][CtVariableReadImpl]schema.getProperties();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]properties != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema property : [CtInvocationImpl][CtVariableReadImpl]properties.values()) [CtBlockImpl]{
                [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.visitSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]property, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]visitedSchemas, [CtVariableReadImpl]visitor);
            }
        }
    }

    [CtInterfaceImpl][CtAnnotationImpl]@java.lang.FunctionalInterface
    private static interface OpenAPISchemaVisitor {
        [CtMethodImpl]public [CtTypeReferenceImpl]void visit([CtParameterImpl][CtTypeReferenceImpl]Schema schema, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String mimeType);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getSimpleRef([CtParameterImpl][CtTypeReferenceImpl]java.lang.String ref) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ref.startsWith([CtLiteralImpl]"#/components/")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ref = [CtInvocationImpl][CtVariableReadImpl]ref.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ref.lastIndexOf([CtLiteralImpl]"/") + [CtLiteralImpl]1);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ref.startsWith([CtLiteralImpl]"#/definitions/")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ref = [CtInvocationImpl][CtVariableReadImpl]ref.substring([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ref.lastIndexOf([CtLiteralImpl]"/") + [CtLiteralImpl]1);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.warn([CtLiteralImpl]"Failed to get the schema name: {}", [CtVariableReadImpl]ref);
            [CtReturnImpl][CtCommentImpl]// throw new RuntimeException("Failed to get the schema: " + ref);
            return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtVariableReadImpl]ref;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isObjectSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ObjectSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl][CtCommentImpl]// must not be a map
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.OBJECT_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MapSchema))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl][CtCommentImpl]// must have at least one property
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getType() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getProperties() != [CtLiteralImpl]null)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getProperties().isEmpty())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isComposedSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isMapSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]MapSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]schema.getAdditionalProperties() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Schema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getAdditionalProperties() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Boolean) && [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtVariableReadImpl]schema.getAdditionalProperties()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isArraySchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ArraySchema;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isSet([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.isArraySchema([CtVariableReadImpl]schema) && [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getUniqueItems());
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isStringSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]StringSchema) || [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isIntegerSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]IntegerSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.INTEGER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isShortSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.INTEGER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())[CtCommentImpl]// type: integer
         && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.INTEGER32_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: short (int32)
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isLongSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.INTEGER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())[CtCommentImpl]// type: integer
         && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.INTEGER64_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: long (int64)
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isBooleanSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BooleanSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.BOOLEAN_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isNumberSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]NumberSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.NUMBER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isFloatSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.NUMBER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.FLOAT_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: float
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isDoubleSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.NUMBER_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.DOUBLE_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: double
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isDateSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]DateSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.DATE_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: date
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isDateTimeSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]DateTimeSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.DATE_TIME_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: date-time
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isPasswordSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]PasswordSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.PASSWORD_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// double
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isByteArraySchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ByteArraySchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.BYTE_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: byte
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isBinarySchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BinarySchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.BINARY_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: binary
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isFileSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]FileSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl][CtCommentImpl]// file type in oas2 mapped to binary in oas3
        return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isBinarySchema([CtVariableReadImpl]schema);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isUUIDSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]UUIDSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.UUID_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: uuid
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isURISchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.URI_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: uri
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isEmailSchema([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]EmailSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.STRING_TYPE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getType()) && [CtInvocationImpl][CtTypeAccessImpl]SchemaTypeUtil.EMAIL_FORMAT.equals([CtInvocationImpl][CtVariableReadImpl]schema.getFormat())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// format: email
            return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check to see if the schema is a model with at least one properties
     *
     * @param schema
     * 		potentially containing a '$ref'
     * @return true if it's a model with at least one properties
     */
    public static [CtTypeReferenceImpl]boolean isModel([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.error([CtLiteralImpl]"Schema cannot be null in isModel check");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl][CtCommentImpl]// has at least one property
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getProperties() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getProperties().isEmpty())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl][CtCommentImpl]// composed schema is a model
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check to see if the schema is a free form object
     *
     * @param schema
     * 		potentially containing a '$ref'
     * @return true if it's a free-form object
     */
    public static [CtTypeReferenceImpl]boolean isFreeFormObject([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.error([CtLiteralImpl]"Schema cannot be null in isFreeFormObject check");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl][CtCommentImpl]// not free-form if allOf, anyOf, oneOf is not empty
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]ComposedSchema cs = [CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (schema));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> interfaces = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getInterfaces([CtVariableReadImpl]cs);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]interfaces != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]interfaces.isEmpty())) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtIfImpl][CtCommentImpl]// has at least one property
        if ([CtInvocationImpl][CtLiteralImpl]"object".equals([CtInvocationImpl][CtVariableReadImpl]schema.getType())) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// no properties
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getProperties() == [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getProperties().isEmpty()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Schema addlProps = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getAdditionalProperties([CtVariableReadImpl]schema);
                [CtIfImpl][CtCommentImpl]// additionalProperties not defined
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]addlProps == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]addlProps instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ObjectSchema) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]ObjectSchema objSchema = [CtVariableReadImpl](([CtTypeReferenceImpl]ObjectSchema) (addlProps));
                    [CtIfImpl][CtCommentImpl]// additionalProperties defined as {}
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]objSchema.getProperties() == [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]objSchema.getProperties().isEmpty()) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    }
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a Schema contains a reference to another Schema with '$ref', returns the referenced Schema if it is found or the actual Schema in the other cases.
     *
     * @param openAPI
     * 		specification being checked
     * @param schema
     * 		potentially containing a '$ref'
     * @return schema without '$ref'
     */
    public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getReferencedSchema([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]schema != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]Schema referencedSchema = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchema([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedSchema != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedSchema;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]schema;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getSchema([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemas([CtVariableReadImpl]openAPI).get([CtVariableReadImpl]name);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> getSchemas([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getSchemas() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getSchemas();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a RequestBody contains a reference to an other RequestBody with '$ref', returns the referenced RequestBody if it is found or the actual RequestBody in the other cases.
     *
     * @param openAPI
     * 		specification being checked
     * @param requestBody
     * 		potentially containing a '$ref'
     * @return requestBody without '$ref'
     */
    public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody getReferencedRequestBody([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody requestBody) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]requestBody != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]requestBody.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]requestBody.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody referencedRequestBody = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getRequestBody([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedRequestBody != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedRequestBody;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]requestBody;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody getRequestBody([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getRequestBodies() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getRequestBodies().get([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a ApiResponse contains a reference to an other ApiResponse with '$ref', returns the referenced ApiResponse if it is found or the actual ApiResponse in the other cases.
     *
     * @param openAPI
     * 		specification being checked
     * @param apiResponse
     * 		potentially containing a '$ref'
     * @return apiResponse without '$ref'
     */
    public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse getReferencedApiResponse([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse apiResponse) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]apiResponse != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]apiResponse.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]apiResponse.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse referencedApiResponse = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getApiResponse([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedApiResponse != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedApiResponse;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]apiResponse;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse getApiResponse([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getResponses() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getResponses().get([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a Parameter contains a reference to an other Parameter with '$ref', returns the referenced Parameter if it is found or the actual Parameter in the other cases.
     *
     * @param openAPI
     * 		specification being checked
     * @param parameter
     * 		potentially containing a '$ref'
     * @return parameter without '$ref'
     */
    public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter getReferencedParameter([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter parameter) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]parameter != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]parameter.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]parameter.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter referencedParameter = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getParameter([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedParameter != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedParameter;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]parameter;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.Parameter getParameter([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getParameters() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getParameters().get([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a Callback contains a reference to an other Callback with '$ref', returns the referenced Callback if it is found or the actual Callback in the other cases.
     *
     * @param openAPI
     * 		specification being checked
     * @param callback
     * 		potentially containing a '$ref'
     * @return callback without '$ref'
     */
    public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback getReferencedCallback([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback callback) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]callback != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]callback.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]callback.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback referencedCallback = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getCallback([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedCallback != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedCallback;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]callback;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.callbacks.Callback getCallback([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getCallbacks() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getCallbacks().get([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the first defined Schema for a RequestBody
     *
     * @param requestBody
     * 		request body of the operation
     * @return firstSchema
     */
    public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getSchemaFromRequestBody([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.parameters.RequestBody requestBody) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemaFromContent([CtInvocationImpl][CtVariableReadImpl]requestBody.getContent());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the first defined Schema for a ApiResponse
     *
     * @param response
     * 		api response of the operation
     * @return firstSchema
     */
    public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getSchemaFromResponse([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.responses.ApiResponse response) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemaFromContent([CtInvocationImpl][CtVariableReadImpl]response.getContent());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the first Schema from a specified OAS 'content' section.
     *
     * For example, given the following OAS, this method returns the schema
     * for the 'application/json' content type because it is listed first in the OAS.
     *
     * responses:
     *   '200':
     *     content:
     *       application/json:
     *         schema:
     *           $ref: '#/components/schemas/XYZ'
     *       application/xml:
     *          ...
     *
     * @param content
     * 		a 'content' section in the OAS specification.
     * @return the Schema.
     */
    private static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getSchemaFromContent([CtParameterImpl][CtTypeReferenceImpl]Content content) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]content == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]content.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]MediaType> entry = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.entrySet().iterator().next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]MediaType mediaType = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.values().iterator().next();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]content.size() > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Other content types are currently ignored by codegen. If you see this warning,
            [CtCommentImpl]// reorder the OAS spec to put the desired content type first.
            [CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Multiple schemas found in the OAS 'content' section, returning only the first one ('" + [CtInvocationImpl][CtVariableReadImpl]entry.getKey()) + [CtLiteralImpl]"')");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().getSchema();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the actual schema from aliases. If the provided schema is not an alias, the schema itself will be returned.
     *
     * @param openAPI
     * 		specification being checked
     * @param schema
     * 		schema (alias or direct reference)
     * @return actual schema
     */
    public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema unaliasSchema([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> allSchemas = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemas([CtVariableReadImpl]openAPI);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]allSchemas == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]allSchemas.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// skip the warning as the spec can have no model defined
            [CtCommentImpl]// LOGGER.warn("allSchemas cannot be null/empty in unaliasSchema. Returned 'schema'");
            return [CtVariableReadImpl]schema;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]schema != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Schema ref = [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ref == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.warn([CtLiteralImpl]"{} is not defined", [CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
                [CtReturnImpl]return [CtVariableReadImpl]schema;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ref.getEnum() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ref.getEnum().isEmpty())) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// top-level enum class
                return [CtVariableReadImpl]schema;
            } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isArraySchema([CtVariableReadImpl]ref)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isGenerateAliasAsModel()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]schema;[CtCommentImpl]// generate a model extending array

                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.unaliasSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())));
                }
            } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isComposedSchema([CtVariableReadImpl]ref)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]schema;
            } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isMapSchema([CtVariableReadImpl]ref)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// has at least one property
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ref.getProperties() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ref.getProperties().isEmpty()))[CtBlockImpl]
                    [CtReturnImpl]return [CtVariableReadImpl]schema;
                else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isGenerateAliasAsModel()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]schema;[CtCommentImpl]// generate a model extending map

                } else [CtBlockImpl]{
                    [CtReturnImpl][CtCommentImpl]// treat it as a typical map
                    return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.unaliasSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())));
                }
            } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.isObjectSchema([CtVariableReadImpl]ref)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// model
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ref.getProperties() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ref.getProperties().isEmpty())) [CtBlockImpl]{
                    [CtReturnImpl][CtCommentImpl]// has at least one property
                    return [CtVariableReadImpl]schema;
                } else [CtBlockImpl]{
                    [CtReturnImpl][CtCommentImpl]// free form object (type: object)
                    return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.unaliasSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())));
                }
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.unaliasSchema([CtVariableReadImpl]openAPI, [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtInvocationImpl][CtTypeAccessImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]schema;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.openapitools.codegen.utils.Schema getAdditionalProperties([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]schema.getAdditionalProperties() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Schema) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]Schema) ([CtVariableReadImpl]schema.getAdditionalProperties()));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getAdditionalProperties() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Boolean) && [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtVariableReadImpl]schema.getAdditionalProperties()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]ObjectSchema();
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header getReferencedHeader([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header header) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]header != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]header.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]header.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header referencedheader = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getHeader([CtVariableReadImpl]openAPI, [CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]referencedheader != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]referencedheader;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]header;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]io.swagger.v3.oas.models.headers.Header getHeader([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]name == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]openAPI != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getHeaders() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]openAPI.getComponents().getHeaders().get([CtVariableReadImpl]name);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> getChildrenMap([CtParameterImpl][CtTypeReferenceImpl]io.swagger.v3.oas.models.OpenAPI openAPI) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> allSchemas = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSchemas([CtVariableReadImpl]openAPI);
        [CtLocalVariableImpl][CtCommentImpl]// FIXME: The collect here will throw NPE if a spec document has only a single oneOf hierarchy.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema>>> groupedByParent = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]allSchemas.entrySet().stream().filter([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl]isComposedSchema([CtInvocationImpl][CtVariableReadImpl]entry.getValue())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.groupingBy([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl]getParentName([CtInvocationImpl](([CtTypeReferenceImpl]ComposedSchema) ([CtVariableReadImpl]entry.getValue())), [CtVariableReadImpl]allSchemas)));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]groupedByParent.entrySet().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().stream().map([CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl][CtVariableReadImpl]e.getKey()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList())));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the interfaces from the schema (composed)
     *
     * @param composed
     * 		schema (alias or direct reference)
     * @return a list of schema defined in allOf, anyOf or oneOf
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> getInterfaces([CtParameterImpl][CtTypeReferenceImpl]ComposedSchema composed) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]composed.getAllOf() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]composed.getAllOf().isEmpty())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]composed.getAllOf();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]composed.getAnyOf() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]composed.getAnyOf().isEmpty())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]composed.getAnyOf();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]composed.getOneOf() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]composed.getOneOf().isEmpty())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]composed.getOneOf();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.<[CtTypeReferenceImpl]Schema>emptyList();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the parent model name from the schemas (allOf, anyOf, oneOf).
     * If there are multiple parents, return the first one.
     *
     * @param composedSchema
     * 		schema (alias or direct reference)
     * @param allSchemas
     * 		all schemas
     * @return the name of the parent model
     */
    public static [CtTypeReferenceImpl]java.lang.String getParentName([CtParameterImpl][CtTypeReferenceImpl]ComposedSchema composedSchema, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> allSchemas) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> interfaces = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getInterfaces([CtVariableReadImpl]composedSchema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> refedParentNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]interfaces != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]interfaces.isEmpty())) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema schema : [CtVariableReadImpl]interfaces) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// get the actual schema
                if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentName = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Schema s = [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtVariableReadImpl]parentName);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.error([CtLiteralImpl]"Failed to obtain schema from {}", [CtVariableReadImpl]parentName);
                        [CtReturnImpl]return [CtLiteralImpl]"UNKNOWN_PARENT_NAME";
                    } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.hasOrInheritsDiscriminator([CtVariableReadImpl]s, [CtVariableReadImpl]allSchemas)) [CtBlockImpl]{
                        [CtReturnImpl][CtCommentImpl]// discriminator.propertyName is used
                        return [CtVariableReadImpl]parentName;
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.debug([CtLiteralImpl]"Not a parent since discriminator.propertyName is not set {}", [CtInvocationImpl][CtVariableReadImpl]s.get$ref());
                        [CtInvocationImpl][CtCommentImpl]// not a parent since discriminator.propertyName is not set
                        [CtVariableReadImpl]refedParentNames.add([CtVariableReadImpl]parentName);
                    }
                } else [CtBlockImpl]{
                    [CtCommentImpl]// not a ref, doing nothing
                }
            }
        }
        [CtIfImpl][CtCommentImpl]// parent name only makes sense when there is a single obvious parent
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]refedParentNames.size() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.warn([CtBinaryOperatorImpl][CtLiteralImpl]"[deprecated] inheritance without use of 'discriminator.propertyName' is deprecated " + [CtLiteralImpl]"and will be removed in a future release. Generating model for {}", [CtInvocationImpl][CtVariableReadImpl]composedSchema.getName());
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]refedParentNames.get([CtLiteralImpl]0);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the list of parent model names from the schemas (allOf, anyOf, oneOf).
     *
     * @param composedSchema
     * 		schema (alias or direct reference)
     * @param allSchemas
     * 		all schemas
     * @param includeAncestors
     * 		if true, include the indirect ancestors in the return value. If false, return the direct parents.
     * @return the name of the parent model
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAllParentsName([CtParameterImpl][CtTypeReferenceImpl]ComposedSchema composedSchema, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> allSchemas, [CtParameterImpl][CtTypeReferenceImpl]boolean includeAncestors) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> interfaces = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getInterfaces([CtVariableReadImpl]composedSchema);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> names = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]interfaces != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]interfaces.isEmpty())) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema schema : [CtVariableReadImpl]interfaces) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// get the actual schema
                if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentName = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Schema s = [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtVariableReadImpl]parentName);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.error([CtLiteralImpl]"Failed to obtain schema from {}", [CtVariableReadImpl]parentName);
                        [CtInvocationImpl][CtVariableReadImpl]names.add([CtLiteralImpl]"UNKNOWN_PARENT_NAME");
                    } else [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.hasOrInheritsDiscriminator([CtVariableReadImpl]s, [CtVariableReadImpl]allSchemas)) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// discriminator.propertyName is used
                        [CtVariableReadImpl]names.add([CtVariableReadImpl]parentName);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]includeAncestors && [CtBinaryOperatorImpl]([CtVariableReadImpl]s instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]names.addAll([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getAllParentsName([CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (s)), [CtVariableReadImpl]allSchemas, [CtLiteralImpl]true));
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.debug([CtLiteralImpl]"Not a parent since discriminator.propertyName is not set {}", [CtInvocationImpl][CtVariableReadImpl]s.get$ref());
                        [CtCommentImpl]// not a parent since discriminator.propertyName is not set
                    }
                } else [CtBlockImpl]{
                    [CtCommentImpl]// not a ref, doing nothing
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]names;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasOrInheritsDiscriminator([CtParameterImpl][CtTypeReferenceImpl]Schema schema, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]Schema> allSchemas) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getDiscriminator() != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getDiscriminator().getPropertyName())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtInvocationImpl][CtVariableReadImpl]schema.get$ref())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentName = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getSimpleRef([CtInvocationImpl][CtVariableReadImpl]schema.get$ref());
            [CtLocalVariableImpl][CtTypeReferenceImpl]Schema s = [CtInvocationImpl][CtVariableReadImpl]allSchemas.get([CtVariableReadImpl]parentName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.hasOrInheritsDiscriminator([CtVariableReadImpl]s, [CtVariableReadImpl]allSchemas);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.openapitools.codegen.utils.ModelUtils.LOGGER.error([CtLiteralImpl]"Failed to obtain schema from {}", [CtVariableReadImpl]parentName);
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ComposedSchema) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]ComposedSchema composed = [CtVariableReadImpl](([CtTypeReferenceImpl]ComposedSchema) (schema));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Schema> interfaces = [CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.getInterfaces([CtVariableReadImpl]composed);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Schema i : [CtVariableReadImpl]interfaces) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]org.openapitools.codegen.utils.ModelUtils.hasOrInheritsDiscriminator([CtVariableReadImpl]i, [CtVariableReadImpl]allSchemas)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean isNullable([CtParameterImpl][CtTypeReferenceImpl]Schema schema) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]schema == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]schema.getNullable())) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]schema.getExtensions() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getExtensions().get([CtLiteralImpl]"x-nullable") != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]schema.getExtensions().get([CtLiteralImpl]"x-nullable").toString());
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void syncValidationProperties([CtParameterImpl][CtTypeReferenceImpl]Schema schema, [CtParameterImpl][CtTypeReferenceImpl]org.openapitools.codegen.IJsonSchemaValidationProperties target) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]schema != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]target != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.setPattern([CtInvocationImpl][CtVariableReadImpl]schema.getPattern());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.math.BigDecimal minimum = [CtInvocationImpl][CtVariableReadImpl]schema.getMinimum();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.math.BigDecimal maximum = [CtInvocationImpl][CtVariableReadImpl]schema.getMaximum();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean exclusiveMinimum = [CtInvocationImpl][CtVariableReadImpl]schema.getExclusiveMinimum();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean exclusiveMaximum = [CtInvocationImpl][CtVariableReadImpl]schema.getExclusiveMaximum();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer minLength = [CtInvocationImpl][CtVariableReadImpl]schema.getMinLength();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer maxLength = [CtInvocationImpl][CtVariableReadImpl]schema.getMaxLength();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer minItems = [CtInvocationImpl][CtVariableReadImpl]schema.getMinItems();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer maxItems = [CtInvocationImpl][CtVariableReadImpl]schema.getMaxItems();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean uniqueItems = [CtInvocationImpl][CtVariableReadImpl]schema.getUniqueItems();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer minProperties = [CtInvocationImpl][CtVariableReadImpl]schema.getMinProperties();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer maxProperties = [CtInvocationImpl][CtVariableReadImpl]schema.getMaxProperties();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minimum != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMinimum([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]minimum));

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maximum != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMaximum([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]maximum));

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exclusiveMinimum != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setExclusiveMinimum([CtVariableReadImpl]exclusiveMinimum);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exclusiveMaximum != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setExclusiveMaximum([CtVariableReadImpl]exclusiveMaximum);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minLength != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMinLength([CtVariableReadImpl]minLength);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxLength != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMaxLength([CtVariableReadImpl]maxLength);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minItems != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMinItems([CtVariableReadImpl]minItems);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxItems != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMaxItems([CtVariableReadImpl]maxItems);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]uniqueItems != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setUniqueItems([CtVariableReadImpl]uniqueItems);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minProperties != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMinProperties([CtVariableReadImpl]minProperties);

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxProperties != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]target.setMaxProperties([CtVariableReadImpl]maxProperties);

        }
    }
}
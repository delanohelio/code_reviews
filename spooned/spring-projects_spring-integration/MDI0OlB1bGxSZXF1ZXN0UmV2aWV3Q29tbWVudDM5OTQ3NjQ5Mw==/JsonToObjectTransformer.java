[CompilationUnitImpl][CtCommentImpl]/* Copyright 2002-2020 the original author or authors.

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
[CtPackageDeclarationImpl]package org.springframework.integration.json;
[CtUnresolvedImport]import org.springframework.integration.mapping.support.JsonHeaders;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.springframework.integration.expression.ExpressionUtils;
[CtUnresolvedImport]import org.springframework.integration.support.json.JsonObjectMapperProvider;
[CtImportImpl]import java.io.UncheckedIOException;
[CtUnresolvedImport]import org.springframework.messaging.MessageHeaders;
[CtUnresolvedImport]import org.springframework.expression.EvaluationContext;
[CtUnresolvedImport]import org.springframework.integration.transformer.AbstractTransformer;
[CtUnresolvedImport]import org.springframework.integration.support.json.JsonObjectMapper;
[CtUnresolvedImport]import org.springframework.integration.expression.FunctionExpression;
[CtUnresolvedImport]import org.springframework.expression.Expression;
[CtUnresolvedImport]import org.springframework.lang.Nullable;
[CtUnresolvedImport]import org.springframework.messaging.Message;
[CtUnresolvedImport]import org.springframework.core.ResolvableType;
[CtUnresolvedImport]import org.springframework.beans.factory.BeanClassLoaderAware;
[CtUnresolvedImport]import org.springframework.util.Assert;
[CtClassImpl][CtJavaDocImpl]/**
 * Transformer implementation that converts a JSON string payload into an instance of the
 * provided target Class. By default this transformer uses
 * {@linkplain org.springframework.integration.support.json.JsonObjectMapperProvider}
 * factory to get an instance of Jackson JSON-processor
 * if jackson-databind lib is present on the classpath. Any other {@linkplain JsonObjectMapper}
 * implementation can be provided.
 * <p> Starting version 3.0, you can omit the target class and the target type can be
 * determined by the {@link JsonHeaders} type entries - including the contents of a
 * one-level container or map type.
 * <p> The type headers can be classes or fully-qualified class names.
 * <p> Starting version 5.2.6, a SpEL expression option is provided to let to build a target
 * {@link ResolvableType} somehow externally.
 *
 * @author Mark Fisher
 * @author Artem Bilan
 * @since 2.0
 * @see JsonObjectMapper
 * @see org.springframework.integration.support.json.JsonObjectMapperProvider
 * @see ResolvableType
 */
public class JsonToObjectTransformer extends [CtTypeReferenceImpl]org.springframework.integration.transformer.AbstractTransformer implements [CtTypeReferenceImpl]org.springframework.beans.factory.BeanClassLoaderAware {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.springframework.core.ResolvableType targetType;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.springframework.integration.support.json.JsonObjectMapper<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> jsonObjectMapper;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.ClassLoader classLoader;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.expression.Expression valueTypeExpression = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.integration.expression.FunctionExpression<[CtTypeReferenceImpl]org.springframework.messaging.Message<[CtWildcardReferenceImpl]?>>([CtLambdaImpl]([CtParameterImpl] message) -> [CtInvocationImpl]obtainResolvableTypeFromHeadersIfAny([CtInvocationImpl][CtVariableReadImpl]message.getHeaders(), [CtFieldReadImpl][CtThisAccessImpl]this.classLoader));

    [CtFieldImpl]private [CtTypeReferenceImpl]org.springframework.expression.EvaluationContext evaluationContext;

    [CtConstructorImpl]public JsonToObjectTransformer() [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>) (null)));
    }

    [CtConstructorImpl]public JsonToObjectTransformer([CtParameterImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> targetClass) [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ResolvableType.forClass([CtVariableReadImpl]targetClass));
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Construct an instance based on the provided {@link ResolvableType}.
     *
     * @param targetType
     * 		the {@link ResolvableType} to use.
     * @since 5.2
     */
    public JsonToObjectTransformer([CtParameterImpl][CtTypeReferenceImpl]org.springframework.core.ResolvableType targetType) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]targetType, [CtLiteralImpl]null);
    }

    [CtConstructorImpl]public JsonToObjectTransformer([CtParameterImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    [CtTypeReferenceImpl]org.springframework.integration.support.json.JsonObjectMapper<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> jsonObjectMapper) [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl](([CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>) (null)), [CtVariableReadImpl]jsonObjectMapper);
    }

    [CtConstructorImpl]public JsonToObjectTransformer([CtParameterImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> targetClass, [CtParameterImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    [CtTypeReferenceImpl]org.springframework.integration.support.json.JsonObjectMapper<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> jsonObjectMapper) [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ResolvableType.forClass([CtVariableReadImpl]targetClass), [CtVariableReadImpl]jsonObjectMapper);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Construct an instance based on the provided {@link ResolvableType} and {@link JsonObjectMapper}.
     *
     * @param targetType
     * 		the {@link ResolvableType} to use.
     * @param jsonObjectMapper
     * 		the {@link JsonObjectMapper} to use.
     * @since 5.2
     */
    public JsonToObjectTransformer([CtParameterImpl][CtTypeReferenceImpl]org.springframework.core.ResolvableType targetType, [CtParameterImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    [CtTypeReferenceImpl]org.springframework.integration.support.json.JsonObjectMapper<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> jsonObjectMapper) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.Assert.notNull([CtVariableReadImpl]targetType, [CtLiteralImpl]"'targetType' must not be null");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.targetType = [CtVariableReadImpl]targetType;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jsonObjectMapper = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]jsonObjectMapper != [CtLiteralImpl]null) ? [CtVariableReadImpl]jsonObjectMapper : [CtInvocationImpl][CtTypeAccessImpl]org.springframework.integration.support.json.JsonObjectMapperProvider.newInstance();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setBeanClassLoader([CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.classLoader = [CtVariableReadImpl]classLoader;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.jsonObjectMapper instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.springframework.beans.factory.BeanClassLoaderAware) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]org.springframework.beans.factory.BeanClassLoaderAware) ([CtThisAccessImpl]this.jsonObjectMapper)).setBeanClassLoader([CtVariableReadImpl]classLoader);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Configure a SpEL expression to evaluate a {@link ResolvableType}
     * to instantiate the payload from the incoming JSON.
     * By default this transformer consults {@link JsonHeaders} in the request message.
     * If this expression returns {@code null} or {@link ResolvableType} building throws a
     * {@link ClassNotFoundException}, this transformer falls back to the provided {@link #targetType}.
     * This logic is present as an expression because {@link JsonHeaders} may not have real class values,
     * but rather some type ids which have to be mapped to target classes according some external registry.
     *
     * @param valueTypeExpressionString
     * 		the SpEL expression to use.
     * @since 5.2.6
     */
    public [CtTypeReferenceImpl]void setValueTypeExpressionString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String valueTypeExpressionString) [CtBlockImpl]{
        [CtInvocationImpl]setValueTypeExpression([CtInvocationImpl][CtTypeAccessImpl]org.springframework.integration.json.EXPRESSION_PARSER.parseExpression([CtVariableReadImpl]valueTypeExpressionString));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Configure a SpEL {@link Expression} to evaluate a {@link ResolvableType}
     * to instantiate the payload from the incoming JSON.
     * By default this transformer consults {@link JsonHeaders} in the request message.
     * If this expression returns {@code null} or {@link ResolvableType} building throws a
     * {@link ClassNotFoundException}, this transformer falls back to the provided {@link #targetType}.
     * This logic is present as an expression because {@link JsonHeaders} may not have real class values,
     * but rather some type ids which have to be mapped to target classes according some external registry.
     *
     * @param valueTypeExpression
     * 		the SpEL {@link Expression} to use.
     * @since 5.2.6
     */
    public [CtTypeReferenceImpl]void setValueTypeExpression([CtParameterImpl][CtTypeReferenceImpl]org.springframework.expression.Expression valueTypeExpression) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.valueTypeExpression = [CtVariableReadImpl]valueTypeExpression;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getComponentType() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"json-to-object-transformer";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onInit() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onInit();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.evaluationContext = [CtInvocationImpl][CtTypeAccessImpl]org.springframework.integration.expression.ExpressionUtils.createStandardEvaluationContext([CtInvocationImpl]getBeanFactory());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.Object doTransform([CtParameterImpl][CtTypeReferenceImpl]org.springframework.messaging.Message<[CtWildcardReferenceImpl]?> message) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.core.ResolvableType valueType = [CtInvocationImpl]obtainResolvableType([CtVariableReadImpl]message);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean removeHeaders = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]valueType != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]removeHeaders = [CtLiteralImpl]true;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]valueType = [CtFieldReadImpl][CtThisAccessImpl]this.targetType;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object result;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.jsonObjectMapper.fromJson([CtInvocationImpl][CtVariableReadImpl]message.getPayload(), [CtVariableReadImpl]valueType);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.UncheckedIOException([CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtVariableReadImpl]removeHeaders) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getMessageBuilderFactory().withPayload([CtVariableReadImpl]result).copyHeaders([CtInvocationImpl][CtVariableReadImpl]message.getHeaders()).removeHeaders([CtInvocationImpl][CtTypeAccessImpl]JsonHeaders.HEADERS.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0])).build();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]result;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    private [CtTypeReferenceImpl]org.springframework.core.ResolvableType obtainResolvableType([CtParameterImpl][CtTypeReferenceImpl]org.springframework.messaging.Message<[CtWildcardReferenceImpl]?> message) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.valueTypeExpression.getValue([CtFieldReadImpl][CtThisAccessImpl]this.evaluationContext, [CtVariableReadImpl]message, [CtFieldReadImpl]org.springframework.core.ResolvableType.class);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ex.getCause() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Cannot build a ResolvableType from a request message '" + [CtVariableReadImpl]message) + [CtLiteralImpl]"' evaluating expression '") + [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.valueTypeExpression.getExpressionString()) + [CtLiteralImpl]"'", [CtVariableReadImpl]ex);
                [CtReturnImpl]return [CtLiteralImpl]null;
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtVariableReadImpl]ex;
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.lang.Nullable
    private static [CtTypeReferenceImpl]org.springframework.core.ResolvableType obtainResolvableTypeFromHeadersIfAny([CtParameterImpl][CtTypeReferenceImpl]org.springframework.messaging.MessageHeaders headers, [CtParameterImpl][CtTypeReferenceImpl]java.lang.ClassLoader classLoader) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object valueType = [CtInvocationImpl][CtVariableReadImpl]headers.get([CtTypeAccessImpl]JsonHeaders.RESOLVABLE_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object typeIdHeader = [CtInvocationImpl][CtVariableReadImpl]headers.get([CtTypeAccessImpl]JsonHeaders.TYPE_ID);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]valueType instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.springframework.core.ResolvableType)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]typeIdHeader != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]valueType = [CtInvocationImpl][CtTypeAccessImpl]org.springframework.integration.mapping.support.JsonHeaders.buildResolvableType([CtVariableReadImpl]classLoader, [CtVariableReadImpl]typeIdHeader, [CtInvocationImpl][CtVariableReadImpl]headers.get([CtTypeAccessImpl]JsonHeaders.CONTENT_TYPE_ID), [CtInvocationImpl][CtVariableReadImpl]headers.get([CtTypeAccessImpl]JsonHeaders.KEY_TYPE_ID));
        }
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]valueType instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.springframework.core.ResolvableType ? [CtVariableReadImpl](([CtTypeReferenceImpl]org.springframework.core.ResolvableType) (valueType)) : [CtLiteralImpl]null;
    }
}
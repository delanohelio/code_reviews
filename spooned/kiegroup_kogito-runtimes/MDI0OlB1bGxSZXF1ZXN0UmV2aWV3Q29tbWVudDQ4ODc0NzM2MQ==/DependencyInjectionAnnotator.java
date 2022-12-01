[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Red Hat, Inc. and/or its affiliates.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.kie.kogito.codegen.di;
[CtUnresolvedImport]import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
[CtUnresolvedImport]import com.github.javaparser.ast.NodeList;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.MethodCallExpr;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.Name;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.Expression;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.NameExpr;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.ArrayInitializerExpr;
[CtUnresolvedImport]import com.github.javaparser.ast.expr.StringLiteralExpr;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Generic abstraction for dependency injection annotations that allow to
 * use different frameworks based needs.
 *
 * Currently in scope
 *
 * <ul>
 *  <li>CDI</li>
 *  <li>Spring</li>
 * </ul>
 */
public interface DependencyInjectionAnnotator {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with name annotation e.g. Named, Qualifier
     *
     * @param node
     * 		node to be annotated
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withNamed([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with application level annotations e.g. ApplicationScoped, Component
     *
     * @param node
     * 		node to be annotated
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withApplicationComponent([CtParameterImpl][CtTypeParameterReferenceImpl]T node);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with application level annotations e.g. ApplicationScoped, Component
     * additionally adding name to it
     *
     * @param node
     * 		node to be annotated
     * @param name
     * 		name to be assigned to given node
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withNamedApplicationComponent([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with singleton level annotations e.g. Singleton, Component
     *
     * @param node
     * 		node to be annotated
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withSingletonComponent([CtParameterImpl][CtTypeParameterReferenceImpl]T node);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with singleton level annotations e.g. Singleton, Component
     * additionally adding name to it
     *
     * @param node
     * 		node to be annotated
     * @param name
     * 		name to be assigned to given node
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withNamedSingletonComponent([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with injection annotations e.g. Inject, Autowire
     *
     * @param node
     * 		node to be annotated
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withInjection([CtParameterImpl][CtTypeParameterReferenceImpl]T node);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with injection annotations e.g. Inject, Autowire
     * additionally adding name to it
     *
     * @param node
     * 		node to be annotated
     * @param name
     * 		name to be assigned to given node
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withNamedInjection([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with optional injection annotations e.g. Inject, Autowire
     *
     * @param node
     * 		node to be annotated
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withOptionalInjection([CtParameterImpl][CtTypeParameterReferenceImpl]T node);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with incoming message that it should consume from
     *
     * @param node
     * 		node to be annotated
     * @param channel
     * 		name of the channel messages should be consumer from
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withIncomingMessage([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String channel);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with outgoing message that it should send to
     *
     * @param node
     * 		node to be annotated
     * @param channel
     * 		name of the channel messages should be send to
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withOutgoingMessage([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String channel);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with configuration parameter injection
     *
     * @param node
     * 		node to be annotated
     * @param configKey
     * 		name of the configuration property to be injected
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withConfigInjection([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String configKey);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with configuration parameter injection with default value
     *
     * @param node
     * 		node to be annotated
     * @param configKey
     * 		name of the configuration property to be injected
     * @param defaultValue
     * 		value to be used in case there is no config parameter defined
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withConfigInjection([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String configKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultValue);

    [CtMethodImpl]default <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withLazyInit([CtParameterImpl][CtTypeParameterReferenceImpl]T node) [CtBlockImpl]{
        [CtReturnImpl]return [CtVariableReadImpl]node;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates and enhances method used to produce messages
     *
     * @param produceMethod
     * 		method to be annotated
     * @param channel
     * 		channel on which messages should be produced
     * @param event
     * 		actual data to be send
     */
    [CtTypeReferenceImpl]com.github.javaparser.ast.expr.MethodCallExpr withMessageProducer([CtParameterImpl][CtTypeReferenceImpl]com.github.javaparser.ast.expr.MethodCallExpr produceMethod, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String channel, [CtParameterImpl][CtTypeReferenceImpl]com.github.javaparser.ast.expr.Expression event);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Annotates given node with set of roles to enforce security
     *
     * @param node
     * 		node to be annotated
     * @param roles
     * 		roles that are allowed
     */
    default <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.github.javaparser.ast.nodeTypes.NodeWithAnnotations<[CtWildcardReferenceImpl]?>> [CtTypeParameterReferenceImpl]T withSecurityRoles([CtParameterImpl][CtTypeParameterReferenceImpl]T node, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] roles) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roles != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]roles.length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.github.javaparser.ast.expr.Expression> rolesExpr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String role : [CtVariableReadImpl]roles) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rolesExpr.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.StringLiteralExpr([CtInvocationImpl][CtVariableReadImpl]role.trim()));
            }
            [CtInvocationImpl][CtVariableReadImpl]node.addAnnotation([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.SingleMemberAnnotationExpr([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.Name([CtLiteralImpl]"javax.annotation.security.RolesAllowed"), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.ArrayInitializerExpr([CtInvocationImpl][CtTypeAccessImpl]com.github.javaparser.ast.NodeList.nodeList([CtVariableReadImpl]rolesExpr))));
        }
        [CtReturnImpl]return [CtVariableReadImpl]node;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns type that allows to inject optional instances of the same type
     *
     * @return fully qualified class name
     */
    [CtTypeReferenceImpl]java.lang.String optionalInstanceInjectionType();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an expression that represents optional instance for given field
     *
     * @param fieldName
     * 		name of the field that should be considered optional
     * @return complete expression for optional instance
     */
    [CtTypeReferenceImpl]com.github.javaparser.ast.expr.Expression optionalInstanceExists([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an expression that returns instance for given optional field
     *
     * @param fieldName
     * 		name of the optional field that should be accessed
     * @return complete expression for optional instance
     */
    default [CtTypeReferenceImpl]com.github.javaparser.ast.expr.Expression getOptionalInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.MethodCallExpr([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.github.javaparser.ast.expr.NameExpr([CtVariableReadImpl]fieldName), [CtLiteralImpl]"get");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns type that allows to inject multiple instances of the same type
     *
     * @return fully qualified class name
     */
    [CtTypeReferenceImpl]java.lang.String multiInstanceInjectionType();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates an expression that returns a list of instances for given multi instance field
     *
     * @param fieldName
     * 		name of the multi field that should be accessed
     * @return complete expression for multi instance
     */
    [CtTypeReferenceImpl]com.github.javaparser.ast.expr.Expression getMultiInstance([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns type that allows to mark instance as application component e.g. ApplicationScoped, Component
     *
     * @return fully qualified class name
     */
    [CtTypeReferenceImpl]java.lang.String applicationComponentType();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns type to be used as message emitter
     *
     * @param dataType
     * 		type of the data produces by the emitter
     * @return fully qualified class name
     */
    [CtTypeReferenceImpl]java.lang.String emitterType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dataType);

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String objectMapperInjectorSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName);
}
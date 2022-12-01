[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2010-2020. Axon Framework

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
[CtPackageDeclarationImpl]package org.axonframework.modelling.command.inspection;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.ParameterResolverFactory;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import static java.util.stream.Collectors.toList;
[CtUnresolvedImport]import static org.axonframework.common.ListUtils.distinct;
[CtUnresolvedImport]import org.axonframework.common.IdentifierValidator;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.HandlerDefinition;
[CtImportImpl]import java.util.SortedSet;
[CtImportImpl]import java.lang.reflect.Field;
[CtImportImpl]import java.lang.reflect.Modifier;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.axonframework.messaging.Message;
[CtUnresolvedImport]import org.axonframework.modelling.command.AggregateVersion;
[CtUnresolvedImport]import org.axonframework.commandhandling.CommandMessageHandlingMember;
[CtUnresolvedImport]import org.axonframework.common.ReflectionUtils;
[CtImportImpl]import java.lang.reflect.AccessibleObject;
[CtUnresolvedImport]import org.axonframework.common.annotation.AnnotationUtils;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.AnnotatedHandlerInspector;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import java.lang.reflect.Member;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.ClasspathParameterResolverFactory;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.MessageHandlingMember;
[CtUnresolvedImport]import org.axonframework.eventhandling.EventMessage;
[CtUnresolvedImport]import org.axonframework.modelling.command.EntityId;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.lang.reflect.Constructor;
[CtImportImpl]import static java.lang.String.format;
[CtImportImpl]import java.lang.reflect.Method;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.ClasspathHandlerDefinition;
[CtImportImpl]import java.util.ServiceLoader;
[CtUnresolvedImport]import org.axonframework.modelling.command.AggregateRoot;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.axonframework.messaging.annotation.MessageHandlerInvocationException;
[CtClassImpl][CtJavaDocImpl]/**
 * AggregateMetaModelFactory implementation that uses annotations on the target aggregate's members to build up the meta
 * model of the aggregate.
 */
public class AnnotatedAggregateMetaModelFactory implements [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateMetaModelFactory {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel> registry;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.axonframework.messaging.annotation.HandlerDefinition handlerDefinition;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shorthand to create a factory instance and inspect the model for the given {@code aggregateType}.
     *
     * @param aggregateType
     * 		The class of the aggregate to create the model for
     * @param <T>
     * 		The type of aggregate described in the model
     * @return The model describing the structure of the aggregate
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> inspectAggregate([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> aggregateType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory().createModel([CtVariableReadImpl]aggregateType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shorthand to create a factory instance and inspect the model for the given {@code aggregateType} and its {@code subtypes}.
     *
     * @param aggregateType
     * 		The class of the aggregate to create the model for
     * @param subtypes
     * 		Subtypes of this aggregate class
     * @param <T>
     * 		The type of aggregate described in the model
     * @return The model describing the structure of the aggregate
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> inspectAggregate([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T>> subtypes) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory().createModel([CtVariableReadImpl]aggregateType, [CtVariableReadImpl]subtypes);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shorthand to create a factory instance and inspect the model for the given {@code aggregateType}, using given
     * {@code parameterResolverFactory} to resolve parameter values for annotated handlers.
     *
     * @param aggregateType
     * 		The class of the aggregate to create the model for
     * @param parameterResolverFactory
     * 		to resolve parameter values of annotated handlers with
     * @param <T>
     * 		The type of aggregate described in the model
     * @return The model describing the structure of the aggregate
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> inspectAggregate([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory([CtVariableReadImpl]parameterResolverFactory).createModel([CtVariableReadImpl]aggregateType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shorthand to create a factory instance and inspect the model for the given {@code aggregateType}, using given
     * {@code parameterResolverFactory} to resolve parameter values for annotated handlers and {@code handlerDefinition}
     * to create concrete handlers.
     *
     * @param aggregateType
     * 		The class of the aggregate to create the model for
     * @param parameterResolverFactory
     * 		to resolve parameter values of annotated handlers with
     * @param handlerDefinition
     * 		The handler definition used to create concrete handlers
     * @param <T>
     * 		The type of aggregate described in the model
     * @return The model describing the structure of the aggregate
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> inspectAggregate([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.HandlerDefinition handlerDefinition) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory([CtVariableReadImpl]parameterResolverFactory, [CtVariableReadImpl]handlerDefinition).createModel([CtVariableReadImpl]aggregateType);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shorthand to create a factory instance and inspect the model for the given {@code aggregateType} and its {@code subytpes}, using given {@code parameterResolverFactory} to resolve parameter values for annotated handlers and
     * {@code handlerDefinition} to create concrete handlers.
     *
     * @param aggregateType
     * 		The class of the aggregate to create the model for
     * @param parameterResolverFactory
     * 		to resolve parameter values of annotated handlers with
     * @param handlerDefinition
     * 		The handler definition used to create concrete handlers
     * @param subtypes
     * 		Subtypes of this aggregate class
     * @param <T>
     * 		The type of aggregate described in the model
     * @return The model describing the structure of the aggregate
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> inspectAggregate([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.HandlerDefinition handlerDefinition, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T>> subtypes) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory([CtVariableReadImpl]parameterResolverFactory, [CtVariableReadImpl]handlerDefinition).createModel([CtVariableReadImpl]aggregateType, [CtVariableReadImpl]subtypes);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Initializes an instance which uses the default, classpath based, ParameterResolverFactory to detect parameters
     * for annotated handlers.
     */
    public AnnotatedAggregateMetaModelFactory() [CtBlockImpl]{
        [CtInvocationImpl]this([CtInvocationImpl][CtTypeAccessImpl]org.axonframework.messaging.annotation.ClasspathParameterResolverFactory.forClassLoader([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader()));
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Initializes an instance which uses the given {@code parameterResolverFactory} to detect parameters for annotated
     * handlers.
     *
     * @param parameterResolverFactory
     * 		to resolve parameter values of annotated handlers with
     */
    public AnnotatedAggregateMetaModelFactory([CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]parameterResolverFactory, [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.messaging.annotation.ClasspathHandlerDefinition.forClassLoader([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getContextClassLoader()));
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Initializes an instance which uses the given {@code parameterResolverFactory} to detect parameters for annotated
     * handlers and {@code handlerDefinition} to create concrete handlers.
     *
     * @param parameterResolverFactory
     * 		to resolve parameter values of annotated handlers with
     * @param handlerDefinition
     * 		The handler definition used to create concrete handlers
     */
    public AnnotatedAggregateMetaModelFactory([CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.ParameterResolverFactory parameterResolverFactory, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.HandlerDefinition handlerDefinition) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parameterResolverFactory = [CtVariableReadImpl]parameterResolverFactory;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.handlerDefinition = [CtVariableReadImpl]handlerDefinition;
        [CtAssignmentImpl][CtFieldWriteImpl]registry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<[CtTypeParameterReferenceImpl]T> createModel([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T>> subtypes) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]registry.containsKey([CtVariableReadImpl]aggregateType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.AnnotatedHandlerInspector<[CtTypeParameterReferenceImpl]T> inspector = [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.messaging.annotation.AnnotatedHandlerInspector.inspectType([CtVariableReadImpl]aggregateType, [CtFieldReadImpl]parameterResolverFactory, [CtFieldReadImpl]handlerDefinition, [CtVariableReadImpl]subtypes);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<[CtTypeParameterReferenceImpl]T> model = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<>([CtVariableReadImpl]aggregateType, [CtVariableReadImpl]inspector);
            [CtInvocationImpl][CtCommentImpl]// Add the newly created inspector to the registry first to prevent a StackOverflowError:
            [CtCommentImpl]// another call to createInspector with the same inspectedType will return this instance of the inspector.
            [CtFieldReadImpl]registry.put([CtVariableReadImpl]aggregateType, [CtVariableReadImpl]model);
            [CtInvocationImpl][CtVariableReadImpl]model.initialize();
        }
        [CtReturnImpl][CtCommentImpl]// noinspection unchecked
        return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]registry.get([CtVariableReadImpl]aggregateType).whenReadSafe();
    }

    [CtClassImpl]private class AnnotatedAggregateModel<[CtTypeParameterImpl]T> implements [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModel<[CtTypeParameterReferenceImpl]T> {
        [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String JAVAX_PERSISTENCE_ID = [CtLiteralImpl]"javax.persistence.Id";

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> inspectedType;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.ChildEntity<[CtTypeParameterReferenceImpl]T>> children;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.axonframework.messaging.annotation.AnnotatedHandlerInspector<[CtTypeParameterReferenceImpl]T> handlerInspector;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allCommandHandlerInterceptors;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allCommandHandlers;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allEventHandlers;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> types;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.lang.String> declaredTypes;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.reflect.Member identifierMember;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.reflect.Member versionMember;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String routingKey;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.ThreadLocal<[CtTypeReferenceImpl]java.lang.Boolean> initializing = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ThreadLocal<>();

        [CtFieldImpl]private volatile [CtTypeReferenceImpl]boolean initialized;

        [CtConstructorImpl]public AnnotatedAggregateModel([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> aggregateType, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.AnnotatedHandlerInspector<[CtTypeParameterReferenceImpl]T> handlerInspector) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inspectedType = [CtVariableReadImpl]aggregateType;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.types = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.declaredTypes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allCommandHandlerInterceptors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allCommandHandlers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allEventHandlers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.children = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.handlerInspector = [CtVariableReadImpl]handlerInspector;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void initialize() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]initializing.set([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
            [CtInvocationImpl]inspectFieldsAndMethods();
            [CtInvocationImpl]prepareHandlers();
            [CtInvocationImpl]inspectAggregateTypes();
            [CtAssignmentImpl][CtFieldWriteImpl]initialized = [CtLiteralImpl]true;
            [CtInvocationImpl][CtFieldReadImpl]initializing.remove();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        private [CtTypeReferenceImpl]void prepareHandlers() [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.SortedSet<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> handlersPerType : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]handlerInspector.getAllHandlers().entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type = [CtInvocationImpl][CtVariableReadImpl]handlersPerType.getKey();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> handler : [CtInvocationImpl][CtVariableReadImpl]handlersPerType.getValue()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.unwrap([CtFieldReadImpl]org.axonframework.commandhandling.CommandMessageHandlingMember.class).isPresent()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isAbstract([CtInvocationImpl][CtVariableReadImpl]type.getModifiers()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.unwrap([CtFieldReadImpl]java.lang.reflect.Constructor.class).isPresent()) [CtBlockImpl]{
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"An abstract aggregate %s cannot have @CommandHandler on constructor.", [CtVariableReadImpl]type));
                        }
                        [CtInvocationImpl]addHandler([CtFieldReadImpl]allCommandHandlers, [CtVariableReadImpl]type, [CtVariableReadImpl]handler);
                    } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.unwrap([CtFieldReadImpl]org.axonframework.modelling.command.inspection.CommandHandlerInterceptorHandlingMember.class).isPresent()) [CtBlockImpl]{
                        [CtInvocationImpl]addHandler([CtFieldReadImpl]allCommandHandlerInterceptors, [CtVariableReadImpl]type, [CtVariableReadImpl]handler);
                    } else [CtBlockImpl]{
                        [CtInvocationImpl]addHandler([CtFieldReadImpl]allEventHandlers, [CtVariableReadImpl]type, [CtVariableReadImpl]handler);
                    }
                }
            }
            [CtInvocationImpl]validateCommandHandlers();
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void addHandler([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> handlers, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type, [CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> handler) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handlers.computeIfAbsent([CtVariableReadImpl]type, [CtLambdaImpl]([CtParameterImpl] t) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.ArrayList<>()).add([CtVariableReadImpl]handler);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * In polymorphic aggregate hierarchy there must not be more than one creational (factory) command handler (of
         * the same command name) in more than one aggregate.
         */
        private [CtTypeReferenceImpl]void validateCommandHandlers() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> handlers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]allCommandHandlers.values());
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]handlers.size() - [CtLiteralImpl]1); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.commandhandling.CommandMessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> factoryCommands1 = [CtInvocationImpl]factoryCommands([CtInvocationImpl][CtVariableReadImpl]handlers.get([CtVariableReadImpl]i));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.commandhandling.CommandMessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> factoryCommands2 = [CtInvocationImpl]factoryCommands([CtInvocationImpl][CtVariableReadImpl]handlers.get([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1));
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.axonframework.commandhandling.CommandMessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> handler1 : [CtVariableReadImpl]factoryCommands1) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.axonframework.commandhandling.CommandMessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T> handler2 : [CtVariableReadImpl]factoryCommands2) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commandName1 = [CtInvocationImpl][CtVariableReadImpl]handler1.commandName();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commandName2 = [CtInvocationImpl][CtVariableReadImpl]handler2.commandName();
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]commandName1.equals([CtVariableReadImpl]commandName2)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass1 = [CtInvocationImpl][CtVariableReadImpl]handler1.declaringClass();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> declaringClass2 = [CtInvocationImpl][CtVariableReadImpl]handler2.declaringClass();
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]declaringClass1.equals([CtVariableReadImpl]declaringClass2)) [CtBlockImpl]{
                                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregates %s and %s have the same creation @CommandHandler %s", [CtVariableReadImpl]declaringClass1, [CtVariableReadImpl]declaringClass2, [CtVariableReadImpl]commandName1));
                            }
                        }
                    }
                }
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.commandhandling.CommandMessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> factoryCommands([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> handlers) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handlers.stream().map([CtLambdaImpl]([CtParameterImpl] h) -> [CtInvocationImpl][CtVariableReadImpl]h.unwrap([CtFieldReadImpl]org.axonframework.commandhandling.CommandMessageHandlingMember.class)).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Optional::isPresent).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Optional::get).filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]CommandMessageHandlingMember::isFactoryHandler).map([CtLambdaImpl]([CtParameterImpl] h) -> [CtVariableReadImpl](([CtTypeReferenceImpl]CommandMessageHandlingMember<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.T>) (h))).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void inspectAggregateTypes() [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]handlerInspector.getAllHandlers().keySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String declaredType = [CtInvocationImpl]findDeclaredType([CtVariableReadImpl]type);
                [CtInvocationImpl][CtFieldReadImpl]types.put([CtVariableReadImpl]declaredType, [CtVariableReadImpl]type);
                [CtInvocationImpl][CtFieldReadImpl]declaredTypes.put([CtVariableReadImpl]type, [CtVariableReadImpl]declaredType);
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String findDeclaredType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]type, [CtFieldReadImpl]org.axonframework.modelling.command.AggregateRoot.class).map([CtLambdaImpl]([CtParameterImpl] map) -> [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]map.get([CtLiteralImpl]"type")))).filter([CtLambdaImpl]([CtParameterImpl] i) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]i.length() > [CtLiteralImpl]0).orElse([CtInvocationImpl][CtVariableReadImpl]type.getSimpleName());
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void inspectFieldsAndMethods() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ServiceLoader<[CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.ChildEntityDefinition> childEntityDefinitions = [CtInvocationImpl][CtTypeAccessImpl]java.util.ServiceLoader.load([CtFieldReadImpl]org.axonframework.modelling.command.inspection.ChildEntityDefinition.class, [CtInvocationImpl][CtFieldReadImpl]inspectedType.getClassLoader());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Member> entityIdMembers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Member> persistenceIdMembers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.reflect.Member> aggregateVersionMembers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]handlerInspector.getAllHandlers().keySet()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Field field : [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.ReflectionUtils.fieldsOf([CtVariableReadImpl]type)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]childEntityDefinitions.forEach([CtLambdaImpl]([CtParameterImpl] def) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]def.createChildDefinition([CtVariableReadImpl]field, [CtThisAccessImpl]this).ifPresent([CtLambdaImpl]([CtParameterImpl] child) -> [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]children.add([CtVariableReadImpl]child);
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]child.commandHandlers().forEach([CtLambdaImpl]([CtParameterImpl] handler) -> [CtInvocationImpl]addHandler([CtFieldReadImpl][CtFieldReferenceImpl]allCommandHandlers, [CtVariableReadImpl]type, [CtVariableReadImpl]handler));
                    }));
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]field, [CtFieldReadImpl]org.axonframework.modelling.command.EntityId.class).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtInvocationImpl][CtVariableReadImpl]entityIdMembers.add([CtVariableReadImpl]field));
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]field, [CtFieldReadImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel.JAVAX_PERSISTENCE_ID).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtInvocationImpl][CtVariableReadImpl]persistenceIdMembers.add([CtVariableReadImpl]field));
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]field, [CtFieldReadImpl]org.axonframework.modelling.command.AggregateVersion.class).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtInvocationImpl][CtVariableReadImpl]aggregateVersionMembers.add([CtVariableReadImpl]field));
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method method : [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.ReflectionUtils.methodsOf([CtVariableReadImpl]type)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]childEntityDefinitions.forEach([CtLambdaImpl]([CtParameterImpl] def) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]def.createChildDefinition([CtVariableReadImpl]method, [CtThisAccessImpl]this).ifPresent([CtLambdaImpl]([CtParameterImpl] child) -> [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]children.add([CtVariableReadImpl]child);
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]child.commandHandlers().forEach([CtLambdaImpl]([CtParameterImpl] handler) -> [CtInvocationImpl]addHandler([CtFieldReadImpl][CtFieldReferenceImpl]allCommandHandlers, [CtVariableReadImpl]type, [CtVariableReadImpl]handler));
                    }));
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]method, [CtFieldReadImpl]org.axonframework.modelling.command.EntityId.class).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtBlockImpl]{
                        [CtInvocationImpl]assertValidValueProvidingMethod([CtVariableReadImpl]method);
                        [CtInvocationImpl][CtVariableReadImpl]entityIdMembers.add([CtVariableReadImpl]method);
                    });
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]method, [CtFieldReadImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel.JAVAX_PERSISTENCE_ID).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtBlockImpl]{
                        [CtInvocationImpl]assertValidValueProvidingMethod([CtVariableReadImpl]method);
                        [CtInvocationImpl][CtVariableReadImpl]persistenceIdMembers.add([CtVariableReadImpl]method);
                    });
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.findAnnotationAttributes([CtVariableReadImpl]method, [CtFieldReadImpl]org.axonframework.modelling.command.AggregateVersion.class).ifPresent([CtLambdaImpl]([CtParameterImpl] attributes) -> [CtBlockImpl]{
                        [CtInvocationImpl]assertValidValueProvidingMethod([CtVariableReadImpl]method);
                        [CtInvocationImpl][CtVariableReadImpl]aggregateVersionMembers.add([CtVariableReadImpl]method);
                    });
                }
            }
            [CtInvocationImpl][CtInvocationImpl]findIdentifierMember([CtInvocationImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel.distinct([CtVariableReadImpl]entityIdMembers), [CtInvocationImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel.distinct([CtVariableReadImpl]persistenceIdMembers)).ifPresent([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::setIdentifierAndRoutingKey);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]aggregateVersionMembers.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl]setVersionMember([CtInvocationImpl][CtVariableReadImpl]aggregateVersionMembers.get([CtLiteralImpl]0));
            }
            [CtInvocationImpl]assertIdentifierValidity([CtFieldReadImpl]identifierMember);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void setIdentifierAndRoutingKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Member identifier) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]identifierMember = [CtVariableReadImpl]identifier;
            [CtAssignmentImpl][CtFieldWriteImpl]routingKey = [CtInvocationImpl][CtInvocationImpl]findRoutingKey([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.reflect.AccessibleObject) (identifier))).orElseGet([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]identifier::getName);
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.reflect.Member> findIdentifierMember([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Member> entityIdMembers, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.reflect.Member> persistenceIdMembers) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entityIdMembers.size() > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregate [%s] has more than one identifier member", [CtFieldReadImpl]inspectedType));
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]entityIdMembers.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]entityIdMembers.get([CtLiteralImpl]0));
            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]persistenceIdMembers.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]persistenceIdMembers.get([CtLiteralImpl]0));
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void assertValidValueProvidingMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Method method) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]method.getParameterCount() != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregate [%s] has an annotated method [%s] with parameters", [CtFieldReadImpl]inspectedType, [CtVariableReadImpl]method));
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]method.getReturnType() == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Void.[CtFieldReferenceImpl]TYPE) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregate [%s] has an annotated method [%s] with void return type, but a return value is required", [CtFieldReadImpl]inspectedType, [CtVariableReadImpl]method));
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void assertIdentifierValidity([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Member identifier) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]identifier != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> idClazz = [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.ReflectionUtils.getMemberValueType([CtVariableReadImpl]identifier);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.IdentifierValidator.getInstance().isValidIdentifier([CtVariableReadImpl]idClazz)) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregate identifier type [%s] should override Object.toString()", [CtInvocationImpl][CtVariableReadImpl]idClazz.getName()));
                }
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void setVersionMember([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.Member member) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]versionMember != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]member.equals([CtFieldReadImpl]versionMember))) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AggregateModellingException([CtInvocationImpl]java.lang.String.format([CtLiteralImpl]"Aggregate [%s] has two version fields [%s] and [%s].", [CtFieldReadImpl]inspectedType, [CtFieldReadImpl]versionMember, [CtVariableReadImpl]member));
            }
            [CtAssignmentImpl][CtFieldWriteImpl]versionMember = [CtVariableReadImpl]member;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> findRoutingKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.reflect.AccessibleObject accessibleObject) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.annotation.AnnotationUtils.<[CtTypeReferenceImpl]java.lang.String>findAnnotationAttribute([CtVariableReadImpl]accessibleObject, [CtFieldReadImpl]org.axonframework.modelling.command.EntityId.class, [CtLiteralImpl]"routingKey").filter([CtLambdaImpl]([CtParameterImpl] key) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]key));
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        private [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<[CtTypeParameterReferenceImpl]T> runtimeModelOf([CtParameterImpl][CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]modelOf([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T>) ([CtVariableReadImpl]target.getClass())));
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allCommandHandlers() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtFieldReadImpl]allCommandHandlers);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> commandHandlers([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> subtype) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]handlers([CtFieldReadImpl]allCommandHandlers, [CtVariableReadImpl]subtype);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> types() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]handlerInspector.getAllHandlers().keySet().stream();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public <[CtTypeParameterImpl]C> [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<[CtTypeParameterReferenceImpl]C> modelOf([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]C> childEntityType) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// using empty list subtypes because this model is already in the registry, so it doesn't matter
            return [CtInvocationImpl][CtThisAccessImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.this.createModel([CtVariableReadImpl]childEntityType, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptySet());
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> entityClass() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]inspectedType;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void publish([CtParameterImpl][CtTypeReferenceImpl]org.axonframework.eventhandling.EventMessage<[CtWildcardReferenceImpl]?> message, [CtParameterImpl][CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]runtimeModelOf([CtVariableReadImpl]target).doPublish([CtVariableReadImpl]message, [CtVariableReadImpl]target);
            }
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void doPublish([CtParameterImpl][CtTypeReferenceImpl]org.axonframework.eventhandling.EventMessage<[CtWildcardReferenceImpl]?> message, [CtParameterImpl][CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]getHandler([CtVariableReadImpl]message, [CtInvocationImpl][CtVariableReadImpl]target.getClass()).ifPresent([CtLambdaImpl]([CtParameterImpl] h) -> [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]h.handle([CtVariableReadImpl]message, [CtVariableReadImpl]target);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new <org.axonframework.modelling.command.inspection.e>[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlerInvocationException([CtInvocationImpl]format([CtLiteralImpl]"Error handling event of type [%s] in aggregate", [CtInvocationImpl][CtVariableReadImpl]message.getPayloadType()));
                }
            });
            [CtInvocationImpl][CtFieldReadImpl]children.forEach([CtLambdaImpl]([CtParameterImpl] i) -> [CtInvocationImpl][CtVariableReadImpl]i.publish([CtVariableReadImpl]message, [CtVariableReadImpl]target));
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String type() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]declaredTypes.get([CtFieldReadImpl]inspectedType);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>> type([CtParameterImpl][CtTypeReferenceImpl]java.lang.String declaredType) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]types.getOrDefault([CtVariableReadImpl]declaredType, [CtLiteralImpl]null));
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> declaredType([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]declaredTypes.getOrDefault([CtVariableReadImpl]type, [CtLiteralImpl]null));
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.Long getVersion([CtParameterImpl][CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]versionMember != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.ReflectionUtils.<[CtTypeReferenceImpl]java.lang.Long>getMemberValue([CtFieldReadImpl]versionMember, [CtVariableReadImpl]target);
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allCommandHandlerInterceptors() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtFieldReadImpl]allCommandHandlerInterceptors);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> commandHandlerInterceptors([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> subtype) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]handlers([CtFieldReadImpl]allCommandHandlerInterceptors, [CtVariableReadImpl]subtype);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the {@link MessageHandlingMember} that is capable of handling the given {@code message}. If no member
         * is found an empty optional is returned.
         *
         * @param message
         * 		the message to find a handler for
         * @param targetClass
         * 		the target class that handler should be executed on
         * @return the handler of the message if present on the model
         */
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
        protected [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> getHandler([CtParameterImpl][CtTypeReferenceImpl]org.axonframework.messaging.Message<[CtWildcardReferenceImpl]?> message, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> targetClass) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]handlers([CtFieldReadImpl]allEventHandlers, [CtVariableReadImpl]targetClass).filter([CtLambdaImpl]([CtParameterImpl] handler) -> [CtInvocationImpl][CtVariableReadImpl]handler.canHandle([CtVariableReadImpl]message)).findAny();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> allEventHandlers() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtFieldReadImpl]allEventHandlers);
        }

        [CtMethodImpl][CtCommentImpl]// backwards compatibility - if you don't specify a designated child,
        [CtCommentImpl]// you should at least get handlers of its first registered parent (if any)
        private [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>> handlers([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.axonframework.messaging.annotation.MessageHandlingMember<[CtWildcardReferenceImpl]? super [CtTypeParameterReferenceImpl]T>>> handlers, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> subtype) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> type = [CtVariableReadImpl]subtype;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]handlers.containsKey([CtVariableReadImpl]type)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]type.equals([CtFieldReadImpl]java.lang.Object.class))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]type = [CtInvocationImpl][CtVariableReadImpl]type.getSuperclass();
            } 
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handlers.getOrDefault([CtVariableReadImpl]type, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>()).stream();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.Object getIdentifier([CtParameterImpl][CtTypeParameterReferenceImpl]T target) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]identifierMember == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.axonframework.common.ReflectionUtils.getMemberValue([CtFieldReadImpl]identifierMember, [CtVariableReadImpl]target);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String routingKey() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]routingKey;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns this instance when it it safe to read from. This is either if the current thread is already
         * initializing this instance, or when the instance is fully initialized.
         *
         * @return this instance, as soon as it is safe for reading
         */
        private [CtTypeReferenceImpl]org.axonframework.modelling.command.inspection.AnnotatedAggregateMetaModelFactory.AnnotatedAggregateModel<[CtTypeParameterReferenceImpl]T> whenReadSafe() [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtFieldReadImpl]initializing.get())) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// This thread is initializing. To prevent deadlocks, we should return this instance immediately
                return [CtThisAccessImpl]this;
            }
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtFieldReadImpl]initialized) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// another thread is initializing, it shouldn't take long...
                [CtTypeAccessImpl]java.lang.Thread.yield();
            } 
            [CtReturnImpl][CtCommentImpl]// we're safe to go
            return [CtThisAccessImpl]this;
        }
    }
}
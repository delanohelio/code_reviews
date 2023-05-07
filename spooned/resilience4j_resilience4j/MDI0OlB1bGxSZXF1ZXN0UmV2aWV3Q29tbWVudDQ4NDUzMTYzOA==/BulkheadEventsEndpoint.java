[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 lespinsideg

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
[CtPackageDeclarationImpl]package io.github.resilience4j.bulkhead.monitoring.endpoint;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
[CtUnresolvedImport]import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
[CtUnresolvedImport]import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
[CtUnresolvedImport]import io.github.resilience4j.bulkhead.event.BulkheadEvent;
[CtUnresolvedImport]import io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse;
[CtImportImpl]import java.util.Comparator;
[CtUnresolvedImport]import io.github.resilience4j.consumer.CircularEventConsumer;
[CtUnresolvedImport]import io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventDTOFactory;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventDTO;
[CtUnresolvedImport]import io.github.resilience4j.consumer.EventConsumerRegistry;
[CtUnresolvedImport]import org.springframework.boot.actuate.endpoint.annotation.Selector;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.Endpoint(id = [CtLiteralImpl]"bulkheadevents")
public class BulkheadEventsEndpoint {
    [CtFieldImpl]private final [CtTypeReferenceImpl]io.github.resilience4j.consumer.EventConsumerRegistry<[CtTypeReferenceImpl]io.github.resilience4j.bulkhead.event.BulkheadEvent> eventConsumerRegistry;

    [CtConstructorImpl]public BulkheadEventsEndpoint([CtParameterImpl][CtTypeReferenceImpl]io.github.resilience4j.consumer.EventConsumerRegistry<[CtTypeReferenceImpl]io.github.resilience4j.bulkhead.event.BulkheadEvent> eventConsumerRegistry) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.eventConsumerRegistry = [CtVariableReadImpl]eventConsumerRegistry;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.ReadOperation
    public [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse getAllBulkheadEvents() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventDTO> response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]eventConsumerRegistry.getAllEventConsumer().stream().flatMap([CtLambdaImpl]([CtParameterImpl] bulkheadEventCircularEventConsumer) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bulkheadEventCircularEventConsumer.getBufferedEvents().stream()).sorted([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]BulkheadEvent::getCreationTime)).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]BulkheadEventDTOFactory::createBulkheadEventDTO).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toUnmodifiableList());
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse([CtVariableReadImpl]response);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.ReadOperation
    public [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse getEventsFilteredByBulkheadName([CtParameterImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.Selector
    [CtTypeReferenceImpl]java.lang.String bulkheadName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventDTO> response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getBulkheadEvent([CtVariableReadImpl]bulkheadName).stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]BulkheadEventDTOFactory::createBulkheadEventDTO).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse([CtVariableReadImpl]response);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.ReadOperation
    public [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse getEventsFilteredByBulkheadNameAndEventType([CtParameterImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.Selector
    [CtTypeReferenceImpl]java.lang.String bulkheadName, [CtParameterImpl][CtAnnotationImpl]@org.springframework.boot.actuate.endpoint.annotation.Selector
    [CtTypeReferenceImpl]java.lang.String eventType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventDTO> response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getBulkheadEvent([CtVariableReadImpl]bulkheadName).stream().filter([CtLambdaImpl]([CtParameterImpl] event) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getEventType() == [CtInvocationImpl][CtVariableReadImpl]BulkheadEvent.Type.valueOf([CtInvocationImpl][CtVariableReadImpl]eventType.toUpperCase())).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]BulkheadEventDTOFactory::createBulkheadEventDTO).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.common.bulkhead.monitoring.endpoint.BulkheadEventsEndpointResponse([CtVariableReadImpl]response);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.github.resilience4j.bulkhead.event.BulkheadEvent> getBulkheadEvent([CtParameterImpl][CtTypeReferenceImpl]java.lang.String bulkheadName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.consumer.CircularEventConsumer<[CtTypeReferenceImpl]io.github.resilience4j.bulkhead.event.BulkheadEvent> eventConsumer = [CtInvocationImpl][CtFieldReadImpl]eventConsumerRegistry.getEventConsumer([CtVariableReadImpl]bulkheadName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]eventConsumer == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.consumer.CircularEventConsumer<[CtTypeReferenceImpl]io.github.resilience4j.bulkhead.event.BulkheadEvent> threadPoolEventConsumer = [CtInvocationImpl][CtFieldReadImpl]eventConsumerRegistry.getEventConsumer([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]"-", [CtInvocationImpl][CtFieldReadImpl]io.github.resilience4j.bulkhead.ThreadPoolBulkhead.class.getSimpleName(), [CtVariableReadImpl]bulkheadName));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]threadPoolEventConsumer != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]threadPoolEventConsumer.getBufferedEvents().stream().filter([CtLambdaImpl]([CtParameterImpl] event) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getBulkheadName().equals([CtVariableReadImpl]bulkheadName)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
            }
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]eventConsumer.getBufferedEvents().stream().filter([CtLambdaImpl]([CtParameterImpl] event) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getBulkheadName().equals([CtVariableReadImpl]bulkheadName)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        }
    }
}
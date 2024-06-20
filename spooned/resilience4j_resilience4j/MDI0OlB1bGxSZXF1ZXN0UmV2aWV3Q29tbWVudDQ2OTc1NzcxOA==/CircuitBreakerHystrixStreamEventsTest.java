[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Vijay Ram

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
[CtPackageDeclarationImpl]package io.github.resilience4j.circuitbreaker.monitoring.events;
[CtUnresolvedImport]import io.github.resilience4j.service.test.TestApplication;
[CtUnresolvedImport]import org.springframework.core.ParameterizedTypeReference;
[CtUnresolvedImport]import io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse;
[CtUnresolvedImport]import org.springframework.http.MediaType;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.springframework.web.reactive.function.client.WebClient;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import io.github.resilience4j.service.test.DummyService;
[CtUnresolvedImport]import org.springframework.boot.web.server.LocalServerPort;
[CtUnresolvedImport]import org.springframework.test.web.reactive.server.WebTestClient;
[CtUnresolvedImport]import reactor.core.publisher.Flux;
[CtUnresolvedImport]import org.springframework.http.codec.ServerSentEvent;
[CtUnresolvedImport]import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
[CtUnresolvedImport]import org.springframework.boot.test.context.SpringBootTest;
[CtImportImpl]import java.util.List;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author vijayram
 */
[CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
[CtAnnotationImpl]@org.springframework.boot.test.context.SpringBootTest(webEnvironment = [CtFieldReadImpl]SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CtFieldReadImpl]io.github.resilience4j.service.test.TestApplication.class)
public class CircuitBreakerHystrixStreamEventsTest {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ACTUATOR_STREAM_CIRCUITBREAKER_EVENTS = [CtLiteralImpl]"/actuator/hystrix-stream-circuitbreaker-events";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ACTUATOR_CIRCUITBREAKEREVENTS = [CtLiteralImpl]"/actuator/circuitbreakerevents";

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]org.springframework.test.web.reactive.server.WebTestClient webTestClient;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.boot.web.server.LocalServerPort
    [CtTypeReferenceImpl]int randomServerPort;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]io.github.resilience4j.service.test.DummyService dummyService;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void streamAllEvents() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> events = [CtInvocationImpl]getServerSentEvents([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_STREAM_CIRCUITBREAKER_EVENTS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsBefore = [CtInvocationImpl]circuitBreakerEvents([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ex) [CtBlockImpl]{
            [CtCommentImpl]// Do nothing. The IOException is recorded by the CircuitBreaker as part of the recordFailurePredicate as a failure.
        }
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// to record the event

        [CtInvocationImpl][CtCommentImpl]// The invocation is recorded by the CircuitBreaker as a success.
        [CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsAfter = [CtInvocationImpl]circuitBreakerEvents([CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS);
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsBefore.getCircuitBreakerEvents().size() < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsAfter.getCircuitBreakerEvents().size();
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// for webClient to complete the subscribe operation

        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]events.size() == [CtLiteralImpl]2;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void streamEventsbyName() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> events = [CtInvocationImpl]getServerSentEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_STREAM_CIRCUITBREAKER_EVENTS + [CtLiteralImpl]"/backendA");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsBefore = [CtInvocationImpl]circuitBreakerEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS + [CtLiteralImpl]"/backendA");
        [CtInvocationImpl][CtCommentImpl]// The invocation is recorded by the CircuitBreaker as a success.
        [CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// sleep is needed to record the event

        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ex) [CtBlockImpl]{
            [CtCommentImpl]// Do nothing. The IOException is recorded by the CircuitBreaker as part of the recordFailurePredicate as a failure.
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsAfter = [CtInvocationImpl]circuitBreakerEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS + [CtLiteralImpl]"/backendA");
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// webClient to complete the subscribe operation

        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsBefore.getCircuitBreakerEvents().size() < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsAfter.getCircuitBreakerEvents().size();
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]events.size() == [CtLiteralImpl]2;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void streamEventsbyNameAndType() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> events = [CtInvocationImpl]getServerSentEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_STREAM_CIRCUITBREAKER_EVENTS + [CtLiteralImpl]"/backendA/ERROR");
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsBefore = [CtInvocationImpl]circuitBreakerEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS + [CtLiteralImpl]"/backendA");
        [CtInvocationImpl][CtCommentImpl]// The invocation is recorded by the CircuitBreaker as a success.
        [CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// to record the event

        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]dummyService.doSomething([CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ex) [CtBlockImpl]{
            [CtCommentImpl]// Do nothing. The IOException is recorded by the CircuitBreaker as part of the recordFailurePredicate as a failure.
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEventsAfter = [CtInvocationImpl]circuitBreakerEvents([CtBinaryOperatorImpl][CtFieldReadImpl]io.github.resilience4j.circuitbreaker.monitoring.events.CircuitBreakerHystrixStreamEventsTest.ACTUATOR_CIRCUITBREAKEREVENTS + [CtLiteralImpl]"/backendA");
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);[CtCommentImpl]// for webClient to complete the subscribe operation

        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsBefore.getCircuitBreakerEvents().size() < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]circuitBreakerEventsAfter.getCircuitBreakerEvents().size();
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]events.size() == [CtLiteralImpl]1;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> getServerSentEvents([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> circuitBreakerStreamEventsForAfter = [CtInvocationImpl]circuitBreakerStreamEvents([CtVariableReadImpl]s);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> events = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]circuitBreakerStreamEventsForAfter.subscribe([CtLambdaImpl]([CtParameterImpl] content) -> [CtInvocationImpl][CtVariableReadImpl]events.add([CtVariableReadImpl]content), [CtLambdaImpl]([CtParameterImpl] error) -> [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Error receiving SSE: {}" + [CtVariableReadImpl]error), [CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtLiteralImpl]"Completed!!!"));
        [CtReturnImpl]return [CtVariableReadImpl]events;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse circuitBreakerEvents([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.webTestClient.get().uri([CtVariableReadImpl]s).exchange().expectStatus().isOk().expectBody([CtFieldReadImpl]io.github.resilience4j.common.circuitbreaker.monitoring.endpoint.CircuitBreakerEventsEndpointResponse.class).returnResult().getResponseBody();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> circuitBreakerStreamEvents([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.web.reactive.function.client.WebClient client = [CtInvocationImpl][CtTypeAccessImpl]org.springframework.web.reactive.function.client.WebClient.create([CtBinaryOperatorImpl][CtLiteralImpl]"http://localhost:" + [CtFieldReadImpl]randomServerPort);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> type = [CtNewClassImpl]new [CtTypeReferenceImpl]org.springframework.core.ParameterizedTypeReference<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>>()[CtClassImpl] {};
        [CtLocalVariableImpl][CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]org.springframework.http.codec.ServerSentEvent<[CtTypeReferenceImpl]java.lang.String>> eventStream = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.get().uri([CtVariableReadImpl]s).accept([CtTypeAccessImpl]MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux([CtVariableReadImpl]type).take([CtLiteralImpl]2);
        [CtReturnImpl]return [CtVariableReadImpl]eventStream;
    }
}
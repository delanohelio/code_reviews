[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020, OpenTelemetry Authors

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
[CtPackageDeclarationImpl]package io.opentelemetry.exporters.jaeger;
[CtUnresolvedImport]import io.opentelemetry.trace.Span;
[CtUnresolvedImport]import io.grpc.ManagedChannel;
[CtUnresolvedImport]import io.opentelemetry.sdk.OpenTelemetrySdk;
[CtUnresolvedImport]import io.restassured.http.ContentType;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.junit.runners.JUnit4;
[CtImportImpl]import java.util.concurrent.Callable;
[CtUnresolvedImport]import io.grpc.ManagedChannelBuilder;
[CtUnresolvedImport]import io.restassured.response.Response;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import org.testcontainers.containers.GenericContainer;
[CtUnresolvedImport]import io.opentelemetry.OpenTelemetry;
[CtUnresolvedImport]import io.opentelemetry.sdk.trace.export.SimpleSpansProcessor;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.junit.ClassRule;
[CtUnresolvedImport]import static io.restassured.RestAssured.given;
[CtUnresolvedImport]import org.awaitility.Awaitility;
[CtUnresolvedImport]import io.opentelemetry.trace.Tracer;
[CtUnresolvedImport]import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.JUnit4.class)
public class JaegerIntegrationTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int QUERY_PORT = [CtLiteralImpl]16686;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int COLLECTOR_PORT = [CtLiteralImpl]14250;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JAEGER_VERSION = [CtLiteralImpl]"1.17";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SERVICE_NAME = [CtLiteralImpl]"E2E-test";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String JAEGER_IP = [CtLiteralImpl]"http://localhost";

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.opentelemetry.trace.Tracer tracer = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.OpenTelemetry.getTracerProvider().get([CtLiteralImpl]"io.opentelemetry.exporters.jaeger.JaegerIntegrationTest");

    [CtFieldImpl]private [CtTypeReferenceImpl]io.opentelemetry.exporters.jaeger.JaegerGrpcSpanExporter jaegerExporter;

    [CtMethodImpl]private [CtTypeReferenceImpl]void setupJaegerExporter() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.grpc.ManagedChannel jaegerChannel = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.grpc.ManagedChannelBuilder.forAddress([CtLiteralImpl]"127.0.0.1", [CtInvocationImpl][CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.jaeger.getMappedPort([CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.COLLECTOR_PORT)).usePlaintext().build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jaegerExporter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.exporters.jaeger.JaegerGrpcSpanExporter.newBuilder().setServiceName([CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.SERVICE_NAME).setChannel([CtVariableReadImpl]jaegerChannel).setDeadlineMs([CtLiteralImpl]30000).build();
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.sdk.OpenTelemetrySdk.getTracerProvider().addSpanProcessor([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.sdk.trace.export.SimpleSpansProcessor.newBuilder([CtFieldReadImpl][CtThisAccessImpl]this.jaegerExporter).build());
    }

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"rawtypes")
    [CtAnnotationImpl]@org.junit.ClassRule
    public static [CtTypeReferenceImpl]org.testcontainers.containers.GenericContainer jaeger = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.testcontainers.containers.GenericContainer([CtBinaryOperatorImpl][CtLiteralImpl]"jaegertracing/all-in-one:" + [CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.JAEGER_VERSION).withExposedPorts([CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.COLLECTOR_PORT, [CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.QUERY_PORT).waitingFor([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.testcontainers.containers.wait.strategy.HttpWaitStrategy().forPath([CtLiteralImpl]"/"));

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testJaegerIntegration() [CtBlockImpl]{
        [CtInvocationImpl]setupJaegerExporter();
        [CtInvocationImpl]imitateWork();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.awaitility.Awaitility.await().atMost([CtLiteralImpl]30, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS).until([CtInvocationImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.assertJaegerHaveTrace());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void imitateWork() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.opentelemetry.trace.Span span = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tracer.spanBuilder([CtLiteralImpl]"Test span").startSpan();
        [CtInvocationImpl][CtVariableReadImpl]span.addEvent([CtLiteralImpl]"some event");
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]1000);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtVariableReadImpl]span.end();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean> assertJaegerHaveTrace() [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.concurrent.Callable<[CtTypeReferenceImpl]java.lang.Boolean>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Boolean call() [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s/api/traces?service=%s", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.JAEGER_IP + [CtLiteralImpl]":%d", [CtInvocationImpl][CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.jaeger.getMappedPort([CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.QUERY_PORT)), [CtFieldReadImpl]io.opentelemetry.exporters.jaeger.JaegerIntegrationTest.SERVICE_NAME);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]io.restassured.response.Response response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]RestAssured.given().headers([CtLiteralImpl]"Content-Type", [CtTypeAccessImpl]ContentType.JSON, [CtLiteralImpl]"Accept", [CtTypeAccessImpl]ContentType.JSON).when().get([CtVariableReadImpl]url).then().contentType([CtTypeAccessImpl]ContentType.JSON).extract().response();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> path = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.jsonPath().getMap([CtLiteralImpl]"data[0]");
                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]path.get([CtLiteralImpl]"traceID") != [CtLiteralImpl]null;
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
        };
    }
}
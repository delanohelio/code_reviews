[CompilationUnitImpl][CtCommentImpl]/* Copyright The OpenTelemetry Authors
SPDX-License-Identifier: Apache-2.0
 */
[CtPackageDeclarationImpl]package io.opentelemetry.javaagent.instrumentation.lettuce.v5_1;
[CtUnresolvedImport]import io.opentelemetry.instrumentation.api.tracer.utils.NetPeerUtils;
[CtUnresolvedImport]import io.opentelemetry.context.Context;
[CtUnresolvedImport]import io.opentelemetry.trace.Span;
[CtImportImpl]import java.time.Instant;
[CtUnresolvedImport]import io.lettuce.core.tracing.TracerProvider;
[CtImportImpl]import java.net.InetSocketAddress;
[CtUnresolvedImport]import io.opentelemetry.trace.Span.Kind;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import io.opentelemetry.javaagent.instrumentation.api.db.DbSystem;
[CtUnresolvedImport]import static io.opentelemetry.javaagent.instrumentation.lettuce.LettuceArgSplitter.splitArgs;
[CtUnresolvedImport]import io.opentelemetry.trace.attributes.SemanticAttributes;
[CtUnresolvedImport]import io.lettuce.core.tracing.Tracer;
[CtUnresolvedImport]import io.opentelemetry.instrumentation.api.tracer.utils.NetPeerUtils.SpanAttributeSetter;
[CtUnresolvedImport]import io.opentelemetry.trace.StatusCode;
[CtUnresolvedImport]import io.lettuce.core.tracing.TraceContext;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import io.opentelemetry.OpenTelemetry;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import io.lettuce.core.tracing.Tracing;
[CtUnresolvedImport]import org.checkerframework.checker.nullness.qual.Nullable;
[CtUnresolvedImport]import io.lettuce.core.tracing.TraceContextProvider;
[CtUnresolvedImport]import io.opentelemetry.javaagent.instrumentation.api.db.RedisCommandNormalizer;
[CtImportImpl]import java.net.SocketAddress;
[CtEnumImpl]public enum OpenTelemetryTracing implements [CtTypeReferenceImpl]io.lettuce.core.tracing.Tracing {

    [CtEnumValueImpl]INSTANCE;
    [CtFieldImpl]public static final [CtTypeReferenceImpl]io.lettuce.core.tracing.Tracer TRACER = [CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.OpenTelemetry.getGlobalTracer([CtLiteralImpl]"io.opentelemetry.auto.lettuce-5.1");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.api.db.RedisCommandNormalizer COMMAND_NORMALIZER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.api.db.RedisCommandNormalizer([CtLiteralImpl]"lettuce", [CtLiteralImpl]"lettuce-5", [CtLiteralImpl]"lettuce-5.1");

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.lettuce.core.tracing.TracerProvider getTracerProvider() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTracerProvider.[CtFieldReferenceImpl]INSTANCE;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.lettuce.core.tracing.TraceContextProvider initialTraceContextProvider() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTraceContextProvider.[CtFieldReferenceImpl]INSTANCE;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtCommentImpl]// Added in lettuce 5.2
    [CtCommentImpl]// @Override
    public [CtTypeReferenceImpl]boolean includeCommandArgsInSpanTags() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.Endpoint createEndpoint([CtParameterImpl][CtTypeReferenceImpl]java.net.SocketAddress socketAddress) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]socketAddress instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.net.InetSocketAddress) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.InetSocketAddress address = [CtVariableReadImpl](([CtTypeReferenceImpl]java.net.InetSocketAddress) (socketAddress));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ip = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]address.getAddress() == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]address.getAddress().getHostAddress();
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryEndpoint([CtVariableReadImpl]ip, [CtInvocationImpl][CtVariableReadImpl]address.getPort(), [CtInvocationImpl][CtVariableReadImpl]address.getHostString());
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtEnumImpl]private enum OpenTelemetryTracerProvider implements [CtTypeReferenceImpl]io.lettuce.core.tracing.TracerProvider {

        [CtEnumValueImpl]INSTANCE;
        [CtFieldImpl]private final [CtTypeReferenceImpl]io.lettuce.core.tracing.Tracer openTelemetryTracer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTracer();

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]io.lettuce.core.tracing.Tracer getTracer() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]openTelemetryTracer;
        }
    }

    [CtEnumImpl]private enum OpenTelemetryTraceContextProvider implements [CtTypeReferenceImpl]io.lettuce.core.tracing.TraceContextProvider {

        [CtEnumValueImpl]INSTANCE;
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]io.lettuce.core.tracing.TraceContext getTraceContext() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTraceContext();
        }
    }

    [CtClassImpl]private static class OpenTelemetryTraceContext implements [CtTypeReferenceImpl]io.lettuce.core.tracing.TraceContext {
        [CtFieldImpl]private final [CtTypeReferenceImpl]io.opentelemetry.context.Context context;

        [CtConstructorImpl]OpenTelemetryTraceContext() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.context.Context.current();
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]io.opentelemetry.context.Context getSpanContext() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]context;
        }
    }

    [CtClassImpl]private static class OpenTelemetryEndpoint implements [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.Endpoint {
        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        final [CtTypeReferenceImpl]java.lang.String ip;

        [CtFieldImpl]final [CtTypeReferenceImpl]int port;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        final [CtTypeReferenceImpl]java.lang.String name;

        [CtConstructorImpl]OpenTelemetryEndpoint([CtParameterImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        [CtTypeReferenceImpl]java.lang.String ip, [CtParameterImpl][CtTypeReferenceImpl]int port, [CtParameterImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        [CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ip = [CtVariableReadImpl]ip;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.port = [CtVariableReadImpl]port;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        }
    }

    [CtClassImpl]private static class OpenTelemetryTracer extends [CtTypeReferenceImpl]io.lettuce.core.tracing.Tracer {
        [CtConstructorImpl]OpenTelemetryTracer() [CtBlockImpl]{
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan nextSpan() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan([CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.context.Context.current());
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan nextSpan([CtParameterImpl][CtTypeReferenceImpl]io.lettuce.core.tracing.TraceContext traceContext) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]traceContext instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTraceContext)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]nextSpan();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.opentelemetry.context.Context context = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryTraceContext) (traceContext)).getSpanContext();
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan([CtVariableReadImpl]context);
        }
    }

    [CtClassImpl][CtCommentImpl]// The order that callbacks will be called in or which thread they are called from is not well
    [CtCommentImpl]// defined. We go ahead and buffer all data until we know we have a span. This implementation is
    [CtCommentImpl]// particularly safe, synchronizing all accesses. Relying on implementation details would allow
    [CtCommentImpl]// reducing synchronization but the impact should be minimal.
    private static class OpenTelemetrySpan extends [CtTypeReferenceImpl][CtTypeReferenceImpl]io.lettuce.core.tracing.Tracer.Span {
        [CtFieldImpl]private final [CtTypeReferenceImpl]Span.Builder spanBuilder;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private [CtTypeReferenceImpl]java.lang.String name;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> events;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private [CtTypeReferenceImpl]java.lang.Throwable error;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private [CtTypeReferenceImpl]io.opentelemetry.trace.Span span;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private [CtTypeReferenceImpl]java.lang.String args;

        [CtConstructorImpl]OpenTelemetrySpan([CtParameterImpl][CtTypeReferenceImpl]io.opentelemetry.context.Context parent) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Name will be updated later, we create with an arbitrary one here to store other data before
            [CtCommentImpl]// the span starts.
            [CtFieldWriteImpl]spanBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.TRACER.spanBuilder([CtLiteralImpl]"redis").setSpanKind([CtTypeAccessImpl]Kind.CLIENT).setParent([CtVariableReadImpl]parent).setAttribute([CtTypeAccessImpl]SemanticAttributes.DB_SYSTEM, [CtTypeAccessImpl]DbSystem.REDIS);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span name([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.updateName([CtVariableReadImpl]name);
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span remoteEndpoint([CtParameterImpl][CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.Endpoint endpoint) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]endpoint instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryEndpoint) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan.fillEndpoint([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]span::setAttribute, [CtVariableReadImpl](([CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryEndpoint) (endpoint)));
                } else [CtBlockImpl]{
                    [CtInvocationImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetrySpan.fillEndpoint([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]spanBuilder::setAttribute, [CtVariableReadImpl](([CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryEndpoint) (endpoint)));
                }
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span start() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]span = [CtInvocationImpl][CtFieldReadImpl]spanBuilder.startSpan();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]name != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.updateName([CtFieldReadImpl]name);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]events != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]events.size(); [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]span.addEvent([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]events.get([CtVariableReadImpl]i))), [CtInvocationImpl](([CtTypeReferenceImpl]long) ([CtFieldReadImpl]events.get([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1))));
                }
                [CtAssignmentImpl][CtFieldWriteImpl]events = [CtLiteralImpl]null;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]error != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.setStatus([CtTypeAccessImpl]StatusCode.ERROR);
                [CtInvocationImpl][CtFieldReadImpl]span.recordException([CtFieldReadImpl]error);
                [CtAssignmentImpl][CtFieldWriteImpl]error = [CtLiteralImpl]null;
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span annotate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.addEvent([CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]events == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]events = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                }
                [CtInvocationImpl][CtFieldReadImpl]events.add([CtVariableReadImpl]value);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.Instant now = [CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.now();
                [CtInvocationImpl][CtFieldReadImpl]events.add([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS.toNanos([CtInvocationImpl][CtVariableReadImpl]now.getEpochSecond()) + [CtInvocationImpl][CtVariableReadImpl]now.getNano());
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span tag([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]key.equals([CtLiteralImpl]"redis.args")) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]args = [CtVariableReadImpl]value;
                [CtReturnImpl]return [CtThisAccessImpl]this;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.setAttribute([CtVariableReadImpl]key, [CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]spanBuilder.setAttribute([CtVariableReadImpl]key, [CtVariableReadImpl]value);
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]io.opentelemetry.trace.Span error([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable throwable) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]span.recordException([CtVariableReadImpl]throwable);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.error = [CtVariableReadImpl]throwable;
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public synchronized [CtTypeReferenceImpl]void finish() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]span != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]name != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String statement = [CtInvocationImpl][CtFieldReadImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.COMMAND_NORMALIZER.normalize([CtFieldReadImpl]name, [CtInvocationImpl]io.opentelemetry.javaagent.instrumentation.lettuce.LettuceArgSplitter.splitArgs([CtFieldReadImpl]args));
                    [CtInvocationImpl][CtFieldReadImpl]span.setAttribute([CtTypeAccessImpl]SemanticAttributes.DB_STATEMENT, [CtVariableReadImpl]statement);
                }
                [CtInvocationImpl][CtFieldReadImpl]span.end();
            }
        }

        [CtMethodImpl]private static [CtTypeReferenceImpl]void fillEndpoint([CtParameterImpl][CtTypeReferenceImpl]io.opentelemetry.instrumentation.api.tracer.utils.NetPeerUtils.SpanAttributeSetter span, [CtParameterImpl][CtTypeReferenceImpl]io.opentelemetry.javaagent.instrumentation.lettuce.v5_1.OpenTelemetryTracing.OpenTelemetryEndpoint endpoint) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]span.setAttribute([CtTypeAccessImpl]SemanticAttributes.NET_TRANSPORT, [CtLiteralImpl]"IP.TCP");
            [CtInvocationImpl][CtTypeAccessImpl]io.opentelemetry.instrumentation.api.tracer.utils.NetPeerUtils.setNetPeer([CtVariableReadImpl]span, [CtFieldReadImpl][CtVariableReadImpl]endpoint.name, [CtFieldReadImpl][CtVariableReadImpl]endpoint.ip, [CtFieldReadImpl][CtVariableReadImpl]endpoint.port);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder redisUrl = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"redis://").append([CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]endpoint.name != [CtLiteralImpl]null ? [CtFieldReadImpl][CtVariableReadImpl]endpoint.name : [CtFieldReadImpl][CtVariableReadImpl]endpoint.ip);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]endpoint.port > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]redisUrl.append([CtLiteralImpl]":").append([CtFieldReadImpl][CtVariableReadImpl]endpoint.port);
            }
            [CtInvocationImpl][CtVariableReadImpl]span.setAttribute([CtTypeAccessImpl]SemanticAttributes.DB_CONNECTION_STRING, [CtInvocationImpl][CtVariableReadImpl]redisUrl.toString());
        }
    }
}
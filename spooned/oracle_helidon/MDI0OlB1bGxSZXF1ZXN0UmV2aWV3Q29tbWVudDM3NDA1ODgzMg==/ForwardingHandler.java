[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.

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
[CtPackageDeclarationImpl]package io.helidon.webserver;
[CtImportImpl]import javax.net.ssl.SSLEngine;
[CtUnresolvedImport]import io.helidon.common.http.DataChunk;
[CtImportImpl]import java.util.logging.Logger;
[CtUnresolvedImport]import io.netty.buffer.Unpooled;
[CtUnresolvedImport]import io.netty.handler.codec.http.LastHttpContent;
[CtUnresolvedImport]import io.netty.channel.ChannelHandlerContext;
[CtUnresolvedImport]import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
[CtUnresolvedImport]import io.netty.buffer.ByteBuf;
[CtUnresolvedImport]import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
[CtImportImpl]import java.util.Iterator;
[CtImportImpl]import java.util.Queue;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpContent;
[CtUnresolvedImport]import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpRequest;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpMethod;
[CtUnresolvedImport]import io.netty.handler.codec.http.FullHttpResponse;
[CtUnresolvedImport]import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
[CtUnresolvedImport]import io.netty.channel.SimpleChannelInboundHandler;
[CtUnresolvedImport]import io.netty.handler.codec.http.DefaultFullHttpResponse;
[CtUnresolvedImport]import io.netty.channel.ChannelHandler;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpRequestDecoder;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpHeaderNames;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.concurrent.atomic.AtomicLong;
[CtUnresolvedImport]import io.netty.handler.codec.http.HttpUtil;
[CtClassImpl][CtJavaDocImpl]/**
 * ForwardingHandler bridges Netty response and request related APIs to
 * {@link BareRequest} and {@link BareResponse}.
 * <p>
 * For each tcp connection, a single {@link ForwardingHandler} is created.
 */
public class ForwardingHandler extends [CtTypeReferenceImpl]io.netty.channel.SimpleChannelInboundHandler<[CtTypeReferenceImpl]java.lang.Object> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.class.getName());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong REQUEST_ID_GENERATOR = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicLong([CtLiteralImpl]0);

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.helidon.webserver.Routing routing;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.helidon.webserver.NettyWebServer webServer;

    [CtFieldImpl]private final [CtTypeReferenceImpl]javax.net.ssl.SSLEngine sslEngine;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]io.helidon.webserver.ReferenceHoldingQueue<[CtTypeReferenceImpl]io.helidon.common.http.DataChunk>> queues;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequestDecoder httpRequestDecoder;

    [CtFieldImpl][CtCommentImpl]// this field is always accessed by the very same thread; as such, it doesn't need to be
    [CtCommentImpl]// concurrency aware
    private [CtTypeReferenceImpl]io.helidon.webserver.RequestContext requestContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isWebSocketUpgrade = [CtLiteralImpl]false;

    [CtConstructorImpl]ForwardingHandler([CtParameterImpl][CtTypeReferenceImpl]io.helidon.webserver.Routing routing, [CtParameterImpl][CtTypeReferenceImpl]io.helidon.webserver.NettyWebServer webServer, [CtParameterImpl][CtTypeReferenceImpl]javax.net.ssl.SSLEngine sslEngine, [CtParameterImpl][CtTypeReferenceImpl]java.util.Queue<[CtTypeReferenceImpl]io.helidon.webserver.ReferenceHoldingQueue<[CtTypeReferenceImpl]io.helidon.common.http.DataChunk>> queues, [CtParameterImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequestDecoder httpRequestDecoder) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.routing = [CtVariableReadImpl]routing;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.webServer = [CtVariableReadImpl]webServer;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sslEngine = [CtVariableReadImpl]sslEngine;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queues = [CtVariableReadImpl]queues;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.httpRequestDecoder = [CtVariableReadImpl]httpRequestDecoder;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void channelReadComplete([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]ctx.flush();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]requestContext == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// there was no publisher associated with this connection
            [CtCommentImpl]// this happens in case there was no http request made on this connection
            return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.publisher().tryAcquire() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.channel().read();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void channelRead0([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object msg) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.fine([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"[Handler: %s] Received object: %s", [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.identityHashCode([CtThisAccessImpl]this), [CtInvocationImpl][CtVariableReadImpl]msg.getClass()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequest) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.channel().config().setAutoRead([CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequest request = [CtVariableReadImpl](([CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequest) (msg));
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.helidon.webserver.ReferenceHoldingQueue<[CtTypeReferenceImpl]io.helidon.common.http.DataChunk> queue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.ReferenceHoldingQueue<>();
            [CtInvocationImpl][CtFieldReadImpl]queues.add([CtVariableReadImpl]queue);
            [CtAssignmentImpl][CtFieldWriteImpl]requestContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.RequestContext([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.HttpRequestScopedPublisher([CtVariableReadImpl]ctx, [CtVariableReadImpl]queue), [CtVariableReadImpl]request);
            [CtLocalVariableImpl][CtCommentImpl]// the only reason we have the 'ref' here is that the field might get assigned with null
            final [CtTypeReferenceImpl]io.helidon.webserver.HttpRequestScopedPublisher publisherRef = [CtInvocationImpl][CtFieldReadImpl]requestContext.publisher();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long requestId = [CtInvocationImpl][CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.REQUEST_ID_GENERATOR.incrementAndGet();
            [CtLocalVariableImpl][CtCommentImpl]// If a problem with the request URI, return 400 response
            [CtTypeReferenceImpl]io.helidon.webserver.BareRequestImpl bareRequest;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]bareRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.BareRequestImpl([CtVariableReadImpl](([CtTypeReferenceImpl]io.netty.handler.codec.http.HttpRequest) (msg)), [CtInvocationImpl][CtFieldReadImpl]requestContext.publisher(), [CtFieldReadImpl]webServer, [CtVariableReadImpl]ctx, [CtFieldReadImpl]sslEngine, [CtVariableReadImpl]requestId);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                [CtInvocationImpl]io.helidon.webserver.ForwardingHandler.send400BadRequest([CtVariableReadImpl]ctx, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.helidon.webserver.BareResponseImpl bareResponse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.BareResponseImpl([CtVariableReadImpl]ctx, [CtVariableReadImpl]request, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]publisherRef::isCompleted, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread(), [CtVariableReadImpl]requestId);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bareResponse.whenCompleted().thenRun([CtLambdaImpl]() -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.helidon.webserver.RequestContext requestContext = [CtFieldReadImpl][CtThisAccessImpl]this.requestContext;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]requestContext != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]requestContext.responseCompleted([CtLiteralImpl]true);
                }
                [CtIfImpl][CtCommentImpl]// Cleanup for these queues is done in HttpInitializer, but
                [CtCommentImpl]// we try to do it here if possible to reduce memory usage,
                [CtCommentImpl]// especially for keep-alive connections
                if ([CtInvocationImpl][CtVariableReadImpl]queue.release()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]queues.remove([CtVariableReadImpl]queue);
                }
                [CtInvocationImpl][CtVariableReadImpl]publisherRef.drain();
                [CtInvocationImpl][CtCommentImpl]// Enable auto-read only after response has been completed
                [CtCommentImpl]// to avoid a race condition with the next response
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.channel().config().setAutoRead([CtLiteralImpl]true);
            });
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]io.netty.handler.codec.http.HttpUtil.is100ContinueExpected([CtVariableReadImpl]request)) [CtBlockImpl]{
                [CtInvocationImpl]io.helidon.webserver.ForwardingHandler.send100Continue([CtVariableReadImpl]ctx);
            }
            [CtTryImpl][CtCommentImpl]// If a problem during routing, return 400 response
            try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]routing.route([CtVariableReadImpl]bareRequest, [CtVariableReadImpl]bareResponse);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                [CtInvocationImpl]io.helidon.webserver.ForwardingHandler.send400BadRequest([CtVariableReadImpl]ctx, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]// If WebSockets upgrade, re-arrange pipeline and drop HTTP decoder
            if ([CtInvocationImpl][CtVariableReadImpl]bareResponse.isWebSocketUpgrade()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.fine([CtLiteralImpl]"Replacing HttpRequestDecoder by WebSocketServerProtocolHandler");
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.pipeline().replace([CtFieldReadImpl]httpRequestDecoder, [CtLiteralImpl]"webSocketsHandler", [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bareRequest.uri().getPath(), [CtLiteralImpl]null, [CtLiteralImpl]true));
                [CtInvocationImpl]io.helidon.webserver.ForwardingHandler.removeHandshakeHandler([CtVariableReadImpl]ctx);[CtCommentImpl]// already done by Tyrus

                [CtAssignmentImpl][CtFieldWriteImpl]isWebSocketUpgrade = [CtLiteralImpl]true;
                [CtReturnImpl]return;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpContent) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]requestContext == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"There is no request context associated with this http content. " + [CtLiteralImpl]"This is never expected to happen!");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpContent httpContent = [CtVariableReadImpl](([CtTypeReferenceImpl]io.netty.handler.codec.http.HttpContent) (msg));
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf content = [CtInvocationImpl][CtVariableReadImpl]httpContent.content();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]content.isReadable()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.HttpMethod method = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.request().method();
                [CtIfImpl][CtCommentImpl]// compliance with RFC 7231
                if ([CtInvocationImpl][CtTypeAccessImpl]HttpMethod.TRACE.equals([CtVariableReadImpl]method)) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// regarding the TRACE method, we're failing when payload is present only when the payload is actually
                    [CtCommentImpl]// consumed; if not, the request might proceed when payload is small enough
                    [CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.finer([CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtLiteralImpl]"Closing connection because of an illegal payload; method: " + [CtVariableReadImpl]method);
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.helidon.webserver.BadRequestException([CtBinaryOperatorImpl][CtLiteralImpl]"It is illegal to send a payload with http method: " + [CtVariableReadImpl]method);
                }
                [CtIfImpl][CtCommentImpl]// compliance with RFC 7231
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.responseCompleted() && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.LastHttpContent))) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// payload is not consumed and the response is already sent; we must close the connection
                    [CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.finer([CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtLiteralImpl]"Closing connection because request payload was not consumed; method: " + [CtVariableReadImpl]method);
                    [CtInvocationImpl][CtVariableReadImpl]ctx.close();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.publisher().submit([CtVariableReadImpl]content);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.LastHttpContent) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]isWebSocketUpgrade) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.publisher().complete();
                    [CtAssignmentImpl][CtFieldWriteImpl]requestContext = [CtLiteralImpl]null;[CtCommentImpl]// just to be sure that current http req/res session doesn't interfere with other ones

                }
            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]content.isReadable()) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// this is here to handle the case when the content is not readable but we didn't
                [CtCommentImpl]// exceptionally complete the publisher and close the connection
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"It is not expected to not have readable content.");
            }
        }
        [CtIfImpl][CtCommentImpl]// We receive a raw bytebuf if connection was upgraded to WebSockets
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]msg instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]isWebSocketUpgrade) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Received ByteBuf without upgrading to WebSockets");
            }
            [CtInvocationImpl][CtCommentImpl]// Simply forward raw bytebuf to Tyrus for processing
            [CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.fine([CtBinaryOperatorImpl][CtLiteralImpl]"Received ByteBuf of WebSockets connection" + [CtVariableReadImpl]msg);
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.publisher().submit([CtVariableReadImpl](([CtTypeReferenceImpl]io.netty.buffer.ByteBuf) (msg)));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Find and remove the WebSockets handshake handler. Note that the handler's implementation
     * class is package private, so we look for it by name. Handshake is done in Helidon using
     * Tyrus' code instead of here.
     *
     * @param ctx
     * 		Channel handler context.
     */
    private static [CtTypeReferenceImpl]void removeHandshakeHandler([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandler handshakeHandler = [CtLiteralImpl]null;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]io.netty.channel.ChannelHandler>> it = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.pipeline().iterator(); [CtInvocationImpl][CtVariableReadImpl]it.hasNext();) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandler handler = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]it.next().getValue();
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handler.getClass().getName().endsWith([CtLiteralImpl]"WebSocketServerProtocolHandshakeHandler")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]handshakeHandler = [CtVariableReadImpl]handler;
                [CtBreakImpl]break;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]handshakeHandler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.pipeline().remove([CtVariableReadImpl]handshakeHandler);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]io.helidon.webserver.ForwardingHandler.LOGGER.warning([CtLiteralImpl]"Unable to remove WebSockets handshake handler from pipeline");
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void send100Continue([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.FullHttpResponse response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.handler.codec.http.DefaultFullHttpResponse([CtFieldReadImpl]HTTP_1_1, [CtFieldReadImpl]CONTINUE);
        [CtInvocationImpl][CtVariableReadImpl]ctx.write([CtVariableReadImpl]response);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a 400 (Bad Request) response with a message as content.
     *
     * @param ctx
     * 		Channel context.
     * @param message
     * 		The message.
     */
    private static [CtTypeReferenceImpl]void send400BadRequest([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] entity = [CtInvocationImpl][CtVariableReadImpl]message.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.netty.handler.codec.http.FullHttpResponse response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.handler.codec.http.DefaultFullHttpResponse([CtFieldReadImpl]HTTP_1_1, [CtFieldReadImpl]BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]io.netty.buffer.Unpooled.wrappedBuffer([CtVariableReadImpl]entity));
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.headers().add([CtTypeAccessImpl]HttpHeaderNames.CONTENT_TYPE, [CtLiteralImpl]"text/plain");
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.headers().add([CtTypeAccessImpl]HttpHeaderNames.CONTENT_LENGTH, [CtFieldReadImpl][CtVariableReadImpl]entity.length);
        [CtInvocationImpl][CtVariableReadImpl]ctx.write([CtVariableReadImpl]response);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void exceptionCaught([CtParameterImpl][CtTypeReferenceImpl]io.netty.channel.ChannelHandlerContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable cause) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]requestContext != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]requestContext.publisher().error([CtVariableReadImpl]cause);
        }
        [CtInvocationImpl][CtVariableReadImpl]ctx.close();
    }
}
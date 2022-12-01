[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.tinkerpop.gremlin.server;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.structure.Graph;
[CtImportImpl]import javax.net.ssl.SSLException;
[CtImportImpl]import java.security.NoSuchAlgorithmException;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.driver.ser.GraphBinaryMessageSerializerV1;
[CtImportImpl]import java.security.cert.CertificateException;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.authz.AuthenticatorAuthorizer;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.driver.message.RequestMessage;
[CtUnresolvedImport]import io.netty.handler.ssl.ClientAuth;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.driver.message.ResponseMessage;
[CtImportImpl]import java.security.UnrecoverableKeyException;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.authz.Authorizer;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.auth.Authenticator;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import io.netty.channel.socket.SocketChannel;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.concurrent.ScheduledExecutorService;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.handler.OpSelectorHandler;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.util.ServerGremlinExecutor;
[CtUnresolvedImport]import io.netty.handler.ssl.SslContext;
[CtUnresolvedImport]import io.netty.channel.ChannelInitializer;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.handler.AbstractAuthenticationHandler;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.security.KeyStoreException;
[CtUnresolvedImport]import io.netty.channel.ChannelPipeline;
[CtImportImpl]import java.security.KeyStore;
[CtUnresolvedImport]import org.javatuples.Pair;
[CtImportImpl]import javax.net.ssl.KeyManagerFactory;
[CtImportImpl]import java.io.FileInputStream;
[CtUnresolvedImport]import io.netty.handler.timeout.IdleStateHandler;
[CtUnresolvedImport]import io.netty.handler.ssl.SslProvider;
[CtImportImpl]import javax.net.ssl.TrustManagerFactory;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.server.handler.OpExecutorHandler;
[CtUnresolvedImport]import io.netty.handler.ssl.SslContextBuilder;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV2d0;
[CtClassImpl][CtJavaDocImpl]/**
 * A base implementation for the {@code Channelizer} which does a basic configuration of the pipeline, one that
 * is generally common to virtually any Gremlin Server operation (i.e. where the server's purpose is to process
 * Gremlin scripts).
 * <p/>
 * Implementers need only worry about determining how incoming data is converted to a
 * {@link RequestMessage} and outgoing data is converted from a  {@link ResponseMessage} to whatever expected format is
 * needed by the pipeline.
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public abstract class AbstractChannelizer extends [CtTypeReferenceImpl]io.netty.channel.ChannelInitializer<[CtTypeReferenceImpl]io.netty.channel.socket.SocketChannel> implements [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Channelizer {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.class);

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SerializerSettings> DEFAULT_SERIALIZERS = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SerializerSettings([CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV2d0.class.getName(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap()), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SerializerSettings([CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.driver.ser.GraphBinaryMessageSerializerV1.class.getName(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap()), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SerializerSettings([CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.driver.ser.GraphBinaryMessageSerializerV1.class.getName(), [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>()[CtClassImpl] {
        [CtAnonymousExecutableImpl][CtBlockImpl]{
            [CtInvocationImpl]put([CtTypeAccessImpl]GraphBinaryMessageSerializerV1.TOKEN_SERIALIZE_RESULT_TO_STRING, [CtLiteralImpl]true);
        }
    }));

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings settings;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor gremlinExecutor;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.netty.handler.ssl.SslContext> sslContext;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.GraphManager graphManager;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.concurrent.ExecutorService gremlinExecutorService;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.concurrent.ScheduledExecutorService scheduledExecutorService;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PIPELINE_AUTHENTICATOR = [CtLiteralImpl]"authenticator";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PIPELINE_AUTHORIZER = [CtLiteralImpl]"authorizer";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PIPELINE_REQUEST_HANDLER = [CtLiteralImpl]"request-handler";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PIPELINE_HTTP_RESPONSE_ENCODER = [CtLiteralImpl]"http-response-encoder";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String PIPELINE_SSL = [CtLiteralImpl]"ssl";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String PIPELINE_OP_SELECTOR = [CtLiteralImpl]"op-selector";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String PIPELINE_OP_EXECUTOR = [CtLiteralImpl]"op-executor";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String PIPELINE_HTTP_REQUEST_DECODER = [CtLiteralImpl]"http-request-decoder";

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]java.lang.String GREMLIN_ENDPOINT = [CtLiteralImpl]"/gremlin";

    [CtFieldImpl]protected final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer> serializers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.OpSelectorHandler opSelectorHandler;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.OpExecutorHandler opExecutorHandler;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.auth.Authenticator authenticator;

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.Authorizer authorizer;

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called from within {@link #initChannel(SocketChannel)} just after the SSL handler is put in the pipeline.
     * Modify the pipeline as needed here.
     */
    public abstract [CtTypeReferenceImpl]void configure([CtParameterImpl]final [CtTypeReferenceImpl]io.netty.channel.ChannelPipeline pipeline);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called after the pipeline is completely configured.  It can be overridden to make any
     * final changes to the pipeline before it goes into use.
     */
    public [CtTypeReferenceImpl]void finalize([CtParameterImpl]final [CtTypeReferenceImpl]io.netty.channel.ChannelPipeline pipeline) [CtBlockImpl]{
        [CtCommentImpl]// do nothing
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void init([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.util.ServerGremlinExecutor serverGremlinExecutor) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]settings = [CtInvocationImpl][CtVariableReadImpl]serverGremlinExecutor.getSettings();
        [CtAssignmentImpl][CtFieldWriteImpl]gremlinExecutor = [CtInvocationImpl][CtVariableReadImpl]serverGremlinExecutor.getGremlinExecutor();
        [CtAssignmentImpl][CtFieldWriteImpl]graphManager = [CtInvocationImpl][CtVariableReadImpl]serverGremlinExecutor.getGraphManager();
        [CtAssignmentImpl][CtFieldWriteImpl]gremlinExecutorService = [CtInvocationImpl][CtVariableReadImpl]serverGremlinExecutor.getGremlinExecutorService();
        [CtAssignmentImpl][CtFieldWriteImpl]scheduledExecutorService = [CtInvocationImpl][CtVariableReadImpl]serverGremlinExecutor.getScheduledExecutorService();
        [CtInvocationImpl][CtCommentImpl]// instantiate and configure the serializers that gremlin server will use - could error out here
        [CtCommentImpl]// and fail the server startup
        configureSerializers();
        [CtAssignmentImpl][CtCommentImpl]// configure ssl if present
        [CtFieldWriteImpl]sslContext = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]settings.optionalSsl().isPresent() && [CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.ssl.enabled) ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl]createSSLContext([CtFieldReadImpl]settings)) : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]sslContext.isPresent())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.info([CtLiteralImpl]"SSL enabled");

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.authentication.authenticator.equals([CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.authorization.authorizer)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]authenticator = [CtInvocationImpl]createAuthenticator([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.authentication);
            [CtAssignmentImpl][CtFieldWriteImpl]authorizer = [CtInvocationImpl]createAuthorizer([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.authorization);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.AuthenticatorAuthorizer authenticatorAuthorizer = [CtInvocationImpl]createAuthenticatorAuthorizer([CtFieldReadImpl]settings);
            [CtAssignmentImpl][CtFieldWriteImpl]authenticator = [CtVariableReadImpl]authenticatorAuthorizer;
            [CtAssignmentImpl][CtFieldWriteImpl]authorizer = [CtVariableReadImpl]authenticatorAuthorizer;
        }
        [CtAssignmentImpl][CtCommentImpl]// these handlers don't share any state and can thus be initialized once per pipeline
        [CtFieldWriteImpl]opSelectorHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.OpSelectorHandler([CtFieldReadImpl]settings, [CtFieldReadImpl]graphManager, [CtFieldReadImpl]gremlinExecutor, [CtFieldReadImpl]scheduledExecutorService, [CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]opExecutorHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.OpExecutorHandler([CtFieldReadImpl]settings, [CtFieldReadImpl]graphManager, [CtFieldReadImpl]gremlinExecutor, [CtFieldReadImpl]scheduledExecutorService);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void initChannel([CtParameterImpl]final [CtTypeReferenceImpl]io.netty.channel.socket.SocketChannel ch) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.netty.channel.ChannelPipeline pipeline = [CtInvocationImpl][CtVariableReadImpl]ch.pipeline();
        [CtInvocationImpl][CtFieldReadImpl]sslContext.ifPresent([CtLambdaImpl]([CtParameterImpl] sslContext) -> [CtInvocationImpl][CtVariableReadImpl]pipeline.addLast([CtFieldReadImpl][CtFieldReferenceImpl]PIPELINE_SSL, [CtInvocationImpl][CtVariableReadImpl]sslContext.newHandler([CtInvocationImpl][CtVariableReadImpl]ch.alloc())));
        [CtIfImpl][CtCommentImpl]// checks for no activity on a channel and triggers an event that is consumed by the OpSelectorHandler
        [CtCommentImpl]// and either closes the connection or sends a ping to see if the client is still alive
        if ([CtInvocationImpl]supportsIdleMonitor()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int idleConnectionTimeout = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.idleConnectionTimeout / [CtLiteralImpl]1000));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int keepAliveInterval = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.keepAliveInterval / [CtLiteralImpl]1000));
            [CtInvocationImpl][CtVariableReadImpl]pipeline.addLast([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.netty.handler.timeout.IdleStateHandler([CtVariableReadImpl]idleConnectionTimeout, [CtVariableReadImpl]keepAliveInterval, [CtLiteralImpl]0));
        }
        [CtInvocationImpl][CtCommentImpl]// the implementation provides the method by which Gremlin Server will process requests.  the end of the
        [CtCommentImpl]// pipeline must decode to an incoming RequestMessage instances and encode to a outgoing ResponseMessage
        [CtCommentImpl]// instance
        configure([CtVariableReadImpl]pipeline);
        [CtInvocationImpl][CtVariableReadImpl]pipeline.addLast([CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.PIPELINE_OP_SELECTOR, [CtFieldReadImpl]opSelectorHandler);
        [CtInvocationImpl][CtVariableReadImpl]pipeline.addLast([CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.PIPELINE_OP_EXECUTOR, [CtFieldReadImpl]opExecutorHandler);
        [CtInvocationImpl]finalize([CtVariableReadImpl]pipeline);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.AbstractAuthenticationHandler createAuthenticationHandler([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings settings) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]settings.authentication.authenticationHandler);
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.Class[] constructorArgs = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Class[[CtLiteralImpl]2];
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]constructorArgs[[CtLiteralImpl]0] = [CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.auth.Authenticator.class;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]constructorArgs[[CtLiteralImpl]1] = [CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.Settings.class;
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.handler.AbstractAuthenticationHandler) ([CtInvocationImpl][CtVariableReadImpl]clazz.getDeclaredConstructor([CtVariableReadImpl]constructorArgs).newInstance([CtFieldReadImpl]authenticator, [CtVariableReadImpl]settings)));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.warn([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not create/configure AuthenticationHandler %s", [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]settings.authentication.authenticationHandler), [CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.auth.Authenticator createAuthenticator([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.AuthenticationSettings config) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String authenticatorClass = [CtFieldReadImpl][CtVariableReadImpl]config.authenticator;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtVariableReadImpl]authenticatorClass);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.auth.Authenticator authenticator = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.auth.Authenticator) ([CtVariableReadImpl]clazz.newInstance()));
            [CtInvocationImpl][CtVariableReadImpl]authenticator.setup([CtFieldReadImpl][CtVariableReadImpl]config.config);
            [CtReturnImpl]return [CtVariableReadImpl]authenticator;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.warn([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not create/configure Authenticator %s", [CtFieldReadImpl]authenticator), [CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.Authorizer createAuthorizer([CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.AuthorizationSettings config) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String authorizerClass = [CtFieldReadImpl][CtVariableReadImpl]config.authorizer;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]authorizerClass) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtVariableReadImpl]authorizerClass);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.Authorizer authorizer = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.Authorizer) ([CtVariableReadImpl]clazz.newInstance()));
            [CtInvocationImpl][CtVariableReadImpl]authorizer.configure([CtFieldReadImpl][CtVariableReadImpl]config.config);
            [CtReturnImpl]return [CtVariableReadImpl]authorizer;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.warn([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not create/configure Authorizer %s", [CtFieldReadImpl]authorizer), [CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.AuthenticatorAuthorizer createAuthenticatorAuthorizer([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings settings) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> clazz = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]settings.authentication.authenticator);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.AuthenticatorAuthorizer authenticatorAuthorizer = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.authz.AuthenticatorAuthorizer) ([CtVariableReadImpl]clazz.newInstance()));
            [CtInvocationImpl][CtVariableReadImpl]authenticatorAuthorizer.setup([CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]settings.authentication.config);
            [CtInvocationImpl][CtVariableReadImpl]authenticatorAuthorizer.configure([CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]settings.authorization.config);
            [CtReturnImpl]return [CtVariableReadImpl]authenticatorAuthorizer;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.warn([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not create/configure Authenticator/Authorizer %s", [CtFieldReadImpl]authenticator), [CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void configureSerializers() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// grab some sensible defaults if no serializers are present in the config
        final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SerializerSettings> serializerSettings = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtFieldReadImpl][CtFieldReadImpl][CtThisAccessImpl]this.settings.serializers) || [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtThisAccessImpl]this.settings.serializers.isEmpty()) ? [CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.DEFAULT_SERIALIZERS : [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]settings.serializers;
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serializerSettings.stream().map([CtLambdaImpl]([CtParameterImpl] config) -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Class clazz = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtVariableReadImpl]config.className);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer.class.isAssignableFrom([CtVariableReadImpl]clazz)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtLiteralImpl]"The {} serialization class does not implement {} - it will not be available.", [CtVariableReadImpl]config.className, [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer.class.getCanonicalName());
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer>empty();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]clazz.getAnnotation([CtFieldReadImpl]java.lang.Deprecated.class) != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtLiteralImpl]"The {} serialization class is deprecated.", [CtVariableReadImpl]config.className);

                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer serializer = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer) ([CtVariableReadImpl]clazz.newInstance()));
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.structure.Graph> graphsDefinedAtStartup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.HashMap<>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String graphName : [CtInvocationImpl][CtVariableReadImpl]settings.graphs.keySet()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]graphsDefinedAtStartup.put([CtVariableReadImpl]graphName, [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]graphManager.getGraph([CtVariableReadImpl]graphName));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]config.config != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]serializer.configure([CtVariableReadImpl]config.config, [CtVariableReadImpl]graphsDefinedAtStartup);

                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]serializer);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException cnfe) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtLiteralImpl]"Could not find configured serializer class - {} - it will not be available", [CtVariableReadImpl]config.className);
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer>empty();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtLiteralImpl]"Could not instantiate configured serializer class - {} - it will not be available. {}", [CtVariableReadImpl]config.className, [CtInvocationImpl][CtTypeAccessImpl]org.apache.tinkerpop.gremlin.server.ex.getMessage());
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.<[CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer>empty();
            }
        }).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Optional::isPresent).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Optional::get).flatMap([CtLambdaImpl]([CtParameterImpl] serializer) -> [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.of([CtInvocationImpl][CtVariableReadImpl]serializer.mimeTypesSupported()).map([CtLambdaImpl]([CtParameterImpl] mimeType) -> [CtInvocationImpl][CtTypeAccessImpl]org.javatuples.Pair.with([CtVariableReadImpl]mimeType, [CtVariableReadImpl]serializer))).forEach([CtLambdaImpl]([CtParameterImpl] pair) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String mimeType = [CtInvocationImpl][CtVariableReadImpl]pair.getValue0();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.driver.MessageSerializer serializer = [CtInvocationImpl][CtVariableReadImpl]pair.getValue1();
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]serializers.containsKey([CtVariableReadImpl]mimeType))[CtBlockImpl]
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.info([CtLiteralImpl]"{} already has {} configured - it will not be replaced by {}, change order of serialization configuration if this is not desired.", [CtVariableReadImpl]mimeType, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]serializers.get([CtVariableReadImpl]mimeType).getClass().getName(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]serializer.getClass().getName());
            else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.info([CtLiteralImpl]"Configured {} with {}", [CtVariableReadImpl]mimeType, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pair.getValue1().getClass().getName());
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]serializers.put([CtVariableReadImpl]mimeType, [CtVariableReadImpl]serializer);
            }
        });
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]serializers.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.error([CtLiteralImpl]"No serializers were successfully configured - server will not start.");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Serialization configuration error.");
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.netty.handler.ssl.SslContext createSSLContext([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings settings) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.tinkerpop.gremlin.server.Settings.SslSettings sslSettings = [CtFieldReadImpl][CtVariableReadImpl]settings.ssl;
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sslSettings.getSslContext().isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.info([CtLiteralImpl]"Using the SslContext override");
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sslSettings.getSslContext().get();
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.netty.handler.ssl.SslProvider provider = [CtFieldReadImpl]io.netty.handler.ssl.SslProvider.JDK;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]io.netty.handler.ssl.SslContextBuilder builder;
        [CtTryImpl][CtCommentImpl]// Build JSSE SSLContext
        try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.net.ssl.KeyManagerFactory kmf = [CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.KeyManagerFactory.getInstance([CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
            [CtIfImpl][CtCommentImpl]// Load private key and signed cert
            if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStore) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String keyStoreType = [CtConditionalImpl]([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStoreType) ? [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getDefaultType() : [CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStoreType;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.security.KeyStore keystore = [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getInstance([CtVariableReadImpl]keyStoreType);
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]char[] password = [CtConditionalImpl]([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStorePassword) ? [CtLiteralImpl]null : [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStorePassword.toCharArray();
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream in = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStore)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]keystore.load([CtVariableReadImpl]in, [CtVariableReadImpl]password);
                }
                [CtInvocationImpl][CtVariableReadImpl]kmf.init([CtVariableReadImpl]keystore, [CtVariableReadImpl]password);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"keyStore must be configured when SSL is enabled.");
            }
            [CtAssignmentImpl][CtVariableWriteImpl]builder = [CtInvocationImpl][CtTypeAccessImpl]io.netty.handler.ssl.SslContextBuilder.forServer([CtVariableReadImpl]kmf);
            [CtIfImpl][CtCommentImpl]// Load custom truststore for client auth certs
            if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStore) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String trustStoreType = [CtConditionalImpl]([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStoreType) ? [CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStoreType : [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStoreType != [CtLiteralImpl]null ? [CtFieldReadImpl][CtVariableReadImpl]sslSettings.keyStoreType : [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getDefaultType();
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.security.KeyStore truststore = [CtInvocationImpl][CtTypeAccessImpl]java.security.KeyStore.getInstance([CtVariableReadImpl]trustStoreType);
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]char[] password = [CtConditionalImpl]([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStorePassword) ? [CtLiteralImpl]null : [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStorePassword.toCharArray();
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.InputStream in = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtFieldReadImpl][CtVariableReadImpl]sslSettings.trustStore)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]truststore.load([CtVariableReadImpl]in, [CtVariableReadImpl]password);
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.net.ssl.TrustManagerFactory tmf = [CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.TrustManagerFactory.getInstance([CtInvocationImpl][CtTypeAccessImpl]javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
                [CtInvocationImpl][CtVariableReadImpl]tmf.init([CtVariableReadImpl]truststore);
                [CtInvocationImpl][CtVariableReadImpl]builder.trustManager([CtVariableReadImpl]tmf);
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.security.UnrecoverableKeyException | [CtTypeReferenceImpl]java.security.NoSuchAlgorithmException | [CtTypeReferenceImpl]java.security.KeyStoreException | [CtTypeReferenceImpl]java.security.cert.CertificateException | [CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.error([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"There was an error enabling SSL.", [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslCipherSuites) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslCipherSuites.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.ciphers([CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslCipherSuites);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslEnabledProtocols) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslEnabledProtocols.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.protocols([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]sslSettings.sslEnabledProtocols.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{  }));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl][CtVariableReadImpl]sslSettings.needClientAuth) && [CtBinaryOperatorImpl]([CtFieldReadImpl]io.netty.handler.ssl.ClientAuth.OPTIONAL == [CtFieldReadImpl][CtVariableReadImpl]sslSettings.needClientAuth)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.warn([CtLiteralImpl]"needClientAuth = OPTIONAL is not a secure configuration. Setting to REQUIRE.");
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]sslSettings.needClientAuth = [CtFieldReadImpl]io.netty.handler.ssl.ClientAuth.REQUIRE;
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.clientAuth([CtFieldReadImpl][CtVariableReadImpl]sslSettings.needClientAuth).sslProvider([CtVariableReadImpl]provider);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.net.ssl.SSLException ssle) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.tinkerpop.gremlin.server.AbstractChannelizer.logger.error([CtInvocationImpl][CtVariableReadImpl]ssle.getMessage());
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"There was an error enabling SSL.", [CtVariableReadImpl]ssle);
        }
    }
}
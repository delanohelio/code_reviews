[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.activemq.artemis.core.protocol.core;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAForgetMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.RollbackMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ActiveMQExceptionType;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerCloseMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_SET_TIMEOUT;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.CREATE_SHARED_QUEUE;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_INDIVIDUAL_ACKNOWLEDGE;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_FLOWTOKEN;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_ROLLBACK;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ActiveMQException;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionForceConsumerDelivery;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_STOP;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAStartMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXASetTimeoutResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.NullResponseMessage_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.utils.actors.Actor;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_SEND_LARGE;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionDeleteQueueMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerFlowCreditMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_START;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ICoreMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V4;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V3;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.persistence.StorageManager;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.QueueQueryResult;
[CtImportImpl]import javax.transaction.xa.XAResource;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_PRODUCER_REQUEST_CREDITS;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage_V2;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_RESUME;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.SimpleString;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_PREPARE;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXASetTimeoutMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAJoinMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_EXPIRED;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.CREATE_QUEUE;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_COMMIT;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionCreateConsumerMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.BindingQueryResult;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_GET_TIMEOUT;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ActiveMQIOErrorException;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.ServerSession;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.client.ClientSession;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_BINDINGQUERY;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_END;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.remoting.FailureListener;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.utils.actors.ArtemisExecutor;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_ACKNOWLEDGE;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_CLOSE;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_QUEUEQUERY;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_ROLLBACK;
[CtImportImpl]import javax.transaction.xa.Xid;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.utils.SimpleFutureImpl;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendLargeMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAcknowledgeMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAAfterFailedMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAPrepareMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionUniqueAddMetaDataMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_CREATECONSUMER;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_COMMIT;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.exception.ActiveMQXAException;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.DELETE_QUEUE;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionRequestProducerCreditsMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_INDOUBT_XIDS;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.LargeServerMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessageV2;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateAddressMessage;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V3;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionIndividualAcknowledgeMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.CREATE_QUEUE_V2;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_SEND;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.CoreProtocolManager;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_START;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXARollbackMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.CREATE_ADDRESS;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.ActiveMQServer;
[CtUnresolvedImport]import org.apache.activemq.artemis.spi.core.protocol.EmbedMessageUtil;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionExpireMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.utils.SimpleFuture;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.remoting.CloseListener;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_FORGET;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_FORCE_CONSUMER_DELIVERY;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.ActiveMQServerLogger;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXACommitMessage;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_JOIN;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_FAILED;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_SEND_CONTINUATION;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.ActiveMQMessageBundle;
[CtUnresolvedImport]import org.jboss.logging.Logger;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.QueueConfiguration;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResumeMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAGetTimeoutResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.spi.core.remoting.Connection;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_XA_SUSPEND;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage_V2;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.CREATE_SHARED_QUEUE_V2;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.io.IOCallback;
[CtUnresolvedImport]import static org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_CONSUMER_CLOSE;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.RoutingType;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.ActiveMQInternalErrorException;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAGetInDoubtXidsResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.NullResponseMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendContinuationMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAEndMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.Message;
[CtClassImpl]public class ServerSessionPacketHandler implements [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.ChannelHandler {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.jboss.logging.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.jboss.logging.Logger.getLogger([CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ServerSession session;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.persistence.StorageManager storageManager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Channel channel;

    [CtFieldImpl]private volatile [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.CoreRemotingConnection remotingConnection;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.utils.actors.Actor<[CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet> packetActor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.utils.actors.ArtemisExecutor callExecutor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.CoreProtocolManager manager;

    [CtFieldImpl][CtCommentImpl]// The current currentLargeMessage being processed
    private volatile [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.LargeServerMessage currentLargeMessage;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean direct;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Object largeMessageLock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Object();

    [CtConstructorImpl]public ServerSessionPacketHandler([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ActiveMQServer server, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.CoreProtocolManager manager, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ServerSession session, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.persistence.StorageManager storageManager, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Channel channel) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.manager = [CtVariableReadImpl]manager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.session = [CtVariableReadImpl]session;
        [CtInvocationImpl][CtVariableReadImpl]session.addCloseable([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]boolean failed) -> [CtInvocationImpl]clearLargeMessage());
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storageManager = [CtVariableReadImpl]storageManager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.channel = [CtVariableReadImpl]channel;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.remotingConnection = [CtInvocationImpl][CtVariableReadImpl]channel.getConnection();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.spi.core.remoting.Connection conn = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.getTransportConnection();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.callExecutor = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]server.getExecutorFactory().getExecutor();
        [CtAssignmentImpl][CtCommentImpl]// In an optimized way packetActor should use the threadPool as the parent executor
        [CtCommentImpl]// directly from server.getThreadPool();
        [CtCommentImpl]// However due to how transferConnection is handled we need to
        [CtCommentImpl]// use the same executor
        [CtFieldWriteImpl][CtThisAccessImpl]this.packetActor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.utils.actors.Actor<>([CtFieldReadImpl]callExecutor, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::onMessagePacket);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.direct = [CtInvocationImpl][CtVariableReadImpl]conn.isDirectDeliver();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void clearLargeMessage() [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]largeMessageLock) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentLargeMessage != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]currentLargeMessage.deleteFile();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable error) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.errorDeletingLargeMessageFile([CtVariableReadImpl]error);
                } finally [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]currentLargeMessage = [CtLiteralImpl]null;
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ServerSession getSession() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]session;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]long getID() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]channel.getID();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void connectionFailed([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException exception, [CtParameterImpl][CtTypeReferenceImpl]boolean failedOver) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.clientConnectionFailed([CtInvocationImpl][CtFieldReadImpl]session.getName());
        [CtInvocationImpl]closeExecutors();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]session.close([CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.errorClosingSession([CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.clearingUpSession([CtInvocationImpl][CtFieldReadImpl]session.getName());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeExecutors() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]packetActor.shutdown();
        [CtInvocationImpl][CtFieldReadImpl]callExecutor.shutdown();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void close() [CtBlockImpl]{
        [CtInvocationImpl]closeExecutors();
        [CtInvocationImpl][CtFieldReadImpl]channel.flushConfirmations();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]session.close([CtLiteralImpl]false);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.errorClosingSession([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Channel getChannel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]channel;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void handlePacket([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// This method will call onMessagePacket through an actor
        [CtFieldReadImpl]packetActor.act([CtVariableReadImpl]packet);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onMessagePacket([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.isTraceEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.trace([CtBinaryOperatorImpl][CtLiteralImpl]"ServerSessionPacketHandler::handlePacket," + [CtVariableReadImpl]packet);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]byte type = [CtInvocationImpl][CtVariableReadImpl]packet.getType();
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_SEND :
                [CtBlockImpl]{
                    [CtInvocationImpl]onSessionSend([CtVariableReadImpl]packet);
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_ACKNOWLEDGE :
                [CtBlockImpl]{
                    [CtInvocationImpl]onSessionAcknowledge([CtVariableReadImpl]packet);
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_PRODUCER_REQUEST_CREDITS :
                [CtBlockImpl]{
                    [CtInvocationImpl]onSessionRequestProducerCredits([CtVariableReadImpl]packet);
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_FLOWTOKEN :
                [CtBlockImpl]{
                    [CtInvocationImpl]onSessionConsumerFlowCredit([CtVariableReadImpl]packet);
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtInvocationImpl][CtCommentImpl]// separating a method for everything else as JIT was faster this way
                slowPacketHandler([CtVariableReadImpl]packet);
                [CtBreakImpl]break;
        }
    }

    [CtMethodImpl][CtCommentImpl]// This is being separated from onMessagePacket as JIT was more efficient with a small method for the
    [CtCommentImpl]// hot executions.
    private [CtTypeReferenceImpl]void slowPacketHandler([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]byte type = [CtInvocationImpl][CtVariableReadImpl]packet.getType();
        [CtInvocationImpl][CtFieldReadImpl]storageManager.setContext([CtInvocationImpl][CtFieldReadImpl]session.getSessionContext());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean flush = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean closeChannel = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresResponse = [CtLiteralImpl]false;
        [CtTryImpl]try [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_SEND_LARGE :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendLargeMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendLargeMessage) (packet));
                            [CtInvocationImpl]sendLarge([CtInvocationImpl][CtVariableReadImpl]message.getLargeMessage());
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_SEND_CONTINUATION :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendContinuationMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendContinuationMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]message.isRequiresResponse();
                            [CtInvocationImpl]sendContinuations([CtInvocationImpl][CtVariableReadImpl]message.getPacketSize(), [CtInvocationImpl][CtVariableReadImpl]message.getMessageBodySize(), [CtInvocationImpl][CtVariableReadImpl]message.getBody(), [CtInvocationImpl][CtVariableReadImpl]message.isContinues());
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_CREATECONSUMER :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionCreateConsumerMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionCreateConsumerMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtInvocationImpl][CtFieldReadImpl]session.createConsumer([CtInvocationImpl][CtVariableReadImpl]request.getID(), [CtInvocationImpl][CtVariableReadImpl]request.getQueueName(), [CtInvocationImpl][CtVariableReadImpl]request.getFilterString(), [CtInvocationImpl][CtVariableReadImpl]request.getPriority(), [CtInvocationImpl][CtVariableReadImpl]request.isBrowseOnly(), [CtLiteralImpl]true, [CtLiteralImpl]null);
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtCommentImpl]// We send back queue information on the queue as a response- this allows the queue to
                                [CtCommentImpl]// be automatically recreated on failover
                                [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.QueueQueryResult queueQueryResult = [CtInvocationImpl][CtFieldReadImpl]session.executeQueueQuery([CtInvocationImpl][CtVariableReadImpl]request.getQueueName());
                                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_QUEUEQUERY_RESP_V3)) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V3([CtVariableReadImpl]queueQueryResult);
                                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_QUEUEQUERY_RESP_V2)) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V2([CtVariableReadImpl]queueQueryResult);
                                } else [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage([CtVariableReadImpl]queueQueryResult);
                                }
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.CREATE_ADDRESS :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateAddressMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateAddressMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtInvocationImpl][CtFieldReadImpl]session.createAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress(), [CtInvocationImpl][CtVariableReadImpl]request.getRoutingTypes(), [CtInvocationImpl][CtVariableReadImpl]request.isAutoCreated());
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.CREATE_QUEUE :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtInvocationImpl][CtFieldReadImpl]session.createQueue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl][CtVariableReadImpl]request.getQueueName()).setAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress()).setRoutingType([CtInvocationImpl]getRoutingTypeFromAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress())).setFilterString([CtInvocationImpl][CtVariableReadImpl]request.getFilterString()).setTemporary([CtInvocationImpl][CtVariableReadImpl]request.isTemporary()).setDurable([CtInvocationImpl][CtVariableReadImpl]request.isDurable()));
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.CREATE_QUEUE_V2 :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage_V2 request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateQueueMessage_V2) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtInvocationImpl][CtFieldReadImpl]session.createQueue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl][CtVariableReadImpl]request.getQueueName()).setAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress()).setRoutingType([CtInvocationImpl][CtVariableReadImpl]request.getRoutingType()).setFilterString([CtInvocationImpl][CtVariableReadImpl]request.getFilterString()).setTemporary([CtInvocationImpl][CtVariableReadImpl]request.isTemporary()).setDurable([CtInvocationImpl][CtVariableReadImpl]request.isDurable()).setMaxConsumers([CtInvocationImpl][CtVariableReadImpl]request.getMaxConsumers()).setPurgeOnNoConsumers([CtInvocationImpl][CtVariableReadImpl]request.isPurgeOnNoConsumers()).setExclusive([CtInvocationImpl][CtVariableReadImpl]request.isExclusive()).setGroupRebalance([CtInvocationImpl][CtVariableReadImpl]request.isGroupRebalance()).setGroupBuckets([CtInvocationImpl][CtVariableReadImpl]request.getGroupBuckets()).setGroupFirstKey([CtInvocationImpl][CtVariableReadImpl]request.getGroupFirstKey()).setLastValue([CtInvocationImpl][CtVariableReadImpl]request.isLastValue()).setLastValueKey([CtInvocationImpl][CtVariableReadImpl]request.getLastValueKey()).setNonDestructive([CtInvocationImpl][CtVariableReadImpl]request.isNonDestructive()).setConsumersBeforeDispatch([CtInvocationImpl][CtVariableReadImpl]request.getConsumersBeforeDispatch()).setDelayBeforeDispatch([CtInvocationImpl][CtVariableReadImpl]request.getDelayBeforeDispatch()).setAutoDelete([CtInvocationImpl][CtVariableReadImpl]request.isAutoDelete()).setAutoDeleteDelay([CtInvocationImpl][CtVariableReadImpl]request.getAutoDeleteDelay()).setAutoDeleteMessageCount([CtInvocationImpl][CtVariableReadImpl]request.getAutoDeleteMessageCount()).setAutoCreated([CtInvocationImpl][CtVariableReadImpl]request.isAutoCreated()).setRingSize([CtInvocationImpl][CtVariableReadImpl]request.getRingSize()));
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.CREATE_SHARED_QUEUE :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.QueueQueryResult result = [CtInvocationImpl][CtFieldReadImpl]session.executeQueueQuery([CtInvocationImpl][CtVariableReadImpl]request.getQueueName());
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]result.isExists() && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]result.getAddress(), [CtInvocationImpl][CtVariableReadImpl]request.getAddress())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]result.getFilterString(), [CtInvocationImpl][CtVariableReadImpl]request.getFilterString()))) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]session.createSharedQueue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl][CtVariableReadImpl]request.getQueueName()).setAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress()).setFilterString([CtInvocationImpl][CtVariableReadImpl]request.getFilterString()).setDurable([CtInvocationImpl][CtVariableReadImpl]request.isDurable()));
                            }
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.CREATE_SHARED_QUEUE_V2 :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage_V2 request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.CreateSharedQueueMessage_V2) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]request.isRequiresResponse();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.QueueQueryResult result = [CtInvocationImpl][CtFieldReadImpl]session.executeQueueQuery([CtInvocationImpl][CtVariableReadImpl]request.getQueueName());
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]result.isExists() && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]result.getAddress(), [CtInvocationImpl][CtVariableReadImpl]request.getAddress())) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]result.getFilterString(), [CtInvocationImpl][CtVariableReadImpl]request.getFilterString()))) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]session.createSharedQueue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl][CtVariableReadImpl]request.getQueueName()).setAddress([CtInvocationImpl][CtVariableReadImpl]request.getAddress()).setRoutingType([CtInvocationImpl][CtVariableReadImpl]request.getRoutingType()).setFilterString([CtInvocationImpl][CtVariableReadImpl]request.getFilterString()).setDurable([CtInvocationImpl][CtVariableReadImpl]request.isDurable()).setMaxConsumers([CtInvocationImpl][CtVariableReadImpl]request.getMaxConsumers()).setPurgeOnNoConsumers([CtInvocationImpl][CtVariableReadImpl]request.isPurgeOnNoConsumers()).setExclusive([CtInvocationImpl][CtVariableReadImpl]request.isExclusive()).setGroupRebalance([CtInvocationImpl][CtVariableReadImpl]request.isGroupRebalance()).setGroupBuckets([CtInvocationImpl][CtVariableReadImpl]request.getGroupBuckets()).setGroupFirstKey([CtInvocationImpl][CtVariableReadImpl]request.getGroupFirstKey()).setLastValue([CtInvocationImpl][CtVariableReadImpl]request.isLastValue()).setLastValueKey([CtInvocationImpl][CtVariableReadImpl]request.getLastValueKey()).setNonDestructive([CtInvocationImpl][CtVariableReadImpl]request.isNonDestructive()).setConsumersBeforeDispatch([CtInvocationImpl][CtVariableReadImpl]request.getConsumersBeforeDispatch()).setDelayBeforeDispatch([CtInvocationImpl][CtVariableReadImpl]request.getDelayBeforeDispatch()).setAutoDelete([CtInvocationImpl][CtVariableReadImpl]request.isAutoDelete()).setAutoDeleteDelay([CtInvocationImpl][CtVariableReadImpl]request.getAutoDeleteDelay()).setAutoDeleteMessageCount([CtInvocationImpl][CtVariableReadImpl]request.getAutoDeleteMessageCount()));
                            }
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.DELETE_QUEUE :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionDeleteQueueMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionDeleteQueueMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.deleteQueue([CtInvocationImpl][CtVariableReadImpl]request.getQueueName());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_QUEUEQUERY :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryMessage) (packet));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.QueueQueryResult result = [CtInvocationImpl][CtFieldReadImpl]session.executeQueueQuery([CtInvocationImpl][CtVariableReadImpl]request.getQueueName());
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.isExists() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]remotingConnection.getChannelVersion() < [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.ADDRESSING_CHANGE_VERSION)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]result.setAddress([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryMessage.getOldPrefixedAddress([CtInvocationImpl][CtVariableReadImpl]result.getAddress(), [CtInvocationImpl][CtVariableReadImpl]result.getRoutingType()));
                            }
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_QUEUEQUERY_RESP_V3)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V3([CtVariableReadImpl]result);
                            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_QUEUEQUERY_RESP_V2)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage_V2([CtVariableReadImpl]result);
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionQueueQueryResponseMessage([CtVariableReadImpl]result);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_BINDINGQUERY :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryMessage request = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryMessage) (packet));
                            [CtLocalVariableImpl]final [CtTypeReferenceImpl]int clientVersion = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.getChannelVersion();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.BindingQueryResult result = [CtInvocationImpl][CtFieldReadImpl]session.executeBindingQuery([CtInvocationImpl][CtVariableReadImpl]request.getAddress());
                            [CtIfImpl][CtCommentImpl]/* if the session is JMS and it's from an older client then we need to add the old prefix to the queue
                            names otherwise the older client won't realize the queue exists and will try to create it and receive
                            an error
                             */
                            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]result.isExists() && [CtBinaryOperatorImpl]([CtVariableReadImpl]clientVersion < [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.ADDRESSING_CHANGE_VERSION)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]session.getMetaData([CtTypeAccessImpl]ClientSession.JMS_SESSION_IDENTIFIER_PROPERTY) != [CtLiteralImpl]null)) [CtBlockImpl]{
                                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.SimpleString> queueNames = [CtInvocationImpl][CtVariableReadImpl]result.getQueueNames();
                                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]queueNames.isEmpty()) [CtBlockImpl]{
                                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.SimpleString> convertedQueueNames = [CtInvocationImpl][CtVariableReadImpl]request.convertQueueNames([CtVariableReadImpl]clientVersion, [CtVariableReadImpl]queueNames);
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]convertedQueueNames != [CtVariableReadImpl]queueNames) [CtBlockImpl]{
                                        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.BindingQueryResult([CtInvocationImpl][CtVariableReadImpl]result.isExists(), [CtInvocationImpl][CtVariableReadImpl]result.getAddressInfo(), [CtVariableReadImpl]convertedQueueNames, [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateQueues(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateAddresses(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultPurgeOnNoConsumers(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultMaxConsumers(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultExclusive(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultLastValue(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultLastValueKey(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultNonDestructive(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultConsumersBeforeDispatch(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultDelayBeforeDispatch());
                                    }
                                }
                            }
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_BINDINGQUERY_RESP_V4)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V4([CtInvocationImpl][CtVariableReadImpl]result.isExists(), [CtInvocationImpl][CtVariableReadImpl]result.getQueueNames(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateQueues(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateAddresses(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultPurgeOnNoConsumers(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultMaxConsumers(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultExclusive(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultLastValue(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultLastValueKey(), [CtInvocationImpl][CtVariableReadImpl]result.isDefaultNonDestructive(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultConsumersBeforeDispatch(), [CtInvocationImpl][CtVariableReadImpl]result.getDefaultDelayBeforeDispatch());
                            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_BINDINGQUERY_RESP_V3)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V3([CtInvocationImpl][CtVariableReadImpl]result.isExists(), [CtInvocationImpl][CtVariableReadImpl]result.getQueueNames(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateQueues(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateAddresses());
                            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]channel.supports([CtTypeAccessImpl]PacketImpl.SESS_BINDINGQUERY_RESP_V2)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage_V2([CtInvocationImpl][CtVariableReadImpl]result.isExists(), [CtInvocationImpl][CtVariableReadImpl]result.getQueueNames(), [CtInvocationImpl][CtVariableReadImpl]result.isAutoCreateQueues());
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionBindingQueryResponseMessage([CtInvocationImpl][CtVariableReadImpl]result.isExists(), [CtInvocationImpl][CtVariableReadImpl]result.getQueueNames());
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_EXPIRED :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionExpireMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionExpireMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.expire([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID(), [CtInvocationImpl][CtVariableReadImpl]message.getMessageID());
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_COMMIT :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtFieldReadImpl]session.commit();
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_ROLLBACK :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtFieldReadImpl]session.rollback([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.RollbackMessage) (packet)).isConsiderLastMessageAsDelivered());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_COMMIT :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXACommitMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXACommitMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaCommit([CtInvocationImpl][CtVariableReadImpl]message.getXid(), [CtInvocationImpl][CtVariableReadImpl]message.isOnePhase());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_END :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAEndMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAEndMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaEnd([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_FORGET :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAForgetMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAForgetMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaForget([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_JOIN :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAJoinMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAJoinMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaJoin([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_RESUME :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResumeMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResumeMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaResume([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_ROLLBACK :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXARollbackMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXARollbackMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaRollback([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_START :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAStartMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAStartMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaStart([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_FAILED :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAAfterFailedMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAAfterFailedMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaFailed([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtBreakImpl][CtCommentImpl]// no response on this case
                            break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_SUSPEND :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtFieldReadImpl]session.xaSuspend();
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_PREPARE :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAPrepareMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAPrepareMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaPrepare([CtInvocationImpl][CtVariableReadImpl]message.getXid());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createSessionXAResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_INDOUBT_XIDS :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]javax.transaction.xa.Xid> xids = [CtInvocationImpl][CtFieldReadImpl]session.xaGetInDoubtXids();
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAGetInDoubtXidsResponseMessage([CtVariableReadImpl]xids);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_GET_TIMEOUT :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int timeout = [CtInvocationImpl][CtFieldReadImpl]session.xaGetTimeout();
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAGetTimeoutResponseMessage([CtVariableReadImpl]timeout);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_XA_SET_TIMEOUT :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXASetTimeoutMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXASetTimeoutMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.xaSetTimeout([CtInvocationImpl][CtVariableReadImpl]message.getTimeoutSeconds());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXASetTimeoutResponseMessage([CtLiteralImpl]true);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_START :
                        [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]session.start();
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_STOP :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtFieldReadImpl]session.stop();
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_CLOSE :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtFieldReadImpl]session.close([CtLiteralImpl]false);
                            [CtAssignmentImpl][CtCommentImpl]// removeConnectionListeners();
                            [CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtAssignmentImpl][CtVariableWriteImpl]flush = [CtLiteralImpl]true;
                            [CtAssignmentImpl][CtVariableWriteImpl]closeChannel = [CtLiteralImpl]true;
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_INDIVIDUAL_ACKNOWLEDGE :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionIndividualAcknowledgeMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionIndividualAcknowledgeMessage) (packet));
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]message.isRequiresResponse();
                            [CtInvocationImpl][CtFieldReadImpl]session.individualAcknowledge([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID(), [CtInvocationImpl][CtVariableReadImpl]message.getMessageID());
                            [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_CONSUMER_CLOSE :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerCloseMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerCloseMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.closeConsumer([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID());
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]PacketImpl.SESS_FORCE_CONSUMER_DELIVERY :
                        [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionForceConsumerDelivery message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionForceConsumerDelivery) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.forceConsumerDelivery([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID(), [CtInvocationImpl][CtVariableReadImpl]message.getSequence());
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_ADD_METADATA :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessage) (packet));
                            [CtInvocationImpl][CtFieldReadImpl]session.addMetaData([CtInvocationImpl][CtVariableReadImpl]message.getKey(), [CtInvocationImpl][CtVariableReadImpl]message.getData());
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_ADD_METADATA2 :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessageV2 message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAddMetaDataMessageV2) (packet));
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]message.isRequiresConfirmations()) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            }
                            [CtInvocationImpl][CtFieldReadImpl]session.addMetaData([CtInvocationImpl][CtVariableReadImpl]message.getKey(), [CtInvocationImpl][CtVariableReadImpl]message.getData());
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.SESS_UNIQUE_ADD_METADATA :
                        [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtLiteralImpl]true;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionUniqueAddMetaDataMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionUniqueAddMetaDataMessage) (packet));
                            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]session.addUniqueMetaData([CtInvocationImpl][CtVariableReadImpl]message.getKey(), [CtInvocationImpl][CtVariableReadImpl]message.getData())) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage([CtInvocationImpl][CtTypeAccessImpl]ActiveMQMessageBundle.BUNDLE.duplicateMetadata([CtInvocationImpl][CtVariableReadImpl]message.getKey(), [CtInvocationImpl][CtVariableReadImpl]message.getData()));
                            }
                            [CtBreakImpl]break;
                        }
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQIOErrorExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQXAExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onCatchThrowableWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]t, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }
            [CtInvocationImpl]sendResponse([CtVariableReadImpl]packet, [CtVariableReadImpl]response, [CtVariableReadImpl]flush, [CtVariableReadImpl]closeChannel);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]storageManager.clearContext();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.RoutingType getRoutingTypeFromAddress([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.SimpleString address) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]address.startsWith([CtTypeAccessImpl]PacketImpl.OLD_QUEUE_PREFIX) || [CtInvocationImpl][CtVariableReadImpl]address.startsWith([CtTypeAccessImpl]PacketImpl.OLD_TEMP_QUEUE_PREFIX)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.ANYCAST;
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.MULTICAST;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet createNullResponseMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]packet.isResponseAsync()) || [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]channel.getConnection().isVersionBeforeAsyncResponseChange()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.NullResponseMessage();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.NullResponseMessage_V2([CtInvocationImpl][CtVariableReadImpl]packet.getCorrelationID());
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet createSessionXAResponseMessage([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]packet.isResponseAsync()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage_V2([CtInvocationImpl][CtVariableReadImpl]packet.getCorrelationID(), [CtLiteralImpl]false, [CtFieldReadImpl][CtTypeAccessImpl]javax.transaction.xa.XAResource.[CtFieldReferenceImpl]XA_OK, [CtLiteralImpl]null);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage([CtLiteralImpl]false, [CtFieldReadImpl][CtTypeAccessImpl]javax.transaction.xa.XAResource.[CtFieldReferenceImpl]XA_OK, [CtLiteralImpl]null);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onSessionAcknowledge([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.setContext([CtInvocationImpl][CtFieldReadImpl]session.getSessionContext());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresResponse = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAcknowledgeMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionAcknowledgeMessage) (packet));
                [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]message.isRequiresResponse();
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.session.acknowledge([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID(), [CtInvocationImpl][CtVariableReadImpl]message.getMessageID());
                [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQIOErrorExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQXAExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onCatchThrowableWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]t, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }
            [CtInvocationImpl]sendResponse([CtVariableReadImpl]packet, [CtVariableReadImpl]response, [CtLiteralImpl]false, [CtLiteralImpl]false);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.clearContext();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onSessionSend([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.setContext([CtInvocationImpl][CtFieldReadImpl]session.getSessionContext());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresResponse = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendMessage) (packet));
                [CtAssignmentImpl][CtVariableWriteImpl]requiresResponse = [CtInvocationImpl][CtVariableReadImpl]message.isRequiresResponse();
                [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.session.send([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.spi.core.protocol.EmbedMessageUtil.extractEmbedded([CtInvocationImpl][CtVariableReadImpl]message.getMessage(), [CtFieldReadImpl]storageManager), [CtFieldReadImpl][CtThisAccessImpl]this.direct);
                [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]createNullResponseMessage([CtVariableReadImpl]packet);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQIOErrorExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQXAExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onCatchThrowableWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]t, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }
            [CtInvocationImpl]sendResponse([CtVariableReadImpl]packet, [CtVariableReadImpl]response, [CtLiteralImpl]false, [CtLiteralImpl]false);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.clearContext();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onSessionRequestProducerCredits([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.setContext([CtInvocationImpl][CtFieldReadImpl]session.getSessionContext());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresResponse = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionRequestProducerCreditsMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionRequestProducerCreditsMessage) (packet));
                [CtInvocationImpl][CtFieldReadImpl]session.requestProducerCredits([CtInvocationImpl][CtVariableReadImpl]message.getAddress(), [CtInvocationImpl][CtVariableReadImpl]message.getCredits());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQIOErrorExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQXAExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onCatchThrowableWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]t, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }
            [CtInvocationImpl]sendResponse([CtVariableReadImpl]packet, [CtVariableReadImpl]response, [CtLiteralImpl]false, [CtLiteralImpl]false);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.clearContext();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onSessionConsumerFlowCredit([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.setContext([CtInvocationImpl][CtFieldReadImpl]session.getSessionContext());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresResponse = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerFlowCreditMessage message = [CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionConsumerFlowCreditMessage) (packet));
                [CtInvocationImpl][CtFieldReadImpl]session.receiveConsumerCredits([CtInvocationImpl][CtVariableReadImpl]message.getConsumerID(), [CtInvocationImpl][CtVariableReadImpl]message.getCredits());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQIOErrorExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQXAExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onActiveMQExceptionWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.onCatchThrowableWhileHandlePacket([CtVariableReadImpl]packet, [CtVariableReadImpl]t, [CtVariableReadImpl]requiresResponse, [CtVariableReadImpl]response, [CtFieldReadImpl][CtThisAccessImpl]this.session);
            }
            [CtInvocationImpl]sendResponse([CtVariableReadImpl]packet, [CtVariableReadImpl]response, [CtLiteralImpl]false, [CtLiteralImpl]false);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageManager.clearContext();
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet onActiveMQIOErrorExceptionWhileHandlePacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQIOErrorException e, [CtParameterImpl][CtTypeReferenceImpl]boolean requiresResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ServerSession session) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]session.markTXFailed([CtVariableReadImpl]e);
        [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.debug([CtLiteralImpl]"Sending exception to client", [CtVariableReadImpl]e);
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.convertToExceptionPacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.caughtException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet onActiveMQXAExceptionWhileHandlePacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.exception.ActiveMQXAException e, [CtParameterImpl][CtTypeReferenceImpl]boolean requiresResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.debug([CtLiteralImpl]"Sending exception to client", [CtVariableReadImpl]e);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]packet.isResponseAsync()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage_V2([CtInvocationImpl][CtVariableReadImpl]packet.getCorrelationID(), [CtLiteralImpl]true, [CtFieldReadImpl][CtVariableReadImpl]e.errorCode, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionXAResponseMessage([CtLiteralImpl]true, [CtFieldReadImpl][CtVariableReadImpl]e.errorCode, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.caughtXaException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet onActiveMQQueueMaxConsumerLimitReachedWhileHandlePacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQQueueMaxConsumerLimitReached e, [CtParameterImpl][CtTypeReferenceImpl]boolean requiresResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.debug([CtLiteralImpl]"Sending exception to client", [CtVariableReadImpl]e);
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.convertToExceptionPacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.caughtException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet convertToExceptionPacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]packet.isResponseAsync()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage_V2([CtInvocationImpl][CtVariableReadImpl]packet.getCorrelationID(), [CtVariableReadImpl]e);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.impl.wireformat.ActiveMQExceptionMessage([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet onActiveMQExceptionWhileHandlePacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException e, [CtParameterImpl][CtTypeReferenceImpl]boolean requiresResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.debug([CtLiteralImpl]"Sending exception to client", [CtVariableReadImpl]e);
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.convertToExceptionPacket([CtVariableReadImpl]packet, [CtVariableReadImpl]e);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getType() == [CtFieldReadImpl]org.apache.activemq.artemis.api.core.ActiveMQExceptionType.QUEUE_EXISTS) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.debug([CtLiteralImpl]"Caught exception", [CtVariableReadImpl]e);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.caughtException([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet onCatchThrowableWhileHandlePacket([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet packet, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t, [CtParameterImpl][CtTypeReferenceImpl]boolean requiresResponse, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response, [CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ServerSession session) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]session.markTXFailed([CtVariableReadImpl]t);
        [CtIfImpl]if ([CtVariableReadImpl]requiresResponse) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.sendingUnexpectedExceptionToClient([CtVariableReadImpl]t);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQException activeMQInternalErrorException = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ActiveMQInternalErrorException();
            [CtInvocationImpl][CtVariableReadImpl]activeMQInternalErrorException.initCause([CtVariableReadImpl]t);
            [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.convertToExceptionPacket([CtVariableReadImpl]packet, [CtVariableReadImpl]activeMQInternalErrorException);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.caughtException([CtVariableReadImpl]t);
        }
        [CtReturnImpl]return [CtVariableReadImpl]response;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void sendResponse([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet confirmPacket, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response, [CtParameterImpl]final [CtTypeReferenceImpl]boolean flush, [CtParameterImpl]final [CtTypeReferenceImpl]boolean closeChannel) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.isTraceEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.trace([CtBinaryOperatorImpl][CtLiteralImpl]"ServerSessionPacketHandler::scheduling response::" + [CtVariableReadImpl]response);
        }
        [CtInvocationImpl][CtFieldReadImpl]storageManager.afterCompleteOperations([CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.io.IOCallback()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl]final [CtTypeReferenceImpl]int errorCode, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String errorMessage) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.errorProcessingIOCallback([CtVariableReadImpl]errorCode, [CtVariableReadImpl]errorMessage);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet exceptionPacket = [CtInvocationImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.convertToExceptionPacket([CtVariableReadImpl]confirmPacket, [CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.ActiveMQExceptionType.createException([CtVariableReadImpl]errorCode, [CtVariableReadImpl]errorMessage));
                [CtInvocationImpl]doConfirmAndResponse([CtVariableReadImpl]confirmPacket, [CtVariableReadImpl]exceptionPacket, [CtVariableReadImpl]flush, [CtVariableReadImpl]closeChannel);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.isTraceEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.trace([CtBinaryOperatorImpl][CtLiteralImpl]"ServerSessionPacketHandler::exception response sent::" + [CtVariableReadImpl]exceptionPacket);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void done() [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.isTraceEnabled()) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.trace([CtBinaryOperatorImpl][CtLiteralImpl]"ServerSessionPacketHandler::regular response sent::" + [CtVariableReadImpl]response);
                }
                [CtInvocationImpl]doConfirmAndResponse([CtVariableReadImpl]confirmPacket, [CtVariableReadImpl]response, [CtVariableReadImpl]flush, [CtVariableReadImpl]closeChannel);
            }
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void doConfirmAndResponse([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet confirmPacket, [CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.Packet response, [CtParameterImpl]final [CtTypeReferenceImpl]boolean flush, [CtParameterImpl]final [CtTypeReferenceImpl]boolean closeChannel) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// don't confirm if the response is an exception
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]confirmPacket != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]response == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]response != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]response.getType() != [CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.impl.PacketImpl.EXCEPTION)))) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]channel.confirm([CtVariableReadImpl]confirmPacket);
            [CtIfImpl]if ([CtVariableReadImpl]flush) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]channel.flushConfirmations();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]response != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]channel.send([CtVariableReadImpl]response);
        }
        [CtIfImpl]if ([CtVariableReadImpl]closeChannel) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]channel.close();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void closeListeners() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.CloseListener> listeners = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.removeCloseListeners();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.CloseListener closeListener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]closeListener.connectionClosed();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]closeListener instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.FailureListener) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]remotingConnection.removeFailureListener([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.FailureListener) (closeListener)));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int transferConnection([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.CoreRemotingConnection newConnection, [CtParameterImpl]final [CtTypeReferenceImpl]int lastReceivedCommandID) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.utils.SimpleFuture<[CtTypeReferenceImpl]java.lang.Integer> future = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.utils.SimpleFutureImpl<>();
        [CtInvocationImpl][CtFieldReadImpl]callExecutor.execute([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int value = [CtInvocationImpl]internaltransferConnection([CtVariableReadImpl]newConnection, [CtVariableReadImpl]lastReceivedCommandID);
            [CtInvocationImpl][CtVariableReadImpl]future.set([CtVariableReadImpl]value);
        });
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]future.get().intValue();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int internaltransferConnection([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.protocol.core.CoreRemotingConnection newConnection, [CtParameterImpl]final [CtTypeReferenceImpl]int lastReceivedCommandID) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// We need to disable delivery on all the consumers while the transfer is occurring- otherwise packets might get
        [CtCommentImpl]// delivered
        [CtCommentImpl]// after the channel has transferred but *before* packets have been replayed - this will give the client the wrong
        [CtCommentImpl]// sequence of packets.
        [CtCommentImpl]// It is not sufficient to just stop the session, since right after stopping the session, another session start
        [CtCommentImpl]// might be executed
        [CtCommentImpl]// before we have transferred the connection, leaving it in a started state
        [CtFieldReadImpl]session.setTransferring([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.CloseListener> closeListeners = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.removeCloseListeners();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.activemq.artemis.core.remoting.FailureListener> failureListeners = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.removeFailureListeners();
        [CtInvocationImpl][CtCommentImpl]// Note. We do not destroy the replicating connection here. In the case the live server has really crashed
        [CtCommentImpl]// then the connection will get cleaned up anyway when the server ping timeout kicks in.
        [CtCommentImpl]// In the case the live server is really still up, i.e. a split brain situation (or in tests), then closing
        [CtCommentImpl]// the replicating connection will cause the outstanding responses to be be replayed on the live server,
        [CtCommentImpl]// if these reach the client who then subsequently fails over, on reconnection to backup, it will have
        [CtCommentImpl]// received responses that the backup did not know about.
        [CtFieldReadImpl]channel.transferConnection([CtVariableReadImpl]newConnection);
        [CtInvocationImpl][CtVariableReadImpl]newConnection.syncIDGeneratorSequence([CtInvocationImpl][CtFieldReadImpl]remotingConnection.getIDGeneratorSequence());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.spi.core.remoting.Connection oldTransportConnection = [CtInvocationImpl][CtFieldReadImpl]remotingConnection.getTransportConnection();
        [CtAssignmentImpl][CtFieldWriteImpl]remotingConnection = [CtVariableReadImpl]newConnection;
        [CtInvocationImpl][CtFieldReadImpl]remotingConnection.setCloseListeners([CtVariableReadImpl]closeListeners);
        [CtInvocationImpl][CtFieldReadImpl]remotingConnection.setFailureListeners([CtVariableReadImpl]failureListeners);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int serverLastReceivedCommandID = [CtInvocationImpl][CtFieldReadImpl]channel.getLastConfirmedCommandID();
        [CtInvocationImpl][CtFieldReadImpl]channel.replayCommands([CtVariableReadImpl]lastReceivedCommandID);
        [CtInvocationImpl][CtFieldReadImpl]channel.setTransferring([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]session.setTransferring([CtLiteralImpl]false);
        [CtInvocationImpl][CtCommentImpl]// We do this because the old connection could be out of credits on netty
        [CtCommentImpl]// this will force anything to resume after the reattach through the ReadyListener callbacks
        [CtVariableReadImpl]oldTransportConnection.fireReady([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]serverLastReceivedCommandID;
    }

    [CtMethodImpl][CtCommentImpl]// Large Message is part of the core protocol, we have these functions here as part of Packet handler
    private [CtTypeReferenceImpl]void sendLarge([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.Message message) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// need to create the LargeMessage before continue
        [CtTypeReferenceImpl]long id = [CtInvocationImpl][CtFieldReadImpl]storageManager.generateID();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.LargeServerMessage largeMsg = [CtInvocationImpl][CtFieldReadImpl]storageManager.createLargeMessage([CtVariableReadImpl]id, [CtVariableReadImpl]message);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.isTraceEnabled()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.core.protocol.core.ServerSessionPacketHandler.logger.trace([CtBinaryOperatorImpl][CtLiteralImpl]"sendLarge::" + [CtVariableReadImpl]largeMsg);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentLargeMessage != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ActiveMQServerLogger.LOGGER.replacingIncompleteLargeMessage([CtInvocationImpl][CtFieldReadImpl]currentLargeMessage.getMessageID());
        }
        [CtAssignmentImpl][CtFieldWriteImpl]currentLargeMessage = [CtVariableReadImpl]largeMsg;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void sendContinuations([CtParameterImpl]final [CtTypeReferenceImpl]int packetSize, [CtParameterImpl]final [CtTypeReferenceImpl]long messageBodySize, [CtParameterImpl]final [CtArrayTypeReferenceImpl]byte[] body, [CtParameterImpl]final [CtTypeReferenceImpl]boolean continues) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]largeMessageLock) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]currentLargeMessage == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]ActiveMQMessageBundle.BUNDLE.largeMessageNotInitialised();
            }
            [CtInvocationImpl][CtCommentImpl]// Immediately release the credits for the continuations- these don't contribute to the in-memory size
            [CtCommentImpl]// of the message
            [CtFieldReadImpl]currentLargeMessage.addBytes([CtVariableReadImpl]body);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]continues) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]currentLargeMessage.releaseResources([CtLiteralImpl]true);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]messageBodySize >= [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentLargeMessage.toMessage().putLongProperty([CtTypeAccessImpl]Message.HDR_LARGE_BODY_SIZE, [CtVariableReadImpl]messageBodySize);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.LargeServerMessage message = [CtFieldReadImpl]currentLargeMessage;
                [CtInvocationImpl][CtFieldReadImpl]currentLargeMessage.setStorageManager([CtFieldReadImpl]storageManager);
                [CtAssignmentImpl][CtFieldWriteImpl]currentLargeMessage = [CtLiteralImpl]null;
                [CtInvocationImpl][CtFieldReadImpl]session.doSend([CtInvocationImpl][CtFieldReadImpl]session.getCurrentTransaction(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.spi.core.protocol.EmbedMessageUtil.extractEmbedded([CtInvocationImpl](([CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.ICoreMessage) ([CtVariableReadImpl]message.toMessage())), [CtFieldReadImpl]storageManager), [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false);
            }
        }
    }
}
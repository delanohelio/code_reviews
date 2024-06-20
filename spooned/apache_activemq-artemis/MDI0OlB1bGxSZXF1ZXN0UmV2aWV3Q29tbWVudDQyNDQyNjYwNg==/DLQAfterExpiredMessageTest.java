[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.activemq.artemis.tests.integration.amqp;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.settings.impl.AddressSettings;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpSender;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.impl.AddressInfo;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.ActiveMQServer;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpSession;
[CtUnresolvedImport]import org.jboss.logging.Logger;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.SimpleString;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.server.Queue;
[CtUnresolvedImport]import org.apache.activemq.artemis.tests.util.Wait;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.apache.activemq.artemis.protocol.amqp.converter.AMQPMessageSupport;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpConnection;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.QueueConfiguration;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpClient;
[CtUnresolvedImport]import org.apache.activemq.artemis.api.core.RoutingType;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpMessage;
[CtUnresolvedImport]import org.apache.activemq.artemis.core.settings.impl.AddressFullMessagePolicy;
[CtUnresolvedImport]import org.apache.qpid.proton.amqp.Symbol;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.junit.Assert;
[CtUnresolvedImport]import org.apache.activemq.transport.amqp.client.AmqpReceiver;
[CtClassImpl][CtJavaDocImpl]/**
 * This is testing a double transfer (copy).
 * First messages will expire, then DLQ.
 * This will validate the data added to the queues.
 */
public class DLQAfterExpiredMessageTest extends [CtTypeReferenceImpl]org.apache.activemq.artemis.tests.integration.amqp.AmqpClientTestSupport {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.jboss.logging.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.jboss.logging.Logger.getLogger([CtFieldReadImpl]org.apache.activemq.artemis.tests.integration.amqp.DLQAfterExpiredMessageTest.class);

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String getExpiryQueue() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"ActiveMQ.Expiry";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void createAddressAndQueues([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ActiveMQServer server) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Default Queue
        [CtVariableReadImpl]server.addAddressInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.impl.AddressInfo([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getQueueName()), [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.ANYCAST));
        [CtInvocationImpl][CtVariableReadImpl]server.createQueue([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl]getQueueName()).setRoutingType([CtTypeAccessImpl]RoutingType.ANYCAST));
        [CtInvocationImpl][CtCommentImpl]// Default DLQ
        [CtVariableReadImpl]server.addAddressInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.impl.AddressInfo([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getDeadLetterAddress()), [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.ANYCAST));
        [CtInvocationImpl][CtVariableReadImpl]server.createQueue([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl]getDeadLetterAddress()).setRoutingType([CtTypeAccessImpl]RoutingType.ANYCAST));
        [CtInvocationImpl][CtCommentImpl]// Expiry
        [CtVariableReadImpl]server.addAddressInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.impl.AddressInfo([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getExpiryQueue()), [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.ANYCAST));
        [CtInvocationImpl][CtVariableReadImpl]server.createQueue([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl]getExpiryQueue()).setRoutingType([CtTypeAccessImpl]RoutingType.ANYCAST));
        [CtInvocationImpl][CtCommentImpl]// Default Topic
        [CtVariableReadImpl]server.addAddressInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.impl.AddressInfo([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getTopicName()), [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.MULTICAST));
        [CtInvocationImpl][CtVariableReadImpl]server.createQueue([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl]getTopicName()));
        [CtForImpl][CtCommentImpl]// Additional Test Queues
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl]getPrecreatedQueueSize(); [CtUnaryOperatorImpl]++[CtVariableWriteImpl]i) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]server.addAddressInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.impl.AddressInfo([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getQueueName([CtVariableReadImpl]i)), [CtFieldReadImpl]org.apache.activemq.artemis.api.core.RoutingType.ANYCAST));
            [CtInvocationImpl][CtVariableReadImpl]server.createQueue([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.api.core.QueueConfiguration([CtInvocationImpl]getQueueName([CtVariableReadImpl]i)).setRoutingType([CtTypeAccessImpl]RoutingType.ANYCAST));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void configureAddressPolicy([CtParameterImpl][CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.ActiveMQServer server) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Address configuration
        [CtTypeReferenceImpl]org.apache.activemq.artemis.core.settings.impl.AddressSettings addressSettings = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.artemis.core.settings.impl.AddressSettings();
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setAddressFullMessagePolicy([CtTypeAccessImpl]AddressFullMessagePolicy.PAGE);
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setAutoCreateQueues([CtInvocationImpl]isAutoCreateQueues());
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setAutoCreateAddresses([CtInvocationImpl]isAutoCreateAddresses());
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setDeadLetterAddress([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getDeadLetterAddress()));
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setExpiryAddress([CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.api.core.SimpleString.toSimpleString([CtInvocationImpl]getExpiryQueue()));
        [CtInvocationImpl][CtVariableReadImpl]addressSettings.setMaxDeliveryAttempts([CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]server.getConfiguration().getAddressesSettings().put([CtLiteralImpl]"#", [CtVariableReadImpl]addressSettings);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]server.getConfiguration().getAddressesSettings().put([CtInvocationImpl]getExpiryQueue(), [CtVariableReadImpl]addressSettings);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testDoubleTransfer() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpClient client = [CtInvocationImpl]createAmqpClient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpConnection connection = [CtInvocationImpl]addConnection([CtInvocationImpl][CtVariableReadImpl]client.connect());
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpSession session = [CtInvocationImpl][CtVariableReadImpl]connection.createSession();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpSender sender = [CtInvocationImpl][CtVariableReadImpl]session.createSender([CtInvocationImpl]getQueueName());
            [CtLocalVariableImpl][CtCommentImpl]// Get the Queue View early to avoid racing the delivery.
            final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.Queue queueView = [CtInvocationImpl]getProxyToQueue([CtInvocationImpl]getQueueName());
            [CtInvocationImpl]assertNotNull([CtVariableReadImpl]queueView);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpMessage message = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpMessage();
            [CtInvocationImpl][CtVariableReadImpl]message.setTimeToLive([CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]message.setText([CtLiteralImpl]"Test-Message");
            [CtInvocationImpl][CtVariableReadImpl]message.setDurable([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]message.setApplicationProperty([CtLiteralImpl]"key1", [CtLiteralImpl]"Value1");
            [CtInvocationImpl][CtVariableReadImpl]sender.send([CtVariableReadImpl]message);
            [CtInvocationImpl][CtVariableReadImpl]sender.close();
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.tests.util.Wait.assertEquals([CtLiteralImpl]1, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]queueView::getMessagesExpired);
            [CtLocalVariableImpl][CtCommentImpl]// Now try and get the message
            [CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpReceiver receiver = [CtInvocationImpl][CtVariableReadImpl]session.createReceiver([CtInvocationImpl]getQueueName());
            [CtInvocationImpl][CtVariableReadImpl]receiver.flow([CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpMessage received = [CtInvocationImpl][CtVariableReadImpl]receiver.receiveNoWait();
            [CtInvocationImpl]assertNull([CtVariableReadImpl]received);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.activemq.artemis.core.server.Queue expiryView = [CtInvocationImpl]getProxyToQueue([CtInvocationImpl]getExpiryQueue());
            [CtInvocationImpl]assertNotNull([CtVariableReadImpl]expiryView);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.activemq.artemis.tests.util.Wait.assertEquals([CtLiteralImpl]1, [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]expiryView::getMessageCount);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean dlqed = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> annotations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.activemq.transport.amqp.client.AmqpReceiver receiverDLQ = [CtInvocationImpl][CtVariableReadImpl]session.createReceiver([CtInvocationImpl]getExpiryQueue(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\"m." + [CtFieldReadImpl]org.apache.activemq.artemis.protocol.amqp.converter.AMQPMessageSupport.HDR_ORIGINAL_ADDRESS_ANNOTATION) + [CtLiteralImpl]"\"=\'") + [CtInvocationImpl]getQueueName()) + [CtLiteralImpl]"'");
            [CtInvocationImpl][CtVariableReadImpl]receiverDLQ.flow([CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]received = [CtInvocationImpl][CtVariableReadImpl]receiverDLQ.receive([CtLiteralImpl]5, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS);
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]received);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.apache.qpid.proton.amqp.Symbol, [CtTypeReferenceImpl]java.lang.Object> avAnnotations = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]received.getWrappedMessage().getMessageAnnotations().getValue();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.qpid.proton.amqp.Symbol an : [CtInvocationImpl][CtVariableReadImpl]avAnnotations.keySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]annotations.put([CtInvocationImpl][CtVariableReadImpl]an.toString(), [CtInvocationImpl][CtVariableReadImpl]avAnnotations.get([CtVariableReadImpl]an));
            }
            [CtInvocationImpl][CtVariableReadImpl]received.reject();
            [CtInvocationImpl][CtVariableReadImpl]receiverDLQ.close();
            [CtAssignmentImpl][CtCommentImpl]// Redo the selection
            [CtVariableWriteImpl]receiverDLQ = [CtInvocationImpl][CtVariableReadImpl]session.createReceiver([CtInvocationImpl]getDeadLetterAddress(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\"m." + [CtFieldReadImpl]org.apache.activemq.artemis.protocol.amqp.converter.AMQPMessageSupport.HDR_ORIGINAL_ADDRESS_ANNOTATION) + [CtLiteralImpl]"\"=\'") + [CtInvocationImpl]getQueueName()) + [CtLiteralImpl]"'");
            [CtInvocationImpl][CtVariableReadImpl]receiverDLQ.flow([CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]received = [CtInvocationImpl][CtVariableReadImpl]receiverDLQ.receive([CtLiteralImpl]5, [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]SECONDS);
            [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertNotNull([CtVariableReadImpl]received);
            [CtInvocationImpl][CtVariableReadImpl]received.accept();
            [CtForEachImpl][CtJavaDocImpl]/**
             * When moving to DLQ, the original headers shoudln't be touched.
             */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> entry : [CtInvocationImpl][CtVariableReadImpl]annotations.entrySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.activemq.artemis.tests.integration.amqp.DLQAfterExpiredMessageTest.log.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Checking " + [CtInvocationImpl][CtVariableReadImpl]entry.getKey()) + [CtLiteralImpl]" = ") + [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
                [CtInvocationImpl][CtTypeAccessImpl]org.junit.Assert.assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entry.getKey() + [CtLiteralImpl]" should be = ") + [CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtInvocationImpl][CtVariableReadImpl]received.getMessageAnnotation([CtInvocationImpl][CtVariableReadImpl]entry.getKey()));
            }
            [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]received.getTimeToLive());
            [CtInvocationImpl]assertNotNull([CtVariableReadImpl]received);
            [CtInvocationImpl]assertEquals([CtLiteralImpl]"Value1", [CtInvocationImpl][CtVariableReadImpl]received.getApplicationProperty([CtLiteralImpl]"key1"));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]connection.close();
        }
    }
}
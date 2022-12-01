[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.kie.kogito.addon.cloudevents.spring;
[CtUnresolvedImport]import reactor.kafka.receiver.ReceiverOptions;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.apache.kafka.clients.consumer.ConsumerConfig;
[CtUnresolvedImport]import reactor.kafka.receiver.ReceiverRecord;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Value;
[CtUnresolvedImport]import reactor.core.publisher.Flux;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import org.springframework.stereotype.Component;
[CtUnresolvedImport]import org.apache.kafka.common.serialization.StringDeserializer;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Qualifier;
[CtUnresolvedImport]import org.springframework.context.annotation.Bean;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import reactor.kafka.receiver.ReceiverOffset;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import reactor.kafka.receiver.KafkaReceiver;
[CtClassImpl][CtAnnotationImpl]@org.springframework.stereotype.Component
public class SpringKafkaCloudEventPublisher {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtFieldReadImpl]org.kie.kogito.addon.cloudevents.spring.SpringKafkaCloudEventPublisher.class.getName());

    [CtFieldImpl]private final [CtTypeReferenceImpl]reactor.kafka.receiver.ReceiverOptions<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.String> receiverOptions;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String topic;

    [CtConstructorImpl]public SpringKafkaCloudEventPublisher([CtParameterImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtLiteralImpl]"${spring.kafka.bootstrap-servers}")
    [CtTypeReferenceImpl]java.lang.String kafkaBootstrapAddress, [CtParameterImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtLiteralImpl]"${spring.kafka.consumer.group-id}")
    [CtTypeReferenceImpl]java.lang.String groupId, [CtParameterImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Value([CtLiteralImpl]"${kogito.addon.cloudevents.kafka.kogito_incoming_stream:kogito_incoming_stream}")
    [CtTypeReferenceImpl]java.lang.String kafkaTopicName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.topic = [CtVariableReadImpl]kafkaTopicName;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, [CtVariableReadImpl]kafkaBootstrapAddress);
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]ConsumerConfig.GROUP_ID_CONFIG, [CtVariableReadImpl]groupId);
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringDeserializer.class);
        [CtInvocationImpl][CtVariableReadImpl]props.put([CtTypeAccessImpl]ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, [CtFieldReadImpl]org.apache.kafka.common.serialization.StringDeserializer.class);
        [CtAssignmentImpl][CtFieldWriteImpl]receiverOptions = [CtInvocationImpl][CtTypeAccessImpl]reactor.kafka.receiver.ReceiverOptions.create([CtVariableReadImpl]props);
        [CtAssignmentImpl][CtFieldWriteImpl]dateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"HH:mm:ss:SSS z dd MMM yyyy");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtAnnotationImpl]@org.springframework.beans.factory.annotation.Qualifier([CtLiteralImpl]"kogito_event_publisher")
    public [CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]java.lang.String> convert_to_demo_topic() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]makeConsumer();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]java.lang.String> makeConsumer() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]reactor.kafka.receiver.ReceiverOptions<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.String> options = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]receiverOptions.subscription([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singleton([CtFieldReadImpl]topic)).addAssignListener([CtLambdaImpl]([CtParameterImpl] partitions) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtLiteralImpl]"onPartitionsAssigned {}", [CtVariableReadImpl]partitions)).addRevokeListener([CtLambdaImpl]([CtParameterImpl] partitions) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtLiteralImpl]"onPartitionsRevoked {}", [CtVariableReadImpl]partitions));
        [CtLocalVariableImpl][CtTypeReferenceImpl]reactor.core.publisher.Flux<[CtTypeReferenceImpl]reactor.kafka.receiver.ReceiverRecord<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]java.lang.String>> kafkaFlux = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]reactor.kafka.receiver.KafkaReceiver.create([CtVariableReadImpl]options).receive();
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]kafkaFlux.map([CtLambdaImpl]([CtParameterImpl] record) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]reactor.kafka.receiver.ReceiverOffset offset = [CtInvocationImpl][CtVariableReadImpl]record.receiverOffset();
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]log.debug([CtLiteralImpl]"Received message: topic-partition={} offset={} timestamp={} key={} value={}\n", [CtInvocationImpl][CtVariableReadImpl]offset.topicPartition(), [CtInvocationImpl][CtVariableReadImpl]offset.offset(), [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]dateFormat.format([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]record.timestamp())), [CtInvocationImpl][CtVariableReadImpl]record.key(), [CtInvocationImpl][CtVariableReadImpl]record.value());
            [CtInvocationImpl][CtVariableReadImpl]offset.acknowledge();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]record.value();
        });
    }
}
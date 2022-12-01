[CompilationUnitImpl][CtPackageDeclarationImpl]package org.lfenergy.operatorfabric.autoconfigure.kafka;
[CtUnresolvedImport]import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
[CtUnresolvedImport]import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
[CtUnresolvedImport]import lombok.RequiredArgsConstructor;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.cards.publication.kafka.consumer.CardCommandConsumerListener;
[CtImportImpl]import java.time.Duration;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.cards.publication.kafka.command.CommandHandler;
[CtUnresolvedImport]import org.lfenergy.operatorfabric.avro.CardCommand;
[CtUnresolvedImport]import org.springframework.context.annotation.Configuration;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import org.springframework.kafka.config.KafkaListenerContainerFactory;
[CtUnresolvedImport]import org.springframework.kafka.core.ConsumerFactory;
[CtUnresolvedImport]import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
[CtUnresolvedImport]import org.springframework.context.annotation.Bean;
[CtImportImpl]import java.util.List;
[CtClassImpl][CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
[CtAnnotationImpl]@lombok.RequiredArgsConstructor
[CtAnnotationImpl]@org.springframework.context.annotation.Configuration
public class KafkaListenerContainerFactoryConfiguration {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.springframework.boot.autoconfigure.kafka.KafkaProperties kafkaProperties;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.springframework.kafka.core.ConsumerFactory<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.lfenergy.operatorfabric.avro.CardCommand> consumerFactory;

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Integer getConcurrency([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.springframework.boot.autoconfigure.kafka.KafkaProperties.Listener listener) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer concurrency = [CtInvocationImpl][CtVariableReadImpl]listener.getConcurrency();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]concurrency != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]concurrency;
        }
        [CtReturnImpl]return [CtLiteralImpl]1;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Long getPollTimeout([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.springframework.boot.autoconfigure.kafka.KafkaProperties.Listener listener) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.Duration duration = [CtInvocationImpl][CtVariableReadImpl]listener.getPollTimeout();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]duration != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]duration.toMillis();
        }
        [CtReturnImpl]return [CtLiteralImpl]1000L;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    public [CtTypeReferenceImpl]org.springframework.kafka.config.KafkaListenerContainerFactory<[CtTypeReferenceImpl]org.springframework.kafka.listener.ConcurrentMessageListenerContainer<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.lfenergy.operatorfabric.avro.CardCommand>> kafkaListenerContainerFactory() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.springframework.boot.autoconfigure.kafka.KafkaProperties.Listener listener = [CtInvocationImpl][CtFieldReadImpl]kafkaProperties.getListener();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer concurrency = [CtInvocationImpl]getConcurrency([CtVariableReadImpl]listener);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long pollTimeOut = [CtInvocationImpl]getPollTimeout([CtVariableReadImpl]listener);
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Concurrency: " + [CtVariableReadImpl]concurrency);
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"PollTimeout: " + [CtVariableReadImpl]pollTimeOut);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.lfenergy.operatorfabric.avro.CardCommand> factory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<>();
        [CtInvocationImpl][CtVariableReadImpl]factory.setConsumerFactory([CtFieldReadImpl]consumerFactory);
        [CtInvocationImpl][CtVariableReadImpl]factory.setConcurrency([CtVariableReadImpl]concurrency);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]factory.getContainerProperties().setPollTimeout([CtVariableReadImpl]pollTimeOut);
        [CtReturnImpl]return [CtVariableReadImpl]factory;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.context.annotation.Bean
    [CtTypeReferenceImpl]org.lfenergy.operatorfabric.cards.publication.kafka.consumer.CardCommandConsumerListener createCardCommandConsumerListener([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.lfenergy.operatorfabric.cards.publication.kafka.command.CommandHandler> commandHandlerList) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.lfenergy.operatorfabric.cards.publication.kafka.consumer.CardCommandConsumerListener([CtVariableReadImpl]commandHandlerList);
    }
}
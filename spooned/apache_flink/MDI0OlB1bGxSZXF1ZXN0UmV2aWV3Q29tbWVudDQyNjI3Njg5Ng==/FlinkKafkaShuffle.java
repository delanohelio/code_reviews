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
[CtPackageDeclarationImpl]package org.apache.flink.streaming.connectors.kafka.shuffle;
[CtUnresolvedImport]import org.apache.flink.api.java.tuple.Tuple;
[CtUnresolvedImport]import org.apache.flink.streaming.api.TimeCharacteristic;
[CtUnresolvedImport]import org.apache.flink.streaming.api.transformations.SinkTransformation;
[CtUnresolvedImport]import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
[CtUnresolvedImport]import org.apache.flink.api.common.typeutils.TypeSerializer;
[CtUnresolvedImport]import org.apache.flink.api.java.functions.KeySelector;
[CtUnresolvedImport]import org.apache.flink.streaming.util.keys.KeySelectorUtil;
[CtUnresolvedImport]import org.apache.flink.api.common.typeinfo.BasicArrayTypeInfo;
[CtUnresolvedImport]import org.apache.flink.streaming.api.datastream.DataStreamUtils;
[CtUnresolvedImport]import org.apache.flink.streaming.api.datastream.KeyedStream;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import org.apache.flink.util.PropertiesUtil;
[CtUnresolvedImport]import org.apache.flink.util.Preconditions;
[CtUnresolvedImport]import org.apache.flink.api.common.typeinfo.PrimitiveArrayTypeInfo;
[CtUnresolvedImport]import org.apache.flink.streaming.api.datastream.DataStream;
[CtUnresolvedImport]import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
[CtUnresolvedImport]import org.apache.flink.streaming.api.functions.source.SourceFunction;
[CtUnresolvedImport]import org.apache.flink.annotation.Experimental;
[CtUnresolvedImport]import org.apache.flink.api.common.operators.Keys;
[CtUnresolvedImport]import org.apache.flink.api.common.typeinfo.TypeInformation;
[CtUnresolvedImport]import org.apache.flink.runtime.state.KeyGroupRangeAssignment;
[CtUnresolvedImport]import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
[CtClassImpl][CtJavaDocImpl]/**
 * {@link FlinkKafkaShuffle} uses Kafka as a message bus to shuffle and persist data at the same time.
 *
 * <p>Persisting shuffle data is useful when
 *     - you would like to reuse the shuffle data and/or,
 *     - you would like to avoid a full restart of a pipeline during failure recovery
 *
 * <p>Persisting shuffle is achieved by wrapping a {@link FlinkKafkaShuffleProducer} and
 * a {@link FlinkKafkaShuffleConsumer} together into a {@link FlinkKafkaShuffle}.
 * Here is an example how to use a {@link FlinkKafkaShuffle}.
 *
 * <p><pre>{@code StreamExecutionEnvironment env = ... 					// create execution environment
 * 	DataStream<X> source = env.addSource(...)				// add data stream source
 * 	DataStream<Y> dataStream = ...							// some transformation(s) based on source
 *
 * 	KeyedStream<Y, KEY> keyedStream = FlinkKafkaShuffle
 * 		.persistentKeyBy(									// keyBy shuffle through kafka
 * 			dataStream,										// data stream to be shuffled
 * 			topic,											// Kafka topic written to
 * 			producerParallelism,							// the number of tasks of a Kafka Producer
 * 			numberOfPartitions,								// the number of partitions of the Kafka topic written to
 * 			kafkaProperties,								// kafka properties for Kafka Producer and Consumer
 * 			keySelector<Y, KEY>);							// key selector to retrieve key from `dataStream'
 *
 * 	keyedStream.transform...								// some other transformation(s)
 *
 * 	KeyedStream<Y, KEY> keyedStreamReuse = FlinkKafkaShuffle
 * 		.readKeyBy(											// Read the Kafka shuffle data again for other usages
 * 			topic,											// the topic of Kafka where data is persisted
 * 			env,											// execution environment, and it can be a new environment
 * 			typeInformation<Y>,								// type information of the data persisted in Kafka
 * 			kafkaProperties,								// kafka properties for Kafka Consumer
 * 			keySelector<Y, KEY>);							// key selector to retrieve key
 *
 * 	keyedStreamReuse.transform...							// some other transformation(s)}</pre>
 *
 * <p>Usage of {@link FlinkKafkaShuffle#persistentKeyBy} is similar to {@link DataStream#keyBy(KeySelector)}.
 * The differences are:
 *
 * <p>1). Partitioning is done through {@link FlinkKafkaShuffleProducer}. {@link FlinkKafkaShuffleProducer} decides
 * 			which partition a key goes when writing to Kafka
 *
 * <p>2). Shuffle data can be reused through {@link FlinkKafkaShuffle#readKeyBy}, as shown in the example above.
 *
 * <p>3). Job execution is decoupled by the persistent Kafka message bus. In the example, the job execution graph is
 * 			decoupled to three regions: `KafkaShuffleProducer', `KafkaShuffleConsumer' and `KafkaShuffleConsumerReuse'
 * 			through `PERSISTENT DATA` as shown below. If any region fails the execution, the other two keep progressing.
 *
 * <p><pre>
 *     source -> ... KafkaShuffleProducer -> PERSISTENT DATA -> KafkaShuffleConsumer -> ...
 *                                                |
 *                                                | ----------> KafkaShuffleConsumerReuse -> ...
 * </pre>
 */
[CtAnnotationImpl]@org.apache.flink.annotation.Experimental
public class FlinkKafkaShuffle {
    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String PRODUCER_PARALLELISM = [CtLiteralImpl]"producer parallelism";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String PARTITION_NUMBER = [CtLiteralImpl]"partition number";

    [CtMethodImpl][CtJavaDocImpl]/**
     * Uses Kafka as a message bus to persist keyBy shuffle.
     *
     * <p>Persisting keyBy shuffle is achieved by wrapping a {@link FlinkKafkaShuffleProducer} and
     * {@link FlinkKafkaShuffleConsumer} together.
     *
     * <p>On the producer side, {@link FlinkKafkaShuffleProducer}
     * is similar to {@link DataStream#keyBy(KeySelector)}. They use the same key group assignment function
     * {@link KeyGroupRangeAssignment#assignKeyToParallelOperator} to decide which partition a key goes.
     * Hence, each producer task can potentially write to each Kafka partition based on where the key goes.
     * Here, `numberOfPartitions` equals to the key group size.
     * In the case of using {@link TimeCharacteristic#EventTime}, each producer task broadcasts its watermark
     * to ALL of the Kafka partitions to make sure watermark information is propagated correctly.
     *
     * <p>On the consumer side, each consumer task should read partitions equal to the key group indices
     * it is assigned. `numberOfPartitions` is the maximum parallelism of the consumer. This version only
     * supports numberOfPartitions = consumerParallelism.
     * In the case of using {@link TimeCharacteristic#EventTime}, a consumer task is responsible to emit
     * watermarks. Watermarks are read from the corresponding Kafka partitions. Notice that a consumer task only starts
     * to emit a watermark after reading at least one watermark from each producer task to make sure watermarks
     * are monotonically increasing. Hence a consumer task needs to know `producerParallelism` as well.
     *
     * @see FlinkKafkaShuffle#writeKeyBy
     * @see FlinkKafkaShuffle#readKeyBy
     * @param dataStream
     * 		Data stream to be shuffled
     * @param topic
     * 		Kafka topic written to
     * @param producerParallelism
     * 		Parallelism of producer
     * @param numberOfPartitions
     * 		Number of partitions
     * @param properties
     * 		Kafka properties
     * @param keySelector
     * 		Key selector to retrieve key from `dataStream'
     * @param <T>
     * 		Type of the input data stream
     * @param <K>
     * 		Type of key
     */
    public static <[CtTypeParameterImpl]T, [CtTypeParameterImpl]K> [CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.KeyedStream<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> persistentKeyBy([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> dataStream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String topic, [CtParameterImpl][CtTypeReferenceImpl]int producerParallelism, [CtParameterImpl][CtTypeReferenceImpl]int numberOfPartitions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties properties, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.api.java.functions.KeySelector<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> keySelector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// KafkaProducer#propsToMap uses Properties purely as a HashMap without considering the default properties
        [CtCommentImpl]// So we have to flatten the default property to first level elements.
        [CtTypeReferenceImpl]java.util.Properties kafkaProperties = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.util.PropertiesUtil.flatten([CtVariableReadImpl]properties);
        [CtInvocationImpl][CtVariableReadImpl]kafkaProperties.setProperty([CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PRODUCER_PARALLELISM, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]producerParallelism));
        [CtInvocationImpl][CtVariableReadImpl]kafkaProperties.setProperty([CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PARTITION_NUMBER, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]numberOfPartitions));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.environment.StreamExecutionEnvironment env = [CtInvocationImpl][CtVariableReadImpl]dataStream.getExecutionEnvironment();
        [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.writeKeyBy([CtVariableReadImpl]dataStream, [CtVariableReadImpl]topic, [CtVariableReadImpl]kafkaProperties, [CtVariableReadImpl]keySelector);
        [CtReturnImpl]return [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.readKeyBy([CtVariableReadImpl]topic, [CtVariableReadImpl]env, [CtInvocationImpl][CtVariableReadImpl]dataStream.getType(), [CtVariableReadImpl]kafkaProperties, [CtVariableReadImpl]keySelector);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Uses Kafka as a message bus to persist keyBy shuffle.
     *
     * @param dataStream
     * 		Data stream to be shuffled
     * @param topic
     * 		Kafka topic written to
     * @param producerParallelism
     * 		Parallelism of producer
     * @param numberOfPartitions
     * 		Number of partitions
     * @param properties
     * 		Kafka properties
     * @param fields
     * 		Key positions from the input data stream
     * @param <T>
     * 		Type of the input data stream
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.KeyedStream<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]org.apache.flink.api.java.tuple.Tuple> persistentKeyBy([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> dataStream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String topic, [CtParameterImpl][CtTypeReferenceImpl]int producerParallelism, [CtParameterImpl][CtTypeReferenceImpl]int numberOfPartitions, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties properties, [CtParameterImpl]int... fields) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.persistentKeyBy([CtVariableReadImpl]dataStream, [CtVariableReadImpl]topic, [CtVariableReadImpl]producerParallelism, [CtVariableReadImpl]numberOfPartitions, [CtVariableReadImpl]properties, [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.keySelector([CtVariableReadImpl]dataStream, [CtVariableReadImpl]fields));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The write side of {@link FlinkKafkaShuffle#persistentKeyBy}.
     *
     * <p>This function contains a {@link FlinkKafkaShuffleProducer} to shuffle and persist data in Kafka.
     * {@link FlinkKafkaShuffleProducer} uses the same key group assignment function
     * {@link KeyGroupRangeAssignment#assignKeyToParallelOperator} to decide which partition a key goes.
     * Hence, each producer task can potentially write to each Kafka partition based on the key.
     * Here, the number of partitions equals to the key group size.
     * In the case of using {@link TimeCharacteristic#EventTime}, each producer task broadcasts each watermark
     * to all of the Kafka partitions to make sure watermark information is propagated properly.
     *
     * <p>Attention: make sure kafkaProperties include
     * {@link FlinkKafkaShuffle#PRODUCER_PARALLELISM} and {@link FlinkKafkaShuffle#PARTITION_NUMBER} explicitly.
     * {@link FlinkKafkaShuffle#PRODUCER_PARALLELISM} is the parallelism of the producer.
     * {@link FlinkKafkaShuffle#PARTITION_NUMBER} is the number of partitions.
     * They are not necessarily the same and allowed to be set independently.
     *
     * @see FlinkKafkaShuffle#persistentKeyBy
     * @see FlinkKafkaShuffle#readKeyBy
     * @param dataStream
     * 		Data stream to be shuffled
     * @param topic
     * 		Kafka topic written to
     * @param kafkaProperties
     * 		Kafka properties for Kafka Producer
     * @param keySelector
     * 		Key selector to retrieve key from `dataStream'
     * @param <T>
     * 		Type of the input data stream
     * @param <K>
     * 		Type of key
     */
    public static <[CtTypeParameterImpl]T, [CtTypeParameterImpl]K> [CtTypeReferenceImpl]void writeKeyBy([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> dataStream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String topic, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties kafkaProperties, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.api.java.functions.KeySelector<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> keySelector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.environment.StreamExecutionEnvironment env = [CtInvocationImpl][CtVariableReadImpl]dataStream.getExecutionEnvironment();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.api.common.typeutils.TypeSerializer<[CtTypeParameterReferenceImpl]T> typeSerializer = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataStream.getType().createSerializer([CtInvocationImpl][CtVariableReadImpl]env.getConfig());
        [CtLocalVariableImpl][CtCommentImpl]// write data to Kafka
        [CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleProducer<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> kafkaProducer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleProducer<>([CtVariableReadImpl]topic, [CtVariableReadImpl]typeSerializer, [CtVariableReadImpl]kafkaProperties, [CtInvocationImpl][CtVariableReadImpl]env.clean([CtVariableReadImpl]keySelector), [CtFieldReadImpl]FlinkKafkaProducer.Semantic.EXACTLY_ONCE, [CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer.DEFAULT_KAFKA_PRODUCERS_POOL_SIZE);
        [CtInvocationImpl][CtCommentImpl]// make sure the sink parallelism is set to producerParallelism
        [CtTypeAccessImpl]org.apache.flink.util.Preconditions.checkArgument([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]kafkaProperties.getProperty([CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PRODUCER_PARALLELISM) != [CtLiteralImpl]null, [CtLiteralImpl]"Missing producer parallelism for Kafka Shuffle");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int producerParallelism = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.util.PropertiesUtil.getInt([CtVariableReadImpl]kafkaProperties, [CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PRODUCER_PARALLELISM, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE);
        [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.addKafkaShuffle([CtVariableReadImpl]dataStream, [CtVariableReadImpl]kafkaProducer, [CtVariableReadImpl]producerParallelism);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The write side of {@link FlinkKafkaShuffle#persistentKeyBy}.
     *
     * @param dataStream
     * 		Data stream to be shuffled
     * @param topic				Kafka
     * 		topic written to
     * @param kafkaProperties
     * 		Kafka properties for Kafka Producer
     * @param fields
     * 		Key positions from the input data stream
     * @param <T>
     * 		Type of the input data stream
     */
    public static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]void writeKeyBy([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> dataStream, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String topic, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties kafkaProperties, [CtParameterImpl]int... fields) [CtBlockImpl]{
        [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.writeKeyBy([CtVariableReadImpl]dataStream, [CtVariableReadImpl]topic, [CtVariableReadImpl]kafkaProperties, [CtInvocationImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.keySelector([CtVariableReadImpl]dataStream, [CtVariableReadImpl]fields));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The read side of {@link FlinkKafkaShuffle#persistentKeyBy}.
     *
     * <p>Each consumer task should read kafka partitions equal to the key group indices it is assigned.
     * The number of kafka partitions is the maximum parallelism of the consumer.
     * This version only supports numberOfPartitions = consumerParallelism.
     * In the case of using {@link TimeCharacteristic#EventTime}, a consumer task is responsible to emit
     * watermarks. Watermarks are read from the corresponding Kafka partitions. Notice that a consumer task only starts
     * to emit a watermark after receiving at least one watermark from each producer task to make sure watermarks
     * are monotonically increasing. Hence a consumer task needs to know `producerParallelism` as well.
     *
     * <p>Attention: make sure kafkaProperties include
     * {@link FlinkKafkaShuffle#PRODUCER_PARALLELISM} and {@link FlinkKafkaShuffle#PARTITION_NUMBER} explicitly.
     * {@link FlinkKafkaShuffle#PRODUCER_PARALLELISM} is the parallelism of the producer.
     * {@link FlinkKafkaShuffle#PARTITION_NUMBER} is the number of partitions.
     * They are not necessarily the same and allowed to be set independently.
     *
     * @see FlinkKafkaShuffle#persistentKeyBy
     * @see FlinkKafkaShuffle#writeKeyBy
     * @param topic
     * 		The topic of Kafka where data is persisted
     * @param env
     * 		Execution environment. readKeyBy's environment can be different from writeKeyBy's
     * @param typeInformation
     * 		Type information of the data persisted in Kafka
     * @param kafkaProperties
     * 		kafka properties for Kafka Consumer
     * @param keySelector
     * 		key selector to retrieve key
     * @param <T>
     * 		Schema type
     * @param <K>
     * 		Key type
     * @return Keyed data stream
     */
    public static <[CtTypeParameterImpl]T, [CtTypeParameterImpl]K> [CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.KeyedStream<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> readKeyBy([CtParameterImpl][CtTypeReferenceImpl]java.lang.String topic, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.environment.StreamExecutionEnvironment env, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.api.common.typeinfo.TypeInformation<[CtTypeParameterReferenceImpl]T> typeInformation, [CtParameterImpl][CtTypeReferenceImpl]java.util.Properties kafkaProperties, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.api.java.functions.KeySelector<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> keySelector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.api.common.typeutils.TypeSerializer<[CtTypeParameterReferenceImpl]T> typeSerializer = [CtInvocationImpl][CtVariableReadImpl]typeInformation.createSerializer([CtInvocationImpl][CtVariableReadImpl]env.getConfig());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.api.common.serialization.TypeInformationSerializationSchema<[CtTypeParameterReferenceImpl]T> schema = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.flink.api.common.serialization.TypeInformationSerializationSchema<>([CtVariableReadImpl]typeInformation, [CtVariableReadImpl]typeSerializer);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.functions.source.SourceFunction<[CtTypeParameterReferenceImpl]T> kafkaConsumer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleConsumer<>([CtVariableReadImpl]topic, [CtVariableReadImpl]schema, [CtVariableReadImpl]typeSerializer, [CtVariableReadImpl]kafkaProperties);
        [CtInvocationImpl][CtCommentImpl]// TODO: consider situations where numberOfPartitions != consumerParallelism
        [CtTypeAccessImpl]org.apache.flink.util.Preconditions.checkArgument([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]kafkaProperties.getProperty([CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PARTITION_NUMBER) != [CtLiteralImpl]null, [CtLiteralImpl]"Missing partition number for Kafka Shuffle");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int numberOfPartitions = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.util.PropertiesUtil.getInt([CtVariableReadImpl]kafkaProperties, [CtFieldReadImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffle.PARTITION_NUMBER, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> outputDataStream = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]env.addSource([CtVariableReadImpl]kafkaConsumer).setParallelism([CtVariableReadImpl]numberOfPartitions);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.streaming.api.datastream.DataStreamUtils.reinterpretAsKeyedStream([CtVariableReadImpl]outputDataStream, [CtVariableReadImpl]keySelector);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds a {@link StreamKafkaShuffleSink} to {@link DataStream}.
     *
     * <p>{@link StreamKafkaShuffleSink} is associated a {@link FlinkKafkaShuffleProducer}.
     *
     * @param inputStream
     * 		Input data stream connected to the shuffle
     * @param kafkaShuffleProducer
     * 		Kafka shuffle sink function that can handle both records and watermark
     * @param producerParallelism
     * 		The number of tasks writing to the kafka shuffle
     */
    private static <[CtTypeParameterImpl]T, [CtTypeParameterImpl]K> [CtTypeReferenceImpl]void addKafkaShuffle([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> inputStream, [CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.FlinkKafkaShuffleProducer<[CtTypeParameterReferenceImpl]T, [CtTypeParameterReferenceImpl]K> kafkaShuffleProducer, [CtParameterImpl][CtTypeReferenceImpl]int producerParallelism) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// read the output type of the input Transform to coax out errors about MissingTypeInfo
        [CtInvocationImpl][CtVariableReadImpl]inputStream.getTransformation().getOutputType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.StreamKafkaShuffleSink<[CtTypeParameterReferenceImpl]T> shuffleSinkOperator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.flink.streaming.connectors.kafka.shuffle.StreamKafkaShuffleSink<>([CtVariableReadImpl]kafkaShuffleProducer);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.transformations.SinkTransformation<[CtTypeParameterReferenceImpl]T> transformation = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.flink.streaming.api.transformations.SinkTransformation<>([CtInvocationImpl][CtVariableReadImpl]inputStream.getTransformation(), [CtLiteralImpl]"kafka_shuffle", [CtVariableReadImpl]shuffleSinkOperator, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputStream.getExecutionEnvironment().getParallelism());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputStream.getExecutionEnvironment().addOperator([CtVariableReadImpl]transformation);
        [CtInvocationImpl][CtVariableReadImpl]transformation.setParallelism([CtVariableReadImpl]producerParallelism);
    }

    [CtMethodImpl][CtCommentImpl]// A better place to put this function is DataStream; but put it here for now to avoid changing DataStream
    private static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]org.apache.flink.api.java.functions.KeySelector<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]org.apache.flink.api.java.tuple.Tuple> keySelector([CtParameterImpl][CtTypeReferenceImpl]org.apache.flink.streaming.api.datastream.DataStream<[CtTypeParameterReferenceImpl]T> source, [CtParameterImpl]int... fields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.api.java.functions.KeySelector<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]org.apache.flink.api.java.tuple.Tuple> keySelector;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]source.getType() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.flink.api.common.typeinfo.BasicArrayTypeInfo) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]source.getType() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.flink.api.common.typeinfo.PrimitiveArrayTypeInfo)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keySelector = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.streaming.util.keys.KeySelectorUtil.getSelectorForArray([CtVariableReadImpl]fields, [CtInvocationImpl][CtVariableReadImpl]source.getType());
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.flink.api.common.operators.Keys<[CtTypeParameterReferenceImpl]T> keys = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.flink.api.common.operators.Keys.ExpressionKeys<>([CtVariableReadImpl]fields, [CtInvocationImpl][CtVariableReadImpl]source.getType());
            [CtAssignmentImpl][CtVariableWriteImpl]keySelector = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.streaming.util.keys.KeySelectorUtil.getSelectorForKeys([CtVariableReadImpl]keys, [CtInvocationImpl][CtVariableReadImpl]source.getType(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]source.getExecutionEnvironment().getConfig());
        }
        [CtReturnImpl]return [CtVariableReadImpl]keySelector;
    }
}
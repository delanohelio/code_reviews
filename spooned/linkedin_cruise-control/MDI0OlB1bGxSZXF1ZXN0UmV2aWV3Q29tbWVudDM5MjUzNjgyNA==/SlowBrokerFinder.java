[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 LinkedIn Corp. Licensed under the BSD 2-Clause License (the "License"). See License in the project root for license information. */
[CtPackageDeclarationImpl]package com.linkedin.kafka.cruisecontrol.detector;
[CtUnresolvedImport]import com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomalyFinder;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_DESCRIPTION_OBJECT_CONFIG;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_BROKER_ENTITIES_OBJECT_CONFIG;
[CtUnresolvedImport]import com.linkedin.kafka.cruisecontrol.monitor.metricdefinition.KafkaMetricDef;
[CtUnresolvedImport]import com.linkedin.cruisecontrol.monitor.sampling.aggregator.AggregatedMetricValues;
[CtUnresolvedImport]import com.linkedin.kafka.cruisecontrol.KafkaCruiseControl;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.KAFKA_CRUISE_CONTROL_OBJECT_CONFIG;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations;
[CtUnresolvedImport]import com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomaly;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_FIXABLE_OBJECT_CONFIG;
[CtUnresolvedImport]import com.linkedin.kafka.cruisecontrol.config.constants.AnomalyDetectorConfig;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.KafkaCruiseControlUtils.toDateString;
[CtUnresolvedImport]import static com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.ANOMALY_DETECTION_TIME_MS_OBJECT_CONFIG;
[CtUnresolvedImport]import org.apache.commons.math3.stat.descriptive.rank.Percentile;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import static com.linkedin.cruisecontrol.detector.metricanomaly.PercentileMetricAnomalyFinderUtils.isDataSufficient;
[CtClassImpl][CtJavaDocImpl]/**
 * This class will check whether there is broker performance degradation (i.e. slow broker) from collected broker metrics.
 *
 * Slow brokers are identified by calculating a derived broker metric
 * {@code BROKER_LOG_FLUSH_TIME_MS_999TH / (LEADER_BYTES_IN + REPLICATION_BYTES_IN_RATE) for each broker and then
 * checking in two ways.
 * <ul>
 *   <li>Comparing the latest metric value against broker's own history. If the latest value is larger than
 *       {@link #_metricHistoryMargin} * ({@link #_metricHistoryPercentile} of historical values), it is
 *       considered to be abnormally high.</li>
 *   <li>Comparing the latest metric value against the latest metric value of all active brokers in cluster (i.e. brokers
 *       which serve non-negligible traffic). If the value is larger than {@link #_peerMetricMargin} * ({@link #_peerMetricPercentile}
 *       of all metric values), it is considered to be abnormally high.</li>
 * </ul>
 *
 * If certain broker's metric value is abnormally high, the broker is marked as a slow broker suspect by the finder.
 * Then if this suspect broker's derived metric anomaly persists for some time, it is confirmed to be a slow broker and
 * the finder will report {@link SlowBrokers}} anomaly with broker demotion as self-healing proposal. If the metric
 * anomaly still persists for an extended time, the finder will eventually report {@link SlowBrokers}} anomaly with broker
 * removal as self-healing proposal.
 *
 * The time to report slow broker for demotion and removal is controlled by an internal broker scoring system.
 * The system keeps a "slowness score" for brokers which have metric anomaly detected recently. The scores are updated in
 * each round of detection with following rules.
 * <ul>
 *   <li> For any broker not in the scoring system, once there is metric anomaly detected on it, the broker is added to the system
 *        with the initial "slowness score" of one. </li>
 *   <li> For any broker in the scoring system, if there is metric anomaly detected on it, its "slowness score" increases
 *        by 1. Once the score exceeds {@link #_slowBrokersDemotionScore}, finder begins to report the broker as slow broker
 *        with broker demotion as self-healing proposal; once the score reaches {@link #_slowBrokersDecommissionScore},
 *        finder begin to report the broker as slow broker with broker removal as self-healing proposal (if
 *        {@link #SELF_HEALING_SLOW_BROKERS_REMOVAL_ENABLED_CONFIG is configed to be true}).</li>
 *   <li> For any broker in the scoring system, if there is no metric anomaly detected on it, its "slowness score" decreases by 1.
 *        Once "slowness score" reaches zero, the broker is dropped from scoring system.</li>
 * </ul>
 *
 * Note: if there are too many brokers being confirmed as slow broker in the same run, the finder will report the {@link SlowBrokers}
 * anomaly as unfixable. Because this often indicates some serious issue in the cluster and probably requires administrator's
 * intervention to decide the right remediation strategy.
 *
 * Required configurations for this class.
 * <ul>
 *   <li>{@link #SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD_CONFIG}: the bytes in rate threshold to determine whether to include broker
 *   in slow broker detection. If the broker only serves negligible traffic, its derived metric wil be abnormally high since
 *   bytes in rate is used as divisor in metric calculation. Default value is set to
 *   {@link #DEFAULT_SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD}.</li>
 *   <li>{@link #SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD_CONFIG}: the percentile threshold used to compare latest metric value against
 *   historical value in slow broker detection. Default value is set to {@link #DEFAULT_SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD}.</li>
 *   <li>{@link #SLOW_BROKERS_METRIC_HISTORY_MARGIN_CONFIG}: the margin used to compare latest metric value against historical value in
 *   slow broker detection. Default value is set to {@link #DEFAULT_SLOW_BROKERS_METRIC_HISTORY_MARGIN}.</li>
 *   <li>{@link #SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD_CONFIG}: the percentile threshold used to compare last metric value against
 *   peers' latest value in slow broker detection. Default value is set to {@link #DEFAULT_SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD}.</li>
 *   <li>{@link #SLOW_BROKERS_PEER_METRIC_MARGIN_CONFIG}: the margin used to compare last metric value against peers' latest value
 *   in slow broker detection. Default value is set to {@link #DEFAULT_SLOW_BROKERS_PEER_METRIC_MARGIN}.</li>
 *   <li>{@link #SLOW_BROKERS_DEMOTION_SCORE_CONFIG}: the score threshold to trigger a demotion for slow broker. Default value is set to
 *   {@link #DEFAULT_SLOW_BROKERS_DEMOTION_SCORE}.</li>
 *   <li>{@link #SLOW_BROKERS_DECOMMISSION_SCORE_CONFIG}: the score threshold to trigger a removal for slow broker. Default value is set to
 *   {@link #DEFAULT_SLOW_BROKERS_DECOMMISSION_SCORE}.</li>
 *   <li>{@link #SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO_CONFIG}: the maximum ratio of slow brokers in the cluster to trigger self-healing
 *   operation. Default value is set to {@link #DEFAULT_SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO}.</li>
 * </ul>
 */
public class SlowBrokerFinder implements [CtTypeReferenceImpl]com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomalyFinder<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.class);

    [CtFieldImpl][CtCommentImpl]// The config to enable finder reporting slow broker anomaly with broker removal as self-healing proposal.
    public static final [CtTypeReferenceImpl]java.lang.String SELF_HEALING_SLOW_BROKERS_REMOVAL_ENABLED_CONFIG = [CtLiteralImpl]"self.healing.slow.brokers.removal.enabled";

    [CtFieldImpl][CtCommentImpl]// The config finder uses to indicate anomaly to perform broker demotion or broker removal for self-healing.
    public static final [CtTypeReferenceImpl]java.lang.String REMOVE_SLOW_BROKERS_CONFIG = [CtLiteralImpl]"remove.slow.brokers";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD_CONFIG = [CtLiteralImpl]"slow.brokers.bytes.in.rate.detection.threshold";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]double DEFAULT_SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD = [CtBinaryOperatorImpl][CtLiteralImpl]1024.0 * [CtLiteralImpl]1024.0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD_CONFIG = [CtLiteralImpl]"slow.brokers.metric.history.percentile.threshold";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]double DEFAULT_SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD = [CtLiteralImpl]90.0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_METRIC_HISTORY_MARGIN_CONFIG = [CtLiteralImpl]"slow.brokers.metric.history.margin";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]double DEFAULT_SLOW_BROKERS_METRIC_HISTORY_MARGIN = [CtLiteralImpl]3.0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD_CONFIG = [CtLiteralImpl]"slow.brokers.peer.metric.percentile.threshold";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]double DEFAULT_SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD = [CtLiteralImpl]50.0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_PEER_METRIC_MARGIN_CONFIG = [CtLiteralImpl]"slow.brokers.peer.metric.margin";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]double DEFAULT_SLOW_BROKERS_PEER_METRIC_MARGIN = [CtLiteralImpl]10.0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_DEMOTION_SCORE_CONFIG = [CtLiteralImpl]"slow.brokers.demotion.score";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_SLOW_BROKERS_DEMOTION_SCORE = [CtLiteralImpl]5;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SLOW_BROKERS_DECOMMISSION_SCORE_CONFIG = [CtLiteralImpl]"slow.brokers.decommission.score";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DEFAULT_SLOW_BROKERS_DECOMMISSION_SCORE = [CtLiteralImpl]50;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO_CONFIG = [CtLiteralImpl]"slow.brokers.self.healing.unfixable.ratio";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]double DEFAULT_SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO = [CtLiteralImpl]0.1;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]short BROKER_LOG_FLUSH_TIME_MS_999TH_ID = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.monitor.metricdefinition.KafkaMetricDef.brokerMetricDef().metricInfo([CtInvocationImpl][CtTypeAccessImpl]KafkaMetricDef.BROKER_LOG_FLUSH_TIME_MS_999TH.name()).id();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]short LEADER_BYTES_IN_ID = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.monitor.metricdefinition.KafkaMetricDef.brokerMetricDef().metricInfo([CtInvocationImpl][CtTypeAccessImpl]KafkaMetricDef.LEADER_BYTES_IN.name()).id();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]short REPLICATION_BYTES_IN_RATE_ID = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.monitor.metricdefinition.KafkaMetricDef.brokerMetricDef().metricInfo([CtInvocationImpl][CtTypeAccessImpl]KafkaMetricDef.REPLICATION_BYTES_IN_RATE.name()).id();

    [CtFieldImpl]private [CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.KafkaCruiseControl _kafkaCruiseControl;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean _slowBrokerRemovalEnabled;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Integer> _brokerSlownessScore;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Long> _detectedSlowBrokers;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.commons.math3.stat.descriptive.rank.Percentile _percentile;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _bytesInRateDetectionThreshold;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _metricHistoryPercentile;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _metricHistoryMargin;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _peerMetricPercentile;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _peerMetricMargin;

    [CtFieldImpl]private [CtTypeReferenceImpl]int _slowBrokersDemotionScore;

    [CtFieldImpl]private [CtTypeReferenceImpl]int _slowBrokersDecommissionScore;

    [CtFieldImpl]private [CtTypeReferenceImpl]double _selfHealingUnfixableRatio;

    [CtConstructorImpl]public SlowBrokerFinder() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_brokerSlownessScore = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl]_detectedSlowBrokers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl]_percentile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.stat.descriptive.rank.Percentile();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectMetricAnomalies([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations> metricsHistoryByBroker, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations> currentMetricsByBroker) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Preprocess raw metrics to get the derived metrics for each broker.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double>> historicalValueByBroker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Double> currentValueByBroker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> skippedBrokers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations> entry : [CtInvocationImpl][CtVariableReadImpl]currentMetricsByBroker.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity entity = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.AggregatedMetricValues aggregatedMetricValues = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getValue().metricValues();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double latestLogFlushTime = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.BROKER_LOG_FLUSH_TIME_MS_999TH_ID).latest();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double latestTotalBytesIn = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LEADER_BYTES_IN_ID).latest() + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.REPLICATION_BYTES_IN_RATE_ID).latest();
            [CtIfImpl][CtCommentImpl]// Ignore brokers which currently serve negligible traffic.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]latestTotalBytesIn >= [CtFieldReadImpl]_bytesInRateDetectionThreshold) && [CtBinaryOperatorImpl]([CtVariableReadImpl]latestLogFlushTime > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]currentValueByBroker.put([CtVariableReadImpl]entity, [CtBinaryOperatorImpl][CtVariableReadImpl]latestLogFlushTime / [CtVariableReadImpl]latestTotalBytesIn);
                [CtAssignmentImpl][CtVariableWriteImpl]aggregatedMetricValues = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]metricsHistoryByBroker.get([CtVariableReadImpl]entity).metricValues();
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] historicalBytesIn = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LEADER_BYTES_IN_ID).doubleArray();
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] historicalReplicationBytesIn = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.REPLICATION_BYTES_IN_RATE_ID).doubleArray();
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] historicalLogFlushTime = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aggregatedMetricValues.valuesFor([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.BROKER_LOG_FLUSH_TIME_MS_999TH_ID).doubleArray();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double> historicalValue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl][CtVariableReadImpl]historicalBytesIn.length);
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]historicalBytesIn.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]double totalBytesIn = [CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]historicalBytesIn[[CtVariableReadImpl]i] + [CtArrayReadImpl][CtVariableReadImpl]historicalReplicationBytesIn[[CtVariableReadImpl]i];
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]totalBytesIn >= [CtFieldReadImpl]_bytesInRateDetectionThreshold) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]historicalValue.add([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]historicalLogFlushTime[[CtVariableReadImpl]i] / [CtVariableReadImpl]totalBytesIn);
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]historicalValueByBroker.put([CtVariableReadImpl]entity, [CtVariableReadImpl]historicalValue);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]skippedBrokers.add([CtInvocationImpl][CtVariableReadImpl]entity.brokerId());
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]skippedBrokers.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LOG.info([CtLiteralImpl]"Skip broker slowness checking for brokers {} because they serve negligible traffic.", [CtVariableReadImpl]skippedBrokers);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtCommentImpl]// Detect metric anomalies by comparing each broker's current derived metric value against historical value.
        detectMetricAnomaliesFromHistory([CtVariableReadImpl]historicalValueByBroker, [CtVariableReadImpl]currentValueByBroker, [CtVariableReadImpl]detectedMetricAnomalies);
        [CtInvocationImpl][CtCommentImpl]// Detect metric anomalies by comparing each broker's derived metric value against its peers' value.
        detectMetricAnomaliesFromPeers([CtVariableReadImpl]currentValueByBroker, [CtVariableReadImpl]detectedMetricAnomalies);
        [CtReturnImpl]return [CtVariableReadImpl]detectedMetricAnomalies;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void detectMetricAnomaliesFromHistory([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double>> historicalValue, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Double> currentValue, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Double> entry : [CtInvocationImpl][CtVariableReadImpl]currentValue.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity entity = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtIfImpl]if ([CtInvocationImpl]isDataSufficient([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]historicalValue.get([CtVariableReadImpl]entity).size(), [CtFieldReadImpl]_metricHistoryPercentile, [CtFieldReadImpl]_metricHistoryPercentile)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] data = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]historicalValue.get([CtVariableReadImpl]entity).stream().mapToDouble([CtLambdaImpl]([CtParameterImpl] i) -> [CtVariableReadImpl]i).toArray();
                [CtInvocationImpl][CtFieldReadImpl]_percentile.setData([CtVariableReadImpl]data);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]currentValue.get([CtVariableReadImpl]entity) > [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]_percentile.evaluate([CtFieldReadImpl]_metricHistoryPercentile) * [CtFieldReadImpl]_metricHistoryMargin)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]detectedMetricAnomalies.add([CtVariableReadImpl]entity);
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void detectMetricAnomaliesFromPeers([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Double> currentValue, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isDataSufficient([CtInvocationImpl][CtVariableReadImpl]currentValue.size(), [CtFieldReadImpl]_peerMetricPercentile, [CtFieldReadImpl]_peerMetricPercentile)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] data = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]currentValue.values().stream().mapToDouble([CtLambdaImpl]([CtParameterImpl] i) -> [CtVariableReadImpl]i).toArray();
            [CtInvocationImpl][CtFieldReadImpl]_percentile.setData([CtVariableReadImpl]data);
            [CtLocalVariableImpl][CtTypeReferenceImpl]double base = [CtInvocationImpl][CtFieldReadImpl]_percentile.evaluate([CtFieldReadImpl]_peerMetricPercentile);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Double> entry : [CtInvocationImpl][CtVariableReadImpl]currentValue.entrySet()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]currentValue.get([CtInvocationImpl][CtVariableReadImpl]entry.getKey()) > [CtBinaryOperatorImpl]([CtVariableReadImpl]base * [CtFieldReadImpl]_peerMetricMargin)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]detectedMetricAnomalies.add([CtInvocationImpl][CtVariableReadImpl]entry.getKey());
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokers createSlowBrokersAnomaly([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Long> detectedBrokers, [CtParameterImpl][CtTypeReferenceImpl]boolean fixable, [CtParameterImpl][CtTypeReferenceImpl]boolean removeSlowBroker, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String description) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> parameterConfigOverrides = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>([CtLiteralImpl]5);
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.KAFKA_CRUISE_CONTROL_OBJECT_CONFIG, [CtFieldReadImpl]_kafkaCruiseControl);
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_DESCRIPTION_OBJECT_CONFIG, [CtVariableReadImpl]description);
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_BROKER_ENTITIES_OBJECT_CONFIG, [CtVariableReadImpl]detectedBrokers);
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.REMOVE_SLOW_BROKERS_CONFIG, [CtVariableReadImpl]removeSlowBroker);
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.ANOMALY_DETECTION_TIME_MS_OBJECT_CONFIG, [CtInvocationImpl][CtFieldReadImpl]_kafkaCruiseControl.timeMs());
        [CtInvocationImpl][CtVariableReadImpl]parameterConfigOverrides.put([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.MetricAnomalyDetector.METRIC_ANOMALY_FIXABLE_OBJECT_CONFIG, [CtVariableReadImpl]fixable);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_kafkaCruiseControl.config().getConfiguredInstance([CtTypeAccessImpl]AnomalyDetectorConfig.METRIC_ANOMALY_CLASS_CONFIG, [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokers.class, [CtVariableReadImpl]parameterConfigOverrides);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getSlowBrokerDescription([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Long> detectedBrokers) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder descriptionSb = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder().append([CtLiteralImpl]"{");
        [CtInvocationImpl][CtVariableReadImpl]detectedBrokers.forEach([CtLambdaImpl]([CtParameterImpl] key,[CtParameterImpl] value) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]descriptionSb.append([CtLiteralImpl]"Broker ").append([CtInvocationImpl][CtVariableReadImpl]key.brokerId()).append([CtLiteralImpl]"'s performance degraded at ").append([CtInvocationImpl]toDateString([CtVariableReadImpl]value)).append([CtLiteralImpl]", ");
        });
        [CtInvocationImpl][CtVariableReadImpl]descriptionSb.setLength([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]descriptionSb.length() - [CtLiteralImpl]2);
        [CtInvocationImpl][CtVariableReadImpl]descriptionSb.append([CtLiteralImpl]"}");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]descriptionSb.toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomaly<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity>> metricAnomalies([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations> metricsHistoryByBroker, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]com.linkedin.cruisecontrol.monitor.sampling.aggregator.ValuesAndExtrapolations> currentMetricsByBroker) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LOG.info([CtLiteralImpl]"Slow broker detection started.");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies = [CtInvocationImpl]detectMetricAnomalies([CtVariableReadImpl]metricsHistoryByBroker, [CtVariableReadImpl]currentMetricsByBroker);
            [CtInvocationImpl]updateBrokerSlownessScore([CtVariableReadImpl]detectedMetricAnomalies);
            [CtReturnImpl]return [CtInvocationImpl]createSlowBrokerAnomalies([CtVariableReadImpl]detectedMetricAnomalies, [CtInvocationImpl][CtVariableReadImpl]metricsHistoryByBroker.size());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LOG.warn([CtLiteralImpl]"Slow broker detector encountered exception: ", [CtVariableReadImpl]e);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.LOG.info([CtLiteralImpl]"Slow broker detection finished.");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptySet();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateBrokerSlownessScore([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity broker : [CtVariableReadImpl]detectedMetricAnomalies) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Update slow broker detection time and slowness score.
            [CtTypeReferenceImpl]java.lang.Long currentTimeMs = [CtInvocationImpl][CtFieldReadImpl]_kafkaCruiseControl.timeMs();
            [CtInvocationImpl][CtFieldReadImpl]_detectedSlowBrokers.putIfAbsent([CtVariableReadImpl]broker, [CtVariableReadImpl]currentTimeMs);
            [CtInvocationImpl][CtFieldReadImpl]_brokerSlownessScore.compute([CtVariableReadImpl]broker, [CtLambdaImpl]([CtParameterImpl] k,[CtParameterImpl] v) -> [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]v == [CtLiteralImpl]null ? [CtLiteralImpl]1 : [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtBinaryOperatorImpl][CtVariableReadImpl]v + [CtLiteralImpl]1, [CtFieldReadImpl][CtFieldReferenceImpl]_slowBrokersDecommissionScore));
        }
        [CtLocalVariableImpl][CtCommentImpl]// For brokers which are previously detected as slow brokers, decrease their slowness score if their metrics has
        [CtCommentImpl]// recovered back to normal range.
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> brokersRecovered = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Integer> entry : [CtInvocationImpl][CtFieldReadImpl]_brokerSlownessScore.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity broker = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]detectedMetricAnomalies.contains([CtVariableReadImpl]broker)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer score = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]score != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](--[CtVariableWriteImpl]score) == [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]brokersRecovered.add([CtVariableReadImpl]broker);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]entry.setValue([CtVariableReadImpl]score);
                }
            }
        }
        [CtForEachImpl][CtCommentImpl]// If the broker has recovered, remove its suspicion.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity broker : [CtVariableReadImpl]brokersRecovered) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]_brokerSlownessScore.remove([CtVariableReadImpl]broker);
            [CtInvocationImpl][CtFieldReadImpl]_detectedSlowBrokers.remove([CtVariableReadImpl]broker);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomaly<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity>> createSlowBrokerAnomalies([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity> detectedMetricAnomalies, [CtParameterImpl][CtTypeReferenceImpl]int clusterSize) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.linkedin.cruisecontrol.detector.metricanomaly.MetricAnomaly<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity>> detectedSlowBrokers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Long> brokersToDemote = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity, [CtTypeReferenceImpl]java.lang.Long> brokersToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.monitor.sampling.holder.BrokerEntity broker : [CtVariableReadImpl]detectedMetricAnomalies) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Report anomaly if slowness score reaches threshold for broker decommission/demotion.
            [CtTypeReferenceImpl]int slownessScore = [CtInvocationImpl][CtFieldReadImpl]_brokerSlownessScore.get([CtVariableReadImpl]broker);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slownessScore == [CtFieldReadImpl]_slowBrokersDecommissionScore) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]brokersToRemove.put([CtVariableReadImpl]broker, [CtInvocationImpl][CtFieldReadImpl]_detectedSlowBrokers.get([CtVariableReadImpl]broker));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]slownessScore >= [CtFieldReadImpl]_slowBrokersDemotionScore) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]brokersToDemote.put([CtVariableReadImpl]broker, [CtInvocationImpl][CtFieldReadImpl]_detectedSlowBrokers.get([CtVariableReadImpl]broker));
            }
        }
        [CtIfImpl][CtCommentImpl]// If too many brokers in the cluster are detected as slow brokers, report anomaly as not fixable.
        [CtCommentImpl]// Otherwise report anomaly with brokers to be removed/demoted.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]brokersToDemote.size() + [CtInvocationImpl][CtVariableReadImpl]brokersToRemove.size()) > [CtBinaryOperatorImpl]([CtVariableReadImpl]clusterSize * [CtFieldReadImpl]_selfHealingUnfixableRatio)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]brokersToRemove.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]brokersToDemote::put);
            [CtInvocationImpl][CtVariableReadImpl]detectedSlowBrokers.add([CtInvocationImpl]createSlowBrokersAnomaly([CtVariableReadImpl]brokersToDemote, [CtLiteralImpl]false, [CtLiteralImpl]false, [CtInvocationImpl]getSlowBrokerDescription([CtVariableReadImpl]brokersToDemote)));
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]brokersToDemote.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]detectedSlowBrokers.add([CtInvocationImpl]createSlowBrokersAnomaly([CtVariableReadImpl]brokersToDemote, [CtLiteralImpl]true, [CtLiteralImpl]false, [CtInvocationImpl]getSlowBrokerDescription([CtVariableReadImpl]brokersToDemote)));
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]brokersToRemove.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]detectedSlowBrokers.add([CtInvocationImpl]createSlowBrokersAnomaly([CtVariableReadImpl]brokersToRemove, [CtFieldReadImpl]_slowBrokerRemovalEnabled, [CtLiteralImpl]true, [CtInvocationImpl]getSlowBrokerDescription([CtVariableReadImpl]brokersToRemove)));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]detectedSlowBrokers;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void configure([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtWildcardReferenceImpl]?> configs) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_kafkaCruiseControl = [CtInvocationImpl](([CtTypeReferenceImpl]com.linkedin.kafka.cruisecontrol.KafkaCruiseControl) ([CtVariableReadImpl]configs.get([CtTypeAccessImpl]com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.KAFKA_CRUISE_CONTROL_OBJECT_CONFIG)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_kafkaCruiseControl == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Slow broker detector is missing " + [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.AnomalyDetectorUtils.KAFKA_CRUISE_CONTROL_OBJECT_CONFIG);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Config for slow broker removal.
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> originalConfig = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]_kafkaCruiseControl.config().originals();
        [CtAssignmentImpl][CtFieldWriteImpl]_slowBrokerRemovalEnabled = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SELF_HEALING_SLOW_BROKERS_REMOVAL_ENABLED_CONFIG))));
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_bytesInRateDetectionThreshold = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_bytesInRateDetectionThreshold < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should not be set to negative.", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_bytesInRateDetectionThreshold = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_BYTES_IN_RATE_DETECTION_THRESHOLD;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_metricHistoryPercentile = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_metricHistoryPercentile < [CtLiteralImpl]0.0) || [CtBinaryOperatorImpl]([CtFieldReadImpl]_metricHistoryPercentile > [CtLiteralImpl]100.0)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should be set in range [0.0, 100.0].", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_metricHistoryPercentile = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_METRIC_HISTORY_PERCENTILE_THRESHOLD;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_metricHistoryMargin = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_METRIC_HISTORY_MARGIN_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_metricHistoryMargin < [CtLiteralImpl]1.0) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should not be less than 1.0.", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_METRIC_HISTORY_MARGIN_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_metricHistoryMargin = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_METRIC_HISTORY_MARGIN;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_peerMetricPercentile = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_peerMetricPercentile < [CtLiteralImpl]0.0) || [CtBinaryOperatorImpl]([CtFieldReadImpl]_peerMetricPercentile > [CtLiteralImpl]100.0)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should be set in range [0.0, 100.0].", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_peerMetricPercentile = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_PEER_METRIC_PERCENTILE_THRESHOLD;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_peerMetricMargin = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_PEER_METRIC_MARGIN_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_peerMetricMargin < [CtLiteralImpl]1.0) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should not be less than 1.0", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_PEER_METRIC_MARGIN_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_peerMetricMargin = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_PEER_METRIC_MARGIN;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_slowBrokersDemotionScore = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseUnsignedInt([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_DEMOTION_SCORE_CONFIG))));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_slowBrokersDemotionScore = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_DEMOTION_SCORE;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_slowBrokersDecommissionScore = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseUnsignedInt([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SLOW_BROKERS_DECOMMISSION_SCORE_CONFIG))));
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_slowBrokersDecommissionScore = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SLOW_BROKERS_DECOMMISSION_SCORE;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_selfHealingUnfixableRatio = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Double.parseDouble([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]originalConfig.get([CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO_CONFIG))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_selfHealingUnfixableRatio < [CtLiteralImpl]0.0) || [CtBinaryOperatorImpl]([CtFieldReadImpl]_selfHealingUnfixableRatio > [CtLiteralImpl]1.0)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s config of slow broker finder should be set in range [0.0, 1.0].", [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO_CONFIG));
            }
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.NumberFormatException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_selfHealingUnfixableRatio = [CtFieldReadImpl]com.linkedin.kafka.cruisecontrol.detector.SlowBrokerFinder.DEFAULT_SlOW_BROKERS_SELF_HEALING_UNFIXABLE_RATIO;
        }
    }
}
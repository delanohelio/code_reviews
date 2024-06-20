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
[CtPackageDeclarationImpl]package org.apache.pinot.thirdeye.detection.components;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.Series;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.model.InputDataSpec;
[CtUnresolvedImport]import org.joda.time.DateTimeZone;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.components.BaselineProvider;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.components.AnomalyDetector;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.util.MetricSlice;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.datalayer.dto.MetricConfigDTO;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.Pattern;
[CtUnresolvedImport]import static org.apache.pinot.thirdeye.dataframe.util.DataFrameUtils.*;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.model.InputData;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.LongSeries;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.algorithm.AlgorithmUtils;
[CtUnresolvedImport]import org.joda.time.Interval;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.DataFrame;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.BooleanSeries;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.annotation.Components;
[CtUnresolvedImport]import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
[CtUnresolvedImport]import org.apache.commons.math3.optim.InitialGuess;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.rootcause.impl.MetricEntity;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.InputDataFetcher;
[CtUnresolvedImport]import org.joda.time.Period;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.DetectionUtils;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.datalayer.dto.MergedAnomalyResultDTO;
[CtUnresolvedImport]import org.joda.time.DateTime;
[CtUnresolvedImport]import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
[CtUnresolvedImport]import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
[CtUnresolvedImport]import org.apache.commons.math3.optim.PointValuePair;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.annotation.DetectionTag;
[CtImportImpl]import java.time.DayOfWeek;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.dataframe.DoubleSeries;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.model.DetectionResult;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spec.HoltWintersDetectorSpec;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.annotation.Param;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.datalayer.dto.DatasetConfigDTO;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.detection.spi.model.TimeSeries;
[CtUnresolvedImport]import org.apache.commons.math3.analysis.MultivariateFunction;
[CtUnresolvedImport]import org.apache.pinot.thirdeye.common.time.TimeGranularity;
[CtUnresolvedImport]import org.apache.commons.math3.optim.MaxEval;
[CtUnresolvedImport]import org.apache.commons.math3.optim.MaxIter;
[CtUnresolvedImport]import org.apache.commons.math3.optim.SimpleBounds;
[CtClassImpl][CtJavaDocImpl]/**
 * Holt-Winters forecasting algorithm with multiplicative method
 * Supports seasonality and trend detection
 * https://otexts.com/fpp2/holt-winters.html
 */
[CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Components(title = [CtLiteralImpl]"Holt Winters triple exponential smoothing forecasting and detection", type = [CtLiteralImpl]"HOLT_WINTERS_RULE", tags = [CtNewArrayImpl]{ [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.annotation.DetectionTag.RULE_DETECTION }, description = [CtLiteralImpl]"Forecast with holt winters triple exponential smoothing and generate anomalies", params = [CtNewArrayImpl]{ [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"alpha"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"beta"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"gamma"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"period"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"pattern"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"sensitivity"), [CtAnnotationImpl]@org.apache.pinot.thirdeye.detection.annotation.Param(name = [CtLiteralImpl]"kernelSmoothing") })
public class HoltWintersDetector implements [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.components.BaselineProvider<[CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spec.HoltWintersDetectorSpec> , [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.components.AnomalyDetector<[CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spec.HoltWintersDetectorSpec> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.class);

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.InputDataFetcher dataFetcher;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_CURR = [CtLiteralImpl]"current";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_ANOMALY = [CtLiteralImpl]"anomaly";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_PATTERN = [CtLiteralImpl]"pattern";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_DIFF = [CtLiteralImpl]"diff";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_DIFF_VIOLATION = [CtLiteralImpl]"diff_violation";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COL_ERROR = [CtLiteralImpl]"error";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long KERNEL_PERIOD = [CtLiteralImpl]3600000L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int LOOKBACK = [CtLiteralImpl]60;

    [CtFieldImpl]private [CtTypeReferenceImpl]int period;

    [CtFieldImpl]private [CtTypeReferenceImpl]double alpha;

    [CtFieldImpl]private [CtTypeReferenceImpl]double beta;

    [CtFieldImpl]private [CtTypeReferenceImpl]double gamma;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.Pattern pattern;

    [CtFieldImpl]private [CtTypeReferenceImpl]double sensitivity;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean smoothing;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String monitoringGranularity;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.common.time.TimeGranularity timeGranularity;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.DayOfWeek weekStart;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void init([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spec.HoltWintersDetectorSpec spec, [CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.InputDataFetcher dataFetcher) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.period = [CtInvocationImpl][CtVariableReadImpl]spec.getPeriod();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.alpha = [CtInvocationImpl][CtVariableReadImpl]spec.getAlpha();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.beta = [CtInvocationImpl][CtVariableReadImpl]spec.getBeta();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gamma = [CtInvocationImpl][CtVariableReadImpl]spec.getGamma();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataFetcher = [CtVariableReadImpl]dataFetcher;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pattern = [CtInvocationImpl][CtVariableReadImpl]spec.getPattern();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.smoothing = [CtInvocationImpl][CtVariableReadImpl]spec.getSmoothing();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sensitivity = [CtInvocationImpl][CtVariableReadImpl]spec.getSensitivity();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.monitoringGranularity = [CtInvocationImpl][CtVariableReadImpl]spec.getMonitoringGranularity();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"MONTHS") || [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"WEEKS")) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timeGranularity = [CtFieldReadImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice.NATIVE_GRANULARITY;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timeGranularity = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.common.time.TimeGranularity.fromString([CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"WEEKS")) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.weekStart = [CtInvocationImpl][CtTypeAccessImpl]java.time.DayOfWeek.valueOf([CtInvocationImpl][CtVariableReadImpl]spec.getWeekStart());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.TimeSeries computePredictedTimeSeries([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice slice) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.rootcause.impl.MetricEntity metricEntity = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.rootcause.impl.MetricEntity.fromSlice([CtVariableReadImpl]slice, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.Interval window = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.joda.time.Interval([CtInvocationImpl][CtVariableReadImpl]slice.getStart(), [CtInvocationImpl][CtVariableReadImpl]slice.getEnd());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime trainStart = [CtInvocationImpl]getTrainingStartTime([CtVariableReadImpl]window);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.datalayer.dto.DatasetConfigDTO datasetConfig = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.dataFetcher.fetchData([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.InputDataSpec().withMetricIdsForDataset([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singleton([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId()))).getDatasetForMetricId().get([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame inputDf = [CtInvocationImpl]fetchData([CtVariableReadImpl]metricEntity, [CtInvocationImpl][CtVariableReadImpl]trainStart.getMillis(), [CtInvocationImpl][CtVariableReadImpl]window.getEndMillis(), [CtVariableReadImpl]datasetConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame resultDF = [CtInvocationImpl]computePredictionInterval([CtVariableReadImpl]inputDf, [CtInvocationImpl][CtVariableReadImpl]window.getStartMillis(), [CtInvocationImpl][CtVariableReadImpl]datasetConfig.getTimezone());
        [CtAssignmentImpl][CtVariableWriteImpl]resultDF = [CtInvocationImpl][CtVariableReadImpl]resultDF.joinLeft([CtInvocationImpl][CtVariableReadImpl]inputDf.renameSeries([CtTypeAccessImpl]COL_VALUE, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_CURR), [CtTypeAccessImpl]COL_TIME);
        [CtIfImpl][CtCommentImpl]// Exclude the end because baseline calculation should not contain the end
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]resultDF.size() > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resultDF = [CtInvocationImpl][CtVariableReadImpl]resultDF.head([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]resultDF.size() - [CtLiteralImpl]1);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.spi.model.TimeSeries.fromDataFrame([CtVariableReadImpl]resultDF);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.joda.time.DateTime getTrainingStartTime([CtParameterImpl][CtTypeReferenceImpl]org.joda.time.Interval window) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime trainStart;
        [CtIfImpl]if ([CtInvocationImpl]isMultiDayGranularity()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]trainStart = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]window.getStart().minusDays([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]timeGranularity.getSize() * [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK);
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"MONTHS")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]trainStart = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]window.getStart().minusMonths([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK);
        } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"WEEKS")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]trainStart = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]window.getStart().withDayOfWeek([CtInvocationImpl][CtFieldReadImpl]weekStart.getValue()).minusWeeks([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]trainStart = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]window.getStart().minusDays([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK);
        }
        [CtReturnImpl]return [CtVariableReadImpl]trainStart;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.DetectionResult runDetection([CtParameterImpl][CtTypeReferenceImpl]org.joda.time.Interval window, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String metricUrn) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.rootcause.impl.MetricEntity metricEntity = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.rootcause.impl.MetricEntity.fromURN([CtVariableReadImpl]metricUrn);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime trainStart = [CtInvocationImpl]getTrainingStartTime([CtVariableReadImpl]window);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.datalayer.dto.DatasetConfigDTO datasetConfig = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.dataFetcher.fetchData([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.InputDataSpec().withMetricIdsForDataset([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singleton([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId()))).getDatasetForMetricId().get([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice sliceData = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice.from([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId(), [CtInvocationImpl][CtVariableReadImpl]trainStart.getMillis(), [CtInvocationImpl][CtVariableReadImpl]window.getEndMillis(), [CtInvocationImpl][CtVariableReadImpl]metricEntity.getFilters());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame dfInput = [CtInvocationImpl]fetchData([CtVariableReadImpl]metricEntity, [CtInvocationImpl][CtVariableReadImpl]trainStart.getMillis(), [CtInvocationImpl][CtVariableReadImpl]window.getEndMillis(), [CtVariableReadImpl]datasetConfig);
        [CtIfImpl][CtCommentImpl]// Kernel smoothing
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]smoothing && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]DAYS.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetConfig.bucketTimeGranularity().getUnit()))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int kernelSize = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.KERNEL_PERIOD / [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]datasetConfig.bucketTimeGranularity().toMillis()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]kernelSize > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int kernelOffset = [CtBinaryOperatorImpl][CtVariableReadImpl]kernelSize / [CtLiteralImpl]2;
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] values = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dfInput.getDoubles([CtTypeAccessImpl]COL_VALUE).values();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i <= [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]values.length - [CtVariableReadImpl]kernelSize); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]values[[CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtVariableReadImpl]kernelOffset] = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.algorithm.AlgorithmUtils.robustMean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dfInput.getDoubles([CtTypeAccessImpl]COL_VALUE).slice([CtVariableReadImpl]i, [CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtVariableReadImpl]kernelSize), [CtVariableReadImpl]kernelSize).getDouble([CtBinaryOperatorImpl][CtVariableReadImpl]kernelSize - [CtLiteralImpl]1);
                }
                [CtInvocationImpl][CtVariableReadImpl]dfInput.addSeries([CtTypeAccessImpl]COL_VALUE, [CtVariableReadImpl]values);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame dfCurr = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame([CtVariableReadImpl]dfInput).renameSeries([CtTypeAccessImpl]COL_VALUE, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_CURR);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame dfBase = [CtInvocationImpl]computePredictionInterval([CtVariableReadImpl]dfInput, [CtInvocationImpl][CtVariableReadImpl]window.getStartMillis(), [CtInvocationImpl][CtVariableReadImpl]datasetConfig.getTimezone());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame df = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame([CtVariableReadImpl]dfCurr).addSeries([CtVariableReadImpl]dfBase, [CtTypeAccessImpl]COL_VALUE, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ERROR);
        [CtInvocationImpl][CtVariableReadImpl]df.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getDoubles([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_CURR).subtract([CtInvocationImpl][CtVariableReadImpl]df.get([CtTypeAccessImpl]COL_VALUE)));
        [CtInvocationImpl][CtVariableReadImpl]df.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ANOMALY, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.BooleanSeries.fillValues([CtInvocationImpl][CtVariableReadImpl]df.size(), [CtLiteralImpl]false));
        [CtIfImpl][CtCommentImpl]// Filter pattern
        if ([CtInvocationImpl][CtFieldReadImpl]pattern.equals([CtTypeAccessImpl]Pattern.UP_OR_DOWN)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]df.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_PATTERN, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.BooleanSeries.fillValues([CtInvocationImpl][CtVariableReadImpl]df.size(), [CtLiteralImpl]true));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]df.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_PATTERN, [CtConditionalImpl][CtInvocationImpl][CtFieldReadImpl]pattern.equals([CtTypeAccessImpl]Pattern.UP) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getDoubles([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF).gt([CtLiteralImpl]0) : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getDoubles([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF).lt([CtLiteralImpl]0));
        }
        [CtInvocationImpl][CtVariableReadImpl]df.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF_VIOLATION, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]df.getDoubles([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF).abs().gte([CtInvocationImpl][CtVariableReadImpl]df.getDoubles([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ERROR)));
        [CtInvocationImpl][CtVariableReadImpl]df.mapInPlace([CtTypeAccessImpl]BooleanSeries.ALL_TRUE, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ANOMALY, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_PATTERN, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_DIFF_VIOLATION);
        [CtLocalVariableImpl][CtCommentImpl]// Anomalies
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.pinot.thirdeye.datalayer.dto.MergedAnomalyResultDTO> anomalyResults = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.DetectionUtils.makeAnomalies([CtVariableReadImpl]sliceData, [CtVariableReadImpl]df, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ANOMALY, [CtInvocationImpl][CtVariableReadImpl]window.getEndMillis(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.DetectionUtils.getMonitoringGranularityPeriod([CtInvocationImpl][CtFieldReadImpl]timeGranularity.toAggregationGranularityString(), [CtVariableReadImpl]datasetConfig), [CtVariableReadImpl]datasetConfig);
        [CtAssignmentImpl][CtVariableWriteImpl]dfBase = [CtInvocationImpl][CtVariableReadImpl]dfBase.joinRight([CtInvocationImpl][CtVariableReadImpl]df.retainSeries([CtTypeAccessImpl]COL_TIME, [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_CURR), [CtTypeAccessImpl]COL_TIME);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.spi.model.DetectionResult.from([CtVariableReadImpl]anomalyResults, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.spi.model.TimeSeries.fromDataFrame([CtVariableReadImpl]dfBase));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch data from metric
     *
     * @param metricEntity
     * 		metric entity
     * @param start
     * 		start timestamp
     * @param end
     * 		end timestamp
     * @param datasetConfig
     * 		the dataset config
     * @return Data Frame that has data from start to end
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame fetchData([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.rootcause.impl.MetricEntity metricEntity, [CtParameterImpl][CtTypeReferenceImpl]long start, [CtParameterImpl][CtTypeReferenceImpl]long end, [CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.datalayer.dto.DatasetConfigDTO datasetConfig) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice> slices = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice sliceData = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.util.MetricSlice.from([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId(), [CtVariableReadImpl]start, [CtVariableReadImpl]end, [CtInvocationImpl][CtVariableReadImpl]metricEntity.getFilters(), [CtFieldReadImpl]timeGranularity);
        [CtInvocationImpl][CtVariableReadImpl]slices.add([CtVariableReadImpl]sliceData);
        [CtInvocationImpl][CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOG.info([CtBinaryOperatorImpl][CtLiteralImpl]"Getting data for" + [CtInvocationImpl][CtVariableReadImpl]sliceData.toString());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.InputData data = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.dataFetcher.fetchData([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.spi.model.InputDataSpec().withTimeseriesSlices([CtVariableReadImpl]slices).withMetricIdsForDataset([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId())).withMetricIds([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId())));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.datalayer.dto.MetricConfigDTO metricConfig = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]data.getMetrics().get([CtInvocationImpl][CtVariableReadImpl]metricEntity.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame df = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]data.getTimeseries().get([CtVariableReadImpl]sliceData);
        [CtIfImpl][CtCommentImpl]// aggregate data to specified weekly granularity
        if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"WEEKS")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.Period monitoringGranularityPeriod = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.DetectionUtils.getMonitoringGranularityPeriod([CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity, [CtVariableReadImpl]datasetConfig);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long latestDataTimeStamp = [CtInvocationImpl][CtVariableReadImpl]df.getLong([CtTypeAccessImpl]COL_TIME, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]df.size() - [CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.DetectionUtils.aggregateByPeriod([CtVariableReadImpl]df, [CtVariableReadImpl]start, [CtVariableReadImpl]monitoringGranularityPeriod, [CtInvocationImpl][CtVariableReadImpl]metricConfig.getDefaultAggFunction());
            [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.detection.DetectionUtils.filterIncompleteAggregation([CtVariableReadImpl]df, [CtVariableReadImpl]latestDataTimeStamp, [CtInvocationImpl][CtVariableReadImpl]datasetConfig.bucketTimeGranularity(), [CtVariableReadImpl]monitoringGranularityPeriod);
        }
        [CtReturnImpl]return [CtVariableReadImpl]df;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a data frame containing lookback number of data before prediction time
     *
     * @param originalDF
     * 		the original dataframe
     * @param time
     * 		the prediction time, in unix timestamp
     * @return DataFrame containing lookback number of data
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame getLookbackDF([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame originalDF, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long time) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.LongSeries longSeries = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.LongSeries) ([CtVariableReadImpl]originalDF.get([CtTypeAccessImpl]COL_TIME)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int indexFinish = [CtInvocationImpl][CtVariableReadImpl]longSeries.find([CtVariableReadImpl]time);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame df = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DataFrame.builder([CtTypeAccessImpl]COL_TIME, [CtTypeAccessImpl]COL_VALUE).build();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]indexFinish != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int indexStart = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]indexFinish - [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK);
            [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtVariableReadImpl]df.append([CtInvocationImpl][CtVariableReadImpl]originalDF.slice([CtVariableReadImpl]indexStart, [CtVariableReadImpl]indexFinish));
        }
        [CtReturnImpl]return [CtVariableReadImpl]df;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a data frame containing the same time daily data, based on input time
     *
     * @param originalDF
     * 		the original dataframe
     * @param time
     * 		the prediction time, in unix timestamp
     * @return DataFrame containing same time of daily data for LOOKBACK number of days
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame getDailyDF([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame originalDF, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long time, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String timezone) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.LongSeries longSeries = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.LongSeries) ([CtVariableReadImpl]originalDF.get([CtTypeAccessImpl]COL_TIME)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]long start = [CtInvocationImpl][CtVariableReadImpl]longSeries.getLong([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime dt = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.joda.time.DateTime([CtVariableReadImpl]time).withZone([CtInvocationImpl][CtTypeAccessImpl]org.joda.time.DateTimeZone.forID([CtVariableReadImpl]timezone));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame df = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DataFrame.builder([CtTypeAccessImpl]COL_TIME, [CtTypeAccessImpl]COL_VALUE).build();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOOKBACK; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime subDt = [CtInvocationImpl][CtVariableReadImpl]dt.minusDays([CtLiteralImpl]1);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long t = [CtInvocationImpl][CtVariableReadImpl]subDt.getMillis();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t < [CtVariableReadImpl]start) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl][CtVariableReadImpl]longSeries.find([CtVariableReadImpl]t);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtVariableReadImpl]df.append([CtInvocationImpl][CtVariableReadImpl]originalDF.slice([CtVariableReadImpl]index, [CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtLiteralImpl]1));
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int backtrackCounter = [CtLiteralImpl]0;
                [CtWhileImpl][CtCommentImpl]// If the 1 day look back data doesn't exist, use the data one period before till backtrackCounter greater than 4
                while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]index == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]backtrackCounter <= [CtLiteralImpl]4)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]subDt = [CtInvocationImpl][CtVariableReadImpl]subDt.minusDays([CtFieldReadImpl]period);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long timestamp = [CtInvocationImpl][CtVariableReadImpl]subDt.getMillis();
                    [CtAssignmentImpl][CtVariableWriteImpl]index = [CtInvocationImpl][CtVariableReadImpl]longSeries.find([CtVariableReadImpl]timestamp);
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]backtrackCounter++;
                } 
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtVariableReadImpl]df.append([CtInvocationImpl][CtVariableReadImpl]originalDF.slice([CtVariableReadImpl]index, [CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtLiteralImpl]1));
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// If not found value up to 4 weeks, insert the last value
                    [CtTypeReferenceImpl]double lastVal = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]originalDF.get([CtTypeAccessImpl]COL_VALUE).getDouble([CtInvocationImpl][CtVariableReadImpl]longSeries.find([CtInvocationImpl][CtVariableReadImpl]dt.getMillis()));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.joda.time.DateTime nextDt = [CtInvocationImpl][CtVariableReadImpl]dt.minusDays([CtLiteralImpl]1);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame appendDf = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DataFrame.builder([CtTypeAccessImpl]COL_TIME, [CtTypeAccessImpl]COL_VALUE).append([CtVariableReadImpl]nextDt, [CtVariableReadImpl]lastVal).build();
                    [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtVariableReadImpl]df.append([CtVariableReadImpl]appendDf);
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]dt = [CtInvocationImpl][CtVariableReadImpl]dt.minusDays([CtLiteralImpl]1);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]df = [CtInvocationImpl][CtVariableReadImpl]df.reverse();
        [CtReturnImpl]return [CtVariableReadImpl]df;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]double calculateInitialLevel([CtParameterImpl][CtArrayTypeReferenceImpl]double[] y) [CtBlockImpl]{
        [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]y[[CtLiteralImpl]0];
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * See: http://www.itl.nist.gov/div898/handbook/pmc/section4/pmc435.htm
     *
     * @return - Initial trend - Bt[1]
     */
    private static [CtTypeReferenceImpl]double calculateInitialTrend([CtParameterImpl][CtArrayTypeReferenceImpl]double[] y, [CtParameterImpl][CtTypeReferenceImpl]int period) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]double sum = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]period; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]sum += [CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]y[[CtBinaryOperatorImpl][CtVariableReadImpl]period + [CtVariableReadImpl]i] - [CtArrayReadImpl][CtVariableReadImpl]y[[CtVariableReadImpl]i];
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]sum / [CtBinaryOperatorImpl]([CtVariableReadImpl]period * [CtVariableReadImpl]period);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * See: http://www.itl.nist.gov/div898/handbook/pmc/section4/pmc435.htm
     *
     * @return - Seasonal Indices.
     */
    private static [CtArrayTypeReferenceImpl]double[] calculateSeasonalIndices([CtParameterImpl][CtArrayTypeReferenceImpl]double[] y, [CtParameterImpl][CtTypeReferenceImpl]int period, [CtParameterImpl][CtTypeReferenceImpl]int seasons) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] seasonalMean = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]seasons];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] seasonalIndices = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]period];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] averagedObservations = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtFieldReadImpl][CtVariableReadImpl]y.length];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]seasons; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtVariableReadImpl]period; [CtUnaryOperatorImpl][CtVariableWriteImpl]j++) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonalMean[[CtVariableReadImpl]i] += [CtArrayReadImpl][CtVariableReadImpl]y[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i * [CtVariableReadImpl]period) + [CtVariableReadImpl]j];
            }
            [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonalMean[[CtVariableReadImpl]i] /= [CtVariableReadImpl]period;
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]seasons; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtVariableReadImpl]period; [CtUnaryOperatorImpl][CtVariableWriteImpl]j++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]averagedObservations[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i * [CtVariableReadImpl]period) + [CtVariableReadImpl]j] = [CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]y[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i * [CtVariableReadImpl]period) + [CtVariableReadImpl]j] / [CtArrayReadImpl][CtVariableReadImpl]seasonalMean[[CtVariableReadImpl]i];
            }
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]period; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtVariableReadImpl]seasons; [CtUnaryOperatorImpl][CtVariableWriteImpl]j++) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonalIndices[[CtVariableReadImpl]i] += [CtArrayReadImpl][CtVariableReadImpl]averagedObservations[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]j * [CtVariableReadImpl]period) + [CtVariableReadImpl]i];
            }
            [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonalIndices[[CtVariableReadImpl]i] /= [CtVariableReadImpl]seasons;
        }
        [CtReturnImpl]return [CtVariableReadImpl]seasonalIndices;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Holt Winters forecasting method
     *
     * @param y
     * 		Timeseries to be forecasted
     * @param alpha
     * 		level smoothing factor
     * @param beta
     * 		trend smoothing factor
     * @param gamma
     * 		seasonality smoothing factor
     * @return ForecastResults containing predicted value, SSE(sum of squared error) and error bound
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.ForecastResults forecast([CtParameterImpl][CtArrayTypeReferenceImpl]double[] y, [CtParameterImpl][CtTypeReferenceImpl]double alpha, [CtParameterImpl][CtTypeReferenceImpl]double beta, [CtParameterImpl][CtTypeReferenceImpl]double gamma) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] seasonal = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]y.length + [CtLiteralImpl]1];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] forecast = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]y.length + [CtLiteralImpl]1];
        [CtLocalVariableImpl][CtTypeReferenceImpl]double a0 = [CtInvocationImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.calculateInitialLevel([CtVariableReadImpl]y);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double b0 = [CtInvocationImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.calculateInitialTrend([CtVariableReadImpl]y, [CtFieldReadImpl]period);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int seasons = [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]y.length / [CtFieldReadImpl]period;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] initialSeasonalIndices = [CtInvocationImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.calculateSeasonalIndices([CtVariableReadImpl]y, [CtFieldReadImpl]period, [CtVariableReadImpl]seasons);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]period; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonal[[CtVariableReadImpl]i] = [CtArrayReadImpl][CtVariableReadImpl]initialSeasonalIndices[[CtVariableReadImpl]i];
        }
        [CtLocalVariableImpl][CtCommentImpl]// s is level and t is trend
        [CtTypeReferenceImpl]double s = [CtVariableReadImpl]a0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double t = [CtVariableReadImpl]b0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double predictedValue = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]y.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]double sNew;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double tNew;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]forecast[[CtVariableReadImpl]i] = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s + [CtVariableReadImpl]t) * [CtArrayReadImpl][CtVariableReadImpl]seasonal[[CtVariableReadImpl]i];
            [CtAssignmentImpl][CtVariableWriteImpl]sNew = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]alpha * [CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]y[[CtVariableReadImpl]i] / [CtArrayReadImpl][CtVariableReadImpl]seasonal[[CtVariableReadImpl]i])) + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1 - [CtVariableReadImpl]alpha) * [CtBinaryOperatorImpl]([CtVariableReadImpl]s + [CtVariableReadImpl]t));
            [CtAssignmentImpl][CtVariableWriteImpl]tNew = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]beta * [CtBinaryOperatorImpl]([CtVariableReadImpl]sNew - [CtVariableReadImpl]s)) + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1 - [CtVariableReadImpl]beta) * [CtVariableReadImpl]t);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i + [CtFieldReadImpl]period) <= [CtFieldReadImpl][CtVariableReadImpl]y.length) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]seasonal[[CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtFieldReadImpl]period] = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]gamma * [CtBinaryOperatorImpl]([CtArrayReadImpl][CtVariableReadImpl]y[[CtVariableReadImpl]i] / [CtBinaryOperatorImpl]([CtVariableReadImpl]sNew * [CtArrayReadImpl][CtVariableReadImpl]seasonal[[CtVariableReadImpl]i]))) + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1 - [CtVariableReadImpl]gamma) * [CtArrayReadImpl][CtVariableReadImpl]seasonal[[CtVariableReadImpl]i]);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]s = [CtVariableReadImpl]sNew;
            [CtAssignmentImpl][CtVariableWriteImpl]t = [CtVariableReadImpl]tNew;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]y.length - [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]predictedValue = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s + [CtVariableReadImpl]t) * [CtArrayReadImpl][CtVariableReadImpl]seasonal[[CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1];
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double> diff = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double sse = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]y.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]forecast[[CtVariableReadImpl]i] != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]sse += [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.pow([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]y[[CtVariableReadImpl]i] - [CtArrayReadImpl][CtVariableReadImpl]forecast[[CtVariableReadImpl]i], [CtLiteralImpl]2);
                [CtInvocationImpl][CtVariableReadImpl]diff.add([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]forecast[[CtVariableReadImpl]i] - [CtArrayReadImpl][CtVariableReadImpl]y[[CtVariableReadImpl]i]);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]double error = [CtInvocationImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.calculateErrorBound([CtVariableReadImpl]diff, [CtInvocationImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.sensitivityToZscore([CtFieldReadImpl]sensitivity));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.ForecastResults([CtVariableReadImpl]predictedValue, [CtVariableReadImpl]sse, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Compute the baseline and error bound for given data
     *
     * @param inputDF
     * 		training dataframe
     * @param windowStartTime
     * 		prediction start time
     * @return DataFrame with timestamp, baseline, error bound
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame computePredictionInterval([CtParameterImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame inputDF, [CtParameterImpl][CtTypeReferenceImpl]long windowStartTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String timezone) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame resultDF = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame forecastDF = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputDF.filter([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.Series.LongConditional()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean apply([CtParameterImpl]long... values) [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]values[[CtLiteralImpl]0] >= [CtVariableReadImpl]windowStartTime;
            }
        }, [CtTypeAccessImpl]COL_TIME).dropNull();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtVariableReadImpl]forecastDF.size();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] baselineArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]size];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] upperBoundArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]size];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] lowerBoundArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]size];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] resultTimeArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtVariableReadImpl]size];
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] errorArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]double[[CtVariableReadImpl]size];
        [CtLocalVariableImpl][CtTypeReferenceImpl]double lastAlpha = [CtFieldReadImpl]alpha;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double lastBeta = [CtFieldReadImpl]beta;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double lastGamma = [CtFieldReadImpl]gamma;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int k = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]k < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]k++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.dataframe.DataFrame trainingDF;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]timeGranularity.equals([CtTypeAccessImpl]MetricSlice.NATIVE_GRANULARITY) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"MONTHS"))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.monitoringGranularity.endsWith([CtLiteralImpl]"WEEKS"))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]trainingDF = [CtInvocationImpl]getDailyDF([CtVariableReadImpl]inputDF, [CtInvocationImpl][CtVariableReadImpl]forecastDF.getLong([CtTypeAccessImpl]COL_TIME, [CtVariableReadImpl]k), [CtVariableReadImpl]timezone);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]trainingDF = [CtInvocationImpl]getLookbackDF([CtVariableReadImpl]inputDF, [CtInvocationImpl][CtVariableReadImpl]forecastDF.getLong([CtTypeAccessImpl]COL_TIME, [CtVariableReadImpl]k));
            }
            [CtIfImpl][CtCommentImpl]// We need at least 2 periods of data
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]trainingDF.size() < [CtBinaryOperatorImpl]([CtLiteralImpl]2 * [CtFieldReadImpl]period)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]resultTimeArray[[CtVariableReadImpl]k] = [CtInvocationImpl][CtVariableReadImpl]forecastDF.getLong([CtTypeAccessImpl]COL_TIME, [CtVariableReadImpl]k);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]double[] y = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trainingDF.getDoubles([CtTypeAccessImpl]COL_VALUE).values();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams params;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]alpha < [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtFieldReadImpl]beta < [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]gamma < [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]params = [CtInvocationImpl]fitModelWithBOBYQA([CtVariableReadImpl]y, [CtVariableReadImpl]lastAlpha, [CtVariableReadImpl]lastBeta, [CtVariableReadImpl]lastGamma);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams([CtFieldReadImpl]alpha, [CtFieldReadImpl]beta, [CtFieldReadImpl]gamma);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]lastAlpha = [CtInvocationImpl][CtVariableReadImpl]params.getAlpha();
            [CtAssignmentImpl][CtVariableWriteImpl]lastBeta = [CtInvocationImpl][CtVariableReadImpl]params.getBeta();
            [CtAssignmentImpl][CtVariableWriteImpl]lastGamma = [CtInvocationImpl][CtVariableReadImpl]params.getGamma();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.ForecastResults result = [CtInvocationImpl]forecast([CtVariableReadImpl]y, [CtInvocationImpl][CtVariableReadImpl]params.getAlpha(), [CtInvocationImpl][CtVariableReadImpl]params.getBeta(), [CtInvocationImpl][CtVariableReadImpl]params.getGamma());
            [CtLocalVariableImpl][CtTypeReferenceImpl]double predicted = [CtInvocationImpl][CtVariableReadImpl]result.getPredictedValue();
            [CtLocalVariableImpl][CtTypeReferenceImpl]double error = [CtInvocationImpl][CtVariableReadImpl]result.getErrorBound();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]baselineArray[[CtVariableReadImpl]k] = [CtVariableReadImpl]predicted;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]errorArray[[CtVariableReadImpl]k] = [CtVariableReadImpl]error;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]upperBoundArray[[CtVariableReadImpl]k] = [CtBinaryOperatorImpl][CtVariableReadImpl]predicted + [CtVariableReadImpl]error;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]lowerBoundArray[[CtVariableReadImpl]k] = [CtBinaryOperatorImpl][CtVariableReadImpl]predicted - [CtVariableReadImpl]error;
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resultDF.addSeries([CtTypeAccessImpl]COL_TIME, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.LongSeries.buildFrom([CtVariableReadImpl]resultTimeArray)).setIndex([CtTypeAccessImpl]COL_TIME);
        [CtInvocationImpl][CtVariableReadImpl]resultDF.addSeries([CtTypeAccessImpl]COL_VALUE, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DoubleSeries.buildFrom([CtVariableReadImpl]baselineArray));
        [CtInvocationImpl][CtVariableReadImpl]resultDF.addSeries([CtTypeAccessImpl]COL_UPPER_BOUND, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DoubleSeries.buildFrom([CtVariableReadImpl]upperBoundArray));
        [CtInvocationImpl][CtVariableReadImpl]resultDF.addSeries([CtTypeAccessImpl]COL_LOWER_BOUND, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DoubleSeries.buildFrom([CtVariableReadImpl]lowerBoundArray));
        [CtInvocationImpl][CtVariableReadImpl]resultDF.addSeries([CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.COL_ERROR, [CtInvocationImpl][CtTypeAccessImpl]org.apache.pinot.thirdeye.dataframe.DoubleSeries.buildFrom([CtVariableReadImpl]errorArray));
        [CtReturnImpl]return [CtVariableReadImpl]resultDF;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the error bound of given list based on mean, std and given zscore
     *
     * @param givenNumbers
     * 		double list
     * @param zscore
     * 		zscore used to multiply by std
     * @return the error bound
     */
    private static [CtTypeReferenceImpl]double calculateErrorBound([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Double> givenNumbers, [CtParameterImpl][CtTypeReferenceImpl]double zscore) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// calculate the mean value (= average)
        [CtTypeReferenceImpl]double sum = [CtLiteralImpl]0.0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]double num : [CtVariableReadImpl]givenNumbers) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]sum += [CtVariableReadImpl]num;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]double mean = [CtBinaryOperatorImpl][CtVariableReadImpl]sum / [CtInvocationImpl][CtVariableReadImpl]givenNumbers.size();
        [CtLocalVariableImpl][CtCommentImpl]// calculate standard deviation
        [CtTypeReferenceImpl]double squaredDifferenceSum = [CtLiteralImpl]0.0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]double num : [CtVariableReadImpl]givenNumbers) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]squaredDifferenceSum += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]num - [CtVariableReadImpl]mean) * [CtBinaryOperatorImpl]([CtVariableReadImpl]num - [CtVariableReadImpl]mean);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]double variance = [CtBinaryOperatorImpl][CtVariableReadImpl]squaredDifferenceSum / [CtInvocationImpl][CtVariableReadImpl]givenNumbers.size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double standardDeviation = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.sqrt([CtVariableReadImpl]variance);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]zscore * [CtVariableReadImpl]standardDeviation;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fit alpha, beta, gamma by optimizing SSE (Sum of squared errors) using BOBYQA
     * It is a derivative free bound constrained optimization algorithm
     * https://en.wikipedia.org/wiki/BOBYQA
     *
     * @param y
     * 		the data
     * @param lastAlpha
     * 		last alpha value
     * @param lastBeta
     * 		last beta value
     * @param lastGamma
     * 		last gamma value
     * @return double array containing fitted alpha, beta and gamma
     */
    private [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams fitModelWithBOBYQA([CtParameterImpl][CtArrayTypeReferenceImpl]double[] y, [CtParameterImpl][CtTypeReferenceImpl]double lastAlpha, [CtParameterImpl][CtTypeReferenceImpl]double lastBeta, [CtParameterImpl][CtTypeReferenceImpl]double lastGamma) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer optimizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer([CtLiteralImpl]7);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lastAlpha < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]lastAlpha = [CtLiteralImpl]0.1;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lastBeta < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]lastBeta = [CtLiteralImpl]0.01;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lastGamma < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]lastGamma = [CtLiteralImpl]0.001;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.InitialGuess initGuess = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.InitialGuess([CtNewArrayImpl]new [CtTypeReferenceImpl]double[]{ [CtVariableReadImpl]lastAlpha, [CtVariableReadImpl]lastBeta, [CtVariableReadImpl]lastGamma });
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.MaxIter maxIter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.MaxIter([CtLiteralImpl]30000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.MaxEval maxEval = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.MaxEval([CtLiteralImpl]30000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.nonlinear.scalar.GoalType goal = [CtFieldReadImpl]org.apache.commons.math3.optim.nonlinear.scalar.GoalType.MINIMIZE;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction objectiveFunction = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction([CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.analysis.MultivariateFunction()[CtClassImpl] {
            [CtMethodImpl]public [CtTypeReferenceImpl]double value([CtParameterImpl][CtArrayTypeReferenceImpl]double[] params) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]forecast([CtVariableReadImpl]y, [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]0], [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]1], [CtArrayReadImpl][CtVariableReadImpl]params[[CtLiteralImpl]2]).getSSE();
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.SimpleBounds bounds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.math3.optim.SimpleBounds([CtNewArrayImpl]new [CtTypeReferenceImpl]double[]{ [CtLiteralImpl]0.001, [CtLiteralImpl]0.001, [CtLiteralImpl]0.001 }, [CtNewArrayImpl]new [CtTypeReferenceImpl]double[]{ [CtLiteralImpl]0.999, [CtLiteralImpl]0.999, [CtLiteralImpl]0.999 });
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams params;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.math3.optim.PointValuePair optimal = [CtInvocationImpl][CtVariableReadImpl]optimizer.optimize([CtVariableReadImpl]objectiveFunction, [CtVariableReadImpl]goal, [CtVariableReadImpl]bounds, [CtVariableReadImpl]initGuess, [CtVariableReadImpl]maxIter, [CtVariableReadImpl]maxEval);
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams([CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]optimal.getPoint()[[CtLiteralImpl]0], [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]optimal.getPoint()[[CtLiteralImpl]1], [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]optimal.getPoint()[[CtLiteralImpl]2]);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.LOG.error([CtInvocationImpl][CtVariableReadImpl]e.toString());
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.pinot.thirdeye.detection.components.HoltWintersDetector.HoltWintersParams([CtVariableReadImpl]lastAlpha, [CtVariableReadImpl]lastBeta, [CtVariableReadImpl]lastGamma);
        }
        [CtReturnImpl]return [CtVariableReadImpl]params;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Mapping of sensitivity to zscore on range of 1 - 3
     *
     * @param sensitivity
     * 		double from 0 to 10
     * @return zscore
     */
    private static [CtTypeReferenceImpl]double sensitivityToZscore([CtParameterImpl][CtTypeReferenceImpl]double sensitivity) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// If out of bound, use boundary sensitivity
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensitivity < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sensitivity = [CtLiteralImpl]0;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sensitivity > [CtLiteralImpl]10) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]sensitivity = [CtLiteralImpl]10;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]double z = [CtBinaryOperatorImpl][CtLiteralImpl]1 + [CtBinaryOperatorImpl]([CtLiteralImpl]0.2 * [CtBinaryOperatorImpl]([CtLiteralImpl]10 - [CtVariableReadImpl]sensitivity));
        [CtReturnImpl]return [CtVariableReadImpl]z;
    }

    [CtMethodImpl][CtCommentImpl]// Check whether monitoring timeGranularity is multiple days
    private [CtTypeReferenceImpl]boolean isMultiDayGranularity() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]timeGranularity.equals([CtTypeAccessImpl]MetricSlice.NATIVE_GRANULARITY)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]timeGranularity.getUnit() == [CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]DAYS);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Container class to store holt winters parameters
     */
    static final class HoltWintersParams {
        [CtFieldImpl]private final [CtTypeReferenceImpl]double alpha;

        [CtFieldImpl]private final [CtTypeReferenceImpl]double beta;

        [CtFieldImpl]private final [CtTypeReferenceImpl]double gamma;

        [CtConstructorImpl]HoltWintersParams([CtParameterImpl][CtTypeReferenceImpl]double alpha, [CtParameterImpl][CtTypeReferenceImpl]double beta, [CtParameterImpl][CtTypeReferenceImpl]double gamma) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.alpha = [CtVariableReadImpl]alpha;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.beta = [CtVariableReadImpl]beta;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gamma = [CtVariableReadImpl]gamma;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getAlpha() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]alpha;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getBeta() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]beta;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getGamma() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]gamma;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Container class to store forecasting results
     */
    static final class ForecastResults {
        [CtFieldImpl]private final [CtTypeReferenceImpl]double predictedValue;

        [CtFieldImpl]private final [CtTypeReferenceImpl]double SSE;

        [CtFieldImpl]private final [CtTypeReferenceImpl]double errorBound;

        [CtConstructorImpl]ForecastResults([CtParameterImpl][CtTypeReferenceImpl]double predictedValue, [CtParameterImpl][CtTypeReferenceImpl]double SSE, [CtParameterImpl][CtTypeReferenceImpl]double errorBound) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.predictedValue = [CtVariableReadImpl]predictedValue;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.SSE = [CtVariableReadImpl]SSE;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.errorBound = [CtVariableReadImpl]errorBound;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getPredictedValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]predictedValue;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getSSE() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]SSE;
        }

        [CtMethodImpl][CtTypeReferenceImpl]double getErrorBound() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]errorBound;
        }
    }
}
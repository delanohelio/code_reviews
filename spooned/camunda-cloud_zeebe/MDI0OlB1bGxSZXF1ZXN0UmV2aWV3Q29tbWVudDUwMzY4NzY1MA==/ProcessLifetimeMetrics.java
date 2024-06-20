[CompilationUnitImpl][CtCommentImpl]/* Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
one or more contributor license agreements. See the NOTICE file distributed
with this work for additional information regarding copyright ownership.
Licensed under the Zeebe Community License 1.0. You may not use this file
except in compliance with the Zeebe Community License 1.0.
 */
[CtPackageDeclarationImpl]package io.zeebe.broker.system.monitoring;
[CtUnresolvedImport]import io.prometheus.client.Gauge;
[CtClassImpl]public class ProcessLifetimeMetrics {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ZEEBE_NAMESPACE = [CtLiteralImpl]"zeebe";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PROCESS_NAME_LABEL = [CtLiteralImpl]"processName";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String STEP_NAME_LABEL = [CtLiteralImpl]"stepName";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.prometheus.client.Gauge STARTUP_METRIC = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.prometheus.client.Gauge.build().namespace([CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.ZEEBE_NAMESPACE).name([CtLiteralImpl]"broker_process_startup_metric").labelNames([CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.PROCESS_NAME_LABEL, [CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.STEP_NAME_LABEL).create().register();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]io.prometheus.client.Gauge CLOSE_METRICS = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.prometheus.client.Gauge.build().namespace([CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.ZEEBE_NAMESPACE).name([CtLiteralImpl]"broker_process_close_metric").labelNames([CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.PROCESS_NAME_LABEL, [CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.STEP_NAME_LABEL).create().register();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String processName;

    [CtConstructorImpl]public ProcessLifetimeMetrics([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String processName) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.processName = [CtVariableReadImpl]processName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Meter the time to start for a single step.
     *
     * @param stepName
     * 		the name of the step
     * @param startupDuration
     * 		the step start duration in ms
     */
    public [CtTypeReferenceImpl]void meterStartupTimeForStep([CtParameterImpl][CtTypeReferenceImpl]java.lang.String stepName, [CtParameterImpl][CtTypeReferenceImpl]long startupDuration) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.STARTUP_METRIC.labels([CtFieldReadImpl]processName, [CtVariableReadImpl]stepName).set([CtVariableReadImpl]startupDuration);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Meter the the time to close for a single step.
     *
     * @param stepName
     * 		the name of the step
     * @param closeDuration
     * 		the step close duration in ms
     */
    public [CtTypeReferenceImpl]void meterCloseTimeForStep([CtParameterImpl][CtTypeReferenceImpl]java.lang.String stepName, [CtParameterImpl][CtTypeReferenceImpl]long closeDuration) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]io.zeebe.broker.system.monitoring.ProcessLifetimeMetrics.CLOSE_METRICS.labels([CtFieldReadImpl]processName, [CtVariableReadImpl]stepName).set([CtVariableReadImpl]closeDuration);
    }
}
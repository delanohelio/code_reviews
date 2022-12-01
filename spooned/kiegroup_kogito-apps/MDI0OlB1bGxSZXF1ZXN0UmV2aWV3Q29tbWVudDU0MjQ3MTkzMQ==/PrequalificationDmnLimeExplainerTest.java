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
[CtPackageDeclarationImpl]package org.kie.kogito.explainability.explainability.integrationtests.dmn;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertNotNull;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.FeatureFactory;
[CtUnresolvedImport]import org.kie.kogito.decision.DecisionModel;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.PerturbationContext;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.Feature;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.Prediction;
[CtUnresolvedImport]import org.kie.kogito.dmn.DmnDecisionModel;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.Saliency;
[CtImportImpl]import java.util.Random;
[CtImportImpl]import java.util.concurrent.ExecutionException;
[CtUnresolvedImport]import org.kie.kogito.explainability.local.lime.LimeConfig;
[CtUnresolvedImport]import org.kie.dmn.api.core.DMNRuntime;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtImportImpl]import java.util.concurrent.TimeoutException;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.PredictionOutput;
[CtImportImpl]import java.util.LinkedList;
[CtUnresolvedImport]import org.kie.kogito.explainability.local.lime.LimeExplainer;
[CtImportImpl]import java.io.InputStreamReader;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.FeatureImportance;
[CtUnresolvedImport]import org.kie.kogito.dmn.DMNKogito;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.PredictionProvider;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.kie.kogito.explainability.model.PredictionInput;
[CtUnresolvedImport]import org.kie.kogito.explainability.utils.ExplainabilityMetrics;
[CtUnresolvedImport]import org.kie.kogito.explainability.utils.ValidationUtils;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.kie.kogito.explainability.Config;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
[CtClassImpl]class PrequalificationDmnLimeExplainerTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testPrequalificationDMNExplanation1() throws [CtTypeReferenceImpl]java.util.concurrent.ExecutionException, [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.util.concurrent.TimeoutException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.dmn.api.core.DMNRuntime dmnRuntime = [CtInvocationImpl][CtTypeAccessImpl]org.kie.kogito.dmn.DMNKogito.createGenericDMNRuntime([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtInvocationImpl][CtInvocationImpl]getClass().getResourceAsStream([CtLiteralImpl]"/dmn/Prequalification-1.dmn")));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dmnRuntime.getModels().size());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String NS = [CtLiteralImpl]"http://www.trisotech.com/definitions/_f31e1f8e-d4ce-4a3a-ac3b-747efa6b3401";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"Prequalification";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.decision.DecisionModel decisionModel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.dmn.DmnDecisionModel([CtVariableReadImpl]dmnRuntime, [CtVariableReadImpl]NS, [CtVariableReadImpl]NAME);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> borrower = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]borrower.put([CtLiteralImpl]"Monthly Other Debt", [CtLiteralImpl]1000);
        [CtInvocationImpl][CtVariableReadImpl]borrower.put([CtLiteralImpl]"Monthly Income", [CtLiteralImpl]10000);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contextVariables = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]contextVariables.put([CtLiteralImpl]"Appraised Value", [CtLiteralImpl]500000);
        [CtInvocationImpl][CtVariableReadImpl]contextVariables.put([CtLiteralImpl]"Loan Amount", [CtLiteralImpl]300000);
        [CtInvocationImpl][CtVariableReadImpl]contextVariables.put([CtLiteralImpl]"Credit Score", [CtLiteralImpl]600);
        [CtInvocationImpl][CtVariableReadImpl]contextVariables.put([CtLiteralImpl]"Borrower", [CtVariableReadImpl]borrower);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.kogito.explainability.model.Feature> features = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
        [CtInvocationImpl][CtVariableReadImpl]features.add([CtInvocationImpl][CtTypeAccessImpl]org.kie.kogito.explainability.model.FeatureFactory.newCompositeFeature([CtLiteralImpl]"context", [CtVariableReadImpl]contextVariables));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.model.PredictionInput predictionInput = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.model.PredictionInput([CtVariableReadImpl]features);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.model.PredictionProvider model = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.explainability.integrationtests.dmn.DecisionModelWrapper([CtVariableReadImpl]decisionModel);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Random random = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Random();
        [CtInvocationImpl][CtVariableReadImpl]random.setSeed([CtLiteralImpl]4);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.local.lime.LimeConfig limeConfig = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.local.lime.LimeConfig().withSamples([CtLiteralImpl]3000).withPerturbationContext([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.model.PerturbationContext([CtVariableReadImpl]random, [CtLiteralImpl]3));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.local.lime.LimeExplainer limeExplainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.local.lime.LimeExplainer([CtVariableReadImpl]limeConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.kogito.explainability.model.PredictionOutput> predictionOutputs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]model.predictAsync([CtInvocationImpl][CtTypeAccessImpl]java.util.List.of([CtVariableReadImpl]predictionInput)).get([CtInvocationImpl][CtTypeAccessImpl]Config.INSTANCE.getAsyncTimeout(), [CtInvocationImpl][CtTypeAccessImpl]Config.INSTANCE.getAsyncTimeUnit());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.model.Prediction prediction = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.explainability.model.Prediction([CtVariableReadImpl]predictionInput, [CtInvocationImpl][CtVariableReadImpl]predictionOutputs.get([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.kie.kogito.explainability.model.Saliency> saliencyMap = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]limeExplainer.explainAsync([CtVariableReadImpl]prediction, [CtVariableReadImpl]model).get([CtInvocationImpl][CtTypeAccessImpl]Config.INSTANCE.getAsyncTimeout(), [CtInvocationImpl][CtTypeAccessImpl]Config.INSTANCE.getAsyncTimeUnit());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.explainability.model.Saliency saliency : [CtInvocationImpl][CtVariableReadImpl]saliencyMap.values()) [CtBlockImpl]{
            [CtInvocationImpl]assertNotNull([CtVariableReadImpl]saliency);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.kie.kogito.explainability.model.FeatureImportance> topFeatures = [CtInvocationImpl][CtVariableReadImpl]saliency.getTopFeatures([CtLiteralImpl]2);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]topFeatures.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtTypeAccessImpl]org.kie.kogito.explainability.utils.ExplainabilityMetrics.impactScore([CtVariableReadImpl]model, [CtVariableReadImpl]prediction, [CtVariableReadImpl]topFeatures)).isPositive();
            }
        }
        [CtInvocationImpl]assertDoesNotThrow([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]org.kie.kogito.explainability.utils.ValidationUtils.validateLocalSaliencyStability([CtVariableReadImpl]model, [CtVariableReadImpl]prediction, [CtVariableReadImpl]limeExplainer, [CtLiteralImpl]1, [CtLiteralImpl]0.5, [CtLiteralImpl]0.5));
    }
}
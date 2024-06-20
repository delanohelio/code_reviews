[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

    http://www.apache.org/licenses/LICENSE-2.0

or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package com.amazon.opendistroforelasticsearch.ad;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.elasticsearch.action.get.GetResponse;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.XContentParseException;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.XContentType;
[CtUnresolvedImport]import org.elasticsearch.search.SearchHits;
[CtUnresolvedImport]import org.elasticsearch.action.search.SearchRequest;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.LoggingDeprecationHandler;
[CtUnresolvedImport]import org.elasticsearch.index.query.QueryBuilders;
[CtUnresolvedImport]import static org.elasticsearch.common.xcontent.XContentParserUtils.ensureExpectedToken;
[CtUnresolvedImport]import org.elasticsearch.index.IndexNotFoundException;
[CtUnresolvedImport]import org.elasticsearch.action.search.SearchResponse;
[CtUnresolvedImport]import org.elasticsearch.search.builder.SearchSourceBuilder;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetector.ANOMALY_DETECTORS_INDEX;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.NamedXContentRegistry;
[CtUnresolvedImport]import org.elasticsearch.search.SearchHit;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetector;
[CtUnresolvedImport]import org.elasticsearch.action.get.GetRequest;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener;
[CtUnresolvedImport]import org.elasticsearch.client.Client;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult;
[CtUnresolvedImport]import org.elasticsearch.search.sort.FieldSortBuilder;
[CtUnresolvedImport]import org.elasticsearch.common.xcontent.XContentParser;
[CtUnresolvedImport]import static com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetectorJob.ANOMALY_DETECTOR_JOB_INDEX;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.ProfileName;
[CtUnresolvedImport]import org.apache.logging.log4j.Logger;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile;
[CtUnresolvedImport]import org.elasticsearch.search.sort.SortOrder;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.DetectorState;
[CtUnresolvedImport]import org.elasticsearch.index.query.BoolQueryBuilder;
[CtUnresolvedImport]import org.apache.logging.log4j.LogManager;
[CtUnresolvedImport]import org.elasticsearch.action.ActionListener;
[CtUnresolvedImport]import com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetectorJob;
[CtClassImpl]public class AnomalyDetectorProfileRunner {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.logging.log4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.LogManager.getLogger([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.AnomalyDetectorProfileRunner.class);

    [CtFieldImpl]private [CtTypeReferenceImpl]org.elasticsearch.client.Client client;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.elasticsearch.common.xcontent.NamedXContentRegistry xContentRegistry;

    [CtFieldImpl]static [CtTypeReferenceImpl]java.lang.String FAIL_TO_FIND_DETECTOR_MSG = [CtLiteralImpl]"Fail to find detector with id: ";

    [CtConstructorImpl]public AnomalyDetectorProfileRunner([CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.client.Client client, [CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.NamedXContentRegistry xContentRegistry) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.client = [CtVariableReadImpl]client;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.xContentRegistry = [CtVariableReadImpl]xContentRegistry;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void profile([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.action.ActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> profiles) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> delegateListener = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile>([CtVariableReadImpl]listener, [CtInvocationImpl][CtVariableReadImpl]profiles.size(), [CtBinaryOperatorImpl][CtLiteralImpl]"Fail to fetch profile for " + [CtVariableReadImpl]detectorId);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profiles.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Unsupported profile types."));
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]profiles.contains([CtInvocationImpl][CtTypeAccessImpl]ProfileName.STATE.getName()) || [CtInvocationImpl][CtVariableReadImpl]profiles.contains([CtInvocationImpl][CtTypeAccessImpl]ProfileName.ERROR.getName())) [CtBlockImpl]{
            [CtInvocationImpl]prepareProfileStateNError([CtVariableReadImpl]detectorId, [CtVariableReadImpl]delegateListener, [CtVariableReadImpl]profiles);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void prepareProfileStateNError([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> profiles) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.get.GetRequest getDetectorRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.action.get.GetRequest([CtFieldReadImpl]AnomalyDetector.ANOMALY_DETECTORS_INDEX, [CtVariableReadImpl]detectorId);
        [CtInvocationImpl][CtFieldReadImpl]client.get([CtVariableReadImpl]getDetectorRequest, [CtInvocationImpl]onGetDetectorResponse([CtVariableReadImpl]listener, [CtVariableReadImpl]detectorId, [CtVariableReadImpl]profiles));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.elasticsearch.action.ActionListener<[CtTypeReferenceImpl]org.elasticsearch.action.get.GetResponse> onGetDetectorResponse([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> profiles) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.action.ActionListener.wrap([CtLambdaImpl]([CtParameterImpl] getResponse) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]getResponse != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]getResponse.isExists()) [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParser parser = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]XContentType.JSON.xContent().createParser([CtFieldReadImpl][CtFieldReferenceImpl]xContentRegistry, [CtVariableReadImpl]LoggingDeprecationHandler.INSTANCE, [CtInvocationImpl][CtVariableReadImpl]getResponse.getSourceAsString())) [CtBlockImpl]{
                    [CtInvocationImpl]ensureExpectedToken([CtVariableReadImpl]XContentParser.Token.START_OBJECT, [CtInvocationImpl][CtVariableReadImpl]parser.nextToken(), [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]parser::getTokenLocation);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetector detector = [CtInvocationImpl][CtVariableReadImpl]parser.namedObject([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetector.class, [CtVariableReadImpl]AnomalyDetector.PARSE_FIELD_NAME, [CtLiteralImpl]null);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long lastUpdateTimeMs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]detector.getLastUpdateTime().toEpochMilli();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profiles.contains([CtInvocationImpl][CtVariableReadImpl]ProfileName.STATE.getName())) [CtBlockImpl]{
                        [CtInvocationImpl]profileState([CtVariableReadImpl]detectorId, [CtVariableReadImpl]lastUpdateTimeMs, [CtVariableReadImpl]listener);
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]profiles.contains([CtInvocationImpl][CtVariableReadImpl]ProfileName.ERROR.getName())) [CtBlockImpl]{
                        [CtInvocationImpl]profileError([CtVariableReadImpl]detectorId, [CtVariableReadImpl]lastUpdateTimeMs, [CtVariableReadImpl]listener);
                    }
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParseException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.e);
                    [CtInvocationImpl][CtVariableReadImpl]listener.failImmediately([CtConstructorCallImpl]new <com.amazon.opendistroforelasticsearch.ad.e>[CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]com.amazon.opendistroforelasticsearch.ad.AnomalyDetectorProfileRunner.FAIL_TO_FIND_DETECTOR_MSG + [CtVariableReadImpl]detectorId));
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.failImmediately([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]com.amazon.opendistroforelasticsearch.ad.AnomalyDetectorProfileRunner.FAIL_TO_FIND_DETECTOR_MSG + [CtVariableReadImpl]detectorId);
            }
        }, [CtLambdaImpl]([CtParameterImpl] exception) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]listener.failImmediately([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReferenceImpl]com.amazon.opendistroforelasticsearch.ad.AnomalyDetectorProfileRunner.FAIL_TO_FIND_DETECTOR_MSG + [CtVariableReadImpl]detectorId, [CtVariableReadImpl]exception);
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * We expect three kinds of states:
     *  -Disabled: if get ad job api says the job is disabled;
     *  -Init: if anomaly score after the last update time of the detector is larger than 0
     *  -Running: if neither of the above applies and no exceptions.
     *
     * @param detectorId
     * 		detector id
     * @param lastUpdateTimeMs
     * 		last update time of the detector in milliseconds
     * @param listener
     * 		listener to process the returned state or exception
     */
    private [CtTypeReferenceImpl]void profileState([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]long lastUpdateTimeMs, [CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.get.GetRequest getRequest = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.action.get.GetRequest([CtFieldReadImpl]AnomalyDetectorJob.ANOMALY_DETECTOR_JOB_INDEX, [CtVariableReadImpl]detectorId);
        [CtInvocationImpl][CtFieldReadImpl]client.get([CtVariableReadImpl]getRequest, [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.action.ActionListener.wrap([CtLambdaImpl]([CtParameterImpl] getResponse) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]getResponse.isExists()) [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParser parser = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]XContentType.JSON.xContent().createParser([CtFieldReadImpl][CtFieldReferenceImpl]xContentRegistry, [CtVariableReadImpl]LoggingDeprecationHandler.INSTANCE, [CtInvocationImpl][CtVariableReadImpl]getResponse.getSourceAsString())) [CtBlockImpl]{
                    [CtInvocationImpl]ensureExpectedToken([CtVariableReadImpl]XContentParser.Token.START_OBJECT, [CtInvocationImpl][CtVariableReadImpl]parser.nextToken(), [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]parser::getTokenLocation);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetectorJob job = [CtInvocationImpl][CtTypeAccessImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyDetectorJob.parse([CtVariableReadImpl]parser);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]job.isEnabled()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest searchLatestResult = [CtInvocationImpl]createInittedEverRequest([CtVariableReadImpl]detectorId, [CtVariableReadImpl]lastUpdateTimeMs);
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]client.search([CtVariableReadImpl]searchLatestResult, [CtInvocationImpl]onInittedEver([CtVariableReadImpl]listener, [CtVariableReadImpl]detectorId, [CtVariableReadImpl]lastUpdateTimeMs));
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
                        [CtInvocationImpl][CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.DISABLED);
                        [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
                    }
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParseException e) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtBinaryOperatorImpl][CtLiteralImpl]"Fail to parse detector with id: " + [CtVariableReadImpl]detectorId;
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtVariableReadImpl]error);
                    [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new <com.amazon.opendistroforelasticsearch.ad.e>[CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]error));
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
                [CtInvocationImpl][CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.DISABLED);
                [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
            }
        }, [CtLambdaImpl]([CtParameterImpl] exception) -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.warn([CtVariableReadImpl]exception);
            [CtIfImpl][CtCommentImpl]// detector job index does not exist
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]exception instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.elasticsearch.index.IndexNotFoundException) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
                [CtInvocationImpl][CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.DISABLED);
                [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to get detector state for " + [CtVariableReadImpl]detectorId);
                [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtVariableReadImpl]exception);
            }
        }));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.elasticsearch.action.ActionListener<[CtTypeReferenceImpl]org.elasticsearch.action.search.SearchResponse> onInittedEver([CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]long lastUpdateTimeMs) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.action.ActionListener.wrap([CtLambdaImpl]([CtParameterImpl] searchResponse) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.SearchHits hits = [CtInvocationImpl][CtVariableReadImpl]searchResponse.getHits();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]hits.getTotalHits().value == [CtLiteralImpl]0L) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.INIT);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.RUNNING);
            }
            [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
        }, [CtLambdaImpl]([CtParameterImpl] exception) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exception instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.elasticsearch.index.IndexNotFoundException) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
                [CtInvocationImpl][CtCommentImpl]// anomaly result index is not created yet
                [CtVariableReadImpl]profile.setState([CtVariableReadImpl]DetectorState.INIT);
                [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtLiteralImpl]"Fail to find latest anomaly result of id: {}", [CtVariableReadImpl]detectorId);
                [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to find detector state: " + [CtVariableReadImpl]detectorId, [CtVariableReadImpl]exception));
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Error is populated if error of the latest anomaly result is not empty.
     *
     * @param detectorId
     * 		detector id
     * @param lastUpdateTimeMs
     * 		last update time of the detector in milliseconds
     * @param listener
     * 		listener to process the returned error or exception
     */
    private [CtTypeReferenceImpl]void profileError([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]long lastUpdateTimeMs, [CtParameterImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.util.DelegateActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest searchLatestResult = [CtInvocationImpl]createLatestAnomalyResultRequest([CtVariableReadImpl]detectorId, [CtVariableReadImpl]lastUpdateTimeMs);
        [CtInvocationImpl][CtFieldReadImpl]client.search([CtVariableReadImpl]searchLatestResult, [CtInvocationImpl]onGetLatestAnomalyResult([CtVariableReadImpl]listener, [CtVariableReadImpl]detectorId));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.elasticsearch.action.ActionListener<[CtTypeReferenceImpl]org.elasticsearch.action.search.SearchResponse> onGetLatestAnomalyResult([CtParameterImpl][CtTypeReferenceImpl]org.elasticsearch.action.ActionListener<[CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile> listener, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.action.ActionListener.wrap([CtLambdaImpl]([CtParameterImpl] searchResponse) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.SearchHits hits = [CtInvocationImpl][CtVariableReadImpl]searchResponse.getHits();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]hits.getTotalHits().value == [CtLiteralImpl]0L) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtLiteralImpl]"We should not get empty result: {}", [CtVariableReadImpl]detectorId);
                [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected error while looking for detector state:  " + [CtVariableReadImpl]detectorId));
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.SearchHit hit = [CtInvocationImpl][CtVariableReadImpl]hits.getAt([CtLiteralImpl]0);
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParser parser = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]XContentType.JSON.xContent().createParser([CtFieldReadImpl][CtFieldReferenceImpl]xContentRegistry, [CtVariableReadImpl]LoggingDeprecationHandler.INSTANCE, [CtInvocationImpl][CtVariableReadImpl]hit.getSourceAsString())) [CtBlockImpl]{
                    [CtInvocationImpl]ensureExpectedToken([CtVariableReadImpl]XContentParser.Token.START_OBJECT, [CtInvocationImpl][CtVariableReadImpl]parser.nextToken(), [CtExecutableReferenceExpressionImpl][CtVariableReadImpl]parser::getTokenLocation);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult result = [CtInvocationImpl][CtVariableReadImpl]parser.namedObject([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult.class, [CtVariableReadImpl]AnomalyResult.PARSE_FIELD_NAME, [CtLiteralImpl]null);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile profile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]result.getError() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]profile.setError([CtInvocationImpl][CtVariableReadImpl]result.getError());
                    }
                    [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtVariableReadImpl]profile);
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.elasticsearch.common.xcontent.XContentParseException | [CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to parse anomaly result with " + [CtInvocationImpl][CtVariableReadImpl]hit.toString());
                    [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new <com.amazon.opendistroforelasticsearch.ad.e>[CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to find detector error: " + [CtVariableReadImpl]detectorId));
                }
            }
        }, [CtLambdaImpl]([CtParameterImpl] exception) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exception instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.elasticsearch.index.IndexNotFoundException) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.onResponse([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazon.opendistroforelasticsearch.ad.model.DetectorProfile());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]logger.error([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to find latest anomaly result of id: " + [CtVariableReadImpl]detectorId);
                [CtInvocationImpl][CtVariableReadImpl]listener.onFailure([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtBinaryOperatorImpl][CtLiteralImpl]"Fail to find detector error: " + [CtVariableReadImpl]detectorId, [CtVariableReadImpl]exception));
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create search request to check if we have at least 1 anomaly score larger than 0 after last update time
     *
     * @param detectorId
     * 		detector id
     * @param lastUpdateTimeEpochMs
     * 		last update time in milliseconds
     * @return the search request
     */
    private [CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest createInittedEverRequest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]long lastUpdateTimeEpochMs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.index.query.BoolQueryBuilder filterQuery = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.index.query.BoolQueryBuilder();
        [CtInvocationImpl][CtVariableReadImpl]filterQuery.filter([CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.index.query.QueryBuilders.termQuery([CtTypeAccessImpl]AnomalyResult.DETECTOR_ID_FIELD, [CtVariableReadImpl]detectorId));
        [CtInvocationImpl][CtVariableReadImpl]filterQuery.filter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.index.query.QueryBuilders.rangeQuery([CtTypeAccessImpl]AnomalyResult.EXECUTION_END_TIME_FIELD).gte([CtVariableReadImpl]lastUpdateTimeEpochMs));
        [CtInvocationImpl][CtVariableReadImpl]filterQuery.filter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.index.query.QueryBuilders.rangeQuery([CtTypeAccessImpl]AnomalyResult.ANOMALY_SCORE_FIELD).gt([CtLiteralImpl]0));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.builder.SearchSourceBuilder source = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.search.builder.SearchSourceBuilder().query([CtVariableReadImpl]filterQuery).size([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult.ANOMALY_RESULT_INDEX);
        [CtInvocationImpl][CtVariableReadImpl]request.source([CtVariableReadImpl]source);
        [CtReturnImpl]return [CtVariableReadImpl]request;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest createLatestAnomalyResultRequest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String detectorId, [CtParameterImpl][CtTypeReferenceImpl]long lastUpdateTimeEpochMs) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.index.query.BoolQueryBuilder filterQuery = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.index.query.BoolQueryBuilder();
        [CtInvocationImpl][CtVariableReadImpl]filterQuery.filter([CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.index.query.QueryBuilders.termQuery([CtTypeAccessImpl]AnomalyResult.DETECTOR_ID_FIELD, [CtVariableReadImpl]detectorId));
        [CtInvocationImpl][CtVariableReadImpl]filterQuery.filter([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.elasticsearch.index.query.QueryBuilders.rangeQuery([CtTypeAccessImpl]AnomalyResult.EXECUTION_END_TIME_FIELD).gte([CtVariableReadImpl]lastUpdateTimeEpochMs));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.sort.FieldSortBuilder sortQuery = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.search.sort.FieldSortBuilder([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult.EXECUTION_END_TIME_FIELD).order([CtTypeAccessImpl]SortOrder.DESC);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.search.builder.SearchSourceBuilder source = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.search.builder.SearchSourceBuilder().query([CtVariableReadImpl]filterQuery).size([CtLiteralImpl]1).sort([CtVariableReadImpl]sortQuery);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.elasticsearch.action.search.SearchRequest([CtFieldReadImpl]com.amazon.opendistroforelasticsearch.ad.model.AnomalyResult.ANOMALY_RESULT_INDEX);
        [CtInvocationImpl][CtVariableReadImpl]request.source([CtVariableReadImpl]source);
        [CtReturnImpl]return [CtVariableReadImpl]request;
    }
}
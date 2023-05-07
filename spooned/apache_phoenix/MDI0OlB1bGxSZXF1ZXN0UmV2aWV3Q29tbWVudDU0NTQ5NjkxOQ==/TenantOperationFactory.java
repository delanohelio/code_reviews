[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.phoenix.pherf.workload.mt.tenantoperation;
[CtImportImpl]import java.sql.SQLException;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.TenantGroup;
[CtUnresolvedImport]import org.apache.phoenix.pherf.rules.RulesApplier;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Noop;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.OperationStats;
[CtUnresolvedImport]import com.google.common.collect.Lists;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.UserDefinedOperation;
[CtUnresolvedImport]import com.google.common.hash.PrimitiveSink;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Upsert;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.sql.ResultSet;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.phoenix.pherf.rules.DataValue;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Scenario;
[CtUnresolvedImport]import com.google.common.base.Charsets;
[CtUnresolvedImport]import com.google.common.base.Function;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Query;
[CtUnresolvedImport]import com.google.common.hash.BloomFilter;
[CtImportImpl]import java.sql.Connection;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.EventGenerator;
[CtUnresolvedImport]import org.apache.phoenix.util.EnvironmentEdgeManager;
[CtImportImpl]import java.sql.PreparedStatement;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.QuerySet;
[CtUnresolvedImport]import com.google.common.hash.Funnel;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Ddl;
[CtUnresolvedImport]import org.apache.phoenix.pherf.util.PhoenixUtil;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.UserDefined;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.Operation;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.XMLConfigParser;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.LoadProfile;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.QueryOperation;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.PreScenarioOperation;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.UpsertOperation;
[CtUnresolvedImport]import com.google.common.annotations.VisibleForTesting;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.Column;
[CtUnresolvedImport]import org.apache.phoenix.pherf.workload.mt.NoopOperation;
[CtUnresolvedImport]import org.apache.phoenix.pherf.configuration.DataModel;
[CtClassImpl][CtJavaDocImpl]/**
 * Factory class for operations.
 * The class is responsible for creating new instances of various operation types.
 * Operations typically implement @see {@link TenantOperationImpl}
 * Operations that need to be executed are generated
 * by @see {@link EventGenerator}
 */
public class TenantOperationFactory {
    [CtClassImpl]private static class TenantView {
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String tenantId;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String viewName;

        [CtConstructorImpl]public TenantView([CtParameterImpl][CtTypeReferenceImpl]java.lang.String tenantId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String viewName) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tenantId = [CtVariableReadImpl]tenantId;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.viewName = [CtVariableReadImpl]viewName;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTenantId() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]tenantId;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getViewName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]viewName;
        }
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.util.PhoenixUtil phoenixUtil;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.DataModel model;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Scenario scenario;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.XMLConfigParser parser;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.rules.RulesApplier rulesApplier;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.LoadProfile loadProfile;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation> operationList = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.newArrayList();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.common.hash.BloomFilter<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView> tenantsLoaded;

    [CtConstructorImpl]public TenantOperationFactory([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.util.PhoenixUtil phoenixUtil, [CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.DataModel model, [CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Scenario scenario) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.phoenixUtil = [CtVariableReadImpl]phoenixUtil;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.model = [CtVariableReadImpl]model;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.scenario = [CtVariableReadImpl]scenario;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parser = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rulesApplier = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.rules.RulesApplier([CtVariableReadImpl]model);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.loadProfile = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.scenario.getLoadProfile();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.common.hash.Funnel<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView> tenantViewFunnel = [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.hash.Funnel<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void funnel([CtParameterImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView tenantView, [CtParameterImpl][CtTypeReferenceImpl]com.google.common.hash.PrimitiveSink into) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]into.putString([CtInvocationImpl][CtVariableReadImpl]tenantView.getTenantId(), [CtTypeAccessImpl]Charsets.UTF_8).putString([CtInvocationImpl][CtVariableReadImpl]tenantView.getViewName(), [CtTypeAccessImpl]Charsets.UTF_8);
            }
        };
        [CtLocalVariableImpl][CtTypeReferenceImpl]int numTenants = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.TenantGroup tg : [CtInvocationImpl][CtFieldReadImpl]loadProfile.getTenantDistribution()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]numTenants += [CtInvocationImpl][CtVariableReadImpl]tg.getNumTenants();
        }
        [CtAssignmentImpl][CtCommentImpl]// This holds the info whether the tenant view was created (initialized) or not.
        [CtFieldWriteImpl]tenantsLoaded = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.hash.BloomFilter.create([CtVariableReadImpl]tenantViewFunnel, [CtVariableReadImpl]numTenants, [CtLiteralImpl]0.01);
        [CtForEachImpl][CtCommentImpl]// Read the scenario definition and load the various operations.
        for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Noop noOp : [CtInvocationImpl][CtVariableReadImpl]scenario.getNoop()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation noopOperation = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.NoopOperation()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Noop getNoop() [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]noOp;
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.String getId() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]noOp.getId();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.OperationType getType() [CtBlockImpl]{
                    [CtReturnImpl]return [CtFieldReadImpl]OperationType.NO_OP;
                }
            };
            [CtInvocationImpl][CtFieldReadImpl]operationList.add([CtVariableReadImpl]noopOperation);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Upsert upsert : [CtInvocationImpl][CtVariableReadImpl]scenario.getUpsert()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation upsertOp = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.UpsertOperation()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Upsert getUpsert() [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]upsert;
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.String getId() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]upsert.getId();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.OperationType getType() [CtBlockImpl]{
                    [CtReturnImpl]return [CtFieldReadImpl]OperationType.UPSERT;
                }
            };
            [CtInvocationImpl][CtFieldReadImpl]operationList.add([CtVariableReadImpl]upsertOp);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.QuerySet querySet : [CtInvocationImpl][CtVariableReadImpl]scenario.getQuerySet()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Query query : [CtInvocationImpl][CtVariableReadImpl]querySet.getQuery()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation queryOp = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.QueryOperation()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Query getQuery() [CtBlockImpl]{
                        [CtReturnImpl]return [CtVariableReadImpl]query;
                    }

                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.String getId() [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]query.getId();
                    }

                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.OperationType getType() [CtBlockImpl]{
                        [CtReturnImpl]return [CtFieldReadImpl]OperationType.SELECT;
                    }
                };
                [CtInvocationImpl][CtFieldReadImpl]operationList.add([CtVariableReadImpl]queryOp);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.UserDefined udf : [CtInvocationImpl][CtVariableReadImpl]scenario.getUdf()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation udfOperation = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.UserDefinedOperation()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.UserDefined getUserFunction() [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]udf;
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.String getId() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]udf.getId();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.OperationType getType() [CtBlockImpl]{
                    [CtReturnImpl]return [CtFieldReadImpl]OperationType.USER_DEFINED;
                }
            };
            [CtInvocationImpl][CtFieldReadImpl]operationList.add([CtVariableReadImpl]udfOperation);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.phoenix.pherf.util.PhoenixUtil getPhoenixUtil() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]phoenixUtil;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.DataModel getModel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]model;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Scenario getScenario() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]scenario;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.Operation> getOperationsForScenario() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]operationList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl getOperation([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView tenantView = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.TenantView([CtInvocationImpl][CtVariableReadImpl]input.getTenantId(), [CtInvocationImpl][CtFieldReadImpl]scenario.getTableName());
        [CtIfImpl][CtCommentImpl]// Check if pre run ddls are needed.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]tenantsLoaded.mightContain([CtVariableReadImpl]tenantView)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Initialize the tenant using the pre scenario ddls.
            final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.PreScenarioOperation operation = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.PreScenarioOperation()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Ddl> getPreScenarioDdls() [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Ddl> ddls = [CtInvocationImpl][CtFieldReadImpl]scenario.getPreScenarioDdls();
                    [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]ddls == [CtLiteralImpl]null ? [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.Lists.<[CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Ddl>newArrayList() : [CtVariableReadImpl]ddls;
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]java.lang.String getId() [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]OperationType.PRE_RUN.name();
                }

                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.OperationType getType() [CtBlockImpl]{
                    [CtReturnImpl]return [CtFieldReadImpl]OperationType.PRE_RUN;
                }
            };
            [CtLocalVariableImpl][CtCommentImpl]// Initialize with the pre run operation.
            [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo preRunSample = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo([CtInvocationImpl][CtVariableReadImpl]input.getModelName(), [CtInvocationImpl][CtVariableReadImpl]input.getScenarioName(), [CtInvocationImpl][CtVariableReadImpl]input.getTableName(), [CtInvocationImpl][CtVariableReadImpl]input.getTenantGroupId(), [CtInvocationImpl][CtTypeAccessImpl]Operation.OperationType.PRE_RUN.name(), [CtInvocationImpl][CtVariableReadImpl]input.getTenantId(), [CtVariableReadImpl]operation);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl impl = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.PreScenarioTenantOperationImpl();
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Run the initialization operation.
                [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats stats = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]impl.getMethod().apply([CtVariableReadImpl]preRunSample);
                [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.info([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]phoenixUtil.getGSON().toJson([CtVariableReadImpl]stats));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.error([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failed to initialize tenant. [%s, %s] ", [CtFieldReadImpl][CtVariableReadImpl]tenantView.tenantId, [CtFieldReadImpl][CtVariableReadImpl]tenantView.viewName), [CtInvocationImpl][CtVariableReadImpl]e.fillInStackTrace());
            }
            [CtInvocationImpl][CtFieldReadImpl]tenantsLoaded.put([CtVariableReadImpl]tenantView);
        }
        [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]input.getOperation().getType()) {
            [CtCaseImpl]case [CtFieldReadImpl]NO_OP :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.NoopTenantOperationImpl();
            [CtCaseImpl]case [CtFieldReadImpl]SELECT :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.QueryTenantOperationImpl();
            [CtCaseImpl]case [CtFieldReadImpl]UPSERT :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.UpsertTenantOperationImpl();
            [CtCaseImpl]case [CtFieldReadImpl]USER_DEFINED :
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.UserDefinedOperationImpl();
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Unknown operation type");
        }
    }

    [CtClassImpl]class QueryTenantOperationImpl implements [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats> getMethod() [CtBlockImpl]{
            [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
                [CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats apply([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
                [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.QueryOperation operation = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.QueryOperation) ([CtVariableReadImpl]input.getOperation()));
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tenantGroup = [CtInvocationImpl][CtVariableReadImpl]input.getTenantGroupId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String opGroup = [CtInvocationImpl][CtVariableReadImpl]input.getOperationGroupId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tenantId = [CtInvocationImpl][CtVariableReadImpl]input.getTenantId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String scenarioName = [CtInvocationImpl][CtVariableReadImpl]input.getScenarioName();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtVariableReadImpl]input.getTableName();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Query query = [CtInvocationImpl][CtVariableReadImpl]operation.getQuery();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]long opCounter = [CtLiteralImpl]1;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String opName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s:%s:%s:%s:%s", [CtVariableReadImpl]scenarioName, [CtVariableReadImpl]tableName, [CtVariableReadImpl]opGroup, [CtVariableReadImpl]tenantGroup, [CtVariableReadImpl]tenantId);
                    [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.info([CtBinaryOperatorImpl][CtLiteralImpl]"\nExecuting query " + [CtInvocationImpl][CtVariableReadImpl]query.getStatement());
                    [CtLocalVariableImpl][CtCommentImpl]// TODO add explain plan output to the stats.
                    [CtTypeReferenceImpl]java.sql.Connection conn = [CtLiteralImpl]null;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement statement = [CtLiteralImpl]null;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.ResultSet rs = [CtLiteralImpl]null;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long startTime = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long resultRowCount = [CtLiteralImpl]0L;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long queryElapsedTime = [CtLiteralImpl]0L;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String queryIteration = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]opName + [CtLiteralImpl]":") + [CtVariableReadImpl]opCounter;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]conn = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.getConnection([CtVariableReadImpl]tenantId);
                        [CtInvocationImpl][CtVariableReadImpl]conn.setAutoCommit([CtLiteralImpl]true);
                        [CtAssignmentImpl][CtCommentImpl]// TODO dynamic statements
                        [CtCommentImpl]// final String statementString = query.getDynamicStatement(rulesApplier, scenario);
                        [CtVariableWriteImpl]statement = [CtInvocationImpl][CtVariableReadImpl]conn.prepareStatement([CtInvocationImpl][CtVariableReadImpl]query.getStatement());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isQuery = [CtInvocationImpl][CtVariableReadImpl]statement.execute();
                        [CtIfImpl]if ([CtVariableReadImpl]isQuery) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]rs = [CtInvocationImpl][CtVariableReadImpl]statement.getResultSet();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isSelectCountStatement = [CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]query.getStatement().toUpperCase().trim().contains([CtLiteralImpl]"COUNT(")) ? [CtLiteralImpl]true : [CtLiteralImpl]false;
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.util.Pair<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.Long> r = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.getResults([CtVariableReadImpl]query, [CtVariableReadImpl]rs, [CtVariableReadImpl]queryIteration, [CtVariableReadImpl]isSelectCountStatement, [CtVariableReadImpl]startTime);
                            [CtAssignmentImpl][CtVariableWriteImpl]resultRowCount = [CtInvocationImpl][CtVariableReadImpl]r.getFirst();
                            [CtAssignmentImpl][CtVariableWriteImpl]queryElapsedTime = [CtInvocationImpl][CtVariableReadImpl]r.getSecond();
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]conn.commit();
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.error([CtBinaryOperatorImpl][CtLiteralImpl]"Exception while executing query iteration " + [CtVariableReadImpl]queryIteration, [CtVariableReadImpl]e);
                    } finally [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rs != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]rs.close();

                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]statement != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]statement.close();

                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]conn != [CtLiteralImpl]null)[CtBlockImpl]
                                [CtInvocationImpl][CtVariableReadImpl]conn.close();

                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                            [CtCommentImpl]// swallow;
                        }
                    }
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtLiteralImpl]0, [CtVariableReadImpl]resultRowCount, [CtVariableReadImpl]queryElapsedTime);
                }
            };
        }
    }

    [CtClassImpl]class UpsertTenantOperationImpl implements [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats> getMethod() [CtBlockImpl]{
            [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
                [CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats apply([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
                [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]int batchSize = [CtInvocationImpl][CtFieldReadImpl]loadProfile.getBatchSize();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean useBatchApi = [CtBinaryOperatorImpl][CtVariableReadImpl]batchSize != [CtLiteralImpl]0;
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]int rowCount = [CtConditionalImpl]([CtVariableReadImpl]useBatchApi) ? [CtVariableReadImpl]batchSize : [CtLiteralImpl]1;
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.UpsertOperation operation = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.UpsertOperation) ([CtVariableReadImpl]input.getOperation()));
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tenantGroup = [CtInvocationImpl][CtVariableReadImpl]input.getTenantGroupId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String opGroup = [CtInvocationImpl][CtVariableReadImpl]input.getOperationGroupId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tenantId = [CtInvocationImpl][CtVariableReadImpl]input.getTenantId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Upsert upsert = [CtInvocationImpl][CtVariableReadImpl]operation.getUpsert();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtVariableReadImpl]input.getTableName();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String scenarioName = [CtInvocationImpl][CtVariableReadImpl]input.getScenarioName();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Column> columns = [CtInvocationImpl][CtVariableReadImpl]upsert.getColumn();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String opName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s:%s:%s:%s:%s", [CtVariableReadImpl]scenarioName, [CtVariableReadImpl]tableName, [CtVariableReadImpl]opGroup, [CtVariableReadImpl]tenantGroup, [CtVariableReadImpl]tenantId);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long rowsCreated = [CtLiteralImpl]0;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtLiteralImpl]0;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long duration;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long totalDuration;
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd HH:mm:ss");
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection connection = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.getConnection([CtVariableReadImpl]tenantId)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]connection.setAutoCommit([CtLiteralImpl]true);
                        [CtAssignmentImpl][CtVariableWriteImpl]startTime = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sql = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.buildSql([CtVariableReadImpl]columns, [CtVariableReadImpl]tableName);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.PreparedStatement stmt = [CtLiteralImpl]null;
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]stmt = [CtInvocationImpl][CtVariableReadImpl]connection.prepareStatement([CtVariableReadImpl]sql);
                            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]long i = [CtVariableReadImpl]rowCount; [CtBinaryOperatorImpl][CtVariableReadImpl]i > [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Operation " + [CtVariableReadImpl]opName) + [CtLiteralImpl]" executing ");
                                [CtAssignmentImpl][CtVariableWriteImpl]stmt = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.buildStatement([CtFieldReadImpl]rulesApplier, [CtFieldReadImpl]scenario, [CtVariableReadImpl]columns, [CtVariableReadImpl]stmt, [CtVariableReadImpl]simpleDateFormat);
                                [CtIfImpl]if ([CtVariableReadImpl]useBatchApi) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]stmt.addBatch();
                                } else [CtBlockImpl]{
                                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]rowsCreated += [CtInvocationImpl][CtVariableReadImpl]stmt.executeUpdate();
                                }
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException e) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.error([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Operation " + [CtVariableReadImpl]opName) + [CtLiteralImpl]" failed with exception ", [CtVariableReadImpl]e);
                            [CtThrowImpl]throw [CtVariableReadImpl]e;
                        } finally [CtBlockImpl]{
                            [CtIfImpl][CtCommentImpl]// Need to keep the statement open to send the remaining batch of updates
                            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]useBatchApi) && [CtBinaryOperatorImpl]([CtVariableReadImpl]stmt != [CtLiteralImpl]null)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]stmt.close();
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]connection != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]useBatchApi && [CtBinaryOperatorImpl]([CtVariableReadImpl]stmt != [CtLiteralImpl]null)) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] results = [CtInvocationImpl][CtVariableReadImpl]stmt.executeBatch();
                                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int x = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]x < [CtFieldReadImpl][CtVariableReadImpl]results.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]x++) [CtBlockImpl]{
                                        [CtLocalVariableImpl][CtTypeReferenceImpl]int result = [CtArrayReadImpl][CtVariableReadImpl]results[[CtVariableReadImpl]x];
                                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result < [CtLiteralImpl]1) [CtBlockImpl]{
                                            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String msg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to write update in batch (update count=" + [CtVariableReadImpl]result) + [CtLiteralImpl]")";
                                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]msg);
                                        }
                                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]rowsCreated += [CtVariableReadImpl]result;
                                    }
                                    [CtInvocationImpl][CtCommentImpl]// Close the statement after our last batch execution.
                                    [CtVariableReadImpl]stmt.close();
                                }
                                [CtTryImpl]try [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]connection.commit();
                                    [CtAssignmentImpl][CtVariableWriteImpl]duration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                                    [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Writer ( " + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().getName()) + [CtLiteralImpl]") committed Final Batch. Duration (") + [CtVariableReadImpl]duration) + [CtLiteralImpl]") Ms");
                                    [CtInvocationImpl][CtVariableReadImpl]connection.close();
                                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException e) [CtBlockImpl]{
                                    [CtInvocationImpl][CtCommentImpl]// Swallow since we are closing anyway
                                    [CtVariableReadImpl]e.printStackTrace();
                                }
                            }
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException throwables) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]throwables);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
                    }
                    [CtAssignmentImpl][CtVariableWriteImpl]totalDuration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtLiteralImpl]0, [CtVariableReadImpl]rowsCreated, [CtVariableReadImpl]totalDuration);
                }
            };
        }
    }

    [CtClassImpl]class PreScenarioTenantOperationImpl implements [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats> getMethod() [CtBlockImpl]{
            [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats apply([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.PreScenarioOperation operation = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.PreScenarioOperation) ([CtVariableReadImpl]input.getOperation()));
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tenantId = [CtInvocationImpl][CtVariableReadImpl]input.getTenantId();
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String tableName = [CtInvocationImpl][CtFieldReadImpl]scenario.getTableName();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis();
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]operation.getPreScenarioDdls().isEmpty()) [CtBlockImpl]{
                        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.sql.Connection conn = [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.getConnection([CtVariableReadImpl]tenantId)) [CtBlockImpl]{
                            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Ddl ddl : [CtInvocationImpl][CtFieldReadImpl]scenario.getPreScenarioDdls()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationFactory.LOGGER.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"\nExecuting DDL:" + [CtVariableReadImpl]ddl) + [CtLiteralImpl]" on tenantId:") + [CtVariableReadImpl]tenantId);
                                [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.executeStatement([CtInvocationImpl][CtVariableReadImpl]ddl.toString(), [CtVariableReadImpl]conn);
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ddl.getStatement().toUpperCase().contains([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]phoenixUtil.ASYNC_KEYWORD)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtFieldReadImpl]phoenixUtil.waitForAsyncIndexToFinish([CtInvocationImpl][CtVariableReadImpl]ddl.getTableName());
                                }
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.sql.SQLException throwables) [CtBlockImpl]{
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]throwables);
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
                        }
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long totalDuration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtLiteralImpl]0, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]operation.getPreScenarioDdls().size(), [CtVariableReadImpl]totalDuration);
                }
            };
        }
    }

    [CtClassImpl][CtAnnotationImpl]@com.google.common.annotations.VisibleForTesting
    class NoopTenantOperationImpl implements [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats> getMethod() [CtBlockImpl]{
            [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats apply([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.NoopOperation operation = [CtInvocationImpl](([CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.NoopOperation) ([CtVariableReadImpl]input.getOperation()));
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.configuration.Noop noop = [CtInvocationImpl][CtVariableReadImpl]operation.getNoop();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis();
                    [CtTryImpl][CtCommentImpl]// Sleep for the specified time to simulate idle time.
                    try [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MILLISECONDS.sleep([CtInvocationImpl][CtVariableReadImpl]noop.getIdleTime());
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long duration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtVariableReadImpl]duration);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long duration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtLiteralImpl]0, [CtVariableReadImpl]duration);
                    }
                }
            };
        }
    }

    [CtClassImpl]class UserDefinedOperationImpl implements [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationImpl {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats> getMethod() [CtBlockImpl]{
            [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.common.base.Function<[CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo, [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats>()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats apply([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.tenantoperation.TenantOperationInfo input) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// TODO : implement user defined operation invocation.
                    [CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]long duration = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.phoenix.util.EnvironmentEdgeManager.currentTimeMillis() - [CtVariableReadImpl]startTime;
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.phoenix.pherf.workload.mt.OperationStats([CtVariableReadImpl]input, [CtVariableReadImpl]startTime, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtVariableReadImpl]duration);
                }
            };
        }
    }
}
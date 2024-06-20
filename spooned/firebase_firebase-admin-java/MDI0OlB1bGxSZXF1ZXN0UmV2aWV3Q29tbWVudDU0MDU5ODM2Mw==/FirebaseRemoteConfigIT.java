[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Google LLC

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
[CtPackageDeclarationImpl]package com.google.firebase.remoteconfig;
[CtUnresolvedImport]import com.google.common.util.concurrent.MoreExecutors;
[CtUnresolvedImport]import com.google.api.core.ApiFutures;
[CtUnresolvedImport]import org.junit.BeforeClass;
[CtUnresolvedImport]import com.google.common.collect.ImmutableMap;
[CtUnresolvedImport]import com.google.firebase.testing.IntegrationTestUtils;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.google.api.core.ApiFutureCallback;
[CtImportImpl]import java.util.concurrent.Semaphore;
[CtUnresolvedImport]import org.junit.Test;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import static org.junit.Assert.assertNotEquals;
[CtUnresolvedImport]import com.google.api.core.ApiFuture;
[CtImportImpl]import java.util.concurrent.atomic.AtomicInteger;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtClassImpl]public class FirebaseRemoteConfigIT {
    [CtFieldImpl]private static [CtTypeReferenceImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfig remoteConfig;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long timestamp = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Parameter> PARAMETERS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of([CtLiteralImpl]"welcome_message_text", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Parameter().setDefaultValue([CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.ParameterValue.of([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"welcome to app %s", [CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.timestamp))).setConditionalValues([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.firebase.remoteconfig.ParameterValue>of([CtLiteralImpl]"ios_en", [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.ParameterValue.of([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"welcome to app en %s", [CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.timestamp)))).setDescription([CtLiteralImpl]"text for welcome message!"), [CtLiteralImpl]"header_text", [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Parameter().setDefaultValue([CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.ParameterValue.inAppDefault()));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.firebase.remoteconfig.ParameterGroup> PARAMETER_GROUPS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of([CtLiteralImpl]"new menu", [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.ParameterGroup().setDescription([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"New Menu %s", [CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.timestamp)).setParameters([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableMap.of([CtLiteralImpl]"pumpkin_spice_season", [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Parameter().setDefaultValue([CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.ParameterValue.of([CtLiteralImpl]"true")).setDescription([CtLiteralImpl]"Whether it's currently pumpkin spice season."))));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.firebase.remoteconfig.Condition> CONDITIONS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Condition([CtLiteralImpl]"ios_en", [CtLiteralImpl]"device.os == 'ios' && device.country in ['us', 'uk']").setTagColor([CtTypeAccessImpl]TagColor.INDIGO), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Condition([CtLiteralImpl]"android_en", [CtLiteralImpl]"device.os == 'android' && device.country in ['us', 'uk']"));

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Version VERSION = [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.Version.withDescription([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"promo config %s", [CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.timestamp));

    [CtMethodImpl][CtAnnotationImpl]@org.junit.BeforeClass
    public static [CtTypeReferenceImpl]void setUpClass() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig = [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance([CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.testing.IntegrationTestUtils.ensureDefaultApp());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTemplateOperations() throws [CtTypeReferenceImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get template to fetch the active template with correct etag
        final [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template oldTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.getTemplate();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template inputTemplate = [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.Template.fromJSON([CtInvocationImpl][CtVariableReadImpl]oldTemplate.toJSON());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String versionNumber = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]oldTemplate.getVersion().getVersionNumber();
        [CtInvocationImpl][CtCommentImpl]// modify template
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inputTemplate.setParameters([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETERS).setParameterGroups([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETER_GROUPS).setConditions([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.CONDITIONS).setVersion([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.VERSION);
        [CtLocalVariableImpl][CtCommentImpl]// validate template
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template validatedTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.validateTemplate([CtVariableReadImpl]inputTemplate);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]inputTemplate.getETag(), [CtInvocationImpl][CtVariableReadImpl]validatedTemplate.getETag());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETERS, [CtInvocationImpl][CtVariableReadImpl]validatedTemplate.getParameters());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETER_GROUPS, [CtInvocationImpl][CtVariableReadImpl]validatedTemplate.getParameterGroups());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.CONDITIONS, [CtInvocationImpl][CtVariableReadImpl]validatedTemplate.getConditions());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.VERSION, [CtInvocationImpl][CtVariableReadImpl]validatedTemplate.getVersion());
        [CtLocalVariableImpl][CtCommentImpl]// publish template
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template publishedTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.publishTemplate([CtVariableReadImpl]inputTemplate);
        [CtInvocationImpl]Assert.assertNotEquals([CtInvocationImpl][CtVariableReadImpl]inputTemplate.getETag(), [CtInvocationImpl][CtVariableReadImpl]publishedTemplate.getETag());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETERS, [CtInvocationImpl][CtVariableReadImpl]publishedTemplate.getParameters());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.PARAMETER_GROUPS, [CtInvocationImpl][CtVariableReadImpl]publishedTemplate.getParameterGroups());
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.CONDITIONS, [CtInvocationImpl][CtVariableReadImpl]publishedTemplate.getConditions());
        [CtInvocationImpl]Assert.assertNotEquals([CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.VERSION, [CtInvocationImpl][CtVariableReadImpl]publishedTemplate.getVersion());
        [CtLocalVariableImpl][CtCommentImpl]// get template
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template currentTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.getTemplate();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]publishedTemplate, [CtVariableReadImpl]currentTemplate);
        [CtLocalVariableImpl][CtCommentImpl]// get template at version
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template atVersionTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.getTemplateAtVersion([CtVariableReadImpl]versionNumber);
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]oldTemplate, [CtVariableReadImpl]atVersionTemplate);
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]versionNumber, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]atVersionTemplate.getVersion().getVersionNumber());
        [CtLocalVariableImpl][CtCommentImpl]// rollback template
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template rolledBackTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.rollback([CtVariableReadImpl]versionNumber);
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Rollback to version %s", [CtVariableReadImpl]versionNumber), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rolledBackTemplate.getVersion().getDescription());
        [CtLocalVariableImpl][CtCommentImpl]// get template to verify rollback
        [CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template activeTemplate = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.getTemplate();
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]rolledBackTemplate, [CtVariableReadImpl]activeTemplate);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testListVersions() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> versions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.Template template = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.getTemplate();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]3; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]template = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.publishTemplate([CtVariableReadImpl]template);
            [CtInvocationImpl][CtVariableReadImpl]versions.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]template.getVersion().getVersionNumber());
        }
        [CtLocalVariableImpl][CtCommentImpl]// Test list by batches
        final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger collected = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicInteger([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.ListVersionsPage page = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.listVersionsAsync().get();
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]page != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.Version version : [CtInvocationImpl][CtVariableReadImpl]page.getValues()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]versions.contains([CtInvocationImpl][CtVariableReadImpl]version.getVersionNumber())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]collected.incrementAndGet();
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]page = [CtInvocationImpl][CtVariableReadImpl]page.getNextPage();
        } 
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]versions.size(), [CtInvocationImpl][CtVariableReadImpl]collected.get());
        [CtInvocationImpl][CtCommentImpl]// Test iterate all
        [CtVariableReadImpl]collected.set([CtLiteralImpl]0);
        [CtAssignmentImpl][CtVariableWriteImpl]page = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.listVersionsAsync().get();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.Version version : [CtInvocationImpl][CtVariableReadImpl]page.iterateAll()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]versions.contains([CtInvocationImpl][CtVariableReadImpl]version.getVersionNumber())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collected.incrementAndGet();
            }
        }
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]versions.size(), [CtInvocationImpl][CtVariableReadImpl]collected.get());
        [CtInvocationImpl][CtCommentImpl]// Test with list options
        [CtVariableReadImpl]collected.set([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.ListVersionsOptions listOptions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.ListVersionsOptions.builder().setPageSize([CtLiteralImpl]2).build();
        [CtAssignmentImpl][CtVariableWriteImpl]page = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.listVersionsAsync([CtVariableReadImpl]listOptions).get();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.Version version : [CtInvocationImpl][CtVariableReadImpl]page.getValues()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]versions.contains([CtInvocationImpl][CtVariableReadImpl]version.getVersionNumber())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collected.incrementAndGet();
            }
        }
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]collected.get());
        [CtInvocationImpl][CtCommentImpl]// Test iterate async
        [CtVariableReadImpl]collected.set([CtLiteralImpl]0);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.Semaphore semaphore = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.Semaphore([CtLiteralImpl]0);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.lang.Throwable> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.api.core.ApiFuture<[CtTypeReferenceImpl]com.google.firebase.remoteconfig.ListVersionsPage> pageFuture = [CtInvocationImpl][CtFieldReadImpl]com.google.firebase.remoteconfig.FirebaseRemoteConfigIT.remoteConfig.listVersionsAsync();
        [CtInvocationImpl][CtTypeAccessImpl]com.google.api.core.ApiFutures.addCallback([CtVariableReadImpl]pageFuture, [CtNewClassImpl]new [CtTypeReferenceImpl]com.google.api.core.ApiFutureCallback<[CtTypeReferenceImpl]com.google.firebase.remoteconfig.ListVersionsPage>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onFailure([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]error.set([CtVariableReadImpl]t);
                [CtInvocationImpl][CtVariableReadImpl]semaphore.release();
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.ListVersionsPage result) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.firebase.remoteconfig.Version version : [CtInvocationImpl][CtVariableReadImpl]result.iterateAll()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]versions.contains([CtInvocationImpl][CtVariableReadImpl]version.getVersionNumber())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]collected.incrementAndGet();
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]semaphore.release();
            }
        }, [CtInvocationImpl][CtTypeAccessImpl]com.google.common.util.concurrent.MoreExecutors.directExecutor());
        [CtInvocationImpl][CtVariableReadImpl]semaphore.acquire();
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]versions.size(), [CtInvocationImpl][CtVariableReadImpl]collected.get());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtVariableReadImpl]error.get());
    }
}
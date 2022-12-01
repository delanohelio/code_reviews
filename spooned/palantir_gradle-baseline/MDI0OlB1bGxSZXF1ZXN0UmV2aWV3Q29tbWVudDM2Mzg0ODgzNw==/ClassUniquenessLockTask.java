[CompilationUnitImpl][CtCommentImpl]/* (c) Copyright 2020 Palantir Technologies Inc. All rights reserved.

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
[CtPackageDeclarationImpl]package com.palantir.baseline.tasks;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.gradle.util.GFileUtils;
[CtUnresolvedImport]import org.gradle.api.provider.SetProperty;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import org.gradle.api.artifacts.Configuration;
[CtUnresolvedImport]import org.gradle.api.tasks.TaskAction;
[CtUnresolvedImport]import org.gradle.api.Task;
[CtUnresolvedImport]import org.gradle.api.tasks.OutputFile;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import org.gradle.api.artifacts.ModuleVersionIdentifier;
[CtUnresolvedImport]import org.gradle.api.specs.Spec;
[CtUnresolvedImport]import org.gradle.api.GradleException;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.gradle.api.tasks.CacheableTask;
[CtUnresolvedImport]import org.gradle.api.DefaultTask;
[CtUnresolvedImport]import org.gradle.api.tasks.Input;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtAnnotationImpl]@org.gradle.api.tasks.CacheableTask
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"VisibilityModifier")
public class ClassUniquenessLockTask extends [CtTypeReferenceImpl]org.gradle.api.DefaultTask {
    [CtFieldImpl][CtCommentImpl]// not marking this as an Input, because we want to re-run if the *contents* of a configuration changes
    private final [CtTypeReferenceImpl]java.io.File lockFile;

    [CtFieldImpl]public final [CtTypeReferenceImpl]org.gradle.api.provider.SetProperty<[CtTypeReferenceImpl]java.lang.String> configurations;

    [CtConstructorImpl]public ClassUniquenessLockTask() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configurations = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getObjects().setProperty([CtFieldReadImpl]java.lang.String.class);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lockFile = [CtInvocationImpl][CtInvocationImpl]getProject().file([CtLiteralImpl]"baseline-class-uniqueness.lock");
        [CtInvocationImpl]onlyIf([CtNewClassImpl]new [CtTypeReferenceImpl]org.gradle.api.specs.Spec<[CtTypeReferenceImpl]org.gradle.api.Task>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean isSatisfiedBy([CtParameterImpl][CtTypeReferenceImpl]org.gradle.api.Task task) [CtBlockImpl]{
                [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]configurations.get().isEmpty();
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method exists purely for up-to-dateness purposes - we want to re-run if the contents of a configuration
     * changes.
     */
    [CtAnnotationImpl]@org.gradle.api.tasks.Input
    public final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]java.lang.String>> getContentsOfAllConfigurations() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]configurations.get().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtInvocationImpl][CtTypeAccessImpl]java.util.function.Function.identity(), [CtLambdaImpl]([CtParameterImpl] name) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration configuration = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getConfigurations().getByName([CtVariableReadImpl]name);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configuration.getIncoming().getResolutionResult().getAllComponents().stream().map([CtLambdaImpl]([CtParameterImpl] resolvedComponentResult) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resolvedComponentResult.getModuleVersion().toString()).collect([CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.toImmutableList());[CtCommentImpl]// Gradle requires this to be Serializable

        }));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.gradle.api.tasks.OutputFile
    public final [CtTypeReferenceImpl]java.io.File getLockFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]lockFile;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.gradle.api.tasks.TaskAction
    public final [CtTypeReferenceImpl]void doIt() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String>> resultsByConfiguration = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]configurations.get().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtInvocationImpl][CtTypeAccessImpl]java.util.function.Function.identity(), [CtLambdaImpl]([CtParameterImpl] configurationName) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palantir.baseline.tasks.ClassUniquenessAnalyzer analyzer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palantir.baseline.tasks.ClassUniquenessAnalyzer([CtInvocationImpl][CtInvocationImpl]getProject().getLogger());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.gradle.api.artifacts.Configuration configuration = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getConfigurations().getByName([CtVariableReadImpl]configurationName);
            [CtInvocationImpl][CtVariableReadImpl]analyzer.analyzeConfiguration([CtVariableReadImpl]configuration);
            [CtLocalVariableImpl][CtTypeReferenceImpl]Collection<[CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]org.gradle.api.artifacts.ModuleVersionIdentifier>> problemJars = [CtInvocationImpl][CtVariableReadImpl]analyzer.getDifferingProblemJars();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]problemJars.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder stringBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtForEachImpl][CtCommentImpl]// TODO(dfox): ensure we iterate through problemJars in a stable order
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl]org.gradle.api.artifacts.ModuleVersionIdentifier> clashingJars : [CtVariableReadImpl]problemJars) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clashingJars.stream().map([CtLambdaImpl]([CtParameterImpl] mvi) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]mvi.getGroup() + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]mvi.getName()).sorted().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", ", [CtLiteralImpl]"[", [CtLiteralImpl]"]"))).append([CtLiteralImpl]'\n');
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]analyzer.getDifferingSharedClassesInProblemJars([CtVariableReadImpl]clashingJars).stream().sorted().forEach([CtLambdaImpl]([CtParameterImpl] className) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtLiteralImpl]"  - ");
                    [CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtVariableReadImpl]className);
                    [CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtLiteralImpl]'\n');
                });
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]stringBuilder.toString());
        }));
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean conflictsFound = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resultsByConfiguration.values().stream().anyMatch([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Optional::isPresent);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]conflictsFound) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// this is desirable because if means if people apply the plugin to lots of projects which are already
            [CtCommentImpl]// compliant, they don't get loads of noisy lockfiles created.
            ensureLockfileDoesNotExist();
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder stringBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtLiteralImpl]"# Run ./gradlew checkClassUniquenessLock --write-locks to update this file\n\n");
            [CtInvocationImpl][CtCommentImpl]// TODO(dfox): make configuration order stable!
            [CtVariableReadImpl]resultsByConfiguration.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String configuration,[CtParameterImpl]java.util.Optional<java.lang.String> contents) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]contents.isPresent()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtLiteralImpl]"## ").append([CtVariableReadImpl]configuration).append([CtLiteralImpl]"\n");
                    [CtInvocationImpl][CtVariableReadImpl]stringBuilder.append([CtInvocationImpl][CtVariableReadImpl]contents.get());
                }
            });
            [CtInvocationImpl]ensureLockfileContains([CtInvocationImpl][CtVariableReadImpl]stringBuilder.toString());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void ensureLockfileContains([CtParameterImpl][CtTypeReferenceImpl]java.lang.String expected) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getGradle().getStartParameter().isWriteDependencyLocks()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.gradle.util.GFileUtils.writeFile([CtVariableReadImpl]expected, [CtFieldReadImpl]lockFile);
            [CtInvocationImpl][CtInvocationImpl]getLogger().lifecycle([CtLiteralImpl]"Updated {}", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getRootDir().toPath().relativize([CtInvocationImpl][CtFieldReadImpl]lockFile.toPath()));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String onDisk = [CtInvocationImpl][CtTypeAccessImpl]org.gradle.util.GFileUtils.readFile([CtFieldReadImpl]lockFile);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]onDisk.equals([CtVariableReadImpl]expected)) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.gradle.api.GradleException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]lockFile + [CtLiteralImpl]" is out of date, please run `./gradlew ") + [CtLiteralImpl]"checkClassUniquenessLock --write-locks` to update this file");
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void ensureLockfileDoesNotExist() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]lockFile.exists()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getGradle().getStartParameter().isWriteDependencyLocks()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.gradle.util.GFileUtils.deleteQuietly([CtFieldReadImpl]lockFile);
                [CtInvocationImpl][CtInvocationImpl]getLogger().lifecycle([CtLiteralImpl]"Deleted {}", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getProject().getRootDir().toPath().relativize([CtInvocationImpl][CtFieldReadImpl]lockFile.toPath()));
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.gradle.api.GradleException([CtBinaryOperatorImpl][CtFieldReadImpl]lockFile + [CtLiteralImpl]" should not exist (as no problems were found).");
            }
        }
    }
}
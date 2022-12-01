[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Red Hat, Inc. and/or its affiliates.

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
[CtPackageDeclarationImpl]package org.kie.kogito.quarkus.deployment;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.kie.kogito.codegen.process.persistence.proto.ProtoEnum;
[CtUnresolvedImport]import org.infinispan.protostream.annotations.ProtoEnumValue;
[CtUnresolvedImport]import org.kie.internal.kogito.codegen.Generated;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.jboss.jandex.DotName;
[CtUnresolvedImport]import org.jboss.jandex.Type;
[CtUnresolvedImport]import org.jboss.jandex.ClassInfo;
[CtUnresolvedImport]import org.jboss.jandex.FieldInfo;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.kie.kogito.codegen.process.persistence.proto.Proto;
[CtImportImpl]import java.lang.reflect.Modifier;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.kie.kogito.codegen.GeneratedFile;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.jboss.jandex.IndexView;
[CtUnresolvedImport]import org.jboss.jandex.Type.Kind;
[CtUnresolvedImport]import org.jboss.jandex.AnnotationValue;
[CtImportImpl]import java.util.Optional;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.kie.kogito.codegen.process.persistence.proto.ProtoMessage;
[CtUnresolvedImport]import org.jboss.jandex.AnnotationInstance;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.kie.kogito.codegen.process.persistence.proto.AbstractProtoGenerator;
[CtImportImpl]import static java.util.stream.Collectors.toSet;
[CtClassImpl]public class JandexProtoGenerator extends [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.AbstractProtoGenerator<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.jboss.jandex.DotName ENUM_VALUE_ANNOTATION = [CtInvocationImpl][CtTypeAccessImpl]org.jboss.jandex.DotName.createSimple([CtInvocationImpl][CtFieldReadImpl]org.infinispan.protostream.annotations.ProtoEnumValue.class.getName());

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.IndexView index;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.DotName generatedAnnotation;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.DotName variableInfoAnnotation;

    [CtConstructorImpl]private JandexProtoGenerator([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo persistenceClass, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> modelClasses, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> dataClasses, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.IndexView index, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName generatedAnnotation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName variableInfoAnnotation) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]persistenceClass, [CtVariableReadImpl]modelClasses, [CtVariableReadImpl]dataClasses);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.index = [CtVariableReadImpl]index;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.generatedAnnotation = [CtVariableReadImpl]generatedAnnotation;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.variableInfoAnnotation = [CtVariableReadImpl]variableInfoAnnotation;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto protoOfDataClasses([CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl]java.lang.String... headers) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto proto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto([CtVariableReadImpl]packageName, [CtVariableReadImpl]headers);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo clazz : [CtFieldReadImpl]dataClasses) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]clazz.superName() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]java.lang.Enum.class.getName().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.superName().toString())) [CtBlockImpl]{
                    [CtInvocationImpl]enumFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]clazz, [CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl]messageFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]clazz, [CtFieldReadImpl]index, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]proto;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Error while generating proto for data model", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto generate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageComment, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldComment, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo dataModel, [CtParameterImpl]java.lang.String... headers) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto proto = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto([CtVariableReadImpl]packageName, [CtVariableReadImpl]headers);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataModel.superName() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]java.lang.Enum.class.getName().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataModel.superName().toString())) [CtBlockImpl]{
                [CtInvocationImpl]enumFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]dataModel, [CtLiteralImpl]null);
            } else [CtBlockImpl]{
                [CtInvocationImpl]messageFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]dataModel, [CtFieldReadImpl]index, [CtVariableReadImpl]packageName, [CtVariableReadImpl]messageComment, [CtVariableReadImpl]fieldComment);
            }
            [CtReturnImpl]return [CtVariableReadImpl]proto;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Error while generating proto for data model", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> getPersistenceClassParams() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> parameters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]persistenceClass).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ClassInfo::constructors).flatMap([CtLambdaImpl]([CtParameterImpl] values) -> [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]values.isEmpty() ? [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty() : [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl][CtVariableReadImpl]values.get([CtLiteralImpl]0))).ifPresent([CtLambdaImpl]([CtParameterImpl] mi) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mi.parameters().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.name().toString()).forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]parameters::add));
        [CtReturnImpl]return [CtVariableReadImpl]parameters;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getProcessIds() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]modelClasses.stream().map([CtLambdaImpl]([CtParameterImpl] c) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationInstance instance = [CtInvocationImpl][CtVariableReadImpl]c.classAnnotation([CtInvocationImpl][CtTypeAccessImpl]org.jboss.jandex.DotName.createSimple([CtInvocationImpl][CtFieldReadImpl]org.kie.internal.kogito.codegen.Generated.class.getCanonicalName()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]instance == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationValue value = [CtInvocationImpl][CtVariableReadImpl]instance.value([CtLiteralImpl]"reference");
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]value.asString();
        }).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).collect([CtInvocationImpl]java.util.stream.Collectors.toSet());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoMessage messageFromClass([CtParameterImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto proto, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo clazz, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.IndexView index, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageComment, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fieldComment) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isHidden([CtVariableReadImpl]clazz)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// since class is marked as hidden skip processing of that class
            return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]clazz.simpleName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String altName = [CtInvocationImpl]getReferenceOfModel([CtVariableReadImpl]clazz, [CtLiteralImpl]"name");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]altName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]name = [CtVariableReadImpl]altName;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoMessage message = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoMessage([CtVariableReadImpl]name, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]packageName == [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.name().prefix().toString() : [CtVariableReadImpl]packageName);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.FieldInfo pd : [CtInvocationImpl][CtVariableReadImpl]clazz.fields()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String completeFieldComment = [CtVariableReadImpl]fieldComment;
            [CtIfImpl][CtCommentImpl]// ignore static and/or transient fields
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isStatic([CtInvocationImpl][CtVariableReadImpl]pd.flags()) || [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Modifier.isTransient([CtInvocationImpl][CtVariableReadImpl]pd.flags())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationInstance variableInfo = [CtInvocationImpl][CtVariableReadImpl]pd.annotation([CtFieldReadImpl]variableInfoAnnotation);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]variableInfo != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]completeFieldComment = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]fieldComment + [CtLiteralImpl]"\n @VariableInfo(tags=\"") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]variableInfo.value([CtLiteralImpl]"tags").asString()) + [CtLiteralImpl]"\")";
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fieldTypeString = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().name().toString();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName fieldType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().name();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String protoType;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().kind() == [CtFieldReadImpl]org.jboss.jandex.Type.Kind.PARAMETERIZED_TYPE) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]fieldTypeString = [CtLiteralImpl]"Collection";
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.jboss.jandex.Type> typeParameters = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().asParameterizedType().arguments();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]typeParameters.isEmpty()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Field " + [CtInvocationImpl][CtVariableReadImpl]pd.name()) + [CtLiteralImpl]" of class ") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.name().toString()) + [CtLiteralImpl]" uses collection without type information");
                }
                [CtAssignmentImpl][CtVariableWriteImpl]fieldType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]typeParameters.get([CtLiteralImpl]0).name();
                [CtAssignmentImpl][CtVariableWriteImpl]protoType = [CtInvocationImpl]protoType([CtInvocationImpl][CtVariableReadImpl]fieldType.toString());
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]protoType = [CtInvocationImpl]protoType([CtVariableReadImpl]fieldTypeString);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]protoType == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo classInfo = [CtInvocationImpl][CtVariableReadImpl]index.getClassByName([CtVariableReadImpl]fieldType);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]classInfo == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find class info in jandex index for " + [CtVariableReadImpl]fieldType);
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isHidden([CtVariableReadImpl]classInfo)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]classInfo.superName() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]java.lang.Enum.class.getName().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]classInfo.superName().toString())) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoEnum another = [CtInvocationImpl]enumFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]classInfo, [CtVariableReadImpl]packageName);
                        [CtAssignmentImpl][CtVariableWriteImpl]protoType = [CtInvocationImpl][CtVariableReadImpl]another.getName();
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoMessage another = [CtInvocationImpl]messageFromClass([CtVariableReadImpl]proto, [CtVariableReadImpl]classInfo, [CtVariableReadImpl]index, [CtVariableReadImpl]packageName, [CtVariableReadImpl]messageComment, [CtVariableReadImpl]fieldComment);
                        [CtAssignmentImpl][CtVariableWriteImpl]protoType = [CtInvocationImpl][CtVariableReadImpl]another.getName();
                    }
                }
            }
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]message.addField([CtInvocationImpl]applicabilityByType([CtVariableReadImpl]fieldTypeString), [CtVariableReadImpl]protoType, [CtInvocationImpl][CtVariableReadImpl]pd.name()).setComment([CtVariableReadImpl]completeFieldComment);
        }
        [CtInvocationImpl][CtVariableReadImpl]message.setComment([CtVariableReadImpl]messageComment);
        [CtInvocationImpl][CtVariableReadImpl]proto.addMessage([CtVariableReadImpl]message);
        [CtReturnImpl]return [CtVariableReadImpl]message;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoEnum enumFromClass([CtParameterImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto proto, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo clazz, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String packageName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]clazz.simpleName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String altName = [CtInvocationImpl]getReferenceOfModel([CtVariableReadImpl]clazz, [CtLiteralImpl]"name");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]altName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]name = [CtVariableReadImpl]altName;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoEnum modelEnum = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoEnum([CtVariableReadImpl]name, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]packageName == [CtLiteralImpl]null ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.name().prefix().toString() : [CtVariableReadImpl]packageName);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]clazz.fields().stream().filter([CtLambdaImpl]([CtParameterImpl] f) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]f.name().startsWith([CtLiteralImpl]"$")).forEach([CtLambdaImpl]([CtParameterImpl] f) -> [CtInvocationImpl]addEnumField([CtVariableReadImpl]f, [CtVariableReadImpl]modelEnum));
        [CtInvocationImpl][CtVariableReadImpl]proto.addEnum([CtVariableReadImpl]modelEnum);
        [CtReturnImpl]return [CtVariableReadImpl]modelEnum;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addEnumField([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.FieldInfo field, [CtParameterImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoEnum pEnum) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationInstance annotation = [CtInvocationImpl][CtVariableReadImpl]field.annotation([CtFieldReadImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator.ENUM_VALUE_ANNOTATION);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer ordinal = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationValue number = [CtInvocationImpl][CtVariableReadImpl]annotation.value([CtLiteralImpl]"number");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]number != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]ordinal = [CtInvocationImpl][CtVariableReadImpl]number.asInt();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ordinal == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ordinal = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pEnum.getFields().values().stream().mapToInt([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.Integer::intValue).max().orElse([CtUnaryOperatorImpl]-[CtLiteralImpl]1) + [CtLiteralImpl]1;
        }
        [CtInvocationImpl][CtVariableReadImpl]pEnum.addField([CtInvocationImpl][CtVariableReadImpl]field.name(), [CtVariableReadImpl]ordinal);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.kie.kogito.codegen.GeneratedFile> generateModelClassProto([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo modelClazz) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String processId = [CtInvocationImpl]getReferenceOfModel([CtVariableReadImpl]modelClazz, [CtLiteralImpl]"reference");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]getReferenceOfModel([CtVariableReadImpl]modelClazz, [CtLiteralImpl]"name");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]processId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.Proto modelProto = [CtInvocationImpl]generate([CtLiteralImpl]"@Indexed", [CtTypeAccessImpl]org.kie.kogito.quarkus.deployment.INDEX_COMMENT, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelClazz.name().prefix().toString() + [CtLiteralImpl]".") + [CtVariableReadImpl]processId, [CtVariableReadImpl]modelClazz, [CtLiteralImpl]"import \"kogito-index.proto\";", [CtLiteralImpl]"import \"kogito-types.proto\";", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"option kogito_model = \"" + [CtVariableReadImpl]name) + [CtLiteralImpl]"\";", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"option kogito_id = \"" + [CtVariableReadImpl]processId) + [CtLiteralImpl]"\";");
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelProto.getMessages().isEmpty()) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// no messages, nothing to do
                return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.kie.kogito.codegen.process.persistence.proto.ProtoMessage modelMessage = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelProto.getMessages().stream().filter([CtLambdaImpl]([CtParameterImpl] msg) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.getName().equals([CtVariableReadImpl]name)).findFirst().orElseThrow([CtLambdaImpl]() -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Unable to find model message"));
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modelMessage.addField([CtLiteralImpl]"optional", [CtLiteralImpl]"org.kie.kogito.index.model.KogitoMetadata", [CtLiteralImpl]"metadata").setComment([CtTypeAccessImpl]org.kie.kogito.quarkus.deployment.INDEX_COMMENT);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtInvocationImpl]generateProtoFiles([CtVariableReadImpl]processId, [CtVariableReadImpl]modelProto));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String getReferenceOfModel([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo modelClazz, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationInstance generatedData = [CtInvocationImpl][CtVariableReadImpl]modelClazz.classAnnotation([CtFieldReadImpl]generatedAnnotation);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]generatedData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]generatedData.value([CtVariableReadImpl]name).asString();
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean isHidden([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo modelClazz) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.AnnotationInstance generatedData = [CtInvocationImpl][CtVariableReadImpl]modelClazz.classAnnotation([CtFieldReadImpl]generatedAnnotation);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]generatedData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]generatedData.value([CtLiteralImpl]"hidden").asBoolean();
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.Builder<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo, [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator> builder([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.IndexView index, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName generatedAnnotation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName variableInfoAnnotation) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator.JandexProtoGeneratorBuilder([CtVariableReadImpl]index, [CtVariableReadImpl]generatedAnnotation, [CtVariableReadImpl]variableInfoAnnotation);
    }

    [CtClassImpl]private static class JandexProtoGeneratorBuilder extends [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.AbstractProtoGeneratorBuilder<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo, [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator.JandexProtoGeneratorBuilder.class);

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.IndexView index;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.DotName generatedAnnotation;

        [CtFieldImpl]private final [CtTypeReferenceImpl]org.jboss.jandex.DotName variableInfoAnnotation;

        [CtConstructorImpl]private JandexProtoGeneratorBuilder([CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.IndexView index, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName generatedAnnotation, [CtParameterImpl][CtTypeReferenceImpl]org.jboss.jandex.DotName variableInfoAnnotation) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.index = [CtVariableReadImpl]index;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.generatedAnnotation = [CtVariableReadImpl]generatedAnnotation;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.variableInfoAnnotation = [CtVariableReadImpl]variableInfoAnnotation;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> extractDataClasses([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> modelClasses) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]dataClasses != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]modelClasses == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator.JandexProtoGeneratorBuilder.LOGGER.info([CtLiteralImpl]"Using provided dataClasses instead of extracting from modelClasses");
                [CtReturnImpl]return [CtFieldReadImpl]dataClasses;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> dataModelClasses = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.ClassInfo modelClazz : [CtVariableReadImpl]modelClasses) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.jboss.jandex.FieldInfo pd : [CtInvocationImpl][CtVariableReadImpl]modelClazz.fields()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().name().toString().startsWith([CtLiteralImpl]"java.lang") || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().name().toString().equals([CtInvocationImpl][CtFieldReadImpl]java.util.Date.class.getCanonicalName())) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]dataModelClasses.add([CtInvocationImpl][CtFieldReadImpl]index.getClassByName([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pd.type().name()));
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]dataModelClasses;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator build([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.jboss.jandex.ClassInfo> modelClasses) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kie.kogito.quarkus.deployment.JandexProtoGenerator([CtFieldReadImpl]persistenceClass, [CtVariableReadImpl]modelClasses, [CtInvocationImpl]extractDataClasses([CtVariableReadImpl]modelClasses), [CtFieldReadImpl]index, [CtFieldReadImpl]generatedAnnotation, [CtFieldReadImpl]variableInfoAnnotation);
        }
    }
}
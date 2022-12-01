[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

 http://aws.amazon.com/apache2.0

or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package software.amazon.smithy.waiters;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ResourceShape;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import software.amazon.smithy.model.Model;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.BooleanShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.FloatShape;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ShapeVisitor;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.IntegerShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.DocumentShape;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.OperationShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.BigIntegerShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.Shape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ServiceShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.UnionShape;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.function.Supplier;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.StringShape;
[CtUnresolvedImport]import software.amazon.smithy.model.traits.RangeTrait;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ShortShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.DoubleShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.BigDecimalShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.SetShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.LongShape;
[CtUnresolvedImport]import software.amazon.smithy.model.traits.LengthTrait;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.StructureShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ListShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.MapShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.ByteShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.TimestampShape;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.BlobShape;
[CtUnresolvedImport]import software.amazon.smithy.jmespath.ast.LiteralExpression;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import software.amazon.smithy.model.shapes.MemberShape;
[CtClassImpl][CtJavaDocImpl]/**
 * Generates fake data from a modeled shape for static JMESPath analysis.
 */
final class ModelRuntimeTypeGenerator implements [CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ShapeVisitor<[CtTypeReferenceImpl]java.lang.Object> {
    [CtFieldImpl]private final [CtTypeReferenceImpl]software.amazon.smithy.model.Model model;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> visited = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtConstructorImpl]ModelRuntimeTypeGenerator([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.Model model) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.model = [CtVariableReadImpl]model;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object blobShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.BlobShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"blob";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object booleanShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.BooleanShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object byteShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ByteShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object shortShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ShortShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object integerShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.IntegerShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object longShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.LongShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object floatShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.FloatShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object doubleShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.DoubleShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object bigIntegerShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.BigIntegerShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object bigDecimalShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.BigDecimalShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]computeRange([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object documentShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.DocumentShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]software.amazon.smithy.jmespath.ast.LiteralExpression.ANY;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object stringShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StringShape shape) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create a random string that does not exceed or go under the length trait.
        [CtTypeReferenceImpl]int chars = [CtInvocationImpl]computeLength([CtVariableReadImpl]shape);
        [CtReturnImpl][CtCommentImpl]// Fill a string with "a"'s up to chars.
        return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtVariableReadImpl]chars]).replace([CtLiteralImpl]"\u0000", [CtLiteralImpl]"a");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object listShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ListShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]createListOrSet([CtVariableReadImpl]shape, [CtInvocationImpl][CtVariableReadImpl]shape.getMember());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object setShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.SetShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]createListOrSet([CtVariableReadImpl]shape, [CtInvocationImpl][CtVariableReadImpl]shape.getMember());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object createListOrSet([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.Shape shape, [CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape member) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]withCopiedVisitors([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl]computeLength([CtVariableReadImpl]shape);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]size);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object memberValue = [CtInvocationImpl][CtVariableReadImpl]member.accept([CtThisAccessImpl]this);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]memberValue != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]memberValue);
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        });
    }

    [CtMethodImpl][CtCommentImpl]// Visits members and mutates a copy of the current set of
    [CtCommentImpl]// visited shapes rather than a shared set. This a shape to
    [CtCommentImpl]// be used multiple times in the closure of a single shape
    [CtCommentImpl]// without causing the reuse of the shape to always be
    [CtCommentImpl]// assume to be a recursive type.
    private [CtTypeReferenceImpl]java.lang.Object withCopiedVisitors([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]java.lang.Object> supplier) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Account for recursive shapes at the current
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape> visitedCopy = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtFieldReadImpl]visited);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object result = [CtInvocationImpl][CtVariableReadImpl]supplier.get();
        [CtAssignmentImpl][CtFieldWriteImpl]visited = [CtVariableReadImpl]visitedCopy;
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object mapShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MapShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]withCopiedVisitors([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl]computeLength([CtVariableReadImpl]shape);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]shape.getKey().accept([CtThisAccessImpl]this)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object memberValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shape.getValue().accept([CtThisAccessImpl]this);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]result.put([CtBinaryOperatorImpl][CtVariableReadImpl]key + [CtVariableReadImpl]i, [CtVariableReadImpl]memberValue);
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object structureShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.StructureShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]structureOrUnion([CtVariableReadImpl]shape);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object unionShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.UnionShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]structureOrUnion([CtVariableReadImpl]shape);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Object structureOrUnion([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.Shape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]withCopiedVisitors([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape member : [CtInvocationImpl][CtVariableReadImpl]shape.members()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object memberValue = [CtInvocationImpl][CtVariableReadImpl]member.accept([CtThisAccessImpl]this);
                [CtInvocationImpl][CtVariableReadImpl]result.put([CtInvocationImpl][CtVariableReadImpl]member.getMemberName(), [CtVariableReadImpl]memberValue);
            }
            [CtReturnImpl]return [CtVariableReadImpl]result;
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object memberShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.MemberShape shape) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Account for recursive shapes.
        [CtCommentImpl]// A false return value means it was in the set.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]visited.add([CtVariableReadImpl]shape)) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]software.amazon.smithy.jmespath.ast.LiteralExpression.ANY;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]// Rather than fail on broken models during waiter validation,
        [CtCommentImpl]// return an ANY to get *some* validation.
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]model.getShape([CtInvocationImpl][CtVariableReadImpl]shape.getTarget()).map([CtLambdaImpl]([CtParameterImpl] target) -> [CtInvocationImpl][CtVariableReadImpl]target.accept([CtThisAccessImpl]this)).orElse([CtTypeAccessImpl]LiteralExpression.ANY);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object timestampShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.TimestampShape shape) [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]software.amazon.smithy.jmespath.ast.LiteralExpression.NUMBER;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object operationShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.OperationShape shape) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtInvocationImpl][CtVariableReadImpl]shape.toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object resourceShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ResourceShape shape) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtInvocationImpl][CtVariableReadImpl]shape.toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object serviceShape([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.ServiceShape shape) [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtInvocationImpl][CtVariableReadImpl]shape.toString());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int computeLength([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.Shape shape) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create a random string that does not exceed or go under the length trait.
        [CtTypeReferenceImpl]int chars = [CtLiteralImpl]2;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]shape.hasTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.LengthTrait.class)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]software.amazon.smithy.model.traits.LengthTrait trait = [CtInvocationImpl][CtVariableReadImpl]shape.expectTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.LengthTrait.class);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMin().isPresent()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]chars = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]chars, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMin().get().intValue());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMax().isPresent()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]chars = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]chars, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMax().get().intValue());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]chars;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double computeRange([CtParameterImpl][CtTypeReferenceImpl]software.amazon.smithy.model.shapes.Shape shape) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create a random string that does not exceed or go under the length trait.
        [CtTypeReferenceImpl]double i = [CtLiteralImpl]8;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]shape.hasTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.RangeTrait.class)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]software.amazon.smithy.model.traits.RangeTrait trait = [CtInvocationImpl][CtVariableReadImpl]shape.expectTrait([CtFieldReadImpl]software.amazon.smithy.model.traits.RangeTrait.class);
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMin().isPresent()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]i = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMin().get().doubleValue());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMax().isPresent()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]i = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trait.getMax().get().doubleValue());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]i;
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
=======================================================================
 */
[CtPackageDeclarationImpl]package org.tensorflow.keras.backend;
[CtUnresolvedImport]import org.tensorflow.op.core.Variable;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.tensorflow.keras.backend.tf.ConfusionMatrix;
[CtUnresolvedImport]import org.tensorflow.keras.backend.tf.Tuple;
[CtUnresolvedImport]import org.tensorflow.Session;
[CtUnresolvedImport]import org.tensorflow.Operand;
[CtUnresolvedImport]import org.tensorflow.Graph;
[CtUnresolvedImport]import org.tensorflow.types.TFloat32;
[CtUnresolvedImport]import org.tensorflow.types.TFloat16;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import org.tensorflow.op.core.Squeeze;
[CtUnresolvedImport]import org.tensorflow.ndarray.NdArraySequence;
[CtUnresolvedImport]import org.tensorflow.types.family.TType;
[CtUnresolvedImport]import org.tensorflow.ExecutionEnvironment;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.tensorflow.Tensor;
[CtUnresolvedImport]import org.tensorflow.EagerSession;
[CtUnresolvedImport]import org.tensorflow.keras.backend.tf.NN;
[CtUnresolvedImport]import org.tensorflow.types.TInt32;
[CtUnresolvedImport]import org.tensorflow.types.family.TNumber;
[CtUnresolvedImport]import org.tensorflow.types.TBool;
[CtUnresolvedImport]import org.tensorflow.types.TBfloat16;
[CtUnresolvedImport]import org.tensorflow.types.TFloat64;
[CtUnresolvedImport]import org.tensorflow.types.TUint8;
[CtUnresolvedImport]import org.tensorflow.op.math.Mean;
[CtUnresolvedImport]import org.tensorflow.op.nn.SoftmaxCrossEntropyWithLogits;
[CtUnresolvedImport]import org.tensorflow.op.core.ReduceSum;
[CtUnresolvedImport]import org.tensorflow.DataType;
[CtUnresolvedImport]import org.tensorflow.types.TInt64;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.tensorflow.ndarray.Shape;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.tensorflow.op.Ops;
[CtClassImpl][CtJavaDocImpl]/**
 * Keras backend methods
 */
public class K {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]double Epsilon = [CtLiteralImpl]1.0E-7;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]float EpsilonF = [CtLiteralImpl]1.0E-7F;

    [CtMethodImpl]public static final [CtTypeReferenceImpl]double epsilon() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.tensorflow.keras.backend.K.Epsilon;
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand epsilonConstant([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtFieldReadImpl]org.tensorflow.keras.backend.K.Epsilon);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand epsilonConstant([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtFieldReadImpl]org.tensorflow.keras.backend.K.Epsilon), [CtVariableReadImpl]dtype);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand one([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]1);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand one([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]1), [CtVariableReadImpl]dtype);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand minusOne([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand minusOne([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1), [CtVariableReadImpl]dtype);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand zero([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]0);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand zero([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]0), [CtVariableReadImpl]dtype);
    }

    [CtMethodImpl]public static final [CtTypeReferenceImpl]org.tensorflow.Operand constant([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]double number, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]number), [CtVariableReadImpl]dtype);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand clip([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]double minValue, [CtParameterImpl][CtTypeReferenceImpl]double maxValue) [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]x != [CtLiteralImpl]null : [CtLiteralImpl]"Operand x must not be null";
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]x.asOutput().dataType();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]maxValue < [CtVariableReadImpl]minValue) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]double tmp = [CtVariableReadImpl]maxValue;
            [CtAssignmentImpl][CtVariableWriteImpl]maxValue = [CtVariableReadImpl]minValue;
            [CtAssignmentImpl][CtVariableWriteImpl]minValue = [CtVariableReadImpl]tmp;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand minValueConstant = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]minValue), [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand maxValueConstant = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]maxValue), [CtVariableReadImpl]dtype);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.clipByValue([CtVariableReadImpl]x, [CtVariableReadImpl]minValueConstant, [CtVariableReadImpl]maxValueConstant);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand mean([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.mean([CtVariableReadImpl]tf, [CtVariableReadImpl]x, [CtLiteralImpl]null, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand mean([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand axis) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.mean([CtVariableReadImpl]tf, [CtVariableReadImpl]x, [CtVariableReadImpl]axis, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand mean([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]boolean keepDims) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.mean([CtVariableReadImpl]tf, [CtVariableReadImpl]x, [CtLiteralImpl]null, [CtVariableReadImpl]keepDims);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand mean([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand axis, [CtParameterImpl][CtTypeReferenceImpl]boolean keepDims) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]x.asOutput().dataType() == [CtFieldReadImpl]org.tensorflow.types.TBool.DTYPE) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]x = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]x, [CtTypeAccessImpl]TFloat32.DTYPE);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]axis == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]axis = [CtInvocationImpl]org.tensorflow.keras.backend.K.allAxis([CtVariableReadImpl]tf, [CtVariableReadImpl]x);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.mean([CtVariableReadImpl]x, [CtVariableReadImpl]axis, [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.op.math.Mean.keepDims([CtVariableReadImpl]keepDims));
    }

    [CtMethodImpl][CtCommentImpl]// alias for mean
    public static [CtTypeReferenceImpl]org.tensorflow.Operand reduceMean([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand axis, [CtParameterImpl][CtTypeReferenceImpl]boolean keepDims) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.mean([CtVariableReadImpl]tf, [CtVariableReadImpl]x, [CtVariableReadImpl]axis, [CtVariableReadImpl]keepDims);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand maximum([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand x, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand y) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]y = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]y, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]x.asOutput().dataType());
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.maximum([CtVariableReadImpl]x, [CtVariableReadImpl]y);
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.tensorflow.types.family.TType> [CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> sqrt([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> x) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]x.asOutput().dataType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> zero = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]0), [CtVariableReadImpl]dType);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> inf = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Float.[CtFieldReferenceImpl]POSITIVE_INFINITY), [CtVariableReadImpl]dType);
        [CtAssignmentImpl][CtVariableWriteImpl]x = [CtInvocationImpl][CtVariableReadImpl]tf.clipByValue([CtVariableReadImpl]x, [CtVariableReadImpl]zero, [CtVariableReadImpl]inf);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sqrt([CtVariableReadImpl]x);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.ndarray.Shape merge([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape a, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape b) [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.numDimensions() == [CtInvocationImpl][CtVariableReadImpl]b.numDimensions() : [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Shapes %s and %s are incompatible", [CtVariableReadImpl]a, [CtVariableReadImpl]b);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] array = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtInvocationImpl][CtVariableReadImpl]a.numDimensions()];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]a.numDimensions(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.size([CtVariableReadImpl]i) != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]b.size([CtVariableReadImpl]i) != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) [CtBlockImpl]{
                    [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.size([CtVariableReadImpl]i) == [CtInvocationImpl][CtVariableReadImpl]b.size([CtVariableReadImpl]i) : [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Shapes %s and %s are incompatible", [CtVariableReadImpl]a, [CtVariableReadImpl]b);
                }
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]array[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]a.size([CtVariableReadImpl]i);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]array[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]b.size([CtVariableReadImpl]i);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.ndarray.Shape.of([CtVariableReadImpl]array);
    }

    [CtMethodImpl][CtCommentImpl]// this is from nn in Python, I could not find it in the Java frameworks.
    public static [CtTypeReferenceImpl]org.tensorflow.Operand sigmoidCrossEntropyWithLogits([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand labels, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand logits) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape lablesShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]labels.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape logitsShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape newShape = [CtInvocationImpl]org.tensorflow.keras.backend.K.merge([CtVariableReadImpl]lablesShape, [CtVariableReadImpl]logitsShape);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand zeros = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.zerosLike([CtVariableReadImpl]logits), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().dataType());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand cond = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.greaterEqual([CtVariableReadImpl]logits, [CtVariableReadImpl]zeros);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand relu_logits = [CtInvocationImpl][CtVariableReadImpl]tf.select([CtVariableReadImpl]cond, [CtVariableReadImpl]logits, [CtVariableReadImpl]zeros);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand neg_abs_logits = [CtInvocationImpl][CtVariableReadImpl]tf.select([CtVariableReadImpl]cond, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.neg([CtVariableReadImpl]logits), [CtVariableReadImpl]logits);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.add([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]relu_logits, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.mul([CtVariableReadImpl]logits, [CtVariableReadImpl]labels)), [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.log1p([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.exp([CtVariableReadImpl]neg_abs_logits)));
    }

    [CtMethodImpl][CtCommentImpl]// TODO need to walk back identity until it hits something else
    [CtCommentImpl]// not sure how to get the input nodes for the Operand.
    private static [CtTypeReferenceImpl]org.tensorflow.Operand backtrackIdentity([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand output) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// while(!output.op().type().equals("Identity"))
        [CtCommentImpl]// output = output.op().output(0);
        return [CtVariableReadImpl]output;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand binary_crossentropy([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand target, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand output, [CtParameterImpl][CtTypeReferenceImpl]boolean fromLogits) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]fromLogits) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.sigmoidCrossEntropyWithLogits([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]output instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.tensorflow.op.core.Variable)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env().isEager())) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// output = backtrackIdentity(output); // TODO - this does not work, goes infinite loop
            if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().type().equals([CtLiteralImpl]"Sigmoid")) [CtBlockImpl]{
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().numOutputs() == [CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().output([CtLiteralImpl]0);
                [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.sigmoidCrossEntropyWithLogits([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.asOutput().dataType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl]org.tensorflow.keras.backend.K.one([CtVariableReadImpl]tf, [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand epsilonConst = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.keras.backend.K.epsilonConstant([CtVariableReadImpl]tf, [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand oneMinusEpsilonConst = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]one, [CtVariableReadImpl]epsilonConst);
        [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtVariableReadImpl]tf.clipByValue([CtVariableReadImpl]output, [CtVariableReadImpl]epsilonConst, [CtVariableReadImpl]oneMinusEpsilonConst);
        [CtLocalVariableImpl][CtCommentImpl]// Compute cross entropy from probabilities.
        [CtTypeReferenceImpl]org.tensorflow.Operand bce = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.mul([CtVariableReadImpl]target, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.log([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.add([CtVariableReadImpl]output, [CtVariableReadImpl]epsilonConst)));
        [CtAssignmentImpl][CtVariableWriteImpl]bce = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.add([CtVariableReadImpl]bce, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.mul([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]one, [CtVariableReadImpl]target), [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.log([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.add([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]one, [CtVariableReadImpl]output), [CtVariableReadImpl]epsilonConst))));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand result = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.neg([CtVariableReadImpl]bce);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand categorical_crossentropy([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand target, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand output, [CtParameterImpl][CtTypeReferenceImpl]boolean fromLogits) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.categorical_crossentropy([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output, [CtVariableReadImpl]fromLogits, [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand categorical_crossentropy([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand target, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand output, [CtParameterImpl][CtTypeReferenceImpl]boolean fromLogits, [CtParameterImpl][CtTypeReferenceImpl]int axis) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]fromLogits) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.softmax_cross_entropy_with_logits([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]output instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.tensorflow.op.core.Variable)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env().isEager())) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// TODO output = backtrackIdentity(output); doesn't seem to work with Java version.
            if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().type().equals([CtLiteralImpl]"Softmax")) [CtBlockImpl]{
                [CtAssertImpl]assert [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().numOutputs() == [CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().output([CtLiteralImpl]0);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand op = [CtInvocationImpl]org.tensorflow.keras.backend.K.softmax_cross_entropy_with_logits([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output);
                [CtReturnImpl]return [CtVariableReadImpl]op;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.asOutput().dataType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl]org.tensorflow.keras.backend.K.one([CtVariableReadImpl]tf, [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand epsilonConst = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.keras.backend.K.epsilonConstant([CtVariableReadImpl]tf, [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand oneMinusepsilonConst = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]one, [CtVariableReadImpl]epsilonConst);
        [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.div([CtVariableReadImpl]output, [CtInvocationImpl][CtVariableReadImpl]tf.reduceSum([CtVariableReadImpl]output, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]axis), [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.op.core.ReduceSum.keepDims([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE)));
        [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtVariableReadImpl]tf.clipByValue([CtVariableReadImpl]output, [CtVariableReadImpl]epsilonConst, [CtVariableReadImpl]oneMinusepsilonConst);
        [CtLocalVariableImpl][CtCommentImpl]// Compute cross entropy from probabilities.
        [CtTypeReferenceImpl]org.tensorflow.Operand cce = [CtInvocationImpl][CtVariableReadImpl]tf.reduceSum([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.mul([CtVariableReadImpl]target, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.log([CtVariableReadImpl]output)), [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]axis), [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.op.core.ReduceSum.keepDims([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE));
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.neg([CtVariableReadImpl]cce);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand flatten([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand t) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape shape = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.ndarray.Shape.of([CtLiteralImpl]1L);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]t, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]shape));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand sparse_categorical_crossentropy([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand target, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand output, [CtParameterImpl][CtTypeReferenceImpl]boolean fromLogits, [CtParameterImpl][CtTypeReferenceImpl]int axis) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.asOutput().dataType();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]output instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.tensorflow.op.core.Variable)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env().isEager())) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// TODO output = backtrackIdentity(output); doesn't seem to work with Java version.
            if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.op().type().equals([CtLiteralImpl]"Softmax")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// assert output.op().numOutputs() == 1;
                [CtCommentImpl]// When softmax activation function is used for output operation, we
                [CtCommentImpl]// use logits from the softmax function directly to compute loss in order
                [CtCommentImpl]// to prevent collapsing zero when training.
                [CtCommentImpl]// TODO assert len(output.op.inputs) == 1
                [CtCommentImpl]// TODO output = output.op.inputs[0]
                [CtVariableWriteImpl]fromLogits = [CtLiteralImpl]true;
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]fromLogits) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand epsilonConst = [CtInvocationImpl]org.tensorflow.keras.backend.K.epsilonConstant([CtVariableReadImpl]tf, [CtVariableReadImpl]dType);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl]org.tensorflow.keras.backend.K.one([CtVariableReadImpl]tf, [CtVariableReadImpl]dType);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand oneMinusEpsilonConst = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]one, [CtVariableReadImpl]epsilonConst);
            [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtVariableReadImpl]tf.clipByValue([CtVariableReadImpl]output, [CtVariableReadImpl]epsilonConst, [CtVariableReadImpl]oneMinusEpsilonConst);
            [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.log([CtVariableReadImpl]output);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape outputShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int outputRank = [CtInvocationImpl][CtVariableReadImpl]outputShape.numDimensions();
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]axis %= [CtVariableReadImpl]outputRank;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]axis < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]axis += [CtVariableReadImpl]outputRank;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]axis != [CtBinaryOperatorImpl]([CtVariableReadImpl]outputRank - [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] axisNew = [CtInvocationImpl]org.tensorflow.keras.backend.K.moveAxisToEnd([CtVariableReadImpl]axis, [CtVariableReadImpl]outputRank);
            [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.linalg.transpose([CtVariableReadImpl]output, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]axisNew));
        }
        [CtAssignmentImpl][CtVariableWriteImpl]target = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]target, [CtTypeAccessImpl]TInt64.DTYPE);
        [CtAssignmentImpl][CtCommentImpl]// TODO Try to adjust the shape so that rank of labels = rank of logits - 1.
        [CtVariableWriteImpl]outputShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape targetShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int targetRank = [CtInvocationImpl][CtVariableReadImpl]targetShape.numDimensions();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateShape = [CtBinaryOperatorImpl][CtVariableReadImpl]targetRank != [CtBinaryOperatorImpl]([CtVariableReadImpl]outputRank - [CtLiteralImpl]1);
        [CtIfImpl]if ([CtVariableReadImpl]updateShape) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// TODO check to see if this is right
            [CtVariableWriteImpl]target = [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]target, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1L));[CtCommentImpl]// flatten

            [CtAssignmentImpl][CtVariableWriteImpl]output = [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]output, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtNewArrayImpl]new [CtTypeReferenceImpl]long[]{ [CtUnaryOperatorImpl]-[CtLiteralImpl]1L, [CtInvocationImpl][CtVariableReadImpl]outputShape.size([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]outputShape.numDimensions() - [CtLiteralImpl]1) }));
        }
        [CtLocalVariableImpl][CtCommentImpl]// call nn.nn.sparse_softmax_cross_entropy_with_logits_v2
        [CtTypeReferenceImpl]org.tensorflow.Operand loss = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.keras.backend.tf.NN.sparse_softmax_cross_entropy_with_logits([CtVariableReadImpl]tf, [CtVariableReadImpl]target, [CtVariableReadImpl]output);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]updateShape && [CtBinaryOperatorImpl]([CtVariableReadImpl]outputRank >= [CtLiteralImpl]3)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] dims = [CtInvocationImpl][CtVariableReadImpl]outputShape.asArray();
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] newDims = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]dims.length - [CtLiteralImpl]1];
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]dims, [CtLiteralImpl]0, [CtVariableReadImpl]newDims, [CtLiteralImpl]0, [CtFieldReadImpl][CtVariableReadImpl]newDims.length);
            [CtAssignmentImpl][CtVariableWriteImpl]loss = [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]loss, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]newDims));
        }
        [CtReturnImpl]return [CtVariableReadImpl]loss;
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]int[] allAxis([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand op) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int rank = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]op.asOutput().shape().numDimensions();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] ranks = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtVariableReadImpl]rank];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]rank; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]ranks[[CtVariableReadImpl]i] = [CtVariableReadImpl]i;
        }
        [CtReturnImpl]return [CtVariableReadImpl]ranks;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand allAxis([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand op) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] ranks = [CtInvocationImpl]org.tensorflow.keras.backend.K.allAxis([CtVariableReadImpl]op);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]ranks);
    }

    [CtMethodImpl][CtCommentImpl]// TODO shouldn't these be in tensorflow itself under nn?
    private static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.tensorflow.types.family.TType, [CtTypeParameterImpl]U extends [CtTypeReferenceImpl]org.tensorflow.types.family.TNumber> [CtTypeReferenceImpl]org.tensorflow.Operand moveDimToEnd([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand tensor, [CtParameterImpl][CtTypeReferenceImpl]int dim_index, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand rank) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl]org.tensorflow.keras.backend.K.one([CtVariableReadImpl]tf, [CtTypeAccessImpl]TInt32.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T>> concatList = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]tf.range([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtVariableReadImpl]dim_index), [CtVariableReadImpl]one, [CtVariableReadImpl]one), [CtInvocationImpl][CtVariableReadImpl]tf.range([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtBinaryOperatorImpl][CtVariableReadImpl]dim_index + [CtLiteralImpl]1), [CtVariableReadImpl]rank, [CtVariableReadImpl]one));
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.linalg.transpose([CtVariableReadImpl]tensor, [CtInvocationImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]U>) ([CtVariableReadImpl]tf.concat([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T>>) (concatList)), [CtInvocationImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]U>) ([CtVariableReadImpl]tf.constant([CtLiteralImpl]0)))))));
    }

    [CtMethodImpl]private static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.tensorflow.types.family.TType, [CtTypeParameterImpl]U extends [CtTypeReferenceImpl]org.tensorflow.types.family.TNumber> [CtTypeReferenceImpl]org.tensorflow.Operand flattenOuterDims([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand logits) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand zero = [CtInvocationImpl]org.tensorflow.keras.backend.K.zero([CtVariableReadImpl]tf, [CtTypeAccessImpl]TInt64.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl]org.tensorflow.keras.backend.K.one([CtVariableReadImpl]tf, [CtTypeAccessImpl]TInt64.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand minusOne = [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtLocalVariableImpl][CtCommentImpl]// Shape logitsShape = logits.asOutput().shape();
        [CtCommentImpl]// long lastDimSize = logitsShape.size(logitsShape.numDimensions()-1);
        [CtCommentImpl]// if(!tf.scope().env().isEager()) {
        [CtTypeReferenceImpl]org.tensorflow.ndarray.Shape shape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int ndims = [CtInvocationImpl][CtVariableReadImpl]shape.numDimensions();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]shape.hasUnknownDimension()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long product = [CtLiteralImpl]1L;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean productValid = [CtLiteralImpl]true;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]ndims - [CtLiteralImpl]2; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long d = [CtInvocationImpl][CtVariableReadImpl]shape.size([CtVariableReadImpl]i);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]d == [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]productValid = [CtLiteralImpl]false;
                    [CtBreakImpl]break;
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]product *= [CtVariableReadImpl]d;
            }
            [CtIfImpl]if ([CtVariableReadImpl]productValid) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape outputShape = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.ndarray.Shape.of([CtVariableReadImpl]product, [CtInvocationImpl][CtVariableReadImpl]shape.size([CtBinaryOperatorImpl][CtVariableReadImpl]ndims - [CtLiteralImpl]1));
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]logits, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtInvocationImpl][CtVariableReadImpl]outputShape.asArray()));
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// }
        [CtTypeReferenceImpl]org.tensorflow.Operand rank = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.rank([CtVariableReadImpl]logits), [CtTypeAccessImpl]TInt64.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand rankMinusOne = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]rank, [CtVariableReadImpl]one);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand last_dim_size = [CtInvocationImpl][CtVariableReadImpl]tf.slice([CtInvocationImpl][CtVariableReadImpl]tf.shape([CtVariableReadImpl]logits), [CtVariableReadImpl]rankMinusOne, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]1));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand concat = [CtInvocationImpl][CtVariableReadImpl]tf.concat([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtNewArrayImpl]new [CtTypeReferenceImpl]int[]{ [CtUnaryOperatorImpl]-[CtLiteralImpl]1 }), [CtVariableReadImpl]last_dim_size), [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]0));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]zero, [CtVariableReadImpl]concat);
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]int[] moveAxisToEnd([CtParameterImpl][CtTypeReferenceImpl]int axis, [CtParameterImpl][CtTypeReferenceImpl]int outputRank) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] axisNew = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtVariableReadImpl]outputRank];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]axis; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]axisNew[[CtVariableReadImpl]i] = [CtVariableReadImpl]i;
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]axis + [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]outputRank; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]axisNew[[CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1] = [CtVariableReadImpl]i;
        }
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]axisNew[[CtBinaryOperatorImpl][CtVariableReadImpl]outputRank - [CtLiteralImpl]1] = [CtVariableReadImpl]axis;
        [CtReturnImpl]return [CtVariableReadImpl]axisNew;
    }

    [CtMethodImpl][CtCommentImpl]// TODO, maybe part of Shape ??
    private static [CtTypeReferenceImpl]boolean shapeIsCompatible([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape a, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape b) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.numDimensions() != [CtInvocationImpl][CtVariableReadImpl]b.numDimensions()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]a.numDimensions(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long aSize = [CtInvocationImpl][CtVariableReadImpl]a.size([CtVariableReadImpl]i);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long bSize = [CtInvocationImpl][CtVariableReadImpl]b.size([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]aSize != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) && [CtBinaryOperatorImpl]([CtVariableReadImpl]bSize != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]aSize != [CtVariableReadImpl]bSize)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtCommentImpl]// TODO these are "nn" ops
    public static [CtTypeReferenceImpl]org.tensorflow.Operand softmax_cross_entropy_with_logits([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand labels, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand logits) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.softmax_cross_entropy_with_logits([CtVariableReadImpl]tf, [CtVariableReadImpl]labels, [CtVariableReadImpl]logits, [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.tensorflow.Operand softmax_cross_entropy_with_logits([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand labels, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand logits, [CtParameterImpl][CtTypeReferenceImpl]int axis) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]axis = [CtBinaryOperatorImpl][CtVariableReadImpl]axis % [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().shape().numDimensions();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]axis < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]axis += [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().shape().numDimensions();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand minusOne = [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand precise_logits = [CtVariableReadImpl]logits;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand one = [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]1L);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean convertToFloat32 = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().dataType() == [CtFieldReadImpl]org.tensorflow.types.TFloat16.DTYPE) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().dataType() == [CtFieldReadImpl]org.tensorflow.types.TBfloat16.DTYPE);
        [CtIfImpl]if ([CtVariableReadImpl]convertToFloat32) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]precise_logits = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]logits, [CtTypeAccessImpl]TFloat32.DTYPE);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]precise_logits.asOutput().dataType();
        [CtAssignmentImpl][CtVariableWriteImpl]labels = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]labels, [CtVariableReadImpl]dtype);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand inputRank = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtVariableReadImpl]tf.rank([CtVariableReadImpl]precise_logits), [CtTypeAccessImpl]TInt64.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand inputRankMinusOne = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]inputRank, [CtVariableReadImpl]one), [CtTypeAccessImpl]TInt64.DTYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape shape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().shape();
        [CtIfImpl][CtCommentImpl]// Move the dim to the end if dim is not the last dimension.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]axis != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]axis != [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]precise_logits.asOutput().shape().numDimensions() - [CtLiteralImpl]1))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]precise_logits = [CtInvocationImpl]org.tensorflow.keras.backend.K.moveDimToEnd([CtVariableReadImpl]tf, [CtVariableReadImpl]precise_logits, [CtVariableReadImpl]axis, [CtVariableReadImpl]inputRank);
            [CtAssignmentImpl][CtVariableWriteImpl]labels = [CtInvocationImpl]org.tensorflow.keras.backend.K.moveDimToEnd([CtVariableReadImpl]tf, [CtVariableReadImpl]labels, [CtVariableReadImpl]axis, [CtVariableReadImpl]inputRank);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape inputShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]precise_logits.asOutput().shape();
        [CtAssignmentImpl][CtVariableWriteImpl]precise_logits = [CtInvocationImpl]org.tensorflow.keras.backend.K.flattenOuterDims([CtVariableReadImpl]tf, [CtVariableReadImpl]precise_logits);
        [CtAssignmentImpl][CtVariableWriteImpl]labels = [CtInvocationImpl]org.tensorflow.keras.backend.K.flattenOuterDims([CtVariableReadImpl]tf, [CtVariableReadImpl]labels);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.op.nn.SoftmaxCrossEntropyWithLogits smax = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.nn.softmaxCrossEntropyWithLogits([CtVariableReadImpl]precise_logits, [CtVariableReadImpl]labels);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand cost = [CtInvocationImpl][CtVariableReadImpl]smax.loss();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand outputShape = [CtInvocationImpl][CtVariableReadImpl]tf.slice([CtInvocationImpl][CtVariableReadImpl]tf.constant([CtInvocationImpl][CtVariableReadImpl]inputShape.asArray()), [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtNewArrayImpl]new [CtTypeReferenceImpl]long[]{ [CtLiteralImpl]0 }), [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtNewArrayImpl]new [CtTypeReferenceImpl]long[]{ [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]inputShape.numDimensions() - [CtLiteralImpl]1 }));
        [CtAssignmentImpl][CtVariableWriteImpl]cost = [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]cost, [CtVariableReadImpl]outputShape);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env().isGraph() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]shape.hasUnknownDimension())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] array = [CtInvocationImpl][CtVariableReadImpl]shape.asArray();
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] newArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]array.length - [CtLiteralImpl]1];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]axis < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]axis = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shape.numDimensions() + [CtVariableReadImpl]axis;
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]axis; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]newArray[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]shape.size([CtVariableReadImpl]i);
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]axis + [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]shape.numDimensions(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]newArray[[CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1] = [CtInvocationImpl][CtVariableReadImpl]shape.size([CtVariableReadImpl]i);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape newShape = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.ndarray.Shape.of([CtVariableReadImpl]newArray);
            [CtAssignmentImpl][CtVariableWriteImpl]cost = [CtInvocationImpl][CtVariableReadImpl]tf.reshape([CtVariableReadImpl]cost, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtInvocationImpl][CtVariableReadImpl]newShape.asArray()));
        }
        [CtIfImpl]if ([CtVariableReadImpl]convertToFloat32) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cost = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.dtypes.cast([CtVariableReadImpl]cost, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]logits.asOutput().dataType());
        }
        [CtReturnImpl]return [CtVariableReadImpl]cost;
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.tensorflow.types.family.TType> [CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> map([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> input, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T>, [CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T>> mapFunc) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtArrayTypeReferenceImpl]long[] concatenate([CtParameterImpl][CtTypeReferenceImpl]long first, [CtParameterImpl]long... remaining) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] dims = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]remaining.length + [CtLiteralImpl]1];
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]remaining, [CtLiteralImpl]0, [CtVariableReadImpl]dims, [CtLiteralImpl]1, [CtFieldReadImpl][CtVariableReadImpl]remaining.length);
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]dims[[CtLiteralImpl]0] = [CtVariableReadImpl]first;
        [CtReturnImpl]return [CtVariableReadImpl]dims;
    }

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.tensorflow.ExecutionEnvironment, [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer>> uidMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Associates a string prefix with an integer counter in a TensorFlow graph.
     *
     * <p>Example:
     *
     * <pre>
     * get_uid('dense')
     * 1
     * get_uid('dense')
     * 2
     * </pre>
     *
     * @param tf
     * 		the TensorFlow Ops
     * @param prefix
     * 		String prefix to index.
     * @return Unique integer ID.
     */
    public static [CtTypeReferenceImpl]int getUid([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ExecutionEnvironment env = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> uids = [CtInvocationImpl][CtFieldReadImpl]org.tensorflow.keras.backend.K.uidMap.get([CtVariableReadImpl]env);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]uids == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]uids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtFieldReadImpl]org.tensorflow.keras.backend.K.uidMap.put([CtVariableReadImpl]env, [CtVariableReadImpl]uids);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer id = [CtInvocationImpl][CtVariableReadImpl]uids.get([CtVariableReadImpl]prefix);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]id == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]id = [CtLiteralImpl]0;
        } else [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtVariableWriteImpl]id++;
        }
        [CtInvocationImpl][CtVariableReadImpl]uids.put([CtVariableReadImpl]prefix, [CtVariableReadImpl]id);
        [CtReturnImpl]return [CtVariableReadImpl]id;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * returns the larger DataType between the two.
     *
     * @param a
     * 		the first DataType to compare
     * @param b
     * 		the second DataType to compare
     * @return the wider DataType
     */
    public [CtTypeReferenceImpl]org.tensorflow.DataType wider([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType a, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType b) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.byteSize() < [CtInvocationImpl][CtVariableReadImpl]b.byteSize() ? [CtVariableReadImpl]b : [CtVariableReadImpl]a;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * returns the smaller DataType between the two.
     *
     * @param a
     * 		the first DataType to compare
     * @param b
     * 		the second DataType to compare
     * @return the smaller DataType
     */
    public [CtTypeReferenceImpl]org.tensorflow.DataType narrower([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType a, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.DataType b) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]a.byteSize() > [CtInvocationImpl][CtVariableReadImpl]b.byteSize() ? [CtVariableReadImpl]b : [CtVariableReadImpl]a;
    }

    [CtMethodImpl]public <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]org.tensorflow.types.family.TNumber> [CtTypeReferenceImpl]org.tensorflow.ndarray.NdArraySequence getTensorValue([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeParameterReferenceImpl]T> operand) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.DataType dtype = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]operand.asOutput().dataType();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tf.scope().env().isGraph()) [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Session session = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.tensorflow.Session([CtInvocationImpl](([CtTypeReferenceImpl]org.tensorflow.Graph) ([CtInvocationImpl][CtVariableReadImpl]tf.scope().env())))) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TInt32.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TInt32> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TInt32.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TInt64.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TInt64> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TInt64.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TUint8.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TUint8> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TUint8.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TBfloat16.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TBfloat16> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TBfloat16.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat16.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TFloat16> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TFloat16.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat32.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TFloat32> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TFloat32.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat64.DTYPE)) [CtBlockImpl]{
                    [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Tensor<[CtTypeReferenceImpl]org.tensorflow.types.TFloat64> result = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.runner().fetch([CtVariableReadImpl]operand).run().get([CtLiteralImpl]0).expect([CtTypeAccessImpl]TFloat64.DTYPE)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.data().scalars();
                    }
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
            }
        } else [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.EagerSession session = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.EagerSession.create()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TInt32.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TInt32>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TInt64.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TInt64>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TUint8.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TUint8>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TBfloat16.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TBfloat16>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat16.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TFloat16>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat32.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TFloat32>) (operand)).data().scalars();
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dtype.equals([CtTypeAccessImpl]TFloat64.DTYPE)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.tensorflow.Operand<[CtTypeReferenceImpl]org.tensorflow.types.TFloat64>) (operand)).data().scalars();
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]null;
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Squeeze or expand last dimension if needed. 1. Squeezes last dim of `y_pred` or `y_true` if
     * their rank differs by 1 (using `confusion_matrix.remove_squeezable_dimensions`). 2. Squeezes or
     * expands last dim of `sample_weight` if its rank differs by 1 from the new rank of `y_pred`. If
     * `sample_weight` is scalar, it is kept scalar.
     *
     * @param tf
     * 		the TensorVlow Ops
     * @param yPred
     * 		Predicted values, a `Tensor` of arbitrary dimensions.
     * @param yTrue
     * 		Optional label `Tensor` whose dimensions match `y_pred`.
     * @return Tuple of `y_pred`, `y_true` and `sample_weight`. Each of them possibly has the last
    dimension squeezed, `sample_weight` could be extended by one dimension. If `sample_weight`
    is null, (y_pred, y_true) is returned.
     */
    public static [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple squeezeOrExpandDimensions([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand yTrue, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand yPred) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.tensorflow.keras.backend.K.squeezeOrExpandDimensions([CtVariableReadImpl]tf, [CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Squeeze or expand last dimension if needed. 1. Squeezes last dim of `y_pred` or `y_true` if
     * their rank differs by 1 (using `confusion_matrix.remove_squeezable_dimensions`). 2. Squeezes or
     * expands last dim of `sample_weight` if its rank differs by 1 from the new rank of `y_pred`. If
     * `sample_weight` is scalar, it is kept scalar.
     *
     * @param tf
     * 		the TensorVlow Ops
     * @param yPred
     * 		Predicted values, a `Tensor` of arbitrary dimensions.
     * @param yTrue
     * 		Optional label `Tensor` whose dimensions match `y_pred`.
     * @param sampleWeight
     * 		Optional weight scalar or `Tensor` whose dimensions match `y_pred`.
     * @return Tuple of `y_pred`, `y_true` and `sample_weight`. Each of them possibly has the last
    dimension squeezed, `sample_weight` could be extended by one dimension. If `sample_weight`
    is null, (y_pred, y_true) is returned.
     */
    public static [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple squeezeOrExpandDimensions([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand yTrue, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand yPred, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand sampleWeight) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple tuple = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple([CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape ypredShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yPred.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long ypredRank = [CtInvocationImpl][CtVariableReadImpl]ypredShape.numDimensions();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]yTrue != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape ytrueShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]yTrue.asOutput().shape();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long ytrueRank = [CtInvocationImpl][CtVariableReadImpl]ytrueShape.numDimensions();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]ytrueRank != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) && [CtBinaryOperatorImpl]([CtVariableReadImpl]ypredRank != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Use static rank for `y_true` and `y_pred`.
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]ypredRank - [CtVariableReadImpl]ytrueRank) != [CtLiteralImpl]1) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ypredShape.size([CtUnaryOperatorImpl]-[CtLiteralImpl]1) == [CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// y_true, y_pred = confusion_matrix.remove_squeezable_dimensions(y_true, y_pred)
                    [CtVariableWriteImpl]tuple = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.keras.backend.tf.ConfusionMatrix.removeSqueezableDimensions([CtVariableReadImpl]tf, [CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred);
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// use dynamic rank
                [CtVariableWriteImpl]tuple = [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.keras.backend.tf.ConfusionMatrix.removeSqueezableDimensions([CtVariableReadImpl]tf, [CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sampleWeight == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]tuple;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.ndarray.Shape weightsShape = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sampleWeight.asOutput().shape();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long weightsRank = [CtInvocationImpl][CtVariableReadImpl]weightsShape.numDimensions();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]weightsRank == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// scalar
            return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple([CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred, [CtVariableReadImpl]sampleWeight);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]ypredRank != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE) && [CtBinaryOperatorImpl]([CtVariableReadImpl]weightsRank != [CtFieldReadImpl]org.tensorflow.ndarray.Shape.UNKNOWN_SIZE)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]weightsRank - [CtVariableReadImpl]ypredRank) == [CtLiteralImpl]1) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sampleWeight = [CtInvocationImpl][CtVariableReadImpl]tf.squeeze([CtVariableReadImpl]sampleWeight);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]ypredRank - [CtVariableReadImpl]weightsRank) == [CtLiteralImpl]1) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sampleWeight = [CtInvocationImpl][CtVariableReadImpl]tf.expandDims([CtVariableReadImpl]sampleWeight, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1L));
            }
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple([CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred, [CtVariableReadImpl]sampleWeight);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Use dynamic rank.
        [CtTypeReferenceImpl]org.tensorflow.Operand weightsRankTensor = [CtInvocationImpl][CtVariableReadImpl]tf.rank([CtVariableReadImpl]sampleWeight);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.tensorflow.Operand rankDiff = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.sub([CtVariableReadImpl]weightsRankTensor, [CtInvocationImpl][CtVariableReadImpl]tf.rank([CtVariableReadImpl]yPred));
        [CtAssignmentImpl][CtVariableWriteImpl]sampleWeight = [CtInvocationImpl][CtVariableReadImpl]tf.select([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.equal([CtVariableReadImpl]weightsRankTensor, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]0)), [CtVariableReadImpl]sampleWeight, [CtInvocationImpl]org.tensorflow.keras.backend.K.maybeAdjustWeights([CtVariableReadImpl]tf, [CtVariableReadImpl]sampleWeight, [CtVariableReadImpl]rankDiff));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.tensorflow.keras.backend.tf.Tuple([CtVariableReadImpl]yTrue, [CtVariableReadImpl]yPred, [CtVariableReadImpl]sampleWeight);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.tensorflow.Operand maybeAdjustWeights([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand sampleWeight, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand rankDiff) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.select([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.equal([CtVariableReadImpl]rankDiff, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtLiteralImpl]1)), [CtInvocationImpl][CtVariableReadImpl]tf.squeeze([CtVariableReadImpl]sampleWeight, [CtInvocationImpl][CtTypeAccessImpl]org.tensorflow.op.core.Squeeze.axis([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtUnaryOperatorImpl]-[CtLiteralImpl]1L))), [CtInvocationImpl]org.tensorflow.keras.backend.K.maybeExpandWeights([CtVariableReadImpl]tf, [CtVariableReadImpl]sampleWeight, [CtVariableReadImpl]rankDiff));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.tensorflow.Operand maybeExpandWeights([CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.op.Ops tf, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand sampleWeight, [CtParameterImpl][CtTypeReferenceImpl]org.tensorflow.Operand rankDiff) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tf.select([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]tf.math.equal([CtVariableReadImpl]rankDiff, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1)), [CtInvocationImpl][CtVariableReadImpl]tf.expandDims([CtVariableReadImpl]sampleWeight, [CtInvocationImpl][CtVariableReadImpl]tf.constant([CtUnaryOperatorImpl]-[CtLiteralImpl]1)), [CtVariableReadImpl]sampleWeight);
    }
}
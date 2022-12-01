[CompilationUnitImpl][CtCommentImpl]/* Copyright 2017-2020 Crown Copyright

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
[CtPackageDeclarationImpl]package uk.gov.gchq.gaffer.flink.operation.handler;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.store.Store;
[CtUnresolvedImport]import org.apache.flink.api.java.operators.FlatMapOperator;
[CtUnresolvedImport]import org.apache.flink.api.java.ExecutionEnvironment;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.flink.operation.handler.util.FlinkConstants;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.store.Context;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.operation.OperationException;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.store.operation.handler.OperationHandler;
[CtUnresolvedImport]import org.apache.flink.api.common.io.RichOutputFormat;
[CtUnresolvedImport]import uk.gov.gchq.gaffer.data.element.Element;
[CtClassImpl][CtJavaDocImpl]/**
 * <p>
 * A {@code AddElementsFromFileHandler} handles the {@link AddElementsFromFile}
 * operation.
 * </p>
 * <p>
 * This uses Flink to stream the {@link uk.gov.gchq.gaffer.data.element.Element}
 * objects from a file into Gaffer.
 * </p>
 * <p>
 * Rebalancing can be skipped by setting the operation option: gaffer.flink.operation.handler.skip-rebalancing to true
 * </p>
 */
public class AddElementsFromFileHandler implements [CtTypeReferenceImpl]uk.gov.gchq.gaffer.store.operation.handler.OperationHandler<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.flink.api.common.io.RichOutputFormat<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> CREATE_GAFFER_OUTPUT_ON_DO_OPERATION = [CtLiteralImpl]null;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.flink.api.common.io.RichOutputFormat<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> outputFormat;

    [CtConstructorImpl]public AddElementsFromFileHandler() [CtBlockImpl]{
        [CtInvocationImpl]this([CtFieldReadImpl]uk.gov.gchq.gaffer.flink.operation.handler.AddElementsFromFileHandler.CREATE_GAFFER_OUTPUT_ON_DO_OPERATION);
    }

    [CtConstructorImpl]public AddElementsFromFileHandler([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.flink.api.common.io.RichOutputFormat<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> outputFormat) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.outputFormat = [CtVariableReadImpl]outputFormat;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Object doOperation([CtParameterImpl]final [CtTypeReferenceImpl]uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile op, [CtParameterImpl]final [CtTypeReferenceImpl]uk.gov.gchq.gaffer.store.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]uk.gov.gchq.gaffer.store.Store store) throws [CtTypeReferenceImpl]uk.gov.gchq.gaffer.operation.OperationException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.flink.api.java.ExecutionEnvironment env = [CtInvocationImpl][CtTypeAccessImpl]org.apache.flink.api.java.ExecutionEnvironment.getExecutionEnvironment();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]op.getParallelism()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]env.setParallelism([CtInvocationImpl][CtVariableReadImpl]op.getParallelism());
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.flink.api.java.operators.FlatMapOperator<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> builder = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]env.readTextFile([CtInvocationImpl][CtVariableReadImpl]op.getFilename()).flatMap([CtConstructorCallImpl]new [CtTypeReferenceImpl]uk.gov.gchq.gaffer.flink.operation.handler.GafferMapFunction([CtFieldReadImpl]java.lang.String.class, [CtInvocationImpl][CtVariableReadImpl]op.getElementGenerator()));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.flink.api.common.io.RichOutputFormat<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> gafferOutput = [CtInvocationImpl]getOutputFormat([CtVariableReadImpl]op, [CtVariableReadImpl]store);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtVariableReadImpl]op.getOption([CtTypeAccessImpl]FlinkConstants.SKIP_REBALANCING))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]builder.output([CtVariableReadImpl]gafferOutput);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.rebalance().output([CtVariableReadImpl]gafferOutput);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]env.execute([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]op.getClass().getSimpleName() + [CtLiteralImpl]"-") + [CtInvocationImpl][CtVariableReadImpl]op.getFilename());
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]uk.gov.gchq.gaffer.operation.OperationException([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to add elements from file: " + [CtInvocationImpl][CtVariableReadImpl]op.getFilename(), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.flink.api.common.io.RichOutputFormat<[CtTypeReferenceImpl]uk.gov.gchq.gaffer.data.element.Element> getOutputFormat([CtParameterImpl]final [CtTypeReferenceImpl]uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile op, [CtParameterImpl]final [CtTypeReferenceImpl]uk.gov.gchq.gaffer.store.Store store) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]outputFormat == [CtFieldReadImpl]uk.gov.gchq.gaffer.flink.operation.handler.AddElementsFromFileHandler.CREATE_GAFFER_OUTPUT_ON_DO_OPERATION ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]uk.gov.gchq.gaffer.flink.operation.handler.GafferOutput([CtVariableReadImpl]op, [CtVariableReadImpl]store) : [CtFieldReadImpl]outputFormat;
    }
}
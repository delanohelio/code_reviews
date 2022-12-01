[CompilationUnitImpl][CtPackageDeclarationImpl]package io.warp10.json;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.io.Writer;
[CtClassImpl][CtJavaDocImpl]/**
 * A wrapper for Writers to limit the number of written chars.
 * When the number of chars that should be written exceeds the given limit, a WriterBoundReachedException is thrown.
 */
public class BoundedWriter extends [CtTypeReferenceImpl]java.io.Writer {
    [CtClassImpl]public static class WriterBoundReachedException extends [CtTypeReferenceImpl]java.io.IOException {
        [CtConstructorImpl]public WriterBoundReachedException([CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]message);
        }
    }

    [CtFieldImpl]protected final [CtTypeReferenceImpl]java.io.Writer writer;

    [CtFieldImpl]protected final [CtTypeReferenceImpl]long maxWrittenChars;

    [CtFieldImpl]protected [CtTypeReferenceImpl]long currentWrittenChars;

    [CtConstructorImpl]public BoundedWriter([CtParameterImpl][CtTypeReferenceImpl]java.io.Writer writer, [CtParameterImpl][CtTypeReferenceImpl]long maxAppendedChars) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.writer = [CtVariableReadImpl]writer;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxWrittenChars = [CtVariableReadImpl]maxAppendedChars;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentWrittenChars = [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void write([CtParameterImpl][CtArrayTypeReferenceImpl]char[] chars, [CtParameterImpl][CtTypeReferenceImpl]int start, [CtParameterImpl][CtTypeReferenceImpl]int end) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]end - [CtVariableReadImpl]start) + [CtFieldReadImpl]currentWrittenChars) - [CtFieldReadImpl]maxWrittenChars) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.warp10.json.BoundedWriter.WriterBoundReachedException([CtBinaryOperatorImpl][CtLiteralImpl]"Cannot write, maximum number of characters written :" + [CtFieldReadImpl][CtThisAccessImpl]this.maxWrittenChars);
        }
        [CtOperatorAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentWrittenChars += [CtBinaryOperatorImpl][CtVariableReadImpl]end - [CtVariableReadImpl]start;
        [CtInvocationImpl][CtFieldReadImpl]writer.write([CtVariableReadImpl]chars, [CtVariableReadImpl]start, [CtVariableReadImpl]end);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void flush() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]writer.flush();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]writer.close();
    }
}
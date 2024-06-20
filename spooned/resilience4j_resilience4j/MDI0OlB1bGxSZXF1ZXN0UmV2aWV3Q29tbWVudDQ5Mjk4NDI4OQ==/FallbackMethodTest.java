[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Kyuhyen Hwang , Mahmoud Romih

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
[CtPackageDeclarationImpl]package io.github.resilience4j.fallback;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThatThrownBy;
[CtImportImpl]import java.lang.reflect.Method;
[CtUnresolvedImport]import org.junit.Test;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
public class FallbackMethodTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fallbackRuntimeExceptionTest() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"fallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"err"))).isEqualTo([CtLiteralImpl]"recovered-RuntimeException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fallbackFuture() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testFutureMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"futureFallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CompletableFuture future = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.concurrent.CompletableFuture) ([CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"err"))));
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]future.get()).isEqualTo([CtLiteralImpl]"recovered-IllegalStateException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fallbackGlobalExceptionWithSameMethodReturnType() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"fallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"err"))).isEqualTo([CtLiteralImpl]"recovered-IllegalStateException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fallbackGlobalExceptionWithSameMethodReturnTypeAndMultipleParameters() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"multipleParameterTestMethod", [CtFieldReadImpl]java.lang.String.class, [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"fallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test", [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"err"))).isEqualTo([CtLiteralImpl]"recovered-IllegalStateException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fallbackClosestSuperclassExceptionTest() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"fallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.NumberFormatException([CtLiteralImpl]"err"))).isEqualTo([CtLiteralImpl]"recovered-IllegalArgumentException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldThrowUnrecoverableThrowable() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"fallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable unrecoverableThrown = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Throwable([CtLiteralImpl]"err");
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtVariableReadImpl]unrecoverableThrown)).isEqualTo([CtVariableReadImpl]unrecoverableThrown);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldCallPrivateFallbackMethod() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"privateFallback", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"err"))).isEqualTo([CtLiteralImpl]"recovered-privateMethod");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void mismatchReturnType_shouldThrowNoSuchMethodException() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"duplicateException", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target)).isInstanceOf([CtFieldReadImpl]java.lang.IllegalStateException.class).hasMessage([CtLiteralImpl]"You have more that one fallback method that cover the same exception type java.lang.IllegalArgumentException");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldFailIf2FallBackMethodsHandleSameException() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"returnMismatchFallback", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target)).isInstanceOf([CtFieldReadImpl]java.lang.NoSuchMethodException.class).hasMessage([CtLiteralImpl]"class java.lang.String class io.github.resilience4j.fallback.FallbackMethodTest.returnMismatchFallback(class java.lang.String,class java.lang.Throwable)");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void notFoundFallbackMethod_shouldThrowsNoSuchMethodException() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"noMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target)).isInstanceOf([CtFieldReadImpl]java.lang.NoSuchMethodException.class).hasMessage([CtLiteralImpl]"class java.lang.String class io.github.resilience4j.fallback.FallbackMethodTest.noMethod(class java.lang.String,class java.lang.Throwable)");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void rethrownFallbackMethodRuntimeExceptionShouldNotBeWrapped() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"rethrowingFallbackMethod", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowException exception = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowException();
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtVariableReadImpl]exception)).isSameAs([CtVariableReadImpl]exception);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void rethrownFallbackMethodCheckedExceptionShouldNotBeWrapped() throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethodTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Method testMethod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]target.getClass().getMethod([CtLiteralImpl]"testMethod", [CtFieldReadImpl]java.lang.String.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.FallbackMethod fallbackMethod = [CtInvocationImpl][CtTypeAccessImpl]io.github.resilience4j.fallback.FallbackMethod.create([CtLiteralImpl]"rethrowingFallbackMethodChecked", [CtVariableReadImpl]testMethod, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtLiteralImpl]"test" }, [CtVariableReadImpl]target);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowCheckedException exception = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowCheckedException();
        [CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtLambdaImpl]() -> [CtInvocationImpl][CtVariableReadImpl]fallbackMethod.fallback([CtVariableReadImpl]exception)).isSameAs([CtVariableReadImpl]exception);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String testMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"test";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String multipleParameterTestMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String param1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String param2) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"multiple parameter test";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> testFutureMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.completedFuture([CtLiteralImpl]"test");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String fallbackMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.RuntimeException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-RuntimeException";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String fallbackMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.IllegalStateException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-IllegalStateException";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.concurrent.CompletableFuture<[CtTypeReferenceImpl]java.lang.String> futureFallbackMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.IllegalStateException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.completedFuture([CtLiteralImpl]"recovered-IllegalStateException");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String fallbackMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-IllegalArgumentException";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Object returnMismatchFallback([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.RuntimeException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered";
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String privateFallback([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.RuntimeException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-privateMethod";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String duplicateException([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-IllegalArgumentException";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String duplicateException([CtParameterImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"recovered-IllegalArgumentException";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String rethrowingFallbackMethod([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception exception) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// To illustrate the typical use case:
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]exception instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowException) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl](([CtTypeReferenceImpl]io.github.resilience4j.fallback.RethrowException) (exception));
        }
        [CtReturnImpl]return [CtLiteralImpl]"normal recovery result";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String rethrowingFallbackMethodChecked([CtParameterImpl][CtTypeReferenceImpl]java.lang.String parameter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception exception) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtThrowImpl]throw [CtVariableReadImpl]exception;
    }
}
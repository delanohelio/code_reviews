[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.firebase.encoders.reflective;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtImportImpl]import java.lang.reflect.Constructor;
[CtUnresolvedImport]import com.google.firebase.decoders.TypeToken;
[CtImportImpl]import java.lang.reflect.InvocationTargetException;
[CtClassImpl]final class ReflectiveInitializer {
    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T newInstance([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.firebase.decoders.TypeToken.ClassToken<[CtTypeParameterReferenceImpl]T> classToken) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.reflect.Constructor<[CtTypeReferenceImpl]com.google.firebase.encoders.reflective.T> constructor = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]classToken.getRawType().getDeclaredConstructor();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]constructor.newInstance();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NoSuchMethodException e) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// TODO: try JVM sun.misc.Unsafe to allocate an instance
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalAccessException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InstantiationException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.reflect.InvocationTargetException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
    }

    [CtConstructorImpl]private ReflectiveInitializer() [CtBlockImpl]{
    }
}
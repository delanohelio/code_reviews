[CompilationUnitImpl][CtCommentImpl]/* The MIT License (MIT)

Copyright (c) 2017-2020 Yegor Bugayenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
[CtPackageDeclarationImpl]package org.cactoos.scalar;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.llorllale.cactoos.matchers.Assertion;
[CtUnresolvedImport]import org.cactoos.iterable.IterableOf;
[CtUnresolvedImport]import org.llorllale.cactoos.matchers.ScalarHasValue;
[CtUnresolvedImport]import org.cactoos.Scalar;
[CtClassImpl][CtJavaDocImpl]/**
 * Test case for {@link Xor}.
 *
 * @since 0.48
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"PMD.AvoidDuplicateLiterals", [CtLiteralImpl]"PMD.TooManyMethods" })
final class XorTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void trueTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Either one, but not both nor none", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]false)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void falseTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Either one, but not both nor none", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void treuFalse() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Either one, but not both nor none", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void falseFalse() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Either one, but not both nor none", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]false)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void singleTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Single True must be True", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void singleFalse() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Single False must be False", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]false)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void emptyIterator() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Empty iterator must be true", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.iterable.IterableOf<[CtTypeReferenceImpl]org.cactoos.Scalar<[CtTypeReferenceImpl]java.lang.Boolean>>()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void oddNumberOfTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Odd number of True must be True", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void evenNumberOfTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Even number of True must be False", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]false)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void allFalse() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Even number of True must be False", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.False()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]false)).affirm();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void allTrue() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.Assertion<>([CtLiteralImpl]"Odd number of True must be True", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.Xor([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.cactoos.scalar.True()), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.llorllale.cactoos.matchers.ScalarHasValue<>([CtLiteralImpl]true)).affirm();
    }
}
[CompilationUnitImpl][CtCommentImpl]/* Copyright 2013-2020 The OpenZipkin Authors

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
 */
[CtPackageDeclarationImpl]package brave.internal.baggage;
[CtUnresolvedImport]import brave.internal.Platform;
[CtClassImpl][CtJavaDocImpl]/**
 * Splits a character sequence that's in a delimited string trimming optional whitespace (OWS)
 * before or after delimiters.
 */
public final class EntrySplitter {
    [CtFieldImpl]static final [CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter INSTANCE = [CtConstructorCallImpl]new [CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter([CtLiteralImpl]'=', [CtLiteralImpl]',');

    [CtMethodImpl]public static [CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter get() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]brave.internal.baggage.EntrySplitter.INSTANCE;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter create([CtParameterImpl][CtTypeReferenceImpl]char keyValueSeparator, [CtParameterImpl][CtTypeReferenceImpl]char entrySeparator) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter([CtVariableReadImpl]keyValueSeparator, [CtVariableReadImpl]entrySeparator);
    }

    [CtInterfaceImpl]interface Handler {
        [CtMethodImpl][CtTypeReferenceImpl]boolean onEntry([CtParameterImpl][CtTypeReferenceImpl]java.lang.CharSequence buffer, [CtParameterImpl][CtTypeReferenceImpl]int beginName, [CtParameterImpl][CtTypeReferenceImpl]int endName, [CtParameterImpl][CtTypeReferenceImpl]int beginValue, [CtParameterImpl][CtTypeReferenceImpl]int endValue);
    }

    [CtFieldImpl]final [CtTypeReferenceImpl]int keyValueSeparator;

    [CtFieldImpl]final [CtTypeReferenceImpl]int entrySeparator;

    [CtFieldImpl]final [CtTypeReferenceImpl]java.lang.String missingKeyValueSeparator;

    [CtConstructorImpl]EntrySplitter([CtParameterImpl][CtTypeReferenceImpl]int keyValueSeparator, [CtParameterImpl][CtTypeReferenceImpl]int entrySeparator) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.keyValueSeparator = [CtVariableReadImpl]keyValueSeparator;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.entrySeparator = [CtVariableReadImpl]entrySeparator;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.missingKeyValueSeparator = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid input: missing '" + [CtVariableReadImpl]keyValueSeparator) + [CtLiteralImpl]"' between key and value";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean parse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String buffer, [CtParameterImpl][CtTypeReferenceImpl]brave.internal.baggage.EntrySplitter.Handler handler, [CtParameterImpl][CtTypeReferenceImpl]boolean shouldThrow) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int length = [CtInvocationImpl][CtVariableReadImpl]buffer.length();
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]length) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]brave.internal.baggage.EntrySplitter.isOWS([CtInvocationImpl][CtVariableReadImpl]buffer.charAt([CtUnaryOperatorImpl][CtVariableWriteImpl]i++)))[CtBlockImpl]
                [CtBreakImpl]break;
            [CtCommentImpl]// skip whitespace

        } 
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtVariableReadImpl]length)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl]brave.internal.baggage.EntrySplitter.logOrThrow([CtLiteralImpl]"Invalid input: only whitespace", [CtVariableReadImpl]shouldThrow);

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]buffer.charAt([CtVariableReadImpl]i) == [CtFieldReadImpl]keyValueSeparator) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]brave.internal.baggage.EntrySplitter.logOrThrow([CtLiteralImpl]"Invalid input: missing key", [CtVariableReadImpl]shouldThrow);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int beginName = [CtVariableReadImpl]i;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int endName = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int beginValue = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]length) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]buffer.charAt([CtUnaryOperatorImpl][CtVariableWriteImpl]i++);
            [CtIfImpl][CtCommentImpl]// OWS is zero or more spaces or tabs https://httpwg.org/specs/rfc7230.html#rfc.section.3.2
            if ([CtInvocationImpl]brave.internal.baggage.EntrySplitter.isOWS([CtVariableReadImpl]c))[CtBlockImpl]
                [CtContinueImpl]continue;
            [CtCommentImpl]// trim whitespace

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtFieldReadImpl]keyValueSeparator) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// we reached a field name
                [CtVariableWriteImpl]endName = [CtVariableReadImpl]i;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](++[CtVariableWriteImpl]i) == [CtVariableReadImpl]length)[CtBlockImpl]
                    [CtBreakImpl]break;
                [CtCommentImpl]// skip '=' character

                [CtAssignmentImpl][CtVariableWriteImpl]beginValue = [CtVariableReadImpl]i;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i == [CtVariableReadImpl]length) || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtFieldReadImpl]entrySeparator)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// we finished an entry
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]beginValue == [CtUnaryOperatorImpl](-[CtLiteralImpl]1))[CtBlockImpl]
                    [CtReturnImpl]return [CtInvocationImpl]brave.internal.baggage.EntrySplitter.logOrThrow([CtFieldReadImpl]missingKeyValueSeparator, [CtVariableReadImpl]shouldThrow);

                [CtLocalVariableImpl][CtTypeReferenceImpl]int endValue = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtVariableReadImpl]length) ? [CtVariableReadImpl]i : [CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1;
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]handler.onEntry([CtVariableReadImpl]buffer, [CtVariableReadImpl]beginName, [CtVariableReadImpl]endName, [CtVariableReadImpl]beginValue, [CtVariableReadImpl]endValue)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// assume handler logs

                }
                [CtAssignmentImpl][CtVariableWriteImpl]beginName = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]endName = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]beginValue = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
            }
        } 
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isOWS([CtParameterImpl][CtTypeReferenceImpl]char c) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]' ') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'\t');
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean logOrThrow([CtParameterImpl][CtTypeReferenceImpl]java.lang.String msg, [CtParameterImpl][CtTypeReferenceImpl]boolean shouldThrow) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]shouldThrow)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtVariableReadImpl]msg);

        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]brave.internal.Platform.get().log([CtVariableReadImpl]msg, [CtLiteralImpl]null);
        [CtReturnImpl]return [CtLiteralImpl]false;
    }
}
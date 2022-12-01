[CompilationUnitImpl][CtJavaDocImpl]/**
 * detectable
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
[CtPackageDeclarationImpl]package com.synopsys.integration.detectable.detectables.clang.compilecommand;
[CtUnresolvedImport]import org.apache.commons.text.StringTokenizer;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.apache.commons.text.matcher.StringMatcherFactory;
[CtClassImpl]public class CompileCommandParser {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ESCAPED_DOUBLE_QUOTE = [CtLiteralImpl]"\\\\\"";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DOUBLE_QUOTE = [CtLiteralImpl]"\"";

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtThisAccessImpl]this.getClass());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]char SINGLE_QUOTE_CHAR = [CtLiteralImpl]'\'';

    [CtFieldImpl]private static final [CtTypeReferenceImpl]char DOUBLE_QUOTE_CHAR = [CtLiteralImpl]'"';

    [CtFieldImpl]private static final [CtTypeReferenceImpl]char ESCAPE_CHAR = [CtLiteralImpl]'\\';

    [CtFieldImpl]private static final [CtTypeReferenceImpl]char TAB_CHAR = [CtLiteralImpl]'\t';

    [CtFieldImpl]private static final [CtTypeReferenceImpl]char SPACE_CHAR = [CtLiteralImpl]' ';

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SPACE_CHAR_AS_STRING = [CtLiteralImpl]" ";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TAB_CHAR_AS_STRING = [CtLiteralImpl]"\t";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ESCAPE_SEQUENCE_FOR_SPACE_CHAR = [CtLiteralImpl]"%20";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ESCAPE_SEQUENCE_FOR_TAB_CHAR = [CtLiteralImpl]"%09";

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> parseCommand([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommand compileCommand, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> optionOverrides) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commandString = [CtFieldReadImpl][CtVariableReadImpl]compileCommand.command;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]commandString)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]commandString = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.join([CtLiteralImpl]" ", [CtFieldReadImpl][CtVariableReadImpl]compileCommand.arguments);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> commandList = [CtInvocationImpl]parseCommandString([CtVariableReadImpl]commandString, [CtVariableReadImpl]optionOverrides);
        [CtReturnImpl]return [CtVariableReadImpl]commandList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> parseCommandString([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String commandString, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> optionOverrides) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]logger.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"origCompileCommand         : %s", [CtVariableReadImpl]commandString));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String quotesRemovedCompileCommand = [CtInvocationImpl]escapeQuotedWhitespace([CtVariableReadImpl]commandString);
        [CtInvocationImpl][CtFieldReadImpl]logger.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"quotesRemovedCompileCommand: %s", [CtVariableReadImpl]quotesRemovedCompileCommand));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.commons.text.StringTokenizer tokenizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.text.StringTokenizer([CtVariableReadImpl]quotesRemovedCompileCommand);
        [CtInvocationImpl][CtVariableReadImpl]tokenizer.setQuoteMatcher([CtInvocationImpl][CtTypeAccessImpl]StringMatcherFactory.INSTANCE.quoteMatcher());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> commandList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lastPart = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int partIndex = [CtLiteralImpl]0;
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]tokenizer.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String part = [CtInvocationImpl]unEscapeDoubleQuotes([CtInvocationImpl]restoreWhitespace([CtInvocationImpl][CtVariableReadImpl]tokenizer.nextToken()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partIndex > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String optionValueOverride = [CtLiteralImpl]null;
                [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String optionToOverride : [CtInvocationImpl][CtVariableReadImpl]optionOverrides.keySet()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]optionToOverride.equals([CtVariableReadImpl]lastPart)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]optionValueOverride = [CtInvocationImpl][CtVariableReadImpl]optionOverrides.get([CtVariableReadImpl]optionToOverride);
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]optionValueOverride != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]commandList.add([CtVariableReadImpl]optionValueOverride);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]commandList.add([CtVariableReadImpl]part);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]commandList.add([CtVariableReadImpl]part);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]lastPart = [CtVariableReadImpl]part;
            [CtUnaryOperatorImpl][CtVariableWriteImpl]partIndex++;
        } 
        [CtReturnImpl]return [CtVariableReadImpl]commandList;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String restoreWhitespace([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String givenString) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String newString = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]givenString.replace([CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPE_SEQUENCE_FOR_SPACE_CHAR, [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.SPACE_CHAR_AS_STRING).replace([CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPE_SEQUENCE_FOR_TAB_CHAR, [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.TAB_CHAR_AS_STRING);
        [CtInvocationImpl][CtFieldReadImpl]logger.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"restoreWhitespace() changed %s to %s", [CtVariableReadImpl]givenString, [CtVariableReadImpl]newString));
        [CtReturnImpl]return [CtVariableReadImpl]newString;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String unEscapeDoubleQuotes([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String givenString) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String newString = [CtInvocationImpl][CtVariableReadImpl]givenString.replaceAll([CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPED_DOUBLE_QUOTE, [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.DOUBLE_QUOTE);
        [CtInvocationImpl][CtFieldReadImpl]logger.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"unEscapeDoubleQuotes() changed %s to %s", [CtVariableReadImpl]givenString, [CtVariableReadImpl]newString));
        [CtReturnImpl]return [CtVariableReadImpl]newString;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String escapeQuotedWhitespace([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String givenString) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.StringBuilder newString = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ParserState parserState = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ParserState();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]givenString.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]givenString.charAt([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]parserState.isInQuotes()) [CtBlockImpl]{
                [CtInvocationImpl]processNonQuotedChar([CtVariableReadImpl]parserState, [CtVariableReadImpl]c, [CtVariableReadImpl]newString);
            } else [CtBlockImpl]{
                [CtInvocationImpl]processQuotedChar([CtVariableReadImpl]parserState, [CtVariableReadImpl]c, [CtVariableReadImpl]newString);
            }
            [CtInvocationImpl][CtVariableReadImpl]parserState.setLastCharWasEscapeChar([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPE_CHAR);
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.trace([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"escapeQuotedWhitespace() changed %s to %s", [CtVariableReadImpl]givenString, [CtInvocationImpl][CtVariableReadImpl]newString.toString()));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]newString.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processQuotedChar([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ParserState parserState, [CtParameterImpl]final [CtTypeReferenceImpl]char c, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.StringBuilder newString) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Currently inside a quoted substring
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]parserState.isLastCharWasEscapeChar()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.SINGLE_QUOTE_CHAR)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]parserState.isQuoteTypeIsDouble())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]parserState.setInQuotes([CtLiteralImpl]false);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]parserState.isLastCharWasEscapeChar()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.DOUBLE_QUOTE_CHAR)) && [CtInvocationImpl][CtVariableReadImpl]parserState.isQuoteTypeIsDouble()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]parserState.setInQuotes([CtLiteralImpl]false);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.SPACE_CHAR) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newString.append([CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPE_SEQUENCE_FOR_SPACE_CHAR);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.TAB_CHAR) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newString.append([CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ESCAPE_SEQUENCE_FOR_TAB_CHAR);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newString.append([CtVariableReadImpl]c);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processNonQuotedChar([CtParameterImpl]final [CtTypeReferenceImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.ParserState parserState, [CtParameterImpl]final [CtTypeReferenceImpl]char c, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.StringBuilder newString) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]parserState.isLastCharWasEscapeChar()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.SINGLE_QUOTE_CHAR)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]parserState.setInQuotes([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]parserState.setQuoteTypeIsDouble([CtLiteralImpl]false);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]parserState.isLastCharWasEscapeChar()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtFieldReadImpl]com.synopsys.integration.detectable.detectables.clang.compilecommand.CompileCommandParser.DOUBLE_QUOTE_CHAR)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]parserState.setInQuotes([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]parserState.setQuoteTypeIsDouble([CtLiteralImpl]true);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newString.append([CtVariableReadImpl]c);
        }
    }

    [CtClassImpl]private class ParserState {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean lastCharWasEscapeChar = [CtLiteralImpl]false;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean inQuotes = [CtLiteralImpl]false;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean quoteTypeIsDouble = [CtLiteralImpl]false;

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isLastCharWasEscapeChar() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]lastCharWasEscapeChar;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isInQuotes() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]inQuotes;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean isQuoteTypeIsDouble() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]quoteTypeIsDouble;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setLastCharWasEscapeChar([CtParameterImpl]final [CtTypeReferenceImpl]boolean lastCharWasEscapeChar) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lastCharWasEscapeChar = [CtVariableReadImpl]lastCharWasEscapeChar;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setInQuotes([CtParameterImpl]final [CtTypeReferenceImpl]boolean inQuotes) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inQuotes = [CtVariableReadImpl]inQuotes;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void setQuoteTypeIsDouble([CtParameterImpl]final [CtTypeReferenceImpl]boolean quoteTypeIsDouble) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.quoteTypeIsDouble = [CtVariableReadImpl]quoteTypeIsDouble;
        }
    }
}
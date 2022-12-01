[CompilationUnitImpl][CtCommentImpl]/* Copyright 2004-2020 H2 Group. Multiple-Licensed under the MPL 2.0,
and the EPL 1.0 (https://h2database.com/html/license.html).
Initial Developer: Daniel Gredler
 */
[CtPackageDeclarationImpl]package org.h2.expression.function;
[CtImportImpl]import java.util.Locale;
[CtUnresolvedImport]import org.h2.api.ErrorCode;
[CtImportImpl]import java.text.DecimalFormatSymbols;
[CtImportImpl]import java.text.DecimalFormat;
[CtUnresolvedImport]import org.h2.value.TypeInfo;
[CtImportImpl]import java.math.RoundingMode;
[CtUnresolvedImport]import org.h2.util.DateTimeUtils;
[CtUnresolvedImport]import org.h2.message.DbException;
[CtImportImpl]import java.math.BigDecimal;
[CtUnresolvedImport]import org.h2.util.StringUtils;
[CtUnresolvedImport]import org.h2.value.ValueTimeTimeZone;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import org.h2.engine.Session;
[CtImportImpl]import java.util.Currency;
[CtUnresolvedImport]import org.h2.util.TimeZoneProvider;
[CtImportImpl]import java.text.DateFormatSymbols;
[CtUnresolvedImport]import org.h2.value.ValueTimestamp;
[CtUnresolvedImport]import org.h2.value.ValueTimestampTimeZone;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.h2.value.Value;
[CtClassImpl][CtJavaDocImpl]/**
 * Emulates Oracle's TO_CHAR function.
 */
public class ToChar {
    [CtFieldImpl][CtJavaDocImpl]/**
     * The beginning of the Julian calendar.
     */
    public static final [CtTypeReferenceImpl]int JULIAN_EPOCH = [CtUnaryOperatorImpl]-[CtLiteralImpl]2440588;

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]int[] ROMAN_VALUES = [CtNewArrayImpl]new int[]{ [CtLiteralImpl]1000, [CtLiteralImpl]900, [CtLiteralImpl]500, [CtLiteralImpl]400, [CtLiteralImpl]100, [CtLiteralImpl]90, [CtLiteralImpl]50, [CtLiteralImpl]40, [CtLiteralImpl]10, [CtLiteralImpl]9, [CtLiteralImpl]5, [CtLiteralImpl]4, [CtLiteralImpl]1 };

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]java.lang.String[] ROMAN_NUMERALS = [CtNewArrayImpl]new java.lang.String[]{ [CtLiteralImpl]"M", [CtLiteralImpl]"CM", [CtLiteralImpl]"D", [CtLiteralImpl]"CD", [CtLiteralImpl]"C", [CtLiteralImpl]"XC", [CtLiteralImpl]"L", [CtLiteralImpl]"XL", [CtLiteralImpl]"X", [CtLiteralImpl]"IX", [CtLiteralImpl]"V", [CtLiteralImpl]"IV", [CtLiteralImpl]"I" };

    [CtFieldImpl][CtJavaDocImpl]/**
     * The month field.
     */
    public static final [CtTypeReferenceImpl]int MONTHS = [CtLiteralImpl]0;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The month field (short form).
     */
    public static final [CtTypeReferenceImpl]int SHORT_MONTHS = [CtLiteralImpl]1;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The weekday field.
     */
    public static final [CtTypeReferenceImpl]int WEEKDAYS = [CtLiteralImpl]2;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The weekday field (short form).
     */
    public static final [CtTypeReferenceImpl]int SHORT_WEEKDAYS = [CtLiteralImpl]3;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The AM / PM field.
     */
    static final [CtTypeReferenceImpl]int AM_PM = [CtLiteralImpl]4;

    [CtFieldImpl]private static volatile [CtArrayTypeReferenceImpl]java.lang.String[][] NAMES;

    [CtConstructorImpl]private ToChar() [CtBlockImpl]{
        [CtCommentImpl]// utility class
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Emulates Oracle's TO_CHAR(number) function.
     *
     * <p><table border="1">
     * <th><td>Input</td>
     * <td>Output</td>
     * <td>Closest {@link DecimalFormat} Equivalent</td></th>
     * <tr><td>,</td>
     * <td>Grouping separator.</td>
     * <td>,</td></tr>
     * <tr><td>.</td>
     * <td>Decimal separator.</td>
     * <td>.</td></tr>
     * <tr><td>$</td>
     * <td>Leading dollar sign.</td>
     * <td>$</td></tr>
     * <tr><td>0</td>
     * <td>Leading or trailing zeroes.</td>
     * <td>0</td></tr>
     * <tr><td>9</td>
     * <td>Digit.</td>
     * <td>#</td></tr>
     * <tr><td>B</td>
     * <td>Blanks integer part of a fixed point number less than 1.</td>
     * <td>#</td></tr>
     * <tr><td>C</td>
     * <td>ISO currency symbol.</td>
     * <td>\u00A4</td></tr>
     * <tr><td>D</td>
     * <td>Local decimal separator.</td>
     * <td>.</td></tr>
     * <tr><td>EEEE</td>
     * <td>Returns a value in scientific notation.</td>
     * <td>E</td></tr>
     * <tr><td>FM</td>
     * <td>Returns values with no leading or trailing spaces.</td>
     * <td>None.</td></tr>
     * <tr><td>G</td>
     * <td>Local grouping separator.</td>
     * <td>,</td></tr>
     * <tr><td>L</td>
     * <td>Local currency symbol.</td>
     * <td>\u00A4</td></tr>
     * <tr><td>MI</td>
     * <td>Negative values get trailing minus sign,
     * positive get trailing space.</td>
     * <td>-</td></tr>
     * <tr><td>PR</td>
     * <td>Negative values get enclosing angle brackets,
     * positive get spaces.</td>
     * <td>None.</td></tr>
     * <tr><td>RN</td>
     * <td>Returns values in Roman numerals.</td>
     * <td>None.</td></tr>
     * <tr><td>S</td>
     * <td>Returns values with leading/trailing +/- signs.</td>
     * <td>None.</td></tr>
     * <tr><td>TM</td>
     * <td>Returns smallest number of characters possible.</td>
     * <td>None.</td></tr>
     * <tr><td>U</td>
     * <td>Returns the dual currency symbol.</td>
     * <td>None.</td></tr>
     * <tr><td>V</td>
     * <td>Returns a value multiplied by 10^n.</td>
     * <td>None.</td></tr>
     * <tr><td>X</td>
     * <td>Hex value.</td>
     * <td>None.</td></tr>
     * </table>
     * See also TO_CHAR(number) and number format models
     * in the Oracle documentation.
     *
     * @param number
     * 		the number to format
     * @param format
     * 		the format pattern to use (if any)
     * @param nlsParam
     * 		the NLS parameter (if any)
     * @return the formatted number
     */
    public static [CtTypeReferenceImpl]java.lang.String toChar([CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal number, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String format, [CtParameterImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    [CtTypeReferenceImpl]java.lang.String nlsParam) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// short-circuit logic for formats that don't follow common logic below
        [CtTypeReferenceImpl]java.lang.String formatUp = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]format != [CtLiteralImpl]null) ? [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toUpperEnglish([CtVariableReadImpl]format) : [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]formatUp == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]formatUp.equals([CtLiteralImpl]"TM")) || [CtInvocationImpl][CtVariableReadImpl]formatUp.equals([CtLiteralImpl]"TM9")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s = [CtInvocationImpl][CtVariableReadImpl]number.toPlainString();
            [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]s.startsWith([CtLiteralImpl]"0.") ? [CtInvocationImpl][CtVariableReadImpl]s.substring([CtLiteralImpl]1) : [CtVariableReadImpl]s;
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]formatUp.equals([CtLiteralImpl]"TME")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int pow = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]number.precision() - [CtInvocationImpl][CtVariableReadImpl]number.scale()) - [CtLiteralImpl]1;
            [CtAssignmentImpl][CtVariableWriteImpl]number = [CtInvocationImpl][CtVariableReadImpl]number.movePointLeft([CtVariableReadImpl]pow);
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]number.toPlainString() + [CtLiteralImpl]"E") + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]pow < [CtLiteralImpl]0 ? [CtLiteralImpl]'-' : [CtLiteralImpl]'+')) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]pow) < [CtLiteralImpl]10 ? [CtLiteralImpl]"0" : [CtLiteralImpl]"")) + [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]pow);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]formatUp.equals([CtLiteralImpl]"RN")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean lowercase = [CtInvocationImpl][CtVariableReadImpl]format.startsWith([CtLiteralImpl]"r");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rn = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtInvocationImpl]org.h2.expression.function.ToChar.toRomanNumeral([CtInvocationImpl][CtVariableReadImpl]number.intValue()), [CtLiteralImpl]15, [CtLiteralImpl]" ", [CtLiteralImpl]false);
            [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]lowercase ? [CtInvocationImpl][CtVariableReadImpl]rn.toLowerCase() : [CtVariableReadImpl]rn;
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]formatUp.equals([CtLiteralImpl]"FMRN")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean lowercase = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.charAt([CtLiteralImpl]2) == [CtLiteralImpl]'r';
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rn = [CtInvocationImpl]org.h2.expression.function.ToChar.toRomanNumeral([CtInvocationImpl][CtVariableReadImpl]number.intValue());
            [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]lowercase ? [CtInvocationImpl][CtVariableReadImpl]rn.toLowerCase() : [CtVariableReadImpl]rn;
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]formatUp.endsWith([CtLiteralImpl]"X")) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]org.h2.expression.function.ToChar.toHex([CtVariableReadImpl]number, [CtVariableReadImpl]format);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String originalFormat = [CtVariableReadImpl]format;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.DecimalFormatSymbols symbols = [CtInvocationImpl][CtTypeAccessImpl]java.text.DecimalFormatSymbols.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]char localGrouping = [CtInvocationImpl][CtVariableReadImpl]symbols.getGroupingSeparator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]char localDecimal = [CtInvocationImpl][CtVariableReadImpl]symbols.getDecimalSeparator();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean leadingSign = [CtInvocationImpl][CtVariableReadImpl]formatUp.startsWith([CtLiteralImpl]"S");
        [CtIfImpl]if ([CtVariableReadImpl]leadingSign) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]1);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean trailingSign = [CtInvocationImpl][CtVariableReadImpl]formatUp.endsWith([CtLiteralImpl]"S");
        [CtIfImpl]if ([CtVariableReadImpl]trailingSign) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() - [CtLiteralImpl]1);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean trailingMinus = [CtInvocationImpl][CtVariableReadImpl]formatUp.endsWith([CtLiteralImpl]"MI");
        [CtIfImpl]if ([CtVariableReadImpl]trailingMinus) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() - [CtLiteralImpl]2);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean angleBrackets = [CtInvocationImpl][CtVariableReadImpl]formatUp.endsWith([CtLiteralImpl]"PR");
        [CtIfImpl]if ([CtVariableReadImpl]angleBrackets) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() - [CtLiteralImpl]2);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int v = [CtInvocationImpl][CtVariableReadImpl]formatUp.indexOf([CtLiteralImpl]'V');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]v >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int digits = [CtLiteralImpl]0;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]v + [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'9')) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]digits++;
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]number = [CtInvocationImpl][CtVariableReadImpl]number.movePointRight([CtVariableReadImpl]digits);
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtVariableReadImpl]v) + [CtInvocationImpl][CtVariableReadImpl]format.substring([CtBinaryOperatorImpl][CtVariableReadImpl]v + [CtLiteralImpl]1);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer power;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]format.endsWith([CtLiteralImpl]"EEEE")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]power = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]number.precision() - [CtInvocationImpl][CtVariableReadImpl]number.scale()) - [CtLiteralImpl]1;
            [CtAssignmentImpl][CtVariableWriteImpl]number = [CtInvocationImpl][CtVariableReadImpl]number.movePointLeft([CtVariableReadImpl]power);
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() - [CtLiteralImpl]4);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]power = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxLength = [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean fillMode = [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]formatUp.startsWith([CtLiteralImpl]"FM");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]fillMode) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]2);
        }
        [CtAssignmentImpl][CtCommentImpl]// blanks flag doesn't seem to actually do anything
        [CtVariableWriteImpl]format = [CtInvocationImpl][CtVariableReadImpl]format.replaceAll([CtLiteralImpl]"[Bb]", [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtCommentImpl]// if we need to round the number to fit into the format specified,
        [CtCommentImpl]// go ahead and do that first
        [CtTypeReferenceImpl]int separator = [CtInvocationImpl]org.h2.expression.function.ToChar.findDecimalSeparator([CtVariableReadImpl]format);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int formatScale = [CtInvocationImpl]org.h2.expression.function.ToChar.calculateScale([CtVariableReadImpl]format, [CtVariableReadImpl]separator);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int numberScale = [CtInvocationImpl][CtVariableReadImpl]number.scale();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]formatScale < [CtVariableReadImpl]numberScale) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]number = [CtInvocationImpl][CtVariableReadImpl]number.setScale([CtVariableReadImpl]formatScale, [CtFieldReadImpl][CtTypeAccessImpl]java.math.RoundingMode.[CtFieldReferenceImpl]HALF_UP);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]numberScale < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]number = [CtInvocationImpl][CtVariableReadImpl]number.setScale([CtLiteralImpl]0);
        }
        [CtForImpl][CtCommentImpl]// any 9s to the left of the decimal separator but to the right of a
        [CtCommentImpl]// 0 behave the same as a 0, e.g. "09999.99" -> "00000.99"
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtVariableReadImpl]format.indexOf([CtLiteralImpl]'0'); [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]i < [CtVariableReadImpl]separator); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]'9') [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]format = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.substring([CtLiteralImpl]0, [CtVariableReadImpl]i) + [CtLiteralImpl]"0") + [CtInvocationImpl][CtVariableReadImpl]format.substring([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder output = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String unscaled = [CtBinaryOperatorImpl][CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]number.abs().compareTo([CtFieldReadImpl][CtTypeAccessImpl]java.math.BigDecimal.[CtFieldReferenceImpl]ONE) < [CtLiteralImpl]0 ? [CtInvocationImpl]org.h2.expression.function.ToChar.zeroesAfterDecimalSeparator([CtVariableReadImpl]number) : [CtLiteralImpl]"") + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]number.unscaledValue().abs().toString();
        [CtLocalVariableImpl][CtCommentImpl]// start at the decimal point and fill in the numbers to the left,
        [CtCommentImpl]// working our way from right to left
        [CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]separator - [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int j = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unscaled.length() - [CtInvocationImpl][CtVariableReadImpl]number.scale()) - [CtLiteralImpl]1;
        [CtForImpl]for (; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
            [CtUnaryOperatorImpl][CtVariableWriteImpl]maxLength++;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'9') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0')) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]j >= [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]char digit = [CtInvocationImpl][CtVariableReadImpl]unscaled.charAt([CtVariableReadImpl]j);
                    [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtVariableReadImpl]digit);
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]j--;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0') && [CtBinaryOperatorImpl]([CtVariableReadImpl]power == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtLiteralImpl]'0');
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtLiteralImpl]',') [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// only add the grouping separator if we have more numbers
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]j >= [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]i > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1) == [CtLiteralImpl]'0'))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtVariableReadImpl]c);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'G') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'g')) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// only add the grouping separator if we have more numbers
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]j >= [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]i > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtBinaryOperatorImpl][CtVariableReadImpl]i - [CtLiteralImpl]1) == [CtLiteralImpl]'0'))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtVariableReadImpl]localGrouping);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'C') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'c')) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Currency currency = [CtInvocationImpl][CtTypeAccessImpl]java.util.Currency.getInstance([CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault());
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]currency.getCurrencyCode());
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]maxLength += [CtLiteralImpl]6;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'L') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'l')) || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'U')) || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'u')) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Currency currency = [CtInvocationImpl][CtTypeAccessImpl]java.util.Currency.getInstance([CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault());
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]currency.getSymbol());
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]maxLength += [CtLiteralImpl]9;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtLiteralImpl]'$') [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Currency currency = [CtInvocationImpl][CtTypeAccessImpl]java.util.Currency.getInstance([CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cs = [CtInvocationImpl][CtVariableReadImpl]currency.getSymbol();
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtVariableReadImpl]cs);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.h2.message.DbException.get([CtTypeAccessImpl]ErrorCode.INVALID_TO_CHAR_FORMAT, [CtVariableReadImpl]originalFormat);
            }
        }
        [CtIfImpl][CtCommentImpl]// if the format (to the left of the decimal point) was too small
        [CtCommentImpl]// to hold the number, return a big "######" string
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]j >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtLiteralImpl]"", [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() + [CtLiteralImpl]1, [CtLiteralImpl]"#", [CtLiteralImpl]true);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]separator < [CtInvocationImpl][CtVariableReadImpl]format.length()) [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtCommentImpl]// add the decimal point
            [CtVariableWriteImpl]maxLength++;
            [CtLocalVariableImpl][CtTypeReferenceImpl]char pt = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]separator);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]pt == [CtLiteralImpl]'d') || [CtBinaryOperatorImpl]([CtVariableReadImpl]pt == [CtLiteralImpl]'D')) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]localDecimal);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]pt);
            }
            [CtAssignmentImpl][CtCommentImpl]// start at the decimal point and fill in the numbers to the right,
            [CtCommentImpl]// working our way from left to right
            [CtVariableWriteImpl]i = [CtBinaryOperatorImpl][CtVariableReadImpl]separator + [CtLiteralImpl]1;
            [CtAssignmentImpl][CtVariableWriteImpl]j = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unscaled.length() - [CtInvocationImpl][CtVariableReadImpl]number.scale();
            [CtForImpl]for (; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
                [CtUnaryOperatorImpl][CtVariableWriteImpl]maxLength++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'9') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0')) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]j < [CtInvocationImpl][CtVariableReadImpl]unscaled.length()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]char digit = [CtInvocationImpl][CtVariableReadImpl]unscaled.charAt([CtVariableReadImpl]j);
                        [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]digit);
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]j++;
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0') || [CtVariableReadImpl]fillMode) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]'0');
                    }
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.h2.message.DbException.get([CtTypeAccessImpl]ErrorCode.INVALID_TO_CHAR_FORMAT, [CtVariableReadImpl]originalFormat);
                }
            }
        }
        [CtInvocationImpl]org.h2.expression.function.ToChar.addSign([CtVariableReadImpl]output, [CtInvocationImpl][CtVariableReadImpl]number.signum(), [CtVariableReadImpl]leadingSign, [CtVariableReadImpl]trailingSign, [CtVariableReadImpl]trailingMinus, [CtVariableReadImpl]angleBrackets, [CtVariableReadImpl]fillMode);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]power != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]'E');
            [CtInvocationImpl][CtVariableReadImpl]output.append([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]power < [CtLiteralImpl]0 ? [CtLiteralImpl]'-' : [CtLiteralImpl]'+');
            [CtInvocationImpl][CtVariableReadImpl]output.append([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]power) < [CtLiteralImpl]10 ? [CtLiteralImpl]"0" : [CtLiteralImpl]"");
            [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]power));
        }
        [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]power != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtLiteralImpl]' ');
            } else [CtBlockImpl]{
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]output.length() < [CtVariableReadImpl]maxLength) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtLiteralImpl]' ');
                } 
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]output.toString();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String zeroesAfterDecimalSeparator([CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal number) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String numberStr = [CtInvocationImpl][CtVariableReadImpl]number.toPlainString();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int idx = [CtInvocationImpl][CtVariableReadImpl]numberStr.indexOf([CtLiteralImpl]'.');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtVariableReadImpl]idx + [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allZeroes = [CtLiteralImpl]true;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int length = [CtInvocationImpl][CtVariableReadImpl]numberStr.length();
        [CtForImpl]for (; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]numberStr.charAt([CtVariableReadImpl]i) != [CtLiteralImpl]'0') [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]allZeroes = [CtLiteralImpl]false;
                [CtBreakImpl]break;
            }
        }
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]char[] zeroes = [CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtConditionalImpl][CtVariableReadImpl]allZeroes ? [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]length - [CtVariableReadImpl]idx) - [CtLiteralImpl]1 : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i - [CtLiteralImpl]1) - [CtVariableReadImpl]idx];
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtVariableReadImpl]zeroes, [CtLiteralImpl]'0');
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]zeroes);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void addSign([CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder output, [CtParameterImpl][CtTypeReferenceImpl]int signum, [CtParameterImpl][CtTypeReferenceImpl]boolean leadingSign, [CtParameterImpl][CtTypeReferenceImpl]boolean trailingSign, [CtParameterImpl][CtTypeReferenceImpl]boolean trailingMinus, [CtParameterImpl][CtTypeReferenceImpl]boolean angleBrackets, [CtParameterImpl][CtTypeReferenceImpl]boolean fillMode) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]angleBrackets) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]signum < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtLiteralImpl]'<');
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]'>');
            } else [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtLiteralImpl]' ');
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]' ');
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sign;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]signum == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sign = [CtLiteralImpl]"";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]signum < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sign = [CtLiteralImpl]"-";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]leadingSign || [CtVariableReadImpl]trailingSign) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sign = [CtLiteralImpl]"+";
            } else [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sign = [CtLiteralImpl]" ";
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]sign = [CtLiteralImpl]"";
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]trailingMinus || [CtVariableReadImpl]trailingSign) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]sign);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.insert([CtLiteralImpl]0, [CtVariableReadImpl]sign);
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]int findDecimalSeparator([CtParameterImpl][CtTypeReferenceImpl]java.lang.String format) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtInvocationImpl][CtVariableReadImpl]format.indexOf([CtLiteralImpl]'.');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]index = [CtInvocationImpl][CtVariableReadImpl]format.indexOf([CtLiteralImpl]'D');
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]index = [CtInvocationImpl][CtVariableReadImpl]format.indexOf([CtLiteralImpl]'d');
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]index = [CtInvocationImpl][CtVariableReadImpl]format.length();
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]index;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]int calculateScale([CtParameterImpl][CtTypeReferenceImpl]java.lang.String format, [CtParameterImpl][CtTypeReferenceImpl]int separator) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int scale = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtVariableReadImpl]separator; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'9')) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]scale++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]scale;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String toRomanNumeral([CtParameterImpl][CtTypeReferenceImpl]int number) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtFieldReadImpl]org.h2.expression.function.ToChar.[CtFieldReferenceImpl]ROMAN_VALUES.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int value = [CtArrayReadImpl][CtFieldReadImpl]org.h2.expression.function.ToChar.ROMAN_VALUES[[CtVariableReadImpl]i];
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String numeral = [CtArrayReadImpl][CtFieldReadImpl]org.h2.expression.function.ToChar.ROMAN_NUMERALS[[CtVariableReadImpl]i];
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]number >= [CtVariableReadImpl]value) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]result.append([CtVariableReadImpl]numeral);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]number -= [CtVariableReadImpl]value;
            } 
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String toHex([CtParameterImpl][CtTypeReferenceImpl]java.math.BigDecimal number, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String format) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean fillMode = [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toUpperEnglish([CtVariableReadImpl]format).startsWith([CtLiteralImpl]"FM");
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean uppercase = [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]format.contains([CtLiteralImpl]"x");
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean zeroPadded = [CtInvocationImpl][CtVariableReadImpl]format.startsWith([CtLiteralImpl]"0");
        [CtLocalVariableImpl][CtTypeReferenceImpl]int digits = [CtLiteralImpl]0;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'0') || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'X')) || [CtBinaryOperatorImpl]([CtVariableReadImpl]c == [CtLiteralImpl]'x')) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]digits++;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]number.setScale([CtLiteralImpl]0, [CtFieldReadImpl][CtTypeAccessImpl]java.math.RoundingMode.[CtFieldReferenceImpl]HALF_UP).intValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String hex = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toHexString([CtVariableReadImpl]i);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]digits < [CtInvocationImpl][CtVariableReadImpl]hex.length()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]hex = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtLiteralImpl]"", [CtBinaryOperatorImpl][CtVariableReadImpl]digits + [CtLiteralImpl]1, [CtLiteralImpl]"#", [CtLiteralImpl]true);
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]uppercase) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hex = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toUpperEnglish([CtVariableReadImpl]hex);
            }
            [CtIfImpl]if ([CtVariableReadImpl]zeroPadded) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hex = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtVariableReadImpl]hex, [CtVariableReadImpl]digits, [CtLiteralImpl]"0", [CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hex = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtVariableReadImpl]hex, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.length() + [CtLiteralImpl]1, [CtLiteralImpl]" ", [CtLiteralImpl]false);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]hex;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the date (month / weekday / ...) names.
     *
     * @param names
     * 		the field
     * @return the names
     */
    public static [CtArrayTypeReferenceImpl]java.lang.String[] getDateNames([CtParameterImpl][CtTypeReferenceImpl]int names) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[][] result = [CtFieldReadImpl]org.h2.expression.function.ToChar.NAMES;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]5][];
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.DateFormatSymbols dfs = [CtInvocationImpl][CtTypeAccessImpl]java.text.DateFormatSymbols.getInstance();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtFieldReadImpl]org.h2.expression.function.ToChar.MONTHS] = [CtInvocationImpl][CtVariableReadImpl]dfs.getMonths();
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] months = [CtInvocationImpl][CtVariableReadImpl]dfs.getShortMonths();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtLiteralImpl]12; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String month = [CtArrayReadImpl][CtVariableReadImpl]months[[CtVariableReadImpl]i];
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]month.endsWith([CtLiteralImpl]".")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]months[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]month.substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]month.length() - [CtLiteralImpl]1);
                }
            }
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtFieldReadImpl]org.h2.expression.function.ToChar.SHORT_MONTHS] = [CtVariableReadImpl]months;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtFieldReadImpl]org.h2.expression.function.ToChar.WEEKDAYS] = [CtInvocationImpl][CtVariableReadImpl]dfs.getWeekdays();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtFieldReadImpl]org.h2.expression.function.ToChar.SHORT_WEEKDAYS] = [CtInvocationImpl][CtVariableReadImpl]dfs.getShortWeekdays();
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]result[[CtFieldReadImpl]org.h2.expression.function.ToChar.AM_PM] = [CtInvocationImpl][CtVariableReadImpl]dfs.getAmPmStrings();
            [CtAssignmentImpl][CtFieldWriteImpl]org.h2.expression.function.ToChar.NAMES = [CtVariableReadImpl]result;
        }
        [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]result[[CtVariableReadImpl]names];
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used for testing.
     */
    public static [CtTypeReferenceImpl]void clearNames() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]org.h2.expression.function.ToChar.NAMES = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns time zone display name or ID for the specified date-time value.
     *
     * @param session
     * 		the session
     * @param value
     * 		value
     * @param tzd
     * 		if {@code true} return TZD (time zone region with Daylight Saving
     * 		Time information included), if {@code false} return TZR (time zone
     * 		region)
     * @return time zone display name or ID
     */
    private static [CtTypeReferenceImpl]java.lang.String getTimeZone([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.h2.value.Value value, [CtParameterImpl][CtTypeReferenceImpl]boolean tzd) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.value.ValueTimestampTimeZone) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.timeZoneNameFromOffsetSeconds([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.value.ValueTimestampTimeZone) (value)).getTimeZoneOffsetSeconds());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.h2.value.ValueTimeTimeZone) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.timeZoneNameFromOffsetSeconds([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.h2.value.ValueTimeTimeZone) (value)).getTimeZoneOffsetSeconds());
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.util.TimeZoneProvider tz = [CtInvocationImpl][CtVariableReadImpl]session.currentTimeZone();
            [CtIfImpl]if ([CtVariableReadImpl]tzd) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.value.ValueTimestamp v = [CtInvocationImpl](([CtTypeReferenceImpl]org.h2.value.ValueTimestamp) ([CtVariableReadImpl]value.convertTo([CtTypeAccessImpl]TypeInfo.TYPE_TIMESTAMP, [CtVariableReadImpl]session)));
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tz.getShortId([CtInvocationImpl][CtVariableReadImpl]tz.getEpochSecondsFromLocal([CtInvocationImpl][CtVariableReadImpl]v.getDateValue(), [CtInvocationImpl][CtVariableReadImpl]v.getTimeNanos()));
            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]tz.getId();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Emulates Oracle's TO_CHAR(datetime) function.
     *
     * <p><table border="1">
     * <th><td>Input</td>
     * <td>Output</td>
     * <td>Closest {@link SimpleDateFormat} Equivalent</td></th>
     * <tr><td>- / , . ; : "text"</td>
     * <td>Reproduced verbatim.</td>
     * <td>'text'</td></tr>
     * <tr><td>A.D. AD B.C. BC</td>
     * <td>Era designator, with or without periods.</td>
     * <td>G</td></tr>
     * <tr><td>A.M. AM P.M. PM</td>
     * <td>AM/PM marker.</td>
     * <td>a</td></tr>
     * <tr><td>CC SCC</td>
     * <td>Century.</td>
     * <td>None.</td></tr>
     * <tr><td>D</td>
     * <td>Day of week.</td>
     * <td>u</td></tr>
     * <tr><td>DAY</td>
     * <td>Name of day.</td>
     * <td>EEEE</td></tr>
     * <tr><td>DY</td>
     * <td>Abbreviated day name.</td>
     * <td>EEE</td></tr>
     * <tr><td>DD</td>
     * <td>Day of month.</td>
     * <td>d</td></tr>
     * <tr><td>DDD</td>
     * <td>Day of year.</td>
     * <td>D</td></tr>
     * <tr><td>DL</td>
     * <td>Long date format.</td>
     * <td>EEEE, MMMM d, yyyy</td></tr>
     * <tr><td>DS</td>
     * <td>Short date format.</td>
     * <td>MM/dd/yyyy</td></tr>
     * <tr><td>E</td>
     * <td>Abbreviated era name (Japanese, Chinese, Thai)</td>
     * <td>None.</td></tr>
     * <tr><td>EE</td>
     * <td>Full era name (Japanese, Chinese, Thai)</td>
     * <td>None.</td></tr>
     * <tr><td>FF[1-9]</td>
     * <td>Fractional seconds.</td>
     * <td>S</td></tr>
     * <tr><td>FM</td>
     * <td>Returns values with no leading or trailing spaces.</td>
     * <td>None.</td></tr>
     * <tr><td>FX</td>
     * <td>Requires exact matches between character data and format model.</td>
     * <td>None.</td></tr>
     * <tr><td>HH HH12</td>
     * <td>Hour in AM/PM (1-12).</td>
     * <td>hh</td></tr>
     * <tr><td>HH24</td>
     * <td>Hour in day (0-23).</td>
     * <td>HH</td></tr>
     * <tr><td>IW</td>
     * <td>Week in year.</td>
     * <td>w</td></tr>
     * <tr><td>WW</td>
     * <td>Week in year.</td>
     * <td>w</td></tr>
     * <tr><td>W</td>
     * <td>Week in month.</td>
     * <td>W</td></tr>
     * <tr><td>IYYY IYY IY I</td>
     * <td>Last 4/3/2/1 digit(s) of ISO year.</td>
     * <td>yyyy yyy yy y</td></tr>
     * <tr><td>RRRR RR</td>
     * <td>Last 4/2 digits of year.</td>
     * <td>yyyy yy</td></tr>
     * <tr><td>Y,YYY</td>
     * <td>Year with comma.</td>
     * <td>None.</td></tr>
     * <tr><td>YEAR SYEAR</td>
     * <td>Year spelled out (S prefixes BC years with minus sign).</td>
     * <td>None.</td></tr>
     * <tr><td>YYYY SYYYY</td>
     * <td>4-digit year (S prefixes BC years with minus sign).</td>
     * <td>yyyy</td></tr>
     * <tr><td>YYY YY Y</td>
     * <td>Last 3/2/1 digit(s) of year.</td>
     * <td>yyy yy y</td></tr>
     * <tr><td>J</td>
     * <td>Julian day (number of days since January 1, 4712 BC).</td>
     * <td>None.</td></tr>
     * <tr><td>MI</td>
     * <td>Minute in hour.</td>
     * <td>mm</td></tr>
     * <tr><td>MM</td>
     * <td>Month in year.</td>
     * <td>MM</td></tr>
     * <tr><td>MON</td>
     * <td>Abbreviated name of month.</td>
     * <td>MMM</td></tr>
     * <tr><td>MONTH</td>
     * <td>Name of month, padded with spaces.</td>
     * <td>MMMM</td></tr>
     * <tr><td>RM</td>
     * <td>Roman numeral month.</td>
     * <td>None.</td></tr>
     * <tr><td>Q</td>
     * <td>Quarter of year.</td>
     * <td>None.</td></tr>
     * <tr><td>SS</td>
     * <td>Seconds in minute.</td>
     * <td>ss</td></tr>
     * <tr><td>SSSSS</td>
     * <td>Seconds in day.</td>
     * <td>None.</td></tr>
     * <tr><td>TS</td>
     * <td>Short time format.</td>
     * <td>h:mm:ss aa</td></tr>
     * <tr><td>TZD</td>
     * <td>Daylight savings time zone abbreviation.</td>
     * <td>z</td></tr>
     * <tr><td>TZR</td>
     * <td>Time zone region information.</td>
     * <td>zzzz</td></tr>
     * <tr><td>X</td>
     * <td>Local radix character.</td>
     * <td>None.</td></tr>
     * </table>
     * <p>
     * See also TO_CHAR(datetime) and datetime format models
     * in the Oracle documentation.
     *
     * @param session
     * 		the session
     * @param value
     * 		the date-time value to format
     * @param format
     * 		the format pattern to use (if any)
     * @param nlsParam
     * 		the NLS parameter (if any)
     * @return the formatted timestamp
     */
    public static [CtTypeReferenceImpl]java.lang.String toCharDateTime([CtParameterImpl][CtTypeReferenceImpl]org.h2.engine.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.h2.value.Value value, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String format, [CtParameterImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    [CtTypeReferenceImpl]java.lang.String nlsParam) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] a = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.dateAndTimeFromValue([CtVariableReadImpl]value, [CtVariableReadImpl]session);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long dateValue = [CtArrayReadImpl][CtVariableReadImpl]a[[CtLiteralImpl]0];
        [CtLocalVariableImpl][CtTypeReferenceImpl]long timeNanos = [CtArrayReadImpl][CtVariableReadImpl]a[[CtLiteralImpl]1];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int year = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.yearFromDateValue([CtVariableReadImpl]dateValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int monthOfYear = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.monthFromDateValue([CtVariableReadImpl]dateValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int dayOfMonth = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.dayFromDateValue([CtVariableReadImpl]dateValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int posYear = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]year);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int second = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]timeNanos / [CtLiteralImpl]1000000000));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nanos = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]timeNanos - [CtBinaryOperatorImpl]([CtVariableReadImpl]second * [CtLiteralImpl]1000000000)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minute = [CtBinaryOperatorImpl][CtVariableReadImpl]second / [CtLiteralImpl]60;
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]second -= [CtBinaryOperatorImpl][CtVariableReadImpl]minute * [CtLiteralImpl]60;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int hour = [CtBinaryOperatorImpl][CtVariableReadImpl]minute / [CtLiteralImpl]60;
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]minute -= [CtBinaryOperatorImpl][CtVariableReadImpl]hour * [CtLiteralImpl]60;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int h12 = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]hour + [CtLiteralImpl]11) % [CtLiteralImpl]12) + [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isAM = [CtBinaryOperatorImpl][CtVariableReadImpl]hour < [CtLiteralImpl]12;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]format == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]format = [CtLiteralImpl]"DD-MON-YY HH.MI.SS.FF PM";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder output = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean fillMode = [CtLiteralImpl]true;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0, [CtLocalVariableImpl]length = [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]length;) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.h2.expression.function.ToChar.Capitalization cap;
            [CtIfImpl][CtCommentImpl]// AD / BC
            if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"A.D.", [CtLiteralImpl]"B.C.")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String era = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]year > [CtLiteralImpl]0) ? [CtLiteralImpl]"A.D." : [CtLiteralImpl]"B.C.";
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]era));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"AD", [CtLiteralImpl]"BC")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String era = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]year > [CtLiteralImpl]0) ? [CtLiteralImpl]"AD" : [CtLiteralImpl]"BC";
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]era));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// AM / PM
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"A.M.", [CtLiteralImpl]"P.M.")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String am = [CtConditionalImpl]([CtVariableReadImpl]isAM) ? [CtLiteralImpl]"A.M." : [CtLiteralImpl]"P.M.";
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]am));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"AM", [CtLiteralImpl]"PM")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String am = [CtConditionalImpl]([CtVariableReadImpl]isAM) ? [CtLiteralImpl]"AM" : [CtLiteralImpl]"PM";
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]am));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Long/short date/time format
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DL") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String day = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.WEEKDAYS)[[CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getSundayDayOfWeek([CtVariableReadImpl]dateValue)];
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String month = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.MONTHS)[[CtBinaryOperatorImpl][CtVariableReadImpl]monthOfYear - [CtLiteralImpl]1];
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]day).append([CtLiteralImpl]", ").append([CtVariableReadImpl]month).append([CtLiteralImpl]' ').append([CtVariableReadImpl]dayOfMonth).append([CtLiteralImpl]", ");
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]4, [CtVariableReadImpl]posYear);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DS") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]monthOfYear).append([CtLiteralImpl]'/');
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]dayOfMonth).append([CtLiteralImpl]'/');
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]4, [CtVariableReadImpl]posYear);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"TS") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]h12).append([CtLiteralImpl]':');
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]minute).append([CtLiteralImpl]':');
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]second).append([CtLiteralImpl]' ').append([CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.AM_PM)[[CtConditionalImpl][CtVariableReadImpl]isAM ? [CtLiteralImpl]0 : [CtLiteralImpl]1]);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Day
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DDD") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getDayOfYear([CtVariableReadImpl]dateValue));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DD") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]dayOfMonth);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DY")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String day = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.SHORT_WEEKDAYS)[[CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getSundayDayOfWeek([CtVariableReadImpl]dateValue)];
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]day));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"DAY")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String day = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.WEEKDAYS)[[CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getSundayDayOfWeek([CtVariableReadImpl]dateValue)];
                [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]day = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtVariableReadImpl]day, [CtInvocationImpl][CtLiteralImpl]"Wednesday".length(), [CtLiteralImpl]" ", [CtLiteralImpl]true);
                }
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]day));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"D") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getSundayDayOfWeek([CtVariableReadImpl]dateValue));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"J") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.absoluteDayFromDateValue([CtVariableReadImpl]dateValue) - [CtFieldReadImpl]org.h2.expression.function.ToChar.JULIAN_EPOCH);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Hours
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"HH24") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]hour);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"HH12") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]h12);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"HH") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]h12);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Minutes
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"MI") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]minute);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Seconds
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"SSSSS") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int seconds = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]timeNanos / [CtLiteralImpl]1000000000));
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]seconds);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]5;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"SS") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]second);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Fractional seconds
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"FF1", [CtLiteralImpl]"FF2", [CtLiteralImpl]"FF3", [CtLiteralImpl]"FF4", [CtLiteralImpl]"FF5", [CtLiteralImpl]"FF6", [CtLiteralImpl]"FF7", [CtLiteralImpl]"FF8", [CtLiteralImpl]"FF9") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int x = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]format.charAt([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]2) - [CtLiteralImpl]'0';
                [CtLocalVariableImpl][CtTypeReferenceImpl]int ff = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]nanos * [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.pow([CtLiteralImpl]10, [CtBinaryOperatorImpl][CtVariableReadImpl]x - [CtLiteralImpl]9)));
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtVariableReadImpl]x, [CtVariableReadImpl]ff);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"FF") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]9, [CtVariableReadImpl]nanos);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Time zone
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"TZR") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl]org.h2.expression.function.ToChar.getTimeZone([CtVariableReadImpl]session, [CtVariableReadImpl]value, [CtLiteralImpl]false));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"TZD") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl]org.h2.expression.function.ToChar.getTimeZone([CtVariableReadImpl]session, [CtVariableReadImpl]value, [CtLiteralImpl]true));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"TZH") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int hours = [CtInvocationImpl][CtTypeAccessImpl]org.h2.expression.function.DateTimeFunction.extractDateTime([CtVariableReadImpl]session, [CtVariableReadImpl]value, [CtTypeAccessImpl]DateTimeFunction.TIMEZONE_HOUR);
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]hours < [CtLiteralImpl]0 ? [CtLiteralImpl]'-' : [CtLiteralImpl]'+');
                [CtAssignmentImpl][CtVariableWriteImpl]hours = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]hours);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hours == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]"00");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]hours);
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"TZM") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int mins = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]org.h2.expression.function.DateTimeFunction.extractDateTime([CtVariableReadImpl]session, [CtVariableReadImpl]value, [CtTypeAccessImpl]DateTimeFunction.TIMEZONE_MINUTE));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mins == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]"00");
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]mins);
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
                [CtCommentImpl]// Week
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"WW") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getDayOfYear([CtVariableReadImpl]dateValue) - [CtLiteralImpl]1) / [CtLiteralImpl]7) + [CtLiteralImpl]1);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"IW") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getIsoWeekOfYear([CtVariableReadImpl]dateValue));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"W") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]dayOfMonth - [CtLiteralImpl]1) / [CtLiteralImpl]7) + [CtLiteralImpl]1);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Year
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"Y,YYY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.DecimalFormat([CtLiteralImpl]"#,###").format([CtVariableReadImpl]posYear));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]5;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"SYYYY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Should be <= 0, but Oracle prints negative years with off-by-one difference
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]year < [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]output.append([CtLiteralImpl]'-');
                }
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]4, [CtVariableReadImpl]posYear);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]5;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"YYYY", [CtLiteralImpl]"RRRR") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]4, [CtVariableReadImpl]posYear);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"IYYY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]4, [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getIsoWeekYear([CtVariableReadImpl]dateValue)));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]4;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"YYY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]3, [CtBinaryOperatorImpl][CtVariableReadImpl]posYear % [CtLiteralImpl]1000);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"IYY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendZeroPadded([CtVariableReadImpl]output, [CtLiteralImpl]3, [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getIsoWeekYear([CtVariableReadImpl]dateValue)) % [CtLiteralImpl]1000);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"YY", [CtLiteralImpl]"RR") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtBinaryOperatorImpl][CtVariableReadImpl]posYear % [CtLiteralImpl]100);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"IY") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getIsoWeekYear([CtVariableReadImpl]dateValue)) % [CtLiteralImpl]100);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"Y") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtBinaryOperatorImpl][CtVariableReadImpl]posYear % [CtLiteralImpl]10);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"I") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]org.h2.util.DateTimeUtils.getIsoWeekYear([CtVariableReadImpl]dateValue)) % [CtLiteralImpl]10);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Month / quarter
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"MONTH")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String month = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.MONTHS)[[CtBinaryOperatorImpl][CtVariableReadImpl]monthOfYear - [CtLiteralImpl]1];
                [CtIfImpl]if ([CtVariableReadImpl]fillMode) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]month = [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.pad([CtVariableReadImpl]month, [CtInvocationImpl][CtLiteralImpl]"September".length(), [CtLiteralImpl]" ", [CtLiteralImpl]true);
                }
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]month));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]5;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"MON")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String month = [CtArrayReadImpl][CtInvocationImpl]org.h2.expression.function.ToChar.getDateNames([CtFieldReadImpl]org.h2.expression.function.ToChar.SHORT_MONTHS)[[CtBinaryOperatorImpl][CtVariableReadImpl]monthOfYear - [CtLiteralImpl]1];
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtVariableReadImpl]month));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"MM") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.appendTwoDigits([CtVariableReadImpl]output, [CtVariableReadImpl]monthOfYear);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]cap = [CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"RM")) != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]cap.apply([CtInvocationImpl]org.h2.expression.function.ToChar.toRomanNumeral([CtVariableReadImpl]monthOfYear)));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"Q") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int q = [CtBinaryOperatorImpl][CtLiteralImpl]1 + [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]monthOfYear - [CtLiteralImpl]1) / [CtLiteralImpl]3);
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]q);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Local radix character
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"X") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.text.DecimalFormatSymbols.getInstance().getDecimalSeparator();
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]c);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Format modifiers
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"FM") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]fillMode = [CtUnaryOperatorImpl]![CtVariableReadImpl]fillMode;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"FX") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]2;
                [CtCommentImpl]// Literal text
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]org.h2.expression.function.ToChar.containsAt([CtVariableReadImpl]format, [CtVariableReadImpl]i, [CtLiteralImpl]"\"") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForImpl]for ([CtAssignmentImpl][CtVariableWriteImpl]i = [CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]format.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]char c = [CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c != [CtLiteralImpl]'"') [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]output.append([CtVariableReadImpl]c);
                    } else [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]i++;
                        [CtBreakImpl]break;
                    }
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]'-') || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]'/')) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]',')) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]'.')) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]';')) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]':')) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i) == [CtLiteralImpl]' ')) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]output.append([CtInvocationImpl][CtVariableReadImpl]format.charAt([CtVariableReadImpl]i));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]i += [CtLiteralImpl]1;
                [CtCommentImpl]// Anything else
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]org.h2.message.DbException.get([CtTypeAccessImpl]ErrorCode.INVALID_TO_CHAR_FORMAT, [CtVariableReadImpl]format);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]output.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a capitalization strategy if the specified string contains any of
     * the specified substrings at the specified index. The capitalization
     * strategy indicates the casing of the substring that was found. If none of
     * the specified substrings are found, this method returns <code>null</code>
     * .
     *
     * @param s
     * 		the string to check
     * @param index
     * 		the index to check at
     * @param substrings
     * 		the substrings to check for within the string
     * @return a capitalization strategy if the specified string contains any of
    the specified substrings at the specified index,
    <code>null</code> otherwise
     */
    private static [CtTypeReferenceImpl]org.h2.expression.function.ToChar.Capitalization containsAt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s, [CtParameterImpl][CtTypeReferenceImpl]int index, [CtParameterImpl]java.lang.String... substrings) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String substring : [CtVariableReadImpl]substrings) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]index + [CtInvocationImpl][CtVariableReadImpl]substring.length()) <= [CtInvocationImpl][CtVariableReadImpl]s.length()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]true;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean up1 = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean up2 = [CtLiteralImpl]null;
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]substring.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]char c1 = [CtInvocationImpl][CtVariableReadImpl]s.charAt([CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtVariableReadImpl]i);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]char c2 = [CtInvocationImpl][CtVariableReadImpl]substring.charAt([CtVariableReadImpl]i);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c1 != [CtVariableReadImpl]c2) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.toUpperCase([CtVariableReadImpl]c1) != [CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.toUpperCase([CtVariableReadImpl]c2))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]false;
                        [CtBreakImpl]break;
                    } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.isLetter([CtVariableReadImpl]c1)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]up1 == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]up1 = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.isUpperCase([CtVariableReadImpl]c1);
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]up2 == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]up2 = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.isUpperCase([CtVariableReadImpl]c1);
                        }
                    }
                }
                [CtIfImpl]if ([CtVariableReadImpl]found) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.toCapitalization([CtVariableReadImpl]up1, [CtVariableReadImpl]up2);
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtEnumImpl][CtJavaDocImpl]/**
     * Represents a capitalization / casing strategy.
     */
    public enum Capitalization {

        [CtEnumValueImpl][CtJavaDocImpl]/**
         * All letters are uppercased.
         */
        UPPERCASE,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * All letters are lowercased.
         */
        LOWERCASE,
        [CtEnumValueImpl][CtJavaDocImpl]/**
         * The string is capitalized (first letter uppercased, subsequent
         * letters lowercased).
         */
        CAPITALIZE;
        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the capitalization / casing strategy which should be used
         * when the first and second letters have the specified casing.
         *
         * @param up1
         * 		whether or not the first letter is uppercased
         * @param up2
         * 		whether or not the second letter is uppercased
         * @return the capitalization / casing strategy which should be used
        when the first and second letters have the specified casing
         */
        static [CtTypeReferenceImpl]org.h2.expression.function.ToChar.Capitalization toCapitalization([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean up1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean up2) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]up1 == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]CAPITALIZE;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]up2 == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]up1 ? [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]UPPERCASE : [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]LOWERCASE;
            } else [CtIfImpl]if ([CtVariableReadImpl]up1) [CtBlockImpl]{
                [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]up2 ? [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]UPPERCASE : [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]CAPITALIZE;
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]org.h2.expression.function.ToChar.Capitalization.[CtFieldReferenceImpl]LOWERCASE;
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Applies this capitalization strategy to the specified string.
         *
         * @param s
         * 		the string to apply this strategy to
         * @return the resultant string
         */
        public [CtTypeReferenceImpl]java.lang.String apply([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]s.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]s;
            }
            [CtSwitchImpl]switch ([CtThisAccessImpl]this) {
                [CtCaseImpl]case UPPERCASE :
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toUpperEnglish([CtVariableReadImpl]s);
                [CtCaseImpl]case LOWERCASE :
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toLowerEnglish([CtVariableReadImpl]s);
                [CtCaseImpl]case CAPITALIZE :
                    [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Character.toUpperCase([CtInvocationImpl][CtVariableReadImpl]s.charAt([CtLiteralImpl]0)) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.length() > [CtLiteralImpl]1 ? [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.h2.util.StringUtils.toLowerEnglish([CtVariableReadImpl]s).substring([CtLiteralImpl]1) : [CtLiteralImpl]"");
                [CtCaseImpl]default :
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unknown capitalization strategy: " + [CtThisAccessImpl]this);
            }
        }
    }
}
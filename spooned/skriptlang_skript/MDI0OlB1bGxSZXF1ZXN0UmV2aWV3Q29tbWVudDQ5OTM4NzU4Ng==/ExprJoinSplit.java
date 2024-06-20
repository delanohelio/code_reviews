[CompilationUnitImpl][CtJavaDocImpl]/**
 * This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
[CtPackageDeclarationImpl]package ch.njol.skript.expressions;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import ch.njol.skript.doc.Name;
[CtUnresolvedImport]import org.bukkit.event.Event;
[CtUnresolvedImport]import ch.njol.util.StringUtils;
[CtUnresolvedImport]import ch.njol.skript.lang.SkriptParser.ParseResult;
[CtUnresolvedImport]import ch.njol.skript.lang.ExpressionType;
[CtUnresolvedImport]import ch.njol.skript.doc.Since;
[CtUnresolvedImport]import ch.njol.skript.lang.Expression;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.Nullable;
[CtUnresolvedImport]import ch.njol.skript.doc.Examples;
[CtUnresolvedImport]import ch.njol.skript.Skript;
[CtUnresolvedImport]import ch.njol.skript.doc.Description;
[CtUnresolvedImport]import ch.njol.util.Kleenean;
[CtUnresolvedImport]import ch.njol.skript.lang.util.SimpleExpression;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Peter Güttinger
 */
[CtAnnotationImpl]@ch.njol.skript.doc.Name([CtLiteralImpl]"Join & Split")
[CtAnnotationImpl]@ch.njol.skript.doc.Description([CtLiteralImpl]"Joins several texts with a common delimiter (e.g. \", \"), or splits a text into multiple texts at a given delimiter.")
[CtAnnotationImpl]@ch.njol.skript.doc.Examples([CtNewArrayImpl]{ [CtLiteralImpl]"message \"Online players: %join all players with \"\" | \"\"%\" # %all players% would use the default \"x, y, and z\"", [CtLiteralImpl]"set {_s::*} to the string argument split at \",\"" })
[CtAnnotationImpl]@ch.njol.skript.doc.Since([CtLiteralImpl]"2.1, INSERT VERSION (added regex support)")
public class ExprJoinSplit extends [CtTypeReferenceImpl]ch.njol.skript.lang.util.SimpleExpression<[CtTypeReferenceImpl]java.lang.String> {
    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.registerExpression([CtFieldReadImpl]ch.njol.skript.expressions.ExprJoinSplit.class, [CtFieldReadImpl]java.lang.String.class, [CtTypeAccessImpl]ExpressionType.COMBINED, [CtLiteralImpl]"(concat[enate]|join) %strings% [(with|using|by) [[the] delimiter] %-string%]", [CtLiteralImpl]"split %string% (at|using|by) [[the] delimiter] %string%", [CtLiteralImpl]"%string% split (at|using|by) [[the] delimiter] %string%", [CtLiteralImpl]"regex split %string% (at|using|by) [[the] delimiter] %string%", [CtLiteralImpl]"regex %string% split (at|using|by) [[the] delimiter] %string%");
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean regex;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean join;

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"null")
    private [CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]java.lang.String> strings;

    [CtFieldImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    private [CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]java.lang.String> delimiter;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"unchecked", [CtLiteralImpl]"null" })
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean init([CtParameterImpl]final [CtArrayTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtWildcardReferenceImpl]?>[] exprs, [CtParameterImpl]final [CtTypeReferenceImpl]int matchedPattern, [CtParameterImpl]final [CtTypeReferenceImpl]ch.njol.util.Kleenean isDelayed, [CtParameterImpl]final [CtTypeReferenceImpl]ch.njol.skript.lang.SkriptParser.ParseResult parseResult) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]join = [CtBinaryOperatorImpl][CtVariableReadImpl]matchedPattern == [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl]regex = [CtBinaryOperatorImpl][CtVariableReadImpl]matchedPattern >= [CtLiteralImpl]3;
        [CtAssignmentImpl][CtFieldWriteImpl]strings = [CtArrayReadImpl](([CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]java.lang.String>) ([CtVariableReadImpl]exprs[[CtLiteralImpl]0]));
        [CtAssignmentImpl][CtFieldWriteImpl]delimiter = [CtArrayReadImpl](([CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]java.lang.String>) ([CtVariableReadImpl]exprs[[CtLiteralImpl]1]));
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    protected [CtArrayTypeReferenceImpl]java.lang.String[] get([CtParameterImpl]final [CtTypeReferenceImpl]org.bukkit.event.Event e) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] s = [CtInvocationImpl][CtFieldReadImpl]strings.getArray([CtVariableReadImpl]e);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String d = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]delimiter != [CtLiteralImpl]null) ? [CtInvocationImpl][CtFieldReadImpl]delimiter.getSingle([CtVariableReadImpl]e) : [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]s.length == [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]d == [CtLiteralImpl]null))[CtBlockImpl]
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0];

        [CtIfImpl]if ([CtFieldReadImpl]join) [CtBlockImpl]{
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtTypeAccessImpl]ch.njol.util.StringUtils.join([CtVariableReadImpl]s, [CtVariableReadImpl]d) };
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]s[[CtLiteralImpl]0].split([CtConditionalImpl][CtFieldReadImpl]regex ? [CtVariableReadImpl]d : [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.quote([CtVariableReadImpl]d), [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isSingle() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]join;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.String> getReturnType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.String.class;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString([CtParameterImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    final [CtTypeReferenceImpl]org.bukkit.event.Event e, [CtParameterImpl]final [CtTypeReferenceImpl]boolean debug) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtFieldReadImpl]join ? [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"join " + [CtInvocationImpl][CtFieldReadImpl]strings.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug)) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]delimiter != [CtLiteralImpl]null ? [CtBinaryOperatorImpl][CtLiteralImpl]" with " + [CtInvocationImpl][CtFieldReadImpl]delimiter.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug) : [CtLiteralImpl]"") : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtConditionalImpl]([CtFieldReadImpl]regex ? [CtLiteralImpl]"regex " : [CtLiteralImpl]"") + [CtLiteralImpl]"split ") + [CtInvocationImpl][CtFieldReadImpl]strings.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug)) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]delimiter != [CtLiteralImpl]null ? [CtBinaryOperatorImpl][CtLiteralImpl]" at " + [CtInvocationImpl][CtFieldReadImpl]delimiter.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug) : [CtLiteralImpl]"");
    }
}
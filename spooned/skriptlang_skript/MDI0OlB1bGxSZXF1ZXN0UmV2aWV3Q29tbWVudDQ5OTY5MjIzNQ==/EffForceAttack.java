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
 * Copyright Peter Güttinger, SkriptLang team and contributors
 */
[CtPackageDeclarationImpl]package ch.njol.skript.effects;
[CtUnresolvedImport]import ch.njol.skript.doc.RequiredPlugins;
[CtUnresolvedImport]import ch.njol.skript.lang.Effect;
[CtUnresolvedImport]import org.bukkit.entity.Entity;
[CtUnresolvedImport]import ch.njol.skript.doc.Name;
[CtUnresolvedImport]import org.bukkit.event.Event;
[CtUnresolvedImport]import org.bukkit.entity.LivingEntity;
[CtUnresolvedImport]import ch.njol.skript.lang.SkriptParser.ParseResult;
[CtUnresolvedImport]import ch.njol.skript.doc.Since;
[CtUnresolvedImport]import ch.njol.skript.lang.Expression;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.Nullable;
[CtUnresolvedImport]import ch.njol.skript.doc.Examples;
[CtUnresolvedImport]import ch.njol.skript.Skript;
[CtUnresolvedImport]import ch.njol.skript.doc.Description;
[CtUnresolvedImport]import ch.njol.util.Kleenean;
[CtClassImpl][CtAnnotationImpl]@ch.njol.skript.doc.Name([CtLiteralImpl]"Force Attack")
[CtAnnotationImpl]@ch.njol.skript.doc.Description([CtLiteralImpl]"Makes a living entity attack an entity with a melee attack.")
[CtAnnotationImpl]@ch.njol.skript.doc.Examples([CtNewArrayImpl]{ [CtLiteralImpl]"spawn a wolf at player's location", [CtLiteralImpl]"make last spawned wolf attack player" })
[CtAnnotationImpl]@ch.njol.skript.doc.Since([CtLiteralImpl]"INSERT VERSION")
[CtAnnotationImpl]@ch.njol.skript.doc.RequiredPlugins([CtLiteralImpl]"1.15.2 or newer")
public class EffForceAttack extends [CtTypeReferenceImpl]ch.njol.skript.lang.Effect {
    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.registerEffect([CtFieldReadImpl]ch.njol.skript.effects.EffForceAttack.class, [CtLiteralImpl]"make %livingentitys% attack %entity%", [CtLiteralImpl]"force %livingentitys% to attack %entity%");
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]boolean ATTACK_IS_SUPPORTED = [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.methodExists([CtFieldReadImpl]org.bukkit.entity.LivingEntity.class, [CtLiteralImpl]"attack", [CtFieldReadImpl]org.bukkit.entity.Entity.class);

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"null")
    private [CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]org.bukkit.entity.LivingEntity> entities;

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"null")
    private [CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]org.bukkit.entity.Entity> target;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean init([CtParameterImpl][CtArrayTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtWildcardReferenceImpl]?>[] exprs, [CtParameterImpl][CtTypeReferenceImpl]int matchedPattern, [CtParameterImpl][CtTypeReferenceImpl]ch.njol.util.Kleenean isDelayed, [CtParameterImpl][CtTypeReferenceImpl]ch.njol.skript.lang.SkriptParser.ParseResult parseResult) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]ch.njol.skript.effects.EffForceAttack.ATTACK_IS_SUPPORTED) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.error([CtLiteralImpl]"The force attack effect requires server version 1.15.2 or newer");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]entities = [CtArrayReadImpl](([CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]org.bukkit.entity.LivingEntity>) ([CtVariableReadImpl]exprs[[CtLiteralImpl]0]));
        [CtAssignmentImpl][CtFieldWriteImpl]target = [CtArrayReadImpl](([CtTypeReferenceImpl]ch.njol.skript.lang.Expression<[CtTypeReferenceImpl]org.bukkit.entity.Entity>) ([CtVariableReadImpl]exprs[[CtLiteralImpl]1]));
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.Event e) [CtBlockImpl]{
        [CtLocalVariableImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
        [CtTypeReferenceImpl]org.bukkit.entity.Entity target = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.target.getSingle([CtVariableReadImpl]e);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.LivingEntity entity : [CtInvocationImpl][CtFieldReadImpl]entities.getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]entity.attack([CtVariableReadImpl]target);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString([CtParameterImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtTypeReferenceImpl]org.bukkit.event.Event e, [CtParameterImpl][CtTypeReferenceImpl]boolean debug) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"make " + [CtInvocationImpl][CtFieldReadImpl]entities.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug)) + [CtLiteralImpl]" attack ") + [CtInvocationImpl][CtFieldReadImpl]target.toString([CtVariableReadImpl]e, [CtVariableReadImpl]debug);
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * This file is part of Skript.
 *
 * Skript is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Skript is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
[CtPackageDeclarationImpl]package ch.njol.skript.expressions;
[CtUnresolvedImport]import ch.njol.skript.doc.Name;
[CtUnresolvedImport]import ch.njol.skript.expressions.base.SimplePropertyExpression;
[CtUnresolvedImport]import org.bukkit.entity.Projectile;
[CtUnresolvedImport]import org.bukkit.event.Event;
[CtUnresolvedImport]import org.bukkit.entity.Arrow;
[CtUnresolvedImport]import ch.njol.skript.doc.Since;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.Nullable;
[CtUnresolvedImport]import ch.njol.skript.doc.Examples;
[CtUnresolvedImport]import ch.njol.util.coll.CollectionUtils;
[CtUnresolvedImport]import ch.njol.skript.classes.Changer.ChangeMode;
[CtUnresolvedImport]import ch.njol.skript.Skript;
[CtUnresolvedImport]import ch.njol.skript.doc.Description;
[CtUnresolvedImport]import org.bukkit.entity.AbstractArrow;
[CtClassImpl][CtAnnotationImpl]@ch.njol.skript.doc.Name([CtLiteralImpl]"Arrow Pierce Level")
[CtAnnotationImpl]@ch.njol.skript.doc.Description([CtLiteralImpl]"An arrow's pierce level.")
[CtAnnotationImpl]@ch.njol.skript.doc.Examples([CtNewArrayImpl]{ [CtLiteralImpl]"on shoot:", [CtLiteralImpl]"\tevent-projectile is an arrow", [CtLiteralImpl]"\tset arrow pierce level of event-projectile to 5" })
[CtAnnotationImpl]@ch.njol.skript.doc.Since([CtLiteralImpl]"INSERT VERSION")
public class ExprArrowPierceLevel extends [CtTypeReferenceImpl]ch.njol.skript.expressions.base.SimplePropertyExpression<[CtTypeReferenceImpl]org.bukkit.entity.Projectile, [CtTypeReferenceImpl]java.lang.Number> {
    [CtFieldImpl]static final [CtTypeReferenceImpl]boolean abstractArrowExists = [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.classExists([CtLiteralImpl]"org.bukkit.entity.AbstractArrow");

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.abstractArrowExists || [CtInvocationImpl][CtTypeAccessImpl]ch.njol.skript.Skript.methodExists([CtFieldReadImpl]org.bukkit.entity.Arrow.class, [CtLiteralImpl]"getPierceLevel"))[CtBlockImpl]
            [CtInvocationImpl]register([CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.class, [CtFieldReadImpl]java.lang.Number.class, [CtLiteralImpl]"[the] arrow pierce level", [CtLiteralImpl]"projectiles");

    }

    [CtMethodImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Number convert([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile arrow) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.abstractArrowExists)[CtBlockImpl]
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]arrow instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) (arrow)).getPierceLevel() : [CtLiteralImpl]null;

        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]arrow instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.Arrow) (arrow)).getPierceLevel() : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?>[] acceptChange([CtParameterImpl][CtTypeReferenceImpl]ch.njol.skript.classes.Changer.ChangeMode mode) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mode == [CtFieldReadImpl]ch.njol.skript.classes.Changer.ChangeMode.DELETE) || [CtBinaryOperatorImpl]([CtVariableReadImpl]mode == [CtFieldReadImpl]ch.njol.skript.classes.Changer.ChangeMode.REMOVE_ALL) ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]ch.njol.util.coll.CollectionUtils.array([CtFieldReadImpl]java.lang.Number.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void change([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.Event e, [CtParameterImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Object[] delta, [CtParameterImpl][CtTypeReferenceImpl]ch.njol.skript.classes.Changer.ChangeMode mode) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int strength = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]delta != [CtLiteralImpl]null) ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Number) ([CtVariableReadImpl]delta[[CtLiteralImpl]0])).intValue(), [CtLiteralImpl]0) : [CtLiteralImpl]0;
        [CtSwitchImpl]switch ([CtVariableReadImpl]mode) {
            [CtCaseImpl]case [CtFieldReadImpl]REMOVE :
                [CtIfImpl]if ([CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.abstractArrowExists)[CtBlockImpl]
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile entity : [CtInvocationImpl][CtInvocationImpl]getExpr().getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow abstractArrow = [CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) (entity));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int dmg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]abstractArrow.getPierceLevel() - [CtVariableReadImpl]strength);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dmg < [CtLiteralImpl]0)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]dmg = [CtLiteralImpl]0;

                            [CtInvocationImpl][CtVariableReadImpl]abstractArrow.setPierceLevel([CtVariableReadImpl]dmg);
                        }
                    }
                else[CtBlockImpl]
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile entity : [CtInvocationImpl][CtInvocationImpl]getExpr().getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow arrow = [CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.Arrow) (entity));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int dmg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arrow.getPierceLevel() - [CtVariableReadImpl]strength);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dmg < [CtLiteralImpl]0)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]dmg = [CtLiteralImpl]0;

                            [CtInvocationImpl][CtVariableReadImpl]arrow.setPierceLevel([CtVariableReadImpl]dmg);
                        }
                    }

                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]ADD :
                [CtIfImpl]if ([CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.abstractArrowExists)[CtBlockImpl]
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile entity : [CtInvocationImpl][CtInvocationImpl]getExpr().getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow abstractArrow = [CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) (entity));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int dmg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]abstractArrow.getPierceLevel() + [CtVariableReadImpl]strength);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dmg < [CtLiteralImpl]0)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]dmg = [CtLiteralImpl]0;

                            [CtInvocationImpl][CtVariableReadImpl]abstractArrow.setPierceLevel([CtVariableReadImpl]dmg);
                        }
                    }
                else[CtBlockImpl]
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile entity : [CtInvocationImpl][CtInvocationImpl]getExpr().getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow arrow = [CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.Arrow) (entity));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int dmg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.round([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]arrow.getPierceLevel() + [CtVariableReadImpl]strength);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dmg < [CtLiteralImpl]0)[CtBlockImpl]
                                [CtAssignmentImpl][CtVariableWriteImpl]dmg = [CtLiteralImpl]0;

                            [CtInvocationImpl][CtVariableReadImpl]arrow.setPierceLevel([CtVariableReadImpl]dmg);
                        }
                    }

                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]RESET :
            [CtCaseImpl]case [CtFieldReadImpl]SET :
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Projectile entity : [CtInvocationImpl][CtInvocationImpl]getExpr().getArray([CtVariableReadImpl]e)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtFieldReadImpl]ch.njol.skript.expressions.ExprArrowPierceLevel.abstractArrowExists) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow)[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.AbstractArrow) (entity)).setPierceLevel([CtVariableReadImpl]strength);

                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.entity.Arrow) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.entity.Arrow) (entity)).setPierceLevel([CtVariableReadImpl]strength);
                    }
                }
                [CtBreakImpl]break;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Number> getReturnType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]java.lang.Number.class;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getPropertyName() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"arrow pierce level";
    }
}
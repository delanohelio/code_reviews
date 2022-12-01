[CompilationUnitImpl][CtCommentImpl]/* This file is part of Sponge, licensed under the MIT License (MIT).

Copyright (c) SpongePowered <https://www.spongepowered.org>
Copyright (c) contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
[CtPackageDeclarationImpl]package org.spongepowered.common.mixin.core.entity.monster;
[CtUnresolvedImport]import net.minecraft.entity.Entity;
[CtUnresolvedImport]import net.minecraft.entity.monster.CreeperEntity;
[CtUnresolvedImport]import org.spongepowered.api.entity.living.monster.Creeper;
[CtUnresolvedImport]import org.spongepowered.api.world.Location;
[CtUnresolvedImport]import net.minecraft.entity.player.PlayerEntity;
[CtUnresolvedImport]import org.spongepowered.common.util.Constants;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import net.minecraft.entity.LivingEntity;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.injection.Inject;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.injection.Redirect;
[CtUnresolvedImport]import org.spongepowered.common.bridge.entity.GrieferBridge;
[CtUnresolvedImport]import org.spongepowered.common.event.SpongeCommonEventFactory;
[CtUnresolvedImport]import org.spongepowered.common.bridge.explosives.ExplosiveBridge;
[CtUnresolvedImport]import net.minecraft.item.ItemStack;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
[CtUnresolvedImport]import org.spongepowered.math.vector.Vector3d;
[CtUnresolvedImport]import org.spongepowered.api.world.explosion.Explosion;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.Mixin;
[CtImportImpl]import java.util.function.Consumer;
[CtUnresolvedImport]import org.spongepowered.common.bridge.explosives.FusedExplosiveBridge;
[CtUnresolvedImport]import org.spongepowered.api.world.World;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.Shadow;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.injection.At;
[CtClassImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Mixin([CtFieldReadImpl]net.minecraft.entity.monster.CreeperEntity.class)
public abstract class CreeperEntityMixin extends [CtTypeReferenceImpl]org.spongepowered.common.mixin.core.entity.monster.MonsterEntityMixin implements [CtTypeReferenceImpl]org.spongepowered.common.bridge.explosives.FusedExplosiveBridge , [CtTypeReferenceImpl]org.spongepowered.common.bridge.explosives.ExplosiveBridge {
    [CtFieldImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    private [CtTypeReferenceImpl]int timeSinceIgnited;

    [CtFieldImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    private [CtTypeReferenceImpl]int fuseTime;

    [CtFieldImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    private [CtTypeReferenceImpl]int explosionRadius;

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    public abstract [CtTypeReferenceImpl]void shadow$ignite();

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    public abstract [CtTypeReferenceImpl]int shadow$getCreeperState();

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    public abstract [CtTypeReferenceImpl]void shadow$setCreeperState([CtParameterImpl][CtTypeReferenceImpl]int state);

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.Shadow
    private [CtTypeReferenceImpl]void shadow$explode() [CtBlockImpl]{
    }[CtCommentImpl]// explode


    [CtFieldImpl]private [CtTypeReferenceImpl]int impl$fuseDuration = [CtLiteralImpl]30;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean impl$interactPrimeCancelled;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean impl$stateDirty;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean impl$detonationCancelled;

    [CtMethodImpl][CtCommentImpl]// FusedExplosive Impl
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.Integer> bridge$getExplosionRadius() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtFieldReadImpl][CtThisAccessImpl]this.explosionRadius);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void bridge$setExplosionRadius([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.Integer radius) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.explosionRadius = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]radius == [CtLiteralImpl]null) ? [CtFieldReadImpl]org.spongepowered.api.entity.living.monster.Creeper.DEFAULT_EXPLOSION_RADIUS : [CtVariableReadImpl]radius;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int bridge$getFuseDuration() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.impl$fuseDuration;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void bridge$setFuseDuration([CtParameterImpl]final [CtTypeReferenceImpl]int fuseTicks) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$fuseDuration = [CtVariableReadImpl]fuseTicks;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int bridge$getFuseTicksRemaining() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.fuseTime - [CtFieldReadImpl][CtThisAccessImpl]this.timeSinceIgnited;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void bridge$setFuseTicksRemaining([CtParameterImpl]final [CtTypeReferenceImpl]int fuseTicks) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// Note: The creeper will detonate when timeSinceIgnited >= fuseTime
        [CtCommentImpl]// assuming it is within range of a player. Every tick that the creeper
        [CtCommentImpl]// is not within a range of a player, timeSinceIgnited is decremented
        [CtCommentImpl]// by one until zero.
        [CtFieldWriteImpl][CtThisAccessImpl]this.timeSinceIgnited = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fuseTime = [CtVariableReadImpl]fuseTicks;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Inject(method = [CtLiteralImpl]"setCreeperState(I)V", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At([CtLiteralImpl]"INVOKE"), cancellable = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]void onStateChange([CtParameterImpl]final [CtTypeReferenceImpl]int state, [CtParameterImpl]final [CtTypeReferenceImpl]org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.bridge$setFuseDuration([CtFieldReadImpl][CtThisAccessImpl]this.impl$fuseDuration);
        [CtIfImpl]if ([CtFieldReadImpl][CtFieldReadImpl][CtThisAccessImpl]this.world.isRemote) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtThisAccessImpl](([CtTypeReferenceImpl]org.spongepowered.api.entity.living.monster.Creeper) (this)).isPrimed()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.spongepowered.api.entity.living.monster.Creeper.STATE_PRIMED)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtThisAccessImpl]this.bridge$shouldPrime())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ci.cancel();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtThisAccessImpl](([CtTypeReferenceImpl]org.spongepowered.api.entity.living.monster.Creeper) (this)).isPrimed() && [CtBinaryOperatorImpl]([CtVariableReadImpl]state == [CtFieldReadImpl]org.spongepowered.api.entity.living.monster.Creeper.STATE_IDLE)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtThisAccessImpl]this.bridge$shouldDefuse())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ci.cancel();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtThisAccessImpl]this.shadow$getCreeperState() != [CtVariableReadImpl]state) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$stateDirty = [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Inject(method = [CtLiteralImpl]"setCreeperState(I)V", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At([CtLiteralImpl]"RETURN"))
    private [CtTypeReferenceImpl]void postStateChange([CtParameterImpl]final [CtTypeReferenceImpl]int state, [CtParameterImpl]final [CtTypeReferenceImpl]org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtFieldReadImpl][CtThisAccessImpl]this.world.isRemote) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.impl$stateDirty) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state == [CtFieldReadImpl]org.spongepowered.api.entity.living.monster.Creeper.STATE_PRIMED) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.bridge$postPrime();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state == [CtFieldReadImpl]org.spongepowered.api.entity.living.monster.Creeper.STATE_IDLE) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.bridge$postDefuse();
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$stateDirty = [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Redirect(method = [CtLiteralImpl]"explode", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At(value = [CtLiteralImpl]"INVOKE", target = [CtBinaryOperatorImpl][CtLiteralImpl]"Lnet/minecraft/world/World;createExplosion" + [CtLiteralImpl]"(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/world/Explosion;"))
    [CtAnnotationImpl]@javax.annotation.Nullable
    private [CtTypeReferenceImpl]org.spongepowered.api.world.explosion.Explosion onExplode([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl]final [CtTypeReferenceImpl]net.minecraft.entity.Entity self, [CtParameterImpl]final [CtTypeReferenceImpl]double x, [CtParameterImpl]final [CtTypeReferenceImpl]double y, [CtParameterImpl]final [CtTypeReferenceImpl]double z, [CtParameterImpl]final [CtTypeReferenceImpl]float strength, [CtParameterImpl]final [CtTypeReferenceImpl]boolean smoking) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.spongepowered.common.event.SpongeCommonEventFactory.detonateExplosive([CtThisAccessImpl]this, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.spongepowered.api.world.explosion.Explosion.builder().location([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.spongepowered.api.world.Location<>([CtVariableReadImpl](([CtTypeReferenceImpl]org.spongepowered.api.world.World) (world)), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.spongepowered.math.vector.Vector3d([CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]z))).sourceExplosive([CtThisAccessImpl](([CtTypeReferenceImpl]org.spongepowered.api.entity.living.monster.Creeper) (this))).radius([CtVariableReadImpl]strength).shouldPlaySmoke([CtVariableReadImpl]smoking).shouldBreakBlocks([CtBinaryOperatorImpl][CtVariableReadImpl]smoking && [CtInvocationImpl][CtThisAccessImpl](([CtTypeReferenceImpl]org.spongepowered.common.bridge.entity.GrieferBridge) (this)).bridge$canGrief())).orElseGet([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$detonationCancelled = [CtLiteralImpl]true;
            [CtReturnImpl]return [CtLiteralImpl]null;
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Inject(method = [CtLiteralImpl]"explode", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At([CtLiteralImpl]"RETURN"))
    private [CtTypeReferenceImpl]void postExplode([CtParameterImpl]final [CtTypeReferenceImpl]org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.impl$detonationCancelled) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$detonationCancelled = [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isDead = [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Redirect(method = [CtLiteralImpl]"processInteract", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At(value = [CtLiteralImpl]"INVOKE", target = [CtLiteralImpl]"Lnet/minecraft/entity/monster/CreeperEntity;ignite()V"))
    private [CtTypeReferenceImpl]void onInteractIgnite([CtParameterImpl]final [CtTypeReferenceImpl]net.minecraft.entity.monster.CreeperEntity self) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$interactPrimeCancelled = [CtUnaryOperatorImpl]![CtInvocationImpl][CtThisAccessImpl]this.bridge$shouldPrime();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtThisAccessImpl]this.impl$interactPrimeCancelled) [CtBlockImpl]{
            [CtInvocationImpl][CtThisAccessImpl]this.shadow$ignite();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.Redirect(method = [CtLiteralImpl]"processInteract", at = [CtAnnotationImpl]@org.spongepowered.asm.mixin.injection.At(value = [CtLiteralImpl]"INVOKE", target = [CtLiteralImpl]"Lnet/minecraft/item/ItemStack;damageItem(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private [CtTypeReferenceImpl]void impl$onDamageFlintAndSteel([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack fas, [CtParameterImpl][CtTypeReferenceImpl]int amount, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity player, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]net.minecraft.entity.LivingEntity> onBroken) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtThisAccessImpl]this.impl$interactPrimeCancelled) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]fas.damageItem([CtVariableReadImpl]amount, [CtVariableReadImpl]player, [CtVariableReadImpl]onBroken);
            [CtCommentImpl]// TODO put this in the cause somehow?
            [CtCommentImpl]// this.primeCause = Cause.of(NamedCause.of(NamedCause.IGNITER, player));
            [CtCommentImpl]// this.detonationCause = this.primeCause;
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.impl$interactPrimeCancelled = [CtLiteralImpl]false;
    }
}
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
[CtPackageDeclarationImpl]package org.spongepowered.common.mixin.entitycollisions;
[CtUnresolvedImport]import org.spongepowered.common.SpongeImpl;
[CtUnresolvedImport]import org.spongepowered.common.config.category.EntityCollisionCategory;
[CtUnresolvedImport]import net.minecraft.block.Block;
[CtUnresolvedImport]import org.spongepowered.asm.mixin.Mixin;
[CtUnresolvedImport]import net.minecraft.world.World;
[CtUnresolvedImport]import org.spongepowered.api.block.BlockType;
[CtUnresolvedImport]import org.spongepowered.common.config.SpongeConfig;
[CtUnresolvedImport]import org.spongepowered.common.mixin.plugin.entitycollisions.interfaces.CollisionsCapability;
[CtUnresolvedImport]import org.spongepowered.common.config.type.WorldConfig;
[CtUnresolvedImport]import org.spongepowered.common.config.category.CollisionModCategory;
[CtUnresolvedImport]import org.spongepowered.common.config.type.GlobalConfig;
[CtUnresolvedImport]import org.spongepowered.common.bridge.world.WorldInfoBridge;
[CtClassImpl][CtCommentImpl]// TODO this is currently inactive - No phase states for blocks override
[CtCommentImpl]// isCollision, and CollideBlockEvent is handled with entities iterating
[CtCommentImpl]// through blocks, not blocks iterating through entities
[CtAnnotationImpl]@org.spongepowered.asm.mixin.Mixin([CtFieldReadImpl]net.minecraft.block.Block.class)
public abstract class BlockMixin_Collisions implements [CtTypeReferenceImpl]org.spongepowered.common.mixin.plugin.entitycollisions.interfaces.CollisionsCapability {
    [CtFieldImpl]private [CtTypeReferenceImpl]int collision$maxCollisions = [CtLiteralImpl]8;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String collision$modId;

    [CtFieldImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    private [CtTypeReferenceImpl]java.lang.String collision$modBlockName;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean collision$refreshCache = [CtLiteralImpl]true;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int collision$getMaxCollisions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.collision$maxCollisions;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collision$setMaxCollisions([CtParameterImpl][CtTypeReferenceImpl]int max) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.collision$maxCollisions = [CtVariableReadImpl]max;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collision$setModDataName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.collision$modBlockName = [CtVariableReadImpl]name;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String collision$getModDataId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.collision$modId;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collision$setModDataId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.collision$modId = [CtVariableReadImpl]id;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collision$requiresCollisionsCacheRefresh([CtParameterImpl][CtTypeReferenceImpl]boolean flag) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.collision$refreshCache = [CtVariableReadImpl]flag;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean collision$requiresCollisionsCacheRefresh() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.collision$refreshCache;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"Duplicates")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void collision$initializeCollisionState([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.spongepowered.common.config.SpongeConfig<[CtTypeReferenceImpl]org.spongepowered.common.config.type.WorldConfig> worldConfigAdapter = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.spongepowered.common.bridge.world.WorldInfoBridge) ([CtVariableReadImpl]world.getWorldInfo())).bridge$getConfigAdapter();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.spongepowered.common.config.SpongeConfig<[CtTypeReferenceImpl]org.spongepowered.common.config.type.GlobalConfig> globalConfigAdapter = [CtInvocationImpl][CtTypeAccessImpl]org.spongepowered.common.SpongeImpl.getGlobalConfigAdapter();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.spongepowered.common.config.category.EntityCollisionCategory worldCollCat = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldConfigAdapter.getConfig().getEntityCollisionCategory();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.spongepowered.common.config.category.EntityCollisionCategory globalCollCat = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]globalConfigAdapter.getConfig().getEntityCollisionCategory();
        [CtInvocationImpl][CtThisAccessImpl]this.collision$setMaxCollisions([CtInvocationImpl][CtVariableReadImpl]worldCollCat.getMaxEntitiesWithinAABB());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean requiresSave = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] ids = [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl](([CtTypeReferenceImpl]org.spongepowered.api.block.BlockType) (this)).getId().split([CtLiteralImpl]":");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String modId = [CtArrayReadImpl][CtVariableReadImpl]ids[[CtLiteralImpl]0];
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtArrayReadImpl][CtVariableReadImpl]ids[[CtLiteralImpl]1];
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.spongepowered.common.config.category.CollisionModCategory worldCollMod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCollCat.getModList().get([CtVariableReadImpl]modId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.spongepowered.common.config.category.CollisionModCategory globalCollMod = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]globalCollCat.getModList().get([CtVariableReadImpl]modId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]worldCollMod == [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]worldCollCat.autoPopulateData()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]globalCollMod = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.spongepowered.common.config.category.CollisionModCategory([CtVariableReadImpl]modId);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]globalCollCat.getModList().put([CtVariableReadImpl]modId, [CtVariableReadImpl]globalCollMod);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]globalCollMod.getBlockList().put([CtVariableReadImpl]name, [CtInvocationImpl][CtThisAccessImpl]this.collision$getMaxCollisions());
            [CtInvocationImpl][CtVariableReadImpl]globalConfigAdapter.save();
            [CtReturnImpl]return;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]worldCollMod != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]worldCollMod.isEnabled()) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.collision$setMaxCollisions([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// check mod overrides
            [CtTypeReferenceImpl]java.lang.Integer modCollisionMax = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCollMod.getDefaultMaxCollisions().get([CtLiteralImpl]"blocks");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]modCollisionMax != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.collision$setMaxCollisions([CtVariableReadImpl]modCollisionMax);
            }
            [CtLocalVariableImpl][CtCommentImpl]// entity overrides
            [CtTypeReferenceImpl]java.lang.Integer blockMaxCollision = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCollMod.getBlockList().get([CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]blockMaxCollision == [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]worldCollCat.autoPopulateData()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]globalCollMod.getBlockList().put([CtVariableReadImpl]name, [CtInvocationImpl][CtThisAccessImpl]this.collision$getMaxCollisions());
                [CtAssignmentImpl][CtVariableWriteImpl]requiresSave = [CtLiteralImpl]true;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]blockMaxCollision != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.collision$setMaxCollisions([CtVariableReadImpl]blockMaxCollision);
            }
        }
        [CtIfImpl][CtCommentImpl]// don't bother saving for negative values
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtThisAccessImpl]this.collision$getMaxCollisions() <= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtVariableReadImpl]requiresSave) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]globalConfigAdapter.save();
        }
    }
}
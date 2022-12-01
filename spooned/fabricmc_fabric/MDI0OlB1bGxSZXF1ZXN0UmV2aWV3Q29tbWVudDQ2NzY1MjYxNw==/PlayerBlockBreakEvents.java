[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2016, 2017, 2018, 2019 FabricMC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package net.fabricmc.fabric.api.event.player;
[CtUnresolvedImport]import net.fabricmc.fabric.api.event.Event;
[CtUnresolvedImport]import net.minecraft.block.entity.BlockEntity;
[CtUnresolvedImport]import net.minecraft.entity.player.PlayerEntity;
[CtUnresolvedImport]import net.minecraft.world.World;
[CtUnresolvedImport]import net.fabricmc.fabric.api.event.EventFactory;
[CtUnresolvedImport]import net.minecraft.util.math.BlockPos;
[CtUnresolvedImport]import net.minecraft.block.BlockState;
[CtClassImpl]public final class PlayerBlockBreakEvents {
    [CtConstructorImpl]private PlayerBlockBreakEvents() [CtBlockImpl]{
    }

    [CtFieldImpl][CtJavaDocImpl]/**
     * Callback before a block is broken.
     * Only called on the server, however updates are synced with the client.
     *
     * <p>If any listener cancels a block breaking action, that block breaking
     * action is cancelled and {@link CANCELED} event is fired. Otherwise, the
     * {@link AFTER} event is fired.</p>
     */
    public static final [CtTypeReferenceImpl]net.fabricmc.fabric.api.event.Event<[CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Before> BEFORE = [CtInvocationImpl][CtTypeAccessImpl]net.fabricmc.fabric.api.event.EventFactory.createArrayBacked([CtFieldReadImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Before.class, [CtLambdaImpl]([CtParameterImpl] listeners) -> [CtLambdaImpl]([CtParameterImpl] world,[CtParameterImpl] player,[CtParameterImpl] pos,[CtParameterImpl] state,[CtParameterImpl] entity) -> [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.Before event : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtVariableReadImpl]event.beforeBlockBreak([CtVariableReadImpl]world, [CtVariableReadImpl]player, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]entity);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]result) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    });

    [CtFieldImpl][CtJavaDocImpl]/**
     * Callback after a block is broken.
     *
     * <p>Called on the Server only.</p>
     */
    public static final [CtTypeReferenceImpl]net.fabricmc.fabric.api.event.Event<[CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.After> AFTER = [CtInvocationImpl][CtTypeAccessImpl]net.fabricmc.fabric.api.event.EventFactory.createArrayBacked([CtFieldReadImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.After.class, [CtLambdaImpl]([CtParameterImpl] listeners) -> [CtLambdaImpl]([CtParameterImpl] world,[CtParameterImpl] player,[CtParameterImpl] pos,[CtParameterImpl] state,[CtParameterImpl] entity) -> [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.After event : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.afterBlockBreak([CtVariableReadImpl]world, [CtVariableReadImpl]player, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]entity);
        }
    });

    [CtFieldImpl][CtJavaDocImpl]/**
     * Callback when a block break has been canceled.
     *
     * <p>Called on the logical server only. May be used to send packets to revert client-side block changes.</p>
     */
    public static final [CtTypeReferenceImpl]net.fabricmc.fabric.api.event.Event<[CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Canceled> CANCELED = [CtInvocationImpl][CtTypeAccessImpl]net.fabricmc.fabric.api.event.EventFactory.createArrayBacked([CtFieldReadImpl]net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Canceled.class, [CtLambdaImpl]([CtParameterImpl] listeners) -> [CtLambdaImpl]([CtParameterImpl] world,[CtParameterImpl] player,[CtParameterImpl] pos,[CtParameterImpl] state,[CtParameterImpl] entity) -> [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.fabricmc.fabric.api.event.player.Canceled event : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.onBlockBreakCancel([CtVariableReadImpl]world, [CtVariableReadImpl]player, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]entity);
        }
    });

    [CtInterfaceImpl][CtAnnotationImpl]@java.lang.FunctionalInterface
    public interface Before {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Called before a block is broken and allows cancelling the block breaking.
         *
         * <p>Implementations should not modify the world or assume the block break has completed or failed.</p>
         *
         * @param world
         * 		the world in which the block is broken
         * @param player
         * 		the player breaking the block
         * @param pos
         * 		the position at which the block is broken
         * @param state
         * 		the block state <strong>before</strong> the block is broken
         * @param blockEntity
         * 		the block entity <strong>before</strong> the block is broken, can be {@code null}
         * @return {@code false} to cancel block breaking action, or {@code true} to pass to next listener
         */
        [CtTypeReferenceImpl]boolean beforeBlockBreak([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtCommentImpl]/* Nullable */
        [CtTypeReferenceImpl]net.minecraft.block.entity.BlockEntity blockEntity);
    }

    [CtInterfaceImpl][CtAnnotationImpl]@java.lang.FunctionalInterface
    public interface After {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Called after a block is successfully broken.
         *
         * @param world
         * 		the world where the block was broken
         * @param player
         * 		the player who broke the block
         * @param pos
         * 		the position where the block was broken
         * @param state
         * 		the block state <strong>before</strong> the block was broken
         * @param blockEntity
         * 		the block entity of the broken block, can be {@code null}
         */
        [CtTypeReferenceImpl]void afterBlockBreak([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtCommentImpl]/* Nullable */
        [CtTypeReferenceImpl]net.minecraft.block.entity.BlockEntity blockEntity);
    }

    [CtInterfaceImpl][CtAnnotationImpl]@java.lang.FunctionalInterface
    public interface Canceled {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Called when a block break has been canceled.
         *
         * @param world
         * 		the world where the block was going to be broken
         * @param player
         * 		the player who was going to break the block
         * @param pos
         * 		the position where the block was going to be broken
         * @param state
         * 		the block state of the block that was going to be broken
         * @param blockEntity
         * 		entity the block entity of the block that was going to be broken, can be {@code null}
         */
        [CtTypeReferenceImpl]void onBlockBreakCancel([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtCommentImpl]/* Nullable */
        [CtTypeReferenceImpl]net.minecraft.block.entity.BlockEntity blockEntity);
    }
}
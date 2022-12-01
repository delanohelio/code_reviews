[CompilationUnitImpl][CtCommentImpl]/* Minecraft Forge, Patchwork Project
Copyright (c) 2016-2020, 2019-2020

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation version 2.1
of the License.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
[CtPackageDeclarationImpl]package net.minecraftforge.event.world;
[CtUnresolvedImport]import net.minecraft.world.WorldSaveHandler;
[CtUnresolvedImport]import net.minecraft.client.render.WorldRenderer;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import net.minecraft.server.world.ServerWorld;
[CtUnresolvedImport]import net.minecraft.world.IWorld;
[CtUnresolvedImport]import net.minecraft.util.math.BlockPos;
[CtUnresolvedImport]import net.minecraft.world.level.LevelInfo;
[CtUnresolvedImport]import net.minecraft.world.level.LevelProperties;
[CtUnresolvedImport]import net.minecraftforge.common.MinecraftForge;
[CtUnresolvedImport]import net.minecraft.server.WorldGenerationProgressListener;
[CtUnresolvedImport]import net.minecraft.server.MinecraftServer;
[CtImportImpl]import java.util.Random;
[CtUnresolvedImport]import net.minecraft.world.SpawnHelper;
[CtUnresolvedImport]import net.minecraft.world.dimension.DimensionType;
[CtUnresolvedImport]import net.minecraft.world.biome.Biome.SpawnEntry;
[CtUnresolvedImport]import net.minecraft.entity.EntityCategory;
[CtUnresolvedImport]import net.minecraft.util.profiler.Profiler;
[CtUnresolvedImport]import net.minecraftforge.eventbus.api.Event;
[CtUnresolvedImport]import net.minecraft.client.MinecraftClient;
[CtUnresolvedImport]import net.minecraft.client.network.ClientPlayNetworkHandler;
[CtUnresolvedImport]import net.minecraft.world.gen.chunk.ChunkGenerator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import net.minecraft.util.ProgressListener;
[CtUnresolvedImport]import net.minecraft.client.world.ClientWorld;
[CtClassImpl][CtJavaDocImpl]/**
 * WorldEvent is fired when an event involving the world occurs.
 *
 * <p>If a method utilizes this {@link Event} as its parameter, the method will
 * receive every child event of this class.</p>
 *
 * <p>{@link #world} contains the World this event is occurring in.</p>
 *
 * <p>All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.</p>
 */
public class WorldEvent extends [CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event {
    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.world.IWorld world;

    [CtConstructorImpl][CtCommentImpl]// For EventBus
    public WorldEvent() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.world = [CtLiteralImpl]null;
    }

    [CtConstructorImpl]public WorldEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.world = [CtVariableReadImpl]world;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]net.minecraft.world.IWorld getWorld() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]world;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * WorldEvent.Load is fired when Minecraft loads a world.
     *
     * <p>This event is fired when a world is loaded in
     * {@link ClientWorld#ClientWorld(ClientPlayNetworkHandler, LevelInfo, DimensionType, int, Profiler, WorldRenderer)},
     * {@link MinecraftServer#createWorlds(WorldSaveHandler, LevelProperties, LevelInfo, WorldGenerationProgressListener)},
     * TODO: {@link DimensionManager#initDimension(int)}</p>
     *
     * <p>This event is not Cancelable.</p>
     *
     * <p>This event does not have a result.</p>
     *
     * <p>This event is fired on the {@link MinecraftForge#EVENT_BUS}.</p>
     */
    public static class Load extends [CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent {
        [CtConstructorImpl]public Load([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]world);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * WorldEvent.Unload is fired when Minecraft unloads a world.
     *
     * <p>This event is fired when a world is unloaded in
     * {@link MinecraftClient#joinWorld(ClientWorld)},
     * {@link MinecraftClient#disconnect()},
     * {@link MinecraftServer#shutdown()},
     * TODO: {@link DimensionManager#unloadWorlds()}</p>
     *
     * <p>This event is not Cancelable.</p>
     *
     * <p>This event does not have a result.</p>
     *
     * <p>This event is fired on the {@link MinecraftForge#EVENT_BUS}.</p>
     */
    public static class Unload extends [CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent {
        [CtConstructorImpl]public Unload([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]world);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * WorldEvent.Save is fired when Minecraft saves a world.
     *
     * <p>This event is fired when a world is saved in
     * {@link ServerWorld#save(ProgressListener, boolean, boolean)}.</p>
     *
     * <p>This event is not Cancelable.</p>
     *
     * <p>This event does not have a result.</p>
     *
     * <p>This event is fired on the {@link MinecraftForge#EVENT_BUS}.</p>
     */
    public static class Save extends [CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent {
        [CtConstructorImpl]public Save([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]world);
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Called by ServerWorld to gather a list of all possible entities that can spawn at the specified location.
     * If an entry is added to the list, it needs to be a globally unique instance.
     * The event is called in {@link SpawnHelper#method_8664(ChunkGenerator, EntityCategory, Random, BlockPos)} as well as
     * {@link SpawnHelper#method_8659(ChunkGenerator, EntityCategory, SpawnEntry, BlockPos)}
     * where the latter checks for identity, meaning both events must add the same instance.
     * Canceling the event will result in a empty list, meaning no entity will be spawned.
     */
    public static class PotentialSpawns extends [CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent {
        [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.entity.EntityCategory type;

        [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnEntry> list;

        [CtConstructorImpl]public PotentialSpawns([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.EntityCategory type, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnEntry> oldList) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]world);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pos = [CtVariableReadImpl]pos;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldList != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnEntry>([CtVariableReadImpl]oldList);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnEntry>();
            }
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]net.minecraft.entity.EntityCategory getType() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]type;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]net.minecraft.util.math.BlockPos getPos() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]pos;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnEntry> getList() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]list;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isCancelable() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Called by ServerWorld when it attempts to create a spawnpoint for a dimension.
     * Canceling the event will prevent the vanilla code from running.
     */
    public static class CreateSpawnPosition extends [CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent {
        [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.world.level.LevelInfo settings;

        [CtConstructorImpl]public CreateSpawnPosition([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.level.LevelInfo settings) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]world);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.settings = [CtVariableReadImpl]settings;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]net.minecraft.world.level.LevelInfo getSettings() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]settings;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isCancelable() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }
}
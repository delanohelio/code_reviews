[CompilationUnitImpl][CtCommentImpl]/* Minecraft Forge
Copyright (c) 2016-2020.

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
[CtPackageDeclarationImpl]package net.minecraftforge.event;
[CtUnresolvedImport]import com.mojang.brigadier.CommandDispatcher;
[CtUnresolvedImport]import net.minecraft.world.GameRules;
[CtUnresolvedImport]import net.minecraftforge.event.entity.ProjectileImpactEvent;
[CtUnresolvedImport]import net.minecraft.world.Explosion;
[CtUnresolvedImport]import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
[CtUnresolvedImport]import net.minecraft.util.Direction;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.UseHoeEvent;
[CtUnresolvedImport]import net.minecraftforge.api.distmarker.Dist;
[CtUnresolvedImport]import net.minecraft.entity.player.PlayerEntity.SleepResult;
[CtUnresolvedImport]import net.minecraft.world.WorldSettings;
[CtUnresolvedImport]import net.minecraftforge.common.capabilities.CapabilityDispatcher;
[CtUnresolvedImport]import net.minecraft.entity.SpawnReason;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.FillBucketEvent;
[CtUnresolvedImport]import net.minecraft.util.NonNullList;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.BonemealEvent;
[CtUnresolvedImport]import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
[CtUnresolvedImport]import net.minecraft.world.storage.IServerWorldInfo;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
[CtUnresolvedImport]import net.minecraftforge.client.event.RenderBlockOverlayEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingHealEvent;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent;
[CtUnresolvedImport]import net.minecraft.entity.LivingEntity;
[CtUnresolvedImport]import net.minecraft.util.ActionResult;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.ItemTooltipEvent;
[CtUnresolvedImport]import net.minecraftforge.event.world.PistonEvent;
[CtUnresolvedImport]import net.minecraft.util.ActionResultType;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import net.minecraftforge.api.distmarker.OnlyIn;
[CtUnresolvedImport]import net.minecraftforge.common.capabilities.ICapabilityProvider;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
[CtUnresolvedImport]import net.minecraft.util.math.AxisAlignedBB;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.ArrowLooseEvent;
[CtUnresolvedImport]import net.minecraft.client.util.ITooltipFlag;
[CtUnresolvedImport]import net.minecraftforge.event.entity.EntityMobGriefingEvent;
[CtUnresolvedImport]import net.minecraft.item.ItemUseContext;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
[CtUnresolvedImport]import net.minecraft.item.ItemStack;
[CtUnresolvedImport]import net.minecraftforge.client.event.ClientChatEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
[CtUnresolvedImport]import net.minecraft.entity.passive.AnimalEntity;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import net.minecraftforge.event.world.ExplosionEvent;
[CtUnresolvedImport]import net.minecraft.entity.projectile.DamagingProjectileEntity;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
[CtUnresolvedImport]import net.minecraft.world.World;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
[CtUnresolvedImport]import net.minecraft.util.math.BlockPos;
[CtUnresolvedImport]import net.minecraft.block.Blocks;
[CtUnresolvedImport]import net.minecraftforge.event.world.SaplingGrowTreeEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.ArrowNockEvent;
[CtUnresolvedImport]import net.minecraft.command.CommandSource;
[CtUnresolvedImport]import net.minecraftforge.common.MinecraftForge;
[CtUnresolvedImport]import net.minecraftforge.eventbus.api.Event.Result;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.AnimalTameEvent;
[CtUnresolvedImport]import net.minecraft.util.ResourceLocation;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn;
[CtUnresolvedImport]import net.minecraftforge.event.entity.EntityEvent;
[CtUnresolvedImport]import net.minecraft.command.Commands;
[CtUnresolvedImport]import net.minecraft.util.SoundEvent;
[CtUnresolvedImport]import net.minecraft.world.server.ServerWorld;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.item.ItemExpireEvent;
[CtUnresolvedImport]import net.minecraft.entity.monster.ZombieEntity;
[CtUnresolvedImport]import net.minecraft.entity.EntityClassification;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
[CtUnresolvedImport]import net.minecraft.util.RegistryKey;
[CtUnresolvedImport]import net.minecraft.resources.DataPackRegistries;
[CtUnresolvedImport]import net.minecraft.world.spawner.AbstractSpawner;
[CtUnresolvedImport]import net.minecraftforge.eventbus.api.Event;
[CtUnresolvedImport]import net.minecraft.block.BlockState;
[CtUnresolvedImport]import net.minecraft.util.SoundCategory;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
[CtUnresolvedImport]import net.minecraft.entity.projectile.AbstractArrowEntity;
[CtUnresolvedImport]import net.minecraft.entity.player.PlayerEntity;
[CtUnresolvedImport]import net.minecraftforge.event.world.WorldEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
[CtUnresolvedImport]import net.minecraft.util.math.ChunkPos;
[CtUnresolvedImport]import net.minecraft.world.storage.PlayerData;
[CtUnresolvedImport]import net.minecraft.block.NetherPortalBlock;
[CtUnresolvedImport]import net.minecraftforge.event.brewing.PotionBrewEvent;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
[CtUnresolvedImport]import net.minecraftforge.common.ToolType;
[CtUnresolvedImport]import net.minecraft.resources.IFutureReloadListener;
[CtUnresolvedImport]import net.minecraftforge.client.event.ClientChatReceivedEvent;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent;
[CtUnresolvedImport]import net.minecraft.entity.player.ServerPlayerEntity;
[CtUnresolvedImport]import net.minecraft.entity.projectile.FireworkRocketEntity;
[CtUnresolvedImport]import net.minecraft.entity.effect.LightningBoltEntity;
[CtUnresolvedImport]import net.minecraft.loot.LootTable;
[CtUnresolvedImport]import net.minecraft.world.IWorld;
[CtUnresolvedImport]import net.minecraft.loot.LootTableManager;
[CtUnresolvedImport]import net.minecraftforge.event.entity.EntityMountEvent;
[CtUnresolvedImport]import net.minecraft.util.text.ITextComponent;
[CtUnresolvedImport]import net.minecraft.util.math.RayTraceResult;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
[CtUnresolvedImport]import net.minecraft.util.Hand;
[CtUnresolvedImport]import net.minecraft.util.text.ChatType;
[CtUnresolvedImport]import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
[CtUnresolvedImport]import net.minecraft.entity.MobEntity;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import com.mojang.blaze3d.matrix.MatrixStack;
[CtUnresolvedImport]import net.minecraft.entity.projectile.ThrowableEntity;
[CtUnresolvedImport]import net.minecraft.entity.Entity;
[CtUnresolvedImport]import net.minecraftforge.common.util.BlockSnapshot;
[CtUnresolvedImport]import net.minecraftforge.event.world.BlockEvent.EntityMultiPlaceEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
[CtUnresolvedImport]import net.minecraftforge.event.entity.living.LivingSpawnEvent;
[CtUnresolvedImport]import net.minecraftforge.event.world.SleepFinishedTimeEvent;
[CtUnresolvedImport]import net.minecraft.entity.item.ItemEntity;
[CtUnresolvedImport]import net.minecraft.world.biome.Biome;
[CtUnresolvedImport]import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
[CtUnresolvedImport]import net.minecraftforge.event.world.ChunkWatchEvent;
[CtClassImpl]public class ForgeEventFactory {
    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onMultiBlockPlace([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraftforge.common.util.BlockSnapshot> blockSnapshots, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Direction direction) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.common.util.BlockSnapshot snap = [CtInvocationImpl][CtVariableReadImpl]blockSnapshots.get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState placedAgainst = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]snap.getWorld().getBlockState([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]snap.getPos().offset([CtInvocationImpl][CtVariableReadImpl]direction.getOpposite()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.EntityMultiPlaceEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.EntityMultiPlaceEvent([CtVariableReadImpl]blockSnapshots, [CtVariableReadImpl]placedAgainst, [CtVariableReadImpl]entity);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onBlockPlace([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraftforge.common.util.BlockSnapshot blockSnapshot, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.util.Direction direction) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState placedAgainst = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blockSnapshot.getWorld().getBlockState([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blockSnapshot.getPos().offset([CtInvocationImpl][CtVariableReadImpl]direction.getOpposite()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent([CtVariableReadImpl]blockSnapshot, [CtVariableReadImpl]placedAgainst, [CtVariableReadImpl]entity);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent onNeighborNotify([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtTypeReferenceImpl]java.util.EnumSet<[CtTypeReferenceImpl]net.minecraft.util.Direction> notifiedSides, [CtParameterImpl][CtTypeReferenceImpl]boolean forceRedstoneUpdate) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]notifiedSides, [CtVariableReadImpl]forceRedstoneUpdate);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtVariableReadImpl]event;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean doPlayerHarvestCheck([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtTypeReferenceImpl]boolean success) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck([CtVariableReadImpl]player, [CtVariableReadImpl]state, [CtVariableReadImpl]success);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.canHarvest();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]float getBreakSpeed([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtTypeReferenceImpl]float original, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed([CtVariableReadImpl]player, [CtVariableReadImpl]state, [CtVariableReadImpl]original, [CtVariableReadImpl]pos);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtUnaryOperatorImpl]-[CtLiteralImpl]1 : [CtInvocationImpl][CtVariableReadImpl]event.getNewSpeed();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPlayerDestroyItem([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.item.ItemStack stack, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraft.util.Hand hand) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerDestroyItemEvent([CtVariableReadImpl]player, [CtVariableReadImpl]stack, [CtVariableReadImpl]hand));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result canEntitySpawn([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.MobEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]double x, [CtParameterImpl][CtTypeReferenceImpl]double y, [CtParameterImpl][CtTypeReferenceImpl]double z, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.spawner.AbstractSpawner spawner, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.SpawnReason spawnReason) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT;

        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn([CtVariableReadImpl]entity, [CtVariableReadImpl]world, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]z, [CtVariableReadImpl]spawner, [CtVariableReadImpl]spawnReason);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getResult();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canEntitySpawnSpawner([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.MobEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]float x, [CtParameterImpl][CtTypeReferenceImpl]float y, [CtParameterImpl][CtTypeReferenceImpl]float z, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.spawner.AbstractSpawner spawner) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result result = [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.canEntitySpawn([CtVariableReadImpl]entity, [CtVariableReadImpl]world, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]z, [CtVariableReadImpl]spawner, [CtTypeAccessImpl]SpawnReason.SPAWNER);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT)[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entity.canSpawn([CtVariableReadImpl]world, [CtTypeAccessImpl]SpawnReason.SPAWNER) && [CtInvocationImpl][CtVariableReadImpl]entity.isNotColliding([CtVariableReadImpl]world);
        else[CtBlockImpl][CtCommentImpl]// vanilla logic (inverted)

            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW;

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean doSpecialSpawn([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.MobEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]float x, [CtParameterImpl][CtTypeReferenceImpl]float y, [CtParameterImpl][CtTypeReferenceImpl]float z, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.spawner.AbstractSpawner spawner, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.SpawnReason spawnReason) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn([CtVariableReadImpl]entity, [CtVariableReadImpl]world, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]z, [CtVariableReadImpl]spawner, [CtVariableReadImpl]spawnReason));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result canEntityDespawn([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.MobEntity entity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn([CtVariableReadImpl]entity);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getResult();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int getItemBurnTime([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.item.ItemStack itemStack, [CtParameterImpl][CtTypeReferenceImpl]int burnTime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent([CtVariableReadImpl]itemStack, [CtVariableReadImpl]burnTime);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getBurnTime();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int getExperienceDrop([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity attackingPlayer, [CtParameterImpl][CtTypeReferenceImpl]int originalExperience) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingExperienceDropEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingExperienceDropEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]attackingPlayer, [CtVariableReadImpl]originalExperience);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getDroppedExperience();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnListEntry> getPotentialSpawns([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.EntityClassification type, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraft.world.biome.Biome.SpawnListEntry> oldList) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent.PotentialSpawns event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent.PotentialSpawns([CtVariableReadImpl]world, [CtVariableReadImpl]type, [CtVariableReadImpl]pos, [CtVariableReadImpl]oldList);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getList();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int getMaxSpawnPackSize([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.MobEntity entity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingPackSizeEvent maxCanSpawnEvent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingPackSizeEvent([CtVariableReadImpl]entity);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]maxCanSpawnEvent);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]maxCanSpawnEvent.getResult() == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW ? [CtInvocationImpl][CtVariableReadImpl]maxCanSpawnEvent.getMaxPackSize() : [CtInvocationImpl][CtVariableReadImpl]entity.getMaxSpawnedInChunk();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.util.text.ITextComponent getPlayerDisplayName([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.text.ITextComponent username) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.NameFormat event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.NameFormat([CtVariableReadImpl]player, [CtVariableReadImpl]username);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getDisplayname();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]float fireBlockHarvesting([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.NonNullList<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> drops, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtTypeReferenceImpl]int fortune, [CtParameterImpl][CtTypeReferenceImpl]float dropChance, [CtParameterImpl][CtTypeReferenceImpl]boolean silkTouch, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]fortune, [CtVariableReadImpl]dropChance, [CtVariableReadImpl]drops, [CtVariableReadImpl]player, [CtVariableReadImpl]silkTouch);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getDropChance();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.block.BlockState fireFluidPlaceBlockEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos liquidPos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]liquidPos, [CtVariableReadImpl]state);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getNewState();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ItemTooltipEvent onItemTooltip([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack itemStack, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity entityPlayer, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.util.text.ITextComponent> list, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.client.util.ITooltipFlag flags) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ItemTooltipEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ItemTooltipEvent([CtVariableReadImpl]itemStack, [CtVariableReadImpl]entityPlayer, [CtVariableReadImpl]list, [CtVariableReadImpl]flags);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtVariableReadImpl]event;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent fireZombieSummonAid([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.monster.ZombieEntity zombie, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]int x, [CtParameterImpl][CtTypeReferenceImpl]int y, [CtParameterImpl][CtTypeReferenceImpl]int z, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity attacker, [CtParameterImpl][CtTypeReferenceImpl]double summonChance) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent summonEvent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent([CtVariableReadImpl]zombie, [CtVariableReadImpl]world, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtVariableReadImpl]z, [CtVariableReadImpl]attacker, [CtVariableReadImpl]summonChance);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]summonEvent);
        [CtReturnImpl]return [CtVariableReadImpl]summonEvent;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onEntityStruckByLightning([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.effect.LightningBoltEntity bolt) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityStruckByLightningEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]bolt));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onItemUseStart([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]int duration) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start([CtVariableReadImpl]entity, [CtVariableReadImpl]item, [CtVariableReadImpl]duration);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtUnaryOperatorImpl]-[CtLiteralImpl]1 : [CtInvocationImpl][CtVariableReadImpl]event.getDuration();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onItemUseTick([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]int duration) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick([CtVariableReadImpl]entity, [CtVariableReadImpl]item, [CtVariableReadImpl]duration);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtUnaryOperatorImpl]-[CtLiteralImpl]1 : [CtInvocationImpl][CtVariableReadImpl]event.getDuration();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onUseItemStop([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]int duration) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop([CtVariableReadImpl]entity, [CtVariableReadImpl]item, [CtVariableReadImpl]duration));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.item.ItemStack onItemUseFinish([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]int duration, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack result) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish([CtVariableReadImpl]entity, [CtVariableReadImpl]item, [CtVariableReadImpl]duration, [CtVariableReadImpl]result);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getResultStack();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onStartEntityTracking([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.StartTracking([CtVariableReadImpl]player, [CtVariableReadImpl]entity));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onStopEntityTracking([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.StopTracking([CtVariableReadImpl]player, [CtVariableReadImpl]entity));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void firePlayerLoadingEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]java.io.File playerDirectory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String uuidString) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile([CtVariableReadImpl]player, [CtVariableReadImpl]playerDirectory, [CtVariableReadImpl]uuidString));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void firePlayerSavingEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]java.io.File playerDirectory, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String uuidString) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile([CtVariableReadImpl]player, [CtVariableReadImpl]playerDirectory, [CtVariableReadImpl]uuidString));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void firePlayerLoadingEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.storage.PlayerData playerFileData, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String uuidString) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]playerFileData.getPlayerDataFolder(), [CtVariableReadImpl]uuidString));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static [CtTypeReferenceImpl]net.minecraft.util.text.ITextComponent onClientChat([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.text.ChatType type, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.text.ITextComponent message, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.util.UUID senderUUID) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.client.event.ClientChatReceivedEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.client.event.ClientChatReceivedEvent([CtVariableReadImpl]type, [CtVariableReadImpl]message, [CtVariableReadImpl]senderUUID);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]event.getMessage();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    public static [CtTypeReferenceImpl]java.lang.String onClientSendMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.client.event.ClientChatEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.client.event.ClientChatEvent([CtVariableReadImpl]message);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtLiteralImpl]"" : [CtInvocationImpl][CtVariableReadImpl]event.getMessage();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onHoeUse([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemUseContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.UseHoeEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.UseHoeEvent([CtVariableReadImpl]context);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getResult() == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getItem().damageItem([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]context.getPlayer(), [CtLambdaImpl]([CtParameterImpl] player) -> [CtInvocationImpl][CtVariableReadImpl]player.sendBreakAnimation([CtInvocationImpl][CtVariableReadImpl]context.getHand()));
            [CtReturnImpl]return [CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static [CtTypeReferenceImpl]net.minecraft.block.BlockState onToolUse([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState originalState, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack stack, [CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.common.ToolType toolType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]originalState, [CtVariableReadImpl]player, [CtVariableReadImpl]stack, [CtVariableReadImpl]toolType);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtLiteralImpl]null : [CtInvocationImpl][CtVariableReadImpl]event.getFinalState();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onApplyBonemeal([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.item.ItemStack stack) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.BonemealEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.BonemealEvent([CtVariableReadImpl]player, [CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]state, [CtVariableReadImpl]stack);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getResult() == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtVariableReadImpl]world.isRemote)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]stack.shrink([CtLiteralImpl]1);

            [CtReturnImpl]return [CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> onBucketUse([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.item.ItemStack stack, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult target) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.FillBucketEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.FillBucketEvent([CtVariableReadImpl]player, [CtVariableReadImpl]stack, [CtVariableReadImpl]world, [CtVariableReadImpl]target);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack>([CtFieldReadImpl]net.minecraft.util.ActionResultType.FAIL, [CtVariableReadImpl]stack);

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getResult() == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]player.abilities.isCreativeMode)[CtBlockImpl]
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack>([CtFieldReadImpl]net.minecraft.util.ActionResultType.SUCCESS, [CtVariableReadImpl]stack);

            [CtInvocationImpl][CtVariableReadImpl]stack.shrink([CtLiteralImpl]1);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]stack.isEmpty())[CtBlockImpl]
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack>([CtFieldReadImpl]net.minecraft.util.ActionResultType.SUCCESS, [CtInvocationImpl][CtVariableReadImpl]event.getFilledBucket());

            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]player.inventory.addItemStackToInventory([CtInvocationImpl][CtVariableReadImpl]event.getFilledBucket()))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]player.dropItem([CtInvocationImpl][CtVariableReadImpl]event.getFilledBucket(), [CtLiteralImpl]false);

            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack>([CtFieldReadImpl]net.minecraft.util.ActionResultType.SUCCESS, [CtVariableReadImpl]stack);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canEntityUpdate([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityEvent.CanUpdate event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityEvent.CanUpdate([CtVariableReadImpl]entity);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getCanUpdate();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraftforge.event.entity.PlaySoundAtEntityEvent onPlaySoundAtEntity([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.SoundEvent name, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.SoundCategory category, [CtParameterImpl][CtTypeReferenceImpl]float volume, [CtParameterImpl][CtTypeReferenceImpl]float pitch) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.PlaySoundAtEntityEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.PlaySoundAtEntityEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]name, [CtVariableReadImpl]category, [CtVariableReadImpl]volume, [CtVariableReadImpl]pitch);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtVariableReadImpl]event;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onItemExpire([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.item.ItemEntity entity, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]net.minecraft.item.ItemStack item) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]item.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.item.ItemExpireEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.item.ItemExpireEvent([CtVariableReadImpl]entity, [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]item.isEmpty() ? [CtLiteralImpl]6000 : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getItem().getEntityLifespan([CtVariableReadImpl]item, [CtFieldReadImpl][CtVariableReadImpl]entity.world));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getExtraLife();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onItemPickup([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.item.ItemEntity entityItem, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.EntityItemPickupEvent([CtVariableReadImpl]player, [CtVariableReadImpl]entityItem);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getResult() == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW ? [CtLiteralImpl]1 : [CtLiteralImpl]0;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canMountEntity([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entityMounting, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entityBeingMounted, [CtParameterImpl][CtTypeReferenceImpl]boolean isMounting) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isCanceled = [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityMountEvent([CtVariableReadImpl]entityMounting, [CtVariableReadImpl]entityBeingMounted, [CtFieldReadImpl][CtVariableReadImpl]entityMounting.world, [CtVariableReadImpl]isMounting));
        [CtIfImpl]if ([CtVariableReadImpl]isCanceled) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]entityMounting.setPositionAndRotation([CtInvocationImpl][CtVariableReadImpl]entityMounting.getPosX(), [CtInvocationImpl][CtVariableReadImpl]entityMounting.getPosY(), [CtInvocationImpl][CtVariableReadImpl]entityMounting.getPosZ(), [CtFieldReadImpl][CtVariableReadImpl]entityMounting.prevRotationYaw, [CtFieldReadImpl][CtVariableReadImpl]entityMounting.prevRotationPitch);
            [CtReturnImpl]return [CtLiteralImpl]false;
        } else[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onAnimalTame([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.passive.AnimalEntity animal, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity tamer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.AnimalTameEvent([CtVariableReadImpl]animal, [CtVariableReadImpl]tamer));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity.SleepResult onPlayerSleepInBed([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]net.minecraft.util.math.BlockPos> pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerSleepInBedEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerSleepInBedEvent([CtVariableReadImpl]player, [CtVariableReadImpl]pos);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getResultStatus();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPlayerWakeup([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]boolean wakeImmediately, [CtParameterImpl][CtTypeReferenceImpl]boolean updateWorldFlag) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerWakeUpEvent([CtVariableReadImpl]player, [CtVariableReadImpl]wakeImmediately, [CtVariableReadImpl]updateWorldFlag));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPlayerFall([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]float distance, [CtParameterImpl][CtTypeReferenceImpl]float multiplier) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerFlyableFallEvent([CtVariableReadImpl]player, [CtVariableReadImpl]distance, [CtVariableReadImpl]multiplier));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onPlayerSpawnSet([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.RegistryKey<[CtTypeReferenceImpl]net.minecraft.world.World> world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]boolean forced) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerSetSpawnEvent([CtVariableReadImpl]player, [CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]forced));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPlayerClone([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity oldPlayer, [CtParameterImpl][CtTypeReferenceImpl]boolean wasDeath) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.PlayerEvent.Clone([CtVariableReadImpl]player, [CtVariableReadImpl]oldPlayer, [CtVariableReadImpl]wasDeath));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onExplosionStart([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.Explosion explosion) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.ExplosionEvent.Start([CtVariableReadImpl]world, [CtVariableReadImpl]explosion));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onExplosionDetonate([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.Explosion explosion, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.entity.Entity> list, [CtParameterImpl][CtTypeReferenceImpl]double diameter) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Filter entities to only those who are effected, to prevent modders from seeing more then will be hurt.
        [CtCommentImpl]/* Enable this if we get issues with modders looping to much.
        Iterator<Entity> itr = list.iterator();
        Vec3 p = explosion.getPosition();
        while (itr.hasNext())
        {
        Entity e = itr.next();
        double dist = e.getDistance(p.xCoord, p.yCoord, p.zCoord) / diameter;
        if (e.isImmuneToExplosions() || dist > 1.0F) itr.remove();
        }
         */
        [CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.ExplosionEvent.Detonate([CtVariableReadImpl]world, [CtVariableReadImpl]explosion, [CtVariableReadImpl]list));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onCreateWorldSpawn([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.storage.IServerWorldInfo settings) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition([CtVariableReadImpl]world, [CtVariableReadImpl]settings));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]float onLivingHeal([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]float amount) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingHealEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingHealEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]amount);
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event) ? [CtLiteralImpl]0 : [CtInvocationImpl][CtVariableReadImpl]event.getAmount();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onPotionAttemptBrew([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.NonNullList<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> stacks) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.NonNullList<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> tmp = [CtInvocationImpl][CtTypeAccessImpl]net.minecraft.util.NonNullList.withSize([CtInvocationImpl][CtVariableReadImpl]stacks.size(), [CtTypeAccessImpl]ItemStack.EMPTY);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int x = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]x < [CtInvocationImpl][CtVariableReadImpl]tmp.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]x++)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]tmp.set([CtVariableReadImpl]x, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stacks.get([CtVariableReadImpl]x).copy());

        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.brewing.PotionBrewEvent.Pre event = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.brewing.PotionBrewEvent.Pre([CtVariableReadImpl]tmp);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean changed = [CtLiteralImpl]false;
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int x = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]x < [CtInvocationImpl][CtVariableReadImpl]stacks.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]x++) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]changed |= [CtInvocationImpl][CtTypeAccessImpl]net.minecraft.item.ItemStack.areItemStacksEqual([CtInvocationImpl][CtVariableReadImpl]tmp.get([CtVariableReadImpl]x), [CtInvocationImpl][CtVariableReadImpl]stacks.get([CtVariableReadImpl]x));
                [CtInvocationImpl][CtVariableReadImpl]stacks.set([CtVariableReadImpl]x, [CtInvocationImpl][CtVariableReadImpl]event.getItem([CtVariableReadImpl]x));
            }
            [CtIfImpl]if ([CtVariableReadImpl]changed)[CtBlockImpl]
                [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.onPotionBrewed([CtVariableReadImpl]stacks);

            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPotionBrewed([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.NonNullList<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> brewingItemStacks) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.brewing.PotionBrewEvent.Post([CtVariableReadImpl]brewingItemStacks));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onPlayerBrewedPotion([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack stack) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.brewing.PlayerBrewedPotionEvent([CtVariableReadImpl]player, [CtVariableReadImpl]stack));
    }

    [CtMethodImpl][CtAnnotationImpl]@net.minecraftforge.api.distmarker.OnlyIn([CtFieldReadImpl]net.minecraftforge.api.distmarker.Dist.CLIENT)
    public static [CtTypeReferenceImpl]boolean renderFireOverlay([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]com.mojang.blaze3d.matrix.MatrixStack mat) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.renderBlockOverlay([CtVariableReadImpl]player, [CtVariableReadImpl]mat, [CtTypeAccessImpl]OverlayType.FIRE, [CtInvocationImpl][CtTypeAccessImpl]Blocks.FIRE.getDefaultState(), [CtInvocationImpl][CtVariableReadImpl]player.func_233580_cy_());
    }

    [CtMethodImpl][CtAnnotationImpl]@net.minecraftforge.api.distmarker.OnlyIn([CtFieldReadImpl]net.minecraftforge.api.distmarker.Dist.CLIENT)
    public static [CtTypeReferenceImpl]boolean renderWaterOverlay([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]com.mojang.blaze3d.matrix.MatrixStack mat) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.renderBlockOverlay([CtVariableReadImpl]player, [CtVariableReadImpl]mat, [CtTypeAccessImpl]OverlayType.WATER, [CtInvocationImpl][CtTypeAccessImpl]Blocks.WATER.getDefaultState(), [CtInvocationImpl][CtVariableReadImpl]player.func_233580_cy_());
    }

    [CtMethodImpl][CtAnnotationImpl]@net.minecraftforge.api.distmarker.OnlyIn([CtFieldReadImpl]net.minecraftforge.api.distmarker.Dist.CLIENT)
    public static [CtTypeReferenceImpl]boolean renderBlockOverlay([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]com.mojang.blaze3d.matrix.MatrixStack mat, [CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType type, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState block, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.client.event.RenderBlockOverlayEvent([CtVariableReadImpl]player, [CtVariableReadImpl]mat, [CtVariableReadImpl]type, [CtVariableReadImpl]block, [CtVariableReadImpl]pos));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    public static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.ICapabilityProvider> [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.CapabilityDispatcher gatherCapabilities([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> type, [CtParameterImpl][CtTypeParameterReferenceImpl]T provider) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.gatherCapabilities([CtVariableReadImpl]type, [CtVariableReadImpl]provider, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@javax.annotation.Nullable
    public static <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.ICapabilityProvider> [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.CapabilityDispatcher gatherCapabilities([CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeParameterReferenceImpl]T> type, [CtParameterImpl][CtTypeParameterReferenceImpl]T provider, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.ICapabilityProvider parent) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.gatherCapabilities([CtConstructorCallImpl]new [CtTypeReferenceImpl]AttachCapabilitiesEvent<[CtTypeParameterReferenceImpl]T>([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T>) (type)), [CtVariableReadImpl]provider), [CtVariableReadImpl]parent);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private static [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.CapabilityDispatcher gatherCapabilities([CtParameterImpl][CtTypeReferenceImpl]AttachCapabilitiesEvent<[CtWildcardReferenceImpl]?> event, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.ICapabilityProvider parent) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getCapabilities().size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]parent != [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.common.capabilities.CapabilityDispatcher([CtInvocationImpl][CtVariableReadImpl]event.getCapabilities(), [CtInvocationImpl][CtVariableReadImpl]event.getListeners(), [CtVariableReadImpl]parent) : [CtLiteralImpl]null;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean fireSleepingLocationCheck([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos sleepingLocation) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.SleepingLocationCheckEvent evt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.SleepingLocationCheckEvent([CtVariableReadImpl]player, [CtVariableReadImpl]sleepingLocation);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]evt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result canContinueSleep = [CtInvocationImpl][CtVariableReadImpl]evt.getResult();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]canContinueSleep == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]player.getBedPosition().map([CtLambdaImpl]([CtParameterImpl] pos) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state = [CtInvocationImpl][CtVariableReadImpl]player.world.getBlockState([CtVariableReadImpl]pos);
                [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]state.getBlock().isBed([CtVariableReadImpl]state, [CtVariableReadImpl]player.world, [CtVariableReadImpl]pos, [CtVariableReadImpl]player);
            }).orElse([CtLiteralImpl]false);
        } else[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]canContinueSleep == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW;

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean fireSleepingTimeCheck([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]net.minecraft.util.math.BlockPos> sleepingLocation) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.SleepingTimeCheckEvent evt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.SleepingTimeCheckEvent([CtVariableReadImpl]player, [CtVariableReadImpl]sleepingLocation);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]evt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result canContinueSleep = [CtInvocationImpl][CtVariableReadImpl]evt.getResult();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]canContinueSleep == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT)[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]player.world.isDaytime();
        else[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]canContinueSleep == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW;

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack> onArrowNock([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Hand hand, [CtParameterImpl][CtTypeReferenceImpl]boolean hasAmmo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ArrowNockEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ArrowNockEvent([CtVariableReadImpl]player, [CtVariableReadImpl]item, [CtVariableReadImpl]hand, [CtVariableReadImpl]world, [CtVariableReadImpl]hasAmmo);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.ActionResult<[CtTypeReferenceImpl]net.minecraft.item.ItemStack>([CtFieldReadImpl]net.minecraft.util.ActionResultType.FAIL, [CtVariableReadImpl]item);

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getAction();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onArrowLoose([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack stack, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.PlayerEntity player, [CtParameterImpl][CtTypeReferenceImpl]int charge, [CtParameterImpl][CtTypeReferenceImpl]boolean hasAmmo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ArrowLooseEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.player.ArrowLooseEvent([CtVariableReadImpl]player, [CtVariableReadImpl]stack, [CtVariableReadImpl]world, [CtVariableReadImpl]charge, [CtVariableReadImpl]hasAmmo);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getCharge();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onProjectileImpact([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult ray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.ProjectileImpactEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]ray));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onProjectileImpact([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.projectile.AbstractArrowEntity arrow, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult ray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.ProjectileImpactEvent.Arrow([CtVariableReadImpl]arrow, [CtVariableReadImpl]ray));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onProjectileImpact([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.projectile.DamagingProjectileEntity fireball, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult ray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.ProjectileImpactEvent.Fireball([CtVariableReadImpl]fireball, [CtVariableReadImpl]ray));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onProjectileImpact([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.projectile.ThrowableEntity throwable, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult ray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.ProjectileImpactEvent.Throwable([CtVariableReadImpl]throwable, [CtVariableReadImpl]ray));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onProjectileImpact([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.projectile.FireworkRocketEntity fireworkRocket, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.RayTraceResult ray) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.ProjectileImpactEvent.FireworkRocket([CtVariableReadImpl]fireworkRocket, [CtVariableReadImpl]ray));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]net.minecraft.loot.LootTable loadLootTable([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.ResourceLocation name, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.loot.LootTable table, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.loot.LootTableManager lootTableManager) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]LootTableLoadEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]LootTableLoadEvent([CtVariableReadImpl]name, [CtVariableReadImpl]table, [CtVariableReadImpl]lootTableManager);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event))[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]net.minecraft.loot.LootTable.EMPTY_LOOT_TABLE;

        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getTable();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean canCreateFluidSource([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state, [CtParameterImpl][CtTypeReferenceImpl]boolean def) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent evt = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]state);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]evt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result result = [CtInvocationImpl][CtVariableReadImpl]evt.getResult();
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT ? [CtVariableReadImpl]def : [CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onTrySpawnPortal([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraft.block.NetherPortalBlock.Size size) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]world.getBlockState([CtVariableReadImpl]pos), [CtVariableReadImpl]size));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]int onEnchantmentLevelSet([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]int enchantRow, [CtParameterImpl][CtTypeReferenceImpl]int power, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.item.ItemStack itemStack, [CtParameterImpl][CtTypeReferenceImpl]int level) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent e = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]enchantRow, [CtVariableReadImpl]power, [CtVariableReadImpl]itemStack, [CtVariableReadImpl]level);
        [CtInvocationImpl][CtTypeAccessImpl]net.minecraftforge.common.MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]e);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]e.getLevel();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onEntityDestroyBlock([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.LivingEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.block.BlockState state) [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.living.LivingDestroyBlockEvent([CtVariableReadImpl]entity, [CtVariableReadImpl]pos, [CtVariableReadImpl]state));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean getMobGriefingEvent([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.Entity entity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityMobGriefingEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.entity.EntityMobGriefingEvent([CtVariableReadImpl]entity);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.eventbus.api.Event.Result result = [CtInvocationImpl][CtVariableReadImpl]event.getResult();
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DEFAULT ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]world.getGameRules().getBoolean([CtTypeAccessImpl]GameRules.MOB_GRIEFING) : [CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.ALLOW;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean saplingGrowTree([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.IWorld world, [CtParameterImpl][CtTypeReferenceImpl]java.util.Random rand, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.SaplingGrowTreeEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.SaplingGrowTreeEvent([CtVariableReadImpl]world, [CtVariableReadImpl]rand, [CtVariableReadImpl]pos);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getResult() != [CtFieldReadImpl]net.minecraftforge.eventbus.api.Event.Result.DENY;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void fireChunkWatch([CtParameterImpl][CtTypeReferenceImpl]boolean watch, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.ServerPlayerEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.ChunkPos chunkpos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.server.ServerWorld world) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]watch)[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.ChunkWatchEvent.Watch([CtVariableReadImpl]entity, [CtVariableReadImpl]chunkpos, [CtVariableReadImpl]world));
        else[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.ChunkWatchEvent.UnWatch([CtVariableReadImpl]entity, [CtVariableReadImpl]chunkpos, [CtVariableReadImpl]world));

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void fireChunkWatch([CtParameterImpl][CtTypeReferenceImpl]boolean wasLoaded, [CtParameterImpl][CtTypeReferenceImpl]boolean load, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.entity.player.ServerPlayerEntity entity, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.ChunkPos chunkpos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.server.ServerWorld world) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]wasLoaded != [CtVariableReadImpl]load)[CtBlockImpl]
            [CtInvocationImpl]net.minecraftforge.event.ForgeEventFactory.fireChunkWatch([CtVariableReadImpl]load, [CtVariableReadImpl]entity, [CtVariableReadImpl]chunkpos, [CtVariableReadImpl]world);

    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onPistonMovePre([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Direction direction, [CtParameterImpl][CtTypeReferenceImpl]boolean extending) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.PistonEvent.Pre([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]direction, [CtConditionalImpl][CtVariableReadImpl]extending ? [CtFieldReadImpl]PistonEvent.PistonMoveType.EXTEND : [CtFieldReadImpl]PistonEvent.PistonMoveType.RETRACT));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean onPistonMovePost([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.World world, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.math.BlockPos pos, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Direction direction, [CtParameterImpl][CtTypeReferenceImpl]boolean extending) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.PistonEvent.Post([CtVariableReadImpl]world, [CtVariableReadImpl]pos, [CtVariableReadImpl]direction, [CtConditionalImpl][CtVariableReadImpl]extending ? [CtFieldReadImpl]PistonEvent.PistonMoveType.EXTEND : [CtFieldReadImpl]PistonEvent.PistonMoveType.RETRACT));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]long onSleepFinished([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.world.server.ServerWorld world, [CtParameterImpl][CtTypeReferenceImpl]long newTime, [CtParameterImpl][CtTypeReferenceImpl]long minTime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.event.world.SleepFinishedTimeEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.event.world.SleepFinishedTimeEvent([CtVariableReadImpl]world, [CtVariableReadImpl]newTime, [CtVariableReadImpl]minTime);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getNewTime();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.minecraft.resources.IFutureReloadListener> onResourceReload([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.resources.DataPackRegistries dataPackRegistries) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]AddReloadListenerEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]AddReloadListenerEvent([CtVariableReadImpl]dataPackRegistries);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]event.getListeners();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void onCommandRegister([CtParameterImpl][CtTypeReferenceImpl]com.mojang.brigadier.CommandDispatcher<[CtTypeReferenceImpl]net.minecraft.command.CommandSource> dispatcher, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]net.minecraft.command.Commands.EnvironmentType environment) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]RegisterCommandsEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RegisterCommandsEvent([CtVariableReadImpl]dispatcher, [CtVariableReadImpl]environment);
        [CtInvocationImpl][CtTypeAccessImpl]MinecraftForge.EVENT_BUS.post([CtVariableReadImpl]event);
    }
}
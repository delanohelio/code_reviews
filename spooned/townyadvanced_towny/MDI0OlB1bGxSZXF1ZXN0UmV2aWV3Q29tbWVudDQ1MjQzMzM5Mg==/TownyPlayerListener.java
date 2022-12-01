[CompilationUnitImpl][CtPackageDeclarationImpl]package com.palmergames.bukkit.towny.listeners;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Resident;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownBlockType;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.bukkit.event.Event;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerBedEnterEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.war.common.WarZoneConfig;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerMoveEvent;
[CtUnresolvedImport]import org.bukkit.block.BlockFace;
[CtUnresolvedImport]import org.bukkit.entity.Player;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerFishEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.war.flagwar.FlagWarConfig;
[CtUnresolvedImport]import net.citizensnpcs.api.CitizensAPI;
[CtUnresolvedImport]import org.bukkit.event.EventHandler;
[CtUnresolvedImport]import org.bukkit.block.Block;
[CtUnresolvedImport]import org.bukkit.Location;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.tasks.TeleportWarmupTimerTask;
[CtUnresolvedImport]import org.bukkit.Material;
[CtUnresolvedImport]import org.bukkit.inventory.EquipmentSlot;
[CtUnresolvedImport]import org.bukkit.Bukkit;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerJoinEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownyPermission;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.permissions.TownyPerms;
[CtUnresolvedImport]import org.bukkit.entity.Entity;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownyWorld;
[CtUnresolvedImport]import com.palmergames.bukkit.util.BukkitTools;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.utils.CombatUtil;
[CtUnresolvedImport]import org.bukkit.entity.EntityType;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Coord;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerBucketFillEvent;
[CtUnresolvedImport]import org.bukkit.inventory.ItemStack;
[CtUnresolvedImport]import org.bukkit.event.entity.PlayerDeathEvent;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerQuitEvent;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.WorldCoord;
[CtUnresolvedImport]import org.bukkit.Tag;
[CtUnresolvedImport]import org.bukkit.block.data.type.WallSign;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerRespawnEvent;
[CtUnresolvedImport]import org.bukkit.event.block.Action;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerCommandPreprocessEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.util.ChatTools;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.db.TownyDataSource;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Town;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerInteractEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.event.PlayerEnterTownEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.war.eventwar.WarUtil;
[CtUnresolvedImport]import org.bukkit.event.Listener;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.tasks.OnPlayerLogin;
[CtUnresolvedImport]import com.palmergames.util.StringMgmt;
[CtImportImpl]import com.palmergames.bukkit.towny.*;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Nation;
[CtUnresolvedImport]import com.palmergames.bukkit.util.Colors;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.PlayerCache;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerInteractEntityEvent;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerTakeLecternBookEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus;
[CtUnresolvedImport]import org.bukkit.block.data.type.Sign;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerBucketEmptyEvent;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerInteractAtEntityEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.permissions.PermissionNodes;
[CtUnresolvedImport]import org.bukkit.event.EventPriority;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerTeleportEvent;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownBlock;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
[CtUnresolvedImport]import org.bukkit.ChatColor;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.TownyException;
[CtUnresolvedImport]import org.bukkit.event.player.PlayerChangedWorldEvent;
[CtClassImpl][CtJavaDocImpl]/**
 * Handle events for all Player related events
 * Players deaths are handled both here and in the TownyEntityMonitorListener
 *
 * @author Shade/ElgarL
 */
public class TownyPlayerListener implements [CtTypeReferenceImpl]org.bukkit.event.Listener {
    [CtFieldImpl]private final [CtTypeReferenceImpl]com.palmergames.bukkit.towny.listeners.Towny plugin;

    [CtConstructorImpl]public TownyPlayerListener([CtParameterImpl][CtTypeReferenceImpl]Towny instance) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]plugin = [CtVariableReadImpl]instance;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerJoin([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerJoinEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]player.sendMessage([CtBinaryOperatorImpl][CtFieldReadImpl]com.palmergames.bukkit.util.Colors.Rose + [CtLiteralImpl]"[Towny Error] Locked in Safe mode!");
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]player.isOnline()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// Test and kick any players with invalid names.
        if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]player.getName().contains([CtLiteralImpl]" ")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]player.kickPlayer([CtLiteralImpl]"Invalid name!");
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// Perform login code in it's own thread to update Towny data.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.BukkitTools.scheduleSyncDelayedTask([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.tasks.OnPlayerLogin([CtInvocationImpl][CtTypeAccessImpl]Towny.getPlugin(), [CtVariableReadImpl]player), [CtLiteralImpl]0L) == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtLiteralImpl]"Could not schedule OnLogin.");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerQuit([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerQuitEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownyDataSource dataSource = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtVariableReadImpl]dataSource.getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
            [CtInvocationImpl][CtVariableReadImpl]resident.setLastOnline([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
            [CtInvocationImpl][CtVariableReadImpl]resident.clearModes();
            [CtInvocationImpl][CtVariableReadImpl]dataSource.saveResident([CtVariableReadImpl]resident);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException ignored) [CtBlockImpl]{
        }
        [CtTryImpl][CtCommentImpl]// Remove from teleport queue (if exists)
        try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownyTimerHandler.isTeleportWarmupRunning()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().abortTeleportRequest([CtInvocationImpl][CtVariableReadImpl]dataSource.getResident([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName().toLowerCase()));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException ignored) [CtBlockImpl]{
        }
        [CtInvocationImpl][CtFieldReadImpl]plugin.deleteCache([CtInvocationImpl][CtVariableReadImpl]event.getPlayer());
        [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.permissions.TownyPerms.removeAttachment([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerRespawn([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerRespawnEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]TownySettings.isTownRespawning()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// If respawn anchors have higher precedence than town spawns, use them instead.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Towny.is116Plus() && [CtInvocationImpl][CtVariableReadImpl]event.isAnchorSpawn()) && [CtInvocationImpl][CtTypeAccessImpl]TownySettings.isRespawnAnchorHigherPrecedence()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location respawn;
        [CtAssignmentImpl][CtVariableWriteImpl]respawn = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getTownSpawnLocation([CtVariableReadImpl]player);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]respawn == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Town has not set respawn location. Using default.
            return;
        }
        [CtIfImpl][CtCommentImpl]// Check if only respawning in the same world as the town's spawn.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]TownySettings.isTownRespawningInOtherWorlds() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]player.getWorld().equals([CtInvocationImpl][CtVariableReadImpl]respawn.getWorld())))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl][CtCommentImpl]// Bed spawn or town.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]TownySettings.getBedUse() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]player.getBedSpawnLocation() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setRespawnLocation([CtInvocationImpl][CtVariableReadImpl]player.getBedSpawnLocation());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setRespawnLocation([CtVariableReadImpl]respawn);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGHEST)
    public [CtTypeReferenceImpl]void onPlayerJailRespawn([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerRespawnEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TownyUniverse townyUniverse = [CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]TownySettings.isTownRespawning())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location respawn = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
            [CtIfImpl][CtCommentImpl]// If player is jailed send them to their jailspawn.
            if ([CtInvocationImpl][CtVariableReadImpl]resident.isJailed()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town respawnTown = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getTown([CtInvocationImpl][CtVariableReadImpl]resident.getJailTown());
                [CtAssignmentImpl][CtVariableWriteImpl]respawn = [CtInvocationImpl][CtVariableReadImpl]respawnTown.getJailSpawn([CtInvocationImpl][CtVariableReadImpl]resident.getJailSpawn());
                [CtInvocationImpl][CtVariableReadImpl]event.setRespawnLocation([CtVariableReadImpl]respawn);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.TownyException e) [CtBlockImpl]{
            [CtCommentImpl]// Town has not set respawn location. Using default.
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerBucketEmpty([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerBucketEmptyEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtCommentImpl]// Test against the item in hand as we need to test the bucket contents
        [CtCommentImpl]// we are trying to empty.
        [CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getBlockClicked().getRelative([CtInvocationImpl][CtVariableReadImpl]event.getBlockFace()), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand()));
        [CtIfImpl][CtCommentImpl]// Test on the resulting empty bucket to see if we have permission to
        [CtCommentImpl]// empty a bucket.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]event.isCancelled())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtVariableReadImpl]event.getBlockClicked(), [CtInvocationImpl][CtVariableReadImpl]event.getItemStack()));

    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerBucketFill([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerBucketFillEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtCommentImpl]// test against the bucket we will finish up with to see if we are
        [CtCommentImpl]// allowed to fill this item.
        [CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtVariableReadImpl]event.getBlockClicked(), [CtInvocationImpl][CtVariableReadImpl]event.getItemStack()));
    }

    [CtMethodImpl][CtCommentImpl]/* PlayerInteractEvent 

     Used to stop trampling of crops,
     admin infotool,
     item use check,
     switch use check
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerInteract([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerInteractEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.Block block = [CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isTownyWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getWorld()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]event.hasItem()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* Info Tool */
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand().getType() == [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Material.getMaterial([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getTool())) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getPermissionSource().isTownyAdmin([CtVariableReadImpl]player)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]block = [CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock();
                        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Tag.SIGNS.isTagged([CtInvocationImpl][CtVariableReadImpl]block.getType())) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.BlockFace facing = [CtLiteralImpl]null;
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]block.getBlockData() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.block.data.type.Sign) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.data.type.Sign sign = [CtInvocationImpl](([CtTypeReferenceImpl]org.bukkit.block.data.type.Sign) ([CtVariableReadImpl]block.getBlockData()));
                                [CtAssignmentImpl][CtVariableWriteImpl]facing = [CtInvocationImpl][CtVariableReadImpl]sign.getRotation();
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]block.getBlockData() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.block.data.type.WallSign) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.data.type.WallSign sign = [CtInvocationImpl](([CtTypeReferenceImpl]org.bukkit.block.data.type.WallSign) ([CtVariableReadImpl]block.getBlockData()));
                                [CtAssignmentImpl][CtVariableWriteImpl]facing = [CtInvocationImpl][CtVariableReadImpl]sign.getFacing();
                            }
                            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMessage([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatTitle([CtLiteralImpl]"Sign Info"), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"Sign Type", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]block.getType().name()), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"Facing", [CtLiteralImpl]"", [CtInvocationImpl][CtVariableReadImpl]facing.toString())));
                        } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Tag.DOORS.isTagged([CtInvocationImpl][CtVariableReadImpl]block.getType())) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.data.type.Door door = [CtInvocationImpl](([CtTypeReferenceImpl]org.bukkit.block.data.type.Door) ([CtVariableReadImpl]block.getBlockData()));
                            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMessage([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatTitle([CtLiteralImpl]"Door Info"), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"Door Type", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]block.getType().name()), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"hinged on ", [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]door.getHinge())), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"isOpen", [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]door.isOpen())), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"getFacing", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]door.getFacing().name())));
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMessage([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatTitle([CtLiteralImpl]"Block Info"), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"Material", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]block.getType().name()), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"MaterialData", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]block.getBlockData().getAsString())));
                        }
                        [CtInvocationImpl][CtVariableReadImpl]event.setUseInteractedBlock([CtTypeAccessImpl]Event.Result.DENY);
                        [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                    }
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isItemUseMaterial([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getItem().getType().name())) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendDebugMsg([CtBinaryOperatorImpl][CtLiteralImpl]"ItemUse Material found: " + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getItem().getType().name());
                [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock(), [CtInvocationImpl][CtVariableReadImpl]event.getItem()));
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.useItemInHand().equals([CtTypeAccessImpl]Event.Result.DENY))[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]TownySettings.isSwitchMaterial([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock().getType().name()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getAction() == [CtFieldReadImpl]org.bukkit.event.block.Action.PHYSICAL)) [CtBlockImpl]{
                    [CtInvocationImpl]onPlayerSwitchEvent([CtVariableReadImpl]event, [CtLiteralImpl]null);
                }
            }

    }

    [CtMethodImpl][CtCommentImpl]/* PlayerInteractAtEntity event

    Handles protection of Armor Stands,
    Admin infotool for entities.
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerInteractEntity([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerInteractAtEntityEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isTownyWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getWorld()))[CtBlockImpl]
                [CtReturnImpl]return;

            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean bBuild = [CtLiteralImpl]true;
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Material block = [CtLiteralImpl]null;
            [CtSwitchImpl][CtCommentImpl]/* Protect specific entity interactions. */
            switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getType()) {
                [CtCaseImpl]case [CtFieldReadImpl]ARMOR_STAND :
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendDebugMsg([CtLiteralImpl]"ArmorStand Right Clicked");
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.ARMOR_STAND;
                    [CtAssignmentImpl][CtCommentImpl]// Get permissions (updates if none exist)
                    [CtVariableWriteImpl]bBuild = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getLocation(), [CtVariableReadImpl]block, [CtTypeAccessImpl]TownyPermission.ActionType.DESTROY);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]ITEM_FRAME :
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendDebugMsg([CtLiteralImpl]"Item_Frame Right Clicked");
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.ITEM_FRAME;
                    [CtAssignmentImpl][CtCommentImpl]// Get permissions (updates if none exist)
                    [CtVariableWriteImpl]bBuild = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getLocation(), [CtVariableReadImpl]block, [CtTypeAccessImpl]TownyPermission.ActionType.SWITCH);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]LEASH_HITCH :
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendDebugMsg([CtLiteralImpl]"Leash Hitch Right Clicked");
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.LEAD;
                    [CtAssignmentImpl][CtCommentImpl]// Get permissions (updates if none exist)
                    [CtVariableWriteImpl]bBuild = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getLocation(), [CtVariableReadImpl]block, [CtTypeAccessImpl]TownyPermission.ActionType.DESTROY);
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]block != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Allow the removal if we are permitted
                if ([CtVariableReadImpl]bBuild)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                [CtLocalVariableImpl][CtCommentImpl]/* Fetch the players cache */
                [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache cache = [CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cache.hasBlockErrMsg())[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]cache.getBlockErrMsg());

                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]/* Item_use protection. */
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isItemUseMaterial([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand().getType().name())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand()));
                }
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]/* PlayerInteractEntity event

    Handles right clicking of entities: Item Frames, Paintings, Minecarts,
    Admin infotool for entities.
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerInteractEntity([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerInteractEntityEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld World = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]World = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getWorld([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getWorld().getName());
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]World.isUsingTowny())[CtBlockImpl]
                    [CtReturnImpl]return;

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// World not registered with Towny.
                [CtVariableReadImpl]e.printStackTrace();
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Material block = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyPermission.ActionType actionType = [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownyPermission.ActionType.SWITCH;
            [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getType()) {
                [CtCaseImpl]case [CtFieldReadImpl]ITEM_FRAME :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.ITEM_FRAME;
                    [CtAssignmentImpl][CtVariableWriteImpl]actionType = [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownyPermission.ActionType.DESTROY;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]PAINTING :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.PAINTING;
                    [CtAssignmentImpl][CtVariableWriteImpl]actionType = [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownyPermission.ActionType.DESTROY;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]LEASH_HITCH :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.LEAD;
                    [CtAssignmentImpl][CtVariableWriteImpl]actionType = [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownyPermission.ActionType.DESTROY;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART :
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_MOB_SPAWNER :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.MINECART;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_CHEST :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.CHEST_MINECART;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_FURNACE :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.FURNACE_MINECART;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_COMMAND :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.COMMAND_BLOCK_MINECART;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_HOPPER :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.HOPPER_MINECART;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]MINECART_TNT :
                    [CtAssignmentImpl][CtVariableWriteImpl]block = [CtFieldReadImpl]org.bukkit.Material.TNT_MINECART;
                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]block != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]TownySettings.isSwitchMaterial([CtInvocationImpl][CtVariableReadImpl]block.name())) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Check if the player has valid permission for interacting with the entity based on the action type.
                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getRightClicked().getLocation(), [CtVariableReadImpl]block, [CtVariableReadImpl]actionType)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);[CtCommentImpl]// Cancel the event

                    [CtLocalVariableImpl][CtCommentImpl]/* Fetch the players cache */
                    [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache cache = [CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cache.hasBlockErrMsg())[CtBlockImpl]
                        [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]cache.getBlockErrMsg());

                }
                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]/* Item_use protection. */
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* Info Tool */
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand().getType() == [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Material.getMaterial([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getTool())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getHand().equals([CtTypeAccessImpl]EquipmentSlot.OFF_HAND))[CtBlockImpl]
                        [CtReturnImpl]return;

                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Entity entity = [CtInvocationImpl][CtVariableReadImpl]event.getRightClicked();
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMessage([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatTitle([CtLiteralImpl]"Entity Info"), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.ChatTools.formatCommand([CtLiteralImpl]"", [CtLiteralImpl]"Entity Class", [CtLiteralImpl]"", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getType().getEntityClass().getSimpleName())));
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                }
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isItemUseMaterial([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand().getType().name())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getInventory().getItemInMainHand()));
                }
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerMove([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerMoveEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TownyUniverse townyUniverse = [CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance();
        [CtIfImpl][CtCommentImpl]/* Abort if we havn't really moved */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getFrom().getBlockX() == [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getTo().getBlockX()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getFrom().getBlockZ() == [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getTo().getBlockZ())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getFrom().getBlockY() == [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getTo().getBlockY())) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location to = [CtInvocationImpl][CtVariableReadImpl]event.getTo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location from;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache cache = [CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resident = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getResident([CtInvocationImpl][CtVariableReadImpl]player.getName());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException ignored) [CtBlockImpl]{
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]resident != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]TownyTimerHandler.isTeleportWarmupRunning()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getTeleportWarmupTime() > [CtLiteralImpl]0)) && [CtInvocationImpl][CtTypeAccessImpl]TownySettings.isMovementCancellingSpawnWarmup()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getPermissionSource().has([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]PermissionNodes.TOWNY_ADMIN.getNode()))) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]resident.getTeleportRequestTime() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.tasks.TeleportWarmupTimerTask.abortTeleportRequest([CtVariableReadImpl]resident);
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMsg([CtVariableReadImpl]resident, [CtBinaryOperatorImpl][CtFieldReadImpl]org.bukkit.ChatColor.RED + [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_teleport_cancelled"));
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]from = [CtInvocationImpl][CtVariableReadImpl]cache.getLastLocation();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]from = [CtInvocationImpl][CtVariableReadImpl]event.getFrom();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.WorldCoord.cellChanged([CtVariableReadImpl]from, [CtVariableReadImpl]to)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld fromWorld = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getWorld().getName());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord fromCoord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord([CtInvocationImpl][CtVariableReadImpl]fromWorld.getName(), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtVariableReadImpl]from));
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld toWorld = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getWorld().getName());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord toCoord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord([CtInvocationImpl][CtVariableReadImpl]toWorld.getName(), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtVariableReadImpl]to));
                [CtInvocationImpl]onPlayerMoveChunk([CtVariableReadImpl]player, [CtVariableReadImpl]fromCoord, [CtVariableReadImpl]toCoord, [CtVariableReadImpl]from, [CtVariableReadImpl]to, [CtVariableReadImpl]event);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
            }
        }
        [CtInvocationImpl][CtCommentImpl]// Update the cached players current location
        [CtVariableReadImpl]cache.setLastLocation([CtVariableReadImpl]to);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerTeleport([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerTeleportEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Citizens stores their NPCs at the world spawn and when players load chunks the NPC is teleported there.
            [CtCommentImpl]// Towny was preventing them being teleported and causing NPCs to be at a world spawn, even after the Safe Mode was cleaned up.
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]plugin.isCitizens2() && [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]net.citizensnpcs.api.CitizensAPI.getNPCRegistry().isNPC([CtInvocationImpl][CtVariableReadImpl]event.getPlayer()))[CtBlockImpl]
                [CtReturnImpl]return;

            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtTryImpl][CtCommentImpl]// Cancel teleport if Jailed by Towny.
        try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getResident([CtInvocationImpl][CtVariableReadImpl]player.getName()).isJailed()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getCause() == [CtFieldReadImpl]org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.COMMAND) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_jailed_players_no_teleport")));
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                    [CtReturnImpl]return;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getCause() == [CtFieldReadImpl]org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getCause() != [CtFieldReadImpl]org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]TownySettings.JailAllowsEnderPearls())) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_jailed_players_no_teleport")));
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException ignored) [CtBlockImpl]{
            [CtCommentImpl]// Not a valid resident, probably an NPC from Citizens.
        }
        [CtIfImpl][CtCommentImpl]/* Test to see if CHORUS_FRUIT is in the item_use list. */
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getCause() == [CtFieldReadImpl]org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)[CtBlockImpl]
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isItemUseMaterial([CtInvocationImpl][CtTypeAccessImpl]Material.CHORUS_FRUIT.name()))[CtBlockImpl]
                [CtIfImpl]if ([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getTo().getBlock(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack([CtFieldReadImpl]org.bukkit.Material.CHORUS_FRUIT))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                    [CtReturnImpl]return;
                }


        [CtIfImpl][CtCommentImpl]/* Test to see if Ender pearls are disabled. */
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getCause() == [CtFieldReadImpl]org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL)[CtBlockImpl]
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isItemUseMaterial([CtInvocationImpl][CtTypeAccessImpl]Material.ENDER_PEARL.name()))[CtBlockImpl]
                [CtIfImpl]if ([CtInvocationImpl]onPlayerInteract([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getTo().getBlock(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack([CtFieldReadImpl]org.bukkit.Material.ENDER_PEARL))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_ender_pearls_disabled")));
                    [CtReturnImpl]return;
                }


        [CtInvocationImpl]onPlayerMove([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.LOWEST)
    public [CtTypeReferenceImpl]void onPlayerChangeWorld([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerChangedWorldEvent event) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// has changed worlds
        if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().isOnline())[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.permissions.TownyPerms.assignPermissions([CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]event.getPlayer());

    }

    [CtMethodImpl][CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.LOWEST, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerBedEnter([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerBedEnterEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isTownyWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getBed().getWorld()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]TownySettings.getBedUse())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isOwner = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isInnPlot = [CtLiteralImpl]false;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getWorld().getName(), [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getBed().getLocation()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock townblock = [CtInvocationImpl][CtVariableReadImpl]worldCoord.getTownBlock();
            [CtAssignmentImpl][CtVariableWriteImpl]isOwner = [CtInvocationImpl][CtVariableReadImpl]townblock.isOwner([CtVariableReadImpl]resident);
            [CtAssignmentImpl][CtVariableWriteImpl]isInnPlot = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]townblock.getType() == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownBlockType.INN;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]resident.hasNation() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townblock.getTown().hasNation()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Nation residentNation = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resident.getTown().getNation();
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Nation townblockNation = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townblock.getTown().getNation();
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]townblockNation.hasEnemy([CtVariableReadImpl]residentNation)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_no_sleep_in_enemy_inn")));
                    [CtReturnImpl]return;
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtCommentImpl]// Wilderness as it error'd getting a townblock.
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]isOwner) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isInnPlot)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_cant_use_bed")));
        }
    }

    [CtMethodImpl][CtCommentImpl]/* ItemUse protection handling */
    public [CtTypeReferenceImpl]boolean onPlayerInteract([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block block, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack item) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean cancelState = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord;
        [CtLocalVariableImpl][CtTypeReferenceImpl]TownyUniverse townyUniverse = [CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String worldName = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]player.getWorld().getName();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]block != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]worldCoord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord([CtVariableReadImpl]worldName, [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtVariableReadImpl]block));
            else[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]worldCoord = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord([CtVariableReadImpl]worldName, [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtVariableReadImpl]player));

            [CtLocalVariableImpl][CtCommentImpl]// Get itemUse permissions (updates if none exist)
            [CtTypeReferenceImpl]boolean bItemUse;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]block != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]bItemUse = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]block.getLocation(), [CtInvocationImpl][CtVariableReadImpl]item.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.ITEM_USE);
            else[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]bItemUse = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]player.getLocation(), [CtInvocationImpl][CtVariableReadImpl]item.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.ITEM_USE);

            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean wildOverride = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getPermissionSource().hasWildOverride([CtInvocationImpl][CtVariableReadImpl]worldCoord.getTownyWorld(), [CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]item.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.ITEM_USE);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache cache = [CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player);
            [CtTryImpl][CtCommentImpl]// cache.updateCoord(worldCoord);
            try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus status = [CtInvocationImpl][CtVariableReadImpl]cache.getStatus();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.UNCLAIMED_ZONE) && [CtVariableReadImpl]wildOverride)[CtBlockImpl]
                    [CtReturnImpl]return [CtVariableReadImpl]cancelState;

                [CtIfImpl][CtCommentImpl]// Allow item_use if we have an override
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.TOWN_RESIDENT) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getPermissionSource().hasOwnTownOverride([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]item.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.ITEM_USE)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.OUTSIDER) || [CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.TOWN_ALLY)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.ENEMY)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getPermissionSource().hasAllTownOverride([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]item.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.ITEM_USE)))[CtBlockImpl]
                    [CtReturnImpl]return [CtVariableReadImpl]cancelState;

                [CtIfImpl][CtCommentImpl]// Allow item_use for Event War if isAllowingItemUseInWarZone is true, FlagWar also handled here
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.WARZONE) && [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.flagwar.FlagWarConfig.isAllowingAttacks())[CtCommentImpl]// Flag War
                 || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isWarTime() && [CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.WARZONE)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.eventwar.WarUtil.isPlayerNeutral([CtVariableReadImpl]player)))) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// Event War
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.common.WarZoneConfig.isAllowingItemUseInWarZone()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]cancelState = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_warzone_cannot_use_item"));
                    }
                    [CtReturnImpl]return [CtVariableReadImpl]cancelState;
                }
                [CtIfImpl][CtCommentImpl]// Non-Override Wilderness & Non-Override Claimed Land Handled here.
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.UNCLAIMED_ZONE) && [CtUnaryOperatorImpl](![CtVariableReadImpl]wildOverride))[CtCommentImpl]// Wilderness
                 || [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]bItemUse) && [CtBinaryOperatorImpl]([CtVariableReadImpl]status != [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.UNCLAIMED_ZONE))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Claimed Land
                    [CtVariableWriteImpl]cancelState = [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cache.hasBlockErrMsg())[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]cache.getBlockErrMsg());

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.print([CtLiteralImpl]"NPE generated!");
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.print([CtBinaryOperatorImpl][CtLiteralImpl]"Player: " + [CtInvocationImpl][CtVariableReadImpl]player.getName());
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.print([CtBinaryOperatorImpl][CtLiteralImpl]"Item: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getType().name());
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e1) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_not_configured"));
            [CtAssignmentImpl][CtVariableWriteImpl]cancelState = [CtLiteralImpl]true;
            [CtReturnImpl]return [CtVariableReadImpl]cancelState;
        }
        [CtReturnImpl]return [CtVariableReadImpl]cancelState;
    }

    [CtMethodImpl][CtCommentImpl]/* Switch protection handling */
    public [CtTypeReferenceImpl]void onPlayerSwitchEvent([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerInteractEvent event, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String errMsg) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.Block block = [CtInvocationImpl][CtVariableReadImpl]event.getClickedBlock();
        [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtInvocationImpl]onPlayerSwitchEvent([CtVariableReadImpl]player, [CtVariableReadImpl]block, [CtVariableReadImpl]errMsg));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean onPlayerSwitchEvent([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block block, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String errMsg) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]TownySettings.isSwitchMaterial([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]block.getType().name()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtCommentImpl]// Get switch permissions (updates if none exist)
        [CtTypeReferenceImpl]boolean bSwitch = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]block.getLocation(), [CtInvocationImpl][CtVariableReadImpl]block.getType(), [CtTypeAccessImpl]TownyPermission.ActionType.SWITCH);
        [CtIfImpl][CtCommentImpl]// Allow switch if we are permitted
        if ([CtVariableReadImpl]bSwitch)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtCommentImpl]/* Fetch the players cache */
        [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache cache = [CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus status = [CtInvocationImpl][CtVariableReadImpl]cache.getStatus();
        [CtIfImpl][CtCommentImpl]/* Flag war & now Event War */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.WARZONE) && [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.flagwar.FlagWarConfig.isAllowingAttacks())[CtCommentImpl]// Flag War
         || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isWarTime() && [CtBinaryOperatorImpl]([CtVariableReadImpl]status == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.PlayerCache.TownBlockStatus.WARZONE)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.eventwar.WarUtil.isPlayerNeutral([CtVariableReadImpl]player)))) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Event War
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.war.common.WarZoneConfig.isAllowingSwitchesInWarZone()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_err_warzone_cannot_use_switches"));
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        } else [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]/* display any error recorded for this plot */
            if ([CtInvocationImpl][CtVariableReadImpl]cache.hasBlockErrMsg())[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]cache.getBlockErrMsg());

            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl][CtCommentImpl]/* PlayerFishEvent

    Prevents players from fishing for entities in protected regions.
    - Armorstands, animals, players, any entity affected by rods.
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH)
    public [CtTypeReferenceImpl]void onPlayerFishEvent([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerFishEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isTownyWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getWorld()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getState().equals([CtTypeAccessImpl]PlayerFishEvent.State.CAUGHT_ENTITY)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Entity caught = [CtInvocationImpl][CtVariableReadImpl]event.getCaught();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean test = [CtLiteralImpl]false;
            [CtIfImpl][CtCommentImpl]// Caught players are tested for pvp at the location of the catch.
            if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]caught.getType().equals([CtTypeAccessImpl]EntityType.PLAYER)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld townyWorld = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getTownWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]caught.getWorld().getName());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock tb = [CtLiteralImpl]null;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]tb = [CtInvocationImpl][CtVariableReadImpl]townyWorld.getTownBlock([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtInvocationImpl][CtVariableReadImpl]event.getCaught()));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e1) [CtBlockImpl]{
                }
                [CtAssignmentImpl][CtVariableWriteImpl]test = [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.CombatUtil.preventPvP([CtVariableReadImpl]townyWorld, [CtVariableReadImpl]tb);
                [CtCommentImpl]// Non-player catches are tested for destroy permissions.
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]test = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]caught.getLocation(), [CtTypeAccessImpl]Material.GRASS, [CtTypeAccessImpl]TownyPermission.ActionType.DESTROY);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]test) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getHook().remove();
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]/* PlayerMoveEvent that can fire the PlayerChangePlotEvent */
    public [CtTypeReferenceImpl]void onPlayerMoveChunk([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player, [CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord from, [CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord to, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.Location fromLoc, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.Location toLoc, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerMoveEvent moveEvent) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player).setLastLocation([CtVariableReadImpl]toLoc);
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]plugin.getCache([CtVariableReadImpl]player).updateCoord([CtVariableReadImpl]to);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerChangePlotEvent event = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerChangePlotEvent([CtVariableReadImpl]player, [CtVariableReadImpl]from, [CtVariableReadImpl]to, [CtVariableReadImpl]moveEvent);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtCommentImpl]/* PlayerChangePlotEvent that can fire the PlayerLeaveTownEvent and PlayerEnterTownEvent */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerChangePlotEvent([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerChangePlotEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerMoveEvent pme = [CtInvocationImpl][CtVariableReadImpl]event.getMoveEvent();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord from = [CtInvocationImpl][CtVariableReadImpl]event.getFrom();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord to = [CtInvocationImpl][CtVariableReadImpl]event.getTo();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Required so we don't fire events on NPCs from plugins like citizens.
            [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
            [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getResident([CtInvocationImpl][CtVariableReadImpl]player.getName());
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]to.getTownBlock();
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().hasTown()) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town fromTown = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getTownBlock().getTown();
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown().equals([CtVariableReadImpl]fromTown)) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerEnterTownEvent([CtVariableReadImpl]player, [CtVariableReadImpl]to, [CtVariableReadImpl]from, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown(), [CtVariableReadImpl]pme));[CtCommentImpl]// From Town into different Town.

                            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent([CtVariableReadImpl]player, [CtVariableReadImpl]to, [CtVariableReadImpl]from, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getTownBlock().getTown(), [CtVariableReadImpl]pme));[CtCommentImpl]// 

                        }
                        [CtCommentImpl]// Both are the same town, do nothing, no Event should fire here.
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// From Wilderness into Town.
                        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerEnterTownEvent([CtVariableReadImpl]player, [CtVariableReadImpl]to, [CtVariableReadImpl]from, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown(), [CtVariableReadImpl]pme));
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getTownBlock().hasTown() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().hasTown())) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// From has a town, to doesn't so: From Town into Wilderness
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent([CtVariableReadImpl]player, [CtVariableReadImpl]to, [CtVariableReadImpl]from, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getTownBlock().getTown(), [CtVariableReadImpl]pme));
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.bukkit.Bukkit.getServer().getPluginManager().callEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent([CtVariableReadImpl]player, [CtVariableReadImpl]to, [CtVariableReadImpl]from, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]from.getTownBlock().getTown(), [CtVariableReadImpl]pme));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtCommentImpl]// If not registered, it is most likely an NPC
        }
    }

    [CtMethodImpl][CtCommentImpl]/* onOutlawEnterTown
    - Shows message to outlaws entering towns in which they are considered an outlaw.
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onOutlawEnterTown([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerEnterTownEvent event) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord to = [CtInvocationImpl][CtVariableReadImpl]event.getTo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getResident([CtInvocationImpl][CtVariableReadImpl]player.getName());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown().hasOutlaw([CtVariableReadImpl]resident))[CtBlockImpl]
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendMsg([CtVariableReadImpl]player, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_you_are_an_outlaw_in_this_town"), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown()));

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * onPlayerDieInTown
     * - Handles death events and the KeepInventory/KeepLevel options are being used.
     *
     * @author - Articdive
     * @param event
     * 		- PlayerDeathEvent
     */
    [CtCommentImpl]// Why Highest??, so that we are the last ones to check for if it keeps their inventory, and then have no problems with it.
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGHEST)
    public [CtTypeReferenceImpl]void onPlayerDieInTown([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.entity.PlayerDeathEvent event) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean keepInventory = [CtInvocationImpl][CtVariableReadImpl]event.getKeepInventory();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean keepLevel = [CtInvocationImpl][CtVariableReadImpl]event.getKeepLevel();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getEntity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location deathloc = [CtInvocationImpl][CtVariableReadImpl]player.getLocation();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getKeepInventoryInTowns()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]keepInventory) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// If you don't keep your inventory via any other plugin or the server
                [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock tb = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getTownBlock([CtVariableReadImpl]deathloc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tb != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// So a valid TownBlock appears, how wonderful
                    if ([CtInvocationImpl][CtVariableReadImpl]tb.hasTown()) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// So the townblock has a town, and we keep inventory in towns, deathloc in a town. Do it!
                        [CtVariableReadImpl]event.setKeepInventory([CtLiteralImpl]true);
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getDrops().clear();
                    }
                }
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getKeepExperienceInTowns()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]keepLevel) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// If you don't keep your levels via any other plugin or the server, other events fire first, we just ignore it if they do save thier invs.
                [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock tb = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getTownBlock([CtVariableReadImpl]deathloc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tb != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// So a valid TownBlock appears, how wonderful
                    if ([CtInvocationImpl][CtVariableReadImpl]tb.hasTown()) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// So the townblock has atown, and is at the death location
                        [CtVariableReadImpl]event.setKeepLevel([CtLiteralImpl]true);
                        [CtInvocationImpl][CtVariableReadImpl]event.setDroppedExp([CtLiteralImpl]0);
                    }
                }
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.getKeepInventoryInArenas()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]keepInventory) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock tb = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getTownBlock([CtVariableReadImpl]deathloc);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tb != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tb.getType() == [CtFieldReadImpl]com.palmergames.bukkit.towny.object.TownBlockType.ARENA)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]event.setKeepInventory([CtLiteralImpl]true);
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getDrops().clear();
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * PlayerEnterTownEvent
     * Currently used for:
     *   - showing NotificationsUsingTitles upon entering a town.
     *
     * @param event
     * 		- PlayerEnterTownEvent
     * @throws TownyException
     * 		- Generic TownyException
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerEnterTown([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerEnterTownEvent event) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.TownyException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance().getDataSource().getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord to = [CtInvocationImpl][CtVariableReadImpl]event.getTo();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isNotificationUsingTitles()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String title = [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.ChatColor.translateAlternateColorCodes([CtLiteralImpl]'&', [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getNotificationTitlesTownTitle());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String subtitle = [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.ChatColor.translateAlternateColorCodes([CtLiteralImpl]'&', [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getNotificationTitlesTownSubtitle());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> placeholders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]placeholders.put([CtLiteralImpl]"{townname}", [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.util.StringMgmt.remUnderscore([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown().getName()));
            [CtInvocationImpl][CtVariableReadImpl]placeholders.put([CtLiteralImpl]"{town_motd}", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown().getTownBoard());
            [CtInvocationImpl][CtVariableReadImpl]placeholders.put([CtLiteralImpl]"{town_residents}", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown().getNumResidents());
            [CtInvocationImpl][CtVariableReadImpl]placeholders.put([CtLiteralImpl]"{town_residents_online}", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getOnlinePlayers([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown()));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> placeholder : [CtInvocationImpl][CtVariableReadImpl]placeholders.entrySet()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]title = [CtInvocationImpl][CtVariableReadImpl]title.replace([CtInvocationImpl][CtVariableReadImpl]placeholder.getKey(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placeholder.getValue().toString());
                [CtAssignmentImpl][CtVariableWriteImpl]subtitle = [CtInvocationImpl][CtVariableReadImpl]subtitle.replace([CtInvocationImpl][CtVariableReadImpl]placeholder.getKey(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placeholder.getValue().toString());
            }
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendTitleMessageToResident([CtVariableReadImpl]resident, [CtVariableReadImpl]title, [CtVariableReadImpl]subtitle);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * PlayerLeaveTownEvent
     * Currently used for:
     *   - showing NotificationsUsingTitles upon entering the wilderness.
     *   - unjailing residents
     *
     * @param event
     * 		- PlayerLeaveTownEvent
     * @throws TownyException
     * 		- Generic TownyException
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onPlayerLeaveTown([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.event.PlayerLeaveTownEvent event) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.TownyException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]TownyUniverse townyUniverse = [CtInvocationImpl][CtTypeAccessImpl]TownyUniverse.getInstance();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord to = [CtInvocationImpl][CtVariableReadImpl]event.getTo();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]TownySettings.isNotificationUsingTitles()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
                [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town toTown = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]to.getTownBlock().getTown();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// No town being entered so this is a move into the wilderness.
                [CtTypeReferenceImpl]java.lang.String title = [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.ChatColor.translateAlternateColorCodes([CtLiteralImpl]'&', [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getNotificationTitlesWildTitle());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String subtitle = [CtInvocationImpl][CtTypeAccessImpl]org.bukkit.ChatColor.translateAlternateColorCodes([CtLiteralImpl]'&', [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getNotificationTitlesWildSubtitle());
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]title.contains([CtLiteralImpl]"{wilderness}")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]title = [CtInvocationImpl][CtVariableReadImpl]title.replace([CtLiteralImpl]"{wilderness}", [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.util.StringMgmt.remUnderscore([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getWorld([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getLocation().getWorld().getName()).getUnclaimedZoneName()));
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]subtitle.contains([CtLiteralImpl]"{wilderness}")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]subtitle = [CtInvocationImpl][CtVariableReadImpl]subtitle.replace([CtLiteralImpl]"{wilderness}", [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.util.StringMgmt.remUnderscore([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getWorld([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getLocation().getWorld().getName()).getUnclaimedZoneName()));
                }
                [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendTitleMessageToResident([CtVariableReadImpl]resident, [CtVariableReadImpl]title, [CtVariableReadImpl]subtitle);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().getResident([CtInvocationImpl][CtVariableReadImpl]player.getName()).isJailed()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]resident.freeFromJail([CtVariableReadImpl]player, [CtInvocationImpl][CtVariableReadImpl]resident.getJailSpawn(), [CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townyUniverse.getDataSource().saveResident([CtVariableReadImpl]resident);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Any player that can break the lectern will be able to get the book anyways.
     *
     * @param event
     * 		- PlayerTakeLecternBookEvent
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.HIGH, ignoreCancelled = [CtLiteralImpl]true)
    public [CtTypeReferenceImpl]void onPlayerTakeLecternBookEvent([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerTakeLecternBookEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().isTownyWorld([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getLectern().getWorld()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.entity.Player player = [CtInvocationImpl][CtVariableReadImpl]event.getPlayer();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.Lectern lectern = [CtInvocationImpl][CtVariableReadImpl]event.getLectern();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location location = [CtInvocationImpl][CtVariableReadImpl]lectern.getLocation();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean bDestroy = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.utils.PlayerCacheUtil.getCachePermission([CtVariableReadImpl]player, [CtVariableReadImpl]location, [CtTypeAccessImpl]Material.LECTERN, [CtTypeAccessImpl]ActionType.DESTROY);
        [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtUnaryOperatorImpl]![CtVariableReadImpl]bDestroy);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Blocks jailed players using blacklisted commands.
     *
     * @param event
     * 		- PlayerCommandPreprocessEvent
     * @throws NotRegisteredException
     * 		- Generic NotRegisteredException
     */
    [CtAnnotationImpl]@org.bukkit.event.EventHandler(priority = [CtFieldReadImpl]org.bukkit.event.EventPriority.NORMAL)
    public [CtTypeReferenceImpl]void onJailedPlayerUsesCommand([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.event.player.PlayerCommandPreprocessEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]plugin.isError()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resident = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownyAPI.getInstance().getDataSource().getResident([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getPlayer().getName());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtCommentImpl]// More than likely another plugin using a fake player to run a command.
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]resident == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]resident.isJailed()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] split = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]event.getMessage().substring([CtLiteralImpl]1).split([CtLiteralImpl]" ");
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]TownySettings.getJailBlacklistedCommands().contains([CtArrayReadImpl][CtVariableReadImpl]split[[CtLiteralImpl]0])) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]TownyMessaging.sendErrorMsg([CtInvocationImpl][CtVariableReadImpl]event.getPlayer(), [CtInvocationImpl][CtTypeAccessImpl]TownySettings.getLangString([CtLiteralImpl]"msg_you_cannot_use_that_command_while_jailed"));
            [CtInvocationImpl][CtVariableReadImpl]event.setCancelled([CtLiteralImpl]true);
        }
    }
}
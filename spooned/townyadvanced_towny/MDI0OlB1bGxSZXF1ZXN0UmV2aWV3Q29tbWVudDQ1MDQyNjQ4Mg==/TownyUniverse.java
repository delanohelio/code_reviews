[CompilationUnitImpl][CtPackageDeclarationImpl]package com.palmergames.bukkit.towny;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.WorldCoord;
[CtUnresolvedImport]import com.palmergames.util.Trie;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Resident;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.db.TownyFlatFileSource;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.util.concurrent.CompletableFuture;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.tasks.BackupTask;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.bukkit.Location;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.KeyAlreadyRegisteredException;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.PlotGroup;
[CtUnresolvedImport]import org.bukkit.World;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.metadata.CustomDataField;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.db.TownyDataSource;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Town;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import org.bukkit.Bukkit;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.permissions.TownyPermissionSource;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Nation;
[CtUnresolvedImport]import org.bukkit.scheduler.BukkitScheduler;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.permissions.TownyPerms;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownyWorld;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.palmergames.bukkit.util.BukkitTools;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.Coord;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.tasks.CleanupBackupTask;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.war.eventwar.War;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.object.TownBlock;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.exceptions.TownyException;
[CtUnresolvedImport]import com.palmergames.util.FileMgmt;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.palmergames.bukkit.towny.db.TownySQLSource;
[CtClassImpl][CtJavaDocImpl]/**
 * Towny's class for internal API Methods
 * If you don't want to change the dataSource, war, permissions or similiar behavior
 * and only for example want to get Resident objects you should use {@link TownyAPI}
 *
 * @author Lukas Mansour (Articdive)
 */
public class TownyUniverse {
    [CtFieldImpl]private static [CtTypeReferenceImpl]com.palmergames.bukkit.towny.TownyUniverse instance;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.palmergames.bukkit.towny.Towny towny;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident> residents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.palmergames.util.Trie residentsTrie = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.util.Trie();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town> towns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.palmergames.util.Trie townsTrie = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.util.Trie();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Nation> nations = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.palmergames.util.Trie nationsTrie = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.util.Trie();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld> worlds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.metadata.CustomDataField> registeredMetadata = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock> townBlocks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident> jailedResidents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String rootFolder;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownyDataSource dataSource;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.palmergames.bukkit.towny.permissions.TownyPermissionSource permissionSource;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.palmergames.bukkit.towny.war.eventwar.War warEvent;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String saveDbType;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String loadDbType;

    [CtConstructorImpl]private TownyUniverse() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]towny = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.Towny.getPlugin();
        [CtAssignmentImpl][CtFieldWriteImpl]rootFolder = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]towny.getDataFolder().getPath();
    }

    [CtMethodImpl][CtCommentImpl]// TODO: Put loadSettings into the constructor, since it is 1-time-run code.
    [CtTypeReferenceImpl]boolean loadSettings() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.loadConfig([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]rootFolder + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"settings") + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"config.yml", [CtInvocationImpl][CtFieldReadImpl]towny.getVersion());
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.loadLanguage([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]rootFolder + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"settings", [CtLiteralImpl]"english.yml");
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.permissions.TownyPerms.loadPerms([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]rootFolder + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"settings", [CtLiteralImpl]"townyperms.yml");
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.TownyException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtCommentImpl]// Init logger
        [CtTypeAccessImpl]com.palmergames.bukkit.towny.TownyLogger.getInstance();
        [CtAssignmentImpl][CtFieldWriteImpl]saveDbType = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.getSaveDatabase();
        [CtAssignmentImpl][CtFieldWriteImpl]loadDbType = [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.getLoadDatabase();
        [CtInvocationImpl][CtCommentImpl]// Setup any defaults before we load the dataSource.
        [CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.setCellSize([CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.getTownBlockSize());
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"[Towny] Database: [Load] " + [CtFieldReadImpl]loadDbType) + [CtLiteralImpl]" [Save] ") + [CtFieldReadImpl]saveDbType);
        [CtInvocationImpl]clearAll();
        [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]loadDatabase([CtFieldReadImpl]loadDbType)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"[Towny] Error: Failed to load!");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long time = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtVariableReadImpl]startTime;
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"[Towny] Database loaded in " + [CtVariableReadImpl]time) + [CtLiteralImpl]"ms.");
        [CtTryImpl]try [CtBlockImpl]{
            [CtSwitchImpl][CtCommentImpl]// Set the new class for saving.
            switch ([CtInvocationImpl][CtFieldReadImpl]saveDbType.toLowerCase()) {
                [CtCaseImpl]case [CtLiteralImpl]"ff" :
                [CtCaseImpl]case [CtLiteralImpl]"flatfile" :
                    [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownyFlatFileSource([CtFieldReadImpl]towny, [CtThisAccessImpl]this);
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtLiteralImpl]"h2" :
                [CtCaseImpl]case [CtLiteralImpl]"sqlite" :
                [CtCaseImpl]case [CtLiteralImpl]"mysql" :
                    [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownySQLSource([CtFieldReadImpl]towny, [CtThisAccessImpl]this, [CtInvocationImpl][CtFieldReadImpl]saveDbType.toLowerCase());
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]default :
                    [CtBlockImpl]{
                    }
            }
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.util.FileMgmt.checkOrCreateFolder([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]rootFolder + [CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator) + [CtLiteralImpl]"logs");[CtCommentImpl]// Setup the logs folder here as the logger will not yet be enabled.

            [CtInvocationImpl][CtCommentImpl]// Run both the backup cleanup and backup async.
            [CtInvocationImpl][CtTypeAccessImpl]java.util.concurrent.CompletableFuture.runAsync([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.tasks.CleanupBackupTask()).thenRunAsync([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.tasks.BackupTask());
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]loadDbType.equalsIgnoreCase([CtFieldReadImpl]saveDbType)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Update all Worlds data files
                [CtFieldReadImpl]dataSource.saveAllWorlds();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Formats are different so save ALL data.
                [CtFieldReadImpl]dataSource.saveAll();
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.UnsupportedOperationException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"[Towny] Error: Unsupported save format!");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File f = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]rootFolder, [CtLiteralImpl]"outpostschecked.txt");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]f.exists()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town town : [CtInvocationImpl][CtFieldReadImpl]dataSource.getTowns()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.db.TownySQLSource.validateTownOutposts([CtVariableReadImpl]town);
            }
            [CtInvocationImpl][CtFieldReadImpl]towny.saveResource([CtLiteralImpl]"outpostschecked.txt", [CtLiteralImpl]false);
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean loadDatabase([CtParameterImpl][CtTypeReferenceImpl]java.lang.String loadDbType) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]loadDbType.toLowerCase()) {
            [CtCaseImpl]case [CtLiteralImpl]"ff" :
            [CtCaseImpl]case [CtLiteralImpl]"flatfile" :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownyFlatFileSource([CtFieldReadImpl]towny, [CtThisAccessImpl]this);
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]case [CtLiteralImpl]"h2" :
            [CtCaseImpl]case [CtLiteralImpl]"sqlite" :
            [CtCaseImpl]case [CtLiteralImpl]"mysql" :
                [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataSource = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownySQLSource([CtFieldReadImpl]towny, [CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]loadDbType.toLowerCase());
                    [CtBreakImpl]break;
                }
            [CtCaseImpl]default :
                [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]dataSource.loadAll();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void startWarEvent() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]warEvent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.war.eventwar.War([CtFieldReadImpl]towny, [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownySettings.getWarTimeWarningDelay());
    }

    [CtMethodImpl][CtCommentImpl]// TODO: This actually breaks the design pattern, so I might just redo warEvent to never be null.
    [CtCommentImpl]// TODO for: Articdive
    public [CtTypeReferenceImpl]void endWarEvent() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]warEvent != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]warEvent.isWarTime()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]warEvent.toggleEnd();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addWarZone([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCoord.getTownyWorld().isWarAllowed())[CtBlockImpl]
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCoord.getTownyWorld().addWarZone([CtVariableReadImpl]worldCoord);

        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtCommentImpl]// Not a registered world
        }
        [CtInvocationImpl][CtFieldReadImpl]towny.updateCache([CtVariableReadImpl]worldCoord);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeWarZone([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]worldCoord.getTownyWorld().removeWarZone([CtVariableReadImpl]worldCoord);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtCommentImpl]// Not a registered world
        }
        [CtInvocationImpl][CtFieldReadImpl]towny.updateCache([CtVariableReadImpl]worldCoord);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.permissions.TownyPermissionSource getPermissionSource() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]permissionSource;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPermissionSource([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.permissions.TownyPermissionSource permissionSource) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.permissionSource = [CtVariableReadImpl]permissionSource;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.war.eventwar.War getWarEvent() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]warEvent;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setWarEvent([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.war.eventwar.War warEvent) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.warEvent = [CtVariableReadImpl]warEvent;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getRootFolder() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]rootFolder;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Nation> getNationsMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]nations;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.util.Trie getNationsTrie() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]nationsTrie;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident> getResidentMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]residents;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.util.Trie getResidentsTrie() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]residentsTrie;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident> getJailedResidentMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]jailedResidents;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town> getTownsMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]towns;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.util.Trie getTownsTrie() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]townsTrie;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld> getWorldMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]worlds;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.db.TownyDataSource getDataSource() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]dataSource;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getTreeString([CtParameterImpl][CtTypeReferenceImpl]int depth) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtInvocationImpl]getTreeDepth([CtVariableReadImpl]depth) + [CtLiteralImpl]"Universe (1)");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]towny != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1) + [CtLiteralImpl]"Server (") + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.BukkitTools.getServer().getName()) + [CtLiteralImpl]")");
            [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2) + [CtLiteralImpl]"Version: ") + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.BukkitTools.getServer().getVersion());
            [CtInvocationImpl][CtCommentImpl]// out.add(getTreeDepth(depth + 2) + "Players: " + BukkitTools.getOnlinePlayers().length + "/" + BukkitTools.getServer().getMaxPlayers());
            [CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2) + [CtLiteralImpl]"Worlds (") + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.BukkitTools.getWorlds().size()) + [CtLiteralImpl]"): ") + [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.toString([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.util.BukkitTools.getWorlds().toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]org.bukkit.World[[CtLiteralImpl]0])));
        }
        [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1) + [CtLiteralImpl]"Worlds (") + [CtInvocationImpl][CtFieldReadImpl]worlds.size()) + [CtLiteralImpl]"):");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownyWorld world : [CtInvocationImpl][CtFieldReadImpl]worlds.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.addAll([CtInvocationImpl][CtVariableReadImpl]world.getTreeString([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2));
        }
        [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1) + [CtLiteralImpl]"Nations (") + [CtInvocationImpl][CtFieldReadImpl]nations.size()) + [CtLiteralImpl]"):");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Nation nation : [CtInvocationImpl][CtFieldReadImpl]nations.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.addAll([CtInvocationImpl][CtVariableReadImpl]nation.getTreeString([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town> townsWithoutNation = [CtInvocationImpl][CtFieldReadImpl]dataSource.getTownsWithoutNation();
        [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1) + [CtLiteralImpl]"Towns (") + [CtInvocationImpl][CtVariableReadImpl]townsWithoutNation.size()) + [CtLiteralImpl]"):");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town town : [CtVariableReadImpl]townsWithoutNation) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.addAll([CtInvocationImpl][CtVariableReadImpl]town.getTreeString([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident> residentsWithoutTown = [CtInvocationImpl][CtFieldReadImpl]dataSource.getResidentsWithoutTown();
        [CtInvocationImpl][CtVariableReadImpl]out.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTreeDepth([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]1) + [CtLiteralImpl]"Residents (") + [CtInvocationImpl][CtVariableReadImpl]residentsWithoutTown.size()) + [CtLiteralImpl]"):");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Resident resident : [CtVariableReadImpl]residentsWithoutTown) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]out.addAll([CtInvocationImpl][CtVariableReadImpl]resident.getTreeString([CtBinaryOperatorImpl][CtVariableReadImpl]depth + [CtLiteralImpl]2));
        }
        [CtReturnImpl]return [CtVariableReadImpl]out;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getTreeDepth([CtParameterImpl][CtTypeReferenceImpl]int depth) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]char[] fill = [CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtBinaryOperatorImpl][CtVariableReadImpl]depth * [CtLiteralImpl]4];
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.fill([CtVariableReadImpl]fill, [CtLiteralImpl]' ');
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]depth > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]fill[[CtLiteralImpl]0] = [CtLiteralImpl]'|';
            [CtLocalVariableImpl][CtTypeReferenceImpl]int offset = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]depth - [CtLiteralImpl]1) * [CtLiteralImpl]4;
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]fill[[CtVariableReadImpl]offset] = [CtLiteralImpl]'+';
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]fill[[CtBinaryOperatorImpl][CtVariableReadImpl]offset + [CtLiteralImpl]1] = [CtLiteralImpl]'-';
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]fill[[CtBinaryOperatorImpl][CtVariableReadImpl]offset + [CtLiteralImpl]2] = [CtLiteralImpl]'-';
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]fill);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Pretty much this method checks if a townblock is contained within a list of locations.
     *
     * @param minecraftcoordinates
     * 		- List of minecraft coordinates you should probably parse town.getAllOutpostSpawns()
     * @param tb
     * 		- TownBlock to check if its contained..
     * @return true if the TownBlock is considered an outpost by it's Town.
     * @author Lukas Mansour (Articdive)
     */
    public [CtTypeReferenceImpl]boolean isTownBlockLocContainedInTownOutposts([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bukkit.Location> minecraftcoordinates, [CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock tb) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]minecraftcoordinates != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]tb != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Location minecraftcoordinate : [CtVariableReadImpl]minecraftcoordinates) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.object.Coord.parseCoord([CtVariableReadImpl]minecraftcoordinate).equals([CtInvocationImpl][CtVariableReadImpl]tb.getCoord())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;[CtCommentImpl]// Yes the TownBlock is considered an outpost by the Town

                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addCustomCustomDataField([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.metadata.CustomDataField cdf) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.KeyAlreadyRegisteredException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getRegisteredMetadataMap().containsKey([CtInvocationImpl][CtVariableReadImpl]cdf.getKey()))[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.KeyAlreadyRegisteredException();

        [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getRegisteredMetadataMap().put([CtInvocationImpl][CtVariableReadImpl]cdf.getKey(), [CtVariableReadImpl]cdf);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.palmergames.bukkit.towny.TownyUniverse getInstance() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.palmergames.bukkit.towny.TownyUniverse.instance == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.palmergames.bukkit.towny.TownyUniverse.instance = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.TownyUniverse();
        }
        [CtReturnImpl]return [CtFieldReadImpl]com.palmergames.bukkit.towny.TownyUniverse.instance;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clearAll() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]worlds.clear();
        [CtInvocationImpl][CtFieldReadImpl]nations.clear();
        [CtInvocationImpl][CtFieldReadImpl]towns.clear();
        [CtInvocationImpl][CtFieldReadImpl]residents.clear();
        [CtInvocationImpl][CtFieldReadImpl]townBlocks.clear();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String townName, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID groupID) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town t = [CtInvocationImpl][CtFieldReadImpl]towns.get([CtVariableReadImpl]townName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]t.getObjectGroupFromID([CtVariableReadImpl]groupID) != [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String townName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town t = [CtInvocationImpl][CtFieldReadImpl]towns.get([CtVariableReadImpl]townName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]t.hasObjectGroupName([CtVariableReadImpl]groupName);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get all the plot object groups from all towns
     * Returns a collection that does not reflect any group additions/removals
     *
     * @return collection of PlotObjectGroup
     */
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup> getGroups() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup> groups = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town town : [CtInvocationImpl][CtFieldReadImpl]towns.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]town.hasObjectGroups()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]groups.addAll([CtInvocationImpl][CtVariableReadImpl]town.getPlotObjectGroups());
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]groups;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the plot group from the town name and the plot group UUID
     *
     * @param townName
     * 		Town name
     * @param groupID
     * 		UUID of the plot group
     * @return PlotGroup if found, null if none found.
     */
    public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup getGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String townName, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID groupID) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town t = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]t = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownyUniverse.getInstance().getDataSource().getTown([CtVariableReadImpl]townName);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]t.getObjectGroupFromID([CtVariableReadImpl]groupID);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the plot group from the town name and the plot group name
     *
     * @param townName
     * 		Town Name
     * @param groupName
     * 		Plot Group Name
     * @return the plot group if found, otherwise null
     */
    public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup getGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String townName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String groupName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town t = [CtInvocationImpl][CtFieldReadImpl]towns.get([CtVariableReadImpl]townName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]t != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]t.getPlotObjectGroupFromName([CtVariableReadImpl]groupName);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.metadata.CustomDataField> getRegisteredMetadataMap() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getRegisteredMetadata();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup newGroup([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.Town town, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Create new plot group.
        [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup newGroup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup([CtVariableReadImpl]id, [CtVariableReadImpl]name, [CtVariableReadImpl]town);
        [CtIfImpl][CtCommentImpl]// Check if there is a duplicate
        if ([CtInvocationImpl][CtVariableReadImpl]town.hasObjectGroupName([CtInvocationImpl][CtVariableReadImpl]newGroup.getName())) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.palmergames.bukkit.towny.TownyMessaging.sendErrorMsg([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"group " + [CtInvocationImpl][CtVariableReadImpl]town.getName()) + [CtLiteralImpl]":") + [CtVariableReadImpl]id) + [CtLiteralImpl]" already exists");[CtCommentImpl]// FIXME Debug message

            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException();
        }
        [CtInvocationImpl][CtCommentImpl]// Create key and store group globally.
        [CtVariableReadImpl]town.addPlotGroup([CtVariableReadImpl]newGroup);
        [CtReturnImpl]return [CtVariableReadImpl]newGroup;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.UUID generatePlotGroupID() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeGroup([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.PlotGroup group) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]group.getTown().removePlotGroup([CtVariableReadImpl]group);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.metadata.CustomDataField> getRegisteredMetadata() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]registeredMetadata;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * How to get a TownBlock for now.
     *
     * @param worldCoord
     * 		we are testing for a townblock.
     * @return townblock if it exists, otherwise null.
     * @throws NotRegisteredException
     * 		if there is no homeblock to get.
     */
    public [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock getTownBlock([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord) throws [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasTownBlock([CtVariableReadImpl]worldCoord))[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]townBlocks.get([CtVariableReadImpl]worldCoord);
        else[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException();

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get Universe-wide ConcurrentHashMap of WorldCoords and their TownBlocks.
     * Populated at load time from townblocks folder's files.
     *
     * @return townblocks hashmap read from townblock files.
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord, [CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock> getTownBlocks() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]townBlocks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addTownBlock([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock townBlock) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]hasTownBlock([CtInvocationImpl][CtVariableReadImpl]townBlock.getWorldCoord()))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtFieldReadImpl]townBlocks.put([CtInvocationImpl][CtVariableReadImpl]townBlock.getWorldCoord(), [CtVariableReadImpl]townBlock);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Does this WorldCoord have a TownBlock?
     *
     * @param worldCoord
     * 		- the coord for which we want to know if there is a townblock.
     * @return true if Coord is a townblock
     */
    public [CtTypeReferenceImpl]boolean hasTownBlock([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]townBlocks.containsKey([CtVariableReadImpl]worldCoord);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove one townblock from the TownyUniverse townblock map.
     *
     * @param townBlock
     * 		to remove.
     */
    public [CtTypeReferenceImpl]void removeTownBlock([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock townBlock) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]removeTownBlock([CtInvocationImpl][CtVariableReadImpl]townBlock.getWorldCoord())) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]townBlock.hasResident())[CtBlockImpl]
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townBlock.getResident().removeTownBlock([CtVariableReadImpl]townBlock);

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]townBlock.hasTown())[CtBlockImpl]
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]townBlock.getTown().removeTownBlock([CtVariableReadImpl]townBlock);

            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.exceptions.NotRegisteredException e) [CtBlockImpl]{
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a list of TownBlocks from the TownyUniverse townblock map.
     *
     * @param townBlocks
     * 		to remove.
     */
    public [CtTypeReferenceImpl]void removeTownBlocks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock> townBlocks) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.TownBlock townBlock : [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]townBlocks))[CtBlockImpl]
            [CtInvocationImpl]removeTownBlock([CtVariableReadImpl]townBlock);

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Removes a townblock at the given worldCoord from the TownyUniverse townblock map.
     *
     * @param worldCoord
     * 		to remove.
     * @return whether the townblock was successfully removed
     */
    private [CtTypeReferenceImpl]boolean removeTownBlock([CtParameterImpl][CtTypeReferenceImpl]com.palmergames.bukkit.towny.object.WorldCoord worldCoord) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]townBlocks.remove([CtVariableReadImpl]worldCoord) != [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getSaveDbType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]saveDbType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getLoadDbType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]loadDbType;
    }
}
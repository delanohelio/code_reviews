[CompilationUnitImpl][CtPackageDeclarationImpl]package io.github.thebusybiscuit.slimefun4.core.networks.cargo;
[CtUnresolvedImport]import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
[CtUnresolvedImport]import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
[CtUnresolvedImport]import org.bukkit.inventory.BrewerInventory;
[CtUnresolvedImport]import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.bukkit.block.BlockState;
[CtUnresolvedImport]import me.mrCookieSlime.Slimefun.api.BlockStorage;
[CtUnresolvedImport]import org.bukkit.inventory.Inventory;
[CtUnresolvedImport]import org.bukkit.inventory.ItemStack;
[CtUnresolvedImport]import org.bukkit.inventory.InventoryHolder;
[CtUnresolvedImport]import org.bukkit.inventory.FurnaceInventory;
[CtUnresolvedImport]import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
[CtUnresolvedImport]import org.bukkit.block.Block;
[CtUnresolvedImport]import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
[CtUnresolvedImport]import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import me.mrCookieSlime.Slimefun.SlimefunPlugin;
[CtUnresolvedImport]import org.bukkit.Material;
[CtUnresolvedImport]import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
[CtClassImpl]final class CargoUtils {
    [CtFieldImpl][CtCommentImpl]// Whitelist or blacklist slots
    private static final [CtArrayTypeReferenceImpl]int[] SLOTS = [CtNewArrayImpl]new int[]{ [CtLiteralImpl]19, [CtLiteralImpl]20, [CtLiteralImpl]21, [CtLiteralImpl]28, [CtLiteralImpl]29, [CtLiteralImpl]30, [CtLiteralImpl]37, [CtLiteralImpl]38, [CtLiteralImpl]39 };

    [CtConstructorImpl]private CargoUtils() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is a performance-saving shortcut to quickly test whether a given
     * {@link Block} might be an {@link InventoryHolder} or not.
     *
     * @param block
     * 		The {@link Block} to check
     * @return Whether this {@link Block} represents a {@link BlockState} that is an {@link InventoryHolder}
     */
    static [CtTypeReferenceImpl]boolean hasInventory([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block block) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]block == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.Material type = [CtInvocationImpl][CtVariableReadImpl]block.getType();
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case [CtFieldReadImpl]CHEST :
            [CtCaseImpl]case [CtFieldReadImpl]TRAPPED_CHEST :
            [CtCaseImpl]case [CtFieldReadImpl]FURNACE :
            [CtCaseImpl]case [CtFieldReadImpl]DISPENSER :
            [CtCaseImpl]case [CtFieldReadImpl]DROPPER :
            [CtCaseImpl]case [CtFieldReadImpl]HOPPER :
            [CtCaseImpl]case [CtFieldReadImpl]BREWING_STAND :
            [CtCaseImpl]case [CtFieldReadImpl]SHULKER_BOX :
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]default :
                [CtBreakImpl]break;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.name().endsWith([CtLiteralImpl]"_SHULKER_BOX")) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.SlimefunPlugin.getMinecraftVersion().isAtLeast([CtTypeAccessImpl]MinecraftVersion.MINECRAFT_1_14)) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
                [CtCaseImpl]case [CtFieldReadImpl]BARREL :
                [CtCaseImpl]case [CtFieldReadImpl]BLAST_FURNACE :
                [CtCaseImpl]case [CtFieldReadImpl]SMOKER :
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]default :
                    [CtBreakImpl]break;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack withdraw([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block node, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block target, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack template) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu menu = [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.getChestMenu([CtVariableReadImpl]target);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.hasInventory([CtVariableReadImpl]target)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.BlockState state = [CtInvocationImpl][CtVariableReadImpl]target.getState();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.withdrawFromVanillaInventory([CtVariableReadImpl]node, [CtVariableReadImpl]template, [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) (state)).getInventory());
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper wrapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper([CtVariableReadImpl]template);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]menu.getPreset().getSlotsAccessedByItemTransport([CtVariableReadImpl]menu, [CtTypeAccessImpl]ItemTransportFlow.WITHDRAW, [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack is = [CtInvocationImpl][CtVariableReadImpl]menu.getItemInSlot([CtVariableReadImpl]slot);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]is, [CtVariableReadImpl]wrapper, [CtLiteralImpl]true) && [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.matchesFilter([CtVariableReadImpl]node, [CtVariableReadImpl]is, [CtUnaryOperatorImpl]-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]is.getAmount() > [CtInvocationImpl][CtVariableReadImpl]template.getAmount()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]is.setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]is.getAmount() - [CtInvocationImpl][CtVariableReadImpl]template.getAmount());
                    [CtInvocationImpl][CtVariableReadImpl]menu.replaceExistingItem([CtVariableReadImpl]slot, [CtInvocationImpl][CtVariableReadImpl]is.clone());
                    [CtReturnImpl]return [CtVariableReadImpl]template;
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]menu.replaceExistingItem([CtVariableReadImpl]slot, [CtLiteralImpl]null);
                    [CtReturnImpl]return [CtVariableReadImpl]is;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack withdrawFromVanillaInventory([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block node, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack template, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.Inventory inv) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.bukkit.inventory.ItemStack[] contents = [CtInvocationImpl][CtVariableReadImpl]inv.getContents();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minSlot = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxSlot = [CtFieldReadImpl][CtVariableReadImpl]contents.length;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.FurnaceInventory) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]minSlot = [CtLiteralImpl]2;
            [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]3;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.BrewerInventory) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]3;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper wrapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper([CtVariableReadImpl]template);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot = [CtVariableReadImpl]minSlot; [CtBinaryOperatorImpl][CtVariableReadImpl]slot < [CtVariableReadImpl]maxSlot; [CtUnaryOperatorImpl][CtVariableWriteImpl]slot++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Changes to this ItemStack are synchronized with the Item in the Inventory
            [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack itemInSlot = [CtArrayReadImpl][CtVariableReadImpl]contents[[CtVariableReadImpl]slot];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]itemInSlot, [CtVariableReadImpl]wrapper, [CtLiteralImpl]true) && [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.matchesFilter([CtVariableReadImpl]node, [CtVariableReadImpl]itemInSlot, [CtUnaryOperatorImpl]-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]itemInSlot.getAmount() > [CtInvocationImpl][CtVariableReadImpl]template.getAmount()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]itemInSlot.setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]itemInSlot.getAmount() - [CtInvocationImpl][CtVariableReadImpl]template.getAmount());
                    [CtReturnImpl]return [CtVariableReadImpl]template;
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack clone = [CtInvocationImpl][CtVariableReadImpl]itemInSlot.clone();
                    [CtInvocationImpl][CtVariableReadImpl]itemInSlot.setAmount([CtLiteralImpl]0);
                    [CtReturnImpl]return [CtVariableReadImpl]clone;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.ItemStackAndInteger withdraw([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block node, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block target, [CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu menu = [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.getChestMenu([CtVariableReadImpl]target);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]menu.getPreset().getSlotsAccessedByItemTransport([CtVariableReadImpl]menu, [CtTypeAccessImpl]ItemTransportFlow.WITHDRAW, [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack is = [CtInvocationImpl][CtVariableReadImpl]menu.getItemInSlot([CtVariableReadImpl]slot);
                [CtIfImpl]if ([CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.matchesFilter([CtVariableReadImpl]node, [CtVariableReadImpl]is, [CtVariableReadImpl]index)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]menu.replaceExistingItem([CtVariableReadImpl]slot, [CtLiteralImpl]null);
                    [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.ItemStackAndInteger([CtVariableReadImpl]is, [CtVariableReadImpl]slot);
                }
            }
        } else [CtIfImpl]if ([CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.hasInventory([CtVariableReadImpl]target)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.BlockState state = [CtInvocationImpl][CtVariableReadImpl]target.getState();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.Inventory inv = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) (state)).getInventory();
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.bukkit.inventory.ItemStack[] contents = [CtInvocationImpl][CtVariableReadImpl]inv.getContents();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int minSlot = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int maxSlot = [CtFieldReadImpl][CtVariableReadImpl]contents.length;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.FurnaceInventory) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]minSlot = [CtLiteralImpl]2;
                    [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]3;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.BrewerInventory) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]3;
                }
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot = [CtVariableReadImpl]minSlot; [CtBinaryOperatorImpl][CtVariableReadImpl]slot < [CtVariableReadImpl]maxSlot; [CtUnaryOperatorImpl][CtVariableWriteImpl]slot++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack is = [CtArrayReadImpl][CtVariableReadImpl]contents[[CtVariableReadImpl]slot];
                    [CtIfImpl]if ([CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.matchesFilter([CtVariableReadImpl]node, [CtVariableReadImpl]is, [CtVariableReadImpl]index)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]inv.setItem([CtVariableReadImpl]slot, [CtLiteralImpl]null);
                        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.ItemStackAndInteger([CtVariableReadImpl]is, [CtVariableReadImpl]slot);
                    }
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack insert([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block node, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block target, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack stack, [CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.matchesFilter([CtVariableReadImpl]node, [CtVariableReadImpl]stack, [CtVariableReadImpl]index))[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]stack;

        [CtLocalVariableImpl][CtTypeReferenceImpl]me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu menu = [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.getChestMenu([CtVariableReadImpl]target);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.hasInventory([CtVariableReadImpl]target)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.block.BlockState state = [CtInvocationImpl][CtVariableReadImpl]target.getState();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]state instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.insertIntoVanillaInventory([CtVariableReadImpl]stack, [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.bukkit.inventory.InventoryHolder) (state)).getInventory());
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]stack;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper wrapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper([CtVariableReadImpl]stack);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]menu.getPreset().getSlotsAccessedByItemTransport([CtVariableReadImpl]menu, [CtTypeAccessImpl]ItemTransportFlow.INSERT, [CtVariableReadImpl]stack)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack itemInSlot = [CtInvocationImpl][CtVariableReadImpl]menu.getItemInSlot([CtVariableReadImpl]slot);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]itemInSlot == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]menu.replaceExistingItem([CtVariableReadImpl]slot, [CtVariableReadImpl]stack);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int maxStackSize = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]itemInSlot.getType().getMaxStackSize();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int currentAmount = [CtInvocationImpl][CtVariableReadImpl]itemInSlot.getAmount();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]itemInSlot, [CtVariableReadImpl]wrapper, [CtLiteralImpl]true, [CtLiteralImpl]false) && [CtBinaryOperatorImpl]([CtVariableReadImpl]currentAmount < [CtVariableReadImpl]maxStackSize)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int amount = [CtBinaryOperatorImpl][CtVariableReadImpl]currentAmount + [CtInvocationImpl][CtVariableReadImpl]stack.getAmount();
                [CtInvocationImpl][CtVariableReadImpl]itemInSlot.setAmount([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]amount, [CtVariableReadImpl]maxStackSize));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]amount > [CtVariableReadImpl]maxStackSize) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]stack.setAmount([CtBinaryOperatorImpl][CtVariableReadImpl]amount - [CtVariableReadImpl]maxStackSize);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]stack = [CtLiteralImpl]null;
                }
                [CtInvocationImpl][CtVariableReadImpl]menu.replaceExistingItem([CtVariableReadImpl]slot, [CtVariableReadImpl]itemInSlot);
                [CtReturnImpl]return [CtVariableReadImpl]stack;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]stack;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack insertIntoVanillaInventory([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack stack, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.Inventory inv) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]org.bukkit.inventory.ItemStack[] contents = [CtInvocationImpl][CtVariableReadImpl]inv.getContents();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minSlot = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxSlot = [CtFieldReadImpl][CtVariableReadImpl]contents.length;
        [CtIfImpl][CtCommentImpl]// Check if it is a normal furnace
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.FurnaceInventory) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Check if it is fuel or not
            if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stack.getType().isFuel()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]minSlot = [CtLiteralImpl]1;
                [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]2;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]1;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inv instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.bukkit.inventory.BrewerInventory) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]stack.getType() == [CtFieldReadImpl]org.bukkit.Material.POTION) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]stack.getType() == [CtFieldReadImpl]org.bukkit.Material.LINGERING_POTION)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]stack.getType() == [CtFieldReadImpl]org.bukkit.Material.SPLASH_POTION)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Potions slot
                [CtVariableWriteImpl]maxSlot = [CtLiteralImpl]3;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]stack.getType() == [CtFieldReadImpl]org.bukkit.Material.BLAZE_POWDER) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Blaze Powder slot
                [CtVariableWriteImpl]minSlot = [CtLiteralImpl]4;
                [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]5;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Input slot
                [CtVariableWriteImpl]minSlot = [CtLiteralImpl]3;
                [CtAssignmentImpl][CtVariableWriteImpl]maxSlot = [CtLiteralImpl]4;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper wrapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper([CtVariableReadImpl]stack);
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot = [CtVariableReadImpl]minSlot; [CtBinaryOperatorImpl][CtVariableReadImpl]slot < [CtVariableReadImpl]maxSlot; [CtUnaryOperatorImpl][CtVariableWriteImpl]slot++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Changes to this ItemStack are synchronized with the Item in the Inventory
            [CtTypeReferenceImpl]org.bukkit.inventory.ItemStack itemInSlot = [CtArrayReadImpl][CtVariableReadImpl]contents[[CtVariableReadImpl]slot];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]itemInSlot == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]inv.setItem([CtVariableReadImpl]slot, [CtVariableReadImpl]stack);
                [CtReturnImpl]return [CtLiteralImpl]null;
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int maxStackSize = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]itemInSlot.getType().getMaxStackSize();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]itemInSlot, [CtVariableReadImpl]wrapper, [CtLiteralImpl]true, [CtLiteralImpl]false) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]itemInSlot.getAmount() < [CtVariableReadImpl]maxStackSize)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int amount = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]itemInSlot.getAmount() + [CtInvocationImpl][CtVariableReadImpl]stack.getAmount();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]amount > [CtVariableReadImpl]maxStackSize) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]stack.setAmount([CtBinaryOperatorImpl][CtVariableReadImpl]amount - [CtVariableReadImpl]maxStackSize);
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]stack = [CtLiteralImpl]null;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]itemInSlot.setAmount([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]amount, [CtVariableReadImpl]maxStackSize));
                    [CtInvocationImpl][CtCommentImpl]// Setting item in inventory will clone the ItemStack
                    [CtVariableReadImpl]inv.setItem([CtVariableReadImpl]slot, [CtVariableReadImpl]itemInSlot);
                    [CtReturnImpl]return [CtVariableReadImpl]stack;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]stack;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu getChestMenu([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block block) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.hasInventory([CtVariableReadImpl]block)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.getInventory([CtVariableReadImpl]block);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.getUniversalInventory([CtVariableReadImpl]block);
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean matchesFilter([CtParameterImpl][CtTypeReferenceImpl]org.bukkit.block.Block block, [CtParameterImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack item, [CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]item == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]item.getType() == [CtFieldReadImpl]org.bukkit.Material.AIR))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtCommentImpl]// Store the returned Config instance to avoid heavy calls
        [CtTypeReferenceImpl]me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config blockInfo = [CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.getLocationInfo([CtInvocationImpl][CtVariableReadImpl]block.getLocation());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]blockInfo.getString([CtLiteralImpl]"id").equals([CtLiteralImpl]"CARGO_NODE_OUTPUT"))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtLocalVariableImpl][CtTypeReferenceImpl]me.mrCookieSlime.Slimefun.api.inventory.BlockMenu menu = [CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.getInventory([CtInvocationImpl][CtVariableReadImpl]block.getLocation());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menu == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean lore = [CtInvocationImpl][CtLiteralImpl]"true".equals([CtInvocationImpl][CtVariableReadImpl]blockInfo.getString([CtLiteralImpl]"filter-lore"));
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"whitelist".equals([CtInvocationImpl][CtVariableReadImpl]blockInfo.getString([CtLiteralImpl]"filter-type"))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.bukkit.inventory.ItemStack> templateItems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot : [CtFieldReadImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.SLOTS) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack template = [CtInvocationImpl][CtVariableReadImpl]menu.getItemInSlot([CtVariableReadImpl]slot);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]template != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]templateItems.add([CtVariableReadImpl]template);
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]templateItems.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index >= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]index++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]index > [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]templateItems.size() - [CtLiteralImpl]1))[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]index = [CtLiteralImpl]0;

                [CtInvocationImpl][CtCommentImpl]// Should probably replace this with a simple HashMap.
                [CtVariableReadImpl]blockInfo.setValue([CtLiteralImpl]"index", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]index));
                [CtInvocationImpl][CtTypeAccessImpl]me.mrCookieSlime.Slimefun.api.BlockStorage.setBlockInfo([CtVariableReadImpl]block, [CtVariableReadImpl]blockInfo, [CtLiteralImpl]false);
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]item, [CtInvocationImpl][CtVariableReadImpl]templateItems.get([CtVariableReadImpl]index), [CtVariableReadImpl]lore);
            } else [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack stack : [CtVariableReadImpl]templateItems) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]item, [CtVariableReadImpl]stack, [CtVariableReadImpl]lore)) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    }
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        } else [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int slot : [CtFieldReadImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.SLOTS) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.bukkit.inventory.ItemStack itemInSlot = [CtInvocationImpl][CtVariableReadImpl]menu.getItemInSlot([CtVariableReadImpl]slot);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]itemInSlot != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils.isItemSimilar([CtVariableReadImpl]item, [CtVariableReadImpl]itemInSlot, [CtVariableReadImpl]lore, [CtLiteralImpl]false)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the whitelist/blacklist slots in a Cargo Input Node. If you wish to access the items
     * in the cargo (without hardcoding the slots in case of change) then you can use this method.
     *
     * @return The slot indexes for the whutelist/blacklist section.
     */
    public static [CtArrayTypeReferenceImpl]int[] getSlots() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoUtils.SLOTS;
    }
}
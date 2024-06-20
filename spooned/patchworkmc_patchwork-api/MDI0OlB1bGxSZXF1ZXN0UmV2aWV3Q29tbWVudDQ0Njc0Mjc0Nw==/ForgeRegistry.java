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
[CtPackageDeclarationImpl]package net.minecraftforge.registries;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import net.patchworkmc.impl.registries.ForgeModDefaultRegistry;
[CtUnresolvedImport]import net.minecraft.util.registry.Registry;
[CtUnresolvedImport]import net.minecraft.util.registry.DefaultedRegistry;
[CtUnresolvedImport]import org.apache.logging.log4j.MarkerManager;
[CtUnresolvedImport]import org.apache.logging.log4j.Logger;
[CtUnresolvedImport]import net.patchworkmc.impl.registries.RemovableRegistry;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Iterator;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import net.minecraft.util.Identifier;
[CtUnresolvedImport]import net.minecraft.util.registry.MutableRegistry;
[CtUnresolvedImport]import net.patchworkmc.impl.registries.ForgeModRegistry;
[CtUnresolvedImport]import net.patchworkmc.impl.registries.VanillaRegistry;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import org.apache.logging.log4j.LogManager;
[CtUnresolvedImport]import org.apache.logging.log4j.Marker;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
[CtClassImpl]public class ForgeRegistry<[CtTypeParameterImpl]V extends [CtTypeReferenceImpl]net.minecraftforge.registries.IForgeRegistryEntry<[CtTypeParameterReferenceImpl]V>> implements [CtTypeReferenceImpl]net.minecraftforge.registries.IForgeRegistryModifiable<[CtTypeParameterReferenceImpl]V> , [CtTypeReferenceImpl]net.minecraftforge.registries.IForgeRegistryInternal<[CtTypeParameterReferenceImpl]V> , [CtTypeReferenceImpl]net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback<[CtTypeParameterReferenceImpl]V> {
    [CtFieldImpl]public static [CtTypeReferenceImpl]org.apache.logging.log4j.Marker REGISTRIES = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.MarkerManager.getMarker([CtLiteralImpl]"REGISTRIES");

    [CtFieldImpl]private static [CtTypeReferenceImpl]org.apache.logging.log4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.LogManager.getLogger();

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.util.Identifier name;[CtCommentImpl]// The forge name


    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isVanilla;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraft.util.registry.Registry<[CtTypeParameterReferenceImpl]V> vanilla;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]V> superType;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]net.minecraft.util.Identifier, [CtWildcardReferenceImpl]?> slaves = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraftforge.registries.CreateCallback<[CtTypeParameterReferenceImpl]V> create;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraftforge.registries.AddCallback<[CtTypeParameterReferenceImpl]V> add;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraftforge.registries.ClearCallback<[CtTypeParameterReferenceImpl]V> clear;

    [CtFieldImpl]private final [CtTypeReferenceImpl]net.minecraftforge.registries.RegistryManager stage;

    [CtFieldImpl]public final [CtTypeReferenceImpl]int min;

    [CtFieldImpl]public final [CtTypeReferenceImpl]int max;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean allowOverrides;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean isModifiable;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isFrozen = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeParameterReferenceImpl]V oldValue;[CtCommentImpl]// context of AddCallback, is not used elsewhere


    [CtConstructorImpl][CtJavaDocImpl]/**
     * Called by RegistryBuilder, for modded registries.
     *
     * @param stage
     * @param name
     * 		the forge name
     * @param builder
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    protected ForgeRegistry([CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.registries.RegistryManager stage, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier name, [CtParameterImpl][CtTypeReferenceImpl]net.minecraftforge.registries.RegistryBuilder<[CtTypeParameterReferenceImpl]V> builder) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.stage = [CtVariableReadImpl]stage;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.superType = [CtInvocationImpl][CtVariableReadImpl]builder.getType();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.min = [CtInvocationImpl][CtVariableReadImpl]builder.getMinId();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.max = [CtInvocationImpl][CtVariableReadImpl]builder.getMaxId();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.create = [CtInvocationImpl][CtVariableReadImpl]builder.getCreate();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.add = [CtInvocationImpl][CtVariableReadImpl]builder.getAdd();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.clear = [CtInvocationImpl][CtVariableReadImpl]builder.getClear();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.allowOverrides = [CtInvocationImpl][CtVariableReadImpl]builder.getAllowOverrides();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isModifiable = [CtInvocationImpl][CtVariableReadImpl]builder.getAllowModifications();
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.registry.Registry<[CtTypeParameterReferenceImpl]V> vanilla = [CtInvocationImpl][CtVariableReadImpl]builder.getVanillaRegistry();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]vanilla == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Forge modded registry
            [CtTypeReferenceImpl]net.minecraft.util.Identifier defaultKey = [CtInvocationImpl][CtVariableReadImpl]builder.getDefault();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.vanilla = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]defaultKey == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.patchworkmc.impl.registries.ForgeModRegistry<>([CtThisAccessImpl]this, [CtVariableReadImpl]builder) : [CtConstructorCallImpl]new [CtTypeReferenceImpl]net.patchworkmc.impl.registries.ForgeModDefaultRegistry<>([CtThisAccessImpl]this, [CtVariableReadImpl]builder);
            [CtInvocationImpl][CtTypeAccessImpl]Registry.REGISTRIES.add([CtVariableReadImpl]name, [CtFieldReadImpl](([CtTypeReferenceImpl]net.minecraft.util.registry.MutableRegistry) ([CtThisAccessImpl]this.vanilla)));
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isVanilla = [CtLiteralImpl]false;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Vanilla registry
            [CtFieldWriteImpl][CtThisAccessImpl]this.vanilla = [CtVariableReadImpl]vanilla;
            [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]net.patchworkmc.impl.registries.VanillaRegistry) ([CtThisAccessImpl]this.vanilla)).patchwork$setForgeRegistry([CtThisAccessImpl]this);
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isVanilla = [CtLiteralImpl]true;
            [CtInvocationImpl][CtCommentImpl]// Set the slave map for compatibility
            [CtThisAccessImpl]this.setSlaveMap([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraft.util.Identifier([CtLiteralImpl]"forge", [CtLiteralImpl]"registry_defaulted_wrapper"), [CtVariableReadImpl]vanilla);
        }
        [CtIfImpl][CtCommentImpl]// Fabric hooks
        [CtCommentImpl]// TODO: Some vanilla registry types are not patched yet, add this check to avoid crash
        if ([CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.IForgeRegistryEntry.class.isAssignableFrom([CtFieldReadImpl][CtThisAccessImpl]this.superType)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback.event([CtFieldReadImpl][CtThisAccessImpl]this.vanilla).register([CtThisAccessImpl]this);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.create != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.create.onCreate([CtThisAccessImpl]this, [CtVariableReadImpl]stage);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onEntryAdded([CtParameterImpl][CtTypeReferenceImpl]int rawId, [CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier id, [CtParameterImpl][CtTypeParameterReferenceImpl]V newValue) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isLocked()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The object %s (name %s) is being added too late.", [CtVariableReadImpl]newValue, [CtVariableReadImpl]id));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.add != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.add.onAdd([CtThisAccessImpl]this, [CtFieldReadImpl][CtThisAccessImpl]this.stage, [CtVariableReadImpl]rawId, [CtVariableReadImpl]newValue, [CtFieldReadImpl][CtThisAccessImpl]this.oldValue);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]net.minecraft.util.Identifier getRegistryName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;[CtCommentImpl]// The forge name of registry

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]V> getRegistrySuperType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]superType;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void register([CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]value, [CtLiteralImpl]"value must not be null");
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier identifier = [CtInvocationImpl][CtVariableReadImpl]value.getRegistryName();
        [CtIfImpl]if ([CtInvocationImpl]isLocked()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The object %s (name %s) is being added too late.", [CtVariableReadImpl]value, [CtVariableReadImpl]identifier));
        }
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V potentialOldValue = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]vanilla.getOrEmpty([CtVariableReadImpl]identifier).orElse([CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOldValue != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]potentialOldValue == [CtVariableReadImpl]value) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.LOGGER.warn([CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.REGISTRIES, [CtLiteralImpl]"Registry {}: The object {} has been registered twice for the same name {}.", [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.superType.getSimpleName(), [CtVariableReadImpl]value, [CtVariableReadImpl]identifier);
                [CtReturnImpl]return;
            } else [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.allowOverrides) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.oldValue = [CtVariableReadImpl]potentialOldValue;
                [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.LOGGER.debug([CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.REGISTRIES, [CtLiteralImpl]"Registry {}: The object {} {} has been overridden by {}.", [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.superType.getSimpleName(), [CtVariableReadImpl]identifier, [CtVariableReadImpl]potentialOldValue, [CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The name %s has been registered twice, for %s and %s.", [CtVariableReadImpl]identifier, [CtVariableReadImpl]potentialOldValue, [CtVariableReadImpl]value));
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.oldValue = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier oldIdentifier = [CtInvocationImpl][CtFieldReadImpl]vanilla.getId([CtVariableReadImpl]value);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]oldIdentifier != [CtInvocationImpl]getDefaultKey()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"The object %s{%x} has been registered twice, using the names %s and %s.", [CtVariableReadImpl]value, [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.identityHashCode([CtVariableReadImpl]value), [CtVariableReadImpl]oldIdentifier, [CtVariableReadImpl]identifier));
        }
        [CtInvocationImpl][CtTypeAccessImpl]net.minecraft.util.registry.Registry.register([CtFieldReadImpl]vanilla, [CtVariableReadImpl]identifier, [CtVariableReadImpl]value);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.oldValue = [CtLiteralImpl]null;[CtCommentImpl]// Clear the onAddEntry context

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void registerAll([CtParameterImpl]V... values) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeParameterReferenceImpl]V value : [CtVariableReadImpl]values) [CtBlockImpl]{
            [CtInvocationImpl]register([CtVariableReadImpl]value);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsKey([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]vanilla.containsId([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsValue([CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]vanilla.getId([CtVariableReadImpl]value) != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isEmpty() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]vanilla.getIds().isEmpty();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]V getValue([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier key) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]vanilla.get([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]net.minecraft.util.Identifier getKey([CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]vanilla.getId([CtVariableReadImpl]value);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]net.minecraft.util.Identifier getDefaultKey() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]vanilla instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]net.minecraft.util.registry.DefaultedRegistry) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]net.minecraft.util.registry.DefaultedRegistry<[CtTypeParameterReferenceImpl]V>) (vanilla)).getDefaultId();
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]net.minecraft.util.Identifier> getKeys() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]vanilla.getIds();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeParameterReferenceImpl]V> getValues() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]vanilla.stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toCollection([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.ArrayList::new));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]net.minecraft.util.Identifier, [CtTypeParameterReferenceImpl]V>> getEntries() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]net.minecraft.util.Identifier, [CtTypeParameterReferenceImpl]V>> entries = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier identifier : [CtInvocationImpl][CtFieldReadImpl]vanilla.getIds()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]entries.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]net.minecraftforge.registries.ForgeRegistry.Entry<>([CtVariableReadImpl]identifier, [CtInvocationImpl][CtFieldReadImpl]vanilla.get([CtVariableReadImpl]identifier)));
        }
        [CtReturnImpl]return [CtVariableReadImpl]entries;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@javax.annotation.Nonnull
    public [CtTypeReferenceImpl]java.util.Iterator<[CtTypeParameterReferenceImpl]V> iterator() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]vanilla.stream().iterator();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String type = [CtConditionalImpl]([CtFieldReadImpl][CtThisAccessImpl]this.isVanilla) ? [CtLiteralImpl]"Vanilla" : [CtLiteralImpl]"Mod";
        [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier vanillaId = [CtInvocationImpl][CtTypeAccessImpl]RegistryManager.ACTIVE.getVanillaRegistryId([CtFieldReadImpl][CtThisAccessImpl]this.name);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String vanillaName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]vanillaId.equals([CtFieldReadImpl][CtThisAccessImpl]this.name)) ? [CtLiteralImpl]"" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(" + [CtInvocationImpl][CtVariableReadImpl]vanillaId.toString()) + [CtLiteralImpl]")";
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type + [CtLiteralImpl]", ") + [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.name.toString()) + [CtVariableReadImpl]vanillaName;
    }

    [CtClassImpl]private static class Entry<[CtTypeParameterImpl]V> implements [CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]net.minecraft.util.Identifier, [CtTypeParameterReferenceImpl]V> {
        [CtFieldImpl]private [CtTypeReferenceImpl]net.minecraft.util.Identifier identifier;

        [CtFieldImpl]private [CtTypeParameterReferenceImpl]V value;

        [CtConstructorImpl]private Entry([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier identifier, [CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.identifier = [CtVariableReadImpl]identifier;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.value = [CtVariableReadImpl]value;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]net.minecraft.util.Identifier getKey() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]identifier;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeParameterReferenceImpl]V getValue() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]value;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeParameterReferenceImpl]V setValue([CtParameterImpl][CtTypeParameterReferenceImpl]V value) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"Cannot update a registry entry in place yet!");
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"rawtypes")
        public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o == [CtThisAccessImpl]this) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]net.minecraftforge.registries.ForgeRegistry.Entry)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]net.minecraftforge.registries.ForgeRegistry.Entry e = [CtVariableReadImpl](([CtTypeReferenceImpl]net.minecraftforge.registries.ForgeRegistry.Entry) (o));
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]identifier.equals([CtFieldReadImpl][CtVariableReadImpl]e.identifier) && [CtInvocationImpl][CtFieldReadImpl]value.equals([CtFieldReadImpl][CtVariableReadImpl]e.value);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]identifier.hashCode() * [CtLiteralImpl]33) + [CtInvocationImpl][CtFieldReadImpl]value.hashCode();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to control the times where people can modify this registry.
     * Users should only ever register things in the Register<?> events!
     */
    public [CtTypeReferenceImpl]void freeze() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isFrozen = [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void unfreeze() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isFrozen = [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isLocked() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.isFrozen;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void clear() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtThisAccessImpl]this.isModifiable) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"Attempted to clear a non-modifiable Forge Registry");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isLocked()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Attempted to clear the registry too late.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.clear != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.clear.onClear([CtThisAccessImpl]this, [CtFieldReadImpl]stage);
        }
        [CtIfImpl][CtCommentImpl]// If it is modifiable, it must be a forge mod registry, vanilla registries do not support clear().
        if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.vanilla instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]net.patchworkmc.impl.registries.RemovableRegistry) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]net.patchworkmc.impl.registries.RemovableRegistry<[CtTypeParameterReferenceImpl]V>) ([CtThisAccessImpl]this.vanilla)).clear();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.LOGGER.error([CtLiteralImpl]"Attempted to clear a non-modifiable or vanilla registry");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeParameterReferenceImpl]V remove([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier key) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtThisAccessImpl]this.isModifiable) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtLiteralImpl]"Attempted to remove from a non-modifiable Forge Registry");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isLocked()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Attempted to remove from the registry too late.");
        }
        [CtLocalVariableImpl][CtCommentImpl]// If it is modifiable, it must be a forge mod registry, vanilla registries do not support remove().
        [CtTypeParameterReferenceImpl]V removed = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.vanilla instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]net.patchworkmc.impl.registries.RemovableRegistry) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]removed = [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]net.patchworkmc.impl.registries.RemovableRegistry<[CtTypeParameterReferenceImpl]V>) ([CtThisAccessImpl]this.vanilla)).remove([CtVariableReadImpl]key);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.LOGGER.error([CtLiteralImpl]"Attempted to clear a non-modifiable or vanilla registry");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]removed != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.LOGGER.trace([CtFieldReadImpl]net.minecraftforge.registries.ForgeRegistry.REGISTRIES, [CtLiteralImpl]"Registry {} remove: {}", [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.superType.getSimpleName(), [CtVariableReadImpl]key);
        }
        [CtReturnImpl]return [CtVariableReadImpl]removed;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@java.lang.Override
    public <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T getSlaveMap([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Class<[CtTypeParameterReferenceImpl]T> type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtFieldReadImpl][CtThisAccessImpl]this.slaves.get([CtVariableReadImpl]name)));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setSlaveMap([CtParameterImpl][CtTypeReferenceImpl]net.minecraft.util.Identifier name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object obj) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl](([CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]net.minecraft.util.Identifier, [CtTypeReferenceImpl]java.lang.Object>) ([CtThisAccessImpl]this.slaves)).put([CtVariableReadImpl]name, [CtVariableReadImpl]obj);
    }
}
[CompilationUnitImpl][CtCommentImpl]/* This file is part of SpongeAPI, licensed under the MIT License (MIT).

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
[CtPackageDeclarationImpl]package org.spongepowered.api.event.block;
[CtUnresolvedImport]import org.spongepowered.api.block.BlockState;
[CtUnresolvedImport]import org.spongepowered.api.util.Direction;
[CtUnresolvedImport]import org.spongepowered.api.item.inventory.ItemStack;
[CtUnresolvedImport]import org.spongepowered.api.block.BlockTypes;
[CtUnresolvedImport]import org.spongepowered.api.world.ServerLocation;
[CtUnresolvedImport]import org.spongepowered.api.event.action.InteractEvent;
[CtUnresolvedImport]import org.spongepowered.api.entity.living.player.Player;
[CtUnresolvedImport]import org.spongepowered.api.event.item.inventory.InteractItemEvent;
[CtUnresolvedImport]import org.spongepowered.api.event.entity.living.AnimateHandEvent;
[CtUnresolvedImport]import org.spongepowered.api.block.BlockSnapshot;
[CtUnresolvedImport]import org.spongepowered.api.util.Tristate;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Base event for all interactions involving a {@link BlockSnapshot} at a
 * {@link ServerLocation}.
 *
 * <p>Note: Any interaction that occurs within {@link BlockTypes#AIR} where the
 * {@link ServerLocation} is not known, will contain a {@link BlockSnapshot#empty()}.</p>
 */
public interface InteractBlockEvent extends [CtTypeReferenceImpl]org.spongepowered.api.event.action.InteractEvent {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link BlockSnapshot}.
     *
     * @return The block snapshot
     */
    [CtTypeReferenceImpl]org.spongepowered.api.block.BlockSnapshot getBlock();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the target "side" of the {@link BlockState} being interacted with
     * or {@link Direction#NONE} if not known.
     *
     * @return An optional containing the side being interacted with or
    {@link Direction#NONE}
     */
    [CtTypeReferenceImpl]org.spongepowered.api.util.Direction getTargetSide();

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * An event where the targeted block is being interacted with the client's
     * "primary" button.
     *
     * <p>This is usually left-click.</p>
     */
    interface Primary extends [CtTypeReferenceImpl]org.spongepowered.api.event.block.InteractBlockEvent {
        [CtInterfaceImpl][CtJavaDocImpl]/**
         * Called when a player starts digging a block.
         *
         * <p>Canceling this will prevent starting to break a block in survival and breaking a block in creative</p>
         */
        interface Start extends [CtTypeReferenceImpl]org.spongepowered.api.event.block.InteractBlockEvent.Primary {}

        [CtInterfaceImpl][CtJavaDocImpl]/**
         * Called when a player cancels digging a block.
         *
         * <p>Canceling this has no effect.</p>
         */
        interface Cancel extends [CtTypeReferenceImpl]org.spongepowered.api.event.block.InteractBlockEvent.Primary {}

        [CtInterfaceImpl][CtJavaDocImpl]/**
         * Called when a player finishes digging a block.
         *
         * <p>Canceling this will prevent breaking a block.</p>
         */
        interface Finish extends [CtTypeReferenceImpl]org.spongepowered.api.event.block.InteractBlockEvent.Primary {}
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * An event where the targeted block is being interacted with the client's
     * "secondary" button.
     *
     * <p>This is usually right-click.</p>
     */
    interface Secondary extends [CtTypeReferenceImpl]org.spongepowered.api.event.block.InteractBlockEvent {
        [CtMethodImpl][CtTypeReferenceImpl]org.spongepowered.api.util.Tristate getOriginalUseItemResult();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Gets the original {@link #getUseBlockResult}.
         *
         * @return The original {@link #getUseBlockResult}
         */
        [CtTypeReferenceImpl]org.spongepowered.api.util.Tristate getOriginalUseBlockResult();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Gets whether the {@link Player#getItemInHand} should be used.
         *
         * <ul>
         * <li>FALSE: The {@link ItemStack} will never be used.</li>
         * <li>UNDEFINED: The {@link ItemStack} will be used if the block fails.
         * </li>
         * <li>TRUE: The {@link ItemStack} will always be used.</li>
         * </ul>
         *
         * <p>Note: These results may differ depending on implementation.</p>
         *
         * @return Whether the {@link Player#getItemInHand} should be used
         */
        [CtTypeReferenceImpl]org.spongepowered.api.util.Tristate getUseItemResult();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Gets whether the interacted {@link BlockSnapshot} should be used.
         *
         * <ul>
         * <li>FALSE: {@link BlockSnapshot} will never be used.</li>
         * <li>UNDEFINED: {@link BlockSnapshot} will be used as normal.</li>
         * <li>TRUE: {@link BlockSnapshot} will always be used.</li>
         * </ul>
         *
         * <p>Note: These results may differ depending on implementation.</p>
         *
         * @return Whether the interacted {@link BlockSnapshot} should be used
         */
        [CtTypeReferenceImpl]org.spongepowered.api.util.Tristate getUseBlockResult();

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets whether the {@link Player#getItemInHand} should be used.
         *
         * <ul>
         * <li>FALSE: The {@link ItemStack} will never be used.</li>
         * <li>UNDEFINED: The {@link ItemStack} will be used if the block fails.
         * </li>
         * <li>TRUE: The {@link ItemStack} will always be used.</li>
         * </ul>
         *
         * <p>Note: These results may differ depending on implementation.</p>
         *
         * @param result
         * 		Whether the {@link Player#getItemInHand} should be used
         */
        [CtTypeReferenceImpl]void setUseItemResult([CtParameterImpl][CtTypeReferenceImpl]org.spongepowered.api.util.Tristate result);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets whether the interacted {@link BlockSnapshot} should be used.
         *
         * <ul>
         * <li>FALSE: {@link BlockSnapshot} will never be used.</li>
         * <li>UNDEFINED: {@link BlockSnapshot} will be used as normal.</li>
         * <li>TRUE: {@link BlockSnapshot} will always be used.</li>
         * </ul>
         *
         * <p>Note: These results may differ depending on implementation.</p>
         *
         * @param result
         * 		Whether the interacted {@link BlockSnapshot} should be
         * 		used
         */
        [CtTypeReferenceImpl]void setUseBlockResult([CtParameterImpl][CtTypeReferenceImpl]org.spongepowered.api.util.Tristate result);
    }
}
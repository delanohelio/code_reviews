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
[CtPackageDeclarationImpl]package org.spongepowered.api.service.placeholder;
[CtUnresolvedImport]import org.spongepowered.api.text.channel.MessageReceiver;
[CtUnresolvedImport]import org.spongepowered.api.text.Text;
[CtUnresolvedImport]import org.spongepowered.api.text.TextRepresentable;
[CtUnresolvedImport]import org.spongepowered.api.util.ResettableBuilder;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.spongepowered.api.Sponge;
[CtUnresolvedImport]import org.spongepowered.api.entity.living.player.Player;
[CtImportImpl]import java.util.UUID;
[CtImportImpl]import java.util.function.Supplier;
[CtUnresolvedImport]import org.checkerframework.checker.nullness.qual.Nullable;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * A {@link TextRepresentable} that can be used in {@link Text} building methods
 * that represents a placeholder in text.
 *
 * <p>A {@link PlaceholderText} is the collection of a {@link PlaceholderParser}
 * along with contextual data, enabling its use in a {@link Text} object.</p>
 *
 * <p>While such placeholders will generally be built from tokenised strings,
 * these objects make no assumption about the format of text templating. Such a
 * system can therefore be used by other templating engines without conforming
 * to a particular standard.</p>
 */
public interface PlaceholderText extends [CtTypeReferenceImpl]org.spongepowered.api.text.TextRepresentable {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a builder for creating {@link PlaceholderText}.
     *
     * @return A {@link Builder}
     */
    static [CtTypeReferenceImpl][CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder builder() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.spongepowered.api.Sponge.getServiceManager().provideUnchecked([CtFieldReadImpl]org.spongepowered.api.service.placeholder.PlaceholderService.class).placeholderBuilder();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link PlaceholderParser} that handles this
     * placeholder.
     *
     * @return The {@link PlaceholderParser}
     */
    [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderParser getParser();

    [CtMethodImpl][CtJavaDocImpl]/**
     * If provided, the {@link MessageReceiver} which to pull information
     * from when building the placeholder text.
     *
     * <p>Examples of how this might affect a placeholder are:</p>
     *
     * <ul>
     *     <li>
     *         For a "name" placeholder that prints out the source's name,
     *         the name would be selected from this source.
     *     </li>
     *     <li>
     *         For a "current world" placeholder that returns a player's
     *         monetary current world, this would pull the balance from the
     *         player.
     *     </li>
     * </ul>
     *
     * <p>It is important to note that the associated source does not
     * necessarily have to be the sender/invoker of a message, nor does it
     * have to be the recipient. The source is selected by the context of
     * builder. It is up to plugins that use such placeholders to be aware
     * of the context of which the placeholder is used.</p>
     *
     * <p>If an invalid {@link MessageReceiver} is provided for the context
     * of the placeholder, then the associated {@link PlaceholderParser} must
     * return a {@link Text#EMPTY}.</p>
     *
     * @return The associated {@link MessageReceiver}, if any.
     */
    [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]org.spongepowered.api.text.channel.MessageReceiver> getAssociatedReceiver();

    [CtMethodImpl][CtJavaDocImpl]/**
     * The variable string passed to this token to provide contextual
     * information.
     *
     * @return The argument, if any
     */
    [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.lang.String> getArgument();

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * A builder for {@link PlaceholderText} objects.
     */
    interface Builder extends [CtTypeReferenceImpl]org.spongepowered.api.util.ResettableBuilder<[CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText, [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder> {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the token that represents a {@link PlaceholderParser} for use
         * in this {@link PlaceholderText}.
         *
         * @param parser
         * 		The {@link PlaceholderParser} to use
         * @return This, for chaining
         */
        [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder setParser([CtParameterImpl][CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderParser parser);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the {@link MessageReceiver} to use as a source of information
         * for this {@link PlaceholderText} to the supplied {@link Player}.
         *
         * @param player
         * 		The player to associate this text with.
         * @return This, for chaining
         * @see PlaceholderText#getAssociatedReceiver()
         */
        default [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder setAssociatedSource([CtParameterImpl][CtTypeReferenceImpl]org.spongepowered.api.entity.living.player.Player player) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uuid = [CtInvocationImpl][CtVariableReadImpl]player.getUniqueId();
            [CtReturnImpl]return [CtInvocationImpl]setAssociatedSource([CtLambdaImpl]() -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.spongepowered.api.Sponge.getServer().getPlayer([CtVariableReadImpl]uuid).orElse([CtLiteralImpl]null));
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the {@link MessageReceiver} to use as a source of information
         * for this {@link PlaceholderText}. If {@code null}, removes this source.
         *
         * @param supplier
         * 		A {@link Supplier} that provides the
         * 		{@link MessageReceiver}
         * @return This, for chaining
         * @see PlaceholderText#getAssociatedReceiver()
         */
        [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder setAssociatedSource([CtParameterImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        [CtTypeReferenceImpl]java.util.function.Supplier<[CtTypeReferenceImpl]org.spongepowered.api.text.channel.MessageReceiver> supplier);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets a string that represents variables for the supplied token.
         * The format of this argument string is dependent on the parser
         * supplied to {@link #setParser(PlaceholderParser)} and thus is
         * not prescribed here.
         *
         * @param string
         * 		The argument string, may be null to reset to
         * 		the default argument string
         * @return This, for chaining
         * @see PlaceholderText#getArgument()
         */
        [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText.Builder setArgument([CtParameterImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        [CtTypeReferenceImpl]java.lang.String string);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Builds and returns the placeholder.
         *
         * @return The appropriate {@link PlaceholderText}
         * @throws IllegalStateException
         * 		if the builder has not been completed,
         * 		or the associated {@link PlaceholderParser} could not validate the
         * 		built {@link PlaceholderText}, if applicable.
         */
        [CtTypeReferenceImpl]org.spongepowered.api.service.placeholder.PlaceholderText build() throws [CtTypeReferenceImpl]java.lang.IllegalStateException;
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.velocitypowered.api.event;
[CtUnresolvedImport]import com.velocitypowered.api.util.AdventureCompat;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import net.kyori.adventure.text.Component;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtUnresolvedImport]import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
[CtUnresolvedImport]import org.checkerframework.checker.nullness.qual.Nullable;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Indicates an event that has a result attached to it.
 */
public interface ResultedEvent<[CtTypeParameterImpl]R extends [CtTypeReferenceImpl][CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.Result> {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the result associated with this event.
     *
     * @return the result of this event
     */
    [CtTypeParameterReferenceImpl]R getResult();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the result of this event. The result must be non-null.
     *
     * @param result
     * 		the new result
     */
    [CtTypeReferenceImpl]void setResult([CtParameterImpl][CtTypeParameterReferenceImpl]R result);

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Represents a result for an event.
     */
    interface Result {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns whether or not the event is allowed to proceed. Plugins may choose to skip denied
         * events, and the proxy will respect the result of this method.
         *
         * @return whether or not the event is allowed to proceed
         */
        [CtTypeReferenceImpl]boolean isAllowed();
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * A generic "allowed/denied" result.
     */
    final class GenericResult implements [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.Result {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult ALLOWED = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult([CtLiteralImpl]true);

        [CtFieldImpl]private static final [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult DENIED = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult([CtLiteralImpl]false);

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean status;

        [CtConstructorImpl]private GenericResult([CtParameterImpl][CtTypeReferenceImpl]boolean b) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.status = [CtVariableReadImpl]b;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isAllowed() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]status;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtConditionalImpl][CtFieldReadImpl]status ? [CtLiteralImpl]"allowed" : [CtLiteralImpl]"denied";
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult allowed() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult.ALLOWED;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult denied() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.velocitypowered.api.event.ResultedEvent.GenericResult.DENIED;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Represents an "allowed/denied" result with a reason allowed for denial.
     */
    final class ComponentResult implements [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.Result {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult ALLOWED = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult([CtLiteralImpl]true, [CtLiteralImpl]null);

        [CtFieldImpl]private final [CtTypeReferenceImpl]boolean status;

        [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        private final [CtTypeReferenceImpl]net.kyori.adventure.text.Component reason;

        [CtConstructorImpl]protected ComponentResult([CtParameterImpl][CtTypeReferenceImpl]boolean status, [CtParameterImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
        [CtTypeReferenceImpl]net.kyori.adventure.text.Component reason) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.status = [CtVariableReadImpl]status;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reason = [CtVariableReadImpl]reason;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isAllowed() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]status;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]net.kyori.text.Component> getReason() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]reason).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]AdventureCompat::asOriginalTextComponent);
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]net.kyori.adventure.text.Component> getReasonComponent() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtFieldReadImpl]reason);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]status) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"allowed";
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]reason != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"denied: " + [CtInvocationImpl][CtTypeAccessImpl]PlainComponentSerializer.INSTANCE.serialize([CtFieldReadImpl]reason);
            }
            [CtReturnImpl]return [CtLiteralImpl]"denied";
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult allowed() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult.ALLOWED;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult denied([CtParameterImpl][CtTypeReferenceImpl]net.kyori.adventure.text.Component reason) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]reason, [CtLiteralImpl]"reason");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult([CtLiteralImpl]false, [CtVariableReadImpl]reason);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
        public static [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult denied([CtParameterImpl][CtTypeReferenceImpl]net.kyori.text.Component reason) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]reason, [CtLiteralImpl]"reason");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.api.event.ResultedEvent.ComponentResult([CtLiteralImpl]false, [CtInvocationImpl][CtTypeAccessImpl]com.velocitypowered.api.util.AdventureCompat.asAdventureComponent([CtVariableReadImpl]reason));
        }
    }
}
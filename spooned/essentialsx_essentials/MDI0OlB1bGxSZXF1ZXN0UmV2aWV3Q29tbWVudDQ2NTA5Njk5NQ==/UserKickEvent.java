[CompilationUnitImpl][CtPackageDeclarationImpl]package net.ess3.api.events;
[CtUnresolvedImport]import org.bukkit.event.HandlerList;
[CtUnresolvedImport]import org.bukkit.event.Cancellable;
[CtUnresolvedImport]import org.bukkit.event.Event;
[CtUnresolvedImport]import com.earth2me.essentials.IUser;
[CtClassImpl]public class UserKickEvent extends [CtTypeReferenceImpl]org.bukkit.event.Event implements [CtTypeReferenceImpl]org.bukkit.event.Cancellable {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.bukkit.event.HandlerList handlers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bukkit.event.HandlerList();

    [CtFieldImpl]private [CtTypeReferenceImpl]com.earth2me.essentials.IUser kicked;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.earth2me.essentials.IUser kicker;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String reason;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean cancelled;

    [CtConstructorImpl]public UserKickEvent([CtParameterImpl][CtTypeReferenceImpl]com.earth2me.essentials.IUser kicked, [CtParameterImpl][CtTypeReferenceImpl]com.earth2me.essentials.IUser kicker, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String reason) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kicked = [CtVariableReadImpl]kicked;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.kicker = [CtVariableReadImpl]kicker;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reason = [CtVariableReadImpl]reason;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.earth2me.essentials.IUser getKicked() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]kicked;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.earth2me.essentials.IUser getKicker() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]kicker;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getReason() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]reason;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setReason([CtParameterImpl][CtTypeReferenceImpl]java.lang.String reason) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reason = [CtVariableReadImpl]reason;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isCancelled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]cancelled;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void setCancelled([CtParameterImpl][CtTypeReferenceImpl]boolean b) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cancelled = [CtVariableReadImpl]b;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.bukkit.event.HandlerList getHandlers() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]net.ess3.api.events.UserKickEvent.handlers;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.bukkit.event.HandlerList getHandlerList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]net.ess3.api.events.UserKickEvent.handlers;
    }
}
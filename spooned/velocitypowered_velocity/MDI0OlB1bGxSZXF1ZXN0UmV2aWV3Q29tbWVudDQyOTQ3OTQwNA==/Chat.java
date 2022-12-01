[CompilationUnitImpl][CtPackageDeclarationImpl]package com.velocitypowered.proxy.protocol.packet;
[CtUnresolvedImport]import net.kyori.text.Component;
[CtUnresolvedImport]import io.netty.buffer.ByteBuf;
[CtUnresolvedImport]import net.kyori.text.serializer.gson.GsonComponentSerializer;
[CtUnresolvedImport]import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
[CtUnresolvedImport]import com.velocitypowered.proxy.protocol.MinecraftPacket;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtUnresolvedImport]import com.velocitypowered.proxy.protocol.ProtocolUtils;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import com.velocitypowered.api.network.ProtocolVersion;
[CtUnresolvedImport]import org.checkerframework.checker.nullness.qual.Nullable;
[CtClassImpl]public class Chat implements [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.MinecraftPacket {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]byte CHAT_TYPE = [CtLiteralImpl](([CtTypeReferenceImpl]byte) (0));

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int MAX_SERVERBOUND_MESSAGE_LENGTH = [CtLiteralImpl]256;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.util.UUID EMPTY_SENDER = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.UUID([CtLiteralImpl]0, [CtLiteralImpl]0);

    [CtFieldImpl][CtAnnotationImpl]@org.checkerframework.checker.nullness.qual.Nullable
    private [CtTypeReferenceImpl]java.lang.String message;

    [CtFieldImpl]private [CtTypeReferenceImpl]byte type;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.UUID sender;

    [CtConstructorImpl]public Chat() [CtBlockImpl]{
    }

    [CtConstructorImpl]public Chat([CtParameterImpl][CtTypeReferenceImpl]java.lang.String message, [CtParameterImpl][CtTypeReferenceImpl]byte type, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID sender) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.message = [CtVariableReadImpl]message;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sender = [CtVariableReadImpl]sender;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getMessage() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Message is not specified");
        }
        [CtReturnImpl]return [CtFieldReadImpl]message;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.message = [CtVariableReadImpl]message;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]byte getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]type;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setType([CtParameterImpl][CtTypeReferenceImpl]byte type) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.UUID getSenderUuid() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sender;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSenderUuid([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID sender) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sender = [CtVariableReadImpl]sender;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Chat{" + [CtLiteralImpl]"message='") + [CtFieldReadImpl]message) + [CtLiteralImpl]'\'') + [CtLiteralImpl]", type=") + [CtFieldReadImpl]type) + [CtLiteralImpl]", sender=") + [CtFieldReadImpl]sender) + [CtLiteralImpl]'}';
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void decode([CtParameterImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf buf, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.Direction direction, [CtParameterImpl][CtTypeReferenceImpl]com.velocitypowered.api.network.ProtocolVersion version) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]message = [CtInvocationImpl][CtTypeAccessImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.readString([CtVariableReadImpl]buf);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]direction == [CtFieldReadImpl]ProtocolUtils.Direction.CLIENTBOUND) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]type = [CtInvocationImpl][CtVariableReadImpl]buf.readByte();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtTypeAccessImpl]ProtocolVersion.MINECRAFT_1_16) >= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]sender = [CtInvocationImpl][CtTypeAccessImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.readUuid([CtVariableReadImpl]buf);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void encode([CtParameterImpl][CtTypeReferenceImpl]io.netty.buffer.ByteBuf buf, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.Direction direction, [CtParameterImpl][CtTypeReferenceImpl]com.velocitypowered.api.network.ProtocolVersion version) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Message is not specified");
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.writeString([CtVariableReadImpl]buf, [CtFieldReadImpl]message);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]direction == [CtFieldReadImpl]ProtocolUtils.Direction.CLIENTBOUND) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]buf.writeByte([CtFieldReadImpl]type);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]version.compareTo([CtTypeAccessImpl]ProtocolVersion.MINECRAFT_1_16) >= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.velocitypowered.proxy.protocol.ProtocolUtils.writeUuid([CtVariableReadImpl]buf, [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]sender == [CtLiteralImpl]null ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.UUID([CtLiteralImpl]0, [CtLiteralImpl]0) : [CtFieldReadImpl]sender);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean handle([CtParameterImpl][CtTypeReferenceImpl]com.velocitypowered.proxy.connection.MinecraftSessionHandler handler) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]handler.handle([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.packet.Chat createClientbound([CtParameterImpl][CtTypeReferenceImpl]net.kyori.text.Component component) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.velocitypowered.proxy.protocol.packet.Chat.createClientbound([CtVariableReadImpl]component, [CtFieldReadImpl]com.velocitypowered.proxy.protocol.packet.Chat.CHAT_TYPE, [CtFieldReadImpl]com.velocitypowered.proxy.protocol.packet.Chat.EMPTY_SENDER);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.packet.Chat createClientbound([CtParameterImpl][CtTypeReferenceImpl]net.kyori.text.Component component, [CtParameterImpl][CtTypeReferenceImpl]byte type, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID sender) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]component, [CtLiteralImpl]"component");
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.packet.Chat([CtInvocationImpl][CtTypeAccessImpl]GsonComponentSerializer.INSTANCE.serialize([CtVariableReadImpl]component), [CtVariableReadImpl]type, [CtVariableReadImpl]sender);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.packet.Chat createServerbound([CtParameterImpl][CtTypeReferenceImpl]java.lang.String message) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.velocitypowered.proxy.protocol.packet.Chat([CtVariableReadImpl]message, [CtFieldReadImpl]com.velocitypowered.proxy.protocol.packet.Chat.CHAT_TYPE, [CtFieldReadImpl]com.velocitypowered.proxy.protocol.packet.Chat.EMPTY_SENDER);
    }
}
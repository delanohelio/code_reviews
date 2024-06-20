[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
[CtPackageDeclarationImpl]package org.openhab.binding.upb.handler;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.Channel;
[CtUnresolvedImport]import static org.openhab.binding.upb.internal.message.Command.*;
[CtUnresolvedImport]import org.eclipse.smarthome.core.library.types.OnOffType;
[CtUnresolvedImport]import org.openhab.binding.upb.UPBDevice;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
[CtUnresolvedImport]import org.eclipse.smarthome.core.types.Command;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.ChannelUID;
[CtUnresolvedImport]import org.openhab.binding.upb.internal.message.MessageBuilder;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.Nullable;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.Thing;
[CtUnresolvedImport]import org.openhab.binding.upb.handler.UPBIoHandler.CmdStatus;
[CtUnresolvedImport]import org.eclipse.smarthome.core.types.State;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.eclipse.jdt.annotation.NonNullByDefault;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.Bridge;
[CtUnresolvedImport]import org.eclipse.smarthome.core.types.RefreshType;
[CtUnresolvedImport]import org.openhab.binding.upb.Constants;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.ThingStatusInfo;
[CtImportImpl]import java.math.BigDecimal;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.type.ChannelTypeUID;
[CtUnresolvedImport]import org.eclipse.smarthome.core.library.types.PercentType;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.ThingStatusDetail;
[CtUnresolvedImport]import org.openhab.binding.upb.internal.message.UPBMessage;
[CtUnresolvedImport]import org.eclipse.smarthome.core.thing.ThingStatus;
[CtClassImpl][CtJavaDocImpl]/**
 * Handler for things representing devices on an UPB network.
 *
 * @author Marcus Better - Initial contribution
 */
[CtAnnotationImpl]@org.eclipse.jdt.annotation.NonNullByDefault
public class UPBThingHandler extends [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.binding.BaseThingHandler {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.openhab.binding.upb.handler.UPBThingHandler.class);

    [CtFieldImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    private final [CtTypeReferenceImpl]java.lang.Byte defaultNetworkId;

    [CtFieldImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    protected volatile [CtTypeReferenceImpl]org.openhab.binding.upb.handler.PIMHandler controllerHandler;

    [CtFieldImpl]protected volatile [CtTypeReferenceImpl]byte networkId;

    [CtFieldImpl]protected volatile [CtTypeReferenceImpl]int unitId;

    [CtConstructorImpl]public UPBThingHandler([CtParameterImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.Thing device, [CtParameterImpl][CtAnnotationImpl]@org.eclipse.jdt.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.Byte defaultNetworkId) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]device);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultNetworkId = [CtVariableReadImpl]defaultNetworkId;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void initialize() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"initializing UPB thing handler {}", [CtInvocationImpl][CtInvocationImpl]getThing().getUID());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.math.BigDecimal val = [CtInvocationImpl](([CtTypeReferenceImpl]java.math.BigDecimal) ([CtInvocationImpl]getConfig().get([CtTypeAccessImpl]Constants.CONFIGURATION_NETWORK_ID)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]val == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// use value from binding config
            final [CtTypeReferenceImpl]java.lang.Byte defaultNetworkId = [CtFieldReadImpl][CtThisAccessImpl]this.defaultNetworkId;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]defaultNetworkId == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtLiteralImpl]"missing network ID for {}", [CtInvocationImpl][CtInvocationImpl]getThing().getUID());
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]networkId = [CtInvocationImpl][CtVariableReadImpl]defaultNetworkId.byteValue();
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]val.compareTo([CtFieldReadImpl][CtTypeAccessImpl]java.math.BigDecimal.[CtFieldReferenceImpl]ZERO) < [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]val.compareTo([CtInvocationImpl][CtTypeAccessImpl]java.math.BigDecimal.valueOf([CtLiteralImpl]255)) > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtLiteralImpl]"invalid network ID {} for {}", [CtVariableReadImpl]val, [CtInvocationImpl][CtInvocationImpl]getThing().getUID());
            [CtReturnImpl]return;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]networkId = [CtInvocationImpl][CtVariableReadImpl]val.byteValue();
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.math.BigDecimal cfgUnitId = [CtInvocationImpl](([CtTypeReferenceImpl]java.math.BigDecimal) ([CtInvocationImpl]getConfig().get([CtTypeAccessImpl]Constants.CONFIGURATION_UNIT_ID)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cfgUnitId == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtLiteralImpl]"Unit ID is not set in {}", [CtInvocationImpl][CtInvocationImpl]getThing().getUID());
            [CtReturnImpl]return;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]unitId = [CtInvocationImpl][CtVariableReadImpl]cfgUnitId.intValue();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]unitId < [CtLiteralImpl]1) || [CtBinaryOperatorImpl]([CtFieldReadImpl]unitId > [CtLiteralImpl]250)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtLiteralImpl]"Unit ID ({}) out of range for {}", [CtVariableReadImpl]cfgUnitId, [CtInvocationImpl][CtInvocationImpl]getThing().getUID());
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.Bridge bridge = [CtInvocationImpl]getBridge();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bridge == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.OFFLINE, [CtTypeAccessImpl]ThingStatusDetail.BRIDGE_OFFLINE, [CtTypeAccessImpl]Constants.OFFLINE_CTLR_OFFLINE);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]bridgeStatusChanged([CtInvocationImpl][CtVariableReadImpl]bridge.getStatusInfo());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void dispose() [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void bridgeStatusChanged([CtParameterImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.ThingStatusInfo bridgeStatusInfo) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: Controller status is {}", [CtFieldReadImpl]unitId, [CtInvocationImpl][CtVariableReadImpl]bridgeStatusInfo.getStatus());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]bridgeStatusInfo.getStatus() != [CtFieldReadImpl]org.eclipse.smarthome.core.thing.ThingStatus.ONLINE) [CtBlockImpl]{
            [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.OFFLINE, [CtTypeAccessImpl]ThingStatusDetail.BRIDGE_OFFLINE, [CtTypeAccessImpl]Constants.OFFLINE_CTLR_OFFLINE);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: Controller is ONLINE. Starting device initialisation.", [CtFieldReadImpl]unitId);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.Bridge bridge = [CtInvocationImpl]getBridge();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bridge == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: bridge is null!", [CtFieldReadImpl]unitId);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]PIMHandler bridgeHandler = [CtInvocationImpl](([CtTypeReferenceImpl]PIMHandler) ([CtVariableReadImpl]bridge.getHandler()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bridgeHandler == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: bridge handler is null!", [CtFieldReadImpl]unitId);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]updateDeviceStatus([CtVariableReadImpl]bridgeHandler);
        [CtIfImpl][CtCommentImpl]// If we already know the controller, then we don't want to initialise again
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]controllerHandler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: Controller already initialised", [CtFieldReadImpl]unitId);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]controllerHandler = [CtVariableReadImpl]bridgeHandler;
        }
        [CtInvocationImpl]pingDevice();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void handleCommand([CtParameterImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.ChannelUID channelUID, [CtParameterImpl]final [CtTypeReferenceImpl]Command cmd) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]PIMHandler controllerHandler = [CtFieldReadImpl][CtThisAccessImpl]this.controllerHandler;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]controllerHandler == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"DEV {}: received cmd {} but no bridge handler", [CtFieldReadImpl]unitId, [CtVariableReadImpl]cmd);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.internal.message.MessageBuilder message;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cmd == [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.ON) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtTypeAccessImpl]org.openhab.binding.upb.internal.message.MessageBuilder.forCommand([CtTypeAccessImpl]ACTIVATE);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cmd == [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.OFF) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtTypeAccessImpl]org.openhab.binding.upb.internal.message.MessageBuilder.forCommand([CtTypeAccessImpl]DEACTIVATE);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cmd instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.eclipse.smarthome.core.library.types.PercentType) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openhab.binding.upb.internal.message.MessageBuilder.forCommand([CtTypeAccessImpl]GOTO).args([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.eclipse.smarthome.core.library.types.PercentType) (cmd)).byteValue());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cmd == [CtFieldReadImpl]org.eclipse.smarthome.core.types.RefreshType.REFRESH) [CtBlockImpl]{
            [CtInvocationImpl]refreshDeviceState();
            [CtReturnImpl]return;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"channel {}: unsupported cmd {}", [CtVariableReadImpl]channelUID, [CtVariableReadImpl]cmd);
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]message.network([CtFieldReadImpl]networkId).destination([CtInvocationImpl]getUnitId());
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]controllerHandler.sendPacket([CtVariableReadImpl]message).thenAccept([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::updateStatus);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onMessageReceived([CtParameterImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.internal.message.UPBMessage msg) [CtBlockImpl]{
        [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.ONLINE);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.getControlWord().isLink()) [CtBlockImpl]{
            [CtInvocationImpl]handleLinkMessage([CtVariableReadImpl]msg);
        } else [CtBlockImpl]{
            [CtInvocationImpl]handleDirectMessage([CtVariableReadImpl]msg);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleDirectMessage([CtParameterImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.internal.message.UPBMessage msg) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.types.State state;
        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]msg.getCommand()) {
            [CtCaseImpl]case [CtFieldReadImpl]ACTIVATE :
                [CtAssignmentImpl][CtVariableWriteImpl]state = [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.ON;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]DEACTIVATE :
                [CtAssignmentImpl][CtVariableWriteImpl]state = [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.OFF;
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]GOTO :
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]msg.getArguments().length == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]logger.info([CtLiteralImpl]"DEV {}: malformed GOTO cmd", [CtFieldReadImpl]unitId);
                    [CtReturnImpl]return;
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int level = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]msg.getArguments()[[CtLiteralImpl]0];
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]level == [CtLiteralImpl]100) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]state = [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.ON;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]state = [CtFieldReadImpl]org.eclipse.smarthome.core.library.types.OnOffType.OFF;
                }
                [CtInvocationImpl]updateState([CtTypeAccessImpl]Constants.DIMMER_TYPE_ID, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.eclipse.smarthome.core.library.types.PercentType([CtVariableReadImpl]level));
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: Message {} ignored", [CtFieldReadImpl]unitId, [CtInvocationImpl][CtVariableReadImpl]msg.getCommand());
                [CtReturnImpl]return;
        }
        [CtInvocationImpl]updateState([CtTypeAccessImpl]Constants.SWITCH_TYPE_ID, [CtVariableReadImpl]state);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleLinkMessage([CtParameterImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.internal.message.UPBMessage msg) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]byte linkId = [CtInvocationImpl][CtVariableReadImpl]msg.getDestination();
        [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.Channel ch : [CtInvocationImpl][CtInvocationImpl]getThing().getChannels()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.eclipse.smarthome.core.thing.type.ChannelTypeUID channelTypeUID = [CtInvocationImpl][CtVariableReadImpl]ch.getChannelTypeUID();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]channelTypeUID != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]Constants.SCENE_CHANNEL_TYPE_ID.equals([CtInvocationImpl][CtVariableReadImpl]channelTypeUID.getId())) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.math.BigDecimal channelLinkId = [CtInvocationImpl](([CtTypeReferenceImpl]java.math.BigDecimal) ([CtInvocationImpl][CtVariableReadImpl]ch.getConfiguration().get([CtTypeAccessImpl]Constants.CONFIGURATION_LINK_ID)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]channelLinkId == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]channelLinkId.byteValue() != [CtVariableReadImpl]linkId)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]msg.getCommand()) {
                    [CtCaseImpl]case [CtFieldReadImpl]ACTIVATE :
                    [CtCaseImpl]case [CtFieldReadImpl]DEACTIVATE :
                        [CtInvocationImpl]triggerChannel([CtInvocationImpl][CtVariableReadImpl]ch.getUID(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]msg.getCommand().name());
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtInvocationImpl][CtFieldReadImpl]logger.debug([CtLiteralImpl]"DEV {}: Message {} ignored for link {}", [CtFieldReadImpl]unitId, [CtBinaryOperatorImpl][CtVariableReadImpl]linkId & [CtLiteralImpl]0xff, [CtInvocationImpl][CtVariableReadImpl]msg.getCommand());
                        [CtReturnImpl]return;
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateDeviceStatus([CtParameterImpl]final [CtTypeReferenceImpl]PIMHandler bridgeHandler) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.UPBDevice device = [CtInvocationImpl][CtVariableReadImpl]bridgeHandler.getDevice([CtInvocationImpl]getNetworkId(), [CtInvocationImpl]getUnitId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]device == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.OFFLINE, [CtTypeAccessImpl]ThingStatusDetail.NONE, [CtTypeAccessImpl]Constants.OFFLINE_NODE_NOTFOUND);
        } else [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]device.getState()) {
                [CtCaseImpl]case [CtFieldReadImpl]INITIALIZING :
                [CtCaseImpl]case [CtFieldReadImpl]ALIVE :
                    [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.ONLINE);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]DEAD :
                [CtCaseImpl]case [CtFieldReadImpl]FAILED :
                    [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.OFFLINE, [CtTypeAccessImpl]ThingStatusDetail.COMMUNICATION_ERROR, [CtTypeAccessImpl]Constants.OFFLINE_NODE_DEAD);
                    [CtBreakImpl]break;
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void pingDevice() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]PIMHandler controllerHandler = [CtFieldReadImpl][CtThisAccessImpl]this.controllerHandler;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]controllerHandler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]controllerHandler.sendPacket([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openhab.binding.upb.internal.message.MessageBuilder.forCommand([CtTypeAccessImpl]NULL).ackMessage([CtLiteralImpl]true).network([CtFieldReadImpl]networkId).destination([CtFieldReadImpl](([CtTypeReferenceImpl]byte) (unitId)))).thenAccept([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::updateStatus);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateStatus([CtParameterImpl]final [CtTypeReferenceImpl]org.openhab.binding.upb.handler.UPBIoHandler.CmdStatus result) [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]result) {
            [CtCaseImpl]case [CtFieldReadImpl]WRITE_FAILED :
                [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.OFFLINE, [CtTypeAccessImpl]ThingStatusDetail.COMMUNICATION_ERROR, [CtTypeAccessImpl]Constants.OFFLINE_NODE_DEAD);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]ACK :
            [CtCaseImpl]case [CtFieldReadImpl]NAK :
                [CtInvocationImpl]updateStatus([CtTypeAccessImpl]ThingStatus.ONLINE);
                [CtBreakImpl]break;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void refreshDeviceState() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]PIMHandler controllerHandler = [CtFieldReadImpl][CtThisAccessImpl]this.controllerHandler;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]controllerHandler != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]controllerHandler.sendPacket([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.openhab.binding.upb.internal.message.MessageBuilder.forCommand([CtTypeAccessImpl]REPORT_STATE).network([CtFieldReadImpl]networkId).destination([CtInvocationImpl]getUnitId())).thenAccept([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::updateStatus);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]byte getNetworkId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]networkId;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]byte getUnitId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]byte) (unitId));
    }
}
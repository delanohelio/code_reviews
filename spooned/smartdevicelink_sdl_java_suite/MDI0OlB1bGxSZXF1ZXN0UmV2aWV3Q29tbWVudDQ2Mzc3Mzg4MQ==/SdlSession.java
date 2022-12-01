[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2018 Livio, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following
disclaimer in the documentation and/or other materials provided with the
distribution.

Neither the name of the Livio Inc. nor the names of its contributors
may be used to endorse or promote products derived from this software
without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */
[CtPackageDeclarationImpl]package com.smartdevicelink.SdlConnection;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitor;
[CtUnresolvedImport]import com.smartdevicelink.util.DebugTool;
[CtUnresolvedImport]import com.smartdevicelink.transport.enums.TransportType;
[CtUnresolvedImport]import com.smartdevicelink.protocol.SdlProtocolBase;
[CtUnresolvedImport]import com.smartdevicelink.protocol.ProtocolMessage;
[CtImportImpl]import java.lang.ref.WeakReference;
[CtUnresolvedImport]import com.smartdevicelink.util.Version;
[CtUnresolvedImport]import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
[CtUnresolvedImport]import com.smartdevicelink.exception.SdlException;
[CtUnresolvedImport]import com.smartdevicelink.transport.TCPTransportConfig;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.smartdevicelink.transport.BaseTransportConfig;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
[CtUnresolvedImport]import com.smartdevicelink.protocol.SdlProtocol;
[CtUnresolvedImport]import com.smartdevicelink.streaming.IStreamListener;
[CtUnresolvedImport]import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
[CtUnresolvedImport]import com.smartdevicelink.protocol.ISdlProtocol;
[CtUnresolvedImport]import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
[CtUnresolvedImport]import com.smartdevicelink.util.MediaStreamingStatus;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.smartdevicelink.transport.MultiplexTransportConfig;
[CtUnresolvedImport]import com.smartdevicelink.streaming.video.RTPH264Packetizer;
[CtUnresolvedImport]import com.smartdevicelink.streaming.AbstractPacketizer;
[CtImportImpl]import java.util.ListIterator;
[CtUnresolvedImport]import com.smartdevicelink.protocol.enums.SessionType;
[CtUnresolvedImport]import com.smartdevicelink.streaming.StreamPacketizer;
[CtUnresolvedImport]import com.smartdevicelink.protocol.SdlPacket;
[CtUnresolvedImport]import com.smartdevicelink.streaming.video.VideoStreamingParameters;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
[CtImportImpl]import java.util.concurrent.CopyOnWriteArrayList;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"WeakerAccess", [CtLiteralImpl]"deprecation" })
public class SdlSession extends [CtTypeReferenceImpl]com.smartdevicelink.SdlConnection.BaseSdlSession {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TAG = [CtLiteralImpl]"SdlSession";

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.ref.WeakReference<[CtTypeReferenceImpl]android.content.Context> contextWeakReference;

    [CtFieldImpl][CtTypeReferenceImpl]com.smartdevicelink.util.MediaStreamingStatus mediaStreamingStatus;

    [CtFieldImpl][CtTypeReferenceImpl]boolean requiresAudioSupport = [CtLiteralImpl]false;

    [CtConstructorImpl]public SdlSession([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.SdlConnection.ISdlConnectionListener listener, [CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.transport.MultiplexTransportConfig config) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]listener, [CtVariableReadImpl]config);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transportConfig = [CtVariableReadImpl]config;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]config != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]contextWeakReference = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.WeakReference<>([CtInvocationImpl][CtVariableReadImpl]config.getContext());
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requiresAudioSupport = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]config.requiresAudioSupport());[CtCommentImpl]// handle null case

        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sessionListener = [CtVariableReadImpl]listener;
    }

    [CtConstructorImpl]public SdlSession([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.SdlConnection.ISdlConnectionListener listener, [CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.transport.TCPTransportConfig config) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// TODO is it better to have two constructors or make it take BaseTransportConfig?
        super([CtVariableReadImpl]listener, [CtVariableReadImpl]config);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transportConfig = [CtVariableReadImpl]config;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sessionListener = [CtVariableReadImpl]listener;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]com.smartdevicelink.protocol.SdlProtocolBase getSdlProtocolImplementation() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]transportConfig instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.smartdevicelink.transport.MultiplexTransportConfig) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.protocol.SdlProtocol([CtThisAccessImpl]this, [CtFieldReadImpl](([CtTypeReferenceImpl]com.smartdevicelink.transport.MultiplexTransportConfig) (transportConfig)));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]transportConfig instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.smartdevicelink.transport.TCPTransportConfig) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.protocol.SdlProtocol([CtThisAccessImpl]this, [CtFieldReadImpl](([CtTypeReferenceImpl]com.smartdevicelink.transport.TCPTransportConfig) (transportConfig)));
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtTypeReferenceImpl]boolean isAudioRequirementMet() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]mediaStreamingStatus == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]contextWeakReference != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]contextWeakReference.get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mediaStreamingStatus = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.util.MediaStreamingStatus([CtInvocationImpl][CtFieldReadImpl]contextWeakReference.get(), [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.smartdevicelink.util.MediaStreamingStatus.Callback()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onAudioNoLongerAvailable() [CtBlockImpl]{
                    [CtInvocationImpl]close();
                    [CtInvocationImpl]shutdown([CtLiteralImpl]"Audio output no longer available");
                }
            });
        }
        [CtReturnImpl][CtCommentImpl]// If requiresAudioSupport is false, or a supported audio output device is available
        return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]requiresAudioSupport) || [CtInvocationImpl][CtFieldReadImpl]mediaStreamingStatus.isAudioOutputAvailable();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"RedundantThrows")
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void startSession() throws [CtTypeReferenceImpl]com.smartdevicelink.exception.SdlException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isAudioRequirementMet()) [CtBlockImpl]{
            [CtInvocationImpl]shutdown([CtLiteralImpl]"Audio output not available");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]sdlProtocol.start();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.smartdevicelink.transport.enums.TransportType getCurrentTransportType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.smartdevicelink.transport.enums.TransportType.MULTIPLEX;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void shutdown([CtParameterImpl][CtTypeReferenceImpl]java.lang.String info) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.DebugTool.logInfo([CtFieldReadImpl]com.smartdevicelink.SdlConnection.SdlSession.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"Shutdown - " + [CtVariableReadImpl]info);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mediaStreamingStatus != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mediaStreamingStatus.clear();
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.shutdown([CtVariableReadImpl]info);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the current protocol version used by this session
     *
     * @return Version that represents the Protocol version being used
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.smartdevicelink.util.Version getProtocolVersion() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]sdlProtocol != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]sdlProtocol.getProtocolVersion();
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.util.Version([CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtCommentImpl]/* ***********************************************************************************************************************************************************************
    *****************************************************************  IProtocol Listener  ********************************************************************************
    ***********************************************************************************************************************************************************************
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onProtocolMessageBytesToSend([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.SdlPacket packet) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Log.d(TAG, "onProtocolMessageBytesToSend - " + packet.getTransportType());
        [CtFieldReadImpl]sdlProtocol.sendPacket([CtVariableReadImpl]packet);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onProtocolSessionStarted([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType sessionType, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID, [CtParameterImpl][CtTypeReferenceImpl]byte version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlationID, [CtParameterImpl][CtTypeReferenceImpl]int hashID, [CtParameterImpl][CtTypeReferenceImpl]boolean isEncrypted) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.DebugTool.logInfo([CtFieldReadImpl]com.smartdevicelink.SdlConnection.SdlSession.TAG, [CtLiteralImpl]"Protocol session started");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sessionId = [CtVariableReadImpl]sessionID;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]sessionType.eq([CtTypeAccessImpl]SessionType.RPC)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]sessionHashId = [CtVariableReadImpl]hashID;
        }
        [CtIfImpl]if ([CtVariableReadImpl]isEncrypted)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]encryptedServices.addIfAbsent([CtVariableReadImpl]sessionType);

        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.sessionListener.onProtocolSessionStarted([CtVariableReadImpl]sessionType, [CtVariableReadImpl]sessionID, [CtVariableReadImpl]version, [CtVariableReadImpl]correlationID, [CtVariableReadImpl]hashID, [CtVariableReadImpl]isEncrypted);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]serviceListeners != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]serviceListeners.containsKey([CtVariableReadImpl]sessionType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener> listeners = [CtInvocationImpl][CtFieldReadImpl]serviceListeners.get([CtVariableReadImpl]sessionType);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.onServiceStarted([CtThisAccessImpl]this, [CtVariableReadImpl]sessionType, [CtVariableReadImpl]isEncrypted);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void onProtocolSessionStartedNACKed([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType sessionType, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID, [CtParameterImpl][CtTypeReferenceImpl]byte version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlationID, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> rejectedParams) [CtBlockImpl]{
        [CtInvocationImpl]onProtocolSessionNACKed([CtVariableReadImpl]sessionType, [CtVariableReadImpl]sessionID, [CtVariableReadImpl]version, [CtVariableReadImpl]correlationID, [CtVariableReadImpl]rejectedParams);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onProtocolSessionNACKed([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType sessionType, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID, [CtParameterImpl][CtTypeReferenceImpl]byte version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlationID, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> rejectedParams) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.sessionListener.onProtocolSessionStartedNACKed([CtVariableReadImpl]sessionType, [CtVariableReadImpl]sessionID, [CtVariableReadImpl]version, [CtVariableReadImpl]correlationID, [CtVariableReadImpl]rejectedParams);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]serviceListeners != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]serviceListeners.containsKey([CtVariableReadImpl]sessionType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener> listeners = [CtInvocationImpl][CtFieldReadImpl]serviceListeners.get([CtVariableReadImpl]sessionType);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]listeners != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]listener.onServiceError([CtThisAccessImpl]this, [CtVariableReadImpl]sessionType, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Start " + [CtInvocationImpl][CtVariableReadImpl]sessionType.toString()) + [CtLiteralImpl]" Service NAKed");
                }
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onProtocolSessionEnded([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType sessionType, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlationID) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.sessionListener.onProtocolSessionEnded([CtVariableReadImpl]sessionType, [CtVariableReadImpl]sessionID, [CtVariableReadImpl]correlationID);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]serviceListeners != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]serviceListeners.containsKey([CtVariableReadImpl]sessionType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener> listeners = [CtInvocationImpl][CtFieldReadImpl]serviceListeners.get([CtVariableReadImpl]sessionType);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.onServiceEnded([CtThisAccessImpl]this, [CtVariableReadImpl]sessionType);
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]encryptedServices.remove([CtVariableReadImpl]sessionType);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onProtocolSessionEndedNACKed([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType sessionType, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correlationID) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.sessionListener.onProtocolSessionEndedNACKed([CtVariableReadImpl]sessionType, [CtVariableReadImpl]sessionID, [CtVariableReadImpl]correlationID);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]serviceListeners != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]serviceListeners.containsKey([CtVariableReadImpl]sessionType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener> listeners = [CtInvocationImpl][CtFieldReadImpl]serviceListeners.get([CtVariableReadImpl]sessionType);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.onServiceError([CtThisAccessImpl]this, [CtVariableReadImpl]sessionType, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"End " + [CtInvocationImpl][CtVariableReadImpl]sessionType.toString()) + [CtLiteralImpl]" Service NACK'ed");
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]/* Not supported methods from IProtocolListener */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onHeartbeatTimedOut([CtParameterImpl][CtTypeReferenceImpl]byte sessionId) [CtBlockImpl]{
        [CtCommentImpl]/* Not supported */
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onAuthTokenReceived([CtParameterImpl][CtTypeReferenceImpl]java.lang.String authToken, [CtParameterImpl][CtTypeReferenceImpl]byte sessionID) [CtBlockImpl]{
        [CtCommentImpl]/* Do nothing */
    }

    [CtFieldImpl][CtCommentImpl]/* ***********************************************************************************************************************************************************************
    *****************************************************************  Fix after initial refactor *********************************************************************************
    ***********************************************************************************************************************************************************************
     */
    [CtCommentImpl]// FIXME there is a lot of spaghetti code here that needs to be addressed. For first refactor the
    [CtCommentImpl]// the goal is to only refactor SdlSession. Another PR should be opened to fix all the packetizer
    [CtCommentImpl]// classes and method calls.
    [CtCommentImpl]// FIXME Move this logic to the related streaming manager
    private [CtTypeReferenceImpl]com.smartdevicelink.streaming.AbstractPacketizer videoPacketizer;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.smartdevicelink.streaming.StreamPacketizer audioPacketizer;

    [CtFieldImpl][CtTypeReferenceImpl]com.smartdevicelink.streaming.IStreamListener streamListener = [CtNewClassImpl]new [CtTypeReferenceImpl]com.smartdevicelink.streaming.IStreamListener()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void sendStreamPacket([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.ProtocolMessage pm) [CtBlockImpl]{
            [CtInvocationImpl]sendMessage([CtVariableReadImpl]pm);
        }
    };

    [CtMethodImpl]private [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol getAcceptedProtocol() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]acceptedVideoParams != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.VideoStreamingFormat format = [CtInvocationImpl][CtFieldReadImpl]acceptedVideoParams.getFormat();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]format != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]format.getProtocol() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]format.getProtocol();
            }
        }
        [CtReturnImpl][CtCommentImpl]// Returns default protocol if none are found
        return [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.streaming.video.VideoStreamingParameters().getFormat().getProtocol();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.IVideoStreamListener startVideoStream() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol protocol = [CtInvocationImpl]getAcceptedProtocol();
        [CtTryImpl]try [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]protocol) {
                [CtCaseImpl]case [CtFieldReadImpl]RAW :
                    [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]videoPacketizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.streaming.StreamPacketizer([CtFieldReadImpl]streamListener, [CtLiteralImpl]null, [CtFieldReadImpl]com.smartdevicelink.protocol.enums.SessionType.NAV, [CtFieldReadImpl][CtThisAccessImpl]this.sessionId, [CtThisAccessImpl]this);
                        [CtInvocationImpl][CtFieldReadImpl]videoPacketizer.start();
                        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.IVideoStreamListener) (videoPacketizer));
                    }
                [CtCaseImpl]case [CtFieldReadImpl]RTP :
                    [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// FIXME why is this not an extension of StreamPacketizer?
                        [CtFieldWriteImpl]videoPacketizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.streaming.video.RTPH264Packetizer([CtFieldReadImpl]streamListener, [CtFieldReadImpl]com.smartdevicelink.protocol.enums.SessionType.NAV, [CtFieldReadImpl][CtThisAccessImpl]this.sessionId, [CtThisAccessImpl]this);
                        [CtInvocationImpl][CtFieldReadImpl]videoPacketizer.start();
                        [CtReturnImpl]return [CtFieldReadImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.IVideoStreamListener) (videoPacketizer));
                    }
                [CtCaseImpl]default :
                    [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.DebugTool.logError([CtFieldReadImpl]com.smartdevicelink.SdlConnection.SdlSession.TAG, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Protocol " + [CtVariableReadImpl]protocol) + [CtLiteralImpl]" is not supported.");
                    [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.IAudioStreamListener startAudioStream() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]audioPacketizer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.smartdevicelink.streaming.StreamPacketizer([CtFieldReadImpl]streamListener, [CtLiteralImpl]null, [CtFieldReadImpl]com.smartdevicelink.protocol.enums.SessionType.PCM, [CtFieldReadImpl][CtThisAccessImpl]this.sessionId, [CtThisAccessImpl]this);
            [CtInvocationImpl][CtFieldReadImpl]audioPacketizer.start();
            [CtReturnImpl]return [CtFieldReadImpl]audioPacketizer;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void stopStream([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.protocol.enums.SessionType serviceType) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SessionType.NAV.equals([CtVariableReadImpl]serviceType)) [CtBlockImpl]{
            [CtInvocationImpl]stopVideoStream();
        } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]SessionType.PCM.equals([CtVariableReadImpl]serviceType)) [CtBlockImpl]{
            [CtInvocationImpl]stopAudioStream();
        }
        [CtIfImpl][CtCommentImpl]// Notify any listeners of the service being ended
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]serviceListeners != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]serviceListeners.containsKey([CtVariableReadImpl]serviceType)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.CopyOnWriteArrayList<[CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener> listeners = [CtInvocationImpl][CtFieldReadImpl]serviceListeners.get([CtVariableReadImpl]serviceType);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]listeners != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]listeners.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.interfaces.ISdlServiceListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]listener.onServiceEnded([CtThisAccessImpl]this, [CtVariableReadImpl]serviceType);
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean stopVideoStream() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]videoPacketizer != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]videoPacketizer.stop();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean stopAudioStream() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]audioPacketizer != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]audioPacketizer.stop();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }
}
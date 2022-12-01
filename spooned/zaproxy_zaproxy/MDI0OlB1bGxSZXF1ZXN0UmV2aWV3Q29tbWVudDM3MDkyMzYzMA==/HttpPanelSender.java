[CompilationUnitImpl][CtCommentImpl]/* Zed Attack Proxy (ZAP) and its related class files.

ZAP is an HTTP/HTTPS proxy for assessing web application security.

Copyright 2012 The ZAP Development Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.parosproxy.paros.extension.manualrequest.http.impl;
[CtImportImpl]import javax.swing.ImageIcon;
[CtImportImpl]import java.awt.EventQueue;
[CtUnresolvedImport]import org.parosproxy.paros.control.Control.Mode;
[CtUnresolvedImport]import org.parosproxy.paros.model.HistoryReference;
[CtUnresolvedImport]import org.parosproxy.paros.model.Model;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.zaproxy.zap.extension.httppanel.Message;
[CtImportImpl]import javax.net.ssl.SSLException;
[CtImportImpl]import java.awt.event.ItemEvent;
[CtUnresolvedImport]import org.parosproxy.paros.network.HttpSender;
[CtUnresolvedImport]import org.parosproxy.paros.network.HttpMalformedHeaderException;
[CtUnresolvedImport]import org.zaproxy.zap.network.HttpRequestConfig;
[CtUnresolvedImport]import org.apache.log4j.Logger;
[CtImportImpl]import javax.swing.JToggleButton;
[CtUnresolvedImport]import org.zaproxy.zap.extension.httppanel.HttpPanelResponse;
[CtUnresolvedImport]import org.zaproxy.zap.PersistentConnectionListener;
[CtUnresolvedImport]import org.parosproxy.paros.Constant;
[CtUnresolvedImport]import org.zaproxy.zap.network.HttpRedirectionValidator;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.parosproxy.paros.network.HttpMessage;
[CtUnresolvedImport]import org.zaproxy.zap.extension.httppanel.HttpPanelRequest;
[CtUnresolvedImport]import org.parosproxy.paros.extension.history.ExtensionHistory;
[CtUnresolvedImport]import org.zaproxy.zap.model.SessionStructure;
[CtImportImpl]import java.net.UnknownHostException;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.parosproxy.paros.model.Session;
[CtUnresolvedImport]import org.zaproxy.zap.ZapGetMethod;
[CtUnresolvedImport]import org.parosproxy.paros.db.DatabaseException;
[CtUnresolvedImport]import org.parosproxy.paros.control.Control;
[CtUnresolvedImport]import org.parosproxy.paros.extension.manualrequest.MessageSender;
[CtImportImpl]import java.net.Socket;
[CtUnresolvedImport]import org.apache.commons.httpclient.URI;
[CtUnresolvedImport]import org.zaproxy.zap.extension.httppanel.HttpPanel;
[CtUnresolvedImport]import org.parosproxy.paros.view.View;
[CtClassImpl][CtJavaDocImpl]/**
 * Knows how to send {@link HttpMessage} objects.
 */
public class HttpPanelSender implements [CtTypeReferenceImpl]org.parosproxy.paros.extension.manualrequest.MessageSender {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.log4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.apache.log4j.Logger.getLogger([CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.zaproxy.zap.extension.httppanel.HttpPanelResponse responsePanel;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.parosproxy.paros.extension.history.ExtensionHistory extension;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.parosproxy.paros.network.HttpSender delegate;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton followRedirect = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton useTrackingSessionState = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton useCookies = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton useCsrf = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.zaproxy.zap.PersistentConnectionListener> persistentConnectionListener = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtConstructorImpl]public HttpPanelSender([CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.extension.httppanel.HttpPanelRequest requestPanel, [CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.extension.httppanel.HttpPanelResponse responsePanel) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.responsePanel = [CtVariableReadImpl]responsePanel;
        [CtInvocationImpl][CtVariableReadImpl]requestPanel.addOptions([CtInvocationImpl]getButtonUseTrackingSessionState(), [CtTypeAccessImpl]HttpPanel.OptionsLocation.AFTER_COMPONENTS);
        [CtInvocationImpl][CtVariableReadImpl]requestPanel.addOptions([CtInvocationImpl]getButtonUseCookies(), [CtTypeAccessImpl]HttpPanel.OptionsLocation.AFTER_COMPONENTS);
        [CtInvocationImpl][CtVariableReadImpl]requestPanel.addOptions([CtInvocationImpl]getButtonFollowRedirects(), [CtTypeAccessImpl]HttpPanel.OptionsLocation.AFTER_COMPONENTS);
        [CtInvocationImpl][CtVariableReadImpl]requestPanel.addOptions([CtInvocationImpl]getButtonUseCsrf(), [CtTypeAccessImpl]HttpPanel.OptionsLocation.AFTER_COMPONENTS);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isSessionTrackingEnabled = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.model.Model.getSingleton().getOptionsParam().getConnectionParam().isHttpStateEnabled();
        [CtInvocationImpl][CtInvocationImpl]getButtonUseTrackingSessionState().setEnabled([CtVariableReadImpl]isSessionTrackingEnabled);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void handleSendMessage([CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.extension.httppanel.Message aMessage) throws [CtTypeReferenceImpl]java.lang.IllegalArgumentException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMessage httpMessage = [CtVariableReadImpl](([CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMessage) (aMessage));
        [CtInvocationImpl][CtCommentImpl]// Reset the user before sending (e.g. Forced User mode sets the user, if needed).
        [CtVariableReadImpl]httpMessage.setRequestingUser([CtLiteralImpl]null);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.ModeRedirectionValidator redirectionValidator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.ModeRedirectionValidator();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean followRedirects = [CtInvocationImpl][CtInvocationImpl]getButtonFollowRedirects().isSelected();
            [CtIfImpl]if ([CtVariableReadImpl]followRedirects) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]getDelegate().sendAndReceive([CtVariableReadImpl]httpMessage, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.zaproxy.zap.network.HttpRequestConfig.builder().setRedirectionValidator([CtVariableReadImpl]redirectionValidator).build());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl]getDelegate().sendAndReceive([CtVariableReadImpl]httpMessage, [CtLiteralImpl]false);
            }
            [CtInvocationImpl][CtTypeAccessImpl]java.awt.EventQueue.invokeAndWait([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]httpMessage.getResponseHeader().isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// Indicate UI new response arrived
                        [CtFieldReadImpl]responsePanel.updateContent();
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]followRedirects) [CtBlockImpl]{
                            [CtInvocationImpl]persistAndShowMessage([CtVariableReadImpl]httpMessage);
                        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]redirectionValidator.isRequestValid()) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.view.View.getSingleton().showWarningDialog([CtInvocationImpl][CtTypeAccessImpl]Constant.messages.getString([CtLiteralImpl]"manReq.outofscope.redirection.warning", [CtInvocationImpl][CtVariableReadImpl]redirectionValidator.getInvalidRedirection()));
                        }
                    }
                }
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.zaproxy.zap.ZapGetMethod method = [CtInvocationImpl](([CtTypeReferenceImpl]org.zaproxy.zap.ZapGetMethod) ([CtVariableReadImpl]httpMessage.getUserObject()));
            [CtInvocationImpl]notifyPersistentConnectionListener([CtVariableReadImpl]httpMessage, [CtLiteralImpl]null, [CtVariableReadImpl]method);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMalformedHeaderException mhe) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Malformed header error.", [CtVariableReadImpl]mhe);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.net.UnknownHostException uhe) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtBinaryOperatorImpl][CtLiteralImpl]"Error forwarding to an Unknown host: " + [CtInvocationImpl][CtVariableReadImpl]uhe.getMessage(), [CtVariableReadImpl]uhe);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]javax.net.ssl.SSLException sslEx) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]sslEx;
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.io.IOException ioe) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"IO error in sending request: " + [CtInvocationImpl][CtVariableReadImpl]ioe.getClass()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ioe.getMessage(), [CtVariableReadImpl]ioe);
        }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.logger.error([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void persistAndShowMessage([CtParameterImpl][CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMessage httpMessage) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]java.awt.EventQueue.isDispatchThread()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.awt.EventQueue.invokeLater([CtLambdaImpl]() -> [CtInvocationImpl]persistAndShowMessage([CtVariableReadImpl]httpMessage));
            [CtReturnImpl]return;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.parosproxy.paros.model.Session session = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.model.Model.getSingleton().getSession();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.parosproxy.paros.model.HistoryReference ref = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.parosproxy.paros.model.HistoryReference([CtVariableReadImpl]session, [CtFieldReadImpl]org.parosproxy.paros.model.HistoryReference.TYPE_ZAP_USER, [CtVariableReadImpl]httpMessage);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.parosproxy.paros.extension.history.ExtensionHistory extHistory = [CtInvocationImpl]getHistoryExtension();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]extHistory != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]extHistory.addHistory([CtVariableReadImpl]ref);
            }
            [CtInvocationImpl][CtTypeAccessImpl]org.zaproxy.zap.model.SessionStructure.addPath([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.model.Model.getSingleton().getSession(), [CtVariableReadImpl]ref, [CtVariableReadImpl]httpMessage);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMalformedHeaderException | [CtTypeReferenceImpl]org.parosproxy.paros.db.DatabaseException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.logger.warn([CtLiteralImpl]"Failed to persist message sent:", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Go thru each listener and offer him to take over the connection. The first observer that
     * returns true gets exclusive rights.
     *
     * @param httpMessage
     * 		Contains HTTP request & response.
     * @param inSocket
     * 		Encapsulates the TCP connection to the browser.
     * @param method
     * 		Provides more power to process response.
     * @return Boolean to indicate if socket should be kept open.
     */
    private [CtTypeReferenceImpl]boolean notifyPersistentConnectionListener([CtParameterImpl][CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMessage httpMessage, [CtParameterImpl][CtTypeReferenceImpl]java.net.Socket inSocket, [CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.ZapGetMethod method) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean keepSocketOpen = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.zaproxy.zap.PersistentConnectionListener listener = [CtLiteralImpl]null;
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]persistentConnectionListener) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtFieldReadImpl]persistentConnectionListener.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]listener = [CtInvocationImpl][CtFieldReadImpl]persistentConnectionListener.get([CtVariableReadImpl]i);
                [CtTryImpl]try [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]listener.onHandshakeResponse([CtVariableReadImpl]httpMessage, [CtVariableReadImpl]inSocket, [CtVariableReadImpl]method)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// inform as long as one listener wishes to overtake the connection
                        [CtVariableWriteImpl]keepSocketOpen = [CtLiteralImpl]true;
                        [CtBreakImpl]break;
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.logger.warn([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]keepSocketOpen;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]org.parosproxy.paros.extension.history.ExtensionHistory getHistoryExtension() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]extension == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]extension = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.control.Control.getSingleton().getExtensionLoader().getExtension([CtFieldReadImpl]org.parosproxy.paros.extension.history.ExtensionHistory.class);
        }
        [CtReturnImpl]return [CtFieldReadImpl]extension;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void cleanup() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delegate != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]delegate.shutdown();
            [CtAssignmentImpl][CtFieldWriteImpl]delegate = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.parosproxy.paros.network.HttpSender getDelegate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delegate == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]delegate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.parosproxy.paros.network.HttpSender([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.model.Model.getSingleton().getOptionsParam().getConnectionParam(), [CtInvocationImpl][CtInvocationImpl]getButtonUseTrackingSessionState().isSelected(), [CtFieldReadImpl]org.parosproxy.paros.network.HttpSender.MANUAL_REQUEST_INITIATOR);
            [CtInvocationImpl][CtFieldReadImpl]delegate.setUseCookies([CtInvocationImpl][CtInvocationImpl]getButtonUseCookies().isSelected());
        }
        [CtReturnImpl]return [CtFieldReadImpl]delegate;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton getButtonFollowRedirects() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]followRedirect == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]followRedirect = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JToggleButton([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.class.getResource([CtLiteralImpl]"/resource/icon/16/118.png")));[CtCommentImpl]// Arrow

            [CtInvocationImpl][CtCommentImpl]// turn
            [CtCommentImpl]// around
            [CtCommentImpl]// left
            [CtFieldReadImpl]followRedirect.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Constant.messages.getString([CtLiteralImpl]"manReq.checkBox.followRedirect"));
            [CtInvocationImpl][CtFieldReadImpl]followRedirect.setSelected([CtLiteralImpl]true);
        }
        [CtReturnImpl]return [CtFieldReadImpl]followRedirect;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton getButtonUseTrackingSessionState() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]useTrackingSessionState == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]useTrackingSessionState = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JToggleButton([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.class.getResource([CtLiteralImpl]"/resource/icon/fugue/globe-green.png")));
            [CtInvocationImpl][CtFieldReadImpl]useTrackingSessionState.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Constant.messages.getString([CtLiteralImpl]"manReq.checkBox.useSession"));
            [CtInvocationImpl][CtFieldReadImpl]useTrackingSessionState.addItemListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ItemEvent e) -> [CtInvocationImpl]setUseTrackingSessionState([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getStateChange() == [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.ItemEvent.[CtFieldReferenceImpl]SELECTED));
        }
        [CtReturnImpl]return [CtFieldReadImpl]useTrackingSessionState;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton getButtonUseCookies() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]useCookies == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]useCookies = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JToggleButton([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.class.getResource([CtLiteralImpl]"/resource/icon/fugue/cookie.png")), [CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]useCookies.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Constant.messages.getString([CtLiteralImpl]"manReq.checkBox.useCookies"));
            [CtInvocationImpl][CtFieldReadImpl]useCookies.addItemListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ItemEvent e) -> [CtInvocationImpl]setUseCookies([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getStateChange() == [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.ItemEvent.[CtFieldReferenceImpl]SELECTED));
        }
        [CtReturnImpl]return [CtFieldReadImpl]useCookies;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.swing.JToggleButton getButtonUseCsrf() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// TODO: Add resources for icon and tooltip
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]useCsrf == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]useCsrf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.JToggleButton([CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.swing.ImageIcon([CtInvocationImpl][CtFieldReadImpl]org.parosproxy.paros.extension.manualrequest.http.impl.HttpPanelSender.class.getResource([CtLiteralImpl]"/resource/icon/fugue/csrf.png")), [CtLiteralImpl]true);
            [CtInvocationImpl][CtFieldReadImpl]useCsrf.setToolTipText([CtInvocationImpl][CtTypeAccessImpl]Constant.messages.getString([CtLiteralImpl]"manReq.checkBox.useCSRF"));
            [CtInvocationImpl][CtFieldReadImpl]useCsrf.addItemListener([CtLambdaImpl]([CtParameterImpl]java.awt.event.ItemEvent e) -> [CtInvocationImpl]setUseCsrf([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e.getStateChange() == [CtFieldReadImpl][CtTypeAccessImpl]java.awt.event.ItemEvent.[CtFieldReferenceImpl]SELECTED));
        }
        [CtReturnImpl]return [CtFieldReadImpl]useCsrf;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addPersistentConnectionListener([CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.PersistentConnectionListener listener) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]persistentConnectionListener.add([CtVariableReadImpl]listener);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removePersistentConnectionListener([CtParameterImpl][CtTypeReferenceImpl]org.zaproxy.zap.PersistentConnectionListener listener) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]persistentConnectionListener.remove([CtVariableReadImpl]listener);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * A {@link HttpRedirectionValidator} that enforces the {@link Mode} when validating the {@code URI} of redirections.
     *
     * @see #isRequestValid()
     */
    private class ModeRedirectionValidator implements [CtTypeReferenceImpl]org.zaproxy.zap.network.HttpRedirectionValidator {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean isRequestValid;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.commons.httpclient.URI invalidRedirection;

        [CtConstructorImpl]public ModeRedirectionValidator() [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]isRequestValid = [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void notifyMessageReceived([CtParameterImpl][CtTypeReferenceImpl]org.parosproxy.paros.network.HttpMessage message) [CtBlockImpl]{
            [CtInvocationImpl]persistAndShowMessage([CtVariableReadImpl]message);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean isValid([CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.httpclient.URI redirection) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isValidForCurrentMode([CtVariableReadImpl]redirection)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]isRequestValid = [CtLiteralImpl]false;
                [CtAssignmentImpl][CtFieldWriteImpl]invalidRedirection = [CtVariableReadImpl]redirection;
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]boolean isValidForCurrentMode([CtParameterImpl][CtTypeReferenceImpl]org.apache.commons.httpclient.URI uri) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.control.Control.getSingleton().getMode()) {
                [CtCaseImpl]case [CtFieldReadImpl]safe :
                    [CtReturnImpl]return [CtLiteralImpl]false;
                [CtCaseImpl]case [CtFieldReadImpl]protect :
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.parosproxy.paros.model.Model.getSingleton().getSession().isInScope([CtInvocationImpl][CtVariableReadImpl]uri.toString());
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Tells whether or not the request is valid, that is, all redirections were valid for the
         * current {@link Mode}.
         *
         * @return {@code true} is the request is valid, {@code false} otherwise.
         * @see #getInvalidRedirection()
         */
        public [CtTypeReferenceImpl]boolean isRequestValid() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]isRequestValid;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Gets the invalid redirection, if any.
         *
         * @return the invalid redirection, {@code null} if there was none.
         * @see #isRequestValid()
         */
        public [CtTypeReferenceImpl]org.apache.commons.httpclient.URI getInvalidRedirection() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]invalidRedirection;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setUseTrackingSessionState([CtParameterImpl][CtTypeReferenceImpl]boolean shouldUseTrackingSessionState) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delegate != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]delegate.setUseGlobalState([CtVariableReadImpl]shouldUseTrackingSessionState);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setUseCookies([CtParameterImpl][CtTypeReferenceImpl]boolean shouldUseCookies) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delegate != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]delegate.setUseCookies([CtVariableReadImpl]shouldUseCookies);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setUseCsrf([CtParameterImpl][CtTypeReferenceImpl]boolean shouldUseCsrf) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]delegate != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]delegate.setUseCsrf([CtVariableReadImpl]shouldUseCsrf);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setButtonTrackingSessionStateEnabled([CtParameterImpl][CtTypeReferenceImpl]boolean enabled) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getButtonUseTrackingSessionState().setEnabled([CtVariableReadImpl]enabled);
        [CtInvocationImpl][CtInvocationImpl]getButtonUseTrackingSessionState().setSelected([CtVariableReadImpl]enabled);
    }
}
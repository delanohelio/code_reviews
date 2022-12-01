[CompilationUnitImpl][CtPackageDeclarationImpl]package com.iterable.iterableapi;
[CtUnresolvedImport]import android.content.Context;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtImportImpl]import org.json.JSONException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.net.Uri;
[CtImportImpl]import org.json.JSONArray;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import androidx.annotation.RestrictTo;
[CtUnresolvedImport]import android.os.Handler;
[CtUnresolvedImport]import android.os.Looper;
[CtUnresolvedImport]import com.iterable.iterableapi.IterableInAppHandler.InAppResponse;
[CtUnresolvedImport]import com.iterable.iterableapi.IterableInAppMessage.Trigger.TriggerType;
[CtImportImpl]import org.json.JSONObject;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import androidx.annotation.VisibleForTesting;
[CtClassImpl][CtJavaDocImpl]/**
 * Created by David Truong dt@iterable.com.
 *
 * The IterableInAppManager handles creating and rendering different types of InApp Notifications received from the IterableApi
 */
public class IterableInAppManager implements [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableActivityMonitor.AppStateCallback {
    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String TAG = [CtLiteralImpl]"IterableInAppManager";

    [CtFieldImpl]static final [CtTypeReferenceImpl]long MOVE_TO_FOREGROUND_SYNC_INTERVAL_MS = [CtBinaryOperatorImpl][CtLiteralImpl]60 * [CtLiteralImpl]1000;

    [CtFieldImpl]static final [CtTypeReferenceImpl]int MESSAGES_TO_FETCH = [CtLiteralImpl]100;

    [CtInterfaceImpl]public interface Listener {
        [CtMethodImpl][CtTypeReferenceImpl]void onInboxUpdated();
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi api;

    [CtFieldImpl]private final [CtTypeReferenceImpl]android.content.Context context;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppStorage storage;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppHandler handler;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppDisplayer displayer;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableActivityMonitor activityMonitor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]double inAppDisplayInterval;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager.Listener> listeners = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]long lastSyncTime = [CtLiteralImpl]0;

    [CtFieldImpl]private [CtTypeReferenceImpl]long lastInAppShown = [CtLiteralImpl]0;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean inAppDisplayhold = [CtLiteralImpl]false;

    [CtConstructorImpl]IterableInAppManager([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi iterableApi, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppHandler handler, [CtParameterImpl][CtTypeReferenceImpl]double inAppDisplayInterval) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]iterableApi, [CtVariableReadImpl]handler, [CtVariableReadImpl]inAppDisplayInterval, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppFileStorage([CtInvocationImpl][CtVariableReadImpl]iterableApi.getMainActivityContext()), [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActivityMonitor.getInstance(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppDisplayer([CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActivityMonitor.getInstance()));
    }

    [CtConstructorImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    IterableInAppManager([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi iterableApi, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppHandler handler, [CtParameterImpl][CtTypeReferenceImpl]double inAppDisplayInterval, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppStorage storage, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableActivityMonitor activityMonitor, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppDisplayer displayer) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.api = [CtVariableReadImpl]iterableApi;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtInvocationImpl][CtVariableReadImpl]iterableApi.getMainActivityContext();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.handler = [CtVariableReadImpl]handler;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inAppDisplayInterval = [CtVariableReadImpl]inAppDisplayInterval;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storage = [CtVariableReadImpl]storage;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.displayer = [CtVariableReadImpl]displayer;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activityMonitor = [CtVariableReadImpl]activityMonitor;
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.activityMonitor.addCallback([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the list of available in-app messages
     * This list is synchronized with the server by the SDK
     *
     * @return A {@link List} of {@link IterableInAppMessage} objects
     */
    [CtAnnotationImpl]@androidx.annotation.NonNull
    public synchronized [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> getMessages() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> filteredList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtInvocationImpl][CtFieldReadImpl]storage.getMessages()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]message.isConsumed()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMessageExpired([CtVariableReadImpl]message))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]filteredList.add([CtVariableReadImpl]message);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]filteredList;
    }

    [CtMethodImpl]synchronized [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage getMessageById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storage.getMessage([CtVariableReadImpl]messageId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the list of inbox messages
     *
     * @return A {@link List} of {@link IterableInAppMessage} objects stored in inbox
     */
    [CtAnnotationImpl]@androidx.annotation.NonNull
    public synchronized [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> getInboxMessages() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> filteredList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtInvocationImpl][CtFieldReadImpl]storage.getMessages()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]message.isConsumed()) && [CtUnaryOperatorImpl](![CtInvocationImpl]isMessageExpired([CtVariableReadImpl]message))) && [CtInvocationImpl][CtVariableReadImpl]message.isInboxMessage()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]filteredList.add([CtVariableReadImpl]message);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]filteredList;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the count of unread inbox messages
     *
     * @return Unread inbox messages count
     */
    public synchronized [CtTypeReferenceImpl]int getUnreadInboxMessagesCount() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int unreadInboxMessageCount = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtInvocationImpl]getInboxMessages()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]message.isRead()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]unreadInboxMessageCount++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]unreadInboxMessageCount;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the read flag on an inbox message
     *
     * @param message
     * 		Inbox message object retrieved from {@link IterableInAppManager#getInboxMessages()}
     * @param read
     * 		Read state flag. true = read, false = unread
     */
    public synchronized [CtTypeReferenceImpl]void setRead([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtTypeReferenceImpl]boolean read) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]message.setRead([CtVariableReadImpl]read);
        [CtInvocationImpl]notifyOnChange();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isInAppDisplayhold() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]inAppDisplayhold;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set a hold on InApp Display to prevent showing an inapp. By default the value is set to false.
     *
     * @param hold
     * 		Whether or not to hold showing an in-app.
     */
    public [CtTypeReferenceImpl]void setInAppDisplayhold([CtParameterImpl][CtTypeReferenceImpl]boolean hold) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inAppDisplayhold = [CtVariableReadImpl]hold;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]hold) [CtBlockImpl]{
            [CtInvocationImpl]scheduleProcessing();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Trigger a manual sync. This method is called automatically by the SDK, so there should be no
     * need to call this method from your app.
     */
    [CtTypeReferenceImpl]void syncInApp() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.api.getInAppMessages([CtFieldReadImpl]com.iterable.iterableapi.IterableInAppManager.MESSAGES_TO_FETCH, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]java.lang.String payload) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]payload != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]payload.isEmpty())) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> messages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject mainObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject([CtVariableReadImpl]payload);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray jsonArray = [CtInvocationImpl][CtVariableReadImpl]mainObject.optJSONArray([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_MESSAGE);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]jsonArray != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]jsonArray.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject messageJson = [CtInvocationImpl][CtVariableReadImpl]jsonArray.optJSONObject([CtVariableReadImpl]i);
                                [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableInAppMessage.fromJSONObject([CtVariableReadImpl]messageJson, [CtLiteralImpl]null);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message != [CtLiteralImpl]null) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]messages.add([CtVariableReadImpl]message);
                                }
                            }
                            [CtInvocationImpl]syncWithRemoteQueue([CtVariableReadImpl]messages);
                            [CtAssignmentImpl][CtFieldWriteImpl]lastSyncTime = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.currentTimeMillis();
                        }
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableInAppManager.TAG, [CtInvocationImpl][CtVariableReadImpl]e.toString());
                    }
                }
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Clear all in-app messages.
     * Should be called on user logout.
     */
    [CtTypeReferenceImpl]void reset() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtInvocationImpl][CtFieldReadImpl]storage.getMessages()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]storage.removeMessage([CtVariableReadImpl]message);
        }
        [CtInvocationImpl]notifyOnChange();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Display the in-app message on the screen
     *
     * @param message
     * 		In-App message object retrieved from {@link IterableInAppManager#getMessages()}
     */
    public [CtTypeReferenceImpl]void showMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtInvocationImpl]showMessage([CtVariableReadImpl]message, [CtLiteralImpl]true, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void showMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation location) [CtBlockImpl]{
        [CtInvocationImpl]showMessage([CtVariableReadImpl]message, [CtBinaryOperatorImpl][CtVariableReadImpl]location == [CtFieldReadImpl]IterableInAppLocation.IN_APP, [CtLiteralImpl]null, [CtVariableReadImpl]location);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Display the in-app message on the screen. This method, by default, assumes the current location of activity as InApp. To pass
     * different inAppLocation as paramter, use showMessage method which takes in IterableAppLocation as a parameter.
     *
     * @param message
     * 		In-App message object retrieved from {@link IterableInAppManager#getMessages()}
     * @param consume
     * 		A boolean indicating whether to remove the message from the list after showing
     * @param clickCallback
     * 		A callback that is called when the user clicks on a link in the in-app message
     */
    public [CtTypeReferenceImpl]void showMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtTypeReferenceImpl]boolean consume, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableUrlCallback clickCallback) [CtBlockImpl]{
        [CtInvocationImpl]showMessage([CtVariableReadImpl]message, [CtVariableReadImpl]consume, [CtVariableReadImpl]clickCallback, [CtTypeAccessImpl]IterableInAppLocation.IN_APP);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void showMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtTypeReferenceImpl]boolean consume, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableUrlCallback clickCallback, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation inAppLocation) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]displayer.showMessage([CtVariableReadImpl]message, [CtVariableReadImpl]inAppLocation, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableUrlCallback()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]android.net.Uri url) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clickCallback != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]clickCallback.execute([CtVariableReadImpl]url);
                }
                [CtInvocationImpl]handleInAppClick([CtVariableReadImpl]message, [CtVariableReadImpl]url);
                [CtAssignmentImpl][CtFieldWriteImpl]lastInAppShown = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.currentTimeMillis();
                [CtInvocationImpl]scheduleProcessing();
            }
        })) [CtBlockImpl]{
            [CtInvocationImpl]setRead([CtVariableReadImpl]message, [CtLiteralImpl]true);
            [CtIfImpl]if ([CtVariableReadImpl]consume) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Remove the message without tracking
                removeMessage([CtVariableReadImpl]message);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove message from the list
     *
     * @param message
     * 		The message to be removed
     */
    public synchronized [CtTypeReferenceImpl]void removeMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]message.setConsumed([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]api.inAppConsume([CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
        [CtInvocationImpl]notifyOnChange();
    }

    [CtMethodImpl]public synchronized [CtTypeReferenceImpl]void removeMessage([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppDeleteActionType source, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation clickLocation) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtInvocationImpl][CtVariableReadImpl]message.setConsumed([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]api.inAppConsume([CtVariableReadImpl]message, [CtVariableReadImpl]source, [CtVariableReadImpl]clickLocation);
        [CtInvocationImpl]notifyOnChange();
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.RestrictTo([CtFieldReadImpl]RestrictTo.Scope.LIBRARY_GROUP)
    public [CtTypeReferenceImpl]void handleInAppClick([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]android.net.Uri url) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]url != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]url.toString().isEmpty())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String urlString = [CtInvocationImpl][CtVariableReadImpl]url.toString();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]urlString.startsWith([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ACTION)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// This is an action:// URL, pass that to the custom action handler
                [CtTypeReferenceImpl]java.lang.String actionName = [CtInvocationImpl][CtVariableReadImpl]urlString.replace([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ACTION, [CtLiteralImpl]"");
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtFieldReadImpl]context, [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionCustomAction([CtVariableReadImpl]actionName), [CtTypeAccessImpl]IterableActionSource.IN_APP);
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]urlString.startsWith([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ITBL)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Handle itbl:// URLs, pass that to the custom action handler for compatibility
                [CtTypeReferenceImpl]java.lang.String actionName = [CtInvocationImpl][CtVariableReadImpl]urlString.replace([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ITBL, [CtLiteralImpl]"");
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtFieldReadImpl]context, [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionCustomAction([CtVariableReadImpl]actionName), [CtTypeAccessImpl]IterableActionSource.IN_APP);
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]urlString.startsWith([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ITERABLE)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Handle iterable:// URLs - reserved for actions defined by the SDK only
                [CtTypeReferenceImpl]java.lang.String actionName = [CtInvocationImpl][CtVariableReadImpl]urlString.replace([CtTypeAccessImpl]IterableConstants.URL_SCHEME_ITERABLE, [CtLiteralImpl]"");
                [CtInvocationImpl]handleIterableCustomAction([CtVariableReadImpl]actionName, [CtVariableReadImpl]message);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtFieldReadImpl]context, [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionOpenUrl([CtVariableReadImpl]urlString), [CtTypeAccessImpl]IterableActionSource.IN_APP);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove message from the queue
     * This will actually remove it from the local queue
     * This should only be called when a silent push is received
     *
     * @param messageId
     * 		messageId of the message to be removed
     */
    synchronized [CtTypeReferenceImpl]void removeMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String messageId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtFieldReadImpl]storage.getMessage([CtVariableReadImpl]messageId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]storage.removeMessage([CtVariableReadImpl]message);
        }
        [CtInvocationImpl]notifyOnChange();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isMessageExpired([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]message.getExpiresAt() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.currentTimeMillis() > [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]message.getExpiresAt().getTime();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void syncWithRemoteQueue([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> remoteQueue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean changed = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> remoteQueueMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtVariableReadImpl]remoteQueue) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]remoteQueueMap.put([CtInvocationImpl][CtVariableReadImpl]message.getMessageId(), [CtVariableReadImpl]message);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]storage.getMessage([CtInvocationImpl][CtVariableReadImpl]message.getMessageId()) == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]storage.addMessage([CtVariableReadImpl]message);
                [CtInvocationImpl]onMessageAdded([CtVariableReadImpl]message);
                [CtAssignmentImpl][CtVariableWriteImpl]changed = [CtLiteralImpl]true;
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage localMessage : [CtInvocationImpl][CtFieldReadImpl]storage.getMessages()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]remoteQueueMap.containsKey([CtInvocationImpl][CtVariableReadImpl]localMessage.getMessageId())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]storage.removeMessage([CtVariableReadImpl]localMessage);
                [CtAssignmentImpl][CtVariableWriteImpl]changed = [CtLiteralImpl]true;
            }
        }
        [CtInvocationImpl]scheduleProcessing();
        [CtIfImpl]if ([CtVariableReadImpl]changed) [CtBlockImpl]{
            [CtInvocationImpl]notifyOnChange();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processMessages() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]activityMonitor.isInForeground()) || [CtInvocationImpl]isShowingInApp()) || [CtUnaryOperatorImpl](![CtInvocationImpl]canShowInAppAfterPrevious())) || [CtInvocationImpl]isInAppDisplayhold()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage> messages = [CtInvocationImpl]getMessages();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message : [CtVariableReadImpl]messages) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]message.isProcessed()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]message.isConsumed())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]message.getTriggerType() == [CtFieldReadImpl]com.iterable.iterableapi.IterableInAppMessage.Trigger.TriggerType.IMMEDIATE)) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableInAppManager.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"Calling onNewInApp on " + [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppHandler.InAppResponse response = [CtInvocationImpl][CtFieldReadImpl]handler.onNewInApp([CtVariableReadImpl]message);
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableInAppManager.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"Response: " + [CtVariableReadImpl]response);
                [CtInvocationImpl][CtVariableReadImpl]message.setProcessed([CtLiteralImpl]true);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]response == [CtFieldReadImpl]com.iterable.iterableapi.IterableInAppHandler.InAppResponse.SHOW) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]boolean consume = [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]message.isInboxMessage();
                    [CtInvocationImpl]showMessage([CtVariableReadImpl]message, [CtVariableReadImpl]consume, [CtLiteralImpl]null);
                    [CtReturnImpl]return;
                }
            }
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void scheduleProcessing() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtIfImpl]if ([CtInvocationImpl]canShowInAppAfterPrevious()) [CtBlockImpl]{
            [CtInvocationImpl]processMessages();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler([CtInvocationImpl][CtTypeAccessImpl]android.os.Looper.getMainLooper()).postDelayed([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                    [CtInvocationImpl]processMessages();
                }
            }, [CtBinaryOperatorImpl](([CtTypeReferenceImpl]long) ([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]inAppDisplayInterval - [CtInvocationImpl]getSecondsSinceLastInApp()) + [CtLiteralImpl]2.0) * [CtLiteralImpl]1000)));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onMessageAdded([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]api.trackInAppDelivery([CtVariableReadImpl]message);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isShowingInApp() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]displayer.isShowingInApp();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]double getSecondsSinceLastInApp() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.currentTimeMillis() - [CtFieldReadImpl]lastInAppShown) / [CtLiteralImpl]1000.0;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean canShowInAppAfterPrevious() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getSecondsSinceLastInApp() >= [CtFieldReadImpl]inAppDisplayInterval;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleIterableCustomAction([CtParameterImpl][CtTypeReferenceImpl]java.lang.String actionName, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_ACTION_DELETE.equals([CtVariableReadImpl]actionName)) [CtBlockImpl]{
            [CtInvocationImpl]removeMessage([CtVariableReadImpl]message, [CtTypeAccessImpl]IterableInAppDeleteActionType.DELETE_BUTTON, [CtTypeAccessImpl]IterableInAppLocation.IN_APP);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSwitchToForeground() [CtBlockImpl]{
        [CtInvocationImpl]scheduleProcessing();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.currentTimeMillis() - [CtFieldReadImpl]lastSyncTime) > [CtFieldReadImpl]com.iterable.iterableapi.IterableInAppManager.MOVE_TO_FOREGROUND_SYNC_INTERVAL_MS) [CtBlockImpl]{
            [CtInvocationImpl]syncInApp();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onSwitchToBackground() [CtBlockImpl]{
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addListener([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager.Listener listener) [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]listeners.add([CtVariableReadImpl]listener);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeListener([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager.Listener listener) [CtBlockImpl]{
        [CtSynchronizedImpl]synchronized([CtFieldReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]listeners.remove([CtVariableReadImpl]listener);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void notifyOnChange() [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler([CtInvocationImpl][CtTypeAccessImpl]android.os.Looper.getMainLooper()).post([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtSynchronizedImpl]synchronized([CtFieldReadImpl]listeners) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager.Listener listener : [CtFieldReadImpl]listeners) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]listener.onInboxUpdated();
                    }
                }
            }
        });
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.getcapacitor.plugin.notification;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import androidx.core.app.NotificationCompat;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import android.app.PendingIntent;
[CtUnresolvedImport]import com.getcapacitor.PluginCall;
[CtUnresolvedImport]import androidx.core.app.RemoteInput;
[CtUnresolvedImport]import com.getcapacitor.LogUtils;
[CtUnresolvedImport]import android.app.NotificationChannel;
[CtUnresolvedImport]import androidx.core.app.NotificationManagerCompat;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import android.os.Build;
[CtUnresolvedImport]import com.getcapacitor.android.R;
[CtUnresolvedImport]import android.util.Log;
[CtUnresolvedImport]import com.getcapacitor.JSObject;
[CtUnresolvedImport]import android.app.AlarmManager;
[CtImportImpl]import org.json.JSONException;
[CtImportImpl]import org.json.JSONArray;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import android.graphics.Color;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import android.app.Notification;
[CtImportImpl]import org.json.JSONObject;
[CtUnresolvedImport]import android.app.Activity;
[CtClassImpl][CtJavaDocImpl]/**
 * Contains implementations for all notification actions
 */
public class LocalNotificationManager {
    [CtFieldImpl][CtCommentImpl]// Action constants
    public static final [CtTypeReferenceImpl]java.lang.String NOTIFICATION_INTENT_KEY = [CtLiteralImpl]"LocalNotificationId";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NOTIFICATION_OBJ_INTENT_KEY = [CtLiteralImpl]"LocalNotficationObject";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ACTION_INTENT_KEY = [CtLiteralImpl]"LocalNotificationUserAction";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NOTIFICATION_IS_REMOVABLE_KEY = [CtLiteralImpl]"LocalNotificationRepeating";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String REMOTE_INPUT_KEY = [CtLiteralImpl]"LocalNotificationRemoteInput";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String DEFAULT_NOTIFICATION_CHANNEL_ID = [CtLiteralImpl]"default";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DEFAULT_PRESS_ACTION = [CtLiteralImpl]"tap";

    [CtFieldImpl]private [CtTypeReferenceImpl]android.content.Context context;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.app.Activity activity;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.getcapacitor.plugin.notification.NotificationStorage storage;

    [CtConstructorImpl]public LocalNotificationManager([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.NotificationStorage notificationStorage, [CtParameterImpl][CtTypeReferenceImpl]android.app.Activity activity) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]storage = [CtVariableReadImpl]notificationStorage;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.activity = [CtVariableReadImpl]activity;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]activity;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method extecuted when notification is launched by user from the notification bar.
     */
    public [CtTypeReferenceImpl]com.getcapacitor.JSObject handleNotificationActionPerformed([CtParameterImpl][CtTypeReferenceImpl]android.content.Intent data, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.NotificationStorage notificationStorage) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.d([CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.LogUtils.getPluginTag([CtLiteralImpl]"LN"), [CtBinaryOperatorImpl][CtLiteralImpl]"LocalNotification received: " + [CtInvocationImpl][CtVariableReadImpl]data.getDataString());
        [CtLocalVariableImpl][CtTypeReferenceImpl]int notificationId = [CtInvocationImpl][CtVariableReadImpl]data.getIntExtra([CtFieldReadImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.[CtFieldReferenceImpl]NOTIFICATION_INTENT_KEY, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]notificationId == [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MIN_VALUE) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.d([CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.LogUtils.getPluginTag([CtLiteralImpl]"LN"), [CtLiteralImpl]"Activity started without notification attached");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isRemovable = [CtInvocationImpl][CtVariableReadImpl]data.getBooleanExtra([CtFieldReadImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.[CtFieldReferenceImpl]NOTIFICATION_IS_REMOVABLE_KEY, [CtLiteralImpl]true);
        [CtIfImpl]if ([CtVariableReadImpl]isRemovable) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]notificationStorage.deleteNotification([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtVariableReadImpl]notificationId));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject dataJson = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.getcapacitor.JSObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle results = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.RemoteInput.getResultsFromIntent([CtVariableReadImpl]data);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]results != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence input = [CtInvocationImpl][CtVariableReadImpl]results.getCharSequence([CtFieldReadImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.[CtFieldReferenceImpl]REMOTE_INPUT_KEY);
            [CtInvocationImpl][CtVariableReadImpl]dataJson.put([CtLiteralImpl]"inputValue", [CtInvocationImpl][CtVariableReadImpl]input.toString());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String menuAction = [CtInvocationImpl][CtVariableReadImpl]data.getStringExtra([CtFieldReadImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.[CtFieldReferenceImpl]ACTION_INTENT_KEY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]menuAction != [CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.DEFAULT_PRESS_ACTION) [CtBlockImpl]{
            [CtInvocationImpl]dismissVisibleNotification([CtVariableReadImpl]notificationId);
        }
        [CtInvocationImpl][CtVariableReadImpl]dataJson.put([CtLiteralImpl]"actionId", [CtVariableReadImpl]menuAction);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject request = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String notificationJsonString = [CtInvocationImpl][CtVariableReadImpl]data.getStringExtra([CtFieldReadImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.[CtFieldReferenceImpl]NOTIFICATION_OBJ_INTENT_KEY);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]notificationJsonString != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.getcapacitor.JSObject([CtVariableReadImpl]notificationJsonString);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
        }
        [CtInvocationImpl][CtVariableReadImpl]dataJson.put([CtLiteralImpl]"notification", [CtVariableReadImpl]request);
        [CtReturnImpl]return [CtVariableReadImpl]dataJson;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create notification channel
     */
    public [CtTypeReferenceImpl]void createNotificationChannel() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// TODO allow to create multiple channels
        [CtCommentImpl]// Create the NotificationChannel, but only on API 26+ because
        [CtCommentImpl]// the NotificationChannel class is new and not in the support library
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]Build.VERSION.SDK_INT >= [CtFieldReadImpl]Build.VERSION_CODES.O) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence name = [CtLiteralImpl]"Default";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String description = [CtLiteralImpl]"Default";
            [CtLocalVariableImpl][CtTypeReferenceImpl]int importance = [CtFieldReadImpl]android.app.NotificationManager.IMPORTANCE_DEFAULT;
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.NotificationChannel channel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.app.NotificationChannel([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.DEFAULT_NOTIFICATION_CHANNEL_ID, [CtVariableReadImpl]name, [CtVariableReadImpl]importance);
            [CtInvocationImpl][CtVariableReadImpl]channel.setDescription([CtVariableReadImpl]description);
            [CtLocalVariableImpl][CtCommentImpl]// Register the channel with the system; you can't change the importance
            [CtCommentImpl]// or other notification behaviors after this
            [CtTypeReferenceImpl]android.app.NotificationManager notificationManager = [CtInvocationImpl][CtFieldReadImpl]context.getSystemService([CtFieldReadImpl]android.app.NotificationManager.class);
            [CtInvocationImpl][CtVariableReadImpl]notificationManager.createNotificationChannel([CtVariableReadImpl]channel);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    public [CtTypeReferenceImpl]org.json.JSONArray schedule([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification> localNotifications) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray ids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray();
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.core.app.NotificationManagerCompat notificationManager = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.NotificationManagerCompat.from([CtFieldReadImpl]context);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean notificationsEnabled = [CtInvocationImpl][CtVariableReadImpl]notificationManager.areNotificationsEnabled();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]notificationsEnabled) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]call.error([CtLiteralImpl]"Notifications not enabled on this device");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification localNotification : [CtVariableReadImpl]localNotifications) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer id = [CtInvocationImpl][CtVariableReadImpl]localNotification.getId();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]localNotification.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]call.error([CtLiteralImpl]"LocalNotification missing identifier");
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtInvocationImpl]dismissVisibleNotification([CtVariableReadImpl]id);
            [CtInvocationImpl]cancelTimerForNotification([CtVariableReadImpl]id);
            [CtInvocationImpl]buildNotification([CtVariableReadImpl]notificationManager, [CtVariableReadImpl]localNotification, [CtVariableReadImpl]call);
            [CtInvocationImpl][CtVariableReadImpl]ids.put([CtVariableReadImpl]id);
        }
        [CtReturnImpl]return [CtVariableReadImpl]ids;
    }

    [CtMethodImpl][CtCommentImpl]// TODO Progressbar support
    [CtCommentImpl]// TODO System categories (DO_NOT_DISTURB etc.)
    [CtCommentImpl]// TODO control visibility by flag Notification.VISIBILITY_PRIVATE
    [CtCommentImpl]// TODO Group notifications (setGroup, setGroupSummary, setNumber)
    [CtCommentImpl]// TODO use NotificationCompat.MessagingStyle for latest API
    [CtCommentImpl]// TODO expandable notification NotificationCompat.MessagingStyle
    [CtCommentImpl]// TODO media style notification support NotificationCompat.MediaStyle
    [CtCommentImpl]// TODO custom small/large icons
    private [CtTypeReferenceImpl]void buildNotification([CtParameterImpl][CtTypeReferenceImpl]androidx.core.app.NotificationManagerCompat notificationManager, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification localNotification, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.NotificationCompat.Builder mBuilder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.NotificationCompat.Builder([CtFieldReadImpl][CtThisAccessImpl]this.context, [CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.DEFAULT_NOTIFICATION_CHANNEL_ID).setContentTitle([CtInvocationImpl][CtVariableReadImpl]localNotification.getTitle()).setContentText([CtInvocationImpl][CtVariableReadImpl]localNotification.getBody()).setAutoCancel([CtLiteralImpl]true).setOngoing([CtLiteralImpl]false).setPriority([CtTypeAccessImpl]NotificationCompat.PRIORITY_DEFAULT).setGroupSummary([CtInvocationImpl][CtVariableReadImpl]localNotification.isGroupSummary()).setDefaults([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]android.app.Notification.DEFAULT_SOUND | [CtFieldReadImpl]android.app.Notification.DEFAULT_VIBRATE) | [CtFieldReadImpl]android.app.Notification.DEFAULT_LIGHTS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sound = [CtInvocationImpl][CtVariableReadImpl]localNotification.getSound();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sound != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.net.Uri soundUri = [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtVariableReadImpl]sound);
            [CtInvocationImpl][CtCommentImpl]// Grant permission to use sound
            [CtFieldReadImpl]context.grantUriPermission([CtLiteralImpl]"com.android.systemui", [CtVariableReadImpl]soundUri, [CtTypeAccessImpl]Intent.FLAG_GRANT_READ_URI_PERMISSION);
            [CtInvocationImpl][CtVariableReadImpl]mBuilder.setSound([CtVariableReadImpl]soundUri);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String group = [CtInvocationImpl][CtVariableReadImpl]localNotification.getGroup();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]group != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]mBuilder.setGroup([CtVariableReadImpl]group);
        }
        [CtInvocationImpl][CtVariableReadImpl]mBuilder.setVisibility([CtTypeAccessImpl]Notification.VISIBILITY_PRIVATE);
        [CtInvocationImpl][CtVariableReadImpl]mBuilder.setOnlyAlertOnce([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]mBuilder.setSmallIcon([CtInvocationImpl][CtVariableReadImpl]localNotification.getSmallIcon([CtFieldReadImpl]context));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String iconColor = [CtInvocationImpl][CtVariableReadImpl]localNotification.getIconColor();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]iconColor != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]mBuilder.setColor([CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.parseColor([CtVariableReadImpl]iconColor));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]call.error([CtLiteralImpl]"The iconColor string was not able to be parsed.  Please provide a valid string hexidecimal color code.", [CtVariableReadImpl]ex);
                [CtReturnImpl]return;
            }
        }
        [CtInvocationImpl]createActionIntents([CtVariableReadImpl]localNotification, [CtVariableReadImpl]mBuilder);
        [CtLocalVariableImpl][CtCommentImpl]// notificationId is a unique int for each localNotification that you must define
        [CtTypeReferenceImpl]android.app.Notification buildNotification = [CtInvocationImpl][CtVariableReadImpl]mBuilder.build();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]localNotification.isScheduled()) [CtBlockImpl]{
            [CtInvocationImpl]triggerScheduledNotification([CtVariableReadImpl]buildNotification, [CtVariableReadImpl]localNotification);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]notificationManager.notify([CtInvocationImpl][CtVariableReadImpl]localNotification.getId(), [CtVariableReadImpl]buildNotification);
        }
    }

    [CtMethodImpl][CtCommentImpl]// Create intents for open/dissmis actions
    private [CtTypeReferenceImpl]void createActionIntents([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification localNotification, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.NotificationCompat.Builder mBuilder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Open intent
        [CtTypeReferenceImpl]android.content.Intent intent = [CtInvocationImpl]buildIntent([CtVariableReadImpl]localNotification, [CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.DEFAULT_PRESS_ACTION);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.PendingIntent pendingIntent = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getActivity([CtFieldReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]localNotification.getId(), [CtVariableReadImpl]intent, [CtTypeAccessImpl]PendingIntent.FLAG_CANCEL_CURRENT);
        [CtInvocationImpl][CtVariableReadImpl]mBuilder.setContentIntent([CtVariableReadImpl]pendingIntent);
        [CtLocalVariableImpl][CtCommentImpl]// Build action types
        [CtTypeReferenceImpl]java.lang.String actionTypeId = [CtInvocationImpl][CtVariableReadImpl]localNotification.getActionTypeId();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actionTypeId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]com.getcapacitor.plugin.notification.NotificationAction[] actionGroup = [CtInvocationImpl][CtFieldReadImpl]storage.getActionGroup([CtVariableReadImpl]actionTypeId);
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]actionGroup.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.NotificationAction notificationAction = [CtArrayReadImpl][CtVariableReadImpl]actionGroup[[CtVariableReadImpl]i];
                [CtLocalVariableImpl][CtCommentImpl]// TODO Add custom icons to actions
                [CtTypeReferenceImpl]android.content.Intent actionIntent = [CtInvocationImpl]buildIntent([CtVariableReadImpl]localNotification, [CtInvocationImpl][CtVariableReadImpl]notificationAction.getId());
                [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.PendingIntent actionPendingIntent = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getActivity([CtFieldReadImpl]context, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]localNotification.getId() + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]notificationAction.getId().hashCode(), [CtVariableReadImpl]actionIntent, [CtTypeAccessImpl]PendingIntent.FLAG_CANCEL_CURRENT);
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.NotificationCompat.Action.Builder actionBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.NotificationCompat.Action.Builder([CtFieldReadImpl]R.drawable.ic_transparent, [CtInvocationImpl][CtVariableReadImpl]notificationAction.getTitle(), [CtVariableReadImpl]actionPendingIntent);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]notificationAction.isInput()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.core.app.RemoteInput remoteInput = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]androidx.core.app.RemoteInput.Builder([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.REMOTE_INPUT_KEY).setLabel([CtInvocationImpl][CtVariableReadImpl]notificationAction.getTitle()).build();
                    [CtInvocationImpl][CtVariableReadImpl]actionBuilder.addRemoteInput([CtVariableReadImpl]remoteInput);
                }
                [CtInvocationImpl][CtVariableReadImpl]mBuilder.addAction([CtInvocationImpl][CtVariableReadImpl]actionBuilder.build());
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Dismiss intent
        [CtTypeReferenceImpl]android.content.Intent dissmissIntent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]context, [CtFieldReadImpl]com.getcapacitor.plugin.notification.NotificationDismissReceiver.class);
        [CtInvocationImpl][CtVariableReadImpl]dissmissIntent.setFlags([CtBinaryOperatorImpl][CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_NEW_TASK | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
        [CtInvocationImpl][CtVariableReadImpl]dissmissIntent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.NOTIFICATION_INTENT_KEY, [CtInvocationImpl][CtVariableReadImpl]localNotification.getId());
        [CtInvocationImpl][CtVariableReadImpl]dissmissIntent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.ACTION_INTENT_KEY, [CtLiteralImpl]"dismiss");
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.PendingIntent deleteIntent = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getBroadcast([CtFieldReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]localNotification.getId(), [CtVariableReadImpl]dissmissIntent, [CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]mBuilder.setDeleteIntent([CtVariableReadImpl]deleteIntent);
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    private [CtTypeReferenceImpl]android.content.Intent buildIntent([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification localNotification, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String action) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]context, [CtInvocationImpl][CtFieldReadImpl]activity.getClass());
        [CtInvocationImpl][CtVariableReadImpl]intent.setAction([CtTypeAccessImpl]Intent.ACTION_MAIN);
        [CtInvocationImpl][CtVariableReadImpl]intent.addCategory([CtTypeAccessImpl]Intent.CATEGORY_LAUNCHER);
        [CtInvocationImpl][CtVariableReadImpl]intent.setFlags([CtBinaryOperatorImpl][CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.NOTIFICATION_INTENT_KEY, [CtInvocationImpl][CtVariableReadImpl]localNotification.getId());
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.ACTION_INTENT_KEY, [CtVariableReadImpl]action);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.NOTIFICATION_OBJ_INTENT_KEY, [CtInvocationImpl][CtVariableReadImpl]localNotification.getSource());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotificationSchedule schedule = [CtInvocationImpl][CtVariableReadImpl]localNotification.getSchedule();
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.NOTIFICATION_IS_REMOVABLE_KEY, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]schedule == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]schedule.isRemovable());
        [CtReturnImpl]return [CtVariableReadImpl]intent;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build a notification trigger, such as triggering each N seconds, or
     * on a certain date "shape" (such as every first of the month)
     */
    [CtCommentImpl]// TODO support different AlarmManager.RTC modes depending on priority
    [CtCommentImpl]// TODO restore alarm on device shutdown (requires persistence)
    private [CtTypeReferenceImpl]void triggerScheduledNotification([CtParameterImpl][CtTypeReferenceImpl]android.app.Notification notification, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotification request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.AlarmManager alarmManager = [CtInvocationImpl](([CtTypeReferenceImpl]android.app.AlarmManager) ([CtFieldReadImpl]context.getSystemService([CtTypeAccessImpl]Context.ALARM_SERVICE)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.plugin.notification.LocalNotificationSchedule schedule = [CtInvocationImpl][CtVariableReadImpl]request.getSchedule();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent notificationIntent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]context, [CtFieldReadImpl]com.getcapacitor.plugin.notification.TimedNotificationPublisher.class);
        [CtInvocationImpl][CtVariableReadImpl]notificationIntent.putExtra([CtFieldReadImpl]com.getcapacitor.plugin.notification.LocalNotificationManager.NOTIFICATION_INTENT_KEY, [CtInvocationImpl][CtVariableReadImpl]request.getId());
        [CtInvocationImpl][CtVariableReadImpl]notificationIntent.putExtra([CtTypeAccessImpl]TimedNotificationPublisher.NOTIFICATION_KEY, [CtVariableReadImpl]notification);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.PendingIntent pendingIntent = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getBroadcast([CtFieldReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]request.getId(), [CtVariableReadImpl]notificationIntent, [CtTypeAccessImpl]PendingIntent.FLAG_CANCEL_CURRENT);
        [CtLocalVariableImpl][CtCommentImpl]// Schedule at specific time (with repeating support)
        [CtTypeReferenceImpl]java.util.Date at = [CtInvocationImpl][CtVariableReadImpl]schedule.getAt();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]at != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]at.getTime() < [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date().getTime()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.e([CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.LogUtils.getPluginTag([CtLiteralImpl]"LN"), [CtLiteralImpl]"Scheduled time must be *after* current time");
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]schedule.isRepeating()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long interval = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]at.getTime() - [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date().getTime();
                [CtInvocationImpl][CtVariableReadImpl]alarmManager.setRepeating([CtTypeAccessImpl]AlarmManager.RTC, [CtInvocationImpl][CtVariableReadImpl]at.getTime(), [CtVariableReadImpl]interval, [CtVariableReadImpl]pendingIntent);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]alarmManager.setExact([CtTypeAccessImpl]AlarmManager.RTC, [CtInvocationImpl][CtVariableReadImpl]at.getTime(), [CtVariableReadImpl]pendingIntent);
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Schedule at specific intervals
        [CtTypeReferenceImpl]java.lang.String every = [CtInvocationImpl][CtVariableReadImpl]schedule.getEvery();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]every != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long everyInterval = [CtInvocationImpl][CtVariableReadImpl]schedule.getEveryInterval();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]everyInterval != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long startTime = [CtBinaryOperatorImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date().getTime() + [CtVariableReadImpl]everyInterval;
                [CtInvocationImpl][CtVariableReadImpl]alarmManager.setRepeating([CtTypeAccessImpl]AlarmManager.RTC, [CtVariableReadImpl]startTime, [CtVariableReadImpl]everyInterval, [CtVariableReadImpl]pendingIntent);
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Cron like scheduler
        [CtTypeReferenceImpl]com.getcapacitor.plugin.notification.DateMatch on = [CtInvocationImpl][CtVariableReadImpl]schedule.getOn();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]on != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]notificationIntent.putExtra([CtTypeAccessImpl]TimedNotificationPublisher.CRON_KEY, [CtInvocationImpl][CtVariableReadImpl]on.toMatchString());
            [CtAssignmentImpl][CtVariableWriteImpl]pendingIntent = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getBroadcast([CtFieldReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]request.getId(), [CtVariableReadImpl]notificationIntent, [CtTypeAccessImpl]PendingIntent.FLAG_CANCEL_CURRENT);
            [CtInvocationImpl][CtVariableReadImpl]alarmManager.setExact([CtTypeAccessImpl]AlarmManager.RTC, [CtInvocationImpl][CtVariableReadImpl]on.nextTrigger([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date()), [CtVariableReadImpl]pendingIntent);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void cancel([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Integer> notificationsToCancel = [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.plugin.notification.LocalNotification.getLocalNotificationPendingList([CtVariableReadImpl]call);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]notificationsToCancel != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer id : [CtVariableReadImpl]notificationsToCancel) [CtBlockImpl]{
                [CtInvocationImpl]dismissVisibleNotification([CtVariableReadImpl]id);
                [CtInvocationImpl]cancelTimerForNotification([CtVariableReadImpl]id);
                [CtInvocationImpl][CtFieldReadImpl]storage.deleteNotification([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtVariableReadImpl]id));
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]call.success();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void cancelTimerForNotification([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer notificationId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]context, [CtFieldReadImpl]com.getcapacitor.plugin.notification.TimedNotificationPublisher.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.PendingIntent pi = [CtInvocationImpl][CtTypeAccessImpl]android.app.PendingIntent.getBroadcast([CtFieldReadImpl]context, [CtVariableReadImpl]notificationId, [CtVariableReadImpl]intent, [CtLiteralImpl]0);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pi != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.app.AlarmManager alarmManager = [CtInvocationImpl](([CtTypeReferenceImpl]android.app.AlarmManager) ([CtFieldReadImpl]context.getSystemService([CtTypeAccessImpl]Context.ALARM_SERVICE)));
            [CtInvocationImpl][CtVariableReadImpl]alarmManager.cancel([CtVariableReadImpl]pi);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void dismissVisibleNotification([CtParameterImpl][CtTypeReferenceImpl]int notificationId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.core.app.NotificationManagerCompat notificationManager = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.NotificationManagerCompat.from([CtFieldReadImpl][CtThisAccessImpl]this.context);
        [CtInvocationImpl][CtVariableReadImpl]notificationManager.cancel([CtVariableReadImpl]notificationId);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean areNotificationsEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.core.app.NotificationManagerCompat notificationManager = [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.NotificationManagerCompat.from([CtFieldReadImpl]context);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]notificationManager.areNotificationsEnabled();
    }
}
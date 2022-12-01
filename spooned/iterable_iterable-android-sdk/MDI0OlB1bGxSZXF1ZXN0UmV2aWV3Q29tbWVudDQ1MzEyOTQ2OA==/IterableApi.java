[CompilationUnitImpl][CtPackageDeclarationImpl]package com.iterable.iterableapi;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.os.Bundle;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtImportImpl]import org.json.JSONException;
[CtUnresolvedImport]import android.content.Intent;
[CtImportImpl]import org.json.JSONArray;
[CtUnresolvedImport]import com.iterable.iterableapi.ddl.DeviceInfo;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import androidx.annotation.RestrictTo;
[CtUnresolvedImport]import android.content.SharedPreferences;
[CtUnresolvedImport]import android.app.Application;
[CtUnresolvedImport]import com.iterable.iterableapi.ddl.MatchFpResponse;
[CtUnresolvedImport]import androidx.core.app.NotificationManagerCompat;
[CtImportImpl]import org.json.JSONObject;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import android.os.Build;
[CtUnresolvedImport]import android.app.Activity;
[CtUnresolvedImport]import androidx.annotation.VisibleForTesting;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtJavaDocImpl]/**
 * Created by David Truong dt@iterable.com
 */
[CtCommentImpl]// ---------------------------------------------------------------------------------------
[CtCommentImpl]// endregion
public class IterableApi {
    [CtFieldImpl][CtCommentImpl]// region Variables
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    private static final [CtTypeReferenceImpl]java.lang.String TAG = [CtLiteralImpl]"IterableApi";

    [CtFieldImpl][CtJavaDocImpl]/**
     * {@link IterableApi} singleton instance
     */
    static volatile [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi sharedInstance = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi();

    [CtFieldImpl]private [CtTypeReferenceImpl]android.content.Context _applicationContext;

    [CtFieldImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableConfig config;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String _apiKey;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String _email;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String _userId;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String _authToken;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean _debugMode;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.os.Bundle _payloadData;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.iterable.iterableapi.IterableNotificationData _notificationData;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String _deviceId;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean _firstForegroundHandled;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager inAppManager;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String inboxSessionId;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> deviceAttributes = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Constructor
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    IterableApi() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]config = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableConfig.Builder().build();
    }

    [CtConstructorImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    IterableApi([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager inAppManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]config = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableConfig.Builder().build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inAppManager = [CtVariableReadImpl]inAppManager;
    }

    [CtMethodImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Getters/Setters
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtJavaDocImpl]/**
     * Sets the icon to be displayed in notifications.
     * The icon name should match the resource name stored in the /res/drawable directory.
     *
     * @param iconName
     */
    public [CtTypeReferenceImpl]void setNotificationIcon([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String iconName) [CtBlockImpl]{
        [CtInvocationImpl]com.iterable.iterableapi.IterableApi.setNotificationIcon([CtFieldReadImpl]_applicationContext, [CtVariableReadImpl]iconName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves the payload string for a given key.
     * Used for deeplinking and retrieving extra data passed down along with a campaign.
     *
     * @param key
     * @return Returns the requested payload data from the current push campaign if it exists.
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    public [CtTypeReferenceImpl]java.lang.String getPayloadData([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]_payloadData != [CtLiteralImpl]null ? [CtInvocationImpl][CtFieldReadImpl]_payloadData.getString([CtVariableReadImpl]key, [CtLiteralImpl]null) : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Retrieves all of the payload as a single Bundle Object
     *
     * @return Bundle
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    public [CtTypeReferenceImpl]android.os.Bundle getPayloadData() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]_payloadData;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns an {@link IterableInAppManager} that can be used to manage in-app messages.
     * Make sure the Iterable API is initialized before calling this method.
     *
     * @return {@link IterableInAppManager} instance
     */
    [CtAnnotationImpl]@androidx.annotation.NonNull
    public [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager getInAppManager() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]inAppManager == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]inAppManager = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppManager([CtThisAccessImpl]this, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.inAppHandler, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.inAppDisplayInterval);
            [CtInvocationImpl][CtFieldReadImpl]inAppManager.syncInApp();
        }
        [CtReturnImpl]return [CtFieldReadImpl]inAppManager;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the attribution information ({@link IterableAttributionInfo}) for last push open
     * or app link click from an email.
     *
     * @return {@link IterableAttributionInfo} Object containing
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    public [CtTypeReferenceImpl]com.iterable.iterableapi.IterableAttributionInfo getAttributionInfo() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAttributionInfo.fromJSONObject([CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.retrieveExpirableJsonObject([CtInvocationImpl]getPreferences(), [CtTypeAccessImpl]IterableConstants.SHARED_PREFS_ATTRIBUTION_INFO_KEY));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Stores attribution information.
     *
     * @param attributionInfo
     * 		Attribution information object
     */
    [CtTypeReferenceImpl]void setAttributionInfo([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableAttributionInfo attributionInfo) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_applicationContext == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"setAttributionInfo: Iterable SDK is not initialized with a context.");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.saveExpirableJsonObject([CtInvocationImpl]getPreferences(), [CtTypeAccessImpl]IterableConstants.SHARED_PREFS_ATTRIBUTION_INFO_KEY, [CtInvocationImpl][CtVariableReadImpl]attributionInfo.toJSONObject(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]3600 * [CtFieldReadImpl]IterableConstants.SHARED_PREFS_ATTRIBUTION_INFO_EXPIRATION_HOURS) * [CtLiteralImpl]1000);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the current context for the application.
     *
     * @return  */
    [CtTypeReferenceImpl]android.content.Context getMainActivityContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]_applicationContext;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets debug mode.
     *
     * @param debugMode
     */
    [CtTypeReferenceImpl]void setDebugMode([CtParameterImpl][CtTypeReferenceImpl]boolean debugMode) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_debugMode = [CtVariableReadImpl]debugMode;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the current state of the debug mode.
     *
     * @return  */
    [CtTypeReferenceImpl]boolean getDebugMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]_debugMode;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the payload for a given intent if it is from Iterable.
     *
     * @param intent
     */
    [CtTypeReferenceImpl]void setPayloadData([CtParameterImpl][CtTypeReferenceImpl]android.content.Intent intent) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle extras = [CtInvocationImpl][CtVariableReadImpl]intent.getExtras();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]extras != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]extras.containsKey([CtTypeAccessImpl]IterableConstants.ITERABLE_DATA_KEY)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableNotificationHelper.isGhostPush([CtVariableReadImpl]extras))) [CtBlockImpl]{
            [CtInvocationImpl]setPayloadData([CtVariableReadImpl]extras);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the payload bundle.
     *
     * @param bundle
     */
    [CtTypeReferenceImpl]void setPayloadData([CtParameterImpl][CtTypeReferenceImpl]android.os.Bundle bundle) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_payloadData = [CtVariableReadImpl]bundle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the IterableNotification data
     *
     * @param data
     */
    [CtTypeReferenceImpl]void setNotificationData([CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableNotificationData data) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]_notificationData = [CtVariableReadImpl]data;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]data != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]setAttributionInfo([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableAttributionInfo([CtInvocationImpl][CtVariableReadImpl]data.getCampaignId(), [CtInvocationImpl][CtVariableReadImpl]data.getTemplateId(), [CtInvocationImpl][CtVariableReadImpl]data.getMessageId()));
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.util.HashMap getDeviceAttributes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]deviceAttributes;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDeviceAttribute([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]deviceAttributes.put([CtVariableReadImpl]key, [CtVariableReadImpl]value);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeDeviceAttribute([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]deviceAttributes.remove([CtVariableReadImpl]key);
    }

    [CtMethodImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Public Functions
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtJavaDocImpl]/**
     * Get {@link IterableApi} singleton instance
     *
     * @return {@link IterableApi} singleton instance
     */
    [CtAnnotationImpl]@androidx.annotation.NonNull
    public static [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApi getInstance() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.iterable.iterableapi.IterableApi.sharedInstance;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initializes IterableApi
     * This method must be called from {@link Application#onCreate()}
     * Note: Make sure you also call {@link #setEmail(String)} or {@link #setUserId(String)} before calling other methods
     *
     * @param context
     * 		Application context
     * @param apiKey
     * 		Iterable Mobile API key
     */
    public static [CtTypeReferenceImpl]void initialize([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String apiKey) [CtBlockImpl]{
        [CtInvocationImpl]com.iterable.iterableapi.IterableApi.initialize([CtVariableReadImpl]context, [CtVariableReadImpl]apiKey, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initializes IterableApi
     * This method must be called from {@link Application#onCreate()}
     * Note: Make sure you also call {@link #setEmail(String)} or {@link #setUserId(String)} before calling other methods
     *
     * @param context
     * 		Application context
     * @param apiKey
     * 		Iterable Mobile API key
     * @param config
     * 		{@link IterableConfig} object holding SDK configuration options
     */
    public static [CtTypeReferenceImpl]void initialize([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String apiKey, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableConfig config) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance._applicationContext = [CtInvocationImpl][CtVariableReadImpl]context.getApplicationContext();
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance._apiKey = [CtVariableReadImpl]apiKey;
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance.config = [CtVariableReadImpl]config;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance.config == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance.config = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableConfig.Builder().build();
        }
        [CtInvocationImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.sharedInstance.retrieveEmailAndUserId();
        [CtInvocationImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.sharedInstance.checkForDeferredDeeplink();
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActivityMonitor.getInstance().registerLifecycleCallbacks([CtVariableReadImpl]context);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActivityMonitor.getInstance().addCallback([CtFieldReadImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance.activityMonitorListener);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set user email used for API calls
     * Calling this or `setUserId:` is required before making any API calls.
     *
     * Note: This clears userId and persists the user email so you only need to call this once when the user logs in.
     *
     * @param email
     * 		User email
     */
    public [CtTypeReferenceImpl]void setEmail([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String email) [CtBlockImpl]{
        [CtInvocationImpl]setEmail([CtVariableReadImpl]email, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set user email used for API calls
     * Calling this or `setUserId:` is required before making any API calls.
     *
     * Note: This clears userId and persists the user email so you only need to call this once when the user logs in.
     *
     * @param email
     * 		User email
     * @param authToken
     * 		Authorization token
     */
    public [CtTypeReferenceImpl]void setEmail([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_email != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]_email.equals([CtVariableReadImpl]email)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]_email == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]_userId == [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]email == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]onLogOut();
        [CtAssignmentImpl][CtFieldWriteImpl]_email = [CtVariableReadImpl]email;
        [CtAssignmentImpl][CtFieldWriteImpl]_userId = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]_authToken = [CtVariableReadImpl]token;
        [CtInvocationImpl]storeAuthData();
        [CtInvocationImpl]onLogIn();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set user ID used for API calls
     * Calling this or `setEmail:` is required before making any API calls.
     *
     * Note: This clears user email and persists the user ID so you only need to call this once when the user logs in.
     *
     * @param userId
     * 		User ID
     */
    public [CtTypeReferenceImpl]void setUserId([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtInvocationImpl]setUserId([CtVariableReadImpl]userId, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set user ID used for API calls
     * Calling this or `setEmail:` is required before making any API calls.
     *
     * Note: This clears user email and persists the user ID so you only need to call this once when the user logs in.
     *
     * @param userId
     * 		User ID
     * @param authToken
     * 		Authorization token
     */
    public [CtTypeReferenceImpl]void setUserId([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_userId != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]_userId.equals([CtVariableReadImpl]userId)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]_email == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]_userId == [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]userId == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]onLogOut();
        [CtAssignmentImpl][CtFieldWriteImpl]_email = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]_userId = [CtVariableReadImpl]userId;
        [CtAssignmentImpl][CtFieldWriteImpl]_authToken = [CtVariableReadImpl]token;
        [CtInvocationImpl]storeAuthData();
        [CtInvocationImpl]onLogIn();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks a click on the uri if it is an iterable link.
     *
     * @param uri
     * 		the
     * @param onCallback
     * 		Calls the callback handler with the destination location
     * 		or the original url if it is not a interable link.
     */
    public static [CtTypeReferenceImpl]void getAndTrackDeeplink([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String uri, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler onCallback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableDeeplinkManager.getAndTrackDeeplink([CtVariableReadImpl]uri, [CtVariableReadImpl]onCallback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handles an App Link
     * For Iterable links, it will track the click and retrieve the original URL, pass it to
     * {@link IterableUrlHandler} for handling
     * If it's not an Iterable link, it just passes the same URL to {@link IterableUrlHandler}
     *
     * Call this from {@link Activity#onCreate(Bundle)} and {@link Activity#onNewIntent(Intent)}
     * in your deep link handler activity
     *
     * @param uri
     * 		the URL obtained from {@link Intent#getData()} in your deep link
     * 		handler activity
     * @return  */
    public static [CtTypeReferenceImpl]boolean handleAppLink([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String uri) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableDeeplinkManager.isIterableDeeplink([CtVariableReadImpl]uri)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableDeeplinkManager.getAndTrackDeeplink([CtVariableReadImpl]uri, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]java.lang.String originalUrl) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableAction action = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionOpenUrl([CtVariableReadImpl]originalUrl);
                    [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtInvocationImpl][CtInvocationImpl]com.iterable.iterableapi.IterableApi.getInstance().getMainActivityContext(), [CtVariableReadImpl]action, [CtTypeAccessImpl]IterableActionSource.APP_LINK);
                }
            });
            [CtReturnImpl]return [CtLiteralImpl]true;
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableAction action = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionOpenUrl([CtVariableReadImpl]uri);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtInvocationImpl][CtInvocationImpl]com.iterable.iterableapi.IterableApi.getInstance().getMainActivityContext(), [CtVariableReadImpl]action, [CtTypeAccessImpl]IterableActionSource.APP_LINK);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Debugging function to send API calls to different url endpoints.
     *
     * @param url
     */
    public static [CtTypeReferenceImpl]void overrideURLEndpointPath([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]IterableRequest.overrideUrl = [CtVariableReadImpl]url;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns whether or not the intent was sent from Iterable.
     */
    public [CtTypeReferenceImpl]boolean isIterableIntent([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]android.content.Intent intent) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]intent != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle extras = [CtInvocationImpl][CtVariableReadImpl]intent.getExtras();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]extras != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]extras.containsKey([CtTypeAccessImpl]IterableConstants.ITERABLE_DATA_KEY);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers a device token with Iterable.
     * Make sure {@link IterableConfig#pushIntegrationName} is set before calling this.
     *
     * @param token
     * 		Push token obtained from GCM or FCM
     */
    public [CtTypeReferenceImpl]void registerDeviceToken([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtInvocationImpl]registerDeviceToken([CtFieldReadImpl]_email, [CtFieldReadImpl]_userId, [CtInvocationImpl]getPushIntegrationName(), [CtVariableReadImpl]token, [CtFieldReadImpl]deviceAttributes);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void registerDeviceToken([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]java.lang.String applicationName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]java.lang.String token, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> deviceAttributes) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]token != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Thread registrationThread = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                [CtMethodImpl]public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                    [CtInvocationImpl]registerDeviceToken([CtVariableReadImpl]email, [CtVariableReadImpl]userId, [CtVariableReadImpl]applicationName, [CtVariableReadImpl]token, [CtLiteralImpl]null, [CtVariableReadImpl]deviceAttributes);
                }
            });
            [CtInvocationImpl][CtVariableReadImpl]registrationThread.start();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track an event.
     *
     * @param eventName
     */
    public [CtTypeReferenceImpl]void track([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String eventName) [CtBlockImpl]{
        [CtInvocationImpl]track([CtVariableReadImpl]eventName, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track an event.
     *
     * @param eventName
     * @param dataFields
     */
    public [CtTypeReferenceImpl]void track([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]org.json.JSONObject dataFields) [CtBlockImpl]{
        [CtInvocationImpl]track([CtVariableReadImpl]eventName, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtVariableReadImpl]dataFields);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track an event.
     *
     * @param eventName
     * @param campaignId
     * @param templateId
     */
    public [CtTypeReferenceImpl]void track([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]int campaignId, [CtParameterImpl][CtTypeReferenceImpl]int templateId) [CtBlockImpl]{
        [CtInvocationImpl]track([CtVariableReadImpl]eventName, [CtVariableReadImpl]campaignId, [CtVariableReadImpl]templateId, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track an event.
     *
     * @param eventName
     * @param campaignId
     * @param templateId
     * @param dataFields
     */
    public [CtTypeReferenceImpl]void track([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]int campaignId, [CtParameterImpl][CtTypeReferenceImpl]int templateId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]org.json.JSONObject dataFields) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_EVENT_NAME, [CtVariableReadImpl]eventName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]campaignId != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_CAMPAIGN_ID, [CtVariableReadImpl]campaignId);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]templateId != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_TEMPLATE_ID, [CtVariableReadImpl]templateId);
            }
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DATA_FIELDS, [CtVariableReadImpl]dataFields);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks a purchase.
     *
     * @param total
     * 		total purchase amount
     * @param items
     * 		list of purchased items
     */
    public [CtTypeReferenceImpl]void trackPurchase([CtParameterImpl][CtTypeReferenceImpl]double total, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.CommerceItem> items) [CtBlockImpl]{
        [CtInvocationImpl]trackPurchase([CtVariableReadImpl]total, [CtVariableReadImpl]items, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks a purchase.
     *
     * @param total
     * 		total purchase amount
     * @param items
     * 		list of purchased items
     * @param dataFields
     * 		a `JSONObject` containing any additional information to save along with the event
     */
    public [CtTypeReferenceImpl]void trackPurchase([CtParameterImpl][CtTypeReferenceImpl]double total, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iterable.iterableapi.CommerceItem> items, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]org.json.JSONObject dataFields) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray itemsArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.CommerceItem item : [CtVariableReadImpl]items) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]itemsArray.put([CtInvocationImpl][CtVariableReadImpl]item.toJSONObject());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject userObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]userObject);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_USER, [CtVariableReadImpl]userObject);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_ITEMS, [CtVariableReadImpl]itemsArray);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_TOTAL, [CtVariableReadImpl]total);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataFields != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DATA_FIELDS, [CtVariableReadImpl]dataFields);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_PURCHASE, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the current user's email.
     * Also updates the current email in this IterableAPI instance if the API call was successful.
     *
     * @param newEmail
     * 		New email
     */
    public [CtTypeReferenceImpl]void updateEmail([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]java.lang.String newEmail) [CtBlockImpl]{
        [CtInvocationImpl]updateEmail([CtVariableReadImpl]newEmail, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the current user's email.
     * Also updates the current email and authToken in this IterableAPI instance if the API call was successful.
     *
     * @param newEmail
     * 		New email
     * @param authToken
     * 		Authorization token
     */
    public [CtTypeReferenceImpl]void updateEmail([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]java.lang.String newEmail, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.String authToken) [CtBlockImpl]{
        [CtInvocationImpl]updateEmail([CtVariableReadImpl]newEmail, [CtVariableReadImpl]authToken, [CtLiteralImpl]null, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the current user's email.
     * Also updates the current email and authToken in this IterableAPI instance if the API call was successful.
     *
     * @param newEmail
     * 		New email
     * @param authToken
     * @param successHandler
     * 		Success handler. Called when the server returns a success code.
     * @param failureHandler
     * 		Failure handler. Called when the server call failed.
     */
    public [CtTypeReferenceImpl]void updateEmail([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    final [CtTypeReferenceImpl]java.lang.String newEmail, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl]java.lang.String authToken, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.SuccessHandler successHandler, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.FailureHandler failureHandler) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"The Iterable SDK must be initialized with email or userId before " + [CtLiteralImpl]"calling updateEmail");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]failureHandler != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]failureHandler.onFailure([CtBinaryOperatorImpl][CtLiteralImpl]"The Iterable SDK must be initialized with email or " + [CtLiteralImpl]"userId before calling updateEmail", [CtLiteralImpl]null);
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_email != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_CURRENT_EMAIL, [CtFieldReadImpl]_email);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_CURRENT_USERID, [CtFieldReadImpl]_userId);
            }
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_NEW_EMAIL, [CtVariableReadImpl]newEmail);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_UPDATE_EMAIL, [CtVariableReadImpl]requestJSON, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.SuccessHandler()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
                [CtTypeReferenceImpl]org.json.JSONObject data) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_email != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]_email = [CtVariableReadImpl]newEmail;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_authToken != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]_authToken = [CtVariableReadImpl]authToken;
                    }
                    [CtInvocationImpl]storeAuthData();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]successHandler != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]successHandler.onSuccess([CtVariableReadImpl]data);
                    }
                }
            }, [CtVariableReadImpl]failureHandler);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the current user.
     *
     * @param dataFields
     */
    public [CtTypeReferenceImpl]void updateUser([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject dataFields) [CtBlockImpl]{
        [CtInvocationImpl]updateUser([CtVariableReadImpl]dataFields, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the current user.
     *
     * @param dataFields
     * @param mergeNestedObjects
     */
    public [CtTypeReferenceImpl]void updateUser([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject dataFields, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean mergeNestedObjects) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtIfImpl][CtCommentImpl]// Create the user by userId if it doesn't exist
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_email == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]_userId != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_PREFER_USER_ID, [CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DATA_FIELDS, [CtVariableReadImpl]dataFields);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MERGE_NESTED_OBJECTS, [CtVariableReadImpl]mergeNestedObjects);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_UPDATE_USER, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getPushIntegrationName() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.pushIntegrationName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.pushIntegrationName;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]_applicationContext.getPackageName();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers for push notifications.
     * Make sure the API is initialized with {@link IterableConfig#pushIntegrationName} defined, and
     * user email or user ID is set before calling this method.
     */
    public [CtTypeReferenceImpl]void registerForPush() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistrationData data = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistrationData([CtFieldReadImpl]_email, [CtFieldReadImpl]_userId, [CtInvocationImpl]getPushIntegrationName(), [CtFieldReadImpl]IterablePushRegistrationData.PushRegistrationAction.ENABLE);
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistration().execute([CtVariableReadImpl]data);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Disables the device from push notifications
     */
    public [CtTypeReferenceImpl]void disablePush() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistrationData data = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistrationData([CtFieldReadImpl]_email, [CtFieldReadImpl]_userId, [CtInvocationImpl]getPushIntegrationName(), [CtFieldReadImpl]IterablePushRegistrationData.PushRegistrationAction.DISABLE);
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterablePushRegistration().execute([CtVariableReadImpl]data);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the user subscription preferences. Passing in an empty array will clear the list, passing in null will not modify the list
     *
     * @param emailListIds
     * @param unsubscribedChannelIds
     * @param unsubscribedMessageTypeIds
     */
    public [CtTypeReferenceImpl]void updateSubscriptions([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] emailListIds, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] unsubscribedChannelIds, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] unsubscribedMessageTypeIds) [CtBlockImpl]{
        [CtInvocationImpl]updateSubscriptions([CtVariableReadImpl]emailListIds, [CtVariableReadImpl]unsubscribedChannelIds, [CtVariableReadImpl]unsubscribedMessageTypeIds, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void updateSubscriptions([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] emailListIds, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] unsubscribedChannelIds, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] unsubscribedMessageTypeIds, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtArrayTypeReferenceImpl]java.lang.Integer[] subscribedMessageTypeIDs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer campaignId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer templateId) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
        [CtInvocationImpl]tryAddArrayToJSON([CtVariableReadImpl]requestJSON, [CtTypeAccessImpl]IterableConstants.KEY_EMAIL_LIST_IDS, [CtVariableReadImpl]emailListIds);
        [CtInvocationImpl]tryAddArrayToJSON([CtVariableReadImpl]requestJSON, [CtTypeAccessImpl]IterableConstants.KEY_UNSUB_CHANNEL, [CtVariableReadImpl]unsubscribedChannelIds);
        [CtInvocationImpl]tryAddArrayToJSON([CtVariableReadImpl]requestJSON, [CtTypeAccessImpl]IterableConstants.KEY_UNSUB_MESSAGE, [CtVariableReadImpl]unsubscribedMessageTypeIds);
        [CtInvocationImpl]tryAddArrayToJSON([CtVariableReadImpl]requestJSON, [CtTypeAccessImpl]IterableConstants.KEY_SUB_MESSAGE, [CtVariableReadImpl]subscribedMessageTypeIDs);
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]campaignId != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]campaignId != [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.putOpt([CtTypeAccessImpl]IterableConstants.KEY_CAMPAIGN_ID, [CtVariableReadImpl]campaignId);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]templateId != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]templateId != [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.putOpt([CtTypeAccessImpl]IterableConstants.KEY_TEMPLATE_ID, [CtVariableReadImpl]templateId);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtInvocationImpl][CtVariableReadImpl]e.toString());
        }
        [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_UPDATE_USER_SUBS, [CtVariableReadImpl]requestJSON);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attempts to add an array as a JSONArray to a JSONObject
     *
     * @param requestJSON
     * @param key
     * @param value
     */
    [CtTypeReferenceImpl]void tryAddArrayToJSON([CtParameterImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] value) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]requestJSON != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]key != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]value != [CtLiteralImpl]null))[CtBlockImpl]
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray mJSONArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]value));
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtVariableReadImpl]key, [CtVariableReadImpl]mJSONArray);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtInvocationImpl][CtVariableReadImpl]e.toString());
            }

    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * In-app messages are now shown automatically, and you can customize it via {@link IterableInAppHandler}
     * If you need to show messages manually, see {@link IterableInAppManager#getMessages()} and
     * {@link IterableInAppManager#showMessage(IterableInAppMessage)}
     *
     * @deprecated Please check our migration guide here:
    https://github.com/iterable/iterable-android-sdk/#migrating-in-app-messages-from-the-previous-version-of-the-sdk
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    [CtTypeReferenceImpl]void spawnInAppNotification([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler clickCallback) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of InAppNotifications from Iterable; passes the result to the callback.
     *
     * @deprecated Use {@link IterableInAppManager#getMessages()} instead
     * @param count
     * 		the number of messages to fetch
     * @param onCallback
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void getInAppMessages([CtParameterImpl][CtTypeReferenceImpl]int count, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler onCallback) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_COUNT, [CtVariableReadImpl]count);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_PLATFORM, [CtTypeAccessImpl]IterableConstants.ITBL_PLATFORM_ANDROID);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITBL_KEY_SDK_VERSION, [CtTypeAccessImpl]IterableConstants.ITBL_KEY_SDK_VERSION_NUMBER);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_PACKAGE_NAME, [CtInvocationImpl][CtFieldReadImpl]_applicationContext.getPackageName());
            [CtInvocationImpl]sendGetRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_GET_INAPP_MESSAGES, [CtVariableReadImpl]requestJSON, [CtVariableReadImpl]onCallback);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks an in-app open.
     *
     * @param messageId
     */
    public [CtTypeReferenceImpl]void trackInAppOpen([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtVariableReadImpl]messageId);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_OPEN, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void trackInAppOpen([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation location) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtInvocationImpl]getInAppManager().getMessageById([CtVariableReadImpl]messageId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]trackInAppOpen([CtVariableReadImpl]message, [CtVariableReadImpl]location);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.w([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"trackInAppOpen: could not find an in-app message with ID: " + [CtVariableReadImpl]messageId);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks an in-app open.
     *
     * @param message
     * 		in-app message
     */
    public [CtTypeReferenceImpl]void trackInAppOpen([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation location) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInAppOpen: message is null");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_CONTEXT, [CtInvocationImpl]getInAppMessageContext([CtVariableReadImpl]message, [CtVariableReadImpl]location));
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]location == [CtFieldReadImpl]IterableInAppLocation.INBOX) [CtBlockImpl]{
                [CtInvocationImpl]addInboxSessionID([CtVariableReadImpl]requestJSON);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_OPEN, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void trackInAppClick([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String clickedUrl, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation location) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtInvocationImpl]getInAppManager().getMessageById([CtVariableReadImpl]messageId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]trackInAppClick([CtVariableReadImpl]message, [CtVariableReadImpl]clickedUrl, [CtVariableReadImpl]location);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.w([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"trackInAppClick: could not find an in-app message with ID: " + [CtVariableReadImpl]messageId);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks an InApp click.
     *
     * @param messageId
     * @param clickedUrl
     */
    public [CtTypeReferenceImpl]void trackInAppClick([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String clickedUrl) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtVariableReadImpl]messageId);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_CLICKED_URL, [CtVariableReadImpl]clickedUrl);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_CLICK, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks an InApp click.
     *
     * @param message
     * 		in-app message
     * @param clickedUrl
     */
    public [CtTypeReferenceImpl]void trackInAppClick([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String clickedUrl, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation clickLocation) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInAppClick: message is null");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_CLICKED_URL, [CtVariableReadImpl]clickedUrl);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_CONTEXT, [CtInvocationImpl]getInAppMessageContext([CtVariableReadImpl]message, [CtVariableReadImpl]clickLocation));
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clickLocation == [CtFieldReadImpl]IterableInAppLocation.INBOX) [CtBlockImpl]{
                [CtInvocationImpl]addInboxSessionID([CtVariableReadImpl]requestJSON);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_CLICK, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void trackInAppClose([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String clickedURL, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppCloseAction closeAction, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation clickLocation) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtInvocationImpl]getInAppManager().getMessageById([CtVariableReadImpl]messageId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]trackInAppClose([CtVariableReadImpl]message, [CtVariableReadImpl]clickedURL, [CtVariableReadImpl]closeAction, [CtVariableReadImpl]clickLocation);
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.w([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"trackInAppClose: could not find an in-app message with ID: " + [CtVariableReadImpl]messageId);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks InApp Close events.
     *
     * @param message
     * 		in-app message
     * @param clickedURL
     * 		clicked Url if available
     * @param clickLocation
     * 		location of the click
     */
    [CtTypeReferenceImpl]void trackInAppClose([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String clickedURL, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppCloseAction closeAction, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation clickLocation) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInAppClose: message is null");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_EMAIL, [CtInvocationImpl]getEmail());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_USER_ID, [CtInvocationImpl]getUserId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_CLICKED_URL, [CtVariableReadImpl]clickedURL);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_CLOSE_ACTION, [CtInvocationImpl][CtVariableReadImpl]closeAction.toString());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_CONTEXT, [CtInvocationImpl]getInAppMessageContext([CtVariableReadImpl]message, [CtVariableReadImpl]clickLocation));
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clickLocation == [CtFieldReadImpl]IterableInAppLocation.INBOX) [CtBlockImpl]{
                [CtInvocationImpl]addInboxSessionID([CtVariableReadImpl]requestJSON);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_CLOSE, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]void trackInAppDelivery([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInAppDelivery: message is null");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_CONTEXT, [CtInvocationImpl]getInAppMessageContext([CtVariableReadImpl]message, [CtLiteralImpl]null));
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INAPP_DELIVERY, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Consumes an InApp message.
     *
     * @param messageId
     */
    public [CtTypeReferenceImpl]void inAppConsume([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String messageId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message = [CtInvocationImpl][CtInvocationImpl]getInAppManager().getMessageById([CtVariableReadImpl]messageId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"inAppConsume: message is null");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]inAppConsume([CtVariableReadImpl]message, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.printInfo();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks InApp delete.
     * This method from informs Iterable about inApp messages deleted with additional paramters.
     * Call this method from places where inApp deletion are invoked by user. The messages can be swiped to delete or can be deleted using the link to delete button.
     *
     * @param message
     * 		message object
     * @param source
     * 		An enum describing how the in App delete was triggered
     * @param clickLocation
     * 		The module in which the action happened
     */
    public [CtTypeReferenceImpl]void inAppConsume([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppDeleteActionType source, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation clickLocation) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_USER_ID, [CtInvocationImpl]getUserId());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtInvocationImpl][CtVariableReadImpl]message.getMessageId());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]source != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_DELETE_ACTION, [CtInvocationImpl][CtVariableReadImpl]source.toString());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clickLocation != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_CONTEXT, [CtInvocationImpl]getInAppMessageContext([CtVariableReadImpl]message, [CtVariableReadImpl]clickLocation));
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clickLocation == [CtFieldReadImpl]IterableInAppLocation.INBOX) [CtBlockImpl]{
                [CtInvocationImpl]addInboxSessionID([CtVariableReadImpl]requestJSON);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_INAPP_CONSUME, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.RestrictTo([CtFieldReadImpl]RestrictTo.Scope.LIBRARY_GROUP)
    public [CtTypeReferenceImpl]void trackInboxSession([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInboxSession session) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]session == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInboxSession: session is null");
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]session.sessionStartTime == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]session.sessionEndTime == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"trackInboxSession: sessionStartTime and sessionEndTime must be set");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_SESSION_START, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]session.sessionStartTime.getTime());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_SESSION_END, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]session.sessionEndTime.getTime());
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_START_TOTAL_MESSAGE_COUNT, [CtFieldReadImpl][CtVariableReadImpl]session.startTotalMessageCount);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_START_UNREAD_MESSAGE_COUNT, [CtFieldReadImpl][CtVariableReadImpl]session.startUnreadMessageCount);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_END_TOTAL_MESSAGE_COUNT, [CtFieldReadImpl][CtVariableReadImpl]session.endTotalMessageCount);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_END_UNREAD_MESSAGE_COUNT, [CtFieldReadImpl][CtVariableReadImpl]session.endUnreadMessageCount);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]session.impressions != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray impressionsJsonArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableInboxSession.Impression impression : [CtFieldReadImpl][CtVariableReadImpl]session.impressions) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject impressionJson = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
                    [CtInvocationImpl][CtVariableReadImpl]impressionJson.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtFieldReadImpl][CtVariableReadImpl]impression.messageId);
                    [CtInvocationImpl][CtVariableReadImpl]impressionJson.put([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_SILENT_INBOX, [CtFieldReadImpl][CtVariableReadImpl]impression.silentInbox);
                    [CtInvocationImpl][CtVariableReadImpl]impressionJson.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_IMP_DISPLAY_COUNT, [CtFieldReadImpl][CtVariableReadImpl]impression.displayCount);
                    [CtInvocationImpl][CtVariableReadImpl]impressionJson.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_IMP_DISPLAY_DURATION, [CtFieldReadImpl][CtVariableReadImpl]impression.duration);
                    [CtInvocationImpl][CtVariableReadImpl]impressionsJsonArray.put([CtVariableReadImpl]impressionJson);
                }
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.ITERABLE_INBOX_IMPRESSIONS, [CtVariableReadImpl]impressionsJsonArray);
            }
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.putOpt([CtTypeAccessImpl]IterableConstants.KEY_DEVICE_INFO, [CtInvocationImpl]getDeviceInfoJson());
            [CtInvocationImpl]addInboxSessionID([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_INBOX_SESSION, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Package-Protected Functions
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtJavaDocImpl]/**
     * Get user email
     *
     * @return user email
     */
    [CtTypeReferenceImpl]java.lang.String getEmail() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]_email;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get user ID
     *
     * @return user ID
     */
    [CtTypeReferenceImpl]java.lang.String getUserId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]_userId;
    }

    [CtMethodImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Protected Functions
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtJavaDocImpl]/**
     * Set the notification icon with the given iconName.
     *
     * @param context
     * @param iconName
     */
    static [CtTypeReferenceImpl]void setNotificationIcon([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String iconName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.SharedPreferences sharedPref = [CtInvocationImpl][CtVariableReadImpl]context.getSharedPreferences([CtTypeAccessImpl]IterableConstants.NOTIFICATION_ICON_NAME, [CtTypeAccessImpl]Context.MODE_PRIVATE);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.SharedPreferences.Editor editor = [CtInvocationImpl][CtVariableReadImpl]sharedPref.edit();
        [CtInvocationImpl][CtVariableReadImpl]editor.putString([CtTypeAccessImpl]IterableConstants.NOTIFICATION_ICON_NAME, [CtVariableReadImpl]iconName);
        [CtInvocationImpl][CtVariableReadImpl]editor.commit();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the stored notification icon.
     *
     * @param context
     * @return  */
    static [CtTypeReferenceImpl]java.lang.String getNotificationIcon([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.SharedPreferences sharedPref = [CtInvocationImpl][CtVariableReadImpl]context.getSharedPreferences([CtTypeAccessImpl]IterableConstants.NOTIFICATION_ICON_NAME, [CtTypeAccessImpl]Context.MODE_PRIVATE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String iconName = [CtInvocationImpl][CtVariableReadImpl]sharedPref.getString([CtTypeAccessImpl]IterableConstants.NOTIFICATION_ICON_NAME, [CtLiteralImpl]"");
        [CtReturnImpl]return [CtVariableReadImpl]iconName;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void trackPushOpen([CtParameterImpl][CtTypeReferenceImpl]int campaignId, [CtParameterImpl][CtTypeReferenceImpl]int templateId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String messageId) [CtBlockImpl]{
        [CtInvocationImpl]trackPushOpen([CtVariableReadImpl]campaignId, [CtVariableReadImpl]templateId, [CtVariableReadImpl]messageId, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks when a push notification is opened on device.
     *
     * @param campaignId
     * @param templateId
     */
    protected [CtTypeReferenceImpl]void trackPushOpen([CtParameterImpl][CtTypeReferenceImpl]int campaignId, [CtParameterImpl][CtTypeReferenceImpl]int templateId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String messageId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]org.json.JSONObject dataFields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataFields == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dataFields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
            }
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_CAMPAIGN_ID, [CtVariableReadImpl]campaignId);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_TEMPLATE_ID, [CtVariableReadImpl]templateId);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_MESSAGE_ID, [CtVariableReadImpl]messageId);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.putOpt([CtTypeAccessImpl]IterableConstants.KEY_DATA_FIELDS, [CtVariableReadImpl]dataFields);
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_TRACK_PUSH_OPEN, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void disableToken([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String token) [CtBlockImpl]{
        [CtInvocationImpl]disableToken([CtVariableReadImpl]email, [CtVariableReadImpl]userId, [CtVariableReadImpl]token, [CtLiteralImpl]null, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Internal api call made from IterablePushRegistration after a registrationToken is obtained.
     * It disables the device for all users with this device by default. If `email` or `userId` is provided, it will disable the device for the specific user.
     *
     * @param email
     * 		User email for whom to disable the device.
     * @param userId
     * 		User ID for whom to disable the device.
     * @param token
     * 		The device token
     */
    protected [CtTypeReferenceImpl]void disableToken([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String token, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.SuccessHandler onSuccess, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.FailureHandler onFailure) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_TOKEN, [CtVariableReadImpl]token);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]email != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_EMAIL, [CtVariableReadImpl]email);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userId != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_USER_ID, [CtVariableReadImpl]userId);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_DISABLE_DEVICE, [CtVariableReadImpl]requestJSON, [CtVariableReadImpl]onSuccess, [CtVariableReadImpl]onFailure);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Registers the GCM registration ID with Iterable.
     *
     * @param applicationName
     * @param token
     * @param dataFields
     */
    protected [CtTypeReferenceImpl]void registerDeviceToken([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String userId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String applicationName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String token, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]org.json.JSONObject dataFields, [CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> deviceAttributes) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]checkSDKInitialization()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]token == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"registerDeviceToken: token is null");
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]applicationName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"registerDeviceToken: applicationName is null, check that pushIntegrationName is set in IterableConfig");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]addEmailOrUserIdToJson([CtVariableReadImpl]requestJSON);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataFields == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dataFields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtVariableReadImpl]deviceAttributes.entrySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
            }
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.FIREBASE_TOKEN_TYPE, [CtTypeAccessImpl]IterableConstants.MESSAGING_PLATFORM_FIREBASE);
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.FIREBASE_COMPATIBLE, [CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_BRAND, [CtTypeAccessImpl]Build.BRAND);[CtCommentImpl]// brand: google

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_MANUFACTURER, [CtTypeAccessImpl]Build.MANUFACTURER);[CtCommentImpl]// manufacturer: samsung

            [CtInvocationImpl][CtVariableReadImpl]dataFields.putOpt([CtTypeAccessImpl]IterableConstants.DEVICE_ADID, [CtInvocationImpl]getAdvertisingId());[CtCommentImpl]// ADID: "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX"

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_SYSTEM_NAME, [CtTypeAccessImpl]Build.DEVICE);[CtCommentImpl]// device name: toro

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_SYSTEM_VERSION, [CtTypeAccessImpl]Build.VERSION.RELEASE);[CtCommentImpl]// version: 4.0.4

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_MODEL, [CtTypeAccessImpl]Build.MODEL);[CtCommentImpl]// device model: Galaxy Nexus

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_SDK_VERSION, [CtTypeAccessImpl]Build.VERSION.SDK_INT);[CtCommentImpl]// sdk version/api level: 15

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_ID, [CtInvocationImpl]getDeviceId());[CtCommentImpl]// Random UUID

            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_APP_PACKAGE_NAME, [CtInvocationImpl][CtFieldReadImpl]_applicationContext.getPackageName());
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_APP_VERSION, [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.getAppVersion([CtFieldReadImpl]_applicationContext));
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_APP_BUILD, [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableUtil.getAppVersionCode([CtFieldReadImpl]_applicationContext));
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_ITERABLE_SDK_VERSION, [CtTypeAccessImpl]IterableConstants.ITBL_KEY_SDK_VERSION_NUMBER);
            [CtInvocationImpl][CtVariableReadImpl]dataFields.put([CtTypeAccessImpl]IterableConstants.DEVICE_NOTIFICATIONS_ENABLED, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.NotificationManagerCompat.from([CtFieldReadImpl]_applicationContext).areNotificationsEnabled());
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject device = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
            [CtInvocationImpl][CtVariableReadImpl]device.put([CtTypeAccessImpl]IterableConstants.KEY_TOKEN, [CtVariableReadImpl]token);
            [CtInvocationImpl][CtVariableReadImpl]device.put([CtTypeAccessImpl]IterableConstants.KEY_PLATFORM, [CtTypeAccessImpl]IterableConstants.MESSAGING_PLATFORM_GOOGLE);
            [CtInvocationImpl][CtVariableReadImpl]device.put([CtTypeAccessImpl]IterableConstants.KEY_APPLICATION_NAME, [CtVariableReadImpl]applicationName);
            [CtInvocationImpl][CtVariableReadImpl]device.putOpt([CtTypeAccessImpl]IterableConstants.KEY_DATA_FIELDS, [CtVariableReadImpl]dataFields);
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_DEVICE, [CtVariableReadImpl]device);
            [CtIfImpl][CtCommentImpl]// Create the user by userId if it doesn't exist
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]email == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]userId != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_PREFER_USER_ID, [CtLiteralImpl]true);
            }
            [CtInvocationImpl]sendPostRequest([CtTypeAccessImpl]IterableConstants.ENDPOINT_REGISTER_DEVICE_TOKEN, [CtVariableReadImpl]requestJSON);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"registerDeviceToken: exception", [CtVariableReadImpl]e);
        }
    }

    [CtFieldImpl][CtCommentImpl]// ---------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region Private Functions
    [CtCommentImpl]// ---------------------------------------------------------------------------------------
    private final [CtTypeReferenceImpl]IterableActivityMonitor.AppStateCallback activityMonitorListener = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableActivityMonitor.AppStateCallback()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onSwitchToForeground() [CtBlockImpl]{
            [CtInvocationImpl]onForeground();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onSwitchToBackground() [CtBlockImpl]{
        }
    };

    [CtMethodImpl]private [CtTypeReferenceImpl]void onForeground() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]_firstForegroundHandled) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_firstForegroundHandled = [CtLiteralImpl]true;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.[CtFieldReferenceImpl]sharedInstance.config.autoPushRegistration && [CtInvocationImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.sharedInstance.isInitialized()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Performing automatic push registration");
                [CtInvocationImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableApi.sharedInstance.registerForPush();
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isInitialized() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]_apiKey != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]_email != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl]_userId != [CtLiteralImpl]null));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean checkSDKInitialization() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isInitialized()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Iterable SDK must be initialized with an API key and user email/userId before calling SDK methods");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]android.content.SharedPreferences getPreferences() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]_applicationContext.getSharedPreferences([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_FILE, [CtTypeAccessImpl]Context.MODE_PRIVATE);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addInboxSessionID([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject requestJSON) throws [CtTypeReferenceImpl]org.json.JSONException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.inboxSessionId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_INBOX_SESSION_ID, [CtFieldReadImpl][CtThisAccessImpl]this.inboxSessionId);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sends the POST request to Iterable.
     * Performs network operations on an async thread instead of the main thread.
     *
     * @param resourcePath
     * @param json
     */
    [CtTypeReferenceImpl]void sendPostRequest([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String resourcePath, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject json) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest([CtFieldReadImpl]_apiKey, [CtVariableReadImpl]resourcePath, [CtVariableReadImpl]json, [CtFieldReadImpl]IterableApiRequest.POST, [CtFieldReadImpl]_authToken, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableRequest().execute([CtVariableReadImpl]request);
    }

    [CtMethodImpl][CtTypeReferenceImpl]void sendPostRequest([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String resourcePath, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject json, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.SuccessHandler onSuccess, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.FailureHandler onFailure) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest([CtFieldReadImpl]_apiKey, [CtVariableReadImpl]resourcePath, [CtVariableReadImpl]json, [CtFieldReadImpl]IterableApiRequest.POST, [CtFieldReadImpl]_authToken, [CtVariableReadImpl]onSuccess, [CtVariableReadImpl]onFailure);
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableRequest().execute([CtVariableReadImpl]request);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sends a GET request to Iterable.
     * Performs network operations on an async thread instead of the main thread.
     *
     * @param resourcePath
     * @param json
     */
    [CtTypeReferenceImpl]void sendGetRequest([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String resourcePath, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]org.json.JSONObject json, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.IterableActionHandler onCallback) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest([CtFieldReadImpl]_apiKey, [CtVariableReadImpl]resourcePath, [CtVariableReadImpl]json, [CtFieldReadImpl]IterableApiRequest.GET, [CtLiteralImpl]null, [CtVariableReadImpl]onCallback);
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableRequest().execute([CtVariableReadImpl]request);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds the current email or userID to the json request.
     *
     * @param requestJSON
     */
    private [CtTypeReferenceImpl]void addEmailOrUserIdToJson([CtParameterImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_email != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_EMAIL, [CtFieldReadImpl]_email);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestJSON.put([CtTypeAccessImpl]IterableConstants.KEY_USER_ID, [CtFieldReadImpl]_userId);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the advertisingId if available
     *
     * @return  */
    private [CtTypeReferenceImpl]java.lang.String getAdvertisingId() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String advertisingId = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class adClass = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Class.forName([CtLiteralImpl]"com.google.android.gms.ads.identifier.AdvertisingIdClient");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]adClass != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object advertisingIdInfo = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adClass.getMethod([CtLiteralImpl]"getAdvertisingIdInfo", [CtFieldReadImpl]android.content.Context.class).invoke([CtLiteralImpl]null, [CtFieldReadImpl]_applicationContext);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]advertisingIdInfo != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]advertisingId = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]advertisingIdInfo.getClass().getMethod([CtLiteralImpl]"getId").invoke([CtVariableReadImpl]advertisingIdInfo)));
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"ClassNotFoundException: Can't track ADID. " + [CtLiteralImpl]"Check that play-services-ads is added to the dependencies.", [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.w([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Error while fetching advertising ID", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]advertisingId;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDeviceId() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_deviceId == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]_deviceId = [CtInvocationImpl][CtInvocationImpl]getPreferences().getString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_DEVICEID_KEY, [CtLiteralImpl]null);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]_deviceId == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]_deviceId = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString();
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getPreferences().edit().putString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_DEVICEID_KEY, [CtFieldReadImpl]_deviceId).apply();
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]_deviceId;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void storeAuthData() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.SharedPreferences.Editor editor = [CtInvocationImpl][CtInvocationImpl]getPreferences().edit();
            [CtInvocationImpl][CtVariableReadImpl]editor.putString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_EMAIL_KEY, [CtFieldReadImpl]_email);
            [CtInvocationImpl][CtVariableReadImpl]editor.putString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_USERID_KEY, [CtFieldReadImpl]_userId);
            [CtInvocationImpl][CtVariableReadImpl]editor.putString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_AUTHTOKEN_KEY, [CtFieldReadImpl]_authToken);
            [CtInvocationImpl][CtVariableReadImpl]editor.commit();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Error while persisting email/userId", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void retrieveEmailAndUserId() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.SharedPreferences prefs = [CtInvocationImpl]getPreferences();
            [CtAssignmentImpl][CtFieldWriteImpl]_email = [CtInvocationImpl][CtVariableReadImpl]prefs.getString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_EMAIL_KEY, [CtLiteralImpl]null);
            [CtAssignmentImpl][CtFieldWriteImpl]_userId = [CtInvocationImpl][CtVariableReadImpl]prefs.getString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_USERID_KEY, [CtLiteralImpl]null);
            [CtAssignmentImpl][CtFieldWriteImpl]_authToken = [CtInvocationImpl][CtVariableReadImpl]prefs.getString([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_AUTHTOKEN_KEY, [CtLiteralImpl]null);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Error while retrieving email/userId/authToken", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onLogOut() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.autoPushRegistration && [CtInvocationImpl]isInitialized()) [CtBlockImpl]{
            [CtInvocationImpl]disablePush();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onLogIn() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.autoPushRegistration && [CtInvocationImpl]isInitialized()) [CtBlockImpl]{
            [CtInvocationImpl]registerForPush();
        }
        [CtInvocationImpl][CtInvocationImpl]getInAppManager().syncInApp();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean getDDLChecked() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getPreferences().getBoolean([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_DDL_CHECKED_KEY, [CtLiteralImpl]false);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setDDLChecked([CtParameterImpl][CtTypeReferenceImpl]boolean value) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getPreferences().edit().putBoolean([CtTypeAccessImpl]IterableConstants.SHARED_PREFS_DDL_CHECKED_KEY, [CtVariableReadImpl]value).apply();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void checkForDeferredDeeplink() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]config.checkForDeferredDeeplink) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]getDDLChecked()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject requestJSON = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.ddl.DeviceInfo.createDeviceInfo([CtFieldReadImpl]_applicationContext).toJSONObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest request = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableApiRequest([CtFieldReadImpl]_apiKey, [CtFieldReadImpl]IterableConstants.BASE_URL_LINKS, [CtFieldReadImpl]IterableConstants.ENDPOINT_DDL_MATCH, [CtVariableReadImpl]requestJSON, [CtFieldReadImpl]IterableApiRequest.POST, [CtLiteralImpl]null, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.SuccessHandler()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onSuccess([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
                [CtTypeReferenceImpl]org.json.JSONObject data) [CtBlockImpl]{
                    [CtInvocationImpl]handleDDL([CtVariableReadImpl]data);
                }
            }, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableHelper.FailureHandler()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onFailure([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
                [CtTypeReferenceImpl]java.lang.String reason, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
                [CtTypeReferenceImpl]org.json.JSONObject data) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Error while checking deferred deep link: " + [CtVariableReadImpl]reason) + [CtLiteralImpl]", response: ") + [CtVariableReadImpl]data);
                }
            });
            [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableRequest().execute([CtVariableReadImpl]request);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Error while checking deferred deep link", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleDDL([CtParameterImpl][CtTypeReferenceImpl]org.json.JSONObject response) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"handleDDL: " + [CtVariableReadImpl]response);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.ddl.MatchFpResponse matchFpResponse = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.ddl.MatchFpResponse.fromJSONObject([CtVariableReadImpl]response);
            [CtIfImpl]if ([CtFieldReadImpl][CtVariableReadImpl]matchFpResponse.isMatch) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableAction action = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableAction.actionOpenUrl([CtFieldReadImpl][CtVariableReadImpl]matchFpResponse.destinationUrl);
                [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableActionRunner.executeAction([CtInvocationImpl]getMainActivityContext(), [CtVariableReadImpl]action, [CtTypeAccessImpl]IterableActionSource.APP_LINK);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Error while handling deferred deep link", [CtVariableReadImpl]e);
        }
        [CtInvocationImpl]setDDLChecked([CtLiteralImpl]true);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.json.JSONObject getInAppMessageContext([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppMessage message, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableInAppLocation location) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject messageContext = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isSilentInbox = [CtInvocationImpl][CtVariableReadImpl]message.isSilentInboxMessage();
            [CtInvocationImpl][CtVariableReadImpl]messageContext.putOpt([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_SAVE_TO_INBOX, [CtInvocationImpl][CtVariableReadImpl]message.isInboxMessage());
            [CtInvocationImpl][CtVariableReadImpl]messageContext.putOpt([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_SILENT_INBOX, [CtVariableReadImpl]isSilentInbox);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]location != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]messageContext.putOpt([CtTypeAccessImpl]IterableConstants.ITERABLE_IN_APP_LOCATION, [CtInvocationImpl][CtVariableReadImpl]location.toString());
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Could not populate messageContext JSON", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]messageContext;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    private [CtTypeReferenceImpl]org.json.JSONObject getDeviceInfoJson() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONObject deviceInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]deviceInfo.putOpt([CtTypeAccessImpl]IterableConstants.DEVICE_ID, [CtInvocationImpl]getDeviceId());
            [CtInvocationImpl][CtVariableReadImpl]deviceInfo.putOpt([CtTypeAccessImpl]IterableConstants.KEY_PLATFORM, [CtTypeAccessImpl]IterableConstants.ITBL_PLATFORM_ANDROID);
            [CtInvocationImpl][CtVariableReadImpl]deviceInfo.putOpt([CtTypeAccessImpl]IterableConstants.DEVICE_APP_PACKAGE_NAME, [CtInvocationImpl][CtFieldReadImpl]_applicationContext.getPackageName());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableApi.TAG, [CtLiteralImpl]"Could not populate deviceInfo JSON", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]deviceInfo;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.RestrictTo([CtFieldReadImpl]RestrictTo.Scope.LIBRARY_GROUP)
    public [CtTypeReferenceImpl]void setInboxSessionId([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String inboxSessionId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inboxSessionId = [CtVariableReadImpl]inboxSessionId;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.RestrictTo([CtFieldReadImpl]RestrictTo.Scope.LIBRARY_GROUP)
    public [CtTypeReferenceImpl]void clearInboxSessionId() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inboxSessionId = [CtLiteralImpl]null;
    }
}
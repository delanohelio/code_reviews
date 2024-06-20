[CompilationUnitImpl][CtJavaDocImpl]/**
 * Modified MIT License
 *
 * Copyright 2017 OneSignal
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * 1. The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * 2. All copies of substantial portions of the Software may only be used in connection
 * with services provided by OneSignal.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
[CtPackageDeclarationImpl]package com.onesignal;
[CtUnresolvedImport]import android.content.Context;
[CtImportImpl]import java.util.Locale;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import android.content.pm.PackageManager;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import android.support.annotation.NonNull;
[CtUnresolvedImport]import android.content.pm.PackageInfo;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import android.content.ContentResolver;
[CtUnresolvedImport]import android.os.Handler;
[CtUnresolvedImport]import android.os.Looper;
[CtUnresolvedImport]import android.telephony.TelephonyManager;
[CtImportImpl]import java.security.MessageDigest;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import android.os.Build;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import static com.onesignal.OneSignal.Log;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import android.net.ConnectivityManager;
[CtUnresolvedImport]import com.huawei.hms.api.HuaweiApiAvailability;
[CtImportImpl]import java.util.regex.Pattern;
[CtImportImpl]import org.json.JSONException;
[CtUnresolvedImport]import android.net.NetworkInfo;
[CtImportImpl]import org.json.JSONArray;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import org.json.JSONObject;
[CtUnresolvedImport]import android.support.annotation.Nullable;
[CtUnresolvedImport]import android.support.v4.app.NotificationManagerCompat;
[CtUnresolvedImport]import android.app.Activity;
[CtUnresolvedImport]import android.content.pm.ApplicationInfo;
[CtUnresolvedImport]import com.google.android.gms.common.GoogleApiAvailability;
[CtUnresolvedImport]import android.content.res.Resources;
[CtClassImpl]class OSUtils {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]int UNINITIALIZABLE_STATUS = [CtUnaryOperatorImpl]-[CtLiteralImpl]999;

    [CtFieldImpl]public static [CtTypeReferenceImpl]int MAX_NETWORK_REQUEST_ATTEMPT_COUNT = [CtLiteralImpl]3;

    [CtFieldImpl]static final [CtArrayTypeReferenceImpl]int[] NO_RETRY_NETWROK_REQUEST_STATUS_CODES = [CtNewArrayImpl]new int[]{ [CtLiteralImpl]401, [CtLiteralImpl]402, [CtLiteralImpl]403, [CtLiteralImpl]404, [CtLiteralImpl]410 };

    [CtEnumImpl]public enum SchemaType {

        [CtEnumValueImpl]DATA([CtLiteralImpl]"data"),
        [CtEnumValueImpl]HTTPS([CtLiteralImpl]"https"),
        [CtEnumValueImpl]HTTP([CtLiteralImpl]"http");
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String text;

        [CtConstructorImpl]SchemaType([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String text) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.text = [CtVariableReadImpl]text;
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]com.onesignal.OSUtils.SchemaType fromString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String text) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.onesignal.OSUtils.SchemaType type : [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.SchemaType.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]type.text.equalsIgnoreCase([CtVariableReadImpl]text)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]type;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]boolean shouldRetryNetworkRequest([CtParameterImpl][CtTypeReferenceImpl]int statusCode) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int code : [CtFieldReadImpl]com.onesignal.OSUtils.NO_RETRY_NETWROK_REQUEST_STATUS_CODES)[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]statusCode == [CtVariableReadImpl]code)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]false;


        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtTypeReferenceImpl]int initializationChecker([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String oneSignalAppId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int subscribableStatus = [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int deviceType = [CtInvocationImpl]getDeviceType();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// noinspection ResultOfMethodCallIgnored
            [CtTypeAccessImpl]java.util.UUID.fromString([CtVariableReadImpl]oneSignalAppId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.FATAL, [CtLiteralImpl]"OneSignal AppId format is invalid.\nExample: \'b2f7f966-d8cc-11e4-bed1-df8f05be55ba\'\n", [CtVariableReadImpl]t);
            [CtReturnImpl]return [CtFieldReadImpl]com.onesignal.OSUtils.UNINITIALIZABLE_STATUS;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"b2f7f966-d8cc-11e4-bed1-df8f05be55ba".equals([CtVariableReadImpl]oneSignalAppId) || [CtInvocationImpl][CtLiteralImpl]"5eb5a37e-b458-11e3-ac11-000c2940e62c".equals([CtVariableReadImpl]oneSignalAppId))[CtBlockImpl]
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.ERROR, [CtLiteralImpl]"OneSignal Example AppID detected, please update to your app's id found on OneSignal.com");

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deviceType == [CtFieldReadImpl]UserState.DEVICE_TYPE_ANDROID) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer pushErrorType = [CtInvocationImpl]checkForGooglePushLibrary();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pushErrorType != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]subscribableStatus = [CtVariableReadImpl]pushErrorType;

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer supportErrorType = [CtInvocationImpl]checkAndroidSupportLibrary([CtVariableReadImpl]context);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]supportErrorType != [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]subscribableStatus = [CtVariableReadImpl]supportErrorType;

        [CtReturnImpl]return [CtVariableReadImpl]subscribableStatus;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean hasFCMLibrary() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Using class instead of Strings for proguard compatibility
            [CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]com.google.firebase.messaging.FirebaseMessaging.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasGCMLibrary() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]com.google.android.gms.gcm.GoogleCloudMessaging.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasHMSAvailabilityLibrary() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]com.huawei.hms.api.HuaweiApiAvailability.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasHMSPushKitLibrary() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]com.huawei.hms.aaid.HmsInstanceId.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasHMSAGConnectLibrary() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]com.huawei.agconnect.config.AGConnectServicesConfig.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean hasAllHMSLibrariesForPushKit() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// NOTE: hasHMSAvailabilityLibrary technically is not required,
        [CtCommentImpl]// just used as recommend way to detect if "HMS Core" app exists and is enabled
        return [CtBinaryOperatorImpl][CtInvocationImpl]com.onesignal.OSUtils.hasHMSAGConnectLibrary() && [CtInvocationImpl]com.onesignal.OSUtils.hasHMSPushKitLibrary();
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.Integer checkForGooglePushLibrary() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasFCMLibrary = [CtInvocationImpl]com.onesignal.OSUtils.hasFCMLibrary();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasGCMLibrary = [CtInvocationImpl]com.onesignal.OSUtils.hasGCMLibrary();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]hasFCMLibrary) && [CtUnaryOperatorImpl](![CtVariableReadImpl]hasGCMLibrary)) [CtBlockImpl]{
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.FATAL, [CtLiteralImpl]"The Firebase FCM library is missing! Please make sure to include it in your project.");
            [CtReturnImpl]return [CtFieldReadImpl]UserState.PUSH_STATUS_MISSING_FIREBASE_FCM_LIBRARY;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hasGCMLibrary && [CtUnaryOperatorImpl](![CtVariableReadImpl]hasFCMLibrary))[CtBlockImpl]
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.WARN, [CtLiteralImpl]"GCM Library detected, please upgrade to Firebase FCM library as GCM is deprecated!");

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hasGCMLibrary && [CtVariableReadImpl]hasFCMLibrary)[CtBlockImpl]
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.WARN, [CtLiteralImpl]"Both GCM & FCM Libraries detected! Please remove the deprecated GCM library.");

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasWakefulBroadcastReceiver() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]android.support.v4.content.WakefulBroadcastReceiver.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasNotificationManagerCompat() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]android.support.v4.app.NotificationManagerCompat.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean hasJobIntentService() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// noinspection ConstantConditions
            return [CtBinaryOperatorImpl][CtFieldReadImpl]android.support.v4.app.JobIntentService.class != [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Integer checkAndroidSupportLibrary([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasWakefulBroadcastReceiver = [CtInvocationImpl]com.onesignal.OSUtils.hasWakefulBroadcastReceiver();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasNotificationManagerCompat = [CtInvocationImpl]com.onesignal.OSUtils.hasNotificationManagerCompat();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]hasWakefulBroadcastReceiver) && [CtUnaryOperatorImpl](![CtVariableReadImpl]hasNotificationManagerCompat)) [CtBlockImpl]{
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.FATAL, [CtLiteralImpl]"Could not find the Android Support Library. Please make sure it has been correctly added to your project.");
            [CtReturnImpl]return [CtFieldReadImpl]UserState.PUSH_STATUS_MISSING_ANDROID_SUPPORT_LIBRARY;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]hasWakefulBroadcastReceiver) || [CtUnaryOperatorImpl](![CtVariableReadImpl]hasNotificationManagerCompat)) [CtBlockImpl]{
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.FATAL, [CtLiteralImpl]"The included Android Support Library is to old or incomplete. Please update to the 26.0.0 revision or newer.");
            [CtReturnImpl]return [CtFieldReadImpl]UserState.PUSH_STATUS_OUTDATED_ANDROID_SUPPORT_LIBRARY;
        }
        [CtIfImpl][CtCommentImpl]// If running on Android O and targeting O we need version 26.0.0 for
        [CtCommentImpl]// the new compat NotificationCompat.Builder constructor.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]Build.VERSION.SDK_INT >= [CtFieldReadImpl]Build.VERSION_CODES.O) && [CtBinaryOperatorImpl]([CtInvocationImpl]com.onesignal.OSUtils.getTargetSdkVersion([CtVariableReadImpl]context) >= [CtFieldReadImpl]Build.VERSION_CODES.O)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Class was added in 26.0.0-beta2
            if ([CtUnaryOperatorImpl]![CtInvocationImpl]com.onesignal.OSUtils.hasJobIntentService()) [CtBlockImpl]{
                [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.FATAL, [CtLiteralImpl]"The included Android Support Library is to old or incomplete. Please update to the 26.0.0 revision or newer.");
                [CtReturnImpl]return [CtFieldReadImpl]UserState.PUSH_STATUS_OUTDATED_ANDROID_SUPPORT_LIBRARY;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean packageInstalledAndEnabled([CtParameterImpl][CtAnnotationImpl]@android.support.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String packageName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.pm.PackageManager pm = [CtInvocationImpl][CtTypeAccessImpl]OneSignal.appContext.getPackageManager();
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.pm.PackageInfo info = [CtInvocationImpl][CtVariableReadImpl]pm.getPackageInfo([CtVariableReadImpl]packageName, [CtTypeAccessImpl]PackageManager.GET_META_DATA);
            [CtReturnImpl]return [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]info.applicationInfo.enabled;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.pm.PackageManager.NameNotFoundException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtCommentImpl]// TODO: Maybe able to switch to GoogleApiAvailability.isGooglePlayServicesAvailable to simplify
    [CtCommentImpl]// However before doing so we need to test with an old version of the "Google Play services"
    [CtCommentImpl]// on the device to make sure it would still be counted as "SUCCESS".
    [CtCommentImpl]// Or if we get back "SERVICE_VERSION_UPDATE_REQUIRED" then we may want to count that as successful too.
    static [CtTypeReferenceImpl]boolean isGMSInstalledAndEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.onesignal.OSUtils.packageInstalledAndEnabled([CtTypeAccessImpl]GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE);
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int HMS_AVAILABLE_SUCCESSFUL = [CtLiteralImpl]0;

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isHMSCoreInstalledAndEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.huawei.hms.api.HuaweiApiAvailability availability = [CtInvocationImpl][CtTypeAccessImpl]com.huawei.hms.api.HuaweiApiAvailability.getInstance();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]availability.isHuaweiMobileServicesAvailable([CtTypeAccessImpl]OneSignal.appContext) == [CtFieldReadImpl]com.onesignal.OSUtils.HMS_AVAILABLE_SUCCESSFUL;
    }

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String HMS_CORE_SERVICES_PACKAGE = [CtLiteralImpl]"com.huawei.hwid";[CtCommentImpl]// = HuaweiApiAvailability.SERVICES_PACKAGE


    [CtMethodImpl][CtCommentImpl]// HuaweiApiAvailability is the recommend way to detect if "HMS Core" is available but this fallback
    [CtCommentImpl]// works even if the app developer doesn't include any HMS libraries in their app.
    private static [CtTypeReferenceImpl]boolean isHMSCoreInstalledAndEnabledFallback() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]com.onesignal.OSUtils.packageInstalledAndEnabled([CtFieldReadImpl]com.onesignal.OSUtils.HMS_CORE_SERVICES_PACKAGE);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean supportsADM() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Class only available on the FireOS and only when the following is in the AndroidManifest.xml.
            [CtCommentImpl]// <amazon:enable-feature android:name="com.amazon.device.messaging" android:required="false"/>
            [CtTypeAccessImpl]java.lang.Class.forName([CtLiteralImpl]"com.amazon.device.messaging.ADM");
            [CtReturnImpl]return [CtLiteralImpl]true;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.ClassNotFoundException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean supportsHMS() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// 1. App should have the HMSAvailability for best detection and must have PushKit libraries
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]com.onesignal.OSUtils.hasHMSAvailabilityLibrary()) || [CtUnaryOperatorImpl](![CtInvocationImpl]com.onesignal.OSUtils.hasAllHMSLibrariesForPushKit()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtReturnImpl][CtCommentImpl]// 2. Device must have HMS Core installed and enabled
        return [CtInvocationImpl]com.onesignal.OSUtils.isHMSCoreInstalledAndEnabled();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean supportsGooglePush() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// 1. If app does not have the FCM or GCM library it won't support Google push
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]com.onesignal.OSUtils.hasFCMLibrary()) && [CtUnaryOperatorImpl](![CtInvocationImpl]com.onesignal.OSUtils.hasGCMLibrary()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtReturnImpl][CtCommentImpl]// 2. "Google Play services" must be installed and enabled
        return [CtInvocationImpl]com.onesignal.OSUtils.isGMSInstalledAndEnabled();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Device type is determined by the push channel(s) the device supports.
     * Since a player_id can only support one we attempt to select the one that is native to the device
     * 1. ADM - This can NOT be side loaded on the device, if it has it then it is native
     * 2. FCM - If this is available then most likely native.
     *   - Prefer over HMS as FCM has more features on older Huawei devices.
     * 3. HMS - Huawei devices only.
     *   - New 2020 Huawei devices don't have FCM support, HMS only
     *   - Technically works for non-Huawei devices if you side load the Huawei AppGallery.
     *     i. "Notification Message" pushes are very bare bones. (title + body)
     *     ii. "Data Message" works as expected.
     */
    [CtTypeReferenceImpl]int getDeviceType() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]supportsADM())[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]UserState.DEVICE_TYPE_FIREOS;

        [CtIfImpl]if ([CtInvocationImpl]supportsGooglePush())[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]UserState.DEVICE_TYPE_ANDROID;

        [CtIfImpl][CtCommentImpl]// Some Huawei devices have both FCM & HMS support, but prefer FCM (Google push) over HMS
        if ([CtInvocationImpl]supportsHMS())[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]UserState.DEVICE_TYPE_HUAWEI;

        [CtIfImpl][CtCommentImpl]// Start - Fallback logic
        [CtCommentImpl]// Libraries in the app (Google:FCM, HMS:PushKit) + Device may not have a valid combo
        [CtCommentImpl]// Example: App with only the FCM library in it and a Huawei device with only HMS Core
        if ([CtInvocationImpl]com.onesignal.OSUtils.isGMSInstalledAndEnabled())[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]UserState.DEVICE_TYPE_ANDROID;

        [CtIfImpl]if ([CtInvocationImpl]com.onesignal.OSUtils.isHMSCoreInstalledAndEnabledFallback())[CtBlockImpl]
            [CtReturnImpl]return [CtFieldReadImpl]UserState.DEVICE_TYPE_HUAWEI;

        [CtReturnImpl][CtCommentImpl]// Last fallback
        [CtCommentImpl]// Fallback to device_type 1 (Android) if there are no supported push channels on the device
        return [CtFieldReadImpl]UserState.DEVICE_TYPE_ANDROID;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isAndroidDeviceType() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.onesignal.OSUtils().getDeviceType() == [CtFieldReadImpl]UserState.DEVICE_TYPE_ANDROID;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isFireOSDeviceType() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.onesignal.OSUtils().getDeviceType() == [CtFieldReadImpl]UserState.DEVICE_TYPE_FIREOS;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isHuaweiDeviceType() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.onesignal.OSUtils().getDeviceType() == [CtFieldReadImpl]UserState.DEVICE_TYPE_HUAWEI;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.Integer getNetType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.net.ConnectivityManager cm = [CtInvocationImpl](([CtTypeReferenceImpl]android.net.ConnectivityManager) ([CtTypeAccessImpl]OneSignal.appContext.getSystemService([CtTypeAccessImpl]Context.CONNECTIVITY_SERVICE)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.net.NetworkInfo netInfo = [CtInvocationImpl][CtVariableReadImpl]cm.getActiveNetworkInfo();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]netInfo != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int networkType = [CtInvocationImpl][CtVariableReadImpl]netInfo.getType();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]networkType == [CtFieldReadImpl]android.net.ConnectivityManager.TYPE_WIFI) || [CtBinaryOperatorImpl]([CtVariableReadImpl]networkType == [CtFieldReadImpl]android.net.ConnectivityManager.TYPE_ETHERNET))[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]0;

            [CtReturnImpl]return [CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String getCarrierName() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.telephony.TelephonyManager manager = [CtInvocationImpl](([CtTypeReferenceImpl]android.telephony.TelephonyManager) ([CtTypeAccessImpl]OneSignal.appContext.getSystemService([CtTypeAccessImpl]Context.TELEPHONY_SERVICE)));
            [CtLocalVariableImpl][CtCommentImpl]// May throw even though it's not in noted in the Android docs.
            [CtCommentImpl]// Issue #427
            [CtTypeReferenceImpl]java.lang.String carrierName = [CtInvocationImpl][CtVariableReadImpl]manager.getNetworkOperatorName();
            [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]carrierName) ? [CtLiteralImpl]null : [CtVariableReadImpl]carrierName;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]t.printStackTrace();
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String getManifestMeta([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String metaName) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.pm.ApplicationInfo ai = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getPackageManager().getApplicationInfo([CtInvocationImpl][CtVariableReadImpl]context.getPackageName(), [CtTypeAccessImpl]PackageManager.GET_META_DATA);
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle bundle = [CtFieldReadImpl][CtVariableReadImpl]ai.metaData;
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]bundle.getString([CtVariableReadImpl]metaName);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl]com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.ERROR, [CtLiteralImpl]"", [CtVariableReadImpl]t);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String getResourceString([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultStr) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.res.Resources resources = [CtInvocationImpl][CtVariableReadImpl]context.getResources();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int bodyResId = [CtInvocationImpl][CtVariableReadImpl]resources.getIdentifier([CtVariableReadImpl]key, [CtLiteralImpl]"string", [CtInvocationImpl][CtVariableReadImpl]context.getPackageName());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bodyResId != [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]resources.getString([CtVariableReadImpl]bodyResId);

        [CtReturnImpl]return [CtVariableReadImpl]defaultStr;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String getCorrectedLanguage() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lang = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault().getLanguage();
        [CtIfImpl][CtCommentImpl]// https://github.com/OneSignal/OneSignal-Android-SDK/issues/64
        if ([CtInvocationImpl][CtVariableReadImpl]lang.equals([CtLiteralImpl]"iw"))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"he";

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lang.equals([CtLiteralImpl]"in"))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"id";

        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]lang.equals([CtLiteralImpl]"ji"))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"yi";

        [CtIfImpl][CtCommentImpl]// https://github.com/OneSignal/OneSignal-Android-SDK/issues/98
        if ([CtInvocationImpl][CtVariableReadImpl]lang.equals([CtLiteralImpl]"zh"))[CtBlockImpl]
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]lang + [CtLiteralImpl]"-") + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Locale.getDefault().getCountry();

        [CtReturnImpl]return [CtVariableReadImpl]lang;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isValidEmail([CtParameterImpl][CtTypeReferenceImpl]java.lang.String email) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]email == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String emRegex = [CtLiteralImpl]"^[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Pattern pattern = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtVariableReadImpl]emRegex);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pattern.matcher([CtVariableReadImpl]email).matches();
    }

    [CtMethodImpl][CtCommentImpl]// Get the app's permission which will be false if the user disabled notifications for the app
    [CtCommentImpl]// from Settings > Apps or by long pressing the notifications and selecting block.
    [CtCommentImpl]// - Detection works on Android 4.4+, requires Android Support v4 Library 24.0.0+
    static [CtTypeReferenceImpl]boolean areNotificationsEnabled([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.support.v4.app.NotificationManagerCompat.from([CtTypeAccessImpl]OneSignal.appContext).areNotificationsEnabled();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]void runOnMainUIThread([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.os.Looper.getMainLooper().getThread() == [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread())[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]runnable.run();
        else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Handler handler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler([CtInvocationImpl][CtTypeAccessImpl]android.os.Looper.getMainLooper());
            [CtInvocationImpl][CtVariableReadImpl]handler.post([CtVariableReadImpl]runnable);
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]void runOnMainThreadDelayed([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable, [CtParameterImpl][CtTypeReferenceImpl]int delay) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Handler handler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler([CtInvocationImpl][CtTypeAccessImpl]android.os.Looper.getMainLooper());
        [CtInvocationImpl][CtVariableReadImpl]handler.postDelayed([CtVariableReadImpl]runnable, [CtVariableReadImpl]delay);
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]int getTargetSdkVersion([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl][CtVariableReadImpl]context.getPackageName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.pm.PackageManager packageManager = [CtInvocationImpl][CtVariableReadImpl]context.getPackageManager();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.pm.ApplicationInfo applicationInfo = [CtInvocationImpl][CtVariableReadImpl]packageManager.getApplicationInfo([CtVariableReadImpl]packageName, [CtLiteralImpl]0);
            [CtReturnImpl]return [CtFieldReadImpl][CtVariableReadImpl]applicationInfo.targetSdkVersion;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.pm.PackageManager.NameNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
        [CtReturnImpl]return [CtFieldReadImpl]Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean isValidResourceName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.matches([CtLiteralImpl]"^[0-9]"));
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]android.net.Uri getSoundUri([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String sound) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.res.Resources resources = [CtInvocationImpl][CtVariableReadImpl]context.getResources();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl][CtVariableReadImpl]context.getPackageName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int soundId;
        [CtIfImpl]if ([CtInvocationImpl]com.onesignal.OSUtils.isValidResourceName([CtVariableReadImpl]sound)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]soundId = [CtInvocationImpl][CtVariableReadImpl]resources.getIdentifier([CtVariableReadImpl]sound, [CtLiteralImpl]"raw", [CtVariableReadImpl]packageName);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]soundId != [CtLiteralImpl]0)[CtBlockImpl]
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]android.content.ContentResolver.SCHEME_ANDROID_RESOURCE + [CtLiteralImpl]"://") + [CtVariableReadImpl]packageName) + [CtLiteralImpl]"/") + [CtVariableReadImpl]soundId);

        }
        [CtAssignmentImpl][CtVariableWriteImpl]soundId = [CtInvocationImpl][CtVariableReadImpl]resources.getIdentifier([CtLiteralImpl]"onesignal_default_sound", [CtLiteralImpl]"raw", [CtVariableReadImpl]packageName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]soundId != [CtLiteralImpl]0)[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]android.content.ContentResolver.SCHEME_ANDROID_RESOURCE + [CtLiteralImpl]"://") + [CtVariableReadImpl]packageName) + [CtLiteralImpl]"/") + [CtVariableReadImpl]soundId);

        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtArrayTypeReferenceImpl]long[] parseVibrationPattern([CtParameterImpl][CtTypeReferenceImpl]org.json.JSONObject gcmBundle) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object patternObj = [CtInvocationImpl][CtVariableReadImpl]gcmBundle.opt([CtLiteralImpl]"vib_pt");
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.json.JSONArray jsonVibArray;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]patternObj instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]jsonVibArray = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.json.JSONArray([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (patternObj)));
            else[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]jsonVibArray = [CtVariableReadImpl](([CtTypeReferenceImpl]org.json.JSONArray) (patternObj));

            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]long[] longArray = [CtNewArrayImpl]new [CtTypeReferenceImpl]long[[CtInvocationImpl][CtVariableReadImpl]jsonVibArray.length()];
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]jsonVibArray.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++)[CtBlockImpl]
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]longArray[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]jsonVibArray.optLong([CtVariableReadImpl]i);

            [CtReturnImpl]return [CtVariableReadImpl]longArray;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String hexDigest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String str, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String digestInstance) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.MessageDigest digest = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtVariableReadImpl]digestInstance);
        [CtInvocationImpl][CtVariableReadImpl]digest.update([CtInvocationImpl][CtVariableReadImpl]str.getBytes([CtLiteralImpl]"UTF-8"));
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte messageDigest[] = [CtInvocationImpl][CtVariableReadImpl]digest.digest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder hexString = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]byte aMessageDigest : [CtVariableReadImpl]messageDigest) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String h = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toHexString([CtBinaryOperatorImpl][CtLiteralImpl]0xff & [CtVariableReadImpl]aMessageDigest);
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]h.length() < [CtLiteralImpl]2)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]h = [CtBinaryOperatorImpl][CtLiteralImpl]"0" + [CtVariableReadImpl]h;

            [CtInvocationImpl][CtVariableReadImpl]hexString.append([CtVariableReadImpl]h);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hexString.toString();
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]void sleep([CtParameterImpl][CtTypeReferenceImpl]int ms) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtVariableReadImpl]ms);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]void openURLInBrowser([CtParameterImpl][CtAnnotationImpl]@android.support.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
        [CtInvocationImpl]com.onesignal.OSUtils.openURLInBrowser([CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtInvocationImpl][CtVariableReadImpl]url.trim()));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void openURLInBrowser([CtParameterImpl][CtAnnotationImpl]@android.support.annotation.NonNull
    [CtTypeReferenceImpl]android.net.Uri uri) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.onesignal.OSUtils.SchemaType type = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]uri.getScheme() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.SchemaType.fromString([CtInvocationImpl][CtVariableReadImpl]uri.getScheme()) : [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]type = [CtFieldReadImpl][CtTypeAccessImpl]com.onesignal.OSUtils.SchemaType.[CtFieldReferenceImpl]HTTP;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]uri.toString().contains([CtLiteralImpl]"://")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]uri = [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtBinaryOperatorImpl][CtLiteralImpl]"http://" + [CtInvocationImpl][CtVariableReadImpl]uri.toString());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent;
        [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
            [CtCaseImpl]case DATA :
                [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtInvocationImpl][CtTypeAccessImpl]android.content.Intent.makeMainSelectorActivity([CtTypeAccessImpl]Intent.ACTION_MAIN, [CtTypeAccessImpl]Intent.CATEGORY_APP_BROWSER);
                [CtInvocationImpl][CtVariableReadImpl]intent.setData([CtVariableReadImpl]uri);
                [CtBreakImpl]break;
            [CtCaseImpl]case HTTPS :
            [CtCaseImpl]case HTTP :
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]android.content.Intent.ACTION_VIEW, [CtVariableReadImpl]uri);
                [CtBreakImpl]break;
        }
        [CtInvocationImpl][CtVariableReadImpl]intent.addFlags([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_NO_HISTORY | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET) | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK) | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        [CtInvocationImpl][CtTypeAccessImpl]OneSignal.appContext.startActivity([CtVariableReadImpl]intent);
    }

    [CtMethodImpl][CtCommentImpl]// Creates a new Set<T> that supports reads and writes from more than one thread at a time
    static <[CtTypeParameterImpl]T> [CtTypeReferenceImpl]java.util.Set<[CtTypeParameterReferenceImpl]T> newConcurrentSet() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.newSetFromMap([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<[CtTypeParameterReferenceImpl]T, [CtTypeReferenceImpl]java.lang.Boolean>());
    }

    [CtMethodImpl][CtCommentImpl]// Creates a new Set<String> from a Set String by converting and iterating a JSONArray
    static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> newStringSetFromJSONArray([CtParameterImpl][CtTypeReferenceImpl]org.json.JSONArray jsonArray) throws [CtTypeReferenceImpl]org.json.JSONException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> stringSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]jsonArray.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]stringSet.add([CtInvocationImpl][CtVariableReadImpl]jsonArray.getString([CtVariableReadImpl]i));
        }
        [CtReturnImpl]return [CtVariableReadImpl]stringSet;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean hasConfigChangeFlag([CtParameterImpl][CtTypeReferenceImpl]android.app.Activity activity, [CtParameterImpl][CtTypeReferenceImpl]int configChangeFlag) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasFlag = [CtLiteralImpl]false;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int configChanges = [CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]activity.getPackageManager().getActivityInfo([CtInvocationImpl][CtVariableReadImpl]activity.getComponentName(), [CtLiteralImpl]0).configChanges;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int flagInt = [CtBinaryOperatorImpl][CtVariableReadImpl]configChanges & [CtVariableReadImpl]configChangeFlag;
            [CtAssignmentImpl][CtVariableWriteImpl]hasFlag = [CtBinaryOperatorImpl][CtVariableReadImpl]flagInt != [CtLiteralImpl]0;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.pm.PackageManager.NameNotFoundException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
        [CtReturnImpl]return [CtVariableReadImpl]hasFlag;
    }

    [CtMethodImpl][CtAnnotationImpl]@android.support.annotation.NonNull
    static [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> extractStringsFromCollection([CtParameterImpl][CtAnnotationImpl]@android.support.annotation.Nullable
    [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.Object> collection) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]collection == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]result;

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object value : [CtVariableReadImpl]collection) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)));

        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean shouldLogMissingAppIdError([CtParameterImpl][CtAnnotationImpl]@android.support.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String appId) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]appId != [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtInvocationImpl][CtCommentImpl]// Wrapper SDKs can't normally call on Application.onCreate so just count this as informational.
        com.onesignal.OneSignal.Log([CtTypeAccessImpl]OneSignal.LOG_LEVEL.INFO, [CtBinaryOperatorImpl][CtLiteralImpl]"OneSignal was not initialized, " + [CtLiteralImpl]"ensure to always initialize OneSignal from the onCreate of your Application class.");
        [CtReturnImpl]return [CtLiteralImpl]true;
    }
}
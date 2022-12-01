[CompilationUnitImpl][CtPackageDeclarationImpl]package com.getcapacitor;
[CtUnresolvedImport]import android.content.Context;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import com.getcapacitor.util.PermissionHelper;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import android.content.pm.PackageManager;
[CtUnresolvedImport]import androidx.core.app.ActivityCompat;
[CtUnresolvedImport]import com.getcapacitor.annotation.CapacitorPlugin;
[CtImportImpl]import org.json.JSONException;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import androidx.appcompat.app.AppCompatActivity;
[CtUnresolvedImport]import android.content.res.Configuration;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import android.app.Activity;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.getcapacitor.annotation.Permission;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl][CtJavaDocImpl]/**
 * Plugin is the base class for all plugins, containing a number of
 * convenient features for interacting with the {@link Bridge}, managing
 * plugin permissions, tracking lifecycle events, and more.
 *
 * You should inherit from this class when creating new plugins, along with
 * adding the {@link CapacitorPlugin} annotation to add additional required
 * metadata about the Plugin
 */
public class Plugin {
    [CtFieldImpl][CtCommentImpl]// The key we will use inside of a persisted Bundle for the JSON blob
    [CtCommentImpl]// for a plugin call options.
    private static final [CtTypeReferenceImpl]java.lang.String BUNDLE_PERSISTED_OPTIONS_JSON_KEY = [CtLiteralImpl]"_json";

    [CtFieldImpl][CtCommentImpl]// Reference to the Bridge
    protected [CtTypeReferenceImpl]com.getcapacitor.Bridge bridge;

    [CtFieldImpl][CtCommentImpl]// Reference to the PluginHandle wrapper for this Plugin
    protected [CtTypeReferenceImpl]com.getcapacitor.PluginHandle handle;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A way for plugins to quickly save a call that they will need to reference
     * between activity/permissions starts/requests
     *
     * @deprecated store calls on the bridge using the methods
    {@link com.getcapacitor.Bridge#saveCall(PluginCall)},
    {@link com.getcapacitor.Bridge#getSavedCall(String)} and
    {@link com.getcapacitor.Bridge#releaseCall(PluginCall)}
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    protected [CtTypeReferenceImpl]com.getcapacitor.PluginCall savedLastCall;

    [CtFieldImpl][CtCommentImpl]// Stored event listeners
    private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.PluginCall>> eventListeners;

    [CtFieldImpl][CtCommentImpl]// Stored results of an event if an event was fired and
    [CtCommentImpl]// no listeners were attached yet. Only stores the last value.
    private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.getcapacitor.JSObject> retainedEventArguments;

    [CtConstructorImpl]public Plugin() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]eventListeners = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtAssignmentImpl][CtFieldWriteImpl]retainedEventArguments = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called when the plugin has been connected to the bridge
     * and is ready to start initializing.
     */
    public [CtTypeReferenceImpl]void load() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the main {@link Context} for the current Activity (your app)
     *
     * @return the Context for the current activity
     */
    public [CtTypeReferenceImpl]android.content.Context getContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.bridge.getContext();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the main {@link Activity} for the app
     *
     * @return the Activity for the current app
     */
    public [CtTypeReferenceImpl]androidx.appcompat.app.AppCompatActivity getActivity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.bridge.getActivity();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the Bridge instance for this plugin
     *
     * @param bridge
     */
    public [CtTypeReferenceImpl]void setBridge([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.Bridge bridge) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bridge = [CtVariableReadImpl]bridge;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the Bridge instance for this plugin
     */
    public [CtTypeReferenceImpl]com.getcapacitor.Bridge getBridge() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.bridge;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the wrapper {@link PluginHandle} instance for this plugin that
     * contains additional metadata about the Plugin instance (such
     * as indexed methods for reflection, and {@link CapacitorPlugin} annotation data).
     *
     * @param pluginHandle
     */
    public [CtTypeReferenceImpl]void setPluginHandle([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginHandle pluginHandle) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.handle = [CtVariableReadImpl]pluginHandle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the wrapper {@link PluginHandle} for this plugin.
     *
     * This wrapper contains additional metadata about the plugin instance,
     * such as indexed methods for reflection, and {@link CapacitorPlugin} annotation data).
     *
     * @return  */
    public [CtTypeReferenceImpl]com.getcapacitor.PluginHandle getPluginHandle() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.handle;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the root App ID
     *
     * @return  */
    public [CtTypeReferenceImpl]java.lang.String getAppId() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getContext().getPackageName();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called to save a {@link PluginCall} in order to reference it
     * later, such as in an activity or permissions result handler
     *
     * @deprecated use {@link Bridge#saveCall(PluginCall)}
     * @param lastCall
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void saveCall([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall lastCall) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.savedLastCall = [CtVariableReadImpl]lastCall;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the last saved call to null to free memory
     *
     * @deprecated use {@link PluginCall#release(Bridge)}
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void freeSavedCall() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.savedLastCall.isReleased()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.savedLastCall.release([CtFieldReadImpl]bridge);
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.savedLastCall = [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the last saved call, if any
     *
     * @deprecated use {@link Bridge#getSavedCall(String)}
     * @return  */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]com.getcapacitor.PluginCall getSavedCall() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.savedLastCall;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the config options for this plugin.
     *
     * @return a config object representing the plugin config options, or an empty config
    if none exists
     */
    public [CtTypeReferenceImpl]com.getcapacitor.PluginConfig getConfig() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]bridge.getConfig().getPluginConfiguration([CtInvocationImpl][CtFieldReadImpl]handle.getId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the value for a key on the config for this plugin.
     *
     * @deprecated use {@link #getConfig()} and access config values using the methods available
    depending on the type.
     * @param key
     * 		the key for the config value
     * @return some object containing the value from the config
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]java.lang.Object getConfigValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.PluginConfig pluginConfig = [CtInvocationImpl]getConfig();
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pluginConfig.getConfigJSON().get([CtVariableReadImpl]key);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether any of the given permissions has been defined in the AndroidManifest.xml
     *
     * @param permissions
     * @return  */
    public [CtTypeReferenceImpl]boolean hasDefinedPermissions([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] permissions) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String permission : [CtVariableReadImpl]permissions) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.util.PermissionHelper.hasDefinedPermission([CtInvocationImpl]getContext(), [CtVariableReadImpl]permission)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether any of the given permissions has been defined in the AndroidManifest.xml
     *
     * @param permissions
     * @return  */
    public [CtTypeReferenceImpl]boolean hasDefinedPermissions([CtParameterImpl][CtArrayTypeReferenceImpl]com.getcapacitor.annotation.Permission[] permissions) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.Permission perm : [CtVariableReadImpl]permissions) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String permString : [CtInvocationImpl][CtVariableReadImpl]perm.strings()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.util.PermissionHelper.hasDefinedPermission([CtInvocationImpl]getContext(), [CtVariableReadImpl]permString)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether any of annotation permissions has been defined in the AndroidManifest.xml
     *
     * @return  */
    public [CtTypeReferenceImpl]boolean hasDefinedRequiredPermissions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.CapacitorPlugin annotation = [CtInvocationImpl][CtFieldReadImpl]handle.getPluginAnnotation();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Check for legacy plugin annotation, @NativePlugin
            [CtTypeReferenceImpl]com.getcapacitor.NativePlugin legacyAnnotation = [CtInvocationImpl][CtFieldReadImpl]handle.getLegacyPluginAnnotation();
            [CtReturnImpl]return [CtInvocationImpl]hasDefinedPermissions([CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissions());
        }
        [CtReturnImpl]return [CtInvocationImpl]hasDefinedPermissions([CtInvocationImpl][CtVariableReadImpl]annotation.permissions());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether the given permission has been granted by the user
     *
     * @param permission
     * @return  */
    public [CtTypeReferenceImpl]boolean hasPermission([CtParameterImpl][CtTypeReferenceImpl]java.lang.String permission) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.checkSelfPermission([CtInvocationImpl][CtThisAccessImpl]this.getContext(), [CtVariableReadImpl]permission) == [CtFieldReadImpl]android.content.pm.PackageManager.PERMISSION_GRANTED;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If the {@link CapacitorPlugin} annotation specified a set of permissions,
     * this method checks if each is granted. Note: if you are okay
     * with a limited subset of the permissions being granted, check
     * each one individually instead with hasPermission
     *
     * @return  */
    public [CtTypeReferenceImpl]boolean hasRequiredPermissions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.CapacitorPlugin annotation = [CtInvocationImpl][CtFieldReadImpl]handle.getPluginAnnotation();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Check for legacy plugin annotation, @NativePlugin
            [CtTypeReferenceImpl]com.getcapacitor.NativePlugin legacyAnnotation = [CtInvocationImpl][CtFieldReadImpl]handle.getLegacyPluginAnnotation();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String perm : [CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissions()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasPermission([CtVariableReadImpl]perm)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.Permission perm : [CtInvocationImpl][CtVariableReadImpl]annotation.permissions()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String permString : [CtInvocationImpl][CtVariableReadImpl]perm.strings()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasPermission([CtVariableReadImpl]permString)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper for requesting a specific permission
     *
     * @since 3.0.0
     * @param call
     * 		the plugin call
     * @param permission
     * 		the permission to request
     * @param requestCode
     * 		the requestCode to use to associate the result with the plugin
     */
    public [CtTypeReferenceImpl]void requestPermission([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String permission, [CtParameterImpl][CtTypeReferenceImpl]int requestCode) [CtBlockImpl]{
        [CtInvocationImpl]requestPermissions([CtVariableReadImpl]call, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]permission }, [CtVariableReadImpl]requestCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper for requesting specific permissions
     *
     * @since 3.0.0
     * @param call
     * 		the plugin call
     * @param permissions
     * 		the set of permissions to request
     * @param requestCode
     * 		the requestCode to use to associate the result with the plugin
     */
    public [CtTypeReferenceImpl]void requestPermissions([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] permissions, [CtParameterImpl][CtTypeReferenceImpl]int requestCode) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]bridge.savePermissionCall([CtVariableReadImpl]call);
        [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.requestPermissions([CtInvocationImpl]getActivity(), [CtVariableReadImpl]permissions, [CtVariableReadImpl]requestCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Request all of the specified permissions in the CapacitorPlugin annotation (if any)
     *
     * @since 3.0.0
     * @param call
     * 		the plugin call
     */
    public [CtTypeReferenceImpl]void requestAllPermissions([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.CapacitorPlugin annotation = [CtInvocationImpl][CtFieldReadImpl]handle.getPluginAnnotation();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> perms = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.Permission perm : [CtInvocationImpl][CtVariableReadImpl]annotation.permissions()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]perms.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]perm.strings()));
            }
            [CtInvocationImpl][CtFieldReadImpl]bridge.savePermissionCall([CtVariableReadImpl]call);
            [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.requestPermissions([CtInvocationImpl]getActivity(), [CtInvocationImpl][CtVariableReadImpl]perms.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0]), [CtInvocationImpl][CtVariableReadImpl]annotation.permissionRequestCode());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper for requesting specific permissions
     *
     * @deprecated use {@link #requestPermissions(PluginCall)} in conjunction with @CapacitorPlugin
     * @param permissions
     * 		the set of permissions to request
     * @param requestCode
     * 		the requestCode to use to associate the result with the plugin
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void pluginRequestPermissions([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] permissions, [CtParameterImpl][CtTypeReferenceImpl]int requestCode) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.requestPermissions([CtInvocationImpl]getActivity(), [CtVariableReadImpl]permissions, [CtVariableReadImpl]requestCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Request all of the specified permissions in the CapacitorPlugin annotation (if any)
     *
     * @deprecated use {@link #requestAllPermissions(PluginCall)} in conjunction with @CapacitorPlugin
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void pluginRequestAllPermissions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.NativePlugin legacyAnnotation = [CtInvocationImpl][CtFieldReadImpl]handle.getLegacyPluginAnnotation();
        [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.requestPermissions([CtInvocationImpl]getActivity(), [CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissions(), [CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissionRequestCode());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper for requesting a specific permission
     *
     * @deprecated use {@link #requestPermission(PluginCall, String, int)} in conjunction with @CapacitorPlugin
     * @param permission
     * 		the permission to request
     * @param requestCode
     * 		the requestCode to use to associate the result with the plugin
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void pluginRequestPermission([CtParameterImpl][CtTypeReferenceImpl]java.lang.String permission, [CtParameterImpl][CtTypeReferenceImpl]int requestCode) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.ActivityCompat.requestPermissions([CtInvocationImpl]getActivity(), [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]permission }, [CtVariableReadImpl]requestCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Helper to check all permissions defined on a plugin and see the state of each.
     *
     * @since 3.0.0
     * @return an map containing the permission names and the permission state
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.getcapacitor.PermissionState> getPermissionStates() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]bridge.getPermissionStates([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a listener for the given event
     *
     * @param eventName
     * @param call
     */
    private [CtTypeReferenceImpl]void addEventListener([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.PluginCall> listeners = [CtInvocationImpl][CtFieldReadImpl]eventListeners.get([CtVariableReadImpl]eventName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]listeners == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]listeners.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]listeners = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtInvocationImpl][CtFieldReadImpl]eventListeners.put([CtVariableReadImpl]eventName, [CtVariableReadImpl]listeners);
            [CtInvocationImpl][CtCommentImpl]// Must add the call before sending retained arguments
            [CtVariableReadImpl]listeners.add([CtVariableReadImpl]call);
            [CtInvocationImpl]sendRetainedArgumentsForEvent([CtVariableReadImpl]eventName);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]listeners.add([CtVariableReadImpl]call);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove a listener from the given event
     *
     * @param eventName
     * @param call
     */
    private [CtTypeReferenceImpl]void removeEventListener([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.PluginCall> listeners = [CtInvocationImpl][CtFieldReadImpl]eventListeners.get([CtVariableReadImpl]eventName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]listeners == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]listeners.remove([CtVariableReadImpl]call);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify all listeners that an event occurred
     *
     * @param eventName
     * @param data
     */
    protected [CtTypeReferenceImpl]void notifyListeners([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject data, [CtParameterImpl][CtTypeReferenceImpl]boolean retainUntilConsumed) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.Logger.verbose([CtInvocationImpl]getLogTag(), [CtBinaryOperatorImpl][CtLiteralImpl]"Notifying listeners for event " + [CtVariableReadImpl]eventName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.PluginCall> listeners = [CtInvocationImpl][CtFieldReadImpl]eventListeners.get([CtVariableReadImpl]eventName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]listeners == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]listeners.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.Logger.debug([CtInvocationImpl]getLogTag(), [CtBinaryOperatorImpl][CtLiteralImpl]"No listeners found for event " + [CtVariableReadImpl]eventName);
            [CtIfImpl]if ([CtVariableReadImpl]retainUntilConsumed) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]retainedEventArguments.put([CtVariableReadImpl]eventName, [CtVariableReadImpl]data);
            }
            [CtReturnImpl]return;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]call.resolve([CtVariableReadImpl]data);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Notify all listeners that an event occurred
     * This calls {@link Plugin#notifyListeners(String, JSObject, boolean)}
     * with retainUntilConsumed set to false
     *
     * @param eventName
     * @param data
     */
    protected [CtTypeReferenceImpl]void notifyListeners([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject data) [CtBlockImpl]{
        [CtInvocationImpl]notifyListeners([CtVariableReadImpl]eventName, [CtVariableReadImpl]data, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check if there are any listeners for the given event
     */
    protected [CtTypeReferenceImpl]boolean hasListeners([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.getcapacitor.PluginCall> listeners = [CtInvocationImpl][CtFieldReadImpl]eventListeners.get([CtVariableReadImpl]eventName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]listeners == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]listeners.isEmpty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send retained arguments (if any) for this event. This
     * is called only when the first listener for an event is added
     *
     * @param eventName
     */
    private [CtTypeReferenceImpl]void sendRetainedArgumentsForEvent([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eventName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject retained = [CtInvocationImpl][CtFieldReadImpl]retainedEventArguments.get([CtVariableReadImpl]eventName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retained == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]notifyListeners([CtVariableReadImpl]eventName, [CtVariableReadImpl]retained);
        [CtInvocationImpl][CtFieldReadImpl]retainedEventArguments.remove([CtVariableReadImpl]eventName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Exported plugin call for adding a listener to this plugin
     *
     * @param call
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    [CtAnnotationImpl]@com.getcapacitor.PluginMethod(returnType = [CtFieldReadImpl]PluginMethod.RETURN_NONE)
    public [CtTypeReferenceImpl]void addListener([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eventName = [CtInvocationImpl][CtVariableReadImpl]call.getString([CtLiteralImpl]"eventName");
        [CtInvocationImpl][CtVariableReadImpl]call.save();
        [CtInvocationImpl]addEventListener([CtVariableReadImpl]eventName, [CtVariableReadImpl]call);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Exported plugin call to remove a listener from this plugin
     *
     * @param call
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    [CtAnnotationImpl]@com.getcapacitor.PluginMethod(returnType = [CtFieldReadImpl]PluginMethod.RETURN_NONE)
    public [CtTypeReferenceImpl]void removeListener([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eventName = [CtInvocationImpl][CtVariableReadImpl]call.getString([CtLiteralImpl]"eventName");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String callbackId = [CtInvocationImpl][CtVariableReadImpl]call.getString([CtLiteralImpl]"callbackId");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall savedCall = [CtInvocationImpl][CtFieldReadImpl]bridge.getSavedCall([CtVariableReadImpl]callbackId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]savedCall != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]removeEventListener([CtVariableReadImpl]eventName, [CtVariableReadImpl]savedCall);
            [CtInvocationImpl][CtFieldReadImpl]bridge.releaseCall([CtVariableReadImpl]savedCall);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Exported plugin call to remove all listeners from this plugin
     *
     * @param call
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    [CtAnnotationImpl]@com.getcapacitor.PluginMethod(returnType = [CtFieldReadImpl]PluginMethod.RETURN_NONE)
    public [CtTypeReferenceImpl]void removeAllListeners([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]eventListeners.clear();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Exported plugin call for checking the granted status for each permission
     * declared on the plugin. This plugin call responds with a mapping of permissions to
     * the associated granted status.
     *
     * @since 3.0.0
     */
    [CtAnnotationImpl]@com.getcapacitor.PluginMethod
    public [CtTypeReferenceImpl]void checkPermissions([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall pluginCall) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.getcapacitor.PermissionState> permissionsResult = [CtInvocationImpl]getPermissionStates();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]permissionsResult.size() == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// if no permissions are defined on the plugin, resolve undefined
            [CtVariableReadImpl]pluginCall.resolve();
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject permissionsResultJSON = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.getcapacitor.JSObject();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.getcapacitor.PermissionState> entry : [CtInvocationImpl][CtVariableReadImpl]permissionsResult.entrySet()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]permissionsResultJSON.put([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
            }
            [CtInvocationImpl][CtVariableReadImpl]pluginCall.resolve([CtVariableReadImpl]permissionsResultJSON);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Exported plugin call to request all permissions for this plugin.
     * To manually request permissions within a plugin use:
     *  {@link #requestPermission(PluginCall, String, int)}, or
     *  {@link #requestPermissions(PluginCall, String[], int)}, or
     *  {@link #requestAllPermissions(PluginCall)}
     *
     * @param call
     */
    [CtAnnotationImpl]@com.getcapacitor.PluginMethod
    public [CtTypeReferenceImpl]void requestPermissions([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] perms = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> autoGrantPerms = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int permissionRequestCode;
        [CtLocalVariableImpl][CtCommentImpl]// If call was made with a list of permissions to request, save them to be requested
        [CtCommentImpl]// instead of all permissions
        [CtTypeReferenceImpl]com.getcapacitor.JSArray providedPerms = [CtInvocationImpl][CtVariableReadImpl]call.getArray([CtLiteralImpl]"permissions");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> providedPermsList = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]providedPermsList = [CtInvocationImpl][CtVariableReadImpl]providedPerms.toList();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.CapacitorPlugin annotation = [CtInvocationImpl][CtFieldReadImpl]handle.getPluginAnnotation();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.NativePlugin legacyAnnotation = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.handle.getLegacyPluginAnnotation();
            [CtAssignmentImpl][CtVariableWriteImpl]perms = [CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissions();
            [CtAssignmentImpl][CtVariableWriteImpl]permissionRequestCode = [CtInvocationImpl][CtVariableReadImpl]legacyAnnotation.permissionRequestCode();
        } else [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// If call was made without any custom permissions, request all from plugin annotation
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]providedPermsList == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]providedPermsList.isEmpty()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> permsSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.Permission perm : [CtInvocationImpl][CtVariableReadImpl]annotation.permissions()) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// If a permission is defined with no permission constants, separate it for auto-granting.
                    [CtCommentImpl]// Otherwise, it is added to the list to be requested.
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]perm.strings().length == [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]perm.strings().length == [CtLiteralImpl]1) && [CtInvocationImpl][CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]perm.strings()[[CtLiteralImpl]0].isEmpty())) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]perm.alias().isEmpty()) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]autoGrantPerms.add([CtInvocationImpl][CtVariableReadImpl]perm.alias());
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]permsSet.addAll([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]perm.strings()));
                    }
                }
                [CtAssignmentImpl][CtVariableWriteImpl]perms = [CtInvocationImpl][CtVariableReadImpl]permsSet.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0]);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> permsSet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.annotation.Permission perm : [CtInvocationImpl][CtVariableReadImpl]annotation.permissions()) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String permString : [CtInvocationImpl][CtVariableReadImpl]perm.strings()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]providedPermsList.contains([CtInvocationImpl][CtVariableReadImpl]perm.alias()) || [CtInvocationImpl][CtVariableReadImpl]providedPermsList.contains([CtVariableReadImpl]permString)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]permsSet.add([CtVariableReadImpl]permString);
                        }
                    }
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]permsSet.isEmpty()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]call.reject([CtLiteralImpl]"No valid permission or permission alias was requested.");
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]perms = [CtInvocationImpl][CtVariableReadImpl]permsSet.toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]0]);
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]permissionRequestCode = [CtInvocationImpl][CtVariableReadImpl]annotation.permissionRequestCode();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]perms != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]perms.length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Save the call so we can return data back once the permission request has completed
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]annotation == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]saveCall([CtVariableReadImpl]call);
                [CtInvocationImpl]pluginRequestPermissions([CtVariableReadImpl]perms, [CtVariableReadImpl]permissionRequestCode);
            } else [CtBlockImpl]{
                [CtInvocationImpl]requestPermissions([CtVariableReadImpl]call, [CtVariableReadImpl]perms, [CtVariableReadImpl]permissionRequestCode);
            }
        } else [CtIfImpl][CtCommentImpl]// if the plugin only has auto-grant permissions, return those
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]autoGrantPerms.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject permissionsResults = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.getcapacitor.JSObject();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String perm : [CtVariableReadImpl]autoGrantPerms) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]permissionsResults.put([CtVariableReadImpl]perm, [CtInvocationImpl][CtTypeAccessImpl]PermissionState.GRANTED.toString());
            }
            [CtInvocationImpl][CtVariableReadImpl]call.resolve([CtVariableReadImpl]permissionsResults);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// no permissions are defined on the plugin, resolve undefined
            [CtVariableReadImpl]call.resolve();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle request permissions result. A plugin using the deprecated {@link NativePlugin}
     * should override this to handle the result, or this method will handle the result
     * for our convenient requestPermissions call.
     *
     * @deprecated in favor of using {@link #onRequestPermissionsResult} in conjunction with {@link CapacitorPlugin}
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    protected [CtTypeReferenceImpl]void handleRequestPermissionsResult([CtParameterImpl][CtTypeReferenceImpl]int requestCode, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] permissions, [CtParameterImpl][CtArrayTypeReferenceImpl]int[] grantResults) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasDefinedPermissions([CtVariableReadImpl]permissions)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]"Missing the following permissions in AndroidManifest.xml:\n");
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] missing = [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.util.PermissionHelper.getUndefinedPermissions([CtInvocationImpl]getContext(), [CtVariableReadImpl]permissions);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String perm : [CtVariableReadImpl]missing) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]builder.append([CtBinaryOperatorImpl][CtVariableReadImpl]perm + [CtLiteralImpl]"\n");
            }
            [CtInvocationImpl][CtFieldReadImpl]savedLastCall.reject([CtInvocationImpl][CtVariableReadImpl]builder.toString());
            [CtAssignmentImpl][CtFieldWriteImpl]savedLastCall = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle request permissions result. A plugin using the {@link CapacitorPlugin} annotation
     * can override this to handle the result.
     *
     * @param savedCall
     * @param permissionResults
     */
    protected [CtTypeReferenceImpl]void onRequestPermissionsResult([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall savedCall, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.getcapacitor.PermissionState> permissionResults) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called before the app is destroyed to give a plugin the chance to
     * save the last call options for a saved plugin. By default, this
     * method saves the full JSON blob of the options call. Since Bundle sizes
     * may be limited, plugins that expect to be called with large data
     * objects (such as a file), should override this method and selectively
     * store option values in a {@link Bundle} to avoid exceeding limits.
     *
     * @return a new {@link Bundle} with fields set from the options of the last saved {@link PluginCall}
     */
    protected [CtTypeReferenceImpl]android.os.Bundle saveInstanceState() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall savedCall = [CtInvocationImpl]getSavedCall();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]savedCall == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle ret = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Bundle();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.getcapacitor.JSObject callData = [CtInvocationImpl][CtVariableReadImpl]savedCall.getData();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]callData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ret.putString([CtFieldReadImpl]com.getcapacitor.Plugin.BUNDLE_PERSISTED_OPTIONS_JSON_KEY, [CtInvocationImpl][CtVariableReadImpl]callData.toString());
        }
        [CtReturnImpl]return [CtVariableReadImpl]ret;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called when the app is opened with a previously un-handled
     * activity response. If the plugin that started the activity
     * stored data in {@link Plugin#saveInstanceState()} then this
     * method will be called to allow the plugin to restore from that.
     *
     * @param state
     */
    protected [CtTypeReferenceImpl]void restoreState([CtParameterImpl][CtTypeReferenceImpl]android.os.Bundle state) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle activity result, should be overridden by each plugin
     *
     * @param lastPluginCall
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected [CtTypeReferenceImpl]void handleOnActivityResult([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall lastPluginCall, [CtParameterImpl][CtTypeReferenceImpl]int requestCode, [CtParameterImpl][CtTypeReferenceImpl]int resultCode, [CtParameterImpl][CtTypeReferenceImpl]android.content.Intent data) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle activity result, should be overridden by each plugin
     *
     * @deprecated use {@link #handleOnActivityResult(PluginCall, int, int, Intent)} in
    conjunction with @CapacitorPlugin
     * @param requestCode
     * @param resultCode
     * @param data
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    protected [CtTypeReferenceImpl]void handleOnActivityResult([CtParameterImpl][CtTypeReferenceImpl]int requestCode, [CtParameterImpl][CtTypeReferenceImpl]int resultCode, [CtParameterImpl][CtTypeReferenceImpl]android.content.Intent data) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onNewIntent
     *
     * @param intent
     */
    protected [CtTypeReferenceImpl]void handleOnNewIntent([CtParameterImpl][CtTypeReferenceImpl]android.content.Intent intent) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onConfigurationChanged
     *
     * @param newConfig
     */
    protected [CtTypeReferenceImpl]void handleOnConfigurationChanged([CtParameterImpl][CtTypeReferenceImpl]android.content.res.Configuration newConfig) [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onStart
     */
    protected [CtTypeReferenceImpl]void handleOnStart() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onRestart
     */
    protected [CtTypeReferenceImpl]void handleOnRestart() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onResume
     */
    protected [CtTypeReferenceImpl]void handleOnResume() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onPause
     */
    protected [CtTypeReferenceImpl]void handleOnPause() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onStop
     */
    protected [CtTypeReferenceImpl]void handleOnStop() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle onDestroy
     */
    protected [CtTypeReferenceImpl]void handleOnDestroy() [CtBlockImpl]{
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Give the plugins a chance to take control when a URL is about to be loaded in the WebView.
     * Returning true causes the WebView to abort loading the URL.
     * Returning false causes the WebView to continue loading the URL.
     * Returning null will defer to the default Capacitor policy
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    public [CtTypeReferenceImpl]java.lang.Boolean shouldOverrideLoad([CtParameterImpl][CtTypeReferenceImpl]android.net.Uri url) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Start a new Activity.
     *
     * Note: This method must be used by all plugins instead of calling
     * {@link Activity#startActivityForResult} as it associates the plugin with
     * any resulting data from the new Activity even if this app
     * is destroyed by the OS (to free up memory, for example).
     *
     * @param intent
     * @param resultCode
     */
    protected [CtTypeReferenceImpl]void startActivityForResult([CtParameterImpl][CtTypeReferenceImpl]com.getcapacitor.PluginCall call, [CtParameterImpl][CtTypeReferenceImpl]android.content.Intent intent, [CtParameterImpl][CtTypeReferenceImpl]int resultCode) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]bridge.startActivityForPluginWithResult([CtVariableReadImpl]call, [CtVariableReadImpl]intent, [CtVariableReadImpl]resultCode);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Execute the given runnable on the Bridge's task handler
     *
     * @param runnable
     */
    public [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]java.lang.Runnable runnable) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]bridge.execute([CtVariableReadImpl]runnable);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shortcut for getting the plugin log tag
     *
     * @param subTags
     */
    protected [CtTypeReferenceImpl]java.lang.String getLogTag([CtParameterImpl]java.lang.String... subTags) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.Logger.tags([CtVariableReadImpl]subTags);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a plugin log tag with the child's class name as subTag.
     */
    protected [CtTypeReferenceImpl]java.lang.String getLogTag() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.getcapacitor.Logger.tags([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getSimpleName());
    }
}
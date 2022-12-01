[CompilationUnitImpl][CtCommentImpl]/* Copyright 2020 Realm Inc.

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
[CtPackageDeclarationImpl]package io.realm;
[CtUnresolvedImport]import io.realm.internal.objectstore.OsJavaNetworkTransport;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import io.realm.internal.Util;
[CtUnresolvedImport]import org.bson.types.ObjectId;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.concurrent.atomic.AtomicReference;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtClassImpl][CtJavaDocImpl]/**
 * This class exposes functionality for a user to manage API keys under their control.
 */
public class ApiKeyAuthProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_CREATE = [CtLiteralImpl]1;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_FETCH_SINGLE = [CtLiteralImpl]2;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_FETCH_ALL = [CtLiteralImpl]3;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_DELETE = [CtLiteralImpl]4;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_DISABLE = [CtLiteralImpl]5;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int TYPE_ENABLE = [CtLiteralImpl]6;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.realm.RealmUser user;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create an instance of this class for a specific user.
     *
     * @param user
     * 		user that is controlling the API keys.
     */
    public ApiKeyAuthProvider([CtParameterImpl][CtTypeReferenceImpl]io.realm.RealmUser user) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.user = [CtVariableReadImpl]user;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.realm.RealmUser getUser() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]user;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.realm.RealmApp getApp() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]user.getApp();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a user API key that can be used to authenticate as the user.
     * <p>
     * The value of the key must be persisted at this time as this is the only time it is visible.
     * <p>
     * The key is enabled when created. It can be disabled by calling {@link #disableApiKey(ObjectId)}.
     *
     * @param name
     * 		the name of the key
     * @throws ObjectServer
     * 		if the server failed to create the API key.
     * @return the new API key for the user.
     */
    public [CtTypeReferenceImpl]io.realm.RealmUserApiKey createApiKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkEmpty([CtVariableReadImpl]name, [CtLiteralImpl]"name");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> success = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIResultCallback<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> callback = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIResultCallback<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>([CtVariableReadImpl]success, [CtVariableReadImpl]error)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]io.realm.RealmUserApiKey mapSuccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object result) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]createKeyFromNative([CtVariableReadImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) (result)));
            }
        };
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_CREATE, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtVariableReadImpl]name, [CtVariableReadImpl]callback);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtVariableReadImpl]success, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Asynchronously creates a user API key that can be used to authenticate as the user.
     * <p>
     * The value of the key must be persisted at this time as this is the only time it is visible.
     * <p>
     * The key is enabled when created. It can be disabled by calling {@link #disableApiKey(ObjectId)}.
     *
     * @param name
     * 		the name of the key
     * @param callback
     * 		callback when key creation has completed or failed. The callback will always
     * 		happen on the same thread as this method is called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask createApiKeyAsync([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous creation of api keys are only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]io.realm.RealmUserApiKey run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]createApiKey([CtVariableReadImpl]name);
            }
        }.start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetches a specific user API key associated with the user.
     *
     * @param id
     * 		the id of the key to fetch.
     * @throws ObjectServer
     * 		if the server failed to fetch the API key.
     */
    public [CtTypeReferenceImpl]io.realm.RealmUserApiKey fetchApiKey([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id) throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]id, [CtLiteralImpl]"id");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> success = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_FETCH_SINGLE, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtInvocationImpl][CtVariableReadImpl]id.toHexString(), [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIResultCallback<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>([CtVariableReadImpl]success, [CtVariableReadImpl]error)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]io.realm.RealmUserApiKey mapSuccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object result) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]createKeyFromNative([CtVariableReadImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) (result)));
            }
        });
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtVariableReadImpl]success, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetches a specific user API key associated with the user.
     *
     * @param id
     * 		the id of the key to fetch.
     * @param callback
     * 		callback used when the the key was fetched or the call failed. The callback
     * 		will always happen on the same thread as this method was called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask fetchApiKeyAsync([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous fetching an api key is only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]io.realm.RealmUserApiKey run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]fetchApiKey([CtVariableReadImpl]id);
            }
        }.start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetches all API keys associated with the user.
     *
     * @throws ObjectServer
     * 		if the server failed to fetch the API keys.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> fetchAllApiKeys() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>> success = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_FETCH_ALL, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtLiteralImpl]null, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIResultCallback<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>>([CtVariableReadImpl]success, [CtVariableReadImpl]error)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> mapSuccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object result) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] keyData = [CtVariableReadImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) (result));
                [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> list = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.realm.ArrayList<>();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]keyData.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]list.add([CtInvocationImpl]createKeyFromNative([CtArrayReadImpl](([CtArrayTypeReferenceImpl]java.lang.Object[]) ([CtVariableReadImpl]keyData[[CtVariableReadImpl]i]))));
                }
                [CtReturnImpl]return [CtVariableReadImpl]list;
            }
        });
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtVariableReadImpl]success, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetches all API keys associated with the user.
     *
     * @param callback
     * 		callback used when the the keys were fetched or the call failed. The callback
     * 		will always happen on the same thread as this method was called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask fetchAllApiKeys([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous fetching an api key is only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey>>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]io.realm.RealmUserApiKey> run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]fetchAllApiKeys();
            }
        }.start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to delete.
     * @throws ObjectServer
     * 		if the server failed to delete the API key.
     */
    public [CtTypeReferenceImpl]void deleteApiKey([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id) throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]id, [CtLiteralImpl]"id");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_DELETE, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtInvocationImpl][CtVariableReadImpl]id.toHexString(), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIVoidResultCallback([CtVariableReadImpl]error));
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtLiteralImpl]null, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to delete.
     * @param callback
     * 		callback used when the the key was deleted or the call failed. The callback
     * 		will always happen on the same thread as this method was called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask deleteApiKeyAsync([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]java.lang.Void> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous deleting an api key is only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]java.lang.Void>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtInvocationImpl]deleteApiKey([CtVariableReadImpl]id);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }.start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Disables a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to disable.
     * @throws ObjectServer
     * 		if the server failed to disable the API key.
     */
    public [CtTypeReferenceImpl]void disableApiKey([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id) throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]id, [CtLiteralImpl]"id");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_DISABLE, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtInvocationImpl][CtVariableReadImpl]id.toHexString(), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIVoidResultCallback([CtVariableReadImpl]error));
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtLiteralImpl]null, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Disables a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to disable.
     * @param callback
     * 		callback used when the the key was disabled or the call failed. The callback
     * 		will always happen on the same thread as this method was called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask disableApiKeyAsync([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]java.lang.Void> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous disabling an api key is only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]java.lang.Void>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtInvocationImpl]disableApiKey([CtVariableReadImpl]id);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }.start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Enables a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to enable.
     * @throws ObjectServer
     * 		if the server failed to enable the API key.
     */
    public [CtTypeReferenceImpl]void enableApiKey([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id) throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]id, [CtLiteralImpl]"id");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<[CtTypeReferenceImpl]io.realm.ObjectServerError> error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicReference<>([CtLiteralImpl]null);
        [CtInvocationImpl]io.realm.ApiKeyAuthProvider.nativeCallFunction([CtFieldReadImpl]io.realm.ApiKeyAuthProvider.TYPE_ENABLE, [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl]user.getApp().nativePtr, [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]user.osUser.getNativePtr(), [CtInvocationImpl][CtVariableReadImpl]id.toHexString(), [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.OsJNIVoidResultCallback([CtVariableReadImpl]error));
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.RealmApp.handleResult([CtLiteralImpl]null, [CtVariableReadImpl]error);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Enables a specific API key created by the user.
     *
     * @param id
     * 		the id of the key to enable.
     * @param callback
     * 		callback used when the the key was enabled or the call failed. The callback
     * 		will always happen on the same thread as this method was called on.
     * @throws IllegalStateException
     * 		if called from a non-looper thread.
     */
    public [CtTypeReferenceImpl]io.realm.RealmAsyncTask enableApiKeyAsync([CtParameterImpl][CtTypeReferenceImpl]org.bson.types.ObjectId id, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Callback<[CtTypeReferenceImpl]java.lang.Void> callback) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkLooperThread([CtLiteralImpl]"Asynchronous enabling an api key is only possible from looper threads.");
        [CtReturnImpl]return [CtInvocationImpl][CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.RealmApp.Request<[CtTypeReferenceImpl]java.lang.Void>([CtFieldReadImpl]SyncManager.NETWORK_POOL_EXECUTOR, [CtVariableReadImpl]callback)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]java.lang.Void run() throws [CtTypeReferenceImpl]io.realm.ObjectServerError [CtBlockImpl]{
                [CtInvocationImpl]enableApiKey([CtVariableReadImpl]id);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
        }.start();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]io.realm.RealmUserApiKey createKeyFromNative([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.Object[] keyData) [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.realm.RealmUserApiKey([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.types.ObjectId([CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]keyData[[CtLiteralImpl]0]))), [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]keyData[[CtLiteralImpl]1])), [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]keyData[[CtLiteralImpl]2])), [CtUnaryOperatorImpl]![CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) ([CtVariableReadImpl]keyData[[CtLiteralImpl]3])));[CtCommentImpl]// Server returns disabled state instead of enabled

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]io.realm.ApiKeyAuthProvider that = [CtVariableReadImpl](([CtTypeReferenceImpl]io.realm.ApiKeyAuthProvider) (o));
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]user.equals([CtFieldReadImpl][CtVariableReadImpl]that.user);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]user.hashCode();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"ApiKeyAuthProvider{" + [CtLiteralImpl]"user=") + [CtInvocationImpl][CtFieldReadImpl]user.getId()) + [CtLiteralImpl]'}';
    }

    [CtMethodImpl]private static native [CtTypeReferenceImpl]void nativeCallFunction([CtParameterImpl][CtTypeReferenceImpl]int functionType, [CtParameterImpl][CtTypeReferenceImpl]long nativeAppPtr, [CtParameterImpl][CtTypeReferenceImpl]long nativeUserPtr, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String arg, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.internal.objectstore.OsJavaNetworkTransport.NetworkTransportJNIResultCallback callback);
}
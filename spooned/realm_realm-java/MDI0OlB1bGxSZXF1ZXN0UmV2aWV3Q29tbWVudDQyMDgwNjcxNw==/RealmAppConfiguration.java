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
[CtUnresolvedImport]import org.bson.codecs.ValueCodecProvider;
[CtUnresolvedImport]import android.content.Context;
[CtImportImpl]import java.util.Locale;
[CtUnresolvedImport]import io.realm.log.LogLevel;
[CtUnresolvedImport]import io.realm.log.RealmLog;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.net.MalformedURLException;
[CtUnresolvedImport]import org.bson.codecs.configuration.CodecRegistries;
[CtUnresolvedImport]import org.bson.codecs.configuration.CodecRegistry;
[CtUnresolvedImport]import org.bson.codecs.BsonValueCodecProvider;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import org.bson.codecs.IterableCodecProvider;
[CtUnresolvedImport]import io.realm.internal.Util;
[CtImportImpl]import java.util.concurrent.TimeUnit;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtJavaDocImpl]/**
 * FIXME
 */
public class RealmAppConfiguration {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String appId;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String appName;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String appVersion;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.net.URL baseUrl;

    [CtFieldImpl]private final [CtTypeReferenceImpl]SyncSession.ErrorHandler defaultErrorHandler;

    [CtFieldImpl][CtAnnotationImpl]@javax.annotation.Nullable
    private final [CtArrayTypeReferenceImpl]byte[] encryptionKey;

    [CtFieldImpl]private final [CtTypeReferenceImpl]long logLevel;

    [CtFieldImpl]private final [CtTypeReferenceImpl]long requestTimeoutMs;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String authorizationHeaderName;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> customHeaders;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.io.File syncRootDir;[CtCommentImpl]// Root directory for storing Sync related files


    [CtFieldImpl]private final [CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry codecRegistry;

    [CtConstructorImpl]private RealmAppConfiguration([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String appName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String appVersion, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.SyncSession.ErrorHandler defaultErrorHandler, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtArrayTypeReferenceImpl]byte[] encryptionKey, [CtParameterImpl][CtTypeReferenceImpl]long logLevel, [CtParameterImpl][CtTypeReferenceImpl]long requestTimeoutMs, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String authorizationHeaderName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> customHeaders, [CtParameterImpl][CtTypeReferenceImpl]java.io.File syncRootdir, [CtParameterImpl][CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry codecRegistry) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.appId = [CtVariableReadImpl]appId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.appName = [CtVariableReadImpl]appName;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.appVersion = [CtVariableReadImpl]appVersion;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.baseUrl = [CtInvocationImpl]createUrl([CtVariableReadImpl]baseUrl);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.defaultErrorHandler = [CtVariableReadImpl]defaultErrorHandler;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encryptionKey = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]encryptionKey == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]encryptionKey, [CtFieldReadImpl][CtVariableReadImpl]encryptionKey.length);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.logLevel = [CtVariableReadImpl]logLevel;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestTimeoutMs = [CtVariableReadImpl]requestTimeoutMs;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.authorizationHeaderName = [CtConditionalImpl]([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.isEmptyString([CtVariableReadImpl]authorizationHeaderName)) ? [CtVariableReadImpl]authorizationHeaderName : [CtLiteralImpl]"Authorization";
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.customHeaders = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableMap([CtVariableReadImpl]customHeaders);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.syncRootDir = [CtVariableReadImpl]syncRootdir;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.codecRegistry = [CtVariableReadImpl]codecRegistry;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.net.URL createUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtVariableReadImpl]baseUrl);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.net.MalformedURLException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtVariableReadImpl]baseUrl);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.lang.String getAppId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]appId;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.lang.String getAppName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]appName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.lang.String getAppVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]appVersion;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.net.URL getBaseUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]baseUrl;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtArrayTypeReferenceImpl]byte[] getEncryptionKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]encryptionKey == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtFieldReadImpl]encryptionKey, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]encryptionKey.length);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]long getLogLevel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]logLevel;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]long getRequestTimeoutMs() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]requestTimeoutMs;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.lang.String getAuthorizationHeaderName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]authorizationHeaderName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getCustomRequestHeaders() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]customHeaders;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * FIXME
     *
     * @return  */
    public [CtTypeReferenceImpl]SyncSession.ErrorHandler getDefaultErrorHandler() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]defaultErrorHandler;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the root folder containing all files and Realms used when synchronizing data
     * between the device and MongoDB Realm.
     */
    public [CtTypeReferenceImpl]java.io.File getSyncRootDirectory() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]syncRootDir;
    }

    [CtMethodImpl][CtCommentImpl]// FIXME Doc
    public [CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry getCodecRegistry() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]codecRegistry;
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * FIXME
     */
    public static class Builder {
        [CtFieldImpl][CtCommentImpl]// Default BSON codec for passing BSON to/from JNI
        static [CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry DEFAULT_BSON_CODEC_REGISTRY = [CtInvocationImpl][CtTypeAccessImpl]org.bson.codecs.configuration.CodecRegistries.fromRegistries([CtInvocationImpl][CtCommentImpl]// For primitive support
        [CtCommentImpl]// For BSONValue support
        [CtCommentImpl]// For list support
        [CtTypeAccessImpl]org.bson.codecs.configuration.CodecRegistries.fromProviders([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.codecs.ValueCodecProvider(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.codecs.BsonValueCodecProvider(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.bson.codecs.IterableCodecProvider()));

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String appId;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String appName;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String appVersion;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String baseUrl = [CtLiteralImpl]"https://stitch.mongodb.com";[CtCommentImpl]// FIXME Find the correct base url for release


        [CtFieldImpl]private [CtTypeReferenceImpl]SyncSession.ErrorHandler defaultErrorHandler = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.SyncSession.ErrorHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]io.realm.SyncSession session, [CtParameterImpl][CtTypeReferenceImpl]io.realm.ObjectServerError error) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]error.getErrorCode() == [CtFieldReadImpl]ErrorCode.CLIENT_RESET) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]io.realm.log.RealmLog.error([CtBinaryOperatorImpl][CtLiteralImpl]"Client Reset required for: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getConfiguration().getServerUrl());
                    [CtReturnImpl]return;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMsg = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]US, [CtLiteralImpl]"Session Error[%s]: %s", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]session.getConfiguration().getServerUrl(), [CtInvocationImpl][CtVariableReadImpl]error.toString());
                [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.getErrorCode().getCategory()) {
                    [CtCaseImpl]case [CtFieldReadImpl]FATAL :
                        [CtInvocationImpl][CtTypeAccessImpl]io.realm.log.RealmLog.error([CtVariableReadImpl]errorMsg);
                        [CtBreakImpl]break;
                    [CtCaseImpl]case [CtFieldReadImpl]RECOVERABLE :
                        [CtInvocationImpl][CtTypeAccessImpl]io.realm.log.RealmLog.info([CtVariableReadImpl]errorMsg);
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"Unsupported error category: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.getErrorCode().getCategory());
                }
            }
        };

        [CtFieldImpl]private [CtArrayTypeReferenceImpl]byte[] encryptionKey;

        [CtFieldImpl]private [CtTypeReferenceImpl]long logLevel = [CtFieldReadImpl]io.realm.log.LogLevel.WARN;[CtCommentImpl]// FIXME: Consider what this should be set at


        [CtFieldImpl]private [CtTypeReferenceImpl]long requestTimeoutMs = [CtLiteralImpl]60000;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String autorizationHeaderName;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> customHeaders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

        [CtFieldImpl]private [CtTypeReferenceImpl]java.io.File syncRootDir;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry codecRegistry = [CtFieldReadImpl]io.realm.RealmAppConfiguration.Builder.DEFAULT_BSON_CODEC_REGISTRY;

        [CtConstructorImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param appId
         */
        public Builder([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appId) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkEmpty([CtVariableReadImpl]appId, [CtLiteralImpl]"appId");
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.appId = [CtVariableReadImpl]appId;
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Context context = [CtFieldReadImpl]BaseRealm.applicationContext;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]context == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Call `Realm.init(Context)` before calling this method.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File rootDir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtVariableReadImpl]context.getFilesDir(), [CtLiteralImpl]"mongodb-realm");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]rootDir.exists()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]rootDir.mkdir())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"Could not create Sync root dir: " + [CtInvocationImpl][CtVariableReadImpl]rootDir.getAbsolutePath());
            }
            [CtAssignmentImpl][CtFieldWriteImpl]syncRootDir = [CtVariableReadImpl]rootDir;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param level
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder logLevel([CtParameterImpl][CtTypeReferenceImpl]int level) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// FIXME: Boundary checks
            [CtFieldWriteImpl][CtThisAccessImpl]this.logLevel = [CtVariableReadImpl]level;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param key
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder encryptionKey([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] key) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encryptionKey = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.copyOf([CtVariableReadImpl]key, [CtFieldReadImpl][CtVariableReadImpl]key.length);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param baseUrl
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder baseUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String baseUrl) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// FIXME Check input
            [CtFieldWriteImpl][CtThisAccessImpl]this.baseUrl = [CtVariableReadImpl]baseUrl;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param appName
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder appName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appName) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// FIXME CHecks
            [CtFieldWriteImpl][CtThisAccessImpl]this.appName = [CtVariableReadImpl]appName;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param appVersion
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder appVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String appVersion) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// FIXME checks
            [CtFieldWriteImpl][CtThisAccessImpl]this.appVersion = [CtVariableReadImpl]appVersion;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * FIXME
         *
         * @param time
         * @param unit
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder requestTimeout([CtParameterImpl][CtTypeReferenceImpl]long time, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.TimeUnit unit) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]time < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtLiteralImpl]"A timeout above 0 is required: " + [CtVariableReadImpl]time);
            }
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]unit, [CtLiteralImpl]"unit");
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.requestTimeoutMs = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.util.concurrent.TimeUnit.[CtFieldReferenceImpl]MICROSECONDS.convert([CtVariableReadImpl]time, [CtVariableReadImpl]unit);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the name of the HTTP header used to send authorization data in when making requests to
         * MongoDB Realm. The MongoDB server or firewall must have been configured to expect a
         * custom authorization header.
         * <p>
         * The default authorization header is named "Authorization".
         *
         * @param headerName
         * 		name of the header.
         * @throws IllegalArgumentException
         * 		if a null or empty header is provided.
         * @see <a href="https://docs.realm.io/platform/guides/learn-realm-sync-and-integrate-with-a-proxy#adding-a-custom-proxy">Adding a custom proxy</a>
         */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder authorizationHeaderName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String headerName) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkEmpty([CtVariableReadImpl]headerName, [CtLiteralImpl]"headerName");
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.autorizationHeaderName = [CtVariableReadImpl]headerName;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Adds an extra HTTP header to append to every request to a Realm Object Server.
         *
         * @param headerName
         * 		the name of the header.
         * @param headerValue
         * 		the value of header.
         * @throws IllegalArgumentException
         * 		if a non-empty {@code headerName} is provided or a null {@code headerValue}.
         */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder addCustomRequestHeader([CtParameterImpl][CtTypeReferenceImpl]java.lang.String headerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String headerValue) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkEmpty([CtVariableReadImpl]headerName, [CtLiteralImpl]"headerName");
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]headerValue, [CtLiteralImpl]"headerValue");
            [CtInvocationImpl][CtFieldReadImpl]customHeaders.put([CtVariableReadImpl]headerName, [CtVariableReadImpl]headerValue);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Adds extra HTTP headers to append to every request to a Realm Object Server.
         *
         * @param headers
         * 		map of (headerName, headerValue) pairs.
         * @throws IllegalArgumentException
         * 		If any of the headers provided are illegal.
         */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder addCustomRequestHeaders([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> headers) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]headers != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]customHeaders.putAll([CtVariableReadImpl]headers);
            }
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @param errorHandler
         * @return  */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder defaultSyncErrorHandler([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.realm.SyncSession.ErrorHandler errorHandler) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]errorHandler, [CtLiteralImpl]"errorHandler");
            [CtAssignmentImpl][CtFieldWriteImpl]defaultErrorHandler = [CtVariableReadImpl]errorHandler;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Configures the root folder containing all files and Realms used when synchronizing data
         * between the device and MongoDB Realm.
         * <p>
         * The default root dir is {@code Context.getFilesDir()/mongodb-realm}.
         * </p>
         *
         * @param rootDir
         * 		where to store sync related files.
         */
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder syncRootDirectory([CtParameterImpl][CtTypeReferenceImpl]java.io.File rootDir) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]rootDir, [CtLiteralImpl]"rootDir");
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rootDir.isFile()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"'rootDir' is a file, not a directory: " + [CtInvocationImpl][CtVariableReadImpl]rootDir.getAbsolutePath()) + [CtLiteralImpl]".");
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]rootDir.exists()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]rootDir.mkdirs())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Could not create the specified directory: " + [CtInvocationImpl][CtVariableReadImpl]rootDir.getAbsolutePath()) + [CtLiteralImpl]".");
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]rootDir.canWrite()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Realm directory is not writable: " + [CtInvocationImpl][CtVariableReadImpl]rootDir.getAbsolutePath()) + [CtLiteralImpl]".");
            }
            [CtAssignmentImpl][CtFieldWriteImpl]syncRootDir = [CtVariableReadImpl]rootDir;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtCommentImpl]// FIXME Naming: Does it clash with Realm sync concepts and set up wrong expectations?
        [CtCommentImpl]// FIXME Doc
        public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration.Builder codecRegistry([CtParameterImpl][CtTypeReferenceImpl]org.bson.codecs.configuration.CodecRegistry codecRegistry) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]io.realm.internal.Util.checkNull([CtVariableReadImpl]codecRegistry, [CtLiteralImpl]"codecRegistry");
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.codecRegistry = [CtVariableReadImpl]codecRegistry;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]io.realm.RealmAppConfiguration build() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.realm.RealmAppConfiguration([CtFieldReadImpl]appId, [CtFieldReadImpl]appName, [CtFieldReadImpl]appVersion, [CtFieldReadImpl]baseUrl, [CtFieldReadImpl]defaultErrorHandler, [CtFieldReadImpl]encryptionKey, [CtFieldReadImpl]logLevel, [CtFieldReadImpl]requestTimeoutMs, [CtFieldReadImpl]autorizationHeaderName, [CtFieldReadImpl]customHeaders, [CtFieldReadImpl]syncRootDir, [CtFieldReadImpl]codecRegistry);
        }
    }
}
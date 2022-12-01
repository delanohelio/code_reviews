[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.firebase.remoteconfig;
[CtImportImpl]import java.lang.annotation.RetentionPolicy;
[CtUnresolvedImport]import androidx.annotation.StringDef;
[CtImportImpl]import java.lang.annotation.Retention;
[CtClassImpl][CtJavaDocImpl]/**
 * Constants used throughout the Firebase Remote Config SDK.
 *
 * @author Lucas Png
 * @hide  */
public final class RemoteConfigConstants {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String FETCH_REGEX_URL = [CtLiteralImpl]"https://firebaseremoteconfig.googleapis.com/v1/projects/%s/namespaces/%s:fetch";

    [CtAnnotationTypeImpl][CtJavaDocImpl]/**
     * Keys of fields in the Fetch request body that the client sends to the Firebase Remote Config
     * server.
     *
     * <p>{@code INSTANCE_ID} and {@code INSTANCE_ID_TOKEN} are legacy names for the fields that used
     * to be populated by the IID SDK and now come from the Firebase Installations SDK. The fields are
     * now the installation ID and installation auth token, respectively.
     */
    [CtAnnotationImpl]@androidx.annotation.StringDef([CtNewArrayImpl]{ [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]INSTANCE_ID, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]INSTANCE_ID_TOKEN, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]APP_ID, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]COUNTRY_CODE, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]LANGUAGE_CODE, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]PLATFORM_VERSION, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]TIME_ZONE, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]APP_VERSION, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]PACKAGE_NAME, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]SDK_VERSION, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.RequestFieldKey.[CtFieldReferenceImpl]ANALYTICS_USER_PROPERTIES })
    [CtAnnotationImpl]@java.lang.annotation.Retention([CtFieldReadImpl][CtTypeAccessImpl]java.lang.annotation.RetentionPolicy.[CtFieldReferenceImpl]SOURCE)
    public @interface RequestFieldKey {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String INSTANCE_ID = [CtLiteralImpl]"appInstanceId";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String INSTANCE_ID_TOKEN = [CtLiteralImpl]"appInstanceIdToken";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_ID = [CtLiteralImpl]"appId";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COUNTRY_CODE = [CtLiteralImpl]"countryCode";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LANGUAGE_CODE = [CtLiteralImpl]"languageCode";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLATFORM_VERSION = [CtLiteralImpl]"platformVersion";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TIME_ZONE = [CtLiteralImpl]"timeZone";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_VERSION = [CtLiteralImpl]"appVersion";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PACKAGE_NAME = [CtLiteralImpl]"packageName";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SDK_VERSION = [CtLiteralImpl]"sdkVersion";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ANALYTICS_USER_PROPERTIES = [CtLiteralImpl]"analyticsUserProperties";
    }

    [CtAnnotationTypeImpl][CtJavaDocImpl]/**
     * Keys of fields in the Fetch response body from the Firebase Remote Config server.
     */
    [CtAnnotationImpl]@androidx.annotation.StringDef([CtNewArrayImpl]{ [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.ResponseFieldKey.[CtFieldReferenceImpl]ENTRIES, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.ResponseFieldKey.[CtFieldReferenceImpl]EXPERIMENT_DESCRIPTIONS, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.ResponseFieldKey.[CtFieldReferenceImpl]STATE })
    [CtAnnotationImpl]@java.lang.annotation.Retention([CtFieldReadImpl][CtTypeAccessImpl]java.lang.annotation.RetentionPolicy.[CtFieldReferenceImpl]SOURCE)
    public @interface ResponseFieldKey {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ENTRIES = [CtLiteralImpl]"entries";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String EXPERIMENT_DESCRIPTIONS = [CtLiteralImpl]"experimentDescriptions";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String STATE = [CtLiteralImpl]"state";
    }

    [CtAnnotationTypeImpl][CtJavaDocImpl]/**
     * Select keys of fields in the experiment descriptions returned from the Firebase Remote Config
     * server.
     */
    [CtAnnotationImpl]@androidx.annotation.StringDef([CtNewArrayImpl]{ [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.ExperimentDescriptionFieldKey.[CtFieldReferenceImpl]EXPERIMENT_ID, [CtFieldReadImpl][CtTypeAccessImpl]com.google.firebase.remoteconfig.RemoteConfigConstants.ExperimentDescriptionFieldKey.[CtFieldReferenceImpl]VARIANT_ID })
    [CtAnnotationImpl]@java.lang.annotation.Retention([CtFieldReadImpl][CtTypeAccessImpl]java.lang.annotation.RetentionPolicy.[CtFieldReferenceImpl]SOURCE)
    public @interface ExperimentDescriptionFieldKey {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String EXPERIMENT_ID = [CtLiteralImpl]"experimentId";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VARIANT_ID = [CtLiteralImpl]"variantId";
    }

    [CtConstructorImpl]private RemoteConfigConstants() [CtBlockImpl]{
    }
}
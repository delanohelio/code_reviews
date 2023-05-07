[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.firebase.inappmessaging.internal;
[CtImportImpl]import javax.inject.Singleton;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import com.google.internal.firebase.inappmessaging.v1.CampaignProto;
[CtUnresolvedImport]import io.reactivex.Completable;
[CtUnresolvedImport]import io.reactivex.Maybe;
[CtUnresolvedImport]import com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList;
[CtUnresolvedImport]import com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression;
[CtUnresolvedImport]import io.reactivex.Observable;
[CtUnresolvedImport]import com.google.firebase.inappmessaging.internal.injection.qualifiers.ImpressionStore;
[CtUnresolvedImport]import io.reactivex.Single;
[CtUnresolvedImport]import com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl][CtJavaDocImpl]/**
 * Class to store and retrieve in app message impressions
 *
 * @hide  */
[CtAnnotationImpl]@javax.inject.Singleton
public class ImpressionStorageClient {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList EMPTY_IMPRESSIONS = [CtInvocationImpl][CtTypeAccessImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList.getDefaultInstance();

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.firebase.inappmessaging.internal.ProtoStorageClient storageClient;

    [CtFieldImpl]private [CtTypeReferenceImpl]io.reactivex.Maybe<[CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList> cachedImpressionsMaybe = [CtInvocationImpl][CtTypeAccessImpl]io.reactivex.Maybe.empty();

    [CtConstructorImpl][CtAnnotationImpl]@javax.inject.Inject
    ImpressionStorageClient([CtParameterImpl][CtAnnotationImpl]@com.google.firebase.inappmessaging.internal.injection.qualifiers.ImpressionStore
    [CtTypeReferenceImpl]com.google.firebase.inappmessaging.internal.ProtoStorageClient storageClient) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storageClient = [CtVariableReadImpl]storageClient;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList appendImpression([CtParameterImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList campaignImpressions, [CtParameterImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression impression) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList.newBuilder([CtVariableReadImpl]campaignImpressions).addAlreadySeenCampaigns([CtVariableReadImpl]impression).build();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Stores the provided {@link CampaignImpression} to file storage
     */
    public [CtTypeReferenceImpl]io.reactivex.Completable storeImpression([CtParameterImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression impression) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getAllImpressions().defaultIfEmpty([CtFieldReadImpl]com.google.firebase.inappmessaging.internal.ImpressionStorageClient.EMPTY_IMPRESSIONS).flatMapCompletable([CtLambdaImpl]([CtParameterImpl] storedImpressions) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList appendedImpressions = [CtInvocationImpl]appendImpression([CtVariableReadImpl]storedImpressions, [CtVariableReadImpl]impression);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]storageClient.write([CtVariableReadImpl]appendedImpressions).doOnComplete([CtLambdaImpl]() -> [CtInvocationImpl]initInMemCache([CtVariableReadImpl]appendedImpressions));
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the list of impressed campaigns
     *
     * <p>Returns {@link Maybe#empty()} if no campaigns have ever been impressed or if the storage was
     * corrupt.
     */
    public [CtTypeReferenceImpl]io.reactivex.Maybe<[CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList> getAllImpressions() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]cachedImpressionsMaybe.switchIfEmpty([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]storageClient.read([CtInvocationImpl][CtTypeAccessImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList.parser()).doOnSuccess([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::initInMemCache)).doOnError([CtLambdaImpl]([CtParameterImpl] ignored) -> [CtInvocationImpl]clearInMemCache());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void initInMemCache([CtParameterImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList campaignImpressions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]cachedImpressionsMaybe = [CtInvocationImpl][CtTypeAccessImpl]io.reactivex.Maybe.just([CtVariableReadImpl]campaignImpressions);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void clearInMemCache() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]cachedImpressionsMaybe = [CtInvocationImpl][CtTypeAccessImpl]io.reactivex.Maybe.empty();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns {@code Single.just(true)} if the campaign has been impressed
     */
    public [CtTypeReferenceImpl]io.reactivex.Single<[CtTypeReferenceImpl]java.lang.Boolean> isImpressed([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.CampaignProto.ThickContent content) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String campaignId = [CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getPayloadCase().equals([CtTypeAccessImpl]CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getVanillaPayload().getCampaignId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getExperimentalPayload().getCampaignId();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getAllImpressions().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]CampaignImpressionList::getAlreadySeenCampaignsList).flatMapObservable([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Observable::fromIterable).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]CampaignImpression::getCampaignId).contains([CtVariableReadImpl]campaignId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Clears impressions for all campaigns found in the provided {@link FetchEligibleCampaignsResponse}
     */
    public [CtTypeReferenceImpl]io.reactivex.Completable clearImpressions([CtParameterImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse response) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]java.lang.String> idsToClear = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.CampaignProto.ThickContent content : [CtInvocationImpl][CtVariableReadImpl]response.getMessagesList()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String id = [CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getPayloadCase().equals([CtTypeAccessImpl]CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getVanillaPayload().getCampaignId() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]content.getExperimentalPayload().getCampaignId();
            [CtInvocationImpl][CtVariableReadImpl]idsToClear.add([CtVariableReadImpl]id);
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.inappmessaging.internal.Logging.logd([CtBinaryOperatorImpl][CtLiteralImpl]"Potential impressions to clear: " + [CtInvocationImpl][CtVariableReadImpl]idsToClear.toString());
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getAllImpressions().defaultIfEmpty([CtFieldReadImpl]com.google.firebase.inappmessaging.internal.ImpressionStorageClient.EMPTY_IMPRESSIONS).flatMapCompletable([CtLambdaImpl]([CtParameterImpl] storedImpressions) -> [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.inappmessaging.internal.Logging.logd([CtBinaryOperatorImpl][CtLiteralImpl]"Existing impressions: " + [CtInvocationImpl][CtVariableReadImpl]storedImpressions.toString());
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList.Builder clearedImpressionListBuilder = [CtInvocationImpl][CtTypeAccessImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList.newBuilder();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpression storedImpression : [CtInvocationImpl][CtVariableReadImpl]storedImpressions.getAlreadySeenCampaignsList()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]idsToClear.contains([CtInvocationImpl][CtVariableReadImpl]storedImpression.getCampaignId())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]clearedImpressionListBuilder.addAlreadySeenCampaigns([CtVariableReadImpl]storedImpression);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.internal.firebase.inappmessaging.v1.sdkserving.CampaignImpressionList clearedImpressionList = [CtInvocationImpl][CtVariableReadImpl]clearedImpressionListBuilder.build();
            [CtInvocationImpl][CtTypeAccessImpl]com.google.firebase.inappmessaging.internal.Logging.logd([CtBinaryOperatorImpl][CtLiteralImpl]"New cleared impression list: " + [CtInvocationImpl][CtVariableReadImpl]clearedImpressionList.toString());
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]storageClient.write([CtVariableReadImpl]clearedImpressionList).doOnComplete([CtLambdaImpl]() -> [CtInvocationImpl]initInMemCache([CtVariableReadImpl]clearedImpressionList));
        });
    }
}
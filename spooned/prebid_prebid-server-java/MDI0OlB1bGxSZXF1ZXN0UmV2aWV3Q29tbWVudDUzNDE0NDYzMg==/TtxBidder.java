[CompilationUnitImpl][CtPackageDeclarationImpl]package org.prebid.server.bidder.ttx;
[CtUnresolvedImport]import org.prebid.server.bidder.ttx.response.TtxBidExt;
[CtUnresolvedImport]import org.prebid.server.bidder.Bidder;
[CtUnresolvedImport]import org.prebid.server.proto.openrtb.ext.response.BidType;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.prebid.server.bidder.model.BidderBid;
[CtUnresolvedImport]import org.prebid.server.bidder.model.BidderError;
[CtUnresolvedImport]import org.prebid.server.proto.openrtb.ext.request.ttx.ExtImpTtx;
[CtUnresolvedImport]import org.prebid.server.bidder.ttx.proto.TtxImpExtTtx;
[CtUnresolvedImport]import org.prebid.server.json.JacksonMapper;
[CtUnresolvedImport]import org.apache.commons.collections4.CollectionUtils;
[CtUnresolvedImport]import org.prebid.server.json.DecodeException;
[CtUnresolvedImport]import com.iab.openrtb.request.BidRequest;
[CtUnresolvedImport]import org.prebid.server.bidder.model.HttpRequest;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtImportImpl]import com.fasterxml.jackson.core.type.TypeReference;
[CtUnresolvedImport]import com.iab.openrtb.request.Imp;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.prebid.server.proto.openrtb.ext.ExtPrebid;
[CtUnresolvedImport]import org.prebid.server.util.HttpUtil;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.iab.openrtb.response.Bid;
[CtUnresolvedImport]import org.prebid.server.exception.PreBidException;
[CtImportImpl]import com.fasterxml.jackson.databind.node.ObjectNode;
[CtUnresolvedImport]import com.iab.openrtb.request.Video;
[CtUnresolvedImport]import io.vertx.core.http.HttpMethod;
[CtUnresolvedImport]import org.prebid.server.bidder.ttx.response.TtxBidExtTtx;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.prebid.server.bidder.ttx.proto.TtxImpExt;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.iab.openrtb.response.SeatBid;
[CtUnresolvedImport]import org.prebid.server.bidder.model.HttpCall;
[CtUnresolvedImport]import com.iab.openrtb.response.BidResponse;
[CtUnresolvedImport]import com.iab.openrtb.request.Site;
[CtUnresolvedImport]import org.prebid.server.bidder.model.Result;
[CtClassImpl][CtJavaDocImpl]/**
 * 33across {@link Bidder} implementation.
 */
public class TtxBidder implements [CtTypeReferenceImpl]org.prebid.server.bidder.Bidder<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.core.type.TypeReference<[CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.ExtPrebid<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.request.ttx.ExtImpTtx>> TTX_EXT_TYPE_REFERENCE = [CtNewClassImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.core.type.TypeReference<[CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.ExtPrebid<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.request.ttx.ExtImpTtx>>()[CtClassImpl] {};

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String endpointUrl;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.prebid.server.json.JacksonMapper mapper;

    [CtConstructorImpl]public TtxBidder([CtParameterImpl][CtTypeReferenceImpl]java.lang.String endpointUrl, [CtParameterImpl][CtTypeReferenceImpl]org.prebid.server.json.JacksonMapper mapper) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endpointUrl = [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.util.HttpUtil.validateUrl([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]endpointUrl));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.mapper = [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]mapper);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>>> makeHttpRequests([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderError> errors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getImp().get([CtLiteralImpl]0).getBanner() == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getImp().get([CtLiteralImpl]0).getVideo() == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errors.add([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badInput([CtBinaryOperatorImpl][CtLiteralImpl]"At least one of [banner, video] " + [CtLiteralImpl]"formats must be defined in Imp. None found"));
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.Result.withErrors([CtVariableReadImpl]errors);
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpRequest = [CtInvocationImpl]createRequest([CtVariableReadImpl]request, [CtVariableReadImpl]errors);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.Result.of([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]httpRequest), [CtVariableReadImpl]errors);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> createRequest([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest request, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderError> errors) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.iab.openrtb.request.Imp firstImp = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getImp().get([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Site updatedSite = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp updatedFirstImp = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.request.ttx.ExtImpTtx extImpTtx = [CtInvocationImpl]parseImpExt([CtVariableReadImpl]firstImp);
            [CtAssignmentImpl][CtVariableWriteImpl]updatedSite = [CtInvocationImpl]updateSite([CtInvocationImpl][CtVariableReadImpl]request.getSite(), [CtInvocationImpl][CtVariableReadImpl]extImpTtx.getSiteId());
            [CtAssignmentImpl][CtVariableWriteImpl]updatedFirstImp = [CtInvocationImpl]updateFirstImp([CtVariableReadImpl]firstImp, [CtInvocationImpl][CtVariableReadImpl]extImpTtx.getProductId(), [CtInvocationImpl][CtVariableReadImpl]extImpTtx.getZoneId(), [CtVariableReadImpl]errors);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.prebid.server.exception.PreBidException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errors.add([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badInput([CtInvocationImpl][CtVariableReadImpl]e.getMessage()));
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest modifiedRequest = [CtInvocationImpl]updateRequest([CtVariableReadImpl]request, [CtVariableReadImpl]updatedSite, [CtVariableReadImpl]updatedFirstImp);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.HttpRequest.<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>builder().method([CtTypeAccessImpl]HttpMethod.POST).uri([CtFieldReadImpl]endpointUrl).headers([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.util.HttpUtil.headers()).payload([CtVariableReadImpl]modifiedRequest).body([CtInvocationImpl][CtFieldReadImpl]mapper.encode([CtVariableReadImpl]modifiedRequest)).build();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.request.ttx.ExtImpTtx parseImpExt([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp imp) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mapper.mapper().convertValue([CtInvocationImpl][CtVariableReadImpl]imp.getExt(), [CtFieldReadImpl]org.prebid.server.bidder.ttx.TtxBidder.TTX_EXT_TYPE_REFERENCE).getBidder();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.prebid.server.exception.PreBidException([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.iab.openrtb.request.Imp updateFirstImp([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp firstImp, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String zoneId, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderError> errors) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder modifiedFirstImp = [CtInvocationImpl][CtVariableReadImpl]firstImp.toBuilder();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]modifiedFirstImp.ext([CtInvocationImpl]createImpExt([CtVariableReadImpl]productId, [CtVariableReadImpl]zoneId)).build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.iab.openrtb.request.Video video = [CtInvocationImpl][CtVariableReadImpl]firstImp.getVideo();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]video != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]modifiedFirstImp.video([CtInvocationImpl]updateVideo([CtVariableReadImpl]video, [CtVariableReadImpl]productId));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.prebid.server.exception.PreBidException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errors.add([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badInput([CtInvocationImpl][CtVariableReadImpl]e.getMessage()));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]modifiedFirstImp.build();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iab.openrtb.request.Imp> replaceFirstImp([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iab.openrtb.request.Imp> imps, [CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp firstImp) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iab.openrtb.request.Imp> updatedImpList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]imps);
        [CtInvocationImpl][CtVariableReadImpl]updatedImpList.set([CtLiteralImpl]0, [CtVariableReadImpl]firstImp);
        [CtReturnImpl]return [CtVariableReadImpl]updatedImpList;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.fasterxml.jackson.databind.node.ObjectNode createImpExt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String zoneId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.prebid.server.bidder.ttx.proto.TtxImpExt ttxImpExt = [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.ttx.proto.TtxImpExt.of([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.ttx.proto.TtxImpExtTtx.of([CtVariableReadImpl]productId, [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.stripToNull([CtVariableReadImpl]zoneId)));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mapper.mapper().valueToTree([CtVariableReadImpl]ttxImpExt);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.iab.openrtb.request.Video updateVideo([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Video video, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]org.prebid.server.bidder.ttx.TtxBidder.isZeroOrNullInteger([CtInvocationImpl][CtVariableReadImpl]video.getW()) || [CtInvocationImpl]org.prebid.server.bidder.ttx.TtxBidder.isZeroOrNullInteger([CtInvocationImpl][CtVariableReadImpl]video.getH())) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections4.CollectionUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]video.getProtocols())) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections4.CollectionUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]video.getMimes())) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections4.CollectionUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]video.getPlaybackmethod())) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.prebid.server.exception.PreBidException([CtBinaryOperatorImpl][CtLiteralImpl]"One or more invalid or missing video field(s) " + [CtLiteralImpl]"w, h, protocols, mimes, playbackmethod");
        }
        [CtReturnImpl]return [CtInvocationImpl]modifyVideo([CtVariableReadImpl]video, [CtVariableReadImpl]productId);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.iab.openrtb.request.Video modifyVideo([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Video video, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Integer resolvedPlacement = [CtInvocationImpl]resolvePlacement([CtInvocationImpl][CtVariableReadImpl]video.getPlacement(), [CtVariableReadImpl]productId);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.Integer resolvedStartDelay = [CtInvocationImpl]resolveStartDelay([CtVariableReadImpl]productId);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]resolvedPlacement != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]resolvedStartDelay != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]video.toBuilder().startdelay([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]resolvedStartDelay != [CtLiteralImpl]null ? [CtVariableReadImpl]resolvedStartDelay : [CtInvocationImpl][CtVariableReadImpl]video.getStartdelay()).placement([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]resolvedPlacement != [CtLiteralImpl]null ? [CtVariableReadImpl]resolvedPlacement : [CtInvocationImpl][CtVariableReadImpl]video.getPlacement()).build() : [CtVariableReadImpl]video;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Integer resolvePlacement([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer videoPlacement, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]productId, [CtLiteralImpl]"instream") ? [CtLiteralImpl]1 : [CtConditionalImpl][CtInvocationImpl]org.prebid.server.bidder.ttx.TtxBidder.isZeroOrNullInteger([CtVariableReadImpl]videoPlacement) ? [CtLiteralImpl]2 : [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Integer resolveStartDelay([CtParameterImpl][CtTypeReferenceImpl]java.lang.String productId) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtVariableReadImpl]productId, [CtLiteralImpl]"instream") ? [CtLiteralImpl]0 : [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.iab.openrtb.request.Site updateSite([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Site site, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteId) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Site.SiteBuilder siteBuilder = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]site == [CtLiteralImpl]null) ? [CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Site.builder() : [CtInvocationImpl][CtVariableReadImpl]site.toBuilder();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]siteBuilder.id([CtVariableReadImpl]siteId).build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean isZeroOrNullInteger([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer integer) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]integer == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]integer == [CtLiteralImpl]0);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest updateRequest([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest request, [CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Site site, [CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp firstImp) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]site == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]firstImp == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]request;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.iab.openrtb.request.Imp> requestImps = [CtInvocationImpl][CtVariableReadImpl]request.getImp();
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.toBuilder().site([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null ? [CtVariableReadImpl]site : [CtInvocationImpl][CtVariableReadImpl]request.getSite()).imp([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]firstImp != [CtLiteralImpl]null ? [CtInvocationImpl]replaceFirstImp([CtVariableReadImpl]requestImps, [CtVariableReadImpl]firstImp) : [CtVariableReadImpl]requestImps).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid>> makeBids([CtParameterImpl][CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpCall, [CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest bidRequest) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.iab.openrtb.response.BidResponse bidResponse = [CtInvocationImpl][CtFieldReadImpl]mapper.decodeValue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]httpCall.getResponse().getBody(), [CtFieldReadImpl]com.iab.openrtb.response.BidResponse.class);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.Result.of([CtInvocationImpl]extractBids([CtVariableReadImpl]bidResponse), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]org.prebid.server.json.DecodeException | [CtTypeReferenceImpl]org.prebid.server.exception.PreBidException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.Result.withError([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badServerResponse([CtInvocationImpl][CtVariableReadImpl]e.getMessage()));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid> extractBids([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.response.BidResponse bidResponse) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]bidResponse == [CtLiteralImpl]null) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections4.CollectionUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]bidResponse.getSeatbid())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
        [CtReturnImpl]return [CtInvocationImpl]bidsFromResponse([CtVariableReadImpl]bidResponse);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid> bidsFromResponse([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.response.BidResponse bidResponse) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bidResponse.getSeatbid().stream().filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]SeatBid::getBid).filter([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Objects::nonNull).flatMap([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.util.Collection::stream).map([CtLambdaImpl]([CtParameterImpl] bid) -> [CtInvocationImpl]createBidderBid([CtVariableReadImpl]bid, [CtInvocationImpl][CtVariableReadImpl]bidResponse.getCur())).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid createBidderBid([CtParameterImpl][CtTypeReferenceImpl]com.iab.openrtb.response.Bid bid, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String currency) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.response.BidType bidType;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.prebid.server.bidder.ttx.response.TtxBidExt ttxBidExt = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mapper.mapper().convertValue([CtInvocationImpl][CtVariableReadImpl]bid.getExt(), [CtFieldReadImpl]org.prebid.server.bidder.ttx.response.TtxBidExt.class);
            [CtAssignmentImpl][CtVariableWriteImpl]bidType = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]ttxBidExt != [CtLiteralImpl]null) ? [CtInvocationImpl]org.prebid.server.bidder.ttx.TtxBidder.getBidType([CtInvocationImpl][CtVariableReadImpl]ttxBidExt.getTtx()) : [CtFieldReadImpl]org.prebid.server.proto.openrtb.ext.response.BidType.banner;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]bidType = [CtFieldReadImpl]org.prebid.server.proto.openrtb.ext.response.BidType.banner;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderBid.of([CtVariableReadImpl]bid, [CtVariableReadImpl]bidType, [CtVariableReadImpl]currency);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.prebid.server.proto.openrtb.ext.response.BidType getBidType([CtParameterImpl][CtTypeReferenceImpl]org.prebid.server.bidder.ttx.response.TtxBidExtTtx bidExt) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]bidExt != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtVariableReadImpl]bidExt.getMediaType(), [CtLiteralImpl]"video")) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.prebid.server.proto.openrtb.ext.response.BidType.video;
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.prebid.server.proto.openrtb.ext.response.BidType.banner;
    }
}
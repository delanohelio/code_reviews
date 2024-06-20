[CompilationUnitImpl][CtPackageDeclarationImpl]package org.prebid.server.bidder.admixer;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import org.prebid.server.bidder.model.HttpResponse;
[CtImportImpl]import static java.util.Collections.emptyMap;
[CtUnresolvedImport]import org.prebid.server.bidder.model.BidderBid;
[CtUnresolvedImport]import org.prebid.server.bidder.model.BidderError;
[CtImportImpl]import com.fasterxml.jackson.databind.JsonNode;
[CtImportImpl]import static java.util.Arrays.asList;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import com.iab.openrtb.request.Banner;
[CtUnresolvedImport]import com.iab.openrtb.request.BidRequest;
[CtImportImpl]import static java.util.Collections.emptyList;
[CtImportImpl]import static java.util.Collections.singletonMap;
[CtUnresolvedImport]import org.prebid.server.bidder.model.HttpRequest;
[CtImportImpl]import static java.util.function.Function.identity;
[CtUnresolvedImport]import com.iab.openrtb.request.Imp;
[CtImportImpl]import java.util.List;
[CtImportImpl]import com.fasterxml.jackson.core.JsonProcessingException;
[CtUnresolvedImport]import org.prebid.server.proto.openrtb.ext.ExtPrebid;
[CtUnresolvedImport]import org.junit.Before;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import static org.prebid.server.proto.openrtb.ext.response.BidType.banner;
[CtUnresolvedImport]import com.iab.openrtb.response.Bid;
[CtImportImpl]import static java.util.Collections.singletonList;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
[CtUnresolvedImport]import com.iab.openrtb.response.SeatBid;
[CtUnresolvedImport]import org.prebid.server.bidder.model.HttpCall;
[CtUnresolvedImport]import com.iab.openrtb.request.Native;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.iab.openrtb.response.BidResponse;
[CtUnresolvedImport]import org.prebid.server.VertxTest;
[CtUnresolvedImport]import org.prebid.server.bidder.model.Result;
[CtUnresolvedImport]import org.prebid.server.proto.openrtb.ext.request.admixer.ExtImpAdmixer;
[CtClassImpl]public class AdmixerBidderTest extends [CtTypeReferenceImpl]org.prebid.server.VertxTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ENDPOINT_URL = [CtLiteralImpl]"https://test.endpoint.com/";

    [CtFieldImpl]private [CtTypeReferenceImpl]org.prebid.server.bidder.admixer.AdmixerBidder admixerBidder;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]admixerBidder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.prebid.server.bidder.admixer.AdmixerBidder([CtFieldReadImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.ENDPOINT_URL, [CtFieldReadImpl]jacksonMapper);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void creationShouldFailOnInvalidEndpointUrl() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]assertThatIllegalArgumentException().isThrownBy([CtLambdaImpl]() -> [CtConstructorCallImpl]new <org.prebid.server.bidder.admixer.jacksonMapper>[CtTypeReferenceImpl]org.prebid.server.bidder.admixer.AdmixerBidder([CtLiteralImpl]"invalid_url"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeHttpRequestsShouldReturnErrorIfImpressionListSizeIsZero() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest bidRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().imp([CtInvocationImpl]java.util.Collections.emptyList()).build();
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeHttpRequests([CtVariableReadImpl]bidRequest);
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).hasSize([CtLiteralImpl]1).containsOnly([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badInput([CtLiteralImpl]"No valid impressions in the bid request"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeHttpRequestsShouldReturnErrorIfZoneIdLengthIsNotEqual36() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest bidRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().imp([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Imp.builder().id([CtLiteralImpl]"123").ext([CtInvocationImpl][CtFieldReadImpl]mapper.valueToTree([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.proto.openrtb.ext.ExtPrebid.of([CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.proto.openrtb.ext.request.admixer.ExtImpAdmixer.of([CtLiteralImpl]"zoneId", [CtLiteralImpl]36.0, [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenCustomParams([CtLiteralImpl]"foo1", [CtInvocationImpl]java.util.Collections.singletonList([CtLiteralImpl]"bar1")))))).build())).build();
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeHttpRequests([CtVariableReadImpl]bidRequest);
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).hasSize([CtLiteralImpl]1).containsOnly([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderError.badInput([CtLiteralImpl]"ZoneId must be UUID/GUID"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeHttpRequestsShouldCreateCorrectURL() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest bidRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().imp([CtInvocationImpl]java.util.Arrays.asList([CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenImp([CtInvocationImpl]java.util.function.Function.identity()), [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenImp([CtLambdaImpl]([CtParameterImpl]Imp.ImpBuilder impBuilder) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]impBuilder.banner([CtLiteralImpl]null).xNative([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Native.builder().build())))).build();
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpRequest<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeHttpRequests([CtVariableReadImpl]bidRequest);
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).isEmpty();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getValue()).hasSize([CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getValue().get([CtLiteralImpl]0).getUri()).isEqualTo([CtFieldReadImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.ENDPOINT_URL);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeBidsShouldReturnErrorIfResponseBodyCouldNotBeParsed() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpCall = [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenHttpCall([CtLiteralImpl]"false");
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeBids([CtVariableReadImpl]httpCall, [CtLiteralImpl]null);
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getErrors().get([CtLiteralImpl]0).getType()).isEqualTo([CtTypeAccessImpl]BidderError.Type.bad_server_response);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getValue()).isEmpty();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeBidsShouldReturnErrorsWhenSeatBidIsEmpty() throws [CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpCall = [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenHttpCall([CtInvocationImpl][CtFieldReadImpl]mapper.writeValueAsString([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.BidResponse.builder().seatbid([CtInvocationImpl]java.util.Collections.emptyList()).build()));
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeBids([CtVariableReadImpl]httpCall, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().build());
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).isEmpty();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]result).isNotNull().extracting([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Result::getValue, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Result::getErrors).containsOnly([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeBidsShouldReturnErrorsWhenBidsEmpty() throws [CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpCall = [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenHttpCall([CtInvocationImpl][CtFieldReadImpl]mapper.writeValueAsString([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.BidResponse.builder().seatbid([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.SeatBid.builder().bid([CtInvocationImpl]java.util.Collections.emptyList()).build())).build()));
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeBids([CtVariableReadImpl]httpCall, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().build());
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).isEmpty();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]result).isNotNull().extracting([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Result::getValue, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Result::getErrors).containsOnly([CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void makeBidsShouldReturnBannerBidIfBannerIsPresentInRequestImp() throws [CtTypeReferenceImpl]com.fasterxml.jackson.core.JsonProcessingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// given
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> httpCall = [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenHttpCall([CtInvocationImpl][CtFieldReadImpl]mapper.writeValueAsString([CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenBidResponse([CtLambdaImpl]([CtParameterImpl]Bid.BidBuilder bidBuilder) -> [CtInvocationImpl][CtVariableReadImpl]bidBuilder.impid([CtLiteralImpl]"123"))));
        [CtLocalVariableImpl][CtCommentImpl]// when
        final [CtTypeReferenceImpl]org.prebid.server.bidder.model.Result<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.prebid.server.bidder.model.BidderBid>> result = [CtInvocationImpl][CtFieldReadImpl]admixerBidder.makeBids([CtVariableReadImpl]httpCall, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().imp([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Imp.builder().id([CtLiteralImpl]"123").banner([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Banner.builder().build()).build())).build());
        [CtInvocationImpl][CtCommentImpl]// then
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getErrors()).isEmpty();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.getValue()).containsOnly([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.BidderBid.of([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.Bid.builder().impid([CtLiteralImpl]"123").build(), [CtFieldReadImpl]banner, [CtLiteralImpl]"USD"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void extractTargetingShouldReturnEmptyMap() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtFieldReadImpl]admixerBidder.extractTargeting([CtInvocationImpl][CtFieldReadImpl]mapper.createObjectNode())).isEqualTo([CtInvocationImpl]java.util.Collections.emptyMap());
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest givenBidRequest([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest.BidRequestBuilder, [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest.BidRequestBuilder> bidRequestCustomizer, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder, [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder> impCustomizer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bidRequestCustomizer.apply([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.BidRequest.builder().imp([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenImp([CtVariableReadImpl]impCustomizer)))).build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest givenBidRequest([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder, [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder> impCustomizer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenBidRequest([CtInvocationImpl]java.util.function.Function.identity(), [CtVariableReadImpl]impCustomizer);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.iab.openrtb.request.Imp givenImp([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder, [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.request.Imp.ImpBuilder> impCustomizer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]impCustomizer.apply([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Imp.builder().id([CtLiteralImpl]"123").banner([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.request.Banner.builder().id([CtLiteralImpl]"banner_id").build()).ext([CtInvocationImpl][CtFieldReadImpl]mapper.valueToTree([CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.proto.openrtb.ext.ExtPrebid.of([CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.proto.openrtb.ext.request.admixer.ExtImpAdmixer.of([CtLiteralImpl]"3e56bd58-865c-47ce-af7f-a918108c3fd2", [CtLiteralImpl]36.0, [CtInvocationImpl]org.prebid.server.bidder.admixer.AdmixerBidderTest.givenCustomParams([CtLiteralImpl]"foo1", [CtInvocationImpl]java.util.Collections.singletonList([CtLiteralImpl]"bar1"))))))).build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.iab.openrtb.response.BidResponse givenBidResponse([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.response.Bid.BidBuilder, [CtTypeReferenceImpl][CtTypeReferenceImpl]com.iab.openrtb.response.Bid.BidBuilder> bidCustomizer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.BidResponse.builder().seatbid([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.SeatBid.builder().bid([CtInvocationImpl]java.util.Collections.singletonList([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bidCustomizer.apply([CtInvocationImpl][CtTypeAccessImpl]com.iab.openrtb.response.Bid.builder()).build())).build())).build();
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.prebid.server.bidder.model.HttpCall<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest> givenHttpCall([CtParameterImpl][CtTypeReferenceImpl]java.lang.String body) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.HttpCall.success([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.HttpRequest.<[CtTypeReferenceImpl]com.iab.openrtb.request.BidRequest>builder().payload([CtLiteralImpl]null).build(), [CtInvocationImpl][CtTypeAccessImpl]org.prebid.server.bidder.model.HttpResponse.of([CtLiteralImpl]200, [CtLiteralImpl]null, [CtVariableReadImpl]body), [CtLiteralImpl]null);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.fasterxml.jackson.databind.JsonNode> givenCustomParams([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object values) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]java.util.Collections.singletonMap([CtVariableReadImpl]key, [CtInvocationImpl][CtFieldReadImpl]mapper.valueToTree([CtVariableReadImpl]values));
    }
}
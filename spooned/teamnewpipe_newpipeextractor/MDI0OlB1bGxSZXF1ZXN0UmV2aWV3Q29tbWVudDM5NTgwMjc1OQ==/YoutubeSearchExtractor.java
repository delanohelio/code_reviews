[CompilationUnitImpl][CtPackageDeclarationImpl]package org.schabi.newpipe.extractor.services.youtube.extractors;
[CtUnresolvedImport]import com.grack.nanojson.JsonObject;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getUrlFromNavigationEndpoint;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.MUSIC_VIDEOS;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.search.SearchExtractor;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.playlist.PlaylistExtractor.UNKNOWN_ITEMS;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.StreamingService;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.MUSIC_PLAYLISTS;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.MUSIC_ALBUMS;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.search.InfoItemsSearchCollector;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.InfoItem;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.localization.DateWrapper;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.playlist.PlaylistExtractor.MORE_THAN_100_ITEMS;
[CtUnresolvedImport]import com.grack.nanojson.JsonArray;
[CtUnresolvedImport]import com.grack.nanojson.JsonParserException;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.MUSIC_ARTISTS;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ParsingException;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ExtractionException;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.fixThumbnailUrl;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.downloader.Downloader;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getTextFromObject;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.utils.Utils;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.localization.TimeAgoParser;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getJsonResponse;
[CtUnresolvedImport]import com.grack.nanojson.JsonParser;
[CtUnresolvedImport]import com.grack.nanojson.JsonWriter;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.downloader.Response;
[CtUnresolvedImport]import static org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory.MUSIC_SONGS;
[CtClassImpl][CtCommentImpl]/* Created by Christian Schabesberger on 22.07.2018

Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.org>
YoutubeSearchExtractor.java is part of NewPipe.

NewPipe is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

NewPipe is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */
public class YoutubeSearchExtractor extends [CtTypeReferenceImpl]org.schabi.newpipe.extractor.search.SearchExtractor {
    [CtFieldImpl]private [CtTypeReferenceImpl]com.grack.nanojson.JsonObject initialData;

    [CtConstructorImpl]public YoutubeSearchExtractor([CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.StreamingService service, [CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler linkHandler) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]service, [CtVariableReadImpl]linkHandler);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onFetchPage([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]org.schabi.newpipe.extractor.downloader.Downloader downloader) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] youtubeMusicKeys = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getYoutubeMusicKeys();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String url = [CtBinaryOperatorImpl][CtLiteralImpl]"https://music.youtube.com/youtubei/v1/search?alt=json&key=" + [CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]0];
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String params = [CtLiteralImpl]null;
            [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLinkHandler().getContentFilters().get([CtLiteralImpl]0)) {
                [CtCaseImpl]case [CtFieldReadImpl]YoutubeSearchQueryHandlerFactory.MUSIC_SONGS :
                    [CtAssignmentImpl][CtVariableWriteImpl]params = [CtLiteralImpl]"Eg-KAQwIARAAGAAgACgAMABqChAEEAUQAxAKEAk%3D";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]YoutubeSearchQueryHandlerFactory.MUSIC_VIDEOS :
                    [CtAssignmentImpl][CtVariableWriteImpl]params = [CtLiteralImpl]"Eg-KAQwIABABGAAgACgAMABqChAEEAUQAxAKEAk%3D";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ALBUMS :
                    [CtAssignmentImpl][CtVariableWriteImpl]params = [CtLiteralImpl]"Eg-KAQwIABAAGAEgACgAMABqChAEEAUQAxAKEAk%3D";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]YoutubeSearchQueryHandlerFactory.MUSIC_PLAYLISTS :
                    [CtAssignmentImpl][CtVariableWriteImpl]params = [CtLiteralImpl]"Eg-KAQwIABAAGAAgACgBMABqChAEEAUQAxAKEAk%3D";
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ARTISTS :
                    [CtAssignmentImpl][CtVariableWriteImpl]params = [CtLiteralImpl]"Eg-KAQwIABAAGAAgASgAMABqChAEEAUQAxAKEAk%3D";
                    [CtBreakImpl]break;
            }
            [CtLocalVariableImpl][CtCommentImpl]// @formatter:off
            [CtArrayTypeReferenceImpl]byte[] json = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonWriter.string().object().object([CtLiteralImpl]"context").object([CtLiteralImpl]"client").value([CtLiteralImpl]"clientName", [CtLiteralImpl]"WEB_REMIX").value([CtLiteralImpl]"clientVersion", [CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]2]).value([CtLiteralImpl]"hl", [CtLiteralImpl]"en").value([CtLiteralImpl]"gl", [CtInvocationImpl][CtInvocationImpl]getExtractorContentCountry().getCountryCode()).array([CtLiteralImpl]"experimentIds").end().value([CtLiteralImpl]"experimentsToken", [CtLiteralImpl]"").value([CtLiteralImpl]"utcOffsetMinutes", [CtLiteralImpl]0).object([CtLiteralImpl]"locationInfo").end().object([CtLiteralImpl]"musicAppInfo").end().end().object([CtLiteralImpl]"capabilities").end().object([CtLiteralImpl]"request").array([CtLiteralImpl]"internalExperimentFlags").end().object([CtLiteralImpl]"sessionIndex").end().end().object([CtLiteralImpl]"activePlayers").end().object([CtLiteralImpl]"user").value([CtLiteralImpl]"enableSafetyMode", [CtLiteralImpl]false).end().end().value([CtLiteralImpl]"query", [CtInvocationImpl]getSearchString()).value([CtLiteralImpl]"params", [CtVariableReadImpl]params).end().done().getBytes([CtLiteralImpl]"UTF-8");
            [CtLocalVariableImpl][CtCommentImpl]// @formatter:on
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"X-YouTube-Client-Name", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]1]));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"X-YouTube-Client-Version", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]2]));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Origin", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"https://music.youtube.com"));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Referer", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"music.youtube.com"));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Content-Type", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"application/json"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.downloader.Response response = [CtInvocationImpl][CtInvocationImpl]getDownloader().post([CtVariableReadImpl]url, [CtVariableReadImpl]headers, [CtVariableReadImpl]json);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]response.responseCode() == [CtLiteralImpl]404) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Not found" + [CtLiteralImpl]" (\"") + [CtInvocationImpl][CtVariableReadImpl]response.responseCode()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]response.responseMessage()) + [CtLiteralImpl]"\")");
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String responseBody = [CtInvocationImpl][CtVariableReadImpl]response.responseBody();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]responseBody.length() < [CtLiteralImpl]50) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// ensure to have a valid response
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"JSON response is too short");
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String responseContentType = [CtInvocationImpl][CtVariableReadImpl]response.getHeader([CtLiteralImpl]"Content-Type");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]responseContentType != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]responseContentType.toLowerCase().contains([CtLiteralImpl]"text/html")) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Got HTML document, expected JSON response" + [CtLiteralImpl]" (latest url was: \"") + [CtInvocationImpl][CtVariableReadImpl]response.latestUrl()) + [CtLiteralImpl]"\")");
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]initialData = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonParser.object().from([CtVariableReadImpl]responseBody);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonParserException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not parse JSON", [CtVariableReadImpl]e);
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String url = [CtBinaryOperatorImpl][CtInvocationImpl]getUrl() + [CtLiteralImpl]"&pbj=1";
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonArray ajaxJson = [CtInvocationImpl]YoutubeParsingHelper.getJsonResponse([CtVariableReadImpl]url, [CtInvocationImpl]getExtractorLocalization());
            [CtAssignmentImpl][CtFieldWriteImpl]initialData = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ajaxJson.getObject([CtLiteralImpl]1).getObject([CtLiteralImpl]"response");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch())[CtBlockImpl]
            [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.getUrl();

        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtSuperAccessImpl]super.getUrl() + [CtLiteralImpl]"&gl=") + [CtInvocationImpl][CtInvocationImpl]getExtractorContentCountry().getCountryCode();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getSearchSuggestion() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonObject itemSectionRenderer = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"itemSectionRenderer");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]itemSectionRenderer == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"";

            [CtReturnImpl]return [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]itemSectionRenderer.getArray([CtLiteralImpl]"contents").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"didYouMeanRenderer").getObject([CtLiteralImpl]"correctedQuery"));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject showingResultsForRenderer = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"twoColumnSearchResultsRenderer").getObject([CtLiteralImpl]"primaryContents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"itemSectionRenderer").getArray([CtLiteralImpl]"contents").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"showingResultsForRenderer");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]showingResultsForRenderer == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtLiteralImpl]"";

            [CtReturnImpl]return [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtVariableReadImpl]showingResultsForRenderer.getObject([CtLiteralImpl]"correctedQuery"));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<[CtTypeReferenceImpl]org.schabi.newpipe.extractor.InfoItem> getInitialPage() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.search.InfoItemsSearchCollector collector = [CtInvocationImpl]getInfoItemSearchCollector();
        [CtInvocationImpl][CtVariableReadImpl]collector.reset();
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonArray contents = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object content : [CtVariableReadImpl]contents) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (content)).getObject([CtLiteralImpl]"musicShelfRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]collectMusicStreamsFrom([CtVariableReadImpl]collector, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (content)).getObject([CtLiteralImpl]"musicShelfRenderer").getArray([CtLiteralImpl]"contents"));
                }
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray sections = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"twoColumnSearchResultsRenderer").getObject([CtLiteralImpl]"primaryContents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object section : [CtVariableReadImpl]sections) [CtBlockImpl]{
                [CtInvocationImpl]collectStreamsFrom([CtVariableReadImpl]collector, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (section)).getObject([CtLiteralImpl]"itemSectionRenderer").getArray([CtLiteralImpl]"contents"));
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<>([CtVariableReadImpl]collector, [CtInvocationImpl]getNextPageUrl());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getNextPageUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonArray contents = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object content : [CtVariableReadImpl]contents) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (content)).getObject([CtLiteralImpl]"musicShelfRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]getNextPageUrlFrom([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (content)).getObject([CtLiteralImpl]"musicShelfRenderer").getArray([CtLiteralImpl]"continuations"));
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]"";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getNextPageUrlFrom([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]initialData.getObject([CtLiteralImpl]"contents").getObject([CtLiteralImpl]"twoColumnSearchResultsRenderer").getObject([CtLiteralImpl]"primaryContents").getObject([CtLiteralImpl]"sectionListRenderer").getArray([CtLiteralImpl]"contents").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"itemSectionRenderer").getArray([CtLiteralImpl]"continuations"));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<[CtTypeReferenceImpl]org.schabi.newpipe.extractor.InfoItem> getPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageUrl) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]pageUrl == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]pageUrl.isEmpty()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Page url is empty or null"));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.search.InfoItemsSearchCollector collector = [CtInvocationImpl]getInfoItemSearchCollector();
        [CtInvocationImpl][CtVariableReadImpl]collector.reset();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray continuations;
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] youtubeMusicKeys = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getYoutubeMusicKeys();
            [CtLocalVariableImpl][CtCommentImpl]// @formatter:off
            [CtArrayTypeReferenceImpl]byte[] json = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonWriter.string().object().object([CtLiteralImpl]"context").object([CtLiteralImpl]"client").value([CtLiteralImpl]"clientName", [CtLiteralImpl]"WEB_REMIX").value([CtLiteralImpl]"clientVersion", [CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]2]).value([CtLiteralImpl]"hl", [CtLiteralImpl]"en").value([CtLiteralImpl]"gl", [CtInvocationImpl][CtInvocationImpl]getExtractorContentCountry().getCountryCode()).array([CtLiteralImpl]"experimentIds").end().value([CtLiteralImpl]"experimentsToken", [CtLiteralImpl]"").value([CtLiteralImpl]"utcOffsetMinutes", [CtLiteralImpl]0).object([CtLiteralImpl]"locationInfo").end().object([CtLiteralImpl]"musicAppInfo").end().end().object([CtLiteralImpl]"capabilities").end().object([CtLiteralImpl]"request").array([CtLiteralImpl]"internalExperimentFlags").end().object([CtLiteralImpl]"sessionIndex").end().end().object([CtLiteralImpl]"activePlayers").end().object([CtLiteralImpl]"user").value([CtLiteralImpl]"enableSafetyMode", [CtLiteralImpl]false).end().end().end().done().getBytes([CtLiteralImpl]"UTF-8");
            [CtLocalVariableImpl][CtCommentImpl]// @formatter:on
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> headers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"X-YouTube-Client-Name", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]1]));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"X-YouTube-Client-Version", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtArrayReadImpl][CtVariableReadImpl]youtubeMusicKeys[[CtLiteralImpl]2]));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Origin", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"https://music.youtube.com"));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Referer", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"music.youtube.com"));
            [CtInvocationImpl][CtVariableReadImpl]headers.put([CtLiteralImpl]"Content-Type", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtLiteralImpl]"application/json"));
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.downloader.Response response = [CtInvocationImpl][CtInvocationImpl]getDownloader().post([CtVariableReadImpl]pageUrl, [CtVariableReadImpl]headers, [CtVariableReadImpl]json);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]response.responseCode() == [CtLiteralImpl]404) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Not found" + [CtLiteralImpl]" (\"") + [CtInvocationImpl][CtVariableReadImpl]response.responseCode()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]response.responseMessage()) + [CtLiteralImpl]"\")");
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String responseBody = [CtInvocationImpl][CtVariableReadImpl]response.responseBody();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]responseBody.length() < [CtLiteralImpl]50) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// ensure to have a valid response
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"JSON response is too short");
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String responseContentType = [CtInvocationImpl][CtVariableReadImpl]response.getHeader([CtLiteralImpl]"Content-Type");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]responseContentType != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]responseContentType.toLowerCase().contains([CtLiteralImpl]"text/html")) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Got HTML document, expected JSON response" + [CtLiteralImpl]" (latest url was: \"") + [CtInvocationImpl][CtVariableReadImpl]response.latestUrl()) + [CtLiteralImpl]"\")");
            }
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonObject ajaxJson;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]ajaxJson = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonParser.object().from([CtVariableReadImpl]responseBody);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonParserException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not parse JSON", [CtVariableReadImpl]e);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ajaxJson.getObject([CtLiteralImpl]"continuationContents") == [CtLiteralImpl]null)[CtBlockImpl]
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<>([CtVariableReadImpl]collector, [CtLiteralImpl]null);

            [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject musicShelfContinuation = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ajaxJson.getObject([CtLiteralImpl]"continuationContents").getObject([CtLiteralImpl]"musicShelfContinuation");
            [CtInvocationImpl]collectMusicStreamsFrom([CtVariableReadImpl]collector, [CtInvocationImpl][CtVariableReadImpl]musicShelfContinuation.getArray([CtLiteralImpl]"contents"));
            [CtAssignmentImpl][CtVariableWriteImpl]continuations = [CtInvocationImpl][CtVariableReadImpl]musicShelfContinuation.getArray([CtLiteralImpl]"continuations");
        } else [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonArray ajaxJson = [CtInvocationImpl]YoutubeParsingHelper.getJsonResponse([CtVariableReadImpl]pageUrl, [CtInvocationImpl]getExtractorLocalization());
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject itemSectionRenderer = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ajaxJson.getObject([CtLiteralImpl]1).getObject([CtLiteralImpl]"response").getObject([CtLiteralImpl]"continuationContents").getObject([CtLiteralImpl]"itemSectionContinuation");
            [CtInvocationImpl]collectStreamsFrom([CtVariableReadImpl]collector, [CtInvocationImpl][CtVariableReadImpl]itemSectionRenderer.getArray([CtLiteralImpl]"contents"));
            [CtAssignmentImpl][CtVariableWriteImpl]continuations = [CtInvocationImpl][CtVariableReadImpl]itemSectionRenderer.getArray([CtLiteralImpl]"continuations");
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<>([CtVariableReadImpl]collector, [CtInvocationImpl]getNextPageUrlFrom([CtVariableReadImpl]continuations));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isMusicSearch() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> contentFilters = [CtInvocationImpl][CtInvocationImpl]getLinkHandler().getContentFilters();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentFilters.size() > [CtLiteralImpl]0) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentFilters.get([CtLiteralImpl]0).startsWith([CtLiteralImpl]"music_"))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void collectStreamsFrom([CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.search.InfoItemsSearchCollector collector, [CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray videos) throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.NothingFoundException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.extractor.localization.TimeAgoParser timeAgoParser = [CtInvocationImpl]getTimeAgoParser();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object item : [CtVariableReadImpl]videos) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"backgroundPromoRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.NothingFoundException([CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"backgroundPromoRenderer").getObject([CtLiteralImpl]"bodyText")));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"videoRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"videoRenderer"), [CtVariableReadImpl]timeAgoParser));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"channelRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelInfoItemExtractor([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"channelRenderer")));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"playlistRenderer") != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistInfoItemExtractor([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"playlistRenderer")));
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void collectMusicStreamsFrom([CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.search.InfoItemsSearchCollector collector, [CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray videos) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.extractor.localization.TimeAgoParser timeAgoParser = [CtInvocationImpl]getTimeAgoParser();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object item : [CtVariableReadImpl]videos) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.grack.nanojson.JsonObject info = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (item)).getObject([CtLiteralImpl]"musicResponsiveListItemRenderer");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]info != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String searchType = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLinkHandler().getContentFilters().get([CtLiteralImpl]0);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_SONGS) || [CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_VIDEOS)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtNewClassImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor([CtVariableReadImpl]info, [CtVariableReadImpl]timeAgoParser)[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtInvocationImpl]YoutubeParsingHelper.getUrlFromNavigationEndpoint([CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"doubleTapCommand"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]url != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]url.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]url;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get url");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]name;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get name");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]long getDuration() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String duration = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]3).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]duration != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]duration.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.parseDurationString([CtVariableReadImpl]duration);

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get duration");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getUploaderName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]1).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]name;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get uploader name");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getTextualUploadDate() [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]null;
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]org.schabi.newpipe.extractor.localization.DateWrapper getUploadDate() [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]null;
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]long getViewCount() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_SONGS))[CtBlockImpl]
                                [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;

                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String viewCount = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]2).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]viewCount != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]viewCount.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.Utils.mixedNumberWordToLong([CtVariableReadImpl]viewCount);

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get view count");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getThumbnailUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtReturnImpl][CtCommentImpl]// TODO: Don't simply get the first item, but look at all thumbnails and their resolution
                                return [CtInvocationImpl]YoutubeParsingHelper.fixThumbnailUrl([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"thumbnail").getObject([CtLiteralImpl]"musicThumbnailRenderer").getObject([CtLiteralImpl]"thumbnail").getArray([CtLiteralImpl]"thumbnails").getObject([CtLiteralImpl]0).getString([CtLiteralImpl]"url"));
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get thumbnail url", [CtVariableReadImpl]e);
                            }
                        }
                    });
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ARTISTS)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtNewClassImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelInfoItemExtractor([CtVariableReadImpl]info)[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getThumbnailUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtReturnImpl][CtCommentImpl]// TODO: Don't simply get the first item, but look at all thumbnails and their resolution
                                return [CtInvocationImpl]YoutubeParsingHelper.fixThumbnailUrl([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"thumbnail").getObject([CtLiteralImpl]"musicThumbnailRenderer").getObject([CtLiteralImpl]"thumbnail").getArray([CtLiteralImpl]"thumbnails").getObject([CtLiteralImpl]0).getString([CtLiteralImpl]"url"));
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get thumbnail url", [CtVariableReadImpl]e);
                            }
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]name;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get name");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtInvocationImpl]YoutubeParsingHelper.getUrlFromNavigationEndpoint([CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"navigationEndpoint"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]url != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]url.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]url;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get url");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]long getSubscriberCount() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String viewCount = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]2).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]viewCount != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]viewCount.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.Utils.mixedNumberWordToLong([CtVariableReadImpl]viewCount);

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get subscriber count");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]long getStreamCount() [CtBlockImpl]{
                            [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getDescription() [CtBlockImpl]{
                            [CtReturnImpl]return [CtLiteralImpl]null;
                        }
                    });
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ALBUMS) || [CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_PLAYLISTS)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtNewClassImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistInfoItemExtractor([CtVariableReadImpl]info)[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getThumbnailUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtReturnImpl][CtCommentImpl]// TODO: Don't simply get the first item, but look at all thumbnails and their resolution
                                return [CtInvocationImpl]YoutubeParsingHelper.fixThumbnailUrl([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"thumbnail").getObject([CtLiteralImpl]"musicThumbnailRenderer").getObject([CtLiteralImpl]"thumbnail").getArray([CtLiteralImpl]"thumbnails").getObject([CtLiteralImpl]0).getString([CtLiteralImpl]"url"));
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get thumbnail url", [CtVariableReadImpl]e);
                            }
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]name;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get name");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getUrl() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtInvocationImpl]YoutubeParsingHelper.getUrlFromNavigationEndpoint([CtInvocationImpl][CtVariableReadImpl]info.getObject([CtLiteralImpl]"doubleTapCommand"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]url != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]url.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]url;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get url");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]java.lang.String getUploaderName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name;
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ALBUMS)) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]2).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]name = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]1).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            }
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]name != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]name.isEmpty()))[CtBlockImpl]
                                [CtReturnImpl]return [CtVariableReadImpl]name;

                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get uploader name");
                        }

                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]long getStreamCount() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]searchType.equals([CtTypeAccessImpl]YoutubeSearchQueryHandlerFactory.MUSIC_ALBUMS))[CtBlockImpl]
                                [CtReturnImpl]return [CtFieldReadImpl]PlaylistExtractor.UNKNOWN_ITEMS;

                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String count = [CtInvocationImpl]YoutubeParsingHelper.getTextFromObject([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]info.getArray([CtLiteralImpl]"flexColumns").getObject([CtLiteralImpl]2).getObject([CtLiteralImpl]"musicResponsiveListItemFlexColumnRenderer").getObject([CtLiteralImpl]"text"));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]count != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]count.isEmpty())) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]count.contains([CtLiteralImpl]"100+")) [CtBlockImpl]{
                                    [CtReturnImpl]return [CtFieldReadImpl]PlaylistExtractor.MORE_THAN_100_ITEMS;
                                } else [CtBlockImpl]{
                                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.Utils.removeNonDigitCharacters([CtVariableReadImpl]count));
                                }
                            }
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get count");
                        }
                    });
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getNextPageUrlFrom([CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray continuations) throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException, [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ReCaptchaException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]continuations == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]"";

        [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject nextContinuationData = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]continuations.getObject([CtLiteralImpl]0).getObject([CtLiteralImpl]"nextContinuationData");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String continuation = [CtInvocationImpl][CtVariableReadImpl]nextContinuationData.getString([CtLiteralImpl]"continuation");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clickTrackingParams = [CtInvocationImpl][CtVariableReadImpl]nextContinuationData.getString([CtLiteralImpl]"clickTrackingParams");
        [CtIfImpl]if ([CtInvocationImpl]isMusicSearch()) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"https://music.youtube.com/youtubei/v1/search?ctoken=" + [CtVariableReadImpl]continuation) + [CtLiteralImpl]"&continuation=") + [CtVariableReadImpl]continuation) + [CtLiteralImpl]"&itct=") + [CtVariableReadImpl]clickTrackingParams) + [CtLiteralImpl]"&alt=json&key=") + [CtArrayReadImpl][CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper.getYoutubeMusicKeys()[[CtLiteralImpl]0];
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getUrl() + [CtLiteralImpl]"&pbj=1&ctoken=") + [CtVariableReadImpl]continuation) + [CtLiteralImpl]"&continuation=") + [CtVariableReadImpl]continuation) + [CtLiteralImpl]"&itct=") + [CtVariableReadImpl]clickTrackingParams;
        }
    }
}
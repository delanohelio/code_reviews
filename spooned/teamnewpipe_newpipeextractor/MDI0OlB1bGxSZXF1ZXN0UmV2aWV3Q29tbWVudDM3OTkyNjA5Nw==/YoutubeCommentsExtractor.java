[CompilationUnitImpl][CtPackageDeclarationImpl]package org.schabi.newpipe.extractor.services.youtube.extractors;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector;
[CtUnresolvedImport]import com.grack.nanojson.JsonObject;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.utils.Parser;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.StreamingService;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
[CtUnresolvedImport]import com.grack.nanojson.JsonArray;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.utils.JsonUtils;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ParsingException;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.exceptions.ExtractionException;
[CtImportImpl]import java.net.URLEncoder;
[CtImportImpl]import java.util.regex.Pattern;
[CtImportImpl]import static java.util.Collections.singletonList;
[CtImportImpl]import java.io.UnsupportedEncodingException;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.downloader.Downloader;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.comments.CommentsInfoItem;
[CtUnresolvedImport]import com.grack.nanojson.JsonParser;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.comments.CommentsExtractor;
[CtUnresolvedImport]import org.schabi.newpipe.extractor.downloader.Response;
[CtClassImpl]public class YoutubeCommentsExtractor extends [CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsExtractor {
    [CtFieldImpl][CtCommentImpl]// using the mobile site for comments because it loads faster and uses get requests instead of post
    private static final [CtTypeReferenceImpl]java.lang.String USER_AGENT = [CtLiteralImpl]"Mozilla/5.0 (Android 8.1.0; Mobile; rv:62.0) Gecko/62.0 Firefox/62.0";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.regex.Pattern YT_CLIENT_NAME_PATTERN = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"INNERTUBE_CONTEXT_CLIENT_NAME\\\":(.*?)[,}]");

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String ytClientVersion;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String ytClientName;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String title;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<[CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItem> initPage;

    [CtConstructorImpl]public YoutubeCommentsExtractor([CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.StreamingService service, [CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.linkhandler.ListLinkHandler uiHandler) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]service, [CtVariableReadImpl]uiHandler);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<[CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItem> getInitialPage() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// initial page does not load any comments but is required to get comments token
        [CtSuperAccessImpl]super.fetchPage();
        [CtReturnImpl]return [CtFieldReadImpl]initPage;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getNextPageUrl() throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// initial page does not load any comments but is required to get comments token
        [CtSuperAccessImpl]super.fetchPage();
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]initPage.getNextPageUrl();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getNextPageUrl([CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject ajaxJson) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray arr;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]arr = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getArray([CtVariableReadImpl]ajaxJson, [CtLiteralImpl]"response.continuationContents.commentSectionContinuation.continuations");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]arr.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String continuation;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]continuation = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getString([CtInvocationImpl][CtVariableReadImpl]arr.getObject([CtLiteralImpl]0), [CtLiteralImpl]"nextContinuationData.continuation");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtReturnImpl]return [CtInvocationImpl]getNextPageUrl([CtVariableReadImpl]continuation);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getNextPageUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String continuation) throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"action_get_comments", [CtLiteralImpl]"1");
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"pbj", [CtLiteralImpl]"1");
        [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"ctoken", [CtVariableReadImpl]continuation);
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"https://m.youtube.com/watch_comment?" + [CtInvocationImpl]getDataString([CtVariableReadImpl]params);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.UnsupportedEncodingException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not get next page url", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<[CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItem> getPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageUrl) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]pageUrl == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]pageUrl.isEmpty()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Page url is empty or null"));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String ajaxResponse = [CtInvocationImpl]makeAjaxRequest([CtVariableReadImpl]pageUrl);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject ajaxJson;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ajaxJson = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonParser.array().from([CtVariableReadImpl]ajaxResponse).getObject([CtLiteralImpl]1);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"Could not parse json data for comments", [CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector collector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector([CtInvocationImpl]getServiceId());
        [CtInvocationImpl]collectCommentsFrom([CtVariableReadImpl]collector, [CtVariableReadImpl]ajaxJson);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.InfoItemsPage<>([CtVariableReadImpl]collector, [CtInvocationImpl]getNextPageUrl([CtVariableReadImpl]ajaxJson));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void collectCommentsFrom([CtParameterImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector collector, [CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject ajaxJson) throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray contents;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]contents = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getArray([CtVariableReadImpl]ajaxJson, [CtLiteralImpl]"response.continuationContents.commentSectionContinuation.items");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// no comments
            return;
        }
        [CtInvocationImpl]fetchTitle([CtVariableReadImpl]contents);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> comments;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]comments = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getValues([CtVariableReadImpl]contents, [CtLiteralImpl]"commentThreadRenderer.comment.commentRenderer");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException([CtLiteralImpl]"unable to get parse youtube comments", [CtVariableReadImpl]e);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object c : [CtVariableReadImpl]comments) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonObject) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor extractor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsInfoItemExtractor([CtVariableReadImpl](([CtTypeReferenceImpl]com.grack.nanojson.JsonObject) (c)), [CtInvocationImpl]getUrl(), [CtInvocationImpl]getTimeAgoParser());
                [CtInvocationImpl][CtVariableReadImpl]collector.commit([CtVariableReadImpl]extractor);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void fetchTitle([CtParameterImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray contents) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtFieldReadImpl]title) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]title = [CtInvocationImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor.getYoutubeText([CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getObject([CtInvocationImpl][CtVariableReadImpl]contents.getObject([CtLiteralImpl]0), [CtLiteralImpl]"commentThreadRenderer.commentTargetTitle"));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]title = [CtLiteralImpl]"Youtube Comments";
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onFetchPage([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]org.schabi.newpipe.extractor.downloader.Downloader downloader) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ExtractionException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> requestHeaders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"User-Agent", [CtInvocationImpl]java.util.Collections.singletonList([CtFieldReadImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor.USER_AGENT));
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.extractor.downloader.Response response = [CtInvocationImpl][CtVariableReadImpl]downloader.get([CtInvocationImpl]getUrl(), [CtVariableReadImpl]requestHeaders, [CtInvocationImpl]getExtractorLocalization());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String responseBody = [CtInvocationImpl][CtVariableReadImpl]response.responseBody();
        [CtAssignmentImpl][CtFieldWriteImpl]ytClientVersion = [CtInvocationImpl]findValue([CtVariableReadImpl]responseBody, [CtLiteralImpl]"INNERTUBE_CONTEXT_CLIENT_VERSION\":\"", [CtLiteralImpl]"\"");
        [CtAssignmentImpl][CtFieldWriteImpl]ytClientName = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.Parser.matchGroup1([CtFieldReadImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor.YT_CLIENT_NAME_PATTERN, [CtVariableReadImpl]responseBody);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentsTokenInside = [CtInvocationImpl]findValue([CtVariableReadImpl]responseBody, [CtLiteralImpl]"commentSectionRenderer", [CtLiteralImpl]"}");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String commentsToken = [CtInvocationImpl]findValue([CtVariableReadImpl]commentsTokenInside, [CtLiteralImpl]"continuation\":\"", [CtLiteralImpl]"\"");
        [CtAssignmentImpl][CtFieldWriteImpl]initPage = [CtInvocationImpl]getPage([CtInvocationImpl]getNextPageUrl([CtVariableReadImpl]commentsToken));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getName() throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]title;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String makeAjaxRequest([CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteUrl) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ReCaptchaException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> requestHeaders = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"Accept", [CtInvocationImpl]java.util.Collections.singletonList([CtLiteralImpl]"*/*"));
        [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"User-Agent", [CtInvocationImpl]java.util.Collections.singletonList([CtFieldReadImpl]org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor.USER_AGENT));
        [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"X-YouTube-Client-Version", [CtInvocationImpl]java.util.Collections.singletonList([CtFieldReadImpl]ytClientVersion));
        [CtInvocationImpl][CtVariableReadImpl]requestHeaders.put([CtLiteralImpl]"X-YouTube-Client-Name", [CtInvocationImpl]java.util.Collections.singletonList([CtFieldReadImpl]ytClientName));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getDownloader().get([CtVariableReadImpl]siteUrl, [CtVariableReadImpl]requestHeaders, [CtInvocationImpl]getExtractorLocalization()).responseBody();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDataString([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> params) throws [CtTypeReferenceImpl]java.io.UnsupportedEncodingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean first = [CtLiteralImpl]true;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> entry : [CtInvocationImpl][CtVariableReadImpl]params.entrySet()) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]first)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]first = [CtLiteralImpl]false;
            else[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"&");

            [CtInvocationImpl][CtVariableReadImpl]result.append([CtInvocationImpl][CtTypeAccessImpl]java.net.URLEncoder.encode([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtLiteralImpl]"UTF-8"));
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtLiteralImpl]"=");
            [CtInvocationImpl][CtVariableReadImpl]result.append([CtInvocationImpl][CtTypeAccessImpl]java.net.URLEncoder.encode([CtInvocationImpl][CtVariableReadImpl]entry.getValue(), [CtLiteralImpl]"UTF-8"));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]result.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String findValue([CtParameterImpl][CtTypeReferenceImpl]java.lang.String doc, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String start, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String end) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int beginIndex = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doc.indexOf([CtVariableReadImpl]start) + [CtInvocationImpl][CtVariableReadImpl]start.length();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int endIndex = [CtInvocationImpl][CtVariableReadImpl]doc.indexOf([CtVariableReadImpl]end, [CtVariableReadImpl]beginIndex);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]doc.substring([CtVariableReadImpl]beginIndex, [CtVariableReadImpl]endIndex);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String getYoutubeText([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]com.grack.nanojson.JsonObject object) throws [CtTypeReferenceImpl]org.schabi.newpipe.extractor.exceptions.ParsingException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getString([CtVariableReadImpl]object, [CtLiteralImpl]"simpleText");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e1) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.grack.nanojson.JsonArray arr = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getArray([CtVariableReadImpl]object, [CtLiteralImpl]"runs");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String result = [CtLiteralImpl]"";
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]arr.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl][CtVariableReadImpl]result + [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.extractor.utils.JsonUtils.getString([CtInvocationImpl][CtVariableReadImpl]arr.getObject([CtVariableReadImpl]i), [CtLiteralImpl]"text");
                }
                [CtReturnImpl]return [CtVariableReadImpl]result;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e2) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]"";
            }
        }
    }
}
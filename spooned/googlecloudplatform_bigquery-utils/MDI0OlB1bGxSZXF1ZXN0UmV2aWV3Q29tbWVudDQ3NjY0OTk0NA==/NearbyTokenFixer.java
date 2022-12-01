[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.cloud.bigquery.utils.queryfixer.fixer;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.errors.ExpectKeywordButGotOthersError;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.FixResult;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.IToken;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.Position;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.tokenizer.QueryTokenProcessor;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.FixOption;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.util.StringUtil;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.apache.commons.lang3.tuple.Pair;
[CtClassImpl][CtJavaDocImpl]/**
 * A class to fix general syntax errors. Usually, a general syntax error looks like {@link ExpectKeywordButGotOthersError} but expects "end of input". It basically means there may be
 * multiple expected tokens but the error message cannot recognize them, so the fixer will try to
 * see if the tokens near the error position are similar to any keywords. If yes, then replace the
 * token to the similar keyword anc check if the error is eliminated from the query.
 *
 * <p>Here is an example with an input query:
 *
 * <pre>
 *     SELECT status FORM `bigquery-public-data.austin_311.311_request` LIMIT 10
 * </pre>
 *
 * It causes an error "Syntax error: Expected end of input but got identifier `...` at [1:20]". The
 * fixer will look around this position and find FORM looks like a keyword FROM and convert it to
 * FROM, leading to a new query
 *
 * <pre>
 *     SELECT status FROM `bigquery-public-data.austin_311.311_request` LIMIT 10
 * </pre>
 */
public class NearbyTokenFixer implements [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.fixer.IFixer {
    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String query;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.errors.ExpectKeywordButGotOthersError err;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.tokenizer.QueryTokenProcessor queryTokenProcessor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> keywords;

    [CtFieldImpl][CtCommentImpl]// TODO: it could be configured by users in future.
    private static final [CtTypeReferenceImpl]double SIMILARITY_THRESHOLD = [CtLiteralImpl]0.5;

    [CtConstructorImpl]public NearbyTokenFixer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String query, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.errors.ExpectKeywordButGotOthersError err, [CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.tokenizer.QueryTokenProcessor queryTokenProcessor) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.query = [CtVariableReadImpl]query;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.err = [CtVariableReadImpl]err;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.queryTokenProcessor = [CtVariableReadImpl]queryTokenProcessor;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.keywords = [CtInvocationImpl]getAllKeywords();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fix() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.Position errorPosition = [CtInvocationImpl][CtFieldReadImpl]err.getErrorPosition();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.lang3.tuple.Pair<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.IToken, [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.IToken> tokens = [CtInvocationImpl][CtFieldReadImpl]queryTokenProcessor.getNearbyTokens([CtFieldReadImpl]query, [CtInvocationImpl][CtVariableReadImpl]errorPosition.getRow(), [CtInvocationImpl][CtVariableReadImpl]errorPosition.getColumn());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.util.StringUtil.SimilarStrings rightTokenSimilarStrings = [CtInvocationImpl]findSimilarKeywords([CtInvocationImpl][CtVariableReadImpl]tokens.getRight());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.util.StringUtil.SimilarStrings leftTokenSimilarStrings = [CtInvocationImpl]findSimilarKeywords([CtInvocationImpl][CtVariableReadImpl]tokens.getLeft());
        [CtIfImpl][CtCommentImpl]// The overall logic below is that if left token has a more similar (measured by
        [CtCommentImpl]// edit distance) keyword, then only choose the fixes on left tokens. If right token
        [CtCommentImpl]// has more similar keywords, choose the fixes on right tokens. If both of them have
        [CtCommentImpl]// the similar keywords with the same edit distance, then choose both of them.
        [CtCommentImpl]// Otherwise, if neither of them have any similar keywords, then return FAILURE FixResult.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]leftTokenSimilarStrings.isEmpty() && [CtInvocationImpl][CtVariableReadImpl]rightTokenSimilarStrings.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.failure([CtFieldReadImpl]query, [CtFieldReadImpl]err);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rightTokenSimilarStrings.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options = [CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getLeft(), [CtVariableReadImpl]leftTokenSimilarStrings);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String approach = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Replace `%s`.", [CtInvocationImpl][CtVariableReadImpl]tokens.getLeft());
            [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* isConfident= */
            [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.success([CtFieldReadImpl]query, [CtVariableReadImpl]approach, [CtVariableReadImpl]options, [CtFieldReadImpl]err, [CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]leftTokenSimilarStrings.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options = [CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getRight(), [CtVariableReadImpl]rightTokenSimilarStrings);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String approach = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Replace `%s`.", [CtInvocationImpl][CtVariableReadImpl]tokens.getRight());
            [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* isConfident= */
            [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.success([CtFieldReadImpl]query, [CtVariableReadImpl]approach, [CtVariableReadImpl]options, [CtFieldReadImpl]err, [CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rightTokenSimilarStrings.getDistance() > [CtInvocationImpl][CtVariableReadImpl]leftTokenSimilarStrings.getDistance()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options = [CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getLeft(), [CtVariableReadImpl]leftTokenSimilarStrings);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String approach = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Replace `%s`.", [CtInvocationImpl][CtVariableReadImpl]tokens.getLeft());
            [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* isConfident= */
            [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.success([CtFieldReadImpl]query, [CtVariableReadImpl]approach, [CtVariableReadImpl]options, [CtFieldReadImpl]err, [CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]rightTokenSimilarStrings.getDistance() < [CtInvocationImpl][CtVariableReadImpl]leftTokenSimilarStrings.getDistance()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options = [CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getRight(), [CtVariableReadImpl]rightTokenSimilarStrings);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String approach = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Replace `%s`.", [CtInvocationImpl][CtVariableReadImpl]tokens.getRight());
            [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* isConfident= */
            [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.success([CtFieldReadImpl]query, [CtVariableReadImpl]approach, [CtVariableReadImpl]options, [CtFieldReadImpl]err, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options = [CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getRight(), [CtVariableReadImpl]rightTokenSimilarStrings);
        [CtInvocationImpl][CtVariableReadImpl]options.addAll([CtInvocationImpl]toFixOptions([CtInvocationImpl][CtVariableReadImpl]tokens.getLeft(), [CtVariableReadImpl]leftTokenSimilarStrings));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String approach = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Replace `%s` or `%s`.", [CtInvocationImpl][CtVariableReadImpl]tokens.getLeft(), [CtInvocationImpl][CtVariableReadImpl]tokens.getRight());
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]/* isConfident= */
        [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult.success([CtFieldReadImpl]query, [CtVariableReadImpl]approach, [CtVariableReadImpl]options, [CtFieldReadImpl]err, [CtLiteralImpl]false);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAllKeywords() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// TODO: needs to implement actual log when the ZetaSQL Helper is updated.
        return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtLiteralImpl]"SELECT", [CtLiteralImpl]"FROM");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]StringUtil.SimilarStrings findSimilarKeywords([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.IToken token) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]token == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]StringUtil.SimilarStrings.empty();
        }
        [CtLocalVariableImpl][CtCommentImpl]// TODO: only consider the identifier token. This logic will be implemented when #155 is merged.
        [CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.util.StringUtil.SimilarStrings similarStrings = [CtInvocationImpl][CtCommentImpl]/* caseSensitive= */
        [CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.util.StringUtil.findSimilarWords([CtFieldReadImpl]keywords, [CtInvocationImpl][CtVariableReadImpl]token.getImage(), [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int tokenSize = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]token.getImage().length();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1.0 * [CtInvocationImpl][CtVariableReadImpl]similarStrings.getDistance()) / [CtVariableReadImpl]tokenSize) > [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.fixer.NearbyTokenFixer.SIMILARITY_THRESHOLD) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]StringUtil.SimilarStrings.empty();
        }
        [CtReturnImpl]return [CtVariableReadImpl]similarStrings;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> toFixOptions([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.IToken token, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.util.StringUtil.SimilarStrings similarStrings) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]similarStrings.getStrings().stream().map([CtLambdaImpl]([CtParameterImpl] word) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String action = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s => %s", [CtInvocationImpl][CtVariableReadImpl]token.getImage(), [CtVariableReadImpl]word);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String modifiedQuery = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]queryTokenProcessor.replaceToken([CtFieldReadImpl][CtFieldReferenceImpl]query, [CtVariableReadImpl]token, [CtVariableReadImpl]word);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption.of([CtVariableReadImpl]action, [CtVariableReadImpl]modifiedQuery);
        }).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }
}
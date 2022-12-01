[CompilationUnitImpl][CtPackageDeclarationImpl]package com.google.cloud.bigquery.utils.queryfixer;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.FixResult;
[CtUnresolvedImport]import org.apache.commons.cli.*;
[CtUnresolvedImport]import com.google.cloud.bigquery.utils.queryfixer.entity.FixOption;
[CtUnresolvedImport]import com.google.gson.Gson;
[CtImportImpl]import java.util.List;
[CtImportImpl]import com.google.cloud.bigquery.*;
[CtUnresolvedImport]import com.google.gson.GsonBuilder;
[CtUnresolvedImport]import org.apache.commons.cli.Option;
[CtClassImpl]public class QueryFixerMain {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CREDENTIAL_SHORTCUT = [CtLiteralImpl]"c";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CREDENTIAL = [CtLiteralImpl]"credential";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PROJECT_ID_SHORTCUT = [CtLiteralImpl]"p";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PROJECT_ID = [CtLiteralImpl]"project-id";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OUTPUT_SHORTCUT = [CtLiteralImpl]"o";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OUTPUT = [CtLiteralImpl]"output";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OUTPUT_MODE_JSON = [CtLiteralImpl]"json";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OUTPUT_MODE_NATURAL = [CtLiteralImpl]"natural";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT_SHORTCUT = [CtLiteralImpl]"i";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT = [CtLiteralImpl]"interact";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT_MODE_NONE = [CtLiteralImpl]"none";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT_MODE_GUIDE = [CtLiteralImpl]"guide";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT_MODE_ALL = [CtLiteralImpl]"all";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INTERACT_MODE_FULL = [CtLiteralImpl]"full";

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.CommandLine readFlags([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] args) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Options options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Options();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.commons.cli.Option option = [CtConstructorCallImpl][CtCommentImpl]/* opt= */
        [CtCommentImpl]/* long-opt= */
        [CtCommentImpl]/* hasArg= */
        [CtCommentImpl]/* description= */
        new [CtTypeReferenceImpl]org.apache.commons.cli.Option([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.CREDENTIAL_SHORTCUT, [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.CREDENTIAL, [CtLiteralImpl]true, [CtLiteralImpl]"The path to the credential file of the service account connecting to BigQuery. Otherwise, the default application-login credential will be used.");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtVariableReadImpl]option);
        [CtAssignmentImpl][CtVariableWriteImpl]option = [CtConstructorCallImpl][CtCommentImpl]/* opt= */
        [CtCommentImpl]/* long-opt= */
        [CtCommentImpl]/* hasArg= */
        [CtCommentImpl]/* description= */
        new [CtTypeReferenceImpl]org.apache.commons.cli.Option([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.PROJECT_ID_SHORTCUT, [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.PROJECT_ID, [CtLiteralImpl]true, [CtLiteralImpl]"The ID of project where queries will be performed. This field is required if the project is not specified in credential");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtVariableReadImpl]option);
        [CtAssignmentImpl][CtVariableWriteImpl]option = [CtConstructorCallImpl][CtCommentImpl]/* opt= */
        [CtCommentImpl]/* long-opt= */
        [CtCommentImpl]/* hasArg= */
        [CtCommentImpl]/* description= */
        new [CtTypeReferenceImpl]org.apache.commons.cli.Option([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.OUTPUT_SHORTCUT, [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.OUTPUT, [CtLiteralImpl]true, [CtLiteralImpl]"The format to output fix results. The available formats are \"natural\" (default) and \"json\"");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtVariableReadImpl]option);
        [CtAssignmentImpl][CtVariableWriteImpl]option = [CtConstructorCallImpl][CtCommentImpl]/* opt= */
        [CtCommentImpl]/* long-opt= */
        [CtCommentImpl]/* hasArg= */
        [CtCommentImpl]/* description= */
        new [CtTypeReferenceImpl]org.apache.commons.cli.Option([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_SHORTCUT, [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT, [CtLiteralImpl]true, [CtLiteralImpl]"Interactive Mode. The available mode are \"none\" (default), \"guide\" and \"all/full\"");
        [CtInvocationImpl][CtVariableReadImpl]options.addOption([CtVariableReadImpl]option);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]args.length == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Please provide arguments.");
            [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printHelpAndExit([CtVariableReadImpl]options);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]CommandLineParser parser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]DefaultParser();
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]parser.parse([CtVariableReadImpl]options, [CtVariableReadImpl]args);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]ParseException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
            [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printHelpAndExit([CtVariableReadImpl]options);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void printHelpAndExit([CtParameterImpl][CtTypeReferenceImpl]Options options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]HelpFormatter formatter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]HelpFormatter();
        [CtInvocationImpl][CtVariableReadImpl]formatter.printHelp([CtLiteralImpl]"-opt <value> --long-opt <value> \"query\"", [CtVariableReadImpl]options);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]1);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void main([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] args) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]CommandLine cmd = [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.readFlags([CtVariableReadImpl]args);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String credentialPath = [CtInvocationImpl][CtVariableReadImpl]cmd.getOptionValue([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.CREDENTIAL);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String projectId = [CtInvocationImpl][CtVariableReadImpl]cmd.getOptionValue([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.PROJECT_ID);
        [CtLocalVariableImpl][CtTypeReferenceImpl]BigQueryOptions bigQueryOptions = [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.buildBigQueryOptions([CtVariableReadImpl]credentialPath, [CtVariableReadImpl]projectId);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getArgList().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// In CLI mode, all the instructions are output by print functions, because logger outputs
            [CtCommentImpl]// extract info (time, code position) that distracts users.
            [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Please provide the query as an argument, enclosed by double quote. Use --help for instruction.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String query = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cmd.getArgList().get([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Input query: " + [CtVariableReadImpl]query);
        [CtLocalVariableImpl][CtTypeReferenceImpl]AutomaticQueryFixer queryFixer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]AutomaticQueryFixer([CtVariableReadImpl]bigQueryOptions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String interactMode = [CtInvocationImpl][CtVariableReadImpl]cmd.getOptionValue([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]interactMode == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]interactMode = [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_MODE_NONE;
        }
        [CtSwitchImpl]switch ([CtVariableReadImpl]interactMode) {
            [CtCaseImpl]case [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_MODE_NONE :
                [CtLocalVariableImpl][CtCommentImpl]// todo: Implement Non-interactive mode
                [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fixResult = [CtInvocationImpl][CtVariableReadImpl]queryFixer.fix([CtVariableReadImpl]query);
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fixResult.getOptions().isEmpty()) [CtBlockImpl]{
                    [CtReturnImpl]return;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newQuery = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]fixResult.getOptions().get([CtLiteralImpl]0).getFixedQuery();
                [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printQueryResult([CtVariableReadImpl]newQuery, [CtVariableReadImpl]bigQueryOptions);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_MODE_GUIDE :
                [CtReturnImpl][CtCommentImpl]// todo: Implement guide mode
                return;
            [CtCaseImpl]case [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_MODE_ALL :
            [CtCaseImpl]case [CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.INTERACT_MODE_FULL :
                [CtAssignmentImpl][CtVariableWriteImpl]fixResult = [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.fixQueryInFullInteractMode([CtVariableReadImpl]queryFixer, [CtVariableReadImpl]query);
                [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printFixResult([CtVariableReadImpl]fixResult, [CtInvocationImpl][CtVariableReadImpl]cmd.getOptionValue([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.OUTPUT));
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Interact Mode (-i) is incorrect. Use --help for usage.");
                [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]1);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.BigQueryOptions buildBigQueryOptions([CtParameterImpl][CtTypeReferenceImpl]java.lang.String credentialPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String projectId) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]credentialPath == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]BigQueryOptions.newBuilder().setProjectId([CtVariableReadImpl]projectId).build();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// TODO: should support this in near future.
            [CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"customized credential path is not supported");
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]1);
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fixQueryInFullInteractMode([CtParameterImpl][CtTypeReferenceImpl]AutomaticQueryFixer queryFixer, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String query) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]queryFixer.fix([CtVariableReadImpl]query);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printFixResult([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fixResult, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String outputFormat) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]outputFormat == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]outputFormat.equalsIgnoreCase([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.OUTPUT_MODE_NATURAL)) [CtBlockImpl]{
            [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printFixResultInCommandLine([CtVariableReadImpl]fixResult);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]outputFormat.equalsIgnoreCase([CtFieldReadImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.OUTPUT_MODE_JSON)) [CtBlockImpl]{
            [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printFixResultAsJson([CtVariableReadImpl]fixResult);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Output Mode (-o) is incorrect. Use --help for usage.");
            [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.exit([CtLiteralImpl]1);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printFixResultAsJson([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fixResult) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.Gson gson = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.GsonBuilder().setPrettyPrinting().serializeNulls().create();
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtVariableReadImpl]gson.toJson([CtVariableReadImpl]fixResult));
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printFixResultInCommandLine([CtParameterImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixResult fixResult) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fixResult.getStatus() == [CtFieldReadImpl]FixResult.Status.NO_ERROR) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"The input query is valid. No errors to fix.");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"The query has an error: " + [CtInvocationImpl][CtVariableReadImpl]fixResult.getError());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]fixResult.getStatus() == [CtFieldReadImpl]FixResult.Status.FAILURE) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtLiteralImpl]"Failed to fix the input query.");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"It can be fixed by the approach: " + [CtInvocationImpl][CtVariableReadImpl]fixResult.getApproach());
        [CtInvocationImpl]com.google.cloud.bigquery.utils.queryfixer.QueryFixerMain.printFixOptions([CtInvocationImpl][CtVariableReadImpl]fixResult.getOptions());
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printFixOptions([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption> options) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.google.cloud.bigquery.utils.queryfixer.entity.FixOption option : [CtVariableReadImpl]options) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%d. Option: %s", [CtUnaryOperatorImpl][CtVariableWriteImpl]count++, [CtInvocationImpl][CtVariableReadImpl]option.getDescription()));
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"   Fixed query: %s", [CtInvocationImpl][CtVariableReadImpl]option.getFixedQuery()));
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println();
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void printQueryResult([CtParameterImpl][CtTypeReferenceImpl]java.lang.String query, [CtParameterImpl][CtTypeReferenceImpl]BigQueryOptions options) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]BigQuery bigQuery = [CtInvocationImpl][CtVariableReadImpl]options.getService();
        [CtLocalVariableImpl][CtTypeReferenceImpl]QueryJobConfiguration queryConfig = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]QueryJobConfiguration.newBuilder([CtVariableReadImpl]query).build();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int maxCount = [CtLiteralImpl]10;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtLiteralImpl]0;
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtInvocationImpl][CtLiteralImpl]"=".repeat([CtLiteralImpl]20));
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Now query: " + [CtVariableReadImpl]query);
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.printf([CtLiteralImpl]"Only %d rows are printed\n", [CtVariableReadImpl]maxCount);
            [CtLocalVariableImpl][CtTypeReferenceImpl]TableResult result = [CtInvocationImpl][CtVariableReadImpl]bigQuery.query([CtVariableReadImpl]queryConfig);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Field field : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.getSchema().getFields()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.printf([CtLiteralImpl]"%s,", [CtInvocationImpl][CtVariableReadImpl]field.getName());
            }
            [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.print([CtLiteralImpl]"\n");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]FieldValueList row : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bigQuery.query([CtVariableReadImpl]queryConfig).iterateAll()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]FieldValue val : [CtVariableReadImpl]row) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.printf([CtLiteralImpl]"%s,", [CtInvocationImpl][CtVariableReadImpl]val.getValue());
                }
                [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.print([CtLiteralImpl]"\n");
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](++[CtVariableWriteImpl]count) >= [CtVariableReadImpl]maxCount) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignored) [CtBlockImpl]{
        }
    }
}
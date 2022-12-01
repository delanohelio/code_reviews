[CompilationUnitImpl][CtPackageDeclarationImpl]package com.hedera.mirror.importer.parser.record;
[CtImportImpl]import java.util.stream.Collectors;
[CtImportImpl]import javax.inject.Named;
[CtImportImpl]import java.nio.file.Path;
[CtImportImpl]import org.apache.commons.io.FileUtils;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import com.hedera.mirror.importer.parser.domain.StreamFileData;
[CtImportImpl]import java.io.FileNotFoundException;
[CtUnresolvedImport]import com.hedera.mirror.importer.parser.FilePoller;
[CtUnresolvedImport]import org.springframework.scheduling.annotation.Scheduled;
[CtUnresolvedImport]import com.hedera.mirror.importer.exception.DuplicateFileException;
[CtImportImpl]import java.io.FileInputStream;
[CtUnresolvedImport]import lombok.extern.log4j.Log4j2;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import lombok.AllArgsConstructor;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import com.hedera.mirror.importer.util.ShutdownHelper;
[CtUnresolvedImport]import com.hedera.mirror.importer.util.Utility;
[CtImportImpl]import java.util.Collections;
[CtClassImpl][CtAnnotationImpl]@lombok.extern.log4j.Log4j2
[CtAnnotationImpl]@javax.inject.Named
[CtAnnotationImpl]@com.hedera.mirror.importer.parser.record.ConditionalOnRecordParser
[CtAnnotationImpl]@lombok.AllArgsConstructor
public class RecordFilePoller implements [CtTypeReferenceImpl]com.hedera.mirror.importer.parser.FilePoller {
    [CtFieldImpl]private final [CtTypeReferenceImpl]com.hedera.mirror.importer.parser.record.RecordParserProperties parserProperties;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.hedera.mirror.importer.parser.record.RecordFileParser recordFileParser;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.springframework.scheduling.annotation.Scheduled(fixedRateString = [CtLiteralImpl]"${hedera.mirror.importer.parser.record.frequency:100}")
    public [CtTypeReferenceImpl]void poll() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.util.ShutdownHelper.isStopping()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.file.Path path = [CtInvocationImpl][CtFieldReadImpl]parserProperties.getValidPath();
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Parsing record files from {}", [CtVariableReadImpl]path);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File file = [CtInvocationImpl][CtVariableReadImpl]path.toFile();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]file.isDirectory()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] files = [CtInvocationImpl][CtVariableReadImpl]file.list();
                [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.sort([CtVariableReadImpl]files);[CtCommentImpl]// sorted by name (timestamp)

                [CtLocalVariableImpl][CtCommentImpl]// add directory prefix to get full path
                [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> fullPaths = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]files).stream().map([CtLambdaImpl]([CtParameterImpl]java.lang.String s) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]file + [CtLiteralImpl]"/") + [CtVariableReadImpl]s).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]fullPaths != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]fullPaths.size() != [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]log.trace([CtLiteralImpl]"Processing record files: {}", [CtVariableReadImpl]fullPaths);
                    [CtInvocationImpl]loadRecordFiles([CtVariableReadImpl]fullPaths);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"No files to parse");
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Input parameter is not a folder: {}", [CtVariableReadImpl]path);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Error parsing files", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * read and parse a list of record files
     *
     * @throws Exception
     */
    private [CtTypeReferenceImpl]void loadRecordFiles([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> filePaths) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.sort([CtVariableReadImpl]filePaths);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String filePath : [CtVariableReadImpl]filePaths) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.util.ShutdownHelper.isStopping()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File file = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtVariableReadImpl]filePath);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream fileInputStream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtVariableReadImpl]file)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]recordFileParser.parse([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hedera.mirror.importer.parser.domain.StreamFileData([CtVariableReadImpl]filePath, [CtVariableReadImpl]fileInputStream));
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]parserProperties.isKeepFiles()) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.hedera.mirror.importer.util.Utility.archiveFile([CtVariableReadImpl]file, [CtInvocationImpl][CtFieldReadImpl]parserProperties.getParsedPath());
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.io.FileUtils.deleteQuietly([CtVariableReadImpl]file);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.FileNotFoundException e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.warn([CtLiteralImpl]"File does not exist {}", [CtVariableReadImpl]filePath);
                [CtReturnImpl]return;
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.error([CtLiteralImpl]"Error parsing file {}", [CtVariableReadImpl]filePath, [CtVariableReadImpl]e);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.hedera.mirror.importer.exception.DuplicateFileException)) [CtBlockImpl]{
                    [CtReturnImpl][CtCommentImpl]// if DuplicateFileException, continue with other
                    [CtCommentImpl]// files
                    return;
                }
            }
        }
    }
}
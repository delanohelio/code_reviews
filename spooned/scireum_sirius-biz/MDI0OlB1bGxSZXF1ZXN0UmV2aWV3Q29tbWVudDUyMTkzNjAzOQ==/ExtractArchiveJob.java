[CompilationUnitImpl][CtCommentImpl]/* Made with all the love in the world
by scireum in Remshalden, Germany

Copyright by scireum GmbH
http://www.scireum.de - info@scireum.de
 */
[CtPackageDeclarationImpl]package sirius.biz.storage.layer3;
[CtUnresolvedImport]import sirius.kernel.commons.Strings;
[CtUnresolvedImport]import sirius.kernel.di.std.Part;
[CtUnresolvedImport]import sirius.biz.storage.layer1.FileHandle;
[CtUnresolvedImport]import sirius.kernel.commons.Watch;
[CtUnresolvedImport]import sirius.biz.process.PersistencePeriod;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import sirius.biz.jobs.params.BooleanParameter;
[CtUnresolvedImport]import sirius.kernel.async.TaskContext;
[CtUnresolvedImport]import sirius.kernel.commons.Files;
[CtUnresolvedImport]import sirius.kernel.di.std.Register;
[CtUnresolvedImport]import sirius.biz.jobs.batch.SimpleBatchProcessJobFactory;
[CtUnresolvedImport]import sirius.kernel.nls.NLS;
[CtImportImpl]import java.util.function.Consumer;
[CtUnresolvedImport]import sirius.biz.util.ExtractedFile;
[CtUnresolvedImport]import sirius.biz.process.logs.ProcessLog;
[CtUnresolvedImport]import sirius.web.http.QueryString;
[CtUnresolvedImport]import sirius.biz.util.ArchiveExtractor;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import sirius.biz.process.ProcessContext;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import sirius.biz.jobs.params.Parameter;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtUnresolvedImport]import sirius.biz.jobs.params.EnumParameter;
[CtClassImpl][CtJavaDocImpl]/**
 * Provides a job able to extract archives from the {@link VirtualFileSystem}.
 * <p>
 * This uses the {@link ArchiveExtractor} so depending if 7-ZIP is enabled this supports either a whole bunch
 * of formats (rar, 7z, tar etc.) or "just" ZIP files using the Java API.
 */
[CtAnnotationImpl]@sirius.kernel.di.std.Register
public class ExtractArchiveJob extends [CtTypeReferenceImpl]sirius.biz.jobs.batch.SimpleBatchProcessJobFactory {
    [CtFieldImpl][CtAnnotationImpl]@sirius.kernel.di.std.Part
    private [CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFileSystem vfs;

    [CtFieldImpl][CtAnnotationImpl]@sirius.kernel.di.std.Part
    private [CtTypeReferenceImpl]sirius.biz.util.ArchiveExtractor extractor;

    [CtFieldImpl]private final [CtTypeReferenceImpl]sirius.biz.jobs.params.Parameter<[CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile> sourceParamter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]sirius.biz.jobs.params.Parameter<[CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile> destinationParameter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]sirius.biz.jobs.params.Parameter<[CtTypeReferenceImpl][CtTypeReferenceImpl]sirius.biz.util.ArchiveExtractor.OverrideMode> overwriteExistingFilesParameter;

    [CtFieldImpl]private final [CtTypeReferenceImpl]sirius.biz.jobs.params.Parameter<[CtTypeReferenceImpl]java.lang.Boolean> deleteArchiveParameter;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Creates the job factory so that it can be invoked by the framework.
     * <p>
     * This constructor is primarily used to make the parameter instantiation more readable.
     */
    public ExtractArchiveJob() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sourceParamter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]sirius.biz.storage.layer3.FileParameter([CtLiteralImpl]"source", [CtLiteralImpl]"$ExtractArchiveJob.sourceParameter").withAcceptedExtensionsList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]extractor.getSupportedFileExtensions())).withDescription([CtLiteralImpl]"$ExtractArchiveJob.sourceParameter.help").markRequired().build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.destinationParameter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]sirius.biz.storage.layer3.DirectoryParameter([CtLiteralImpl]"destination", [CtLiteralImpl]"$ExtractArchiveJob.destinationParameter").withDescription([CtLiteralImpl]"$ExtractArchiveJob.destinationParameter.help").markRequired().build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.overwriteExistingFilesParameter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]sirius.biz.jobs.params.EnumParameter<>([CtLiteralImpl]"overwriteExistingFiles", [CtLiteralImpl]"$ExtractArchiveJob.overwriteExistingFilesParameter", [CtFieldReadImpl]ArchiveExtractor.OverrideMode.class).withDescription([CtLiteralImpl]"$ExtractArchiveJob.overwriteExistingFilesParameter.help").withDefault([CtTypeAccessImpl]ArchiveExtractor.OverrideMode.ON_CHANGE).build();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.deleteArchiveParameter = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]sirius.biz.jobs.params.BooleanParameter([CtLiteralImpl]"deleteArchive", [CtLiteralImpl]"$ExtractArchiveJob.deleteArchiveParameter").withDescription([CtLiteralImpl]"$ExtractArchiveJob.deleteArchiveParameter.help").withDefaultTrue().build();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void execute([CtParameterImpl][CtTypeReferenceImpl]sirius.biz.process.ProcessContext process) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile sourceFile = [CtInvocationImpl][CtVariableReadImpl]process.require([CtFieldReadImpl]sourceParamter);
        [CtLocalVariableImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile targetDirectory = [CtInvocationImpl][CtVariableReadImpl]process.require([CtFieldReadImpl]destinationParameter);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]sirius.biz.util.ArchiveExtractor.OverrideMode overrideMode = [CtInvocationImpl][CtVariableReadImpl]process.require([CtFieldReadImpl]overwriteExistingFilesParameter);
        [CtInvocationImpl][CtVariableReadImpl]process.log([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]sirius.biz.process.logs.ProcessLog.info().withNLSKey([CtLiteralImpl]"FileImportJob.downloadingFile").withContext([CtLiteralImpl]"file", [CtInvocationImpl][CtVariableReadImpl]sourceFile.name()).withContext([CtLiteralImpl]"size", [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.formatSize([CtInvocationImpl][CtVariableReadImpl]sourceFile.size())));
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]sirius.biz.storage.layer1.FileHandle archive = [CtInvocationImpl][CtVariableReadImpl]sourceFile.download()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]extractor.extractAll([CtInvocationImpl][CtVariableReadImpl]sourceFile.name(), [CtInvocationImpl][CtVariableReadImpl]archive.getFile(), [CtLiteralImpl]null, [CtLambdaImpl]([CtParameterImpl] file) -> [CtInvocationImpl]handleExtractedFile([CtVariableReadImpl]file, [CtVariableReadImpl]process, [CtVariableReadImpl]overrideMode, [CtVariableReadImpl]targetDirectory));
        }
        [CtInvocationImpl][CtVariableReadImpl]process.setState([CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.get([CtLiteralImpl]"ExtractArchiveJob.completed"));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]process.isErroneous()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]process.require([CtFieldReadImpl]deleteArchiveParameter).booleanValue()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]process.log([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]sirius.biz.process.logs.ProcessLog.info().withNLSKey([CtLiteralImpl]"ExtractArchiveJob.deletingArchive"));
            [CtInvocationImpl][CtVariableReadImpl]sourceFile.delete();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleExtractedFile([CtParameterImpl][CtTypeReferenceImpl]sirius.biz.util.ExtractedFile extractedFile, [CtParameterImpl][CtTypeReferenceImpl]sirius.biz.process.ProcessContext process, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]sirius.biz.util.ArchiveExtractor.OverrideMode overrideMode, [CtParameterImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile targetDirectory) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]updateState([CtVariableReadImpl]extractedFile);
        [CtLocalVariableImpl][CtTypeReferenceImpl]sirius.kernel.commons.Watch watch = [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.commons.Watch.start();
        [CtLocalVariableImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile targetFile = [CtInvocationImpl][CtVariableReadImpl]targetDirectory.resolve([CtInvocationImpl][CtVariableReadImpl]extractedFile.getFilePath());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]sirius.biz.util.ArchiveExtractor.UpdateResult result = [CtInvocationImpl][CtFieldReadImpl]extractor.updateFile([CtVariableReadImpl]extractedFile, [CtVariableReadImpl]targetFile, [CtVariableReadImpl]overrideMode);
        [CtSwitchImpl]switch ([CtVariableReadImpl]result) {
            [CtCaseImpl]case [CtFieldReadImpl]CREATED :
                [CtInvocationImpl][CtVariableReadImpl]process.addTiming([CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.get([CtLiteralImpl]"ExtractArchiveJob.fileCreated"), [CtInvocationImpl][CtVariableReadImpl]watch.elapsedMillis());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]UPDATED :
                [CtInvocationImpl][CtVariableReadImpl]process.addTiming([CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.get([CtLiteralImpl]"ExtractArchiveJob.fileOverwritten"), [CtInvocationImpl][CtVariableReadImpl]watch.elapsedMillis());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]SKIPPED :
                [CtInvocationImpl][CtVariableReadImpl]process.addTiming([CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.get([CtLiteralImpl]"ExtractArchiveJob.fileSkipped"), [CtInvocationImpl][CtVariableReadImpl]watch.elapsedMillis());
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtVariableReadImpl]result.name());
        }
        [CtInvocationImpl]log([CtVariableReadImpl]process, [CtVariableReadImpl]extractedFile, [CtVariableReadImpl]targetFile, [CtInvocationImpl][CtVariableReadImpl]result.name());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void log([CtParameterImpl][CtTypeReferenceImpl]sirius.biz.process.ProcessContext process, [CtParameterImpl][CtTypeReferenceImpl]sirius.biz.util.ExtractedFile extractedFile, [CtParameterImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile targetFile, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String result) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]process.debug([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]sirius.biz.process.logs.ProcessLog.info().withFormattedMessage([CtLiteralImpl]"Extracting file '%s' to '%s' - size: %s, last modified: %s. Result: %s", [CtInvocationImpl][CtVariableReadImpl]extractedFile.getFilePath(), [CtInvocationImpl][CtVariableReadImpl]targetFile.path(), [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.formatSize([CtInvocationImpl][CtVariableReadImpl]extractedFile.size()), [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.toUserString([CtInvocationImpl][CtVariableReadImpl]extractedFile.lastModified()), [CtVariableReadImpl]result));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateState([CtParameterImpl][CtTypeReferenceImpl]sirius.biz.util.ExtractedFile extractedFile) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]sirius.kernel.async.TaskContext taskContext = [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.async.TaskContext.get();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]taskContext.shouldUpdateState().check()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]taskContext.setState([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.nls.NLS.fmtr([CtLiteralImpl]"$ExtractArchiveJob.progress").set([CtLiteralImpl]"progress", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]extractedFile.getProgressInPercent().toPercentString()).format());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void computePresetFor([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]sirius.web.http.QueryString queryString, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.Object targetObject, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> preset) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]preset.put([CtInvocationImpl][CtFieldReadImpl]sourceParamter.getName(), [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile) (targetObject)).path());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean hasPresetFor([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]sirius.web.http.QueryString queryString, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.Object targetObject) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]targetObject instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]sirius.biz.storage.layer3.VirtualFile) && [CtInvocationImpl][CtFieldReadImpl]extractor.isArchiveFile([CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.commons.Files.getFileExtension([CtInvocationImpl][CtVariableReadImpl]targetObject.toString()));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void collectParameters([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]sirius.biz.jobs.params.Parameter<[CtWildcardReferenceImpl]?>> parameterCollector) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]parameterCollector.accept([CtFieldReadImpl]sourceParamter);
        [CtInvocationImpl][CtVariableReadImpl]parameterCollector.accept([CtFieldReadImpl]destinationParameter);
        [CtInvocationImpl][CtVariableReadImpl]parameterCollector.accept([CtFieldReadImpl]overwriteExistingFilesParameter);
        [CtInvocationImpl][CtVariableReadImpl]parameterCollector.accept([CtFieldReadImpl]deleteArchiveParameter);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getIcon() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"fa-file-archive-o";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String createProcessTitle([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]sirius.kernel.commons.Strings.apply([CtLiteralImpl]"%s (%s)", [CtInvocationImpl]getLabel(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]sourceParamter.require([CtVariableReadImpl]context).name());
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]sirius.biz.process.PersistencePeriod getPersistencePeriod() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sirius.biz.process.PersistencePeriod.THREE_YEARS;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"file-extraction";
    }
}
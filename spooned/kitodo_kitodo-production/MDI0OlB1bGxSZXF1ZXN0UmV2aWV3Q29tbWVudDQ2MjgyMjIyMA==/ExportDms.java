[CompilationUnitImpl][CtCommentImpl]/* (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>

This file is part of the Kitodo project.

It is licensed under GNU General Public License version 3 or later.

For the full copyright and license information, please read the
GPL3-License.txt file that was distributed with this source code.
 */
[CtPackageDeclarationImpl]package org.kitodo.export;
[CtUnresolvedImport]import org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyDocStructHelperInterface;
[CtUnresolvedImport]import org.kitodo.exceptions.ExportException;
[CtUnresolvedImport]import org.apache.logging.log4j.Level;
[CtImportImpl]import java.net.URISyntaxException;
[CtUnresolvedImport]import org.kitodo.production.helper.Helper;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import org.kitodo.data.database.beans.Process;
[CtUnresolvedImport]import org.kitodo.production.metadata.copier.DataCopier;
[CtUnresolvedImport]import org.kitodo.production.model.Subfolder;
[CtUnresolvedImport]import org.kitodo.data.database.beans.Folder;
[CtUnresolvedImport]import org.kitodo.production.metadata.copier.CopierData;
[CtUnresolvedImport]import org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetadataHelper;
[CtUnresolvedImport]import org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper;
[CtUnresolvedImport]import org.kitodo.production.helper.tasks.ExportDmsTask;
[CtUnresolvedImport]import org.kitodo.config.ConfigCore;
[CtUnresolvedImport]import org.kitodo.data.exceptions.DataException;
[CtUnresolvedImport]import org.kitodo.exceptions.MetadataException;
[CtUnresolvedImport]import org.kitodo.production.services.file.FileService;
[CtUnresolvedImport]import org.kitodo.data.database.exceptions.DAOException;
[CtUnresolvedImport]import org.kitodo.production.helper.tasks.EmptyTask;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.kitodo.production.helper.VariableReplacer;
[CtUnresolvedImport]import org.kitodo.production.helper.tasks.TaskManager;
[CtUnresolvedImport]import org.kitodo.production.services.ServiceManager;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.apache.logging.log4j.Logger;
[CtUnresolvedImport]import org.kitodo.production.helper.tasks.TaskSitter;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.NoSuchElementException;
[CtImportImpl]import java.util.Objects;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.kitodo.config.enums.ParameterCore;
[CtUnresolvedImport]import org.apache.logging.log4j.LogManager;
[CtUnresolvedImport]import org.apache.commons.configuration.ConfigurationException;
[CtClassImpl]public class ExportDms extends [CtTypeReferenceImpl]org.kitodo.export.ExportMets {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.apache.logging.log4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.apache.logging.log4j.LogManager.getLogger([CtFieldReadImpl]org.kitodo.export.ExportDms.class);

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean exportWithImages = [CtLiteralImpl]true;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.kitodo.production.services.file.FileService fileService = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getFileService();

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPORT_DIR_DELETE = [CtLiteralImpl]"errorDirectoryDeleting";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ERROR_EXPORT = [CtLiteralImpl]"errorExport";

    [CtConstructorImpl]public ExportDms() [CtBlockImpl]{
    }

    [CtConstructorImpl]public ExportDms([CtParameterImpl][CtTypeReferenceImpl]boolean exportImages) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.exportWithImages = [CtVariableReadImpl]exportImages;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Export to DMS.
     *
     * @param process
     * 		Process object
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void startExport([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process) throws [CtTypeReferenceImpl]org.kitodo.data.exceptions.DataException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean wasNotAlreadyExported = [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]process.isExported();
        [CtIfImpl]if ([CtVariableReadImpl]wasNotAlreadyExported) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]process.setExported([CtLiteralImpl]true);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getProcessService().save([CtVariableReadImpl]process);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean exportSucessfull = [CtInvocationImpl]startExport([CtVariableReadImpl]process, [CtLiteralImpl](([CtTypeReferenceImpl]java.net.URI) (null)));
        [CtIfImpl]if ([CtVariableReadImpl]exportSucessfull) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]allChildsExported([CtVariableReadImpl]process)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]process.setSortHelperStatus([CtLiteralImpl]"100000000");
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtInvocationImpl][CtVariableReadImpl]process.getParent())) [CtBlockImpl]{
                [CtInvocationImpl]startExport([CtInvocationImpl][CtVariableReadImpl]process.getParent());
            }
        } else [CtIfImpl]if ([CtVariableReadImpl]wasNotAlreadyExported) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]process.setExported([CtLiteralImpl]false);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getProcessService().save([CtVariableReadImpl]process);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean allChildsExported([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]process.getChildren().isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allChildsExported = [CtLiteralImpl]true;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Process child : [CtInvocationImpl][CtVariableReadImpl]process.getChildren()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]allChildsExported &= [CtInvocationImpl][CtVariableReadImpl]child.isExported();
            }
            [CtReturnImpl]return [CtVariableReadImpl]allChildsExported;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Export to the DMS.
     *
     * @param process
     * 		process to export
     * @param unused
     * 		user home directory
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean startExport([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI unused) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.kitodo.config.ConfigCore.getBooleanParameterOrDefaultValue([CtTypeAccessImpl]ParameterCore.ASYNCHRONOUS_AUTOMATIC_EXPORT)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.tasks.TaskManager.addTask([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.helper.tasks.ExportDmsTask([CtThisAccessImpl]this, [CtVariableReadImpl]process));
            [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setMessage([CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.tasks.TaskSitter.isAutoRunningThreads() ? [CtLiteralImpl]"DMSExportByThread" : [CtLiteralImpl]"DMSExportThreadCreated", [CtInvocationImpl][CtVariableReadImpl]process.getTitle());
            [CtReturnImpl]return [CtLiteralImpl]true;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]startExport([CtVariableReadImpl]process, [CtLiteralImpl](([CtTypeReferenceImpl]org.kitodo.production.helper.tasks.ExportDmsTask) (null)));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Performs a DMS export to a desired place. In addition, it accepts an
     * optional ExportDmsTask object. If that is passed in, the progress in it
     * will be updated during processing and occurring errors will be passed to
     * it to be visible in the task manager screen.
     *
     * @param process
     * 		process to export
     * @param exportDmsTask
     * 		ExportDmsTask object to submit progress updates and errors
     * @return false if an error condition was caught, true otherwise
     */
    public [CtTypeReferenceImpl]boolean startExport([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.tasks.ExportDmsTask exportDmsTask) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.exportDmsTask = [CtVariableReadImpl]exportDmsTask;
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]startExport([CtVariableReadImpl]process, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getProcessService().readMetadataFile([CtVariableReadImpl]process).getDigitalDocument());
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.kitodo.data.database.exceptions.DAOException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtVariableReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]exportDmsTask.setException([CtVariableReadImpl]e);
                [CtInvocationImpl][CtFieldReadImpl]org.kitodo.export.ExportDms.logger.error([CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getTranslation([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]process.getTitle())), [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setErrorMessage([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtVariableReadImpl]process.getTitle() }, [CtFieldReadImpl]org.kitodo.export.ExportDms.logger, [CtVariableReadImpl]e);
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Start export.
     *
     * @param process
     * 		object
     * @param newFile
     * 		DigitalDocument
     * @return boolean
     */
    private [CtTypeReferenceImpl]boolean startExport([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper newFile) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.kitodo.data.database.exceptions.DAOException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.myPrefs = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getRulesetService().getPreferences([CtInvocationImpl][CtVariableReadImpl]process.getRuleset());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile = [CtInvocationImpl]readDocument([CtVariableReadImpl]process, [CtVariableReadImpl]newFile);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.isNull([CtVariableReadImpl]gdzfile)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean dataCopierResult = [CtInvocationImpl]executeDataCopierProcess([CtVariableReadImpl]gdzfile, [CtVariableReadImpl]process);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]dataCopierResult) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl]trimAllMetadata([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]gdzfile.getDigitalDocument().getLogicalDocStruct());
        [CtIfImpl][CtCommentImpl]// validate metadata
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.config.ConfigCore.getBooleanParameterOrDefaultValue([CtTypeAccessImpl]ParameterCore.USE_META_DATA_VALIDATION) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getMetadataValidationService().validate([CtVariableReadImpl]gdzfile, [CtFieldReadImpl][CtThisAccessImpl]this.myPrefs, [CtVariableReadImpl]process))) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setException([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.exceptions.MetadataException([CtLiteralImpl]"metadata validation failed", [CtLiteralImpl]null));
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtInvocationImpl]prepareExportLocation([CtVariableReadImpl]process, [CtVariableReadImpl]gdzfile);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean prepareExportLocation([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.kitodo.data.database.exceptions.DAOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI hotfolder = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]process.getProject().getDmsImportRootPath()).toURI();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String processTitle = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getNormalizedTitle([CtInvocationImpl][CtVariableReadImpl]process.getTitle());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI exportFolder = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtVariableReadImpl]hotfolder.getPath(), [CtVariableReadImpl]processTitle).toURI();
        [CtIfImpl][CtCommentImpl]// delete old export folder
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]fileService.delete([CtVariableReadImpl]exportFolder)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getTranslation([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]processTitle));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String description = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getTranslation([CtFieldReadImpl]org.kitodo.export.ExportDms.EXPORT_DIR_DELETE, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]exportFolder.getPath()));
            [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setErrorMessage([CtVariableReadImpl]message, [CtVariableReadImpl]description);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setException([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.exceptions.ExportException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]message + [CtLiteralImpl]": ") + [CtVariableReadImpl]description));
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtFieldReadImpl]fileService.createDirectory([CtVariableReadImpl]hotfolder, [CtVariableReadImpl]processTitle);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setProgress([CtLiteralImpl]1);
        }
        [CtReturnImpl]return [CtInvocationImpl]exportImagesAndMetsToDestinationUri([CtVariableReadImpl]process, [CtVariableReadImpl]gdzfile, [CtVariableReadImpl]exportFolder);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean exportImagesAndMetsToDestinationUri([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI destination) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.kitodo.data.database.exceptions.DAOException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]exportWithImages) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]directoryDownload([CtVariableReadImpl]process, [CtVariableReadImpl]destination);
            }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]java.lang.InterruptedException | [CtTypeReferenceImpl]java.lang.RuntimeException | [CtTypeReferenceImpl]java.net.URISyntaxException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setException([CtVariableReadImpl]e);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setErrorMessage([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtVariableReadImpl]process.getTitle() }, [CtFieldReadImpl]org.kitodo.export.ExportDms.logger, [CtVariableReadImpl]e);
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtInvocationImpl][CtCommentImpl]// export the file to the import folder
        asyncExportWithImport([CtVariableReadImpl]process, [CtVariableReadImpl]gdzfile, [CtVariableReadImpl]destination);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean executeDataCopierProcess([CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String rules = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.config.ConfigCore.getParameter([CtTypeAccessImpl]ParameterCore.COPY_DATA_ON_EXPORT);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtVariableReadImpl]rules) && [CtUnaryOperatorImpl](![CtInvocationImpl]executeDataCopierProcess([CtVariableReadImpl]gdzfile, [CtVariableReadImpl]process, [CtVariableReadImpl]rules))) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.util.NoSuchElementException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.kitodo.export.ExportDms.logger.catching([CtTypeAccessImpl]Level.TRACE, [CtVariableReadImpl]e);
            [CtCommentImpl]// no configuration simply means here is nothing to do
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean executeDataCopierProcess([CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String rules) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.metadata.copier.DataCopier([CtVariableReadImpl]rules).process([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.metadata.copier.CopierData([CtVariableReadImpl]gdzfile, [CtVariableReadImpl]process));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.apache.commons.configuration.ConfigurationException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setException([CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setErrorMessage([CtLiteralImpl]"dataCopier.syntaxError", [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtFieldReadImpl]org.kitodo.export.ExportDms.logger, [CtVariableReadImpl]e);
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper readDocument([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper newFile) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]gdzfile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.myPrefs.getRuleset());
            [CtInvocationImpl][CtVariableReadImpl]gdzfile.setDigitalDocument([CtVariableReadImpl]newFile);
            [CtReturnImpl]return [CtVariableReadImpl]gdzfile;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setException([CtVariableReadImpl]e);
                [CtInvocationImpl][CtFieldReadImpl]org.kitodo.export.ExportDms.logger.error([CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getTranslation([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtInvocationImpl][CtVariableReadImpl]process.getTitle())), [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.setErrorMessage([CtFieldReadImpl]org.kitodo.export.ExportDms.ERROR_EXPORT, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtVariableReadImpl]process.getTitle() }, [CtFieldReadImpl]org.kitodo.export.ExportDms.logger, [CtVariableReadImpl]e);
            }
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void asyncExportWithImport([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetsModsDigitalDocumentHelper gdzfile, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI userHome) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]org.kitodo.data.database.exceptions.DAOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String atsPpnBand = [CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.helper.Helper.getNormalizedTitle([CtInvocationImpl][CtVariableReadImpl]process.getTitle());
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setWorkDetail([CtBinaryOperatorImpl][CtVariableReadImpl]atsPpnBand + [CtLiteralImpl]".xml");
        }
        [CtInvocationImpl]writeMetsFile([CtVariableReadImpl]process, [CtInvocationImpl][CtFieldReadImpl]fileService.createResource([CtVariableReadImpl]userHome, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator + [CtVariableReadImpl]atsPpnBand) + [CtLiteralImpl]".xml"), [CtVariableReadImpl]gdzfile);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setProgress([CtLiteralImpl]100);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get exportDmsTask.
     *
     * @return value of exportDmsTask
     */
    public [CtTypeReferenceImpl]org.kitodo.production.helper.tasks.EmptyTask getExportDmsTask() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]exportDmsTask;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Setter method to pass in a task thread to whom progress and error messages
     * shall be reported.
     *
     * @param task
     * 		task implementation
     */
    public [CtTypeReferenceImpl]void setExportDmsTask([CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.tasks.EmptyTask task) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.exportDmsTask = [CtVariableReadImpl]task;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Run through all metadata and children of given docstruct to trim the strings
     * calls itself recursively.
     */
    private [CtTypeReferenceImpl]void trimAllMetadata([CtParameterImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyDocStructHelperInterface inStruct) [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// trim all metadata values
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyMetadataHelper md : [CtInvocationImpl][CtVariableReadImpl]inStruct.getAllMetadata()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtInvocationImpl][CtVariableReadImpl]md.getValue())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]md.setStringValue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]md.getValue().trim());
            }
        }
        [CtForEachImpl][CtCommentImpl]// run through all children of docstruct
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.helper.metadata.legacytypeimplementations.LegacyDocStructHelperInterface child : [CtInvocationImpl][CtVariableReadImpl]inStruct.getAllChildren()) [CtBlockImpl]{
            [CtInvocationImpl]trimAllMetadata([CtVariableReadImpl]child);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Download image.
     *
     * @param process
     * 		object
     * @param userHome
     * 		File
     * @param atsPpnBand
     * 		String
     * @param ordnerEndung
     * 		String
     */
    public [CtTypeReferenceImpl]void imageDownload([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI userHome, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String atsPpnBand, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String ordnerEndung) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// determine the source folder
        [CtTypeReferenceImpl]java.net.URI tifOrdner = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.kitodo.production.services.ServiceManager.getProcessService().getImagesTifDirectory([CtLiteralImpl]true, [CtInvocationImpl][CtVariableReadImpl]process.getId(), [CtInvocationImpl][CtVariableReadImpl]process.getTitle(), [CtInvocationImpl][CtVariableReadImpl]process.getProcessBaseUri());
        [CtIfImpl][CtCommentImpl]// copy the source folder to the destination folder
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]fileService.fileExist([CtVariableReadImpl]tifOrdner) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]fileService.getSubUris([CtVariableReadImpl]tifOrdner).isEmpty())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI zielTif = [CtInvocationImpl][CtVariableReadImpl]userHome.resolve([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]atsPpnBand + [CtVariableReadImpl]ordnerEndung) + [CtLiteralImpl]"/");
            [CtIfImpl][CtCommentImpl]// with Agora import simply create the folder
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]fileService.fileExist([CtVariableReadImpl]zielTif)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]fileService.createDirectory([CtVariableReadImpl]userHome, [CtBinaryOperatorImpl][CtVariableReadImpl]atsPpnBand + [CtVariableReadImpl]ordnerEndung);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setWorkDetail([CtLiteralImpl]null);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Starts copying all directories configured as export folder.
     *
     * @param process
     * 		object
     * @param destination
     * 		the destination directory
     * @throws InterruptedException
     * 		if the user clicked stop on the thread running the export DMS
     * 		task
     */
    private [CtTypeReferenceImpl]void directoryDownload([CtParameterImpl][CtTypeReferenceImpl]java.lang.Process process, [CtParameterImpl][CtTypeReferenceImpl]java.net.URI destination) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.InterruptedException, [CtTypeReferenceImpl]java.net.URISyntaxException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]org.kitodo.production.model.Subfolder> processDirs = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]process.getProject().getFolders().parallelStream().filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Folder::isCopyFolder).map([CtLambdaImpl]([CtParameterImpl] folder) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.model.Subfolder([CtVariableReadImpl]process, [CtVariableReadImpl]folder)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.helper.VariableReplacer variableReplacer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.kitodo.production.helper.VariableReplacer([CtLiteralImpl]null, [CtLiteralImpl]null, [CtVariableReadImpl]process, [CtLiteralImpl]null);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uriToDestination = [CtInvocationImpl][CtVariableReadImpl]destination.toString();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]uriToDestination.endsWith([CtLiteralImpl]"/")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]uriToDestination = [CtInvocationImpl][CtVariableReadImpl]uriToDestination.concat([CtLiteralImpl]"/");
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.kitodo.production.model.Subfolder processDir : [CtVariableReadImpl]processDirs) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI dstDir = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtBinaryOperatorImpl][CtVariableReadImpl]uriToDestination + [CtInvocationImpl][CtVariableReadImpl]variableReplacer.replace([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]processDir.getFolder().getRelativePath()));
            [CtInvocationImpl][CtFieldReadImpl]fileService.createDirectories([CtVariableReadImpl]dstDir);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.net.URI> srcs = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]processDir.listContents().values();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int progress = [CtLiteralImpl]0;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI src : [CtVariableReadImpl]srcs) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setWorkDetail([CtInvocationImpl][CtFieldReadImpl]fileService.getFileName([CtVariableReadImpl]src));
                }
                [CtInvocationImpl][CtFieldReadImpl]fileService.copyFileToDirectory([CtVariableReadImpl]src, [CtVariableReadImpl]dstDir);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.nonNull([CtFieldReadImpl]exportDmsTask)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]exportDmsTask.setProgress([CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl]([CtVariableWriteImpl]progress++) + [CtLiteralImpl]1) * [CtLiteralImpl]98.0) / [CtInvocationImpl][CtVariableReadImpl]processDirs.size()) / [CtInvocationImpl][CtVariableReadImpl]srcs.size()) + [CtLiteralImpl]1)));
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]exportDmsTask.isInterrupted()) [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.InterruptedException();
                    }
                }
            }
        }
    }
}
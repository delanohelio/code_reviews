[CompilationUnitImpl][CtPackageDeclarationImpl]package com.dotcms.tika;
[CtUnresolvedImport]import com.dotmarketing.util.Logger;
[CtUnresolvedImport]import com.dotmarketing.util.UtilMethods;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import com.dotmarketing.exception.DotDataException;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.io.OutputStream;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.dotmarketing.util.Config;
[CtUnresolvedImport]import com.dotcms.repackage.org.apache.commons.io.FileUtils;
[CtUnresolvedImport]import com.dotmarketing.business.APILocator;
[CtUnresolvedImport]import com.google.gson.GsonBuilder;
[CtImportImpl]import java.io.Reader;
[CtUnresolvedImport]import com.dotcms.business.CloseDBIfOpened;
[CtImportImpl]import java.util.zip.GZIPOutputStream;
[CtImportImpl]import java.nio.file.Files;
[CtUnresolvedImport]import com.google.gson.Gson;
[CtUnresolvedImport]import com.dotcms.repackage.org.apache.commons.io.IOUtils;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.model.Contentlet;
[CtImportImpl]import java.io.StringReader;
[CtUnresolvedImport]import com.google.gson.stream.JsonWriter;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.io.InputStream;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtImportImpl]import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import com.dotcms.osgi.OSGIConstants;
[CtUnresolvedImport]import com.dotmarketing.exception.DotSecurityException;
[CtImportImpl]import java.util.TreeMap;
[CtUnresolvedImport]import com.liferay.util.FileUtil;
[CtUnresolvedImport]import org.apache.felix.framework.OSGIUtil;
[CtUnresolvedImport]import com.dotmarketing.util.StringUtils;
[CtUnresolvedImport]import com.dotcms.contenttype.model.type.BaseContentType;
[CtUnresolvedImport]import com.dotmarketing.business.FactoryLocator;
[CtUnresolvedImport]import com.dotmarketing.portlets.fileassets.business.FileAssetAPI;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl]public class TikaUtils {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int SIZE = [CtLiteralImpl]1024;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int DEFAULT_META_DATA_MAX_SIZE = [CtLiteralImpl]5;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper objectMapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotcms.tika.TikaProxyService tikaService;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Boolean osgiInitialized;

    [CtConstructorImpl]public TikaUtils() throws [CtTypeReferenceImpl]com.dotmarketing.exception.DotDataException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]osgiInitialized = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.felix.framework.OSGIUtil.getInstance().isInitialized();
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]osgiInitialized) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.warn([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"OSGI Framework not initialized, trying to initialize...");
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.felix.framework.OSGIUtil.getInstance().initializeFramework([CtTypeAccessImpl]Config.CONTEXT);
                [CtAssignmentImpl][CtFieldWriteImpl]osgiInitialized = [CtLiteralImpl]true;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"Unable to initialized OSGI Framework", [CtVariableReadImpl]e);
        }
        [CtIfImpl]if ([CtFieldReadImpl]osgiInitialized) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Search for the TikaServiceBuilder service instance expose through OSGI
            [CtTypeReferenceImpl]com.dotcms.tika.TikaServiceBuilder tikaServiceBuilder = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]tikaServiceBuilder = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.felix.framework.OSGIUtil.getInstance().getService([CtFieldReadImpl]com.dotcms.tika.TikaServiceBuilder.class, [CtTypeAccessImpl]OSGIConstants.BUNDLE_NAME_DOTCMS_TIKA);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tikaServiceBuilder) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"OSGI Service [%s] not found for bundle [%s]", [CtFieldReadImpl]com.dotcms.tika.TikaServiceBuilder.class, [CtTypeAccessImpl]OSGIConstants.BUNDLE_NAME_DOTCMS_TIKA));
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Failure retrieving OSGI Service [%s] in bundle [%s]", [CtFieldReadImpl]com.dotcms.tika.TikaServiceBuilder.class, [CtTypeAccessImpl]OSGIConstants.BUNDLE_NAME_DOTCMS_TIKA), [CtVariableReadImpl]e);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tikaServiceBuilder) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]osgiInitialized = [CtLiteralImpl]false;
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtCommentImpl]/* Creating a new instance of the TikaProxyService in order to use the Tika services exposed in OSGI,
            when the createTikaService method is called a new instance of Tika is also created
            by the TikaProxyService implementation.
             */
            [CtFieldWriteImpl][CtThisAccessImpl]this.tikaService = [CtInvocationImpl][CtVariableReadImpl]tikaServiceBuilder.createTikaService();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"OSGI Framework not initialized");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method takes a file and uses tika to parse the metadata from it. It
     * returns a Map of the metadata <strong>BUT this method won't try to create any metadata file
     * if does not exist or to override the existing metadata file for the given Contentlet and
     * beside that will put in memory the given file content before to parse it</strong>.
     *
     * @param inode
     * 		Contentlet owner of the file to parse
     * @param binFile
     * 		File to parse the metadata from it
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getMetaDataMapForceMemory([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String inode, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getMetaDataMap([CtVariableReadImpl]inode, [CtVariableReadImpl]binFile, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method takes a file and uses tika to parse the metadata from it. It
     * returns a Map of the metadata and creates a metadata file for the given
     * Contentlet if does not already exist, if already exist only the metadata is returned and no
     * file is override.
     *
     * @param inode
     * 		Contentlet owner of the file to parse
     * @param binFile
     * 		File to parse the metadata from it
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getMetaDataMap([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String inode, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getMetaDataMap([CtVariableReadImpl]inode, [CtVariableReadImpl]binFile, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the Contentlet is a File asset in order to identify if it
     * is missing a metadata file, if the metadata does not exist this method
     * parses the file asset and generates it, <strong>this operation also implies a save
     * operation to the Contentlet in order to save the parsed metadata info</strong>.
     *
     * @param contentlet
     * 		Content parse in order to extract the metadata info
     * @return True if a metadata file was generated.
     */
    public [CtTypeReferenceImpl]boolean generateMetaData([CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet) throws [CtTypeReferenceImpl]com.dotmarketing.exception.DotDataException, [CtTypeReferenceImpl]com.dotmarketing.exception.DotSecurityException [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]generateMetaData([CtVariableReadImpl]contentlet, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the Contentlet is a File asset in order to parse it and generate a metadata
     * file for it, <strong>this operation also implies a save operation to the Contentlet
     * in order to save the parsed metadata info</strong>.
     *
     * @param contentlet
     * 		Content parse in order to extract the metadata info
     * @return True if a metadata file was generated.
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> generateMetaDataForce([CtParameterImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binaryField, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String fieldVariableName, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> metadataFields) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.generateMetaData([CtVariableReadImpl]contentlet, [CtVariableReadImpl]binaryField, [CtVariableReadImpl]fieldVariableName, [CtVariableReadImpl]metadataFields, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the Contentlet is a File asset in order to parse it and generate a metadata
     * file for it, <strong>this operation also implies a save operation to the Contentlet
     * in order to save the parsed metadata info</strong>.
     *
     * @param contentlet
     * 		Content parse in order to extract the metadata info
     * @return True if a metadata file was generated.
     */
    public [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> generateMetaData([CtParameterImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binaryField, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String fieldVariableName, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> metadataFields) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtThisAccessImpl]this.generateMetaData([CtVariableReadImpl]contentlet, [CtVariableReadImpl]binaryField, [CtVariableReadImpl]fieldVariableName, [CtVariableReadImpl]metadataFields, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.dotcms.business.CloseDBIfOpened
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> generateMetaData([CtParameterImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binaryField, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String fieldVariableName, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> metadataFields, [CtParameterImpl]final [CtTypeReferenceImpl]boolean force) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// See if we have content metadata file
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> metaDataMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String fileName = [CtBinaryOperatorImpl][CtVariableReadImpl]fieldVariableName + [CtLiteralImpl]"-metadata.json";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.File contentMetaFile = [CtInvocationImpl][CtCommentImpl]// creates something like /1/2/12421124-15652532-235325-12312/fileAsset-metadata.json
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.APILocator.getFileAssetAPI().getContentMetadataFile([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode(), [CtVariableReadImpl]fileName);
        [CtIfImpl][CtCommentImpl]/* If we want to force the parse of the file and the generation of the metadata file
        we need to delete the existing one first.
         */
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]force && [CtInvocationImpl][CtVariableReadImpl]contentMetaFile.exists()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contentMetaFile.delete();
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to delete existing metadata file [%s] [%s]", [CtInvocationImpl][CtVariableReadImpl]contentMetaFile.getAbsolutePath(), [CtInvocationImpl][CtVariableReadImpl]e.getMessage()), [CtVariableReadImpl]e);
            }
        }
        [CtIfImpl][CtCommentImpl]// If the metadata file does not exist we need to parse and get the metadata for the file
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contentMetaFile.exists()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]binaryField != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int maxLength = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getIntProperty([CtLiteralImpl]"META_DATA_MAX_SIZE", [CtFieldReadImpl]com.dotcms.tika.TikaUtils.DEFAULT_META_DATA_MAX_SIZE) * [CtFieldReadImpl]com.dotcms.tika.TikaUtils.SIZE;
                [CtAssignmentImpl][CtCommentImpl]// Parse the metadata from this file
                [CtVariableWriteImpl]metaDataMap = [CtInvocationImpl][CtThisAccessImpl]this.getForcedMetaDataMap([CtVariableReadImpl]binaryField, [CtVariableReadImpl]metadataFields, [CtVariableReadImpl]maxLength);
                [CtInvocationImpl][CtThisAccessImpl]this.writeCompressJsonMetadataFile([CtVariableReadImpl]contentMetaFile, [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtVariableReadImpl]metaDataMap) ? [CtVariableReadImpl]metaDataMap : [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap());
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]metaDataMap = [CtInvocationImpl][CtThisAccessImpl]this.readCompressedJsonMetadataFile([CtVariableReadImpl]contentMetaFile);
        }
        [CtReturnImpl]return [CtVariableReadImpl]metaDataMap;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> readCompressedJsonMetadataFile([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File contentMetaFile) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> objectMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
        [CtLocalVariableImpl][CtCommentImpl]// compressor config
        final [CtTypeReferenceImpl]java.lang.String compressor = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getStringProperty([CtLiteralImpl]"CONTENT_METADATA_COMPRESSOR", [CtLiteralImpl]"none");
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.FileUtil.createInputStream([CtInvocationImpl][CtVariableReadImpl]contentMetaFile.toPath(), [CtVariableReadImpl]compressor)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]objectMap = [CtInvocationImpl][CtFieldReadImpl]com.dotcms.tika.TikaUtils.objectMapper.readValue([CtVariableReadImpl]inputStream, [CtFieldReadImpl]java.util.Map.class);
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.info([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Metadata read from: " + [CtVariableReadImpl]contentMetaFile);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]objectMap;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void writeCompressJsonMetadataFile([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File contentMetaFile, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtWildcardReferenceImpl]?, [CtTypeReferenceImpl]java.lang.Object> objectMap) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// compressor config
        final [CtTypeReferenceImpl]java.lang.String compressor = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getStringProperty([CtLiteralImpl]"CONTENT_METADATA_COMPRESSOR", [CtLiteralImpl]"none");
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentMetaFile.getParentFile().exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentMetaFile.getParentFile().mkdirs();
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.OutputStream out = [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.FileUtil.createOutputStream([CtInvocationImpl][CtVariableReadImpl]contentMetaFile.toPath(), [CtVariableReadImpl]compressor)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]com.dotcms.tika.TikaUtils.objectMapper.writeValue([CtVariableReadImpl]out, [CtVariableReadImpl]objectMap);
            [CtInvocationImpl][CtVariableReadImpl]out.flush();
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.info([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Metadata wrote on: " + [CtVariableReadImpl]contentMetaFile);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if the Contentlet is a File asset in order to parse it and generate a metadata
     * file for it, <strong>this operation also implies a save operation to the Contentlet
     * in order to save the parsed metadata info</strong>.
     *
     * @param contentlet
     * 		Content parse in order to extract the metadata info
     * @param force
     * 		If <strong>false</strong> we will try to parse and generate the metadata file
     * 		only if a metadata file does NOT already exist. If <strong>true</strong> we delete the
     * 		existing metadata file in order to force a parse and generation of the metadata file.
     * @return True if a metadata file was generated.
     */
    [CtAnnotationImpl]@com.dotcms.business.CloseDBIfOpened
    public [CtTypeReferenceImpl]boolean generateMetaData([CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl][CtTypeReferenceImpl]boolean force) throws [CtTypeReferenceImpl]com.dotmarketing.exception.DotSecurityException, [CtTypeReferenceImpl]com.dotmarketing.exception.DotDataException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]BaseContentType.FILEASSET.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentlet.getContentType().baseType())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// See if we have content metadata file
            final [CtTypeReferenceImpl]java.io.File contentMeta = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.APILocator.getFileAssetAPI().getContentMetadataFile([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode());
            [CtIfImpl][CtCommentImpl]/* If we want to force the parse of the file and the generation of the metadata file
            we need to delete the existing one first.
             */
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]force && [CtInvocationImpl][CtVariableReadImpl]contentMeta.exists()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]contentMeta.delete();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Unable to delete existing metadata file [%s] [%s]", [CtInvocationImpl][CtVariableReadImpl]contentMeta.getAbsolutePath(), [CtInvocationImpl][CtVariableReadImpl]e.getMessage()), [CtVariableReadImpl]e);
                }
            }
            [CtIfImpl][CtCommentImpl]// If the metadata file does not exist we need to parse and get the metadata for the file
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contentMeta.exists()) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.File binFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.APILocator.getContentletAPI().getBinaryFile([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode(), [CtTypeAccessImpl]FileAssetAPI.BINARY_FIELD, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.APILocator.getUserAPI().getSystemUser());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]binFile != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// Parse the metadata from this file
                    final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metaData = [CtInvocationImpl]getMetaDataMap([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode(), [CtVariableReadImpl]binFile);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]metaData) [CtBlockImpl]{
                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.google.gson.Gson gson = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.GsonBuilder().disableHtmlEscaping().create();
                        [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtTypeAccessImpl]FileAssetAPI.META_DATA_FIELD, [CtInvocationImpl][CtVariableReadImpl]gson.toJson([CtVariableReadImpl]metaData));
                        [CtInvocationImpl][CtCommentImpl]// Save the parsed metadata to the contentlet
                        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.FactoryLocator.getContentletFactory().save([CtVariableReadImpl]contentlet);
                    }
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Similar as {@link #getMetaDataMap(String, File, boolean)} but includes the metadata fields to filter from the tika collection
     * and the max length of the binary file to parse.
     * Also, it is not storing anything on the file system as the reference method {@link #getMetaDataMap(String, File, boolean)}
     * This one does everything on memory, means forceMemory is always true and the file system cache has to be performed on upper layers
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> getForcedMetaDataMap([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> metadataFields, [CtParameterImpl]final [CtTypeReferenceImpl]int maxLength) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]osgiInitialized) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"Unable to get file Meta Data, OSGI Framework not initialized");
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyMap();
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> metaMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.setMaxStringLength([CtVariableReadImpl]maxLength);
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream stream = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.newInputStream([CtInvocationImpl][CtVariableReadImpl]binFile.toPath())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// no worry about the limit and less time to process.
            final [CtTypeReferenceImpl]java.lang.String content = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.parseToString([CtVariableReadImpl]stream);
            [CtInvocationImpl][CtCommentImpl]// Creating the meta data map to use by our content
            [CtVariableReadImpl]metaMap.putAll([CtInvocationImpl][CtThisAccessImpl]this.buildMetaDataMap());
            [CtInvocationImpl][CtVariableReadImpl]metaMap.put([CtTypeAccessImpl]FileAssetAPI.CONTENT_FIELD, [CtVariableReadImpl]content);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ioExc) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isZeroByteFileException([CtInvocationImpl][CtVariableReadImpl]ioExc.getCause())) [CtBlockImpl]{
                [CtInvocationImpl]logWarning([CtVariableReadImpl]binFile, [CtInvocationImpl][CtVariableReadImpl]ioExc.getCause());
            } else [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String errorMessage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Error Reading Tika parsed Stream for file [%s] [%s] ", [CtInvocationImpl][CtVariableReadImpl]binFile.getAbsolutePath(), [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]ioExc.getMessage()) ? [CtInvocationImpl][CtVariableReadImpl]ioExc.getMessage() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ioExc.getCause().getMessage());
                [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.warn([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtVariableReadImpl]errorMessage);
                [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.debug([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtVariableReadImpl]errorMessage, [CtVariableReadImpl]ioExc);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtThisAccessImpl]this.isZeroByteFileException([CtVariableReadImpl]e)) [CtBlockImpl]{
                [CtInvocationImpl]logWarning([CtVariableReadImpl]binFile, [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl]logError([CtVariableReadImpl]binFile, [CtVariableReadImpl]e);
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]metaMap.put([CtLiteralImpl]"length", [CtInvocationImpl][CtVariableReadImpl]binFile.length());
        }
        [CtInvocationImpl][CtThisAccessImpl]this.filterMetadataFields([CtVariableReadImpl]metaMap, [CtVariableReadImpl]metadataFields);
        [CtReturnImpl]return [CtVariableReadImpl]metaMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Right now the method use the Tika facade directly for parse the document without any kind of restriction about the parser because the
     * new Tika().parse method use the AutoDetectParser by default.
     *
     * @author Graziano Aliberti - Engineering Ingegneria Informatica S.p.a

    May 31, 2013 - 12:27:19 PM
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> getMetaDataMap([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String inode, [CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile, [CtParameterImpl][CtTypeReferenceImpl]boolean forceMemory) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]osgiInitialized) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"Unable to get file Meta Data, OSGI Framework not initialized");
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        }
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.setMaxStringLength([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metaMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtCommentImpl]// Search for the stored content metadata on disk
        [CtTypeReferenceImpl]java.io.File contentMetadataFile = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.APILocator.getFileAssetAPI().getContentMetadataFile([CtVariableReadImpl]inode);
        [CtTryImpl][CtCommentImpl]// if the limit is not "unlimited"
        [CtCommentImpl]// I can use the faster parseToString
        try [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]forceMemory) [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream stream = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.newInputStream([CtInvocationImpl][CtVariableReadImpl]binFile.toPath())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// no worry about the limit and less time to process.
                    final [CtTypeReferenceImpl]java.lang.String content = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.parseToString([CtVariableReadImpl]stream);
                    [CtAssignmentImpl][CtCommentImpl]// Creating the meta data map to use by our content
                    [CtVariableWriteImpl]metaMap = [CtInvocationImpl]buildMetaDataMap();
                    [CtInvocationImpl][CtVariableReadImpl]metaMap.put([CtTypeAccessImpl]FileAssetAPI.CONTENT_FIELD, [CtVariableReadImpl]content);
                }
            } else [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.tikaInputStreamGet([CtVariableReadImpl]binFile);[CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader fulltext = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.parse([CtVariableReadImpl]is)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Write the parsed info into the metadata file
                    [CtVariableWriteImpl]metaMap = [CtInvocationImpl]writeMetadata([CtVariableReadImpl]fulltext, [CtVariableReadImpl]contentMetadataFile);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ioExc) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isZeroByteFileException([CtInvocationImpl][CtVariableReadImpl]ioExc.getCause())) [CtBlockImpl]{
                [CtInvocationImpl]logWarning([CtVariableReadImpl]binFile, [CtInvocationImpl][CtVariableReadImpl]ioExc.getCause());
            } else [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// On error lets try a fallback operation
                    [CtVariableWriteImpl]metaMap = [CtInvocationImpl]fallbackParse([CtVariableReadImpl]binFile, [CtVariableReadImpl]contentMetadataFile, [CtVariableReadImpl]ioExc);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl]logError([CtVariableReadImpl]binFile, [CtVariableReadImpl]e);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl]isZeroByteFileException([CtVariableReadImpl]e)) [CtBlockImpl]{
                [CtInvocationImpl]logWarning([CtVariableReadImpl]binFile, [CtVariableReadImpl]e);
            } else [CtBlockImpl]{
                [CtInvocationImpl]logError([CtVariableReadImpl]binFile, [CtVariableReadImpl]e);
            }
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]metaMap.put([CtTypeAccessImpl]FileAssetAPI.SIZE_FIELD, [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]binFile.length()));
        }
        [CtInvocationImpl]filterMetadataFields([CtVariableReadImpl]metaMap, [CtInvocationImpl]getConfiguredMetadataFields());
        [CtReturnImpl]return [CtVariableReadImpl]metaMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads INDEX_METADATA_FIELDS from configuration
     *
     * @return  */
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> getConfiguredMetadataFields() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String configFields = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getStringProperty([CtLiteralImpl]"INDEX_METADATA_FIELDS", [CtLiteralImpl]null);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtVariableReadImpl]configFields)) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]configFields.split([CtLiteralImpl]",")));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptySet();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Filters fields from a map given a set of fields to be kept
     *
     * @param metaMap
     * @param configFieldsSet
     */
    public [CtTypeReferenceImpl]void filterMetadataFields([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]java.lang.Object> metaMap, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> configFieldsSet) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtVariableReadImpl]metaMap) && [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtVariableReadImpl]configFieldsSet)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]metaMap.entrySet().removeIf([CtLambdaImpl]([CtParameterImpl]java.util.Map.Entry<java.lang.String, ? extends java.lang.Object> entry) -> [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]configFieldsSet.contains([CtLiteralImpl]"*")) && [CtUnaryOperatorImpl](![CtInvocationImpl]checkIfFieldMatches([CtInvocationImpl][CtVariableReadImpl]entry.getKey(), [CtVariableReadImpl]configFieldsSet)));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies if a string matches in a set of regex/strings
     *
     * @param key
     * @param configFieldsSet
     * @return  */
    private [CtTypeReferenceImpl]boolean checkIfFieldMatches([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> configFieldsSet) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]java.lang.String> condition = [CtLambdaImpl]([CtParameterImpl]java.lang.String e) -> [CtInvocationImpl][CtVariableReadImpl]key.matches([CtVariableReadImpl]e);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configFieldsSet.stream().anyMatch([CtVariableReadImpl]condition);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Writes the content of a given Reader into the Contentlet metadata file
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> writeMetadata([CtParameterImpl][CtTypeReferenceImpl]java.io.Reader fullText, [CtParameterImpl][CtTypeReferenceImpl]java.io.File contentMetadataFile) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]char[] buf = [CtNewArrayImpl]new [CtTypeReferenceImpl]char[[CtFieldReadImpl]com.dotcms.tika.TikaUtils.SIZE];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtInvocationImpl][CtVariableReadImpl]fullText.read([CtVariableReadImpl]buf);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]count > [CtLiteralImpl]0) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.exists())) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Create the new content metadata file
            prepareMetaDataFile([CtVariableReadImpl]contentMetadataFile);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.OutputStream out = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.newOutputStream([CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.toPath());
            [CtLocalVariableImpl][CtCommentImpl]// compressor config
            final [CtTypeReferenceImpl]java.lang.String compressor = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getStringProperty([CtLiteralImpl]"CONTENT_METADATA_COMPRESSOR", [CtLiteralImpl]"none");
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"gzip".equals([CtVariableReadImpl]compressor)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.zip.GZIPOutputStream([CtVariableReadImpl]out);
            } else [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"bzip2".equals([CtVariableReadImpl]compressor)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]out = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream([CtVariableReadImpl]out);
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] bytes;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]int metadataLimit = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Config.getIntProperty([CtLiteralImpl]"META_DATA_MAX_SIZE", [CtFieldReadImpl]com.dotcms.tika.TikaUtils.DEFAULT_META_DATA_MAX_SIZE) * [CtFieldReadImpl]com.dotcms.tika.TikaUtils.SIZE) * [CtFieldReadImpl]com.dotcms.tika.TikaUtils.SIZE;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int numOfChunks = [CtBinaryOperatorImpl][CtVariableReadImpl]metadataLimit / [CtFieldReadImpl]com.dotcms.tika.TikaUtils.SIZE;
                [CtDoImpl]do [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lowered = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]buf);
                    [CtAssignmentImpl][CtVariableWriteImpl]lowered = [CtInvocationImpl][CtVariableReadImpl]lowered.toLowerCase();
                    [CtAssignmentImpl][CtVariableWriteImpl]bytes = [CtInvocationImpl][CtVariableReadImpl]lowered.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8);
                    [CtInvocationImpl][CtVariableReadImpl]out.write([CtVariableReadImpl]bytes, [CtLiteralImpl]0, [CtFieldReadImpl][CtVariableReadImpl]bytes.length);
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]numOfChunks--;
                } while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtAssignmentImpl]([CtVariableWriteImpl]count = [CtInvocationImpl][CtVariableReadImpl]fullText.read([CtVariableReadImpl]buf)) > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]numOfChunks > [CtLiteralImpl]0) );
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.apache.commons.io.IOUtils.closeQuietly([CtVariableReadImpl]out);
            }
        } else [CtIfImpl][CtCommentImpl]/* Create an empty file if count == 0, there is no content but it is a record
        that we already try to process this file. If the file already exist do nothing
         */
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.exists()) [CtBlockImpl]{
            [CtInvocationImpl]prepareMetaDataFile([CtVariableReadImpl]contentMetadataFile);
            [CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.apache.commons.io.FileUtils.writeStringToFile([CtVariableReadImpl]contentMetadataFile, [CtLiteralImpl]"NO_METADATA");
        }
        [CtReturnImpl][CtCommentImpl]// Creating the meta data map to use by our content
        return [CtInvocationImpl]buildMetaDataMap();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fallback method used in cases when a file can not be parsed properly like for example
     * malformed xml files, a malformed xml file will throw a parsing exception.
     * </br>
     * This fallback operation will read the given file as a plain text file in order to avoid
     * validation errors.
     */
    private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> fallbackParse([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile, [CtParameterImpl][CtTypeReferenceImpl]java.io.File contentMetadataFile, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Exception ioExc) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String errorMessage = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error Reading Tika parsed Stream for file [%s] [%s] - " + [CtLiteralImpl]"Executing fallback in order to parse the file ") + [CtLiteralImpl]"as a plain text file.", [CtInvocationImpl][CtVariableReadImpl]binFile.getAbsolutePath(), [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]ioExc.getMessage()) ? [CtInvocationImpl][CtVariableReadImpl]ioExc.getMessage() : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ioExc.getCause().getMessage());
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.warn([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtVariableReadImpl]errorMessage);
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.debug([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtVariableReadImpl]errorMessage, [CtVariableReadImpl]ioExc);
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream stream = [CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Files.newInputStream([CtInvocationImpl][CtVariableReadImpl]binFile.toPath())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Parse the content as plain text in order to avoid validation errors
            final [CtTypeReferenceImpl]java.lang.String content = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.parseToStringAsPlainText([CtVariableReadImpl]stream);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader contentReader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]content)) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// Write the parsed info into the metadata file
                return [CtInvocationImpl]writeMetadata([CtVariableReadImpl]contentReader, [CtVariableReadImpl]contentMetadataFile);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Detects the media type of the given file. The type detection is
     * based on the document content and a potential known file extension.
     *
     * @param file
     * 		the file
     * @return detected media type
     * @throws IOException
     * 		if the file can not be read
     */
    public [CtTypeReferenceImpl]java.lang.String detect([CtParameterImpl][CtTypeReferenceImpl]java.io.File file) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]osgiInitialized) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtLiteralImpl]"Unable to get file media type, OSGI Framework not initialized");
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.detect([CtVariableReadImpl]file);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> buildMetaDataMap() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metaMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.metadataNames().length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtArrayReadImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.metadataNames()[[CtVariableReadImpl]i];
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.UtilMethods.isSet([CtVariableReadImpl]name) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.metadataGetName([CtVariableReadImpl]name) != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// we will want to normalize our metadata for searching
                [CtArrayTypeReferenceImpl]java.lang.String[] x = [CtInvocationImpl]translateKey([CtVariableReadImpl]name);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String y : [CtVariableReadImpl]x) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]metaMap.put([CtVariableReadImpl]y, [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tikaService.metadataGetName([CtVariableReadImpl]name));
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]metaMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates the metadata file where the parsed info will be stored
     */
    private [CtTypeReferenceImpl]void prepareMetaDataFile([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File contentMetadataFile) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.exists()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Create the file if does not exist
            [CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.getParentFile().mkdirs();
            [CtInvocationImpl][CtVariableReadImpl]contentMetadataFile.createNewFile();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * normalize metadata from various filetypes this method will return an
     * array of metadata keys that we can use to normalize the values in our
     * fileAsset metadata For example, tiff:ImageLength = "height" for image
     * files, so we return {"tiff:ImageLength", "height"} and both metadata are
     * written to our metadata field
     */
    private [CtArrayTypeReferenceImpl]java.lang.String[] translateKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] x = [CtInvocationImpl][CtInvocationImpl]getTranslationMap().get([CtVariableReadImpl]key);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]x == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]x = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.StringUtils.camelCaseLower([CtVariableReadImpl]key) };
        }
        [CtReturnImpl]return [CtVariableReadImpl]x;
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]> translateMeta = [CtLiteralImpl]null;

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]> getTranslationMap() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]translateMeta == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtInvocationImpl][CtLiteralImpl]"translateMeta".intern()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]translateMeta == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]translateMeta = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
                    [CtInvocationImpl][CtFieldReadImpl]translateMeta.put([CtLiteralImpl]"tiff:ImageWidth", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"tiff:ImageWidth", [CtLiteralImpl]"width" });
                    [CtInvocationImpl][CtFieldReadImpl]translateMeta.put([CtLiteralImpl]"tiff:ImageLength", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"tiff:ImageLength", [CtLiteralImpl]"height" });
                }
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]translateMeta;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isZeroByteFileException([CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable exception) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]exception) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exception.getClass().getCanonicalName().equals([CtTypeAccessImpl]TikaProxyService.EXCEPTION_ZERO_BYTE_FILE_EXCEPTION);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void logWarning([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable exception) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.warn([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not parse file metadata for file [%s] [%s]", [CtInvocationImpl][CtVariableReadImpl]binFile.getAbsolutePath(), [CtInvocationImpl][CtVariableReadImpl]exception.getMessage()));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void logError([CtParameterImpl]final [CtTypeReferenceImpl]java.io.File binFile, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable exception) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Logger.error([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Could not parse file metadata for file [%s] [%s]", [CtInvocationImpl][CtVariableReadImpl]binFile.getAbsolutePath(), [CtInvocationImpl][CtVariableReadImpl]exception.getMessage()), [CtVariableReadImpl]exception);
    }
}
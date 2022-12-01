[CompilationUnitImpl][CtPackageDeclarationImpl]package org.greenplum.pxf.plugins.hdfs;
[CtUnresolvedImport]import org.greenplum.pxf.api.utilities.Utilities;
[CtUnresolvedImport]import org.apache.commons.lang.StringUtils;
[CtUnresolvedImport]import org.apache.hadoop.mapreduce.MRJobConfig;
[CtUnresolvedImport]import org.apache.hadoop.fs.FileSystem;
[CtUnresolvedImport]import org.apache.hadoop.conf.Configuration;
[CtUnresolvedImport]import static org.apache.hadoop.fs.FileSystem.FS_DEFAULT_NAME_KEY;
[CtUnresolvedImport]import org.apache.parquet.hadoop.metadata.CompressionCodecName;
[CtUnresolvedImport]import org.greenplum.pxf.api.model.RequestContext;
[CtImportImpl]import java.net.URI;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtImportImpl]import java.util.Arrays;
[CtEnumImpl]public enum HcfsType {

    [CtEnumValueImpl]ADL,
    [CtEnumValueImpl]CUSTOM()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getDataUri([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String profileScheme = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtInvocationImpl][CtVariableReadImpl]context.getProfileScheme())) ? [CtLiteralImpl]"" : [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]context.getProfileScheme() + [CtLiteralImpl]"://";
            [CtReturnImpl]return [CtInvocationImpl]getDataUriForPrefix([CtVariableReadImpl]configuration, [CtVariableReadImpl]context, [CtVariableReadImpl]profileScheme);
        }
    },
    [CtEnumValueImpl]FILE()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]java.lang.String validateAndNormalizeBasePath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String basePath) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]basePath))[CtBlockImpl]
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"the '%1$s' configuration is required to access locally mounted file systems. Configure a valid '%1$s' property to access this server", [CtFieldReadImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.CONFIG_KEY_BASE_PATH));

            [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtLiteralImpl]"/".equals([CtVariableReadImpl]basePath) ? [CtLiteralImpl]"/" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"/" + [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeEnd([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeStart([CtVariableReadImpl]basePath, [CtLiteralImpl]"/"), [CtLiteralImpl]"/")) + [CtLiteralImpl]"/";
        }
    },
    [CtEnumValueImpl]GS,
    [CtEnumValueImpl]HDFS,
    [CtEnumValueImpl][CtAnnotationImpl]@java.lang.Deprecated
    [CtCommentImpl]// LOCALFILE is deprecated and it will be removed in version 6.0.0 of PXF
    LOCALFILE([CtLiteralImpl]"file")[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String validateAndNormalizeDataSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dataSource) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]dataSource;
        }
    },
    [CtEnumValueImpl]S3,
    [CtEnumValueImpl]S3A,
    [CtEnumValueImpl]S3N,
    [CtEnumValueImpl][CtCommentImpl]// We prefer WASBS over WASB for Azure Blob Storage,
    [CtCommentImpl]// as it uses SSL for communication to Azure servers
    WASBS;
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String CONFIG_KEY_BASE_PATH = [CtLiteralImpl]"pxf.fs.basePath";

    [CtFieldImpl]protected [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtInvocationImpl][CtThisAccessImpl]this.getClass());

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FILE_SCHEME = [CtLiteralImpl]"file";

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.lang.String prefix;

    [CtConstructorImpl]HcfsType() [CtBlockImpl]{
        [CtInvocationImpl]this([CtLiteralImpl]null);
    }

    [CtConstructorImpl]HcfsType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.prefix = [CtBinaryOperatorImpl][CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]prefix != [CtLiteralImpl]null ? [CtVariableReadImpl]prefix : [CtInvocationImpl][CtInvocationImpl]name().toLowerCase()) + [CtLiteralImpl]"://";
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.greenplum.pxf.plugins.hdfs.HcfsType fromString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtInvocationImpl][CtTypeAccessImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.values()).filter([CtLambdaImpl]([CtParameterImpl]org.greenplum.pxf.plugins.hdfs.HcfsType v) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]v.name().equals([CtVariableReadImpl]s)).findFirst().orElse([CtFieldReadImpl][CtTypeAccessImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.[CtFieldReferenceImpl]CUSTOM);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the type of filesystem being accesses
     * Profile will override the default filesystem configured
     *
     * @param context
     * 		The input data parameters
     * @return an absolute data path
     */
    public static [CtTypeReferenceImpl]org.greenplum.pxf.plugins.hdfs.HcfsType getHcfsType([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scheme = [CtInvocationImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.getScheme([CtVariableReadImpl]configuration, [CtVariableReadImpl]context);
        [CtLocalVariableImpl][CtCommentImpl]// now we have scheme, resolve to enum
        [CtTypeReferenceImpl]org.greenplum.pxf.plugins.hdfs.HcfsType type = [CtInvocationImpl][CtTypeAccessImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.fromString([CtInvocationImpl][CtVariableReadImpl]scheme.toUpperCase());
        [CtInvocationImpl][CtCommentImpl]// disableSecureTokenRenewal for this configuration if non-secure
        [CtVariableReadImpl]type.getDataUriForPrefix([CtVariableReadImpl]configuration, [CtLiteralImpl]"/", [CtVariableReadImpl]scheme);
        [CtReturnImpl]return [CtVariableReadImpl]type;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getScheme([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// if defaultFs is defined and not file://, it takes precedence over protocol
        [CtTypeReferenceImpl]java.lang.String schemeFromContext = [CtInvocationImpl][CtVariableReadImpl]context.getProfileScheme();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI defaultFS = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.fs.FileSystem.getDefaultUri([CtVariableReadImpl]configuration);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String defaultFSScheme = [CtInvocationImpl][CtVariableReadImpl]defaultFS.getScheme();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]defaultFSScheme)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"No scheme for property %s=%s", [CtTypeAccessImpl]org.greenplum.pxf.plugins.hdfs.FS_DEFAULT_NAME_KEY, [CtVariableReadImpl]defaultFS));
        }
        [CtIfImpl][CtCommentImpl]// protocol from RequestContext will take precedence over defaultFS
        if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isNotBlank([CtVariableReadImpl]schemeFromContext)) [CtBlockImpl]{
            [CtInvocationImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.checkForConfigurationMismatch([CtVariableReadImpl]defaultFSScheme, [CtVariableReadImpl]schemeFromContext);
            [CtReturnImpl]return [CtVariableReadImpl]schemeFromContext;
        }
        [CtReturnImpl]return [CtVariableReadImpl]defaultFSScheme;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void checkForConfigurationMismatch([CtParameterImpl][CtTypeReferenceImpl]java.lang.String defaultFSScheme, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String schemeFromContext) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// do not allow protocol mismatch, unless defaultFs has file:// scheme
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.FILE_SCHEME.equals([CtVariableReadImpl]defaultFSScheme)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.equalsIgnoreCase([CtVariableReadImpl]defaultFSScheme, [CtVariableReadImpl]schemeFromContext))) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"profile protocol (%s) is not compatible with server filesystem (%s)", [CtVariableReadImpl]schemeFromContext, [CtVariableReadImpl]defaultFSScheme));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a unique fully resolved URI including the protocol for write.
     * The filename is generated with the transaction and segment IDs resulting
     * in <TRANSACTION-ID>_<SEGMENT-ID>. If a COMPRESSION_CODEC is provided, the
     * default codec extension will be appended to the name of the file.
     *
     * @param configuration
     * 		The hadoop configurations
     * @param context
     * 		The input data parameters
     * @return an absolute data path for write
     */
    public [CtTypeReferenceImpl]java.lang.String getUriForWrite([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getUriForWrite([CtVariableReadImpl]configuration, [CtVariableReadImpl]context, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a unique fully resolved URI including the protocol for write.
     * The filename is generated with the transaction and segment IDs resulting
     * in <TRANSACTION-ID>_<SEGMENT-ID>. If a COMPRESSION_CODEC is provided and
     * the skipCodedExtension parameter is false, the default codec extension
     * will be appended to the name of the file.
     *
     * @param configuration
     * 		the hadoop configurations
     * @param context
     * 		the input data parameters
     * @param skipCodecExtension
     * 		true if the codec extension is not desired, false otherwise
     * @return an absolute data path for write
     */
    public [CtTypeReferenceImpl]java.lang.String getUriForWrite([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context, [CtParameterImpl][CtTypeReferenceImpl]boolean skipCodecExtension) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fileName = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s/%s_%d", [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeEnd([CtInvocationImpl]getDataUri([CtVariableReadImpl]configuration, [CtVariableReadImpl]context), [CtLiteralImpl]"/"), [CtInvocationImpl][CtVariableReadImpl]context.getTransactionId(), [CtInvocationImpl][CtVariableReadImpl]context.getSegmentId());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]skipCodecExtension) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String compressCodec = [CtInvocationImpl][CtVariableReadImpl]context.getOption([CtLiteralImpl]"COMPRESSION_CODEC");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]compressCodec != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// get compression codec default extension
                [CtTypeReferenceImpl]org.greenplum.pxf.plugins.hdfs.CodecFactory codecFactory = [CtInvocationImpl][CtTypeAccessImpl]org.greenplum.pxf.plugins.hdfs.CodecFactory.getInstance();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String extension;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]extension = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]codecFactory.getCodec([CtVariableReadImpl]compressCodec, [CtVariableReadImpl]configuration).getDefaultExtension();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]LOG.debug([CtLiteralImpl]"Unable to get extension for codec '{}'", [CtVariableReadImpl]compressCodec);
                    [CtAssignmentImpl][CtVariableWriteImpl]extension = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]codecFactory.getCodec([CtVariableReadImpl]compressCodec, [CtTypeAccessImpl]CompressionCodecName.UNCOMPRESSED).getExtension();
                }
                [CtOperatorAssignmentImpl][CtCommentImpl]// append codec extension to the filename
                [CtVariableWriteImpl]fileName += [CtVariableReadImpl]extension;
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]LOG.debug([CtLiteralImpl]"File name for write: {}", [CtVariableReadImpl]fileName);
        [CtReturnImpl]return [CtVariableReadImpl]fileName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a fully resolved path include protocol
     *
     * @param context
     * 		The input data parameters
     * @return an absolute data path
     */
    public [CtTypeReferenceImpl]java.lang.String getDataUri([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDataUriForPrefix([CtVariableReadImpl]configuration, [CtVariableReadImpl]context, [CtFieldReadImpl][CtThisAccessImpl]this.prefix);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a fully resolved path include protocol
     *
     * @param path
     * 		The path to file
     * @return an absolute data path
     */
    public [CtTypeReferenceImpl]java.lang.String getDataUri([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDataUriForPrefix([CtVariableReadImpl]configuration, [CtVariableReadImpl]path, [CtFieldReadImpl][CtThisAccessImpl]this.prefix);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the normalized data source for the given protocol
     *
     * @param dataSource
     * 		The path to the data source
     * @return the normalized path to the data source
     */
    public [CtTypeReferenceImpl]java.lang.String validateAndNormalizeDataSource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String dataSource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String effectiveDataSource = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeStart([CtVariableReadImpl]dataSource, [CtLiteralImpl]"/");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtLiteralImpl]"..".equals([CtVariableReadImpl]effectiveDataSource) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.contains([CtVariableReadImpl]effectiveDataSource, [CtLiteralImpl]"../")) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.endsWith([CtVariableReadImpl]effectiveDataSource, [CtLiteralImpl]"/..")) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// Disallow relative paths
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"the provided path '%s' is invalid. Relative paths are not allowed by PXF", [CtVariableReadImpl]effectiveDataSource));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.contains([CtVariableReadImpl]effectiveDataSource, [CtLiteralImpl]"$")) [CtBlockImpl]{
            [CtThrowImpl][CtCommentImpl]// Disallow $ to prevent users to access environment variables
            throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"the provided path '%s' is invalid. The dollar sign character ($) is not allowed by PXF", [CtVariableReadImpl]effectiveDataSource));
        }
        [CtReturnImpl]return [CtVariableReadImpl]effectiveDataSource;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String getDataUriForPrefix([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]org.greenplum.pxf.api.model.RequestContext context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scheme) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getDataUriForPrefix([CtVariableReadImpl]configuration, [CtInvocationImpl][CtVariableReadImpl]context.getDataSource(), [CtVariableReadImpl]scheme);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String getDataUriForPrefix([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dataSource, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String scheme) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI defaultFS = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.fs.FileSystem.getDefaultUri([CtVariableReadImpl]configuration);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uri;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String basePath = [CtInvocationImpl]validateAndNormalizeBasePath([CtInvocationImpl][CtVariableReadImpl]configuration.get([CtFieldReadImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.CONFIG_KEY_BASE_PATH));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String normalizedDataSource = [CtInvocationImpl]validateAndNormalizeDataSource([CtVariableReadImpl]dataSource);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]org.greenplum.pxf.plugins.hdfs.HcfsType.FILE_SCHEME.equals([CtInvocationImpl][CtVariableReadImpl]defaultFS.getScheme())) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// if the defaultFS is file://, but enum is not FILE, use enum scheme only
            [CtVariableWriteImpl]uri = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeEnd([CtVariableReadImpl]scheme, [CtLiteralImpl]"://") + [CtLiteralImpl]"://") + [CtVariableReadImpl]basePath) + [CtVariableReadImpl]normalizedDataSource;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// if the defaultFS is not file://, use it, instead of enum scheme and append user's path
            [CtVariableWriteImpl]uri = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeEnd([CtInvocationImpl][CtVariableReadImpl]defaultFS.toString(), [CtLiteralImpl]"/") + [CtVariableReadImpl]basePath) + [CtLiteralImpl]"/") + [CtVariableReadImpl]normalizedDataSource;
        }
        [CtInvocationImpl]disableSecureTokenRenewal([CtVariableReadImpl]uri, [CtVariableReadImpl]configuration);
        [CtReturnImpl]return [CtVariableReadImpl]uri;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validates the basePath and normalizes it for the appropriate filesystem
     *
     * @param basePath
     * 		the basePath as configured by the user
     * @return the normalized basePath
     */
    protected [CtTypeReferenceImpl]java.lang.String validateAndNormalizeBasePath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String basePath) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]basePath) ? [CtLiteralImpl][CtCommentImpl]// Return an empty string to prevent "null" in the string concatenation
        "" : [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeEnd([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.removeStart([CtVariableReadImpl]basePath, [CtLiteralImpl]"/"), [CtLiteralImpl]"/") + [CtLiteralImpl]"/";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * For secured cluster, circumvent token renewal for non-HDFS hcfs access (such as s3 etc)
     *
     * @param uri
     * 		URI of the resource to access
     * @param configuration
     * 		configuration used for HCFS operations
     */
    protected [CtTypeReferenceImpl]void disableSecureTokenRenewal([CtParameterImpl][CtTypeReferenceImpl]java.lang.String uri, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.conf.Configuration configuration) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.greenplum.pxf.api.utilities.Utilities.isSecurityEnabled([CtVariableReadImpl]configuration) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang.StringUtils.isBlank([CtVariableReadImpl]uri))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// find the "host" that TokenCache will check against the exclusion list, for cloud file systems (like S3)
        [CtCommentImpl]// it might actually be a bucket in the full resource path
        [CtTypeReferenceImpl]java.lang.String host = [CtInvocationImpl][CtTypeAccessImpl]org.greenplum.pxf.api.utilities.Utilities.getHost([CtVariableReadImpl]uri);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]host != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]LOG.debug([CtLiteralImpl]"Disabling token renewal for host {} for path {}", [CtVariableReadImpl]host, [CtVariableReadImpl]uri);
            [CtInvocationImpl][CtCommentImpl]// disable token renewal for the "host" in the path
            [CtVariableReadImpl]configuration.set([CtTypeAccessImpl]MRJobConfig.JOB_NAMENODES_TOKEN_RENEWAL_EXCLUDE, [CtVariableReadImpl]host);
        }
    }
}
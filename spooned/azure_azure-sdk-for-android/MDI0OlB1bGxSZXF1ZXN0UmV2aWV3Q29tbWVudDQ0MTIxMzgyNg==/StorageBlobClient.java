[CompilationUnitImpl][CtPackageDeclarationImpl]package com.azure.android.storage.blob;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.CpkInfo;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.ListBlobsOptions;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobHttpHeaders;
[CtUnresolvedImport]import com.azure.android.core.http.Callback;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobRequestConditions;
[CtUnresolvedImport]import com.azure.android.core.internal.util.serializer.SerializerFormat;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobItem;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlockBlobItem;
[CtUnresolvedImport]import com.azure.android.core.http.interceptor.AddDateInterceptor;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlockBlobsCommitBlockListResponse;
[CtUnresolvedImport]import org.threeten.bp.OffsetDateTime;
[CtUnresolvedImport]import com.azure.android.core.http.ServiceClient;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import okhttp3.ResponseBody;
[CtUnresolvedImport]import okhttp3.Interceptor;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.DeleteSnapshotsOptionType;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.ListBlobsIncludeItem;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.AccessTier;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobDownloadAsyncResponse;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobGetPropertiesHeaders;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobRange;
[CtUnresolvedImport]import com.azure.android.core.http.ServiceCall;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.ContainersListBlobFlatSegmentResponse;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobGetPropertiesResponse;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlockBlobsStageBlockResponse;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import com.azure.android.storage.blob.models.BlobDeleteResponse;
[CtImportImpl]import java.util.Map;
[CtClassImpl][CtJavaDocImpl]/**
 * Client for Storage Blob service.
 */
public class StorageBlobClient {
    [CtFieldImpl]private final [CtTypeReferenceImpl]com.azure.android.core.http.ServiceClient serviceClient;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobServiceImpl storageBlobServiceClient;

    [CtConstructorImpl]private StorageBlobClient([CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.ServiceClient serviceClient) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceClient = [CtVariableReadImpl]serviceClient;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.storageBlobServiceClient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobServiceImpl([CtFieldReadImpl][CtThisAccessImpl]this.serviceClient);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a new {@link Builder} with initial configuration copied from this {@link StorageBlobClient}.
     *
     * @return A new {@link Builder}.
     */
    public [CtTypeReferenceImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient.Builder newBuilder() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient.Builder([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the blob service base URL.
     *
     * @return The blob service base URL.
     */
    public [CtTypeReferenceImpl]java.lang.String getBlobServiceUrl() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.serviceClient.getBaseUrl();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of blobs identified by a page id in a given container.
     *
     * @param pageId
     * 		Identifies the portion of the list to be returned.
     * @param containerName
     * 		The container name.
     * @param options
     * 		The page options.
     * @return A list of blobs.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobItem> getBlobsInPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.ListBlobsOptions options) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.getBlobsInPage([CtVariableReadImpl]pageId, [CtVariableReadImpl]containerName, [CtVariableReadImpl]options);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of blobs identified by a page id in a given container.
     *
     * @param pageId
     * 		Identifies the portion of the list to be returned.
     * @param containerName
     * 		The container name.
     * @param options
     * 		The page options.
     * @param callback
     * 		Callback that receives the retrieved blob list.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall getBlobsInPage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.ListBlobsOptions options, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobItem>> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.getBlobsInPage([CtVariableReadImpl]pageId, [CtVariableReadImpl]containerName, [CtVariableReadImpl]options, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of blobs identified by a page id in a given container.
     *
     * @param pageId
     * 		Identifies the portion of the list to be returned.
     * @param containerName
     * 		The container name.
     * @param prefix
     * 		Filters the results to return only blobs whose name begins with the specified prefix.
     * @param maxResults
     * 		Specifies the maximum number of blobs to return.
     * @param include
     * 		Include this parameter to specify one or more datasets to include in the response.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is recorded in
     * 		the analytics logs when storage analytics logging is enabled.
     * @return A response object containing a list of blobs.
     */
    public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.ContainersListBlobFlatSegmentResponse getBlobsInPageWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer maxResults, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.ListBlobsIncludeItem> include, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.getBlobsInPageWithRestResponse([CtVariableReadImpl]pageId, [CtVariableReadImpl]containerName, [CtVariableReadImpl]prefix, [CtVariableReadImpl]maxResults, [CtVariableReadImpl]include, [CtVariableReadImpl]timeout, [CtVariableReadImpl]requestId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of blobs identified by a page id in a given container.
     *
     * @param pageId
     * 		Identifies the portion of the list to be returned.
     * @param containerName
     * 		The container name.
     * @param prefix
     * 		Filters the results to return only blobs whose name begins with the specified prefix.
     * @param maxResults
     * 		Specifies the maximum number of blobs to return.
     * @param include
     * 		Include this parameter to specify one or more datasets to include in the response.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is recorded in
     * 		the analytics logs when storage analytics logging is enabled.
     * @param callback
     * 		Callback that receives the response.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall getBlobsInPageWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String pageId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer maxResults, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.ListBlobsIncludeItem> include, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.ContainersListBlobFlatSegmentResponse> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.getBlobsInPageWithRestResponse([CtVariableReadImpl]pageId, [CtVariableReadImpl]containerName, [CtVariableReadImpl]prefix, [CtVariableReadImpl]maxResults, [CtVariableReadImpl]include, [CtVariableReadImpl]timeout, [CtVariableReadImpl]requestId, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     */
    public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobGetPropertiesHeaders getBlobProperties([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.getBlobProperties([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param callback
     * 		Callback that receives the response.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall getBlobProperties([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobGetPropertiesHeaders> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.getBlobProperties([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads a blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies
     * 		the blob snapshot to retrieve. For more information on working with blob snapshots,
     * 		see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param version
     * 		Specifies the version of the operation to use for this request.
     * @param blobRequestConditions
     * 		Object that contains values which will restrict the successful operation of a
     * 		variety of requests to the conditions present. These conditions are entirely
     * 		optional.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     * @param cpkInfo
     * 		Additional parameters for the operation.
     * @return The response information returned from the server when downloading a blob.
     */
    public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobGetPropertiesResponse getBlobPropertiesWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions blobRequestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]blobRequestConditions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]blobRequestConditions == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions() : [CtVariableReadImpl]blobRequestConditions;
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.getBlobPropertiesWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtVariableReadImpl]version, [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getLeaseId(), [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads a blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies
     * 		the blob snapshot to retrieve. For more information on working with blob snapshots,
     * 		see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param version
     * 		Specifies the version of the operation to use for this request.
     * @param blobRequestConditions
     * 		Object that contains values which will restrict the successful operation of a
     * 		variety of requests to the conditions present. These conditions are entirely
     * 		optional.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     * @param cpkInfo
     * 		Additional parameters for the operation.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall getBlobPropertiesWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions blobRequestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobGetPropertiesResponse> callback) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]blobRequestConditions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]blobRequestConditions == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions() : [CtVariableReadImpl]blobRequestConditions;
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.getBlobPropertiesWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtVariableReadImpl]version, [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getLeaseId(), [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the entire blob.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     */
    public [CtTypeReferenceImpl]okhttp3.ResponseBody download([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.download([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the entire blob.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param callback
     * 		Callback that receives the response.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall download([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]okhttp3.ResponseBody> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.download([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads a range of bytes from a blob.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies
     * 		the blob snapshot to retrieve. For more information on working with blob snapshots,
     * 		see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param range
     * 		Return only the bytes of the blob in the specified range.
     * @param blobRequestConditions
     * 		Object that contains values which will restrict the successful operation of a
     * 		variety of requests to the conditions present. These conditions are entirely
     * 		optional.
     * @param getRangeContentMd5
     * 		When set to true and specified together with the Range, the service returns the
     * 		MD5 hash for the range, as long as the range is less than or equal to 4 MB in size.
     * @param getRangeContentCrc64
     * 		When set to true and specified together with the Range, the service returns the
     * 		CRC64 hash for the range, as long as the range is less than or equal to 4 MB in size.
     * @param version
     * 		Specifies the version of the operation to use for this request.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     * @param cpkInfo
     * 		Additional parameters for the operation.
     * @return The response information returned from the server when downloading a blob.
     */
    public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobDownloadAsyncResponse downloadWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRange range, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions blobRequestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean getRangeContentMd5, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean getRangeContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]range = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]range == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRange([CtLiteralImpl]0) : [CtVariableReadImpl]range;
        [CtAssignmentImpl][CtVariableWriteImpl]blobRequestConditions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]blobRequestConditions == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions() : [CtVariableReadImpl]blobRequestConditions;
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.downloadWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtInvocationImpl][CtVariableReadImpl]range.toHeaderValue(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getLeaseId(), [CtVariableReadImpl]getRangeContentMd5, [CtVariableReadImpl]getRangeContentCrc64, [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfModifiedSince(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfUnmodifiedSince(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfMatch(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfNoneMatch(), [CtVariableReadImpl]version, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads a range of bytes from a blob.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies
     * 		the blob snapshot to retrieve. For more information on working with blob snapshots,
     * 		see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param range
     * 		Return only the bytes of the blob in the specified range.
     * @param blobRequestConditions
     * 		Object that contains values which will restrict the successful operation of a
     * 		variety of requests to the conditions present. These conditions are entirely optional.
     * @param getRangeContentMd5
     * 		When set to true and specified together with the Range, the service returns the
     * 		MD5 hash for the range, as long as the range is less than or equal to 4 MB in size.
     * @param getRangeContentCrc64
     * 		When set to true and specified together with the Range, the service returns the
     * 		CRC64 hash for the range, as long as the range is less than or equal to 4 MB in size.
     * @param version
     * 		Specifies the version of the operation to use for this request.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     * @param cpkInfo
     * 		Additional parameters for the operation.
     */
    public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall downloadWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRange range, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions blobRequestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean getRangeContentMd5, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean getRangeContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobDownloadAsyncResponse> callback) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]range = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]range == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRange([CtLiteralImpl]0) : [CtVariableReadImpl]range;
        [CtAssignmentImpl][CtVariableWriteImpl]blobRequestConditions = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]blobRequestConditions == [CtLiteralImpl]null) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions() : [CtVariableReadImpl]blobRequestConditions;
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.downloadWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtInvocationImpl][CtVariableReadImpl]range.toHeaderValue(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getLeaseId(), [CtVariableReadImpl]getRangeContentMd5, [CtVariableReadImpl]getRangeContentCrc64, [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfModifiedSince(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfUnmodifiedSince(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfMatch(), [CtInvocationImpl][CtVariableReadImpl]blobRequestConditions.getIfNoneMatch(), [CtVariableReadImpl]version, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Void stageBlock([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String base64BlockId, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] blockContent, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] contentMd5) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.stageBlock([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockId, [CtVariableReadImpl]blockContent, [CtVariableReadImpl]contentMd5);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall stageBlock([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String base64BlockId, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] blockContent, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] contentMd5, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]java.lang.Void> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.stageBlock([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockId, [CtVariableReadImpl]blockContent, [CtVariableReadImpl]contentMd5, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobsStageBlockResponse stageBlockWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String base64BlockId, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] body, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentMD5, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String leaseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.stageBlockWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockId, [CtVariableReadImpl]body, [CtVariableReadImpl]transactionalContentMD5, [CtVariableReadImpl]transactionalContentCrc64, [CtVariableReadImpl]timeout, [CtVariableReadImpl]leaseId, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall stageBlockWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String base64BlockId, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] body, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentMD5, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String leaseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobsStageBlockResponse> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.stageBlockWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockId, [CtVariableReadImpl]body, [CtVariableReadImpl]transactionalContentMD5, [CtVariableReadImpl]transactionalContentCrc64, [CtVariableReadImpl]timeout, [CtVariableReadImpl]leaseId, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobItem commitBlockList([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> base64BlockIds, [CtParameterImpl][CtTypeReferenceImpl]boolean overwrite) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.commitBlockList([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockIds, [CtVariableReadImpl]overwrite);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall commitBlockList([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> base64BlockIds, [CtParameterImpl][CtTypeReferenceImpl]boolean overwrite, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobItem> callBack) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.commitBlockList([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockIds, [CtVariableReadImpl]overwrite, [CtVariableReadImpl]callBack);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobsCommitBlockListResponse commitBlockListWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> base64BlockIds, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentMD5, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobHttpHeaders blobHttpHeaders, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metadata, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions requestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.AccessTier tier) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.commitBlockListWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockIds, [CtVariableReadImpl]transactionalContentMD5, [CtVariableReadImpl]transactionalContentCrc64, [CtVariableReadImpl]timeout, [CtVariableReadImpl]blobHttpHeaders, [CtVariableReadImpl]metadata, [CtVariableReadImpl]requestConditions, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo, [CtVariableReadImpl]tier);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall commitBlockListWithRestResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> base64BlockIds, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentMD5, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] transactionalContentCrc64, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobHttpHeaders blobHttpHeaders, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metadata, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobRequestConditions requestConditions, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.CpkInfo cpkInfo, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.AccessTier tier, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlockBlobsCommitBlockListResponse> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.storageBlobServiceClient.commitBlockListWithRestResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]base64BlockIds, [CtVariableReadImpl]transactionalContentMD5, [CtVariableReadImpl]transactionalContentCrc64, [CtVariableReadImpl]timeout, [CtVariableReadImpl]blobHttpHeaders, [CtVariableReadImpl]metadata, [CtVariableReadImpl]requestConditions, [CtVariableReadImpl]requestId, [CtVariableReadImpl]cpkInfo, [CtVariableReadImpl]tier, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @return The blob's metadata.
     */
    [CtTypeReferenceImpl]java.lang.Void delete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.delete([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the blob's metadata & properties.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param callback
     * 		Callback that receives the response.
     */
    [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall delete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]java.lang.Void> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.delete([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]callback);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If the storage account's soft delete feature is disabled then, when a blob is deleted, it is permanently
     * removed from the storage account. If the storage account's soft delete feature is enabled, then, when a blob
     * is deleted, it is marked for deletion and becomes inaccessible immediately. However, the blob service retains
     * the blob or snapshot for the number of days specified by the DeleteRetentionPolicy section of [Storage service
     * properties] (Set-Blob-Service-Properties.md). After the specified number of days has passed, the blob's data
     * is permanently removed from the storage account. Note that you continue to be charged for the soft-deleted
     * blob's storage until it is permanently removed. Use the List Blobs API and specify the "include=deleted" query
     * parameter to discover which blobs and snapshots have been soft deleted. You can then use the Undelete Blob API
     * to restore a soft-deleted blob. All other operations on a soft-deleted blob or snapshot causes the service to
     * return an HTTP status code of 404 (ResourceNotFound). If the storage account's automatic snapshot feature is
     * enabled, then, when a blob is deleted, an automatic snapshot is created. The blob becomes inaccessible
     * immediately. All other operations on the blob causes the service to return an HTTP status code of 404
     * (ResourceNotFound). You can access automatic snapshot using snapshot timestamp or version id. You can restore
     * the blob by calling Put or Copy Blob API with automatic snapshot as source. Deleting automatic snapshot
     * requires shared key or special SAS/RBAC permissions.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies the
     * 		blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId
     * 		If specified, the operation only succeeds if the resource's lease is active and
     * 		matches this ID.
     * @param deleteSnapshots
     * 		Required if the blob has associated snapshots. Specify one of the following two
     * 		options: include: Delete the base blob and all of its snapshots. only: Delete only the blob's snapshots and not the blob itself. Possible values include: 'include', 'only'.
     * @param ifModifiedSince
     * 		Specify this header value to operate only on a blob if it has been modified since the
     * 		specified date/time.
     * @param ifUnmodifiedSince
     * 		Specify this header value to operate only on a blob if it has not been modified since
     * 		the specified date/time.
     * @param ifMatch
     * 		Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch
     * 		Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     */
    [CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobDeleteResponse deleteWithResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String leaseId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.DeleteSnapshotsOptionType deleteSnapshots, [CtParameterImpl][CtTypeReferenceImpl]org.threeten.bp.OffsetDateTime ifModifiedSince, [CtParameterImpl][CtTypeReferenceImpl]org.threeten.bp.OffsetDateTime ifUnmodifiedSince, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ifMatch, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ifNoneMatch, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.deleteWithResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtVariableReadImpl]version, [CtVariableReadImpl]leaseId, [CtVariableReadImpl]deleteSnapshots, [CtVariableReadImpl]ifModifiedSince, [CtVariableReadImpl]ifUnmodifiedSince, [CtVariableReadImpl]ifMatch, [CtVariableReadImpl]ifNoneMatch, [CtVariableReadImpl]requestId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If the storage account's soft delete feature is disabled then, when a blob is deleted, it is permanently
     * removed from the storage account. If the storage account's soft delete feature is enabled, then, when a blob
     * is deleted, it is marked for deletion and becomes inaccessible immediately. However, the blob service retains
     * the blob or snapshot for the number of days specified by the DeleteRetentionPolicy section of [Storage service
     * properties] (Set-Blob-Service-Properties.md). After the specified number of days has passed, the blob's data
     * is permanently removed from the storage account. Note that you continue to be charged for the soft-deleted
     * blob's storage until it is permanently removed. Use the List Blobs API and specify the "include=deleted" query
     * parameter to discover which blobs and snapshots have been soft deleted. You can then use the Undelete Blob API
     * to restore a soft-deleted blob. All other operations on a soft-deleted blob or snapshot causes the service to
     * return an HTTP status code of 404 (ResourceNotFound). If the storage account's automatic snapshot feature is
     * enabled, then, when a blob is deleted, an automatic snapshot is created. The blob becomes inaccessible
     * immediately. All other operations on the blob causes the service to return an HTTP status code of 404
     * (ResourceNotFound). You can access automatic snapshot using snapshot timestamp or version id. You can restore
     * the blob by calling Put or Copy Blob API with automatic snapshot as source. Deleting automatic snapshot
     * requires shared key or special SAS/RBAC permissions.
     *
     * @param containerName
     * 		The container name.
     * @param blobName
     * 		The blob name.
     * @param snapshot
     * 		The snapshot parameter is an opaque DateTime value that, when present, specifies the
     * 		blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout
     * 		The timeout parameter is expressed in seconds. For more information, see
     * 		&lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId
     * 		If specified, the operation only succeeds if the resource's lease is active and
     * 		matches this ID.
     * @param deleteSnapshots
     * 		Required if the blob has associated snapshots. Specify one of the following two
     * 		options: include: Delete the base blob and all of its snapshots. only: Delete only the blob's snapshots and not the blob itself. Possible values include: 'include', 'only'.
     * @param ifModifiedSince
     * 		Specify this header value to operate only on a blob if it has been modified since the
     * 		specified date/time.
     * @param ifUnmodifiedSince
     * 		Specify this header value to operate only on a blob if it has not been modified since
     * 		the specified date/time.
     * @param ifMatch
     * 		Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch
     * 		Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId
     * 		Provides a client-generated, opaque value with a 1 KB character limit that is
     * 		recorded in the analytics logs when storage analytics logging is enabled.
     * @param callback
     * 		Callback that receives the response.
     */
    [CtTypeReferenceImpl]com.azure.android.core.http.ServiceCall deleteWithResponse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String containerName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String snapshot, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer timeout, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String leaseId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.storage.blob.models.DeleteSnapshotsOptionType deleteSnapshots, [CtParameterImpl][CtTypeReferenceImpl]org.threeten.bp.OffsetDateTime ifModifiedSince, [CtParameterImpl][CtTypeReferenceImpl]org.threeten.bp.OffsetDateTime ifUnmodifiedSince, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ifMatch, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String ifNoneMatch, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String requestId, [CtParameterImpl][CtTypeReferenceImpl]com.azure.android.core.http.Callback<[CtTypeReferenceImpl]com.azure.android.storage.blob.models.BlobDeleteResponse> callback) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]storageBlobServiceClient.deleteWithResponse([CtVariableReadImpl]containerName, [CtVariableReadImpl]blobName, [CtVariableReadImpl]snapshot, [CtVariableReadImpl]timeout, [CtVariableReadImpl]version, [CtVariableReadImpl]leaseId, [CtVariableReadImpl]deleteSnapshots, [CtVariableReadImpl]ifModifiedSince, [CtVariableReadImpl]ifUnmodifiedSince, [CtVariableReadImpl]ifMatch, [CtVariableReadImpl]ifNoneMatch, [CtVariableReadImpl]requestId, [CtVariableReadImpl]callback);
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Builder for {@link StorageBlobClient}.
     */
    public static class Builder {
        [CtFieldImpl]private final [CtTypeReferenceImpl]ServiceClient.Builder serviceClientBuilder;

        [CtConstructorImpl][CtJavaDocImpl]/**
         * Creates a {@link Builder}.
         */
        public Builder() [CtBlockImpl]{
            [CtInvocationImpl]this([CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.azure.android.core.http.ServiceClient.Builder());
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.serviceClientBuilder.addInterceptor([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.core.http.interceptor.AddDateInterceptor()).setSerializationFormat([CtTypeAccessImpl]SerializerFormat.XML);
        }

        [CtConstructorImpl][CtJavaDocImpl]/**
         * Creates a {@link Builder} that uses the provided {@link com.azure.android.core.http.ServiceClient.Builder}
         * to build a {@link ServiceClient} for the {@link StorageBlobClient}.
         *
         * @param serviceClientBuilder
         * 		The {@link com.azure.android.core.http.ServiceClient.Builder}.
         */
        public Builder([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.azure.android.core.http.ServiceClient.Builder serviceClientBuilder) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]serviceClientBuilder, [CtLiteralImpl]"serviceClientBuilder cannot be null.");
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceClientBuilder = [CtVariableReadImpl]serviceClientBuilder;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets the base URL for the {@link StorageBlobClient}.
         *
         * @param blobServiceUrl
         * 		The blob service base URL.
         * @return An updated {@link Builder} with these settings applied.
         */
        public [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient.Builder setBlobServiceUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String blobServiceUrl) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.requireNonNull([CtVariableReadImpl]blobServiceUrl, [CtLiteralImpl]"blobServiceUrl cannot be null.");
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.serviceClientBuilder.setBaseUrl([CtVariableReadImpl]blobServiceUrl);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Sets an interceptor used to authenticate the blob service request.
         *
         * @param credentialInterceptor
         * 		The credential interceptor.
         * @return An updated {@link Builder} with these settings applied.
         */
        public [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient.Builder setCredentialInterceptor([CtParameterImpl][CtTypeReferenceImpl]okhttp3.Interceptor credentialInterceptor) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.serviceClientBuilder.setCredentialsInterceptor([CtVariableReadImpl]credentialInterceptor);
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Builds a {@link StorageBlobClient} based on this {@link Builder}'s configuration.
         *
         * @return A {@link StorageBlobClient}.
         */
        public [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient build() [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.serviceClientBuilder.build());
        }

        [CtConstructorImpl]private Builder([CtParameterImpl]final [CtTypeReferenceImpl]com.azure.android.storage.blob.StorageBlobClient storageBlobClient) [CtBlockImpl]{
            [CtInvocationImpl]this([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]storageBlobClient.serviceClient.newBuilder());
        }
    }
}
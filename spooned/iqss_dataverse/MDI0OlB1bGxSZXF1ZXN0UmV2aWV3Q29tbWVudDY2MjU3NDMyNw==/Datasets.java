[CompilationUnitImpl][CtPackageDeclarationImpl]package edu.harvard.iq.dataverse.api;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
[CtUnresolvedImport]import static edu.harvard.iq.dataverse.util.json.NullSafeJsonBuilder.jsonObjectBuilder;
[CtUnresolvedImport]import javax.ws.rs.Produces;
[CtUnresolvedImport]import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.batch.util.LoggingUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datasetutility.DataFileTagException;
[CtUnresolvedImport]import org.apache.solr.client.solrj.SolrServerException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.users.User;
[CtUnresolvedImport]import javax.ws.rs.core.MediaType;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.json.JsonParseException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.MoveDatasetCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetPrivateUrlCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.MakeDataCountUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.AddLockCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetField;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.DatasetMetricsServiceBean;
[CtUnresolvedImport]import javax.json.JsonObject;
[CtUnresolvedImport]import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.MetadataBlockServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataverseServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.AbstractSubmitToArchiveCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.dataaccess.DataAccess;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataFile;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetServiceBean;
[CtUnresolvedImport]import javax.ejb.EJBException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.batch.jobs.importer.ImportMode;
[CtUnresolvedImport]import org.glassfish.jersey.media.multipart.FormDataParam;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.RemoveLockCommand;
[CtUnresolvedImport]import javax.json.JsonException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetVersion;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetSpecificPublishedDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.S3PackageImporter;
[CtImportImpl]import java.util.logging.Level;
[CtUnresolvedImport]import javax.ws.rs.core.UriInfo;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import javax.json.stream.JsonParsingException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.CreatePrivateUrlCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.RoleAssignment;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datacapturemodule.DataCaptureModuleUtil;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import javax.json.JsonArrayBuilder;
[CtUnresolvedImport]import javax.ws.rs.GET;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.DatasetExternalCitations;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.FileUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.PermissionServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.EjbDataverseEngine;
[CtImportImpl]import java.sql.Timestamp;
[CtImportImpl]import java.util.logging.Logger;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.api.dto.RoleAssignmentDTO;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataverseSession;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.DatasetExternalCitationsServiceBean;
[CtImportImpl]import java.util.Map.Entry;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.dataset.DatasetUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.BundleUtil;
[CtImportImpl]import java.io.StringReader;
[CtUnresolvedImport]import javax.json.Json;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.PublishDatasetCommand;
[CtUnresolvedImport]import javax.ejb.EJB;
[CtUnresolvedImport]import javax.json.JsonReader;
[CtUnresolvedImport]import static edu.harvard.iq.dataverse.util.json.JsonPrinter.*;
[CtUnresolvedImport]import javax.ws.rs.NotAcceptableException;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.export.ExportService;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.FileMetadata;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import javax.json.JsonArray;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.UserNotificationServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.CuratePublishedDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.makedatacount.DatasetMetrics;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.DeletePrivateUrlCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.json.JSONLDUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.UserNotification;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.dataaccess.ImageThumbConverter;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetTargetURLCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.ListVersionsCommand;
[CtUnresolvedImport]import com.amazonaws.services.s3.model.PartETag;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataFileServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
[CtUnresolvedImport]import javax.ws.rs.Path;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.RoleAssignee;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetLinkingDataverseCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.export.DDIExportServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.metrics.MetricsUtil;
[CtUnresolvedImport]import javax.ws.rs.QueryParam;
[CtUnresolvedImport]import javax.ws.rs.Consumes;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.Permission;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.LinkDatasetCommand;
[CtUnresolvedImport]import javax.ws.rs.DefaultValue;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DvObject;
[CtUnresolvedImport]import org.glassfish.jersey.media.multipart.FormDataBodyPart;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datacapturemodule.ScriptRequestResponse;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetDatasetCommand;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.DestroyDatasetCommand;
[CtUnresolvedImport]import javax.ws.rs.DELETE;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datasetutility.AddReplaceFileHelper;
[CtImportImpl]import java.util.LinkedList;
[CtImportImpl]import java.time.format.DateTimeFormatter;
[CtUnresolvedImport]import javax.ws.rs.core.Context;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetFieldValue;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.exception.UnforcedCommandException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.ListRoleAssignments;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.MetadataBlock;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.UpdateDvObjectPIDMetadataCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.dataset.DatasetThumbnail;
[CtUnresolvedImport]import javax.ws.rs.core.HttpHeaders;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.ingest.IngestServiceBean;
[CtUnresolvedImport]import javax.ws.rs.core.Response;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.ImportFromFileSystemCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.ReturnDatasetToAuthorCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.SubmitDatasetForReviewCommand;
[CtUnresolvedImport]import javax.json.JsonObjectBuilder;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datasetutility.OptionalFileParams;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetDraftDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetLock;
[CtUnresolvedImport]import javax.ws.rs.PathParam;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.SystemConfig;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.json.NullSafeJsonBuilder;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetLatestPublishedDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.EjbUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.ControlledVocabularyValue;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetFieldCompoundValue;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataverseRequestServiceBean;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.DataverseRole;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.RevokeRoleCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.datasetutility.NoFilesException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.bagit.OREMap;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.Dataverse;
[CtUnresolvedImport]import javax.ws.rs.core.Response.Status;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.Command;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetThumbnailCommand;
[CtUnresolvedImport]import javax.ws.rs.POST;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetFieldType;
[CtImportImpl]import java.text.MessageFormat;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetDatasetStorageSizeCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.search.IndexServiceBean;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.Dataset;
[CtUnresolvedImport]import javax.servlet.http.HttpServletResponse;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.GetLatestAccessibleDatasetVersionCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.AssignRoleCommand;
[CtImportImpl]import java.time.ZoneId;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.privateurl.PrivateUrl;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.RequestRsyncScriptCommand;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.PublishDatasetResult;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.impl.SetDatasetCitationDateCommand;
[CtUnresolvedImport]import javax.ws.rs.PUT;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.util.ArchiverUtil;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.dataaccess.S3AccessIO;
[CtClassImpl][CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"datasets")
public class Datasets extends [CtTypeReferenceImpl]AbstractApiBean {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.class.getCanonicalName());

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseSession session;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetServiceBean datasetService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseServiceBean dataverseService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.UserNotificationServiceBean userNotificationService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.PermissionServiceBean permissionService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean authenticationServiceBean;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.export.DDIExportServiceBean ddiExportService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.MetadataBlockServiceBean metadataBlockService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataFileServiceBean fileService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.ingest.IngestServiceBean ingestService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.EjbDataverseEngine commandEngine;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.search.IndexServiceBean indexService;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.S3PackageImporter s3PackageImporter;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.settings.SettingsServiceBean settingsService;

    [CtFieldImpl][CtCommentImpl]// TODO: Move to AbstractApiBean
    [CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.DatasetMetricsServiceBean datasetMetricsSvc;

    [CtFieldImpl][CtAnnotationImpl]@javax.ejb.EJB
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.DatasetExternalCitationsServiceBean datasetExternalCitationsService;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean mdcLogService;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseRequestServiceBean dvRequestService;

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Used to consolidate the way we parse and handle dataset versions.
     *
     * @param <T>
     */
    public interface DsVersionHandler<[CtTypeParameterImpl]T> {
        [CtMethodImpl][CtTypeParameterReferenceImpl]T handleLatest();

        [CtMethodImpl][CtTypeParameterReferenceImpl]T handleDraft();

        [CtMethodImpl][CtTypeParameterReferenceImpl]T handleSpecific([CtParameterImpl][CtTypeReferenceImpl]long major, [CtParameterImpl][CtTypeReferenceImpl]long minor);

        [CtMethodImpl][CtTypeParameterReferenceImpl]T handleLatestPublished();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset retrieved = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetDatasetCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id)));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion latest = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetLatestAccessibleDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]retrieved));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.json.JsonObjectBuilder jsonbuilder = [CtInvocationImpl]json([CtVariableReadImpl]retrieved);
            [CtIfImpl][CtCommentImpl]// Report MDC if this is a released version (could be draft if user has access, or user may not have access at all and is not getting metadata beyond the minimum)
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]latest != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]latest.isReleased()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry entry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry([CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers, [CtFieldReadImpl][CtFieldReferenceImpl]dvRequestService, [CtVariableReadImpl]retrieved);
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mdcLogService.logEntry([CtVariableReadImpl]entry);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]jsonbuilder.add([CtLiteralImpl]"latestVersion", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]latest != [CtLiteralImpl]null ? [CtInvocationImpl]json([CtVariableReadImpl]latest) : [CtLiteralImpl]null));
        });
    }

    [CtMethodImpl][CtCommentImpl]// TODO:
    [CtCommentImpl]// This API call should, ideally, call findUserOrDie() and the GetDatasetCommand
    [CtCommentImpl]// to obtain the dataset that we are trying to export - which would handle
    [CtCommentImpl]// Auth in the process... For now, Auth isn't necessary - since export ONLY
    [CtCommentImpl]// WORKS on published datasets, which are open to the world. -- L.A. 4.5
    [CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/export")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"application/xml", [CtLiteralImpl]"application/json", [CtLiteralImpl]"application/html" })
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response exportDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"persistentId")
    [CtTypeReferenceImpl]java.lang.String persistentId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"exporter")
    [CtTypeReferenceImpl]java.lang.String exporter, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl][CtFieldReadImpl]datasetService.findByGlobalId([CtVariableReadImpl]persistentId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataset == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"A dataset with the persistentId " + [CtVariableReadImpl]persistentId) + [CtLiteralImpl]" could not be found.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.export.ExportService instance = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.export.ExportService.getInstance();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtVariableReadImpl]instance.getExport([CtVariableReadImpl]dataset, [CtVariableReadImpl]exporter);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mediaType = [CtInvocationImpl][CtVariableReadImpl]instance.getMediaType([CtVariableReadImpl]exporter);
            [CtLocalVariableImpl][CtCommentImpl]// Export is only possible for released (non-draft) dataset versions so we can log without checking to see if this is a request for a draft
            [CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry entry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry([CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers, [CtFieldReadImpl]dvRequestService, [CtVariableReadImpl]dataset);
            [CtInvocationImpl][CtFieldReadImpl]mdcLogService.logEntry([CtVariableReadImpl]entry);
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok().entity([CtVariableReadImpl]is).type([CtVariableReadImpl]mediaType).build();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Export Failed");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Internally, "DeleteDatasetCommand" simply redirects to "DeleteDatasetVersionCommand"
        [CtCommentImpl]// (and there's a comment that says "TODO: remove this command")
        [CtCommentImpl]// do we need an exposed API call for it?
        [CtCommentImpl]// And DeleteDatasetVersionCommand further redirects to DestroyDatasetCommand,
        [CtCommentImpl]// if the dataset only has 1 version... In other words, the functionality
        [CtCommentImpl]// currently provided by this API is covered between the "deleteDraftVersion" and
        [CtCommentImpl]// "destroyDataset" API calls.
        [CtCommentImpl]// (The logic below follows the current implementation of the underlying
        [CtCommentImpl]// commands!)
        return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset doomed = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion doomedVersion = [CtInvocationImpl][CtVariableReadImpl]doomed.getLatestVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean destroy = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]doomed.getVersions().size() == [CtLiteralImpl]1) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doomed.isReleased() && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]u instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isSuperuser()))) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtVariableReadImpl]Response.Status.UNAUTHORIZED, [CtLiteralImpl]"Only superusers can delete published datasets"));
                }
                [CtAssignmentImpl][CtVariableWriteImpl]destroy = [CtLiteralImpl]true;
            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]doomedVersion.isDraft()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtVariableReadImpl]Response.Status.UNAUTHORIZED, [CtLiteralImpl]"This is a published dataset with multiple versions. This API can only delete the latest version if it is a DRAFT"));
            }
            [CtLocalVariableImpl][CtCommentImpl]// Gather the locations of the physical files that will need to be
            [CtCommentImpl]// deleted once the destroy command execution has been finalized:
            [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.String> deleteStorageLocations = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.getPhysicalFilesToDelete([CtVariableReadImpl]doomedVersion, [CtVariableReadImpl]destroy);
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id)));
            [CtIfImpl][CtCommentImpl]// If we have gotten this far, the destroy command has succeeded,
            [CtCommentImpl]// so we can finalize it by permanently deleting the physical files:
            [CtCommentImpl]// (DataFileService will double-check that the datafiles no
            [CtCommentImpl]// longer exist in the database, before attempting to delete
            [CtCommentImpl]// the physical files)
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]deleteStorageLocations.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.finalizeFileDeletes([CtVariableReadImpl]deleteStorageLocations);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" deleted");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/destroy")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response destroyDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// first check if dataset is released, and if so, if user is a superuser
            [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset doomed = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doomed.isReleased() && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]u instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser)) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isSuperuser()))) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtVariableReadImpl]Response.Status.UNAUTHORIZED, [CtLiteralImpl]"Destroy can only be called by superusers."));
            }
            [CtLocalVariableImpl][CtCommentImpl]// Gather the locations of the physical files that will need to be
            [CtCommentImpl]// deleted once the destroy command execution has been finalized:
            [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.String> deleteStorageLocations = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.getPhysicalFilesToDelete([CtVariableReadImpl]doomed);
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.DestroyDatasetCommand([CtVariableReadImpl]doomed, [CtVariableReadImpl]req));
            [CtIfImpl][CtCommentImpl]// If we have gotten this far, the destroy command has succeeded,
            [CtCommentImpl]// so we can finalize permanently deleting the physical files:
            [CtCommentImpl]// (DataFileService will double-check that the datafiles no
            [CtCommentImpl]// longer exist in the database, before attempting to delete
            [CtCommentImpl]// the physical files)
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]deleteStorageLocations.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.finalizeFileDeletes([CtVariableReadImpl]deleteStorageLocations);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" destroyed");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteDraftVersion([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]":draft".equals([CtVariableReadImpl]versionId)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]badRequest([CtLiteralImpl]"Only the :draft version can be deleted");
        }
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion doomed = [CtInvocationImpl][CtVariableReadImpl]dataset.getLatestVersion();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]doomed.isDraft()) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtVariableReadImpl]Response.Status.UNAUTHORIZED, [CtLiteralImpl]"This is NOT a DRAFT version"));
            }
            [CtLocalVariableImpl][CtCommentImpl]// Gather the locations of the physical files that will need to be
            [CtCommentImpl]// deleted once the destroy command execution has been finalized:
            [CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.lang.String> deleteStorageLocations = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.getPhysicalFilesToDelete([CtVariableReadImpl]doomed);
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset));
            [CtIfImpl][CtCommentImpl]// If we have gotten this far, the delete command has succeeded -
            [CtCommentImpl]// by either deleting the Draft version of a published dataset,
            [CtCommentImpl]// or destroying an unpublished one.
            [CtCommentImpl]// This means we can finalize permanently deleting the physical files:
            [CtCommentImpl]// (DataFileService will double-check that the datafiles no
            [CtCommentImpl]// longer exist in the database, before attempting to delete
            [CtCommentImpl]// the physical files)
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]deleteStorageLocations.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]fileService.finalizeFileDeletes([CtVariableReadImpl]deleteStorageLocations);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Draft version of dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" deleted");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{datasetId}/deleteLink/{linkedDataverseId}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteDatasetLinkingDataverse([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"datasetId")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"linkedDataverseId")
    [CtTypeReferenceImpl]java.lang.String linkedDataverseId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean index = [CtLiteralImpl]true;
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.DeleteDatasetLinkingDataverseCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtInvocationImpl]findDatasetLinkingDataverseOrDie([CtVariableReadImpl]datasetId, [CtVariableReadImpl]linkedDataverseId), [CtVariableReadImpl]index));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Link from Dataset " + [CtVariableReadImpl]datasetId) + [CtLiteralImpl]" to linked Dataverse ") + [CtVariableReadImpl]linkedDataverseId) + [CtLiteralImpl]" deleted");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/citationdate")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response setCitationDate([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String dsfTypeName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsfTypeName.trim().isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]badRequest([CtLiteralImpl]"Please provide a dataset field type in the requst body.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldType dsfType = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]":publicationDate".equals([CtVariableReadImpl]dsfTypeName)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]dsfType = [CtInvocationImpl][CtTypeAccessImpl]datasetFieldSvc.findByName([CtVariableReadImpl]dsfTypeName);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dsfType == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]badRequest([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset Field Type Name " + [CtVariableReadImpl]dsfTypeName) + [CtLiteralImpl]" not found.");
                }
            }
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.SetDatasetCitationDateCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id), [CtVariableReadImpl]dsfType));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Citation Date for dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" set to: ") + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]dsfType != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]dsfType.getDisplayName() : [CtLiteralImpl]"default"));
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/citationdate")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response useDefaultCitationDate([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.SetDatasetCitationDateCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id), [CtLiteralImpl]null));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Citation Date for dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" set to default");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response listVersions([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.ListVersionsCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id))).stream().map([CtLambdaImpl]([CtParameterImpl] d) -> [CtInvocationImpl]json([CtVariableReadImpl]d)).collect([CtInvocationImpl]toJsonArray())));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersion([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionId, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers);
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dsv == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dsv.getId() == [CtLiteralImpl]null) ? [CtInvocationImpl]notFound([CtLiteralImpl]"Dataset version not found") : [CtInvocationImpl]ok([CtInvocationImpl]json([CtVariableReadImpl]dsv));
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}/files")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersionFiles([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl]jsonFileMetadatas([CtInvocationImpl][CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionId, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers).getFileMetadatas())));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/dirindex")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtLiteralImpl]"text/html")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getFileAccessFolderView([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"version")
    [CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"folder")
    [CtTypeReferenceImpl]java.lang.String folderName, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"original")
    [CtTypeReferenceImpl]java.lang.Boolean originals, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse response) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]folderName = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]folderName == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtVariableReadImpl]folderName;
        [CtAssignmentImpl][CtVariableWriteImpl]versionId = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]versionId == [CtLiteralImpl]null) ? [CtLiteralImpl]":latest-published" : [CtVariableReadImpl]versionId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion version;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
            [CtAssignmentImpl][CtVariableWriteImpl]version = [CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionId, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String output = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.FileUtil.formatFolderListingHtml([CtVariableReadImpl]folderName, [CtVariableReadImpl]version, [CtLiteralImpl]"", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]originals != [CtLiteralImpl]null) && [CtVariableReadImpl]originals);
        [CtIfImpl][CtCommentImpl]// return "NOT FOUND" if there is no such folder in the dataset version:
        if ([CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]output)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]notFound([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Folder " + [CtVariableReadImpl]folderName) + [CtLiteralImpl]" does not exist");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String indexFileName = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]folderName.equals([CtLiteralImpl]"")) ? [CtLiteralImpl]".index.html" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]".index-" + [CtInvocationImpl][CtVariableReadImpl]folderName.replace([CtLiteralImpl]'/', [CtLiteralImpl]'_')) + [CtLiteralImpl]".html";
        [CtInvocationImpl][CtVariableReadImpl]response.setHeader([CtLiteralImpl]"Content-disposition", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"filename=\"" + [CtVariableReadImpl]indexFileName) + [CtLiteralImpl]"\"");
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]// .type("application/html").
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok().entity([CtVariableReadImpl]output).build();
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}/metadata")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersionMetadata([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl]jsonByBlocks([CtInvocationImpl][CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionId, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers).getDatasetFields())));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionNumber}/metadata/{block}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersionMetadataBlock([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String datasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionNumber")
    [CtTypeReferenceImpl]java.lang.String versionNumber, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"block")
    [CtTypeReferenceImpl]java.lang.String blockName, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionNumber, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]datasetId), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers);
            [CtLocalVariableImpl][CtTypeReferenceImpl]Map<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.MetadataBlock, [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField>> fieldsByBlock = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.DatasetField.groupByBlock([CtInvocationImpl][CtVariableReadImpl]dsv.getDatasetFields());
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.MetadataBlock, [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField>> p : [CtInvocationImpl][CtVariableReadImpl]fieldsByBlock.entrySet()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getKey().getName().equals([CtVariableReadImpl]blockName)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl]json([CtInvocationImpl][CtVariableReadImpl]p.getKey(), [CtInvocationImpl][CtVariableReadImpl]p.getValue()));
                }
            }
            [CtReturnImpl]return [CtInvocationImpl]notFound([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"metadata block named " + [CtVariableReadImpl]blockName) + [CtLiteralImpl]" not found");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/modifyRegistration")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateDatasetTargetURL([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetTargetURLCommand([CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id), [CtVariableReadImpl]req));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset " + [CtVariableReadImpl]id) + [CtLiteralImpl]" target url updated");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/modifyRegistrationAll")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateDatasetTargetURLAll() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]datasetService.findAll().forEach([CtLambdaImpl]([CtParameterImpl] ds) -> [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetTargetURLCommand([CtInvocationImpl]findDatasetOrDie([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getId().toString()), [CtVariableReadImpl]req));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]Datasets.class.getName()).log([CtVariableReadImpl]Level.SEVERE, [CtLiteralImpl]null, [CtTypeAccessImpl]ex);
                }
            });
            [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"Update All Dataset target url completed");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/modifyRegistrationMetadata")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateDatasetPIDMetadata([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]dataset.isReleased()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.updatePIDMetadata.failure.dataset.must.be.released"));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.class.getName()).log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]null, [CtVariableReadImpl]ex);
        }
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDvObjectPIDMetadataCommand([CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id), [CtVariableReadImpl]req));
            [CtLocalVariableImpl][CtTypeReferenceImpl]List<[CtTypeReferenceImpl]java.lang.String> args = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]id);
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.updatePIDMetadata.success.for.single.dataset", [CtVariableReadImpl]args));
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"/modifyRegistrationPIDMetadataAll")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateDatasetPIDMetadataAll() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]datasetService.findAll().forEach([CtLambdaImpl]([CtParameterImpl] ds) -> [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDvObjectPIDMetadataCommand([CtInvocationImpl]findDatasetOrDie([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getId().toString()), [CtVariableReadImpl]req));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]Datasets.class.getName()).log([CtVariableReadImpl]Level.SEVERE, [CtLiteralImpl]null, [CtTypeAccessImpl]ex);
                }
            });
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.updatePIDMetadata.success.for.update.all"));
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateDraftVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]":draft".equals([CtVariableReadImpl]versionId)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Only the :draft version can be updated");
        }
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringReader rdr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]jsonBody)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createReader([CtVariableReadImpl]rdr).readObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion incomingVersion = [CtInvocationImpl][CtInvocationImpl]jsonParser().parseDatasetVersion([CtVariableReadImpl]json);
            [CtInvocationImpl][CtCommentImpl]// clear possibly stale fields from the incoming dataset version.
            [CtCommentImpl]// creation and modification dates are updated by the commands.
            [CtVariableReadImpl]incomingVersion.setId([CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setVersionNumber([CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setMinorVersionNumber([CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setVersionState([CtTypeAccessImpl]DatasetVersion.VersionState.DRAFT);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setDataset([CtVariableReadImpl]ds);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setCreateTime([CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]incomingVersion.setLastUpdateTime([CtLiteralImpl]null);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]incomingVersion.getFileMetadatas().isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"You may not add files via this api.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateDraft = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion().isDraft();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion managedVersion;
            [CtIfImpl]if ([CtVariableReadImpl]updateDraft) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion editVersion = [CtInvocationImpl][CtVariableReadImpl]ds.getEditVersion();
                [CtInvocationImpl][CtVariableReadImpl]editVersion.setDatasetFields([CtInvocationImpl][CtVariableReadImpl]incomingVersion.getDatasetFields());
                [CtInvocationImpl][CtVariableReadImpl]editVersion.setTermsOfUseAndAccess([CtInvocationImpl][CtVariableReadImpl]incomingVersion.getTermsOfUseAndAccess());
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset managedDataset = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]ds, [CtVariableReadImpl]req));
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl][CtVariableReadImpl]managedDataset.getEditVersion();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]incomingVersion));
            }
            [CtReturnImpl][CtCommentImpl]// DatasetVersion managedVersion = execCommand( updateDraft
            [CtCommentImpl]// ? new UpdateDatasetVersionCommand(req, incomingVersion)
            [CtCommentImpl]// : new CreateDatasetVersionCommand(req, ds, incomingVersion));
            return [CtInvocationImpl]ok([CtInvocationImpl]json([CtVariableReadImpl]managedVersion));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.json.JsonParseException ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Semantic error parsing dataset version Json: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error parsing dataset version: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/versions/{versionId}/metadata")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtLiteralImpl]"application/ld+json, application/json-ld")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersionJsonLDMetadata([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]versionId, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.bagit.OREMap ore = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.bagit.OREMap([CtVariableReadImpl]dsv, [CtInvocationImpl][CtFieldReadImpl]settingsService.isTrueForKey([CtTypeAccessImpl]SettingsServiceBean.Key.ExcludeEmailFromExport, [CtLiteralImpl]false));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]ore.getOREMapBuilder([CtLiteralImpl]true));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ex.printStackTrace();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception jpe) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]"Error getting jsonld metadata for dsv: ", [CtInvocationImpl][CtVariableReadImpl]jpe.getLocalizedMessage());
            [CtInvocationImpl][CtVariableReadImpl]jpe.printStackTrace();
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtInvocationImpl][CtVariableReadImpl]jpe.getLocalizedMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/metadata")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtLiteralImpl]"application/ld+json, application/json-ld")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getVersionJsonLDMetadata([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getVersionJsonLDMetadata([CtVariableReadImpl]id, [CtLiteralImpl]":draft", [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/metadata")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtLiteralImpl]"application/ld+json, application/json-ld")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response updateVersionMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonLDBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.DefaultValue([CtLiteralImpl]"false")
    [CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"replace")
    [CtTypeReferenceImpl]boolean replaceTerms) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl][CtVariableReadImpl]ds.getEditVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateDraft = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion().isDraft();
            [CtAssignmentImpl][CtVariableWriteImpl]dsv = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.json.JSONLDUtil.updateDatasetVersionMDFromJsonLD([CtVariableReadImpl]dsv, [CtVariableReadImpl]jsonLDBody, [CtFieldReadImpl]metadataBlockService, [CtFieldReadImpl]datasetFieldSvc, [CtUnaryOperatorImpl]![CtVariableReadImpl]replaceTerms, [CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion managedVersion;
            [CtIfImpl]if ([CtVariableReadImpl]updateDraft) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset managedDataset = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]ds, [CtVariableReadImpl]req));
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl][CtVariableReadImpl]managedDataset.getEditVersion();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]dsv));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String info = [CtConditionalImpl]([CtVariableReadImpl]updateDraft) ? [CtLiteralImpl]"Version Updated" : [CtLiteralImpl]"Version Created";
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder().add([CtVariableReadImpl]info, [CtInvocationImpl][CtVariableReadImpl]managedVersion.getVersionDate()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.json.stream.JsonParsingException jpe) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]"Error parsing dataset json. Json: {0}", [CtVariableReadImpl]jsonLDBody);
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error parsing Json: " + [CtInvocationImpl][CtVariableReadImpl]jpe.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/metadata/delete")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtLiteralImpl]"application/ld+json, application/json-ld")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonLDBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtLiteralImpl]"In delteMetadata");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl][CtVariableReadImpl]ds.getEditVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateDraft = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion().isDraft();
            [CtAssignmentImpl][CtVariableWriteImpl]dsv = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.json.JSONLDUtil.deleteDatasetVersionMDFromJsonLD([CtVariableReadImpl]dsv, [CtVariableReadImpl]jsonLDBody, [CtFieldReadImpl]metadataBlockService, [CtFieldReadImpl]datasetFieldSvc);
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtLiteralImpl]"Updating ver");
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion managedVersion;
            [CtIfImpl]if ([CtVariableReadImpl]updateDraft) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset managedDataset = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]ds, [CtVariableReadImpl]req));
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl][CtVariableReadImpl]managedDataset.getEditVersion();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]dsv));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String info = [CtConditionalImpl]([CtVariableReadImpl]updateDraft) ? [CtLiteralImpl]"Version Updated" : [CtLiteralImpl]"Version Created";
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder().add([CtVariableReadImpl]info, [CtInvocationImpl][CtVariableReadImpl]managedVersion.getVersionDate()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ex.printStackTrace();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.json.stream.JsonParsingException jpe) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]"Error parsing dataset json. Json: {0}", [CtVariableReadImpl]jsonLDBody);
            [CtInvocationImpl][CtVariableReadImpl]jpe.printStackTrace();
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error parsing Json: " + [CtInvocationImpl][CtVariableReadImpl]jpe.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/deleteMetadata")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteVersionMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
        [CtReturnImpl]return [CtInvocationImpl]processDatasetFieldDataDelete([CtVariableReadImpl]jsonBody, [CtVariableReadImpl]id, [CtVariableReadImpl]req);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.ws.rs.core.Response processDatasetFieldDataDelete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringReader rdr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]jsonBody)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createReader([CtVariableReadImpl]rdr).readObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl][CtVariableReadImpl]ds.getEditVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField> fields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField singleField = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonArray fieldsJson = [CtInvocationImpl][CtVariableReadImpl]json.getJsonArray([CtLiteralImpl]"fields");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fieldsJson == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]singleField = [CtInvocationImpl][CtInvocationImpl]jsonParser().parseField([CtVariableReadImpl]json, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
                [CtInvocationImpl][CtVariableReadImpl]fields.add([CtVariableReadImpl]singleField);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]fields = [CtInvocationImpl][CtInvocationImpl]jsonParser().parseMultipleFields([CtVariableReadImpl]json);
            }
            [CtInvocationImpl][CtVariableReadImpl]dsv.setVersionState([CtTypeAccessImpl]DatasetVersion.VersionState.DRAFT);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue> controlledVocabularyItemsToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldValue> datasetFieldValueItemsToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldValue>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue> datasetFieldCompoundValueItemsToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField updateField : [CtVariableReadImpl]fields) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField dsf : [CtInvocationImpl][CtVariableReadImpl]dsv.getDatasetFields()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().equals([CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType())) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().isControlledVocabulary()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue cvv : [CtInvocationImpl][CtVariableReadImpl]updateField.getControlledVocabularyValues()) [CtBlockImpl]{
                                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue existing : [CtInvocationImpl][CtVariableReadImpl]dsf.getControlledVocabularyValues()) [CtBlockImpl]{
                                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existing.getStrValue().equals([CtInvocationImpl][CtVariableReadImpl]cvv.getStrValue())) [CtBlockImpl]{
                                                [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                                [CtInvocationImpl][CtVariableReadImpl]controlledVocabularyItemsToRemove.add([CtVariableReadImpl]existing);
                                            }
                                        }
                                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
                                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]cvv.getStrValue()) + [CtLiteralImpl]" not found.");
                                            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]cvv.getStrValue()) + [CtLiteralImpl]" not found.");
                                        }
                                    }
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue remove : [CtVariableReadImpl]controlledVocabularyItemsToRemove) [CtBlockImpl]{
                                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getControlledVocabularyValues().remove([CtVariableReadImpl]remove);
                                    }
                                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getSingleControlledVocabularyValue().getStrValue().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getSingleControlledVocabularyValue().getStrValue())) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleControlledVocabularyValue([CtLiteralImpl]null);
                                }
                            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().isCompound()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldValue dfv : [CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldValues()) [CtBlockImpl]{
                                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldValue edsfv : [CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldValues()) [CtBlockImpl]{
                                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]edsfv.getDisplayValue().equals([CtInvocationImpl][CtVariableReadImpl]dfv.getDisplayValue())) [CtBlockImpl]{
                                                [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                                [CtInvocationImpl][CtVariableReadImpl]datasetFieldValueItemsToRemove.add([CtVariableReadImpl]dfv);
                                            }
                                        }
                                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
                                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]dfv.getDisplayValue()) + [CtLiteralImpl]" not found.");
                                            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]dfv.getDisplayValue()) + [CtLiteralImpl]" not found.");
                                        }
                                    }
                                    [CtInvocationImpl][CtVariableReadImpl]datasetFieldValueItemsToRemove.forEach([CtLambdaImpl]([CtParameterImpl] remove) -> [CtBlockImpl]{
                                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldValues().remove([CtVariableReadImpl]remove);
                                    });
                                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getSingleValue().getDisplayValue().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getSingleValue().getDisplayValue())) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleValue([CtLiteralImpl]null);
                                }
                            } else [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue dfcv : [CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldCompoundValues()) [CtBlockImpl]{
                                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String deleteVal = [CtInvocationImpl]getCompoundDisplayValue([CtVariableReadImpl]dfcv);
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue existing : [CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldCompoundValues()) [CtBlockImpl]{
                                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String existingString = [CtInvocationImpl]getCompoundDisplayValue([CtVariableReadImpl]existing);
                                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]existingString.equals([CtVariableReadImpl]deleteVal)) [CtBlockImpl]{
                                            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                                            [CtInvocationImpl][CtVariableReadImpl]datasetFieldCompoundValueItemsToRemove.add([CtVariableReadImpl]existing);
                                        }
                                    }
                                    [CtInvocationImpl][CtVariableReadImpl]datasetFieldCompoundValueItemsToRemove.forEach([CtLambdaImpl]([CtParameterImpl] remove) -> [CtBlockImpl]{
                                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldCompoundValues().remove([CtVariableReadImpl]remove);
                                    });
                                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
                                        [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtVariableReadImpl]deleteVal) + [CtLiteralImpl]" not found.");
                                        [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtVariableReadImpl]deleteVal) + [CtLiteralImpl]" not found.");
                                    }
                                }
                            }
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                            [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleValue([CtLiteralImpl]null);
                            [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleControlledVocabularyValue([CtLiteralImpl]null);
                        }
                        [CtBreakImpl]break;
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String displayValue = [CtConditionalImpl]([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDisplayValue().isEmpty()) ? [CtInvocationImpl][CtVariableReadImpl]updateField.getDisplayValue() : [CtInvocationImpl][CtVariableReadImpl]updateField.getCompoundDisplayValue();
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtVariableReadImpl]displayValue) + [CtLiteralImpl]" not found.");
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Delete metadata failed: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]": ") + [CtVariableReadImpl]displayValue) + [CtLiteralImpl]" not found.");
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateDraft = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion().isDraft();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion managedVersion = [CtConditionalImpl]([CtVariableReadImpl]updateDraft) ? [CtInvocationImpl][CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]ds, [CtVariableReadImpl]req)).getEditVersion() : [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]dsv));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl]json([CtVariableReadImpl]managedVersion));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.json.JsonParseException ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Semantic error parsing dataset update Json: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error processing metadata delete: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Delete metadata error: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getCompoundDisplayValue([CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue dscv) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String returnString = [CtLiteralImpl]"";
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField dsf : [CtInvocationImpl][CtVariableReadImpl]dscv.getChildDatasetFields()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String value : [CtInvocationImpl][CtVariableReadImpl]dsf.getValues()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]value == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]returnString += [CtBinaryOperatorImpl][CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]returnString.isEmpty() ? [CtLiteralImpl]"" : [CtLiteralImpl]"; ") + [CtInvocationImpl][CtVariableReadImpl]value.trim();
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]returnString;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/editMetadata")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response editVersionMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"replace")
    [CtTypeReferenceImpl]java.lang.Boolean replace) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean replaceData = [CtBinaryOperatorImpl][CtVariableReadImpl]replace != [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]req = [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Edit metdata error: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
        [CtReturnImpl]return [CtInvocationImpl]processDatasetUpdate([CtVariableReadImpl]jsonBody, [CtVariableReadImpl]id, [CtVariableReadImpl]req, [CtVariableReadImpl]replaceData);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]javax.ws.rs.core.Response processDatasetUpdate([CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean replaceData) [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringReader rdr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]jsonBody)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createReader([CtVariableReadImpl]rdr).readObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl][CtVariableReadImpl]ds.getEditVersion();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField> fields = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField singleField = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonArray fieldsJson = [CtInvocationImpl][CtVariableReadImpl]json.getJsonArray([CtLiteralImpl]"fields");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fieldsJson == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]singleField = [CtInvocationImpl][CtInvocationImpl]jsonParser().parseField([CtVariableReadImpl]json, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]FALSE);
                [CtInvocationImpl][CtVariableReadImpl]fields.add([CtVariableReadImpl]singleField);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]fields = [CtInvocationImpl][CtInvocationImpl]jsonParser().parseMultipleFields([CtVariableReadImpl]json);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String valdationErrors = [CtInvocationImpl]validateDatasetFieldValues([CtVariableReadImpl]fields);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]valdationErrors.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Semantic error parsing dataset update Json: " + [CtVariableReadImpl]valdationErrors, [CtVariableReadImpl]valdationErrors);
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error parsing dataset update: " + [CtVariableReadImpl]valdationErrors);
            }
            [CtInvocationImpl][CtVariableReadImpl]dsv.setVersionState([CtTypeAccessImpl]DatasetVersion.VersionState.DRAFT);
            [CtForEachImpl][CtCommentImpl]// loop through the update fields
            [CtCommentImpl]// and compare to the version fields
            [CtCommentImpl]// if exist add/replace values
            [CtCommentImpl]// if not add entire dsf
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField updateField : [CtVariableReadImpl]fields) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField dsf : [CtInvocationImpl][CtVariableReadImpl]dsv.getDatasetFields()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().equals([CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType())) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dsf.isEmpty() || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) || [CtVariableReadImpl]replaceData) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List priorCVV = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cvvDisplay = [CtLiteralImpl]"";
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().isControlledVocabulary()) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]cvvDisplay = [CtInvocationImpl][CtVariableReadImpl]dsf.getDisplayValue();
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue cvvOld : [CtInvocationImpl][CtVariableReadImpl]dsf.getControlledVocabularyValues()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]priorCVV.add([CtVariableReadImpl]cvvOld);
                                }
                            }
                            [CtIfImpl]if ([CtVariableReadImpl]replaceData) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setDatasetFieldCompoundValues([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setDatasetFieldValues([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setControlledVocabularyValues([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
                                    [CtInvocationImpl][CtVariableReadImpl]priorCVV.clear();
                                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getControlledVocabularyValues().clear();
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleValue([CtLiteralImpl]"");
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleControlledVocabularyValue([CtLiteralImpl]null);
                                }
                            }
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().isControlledVocabulary()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.ControlledVocabularyValue cvv : [CtInvocationImpl][CtVariableReadImpl]updateField.getControlledVocabularyValues()) [CtBlockImpl]{
                                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cvvDisplay.contains([CtInvocationImpl][CtVariableReadImpl]cvv.getStrValue())) [CtBlockImpl]{
                                            [CtInvocationImpl][CtVariableReadImpl]priorCVV.add([CtVariableReadImpl]cvv);
                                        }
                                    }
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setControlledVocabularyValues([CtVariableReadImpl]priorCVV);
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleControlledVocabularyValue([CtInvocationImpl][CtVariableReadImpl]updateField.getSingleControlledVocabularyValue());
                                }
                            } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldType().isCompound()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) [CtBlockImpl]{
                                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldValue dfv : [CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldValues()) [CtBlockImpl]{
                                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDisplayValue().contains([CtInvocationImpl][CtVariableReadImpl]dfv.getDisplayValue())) [CtBlockImpl]{
                                            [CtInvocationImpl][CtVariableReadImpl]dfv.setDatasetField([CtVariableReadImpl]dsf);
                                            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldValues().add([CtVariableReadImpl]dfv);
                                        }
                                    }
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]dsf.setSingleValue([CtInvocationImpl][CtVariableReadImpl]updateField.getValue());
                                }
                            } else [CtBlockImpl]{
                                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldCompoundValue dfcv : [CtInvocationImpl][CtVariableReadImpl]updateField.getDatasetFieldCompoundValues()) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getCompoundDisplayValue().contains([CtInvocationImpl][CtVariableReadImpl]updateField.getCompoundDisplayValue())) [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]dfcv.setParentDatasetField([CtVariableReadImpl]dsf);
                                        [CtInvocationImpl][CtVariableReadImpl]dsf.setDatasetVersion([CtVariableReadImpl]dsv);
                                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldCompoundValues().add([CtVariableReadImpl]dfcv);
                                    }
                                }
                            }
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]dsf.isEmpty()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples())) || [CtUnaryOperatorImpl](![CtVariableReadImpl]replaceData)) [CtBlockImpl]{
                            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"You may not add data to a field that already has data and does not allow multiples. Use replace=true to replace existing data (" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().getDisplayName()) + [CtLiteralImpl]")");
                        }
                        [CtBreakImpl]break;
                    }
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]found) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]updateField.setDatasetVersion([CtVariableReadImpl]dsv);
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsv.getDatasetFields().add([CtVariableReadImpl]updateField);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateDraft = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion().isDraft();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion managedVersion;
            [CtIfImpl]if ([CtVariableReadImpl]updateDraft) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl][CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]ds, [CtVariableReadImpl]req)).getEditVersion();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]managedVersion = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreateDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]dsv));
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl]json([CtVariableReadImpl]managedVersion));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.json.JsonParseException ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Semantic error parsing dataset update Json: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Error parsing dataset update: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtBinaryOperatorImpl][CtLiteralImpl]"Update metdata error: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String validateDatasetFieldValues([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField> fields) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder error = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetField dsf : [CtVariableReadImpl]fields) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getControlledVocabularyValues().isEmpty()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldCompoundValues().isEmpty()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldValues().isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.append([CtLiteralImpl]"Empty multiple value for field: ").append([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().getDisplayName()).append([CtLiteralImpl]" ");
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().isAllowMultiples()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getSingleValue().getValue().isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.append([CtLiteralImpl]"Empty value for field: ").append([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dsf.getDatasetFieldType().getDisplayName()).append([CtLiteralImpl]" ");
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]error.toString().isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]error.toString();
        }
        [CtReturnImpl]return [CtLiteralImpl]"";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @deprecated This was shipped as a GET but should have been a POST, see https://github.com/IQSS/dataverse/issues/2431
     */
    [CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/actions/:publish")
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response publishDataseUsingGetDeprecated([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"type")
    [CtTypeReferenceImpl]java.lang.String type) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"publishDataseUsingGetDeprecated called on id " + [CtVariableReadImpl]id) + [CtLiteralImpl]". Encourage use of POST rather than GET, which is deprecated.");
        [CtReturnImpl]return [CtInvocationImpl]publishDataset([CtVariableReadImpl]id, [CtVariableReadImpl]type, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/actions/:publish")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response publishDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"type")
    [CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"assureIsIndexed")
    [CtTypeReferenceImpl]boolean mustBeIndexed) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Missing 'type' parameter (either 'major','minor', or 'updatecurrent').");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean updateCurrent = [CtLiteralImpl]false;
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user = [CtInvocationImpl]findAuthenticatedUserOrDie();
            [CtAssignmentImpl][CtVariableWriteImpl]type = [CtInvocationImpl][CtVariableReadImpl]type.toLowerCase();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isMinor = [CtLiteralImpl]false;
            [CtSwitchImpl]switch ([CtVariableReadImpl]type) {
                [CtCaseImpl]case [CtLiteralImpl]"minor" :
                    [CtAssignmentImpl][CtVariableWriteImpl]isMinor = [CtLiteralImpl]true;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"major" :
                    [CtAssignmentImpl][CtVariableWriteImpl]isMinor = [CtLiteralImpl]false;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"updatecurrent" :
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]user.isSuperuser()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]updateCurrent = [CtLiteralImpl]true;
                    } else [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Only superusers can update the current version");
                    }
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Illegal 'type' parameter value '" + [CtVariableReadImpl]type) + [CtLiteralImpl]"'. It needs to be either 'major', 'minor', or 'updatecurrent'.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtIfImpl]if ([CtVariableReadImpl]mustBeIndexed) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"IT: " + [CtInvocationImpl][CtVariableReadImpl]ds.getIndexTime());
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"MT: " + [CtInvocationImpl][CtVariableReadImpl]ds.getModificationTime());
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"PIT: " + [CtInvocationImpl][CtVariableReadImpl]ds.getPermissionIndexTime());
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"PMT: " + [CtInvocationImpl][CtVariableReadImpl]ds.getPermissionModificationTime());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getIndexTime() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getModificationTime() != [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"ITMT: " + [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getIndexTime().compareTo([CtInvocationImpl][CtVariableReadImpl]ds.getModificationTime()) <= [CtLiteralImpl]0));
                }
                [CtIfImpl][CtCommentImpl]/* Some calls, such as the /datasets/actions/:import* commands do not set the
                modification or permission modification times. The checks here are trying to
                see if indexing or permissionindexing could be pending, so they check to see
                if the relevant modification time is set and if so, whether the index is also
                set and if so, if it after the modification time. If the modification time is
                set and the index time is null or is before the mod time, the 409/conflict
                error is returned.
                 */
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getModificationTime() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getIndexTime() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getIndexTime().compareTo([CtInvocationImpl][CtVariableReadImpl]ds.getModificationTime()) <= [CtLiteralImpl]0))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getPermissionModificationTime() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ds.getPermissionIndexTime() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ds.getPermissionIndexTime().compareTo([CtInvocationImpl][CtVariableReadImpl]ds.getPermissionModificationTime()) <= [CtLiteralImpl]0)))) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.CONFLICT, [CtLiteralImpl]"Dataset is awaiting indexing");
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]updateCurrent) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]/* Note: The code here mirrors that in the
                edu.harvard.iq.dataverse.DatasetPage:updateCurrentVersion method. Any changes
                to the core logic (i.e. beyond updating the messaging about results) should
                be applied to the code there as well.
                 */
                [CtTypeReferenceImpl]java.lang.String errorMsg = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String successMsg = [CtLiteralImpl]null;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CuratePublishedDatasetVersionCommand cmd = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CuratePublishedDatasetVersionCommand([CtVariableReadImpl]ds, [CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user));
                    [CtAssignmentImpl][CtVariableWriteImpl]ds = [CtInvocationImpl][CtFieldReadImpl]commandEngine.submit([CtVariableReadImpl]cmd);
                    [CtAssignmentImpl][CtVariableWriteImpl]successMsg = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasetversion.update.success");
                    [CtLocalVariableImpl][CtCommentImpl]// If configured, update archive copy as well
                    [CtTypeReferenceImpl]java.lang.String className = [CtInvocationImpl][CtFieldReadImpl]settingsService.get([CtInvocationImpl][CtTypeAccessImpl]SettingsServiceBean.Key.ArchiverClassName.toString());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion updateVersion = [CtInvocationImpl][CtVariableReadImpl]ds.getLatestVersion();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.AbstractSubmitToArchiveCommand archiveCommand = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.ArchiverUtil.createSubmitToArchiveCommand([CtVariableReadImpl]className, [CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user), [CtVariableReadImpl]updateVersion);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]archiveCommand != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// Delete the record of any existing copy since it is now out of date/incorrect
                        [CtVariableReadImpl]updateVersion.setArchivalCopyLocation([CtLiteralImpl]null);
                        [CtTryImpl][CtCommentImpl]/* Then try to generate and submit an archival copy. Note that running this
                        command within the CuratePublishedDatasetVersionCommand was causing an error:
                        "The attribute [id] of class
                        [edu.harvard.iq.dataverse.DatasetFieldCompoundValue] is mapped to a primary
                        key column in the database. Updates are not allowed." To avoid that, and to
                        simplify reporting back to the GUI whether this optional step succeeded, I've
                        pulled this out as a separate submit().
                         */
                        try [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]updateVersion = [CtInvocationImpl][CtFieldReadImpl]commandEngine.submit([CtVariableReadImpl]archiveCommand);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]updateVersion.getArchivalCopyLocation() != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]successMsg = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasetversion.update.archive.success");
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]successMsg = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasetversion.update.archive.failure");
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.exception.CommandException ex) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]successMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasetversion.update.archive.failure") + [CtLiteralImpl]" - ") + [CtInvocationImpl][CtVariableReadImpl]ex.toString();
                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.severe([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
                        }
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.exception.CommandException ex) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]errorMsg = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasetversion.update.failure") + [CtLiteralImpl]" - ") + [CtInvocationImpl][CtVariableReadImpl]ex.toString();
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.severe([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]errorMsg != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtVariableReadImpl]errorMsg);
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder().add([CtLiteralImpl]"status", [CtTypeAccessImpl]STATUS_OK).add([CtLiteralImpl]"status_details", [CtVariableReadImpl]successMsg).add([CtLiteralImpl]"data", [CtInvocationImpl]json([CtVariableReadImpl]ds)).build()).type([CtTypeAccessImpl]MediaType.APPLICATION_JSON).build();
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.PublishDatasetResult res = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.PublishDatasetCommand([CtVariableReadImpl]ds, [CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user), [CtVariableReadImpl]isMinor));
                [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]res.isWorkflow() ? [CtInvocationImpl]accepted([CtInvocationImpl]json([CtInvocationImpl][CtVariableReadImpl]res.getDataset())) : [CtInvocationImpl]ok([CtInvocationImpl]json([CtInvocationImpl][CtVariableReadImpl]res.getDataset()));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/move/{targetDataverseAlias}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response moveDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"targetDataverseAlias")
    [CtTypeReferenceImpl]java.lang.String targetDataverseAlias, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"forceMove")
    [CtTypeReferenceImpl]java.lang.Boolean force) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse target = [CtInvocationImpl][CtFieldReadImpl]dataverseService.findByAlias([CtVariableReadImpl]targetDataverseAlias);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.moveDataset.error.targetDataverseNotFound"));
            }
            [CtInvocationImpl][CtCommentImpl]// Command requires Super user - it will be tested by the command
            execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.MoveDatasetCommand([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]u), [CtVariableReadImpl]ds, [CtVariableReadImpl]target, [CtVariableReadImpl]force));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.moveDataset.success"));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ex.getCause() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.exception.UnforcedCommandException) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.refineResponse([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.moveDataset.error.suggestForce"));
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{linkedDatasetId}/link/{linkingDataverseAlias}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response linkDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"linkedDatasetId")
    [CtTypeReferenceImpl]java.lang.String linkedDatasetId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"linkingDataverseAlias")
    [CtTypeReferenceImpl]java.lang.String linkingDataverseAlias) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset linked = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]linkedDatasetId);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse linking = [CtInvocationImpl]findDataverseOrDie([CtVariableReadImpl]linkingDataverseAlias);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]linked == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Linked Dataset not found.");
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]linking == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Linking Dataverse not found.");
            }
            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.LinkDatasetCommand([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]u), [CtVariableReadImpl]linking, [CtVariableReadImpl]linked));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset " + [CtInvocationImpl][CtVariableReadImpl]linked.getId()) + [CtLiteralImpl]" linked successfully to ") + [CtInvocationImpl][CtVariableReadImpl]linking.getAlias());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/links")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getLinks([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isSuperuser()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Not a superuser");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]long datasetId = [CtInvocationImpl][CtVariableReadImpl]dataset.getId();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse> dvsThatLinkToThisDatasetId = [CtInvocationImpl][CtFieldReadImpl]dataverseSvc.findDataversesThatLinkToThisDatasetId([CtVariableReadImpl]datasetId);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonArrayBuilder dataversesThatLinkToThisDatasetIdBuilder = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createArrayBuilder();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse dataverse : [CtVariableReadImpl]dvsThatLinkToThisDatasetId) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dataversesThatLinkToThisDatasetIdBuilder.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataverse.getAlias() + [CtLiteralImpl]" (id ") + [CtInvocationImpl][CtVariableReadImpl]dataverse.getId()) + [CtLiteralImpl]")");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder response = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
            [CtInvocationImpl][CtVariableReadImpl]response.add([CtBinaryOperatorImpl][CtLiteralImpl]"dataverses that link to dataset id " + [CtVariableReadImpl]datasetId, [CtVariableReadImpl]dataversesThatLinkToThisDatasetIdBuilder);
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]response);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a given assignment to a given user or group
     *
     * @param ra
     * 		role assignment DTO
     * @param id
     * 		dataset id
     * @param apiKey
     */
    [CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/assignments")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response createAssignment([CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.dto.RoleAssignmentDTO ra, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"key")
    [CtTypeReferenceImpl]java.lang.String apiKey) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.RoleAssignee assignee = [CtInvocationImpl]findAssignee([CtInvocationImpl][CtVariableReadImpl]ra.getAssignee());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]assignee == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.grant.role.assignee.not.found.error"));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.DataverseRole theRole;
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse dv = [CtInvocationImpl][CtVariableReadImpl]dataset.getOwner();
            [CtAssignmentImpl][CtVariableWriteImpl]theRole = [CtLiteralImpl]null;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]theRole == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]dv != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.DataverseRole aRole : [CtInvocationImpl][CtFieldReadImpl]rolesSvc.availableRoles([CtInvocationImpl][CtVariableReadImpl]dv.getId())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aRole.getAlias().equals([CtInvocationImpl][CtVariableReadImpl]ra.getRole())) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]theRole = [CtVariableReadImpl]aRole;
                        [CtBreakImpl]break;
                    }
                }
                [CtAssignmentImpl][CtVariableWriteImpl]dv = [CtInvocationImpl][CtVariableReadImpl]dv.getOwner();
            } 
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]theRole == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> args = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]ra.getRole(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getOwner().getDisplayName());
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Status.BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.grant.role.not.found.error", [CtVariableReadImpl]args));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String privateUrlToken = [CtLiteralImpl]null;
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl]json([CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.AssignRoleCommand([CtVariableReadImpl]assignee, [CtVariableReadImpl]theRole, [CtVariableReadImpl]dataset, [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtVariableReadImpl]privateUrlToken))));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> args = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.grant.role.cant.create.assignment.error", [CtVariableReadImpl]args));
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/assignments/{id}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteAssignment([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]long assignmentId, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dsId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.RoleAssignment ra = [CtInvocationImpl][CtFieldReadImpl]em.find([CtFieldReadImpl]edu.harvard.iq.dataverse.RoleAssignment.class, [CtVariableReadImpl]assignmentId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ra != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dsId);
                [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.RevokeRoleCommand([CtVariableReadImpl]ra, [CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie())));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> args = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ra.getRole().getName(), [CtInvocationImpl][CtVariableReadImpl]ra.getAssigneeIdentifier(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ra.getDefinitionPoint().accept([CtTypeAccessImpl]DvObject.NamePrinter));
                [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.revoke.role.success", [CtVariableReadImpl]args));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> args = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtVariableReadImpl]assignmentId));
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Status.NOT_FOUND, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.revoke.role.not.found.error", [CtVariableReadImpl]args));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/assignments")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getAssignments([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.ListRoleAssignments([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id))).stream().map([CtLambdaImpl]([CtParameterImpl] ra) -> [CtInvocationImpl]json([CtVariableReadImpl]ra)).collect([CtInvocationImpl]toJsonArray())));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/privateUrl")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getPrivateUrlData([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.privateurl.PrivateUrl privateUrl = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetPrivateUrlCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied)));
            [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]privateUrl != [CtLiteralImpl]null ? [CtInvocationImpl]ok([CtInvocationImpl]json([CtVariableReadImpl]privateUrl)) : [CtInvocationImpl]error([CtVariableReadImpl]Response.Status.NOT_FOUND, [CtLiteralImpl]"Private URL not found.");
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/privateUrl")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response createPrivateUrl([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.DefaultValue([CtLiteralImpl]"false")
    [CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"anonymizedAccess")
    [CtTypeReferenceImpl]boolean anonymizedAccess) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]anonymizedAccess && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]settingsSvc.getValueForKey([CtTypeAccessImpl]SettingsServiceBean.Key.AnonymizedFieldTypeNames) == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.ws.rs.NotAcceptableException([CtLiteralImpl]"Anonymized Access not enabled");
        }
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl]json([CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.CreatePrivateUrlCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied), [CtVariableReadImpl]anonymizedAccess)))));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/privateUrl")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deletePrivateUrl([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.privateurl.PrivateUrl privateUrl = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetPrivateUrlCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]privateUrl != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.DeletePrivateUrlCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset));
                [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"Private URL deleted.");
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]notFound([CtLiteralImpl]"No Private URL to delete.");
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/thumbnail/candidates")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getDatasetThumbnailCandidates([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean canUpdateThumbnail = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]canUpdateThumbnail = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]permissionSvc.requestOn([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtVariableReadImpl]dataset).canIssue([CtFieldReadImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetThumbnailCommand.class);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Exception thrown while trying to figure out permissions while getting thumbnail for dataset id " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ex.getLocalizedMessage());
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]canUpdateThumbnail) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"You are not permitted to list dataset thumbnail candidates.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonArrayBuilder data = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createArrayBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean considerDatasetLogoAsCandidate = [CtLiteralImpl]true;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataset.DatasetThumbnail datasetThumbnail : [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataset.DatasetUtil.getThumbnailCandidates([CtVariableReadImpl]dataset, [CtVariableReadImpl]considerDatasetLogoAsCandidate, [CtTypeAccessImpl]ImageThumbConverter.DEFAULT_CARDIMAGE_SIZE)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder candidate = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String base64image = [CtInvocationImpl][CtVariableReadImpl]datasetThumbnail.getBase64image();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]base64image != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtLiteralImpl]"found a candidate!");
                    [CtInvocationImpl][CtVariableReadImpl]candidate.add([CtLiteralImpl]"base64image", [CtVariableReadImpl]base64image);
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataFile dataFile = [CtInvocationImpl][CtVariableReadImpl]datasetThumbnail.getDataFile();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataFile != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]candidate.add([CtLiteralImpl]"dataFileId", [CtInvocationImpl][CtVariableReadImpl]dataFile.getId());
                }
                [CtInvocationImpl][CtVariableReadImpl]data.add([CtVariableReadImpl]candidate);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]data);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Could not find dataset based on id supplied: " + [CtVariableReadImpl]idSupplied) + [CtLiteralImpl]".");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtNewArrayImpl]{ [CtLiteralImpl]"image/png" })
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/thumbnail")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getDatasetThumbnail([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream is = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataset.DatasetUtil.getThumbnailAsInputStream([CtVariableReadImpl]dataset, [CtTypeAccessImpl]ImageThumbConverter.DEFAULT_CARDIMAGE_SIZE);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]is == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]notFound([CtLiteralImpl]"Thumbnail not available");
            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.ok([CtVariableReadImpl]is).build();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]notFound([CtLiteralImpl]"Thumbnail not available");
        }
    }

    [CtMethodImpl][CtCommentImpl]// TODO: Rather than only supporting looking up files by their database IDs (dataFileIdSupplied), consider supporting persistent identifiers.
    [CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/thumbnail/{dataFileId}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response setDataFileAsThumbnail([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"dataFileId")
    [CtTypeReferenceImpl]long dataFileIdSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataset.DatasetThumbnail datasetThumbnail = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetThumbnailCommand([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied), [CtFieldReadImpl]UpdateDatasetThumbnailCommand.UserIntent.setDatasetFileAsThumbnail, [CtVariableReadImpl]dataFileIdSupplied, [CtLiteralImpl]null));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtLiteralImpl]"Thumbnail set to " + [CtInvocationImpl][CtVariableReadImpl]datasetThumbnail.getBase64image());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/thumbnail")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtFieldReadImpl]javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response uploadDatasetLogo([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@org.glassfish.jersey.media.multipart.FormDataParam([CtLiteralImpl]"file")
    [CtTypeReferenceImpl]java.io.InputStream inputStream) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataset.DatasetThumbnail datasetThumbnail = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetThumbnailCommand([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied), [CtFieldReadImpl]UpdateDatasetThumbnailCommand.UserIntent.setNonDatasetFileAsThumbnail, [CtLiteralImpl]null, [CtVariableReadImpl]inputStream));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtLiteralImpl]"Thumbnail is now " + [CtInvocationImpl][CtVariableReadImpl]datasetThumbnail.getBase64image());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/thumbnail")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response removeDatasetLogo([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataset.DatasetThumbnail datasetThumbnail = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetThumbnailCommand([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied), [CtFieldReadImpl]UpdateDatasetThumbnailCommand.UserIntent.removeThumbnail, [CtLiteralImpl]null, [CtLiteralImpl]null));
            [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"Dataset thumbnail removed.");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/dataCaptureModule/rsync")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getRsync([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// TODO - does it make sense to switch this to dataset identifier for consistency with the rest of the DCM APIs?
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.datacapturemodule.DataCaptureModuleUtil.rsyncSupportEnabled([CtInvocationImpl][CtFieldReadImpl]settingsSvc.getValueForKey([CtTypeAccessImpl]SettingsServiceBean.Key.UploadMethods))) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.METHOD_NOT_ALLOWED, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]SettingsServiceBean.Key.UploadMethods + [CtLiteralImpl]" does not contain ") + [CtFieldReadImpl]SystemConfig.FileUploadMethods.RSYNC) + [CtLiteralImpl]".");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user = [CtInvocationImpl]findAuthenticatedUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.datacapturemodule.ScriptRequestResponse scriptRequestResponse = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.RequestRsyncScriptCommand([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user), [CtVariableReadImpl]dataset));
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock lock = [CtInvocationImpl][CtFieldReadImpl]datasetService.addDatasetLock([CtInvocationImpl][CtVariableReadImpl]dataset.getId(), [CtTypeAccessImpl]DatasetLock.Reason.DcmUpload, [CtInvocationImpl][CtVariableReadImpl]user.getId(), [CtLiteralImpl]"script downloaded");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lock == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"Failed to lock the dataset (dataset id={0})", [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to lock the dataset (dataset id=" + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]")");
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]scriptRequestResponse.getScript(), [CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.MediaType.valueOf([CtTypeAccessImpl]MediaType.TEXT_PLAIN), [CtLiteralImpl]null);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.ejb.EJBException ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtBinaryOperatorImpl][CtLiteralImpl]"Something went wrong attempting to download rsync script: " + [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.EjbUtil.ejbExceptionToString([CtVariableReadImpl]ex));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This api endpoint triggers the creation of a "package" file in a dataset
     *    after that package has been moved onto the same filesystem via the Data Capture Module.
     * The package is really just a way that Dataverse interprets a folder created by DCM, seeing it as just one file.
     * The "package" can be downloaded over RSAL.
     *
     * This endpoint currently supports both posix file storage and AWS s3 storage in Dataverse, and depending on which one is active acts accordingly.
     *
     * The initial design of the DCM/Dataverse interaction was not to use packages, but to allow import of all individual files natively into Dataverse.
     * But due to the possibly immense number of files (millions) the package approach was taken.
     * This is relevant because the posix ("file") code contains many remnants of that development work.
     * The s3 code was written later and is set to only support import as packages. It takes a lot from FileRecordWriter.
     * -MAD 4.9.1
     */
    [CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/dataCaptureModule/checksumValidation")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response receiveChecksumValidationResults([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]javax.json.JsonObject jsonFromDcm) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINE, [CtLiteralImpl]"jsonFromDcm: {0}", [CtVariableReadImpl]jsonFromDcm);
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser authenticatedUser = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]authenticatedUser = [CtInvocationImpl]findAuthenticatedUserOrDie();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Authentication is required.");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]authenticatedUser.isSuperuser()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Superusers only.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String statusMessageFromDcm = [CtInvocationImpl][CtVariableReadImpl]jsonFromDcm.getString([CtLiteralImpl]"status");
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"validation passed".equals([CtVariableReadImpl]statusMessageFromDcm)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtLiteralImpl]"Checksum Validation passed for DCM.");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String storageDriver = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getDataverseContext().getEffectiveStorageDriverId();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String uploadFolder = [CtInvocationImpl][CtVariableReadImpl]jsonFromDcm.getString([CtLiteralImpl]"uploadFolder");
                [CtLocalVariableImpl][CtTypeReferenceImpl]int totalSize = [CtInvocationImpl][CtVariableReadImpl]jsonFromDcm.getInt([CtLiteralImpl]"totalSize");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String storageDriverType = [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"dataverse.file." + [CtVariableReadImpl]storageDriver) + [CtLiteralImpl]".type");
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]storageDriverType.equals([CtLiteralImpl]"file")) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtLiteralImpl]"File storage driver used for (dataset id={0})", [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.batch.jobs.importer.ImportMode importMode = [CtFieldReadImpl]edu.harvard.iq.dataverse.batch.jobs.importer.ImportMode.MERGE;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject jsonFromImportJobKickoff = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.ImportFromFileSystemCommand([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtVariableReadImpl]dataset, [CtVariableReadImpl]uploadFolder, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Long([CtVariableReadImpl]totalSize), [CtVariableReadImpl]importMode));
                        [CtLocalVariableImpl][CtTypeReferenceImpl]long jobId = [CtInvocationImpl][CtVariableReadImpl]jsonFromImportJobKickoff.getInt([CtLiteralImpl]"executionId");
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtVariableReadImpl]jsonFromImportJobKickoff.getString([CtLiteralImpl]"message");
                        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder job = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
                        [CtInvocationImpl][CtVariableReadImpl]job.add([CtLiteralImpl]"jobId", [CtVariableReadImpl]jobId);
                        [CtInvocationImpl][CtVariableReadImpl]job.add([CtLiteralImpl]"message", [CtVariableReadImpl]message);
                        [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]job);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtVariableReadImpl]wr.getMessage();
                        [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Uploaded files have passed checksum validation but something went wrong while attempting to put the files into Dataverse. Message was '" + [CtVariableReadImpl]message) + [CtLiteralImpl]"'.");
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]storageDriverType.equals([CtLiteralImpl]"s3")) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]INFO, [CtLiteralImpl]"S3 storage driver used for DCM (dataset id={0})", [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// Where the lifting is actually done, moving the s3 files over and having dataverse know of the existance of the package
                        [CtFieldReadImpl]s3PackageImporter.copyFromS3([CtVariableReadImpl]dataset, [CtVariableReadImpl]uploadFolder);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataFile packageFile = [CtInvocationImpl][CtFieldReadImpl]s3PackageImporter.createPackageDataFile([CtVariableReadImpl]dataset, [CtVariableReadImpl]uploadFolder, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Long([CtVariableReadImpl]totalSize));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]packageFile == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]"S3 File package import failed.");
                            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"S3 File package import failed.");
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock dcmLock = [CtInvocationImpl][CtVariableReadImpl]dataset.getLockFor([CtTypeAccessImpl]DatasetLock.Reason.DcmUpload);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dcmLock == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]WARNING, [CtLiteralImpl]"Dataset not locked for DCM upload");
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]datasetService.removeDatasetLocks([CtVariableReadImpl]dataset, [CtTypeAccessImpl]DatasetLock.Reason.DcmUpload);
                            [CtInvocationImpl][CtVariableReadImpl]dataset.removeLock([CtVariableReadImpl]dcmLock);
                        }
                        [CtIfImpl][CtCommentImpl]// update version using the command engine to enforce user permissions and constraints
                        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getVersions().size() == [CtLiteralImpl]1) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getLatestVersion().getVersionState() == [CtFieldReadImpl]DatasetVersion.VersionState.DRAFT)) [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset> cmd;
                                [CtAssignmentImpl][CtVariableWriteImpl]cmd = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand([CtVariableReadImpl]dataset, [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest([CtVariableReadImpl]authenticatedUser, [CtLiteralImpl](([CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest) (null))));
                                [CtInvocationImpl][CtFieldReadImpl]commandEngine.submit([CtVariableReadImpl]cmd);
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.exception.CommandException ex) [CtBlockImpl]{
                                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtBinaryOperatorImpl][CtLiteralImpl]"CommandException updating DatasetVersion from batch job: " + [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
                            }
                        } else [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String constraintError = [CtBinaryOperatorImpl][CtLiteralImpl]"ConstraintException updating DatasetVersion form batch job: dataset must be a " + [CtLiteralImpl]"single version in draft mode.";
                            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtVariableReadImpl]constraintError);
                        }
                        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder job = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
                        [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]job);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtInvocationImpl][CtVariableReadImpl]e.getMessage();
                        [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Uploaded files have passed checksum validation but something went wrong while attempting to move the files into Dataverse. Message was '" + [CtVariableReadImpl]message) + [CtLiteralImpl]"'.");
                    }
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Invalid storage driver in Dataverse, not compatible with dcm");
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"validation failed".equals([CtVariableReadImpl]statusMessageFromDcm)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser> distinctAuthors = [CtInvocationImpl][CtFieldReadImpl]permissionService.getDistinctUsersWithPermissionOn([CtTypeAccessImpl]Permission.EditDataset, [CtVariableReadImpl]dataset);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]distinctAuthors.values().forEach([CtLambdaImpl]([CtParameterImpl] value) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]userNotificationService.sendNotification([CtVariableReadImpl](([CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser) (value)), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.Timestamp([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date().getTime()), [CtVariableReadImpl]UserNotification.Type.CHECKSUMFAIL, [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                });
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser> superUsers = [CtInvocationImpl][CtFieldReadImpl]authenticationServiceBean.findSuperUsers();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]superUsers != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]superUsers.isEmpty())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]superUsers.forEach([CtLambdaImpl]([CtParameterImpl] au) -> [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]userNotificationService.sendNotification([CtVariableReadImpl]au, [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.sql.Timestamp([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date().getTime()), [CtVariableReadImpl]UserNotification.Type.CHECKSUMFAIL, [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                    });
                }
                [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"User notified about checksum validation failure.");
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected status cannot be processed: " + [CtVariableReadImpl]statusMessageFromDcm);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]ex.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/submitForReview")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response submitForReview([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset updatedDataset = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.SubmitDatasetForReviewCommand([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder result = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean inReview = [CtInvocationImpl][CtVariableReadImpl]updatedDataset.isLockedFor([CtTypeAccessImpl]DatasetLock.Reason.InReview);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtLiteralImpl]"inReview", [CtVariableReadImpl]inReview);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtLiteralImpl]"message", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset id " + [CtInvocationImpl][CtVariableReadImpl]updatedDataset.getId()) + [CtLiteralImpl]" has been submitted for review.");
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]result);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/returnToAuthor")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response returnToAuthor([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String jsonBody) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]jsonBody == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]jsonBody.isEmpty()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"You must supply JSON to this API endpoint and it must contain a reason for returning the dataset (field: reasonForReturn).");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.StringReader rdr = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]jsonBody);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject json = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createReader([CtVariableReadImpl]rdr).readObject();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String reasonForReturn = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtVariableWriteImpl]reasonForReturn = [CtInvocationImpl][CtVariableReadImpl]json.getString([CtLiteralImpl]"reasonForReturn");
            [CtIfImpl][CtCommentImpl]// TODO: Once we add a box for the curator to type into, pass the reason for return to the ReturnDatasetToAuthorCommand and delete this check and call to setReturnReason on the API side.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]reasonForReturn == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]reasonForReturn.isEmpty()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"You must enter a reason for returning a dataset to the author(s).");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser authenticatedUser = [CtInvocationImpl]findAuthenticatedUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset updatedDataset = [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.ReturnDatasetToAuthorCommand([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]authenticatedUser), [CtVariableReadImpl]dataset, [CtVariableReadImpl]reasonForReturn));
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder result = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtLiteralImpl]"inReview", [CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtLiteralImpl]"message", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset id " + [CtInvocationImpl][CtVariableReadImpl]updatedDataset.getId()) + [CtLiteralImpl]" has been sent back to the author(s).");
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]result);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/uploadsid")
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getUploadUrl([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean canUpdateDataset = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]canUpdateDataset = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]permissionSvc.requestOn([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtVariableReadImpl]dataset).canIssue([CtFieldReadImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand.class);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Exception thrown while trying to figure out permissions while getting upload URL for dataset id " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ex.getLocalizedMessage());
                [CtThrowImpl]throw [CtVariableReadImpl]ex;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]canUpdateDataset) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"You are not permitted to upload files to this dataset.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataaccess.S3AccessIO<[CtWildcardReferenceImpl]?> s3io = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.FileUtil.getS3AccessForDirectUpload([CtVariableReadImpl]dataset);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s3io == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtBinaryOperatorImpl][CtLiteralImpl]"Direct upload not supported for files in this dataset: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String storageIdentifier = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]url = [CtInvocationImpl][CtVariableReadImpl]s3io.generateTemporaryS3UploadUrl();
                [CtAssignmentImpl][CtVariableWriteImpl]storageIdentifier = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.FileUtil.getStorageIdentifierFromLocation([CtInvocationImpl][CtVariableReadImpl]s3io.getStorageLocation());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException io) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtInvocationImpl][CtVariableReadImpl]io.getMessage());
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtVariableReadImpl]io, [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Could not create process direct upload request"));
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder response = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder().add([CtLiteralImpl]"url", [CtVariableReadImpl]url).add([CtLiteralImpl]"storageIdentifier", [CtVariableReadImpl]storageIdentifier);
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]response);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/uploadurls")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getMPUploadUrls([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"size")
    [CtTypeReferenceImpl]long fileSize) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean canUpdateDataset = [CtLiteralImpl]false;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]canUpdateDataset = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]permissionSvc.requestOn([CtInvocationImpl]createDataverseRequest([CtInvocationImpl]findUserOrDie()), [CtVariableReadImpl]dataset).canIssue([CtFieldReadImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand.class);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Exception thrown while trying to figure out permissions while getting upload URLs for dataset id " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ex.getLocalizedMessage());
                [CtThrowImpl]throw [CtVariableReadImpl]ex;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]canUpdateDataset) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"You are not permitted to upload files to this dataset.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.dataaccess.S3AccessIO<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataFile> s3io = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.FileUtil.getS3AccessForDirectUpload([CtVariableReadImpl]dataset);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]s3io == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtBinaryOperatorImpl][CtLiteralImpl]"Direct upload not supported for files in this dataset: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder response = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String storageIdentifier = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]storageIdentifier = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.FileUtil.getStorageIdentifierFromLocation([CtInvocationImpl][CtVariableReadImpl]s3io.getStorageLocation());
                [CtAssignmentImpl][CtVariableWriteImpl]response = [CtInvocationImpl][CtVariableReadImpl]s3io.generateTemporaryS3UploadUrls([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getGlobalId().asString(), [CtVariableReadImpl]storageIdentifier, [CtVariableReadImpl]fileSize);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException io) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtInvocationImpl][CtVariableReadImpl]io.getMessage());
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtVariableReadImpl]io, [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Could not create process direct upload request"));
            }
            [CtInvocationImpl][CtVariableReadImpl]response.add([CtLiteralImpl]"storageIdentifier", [CtVariableReadImpl]storageIdentifier);
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]response);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"mpupload")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response abortMPUpload([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"globalid")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"storageidentifier")
    [CtTypeReferenceImpl]java.lang.String storageidentifier, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"uploadid")
    [CtTypeReferenceImpl]java.lang.String uploadId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl][CtFieldReadImpl]datasetSvc.findByGlobalId([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtCommentImpl]// Allow the API to be used within a session (e.g. for direct upload in the UI)
            [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User user = [CtInvocationImpl][CtFieldReadImpl]session.getUser();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isAuthenticated()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl]findAuthenticatedUserOrDie();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Exception thrown while trying to figure out permissions while getting aborting upload for dataset id " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ex.getLocalizedMessage());
                    [CtThrowImpl]throw [CtVariableReadImpl]ex;
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allowed = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataset != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]allowed = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]permissionSvc.requestOn([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user), [CtVariableReadImpl]dataset).canIssue([CtFieldReadImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand.class);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]/* The only legitimate case where a global id won't correspond to a dataset is
                for uploads during creation. Given that this call will still fail unless all
                three parameters correspond to an active multipart upload, it should be safe
                to allow the attempt for an authenticated user. If there are concerns about
                permissions, one could check with the current design that the user is allowed
                to create datasets in some dataverse that is configured to use the storage
                provider specified in the storageidentifier, but testing for the ability to
                create a dataset in a specific dataverse would requiring changing the design
                somehow (e.g. adding the ownerId to this call).
                 */
                [CtVariableWriteImpl]allowed = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]allowed) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"You are not permitted to abort file uploads with the supplied parameters.");
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataaccess.S3AccessIO.abortMultipartUpload([CtVariableReadImpl]idSupplied, [CtVariableReadImpl]storageidentifier, [CtVariableReadImpl]uploadId);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException io) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Multipart upload abort failed for uploadId: " + [CtVariableReadImpl]uploadId) + [CtLiteralImpl]" storageidentifier=") + [CtVariableReadImpl]storageidentifier) + [CtLiteralImpl]" dataset Id: ") + [CtInvocationImpl][CtVariableReadImpl]dataset.getId());
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtInvocationImpl][CtVariableReadImpl]io.getMessage());
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtVariableReadImpl]io, [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Could not abort multipart upload"));
            }
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.ws.rs.core.Response.noContent().build();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"mpupload")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response completeMPUpload([CtParameterImpl][CtTypeReferenceImpl]java.lang.String partETagBody, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"globalid")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"storageidentifier")
    [CtTypeReferenceImpl]java.lang.String storageidentifier, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"uploadid")
    [CtTypeReferenceImpl]java.lang.String uploadId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl][CtFieldReadImpl]datasetSvc.findByGlobalId([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtCommentImpl]// Allow the API to be used within a session (e.g. for direct upload in the UI)
            [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User user = [CtInvocationImpl][CtFieldReadImpl]session.getUser();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isAuthenticated()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl]findAuthenticatedUserOrDie();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Exception thrown while trying to figure out permissions to complete mpupload for dataset id " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtVariableReadImpl]ex.getLocalizedMessage());
                    [CtThrowImpl]throw [CtVariableReadImpl]ex;
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean allowed = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dataset != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]allowed = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]permissionSvc.requestOn([CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]user), [CtVariableReadImpl]dataset).canIssue([CtFieldReadImpl]edu.harvard.iq.dataverse.engine.command.impl.UpdateDatasetVersionCommand.class);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]/* The only legitimate case where a global id won't correspond to a dataset is
                for uploads during creation. Given that this call will still fail unless all
                three parameters correspond to an active multipart upload, it should be safe
                to allow the attempt for an authenticated user. If there are concerns about
                permissions, one could check with the current design that the user is allowed
                to create datasets in some dataverse that is configured to use the storage
                provider specified in the storageidentifier, but testing for the ability to
                create a dataset in a specific dataverse would requiring changing the design
                somehow (e.g. adding the ownerId to this call).
                 */
                [CtVariableWriteImpl]allowed = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]allowed) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"You are not permitted to complete file uploads with the supplied parameters.");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.amazonaws.services.s3.model.PartETag> eTagList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.amazonaws.services.s3.model.PartETag>();
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtLiteralImpl]"Etags: " + [CtVariableReadImpl]partETagBody);
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonReader jsonReader = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringReader([CtVariableReadImpl]partETagBody));
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObject object = [CtInvocationImpl][CtVariableReadImpl]jsonReader.readObject();
                [CtInvocationImpl][CtVariableReadImpl]jsonReader.close();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partNo : [CtInvocationImpl][CtVariableReadImpl]object.keySet()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]eTagList.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.amazonaws.services.s3.model.PartETag([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtVariableReadImpl]partNo), [CtInvocationImpl][CtVariableReadImpl]object.getString([CtVariableReadImpl]partNo)));
                }
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.amazonaws.services.s3.model.PartETag et : [CtVariableReadImpl]eTagList) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Part: " + [CtInvocationImpl][CtVariableReadImpl]et.getPartNumber()) + [CtLiteralImpl]" : ") + [CtInvocationImpl][CtVariableReadImpl]et.getETag());
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.json.JsonException je) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.info([CtBinaryOperatorImpl][CtLiteralImpl]"Unable to parse eTags from: " + [CtVariableReadImpl]partETagBody);
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtVariableReadImpl]je, [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Could not complete multipart upload"));
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataaccess.S3AccessIO.completeMultipartUpload([CtVariableReadImpl]idSupplied, [CtVariableReadImpl]storageidentifier, [CtVariableReadImpl]uploadId, [CtVariableReadImpl]eTagList);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException io) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Multipart upload completion failed for uploadId: " + [CtVariableReadImpl]uploadId) + [CtLiteralImpl]" storageidentifier=") + [CtVariableReadImpl]storageidentifier) + [CtLiteralImpl]" globalId: ") + [CtVariableReadImpl]idSupplied);
                [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.warning([CtInvocationImpl][CtVariableReadImpl]io.getMessage());
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataaccess.S3AccessIO.abortMultipartUpload([CtVariableReadImpl]idSupplied, [CtVariableReadImpl]storageidentifier, [CtVariableReadImpl]uploadId);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.severe([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Also unable to abort the upload (and release the space on S3 for uploadId: " + [CtVariableReadImpl]uploadId) + [CtLiteralImpl]" storageidentifier=") + [CtVariableReadImpl]storageidentifier) + [CtLiteralImpl]" globalId: ") + [CtVariableReadImpl]idSupplied);
                    [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.severe([CtInvocationImpl][CtVariableReadImpl]io.getMessage());
                }
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtVariableReadImpl]io, [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.INTERNAL_SERVER_ERROR, [CtLiteralImpl]"Could not complete multipart upload"));
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"Multipart Upload completed");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a File to an existing Dataset
     *
     * @param idSupplied
     * @param jsonData
     * @param fileInputStream
     * @param contentDispositionHeader
     * @param formDataBodyPart
     * @return  */
    [CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/add")
    [CtAnnotationImpl]@javax.ws.rs.Consumes([CtFieldReadImpl]javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response addFileToDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@org.glassfish.jersey.media.multipart.FormDataParam([CtLiteralImpl]"jsonData")
    [CtTypeReferenceImpl]java.lang.String jsonData, [CtParameterImpl][CtAnnotationImpl]@org.glassfish.jersey.media.multipart.FormDataParam([CtLiteralImpl]"file")
    [CtTypeReferenceImpl]java.io.InputStream fileInputStream, [CtParameterImpl][CtAnnotationImpl]@org.glassfish.jersey.media.multipart.FormDataParam([CtLiteralImpl]"file")
    [CtTypeReferenceImpl]org.glassfish.jersey.media.multipart.FormDataContentDisposition contentDispositionHeader, [CtParameterImpl][CtAnnotationImpl]@org.glassfish.jersey.media.multipart.FormDataParam([CtLiteralImpl]"file")
    final [CtTypeReferenceImpl]org.glassfish.jersey.media.multipart.FormDataBodyPart formDataBodyPart) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]systemConfig.isHTTPUpload()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.SERVICE_UNAVAILABLE, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"file.api.httpDisabled"));
        }
        [CtLocalVariableImpl][CtCommentImpl]// -------------------------------------
        [CtCommentImpl]// (1) Get the user from the API key
        [CtCommentImpl]// -------------------------------------
        [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User authUser;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]authUser = [CtInvocationImpl]findUserOrDie();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"file.addreplace.error.auth"));
        }
        [CtLocalVariableImpl][CtCommentImpl]// -------------------------------------
        [CtCommentImpl]// (2) Get the Dataset Id
        [CtCommentImpl]// 
        [CtCommentImpl]// -------------------------------------
        [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
        [CtForEachImpl][CtCommentImpl]// ------------------------------------
        [CtCommentImpl]// (2a) Make sure dataset does not have package file
        [CtCommentImpl]// 
        [CtCommentImpl]// --------------------------------------
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dv : [CtInvocationImpl][CtVariableReadImpl]dataset.getVersions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dv.isHasPackageFile()) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"file.api.alreadyHasPackageFile"));
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// (2a) Load up optional params via JSON
        [CtCommentImpl]// ---------------------------------------
        [CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.OptionalFileParams optionalFileParams = [CtLiteralImpl]null;
        [CtInvocationImpl]msgt([CtBinaryOperatorImpl][CtLiteralImpl]"(api) jsonData: " + [CtVariableReadImpl]jsonData);
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]optionalFileParams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.OptionalFileParams([CtVariableReadImpl]jsonData);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.DataFileTagException ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.lang.ClassCastException | [CtTypeReferenceImpl]google ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"file.addreplace.error.parsing"));
        }
        [CtLocalVariableImpl][CtCommentImpl]// -------------------------------------
        [CtCommentImpl]// (3) Get the file name and content type
        [CtCommentImpl]// -------------------------------------
        [CtTypeReferenceImpl]java.lang.String newFilename = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newFileContentType = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newStorageIdentifier = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]contentDispositionHeader) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]optionalFileParams.hasStorageIdentifier()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]newStorageIdentifier = [CtInvocationImpl][CtVariableReadImpl]optionalFileParams.getStorageIdentifier();
                [CtIfImpl][CtCommentImpl]// ToDo - check that storageIdentifier is valid
                if ([CtInvocationImpl][CtVariableReadImpl]optionalFileParams.hasFileName()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]newFilename = [CtInvocationImpl][CtVariableReadImpl]optionalFileParams.getFileName();
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]optionalFileParams.hasMimetype()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]newFileContentType = [CtInvocationImpl][CtVariableReadImpl]optionalFileParams.getMimeType();
                    }
                }
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]BAD_REQUEST, [CtLiteralImpl]"You must upload a file or provide a storageidentifier, filename, and mimetype.");
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]newFilename = [CtInvocationImpl][CtVariableReadImpl]contentDispositionHeader.getFileName();
            [CtAssignmentImpl][CtVariableWriteImpl]newFileContentType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]formDataBodyPart.getMediaType().toString();
        }
        [CtInvocationImpl][CtCommentImpl]// -------------------
        [CtCommentImpl]// (3) Create the AddReplaceFileHelper object
        [CtCommentImpl]// -------------------
        msg([CtLiteralImpl]"ADD!");
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest dvRequest2 = [CtInvocationImpl]createDataverseRequest([CtVariableReadImpl]authUser);
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.AddReplaceFileHelper addFileHelper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.AddReplaceFileHelper([CtVariableReadImpl]dvRequest2, [CtFieldReadImpl]ingestService, [CtFieldReadImpl]datasetService, [CtFieldReadImpl]fileService, [CtFieldReadImpl]permissionSvc, [CtFieldReadImpl]commandEngine, [CtFieldReadImpl]systemConfig);
        [CtInvocationImpl][CtCommentImpl]// -------------------
        [CtCommentImpl]// (4) Run "runAddFileByDatasetId"
        [CtCommentImpl]// -------------------
        [CtVariableReadImpl]addFileHelper.runAddFileByDataset([CtVariableReadImpl]dataset, [CtVariableReadImpl]newFilename, [CtVariableReadImpl]newFileContentType, [CtVariableReadImpl]newStorageIdentifier, [CtVariableReadImpl]fileInputStream, [CtVariableReadImpl]optionalFileParams);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]addFileHelper.hasError()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtInvocationImpl][CtVariableReadImpl]addFileHelper.getHttpErrorCode(), [CtInvocationImpl][CtVariableReadImpl]addFileHelper.getErrorMessagesAsString([CtLiteralImpl]"\n"));
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String successMsg = [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"file.addreplace.success.add");
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// msgt("as String: " + addFileHelper.getSuccessResult());
                [CtJavaDocImpl]/**
                 *
                 * @todo We need a consistent, sane way to communicate a human
                readable message to an API client suitable for human
                consumption. Imagine if the UI were built in Angular or React
                and we want to return a message from the API as-is to the
                user. Human readable.
                 */
                [CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"successMsg: " + [CtVariableReadImpl]successMsg);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String duplicateWarning = [CtInvocationImpl][CtVariableReadImpl]addFileHelper.getDuplicateFileWarning();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]duplicateWarning != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]duplicateWarning.isEmpty())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]addFileHelper.getDuplicateFileWarning(), [CtInvocationImpl][CtVariableReadImpl]addFileHelper.getSuccessResultAsJsonObjectBuilder());
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]addFileHelper.getSuccessResultAsJsonObjectBuilder());
                }
                [CtCommentImpl]// "Look at that!  You added a file! (hey hey, it may have worked)");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.datasetutility.NoFilesException ex) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Files.class.getName()).log([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]SEVERE, [CtLiteralImpl]null, [CtVariableReadImpl]ex);
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"NoFileException!  Serious Error! See administrator!");
            }
        }
    }[CtCommentImpl]// end: addFileToDataset


    [CtMethodImpl]private [CtTypeReferenceImpl]void msg([CtParameterImpl][CtTypeReferenceImpl]java.lang.String m) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// System.out.println(m);
        [CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtVariableReadImpl]m);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void dashes() [CtBlockImpl]{
        [CtInvocationImpl]msg([CtLiteralImpl]"----------------");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void msgt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String m) [CtBlockImpl]{
        [CtInvocationImpl]dashes();
        [CtInvocationImpl]msg([CtVariableReadImpl]m);
        [CtInvocationImpl]dashes();
    }

    [CtMethodImpl]public static <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T handleVersion([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionId, [CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.Datasets.DsVersionHandler<[CtTypeParameterReferenceImpl]T> hdl) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtSwitchImpl]switch ([CtVariableReadImpl]versionId) {
            [CtCaseImpl]case [CtLiteralImpl]":latest" :
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hdl.handleLatest();
            [CtCaseImpl]case [CtLiteralImpl]":draft" :
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hdl.handleDraft();
            [CtCaseImpl]case [CtLiteralImpl]":latest-published" :
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hdl.handleLatestPublished();
            [CtCaseImpl]default :
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] versions = [CtInvocationImpl][CtVariableReadImpl]versionId.split([CtLiteralImpl]"\\.");
                    [CtSwitchImpl]switch ([CtFieldReadImpl][CtVariableReadImpl]versions.length) {
                        [CtCaseImpl]case [CtLiteralImpl]1 :
                            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hdl.handleSpecific([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtArrayReadImpl][CtVariableReadImpl]versions[[CtLiteralImpl]0]), [CtLiteralImpl](([CtTypeReferenceImpl]long) (0.0)));
                        [CtCaseImpl]case [CtLiteralImpl]2 :
                            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]hdl.handleSpecific([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtArrayReadImpl][CtVariableReadImpl]versions[[CtLiteralImpl]0]), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtArrayReadImpl][CtVariableReadImpl]versions[[CtLiteralImpl]1]));
                        [CtCaseImpl]default :
                            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Illegal version identifier '" + [CtVariableReadImpl]versionId) + [CtLiteralImpl]"'"));
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NumberFormatException nfe) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Illegal version identifier '" + [CtVariableReadImpl]versionId) + [CtLiteralImpl]"'"));
                }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion getDatasetVersionOrDie([CtParameterImpl]final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest req, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionNumber, [CtParameterImpl]final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset ds, [CtParameterImpl][CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion dsv = [CtInvocationImpl]execCommand([CtInvocationImpl]edu.harvard.iq.dataverse.api.Datasets.handleVersion([CtVariableReadImpl]versionNumber, [CtNewClassImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.Datasets.DsVersionHandler<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion>>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion> handleLatest() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetLatestAccessibleDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion> handleDraft() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetDraftDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion> handleSpecific([CtParameterImpl][CtTypeReferenceImpl]long major, [CtParameterImpl][CtTypeReferenceImpl]long minor) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetSpecificPublishedDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds, [CtVariableReadImpl]major, [CtVariableReadImpl]minor);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.Command<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetVersion> handleLatestPublished() [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetLatestPublishedDatasetVersionCommand([CtVariableReadImpl]req, [CtVariableReadImpl]ds);
            }
        }));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dsv == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dsv.getId() == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse([CtInvocationImpl]notFound([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Dataset version " + [CtVariableReadImpl]versionNumber) + [CtLiteralImpl]" of dataset ") + [CtInvocationImpl][CtVariableReadImpl]ds.getId()) + [CtLiteralImpl]" not found"));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]dsv.isReleased()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry entry = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountLoggingServiceBean.MakeDataCountEntry([CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers, [CtFieldReadImpl]dvRequestService, [CtVariableReadImpl]ds);
            [CtInvocationImpl][CtFieldReadImpl]mdcLogService.logEntry([CtVariableReadImpl]entry);
        }
        [CtReturnImpl]return [CtVariableReadImpl]dsv;
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/locks")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getLocks([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"type")
    [CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock.Reason lockType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock> locks;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lockType == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]locks = [CtInvocationImpl][CtVariableReadImpl]dataset.getLocks();
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// request for a specific type lock:
                [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock lock = [CtInvocationImpl][CtVariableReadImpl]dataset.getLockFor([CtVariableReadImpl]lockType);
                [CtAssignmentImpl][CtVariableWriteImpl]locks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lock != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]locks.add([CtVariableReadImpl]lock);
                }
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]locks.stream().map([CtLambdaImpl]([CtParameterImpl] lock) -> [CtInvocationImpl]json([CtVariableReadImpl]lock)).collect([CtInvocationImpl]toJsonArray()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/locks")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response deleteLocks([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"type")
    [CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock.Reason lockType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user = [CtInvocationImpl]findAuthenticatedUserOrDie();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isSuperuser()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtVariableReadImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"This API end point can be used by superusers only.");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lockType == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Set<[CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock.Reason> locks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]HashSet<>();
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock lock : [CtInvocationImpl][CtVariableReadImpl]dataset.getLocks()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]locks.add([CtInvocationImpl][CtVariableReadImpl]lock.getReason());
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]locks.isEmpty()) [CtBlockImpl]{
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock.Reason locktype : [CtVariableReadImpl]locks) [CtBlockImpl]{
                            [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.RemoveLockCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset, [CtVariableReadImpl]locktype));
                            [CtAssignmentImpl][CtCommentImpl]// refresh the dataset:
                            [CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
                        }
                        [CtTryImpl][CtCommentImpl]// kick of dataset reindexing, in case the locks removed
                        [CtCommentImpl]// affected the search card:
                        try [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]indexService.indexDataset([CtVariableReadImpl]dataset, [CtLiteralImpl]true);
                        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.solr.client.solrj.SolrServerException e) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String failureLogText = [CtBinaryOperatorImpl][CtLiteralImpl]"Post lock removal indexing failed. You can kickoff a re-index of this dataset with: \r\n curl http://localhost:8080/api/admin/index/datasets/" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getId().toString();
                            [CtOperatorAssignmentImpl][CtVariableWriteImpl]failureLogText += [CtBinaryOperatorImpl][CtLiteralImpl]"\r\n" + [CtInvocationImpl][CtTypeAccessImpl]e.getLocalizedMessage();
                            [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.batch.util.LoggingUtil.writeOnSuccessFailureLog([CtLiteralImpl]null, [CtVariableReadImpl]failureLogText, [CtVariableReadImpl]dataset);
                        }
                        [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"locks removed");
                    }
                    [CtReturnImpl]return [CtInvocationImpl]ok([CtLiteralImpl]"dataset not locked");
                }
                [CtLocalVariableImpl][CtCommentImpl]// request for a specific type lock:
                [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock lock = [CtInvocationImpl][CtVariableReadImpl]dataset.getLockFor([CtVariableReadImpl]lockType);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lock != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.RemoveLockCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset, [CtInvocationImpl][CtVariableReadImpl]lock.getReason()));
                    [CtAssignmentImpl][CtCommentImpl]// refresh the dataset:
                    [CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
                    [CtTryImpl][CtCommentImpl]// ... and kick of dataset reindexing, in case the lock removed
                    [CtCommentImpl]// affected the search card:
                    try [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]indexService.indexDataset([CtVariableReadImpl]dataset, [CtLiteralImpl]true);
                    }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.solr.client.solrj.SolrServerException e) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String failureLogText = [CtBinaryOperatorImpl][CtLiteralImpl]"Post lock removal indexing failed. You can kickoff a re-index of this dataset with: \r\n curl http://localhost:8080/api/admin/index/datasets/" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getId().toString();
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]failureLogText += [CtBinaryOperatorImpl][CtLiteralImpl]"\r\n" + [CtInvocationImpl][CtTypeAccessImpl]e.getLocalizedMessage();
                        [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.batch.util.LoggingUtil.writeOnSuccessFailureLog([CtLiteralImpl]null, [CtVariableReadImpl]failureLogText, [CtVariableReadImpl]dataset);
                    }
                    [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"lock type " + [CtInvocationImpl][CtVariableReadImpl]lock.getReason()) + [CtLiteralImpl]" removed");
                }
                [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"no lock type " + [CtVariableReadImpl]lockType) + [CtLiteralImpl]" on the dataset");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]wr.getResponse();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.POST
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/lock/{type}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response lockDataset([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"type")
    [CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock.Reason lockType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user = [CtInvocationImpl]findAuthenticatedUserOrDie();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isSuperuser()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtVariableReadImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"This API end point can be used by superusers only.");
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock lock = [CtInvocationImpl][CtVariableReadImpl]dataset.getLockFor([CtVariableReadImpl]lockType);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]lock != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtVariableReadImpl]Response.Status.FORBIDDEN, [CtBinaryOperatorImpl][CtLiteralImpl]"dataset already locked with lock type " + [CtVariableReadImpl]lockType);
                }
                [CtAssignmentImpl][CtVariableWriteImpl]lock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetLock([CtVariableReadImpl]lockType, [CtVariableReadImpl]user);
                [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.AddLockCommand([CtVariableReadImpl]req, [CtVariableReadImpl]dataset, [CtVariableReadImpl]lock));
                [CtAssignmentImpl][CtCommentImpl]// refresh the dataset:
                [CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
                [CtTryImpl][CtCommentImpl]// ... and kick of dataset reindexing:
                try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]indexService.indexDataset([CtVariableReadImpl]dataset, [CtLiteralImpl]true);
                }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.solr.client.solrj.SolrServerException e) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String failureLogText = [CtBinaryOperatorImpl][CtLiteralImpl]"Post add lock indexing failed. You can kickoff a re-index of this dataset with: \r\n curl http://localhost:8080/api/admin/index/datasets/" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getId().toString();
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]failureLogText += [CtBinaryOperatorImpl][CtLiteralImpl]"\r\n" + [CtInvocationImpl][CtTypeAccessImpl]e.getLocalizedMessage();
                    [CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.batch.util.LoggingUtil.writeOnSuccessFailureLog([CtLiteralImpl]null, [CtVariableReadImpl]failureLogText, [CtVariableReadImpl]dataset);
                }
                [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtLiteralImpl]"dataset locked with lock type " + [CtVariableReadImpl]lockType);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]wr.getResponse();
            }
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/makeDataCount/citations")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getMakeDataCountCitations([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonArrayBuilder datasetsCitations = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createArrayBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.DatasetExternalCitations> externalCitations = [CtInvocationImpl][CtFieldReadImpl]datasetExternalCitationsService.getDatasetExternalCitationsByDataset([CtVariableReadImpl]dataset);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.DatasetExternalCitations citation : [CtVariableReadImpl]externalCitations) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder candidateObj = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
                [CtInvocationImpl][CtJavaDocImpl]/**
                 * In the future we can imagine storing and presenting more
                 * information about the citation such as the title of the paper
                 * and the names of the authors. For now, we'll at least give
                 * the URL of the citation so people can click and find out more
                 * about the citation.
                 */
                [CtVariableReadImpl]candidateObj.add([CtLiteralImpl]"citationUrl", [CtInvocationImpl][CtVariableReadImpl]citation.getCitedByUrl());
                [CtInvocationImpl][CtVariableReadImpl]datasetsCitations.add([CtVariableReadImpl]candidateObj);
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]datasetsCitations);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/makeDataCount/{metric}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getMakeDataCountMetricCurrentMonth([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"metric")
    [CtTypeReferenceImpl]java.lang.String metricSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"country")
    [CtTypeReferenceImpl]java.lang.String country) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nullCurrentMonth = [CtLiteralImpl]null;
        [CtReturnImpl]return [CtInvocationImpl]getMakeDataCountMetric([CtVariableReadImpl]idSupplied, [CtVariableReadImpl]metricSupplied, [CtVariableReadImpl]nullCurrentMonth, [CtVariableReadImpl]country);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/storagesize")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getStorageSize([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dvIdtf, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"includeCached")
    [CtTypeReferenceImpl]boolean includeCached, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.datasize.storage"), [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetDatasetStorageSizeCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf), [CtVariableReadImpl]includeCached, [CtVariableReadImpl]GetDatasetStorageSizeCommand.Mode.STORAGE, [CtLiteralImpl]null)))));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/versions/{versionId}/downloadsize")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getDownloadSize([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dvIdtf, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"versionId")
    [CtTypeReferenceImpl]java.lang.String version, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.util.BundleUtil.getStringFromBundle([CtLiteralImpl]"datasets.api.datasize.download"), [CtInvocationImpl]execCommand([CtConstructorCallImpl]new [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.impl.GetDatasetStorageSizeCommand([CtVariableReadImpl]req, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf), [CtLiteralImpl]false, [CtVariableReadImpl]GetDatasetStorageSizeCommand.Mode.DOWNLOAD, [CtInvocationImpl]getDatasetVersionOrDie([CtVariableReadImpl]req, [CtVariableReadImpl]version, [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf), [CtVariableReadImpl]uriInfo, [CtVariableReadImpl]headers))))));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{id}/makeDataCount/{metric}/{yyyymm}")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getMakeDataCountMetric([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"id")
    [CtTypeReferenceImpl]java.lang.String idSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"metric")
    [CtTypeReferenceImpl]java.lang.String metricSupplied, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"yyyymm")
    [CtTypeReferenceImpl]java.lang.String yyyymm, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.QueryParam([CtLiteralImpl]"country")
    [CtTypeReferenceImpl]java.lang.String country) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]idSupplied);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.util.json.NullSafeJsonBuilder jsonObjectBuilder = [CtInvocationImpl]jsonObjectBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountUtil.MetricType metricType = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]metricType = [CtInvocationImpl][CtTypeAccessImpl]MakeDataCountUtil.MetricType.fromString([CtVariableReadImpl]metricSupplied);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.IllegalArgumentException ex) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String monthYear = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]yyyymm != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// We add "-01" because we store "2018-05-01" rather than "2018-05" in the "monthyear" column.
                [CtCommentImpl]// Dates come to us as "2018-05-01" in the SUSHI JSON ("begin-date") and we decided to store them as-is.
                [CtVariableWriteImpl]monthYear = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.metrics.MetricsUtil.sanitizeYearMonthUserInput([CtVariableReadImpl]yyyymm) + [CtLiteralImpl]"-01";
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]country != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]country = [CtInvocationImpl][CtVariableReadImpl]country.toLowerCase();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.makedatacount.MakeDataCountUtil.isValidCountryCode([CtVariableReadImpl]country)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Country must be one of the ISO 1366 Country Codes");
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.makedatacount.DatasetMetrics datasetMetrics = [CtInvocationImpl][CtFieldReadImpl]datasetMetricsSvc.getDatasetMetricsByDatasetForDisplay([CtVariableReadImpl]dataset, [CtVariableReadImpl]monthYear, [CtVariableReadImpl]country);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]datasetMetrics == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"No metrics available for dataset " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]" for ") + [CtVariableReadImpl]yyyymm) + [CtLiteralImpl]" for country code ") + [CtVariableReadImpl]country) + [CtLiteralImpl]".");
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsTotal() + [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsTotal()) == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"No metrics available for dataset " + [CtInvocationImpl][CtVariableReadImpl]dataset.getId()) + [CtLiteralImpl]" for ") + [CtVariableReadImpl]yyyymm) + [CtLiteralImpl]" for country code ") + [CtVariableReadImpl]country) + [CtLiteralImpl]".");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsTotalRegular = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsUniqueRegular = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsTotalRegular = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsUniqueRegular = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsTotalMachine = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsUniqueMachine = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsTotalMachine = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsUniqueMachine = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsTotal = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long viewsUnique = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsTotal = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long downloadsUnique = [CtLiteralImpl]null;
            [CtSwitchImpl]switch ([CtVariableReadImpl]metricSupplied) {
                [CtCaseImpl]case [CtLiteralImpl]"viewsTotal" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsTotal = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsTotal();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"viewsTotalRegular" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsTotalRegular = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsTotalRegular();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"viewsTotalMachine" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsTotalMachine = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsTotalMachine();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"viewsUnique" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsUnique = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsUnique();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"viewsUniqueRegular" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsUniqueRegular = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsUniqueRegular();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"viewsUniqueMachine" :
                    [CtAssignmentImpl][CtVariableWriteImpl]viewsUniqueMachine = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getViewsUniqueMachine();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsTotal" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsTotal = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsTotal();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsTotalRegular" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsTotalRegular = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsTotalRegular();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsTotalMachine" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsTotalMachine = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsTotalMachine();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsUnique" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsUnique = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsUnique();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsUniqueRegular" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsUniqueRegular = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsUniqueRegular();
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtLiteralImpl]"downloadsUniqueMachine" :
                    [CtAssignmentImpl][CtVariableWriteImpl]downloadsUniqueMachine = [CtInvocationImpl][CtVariableReadImpl]datasetMetrics.getDownloadsUniqueMachine();
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtBreakImpl]break;
            }
            [CtInvocationImpl][CtJavaDocImpl]/**
             * TODO: Think more about the JSON output and the API design.
             * getDatasetMetricsByDatasetMonthCountry returns a single row right
             * now, by country. We could return multiple metrics (viewsTotal,
             * viewsUnique, downloadsTotal, and downloadsUnique) by country.
             */
            [CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsTotalRegular", [CtVariableReadImpl]viewsTotalRegular);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsUniqueRegular", [CtVariableReadImpl]viewsUniqueRegular);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsTotalRegular", [CtVariableReadImpl]downloadsTotalRegular);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsUniqueRegular", [CtVariableReadImpl]downloadsUniqueRegular);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsTotalMachine", [CtVariableReadImpl]viewsTotalMachine);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsUniqueMachine", [CtVariableReadImpl]viewsUniqueMachine);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsTotalMachine", [CtVariableReadImpl]downloadsTotalMachine);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsUniqueMachine", [CtVariableReadImpl]downloadsUniqueMachine);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsTotal", [CtVariableReadImpl]viewsTotal);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"viewsUnique", [CtVariableReadImpl]viewsUnique);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsTotal", [CtVariableReadImpl]downloadsTotal);
            [CtInvocationImpl][CtVariableReadImpl]jsonObjectBuilder.add([CtLiteralImpl]"downloadsUnique", [CtVariableReadImpl]downloadsUnique);
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]jsonObjectBuilder);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// bad date - caught in sanitize call
            return [CtInvocationImpl]error([CtTypeAccessImpl]BAD_REQUEST, [CtInvocationImpl][CtVariableReadImpl]e.getMessage());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/storageDriver")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getFileStore([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dvIdtf, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtLiteralImpl]"No such dataset");
        }
        [CtReturnImpl]return [CtInvocationImpl]response([CtLambdaImpl]([CtParameterImpl] req) -> [CtInvocationImpl]ok([CtInvocationImpl][CtVariableReadImpl]dataset.getEffectiveStorageDriverId()));
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.PUT
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/storageDriver")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response setFileStore([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dvIdtf, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String storageDriverLabel, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Superuser-only:
        [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl]findAuthenticatedUserOrDie();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Authentication is required.");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isSuperuser()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Superusers only.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtLiteralImpl]"No such dataset");
        }
        [CtForEachImpl][CtCommentImpl]// We don't want to allow setting this to a store id that does not exist:
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> store : [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]edu.harvard.iq.dataverse.dataaccess.DataAccess.getStorageDriverLabels().entrySet()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]store.getKey().equals([CtVariableReadImpl]storageDriverLabel)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dataset.setStorageDriverId([CtInvocationImpl][CtVariableReadImpl]store.getValue());
                [CtInvocationImpl][CtFieldReadImpl]datasetService.merge([CtVariableReadImpl]dataset);
                [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Storage driver set to: " + [CtInvocationImpl][CtVariableReadImpl]store.getKey()) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtVariableReadImpl]store.getValue());
            }
        }
        [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtBinaryOperatorImpl][CtLiteralImpl]"No Storage Driver found for : " + [CtVariableReadImpl]storageDriverLabel);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.DELETE
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/storageDriver")
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response resetFileStore([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String dvIdtf, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.UriInfo uriInfo, [CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.core.Context
    [CtTypeReferenceImpl]javax.ws.rs.core.HttpHeaders headers) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Superuser-only:
        [CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser user;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl]findAuthenticatedUserOrDie();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.BAD_REQUEST, [CtLiteralImpl]"Authentication is required.");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]user.isSuperuser()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.FORBIDDEN, [CtLiteralImpl]"Superusers only.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]dvIdtf);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse ex) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]error([CtTypeAccessImpl]Response.Status.NOT_FOUND, [CtLiteralImpl]"No such dataset");
        }
        [CtInvocationImpl][CtVariableReadImpl]dataset.setStorageDriverId([CtLiteralImpl]null);
        [CtInvocationImpl][CtFieldReadImpl]datasetService.merge([CtVariableReadImpl]dataset);
        [CtReturnImpl]return [CtInvocationImpl]ok([CtBinaryOperatorImpl][CtLiteralImpl]"Storage reset to default: " + [CtFieldReadImpl]edu.harvard.iq.dataverse.dataaccess.DataAccess.DEFAULT_STORAGE_DRIVER_IDENTIFIER);
    }

    [CtMethodImpl][CtAnnotationImpl]@javax.ws.rs.GET
    [CtAnnotationImpl]@javax.ws.rs.Path([CtLiteralImpl]"{identifier}/timestamps")
    [CtAnnotationImpl]@javax.ws.rs.Produces([CtFieldReadImpl]javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public [CtTypeReferenceImpl]javax.ws.rs.core.Response getTimestamps([CtParameterImpl][CtAnnotationImpl]@javax.ws.rs.PathParam([CtLiteralImpl]"identifier")
    [CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset dataset = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.format.DateTimeFormatter formatter = [CtFieldReadImpl][CtTypeAccessImpl]java.time.format.DateTimeFormatter.[CtFieldReferenceImpl]ISO_LOCAL_DATE_TIME;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dataset = [CtInvocationImpl]findDatasetOrDie([CtVariableReadImpl]id);
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.users.User u = [CtInvocationImpl]findUserOrDie();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.Permission> perms = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.authorization.Permission>();
            [CtInvocationImpl][CtVariableReadImpl]perms.add([CtTypeAccessImpl]Permission.ViewUnpublishedDataset);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean canSeeDraft = [CtInvocationImpl][CtFieldReadImpl]permissionSvc.hasPermissionsFor([CtVariableReadImpl]u, [CtVariableReadImpl]dataset, [CtVariableReadImpl]perms);
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.json.JsonObjectBuilder timestamps = [CtInvocationImpl][CtTypeAccessImpl]javax.json.Json.createObjectBuilder();
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"CSD: " + [CtVariableReadImpl]canSeeDraft);
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"IT: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime());
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"MT: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getModificationTime());
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"PIT: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionIndexTime());
            [CtInvocationImpl][CtFieldReadImpl]edu.harvard.iq.dataverse.api.Datasets.logger.fine([CtBinaryOperatorImpl][CtLiteralImpl]"PMT: " + [CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionModificationTime());
            [CtIfImpl][CtCommentImpl]// Basic info if it's released
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.isReleased() || [CtVariableReadImpl]canSeeDraft) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"createTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getCreateDate().toLocalDateTime()));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPublicationDate() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"publicationTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPublicationDate().toLocalDateTime()));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getLastExportTime() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastMetadataExportTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getLastExportTime().toInstant().atZone([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault())));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getMostRecentMajorVersionReleaseDate() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastMajorVersionReleaseTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getMostRecentMajorVersionReleaseDate().toInstant().atZone([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault())));
                }
                [CtInvocationImpl][CtCommentImpl]// If the modification/permissionmodification time is
                [CtCommentImpl]// set and the index time is null or is before the mod time, the relevant index is stale
                [CtVariableReadImpl]timestamps.add([CtLiteralImpl]"hasStaleIndex", [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataset.getModificationTime() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime().compareTo([CtInvocationImpl][CtVariableReadImpl]dataset.getModificationTime()) <= [CtLiteralImpl]0)) ? [CtLiteralImpl]true : [CtLiteralImpl]false);
                [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"hasStalePermissionIndex", [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionModificationTime() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime().compareTo([CtInvocationImpl][CtVariableReadImpl]dataset.getModificationTime()) <= [CtLiteralImpl]0)) ? [CtLiteralImpl]true : [CtLiteralImpl]false);
            }
            [CtIfImpl][CtCommentImpl]// More detail if you can see a draft
            if ([CtVariableReadImpl]canSeeDraft) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastUpdateTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getModificationTime().toLocalDateTime()));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastIndexTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getIndexTime().toLocalDateTime()));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionModificationTime() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastPermissionUpdateTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionModificationTime().toLocalDateTime()));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionIndexTime() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"lastPermissionIndexTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getPermissionIndexTime().toLocalDateTime()));
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getGlobalIdCreateTime() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]timestamps.add([CtLiteralImpl]"globalIdCreateTime", [CtInvocationImpl][CtVariableReadImpl]formatter.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dataset.getGlobalIdCreateTime().toInstant().atZone([CtInvocationImpl][CtTypeAccessImpl]java.time.ZoneId.systemDefault())));
                }
            }
            [CtReturnImpl]return [CtInvocationImpl]ok([CtVariableReadImpl]timestamps);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.api.AbstractApiBean.WrappedResponse wr) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wr.getResponse();
        }
    }
}
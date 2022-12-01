[CompilationUnitImpl][CtPackageDeclarationImpl]package com.dotmarketing.portlets.contentlet.business.web;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.model.ContentletDependencies;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.model.Relationship;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.model.IndexPolicyProvider;
[CtUnresolvedImport]import com.dotcms.contenttype.transform.contenttype.StructureTransformer;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.model.Structure;
[CtUnresolvedImport]import com.dotmarketing.portlets.containers.model.Container;
[CtUnresolvedImport]import com.liferay.util.StringPool;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.factories.StructureFactory;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.model.ContentletVersionInfo;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.model.Contentlet;
[CtUnresolvedImport]import com.liferay.portal.model.User;
[CtUnresolvedImport]import com.dotmarketing.portlets.htmlpageasset.model.IHTMLPage;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords;
[CtUnresolvedImport]import com.dotcms.contenttype.exception.NotFoundInDbException;
[CtUnresolvedImport]import com.dotmarketing.business.web.WebAPILocator;
[CtUnresolvedImport]import com.dotmarketing.portlets.categories.business.CategoryAPI;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.business.FieldAPI;
[CtUnresolvedImport]import com.liferay.portal.SystemException;
[CtUnresolvedImport]import com.dotcms.repackage.javax.portlet.WindowState;
[CtUnresolvedImport]import com.dotmarketing.beans.Host;
[CtUnresolvedImport]import com.dotmarketing.portlets.calendar.model.Event;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.model.ContentletRelationships;
[CtUnresolvedImport]import com.dotmarketing.portlets.calendar.business.EventAPI;
[CtUnresolvedImport]import com.dotmarketing.portlets.categories.model.Category;
[CtUnresolvedImport]import com.liferay.util.servlet.SessionMessages;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.business.DotContentletValidationException;
[CtUnresolvedImport]import com.dotcms.api.system.event.ContentletSystemEventUtil;
[CtUnresolvedImport]import static com.dotmarketing.portlets.contentlet.util.ActionletUtil.hasPushPublishActionlet;
[CtUnresolvedImport]import com.dotmarketing.util.*;
[CtUnresolvedImport]import com.dotmarketing.business.web.HostWebAPI;
[CtUnresolvedImport]import com.dotmarketing.portlets.folders.business.FolderAPI;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import com.dotcms.contenttype.business.ContentTypeAPI;
[CtUnresolvedImport]import com.dotcms.business.WrapInTransaction;
[CtUnresolvedImport]import com.liferay.portal.language.LanguageUtil;
[CtUnresolvedImport]import com.dotmarketing.beans.Identifier;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.business.ContentletAPI;
[CtUnresolvedImport]import com.dotcms.publisher.environment.bean.Environment;
[CtUnresolvedImport]import com.dotcms.repackage.org.directwebremoting.WebContextFactory;
[CtUnresolvedImport]import com.dotmarketing.beans.MultiTree;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import static com.dotmarketing.portlets.workflows.actionlet.PushPublishActionlet.getEnvironmentsToSendTo;
[CtUnresolvedImport]import org.apache.commons.collections.CollectionUtils;
[CtUnresolvedImport]import com.dotcms.contenttype.model.type.ContentType;
[CtImportImpl]import java.text.MessageFormat;
[CtUnresolvedImport]import static com.dotmarketing.portlets.folders.business.FolderAPI.SYSTEM_FOLDER;
[CtUnresolvedImport]import com.dotmarketing.portlets.contentlet.business.DotContentletStateException;
[CtUnresolvedImport]import com.dotmarketing.business.*;
[CtUnresolvedImport]import com.liferay.portal.PortalException;
[CtUnresolvedImport]import com.dotmarketing.cache.FieldsCache;
[CtUnresolvedImport]import com.dotmarketing.db.HibernateUtil;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtImportImpl]import java.text.DateFormat;
[CtImportImpl]import java.text.ParseException;
[CtUnresolvedImport]import com.dotmarketing.exception.*;
[CtUnresolvedImport]import com.dotmarketing.portlets.structure.model.Field;
[CtUnresolvedImport]import com.dotmarketing.portlets.folders.model.Folder;
[CtClassImpl][CtCommentImpl]/* //http://jira.dotmarketing.net/browse/DOTCMS-2273
    To save content via ajax.
 */
public class ContentletWebAPIImpl implements [CtTypeReferenceImpl]ContentletWebAPI {
    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.categories.business.CategoryAPI catAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.PermissionAPI perAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.ContentletAPI conAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.business.FieldAPI fAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.business.web.HostWebAPI hostAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.UserAPI userAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.folders.business.FolderAPI folderAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.IdentifierAPI identAPI;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.calendar.business.EventAPI eventAPI;

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.text.DateFormat eventRecurrenceStartDateF = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd HH:mm");

    [CtFieldImpl]private static [CtTypeReferenceImpl]java.text.DateFormat eventRecurrenceEndDateF = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd");

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.dotcms.api.system.event.ContentletSystemEventUtil contentletSystemEventUtil;

    [CtConstructorImpl]public ContentletWebAPIImpl() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]catAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getCategoryAPI();
        [CtAssignmentImpl][CtFieldWriteImpl]perAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getPermissionAPI();
        [CtAssignmentImpl][CtFieldWriteImpl]conAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getContentletAPI();
        [CtAssignmentImpl][CtFieldWriteImpl]fAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getFieldAPI();
        [CtAssignmentImpl][CtFieldWriteImpl]hostAPI = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.business.web.WebAPILocator.getHostWebAPI();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.userAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getUserAPI();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.folderAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getFolderAPI();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.identAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getIdentifierAPI();
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.eventAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getEventAPI();
        [CtAssignmentImpl][CtFieldWriteImpl]contentletSystemEventUtil = [CtInvocationImpl][CtTypeAccessImpl]com.dotcms.api.system.event.ContentletSystemEventUtil.getInstance();
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see com.dotmarketing.portlets.contentlet.business.web.ContentletWebAPI#saveContent(java.util.Map, boolean, boolean, com.liferay.portal.model.User)
    This funtion works similar to EditContentletAction cmd = Constants.ADD
     */
    public [CtTypeReferenceImpl]java.lang.String saveContent([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]boolean isAutoSave, [CtParameterImpl][CtTypeReferenceImpl]boolean isCheckin, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user) throws [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException, [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]saveContent([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]isAutoSave, [CtVariableReadImpl]isCheckin, [CtVariableReadImpl]user, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtAnnotationImpl]@com.dotcms.business.WrapInTransaction
    public [CtTypeReferenceImpl]java.lang.String saveContent([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl]final [CtTypeReferenceImpl]boolean isAutoSave, [CtParameterImpl]final [CtTypeReferenceImpl]boolean isCheckin, [CtParameterImpl]final [CtTypeReferenceImpl]com.liferay.portal.model.User user, [CtParameterImpl]final [CtTypeReferenceImpl]boolean generateSystemEvent) throws [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException, [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.directwebremoting.WebContextFactory.get().getHttpServletRequest();
        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtLiteralImpl]"############################# Contentlet");
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtLiteralImpl]"Calling Retrieve method");
            [CtInvocationImpl]retrieveWebAsset([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]user);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ae) [CtBlockImpl]{
            [CtInvocationImpl]handleException([CtVariableReadImpl]ae);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Exception([CtInvocationImpl][CtVariableReadImpl]ae.getMessage());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet = [CtLiteralImpl]null;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNew = [CtInvocationImpl]isNew([CtVariableReadImpl]contentletFormData);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtLiteralImpl]"Calling Save Method");
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]saveWebAsset([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]isAutoSave, [CtVariableReadImpl]isCheckin, [CtVariableReadImpl]user, [CtVariableReadImpl]generateSystemEvent);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ce) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isAutoSave)[CtBlockImpl]
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]req, [CtLiteralImpl]"message.contentlet.save.error");

                [CtThrowImpl]throw [CtVariableReadImpl]ce;
            }
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtLiteralImpl]"HTMLPage inode=" + [CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"htmlpage_inode"));
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtBinaryOperatorImpl][CtLiteralImpl]"Container inode=" + [CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentcontainer_inode"));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"htmlpage_inode")))) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentcontainer_inode"))))) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLambdaImpl]() -> [CtLiteralImpl]"I'm setting my contentlet parents");
                    [CtInvocationImpl]_addToParents([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]user, [CtVariableReadImpl]isAutoSave);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]DotSecurityException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotSecurityException([CtInvocationImpl][CtVariableReadImpl]e.getMessage());
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ae) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtVariableReadImpl]ae;
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]contentlet = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT)));
            [CtIfImpl][CtCommentImpl]// finally we unlock the asset as the lock attribute is
            [CtCommentImpl]// attached to the identifier rather than contentlet as
            [CtCommentImpl]// before DOTCMS-6383
            [CtCommentImpl]// conAPI.unlock(cont, user, false);
            if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]contentlet) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.pushSaveEvent([CtVariableReadImpl]contentlet, [CtVariableReadImpl]isNew);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ae) [CtBlockImpl]{
            [CtInvocationImpl]handleException([CtVariableReadImpl]ae);
            [CtThrowImpl]throw [CtVariableReadImpl]ae;
        }
        [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtLiteralImpl]"cache_control", [CtLiteralImpl]"0");
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]contentlet != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]contentlet.getInode() : [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void pushSaveEvent([CtParameterImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet eventContentlet, [CtParameterImpl]final [CtTypeReferenceImpl]boolean eventCreateNewVersion) throws [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.DotHibernateException [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.db.HibernateUtil.addCommitListener([CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentletSystemEventUtil.pushSaveEvent([CtVariableReadImpl]eventContentlet, [CtVariableReadImpl]eventCreateNewVersion);
        }, [CtLiteralImpl]1000);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isNew([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet currentContentlet = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT)));
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]currentContentlet.getInode());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates the relationship between a given Legacy or Content Page, a
     * container, and its new or existing contentlet.
     *
     * @param contentletFormData
     * 		- The information passed down after form submission.
     * @param user
     * 		- The user performing this action.
     * @param isAutoSave
     * 		-
     * @throws Exception
     * 		It can be thrown if the user does not have the permission to
     * 		perform this action, or if an error occurred during the save
     * 		process.
     */
    private [CtTypeReferenceImpl]void _addToParents([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user, [CtParameterImpl][CtTypeReferenceImpl]boolean isAutoSave) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLiteralImpl]"Inside AddContentletToParentsAction");
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.directwebremoting.WebContextFactory.get().getHttpServletRequest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_FORM_EDIT)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet currentContentlet = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT)));
        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"currentContentlet inode=" + [CtInvocationImpl][CtVariableReadImpl]currentContentlet.getInode());
        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"contentlet inode=" + [CtInvocationImpl][CtVariableReadImpl]contentlet.getInode());
        [CtIfImpl][CtCommentImpl]// it's a new contentlet. we should add to parents
        [CtCommentImpl]// if it's a version the parents get copied on save asset method
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]currentContentlet.getInode().equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"htmlpage_inode")) && [CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentcontainer_inode")))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String htmlpage_inode = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"htmlpage_inode")));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String contentcontainer_inode = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentcontainer_inode")));
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]IdentifierAPI identifierAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getIdentifierAPI();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]VersionableAPI versionableAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getVersionableAPI();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier htmlParentId = [CtInvocationImpl][CtVariableReadImpl]identifierAPI.findFromInode([CtVariableReadImpl]htmlpage_inode);
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Added Contentlet to parent=" + [CtVariableReadImpl]htmlpage_inode);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier containerParentId = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.containers.model.Container containerParent = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]containerParentId = [CtInvocationImpl][CtVariableReadImpl]identifierAPI.findFromInode([CtVariableReadImpl]contentcontainer_inode);
                [CtAssignmentImpl][CtVariableWriteImpl]containerParent = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.containers.model.Container) ([CtVariableReadImpl]versionableAPI.findWorkingVersion([CtVariableReadImpl]containerParentId, [CtVariableReadImpl]user, [CtLiteralImpl]false)));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]DotSecurityException) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]req, [CtLiteralImpl]"message", [CtLiteralImpl]"User needs 'View' Permissions on container");
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotSecurityException([CtLiteralImpl]"User have no View Permissions on container");
                } else [CtBlockImpl]{
                    [CtThrowImpl]throw [CtVariableReadImpl]e;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]containerParent != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Added Contentlet to parent=" + [CtInvocationImpl][CtVariableReadImpl]containerParent.getInode());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]htmlParentId.getId()) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]containerParent.getInode())) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier containerIdentifier = [CtInvocationImpl][CtVariableReadImpl]identifierAPI.find([CtVariableReadImpl]containerParent);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier contenletIdentifier = [CtInvocationImpl][CtVariableReadImpl]identifierAPI.find([CtVariableReadImpl]contentlet);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.MultiTree multiTree = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getMultiTreeAPI().getMultiTree([CtVariableReadImpl]htmlParentId, [CtVariableReadImpl]containerIdentifier, [CtVariableReadImpl]contenletIdentifier, [CtTypeAccessImpl]MultiTree.LEGACY_RELATION_TYPE);[CtCommentImpl]// todo: check if needs to persona

                    [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Getting multitree for=" + [CtVariableReadImpl]htmlpage_inode) + [CtLiteralImpl]" ,") + [CtInvocationImpl][CtVariableReadImpl]containerParent.getInode()) + [CtLiteralImpl]" ,") + [CtInvocationImpl][CtVariableReadImpl]contentlet.getIdentifier());
                    [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Coming from multitree parent1=" + [CtInvocationImpl][CtVariableReadImpl]multiTree.getParent1()) + [CtLiteralImpl]" parent2=") + [CtInvocationImpl][CtVariableReadImpl]multiTree.getParent2());
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int contentletCount = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getMultiTreeAPI().getMultiTrees([CtVariableReadImpl]htmlParentId).size();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]multiTree.getParent1())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]multiTree.getParent2()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]multiTree.getChild()))) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLiteralImpl]"MTree is null!!! Creating new one!");
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.MultiTree mTree = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.beans.MultiTree([CtInvocationImpl][CtVariableReadImpl]htmlParentId.getInode(), [CtInvocationImpl][CtVariableReadImpl]containerIdentifier.getInode(), [CtInvocationImpl][CtVariableReadImpl]contenletIdentifier.getInode(), [CtLiteralImpl]null, [CtVariableReadImpl]contentletCount);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet htmlContentlet = [CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]htmlpage_inode, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]htmlContentlet) && [CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]htmlContentlet.getInode())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]htmlContentlet.getStructure().getStructureType() == [CtFieldReadImpl]com.dotmarketing.portlets.structure.model.Structure.STRUCTURE_TYPE_HTMLPAGE)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pageIdentifier = [CtInvocationImpl][CtVariableReadImpl]htmlContentlet.getIdentifier();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]long contentletLang = [CtInvocationImpl][CtVariableReadImpl]contentlet.getLanguageId();
                            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.ContentletVersionInfo versionInfo = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getVersionableAPI().getContentletVersionInfo([CtVariableReadImpl]pageIdentifier, [CtVariableReadImpl]contentletLang);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]versionInfo != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getMultiTreeAPI().saveMultiTree([CtVariableReadImpl]mTree);
                            } else [CtBlockImpl]{
                                [CtLocalVariableImpl][CtCommentImpl]// The language in the page and the contentlet
                                [CtCommentImpl]// do not match
                                [CtTypeReferenceImpl]java.lang.String language = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getLanguageAPI().getLanguage([CtVariableReadImpl]contentletLang).getLanguage();
                                [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Creating MultiTree failed: Contentlet with identifier " + [CtVariableReadImpl]pageIdentifier) + [CtLiteralImpl]" does not exist in ") + [CtVariableReadImpl]language);
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtTypeAccessImpl]com.liferay.portal.language.LanguageUtil.get([CtVariableReadImpl]user, [CtLiteralImpl]"message.htmlpage.error.addcontent.invalidlanguage"), [CtVariableReadImpl]language);
                                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotLanguageException([CtVariableReadImpl]msg);
                            }
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getMultiTreeAPI().saveMultiTree([CtVariableReadImpl]mTree);
                        }
                    }
                }
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isAutoSave)[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]req, [CtLiteralImpl]"message", [CtLiteralImpl]"message.contentlet.add.parents");

        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveWebAsset([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]boolean isAutoSave, [CtParameterImpl][CtTypeReferenceImpl]boolean isCheckin, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user, [CtParameterImpl][CtTypeReferenceImpl]boolean generateSystemEvent) throws [CtTypeReferenceImpl]java.lang.Exception, [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.directwebremoting.WebContextFactory.get().getHttpServletRequest();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set contentletFormKeys = [CtInvocationImpl][CtVariableReadImpl]contentletFormData.keySet();[CtCommentImpl]// To replace req.getParameterValues()

        [CtLocalVariableImpl][CtCommentImpl]// Getting the contentlets variables to work
        [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet currentContentlet = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT)));
        [CtInvocationImpl][CtCommentImpl]// Form doesn't always contain this value upfront. And since populateContentlet sets 0 we better set it upfront
        [CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]Contentlet.IDENTIFIER_KEY, [CtInvocationImpl][CtVariableReadImpl]currentContentlet.getIdentifier());
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isNew = [CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]currentContentlet.getInode());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]isNew) && [CtInvocationImpl][CtTypeAccessImpl]Host.HOST_VELOCITY_VAR_NAME.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]currentContentlet.getStructure().getVelocityVarName())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]currentContentlet = [CtInvocationImpl][CtFieldReadImpl]conAPI.checkout([CtInvocationImpl][CtVariableReadImpl]currentContentlet.getInode(), [CtVariableReadImpl]user, [CtLiteralImpl]false);
        }
        [CtInvocationImpl][CtJavaDocImpl]/**
         * *
         * Workflow
         */
        [CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_ACTION_KEY, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_ACTION_KEY))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_COMMENTS_KEY, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_COMMENTS_KEY))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_ASSIGN_KEY, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_ASSIGN_KEY))));
        [CtInvocationImpl][CtJavaDocImpl]/**
         * Push Publishing Actionlet
         */
        [CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_PUBLISH_DATE, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_PUBLISH_DATE))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_PUBLISH_TIME, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_PUBLISH_TIME))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_EXPIRE_DATE, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_EXPIRE_DATE))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_EXPIRE_TIME, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_EXPIRE_TIME))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WORKFLOW_NEVER_EXPIRE, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WORKFLOW_NEVER_EXPIRE))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.WHERE_TO_SEND, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.WHERE_TO_SEND))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.FILTER_KEY, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.FILTER_KEY))));
        [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtTypeAccessImpl]Contentlet.I_WANT_TO, [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]Contentlet.I_WANT_TO))));
        [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_FORM_EDIT, [CtVariableReadImpl]currentContentlet);
        [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT, [CtVariableReadImpl]currentContentlet);
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]populateContent([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]user, [CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]isAutoSave);
            [CtIfImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-1450
            [CtCommentImpl]// The form doesn't have the identifier in it. so the populate content was setting it to 0
            [CtCommentImpl]// currentContentlet.setIdentifier(currentContentident);
            if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"new_owner_permissions"))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setOwner([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"new_owner_permissions"))));
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException ve) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException([CtInvocationImpl][CtVariableReadImpl]ve.getMessage());
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String subCommand = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"subcmd"))) ? [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"subcmd"))) : [CtFieldReadImpl]com.liferay.util.StringPool.BLANK;
        [CtIfImpl][CtCommentImpl]// Saving interval review properties
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewContent") != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewContent").toString().equalsIgnoreCase([CtLiteralImpl]"true")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setReviewInterval([CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewIntervalNum"))) + [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewIntervalSelect"))));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setReviewInterval([CtLiteralImpl]null);
        }
        [CtInvocationImpl][CtCommentImpl]// saving the review dates
        [CtVariableReadImpl]currentContentlet.setLastReview([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]currentContentlet.getReviewInterval() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setNextReview([CtInvocationImpl][CtFieldReadImpl]conAPI.getNextReview([CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]user, [CtLiteralImpl]false));
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.dotmarketing.portlets.categories.model.Category> categories = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.dotmarketing.portlets.categories.model.Category>();
        [CtLocalVariableImpl][CtCommentImpl]// Getting categories that come from the entity
        final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> categoriesList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Host host = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.folders.model.Folder folder = [CtLiteralImpl]null;
        [CtForImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Iterator iterator = [CtInvocationImpl][CtVariableReadImpl]contentletFormKeys.iterator(); [CtInvocationImpl][CtVariableReadImpl]iterator.hasNext();) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String elementName = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]iterator.next()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]elementName.startsWith([CtLiteralImpl]"categories") && [CtInvocationImpl][CtVariableReadImpl]elementName.endsWith([CtLiteralImpl]"_")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]categoriesList.add([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]elementName))));
            }
            [CtIfImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-3232
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"hostId".equalsIgnoreCase([CtVariableReadImpl]elementName) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]elementName).toString())) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String hostId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]elementName).toString();
                [CtAssignmentImpl][CtVariableWriteImpl]host = [CtInvocationImpl][CtFieldReadImpl]hostAPI.find([CtVariableReadImpl]hostId, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]host == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]host = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.beans.Host();
                }
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]perAPI.doesUserHavePermission([CtVariableReadImpl]host, [CtTypeAccessImpl]PermissionAPI.PERMISSION_CAN_ADD_CHILDREN, [CtVariableReadImpl]user, [CtLiteralImpl]false)) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]request, [CtLiteralImpl]"message", [CtLiteralImpl]"User needs 'Add Children' Permissions on selected host");
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotSecurityException([CtLiteralImpl]"User has no Add Children Permissions on selected host");
                }
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setHost([CtVariableReadImpl]hostId);
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setFolder([CtTypeAccessImpl]SYSTEM_FOLDER);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"folderInode".equalsIgnoreCase([CtVariableReadImpl]elementName) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]elementName).toString())) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String folderInode = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]elementName).toString();
                [CtAssignmentImpl][CtVariableWriteImpl]folder = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.folderAPI.find([CtVariableReadImpl]folderInode, [CtVariableReadImpl]user, [CtLiteralImpl]true);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]isNew && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]perAPI.doesUserHavePermission([CtVariableReadImpl]folder, [CtTypeAccessImpl]PermissionAPI.PERMISSION_CAN_ADD_CHILDREN, [CtVariableReadImpl]user, [CtLiteralImpl]false))) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]request, [CtLiteralImpl]"message", [CtLiteralImpl]"User needs 'Add Children Permissions' on selected folder");
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotSecurityException([CtLiteralImpl]"User has no Add Children Permissions on selected folder");
                }
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setHost([CtInvocationImpl][CtVariableReadImpl]folder.getHostId());
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setFolder([CtVariableReadImpl]folderInode);
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]categoriesList)) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> iterator = [CtInvocationImpl][CtVariableReadImpl]categoriesList.iterator(); [CtInvocationImpl][CtVariableReadImpl]iterator.hasNext();) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String categoryId = [CtInvocationImpl][CtVariableReadImpl]iterator.next();
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]categoryId)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]categories.add([CtInvocationImpl][CtFieldReadImpl]catAPI.find([CtVariableReadImpl]categoryId, [CtVariableReadImpl]user, [CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]PageMode.get().respectAnonPerms));
                }
            }
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships contentletRelationships = [CtInvocationImpl]retrieveRelationshipsData([CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]user, [CtVariableReadImpl]contentletFormData);
            [CtLocalVariableImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-65
            [CtCommentImpl]// Coming from other contentlet to relate it automatically
            final [CtTypeReferenceImpl]java.lang.String relateWith = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"relwith"))) ? [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"relwith"))) : [CtLiteralImpl]null;
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String relationType = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reltype"))) ? [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reltype"))) : [CtLiteralImpl]null;
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String relationHasParent = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"relisparent")));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]relateWith != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords> recordsList = [CtInvocationImpl][CtVariableReadImpl]contentletRelationships.getRelationshipsRecords();
                    [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords records : [CtVariableReadImpl]recordsList) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]records.getRelationship().getRelationTypeValue().equals([CtVariableReadImpl]relationType)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]FactoryLocator.getRelationshipFactory().sameParentAndChild([CtInvocationImpl][CtVariableReadImpl]records.getRelationship()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]records.isHasParent()) && [CtInvocationImpl][CtVariableReadImpl]relationHasParent.equals([CtLiteralImpl]"no")) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]records.isHasParent() && [CtInvocationImpl][CtVariableReadImpl]relationHasParent.equals([CtLiteralImpl]"yes"))))) [CtBlockImpl]{
                            [CtContinueImpl]continue;
                        }
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]records.getRecords().add([CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]relateWith, [CtVariableReadImpl]user, [CtLiteralImpl]false));
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Logger.error([CtThisAccessImpl]this, [CtLiteralImpl]"Contentlet failed while creating new relationship", [CtVariableReadImpl]e);
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.util.Constants.PUBLISH.equals([CtVariableReadImpl]subCommand)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setBoolProperty([CtLiteralImpl]"live", [CtLiteralImpl]true);
            }
            [CtIfImpl][CtCommentImpl]// Perform some validations before saving it
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]currentContentlet.getStructure().getStructureType() == [CtFieldReadImpl]com.dotmarketing.portlets.structure.model.Structure.STRUCTURE_TYPE_HTMLPAGE) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String status = [CtInvocationImpl]validateNewContentPage([CtVariableReadImpl]currentContentlet);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]status)) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtTypeAccessImpl]com.liferay.portal.language.LanguageUtil.get([CtVariableReadImpl]user, [CtVariableReadImpl]status);
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotRuntimeException([CtVariableReadImpl]msg);
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionId"))))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]currentContentlet = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getWorkflowAPI().fireContentWorkflow([CtVariableReadImpl]currentContentlet, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.ContentletDependencies.Builder().respectAnonymousPermissions([CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]PageMode.get([CtVariableReadImpl]request).respectAnonPerms).modUser([CtVariableReadImpl]user).relationships([CtVariableReadImpl]contentletRelationships).workflowActionId([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionId")))).workflowActionComments([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionComments")))).workflowAssignKey([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionAssign")))).categories([CtVariableReadImpl]categories).indexPolicy([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.portlets.contentlet.model.IndexPolicyProvider.getInstance().forSingleContent()).generateSystemEvent([CtVariableReadImpl]generateSystemEvent).build());
                [CtIfImpl]if ([CtInvocationImpl]ActionletUtil.hasPushPublishActionlet([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getWorkflowAPI().findAction([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionId"))), [CtVariableReadImpl]user))) [CtBlockImpl]{
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String whoToSendTmp = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]currentContentlet.get([CtTypeAccessImpl]Contentlet.WHERE_TO_SEND)));
                    [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotcms.publisher.environment.bean.Environment> envsToSendTo = [CtInvocationImpl]getEnvironmentsToSendTo([CtVariableReadImpl]whoToSendTmp);
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getSession().setAttribute([CtBinaryOperatorImpl][CtFieldReadImpl]WebKeys.SELECTED_ENVIRONMENTS + [CtInvocationImpl][CtVariableReadImpl]user.getUserId(), [CtVariableReadImpl]envsToSendTo);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]Logger.warn([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Calling Save Web Asset: " + [CtInvocationImpl][CtVariableReadImpl]currentContentlet.getIdentifier()) + [CtLiteralImpl]", without an action.");
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Host.HOST_VELOCITY_VAR_NAME.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]currentContentlet.getStructure().getVelocityVarName())) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Logger.info([CtThisAccessImpl]this, [CtLiteralImpl]"Saving the Host");
                    [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setInode([CtLiteralImpl]null);
                    [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setIndexPolicy([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.portlets.contentlet.model.IndexPolicyProvider.getInstance().forSingleContent());
                    [CtAssignmentImpl][CtVariableWriteImpl]currentContentlet = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.conAPI.checkin([CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]contentletRelationships, [CtVariableReadImpl]categories, [CtLiteralImpl]null, [CtVariableReadImpl]user, [CtLiteralImpl]false, [CtVariableReadImpl]generateSystemEvent);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException ve) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.warnAndDebug([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtVariableReadImpl]ve.getMessage(), [CtVariableReadImpl]ve);
            [CtThrowImpl]throw [CtVariableReadImpl]ve;
        }
        [CtIfImpl][CtCommentImpl]/* We can not expect to always have a contentlet after executing a Workflow Action as
        for example if we run the delete actionlet we will have no contentlet to process at this point
         */
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]currentContentlet) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtLiteralImpl]"wfActionComments", [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionComments"))));
            [CtInvocationImpl][CtVariableReadImpl]currentContentlet.setStringProperty([CtLiteralImpl]"wfActionAssign", [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"wfActionAssign"))));
            [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT, [CtVariableReadImpl]currentContentlet);
            [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_FORM_EDIT, [CtVariableReadImpl]currentContentlet);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Config.getBooleanProperty([CtLiteralImpl]"CONTENT_CHANGE_NOTIFICATIONS", [CtLiteralImpl]false) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isNew)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]isAutoSave)) [CtBlockImpl]{
                [CtInvocationImpl]_sendContentletPublishNotification([CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]request);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isAutoSave) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]request, [CtLiteralImpl]"message", [CtLiteralImpl]"message.contentlet.save");
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]subCommand != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]Constants.PUBLISH.equals([CtVariableReadImpl]subCommand)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getVersionableAPI().setLive([CtVariableReadImpl]currentContentlet);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isAutoSave) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]request, [CtLiteralImpl]"message", [CtLiteralImpl]"message.contentlet.published");
                }
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]/* No contentlet was returned (probably a delete was executed).
            Marking it for upper layers.
             */
            [CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_DELETED, [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String validateNewContentPage([CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentPage) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentFolderId = [CtInvocationImpl][CtVariableReadImpl]contentPage.getFolder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pageUrl = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentPage.getMap().get([CtLiteralImpl]"url") == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentPage.getMap().get([CtLiteralImpl]"url").toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String status = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.liferay.portal.model.User systemUser = [CtInvocationImpl][CtFieldReadImpl]userAPI.getSystemUser();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.folders.model.Folder parentFolder = [CtInvocationImpl][CtFieldReadImpl]folderAPI.find([CtVariableReadImpl]parentFolderId, [CtVariableReadImpl]systemUser, [CtLiteralImpl]false);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]parentFolder != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]parentFolder.getInode())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Host host = [CtInvocationImpl][CtFieldReadImpl]hostAPI.find([CtInvocationImpl][CtVariableReadImpl]parentFolder.getHostId(), [CtVariableReadImpl]systemUser, [CtLiteralImpl]true);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String parentFolderPath = [CtInvocationImpl][CtVariableReadImpl]parentFolder.getPath();
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]parentFolderPath)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]parentFolderPath.startsWith([CtLiteralImpl]"/")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]parentFolderPath = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtVariableReadImpl]parentFolderPath;
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]parentFolderPath.endsWith([CtLiteralImpl]"/")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]parentFolderPath = [CtBinaryOperatorImpl][CtVariableReadImpl]parentFolderPath + [CtLiteralImpl]"/";
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fullPageUrl = [CtBinaryOperatorImpl][CtVariableReadImpl]parentFolderPath + [CtVariableReadImpl]pageUrl;
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]pageUrl.endsWith([CtLiteralImpl]".html")) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotmarketing.beans.Identifier> folders = [CtInvocationImpl][CtFieldReadImpl]identAPI.findByURIPattern([CtLiteralImpl]"folder", [CtVariableReadImpl]fullPageUrl, [CtLiteralImpl]true, [CtVariableReadImpl]host);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]folders.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                            [CtAssignmentImpl][CtCommentImpl]// Found a folder with same path
                            [CtVariableWriteImpl]status = [CtLiteralImpl]"message.htmlpage.error.htmlpage.exists.folder";
                        }
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]status)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier i = [CtInvocationImpl][CtFieldReadImpl]identAPI.find([CtVariableReadImpl]host, [CtVariableReadImpl]fullPageUrl);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]i != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]i.getId())) [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet existingContent = [CtInvocationImpl][CtFieldReadImpl]conAPI.findContentletByIdentifier([CtInvocationImpl][CtVariableReadImpl]i.getId(), [CtLiteralImpl]true, [CtInvocationImpl][CtVariableReadImpl]contentPage.getLanguageId(), [CtVariableReadImpl]systemUser, [CtLiteralImpl]false);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]existingContent) [CtBlockImpl]{
                                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingContent.getStructure().getStructureType() == [CtFieldReadImpl]com.dotmarketing.portlets.structure.model.Structure.STRUCTURE_TYPE_FILEASSET) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingContent.getIdentifier().equals([CtInvocationImpl][CtVariableReadImpl]contentPage.getIdentifier()))) [CtBlockImpl]{
                                        [CtAssignmentImpl][CtCommentImpl]// Found a file asset with same path
                                        [CtVariableWriteImpl]status = [CtLiteralImpl]"message.htmlpage.error.htmlpage.exists.file";
                                    } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]existingContent.getIdentifier().equals([CtInvocationImpl][CtVariableReadImpl]contentPage.getIdentifier())) [CtBlockImpl]{
                                        [CtAssignmentImpl][CtCommentImpl]// Found page with same path and language
                                        [CtVariableWriteImpl]status = [CtLiteralImpl]"message.htmlpage.error.htmlpage.exists";
                                    }
                                }
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletStateException e) [CtBlockImpl]{
                                [CtIfImpl][CtCommentImpl]// If it's a brand new page...
                                if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentPage.getIdentifier())) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtCommentImpl]// Found page with same path
                                    [CtVariableWriteImpl]status = [CtLiteralImpl]"message.htmlpage.error.htmlpage.exists";
                                } else [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]Logger.info([CtInvocationImpl]getClass(), [CtLiteralImpl]"Page with same URI and same language does not exist, so we are OK");
                                }
                            }
                        }
                    }
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]DotDataException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Error trying to retreive information from page '" + [CtInvocationImpl][CtVariableReadImpl]contentPage.getIdentifier()) + [CtLiteralImpl]"'");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotRuntimeException([CtLiteralImpl]"Page information is not valid", [CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]DotSecurityException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Current user has no permission to perform the selected action on page '" + [CtInvocationImpl][CtVariableReadImpl]contentPage.getIdentifier()) + [CtLiteralImpl]"'");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]DotRuntimeException([CtLiteralImpl]"Current user has no permission to perform the selected action", [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]status;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleEventRecurrence([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet) throws [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.DotRuntimeException, [CtTypeReferenceImpl]java.text.ParseException, [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.DotDataException, [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.DotSecurityException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contentlet.isCalendarEvent()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceChanged") != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceChanged").toString())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setBoolProperty([CtLiteralImpl]"recurs", [CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setDateProperty([CtLiteralImpl]"recurrenceStart", [CtInvocationImpl][CtFieldReadImpl]com.dotmarketing.portlets.contentlet.business.web.ContentletWebAPIImpl.eventRecurrenceStartDateF.parse([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceStarts")))));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"noEndDate") == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"noEndDate") != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"noEndDate").toString())))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setDateProperty([CtLiteralImpl]"recurrenceEnd", [CtInvocationImpl][CtFieldReadImpl]com.dotmarketing.portlets.contentlet.business.web.ContentletWebAPIImpl.eventRecurrenceEndDateF.parse([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceEnds")))));
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setBoolProperty([CtLiteralImpl]"noRecurrenceEnd", [CtLiteralImpl]false);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"noEndDate") != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"noEndDate").toString())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setDateProperty([CtLiteralImpl]"recurrenceEnd", [CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setBoolProperty([CtLiteralImpl]"noRecurrenceEnd", [CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtLiteralImpl]"recurrenceDaysOfWeek", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDaysOfWeek").toString());
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceWeekOfMonth", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceWeekOfMonth").toString()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceWeekOfMonth", [CtLiteralImpl]1);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceDayOfWeek", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDayOfWeek").toString()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceDayOfWeek", [CtLiteralImpl]1);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceMonthOfYear", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceMonthOfYear").toString()));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceMonthOfYear", [CtLiteralImpl]1);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceOccurs") == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setBoolProperty([CtLiteralImpl]"recurs", [CtLiteralImpl]false);
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceOccurs").toString().equals([CtLiteralImpl]"daily")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setLongProperty([CtLiteralImpl]"recurrenceInterval", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceIntervalDaily").toString()));
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtLiteralImpl]"recurrenceOccurs", [CtInvocationImpl][CtTypeAccessImpl]Event.Occurrency.DAILY.toString());
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceOccurs").toString().equals([CtLiteralImpl]"weekly")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceInterval", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceIntervalWeekly").toString()));
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtLiteralImpl]"recurrenceOccurs", [CtInvocationImpl][CtTypeAccessImpl]Event.Occurrency.WEEKLY.toString());
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceOccurs").toString().equals([CtLiteralImpl]"monthly")) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"isSpecificDate").toString()) && [CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDayOfMonth"))))) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceDayOfMonth", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDayOfMonth").toString()));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceDayOfMonth", [CtLiteralImpl]0);
            }
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceInterval", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceIntervalMonthly").toString()));
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtLiteralImpl]"recurrenceOccurs", [CtInvocationImpl][CtTypeAccessImpl]Event.Occurrency.MONTHLY.toString());
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceOccurs").toString().equals([CtLiteralImpl]"annually")) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDayOfMonth"))))) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceDayOfMonth", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceDayOfMonth").toString()));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setProperty([CtLiteralImpl]"recurrenceInterval", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.valueOf([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"recurrenceIntervalYearly").toString()));
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtLiteralImpl]"recurrenceOccurs", [CtInvocationImpl][CtTypeAccessImpl]Event.Occurrency.ANNUALLY.toString());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setBoolProperty([CtLiteralImpl]"recurs", [CtLiteralImpl]false);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet populateContent([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user, [CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl][CtTypeReferenceImpl]boolean isAutoSave) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl]handleEventRecurrence([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]contentlet);
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"identifier")))[CtBlockImpl]
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"identifier").toString()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"identifier").toString().equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]contentlet.getIdentifier()))) [CtBlockImpl]{
                [CtThrowImpl][CtCommentImpl]// exceptionData.append("<li>The content form submission data id different from the content which is trying to be edited</li>");
                throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException([CtLiteralImpl]"The content form submission data id different from the content which is trying to be edited");
            }

        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// IF EVENT HANDLE RECURRENCE
            [CtTypeReferenceImpl]java.lang.String structureInode = [CtInvocationImpl][CtVariableReadImpl]contentlet.getStructureInode();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]structureInode)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String selectedStructure = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"selectedStructure")));
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]selectedStructure)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]structureInode = [CtVariableReadImpl]selectedStructure;
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setStructureInode([CtVariableReadImpl]structureInode);
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"identifier")))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setIdentifier([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"identifier").toString());

            [CtIfImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-3232
            if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"hostId")))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setHost([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getHostAPI().findSystemHost([CtVariableReadImpl]user, [CtLiteralImpl]false).getIdentifier());

            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"folderInode")) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"folderInode").toString())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setFolder([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getFolderAPI().find([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"folderInode").toString(), [CtVariableReadImpl]user, [CtLiteralImpl]false).getIdentifier());
            }
            [CtInvocationImpl][CtVariableReadImpl]contentlet.setInode([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentletInode").toString());
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"languageId")))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setLanguageId([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"languageId").toString()));

            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewInterval")))[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setReviewInterval([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reviewInterval").toString());

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> disabled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"disabledWysiwyg")))[CtBlockImpl]
                [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.collections.CollectionUtils.addAll([CtVariableReadImpl]disabled, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"disabledWysiwyg").toString().split([CtLiteralImpl]","));

            [CtInvocationImpl][CtVariableReadImpl]contentlet.setDisabledWysiwyg([CtVariableReadImpl]disabled);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Field> fields = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.cache.FieldsCache.getFieldsByStructureInode([CtVariableReadImpl]structureInode);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Field field : [CtVariableReadImpl]fields) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]fAPI.isElementConstant([CtVariableReadImpl]field)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object value = [CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtInvocationImpl][CtVariableReadImpl]field.getFieldContentlet());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String typeField = [CtInvocationImpl][CtVariableReadImpl]field.getFieldType();
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.TAG.toString())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]contentlet.setStringProperty([CtInvocationImpl][CtVariableReadImpl]field.getVelocityVarName(), [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtInvocationImpl][CtVariableReadImpl]field.getVelocityVarName()))));
                }
                [CtIfImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-5334
                if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.CHECKBOX.toString())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldContentlet().startsWith([CtLiteralImpl]"float") || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldContentlet().startsWith([CtLiteralImpl]"integer")) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)))) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]value);
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)).endsWith([CtLiteralImpl]",")) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)).substring([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)).lastIndexOf([CtLiteralImpl]","));
                            }
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtLiteralImpl]"0";
                        }
                    }
                }
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.DATE_TIME.toString())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldContentlet().startsWith([CtLiteralImpl]"date") && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"fieldNeverExpire") != [CtLiteralImpl]null)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fieldNeverExpire = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"fieldNeverExpire").toString();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Structure structure = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]CacheLocator.getContentTypeCache().getStructureByInode([CtInvocationImpl][CtVariableReadImpl]contentlet.getStructureInode());
                        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getVelocityVarName().equals([CtInvocationImpl][CtVariableReadImpl]structure.getExpireDateVar())) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]fieldNeverExpire.equalsIgnoreCase([CtLiteralImpl]"true")) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentlet.getMap().put([CtLiteralImpl]"NeverExpire", [CtLiteralImpl]"NeverExpire");
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentlet.getMap().put([CtLiteralImpl]"NeverExpire", [CtLiteralImpl]"");
                            }
                        }
                    }
                }
                [CtIfImpl][CtCommentImpl]/* Validate if the field is read only, if so then check to see if it's a new contentlet
                and set the structure field default value, otherwise do not set the new value.
                 */
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]typeField.equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.HIDDEN.toString())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]typeField.equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.IMAGE.toString()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]typeField.equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.FILE.toString()))) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]field.isReadOnly() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode())))[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]field.getDefaultValue();

                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.WYSIWYG.toString())) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// WYSIWYG workaround because the WYSIWYG includes a <br> even if the field was left blank by the user
                        [CtCommentImpl]// we have to check the value to leave it blank in that case.
                        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.String) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (value)).trim().toLowerCase().equals([CtLiteralImpl]"<br>")) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtLiteralImpl]"";
                        }
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]value != [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.BINARY.toString())) && [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getFieldAPI().valueSettable([CtVariableReadImpl]field)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldType().equals([CtInvocationImpl][CtTypeAccessImpl]Field.FieldType.HOST_OR_FOLDER.toString()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]field.getFieldContentlet().startsWith([CtLiteralImpl]"system"))) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]conAPI.setContentletProperty([CtVariableReadImpl]contentlet, [CtVariableReadImpl]field, [CtVariableReadImpl]value);
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletStateException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]e;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.error([CtThisAccessImpl]this, [CtLiteralImpl]"Unable to populate content. ", [CtVariableReadImpl]e);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Exception([CtLiteralImpl]"Unable to populate content");
        }
        [CtReturnImpl]return [CtVariableReadImpl]contentlet;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void handleException([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Exception ae) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]ae instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.DotContentletValidationException)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]ae instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]DotLanguageException))) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.warnAndDebug([CtInvocationImpl][CtThisAccessImpl]this.getClass(), [CtInvocationImpl][CtVariableReadImpl]ae.toString(), [CtVariableReadImpl]ae);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]ae.toString(), [CtVariableReadImpl]ae);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Structure transform([CtParameterImpl]final [CtTypeReferenceImpl]com.dotcms.contenttype.model.type.ContentType contentType) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]contentType ? [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotcms.contenttype.transform.contenttype.StructureTransformer([CtVariableReadImpl]contentType).asStructure() : [CtLiteralImpl]null;
    }[CtCommentImpl]// transform.


    [CtMethodImpl]private [CtTypeReferenceImpl]void retrieveWebAsset([CtParameterImpl]final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl]final [CtTypeReferenceImpl]com.liferay.portal.model.User user) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.dotcms.contenttype.business.ContentTypeAPI contentTypeAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getContentTypeAPI([CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.directwebremoting.WebContextFactory.get().getHttpServletRequest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inode = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"contentletInode")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inodeStr = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]inode)) ? [CtVariableReadImpl]inode : [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]inodeStr)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentletFind = [CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]inodeStr, [CtVariableReadImpl]user, [CtLiteralImpl]false);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contentletFind != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contentlet.getMap().putAll([CtInvocationImpl][CtVariableReadImpl]contentletFind.getMap());
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotcms.contenttype.exception.NotFoundInDbException([CtLiteralImpl]"contentlet with that inode does not exist");
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* In case of multi-language first ocurrence new contentlet */
            [CtTypeReferenceImpl]java.lang.String sibblingInode = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"sibbling")));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]sibblingInode) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]sibblingInode.equals([CtLiteralImpl]"0"))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet sibblingContentlet = [CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]sibblingInode, [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getUserAPI().getSystemUser(), [CtLiteralImpl]false);
                [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtFieldReadImpl]com.dotmarketing.portlets.contentlet.business.web.UtilHTML.class, [CtBinaryOperatorImpl][CtLiteralImpl]"getLanguagesIcons :: Sibbling Contentlet = " + [CtInvocationImpl][CtVariableReadImpl]sibblingContentlet.getInode());
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.beans.Identifier identifier = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getIdentifierAPI().find([CtVariableReadImpl]sibblingContentlet);
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setIdentifier([CtInvocationImpl][CtVariableReadImpl]identifier.getInode());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String langId = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"lang")));
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]langId)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]contentlet.setLanguageId([CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtVariableReadImpl]langId));
                }
                [CtInvocationImpl][CtVariableReadImpl]contentlet.setStructureInode([CtInvocationImpl][CtVariableReadImpl]sibblingContentlet.getStructureInode());
            }
        }
        [CtInvocationImpl][CtCommentImpl]// if(perAPI.doesUserHavePermission(contentlet, PermissionAPI.PERMISSION_READ, user, false));
        [CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT, [CtVariableReadImpl]contentlet);
        [CtLocalVariableImpl][CtCommentImpl]// Contententlets Relationships
        [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Structure st = [CtInvocationImpl][CtVariableReadImpl]contentlet.getStructure();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]st == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]st.getInode()))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String selectedStructure = [CtLiteralImpl]"";
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"selectedStructure"))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]selectedStructure = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"selectedStructure")));
                [CtAssignmentImpl][CtVariableWriteImpl]st = [CtInvocationImpl][CtThisAccessImpl]this.transform([CtInvocationImpl][CtVariableReadImpl]contentTypeAPI.find([CtVariableReadImpl]selectedStructure));
            } else [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"sibblingStructure"))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]selectedStructure = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"sibblingStructure")));
                [CtAssignmentImpl][CtVariableWriteImpl]st = [CtInvocationImpl][CtThisAccessImpl]this.transform([CtInvocationImpl][CtVariableReadImpl]contentTypeAPI.find([CtVariableReadImpl]selectedStructure));
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]st = [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.portlets.structure.factories.StructureFactory.getDefaultStructure();
            }
        }
        [CtInvocationImpl]loadContentletRelationshipsInRequest([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]contentlet, [CtVariableReadImpl]st);
        [CtInvocationImpl][CtCommentImpl]// Asset Versions to list in the versions tab
        [CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.VERSIONS_INODE_EDIT, [CtVariableReadImpl]contentlet);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void loadContentletRelationshipsInRequest([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Structure structure) throws [CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.web.DotDataException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.ContentletAPI contentletService = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getContentletAPI();
        [CtInvocationImpl][CtVariableReadImpl]contentlet.setStructureInode([CtInvocationImpl][CtVariableReadImpl]structure.getInode());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships cRelationships = [CtInvocationImpl][CtVariableReadImpl]contentletService.getAllRelationships([CtVariableReadImpl]contentlet);
        [CtInvocationImpl][CtVariableReadImpl]contentletFormData.put([CtTypeAccessImpl]WebKeys.CONTENTLET_RELATIONSHIPS_EDIT, [CtVariableReadImpl]cRelationships);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void _sendContentletPublishNotification([CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req) throws [CtTypeReferenceImpl]java.lang.Exception, [CtTypeReferenceImpl]com.liferay.portal.PortalException, [CtTypeReferenceImpl]com.liferay.portal.SystemException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]com.liferay.portal.util.WebKeys.LAYOUT, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.getSession().getAttribute([CtTypeAccessImpl]com.dotmarketing.util.WebKeys.LAYOUT));
            [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]com.liferay.portal.util.WebKeys.JAVAX_PORTLET_CONFIG, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.getSession().getAttribute([CtTypeAccessImpl]com.dotmarketing.util.WebKeys.JAVAX_PORTLET_CONFIG));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.liferay.portal.model.User currentUser = [CtInvocationImpl][CtTypeAccessImpl]com.liferay.portal.util.PortalUtil.getUser([CtVariableReadImpl]req);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]> params = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtArrayTypeReferenceImpl]java.lang.String[]>();
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"struts_action", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"/ext/contentlet/edit_contentlet" });
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"cmd", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"edit" });
            [CtInvocationImpl][CtVariableReadImpl]params.put([CtLiteralImpl]"inode", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode()) });
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String contentURL = [CtInvocationImpl][CtTypeAccessImpl]PortletURLUtil.getActionURL([CtVariableReadImpl]req, [CtInvocationImpl][CtTypeAccessImpl]WindowState.MAXIMIZED.toString(), [CtVariableReadImpl]params);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> references = [CtInvocationImpl][CtFieldReadImpl]conAPI.getContentletReferences([CtVariableReadImpl]contentlet, [CtVariableReadImpl]currentUser, [CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>> validReferences = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>>();
            [CtForEachImpl][CtCommentImpl]// Avoinding to send the email to the same users
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> reference : [CtVariableReadImpl]references) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.htmlpageasset.model.IHTMLPage page = [CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.htmlpageasset.model.IHTMLPage) ([CtVariableReadImpl]reference.get([CtLiteralImpl]"page")));
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.liferay.portal.model.User pageUser = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getUserAPI().loadUserById([CtInvocationImpl][CtVariableReadImpl]page.getModUser(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getUserAPI().getSystemUser(), [CtLiteralImpl]false);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pageUser.getUserId().equals([CtInvocationImpl][CtVariableReadImpl]currentUser.getUserId())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]reference.put([CtLiteralImpl]"owner", [CtVariableReadImpl]pageUser);
                        [CtInvocationImpl][CtVariableReadImpl]validReferences.add([CtVariableReadImpl]reference);
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLiteralImpl]"the reference has a null page");
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]ex;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the relationships associated to the current contentlet
     *
     * @param contentletFormData
     * 		Contentlet form map.
     * @param user
     * 		User.
     * @return ContentletRelationships.
     */
    private [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships getCurrentContentletRelationships([CtParameterImpl][CtTypeReferenceImpl]java.util.Map contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords> relationshipsRecords = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> keys = [CtInvocationImpl][CtVariableReadImpl]contentletFormData.keySet();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords contentletRelationshipRecords;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean hasParent;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inodesSt;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] inodes;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Relationship relationship;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inode;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet contentlet;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.business.ContentletAPI contentletAPI = [CtInvocationImpl][CtTypeAccessImpl]APILocator.getContentletAPI();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet> records = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key : [CtVariableReadImpl]keys) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtLiteralImpl]"rel_") && [CtInvocationImpl][CtVariableReadImpl]key.endsWith([CtLiteralImpl]"_inodes")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hasParent = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]key.indexOf([CtLiteralImpl]"_P_") != [CtUnaryOperatorImpl](-[CtLiteralImpl]1);
                [CtAssignmentImpl][CtVariableWriteImpl]inodesSt = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]key)));
                [CtAssignmentImpl][CtVariableWriteImpl]inodes = [CtInvocationImpl][CtVariableReadImpl]inodesSt.split([CtLiteralImpl]",");
                [CtAssignmentImpl][CtVariableWriteImpl]relationship = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getRelationshipAPI().byInode([CtArrayReadImpl][CtVariableReadImpl]inodes[[CtLiteralImpl]0]);
                [CtAssignmentImpl][CtVariableWriteImpl]contentletRelationshipRecords = [CtConstructorCallImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships([CtLiteralImpl]null).new [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords([CtVariableReadImpl]relationship, [CtVariableReadImpl]hasParent);
                [CtAssignmentImpl][CtVariableWriteImpl]records = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet>();
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]inodes.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]inode = [CtArrayReadImpl][CtVariableReadImpl]inodes[[CtVariableReadImpl]i];
                        [CtAssignmentImpl][CtVariableWriteImpl]contentlet = [CtInvocationImpl][CtVariableReadImpl]contentletAPI.find([CtVariableReadImpl]inode, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]contentlet != [CtLiteralImpl]null) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtInvocationImpl][CtVariableReadImpl]contentlet.getInode()))[CtBlockImpl]
                            [CtInvocationImpl][CtVariableReadImpl]records.add([CtVariableReadImpl]contentlet);

                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]Logger.warn([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]e.toString());
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]contentletRelationshipRecords.setRecords([CtVariableReadImpl]records);
                [CtInvocationImpl][CtVariableReadImpl]relationshipsRecords.add([CtVariableReadImpl]contentletRelationshipRecords);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships([CtInvocationImpl](([CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet) ([CtVariableReadImpl]contentletFormData.get([CtTypeAccessImpl]WebKeys.CONTENTLET_EDIT))), [CtVariableReadImpl]relationshipsRecords);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships retrieveRelationshipsData([CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet currentContentlet, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> keys = [CtInvocationImpl][CtVariableReadImpl]contentletFormData.keySet();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships relationshipsData = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords> relationshipsRecords = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key : [CtVariableReadImpl]keys) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]key.startsWith([CtLiteralImpl]"rel_") && [CtInvocationImpl][CtVariableReadImpl]key.endsWith([CtLiteralImpl]"_inodes")) [CtBlockImpl]{
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String inodesSt = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtVariableReadImpl]key)));
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]UtilMethods.isSet([CtVariableReadImpl]inodesSt)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] inodes = [CtInvocationImpl][CtVariableReadImpl]inodesSt.split([CtLiteralImpl]",");
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Relationship relationship = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]APILocator.getRelationshipAPI().byInode([CtArrayReadImpl][CtVariableReadImpl]inodes[[CtLiteralImpl]0]);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean isRelatingNewContent = [CtInvocationImpl]isRelatingNewContent([CtVariableReadImpl]contentletFormData, [CtVariableReadImpl]relationship);
                [CtLocalVariableImpl][CtCommentImpl]// This boolean determines if the contentletFormData is a parent or a child
                [CtCommentImpl]// This is the proper behaviour of the boolean:
                [CtCommentImpl]// if you are relating a new child should be false, because the contentletFormData is a child
                [CtCommentImpl]// if you are relating a new parent should be true, because the contentletFormData is a parent
                [CtCommentImpl]// if you are relating an existing child should be true, because the contentletFormData is a parent
                [CtCommentImpl]// if you are relating an existing parent should be false, because the contentletFormData is a child
                final [CtTypeReferenceImpl]boolean isContentletAParent = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]key.contains([CtLiteralImpl]"_P_")) ? [CtUnaryOperatorImpl]![CtVariableReadImpl]isRelatingNewContent : [CtVariableReadImpl]isRelatingNewContent;
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String relationHasParent = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"relisparent") != [CtLiteralImpl]null) ? [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"relisparent"))) : [CtLiteralImpl]"";
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]relationshipsData == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]relationshipsData = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships([CtVariableReadImpl]currentContentlet);
                    [CtAssignmentImpl][CtVariableWriteImpl]relationshipsRecords = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                    [CtInvocationImpl][CtVariableReadImpl]relationshipsData.setRelationshipsRecords([CtVariableReadImpl]relationshipsRecords);
                }
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords records = [CtConstructorCallImpl][CtVariableReadImpl]relationshipsData.new [CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.ContentletRelationships.ContentletRelationshipRecords([CtVariableReadImpl]relationship, [CtVariableReadImpl]isContentletAParent);
                [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet> cons = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String inode : [CtVariableReadImpl]inodes) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]relationship.getInode().equalsIgnoreCase([CtVariableReadImpl]inode) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]FactoryLocator.getRelationshipFactory().sameParentAndChild([CtInvocationImpl][CtVariableReadImpl]records.getRelationship()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]records.isHasParent()) && [CtInvocationImpl][CtVariableReadImpl]relationHasParent.equals([CtLiteralImpl]"no")) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]records.isHasParent() && [CtInvocationImpl][CtVariableReadImpl]relationHasParent.equals([CtLiteralImpl]"yes"))))) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]cons.add([CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]inode, [CtVariableReadImpl]user, [CtLiteralImpl]false));
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Couldn't look up contentlet.  Assuming inode" + [CtVariableReadImpl]inode) + [CtLiteralImpl]"is not content");
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]records.setRecords([CtVariableReadImpl]cons);
                [CtInvocationImpl][CtVariableReadImpl]relationshipsRecords.add([CtVariableReadImpl]records);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]relationshipsData;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * When you click the option Relate New Content, it adds the relwith key with the id of the contentlet that you edited at first and reltype with the relationship name.
     * Also, it is important to verify that the reltype applies to the relationship that we are currently populating (case when there are multiple relationships).
     * View issue: https://github.com/dotCMS/core/issues/17743
     *
     * @param contentletFormData
     * @param relationship
     * @return  */
    private [CtTypeReferenceImpl]boolean isRelatingNewContent([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> contentletFormData, [CtParameterImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.structure.model.Relationship relationship) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contentletFormData.containsKey([CtLiteralImpl]"relwith") && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]relationship.getRelationTypeValue().equals([CtInvocationImpl][CtVariableReadImpl]contentletFormData.get([CtLiteralImpl]"reltype"));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void cancelContentEdit([CtParameterImpl][CtTypeReferenceImpl]java.lang.String workingContentletInode, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String currentContentletInode, [CtParameterImpl][CtTypeReferenceImpl]com.liferay.portal.model.User user) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.db.HibernateUtil.startTransaction();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.dotcms.repackage.org.directwebremoting.WebContextFactory.get().getHttpServletRequest();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]Logger.debug([CtThisAccessImpl]this, [CtLiteralImpl]"Calling Unlock Method");
            [CtIfImpl][CtCommentImpl]// http://jira.dotmarketing.net/browse/DOTCMS-1073
            [CtCommentImpl]// deleting uploaded files from temp binary path
            [CtCommentImpl]/* Logger.debug(this, "Deleting uploaded files");

            java.io.File tempUserFolder = new java.io.File(Config.CONTEXT.
            getRealPath(com.dotmarketing.util.Constants.TEMP_BINARY_PATH)
            + java.io.File.separator + user.getUserId());

            FileUtil.deltree(tempUserFolder);
             */
            if ([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]workingContentletInode)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet workingContentlet = [CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]workingContentletInode, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]perAPI.doesUserHavePermission([CtVariableReadImpl]workingContentlet, [CtTypeAccessImpl]PermissionAPI.PERMISSION_WRITE, [CtVariableReadImpl]user)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]currentContentletInode)) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]conAPI.restoreVersion([CtVariableReadImpl]workingContentlet, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                    }
                    [CtInvocationImpl][CtFieldReadImpl]conAPI.unlock([CtVariableReadImpl]workingContentlet, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                    [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]req, [CtLiteralImpl]"message", [CtLiteralImpl]"message.contentlet.unlocked");
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]currentContentletInode)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.dotmarketing.portlets.contentlet.model.Contentlet currentContentlet = [CtInvocationImpl][CtFieldReadImpl]conAPI.find([CtVariableReadImpl]currentContentletInode, [CtVariableReadImpl]user, [CtLiteralImpl]false);
                [CtIfImpl][CtCommentImpl]// Deleting auto saved version of a New Content upon "Cancel".
                if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]workingContentletInode)) && [CtInvocationImpl][CtTypeAccessImpl]InodeUtils.isSet([CtVariableReadImpl]currentContentletInode)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]conAPI.delete([CtVariableReadImpl]currentContentlet, [CtVariableReadImpl]user, [CtLiteralImpl]false, [CtLiteralImpl]true);
                    [CtCommentImpl]// conAPI.reindex(currentContentlet);
                }
                [CtCommentImpl]// Deleting auto saved version of an Existing Content upon "Cancel".
                [CtCommentImpl]/* Commenting as this makes the content to disappear when editing from HTML PAGE
                if(workingContentletInode > 0 && currentContentletInode > 0 ){
                conAPI.delete(currentContentlet, user, false, false);
                }
                 */
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ae) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.db.HibernateUtil.rollbackTransaction();
            [CtInvocationImpl][CtTypeAccessImpl]com.liferay.util.servlet.SessionMessages.add([CtVariableReadImpl]req, [CtLiteralImpl]"message", [CtLiteralImpl]"message.contentlets.batch.deleted.error");
            [CtThrowImpl]throw [CtVariableReadImpl]ae;
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.dotmarketing.db.HibernateUtil.closeAndCommitTransaction();
    }
}
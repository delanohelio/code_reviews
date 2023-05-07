[CompilationUnitImpl][CtJavaDocImpl]/**
 * ********************************************************************************
 * $URL$
 * $Id$
 * **********************************************************************************
 *
 * Copyright (c) 2005, 2006, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ********************************************************************************
 */
[CtPackageDeclarationImpl]package org.sakaiproject.portal.charon;
[CtUnresolvedImport]import org.sakaiproject.portal.util.ToolUtils;
[CtUnresolvedImport]import org.sakaiproject.tool.api.Session;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.TimeoutDialogHandler;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.AtomHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.WorksiteResetHandler;
[CtUnresolvedImport]import org.sakaiproject.tool.cover.ActiveToolManager;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalService;
[CtUnresolvedImport]import org.sakaiproject.exception.SakaiException;
[CtUnresolvedImport]import org.sakaiproject.portal.util.ErrorReporter;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.LoginHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalSiteHelper;
[CtUnresolvedImport]import javax.servlet.http.HttpServlet;
[CtUnresolvedImport]import org.sakaiproject.event.cover.UsageSessionService;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.PageResetHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.SiteHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.GenerateBugReportHandler;
[CtImportImpl]import java.util.TimeZone;
[CtUnresolvedImport]import org.sakaiproject.portal.render.api.RenderResult;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.SiteResetHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.WorksiteHandler;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.sakaiproject.tool.api.Tool;
[CtUnresolvedImport]import org.sakaiproject.util.RequestFilter;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PageFilter;
[CtUnresolvedImport]import org.sakaiproject.authz.api.SecurityService;
[CtUnresolvedImport]import org.sakaiproject.portal.render.cover.ToolRenderService;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.OpmlHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.RssHandler;
[CtUnresolvedImport]import org.sakaiproject.user.api.PreferencesEdit;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalRenderEngine;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.ToolHandler;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.PresenceHandler;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import org.sakaiproject.thread_local.cover.ThreadLocalManager;
[CtUnresolvedImport]import org.sakaiproject.util.Resource;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.StaticStylesHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.util.CSSUtils;
[CtUnresolvedImport]import org.sakaiproject.entity.api.ResourceProperties;
[CtUnresolvedImport]import org.sakaiproject.user.api.PreferencesService;
[CtImportImpl]import java.util.Calendar;
[CtUnresolvedImport]import org.sakaiproject.portal.api.SiteView;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.FavoritesHandler;
[CtUnresolvedImport]import org.sakaiproject.tool.api.ToolSession;
[CtImportImpl]import java.util.Enumeration;
[CtUnresolvedImport]import org.sakaiproject.site.api.SitePage;
[CtUnresolvedImport]import org.sakaiproject.user.cover.UserDirectoryService;
[CtUnresolvedImport]import org.sakaiproject.portal.util.URLUtils;
[CtUnresolvedImport]import org.sakaiproject.pasystem.api.PASystem;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.ToolResetHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.util.ToolURLManagerImpl;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.RoleSwitchOutHandler;
[CtUnresolvedImport]import org.sakaiproject.tool.cover.SessionManager;
[CtUnresolvedImport]import org.sakaiproject.user.api.UserNotDefinedException;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import javax.servlet.ServletException;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.StaticScriptsHandler;
[CtUnresolvedImport]import org.sakaiproject.entity.api.ResourcePropertiesEdit;
[CtUnresolvedImport]import org.sakaiproject.portal.api.SiteNeighbourhoodService;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.ReLoginHandler;
[CtUnresolvedImport]import org.sakaiproject.event.api.UsageSession;
[CtUnresolvedImport]import org.sakaiproject.exception.IdUnusedException;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.sakaiproject.util.ResourceLoader;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.ErrorReportHandler;
[CtUnresolvedImport]import org.sakaiproject.user.api.Preferences;
[CtUnresolvedImport]import javax.servlet.ServletConfig;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.DirectToolHandler;
[CtUnresolvedImport]import org.sakaiproject.tool.api.ToolURL;
[CtUnresolvedImport]import org.sakaiproject.portal.api.Editor;
[CtUnresolvedImport]import org.sakaiproject.util.Validator;
[CtUnresolvedImport]import org.sakaiproject.user.api.User;
[CtUnresolvedImport]import org.sakaiproject.time.api.TimeService;
[CtUnresolvedImport]import org.sakaiproject.exception.PermissionException;
[CtUnresolvedImport]import org.sakaiproject.util.BasicAuth;
[CtUnresolvedImport]import org.sakaiproject.tool.api.Placement;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalChatPermittedHelper;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.ErrorDoneHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.PageHandler;
[CtUnresolvedImport]import org.sakaiproject.site.api.Site;
[CtUnresolvedImport]import org.sakaiproject.portal.api.Portal;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.XLoginHandler;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import org.sakaiproject.tool.api.ActiveTool;
[CtUnresolvedImport]import org.sakaiproject.component.cover.ServerConfigurationService;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.NavLoginHandler;
[CtImportImpl]import org.apache.commons.lang3.BooleanUtils;
[CtUnresolvedImport]import org.sakaiproject.component.cover.ComponentManager;
[CtUnresolvedImport]import org.sakaiproject.portal.api.StoredState;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.JoinHandler;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import org.sakaiproject.tool.api.ToolException;
[CtImportImpl]import java.util.Iterator;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.LogoutHandler;
[CtUnresolvedImport]import org.sakaiproject.util.EditorConfiguration;
[CtUnresolvedImport]import javax.servlet.http.Cookie;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.RoleSwitchHandler;
[CtUnresolvedImport]import org.sakaiproject.portal.api.PortalRenderContext;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.site.PortalSiteHelperImpl;
[CtUnresolvedImport]import javax.servlet.http.HttpServletResponse;
[CtUnresolvedImport]import org.sakaiproject.portal.util.PortalUtils;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.sakaiproject.site.cover.SiteService;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import org.sakaiproject.util.Web;
[CtUnresolvedImport]import org.sakaiproject.portal.charon.handlers.HelpHandler;
[CtUnresolvedImport]import org.sakaiproject.authz.api.Role;
[CtUnresolvedImport]import org.sakaiproject.site.api.ToolConfiguration;
[CtClassImpl][CtJavaDocImpl]/**
 * <p/> Charon is the Sakai Site based portal.
 * </p>
 *
 * @since Sakai 2.4
 * @version $Rev$
 */
[CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
[CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
public class SkinnableCharonPortal extends [CtTypeReferenceImpl]javax.servlet.http.HttpServlet implements [CtTypeReferenceImpl]org.sakaiproject.portal.api.Portal {
    [CtFieldImpl][CtJavaDocImpl]/**
     */
    private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]2645929710236293089L;

    [CtFieldImpl][CtJavaDocImpl]/**
     * messages.
     */
    private static [CtTypeReferenceImpl]org.sakaiproject.util.ResourceLoader rloader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.util.ResourceLoader([CtLiteralImpl]"sitenav");

    [CtFieldImpl]private static [CtTypeReferenceImpl]org.sakaiproject.util.ResourceLoader cmLoader = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Resource.getResourceLoader([CtLiteralImpl]"org.sakaiproject.portal.api.PortalService", [CtLiteralImpl]"connection-manager");

    [CtFieldImpl][CtJavaDocImpl]/**
     * Parameter value to indicate to look up a tool ID within a site
     */
    protected static final [CtTypeReferenceImpl]java.lang.String PARAM_SAKAI_SITE = [CtLiteralImpl]"sakai.site";

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.util.BasicAuth basicAuth = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean enableDirect = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalService portalService;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.authz.api.SecurityService securityService = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// Get user preferences
    private [CtTypeReferenceImpl]org.sakaiproject.user.api.PreferencesService preferencesService;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Keyword to look for in sakai.properties copyright message to replace
     * for the server's time's year for auto-update of Copyright end date
     */
    private static final [CtTypeReferenceImpl]java.lang.String SERVER_COPYRIGHT_CURRENT_YEAR_KEYWORD = [CtLiteralImpl]"currentYearFromServer";

    [CtFieldImpl][CtJavaDocImpl]/**
     * Chat helper.
     */
    private [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalChatPermittedHelper chatHelper;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PADDING = [CtLiteralImpl]"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INCLUDE_BOTTOM = [CtLiteralImpl]"include-bottom";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INCLUDE_LOGIN = [CtLiteralImpl]"include-login";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String INCLUDE_TITLE = [CtLiteralImpl]"include-title";

    [CtFieldImpl][CtCommentImpl]// SAK-22384
    private static final [CtTypeReferenceImpl]java.lang.String MATHJAX_SRC_PATH_SAKAI_PROP = [CtLiteralImpl]"portal.mathjax.src.path";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MATHJAX_ENABLED_SAKAI_PROP = [CtLiteralImpl]"portal.mathjax.enabled";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]boolean ENABLED_SAKAI_PROP_DEFAULT = [CtLiteralImpl]true;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MATHJAX_SRC_PATH = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.MATHJAX_SRC_PATH_SAKAI_PROP);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]boolean MATHJAX_ENABLED_AT_SYSTEM_LEVEL = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.MATHJAX_ENABLED_SAKAI_PROP, [CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.ENABLED_SAKAI_PROP_DEFAULT) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.MATHJAX_SRC_PATH.trim().isEmpty());

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalSiteHelper siteHelper = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// private HashMap<String, PortalHandler> handlerMap = new HashMap<String,
    [CtCommentImpl]// PortalHandler>();
    private [CtTypeReferenceImpl]java.lang.String gatewaySiteUrl;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.WorksiteHandler worksiteHandler;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.SiteHandler siteHandler;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String portalContext;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String PROP_PARENT_ID = [CtFieldReadImpl]org.sakaiproject.site.cover.SiteService.PROP_PARENT_ID;

    [CtFieldImpl][CtCommentImpl]// 2.3 back port
    [CtCommentImpl]// public String String PROP_PARENT_ID = "sakai:parent-id";
    private [CtTypeReferenceImpl]java.lang.String PROP_SHOW_SUBSITES = [CtFieldReadImpl]org.sakaiproject.site.cover.SiteService.PROP_SHOW_SUBSITES;

    [CtFieldImpl][CtCommentImpl]// 2.3 back port
    [CtCommentImpl]// public String PROP_SHOW_SUBSITES = "sakai:show-subsites";
    private [CtTypeReferenceImpl]boolean forceContainer = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean sakaiTutorialEnabled = [CtLiteralImpl]true;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean sakaiThemeSwitcherEnabled = [CtLiteralImpl]true;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String handlerPrefix;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.sakaiproject.portal.api.PageFilter pageFilter = [CtNewClassImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.api.PageFilter()[CtClassImpl] {
        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List filter([CtParameterImpl][CtTypeReferenceImpl]java.util.List newPages, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]newPages;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map> filterPlacements([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map> l, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]l;
        }
    };

    [CtFieldImpl][CtCommentImpl]// define string that identifies this as the logged in users' my workspace
    private [CtTypeReferenceImpl]java.lang.String myWorkspaceSiteId = [CtLiteralImpl]"~";

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getPortalContext() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]portalContext;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shutdown the servlet.
     */
    public [CtTypeReferenceImpl]void destroy() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"destroy()");
        [CtInvocationImpl][CtFieldReadImpl]portalService.removePortal([CtThisAccessImpl]this);
        [CtInvocationImpl][CtSuperAccessImpl]super.destroy();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void doError([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]int mode) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.thread_local.cover.ThreadLocalManager.get([CtTypeAccessImpl]org.sakaiproject.portal.charon.ATTR_ERROR) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.thread_local.cover.ThreadLocalManager.set([CtTypeAccessImpl]org.sakaiproject.portal.charon.ATTR_ERROR, [CtTypeAccessImpl]org.sakaiproject.portal.charon.ATTR_ERROR);
            [CtSwitchImpl][CtCommentImpl]// send to the error site
            switch ([CtVariableReadImpl]mode) {
                [CtCaseImpl]case [CtFieldReadImpl]ERROR_SITE :
                    [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// This preseves the "bad" origin site ID.
                        [CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl]getParts([CtVariableReadImpl]req);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]parts.length >= [CtLiteralImpl]3) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String siteId = [CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]2];
                            [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.thread_local.cover.ThreadLocalManager.set([CtTypeAccessImpl]PortalService.SAKAI_PORTAL_ORIGINAL_SITEID, [CtVariableReadImpl]siteId);
                        }
                        [CtInvocationImpl][CtFieldReadImpl]siteHandler.doGet([CtVariableReadImpl]parts, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtLiteralImpl]"!error");
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]ERROR_WORKSITE :
                    [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]worksiteHandler.doWorksite([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtLiteralImpl]"!error", [CtLiteralImpl]null, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]req.getContextPath() + [CtInvocationImpl][CtVariableReadImpl]req.getServletPath());
                        [CtBreakImpl]break;
                    }
            }
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// error and we cannot use the error site...
        [CtCommentImpl]// form a context sensitive title
        [CtTypeReferenceImpl]java.lang.String title = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"ui.service", [CtLiteralImpl]"Sakai") + [CtLiteralImpl]" : Portal";
        [CtLocalVariableImpl][CtCommentImpl]// start the response
        [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext = [CtInvocationImpl]startPageContext([CtLiteralImpl]"", [CtVariableReadImpl]title, [CtLiteralImpl]null, [CtVariableReadImpl]req, [CtLiteralImpl]null);
        [CtInvocationImpl]showSession([CtVariableReadImpl]rcontext, [CtLiteralImpl]true);
        [CtInvocationImpl]showSnoop([CtVariableReadImpl]rcontext, [CtLiteralImpl]true, [CtInvocationImpl]getServletConfig(), [CtVariableReadImpl]req);
        [CtInvocationImpl]sendResponse([CtVariableReadImpl]rcontext, [CtVariableReadImpl]res, [CtLiteralImpl]"error", [CtLiteralImpl]null);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void showSnoop([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]boolean b, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.ServletConfig servletConfig, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration e = [CtLiteralImpl]null;
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequest", [CtInvocationImpl][CtVariableReadImpl]req.toString());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]servletConfig != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtAssignmentImpl][CtVariableWriteImpl]e = [CtInvocationImpl][CtVariableReadImpl]servletConfig.getInitParameterNames();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String param = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]e.nextElement()));
                    [CtInvocationImpl][CtVariableReadImpl]m.put([CtVariableReadImpl]param, [CtInvocationImpl][CtVariableReadImpl]servletConfig.getInitParameter([CtVariableReadImpl]param));
                } 
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopServletConfigParams", [CtVariableReadImpl]m);
        }
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequest", [CtVariableReadImpl]req);
        [CtAssignmentImpl][CtVariableWriteImpl]e = [CtInvocationImpl][CtVariableReadImpl]req.getHeaderNames();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]e.nextElement()));
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtVariableReadImpl]name, [CtInvocationImpl][CtVariableReadImpl]req.getHeader([CtVariableReadImpl]name));
            } 
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequestHeaders", [CtVariableReadImpl]m);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]e = [CtInvocationImpl][CtVariableReadImpl]req.getParameterNames();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]e.nextElement()));
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtVariableReadImpl]name, [CtInvocationImpl][CtVariableReadImpl]req.getParameter([CtVariableReadImpl]name));
            } 
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequestParamsSingle", [CtVariableReadImpl]m);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]e = [CtInvocationImpl][CtVariableReadImpl]req.getParameterNames();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]e.nextElement()));
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] vals = [CtInvocationImpl](([CtArrayTypeReferenceImpl]java.lang.String[]) ([CtVariableReadImpl]req.getParameterValues([CtVariableReadImpl]name)));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]vals != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]sb.append([CtArrayReadImpl][CtVariableReadImpl]vals[[CtLiteralImpl]0]);
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]vals.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++)[CtBlockImpl]
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"           ").append([CtArrayReadImpl][CtVariableReadImpl]vals[[CtVariableReadImpl]i]);

                }
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtVariableReadImpl]name, [CtInvocationImpl][CtVariableReadImpl]sb.toString());
            } 
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequestParamsMulti", [CtVariableReadImpl]m);
        }
        [CtAssignmentImpl][CtVariableWriteImpl]e = [CtInvocationImpl][CtVariableReadImpl]req.getAttributeNames();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]e.hasMoreElements()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]e.nextElement()));
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtVariableReadImpl]name, [CtInvocationImpl][CtVariableReadImpl]req.getAttribute([CtVariableReadImpl]name));
            } 
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"snoopRequestAttr", [CtVariableReadImpl]m);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void doThrowableError([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.util.ErrorReporter err = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.util.ErrorReporter();
        [CtInvocationImpl][CtVariableReadImpl]err.report([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]t);
    }

    [CtMethodImpl][CtCommentImpl]/* Include the children of a site */
    [CtCommentImpl]// TODO: Extract to a provider
    [CtCommentImpl]// throws ToolException, IOException
    public [CtTypeReferenceImpl]void includeSubSites([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolContextPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]boolean resetTools) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]siteId == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]rcontext == [CtLiteralImpl]null))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtCommentImpl]// Check the setting as to whether we are to do this
        [CtTypeReferenceImpl]java.lang.String pref = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.includesubsites");
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"never".equals([CtVariableReadImpl]pref))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtInvocationImpl][CtFieldReadImpl]siteHelper.getSiteVisit([CtVariableReadImpl]siteId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.entity.api.ResourceProperties rp = [CtInvocationImpl][CtVariableReadImpl]site.getProperties();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String showSub = [CtInvocationImpl][CtVariableReadImpl]rp.getProperty([CtFieldReadImpl]PROP_SHOW_SUBSITES);
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Checking subsite pref:{} pref={} show={}", [CtInvocationImpl][CtVariableReadImpl]site.getTitle(), [CtVariableReadImpl]pref, [CtVariableReadImpl]showSub);
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"false".equals([CtVariableReadImpl]showSub))[CtBlockImpl]
            [CtReturnImpl]return;

        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"false".equals([CtVariableReadImpl]pref)) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"true".equals([CtVariableReadImpl]showSub))[CtBlockImpl]
                [CtReturnImpl]return;

        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.SiteView siteView = [CtInvocationImpl][CtFieldReadImpl]siteHelper.getSitesView([CtTypeAccessImpl]SiteView.View.SUB_SITES_VIEW, [CtVariableReadImpl]req, [CtVariableReadImpl]session, [CtVariableReadImpl]siteId);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]siteView.isEmpty())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtInvocationImpl][CtVariableReadImpl]siteView.setPrefix([CtVariableReadImpl]prefix);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setToolContextPath([CtVariableReadImpl]toolContextPath);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setResetTools([CtVariableReadImpl]resetTools);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]siteView.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"subSites", [CtInvocationImpl][CtVariableReadImpl]siteView.getRenderContextObject());
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean showSubsitesAsFlyout = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.showSubsitesAsFlyout", [CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"showSubsitesAsFlyout", [CtVariableReadImpl]showSubsitesAsFlyout);
        }
    }

    [CtMethodImpl][CtCommentImpl]/* Produce a portlet like view with the navigation all at the top with
    implicit reset
     */
    public [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext includePortal([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolContextPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String prefix, [CtParameterImpl][CtTypeReferenceImpl]boolean doPages, [CtParameterImpl][CtTypeReferenceImpl]boolean resetTools, [CtParameterImpl][CtTypeReferenceImpl]boolean includeSummary, [CtParameterImpl][CtTypeReferenceImpl]boolean expandSite) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// find the site, for visiting
        [CtTypeReferenceImpl]org.sakaiproject.site.api.Site site = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtInvocationImpl][CtFieldReadImpl]siteHelper.getSiteVisit([CtVariableReadImpl]siteId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.IdUnusedException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtLiteralImpl]"Unable to find site: " + [CtVariableReadImpl]siteId;
            [CtAssignmentImpl][CtVariableWriteImpl]siteId = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtVariableWriteImpl]toolId = [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.PermissionException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtLiteralImpl]"No permission for anonymous user to view site: " + [CtVariableReadImpl]siteId;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtLiteralImpl]"No permission to view site: " + [CtVariableReadImpl]siteId;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]siteId = [CtLiteralImpl]null;
            [CtAssignmentImpl][CtVariableWriteImpl]toolId = [CtLiteralImpl]null;[CtCommentImpl]// Tool needs the site and needs it to be visitable

        }
        [CtLocalVariableImpl][CtCommentImpl]// Get the Tool Placement
        [CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration placement = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]site != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]toolId != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]placement = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.findTool([CtVariableReadImpl]toolId);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]placement == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtLiteralImpl]"Unable to find tool placement " + [CtVariableReadImpl]toolId;
                [CtAssignmentImpl][CtVariableWriteImpl]toolId = [CtLiteralImpl]null;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean thisTool = [CtInvocationImpl][CtFieldReadImpl]siteHelper.allowTool([CtVariableReadImpl]site, [CtVariableReadImpl]placement);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]thisTool) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]errorMessage = [CtBinaryOperatorImpl][CtLiteralImpl]"No permission to view tool placement " + [CtVariableReadImpl]toolId;
                [CtAssignmentImpl][CtVariableWriteImpl]toolId = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtVariableWriteImpl]placement = [CtLiteralImpl]null;
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// form a context sensitive title
        [CtTypeReferenceImpl]java.lang.String title = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"ui.service", [CtLiteralImpl]"Sakai");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// SAK-29138
            [CtVariableWriteImpl]title = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]title + [CtLiteralImpl]":") + [CtInvocationImpl][CtFieldReadImpl]siteHelper.getUserSpecificSiteTitle([CtVariableReadImpl]site, [CtLiteralImpl]false);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]placement != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]title = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]title + [CtLiteralImpl]" : ") + [CtInvocationImpl][CtVariableReadImpl]placement.getTitle();

        }
        [CtLocalVariableImpl][CtCommentImpl]// start the response
        [CtTypeReferenceImpl]java.lang.String siteType = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String siteSkin = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtInvocationImpl]calcSiteType([CtVariableReadImpl]siteId);
            [CtAssignmentImpl][CtVariableWriteImpl]siteSkin = [CtInvocationImpl][CtVariableReadImpl]site.getSkin();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext = [CtInvocationImpl]startPageContext([CtVariableReadImpl]siteType, [CtVariableReadImpl]title, [CtVariableReadImpl]siteSkin, [CtVariableReadImpl]req, [CtVariableReadImpl]site);
        [CtLocalVariableImpl][CtCommentImpl]// Make the top Url where the "top" url is
        [CtTypeReferenceImpl]java.lang.String portalTopUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.RequestFilter.serverUrl([CtVariableReadImpl]req) + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath")) + [CtLiteralImpl]"/";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]prefix != [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]portalTopUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]portalTopUrl + [CtVariableReadImpl]prefix) + [CtLiteralImpl]"/";

        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalTopUrl", [CtVariableReadImpl]portalTopUrl);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loggedIn", [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]session.getUserId()));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"siteId", [CtVariableReadImpl]siteId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]placement != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map m = [CtInvocationImpl]includeTool([CtVariableReadImpl]res, [CtVariableReadImpl]req, [CtVariableReadImpl]placement);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]m != [CtLiteralImpl]null)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"currentPlacement", [CtVariableReadImpl]m);

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.SiteView siteView = [CtInvocationImpl][CtFieldReadImpl]siteHelper.getSitesView([CtTypeAccessImpl]SiteView.View.CURRENT_SITE_VIEW, [CtVariableReadImpl]req, [CtVariableReadImpl]session, [CtVariableReadImpl]siteId);
            [CtInvocationImpl][CtVariableReadImpl]siteView.setPrefix([CtVariableReadImpl]prefix);
            [CtInvocationImpl][CtVariableReadImpl]siteView.setResetTools([CtVariableReadImpl]resetTools);
            [CtInvocationImpl][CtVariableReadImpl]siteView.setToolContextPath([CtVariableReadImpl]toolContextPath);
            [CtInvocationImpl][CtVariableReadImpl]siteView.setIncludeSummary([CtVariableReadImpl]includeSummary);
            [CtInvocationImpl][CtVariableReadImpl]siteView.setDoPages([CtVariableReadImpl]doPages);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]siteView.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"currentSite", [CtInvocationImpl][CtVariableReadImpl]siteView.getRenderContextObject());
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// List l = siteHelper.convertSitesToMaps(req, mySites, prefix, siteId, myWorkspaceSiteId,
        [CtCommentImpl]// includeSummary, expandSite, resetTools, doPages, toolContextPath,
        [CtCommentImpl]// loggedIn);
        [CtTypeReferenceImpl]org.sakaiproject.portal.api.SiteView siteView = [CtInvocationImpl][CtFieldReadImpl]siteHelper.getSitesView([CtTypeAccessImpl]SiteView.View.ALL_SITES_VIEW, [CtVariableReadImpl]req, [CtVariableReadImpl]session, [CtVariableReadImpl]siteId);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setPrefix([CtVariableReadImpl]prefix);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setResetTools([CtVariableReadImpl]resetTools);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setToolContextPath([CtVariableReadImpl]toolContextPath);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setIncludeSummary([CtVariableReadImpl]includeSummary);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setDoPages([CtVariableReadImpl]doPages);
        [CtInvocationImpl][CtVariableReadImpl]siteView.setExpandSite([CtVariableReadImpl]expandSite);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"allSites", [CtInvocationImpl][CtVariableReadImpl]siteView.getRenderContextObject());
        [CtInvocationImpl]includeLogin([CtVariableReadImpl]rcontext, [CtVariableReadImpl]req, [CtVariableReadImpl]session);
        [CtInvocationImpl]includeBottom([CtVariableReadImpl]rcontext, [CtVariableReadImpl]site);
        [CtReturnImpl]return [CtVariableReadImpl]rcontext;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isPortletPlacement([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Placement placement) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.ToolUtils.isPortletPlacement([CtVariableReadImpl]placement);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Map includeTool([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration placement) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean toolInline = [CtInvocationImpl][CtLiteralImpl]"true".equals([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.thread_local.cover.ThreadLocalManager.get([CtLiteralImpl]"sakai:inline-tool"));
        [CtReturnImpl]return [CtInvocationImpl]includeTool([CtVariableReadImpl]res, [CtVariableReadImpl]req, [CtVariableReadImpl]placement, [CtVariableReadImpl]toolInline);
    }

    [CtMethodImpl][CtCommentImpl]// This will be called twice in the buffered scenario since we need to set
    [CtCommentImpl]// the session for neo tools with the sessio reset, helpurl and reseturl
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]java.util.Map includeTool([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration placement, [CtParameterImpl][CtTypeReferenceImpl]boolean toolInline) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.render.api.RenderResult renderResult = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]toolInline) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// if not already inlined, allow a final chance for a tool to be inlined, based on its tool configuration
            [CtCommentImpl]// set renderInline = true to enable this, in the tool config
            [CtVariableWriteImpl]renderResult = [CtInvocationImpl][CtThisAccessImpl]this.getInlineRenderingForTool([CtVariableReadImpl]res, [CtVariableReadImpl]req, [CtVariableReadImpl]placement);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]renderResult != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Using buffered content rendering");
                [CtAssignmentImpl][CtVariableWriteImpl]toolInline = [CtLiteralImpl]true;
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// find the tool registered for this
        [CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.ActiveToolManager.getActiveTool([CtInvocationImpl][CtVariableReadImpl]placement.getToolId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tool == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// doError(req, res, session);
            return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// Get the Site - we could change the API call in the future to
        [CtCommentImpl]// pass site in, but that would break portals that extend Charon
        [CtCommentImpl]// so for now we simply look this up here.
        [CtTypeReferenceImpl]java.lang.String siteId = [CtInvocationImpl][CtVariableReadImpl]placement.getSiteId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getSiteVisit([CtVariableReadImpl]siteId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.IdUnusedException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtLiteralImpl]null;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.PermissionException e) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtCommentImpl]// emit title information
        [CtTypeReferenceImpl]java.lang.String titleString = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeHtml([CtInvocationImpl][CtVariableReadImpl]placement.getTitle());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String toolId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeHtml([CtInvocationImpl][CtVariableReadImpl]placement.getToolId());
        [CtLocalVariableImpl][CtCommentImpl]// for the reset button
        [CtTypeReferenceImpl]java.lang.String toolUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getToolUrl() + [CtLiteralImpl]"/") + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeUrl([CtInvocationImpl][CtVariableReadImpl]placement.getId())) + [CtLiteralImpl]"/";
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"includeTool toolInline={} toolUrl={}", [CtVariableReadImpl]toolInline, [CtVariableReadImpl]toolUrl);
        [CtIfImpl][CtCommentImpl]// Reset is different (and awesome) when inlining
        if ([CtVariableReadImpl]toolInline) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.ToolUtils.getPageUrlForTool([CtVariableReadImpl]req, [CtVariableReadImpl]site, [CtVariableReadImpl]placement);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newUrl != [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]toolUrl = [CtVariableReadImpl]newUrl;

        }
        [CtIfImpl][CtCommentImpl]// Reset the tool state if requested
        if ([CtInvocationImpl][CtFieldReadImpl]portalService.isResetRequested([CtVariableReadImpl]req)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session s = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolSession ts = [CtInvocationImpl][CtVariableReadImpl]s.getToolSession([CtInvocationImpl][CtVariableReadImpl]placement.getId());
            [CtInvocationImpl][CtVariableReadImpl]ts.clearAttributes();
            [CtInvocationImpl][CtFieldReadImpl]portalService.setResetState([CtLiteralImpl]null);
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"includeTool state reset");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean showResetButton = [CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"false".equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.TOOLCONFIG_SHOW_RESET_BUTTON));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resetActionUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.charon.PortalStringUtil.replaceFirst([CtVariableReadImpl]toolUrl, [CtLiteralImpl]"/tool/", [CtLiteralImpl]"/tool-reset/");
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"includeTool resetActionUrl={}", [CtVariableReadImpl]resetActionUrl);
        [CtLocalVariableImpl][CtCommentImpl]// SAK-20462 - Pass through the sakai_action parameter
        [CtTypeReferenceImpl]java.lang.String sakaiAction = [CtInvocationImpl][CtVariableReadImpl]req.getParameter([CtLiteralImpl]"sakai_action");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]sakaiAction != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]sakaiAction.matches([CtLiteralImpl]".*[\"\'<>].*"))[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]sakaiAction = [CtLiteralImpl]null;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]sakaiAction != [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]resetActionUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.addParameter([CtVariableReadImpl]resetActionUrl, [CtLiteralImpl]"sakai_action", [CtVariableReadImpl]sakaiAction);

        [CtIfImpl][CtCommentImpl]// Reset is different for Portlets
        if ([CtInvocationImpl]isPortletPlacement([CtVariableReadImpl]placement)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]resetActionUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.RequestFilter.serverUrl([CtVariableReadImpl]req) + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath")) + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req)) + [CtLiteralImpl]"?sakai.state.reset=true";
        }
        [CtLocalVariableImpl][CtCommentImpl]// for the help button
        [CtCommentImpl]// get the help document ID from the tool config (tool registration
        [CtCommentImpl]// usually).
        [CtCommentImpl]// The help document ID defaults to the tool ID
        [CtTypeReferenceImpl]boolean helpEnabledGlobally = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"display.help.icon", [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean helpEnabledInTool = [CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"false".equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.TOOLCONFIG_SHOW_HELP_BUTTON));
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean showHelpButton = [CtBinaryOperatorImpl][CtVariableReadImpl]helpEnabledGlobally && [CtVariableReadImpl]helpEnabledInTool;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String helpActionUrl = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtVariableReadImpl]showHelpButton) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String helpDocUrl = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.TOOLCONFIG_HELP_DOCUMENT_URL);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String helpDocId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.TOOLCONFIG_HELP_DOCUMENT_ID);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]helpDocUrl != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]helpDocUrl.length() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]helpActionUrl = [CtVariableReadImpl]helpDocUrl;
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]helpDocId == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]helpDocId.length() == [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]helpDocId = [CtInvocationImpl][CtVariableReadImpl]tool.getId();
                }
                [CtAssignmentImpl][CtVariableWriteImpl]helpActionUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getHelpUrl([CtVariableReadImpl]helpDocId);
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> toolMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolInline", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]toolInline));
        [CtIfImpl][CtCommentImpl]// For JSR-168 portlets - this gets the content
        [CtCommentImpl]// For legacy tools, this returns the "<iframe" bit
        [CtCommentImpl]// For buffered legacy tools - the buffering is done outside of this
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]renderResult == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// standard iframe
            [CtFieldReadImpl]log.debug([CtLiteralImpl]"Using standard iframe rendering");
            [CtAssignmentImpl][CtVariableWriteImpl]renderResult = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.render.cover.ToolRenderService.render([CtThisAccessImpl]this, [CtVariableReadImpl]placement, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtInvocationImpl]getServletContext());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]renderResult.getJSR168HelpUrl() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolJSR168Help", [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.RequestFilter.serverUrl([CtVariableReadImpl]req) + [CtInvocationImpl][CtVariableReadImpl]renderResult.getJSR168HelpUrl());
        }
        [CtIfImpl][CtCommentImpl]// Must have site.upd to see the Edit button
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]renderResult.getJSR168EditUrl() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]site != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]securityService.unlock([CtTypeAccessImpl]SiteService.SECURE_UPDATE_SITE, [CtInvocationImpl][CtVariableReadImpl]site.getReference())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editUrl = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.RequestFilter.serverUrl([CtVariableReadImpl]req) + [CtInvocationImpl][CtVariableReadImpl]renderResult.getJSR168EditUrl();
                [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolJSR168Edit", [CtVariableReadImpl]editUrl);
                [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolJSR168EditEncode", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.encodeUrl([CtVariableReadImpl]editUrl));
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolRenderResult", [CtVariableReadImpl]renderResult);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"hasRenderResult", [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolUrl", [CtVariableReadImpl]toolUrl);
        [CtLocalVariableImpl][CtCommentImpl]// Allow a tool to suppress the rendering of its title nav. Defaults to false if not specified, and title nav is rendered.
        [CtCommentImpl]// Set suppressTitle = true to suppress
        [CtTypeReferenceImpl]boolean suppressTitle = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.BooleanUtils.toBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtLiteralImpl]"suppressTitle"));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"suppressTitle", [CtVariableReadImpl]suppressTitle);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session s = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolSession ts = [CtInvocationImpl][CtVariableReadImpl]s.getToolSession([CtInvocationImpl][CtVariableReadImpl]placement.getId());
        [CtIfImpl]if ([CtInvocationImpl]isPortletPlacement([CtVariableReadImpl]placement)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// If the tool has requested it, pre-fetch render output.
            [CtTypeReferenceImpl]java.lang.String doPreFetch = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.JSR_168_PRE_RENDER);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"false".equals([CtVariableReadImpl]doPreFetch)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]renderResult.getContent();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.util.ErrorReporter err = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.util.ErrorReporter();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String str = [CtInvocationImpl][CtVariableReadImpl]err.reportFragment([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]t);
                    [CtInvocationImpl][CtVariableReadImpl]renderResult.setContent([CtVariableReadImpl]str);
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolPlacementIDJS", [CtLiteralImpl]"_self");
            [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"isPortletPlacement", [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolPlacementIDJS", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeJavascript([CtBinaryOperatorImpl][CtLiteralImpl]"Main" + [CtInvocationImpl][CtVariableReadImpl]placement.getId()));
        }
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolResetActionUrl", [CtVariableReadImpl]resetActionUrl);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolResetActionUrlEncode", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.encodeUrl([CtVariableReadImpl]resetActionUrl));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolTitle", [CtVariableReadImpl]titleString);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolTitleEncode", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.encodeUrl([CtVariableReadImpl]titleString));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolShowResetButton", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]showResetButton));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolShowHelpButton", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]showHelpButton));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolHelpActionUrl", [CtVariableReadImpl]helpActionUrl);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolId", [CtVariableReadImpl]toolId);
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"toolInline", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]toolInline));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String directToolUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getPortalUrl() + [CtLiteralImpl]"/") + [CtFieldReadImpl]org.sakaiproject.portal.charon.handlers.DirectToolHandler.URL_FRAGMENT) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeUrl([CtInvocationImpl][CtVariableReadImpl]placement.getId())) + [CtLiteralImpl]"/";
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"directToolUrl", [CtVariableReadImpl]directToolUrl);
        [CtLocalVariableImpl][CtCommentImpl]// props to enable/disable the display on a per tool/placement basis
        [CtCommentImpl]// will be displayed if not explicitly disabled in the tool/placement properties
        [CtTypeReferenceImpl]boolean showDirectToolUrl = [CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"false".equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtTypeAccessImpl]Portal.TOOL_DIRECTURL_ENABLED_PROP));
        [CtInvocationImpl][CtVariableReadImpl]toolMap.put([CtLiteralImpl]"showDirectToolUrl", [CtVariableReadImpl]showDirectToolUrl);
        [CtReturnImpl]return [CtVariableReadImpl]toolMap;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Respond to navigation / access requests.
     *
     * @param req
     * 		The servlet request.
     * @param res
     * 		The servlet response.
     * @throws javax.servlet.ServletException.
     * @throws java.io.IOException.
     */
    protected [CtTypeReferenceImpl]void doGet([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res) throws [CtTypeReferenceImpl]javax.servlet.ServletException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int stat = [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT;
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]basicAuth.doLogin([CtVariableReadImpl]req);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.render.cover.ToolRenderService.preprocess([CtThisAccessImpl]this, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtInvocationImpl]getServletContext())) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]// Check to see if the pre-process step has redirected us - if so,
            [CtCommentImpl]// our work is done here - we will likely come back again to finish
            [CtCommentImpl]// our
            [CtCommentImpl]// work.
            if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// get the Sakai session
            [CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
            [CtLocalVariableImpl][CtCommentImpl]// recognize what to do from the path
            [CtTypeReferenceImpl]java.lang.String option = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl]getParts([CtVariableReadImpl]req);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler> handlerMap = [CtInvocationImpl][CtFieldReadImpl]portalService.getHandlerMap([CtThisAccessImpl]this);
            [CtIfImpl][CtCommentImpl]// begin SAK-19089
            [CtCommentImpl]// if not logged in and accessing "/", redirect to gatewaySiteUrl
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]gatewaySiteUrl != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]option == [CtLiteralImpl]null) || [CtInvocationImpl][CtLiteralImpl]"/".equals([CtVariableReadImpl]option))) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]session.getUserId() == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// redirect to gatewaySiteURL
                [CtVariableReadImpl]res.sendRedirect([CtFieldReadImpl]gatewaySiteUrl);
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// end SAK-19089
            [CtCommentImpl]// Look up the handler and dispatch
            [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler ph = [CtInvocationImpl][CtVariableReadImpl]handlerMap.get([CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1]);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ph != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]stat = [CtInvocationImpl][CtVariableReadImpl]ph.doGet([CtVariableReadImpl]parts, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat != [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.RESET_DONE) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]portalService.setResetState([CtLiteralImpl]null);
                    }
                    [CtReturnImpl]return;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler> i = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handlerMap.values().iterator(); [CtInvocationImpl][CtVariableReadImpl]i.hasNext();) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]ph = [CtInvocationImpl][CtVariableReadImpl]i.next();
                    [CtAssignmentImpl][CtVariableWriteImpl]stat = [CtInvocationImpl][CtVariableReadImpl]ph.doGet([CtVariableReadImpl]parts, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat != [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.RESET_DONE) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]portalService.setResetState([CtLiteralImpl]null);
                        }
                        [CtReturnImpl]return;
                    }
                    [CtIfImpl][CtCommentImpl]// this should be
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat != [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                        [CtBreakImpl]break;
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                [CtInvocationImpl]doError([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtTypeAccessImpl]Portal.ERROR_SITE);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl]doThrowableError([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]t);
        }
        [CtIfImpl][CtCommentImpl]// Make sure to clear any reset State at the end of the request unless
        [CtCommentImpl]// we *just* set it
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat != [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.RESET_DONE) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]portalService.setResetState([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtArrayTypeReferenceImpl]java.lang.String[] getParts([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String option = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req);
        [CtLocalVariableImpl][CtCommentImpl]// FindBugs thinks this is not used but is passed to the portal handler
        [CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtNewArrayImpl]new java.lang.String[]{  };
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]option == [CtLiteralImpl]null) || [CtInvocationImpl][CtLiteralImpl]"/".equals([CtVariableReadImpl]option)) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Use the default handler prefix
            [CtVariableWriteImpl]parts = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtLiteralImpl]"", [CtFieldReadImpl]handlerPrefix };
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// get the parts (the first will be "")
            [CtVariableWriteImpl]parts = [CtInvocationImpl][CtVariableReadImpl]option.split([CtLiteralImpl]"/");
        }
        [CtReturnImpl]return [CtVariableReadImpl]parts;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void doLogin([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String returnPath, [CtParameterImpl][CtTypeReferenceImpl]boolean skipContainer) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]basicAuth.doAuth([CtVariableReadImpl]req, [CtVariableReadImpl]res)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"BASIC Auth Request Sent to the Browser");
                [CtReturnImpl]return;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException ioex) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException([CtVariableReadImpl]ioex);
        }
        [CtIfImpl][CtCommentImpl]// setup for the helper if needed (Note: in session, not tool session,
        [CtCommentImpl]// special for Login helper)
        [CtCommentImpl]// Note: always set this if we are passed in a return path... a blank
        [CtCommentImpl]// return path is valid... to clean up from
        [CtCommentImpl]// possible abandened previous login attempt -ggolden
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]returnPath != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// where to go after
            [CtTypeReferenceImpl]java.lang.String returnUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.returnUrl([CtVariableReadImpl]req, [CtVariableReadImpl]returnPath);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]req.getQueryString() != [CtLiteralImpl]null)[CtBlockImpl]
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]returnUrl += [CtBinaryOperatorImpl][CtLiteralImpl]"?" + [CtInvocationImpl][CtVariableReadImpl]req.getQueryString();

            [CtInvocationImpl][CtVariableReadImpl]session.setAttribute([CtTypeAccessImpl]Tool.HELPER_DONE_URL, [CtVariableReadImpl]returnUrl);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.ActiveToolManager.getActiveTool([CtLiteralImpl]"sakai.login");
        [CtLocalVariableImpl][CtCommentImpl]// to skip container auth for this one, forcing things to be handled
        [CtCommentImpl]// internaly, set the "extreme" login path
        [CtTypeReferenceImpl]java.lang.String loginPath = [CtConditionalImpl]([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]forceContainer) && [CtVariableReadImpl]skipContainer) ? [CtLiteralImpl]"/xlogin" : [CtLiteralImpl]"/relogin";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String context = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]req.getContextPath() + [CtInvocationImpl][CtVariableReadImpl]req.getServletPath()) + [CtVariableReadImpl]loginPath;
        [CtInvocationImpl][CtVariableReadImpl]tool.help([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]context, [CtVariableReadImpl]loginPath);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Process a logout
     *
     * @param req
     * 		Request object
     * @param res
     * 		Response object
     * @param session
     * 		Current session
     * @param returnPath
     * 		if not null, the path to use for the end-user browser redirect
     * 		after the logout is complete. Leave null to use the configured
     * 		logged out URL.
     * @throws IOException
     */
    public [CtTypeReferenceImpl]void doLogout([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String returnPath) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// SAK-16370 to allow multiple logout urls
        [CtTypeReferenceImpl]java.lang.String loggedOutUrl = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userType = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.user.cover.UserDirectoryService.getCurrentUser().getType();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userType == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]loggedOutUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getLoggedOutUrl();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]loggedOutUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtBinaryOperatorImpl][CtLiteralImpl]"loggedOutUrl." + [CtVariableReadImpl]userType, [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getLoggedOutUrl());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]returnPath != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]loggedOutUrl = [CtBinaryOperatorImpl][CtVariableReadImpl]loggedOutUrl + [CtVariableReadImpl]returnPath;
        }
        [CtInvocationImpl][CtVariableReadImpl]session.setAttribute([CtTypeAccessImpl]Tool.HELPER_DONE_URL, [CtVariableReadImpl]loggedOutUrl);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.ActiveToolManager.getActiveTool([CtLiteralImpl]"sakai.login");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String context = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]req.getContextPath() + [CtInvocationImpl][CtVariableReadImpl]req.getServletPath()) + [CtLiteralImpl]"/logout";
        [CtInvocationImpl][CtVariableReadImpl]tool.help([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]context, [CtLiteralImpl]"/logout");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext startPageContext([CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest request, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderEngine rengine = [CtInvocationImpl][CtFieldReadImpl]portalService.getRenderEngine([CtFieldReadImpl]portalContext, [CtVariableReadImpl]request);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext = [CtInvocationImpl][CtVariableReadImpl]rengine.newRenderContext([CtVariableReadImpl]request);
        [CtAssignmentImpl][CtVariableWriteImpl]skin = [CtInvocationImpl]getSkin([CtVariableReadImpl]skin);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String skinRepo = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"skin.repo");
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageSkinRepo", [CtVariableReadImpl]skinRepo);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageSkin", [CtVariableReadImpl]skin);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageTitle", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeHtml([CtVariableReadImpl]title));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageScriptPath", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getScriptPath());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageWebjarsPath", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getWebjarsPath());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalCDNPath", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNPath());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalCDNQuery", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNQuery());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"includeLatestJQuery", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.includeLatestJQuery([CtLiteralImpl]"Portal"));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageTop", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"rloader", [CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"cmLoader", [CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.cmLoader);
        [CtLocalVariableImpl][CtCommentImpl]// rcontext.put("browser", new BrowserDetector(request));
        [CtCommentImpl]// Allow for inclusion of extra header code via property
        [CtTypeReferenceImpl]java.lang.String includeExtraHead = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.include.extrahead", [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"includeExtraHead", [CtVariableReadImpl]includeExtraHead);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String universalAnalyticsId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.google.universal_analytics_id", [CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]universalAnalyticsId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleUniversalAnalyticsId", [CtVariableReadImpl]universalAnalyticsId);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleAnonymizeIp", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.google.anonymize.ip", [CtLiteralImpl]false));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String analyticsId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.google.analytics_id", [CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]analyticsId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleAnalyticsId", [CtVariableReadImpl]analyticsId);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleAnalyticsDomain", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.google.analytics_domain"));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleAnalyticsDetail", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.google.analytics_detail", [CtLiteralImpl]false));
        }
        [CtLocalVariableImpl][CtCommentImpl]// SAK-29668
        [CtTypeReferenceImpl]java.lang.String googleTagManagerContainerId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.google.tag.manager.container_id", [CtLiteralImpl]null);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]googleTagManagerContainerId != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"googleTagManagerContainerId", [CtVariableReadImpl]googleTagManagerContainerId);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.User currentUser = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.user.cover.UserDirectoryService.getCurrentUser();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.authz.api.Role role = [CtConditionalImpl]([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]site != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]currentUser != [CtLiteralImpl]null)) ? [CtInvocationImpl][CtVariableReadImpl]site.getUserRole([CtInvocationImpl][CtVariableReadImpl]currentUser.getId()) : [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.Preferences prefs = [CtInvocationImpl][CtFieldReadImpl]preferencesService.getPreferences([CtInvocationImpl][CtVariableReadImpl]currentUser.getId());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editorType = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]prefs.getProperties([CtTypeAccessImpl]PreferencesService.EDITOR_PREFS_KEY).getProperty([CtTypeAccessImpl]PreferencesService.EDITOR_PREFS_TYPE);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loggedIn", [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtInvocationImpl][CtVariableReadImpl]currentUser.getId()));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userId", [CtInvocationImpl][CtVariableReadImpl]currentUser.getId());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userEid", [CtInvocationImpl][CtVariableReadImpl]currentUser.getEid());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userType", [CtInvocationImpl][CtVariableReadImpl]currentUser.getType());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userSiteRole", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]role != [CtLiteralImpl]null ? [CtInvocationImpl][CtVariableReadImpl]role.getId() : [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"editorType", [CtVariableReadImpl]editorType);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loggedOutUrl", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getLoggedOutUrl());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalPath", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getPortalUrl());
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"timeoutDialogEnabled", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"timeoutDialogEnabled", [CtLiteralImpl]true)));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"timeoutDialogWarningSeconds", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getInt([CtLiteralImpl]"timeoutDialogWarningSeconds", [CtLiteralImpl]600)));
        [CtLocalVariableImpl][CtCommentImpl]// rcontext.put("sitHelp", Web.escapeHtml(rb.getString("sit_help")));
        [CtCommentImpl]// rcontext.put("sitReset", Web.escapeHtml(rb.getString("sit_reset")));
        [CtCommentImpl]// SAK-29457 Add warning about cookie use
        [CtTypeReferenceImpl]java.lang.String cookieNoticeText = [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getFormattedMessage([CtLiteralImpl]"cookie_notice_text", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.cookie.policy.warning.url", [CtLiteralImpl]"/library/content/cookie_policy.html"));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"cookieNoticeEnabled", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.cookie.policy.warning.enabled", [CtLiteralImpl]false));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"cookieNoticeText", [CtVariableReadImpl]cookieNoticeText);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]siteType != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]siteType.length() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"class=\"" + [CtVariableReadImpl]siteType) + [CtLiteralImpl]"\"";
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtLiteralImpl]"";
        }
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pageSiteType", [CtVariableReadImpl]siteType);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"toolParamResetState", [CtInvocationImpl][CtFieldReadImpl]portalService.getResetStateParam());
        [CtLocalVariableImpl][CtCommentImpl]// Get the tool header properties
        [CtTypeReferenceImpl]java.util.Properties props = [CtInvocationImpl]toolHeaderProperties([CtVariableReadImpl]request, [CtVariableReadImpl]skin, [CtVariableReadImpl]site, [CtLiteralImpl]null);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object okey : [CtInvocationImpl][CtVariableReadImpl]props.keySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (okey));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyund = [CtInvocationImpl][CtVariableReadImpl]key.replace([CtLiteralImpl]'.', [CtLiteralImpl]'_');
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtVariableReadImpl]keyund, [CtInvocationImpl][CtVariableReadImpl]props.getProperty([CtVariableReadImpl]key));
        }
        [CtLocalVariableImpl][CtCommentImpl]// Copy the minimization preferences to the context
        [CtTypeReferenceImpl]java.lang.String enableGAM = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.use.global.alert.message", [CtLiteralImpl]"false");
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portal_use_global_alert_message", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]enableGAM));
        [CtInvocationImpl][CtCommentImpl]// how many tools to show in portal pull downs
        [CtVariableReadImpl]rcontext.put([CtLiteralImpl]"maxToolsInt", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.valueOf([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getInt([CtLiteralImpl]"portal.tool.menu.max", [CtLiteralImpl]10)));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"toolDirectUrlEnabled", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.tool.direct.url.enabled", [CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"toolShortUrlEnabled", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"shortenedurl.portal.tool.enabled", [CtLiteralImpl]true));
        [CtInvocationImpl][CtCommentImpl]// SAK-32224. Ability to disable the animated tool menu by property
        [CtVariableReadImpl]rcontext.put([CtLiteralImpl]"scrollingToolbarEnabled", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.scrolling.toolbar.enabled", [CtLiteralImpl]true));
        [CtReturnImpl]return [CtVariableReadImpl]rcontext;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Respond to data posting requests.
     *
     * @param req
     * 		The servlet request.
     * @param res
     * 		The servlet response.
     * @throws ServletException
     * @throws IOException
     */
    protected [CtTypeReferenceImpl]void doPost([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res) throws [CtTypeReferenceImpl]javax.servlet.ServletException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int stat = [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT;
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]basicAuth.doLogin([CtVariableReadImpl]req);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.render.cover.ToolRenderService.preprocess([CtThisAccessImpl]this, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtInvocationImpl]getServletContext())) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"POST FAILED, REDIRECT ?");
                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]// Check to see if the pre-process step has redirected us - if so,
            [CtCommentImpl]// our work is done here - we will likely come back again to finish
            [CtCommentImpl]// our
            [CtCommentImpl]// work. T
            if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// get the Sakai session
            [CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
            [CtLocalVariableImpl][CtCommentImpl]// recognize what to do from the path
            [CtTypeReferenceImpl]java.lang.String option = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req);
            [CtIfImpl][CtCommentImpl]// if missing, we have a stray post
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]option == [CtLiteralImpl]null) || [CtInvocationImpl][CtLiteralImpl]"/".equals([CtVariableReadImpl]option)) [CtBlockImpl]{
                [CtInvocationImpl]doError([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtTypeAccessImpl]org.sakaiproject.portal.charon.ERROR_SITE);
                [CtReturnImpl]return;
            }
            [CtLocalVariableImpl][CtCommentImpl]// get the parts (the first will be "")
            [CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl][CtVariableReadImpl]option.split([CtLiteralImpl]"/");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler> handlerMap = [CtInvocationImpl][CtFieldReadImpl]portalService.getHandlerMap([CtThisAccessImpl]this);
            [CtLocalVariableImpl][CtCommentImpl]// Look up handler and dispatch
            [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler ph = [CtInvocationImpl][CtVariableReadImpl]handlerMap.get([CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1]);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ph != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]stat = [CtInvocationImpl][CtVariableReadImpl]ph.doPost([CtVariableReadImpl]parts, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                    [CtReturnImpl]return;
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler> urlHandlers;
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler> i = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]handlerMap.values().iterator(); [CtInvocationImpl][CtVariableReadImpl]i.hasNext();) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]ph = [CtInvocationImpl][CtVariableReadImpl]i.next();
                    [CtAssignmentImpl][CtVariableWriteImpl]stat = [CtInvocationImpl][CtVariableReadImpl]ph.doPost([CtVariableReadImpl]parts, [CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.isCommitted()) [CtBlockImpl]{
                        [CtReturnImpl]return;
                    }
                    [CtIfImpl][CtCommentImpl]// this should be
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat != [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                        [CtBreakImpl]break;
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]stat == [CtFieldReadImpl]org.sakaiproject.portal.api.PortalHandler.NEXT) [CtBlockImpl]{
                [CtInvocationImpl]doError([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtTypeAccessImpl]Portal.ERROR_SITE);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl]doThrowableError([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]t);
        }
    }

    [CtMethodImpl][CtCommentImpl]/* Checks to see which form of tool or page placement we have. The normal
    placement is a GUID. However when the parameter sakai.site is added to
    the request, the placement can be of the form sakai.resources. This
    routine determines which form of the placement id, and if this is the
    second type, performs the lookup and returns the GUID of the placement.
    If we cannot resolve the placement, we simply return the passed in
    placement ID. If we cannot visit the site, we send the user to login
    processing and return null to the caller.

    If the reference is to the magical, indexical MyWorkspace site ('~')
    then replace ~ by their Home.  Give them a chance to login
    if necessary.
     */
    public [CtTypeReferenceImpl]java.lang.String getPlacement([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String placementId, [CtParameterImpl][CtTypeReferenceImpl]boolean doPage) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String siteId = [CtInvocationImpl][CtVariableReadImpl]req.getParameter([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.PARAM_SAKAI_SITE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]siteId == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]placementId;
        [CtCommentImpl]// Standard placement

        [CtIfImpl][CtCommentImpl]// Try to resolve the indexical MyWorkspace reference
        if ([CtInvocationImpl][CtFieldReadImpl]myWorkspaceSiteId.equals([CtVariableReadImpl]siteId)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// If not logged in then allow login.  You can't go to your workspace if
            [CtCommentImpl]// you aren't known to the system.
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]doLogin([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req), [CtLiteralImpl]false);
            }
            [CtIfImpl][CtCommentImpl]// If the login was successful lookup the myworkworkspace site.
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]siteId = [CtInvocationImpl]getUserEidBasedSiteId([CtInvocationImpl][CtVariableReadImpl]session.getUserEid());
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// find the site, for visiting
        [CtCommentImpl]// Sites like the !gateway site allow visits by anonymous
        [CtTypeReferenceImpl]org.sakaiproject.site.api.Site site = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]site = [CtInvocationImpl][CtInvocationImpl]getSiteHelper().getSiteVisit([CtVariableReadImpl]siteId);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.IdUnusedException e) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]placementId;[CtCommentImpl]// cannot resolve placement

        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.PermissionException e) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// If we are not logged in, try again after we log in, otherwise
            [CtCommentImpl]// punt
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]doLogin([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.URLUtils.getSafePathInfo([CtVariableReadImpl]req), [CtLiteralImpl]false);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtReturnImpl]return [CtVariableReadImpl]placementId;[CtCommentImpl]// cannot resolve placement

        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]placementId;

        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration toolConfig = [CtInvocationImpl][CtVariableReadImpl]site.getToolForCommonId([CtVariableReadImpl]placementId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]toolConfig == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]placementId;

        [CtIfImpl]if ([CtVariableReadImpl]doPage) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]toolConfig.getPageId();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]toolConfig.getId();
        }
    }

    [CtMethodImpl][CtCommentImpl]// Note - When modifying this code, make sure to review
    [CtCommentImpl]// org.sakaiproject.editor.EditorServlet.java
    [CtCommentImpl]// as it includes these values when it is running in its own frame
    public [CtTypeReferenceImpl]java.util.Properties toolHeaderProperties([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Placement placement) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties retval = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isInlineReq = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.ToolUtils.isInlineRequest([CtVariableReadImpl]req);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String headCss = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.getCssHead([CtVariableReadImpl]skin, [CtVariableReadImpl]isInlineReq);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.Editor editor = [CtInvocationImpl][CtFieldReadImpl]portalService.getActiveEditor([CtVariableReadImpl]placement);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String preloadScript = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editor.getPreloadScript() == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<script type=\"text/javascript\">" + [CtInvocationImpl][CtVariableReadImpl]editor.getPreloadScript()) + [CtLiteralImpl]"</script>\n";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String editorScript = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editor.getEditorUrl() == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<script type=\"text/javascript\" src=\"" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNPath()) + [CtInvocationImpl][CtVariableReadImpl]editor.getEditorUrl()) + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNQuery()) + [CtLiteralImpl]"\"></script>\n";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String launchScript = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]editor.getLaunchUrl() == [CtLiteralImpl]null) ? [CtLiteralImpl]"" : [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<script type=\"text/javascript\" src=\"" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNPath()) + [CtInvocationImpl][CtVariableReadImpl]editor.getLaunchUrl()) + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNQuery()) + [CtLiteralImpl]"\"></script>\n";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder headJs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl][CtCommentImpl]// SAK-22384
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]site != [CtLiteralImpl]null) && [CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.MATHJAX_ENABLED_AT_SYSTEM_LEVEL) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String strMathJaxEnabledForSite = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]site.getProperties().getProperty([CtTypeAccessImpl]Site.PROP_SITE_MATHJAX_ALLOWED);
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotBlank([CtVariableReadImpl]strMathJaxEnabledForSite)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]strMathJaxEnabledForSite)) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// this call to MathJax.Hub.Config seems to be needed for MathJax to work in IE
                        [CtVariableReadImpl]headJs.append([CtLiteralImpl]"<script type=\"text/x-mathjax-config\">\nMathJax.Hub.Config({\nmessageStyle: \"none\",\ntex2jax: { inlineMath: [[\'\\\\(\',\'\\\\)\']] }\n});\n</script>\n");
                        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"<script src=\"").append([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.MATHJAX_SRC_PATH).append([CtLiteralImpl]"\" type=\"text/javascript\"></script>\n");
                    }
                }
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String contentItemUrl = [CtInvocationImpl][CtFieldReadImpl]portalService.getContentItemUrl([CtVariableReadImpl]site);
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"<script type=\"text/javascript\" src=\"");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNPath());
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"/library/js/headscripts.js");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNQuery());
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"\"></script>\n");
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl]getParts([CtVariableReadImpl]req);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]parts.length > [CtLiteralImpl]2) && [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]parts[[CtLiteralImpl]1].equals([CtLiteralImpl]"tool")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<script src=\"" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getWebjarsPath()) + [CtLiteralImpl]"momentjs/") + [CtFieldReadImpl]org.sakaiproject.portal.util.PortalUtils.MOMENTJS_VERSION) + [CtLiteralImpl]"/min/moment-with-locales.min.js") + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.PortalUtils.getCDNQuery()) + [CtLiteralImpl]"\"></script>\n");
        }
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<script type=\"text/javascript\">var sakai = sakai || {}; sakai.editor = sakai.editor || {}; " + [CtLiteralImpl]"sakai.editor.editors = sakai.editor.editors || {}; ") + [CtLiteralImpl]"sakai.editor.editors.ckeditor = sakai.editor.editors.ckeditor || {}; ") + [CtLiteralImpl]"sakai.locale = sakai.locale || {};\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.locale.userCountry = '" + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getLocale().getCountry()) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.locale.userLanguage = '" + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getLocale().getLanguage()) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.locale.userLocale = '" + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getLocale().toString()) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.collectionId = '" + [CtInvocationImpl][CtFieldReadImpl]portalService.getBrowserCollectionId([CtVariableReadImpl]placement)) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.enableResourceSearch = " + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.EditorConfiguration.enableResourceSearch()) + [CtLiteralImpl]";\n");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contentItemUrl != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.contentItemUrl = '" + [CtVariableReadImpl]contentItemUrl) + [CtLiteralImpl]"\';\n");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"sakai.editor.contentItemUrl = false;\n");
        }
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.siteToolSkin = '" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.getCssToolSkin([CtVariableReadImpl]skin)) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.sitePrintSkin = '" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.getCssPrintSkin([CtVariableReadImpl]skin)) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"sakai.editor.editors.ckeditor.browser = '" + [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.EditorConfiguration.getCKEditorFileBrowser()) + [CtLiteralImpl]"\';\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"</script>\n");
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtVariableReadImpl]preloadScript);
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtVariableReadImpl]editorScript);
        [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtVariableReadImpl]launchScript);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session s = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userWarning = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]s.getAttribute([CtLiteralImpl]"userWarning")));
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtVariableReadImpl]userWarning)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"<script type=\"text/javascript\">");
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"if ( window.self !== window.top ) {");
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]" setTimeout(function(){ window.top.portal_check_pnotify() }, 3000);");
            [CtInvocationImpl][CtVariableReadImpl]headJs.append([CtLiteralImpl]"}</script>");
        }
        [CtLocalVariableImpl][CtCommentImpl]// TODO: Should we include jquery here?  See includeStandardHead.vm
        [CtTypeReferenceImpl]java.lang.String head = [CtBinaryOperatorImpl][CtVariableReadImpl]headCss + [CtInvocationImpl][CtVariableReadImpl]headJs.toString();
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head", [CtVariableReadImpl]head);
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head.css", [CtVariableReadImpl]headCss);
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head.lang", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getLocale().getLanguage());
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head.css.base", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.getCssToolBaseLink([CtVariableReadImpl]skin, [CtVariableReadImpl]isInlineReq));
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head.css.skin", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.getCssToolSkinLink([CtVariableReadImpl]skin, [CtVariableReadImpl]isInlineReq));
        [CtInvocationImpl][CtVariableReadImpl]retval.setProperty([CtLiteralImpl]"sakai.html.head.js", [CtInvocationImpl][CtVariableReadImpl]headJs.toString());
        [CtReturnImpl]return [CtVariableReadImpl]retval;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setupForward([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Placement p, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]site = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getSite([CtInvocationImpl][CtVariableReadImpl]p.getContext());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.IdUnusedException ex) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtInvocationImpl][CtVariableReadImpl]ex.getMessage());
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Get the tool header properties
        [CtTypeReferenceImpl]java.util.Properties props = [CtInvocationImpl]toolHeaderProperties([CtVariableReadImpl]req, [CtVariableReadImpl]skin, [CtVariableReadImpl]site, [CtVariableReadImpl]p);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object okey : [CtInvocationImpl][CtVariableReadImpl]props.keySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (okey));
            [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtVariableReadImpl]key, [CtInvocationImpl][CtVariableReadImpl]props.getProperty([CtVariableReadImpl]key));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder bodyonload = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bodyclass = [CtLiteralImpl]"Mrphs-container";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String element = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.escapeJavascript([CtBinaryOperatorImpl][CtLiteralImpl]"Main" + [CtInvocationImpl][CtVariableReadImpl]p.getId());
            [CtInvocationImpl][CtVariableReadImpl]bodyonload.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"setMainFrameHeight('" + [CtVariableReadImpl]element) + [CtLiteralImpl]"');");
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]bodyclass += [CtBinaryOperatorImpl][CtLiteralImpl]" Mrphs-" + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getToolId().replace([CtLiteralImpl]".", [CtLiteralImpl]"-");
        }
        [CtInvocationImpl][CtVariableReadImpl]bodyonload.append([CtLiteralImpl]"setFocus(focus_path);");
        [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtLiteralImpl]"sakai.html.body.onload", [CtInvocationImpl][CtVariableReadImpl]bodyonload.toString());
        [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtLiteralImpl]"sakai.html.body.class", [CtInvocationImpl][CtVariableReadImpl]bodyclass.toString());
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]portalService.getRenderEngine([CtFieldReadImpl]portalContext, [CtVariableReadImpl]req).setupForward([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]p, [CtVariableReadImpl]skin);
    }

    [CtMethodImpl][CtCommentImpl]// SAK-28086 - Wrapped Requests have issues with NATIVE_URL
    [CtTypeReferenceImpl]java.lang.String fixPath1([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String c, [CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder ctx) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]s != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]s.startsWith([CtVariableReadImpl]c)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtInvocationImpl][CtVariableReadImpl]s.indexOf([CtLiteralImpl]"/", [CtLiteralImpl]6);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ctx.append([CtInvocationImpl][CtVariableReadImpl]s.substring([CtLiteralImpl]0, [CtVariableReadImpl]i));
                [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl][CtVariableReadImpl]s.substring([CtVariableReadImpl]i);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]ctx.append([CtVariableReadImpl]s);
                [CtAssignmentImpl][CtVariableWriteImpl]s = [CtLiteralImpl]null;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]s;
    }

    [CtMethodImpl][CtTypeReferenceImpl]java.lang.String fixPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s, [CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder ctx) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl]fixPath1([CtVariableReadImpl]s, [CtLiteralImpl]"/site/", [CtVariableReadImpl]ctx);
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl]fixPath1([CtVariableReadImpl]s, [CtLiteralImpl]"/tool/", [CtVariableReadImpl]ctx);
        [CtAssignmentImpl][CtVariableWriteImpl]s = [CtInvocationImpl]fixPath1([CtVariableReadImpl]s, [CtLiteralImpl]"/page/", [CtVariableReadImpl]ctx);
        [CtReturnImpl]return [CtVariableReadImpl]s;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Forward to the tool - but first setup JavaScript/CSS etc that the tool
     * will render
     */
    public [CtTypeReferenceImpl]void forwardTool([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Placement p, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolContextPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolPathInfo) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// SAK-29656 - Make sure the request URL and toolContextPath treat tilde encoding the same way
        [CtCommentImpl]// 
        [CtCommentImpl]// Since we cannot easily change what the request object already knows as its URL,
        [CtCommentImpl]// we patch the toolContextPath to match the tilde encoding in the request URL.
        [CtCommentImpl]// 
        [CtCommentImpl]// This is what we would see in Chrome and Firefox.  Firefox fails with Wicket
        [CtCommentImpl]// Chrome: forwardtool call http://localhost:8080/portal/site/~csev/tool/aaf64e38-00df-419a-b2ac-63cf2d7f99cf
        [CtCommentImpl]// toolPathInfo null ctx /portal/site/~csev/tool/aaf64e38-00df-419a-b2ac-63cf2d7f99cf
        [CtCommentImpl]// Firefox: http://localhost:8080/portal/site/%7ecsev/tool/aaf64e38-00df-419a-b2ac-63cf2d7f99cf/
        [CtCommentImpl]// toolPathInfo null ctx /portal/site/~csev/tool/aaf64e38-00df-419a-b2ac-63cf2d7f99cf
        [CtTypeReferenceImpl]java.lang.String reqUrl = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]req.getRequestURL().toString();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]reqUrl.indexOf([CtVariableReadImpl]toolContextPath) < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Mismatch between request url {} and toolContextPath {}", [CtVariableReadImpl]reqUrl, [CtVariableReadImpl]toolContextPath);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]toolContextPath.indexOf([CtLiteralImpl]"/~") > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]reqUrl.indexOf([CtLiteralImpl]"/~") < [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]reqUrl.indexOf([CtLiteralImpl]"/%7e") > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]toolContextPath = [CtInvocationImpl][CtVariableReadImpl]toolContextPath.replace([CtLiteralImpl]"/~", [CtLiteralImpl]"/%7e");
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]toolContextPath = [CtInvocationImpl][CtVariableReadImpl]toolContextPath.replace([CtLiteralImpl]"/~", [CtLiteralImpl]"/%7E");
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]toolContextPath.indexOf([CtLiteralImpl]" ") > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]reqUrl.indexOf([CtLiteralImpl]" ") < [CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]toolContextPath = [CtInvocationImpl][CtVariableReadImpl]toolContextPath.replace([CtLiteralImpl]" ", [CtLiteralImpl]"%20");
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"forwardtool call {} toolPathInfo {} ctx {}", [CtInvocationImpl][CtVariableReadImpl]req.getRequestURL(), [CtVariableReadImpl]toolPathInfo, [CtVariableReadImpl]toolContextPath);
        [CtLocalVariableImpl][CtCommentImpl]// if there is a stored request state, and path, extract that from the
        [CtCommentImpl]// session and reinstance it
        [CtTypeReferenceImpl]java.lang.StringBuilder ctx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtVariableReadImpl]toolContextPath);
        [CtAssignmentImpl][CtVariableWriteImpl]toolPathInfo = [CtInvocationImpl]fixPath([CtVariableReadImpl]toolPathInfo, [CtVariableReadImpl]ctx);
        [CtAssignmentImpl][CtVariableWriteImpl]toolContextPath = [CtInvocationImpl][CtVariableReadImpl]ctx.toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean needNative = [CtLiteralImpl]false;
        [CtIfImpl][CtCommentImpl]// let the tool do the the work (forward)
        if ([CtFieldReadImpl]enableDirect) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.StoredState ss = [CtInvocationImpl][CtFieldReadImpl]portalService.getStoredState();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]ss == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]toolContextPath.equals([CtInvocationImpl][CtVariableReadImpl]ss.getToolContextPath()))) [CtBlockImpl]{
                [CtInvocationImpl]setupForward([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]p, [CtVariableReadImpl]skin);
                [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]ToolURL.MANAGER, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.util.ToolURLManagerImpl([CtVariableReadImpl]res));
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"tool forward 1 {} context {}", [CtVariableReadImpl]toolPathInfo, [CtVariableReadImpl]toolContextPath);
                [CtAssignmentImpl][CtVariableWriteImpl]needNative = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]req.getAttribute([CtTypeAccessImpl]Tool.NATIVE_URL) != [CtLiteralImpl]null;
                [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]req.removeAttribute([CtTypeAccessImpl]Tool.NATIVE_URL);

                [CtInvocationImpl][CtVariableReadImpl]tool.forward([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]p, [CtVariableReadImpl]toolContextPath, [CtVariableReadImpl]toolPathInfo);
                [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]Tool.NATIVE_URL, [CtTypeAccessImpl]Tool.NATIVE_URL);

            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Restoring StoredState [{}]", [CtVariableReadImpl]ss);
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest sreq = [CtInvocationImpl][CtVariableReadImpl]ss.getRequest([CtVariableReadImpl]req);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Placement splacement = [CtInvocationImpl][CtVariableReadImpl]ss.getPlacement();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sctx = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtInvocationImpl][CtVariableReadImpl]ss.getToolContextPath());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stoolPathInfo = [CtInvocationImpl]fixPath([CtInvocationImpl][CtVariableReadImpl]ss.getToolPathInfo(), [CtVariableReadImpl]sctx);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String stoolContext = [CtInvocationImpl][CtVariableReadImpl]sctx.toString();
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool stool = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.ActiveToolManager.getActiveTool([CtInvocationImpl][CtVariableReadImpl]p.getToolId());
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sskin = [CtInvocationImpl][CtVariableReadImpl]ss.getSkin();
                [CtInvocationImpl]setupForward([CtVariableReadImpl]sreq, [CtVariableReadImpl]res, [CtVariableReadImpl]splacement, [CtVariableReadImpl]sskin);
                [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]ToolURL.MANAGER, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.util.ToolURLManagerImpl([CtVariableReadImpl]res));
                [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"tool forward 2 {} context {}", [CtVariableReadImpl]stoolPathInfo, [CtVariableReadImpl]stoolContext);
                [CtAssignmentImpl][CtVariableWriteImpl]needNative = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sreq.getAttribute([CtTypeAccessImpl]Tool.NATIVE_URL) != [CtLiteralImpl]null;
                [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]sreq.removeAttribute([CtTypeAccessImpl]Tool.NATIVE_URL);

                [CtInvocationImpl][CtVariableReadImpl]stool.forward([CtVariableReadImpl]sreq, [CtVariableReadImpl]res, [CtVariableReadImpl]splacement, [CtVariableReadImpl]stoolContext, [CtVariableReadImpl]stoolPathInfo);
                [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                    [CtInvocationImpl][CtVariableReadImpl]sreq.setAttribute([CtTypeAccessImpl]Tool.NATIVE_URL, [CtTypeAccessImpl]Tool.NATIVE_URL);

                [CtInvocationImpl][CtCommentImpl]// this is correct as we have checked the context path of the
                [CtCommentImpl]// tool
                [CtFieldReadImpl]portalService.setStoredState([CtLiteralImpl]null);
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl]setupForward([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]p, [CtVariableReadImpl]skin);
            [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]ToolURL.MANAGER, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.util.ToolURLManagerImpl([CtVariableReadImpl]res));
            [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"tool forward 3 {} context {}", [CtVariableReadImpl]toolPathInfo, [CtVariableReadImpl]toolContextPath);
            [CtAssignmentImpl][CtVariableWriteImpl]needNative = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]req.getAttribute([CtTypeAccessImpl]Tool.NATIVE_URL) != [CtLiteralImpl]null;
            [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]req.removeAttribute([CtTypeAccessImpl]Tool.NATIVE_URL);

            [CtInvocationImpl][CtVariableReadImpl]tool.forward([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]p, [CtVariableReadImpl]toolContextPath, [CtVariableReadImpl]toolPathInfo);
            [CtIfImpl]if ([CtVariableReadImpl]needNative)[CtBlockImpl]
                [CtInvocationImpl][CtVariableReadImpl]req.setAttribute([CtTypeAccessImpl]Tool.NATIVE_URL, [CtTypeAccessImpl]Tool.NATIVE_URL);

        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void forwardPortal([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration p, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolContextPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolPathInfo) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String portalPath = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath", [CtLiteralImpl]"/portal");
        [CtLocalVariableImpl][CtCommentImpl]// if there is a stored request state, and path, extract that from the
        [CtCommentImpl]// session and reinstance it
        [CtCommentImpl]// generate the forward to the tool page placement
        [CtTypeReferenceImpl]java.lang.String portalPlacementUrl = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]portalPath + [CtInvocationImpl]getPortalPageUrl([CtVariableReadImpl]p)) + [CtLiteralImpl]"?") + [CtInvocationImpl][CtVariableReadImpl]req.getQueryString();
        [CtInvocationImpl][CtVariableReadImpl]res.sendRedirect([CtVariableReadImpl]portalPlacementUrl);
        [CtReturnImpl]return;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getPortalPageUrl([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.SitePage sitePage = [CtInvocationImpl][CtVariableReadImpl]p.getContainingPage();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String page = [CtInvocationImpl][CtInvocationImpl]getSiteHelper().lookupPageToAlias([CtInvocationImpl][CtVariableReadImpl]p.getSiteId(), [CtVariableReadImpl]sitePage);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]page == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// Fall back to default of using the page Id.
            [CtVariableWriteImpl]page = [CtInvocationImpl][CtVariableReadImpl]p.getPageId();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder portalPageUrl = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]portalPageUrl.append([CtLiteralImpl]"/site/");
        [CtInvocationImpl][CtVariableReadImpl]portalPageUrl.append([CtInvocationImpl][CtVariableReadImpl]p.getSiteId());
        [CtInvocationImpl][CtVariableReadImpl]portalPageUrl.append([CtLiteralImpl]"/page/");
        [CtInvocationImpl][CtVariableReadImpl]portalPageUrl.append([CtVariableReadImpl]page);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]portalPageUrl.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Access the Servlet's information display.
     *
     * @return servlet information.
     */
    public [CtTypeReferenceImpl]java.lang.String getServletInfo() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"Sakai Charon Portal";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void includeBottom([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rcontext.uses([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.INCLUDE_BOTTOM)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String thisUser = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSessionUserId();
            [CtLocalVariableImpl][CtCommentImpl]// Get user preferences
            [CtTypeReferenceImpl]org.sakaiproject.user.api.Preferences prefs = [CtInvocationImpl][CtFieldReadImpl]preferencesService.getPreferences([CtVariableReadImpl]thisUser);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean showServerTime = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.show.time", [CtLiteralImpl]true);
            [CtIfImpl]if ([CtVariableReadImpl]showServerTime) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"showServerTime", [CtLiteralImpl]"true");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar now = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date nowDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]now.getTimeInMillis());
                [CtLocalVariableImpl][CtCommentImpl]// first set server date and time
                [CtTypeReferenceImpl]java.util.TimeZone serverTz = [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getDefault();
                [CtInvocationImpl][CtVariableReadImpl]now.setTimeZone([CtVariableReadImpl]serverTz);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"serverTzDisplay", [CtInvocationImpl][CtVariableReadImpl]serverTz.getDisplayName([CtInvocationImpl][CtVariableReadImpl]serverTz.inDaylightTime([CtVariableReadImpl]nowDate), [CtFieldReadImpl][CtTypeAccessImpl]java.util.TimeZone.[CtFieldReferenceImpl]SHORT));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"serverTzGMTOffset", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]now.getTimeInMillis() + [CtInvocationImpl][CtVariableReadImpl]now.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]ZONE_OFFSET)) + [CtInvocationImpl][CtVariableReadImpl]now.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]DST_OFFSET)));
                [CtLocalVariableImpl][CtCommentImpl]// provide the user's preferred timezone information if it is different
                [CtCommentImpl]// Get the Properties object that holds user's TimeZone preferences
                [CtTypeReferenceImpl]org.sakaiproject.entity.api.ResourceProperties tzprops = [CtInvocationImpl][CtVariableReadImpl]prefs.getProperties([CtTypeAccessImpl]TimeService.APPLICATION_ID);
                [CtLocalVariableImpl][CtCommentImpl]// Get the ID of the timezone using the timezone key.
                [CtCommentImpl]// Default to 'localTimeZone' (server timezone?)
                [CtTypeReferenceImpl]java.lang.String preferredTzId = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]tzprops.get([CtTypeAccessImpl]TimeService.TIMEZONE_KEY)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]preferredTzId != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]preferredTzId.equals([CtInvocationImpl][CtVariableReadImpl]serverTz.getID()))) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.TimeZone preferredTz = [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtVariableReadImpl]preferredTzId);
                    [CtInvocationImpl][CtVariableReadImpl]now.setTimeZone([CtVariableReadImpl]preferredTz);
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"showPreferredTzTime", [CtLiteralImpl]"true");
                    [CtInvocationImpl][CtCommentImpl]// now set up the portal information
                    [CtVariableReadImpl]rcontext.put([CtLiteralImpl]"preferredTzDisplay", [CtInvocationImpl][CtVariableReadImpl]preferredTz.getDisplayName([CtInvocationImpl][CtVariableReadImpl]preferredTz.inDaylightTime([CtVariableReadImpl]nowDate), [CtFieldReadImpl][CtTypeAccessImpl]java.util.TimeZone.[CtFieldReferenceImpl]SHORT));
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"preferredTzGMTOffset", [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]now.getTimeInMillis() + [CtInvocationImpl][CtVariableReadImpl]now.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]ZONE_OFFSET)) + [CtInvocationImpl][CtVariableReadImpl]now.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]DST_OFFSET)));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"showPreferredTzTime", [CtLiteralImpl]"false");
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pagepopup", [CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String copyright = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"bottom.copyrighttext");
            [CtLocalVariableImpl][CtJavaDocImpl]/**
             * Replace keyword in copyright message from sakai.properties
             * with the server's current year to auto-update of Copyright end date
             */
            [CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormat = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String currentServerYear = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat.format([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
            [CtAssignmentImpl][CtVariableWriteImpl]copyright = [CtInvocationImpl][CtVariableReadImpl]copyright.replaceAll([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.SERVER_COPYRIGHT_CURRENT_YEAR_KEYWORD, [CtVariableReadImpl]currentServerYear);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String service = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"ui.service", [CtLiteralImpl]"Sakai");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String serviceVersion = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"version.service", [CtLiteralImpl]"?");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sakaiVersion = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"version.sakai", [CtLiteralImpl]"?");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String server = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getServerId();
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] bottomNav = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getStrings([CtLiteralImpl]"bottomnav");
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] poweredByUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getStrings([CtLiteralImpl]"powered.url");
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] poweredByImage = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getStrings([CtLiteralImpl]"powered.img");
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] poweredByAltText = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getStrings([CtLiteralImpl]"powered.alt");
            [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Object>();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]bottomNav != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]bottomNav.length > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]bottomNav.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]l.add([CtArrayReadImpl][CtVariableReadImpl]bottomNav[[CtVariableReadImpl]i]);
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNav", [CtVariableReadImpl]l);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String neoChatProperty = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtTypeAccessImpl]Site.PROP_SITE_PORTAL_NEOCHAT, [CtLiteralImpl]"never");
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean neoChatAvailable = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"true".equals([CtVariableReadImpl]neoChatProperty) || [CtInvocationImpl][CtLiteralImpl]"false".equals([CtVariableReadImpl]neoChatProperty)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]neoChatAvailable = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]neoChatProperty);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]site != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String siteNeoChatStr = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]site.getProperties().getProperty([CtTypeAccessImpl]Site.PROP_SITE_PORTAL_NEOCHAT);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]siteNeoChatStr != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]neoChatAvailable = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]siteNeoChatStr);
                    }
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"always".equals([CtVariableReadImpl]neoChatProperty)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]neoChatAvailable = [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]chatHelper.checkChatPermitted([CtVariableReadImpl]thisUser)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]neoChatAvailable = [CtLiteralImpl]false;
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"neoChat", [CtVariableReadImpl]neoChatAvailable);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalChatPollInterval", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getInt([CtLiteralImpl]"portal.chat.pollInterval", [CtLiteralImpl]5000));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"neoAvatar", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.neoavatar", [CtLiteralImpl]true));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"neoChatVideo", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.chat.video", [CtLiteralImpl]true));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"portalVideoChatTimeout", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getInt([CtLiteralImpl]"portal.chat.video.timeout", [CtLiteralImpl]25));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]sakaiTutorialEnabled && [CtBinaryOperatorImpl]([CtVariableReadImpl]thisUser != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"1".equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]prefs.getProperties().getProperty([CtLiteralImpl]"sakaiTutorialFlag"))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"tutorial", [CtLiteralImpl]true);
                    [CtLocalVariableImpl][CtCommentImpl]// now save this in the user's preferences so we don't show it again
                    [CtTypeReferenceImpl]org.sakaiproject.user.api.PreferencesEdit preferences = [CtLiteralImpl]null;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]preferences = [CtInvocationImpl][CtFieldReadImpl]preferencesService.edit([CtVariableReadImpl]thisUser);
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.entity.api.ResourcePropertiesEdit props = [CtInvocationImpl][CtVariableReadImpl]preferences.getPropertiesEdit();
                        [CtInvocationImpl][CtVariableReadImpl]props.addProperty([CtLiteralImpl]"sakaiTutorialFlag", [CtLiteralImpl]"1");
                        [CtInvocationImpl][CtFieldReadImpl]preferencesService.commit([CtVariableReadImpl]preferences);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.SakaiException e1) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]log.error([CtInvocationImpl][CtVariableReadImpl]e1.getMessage(), [CtVariableReadImpl]e1);
                    }
                }
            }
            [CtIfImpl]if ([CtFieldReadImpl]sakaiThemeSwitcherEnabled) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"themeSwitcher", [CtLiteralImpl]true);
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean darkTheme = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int themeUserPref = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.entity.api.ResourceProperties themePrefs = [CtInvocationImpl][CtVariableReadImpl]prefs.getProperties([CtTypeAccessImpl]org.sakaiproject.user.api.PreferencesService.USER_SELECTED_UI_THEME_PREFS);
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]darkTheme = [CtInvocationImpl][CtVariableReadImpl]themePrefs.getBooleanProperty([CtLiteralImpl]"darkTheme");
                    [CtIfImpl][CtCommentImpl]// temporary:
                    if ([CtInvocationImpl][CtVariableReadImpl]themePrefs.getBooleanProperty([CtLiteralImpl]"darkTheme")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]themeUserPref = [CtLiteralImpl]1;
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]themeUserPref = [CtLiteralImpl]0;
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception any) [CtBlockImpl]{
                    [CtCommentImpl]// We'll default to light theme
                }
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"darkTheme", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]darkTheme));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"themeUserPref", [CtVariableReadImpl]themeUserPref);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]poweredByUrl != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]poweredByImage != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]poweredByAltText != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]poweredByUrl.length == [CtFieldReadImpl][CtVariableReadImpl]poweredByImage.length)) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]poweredByUrl.length == [CtFieldReadImpl][CtVariableReadImpl]poweredByAltText.length)) [CtBlockImpl]{
                [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Object>();
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]poweredByUrl.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
                        [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByUrl", [CtArrayReadImpl][CtVariableReadImpl]poweredByUrl[[CtVariableReadImpl]i]);
                        [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByImage", [CtArrayReadImpl][CtVariableReadImpl]poweredByImage[[CtVariableReadImpl]i]);
                        [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByAltText", [CtArrayReadImpl][CtVariableReadImpl]poweredByAltText[[CtVariableReadImpl]i]);
                        [CtInvocationImpl][CtVariableReadImpl]l.add([CtVariableReadImpl]m);
                    }
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavPoweredBy", [CtVariableReadImpl]l);
                }
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> l = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Object>();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByUrl", [CtLiteralImpl]"https://www.sakailms.org/");
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByImage", [CtLiteralImpl]"/library/image/poweredBySakai.png");
                [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"poweredByAltText", [CtLiteralImpl]"Powered by Sakai");
                [CtInvocationImpl][CtVariableReadImpl]l.add([CtVariableReadImpl]m);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavPoweredBy", [CtVariableReadImpl]l);
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavService", [CtVariableReadImpl]service);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavCopyright", [CtVariableReadImpl]copyright);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavServiceVersion", [CtVariableReadImpl]serviceVersion);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavSakaiVersion", [CtVariableReadImpl]sakaiVersion);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bottomNavServer", [CtVariableReadImpl]server);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean useBullhornAlerts = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.bullhorns.enabled", [CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"useBullhornAlerts", [CtVariableReadImpl]useBullhornAlerts);
            [CtIfImpl]if ([CtVariableReadImpl]useBullhornAlerts) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int bullhornAlertInterval = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getInt([CtLiteralImpl]"portal.bullhorns.poll.interval", [CtLiteralImpl]240000);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"bullhornsPollInterval", [CtVariableReadImpl]bullhornAlertInterval);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String faviconURL = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.favicon.url");
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"faviconURL", [CtVariableReadImpl]faviconURL);
            [CtLocalVariableImpl][CtCommentImpl]// SAK-25931 - Do not remove this from session here - removal is done by /direct
            [CtTypeReferenceImpl]org.sakaiproject.tool.api.Session s = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userWarning = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]s.getAttribute([CtLiteralImpl]"userWarning")));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userWarning", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Boolean([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isNotEmpty([CtVariableReadImpl]userWarning)));
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"pasystem.enabled", [CtLiteralImpl]true)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.pasystem.api.PASystem paSystem = [CtInvocationImpl](([CtTypeReferenceImpl]org.sakaiproject.pasystem.api.PASystem) ([CtTypeAccessImpl]org.sakaiproject.component.cover.ComponentManager.get([CtFieldReadImpl]org.sakaiproject.pasystem.api.PASystem.class)));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"paSystemEnabled", [CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"paSystem", [CtVariableReadImpl]paSystem);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void includeLogin([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]rcontext.uses([CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.INCLUDE_LOGIN)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// for the main login/out link
            [CtTypeReferenceImpl]java.lang.String logInOutUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.RequestFilter.serverUrl([CtVariableReadImpl]req);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String image1 = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtCommentImpl]// for a possible second link
            [CtTypeReferenceImpl]java.lang.String logInOutUrl2 = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message2 = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String image2 = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String logoutWarningMessage = [CtLiteralImpl]"";
            [CtLocalVariableImpl][CtCommentImpl]// for showing user display name and id next to logout (SAK-10492)
            [CtTypeReferenceImpl]java.lang.String loginUserDispName = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loginUserDispId = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loginUserId = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loginUserFirstName = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean displayUserloginInfo = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"display.userlogin.info", [CtLiteralImpl]true);
            [CtLocalVariableImpl][CtCommentImpl]// check for the top.login (where the login fields are present
            [CtCommentImpl]// instead
            [CtCommentImpl]// of a login link, but ignore it if container.login is set
            [CtTypeReferenceImpl]boolean topLogin = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"top.login", [CtLiteralImpl]true);
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean containerLogin = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"container.login", [CtLiteralImpl]false);
            [CtIfImpl]if ([CtVariableReadImpl]containerLogin)[CtBlockImpl]
                [CtAssignmentImpl][CtVariableWriteImpl]topLogin = [CtLiteralImpl]false;

            [CtIfImpl][CtCommentImpl]// if not logged in they get login
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// we don't need any of this if we are doing top login
                if ([CtUnaryOperatorImpl]![CtVariableReadImpl]topLogin) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]logInOutUrl += [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath") + [CtLiteralImpl]"/login";
                    [CtLocalVariableImpl][CtCommentImpl]// let the login url be overridden by configuration
                    [CtTypeReferenceImpl]java.lang.String overrideLoginUrl = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"login.url"));
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]overrideLoginUrl != [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]logInOutUrl = [CtVariableReadImpl]overrideLoginUrl;

                    [CtAssignmentImpl][CtCommentImpl]// check for a login text override
                    [CtVariableWriteImpl]message = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"login.text"));
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.login");

                    [CtAssignmentImpl][CtCommentImpl]// check for an image for the login
                    [CtVariableWriteImpl]image1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"login.icon"));
                    [CtIfImpl][CtCommentImpl]// check for a possible second, xlogin link
                    if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.toString().equalsIgnoreCase([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"xlogin.enabled"))) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// get the text and image as configured
                        [CtVariableWriteImpl]message2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"xlogin.text"));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]message2 == [CtLiteralImpl]null)[CtBlockImpl]
                            [CtAssignmentImpl][CtVariableWriteImpl]message2 = [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.xlogin");

                        [CtAssignmentImpl][CtVariableWriteImpl]image2 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"xlogin.icon"));
                        [CtAssignmentImpl][CtVariableWriteImpl]logInOutUrl2 = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath") + [CtLiteralImpl]"/xlogin";
                    }
                }
            } else [CtBlockImpl][CtCommentImpl]// if logged in they get logout
            {
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]logInOutUrl += [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath") + [CtLiteralImpl]"/logout";
                [CtIfImpl][CtCommentImpl]// get current user display id and name
                if ([CtVariableReadImpl]displayUserloginInfo) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.User thisUser = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.user.cover.UserDirectoryService.getCurrentUser();
                    [CtAssignmentImpl][CtVariableWriteImpl]loginUserDispId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Validator.escapeHtml([CtInvocationImpl][CtVariableReadImpl]thisUser.getDisplayId());
                    [CtAssignmentImpl][CtVariableWriteImpl]loginUserId = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Validator.escapeHtml([CtInvocationImpl][CtVariableReadImpl]thisUser.getId());
                    [CtAssignmentImpl][CtVariableWriteImpl]loginUserDispName = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Validator.escapeHtml([CtInvocationImpl][CtVariableReadImpl]thisUser.getDisplayName());
                    [CtAssignmentImpl][CtVariableWriteImpl]loginUserFirstName = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Validator.escapeHtml([CtInvocationImpl][CtVariableReadImpl]thisUser.getFirstName());
                }
                [CtLocalVariableImpl][CtCommentImpl]// check if current user is being impersonated (by become user/sutool)
                [CtTypeReferenceImpl]java.lang.String impersonatorDisplayId = [CtInvocationImpl]getImpersonatorDisplayId();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]impersonatorDisplayId.isEmpty()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getFormattedMessage([CtLiteralImpl]"sit_return", [CtVariableReadImpl]impersonatorDisplayId);
                    [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"impersonatorDisplayId", [CtVariableReadImpl]impersonatorDisplayId);
                }
                [CtIfImpl][CtCommentImpl]// check for a logout text override
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]message == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]message = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"logout.text", [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"sit_log"));
                }
                [CtAssignmentImpl][CtCommentImpl]// check for an image for the logout
                [CtVariableWriteImpl]image1 = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"logout.icon"));
                [CtAssignmentImpl][CtCommentImpl]// since we are doing logout, cancel top.login
                [CtVariableWriteImpl]topLogin = [CtLiteralImpl]false;
                [CtAssignmentImpl][CtVariableWriteImpl]logoutWarningMessage = [CtConditionalImpl]([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.logout.confirmation", [CtLiteralImpl]false)) ? [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"sit_logout_warn") : [CtLiteralImpl]"";
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"userIsLoggedIn", [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]session.getUserId() != [CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginTopLogin", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]topLogin));
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"logoutWarningMessage", [CtVariableReadImpl]logoutWarningMessage);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]topLogin) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginLogInOutUrl", [CtVariableReadImpl]logInOutUrl);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginMessage", [CtVariableReadImpl]message);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginImage1", [CtVariableReadImpl]image1);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginHasImage1", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]image1 != [CtLiteralImpl]null));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginLogInOutUrl2", [CtVariableReadImpl]logInOutUrl2);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginHasLogInOutUrl2", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]logInOutUrl2 != [CtLiteralImpl]null));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginMessage2", [CtVariableReadImpl]message2);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginImage2", [CtVariableReadImpl]image2);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginHasImage2", [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]image2 != [CtLiteralImpl]null));
                [CtCommentImpl]// put out the links version
                [CtCommentImpl]// else put out the fields that will send to the login interface
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eidWording = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pwWording = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtVariableWriteImpl]eidWording = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.userid"));
                [CtAssignmentImpl][CtVariableWriteImpl]pwWording = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.pass"));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String eidPlaceholder = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.inputuserplaceholder"));
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String pwPlaceholder = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.trimToNull([CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.inputpasswordplaceholder"));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]eidWording == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]eidWording = [CtLiteralImpl]"eid";

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pwWording == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]pwWording = [CtLiteralImpl]"pw";

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]eidPlaceholder == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]eidPlaceholder = [CtLiteralImpl]"";

                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pwPlaceholder == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtVariableWriteImpl]pwPlaceholder = [CtLiteralImpl]"";

                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loginWording = [CtInvocationImpl][CtFieldReadImpl]org.sakaiproject.portal.charon.SkinnableCharonPortal.rloader.getString([CtLiteralImpl]"log.login");
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginPortalPath", [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portalPath"));
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginEidWording", [CtVariableReadImpl]eidWording);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginPwWording", [CtVariableReadImpl]pwWording);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginWording", [CtVariableReadImpl]loginWording);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"eidPlaceholder", [CtVariableReadImpl]eidPlaceholder);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"pwPlaceholder", [CtVariableReadImpl]pwPlaceholder);
                [CtInvocationImpl][CtCommentImpl]// setup for the redirect after login
                [CtVariableReadImpl]session.setAttribute([CtTypeAccessImpl]Tool.HELPER_DONE_URL, [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getPortalUrl());
            }
            [CtIfImpl]if ([CtVariableReadImpl]displayUserloginInfo) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginUserDispName", [CtVariableReadImpl]loginUserDispName);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginUserFirstName", [CtVariableReadImpl]loginUserFirstName);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginUserDispId", [CtVariableReadImpl]loginUserDispId);
                [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"loginUserId", [CtVariableReadImpl]loginUserId);
            }
            [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"displayUserloginInfo", [CtBinaryOperatorImpl][CtVariableReadImpl]displayUserloginInfo && [CtBinaryOperatorImpl]([CtVariableReadImpl]loginUserDispId != [CtLiteralImpl]null));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param rcontext
     * @param res
     * @param req
     * @param session
     * @param site
     * @param page
     * @param toolContextPath
     * @param portalPrefix
     * @return  * @throws IOException
     */
    public [CtTypeReferenceImpl]void includeWorksite([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.Site site, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.SitePage page, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String toolContextPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String portalPrefix) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]worksiteHandler.includeWorksite([CtVariableReadImpl]rcontext, [CtVariableReadImpl]res, [CtVariableReadImpl]req, [CtVariableReadImpl]session, [CtVariableReadImpl]site, [CtVariableReadImpl]page, [CtVariableReadImpl]toolContextPath, [CtVariableReadImpl]portalPrefix);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initialize the servlet.
     *
     * @param config
     * 		The servlet config.
     * @throws ServletException
     */
    public [CtTypeReferenceImpl]void init([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.ServletConfig config) throws [CtTypeReferenceImpl]javax.servlet.ServletException [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.init([CtVariableReadImpl]config);
        [CtAssignmentImpl][CtFieldWriteImpl]portalContext = [CtInvocationImpl][CtVariableReadImpl]config.getInitParameter([CtLiteralImpl]"portal.context");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]portalContext == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]portalContext.length() == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]portalContext = [CtFieldReadImpl]DEFAULT_PORTAL_CONTEXT;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean findPageAliases = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.use.page.aliases", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]siteHelper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.site.PortalSiteHelperImpl([CtThisAccessImpl]this, [CtVariableReadImpl]findPageAliases);
        [CtAssignmentImpl][CtFieldWriteImpl]portalService = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.api.cover.PortalService.getInstance();
        [CtAssignmentImpl][CtFieldWriteImpl]securityService = [CtInvocationImpl](([CtTypeReferenceImpl]org.sakaiproject.authz.api.SecurityService) ([CtTypeAccessImpl]org.sakaiproject.component.cover.ComponentManager.get([CtLiteralImpl]"org.sakaiproject.authz.api.SecurityService")));
        [CtAssignmentImpl][CtFieldWriteImpl]chatHelper = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.api.cover.PortalChatPermittedHelper.getInstance();
        [CtAssignmentImpl][CtFieldWriteImpl]preferencesService = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ComponentManager.get([CtFieldReadImpl]org.sakaiproject.user.api.PreferencesService.class);
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"init()");
        [CtAssignmentImpl][CtFieldWriteImpl]forceContainer = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"login.use.xlogin.to.relogin", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]handlerPrefix = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"portal.handler.default", [CtLiteralImpl]"site");
        [CtAssignmentImpl][CtFieldWriteImpl]gatewaySiteUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getString([CtLiteralImpl]"gatewaySiteUrl", [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl]sakaiTutorialEnabled = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.use.tutorial", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]sakaiThemeSwitcherEnabled = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getBoolean([CtLiteralImpl]"portal.themeswitcher", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]basicAuth = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.util.BasicAuth();
        [CtInvocationImpl][CtFieldReadImpl]basicAuth.init();
        [CtAssignmentImpl][CtFieldWriteImpl]enableDirect = [CtInvocationImpl][CtFieldReadImpl]portalService.isEnableDirect();
        [CtInvocationImpl][CtCommentImpl]// do this before adding handlers to prevent handlers registering 2
        [CtCommentImpl]// times.
        [CtCommentImpl]// if the handlers were already there they will be re-registered,
        [CtCommentImpl]// but when they are added again, they will be replaced.
        [CtCommentImpl]// warning messages will appear, but the end state will be the same.
        [CtFieldReadImpl]portalService.addPortal([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]worksiteHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.WorksiteHandler();
        [CtAssignmentImpl][CtFieldWriteImpl]siteHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.SiteHandler();
        [CtInvocationImpl]addHandler([CtFieldReadImpl]siteHandler);
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.SiteResetHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.ToolHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.ToolResetHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.PageResetHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.PageHandler());
        [CtInvocationImpl]addHandler([CtFieldReadImpl]worksiteHandler);
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.WorksiteResetHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.RssHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.AtomHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.OpmlHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.NavLoginHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.PresenceHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.HelpHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.ReLoginHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.LoginHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.XLoginHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.LogoutHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.ErrorDoneHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.ErrorReportHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.StaticStylesHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.StaticScriptsHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.DirectToolHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.RoleSwitchHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.RoleSwitchOutHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.TimeoutDialogHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.JoinHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.FavoritesHandler());
        [CtInvocationImpl]addHandler([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.handlers.GenerateBugReportHandler());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Register a handler for a URL stub
     *
     * @param handler
     */
    private [CtTypeReferenceImpl]void addHandler([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalHandler handler) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]portalService.addHandler([CtThisAccessImpl]this, [CtVariableReadImpl]handler);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void removeHandler([CtParameterImpl][CtTypeReferenceImpl]java.lang.String urlFragment) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]portalService.removeHandler([CtThisAccessImpl]this, [CtVariableReadImpl]urlFragment);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send the POST request to login
     *
     * @param req
     * @param res
     * @param session
     * @throws IOException
     */
    protected [CtTypeReferenceImpl]void postLogin([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String loginPath) throws [CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ActiveTool tool = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.ActiveToolManager.getActiveTool([CtLiteralImpl]"sakai.login");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String context = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]req.getContextPath() + [CtInvocationImpl][CtVariableReadImpl]req.getServletPath()) + [CtLiteralImpl]"/") + [CtVariableReadImpl]loginPath;
        [CtInvocationImpl][CtVariableReadImpl]tool.help([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]context, [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtVariableReadImpl]loginPath);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Output some session information
     *
     * @param rcontext
     * 		The print writer
     * @param html
     * 		If true, output in HTML, else in text.
     */
    protected [CtTypeReferenceImpl]void showSession([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]boolean html) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get the current user session information
        [CtTypeReferenceImpl]org.sakaiproject.tool.api.Session s = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"sessionSession", [CtVariableReadImpl]s);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.ToolSession ts = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentToolSession();
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"sessionToolSession", [CtVariableReadImpl]ts);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sendResponse([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String template, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String contentType) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// headers
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]contentType == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]res.setContentType([CtLiteralImpl]"text/html; charset=UTF-8");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]res.setContentType([CtVariableReadImpl]contentType);
        }
        [CtInvocationImpl][CtVariableReadImpl]res.addDateHeader([CtLiteralImpl]"Expires", [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() - [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]1000L * [CtLiteralImpl]60L) * [CtLiteralImpl]60L) * [CtLiteralImpl]24L) * [CtLiteralImpl]365L));
        [CtInvocationImpl][CtVariableReadImpl]res.addDateHeader([CtLiteralImpl]"Last-Modified", [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
        [CtInvocationImpl][CtVariableReadImpl]res.addHeader([CtLiteralImpl]"Cache-Control", [CtLiteralImpl]"no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        [CtInvocationImpl][CtVariableReadImpl]res.addHeader([CtLiteralImpl]"Pragma", [CtLiteralImpl]"no-cache");
        [CtLocalVariableImpl][CtCommentImpl]// get the writer
        [CtTypeReferenceImpl]java.io.PrintWriter out = [CtInvocationImpl][CtVariableReadImpl]res.getWriter();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderEngine rengine = [CtInvocationImpl][CtVariableReadImpl]rcontext.getRenderEngine();
            [CtInvocationImpl][CtVariableReadImpl]rengine.render([CtVariableReadImpl]template, [CtVariableReadImpl]rcontext, [CtVariableReadImpl]out);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"Failed to render template ", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the type ("course", "project", "workspace", "mySpecialSiteType",
     * etc) of the given site; special handling of returning "workspace" for
     * user workspace sites. This method is tightly coupled to site skinning.
     */
    public [CtTypeReferenceImpl]java.lang.String calcSiteType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String siteType = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]siteId != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]siteId.length() != [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.isUserSite([CtVariableReadImpl]siteId)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtLiteralImpl]"workspace";
            } else [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getSite([CtVariableReadImpl]siteId).getType();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.exception.IdUnusedException ex) [CtBlockImpl]{
                    [CtCommentImpl]// ignore, the site wasn't found
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]siteType != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]siteType.trim().length() == [CtLiteralImpl]0))[CtBlockImpl]
            [CtAssignmentImpl][CtVariableWriteImpl]siteType = [CtLiteralImpl]null;

        [CtReturnImpl]return [CtVariableReadImpl]siteType;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void logXEntry() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Exception e = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Exception();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StackTraceElement se = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]e.getStackTrace()[[CtLiteralImpl]1];
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Log marker " + [CtInvocationImpl][CtVariableReadImpl]se.getMethodName()) + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]se.getFileName()) + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]se.getLineNumber());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check for any just expired sessions and redirect
     *
     * @return true if we redirected, false if not
     */
    public [CtTypeReferenceImpl]boolean redirectIfLoggedOut([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// if we are in a newly created session where we had an invalid
        [CtCommentImpl]// (presumed timed out) session in the request,
        [CtCommentImpl]// send script to cause a sakai top level redirect
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.thread_local.cover.ThreadLocalManager.get([CtTypeAccessImpl]SessionManager.CURRENT_INVALID_SESSION) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String loggedOutUrl = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.component.cover.ServerConfigurationService.getLoggedOutUrl();
            [CtInvocationImpl]sendPortalRedirect([CtVariableReadImpl]res, [CtVariableReadImpl]loggedOutUrl);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Send a redirect so our Portal window ends up at the url, via javascript.
     *
     * @param url
     * 		The redirect url
     */
    protected [CtTypeReferenceImpl]void sendPortalRedirect([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalRenderContext rcontext = [CtInvocationImpl]startPageContext([CtLiteralImpl]"", [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]rcontext.put([CtLiteralImpl]"redirectUrl", [CtVariableReadImpl]url);
        [CtInvocationImpl]sendResponse([CtVariableReadImpl]rcontext, [CtVariableReadImpl]res, [CtLiteralImpl]"portal-redirect", [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Compute the string that will identify the user site for this user - use
     * the EID if possible
     *
     * @param userId
     * 		The user id
     * @return The site "ID" but based on the user EID
     */
    public [CtTypeReferenceImpl]java.lang.String getUserEidBasedSiteId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userId) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// use the user EID
            [CtTypeReferenceImpl]java.lang.String eid = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.user.cover.UserDirectoryService.getUserEid([CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getUserSiteId([CtVariableReadImpl]eid);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.UserNotDefinedException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.warn([CtBinaryOperatorImpl][CtLiteralImpl]"getUserEidBasedSiteId: user id not found for eid: " + [CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getUserSiteId([CtVariableReadImpl]userId);
        }
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sakaiproject.portal.api.Portal#getPageFilter()
     */
    public [CtTypeReferenceImpl]org.sakaiproject.portal.api.PageFilter getPageFilter() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]pageFilter;
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sakaiproject.portal.api.Portal#setPageFilter(org.sakaiproject.portal.api.PageFilter)
     */
    public [CtTypeReferenceImpl]void setPageFilter([CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.portal.api.PageFilter pageFilter) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.pageFilter = [CtVariableReadImpl]pageFilter;
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sakaiproject.portal.api.Portal#getSiteHelper()
     */
    public [CtTypeReferenceImpl]org.sakaiproject.portal.api.PortalSiteHelper getSiteHelper() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.siteHelper;
    }

    [CtMethodImpl][CtCommentImpl]/* (non-Javadoc)
    @see org.sakaiproject.portal.api.Portal#getSiteNeighbourhoodService()
     */
    public [CtTypeReferenceImpl]org.sakaiproject.portal.api.SiteNeighbourhoodService getSiteNeighbourhoodService() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]portalService.getSiteNeighbourhoodService();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Find a cookie by this name from the request
     *
     * @param req
     * 		The servlet request.
     * @param name
     * 		The cookie name
     * @return The cookie of this name in the request, or null if not found.
     */
    public [CtTypeReferenceImpl]javax.servlet.http.Cookie findCookie([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]javax.servlet.http.Cookie[] cookies = [CtInvocationImpl][CtVariableReadImpl]req.getCookies();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]cookies != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]cookies.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]cookies[[CtVariableReadImpl]i].getName().equals([CtVariableReadImpl]name)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtArrayReadImpl][CtVariableReadImpl]cookies[[CtVariableReadImpl]i];
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Do the getSiteSkin, adjusting for the overall skin/templates for the portal.
     *
     * @return The skin
     */
    protected [CtTypeReferenceImpl]java.lang.String getSiteSkin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String siteId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String skin = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.site.cover.SiteService.getSiteSkin([CtVariableReadImpl]siteId);
        [CtReturnImpl]return [CtInvocationImpl]getSkin([CtVariableReadImpl]skin);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Do the getSkin, adjusting for the overall skin/templates for the portal.
     *
     * @return The skin
     */
    protected [CtTypeReferenceImpl]java.lang.String getSkin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String skin) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.portal.util.CSSUtils.adjustCssSkinFolder([CtVariableReadImpl]skin);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Renders the content of a tool into a {@link BufferedContentRenderResult}
     *
     * @param res
     * 		{@link HttpServletResponse}
     * @param req
     * 		{@link HttpServletRequest}
     * @param placement
     * 		{@link ToolConfiguration}
     * @return {@link BufferedContentRenderResult} with a head and body representing the appropriate bits for the tool or null if unable to render.
     */
    [CtTypeReferenceImpl]org.sakaiproject.portal.render.api.RenderResult getInlineRenderingForTool([CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletResponse res, [CtParameterImpl][CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest req, [CtParameterImpl][CtTypeReferenceImpl]org.sakaiproject.site.api.ToolConfiguration placement) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.portal.render.api.RenderResult rval = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtCommentImpl]// allow a final chance for a tool to be inlined, based on it's tool configuration
        [CtCommentImpl]// set renderInline = true to enable this
        [CtTypeReferenceImpl]boolean renderInline = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.BooleanUtils.toBoolean([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]placement.getConfig().getProperty([CtLiteralImpl]"renderInline"));
        [CtIfImpl]if ([CtVariableReadImpl]renderInline) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// build tool context path directly to the tool
            [CtTypeReferenceImpl]java.lang.String toolContextPath = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]req.getContextPath() + [CtInvocationImpl][CtVariableReadImpl]req.getServletPath()) + [CtLiteralImpl]"/site/") + [CtInvocationImpl][CtVariableReadImpl]placement.getSiteId()) + [CtLiteralImpl]"/tool/") + [CtInvocationImpl][CtVariableReadImpl]placement.getId();
            [CtLocalVariableImpl][CtCommentImpl]// setup the rest of the params
            [CtArrayTypeReferenceImpl]java.lang.String[] parts = [CtInvocationImpl]getParts([CtVariableReadImpl]req);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String toolPathInfo = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.util.Web.makePath([CtVariableReadImpl]parts, [CtLiteralImpl]5, [CtFieldReadImpl][CtVariableReadImpl]parts.length);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session session = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
            [CtLocalVariableImpl][CtCommentImpl]// get the buffered content
            [CtTypeReferenceImpl]java.lang.Object buffer = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.siteHandler.bufferContent([CtVariableReadImpl]req, [CtVariableReadImpl]res, [CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]placement.getId(), [CtVariableReadImpl]toolContextPath, [CtVariableReadImpl]toolPathInfo, [CtVariableReadImpl]placement);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]buffer instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.Map) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> bufferMap = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String>) (buffer));
                [CtAssignmentImpl][CtVariableWriteImpl]rval = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.portal.charon.BufferedContentRenderResult([CtVariableReadImpl]placement, [CtInvocationImpl][CtVariableReadImpl]bufferMap.get([CtLiteralImpl]"responseHead"), [CtInvocationImpl][CtVariableReadImpl]bufferMap.get([CtLiteralImpl]"responseBody"));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]rval;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if current user is being impersonated (via become user/sutool) and returns displayId of
     * the impersonator. Adapted from SkinnableLogin's isImpersonating()
     *
     * @return displayId of impersonator, or empty string if not being impersonated
     */
    private [CtTypeReferenceImpl]java.lang.String getImpersonatorDisplayId() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.tool.api.Session currentSession = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.tool.cover.SessionManager.getCurrentSession();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.event.api.UsageSession originalSession = [CtInvocationImpl](([CtTypeReferenceImpl]org.sakaiproject.event.api.UsageSession) ([CtVariableReadImpl]currentSession.getAttribute([CtTypeAccessImpl]UsageSessionService.USAGE_SESSION_KEY)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]originalSession != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String originalUserId = [CtInvocationImpl][CtVariableReadImpl]originalSession.getUserId();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.equals([CtInvocationImpl][CtVariableReadImpl]currentSession.getUserId(), [CtVariableReadImpl]originalUserId)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.User originalUser = [CtInvocationImpl][CtTypeAccessImpl]org.sakaiproject.user.cover.UserDirectoryService.getUser([CtVariableReadImpl]originalUserId);
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]originalUser.getDisplayId();
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.sakaiproject.user.api.UserNotDefinedException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"Unable to retrieve user for id: {}", [CtVariableReadImpl]originalUserId);
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]"";
    }
}
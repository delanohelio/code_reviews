[CompilationUnitImpl][CtCommentImpl]/* Licensed to The Apereo Foundation under one or more contributor license
agreements. See the NOTICE file distributed with this work for additional
information regarding copyright ownership.

The Apereo Foundation licenses this file to you under the Apache License,
Version 2.0, (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package com.tle.web.searching.section;
[CtUnresolvedImport]import com.tle.core.institution.InstitutionService;
[CtUnresolvedImport]import com.tle.core.security.TLEAclManager;
[CtUnresolvedImport]import com.tle.web.resources.ResourcesService;
[CtUnresolvedImport]import com.tle.web.sections.equella.annotation.PlugKey;
[CtUnresolvedImport]import com.tle.common.usermanagement.user.CurrentUser;
[CtUnresolvedImport]import com.tle.web.selection.SelectionSession;
[CtUnresolvedImport]import com.tle.web.sections.SectionInfo;
[CtUnresolvedImport]import com.tle.web.sections.events.RenderEventContext;
[CtUnresolvedImport]import com.tle.web.settings.UISettingsJava;
[CtUnresolvedImport]import com.tle.web.template.section.event.BlueBarEventListener;
[CtUnresolvedImport]import com.tle.web.freemarker.FreemarkerFactory;
[CtImportImpl]import javax.inject.Inject;
[CtUnresolvedImport]import com.tle.web.sections.SectionResult;
[CtUnresolvedImport]import com.tle.web.search.base.ContextableSearchSection;
[CtUnresolvedImport]import com.tle.web.template.RenderNewSearchPage;
[CtUnresolvedImport]import com.tle.web.template.section.event.BlueBarEvent;
[CtUnresolvedImport]import com.tle.exceptions.AccessDeniedException;
[CtUnresolvedImport]import com.tle.web.freemarker.annotations.ViewFactory;
[CtUnresolvedImport]import com.dytech.edge.web.WebConstants;
[CtUnresolvedImport]import com.tle.web.resources.PluginResourceHelper;
[CtUnresolvedImport]import com.tle.web.sections.render.Label;
[CtUnresolvedImport]import com.tle.web.sections.equella.layout.ContentLayout;
[CtUnresolvedImport]import com.tle.web.sections.events.RenderContext;
[CtUnresolvedImport]import com.tle.web.sections.generic.InfoBookmark;
[CtUnresolvedImport]import com.tle.web.login.LogonSection;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"nls")
public class RootSearchSection extends [CtTypeReferenceImpl]com.tle.web.search.base.ContextableSearchSection<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.tle.web.search.base.ContextableSearchSection.Model> implements [CtTypeReferenceImpl]com.tle.web.template.section.event.BlueBarEventListener {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SEARCHURL = [CtLiteralImpl]"/searching.do";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SEARCH_SESSIONKEY = [CtLiteralImpl]"searchContext";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.tle.web.resources.PluginResourceHelper urlHelper = [CtInvocationImpl][CtTypeAccessImpl]com.tle.web.resources.ResourcesService.getResourceHelper([CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.class);

    [CtFieldImpl][CtAnnotationImpl]@com.tle.web.sections.equella.annotation.PlugKey([CtLiteralImpl]"searching.search.title")
    private static [CtTypeReferenceImpl]com.tle.web.sections.render.Label LABEL_TITLE;

    [CtFieldImpl][CtAnnotationImpl]@com.tle.web.freemarker.annotations.ViewFactory
    private [CtTypeReferenceImpl]com.tle.web.freemarker.FreemarkerFactory view;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    private [CtTypeReferenceImpl]com.tle.core.security.TLEAclManager aclManager;

    [CtFieldImpl][CtAnnotationImpl]@javax.inject.Inject
    private [CtTypeReferenceImpl]com.tle.core.institution.InstitutionService institutionService;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.tle.web.sections.render.Label getTitle([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.sections.SectionInfo info) [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.LABEL_TITLE;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getSessionKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.SEARCH_SESSIONKEY;
    }

    [CtMethodImpl][CtCommentImpl]// For child sections that need to skip new Search UI, override this function and return false.
    public [CtTypeReferenceImpl]boolean useNewSearch() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.tle.web.sections.SectionResult renderHtml([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.sections.events.RenderEventContext context) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]aclManager.filterNonGrantedPrivileges([CtTypeAccessImpl]WebConstants.SEARCH_PAGE_PRIVILEGE).isEmpty()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.tle.common.usermanagement.user.CurrentUser.isGuest()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.tle.web.login.LogonSection.forwardToLogon([CtVariableReadImpl]context, [CtInvocationImpl][CtFieldReadImpl]institutionService.removeInstitution([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.tle.web.sections.generic.InfoBookmark([CtVariableReadImpl]context).getHref()), [CtTypeAccessImpl]LogonSection.STANDARD_LOGON_PATH);
                [CtReturnImpl]return [CtLiteralImpl]null;
            }
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.tle.exceptions.AccessDeniedException([CtInvocationImpl][CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.urlHelper.getString([CtLiteralImpl]"missingprivileges", [CtTypeAccessImpl]WebConstants.SEARCH_PAGE_PRIVILEGE));
        }
        [CtLocalVariableImpl][CtCommentImpl]// If this method is triggered from Selection Section, then check if Selection Section
        [CtCommentImpl]// is in 'structured' mode. If yes, then render the new search page if it's enabled.
        [CtTypeReferenceImpl]com.tle.web.selection.SelectionSession selectionSession = [CtInvocationImpl][CtFieldReadImpl]selectionService.getCurrentSession([CtVariableReadImpl]context);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isNewSearchUIInSelectionSession([CtVariableReadImpl]selectionSession) && [CtInvocationImpl]useNewSearch()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]getModel([CtVariableReadImpl]context).setNewSearchUIContent([CtInvocationImpl][CtTypeAccessImpl]com.tle.web.template.RenderNewSearchPage.renderNewSearchPage([CtVariableReadImpl]context));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.renderHtml([CtVariableReadImpl]context);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.tle.web.sections.SectionInfo createForward([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.sections.SectionInfo from) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]from.createForward([CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.SEARCHURL);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void addBlueBarResults([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.sections.events.RenderContext context, [CtParameterImpl][CtTypeReferenceImpl]com.tle.web.template.section.event.BlueBarEvent event) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]event.addHelp([CtInvocationImpl][CtFieldReadImpl]view.createResult([CtLiteralImpl]"searching-help.ftl", [CtThisAccessImpl]this));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]com.tle.web.sections.equella.layout.ContentLayout getDefaultLayout([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.sections.SectionInfo info) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]selectionService.getCurrentSession([CtVariableReadImpl]info) != [CtLiteralImpl]null ? [CtInvocationImpl][CtSuperAccessImpl]super.getDefaultLayout([CtVariableReadImpl]info) : [CtFieldReadImpl]com.tle.web.sections.equella.layout.ContentLayout.ONE_COLUMN;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getPageName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.tle.web.searching.section.RootSearchSection.SEARCHURL;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isNewSearchUIInSelectionSession([CtParameterImpl][CtTypeReferenceImpl]com.tle.web.selection.SelectionSession selectionSession) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selectionSession != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.tle.web.settings.UISettingsJava.getUISettings().isNewSearchActive();
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }
}
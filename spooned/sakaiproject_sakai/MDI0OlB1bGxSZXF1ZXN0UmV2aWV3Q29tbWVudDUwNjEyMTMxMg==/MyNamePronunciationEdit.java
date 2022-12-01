[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2003-2019 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package org.sakaiproject.profile2.tool.pages.panels;
[CtUnresolvedImport]import org.apache.wicket.markup.html.form.Form;
[CtUnresolvedImport]import org.apache.wicket.spring.injection.annot.SpringBean;
[CtUnresolvedImport]import org.apache.wicket.model.StringResourceModel;
[CtUnresolvedImport]import org.sakaiproject.profile2.util.ProfileConstants;
[CtUnresolvedImport]import org.sakaiproject.api.common.edu.person.SakaiPerson;
[CtUnresolvedImport]import org.apache.wicket.markup.html.form.TextField;
[CtUnresolvedImport]import org.apache.wicket.markup.html.link.ExternalLink;
[CtUnresolvedImport]import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
[CtUnresolvedImport]import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtUnresolvedImport]import org.apache.wicket.ajax.AjaxRequestTarget;
[CtUnresolvedImport]import org.sakaiproject.profile2.logic.SakaiProxy;
[CtImportImpl]import java.util.Base64;
[CtImportImpl]import org.apache.commons.lang3.RandomStringUtils;
[CtUnresolvedImport]import org.apache.wicket.markup.html.form.HiddenField;
[CtUnresolvedImport]import org.apache.wicket.markup.html.basic.Label;
[CtUnresolvedImport]import org.sakaiproject.entity.api.Entity;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtUnresolvedImport]import org.apache.wicket.ajax.markup.html.AjaxLink;
[CtUnresolvedImport]import org.apache.wicket.model.Model;
[CtUnresolvedImport]import org.apache.wicket.Component;
[CtUnresolvedImport]import org.apache.wicket.AttributeModifier;
[CtUnresolvedImport]import org.sakaiproject.profile2.model.UserProfile;
[CtUnresolvedImport]import org.apache.wicket.markup.html.panel.Panel;
[CtUnresolvedImport]import lombok.extern.slf4j.Slf4j;
[CtUnresolvedImport]import org.apache.wicket.markup.html.WebMarkupContainer;
[CtUnresolvedImport]import org.apache.wicket.model.PropertyModel;
[CtUnresolvedImport]import org.apache.wicket.model.ResourceModel;
[CtUnresolvedImport]import org.sakaiproject.profile2.logic.ProfileLogic;
[CtUnresolvedImport]import org.sakaiproject.profile2.logic.ProfileWallLogic;
[CtUnresolvedImport]import org.apache.wicket.ajax.attributes.AjaxCallListener;
[CtClassImpl][CtAnnotationImpl]@lombok.extern.slf4j.Slf4j
public class MyNamePronunciationEdit extends [CtTypeReferenceImpl]org.apache.wicket.markup.html.panel.Panel {
    [CtFieldImpl][CtAnnotationImpl]@org.apache.wicket.spring.injection.annot.SpringBean(name = [CtLiteralImpl]"org.sakaiproject.profile2.logic.SakaiProxy")
    private [CtTypeReferenceImpl]org.sakaiproject.profile2.logic.SakaiProxy sakaiProxy;

    [CtFieldImpl][CtAnnotationImpl]@org.apache.wicket.spring.injection.annot.SpringBean(name = [CtLiteralImpl]"org.sakaiproject.profile2.logic.ProfileWallLogic")
    private [CtTypeReferenceImpl]org.sakaiproject.profile2.logic.ProfileWallLogic wallLogic;

    [CtFieldImpl][CtAnnotationImpl]@org.apache.wicket.spring.injection.annot.SpringBean(name = [CtLiteralImpl]"org.sakaiproject.profile2.logic.ProfileLogic")
    private [CtTypeReferenceImpl]org.sakaiproject.profile2.logic.ProfileLogic profileLogic;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.wicket.markup.html.form.HiddenField audioBase64;

    [CtConstructorImpl]public MyNamePronunciationEdit([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl]final [CtTypeReferenceImpl]org.sakaiproject.profile2.model.UserProfile userProfile) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]id);
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"MyNamePronunciationEdit()");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.wicket.Component thisPanel = [CtThisAccessImpl]this;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String userId = [CtInvocationImpl][CtVariableReadImpl]userProfile.getUserUuid();
        [CtInvocationImpl][CtCommentImpl]// heading
        add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"heading", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"heading.name.pronunciation.edit")));
        [CtLocalVariableImpl][CtCommentImpl]// setup form
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.form.Form form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.form.Form([CtLiteralImpl]"form", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.Model([CtVariableReadImpl]userProfile));
        [CtInvocationImpl][CtVariableReadImpl]form.setOutputMarkupId([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// form submit feedback
        final [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label formFeedback = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"formFeedback");
        [CtInvocationImpl][CtVariableReadImpl]formFeedback.setOutputMarkupPlaceholderTag([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]formFeedback);
        [CtLocalVariableImpl][CtCommentImpl]// add warning message if superUser and not editing own profile
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label editWarning = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"editWarning");
        [CtInvocationImpl][CtVariableReadImpl]editWarning.setVisible([CtLiteralImpl]false);
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]sakaiProxy.isSuperUserAndProxiedToUser([CtVariableReadImpl]userId)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]editWarning.setDefaultModel([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.StringResourceModel([CtLiteralImpl]"text.edit.other.warning", [CtLiteralImpl]null, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtInvocationImpl][CtVariableReadImpl]userProfile.getDisplayName() }));
            [CtInvocationImpl][CtVariableReadImpl]editWarning.setEscapeModelStrings([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]editWarning.setVisible([CtLiteralImpl]true);
        }
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]editWarning);
        [CtLocalVariableImpl][CtCommentImpl]// phoneticPronunciation
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer phoneticContainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer([CtLiteralImpl]"phoneticContainer");
        [CtInvocationImpl][CtVariableReadImpl]phoneticContainer.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"phoneticLabel", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.phonetic")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.form.TextField phonetic = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.form.TextField([CtLiteralImpl]"phoneticPronunciation", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.PropertyModel([CtVariableReadImpl]userProfile, [CtLiteralImpl]"phoneticPronunciation"));
        [CtInvocationImpl][CtVariableReadImpl]phonetic.setOutputMarkupId([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]phoneticContainer.add([CtVariableReadImpl]phonetic);
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]phoneticContainer);
        [CtLocalVariableImpl][CtCommentImpl]// pronunciationExamples
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer pronunciationExamples = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer([CtLiteralImpl]"pronunciationExamples");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String href = [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.getNamePronunciationExamplesLink();
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.isBlank([CtVariableReadImpl]href)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer examplesLink = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer([CtLiteralImpl]"examplesLink");
            [CtInvocationImpl][CtVariableReadImpl]examplesLink.setVisible([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]pronunciationExamples.add([CtVariableReadImpl]examplesLink);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.link.ExternalLink examplesLink = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.link.ExternalLink([CtLiteralImpl]"examplesLink", [CtVariableReadImpl]href, [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.phonetic.examples.link.label").getObject());
            [CtInvocationImpl][CtVariableReadImpl]examplesLink.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.AttributeModifier([CtLiteralImpl]"target", [CtLiteralImpl]"_blank"));
            [CtInvocationImpl][CtVariableReadImpl]pronunciationExamples.add([CtVariableReadImpl]examplesLink);
        }
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]pronunciationExamples);
        [CtLocalVariableImpl][CtCommentImpl]// nameRecording
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer nameRecordingContainer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer([CtLiteralImpl]"nameRecordingContainer");
        [CtInvocationImpl][CtVariableReadImpl]nameRecordingContainer.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"nameRecordingLabel", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.name.recording")));
        [CtAssignmentImpl][CtFieldWriteImpl]audioBase64 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.form.HiddenField([CtLiteralImpl]"audioBase64", [CtInvocationImpl][CtTypeAccessImpl]org.apache.wicket.model.Model.of());
        [CtInvocationImpl][CtVariableReadImpl]nameRecordingContainer.add([CtFieldReadImpl]audioBase64);
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]nameRecordingContainer);
        [CtLocalVariableImpl][CtCommentImpl]// audioPlayer
        [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer audioPlayer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.WebMarkupContainer([CtLiteralImpl]"audioPlayer");
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String slash = [CtFieldReadImpl]org.sakaiproject.entity.api.Entity.SEPARATOR;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.StringBuilder path = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtVariableReadImpl]slash);
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtLiteralImpl]"direct");
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtVariableReadImpl]slash);
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtLiteralImpl]"profile");
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtVariableReadImpl]slash);
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtInvocationImpl][CtVariableReadImpl]userProfile.getUserUuid());
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtVariableReadImpl]slash);
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtLiteralImpl]"pronunciation");
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtLiteralImpl]"?v=");
        [CtInvocationImpl][CtVariableReadImpl]path.append([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.RandomStringUtils.random([CtLiteralImpl]8, [CtLiteralImpl]true, [CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]audioPlayer.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.AttributeModifier([CtLiteralImpl]"src", [CtInvocationImpl][CtVariableReadImpl]path.toString()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]profileLogic.getUserNamePronunciation([CtInvocationImpl][CtVariableReadImpl]userProfile.getUserUuid()) == [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]audioPlayer.setVisible([CtLiteralImpl]false);

        [CtInvocationImpl][CtVariableReadImpl]nameRecordingContainer.add([CtVariableReadImpl]audioPlayer);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label namePronunciationDuration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"namePronunciationDuration", [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.getNamePronunciationDuration());
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]namePronunciationDuration);
        [CtLocalVariableImpl][CtCommentImpl]// Delete recording link
        [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.AjaxLink clearRecordingLink = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.AjaxLink([CtLiteralImpl]"clearExistingRecordingLink")[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.AjaxRequestTarget target) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userId = [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.getCurrentUserId();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtFieldReadImpl]profileLogic.getUserNamePronunciationResourceId([CtVariableReadImpl]userId);
                [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.removeResource([CtVariableReadImpl]path);
                [CtInvocationImpl][CtFieldReadImpl]log.info([CtLiteralImpl]"Pronunciation recording removed for the user {}. ", [CtVariableReadImpl]userId);
                [CtLocalVariableImpl][CtCommentImpl]// repaint panel
                [CtTypeReferenceImpl]org.apache.wicket.Component newPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.profile2.tool.pages.panels.MyNamePronunciationDisplay([CtVariableReadImpl]id, [CtVariableReadImpl]userProfile);
                [CtInvocationImpl][CtVariableReadImpl]newPanel.setOutputMarkupId([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]thisPanel.replaceWith([CtVariableReadImpl]newPanel);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]target.add([CtVariableReadImpl]newPanel);
                    [CtInvocationImpl][CtVariableReadImpl]target.appendJavaScript([CtLiteralImpl]"setMainFrameHeight(window.name);");
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void updateAjaxAttributes([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxRequestAttributes attributes) [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.updateAjaxAttributes([CtVariableReadImpl]attributes);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxCallListener ajaxCallListener = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxCallListener();
                [CtInvocationImpl][CtVariableReadImpl]ajaxCallListener.onPrecondition([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"return confirm('" + [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.phonetic.clear.confirmation").getObject()) + [CtLiteralImpl]"');");
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributes.getAjaxCallListeners().add([CtVariableReadImpl]ajaxCallListener);
            }
        };
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]profileLogic.getUserNamePronunciation([CtInvocationImpl][CtVariableReadImpl]userProfile.getUserUuid()) == [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtVariableReadImpl]clearRecordingLink.setVisible([CtLiteralImpl]false);

        [CtInvocationImpl][CtVariableReadImpl]clearRecordingLink.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.markup.html.basic.Label([CtLiteralImpl]"clearExistingRecordingLabel", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.phonetic.clear.recording.label")));
        [CtInvocationImpl][CtVariableReadImpl]clearRecordingLink.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.AttributeModifier([CtLiteralImpl]"title", [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"profile.phonetic.clear.recording.label")));
        [CtInvocationImpl][CtVariableReadImpl]nameRecordingContainer.add([CtVariableReadImpl]clearRecordingLink);
        [CtLocalVariableImpl][CtCommentImpl]// submit button
        [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton submitButton = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton([CtLiteralImpl]"submit", [CtVariableReadImpl]form)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void onSubmit([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.AjaxRequestTarget target, [CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.form.Form form) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]save([CtVariableReadImpl]form)) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// post update event
                    [CtFieldReadImpl]sakaiProxy.postEvent([CtTypeAccessImpl]ProfileConstants.EVENT_PROFILE_NAME_PRONUN_UPDATE, [CtBinaryOperatorImpl][CtLiteralImpl]"/profile/" + [CtVariableReadImpl]userId, [CtLiteralImpl]true);
                    [CtIfImpl][CtCommentImpl]// post to wall if enabled
                    if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]sakaiProxy.isWallEnabledGlobally() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]sakaiProxy.isSuperUserAndProxiedToUser([CtVariableReadImpl]userId))) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl]wallLogic.addNewEventToWall([CtTypeAccessImpl]ProfileConstants.EVENT_PROFILE_NAME_PRONUN_UPDATE, [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.getCurrentUserId());
                    }
                    [CtLocalVariableImpl][CtCommentImpl]// repaint panel
                    [CtTypeReferenceImpl]org.apache.wicket.Component newPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.profile2.tool.pages.panels.MyNamePronunciationDisplay([CtVariableReadImpl]id, [CtVariableReadImpl]userProfile);
                    [CtInvocationImpl][CtVariableReadImpl]newPanel.setOutputMarkupId([CtLiteralImpl]true);
                    [CtInvocationImpl][CtVariableReadImpl]thisPanel.replaceWith([CtVariableReadImpl]newPanel);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]target.add([CtVariableReadImpl]newPanel);
                        [CtInvocationImpl][CtVariableReadImpl]target.appendJavaScript([CtLiteralImpl]"setMainFrameHeight(window.name);");
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]formFeedback.setDefaultModel([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.ResourceModel([CtLiteralImpl]"error.profile.save.info.failed"));
                    [CtInvocationImpl][CtVariableReadImpl]formFeedback.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.AttributeModifier([CtLiteralImpl]"class", [CtLiteralImpl]true, [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.wicket.model.Model<>([CtLiteralImpl]"save-failed-error")));
                    [CtInvocationImpl][CtVariableReadImpl]target.add([CtVariableReadImpl]formFeedback);
                }
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void updateAjaxAttributes([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxRequestAttributes attributes) [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.updateAjaxAttributes([CtVariableReadImpl]attributes);
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxCallListener myAjaxCallListener = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.wicket.ajax.attributes.AjaxCallListener()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]java.lang.CharSequence getBeforeHandler([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.Component component) [CtBlockImpl]{
                        [CtReturnImpl]return [CtLiteralImpl]"doUpdateCK()";
                    }
                };
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributes.getAjaxCallListeners().add([CtVariableReadImpl]myAjaxCallListener);
            }
        };
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]submitButton);
        [CtLocalVariableImpl][CtCommentImpl]// cancel button
        [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton cancelButton = [CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton([CtLiteralImpl]"cancel", [CtVariableReadImpl]form)[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            protected [CtTypeReferenceImpl]void onSubmit([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.ajax.AjaxRequestTarget target, [CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.form.Form form) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.wicket.Component newPanel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.sakaiproject.profile2.tool.pages.panels.MyNamePronunciationDisplay([CtVariableReadImpl]id, [CtVariableReadImpl]userProfile);
                [CtInvocationImpl][CtVariableReadImpl]newPanel.setOutputMarkupId([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]thisPanel.replaceWith([CtVariableReadImpl]newPanel);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]target != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]target.add([CtVariableReadImpl]newPanel);
                    [CtInvocationImpl][CtVariableReadImpl]target.appendJavaScript([CtLiteralImpl]"setMainFrameHeight(window.name);");
                }
            }
        };
        [CtInvocationImpl][CtVariableReadImpl]cancelButton.setDefaultFormProcessing([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]form.add([CtVariableReadImpl]cancelButton);
        [CtInvocationImpl][CtCommentImpl]// add form to page
        add([CtVariableReadImpl]form);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean save([CtParameterImpl][CtTypeReferenceImpl]org.apache.wicket.markup.html.form.Form form) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get the backing model
        [CtTypeReferenceImpl]org.sakaiproject.profile2.model.UserProfile userProfile = [CtInvocationImpl](([CtTypeReferenceImpl]org.sakaiproject.profile2.model.UserProfile) ([CtVariableReadImpl]form.getModelObject()));
        [CtLocalVariableImpl][CtCommentImpl]// get userId from the UserProfile (because admin could be editing), then get existing SakaiPerson for that userId
        [CtTypeReferenceImpl]java.lang.String userId = [CtInvocationImpl][CtVariableReadImpl]userProfile.getUserUuid();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.sakaiproject.api.common.edu.person.SakaiPerson sakaiPerson = [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.getSakaiPerson([CtVariableReadImpl]userId);
        [CtInvocationImpl][CtVariableReadImpl]sakaiPerson.setPhoneticPronunciation([CtInvocationImpl][CtVariableReadImpl]userProfile.getPhoneticPronunciation());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]audioBase64.getDefaultModelObject() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String audioStr = [CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]audioBase64.getDefaultModelObject().toString().split([CtLiteralImpl]",")[[CtLiteralImpl]1];
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String path = [CtInvocationImpl][CtFieldReadImpl]profileLogic.getUserNamePronunciationResourceId([CtVariableReadImpl]userId);
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] bytes = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Base64.getDecoder().decode([CtInvocationImpl][CtVariableReadImpl]audioStr.getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8));
            [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.removeResource([CtVariableReadImpl]path);
            [CtInvocationImpl][CtFieldReadImpl]sakaiProxy.saveFile([CtVariableReadImpl]path, [CtVariableReadImpl]userId, [CtBinaryOperatorImpl][CtVariableReadImpl]userId + [CtLiteralImpl]".wav", [CtLiteralImpl]"audio/wav", [CtVariableReadImpl]bytes);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]profileLogic.saveUserProfile([CtVariableReadImpl]sakaiPerson)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Saved SakaiPerson for: " + [CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtLiteralImpl]true;
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Couldn't save SakaiPerson for: " + [CtVariableReadImpl]userId);
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }
}
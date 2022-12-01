[CompilationUnitImpl][CtPackageDeclarationImpl]package org.jellyfin.androidtv.ui;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import org.jellyfin.apiclient.interaction.Response;
[CtUnresolvedImport]import android.widget.LinearLayout;
[CtUnresolvedImport]import org.jellyfin.androidtv.ui.livetv.ILiveTvGuide;
[CtUnresolvedImport]import org.jellyfin.androidtv.TvApp;
[CtUnresolvedImport]import android.graphics.drawable.ColorDrawable;
[CtUnresolvedImport]import static org.koin.java.KoinJavaComponent.inject;
[CtUnresolvedImport]import org.jellyfin.apiclient.model.dto.UserItemDataDto;
[CtUnresolvedImport]import org.jellyfin.apiclient.model.dto.BaseItemDto;
[CtUnresolvedImport]import org.jellyfin.androidtv.util.InfoLayoutHelper;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import android.widget.Button;
[CtUnresolvedImport]import org.jellyfin.apiclient.model.livetv.ChannelInfoDto;
[CtUnresolvedImport]import kotlin.Lazy;
[CtUnresolvedImport]import android.content.DialogInterface;
[CtUnresolvedImport]import org.jellyfin.androidtv.ui.shared.BaseActivity;
[CtUnresolvedImport]import org.jellyfin.apiclient.interaction.EmptyResponse;
[CtUnresolvedImport]import org.jellyfin.androidtv.util.Utils;
[CtUnresolvedImport]import org.jellyfin.androidtv.R;
[CtUnresolvedImport]import android.view.LayoutInflater;
[CtUnresolvedImport]import org.jellyfin.androidtv.util.TimeUtils;
[CtUnresolvedImport]import android.widget.PopupWindow;
[CtUnresolvedImport]import org.jellyfin.androidtv.ui.livetv.TvManager;
[CtUnresolvedImport]import timber.log.Timber;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import android.app.AlertDialog;
[CtUnresolvedImport]import android.graphics.Color;
[CtUnresolvedImport]import android.view.Gravity;
[CtUnresolvedImport]import android.widget.TextView;
[CtUnresolvedImport]import org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto;
[CtUnresolvedImport]import org.jellyfin.apiclient.interaction.ApiClient;
[CtClassImpl]public class LiveProgramDetailPopup {
    [CtFieldImpl]private final [CtTypeReferenceImpl]int NORMAL_HEIGHT = [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.convertDpToPixel([CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication(), [CtLiteralImpl]400);

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.PopupWindow mPopup;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto mProgram;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.ProgramGridCell mSelectedProgramView;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.shared.BaseActivity mActivity;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.livetv.ILiveTvGuide mTvGuide;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mDTitle;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mDSummary;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mDRecordInfo;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout mDTimeline;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout mDInfoRow;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout mDButtonRow;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.LinearLayout mDSimilarRow;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.Button mFirstButton;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.Button mSeriesSettingsButton;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse mTuneAction;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.view.View mAnchor;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mPosLeft;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mPosTop;

    [CtFieldImpl]private [CtTypeReferenceImpl]kotlin.Lazy<[CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.ApiClient> apiClient = [CtInvocationImpl]inject([CtFieldReadImpl]org.jellyfin.apiclient.interaction.ApiClient.class);

    [CtConstructorImpl]public LiveProgramDetailPopup([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.androidtv.ui.shared.BaseActivity activity, [CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.androidtv.ui.livetv.ILiveTvGuide tvGuide, [CtParameterImpl][CtTypeReferenceImpl]int width, [CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse tuneAction) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mActivity = [CtVariableReadImpl]activity;
        [CtAssignmentImpl][CtFieldWriteImpl]mTvGuide = [CtVariableReadImpl]tvGuide;
        [CtAssignmentImpl][CtFieldWriteImpl]mTuneAction = [CtVariableReadImpl]tuneAction;
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.LayoutInflater inflater = [CtInvocationImpl](([CtTypeReferenceImpl]android.view.LayoutInflater) ([CtFieldReadImpl]mActivity.getSystemService([CtTypeAccessImpl]Context.LAYOUT_INFLATER_SERVICE)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View layout = [CtInvocationImpl][CtVariableReadImpl]inflater.inflate([CtTypeAccessImpl]R.layout.program_detail_popup, [CtLiteralImpl]null);
        [CtAssignmentImpl][CtFieldWriteImpl]mPopup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.PopupWindow([CtVariableReadImpl]layout, [CtVariableReadImpl]width, [CtFieldReadImpl]NORMAL_HEIGHT);
        [CtInvocationImpl][CtFieldReadImpl]mPopup.setFocusable([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]mPopup.setOutsideTouchable([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]mPopup.setBackgroundDrawable([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.graphics.drawable.ColorDrawable([CtFieldReadImpl]android.graphics.Color.TRANSPARENT));[CtCommentImpl]// necessary for popup to dismiss

        [CtInvocationImpl][CtFieldReadImpl]mPopup.setAnimationStyle([CtTypeAccessImpl]R.style.PopupSlideInTop);
        [CtAssignmentImpl][CtFieldWriteImpl]mDTitle = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.title);
        [CtAssignmentImpl][CtFieldWriteImpl]mDSummary = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.summary);
        [CtAssignmentImpl][CtFieldWriteImpl]mDRecordInfo = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.recordLine);
        [CtAssignmentImpl][CtFieldWriteImpl]mDTimeline = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.timeline);
        [CtAssignmentImpl][CtFieldWriteImpl]mDButtonRow = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.buttonRow);
        [CtAssignmentImpl][CtFieldWriteImpl]mDInfoRow = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.infoRow);
        [CtAssignmentImpl][CtFieldWriteImpl]mDSimilarRow = [CtInvocationImpl][CtVariableReadImpl]layout.findViewById([CtTypeAccessImpl]R.id.similarRow);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isShowing() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mPopup != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]mPopup.isShowing();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setContent([CtParameterImpl]final [CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto program, [CtParameterImpl]final [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.ProgramGridCell selectedGridView) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mProgram = [CtVariableReadImpl]program;
        [CtAssignmentImpl][CtFieldWriteImpl]mSelectedProgramView = [CtVariableReadImpl]selectedGridView;
        [CtInvocationImpl][CtFieldReadImpl]mDTitle.setText([CtInvocationImpl][CtVariableReadImpl]program.getName());
        [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.removeAllViews();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]program.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// empty item, just offer tune button
            [CtFieldWriteImpl]mFirstButton = [CtInvocationImpl]createTuneButton();
            [CtInvocationImpl]createFavoriteButton();
            [CtInvocationImpl][CtFieldReadImpl]mDInfoRow.removeAllViews();
            [CtInvocationImpl][CtFieldReadImpl]mDTimeline.removeAllViews();
            [CtInvocationImpl][CtFieldReadImpl]mDSummary.setText([CtLiteralImpl]"");
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]mDSummary.setText([CtInvocationImpl][CtVariableReadImpl]program.getOverview());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mDSummary.getLineCount() < [CtLiteralImpl]2) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mDSummary.setGravity([CtTypeAccessImpl]Gravity.CENTER);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mDSummary.setGravity([CtTypeAccessImpl]Gravity.LEFT);
        }
        [CtInvocationImpl][CtCommentImpl]// TvApp.getApplication().getLogger().Debug("Text height: "+mDSummary.getHeight() + " (120 = "+Utils.convertDpToPixel(mActivity, 120)+")");
        [CtCommentImpl]// build timeline info
        [CtTypeAccessImpl]org.jellyfin.androidtv.ui.livetv.TvManager.setTimelineRow([CtFieldReadImpl]mActivity, [CtFieldReadImpl]mDTimeline, [CtVariableReadImpl]program);
        [CtInvocationImpl][CtCommentImpl]// info row
        [CtTypeAccessImpl]org.jellyfin.androidtv.util.InfoLayoutHelper.addInfoRow([CtFieldReadImpl]mActivity, [CtVariableReadImpl]program, [CtFieldReadImpl]mDInfoRow, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// buttons
        [CtFieldWriteImpl]mFirstButton = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date now = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date local = [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.TimeUtils.convertToLocalDate([CtInvocationImpl][CtVariableReadImpl]program.getStartDate());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.TimeUtils.convertToLocalDate([CtInvocationImpl][CtVariableReadImpl]program.getEndDate()).getTime() > [CtInvocationImpl][CtVariableReadImpl]now.getTime()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]local.getTime() <= [CtInvocationImpl][CtVariableReadImpl]now.getTime()) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// program in progress - tune first button
                [CtFieldWriteImpl]mFirstButton = [CtInvocationImpl]createTuneButton();
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication().canManageRecordings()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]program.getTimerId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// cancel button
                    [CtTypeReferenceImpl]android.widget.Button cancel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
                    [CtInvocationImpl][CtVariableReadImpl]cancel.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_cancel_recording));
                    [CtInvocationImpl][CtVariableReadImpl]cancel.setTextColor([CtTypeAccessImpl]Color.WHITE);
                    [CtInvocationImpl][CtVariableReadImpl]cancel.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
                    [CtInvocationImpl][CtVariableReadImpl]cancel.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().CancelLiveTvTimerAsync([CtInvocationImpl][CtVariableReadImpl]program.getTimerId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse()[CtClassImpl] {
                                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                public [CtTypeReferenceImpl]void onResponse() [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]selectedGridView.setRecTimer([CtLiteralImpl]null);
                                    [CtInvocationImpl][CtVariableReadImpl]program.setTimerId([CtLiteralImpl]null);
                                    [CtInvocationImpl]dismiss();
                                    [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_recording_cancelled);
                                }

                                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_cancel);
                                }
                            });
                        }
                    });
                    [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.addView([CtVariableReadImpl]cancel);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mFirstButton == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtFieldWriteImpl]mFirstButton = [CtVariableReadImpl]cancel;

                    [CtInvocationImpl][CtCommentImpl]// recording info
                    [CtFieldReadImpl]mDRecordInfo.setText([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]local.getTime() <= [CtInvocationImpl][CtVariableReadImpl]now.getTime() ? [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.msg_recording_now) : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.msg_will_record));
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtCommentImpl]// record button
                    [CtTypeReferenceImpl]android.widget.Button rec = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
                    [CtInvocationImpl][CtVariableReadImpl]rec.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_record));
                    [CtInvocationImpl][CtVariableReadImpl]rec.setTextColor([CtTypeAccessImpl]Color.WHITE);
                    [CtInvocationImpl][CtVariableReadImpl]rec.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
                    [CtInvocationImpl][CtVariableReadImpl]rec.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// Create one-off recording with defaults
                            [CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().GetDefaultLiveTvTimerInfo([CtInvocationImpl][CtFieldReadImpl]mProgram.getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto>()[CtClassImpl] {
                                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto response) [CtBlockImpl]{
                                    [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().CreateLiveTvTimerAsync([CtVariableReadImpl]response, [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse()[CtClassImpl] {
                                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                        public [CtTypeReferenceImpl]void onResponse() [CtBlockImpl]{
                                            [CtInvocationImpl][CtCommentImpl]// we have to re-retrieve the program to get the timer id
                                            [CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().GetLiveTvProgramAsync([CtInvocationImpl][CtFieldReadImpl]mProgram.getId(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication().getCurrentUser().getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto>()[CtClassImpl] {
                                                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                                public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto response) [CtBlockImpl]{
                                                    [CtAssignmentImpl][CtFieldWriteImpl]mProgram = [CtVariableReadImpl]response;
                                                    [CtInvocationImpl][CtFieldReadImpl]mSelectedProgramView.setRecSeriesTimer([CtInvocationImpl][CtVariableReadImpl]response.getSeriesTimerId());
                                                    [CtInvocationImpl][CtFieldReadImpl]mSelectedProgramView.setRecTimer([CtInvocationImpl][CtVariableReadImpl]response.getTimerId());
                                                    [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_set_to_record);
                                                    [CtInvocationImpl]dismiss();
                                                }
                                            });
                                        }

                                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                        public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                                            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]ex, [CtLiteralImpl]"Error creating recording");
                                            [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_create_recording);
                                        }
                                    });
                                }

                                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception exception) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]exception, [CtLiteralImpl]"Error creating recording");
                                    [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_create_recording);
                                }
                            });
                        }
                    });
                    [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.addView([CtVariableReadImpl]rec);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mFirstButton == [CtLiteralImpl]null)[CtBlockImpl]
                        [CtAssignmentImpl][CtFieldWriteImpl]mFirstButton = [CtVariableReadImpl]rec;

                    [CtInvocationImpl][CtFieldReadImpl]mDRecordInfo.setText([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]program.getSeriesTimerId() == [CtLiteralImpl]null ? [CtLiteralImpl]"" : [CtInvocationImpl][CtFieldReadImpl]mActivity.getString([CtTypeAccessImpl]R.string.lbl_episode_not_record));
                }
                [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.isTrue([CtInvocationImpl][CtVariableReadImpl]program.getIsSeries())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]program.getSeriesTimerId() != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// cancel series button
                        [CtTypeReferenceImpl]android.widget.Button cancel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
                        [CtInvocationImpl][CtVariableReadImpl]cancel.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_cancel_series));
                        [CtInvocationImpl][CtVariableReadImpl]cancel.setTextColor([CtTypeAccessImpl]Color.WHITE);
                        [CtInvocationImpl][CtVariableReadImpl]cancel.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
                        [CtInvocationImpl][CtVariableReadImpl]cancel.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.app.AlertDialog.Builder([CtFieldReadImpl]mActivity).setTitle([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_cancel_series)).setMessage([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.msg_cancel_entire_series)).setNegativeButton([CtTypeAccessImpl]R.string.lbl_no, [CtLiteralImpl]null).setPositiveButton([CtTypeAccessImpl]R.string.lbl_yes, [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.content.DialogInterface.OnClickListener()[CtClassImpl] {
                                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                    public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.content.DialogInterface dialog, [CtParameterImpl][CtTypeReferenceImpl]int which) [CtBlockImpl]{
                                        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().CancelLiveTvSeriesTimerAsync([CtInvocationImpl][CtVariableReadImpl]program.getSeriesTimerId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse()[CtClassImpl] {
                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]void onResponse() [CtBlockImpl]{
                                                [CtInvocationImpl][CtVariableReadImpl]selectedGridView.setRecSeriesTimer([CtLiteralImpl]null);
                                                [CtInvocationImpl][CtVariableReadImpl]program.setSeriesTimerId([CtLiteralImpl]null);
                                                [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setVisibility([CtTypeAccessImpl]View.GONE);
                                                [CtInvocationImpl]dismiss();
                                                [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_recording_cancelled);
                                            }

                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                                                [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_cancel);
                                            }
                                        });
                                    }
                                }).show();
                            }
                        });
                        [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.addView([CtVariableReadImpl]cancel);
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// record series button
                        [CtTypeReferenceImpl]android.widget.Button rec = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
                        [CtInvocationImpl][CtVariableReadImpl]rec.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_record_series));
                        [CtInvocationImpl][CtVariableReadImpl]rec.setTextColor([CtTypeAccessImpl]Color.WHITE);
                        [CtInvocationImpl][CtVariableReadImpl]rec.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
                        [CtInvocationImpl][CtVariableReadImpl]rec.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// Create series recording with defaults
                                [CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().GetDefaultLiveTvTimerInfo([CtInvocationImpl][CtFieldReadImpl]mProgram.getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto>()[CtClassImpl] {
                                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                    public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto response) [CtBlockImpl]{
                                        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().CreateLiveTvSeriesTimerAsync([CtVariableReadImpl]response, [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.EmptyResponse()[CtClassImpl] {
                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]void onResponse() [CtBlockImpl]{
                                                [CtInvocationImpl][CtCommentImpl]// we have to re-retrieve the program to get the timer id
                                                [CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().GetLiveTvProgramAsync([CtInvocationImpl][CtFieldReadImpl]mProgram.getId(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication().getCurrentUser().getId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto>()[CtClassImpl] {
                                                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                                    public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.BaseItemDto response) [CtBlockImpl]{
                                                        [CtAssignmentImpl][CtFieldWriteImpl]mProgram = [CtVariableReadImpl]response;
                                                        [CtInvocationImpl][CtFieldReadImpl]mSelectedProgramView.setRecSeriesTimer([CtInvocationImpl][CtVariableReadImpl]response.getSeriesTimerId());
                                                        [CtInvocationImpl][CtFieldReadImpl]mSelectedProgramView.setRecTimer([CtInvocationImpl][CtVariableReadImpl]response.getTimerId());
                                                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mSeriesSettingsButton != [CtLiteralImpl]null)[CtBlockImpl]
                                                            [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setVisibility([CtTypeAccessImpl]View.VISIBLE);

                                                        [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_set_to_record);
                                                        [CtInvocationImpl]dismiss();
                                                    }
                                                });
                                            }

                                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                            public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
                                                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]ex, [CtLiteralImpl]"Error creating recording");
                                                [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_create_recording);
                                            }
                                        });
                                    }

                                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                                    public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception exception) [CtBlockImpl]{
                                        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]exception, [CtLiteralImpl]"Error creating recording");
                                        [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.util.Utils.showToast([CtFieldReadImpl]mActivity, [CtTypeAccessImpl]R.string.msg_unable_to_create_recording);
                                    }
                                });
                            }
                        });
                        [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.addView([CtVariableReadImpl]rec);
                    }
                    [CtAssignmentImpl][CtCommentImpl]// manage series button
                    [CtFieldWriteImpl]mSeriesSettingsButton = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
                    [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_series_settings));
                    [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setTextColor([CtTypeAccessImpl]Color.WHITE);
                    [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
                    [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setVisibility([CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mProgram.getSeriesTimerId() != [CtLiteralImpl]null ? [CtFieldReadImpl]android.view.View.VISIBLE : [CtFieldReadImpl]android.view.View.GONE);
                    [CtInvocationImpl][CtFieldReadImpl]mSeriesSettingsButton.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                            [CtInvocationImpl]showRecordingOptions([CtLiteralImpl]true);
                        }
                    });
                    [CtInvocationImpl][CtFieldReadImpl]mDButtonRow.addView([CtFieldReadImpl]mSeriesSettingsButton);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]local.getTime() > [CtInvocationImpl][CtVariableReadImpl]now.getTime()) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// add tune to button for programs that haven't started yet
                createTuneButton();
            }
            [CtInvocationImpl]createFavoriteButton();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// program has already ended
            [CtFieldReadImpl]mDRecordInfo.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtTypeAccessImpl]R.string.lbl_program_ended));
            [CtAssignmentImpl][CtFieldWriteImpl]mFirstButton = [CtInvocationImpl]createTuneButton();
        }
        [CtInvocationImpl][CtCommentImpl]// if (program.getIsMovie()) {
        [CtCommentImpl]// mDSimilarRow.setVisibility(View.VISIBLE);
        [CtCommentImpl]// mPopup.setHeight(MOVIE_HEIGHT);
        [CtCommentImpl]// } else {
        [CtFieldReadImpl]mDSimilarRow.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtCommentImpl]// mPopup.setHeight(NORMAL_HEIGHT);
        [CtCommentImpl]// 
        [CtCommentImpl]// }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]android.widget.Button createTuneButton() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button tune = [CtInvocationImpl]addButton([CtFieldReadImpl]mDButtonRow, [CtTypeAccessImpl]R.string.lbl_tune_to_channel);
        [CtInvocationImpl][CtVariableReadImpl]tune.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mTuneAction != [CtLiteralImpl]null)[CtBlockImpl]
                    [CtInvocationImpl][CtFieldReadImpl]mTuneAction.onResponse();

                [CtInvocationImpl][CtFieldReadImpl]mPopup.dismiss();
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]tune;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]android.widget.ImageButton createFavoriteButton() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.ChannelInfoDto channel = [CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.ui.livetv.TvManager.getChannel([CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.ui.livetv.TvManager.getAllChannelsIndex([CtInvocationImpl][CtFieldReadImpl]mProgram.getChannelId()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isFav = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]channel.getUserData() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]channel.getUserData().getIsFavorite();
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.ImageButton fave = [CtInvocationImpl]addImgButton([CtFieldReadImpl]mDButtonRow, [CtConditionalImpl][CtVariableReadImpl]isFav ? [CtFieldReadImpl]R.drawable.ic_heart_red : [CtFieldReadImpl]R.drawable.ic_heart);
        [CtInvocationImpl][CtVariableReadImpl]fave.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().UpdateFavoriteStatusAsync([CtInvocationImpl][CtVariableReadImpl]channel.getId(), [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication().getCurrentUser().getId(), [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]channel.getUserData().getIsFavorite(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.UserItemDataDto>()[CtClassImpl] {
                    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                    public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.dto.UserItemDataDto response) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]channel.setUserData([CtVariableReadImpl]response);
                        [CtInvocationImpl][CtVariableReadImpl]fave.setImageDrawable([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getDrawable([CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]response.getIsFavorite() ? [CtFieldReadImpl]R.drawable.ic_heart_red : [CtFieldReadImpl]R.drawable.ic_heart));
                        [CtInvocationImpl][CtFieldReadImpl]mTvGuide.refreshFavorite([CtInvocationImpl][CtVariableReadImpl]channel.getId());
                        [CtInvocationImpl][CtFieldReadImpl][CtInvocationImpl][CtTypeAccessImpl]org.jellyfin.androidtv.TvApp.getApplication().dataRefreshService.setLastFavoriteUpdate([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis());
                    }
                });
            }
        });
        [CtReturnImpl]return [CtVariableReadImpl]fave;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]android.widget.ImageButton addImgButton([CtParameterImpl][CtTypeReferenceImpl]android.widget.LinearLayout layout, [CtParameterImpl][CtTypeReferenceImpl]int imgResource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.ImageButton btn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.ImageButton([CtFieldReadImpl]mActivity);
        [CtInvocationImpl][CtVariableReadImpl]btn.setImageDrawable([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getDrawable([CtVariableReadImpl]imgResource));
        [CtInvocationImpl][CtVariableReadImpl]btn.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
        [CtInvocationImpl][CtVariableReadImpl]layout.addView([CtVariableReadImpl]btn);
        [CtReturnImpl]return [CtVariableReadImpl]btn;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]android.widget.Button addButton([CtParameterImpl][CtTypeReferenceImpl]android.widget.LinearLayout layout, [CtParameterImpl][CtTypeReferenceImpl]int stringResource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button btn = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.widget.Button([CtFieldReadImpl]mActivity);
        [CtInvocationImpl][CtVariableReadImpl]btn.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mActivity.getResources().getString([CtVariableReadImpl]stringResource));
        [CtInvocationImpl][CtVariableReadImpl]btn.setTextColor([CtTypeAccessImpl]Color.WHITE);
        [CtInvocationImpl][CtVariableReadImpl]btn.setBackgroundResource([CtTypeAccessImpl]R.drawable.jellyfin_button);
        [CtInvocationImpl][CtVariableReadImpl]layout.addView([CtVariableReadImpl]btn);
        [CtReturnImpl]return [CtVariableReadImpl]btn;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void show([CtParameterImpl][CtTypeReferenceImpl]android.view.View anchor, [CtParameterImpl][CtTypeReferenceImpl]int x, [CtParameterImpl][CtTypeReferenceImpl]int y) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mAnchor = [CtVariableReadImpl]anchor;
        [CtAssignmentImpl][CtFieldWriteImpl]mPosLeft = [CtVariableReadImpl]x;
        [CtAssignmentImpl][CtFieldWriteImpl]mPosTop = [CtVariableReadImpl]y;
        [CtInvocationImpl][CtFieldReadImpl]mPopup.showAtLocation([CtVariableReadImpl]anchor, [CtTypeAccessImpl]Gravity.NO_GRAVITY, [CtVariableReadImpl]x, [CtVariableReadImpl]y);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mFirstButton != [CtLiteralImpl]null)[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]mFirstButton.requestFocus();

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void dismiss() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mRecordPopup != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]mRecordPopup.isShowing())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]mRecordPopup.dismiss();

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mPopup != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]mPopup.isShowing())[CtBlockImpl]
            [CtInvocationImpl][CtFieldReadImpl]mPopup.dismiss();

    }

    [CtFieldImpl]private [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.RecordPopup mRecordPopup;

    [CtMethodImpl]public [CtTypeReferenceImpl]void showRecordingOptions([CtParameterImpl]final [CtTypeReferenceImpl]boolean recordSeries) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mRecordPopup == [CtLiteralImpl]null)[CtBlockImpl]
            [CtAssignmentImpl][CtFieldWriteImpl]mRecordPopup = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jellyfin.androidtv.ui.RecordPopup([CtFieldReadImpl]mActivity, [CtFieldReadImpl]mAnchor, [CtFieldReadImpl]mPosLeft, [CtFieldReadImpl]mPosTop, [CtInvocationImpl][CtFieldReadImpl]mPopup.getWidth());

        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]apiClient.getValue().GetLiveTvSeriesTimerAsync([CtInvocationImpl][CtFieldReadImpl]mProgram.getSeriesTimerId(), [CtNewClassImpl]new [CtTypeReferenceImpl]org.jellyfin.apiclient.interaction.Response<[CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onResponse([CtParameterImpl][CtTypeReferenceImpl]org.jellyfin.apiclient.model.livetv.SeriesTimerInfoDto response) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mRecordPopup.setContent([CtFieldReadImpl]mProgram, [CtVariableReadImpl]response, [CtFieldReadImpl]mSelectedProgramView, [CtVariableReadImpl]recordSeries);
                [CtInvocationImpl][CtFieldReadImpl]mRecordPopup.show();
            }
        });
    }
}
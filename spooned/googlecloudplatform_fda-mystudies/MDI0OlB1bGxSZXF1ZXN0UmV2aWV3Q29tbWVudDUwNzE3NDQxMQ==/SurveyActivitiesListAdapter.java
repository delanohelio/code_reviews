[CompilationUnitImpl][CtCommentImpl]/* Copyright © 2017-2019 Harvard Pilgrim Health Care Institute (HPHCI) and its Contributors.
Copyright 2020 Google LLC
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

Funding Source: Food and Drug Administration (“Funding Agency”) effective 18 September 2014 as Contract no. HHSF22320140030I/HHSF22301006T (the “Prime Contract”).

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
[CtPackageDeclarationImpl]package com.harvard.studyappmodule;
[CtUnresolvedImport]import com.harvard.studyappmodule.surveyscheduler.model.ActivityStatus;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.support.v7.widget.AppCompatImageView;
[CtUnresolvedImport]import com.harvard.utils.AppController;
[CtUnresolvedImport]import android.view.LayoutInflater;
[CtUnresolvedImport]import com.harvard.utils.Logger;
[CtImportImpl]import java.util.Calendar;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import com.harvard.studyappmodule.activitylistmodel.ActivitiesWS;
[CtUnresolvedImport]import com.harvard.studyappmodule.surveyscheduler.SurveyScheduler;
[CtUnresolvedImport]import android.support.v7.widget.AppCompatTextView;
[CtUnresolvedImport]import android.view.ViewGroup;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import android.support.v7.widget.RecyclerView;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import android.widget.TextView;
[CtUnresolvedImport]import android.graphics.drawable.GradientDrawable;
[CtUnresolvedImport]import android.os.Handler;
[CtUnresolvedImport]import android.text.Html;
[CtUnresolvedImport]import android.widget.Toast;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import android.widget.RelativeLayout;
[CtImportImpl]import java.text.ParseException;
[CtUnresolvedImport]import com.harvard.R;
[CtClassImpl]public class SurveyActivitiesListAdapter extends [CtTypeReferenceImpl][CtTypeReferenceImpl]android.support.v7.widget.RecyclerView.Adapter<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.Holder> implements [CtTypeReferenceImpl][CtTypeReferenceImpl]com.harvard.studyappmodule.CustomActivitiesDailyDialogClass.DialogClick {
    [CtFieldImpl]private final [CtTypeReferenceImpl]android.content.Context context;

    [CtFieldImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.harvard.studyappmodule.activitylistmodel.ActivitiesWS> items;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesFragment surveyActivitiesFragment;

    [CtFieldImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> status;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TEXT_EVERY = [CtLiteralImpl]" every ";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TEXT_EVERY_MONTH = [CtLiteralImpl]" each month";

    [CtFieldImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.harvard.studyappmodule.surveyscheduler.model.ActivityStatus> currentRunStatusForActivities;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean click = [CtLiteralImpl]true;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean paused;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date joiningDate;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Integer> timePos = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtConstructorImpl]SurveyActivitiesListAdapter([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.harvard.studyappmodule.activitylistmodel.ActivitiesWS> items, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> status, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.harvard.studyappmodule.surveyscheduler.model.ActivityStatus> currentRunStatusForActivities, [CtParameterImpl][CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesFragment surveyActivitiesFragment, [CtParameterImpl][CtTypeReferenceImpl]boolean paused, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date joiningDate) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.items = [CtVariableReadImpl]items;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.status = [CtVariableReadImpl]status;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.surveyActivitiesFragment = [CtVariableReadImpl]surveyActivitiesFragment;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentRunStatusForActivities = [CtVariableReadImpl]currentRunStatusForActivities;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.paused = [CtVariableReadImpl]paused;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.joiningDate = [CtVariableReadImpl]joiningDate;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.Holder onCreateViewHolder([CtParameterImpl][CtTypeReferenceImpl]android.view.ViewGroup parent, [CtParameterImpl][CtTypeReferenceImpl]int viewType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View v = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.view.LayoutInflater.from([CtInvocationImpl][CtVariableReadImpl]parent.getContext()).inflate([CtTypeAccessImpl]R.layout.survey_activities_list_item, [CtVariableReadImpl]parent, [CtLiteralImpl]false);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.Holder([CtVariableReadImpl]v);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getItemCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]items.size();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void clicked([CtParameterImpl][CtTypeReferenceImpl]int positon) [CtBlockImpl]{
    }

    [CtClassImpl]class Holder extends [CtTypeReferenceImpl][CtTypeReferenceImpl]android.support.v7.widget.RecyclerView.ViewHolder {
        [CtFieldImpl]final [CtTypeReferenceImpl]android.widget.RelativeLayout stateLayout;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView state;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.widget.RelativeLayout container;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatImageView surveyIcon;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView whenWasSurvey;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView surveyTitle;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView time;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView date;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView process;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView run;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.view.View hrLine1;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.widget.RelativeLayout container2;

        [CtFieldImpl]final [CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView more;

        [CtConstructorImpl]Holder([CtParameterImpl][CtTypeReferenceImpl]android.view.View itemView) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]itemView);
            [CtAssignmentImpl][CtFieldWriteImpl]stateLayout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.RelativeLayout) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.stateLayout)));
            [CtAssignmentImpl][CtFieldWriteImpl]state = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.state)));
            [CtAssignmentImpl][CtFieldWriteImpl]run = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.run)));
            [CtAssignmentImpl][CtFieldWriteImpl]container = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.RelativeLayout) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.container)));
            [CtAssignmentImpl][CtFieldWriteImpl]surveyIcon = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatImageView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.surveyIcon)));
            [CtAssignmentImpl][CtFieldWriteImpl]whenWasSurvey = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.whenWasSurvey)));
            [CtAssignmentImpl][CtFieldWriteImpl]surveyTitle = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.surveyTitle)));
            [CtAssignmentImpl][CtFieldWriteImpl]time = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.time)));
            [CtAssignmentImpl][CtFieldWriteImpl]date = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.date)));
            [CtAssignmentImpl][CtFieldWriteImpl]process = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.process)));
            [CtAssignmentImpl][CtFieldWriteImpl]hrLine1 = [CtInvocationImpl][CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.hrLine1);
            [CtAssignmentImpl][CtFieldWriteImpl]container2 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.RelativeLayout) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.container2)));
            [CtAssignmentImpl][CtFieldWriteImpl]more = [CtInvocationImpl](([CtTypeReferenceImpl]android.support.v7.widget.AppCompatTextView) ([CtVariableReadImpl]itemView.findViewById([CtTypeAccessImpl]R.id.more)));
            [CtInvocationImpl]setFont();
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void setFont() [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]state.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"medium"));
                [CtInvocationImpl][CtFieldReadImpl]whenWasSurvey.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"bold"));
                [CtInvocationImpl][CtFieldReadImpl]surveyTitle.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"bold"));
                [CtInvocationImpl][CtFieldReadImpl]time.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"regular"));
                [CtInvocationImpl][CtFieldReadImpl]date.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"regular"));
                [CtInvocationImpl][CtFieldReadImpl]process.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"medium"));
                [CtInvocationImpl][CtFieldReadImpl]run.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"medium"));
                [CtInvocationImpl][CtFieldReadImpl]more.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getTypeface([CtFieldReadImpl]context, [CtLiteralImpl]"medium"));
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
            }
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onBindViewHolder([CtParameterImpl]final [CtTypeReferenceImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.Holder holder, [CtParameterImpl]final [CtTypeReferenceImpl]int position) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> mScheduledTime = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtFieldReadImpl]timePos.add([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.graphics.drawable.GradientDrawable bgShape = [CtInvocationImpl](([CtTypeReferenceImpl]android.graphics.drawable.GradientDrawable) ([CtFieldReadImpl][CtVariableReadImpl]holder.process.getBackground()));
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_CURRENT)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.state.setText([CtTypeAccessImpl]R.string.current1);
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_UPCOMING)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.state.setText([CtTypeAccessImpl]R.string.upcoming1);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.state.setText([CtTypeAccessImpl]R.string.past);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition() == [CtLiteralImpl]0) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtInvocationImpl][CtFieldReadImpl]status.get([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition() - [CtLiteralImpl]1)))) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.stateLayout.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.stateLayout.setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getActivityId().equalsIgnoreCase([CtLiteralImpl]"") || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getActivityId().equalsIgnoreCase([CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.container2.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.container.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.hrLine1.setVisibility([CtTypeAccessImpl]View.GONE);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.container2.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.container.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.hrLine1.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_UPCOMING) || [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_COMPLETED)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.YET_To_START)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.start);
                [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.colorPrimary));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.IN_PROGRESS)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.resume);
                [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.rectangle_yellow));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.COMPLETED)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.completed2);
                [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.bullet_green_color));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.INCOMPLETE)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.incompleted2);
                [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.red));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getType().equalsIgnoreCase([CtLiteralImpl]"questionnaire")) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.surveyIcon.setImageResource([CtTypeAccessImpl]R.drawable.survey_icn_small);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getType().equalsIgnoreCase([CtLiteralImpl]"task")) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.surveyIcon.setImageResource([CtTypeAccessImpl]R.drawable.task_icn_small);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.surveyIcon.setVisibility([CtTypeAccessImpl]View.INVISIBLE);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_UPCOMING)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.run.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCurrentRunId() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.GONE);
                }
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.run.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.run.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.run) + [CtLiteralImpl]": ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCurrentRunId()) + [CtLiteralImpl]"/") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getTotalRun()) + [CtLiteralImpl]", ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCompletedRun()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.done2)) + [CtLiteralImpl]", ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getMissedRun()) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.missed));
            }
            [CtIfImpl][CtCommentImpl]// completed status incomplete/complete settings
            if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_COMPLETED)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int missedRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getMissedRun();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int currentRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCurrentRunId();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int totalRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getTotalRun();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int completedRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCompletedRun();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]missedRunVal == [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]currentRunVal == [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]totalRunVal == [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]completedRunVal == [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.expired);
                    [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.black_shade));
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]missedRunVal > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.incompleted2);
                    [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.red));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// completed
                    [CtFieldReadImpl][CtVariableReadImpl]holder.process.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.process.setText([CtTypeAccessImpl]R.string.completed2);
                    [CtInvocationImpl][CtVariableReadImpl]bgShape.setColor([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getColor([CtTypeAccessImpl]R.color.bullet_green_color));
                }
            }
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.surveyTitle.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getTitle());
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtLiteralImpl]"Manually Schedule")) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.whenWasSurvey.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.as_scheduled));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtLiteralImpl]"One time")) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.whenWasSurvey.setText([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.onetime));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.whenWasSurvey.setText([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date startDate = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date endDate = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormat = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatForApi();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormatForActivityList = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatForActivityList();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormatForOtherFreq = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatForOtherFreq();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormat5 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatUtcNoZone();
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getStartTime().equalsIgnoreCase([CtLiteralImpl]"")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]startDate = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getStartTime().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]startDate = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
                }
                [CtAssignmentImpl][CtVariableWriteImpl]endDate = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getEndTime().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtTypeAccessImpl]SurveyScheduler.FREQUENCY_TYPE_DAILY)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String abc = [CtLiteralImpl]"";
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().isEmpty()) [CtBlockImpl]{
                        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                            [CtTryImpl]try [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dateString = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString();
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat sdf = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getHourMinuteSecondFormat();
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date date = [CtInvocationImpl][CtVariableReadImpl]sdf.parse([CtVariableReadImpl]dateString);
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getHourAmPmFormat1();
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat.format([CtVariableReadImpl]date).toString();
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtLiteralImpl]0) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]abc = [CtVariableReadImpl]formattedDate;
                                } else [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]abc = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]abc + [CtLiteralImpl]"<font color=\"#8c95a3\"> | </font>") + [CtVariableReadImpl]formattedDate;
                                }
                            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
                                [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                            }
                        }
                    }
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]abc.isEmpty()) [CtBlockImpl]{
                        [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setText([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]android.text.Html.fromHtml([CtVariableReadImpl]abc) + [CtLiteralImpl]" everyday", [CtTypeAccessImpl]TextView.BufferType.SPANNABLE);
                        [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    }
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]startDate) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.to)) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]endDate));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                }
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtTypeAccessImpl]SurveyScheduler.FREQUENCY_TYPE_MONTHLY)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dateString = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getStartTime().toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date date = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]dateString.split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat1 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getHourAmPmFormat1();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate1 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat1.format([CtVariableReadImpl]date).toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat2 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDdFormat();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate2 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat2.format([CtVariableReadImpl]date).toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]formattedDate1 + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.on)) + [CtLiteralImpl]" day") + [CtLiteralImpl]" ") + [CtVariableReadImpl]formattedDate2) + [CtFieldReadImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.TEXT_EVERY_MONTH;
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setText([CtVariableReadImpl]text);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]startDate) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.to)) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]endDate));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                }
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtTypeAccessImpl]SurveyScheduler.FREQUENCY_TYPE_WEEKLY)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String dateString = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getStartTime().toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date date = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]dateString.split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat1 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getHourAmPmFormat1();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate1 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat1.format([CtVariableReadImpl]date).toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat2 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getEeFormat();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate2 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat2.format([CtVariableReadImpl]date).toString();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]formattedDate1 + [CtFieldReadImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.TEXT_EVERY) + [CtVariableReadImpl]formattedDate2;
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setText([CtVariableReadImpl]text);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]startDate) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.to)) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForActivityList.format([CtVariableReadImpl]endDate));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                }
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtTypeAccessImpl]SurveyScheduler.FREQUENCY_TYPE_ONE_TIME)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtInvocationImpl]getDates([CtFieldReadImpl]items, [CtVariableReadImpl]endDate, [CtVariableReadImpl]position, [CtVariableReadImpl]startDate, [CtFieldReadImpl]joiningDate, [CtFieldReadImpl]context));
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                }
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.GONE);
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getType().equalsIgnoreCase([CtTypeAccessImpl]SurveyScheduler.FREQUENCY_TYPE_MANUALLY_SCHEDULE)) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// / Scheduled
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().isEmpty()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().size();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String startTime = [CtLiteralImpl]"";
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String endTime = [CtLiteralImpl]"";
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String finalTime;
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int finalpos = [CtLiteralImpl]0;
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString().isEmpty()) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]startTime = [CtInvocationImpl]getDateFormatedString([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                            }
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().toString().isEmpty()) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]endTime = [CtInvocationImpl]getDateFormatedString([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                            }
                            [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtInvocationImpl]checkCurrentTimeInBetweenDates([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0], [CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0], [CtVariableReadImpl]i);
                            [CtAssignmentImpl][CtVariableWriteImpl]finalTime = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]startTime + [CtLiteralImpl]" to ") + [CtVariableReadImpl]endTime;
                            [CtInvocationImpl][CtVariableReadImpl]mScheduledTime.add([CtVariableReadImpl]finalTime);
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_COMPLETED)) [CtBlockImpl]{
                                [CtTryImpl]try [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]finalpos = [CtBinaryOperatorImpl][CtVariableReadImpl]size - [CtLiteralImpl]1;
                                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0])) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0])));
                                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
                                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                                }
                            } else [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i == [CtLiteralImpl]0) [CtBlockImpl]{
                                    [CtInvocationImpl][CtCommentImpl]// if only 0 then show
                                    [CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0])) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0])));
                                }
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pos > [CtLiteralImpl]0) [CtBlockImpl]{
                                    [CtAssignmentImpl][CtVariableWriteImpl]finalpos = [CtVariableReadImpl]pos;
                                    [CtTryImpl]try [CtBlockImpl]{
                                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Date d1 = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getStartTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                                        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.Date d2 = [CtInvocationImpl][CtVariableReadImpl]simpleDateFormat5.parse([CtArrayReadImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtVariableReadImpl]position).getFrequency().getRuns().get([CtVariableReadImpl]i).getEndTime().toString().split([CtLiteralImpl]"\\.")[[CtLiteralImpl]0]);
                                        [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.date.setText([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]d1) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]d2));
                                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                                        [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                                    }
                                }
                            }
                        }
                        [CtInvocationImpl][CtFieldReadImpl]timePos.add([CtVariableReadImpl]position, [CtVariableReadImpl]finalpos);
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mScheduledTime.size() > [CtLiteralImpl]1) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]int pickerSize = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mScheduledTime.size() - [CtLiteralImpl]1;
                                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String val = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<u>" + [CtLiteralImpl]"+") + [CtVariableReadImpl]pickerSize) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.more)) + [CtLiteralImpl]"</u>";
                                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setText([CtInvocationImpl][CtTypeAccessImpl]android.text.Html.fromHtml([CtVariableReadImpl]val));
                                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setVisibility([CtTypeAccessImpl]View.GONE);
                            }
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                        }
                    }
                    [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.time.setVisibility([CtTypeAccessImpl]View.GONE);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                }
            }
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.container.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int currentRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getCurrentRunId();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int totalRunVal = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtVariableReadImpl]position).getTotalRun();
                    [CtIfImpl]if ([CtFieldReadImpl]click) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]click = [CtLiteralImpl]false;
                        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler().postDelayed([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
                            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                                [CtAssignmentImpl][CtFieldWriteImpl]click = [CtLiteralImpl]true;
                            }
                        }, [CtLiteralImpl]1500);
                        [CtIfImpl]if ([CtFieldReadImpl]paused) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtFieldReadImpl]context, [CtTypeAccessImpl]R.string.study_Joined_paused, [CtTypeAccessImpl]Toast.LENGTH_SHORT).show();
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_CURRENT) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.IN_PROGRESS) || [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.YET_To_START))) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).isRunIdAvailable()) [CtBlockImpl]{
                                [CtInvocationImpl][CtFieldReadImpl]surveyActivitiesFragment.getActivityInfo([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getActivityId(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getCurrentRunId(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getBranching(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getActivityVersion(), [CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()), [CtInvocationImpl][CtFieldReadImpl]items.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()));
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtFieldReadImpl]context, [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.survey_message), [CtTypeAccessImpl]Toast.LENGTH_SHORT).show();
                            }
                        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]status.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.STATUS_UPCOMING)) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtFieldReadImpl]context, [CtTypeAccessImpl]R.string.upcoming_event, [CtTypeAccessImpl]Toast.LENGTH_SHORT).show();
                        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]currentRunStatusForActivities.get([CtInvocationImpl][CtVariableReadImpl]holder.getAdapterPosition()).getStatus().equalsIgnoreCase([CtTypeAccessImpl]SurveyActivitiesFragment.INCOMPLETE)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentRunVal != [CtVariableReadImpl]totalRunVal) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtFieldReadImpl]context, [CtTypeAccessImpl]R.string.incomple_event, [CtTypeAccessImpl]Toast.LENGTH_SHORT).show();
                            }
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentRunVal != [CtVariableReadImpl]totalRunVal) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtFieldReadImpl]context, [CtTypeAccessImpl]R.string.completed_event, [CtTypeAccessImpl]Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]holder.more.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int p = [CtLiteralImpl]0;
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl][CtFieldReadImpl]timePos.get([CtVariableReadImpl]position);
                    }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]com.harvard.studyappmodule.CustomActivitiesDailyDialogClass c = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.harvard.studyappmodule.CustomActivitiesDailyDialogClass([CtFieldReadImpl]context, [CtVariableReadImpl]mScheduledTime, [CtVariableReadImpl]p, [CtLiteralImpl]false, [CtThisAccessImpl]com.harvard.studyappmodule.SurveyActivitiesListAdapter.this);
                    [CtInvocationImpl][CtVariableReadImpl]c.show();
                }
            });
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getDateFormatedString([CtParameterImpl][CtTypeReferenceImpl]java.lang.String startTime) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat sdf = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatUtcNoZone();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date date = [CtInvocationImpl][CtVariableReadImpl]sdf.parse([CtVariableReadImpl]startTime);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat dateFormat1 = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getHourAmPmMonthDayYearFormat();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formattedDate = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateFormat1.format([CtVariableReadImpl]date).toString();
            [CtReturnImpl]return [CtVariableReadImpl]formattedDate;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int checkCurrentTimeInBetweenDates([CtParameterImpl][CtTypeReferenceImpl]java.lang.String date1, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String date2, [CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0;
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]date1.isEmpty()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]date2.isEmpty())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date time1 = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatUtcNoZone().parse([CtVariableReadImpl]date1);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar calendar1 = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtInvocationImpl][CtVariableReadImpl]calendar1.setTime([CtVariableReadImpl]time1);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date time2 = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatUtcNoZone().parse([CtVariableReadImpl]date2);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar calendar2 = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtInvocationImpl][CtVariableReadImpl]calendar2.setTime([CtVariableReadImpl]time2);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar current = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date x = [CtInvocationImpl][CtVariableReadImpl]current.getTime();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]x.after([CtInvocationImpl][CtVariableReadImpl]calendar1.getTime()) && [CtInvocationImpl][CtVariableReadImpl]x.before([CtInvocationImpl][CtVariableReadImpl]calendar2.getTime())) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtVariableReadImpl]i;
                }
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.text.ParseException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.Logger.log([CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]pos;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getDates([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.harvard.studyappmodule.activitylistmodel.ActivitiesWS> items, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date endDate, [CtParameterImpl][CtTypeReferenceImpl]int position, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date startDate, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date joiningDate, [CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat simpleDateFormatForOtherFreq = [CtInvocationImpl][CtTypeAccessImpl]com.harvard.utils.AppController.getDateFormatForOtherFreq();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String date = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]endDate != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getSchedulingType().equalsIgnoreCase([CtLiteralImpl]"AnchorDate") && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getAnchorDate() != [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getAnchorDate().getSourceType() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getAnchorDate().getSourceType().equalsIgnoreCase([CtLiteralImpl]"EnrollmentDate")) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getAnchorDate().getStart() == [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]items.get([CtVariableReadImpl]position).getAnchorDate().getEnd() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtVariableReadImpl]joiningDate.after([CtVariableReadImpl]startDate)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar joiningCalendar = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtInvocationImpl][CtVariableReadImpl]joiningCalendar.setTime([CtVariableReadImpl]joiningDate);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar startCalendar = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
                [CtInvocationImpl][CtVariableReadImpl]startCalendar.setTime([CtVariableReadImpl]startDate);
                [CtInvocationImpl][CtVariableReadImpl]startCalendar.set([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]DAY_OF_MONTH, [CtInvocationImpl][CtVariableReadImpl]joiningCalendar.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]DAY_OF_MONTH));
                [CtInvocationImpl][CtVariableReadImpl]startCalendar.set([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]MONTH, [CtInvocationImpl][CtVariableReadImpl]joiningCalendar.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]MONTH));
                [CtInvocationImpl][CtVariableReadImpl]startCalendar.set([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]YEAR, [CtInvocationImpl][CtVariableReadImpl]joiningCalendar.get([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]YEAR));
                [CtAssignmentImpl][CtVariableWriteImpl]date = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtInvocationImpl][CtVariableReadImpl]startCalendar.getTime()) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]endDate);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]date = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]startDate) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]endDate);
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]date = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getResources().getString([CtTypeAccessImpl]R.string.from) + [CtLiteralImpl]" : ") + [CtInvocationImpl][CtVariableReadImpl]simpleDateFormatForOtherFreq.format([CtVariableReadImpl]startDate);
        }
        [CtReturnImpl]return [CtVariableReadImpl]date;
    }
}
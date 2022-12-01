[CompilationUnitImpl][CtPackageDeclarationImpl]package org.schabi.newpipe.report;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import org.schabi.newpipe.ActivityCommunicator;
[CtUnresolvedImport]import org.schabi.newpipe.util.ThemeHelper;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import org.schabi.newpipe.util.ShareUtils;
[CtUnresolvedImport]import org.schabi.newpipe.BuildConfig;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import androidx.appcompat.app.AppCompatActivity;
[CtUnresolvedImport]import android.os.Parcel;
[CtUnresolvedImport]import android.view.MenuItem;
[CtUnresolvedImport]import android.content.ClipData;
[CtUnresolvedImport]import androidx.appcompat.app.ActionBar;
[CtImportImpl]import java.io.StringWriter;
[CtUnresolvedImport]import android.view.MenuInflater;
[CtUnresolvedImport]import android.os.Handler;
[CtUnresolvedImport]import android.widget.Toast;
[CtUnresolvedImport]import android.view.Menu;
[CtUnresolvedImport]import android.content.ClipboardManager;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import android.widget.Button;
[CtImportImpl]import java.util.Vector;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import android.os.Build;
[CtUnresolvedImport]import static org.schabi.newpipe.util.Localization.assureCorrectAppLanguage;
[CtUnresolvedImport]import org.schabi.newpipe.R;
[CtUnresolvedImport]import android.util.Log;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import androidx.core.app.NavUtils;
[CtUnresolvedImport]import android.os.Parcelable;
[CtUnresolvedImport]import org.acra.data.CrashReportData;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import android.app.AlertDialog;
[CtUnresolvedImport]import android.graphics.Color;
[CtImportImpl]import java.util.TimeZone;
[CtUnresolvedImport]import androidx.annotation.StringRes;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtUnresolvedImport]import android.widget.TextView;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import com.grack.nanojson.JsonWriter;
[CtUnresolvedImport]import org.schabi.newpipe.MainActivity;
[CtUnresolvedImport]import androidx.appcompat.widget.Toolbar;
[CtUnresolvedImport]import org.acra.ReportField;
[CtUnresolvedImport]import android.app.Activity;
[CtUnresolvedImport]import com.google.android.material.snackbar.Snackbar;
[CtUnresolvedImport]import org.schabi.newpipe.util.Localization;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import android.widget.EditText;
[CtClassImpl][CtCommentImpl]/* Created by Christian Schabesberger on 24.10.15.

Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
ErrorActivity.java is part of NewPipe.

NewPipe is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
<
NewPipe is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
<
You should have received a copy of the GNU General Public License
along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ErrorActivity extends [CtTypeReferenceImpl]androidx.appcompat.app.AppCompatActivity {
    [CtFieldImpl][CtCommentImpl]// LOG TAGS
    public static final [CtTypeReferenceImpl]java.lang.String TAG = [CtInvocationImpl][CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.class.toString();

    [CtFieldImpl][CtCommentImpl]// BUNDLE TAGS
    public static final [CtTypeReferenceImpl]java.lang.String ERROR_INFO = [CtLiteralImpl]"error_info";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ERROR_LIST = [CtLiteralImpl]"error_list";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ERROR_EMAIL_ADDRESS = [CtLiteralImpl]"crashreport@newpipe.schabi.org";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ERROR_EMAIL_SUBJECT = [CtBinaryOperatorImpl][CtLiteralImpl]"Exception in NewPipe " + [CtFieldReadImpl]org.schabi.newpipe.BuildConfig.VERSION_NAME;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ERROR_GITHUB_ISSUE_URL = [CtLiteralImpl]"https://github.com/TeamNewPipe/NewPipe/issues";

    [CtFieldImpl]private [CtArrayTypeReferenceImpl]java.lang.String[] errorList;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Class returnActivity;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String currentTimeStamp;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.EditText userCommentBox;

    [CtMethodImpl]public static [CtTypeReferenceImpl]void reportUiError([CtParameterImpl]final [CtTypeReferenceImpl]androidx.appcompat.app.AppCompatActivity activity, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable el) [CtBlockImpl]{
        [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.reportError([CtVariableReadImpl]activity, [CtVariableReadImpl]el, [CtInvocationImpl][CtVariableReadImpl]activity.getClass(), [CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo.make([CtTypeAccessImpl]UserAction.UI_ERROR, [CtLiteralImpl]"none", [CtLiteralImpl]"", [CtTypeAccessImpl]R.string.app_ui_crash));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void reportError([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> el, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class returnActivity, [CtParameterImpl]final [CtTypeReferenceImpl]android.view.View rootView, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rootView != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.android.material.snackbar.Snackbar.make([CtVariableReadImpl]rootView, [CtTypeAccessImpl]R.string.error_snackbar_message, [CtBinaryOperatorImpl][CtLiteralImpl]3 * [CtLiteralImpl]1000).setActionTextColor([CtTypeAccessImpl]Color.YELLOW).setAction([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getString([CtTypeAccessImpl]R.string.error_snackbar_action).toUpperCase(), [CtLambdaImpl]([CtParameterImpl] v) -> [CtInvocationImpl]startErrorActivity([CtVariableReadImpl]returnActivity, [CtVariableReadImpl]context, [CtVariableReadImpl]errorInfo, [CtVariableReadImpl]el)).show();
        } else [CtBlockImpl]{
            [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.startErrorActivity([CtVariableReadImpl]returnActivity, [CtVariableReadImpl]context, [CtVariableReadImpl]errorInfo, [CtVariableReadImpl]el);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void startErrorActivity([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class returnActivity, [CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> el) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.ActivityCommunicator ac = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.ActivityCommunicator.getCommunicator();
        [CtInvocationImpl][CtVariableReadImpl]ac.setReturnActivity([CtVariableReadImpl]returnActivity);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtVariableReadImpl]context, [CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.class);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_INFO, [CtVariableReadImpl]errorInfo);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_LIST, [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.elToSl([CtVariableReadImpl]el));
        [CtInvocationImpl][CtVariableReadImpl]intent.addFlags([CtTypeAccessImpl]Intent.FLAG_ACTIVITY_NEW_TASK);
        [CtInvocationImpl][CtVariableReadImpl]context.startActivity([CtVariableReadImpl]intent);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void reportError([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable e, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class returnActivity, [CtParameterImpl]final [CtTypeReferenceImpl]android.view.View rootView, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> el = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]el = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
            [CtInvocationImpl][CtVariableReadImpl]el.add([CtVariableReadImpl]e);
        }
        [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.reportError([CtVariableReadImpl]context, [CtVariableReadImpl]el, [CtVariableReadImpl]returnActivity, [CtVariableReadImpl]rootView, [CtVariableReadImpl]errorInfo);
    }

    [CtMethodImpl][CtCommentImpl]// async call
    public static [CtTypeReferenceImpl]void reportError([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Handler handler, [CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable e, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class returnActivity, [CtParameterImpl]final [CtTypeReferenceImpl]android.view.View rootView, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> el = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]e != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]el = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
            [CtInvocationImpl][CtVariableReadImpl]el.add([CtVariableReadImpl]e);
        }
        [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.reportError([CtVariableReadImpl]handler, [CtVariableReadImpl]context, [CtVariableReadImpl]el, [CtVariableReadImpl]returnActivity, [CtVariableReadImpl]rootView, [CtVariableReadImpl]errorInfo);
    }

    [CtMethodImpl][CtCommentImpl]// async call
    public static [CtTypeReferenceImpl]void reportError([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Handler handler, [CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> el, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class returnActivity, [CtParameterImpl]final [CtTypeReferenceImpl]android.view.View rootView, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]handler.post([CtLambdaImpl]() -> [CtInvocationImpl]reportError([CtVariableReadImpl]context, [CtVariableReadImpl]el, [CtVariableReadImpl]returnActivity, [CtVariableReadImpl]rootView, [CtVariableReadImpl]errorInfo));
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void reportError([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]org.acra.data.CrashReportData report, [CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo errorInfo) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] el = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtVariableReadImpl]report.getString([CtTypeAccessImpl]ReportField.STACK_TRACE) };
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtVariableReadImpl]context, [CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.class);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_INFO, [CtVariableReadImpl]errorInfo);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_LIST, [CtVariableReadImpl]el);
        [CtInvocationImpl][CtVariableReadImpl]intent.addFlags([CtTypeAccessImpl]Intent.FLAG_ACTIVITY_NEW_TASK);
        [CtInvocationImpl][CtVariableReadImpl]context.startActivity([CtVariableReadImpl]intent);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.lang.String getStackTrace([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Throwable throwable) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw, [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]throwable.printStackTrace([CtVariableReadImpl]pw);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sw.getBuffer().toString();
    }

    [CtMethodImpl][CtCommentImpl]// errorList to StringList
    private static [CtArrayTypeReferenceImpl]java.lang.String[] elToSl([CtParameterImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Throwable> stackTraces) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] out = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtInvocationImpl][CtVariableReadImpl]stackTraces.size()];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]stackTraces.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]out[[CtVariableReadImpl]i] = [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.getStackTrace([CtInvocationImpl][CtVariableReadImpl]stackTraces.get([CtVariableReadImpl]i));
        }
        [CtReturnImpl]return [CtVariableReadImpl]out;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onCreate([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Bundle savedInstanceState) [CtBlockImpl]{
        [CtInvocationImpl]Localization.assureCorrectAppLanguage([CtThisAccessImpl]this);
        [CtInvocationImpl][CtSuperAccessImpl]super.onCreate([CtVariableReadImpl]savedInstanceState);
        [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.util.ThemeHelper.setTheme([CtThisAccessImpl]this);
        [CtInvocationImpl]setContentView([CtTypeAccessImpl]R.layout.activity_error);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtInvocationImpl]getIntent();
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.appcompat.widget.Toolbar toolbar = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.toolbar);
        [CtInvocationImpl]setSupportActionBar([CtVariableReadImpl]toolbar);
        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.appcompat.app.ActionBar actionBar = [CtInvocationImpl]getSupportActionBar();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actionBar != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]actionBar.setDisplayHomeAsUpEnabled([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]actionBar.setTitle([CtTypeAccessImpl]R.string.error_report_title);
            [CtInvocationImpl][CtVariableReadImpl]actionBar.setDisplayShowTitleEnabled([CtLiteralImpl]true);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button reportEmailButton = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorReportEmailButton);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button copyButton = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorReportCopyButton);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button reportGithubButton = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorReportGitHubButton);
        [CtAssignmentImpl][CtFieldWriteImpl]userCommentBox = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorCommentBox);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.TextView errorView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.TextView infoView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorInfosView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.TextView errorMessageView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorMessageView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.schabi.newpipe.ActivityCommunicator ac = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.ActivityCommunicator.getCommunicator();
        [CtAssignmentImpl][CtFieldWriteImpl]returnActivity = [CtInvocationImpl][CtVariableReadImpl]ac.getReturnActivity();
        [CtAssignmentImpl][CtFieldWriteImpl]errorInfo = [CtInvocationImpl][CtVariableReadImpl]intent.getParcelableExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_INFO);
        [CtAssignmentImpl][CtFieldWriteImpl]errorList = [CtInvocationImpl][CtVariableReadImpl]intent.getStringArrayExtra([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.ERROR_LIST);
        [CtInvocationImpl][CtCommentImpl]// important add guru meditation
        addGuruMeditation();
        [CtAssignmentImpl][CtFieldWriteImpl]currentTimeStamp = [CtInvocationImpl]getCurrentTimeStamp();
        [CtInvocationImpl][CtVariableReadImpl]reportEmailButton.setOnClickListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) -> [CtBlockImpl]{
            [CtInvocationImpl]openPrivacyPolicyDialog([CtThisAccessImpl]this, [CtLiteralImpl]"EMAIL");
        });
        [CtInvocationImpl][CtVariableReadImpl]copyButton.setOnClickListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ClipboardManager clipboard = [CtInvocationImpl](([CtTypeReferenceImpl]android.content.ClipboardManager) (getSystemService([CtVariableReadImpl]Context.CLIPBOARD_SERVICE)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ClipData clip = [CtInvocationImpl][CtTypeAccessImpl]android.content.ClipData.newPlainText([CtLiteralImpl]"NewPipe error report", [CtInvocationImpl]buildMarkdown());
            [CtInvocationImpl][CtVariableReadImpl]clipboard.setPrimaryClip([CtVariableReadImpl]clip);
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.widget.Toast.makeText([CtThisAccessImpl]this, [CtVariableReadImpl]R.string.msg_copied, [CtVariableReadImpl]Toast.LENGTH_SHORT).show();
        });
        [CtInvocationImpl][CtVariableReadImpl]reportGithubButton.setOnClickListener([CtLambdaImpl]([CtParameterImpl][CtTypeReferenceImpl]android.view.View v) -> [CtBlockImpl]{
            [CtInvocationImpl]openPrivacyPolicyDialog([CtThisAccessImpl]this, [CtLiteralImpl]"GITHUB");
        });
        [CtInvocationImpl][CtCommentImpl]// normal bugreport
        buildInfo([CtFieldReadImpl]errorInfo);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.message != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errorMessageView.setText([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.message);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]errorMessageView.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.messageWhatHappenedView).setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtInvocationImpl][CtVariableReadImpl]errorView.setText([CtInvocationImpl]formErrorText([CtFieldReadImpl]errorList));
        [CtForEachImpl][CtCommentImpl]// print stack trace once again for debugging:
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String e : [CtFieldReadImpl]errorList) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.e([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.TAG, [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onCreateOptionsMenu([CtParameterImpl]final [CtTypeReferenceImpl]android.view.Menu menu) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.MenuInflater inflater = [CtInvocationImpl]getMenuInflater();
        [CtInvocationImpl][CtVariableReadImpl]inflater.inflate([CtTypeAccessImpl]R.menu.error_menu, [CtVariableReadImpl]menu);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onOptionsItemSelected([CtParameterImpl]final [CtTypeReferenceImpl]android.view.MenuItem item) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int id = [CtInvocationImpl][CtVariableReadImpl]item.getItemId();
        [CtSwitchImpl]switch ([CtVariableReadImpl]id) {
            [CtCaseImpl]case [CtFieldReadImpl]android.R.id.home :
                [CtInvocationImpl]goToReturnActivity();
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]R.id.menu_item_share_error :
                [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent();
                [CtInvocationImpl][CtVariableReadImpl]intent.setAction([CtTypeAccessImpl]Intent.ACTION_SEND);
                [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtTypeAccessImpl]Intent.EXTRA_TEXT, [CtInvocationImpl]buildJson());
                [CtInvocationImpl][CtVariableReadImpl]intent.setType([CtLiteralImpl]"text/plain");
                [CtInvocationImpl]startActivity([CtInvocationImpl][CtTypeAccessImpl]android.content.Intent.createChooser([CtVariableReadImpl]intent, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.share_dialog_title)));
                [CtBreakImpl]break;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void openPrivacyPolicyDialog([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String action) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.app.AlertDialog.Builder([CtVariableReadImpl]context).setIcon([CtTypeAccessImpl]android.R.drawable.ic_dialog_alert).setTitle([CtTypeAccessImpl]R.string.privacy_policy_title).setMessage([CtTypeAccessImpl]R.string.start_accept_privacy_policy).setCancelable([CtLiteralImpl]false).setNeutralButton([CtTypeAccessImpl]R.string.read_privacy_policy, [CtLambdaImpl]([CtParameterImpl] dialog,[CtParameterImpl] which) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent webIntent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtVariableReadImpl]Intent.ACTION_VIEW, [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtInvocationImpl][CtVariableReadImpl]context.getString([CtVariableReadImpl]R.string.privacy_policy_url)));
            [CtInvocationImpl][CtVariableReadImpl]context.startActivity([CtVariableReadImpl]webIntent);
        }).setPositiveButton([CtTypeAccessImpl]R.string.accept, [CtLambdaImpl]([CtParameterImpl] dialog,[CtParameterImpl] which) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]action.equals([CtLiteralImpl]"EMAIL")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// send on email
                final [CtTypeReferenceImpl]android.content.Intent i = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtCommentImpl]// only email apps should handle this
                [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtVariableReadImpl]Intent.ACTION_SENDTO).setData([CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtLiteralImpl]"mailto:")).putExtra([CtVariableReadImpl]Intent.EXTRA_EMAIL, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtFieldReadImpl][CtFieldReferenceImpl]ERROR_EMAIL_ADDRESS }).putExtra([CtVariableReadImpl]Intent.EXTRA_SUBJECT, [CtFieldReadImpl][CtFieldReferenceImpl]ERROR_EMAIL_SUBJECT).putExtra([CtVariableReadImpl]Intent.EXTRA_TEXT, [CtInvocationImpl]buildJson());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]i.resolveActivity([CtInvocationImpl]getPackageManager()) != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]startActivity([CtVariableReadImpl]i);
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]action.equals([CtLiteralImpl]"GITHUB")) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// open the NewPipe issue page on GitHub
                [CtTypeAccessImpl]org.schabi.newpipe.util.ShareUtils.openUrlInBrowser([CtThisAccessImpl]this, [CtFieldReadImpl][CtFieldReferenceImpl]ERROR_GITHUB_ISSUE_URL);
            }
        }).setNegativeButton([CtTypeAccessImpl]R.string.decline, [CtLambdaImpl]([CtParameterImpl] dialog,[CtParameterImpl] which) -> [CtBlockImpl]{
            [CtCommentImpl]// do nothing
        }).show();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String formErrorText([CtParameterImpl]final [CtArrayTypeReferenceImpl]java.lang.String[] el) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder text = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]el != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String e : [CtVariableReadImpl]el) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]text.append([CtLiteralImpl]"-------------------------------------\n").append([CtVariableReadImpl]e);
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]text.append([CtLiteralImpl]"-------------------------------------");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]text.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the checked activity.
     *
     * @param returnActivity
     * 		the activity to return to
     * @return the casted return activity or null
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    static [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]android.app.Activity> getReturnActivity([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> returnActivity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]android.app.Activity> checkedReturnActivity = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]returnActivity != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]android.app.Activity.class.isAssignableFrom([CtVariableReadImpl]returnActivity)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]checkedReturnActivity = [CtInvocationImpl][CtVariableReadImpl]returnActivity.asSubclass([CtFieldReadImpl]android.app.Activity.class);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]checkedReturnActivity = [CtFieldReadImpl]org.schabi.newpipe.MainActivity.class;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]checkedReturnActivity;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void goToReturnActivity() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]? extends [CtTypeReferenceImpl]android.app.Activity> checkedReturnActivity = [CtInvocationImpl]org.schabi.newpipe.report.ErrorActivity.getReturnActivity([CtFieldReadImpl]returnActivity);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]checkedReturnActivity == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.onBackPressed();
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtThisAccessImpl]this, [CtVariableReadImpl]checkedReturnActivity);
            [CtInvocationImpl][CtVariableReadImpl]intent.addFlags([CtTypeAccessImpl]Intent.FLAG_ACTIVITY_CLEAR_TOP);
            [CtInvocationImpl][CtTypeAccessImpl]androidx.core.app.NavUtils.navigateUpTo([CtThisAccessImpl]this, [CtVariableReadImpl]intent);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void buildInfo([CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo info) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.TextView infoLabelView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorInfoLabelsView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.TextView infoView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorInfosView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtLiteralImpl]"";
        [CtInvocationImpl][CtVariableReadImpl]infoLabelView.setText([CtInvocationImpl][CtInvocationImpl]getString([CtTypeAccessImpl]R.string.info_labels).replace([CtLiteralImpl]"\\n", [CtLiteralImpl]"\n"));
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]text += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getUserActionString([CtFieldReadImpl][CtVariableReadImpl]info.userAction) + [CtLiteralImpl]"\n") + [CtFieldReadImpl][CtVariableReadImpl]info.request) + [CtLiteralImpl]"\n") + [CtInvocationImpl]getContentLanguageString()) + [CtLiteralImpl]"\n") + [CtInvocationImpl]getContentCountryString()) + [CtLiteralImpl]"\n") + [CtInvocationImpl]getAppLanguage()) + [CtLiteralImpl]"\n") + [CtFieldReadImpl][CtVariableReadImpl]info.serviceName) + [CtLiteralImpl]"\n") + [CtFieldReadImpl]currentTimeStamp) + [CtLiteralImpl]"\n") + [CtInvocationImpl]getPackageName()) + [CtLiteralImpl]"\n") + [CtFieldReadImpl]org.schabi.newpipe.BuildConfig.VERSION_NAME) + [CtLiteralImpl]"\n") + [CtInvocationImpl]getOsString();
        [CtInvocationImpl][CtVariableReadImpl]infoView.setText([CtVariableReadImpl]text);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String buildJson() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.grack.nanojson.JsonWriter.string().object().value([CtLiteralImpl]"user_action", [CtInvocationImpl]getUserActionString([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.userAction)).value([CtLiteralImpl]"request", [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.request).value([CtLiteralImpl]"content_language", [CtInvocationImpl]getContentLanguageString()).value([CtLiteralImpl]"content_country", [CtInvocationImpl]getContentCountryString()).value([CtLiteralImpl]"app_langauge", [CtInvocationImpl]getAppLanguage()).value([CtLiteralImpl]"app_language", [CtInvocationImpl]getAppLanguage()).value([CtLiteralImpl]"service", [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.serviceName).value([CtLiteralImpl]"package", [CtInvocationImpl]getPackageName()).value([CtLiteralImpl]"version", [CtTypeAccessImpl]BuildConfig.VERSION_NAME).value([CtLiteralImpl]"os", [CtInvocationImpl]getOsString()).value([CtLiteralImpl]"time", [CtFieldReadImpl]currentTimeStamp).array([CtLiteralImpl]"exceptions", [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtFieldReadImpl]errorList)).value([CtLiteralImpl]"user_comment", [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userCommentBox.getText().toString()).end().done();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.e([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.TAG, [CtLiteralImpl]"Error while erroring: Could not build json");
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
        [CtReturnImpl]return [CtLiteralImpl]"";
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String buildMarkdown() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder htmlErrorReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String userComment = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]userCommentBox.getText().toString();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]userComment.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtVariableReadImpl]userComment).append([CtLiteralImpl]"\n");
            }
            [CtInvocationImpl][CtCommentImpl]// basic error info
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"## Exception").append([CtLiteralImpl]"\n* __User Action:__ ").append([CtInvocationImpl]getUserActionString([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.userAction)).append([CtLiteralImpl]"\n* __Request:__ ").append([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.request).append([CtLiteralImpl]"\n* __Content Country:__ ").append([CtInvocationImpl]getContentCountryString()).append([CtLiteralImpl]"\n* __Content Language:__ ").append([CtInvocationImpl]getContentLanguageString()).append([CtLiteralImpl]"\n* __App Language:__ ").append([CtInvocationImpl]getAppLanguage()).append([CtLiteralImpl]"\n* __Service:__ ").append([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorInfo.serviceName).append([CtLiteralImpl]"\n* __Version:__ ").append([CtTypeAccessImpl]BuildConfig.VERSION_NAME).append([CtLiteralImpl]"\n* __OS:__ ").append([CtInvocationImpl]getOsString()).append([CtLiteralImpl]"\n");
            [CtIfImpl][CtCommentImpl]// Collapse all logs to a single paragraph when there are more than one
            [CtCommentImpl]// to keep the GitHub issue clean.
            if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorList.length > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"<details><summary><b>Exceptions (").append([CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorList.length).append([CtLiteralImpl]")</b></summary><p>\n");
            }
            [CtForImpl][CtCommentImpl]// add the logs
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorList.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"<details><summary><b>Crash log ");
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorList.length > [CtLiteralImpl]1) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1);
                }
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"</b>").append([CtLiteralImpl]"</summary><p>\n").append([CtLiteralImpl]"\n```\n").append([CtArrayReadImpl][CtFieldReadImpl]errorList[[CtVariableReadImpl]i]).append([CtLiteralImpl]"\n```\n").append([CtLiteralImpl]"</details>\n");
            }
            [CtIfImpl][CtCommentImpl]// make sure to close everything
            if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]errorList.length > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"</p></details>\n");
            }
            [CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.append([CtLiteralImpl]"<hr>\n");
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]htmlErrorReport.toString();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]android.util.Log.e([CtFieldReadImpl]org.schabi.newpipe.report.ErrorActivity.TAG, [CtLiteralImpl]"Error while erroring: Could not build markdown");
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getUserActionString([CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.UserAction userAction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]userAction == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"Your description is in another castle.";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]userAction.getMessage();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getContentCountryString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.util.Localization.getPreferredContentCountry([CtThisAccessImpl]this).getCountryCode();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getContentLanguageString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.util.Localization.getPreferredLocalization([CtThisAccessImpl]this).getLocalizationCode();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getAppLanguage() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.util.Localization.getAppLocale([CtInvocationImpl]getApplicationContext()).toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getOsString() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String osBase = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]Build.VERSION.SDK_INT >= [CtLiteralImpl]23) ? [CtFieldReadImpl]Build.VERSION.BASE_OS : [CtLiteralImpl]"Android";
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getProperty([CtLiteralImpl]"os.name") + [CtLiteralImpl]" ") + [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]osBase.isEmpty() ? [CtLiteralImpl]"Android" : [CtVariableReadImpl]osBase)) + [CtLiteralImpl]" ") + [CtFieldReadImpl]Build.VERSION.RELEASE) + [CtLiteralImpl]" - ") + [CtFieldReadImpl]Build.VERSION.SDK_INT;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addGuruMeditation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// just an easter egg
        [CtTypeReferenceImpl]android.widget.TextView sorryView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.errorSorryView);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String text = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sorryView.getText().toString();
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]text += [CtBinaryOperatorImpl][CtLiteralImpl]"\n" + [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.guru_meditation);
        [CtInvocationImpl][CtVariableReadImpl]sorryView.setText([CtVariableReadImpl]text);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onBackPressed() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// super.onBackPressed();
        goToReturnActivity();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCurrentTimeStamp() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.SimpleDateFormat df = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"yyyy-MM-dd HH:mm");
        [CtInvocationImpl][CtVariableReadImpl]df.setTimeZone([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"GMT"));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]df.format([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date());
    }

    [CtClassImpl]public static class ErrorInfo implements [CtTypeReferenceImpl]android.os.Parcelable {
        [CtFieldImpl]public static final [CtTypeReferenceImpl][CtTypeReferenceImpl]android.os.Parcelable.Creator<[CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo> CREATOR = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.os.Parcelable.Creator<[CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo>()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorInfo createFromParcel([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Parcel source) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorInfo([CtVariableReadImpl]source);
            }

            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtArrayTypeReferenceImpl]org.schabi.newpipe.report.ErrorInfo[] newArray([CtParameterImpl]final [CtTypeReferenceImpl]int size) [CtBlockImpl]{
                [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorInfo[[CtVariableReadImpl]size];
            }
        };

        [CtFieldImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.UserAction userAction;

        [CtFieldImpl]public final [CtTypeReferenceImpl]java.lang.String request;

        [CtFieldImpl]final [CtTypeReferenceImpl]java.lang.String serviceName;

        [CtFieldImpl][CtAnnotationImpl]@androidx.annotation.StringRes
        public final [CtTypeReferenceImpl]int message;

        [CtConstructorImpl]private ErrorInfo([CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.UserAction userAction, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String serviceName, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String request, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.StringRes
        final [CtTypeReferenceImpl]int message) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.userAction = [CtVariableReadImpl]userAction;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceName = [CtVariableReadImpl]serviceName;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.request = [CtVariableReadImpl]request;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.message = [CtVariableReadImpl]message;
        }

        [CtConstructorImpl]protected ErrorInfo([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Parcel in) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.userAction = [CtInvocationImpl][CtTypeAccessImpl]org.schabi.newpipe.report.UserAction.valueOf([CtInvocationImpl][CtVariableReadImpl]in.readString());
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.request = [CtInvocationImpl][CtVariableReadImpl]in.readString();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.serviceName = [CtInvocationImpl][CtVariableReadImpl]in.readString();
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.message = [CtInvocationImpl][CtVariableReadImpl]in.readInt();
        }

        [CtMethodImpl]public static [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo make([CtParameterImpl]final [CtTypeReferenceImpl]org.schabi.newpipe.report.UserAction userAction, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String serviceName, [CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String request, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.StringRes
        final [CtTypeReferenceImpl]int message) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.schabi.newpipe.report.ErrorActivity.ErrorInfo([CtVariableReadImpl]userAction, [CtVariableReadImpl]serviceName, [CtVariableReadImpl]request, [CtVariableReadImpl]message);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]int describeContents() [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void writeToParcel([CtParameterImpl]final [CtTypeReferenceImpl]android.os.Parcel dest, [CtParameterImpl]final [CtTypeReferenceImpl]int flags) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]dest.writeString([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.userAction.name());
            [CtInvocationImpl][CtVariableReadImpl]dest.writeString([CtFieldReadImpl][CtThisAccessImpl]this.request);
            [CtInvocationImpl][CtVariableReadImpl]dest.writeString([CtFieldReadImpl][CtThisAccessImpl]this.serviceName);
            [CtInvocationImpl][CtVariableReadImpl]dest.writeInt([CtFieldReadImpl][CtThisAccessImpl]this.message);
        }
    }
}
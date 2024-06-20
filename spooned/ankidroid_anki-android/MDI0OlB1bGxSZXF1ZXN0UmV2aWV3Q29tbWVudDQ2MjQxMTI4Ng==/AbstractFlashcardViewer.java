[CompilationUnitImpl][CtJavaDocImpl]/**
 * **************************************************************************************
 * Copyright (c) 2011 Kostas Spyropoulos <inigo.aldana@gmail.com>                       *
 * Copyright (c) 2014 Bruno Romero de Azevedo <brunodea@inf.ufsm.br>                    *
 * Copyright (c) 2014–15 Roland Sieker <ospalh@gmail.com>                               *
 * Copyright (c) 2015 Timothy Rae <perceptualchaos2@gmail.com>                          *
 * Copyright (c) 2016 Mark Carter <mark@marcardar.com>                                  *
 *                                                                                      *
 * This program is free software; you can redistribute it and/or modify it under        *
 * the terms of the GNU General Public License as published by the Free Software        *
 * Foundation; either version 3 of the License, or (at your option) any later           *
 * version.                                                                             *
 *                                                                                      *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY      *
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.             *
 *                                                                                      *
 * You should have received a copy of the GNU General Public License along with         *
 * this program.  If not, see <http://www.gnu.org/licenses/>.                           *
 * **************************************************************************************
 */
[CtPackageDeclarationImpl]package com.ichi2.anki;
[CtUnresolvedImport]import android.webkit.JavascriptInterface;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import com.ichi2.anki.dialogs.TagsDialog;
[CtUnresolvedImport]import android.webkit.JsResult;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.widget.FrameLayout;
[CtUnresolvedImport]import android.widget.ImageView;
[CtUnresolvedImport]import com.ichi2.libanki.sched.AbstractSched;
[CtUnresolvedImport]import android.webkit.RenderProcessGoneDetail;
[CtUnresolvedImport]import android.webkit.WebView.HitTestResult;
[CtUnresolvedImport]import androidx.appcompat.app.ActionBar;
[CtUnresolvedImport]import com.ichi2.libanki.Decks;
[CtUnresolvedImport]import android.os.Handler;
[CtUnresolvedImport]import com.ichi2.libanki.DeckConfig;
[CtUnresolvedImport]import com.ichi2.utils.JSONException;
[CtUnresolvedImport]import androidx.core.content.ContextCompat;
[CtUnresolvedImport]import android.annotation.TargetApi;
[CtUnresolvedImport]import android.content.IntentFilter;
[CtUnresolvedImport]import com.ichi2.anki.reviewer.CardMarker;
[CtUnresolvedImport]import com.ichi2.utils.JSONObject;
[CtUnresolvedImport]import androidx.annotation.IdRes;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import android.os.Message;
[CtUnresolvedImport]import com.ichi2.libanki.Consts;
[CtUnresolvedImport]import com.ichi2.compat.CompatHelper;
[CtUnresolvedImport]import com.ichi2.utils.WebViewDebugging;
[CtUnresolvedImport]import com.ichi2.utils.DiffEngine;
[CtUnresolvedImport]import com.ichi2.anki.cardviewer.TypedAnswer;
[CtUnresolvedImport]import android.webkit.WebResourceResponse;
[CtUnresolvedImport]import android.text.SpannedString;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.regex.Matcher;
[CtUnresolvedImport]import android.os.SystemClock;
[CtUnresolvedImport]import com.ichi2.anki.receiver.SdCardReceiver;
[CtUnresolvedImport]import com.ichi2.anim.ViewAnimation;
[CtUnresolvedImport]import static com.ichi2.anki.cardviewer.CardAppearance.calculateDynamicFontSize;
[CtUnresolvedImport]import android.webkit.WebResourceRequest;
[CtUnresolvedImport]import com.ichi2.anki.cardviewer.CardAppearance;
[CtUnresolvedImport]import android.webkit.WebChromeClient;
[CtImportImpl]import java.util.regex.Pattern;
[CtUnresolvedImport]import androidx.annotation.CheckResult;
[CtUnresolvedImport]import android.text.SpannableString;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.util.concurrent.locks.Lock;
[CtUnresolvedImport]import android.text.TextUtils;
[CtUnresolvedImport]import android.content.SharedPreferences;
[CtUnresolvedImport]import android.util.TypedValue;
[CtUnresolvedImport]import android.content.res.Configuration;
[CtUnresolvedImport]import com.ichi2.anim.ActivityTransitionAnimation;
[CtUnresolvedImport]import android.widget.EditText;
[CtUnresolvedImport]import android.widget.LinearLayout;
[CtUnresolvedImport]import com.ichi2.themes.HtmlColors;
[CtUnresolvedImport]import androidx.core.view.GestureDetectorCompat;
[CtImportImpl]import java.util.LinkedHashSet;
[CtUnresolvedImport]import android.view.WindowManager;
[CtUnresolvedImport]import android.text.style.UnderlineSpan;
[CtImportImpl]import java.util.concurrent.locks.ReadWriteLock;
[CtUnresolvedImport]import android.webkit.WebViewClient;
[CtUnresolvedImport]import android.view.View;
[CtUnresolvedImport]import android.widget.Button;
[CtUnresolvedImport]import com.ichi2.async.TaskData;
[CtUnresolvedImport]import android.webkit.WebView;
[CtImportImpl]import java.util.HashSet;
[CtImportImpl]import java.io.UnsupportedEncodingException;
[CtUnresolvedImport]import android.view.GestureDetector.SimpleOnGestureListener;
[CtUnresolvedImport]import android.content.BroadcastReceiver;
[CtUnresolvedImport]import android.view.ViewGroup;
[CtUnresolvedImport]import timber.log.Timber;
[CtUnresolvedImport]import android.widget.TextView;
[CtImportImpl]import java.io.FileOutputStream;
[CtUnresolvedImport]import android.content.ActivityNotFoundException;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import android.widget.RelativeLayout;
[CtImportImpl]import java.net.URLDecoder;
[CtUnresolvedImport]import com.ichi2.anki.multimediacard.AudioView;
[CtUnresolvedImport]import com.afollestad.materialdialogs.MaterialDialog;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import android.view.inputmethod.EditorInfo;
[CtUnresolvedImport]import com.ichi2.libanki.Note;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.text.Spanned;
[CtUnresolvedImport]import android.view.KeyEvent;
[CtImportImpl]import java.util.concurrent.locks.ReentrantReadWriteLock;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import com.ichi2.anki.reviewer.ReviewerCustomFonts;
[CtUnresolvedImport]import com.ichi2.libanki.Collection;
[CtUnresolvedImport]import android.view.inputmethod.InputMethodManager;
[CtImportImpl]import java.lang.ref.WeakReference;
[CtUnresolvedImport]import android.annotation.SuppressLint;
[CtUnresolvedImport]import com.ichi2.utils.JSONArray;
[CtUnresolvedImport]import android.view.MotionEvent;
[CtUnresolvedImport]import android.os.Build;
[CtUnresolvedImport]import com.afollestad.materialdialogs.util.TypefaceHelper;
[CtUnresolvedImport]import com.ichi2.libanki.Sound;
[CtUnresolvedImport]import com.ichi2.libanki.Utils;
[CtUnresolvedImport]import com.ichi2.utils.FunctionalInterfaces.Consumer;
[CtUnresolvedImport]import com.ichi2.utils.FunctionalInterfaces.Function;
[CtUnresolvedImport]import com.ichi2.anki.reviewer.CardMarker.FlagDef;
[CtUnresolvedImport]import android.view.LayoutInflater;
[CtUnresolvedImport]import com.ichi2.anki.reviewer.ReviewerUi;
[CtUnresolvedImport]import com.ichi2.async.CollectionTask;
[CtUnresolvedImport]import android.widget.Chronometer;
[CtUnresolvedImport]import com.ichi2.themes.Themes;
[CtUnresolvedImport]import com.ichi2.libanki.template.Template;
[CtUnresolvedImport]import android.graphics.Color;
[CtUnresolvedImport]import static com.ichi2.async.CollectionTask.TASK_TYPE.*;
[CtUnresolvedImport]import static com.ichi2.anki.reviewer.CardMarker.*;
[CtUnresolvedImport]import com.ichi2.utils.AdaptionUtil;
[CtUnresolvedImport]import static com.ichi2.anki.cardviewer.ViewerCommand.*;
[CtUnresolvedImport]import com.ichi2.libanki.Card;
[CtUnresolvedImport]import android.app.Activity;
[CtUnresolvedImport]import androidx.annotation.VisibleForTesting;
[CtUnresolvedImport]import android.content.res.Resources;
[CtUnresolvedImport]import android.view.View.OnClickListener;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtNewArrayImpl]{ [CtLiteralImpl]"PMD.AvoidThrowingRawExceptionTypes", [CtLiteralImpl]"PMD.FieldDeclarationsShouldBeAtStartOfClass" })
public abstract class AbstractFlashcardViewer extends [CtTypeReferenceImpl]NavigationDrawerActivity implements [CtTypeReferenceImpl]com.ichi2.anki.reviewer.ReviewerUi , [CtTypeReferenceImpl]CommandProcessor {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Result codes that are returned when this activity finishes.
     */
    public static final [CtTypeReferenceImpl]int RESULT_DEFAULT = [CtLiteralImpl]50;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int RESULT_NO_MORE_CARDS = [CtLiteralImpl]52;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Available options performed by other activities.
     */
    public static final [CtTypeReferenceImpl]int EDIT_CURRENT_CARD = [CtLiteralImpl]0;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int DECK_OPTIONS = [CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int EASE_1 = [CtLiteralImpl]1;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int EASE_2 = [CtLiteralImpl]2;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int EASE_3 = [CtLiteralImpl]3;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int EASE_4 = [CtLiteralImpl]4;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Maximum time in milliseconds to wait before accepting answer button presses.
     */
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    protected static final [CtTypeReferenceImpl]int DOUBLE_TAP_IGNORE_THRESHOLD = [CtLiteralImpl]200;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Time to wait in milliseconds before resuming fullscreen mode *
     */
    protected static final [CtTypeReferenceImpl]int INITIAL_HIDE_DELAY = [CtLiteralImpl]200;

    [CtFieldImpl][CtCommentImpl]// Type answer patterns
    private static final [CtTypeReferenceImpl]java.util.regex.Pattern sTypeAnsPat = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtLiteralImpl]"\\[\\[type:(.+?)\\]\\]");

    [CtFieldImpl][CtJavaDocImpl]/**
     * to be sent to and from the card editor
     */
    private static [CtTypeReferenceImpl]com.ichi2.libanki.Card sEditorCard;

    [CtFieldImpl]protected static [CtTypeReferenceImpl]boolean sDisplayAnswer = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mTtsInitialized = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mReplayOnTtsInit = [CtLiteralImpl]false;

    [CtFieldImpl]protected static final [CtTypeReferenceImpl]int MENU_DISABLED = [CtLiteralImpl]3;

    [CtFieldImpl][CtCommentImpl]// TODO: Consider extracting to ViewModel
    [CtCommentImpl]// Card counts
    private [CtTypeReferenceImpl]android.text.SpannableString newCount;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.text.SpannableString lrnCount;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.text.SpannableString revCount;

    [CtFieldImpl][CtCommentImpl]// ETA
    private [CtTypeReferenceImpl]int eta;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean isInFullscreen;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Broadcast that informs us when the sd card is about to be unmounted
     */
    private [CtTypeReferenceImpl]android.content.BroadcastReceiver mUnmountReceiver = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Variables to hold preferences
     */
    private [CtTypeReferenceImpl]com.ichi2.anki.cardviewer.CardAppearance mCardAppearance;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mPrefHideDueCount;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mPrefShowETA;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mPrefShowTopbar;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mShowTimer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mPrefWhiteboard;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mPrefFullscreenReview;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mRelativeButtonSize;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mDoubleScrolling;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mScrollingButtons;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mGesturesEnabled;

    [CtFieldImpl][CtCommentImpl]// Android WebView
    protected [CtTypeReferenceImpl]boolean mSpeakText;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mDisableClipboard = [CtLiteralImpl]false;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mOptUseGeneralTimerSettings;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mUseTimer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mWaitAnswerSecond;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mWaitQuestionSecond;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mPrefUseTimer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mOptUseTimer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mOptWaitAnswerSecond;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mOptWaitQuestionSecond;

    [CtFieldImpl]protected [CtTypeReferenceImpl]boolean mUseInputTag;

    [CtFieldImpl][CtCommentImpl]// Preferences from the collection
    private [CtTypeReferenceImpl]boolean mShowNextReviewTime;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mShowRemainingCardCount;

    [CtFieldImpl][CtCommentImpl]// Answer card & cloze deletion variables
    private [CtTypeReferenceImpl]java.lang.String mTypeCorrect = [CtLiteralImpl]null;

    [CtFieldImpl][CtCommentImpl]// The correct answer in the compare to field if answer should be given by learner. Null if no answer is expected.
    private [CtTypeReferenceImpl]java.lang.String mTypeInput = [CtLiteralImpl]"";[CtCommentImpl]// What the learner actually typed


    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mTypeFont = [CtLiteralImpl]"";[CtCommentImpl]// Font face of the compare to field


    [CtFieldImpl]private [CtTypeReferenceImpl]int mTypeSize = [CtLiteralImpl]0;[CtCommentImpl]// Its font size


    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mTypeWarning;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mIsSelecting = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mTouchStarted = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mInAnswer = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mAnswerSoundsAdded = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mCardTemplate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Variables to hold layout objects that we need to update or handle events for
     */
    private [CtTypeReferenceImpl]android.view.View mLookUpIcon;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.webkit.WebView mCard;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.FrameLayout mCardFrame;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.FrameLayout mTouchLayer;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mTextBarNew;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mTextBarLearn;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mTextBarReview;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.TextView mChosenAnswer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mNext1;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mNext2;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mNext3;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mNext4;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.EditText mAnswerField;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mEase1;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mEase2;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mEase3;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.TextView mEase4;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.LinearLayout mFlipCardLayout;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.LinearLayout mEase1Layout;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.LinearLayout mEase2Layout;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.LinearLayout mEase3Layout;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.LinearLayout mEase4Layout;

    [CtFieldImpl]protected [CtTypeReferenceImpl]android.widget.RelativeLayout mTopBarLayout;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.widget.Chronometer mCardTimer;

    [CtFieldImpl]protected [CtTypeReferenceImpl]com.ichi2.anki.Whiteboard mWhiteboard;

    [CtFieldImpl][CtCommentImpl]// Tracked separately as #5023 on github
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    private [CtTypeReferenceImpl]android.text.ClipboardManager mClipboard;

    [CtFieldImpl]protected [CtTypeReferenceImpl]com.ichi2.libanki.Card mCurrentCard;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mCurrentEase;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mButtonHeightSet = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mConfigurationChanged = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mShowChosenAnswerLength = [CtLiteralImpl]2000;

    [CtFieldImpl][CtJavaDocImpl]/**
     * A record of the last time the "show answer" or ease buttons were pressed. We keep track
     * of this time to ignore accidental button presses.
     */
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    protected [CtTypeReferenceImpl]long mLastClickTime;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Swipe Detection
     */
    private [CtTypeReferenceImpl]androidx.core.view.GestureDetectorCompat gestureDetector;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.MyGestureDetector mGestureDetectorImpl;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mLinkOverridesTouchGesture;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mIsXScrolling = [CtLiteralImpl]false;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean mIsYScrolling = [CtLiteralImpl]false;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Gesture Allocation
     */
    private [CtTypeReferenceImpl]int mGestureSwipeUp;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureSwipeDown;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureSwipeLeft;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureSwipeRight;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureDoubleTap;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureTapLeft;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureTapRight;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureTapTop;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureTapBottom;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureLongclick;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureVolumeUp;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mGestureVolumeDown;

    [CtFieldImpl]private [CtTypeReferenceImpl]android.text.Spanned mCardContent;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String mBaseUrl;

    [CtFieldImpl]private [CtTypeReferenceImpl]int mFadeDuration = [CtLiteralImpl]300;

    [CtFieldImpl]protected [CtTypeReferenceImpl]com.ichi2.libanki.sched.AbstractSched mSched;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.ichi2.libanki.Sound mSoundPlayer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.libanki.Sound();

    [CtFieldImpl]private [CtTypeReferenceImpl]long mUseTimerDynamicMS;

    [CtFieldImpl][CtJavaDocImpl]/**
     * File of the temporary mic record *
     */
    protected [CtTypeReferenceImpl]com.ichi2.anki.multimediacard.AudioView mMicToolBar;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.lang.String mTempAudioPath;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Last card that the WebView Renderer crashed on.
     * If we get 2 crashes on the same card, then we likely have an infinite loop and want to exit gracefully.
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    private [CtTypeReferenceImpl]java.lang.Long lastCrashingCardId = [CtLiteralImpl]null;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Reference to the parent of the cardFrame to allow regeneration of the cardFrame in case of crash
     */
    private [CtTypeReferenceImpl]android.view.ViewGroup mCardFrameParent;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Lock to allow thread-safe regeneration of mCard
     */
    private [CtTypeReferenceImpl]java.util.concurrent.locks.ReadWriteLock mCardLock = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.locks.ReentrantReadWriteLock();

    [CtFieldImpl][CtJavaDocImpl]/**
     * whether controls are currently blocked, and how long we expect them to be
     */
    private [CtTypeReferenceImpl]ReviewerUi.ControlBlock mControlBlocked = [CtFieldReadImpl]ControlBlock.SLOW;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Handle Mark/Flag state of cards
     */
    private [CtTypeReferenceImpl]com.ichi2.anki.reviewer.CardMarker mCardMarker;

    [CtFieldImpl][CtCommentImpl]// private int zEase;
    [CtCommentImpl]// ----------------------------------------------------------------------------
    [CtCommentImpl]// LISTENERS
    [CtCommentImpl]// ----------------------------------------------------------------------------
    private [CtTypeReferenceImpl]android.os.Handler mHandler = [CtNewClassImpl]new [CtTypeReferenceImpl]android.os.Handler()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void handleMessage([CtParameterImpl][CtTypeReferenceImpl]android.os.Message msg) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.stopSounds();
            [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.playSound([CtFieldReadImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]msg.obj)), [CtLiteralImpl]null);
        }
    };

    [CtFieldImpl]private final [CtTypeReferenceImpl]android.os.Handler longClickHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler();

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Runnable longClickTestRunnable = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: onEmulatedLongClick");
            [CtIfImpl][CtCommentImpl]// Show hint about lookup function if dictionary available
            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mDisableClipboard) && [CtInvocationImpl][CtTypeAccessImpl]Lookup.isAvailable()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lookupHint = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.lookup_hint);
                [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtVariableReadImpl]lookupHint, [CtLiteralImpl]false);
            }
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().vibrate([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getInstance().getApplicationContext(), [CtLiteralImpl]50);
            [CtInvocationImpl][CtFieldReadImpl]longClickHandler.postDelayed([CtFieldReadImpl]startLongClickAction, [CtLiteralImpl]300);
        }
    };

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Runnable startLongClickAction = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureLongclick);
        }
    };

    [CtFieldImpl][CtCommentImpl]// Handler for the "show answer" button
    private [CtTypeReferenceImpl]android.view.View.OnClickListener mFlipCardListener = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: Show answer button pressed");
            [CtIfImpl][CtCommentImpl]// Ignore what is most likely an accidental double-tap.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime() - [CtFieldReadImpl]mLastClickTime) < [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.DOUBLE_TAP_IGNORE_THRESHOLD) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]mLastClickTime = [CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime();
            [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowAnswerTask);
            [CtInvocationImpl]displayCardAnswer();
        }
    };

    [CtFieldImpl]private [CtTypeReferenceImpl]android.view.View.OnClickListener mSelectEaseHandler = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View view) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Ignore what is most likely an accidental double-tap.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime() - [CtFieldReadImpl]mLastClickTime) < [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.DOUBLE_TAP_IGNORE_THRESHOLD) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]mLastClickTime = [CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime();
            [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowQuestionTask);
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]view.getId()) {
                [CtCaseImpl]case [CtFieldReadImpl]R.id.flashcard_layout_ease1 :
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: EASE_1 pressed");
                    [CtInvocationImpl]answerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]R.id.flashcard_layout_ease2 :
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: EASE_2 pressed");
                    [CtInvocationImpl]answerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]R.id.flashcard_layout_ease3 :
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: EASE_3 pressed");
                    [CtInvocationImpl]answerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3);
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]R.id.flashcard_layout_ease4 :
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: EASE_4 pressed");
                    [CtInvocationImpl]answerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4);
                    [CtBreakImpl]break;
                [CtCaseImpl]default :
                    [CtAssignmentImpl][CtFieldWriteImpl]mCurrentEase = [CtLiteralImpl]0;
                    [CtBreakImpl]break;
            }
        }
    };

    [CtFieldImpl]private [CtTypeReferenceImpl]View.OnTouchListener mGestureListener = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnTouchListener()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onTouch([CtParameterImpl][CtTypeReferenceImpl]android.view.View v, [CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]gestureDetector.onTouchEvent([CtVariableReadImpl]event)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mDisableClipboard) [CtBlockImpl]{
                [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getAction()) {
                    [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_DOWN :
                        [CtAssignmentImpl][CtFieldWriteImpl]mTouchStarted = [CtLiteralImpl]true;
                        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.postDelayed([CtFieldReadImpl]longClickTestRunnable, [CtLiteralImpl]800);
                        [CtBreakImpl]break;
                    [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_UP :
                    [CtCaseImpl]case [CtFieldReadImpl]android.view.MotionEvent.ACTION_MOVE :
                        [CtIfImpl]if ([CtFieldReadImpl]mTouchStarted) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]longClickTestRunnable);
                            [CtAssignmentImpl][CtFieldWriteImpl]mTouchStarted = [CtLiteralImpl]false;
                        }
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]longClickTestRunnable);
                        [CtAssignmentImpl][CtFieldWriteImpl]mTouchStarted = [CtLiteralImpl]false;
                        [CtBreakImpl]break;
                }
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]mGestureDetectorImpl.eventCanBeSentToWebView([CtVariableReadImpl]event)) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtInvocationImpl][CtCommentImpl]// Gesture listener is added before mCard is set
            processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]card == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtReturnImpl]return;

                [CtInvocationImpl][CtVariableReadImpl]card.dispatchTouchEvent([CtVariableReadImpl]event);
            });
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
    };

    [CtMethodImpl][CtCommentImpl]// This is intentionally package-private as it removes the need for synthetic accessors
    [CtAnnotationImpl]@android.annotation.SuppressLint([CtLiteralImpl]"CheckResult")
    [CtTypeReferenceImpl]void processCardAction([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.utils.FunctionalInterfaces.Consumer<[CtTypeReferenceImpl]android.webkit.WebView> cardConsumer) [CtBlockImpl]{
        [CtInvocationImpl]processCardFunction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]cardConsumer.consume([CtVariableReadImpl]card);
            [CtReturnImpl]return [CtLiteralImpl]true;
        });
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.CheckResult
    private <[CtTypeParameterImpl]T> [CtTypeParameterReferenceImpl]T processCardFunction([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.utils.FunctionalInterfaces.Function<[CtTypeReferenceImpl]android.webkit.WebView, [CtTypeParameterReferenceImpl]T> cardFunction) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.locks.Lock readLock = [CtInvocationImpl][CtFieldReadImpl]mCardLock.readLock();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]readLock.lock();
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]cardFunction.apply([CtFieldReadImpl]mCard);
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]readLock.unlock();
        }
    }

    [CtFieldImpl]protected final [CtTypeReferenceImpl]CollectionTask.TaskListener mDismissCardHandler = [CtNewClassImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.NextCardHandler()[CtClassImpl] {};

    [CtFieldImpl]private final [CtTypeReferenceImpl]CollectionTask.TaskListener mUpdateCardHandler = [CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.ichi2.async.CollectionTask.TaskListener()[CtClassImpl] {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean mNoMoreCards;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onPreExecute() [CtBlockImpl]{
            [CtInvocationImpl]showProgressBar();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onProgressUpdate([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.async.TaskData value) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean cardChanged = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard != [CtInvocationImpl][CtVariableReadImpl]value.getCard()) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]/* Before updating mCurrentCard, we check whether it is changing or not. If the current card changes,
                then we need to display it as a new card, without showing the answer.
                 */
                [CtFieldWriteImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer = [CtLiteralImpl]false;
                [CtAssignmentImpl][CtVariableWriteImpl]cardChanged = [CtLiteralImpl]true;[CtCommentImpl]// Keep track of that so we can run a bit of new-card code

            }
            [CtAssignmentImpl][CtFieldWriteImpl]mCurrentCard = [CtInvocationImpl][CtVariableReadImpl]value.getCard();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// If the card is null means that there are no more cards scheduled for review.
                [CtFieldWriteImpl]mNoMoreCards = [CtLiteralImpl]true;
                [CtInvocationImpl]showProgressBar();
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mPrefWhiteboard && [CtBinaryOperatorImpl]([CtFieldReadImpl]mWhiteboard != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mWhiteboard.clear();
            }
            [CtIfImpl]if ([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.resetSounds();[CtCommentImpl]// load sounds from scratch, to expose any edit changes

                [CtAssignmentImpl][CtFieldWriteImpl]mAnswerSoundsAdded = [CtLiteralImpl]false;[CtCommentImpl]// causes answer sounds to be reloaded

                [CtInvocationImpl]generateQuestionSoundList();[CtCommentImpl]// questions must be intentionally regenerated

                [CtInvocationImpl]displayCardAnswer();
            } else [CtBlockImpl]{
                [CtIfImpl]if ([CtVariableReadImpl]cardChanged) [CtBlockImpl]{
                    [CtInvocationImpl]updateTypeAnswerInfo();
                }
                [CtInvocationImpl]displayCardQuestion();
                [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.startTimer();
                [CtInvocationImpl]initTimer();
            }
            [CtInvocationImpl]hideProgressBar();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onPostExecute([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.async.TaskData result) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]result.getBoolean()) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// RuntimeException occurred on update cards
                closeReviewer([CtTypeAccessImpl]DeckPicker.RESULT_DB_ERROR, [CtLiteralImpl]false);
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtFieldReadImpl]mNoMoreCards) [CtBlockImpl]{
                [CtInvocationImpl]closeReviewer([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.RESULT_NO_MORE_CARDS, [CtLiteralImpl]true);
            }
        }
    };

    [CtClassImpl]abstract class NextCardHandler extends [CtTypeReferenceImpl][CtTypeReferenceImpl]com.ichi2.async.CollectionTask.TaskListener {
        [CtFieldImpl]private [CtTypeReferenceImpl]boolean mNoMoreCards;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onPreExecute() [CtBlockImpl]{
            [CtCommentImpl]/* do nothing */
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onProgressUpdate([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.async.TaskData value) [CtBlockImpl]{
            [CtInvocationImpl]displayNext([CtInvocationImpl][CtVariableReadImpl]value.getCard());
        }

        [CtMethodImpl]protected [CtTypeReferenceImpl]void displayNext([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Card nextCard) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.res.Resources res = [CtInvocationImpl]getResources();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mSched == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// TODO: proper testing for restored activity
                finishWithoutAnimation();
                [CtReturnImpl]return;
            }
            [CtAssignmentImpl][CtFieldWriteImpl]mCurrentCard = [CtVariableReadImpl]nextCard;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// If the card is null means that there are no more cards scheduled for review.
                [CtFieldWriteImpl]mNoMoreCards = [CtLiteralImpl]true;[CtCommentImpl]// other handlers use this, toggle state every time through

            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mNoMoreCards = [CtLiteralImpl]false;[CtCommentImpl]// other handlers use this, toggle state every time through

                [CtInvocationImpl][CtCommentImpl]// Start reviewing next card
                updateTypeAnswerInfo();
                [CtInvocationImpl]hideProgressBar();
                [CtInvocationImpl][CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this.unblockControls();
                [CtInvocationImpl][CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this.displayCardQuestion();
            }
            [CtIfImpl][CtCommentImpl]// Since reps are incremented on fetch of next card, we will miss counting the
            [CtCommentImpl]// last rep since there isn't a next card. We manually account for it here.
            if ([CtFieldReadImpl]mNoMoreCards) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mSched.setReps([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mSched.getReps() + [CtLiteralImpl]1);
            }
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Long[] elapsed = [CtInvocationImpl][CtInvocationImpl]getCol().timeboxReached();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elapsed != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// AnkiDroid is always counting one rep ahead, so we decrement it before displaying
                [CtCommentImpl]// it to the user.
                [CtTypeReferenceImpl]int nCards = [CtBinaryOperatorImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]elapsed[[CtLiteralImpl]1].intValue() - [CtLiteralImpl]1;
                [CtLocalVariableImpl][CtTypeReferenceImpl]int nMins = [CtBinaryOperatorImpl][CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]elapsed[[CtLiteralImpl]0].intValue() / [CtLiteralImpl]60;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mins = [CtInvocationImpl][CtVariableReadImpl]res.getQuantityString([CtTypeAccessImpl]R.plurals.in_minutes, [CtVariableReadImpl]nMins, [CtVariableReadImpl]nMins);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String timeboxMessage = [CtInvocationImpl][CtVariableReadImpl]res.getQuantityString([CtTypeAccessImpl]R.plurals.timebox_reached, [CtVariableReadImpl]nCards, [CtVariableReadImpl]nCards, [CtVariableReadImpl]mins);
                [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtVariableReadImpl]timeboxMessage, [CtLiteralImpl]true);
                [CtInvocationImpl][CtInvocationImpl]getCol().startTimebox();
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onPostExecute([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.async.TaskData result) [CtBlockImpl]{
            [CtInvocationImpl]postNextCardDisplay([CtInvocationImpl][CtVariableReadImpl]result.getBoolean());
        }

        [CtMethodImpl]protected [CtTypeReferenceImpl]void postNextCardDisplay([CtParameterImpl][CtTypeReferenceImpl]boolean displaySuccess) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]displaySuccess) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// RuntimeException occurred on answering cards
                closeReviewer([CtTypeAccessImpl]DeckPicker.RESULT_DB_ERROR, [CtLiteralImpl]false);
                [CtReturnImpl]return;
            }
            [CtIfImpl][CtCommentImpl]// Check for no more cards before session complete. If they are both true, no more cards will take
            [CtCommentImpl]// precedence when returning to study options.
            if ([CtFieldReadImpl]mNoMoreCards) [CtBlockImpl]{
                [CtInvocationImpl]closeReviewer([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.RESULT_NO_MORE_CARDS, [CtLiteralImpl]true);
            }
            [CtInvocationImpl][CtCommentImpl]// set the correct mark/unmark icon on action bar
            refreshActionBar();
            [CtInvocationImpl][CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.root_layout).requestFocus();
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]CollectionTask.TaskListener mAnswerCardHandler([CtParameterImpl][CtTypeReferenceImpl]boolean quick) [CtBlockImpl]{
        [CtReturnImpl]return [CtNewClassImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.NextCardHandler()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onPreExecute() [CtBlockImpl]{
                [CtInvocationImpl]blockControls([CtVariableReadImpl]quick);
            }
        };
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Extract type answer/cloze text and font/size
     */
    private [CtTypeReferenceImpl]void updateTypeAnswerInfo() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mTypeCorrect = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]mTypeInput = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String q = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.q([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher m = [CtInvocationImpl][CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sTypeAnsPat.matcher([CtVariableReadImpl]q);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int clozeIdx = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]m.find()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String fld = [CtInvocationImpl][CtVariableReadImpl]m.group([CtLiteralImpl]1);
        [CtIfImpl][CtCommentImpl]// if it's a cloze, extract data
        if ([CtInvocationImpl][CtVariableReadImpl]fld.startsWith([CtLiteralImpl]"cloze:", [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// get field and cloze position
            [CtVariableWriteImpl]clozeIdx = [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getOrd() + [CtLiteralImpl]1;
            [CtAssignmentImpl][CtVariableWriteImpl]fld = [CtArrayReadImpl][CtInvocationImpl][CtVariableReadImpl]fld.split([CtLiteralImpl]":")[[CtLiteralImpl]1];
        }
        [CtLocalVariableImpl][CtCommentImpl]// loop through fields for a match
        [CtTypeReferenceImpl]com.ichi2.utils.JSONArray ja = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.model().getJSONArray([CtLiteralImpl]"flds");
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]ja.length(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]ja.getJSONObject([CtVariableReadImpl]i).get([CtLiteralImpl]"name")));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]name.equals([CtVariableReadImpl]fld)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mTypeCorrect = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.note().getItem([CtVariableReadImpl]name);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clozeIdx != [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// narrow to cloze
                    [CtFieldWriteImpl]mTypeCorrect = [CtInvocationImpl]contentForCloze([CtFieldReadImpl]mTypeCorrect, [CtVariableReadImpl]clozeIdx);
                }
                [CtAssignmentImpl][CtFieldWriteImpl]mTypeFont = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]ja.getJSONObject([CtVariableReadImpl]i).get([CtLiteralImpl]"font")));
                [CtAssignmentImpl][CtFieldWriteImpl]mTypeSize = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtInvocationImpl][CtVariableReadImpl]ja.getJSONObject([CtVariableReadImpl]i).get([CtLiteralImpl]"size")));
                [CtBreakImpl]break;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mTypeCorrect == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]clozeIdx != [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mTypeWarning = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.empty_card_warning);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mTypeWarning = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.unknown_type_field_warning, [CtVariableReadImpl]fld);
            }
        } else [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"".equals([CtFieldReadImpl]mTypeCorrect)) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mTypeCorrect = [CtLiteralImpl]null;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mTypeWarning = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Format question field when it contains typeAnswer or clozes. If there was an error during type text extraction, a
     * warning is displayed
     *
     * @param buf
     * 		The question text
     * @return The formatted question text
     */
    private [CtTypeReferenceImpl]java.lang.String typeAnsQuestionFilter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String buf) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher m = [CtInvocationImpl][CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sTypeAnsPat.matcher([CtVariableReadImpl]buf);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mTypeWarning != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]m.replaceFirst([CtFieldReadImpl]mTypeWarning);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl]if ([CtFieldReadImpl]mUseInputTag) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// These functions are defined in the JavaScript file assets/scripts/card.js. We get the text back in
            [CtCommentImpl]// shouldOverrideUrlLoading() in createWebView() in this file.
            [CtVariableReadImpl]sb.append([CtBinaryOperatorImpl][CtLiteralImpl]"<center>\n<input type=\"text\" name=\"typed\" id=\"typeans\" onfocus=\"taFocus();\" " + [CtLiteralImpl]"onblur=\"taBlur(this);\" onKeyPress=\"return taKey(this, event)\" autocomplete=\"off\" ");
            [CtIfImpl][CtCommentImpl]// We have to watch out. For the preview we don’t know the font or font size. Skip those there. (Anki
            [CtCommentImpl]// desktop just doesn’t show the input tag there. Do it with standard values here instead.)
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]mTypeFont != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]android.text.TextUtils.isEmpty([CtFieldReadImpl]mTypeFont))) && [CtBinaryOperatorImpl]([CtFieldReadImpl]mTypeSize > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"style=\"font-family: \'").append([CtFieldReadImpl]mTypeFont).append([CtLiteralImpl]"'; font-size: ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.toString([CtFieldReadImpl]mTypeSize)).append([CtLiteralImpl]"px;\" ");
            }
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]">\n</center>\n");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"<span id=\"typeans\" class=\"typePrompt");
            [CtIfImpl]if ([CtFieldReadImpl]mUseInputTag) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]" typeOff");
            }
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\">........</span>");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]m.replaceAll([CtInvocationImpl][CtVariableReadImpl]sb.toString());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fill the placeholder for the type comparison. Show the correct answer, and the comparison if appropriate.
     *
     * @param buf
     * 		The answer text
     * @param userAnswer
     * 		Text typed by the user, or empty.
     * @param correctAnswer
     * 		The correct answer, taken from the note.
     * @return The formatted answer text
     */
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    [CtTypeReferenceImpl]java.lang.String typeAnsAnswerFilter([CtParameterImpl][CtTypeReferenceImpl]java.lang.String buf, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userAnswer, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String correctAnswer) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher m = [CtInvocationImpl][CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sTypeAnsPat.matcher([CtVariableReadImpl]buf);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.utils.DiffEngine diffEngine = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.utils.DiffEngine();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"<div><code id=\"typeans\">");
        [CtIfImpl][CtCommentImpl]// We have to use Matcher.quoteReplacement because the inputs here might have $ or \.
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]android.text.TextUtils.isEmpty([CtVariableReadImpl]userAnswer)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// The user did type something.
            if ([CtInvocationImpl][CtVariableReadImpl]userAnswer.equals([CtVariableReadImpl]correctAnswer)) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// and it was right.
                [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Matcher.quoteReplacement([CtInvocationImpl][CtTypeAccessImpl]com.ichi2.utils.DiffEngine.wrapGood([CtVariableReadImpl]correctAnswer)));
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"<span id=\"typecheckmark\">✔</span>");[CtCommentImpl]// Heavy check mark

            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Answer not correct.
                [CtCommentImpl]// Only use the complex diff code when needed, that is when we have some typed text that is not
                [CtCommentImpl]// exactly the same as the correct text.
                [CtArrayTypeReferenceImpl]java.lang.String[] diffedStrings = [CtInvocationImpl][CtVariableReadImpl]diffEngine.diffedHtmlStrings([CtVariableReadImpl]correctAnswer, [CtVariableReadImpl]userAnswer);
                [CtInvocationImpl][CtCommentImpl]// We know we get back two strings.
                [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Matcher.quoteReplacement([CtArrayReadImpl][CtVariableReadImpl]diffedStrings[[CtLiteralImpl]0]));
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"<br><span id=\"typearrow\">&darr;</span><br>");
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Matcher.quoteReplacement([CtArrayReadImpl][CtVariableReadImpl]diffedStrings[[CtLiteralImpl]1]));
            }
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mUseInputTag) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Matcher.quoteReplacement([CtInvocationImpl][CtTypeAccessImpl]com.ichi2.utils.DiffEngine.wrapMissing([CtVariableReadImpl]correctAnswer)));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Matcher.quoteReplacement([CtVariableReadImpl]correctAnswer));
        }
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"</code></div>");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]m.replaceAll([CtInvocationImpl][CtVariableReadImpl]sb.toString());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the correct answer to use for {{type::cloze::NN}} fields.
     *
     * @param txt
     * 		The field text with the clozes
     * @param idx
     * 		The index of the cloze to use
     * @return A string with a comma-separeted list of unique cloze strings with the corret index.
     */
    private [CtTypeReferenceImpl]java.lang.String contentForCloze([CtParameterImpl][CtTypeReferenceImpl]java.lang.String txt, [CtParameterImpl][CtTypeReferenceImpl]int idx) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Pattern re = [CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.compile([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\\{\\{c" + [CtVariableReadImpl]idx) + [CtLiteralImpl]"::(.+?)\\}\\}");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher m = [CtInvocationImpl][CtVariableReadImpl]re.matcher([CtVariableReadImpl]txt);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> matches = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashSet<>();
        [CtLocalVariableImpl][CtCommentImpl]// LinkedHashSet: make entries appear only once, like Anki desktop (see also issue #2208), and keep the order
        [CtCommentImpl]// they appear in.
        [CtTypeReferenceImpl]java.lang.String groupOne;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int colonColonIndex = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]m.find()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]groupOne = [CtInvocationImpl][CtVariableReadImpl]m.group([CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]colonColonIndex = [CtInvocationImpl][CtVariableReadImpl]groupOne.indexOf([CtLiteralImpl]"::");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]colonColonIndex > [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Cut out the hint.
                [CtVariableWriteImpl]groupOne = [CtInvocationImpl][CtVariableReadImpl]groupOne.substring([CtLiteralImpl]0, [CtVariableReadImpl]colonColonIndex);
            }
            [CtInvocationImpl][CtVariableReadImpl]matches.add([CtVariableReadImpl]groupOne);
        } 
        [CtLocalVariableImpl][CtCommentImpl]// Now do what the pythonic ", ".join(matches) does in a tricky way
        [CtTypeReferenceImpl]java.lang.String prefix = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder resultBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String match : [CtVariableReadImpl]matches) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]resultBuilder.append([CtVariableReadImpl]prefix);
            [CtInvocationImpl][CtVariableReadImpl]resultBuilder.append([CtVariableReadImpl]match);
            [CtAssignmentImpl][CtVariableWriteImpl]prefix = [CtLiteralImpl]", ";
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]resultBuilder.toString();
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]android.os.Handler mTimerHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.Runnable removeChosenAnswerText = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setText([CtLiteralImpl]"");
        }
    };

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mPrefWaitAnswerSecond;

    [CtFieldImpl]protected [CtTypeReferenceImpl]int mPrefWaitQuestionSecond;

    [CtMethodImpl]protected [CtTypeReferenceImpl]int getAnswerButtonCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getSched().answerButtons([CtFieldReadImpl]mCurrentCard);
    }

    [CtMethodImpl][CtCommentImpl]// ----------------------------------------------------------------------------
    [CtCommentImpl]// ANDROID METHODS
    [CtCommentImpl]// ----------------------------------------------------------------------------
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onCreate([CtParameterImpl][CtTypeReferenceImpl]android.os.Bundle savedInstanceState) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"onCreate()");
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.SharedPreferences preferences = [CtInvocationImpl]restorePreferences();
        [CtAssignmentImpl][CtFieldWriteImpl]mCardAppearance = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.CardAppearance.create([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.reviewer.ReviewerCustomFonts([CtInvocationImpl][CtThisAccessImpl]this.getBaseContext()), [CtVariableReadImpl]preferences);
        [CtInvocationImpl][CtSuperAccessImpl]super.onCreate([CtVariableReadImpl]savedInstanceState);
        [CtInvocationImpl]setContentView([CtInvocationImpl]getContentViewAttr([CtFieldReadImpl]mPrefFullscreenReview));
        [CtInvocationImpl][CtCommentImpl]// Make ACTION_PROCESS_TEXT for in-app searching possible on > Android 4.0
        [CtInvocationImpl]getDelegate().setHandleNativeActionModesEnabled([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.View mainView = [CtInvocationImpl]findViewById([CtTypeAccessImpl]android.R.id.content);
        [CtInvocationImpl]initNavigationDrawer([CtVariableReadImpl]mainView);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]int getContentViewAttr([CtParameterImpl][CtTypeReferenceImpl]int fullscreenMode) [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]R.layout.reviewer;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean isFullscreen() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]isInFullscreen = [CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getSupportActionBar().isShowing();
        [CtReturnImpl]return [CtFieldReadImpl]isInFullscreen;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onConfigurationChanged([CtParameterImpl][CtTypeReferenceImpl]android.content.res.Configuration config) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// called when screen rotated, etc, since recreating the Webview is too expensive
        [CtSuperAccessImpl]super.onConfigurationChanged([CtVariableReadImpl]config);
        [CtInvocationImpl]refreshActionBar();
    }

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]void setTitle();

    [CtMethodImpl][CtCommentImpl]// Finish initializing the activity after the collection has been correctly loaded
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onCollectionLoaded([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Collection col) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onCollectionLoaded([CtVariableReadImpl]col);
        [CtAssignmentImpl][CtFieldWriteImpl]mSched = [CtInvocationImpl][CtVariableReadImpl]col.getSched();
        [CtAssignmentImpl][CtFieldWriteImpl]mBaseUrl = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.getBaseUrl([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]col.getMedia().dir());
        [CtInvocationImpl]registerExternalStorageListener();
        [CtInvocationImpl]restoreCollectionPreferences();
        [CtInvocationImpl]initLayout();
        [CtInvocationImpl]setTitle();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mDisableClipboard) [CtBlockImpl]{
            [CtInvocationImpl]clipboardSetText([CtLiteralImpl]"");
        }
        [CtTryImpl][CtCommentImpl]// Load the template for the card
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mCardTemplate = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.convertStreamToString([CtInvocationImpl][CtInvocationImpl]getAssets().open([CtLiteralImpl]"card_template.html"));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
        }
        [CtIfImpl][CtCommentImpl]// Initialize text-to-speech. This is an asynchronous operation.
        if ([CtFieldReadImpl]mSpeakText) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ReadText.initializeTts([CtThisAccessImpl]this, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.ReadTextListener());
        }
        [CtInvocationImpl][CtCommentImpl]// Initialize dictionary lookup feature
        [CtTypeAccessImpl]Lookup.initialize([CtThisAccessImpl]this);
        [CtInvocationImpl]updateScreenCounts();
        [CtInvocationImpl]supportInvalidateOptionsMenu();
    }

    [CtMethodImpl][CtCommentImpl]// Saves deck each time Reviewer activity loses focus
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onPause() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onPause();
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"onPause()");
        [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowAnswerTask);
        [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowQuestionTask);
        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]longClickTestRunnable);
        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]startLongClickAction);
        [CtInvocationImpl]pauseTimer();
        [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.stopSounds();
        [CtInvocationImpl][CtCommentImpl]// Prevent loss of data in Cookies
        [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().flushWebViewCookies();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onResume() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onResume();
        [CtInvocationImpl]resumeTimer();
        [CtInvocationImpl][CtCommentImpl]// Set the context for the Sound manager
        [CtFieldReadImpl]mSoundPlayer.setContext([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.ref.WeakReference<[CtTypeReferenceImpl]android.app.Activity>([CtThisAccessImpl]this));
        [CtInvocationImpl][CtCommentImpl]// Reset the activity title
        setTitle();
        [CtInvocationImpl]updateScreenCounts();
        [CtInvocationImpl]selectNavigationItem([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onDestroy() [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onDestroy();
        [CtIfImpl][CtCommentImpl]// Tells the scheduler there is no more current cards. 0 is
        [CtCommentImpl]// not a valid id.
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mSched != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mSched.discardCurrentCard();
        }
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"onDestroy()");
        [CtIfImpl]if ([CtFieldReadImpl]mSpeakText) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]ReadText.releaseTts();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mUnmountReceiver != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]unregisterReceiver([CtFieldReadImpl]mUnmountReceiver);
        }
        [CtIfImpl][CtCommentImpl]// WebView.destroy() should be called after the end of use
        [CtCommentImpl]// http://developer.android.com/reference/android/webkit/WebView.html#destroy()
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCardFrame != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCardFrame.removeAllViews();
        }
        [CtInvocationImpl]destroyWebView([CtFieldReadImpl]mCard);[CtCommentImpl]// OK to do without a lock

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onBackPressed() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isDrawerOpen()) [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.onBackPressed();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Back key pressed");
            [CtInvocationImpl]closeReviewer([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.RESULT_DEFAULT, [CtLiteralImpl]false);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onKeyDown([CtParameterImpl][CtTypeReferenceImpl]int keyCode, [CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]processCardFunction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl]processHardwareButtonScroll([CtVariableReadImpl]keyCode, [CtVariableReadImpl]card))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.onKeyDown([CtVariableReadImpl]keyCode, [CtVariableReadImpl]event);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean processHardwareButtonScroll([CtParameterImpl][CtTypeReferenceImpl]int keyCode, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView card) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_PAGE_UP) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
            [CtIfImpl]if ([CtFieldReadImpl]mDoubleScrolling) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_PAGE_DOWN) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
            [CtIfImpl]if ([CtFieldReadImpl]mDoubleScrolling) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mScrollingButtons && [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_PICTSYMBOLS)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
            [CtIfImpl]if ([CtFieldReadImpl]mDoubleScrolling) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mScrollingButtons && [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_SWITCH_CHARSET)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
            [CtIfImpl]if ([CtFieldReadImpl]mDoubleScrolling) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onKeyUp([CtParameterImpl][CtTypeReferenceImpl]int keyCode, [CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]answerFieldIsFocused()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.onKeyUp([CtVariableReadImpl]keyCode, [CtVariableReadImpl]event);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_SPACE) || [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_ENTER)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_NUMPAD_ENTER)) [CtBlockImpl]{
                [CtInvocationImpl]displayCardAnswer();
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.onKeyUp([CtVariableReadImpl]keyCode, [CtVariableReadImpl]event);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean answerFieldIsFocused() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mAnswerField != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]mAnswerField.isFocused();
    }

    [CtMethodImpl][CtCommentImpl]// Tracked separately as #5023 on github
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]boolean clipboardHasText() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mClipboard != [CtLiteralImpl]null) && [CtInvocationImpl][CtFieldReadImpl]mClipboard.hasText();
    }

    [CtMethodImpl][CtCommentImpl]// Tracked separately as #5023 on github
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    private [CtTypeReferenceImpl]void clipboardSetText([CtParameterImpl][CtTypeReferenceImpl]java.lang.CharSequence text) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mClipboard != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mClipboard.setText([CtVariableReadImpl]text);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// https://code.google.com/p/ankidroid/issues/detail?id=1746
                [CtCommentImpl]// https://code.google.com/p/ankidroid/issues/detail?id=1820
                [CtCommentImpl]// Some devices or external applications make the clipboard throw exceptions. If this happens, we
                [CtCommentImpl]// must disable it or AnkiDroid will crash if it tries to use it.
                [CtTypeAccessImpl]timber.log.Timber.e([CtLiteralImpl]"Clipboard error. Disabling text selection setting.");
                [CtAssignmentImpl][CtFieldWriteImpl]mDisableClipboard = [CtLiteralImpl]true;
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the text stored in the clipboard or the empty string if the clipboard is empty or contains something that
     * cannot be convered to text.
     *
     * @return the text in clipboard or the empty string.
     */
    [CtCommentImpl]// Tracked separately as #5023 on github
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    private [CtTypeReferenceImpl]java.lang.CharSequence clipboardGetText() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.CharSequence text = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]mClipboard != [CtLiteralImpl]null) ? [CtInvocationImpl][CtFieldReadImpl]mClipboard.getText() : [CtLiteralImpl]null;
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]text != [CtLiteralImpl]null ? [CtVariableReadImpl]text : [CtLiteralImpl]"";
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onActivityResult([CtParameterImpl][CtTypeReferenceImpl]int requestCode, [CtParameterImpl][CtTypeReferenceImpl]int resultCode, [CtParameterImpl][CtTypeReferenceImpl]android.content.Intent data) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onActivityResult([CtVariableReadImpl]requestCode, [CtVariableReadImpl]resultCode, [CtVariableReadImpl]data);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCode == [CtFieldReadImpl]DeckPicker.RESULT_DB_ERROR) [CtBlockImpl]{
            [CtInvocationImpl]closeReviewer([CtTypeAccessImpl]DeckPicker.RESULT_DB_ERROR, [CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCode == [CtFieldReadImpl]DeckPicker.RESULT_MEDIA_EJECTED) [CtBlockImpl]{
            [CtInvocationImpl]finishNoStorageAvailable();
        }
        [CtIfImpl][CtCommentImpl]/* Reset the schedule and reload the latest card off the top of the stack if required.
        The card could have been rescheduled, the deck could have changed, or a change of
        note type could have lead to the card being deleted
         */
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]data != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]data.hasExtra([CtLiteralImpl]"reloadRequired")) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getSched().deferReset();
            [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]ANSWER_CARD, [CtInvocationImpl]mAnswerCardHandler([CtLiteralImpl]false), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtLiteralImpl]null, [CtLiteralImpl]0));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]requestCode == [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EDIT_CURRENT_CARD) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]resultCode == [CtFieldReadImpl]RESULT_OK) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// content of note was changed so update the note and current card
                [CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: Saving card...");
                [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]UPDATE_NOTE, [CtFieldReadImpl]mUpdateCardHandler, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sEditorCard, [CtLiteralImpl]true));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]resultCode == [CtFieldReadImpl]RESULT_CANCELED) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]data != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]data.hasExtra([CtLiteralImpl]"reloadRequired")))) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// nothing was changed by the note editor so just redraw the card
                redrawCard();
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]requestCode == [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.DECK_OPTIONS) && [CtBinaryOperatorImpl]([CtVariableReadImpl]resultCode == [CtFieldReadImpl]RESULT_OK)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getSched().deferReset();
            [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]ANSWER_CARD, [CtInvocationImpl]mAnswerCardHandler([CtLiteralImpl]false), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtLiteralImpl]null, [CtLiteralImpl]0));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mDisableClipboard) [CtBlockImpl]{
            [CtInvocationImpl]clipboardSetText([CtLiteralImpl]"");
        }
    }

    [CtMethodImpl][CtCommentImpl]// ----------------------------------------------------------------------------
    [CtCommentImpl]// CUSTOM METHODS
    [CtCommentImpl]// ----------------------------------------------------------------------------
    [CtCommentImpl]// Get the did of the parent deck (ignoring any subdecks)
    protected [CtTypeReferenceImpl]long getParentDid() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]long deckID = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().selected();
        [CtReturnImpl]return [CtVariableReadImpl]deckID;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void redrawCard() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// #3654 We can call this from ActivityResult, which could mean that the card content hasn't yet been set
        [CtCommentImpl]// if the activity was destroyed. In this case, just wait until onCollectionLoaded callback succeeds.
        if ([CtInvocationImpl]hasLoadedCardContent()) [CtBlockImpl]{
            [CtInvocationImpl]fillFlashcard();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Skipping card redraw - card still initialising.");
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Whether the callback to onCollectionLoaded has loaded card content
     */
    private [CtTypeReferenceImpl]boolean hasLoadedCardContent() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]mCardContent != [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]androidx.core.view.GestureDetectorCompat getGestureDetector() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]gestureDetector;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Show/dismiss dialog when sd card is ejected/remounted (collection is saved by SdCardReceiver)
     */
    private [CtTypeReferenceImpl]void registerExternalStorageListener() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mUnmountReceiver == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mUnmountReceiver = [CtNewClassImpl]new [CtTypeReferenceImpl]android.content.BroadcastReceiver()[CtClassImpl] {
                [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                public [CtTypeReferenceImpl]void onReceive([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]android.content.Intent intent) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]intent.getAction().equals([CtTypeAccessImpl]SdCardReceiver.MEDIA_EJECT)) [CtBlockImpl]{
                        [CtInvocationImpl]finishWithoutAnimation();
                    }
                }
            };
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.IntentFilter iFilter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.IntentFilter();
            [CtInvocationImpl][CtVariableReadImpl]iFilter.addAction([CtTypeAccessImpl]SdCardReceiver.MEDIA_EJECT);
            [CtInvocationImpl]registerReceiver([CtFieldReadImpl]mUnmountReceiver, [CtVariableReadImpl]iFilter);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void pauseTimer() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.stopTimer();
        }
        [CtIfImpl][CtCommentImpl]// We also stop the UI timer so it doesn't trigger the tick listener while paused. Letting
        [CtCommentImpl]// it run would trigger the time limit condition (red, stopped timer) in the background.
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCardTimer != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCardTimer.stop();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void resumeTimer() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Resume the card timer first. It internally accounts for the time gap between
            [CtCommentImpl]// suspend and resume.
            [CtFieldReadImpl]mCurrentCard.resumeTimer();
            [CtInvocationImpl][CtCommentImpl]// Then update and resume the UI timer. Set the base time as if the timer had started
            [CtCommentImpl]// timeTaken() seconds ago.
            [CtFieldReadImpl]mCardTimer.setBase([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime() - [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.timeTaken());
            [CtIfImpl][CtCommentImpl]// Don't start the timer if we have already reached the time limit or it will tick over
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime() - [CtInvocationImpl][CtFieldReadImpl]mCardTimer.getBase()) < [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.timeLimit()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mCardTimer.start();
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void undo() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]isUndoAvailable()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]UNDO, [CtInvocationImpl]mAnswerCardHandler([CtLiteralImpl]false));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void finishNoStorageAvailable() [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.setResult([CtTypeAccessImpl]DeckPicker.RESULT_MEDIA_EJECTED);
        [CtInvocationImpl]finishWithoutAnimation();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean editCard() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// This should never occurs. It means the review button was pressed while there is no more card in the reviewer.
            return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent editCard = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtThisAccessImpl]this, [CtFieldReadImpl]com.ichi2.anki.NoteEditor.class);
        [CtInvocationImpl][CtVariableReadImpl]editCard.putExtra([CtTypeAccessImpl]NoteEditor.EXTRA_CALLER, [CtTypeAccessImpl]NoteEditor.CALLER_REVIEWER);
        [CtAssignmentImpl][CtFieldWriteImpl]com.ichi2.anki.AbstractFlashcardViewer.sEditorCard = [CtFieldReadImpl]mCurrentCard;
        [CtInvocationImpl]startActivityForResultWithAnimation([CtVariableReadImpl]editCard, [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EDIT_CURRENT_CARD, [CtTypeAccessImpl]ActivityTransitionAnimation.LEFT);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void generateQuestionSoundList() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.addSounds([CtFieldReadImpl]mBaseUrl, [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.qSimple(), [CtTypeAccessImpl]Sound.SOUNDS_QUESTION);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void lookUpOrSelectText() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]clipboardHasText()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtBinaryOperatorImpl][CtLiteralImpl]"Clipboard has text = " + [CtInvocationImpl]clipboardHasText());
            [CtInvocationImpl]lookUp();
        } else [CtBlockImpl]{
            [CtInvocationImpl]selectAndCopyText();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean lookUp() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtAssignmentImpl][CtFieldWriteImpl]mIsSelecting = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Lookup.lookUp([CtInvocationImpl][CtInvocationImpl]clipboardGetText().toString())) [CtBlockImpl]{
            [CtInvocationImpl]clipboardSetText([CtLiteralImpl]"");
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void showLookupButtonIfNeeded() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mDisableClipboard) && [CtBinaryOperatorImpl]([CtFieldReadImpl]mClipboard != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]clipboardGetText().length() != [CtLiteralImpl]0) && [CtInvocationImpl][CtTypeAccessImpl]Lookup.isAvailable()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.getVisibility() != [CtFieldReadImpl]android.view.View.VISIBLE)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtInvocationImpl]enableViewAnimation([CtFieldReadImpl]mLookUpIcon, [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anim.ViewAnimation.fade([CtTypeAccessImpl]ViewAnimation.FADE_IN, [CtFieldReadImpl]mFadeDuration, [CtLiteralImpl]0));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.getVisibility() == [CtFieldReadImpl]android.view.View.VISIBLE) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setVisibility([CtTypeAccessImpl]View.GONE);
                [CtInvocationImpl]enableViewAnimation([CtFieldReadImpl]mLookUpIcon, [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anim.ViewAnimation.fade([CtTypeAccessImpl]ViewAnimation.FADE_OUT, [CtFieldReadImpl]mFadeDuration, [CtLiteralImpl]0));
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void hideLookupButton() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mDisableClipboard) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.getVisibility() != [CtFieldReadImpl]android.view.View.GONE)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl]enableViewAnimation([CtFieldReadImpl]mLookUpIcon, [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anim.ViewAnimation.fade([CtTypeAccessImpl]ViewAnimation.FADE_OUT, [CtFieldReadImpl]mFadeDuration, [CtLiteralImpl]0));
            [CtInvocationImpl]clipboardSetText([CtLiteralImpl]"");
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void showDeleteNoteDialog() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.res.Resources res = [CtInvocationImpl]getResources();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.afollestad.materialdialogs.MaterialDialog.Builder([CtThisAccessImpl]this).title([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.delete_card_title)).iconAttr([CtTypeAccessImpl]R.attr.dialogErrorIcon).content([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.delete_note_message, [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.stripHTML([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.q([CtLiteralImpl]true)))).positiveText([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.dialog_positive_delete)).negativeText([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.dialog_cancel)).onPositive([CtLambdaImpl]([CtParameterImpl] dialog,[CtParameterImpl] which) -> [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: OK button pressed to delete note %d", [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mCurrentCard.getNid());
            [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mSoundPlayer.stopSounds();
            [CtInvocationImpl]dismiss([CtVariableReadImpl]Collection.DismissType.DELETE_NOTE);
        }).build().show();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int getRecommendedEase([CtParameterImpl][CtTypeReferenceImpl]boolean easy) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl]getAnswerButtonCount()) {
                [CtCaseImpl]case [CtLiteralImpl]2 :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2;
                [CtCaseImpl]case [CtLiteralImpl]3 :
                    [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]easy ? [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3 : [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2;
                [CtCaseImpl]case [CtLiteralImpl]4 :
                    [CtReturnImpl]return [CtConditionalImpl][CtVariableReadImpl]easy ? [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4 : [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3;
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtLiteralImpl]0;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.RuntimeException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.sendExceptionReport([CtVariableReadImpl]e, [CtLiteralImpl]"AbstractReviewer-getRecommendedEase");
            [CtInvocationImpl]closeReviewer([CtTypeAccessImpl]DeckPicker.RESULT_DB_ERROR, [CtLiteralImpl]true);
            [CtReturnImpl]return [CtLiteralImpl]0;
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void answerCard([CtParameterImpl][CtTypeReferenceImpl]int ease) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]mInAnswer) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]mIsSelecting = [CtLiteralImpl]false;
        [CtInvocationImpl]hideLookupButton();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int buttonNumber = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getSched().answerButtons([CtFieldReadImpl]mCurrentCard);
        [CtIfImpl][CtCommentImpl]// Detect invalid ease for current card (e.g. by using keyboard shortcut or gesture).
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]buttonNumber < [CtVariableReadImpl]ease) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtSwitchImpl][CtCommentImpl]// Set the dots appearing below the toolbar
        switch ([CtVariableReadImpl]ease) {
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1 :
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setText([CtLiteralImpl]"•");
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setTextColor([CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtThisAccessImpl]this, [CtTypeAccessImpl]R.color.material_red_500));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2 :
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setText([CtLiteralImpl]"••");
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setTextColor([CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtThisAccessImpl]this, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]buttonNumber == [CtFieldReadImpl]com.ichi2.libanki.Consts.BUTTON_FOUR ? [CtFieldReadImpl]R.color.material_blue_grey_600 : [CtFieldReadImpl]R.color.material_green_500));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3 :
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setText([CtLiteralImpl]"•••");
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setTextColor([CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtThisAccessImpl]this, [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]buttonNumber == [CtFieldReadImpl]com.ichi2.libanki.Consts.BUTTON_FOUR ? [CtFieldReadImpl]R.color.material_green_500 : [CtFieldReadImpl]R.color.material_light_blue_500));
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4 :
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setText([CtLiteralImpl]"••••");
                [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setTextColor([CtInvocationImpl][CtTypeAccessImpl]androidx.core.content.ContextCompat.getColor([CtThisAccessImpl]this, [CtTypeAccessImpl]R.color.material_light_blue_500));
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unknown easy type %s", [CtVariableReadImpl]ease);
                [CtBreakImpl]break;
        }
        [CtInvocationImpl][CtCommentImpl]// remove chosen answer hint after a while
        [CtFieldReadImpl]mTimerHandler.removeCallbacks([CtFieldReadImpl]removeChosenAnswerText);
        [CtInvocationImpl][CtFieldReadImpl]mTimerHandler.postDelayed([CtFieldReadImpl]removeChosenAnswerText, [CtFieldReadImpl]mShowChosenAnswerLength);
        [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.stopSounds();
        [CtAssignmentImpl][CtFieldWriteImpl]mCurrentEase = [CtVariableReadImpl]ease;
        [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]ANSWER_CARD, [CtInvocationImpl]mAnswerCardHandler([CtLiteralImpl]true), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtFieldReadImpl]mCurrentCard, [CtFieldReadImpl]mCurrentEase));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean dispatchKeyEvent([CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]event.getAction() == [CtFieldReadImpl]android.view.KeyEvent.ACTION_DOWN) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// assign correct gesture code
            [CtTypeReferenceImpl]int gesture = [CtFieldReadImpl]COMMAND_NOTHING;
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]event.getKeyCode()) {
                [CtCaseImpl]case [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_VOLUME_UP :
                    [CtAssignmentImpl][CtVariableWriteImpl]gesture = [CtFieldReadImpl]mGestureVolumeUp;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_VOLUME_DOWN :
                    [CtAssignmentImpl][CtVariableWriteImpl]gesture = [CtFieldReadImpl]mGestureVolumeDown;
                    [CtBreakImpl]break;
            }
            [CtIfImpl][CtCommentImpl]// Execute gesture's command, but only consume event if action is assigned. We want the volume buttons to work normally otherwise.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]gesture != [CtFieldReadImpl]COMMAND_NOTHING) [CtBlockImpl]{
                [CtInvocationImpl]executeCommand([CtVariableReadImpl]gesture);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.dispatchKeyEvent([CtVariableReadImpl]event);
    }

    [CtMethodImpl][CtCommentImpl]// Set the content view to the one provided and initialize accessors.
    [CtCommentImpl]// Tracked separately as #5023 on github for clipboard
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    protected [CtTypeReferenceImpl]void initLayout() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.FrameLayout mCardContainer = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.FrameLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_frame)));
        [CtAssignmentImpl][CtFieldWriteImpl]mTopBarLayout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.RelativeLayout) (findViewById([CtTypeAccessImpl]R.id.top_bar)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.ImageView mark = [CtInvocationImpl][CtFieldReadImpl]mTopBarLayout.findViewById([CtTypeAccessImpl]R.id.mark_icon);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.ImageView flag = [CtInvocationImpl][CtFieldReadImpl]mTopBarLayout.findViewById([CtTypeAccessImpl]R.id.flag_icon);
        [CtAssignmentImpl][CtFieldWriteImpl]mCardMarker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.reviewer.CardMarker([CtVariableReadImpl]mark, [CtVariableReadImpl]flag);
        [CtAssignmentImpl][CtFieldWriteImpl]mCardFrame = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.FrameLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard)));
        [CtAssignmentImpl][CtFieldWriteImpl]mCardFrameParent = [CtInvocationImpl](([CtTypeReferenceImpl]android.view.ViewGroup) ([CtFieldReadImpl]mCardFrame.getParent()));
        [CtAssignmentImpl][CtFieldWriteImpl]mTouchLayer = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.FrameLayout) (findViewById([CtTypeAccessImpl]R.id.touch_layer)));
        [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.setOnTouchListener([CtFieldReadImpl]mGestureListener);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mDisableClipboard) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mClipboard = [CtInvocationImpl](([CtTypeReferenceImpl]android.text.ClipboardManager) (getSystemService([CtTypeAccessImpl]Context.CLIPBOARD_SERVICE)));
        }
        [CtInvocationImpl][CtFieldReadImpl]mCardFrame.removeAllViews();
        [CtAssignmentImpl][CtCommentImpl]// Initialize swipe
        [CtFieldWriteImpl]mGestureDetectorImpl = [CtConditionalImpl]([CtFieldReadImpl]mLinkOverridesTouchGesture) ? [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.LinkDetectingGestureDetector() : [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.MyGestureDetector();
        [CtAssignmentImpl][CtFieldWriteImpl]gestureDetector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]androidx.core.view.GestureDetectorCompat([CtThisAccessImpl]this, [CtFieldReadImpl]mGestureDetectorImpl);
        [CtAssignmentImpl][CtFieldWriteImpl]mEase1 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.ease1)));
        [CtInvocationImpl][CtFieldReadImpl]mEase1.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Medium"));
        [CtAssignmentImpl][CtFieldWriteImpl]mEase1Layout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_layout_ease1)));
        [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setOnClickListener([CtFieldReadImpl]mSelectEaseHandler);
        [CtAssignmentImpl][CtFieldWriteImpl]mEase2 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.ease2)));
        [CtInvocationImpl][CtFieldReadImpl]mEase2.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Medium"));
        [CtAssignmentImpl][CtFieldWriteImpl]mEase2Layout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_layout_ease2)));
        [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setOnClickListener([CtFieldReadImpl]mSelectEaseHandler);
        [CtAssignmentImpl][CtFieldWriteImpl]mEase3 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.ease3)));
        [CtInvocationImpl][CtFieldReadImpl]mEase3.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Medium"));
        [CtAssignmentImpl][CtFieldWriteImpl]mEase3Layout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_layout_ease3)));
        [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setOnClickListener([CtFieldReadImpl]mSelectEaseHandler);
        [CtAssignmentImpl][CtFieldWriteImpl]mEase4 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.ease4)));
        [CtInvocationImpl][CtFieldReadImpl]mEase4.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Medium"));
        [CtAssignmentImpl][CtFieldWriteImpl]mEase4Layout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_layout_ease4)));
        [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setOnClickListener([CtFieldReadImpl]mSelectEaseHandler);
        [CtAssignmentImpl][CtFieldWriteImpl]mNext1 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.nextTime1)));
        [CtAssignmentImpl][CtFieldWriteImpl]mNext2 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.nextTime2)));
        [CtAssignmentImpl][CtFieldWriteImpl]mNext3 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.nextTime3)));
        [CtAssignmentImpl][CtFieldWriteImpl]mNext4 = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.nextTime4)));
        [CtInvocationImpl][CtFieldReadImpl]mNext1.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Regular"));
        [CtInvocationImpl][CtFieldReadImpl]mNext2.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Regular"));
        [CtInvocationImpl][CtFieldReadImpl]mNext3.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Regular"));
        [CtInvocationImpl][CtFieldReadImpl]mNext4.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Regular"));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mShowNextReviewTime) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mNext1.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl]mNext2.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl]mNext3.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl]mNext4.setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.Button mFlipCard = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Button) (findViewById([CtTypeAccessImpl]R.id.flip_card)));
        [CtInvocationImpl][CtVariableReadImpl]mFlipCard.setTypeface([CtInvocationImpl][CtTypeAccessImpl]com.afollestad.materialdialogs.util.TypefaceHelper.get([CtThisAccessImpl]this, [CtLiteralImpl]"Roboto-Medium"));
        [CtAssignmentImpl][CtFieldWriteImpl]mFlipCardLayout = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.flashcard_layout_flip)));
        [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.setOnClickListener([CtFieldReadImpl]mFlipCardListener);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mButtonHeightSet) && [CtBinaryOperatorImpl]([CtFieldReadImpl]mRelativeButtonSize != [CtLiteralImpl]100)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.ViewGroup.LayoutParams params = [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.getLayoutParams();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]params.height = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.height * [CtFieldReadImpl]mRelativeButtonSize) / [CtLiteralImpl]100;
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.getLayoutParams();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]params.height = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.height * [CtFieldReadImpl]mRelativeButtonSize) / [CtLiteralImpl]100;
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.getLayoutParams();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]params.height = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.height * [CtFieldReadImpl]mRelativeButtonSize) / [CtLiteralImpl]100;
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.getLayoutParams();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]params.height = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.height * [CtFieldReadImpl]mRelativeButtonSize) / [CtLiteralImpl]100;
            [CtAssignmentImpl][CtVariableWriteImpl]params = [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.getLayoutParams();
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]params.height = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]params.height * [CtFieldReadImpl]mRelativeButtonSize) / [CtLiteralImpl]100;
            [CtAssignmentImpl][CtFieldWriteImpl]mButtonHeightSet = [CtLiteralImpl]true;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]mTextBarNew = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.new_number)));
        [CtAssignmentImpl][CtFieldWriteImpl]mTextBarLearn = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.learn_number)));
        [CtAssignmentImpl][CtFieldWriteImpl]mTextBarReview = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.review_number)));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mShowRemainingCardCount) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mTextBarNew.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarLearn.setVisibility([CtTypeAccessImpl]View.GONE);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarReview.setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]mCardTimer = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.Chronometer) (findViewById([CtTypeAccessImpl]R.id.card_time)));
        [CtAssignmentImpl][CtFieldWriteImpl]mChosenAnswer = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.TextView) (findViewById([CtTypeAccessImpl]R.id.choosen_answer)));
        [CtAssignmentImpl][CtFieldWriteImpl]mAnswerField = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.EditText) (findViewById([CtTypeAccessImpl]R.id.answer_field)));
        [CtAssignmentImpl][CtFieldWriteImpl]mLookUpIcon = [CtInvocationImpl]findViewById([CtTypeAccessImpl]R.id.lookup_button);
        [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mLookUpIcon.setOnClickListener([CtNewClassImpl]new [CtTypeReferenceImpl]android.view.View.OnClickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onClick([CtParameterImpl][CtTypeReferenceImpl]android.view.View arg0) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: Lookup button pressed");
                [CtIfImpl]if ([CtInvocationImpl]clipboardHasText()) [CtBlockImpl]{
                    [CtInvocationImpl]lookUp();
                }
            }
        });
        [CtInvocationImpl]initControls();
        [CtLocalVariableImpl][CtCommentImpl]// Position answer buttons
        [CtTypeReferenceImpl]java.lang.String answerButtonsPosition = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getSharedPrefs([CtThisAccessImpl]this).getString([CtInvocationImpl]getString([CtTypeAccessImpl]R.string.answer_buttons_position_preference), [CtLiteralImpl]"bottom");
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.widget.LinearLayout answerArea = [CtInvocationImpl](([CtTypeReferenceImpl]android.widget.LinearLayout) (findViewById([CtTypeAccessImpl]R.id.bottom_area_layout)));
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.RelativeLayout.LayoutParams answerAreaParams = [CtInvocationImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.RelativeLayout.LayoutParams) ([CtVariableReadImpl]answerArea.getLayoutParams()));
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.RelativeLayout.LayoutParams cardContainerParams = [CtInvocationImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.RelativeLayout.LayoutParams) ([CtVariableReadImpl]mCardContainer.getLayoutParams()));
        [CtSwitchImpl]switch ([CtVariableReadImpl]answerButtonsPosition) {
            [CtCaseImpl]case [CtLiteralImpl]"top" :
                [CtInvocationImpl][CtVariableReadImpl]cardContainerParams.addRule([CtTypeAccessImpl]RelativeLayout.BELOW, [CtTypeAccessImpl]R.id.bottom_area_layout);
                [CtInvocationImpl][CtVariableReadImpl]answerAreaParams.addRule([CtTypeAccessImpl]RelativeLayout.BELOW, [CtTypeAccessImpl]R.id.mic_tool_bar_layer);
                [CtInvocationImpl][CtVariableReadImpl]answerArea.removeView([CtFieldReadImpl]mAnswerField);
                [CtInvocationImpl][CtVariableReadImpl]answerArea.addView([CtFieldReadImpl]mAnswerField, [CtLiteralImpl]1);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtLiteralImpl]"bottom" :
                [CtInvocationImpl][CtVariableReadImpl]cardContainerParams.addRule([CtTypeAccessImpl]RelativeLayout.ABOVE, [CtTypeAccessImpl]R.id.bottom_area_layout);
                [CtInvocationImpl][CtVariableReadImpl]cardContainerParams.addRule([CtTypeAccessImpl]RelativeLayout.BELOW, [CtTypeAccessImpl]R.id.mic_tool_bar_layer);
                [CtInvocationImpl][CtVariableReadImpl]answerAreaParams.addRule([CtTypeAccessImpl]RelativeLayout.ALIGN_PARENT_BOTTOM);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unknown answerButtonsPosition: %s", [CtVariableReadImpl]answerButtonsPosition);
                [CtBreakImpl]break;
        }
        [CtInvocationImpl][CtVariableReadImpl]answerArea.setLayoutParams([CtVariableReadImpl]answerAreaParams);
        [CtInvocationImpl][CtVariableReadImpl]mCardContainer.setLayoutParams([CtVariableReadImpl]cardContainerParams);
    }

    [CtMethodImpl][CtCommentImpl]// they request we review carefully because of XSS security, we have
    [CtAnnotationImpl]@android.annotation.SuppressLint([CtLiteralImpl]"SetJavaScriptEnabled")
    private [CtTypeReferenceImpl]android.webkit.WebView createWebView() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.webkit.WebView webView = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.MyWebView([CtThisAccessImpl]this);
        [CtInvocationImpl][CtVariableReadImpl]webView.setScrollBarStyle([CtTypeAccessImpl]View.SCROLLBARS_OUTSIDE_OVERLAY);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]webView.getSettings().setDisplayZoomControls([CtLiteralImpl]false);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]webView.getSettings().setBuiltInZoomControls([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]webView.getSettings().setSupportZoom([CtLiteralImpl]true);
        [CtInvocationImpl][CtCommentImpl]// Start at the most zoomed-out level
        [CtInvocationImpl][CtVariableReadImpl]webView.getSettings().setLoadWithOverviewMode([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]webView.getSettings().setJavaScriptEnabled([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]webView.setWebChromeClient([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.AnkiDroidWebChromeClient());
        [CtInvocationImpl][CtCommentImpl]// Problems with focus and input tags is the reason we keep the old type answer mechanism for old Androids.
        [CtVariableReadImpl]webView.setFocusableInTouchMode([CtFieldReadImpl]mUseInputTag);
        [CtInvocationImpl][CtVariableReadImpl]webView.setScrollbarFadingEnabled([CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Focusable = %s, Focusable in touch mode = %s", [CtInvocationImpl][CtVariableReadImpl]webView.isFocusable(), [CtInvocationImpl][CtVariableReadImpl]webView.isFocusableInTouchMode());
        [CtInvocationImpl][CtVariableReadImpl]webView.setWebViewClient([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.CardViewerWebClient());
        [CtInvocationImpl][CtCommentImpl]// Set transparent color to prevent flashing white when night mode enabled
        [CtVariableReadImpl]webView.setBackgroundColor([CtInvocationImpl][CtTypeAccessImpl]android.graphics.Color.argb([CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]0, [CtLiteralImpl]0));
        [CtInvocationImpl][CtCommentImpl]// Javascript interface for calling AnkiDroid functions in webview, see card.js
        [CtVariableReadImpl]webView.addJavascriptInterface([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.JavaScriptFunction(), [CtLiteralImpl]"AnkiDroidJS");
        [CtReturnImpl]return [CtVariableReadImpl]webView;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If a card is displaying the question, flip it, otherwise answer it
     */
    private [CtTypeReferenceImpl]void flipOrAnswerCard([CtParameterImpl][CtTypeReferenceImpl]int cardOrdinal) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
            [CtInvocationImpl]displayCardAnswer();
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]answerCard([CtVariableReadImpl]cardOrdinal);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean webViewRendererLastCrashedOnCard([CtParameterImpl][CtTypeReferenceImpl]long cardId) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]lastCrashingCardId != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]lastCrashingCardId == [CtVariableReadImpl]cardId);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean canRecoverFromWebViewRendererCrash() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// DEFECT
        [CtCommentImpl]// If we don't have a card to render, we're in a bad state. The class doesn't currently track state
        [CtCommentImpl]// well enough to be able to know exactly where we are in the initialisation pipeline.
        [CtCommentImpl]// so it's best to mark the crash as non-recoverable.
        [CtCommentImpl]// We should fix this, but it's very unlikely that we'll ever get here. Logs will tell
        [CtCommentImpl]// Revisit webViewCrashedOnCard() if changing this. Logic currently assumes we have a card.
        return [CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard != [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtCommentImpl]// #5780 - Users could OOM the WebView Renderer. This triggers the same symptoms
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    public [CtTypeReferenceImpl]void crashWebViewRenderer() [CtBlockImpl]{
        [CtInvocationImpl]loadUrlInViewer([CtLiteralImpl]"chrome://crash");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Used to set the "javascript:" URIs for IPC
     */
    private [CtTypeReferenceImpl]void loadUrlInViewer([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl][CtVariableReadImpl]card.loadUrl([CtVariableReadImpl]url));
    }

    [CtMethodImpl]private <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]android.view.View> [CtTypeParameterReferenceImpl]T inflateNewView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.IdRes
    [CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int layoutId = [CtInvocationImpl]getContentViewAttr([CtFieldReadImpl]mPrefFullscreenReview);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.ViewGroup content = [CtInvocationImpl](([CtTypeReferenceImpl]android.view.ViewGroup) ([CtInvocationImpl][CtTypeAccessImpl]android.view.LayoutInflater.from([CtThisAccessImpl]this).inflate([CtVariableReadImpl]layoutId, [CtLiteralImpl]null, [CtLiteralImpl]false)));
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]T ret = [CtInvocationImpl][CtVariableReadImpl]content.findViewById([CtVariableReadImpl]id);
        [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]android.view.ViewGroup) ([CtVariableReadImpl]ret.getParent())).removeView([CtVariableReadImpl]ret);[CtCommentImpl]// detach the view from its parent

        [CtInvocationImpl][CtVariableReadImpl]content.removeAllViews();
        [CtReturnImpl]return [CtVariableReadImpl]ret;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void destroyWebView([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView webView) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]webView != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]webView.stopLoading();
                [CtInvocationImpl][CtVariableReadImpl]webView.setWebChromeClient([CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]webView.setWebViewClient([CtLiteralImpl]null);
                [CtInvocationImpl][CtVariableReadImpl]webView.destroy();
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException npe) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]npe, [CtLiteralImpl]"WebView became null on destruction");
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean shouldShowNextReviewTime() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mShowNextReviewTime;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void displayAnswerBottomBar() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// hide flipcard button
        [CtFieldReadImpl]mFlipCardLayout.setVisibility([CtTypeAccessImpl]View.GONE);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void hideEaseButtons() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtInvocationImpl][CtFieldReadImpl]mNext1.setText([CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]mNext2.setText([CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]mNext3.setText([CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]mNext4.setText([CtLiteralImpl]"");
        [CtInvocationImpl]focusAnswerCompletionField();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Focuses the appropriate field for an answer
     * And allows keyboard shortcuts to go to the default handlers.
     */
    private [CtTypeReferenceImpl]void focusAnswerCompletionField() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// This does not handle mUseInputTag (the WebView contains an input field with a typable answer).
        [CtCommentImpl]// In this case, the user can use touch to focus the field if necessary.
        if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mAnswerField.requestFocus();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.requestFocus();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void switchTopBarVisibility([CtParameterImpl][CtTypeReferenceImpl]int visible) [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]mShowTimer) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setVisibility([CtVariableReadImpl]visible);
        }
        [CtIfImpl]if ([CtFieldReadImpl]mShowRemainingCardCount) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mTextBarNew.setVisibility([CtVariableReadImpl]visible);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarLearn.setVisibility([CtVariableReadImpl]visible);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarReview.setVisibility([CtVariableReadImpl]visible);
        }
        [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setVisibility([CtVariableReadImpl]visible);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void initControls() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]mCardFrame.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtIfImpl]if ([CtFieldReadImpl]mShowRemainingCardCount) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mTextBarNew.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarLearn.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl]mTextBarReview.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        }
        [CtInvocationImpl][CtFieldReadImpl]mChosenAnswer.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setVisibility([CtConditionalImpl][CtInvocationImpl]typeAnswer() ? [CtFieldReadImpl]android.view.View.VISIBLE : [CtFieldReadImpl]android.view.View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setOnEditorActionListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.EditText.OnEditorActionListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean onEditorAction([CtParameterImpl][CtTypeReferenceImpl]android.widget.TextView v, [CtParameterImpl][CtTypeReferenceImpl]int actionId, [CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actionId == [CtFieldReadImpl]android.view.inputmethod.EditorInfo.IME_ACTION_DONE) [CtBlockImpl]{
                    [CtInvocationImpl]displayCardAnswer();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        });
        [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setOnKeyListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.view.View.OnKeyListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]boolean onKey([CtParameterImpl][CtTypeReferenceImpl]android.view.View v, [CtParameterImpl][CtTypeReferenceImpl]int keyCode, [CtParameterImpl][CtTypeReferenceImpl]android.view.KeyEvent event) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]event.getAction() == [CtFieldReadImpl]android.view.KeyEvent.ACTION_UP) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_ENTER) || [CtBinaryOperatorImpl]([CtVariableReadImpl]keyCode == [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_NUMPAD_ENTER))) [CtBlockImpl]{
                    [CtInvocationImpl]displayCardAnswer();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        });
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]android.content.SharedPreferences restorePreferences() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.SharedPreferences preferences = [CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getSharedPrefs([CtInvocationImpl]getBaseContext());
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefHideDueCount = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"hideDueCount", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefShowETA = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"showETA", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]mUseInputTag = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"useInputTag", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtCommentImpl]// On newer Androids, ignore this setting, which should be hidden in the prefs anyway.
        [CtFieldWriteImpl]mDisableClipboard = [CtInvocationImpl][CtLiteralImpl]"0".equals([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"dictionary", [CtLiteralImpl]"0"));
        [CtAssignmentImpl][CtCommentImpl]// mDeckFilename = preferences.getString("deckFilename", "");
        [CtFieldWriteImpl]mPrefFullscreenReview = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"fullscreenMode", [CtLiteralImpl]"0"));
        [CtAssignmentImpl][CtFieldWriteImpl]mRelativeButtonSize = [CtInvocationImpl][CtVariableReadImpl]preferences.getInt([CtLiteralImpl]"answerButtonSize", [CtLiteralImpl]100);
        [CtAssignmentImpl][CtFieldWriteImpl]mSpeakText = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"tts", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefUseTimer = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"timeoutAnswer", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefWaitAnswerSecond = [CtInvocationImpl][CtVariableReadImpl]preferences.getInt([CtLiteralImpl]"timeoutAnswerSeconds", [CtLiteralImpl]20);
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefWaitQuestionSecond = [CtInvocationImpl][CtVariableReadImpl]preferences.getInt([CtLiteralImpl]"timeoutQuestionSeconds", [CtLiteralImpl]60);
        [CtAssignmentImpl][CtFieldWriteImpl]mScrollingButtons = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"scrolling_buttons", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]mDoubleScrolling = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"double_scrolling", [CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl]mPrefShowTopbar = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"showTopbar", [CtLiteralImpl]true);
        [CtAssignmentImpl][CtFieldWriteImpl]mGesturesEnabled = [CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.initiateGestures([CtVariableReadImpl]preferences);
        [CtAssignmentImpl][CtFieldWriteImpl]mLinkOverridesTouchGesture = [CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"linkOverridesTouchGesture", [CtLiteralImpl]false);
        [CtIfImpl]if ([CtFieldReadImpl]mGesturesEnabled) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureSwipeUp = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureSwipeUp", [CtLiteralImpl]"9"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureSwipeDown = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureSwipeDown", [CtLiteralImpl]"0"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureSwipeLeft = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureSwipeLeft", [CtLiteralImpl]"8"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureSwipeRight = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureSwipeRight", [CtLiteralImpl]"17"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureDoubleTap = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureDoubleTap", [CtLiteralImpl]"7"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureTapLeft = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureTapLeft", [CtLiteralImpl]"3"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureTapRight = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureTapRight", [CtLiteralImpl]"6"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureTapTop = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureTapTop", [CtLiteralImpl]"12"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureTapBottom = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureTapBottom", [CtLiteralImpl]"2"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureLongclick = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureLongclick", [CtLiteralImpl]"11"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureVolumeUp = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureVolumeUp", [CtLiteralImpl]"0"));
            [CtAssignmentImpl][CtFieldWriteImpl]mGestureVolumeDown = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]preferences.getString([CtLiteralImpl]"gestureVolumeDown", [CtLiteralImpl]"0"));
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]preferences.getBoolean([CtLiteralImpl]"keepScreenOn", [CtLiteralImpl]false)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getWindow().addFlags([CtTypeAccessImpl]WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        [CtReturnImpl]return [CtVariableReadImpl]preferences;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void restoreCollectionPreferences() [CtBlockImpl]{
        [CtTryImpl][CtCommentImpl]// These are preferences we pull out of the collection instead of SharedPreferences
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mShowNextReviewTime = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getConf().getBoolean([CtLiteralImpl]"estTimes");
            [CtAssignmentImpl][CtFieldWriteImpl]mShowRemainingCardCount = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getConf().getBoolean([CtLiteralImpl]"dueCounts");
            [CtLocalVariableImpl][CtCommentImpl]// Dynamic don't have review options; attempt to get deck-specific auto-advance options
            [CtCommentImpl]// but be prepared to go with all default if it's a dynamic deck
            [CtTypeReferenceImpl]com.ichi2.utils.JSONObject revOptions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.utils.JSONObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]long selectedDid = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().selected();
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().isDyn([CtVariableReadImpl]selectedDid)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]revOptions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().confForDid([CtVariableReadImpl]selectedDid).getJSONObject([CtLiteralImpl]"rev");
            }
            [CtAssignmentImpl][CtFieldWriteImpl]mOptUseGeneralTimerSettings = [CtInvocationImpl][CtVariableReadImpl]revOptions.optBoolean([CtLiteralImpl]"useGeneralTimeoutSettings", [CtLiteralImpl]true);
            [CtAssignmentImpl][CtFieldWriteImpl]mOptUseTimer = [CtInvocationImpl][CtVariableReadImpl]revOptions.optBoolean([CtLiteralImpl]"timeoutAnswer", [CtLiteralImpl]false);
            [CtAssignmentImpl][CtFieldWriteImpl]mOptWaitAnswerSecond = [CtInvocationImpl][CtVariableReadImpl]revOptions.optInt([CtLiteralImpl]"timeoutAnswerSeconds", [CtLiteralImpl]20);
            [CtAssignmentImpl][CtFieldWriteImpl]mOptWaitQuestionSecond = [CtInvocationImpl][CtVariableReadImpl]revOptions.optInt([CtLiteralImpl]"timeoutQuestionSeconds", [CtLiteralImpl]60);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]com.ichi2.utils.JSONException e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]e, [CtLiteralImpl]"Unable to restoreCollectionPreferences");
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.NullPointerException npe) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// NPE on collection only happens if the Collection is broken, follow AnkiActivity example
            [CtTypeReferenceImpl]android.content.Intent deckPicker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtThisAccessImpl]this, [CtFieldReadImpl]com.ichi2.anki.DeckPicker.class);
            [CtInvocationImpl][CtVariableReadImpl]deckPicker.putExtra([CtLiteralImpl]"collectionLoadError", [CtLiteralImpl]true);[CtCommentImpl]// don't currently do anything with this

            [CtInvocationImpl][CtVariableReadImpl]deckPicker.addFlags([CtBinaryOperatorImpl][CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | [CtFieldReadImpl]android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            [CtInvocationImpl]startActivityWithAnimation([CtVariableReadImpl]deckPicker, [CtTypeAccessImpl]ActivityTransitionAnimation.LEFT);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setInterface() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]recreateWebView();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void recreateWebView() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mCard = [CtInvocationImpl]createWebView();
            [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.utils.WebViewDebugging.initializeDebugging([CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getSharedPrefs([CtThisAccessImpl]this));
            [CtInvocationImpl][CtFieldReadImpl]mCardFrame.addView([CtFieldReadImpl]mCard);
            [CtInvocationImpl][CtFieldReadImpl]mGestureDetectorImpl.onWebViewCreated([CtFieldReadImpl]mCard);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mCard.getVisibility() != [CtFieldReadImpl]android.view.View.VISIBLE) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCard.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateForNewCard() [CtBlockImpl]{
        [CtInvocationImpl]updateScreenCounts();
        [CtIfImpl][CtCommentImpl]// Clean answer field
        if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setText([CtLiteralImpl]"");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mPrefWhiteboard && [CtBinaryOperatorImpl]([CtFieldReadImpl]mWhiteboard != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mWhiteboard.clear();
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void updateScreenCounts() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.appcompat.app.ActionBar actionBar = [CtInvocationImpl]getSupportActionBar();
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] counts = [CtInvocationImpl][CtFieldReadImpl]mSched.counts([CtFieldReadImpl]mCurrentCard);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]actionBar != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String title = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Decks.basename([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().get([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getDid()).getString([CtLiteralImpl]"name"));
            [CtInvocationImpl][CtVariableReadImpl]actionBar.setTitle([CtVariableReadImpl]title);
            [CtIfImpl]if ([CtFieldReadImpl]mPrefShowETA) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]eta = [CtInvocationImpl][CtFieldReadImpl]mSched.eta([CtVariableReadImpl]counts, [CtLiteralImpl]false);
                [CtInvocationImpl][CtVariableReadImpl]actionBar.setSubtitle([CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.remainingTime([CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getInstance(), [CtBinaryOperatorImpl][CtFieldReadImpl]eta * [CtLiteralImpl]60));
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mPrefShowTopbar) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mTopBarLayout.setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]newCount = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.SpannableString([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtArrayReadImpl][CtVariableReadImpl]counts[[CtLiteralImpl]0]));
        [CtAssignmentImpl][CtFieldWriteImpl]lrnCount = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.SpannableString([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtArrayReadImpl][CtVariableReadImpl]counts[[CtLiteralImpl]1]));
        [CtAssignmentImpl][CtFieldWriteImpl]revCount = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.SpannableString([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtArrayReadImpl][CtVariableReadImpl]counts[[CtLiteralImpl]2]));
        [CtIfImpl]if ([CtFieldReadImpl]mPrefHideDueCount) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]revCount = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.SpannableString([CtLiteralImpl]"???");
        }
        [CtSwitchImpl]switch ([CtInvocationImpl][CtFieldReadImpl]mSched.countIdx([CtFieldReadImpl]mCurrentCard)) {
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.libanki.Consts.CARD_TYPE_NEW :
                [CtInvocationImpl][CtFieldReadImpl]newCount.setSpan([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.style.UnderlineSpan(), [CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]newCount.length(), [CtLiteralImpl]0);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.libanki.Consts.CARD_TYPE_LRN :
                [CtInvocationImpl][CtFieldReadImpl]lrnCount.setSpan([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.style.UnderlineSpan(), [CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]lrnCount.length(), [CtLiteralImpl]0);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.libanki.Consts.CARD_TYPE_REV :
                [CtInvocationImpl][CtFieldReadImpl]revCount.setSpan([CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.style.UnderlineSpan(), [CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]revCount.length(), [CtLiteralImpl]0);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unknown card type %s", [CtInvocationImpl][CtFieldReadImpl]mSched.countIdx([CtFieldReadImpl]mCurrentCard));
                [CtBreakImpl]break;
        }
        [CtInvocationImpl][CtFieldReadImpl]mTextBarNew.setText([CtFieldReadImpl]newCount);
        [CtInvocationImpl][CtFieldReadImpl]mTextBarLearn.setText([CtFieldReadImpl]lrnCount);
        [CtInvocationImpl][CtFieldReadImpl]mTextBarReview.setText([CtFieldReadImpl]revCount);
    }

    [CtFieldImpl][CtCommentImpl]/* Handler for the delay in auto showing question and/or answer One toggle for both question and answer, could set
    longer delay for auto next question
     */
    protected [CtTypeReferenceImpl]android.os.Handler mTimeoutHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler();

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.lang.Runnable mShowQuestionTask = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Assume hitting the "Again" button when auto next question
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mEase1Layout.isEnabled() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mEase1Layout.getVisibility() == [CtFieldReadImpl]android.view.View.VISIBLE)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.performClick();
            }
        }
    };

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.lang.Runnable mShowAnswerTask = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.isEnabled() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.getVisibility() == [CtFieldReadImpl]android.view.View.VISIBLE)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.performClick();
            }
        }
    };

    [CtClassImpl]class ReadTextListener implements [CtTypeReferenceImpl][CtTypeReferenceImpl]ReadText.ReadTextListener {
        [CtMethodImpl]public [CtTypeReferenceImpl]void onDone() [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mUseTimer) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]ReadText.getmQuestionAnswer() == [CtFieldReadImpl]com.ichi2.libanki.Sound.SOUNDS_QUESTION) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long delay = [CtBinaryOperatorImpl][CtFieldReadImpl]mWaitAnswerSecond * [CtLiteralImpl]1000;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]delay > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.postDelayed([CtFieldReadImpl]mShowAnswerTask, [CtVariableReadImpl]delay);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]ReadText.getmQuestionAnswer() == [CtFieldReadImpl]com.ichi2.libanki.Sound.SOUNDS_ANSWER) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long delay = [CtBinaryOperatorImpl][CtFieldReadImpl]mWaitQuestionSecond * [CtLiteralImpl]1000;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]delay > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.postDelayed([CtFieldReadImpl]mShowQuestionTask, [CtVariableReadImpl]delay);
                }
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void initTimer() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]android.util.TypedValue typedValue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.util.TypedValue();
        [CtAssignmentImpl][CtFieldWriteImpl]mShowTimer = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.showTimer();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mShowTimer && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mCardTimer.getVisibility() == [CtFieldReadImpl]android.view.View.INVISIBLE)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mShowTimer) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mCardTimer.getVisibility() != [CtFieldReadImpl]android.view.View.INVISIBLE)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setVisibility([CtTypeAccessImpl]View.INVISIBLE);
        }
        [CtInvocationImpl][CtCommentImpl]// Set normal timer color
        [CtInvocationImpl]getTheme().resolveAttribute([CtTypeAccessImpl]android.R.attr.textColor, [CtVariableReadImpl]typedValue, [CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setTextColor([CtFieldReadImpl][CtVariableReadImpl]typedValue.data);
        [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setBase([CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime());
        [CtInvocationImpl][CtFieldReadImpl]mCardTimer.start();
        [CtInvocationImpl][CtCommentImpl]// Stop and highlight the timer if it reaches the time limit.
        [CtInvocationImpl]getTheme().resolveAttribute([CtTypeAccessImpl]R.attr.maxTimerColor, [CtVariableReadImpl]typedValue, [CtLiteralImpl]true);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int limit = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.timeLimit();
        [CtInvocationImpl][CtFieldReadImpl]mCardTimer.setOnChronometerTickListener([CtNewClassImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]android.widget.Chronometer.OnChronometerTickListener()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void onChronometerTick([CtParameterImpl][CtTypeReferenceImpl]android.widget.Chronometer chronometer) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]long elapsed = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.elapsedRealtime() - [CtInvocationImpl][CtVariableReadImpl]chronometer.getBase();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elapsed >= [CtVariableReadImpl]limit) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]chronometer.setTextColor([CtFieldReadImpl][CtVariableReadImpl]typedValue.data);
                    [CtInvocationImpl][CtVariableReadImpl]chronometer.stop();
                }
            }
        });
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void displayCardQuestion() [CtBlockImpl]{
        [CtInvocationImpl]displayCardQuestion([CtLiteralImpl]false);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void displayCardQuestion([CtParameterImpl][CtTypeReferenceImpl]boolean reload) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"displayCardQuestion()");
        [CtAssignmentImpl][CtFieldWriteImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer = [CtLiteralImpl]false;
        [CtInvocationImpl]setInterface();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String question;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String displayString = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]displayString = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.empty_card_warning);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]question = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.q([CtVariableReadImpl]reload);
            [CtAssignmentImpl][CtVariableWriteImpl]question = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getMedia().escapeImages([CtVariableReadImpl]question);
            [CtAssignmentImpl][CtVariableWriteImpl]question = [CtInvocationImpl]typeAnsQuestionFilter([CtVariableReadImpl]question);
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.v([CtLiteralImpl]"question: '%s'", [CtVariableReadImpl]question);
            [CtIfImpl][CtCommentImpl]// Show text entry based on if the user wants to write the answer
            if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setVisibility([CtTypeAccessImpl]View.GONE);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]displayString = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.CardAppearance.enrichWithQADiv([CtVariableReadImpl]question, [CtLiteralImpl]false);
            [CtCommentImpl]// if (mSpeakText) {
            [CtCommentImpl]// ReadText.setLanguageInformation(Model.getModel(DeckManager.getMainDeck(),
            [CtCommentImpl]// mCurrentCard.getCardModelId(), false).getId(), mCurrentCard.getCardModelId());
            [CtCommentImpl]// }
        }
        [CtInvocationImpl]updateCard([CtVariableReadImpl]displayString);
        [CtInvocationImpl]hideEaseButtons();
        [CtIfImpl][CtCommentImpl]// Check if it should use the general 'Timeout settings' or the ones specific to this deck
        if ([CtFieldReadImpl]mOptUseGeneralTimerSettings) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mUseTimer = [CtFieldReadImpl]mPrefUseTimer;
            [CtAssignmentImpl][CtFieldWriteImpl]mWaitAnswerSecond = [CtFieldReadImpl]mPrefWaitAnswerSecond;
            [CtAssignmentImpl][CtFieldWriteImpl]mWaitQuestionSecond = [CtFieldReadImpl]mPrefWaitQuestionSecond;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mUseTimer = [CtFieldReadImpl]mOptUseTimer;
            [CtAssignmentImpl][CtFieldWriteImpl]mWaitAnswerSecond = [CtFieldReadImpl]mOptWaitAnswerSecond;
            [CtAssignmentImpl][CtFieldWriteImpl]mWaitQuestionSecond = [CtFieldReadImpl]mOptWaitQuestionSecond;
        }
        [CtIfImpl][CtCommentImpl]// If the user wants to show the answer automatically
        if ([CtFieldReadImpl]mUseTimer) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long delay = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mWaitAnswerSecond * [CtLiteralImpl]1000) + [CtFieldReadImpl]mUseTimerDynamicMS;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]delay > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowAnswerTask);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mSpeakText) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.postDelayed([CtFieldReadImpl]mShowAnswerTask, [CtVariableReadImpl]delay);
                }
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: Question successfully shown for card id %d", [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getId());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Clean up the correct answer text, so it can be used for the comparison with the typed text
     *
     * @param answer
     * 		The content of the field the text typed by the user is compared to.
     * @return The correct answer text, with actual HTML and media references removed, and HTML entities unescaped.
     */
    protected [CtTypeReferenceImpl]java.lang.String cleanCorrectAnswer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String answer) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.TypedAnswer.cleanCorrectAnswer([CtVariableReadImpl]answer);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Clean up the typed answer text, so it can be used for the comparison with the correct answer
     *
     * @param answer
     * 		The answer text typed by the user.
     * @return The typed answer text, cleaned up.
     */
    protected [CtTypeReferenceImpl]java.lang.String cleanTypedAnswer([CtParameterImpl][CtTypeReferenceImpl]java.lang.String answer) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]answer == [CtLiteralImpl]null) || [CtInvocationImpl][CtLiteralImpl]"".equals([CtVariableReadImpl]answer)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.nfcNormalized([CtInvocationImpl][CtVariableReadImpl]answer.trim());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void displayCardAnswer() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"displayCardAnswer()");
        [CtIfImpl][CtCommentImpl]// prevent answering (by e.g. gestures) before card is loaded
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// Explicitly hide the soft keyboard. It *should* be hiding itself automatically,
        [CtCommentImpl]// but sometimes failed to do so (e.g. if an OnKeyListener is attached).
        if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.inputmethod.InputMethodManager inputMethodManager = [CtInvocationImpl](([CtTypeReferenceImpl]android.view.inputmethod.InputMethodManager) (getSystemService([CtTypeAccessImpl]Context.INPUT_METHOD_SERVICE)));
            [CtInvocationImpl][CtVariableReadImpl]inputMethodManager.hideSoftInputFromWindow([CtInvocationImpl][CtFieldReadImpl]mAnswerField.getWindowToken(), [CtLiteralImpl]0);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer = [CtLiteralImpl]true;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String answer = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.a();
        [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.stopSounds();
        [CtAssignmentImpl][CtVariableWriteImpl]answer = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getMedia().escapeImages([CtVariableReadImpl]answer);
        [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtLocalVariableImpl][CtCommentImpl]// Clean up the user answer and the correct answer
        [CtTypeReferenceImpl]java.lang.String userAnswer;
        [CtIfImpl]if ([CtFieldReadImpl]mUseInputTag) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userAnswer = [CtInvocationImpl]cleanTypedAnswer([CtFieldReadImpl]mTypeInput);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]userAnswer = [CtInvocationImpl]cleanTypedAnswer([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mAnswerField.getText().toString());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String correctAnswer = [CtInvocationImpl]cleanCorrectAnswer([CtFieldReadImpl]mTypeCorrect);
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"correct answer = %s", [CtVariableReadImpl]correctAnswer);
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"user answer = %s", [CtVariableReadImpl]userAnswer);
        [CtAssignmentImpl][CtVariableWriteImpl]answer = [CtInvocationImpl]typeAnsAnswerFilter([CtVariableReadImpl]answer, [CtVariableReadImpl]userAnswer, [CtVariableReadImpl]correctAnswer);
        [CtAssignmentImpl][CtFieldWriteImpl]mIsSelecting = [CtLiteralImpl]false;
        [CtInvocationImpl]updateCard([CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.CardAppearance.enrichWithQADiv([CtVariableReadImpl]answer, [CtLiteralImpl]true));
        [CtInvocationImpl]displayAnswerBottomBar();
        [CtIfImpl][CtCommentImpl]// If the user wants to show the next question automatically
        if ([CtFieldReadImpl]mUseTimer) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]long delay = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mWaitQuestionSecond * [CtLiteralImpl]1000) + [CtFieldReadImpl]mUseTimerDynamicMS;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]delay > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowQuestionTask);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mSpeakText) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.postDelayed([CtFieldReadImpl]mShowQuestionTask, [CtVariableReadImpl]delay);
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Scroll the currently shown flashcard vertically
     *
     * @param dy
     * 		amount to be scrolled
     */
    public [CtTypeReferenceImpl]void scrollCurrentCardBy([CtParameterImpl][CtTypeReferenceImpl]int dy) [CtBlockImpl]{
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dy != [CtLiteralImpl]0) && [CtInvocationImpl][CtVariableReadImpl]card.canScrollVertically([CtVariableReadImpl]dy)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.scrollBy([CtLiteralImpl]0, [CtVariableReadImpl]dy);
            }
        });
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tap onto the currently shown flashcard at position x and y
     *
     * @param x
     * 		horizontal position of the event
     * @param y
     * 		vertical position of the event
     */
    public [CtTypeReferenceImpl]void tapOnCurrentCard([CtParameterImpl][CtTypeReferenceImpl]int x, [CtParameterImpl][CtTypeReferenceImpl]int y) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// assemble suitable ACTION_DOWN and ACTION_UP events and forward them to the card's handler
        [CtTypeReferenceImpl]android.view.MotionEvent eDown = [CtInvocationImpl][CtTypeAccessImpl]android.view.MotionEvent.obtain([CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.uptimeMillis(), [CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.uptimeMillis(), [CtTypeAccessImpl]MotionEvent.ACTION_DOWN, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]0);
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl][CtVariableReadImpl]card.dispatchTouchEvent([CtVariableReadImpl]eDown));
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.MotionEvent eUp = [CtInvocationImpl][CtTypeAccessImpl]android.view.MotionEvent.obtain([CtInvocationImpl][CtVariableReadImpl]eDown.getDownTime(), [CtInvocationImpl][CtTypeAccessImpl]android.os.SystemClock.uptimeMillis(), [CtTypeAccessImpl]MotionEvent.ACTION_UP, [CtVariableReadImpl]x, [CtVariableReadImpl]y, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]0, [CtLiteralImpl]0);
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl][CtVariableReadImpl]card.dispatchTouchEvent([CtVariableReadImpl]eUp));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * getAnswerFormat returns the answer part of this card's template as entered by user, without any parsing
     */
    public [CtTypeReferenceImpl]java.lang.String getAnswerFormat() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.utils.JSONObject model = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.model();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.utils.JSONObject template;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]model.getInt([CtLiteralImpl]"type") == [CtFieldReadImpl]com.ichi2.libanki.Consts.MODEL_STD) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]template = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]model.getJSONArray([CtLiteralImpl]"tmpls").getJSONObject([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getOrd());
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]template = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]model.getJSONArray([CtLiteralImpl]"tmpls").getJSONObject([CtLiteralImpl]0);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]template.getString([CtLiteralImpl]"afmt");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addAnswerSounds([CtParameterImpl][CtTypeReferenceImpl]java.lang.String answer) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// don't add answer sounds multiple times, such as when reshowing card after exiting editor
        [CtCommentImpl]// additionally, this condition reduces computation time
        if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mAnswerSoundsAdded) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String answerSoundSource = [CtInvocationImpl]removeFrontSideAudio([CtVariableReadImpl]answer);
            [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.addSounds([CtFieldReadImpl]mBaseUrl, [CtVariableReadImpl]answerSoundSource, [CtTypeAccessImpl]Sound.SOUNDS_ANSWER);
            [CtAssignmentImpl][CtFieldWriteImpl]mAnswerSoundsAdded = [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean isInNightMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCardAppearance.isNightMode();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updateCard([CtParameterImpl]final [CtTypeReferenceImpl]java.lang.String newContent) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"updateCard()");
        [CtAssignmentImpl][CtFieldWriteImpl]mUseTimerDynamicMS = [CtLiteralImpl]0;
        [CtIfImpl][CtCommentImpl]// Add CSS for font color and font size
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]card.getSettings().setDefaultFontSize([CtInvocationImpl]calculateDynamicFontSize([CtVariableReadImpl]newContent)));
        }
        [CtIfImpl]if ([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
            [CtInvocationImpl]addAnswerSounds([CtVariableReadImpl]newContent);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// reset sounds each time first side of card is displayed, which may happen repeatedly without ever
            [CtCommentImpl]// leaving the card (such as when edited)
            [CtFieldReadImpl]mSoundPlayer.resetSounds();
            [CtAssignmentImpl][CtFieldWriteImpl]mAnswerSoundsAdded = [CtLiteralImpl]false;
            [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.addSounds([CtFieldReadImpl]mBaseUrl, [CtVariableReadImpl]newContent, [CtTypeAccessImpl]Sound.SOUNDS_QUESTION);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mUseTimer && [CtUnaryOperatorImpl](![CtFieldReadImpl]mAnswerSoundsAdded)) && [CtInvocationImpl][CtInvocationImpl]getConfigForCurrentCard().optBoolean([CtLiteralImpl]"autoplay", [CtLiteralImpl]false)) [CtBlockImpl]{
                [CtInvocationImpl]addAnswerSounds([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.a());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String content = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Sound.expandSounds([CtFieldReadImpl]mBaseUrl, [CtVariableReadImpl]newContent);
        [CtAssignmentImpl][CtVariableWriteImpl]content = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.CardAppearance.fixBoldStyle([CtVariableReadImpl]content);
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.v([CtLiteralImpl]"content card = \n %s", [CtVariableReadImpl]content);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String style = [CtInvocationImpl][CtFieldReadImpl]mCardAppearance.getStyle();
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.v([CtLiteralImpl]"::style:: / %s", [CtVariableReadImpl]style);
        [CtLocalVariableImpl][CtCommentImpl]// CSS class for card-specific styling
        [CtTypeReferenceImpl]java.lang.String cardClass = [CtInvocationImpl][CtFieldReadImpl]mCardAppearance.getCardClass([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getOrd() + [CtLiteralImpl]1, [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.themes.Themes.getCurrentTheme([CtThisAccessImpl]this));
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.template.Template.textContainsMathjax([CtVariableReadImpl]content)) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]cardClass += [CtLiteralImpl]" mathjax-needs-to-render";
        }
        [CtIfImpl]if ([CtInvocationImpl]isInNightMode()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]mCardAppearance.hasUserDefinedNightMode([CtFieldReadImpl]mCurrentCard)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]content = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.themes.HtmlColors.invertColors([CtVariableReadImpl]content);
            }
        }
        [CtAssignmentImpl][CtVariableWriteImpl]content = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.cardviewer.CardAppearance.convertSmpToHtmlEntity([CtVariableReadImpl]content);
        [CtAssignmentImpl][CtFieldWriteImpl]mCardContent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.text.SpannedString([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCardTemplate.replace([CtLiteralImpl]"::content::", [CtVariableReadImpl]content).replace([CtLiteralImpl]"::style::", [CtVariableReadImpl]style).replace([CtLiteralImpl]"::class::", [CtVariableReadImpl]cardClass));
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"base url = %s", [CtFieldReadImpl]mBaseUrl);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]AnkiDroidApp.getSharedPrefs([CtThisAccessImpl]this).getBoolean([CtLiteralImpl]"html_javascript_debugging", [CtLiteralImpl]false)) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.FileOutputStream f = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileOutputStream([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtInvocationImpl][CtTypeAccessImpl]CollectionHelper.getCurrentAnkiDroidDirectory([CtThisAccessImpl]this), [CtLiteralImpl]"card.html"))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]f.write([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCardContent.toString().getBytes());
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtVariableReadImpl]e, [CtLiteralImpl]"failed to save card");
            }
        }
        [CtInvocationImpl]fillFlashcard();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]mConfigurationChanged) [CtBlockImpl]{
            [CtInvocationImpl]playSounds([CtLiteralImpl]false);[CtCommentImpl]// Play sounds if appropriate

        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Plays sounds (or TTS, if configured) for currently shown side of card.
     *
     * @param doAudioReplay
     * 		indicates an anki desktop-like replay call is desired, whose behavior is identical to
     * 		pressing the keyboard shortcut R on the desktop
     */
    protected [CtTypeReferenceImpl]void playSounds([CtParameterImpl][CtTypeReferenceImpl]boolean doAudioReplay) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean replayQuestion = [CtInvocationImpl][CtInvocationImpl]getConfigForCurrentCard().optBoolean([CtLiteralImpl]"replayq", [CtLiteralImpl]true);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfigForCurrentCard().optBoolean([CtLiteralImpl]"autoplay", [CtLiteralImpl]false) || [CtVariableReadImpl]doAudioReplay) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Use TTS if TTS preference enabled and no other sound source
            [CtTypeReferenceImpl]boolean useTTS = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mSpeakText && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer && [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.hasAnswer()))) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) && [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.hasQuestion()));
            [CtIfImpl][CtCommentImpl]// We need to play the sounds from the proper side of the card
            if ([CtUnaryOperatorImpl]![CtVariableReadImpl]useTTS) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// Text to speech not in effect here
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]doAudioReplay && [CtVariableReadImpl]replayQuestion) && [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// only when all of the above are true will question be played with answer, to match desktop
                    [CtFieldReadImpl]mSoundPlayer.playSounds([CtTypeAccessImpl]Sound.SOUNDS_QUESTION_AND_ANSWER);
                } else [CtIfImpl]if ([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.playSounds([CtTypeAccessImpl]Sound.SOUNDS_ANSWER);
                    [CtIfImpl]if ([CtFieldReadImpl]mUseTimer) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]mUseTimerDynamicMS = [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.getSoundsLength([CtTypeAccessImpl]Sound.SOUNDS_ANSWER);
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// question is displayed
                    [CtFieldReadImpl]mSoundPlayer.playSounds([CtTypeAccessImpl]Sound.SOUNDS_QUESTION);
                    [CtIfImpl][CtCommentImpl]// If the user wants to show the answer automatically
                    if ([CtFieldReadImpl]mUseTimer) [CtBlockImpl]{
                        [CtAssignmentImpl][CtFieldWriteImpl]mUseTimerDynamicMS = [CtInvocationImpl][CtFieldReadImpl]mSoundPlayer.getSoundsLength([CtTypeAccessImpl]Sound.SOUNDS_QUESTION_AND_ANSWER);
                    }
                }
            } else [CtIfImpl][CtCommentImpl]// Text to speech is in effect here
            [CtCommentImpl]// If the question is displayed or if the question should be replayed, read the question
            if ([CtFieldReadImpl]mTtsInitialized) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) || [CtBinaryOperatorImpl]([CtVariableReadImpl]doAudioReplay && [CtVariableReadImpl]replayQuestion)) [CtBlockImpl]{
                    [CtInvocationImpl]readCardText([CtFieldReadImpl]mCurrentCard, [CtTypeAccessImpl]Sound.SOUNDS_QUESTION);
                }
                [CtIfImpl]if ([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                    [CtInvocationImpl]readCardText([CtFieldReadImpl]mCurrentCard, [CtTypeAccessImpl]Sound.SOUNDS_ANSWER);
                }
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mReplayOnTtsInit = [CtLiteralImpl]true;
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Reads the text (using TTS) for the given side of a card.
     *
     * @param card
     * 		The card to play TTS for
     * @param cardSide
     * 		The side of the current card to play TTS for
     */
    private [CtTypeReferenceImpl]void readCardText([CtParameterImpl]final [CtTypeReferenceImpl]com.ichi2.libanki.Card card, [CtParameterImpl]final [CtTypeReferenceImpl]int cardSide) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String cardSideContent;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.ichi2.libanki.Sound.SOUNDS_QUESTION == [CtVariableReadImpl]cardSide) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cardSideContent = [CtInvocationImpl][CtVariableReadImpl]card.q([CtLiteralImpl]true);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.ichi2.libanki.Sound.SOUNDS_ANSWER == [CtVariableReadImpl]cardSide) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cardSideContent = [CtInvocationImpl][CtVariableReadImpl]card.getPureAnswer();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unrecognised cardSide");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clozeReplacement = [CtInvocationImpl][CtThisAccessImpl]this.getString([CtTypeAccessImpl]R.string.reviewer_tts_cloze_spoken_replacement);
        [CtInvocationImpl][CtTypeAccessImpl]ReadText.readCardSide([CtVariableReadImpl]cardSide, [CtVariableReadImpl]cardSideContent, [CtInvocationImpl]com.ichi2.anki.AbstractFlashcardViewer.getDeckIdForCard([CtVariableReadImpl]card), [CtInvocationImpl][CtVariableReadImpl]card.getOrd(), [CtVariableReadImpl]clozeReplacement);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shows the dialogue for selecting TTS for the current card and cardside.
     */
    protected [CtTypeReferenceImpl]void showSelectTtsDialogue() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]mTtsInitialized) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]ReadText.selectTts([CtInvocationImpl]getTextForTts([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.q([CtLiteralImpl]true)), [CtInvocationImpl]com.ichi2.anki.AbstractFlashcardViewer.getDeckIdForCard([CtFieldReadImpl]mCurrentCard), [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getOrd(), [CtTypeAccessImpl]Sound.SOUNDS_QUESTION);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]ReadText.selectTts([CtInvocationImpl]getTextForTts([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getPureAnswer()), [CtInvocationImpl]com.ichi2.anki.AbstractFlashcardViewer.getDeckIdForCard([CtFieldReadImpl]mCurrentCard), [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getOrd(), [CtTypeAccessImpl]Sound.SOUNDS_ANSWER);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getTextForTts([CtParameterImpl][CtTypeReferenceImpl]java.lang.String text) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clozeReplacement = [CtInvocationImpl][CtThisAccessImpl]this.getString([CtTypeAccessImpl]R.string.reviewer_tts_cloze_spoken_replacement);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String clozeReplaced = [CtInvocationImpl][CtVariableReadImpl]text.replace([CtTypeAccessImpl]Template.CLOZE_DELETION_REPLACEMENT, [CtVariableReadImpl]clozeReplacement);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.libanki.Utils.stripHTML([CtVariableReadImpl]clozeReplaced);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the configuration for the current {@link Card}.
     *
     * @return The configuration for the current {@link Card}
     */
    private [CtTypeReferenceImpl]com.ichi2.libanki.DeckConfig getConfigForCurrentCard() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getDecks().confForDid([CtInvocationImpl]com.ichi2.anki.AbstractFlashcardViewer.getDeckIdForCard([CtFieldReadImpl]mCurrentCard));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the deck ID of the given {@link Card}.
     *
     * @param card
     * 		The {@link Card} to get the deck ID
     * @return The deck ID of the {@link Card}
     */
    private static [CtTypeReferenceImpl]long getDeckIdForCard([CtParameterImpl]final [CtTypeReferenceImpl]com.ichi2.libanki.Card card) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Try to get the configuration by the original deck ID (available in case of a cram deck),
        [CtCommentImpl]// else use the direct deck ID (in case of a 'normal' deck.
        return [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]card.getODid() == [CtLiteralImpl]0 ? [CtInvocationImpl][CtVariableReadImpl]card.getDid() : [CtInvocationImpl][CtVariableReadImpl]card.getODid();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void fillFlashcard() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"fillFlashcard()");
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"base url = %s", [CtFieldReadImpl]mBaseUrl);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCardContent == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"fillFlashCard() called with no card content");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String cardContent = [CtInvocationImpl][CtFieldReadImpl]mCardContent.toString();
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtInvocationImpl]loadContentIntoCard([CtVariableReadImpl]card, [CtVariableReadImpl]cardContent));
        [CtInvocationImpl][CtFieldReadImpl]mGestureDetectorImpl.onFillFlashcard();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mShowTimer && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mCardTimer.getVisibility() == [CtFieldReadImpl]android.view.View.INVISIBLE)) [CtBlockImpl]{
            [CtInvocationImpl]switchTopBarVisibility([CtTypeAccessImpl]View.VISIBLE);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
            [CtInvocationImpl]updateForNewCard();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void loadContentIntoCard([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView card, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String content) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]card != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().setHTML5MediaAutoPlay([CtInvocationImpl][CtVariableReadImpl]card.getSettings(), [CtInvocationImpl][CtInvocationImpl]getConfigForCurrentCard().optBoolean([CtLiteralImpl]"autoplay"));
            [CtInvocationImpl][CtVariableReadImpl]card.loadDataWithBaseURL([CtBinaryOperatorImpl][CtFieldReadImpl]mBaseUrl + [CtLiteralImpl]"__viewer__.html", [CtVariableReadImpl]content, [CtLiteralImpl]"text/html", [CtLiteralImpl]"utf-8", [CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.ichi2.libanki.Card getEditorCard() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sEditorCard;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return true if the AnkiDroid preference for writing answer is true and if the Anki Deck CardLayout specifies a
    field to query
     */
    private [CtTypeReferenceImpl]boolean typeAnswer() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]mUseInputTag) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtFieldReadImpl]mTypeCorrect);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void unblockControls() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mControlBlocked = [CtFieldReadImpl]ControlBlock.UNBLOCKED;
        [CtInvocationImpl][CtFieldReadImpl]mCardFrame.setEnabled([CtLiteralImpl]true);
        [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.setEnabled([CtLiteralImpl]true);
        [CtSwitchImpl]switch ([CtFieldReadImpl]mCurrentEase) {
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setClickable([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]true);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setClickable([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]true);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setClickable([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]true);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setClickable([CtLiteralImpl]true);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]true);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]true);
                [CtBreakImpl]break;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mPrefWhiteboard && [CtBinaryOperatorImpl]([CtFieldReadImpl]mWhiteboard != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mWhiteboard.setEnabled([CtLiteralImpl]true);
        }
        [CtIfImpl]if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setEnabled([CtLiteralImpl]true);
        }
        [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtAssignmentImpl][CtFieldWriteImpl]mInAnswer = [CtLiteralImpl]false;
        [CtInvocationImpl]invalidateOptionsMenu();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     *
     * @param quick
     * 		Whether we expect the control to come back quickly
     */
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    protected [CtTypeReferenceImpl]void blockControls([CtParameterImpl][CtTypeReferenceImpl]boolean quick) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]quick) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mControlBlocked = [CtFieldReadImpl]ControlBlock.QUICK;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]mControlBlocked = [CtFieldReadImpl]ControlBlock.SLOW;
        }
        [CtInvocationImpl][CtFieldReadImpl]mCardFrame.setEnabled([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]mFlipCardLayout.setEnabled([CtLiteralImpl]false);
        [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.setVisibility([CtTypeAccessImpl]View.INVISIBLE);
        [CtAssignmentImpl][CtFieldWriteImpl]mInAnswer = [CtLiteralImpl]true;
        [CtSwitchImpl]switch ([CtFieldReadImpl]mCurrentEase) {
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setClickable([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]false);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setClickable([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]false);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setClickable([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]false);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4 :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setClickable([CtLiteralImpl]false);
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtFieldReadImpl]mEase1Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase2Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase3Layout.setEnabled([CtLiteralImpl]false);
                [CtInvocationImpl][CtFieldReadImpl]mEase4Layout.setEnabled([CtLiteralImpl]false);
                [CtBreakImpl]break;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mPrefWhiteboard && [CtBinaryOperatorImpl]([CtFieldReadImpl]mWhiteboard != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mWhiteboard.setEnabled([CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtInvocationImpl]typeAnswer()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mAnswerField.setEnabled([CtLiteralImpl]false);
        }
        [CtInvocationImpl]invalidateOptionsMenu();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Select Text in the webview and automatically sends the selected text to the clipboard. From
     * http://cosmez.blogspot.com/2010/04/webview-emulateshiftheld-on-android.html
     */
    [CtCommentImpl]// Tracked separately in Github as #5024
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
    private [CtTypeReferenceImpl]void selectAndCopyText() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.KeyEvent shiftPressEvent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.view.KeyEvent([CtLiteralImpl]0, [CtLiteralImpl]0, [CtFieldReadImpl]android.view.KeyEvent.ACTION_DOWN, [CtFieldReadImpl]android.view.KeyEvent.KEYCODE_SHIFT_LEFT, [CtLiteralImpl]0, [CtLiteralImpl]0);
            [CtInvocationImpl]processCardAction([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]shiftPressEvent::dispatch);
            [CtInvocationImpl][CtVariableReadImpl]shiftPressEvent.isShiftPressed();
            [CtAssignmentImpl][CtFieldWriteImpl]mIsSelecting = [CtLiteralImpl]true;
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.AssertionError([CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean executeCommand([CtParameterImpl][CtAnnotationImpl]@com.ichi2.anki.ViewerCommandDef
    [CtTypeReferenceImpl]int which) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isControlBlocked() && [CtBinaryOperatorImpl]([CtVariableReadImpl]which != [CtFieldReadImpl]COMMAND_EXIT)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtSwitchImpl]switch ([CtVariableReadImpl]which) {
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_NOTHING :
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_SHOW_ANSWER :
                [CtIfImpl]if ([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
                [CtInvocationImpl]displayCardAnswer();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_EASE1 :
                [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_EASE2 :
                [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_EASE3 :
                [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_EASE4 :
                [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_RECOMMENDED :
                [CtInvocationImpl]flipOrAnswerCard([CtInvocationImpl]getRecommendedEase([CtLiteralImpl]false));
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_FLIP_OR_ANSWER_BETTER_THAN_RECOMMENDED :
                [CtInvocationImpl]flipOrAnswerCard([CtInvocationImpl]getRecommendedEase([CtLiteralImpl]true));
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_EXIT :
                [CtInvocationImpl]closeReviewer([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.RESULT_DEFAULT, [CtLiteralImpl]false);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_UNDO :
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isUndoAvailable()) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
                [CtInvocationImpl]undo();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_EDIT :
                [CtInvocationImpl]editCard();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_TAG :
                [CtInvocationImpl]showTagsDialog();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_MARK :
                [CtInvocationImpl]onMark([CtFieldReadImpl]mCurrentCard);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_LOOKUP :
                [CtInvocationImpl]lookUpOrSelectText();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_BURY_CARD :
                [CtInvocationImpl]dismiss([CtTypeAccessImpl]Collection.DismissType.BURY_CARD);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_BURY_NOTE :
                [CtInvocationImpl]dismiss([CtTypeAccessImpl]Collection.DismissType.BURY_NOTE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_SUSPEND_CARD :
                [CtInvocationImpl]dismiss([CtTypeAccessImpl]Collection.DismissType.SUSPEND_CARD);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_SUSPEND_NOTE :
                [CtInvocationImpl]dismiss([CtTypeAccessImpl]Collection.DismissType.SUSPEND_NOTE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_DELETE :
                [CtInvocationImpl]showDeleteNoteDialog();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_PLAY_MEDIA :
                [CtInvocationImpl]playSounds([CtLiteralImpl]true);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_TOGGLE_FLAG_RED :
                [CtInvocationImpl]toggleFlag([CtTypeAccessImpl]FLAG_RED);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_TOGGLE_FLAG_ORANGE :
                [CtInvocationImpl]toggleFlag([CtTypeAccessImpl]FLAG_ORANGE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_TOGGLE_FLAG_GREEN :
                [CtInvocationImpl]toggleFlag([CtTypeAccessImpl]FLAG_GREEN);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_TOGGLE_FLAG_BLUE :
                [CtInvocationImpl]toggleFlag([CtTypeAccessImpl]FLAG_BLUE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_UNSET_FLAG :
                [CtInvocationImpl]onFlag([CtFieldReadImpl]mCurrentCard, [CtTypeAccessImpl]FLAG_NONE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_ANSWER_FIRST_BUTTON :
                [CtReturnImpl]return [CtInvocationImpl]answerCardIfVisible([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1);
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_ANSWER_SECOND_BUTTON :
                [CtReturnImpl]return [CtInvocationImpl]answerCardIfVisible([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2);
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_ANSWER_THIRD_BUTTON :
                [CtReturnImpl]return [CtInvocationImpl]answerCardIfVisible([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3);
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_ANSWER_FOURTH_BUTTON :
                [CtReturnImpl]return [CtInvocationImpl]answerCardIfVisible([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4);
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_ANSWER_RECOMMENDED :
                [CtReturnImpl]return [CtInvocationImpl]answerCardIfVisible([CtInvocationImpl]getRecommendedEase([CtLiteralImpl]false));
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_PAGE_UP :
                [CtInvocationImpl]onPageUp();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]case [CtFieldReadImpl]COMMAND_PAGE_DOWN :
                [CtInvocationImpl]onPageDown();
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unknown command requested: %s", [CtVariableReadImpl]which);
                [CtReturnImpl]return [CtLiteralImpl]false;
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onPageUp() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// pageUp performs a half scroll, we want a full page
        processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]card.pageUp([CtLiteralImpl]false);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void onPageDown() [CtBlockImpl]{
        [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]card.pageDown([CtLiteralImpl]false);
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void toggleFlag([CtParameterImpl][CtAnnotationImpl]@com.ichi2.anki.reviewer.CardMarker.FlagDef
    [CtTypeReferenceImpl]int flag) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.userFlag() == [CtVariableReadImpl]flag) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Toggle flag: unsetting flag");
            [CtInvocationImpl]onFlag([CtFieldReadImpl]mCurrentCard, [CtTypeAccessImpl]FLAG_NONE);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Toggle flag: Setting flag to %d", [CtVariableReadImpl]flag);
            [CtInvocationImpl]onFlag([CtFieldReadImpl]mCurrentCard, [CtVariableReadImpl]flag);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean answerCardIfVisible([CtParameterImpl][CtTypeReferenceImpl]int ease) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl]answerCard([CtVariableReadImpl]ease);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    protected [CtTypeReferenceImpl]boolean isUndoAvailable() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getCol().undoAvailable();
    }

    [CtClassImpl][CtCommentImpl]// ----------------------------------------------------------------------------
    [CtCommentImpl]// INNER CLASSES
    [CtCommentImpl]// ----------------------------------------------------------------------------
    [CtJavaDocImpl]/**
     * Provides a hook for calling "alert" from javascript. Useful for debugging your javascript.
     */
    public static final class AnkiDroidWebChromeClient extends [CtTypeReferenceImpl]android.webkit.WebChromeClient {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onJsAlert([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String url, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String message, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.JsResult result) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"AbstractFlashcardViewer:: onJsAlert: %s", [CtVariableReadImpl]message);
            [CtInvocationImpl][CtVariableReadImpl]result.confirm();
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void closeReviewer([CtParameterImpl][CtTypeReferenceImpl]int result, [CtParameterImpl][CtTypeReferenceImpl]boolean saveDeck) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Stop the mic recording if still pending
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mMicToolBar != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mMicToolBar.notifyStopRecord();
        }
        [CtIfImpl][CtCommentImpl]// Remove the temporary audio file
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]mTempAudioPath != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File tempAudioPathToDelete = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtFieldReadImpl]mTempAudioPath);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tempAudioPathToDelete.exists()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]tempAudioPathToDelete.delete();
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowAnswerTask);
        [CtInvocationImpl][CtFieldReadImpl]mTimeoutHandler.removeCallbacks([CtFieldReadImpl]mShowQuestionTask);
        [CtInvocationImpl][CtFieldReadImpl]mTimerHandler.removeCallbacks([CtFieldReadImpl]removeChosenAnswerText);
        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]longClickTestRunnable);
        [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]startLongClickAction);
        [CtInvocationImpl][CtThisAccessImpl]this.setResult([CtVariableReadImpl]result);
        [CtIfImpl]if ([CtVariableReadImpl]saveDeck) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]UIUtils.saveCollectionInBackground();
        }
        [CtInvocationImpl]finishWithAnimation([CtTypeAccessImpl]ActivityTransitionAnimation.RIGHT);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void refreshActionBar() [CtBlockImpl]{
        [CtInvocationImpl]supportInvalidateOptionsMenu();
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Fixing bug 720: <input> focus, thanks to pablomouzo on android issue 7189
     */
    class MyWebView extends [CtTypeReferenceImpl]android.webkit.WebView {
        [CtConstructorImpl]public MyWebView([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
            [CtInvocationImpl]super([CtVariableReadImpl]context);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void loadDataWithBaseURL([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
        [CtTypeReferenceImpl]java.lang.String baseUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String data, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
        [CtTypeReferenceImpl]java.lang.String mimeType, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
        [CtTypeReferenceImpl]java.lang.String encoding, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
        [CtTypeReferenceImpl]java.lang.String historyUrl) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this.wasDestroyed()) [CtBlockImpl]{
                [CtInvocationImpl][CtSuperAccessImpl]super.loadDataWithBaseURL([CtVariableReadImpl]baseUrl, [CtVariableReadImpl]data, [CtVariableReadImpl]mimeType, [CtVariableReadImpl]encoding, [CtVariableReadImpl]historyUrl);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Not loading card - Activity is in the process of being destroyed.");
            }
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]void onScrollChanged([CtParameterImpl][CtTypeReferenceImpl]int horiz, [CtParameterImpl][CtTypeReferenceImpl]int vert, [CtParameterImpl][CtTypeReferenceImpl]int oldHoriz, [CtParameterImpl][CtTypeReferenceImpl]int oldVert) [CtBlockImpl]{
            [CtInvocationImpl][CtSuperAccessImpl]super.onScrollChanged([CtVariableReadImpl]horiz, [CtVariableReadImpl]vert, [CtVariableReadImpl]oldHoriz, [CtVariableReadImpl]oldVert);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtVariableReadImpl]horiz - [CtVariableReadImpl]oldHoriz) > [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtBinaryOperatorImpl][CtVariableReadImpl]vert - [CtVariableReadImpl]oldVert)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mIsXScrolling = [CtLiteralImpl]true;
                [CtInvocationImpl][CtFieldReadImpl]scrollHandler.removeCallbacks([CtFieldReadImpl]scrollXRunnable);
                [CtInvocationImpl][CtFieldReadImpl]scrollHandler.postDelayed([CtFieldReadImpl]scrollXRunnable, [CtLiteralImpl]300);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mIsYScrolling = [CtLiteralImpl]true;
                [CtInvocationImpl][CtFieldReadImpl]scrollHandler.removeCallbacks([CtFieldReadImpl]scrollYRunnable);
                [CtInvocationImpl][CtFieldReadImpl]scrollHandler.postDelayed([CtFieldReadImpl]scrollYRunnable, [CtLiteralImpl]300);
            }
        }

        [CtFieldImpl]private final [CtTypeReferenceImpl]android.os.Handler scrollHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Handler();

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Runnable scrollXRunnable = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mIsXScrolling = [CtLiteralImpl]false;
            }
        };

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Runnable scrollYRunnable = [CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]mIsYScrolling = [CtLiteralImpl]false;
            }
        };
    }

    [CtClassImpl]class MyGestureDetector extends [CtTypeReferenceImpl]android.view.GestureDetector.SimpleOnGestureListener {
        [CtFieldImpl][CtCommentImpl]// Android design spec for the size of the status bar.
        private final [CtTypeReferenceImpl]int NO_GESTURE_BORDER_DIP = [CtLiteralImpl]24;

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onFling([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e1, [CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e2, [CtParameterImpl][CtTypeReferenceImpl]float velocityX, [CtParameterImpl][CtTypeReferenceImpl]float velocityY) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"onFling");
            [CtIfImpl][CtCommentImpl]// #5741 - A swipe from the top caused delayedHide to be triggered,
            [CtCommentImpl]// accepting a gesture and quickly disabling the status bar, which wasn't ideal.
            [CtCommentImpl]// it would be lovely to use e1.getEdgeFlags(), but alas, it doesn't work.
            if ([CtInvocationImpl]isTouchingEdge([CtVariableReadImpl]e1)) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"ignoring edge fling");
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtIfImpl][CtCommentImpl]// Go back to immersive mode if the user had temporarily exited it (and then execute swipe gesture)
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mPrefFullscreenReview > [CtLiteralImpl]0) && [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().isImmersiveSystemUiVisible([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this)) [CtBlockImpl]{
                [CtInvocationImpl]delayedHide([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.INITIAL_HIDE_DELAY);
            }
            [CtIfImpl]if ([CtFieldReadImpl]mGesturesEnabled) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]float dy = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e2.getY() - [CtInvocationImpl][CtVariableReadImpl]e1.getY();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]float dx = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]e2.getX() - [CtInvocationImpl][CtVariableReadImpl]e1.getX();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]dx) > [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]dy)) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// horizontal swipe if moved further in x direction than y direction
                        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]dx > [CtFieldReadImpl]AnkiDroidApp.sSwipeMinDistance) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]velocityX) > [CtFieldReadImpl]AnkiDroidApp.sSwipeThresholdVelocity)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsXScrolling)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsSelecting)) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// right
                            executeCommand([CtFieldReadImpl]mGestureSwipeRight);
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]dx < [CtUnaryOperatorImpl](-[CtFieldReadImpl]AnkiDroidApp.sSwipeMinDistance)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]velocityX) > [CtFieldReadImpl]AnkiDroidApp.sSwipeThresholdVelocity)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsXScrolling)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsSelecting)) [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// left
                            executeCommand([CtFieldReadImpl]mGestureSwipeLeft);
                        }
                    } else [CtIfImpl][CtCommentImpl]// otherwise vertical swipe
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]dy > [CtFieldReadImpl]AnkiDroidApp.sSwipeMinDistance) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]velocityY) > [CtFieldReadImpl]AnkiDroidApp.sSwipeThresholdVelocity)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsYScrolling)) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// down
                        executeCommand([CtFieldReadImpl]mGestureSwipeDown);
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]dy < [CtUnaryOperatorImpl](-[CtFieldReadImpl]AnkiDroidApp.sSwipeMinDistance)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtVariableReadImpl]velocityY) > [CtFieldReadImpl]AnkiDroidApp.sSwipeThresholdVelocity)) && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsYScrolling)) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// up
                        executeCommand([CtFieldReadImpl]mGestureSwipeUp);
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]e, [CtLiteralImpl]"onFling Exception");
                }
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]boolean isTouchingEdge([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e1) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int height = [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.getHeight();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int width = [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.getWidth();
            [CtLocalVariableImpl][CtTypeReferenceImpl]float margin = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]NO_GESTURE_BORDER_DIP * [CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl]getResources().getDisplayMetrics().density) + [CtLiteralImpl]0.5F;
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e1.getX() < [CtVariableReadImpl]margin) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e1.getY() < [CtVariableReadImpl]margin)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]height - [CtInvocationImpl][CtVariableReadImpl]e1.getY()) < [CtVariableReadImpl]margin)) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]width - [CtInvocationImpl][CtVariableReadImpl]e1.getX()) < [CtVariableReadImpl]margin);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onDoubleTap([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]mGesturesEnabled) [CtBlockImpl]{
                [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureDoubleTap);
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onSingleTapUp([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e) [CtBlockImpl]{
            [CtIfImpl]if ([CtFieldReadImpl]mTouchStarted) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]longClickHandler.removeCallbacks([CtFieldReadImpl]longClickTestRunnable);
                [CtAssignmentImpl][CtFieldWriteImpl]mTouchStarted = [CtLiteralImpl]false;
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean onSingleTapConfirmed([CtParameterImpl][CtTypeReferenceImpl]android.view.MotionEvent e) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Go back to immersive mode if the user had temporarily exited it (and ignore the tap gesture)
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mPrefFullscreenReview > [CtLiteralImpl]0) && [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().isImmersiveSystemUiVisible([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this)) [CtBlockImpl]{
                [CtInvocationImpl]delayedHide([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.INITIAL_HIDE_DELAY);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtReturnImpl]return [CtInvocationImpl]executeTouchCommand([CtVariableReadImpl]e);
        }

        [CtMethodImpl]protected [CtTypeReferenceImpl]boolean executeTouchCommand([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.view.MotionEvent e) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mGesturesEnabled && [CtUnaryOperatorImpl](![CtFieldReadImpl]mIsSelecting)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int height = [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.getHeight();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int width = [CtInvocationImpl][CtFieldReadImpl]mTouchLayer.getWidth();
                [CtLocalVariableImpl][CtTypeReferenceImpl]float posX = [CtInvocationImpl][CtVariableReadImpl]e.getX();
                [CtLocalVariableImpl][CtTypeReferenceImpl]float posY = [CtInvocationImpl][CtVariableReadImpl]e.getY();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]posX > [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]posY / [CtVariableReadImpl]height) * [CtVariableReadImpl]width)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]posY > [CtBinaryOperatorImpl]([CtVariableReadImpl]height * [CtBinaryOperatorImpl]([CtLiteralImpl]1 - [CtBinaryOperatorImpl]([CtVariableReadImpl]posX / [CtVariableReadImpl]width)))) [CtBlockImpl]{
                        [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureTapRight);
                    } else [CtBlockImpl]{
                        [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureTapTop);
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]posY > [CtBinaryOperatorImpl]([CtVariableReadImpl]height * [CtBinaryOperatorImpl]([CtLiteralImpl]1 - [CtBinaryOperatorImpl]([CtVariableReadImpl]posX / [CtVariableReadImpl]width)))) [CtBlockImpl]{
                    [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureTapBottom);
                } else [CtBlockImpl]{
                    [CtInvocationImpl]executeCommand([CtFieldReadImpl]mGestureTapLeft);
                }
            }
            [CtAssignmentImpl][CtFieldWriteImpl]mIsSelecting = [CtLiteralImpl]false;
            [CtInvocationImpl]showLookupButtonIfNeeded();
            [CtReturnImpl]return [CtLiteralImpl]false;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void onWebViewCreated([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.webkit.WebView webView) [CtBlockImpl]{
            [CtCommentImpl]// intentionally blank
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]void onFillFlashcard() [CtBlockImpl]{
            [CtCommentImpl]// intentionally blank
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]boolean eventCanBeSentToWebView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * #6141 - blocks clicking links from executing "touch" gestures.
     * COULD_BE_BETTER: Make base class static and move this out of the CardViewer
     */
    class LinkDetectingGestureDetector extends [CtTypeReferenceImpl][CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.MyGestureDetector {
        [CtFieldImpl][CtJavaDocImpl]/**
         * A list of events to process when listening to WebView touches
         */
        private [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]android.view.MotionEvent> mDesiredTouchEvents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

        [CtFieldImpl][CtJavaDocImpl]/**
         * A list of events we sent to the WebView (to block double-processing)
         */
        private [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]android.view.MotionEvent> mDispatchedTouchEvents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onFillFlashcard() [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Removing pending touch events for gestures");
            [CtInvocationImpl][CtFieldReadImpl]mDesiredTouchEvents.clear();
            [CtInvocationImpl][CtFieldReadImpl]mDispatchedTouchEvents.clear();
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]boolean eventCanBeSentToWebView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.view.MotionEvent event) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// if we processed the event, we don't want to perform it again
            return [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]mDispatchedTouchEvents.remove([CtVariableReadImpl]event);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]boolean executeTouchCommand([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.view.MotionEvent downEvent) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]downEvent.setAction([CtTypeAccessImpl]MotionEvent.ACTION_DOWN);
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.MotionEvent upEvent = [CtInvocationImpl][CtTypeAccessImpl]android.view.MotionEvent.obtainNoHistory([CtVariableReadImpl]downEvent);
            [CtInvocationImpl][CtVariableReadImpl]upEvent.setAction([CtTypeAccessImpl]MotionEvent.ACTION_UP);
            [CtInvocationImpl][CtCommentImpl]// mark the events we want to process
            [CtFieldReadImpl]mDesiredTouchEvents.add([CtVariableReadImpl]downEvent);
            [CtInvocationImpl][CtFieldReadImpl]mDesiredTouchEvents.add([CtVariableReadImpl]upEvent);
            [CtInvocationImpl][CtCommentImpl]// mark the events to can guard against double-processing
            [CtFieldReadImpl]mDispatchedTouchEvents.add([CtVariableReadImpl]downEvent);
            [CtInvocationImpl][CtFieldReadImpl]mDispatchedTouchEvents.add([CtVariableReadImpl]upEvent);
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Dispatching touch events");
            [CtInvocationImpl]processCardAction([CtLambdaImpl]([CtParameterImpl] card) -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]card.dispatchTouchEvent([CtVariableReadImpl]downEvent);
                [CtInvocationImpl][CtVariableReadImpl]card.dispatchTouchEvent([CtVariableReadImpl]upEvent);
            });
            [CtReturnImpl]return [CtLiteralImpl]false;
        }

        [CtMethodImpl][CtAnnotationImpl]@android.annotation.SuppressLint([CtLiteralImpl]"ClickableViewAccessibility")
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onWebViewCreated([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl]android.webkit.WebView webView) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Initializing WebView touch handler");
            [CtInvocationImpl][CtVariableReadImpl]webView.setOnTouchListener([CtLambdaImpl]([CtParameterImpl] webViewAsView,[CtParameterImpl] motionEvent) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mDesiredTouchEvents.remove([CtVariableReadImpl]motionEvent)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
                [CtIfImpl][CtCommentImpl]// We need an associated up event so the WebView doesn't keep a selection
                [CtCommentImpl]// But we don't want to handle this as a touch event.
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]motionEvent.getAction() == [CtVariableReadImpl]MotionEvent.ACTION_UP) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]android.webkit.WebView card = [CtVariableReadImpl](([CtTypeReferenceImpl]android.webkit.WebView) (webViewAsView));
                [CtLocalVariableImpl][CtTypeReferenceImpl]android.webkit.WebView.HitTestResult result = [CtInvocationImpl][CtVariableReadImpl]card.getHitTestResult();
                [CtIfImpl]if ([CtInvocationImpl]isLinkClick([CtVariableReadImpl]result)) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.v([CtLiteralImpl]"Detected link click - ignoring gesture dispatch");
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.v([CtLiteralImpl]"Executing continuation for click type: %d", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null ? [CtUnaryOperatorImpl]-[CtLiteralImpl]178 : [CtInvocationImpl][CtVariableReadImpl]result.getType());
                [CtInvocationImpl][CtSuperAccessImpl]super.executeTouchCommand([CtVariableReadImpl]motionEvent);
                [CtReturnImpl]return [CtLiteralImpl]true;
            });
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]boolean isLinkClick([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView.HitTestResult result) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int type = [CtInvocationImpl][CtVariableReadImpl]result.getType();
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]android.webkit.WebView.HitTestResult.SRC_ANCHOR_TYPE) || [CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE);
        }
    }

    [CtFieldImpl]protected final [CtTypeReferenceImpl]android.os.Handler mFullScreenHandler = [CtNewClassImpl]new [CtTypeReferenceImpl]android.os.Handler()[CtClassImpl] {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void handleMessage([CtParameterImpl][CtTypeReferenceImpl]android.os.Message msg) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mPrefFullscreenReview > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.ichi2.compat.CompatHelper.getCompat().setFullScreen([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this);
            }
        }
    };

    [CtMethodImpl]protected [CtTypeReferenceImpl]void delayedHide([CtParameterImpl][CtTypeReferenceImpl]int delayMillis) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Fullscreen delayed hide in %dms", [CtVariableReadImpl]delayMillis);
        [CtInvocationImpl][CtFieldReadImpl]mFullScreenHandler.removeMessages([CtLiteralImpl]0);
        [CtInvocationImpl][CtFieldReadImpl]mFullScreenHandler.sendEmptyMessageDelayed([CtLiteralImpl]0, [CtVariableReadImpl]delayMillis);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Removes first occurrence in answerContent of any audio that is present due to use of
     * {{FrontSide}} on the answer.
     *
     * @param answerContent
     * 		The content from which to remove front side audio.
     * @return The content stripped of audio due to {{FrontSide}} inclusion.
     */
    private [CtTypeReferenceImpl]java.lang.String removeFrontSideAudio([CtParameterImpl][CtTypeReferenceImpl]java.lang.String answerContent) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String answerFormat = [CtInvocationImpl]getAnswerFormat();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String newAnswerContent = [CtVariableReadImpl]answerContent;
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]answerFormat.contains([CtLiteralImpl]"{{FrontSide}}")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// possible audio removal necessary
            [CtTypeReferenceImpl]java.lang.String frontSideFormat = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard._getQA([CtLiteralImpl]false).get([CtLiteralImpl]"q");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.regex.Matcher audioReferences = [CtInvocationImpl][CtTypeAccessImpl]Sound.sSoundPattern.matcher([CtVariableReadImpl]frontSideFormat);
            [CtWhileImpl][CtCommentImpl]// remove the first instance of audio contained in "{{FrontSide}}"
            while ([CtInvocationImpl][CtVariableReadImpl]audioReferences.find()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]newAnswerContent = [CtInvocationImpl][CtVariableReadImpl]newAnswerContent.replaceFirst([CtInvocationImpl][CtTypeAccessImpl]java.util.regex.Pattern.quote([CtInvocationImpl][CtVariableReadImpl]audioReferences.group()), [CtLiteralImpl]"");
            } 
        }
        [CtReturnImpl]return [CtVariableReadImpl]newAnswerContent;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Public method to start new video player activity
     */
    public [CtTypeReferenceImpl]void playVideo([CtParameterImpl][CtTypeReferenceImpl]java.lang.String path) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Launching Video: %s", [CtVariableReadImpl]path);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent videoPlayer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtThisAccessImpl]this, [CtFieldReadImpl]com.ichi2.anki.VideoPlayer.class);
        [CtInvocationImpl][CtVariableReadImpl]videoPlayer.putExtra([CtLiteralImpl]"path", [CtVariableReadImpl]path);
        [CtInvocationImpl]startActivityWithoutAnimation([CtVariableReadImpl]videoPlayer);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Callback for when TTS has been initialized.
     */
    public [CtTypeReferenceImpl]void ttsInitialized() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mTtsInitialized = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtFieldReadImpl]mReplayOnTtsInit) [CtBlockImpl]{
            [CtInvocationImpl]playSounds([CtLiteralImpl]true);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawMark() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]mCardMarker.displayMark([CtInvocationImpl]shouldDisplayMark());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean shouldDisplayMark() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.note().hasTag([CtLiteralImpl]"marked");
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void onMark([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Card card) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]card == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.libanki.Note note = [CtInvocationImpl][CtVariableReadImpl]card.note();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]note.hasTag([CtLiteralImpl]"marked")) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]note.delTag([CtLiteralImpl]"marked");
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]note.addTag([CtLiteralImpl]"marked");
        }
        [CtInvocationImpl][CtVariableReadImpl]note.flush();
        [CtInvocationImpl]refreshActionBar();
        [CtInvocationImpl]drawMark();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void drawFlag() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mCurrentCard == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtFieldReadImpl]mCardMarker.displayFlag([CtInvocationImpl]getFlagToDisplay());
    }

    [CtMethodImpl][CtAnnotationImpl]@com.ichi2.anki.reviewer.CardMarker.FlagDef
    protected [CtTypeReferenceImpl]int getFlagToDisplay() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.userFlag();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void onFlag([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Card card, [CtParameterImpl][CtAnnotationImpl]@com.ichi2.anki.reviewer.CardMarker.FlagDef
    [CtTypeReferenceImpl]int flag) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]card == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]card.setUserFlag([CtVariableReadImpl]flag);
        [CtInvocationImpl][CtVariableReadImpl]card.flush();
        [CtInvocationImpl]refreshActionBar();
        [CtInvocationImpl]drawFlag();
        [CtCommentImpl]/* Following code would allow to update value of {{cardFlag}}.
        Anki does not update this value when a flag is changed, so
        currently this code would do something that anki itself
        does not do. I hope in the future Anki will correct that
        and this code may becomes useful.

        card._getQA(true); //force reload. Useful iff {{cardFlag}} occurs in the template
        if (sDisplayAnswer) {
        displayCardAnswer();
        } else {
        displayCardQuestion();
        }
         */
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void dismiss([CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.ichi2.libanki.Collection.DismissType type) [CtBlockImpl]{
        [CtInvocationImpl]blockControls([CtLiteralImpl]false);
        [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]DISMISS, [CtFieldReadImpl]mDismissCardHandler, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtFieldReadImpl]mCurrentCard, [CtVariableReadImpl]type }));
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * Signals from a WebView represent actions with no parameters
     */
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    static class WebViewSignalParserUtils {
        [CtFieldImpl][CtJavaDocImpl]/**
         * A signal which we did not know how to handle
         */
        public static final [CtTypeReferenceImpl]int SIGNAL_UNHANDLED = [CtLiteralImpl]0;

        [CtFieldImpl][CtJavaDocImpl]/**
         * A known signal which should perform a noop
         */
        public static final [CtTypeReferenceImpl]int SIGNAL_NOOP = [CtLiteralImpl]1;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int TYPE_FOCUS = [CtLiteralImpl]2;

        [CtFieldImpl][CtJavaDocImpl]/**
         * Tell the app that we no longer want to focus the WebView and should instead return keyboard focus to a
         * native answer input method.
         */
        public static final [CtTypeReferenceImpl]int RELINQUISH_FOCUS = [CtLiteralImpl]3;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int SHOW_ANSWER = [CtLiteralImpl]4;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int ANSWER_ORDINAL_1 = [CtLiteralImpl]5;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int ANSWER_ORDINAL_2 = [CtLiteralImpl]6;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int ANSWER_ORDINAL_3 = [CtLiteralImpl]7;

        [CtFieldImpl]public static final [CtTypeReferenceImpl]int ANSWER_ORDINAL_4 = [CtLiteralImpl]8;

        [CtMethodImpl]public static [CtTypeReferenceImpl]int getSignalFromUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]url) {
                [CtCaseImpl]case [CtLiteralImpl]"signal:typefocus" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.TYPE_FOCUS;
                [CtCaseImpl]case [CtLiteralImpl]"signal:relinquishFocus" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.RELINQUISH_FOCUS;
                [CtCaseImpl]case [CtLiteralImpl]"signal:show_answer" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.SHOW_ANSWER;
                [CtCaseImpl]case [CtLiteralImpl]"signal:answer_ease1" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.ANSWER_ORDINAL_1;
                [CtCaseImpl]case [CtLiteralImpl]"signal:answer_ease2" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.ANSWER_ORDINAL_2;
                [CtCaseImpl]case [CtLiteralImpl]"signal:answer_ease3" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.ANSWER_ORDINAL_3;
                [CtCaseImpl]case [CtLiteralImpl]"signal:answer_ease4" :
                    [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.ANSWER_ORDINAL_4;
                [CtCaseImpl]default :
                    [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:answer_ease")) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unhandled signal: ease value: %s", [CtVariableReadImpl]url);
                [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.SIGNAL_NOOP;
            }
            [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.SIGNAL_UNHANDLED;[CtCommentImpl]// unknown, or not a signal.

        }
    }

    [CtClassImpl]protected class CardViewerWebClient extends [CtTypeReferenceImpl]android.webkit.WebViewClient {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@android.annotation.TargetApi([CtFieldReadImpl]Build.VERSION_CODES.N)
        public [CtTypeReferenceImpl]boolean shouldOverrideUrlLoading([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebResourceRequest request) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String url = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getUrl().toString();
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Obtained URL from card: '%s'", [CtVariableReadImpl]url);
            [CtReturnImpl]return [CtInvocationImpl]filterUrl([CtVariableReadImpl]url);
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@android.annotation.TargetApi([CtFieldReadImpl]Build.VERSION_CODES.N)
        public [CtTypeReferenceImpl]android.webkit.WebResourceResponse shouldInterceptRequest([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebResourceRequest request) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.webkit.WebResourceResponse webResourceResponse = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.ichi2.utils.AdaptionUtil.hasWebBrowser([CtInvocationImpl]getBaseContext())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String scheme = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]request.getUrl().getScheme().trim();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtLiteralImpl]"http".equalsIgnoreCase([CtVariableReadImpl]scheme) || [CtInvocationImpl][CtLiteralImpl]"https".equalsIgnoreCase([CtVariableReadImpl]scheme)) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String response = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.no_outgoing_link_in_cardbrowser);
                    [CtAssignmentImpl][CtVariableWriteImpl]webResourceResponse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.webkit.WebResourceResponse([CtLiteralImpl]"text/html", [CtLiteralImpl]"utf-8", [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]response.getBytes()));
                }
            }
            [CtReturnImpl]return [CtVariableReadImpl]webResourceResponse;
        }

        [CtMethodImpl][CtCommentImpl]// tracked as #5017 in github
        [CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"deprecation")
        public [CtTypeReferenceImpl]boolean shouldOverrideUrlLoading([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]filterUrl([CtVariableReadImpl]url);
        }

        [CtMethodImpl][CtCommentImpl]// Filter any links using the custom "playsound" protocol defined in Sound.java.
        [CtCommentImpl]// We play sounds through these links when a user taps the sound icon.
        private [CtTypeReferenceImpl]boolean filterUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"playsound:")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Send a message that will be handled on the UI thread.
                [CtTypeReferenceImpl]android.os.Message msg = [CtInvocationImpl][CtTypeAccessImpl]android.os.Message.obtain();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String soundPath = [CtInvocationImpl][CtVariableReadImpl]url.replaceFirst([CtLiteralImpl]"playsound:", [CtLiteralImpl]"");
                [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]msg.obj = [CtVariableReadImpl]soundPath;
                [CtInvocationImpl][CtFieldReadImpl]mHandler.sendMessage([CtVariableReadImpl]msg);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"file") || [CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"data:")) [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;[CtCommentImpl]// Let the webview load files, i.e. local images.

            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"typeblurtext:")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Store the text the javascript has send us…
                [CtFieldWriteImpl]mTypeInput = [CtInvocationImpl]decodeUrl([CtInvocationImpl][CtVariableReadImpl]url.replaceFirst([CtLiteralImpl]"typeblurtext:", [CtLiteralImpl]""));
                [CtInvocationImpl][CtCommentImpl]// … and show the “SHOW ANSWER” button again.
                [CtFieldReadImpl]mFlipCardLayout.setVisibility([CtTypeAccessImpl]View.VISIBLE);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"typeentertext:")) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Store the text the javascript has send us…
                [CtFieldWriteImpl]mTypeInput = [CtInvocationImpl]decodeUrl([CtInvocationImpl][CtVariableReadImpl]url.replaceFirst([CtLiteralImpl]"typeentertext:", [CtLiteralImpl]""));
                [CtInvocationImpl][CtCommentImpl]// … and show the answer.
                [CtFieldReadImpl]mFlipCardLayout.performClick();
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl][CtCommentImpl]// Show options menu from WebView
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:anki_show_options_menu")) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]isFullscreen()) [CtBlockImpl]{
                    [CtInvocationImpl]openOptionsMenu();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ankidroid_turn_on_fullscreen_options_menu), [CtLiteralImpl]true);
                }
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl][CtCommentImpl]// Show Navigation Drawer from WebView
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:anki_show_navigation_drawer")) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl]isFullscreen()) [CtBlockImpl]{
                    [CtInvocationImpl][CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this.onNavigationPressed();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.ankidroid_turn_on_fullscreen_nav_drawer), [CtLiteralImpl]true);
                }
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl][CtCommentImpl]// card.html reload
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:reload_card_html")) [CtBlockImpl]{
                [CtInvocationImpl]redrawCard();
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl][CtCommentImpl]// mark card using javascript
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:mark_current_card")) [CtBlockImpl]{
                [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_MARK);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtIfImpl][CtCommentImpl]// flag card (blue, green, orange, red) using javascript from AnkiDroid webview
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:flag_")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mFlag = [CtInvocationImpl][CtVariableReadImpl]url.replaceFirst([CtLiteralImpl]"signal:flag_", [CtLiteralImpl]"");
                [CtSwitchImpl]switch ([CtVariableReadImpl]mFlag) {
                    [CtCaseImpl]case [CtLiteralImpl]"none" :
                        [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_UNSET_FLAG);
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtCaseImpl]case [CtLiteralImpl]"red" :
                        [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_TOGGLE_FLAG_RED);
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtCaseImpl]case [CtLiteralImpl]"orange" :
                        [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_TOGGLE_FLAG_ORANGE);
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtCaseImpl]case [CtLiteralImpl]"green" :
                        [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_TOGGLE_FLAG_GREEN);
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtCaseImpl]case [CtLiteralImpl]"blue" :
                        [CtInvocationImpl]executeCommand([CtTypeAccessImpl]COMMAND_TOGGLE_FLAG_BLUE);
                        [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtCaseImpl]default :
                        [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"No such Flag found.");
                        [CtReturnImpl]return [CtLiteralImpl]true;
                }
            }
            [CtIfImpl][CtCommentImpl]// Show toast using JS
            if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"signal:anki_show_toast:")) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msg = [CtInvocationImpl][CtVariableReadImpl]url.replaceFirst([CtLiteralImpl]"signal:anki_show_toast:", [CtLiteralImpl]"");
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msgDecode = [CtInvocationImpl]decodeUrl([CtVariableReadImpl]msg);
                [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtVariableReadImpl]msgDecode, [CtLiteralImpl]true);
                [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int signalOrdinal = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.getSignalFromUrl([CtVariableReadImpl]url);
            [CtSwitchImpl]switch ([CtVariableReadImpl]signalOrdinal) {
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]SIGNAL_UNHANDLED :
                    [CtBreakImpl]break;[CtCommentImpl]// continue parsing

                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]SIGNAL_NOOP :
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]TYPE_FOCUS :
                    [CtInvocationImpl][CtCommentImpl]// Hide the “SHOW ANSWER” button when the input has focus. The soft keyboard takes up enough
                    [CtCommentImpl]// space by itself.
                    [CtFieldReadImpl]mFlipCardLayout.setVisibility([CtTypeAccessImpl]View.GONE);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]RELINQUISH_FOCUS :
                    [CtInvocationImpl][CtCommentImpl]// #5811 - The WebView could be focused via mouse. Allow components to return focus to Android.
                    focusAnswerCompletionField();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                    [CtJavaDocImpl]/**
                     * Call displayCardAnswer() and answerCard() from anki deck template using javascript
                     *  See card.js in assets/scripts folder
                     */
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]SHOW_ANSWER :
                    [CtIfImpl][CtCommentImpl]// display answer when showAnswer() called from card.js
                    if ([CtUnaryOperatorImpl]![CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer) [CtBlockImpl]{
                        [CtInvocationImpl]displayCardAnswer();
                    }
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]ANSWER_ORDINAL_1 :
                    [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_1);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]ANSWER_ORDINAL_2 :
                    [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_2);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]ANSWER_ORDINAL_3 :
                    [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_3);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]case [CtFieldReadImpl][CtTypeAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.WebViewSignalParserUtils.[CtFieldReferenceImpl]ANSWER_ORDINAL_4 :
                    [CtInvocationImpl]flipOrAnswerCard([CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.EASE_4);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                [CtCaseImpl]default :
                    [CtInvocationImpl][CtCommentImpl]// We know it was a signal, but forgot a case in the case statement.
                    [CtCommentImpl]// This is not the same as SIGNAL_UNHANDLED, where it isn't a known signal.
                    [CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unhandled signal case: %d", [CtVariableReadImpl]signalOrdinal);
                    [CtReturnImpl]return [CtLiteralImpl]true;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"intent:")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtInvocationImpl][CtTypeAccessImpl]android.content.Intent.parseUri([CtVariableReadImpl]url, [CtTypeAccessImpl]Intent.URI_INTENT_SCHEME);
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]url.startsWith([CtLiteralImpl]"android-app:")) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]Build.VERSION.SDK_INT < [CtFieldReadImpl]Build.VERSION_CODES.LOLLIPOP_MR1) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtInvocationImpl][CtTypeAccessImpl]android.content.Intent.parseUri([CtVariableReadImpl]url, [CtLiteralImpl]0);
                        [CtInvocationImpl][CtVariableReadImpl]intent.setData([CtLiteralImpl]null);
                        [CtInvocationImpl][CtVariableReadImpl]intent.setPackage([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtVariableReadImpl]url).getHost());
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtInvocationImpl][CtTypeAccessImpl]android.content.Intent.parseUri([CtVariableReadImpl]url, [CtTypeAccessImpl]Intent.URI_ANDROID_APP_SCHEME);
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]intent != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getPackageManager().resolveActivity([CtVariableReadImpl]intent, [CtLiteralImpl]0) == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String packageName = [CtInvocationImpl][CtVariableReadImpl]intent.getPackage();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]packageName == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Not using resolved intent uri because not available: %s", [CtVariableReadImpl]intent);
                            [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtLiteralImpl]null;
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Resolving intent uri to market uri because not available: %s", [CtVariableReadImpl]intent);
                            [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]android.content.Intent.ACTION_VIEW, [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtBinaryOperatorImpl][CtLiteralImpl]"market://details?id=" + [CtVariableReadImpl]packageName));
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getPackageManager().resolveActivity([CtVariableReadImpl]intent, [CtLiteralImpl]0) == [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtLiteralImpl]null;
                            }
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// https://developer.chrome.com/multidevice/android/intents says that we should remove this
                        [CtVariableReadImpl]intent.addCategory([CtTypeAccessImpl]Intent.CATEGORY_BROWSABLE);
                    }
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.w([CtLiteralImpl]"Unable to parse intent uri: %s because: %s", [CtVariableReadImpl]url, [CtInvocationImpl][CtVariableReadImpl]t.getMessage());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]intent == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Opening external link \"%s\" with an Intent", [CtVariableReadImpl]url);
                [CtAssignmentImpl][CtVariableWriteImpl]intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtFieldReadImpl]android.content.Intent.ACTION_VIEW, [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtVariableReadImpl]url));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Opening resolved external link \"%s\" with an Intent: %s", [CtVariableReadImpl]url, [CtVariableReadImpl]intent);
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]startActivityWithoutAnimation([CtVariableReadImpl]intent);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]android.content.ActivityNotFoundException e) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();[CtCommentImpl]// Don't crash if the intent is not handled

            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String decodeUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.net.URLDecoder.decode([CtVariableReadImpl]url, [CtLiteralImpl]"UTF-8");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.UnsupportedEncodingException e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]e, [CtLiteralImpl]"UTF-8 isn't supported as an encoding?");
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtVariableReadImpl]e, [CtLiteralImpl]"Exception decoding: '%s'", [CtVariableReadImpl]url);
                [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtInvocationImpl]getString([CtTypeAccessImpl]R.string.card_viewer_url_decode_error), [CtLiteralImpl]true);
            }
            [CtReturnImpl]return [CtLiteralImpl]"";
        }

        [CtMethodImpl][CtCommentImpl]// Run any post-load events in javascript that rely on the window being completely loaded.
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void onPageFinished([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"onPageFinished triggered");
            [CtInvocationImpl]drawFlag();
            [CtInvocationImpl]drawMark();
            [CtInvocationImpl][CtVariableReadImpl]view.loadUrl([CtLiteralImpl]"javascript:onPageFinished();");
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Fix: #5780 - WebView Renderer OOM crashes reviewer
         */
        [CtAnnotationImpl]@java.lang.Override
        [CtAnnotationImpl]@android.annotation.TargetApi([CtFieldReadImpl]Build.VERSION_CODES.O)
        public [CtTypeReferenceImpl]boolean onRenderProcessGone([CtParameterImpl][CtTypeReferenceImpl]android.webkit.WebView view, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.RenderProcessGoneDetail detail) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Obtaining write lock for card");
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.concurrent.locks.Lock writeLock = [CtInvocationImpl][CtFieldReadImpl]mCardLock.writeLock();
            [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Obtained write lock for card");
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]writeLock.lock();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mCard == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]mCard.equals([CtVariableReadImpl]view))) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// A view crashed that wasn't ours.
                    [CtCommentImpl]// We have nothing to handle. Returning false is a desire to crash, so return true.
                    [CtTypeAccessImpl]timber.log.Timber.i([CtLiteralImpl]"Unrelated WebView Renderer terminated. Crashed: %b", [CtInvocationImpl][CtVariableReadImpl]detail.didCrash());
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtLiteralImpl]"WebView Renderer process terminated. Crashed: %b", [CtInvocationImpl][CtVariableReadImpl]detail.didCrash());
                [CtInvocationImpl][CtCommentImpl]// Destroy the current WebView (to ensure WebView is GCed).
                [CtCommentImpl]// Otherwise, we get the following error:
                [CtCommentImpl]// "crash wasn't handled by all associated webviews, triggering application crash"
                [CtFieldReadImpl]mCardFrame.removeAllViews();
                [CtInvocationImpl][CtFieldReadImpl]mCardFrameParent.removeView([CtFieldReadImpl]mCardFrame);
                [CtInvocationImpl][CtCommentImpl]// destroy after removal from the view - produces logcat warnings otherwise
                destroyWebView([CtFieldReadImpl]mCard);
                [CtAssignmentImpl][CtFieldWriteImpl]mCard = [CtLiteralImpl]null;
                [CtAssignmentImpl][CtCommentImpl]// inflate a new instance of mCardFrame
                [CtFieldWriteImpl]mCardFrame = [CtInvocationImpl]inflateNewView([CtTypeAccessImpl]R.id.flashcard);
                [CtInvocationImpl][CtCommentImpl]// Even with the above, I occasionally saw the above error. Manually trigger the GC.
                [CtCommentImpl]// I'll keep this line unless I see another crash, which would point to another underlying issue.
                [CtTypeAccessImpl]java.lang.System.gc();
                [CtLocalVariableImpl][CtCommentImpl]// We only want to show one message per branch.
                [CtCommentImpl]// It's not necessarily an OOM crash, false implies a general code which is for "system terminated".
                [CtTypeReferenceImpl]int errorCauseId = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]detail.didCrash()) ? [CtFieldReadImpl]R.string.webview_crash_unknown : [CtFieldReadImpl]R.string.webview_crash_oom;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorCauseString = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtVariableReadImpl]errorCauseId);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]canRecoverFromWebViewRendererCrash()) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtLiteralImpl]"Unrecoverable WebView Render crash");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorMessage = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.webview_crash_fatal, [CtVariableReadImpl]errorCauseString);
                    [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtVariableReadImpl]errorMessage, [CtLiteralImpl]false);
                    [CtInvocationImpl]finishWithoutAnimation();
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtIfImpl]if ([CtInvocationImpl]webViewRendererLastCrashedOnCard([CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getId())) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.e([CtLiteralImpl]"Web Renderer crash loop on card: %d", [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getId());
                    [CtInvocationImpl]displayRenderLoopDialog([CtFieldReadImpl]mCurrentCard, [CtVariableReadImpl]detail);
                    [CtReturnImpl]return [CtLiteralImpl]true;
                }
                [CtAssignmentImpl][CtCommentImpl]// If we get here, the error is non-fatal and we should re-render the WebView
                [CtCommentImpl]// This logic may need to be better defined. The card could have changed by the time we get here.
                [CtFieldWriteImpl]lastCrashingCardId = [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getId();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nonFatalError = [CtInvocationImpl][CtInvocationImpl]getResources().getString([CtTypeAccessImpl]R.string.webview_crash_nonfatal, [CtVariableReadImpl]errorCauseString);
                [CtInvocationImpl][CtTypeAccessImpl]UIUtils.showThemedToast([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this, [CtVariableReadImpl]nonFatalError, [CtLiteralImpl]false);
                [CtInvocationImpl][CtCommentImpl]// we need to add at index 0 so gestures still go through.
                [CtFieldReadImpl]mCardFrameParent.addView([CtFieldReadImpl]mCardFrame, [CtLiteralImpl]0);
                [CtInvocationImpl]recreateWebView();
            } finally [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]writeLock.unlock();
                [CtInvocationImpl][CtTypeAccessImpl]timber.log.Timber.d([CtLiteralImpl]"Relinquished writeLock");
            }
            [CtInvocationImpl]displayCardQuestion();
            [CtReturnImpl][CtCommentImpl]// We handled the crash and can continue.
            return [CtLiteralImpl]true;
        }

        [CtMethodImpl][CtAnnotationImpl]@android.annotation.TargetApi([CtFieldReadImpl]Build.VERSION_CODES.O)
        private [CtTypeReferenceImpl]void displayRenderLoopDialog([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Card mCurrentCard, [CtParameterImpl][CtTypeReferenceImpl]android.webkit.RenderProcessGoneDetail detail) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cardInformation = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.toString([CtInvocationImpl][CtVariableReadImpl]mCurrentCard.getId());
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.res.Resources res = [CtInvocationImpl]getResources();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String errorDetails = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]detail.didCrash()) ? [CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.webview_crash_unknwon_detailed) : [CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.webview_crash_oom_details);
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.afollestad.materialdialogs.MaterialDialog.Builder([CtThisAccessImpl]com.ichi2.anki.AbstractFlashcardViewer.this).title([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.webview_crash_loop_dialog_title)).content([CtInvocationImpl][CtVariableReadImpl]res.getString([CtTypeAccessImpl]R.string.webview_crash_loop_dialog_content, [CtVariableReadImpl]cardInformation, [CtVariableReadImpl]errorDetails)).positiveText([CtTypeAccessImpl]R.string.dialog_ok).cancelable([CtLiteralImpl]false).canceledOnTouchOutside([CtLiteralImpl]false).onPositive([CtLambdaImpl]([CtParameterImpl] materialDialog,[CtParameterImpl] dialogAction) -> [CtInvocationImpl]finishWithoutAnimation()).show();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting(otherwise = [CtFieldReadImpl]androidx.annotation.VisibleForTesting.NONE)
    protected [CtTypeReferenceImpl]java.lang.String getTypedInputText() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mTypeInput;
    }

    [CtMethodImpl][CtAnnotationImpl]@android.annotation.SuppressLint([CtLiteralImpl]"WebViewApiAvailability")
    [CtAnnotationImpl]@androidx.annotation.VisibleForTesting(otherwise = [CtFieldReadImpl]androidx.annotation.VisibleForTesting.NONE)
    [CtTypeReferenceImpl]void handleUrlFromJavascript([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]Build.VERSION.SDK_INT >= [CtFieldReadImpl]Build.VERSION_CODES.O) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// WebViewCompat recommended here, but I'll avoid the dependency as it's test code
            [CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.CardViewerWebClient c = [CtInvocationImpl](([CtTypeReferenceImpl]com.ichi2.anki.AbstractFlashcardViewer.CardViewerWebClient) ([CtFieldReadImpl][CtThisAccessImpl]this.mCard.getWebViewClient()));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Couldn't obtain WebView - maybe it wasn't created yet");
            }
            [CtInvocationImpl][CtVariableReadImpl]c.filterUrl([CtVariableReadImpl]url);
        } else [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"Can't get WebViewClient due to Android API");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting
    [CtTypeReferenceImpl]void loadInitialCard() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.async.CollectionTask.launchCollectionTask([CtTypeAccessImpl]ANSWER_CARD, [CtInvocationImpl]mAnswerCardHandler([CtLiteralImpl]false), [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.ichi2.async.TaskData([CtLiteralImpl]null, [CtLiteralImpl]0));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]ReviewerUi.ControlBlock getControlBlocked() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mControlBlocked;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isDisplayingAnswer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.ichi2.anki.AbstractFlashcardViewer.sDisplayAnswer;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isControlBlocked() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getControlBlocked() != [CtFieldReadImpl]ControlBlock.UNBLOCKED;
    }

    [CtMethodImpl][CtAnnotationImpl]@androidx.annotation.VisibleForTesting(otherwise = [CtFieldReadImpl]androidx.annotation.VisibleForTesting.NONE)
    static [CtTypeReferenceImpl]void setEditorCard([CtParameterImpl][CtTypeReferenceImpl]com.ichi2.libanki.Card card) [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// I don't see why we don't do this by intent.
        [CtFieldWriteImpl]com.ichi2.anki.AbstractFlashcardViewer.sEditorCard = [CtVariableReadImpl]card;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void showTagsDialog() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> tags = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCol().getTags().all());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> selTags = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]mCurrentCard.note().getTags());
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.ichi2.anki.dialogs.TagsDialog.TagsDialogListener tagsDialogListener = [CtLambdaImpl]([CtParameterImpl] selectedTags,[CtParameterImpl] option) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mCurrentCard.note().getTags().equals([CtVariableReadImpl]selectedTags)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String tagString = [CtInvocationImpl][CtTypeAccessImpl]android.text.TextUtils.join([CtLiteralImpl]" ", [CtVariableReadImpl]selectedTags);
                [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.libanki.Note note = [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]mCurrentCard.note();
                [CtInvocationImpl][CtVariableReadImpl]note.setTagsFromStr([CtVariableReadImpl]tagString);
                [CtInvocationImpl][CtVariableReadImpl]note.flush();
                [CtInvocationImpl][CtCommentImpl]// Reload current card to reflect tag changes
                displayCardQuestion([CtLiteralImpl]true);
            }
        };
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.ichi2.anki.dialogs.TagsDialog dialog = [CtInvocationImpl][CtTypeAccessImpl]com.ichi2.anki.dialogs.TagsDialog.newInstance([CtTypeAccessImpl]TagsDialog.TYPE_ADD_TAG, [CtVariableReadImpl]selTags, [CtVariableReadImpl]tags);
        [CtInvocationImpl][CtVariableReadImpl]dialog.setTagsDialogListener([CtVariableReadImpl]tagsDialogListener);
        [CtInvocationImpl]showDialogFragment([CtVariableReadImpl]dialog);
    }

    [CtClassImpl][CtCommentImpl]/* Javascript Interface class for calling Java function from AnkiDroid WebView
    see card.js for available functions
     */
    public class JavaScriptFunction {
        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetNewCardCount() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]newCount.toString();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetLrnCardCount() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]lrnCount.toString();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetRevCardCount() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]revCount.toString();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]int ankiGetETA() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]eta;
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]boolean ankiGetCardMark() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]shouldDisplayMark();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]int ankiGetCardFlag() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.userFlag();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetNextTime1() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]mNext1.getText()));
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetNextTime2() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]mNext2.getText()));
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetNextTime3() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]mNext3.getText()));
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]java.lang.String ankiGetNextTime4() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]mNext4.getText()));
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]int ankiGetCardReps() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getReps();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]int ankiGetCardInterval() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getIvl();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the ease as a double. Default: 2.5. Minimum: 1.3
         */
        [CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]double ankiGetCardEase() [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl](([CtTypeReferenceImpl]double) ([CtFieldReadImpl]mCurrentCard.getFactor())) / [CtLiteralImpl]1000.0;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the last modified time as a Unix timestamp in seconds. Example: 1477384099
         */
        [CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]long ankiGetCardLastModified() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getMod();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the Unix timestamp of card creation in milliseconds. Example: 1477380543053
         */
        [CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]long ankiGetCardCreated() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]ankiGetCardId();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns the ID of the card. Example: 1477380543053
         */
        [CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]long ankiGetCardId() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getId();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        [CtAnnotationImpl]@com.ichi2.libanki.Consts.CARD_QUEUE
        public [CtTypeReferenceImpl]int ankiGetCardQueue() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getQueue();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]int ankiGetCardLapses() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getLapses();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]long ankiGetCardDue() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]mCurrentCard.getDue();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]boolean ankiIsInFullscreen() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]isFullscreen();
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]boolean ankiIsTopbarShown() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]mPrefShowTopbar;
        }

        [CtMethodImpl][CtAnnotationImpl]@android.webkit.JavascriptInterface
        public [CtTypeReferenceImpl]boolean ankiIsInNightMode() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]isInNightMode();
        }
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package org.edx.mobile.module.analytics;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtUnresolvedImport]import androidx.annotation.NonNull;
[CtUnresolvedImport]import com.segment.analytics.Properties;
[CtUnresolvedImport]import org.edx.mobile.util.images.ShareUtils;
[CtImportImpl]import java.util.Map;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * An interface that defines methods for all analytics events, to be implemented for all analytics
 * services that are used in the app. This class contains all the {@link Screens} & {@link Events}.
 * Additionally all the {@link Keys} and their corresponding {@link Values} are defined to make
 * Screens and Events more meaningful.
 */
public interface Analytics {
    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to send the screen tracking event, with an extra event for
     * sending course id.
     *
     * @param screenName
     * 		The screen name to track
     * @param courseId
     * 		course id of the course we are viewing
     * @param action
     * 		any custom action we need to send with event
     * @param values
     * 		any custom key- value pairs we need to send with event
     */
    [CtTypeReferenceImpl]void trackScreenView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String action, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> values);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Playing
     *
     * @param videoId
     * 		- Video Id that is being Played
     * @param currentTime
     * 		- Video Playing started at
     * @param courseId
     * 		- CourseId under which the video is present
     * @param unitUrl
     * 		- Page Url for that Video
     * @param playMedium
     * 		- Play Medium (e.g {@link Values#GOOGLE_CAST})
     */
    [CtTypeReferenceImpl]void trackVideoPlaying([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String playMedium);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Pause
     *
     * @param videoId
     * 		- Video Id that is being Played
     * @param currentTime
     * 		- Video Playing started at
     * @param courseId
     * 		- CourseId under which the video is present
     * @param unitUrl
     * 		- Page Url for that Video
     * @param playMedium
     * 		- Play Medium (e.g {@link Values#YOUTUBE})
     */
    [CtTypeReferenceImpl]void trackVideoPause([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String playMedium);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Stop
     *
     * @param videoId
     * @param currentTime
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackVideoStop([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to Show Transcript
     *
     * @param videoId
     * @param currentTime
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackShowTranscript([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to Hide Transcript
     *
     * @param videoId
     * @param currentTime
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackHideTranscript([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track the video playback speed changes
     *
     * @param videoId
     * @param currentTime
     * @param courseId
     * @param unitUrl
     * @param oldSpeed
     * @param newSpeed
     */
    [CtTypeReferenceImpl]void trackVideoSpeed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl, [CtParameterImpl][CtTypeReferenceImpl]float oldSpeed, [CtParameterImpl][CtTypeReferenceImpl]float newSpeed);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Loading
     *
     * @param videoId
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackVideoLoading([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track 30 second rewind on Video
     *
     * @param videoId
     * @param oldTime
     * @param newTime
     * @param courseId
     * @param unitUrl
     * @param skipSeek
     */
    [CtTypeReferenceImpl]void trackVideoSeek([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double oldTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double newTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean skipSeek);

    [CtMethodImpl][CtCommentImpl]/* Events not mentioned in PDF */
    [CtJavaDocImpl]/**
     * This function is used to track Video Download completed
     *
     * @param videoId
     * 		- Video id for which download has started
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackDownloadComplete([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track user's successful course upgrade.
     *
     * @param blockId
     * 		ID of the locked course unit from which the user is redirected to upgrade screen.
     * @param courseId
     * 		ID of the course which user has upgraded.
     * @param minifiedBlockId
     * 		Block ID of the locked course unit from which the user is redirected to upgrade screen.
     */
    [CtTypeReferenceImpl]void trackCourseUpgradeSuccess([CtParameterImpl][CtTypeReferenceImpl]java.lang.String blockId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String minifiedBlockId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track launching the browser
     *
     * @param url
     */
    [CtTypeReferenceImpl]void trackBrowserLaunched([CtParameterImpl][CtTypeReferenceImpl]java.lang.String url);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Bulk Download from Subsection
     *
     * @param section
     * 		- Section in which the subsection is present
     * @param subSection
     * 		- Subsection from which the download started
     * @param enrollmentId
     * 		- Course under which the subsection is present
     * @param videoCount
     * 		- no of videos started downloading
     */
    [CtTypeReferenceImpl]void trackSubSectionBulkVideoDownload([CtParameterImpl][CtTypeReferenceImpl]java.lang.String section, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String subSection, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String enrollmentId, [CtParameterImpl][CtTypeReferenceImpl]long videoCount);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track User Login
     *
     * @param method
     * 		- will take the following inputs “Password”|”Google”|”Facebook”
     */
    [CtTypeReferenceImpl]void trackUserLogin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String method);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track user logout
     */
    [CtTypeReferenceImpl]void trackUserLogout();

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Language changed for Transcripts
     *
     * @param videoId
     * @param currentTime
     * @param lang
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackTranscriptLanguage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String lang, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Download started from Video List
     *
     * @param videoId
     * 		-  Video id for which download has started
     * @param courseId
     * @param unitUrl
     */
    [CtTypeReferenceImpl]void trackSingleVideoDownload([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track Video Orientation
     *
     * @param videoId
     * @param currentTime
     * @param isLandscape
     * 		-  true / false based on orientation
     * @param courseId
     * @param unitUrl
     * @param playMedium
     * 		- Play Medium (e.g {@link Values#YOUTUBE})
     */
    [CtTypeReferenceImpl]void trackVideoOrientation([CtParameterImpl][CtTypeReferenceImpl]java.lang.String videoId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Double currentTime, [CtParameterImpl][CtTypeReferenceImpl]boolean isLandscape, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String unitUrl, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String playMedium);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks search of courses in the app.
     *
     * @param searchQuery
     * 		The search query.
     * @param isLoggedIn
     * 		<code>true</code> if the user is logged-in, <code>false</code> otherwise.
     * @param versionName
     * 		App's version.
     */
    [CtTypeReferenceImpl]void trackCoursesSearch([CtParameterImpl][CtTypeReferenceImpl]java.lang.String searchQuery, [CtParameterImpl][CtTypeReferenceImpl]boolean isLoggedIn, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user clicks on Sign up on landing page
     */
    [CtTypeReferenceImpl]void trackUserSignUpForAccount();

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user clicks on Find Courses
     */
    [CtTypeReferenceImpl]void trackUserFindsCourses();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track if user clicks on Create Account button on Registration screen.
     *
     * @param appVersion
     * 		Version of app.
     * @param source
     * 		Source through which the user is going to register.
     */
    [CtTypeReferenceImpl]void trackCreateAccountClicked([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String appVersion, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String source);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track successful registration of a user.
     *
     * @param appVersion
     * 		Version of app.
     * @param source
     * 		Source through which the user has completed registration.
     */
    [CtTypeReferenceImpl]void trackRegistrationSuccess([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String appVersion, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String source);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track user's click on the Enroll button of Find Courses Detail screen.
     *
     * @param courseId
     * 		ID of the course for which user is going to enroll.
     * @param emailOptIn
     * 		Flag to represent if user wants to opt in for email notification.
     */
    [CtTypeReferenceImpl]void trackEnrollClicked([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]boolean emailOptIn);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track user's successful enrollment in a course.
     *
     * @param courseId
     * 		ID of the course which user has enrolled in.
     * @param emailOptIn
     * 		Flag to represent if user wants to opt in for email notification.
     */
    [CtTypeReferenceImpl]void trackEnrolmentSuccess([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]boolean emailOptIn);

    [CtMethodImpl][CtTypeReferenceImpl]void trackNotificationReceived([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String courseId);

    [CtMethodImpl][CtTypeReferenceImpl]void trackNotificationTapped([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String courseId);

    [CtMethodImpl][CtTypeReferenceImpl]void trackUserConnectionSpeed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String connectionType, [CtParameterImpl][CtTypeReferenceImpl]float connectionSpeed);

    [CtMethodImpl][CtTypeReferenceImpl]void certificateShared([CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String certificateUrl, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.edx.mobile.util.images.ShareUtils.ShareType method);

    [CtMethodImpl][CtTypeReferenceImpl]void courseDetailShared([CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String aboutUrl, [CtParameterImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.edx.mobile.util.images.ShareUtils.ShareType method);

    [CtMethodImpl][CtTypeReferenceImpl]void trackCourseComponentViewed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String blockId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String minifiedBlockId);

    [CtMethodImpl][CtTypeReferenceImpl]void trackOpenInBrowser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String blockId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]boolean isSupported, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String minifiedBlockId);

    [CtMethodImpl][CtTypeReferenceImpl]void trackProfileViewed([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String username);

    [CtMethodImpl][CtTypeReferenceImpl]void trackProfilePhotoSet([CtParameterImpl][CtTypeReferenceImpl]boolean fromCamera);

    [CtMethodImpl][CtTypeReferenceImpl]void identifyUser([CtParameterImpl][CtTypeReferenceImpl]java.lang.String userID, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String email, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String username);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This resets the Identify user once the user has logged out
     */
    [CtTypeReferenceImpl]void resetIdentifyUser();

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user views the App Rating view.
     *
     * @param versionName
     * 		Version name of app.
     */
    [CtTypeReferenceImpl]void trackAppRatingDialogViewed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user cancels the App Rating view.
     *
     * @param versionName
     * 		Version name of app.
     */
    [CtTypeReferenceImpl]void trackAppRatingDialogCancelled([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user submits rating on the App Rating view.
     *
     * @param versionName
     * 		Version name of app.
     * @param rating
     * 		Rating given by user.
     */
    [CtTypeReferenceImpl]void trackUserSubmitRating([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int rating);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user selects Send Feedback after rating the app.
     *
     * @param versionName
     * 		Version name of app.
     * @param rating
     * 		Rating given by user.
     */
    [CtTypeReferenceImpl]void trackUserSendFeedback([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int rating);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user rates the app and then selects Maybe Later,
     * could be either from Feedback dialog or Rate The App dialog.
     *
     * @param versionName
     * 		Version name of app.
     * @param rating
     * 		Rating given by user.
     */
    [CtTypeReferenceImpl]void trackUserMayReviewLater([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int rating);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user gives positive rating and selects Rate The App option.
     *
     * @param versionName
     * 		Version name of app.
     * @param rating
     * 		Rating given by user.
     */
    [CtTypeReferenceImpl]void trackRateTheAppClicked([CtParameterImpl][CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int rating);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user presses the cross button on WhatsNew screen.
     *
     * @param versionName
     * 		Version name of app.
     * @param totalViewed
     * 		The total number of screens a user viewed.
     * @param currentlyViewed
     * 		The screen being currently viewed.
     * @param totalScreens
     * 		Total number of screens.
     */
    [CtTypeReferenceImpl]void trackWhatsNewClosed([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int totalViewed, [CtParameterImpl][CtTypeReferenceImpl]int currentlyViewed, [CtParameterImpl][CtTypeReferenceImpl]int totalScreens);

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function is used to track if user presses the done button on WhatsNew screen.
     *
     * @param versionName
     * 		Version name of app.
     * @param totalScreens
     * 		Total number of screens.
     */
    [CtTypeReferenceImpl]void trackWhatsNewSeen([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String versionName, [CtParameterImpl][CtTypeReferenceImpl]int totalScreens);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track deletion of all videos within a subsection.
     *
     * @param courseId
     * 		ID of the course.
     * @param subsectionId
     * 		ID of the subsection.
     */
    [CtTypeReferenceImpl]void trackSubsectionVideosDelete([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String subsectionId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track cancelling of all videos' deletion within a subsection.
     *
     * @param courseId
     * 		ID of the course.
     * @param subsectionId
     * 		ID of the subsection.
     */
    [CtTypeReferenceImpl]void trackUndoingSubsectionVideosDelete([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String subsectionId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track deletion of a video unit.
     *
     * @param courseId
     * 		ID of the course.
     * @param unitId
     * 		ID of the unit.
     */
    [CtTypeReferenceImpl]void trackUnitVideoDelete([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String unitId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track cancelling of a video unit's deletion.
     *
     * @param courseId
     * 		ID of the course.
     * @param unitId
     * 		ID of the unit.
     */
    [CtTypeReferenceImpl]void trackUndoingUnitVideoDelete([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String unitId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the pressing of bulk download switch to ON state.
     *
     * @param courseId
     * 		ID of the course.
     * @param totalDownloadableVideos
     * 		Number of videos that can be downloaded in a course.
     * @param remainingDownloadableVideos
     * 		Remaining videos that can be downloaded within a course.
     */
    [CtTypeReferenceImpl]void trackBulkDownloadSwitchOn([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]int totalDownloadableVideos, [CtParameterImpl][CtTypeReferenceImpl]int remainingDownloadableVideos);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the pressing of bulk download switch to OFF state.
     *
     * @param courseId
     * 		ID of the course.
     * @param totalDownloadableVideos
     * 		Number of videos that can be downloaded in a course.
     */
    [CtTypeReferenceImpl]void trackBulkDownloadSwitchOff([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtTypeReferenceImpl]int totalDownloadableVideos);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Tracks the pressing of a subject item.
     *
     * @param subjectId
     * 		ID of the subject.
     */
    [CtTypeReferenceImpl]void trackSubjectClicked([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String subjectId);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the pressing of 'Download Video to SD Card' switch to ON state.
     */
    [CtTypeReferenceImpl]void trackDownloadToSdCardSwitchOn();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the pressing of 'Download Video to SD Card' switch to OFF state.
     */
    [CtTypeReferenceImpl]void trackDownloadToSdCardSwitchOff();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the parameters relevant to the experiment of Firebase Remote Configs.
     * Ref: https://openedx.atlassian.net/browse/LEARNER-7394
     *
     * @param experimentName
     * @param values
     * 		any custom key-value pairs we need to send with event
     */
    [CtTypeReferenceImpl]void trackExperimentParams([CtParameterImpl][CtTypeReferenceImpl]java.lang.String experimentName, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> values);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the casting device connection.
     *
     * @param eventName
     * 		Cast Event Name
     * @param connectionState
     * 		State of casting device (e.g {@link Values#CAST_CONNECTED})
     * @param playMedium
     * 		Casting device playMedium (e.g {@link Values#GOOGLE_CAST})
     */
    [CtTypeReferenceImpl]void trackCastDeviceConnectionChanged([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String eventName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String connectionState, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String playMedium);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the PLS Course Dates Banner appearance
     *
     * @param biValue
     * 		biValue of course date banner type
     * @param courseId
     * 		course id of the course where banner appears
     * @param enrollmentMode
     * 		enrollment mode of the course where banner appears
     * @param screenName
     * 		The screen name where banner appears
     * @param bannerType
     * 		Type of course date banner
     */
    [CtTypeReferenceImpl]void trackPLSCourseDatesBanner([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String biValue, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String enrollmentMode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String bannerType);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the Shift Dates button tapped on PLS Course Dates Banner
     *
     * @param courseId
     * 		course id of the course where banner appears
     * @param enrollmentMode
     * 		enrollment mode of the course where button will be tapped
     * @param screenName
     * 		The screen name on which button will be tapped
     */
    [CtTypeReferenceImpl]void trackPLSShiftButtonTapped([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String enrollmentMode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track that PLS Course Dates Shifted successfully or not
     *
     * @param courseId
     * 		course id of the course where banner appears
     * @param enrollmentMode
     * 		enrollment mode of the course on which dates will be shifted
     * @param screenName
     * 		The screen name on which dates will be shifted
     * @param isSuccess
     * 		Does the shifted successful or not
     */
    [CtTypeReferenceImpl]void trackPLSCourseDatesShift([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String enrollmentMode, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName, [CtParameterImpl][CtTypeReferenceImpl]boolean isSuccess);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the Value Prop Modal appearance
     *
     * @param courseId
     * 		course id of the course through which the modal is appeared
     * @param assignmentId
     * 		Assignment id of course unit
     * @param screenName
     * 		The screen name through which Modal will appear
     */
    [CtTypeReferenceImpl]void trackValuePropModalView([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String assignmentId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the Value Prop Learn more button tapped
     *
     * @param courseId
     * 		course id of the course on which button is tapped
     * @param assignmentId
     * 		Assignment id of course unit
     * @param screenName
     * 		The screen name on which button will be tapped
     */
    [CtTypeReferenceImpl]void trackValuePropLearnMoreTapped([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String assignmentId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String screenName);

    [CtMethodImpl][CtJavaDocImpl]/**
     * Track the tapped occurrence on locked content
     *
     * @param courseId
     * 		course id of the course on which button is tapped
     * @param assignmentId
     * 		Assignment id of course unit
     */
    [CtTypeReferenceImpl]void trackLockedContentTapped([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String courseId, [CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
    [CtTypeReferenceImpl]java.lang.String assignmentId);

    [CtInterfaceImpl]interface Keys {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"name";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_ID = [CtLiteralImpl]"user_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OLD_TIME = [CtLiteralImpl]"old_time";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NEW_TIME = [CtLiteralImpl]"new_time";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NEW_SPEED = [CtLiteralImpl]"new_speed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OLD_SPEED = [CtLiteralImpl]"old_speed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SEEK_TYPE = [CtLiteralImpl]"seek_type";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String REQUESTED_SKIP_INTERVAL = [CtLiteralImpl]"requested_skip_interval";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String MODULE_ID = [CtLiteralImpl]"module_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CODE = [CtLiteralImpl]"code";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CURRENT_TIME = [CtLiteralImpl]"current_time";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_ID = [CtLiteralImpl]"course_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OPEN_BROWSER = [CtLiteralImpl]"open_in_browser_url";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COMPONENT = [CtLiteralImpl]"component";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_SECTION = [CtLiteralImpl]"course_section";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_SUBSECTION = [CtLiteralImpl]"course_subsection";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NO_OF_VIDEOS = [CtLiteralImpl]"number_of_videos";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FULLSCREEN = [CtLiteralImpl]"settings.video.fullscreen";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LANGUAGE = [CtLiteralImpl]"language";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TARGET_URL = [CtLiteralImpl]"target_url";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String URL = [CtLiteralImpl]"url";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONTEXT = [CtLiteralImpl]"context";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DATA = [CtLiteralImpl]"data";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String METHOD = [CtLiteralImpl]"method";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP = [CtLiteralImpl]"app_name";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String EMAIL_OPT_IN = [CtLiteralImpl]"email_opt_in";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROVIDER = [CtLiteralImpl]"provider";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BLOCK_ID = [CtLiteralImpl]"block_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SUBSECTION_ID = [CtLiteralImpl]"subsection_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String UNIT_ID = [CtLiteralImpl]"unit_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ASSIGNMENT_ID = [CtLiteralImpl]"assignment_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SUPPORTED = [CtLiteralImpl]"supported";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DEVICE_ORIENTATION = [CtLiteralImpl]"device-orientation";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String MODE = [CtLiteralImpl]"mode";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SCREEN_NAME = [CtLiteralImpl]"screen_name";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BANNER_TYPE = [CtLiteralImpl]"banner_type";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SUCCESS = [CtLiteralImpl]"success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CELL_CARRIER = [CtLiteralImpl]"cell_carrier";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CELL_ZERO_RATED = [CtLiteralImpl]"cell_zero_rated";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONNECTION_TYPE = [CtLiteralImpl]"connection_type";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONNECTION_SPEED = [CtLiteralImpl]"connection_speed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TYPE = [CtLiteralImpl]"type";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CATEGORY = [CtLiteralImpl]"category";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LABEL = [CtLiteralImpl]"label";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ACTION = [CtLiteralImpl]"action";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SEARCH_STRING = [CtLiteralImpl]"search_string";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TOPIC_ID = [CtLiteralImpl]"topic_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String THREAD_ID = [CtLiteralImpl]"thread_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String RESPONSE_ID = [CtLiteralImpl]"response_id";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String AUTHOR = [CtLiteralImpl]"author";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COMPONENT_VIEWED = [CtLiteralImpl]"Component Viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_VERSION = [CtLiteralImpl]"app_version";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String RATING = [CtLiteralImpl]"rating";

        [CtFieldImpl][CtCommentImpl]// WhatsNew keys
        [CtTypeReferenceImpl]java.lang.String TOTAL_VIEWED = [CtLiteralImpl]"total_viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CURRENTLY_VIEWED = [CtLiteralImpl]"currently_viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TOTAL_SCREENS = [CtLiteralImpl]"total_screens";

        [CtFieldImpl][CtCommentImpl]// Bulk download feature keys
        [CtTypeReferenceImpl]java.lang.String TOTAL_DOWNLOADABLE_VIDEOS = [CtLiteralImpl]"total_downloadable_videos";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String REMAINING_DOWNLOADABLE_VIDEOS = [CtLiteralImpl]"remaining_downloadable_videos";

        [CtFieldImpl][CtCommentImpl]// Subjects
        [CtTypeReferenceImpl]java.lang.String SUBJECT_ID = [CtLiteralImpl]"subject_id";

        [CtFieldImpl][CtCommentImpl]// Firebase Remote Configs keys for A/A test
        [CtCommentImpl]// Ref: https://openedx.atlassian.net/browse/LEARNER-7394
        [CtTypeReferenceImpl]java.lang.String EXPERIMENT = [CtLiteralImpl]"experiment";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String GROUP = [CtLiteralImpl]"group";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String AA_EXPERIMENT = [CtLiteralImpl]"aa_experiment";

        [CtFieldImpl][CtCommentImpl]// Video Play Medium
        [CtTypeReferenceImpl]java.lang.String PLAY_MEDIUM = [CtLiteralImpl]"play_medium";

        [CtFieldImpl][CtCommentImpl]// Used to access the analytics data in middle ware
        [CtTypeReferenceImpl]java.lang.String EVENT = [CtLiteralImpl]"event";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROPERTIES = [CtLiteralImpl]"properties";
    }

    [CtInterfaceImpl]interface Values {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SCREEN = [CtLiteralImpl]"screen";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SKIP = [CtLiteralImpl]"skip";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SLIDE = [CtLiteralImpl]"slide";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String MOBILE = [CtLiteralImpl]"mobile";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOPLAYER = [CtLiteralImpl]"videoplayer";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PASSWORD = [CtLiteralImpl]"Password";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FACEBOOK = [CtLiteralImpl]"Facebook";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String GOOGLE = [CtLiteralImpl]"Google";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String MICROSOFT = [CtLiteralImpl]"Microsoft";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DOWNLOAD_MODULE = [CtLiteralImpl]"downloadmodule";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_LOADED = [CtLiteralImpl]"edx.video.loaded";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_PLAYED = [CtLiteralImpl]"edx.video.played";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_PAUSED = [CtLiteralImpl]"edx.video.paused";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_STOPPED = [CtLiteralImpl]"edx.video.stopped";

        [CtFieldImpl][CtCommentImpl]// The seek event name has been changed as per MOB-1273
        [CtTypeReferenceImpl]java.lang.String VIDEO_SEEKED = [CtLiteralImpl]"edx.video.position.changed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TRANSCRIPT_SHOWN = [CtLiteralImpl]"edx.video.transcript.shown";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_PLAYBACK_SPEED_CHANGED = [CtLiteralImpl]"edx.bi.video.speed.changed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TRANSCRIPT_HIDDEN = [CtLiteralImpl]"edx.video.transcript.hidden";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String TRANSCRIPT_LANGUAGE = [CtLiteralImpl]"edx.bi.video.transcript.language.selected";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FULLSREEN_TOGGLED = [CtLiteralImpl]"edx.bi.video.screen.fullscreen.toggled";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BROWSER_LAUNCHED = [CtLiteralImpl]"edx.bi.app.browser.launched";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SINGLE_VIDEO_DOWNLOAD = [CtLiteralImpl]"edx.bi.video.download.requested";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_SUBSECTION = [CtLiteralImpl]"edx.bi.video.subsection.bulkdownload.requested";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_DOWNLOADED = [CtLiteralImpl]"edx.bi.video.downloaded";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USERLOGOUT = [CtLiteralImpl]"edx.bi.app.user.logout";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USERLOGIN = [CtLiteralImpl]"edx.bi.app.user.login";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_NAME = [CtLiteralImpl]"edx.mobileapp.android";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DISCOVERY_COURSES_SEARCH = [CtLiteralImpl]"edx.bi.app.discovery.courses_search";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_FIND_COURSES = [CtLiteralImpl]"edx.bi.app.search.find_courses.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CREATE_ACCOUNT_CLICKED = [CtLiteralImpl]"edx.bi.app.user.register.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_REGISTRATION_SUCCESS = [CtLiteralImpl]"edx.bi.app.user.register.success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_COURSE_ENROLL_CLICKED = [CtLiteralImpl]"edx.bi.app.course.enroll.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_COURSE_ENROLL_SUCCESS = [CtLiteralImpl]"edx.bi.app.course.enroll.success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_COURSE_UPGRADE_SUCCESS = [CtLiteralImpl]"edx.bi.app.course.upgrade.success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VALUE_PROP_LEARN_MORE_CLICKED = [CtLiteralImpl]"edx.bi.app.value.prop.learn.more.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LOCKED_CONTENT_CLICKED = [CtLiteralImpl]"edx.bi.app.course.unit.locked.content.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_NO_ACCOUNT = [CtLiteralImpl]"edx.bi.app.user.signup.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONVERSION = [CtLiteralImpl]"conversion";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_ENGAGEMENT = [CtLiteralImpl]"user-engagement";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DISCOVERY = [CtLiteralImpl]"course-discovery";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PUSH_NOTIFICATION = [CtLiteralImpl]"notifications";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ANNOUNCEMENT = [CtLiteralImpl]"announcement";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONNECTION_CELL = [CtLiteralImpl]"edx.bi.app.connection.cell";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CONNECTION_SPEED = [CtLiteralImpl]"edx.bi.app.connection.speed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NOTIFICATION_RECEIVED = [CtLiteralImpl]"edx.bi.app.notification.course.update.received";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NOTIFICATION_TAPPED = [CtLiteralImpl]"edx.bi.app.notification.course.update.tapped";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SOCIAL_CERTIFICATE_SHARED = [CtLiteralImpl]"edx.bi.app.certificate.shared";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SOCIAL_COURSE_DETAIL_SHARED = [CtLiteralImpl]"edx.bi.app.course.shared";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String NAVIGATION = [CtLiteralImpl]"navigation";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SOCIAL_SHARING = [CtLiteralImpl]"social-sharing";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE = [CtLiteralImpl]"profiles";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CAMERA = [CtLiteralImpl]"camera";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LIBRARY = [CtLiteralImpl]"library";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_VIEWED = [CtLiteralImpl]"edx.bi.app.profile.view";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_PHOTO_SET = [CtLiteralImpl]"edx.bi.app.profile.setphoto";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COMPONENT_VIEWED = [CtLiteralImpl]"edx.bi.app.navigation.component.viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OPEN_IN_BROWSER = [CtLiteralImpl]"edx.bi.app.navigation.open-in-browser";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OPEN_IN_WEB_SUPPORTED = [CtLiteralImpl]"Open in browser - Supported";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OPEN_IN_WEB_NOT_SUPPORTED = [CtLiteralImpl]"Open in browser - Unsupported";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LANDSCAPE = [CtLiteralImpl]"landscape";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PORTRAIT = [CtLiteralImpl]"portrait";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String WIFI = [CtLiteralImpl]"wifi";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CELL_DATA = [CtLiteralImpl]"cell_data";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String POSTS_ALL = [CtLiteralImpl]"all_posts";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String POSTS_FOLLOWING = [CtLiteralImpl]"posts_following";

        [CtFieldImpl][CtCommentImpl]// App review event values
        [CtTypeReferenceImpl]java.lang.String APP_REVIEWS_CATEGORY = [CtLiteralImpl]"app-reviews";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_VIEW_RATING = [CtLiteralImpl]"edx.bi.app.app_reviews.view_rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_DISMISS_RATING = [CtLiteralImpl]"edx.bi.app.app_reviews.dismiss_rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_SUBMIT_RATING = [CtLiteralImpl]"edx.bi.app.app_reviews.submit_rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_SEND_FEEDBACK = [CtLiteralImpl]"edx.bi.app.app_reviews.send_feedback";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_MAYBE_LATER = [CtLiteralImpl]"edx.bi.app.app_reviews.maybe_later";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_RATE_THE_APP = [CtLiteralImpl]"edx.bi.app.app_reviews.rate_the_app";

        [CtFieldImpl][CtCommentImpl]// WhatsNew event values
        [CtTypeReferenceImpl]java.lang.String WHATS_NEW_CATEGORY = [CtLiteralImpl]"whats-new";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String WHATS_NEW_CLOSE = [CtLiteralImpl]"edx.bi.app.whats_new.close";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String WHATS_NEW_DONE = [CtLiteralImpl]"edx.bi.app.whats_new.done";

        [CtFieldImpl][CtCommentImpl]// Course Videos event values
        [CtTypeReferenceImpl]java.lang.String VIDEOS_SUBSECTION_DELETE = [CtLiteralImpl]"edx.bi.app.video.delete.subsection";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNDO_SUBSECTION_DELETE = [CtLiteralImpl]"edx.bi.app.video.undo.subsection.delete";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNIT_DELETE = [CtLiteralImpl]"edx.bi.app.video.delete.unit";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNDO_UNIT_DELETE = [CtLiteralImpl]"edx.bi.app.video.undo.unit.delete";

        [CtFieldImpl][CtCommentImpl]// Bulk download feature event values
        [CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_SWITCH_ON = [CtLiteralImpl]"edx.bi.app.videos.download.toggle.on";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_SWITCH_OFF = [CtLiteralImpl]"edx.bi.app.videos.download.toggle.off";

        [CtFieldImpl][CtCommentImpl]// Discovery Courses Search
        [CtTypeReferenceImpl]java.lang.String DISCOVERY_COURSES_SEARCH_LANDING = [CtLiteralImpl]"landing_screen";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DISCOVERY_COURSES_SEARCH_TAB = [CtLiteralImpl]"discovery_tab";

        [CtFieldImpl][CtCommentImpl]// Subjects
        [CtTypeReferenceImpl]java.lang.String SUBJECT_CLICKED = [CtLiteralImpl]"edx.bi.app.discover.subject.clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DISCOVERY = [CtLiteralImpl]"discovery";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIEW_ALL_SUBJECTS = [CtLiteralImpl]"View All Subjects";

        [CtFieldImpl][CtCommentImpl]// Settings event values
        [CtTypeReferenceImpl]java.lang.String DOWNLOAD_TO_SD_CARD_SWITCH_ON = [CtLiteralImpl]"edx.bi.app.settings.sdcard.toggle.on";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DOWNLOAD_TO_SD_CARD_SWITCH_OFF = [CtLiteralImpl]"edx.bi.app.settings.sdcard.toggle.off";

        [CtFieldImpl][CtCommentImpl]// Cast device connection state
        [CtTypeReferenceImpl]java.lang.String CAST_CONNECTED = [CtLiteralImpl]"edx.bi.app.cast.connected";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CAST_DISCONNECTED = [CtLiteralImpl]"edx.bi.app.cast.disconnected";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_CASTED = [CtLiteralImpl]"edx.bi.app.cast.video_casted";

        [CtFieldImpl][CtCommentImpl]// -- Play mediums --
        [CtCommentImpl]// Casting Device Types
        [CtTypeReferenceImpl]java.lang.String GOOGLE_CAST = [CtLiteralImpl]"google_cast";

        [CtFieldImpl][CtCommentImpl]// YouTube Player Type
        [CtTypeReferenceImpl]java.lang.String YOUTUBE = [CtLiteralImpl]"youtube";

        [CtFieldImpl][CtCommentImpl]// PLS Course Dates Banner
        [CtTypeReferenceImpl]java.lang.String COURSE_DATES = [CtLiteralImpl]"course_dates";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DATES_BANNER_INFO = [CtLiteralImpl]"edx.bi.app.coursedates.info";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DATES_BANNER_UPGRADE_TO_PARTICIPATE = [CtLiteralImpl]"edx.bi.app.coursedates.upgrade.participate";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DATES_BANNER_UPGRADE_TO_SHIFT = [CtLiteralImpl]"edx.bi.app.coursedates.shift";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DATES_BANNER_SHIFT_DATES = [CtLiteralImpl]"edx.bi.app.coursedates.upgrade.shift";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_BANNER_TYPE_INFO = [CtLiteralImpl]"info";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_BANNER_TYPE_UPGRADE_TO_PARTICIPATE = [CtLiteralImpl]"upgrade_to_participate";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_BANNER_TYPE_UPGRADE_TO_SHIFT = [CtLiteralImpl]"upgrade_to_shift";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_BANNER_TYPE_SHIFT_DATES = [CtLiteralImpl]"shift_dates";
    }

    [CtInterfaceImpl]interface Screens {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_INFO_SCREEN = [CtLiteralImpl]"Course Info";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROGRAM_INFO_SCREEN = [CtLiteralImpl]"Program Info";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LAUNCH_ACTIVITY = [CtLiteralImpl]"Launch";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String REGISTER = [CtLiteralImpl]"Register";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LOGIN = [CtLiteralImpl]"Login";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_ENROLLMENT = [CtLiteralImpl]"Course Enrollment";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DASHBOARD = [CtLiteralImpl]"Course Dashboard";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_OUTLINE = [CtLiteralImpl]"Course Outline";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_HANDOUTS = [CtLiteralImpl]"Course Handouts";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_ANNOUNCEMENTS = [CtLiteralImpl]"Course Announcements";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_DATES = [CtLiteralImpl]"Course Dates";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_UNIT = [CtLiteralImpl]"Course Unit";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SECTION_OUTLINE = [CtLiteralImpl]"Section Outline";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String UNIT_DETAIL = [CtLiteralImpl]"Unit Detail";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CERTIFICATE = [CtLiteralImpl]"View Certificate";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DOWNLOADS = [CtLiteralImpl]"Downloads";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FIND_COURSES = [CtLiteralImpl]"Find Courses";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FIND_PROGRAMS = [CtLiteralImpl]"Find Programs";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FIND_DEGREES = [CtLiteralImpl]"Find Degrees";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String MY_COURSES = [CtLiteralImpl]"My Courses";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SETTINGS = [CtLiteralImpl]"Settings";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_VIEW_TOPICS = [CtLiteralImpl]"Forum: View Topics";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_SEARCH_THREADS = [CtLiteralImpl]"Forum: Search Threads";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_VIEW_TOPIC_THREADS = [CtLiteralImpl]"Forum: View Topic Threads";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_CREATE_TOPIC_THREAD = [CtLiteralImpl]"Forum: Create Topic Thread";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_VIEW_THREAD = [CtLiteralImpl]"Forum: View Thread";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_ADD_RESPONSE = [CtLiteralImpl]"Forum: Add Thread Response";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_VIEW_RESPONSE_COMMENTS = [CtLiteralImpl]"Forum: View Response Comments";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FORUM_ADD_RESPONSE_COMMENT = [CtLiteralImpl]"Forum: Add Response Comment";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_VIEW = [CtLiteralImpl]"Profile View";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_EDIT = [CtLiteralImpl]"Profile Edit";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_CROP_PHOTO = [CtLiteralImpl]"Crop Photo";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_CHOOSE_BIRTH_YEAR = [CtLiteralImpl]"Choose Form Value Birth year";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_CHOOSE_LOCATION = [CtLiteralImpl]"Choose Form Value Location";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_CHOOSE_LANGUAGE = [CtLiteralImpl]"Choose Form Value Primary language";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_EDIT_TEXT_VALUE = [CtLiteralImpl]"Edit Text Form Value";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_VIEW_RATING = [CtLiteralImpl]"AppReviews: View Rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String WHATS_NEW = [CtLiteralImpl]"WhatsNew: Whats New";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_COURSE_VIDEOS = [CtLiteralImpl]"Videos: Course Videos";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String ALL_SUBJECTS = [CtLiteralImpl]"Discover: All Subjects";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PAYMENTS_INFO_SCREEN = [CtLiteralImpl]"Payments info";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_UNIT_LOCKED = [CtLiteralImpl]"Course unit locked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLACE_ORDER_COURSE_UPGRADE = [CtLiteralImpl]"Place order: course upgrade";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_COURSE_DASHBOARD = [CtLiteralImpl]"course_dashboard";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_COURSE_DATES = [CtLiteralImpl]"dates_screen";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_COURSE_UNIT_ASSIGNMENT = [CtLiteralImpl]"assignments_screen";
    }

    [CtInterfaceImpl]interface Events {
        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LOADED_VIDEO = [CtLiteralImpl]"Loaded Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLAYED_VIDEO = [CtLiteralImpl]"Played Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PAUSED_VIDEO = [CtLiteralImpl]"Paused Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String STOPPED_VIDEO = [CtLiteralImpl]"Stopped Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SEEK_VIDEO = [CtLiteralImpl]"Seeked Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SHOW_TRANSCRIPT = [CtLiteralImpl]"Show Transcript";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SPEED_CHANGE_VIDEO = [CtLiteralImpl]"Speed Change Video";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String HIDE_TRANSCRIPT = [CtLiteralImpl]"Hide Transcript";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_DOWNLOADED = [CtLiteralImpl]"Video Downloaded";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_SUBSECTION = [CtLiteralImpl]"Bulk Download Subsection";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SINGLE_VIDEO_DOWNLOAD = [CtLiteralImpl]"Single Video Download";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SCREEN_TOGGLED = [CtLiteralImpl]"Screen Toggled";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_LOGIN = [CtLiteralImpl]"User Login";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String USER_LOGOUT = [CtLiteralImpl]"User Logout";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BROWSER_LAUNCHED = [CtLiteralImpl]"Browser Launched";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String LANGUAGE_CLICKED = [CtLiteralImpl]"Language Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SIGN_UP = [CtLiteralImpl]"Sign up Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String FIND_COURSES = [CtLiteralImpl]"Find Courses Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CREATE_ACCOUNT_CLICKED = [CtLiteralImpl]"Create Account Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String REGISTRATION_SUCCESS = [CtLiteralImpl]"Registration Success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_ENROLL_CLICKED = [CtLiteralImpl]"Course Enroll Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_ENROLL_SUCCESS = [CtLiteralImpl]"Course Enroll Success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_UPGRADE_SUCCESS = [CtLiteralImpl]"Course Upgrade Success";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DISCOVERY_COURSES_SEARCH = [CtLiteralImpl]"Discovery: Courses Search";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SPEED = [CtLiteralImpl]"Connected Speed Report";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SOCIAL_CERTIFICATE_SHARED = [CtLiteralImpl]"Shared a certificate";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SOCIAL_COURSE_DETAIL_SHARED = [CtLiteralImpl]"Shared a course";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COMPONENT_VIEWED = [CtLiteralImpl]"Component Viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String OPEN_IN_BROWSER = [CtLiteralImpl]"Browser Launched";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PUSH_NOTIFICATION_RECEIVED = [CtLiteralImpl]"notification-received";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PUSH_NOTIFICATION_TAPPED = [CtLiteralImpl]"notification-tapped";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_VIEWED = [CtLiteralImpl]"Viewed a profile";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PROFILE_PHOTO_SET = [CtLiteralImpl]"Set a profile picture";

        [CtFieldImpl][CtCommentImpl]// App review events
        [CtTypeReferenceImpl]java.lang.String APP_REVIEWS_VIEW_RATING = [CtLiteralImpl]"AppReviews: View Rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_DISMISS_RATING = [CtLiteralImpl]"AppReviews: Dismiss Rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_SUBMIT_RATING = [CtLiteralImpl]"AppReviews: Submit Rating";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_SEND_FEEDBACK = [CtLiteralImpl]"AppReviews: Send Feedback";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_MAYBE_LATER = [CtLiteralImpl]"AppReviews: Maybe Later";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String APP_REVIEWS_RATE_THE_APP = [CtLiteralImpl]"AppReviews: Rate The App";

        [CtFieldImpl][CtCommentImpl]// WhatsNew events
        [CtTypeReferenceImpl]java.lang.String WHATS_NEW_CLOSE = [CtLiteralImpl]"WhatsNew: Close";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String WHATS_NEW_DONE = [CtLiteralImpl]"WhatsNew: Done";

        [CtFieldImpl][CtCommentImpl]// Course Videos events
        [CtTypeReferenceImpl]java.lang.String VIDEOS_SUBSECTION_DELETE = [CtLiteralImpl]"Videos: Subsection Delete";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNDO_SUBSECTION_DELETE = [CtLiteralImpl]"Videos: Undo Subsection Delete";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNIT_DELETE = [CtLiteralImpl]"Videos: Unit Delete";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEOS_UNDO_UNIT_DELETE = [CtLiteralImpl]"Videos: Undo Unit Delete";

        [CtFieldImpl][CtCommentImpl]// Bulk download events
        [CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_TOGGLE_ON = [CtLiteralImpl]"Bulk Download Toggle On";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String BULK_DOWNLOAD_TOGGLE_OFF = [CtLiteralImpl]"Bulk Download Toggle Off";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String SUBJECT_DISCOVERY = [CtLiteralImpl]"Subject Discovery";

        [CtFieldImpl][CtCommentImpl]// Settings events
        [CtTypeReferenceImpl]java.lang.String DOWNLOAD_TO_SD_CARD_ON = [CtLiteralImpl]"Download to sd-card On";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String DOWNLOAD_TO_SD_CARD_OFF = [CtLiteralImpl]"Download to sd-card Off";

        [CtFieldImpl][CtCommentImpl]// Firebase Remote Configs Event name for A/A test
        [CtCommentImpl]// Ref: https://openedx.atlassian.net/browse/LEARNER-7394
        [CtTypeReferenceImpl]java.lang.String MOBILE_EXPERIMENT_EVALUATED = [CtLiteralImpl]"Mobile Experiment Evaluated";

        [CtFieldImpl][CtCommentImpl]// Casting Devices Event
        [CtTypeReferenceImpl]java.lang.String CAST_CONNECTED = [CtLiteralImpl]"Cast: Connected";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String CAST_DISCONNECTED = [CtLiteralImpl]"Cast: Disconnected";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VIDEO_CASTED = [CtLiteralImpl]"Cast: Video Casted";

        [CtFieldImpl][CtCommentImpl]// PLS Course Dates Banner
        [CtTypeReferenceImpl]java.lang.String PLS_BANNER_VIEWED = [CtLiteralImpl]"PLS Banner Viewed";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_SHIFT_DATES_BUTTON_TAPPED = [CtLiteralImpl]"PLS Shift Button Tapped";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String PLS_SHIFT_DATES = [CtLiteralImpl]"PLS Shift Dates";

        [CtFieldImpl][CtCommentImpl]// Value Prop
        [CtTypeReferenceImpl]java.lang.String VALUE_PROP_LEARN_MORE_CLICKED = [CtLiteralImpl]"Value Prop: Learn More Clicked";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String VALUE_PROP_MODAL_VIEW = [CtLiteralImpl]"Value Prop Modal View";

        [CtFieldImpl][CtTypeReferenceImpl]java.lang.String COURSE_UNIT_LOCKED_CONTENT = [CtLiteralImpl]"Course Unit: Locked Content";
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * We can't have concrete functions inside interfaces till Java 8, therefore this
     * class has been defined to add static utilities to this interface.
     */
    class Util {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Resolves and returns the string alternative of the given share type.
         *
         * @param shareType
         * 		The share type.
         * @return The string alternative of the given share type.
         */
        public static [CtTypeReferenceImpl]java.lang.String getShareTypeValue([CtParameterImpl][CtAnnotationImpl]@androidx.annotation.NonNull
        [CtTypeReferenceImpl][CtTypeReferenceImpl]org.edx.mobile.util.images.ShareUtils.ShareType shareType) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtVariableReadImpl]shareType) {
                [CtCaseImpl]case [CtFieldReadImpl]FACEBOOK :
                    [CtReturnImpl]return [CtLiteralImpl]"facebook";
                [CtCaseImpl]case [CtFieldReadImpl]TWITTER :
                    [CtReturnImpl]return [CtLiteralImpl]"twitter";
                [CtCaseImpl]default :
                    [CtReturnImpl]return [CtLiteralImpl]"other";
            }
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Method to remove the un-supported characters by the Firebase Analytics from the
         * given string.
         */
        public static [CtTypeReferenceImpl]java.lang.String removeUnSupportedCharacters([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.replaceAll([CtLiteralImpl]":", [CtLiteralImpl]"_").replaceAll([CtLiteralImpl]"-", [CtLiteralImpl]"_").replaceAll([CtLiteralImpl]"__", [CtLiteralImpl]"_");
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Method used to format the Analytics data as per firebase recommendations
         * Ref: https://stackoverflow.com/questions/44421234/firebase-analytics-custom-list-of-values
         */
        public static [CtTypeReferenceImpl]com.segment.analytics.Properties formatFirebaseAnalyticsData([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object object) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.segment.analytics.Properties properties = [CtVariableReadImpl](([CtTypeReferenceImpl]com.segment.analytics.Properties) (object));
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.segment.analytics.Properties newProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.segment.analytics.Properties();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> entry : [CtInvocationImpl][CtVariableReadImpl]properties.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String entryValueString = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]entry.getValue());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entryValueString.length() > [CtLiteralImpl]100) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Truncate to first 100 characters
                    [CtVariableWriteImpl]entryValueString = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entryValueString.trim().substring([CtLiteralImpl]0, [CtLiteralImpl]100);
                }
                [CtInvocationImpl][CtVariableReadImpl]newProperties.put([CtInvocationImpl][CtTypeAccessImpl]org.edx.mobile.module.analytics.Analytics.Util.removeUnSupportedCharacters([CtVariableReadImpl]key), [CtVariableReadImpl]entryValueString);
            }
            [CtReturnImpl]return [CtVariableReadImpl]newProperties;
        }
    }

    [CtInterfaceImpl][CtJavaDocImpl]/**
     * Defines the analytics events that need to be fired.
     */
    interface OnEventListener {
        [CtMethodImpl][CtJavaDocImpl]/**
         * Fires a screen event.
         */
        [CtTypeReferenceImpl]void fireScreenEvent();
    }
}
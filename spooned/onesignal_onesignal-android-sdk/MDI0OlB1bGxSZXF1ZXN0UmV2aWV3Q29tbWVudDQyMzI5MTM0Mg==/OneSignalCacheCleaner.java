[CompilationUnitImpl][CtPackageDeclarationImpl]package com.onesignal;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.database.sqlite.SQLiteDatabase;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.onesignal.OneSignalDbContract.CachedUniqueOutcomeNotificationTable;
[CtUnresolvedImport]import com.onesignal.OneSignalDbContract.NotificationTable;
[CtImportImpl]import org.json.JSONException;
[CtUnresolvedImport]import android.support.annotation.WorkerThread;
[CtUnresolvedImport]import android.os.Process;
[CtUnresolvedImport]import android.database.Cursor;
[CtClassImpl]class OneSignalCacheCleaner {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long ONE_WEEK_IN_SECONDS = [CtLiteralImpl]604800L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long SIX_MONTHS_IN_SECONDS = [CtLiteralImpl]15552000L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OS_DELETE_CACHED_NOTIFICATIONS_THREAD = [CtLiteralImpl]"OS_DELETE_CACHED_NOTIFICATIONS_THREAD";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String OS_DELETE_CACHED_REDISPLAYED_IAMS_THREAD = [CtLiteralImpl]"OS_DELETE_CACHED_REDISPLAYED_IAMS_THREAD";

    [CtMethodImpl][CtJavaDocImpl]/**
     * We clean outdated cache from several places within the OneSignal SDK here
     * 1. Notifications & unique outcome events linked to notification ids (1 week)
     * 2. Cached In App Messaging Sets in SharedPreferences (impressions, clicks, views) and SQL IAMs
     */
    static [CtTypeReferenceImpl]void cleanOldCachedData([CtParameterImpl]final [CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.onesignal.OneSignalDbHelper dbHelper = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalDbHelper.getInstance([CtVariableReadImpl]context);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase writableDb = [CtInvocationImpl][CtVariableReadImpl]dbHelper.getSQLiteDatabaseWithRetries();
        [CtInvocationImpl]com.onesignal.OneSignalCacheCleaner.cleanNotificationCache([CtVariableReadImpl]writableDb);
        [CtInvocationImpl]com.onesignal.OneSignalCacheCleaner.cleanCachedInAppMessages([CtVariableReadImpl]writableDb);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cleans two notification tables
     * 1. NotificationTable.TABLE_NAME
     * 2. CachedUniqueOutcomeNotificationTable.TABLE_NAME
     */
    static synchronized [CtTypeReferenceImpl]void cleanNotificationCache([CtParameterImpl]final [CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase writableDb) [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().setPriority([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Process.[CtFieldReferenceImpl]THREAD_PRIORITY_BACKGROUND);
                [CtInvocationImpl]com.onesignal.OneSignalCacheCleaner.cleanCachedNotifications([CtVariableReadImpl]writableDb);
                [CtInvocationImpl]com.onesignal.OneSignalCacheCleaner.cleanCachedUniqueOutcomeEventNotifications([CtVariableReadImpl]writableDb);
            }
        }, [CtFieldReadImpl]com.onesignal.OneSignalCacheCleaner.OS_DELETE_CACHED_NOTIFICATIONS_THREAD).start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Remove IAMs that the last display time was six month ago
     * 1. Query for all old message ids and old clicked click ids
     * 2. Delete old IAMs from SQL
     * 3. Use queried data to clean SharedPreferences
     */
    [CtAnnotationImpl]@android.support.annotation.WorkerThread
    static synchronized [CtTypeReferenceImpl]void cleanCachedInAppMessages([CtParameterImpl]final [CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase writableDb) [CtBlockImpl]{
        [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtNewClassImpl]new [CtTypeReferenceImpl]java.lang.Runnable()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void run() [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.currentThread().setPriority([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Process.[CtFieldReferenceImpl]THREAD_PRIORITY_BACKGROUND);
                [CtLocalVariableImpl][CtCommentImpl]// 1. Query for all old message ids and old clicked click ids
                [CtArrayTypeReferenceImpl]java.lang.String[] retColumns = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtFieldReadImpl]OneSignalDbContract.InAppMessageTable.COLUMN_NAME_MESSAGE_ID, [CtFieldReadImpl]OneSignalDbContract.InAppMessageTable.COLUMN_CLICK_IDS };
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String whereStr = [CtBinaryOperatorImpl][CtFieldReadImpl]OneSignalDbContract.InAppMessageTable.COLUMN_NAME_LAST_DISPLAY + [CtLiteralImpl]" < ?";
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sixMonthsAgoInSeconds = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() / [CtLiteralImpl]1000L) - [CtFieldReadImpl]com.onesignal.OneSignalCacheCleaner.SIX_MONTHS_IN_SECONDS);
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] whereArgs = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]sixMonthsAgoInSeconds };
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> oldMessageIds = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.newConcurrentSet();
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> oldClickedClickIds = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.newConcurrentSet();
                [CtLocalVariableImpl][CtTypeReferenceImpl]android.database.Cursor cursor = [CtLiteralImpl]null;
                [CtTryImpl]try [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]cursor = [CtInvocationImpl][CtVariableReadImpl]writableDb.query([CtTypeAccessImpl]OneSignalDbContract.InAppMessageTable.TABLE_NAME, [CtVariableReadImpl]retColumns, [CtVariableReadImpl]whereStr, [CtVariableReadImpl]whereArgs, [CtLiteralImpl]null, [CtLiteralImpl]null, [CtLiteralImpl]null);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cursor == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]cursor.getCount() == [CtLiteralImpl]0)) [CtBlockImpl]{
                        [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignal.onesignalLog([CtTypeAccessImpl]OneSignal.LOG_LEVEL.DEBUG, [CtLiteralImpl]"Attempted to clean 6 month old IAM data, but none exists!");
                        [CtReturnImpl]return;
                    }
                    [CtIfImpl][CtCommentImpl]// From cursor get all of the old message ids and old clicked click ids
                    if ([CtInvocationImpl][CtVariableReadImpl]cursor.moveToFirst()) [CtBlockImpl]{
                        [CtDoImpl]do [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldMessageId = [CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]OneSignalDbContract.InAppMessageTable.COLUMN_NAME_MESSAGE_ID));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldClickIds = [CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]OneSignalDbContract.InAppMessageTable.COLUMN_CLICK_IDS));
                            [CtInvocationImpl][CtVariableReadImpl]oldMessageIds.add([CtVariableReadImpl]oldMessageId);
                            [CtInvocationImpl][CtVariableReadImpl]oldClickedClickIds.addAll([CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.newStringSetFromString([CtVariableReadImpl]oldClickIds));
                        } while ([CtInvocationImpl][CtVariableReadImpl]cursor.moveToNext() );
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.json.JSONException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]e.printStackTrace();
                } finally [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]cursor != [CtLiteralImpl]null) & [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]cursor.isClosed()))[CtBlockImpl]
                        [CtInvocationImpl][CtVariableReadImpl]cursor.close();

                }
                [CtInvocationImpl][CtCommentImpl]// 2. Delete old IAMs from SQL
                [CtVariableReadImpl]writableDb.delete([CtTypeAccessImpl]OneSignalDbContract.InAppMessageTable.TABLE_NAME, [CtVariableReadImpl]whereStr, [CtVariableReadImpl]whereArgs);
                [CtInvocationImpl][CtCommentImpl]// 3. Use queried data to clean SharedPreferences
                com.onesignal.OneSignalCacheCleaner.cleanCachedSharedPreferenceIamData([CtVariableReadImpl]oldMessageIds, [CtVariableReadImpl]oldClickedClickIds);
            }
        }, [CtFieldReadImpl]com.onesignal.OneSignalCacheCleaner.OS_DELETE_CACHED_REDISPLAYED_IAMS_THREAD).start();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes notifications with created timestamps older than 7 days
     * <br/><br/>
     * Note: This should only ever be called by {@link OneSignalCacheCleaner#cleanNotificationCache(SQLiteDatabase)}
     * <br/><br/>
     *
     * @see OneSignalCacheCleaner#cleanNotificationCache(SQLiteDatabase)
     */
    private static [CtTypeReferenceImpl]void cleanCachedNotifications([CtParameterImpl][CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase writableDb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String whereStr = [CtBinaryOperatorImpl][CtFieldReadImpl]com.onesignal.OneSignalDbContract.NotificationTable.COLUMN_NAME_CREATED_TIME + [CtLiteralImpl]" < ?";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String sevenDaysAgoInSeconds = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.currentTimeMillis() / [CtLiteralImpl]1000L) - [CtFieldReadImpl]com.onesignal.OneSignalCacheCleaner.ONE_WEEK_IN_SECONDS);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] whereArgs = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]sevenDaysAgoInSeconds };
        [CtInvocationImpl][CtVariableReadImpl]writableDb.delete([CtTypeAccessImpl]NotificationTable.TABLE_NAME, [CtVariableReadImpl]whereStr, [CtVariableReadImpl]whereArgs);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes cached unique outcome notifications whose ids do not exist inside of the NotificationTable.TABLE_NAME
     * <br/><br/>
     * Note: This should only ever be called by {@link OneSignalCacheCleaner#cleanNotificationCache(SQLiteDatabase)}
     * <br/><br/>
     *
     * @see OneSignalCacheCleaner#cleanNotificationCache(SQLiteDatabase)
     */
    private static [CtTypeReferenceImpl]void cleanCachedUniqueOutcomeEventNotifications([CtParameterImpl][CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase writableDb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String whereStr = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"NOT EXISTS(" + [CtLiteralImpl]"SELECT NULL FROM ") + [CtFieldReadImpl]com.onesignal.OneSignalDbContract.NotificationTable.TABLE_NAME) + [CtLiteralImpl]" n ") + [CtLiteralImpl]"WHERE") + [CtLiteralImpl]" n.") + [CtFieldReadImpl]com.onesignal.OneSignalDbContract.NotificationTable.COLUMN_NAME_NOTIFICATION_ID) + [CtLiteralImpl]" = ") + [CtFieldReadImpl]com.onesignal.OneSignalDbContract.CachedUniqueOutcomeNotificationTable.COLUMN_NAME_NOTIFICATION_ID) + [CtLiteralImpl]")";
        [CtInvocationImpl][CtVariableReadImpl]writableDb.delete([CtTypeAccessImpl]CachedUniqueOutcomeNotificationTable.TABLE_NAME, [CtVariableReadImpl]whereStr, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes old IAM SharedPreference dismissed and impressioned message ids as well as clicked click ids
     * <br/><br/>
     * Note: This should only ever be called by {@link OneSignalCacheCleaner#cleanCachedInAppMessages(SQLiteDatabase)}
     * <br/><br/>
     *
     * @see OneSignalCacheCleaner#cleanCachedInAppMessages(SQLiteDatabase)
     */
    private static [CtTypeReferenceImpl]void cleanCachedSharedPreferenceIamData([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> oldMessageIds, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> oldClickedClickIds) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// IAMs without redisplay on with pile up and we need to clean these for dismissing, impressions, and clicks
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]oldMessageIds != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]oldMessageIds.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> dismissedMessages = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.getStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_DISMISSED_IAMS, [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.<[CtTypeReferenceImpl]java.lang.String>newConcurrentSet());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> impressionedMessages = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.getStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_IMPRESSIONED_IAMS, [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.<[CtTypeReferenceImpl]java.lang.String>newConcurrentSet());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dismissedMessages != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dismissedMessages.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]dismissedMessages.removeAll([CtVariableReadImpl]oldMessageIds);
                [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.saveStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_DISMISSED_IAMS, [CtVariableReadImpl]dismissedMessages);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]impressionedMessages != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]impressionedMessages.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]impressionedMessages.removeAll([CtVariableReadImpl]oldMessageIds);
                [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.saveStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_IMPRESSIONED_IAMS, [CtVariableReadImpl]impressionedMessages);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]oldClickedClickIds != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]oldClickedClickIds.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> clickedClickIds = [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.getStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_CLICKED_CLICK_IDS_IAMS, [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OSUtils.<[CtTypeReferenceImpl]java.lang.String>newConcurrentSet());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]clickedClickIds != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]clickedClickIds.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]clickedClickIds.removeAll([CtVariableReadImpl]oldClickedClickIds);
                [CtInvocationImpl][CtTypeAccessImpl]com.onesignal.OneSignalPrefs.saveStringSet([CtTypeAccessImpl]OneSignalPrefs.PREFS_ONESIGNAL, [CtTypeAccessImpl]OneSignalPrefs.PREFS_OS_CLICKED_CLICK_IDS_IAMS, [CtVariableReadImpl]clickedClickIds);
            }
        }
    }
}
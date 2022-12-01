[CompilationUnitImpl][CtPackageDeclarationImpl]package com.iterable.iterableapi;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.database.sqlite.SQLiteDatabase;
[CtUnresolvedImport]import android.database.DatabaseUtils;
[CtUnresolvedImport]import androidx.annotation.Nullable;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.content.ContentValues;
[CtImportImpl]import org.json.JSONObject;
[CtUnresolvedImport]import android.database.Cursor;
[CtImportImpl]import java.util.Date;
[CtClassImpl]public class IterableTaskManager {
    [CtFieldImpl]private static [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTaskManager sharedInstance;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TAG = [CtLiteralImpl]"IterableTaskManager";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String ITERABLE_TASK_TABLE_NAME = [CtLiteralImpl]"OfflineTask";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String REPLACING_STRING = [CtLiteralImpl]"*#*#*#*";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String QUERY_GET_TASK_BY_ID = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"select * from OfflineTask where task_id = '" + [CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.REPLACING_STRING) + [CtLiteralImpl]"'";

    [CtFieldImpl]static final [CtTypeReferenceImpl]java.lang.String OFFLINE_TASK_COLUMN_DATA = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]" (" + [CtFieldReadImpl]IterableTask.TASK_ID) + [CtLiteralImpl]" TEXT PRIMARY KEY,") + [CtFieldReadImpl]IterableTask.NAME) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.VERSION) + [CtLiteralImpl]" INTEGER,") + [CtFieldReadImpl]IterableTask.CREATED_AT) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.MODIFIED_AT) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.LAST_ATTEMPTED_AT) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.SCHEDULED_AT) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.REQUESTED_AT) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.PROCESSING) + [CtLiteralImpl]" BOOLEAN,") + [CtFieldReadImpl]IterableTask.FAILED) + [CtLiteralImpl]" BOOLEAN,") + [CtFieldReadImpl]IterableTask.BLOCKING) + [CtLiteralImpl]" BOOLEAN,") + [CtFieldReadImpl]IterableTask.DATA) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.ERROR) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.TYPE) + [CtLiteralImpl]" TEXT,") + [CtFieldReadImpl]IterableTask.ATTEMPTS) + [CtLiteralImpl]" INTEGER") + [CtLiteralImpl]")";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KEY_ROWID = [CtLiteralImpl]"rowid";

    [CtFieldImpl]private [CtTypeReferenceImpl]android.database.sqlite.SQLiteDatabase database;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.iterable.iterableapi.IterableDatabaseManager databaseManager;

    [CtConstructorImpl]private IterableTaskManager([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]context == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]databaseManager == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]databaseManager = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableDatabaseManager([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableApi.getInstance().getMainActivityContext());
            }
            [CtAssignmentImpl][CtFieldWriteImpl]database = [CtInvocationImpl][CtFieldReadImpl]databaseManager.getWritableDatabase();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtLiteralImpl]"Failed to create database");
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTaskManager sharedInstance([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.sharedInstance == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]com.iterable.iterableapi.IterableTaskManager.sharedInstance = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTaskManager([CtVariableReadImpl]context);
        }
        [CtReturnImpl]return [CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.sharedInstance;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a new instance with default values of IterableTask and stores it in the database
     *
     * @param name
     * 		Type of the offline task. See {@link IterableTaskType}
     * @return unique id of the task created
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]java.lang.String createTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableTaskType type, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String data) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]database == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtLiteralImpl]"Database not initialized");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask iterableTask = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask([CtVariableReadImpl]name, [CtFieldReadImpl]IterableTaskType.API, [CtVariableReadImpl]data);
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.TASK_ID, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.id);
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.NAME, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.name);
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.VERSION, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.version);
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.CREATED_AT, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.createdAt.toString());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.modifiedAt != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.MODIFIED_AT, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.modifiedAt.toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.lastAttemptedAt != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.LAST_ATTEMPTED_AT, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.lastAttemptedAt.toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.scheduledAt != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.SCHEDULED_AT, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.scheduledAt.toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.requestedAt != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.REQUESTED_AT, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.requestedAt.toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.processing != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.PROCESSING, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.processing);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.failed != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.FAILED, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.failed);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.blocking != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.BLOCKING, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.blocking);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.data != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.DATA, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.data.toString());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.taskFailureData != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.ERROR, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.taskFailureData.toString());
        }
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.TYPE, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]iterableTask.taskType.toString());
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.ATTEMPTS, [CtFieldReadImpl][CtVariableReadImpl]iterableTask.attempts);
        [CtInvocationImpl][CtFieldReadImpl]database.insert([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.ITERABLE_TASK_TABLE_NAME, [CtLiteralImpl]null, [CtVariableReadImpl]contentValues);
        [CtInvocationImpl][CtVariableReadImpl]contentValues.clear();
        [CtReturnImpl]return [CtFieldReadImpl][CtVariableReadImpl]iterableTask.id;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a Task for the task id provided. Returns null if the database is null.
     *
     * @param id
     * 		Unique id for the task
     * @return {@link IterableTask} corresponding to id provided
     */
    [CtAnnotationImpl]@androidx.annotation.Nullable
    [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask getTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableTaskType type = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int version = [CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int attempts = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateCreated = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateModified = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date datelastAttempted = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date datescheduled = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateRequested = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean processing = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean failed = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean blocking = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String data = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String error = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]database == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtLiteralImpl]"database not initialized");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String query = [CtInvocationImpl][CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.QUERY_GET_TASK_BY_ID.replace([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.REPLACING_STRING, [CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.database.Cursor cursor = [CtInvocationImpl][CtFieldReadImpl]database.rawQuery([CtVariableReadImpl]query, [CtLiteralImpl]null);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.moveToFirst()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.d([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtLiteralImpl]"No record found");
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtAssignmentImpl][CtVariableWriteImpl]name = [CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.NAME));
        [CtAssignmentImpl][CtVariableWriteImpl]version = [CtInvocationImpl][CtVariableReadImpl]cursor.getInt([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.VERSION));
        [CtAssignmentImpl][CtVariableWriteImpl]dateCreated = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.CREATED_AT)));
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.MODIFIED_AT))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dateModified = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.MODIFIED_AT)));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.LAST_ATTEMPTED_AT))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]datelastAttempted = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.LAST_ATTEMPTED_AT)));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.SCHEDULED_AT))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]datescheduled = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.SCHEDULED_AT)));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.REQUESTED_AT))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]dateRequested = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.REQUESTED_AT)));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.PROCESSING))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]processing = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursor.getInt([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.PROCESSING)) > [CtLiteralImpl]0;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.FAILED))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]failed = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursor.getInt([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.FAILED)) > [CtLiteralImpl]0;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.BLOCKING))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]blocking = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursor.getInt([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.BLOCKING)) > [CtLiteralImpl]0;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.DATA))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]data = [CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.DATA));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.ERROR))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]error = [CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.ERROR));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.TYPE))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]type = [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableTaskType.valueOf([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.TYPE)));
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]cursor.isNull([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.ATTEMPTS))) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]attempts = [CtInvocationImpl][CtVariableReadImpl]cursor.getInt([CtInvocationImpl][CtVariableReadImpl]cursor.getColumnIndex([CtTypeAccessImpl]IterableTask.ATTEMPTS));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask task = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask([CtVariableReadImpl]id, [CtVariableReadImpl]name, [CtVariableReadImpl]version, [CtVariableReadImpl]dateCreated, [CtVariableReadImpl]dateModified, [CtVariableReadImpl]datelastAttempted, [CtVariableReadImpl]datescheduled, [CtVariableReadImpl]dateRequested, [CtVariableReadImpl]processing, [CtVariableReadImpl]failed, [CtVariableReadImpl]blocking, [CtVariableReadImpl]data, [CtVariableReadImpl]error, [CtFieldReadImpl]IterableTaskType.API, [CtVariableReadImpl]attempts);
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.v([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Found " + [CtInvocationImpl][CtVariableReadImpl]cursor.getColumnCount()) + [CtLiteralImpl]"columns");
        [CtReturnImpl]return [CtVariableReadImpl]task;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets ids of all the tasks in OfflineTask table
     *
     * @return {@link ArrayList} of {@link String} ids for all the tasks in OfflineTask table
     */
    [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> getAllTaskIds() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> taskIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtVariableReadImpl]taskIds;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.database.Cursor cursor = [CtInvocationImpl][CtFieldReadImpl]database.rawQuery([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT " + [CtFieldReadImpl]IterableTask.TASK_ID) + [CtLiteralImpl]" FROM ") + [CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.ITERABLE_TASK_TABLE_NAME, [CtLiteralImpl]null);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]cursor.moveToFirst()) [CtBlockImpl]{
            [CtDoImpl]do [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]taskIds.add([CtInvocationImpl][CtVariableReadImpl]cursor.getString([CtLiteralImpl]0));
            } while ([CtInvocationImpl][CtVariableReadImpl]cursor.moveToNext() );
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.v([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Found " + [CtInvocationImpl][CtVariableReadImpl]cursor.getColumnCount()) + [CtLiteralImpl]" columns");
        [CtReturnImpl]return [CtVariableReadImpl]taskIds;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes all the entries from the OfflineTask table.
     */
    [CtTypeReferenceImpl]void deleteAllTasks() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return;

        [CtLocalVariableImpl][CtTypeReferenceImpl]int numberOfRowsDeleted = [CtInvocationImpl][CtFieldReadImpl]database.delete([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.ITERABLE_TASK_TABLE_NAME, [CtLiteralImpl]null, [CtLiteralImpl]null);
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.v([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Deleted " + [CtVariableReadImpl]numberOfRowsDeleted) + [CtLiteralImpl]" offline tasks");
        [CtReturnImpl]return;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes a task from OfflineTask table
     *
     * @param id
     * 		for the task
     * @return Whether or not the task was deleted
     */
    [CtTypeReferenceImpl]java.lang.Boolean deleteTask([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]int numberOfEntriesDeleted = [CtInvocationImpl][CtFieldReadImpl]database.delete([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.ITERABLE_TASK_TABLE_NAME, [CtBinaryOperatorImpl][CtFieldReadImpl]IterableTask.TASK_ID + [CtLiteralImpl]" =?", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]id });
        [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.v([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"Deleted entry - " + [CtVariableReadImpl]numberOfEntriesDeleted);
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates Modified at date for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param date
     * 		Date when the task was modified
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateModifiedAt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date date) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.MODIFIED_AT, [CtInvocationImpl][CtVariableReadImpl]date.toString());
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates Last attempted date for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param date
     * 		Date when the task was last attempted
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateLastAttemptedAt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date date) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.LAST_ATTEMPTED_AT, [CtInvocationImpl][CtVariableReadImpl]date.toString());
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates Requested at date for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param date
     * 		Date when the task was last requested
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateRequestedAt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date date) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.REQUESTED_AT, [CtInvocationImpl][CtVariableReadImpl]date.toString());
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates Scheduled at date for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param date
     * 		Date when the task is Scheduled
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateScheduledAt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date date) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.SCHEDULED_AT, [CtInvocationImpl][CtVariableReadImpl]date.toString());
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the processing state of task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param state
     * 		whether the task is processing or completed
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateIsProcessing([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.PROCESSING, [CtVariableReadImpl]state);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the failed state of task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param state
     * 		whether the task failed
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateHasFailed([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean state) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.FAILED, [CtVariableReadImpl]state);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates Number of attempts for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param attempt
     * 		number of times the task has been executed
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean incrementAttempts([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]int attempt) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.ATTEMPTS, [CtVariableReadImpl]attempt);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Increments number of attempts made by a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean incrementAttempts([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]com.iterable.iterableapi.IterableTask task = [CtInvocationImpl]getTask([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]task == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtBinaryOperatorImpl][CtLiteralImpl]"No task found for id " + [CtVariableReadImpl]id);
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.ATTEMPTS, [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]task.attempts + [CtLiteralImpl]1);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates task with error data OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param errorData
     * 		error received after processing the task
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateError([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String errorData) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.ERROR, [CtVariableReadImpl]errorData);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates data for a task in OfflineTask table
     *
     * @param id
     * 		Unique id for the task
     * @param data
     * 		required for the task. JSONObject converted to string
     * @return Whether or not the task was updated
     */
    [CtTypeReferenceImpl]java.lang.Boolean updateData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String data) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]precheck())[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.ContentValues();
        [CtInvocationImpl][CtVariableReadImpl]contentValues.put([CtTypeAccessImpl]IterableTask.DATA, [CtVariableReadImpl]data);
        [CtReturnImpl]return [CtInvocationImpl]updateTaskWithContentValues([CtVariableReadImpl]id, [CtVariableReadImpl]contentValues);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Boolean updateTaskWithContentValues([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id, [CtParameterImpl][CtTypeReferenceImpl]android.content.ContentValues contentValues) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]0 > [CtInvocationImpl][CtFieldReadImpl]database.update([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.ITERABLE_TASK_TABLE_NAME, [CtVariableReadImpl]contentValues, [CtBinaryOperatorImpl][CtFieldReadImpl]IterableTask.TASK_ID + [CtLiteralImpl]"=?", [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtVariableReadImpl]id });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean precheck() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]database == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.iterable.iterableapi.IterableLogger.e([CtFieldReadImpl]com.iterable.iterableapi.IterableTaskManager.TAG, [CtLiteralImpl]"Database not initialized");
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]database.inTransaction()) && [CtInvocationImpl][CtFieldReadImpl]database.isOpen();
    }
}
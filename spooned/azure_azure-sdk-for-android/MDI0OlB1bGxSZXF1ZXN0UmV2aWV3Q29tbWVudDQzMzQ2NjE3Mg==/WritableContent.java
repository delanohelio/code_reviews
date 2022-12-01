[CompilationUnitImpl][CtPackageDeclarationImpl]package com.azure.android.storage.blob.transfer;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.os.ParcelFileDescriptor;
[CtUnresolvedImport]import android.content.UriPermission;
[CtImportImpl]import java.io.Closeable;
[CtUnresolvedImport]import android.net.Uri;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.nio.channels.FileChannel;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import androidx.annotation.MainThread;
[CtImportImpl]import java.nio.ByteBuffer;
[CtImportImpl]import java.util.concurrent.atomic.AtomicBoolean;
[CtImportImpl]import java.io.RandomAccessFile;
[CtImportImpl]import java.io.FileOutputStream;
[CtImportImpl]import java.util.List;
[CtClassImpl][CtJavaDocImpl]/**
 * Package private.
 *
 * A type that describes a content in the device to which data can be written.
 */
final class WritableContent {
    [CtFieldImpl]private final [CtTypeReferenceImpl]android.content.Context context;

    [CtFieldImpl]private final [CtTypeReferenceImpl]android.net.Uri contentUri;

    [CtFieldImpl]private final [CtTypeReferenceImpl]boolean useContentResolver;

    [CtFieldImpl][CtCommentImpl]// Channel to write to the Content if ContentResolver is required to resolve the Content,
    [CtCommentImpl]// i.e. when useContentResolver == true
    private [CtTypeReferenceImpl]com.azure.android.storage.blob.transfer.WritableContent.WriteToContentChannel contentChannel;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create WritableContent describing a content in the device on which data can be written.
     *
     * @param context
     * 		the context
     * @param contentUri
     * 		the content URI identifying the content
     * @param useContentResolver
     * 		indicate whether to use {@link android.content.ContentResolver} to resolve
     * 		the content URI
     */
    WritableContent([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]android.net.Uri contentUri, [CtParameterImpl][CtTypeReferenceImpl]boolean useContentResolver) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.contentUri = [CtVariableReadImpl]contentUri;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.useContentResolver = [CtVariableReadImpl]useContentResolver;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the {@link Uri} to the content.
     *
     * @return the content URI
     */
    [CtTypeReferenceImpl]android.net.Uri getUri() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.contentUri;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check whether to use {@link android.content.ContentResolver} to resolve the content URI.
     *
     * @return true if resolving content URI requires content resolver.
     */
    [CtTypeReferenceImpl]boolean isUseContentResolver() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.useContentResolver;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attempt to take persistable write permission on the content.
     *
     * @throws IllegalStateException
     * 		if write permission is not granted
     */
    [CtAnnotationImpl]@androidx.annotation.MainThread
    [CtTypeReferenceImpl]void takePersistableWritePermission() throws [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.useContentResolver) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.context.getContentResolver().takePersistableUriPermission([CtFieldReadImpl][CtThisAccessImpl]this.contentUri, [CtTypeAccessImpl]Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            [CtInvocationImpl]com.azure.android.storage.blob.transfer.WritableContent.checkPersistableWriteGranted([CtFieldReadImpl][CtThisAccessImpl]this.context, [CtFieldReadImpl][CtThisAccessImpl]this.contentUri);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Open the content for writing.
     *
     * @throws IOException
     * 		if fails to open underlying content resource in write mode
     * @throws IllegalStateException
     * 		if write permission to the content is not granted/revoked or
     * 		the channel was already opened and disposed
     */
    [CtTypeReferenceImpl]void openForWrite([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.useContentResolver) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentChannel == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.contentChannel = [CtInvocationImpl][CtTypeAccessImpl]com.azure.android.storage.blob.transfer.WritableContent.WriteToContentChannel.create([CtVariableReadImpl]context, [CtFieldReadImpl][CtThisAccessImpl]this.contentUri);
                } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentChannel.isClosed()) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtLiteralImpl]"A closed Content Channel cannot be opened.");
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Write a block of bytes to the content.
     *
     * @param blockOffset
     * 		the start offset to write the block to
     * @param block
     * 		the block of bytes to write
     * @throws IOException
     * 		the IO error when attempting to write
     * @throws IllegalStateException
     * 		if write permission is not granted or revoked
     */
    [CtTypeReferenceImpl]void writeBlock([CtParameterImpl][CtTypeReferenceImpl]long blockOffset, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] block) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.useContentResolver) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentChannel == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtLiteralImpl]"openForWrite(..) must be called before invoking writeBlock(..).");
            }
            [CtInvocationImpl][CtCommentImpl]// When `useContentResolver` is true then ContentUri must be treated as an opaque handle
            [CtCommentImpl]// and the raw-path must not be used.
            [CtCommentImpl]// https://commonsware.com/blog/2016/03/15/how-consume-content-uri.html
            [CtCommentImpl]// Obtaining a RandomAccessFile requires the raw-path to the file backing the ContentUri.
            [CtCommentImpl]// 
            [CtCommentImpl]// So to write to Content, instead of RandomAccessFile we will use FileChannel, specifically
            [CtCommentImpl]// we a shared instance of FileChannel to write the blocks downloaded by concurrent (OkHttp) threads.
            [CtCommentImpl]// Operations in FileChannel instance are concurrent safe.
            [CtCommentImpl]// https://developer.android.com/reference/java/nio/channels/FileChannel
            [CtCommentImpl]// 
            [CtCommentImpl]// The FileChannel instance is obtained from a FileOutputStream for the ContentUri
            [CtCommentImpl]// (see WriteToContentChannel). Only one instance of FileOutputStream can be opened
            [CtCommentImpl]// in "write-mode" so we share the FileOutputStream instance and it's backing
            [CtCommentImpl]// FileChannel instance.
            [CtCommentImpl]// https://docs.oracle.com/javase/7/docs/api/java/io/FileOutputStream.html
            [CtCommentImpl]// 
            [CtFieldReadImpl][CtThisAccessImpl]this.contentChannel.writeBlock([CtVariableReadImpl]blockOffset, [CtVariableReadImpl]block);
        } else [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.RandomAccessFile randomAccessFile = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.RandomAccessFile([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentUri.getPath(), [CtLiteralImpl]"rw")) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]randomAccessFile.seek([CtVariableReadImpl]blockOffset);
                [CtInvocationImpl][CtVariableReadImpl]randomAccessFile.write([CtVariableReadImpl]block);
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * close the content.
     *
     * @throws IOException
     * 		if close operation fails
     */
    [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl][CtThisAccessImpl]this.useContentResolver) [CtBlockImpl]{
            [CtSynchronizedImpl]synchronized([CtThisAccessImpl]this) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentChannel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.contentChannel.close();
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check a persistable write permission is granted on the content.
     *
     * @param context
     * 		the context to access content resolver
     * @param contentUri
     * 		the content uri
     * @throws IllegalStateException
     * 		if permission is not granted
     */
    private static [CtTypeReferenceImpl]void checkPersistableWriteGranted([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]android.net.Uri contentUri) throws [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]android.content.UriPermission> permissions = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContentResolver().getPersistedUriPermissions();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean grantedWrite = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]android.content.UriPermission permission : [CtVariableReadImpl]permissions) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]permission.isWritePermission()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]grantedWrite = [CtLiteralImpl]true;
                [CtBreakImpl]break;
            }
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]grantedWrite) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalStateException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Write permission for the content '" + [CtVariableReadImpl]contentUri) + [CtLiteralImpl]"' is not granted or revoked.");
        }
    }

    [CtClassImpl][CtJavaDocImpl]/**
     * A Channel to write to a Content identified by a ContentUri.
     */
    private static class WriteToContentChannel implements [CtTypeReferenceImpl]java.io.Closeable {
        [CtFieldImpl]private final [CtTypeReferenceImpl]android.content.Context context;

        [CtFieldImpl]private final [CtTypeReferenceImpl]android.net.Uri contentUri;

        [CtFieldImpl]private final [CtTypeReferenceImpl]android.os.ParcelFileDescriptor parcelFileDescriptor;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.io.FileOutputStream fileOutputStream;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.nio.channels.FileChannel fileChannel;

        [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean isClosed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.atomic.AtomicBoolean([CtLiteralImpl]false);

        [CtMethodImpl][CtJavaDocImpl]/**
         * Creates WriteToContentChannel to write to the Content identified by the given ContentUri.
         *
         * @param context
         * 		the context to resolve the content uri
         * @param contentUri
         * 		the uri of the content to write to using this Channel.
         * @throws IOException
         * 		if fails to open underlying content resource in write mode
         * @throws IllegalStateException
         * 		if write permission to the content is not granted or revoked
         */
        static [CtTypeReferenceImpl]com.azure.android.storage.blob.transfer.WritableContent.WriteToContentChannel create([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]android.net.Uri contentUri) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.azure.android.storage.blob.transfer.WritableContent.checkPersistableWriteGranted([CtVariableReadImpl]context, [CtVariableReadImpl]contentUri);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.android.storage.blob.transfer.WritableContent.WriteToContentChannel([CtVariableReadImpl]context, [CtVariableReadImpl]contentUri);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Write a block of bytes to the Channel.
         *
         * @param blockOffset
         * 		the start offset in the content to write the block
         * @param block
         * 		the block of bytes to write
         * @throws IOException
         * 		if write fails
         * @throws IllegalStateException
         * 		if write permission is not granted or revoked
         */
        [CtTypeReferenceImpl]void writeBlock([CtParameterImpl][CtTypeReferenceImpl]long blockOffset, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] block) throws [CtTypeReferenceImpl]java.io.IOException, [CtTypeReferenceImpl]java.lang.IllegalStateException [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.azure.android.storage.blob.transfer.WritableContent.checkPersistableWriteGranted([CtFieldReadImpl][CtThisAccessImpl]this.context, [CtFieldReadImpl][CtThisAccessImpl]this.contentUri);
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileChannel.write([CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.wrap([CtVariableReadImpl]block), [CtVariableReadImpl]blockOffset);
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         *
         * @return true if the Channel is closed
         */
        [CtTypeReferenceImpl]boolean isClosed() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.isClosed.get();
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Close the Channel.
         *
         * @throws IOException
         * 		if close fails
         */
        [CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.isClosed.getAndSet([CtLiteralImpl]true)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.parcelFileDescriptor != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.parcelFileDescriptor.close();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileOutputStream != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileOutputStream.close();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileChannel != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileChannel.close();
                }
            }
        }

        [CtConstructorImpl]private WriteToContentChannel([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]android.net.Uri contentUri) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.context = [CtVariableReadImpl]context;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.contentUri = [CtVariableReadImpl]contentUri;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.parcelFileDescriptor = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getContentResolver().openFileDescriptor([CtFieldReadImpl][CtThisAccessImpl]this.contentUri, [CtLiteralImpl]"w");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.parcelFileDescriptor == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.IOException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"FileDescriptor for the content '" + [CtFieldReadImpl][CtThisAccessImpl]this.contentUri) + [CtLiteralImpl]"' cannot be opened.");
            }
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileOutputStream = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileOutputStream([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.parcelFileDescriptor.getFileDescriptor());
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileChannel = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.fileOutputStream.getChannel();
        }
    }
}
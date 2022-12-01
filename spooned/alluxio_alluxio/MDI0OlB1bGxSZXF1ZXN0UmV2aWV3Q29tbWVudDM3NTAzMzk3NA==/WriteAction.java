[CompilationUnitImpl][CtCommentImpl]/* The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
(the "License"). You may not use this work except in compliance with the License, which is
available at www.apache.org/licenses/LICENSE-2.0

This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied, as more fully set forth in the License.

See the NOTICE file distributed with this work for information regarding copyright ownership.
 */
[CtPackageDeclarationImpl]package alluxio.table.common.transform.action;
[CtUnresolvedImport]import alluxio.job.plan.transform.CompactConfig;
[CtImportImpl]import org.apache.commons.io.FileUtils;
[CtUnresolvedImport]import alluxio.exception.ExceptionMessage;
[CtUnresolvedImport]import alluxio.job.JobConfig;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import alluxio.table.common.Layout;
[CtClassImpl][CtJavaDocImpl]/**
 * The definition of the write action.
 */
public class WriteAction implements [CtTypeReferenceImpl]alluxio.table.common.transform.action.TransformAction {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"write";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NUM_FILES_OPTION = [CtLiteralImpl]"hive.file.maxcount";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FILE_SIZE_OPTION = [CtLiteralImpl]"hive.file.minsize";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long DEFAULT_FILE_SIZE = [CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.io.FileUtils.[CtFieldReferenceImpl]ONE_GB * [CtLiteralImpl]2;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int DEFAULT_NUM_FILES = [CtLiteralImpl]100;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Layout type, for example "hive".
     */
    private final [CtTypeReferenceImpl]java.lang.String mLayoutType;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Expected number of files after compaction.
     */
    private final [CtTypeReferenceImpl]int mNumFiles;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Default file size after coalescing.
     */
    private final [CtTypeReferenceImpl]long mFileSize;

    [CtClassImpl][CtJavaDocImpl]/**
     * Factory to create an instance.
     */
    public static class WriteActionFactory implements [CtTypeReferenceImpl]alluxio.table.common.transform.action.TransformActionFactory {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.NAME;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]alluxio.table.common.transform.action.TransformAction create([CtParameterImpl][CtTypeReferenceImpl]java.lang.String definition, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> args, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> options) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkArgument([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]args.size() == [CtLiteralImpl]1, [CtInvocationImpl][CtTypeAccessImpl]ExceptionMessage.TRANSFORM_WRITE_ACTION_INVALID_ARGS.toString());
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String type = [CtInvocationImpl][CtVariableReadImpl]args.get([CtLiteralImpl]0);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numFiles = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]options.containsKey([CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.NUM_FILES_OPTION)) ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtInvocationImpl][CtVariableReadImpl]options.get([CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.NUM_FILES_OPTION)) : [CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.DEFAULT_NUM_FILES;
            [CtLocalVariableImpl][CtTypeReferenceImpl]long fileSize = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]options.containsKey([CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.FILE_SIZE_OPTION)) ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.Long.parseLong([CtInvocationImpl][CtVariableReadImpl]options.get([CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.FILE_SIZE_OPTION)) : [CtFieldReadImpl]alluxio.table.common.transform.action.WriteAction.DEFAULT_FILE_SIZE;
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]numFiles >= [CtLiteralImpl]0, [CtTypeAccessImpl]ExceptionMessage.TRANSFORM_WRITE_ACTION_INVALID_NUM_FILES);
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]alluxio.table.common.transform.action.WriteAction([CtVariableReadImpl]type, [CtVariableReadImpl]numFiles, [CtVariableReadImpl]fileSize);
        }
    }

    [CtConstructorImpl]private WriteAction([CtParameterImpl][CtTypeReferenceImpl]java.lang.String type, [CtParameterImpl][CtTypeReferenceImpl]int numFiles, [CtParameterImpl][CtTypeReferenceImpl]long fileSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mLayoutType = [CtVariableReadImpl]type;
        [CtAssignmentImpl][CtFieldWriteImpl]mNumFiles = [CtVariableReadImpl]numFiles;
        [CtAssignmentImpl][CtFieldWriteImpl]mFileSize = [CtVariableReadImpl]fileSize;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]alluxio.job.JobConfig generateJobConfig([CtParameterImpl][CtTypeReferenceImpl]alluxio.table.common.Layout base, [CtParameterImpl][CtTypeReferenceImpl]alluxio.table.common.Layout transformed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]alluxio.job.plan.transform.PartitionInfo basePartitionInfo = [CtInvocationImpl][CtTypeAccessImpl]alluxio.table.common.transform.action.TransformActionUtils.generatePartitionInfo([CtVariableReadImpl]base);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]alluxio.job.plan.transform.CompactConfig([CtVariableReadImpl]basePartitionInfo, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]base.getLocation().toString(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]transformed.getLocation().toString(), [CtFieldReadImpl]mLayoutType, [CtFieldReadImpl]mNumFiles, [CtFieldReadImpl]mFileSize);
    }
}
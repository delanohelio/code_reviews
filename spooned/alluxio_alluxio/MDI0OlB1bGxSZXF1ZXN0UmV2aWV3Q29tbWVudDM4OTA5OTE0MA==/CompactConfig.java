[CompilationUnitImpl][CtCommentImpl]/* The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
(the "License"). You may not use this work except in compliance with the License, which is
available at www.apache.org/licenses/LICENSE-2.0

This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied, as more fully set forth in the License.

See the NOTICE file distributed with this work for information regarding copyright ownership.
 */
[CtPackageDeclarationImpl]package alluxio.job.plan.transform;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonProperty;
[CtUnresolvedImport]import com.google.common.base.MoreObjects;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtUnresolvedImport]import javax.annotation.concurrent.ThreadSafe;
[CtUnresolvedImport]import alluxio.job.plan.PlanConfig;
[CtUnresolvedImport]import com.google.common.base.Objects;
[CtClassImpl][CtJavaDocImpl]/**
 * Configuration for a job to compact files directly under a directory.
 *
 * Files will be compacted into a certain number of files,
 * if the number of existing files is less than the specified number, then no compaction happens,
 * otherwise, assume we want to compact 100 files to 10 files, then every 10 files will be
 * compacted into one file.
 * The original order of rows is preserved.
 */
[CtAnnotationImpl]@javax.annotation.concurrent.ThreadSafe
public final class CompactConfig implements [CtTypeReferenceImpl]alluxio.job.plan.PlanConfig {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]3434270994964559796L;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NAME = [CtLiteralImpl]"Compact";

    [CtFieldImpl]private final [CtTypeReferenceImpl]alluxio.job.plan.transform.PartitionInfo mPartitionInfo;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Files directly under this directory are compacted.
     */
    private final [CtTypeReferenceImpl]java.lang.String mInput;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Compacted files are stored under this directory.
     */
    private final [CtTypeReferenceImpl]java.lang.String mOutput;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Max number of files after compaction.
     */
    private final [CtTypeReferenceImpl]int mMaxNumFiles;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Minimum file size for compaction.
     */
    private final [CtTypeReferenceImpl]long mMinFileSize;

    [CtConstructorImpl][CtJavaDocImpl]/**
     *
     * @param partitionInfo
     * 		the partition info
     * @param input
     * 		the input directory
     * @param output
     * 		the output directory
     * @param maxNumFiles
     * 		the maximum number of files after compaction
     * @param minFileSize
     * 		the minimum file size for coalescing
     */
    public CompactConfig([CtParameterImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"partitionInfo")
    [CtTypeReferenceImpl]alluxio.job.plan.transform.PartitionInfo partitionInfo, [CtParameterImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"input")
    [CtTypeReferenceImpl]java.lang.String input, [CtParameterImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"output")
    [CtTypeReferenceImpl]java.lang.String output, [CtParameterImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"maxNumFiles")
    [CtTypeReferenceImpl]java.lang.Integer maxNumFiles, [CtParameterImpl][CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"minFileSize")
    [CtTypeReferenceImpl]java.lang.Long minFileSize) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]mPartitionInfo = [CtVariableReadImpl]partitionInfo;
        [CtAssignmentImpl][CtFieldWriteImpl]mInput = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]input, [CtLiteralImpl]"input");
        [CtAssignmentImpl][CtFieldWriteImpl]mOutput = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]output, [CtLiteralImpl]"output");
        [CtAssignmentImpl][CtFieldWriteImpl]mMaxNumFiles = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]maxNumFiles, [CtLiteralImpl]"maxNumFiles");
        [CtAssignmentImpl][CtFieldWriteImpl]mMinFileSize = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtVariableReadImpl]minFileSize, [CtLiteralImpl]"minFileSize");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the partition info
     */
    public [CtTypeReferenceImpl]alluxio.job.plan.transform.PartitionInfo getPartitionInfo() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mPartitionInfo;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the input directory
     */
    public [CtTypeReferenceImpl]java.lang.String getInput() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mInput;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the output directory
     */
    public [CtTypeReferenceImpl]java.lang.String getOutput() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mOutput;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the number of files after compaction
     */
    public [CtTypeReferenceImpl]int getMaxNumFiles() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mMaxNumFiles;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the file size
     */
    public [CtTypeReferenceImpl]long getMinFileSize() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]mMinFileSize;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object obj) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]obj == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]obj) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]obj instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]alluxio.job.plan.transform.CompactConfig)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]alluxio.job.plan.transform.CompactConfig that = [CtVariableReadImpl](([CtTypeReferenceImpl]alluxio.job.plan.transform.CompactConfig) (obj));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]mPartitionInfo.equals([CtFieldReadImpl][CtVariableReadImpl]that.mPartitionInfo) && [CtInvocationImpl][CtFieldReadImpl]mInput.equals([CtFieldReadImpl][CtVariableReadImpl]that.mInput)) && [CtInvocationImpl][CtFieldReadImpl]mOutput.equals([CtFieldReadImpl][CtVariableReadImpl]that.mOutput)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]mMaxNumFiles == [CtFieldReadImpl][CtVariableReadImpl]that.mMaxNumFiles)) && [CtBinaryOperatorImpl]([CtFieldReadImpl]mMinFileSize == [CtFieldReadImpl][CtVariableReadImpl]that.mMinFileSize);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Objects.hashCode([CtFieldReadImpl]mPartitionInfo, [CtFieldReadImpl]mInput, [CtFieldReadImpl]mOutput, [CtFieldReadImpl]mMaxNumFiles, [CtFieldReadImpl]mMinFileSize);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.MoreObjects.toStringHelper([CtThisAccessImpl]this).add([CtLiteralImpl]"input", [CtFieldReadImpl]mInput).add([CtLiteralImpl]"output", [CtFieldReadImpl]mOutput).add([CtLiteralImpl]"maxNumFiles", [CtFieldReadImpl]mMaxNumFiles).add([CtLiteralImpl]"minFileSize", [CtFieldReadImpl]mMinFileSize).add([CtLiteralImpl]"partitionInfo", [CtFieldReadImpl]mPartitionInfo).toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]alluxio.job.plan.transform.CompactConfig.NAME;
    }
}
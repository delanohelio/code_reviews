[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.ignite.internal.processors.bulkload;
[CtUnresolvedImport]import org.apache.ignite.internal.processors.query.RunningQueryManager;
[CtUnresolvedImport]import org.apache.ignite.IgniteIllegalStateException;
[CtUnresolvedImport]import org.apache.ignite.IgniteCheckedException;
[CtUnresolvedImport]import org.apache.ignite.IgniteDataStreamer;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.apache.ignite.internal.util.lang.IgniteClosureX;
[CtUnresolvedImport]import org.apache.ignite.lang.IgniteBiTuple;
[CtClassImpl][CtJavaDocImpl]/**
 * Bulk load (COPY) command processor used on server to keep various context data and process portions of input
 * received from the client side.
 */
public class BulkLoadProcessor implements [CtTypeReferenceImpl]java.lang.AutoCloseable {
    [CtFieldImpl][CtJavaDocImpl]/**
     * Parser of the input bytes.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.bulkload.BulkLoadParser inputParser;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Converter, which transforms the list of strings parsed from the input stream to the key+value entry to add to
     * the cache.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.util.lang.IgniteClosureX<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>> dataConverter;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Streamer that puts actual key/value into the cache.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.bulkload.BulkLoadCacheWriter outputStreamer;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Becomes true after {@link #close()} method is called.
     */
    private [CtTypeReferenceImpl]boolean isClosed;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Running query manager.
     */
    private final [CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.RunningQueryManager runningQryMgr;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Query id.
     */
    private final [CtTypeReferenceImpl]java.lang.Long qryId;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Exception, current load process ended with, or {@code null} if in progress or if succeded.
     */
    private [CtTypeReferenceImpl]java.lang.Exception failReason;

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Creates bulk load processor.
     *
     * @param inputParser
     * 		Parser of the input bytes.
     * @param dataConverter
     * 		Converter, which transforms the list of strings parsed from the input stream to the
     * 		key+value entry to add to the cache.
     * @param outputStreamer
     * 		Streamer that puts actual key/value into the cache.
     * @param runningQryMgr
     * 		Running query manager.
     * @param qryId
     * 		Running query id.
     */
    public BulkLoadProcessor([CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.bulkload.BulkLoadParser inputParser, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.util.lang.IgniteClosureX<[CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?>, [CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?>> dataConverter, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.bulkload.BulkLoadCacheWriter outputStreamer, [CtParameterImpl][CtTypeReferenceImpl]org.apache.ignite.internal.processors.query.RunningQueryManager runningQryMgr, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long qryId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputParser = [CtVariableReadImpl]inputParser;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dataConverter = [CtVariableReadImpl]dataConverter;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.outputStreamer = [CtVariableReadImpl]outputStreamer;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.runningQryMgr = [CtVariableReadImpl]runningQryMgr;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.qryId = [CtVariableReadImpl]qryId;
        [CtAssignmentImpl][CtFieldWriteImpl]isClosed = [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the streamer that puts actual key/value into the cache.
     *
     * @return Streamer that puts actual key/value into the cache.
     */
    public [CtTypeReferenceImpl]org.apache.ignite.internal.processors.bulkload.BulkLoadCacheWriter outputStreamer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]outputStreamer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Processes the incoming batch and writes data to the cache by calling the data converter and output streamer.
     *
     * @param batchData
     * 		Data from the current batch.
     * @param isLastBatch
     * 		true if this is the last batch.
     * @throws IgniteIllegalStateException
     * 		when called after {@link #close()}.
     */
    public [CtTypeReferenceImpl]void processBatch([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] batchData, [CtParameterImpl][CtTypeReferenceImpl]boolean isLastBatch) throws [CtTypeReferenceImpl]org.apache.ignite.IgniteCheckedException [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]isClosed)[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.ignite.IgniteIllegalStateException([CtLiteralImpl]"Attempt to process a batch on a closed BulkLoadProcessor");

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Iterable<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object>> inputRecords = [CtInvocationImpl][CtFieldReadImpl]inputParser.parseBatch([CtVariableReadImpl]batchData, [CtVariableReadImpl]isLastBatch);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> record : [CtVariableReadImpl]inputRecords) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.ignite.lang.IgniteBiTuple<[CtWildcardReferenceImpl]?, [CtWildcardReferenceImpl]?> kv = [CtInvocationImpl][CtFieldReadImpl]dataConverter.apply([CtVariableReadImpl]record);
            [CtInvocationImpl][CtFieldReadImpl]outputStreamer.apply([CtVariableReadImpl]kv);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Is called to notify processor, that bulk load execution, this processor is performing, failed with specified
     * exception.
     *
     * @param failReason
     * 		why current load failed.
     */
    public [CtTypeReferenceImpl]void onError([CtParameterImpl][CtTypeReferenceImpl]java.lang.Exception failReason) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.failReason = [CtVariableReadImpl]failReason;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Aborts processing and closes the underlying objects ({@link IgniteDataStreamer}).
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void close() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]isClosed)[CtBlockImpl]
            [CtReturnImpl]return;

        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]isClosed = [CtLiteralImpl]true;
            [CtInvocationImpl][CtFieldReadImpl]outputStreamer.close();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]failReason == [CtLiteralImpl]null)[CtBlockImpl]
                [CtAssignmentImpl][CtFieldWriteImpl]failReason = [CtVariableReadImpl]e;

            [CtThrowImpl]throw [CtVariableReadImpl]e;
        } finally [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]runningQryMgr.unregister([CtFieldReadImpl]qryId, [CtFieldReadImpl]failReason);
        }
    }
}
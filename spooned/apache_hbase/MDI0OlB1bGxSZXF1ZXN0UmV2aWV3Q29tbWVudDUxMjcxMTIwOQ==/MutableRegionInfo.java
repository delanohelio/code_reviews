[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package org.apache.hadoop.hbase.client;
[CtUnresolvedImport]import org.apache.hadoop.hbase.HConstants;
[CtUnresolvedImport]import org.apache.hadoop.hbase.TableName;
[CtUnresolvedImport]import org.apache.yetus.audience.InterfaceAudience;
[CtImportImpl]import java.util.Arrays;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.apache.hadoop.hbase.util.Bytes;
[CtClassImpl][CtJavaDocImpl]/**
 * An implementation of RegionInfo that adds mutable methods so can build a RegionInfo instance.
 * Package private. Use {@link RegionInfoBuilder} creating instances of {@link RegionInfo}s.
 */
[CtAnnotationImpl]@org.apache.yetus.audience.InterfaceAudience.Private
class MutableRegionInfo implements [CtTypeReferenceImpl]org.apache.hadoop.hbase.client.RegionInfo {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.class);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]int MAX_REPLICA_ID = [CtLiteralImpl]0xffff;

    [CtFieldImpl][CtJavaDocImpl]/**
     * The new format for a region name contains its encodedName at the end.
     * The encoded name also serves as the directory name for the region
     * in the filesystem.
     *
     * New region name format:
     *    &lt;tablename>,,&lt;startkey>,&lt;regionIdTimestamp>.&lt;encodedName>.
     * where,
     *    &lt;encodedName> is a hex version of the MD5 hash of
     *    &lt;tablename>,&lt;startkey>,&lt;regionIdTimestamp>
     *
     * The old region name format:
     *    &lt;tablename>,&lt;startkey>,&lt;regionIdTimestamp>
     * For region names in the old format, the encoded name is a 32-bit
     * JenkinsHash integer value (in its decimal notation, string form).
     * <p>
     * **NOTE**
     *
     * The first hbase:meta region, and regions created by an older
     * version of HBase (0.20 or prior) will continue to use the
     * old region name format.
     */
    [CtCommentImpl]// This flag is in the parent of a split while the parent is still referenced by daughter
    [CtCommentImpl]// regions. We USED to set this flag when we disabled a table but now table state is kept up in
    [CtCommentImpl]// zookeeper as of 0.90.0 HBase. And now in DisableTableProcedure, finally we will create bunch
    [CtCommentImpl]// of UnassignProcedures and at the last of the procedure we will set the region state to
    [CtCommentImpl]// CLOSED, and will not change the offLine flag.
    private [CtTypeReferenceImpl]boolean offLine;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean split;

    [CtFieldImpl]private final [CtTypeReferenceImpl]long regionId;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int replicaId;

    [CtFieldImpl]private final [CtArrayTypeReferenceImpl]byte[] regionName;

    [CtFieldImpl]private final [CtArrayTypeReferenceImpl]byte[] startKey;

    [CtFieldImpl]private final [CtArrayTypeReferenceImpl]byte[] endKey;

    [CtFieldImpl]private final [CtTypeReferenceImpl]int hashCode;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String encodedName;

    [CtFieldImpl]private final [CtArrayTypeReferenceImpl]byte[] encodedNameAsBytes;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName;

    [CtMethodImpl]private static [CtTypeReferenceImpl]int generateHashCode([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName, [CtParameterImpl]final [CtArrayTypeReferenceImpl]byte[] startKey, [CtParameterImpl]final [CtArrayTypeReferenceImpl]byte[] endKey, [CtParameterImpl]final [CtTypeReferenceImpl]long regionId, [CtParameterImpl]final [CtTypeReferenceImpl]int replicaId, [CtParameterImpl][CtTypeReferenceImpl]boolean offLine, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] regionName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int result = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.hashCode([CtVariableReadImpl]regionName);
        [CtAssignmentImpl][CtVariableWriteImpl]result = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtVariableReadImpl]result ^ [CtVariableReadImpl]regionId));
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]result ^= [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.hashCode([CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkStartKey([CtVariableReadImpl]startKey));
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]result ^= [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.hashCode([CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkEndKey([CtVariableReadImpl]endKey));
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]result ^= [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.valueOf([CtVariableReadImpl]offLine).hashCode();
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]result ^= [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.hashCode([CtInvocationImpl][CtVariableReadImpl]tableName.getName());
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]result ^= [CtVariableReadImpl]replicaId;
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]byte[] checkStartKey([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] startKey) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]startKey == [CtLiteralImpl]null ? [CtFieldReadImpl]org.apache.hadoop.hbase.HConstants.EMPTY_START_ROW : [CtVariableReadImpl]startKey;
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]byte[] checkEndKey([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] endKey) [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]endKey == [CtLiteralImpl]null ? [CtFieldReadImpl]org.apache.hadoop.hbase.HConstants.EMPTY_END_ROW : [CtVariableReadImpl]endKey;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName checkTableName([CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]tableName == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"TableName cannot be null");
        }
        [CtReturnImpl]return [CtVariableReadImpl]tableName;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]int checkReplicaId([CtParameterImpl][CtTypeReferenceImpl]int regionId) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]regionId > [CtFieldReadImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.MAX_REPLICA_ID) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtLiteralImpl]"ReplicaId cannot be greater than" + [CtFieldReadImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.MAX_REPLICA_ID);
        }
        [CtReturnImpl]return [CtVariableReadImpl]regionId;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Package private constructor used constructing MutableRegionInfo for the first meta regions
     */
    MutableRegionInfo([CtParameterImpl][CtTypeReferenceImpl]long regionId, [CtParameterImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName, [CtParameterImpl][CtTypeReferenceImpl]int replicaId) [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]tableName, [CtTypeAccessImpl]HConstants.EMPTY_START_ROW, [CtTypeAccessImpl]HConstants.EMPTY_END_ROW, [CtLiteralImpl]false, [CtVariableReadImpl]regionId, [CtVariableReadImpl]replicaId, [CtLiteralImpl]false);
    }

    [CtConstructorImpl]MutableRegionInfo([CtParameterImpl]final [CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName tableName, [CtParameterImpl]final [CtArrayTypeReferenceImpl]byte[] startKey, [CtParameterImpl]final [CtArrayTypeReferenceImpl]byte[] endKey, [CtParameterImpl]final [CtTypeReferenceImpl]boolean split, [CtParameterImpl]final [CtTypeReferenceImpl]long regionId, [CtParameterImpl]final [CtTypeReferenceImpl]int replicaId, [CtParameterImpl][CtTypeReferenceImpl]boolean offLine) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tableName = [CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkTableName([CtVariableReadImpl]tableName);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startKey = [CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkStartKey([CtVariableReadImpl]startKey);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endKey = [CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkEndKey([CtVariableReadImpl]endKey);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.split = [CtVariableReadImpl]split;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.regionId = [CtVariableReadImpl]regionId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.replicaId = [CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.checkReplicaId([CtVariableReadImpl]replicaId);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.offLine = [CtVariableReadImpl]offLine;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.regionName = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.client.RegionInfo.createRegionName([CtFieldReadImpl][CtThisAccessImpl]this.tableName, [CtFieldReadImpl][CtThisAccessImpl]this.startKey, [CtFieldReadImpl][CtThisAccessImpl]this.regionId, [CtFieldReadImpl][CtThisAccessImpl]this.replicaId, [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.tableName.equals([CtTypeAccessImpl]TableName.META_TABLE_NAME));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encodedName = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.client.RegionInfo.encodeRegionName([CtFieldReadImpl][CtThisAccessImpl]this.regionName);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hashCode = [CtInvocationImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.generateHashCode([CtFieldReadImpl][CtThisAccessImpl]this.tableName, [CtFieldReadImpl][CtThisAccessImpl]this.startKey, [CtFieldReadImpl][CtThisAccessImpl]this.endKey, [CtFieldReadImpl][CtThisAccessImpl]this.regionId, [CtFieldReadImpl][CtThisAccessImpl]this.replicaId, [CtFieldReadImpl][CtThisAccessImpl]this.offLine, [CtFieldReadImpl][CtThisAccessImpl]this.regionName);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.encodedNameAsBytes = [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toBytes([CtFieldReadImpl][CtThisAccessImpl]this.encodedName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Return a short, printable name for this region (usually encoded name) for us logging.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getShortNameToLog() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.client.RegionInfo.prettyPrint([CtInvocationImpl][CtThisAccessImpl]this.getEncodedName());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the regionId
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]long getRegionId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]regionId;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the regionName as an array of bytes.
     * @see #getRegionNameAsString()
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] getRegionName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]regionName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return Region name as a String for use in logging, etc.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getRegionNameAsString() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.client.RegionInfo.getRegionNameAsString([CtThisAccessImpl]this, [CtFieldReadImpl][CtThisAccessImpl]this.regionName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the encoded region name
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getEncodedName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.encodedName;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] getEncodedNameAsBytes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.encodedNameAsBytes;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the startKey
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] getStartKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]startKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the endKey
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] getEndKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]endKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get current table name of the region
     *
     * @return TableName
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.hadoop.hbase.TableName getTable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.tableName;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns true if the given inclusive range of rows is fully contained
     * by this region. For example, if the region is foo,a,g and this is
     * passed ["b","c"] or ["a","c"] it will return true, but if this is passed
     * ["b","z"] it will return false.
     *
     * @throws IllegalArgumentException
     * 		if the range passed is invalid (ie. end &lt; start)
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsRange([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] rangeStartKey, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] rangeEndKey) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.compareTo([CtVariableReadImpl]rangeStartKey, [CtVariableReadImpl]rangeEndKey) > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Invalid range: " + [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toStringBinary([CtVariableReadImpl]rangeStartKey)) + [CtLiteralImpl]" > ") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toStringBinary([CtVariableReadImpl]rangeEndKey));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean firstKeyInRange = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.compareTo([CtVariableReadImpl]rangeStartKey, [CtFieldReadImpl]startKey) >= [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean lastKeyInRange = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.compareTo([CtVariableReadImpl]rangeEndKey, [CtFieldReadImpl]endKey) < [CtLiteralImpl]0) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.equals([CtFieldReadImpl]endKey, [CtTypeAccessImpl]HConstants.EMPTY_BYTE_ARRAY);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]firstKeyInRange && [CtVariableReadImpl]lastKeyInRange;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the given row falls in this region.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean containsRow([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] row) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.compareTo([CtVariableReadImpl]row, [CtFieldReadImpl]startKey) >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.compareTo([CtVariableReadImpl]row, [CtFieldReadImpl]endKey) < [CtLiteralImpl]0) || [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.equals([CtFieldReadImpl]endKey, [CtTypeAccessImpl]HConstants.EMPTY_BYTE_ARRAY));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return true if this region is a meta region
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isMetaRegion() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]tableName.equals([CtTypeAccessImpl]TableName.META_TABLE_NAME);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return True if has been split and has daughters.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isSplit() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.split;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param split
     * 		set split status
     * @return MutableRegionInfo
     */
    public [CtTypeReferenceImpl]org.apache.hadoop.hbase.client.MutableRegionInfo setSplit([CtParameterImpl][CtTypeReferenceImpl]boolean split) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.split = [CtVariableReadImpl]split;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return True if this region is offline.
     * @deprecated since 3.0.0 and will be removed in 4.0.0
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]boolean isOffline() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.offLine;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The parent of a region split is offline while split daughters hold
     * references to the parent. Offlined regions are closed.
     *
     * @param offLine
     * 		Set online/offline status.
     * @return MutableRegionInfo
     */
    public [CtTypeReferenceImpl]org.apache.hadoop.hbase.client.MutableRegionInfo setOffline([CtParameterImpl][CtTypeReferenceImpl]boolean offLine) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.offLine = [CtVariableReadImpl]offLine;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return True if this is a split parent region.
     * @deprecated since 3.0.0 and will be removed in 4.0.0
     */
    [CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]boolean isSplitParent() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isSplit()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isOffline()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.hadoop.hbase.client.MutableRegionInfo.LOG.warn([CtBinaryOperatorImpl][CtLiteralImpl]"Region is split but NOT offline: " + [CtInvocationImpl]getRegionNameAsString());
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the region replica id
     *
     * @return returns region replica id
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getReplicaId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]replicaId;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see Object#toString()
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"{ENCODED => " + [CtInvocationImpl]getEncodedName()) + [CtLiteralImpl]", ") + [CtFieldReadImpl]org.apache.hadoop.hbase.HConstants.NAME) + [CtLiteralImpl]" => '") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toStringBinary([CtFieldReadImpl][CtThisAccessImpl]this.regionName)) + [CtLiteralImpl]"', STARTKEY => '") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toStringBinary([CtFieldReadImpl][CtThisAccessImpl]this.startKey)) + [CtLiteralImpl]"', ENDKEY => '") + [CtInvocationImpl][CtTypeAccessImpl]org.apache.hadoop.hbase.util.Bytes.toStringBinary([CtFieldReadImpl][CtThisAccessImpl]this.endKey)) + [CtLiteralImpl]"'") + [CtConditionalImpl]([CtInvocationImpl]isOffline() ? [CtLiteralImpl]", OFFLINE => true" : [CtLiteralImpl]"")) + [CtConditionalImpl]([CtInvocationImpl]isSplit() ? [CtLiteralImpl]", SPLIT => true" : [CtLiteralImpl]"")) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]replicaId > [CtLiteralImpl]0 ? [CtBinaryOperatorImpl][CtLiteralImpl]", REPLICA_ID => " + [CtFieldReadImpl]replicaId : [CtLiteralImpl]"")) + [CtLiteralImpl]"}";
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see Object#equals(Object)
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.apache.hadoop.hbase.client.RegionInfo)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]compareTo([CtVariableReadImpl](([CtTypeReferenceImpl]org.apache.hadoop.hbase.client.RegionInfo) (o))) == [CtLiteralImpl]0;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @see Object#hashCode()
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.hashCode;
    }
}
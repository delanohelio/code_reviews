[CompilationUnitImpl][CtCommentImpl]/* This file is part of Splice Machine.
Splice Machine is free software: you can redistribute it and/or modify it under the terms of the
GNU Affero General Public License as published by the Free Software Foundation, either
version 3, or (at your option) any later version.
Splice Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with Splice Machine.
If not, see <http://www.gnu.org/licenses/>.

Some parts of this source code are based on Apache Derby, and the following notices apply to
Apache Derby:

Apache Derby is a subproject of the Apache DB project, and is licensed under
the Apache License, Version 2.0 (the "License"); you may not use these files
except in compliance with the License. You may obtain a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

Splice Machine, Inc. has modified the Apache Derby code in this file.

All such Splice Machine modifications are Copyright 2012 - 2020 Splice Machine, Inc.,
and are licensed to you under the GNU Affero General Public License.
 */
[CtPackageDeclarationImpl]package com.splicemachine.db.impl.sql.compile;
[CtUnresolvedImport]import com.splicemachine.db.iapi.sql.execute.ConstantAction;
[CtUnresolvedImport]import com.splicemachine.db.iapi.sql.dictionary.SchemaDescriptor;
[CtImportImpl]import java.util.Hashtable;
[CtUnresolvedImport]import com.splicemachine.db.iapi.types.DataTypeDescriptor;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import com.splicemachine.db.iapi.services.sanity.SanityManager;
[CtUnresolvedImport]import com.splicemachine.db.iapi.sql.dictionary.TableDescriptor;
[CtUnresolvedImport]import com.splicemachine.db.iapi.error.StandardException;
[CtUnresolvedImport]import com.splicemachine.db.iapi.reference.SQLState;
[CtImportImpl]import java.util.Vector;
[CtUnresolvedImport]import com.splicemachine.db.iapi.sql.dictionary.ColumnDescriptor;
[CtUnresolvedImport]import com.splicemachine.db.iapi.services.property.PropertyUtil;
[CtUnresolvedImport]import com.splicemachine.db.catalog.UUID;
[CtUnresolvedImport]import com.splicemachine.db.iapi.reference.Property;
[CtClassImpl][CtJavaDocImpl]/**
 * A CreateIndexNode is the root of a QueryTree that represents a CREATE INDEX
 * statement.
 */
public class CreateIndexNode extends [CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.DDLStatementNode {
    [CtFieldImpl][CtTypeReferenceImpl]boolean unique;

    [CtFieldImpl][CtTypeReferenceImpl]boolean uniqueWithDuplicateNulls;

    [CtFieldImpl][CtCommentImpl]// DataDictionary		dd = null;
    [CtTypeReferenceImpl]java.util.Properties properties;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String indexType;

    [CtFieldImpl][CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName indexName;

    [CtFieldImpl][CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName tableName;

    [CtFieldImpl][CtTypeReferenceImpl]java.util.Vector columnNameList;

    [CtFieldImpl][CtArrayTypeReferenceImpl]java.lang.String[] columnNames = [CtLiteralImpl]null;

    [CtFieldImpl][CtArrayTypeReferenceImpl]boolean[] isAscending;

    [CtFieldImpl][CtArrayTypeReferenceImpl]int[] boundColumnIDs;

    [CtFieldImpl][CtTypeReferenceImpl]boolean excludeNulls;

    [CtFieldImpl][CtTypeReferenceImpl]boolean excludeDefaults;

    [CtFieldImpl][CtTypeReferenceImpl]boolean preSplit;

    [CtFieldImpl][CtTypeReferenceImpl]boolean isLogicalKey;

    [CtFieldImpl][CtTypeReferenceImpl]boolean sampling;

    [CtFieldImpl][CtTypeReferenceImpl]double sampleFraction;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String splitKeyPath;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String hfilePath;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String columnDelimiter;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String characterDelimiter;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String timestampFormat;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String dateFormat;

    [CtFieldImpl][CtTypeReferenceImpl]java.lang.String timeFormat;

    [CtFieldImpl][CtTypeReferenceImpl]com.splicemachine.db.iapi.sql.dictionary.TableDescriptor td;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initializer for a CreateIndexNode
     *
     * @param unique	True
     * 		means it's a unique index
     * @param indexType	The
     * 		type of index
     * @param indexName	The
     * 		name of the index
     * @param tableName	The
     * 		name of the table the index will be on
     * @param columnNameList	A
     * 		list of column names, in the order they
     * 		appear in the index.
     * @param properties	The
     * 		optional properties list associated with the index.
     * @exception StandardException		Thrown
     * 		on error
     */
    public [CtTypeReferenceImpl]void init([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object unique, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object indexType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object indexName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object tableName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object columnNameList, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object excludeNulls, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object excludeDefaults, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object preSplit, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object isLogicalKey, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object sampling, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object sampleFraction, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object splitKeyPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object columnDelimiter, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object characterDelimite, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object timestampFormat, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object dateFormat, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object timeFormat, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object hfilePath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object properties) throws [CtTypeReferenceImpl]com.splicemachine.db.iapi.error.StandardException [CtBlockImpl]{
        [CtInvocationImpl]initAndCheck([CtVariableReadImpl]indexName);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.unique = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (unique));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexType = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (indexType));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexName = [CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName) (indexName));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.tableName = [CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName) (tableName));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.columnNameList = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Vector) (columnNameList));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.properties = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Properties) (properties));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeNulls = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (excludeNulls));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludeDefaults = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (excludeDefaults));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.preSplit = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (preSplit));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.isLogicalKey = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (isLogicalKey));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sampling = [CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.Boolean) (sampling));
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sampleFraction = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]sampleFraction != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.NumericConstantNode) (sampleFraction)).getValue().getDouble() : [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.splitKeyPath = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]splitKeyPath != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (splitKeyPath)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.columnDelimiter = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]columnDelimiter != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (columnDelimiter)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.characterDelimiter = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]characterDelimite != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (characterDelimite)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timestampFormat = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]timestampFormat != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (timestampFormat)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dateFormat = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]dateFormat != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (dateFormat)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.timeFormat = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]timeFormat != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (timeFormat)).getString() : [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hfilePath = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]hfilePath != [CtLiteralImpl]null) ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.CharConstantNode) (hfilePath)).getString() : [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Convert this object to a String.  See comments in QueryTreeNode.java
     * for how this should be done for tree printing.
     *
     * @return This object as a String
     */
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtIfImpl]if ([CtFieldReadImpl]com.splicemachine.db.iapi.services.sanity.SanityManager.DEBUG) [CtBlockImpl]{
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtSuperAccessImpl]super.toString() + [CtLiteralImpl]"unique: ") + [CtFieldReadImpl]unique) + [CtLiteralImpl]"\n") + [CtLiteralImpl]"indexType: ") + [CtFieldReadImpl]indexType) + [CtLiteralImpl]"\n") + [CtLiteralImpl]"indexName: ") + [CtFieldReadImpl]indexName) + [CtLiteralImpl]"\n") + [CtLiteralImpl]"tableName: ") + [CtFieldReadImpl]tableName) + [CtLiteralImpl]"\n") + [CtLiteralImpl]"properties: ") + [CtFieldReadImpl]properties) + [CtLiteralImpl]"\n";
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String statementToString() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]"CREATE INDEX";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean getUniqueness() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]unique;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getIndexType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]indexType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName getIndexName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]indexName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.splicemachine.db.catalog.UUID getBoundTableID() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]td.getUUID();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Properties getProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]properties;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.splicemachine.db.impl.sql.compile.TableName getIndexTableName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]tableName;
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]java.lang.String[] getColumnNames() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]columnNames;
    }

    [CtMethodImpl][CtCommentImpl]// get 1-based column ids
    public [CtArrayTypeReferenceImpl]int[] getKeyColumnIDs() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]boundColumnIDs;
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]boolean[] getIsAscending() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]isAscending;
    }

    [CtMethodImpl][CtCommentImpl]// We inherit the generate() method from DDLStatementNode.
    [CtJavaDocImpl]/**
     * Bind this CreateIndexNode.  This means doing any static error
     * checking that can be done before actually creating the table.
     * For example, verifying that the column name list does not
     * contain any duplicate column names.
     *
     * @exception StandardException		Thrown
     * 		on error
     */
    public [CtTypeReferenceImpl]void bindStatement() throws [CtTypeReferenceImpl]com.splicemachine.db.iapi.error.StandardException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int columnCount;
        [CtAssignmentImpl][CtCommentImpl]/* SchemaDescriptor sd = getSchemaDescriptor(); */
        [CtFieldWriteImpl]td = [CtInvocationImpl]getTableDescriptor([CtFieldReadImpl]tableName);
        [CtInvocationImpl][CtCommentImpl]// If total number of indexes on the table so far is more than 32767, then we need to throw an exception
        [CtCommentImpl]/* if (td.getTotalNumberOfIndexes() > Limits.DB2_MAX_INDEXES_ON_TABLE)
        {
        throw StandardException.newException(SQLState.LANG_TOO_MANY_INDEXES_ON_TABLE,
        String.valueOf(td.getTotalNumberOfIndexes()),
        tableName,
        String.valueOf(Limits.DB2_MAX_INDEXES_ON_TABLE));
        }
         */
        [CtCommentImpl]/* Validate the column name list */
        verifyAndGetUniqueNames();
        [CtAssignmentImpl][CtVariableWriteImpl]columnCount = [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]columnNames.length;
        [CtAssignmentImpl][CtFieldWriteImpl]boundColumnIDs = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtVariableReadImpl]columnCount];
        [CtForImpl][CtCommentImpl]// Verify that the columns exist
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]columnCount; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.splicemachine.db.iapi.sql.dictionary.ColumnDescriptor columnDescriptor;
            [CtAssignmentImpl][CtVariableWriteImpl]columnDescriptor = [CtInvocationImpl][CtFieldReadImpl]td.getColumnDescriptor([CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]i]);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]columnDescriptor == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.splicemachine.db.iapi.error.StandardException.newException([CtTypeAccessImpl]SQLState.LANG_COLUMN_NOT_FOUND_IN_TABLE, [CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]i], [CtFieldReadImpl]tableName);
            }
            [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]boundColumnIDs[[CtVariableReadImpl]i] = [CtInvocationImpl][CtVariableReadImpl]columnDescriptor.getPosition();
            [CtAssignmentImpl][CtCommentImpl]// set this only once -- if just one column does is missing "not null" constraint in schema
            [CtFieldWriteImpl]uniqueWithDuplicateNulls = [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtFieldReadImpl]uniqueWithDuplicateNulls) && [CtBinaryOperatorImpl]([CtFieldReadImpl]unique && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]columnDescriptor.hasNonNullDefault()));
            [CtIfImpl][CtCommentImpl]// Don't allow a column to be created on a non-orderable type
            if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]columnDescriptor.getType().getTypeId().orderable([CtInvocationImpl]getClassFactory())) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.splicemachine.db.iapi.error.StandardException.newException([CtTypeAccessImpl]SQLState.LANG_COLUMN_NOT_ORDERABLE_DURING_EXECUTION, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]columnDescriptor.getType().getTypeId().getSQLTypeName());
            }
        }
        [CtInvocationImpl][CtCommentImpl]/* Check for number of key columns to be less than 16 to match DB2 */
        [CtCommentImpl]/* if (columnCount > 16)
        throw StandardException.newException(SQLState.LANG_TOO_MANY_INDEX_KEY_COLS);
         */
        [CtCommentImpl]/* See if the index already exists in this schema.
        NOTE: We still need to check at execution time
        since the index name is only unique to the schema,
        not the table.
         */
        [CtCommentImpl]// if (dd.getConglomerateDescriptor(indexName.getTableName(), sd, false) != null)
        [CtCommentImpl]// {
        [CtCommentImpl]// throw StandardException.newException(SQLState.LANG_OBJECT_ALREADY_EXISTS_IN_OBJECT,
        [CtCommentImpl]// "Index",
        [CtCommentImpl]// indexName.getTableName(),
        [CtCommentImpl]// "schema",
        [CtCommentImpl]// sd.getSchemaName());
        [CtCommentImpl]// }
        [CtCommentImpl]/* Statement is dependent on the TableDescriptor */
        [CtInvocationImpl]getCompilerContext().createDependency([CtFieldReadImpl]td);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the node references SESSION schema tables (temporary or permanent)
     *
     * @return true if references SESSION schema tables, else false
     * @exception StandardException		Thrown
     * 		on error
     */
    public [CtTypeReferenceImpl]boolean referencesSessionSchema() throws [CtTypeReferenceImpl]com.splicemachine.db.iapi.error.StandardException [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// If create index is on a SESSION schema table, then return true.
        return [CtInvocationImpl]isSessionSchema([CtInvocationImpl][CtFieldReadImpl]td.getSchemaName());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return true if the node references temporary tables no matter under which schema
     *
     * @return true if references temporary tables, else false
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean referencesTemporaryTable() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]td.isTemporary();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Create the Constant information that will drive the guts of Execution.
     *
     * @exception StandardException		Thrown
     * 		on failure
     */
    public [CtTypeReferenceImpl]com.splicemachine.db.iapi.sql.execute.ConstantAction makeConstantAction() throws [CtTypeReferenceImpl]com.splicemachine.db.iapi.error.StandardException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.splicemachine.db.iapi.sql.dictionary.SchemaDescriptor sd = [CtInvocationImpl]getSchemaDescriptor();
        [CtLocalVariableImpl][CtCommentImpl]// int columnCount = columnNames.length;
        [CtTypeReferenceImpl]int approxLength = [CtLiteralImpl]0;
        [CtForEachImpl][CtCommentImpl]// boolean index_has_long_column = false;
        [CtCommentImpl]// bump the page size for the index,
        [CtCommentImpl]// if the approximate sizes of the columns in the key are
        [CtCommentImpl]// greater than the bump threshold.
        [CtCommentImpl]// Ideally, we would want to have atleast 2 or 3 keys fit in one page
        [CtCommentImpl]// With fix for beetle 5728, indexes on long types is not allowed
        [CtCommentImpl]// so we do not have to consider key columns of long types
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String columnName : [CtFieldReadImpl]columnNames) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.splicemachine.db.iapi.sql.dictionary.ColumnDescriptor columnDescriptor = [CtInvocationImpl][CtFieldReadImpl]td.getColumnDescriptor([CtVariableReadImpl]columnName);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.splicemachine.db.iapi.types.DataTypeDescriptor dts = [CtInvocationImpl][CtVariableReadImpl]columnDescriptor.getType();
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]approxLength += [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dts.getTypeId().getApproximateLengthInBytes([CtVariableReadImpl]dts);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]approxLength > [CtFieldReadImpl]com.splicemachine.db.iapi.reference.Property.IDX_PAGE_SIZE_BUMP_THRESHOLD) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]properties == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]properties.get([CtTypeAccessImpl]Property.PAGE_SIZE_PARAMETER) == [CtLiteralImpl]null)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]com.splicemachine.db.iapi.services.property.PropertyUtil.getServiceProperty([CtInvocationImpl][CtInvocationImpl]getLanguageConnectionContext().getTransactionCompile(), [CtTypeAccessImpl]Property.PAGE_SIZE_PARAMETER) == [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// do not override the user's choice of page size, whether it
                [CtCommentImpl]// is set for the whole database or just set on this statement.
                if ([CtBinaryOperatorImpl][CtFieldReadImpl]properties == [CtLiteralImpl]null)[CtBlockImpl]
                    [CtAssignmentImpl][CtFieldWriteImpl]properties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();

                [CtInvocationImpl][CtFieldReadImpl]properties.put([CtTypeAccessImpl]Property.PAGE_SIZE_PARAMETER, [CtTypeAccessImpl]Property.PAGE_SIZE_DEFAULT_LONG);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtCommentImpl]// not for CREATE TABLE
        [CtCommentImpl]// UniqueWithDuplicateNulls Index is a unique
        [CtCommentImpl]// index but with no "not null" constraint
        [CtCommentImpl]// on column in schema
        [CtInvocationImpl]getGenericConstantActionFactory().getCreateIndexConstantAction([CtLiteralImpl]false, [CtFieldReadImpl]unique, [CtFieldReadImpl]uniqueWithDuplicateNulls, [CtFieldReadImpl]indexType, [CtInvocationImpl][CtVariableReadImpl]sd.getSchemaName(), [CtInvocationImpl][CtFieldReadImpl]indexName.getTableName(), [CtInvocationImpl][CtFieldReadImpl]tableName.getTableName(), [CtInvocationImpl][CtFieldReadImpl]td.getUUID(), [CtFieldReadImpl]columnNames, [CtFieldReadImpl]isAscending, [CtLiteralImpl]false, [CtLiteralImpl]null, [CtFieldReadImpl]excludeNulls, [CtFieldReadImpl]excludeDefaults, [CtFieldReadImpl]preSplit, [CtFieldReadImpl]isLogicalKey, [CtFieldReadImpl]sampling, [CtFieldReadImpl]sampleFraction, [CtFieldReadImpl]splitKeyPath, [CtFieldReadImpl]hfilePath, [CtFieldReadImpl]columnDelimiter, [CtFieldReadImpl]characterDelimiter, [CtFieldReadImpl]timestampFormat, [CtFieldReadImpl]dateFormat, [CtFieldReadImpl]timeFormat, [CtFieldReadImpl]properties);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Check the uniqueness of the column names within the derived column list.
     *
     * @exception StandardException	Thrown
     * 		if column list contains a
     * 		duplicate name.
     */
    private [CtTypeReferenceImpl]void verifyAndGetUniqueNames() throws [CtTypeReferenceImpl]com.splicemachine.db.iapi.error.StandardException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtFieldReadImpl]columnNameList.size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Hashtable ht = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Hashtable([CtBinaryOperatorImpl][CtVariableReadImpl]size + [CtLiteralImpl]2, [CtLiteralImpl](([CtTypeReferenceImpl]float) (0.999)));
        [CtAssignmentImpl][CtFieldWriteImpl]columnNames = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtVariableReadImpl]size];
        [CtAssignmentImpl][CtFieldWriteImpl]isAscending = [CtNewArrayImpl]new [CtTypeReferenceImpl]boolean[[CtVariableReadImpl]size];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]index < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]index++) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]/* Verify that this column's name is unique within the list
            Having a space at the end meaning descending on the column
             */
            [CtArrayWriteImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index] = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtFieldReadImpl]columnNameList.get([CtVariableReadImpl]index)));
            [CtIfImpl]if ([CtInvocationImpl][CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index].endsWith([CtLiteralImpl]" ")) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index] = [CtInvocationImpl][CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index].substring([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index].length() - [CtLiteralImpl]1);
                [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]isAscending[[CtVariableReadImpl]index] = [CtLiteralImpl]false;
            } else[CtBlockImpl]
                [CtAssignmentImpl][CtArrayWriteImpl][CtFieldReadImpl]isAscending[[CtVariableReadImpl]index] = [CtLiteralImpl]true;

            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl][CtVariableReadImpl]ht.put([CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index], [CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index]);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]object != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]java.lang.String) (object)).equals([CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index])) [CtBlockImpl]{
                [CtThrowImpl]throw [CtInvocationImpl][CtTypeAccessImpl]com.splicemachine.db.iapi.error.StandardException.newException([CtTypeAccessImpl]SQLState.LANG_DUPLICATE_COLUMN_NAME_CREATE_INDEX, [CtArrayReadImpl][CtFieldReadImpl]columnNames[[CtVariableReadImpl]index]);
            }
        }
    }
}
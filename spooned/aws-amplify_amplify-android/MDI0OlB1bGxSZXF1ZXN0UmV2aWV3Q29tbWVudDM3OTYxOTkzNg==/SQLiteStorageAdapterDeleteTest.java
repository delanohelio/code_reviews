[CompilationUnitImpl][CtCommentImpl]/* Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

 http://aws.amazon.com/apache2.0

or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
 */
[CtPackageDeclarationImpl]package com.amplifyframework.datastore.storage.sqlite;
[CtUnresolvedImport]import com.amplifyframework.datastore.DataStoreException;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import com.amplifyframework.testmodels.commentsblog.Blog;
[CtUnresolvedImport]import com.amplifyframework.testmodels.commentsblog.BlogOwner;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import com.amplifyframework.core.model.query.predicate.QueryPredicate;
[CtClassImpl][CtJavaDocImpl]/**
 * Test the delete functionality of {@link SQLiteStorageAdapter} operations.
 */
public final class SQLiteStorageAdapterDeleteTest extends [CtTypeReferenceImpl]com.amplifyframework.datastore.storage.sqlite.StorageAdapterInstrumentedTestBase {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Assert that delete deletes item in the SQLite database correctly.
     *
     * @throws DataStoreException
     * 		On unexpected failure manipulating items in/out of DataStore
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteModelDeletesData() throws [CtTypeReferenceImpl]com.amplifyframework.datastore.DataStoreException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Triggers an insert
        final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner raphael = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.builder().name([CtLiteralImpl]"Raphael Kim").build();
        [CtInvocationImpl]saveModel([CtVariableReadImpl]raphael);
        [CtInvocationImpl][CtCommentImpl]// Triggers a delete
        deleteModel([CtVariableReadImpl]raphael);
        [CtLocalVariableImpl][CtCommentImpl]// Get the BlogOwner record from the database
        final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner> blogOwners = [CtInvocationImpl]queryModel([CtFieldReadImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.class);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]blogOwners.isEmpty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Assert that delete deletes item in the SQLite database without
     * violating foreign key constraints.
     *
     * @throws DataStoreException
     * 		On unexpected failure manipulating items in/out of DataStore
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteModelCascades() throws [CtTypeReferenceImpl]com.amplifyframework.datastore.DataStoreException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Triggers an insert
        final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner raphael = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.builder().name([CtLiteralImpl]"Raphael Kim").build();
        [CtInvocationImpl]saveModel([CtVariableReadImpl]raphael);
        [CtLocalVariableImpl][CtCommentImpl]// Triggers a foreign key constraint check
        final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.Blog raphaelsBlog = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.Blog.builder().name([CtLiteralImpl]"Raphael's Blog").owner([CtVariableReadImpl]raphael).build();
        [CtInvocationImpl]saveModel([CtVariableReadImpl]raphaelsBlog);
        [CtInvocationImpl][CtCommentImpl]// Triggers a delete
        [CtCommentImpl]// Deletes Raphael's Blog also to prevent foreign key violation
        deleteModel([CtVariableReadImpl]raphael);
        [CtLocalVariableImpl][CtCommentImpl]// Get the BlogOwner record from the database
        final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner> blogOwners = [CtInvocationImpl]queryModel([CtFieldReadImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.class);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]blogOwners.isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Get the Blog record from the database
        final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.Blog> blogs = [CtInvocationImpl]queryModel([CtFieldReadImpl]com.amplifyframework.testmodels.commentsblog.Blog.class);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]blogs.isEmpty());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Test delete with predicate. Conditional delete is useful for making sure that
     * no data is removed with outdated assumptions.
     *
     * @throws DataStoreException
     * 		On unexpected failure manipulating items in/out of DataStore
     */
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteModelWithPredicateDeletesConditionally() throws [CtTypeReferenceImpl]com.amplifyframework.datastore.DataStoreException [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner john = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.builder().name([CtLiteralImpl]"John").build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner jane = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.builder().name([CtLiteralImpl]"Jane").build();
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner mark = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.builder().name([CtLiteralImpl]"Mark").build();
        [CtInvocationImpl]saveModel([CtVariableReadImpl]john);
        [CtInvocationImpl]saveModel([CtVariableReadImpl]jane);
        [CtInvocationImpl]saveModel([CtVariableReadImpl]mark);
        [CtLocalVariableImpl][CtCommentImpl]// Delete everybody but Mark
        final [CtTypeReferenceImpl]com.amplifyframework.core.model.query.predicate.QueryPredicate predicate = [CtInvocationImpl][CtTypeAccessImpl]BlogOwner.NAME.ne([CtInvocationImpl][CtVariableReadImpl]mark.getName());
        [CtInvocationImpl]deleteModel([CtVariableReadImpl]john, [CtVariableReadImpl]predicate);
        [CtInvocationImpl]deleteModel([CtVariableReadImpl]jane, [CtVariableReadImpl]predicate);
        [CtInvocationImpl]deleteModelExpectingError([CtVariableReadImpl]mark, [CtVariableReadImpl]predicate);[CtCommentImpl]// Should not be deleted

        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner> blogOwners = [CtInvocationImpl]queryModel([CtFieldReadImpl]com.amplifyframework.testmodels.commentsblog.BlogOwner.class);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtVariableReadImpl]blogOwners.size());
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtVariableReadImpl]blogOwners.contains([CtVariableReadImpl]mark));
    }
}
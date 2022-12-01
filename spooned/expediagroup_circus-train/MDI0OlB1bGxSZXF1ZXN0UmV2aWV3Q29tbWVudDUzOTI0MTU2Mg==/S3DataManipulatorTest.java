[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (C) 2016-2020 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package com.hotels.bdp.circustrain.aws;
[CtUnresolvedImport]import com.amazonaws.services.s3.AmazonS3ClientBuilder;
[CtUnresolvedImport]import com.amazonaws.services.s3.model.AmazonS3Exception;
[CtUnresolvedImport]import static org.junit.Assert.*;
[CtUnresolvedImport]import org.gaul.s3proxy.junit.S3ProxyRule;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import com.amazonaws.client.builder.AwsClientBuilder;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.apache.hadoop.fs.s3a.BasicAWSCredentialsProvider;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtUnresolvedImport]import com.amazonaws.regions.Regions;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import org.mockito.runners.MockitoJUnitRunner;
[CtUnresolvedImport]import com.amazonaws.services.s3.AmazonS3;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import org.junit.rules.TemporaryFolder;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.mockito.runners.MockitoJUnitRunner.class)
public class S3DataManipulatorTest {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AWS_ACCESS_KEY = [CtLiteralImpl]"access";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AWS_SECRET_KEY = [CtLiteralImpl]"secret";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BUCKET = [CtLiteralImpl]"bucket";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BUCKET_PATH = [CtBinaryOperatorImpl][CtLiteralImpl]"s3://" + [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FOLDER = [CtLiteralImpl]"folder";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EMPTY_BUCKET = [CtLiteralImpl]"empty-bucket";

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder temp = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.junit.rules.TemporaryFolder();

    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.gaul.s3proxy.junit.S3ProxyRule s3Proxy = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.gaul.s3proxy.junit.S3ProxyRule.builder().withCredentials([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.AWS_ACCESS_KEY, [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.AWS_SECRET_KEY).build();

    [CtFieldImpl]private [CtTypeReferenceImpl]com.hotels.bdp.circustrain.aws.S3DataManipulator s3DataManipulator;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.amazonaws.services.s3.AmazonS3 s3Client;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]s3Client = [CtInvocationImpl]newClient();
        [CtAssignmentImpl][CtFieldWriteImpl]s3DataManipulator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]S3DataManipulator([CtFieldReadImpl]s3Client);
        [CtInvocationImpl][CtFieldReadImpl]s3Client.createBucket([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.File inputData = [CtInvocationImpl][CtFieldReadImpl]temp.newFile([CtLiteralImpl]"data");
        [CtInvocationImpl][CtFieldReadImpl]s3Client.putObject([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET, [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.FOLDER, [CtVariableReadImpl]inputData);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.amazonaws.services.s3.AmazonS3 newClient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration endpointConfiguration = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]s3Proxy.getUri().toString(), [CtInvocationImpl][CtTypeAccessImpl]Regions.DEFAULT_REGION.getName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazonaws.services.s3.AmazonS3 newClient = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.amazonaws.services.s3.AmazonS3ClientBuilder.standard().withCredentials([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.hadoop.fs.s3a.BasicAWSCredentialsProvider([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.AWS_ACCESS_KEY, [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.AWS_SECRET_KEY)).withEndpointConfiguration([CtVariableReadImpl]endpointConfiguration).build();
        [CtReturnImpl]return [CtVariableReadImpl]newClient;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteFolderSucceeds() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl]s3DataManipulator.delete([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET_PATH + [CtLiteralImpl]"/") + [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.FOLDER);
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result, [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteNonexistentFolderFails() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl]s3DataManipulator.delete([CtBinaryOperatorImpl][CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET_PATH + [CtLiteralImpl]"/nonexistent-folder");
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result, [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteBucketSucceeds() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl]s3DataManipulator.delete([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.BUCKET_PATH);
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result, [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]true));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deleteEmptyBucketFails() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]s3Client.createBucket([CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.EMPTY_BUCKET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl]s3DataManipulator.delete([CtBinaryOperatorImpl][CtLiteralImpl]"s3://" + [CtFieldReadImpl]com.hotels.bdp.circustrain.aws.S3DataManipulatorTest.EMPTY_BUCKET);
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result, [CtInvocationImpl]CoreMatchers.is([CtLiteralImpl]false));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]com.amazonaws.services.s3.model.AmazonS3Exception.class)
    public [CtTypeReferenceImpl]void deleteNonExistentBucketThrowsException() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]s3DataManipulator.delete([CtBinaryOperatorImpl][CtLiteralImpl]"s3://" + [CtLiteralImpl]"nonexistent-bucket");
    }
}
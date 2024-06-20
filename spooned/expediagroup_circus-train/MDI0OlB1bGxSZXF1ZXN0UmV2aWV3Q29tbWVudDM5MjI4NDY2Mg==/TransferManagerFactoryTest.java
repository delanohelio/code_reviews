[CompilationUnitImpl][CtPackageDeclarationImpl]package com.hotels.bdp.circustrain.s3s3copier.aws;
[CtUnresolvedImport]import com.amazonaws.services.s3.transfer.TransferManager;
[CtUnresolvedImport]import org.mockito.runners.MockitoJUnitRunner;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.mockito.Mock;
[CtUnresolvedImport]import static org.junit.Assert.assertThat;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import com.amazonaws.services.s3.AmazonS3;
[CtUnresolvedImport]import com.amazonaws.services.s3.transfer.TransferManagerConfiguration;
[CtUnresolvedImport]import com.hotels.bdp.circustrain.s3s3copier.S3S3CopierOptions;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.mockito.runners.MockitoJUnitRunner.class)
public class TransferManagerFactoryTest {
    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]com.amazonaws.services.s3.AmazonS3 mockClient;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Long MULTIPART_COPY_THRESHOLD_VALUE = [CtLiteralImpl]1L;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.Long MULTIPART_COPY_PART_SIZE = [CtLiteralImpl]1L;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldCreateDefaultTransferManagerClient() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hotels.bdp.circustrain.s3s3copier.S3S3CopierOptions s3Options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hotels.bdp.circustrain.s3s3copier.S3S3CopierOptions([CtNewClassImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>()[CtClassImpl] {
            [CtAnonymousExecutableImpl][CtBlockImpl]{
                [CtInvocationImpl]put([CtInvocationImpl][CtTypeAccessImpl]S3S3CopierOptions.Keys.MULTIPART_COPY_THRESHOLD.keyName(), [CtFieldReadImpl]MULTIPART_COPY_THRESHOLD_VALUE);
                [CtInvocationImpl]put([CtInvocationImpl][CtTypeAccessImpl]S3S3CopierOptions.Keys.MULTIPART_COPY_PART_SIZE.keyName(), [CtFieldReadImpl]MULTIPART_COPY_PART_SIZE);
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.hotels.bdp.circustrain.s3s3copier.aws.TransferManagerFactory factory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.hotels.bdp.circustrain.s3s3copier.aws.TransferManagerFactory();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazonaws.services.s3.transfer.TransferManager transferManager = [CtInvocationImpl][CtVariableReadImpl]factory.newInstance([CtFieldReadImpl]mockClient, [CtVariableReadImpl]s3Options);
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]transferManager.getAmazonS3Client(), [CtInvocationImpl]CoreMatchers.is([CtFieldReadImpl]mockClient));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.amazonaws.services.s3.transfer.TransferManagerConfiguration tmConfig = [CtInvocationImpl][CtVariableReadImpl]transferManager.getConfiguration();
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]tmConfig.getMultipartCopyPartSize(), [CtInvocationImpl]CoreMatchers.is([CtFieldReadImpl]MULTIPART_COPY_PART_SIZE));
        [CtInvocationImpl]Assert.assertThat([CtInvocationImpl][CtVariableReadImpl]tmConfig.getMultipartCopyThreshold(), [CtInvocationImpl]CoreMatchers.is([CtFieldReadImpl]MULTIPART_COPY_THRESHOLD_VALUE));
    }
}
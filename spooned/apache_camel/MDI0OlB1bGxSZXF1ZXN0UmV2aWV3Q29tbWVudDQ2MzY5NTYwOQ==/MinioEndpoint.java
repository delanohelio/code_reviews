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
[CtPackageDeclarationImpl]package org.apache.camel.component.minio;
[CtUnresolvedImport]import io.minio.MakeBucketArgs;
[CtUnresolvedImport]import org.apache.camel.spi.Metadata;
[CtUnresolvedImport]import io.minio.ObjectStat;
[CtUnresolvedImport]import org.apache.camel.Message;
[CtUnresolvedImport]import org.apache.camel.spi.UriPath;
[CtUnresolvedImport]import io.minio.errors.InvalidBucketNameException;
[CtUnresolvedImport]import org.apache.camel.Component;
[CtUnresolvedImport]import org.apache.camel.Exchange;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.camel.Consumer;
[CtUnresolvedImport]import org.apache.camel.ExtendedExchange;
[CtUnresolvedImport]import org.apache.camel.spi.UriEndpoint;
[CtUnresolvedImport]import org.apache.camel.Processor;
[CtUnresolvedImport]import org.apache.camel.spi.UriParam;
[CtUnresolvedImport]import org.apache.camel.support.SynchronizationAdapter;
[CtUnresolvedImport]import io.minio.MinioClient;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import org.apache.camel.ExchangePattern;
[CtImportImpl]import java.nio.charset.StandardCharsets;
[CtImportImpl]import java.nio.charset.Charset;
[CtUnresolvedImport]import org.apache.camel.Category;
[CtUnresolvedImport]import org.apache.camel.Producer;
[CtUnresolvedImport]import org.apache.camel.component.minio.client.MinioClientFactory;
[CtImportImpl]import java.io.*;
[CtUnresolvedImport]import io.minio.BucketExistsArgs;
[CtUnresolvedImport]import io.minio.StatObjectArgs;
[CtUnresolvedImport]import org.apache.camel.util.IOHelper;
[CtUnresolvedImport]import org.apache.camel.util.ObjectHelper;
[CtUnresolvedImport]import io.minio.SetBucketPolicyArgs;
[CtUnresolvedImport]import org.apache.camel.support.ScheduledPollEndpoint;
[CtClassImpl][CtJavaDocImpl]/**
 * Store and retrieve objects from Minio Storage Service using Minio SDK.
 */
[CtAnnotationImpl]@org.apache.camel.spi.UriEndpoint(firstVersion = [CtLiteralImpl]"3.5.0", scheme = [CtLiteralImpl]"minio", title = [CtLiteralImpl]"Minio Storage Service", syntax = [CtLiteralImpl]"minio://bucketName", category = [CtNewArrayImpl]{ [CtFieldReadImpl]org.apache.camel.Category.CLOUD, [CtFieldReadImpl]org.apache.camel.Category.FILE })
public class MinioEndpoint extends [CtTypeReferenceImpl]org.apache.camel.support.ScheduledPollEndpoint {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.class);

    [CtFieldImpl]private [CtTypeReferenceImpl]io.minio.MinioClient minioClient;

    [CtFieldImpl][CtAnnotationImpl]@org.apache.camel.spi.UriPath(description = [CtLiteralImpl]"Bucket name")
    [CtAnnotationImpl]@org.apache.camel.spi.Metadata(required = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]java.lang.String bucketName;[CtCommentImpl]// to support component docs


    [CtFieldImpl][CtAnnotationImpl]@org.apache.camel.spi.UriParam
    private [CtTypeReferenceImpl]org.apache.camel.component.minio.MinioConfiguration configuration;

    [CtFieldImpl][CtAnnotationImpl]@org.apache.camel.spi.UriParam(label = [CtLiteralImpl]"consumer", defaultValue = [CtLiteralImpl]"10")
    private [CtTypeReferenceImpl]int maxMessagesPerPoll = [CtLiteralImpl]10;

    [CtFieldImpl][CtAnnotationImpl]@org.apache.camel.spi.UriParam(label = [CtLiteralImpl]"consumer", defaultValue = [CtLiteralImpl]"60")
    private [CtTypeReferenceImpl]int maxConnections = [CtBinaryOperatorImpl][CtLiteralImpl]50 + [CtFieldReadImpl]maxMessagesPerPoll;

    [CtConstructorImpl]public MinioEndpoint([CtParameterImpl][CtTypeReferenceImpl]java.lang.String uri, [CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Component component, [CtParameterImpl][CtTypeReferenceImpl]MinioConfiguration configuration) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]uri, [CtVariableReadImpl]component);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configuration = [CtVariableReadImpl]configuration;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.camel.Consumer createConsumer([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Processor processor) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]MinioConsumer minioConsumer = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MinioConsumer([CtThisAccessImpl]this, [CtVariableReadImpl]processor);
        [CtInvocationImpl]configureConsumer([CtVariableReadImpl]minioConsumer);
        [CtInvocationImpl][CtVariableReadImpl]minioConsumer.setMaxMessagesPerPoll([CtFieldReadImpl]maxMessagesPerPoll);
        [CtReturnImpl]return [CtVariableReadImpl]minioConsumer;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.camel.Producer createProducer() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]MinioProducer([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void doStart() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.doStart();
        [CtAssignmentImpl][CtFieldWriteImpl]minioClient = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getMinioClient() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl]getConfiguration().getMinioClient() : [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.camel.component.minio.client.MinioClientFactory.getClient([CtInvocationImpl]getConfiguration()).getMinioClient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String objectName = [CtInvocationImpl][CtInvocationImpl]getConfiguration().getObjectName();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]objectName != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Object name {} requested, so skipping bucket check...", [CtVariableReadImpl]objectName);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bucketName = [CtInvocationImpl][CtInvocationImpl]getConfiguration().getBucketName();
        [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Querying whether bucket {} already exists...", [CtVariableReadImpl]bucketName);
        [CtIfImpl]if ([CtInvocationImpl]bucketExists([CtVariableReadImpl]bucketName)) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Bucket {} already exists", [CtVariableReadImpl]bucketName);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getConfiguration().isAutoCreateBucket()) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.minio.errors.InvalidBucketNameException([CtLiteralImpl]"Bucket {} does not exists, set autoCreateBucket option for bucket auto creation", [CtVariableReadImpl]bucketName);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"AutoCreateBucket set to true, Creating bucket {}...", [CtVariableReadImpl]bucketName);
            [CtInvocationImpl]makeBucket([CtVariableReadImpl]bucketName);
            [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Bucket created");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getPolicy() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]setBucketPolicy([CtVariableReadImpl]bucketName);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void doStop() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.apache.camel.util.ObjectHelper.isEmpty([CtInvocationImpl][CtInvocationImpl]getConfiguration().getMinioClient())) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]minioClient != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]minioClient = [CtLiteralImpl]null;
            }
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.doStop();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.camel.Exchange createExchange([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream minioObject, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String objectName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]createExchange([CtInvocationImpl]getExchangePattern(), [CtVariableReadImpl]minioObject, [CtVariableReadImpl]objectName);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.camel.Exchange createExchange([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.ExchangePattern pattern, [CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream minioObject, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String objectName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Getting object with objectName {} from bucket {}...", [CtVariableReadImpl]objectName, [CtInvocationImpl][CtInvocationImpl]getConfiguration().getBucketName());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange = [CtInvocationImpl][CtSuperAccessImpl]super.createExchange([CtVariableReadImpl]pattern);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.camel.Message message = [CtInvocationImpl][CtVariableReadImpl]exchange.getIn();
        [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Got object!");
        [CtInvocationImpl]getObjectStat([CtVariableReadImpl]objectName, [CtVariableReadImpl]message);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getConfiguration().isIncludeBody()) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]message.setBody([CtInvocationImpl]readInputStream([CtVariableReadImpl]minioObject));
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getConfiguration().isAutocloseBody()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]exchange.adapt([CtFieldReadImpl]org.apache.camel.ExtendedExchange.class).addOnCompletion([CtNewClassImpl]new [CtTypeReferenceImpl]org.apache.camel.support.SynchronizationAdapter()[CtClassImpl] {
                        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
                        public [CtTypeReferenceImpl]void onDone([CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Exchange exchange) [CtBlockImpl]{
                            [CtInvocationImpl][CtTypeAccessImpl]org.apache.camel.util.IOHelper.close([CtVariableReadImpl]minioObject);
                        }
                    });
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// TODO Auto-generated catch block
                [CtVariableReadImpl]e.printStackTrace();
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]message.setBody([CtLiteralImpl]null);
            [CtInvocationImpl][CtTypeAccessImpl]org.apache.camel.util.IOHelper.close([CtVariableReadImpl]minioObject);
        }
        [CtReturnImpl]return [CtVariableReadImpl]exchange;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.camel.component.minio.MinioConfiguration getConfiguration() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]configuration;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setConfiguration([CtParameterImpl][CtTypeReferenceImpl]MinioConfiguration configuration) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.configuration = [CtVariableReadImpl]configuration;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.minio.MinioClient getMinioClient() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]minioClient;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMinioClient([CtParameterImpl][CtTypeReferenceImpl]io.minio.MinioClient minioClient) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.minioClient = [CtVariableReadImpl]minioClient;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMaxMessagesPerPoll() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxMessagesPerPoll;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the maximum number of messages as a limit to poll at each polling.
     * <p/>
     * Gets the maximum number of messages as a limit to poll at each polling.
     * The default value is 10. Use 0 or a negative number to set it as
     * unlimited.
     */
    public [CtTypeReferenceImpl]void setMaxMessagesPerPoll([CtParameterImpl][CtTypeReferenceImpl]int maxMessagesPerPoll) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxMessagesPerPoll = [CtVariableReadImpl]maxMessagesPerPoll;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMaxConnections() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]maxConnections;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the maxConnections parameter in the minio client configuration
     */
    public [CtTypeReferenceImpl]void setMaxConnections([CtParameterImpl][CtTypeReferenceImpl]int maxConnections) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.maxConnections = [CtVariableReadImpl]maxConnections;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String readInputStream([CtParameterImpl][CtTypeReferenceImpl]java.io.InputStream minioObject) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder textBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.Reader reader = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.BufferedReader([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtVariableReadImpl]minioObject, [CtInvocationImpl][CtTypeAccessImpl]java.nio.charset.Charset.forName([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_8.name())))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int c;
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtAssignmentImpl]([CtVariableWriteImpl]c = [CtInvocationImpl][CtVariableReadImpl]reader.read()) != [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]textBuilder.append([CtVariableReadImpl](([CtTypeReferenceImpl]char) (c)));
            } 
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]textBuilder.toString();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean bucketExists([CtParameterImpl][CtTypeReferenceImpl]java.lang.String bucketName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]minioClient.bucketExists([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.minio.BucketExistsArgs.builder().bucket([CtVariableReadImpl]bucketName).build());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void makeBucket([CtParameterImpl][CtTypeReferenceImpl]java.lang.String bucketName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.minio.MakeBucketArgs.Builder makeBucketRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.minio.MakeBucketArgs.builder().bucket([CtVariableReadImpl]bucketName).objectLock([CtInvocationImpl][CtInvocationImpl]getConfiguration().isObjectLock());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getRegion() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]makeBucketRequest.region([CtInvocationImpl][CtInvocationImpl]getConfiguration().getRegion());
        }
        [CtInvocationImpl][CtFieldReadImpl]minioClient.makeBucket([CtInvocationImpl][CtVariableReadImpl]makeBucketRequest.build());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setBucketPolicy([CtParameterImpl][CtTypeReferenceImpl]java.lang.String bucketName) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Updating bucket {} with policy...", [CtVariableReadImpl]bucketName);
        [CtInvocationImpl][CtFieldReadImpl]minioClient.setBucketPolicy([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.minio.SetBucketPolicyArgs.builder().bucket([CtVariableReadImpl]bucketName).config([CtInvocationImpl][CtInvocationImpl]getConfiguration().getPolicy()).build());
        [CtInvocationImpl][CtFieldReadImpl]org.apache.camel.component.minio.MinioEndpoint.LOG.trace([CtLiteralImpl]"Bucket policy updated");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void getObjectStat([CtParameterImpl][CtTypeReferenceImpl]java.lang.String objectName, [CtParameterImpl][CtTypeReferenceImpl]org.apache.camel.Message message) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bucketName = [CtInvocationImpl][CtInvocationImpl]getConfiguration().getBucketName();
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]io.minio.StatObjectArgs.Builder statObjectRequest = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.minio.StatObjectArgs.builder().bucket([CtVariableReadImpl]bucketName).object([CtVariableReadImpl]objectName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getServerSideEncryptionCustomerKey() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.ssec([CtInvocationImpl][CtInvocationImpl]getConfiguration().getServerSideEncryptionCustomerKey());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getOffset() != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.offset([CtInvocationImpl][CtInvocationImpl]getConfiguration().getOffset());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getLength() != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.length([CtInvocationImpl][CtInvocationImpl]getConfiguration().getLength());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getVersionId() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.versionId([CtInvocationImpl][CtInvocationImpl]getConfiguration().getVersionId());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getMatchETag() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.matchETag([CtInvocationImpl][CtInvocationImpl]getConfiguration().getMatchETag());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getNotMatchETag() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.notMatchETag([CtInvocationImpl][CtInvocationImpl]getConfiguration().getNotMatchETag());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getModifiedSince() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.modifiedSince([CtInvocationImpl][CtInvocationImpl]getConfiguration().getModifiedSince());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getConfiguration().getUnModifiedSince() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]statObjectRequest.unmodifiedSince([CtInvocationImpl][CtInvocationImpl]getConfiguration().getUnModifiedSince());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.minio.ObjectStat stat = [CtInvocationImpl][CtFieldReadImpl]minioClient.statObject([CtInvocationImpl][CtVariableReadImpl]statObjectRequest.build());
        [CtInvocationImpl][CtCommentImpl]// set all stat as message headers
        [CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.OBJECT_NAME, [CtInvocationImpl][CtVariableReadImpl]stat.name());
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.BUCKET_NAME, [CtInvocationImpl][CtVariableReadImpl]stat.bucketName());
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.E_TAG, [CtInvocationImpl][CtVariableReadImpl]stat.etag());
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.LAST_MODIFIED, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"last-modified"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.VERSION_ID, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"x-amz-version-id"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.CONTENT_TYPE, [CtInvocationImpl][CtVariableReadImpl]stat.contentType());
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.CONTENT_LENGTH, [CtInvocationImpl][CtVariableReadImpl]stat.length());
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.CONTENT_ENCODING, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"content-encoding"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.CONTENT_DISPOSITION, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"content-disposition"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.CACHE_CONTROL, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"cache-control"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.SERVER_SIDE_ENCRYPTION, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"x-amz-server-side-encryption"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.EXPIRATION_TIME, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"x-amz-expiration"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.REPLICATION_STATUS, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"x-amz-replication-status"));
        [CtInvocationImpl][CtVariableReadImpl]message.setHeader([CtTypeAccessImpl]MinioConstants.STORAGE_CLASS, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stat.httpHeaders().get([CtLiteralImpl]"x-amz-storage-class"));
    }
}
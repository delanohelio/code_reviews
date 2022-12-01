[CompilationUnitImpl][CtCommentImpl]/* Copyright 1999-2019 Seata.io Group.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
[CtPackageDeclarationImpl]package io.seata.spring.boot.autoconfigure.properties;
[CtUnresolvedImport]import org.springframework.boot.context.properties.EnableConfigurationProperties;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.springframework.boot.context.properties.ConfigurationProperties;
[CtUnresolvedImport]import static io.seata.spring.boot.autoconfigure.StarterConstants.SEATA_PREFIX;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author xingfudeshi@gmail.com
 */
[CtAnnotationImpl]@org.springframework.boot.context.properties.ConfigurationProperties(prefix = [CtFieldReadImpl]io.seata.spring.boot.autoconfigure.StarterConstants.SEATA_PREFIX)
[CtAnnotationImpl]@org.springframework.boot.context.properties.EnableConfigurationProperties([CtFieldReadImpl]io.seata.spring.boot.autoconfigure.properties.SpringCloudAlibabaConfiguration.class)
public class SeataProperties {
    [CtFieldImpl][CtJavaDocImpl]/**
     * whether enable auto configuration
     */
    private [CtTypeReferenceImpl]boolean enabled = [CtLiteralImpl]true;

    [CtFieldImpl][CtJavaDocImpl]/**
     * application id
     */
    private [CtTypeReferenceImpl]java.lang.String applicationId;

    [CtFieldImpl][CtJavaDocImpl]/**
     * transaction service group
     */
    private [CtTypeReferenceImpl]java.lang.String txServiceGroup;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Whether enable auto proxying of datasource bean
     */
    private [CtTypeReferenceImpl]boolean enableAutoDataSourceProxy = [CtLiteralImpl]true;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Whether use JDK proxy instead of CGLIB proxy
     */
    private [CtTypeReferenceImpl]boolean useJdkProxy = [CtLiteralImpl]false;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Specifies which bean do not scanner in the GlobalTransactionScanner
     */
    private [CtArrayTypeReferenceImpl]java.lang.String[] excludesForScanner = [CtNewArrayImpl]new java.lang.String[]{  };

    [CtFieldImpl][CtJavaDocImpl]/**
     * Specifies which datasource bean are not eligible for auto-proxying
     */
    private [CtArrayTypeReferenceImpl]java.lang.String[] excludesForAutoProxying = [CtNewArrayImpl]new java.lang.String[]{  };

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    private [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SpringCloudAlibabaConfiguration springCloudAlibabaConfiguration;

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEnabled() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]enabled;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setEnabled([CtParameterImpl][CtTypeReferenceImpl]boolean enabled) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.enabled = [CtVariableReadImpl]enabled;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getApplicationId() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]applicationId == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]applicationId = [CtInvocationImpl][CtFieldReadImpl]springCloudAlibabaConfiguration.getApplicationId();
        }
        [CtReturnImpl]return [CtFieldReadImpl]applicationId;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setApplicationId([CtParameterImpl][CtTypeReferenceImpl]java.lang.String applicationId) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.applicationId = [CtVariableReadImpl]applicationId;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTxServiceGroup() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]txServiceGroup == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]txServiceGroup = [CtInvocationImpl][CtFieldReadImpl]springCloudAlibabaConfiguration.getTxServiceGroup();
        }
        [CtReturnImpl]return [CtFieldReadImpl]txServiceGroup;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setTxServiceGroup([CtParameterImpl][CtTypeReferenceImpl]java.lang.String txServiceGroup) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.txServiceGroup = [CtVariableReadImpl]txServiceGroup;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isEnableAutoDataSourceProxy() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]enableAutoDataSourceProxy;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setEnableAutoDataSourceProxy([CtParameterImpl][CtTypeReferenceImpl]boolean enableAutoDataSourceProxy) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.enableAutoDataSourceProxy = [CtVariableReadImpl]enableAutoDataSourceProxy;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isUseJdkProxy() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]useJdkProxy;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setUseJdkProxy([CtParameterImpl][CtTypeReferenceImpl]boolean useJdkProxy) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.useJdkProxy = [CtVariableReadImpl]useJdkProxy;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]java.lang.String[] getExcludesForAutoProxying() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]excludesForAutoProxying;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setExcludesForAutoProxying([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] excludesForAutoProxying) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludesForAutoProxying = [CtVariableReadImpl]excludesForAutoProxying;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtArrayTypeReferenceImpl]java.lang.String[] getExcludesForScanner() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]excludesForScanner;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]io.seata.spring.boot.autoconfigure.properties.SeataProperties setExcludesForScanner([CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] excludesForScanner) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.excludesForScanner = [CtVariableReadImpl]excludesForScanner;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }
}
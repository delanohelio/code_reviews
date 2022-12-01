[CompilationUnitImpl][CtJavaDocImpl]/**
 * *****************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2020 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2020 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 * *****************************************************************************
 */
[CtPackageDeclarationImpl]package org.opennms.core.text.encryptor;
[CtUnresolvedImport]import com.google.common.base.Strings;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import org.opennms.core.config.api.TextEncryptor;
[CtUnresolvedImport]import org.opennms.features.scv.api.SecureCredentialsVault;
[CtUnresolvedImport]import org.jasypt.util.password.StrongPasswordEncryptor;
[CtUnresolvedImport]import org.opennms.features.scv.api.Credentials;
[CtUnresolvedImport]import org.jasypt.util.text.AES256TextEncryptor;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl]public class TextEncryptorImpl implements [CtTypeReferenceImpl]org.opennms.core.config.api.TextEncryptor {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOG = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]org.opennms.core.text.encryptor.TextEncryptorImpl.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.opennms.features.scv.api.SecureCredentialsVault secureCredentialsVault;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.opennms.features.scv.api.Credentials> passwordsByAlias = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtConstructorImpl]public TextEncryptorImpl([CtParameterImpl][CtTypeReferenceImpl]org.opennms.features.scv.api.SecureCredentialsVault secureCredentialsVault) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.secureCredentialsVault = [CtVariableReadImpl]secureCredentialsVault;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String encrypt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String text) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jasypt.util.text.AES256TextEncryptor textEncryptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jasypt.util.text.AES256TextEncryptor();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl]getPasswordFromCredentials([CtVariableReadImpl]alias, [CtVariableReadImpl]key);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]password)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]textEncryptor.setPassword([CtVariableReadImpl]password);
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]textEncryptor.encrypt([CtVariableReadImpl]text);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.opennms.core.text.encryptor.TextEncryptorImpl.LOG.error([CtLiteralImpl]"Exception while encrypting {} with key {}", [CtVariableReadImpl]text, [CtVariableReadImpl]key, [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]text;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String decrypt([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String encrypted) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.jasypt.util.text.AES256TextEncryptor textEncryptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jasypt.util.text.AES256TextEncryptor();
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl]getPasswordFromCredentials([CtVariableReadImpl]alias, [CtVariableReadImpl]key);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]password)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]textEncryptor.setPassword([CtVariableReadImpl]password);
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]textEncryptor.decrypt([CtVariableReadImpl]encrypted);
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]org.opennms.core.text.encryptor.TextEncryptorImpl.LOG.error([CtLiteralImpl]"Exception while decrypting {} with key {}", [CtVariableReadImpl]encrypted, [CtVariableReadImpl]key, [CtVariableReadImpl]e);
        }
        [CtReturnImpl]return [CtVariableReadImpl]encrypted;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getPasswordFromCredentials([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opennms.features.scv.api.Credentials credentials = [CtInvocationImpl][CtFieldReadImpl]passwordsByAlias.get([CtVariableReadImpl]alias);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]credentials == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]credentials = [CtInvocationImpl][CtFieldReadImpl]secureCredentialsVault.getCredentials([CtVariableReadImpl]alias);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]credentials == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]generateAndStorePassword([CtVariableReadImpl]alias, [CtVariableReadImpl]key);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]passwordsByAlias.put([CtVariableReadImpl]alias, [CtVariableReadImpl]credentials);
            }
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]credentials.getPassword();
    }

    [CtMethodImpl][CtCommentImpl]// For encryption, create a new password in scv.
    private [CtTypeReferenceImpl]java.lang.String generateAndStorePassword([CtParameterImpl][CtTypeReferenceImpl]java.lang.String alias, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String key) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jasypt.util.password.StrongPasswordEncryptor passwordEncryptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.jasypt.util.password.StrongPasswordEncryptor();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtVariableReadImpl]passwordEncryptor.encryptPassword([CtVariableReadImpl]key);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opennms.features.scv.api.Credentials credentials = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opennms.features.scv.api.Credentials([CtVariableReadImpl]key, [CtVariableReadImpl]password);
        [CtInvocationImpl][CtFieldReadImpl]secureCredentialsVault.setCredentials([CtVariableReadImpl]alias, [CtVariableReadImpl]credentials);
        [CtInvocationImpl][CtFieldReadImpl]passwordsByAlias.put([CtVariableReadImpl]alias, [CtVariableReadImpl]credentials);
        [CtReturnImpl]return [CtVariableReadImpl]password;
    }
}
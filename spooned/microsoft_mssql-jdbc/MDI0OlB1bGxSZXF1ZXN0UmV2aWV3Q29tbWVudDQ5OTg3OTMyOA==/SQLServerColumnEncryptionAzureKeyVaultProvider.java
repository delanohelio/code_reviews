[CompilationUnitImpl][CtCommentImpl]/* Microsoft JDBC Driver for SQL Server Copyright(c) Microsoft Corporation All rights reserved. This program is made
available under the terms of the MIT License. See the LICENSE file in the project root for more information.
 */
[CtPackageDeclarationImpl]package com.microsoft.sqlserver.jdbc;
[CtImportImpl]import java.util.Locale;
[CtImportImpl]import static java.nio.charset.StandardCharsets.UTF_16LE;
[CtUnresolvedImport]import com.azure.core.http.HttpPipeline;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import java.net.URISyntaxException;
[CtImportImpl]import java.nio.ByteOrder;
[CtImportImpl]import java.security.NoSuchAlgorithmException;
[CtImportImpl]import java.util.Properties;
[CtImportImpl]import java.net.URI;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
[CtImportImpl]import java.util.concurrent.ConcurrentHashMap;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.KeyClient;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
[CtUnresolvedImport]import com.azure.identity.ManagedIdentityCredentialBuilder;
[CtImportImpl]import java.security.MessageDigest;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.SignResult;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.KeyClientBuilder;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.UnwrapResult;
[CtImportImpl]import java.text.MessageFormat;
[CtImportImpl]import java.io.IOException;
[CtImportImpl]import java.nio.ByteBuffer;
[CtImportImpl]import java.io.FileInputStream;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.models.KeyType;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.models.KeyVaultKey;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.VerifyResult;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.SignatureAlgorithm;
[CtImportImpl]import java.util.concurrent.ExecutorService;
[CtUnresolvedImport]import com.azure.core.credential.TokenCredential;
[CtImportImpl]import java.util.logging.Level;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.azure.security.keyvault.keys.cryptography.models.WrapResult;
[CtClassImpl][CtJavaDocImpl]/**
 * Provides implementation similar to certificate store provider. A CEK encrypted with certificate store provider should
 * be decryptable by this provider and vice versa.
 *
 * Envelope Format for the encrypted column encryption key version + keyPathLength + ciphertextLength + keyPath +
 * ciphertext + signature version: A single byte indicating the format version. keyPathLength: Length of the keyPath.
 * ciphertextLength: ciphertext length keyPath: keyPath used to encrypt the column encryption key. This is only used for
 * troubleshooting purposes and is not verified during decryption. ciphertext: Encrypted column encryption key
 * signature: Signature of the entire byte array. Signature is validated before decrypting the column encryption key.
 */
public class SQLServerColumnEncryptionAzureKeyVaultProvider extends [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionKeyStoreProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.logging.Logger akvLogger = [CtInvocationImpl][CtTypeAccessImpl]java.util.logging.Logger.getLogger([CtLiteralImpl]"com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider");

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int KEY_NAME_INDEX = [CtLiteralImpl]4;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]int KEY_URL_SPLIT_LENGTH_WITH_VERSION = [CtLiteralImpl]6;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_URL_DELIMITER = [CtLiteralImpl]"/";

    [CtFieldImpl]private [CtTypeReferenceImpl]com.azure.core.http.HttpPipeline keyVaultPipeline;

    [CtFieldImpl]private [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.KeyVaultTokenCredential keyVaultTokenCredential;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Column Encryption Key Store Provider string
     */
    [CtTypeReferenceImpl]java.lang.String name = [CtLiteralImpl]"AZURE_KEY_VAULT";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String MSSQL_JDBC_PROPERTIES = [CtLiteralImpl]"mssql-jdbc.properties";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String AKV_TRUSTED_ENDPOINTS_KEYWORD = [CtLiteralImpl]"AKVTrustedEndpoints";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String RSA_ENCRYPTION_ALGORITHM_WITH_OAEP_FOR_AKV = [CtLiteralImpl]"RSA-OAEP";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> akvTrustedEndpoints;

    [CtFieldImpl][CtJavaDocImpl]/**
     * Algorithm version
     */
    private final [CtArrayTypeReferenceImpl]byte[] firstVersion = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtLiteralImpl]0x1 };

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClient> cachedKeyClients = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient> cachedCryptographyClients = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.concurrent.ConcurrentHashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]com.azure.core.credential.TokenCredential credential;

    [CtAnonymousExecutableImpl]static [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.akvTrustedEndpoints = [CtInvocationImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.getTrustedEndpoints();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.name;
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider to authenticate to AAD using the client id and client
     * key. This is used by KeyVault client at runtime to authenticate to Azure Key Vault.
     *
     * @param clientId
     * 		Identifier of the client requesting the token.
     * @param clientKey
     * 		Secret key of the client requesting the token.
     * @throws SQLServerException
     * 		when an error occurs
     */
    public SQLServerColumnEncryptionAzureKeyVaultProvider([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientKey) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]clientId) || [CtInvocationImpl][CtVariableReadImpl]clientId.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Client ID" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]clientKey) || [CtInvocationImpl][CtVariableReadImpl]clientKey.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Client Key" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtAssignmentImpl][CtCommentImpl]// create a token credential with given client id and secret which internally identifies the tenant id.
        [CtFieldWriteImpl]keyVaultTokenCredential = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.KeyVaultTokenCredential([CtVariableReadImpl]clientId, [CtVariableReadImpl]clientKey);
        [CtAssignmentImpl][CtCommentImpl]// create the pipeline with the custom Key Vault credential
        [CtFieldWriteImpl]keyVaultPipeline = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.KeyVaultHttpPipelineBuilder().credential([CtFieldReadImpl]keyVaultTokenCredential).buildPipeline();
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider to authenticate to AAD. This is used by
     * KeyVaultClient at runtime to authenticate to Azure Key Vault.
     *
     * @throws SQLServerException
     * 		when an error occurs
     */
    SQLServerColumnEncryptionAzureKeyVaultProvider() throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtInvocationImpl]setCredential([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.identity.ManagedIdentityCredentialBuilder().build());
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider to authenticate to AAD. This is used by
     * KeyVaultClient at runtime to authenticate to Azure Key Vault.
     *
     * @param clientId
     * 		Identifier of the client requesting the token.
     * @throws SQLServerException
     * 		when an error occurs
     */
    SQLServerColumnEncryptionAzureKeyVaultProvider([CtParameterImpl][CtTypeReferenceImpl]java.lang.String clientId) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]clientId) || [CtInvocationImpl][CtVariableReadImpl]clientId.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Client ID" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtInvocationImpl]setCredential([CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.identity.ManagedIdentityCredentialBuilder().clientId([CtVariableReadImpl]clientId).build());
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider using the provided TokenCredential to authenticate to
     * AAD. This is used by KeyVaultClient at runtime to authenticate to Azure Key Vault.
     *
     * @param tokenCredential
     * 		The TokenCredential to use to authenticate to Azure Key Vault.
     * @throws SQLServerException
     * 		when an error occurs
     */
    public SQLServerColumnEncryptionAzureKeyVaultProvider([CtParameterImpl][CtTypeReferenceImpl]com.azure.core.credential.TokenCredential tokenCredential) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tokenCredential) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Token Credential" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtInvocationImpl]setCredential([CtVariableReadImpl]tokenCredential);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider with a callback function to authenticate to AAD and
     * an executor service.. This is used by KeyVaultClient at runtime to authenticate to Azure Key Vault.
     *
     * This constructor is present to maintain backwards compatibility with 6.0 version of the driver. Deprecated for
     * removal in next stable release.
     *
     * @param authenticationCallback
     * 		- Callback function used for authenticating to AAD.
     * @param executorService
     * 		- The ExecutorService, previously used to create the keyVaultClient, but not in use anymore. - This
     * 		parameter can be passed as 'null'
     * @throws SQLServerException
     * 		when an error occurs
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public SQLServerColumnEncryptionAzureKeyVaultProvider([CtParameterImpl][CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerKeyVaultAuthenticationCallback authenticationCallback, [CtParameterImpl][CtTypeReferenceImpl]java.util.concurrent.ExecutorService executorService) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtInvocationImpl]this([CtVariableReadImpl]authenticationCallback);
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a SQLServerColumnEncryptionAzureKeyVaultProvider with a callback function to authenticate to AAD. This
     * is used by KeyVaultClient at runtime to authenticate to Azure Key Vault.
     *
     * @param authenticationCallback
     * 		- Callback function used for authenticating to AAD.
     * @throws SQLServerException
     * 		when an error occurs
     */
    [CtAnnotationImpl]@java.lang.Deprecated
    public SQLServerColumnEncryptionAzureKeyVaultProvider([CtParameterImpl][CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerKeyVaultAuthenticationCallback authenticationCallback) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]authenticationCallback) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"SQLServerKeyVaultAuthenticationCallback" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtAssignmentImpl][CtFieldWriteImpl]keyVaultTokenCredential = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.KeyVaultTokenCredential([CtVariableReadImpl]authenticationCallback);
        [CtAssignmentImpl][CtFieldWriteImpl]keyVaultPipeline = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.KeyVaultHttpPipelineBuilder().credential([CtFieldReadImpl]keyVaultTokenCredential).buildPipeline();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the credential that will be used for authenticating requests to Key Vault service.
     *
     * @param credential
     * 		A credential of type {@link TokenCredential}.
     * @throws SQLServerException
     * 		If the credential is null.
     */
    private [CtTypeReferenceImpl]void setCredential([CtParameterImpl][CtTypeReferenceImpl]com.azure.core.credential.TokenCredential credential) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]credential) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullValue"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs1 = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Credential" };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs1), [CtLiteralImpl]null);
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.credential = [CtVariableReadImpl]credential;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Decrypts an encrypted CEK with RSA encryption algorithm using the asymmetric key specified by the key path
     *
     * @param masterKeyPath
     * 		- Complete path of an asymmetric key in AKV
     * @param encryptionAlgorithm
     * 		- Asymmetric Key Encryption Algorithm
     * @param encryptedColumnEncryptionKey
     * 		- Encrypted Column Encryption Key
     * @return Plain text column encryption key
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] decryptColumnEncryptionKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String encryptionAlgorithm, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] encryptedColumnEncryptionKey) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Validate the input parameters
        [CtThisAccessImpl]this.ValidateNonEmptyAKVPath([CtVariableReadImpl]masterKeyPath);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]encryptedColumnEncryptionKey) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullEncryptedColumnEncryptionKey"), [CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey.length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_EmptyEncryptedColumnEncryptionKey"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Validate encryptionAlgorithm
        [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm keyWrapAlgorithm = [CtInvocationImpl][CtThisAccessImpl]this.validateEncryptionAlgorithm([CtVariableReadImpl]encryptionAlgorithm);
        [CtLocalVariableImpl][CtCommentImpl]// Validate whether the key is RSA one or not and then get the key size
        [CtTypeReferenceImpl]int keySizeInBytes = [CtInvocationImpl]getAKVKeySize([CtVariableReadImpl]masterKeyPath);
        [CtIfImpl][CtCommentImpl]// Validate and decrypt the EncryptedColumnEncryptionKey
        [CtCommentImpl]// Format is
        [CtCommentImpl]// version + keyPathLength + ciphertextLength + keyPath + ciphertext + signature
        [CtCommentImpl]// 
        [CtCommentImpl]// keyPath is present in the encrypted column encryption key for identifying the original source of the
        [CtCommentImpl]// asymmetric key pair and
        [CtCommentImpl]// we will not validate it against the data contained in the CMK metadata (masterKeyPath).
        [CtCommentImpl]// Validate the version byte
        if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey[[CtLiteralImpl]0] != [CtArrayReadImpl][CtFieldReadImpl]firstVersion[[CtLiteralImpl]0]) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_InvalidEcryptionAlgorithmVersion"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%02X ", [CtArrayReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey[[CtLiteralImpl]0]), [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%02X ", [CtArrayReadImpl][CtFieldReadImpl]firstVersion[[CtLiteralImpl]0]) };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Get key path length
        [CtTypeReferenceImpl]int currentIndex = [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]firstVersion.length;
        [CtLocalVariableImpl][CtTypeReferenceImpl]short keyPathLength = [CtInvocationImpl]convertTwoBytesToShort([CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex);
        [CtOperatorAssignmentImpl][CtCommentImpl]// We just read 2 bytes
        [CtVariableWriteImpl]currentIndex += [CtLiteralImpl]2;
        [CtLocalVariableImpl][CtCommentImpl]// Get ciphertext length
        [CtTypeReferenceImpl]short cipherTextLength = [CtInvocationImpl]convertTwoBytesToShort([CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtLiteralImpl]2;
        [CtOperatorAssignmentImpl][CtCommentImpl]// Skip KeyPath
        [CtCommentImpl]// KeyPath exists only for troubleshooting purposes and doesnt need validation.
        [CtVariableWriteImpl]currentIndex += [CtVariableReadImpl]keyPathLength;
        [CtIfImpl][CtCommentImpl]// validate the ciphertext length
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]cipherTextLength != [CtVariableReadImpl]keySizeInBytes) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVKeyLengthError"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]cipherTextLength, [CtVariableReadImpl]keySizeInBytes, [CtVariableReadImpl]masterKeyPath };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Validate the signature length
        [CtTypeReferenceImpl]int signatureLength = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey.length - [CtVariableReadImpl]currentIndex) - [CtVariableReadImpl]cipherTextLength;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]signatureLength != [CtVariableReadImpl]keySizeInBytes) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVSignatureLengthError"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]signatureLength, [CtVariableReadImpl]keySizeInBytes, [CtVariableReadImpl]masterKeyPath };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Get ciphertext
        [CtArrayTypeReferenceImpl]byte[] cipherText = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtVariableReadImpl]cipherTextLength];
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtVariableReadImpl]cipherText, [CtLiteralImpl]0, [CtVariableReadImpl]cipherTextLength);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtVariableReadImpl]cipherTextLength;
        [CtLocalVariableImpl][CtCommentImpl]// Get signature
        [CtArrayTypeReferenceImpl]byte[] signature = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtVariableReadImpl]signatureLength];
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtVariableReadImpl]signature, [CtLiteralImpl]0, [CtVariableReadImpl]signatureLength);
        [CtLocalVariableImpl][CtCommentImpl]// Compute the hash to validate the signature
        [CtArrayTypeReferenceImpl]byte[] hash = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey.length - [CtFieldReadImpl][CtVariableReadImpl]signature.length];
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]encryptedColumnEncryptionKey, [CtLiteralImpl]0, [CtVariableReadImpl]hash, [CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey.length - [CtFieldReadImpl][CtVariableReadImpl]signature.length);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.MessageDigest md = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]md = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtLiteralImpl]"SHA-256");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NoSHA256Algorithm"), [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtVariableReadImpl]md.update([CtVariableReadImpl]hash);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte dataToVerify[] = [CtInvocationImpl][CtVariableReadImpl]md.digest();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]dataToVerify) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_HashNull"), [CtLiteralImpl]null);
        }
        [CtIfImpl][CtCommentImpl]// Validate the signature
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]AzureKeyVaultVerifySignature([CtVariableReadImpl]dataToVerify, [CtVariableReadImpl]signature, [CtVariableReadImpl]masterKeyPath)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_CEKSignatureNotMatchCMK"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]masterKeyPath };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Decrypt the CEK
        [CtArrayTypeReferenceImpl]byte[] decryptedCEK = [CtInvocationImpl][CtThisAccessImpl]this.AzureKeyVaultUnWrap([CtVariableReadImpl]masterKeyPath, [CtVariableReadImpl]keyWrapAlgorithm, [CtVariableReadImpl]cipherText);
        [CtReturnImpl]return [CtVariableReadImpl]decryptedCEK;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]short convertTwoBytesToShort([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] input, [CtParameterImpl][CtTypeReferenceImpl]int index) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]short shortVal;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]index + [CtLiteralImpl]1) >= [CtFieldReadImpl][CtVariableReadImpl]input.length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_ByteToShortConversion"), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.nio.ByteBuffer byteBuffer = [CtInvocationImpl][CtTypeAccessImpl]java.nio.ByteBuffer.allocate([CtLiteralImpl]2);
        [CtInvocationImpl][CtVariableReadImpl]byteBuffer.order([CtFieldReadImpl][CtTypeAccessImpl]java.nio.ByteOrder.[CtFieldReferenceImpl]LITTLE_ENDIAN);
        [CtInvocationImpl][CtVariableReadImpl]byteBuffer.put([CtArrayReadImpl][CtVariableReadImpl]input[[CtVariableReadImpl]index]);
        [CtInvocationImpl][CtVariableReadImpl]byteBuffer.put([CtArrayReadImpl][CtVariableReadImpl]input[[CtBinaryOperatorImpl][CtVariableReadImpl]index + [CtLiteralImpl]1]);
        [CtAssignmentImpl][CtVariableWriteImpl]shortVal = [CtInvocationImpl][CtVariableReadImpl]byteBuffer.getShort([CtLiteralImpl]0);
        [CtReturnImpl]return [CtVariableReadImpl]shortVal;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encrypts CEK with RSA encryption algorithm using the asymmetric key specified by the key path.
     *
     * @param masterKeyPath
     * 		- Complete path of an asymmetric key in AKV
     * @param encryptionAlgorithm
     * 		- Asymmetric Key Encryption Algorithm
     * @param columnEncryptionKey
     * 		- Plain text column encryption key
     * @return Encrypted column encryption key
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtArrayTypeReferenceImpl]byte[] encryptColumnEncryptionKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String encryptionAlgorithm, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] columnEncryptionKey) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Validate the input parameters
        [CtThisAccessImpl]this.ValidateNonEmptyAKVPath([CtVariableReadImpl]masterKeyPath);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]columnEncryptionKey) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullColumnEncryptionKey"), [CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtVariableReadImpl]columnEncryptionKey.length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_EmptyCEK"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Validate encryptionAlgorithm
        [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm keyWrapAlgorithm = [CtInvocationImpl][CtThisAccessImpl]this.validateEncryptionAlgorithm([CtVariableReadImpl]encryptionAlgorithm);
        [CtLocalVariableImpl][CtCommentImpl]// Validate whether the key is RSA one or not and then get the key size
        [CtTypeReferenceImpl]int keySizeInBytes = [CtInvocationImpl]getAKVKeySize([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtCommentImpl]// Construct the encryptedColumnEncryptionKey
        [CtCommentImpl]// Format is
        [CtCommentImpl]// version + keyPathLength + ciphertextLength + ciphertext + keyPath + signature
        [CtCommentImpl]// 
        [CtCommentImpl]// We currently only support one version
        [CtArrayTypeReferenceImpl]byte[] version = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[]{ [CtArrayReadImpl][CtFieldReadImpl]firstVersion[[CtLiteralImpl]0] };
        [CtLocalVariableImpl][CtCommentImpl]// Get the Unicode encoded bytes of cultureinvariant lower case masterKeyPath
        [CtArrayTypeReferenceImpl]byte[] masterKeyPathBytes = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]masterKeyPath.toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ENGLISH).getBytes([CtFieldReadImpl]java.nio.charset.StandardCharsets.UTF_16LE);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] keyPathLength = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtLiteralImpl]2];
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]keyPathLength[[CtLiteralImpl]0] = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]byte) ([CtFieldReadImpl](([CtTypeReferenceImpl]short) ([CtVariableReadImpl]masterKeyPathBytes.length)) & [CtLiteralImpl]0xff));
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]keyPathLength[[CtLiteralImpl]1] = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]byte) ([CtBinaryOperatorImpl]([CtFieldReadImpl](([CtTypeReferenceImpl]short) ([CtVariableReadImpl]masterKeyPathBytes.length)) >> [CtLiteralImpl]8) & [CtLiteralImpl]0xff));
        [CtLocalVariableImpl][CtCommentImpl]// Encrypt the plain text
        [CtArrayTypeReferenceImpl]byte[] cipherText = [CtInvocationImpl][CtThisAccessImpl]this.AzureKeyVaultWrap([CtVariableReadImpl]masterKeyPath, [CtVariableReadImpl]keyWrapAlgorithm, [CtVariableReadImpl]columnEncryptionKey);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] cipherTextLength = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtLiteralImpl]2];
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]cipherTextLength[[CtLiteralImpl]0] = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]byte) ([CtFieldReadImpl](([CtTypeReferenceImpl]short) ([CtVariableReadImpl]cipherText.length)) & [CtLiteralImpl]0xff));
        [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]cipherTextLength[[CtLiteralImpl]1] = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]byte) ([CtBinaryOperatorImpl]([CtFieldReadImpl](([CtTypeReferenceImpl]short) ([CtVariableReadImpl]cipherText.length)) >> [CtLiteralImpl]8) & [CtLiteralImpl]0xff));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]cipherText.length != [CtVariableReadImpl]keySizeInBytes) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_CipherTextLengthNotMatchRSASize"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Compute hash
        [CtCommentImpl]// SHA-2-256(version + keyPathLength + ciphertextLength + keyPath + ciphertext)
        [CtArrayTypeReferenceImpl]byte[] dataToHash = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]version.length + [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length) + [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length) + [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length) + [CtFieldReadImpl][CtVariableReadImpl]cipherText.length];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int destinationPosition = [CtFieldReadImpl][CtVariableReadImpl]version.length;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]version, [CtLiteralImpl]0, [CtVariableReadImpl]dataToHash, [CtLiteralImpl]0, [CtFieldReadImpl][CtVariableReadImpl]version.length);
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]keyPathLength, [CtLiteralImpl]0, [CtVariableReadImpl]dataToHash, [CtVariableReadImpl]destinationPosition, [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]destinationPosition += [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]cipherTextLength, [CtLiteralImpl]0, [CtVariableReadImpl]dataToHash, [CtVariableReadImpl]destinationPosition, [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]destinationPosition += [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]masterKeyPathBytes, [CtLiteralImpl]0, [CtVariableReadImpl]dataToHash, [CtVariableReadImpl]destinationPosition, [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]destinationPosition += [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]cipherText, [CtLiteralImpl]0, [CtVariableReadImpl]dataToHash, [CtVariableReadImpl]destinationPosition, [CtFieldReadImpl][CtVariableReadImpl]cipherText.length);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.MessageDigest md = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]md = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtLiteralImpl]"SHA-256");
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NoSHA256Algorithm"), [CtVariableReadImpl]e);
        }
        [CtInvocationImpl][CtVariableReadImpl]md.update([CtVariableReadImpl]dataToHash);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte dataToSign[] = [CtInvocationImpl][CtVariableReadImpl]md.digest();
        [CtLocalVariableImpl][CtCommentImpl]// Sign the hash
        [CtArrayTypeReferenceImpl]byte[] signedHash = [CtInvocationImpl]AzureKeyVaultSignHashedData([CtVariableReadImpl]dataToSign, [CtVariableReadImpl]masterKeyPath);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]signedHash.length != [CtVariableReadImpl]keySizeInBytes) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_SignedHashLengthError"), [CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtThisAccessImpl]this.AzureKeyVaultVerifySignature([CtVariableReadImpl]dataToSign, [CtVariableReadImpl]signedHash, [CtVariableReadImpl]masterKeyPath)) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_InvalidSignatureComputed"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Construct the encrypted column encryption key
        [CtCommentImpl]// EncryptedColumnEncryptionKey = version + keyPathLength + ciphertextLength + keyPath + ciphertext + signature
        [CtTypeReferenceImpl]int encryptedColumnEncryptionKeyLength = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]version.length + [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length) + [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length) + [CtFieldReadImpl][CtVariableReadImpl]cipherText.length) + [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length) + [CtFieldReadImpl][CtVariableReadImpl]signedHash.length;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] encryptedColumnEncryptionKey = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtVariableReadImpl]encryptedColumnEncryptionKeyLength];
        [CtLocalVariableImpl][CtCommentImpl]// Copy version byte
        [CtTypeReferenceImpl]int currentIndex = [CtLiteralImpl]0;
        [CtInvocationImpl][CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]version, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]version.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtFieldReadImpl][CtVariableReadImpl]version.length;
        [CtInvocationImpl][CtCommentImpl]// Copy key path length
        [CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]keyPathLength, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtFieldReadImpl][CtVariableReadImpl]keyPathLength.length;
        [CtInvocationImpl][CtCommentImpl]// Copy ciphertext length
        [CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]cipherTextLength, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtFieldReadImpl][CtVariableReadImpl]cipherTextLength.length;
        [CtInvocationImpl][CtCommentImpl]// Copy key path
        [CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]masterKeyPathBytes, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtFieldReadImpl][CtVariableReadImpl]masterKeyPathBytes.length;
        [CtInvocationImpl][CtCommentImpl]// Copy ciphertext
        [CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]cipherText, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]cipherText.length);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]currentIndex += [CtFieldReadImpl][CtVariableReadImpl]cipherText.length;
        [CtInvocationImpl][CtCommentImpl]// copy the signature
        [CtTypeAccessImpl]java.lang.System.arraycopy([CtVariableReadImpl]signedHash, [CtLiteralImpl]0, [CtVariableReadImpl]encryptedColumnEncryptionKey, [CtVariableReadImpl]currentIndex, [CtFieldReadImpl][CtVariableReadImpl]signedHash.length);
        [CtReturnImpl]return [CtVariableReadImpl]encryptedColumnEncryptionKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Validates that the encryption algorithm is RSA_OAEP and if it is not, then throws an exception.
     *
     * @param encryptionAlgorithm
     * 		- Asymmetric key encryptio algorithm
     * @return The encryption algorithm that is going to be used.
     * @throws SQLServerException
     */
    private [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm validateEncryptionAlgorithm([CtParameterImpl][CtTypeReferenceImpl]java.lang.String encryptionAlgorithm) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]encryptionAlgorithm) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NullKeyEncryptionAlgorithm"), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtIfImpl][CtCommentImpl]// Transform to standard format (dash instead of underscore) to support enum lookup
        if ([CtInvocationImpl][CtLiteralImpl]"RSA_OAEP".equalsIgnoreCase([CtVariableReadImpl]encryptionAlgorithm)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]encryptionAlgorithm = [CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.RSA_ENCRYPTION_ALGORITHM_WITH_OAEP_FOR_AKV;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.RSA_ENCRYPTION_ALGORITHM_WITH_OAEP_FOR_AKV.equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]encryptionAlgorithm.trim())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_InvalidKeyEncryptionAlgorithm"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]encryptionAlgorithm, [CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.RSA_ENCRYPTION_ALGORITHM_WITH_OAEP_FOR_AKV };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtThisAccessImpl]this, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm.fromString([CtVariableReadImpl]encryptionAlgorithm);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks if the Azure Key Vault key path is Empty or Null (and raises exception if they are).
     *
     * @param masterKeyPath
     * @throws SQLServerException
     */
    private [CtTypeReferenceImpl]void ValidateNonEmptyAKVPath([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// throw appropriate error if masterKeyPath is null or empty
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]masterKeyPath) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]masterKeyPath.trim().isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVPathNull"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]masterKeyPath };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URI parsedUri = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]parsedUri = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URI([CtVariableReadImpl]masterKeyPath);
                [CtLocalVariableImpl][CtCommentImpl]// A valid URI.
                [CtCommentImpl]// Check if it is pointing to a trusted endpoint.
                [CtTypeReferenceImpl]java.lang.String host = [CtInvocationImpl][CtVariableReadImpl]parsedUri.getHost();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]host) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]host = [CtInvocationImpl][CtVariableReadImpl]host.toLowerCase([CtFieldReadImpl][CtTypeAccessImpl]java.util.Locale.[CtFieldReferenceImpl]ENGLISH);
                }
                [CtForEachImpl]for ([CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String endpoint : [CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.akvTrustedEndpoints) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]host) && [CtInvocationImpl][CtVariableReadImpl]host.endsWith([CtVariableReadImpl]endpoint)) [CtBlockImpl]{
                        [CtReturnImpl]return;
                    }
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.net.URISyntaxException e) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVURLInvalid"));
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]masterKeyPath };
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtVariableReadImpl]e);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVMasterKeyPathInvalid"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtVariableReadImpl]masterKeyPath };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encrypts the text using specified Azure Key Vault key.
     *
     * @param masterKeyPath
     * 		- Azure Key Vault key url.
     * @param encryptionAlgorithm
     * 		- Encryption Algorithm.
     * @param columnEncryptionKey
     * 		- Plain text Column Encryption Key.
     * @return Returns an encrypted blob or throws an exception if there are any errors.
     * @throws SQLServerException
     */
    private [CtArrayTypeReferenceImpl]byte[] AzureKeyVaultWrap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath, [CtParameterImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm encryptionAlgorithm, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] columnEncryptionKey) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]columnEncryptionKey) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_CEKNull"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient cryptoClient = [CtInvocationImpl]getCryptographyClient([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.WrapResult wrappedKey = [CtInvocationImpl][CtVariableReadImpl]cryptoClient.wrapKey([CtTypeAccessImpl]KeyWrapAlgorithm.RSA_OAEP, [CtVariableReadImpl]columnEncryptionKey);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]wrappedKey.getEncryptedKey();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Encrypts the text using specified Azure Key Vault key.
     *
     * @param masterKeyPath
     * 		- Azure Key Vault key url.
     * @param encryptionAlgorithm
     * 		- Encrypted Column Encryption Key.
     * @param encryptedColumnEncryptionKey
     * 		- Encrypted Column Encryption Key.
     * @return Returns the decrypted plaintext Column Encryption Key or throws an exception if there are any errors.
     * @throws SQLServerException
     */
    private [CtArrayTypeReferenceImpl]byte[] AzureKeyVaultUnWrap([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath, [CtParameterImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm encryptionAlgorithm, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] encryptedColumnEncryptionKey) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]encryptedColumnEncryptionKey) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_EncryptedCEKNull"), [CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]0 == [CtFieldReadImpl][CtVariableReadImpl]encryptedColumnEncryptionKey.length) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_EmptyEncryptedCEK"), [CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient cryptoClient = [CtInvocationImpl]getCryptographyClient([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.UnwrapResult unwrappedKey = [CtInvocationImpl][CtVariableReadImpl]cryptoClient.unwrapKey([CtVariableReadImpl]encryptionAlgorithm, [CtVariableReadImpl]encryptedColumnEncryptionKey);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]unwrappedKey.getKey();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient getCryptographyClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.cachedCryptographyClients.containsKey([CtVariableReadImpl]masterKeyPath)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]cachedCryptographyClients.get([CtVariableReadImpl]masterKeyPath);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.models.KeyVaultKey retrievedKey = [CtInvocationImpl]getKeyVaultKey([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient cryptoClient;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]credential) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cryptoClient = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder().credential([CtFieldReadImpl]credential).keyIdentifier([CtInvocationImpl][CtVariableReadImpl]retrievedKey.getId()).buildClient();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cryptoClient = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder().pipeline([CtFieldReadImpl]keyVaultPipeline).keyIdentifier([CtInvocationImpl][CtVariableReadImpl]retrievedKey.getId()).buildClient();
        }
        [CtInvocationImpl][CtFieldReadImpl]cachedCryptographyClients.putIfAbsent([CtVariableReadImpl]masterKeyPath, [CtVariableReadImpl]cryptoClient);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]cachedCryptographyClients.get([CtVariableReadImpl]masterKeyPath);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generates signature based on RSA PKCS#v1.5 scheme using a specified Azure Key Vault Key URL.
     *
     * @param dataToSign
     * 		- Text to sign.
     * @param masterKeyPath
     * 		- Azure Key Vault key url.
     * @return Signature
     * @throws SQLServerException
     */
    private [CtArrayTypeReferenceImpl]byte[] AzureKeyVaultSignHashedData([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] dataToSign, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]dataToSign) && [CtBinaryOperatorImpl]([CtLiteralImpl]0 != [CtFieldReadImpl][CtVariableReadImpl]dataToSign.length);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient cryptoClient = [CtInvocationImpl]getCryptographyClient([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.SignResult signedData = [CtInvocationImpl][CtVariableReadImpl]cryptoClient.sign([CtTypeAccessImpl]SignatureAlgorithm.RS256, [CtVariableReadImpl]dataToSign);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]signedData.getSignature();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Verifies the given RSA PKCSv1.5 signature.
     *
     * @param dataToVerify
     * @param signature
     * @param masterKeyPath
     * 		- Azure Key Vault key url.
     * @return true if signature is valid, false if it is not valid
     * @throws SQLServerException
     */
    private [CtTypeReferenceImpl]boolean AzureKeyVaultVerifySignature([CtParameterImpl][CtArrayTypeReferenceImpl]byte[] dataToVerify, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] signature, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]dataToVerify) && [CtBinaryOperatorImpl]([CtLiteralImpl]0 != [CtFieldReadImpl][CtVariableReadImpl]dataToVerify.length);
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]signature) && [CtBinaryOperatorImpl]([CtLiteralImpl]0 != [CtFieldReadImpl][CtVariableReadImpl]signature.length);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.CryptographyClient cryptoClient = [CtInvocationImpl]getCryptographyClient([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.cryptography.models.VerifyResult valid = [CtInvocationImpl][CtVariableReadImpl]cryptoClient.verify([CtTypeAccessImpl]SignatureAlgorithm.RS256, [CtVariableReadImpl]dataToVerify, [CtVariableReadImpl]signature);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]valid.isValid();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the public Key size in bytes.
     *
     * @param masterKeyPath
     * 		- Azure Key Vault Key path
     * @return Key size in bytes
     * @throws SQLServerException
     * 		when an error occurs
     */
    private [CtTypeReferenceImpl]int getAKVKeySize([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.models.KeyVaultKey retrievedKey = [CtInvocationImpl]getKeyVaultKey([CtVariableReadImpl]masterKeyPath);
        [CtReturnImpl]return [CtFieldReadImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]retrievedKey.getKey().getN().length;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetches the key from Azure Key Vault for given key path. If the key path includes a version, then that
     * specific version of the key is retrieved, otherwise the latest key will be retrieved.
     *
     * @param masterKeyPath
     * 		The key path associated with the key
     * @return The Key Vault key.
     * @throws SQLServerException
     * 		If there was an error retrieving the key from Key Vault.
     */
    private [CtTypeReferenceImpl]com.azure.security.keyvault.keys.models.KeyVaultKey getKeyVaultKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] keyTokens = [CtInvocationImpl][CtVariableReadImpl]masterKeyPath.split([CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.KEY_URL_DELIMITER);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyName = [CtArrayReadImpl][CtVariableReadImpl]keyTokens[[CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.KEY_NAME_INDEX];
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyVersion = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]keyTokens.length == [CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.KEY_URL_SPLIT_LENGTH_WITH_VERSION) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyVersion = [CtArrayReadImpl][CtVariableReadImpl]keyTokens[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]keyTokens.length - [CtLiteralImpl]1];
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClient keyClient = [CtInvocationImpl]getKeyClient([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.models.KeyVaultKey retrievedKey;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]keyVersion) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]retrievedKey = [CtInvocationImpl][CtVariableReadImpl]keyClient.getKey([CtVariableReadImpl]keyName, [CtVariableReadImpl]keyVersion);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]retrievedKey = [CtInvocationImpl][CtVariableReadImpl]keyClient.getKey([CtVariableReadImpl]keyName);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]retrievedKey) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_AKVKeyNotFound"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtArrayReadImpl][CtVariableReadImpl]keyTokens[[CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]keyTokens.length - [CtLiteralImpl]1] };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]retrievedKey.getKeyType() != [CtFieldReadImpl]com.azure.security.keyvault.keys.models.KeyType.RSA) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]retrievedKey.getKeyType() != [CtFieldReadImpl]com.azure.security.keyvault.keys.models.KeyType.RSA_HSM)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.text.MessageFormat form = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.MessageFormat([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NonRSAKey"));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] msgArgs = [CtNewArrayImpl]new java.lang.Object[]{ [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]retrievedKey.getKeyType().toString() };
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtLiteralImpl]null, [CtInvocationImpl][CtVariableReadImpl]form.format([CtVariableReadImpl]msgArgs), [CtLiteralImpl]null, [CtLiteralImpl]0, [CtLiteralImpl]false);
        }
        [CtReturnImpl]return [CtVariableReadImpl]retrievedKey;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Creates a new {@link KeyClient} if one does not exist for the given key path. If the client already exists,
     * the client is returned from the cache. As the client is stateless, it's safe to cache the client for each key
     * path.
     *
     * @param masterKeyPath
     * 		The key path for which the {@link KeyClient} will be created, if it does not exist.
     * @return The {@link KeyClient} associated with the key path.
     */
    private [CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClient getKeyClient([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]cachedKeyClients.containsKey([CtVariableReadImpl]masterKeyPath)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]cachedKeyClients.get([CtVariableReadImpl]masterKeyPath);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String vaultUrl = [CtInvocationImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.getVaultUrl([CtVariableReadImpl]masterKeyPath);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClient keyClient;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtFieldReadImpl]credential) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyClient = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClientBuilder().credential([CtFieldReadImpl]credential).vaultUrl([CtVariableReadImpl]vaultUrl).buildClient();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyClient = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]com.azure.security.keyvault.keys.KeyClientBuilder().pipeline([CtFieldReadImpl]keyVaultPipeline).vaultUrl([CtVariableReadImpl]vaultUrl).buildClient();
        }
        [CtInvocationImpl][CtFieldReadImpl]cachedKeyClients.putIfAbsent([CtVariableReadImpl]masterKeyPath, [CtVariableReadImpl]keyClient);
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]cachedKeyClients.get([CtVariableReadImpl]masterKeyPath);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the vault url extracted from the master key path.
     *
     * @param masterKeyPath
     * 		The master key path.
     * @return The vault url.
     */
    private static [CtTypeReferenceImpl]java.lang.String getVaultUrl([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] keyTokens = [CtInvocationImpl][CtVariableReadImpl]masterKeyPath.split([CtLiteralImpl]"/");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String hostName = [CtArrayReadImpl][CtVariableReadImpl]keyTokens[[CtLiteralImpl]2];
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtLiteralImpl]"https://" + [CtVariableReadImpl]hostName;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean verifyColumnMasterKeyMetadata([CtParameterImpl][CtTypeReferenceImpl]java.lang.String masterKeyPath, [CtParameterImpl][CtTypeReferenceImpl]boolean allowEnclaveComputations, [CtParameterImpl][CtArrayTypeReferenceImpl]byte[] signature) throws [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]allowEnclaveComputations) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.KeyStoreProviderCommon.validateNonEmptyMasterKeyPath([CtVariableReadImpl]masterKeyPath);
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.MessageDigest md = [CtInvocationImpl][CtTypeAccessImpl]java.security.MessageDigest.getInstance([CtLiteralImpl]"SHA-256");
            [CtInvocationImpl][CtVariableReadImpl]md.update([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]name.toLowerCase().getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_16LE));
            [CtInvocationImpl][CtVariableReadImpl]md.update([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]masterKeyPath.toLowerCase().getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_16LE));
            [CtInvocationImpl][CtCommentImpl]// value of allowEnclaveComputations is always true here
            [CtVariableReadImpl]md.update([CtInvocationImpl][CtLiteralImpl]"true".getBytes([CtFieldReadImpl][CtTypeAccessImpl]java.nio.charset.StandardCharsets.[CtFieldReferenceImpl]UTF_16LE));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] dataToVerify = [CtInvocationImpl][CtVariableReadImpl]md.digest();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]dataToVerify) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_HashNull"), [CtLiteralImpl]null);
            }
            [CtLocalVariableImpl][CtCommentImpl]// Sign the hash
            [CtArrayTypeReferenceImpl]byte[] signedHash = [CtInvocationImpl]AzureKeyVaultSignHashedData([CtVariableReadImpl]dataToVerify, [CtVariableReadImpl]masterKeyPath);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]signedHash) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_SignedHashLengthError"), [CtLiteralImpl]null);
            }
            [CtReturnImpl][CtCommentImpl]// Validate the signature
            return [CtInvocationImpl]AzureKeyVaultVerifySignature([CtVariableReadImpl]dataToVerify, [CtVariableReadImpl]signature, [CtVariableReadImpl]masterKeyPath);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.security.NoSuchAlgorithmException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.microsoft.sqlserver.jdbc.SQLServerException([CtInvocationImpl][CtTypeAccessImpl]com.microsoft.sqlserver.jdbc.SQLServerException.getErrString([CtLiteralImpl]"R_NoSHA256Algorithm"), [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getTrustedEndpoints() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties mssqlJdbcProperties = [CtInvocationImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.getMssqlJdbcProperties();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> trustedEndpoints = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean append = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]mssqlJdbcProperties) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String endpoints = [CtInvocationImpl][CtVariableReadImpl]mssqlJdbcProperties.getProperty([CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.AKV_TRUSTED_ENDPOINTS_KEYWORD);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]endpoints) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]endpoints.trim().isEmpty())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]endpoints = [CtInvocationImpl][CtVariableReadImpl]endpoints.trim();
                [CtIfImpl][CtCommentImpl]// Append if the list starts with a semicolon.
                if ([CtBinaryOperatorImpl][CtLiteralImpl]';' != [CtInvocationImpl][CtVariableReadImpl]endpoints.charAt([CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]append = [CtLiteralImpl]false;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]endpoints = [CtInvocationImpl][CtVariableReadImpl]endpoints.substring([CtLiteralImpl]1);
                }
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] entries = [CtInvocationImpl][CtVariableReadImpl]endpoints.split([CtLiteralImpl]";");
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String entry : [CtVariableReadImpl]entries) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]entry) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.trim().isEmpty())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]trustedEndpoints.add([CtInvocationImpl][CtVariableReadImpl]entry.trim());
                    }
                }
            }
        }
        [CtIfImpl][CtCommentImpl]/* List of Azure trusted endpoints
        https://docs.microsoft.com/en-us/azure/key-vault/key-vault-secure-your-key-vault
         */
        if ([CtVariableReadImpl]append) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]trustedEndpoints.add([CtLiteralImpl]"vault.azure.net");
            [CtInvocationImpl][CtVariableReadImpl]trustedEndpoints.add([CtLiteralImpl]"vault.azure.cn");
            [CtInvocationImpl][CtVariableReadImpl]trustedEndpoints.add([CtLiteralImpl]"vault.usgovcloudapi.net");
            [CtInvocationImpl][CtVariableReadImpl]trustedEndpoints.add([CtLiteralImpl]"vault.microsoftazure.de");
        }
        [CtReturnImpl]return [CtVariableReadImpl]trustedEndpoints;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attempt to read MSSQL_JDBC_PROPERTIES.
     *
     * @return corresponding Properties object or null if failed to read the file.
     */
    private static [CtTypeReferenceImpl]java.util.Properties getMssqlJdbcProperties() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Properties props = [CtLiteralImpl]null;
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.FileInputStream in = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.FileInputStream([CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.MSSQL_JDBC_PROPERTIES)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]props = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
            [CtInvocationImpl][CtVariableReadImpl]props.load([CtVariableReadImpl]in);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.akvLogger.isLoggable([CtFieldReadImpl][CtTypeAccessImpl]java.util.logging.Level.[CtFieldReferenceImpl]FINER)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.akvLogger.finer([CtBinaryOperatorImpl][CtLiteralImpl]"Unable to load the mssql-jdbc.properties file: " + [CtVariableReadImpl]e);
            }
        }
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]props) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]props.isEmpty()) ? [CtVariableReadImpl]props : [CtLiteralImpl]null;
    }
}
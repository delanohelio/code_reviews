[CompilationUnitImpl][CtPackageDeclarationImpl]package org.opensmartgridplatform.adapter.protocol.dlms.application.services;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import static org.mockito.ArgumentMatchers.any;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.mockito.Mock;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.GetSecretsResponse;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecret;
[CtUnresolvedImport]import org.apache.commons.codec.binary.Hex;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.OsgpResultType;
[CtUnresolvedImport]import org.mockito.ArgumentCaptor;
[CtUnresolvedImport]import org.junit.jupiter.api.extension.ExtendWith;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.StoreSecretsRequest;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeAll;
[CtUnresolvedImport]import org.opensmartgridplatform.adapter.protocol.dlms.exceptions.ProtocolAdapterException;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.opensmartgridplatform.adapter.protocol.dlms.application.wsclient.SecretManagementClient;
[CtUnresolvedImport]import org.mockito.InjectMocks;
[CtUnresolvedImport]import org.mockito.junit.jupiter.MockitoExtension;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.ActivateSecretsRequest;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.HasNewSecretResponse;
[CtUnresolvedImport]import static org.mockito.Mockito.verify;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecrets;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.opensmartgridplatform.ws.schema.core.secret.management.GenerateAndStoreSecretsResponse;
[CtUnresolvedImport]import org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.opensmartgridplatform.shared.security.RsaEncrypter;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtAnnotationImpl]@org.junit.jupiter.api.extension.ExtendWith([CtFieldReadImpl]org.mockito.junit.jupiter.MockitoExtension.class)
public class SecretManagementServiceTest {
    [CtFieldImpl][CtAnnotationImpl]@org.mockito.InjectMocks
    [CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementService testService;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    [CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.wsclient.SecretManagementClient secretManagementClient;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    [CtTypeReferenceImpl]org.opensmartgridplatform.shared.security.RsaEncrypter rsaEncrypter;

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DEVICE_IDENTIFICATION = [CtLiteralImpl]"E000123456789";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType KEY_TYPE = [CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType.E_METER_ENCRYPTION;

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] UNENCRYPTED_SECRET = [CtNewArrayImpl]new byte[]{ [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1, [CtLiteralImpl]1 };

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] AES_SECRET = [CtNewArrayImpl]new byte[]{ [CtLiteralImpl]0, [CtLiteralImpl]1, [CtLiteralImpl]2, [CtLiteralImpl]3, [CtLiteralImpl]4, [CtLiteralImpl]5, [CtLiteralImpl]6, [CtLiteralImpl]7, [CtLiteralImpl]8, [CtLiteralImpl]9, [CtLiteralImpl]10, [CtLiteralImpl]11, [CtLiteralImpl]12, [CtLiteralImpl]13, [CtLiteralImpl]14, [CtLiteralImpl]15 };

    [CtFieldImpl]private static final [CtArrayTypeReferenceImpl]byte[] SOAP_SECRET = [CtNewArrayImpl]new byte[]{ [CtLiteralImpl]15, [CtLiteralImpl]14, [CtLiteralImpl]13, [CtLiteralImpl]12, [CtLiteralImpl]11, [CtLiteralImpl]10, [CtLiteralImpl]9, [CtLiteralImpl]8, [CtLiteralImpl]7, [CtLiteralImpl]6, [CtLiteralImpl]5, [CtLiteralImpl]4, [CtLiteralImpl]3, [CtLiteralImpl]2, [CtLiteralImpl]1, [CtLiteralImpl]0 };

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String HEX_SOAP_SECRET = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.codec.binary.Hex.encodeHexString([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.SOAP_SECRET);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecret TYPED_SECRET = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecret();

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeAll
    public static [CtTypeReferenceImpl]void init() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.TYPED_SECRET.setType([CtInvocationImpl][CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE.toSecretType());
        [CtInvocationImpl][CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.TYPED_SECRET.setSecret([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.HEX_SOAP_SECRET);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testGetKeys() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// SETUP
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType> keyTypes = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.GetSecretsResponse response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.GetSecretsResponse();
        [CtInvocationImpl][CtVariableReadImpl]response.setResult([CtTypeAccessImpl]OsgpResultType.OK);
        [CtInvocationImpl][CtVariableReadImpl]response.setTypedSecrets([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecrets());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getTypedSecrets().getTypedSecret().add([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.TYPED_SECRET);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.secretManagementClient.getSecretsRequest([CtInvocationImpl]ArgumentMatchers.any())).thenReturn([CtVariableReadImpl]response);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.rsaEncrypter.decrypt([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.SOAP_SECRET)).thenReturn([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET);
        [CtLocalVariableImpl][CtCommentImpl]// EXECUTE
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType, [CtArrayTypeReferenceImpl]byte[]> result = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.testService.getKeys([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION, [CtVariableReadImpl]keyTypes);
        [CtInvocationImpl][CtCommentImpl]// ASSERT
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result).isNotNull();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]result.size()).isEqualTo([CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.keySet().iterator().next()).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]result.values().iterator().next()).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testStoreNewKeys() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType, [CtArrayTypeReferenceImpl]byte[]> keys = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]keys.put([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE, [CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.rsaEncrypter.encrypt([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET)).thenReturn([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.SOAP_SECRET);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.StoreSecretsRequest> storeSecretsCaptor = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.ArgumentCaptor.forClass([CtFieldReadImpl]org.opensmartgridplatform.ws.schema.core.secret.management.StoreSecretsRequest.class);
        [CtInvocationImpl][CtCommentImpl]// EXECUTE
        [CtFieldReadImpl][CtThisAccessImpl]this.testService.storeNewKeys([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION, [CtVariableReadImpl]keys);
        [CtInvocationImpl][CtCommentImpl]// ASSERT
        [CtInvocationImpl]Mockito.verify([CtFieldReadImpl][CtThisAccessImpl]this.secretManagementClient).storeSecretsRequest([CtInvocationImpl][CtVariableReadImpl]storeSecretsCaptor.capture());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.StoreSecretsRequest capturedArgument = [CtInvocationImpl][CtVariableReadImpl]storeSecretsCaptor.getValue();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]capturedArgument.getDeviceId()).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]capturedArgument.getTypedSecrets().getTypedSecret().get([CtLiteralImpl]0).getSecret()).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.HEX_SOAP_SECRET);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testActivateKeys() throws [CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.exceptions.ProtocolAdapterException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType> keyTypes = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.ActivateSecretsRequest> activateSecretsCaptor = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.ArgumentCaptor.forClass([CtFieldReadImpl]org.opensmartgridplatform.ws.schema.core.secret.management.ActivateSecretsRequest.class);
        [CtInvocationImpl][CtCommentImpl]// EXECUTE
        [CtFieldReadImpl][CtThisAccessImpl]this.testService.activateNewKeys([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION, [CtVariableReadImpl]keyTypes);
        [CtInvocationImpl][CtCommentImpl]// ASSERT
        [CtInvocationImpl]Mockito.verify([CtFieldReadImpl][CtThisAccessImpl]this.secretManagementClient).activateSecretsRequest([CtInvocationImpl][CtVariableReadImpl]activateSecretsCaptor.capture());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.ActivateSecretsRequest capturedArgument = [CtInvocationImpl][CtVariableReadImpl]activateSecretsCaptor.getValue();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]capturedArgument.getDeviceId()).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION);
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]capturedArgument.getSecretTypes().getSecretType().get([CtLiteralImpl]0)).isEqualTo([CtInvocationImpl][CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE.toSecretType());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testGenerateAndStoreKeys() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType> keyTypes = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.GenerateAndStoreSecretsResponse response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.GenerateAndStoreSecretsResponse();
        [CtInvocationImpl][CtVariableReadImpl]response.setResult([CtTypeAccessImpl]OsgpResultType.OK);
        [CtInvocationImpl][CtVariableReadImpl]response.setTypedSecrets([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.TypedSecrets());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getTypedSecrets().getTypedSecret().add([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.TYPED_SECRET);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.secretManagementClient.generateAndStoreSecrets([CtInvocationImpl]ArgumentMatchers.any())).thenReturn([CtVariableReadImpl]response);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.rsaEncrypter.decrypt([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.SOAP_SECRET)).thenReturn([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET);
        [CtLocalVariableImpl][CtCommentImpl]// EXECUTE
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.opensmartgridplatform.adapter.protocol.dlms.domain.entities.SecurityKeyType, [CtArrayTypeReferenceImpl]byte[]> keys = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.testService.generate128BitsKeysAndStoreAsNewKeys([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION, [CtVariableReadImpl]keyTypes);
        [CtInvocationImpl][CtCommentImpl]// ASSERT
        [CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]keys.get([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE)).isEqualTo([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.UNENCRYPTED_SECRET);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testHasNewKey() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.HasNewSecretResponse response = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.opensmartgridplatform.ws.schema.core.secret.management.HasNewSecretResponse();
        [CtInvocationImpl][CtVariableReadImpl]response.setHasNewSecret([CtLiteralImpl]true);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.secretManagementClient.hasNewSecretRequest([CtInvocationImpl]ArgumentMatchers.any())).thenReturn([CtVariableReadImpl]response);
        [CtLocalVariableImpl][CtCommentImpl]// EXECUTE
        [CtTypeReferenceImpl]boolean result = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.testService.hasNewSecretOfType([CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.DEVICE_IDENTIFICATION, [CtFieldReadImpl]org.opensmartgridplatform.adapter.protocol.dlms.application.services.SecretManagementServiceTest.KEY_TYPE);
        [CtInvocationImpl][CtCommentImpl]// ASSERT
        [CtInvocationImpl]assertThat([CtVariableReadImpl]result).isTrue();
    }
}
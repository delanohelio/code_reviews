[CompilationUnitImpl][CtPackageDeclarationImpl]package samples.com.microsoft.azure.sdk.iot.helper;
[CtUnresolvedImport]import com.google.gson.JsonObject;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import com.microsoft.azure.sdk.iot.service.devicetwin.*;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl]public class PnpHelper {
    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Pair> CreatePropertyPatch([CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The update patch for a property on the root level or a device without component should be in the format:
        [CtCommentImpl]// "desired": {
        [CtCommentImpl]// "propertyName": {
        [CtCommentImpl]// "value": "hello"
        [CtCommentImpl]// }
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Pair> desiredProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]Pair>();
        [CtInvocationImpl][CtVariableReadImpl]desiredProperties.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]Pair([CtVariableReadImpl]propertyName, [CtVariableReadImpl]propertyValue));
        [CtReturnImpl]return [CtVariableReadImpl]desiredProperties;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Pair> CreatePropertyPatch([CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String propertyValue, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The update patch for a property of a component should be in the format:
        [CtCommentImpl]// "desired": {
        [CtCommentImpl]// "componentName": {
        [CtCommentImpl]// "__t": "c",
        [CtCommentImpl]// "propertyName": {
        [CtCommentImpl]// "value": "hello"
        [CtCommentImpl]// }
        [CtCommentImpl]// }
        [CtTypeReferenceImpl]com.google.gson.JsonObject patchJson = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtCommentImpl]// The following property is required to specify that the update is for a component and not root level.
        [CtVariableReadImpl]patchJson.addProperty([CtLiteralImpl]"__t", [CtLiteralImpl]"c");
        [CtInvocationImpl][CtVariableReadImpl]patchJson.addProperty([CtVariableReadImpl]propertyName, [CtVariableReadImpl]propertyValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]Pair> desiredProperties = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<[CtTypeReferenceImpl]Pair>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Pair desiredProperty = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Pair([CtVariableReadImpl]componentName, [CtVariableReadImpl]patchJson);
        [CtInvocationImpl][CtVariableReadImpl]desiredProperties.add([CtVariableReadImpl]desiredProperty);
        [CtReturnImpl]return [CtVariableReadImpl]desiredProperties;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> CreateMethodPayload([CtParameterImpl][CtTypeReferenceImpl]java.lang.String methodValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// The method payload should be in the following format:
        [CtCommentImpl]// "payload": {
        [CtCommentImpl]// "commandRequest": {
        [CtCommentImpl]// "value": "hello"
        [CtCommentImpl]// }
        [CtTypeReferenceImpl]com.google.gson.JsonObject commandPayload = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]commandPayload.addProperty([CtLiteralImpl]"value", [CtVariableReadImpl]methodValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> payload = [CtNewClassImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>()[CtClassImpl] {
            [CtAnonymousExecutableImpl][CtBlockImpl]{
                [CtInvocationImpl]put([CtLiteralImpl]"commandRequest", [CtVariableReadImpl]commandPayload);
            }
        };
        [CtReturnImpl]return [CtVariableReadImpl]payload;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]java.lang.String CreateComponentMethodName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String methodName) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// The method to invoke for components should be in the format:
        [CtCommentImpl]// "comonentName*methodName"
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]componentName + [CtLiteralImpl]"*") + [CtVariableReadImpl]methodName;
    }
}
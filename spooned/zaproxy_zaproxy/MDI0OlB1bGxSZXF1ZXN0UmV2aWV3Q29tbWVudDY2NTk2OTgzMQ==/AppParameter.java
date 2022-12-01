[CompilationUnitImpl][CtPackageDeclarationImpl]package org.parosproxy.paros.core.scanner;
[CtClassImpl][CtJavaDocImpl]/**
 * {@code AppParameter} class wraps the parameters which are used to modify the {@code HttpMessage}.
 * It is specifically used for updating multiple parameters of {@code HttpMessage}
 *
 * @author preetkaran20@gmail.com KSASAN
 */
public class AppParameter {
    [CtFieldImpl]private [CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.NameValuePair nameValuePair;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String param;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String value;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.AppParamValueType appParamValueType;

    [CtConstructorImpl]AppParameter([CtParameterImpl][CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.NameValuePair nameValuePair, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String param, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value, [CtParameterImpl][CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.AppParamValueType appParamValueType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.nameValuePair = [CtVariableReadImpl]nameValuePair;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.param = [CtVariableReadImpl]param;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.value = [CtVariableReadImpl]value;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.appParamValueType = [CtVariableReadImpl]appParamValueType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.NameValuePair getNameValuePair() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]nameValuePair;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getParam() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]param;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]value;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.parosproxy.paros.core.scanner.AppParamValueType getAppParamValueType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]appParamValueType;
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.aws.iot.evergreen.kernel;
[CtUnresolvedImport]import com.aws.iot.evergreen.dependency.Context;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeEach;
[CtUnresolvedImport]import org.mockito.junit.jupiter.MockitoExtension;
[CtUnresolvedImport]import org.mockito.InOrder;
[CtUnresolvedImport]import org.mockito.Mock;
[CtUnresolvedImport]import org.mockito.Captor;
[CtUnresolvedImport]import com.aws.iot.evergreen.util.Log;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.mockito.Mockito;
[CtUnresolvedImport]import org.mockito.ArgumentCaptor;
[CtUnresolvedImport]import com.aws.iot.evergreen.config.Validator;
[CtUnresolvedImport]import org.junit.jupiter.api.extension.ExtendWith;
[CtUnresolvedImport]import org.junit.jupiter.api.Assertions;
[CtUnresolvedImport]import com.aws.iot.evergreen.config.Topics;
[CtUnresolvedImport]import com.aws.iot.evergreen.dependency.State;
[CtUnresolvedImport]import com.aws.iot.evergreen.config.Topic;
[CtClassImpl][CtAnnotationImpl]@org.junit.jupiter.api.extension.ExtendWith([CtFieldReadImpl]org.mockito.junit.jupiter.MockitoExtension.class)
class EvergreenServiceTest {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String STATE_TOPIC_NAME = [CtLiteralImpl]"_State";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EVERGREEN_SERVICE_FULL_NAME = [CtLiteralImpl]"EvergreenServiceFullName";

    [CtFieldImpl]private [CtTypeReferenceImpl]com.aws.iot.evergreen.kernel.EvergreenService evergreenService;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]com.aws.iot.evergreen.config.Topics config;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]com.aws.iot.evergreen.config.Topic stateTopic;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]com.aws.iot.evergreen.dependency.Context context;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]com.aws.iot.evergreen.util.Log log;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Captor
    private [CtTypeReferenceImpl]org.mockito.ArgumentCaptor<[CtTypeReferenceImpl]com.aws.iot.evergreen.config.Validator> validatorArgumentCaptor;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeEach
    [CtTypeReferenceImpl]void beforeEach() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]config.createLeafChild([CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.any())).thenReturn([CtFieldReadImpl]stateTopic);
        [CtAssignmentImpl][CtFieldWriteImpl]evergreenService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.aws.iot.evergreen.kernel.EvergreenService([CtFieldReadImpl]config);
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]evergreenService.context = [CtFieldReadImpl]context;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testConstructor() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// GIVEN
        [CtCommentImpl]// beforeEach
        [CtCommentImpl]// WHEN
        [CtCommentImpl]// beforeEach
        [CtCommentImpl]// THEN
        [CtCommentImpl]// verify config
        [CtTypeAccessImpl]org.junit.jupiter.api.Assertions.assertSame([CtFieldReadImpl]config, [CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]evergreenService.config);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]config).createLeafChild([CtFieldReadImpl]com.aws.iot.evergreen.kernel.EvergreenServiceTest.STATE_TOPIC_NAME);
        [CtInvocationImpl][CtCommentImpl]// verify stateTopic
        [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]stateTopic).setParentNeedsToKnow([CtLiteralImpl]false);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]stateTopic).setValue([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Long.[CtFieldReferenceImpl]MAX_VALUE, [CtTypeAccessImpl]State.New);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]stateTopic).validate([CtInvocationImpl][CtFieldReadImpl]validatorArgumentCaptor.capture());
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]stateTopic).subscribe([CtFieldReadImpl]evergreenService);
        [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verifyNoMoreInteractions([CtFieldReadImpl]stateTopic);
        [CtLocalVariableImpl][CtCommentImpl]// verify validator
        [CtTypeReferenceImpl]com.aws.iot.evergreen.config.Validator validator = [CtInvocationImpl][CtFieldReadImpl]validatorArgumentCaptor.getValue();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.iot.evergreen.dependency.State returnedState = [CtInvocationImpl](([CtTypeReferenceImpl]com.aws.iot.evergreen.dependency.State) ([CtVariableReadImpl]validator.validate([CtTypeAccessImpl]State.New, [CtLiteralImpl]null)));
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.jupiter.api.Assertions.assertSame([CtTypeAccessImpl]State.New, [CtVariableReadImpl]returnedState);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void getState() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]stateTopic.getOnce()).thenReturn([CtTypeAccessImpl]State.New);
        [CtInvocationImpl][CtTypeAccessImpl]org.junit.jupiter.api.Assertions.assertSame([CtTypeAccessImpl]State.New, [CtInvocationImpl][CtFieldReadImpl]evergreenService.getState());
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.verify([CtFieldReadImpl]stateTopic).getOnce();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void setState() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// GIVEN
        [CtTypeReferenceImpl]com.aws.iot.evergreen.dependency.State currentState = [CtFieldReadImpl]com.aws.iot.evergreen.dependency.State.New;
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.aws.iot.evergreen.dependency.State newState = [CtFieldReadImpl]com.aws.iot.evergreen.dependency.State.Installing;
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]stateTopic.getOnce()).thenReturn([CtVariableReadImpl]currentState);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]context.getLog()).thenReturn([CtFieldReadImpl]log);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]config.getFullName()).thenReturn([CtFieldReadImpl]com.aws.iot.evergreen.kernel.EvergreenServiceTest.EVERGREEN_SERVICE_FULL_NAME);
        [CtInvocationImpl][CtCommentImpl]// WHEN
        [CtFieldReadImpl]evergreenService.setState([CtVariableReadImpl]newState);
        [CtLocalVariableImpl][CtCommentImpl]// THEN
        [CtTypeReferenceImpl]org.mockito.InOrder inOrder = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.inOrder([CtFieldReadImpl]stateTopic, [CtFieldReadImpl]context, [CtFieldReadImpl]log);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inOrder.verify([CtFieldReadImpl]stateTopic).getOnce();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inOrder.verify([CtFieldReadImpl]context).getLog();
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inOrder.verify([CtFieldReadImpl]log).note([CtFieldReadImpl]com.aws.iot.evergreen.kernel.EvergreenServiceTest.EVERGREEN_SERVICE_FULL_NAME, [CtVariableReadImpl]currentState, [CtLiteralImpl]"=>", [CtVariableReadImpl]newState);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inOrder.verify([CtFieldReadImpl]stateTopic).setValue([CtFieldReadImpl][CtTypeAccessImpl]java.lang.Long.[CtFieldReferenceImpl]MAX_VALUE, [CtVariableReadImpl]newState);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]inOrder.verify([CtFieldReadImpl]context).globalNotifyStateChanged([CtFieldReadImpl]evergreenService, [CtVariableReadImpl]currentState);
    }
}
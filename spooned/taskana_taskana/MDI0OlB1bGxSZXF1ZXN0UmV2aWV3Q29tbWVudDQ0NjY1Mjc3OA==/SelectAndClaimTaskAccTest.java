[CompilationUnitImpl][CtPackageDeclarationImpl]package acceptance.task;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import pro.taskana.common.internal.security.JaasExtension;
[CtUnresolvedImport]import pro.taskana.common.api.BaseQuery.SortDirection;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import acceptance.AbstractAccTest;
[CtUnresolvedImport]import pro.taskana.task.api.TaskService;
[CtImportImpl]import javax.security.auth.Subject;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import pro.taskana.task.api.models.Task;
[CtUnresolvedImport]import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
[CtUnresolvedImport]import org.junit.jupiter.api.extension.ExtendWith;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThatThrownBy;
[CtImportImpl]import java.util.function.Consumer;
[CtImportImpl]import java.security.PrivilegedAction;
[CtUnresolvedImport]import pro.taskana.common.internal.security.UserPrincipal;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import pro.taskana.task.api.TaskQuery;
[CtUnresolvedImport]import pro.taskana.common.api.exceptions.SystemException;
[CtUnresolvedImport]import pro.taskana.common.internal.util.CheckedConsumer;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import pro.taskana.common.internal.security.WithAccessId;
[CtClassImpl][CtAnnotationImpl]@org.junit.jupiter.api.extension.ExtendWith([CtFieldReadImpl]pro.taskana.common.internal.security.JaasExtension.class)
class SelectAndClaimTaskAccTest extends [CtTypeReferenceImpl]acceptance.AbstractAccTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void should_claimDifferentTasks_For_ConcurrentSelectAndClaimCalls() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.task.api.models.Task> selectedAndClaimedTasks = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.synchronizedList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> accessIds = [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.synchronizedList([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Stream.of([CtLiteralImpl]"admin", [CtLiteralImpl]"teamlead-1", [CtLiteralImpl]"teamlead-2", [CtLiteralImpl]"taskadmin").collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Runnable test = [CtInvocationImpl]getRunnableTest([CtVariableReadImpl]selectedAndClaimedTasks, [CtVariableReadImpl]accessIds);
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Thread[] threads = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Thread[[CtLiteralImpl]4];
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]threads.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]threads[[CtVariableReadImpl]i] = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.Thread([CtVariableReadImpl]test);
            [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]threads[[CtVariableReadImpl]i].start();
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]threads.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl][CtArrayReadImpl][CtVariableReadImpl]threads[[CtVariableReadImpl]i].join();
        }
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selectedAndClaimedTasks.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Task::getId)).containsExactlyInAnyOrder([CtLiteralImpl]"TKI:000000000000000000000000000000000003", [CtLiteralImpl]"TKI:000000000000000000000000000000000004", [CtLiteralImpl]"TKI:000000000000000000000000000000000005", [CtLiteralImpl]"TKI:000000000000000000000000000000000006");
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selectedAndClaimedTasks.stream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Task::getOwner)).containsExactlyInAnyOrder([CtLiteralImpl]"admin", [CtLiteralImpl]"taskadmin", [CtLiteralImpl]"teamlead-1", [CtLiteralImpl]"teamlead-2");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtAnnotationImpl]@pro.taskana.common.internal.security.WithAccessId(user = [CtLiteralImpl]"admin")
    [CtTypeReferenceImpl]void should_ThrowException_When_TryingToSelectAndClaimNonExistingTask() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]pro.taskana.task.api.TaskQuery query = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]taskanaEngine.getTaskService().createTaskQuery();
        [CtInvocationImpl][CtVariableReadImpl]query.idIn([CtLiteralImpl]"notexisting");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.assertj.core.api.ThrowableAssert.ThrowingCallable call = [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]acceptance.task.taskanaEngine.getTaskService().selectAndClaim([CtVariableReadImpl]query);
        };
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThatThrownBy([CtVariableReadImpl]call).isInstanceOf([CtFieldReadImpl]pro.taskana.common.api.exceptions.SystemException.class).hasMessageContaining([CtBinaryOperatorImpl][CtLiteralImpl]"No tasks matched the specified filter and sorting options, " + [CtLiteralImpl]"task query returned nothing!");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.Runnable getRunnableTest([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.task.api.models.Task> selectedAndClaimedTasks, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> accessIds) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Runnable test = [CtLambdaImpl]() -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.security.auth.Subject subject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.security.auth.Subject();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subject.getPrincipals().add([CtConstructorCallImpl]new [CtTypeReferenceImpl]pro.taskana.common.internal.security.UserPrincipal([CtInvocationImpl][CtVariableReadImpl]accessIds.remove([CtLiteralImpl]0)));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]pro.taskana.task.api.TaskService> consumer = [CtInvocationImpl][CtTypeAccessImpl]pro.taskana.common.internal.util.CheckedConsumer.wrap([CtLambdaImpl]([CtParameterImpl] taskService) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]pro.taskana.task.api.models.Task task = [CtInvocationImpl][CtVariableReadImpl]taskService.selectAndClaim([CtInvocationImpl]getTaskQuery());
                [CtInvocationImpl][CtVariableReadImpl]selectedAndClaimedTasks.add([CtVariableReadImpl]task);
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.security.PrivilegedAction<[CtTypeReferenceImpl]java.lang.Void> action = [CtLambdaImpl]() -> [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]consumer.accept([CtInvocationImpl][CtFieldReadImpl]taskanaEngine.getTaskService());
                [CtReturnImpl]return [CtLiteralImpl]null;
            };
            [CtInvocationImpl][CtTypeAccessImpl]javax.security.auth.Subject.doAs([CtVariableReadImpl]subject, [CtVariableReadImpl]action);
        };
        [CtReturnImpl]return [CtVariableReadImpl]test;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]pro.taskana.task.api.TaskQuery getTaskQuery() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]taskanaEngine.getTaskService().createTaskQuery().orderByTaskId([CtTypeAccessImpl]SortDirection.ASCENDING);
    }
}
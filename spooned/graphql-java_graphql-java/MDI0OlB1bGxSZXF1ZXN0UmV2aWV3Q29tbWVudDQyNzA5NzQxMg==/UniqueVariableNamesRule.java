[CompilationUnitImpl][CtPackageDeclarationImpl]package graphql.validation.rules;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import graphql.validation.AbstractRule;
[CtUnresolvedImport]import graphql.language.*;
[CtUnresolvedImport]import graphql.validation.ValidationErrorType;
[CtUnresolvedImport]import graphql.validation.ValidationContext;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import graphql.validation.ValidationErrorCollector;
[CtImportImpl]import java.util.HashSet;
[CtClassImpl][CtJavaDocImpl]/**
 * Unique variable names
 * <p>
 * A GraphQL operation is only valid if all its variables are uniquely named.
 */
public class UniqueVariableNamesRule extends [CtTypeReferenceImpl]graphql.validation.AbstractRule {
    [CtConstructorImpl]public UniqueVariableNamesRule([CtParameterImpl][CtTypeReferenceImpl]graphql.validation.ValidationContext validationContext, [CtParameterImpl][CtTypeReferenceImpl]graphql.validation.ValidationErrorCollector validationErrorCollector) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]validationContext, [CtVariableReadImpl]validationErrorCollector);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void checkOperationDefinition([CtParameterImpl][CtTypeReferenceImpl]OperationDefinition operationDefinition) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]VariableDefinition> variableDefinitions = [CtInvocationImpl][CtVariableReadImpl]operationDefinition.getVariableDefinitions();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]variableDefinitions == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]variableDefinitions.size() <= [CtLiteralImpl]1)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> variableNameList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>([CtInvocationImpl][CtVariableReadImpl]variableDefinitions.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]VariableDefinition variableDefinition : [CtVariableReadImpl]variableDefinitions) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]variableNameList.contains([CtInvocationImpl][CtVariableReadImpl]variableDefinition.getName())) [CtBlockImpl]{
                [CtInvocationImpl]addError([CtTypeAccessImpl]ValidationErrorType.DuplicateVariableName, [CtInvocationImpl][CtVariableReadImpl]variableDefinition.getSourceLocation(), [CtInvocationImpl]graphql.validation.rules.UniqueVariableNamesRule.duplicateVariableNameMessage([CtInvocationImpl][CtVariableReadImpl]variableDefinition.getName()));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]variableNameList.add([CtInvocationImpl][CtVariableReadImpl]variableDefinition.getName());
            }
        }
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.lang.String duplicateVariableNameMessage([CtParameterImpl][CtTypeReferenceImpl]java.lang.String variableName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"There can be only one variable named '%s'", [CtVariableReadImpl]variableName);
    }
}
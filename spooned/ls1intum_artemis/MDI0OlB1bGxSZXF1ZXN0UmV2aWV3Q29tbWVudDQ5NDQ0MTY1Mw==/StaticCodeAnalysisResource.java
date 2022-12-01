[CompilationUnitImpl][CtPackageDeclarationImpl]package de.tum.in.www1.artemis.web.rest;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.PathVariable;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.ProgrammingExercise;
[CtUnresolvedImport]import static de.tum.in.www1.artemis.web.rest.util.ResponseUtil.*;
[CtUnresolvedImport]import org.springframework.security.access.prepost.PreAuthorize;
[CtUnresolvedImport]import de.tum.in.www1.artemis.service.ProgrammingExerciseService;
[CtUnresolvedImport]import de.tum.in.www1.artemis.service.UserService;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RequestMapping;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RestController;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.PatchMapping;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.RequestBody;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import org.springframework.web.bind.annotation.GetMapping;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory;
[CtUnresolvedImport]import de.tum.in.www1.artemis.service.AuthorizationCheckService;
[CtUnresolvedImport]import org.springframework.http.ResponseEntity;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import de.tum.in.www1.artemis.service.StaticCodeAnalysisService;
[CtClassImpl][CtJavaDocImpl]/**
 * REST controller for managing static code analysis.
 * Static code analysis categories are created automatically when the programming exercise with static code analysis is
 * created, therefore a POST mapping is missing. A DELETE mapping is also not necessary as those categories can only be
 * deactivated but not deleted.
 */
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RestController
[CtAnnotationImpl]@org.springframework.web.bind.annotation.RequestMapping([CtLiteralImpl]"/api")
public class StaticCodeAnalysisResource {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ENTITY_NAME = [CtLiteralImpl]"StaticCodeAnalysisCategory";

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]de.tum.in.www1.artemis.service.AuthorizationCheckService authCheckService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]de.tum.in.www1.artemis.service.ProgrammingExerciseService programmingExerciseService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]de.tum.in.www1.artemis.service.StaticCodeAnalysisService staticCodeAnalysisService;

    [CtConstructorImpl]public StaticCodeAnalysisResource([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.service.AuthorizationCheckService authCheckService, [CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.service.ProgrammingExerciseService programmingExerciseService, [CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.service.StaticCodeAnalysisService staticCodeAnalysisService) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.authCheckService = [CtVariableReadImpl]authCheckService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.programmingExerciseService = [CtVariableReadImpl]programmingExerciseService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.staticCodeAnalysisService = [CtVariableReadImpl]staticCodeAnalysisService;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the static code analysis categories for a given exercise id.
     *
     * @param exerciseId
     * 		of the the exercise
     * @return the static code analysis categories
     */
    [CtAnnotationImpl]@org.springframework.web.bind.annotation.GetMapping([CtFieldReadImpl][CtTypeAccessImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.Endpoints.[CtFieldReferenceImpl]CATEGORIES)
    [CtAnnotationImpl]@org.springframework.security.access.prepost.PreAuthorize([CtLiteralImpl]"hasAnyRole('TA', 'INSTRUCTOR', 'ADMIN')")
    public [CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory>> getStaticCodeAnalysisCategories([CtParameterImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.PathVariable
    [CtTypeReferenceImpl]java.lang.Long exerciseId) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"REST request to get static code analysis categories for programming exercise {}", [CtVariableReadImpl]exerciseId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ProgrammingExercise programmingExercise = [CtInvocationImpl][CtFieldReadImpl]programmingExerciseService.findById([CtVariableReadImpl]exerciseId);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]programmingExercise.isStaticCodeAnalysisEnabled())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]badRequest();
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]authCheckService.isAtLeastTeachingAssistantForExercise([CtVariableReadImpl]programmingExercise)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]forbidden();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory> staticCodeAnalysisCategories = [CtInvocationImpl][CtFieldReadImpl]staticCodeAnalysisService.findByExerciseId([CtVariableReadImpl]exerciseId);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.springframework.http.ResponseEntity.ok([CtVariableReadImpl]staticCodeAnalysisCategories);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Updates the static code analysis categories of a given programming exercise using the data in the request body.
     *
     * @param exerciseId
     * 		of the the exercise
     * @param categories
     * 		used for the update
     * @return the updated static code analysis categories
     */
    [CtAnnotationImpl]@org.springframework.web.bind.annotation.PatchMapping([CtFieldReadImpl][CtTypeAccessImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.Endpoints.[CtFieldReferenceImpl]CATEGORIES)
    [CtAnnotationImpl]@org.springframework.security.access.prepost.PreAuthorize([CtLiteralImpl]"hasAnyRole('TA', 'INSTRUCTOR', 'ADMIN')")
    public [CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory>> updateStaticCodeAnalysisCategories([CtParameterImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.PathVariable
    [CtTypeReferenceImpl]java.lang.Long exerciseId, [CtParameterImpl][CtAnnotationImpl]@org.springframework.web.bind.annotation.RequestBody
    [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory> categories) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]log.debug([CtLiteralImpl]"REST request to update static code analysis categories for programming exercise {}", [CtVariableReadImpl]exerciseId);
        [CtLocalVariableImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ProgrammingExercise programmingExercise = [CtInvocationImpl][CtFieldReadImpl]programmingExerciseService.findById([CtVariableReadImpl]exerciseId);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.Boolean.[CtFieldReferenceImpl]TRUE.equals([CtInvocationImpl][CtVariableReadImpl]programmingExercise.isStaticCodeAnalysisEnabled())) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]badRequest();
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]authCheckService.isAtLeastTeachingAssistantForExercise([CtVariableReadImpl]programmingExercise)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]forbidden();
        }
        [CtForEachImpl][CtCommentImpl]// Validate the category updates
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]var category : [CtVariableReadImpl]categories) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Each categories must have an id
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]category.getId() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]badRequest([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.ENTITY_NAME, [CtLiteralImpl]"scaCategoryIdError", [CtLiteralImpl]"Static code analysis category id is missing.");
            }
            [CtIfImpl][CtCommentImpl]// Penalty must not be null or negative
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]category.getPenalty() == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]category.getPenalty() < [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]badRequest([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.ENTITY_NAME + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]category.getId(), [CtLiteralImpl]"scaCategoryPenaltyError", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Penalty for static code analysis category " + [CtInvocationImpl][CtVariableReadImpl]category.getId()) + [CtLiteralImpl]" must be a non-negative integer.");
            }
            [CtIfImpl][CtCommentImpl]// MaxPenalty must not be smaller than penalty
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]category.getMaxPenalty() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]category.getPenalty() > [CtInvocationImpl][CtVariableReadImpl]category.getMaxPenalty())) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]badRequest([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.ENTITY_NAME + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]category.getId(), [CtLiteralImpl]"scaCategoryMaxPenaltyError", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Max Penalty for static code analysis category " + [CtInvocationImpl][CtVariableReadImpl]category.getId()) + [CtLiteralImpl]" must not be smaller than the penalty.");
            }
            [CtIfImpl][CtCommentImpl]// Category state must not be null
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]category.getState() == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]badRequest([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.ENTITY_NAME + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]category.getId(), [CtLiteralImpl]"scaCategoryStateError", [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Max Penalty for static code analysis category " + [CtInvocationImpl][CtVariableReadImpl]category.getId()) + [CtLiteralImpl]" must not be smaller than the penalty.");
            }
            [CtIfImpl][CtCommentImpl]// Exercise id of the request path must match the exerciseId in the request body if present
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]category.getExercise() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]category.getExercise().getId(), [CtVariableReadImpl]exerciseId))) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl]conflict([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.ENTITY_NAME + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]category.getId(), [CtLiteralImpl]"scaCategoryExerciseIdError", [CtBinaryOperatorImpl][CtLiteralImpl]"Exercise id path variable does not match exercise id of static code analysis category " + [CtInvocationImpl][CtVariableReadImpl]category.getId());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.StaticCodeAnalysisCategory> staticCodeAnalysisCategories = [CtInvocationImpl][CtFieldReadImpl]staticCodeAnalysisService.updateCategories([CtVariableReadImpl]exerciseId, [CtVariableReadImpl]categories);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.springframework.http.ResponseEntity.ok([CtVariableReadImpl]staticCodeAnalysisCategories);
    }

    [CtClassImpl]public static final class Endpoints {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PROGRAMMING_EXERCISE = [CtLiteralImpl]"/programming-exercise/{exerciseId}";

        [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String CATEGORIES = [CtBinaryOperatorImpl][CtFieldReadImpl]de.tum.in.www1.artemis.web.rest.StaticCodeAnalysisResource.Endpoints.PROGRAMMING_EXERCISE + [CtLiteralImpl]"/static-code-analysis-categories";

        [CtConstructorImpl]private Endpoints() [CtBlockImpl]{
        }
    }
}
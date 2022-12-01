[CompilationUnitImpl][CtPackageDeclarationImpl]package org.hisp.dhis.tracker.bundle;
[CtUnresolvedImport]import org.hisp.dhis.cache.HibernateCacheManager;
[CtUnresolvedImport]import org.hisp.dhis.tracker.domain.Attribute;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.hisp.dhis.rules.models.RuleEffect;
[CtUnresolvedImport]import org.hisp.dhis.reservedvalue.ReservedValueService;
[CtUnresolvedImport]import org.hibernate.Session;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import org.hisp.dhis.program.ProgramStageInstance;
[CtUnresolvedImport]import org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue;
[CtUnresolvedImport]import org.hisp.dhis.tracker.domain.Relationship;
[CtUnresolvedImport]import org.hisp.dhis.tracker.report.TrackerObjectReport;
[CtUnresolvedImport]import org.hisp.dhis.tracker.domain.Enrollment;
[CtUnresolvedImport]import org.hisp.dhis.tracker.report.TrackerBundleReport;
[CtUnresolvedImport]import org.hisp.dhis.common.IdentifiableObjectManager;
[CtUnresolvedImport]import org.hisp.dhis.dbms.DbmsManager;
[CtUnresolvedImport]import org.springframework.stereotype.Service;
[CtUnresolvedImport]import org.hisp.dhis.program.ProgramInstance;
[CtUnresolvedImport]import org.hisp.dhis.tracker.TrackerIdScheme;
[CtUnresolvedImport]import org.hisp.dhis.tracker.TrackerProgramRuleService;
[CtUnresolvedImport]import org.hisp.dhis.user.User;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.hisp.dhis.tracker.sideeffect.SideEffectHandlerService;
[CtUnresolvedImport]import org.hisp.dhis.tracker.FlushMode;
[CtUnresolvedImport]import org.hisp.dhis.tracker.job.TrackerSideEffectDataBundle;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import org.hisp.dhis.tracker.domain.Event;
[CtUnresolvedImport]import org.hisp.dhis.tracker.converter.TrackerConverterService;
[CtUnresolvedImport]import org.hisp.dhis.fileresource.FileResource;
[CtUnresolvedImport]import org.hisp.dhis.tracker.domain.TrackedEntity;
[CtUnresolvedImport]import org.hibernate.SessionFactory;
[CtUnresolvedImport]import org.hisp.dhis.trackedentity.TrackedEntityInstance;
[CtUnresolvedImport]import org.hisp.dhis.tracker.TrackerType;
[CtUnresolvedImport]import org.hisp.dhis.tracker.preheat.TrackerPreheatService;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.hisp.dhis.user.CurrentUserService;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.hisp.dhis.trackedentity.TrackedEntityAttribute;
[CtUnresolvedImport]import org.hisp.dhis.tracker.preheat.TrackerPreheat;
[CtUnresolvedImport]import org.hisp.dhis.tracker.preheat.TrackerPreheatParams;
[CtUnresolvedImport]import org.hisp.dhis.tracker.report.TrackerTypeReport;
[CtUnresolvedImport]import org.springframework.transaction.annotation.Transactional;
[CtUnresolvedImport]import org.springframework.util.StringUtils;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Morten Olav Hansen <mortenoh@gmail.com>
 */
[CtAnnotationImpl]@org.springframework.stereotype.Service
public class DefaultTrackerBundleService implements [CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundleService {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheatService trackerPreheatService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.TrackedEntity, [CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityInstance> trackedEntityTrackerConverterService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Enrollment, [CtTypeReferenceImpl]org.hisp.dhis.program.ProgramInstance> enrollmentTrackerConverterService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Event, [CtTypeReferenceImpl]org.hisp.dhis.program.ProgramStageInstance> eventTrackerConverterService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Relationship, [CtTypeReferenceImpl]org.hisp.dhis.relationship.Relationship> relationshipTrackerConverterService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.user.CurrentUserService currentUserService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.common.IdentifiableObjectManager manager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hibernate.SessionFactory sessionFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.cache.HibernateCacheManager cacheManager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.dbms.DbmsManager dbmsManager;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.tracker.TrackerProgramRuleService trackerProgramRuleService;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.hisp.dhis.reservedvalue.ReservedValueService reservedValueService;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundleHook> bundleHooks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.sideeffect.SideEffectHandlerService> sideEffectHandlers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired(required = [CtLiteralImpl]false)
    public [CtTypeReferenceImpl]void setBundleHooks([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundleHook> bundleHooks) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bundleHooks = [CtVariableReadImpl]bundleHooks;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired(required = [CtLiteralImpl]false)
    public [CtTypeReferenceImpl]void setSideEffectHandlers([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.sideeffect.SideEffectHandlerService> sideEffectHandlers) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sideEffectHandlers = [CtVariableReadImpl]sideEffectHandlers;
    }

    [CtConstructorImpl]public DefaultTrackerBundleService([CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheatService trackerPreheatService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.TrackedEntity, [CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityInstance> trackedEntityTrackerConverterService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Enrollment, [CtTypeReferenceImpl]org.hisp.dhis.program.ProgramInstance> enrollmentTrackerConverterService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Event, [CtTypeReferenceImpl]org.hisp.dhis.program.ProgramStageInstance> eventTrackerConverterService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.converter.TrackerConverterService<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Relationship, [CtTypeReferenceImpl]org.hisp.dhis.relationship.Relationship> relationshipTrackerConverterService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.user.CurrentUserService currentUserService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.common.IdentifiableObjectManager manager, [CtParameterImpl][CtTypeReferenceImpl]org.hibernate.SessionFactory sessionFactory, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.cache.HibernateCacheManager cacheManager, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.dbms.DbmsManager dbmsManager, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.TrackerProgramRuleService trackerProgramRuleService, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.reservedvalue.ReservedValueService reservedValueService) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.trackerPreheatService = [CtVariableReadImpl]trackerPreheatService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.trackedEntityTrackerConverterService = [CtVariableReadImpl]trackedEntityTrackerConverterService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.enrollmentTrackerConverterService = [CtVariableReadImpl]enrollmentTrackerConverterService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.eventTrackerConverterService = [CtVariableReadImpl]eventTrackerConverterService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.relationshipTrackerConverterService = [CtVariableReadImpl]relationshipTrackerConverterService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentUserService = [CtVariableReadImpl]currentUserService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.manager = [CtVariableReadImpl]manager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sessionFactory = [CtVariableReadImpl]sessionFactory;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.cacheManager = [CtVariableReadImpl]cacheManager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.dbmsManager = [CtVariableReadImpl]dbmsManager;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.trackerProgramRuleService = [CtVariableReadImpl]trackerProgramRuleService;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.reservedValueService = [CtVariableReadImpl]reservedValueService;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.springframework.transaction.annotation.Transactional
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle> create([CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundleParams params) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle trackerBundle = [CtInvocationImpl][CtVariableReadImpl]params.toTrackerBundle();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheatParams preheatParams = [CtInvocationImpl][CtVariableReadImpl]params.toTrackerPreheatParams();
        [CtInvocationImpl][CtVariableReadImpl]preheatParams.setUser([CtInvocationImpl]getUser([CtInvocationImpl][CtVariableReadImpl]preheatParams.getUser(), [CtInvocationImpl][CtVariableReadImpl]preheatParams.getUserId()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheat preheat = [CtInvocationImpl][CtFieldReadImpl]trackerPreheatService.preheat([CtVariableReadImpl]preheatParams);
        [CtInvocationImpl][CtVariableReadImpl]trackerBundle.setPreheat([CtVariableReadImpl]preheat);
        [CtReturnImpl][CtCommentImpl]// Map<String, List<RuleEffect>> enrollmentRuleEffects = trackerProgramRuleService
        [CtCommentImpl]// .calculateEnrollmentRuleEffects( trackerBundle.getEnrollments(), trackerBundle );
        [CtCommentImpl]// Map<String, List<RuleEffect>> eventRuleEffects = trackerProgramRuleService
        [CtCommentImpl]// .calculateEventRuleEffects( trackerBundle.getEvents(), trackerBundle );
        [CtCommentImpl]// trackerBundle.setEnrollmentRuleEffects( enrollmentRuleEffects );
        [CtCommentImpl]// trackerBundle.setEventRuleEffects( eventRuleEffects );
        return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]trackerBundle);[CtCommentImpl]// for now we don't split the bundles

    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.springframework.transaction.annotation.Transactional
    public [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerBundleReport commit([CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle bundle) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerBundleReport bundleReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerBundleReport();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]TrackerBundleMode.VALIDATE == [CtInvocationImpl][CtVariableReadImpl]bundle.getImportMode()) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]bundleReport;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hibernate.Session session = [CtInvocationImpl][CtFieldReadImpl]sessionFactory.getCurrentSession();
        [CtInvocationImpl][CtFieldReadImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.preCommit([CtVariableReadImpl]bundle));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport trackedEntityReport = [CtInvocationImpl]handleTrackedEntities([CtVariableReadImpl]session, [CtVariableReadImpl]bundle);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport enrollmentReport = [CtInvocationImpl]handleEnrollments([CtVariableReadImpl]session, [CtVariableReadImpl]bundle);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport eventReport = [CtInvocationImpl]handleEvents([CtVariableReadImpl]session, [CtVariableReadImpl]bundle);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport relationshipReport = [CtInvocationImpl]handleRelationships([CtVariableReadImpl]session, [CtVariableReadImpl]bundle);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundleReport.getTypeReportMap().put([CtTypeAccessImpl]TrackerType.TRACKED_ENTITY, [CtVariableReadImpl]trackedEntityReport);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundleReport.getTypeReportMap().put([CtTypeAccessImpl]TrackerType.ENROLLMENT, [CtVariableReadImpl]enrollmentReport);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundleReport.getTypeReportMap().put([CtTypeAccessImpl]TrackerType.EVENT, [CtVariableReadImpl]eventReport);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundleReport.getTypeReportMap().put([CtTypeAccessImpl]TrackerType.RELATIONSHIP, [CtVariableReadImpl]relationshipReport);
        [CtInvocationImpl][CtFieldReadImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.postCommit([CtVariableReadImpl]bundle));
        [CtInvocationImpl][CtFieldReadImpl]dbmsManager.clearSession();
        [CtInvocationImpl][CtFieldReadImpl]cacheManager.clearCache();
        [CtReturnImpl]return [CtVariableReadImpl]bundleReport;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport handleTrackedEntities([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle bundle) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.TrackedEntity> trackedEntities = [CtInvocationImpl][CtVariableReadImpl]bundle.getTrackedEntities();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport typeReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.TRACKED_ENTITY);
        [CtInvocationImpl][CtVariableReadImpl]trackedEntities.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.preCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.TrackedEntity.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date now = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtVariableReadImpl]trackedEntities.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.TrackedEntity trackedEntity = [CtInvocationImpl][CtVariableReadImpl]trackedEntities.get([CtVariableReadImpl]idx);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityInstance trackedEntityInstance = [CtInvocationImpl][CtFieldReadImpl]trackedEntityTrackerConverterService.from([CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtVariableReadImpl]trackedEntity);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport objectReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.TRACKED_ENTITY, [CtInvocationImpl][CtVariableReadImpl]trackedEntityInstance.getUid(), [CtVariableReadImpl]idx);
            [CtInvocationImpl][CtVariableReadImpl]typeReport.addObjectReport([CtVariableReadImpl]objectReport);
            [CtInvocationImpl][CtCommentImpl]// TODO: why? this is already done in preheater
            [CtCommentImpl]// if ( bundle.getImportStrategy().isCreateOrCreateAndUpdate() )
            [CtCommentImpl]// {
            [CtCommentImpl]// trackedEntityInstance.setCreated( now );
            [CtCommentImpl]// trackedEntityInstance.setCreatedAtClient( now );
            [CtCommentImpl]// }
            [CtVariableReadImpl]trackedEntityInstance.setLastUpdated([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]trackedEntityInstance.setLastUpdatedAtClient([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]trackedEntityInstance.setLastUpdatedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUser());
            [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]trackedEntityInstance);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat().putTrackedEntities([CtInvocationImpl][CtVariableReadImpl]bundle.getIdentifier(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]trackedEntityInstance));
            [CtInvocationImpl]handleTrackedEntityAttributeValues([CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtInvocationImpl][CtVariableReadImpl]trackedEntity.getAttributes(), [CtVariableReadImpl]trackedEntityInstance);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.hisp.dhis.tracker.FlushMode.OBJECT == [CtInvocationImpl][CtVariableReadImpl]bundle.getFlushMode()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]session.flush();
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtInvocationImpl][CtVariableReadImpl]trackedEntities.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.postCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.TrackedEntity.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtReturnImpl]return [CtVariableReadImpl]typeReport;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport handleEnrollments([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle bundle) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Enrollment> enrollments = [CtInvocationImpl][CtVariableReadImpl]bundle.getEnrollments();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport typeReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.ENROLLMENT);
        [CtInvocationImpl][CtVariableReadImpl]enrollments.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.preCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Enrollment.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date now = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtVariableReadImpl]enrollments.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Enrollment enrollment = [CtInvocationImpl][CtVariableReadImpl]enrollments.get([CtVariableReadImpl]idx);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.program.ProgramInstance programInstance = [CtInvocationImpl][CtFieldReadImpl]enrollmentTrackerConverterService.from([CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtVariableReadImpl]enrollment);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport objectReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.ENROLLMENT, [CtInvocationImpl][CtVariableReadImpl]programInstance.getUid(), [CtVariableReadImpl]idx);
            [CtInvocationImpl][CtVariableReadImpl]typeReport.addObjectReport([CtVariableReadImpl]objectReport);
            [CtInvocationImpl][CtCommentImpl]// TODO: why? this is already done in preheater
            [CtCommentImpl]// if ( bundle.getImportStrategy().isCreateOrCreateAndUpdate() )
            [CtCommentImpl]// {
            [CtCommentImpl]// programInstance.setCreated( now );
            [CtCommentImpl]// programInstance.setCreatedAtClient( now );
            [CtCommentImpl]// }
            [CtVariableReadImpl]programInstance.setLastUpdated([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]programInstance.setLastUpdatedAtClient([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]programInstance.setLastUpdatedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUser());
            [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]programInstance);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat().putEnrollments([CtInvocationImpl][CtVariableReadImpl]bundle.getIdentifier(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]programInstance));
            [CtInvocationImpl]handleTrackedEntityAttributeValues([CtVariableReadImpl]session, [CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtInvocationImpl][CtVariableReadImpl]enrollment.getAttributes(), [CtInvocationImpl][CtVariableReadImpl]programInstance.getEntityInstance());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.hisp.dhis.tracker.FlushMode.OBJECT == [CtInvocationImpl][CtVariableReadImpl]bundle.getFlushMode()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]session.flush();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.job.TrackerSideEffectDataBundle sideEffectDataBundle = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.hisp.dhis.tracker.job.TrackerSideEffectDataBundle.builder().klass([CtFieldReadImpl]org.hisp.dhis.program.ProgramInstance.class).enrollmentRuleEffects([CtInvocationImpl][CtVariableReadImpl]bundle.getEnrollmentRuleEffects()).eventRuleEffects([CtInvocationImpl][CtVariableReadImpl]bundle.getEventRuleEffects()).object([CtVariableReadImpl]programInstance).importStrategy([CtInvocationImpl][CtVariableReadImpl]bundle.getImportStrategy()).accessedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUsername()).build();
            [CtInvocationImpl][CtFieldReadImpl]sideEffectHandlers.forEach([CtLambdaImpl]([CtParameterImpl] handler) -> [CtInvocationImpl][CtVariableReadImpl]handler.handleSideEffect([CtVariableReadImpl]sideEffectDataBundle));
        }
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtInvocationImpl][CtVariableReadImpl]enrollments.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.postCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Enrollment.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtReturnImpl]return [CtVariableReadImpl]typeReport;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport handleEvents([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle bundle) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Event> events = [CtInvocationImpl][CtVariableReadImpl]bundle.getEvents();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport typeReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.EVENT);
        [CtInvocationImpl][CtVariableReadImpl]events.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.preCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Event.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtVariableReadImpl]events.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Event event = [CtInvocationImpl][CtVariableReadImpl]events.get([CtVariableReadImpl]idx);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.program.ProgramStageInstance programStageInstance = [CtInvocationImpl][CtFieldReadImpl]eventTrackerConverterService.from([CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtVariableReadImpl]event);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport objectReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.EVENT, [CtInvocationImpl][CtVariableReadImpl]programStageInstance.getUid(), [CtVariableReadImpl]idx);
            [CtInvocationImpl][CtVariableReadImpl]typeReport.addObjectReport([CtVariableReadImpl]objectReport);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date now = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
            [CtInvocationImpl][CtCommentImpl]// TODO: why? this is already done in preheater
            [CtCommentImpl]// if ( bundle.getImportStrategy().isCreateOrCreateAndUpdate() )
            [CtCommentImpl]// {
            [CtCommentImpl]// programStageInstance.setCreated( now );
            [CtCommentImpl]// programStageInstance.setCreatedAtClient( now );
            [CtCommentImpl]// }
            [CtVariableReadImpl]programStageInstance.setLastUpdated([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]programStageInstance.setLastUpdatedAtClient([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]programStageInstance.setLastUpdatedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUser());
            [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]programStageInstance);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat().putEvents([CtInvocationImpl][CtVariableReadImpl]bundle.getIdentifier(), [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.singletonList([CtVariableReadImpl]programStageInstance));
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]typeReport.getStats().incCreated();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.hisp.dhis.tracker.FlushMode.OBJECT == [CtInvocationImpl][CtVariableReadImpl]bundle.getFlushMode()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]session.flush();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.job.TrackerSideEffectDataBundle sideEffectDataBundle = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.hisp.dhis.tracker.job.TrackerSideEffectDataBundle.builder().klass([CtFieldReadImpl]org.hisp.dhis.program.ProgramStageInstance.class).enrollmentRuleEffects([CtInvocationImpl][CtVariableReadImpl]bundle.getEnrollmentRuleEffects()).eventRuleEffects([CtInvocationImpl][CtVariableReadImpl]bundle.getEventRuleEffects()).object([CtVariableReadImpl]programStageInstance).importStrategy([CtInvocationImpl][CtVariableReadImpl]bundle.getImportStrategy()).accessedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUsername()).build();
            [CtInvocationImpl][CtFieldReadImpl]sideEffectHandlers.forEach([CtLambdaImpl]([CtParameterImpl] handler) -> [CtInvocationImpl][CtVariableReadImpl]handler.handleSideEffect([CtVariableReadImpl]sideEffectDataBundle));
        }
        [CtInvocationImpl][CtVariableReadImpl]session.flush();
        [CtInvocationImpl][CtVariableReadImpl]events.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.postCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Event.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtReturnImpl]return [CtVariableReadImpl]typeReport;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport handleRelationships([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.bundle.TrackerBundle bundle) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Relationship> relationships = [CtInvocationImpl][CtVariableReadImpl]bundle.getRelationships();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport typeReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerTypeReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.RELATIONSHIP);
        [CtInvocationImpl][CtVariableReadImpl]relationships.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.preCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Relationship.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int idx = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]idx < [CtInvocationImpl][CtVariableReadImpl]relationships.size(); [CtUnaryOperatorImpl][CtVariableWriteImpl]idx++) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Relationship relationship = [CtInvocationImpl][CtVariableReadImpl]relationships.get([CtVariableReadImpl]idx);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.relationship.Relationship toRelationship = [CtInvocationImpl][CtFieldReadImpl]relationshipTrackerConverterService.from([CtInvocationImpl][CtVariableReadImpl]bundle.getPreheat(), [CtVariableReadImpl]relationship);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport objectReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.tracker.report.TrackerObjectReport([CtFieldReadImpl]org.hisp.dhis.tracker.TrackerType.EVENT, [CtInvocationImpl][CtVariableReadImpl]toRelationship.getUid(), [CtVariableReadImpl]idx);
            [CtInvocationImpl][CtVariableReadImpl]typeReport.addObjectReport([CtVariableReadImpl]objectReport);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date now = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date();
            [CtInvocationImpl][CtCommentImpl]// TODO: why? this is already done in preheater
            [CtCommentImpl]// if ( bundle.getImportStrategy().isCreateOrCreateAndUpdate() )
            [CtCommentImpl]// {
            [CtCommentImpl]// toRelationship.setCreated( now );
            [CtCommentImpl]// }
            [CtVariableReadImpl]toRelationship.setLastUpdated([CtVariableReadImpl]now);
            [CtInvocationImpl][CtVariableReadImpl]toRelationship.setLastUpdatedBy([CtInvocationImpl][CtVariableReadImpl]bundle.getUser());
            [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]toRelationship);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]typeReport.getStats().incCreated();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]org.hisp.dhis.tracker.FlushMode.OBJECT == [CtInvocationImpl][CtVariableReadImpl]bundle.getFlushMode()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]session.flush();
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]relationships.forEach([CtLambdaImpl]([CtParameterImpl] o) -> [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]bundleHooks.forEach([CtLambdaImpl]([CtParameterImpl] hook) -> [CtInvocationImpl][CtVariableReadImpl]hook.postCreate([CtFieldReadImpl]org.hisp.dhis.tracker.domain.Relationship.class, [CtVariableReadImpl]o, [CtVariableReadImpl]bundle)));
        [CtReturnImpl]return [CtVariableReadImpl]typeReport;
    }

    [CtMethodImpl][CtCommentImpl]// -----------------------------------------------------------------------------------
    [CtCommentImpl]// Utility Methods
    [CtCommentImpl]// -----------------------------------------------------------------------------------
    private [CtTypeReferenceImpl]void handleTrackedEntityAttributeValues([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheat preheat, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Attribute> attributes, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityInstance trackedEntityInstance) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue> attributeValues = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> attributeValuesForDeletion = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> assignedFileResources = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> unassignedFileResources = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue> attributeValueMap = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]trackedEntityInstance.getTrackedEntityAttributeValues().stream().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toMap([CtLambdaImpl]([CtParameterImpl] teav) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]teav.getAttribute().getUid(), [CtLambdaImpl]([CtParameterImpl] trackedEntityAttributeValue) -> [CtVariableReadImpl]trackedEntityAttributeValue));
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.domain.Attribute at : [CtVariableReadImpl]attributes) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// TEAV.getValue has a lot of trickery behind it since its being used for
            [CtCommentImpl]// encryption, so we can't rely on that to
            [CtCommentImpl]// get empty/null values, instead we build a simple list here to compare with.
            if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtInvocationImpl][CtVariableReadImpl]at.getValue())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]attributeValuesForDeletion.add([CtInvocationImpl][CtVariableReadImpl]at.getAttribute());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]attributeValueMap.containsKey([CtInvocationImpl][CtVariableReadImpl]at.getAttribute()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValueMap.get([CtInvocationImpl][CtVariableReadImpl]at.getAttribute()).getAttribute().getValueType().isFile()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]unassignedFileResources.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValueMap.get([CtInvocationImpl][CtVariableReadImpl]at.getAttribute()).getValue());
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityAttribute attribute = [CtInvocationImpl][CtVariableReadImpl]preheat.get([CtTypeAccessImpl]TrackerIdScheme.UID, [CtFieldReadImpl]org.hisp.dhis.trackedentity.TrackedEntityAttribute.class, [CtInvocationImpl][CtVariableReadImpl]at.getAttribute());
            [CtLocalVariableImpl][CtCommentImpl]// TODO: What to do here? Should attribute == NULL this be allowed?
            [CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue attributeValue = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]attributeValueMap.containsKey([CtInvocationImpl][CtVariableReadImpl]at.getAttribute())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue av = [CtInvocationImpl][CtVariableReadImpl]attributeValueMap.get([CtInvocationImpl][CtVariableReadImpl]at.getAttribute());
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]av.setAttribute([CtVariableReadImpl]attribute).setValue([CtInvocationImpl][CtVariableReadImpl]at.getValue()).setStoredBy([CtInvocationImpl][CtVariableReadImpl]at.getStoredBy());
                [CtAssignmentImpl][CtVariableWriteImpl]attributeValue = [CtVariableReadImpl]av;
                [CtInvocationImpl][CtVariableReadImpl]attributeValues.add([CtVariableReadImpl]attributeValue);
            }
            [CtIfImpl][CtCommentImpl]// new attribute value
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]attributeValue == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]attributeValue = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue();
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValue.setAttribute([CtVariableReadImpl]attribute).setValue([CtInvocationImpl][CtVariableReadImpl]at.getValue()).setStoredBy([CtInvocationImpl][CtVariableReadImpl]at.getStoredBy());
                [CtInvocationImpl][CtVariableReadImpl]attributeValues.add([CtVariableReadImpl]attributeValue);
            }
            [CtIfImpl][CtCommentImpl]// TODO: What to do here? Should this be allowed? i.e ,  attributeValue.getAttribute() != null  this makes a NP
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]attributeValuesForDeletion.contains([CtInvocationImpl][CtVariableReadImpl]at.getAttribute())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute().getValueType().isFile()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]assignedFileResources.add([CtInvocationImpl][CtVariableReadImpl]at.getValue());
            }
            [CtCommentImpl]// if ( !attributeValuesForDeletion.contains( at.getAttribute() )
            [CtCommentImpl]// && attributeValue.getAttribute().getValueType().isFile() )
            [CtCommentImpl]// {
            [CtCommentImpl]// assignedFileResources.add( at.getValue() );
            [CtCommentImpl]// }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.trackedentityattributevalue.TrackedEntityAttributeValue attributeValue : [CtVariableReadImpl]attributeValues) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// since TEAV is the owning side here, we don't bother updating the TE.teav
            [CtCommentImpl]// collection
            [CtCommentImpl]// as it will be reloaded on session clear
            [CtTypeReferenceImpl]org.hisp.dhis.trackedentity.TrackedEntityAttribute attribute = [CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute();
            [CtIfImpl][CtCommentImpl]// // TODO: What to do here? Should this be allowed?
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]attribute == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]attributeValuesForDeletion.contains([CtInvocationImpl][CtVariableReadImpl]attribute.getUid())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]session.remove([CtVariableReadImpl]attributeValue);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]attributeValue.setEntityInstance([CtVariableReadImpl]trackedEntityInstance);
                [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]attributeValue);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute().isGenerated() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute().getTextPattern() != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]reservedValueService.useReservedValue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]attributeValue.getAttribute().getTextPattern(), [CtInvocationImpl][CtVariableReadImpl]attributeValue.getValue());
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]assignedFileResources.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String fr) -> [CtInvocationImpl]assignFileResource([CtVariableReadImpl]session, [CtVariableReadImpl]preheat, [CtVariableReadImpl]fr));
        [CtInvocationImpl][CtVariableReadImpl]unassignedFileResources.forEach([CtLambdaImpl]([CtParameterImpl]java.lang.String fr) -> [CtInvocationImpl]unassignFileResource([CtVariableReadImpl]session, [CtVariableReadImpl]preheat, [CtVariableReadImpl]fr));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void assignFileResource([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheat preheat, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fr) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.fileresource.FileResource fileResource = [CtInvocationImpl][CtVariableReadImpl]preheat.get([CtTypeAccessImpl]TrackerIdScheme.UID, [CtFieldReadImpl]org.hisp.dhis.fileresource.FileResource.class, [CtVariableReadImpl]fr);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fileResource == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]fileResource.setAssigned([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]fileResource);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void unassignFileResource([CtParameterImpl][CtTypeReferenceImpl]org.hibernate.Session session, [CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.tracker.preheat.TrackerPreheat preheat, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String fr) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hisp.dhis.fileresource.FileResource fileResource = [CtInvocationImpl][CtVariableReadImpl]preheat.get([CtTypeAccessImpl]TrackerIdScheme.UID, [CtFieldReadImpl]org.hisp.dhis.fileresource.FileResource.class, [CtVariableReadImpl]fr);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]fileResource == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]fileResource.setAssigned([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]session.persist([CtVariableReadImpl]fileResource);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.hisp.dhis.user.User getUser([CtParameterImpl][CtTypeReferenceImpl]org.hisp.dhis.user.User user, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String userUid) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// ıf user already set, reload the user to make sure its loaded in the current
        [CtCommentImpl]// tx
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]user != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]manager.get([CtFieldReadImpl]org.hisp.dhis.user.User.class, [CtInvocationImpl][CtVariableReadImpl]user.getUid());
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]userUid)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]manager.get([CtFieldReadImpl]org.hisp.dhis.user.User.class, [CtVariableReadImpl]userUid);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]user == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]user = [CtInvocationImpl][CtFieldReadImpl]currentUserService.getCurrentUser();
        }
        [CtReturnImpl]return [CtVariableReadImpl]user;
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package org.pmiops.workbench.utils;
[CtUnresolvedImport]import org.pmiops.workbench.model.RecentWorkspace;
[CtUnresolvedImport]import org.pmiops.workbench.conceptset.ConceptSetMapper;
[CtUnresolvedImport]import org.pmiops.workbench.model.UserRole;
[CtUnresolvedImport]import org.mapstruct.Mapping;
[CtUnresolvedImport]import org.pmiops.workbench.cohorts.CohortMapper;
[CtUnresolvedImport]import org.pmiops.workbench.model.Workspace;
[CtUnresolvedImport]import org.pmiops.workbench.model.CdrVersion;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbCohort;
[CtUnresolvedImport]import org.pmiops.workbench.model.WorkspaceResource;
[CtUnresolvedImport]import org.mapstruct.MappingTarget;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbWorkspace;
[CtUnresolvedImport]import org.pmiops.workbench.utils.mappers.CommonMappers;
[CtUnresolvedImport]import org.mapstruct.Mapper;
[CtUnresolvedImport]import org.pmiops.workbench.model.ResearchPurpose;
[CtUnresolvedImport]import org.pmiops.workbench.firecloud.model.FirecloudWorkspace;
[CtUnresolvedImport]import org.pmiops.workbench.cohortreview.CohortReviewMapper;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbConceptSet;
[CtUnresolvedImport]import static org.mapstruct.NullValuePropertyMappingStrategy.*;
[CtUnresolvedImport]import org.pmiops.workbench.firecloud.model.FirecloudWorkspaceResponse;
[CtUnresolvedImport]import org.pmiops.workbench.firecloud.model.FirecloudWorkspaceAccessEntry;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbCohortReview;
[CtUnresolvedImport]import org.mapstruct.CollectionMappingStrategy;
[CtUnresolvedImport]import org.pmiops.workbench.model.WorkspaceResponse;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbDataset;
[CtUnresolvedImport]import org.pmiops.workbench.model.WorkspaceAccessLevel;
[CtUnresolvedImport]import org.pmiops.workbench.db.model.DbUser;
[CtUnresolvedImport]import org.pmiops.workbench.dataset.DataSetMapper;
[CtInterfaceImpl][CtAnnotationImpl]@org.mapstruct.Mapper(componentModel = [CtLiteralImpl]"spring", collectionMappingStrategy = [CtFieldReadImpl]org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE, uses = [CtNewArrayImpl]{ [CtFieldReadImpl]org.pmiops.workbench.utils.mappers.CommonMappers.class, [CtFieldReadImpl]org.pmiops.workbench.cohorts.CohortMapper.class, [CtFieldReadImpl]org.pmiops.workbench.cohortreview.CohortReviewMapper.class, [CtFieldReadImpl]org.pmiops.workbench.conceptset.ConceptSetMapper.class, [CtFieldReadImpl]org.pmiops.workbench.dataset.DataSetMapper.class })
public interface WorkspaceMapper {
    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"researchPurpose", source = [CtLiteralImpl]"dbWorkspace")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"etag", source = [CtLiteralImpl]"dbWorkspace.version", qualifiedByName = [CtLiteralImpl]"cdrVersionToEtag")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"name", source = [CtLiteralImpl]"dbWorkspace.name")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"id", source = [CtLiteralImpl]"fcWorkspace.name")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"googleBucketName", source = [CtLiteralImpl]"fcWorkspace.bucketName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"creator", source = [CtLiteralImpl]"fcWorkspace.createdBy")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cdrVersionId", source = [CtLiteralImpl]"dbWorkspace.cdrVersion")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.Workspace toApiWorkspace([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.firecloud.model.FirecloudWorkspace fcWorkspace);

    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"researchPurpose", source = [CtLiteralImpl]"dbWorkspace")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"etag", source = [CtLiteralImpl]"version", qualifiedByName = [CtLiteralImpl]"cdrVersionToEtag")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"id", source = [CtLiteralImpl]"firecloudName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"namespace", source = [CtLiteralImpl]"workspaceNamespace")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"creator", source = [CtLiteralImpl]"creator.username")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cdrVersionId", source = [CtLiteralImpl]"cdrVersion")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.Workspace toApiWorkspace([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace);

    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspace", source = [CtLiteralImpl]"dbWorkspace")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"accessLevel", source = [CtLiteralImpl]"firecloudWorkspaceResponse")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceResponse toApiWorkspaceResponse([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.firecloud.model.FirecloudWorkspaceResponse firecloudWorkspaceResponse);

    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"timeReviewed", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"populationDetails", source = [CtLiteralImpl]"specificPopulationsEnum")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"researchOutcomeList", source = [CtLiteralImpl]"researchOutcomeEnumSet")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"disseminateResearchFindingList", source = [CtLiteralImpl]"disseminateResearchEnumSet")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"otherDisseminateResearchFindings", source = [CtLiteralImpl]"disseminateResearchOther")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.ResearchPurpose workspaceToResearchPurpose([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace);

    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspace", source = [CtLiteralImpl]"dbWorkspace")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.RecentWorkspace toApiRecentWorkspace([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceAccessLevel accessLevel);

    [CtMethodImpl][CtCommentImpl]// I believe the following fields are ignored because they are only meant to be set once
    [CtCommentImpl]// My intent was to keep the same functionality as in the original mapper so I left it in
    [CtCommentImpl]// but we should be handling special business case logic like this in our controller/services
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"approved", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"reviewRequested", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"timeRequested", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"specificPopulationsEnum", source = [CtLiteralImpl]"populationDetails", nullValuePropertyMappingStrategy = [CtFieldReadImpl]SET_TO_DEFAULT)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"disseminateResearchEnumSet", source = [CtLiteralImpl]"disseminateResearchFindingList", nullValuePropertyMappingStrategy = [CtFieldReadImpl]SET_TO_DEFAULT)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"disseminateResearchOther", source = [CtLiteralImpl]"otherDisseminateResearchFindings")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"researchOutcomeEnumSet", source = [CtLiteralImpl]"researchOutcomeList", nullValuePropertyMappingStrategy = [CtFieldReadImpl]SET_TO_DEFAULT)
    [CtTypeReferenceImpl]void mergeResearchPurposeIntoWorkspace([CtParameterImpl][CtAnnotationImpl]@org.mapstruct.MappingTarget
    [CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace workspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.ResearchPurpose researchPurpose);

    [CtMethodImpl][CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"email", source = [CtLiteralImpl]"user.username")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"role", source = [CtLiteralImpl]"acl")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.UserRole toApiUserRole([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbUser user, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.firecloud.model.FirecloudWorkspaceAccessEntry acl);

    [CtMethodImpl][CtCommentImpl]// All workspaceResources have one object and all others are null. That should be
    [CtCommentImpl]// defined by a setter where used
    [CtCommentImpl]// This should be set when the resource is set
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceId", source = [CtLiteralImpl]"dbWorkspace.workspaceId")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceFirecloudName", source = [CtLiteralImpl]"dbWorkspace.firecloudName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceBillingStatus", source = [CtLiteralImpl]"dbWorkspace.billingStatus")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"permission", source = [CtLiteralImpl]"accessLevel")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohort", source = [CtLiteralImpl]"dbCohort")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohortReview", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"conceptSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"dataSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"notebook", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"modifiedTime", source = [CtLiteralImpl]"dbCohort.lastModifiedTime")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceResource dbWorkspaceAndDbCohortToWorkspaceResource([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceAccessLevel accessLevel, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbCohort dbCohort);

    [CtMethodImpl][CtCommentImpl]// All workspaceResources have one object and all others are null. That should be
    [CtCommentImpl]// defined by a setter where used
    [CtCommentImpl]// This should be set when the resource is set
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceId", source = [CtLiteralImpl]"dbWorkspace.workspaceId")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceFirecloudName", source = [CtLiteralImpl]"dbWorkspace.firecloudName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceBillingStatus", source = [CtLiteralImpl]"dbWorkspace.billingStatus")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"permission", source = [CtLiteralImpl]"accessLevel")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohortReview", source = [CtLiteralImpl]"dbCohortReview")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohort", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"conceptSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"dataSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"notebook", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"modifiedTime", source = [CtLiteralImpl]"dbCohortReview.lastModifiedTime")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceResource dbWorkspaceAndDbCohortReviewToWorkspaceResource([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceAccessLevel accessLevel, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbCohortReview dbCohortReview);

    [CtMethodImpl][CtCommentImpl]// All workspaceResources have one object and all others are null. That should be
    [CtCommentImpl]// defined by a setter where used
    [CtCommentImpl]// This should be set when the resource is set
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceId", source = [CtLiteralImpl]"dbWorkspace.workspaceId")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceFirecloudName", source = [CtLiteralImpl]"dbWorkspace.firecloudName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceBillingStatus", source = [CtLiteralImpl]"dbWorkspace.billingStatus")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"permission", source = [CtLiteralImpl]"accessLevel")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"conceptSet", source = [CtLiteralImpl]"dbConceptSet")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohort", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohortReview", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"dataSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"notebook", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"modifiedTime", source = [CtLiteralImpl]"dbConceptSet.lastModifiedTime")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceResource dbWorkspaceAndDbConceptSetToWorkspaceResource([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceAccessLevel accessLevel, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbConceptSet dbConceptSet);

    [CtMethodImpl][CtCommentImpl]// All workspaceResources have one object and all others are null. That should be
    [CtCommentImpl]// defined by a setter where used
    [CtCommentImpl]// This should be set when the resource is set
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceId", source = [CtLiteralImpl]"dbWorkspace.workspaceId")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceFirecloudName", source = [CtLiteralImpl]"dbWorkspace.firecloudName")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"workspaceBillingStatus", source = [CtLiteralImpl]"dbWorkspace.billingStatus")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"permission", source = [CtLiteralImpl]"accessLevel")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"dataSet", source = [CtLiteralImpl]"dbDataset")
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohort", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"cohortReview", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"conceptSet", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"notebook", ignore = [CtLiteralImpl]true)
    [CtAnnotationImpl]@org.mapstruct.Mapping(target = [CtLiteralImpl]"modifiedTime", source = [CtLiteralImpl]"dbDataset.lastModifiedTime")
    [CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceResource dbWorkspaceAndDbDatasetToWorkspaceResource([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbWorkspace dbWorkspace, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.WorkspaceAccessLevel accessLevel, [CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.db.model.DbDataset dbDataset);

    [CtMethodImpl]default [CtTypeReferenceImpl]java.lang.String cdrVersionId([CtParameterImpl][CtTypeReferenceImpl]org.pmiops.workbench.model.CdrVersion cdrVersion) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtVariableReadImpl]cdrVersion.getCdrVersionId());
    }
}
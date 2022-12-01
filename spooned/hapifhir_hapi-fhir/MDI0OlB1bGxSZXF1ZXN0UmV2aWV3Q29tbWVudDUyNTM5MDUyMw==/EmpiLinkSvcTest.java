[CompilationUnitImpl][CtPackageDeclarationImpl]package ca.uhn.fhir.jpa.empi.svc;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.fail;
[CtUnresolvedImport]import ca.uhn.fhir.jpa.entity.EmpiLink;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.equalTo;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertFalse;
[CtUnresolvedImport]import ca.uhn.fhir.empi.util.EIDHelper;
[CtUnresolvedImport]import static org.hamcrest.CoreMatchers.is;
[CtUnresolvedImport]import org.hl7.fhir.instance.model.api.IBaseResource;
[CtUnresolvedImport]import ca.uhn.fhir.empi.api.EmpiMatchResultEnum;
[CtUnresolvedImport]import ca.uhn.fhir.jpa.empi.BaseEmpiR4Test;
[CtUnresolvedImport]import static org.hamcrest.MatcherAssert.assertThat;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtUnresolvedImport]import org.hl7.fhir.r4.model.Patient;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import ca.uhn.fhir.empi.api.EmpiLinkSourceEnum;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import ca.uhn.fhir.empi.api.IEmpiLinkSvc;
[CtUnresolvedImport]import org.hamcrest.Matchers;
[CtUnresolvedImport]import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
[CtUnresolvedImport]import org.hl7.fhir.r4.model.IdType;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.junit.jupiter.api.AfterEach;
[CtUnresolvedImport]import ca.uhn.fhir.empi.api.EmpiMatchOutcome;
[CtUnresolvedImport]import org.assertj.core.util.Lists;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertTrue;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl]public class EmpiLinkSvcTest extends [CtTypeReferenceImpl]ca.uhn.fhir.jpa.empi.BaseEmpiR4Test {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]ca.uhn.fhir.empi.api.EmpiMatchOutcome POSSIBLE_MATCH = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]ca.uhn.fhir.empi.api.EmpiMatchOutcome([CtLiteralImpl]null, [CtLiteralImpl]null).setMatchResultEnum([CtTypeAccessImpl]EmpiMatchResultEnum.POSSIBLE_MATCH);

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]ca.uhn.fhir.empi.api.IEmpiLinkSvc myEmpiLinkSvc;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    [CtAnnotationImpl]@org.junit.jupiter.api.AfterEach
    public [CtTypeReferenceImpl]void after() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]myExpungeEverythingService.expungeEverythingByType([CtFieldReadImpl]ca.uhn.fhir.jpa.entity.EmpiLink.class);
        [CtInvocationImpl][CtSuperAccessImpl]super.after();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void compareEmptyPatients() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient();
        [CtInvocationImpl][CtVariableReadImpl]patient.setId([CtLiteralImpl]"Patient/1");
        [CtLocalVariableImpl][CtTypeReferenceImpl]ca.uhn.fhir.empi.api.EmpiMatchResultEnum result = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiResourceMatcherSvc.getMatchResult([CtVariableReadImpl]patient, [CtVariableReadImpl]patient).getMatchResultEnum();
        [CtInvocationImpl]assertEquals([CtTypeAccessImpl]EmpiMatchResultEnum.NO_MATCH, [CtVariableReadImpl]result);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testCreateRemoveLink() [CtBlockImpl]{
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.IdType sourcePatientId = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]goldenPatient.getIdElement().toUnqualifiedVersionless();
        [CtLocalVariableImpl][CtCommentImpl]// TODO NG should be ok to remove - assertEquals(0, goldenPatient.getLink().size());
        [CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient = [CtInvocationImpl]createPatient();
        [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient, [CtFieldReadImpl]ca.uhn.fhir.jpa.empi.svc.EmpiLinkSvcTest.POSSIBLE_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
            [CtInvocationImpl]assertLinkCount([CtLiteralImpl]1);
            [CtCommentImpl]// TODO NG should be ok to remove
            [CtCommentImpl]// Patient newSourcePatient = myPatientDao.read(sourcePatientId);
            [CtCommentImpl]// assertEquals(1, newSourcePatient.getLink().size());
        }
        [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient, [CtTypeAccessImpl]EmpiMatchOutcome.NO_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.MANUAL, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
            [CtInvocationImpl]assertLinkCount([CtLiteralImpl]1);
            [CtCommentImpl]// TODO NG should be ok to remove
            [CtCommentImpl]// Patient newSourcePatient = myPatientDao.read(sourcePatientId);
            [CtCommentImpl]// assertEquals(0, newSourcePatient.getLink().size());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testPossibleDuplicate() [CtBlockImpl]{
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient1 = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient2 = [CtInvocationImpl]createGoldenPatient();
        [CtInvocationImpl][CtCommentImpl]// TODO GGG MDM NOT VALID
        [CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient1, [CtVariableReadImpl]goldenPatient2, [CtTypeAccessImpl]EmpiMatchOutcome.POSSIBLE_DUPLICATE, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testNoMatchBlocksPossibleDuplicate() [CtBlockImpl]{
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient1 = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient2 = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long goldenPatient1Pid = [CtInvocationImpl][CtFieldReadImpl]myIdHelperService.getPidOrNull([CtVariableReadImpl]goldenPatient1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long goldenPatient2Pid = [CtInvocationImpl][CtFieldReadImpl]myIdHelperService.getPidOrNull([CtVariableReadImpl]goldenPatient2);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getLinkBySourceResourcePidAndTargetResourcePid([CtVariableReadImpl]goldenPatient1Pid, [CtVariableReadImpl]goldenPatient2Pid).isPresent());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getLinkBySourceResourcePidAndTargetResourcePid([CtVariableReadImpl]goldenPatient2Pid, [CtVariableReadImpl]goldenPatient1Pid).isPresent());
        [CtInvocationImpl]saveNoMatchLink([CtVariableReadImpl]goldenPatient1Pid, [CtVariableReadImpl]goldenPatient2Pid);
        [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient1, [CtVariableReadImpl]goldenPatient2, [CtTypeAccessImpl]EmpiMatchOutcome.POSSIBLE_DUPLICATE, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Person"));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getEmpiLinksByPersonPidTargetPidAndMatchResult([CtVariableReadImpl]goldenPatient1Pid, [CtVariableReadImpl]goldenPatient2Pid, [CtTypeAccessImpl]EmpiMatchResultEnum.POSSIBLE_DUPLICATE).isPresent());
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testNoMatchBlocksPossibleDuplicateReversed() [CtBlockImpl]{
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient1 = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient2 = [CtInvocationImpl]createGoldenPatient();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long goldenPatient1Pid = [CtInvocationImpl][CtFieldReadImpl]myIdHelperService.getPidOrNull([CtVariableReadImpl]goldenPatient1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Long goldenPatient2Pid = [CtInvocationImpl][CtFieldReadImpl]myIdHelperService.getPidOrNull([CtVariableReadImpl]goldenPatient2);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getLinkBySourceResourcePidAndTargetResourcePid([CtVariableReadImpl]goldenPatient1Pid, [CtVariableReadImpl]goldenPatient2Pid).isPresent());
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getLinkBySourceResourcePidAndTargetResourcePid([CtVariableReadImpl]goldenPatient2Pid, [CtVariableReadImpl]goldenPatient1Pid).isPresent());
        [CtInvocationImpl]saveNoMatchLink([CtVariableReadImpl]goldenPatient2Pid, [CtVariableReadImpl]goldenPatient1Pid);
        [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient1, [CtVariableReadImpl]goldenPatient2, [CtTypeAccessImpl]EmpiMatchOutcome.POSSIBLE_DUPLICATE, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Person"));
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.getEmpiLinksByPersonPidTargetPidAndMatchResult([CtVariableReadImpl]goldenPatient1Pid, [CtVariableReadImpl]goldenPatient2Pid, [CtTypeAccessImpl]EmpiMatchResultEnum.POSSIBLE_DUPLICATE).isPresent());
        [CtInvocationImpl]assertLinkCount([CtLiteralImpl]1);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void saveNoMatchLink([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long theGoldenResourcePid, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Long theTargetPid) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]ca.uhn.fhir.jpa.entity.EmpiLink noMatchLink = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.newEmpiLink().setGoldenResourcePid([CtVariableReadImpl]theGoldenResourcePid).setTargetPid([CtVariableReadImpl]theTargetPid).setLinkSource([CtTypeAccessImpl]EmpiLinkSourceEnum.MANUAL).setMatchResult([CtTypeAccessImpl]EmpiMatchResultEnum.NO_MATCH);
        [CtInvocationImpl]saveLink([CtVariableReadImpl]noMatchLink);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testManualEmpiLinksCannotBeModifiedBySystem() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Patient goldenPatient = createGoldenPatient(buildJaneSourcePatient());
        [CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient = [CtInvocationImpl]createGoldenPatient([CtInvocationImpl]buildJanePatient());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient = [CtInvocationImpl]createPatient([CtInvocationImpl]buildJanePatient());
        [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient, [CtTypeAccessImpl]EmpiMatchOutcome.NO_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.MANUAL, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient, [CtTypeAccessImpl]EmpiMatchOutcome.NEW_PERSON_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtLiteralImpl]null);
            [CtInvocationImpl]fail();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.server.exceptions.InternalErrorException e) [CtBlockImpl]{
            [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]"EMPI system is not allowed to modify links on manually created links")));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testAutomaticallyAddedNO_MATCHEmpiLinksAreNotAllowed() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Patient goldenPatient = createGoldenPatient(buildJaneSourcePatient());
        [CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient = [CtInvocationImpl]createGoldenPatient([CtInvocationImpl]buildJanePatient());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient = [CtInvocationImpl]createPatient([CtInvocationImpl]buildJanePatient());
        [CtTryImpl][CtCommentImpl]// Test: it should be impossible to have a AUTO NO_MATCH record.  The only NO_MATCH records in the system must be MANUAL.
        try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkSvc.updateLink([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient, [CtTypeAccessImpl]EmpiMatchOutcome.NO_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.AUTO, [CtInvocationImpl]createContextForUpdate([CtLiteralImpl]"Patient"));
            [CtInvocationImpl]fail();
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.server.exceptions.InternalErrorException e) [CtBlockImpl]{
            [CtInvocationImpl]MatcherAssert.assertThat([CtInvocationImpl][CtVariableReadImpl]e.getMessage(), [CtInvocationImpl]CoreMatchers.is([CtInvocationImpl]CoreMatchers.equalTo([CtLiteralImpl]"EMPI system is not allowed to automatically NO_MATCH a resource")));
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    public [CtTypeReferenceImpl]void testSyncDoesNotSyncNoMatchLinks() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Patient sourcePatient = createGoldenPatient(buildJaneSourcePatient());
        [CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient goldenPatient = [CtInvocationImpl]createGoldenPatient([CtInvocationImpl]buildJanePatient());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient1 = [CtInvocationImpl]createPatient([CtInvocationImpl]buildJanePatient());
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.hl7.fhir.r4.model.Patient patient2 = [CtInvocationImpl]createPatient([CtInvocationImpl]buildJanePatient());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDao.count());
        [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.createOrUpdateLinkEntity([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient1, [CtTypeAccessImpl]EmpiMatchOutcome.NEW_PERSON_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.MANUAL, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
        [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.createOrUpdateLinkEntity([CtVariableReadImpl]goldenPatient, [CtVariableReadImpl]patient2, [CtTypeAccessImpl]EmpiMatchOutcome.NO_MATCH, [CtTypeAccessImpl]EmpiLinkSourceEnum.MANUAL, [CtInvocationImpl]createContextForCreate([CtLiteralImpl]"Patient"));
        [CtLocalVariableImpl][CtCommentImpl]// myEmpiLinkSvc.syncEmpiLinksToPersonLinks(sourcePatient, createContextForCreate("Patient"));
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]ca.uhn.fhir.jpa.entity.EmpiLink> targets = [CtInvocationImpl][CtFieldReadImpl]myEmpiLinkDaoSvc.findEmpiLinksByGoldenResource([CtVariableReadImpl]goldenPatient);
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtVariableReadImpl]targets.isEmpty());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]targets.size());
        [CtLocalVariableImpl][CtCommentImpl]// TODO NG - OK? original assertTrue(goldenPatient.hasLink());
        [CtCommentImpl]// TODO GGG update this test once we decide what has to happen here. There is no more "syncing links"
        [CtCommentImpl]// assertEquals(patient1.getIdElement().toVersionless().getValue(), sourcePatient.getLinkFirstRep().getTarget().getReference());
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> actual = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targets.stream().map([CtLambdaImpl]([CtParameterImpl] link) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]link.getTargetPid().toString()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> expected = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]patient1, [CtVariableReadImpl]patient2).stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getIdElement().toVersionless().getIdPart()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtVariableReadImpl]actual);
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtVariableReadImpl]expected);
        [CtInvocationImpl]MatcherAssert.assertThat([CtVariableReadImpl]actual, [CtInvocationImpl][CtTypeAccessImpl]org.hamcrest.Matchers.containsInAnyOrder([CtInvocationImpl][CtVariableReadImpl]expected.toArray()));
    }
}
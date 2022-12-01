[CompilationUnitImpl][CtPackageDeclarationImpl]package org.openrefine.wikidata.qa.scrutinizers;
[CtUnresolvedImport]import org.openrefine.wikidata.updates.ItemUpdateBuilder;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.Statement;
[CtUnresolvedImport]import org.testng.annotations.Test;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.implementation.StatementImpl;
[CtUnresolvedImport]import org.openrefine.wikidata.updates.ItemUpdate;
[CtUnresolvedImport]import org.openrefine.wikidata.qa.ConstraintFetcher;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.helpers.Datamodel;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;
[CtUnresolvedImport]import org.openrefine.wikidata.qa.MockConstraintFetcher;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.NoValueSnak;
[CtUnresolvedImport]import org.openrefine.wikidata.testing.TestingData;
[CtUnresolvedImport]import org.wikidata.wdtk.datamodel.interfaces.Value;
[CtUnresolvedImport]import static org.mockito.Mockito.mock;
[CtClassImpl]public class ConflictsWithScrutinizerTest extends [CtTypeReferenceImpl]org.openrefine.wikidata.qa.scrutinizers.ScrutinizerTest {
    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.openrefine.wikidata.qa.scrutinizers.EditScrutinizer getScrutinizer() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openrefine.wikidata.qa.scrutinizers.ConflictsWithScrutinizer();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testTrigger() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ItemIdValue idA = [CtFieldReadImpl]org.openrefine.wikidata.testing.TestingData.existingId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue conflictsWithPid = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithPid;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value conflictsWithValue = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithStatementValue;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue propertyWithConflictsPid = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictingStatementPid;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value conflictingValue = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictingStatementValue;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ValueSnak value1 = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeValueSnak([CtVariableReadImpl]conflictsWithPid, [CtVariableReadImpl]conflictsWithValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ValueSnak value2 = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeValueSnak([CtVariableReadImpl]propertyWithConflictsPid, [CtVariableReadImpl]conflictingValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement statement1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.implementation.StatementImpl([CtLiteralImpl]"P50", [CtVariableReadImpl]value1, [CtVariableReadImpl]idA);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement statement2 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.implementation.StatementImpl([CtLiteralImpl]"P31", [CtVariableReadImpl]value2, [CtVariableReadImpl]idA);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdate updateA = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdateBuilder([CtVariableReadImpl]idA).addStatement([CtVariableReadImpl]statement1).addStatement([CtVariableReadImpl]statement2).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue propertyIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2302");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.EntityIdValue entityIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q21502838");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue property = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2306");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value propertyValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P31");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue item = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2305");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value itemValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q5");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement> statementStream = [CtInvocationImpl]createStatementStream([CtVariableReadImpl]propertyIdValue, [CtVariableReadImpl]entityIdValue, [CtVariableReadImpl]property, [CtVariableReadImpl]propertyValue, [CtVariableReadImpl]item, [CtVariableReadImpl]itemValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.qa.ConstraintFetcher fetcher = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.openrefine.wikidata.qa.ConstraintFetcher.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtVariableReadImpl]fetcher.getConstraintsByType([CtVariableReadImpl]conflictsWithPid, [CtLiteralImpl]"Q21502838")).thenReturn([CtVariableReadImpl]statementStream);
        [CtInvocationImpl]setFetcher([CtVariableReadImpl]fetcher);
        [CtInvocationImpl]scrutinize([CtVariableReadImpl]updateA);
        [CtInvocationImpl]assertWarningsRaised([CtTypeAccessImpl]ConflictsWithScrutinizer.type);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testNoIssue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ItemIdValue idA = [CtFieldReadImpl]org.openrefine.wikidata.testing.TestingData.existingId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue conflictsWithPid = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithPid;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value conflictsWithValue = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithStatementValue;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ValueSnak value1 = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeValueSnak([CtVariableReadImpl]conflictsWithPid, [CtVariableReadImpl]conflictsWithValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement statement1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.implementation.StatementImpl([CtLiteralImpl]"P50", [CtVariableReadImpl]value1, [CtVariableReadImpl]idA);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdate updateA = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdateBuilder([CtVariableReadImpl]idA).addStatement([CtVariableReadImpl]statement1).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue propertyIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2302");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.EntityIdValue entityIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q21502838");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue property = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2306");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value propertyValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P31");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue item = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2305");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value itemValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q5");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement> statementStream = [CtInvocationImpl]createStatementStream([CtVariableReadImpl]propertyIdValue, [CtVariableReadImpl]entityIdValue, [CtVariableReadImpl]property, [CtVariableReadImpl]propertyValue, [CtVariableReadImpl]item, [CtVariableReadImpl]itemValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.qa.ConstraintFetcher fetcher = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.openrefine.wikidata.qa.ConstraintFetcher.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtVariableReadImpl]fetcher.getConstraintsByType([CtVariableReadImpl]conflictsWithPid, [CtLiteralImpl]"Q21502838")).thenReturn([CtVariableReadImpl]statementStream);
        [CtInvocationImpl]setFetcher([CtVariableReadImpl]fetcher);
        [CtInvocationImpl]scrutinize([CtVariableReadImpl]updateA);
        [CtInvocationImpl]assertNoWarningRaised();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.testng.annotations.Test
    public [CtTypeReferenceImpl]void testNoValueSnak() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ItemIdValue idA = [CtFieldReadImpl]org.openrefine.wikidata.testing.TestingData.existingId;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue conflictsWithPid = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithPid;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value conflictsWithValue = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictsWithStatementValue;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue propertyWithConflictsPid = [CtFieldReadImpl]org.openrefine.wikidata.qa.MockConstraintFetcher.conflictingStatementPid;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.ValueSnak value1 = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeValueSnak([CtVariableReadImpl]conflictsWithPid, [CtVariableReadImpl]conflictsWithValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.NoValueSnak value2 = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeNoValueSnak([CtVariableReadImpl]propertyWithConflictsPid);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement statement1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.implementation.StatementImpl([CtLiteralImpl]"P50", [CtVariableReadImpl]value1, [CtVariableReadImpl]idA);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement statement2 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.implementation.StatementImpl([CtLiteralImpl]"P31", [CtVariableReadImpl]value2, [CtVariableReadImpl]idA);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdate updateA = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.openrefine.wikidata.updates.ItemUpdateBuilder([CtVariableReadImpl]idA).addStatement([CtVariableReadImpl]statement1).addStatement([CtVariableReadImpl]statement2).build();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue propertyIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2302");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.EntityIdValue entityIdValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q21502838");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue property = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2306");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value propertyValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P31");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue item = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataPropertyIdValue([CtLiteralImpl]"P2305");
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Value itemValue = [CtInvocationImpl][CtTypeAccessImpl]org.wikidata.wdtk.datamodel.helpers.Datamodel.makeWikidataItemIdValue([CtLiteralImpl]"Q5");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.wikidata.wdtk.datamodel.interfaces.Statement> statementStream = [CtInvocationImpl]createStatementStream([CtVariableReadImpl]propertyIdValue, [CtVariableReadImpl]entityIdValue, [CtVariableReadImpl]property, [CtVariableReadImpl]propertyValue, [CtVariableReadImpl]item, [CtVariableReadImpl]itemValue);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openrefine.wikidata.qa.ConstraintFetcher fetcher = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.openrefine.wikidata.qa.ConstraintFetcher.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtVariableReadImpl]fetcher.getConstraintsByType([CtVariableReadImpl]conflictsWithPid, [CtLiteralImpl]"Q21502838")).thenReturn([CtVariableReadImpl]statementStream);
        [CtInvocationImpl]setFetcher([CtVariableReadImpl]fetcher);
        [CtInvocationImpl]scrutinize([CtVariableReadImpl]updateA);
        [CtInvocationImpl]assertNoWarningRaised();
    }
}